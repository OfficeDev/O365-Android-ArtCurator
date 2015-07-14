/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 *  See full license at the bottom of this file.
 */
package com.microsoft.artcurator.ui.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.microsoft.AuthenticationManager;
import com.microsoft.artcurator.R;
import com.microsoft.artcurator.ui.BaseActivity;
import com.microsoft.artcurator.ui.BaseFragment;
import com.microsoft.artcurator.ui.signin.SignInActivity;
import com.microsoft.artcurator.util.sharedprefs.AuthConfig;
import com.microsoft.artcurator.util.sharedprefs.SharedPrefsUtil;
import com.microsoft.artcurator.util.sharedprefs.settings.Settings;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * The Settings screen of the app
 */
public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vanilla);
        if (null == savedInstanceState) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new SettingsFragment())
                    .commit();
        }
    }

    public static class SettingsFragment extends BaseFragment {

        @Inject
        Settings mSettings;

        @Inject
        AuthenticationManager mAuthenticationManager;

        @InjectView(R.id.txt_email)
        TextView mEmail;

        @InjectView(R.id.like_mark_read)
        CheckBox mMarkLikesRead;

        @InjectView(R.id.like_send_response)
        CheckBox mSendLikesResponse;

        @InjectView(R.id.like_response)
        EditText mLikeResponse;

        @InjectView(R.id.dislike_mark_read)
        CheckBox mMarkDislikesRead;

        @InjectView(R.id.dislike_send_response)
        CheckBox mSendDislikesResponse;

        @InjectView(R.id.dislike_response)
        EditText mDislikeResponse;

        @InjectView(R.id.submissions_dir)
        EditText mSubmissionsDir;

        public SettingsFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment_settings, container, false);
            ButterKnife.inject(this, layout);
            return layout;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            populateViews();
        }

        private void populateViews() {
            mMarkLikesRead.setChecked(mSettings.markLikesRead());
            boolean sendLikesResponses = mSettings.sendLikesResponse();
            mSendLikesResponse.setChecked(sendLikesResponses);
            mLikeResponse.setVisibility(sendLikesResponses ? View.VISIBLE : View.GONE);
            mLikeResponse.setText(mSettings.likeResponse());
            mMarkDislikesRead.setChecked(mSettings.markDislikesRead());
            boolean sendDislikesResponses = mSettings.sendDislikesResponse();
            mSendDislikesResponse.setChecked(sendDislikesResponses);
            mDislikeResponse.setVisibility(sendDislikesResponses ? View.VISIBLE : View.GONE);
            mDislikeResponse.setText(mSettings.dislikeResponse());
            mSubmissionsDir.setText(mSettings.submissionsFolder());
            mEmail.setText(SharedPrefsUtil
                    .getSharedPreferences(new AuthConfig())
                    .getString(AuthConfig.Keys.KEY_EMAIL, ""));
        }

        @OnClick(R.id.sign_out)
        public void promptReallySignOut() {
            final Context context = getActivity();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder
                    .setTitle(context.getString(R.string.rly_sign_out))
                    .setPositiveButton(context.getString(R.string.sign_out_yes),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    signOut();
                                }
                            })
                    .setNegativeButton(context.getString(R.string.cancel), null).show();
        }

        private void signOut() {
            mSettings.drop();
            mAuthenticationManager.disconnect();
            getActivity().finish();
            Intent galleryActivity = new Intent(getActivity(), SignInActivity.class);
            galleryActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(galleryActivity);
        }

        @OnClick(R.id.like_mark_read)
        public void onLikeMarkReadClicked(CheckBox checkBox) {
            mSettings.markLikesRead(checkBox.isChecked());
        }

        @OnClick(R.id.like_send_response)
        public void onLikeSendResponseClicked(CheckBox checkBox) {
            mSettings.sendLikesResponse(checkBox.isChecked());
            mLikeResponse.setVisibility((checkBox.isChecked() ? View.VISIBLE : View.GONE));
            hideKeyboard(checkBox.getWindowToken());
        }

        private void hideKeyboard(IBinder windowToken) {
            InputMethodManager imm = (InputMethodManager) getActivity()
                    .getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(windowToken, 0);
        }

        @OnClick(R.id.dislike_mark_read)
        public void onDislikeMarkReadClicked(CheckBox checkBox) {
            mSettings.markDislikesRead(checkBox.isChecked());
        }

        @OnClick(R.id.dislike_send_response)
        public void onDislikeSendResponseClicked(CheckBox checkBox) {
            mSettings.sendDislikesResponse(checkBox.isChecked());
            mDislikeResponse.setVisibility((checkBox.isChecked() ? View.VISIBLE : View.GONE));
            hideKeyboard(checkBox.getWindowToken());
        }

        @OnTextChanged(R.id.like_response)
        public void persistLikeResponse(CharSequence response) {
            mSettings.likeResponse(response.toString());
        }

        @OnTextChanged(R.id.dislike_response)
        public void persistDislikeResponse(CharSequence response) {
            mSettings.dislikeResponse(response.toString());
        }

        @OnTextChanged(R.id.submissions_dir)
        public void persistSubmissionsDir(CharSequence dir) {
            if (!mSettings.submissionsFolder().equals(dir.toString())) {
                mSettings.submissionsFolder(dir.toString());
                mSettings.forceReload(true);
            }
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