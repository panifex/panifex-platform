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
package org.panifex.web.zk.itest;

import static org.junit.Assert.assertEquals;
import static org.ops4j.pax.exam.CoreOptions.cleanCaches;
import static org.ops4j.pax.exam.CoreOptions.frameworkProperty;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;
import static org.ops4j.pax.exam.CoreOptions.workingDirectory;
import static org.ops4j.pax.exam.CoreOptions.wrappedBundle;
import static org.ops4j.pax.exam.MavenUtils.asInProject;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.ops4j.pax.web.service.spi.WebListener;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.panifex.module.api.DefaultPageletMapping;
import org.panifex.module.api.PageletMapping;
import org.panifex.module.zk.api.ZkPagelet;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class ServletTest {

    @Inject
    private BundleContext bundleContext;

    protected WebListener webListener;

    @Configuration
    public Option[] config() {
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
                mavenBundle("net.sf.jasperreports", "jasperreports").version(asInProject()),
                mavenBundle("org.apache.aries", "org.apache.aries.util").version(asInProject()),
                mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.api").version(asInProject()),
                mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.core").version(asInProject()),
                mavenBundle("org.apache.aries.proxy", "org.apache.aries.proxy").version(asInProject()),
                mavenBundle("org.apache.commons", "commons-lang3").version(asInProject()),
                mavenBundle("org.apache.shiro", "shiro-core").version(asInProject()),
                mavenBundle("org.ops4j.pax.logging", "pax-logging-service").version(asInProject()),
                mavenBundle("org.ops4j.pax.swissbox", "pax-swissbox-bnd").version(asInProject()),
                mavenBundle("org.ops4j.pax.swissbox", "pax-swissbox-property").version(asInProject()),
                mavenBundle("org.ops4j.pax.url", "pax-url-aether").version(asInProject()),
                mavenBundle("org.ops4j.pax.url", "pax-url-commons").version(asInProject()),
                mavenBundle("org.ops4j.pax.url", "pax-url-wrap").version(asInProject()),
                mavenBundle("org.ops4j.pax.web", "pax-web-jetty-bundle").version(asInProject()),
                mavenBundle("org.ops4j.pax.web", "pax-web-extender-whiteboard").version(asInProject()),
                mavenBundle("org.panifex", "panifex-module-api").version(asInProject()),
                mavenBundle("org.panifex", "panifex-module-zk-api").version(asInProject()),
                mavenBundle("org.panifex", "panifex-service-api").version(asInProject()),
                mavenBundle("org.panifex", "panifex-web-controller").version(asInProject()),
                mavenBundle("org.panifex", "panifex-web-zk").version(asInProject()),

                wrappedBundle(mavenBundle("org.apache.httpcomponents", "httpcore").version(asInProject())),
                wrappedBundle(mavenBundle("org.apache.httpcomponents", "httpmime").version(asInProject())),
                wrappedBundle(mavenBundle("org.apache.httpcomponents", "httpclient").version(asInProject())));
    }

    @Before
    public void setUp() {
        initWebListener();
        waitForWebListener();
    }

    @Test
    public void httpGetFromServletTest() throws Exception {
        // register zk pagelet
        Dictionary<String, String> pageletProps = new Hashtable<>();
        ZkPagelet helloPagelet = new HelloZkPagelet();
        ServiceRegistration<ZkPagelet> pagelet = bundleContext.
                registerService(ZkPagelet.class, helloPagelet, pageletProps);

        // register pagelet mapping
        Dictionary<String, String> mappingProps = new Hashtable<>();
        String[] urlPatterns = new String[]{ "/*" };
        PageletMapping helloMapping = new DefaultPageletMapping(helloPagelet, urlPatterns);
        ServiceRegistration<PageletMapping> mapping = bundleContext.
                registerService(PageletMapping.class, helloMapping, mappingProps);

        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet("http://localhost:8181/");
        HttpResponse response = httpclient.execute(httpget);

        assertEquals(HttpServletResponse.SC_OK, response.getStatusLine().getStatusCode());

        mapping.unregister();
        pagelet.unregister();
    }

    protected void initWebListener() {
        webListener = new WebListenerImpl();
        bundleContext.registerService(WebListener.class, webListener, null);
    }

    protected void waitForWebListener() {
        new WaitCondition("webapp startup") {
            @Override
            protected boolean isFulfilled() {
                return ((WebListenerImpl) webListener).gotEvent();
            }
        }.waitForCondition();
    }
}