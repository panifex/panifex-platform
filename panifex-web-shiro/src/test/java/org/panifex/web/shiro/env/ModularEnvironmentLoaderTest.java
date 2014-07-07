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
package org.panifex.web.shiro.env;

import javax.servlet.ServletContext;

import org.apache.shiro.util.LifecycleUtils;
import org.apache.shiro.web.env.EnvironmentLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Unit tests for {@link ModularEnvironmentLoader} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(LifecycleUtils.class)
public class ModularEnvironmentLoaderTest extends TestSupport {

    private ServletContext servletContextMock = createMock(ServletContext.class);

    public ModularEnvironmentLoaderTest() {
        mockStatic(LifecycleUtils.class);
    }

    @Before
    public void before() {
        resetAll();
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateEnvironmentWithoutPreparedModularWebEnvironment() {
        EnvironmentLoader loader = new ModularEnvironmentLoader();

        loader.initEnvironment(servletContextMock);
    }

    @Test
    public void testInitEnvironmentWithPreparedModularWebEnvironment() {
        // mocks
        ModularWebEnvironment webEnvironment = createMock(ModularWebEnvironment.class);

        // servlet context must not return an environment attribute because it contains
        // an already registered Shiro's environment
        expect(servletContextMock.getAttribute(EnvironmentLoader.ENVIRONMENT_ATTRIBUTE_KEY))
            .andReturn(null);

        servletContextMock.log(anyObject(String.class));

        // expect binding servlet context to modular web environment
        webEnvironment.setServletContext(servletContextMock);

        // expect lifecycle init
        LifecycleUtils.init(webEnvironment);

        // expect setting modular web environment to servlet context
        servletContextMock.setAttribute(EnvironmentLoader.ENVIRONMENT_ATTRIBUTE_KEY, webEnvironment);

        // prepare modular environment loader to test
        ModularEnvironmentLoader loader = new ModularEnvironmentLoader();
        loader.setEnvironment(webEnvironment);

        replayAll();
        loader.initEnvironment(servletContextMock);
        verifyAll();
    }
}
