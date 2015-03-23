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
package org.panifex.itest.web.zk.security;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.exam.junit.PaxExam;
import org.panifex.itest.web.base.security.LoginPageletSecUtilTest;
import org.panifex.itest.web.zk.support.ZkPageletTestHelper;

@RunWith(PaxExam.class)
public class LoginZkPageletSecUtilTest extends LoginPageletSecUtilTest {

    @Configuration
    public Option[] config() {
        return OptionUtils.combine(
                webConfigure(),

                mavenBundle("net.sf.jasperreports", "jasperreports").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-module-zk-api").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-zk-layout").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-zk-runtime").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-web-zk-security").versionAsInProject());
    }

    public LoginZkPageletSecUtilTest() {
        super(new ZkPageletTestHelper());
    }
}
