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
package org.panifex.web.shiro;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.env.EnvironmentLoader;
import org.apache.shiro.web.env.WebEnvironment;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.web.api.WebApplicationConstants;
import org.panifex.service.api.security.SecurityService;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Unit tests for {@link SecurityFilterImpl} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityFilterImpl.class)
public class SecurityFilterImplTest extends TestSupport {

    private SecurityFilterImpl filter;

    // mocks
    EnvironmentLoader environmentLoaderMock = createMock(EnvironmentLoader.class);
    PathMatchingFilterChainResolver filterChainResolverMock = createMock(PathMatchingFilterChainResolver.class);
    ServletContext servletContextMock = createMock(ServletContext.class);

    public SecurityFilterImplTest() {
        suppress(method(ShiroFilter.class, "init"));
    }

    @Before
    public void setUp() {
        resetAll();

        filter = new SecurityFilterImpl();
        filter.setEnvironmentLoader(environmentLoaderMock);
        filter.setFilterChainResolver(filterChainResolverMock);
        filter.setServletContext(servletContextMock);
    }

    @Test
    public void testBindSecurityService() {
        // create mocks
        SecurityService securityServiceMock = createMock(SecurityService.class);

        replay(securityServiceMock);

        // bind realm
        filter.bind(securityServiceMock);

        verify(securityServiceMock);
    }

    @Test
    public void testUnbindSecurityService() {
        // create mocks
        SecurityService securityServiceMock = createMock(SecurityService.class);

        replay(securityServiceMock);

        // unbind realm
        filter.unbind(securityServiceMock);

        verify(securityServiceMock);
    }

    @Test
    public void testInitSecurityFilter() throws Exception {
        // expect initializing environment
        expect(environmentLoaderMock.initEnvironment(servletContextMock)).
            andReturn(createMock(WebEnvironment.class));

        expectSettingDefaultLoginUrl();

        // perform test
        replayAll();
        filter.init();
        verifyAll();
    }

    @Test
    public void testSetUnknownLoginUrl() throws Exception {
        expectSettingDefaultLoginUrl();

        // perform test
        replayAll();
        filter.setLoginUrl(null);
        verifyAll();
    }

    @Test
    public void testSetEmptyLoginUrl() throws Exception {
        expectSettingDefaultLoginUrl();

        // perform test
        replayAll();
        filter.setLoginUrl(StringUtils.EMPTY);
        verifyAll();
    }

    @Test
    public void testSetValidLoginUrl() throws Exception {
        String validLoginUrl = "/validlogin";

        expectSettingLoginUrl(validLoginUrl);

        // perform test
        replayAll();
        filter.setLoginUrl(validLoginUrl);
        verifyAll();
    }

    private void expectSettingDefaultLoginUrl() {
        expectSettingLoginUrl(WebApplicationConstants.DEFAULT_LOGIN_URL);
    }

    private void expectSettingLoginUrl(String loginUrl) {
        // expect getting filterChainManager
        FilterChainManager filterChainManagerMock = createMock(FilterChainManager.class);
        expect(filterChainResolverMock.getFilterChainManager()).andReturn(filterChainManagerMock);

        // expect getting form authentication filters
        Map<String, Filter> filters = new HashMap<>();
        AccessControlFilter accessControlFilter = createMock(AccessControlFilter.class);
        filters.put(DefaultFilter.authc.toString(), accessControlFilter);
        expect(filterChainManagerMock.getFilters()).andReturn(filters);

        // expect setting login url
        accessControlFilter.setLoginUrl(loginUrl);
    }
}
