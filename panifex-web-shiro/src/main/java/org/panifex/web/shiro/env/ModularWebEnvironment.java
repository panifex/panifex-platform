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

import org.apache.shiro.config.Ini;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.apache.shiro.web.env.IniWebEnvironment;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.panifex.module.api.security.FilterPath;

public class ModularWebEnvironment extends IniWebEnvironment {

    private BundleContext context;

    public void bindFilterPath(ServiceReference<FilterPath> filterPathReference) {
        FilterPath filterPath = context.getService(filterPathReference);
        FilterChainManager manager = getFilterChainManager();
        manager.createChain(filterPath.getUrl(), filterPath.getFilter());
    }

    public void unbindFilterPath(FilterPath filter) {
        // TODO
    }

    public void setBundleContext(BundleContext context) {
        this.context = context;
    }

    @Override
    protected FilterChainResolver createFilterChainResolver() {
        FilterChainResolver resolver = super.createFilterChainResolver();
        if (resolver == null) {
            Ini ini = getIni();
            IniFilterChainResolverFactory factory = new IniFilterChainResolverFactory(ini, objects);
            resolver = factory.getInstance();
        }
        return resolver;
    }

    private FilterChainManager getFilterChainManager() {
        PathMatchingFilterChainResolver resolver =
                (PathMatchingFilterChainResolver) getFilterChainResolver();

    return resolver.getFilterChainManager();
    }
}
