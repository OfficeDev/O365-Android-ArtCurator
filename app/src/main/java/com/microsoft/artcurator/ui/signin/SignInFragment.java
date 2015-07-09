/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 *  See full license at the bottom of this file.
 */
package com.microsoft.artcurator.ui.signin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.microsoft.AuthenticationManager;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.UserInfo;
import com.microsoft.artcurator.R;
import com.microsoft.artcurator.ui.BaseFragment;
import com.microsoft.artcurator.util.sharedprefs.AuthConfig;
import com.microsoft.artcurator.util.sharedprefs.SharedPrefsUtil;
import com.microsoft.artcurator.util.sharedprefs.settings.Settings;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.microsoft.artcurator.util.sharedprefs.AuthConfig.Keys.KEY_EMAIL;

/**
 * Welcome screen + UI to bootstrap ADAL
 */
public class SignInFragment extends BaseFragment
        implements AuthenticationCallback<AuthenticationResult> {

    @Inject
    protected AuthenticationManager mAuthenticationManager;

    @Inject
    protected Settings mSettings;

    private SignInFragmentCallbacks mSignInFragmentCallbacks;

    public SignInFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        mAuthenticationManager.connect(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mSignInFragmentCallbacks = (SignInFragmentCallbacks) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_signin, container, false);
        ButterKnife.inject(this, layout);
        return layout;
    }

    @OnClick(R.id.welcome_connect)
    public void connect() {
        mAuthenticationManager.connect(this);
    }

    @Override
    public void onSuccess(AuthenticationResult authenticationResult) {
        stash(authenticationResult);
        mSignInFragmentCallbacks.onSignInSuccess();
    }

    @SuppressLint("CommitPrefEdits")
    private void stash(AuthenticationResult authenticationResult) {
        UserInfo userInfo = authenticationResult.getUserInfo();
        SharedPreferences prefs = SharedPrefsUtil.getSharedPreferences(new AuthConfig());
        prefs.edit()
                .putString(KEY_EMAIL, userInfo.getDisplayableId())
                .commit();
    }

    @Override
    public void onError(Exception e) {
        mSignInFragmentCallbacks.onSignInFailed(e);
    }

    /**
     * Callback interface to be implemented by the Activity hosting this Fragment
     */
    public interface SignInFragmentCallbacks {

        /**
         * Invoked upon a success request to authenticate
         */
        void onSignInSuccess();

        /**
         * Invoked upon an unsuccessful request to authenticate
         *
         * @param e the {@link java.lang.Exception} which caused this action to fail
         */
        void onSignInFailed(Exception e);
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