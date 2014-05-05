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

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.osgi.framework.InvalidSyntaxException;
import org.panifex.service.api.security.SecurityService;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public final class SecContainerTest {

    @Inject
    private SecurityService securityService;
    
    @Configuration
    public Option[] config() {
        return CoreOptions.options(
            mavenBundle("asm", "asm-all", "3.2"),
            mavenBundle("commons-collections", "commons-collections", "3.2.1"),
            mavenBundle("commons-lang", "commons-lang", "2.6"),
            mavenBundle("org.apache.aries", "org.apache.aries.util", "1.1.0"),
            mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.api", "1.0.0"),
            mavenBundle("org.apache.aries.blueprint", "org.apache.aries.blueprint.core", "1.3.0"),
            mavenBundle("org.apache.aries.jndi", "org.apache.aries.jndi", "1.0.0"),
            mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.api", "1.0.0"),
            mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.blueprint.aries", "1.0.1"),
            mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.container", "1.0.0"),
            mavenBundle("org.apache.aries.jpa", "org.apache.aries.jpa.container.context", "1.0.1"),
            mavenBundle("org.apache.aries.proxy", "org.apache.aries.proxy", "1.0.1"),
            mavenBundle("org.apache.aries.transaction", "org.apache.aries.transaction.blueprint", "1.0.0"),
            mavenBundle("org.apache.aries.transaction", "org.apache.aries.transaction.manager", "1.0.0"),
            mavenBundle("org.apache.aries.transaction", "org.apache.aries.transaction.wrappers", "1.0.0"),
            mavenBundle("org.apache.commons", "commons-lang3", "3.1"),
            mavenBundle("org.apache.derby", "derby", "10.10.1.1"),
            mavenBundle("org.apache.geronimo.specs", "geronimo-jpa_2.0_spec", "1.1"),
            mavenBundle("org.apache.geronimo.specs", "geronimo-jta_1.1_spec", "1.1.1"),
            mavenBundle("org.apache.geronimo.specs", "geronimo-servlet_3.0_spec", "1.0"),
            mavenBundle("org.apache.openjpa", "openjpa", "2.2.2"),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.commons-dbcp", "1.4_3"),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.commons-pool", "1.5.4_4"),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.ehcache", "2.6.6_1"),
            mavenBundle("org.apache.servicemix.bundles", "org.apache.servicemix.bundles.serp"),
            mavenBundle("org.apache.shiro", "shiro-core"),
            mavenBundle("org.apache.shiro", "shiro-ehcache"),
            mavenBundle("org.liquibase", "liquibase-osgi", "3.0.7"),
            mavenBundle("org.osgi", "org.osgi.compendium", "4.3.1"),
            mavenBundle("org.panifex", "panifex-datasource-derby"),
            mavenBundle("org.panifex", "panifex-persistence-spi"),
            mavenBundle("org.panifex", "panifex-security-persistence"),
            mavenBundle("org.panifex", "panifex-service-api"),
            
            junitBundles());

    }
    
    @Test
    public void getSecurityServiceTest() throws InvalidSyntaxException {
        assertNotNull(securityService);
    }
}
