/*
 *  Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 *  See full license at the bottom of this file.
 */
package com.microsoft.artcurator.inject;

import com.microsoft.AzureADModule;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.artcurator.net.MessageHelper;
import com.microsoft.artcurator.net.resolver.DiscoveryResolver;
import com.microsoft.artcurator.net.resolver.OutlookResolver;
import com.microsoft.artcurator.net.resolver.ServiceDiscoveryAgent;
import com.microsoft.artcurator.net.resolver.ServiceInfoLookup;
import com.microsoft.artcurator.ui.BaseActivity;
import com.microsoft.artcurator.ui.BaseFragment;
import com.microsoft.artcurator.ui.emaildetails.EmailDetailsActivity;
import com.microsoft.artcurator.ui.emaildetails.EmailDetailsFragment;
import com.microsoft.artcurator.ui.main.MainActivity;
import com.microsoft.artcurator.ui.settings.SettingsActivity;
import com.microsoft.artcurator.ui.signin.SignInActivity;
import com.microsoft.artcurator.ui.signin.SignInFragment;
import com.microsoft.discoveryservices.odata.DiscoveryClient;
import com.microsoft.outlookservices.odata.OutlookClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.microsoft.artcurator.conf.ServiceConstants.DISCOVERY_RESOURCE_ENDPOINT;

/**
 * /**
 * {@link dagger.Module} instance used by {@link BaseActivity} and its subclasses
 * for their providers & injects
 */
@Module(addsTo = ApplicationModule.class,
        includes = AzureADModule.class,
        injects = {
                // ui
                BaseFragment.class,
                SignInActivity.class,
                SignInFragment.class,
                MainActivity.class,
                EmailDetailsActivity.class,
                EmailDetailsFragment.class,
                SettingsActivity.class,
                SettingsActivity.SettingsFragment.class,
                // plumbing
                ServiceDiscoveryAgent.class,
                MessageHelper.class
        },
        library = true
)
public class ActivityModule {

    private final ObjectGraphInjector mInjector;

    public ActivityModule(ObjectGraphInjector injector) {
        mInjector = injector;
    }

    /**
     * Called by Dagger to produce and return a singleton instance of the {@link DiscoveryResolver}
     *
     * @param context the Dagger provided {@link AuthenticationContext} to use
     * @return a shared instance of {@link DiscoveryResolver}
     */
    @Provides
    @Singleton
    public DiscoveryResolver providesDiscoveryResolver(AuthenticationContext context) {
        return new DiscoveryResolver(context);
    }

    /**
     * Called by Dagger to produce and return a singleton instance of the {@link OutlookResolver}
     *
     * @param context the Dagger provided {@link AuthenticationContext} to use
     * @return a shared instance of {@link OutlookResolver}
     */
    @Provides
    @Singleton
    public OutlookResolver providesOutlookResolver(AuthenticationContext context) {
        return new OutlookResolver(context);
    }

    /**
     * Called by Dagger to produce instances of {@link ServiceDiscoveryAgent}
     *
     * @return a new instance of ServiceDiscoveryAgent
     */
    @Provides
    public ServiceDiscoveryAgent providesServiceDiscoverer() {
        return new ServiceDiscoveryAgent(mInjector);
    }

    /**
     * Called by Dagger to produce new instances of {@link ServiceInfoLookup}
     * <p/>
     * <code>@inject</code> annotated instances of this Object <em>must</em> be declared
     * <code>Lazy<></></code>
     *
     * @return a new instance of ServiceInfoLookup
     */
    @Provides
    // this should always be lazy...
    public ServiceInfoLookup providesServiceInfoLookup() {
        return ServiceDiscoveryAgent.mServiceInfoLookup;
    }

    /**
     * Called by Dagger to produce new instances of {@link OutlookClient}
     *
     * @param lookup   the Dagger-provided ServiceInfoLookup instance
     * @param resolver the Dagger-provided OutlookResolver instance
     * @return an instance of OutlookClient
     */
    @Provides
    // this should always be lazy...
    public OutlookClient providesOutlookClient(ServiceInfoLookup lookup, OutlookResolver resolver) {
        return new OutlookClient(
                lookup
                        .getOutlookServiceInfo()
                        .getserviceEndpointUri(), resolver);
    }

    /**
     * Called by Dagger to produce new instances of {@link DiscoveryClient}
     *
     * @param resolver the Dagger-provided DiscoveryResolver instance
     * @return an instance of DiscoveryClient
     */
    @Provides
    public DiscoveryClient providesDiscoveryClient(DiscoveryResolver resolver) {
        return new DiscoveryClient(DISCOVERY_RESOURCE_ENDPOINT, resolver);
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