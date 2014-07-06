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

import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.web.env.EnvironmentLoader;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.panifex.module.api.WebApplicationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityFilterImpl extends ShiroFilter implements SecurityFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private EnvironmentLoader loader;
    private String loginUrl = WebApplicationConstants.DEFAULT_LOGIN_URL;
    private String passwordParam = WebApplicationConstants.DEFAULT_PASSWORD_PARAM;
    private String rememberMeParam = WebApplicationConstants.DEFAULT_REMEMBER_ME_PARAM;
    private String successUrl = WebApplicationConstants.DEFAULT_SUCCESS_URL;
    private String usernameParam = WebApplicationConstants.DEFAULT_USERNAME_PARAM;

    @Override
    public void init() throws Exception {
        log.info("Initialize security filter");
        loader.initEnvironment(getServletContext());

        super.init();

        bindLoginUrlToAccessControlFilter();
        bindPasswordParamToFormAuthenticationFilter();
        bindRememberMeParamToFormAuthenticationFilter();
        bindSuccessUrlToAuthenticationFilter();
        bindUsernameParamToFormAuthenticationFilter();
    }

    @Override
    public void destroy() {
        loader.destroyEnvironment(getServletContext());
        super.destroy();
    }

    public void setEnvironmentLoader(EnvironmentLoader loader) {
        this.loader = loader;
    }

    @Override
    public String getLoginUrl() {
        return loginUrl;
    }

    @Override
    public void setLoginUrl(String loginUrl) {
        log.debug("Set new login url {}", loginUrl);
        if (loginUrl == null || loginUrl.isEmpty()) {
            loginUrl = WebApplicationConstants.DEFAULT_LOGIN_URL;
        }
        this.loginUrl = loginUrl;
        bindLoginUrlToAccessControlFilter();
    }

    @Override
    public String getPasswordParam() {
        return passwordParam;
    }

    @Override
    public void setPasswordParam(String passwordParam) {
        log.debug("Set new password param {}", passwordParam);
        if (passwordParam == null || passwordParam.isEmpty()) {
            passwordParam = WebApplicationConstants.DEFAULT_PASSWORD_PARAM;
        }
        this.passwordParam = passwordParam;
        bindPasswordParamToFormAuthenticationFilter();
    }

    @Override
    public String getRememberMeParam() {
        return rememberMeParam;
    }

    @Override
    public void setRememberMeParam(String rememberMeParam) {
        log.debug("Set new remember me param {}", rememberMeParam);
        if (rememberMeParam == null || rememberMeParam.isEmpty()) {
            rememberMeParam = WebApplicationConstants.DEFAULT_REMEMBER_ME_PARAM;
        }
        this.rememberMeParam = rememberMeParam;
        bindRememberMeParamToFormAuthenticationFilter();
    }

    @Override
    public String getSuccessUrl() {
        return successUrl;
    }

    @Override
    public void setSuccessUrl(String successUrl) {
        log.debug("Set new success url {}", successUrl);
        if (successUrl == null || successUrl.isEmpty()) {
            successUrl = WebApplicationConstants.DEFAULT_SUCCESS_URL;
        }
        this.successUrl = successUrl;
        bindSuccessUrlToAuthenticationFilter();
    }

    @Override
    public String getUsernameParam() {
        return usernameParam;
    }

    @Override
    public void setUsernameParam(String usernameParam) {
        log.debug("Set new username param {}", usernameParam);
        if (usernameParam == null || usernameParam.isEmpty()) {
            usernameParam = WebApplicationConstants.DEFAULT_USERNAME_PARAM;
        }
        this.usernameParam = usernameParam;
        bindUsernameParamToFormAuthenticationFilter();
    }

    protected void bindLoginUrlToAccessControlFilter() {
        AccessControlFilter accessControlFilter =
                getAccessControlFilter();

        if (accessControlFilter != null) {
            log.debug("Bind login url {}", loginUrl);
            accessControlFilter.setLoginUrl(loginUrl);
        }
    }

    protected void bindPasswordParamToFormAuthenticationFilter() {
        FormAuthenticationFilter formAuthenticationFilter = getFormAuthenticationFilter();

        if (formAuthenticationFilter != null) {
            log.debug("Bind password param {}", passwordParam);
            formAuthenticationFilter.setPasswordParam(passwordParam);
        }
    }

    protected void bindRememberMeParamToFormAuthenticationFilter() {
        FormAuthenticationFilter formAuthenticationFilter = getFormAuthenticationFilter();

        if (formAuthenticationFilter != null) {
            log.debug("Bind remember me param {}", rememberMeParam);
            formAuthenticationFilter.setRememberMeParam(rememberMeParam);
        }
    }

    protected void bindSuccessUrlToAuthenticationFilter() {
        AuthenticationFilter authenticationFilter =
                getAuthenticationFilter();

        if (authenticationFilter != null) {
            log.debug("Bind success url {}", successUrl);
            authenticationFilter.setSuccessUrl(successUrl);
        }
    }

    protected void bindUsernameParamToFormAuthenticationFilter() {
        FormAuthenticationFilter formAuthenticationFilter = getFormAuthenticationFilter();

        if (formAuthenticationFilter != null) {
            log.debug("Bind username param {}", usernameParam);
            formAuthenticationFilter.setUsernameParam(usernameParam);
        }
    }

    /**
     * Returns the active {@link AccessControlFilter}. The superclass for any filter that
     * controls access to a resource and may redirect the user to the login page if they
     * are not authenticated.
     *
     * @return the active {@link AccessControlFilter}
     */
    protected AccessControlFilter getAccessControlFilter() {
        return (AccessControlFilter) getFilter(DefaultFilter.authc.toString());
    }

    /**
     * Returns the active {@link AuthenticationFilter}. The base class for all Filters
     * that require the current user to be authenticated. This class encapsulates
     * the logic of checking whether a user is already authenticated in the system
     * while subclasses are required to perform specific logic for unauthenticated
     * requests.
     *
     * @return the active {@link AuthenticationFilter}
     */
    protected AuthenticationFilter getAuthenticationFilter() {
        return (AuthenticationFilter) getFilter(DefaultFilter.authc.toString());
    }

    /**
     * Returns the active {@link FormAuthenticationFilter}. It requires the requesting
     * user to be authenticated for the request to continue, and if they are not,
     * forces the user to login via by redirecting them to the loginUrl you configure.
     *
     * @return the active {@link FormAuthenticationFilter}
     */
    protected FormAuthenticationFilter getFormAuthenticationFilter() {
        return (FormAuthenticationFilter) getFilter(DefaultFilter.authc.toString());
    }

    /**
     * Returns a {@link Filter} registered to {@link FilterChainManager} with the
     * specified name. The default filter instances are typically named  of the
     * {@link DefaultFilter} enum constant.
     *
     * @param filterName the filter's name
     * @return the {@link Filter} registered with the specified name
     */
    protected Filter getFilter(String filterName) {
        PathMatchingFilterChainResolver filterChainResolver =
                (PathMatchingFilterChainResolver) getFilterChainResolver();

        if (filterChainResolver != null) {
            FilterChainManager filterChainManager = filterChainResolver.getFilterChainManager();

            Map<String, Filter> filters = filterChainManager.getFilters();

            return filters.get(filterName);
        }
        return null;
    }
}
