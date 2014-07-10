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
package org.panifex.security.shiro;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.env.EnvironmentLoader;
import org.apache.shiro.web.env.WebEnvironment;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.api.WebApplicationConstants;
import org.panifex.security.shiro.SecurityFilterImpl;
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
    public void testInitSecurityFilter() throws Exception {
        // expect initializing environment
        expect(environmentLoaderMock.initEnvironment(servletContextMock)).
            andReturn(createMock(WebEnvironment.class));

        expectSettingDefaultLoginUrl();
        expectSettingDefaultPasswordParam();
        expectSettingDefaultRememberMeParam();
        expectSettingDefaultSuccessUrl();
        expectSettingDefaultUsernameParam();

        // perform test
        replayAll();
        filter.init();
        verifyAll();
    }

    @Test
    public void testSetUnknownLoginUrl() throws Exception {
        testSetLoginUrl(null);
    }

    @Test
    public void testSetEmptyLoginUrl() throws Exception {
        testSetLoginUrl(StringUtils.EMPTY);
    }

    @Test
    public void testSetValidLoginUrl() throws Exception {
        testSetLoginUrl("/validloginurl");
    }

    @Test
    public void testSetUnknownPasswordParam() throws Exception {
        testSetPasswordParam(null);
    }

    @Test
    public void testSetEmptyPasswordParam() throws Exception {
        testSetPasswordParam(StringUtils.EMPTY);
    }

    @Test
    public void testSetValidPasswordParam() throws Exception {
        testSetPasswordParam("passwordParam");
    }

    @Test
    public void testSetUnknownRememberMeParam() throws Exception {
        testSetRememberMeParam(null);
    }

    @Test
    public void testSetEmptyRememberMeParam() throws Exception {
        testSetRememberMeParam(StringUtils.EMPTY);
    }

    @Test
    public void testSetValidRememberMeParam() throws Exception {
        testSetRememberMeParam("rememberMeParam");
    }

    @Test
    public void testSetUnknownSuccessUrl() throws Exception {
        testSetSuccessUrl(null);
    }

    @Test
    public void testSetEmptySuccessUrl() throws Exception {
        testSetSuccessUrl(StringUtils.EMPTY);
    }

    @Test
    public void testSetValidSuccessUrl() throws Exception {
        testSetSuccessUrl("/validsuccessurl");
    }

    @Test
    public void testSetUnknownUsernameParam() throws Exception {
        testSetUsernameParam(null);
    }

    @Test
    public void testSetEmptyUsernameParam() throws Exception {
        testSetUsernameParam(StringUtils.EMPTY);
    }

    @Test
    public void testSetValidUsernameParam() throws Exception {
        testSetUsernameParam("usernameParam");
    }

    private void testSetLoginUrl(String loginUrl) {
        String expected = null;
        if (loginUrl == null || loginUrl.isEmpty()) {
            expectSettingDefaultLoginUrl();
            expected = WebApplicationConstants.DEFAULT_LOGIN_URL;
        } else {
            expectSettingLoginUrl(loginUrl);
            expected = loginUrl;
        }

        replayAll();
        filter.setLoginUrl(loginUrl);
        verifyAll();
        assertEquals(expected, filter.getLoginUrl());
    }

    private void expectSettingDefaultLoginUrl() {
        expectSettingLoginUrl(WebApplicationConstants.DEFAULT_LOGIN_URL);
    }

    private void expectSettingLoginUrl(String loginUrl) {
        AccessControlFilter filterMock = expectGettingFormAuthenticationFilter();

        // expect setting login url
        filterMock.setLoginUrl(loginUrl);
    }

    private void testSetPasswordParam(String passwordParam) {
        String expected = null;
        if (passwordParam == null || passwordParam.isEmpty()) {
            expectSettingDefaultPasswordParam();
            expected = WebApplicationConstants.DEFAULT_PASSWORD_PARAM;
        } else {
            expectSettingPasswordParam(passwordParam);
            expected = passwordParam;
        }

        replayAll();
        filter.setPasswordParam(passwordParam);
        verifyAll();
        assertEquals(expected, filter.getPasswordParam());
    }

    private void expectSettingDefaultPasswordParam() {
        expectSettingPasswordParam(WebApplicationConstants.DEFAULT_PASSWORD_PARAM);
    }

    private void expectSettingPasswordParam(String passwordParam) {
        FormAuthenticationFilter filterMock = expectGettingFormAuthenticationFilter();

        // expect setting password param
        filterMock.setPasswordParam(passwordParam);
    }

    private void testSetRememberMeParam(String rememberMeParam) {
        String expected = null;
        if (rememberMeParam == null || rememberMeParam.isEmpty()) {
            expectSettingDefaultRememberMeParam();
            expected = WebApplicationConstants.DEFAULT_REMEMBER_ME_PARAM;
        } else {
            expectSettingRememberMeParam(rememberMeParam);
            expected = rememberMeParam;
        }

        replayAll();
        filter.setRememberMeParam(rememberMeParam);
        verifyAll();
        assertEquals(expected, filter.getRememberMeParam());
    }

    private void expectSettingDefaultRememberMeParam() {
        expectSettingRememberMeParam(WebApplicationConstants.DEFAULT_REMEMBER_ME_PARAM);
    }

    private void expectSettingRememberMeParam(String rememberMeParam) {
        FormAuthenticationFilter filterMock = expectGettingFormAuthenticationFilter();

        // expect setting remember me param
        filterMock.setRememberMeParam(rememberMeParam);
    }

    private void testSetSuccessUrl(String successUrl) {
        String expected = null;
        if (successUrl == null || successUrl.isEmpty()) {
            expectSettingDefaultSuccessUrl();
            expected = WebApplicationConstants.DEFAULT_SUCCESS_URL;
        } else {
            expectSettingSuccessUrl(successUrl);
            expected = successUrl;
        }

        replayAll();
        filter.setSuccessUrl(successUrl);
        verifyAll();
        assertEquals(expected, filter.getSuccessUrl());
    }

    private void expectSettingDefaultSuccessUrl() {
        expectSettingSuccessUrl(WebApplicationConstants.DEFAULT_SUCCESS_URL);
    }

    private void expectSettingSuccessUrl(String successUrl) {
        AuthenticationFilter filterMock = expectGettingFormAuthenticationFilter();

        // expect setting success url
        filterMock.setSuccessUrl(successUrl);
    }

    private void testSetUsernameParam(String usernameParam) {
        String expected = null;
        if (usernameParam == null || usernameParam.isEmpty()) {
            expectSettingDefaultUsernameParam();
            expected = WebApplicationConstants.DEFAULT_USERNAME_PARAM;
        } else {
            expectSettingUsernameParam(usernameParam);
            expected = usernameParam;
        }

        replayAll();
        filter.setUsernameParam(usernameParam);
        verifyAll();
        assertEquals(expected, filter.getUsernameParam());
    }

    private void expectSettingDefaultUsernameParam() {
        expectSettingUsernameParam(WebApplicationConstants.DEFAULT_USERNAME_PARAM);
    }

    private void expectSettingUsernameParam(String usernameParam) {
        FormAuthenticationFilter filterMock = expectGettingFormAuthenticationFilter();

        // expect setting username param
        filterMock.setUsernameParam(usernameParam);
    }

    private void expectGettingFilterWithName(Filter filter, String filterName) {
        // expect getting filterChainManager
        FilterChainManager filterChainManagerMock = createMock(FilterChainManager.class);
        expect(filterChainResolverMock.getFilterChainManager()).andReturn(filterChainManagerMock);

        // expect getting form authentication filters
        Map<String, Filter> filters = new HashMap<>();
        filters.put(DefaultFilter.authc.toString(), filter);
        expect(filterChainManagerMock.getFilters()).andReturn(filters);
    }

    private FormAuthenticationFilter expectGettingFormAuthenticationFilter() {
        FormAuthenticationFilter filterMock = createMock(FormAuthenticationFilter.class);
        expectGettingFilterWithName(filterMock, DefaultFilter.authc.toString());
        return filterMock;
    }
}
