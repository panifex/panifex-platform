/*******************************************************************************
 * Panifex platform
 * Copyright (C) 2013  Mario Krizmanic
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 ******************************************************************************/
package org.panifex.test.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public abstract class HttpClientTestSupport {

    private HttpClient httpClient;

    protected final HttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = HttpClientBuilder.create().build();
        }
        return httpClient;
    }

    protected final String testGet(String path, int httpSC) {
        return testGet(path, httpSC, null);
    }

    protected final String testPost(String path, int httpSC) {
        return testPost(path, httpSC, null, null);
    }

    protected final String testPost(String path, int httpSC, String expectedContent, Map<String, String> params) {
        RequestBuilder requestBuilder = RequestBuilder.post()
            .setUri(path);

        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                requestBuilder.addParameter(param.getKey(), param.getValue());
            }
        }

        return testSendHttpRequest(requestBuilder.build(), httpSC, expectedContent);
    }

    protected final String testGet(String path, int httpSC, String expectedContent) {
        return testSendHttpRequest(new HttpGet(path), httpSC, expectedContent);
    }

    protected final String testSendHttpRequest(HttpUriRequest httpRequest, int httpSC, String expectedContent) {
        try {
            HttpResponse response = getHttpClient().execute(httpRequest);

            assertEquals("HttpResponseCode", httpSC, response.getStatusLine().getStatusCode());

            String responseBodyAsString = EntityUtils.toString(response.getEntity());

            if (expectedContent != null) {
                assertTrue("Content: " + responseBodyAsString,
                        responseBodyAsString.contains(expectedContent));
            }
            return responseBodyAsString;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
