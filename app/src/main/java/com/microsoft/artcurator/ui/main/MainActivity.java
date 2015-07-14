/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 *  See full license at the bottom of this file.
 */
package com.microsoft.artcurator.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.artcurator.R;
import com.microsoft.artcurator.exception.NoImagesToDisplayException;
import com.microsoft.artcurator.net.MessageHelper;
import com.microsoft.artcurator.net.resolver.ServiceDiscoveryAgent;
import com.microsoft.artcurator.net.resolver.ServiceDiscoveryListener;
import com.microsoft.artcurator.ui.BaseActivity;
import com.microsoft.artcurator.ui.emaildetails.EmailDetailsActivity;
import com.microsoft.artcurator.ui.settings.SettingsActivity;
import com.microsoft.artcurator.ui.view.SwipeRefreshLayout;
import com.microsoft.artcurator.util.ImageAttachment;
import com.microsoft.artcurator.util.UICompatFutureCallback;
import com.microsoft.artcurator.util.sharedprefs.settings.Settings;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.microsoft.artcurator.ui.emaildetails.EmailDetailsActivity.ARG_DESC;
import static com.microsoft.artcurator.ui.emaildetails.EmailDetailsActivity.ARG_MSG_ID;
import static com.microsoft.artcurator.ui.emaildetails.EmailDetailsActivity.ARG_SENDER;
import static com.microsoft.artcurator.ui.emaildetails.EmailDetailsActivity.ARG_SUBJECT;
import static com.microsoft.artcurator.ui.emaildetails.EmailDetailsActivity.ARG_TIME_RECV;

/**
 * The central attachment-viewing screen of the app - all images are shown as thumbnails
 * for the user to quickly browse
 */
public class MainActivity extends BaseActivity
        implements
        ServiceDiscoveryListener,
        SwipeRefreshLayout.OnRefreshListener,
        Runnable,
        AdapterView.OnItemClickListener {

    public static final int SPINNER_TIMEOUT = 10000;

    @Inject
    protected ServiceDiscoveryAgent mDiscoveryAgent;

    @Inject
    protected Settings mSettings;

    @InjectView(R.id.swipe_refresh)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.gridview)
    protected GridView mGridView;

    @InjectView(R.id.populate_btn)
    protected Button mPopulateBtn;

    @InjectView(R.id.populate_layout)
    protected RelativeLayout mPopulateLayout;

    @InjectView(R.id.empty)
    protected TextView mEmptyView;

    @InjectView(R.id.empty_layout)
    protected ScrollView mEmptyLayout;

    private MessageHelper mMessageHelper;
    private ImagesAdapter mImagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessageHelper = new MessageHelper(this);
        mImagesAdapter = new ImagesAdapter();
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mGridView.setAdapter(mImagesAdapter);
        mImagesAdapter.notifyDataSetChanged();
        mGridView.setEmptyView(mEmptyView);
        mGridView.setOnItemClickListener(this);
        mSettings.forceReload(false);
        mDiscoveryAgent.discover(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.post(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSettings.forceReload()) {
            mSwipeRefreshLayout.post(this);
            mSettings.forceReload(false);
            onRefresh();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            goToSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    public void onServicesDiscovered() {
        onRefresh();
    }

    @Override
    public void onServiceDiscoveryFailed(Exception e) {
        e.printStackTrace();
        mEmptyView.setText(R.string.fail_discovery);
        mSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, R.string.err_discovery, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        Timber.d("onRefresh");
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        mPopulateLayout.setVisibility(View.GONE);
        mEmptyLayout.setVisibility(View.GONE);
        mImagesAdapter.mImgs.clear();
        mImagesAdapter.notifyDataSetChanged();
        mMessageHelper.loadMessages(mImgItemCallback);
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, SPINNER_TIMEOUT);
    }

    @Override
    public void run() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImagesAdapter.ImgItem clickTarget = mImagesAdapter.getItem(position);
        Intent args = new Intent(this, EmailDetailsActivity.class);
        EmailDetailsActivity.largeTransactionHack = clickTarget.mBitmap;
        args.putExtra(ARG_SUBJECT, clickTarget.mMessage.getSubject());
        Calendar timeRecv = clickTarget.mMessage.getDateTimeReceived();
        DateFormat format = DateFormat.getDateTimeInstance();
        String timestamp = format.format(timeRecv.getTime());
        args.putExtra(ARG_TIME_RECV, timestamp);
        args.putExtra(ARG_SENDER, clickTarget.mMessage.getSender().getEmailAddress().getName());
        args.putExtra(ARG_DESC, clickTarget.mMessage.getBody().getContent());
        args.putExtra(ARG_MSG_ID, clickTarget.mMessage.getId());
        startActivity(args);
    }

    @OnClick(R.id.populate_btn)
    public void onPopulateClicked(Button clicked) {
        clicked.setEnabled(false);
        Toast.makeText(this, "Sending...", Toast.LENGTH_LONG).show();
        final Resources res = getResources();
        final UICompatFutureCallback<Void> callback = new UICompatFutureCallback<Void>() {
            @Override
            protected void _onSuccess(Void result) {
                mSettings.submissionsFolder("Inbox");
                Toast.makeText(MainActivity.this, "Refreshing", Toast.LENGTH_LONG).show();
                onRefresh();
            }

            @Override
            protected void _onFailure(Throwable t) {
                Toast.makeText(
                        MainActivity.this,
                        "Failed to send image",
                        Toast.LENGTH_SHORT).show();
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ImageAttachment[] imageAttachments = {
                        new ImageAttachment(res, R.mipmap.aero, "Aero"),
                        new ImageAttachment(res, R.mipmap.astronaut, "Astronaut"),
                        new ImageAttachment(res, R.mipmap.bird, "Bird"),
                        new ImageAttachment(res, R.mipmap.cyborg, "Cyborg"),
                        new ImageAttachment(res, R.mipmap.egg, "Egg"),
                        new ImageAttachment(res, R.mipmap.feather, "Feather"),
                        new ImageAttachment(res, R.mipmap.guitar, "Guitar"),
                        new ImageAttachment(res, R.mipmap.mountain, "Mountain"),
                        new ImageAttachment(res, R.mipmap.robot, "Robot"),
                        new ImageAttachment(res, R.mipmap.win, "Win")
                };
                mMessageHelper.populateInbox(imageAttachments, callback);
            }
        }).start();
    }

    private final UICompatFutureCallback<ImagesAdapter.ImgItem> mImgItemCallback
            = new UICompatFutureCallback<ImagesAdapter.ImgItem>() {
        @Override
        protected void _onSuccess(ImagesAdapter.ImgItem result) {
            mImagesAdapter.mImgs.add(result);
            mEmptyView.setText("");
            mEmptyLayout.setVisibility(View.GONE);
            mImagesAdapter.sort();
            mImagesAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
        }

        @Override
        protected void _onFailure(Throwable t) {
            mSwipeRefreshLayout.setRefreshing(false);
            if (t instanceof FileNotFoundException) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.folder_nf_title)
                        + mSettings.submissionsFolder());
                builder.setMessage(R.string.folder_nf_msg);
                builder.setPositiveButton(R.string.settings,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                goToSettings();
                            }
                        });
                builder.show();
            } else if (t instanceof NoImagesToDisplayException) {
                mPopulateLayout.setVisibility(View.VISIBLE);
                mPopulateBtn.setEnabled(true);
                mSwipeRefreshLayout.setVisibility(View.GONE);
            } else {
                mPopulateLayout.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                mEmptyView.setText(t.getMessage());
                mEmptyLayout.setVisibility(View.VISIBLE);
            }
        }
    };
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