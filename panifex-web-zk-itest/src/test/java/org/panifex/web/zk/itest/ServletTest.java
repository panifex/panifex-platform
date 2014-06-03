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
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.MavenUtils.asInProject;

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
import org.osgi.framework.ServiceRegistration;
import org.panifex.module.api.DefaultPageletMapping;
import org.panifex.module.api.PageletMapping;
import org.panifex.module.zk.api.ZkPagelet;
import org.panifex.test.support.ITestSupport;

@RunWith(PaxExam.class)
public class ServletTest extends ITestSupport {

    @Configuration
    public Option[] config() {
        return OptionUtils.combine(
                webConfigure(),

                mavenBundle("net.sf.jasperreports", "jasperreports").version(asInProject()),
                mavenBundle("org.apache.shiro", "shiro-core").version(asInProject()),
                mavenBundle("org.panifex", "panifex-module-api").version(asInProject()),
                mavenBundle("org.panifex", "panifex-module-zk-api").version(asInProject()),
                mavenBundle("org.panifex", "panifex-service-api").version(asInProject()),
                mavenBundle("org.panifex", "panifex-test-support").version(asInProject()),
                mavenBundle("org.panifex", "panifex-web-controller").version(asInProject()),
                mavenBundle("org.panifex", "panifex-web-zk").version(asInProject()));
    }

    @Before
    public void setUp() {
        waitForWebListener();
    }

    @Test
    public void httpGetFromServletTest() throws Exception {
        // register zk pagelet
        ZkPagelet pagelet = new HelloZkPagelet();
        ServiceRegistration<ZkPagelet> pageletRegistration =
                registerService(ZkPagelet.class, pagelet);

        // register pagelet mapping
        String[] urlPatterns = new String[]{ "/*" };
        PageletMapping mapping = new DefaultPageletMapping(pagelet, urlPatterns);
        ServiceRegistration<PageletMapping> mappingRegistration =
                registerService(PageletMapping.class, mapping);

        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpGet httpget = new HttpGet("http://localhost:8181/zk/");
        HttpResponse response = httpclient.execute(httpget);

        assertEquals(HttpServletResponse.SC_OK, response.getStatusLine().getStatusCode());

        mappingRegistration.unregister();
        pageletRegistration.unregister();
    }
}
