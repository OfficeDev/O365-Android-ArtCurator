/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 *  See full license at the bottom of this file.
 */
package com.microsoft.artcurator.util.sharedprefs;

import android.content.Context;

import com.microsoft.artcurator.app.ArtCuratorApplication;

/**
 * Defines the configuration of the {@link android.content.SharedPreferences} instance used to
 * persist user settings
 */
public class SettingsConfig extends SharedPrefsUtil.Config {

    public SettingsConfig() {
        super(ArtCuratorApplication.getApplication(), "Settings", Context.MODE_PRIVATE);
    }

    public static class Keys {
        public static final String LIKES_READ = "LIKES_READ";
        public static final String LIKES_SHOULD_RESPOND = "LIKES_SHOULD_RESPOND";
        public static final String LIKES_RESPONSE = "LIKES_RESPONSE";

        public static final String DISLIKES_READ = "DISLIKES_READ";
        public static final String DISLIKES_SHOULD_RESPOND = "DISLIKES_SHOULD_RESPOND";
        public static final String DISLIKES_RESPONSE = "DISLIKES_RESPONSE";

        public static final String SUBMISSIONS_DIR = "SUBMISSIONS_DIR";
        public static final String SIGNED_IN = "SIGNED_IN";
        public static final String FORCE_RELOAD = "FORCE_RELOAD";
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