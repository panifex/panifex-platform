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
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.systemPackages;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;
import static org.ops4j.pax.exam.CoreOptions.wrappedBundle;

import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.web.service.spi.ServletListener;
import org.ops4j.pax.web.service.spi.WebListener;

public abstract class IWebTestSupport extends ITestSupport {

    protected static final String HOSTNAME = "127.0.0.1";
    protected static final String PORT = "8181";
    protected static final String URL = "http://" + HOSTNAME + ":" + PORT;

    // listeners
    private HttpClient httpClient;
    private ServletListener servletListener;
    private WebListener webListener;

    protected final Option[] webConfigure() {
        return OptionUtils.combine(
            baseConfigure(),

            systemProperty("java.protocol.handler.pkgs").value("org.ops4j.pax.url"),
            systemProperty("org.osgi.service.http.hostname").value(HOSTNAME),
            systemProperty("org.osgi.service.http.port").value(PORT),

            systemPackages("javax.annotation.security;version=1.1"),

            mavenBundle("commons-codec", "commons-codec").versionAsInProject(),
            wrappedBundle(mavenBundle("net.sourceforge.cssparser", "cssparser").versionAsInProject()),
            wrappedBundle(mavenBundle("net.sourceforge.htmlunit", "htmlunit").versionAsInProject()),
            wrappedBundle(mavenBundle("net.sourceforge.htmlunit", "htmlunit-core-js").versionAsInProject()),
            wrappedBundle(mavenBundle("net.sourceforge.nekohtml", "nekohtml").versionAsInProject()),
            wrappedBundle(mavenBundle("org.apache.httpcomponents", "httpcore").versionAsInProject()),
            wrappedBundle(mavenBundle("org.apache.httpcomponents", "httpmime").versionAsInProject()),
            wrappedBundle(mavenBundle("org.apache.httpcomponents", "httpclient").versionAsInProject()),
            mavenBundle("org.apache.xbean", "xbean-bundleutils").versionAsInProject(),
            mavenBundle("org.apache.xbean", "xbean-finder").versionAsInProject(),
            mavenBundle("org.ops4j.pax.logging", "pax-logging-service").versionAsInProject(),
            mavenBundle("org.ops4j.pax.swissbox", "pax-swissbox-bnd").versionAsInProject(),
            mavenBundle("org.ops4j.pax.swissbox", "pax-swissbox-property").versionAsInProject(),
            mavenBundle("org.ops4j.pax.url", "pax-url-aether").versionAsInProject(),
            mavenBundle("org.ops4j.pax.url", "pax-url-commons").versionAsInProject(),
            mavenBundle("org.ops4j.pax.url", "pax-url-wrap").versionAsInProject(),
            mavenBundle("org.ops4j.pax.web", "pax-web-jetty-bundle").versionAsInProject(),
            mavenBundle("org.ops4j.pax.web", "pax-web-extender-whiteboard").versionAsInProject(),
            mavenBundle("org.ow2.asm", "asm-all").versionAsInProject(),
            mavenBundle("org.jsoup", "jsoup").versionAsInProject(),
            wrappedBundle(mavenBundle("org.w3c.css", "sac").versionAsInProject()),
            wrappedBundle(mavenBundle("xalan", "xalan").versionAsInProject()),
            wrappedBundle(mavenBundle("xerces", "xercesImpl").versionAsInProject()));
    }

    protected final void initServletListener() {
        initServletListener(null);
    }

    protected final void initServletListener(String servletName) {
        if (servletName == null) {
            servletListener = new ServletListenerImpl();
        } else {
            servletListener = new ServletListenerImpl(servletName);
        }
        bundleContext.registerService(ServletListener.class, servletListener,
                null);
    }

    protected final void waitForServletListener() {
        new WaitCondition("servlet startup") {
            @Override
            protected boolean isFulfilled() {
                return ((ServletListenerImpl) servletListener).gotEvent();
            }
        }.waitForCondition();
    }

    protected final void initWebListener() {
        webListener = new WebListenerImpl();
        bundleContext.registerService(WebListener.class, webListener, null);
    }

    protected final void waitForWebListener() {
        // register web listener if it is not registered yet
        if (webListener == null) {
            initWebListener();
        }
        new WaitCondition("webapp startup") {
            @Override
            protected boolean isFulfilled() {
                return ((WebListenerImpl) webListener).gotEvent();
            }
        }.waitForCondition();
    }

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
