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

import static org.ops4j.pax.exam.CoreOptions.cleanCaches;
import static org.ops4j.pax.exam.CoreOptions.frameworkProperty;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;
import static org.ops4j.pax.exam.CoreOptions.workingDirectory;
import static org.ops4j.pax.exam.CoreOptions.wrappedBundle;
import static org.ops4j.pax.exam.MavenUtils.asInProject;

import javax.inject.Inject;

import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.ops4j.pax.web.service.spi.ServletListener;
import org.ops4j.pax.web.service.spi.WebListener;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

@ExamReactorStrategy(PerMethod.class)
public abstract class ITestSupport {

    @Inject
    protected BundleContext bundleContext;

    // listeners
    protected ServletListener servletListener;
    protected WebListener webListener;

    protected Option[] baseConfigure() {
        return CoreOptions.options(
            workingDirectory("target/paxexam/"),
            cleanCaches(true),
            junitBundles(),

            systemProperty("java.protocol.handler.pkgs").value("org.ops4j.pax.url"),
            systemProperty("org.osgi.service.http.hostname").value("127.0.0.1"),
            systemProperty("org.osgi.service.http.port").value("8181"),

            frameworkProperty("osgi.console").value("6666"),
            frameworkProperty("osgi.console.enable.builtin").value("true"),

            mavenBundle("biz.aQute.bnd", "bndlib").version(asInProject()),
            mavenBundle("commons-beanutils", "commons-beanutils").version(asInProject()),
            mavenBundle("commons-collections", "commons-collections").version(asInProject()),
            mavenBundle("commons-fileupload", "commons-fileupload").version(asInProject()),
            mavenBundle("commons-io", "commons-io").version(asInProject()),
            mavenBundle("org.apache.aries", "org.apache.aries.util").version(asInProject()),
            mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.api").version(asInProject()),
            mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.core").version(asInProject()),
            mavenBundle("org.apache.aries.proxy", "org.apache.aries.proxy").version(asInProject()),
            mavenBundle("org.apache.commons", "commons-lang3").version(asInProject()),
            mavenBundle("org.apache.shiro", "shiro-core").version(asInProject()),

            wrappedBundle(mavenBundle("org.apache.httpcomponents", "httpcore").version(asInProject())),
            wrappedBundle(mavenBundle("org.apache.httpcomponents", "httpmime").version(asInProject())),
            wrappedBundle(mavenBundle("org.apache.httpcomponents", "httpclient").version(asInProject())));
    }

    protected Option[] webConfigure() {
        return OptionUtils.combine(
            baseConfigure(),

            mavenBundle("org.ops4j.pax.logging", "pax-logging-service").version(asInProject()),
            mavenBundle("org.ops4j.pax.swissbox", "pax-swissbox-bnd").version(asInProject()),
            mavenBundle("org.ops4j.pax.swissbox", "pax-swissbox-property").version(asInProject()),
            mavenBundle("org.ops4j.pax.url", "pax-url-aether").version(asInProject()),
            mavenBundle("org.ops4j.pax.url", "pax-url-commons").version(asInProject()),
            mavenBundle("org.ops4j.pax.url", "pax-url-wrap").version(asInProject()),
            mavenBundle("org.ops4j.pax.web", "pax-web-jetty-bundle").version(asInProject()),
            mavenBundle("org.ops4j.pax.web", "pax-web-extender-whiteboard").version(asInProject()));
    }

    protected void initServletListener() {
        initServletListener(null);
    }

    protected void initServletListener(String servletName) {
        if (servletName == null)
            servletListener = new ServletListenerImpl();
        else
            servletListener = new ServletListenerImpl(servletName);
        bundleContext.registerService(ServletListener.class, servletListener,
                null);
    }

    protected void waitForServletListener() {
        new WaitCondition("servlet startup") {
            @Override
            protected boolean isFulfilled() {
                return ((ServletListenerImpl) servletListener).gotEvent();
            }
        }.waitForCondition();
    }

    protected void initWebListener() {
        webListener = new WebListenerImpl();
        bundleContext.registerService(WebListener.class, webListener, null);
    }

    protected void waitForWebListener() {
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

    protected Bundle installAndStartBundle(String bundlePath)
            throws BundleException {
        final Bundle bundle = bundleContext.installBundle(bundlePath);
        bundle.start();
        new WaitCondition("bundle startup") {
            @Override
            protected boolean isFulfilled() {
                return bundle.getState() == Bundle.ACTIVE;
            }
        }.waitForCondition();
        return bundle;
    }
}
