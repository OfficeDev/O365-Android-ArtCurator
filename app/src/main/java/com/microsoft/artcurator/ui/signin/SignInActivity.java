/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 *  See full license at the bottom of this file.
 */
package com.microsoft.artcurator.ui.signin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.microsoft.artcurator.R;
import com.microsoft.artcurator.ui.BaseActivity;
import com.microsoft.artcurator.ui.main.MainActivity;

public class SignInActivity
        extends BaseActivity
        implements SignInFragment.SignInFragmentCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vanilla);
        if (null == savedInstanceState) {
            SignInFragment signInFragment = new SignInFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, signInFragment)
                    .commit();
        }
    }

    @Override
    public void onSignInSuccess() {
        goToMainActivity();
    }

    private void goToMainActivity() {
        finish();
        Intent galleryActivity = new Intent(this, MainActivity.class);
        galleryActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(galleryActivity);
    }

    @Override
    public void onSignInFailed(Exception e) {
        showError(e);
    }

    private void showError(Exception e) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.err_signin_title));
        String msg = getString(R.string.sign_in_noorg);
        if (null != e.getMessage()) {
            msg = e.getMessage() + "\n\n" + msg;
        }
        builder.setMessage(msg);
        builder.show();
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