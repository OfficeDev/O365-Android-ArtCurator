/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 *  See full license at the bottom of this file.
 */
package com.microsoft.artcurator.ui.main;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.microsoft.outlookservices.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.microsoft.artcurator.util.DisplayUtils.dpToPx;

/**
 * Image adapter used to hold the pictures for display
 */
public class ImagesAdapter extends BaseAdapter {

    /**
     * The item to display - associated an image to the Message that contains it
     */
    public static class ImgItem {
        public final Bitmap mBitmap;
        public final Message mMessage;

        public ImgItem(Bitmap bitmap, Message message) {
            mBitmap = bitmap;
            mMessage = message;
        }
    }

    final List<ImgItem> mImgs = new ArrayList<>();

    public void sort() {
        Collections.sort(mImgs, new Comparator<ImgItem>() {
            @Override
            public int compare(ImgItem lhs, ImgItem rhs) {
                return rhs.mMessage.getDateTimeReceived()
                        .compareTo(
                                lhs.mMessage.getDateTimeReceived());
            }
        });
    }

    @Override
    public int getCount() {
        return mImgs.size();
    }

    @Override
    public ImgItem getItem(int position) {
        return mImgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;
        Context context = parent.getContext();

        if (null == imageView) {
            Resources res = context.getResources();
            int imgDimens = (int) dpToPx(res, 140);
            int paddingDimens = (int) dpToPx(res, 8);
            imageView = new ImageView(context);
            GridView.LayoutParams imgParams = new GridView.LayoutParams(imgDimens, imgDimens);
            imageView.setLayoutParams(imgParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(paddingDimens, paddingDimens, paddingDimens, paddingDimens);
        }

        imageView.setImageBitmap(mImgs.get(position).mBitmap);

        return imageView;
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