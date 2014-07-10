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
package org.panifex.security.persistence.itest;

import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.MavenUtils.asInProject;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.panifex.module.api.accounts.SecurityService;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public final class SecContainerTest {

    @Inject
    private SecurityService securityService;

    @Configuration
    public Option[] config() {
        return CoreOptions.options(
            mavenBundle("asm", "asm-all").version(asInProject()),
            mavenBundle("commons-collections", "commons-collections").version(asInProject()),
            mavenBundle("commons-lang", "commons-lang").version(asInProject()),
            mavenBundle("org.apache.aries", "org.apache.aries.util").version(asInProject()),
            mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.api").version(asInProject()),
            mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.core").version(asInProject()),
            mavenBundle("org.apache.aries.jndi", "org.apache.aries.jndi").version(asInProject()),
            mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.api").version(asInProject()),
            mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.blueprint.aries").version(asInProject()),
            mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.container").version(asInProject()),
            mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.container.context").version(asInProject()),
            mavenBundle("org.apache.aries.proxy", "org.apache.aries.proxy").version(asInProject()),
            mavenBundle("org.apache.aries.transaction", "org.apache.aries.transaction.blueprint").version(asInProject()),
            mavenBundle("org.apache.aries.transaction", "org.apache.aries.transaction.manager").version(asInProject()),
            mavenBundle("org.apache.aries.transaction", "org.apache.aries.transaction.wrappers").version(asInProject()),
            mavenBundle("org.apache.commons", "commons-lang3").version(asInProject()),
            mavenBundle("org.apache.derby", "derby").version(asInProject()),
            mavenBundle("org.apache.geronimo.specs", "geronimo-jpa_2.0_spec").version(asInProject()),
            mavenBundle("org.apache.geronimo.specs", "geronimo-jta_1.1_spec").version(asInProject()),
            mavenBundle("org.apache.geronimo.specs", "geronimo-servlet_3.0_spec").version(asInProject()),
            mavenBundle("org.apache.openjpa", "openjpa").version(asInProject()),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.commons-dbcp").version(asInProject()),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.commons-pool").version(asInProject()),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.ehcache").version(asInProject()),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.serp").version(asInProject()),
            mavenBundle("org.apache.shiro", "shiro-core").version(asInProject()),
            mavenBundle("org.apache.shiro", "shiro-ehcache").version(asInProject()),
            mavenBundle("org.liquibase", "liquibase-osgi").version(asInProject()),
            mavenBundle("org.osgi", "org.osgi.compendium").version(asInProject()),
            mavenBundle("org.panifex", "panifex-datasource-derby").version(asInProject()),
            mavenBundle("org.panifex", "panifex-module-api").version(asInProject()),
            mavenBundle("org.panifex", "panifex-persistence-spi").version(asInProject()),
            mavenBundle("org.panifex", "panifex-security-persistence").version(asInProject()),
            junitBundles());

    }

    @Test
    public void getSecurityServiceTest() {
        assertNotNull(securityService);
    }
}
