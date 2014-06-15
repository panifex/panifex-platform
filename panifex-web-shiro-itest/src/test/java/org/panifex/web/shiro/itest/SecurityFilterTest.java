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
import static org.ops4j.pax.exam.MavenUtils.asInProject;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.inject.Inject;
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
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.ops4j.pax.web.extender.whiteboard.ExtenderConstants;
import org.ops4j.pax.web.service.WebContainerConstants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.panifex.module.api.security.DefaultFilterPath;
import org.panifex.module.api.security.FilterPath;
import org.panifex.module.web.api.WebApplicationConstants;
import org.panifex.test.support.ITestSupport;

@ExamReactorStrategy(PerMethod.class)
@RunWith(PaxExam.class)
public final class SecurityFilterTest extends ITestSupport {

    @Inject
    private ConfigurationAdmin configurationAdmin;

    private HttpClient httpclient = HttpClientBuilder.create().build();

    @Configuration
    public Option[] config() {
        return OptionUtils.combine(
            webConfigure(),

            mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.cm").version(asInProject()),
            mavenBundle("org.apache.felix", "org.apache.felix.configadmin").version(asInProject()),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.ehcache").version(asInProject()),
            mavenBundle("org.apache.shiro", "shiro-core").version(asInProject()),
            mavenBundle("org.apache.shiro", "shiro-ehcache").version(asInProject()),
            mavenBundle("org.apache.shiro", "shiro-web").version(asInProject()),
            mavenBundle("org.panifex", "panifex-module-api").version(asInProject()),
            mavenBundle("org.panifex", "panifex-module-web-api").version(asInProject()),
            mavenBundle("org.panifex", "panifex-service-api").version(asInProject()),
            mavenBundle("org.panifex", "panifex-test-support").version(asInProject()),
            mavenBundle("org.panifex", "panifex-web-shiro").version(asInProject()));
    }

    @Before
    public void setUp() throws Exception {
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
        ServiceRegistration<FilterPath> filterPathRegistration =
                registerFilterPath("/zk/*", "authc");

        // register login servlet
        ServiceRegistration<Servlet> servletRegistration =
                registerServlet("loginServlet", WebApplicationConstants.DEFAULT_LOGIN_URL, new OkServlet());

        HttpGet httpget = new HttpGet("http://localhost:8181/zk/");
        HttpResponse response = httpclient.execute(httpget);

        assertEquals(HttpServletResponse.SC_OK, response.getStatusLine().getStatusCode());

        filterPathRegistration.unregister();
        servletRegistration.unregister();
    }

    @Test
    public void testSetValidLoginUrl() throws Exception {
        String newLoginUrl = "/new";
        registerNewLoginUrl(newLoginUrl);

        // register filter path
        ServiceRegistration<FilterPath> filterPathRegistration =
                registerFilterPath("/zk/*", "authc");

        // register login servlet
        ServiceRegistration<Servlet> servletRegistration =
                registerServlet("loginServlet", newLoginUrl, new OkServlet());

        HttpGet httpget = new HttpGet("http://localhost:8181/zk/");
        HttpResponse response = httpclient.execute(httpget);

        assertEquals(HttpServletResponse.SC_OK, response.getStatusLine().getStatusCode());

        resetLoginUrl();
        servletRegistration.unregister();
        filterPathRegistration.unregister();
    }

    @Test
    public void testRedirectToDefaultWelcomeUrl() throws Exception {
        // register welcome servlet
        ServiceRegistration<Servlet> servletRegistration =
                registerServlet("welcomeServlet",
                    WebApplicationConstants.DEFAULT_WELCOME_URL,
                    new OkServlet());

        HttpGet httpget = new HttpGet("http://localhost:8181/");
        HttpResponse response = httpclient.execute(httpget);

        assertEquals(HttpServletResponse.SC_OK, response.getStatusLine().getStatusCode());

        resetWelcomeUrl();
        servletRegistration.unregister();
    }

    @Test
    public void testSetValidWelcomeUrl() throws Exception {
        String newWelcomeUrl = "/welcome";
        registerNewWelcomeUrl(newWelcomeUrl);

        // register welcome servlet
        ServiceRegistration<Servlet> servletRegistration =
                registerServlet("welcomeServlet", newWelcomeUrl, new OkServlet());

        HttpGet httpget = new HttpGet("http://localhost:8181/");
        HttpResponse response = httpclient.execute(httpget);

        assertEquals(HttpServletResponse.SC_OK, response.getStatusLine().getStatusCode());

        resetWelcomeUrl();
        servletRegistration.unregister();
    }

    private void resetLoginUrl() throws Exception {
        registerNewLoginUrl(WebApplicationConstants.DEFAULT_LOGIN_URL);
    }

    private void registerNewLoginUrl(String loginUrl) throws Exception {
        setWebApplicationProperty(WebApplicationConstants.PROPERTY_LOGIN_URL, loginUrl);
    }

    private void resetWelcomeUrl() throws Exception {
        registerNewWelcomeUrl(WebApplicationConstants.DEFAULT_WELCOME_URL);
    }

    private void registerNewWelcomeUrl(String welcomeUrl) throws Exception {
        setWebApplicationProperty(WebApplicationConstants.PROPERTY_WELCOME_URL, welcomeUrl);
    }

    private void setWebApplicationProperty(String propertyKey, Object propertyValue) throws Exception {
        org.osgi.service.cm.Configuration conf =
                configurationAdmin.getConfiguration(WebApplicationConstants.PID, "?");

        Dictionary<String, Object> dict = conf.getProperties();
        if (dict == null) {
            dict = new Hashtable<>();
        }

        dict.put(propertyKey, propertyValue);
        conf.update(dict);
    }

    private ServiceRegistration<FilterPath> registerFilterPath(String path, String filterName) {
        DefaultFilterPath filterPath = new DefaultFilterPath(path, filterName);
        return registerService(FilterPath.class, filterPath);
    }

    private ServiceRegistration<Servlet> registerServlet(
            String servletName,
            String path,
            Servlet servlet) {
        Dictionary<String, String> servletProps = new Hashtable<>();
        servletProps.put(WebContainerConstants.SERVLET_NAME, servletName);
        servletProps.put(ExtenderConstants.PROPERTY_URL_PATTERNS, path);

        // register servlet
        ServiceRegistration<Servlet> servletRegistration =
                registerService(Servlet.class, servlet, servletProps);

        // wait servlet to be deployed
        initServletListener(servletName);
        waitForServletListener();

        return servletRegistration;
    }
}
