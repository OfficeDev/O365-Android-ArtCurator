/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 *  See full license at the bottom of this file.
 */
package com.microsoft.artcurator.net;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.common.net.MediaType;
import com.google.common.util.concurrent.FutureCallback;
import com.microsoft.artcurator.R;
import com.microsoft.artcurator.app.ArtCuratorApplication;
import com.microsoft.artcurator.exception.NoImagesToDisplayException;
import com.microsoft.artcurator.ui.BaseActivity;
import com.microsoft.artcurator.ui.main.ImagesAdapter;
import com.microsoft.artcurator.util.BitmapUtils;
import com.microsoft.artcurator.util.ImageAttachment;
import com.microsoft.artcurator.util.UICompatFutureCallback;
import com.microsoft.artcurator.util.sharedprefs.AuthConfig;
import com.microsoft.artcurator.util.sharedprefs.SharedPrefsUtil;
import com.microsoft.artcurator.util.sharedprefs.settings.Settings;
import com.microsoft.outlookservices.Attachment;
import com.microsoft.outlookservices.FileAttachment;
import com.microsoft.outlookservices.Folder;
import com.microsoft.outlookservices.Message;
import com.microsoft.outlookservices.odata.OutlookClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Loads messages from Outlook based on their MIME type, Attachment status, and Folder name.
 * <p/>
 * This class serves to keep the highly-asynchronous / callback-based implementation details of
 * using {@link MessagesLoader} out of Activity or Fragment classes.
 */
public class MessageHelper {

    @Inject
    Settings mSettings;

    @Inject
    Lazy<OutlookClient> mOutlookClient;

    private final Map<String, Message> mAttachmentMessageLookup = new HashMap<>();

    private UICompatFutureCallback<ImagesAdapter.ImgItem> mCallback;

    public MessageHelper(BaseActivity activity) {
        activity.inject(this);
    }

    private static final Set<String> mSupportedMIMETypes = new HashSet<String>() {{
        add(MediaType.JPEG.toString());
        add(MediaType.PNG.toString());
        add(MediaType.BMP.toString());
    }};

    public void loadMessages(
            UICompatFutureCallback<ImagesAdapter.ImgItem> listener) {
        mCallback = listener;
        MessagesLoader.getFoldersAsync(mOutlookClient.get(), mFolderListener);
    }

    public void populateInbox(
            final ImageAttachment[] attachments,
            final UICompatFutureCallback<Void> callback) {
        final Object lock = new Object();
        FutureCallback<Integer> innerCallback = new FutureCallback<Integer>() {
            volatile int count = 0;

            @Override
            public void onSuccess(Integer result) {
                synchronized (lock) {
                    if (attachments.length == ++count) {
                        callback.onSuccess(null);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        };
        for (ImageAttachment attachment : attachments) {
            MessagesLoader.sendMailWithAttachmentAsync(
                    mOutlookClient.get(),
                    attachment.mName,
                    attachment.mBody,
                    SharedPrefsUtil
                            .getSharedPreferences(new AuthConfig())
                            .getString(AuthConfig.Keys.KEY_EMAIL, ""),
                    ImageAttachment.mimetype,
                    attachment.mContentBytes,
                    attachment.mFilename,
                    innerCallback);
        }
    }

    private final FutureCallback<List<Folder>> mFolderListener
            = new FutureCallback<List<Folder>>() {
        @Override
        public void onSuccess(List<Folder> result) {
            Folder sought = null;
            for (Folder f : result) {
                if (f.getDisplayName()
                        .equalsIgnoreCase(mSettings.submissionsFolder())) {
                    sought = f;
                    break;
                }
            }
            if (null != sought) {
                MessagesLoader.getMessagesWithAttachmentsFromFolderAsync(
                        mOutlookClient.get(),
                        sought.getId(),
                        mMessageListener);
            } else {
                mCallback.onFailure(new FileNotFoundException(getString(R.string.fail_find_folder)
                        + mSettings.submissionsFolder()));
            }
        }

        @Override
        public void onFailure(Throwable t) {
            mCallback.onFailure(
                    new IOException(getString(R.string.fail_load_folders)));
        }
    };

    private volatile int mTotalAttachments, mImgCount, mAmtLoaded;

    private final FutureCallback<List<Message>> mMessageListener
            = new FutureCallback<List<Message>>() {
        @Override
        public void onSuccess(List<Message> result) {
            mTotalAttachments = result.size();
            mImgCount = mAmtLoaded = 0;
            for (final Message message : result) {
                MessagesLoader.getAttachmentContentTypeAsync(
                        mOutlookClient.get(), message.getId(),
                        getAttachmentListListener(message));
            }

            if (result.isEmpty()) {
                mCallback.onFailure(
                        new NoImagesToDisplayException(getString(R.string.no_imgs_found)));
            }
        }

        @Override
        public void onFailure(Throwable t) {
            mCallback.onFailure(new IOException(getString(R.string.fail_atchmnt_load)));
        }
    };

    private FutureCallback<List<Attachment>> getAttachmentListListener(final Message message) {
        return new FutureCallback<List<Attachment>>() {
            @Override
            public void onSuccess(List<Attachment> result) {
                mAmtLoaded++;
                for (Attachment attachment : result) {
                    if (mSupportedMIMETypes.contains(attachment.getContentType())) {
                        mImgCount++;
                        mAttachmentMessageLookup.put(attachment.getId(), message);
                        MessagesLoader.getAttachmentAsync(
                                mOutlookClient.get(),
                                message.getId(),
                                attachment.getId(),
                                getAttachmentListener());
                    }
                }
                if (mAmtLoaded == mTotalAttachments && 0 == mImgCount) {
                    mCallback.onFailure(
                            new NoImagesToDisplayException(getString(R.string.no_imgs_found)));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mCallback.onFailure(new IOException(getString(R.string.fail_mime)));
            }
        };
    }

    private FutureCallback<Attachment> getAttachmentListener() {
        return new FutureCallback<Attachment>() {
            @Override
            public void onSuccess(Attachment result) {
                if (result instanceof FileAttachment) {
                    FileAttachment attachment = (FileAttachment) result;
                    Bitmap bmp = BitmapUtils.asBitmap(attachment.getContentBytes());
                    Message srcMsg = mAttachmentMessageLookup.get(result.getId());
                    ImagesAdapter.ImgItem clicked = new ImagesAdapter.ImgItem(bmp, srcMsg);
                    mCallback.onSuccess(clicked);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mCallback.onFailure(
                        new IOException(getString(R.string.fail_single_attachment)));
            }
        };
    }

    private static String getString(int id) {
        return getContext().getString(id);
    }

    private static Context getContext() {
        return ArtCuratorApplication.getApplication();
    }
}
// *********************************************************
//
// O365-Android-Art Curator
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