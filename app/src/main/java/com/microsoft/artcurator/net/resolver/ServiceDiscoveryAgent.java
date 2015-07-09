/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 *  See full license at the bottom of this file.
 */
package com.microsoft.artcurator.net.resolver;

import android.os.Handler;
import android.os.Looper;

import com.microsoft.artcurator.inject.ObjectGraphInjector;
import com.microsoft.discoveryservices.ServiceInfo;
import com.microsoft.discoveryservices.odata.DiscoveryClient;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

/**
 * Asynchronous loader-class to perform service discovery
 */
public class ServiceDiscoveryAgent {

    /**
     * Static reference to the Services discovered by this agent
     */
    public static ServiceInfoLookup mServiceInfoLookup;

    @Inject
    protected DiscoveryClient mDiscoveryClient;

    public ServiceDiscoveryAgent(ObjectGraphInjector injector) {
        injector.inject(this);
    }

    /**
     * Discovers the Office 365 services available to the logged-in user
     *
     * @param serviceDiscoveryListener callback to notify once the services have been discovered
     *                                 or if an error is encountered
     */
    public void discover(final ServiceDiscoveryListener serviceDiscoveryListener) {
        final Handler uiThread = new Handler(Looper.getMainLooper());
        if (null != mServiceInfoLookup) {
            uiThread.post(doOnDone(serviceDiscoveryListener));
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mServiceInfoLookup = loadServiceInfoMap();
                        uiThread.post(doOnDone(serviceDiscoveryListener));
                    } catch (InterruptedException | ExecutionException e) {
                        uiThread.post(doOnFail(serviceDiscoveryListener, e));
                    }
                }
            }).start();
        }
    }

    private Runnable doOnFail(final ServiceDiscoveryListener serviceDiscoveryListener,
                              final Exception e) {
        return new Runnable() {
            @Override
            public void run() {
                serviceDiscoveryListener.onServiceDiscoveryFailed(e);
            }
        };
    }

    private Runnable doOnDone(final ServiceDiscoveryListener serviceDiscoveryListener) {
        return new Runnable() {
            @Override
            public void run() {
                serviceDiscoveryListener.onServicesDiscovered();
            }
        };
    }

    private ServiceInfoLookup loadServiceInfoMap()
            throws InterruptedException, ExecutionException {
        final List<ServiceInfo> serviceInfoList
                = mDiscoveryClient.getservices().read().get();
        return new ServiceInfoLookup() {{
            for (ServiceInfo serviceInfo : serviceInfoList) {
                put(serviceInfo.getcapability(), serviceInfo);
            }
        }};
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