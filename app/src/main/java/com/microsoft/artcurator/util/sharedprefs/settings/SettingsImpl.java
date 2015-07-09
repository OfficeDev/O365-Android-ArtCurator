/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 *  See full license at the bottom of this file.
 */
package com.microsoft.artcurator.util.sharedprefs.settings;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.microsoft.artcurator.R;
import com.microsoft.artcurator.app.ArtCuratorApplication;
import com.microsoft.artcurator.util.sharedprefs.SettingsConfig;
import com.microsoft.artcurator.util.sharedprefs.SharedPrefsUtil;

import static com.microsoft.artcurator.util.sharedprefs.SettingsConfig.Keys.DISLIKES_READ;
import static com.microsoft.artcurator.util.sharedprefs.SettingsConfig.Keys.DISLIKES_RESPONSE;
import static com.microsoft.artcurator.util.sharedprefs.SettingsConfig.Keys.DISLIKES_SHOULD_RESPOND;
import static com.microsoft.artcurator.util.sharedprefs.SettingsConfig.Keys.FORCE_RELOAD;
import static com.microsoft.artcurator.util.sharedprefs.SettingsConfig.Keys.LIKES_READ;
import static com.microsoft.artcurator.util.sharedprefs.SettingsConfig.Keys.LIKES_RESPONSE;
import static com.microsoft.artcurator.util.sharedprefs.SettingsConfig.Keys.LIKES_SHOULD_RESPOND;
import static com.microsoft.artcurator.util.sharedprefs.SettingsConfig.Keys.SUBMISSIONS_DIR;

/**
 * The delegate used to persist user configuration data across different sessions
 */
public class SettingsImpl implements Settings {

    private final SharedPreferences mSharedPreferences =
            SharedPrefsUtil.getSharedPreferences(new SettingsConfig());

    @SuppressLint("CommitPrefEdits")
    private void commit(String key, Object t) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        try {
            if (t.getClass() == String.class) {
                editor.putString(key, t.toString());
            } else if (t.getClass() == Boolean.class) {
                editor.putBoolean(key, (Boolean) t);
            } else if (t.getClass() == Float.class) {
                editor.putFloat(key, (Float) t);
            } else if (t.getClass() == Long.class) {
                editor.putLong(key, (Long) t);
            } else if (t.getClass() == Integer.class) {
                editor.putInt(key, (Integer) t);
            } else {
                throw new IllegalArgumentException("Unsupported class type " + t.getClass());
            }
        } finally {
            editor.commit();
        }
    }

    @Override
    public void markLikesRead(boolean markRead) {
        commit(LIKES_READ, markRead);
    }

    @Override
    public boolean markLikesRead() {
        return mSharedPreferences.getBoolean(LIKES_READ, true);
    }

    @Override
    public void sendLikesResponse(boolean shouldSend) {
        commit(LIKES_SHOULD_RESPOND, shouldSend);
    }

    @Override
    public boolean sendLikesResponse() {
        return mSharedPreferences.getBoolean(LIKES_SHOULD_RESPOND, true);
    }

    @Override
    public void markDislikesRead(boolean markRead) {
        commit(DISLIKES_READ, markRead);
    }

    @Override
    public boolean markDislikesRead() {
        return mSharedPreferences.getBoolean(DISLIKES_READ, true);
    }

    @Override
    public void sendDislikesResponse(boolean shouldSend) {
        commit(DISLIKES_SHOULD_RESPOND, shouldSend);
    }

    @Override
    public boolean sendDislikesResponse() {
        return mSharedPreferences.getBoolean(DISLIKES_SHOULD_RESPOND, true);
    }

    @Override
    public void submissionsFolder(String folderName) {
        commit(SUBMISSIONS_DIR, folderName);
    }

    @Override
    public String submissionsFolder() {
        return mSharedPreferences.getString(SUBMISSIONS_DIR, "Inbox");
    }

    @Override
    public void likeResponse(String msg) {
        commit(LIKES_RESPONSE, msg);
    }

    @Override
    public String likeResponse() {
        return mSharedPreferences
                .getString(LIKES_RESPONSE,
                        ArtCuratorApplication.getApplication()
                                .getString(R.string.msg_default_like));
    }

    @Override
    public void dislikeResponse(String msg) {
        commit(DISLIKES_RESPONSE, msg);
    }

    @Override
    public String dislikeResponse() {
        return mSharedPreferences
                .getString(DISLIKES_RESPONSE,
                        ArtCuratorApplication.getApplication()
                                .getString(R.string.msg_default_dislike));
    }

    @Override
    public void forceReload(boolean shouldReload) {
        commit(FORCE_RELOAD, shouldReload);
    }

    @Override
    public boolean forceReload() {
        return mSharedPreferences.getBoolean(FORCE_RELOAD, false);
    }

    @Override
    public void drop() {
        mSharedPreferences.edit().clear().apply();
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