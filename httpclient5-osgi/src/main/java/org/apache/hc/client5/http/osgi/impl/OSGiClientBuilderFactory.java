/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package org.apache.hc.client5.http.osgi.impl;

import java.util.List;

import org.apache.hc.client5.http.impl.sync.CloseableHttpClient;
import org.apache.hc.client5.http.impl.sync.HttpClientBuilder;
import org.apache.hc.client5.http.osgi.services.HttpClientBuilderFactory;

/**
 * @since 4.3
 */
final class OSGiClientBuilderFactory implements HttpClientBuilderFactory {

    private final HttpClientBuilderConfigurator configurator;

    private final List<CloseableHttpClient> trackedHttpClients;

    OSGiClientBuilderFactory(
            final HttpClientBuilderConfigurator configurator,
            final List<CloseableHttpClient> trackedHttpClients) {
        this.configurator = configurator;
        this.trackedHttpClients = trackedHttpClients;
    }

    @Override
    public HttpClientBuilder newBuilder() {
        return configurator.configure(new HttpClientBuilder() {
            @Override
            public CloseableHttpClient build() {
                final CloseableHttpClient client = super.build();
                trackedHttpClients.add(client);
                return client;
            }
        });
    }
}
