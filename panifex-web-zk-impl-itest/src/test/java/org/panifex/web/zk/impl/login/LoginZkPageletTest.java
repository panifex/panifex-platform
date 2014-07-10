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
package org.panifex.web.zk.impl.login;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.MavenUtils.asInProject;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.exam.junit.PaxExam;
import org.panifex.test.support.ITestSupport;

@RunWith(PaxExam.class)
public class LoginZkPageletTest extends ITestSupport {

    @Configuration
    public Option[] config() {
        return OptionUtils.combine(
                webConfigure(),

                mavenBundle("net.sf.jasperreports", "jasperreports").version(asInProject()),

                mavenBundle("org.panifex", "panifex-module-api").version(asInProject()),
                mavenBundle("org.panifex", "panifex-module-zk-api").version(asInProject()),
                mavenBundle("org.panifex", "panifex-test-support").version(asInProject()),
                mavenBundle("org.panifex", "panifex-web-controller").version(asInProject()),
                mavenBundle("org.panifex", "panifex-web-zk").version(asInProject()),
                mavenBundle("org.panifex", "panifex-web-zk-impl").version(asInProject()));
    }

    @Before
    public void setUp() {
        waitForWebListener();
    }

    @Test
    public void httpGetFromServletTest() throws Exception {
        testGet(URL + "/zk/login", HttpServletResponse.SC_OK);
    }
}
