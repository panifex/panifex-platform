/*******************************************************************************
 * Panifex platform
 * Copyright (C) 2015  Mario Krizmanic
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
package org.panifex.itest.web.zk.layout;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.exam.junit.PaxExam;
import org.osgi.framework.ServiceRegistration;
import org.panifex.itest.web.zk.support.HelloLayoutZkPagelet;
import org.panifex.module.api.pagelet.PageletMapping;
import org.panifex.module.zk.api.DefaultZkPageletMapping;
import org.panifex.module.zk.api.ZkPagelet;
import org.panifex.test.support.IWebTestSupport;

@RunWith(PaxExam.class)
public class ZkPageletTest extends IWebTestSupport {

    @Configuration
    public Option[] config() {
        return OptionUtils.combine(
                webConfigure(),

                mavenBundle("net.sf.jasperreports", "jasperreports").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-module-api").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-module-zk-api").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-spi").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-zk-layout").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-zk-runtime").versionAsInProject());
    }

    @Test
    public void httpGetFromServletTest() throws Exception {
        ServiceRegistration<ZkPagelet> pageletRegistration = null;
        ServiceRegistration<PageletMapping> mappingRegistration = null;
        try {
            // register zk pagelet
            ZkPagelet pagelet = new HelloLayoutZkPagelet();
            pageletRegistration =
                    registerService(ZkPagelet.class, pagelet);

            // register pagelet mapping
            String[] urlPatterns = new String[]{ "/*" };
            PageletMapping mapping = new DefaultZkPageletMapping(pagelet, urlPatterns);
            mappingRegistration =
                    registerService(PageletMapping.class, mapping);

            testGet(URL + "/zk/", HttpServletResponse.SC_OK);
        } finally {
            if (mappingRegistration != null) {
                mappingRegistration.unregister();
            }
            if (pageletRegistration != null) {
                pageletRegistration.unregister();
            }
        }
    }
}
