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
package org.panifex.web.shiro.itest;

import static org.junit.Assert.assertEquals;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.wrappedBundle;
import static org.ops4j.pax.exam.MavenUtils.asInProject;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.web.extender.whiteboard.ExtenderConstants;
import org.ops4j.pax.web.service.WebContainerConstants;
import org.osgi.framework.ServiceRegistration;
import org.panifex.module.api.security.DefaultFilterPath;
import org.panifex.module.api.security.FilterPath;
import org.panifex.test.support.ITestSupport;

@RunWith(PaxExam.class)
public final class SecurityFilterTest extends ITestSupport {

    @Configuration
    public Option[] config() {
        return OptionUtils.combine(
            baseConfigure(),

            mavenBundle("biz.aQute.bnd", "bndlib").version(asInProject()),
            mavenBundle("commons-beanutils", "commons-beanutils").version(asInProject()),
            mavenBundle("commons-codec", "commons-codec").version(asInProject()),
            mavenBundle("commons-collections", "commons-collections").version(asInProject()),
            mavenBundle("org.apache.aries", "org.apache.aries.util").version(asInProject()),
            mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.api").version(asInProject()),
            mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.core").version(asInProject()),
            mavenBundle("org.apache.aries.proxy", "org.apache.aries.proxy").version(asInProject()),
            mavenBundle("org.apache.commons", "commons-lang3").version(asInProject()),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.ehcache").version(asInProject()),
            mavenBundle("org.apache.shiro", "shiro-core").version(asInProject()),
            mavenBundle("org.apache.shiro", "shiro-ehcache").version(asInProject()),
            mavenBundle("org.apache.shiro", "shiro-web").version(asInProject()),
            mavenBundle("org.ops4j.pax.logging", "pax-logging-service").version(asInProject()),
            mavenBundle("org.ops4j.pax.swissbox", "pax-swissbox-bnd").version(asInProject()),
            mavenBundle("org.ops4j.pax.swissbox", "pax-swissbox-property").version(asInProject()),
            mavenBundle("org.ops4j.pax.url", "pax-url-aether").version(asInProject()),
            mavenBundle("org.ops4j.pax.url", "pax-url-commons").version(asInProject()),
            mavenBundle("org.ops4j.pax.url", "pax-url-wrap").version(asInProject()),
            mavenBundle("org.ops4j.pax.web", "pax-web-jetty-bundle").version(asInProject()),
            mavenBundle("org.ops4j.pax.web", "pax-web-extender-whiteboard").version(asInProject()),
            mavenBundle("org.panifex", "panifex-module-api").version(asInProject()),
            mavenBundle("org.panifex", "panifex-service-api").version(asInProject()),
            mavenBundle("org.panifex", "panifex-test-support").version(asInProject()),
            mavenBundle("org.panifex", "panifex-web-shiro").version(asInProject()),

            wrappedBundle(mavenBundle("org.apache.httpcomponents", "httpcore").version(asInProject())),
            wrappedBundle(mavenBundle("org.apache.httpcomponents", "httpmime").version(asInProject())),
            wrappedBundle(mavenBundle("org.apache.httpcomponents", "httpclient").version(asInProject())));
    }

    @Before
    public void setUp() {
        waitForWebListener();
    }

    @Test
    public void unfilteredGetFromServletTest() throws Exception {
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet("http://localhost:8181/");
        HttpResponse response = httpclient.execute(httpget);

        assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }

    @Test
    public void filteredGetFromServletTest() throws Exception {
        // register filter path
        DefaultFilterPath filterPath = new DefaultFilterPath("/zk/*", "authc");
        ServiceRegistration<FilterPath> filterPathRegistration =
                bundleContext.registerService(FilterPath.class, filterPath, null);

        // register login servlet
        String servletName = "loginServlet";
        Dictionary<String, String> servletProps = new Hashtable<>();
        servletProps.put(WebContainerConstants.SERVLET_NAME, servletName);
        servletProps.put(ExtenderConstants.PROPERTY_URL_PATTERNS, "/login.jsp");
        OkServlet servlet = new OkServlet();
        ServiceRegistration<Servlet> servletRegistration =
                bundleContext.registerService(Servlet.class, servlet, servletProps);

        initServletListener(servletName);
        waitForServletListener();

        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet("http://localhost:8181/zk/");
        HttpResponse response = httpclient.execute(httpget);

        assertEquals(HttpServletResponse.SC_OK, response.getStatusLine().getStatusCode());

        filterPathRegistration.unregister();
        servletRegistration.unregister();
    }
}
