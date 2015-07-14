/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 *  See full license at the bottom of this file.
 */
package com.microsoft.artcurator.ui.emaildetails;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.artcurator.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.microsoft.artcurator.ui.emaildetails.EmailDetailsActivity.ARG_DESC;
import static com.microsoft.artcurator.ui.emaildetails.EmailDetailsActivity.ARG_SENDER;
import static com.microsoft.artcurator.ui.emaildetails.EmailDetailsActivity.ARG_SUBJECT;
import static com.microsoft.artcurator.ui.emaildetails.EmailDetailsActivity.ARG_TIME_RECV;


public class EmailDetailsFragment extends Fragment implements OnActionFailedListener<IOException> {

    @InjectView(R.id.scrollView)
    ScrollView mScrollView;

    @InjectView(R.id.img)
    ImageView mImg;

    @InjectView(R.id.timestamp)
    TextView mTimeStamp;

    @InjectView(R.id.author)
    TextView mAuthor;

    @InjectView(R.id.title)
    TextView mTitle;

    @InjectView(R.id.description)
    TextView mDescription;

    @InjectView(R.id.button_layout)
    LinearLayout mButtonLayout;

    private EmailDetailsFragmentCallbacks<IOException> mCallbacks;

    private int btnHeight;

    private String mSubject;
    private String mTimeRecv;
    private String mSender;
    private String mDesc;

    public EmailDetailsFragment() {
    }

    public static Fragment newInstance(String subject, String time, String sender, String desc) {
        Bundle args = new Bundle();
        args.putString(ARG_SUBJECT, subject);
        args.putString(ARG_TIME_RECV, time);
        args.putString(ARG_SENDER, sender);
        args.putString(ARG_DESC, desc);
        EmailDetailsFragment fragment = new EmailDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mSubject = args.getString(ARG_SUBJECT);
        mTimeRecv = args.getString(ARG_TIME_RECV);
        mSender = args.getString(ARG_SENDER);
        mDesc = args.getString(ARG_DESC);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_email_details, container, false);
        ButterKnife.inject(this, layout);
        mTitle.setText(mSubject);
        mAuthor.setText(mSender);
        mTimeStamp.setText(mTimeRecv);
        mDesc = sanitizeComments(mDesc);
        mDescription.setText(Html.fromHtml(mDesc));
        return layout;
    }

    private String sanitizeComments(String desc) {
        Document doc = Jsoup.parse(desc);
        doc.select("script, style, meta, link, comment, CDATA, #comment").remove();
        return doc.html();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImg.setImageBitmap(mCallbacks.getImg());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //noinspection unchecked
        mCallbacks = (EmailDetailsFragmentCallbacks<IOException>) activity;
    }

    @OnClick(R.id.btn_like)
    public void onLikeClicked(ImageButton like) {
        animateAway(like);
        mCallbacks.onLike(this);
    }

    @OnClick(R.id.btn_dislike)
    public void onDislikeClicked(ImageButton dislike) {
        animateAway(dislike);
        mCallbacks.onDislike(this);
    }

    private void animateAway(final View which) {
        btnHeight = mButtonLayout.getHeight();
        mButtonLayout
                .animate()
                .translationY(btnHeight)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mButtonLayout.setVisibility(View.GONE);
                        mScrollView.setLayoutParams(adjustMargins(Direction.DOWN));

                        boolean liked = which.getId() == R.id.btn_like;

                        String msg;
                        Context context = getActivity();
                        if (liked) {
                            msg = context.getString(R.string.liked);
                        } else {
                            msg = context.getString(R.string.disliked);
                        }

                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onActionFailed(IOException e) {
        e.printStackTrace();
        mButtonLayout.setVisibility(View.VISIBLE);
        mButtonLayout
                .animate()
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mScrollView.setLayoutParams(adjustMargins(Direction.UP));
                    }
                });
    }

    private RelativeLayout.LayoutParams adjustMargins(Direction ofTravel) {
        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) mScrollView.getLayoutParams();
        layoutParams.bottomMargin = layoutParams.bottomMargin
                + (ofTravel == Direction.DOWN ? -btnHeight : btnHeight);
        layoutParams.setMargins(
                layoutParams.leftMargin,
                layoutParams.topMargin,
                layoutParams.rightMargin,
                layoutParams.bottomMargin);
        return layoutParams;
    }

    private enum Direction {UP, DOWN}


    public interface EmailDetailsFragmentCallbacks<E extends Exception> {
        Bitmap getImg();

        void onLike(OnActionFailedListener<E> listener);

        void onDislike(OnActionFailedListener<E> listener);
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