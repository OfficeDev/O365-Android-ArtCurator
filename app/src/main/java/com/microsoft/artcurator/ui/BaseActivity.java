/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 *  See full license at the bottom of this file.
 */
package com.microsoft.artcurator.ui;

import android.annotation.SuppressLint;

import com.microsoft.AzureADModule;
import com.microsoft.AzureAppCompatActivity;
import com.microsoft.artcurator.BuildConfig;
import com.microsoft.artcurator.app.ArtCuratorApplication;
import com.microsoft.artcurator.conf.ServiceConstants;
import com.microsoft.artcurator.inject.ActivityModule;
import com.microsoft.artcurator.inject.ObjectGraphInjector;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import dagger.ObjectGraph;

/**
 * Base Activity class which registers with HockeyApp, checks for updates, and enables DI
 */
@SuppressLint("Registered")
public abstract class BaseActivity
        extends AzureAppCompatActivity
        implements ObjectGraphInjector {

    private static final String APP_IDENTIFIER = "942339f80bc46c3171f04b12b9263f22";

    @Override
    protected ObjectGraph getRootGraph() {
        return ArtCuratorApplication.getApplication().mObjectGraph;
    }

    @Override
    protected AzureADModule getAzureADModule() {
        return new AzureADModule.Builder(this)
                .authenticationResourceId(ServiceConstants.DISCOVERY_RESOURCE_BASE)
                .authorityUrl(ServiceConstants.AUTHORITY_URL)
                .clientId(ServiceConstants.CLIENT_ID)
                .skipBroker(true)
                .redirectUri(ServiceConstants.REDIRECT_URI)
                .build();
    }

    @Override
    protected Object[] getModules() {
        return new Object[]{new ActivityModule(this)};
    }

    @Override
    public void onResume() {
        super.onResume();
        checkForCrashes();
        checkForUpdates();
    }

    @Override
    public void inject(Object target) {
        mObjectGraph.inject(target);
    }

    private void checkForCrashes() {
        CrashManager.register(this, APP_IDENTIFIER);
    }

    private void checkForUpdates() {
        if (BuildConfig.DEBUG) {
            UpdateManager.register(this, APP_IDENTIFIER);
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