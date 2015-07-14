/**
 * The central attachment-viewing screen of the app - all images are shown as thumbnails
 * for the user to quickly browse
 */
package com.microsoft.artcurator.util;

import android.os.Handler;
import android.os.Looper;

import com.google.common.util.concurrent.FutureCallback;

public abstract class UICompatFutureCallback<V>
        implements FutureCallback<V> {

    Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * Invoked with the result of the Future computation when it is successful.
     *
     * @param result
     */
    protected abstract void _onSuccess(V result);

    /**
     * Invoked when a Future computation fails or is canceled.
     * <p/>
     * If the future's get method throws an ExecutionException,
     * then the cause is passed to this method. Any other thrown
     * object is passed unaltered.
     *
     * @param t
     */
    protected abstract void _onFailure(Throwable t);

    @Override
    public final void onSuccess(final V result) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                _onSuccess(result);
            }
        });
    }


    @Override
    public final void onFailure(final Throwable t) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                _onFailure(t);
            }
        });
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