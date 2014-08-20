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
package org.panifex.web.vaadin.runtime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.MavenUtils.asInProject;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.exam.junit.PaxExam;
import org.osgi.framework.ServiceRegistration;
import org.panifex.module.api.pagelet.PageletMapping;
import org.panifex.module.vaadin.api.DefaultVaadinPageletMapping;
import org.panifex.module.vaadin.api.VaadinPagelet;
import org.panifex.test.support.IWebTestSupport;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@RunWith(PaxExam.class)
public class VaadinServletTest extends IWebTestSupport {

    @Configuration
    public Option[] config() {
        return OptionUtils.combine(
                webConfigure(),

                mavenBundle("com.vaadin", "vaadin-server").version(asInProject()),
                mavenBundle("com.vaadin", "vaadin-shared").version(asInProject()),
                mavenBundle("com.vaadin.external.flute", "flute").version(asInProject()),
                mavenBundle("com.vaadin.external.google", "android-json").version(asInProject()),
                mavenBundle("com.vaadin.external.google", "guava").version(asInProject()),
                mavenBundle("com.vaadin.external.streamhtmlparser", "streamhtmlparser-jsilver").version(asInProject()),
                mavenBundle("org.panifex", "panifex-module-api").version(asInProject()),
                mavenBundle("org.panifex", "panifex-module-vaadin-api").version(asInProject()),
                mavenBundle("org.panifex", "panifex-web-spi").version(asInProject()),
                mavenBundle("org.panifex", "panifex-web-vaadin-runtime").version(asInProject()));
    }

    @Test
    public void testInitVaadinServlet() throws Exception {
        ServiceRegistration<VaadinPagelet> pageletRegistration = null;
        ServiceRegistration<PageletMapping> mappingRegistration = null;
        try {
            // register vaadin pagelet
            VaadinPagelet pagelet = new HelloVaadinPagelet();
            pageletRegistration = registerService(VaadinPagelet.class, pagelet);

            // register pagelet mapping
            String[] urlPatterns = new String[] { "/*" };
            PageletMapping mapping = new DefaultVaadinPageletMapping(pagelet, urlPatterns);
            mappingRegistration = registerService(PageletMapping.class, mapping);

            testGet(URL + "/", HttpServletResponse.SC_OK);
            WebClient webClient = new WebClient();
            HtmlPage page = webClient.getPage(URL + "/");
            webClient.waitForBackgroundJavaScript(10000L);
            assertEquals(HelloVaadinPagelet.CONTENT, page.asText());
        } finally {
            if (mappingRegistration != null) {
                mappingRegistration.unregister();
            }
            if (pageletRegistration != null) {
                pageletRegistration.unregister();
            }
        }
    }

    @Test
    public void testGetNotMappedUrl() throws Exception {
        WebClient webClient = new WebClient();
        HtmlPage page = webClient.getPage(URL + "/login");
        webClient.waitForBackgroundJavaScript(10000L);

        // response should be HTTP 404 - Not found
        assertTrue(page.asText().contains("HTTP ERROR 404"));
    }
}
