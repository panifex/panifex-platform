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
package org.panifex.itest.security.persistence.container;

import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.ops4j.pax.exam.junit.PaxExam;
import org.panifex.module.api.security.AuthenticationService;
import org.panifex.test.support.ITestSupport;

@RunWith(PaxExam.class)
public final class SecContainerTest extends ITestSupport {

    @Inject
    private AuthenticationService authenticationService;

    @Configuration
    public Option[] config() {
        return OptionUtils.combine(
                baseConfigure(),

                mavenBundle("org.ow2.asm", "asm-all").versionAsInProject(),
                mavenBundle("commons-lang", "commons-lang").versionAsInProject(),
                mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.api").versionAsInProject(),
                mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.blueprint.aries").versionAsInProject(),
                mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.container").versionAsInProject(),
                mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.container.context").versionAsInProject(),
                mavenBundle("org.apache.aries.transaction", "org.apache.aries.transaction.blueprint").versionAsInProject(),
                mavenBundle("org.apache.aries.transaction", "org.apache.aries.transaction.manager").versionAsInProject(),
                mavenBundle("org.apache.aries.transaction", "org.apache.aries.transaction.wrappers").versionAsInProject(),
                mavenBundle("org.apache.derby", "derby").versionAsInProject(),
                mavenBundle("org.apache.geronimo.specs", "geronimo-jpa_2.0_spec").versionAsInProject(),
                mavenBundle("org.apache.geronimo.specs", "geronimo-jta_1.1_spec").versionAsInProject(),
                mavenBundle("org.apache.geronimo.specs", "geronimo-servlet_3.0_spec").versionAsInProject(),
                mavenBundle("org.apache.openjpa", "openjpa").versionAsInProject(),
                mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.commons-dbcp").versionAsInProject(),
                mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.commons-pool").versionAsInProject(),
                mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.ehcache").versionAsInProject(),
                mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.serp").versionAsInProject(),
                mavenBundle("org.apache.shiro", "shiro-ehcache").versionAsInProject(),
                mavenBundle("org.liquibase", "liquibase-osgi").versionAsInProject(),
                mavenBundle("org.osgi", "org.osgi.compendium").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-datasource-derby").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-module-api").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-persistence-spi").versionAsInProject(),
                mavenBundle("org.panifex", "panifex-security-persistence").versionAsInProject());
    }

    @Test
    public void testAuthenticationServiceRegistered() {
        assertNotNull(authenticationService);
    }
}
