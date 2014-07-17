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
package org.panifex.security.shiro.itest;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.MavenUtils.asInProject;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.ops4j.pax.web.extender.whiteboard.ExtenderConstants;
import org.ops4j.pax.web.service.WebContainerConstants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.panifex.module.api.WebApplicationConstants;
import org.panifex.module.api.security.AuthenticationService;
import org.panifex.module.api.security.DefaultSecFilterMapping;
import org.panifex.module.api.security.SecFilterMapping;
import org.panifex.security.shiro.SecurityFilterImpl;
import org.panifex.test.support.IWebTestSupport;

/**
 * Integration tests for {@link SecurityFilterImpl} class.
 */
@ExamReactorStrategy(PerMethod.class)
@RunWith(PaxExam.class)
public final class SecurityFilterTest extends IWebTestSupport {

    @Inject
    private ConfigurationAdmin configurationAdmin;

    @org.ops4j.pax.exam.Configuration
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
            mavenBundle("org.panifex", "panifex-security-shiro").version(asInProject()),
            mavenBundle("org.panifex", "panifex-test-support").version(asInProject()));
    }

    @Before
    public void setUp() throws Exception {
        waitForWebListener();
        resetWebApplicationProperties();
    }

    @Test
    public void unfilteredGetFromServletTest() throws Exception {
        testGet(URL, HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    public void filteredGetFromServletTest() throws Exception {
        ServiceRegistration<SecFilterMapping> filterPathRegistration = null;
        ServiceRegistration<Servlet> servletRegistration = null;

        try {
            // register filter path
            filterPathRegistration =
                    registerFilterPath("/zk/*", DefaultFilter.authc);

            // register login servlet
            servletRegistration =
                    registerServlet("loginServlet",
                            WebApplicationConstants.DEFAULT_LOGIN_URL,
                            new OkServlet());

            testGet(URL + "/zk/", HttpServletResponse.SC_OK);
        } finally {
            if (filterPathRegistration != null) {
                filterPathRegistration.unregister();
            }
            if (servletRegistration != null) {
                servletRegistration.unregister();
            }
        }
    }

    @Test
    public void testSetValidLoginUrl() throws Exception {
        ServiceRegistration<SecFilterMapping> filterPathRegistration = null;
        ServiceRegistration<Servlet> servletRegistration = null;

        try {
            String newLoginUrl = "/new";
            registerNewLoginUrl(newLoginUrl);

            // register filter path
            filterPathRegistration =
                    registerFilterPath("/zk/*", DefaultFilter.authc);

            // register login servlet
            servletRegistration =
                    registerServlet("loginServlet", newLoginUrl, new OkServlet());

            testGet(URL + "/zk/", HttpServletResponse.SC_OK);
        } finally {
            if (servletRegistration != null) {
                servletRegistration.unregister();
            }
            if (filterPathRegistration != null) {
                filterPathRegistration.unregister();
            }
        }
    }

    @Test
    public void testRedirectToDefaultWelcomeUrl() throws Exception {
        ServiceRegistration<Servlet> servletRegistration = null;

        try {
            // register welcome servlet
            servletRegistration =
                    registerServlet("welcomeServlet",
                        WebApplicationConstants.DEFAULT_WELCOME_URL,
                        new OkServlet());

            testGet(URL, HttpServletResponse.SC_OK);
        } finally {
            if (servletRegistration != null) {
                servletRegistration.unregister();
            }
        }
    }

    @Test
    public void testSetValidWelcomeUrlAndRedirectToIt() throws Exception {
        ServiceRegistration<Servlet> servletRegistration = null;

        try {
            String newWelcomeUrl = "/welcome";
            registerNewWelcomeUrl(newWelcomeUrl);

            // register welcome servlet
            servletRegistration =
                    registerServlet("welcomeServlet", newWelcomeUrl, new OkServlet());

            testGet(URL, HttpServletResponse.SC_OK);
        } finally {
            if (servletRegistration != null) {
                servletRegistration.unregister();
            }
        }
    }

    @Test
    public void testLoginWithDefaultParams() throws Exception {
        testLoginWithSpecifiedParams(
                WebApplicationConstants.DEFAULT_USERNAME_PARAM,
                WebApplicationConstants.DEFAULT_PASSWORD_PARAM,
                WebApplicationConstants.DEFAULT_REMEMBER_ME_PARAM);
    }

    @Test
    public void testLoginWithCustomParams() throws Exception {
        testLoginWithSpecifiedParams(
                "customUsernameParam",
                "customPasswordParam",
                "customRememberMeParam");
    }

    private void testLoginWithSpecifiedParams(
            String usernameParam,
            String passwordParam,
            String rememberMeParam) throws Exception {
        String username = "username1";
        String password = "userPass1";

        ServiceRegistration<SecFilterMapping> filterPathRegistration = null;
        ServiceRegistration<AuthenticationService> authcServiceRegistration = null;

        try {
            // register properties
            setWebApplicationProperty(
                    WebApplicationConstants.PROPERTY_USERNAME_PARAM,
                    usernameParam,
                    WebApplicationConstants.DEFAULT_USERNAME_PARAM);

            setWebApplicationProperty(
                    WebApplicationConstants.PROPERTY_PASSWORD_PARAM,
                    passwordParam,
                    WebApplicationConstants.DEFAULT_PASSWORD_PARAM);

            setWebApplicationProperty(
                    WebApplicationConstants.PROPERTY_REMEMBER_ME_PARAM,
                    rememberMeParam,
                    WebApplicationConstants.DEFAULT_REMEMBER_ME_PARAM);

            // register filter path
            filterPathRegistration =
                    registerFilterPath("/*", DefaultFilter.authc);

            // register security service
            SimpleAuthenticationService simpleAuthcService = new SimpleAuthenticationService();
            simpleAuthcService.addAccount(username, password);

            authcServiceRegistration =
                    registerService(AuthenticationService.class, simpleAuthcService, null);

            Map<String, String> params = new HashMap<>();
            params.put(usernameParam, username);
            params.put(passwordParam, password);

            testPost(URL + WebApplicationConstants.DEFAULT_LOGIN_URL,
                    HttpServletResponse.SC_FOUND,
                    null,
                    params);
        } finally {
            if (filterPathRegistration != null) {
                filterPathRegistration.unregister();
            }
            if (authcServiceRegistration != null) {
                authcServiceRegistration.unregister();
            }
        }
    }

    private void registerNewLoginUrl(String loginUrl) throws Exception {
        setWebApplicationProperty(WebApplicationConstants.PROPERTY_LOGIN_URL, loginUrl);
    }

    private void registerNewWelcomeUrl(String welcomeUrl) throws Exception {
        setWebApplicationProperty(WebApplicationConstants.PROPERTY_WELCOME_URL, welcomeUrl);
    }

    private void resetWebApplicationProperties() throws Exception {
        getWebAppConfiguration().delete();
    }

    private void setWebApplicationProperty(
            String propertyKey,
            Object propertyValue,
            Object defaultValue) throws Exception {
        if (propertyValue != null) {
            if (!propertyValue.equals(defaultValue)) {
                setWebApplicationProperty(propertyKey, propertyValue);
            }
        }
    }

    private void setWebApplicationProperty(String propertyKey, Object propertyValue)
            throws Exception {
        Configuration conf = getWebAppConfiguration();

        Dictionary<String, Object> dict = conf.getProperties();
        if (dict == null) {
            dict = new Hashtable<>();
        }

        dict.put(propertyKey, propertyValue);

        conf.update(dict);
    }

    private Configuration getWebAppConfiguration() throws Exception {
        return configurationAdmin.getConfiguration(WebApplicationConstants.PID, "?");
    }

    private ServiceRegistration<SecFilterMapping> registerFilterPath(
            String path, DefaultFilter defaultFilter) {
        DefaultSecFilterMapping filterPath = new DefaultSecFilterMapping(path, defaultFilter);
        return registerService(SecFilterMapping.class, filterPath);
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
