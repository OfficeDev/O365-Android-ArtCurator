/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 *  See full license at the bottom of this file.
 */
package com.microsoft.artcurator.ui.emaildetails;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.common.util.concurrent.FutureCallback;
import com.microsoft.artcurator.R;
import com.microsoft.artcurator.net.MessagesLoader;
import com.microsoft.artcurator.ui.BaseActivity;
import com.microsoft.artcurator.util.UICompatFutureCallback;
import com.microsoft.artcurator.util.sharedprefs.settings.Settings;
import com.microsoft.outlookservices.odata.OutlookClient;

import java.io.IOException;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * The detail-view of a {@link com.microsoft.outlookservices.Message}
 */
public class EmailDetailsActivity extends BaseActivity
        implements EmailDetailsFragment.EmailDetailsFragmentCallbacks<IOException> {

    /**
     * The subject of the {@link com.microsoft.outlookservices.Message}
     */
    public static final String ARG_SUBJECT = "ARG_SUBJECT";

    /**
     * The time the {@link com.microsoft.outlookservices.Message} was recieved
     */
    public static final String ARG_TIME_RECV = "ARG_TIME_RECV";

    /**
     * The sender of the {@link com.microsoft.outlookservices.Message}
     */
    public static final String ARG_SENDER = "ARG_SENDER";

    /**
     * The body-text of the {@link com.microsoft.outlookservices.Message}
     */
    public static final String ARG_DESC = "ARG_DESC";

    /**
     * The id of the {@link com.microsoft.outlookservices.Message}
     */
    public static final String ARG_MSG_ID = "ARG_MSG_ID";

    /**
     * The image to display in the detail view
     */
    public static Bitmap largeTransactionHack;

    @Inject
    protected Settings mSettings;

    @Inject
    protected Lazy<OutlookClient> mOutlookClient;

    private String mMsgId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vanilla);
        if (null == savedInstanceState) {
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            String subject = extras.getString(ARG_SUBJECT);
            String time = extras.getString(ARG_TIME_RECV);
            String sender = extras.getString(ARG_SENDER);
            String desc = extras.getString(ARG_DESC);
            mMsgId = extras.getString(ARG_MSG_ID);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container,
                            EmailDetailsFragment.newInstance(subject, time, sender, desc))
                    .commit();
        }
    }

    @Override
    public Bitmap getImg() {
        return largeTransactionHack;
    }

    @Override
    public void onLike(OnActionFailedListener<IOException> listener) {
        maybeMarkRead(true, listener);
        maybeRespond(true, listener);
    }

    @Override
    public void onDislike(OnActionFailedListener<IOException> listener) {
        maybeMarkRead(false, listener);
        maybeRespond(false, listener);
    }

    private void maybeMarkRead(
            boolean wasLiked,
            final OnActionFailedListener<IOException> listener) {
        if ((wasLiked && mSettings.markLikesRead())
                || !wasLiked && mSettings.markDislikesRead()) {
            mSettings.forceReload(true);
            MessagesLoader.markReadAsync(mOutlookClient.get(), mMsgId,
                    new FutureCallback<String>() {
                        @Override
                        public void onSuccess(String result) {}

                        @Override
                        public void onFailure(Throwable t) {
                            listener.onActionFailed(
                                    new IOException("Failed to mark Message as 'read'"));
                        }
                    });
        }
    }

    private void maybeRespond(boolean wasLiked, OnActionFailedListener<IOException> listener) {
        if (wasLiked && mSettings.sendLikesResponse()) {
            sendLikeResponse(listener);
        } else if (!wasLiked && mSettings.sendDislikesResponse()) {
            sendDislikeResponse(listener);
        }
    }

    private void sendDislikeResponse(OnActionFailedListener<IOException> listener) {
        MessagesLoader.replyAsync(
                mOutlookClient.get(),
                mMsgId,
                mSettings.dislikeResponse(),
                new EmailListener(Action.Dislike, listener));
    }

    private void sendLikeResponse(OnActionFailedListener<IOException> listener) {
        MessagesLoader.replyAsync(
                mOutlookClient.get(),
                mMsgId,
                mSettings.likeResponse(),
                new EmailListener(Action.Like, listener));
    }

    private enum Action {Like, Dislike}

    private class EmailListener extends UICompatFutureCallback<Integer> {

        final Action mAction;
        final OnActionFailedListener<IOException> mListener;

        EmailListener(Action action, OnActionFailedListener<IOException> listener) {
            mAction = action;
            mListener = listener;
        }

        @Override
        public void _onSuccess(Integer result) {
        }

        @Override
        public void _onFailure(Throwable t) {
            mListener.onActionFailed(new IOException("Failed to send reply"));
        }
    }
}
// *********************************************************
//
// O365-Android-ArtCurator https://github.com/OfficeDev/O365-Android-ArtCurator
//
// Copyright (c) Microsoft Corporation
// All rights reserved.
//
// MIT License:
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//
// *********************************************************