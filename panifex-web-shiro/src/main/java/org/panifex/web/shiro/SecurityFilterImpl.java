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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.web.env.EnvironmentLoader;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.panifex.module.web.api.WebApplicationConstants;
import org.panifex.service.api.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityFilterImpl extends ShiroFilter implements SecurityFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<SecurityService> securityServices = new ArrayList<>();

    private EnvironmentLoader loader;
    private String loginUrl;

    @Override
    public void init() throws Exception {
        log.info("Initialize security filter");
        loader.initEnvironment(getServletContext());

        super.init();
        setLoginUrl(loginUrl);
    }

    @Override
    public void destroy() {
        loader.destroyEnvironment(getServletContext());
        super.destroy();
    }

    public void setEnvironmentLoader(EnvironmentLoader loader) {
        this.loader = loader;
    }

    public void bind(SecurityService securityService) {
        log.debug("Bind security service: {}", securityService);
        securityServices.add(securityService);
    }

    public void unbind(SecurityService securityService) {
        log.debug("Unbind security service: {}", securityService);
        securityServices.remove(securityService);
    }

    @Override
    public String getLoginUrl() {
        return loginUrl;
    }

    @Override
    public void setLoginUrl(String loginUrl) {
        log.debug("Set new login url {}", loginUrl);
        if ((loginUrl == null) || loginUrl.isEmpty()) {
            loginUrl = WebApplicationConstants.DEFAULT_LOGIN_URL;
        }
        this.loginUrl = loginUrl;
        bindLoginUrlToAccessControlFilter();
    }

    private void bindLoginUrlToAccessControlFilter() {
        PathMatchingFilterChainResolver filterChainResolver =
                (PathMatchingFilterChainResolver) getFilterChainResolver();

        if (filterChainResolver != null) {

            FilterChainManager filterChainManager = filterChainResolver.getFilterChainManager();

            Map<String, Filter> filters = filterChainManager.getFilters();

            AccessControlFilter accessControlFilter =
                    (AccessControlFilter) filters.get(DefaultFilter.authc.toString());

            if (accessControlFilter != null) {
                log.debug("Bind login url {}", loginUrl);
                accessControlFilter.setLoginUrl(loginUrl);
            }
        }
    }
}
