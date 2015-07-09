/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 *  See full license at the bottom of this file.
 */
package com.microsoft.artcurator.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.webkit.MimeTypeMap;

import java.io.ByteArrayOutputStream;

public class ImageAttachment {
    public static final String extension = "png";
    public static final String mimetype = MimeTypeMap
            .getSingleton()
            .getMimeTypeFromExtension(extension);

    public final int mId;
    public final String mName, mBody, mFilename;
    public final byte[] mContentBytes;

    public ImageAttachment(Resources res, int id, String name) {
        mId = id;
        mName = name;
        mBody = "Check out " + mName;
        mFilename = mName + "." + extension;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bmp = BitmapFactory.decodeResource(res, id);
        bmp.compress(Bitmap.CompressFormat.PNG, 25, stream);
        mContentBytes = stream.toByteArray();
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