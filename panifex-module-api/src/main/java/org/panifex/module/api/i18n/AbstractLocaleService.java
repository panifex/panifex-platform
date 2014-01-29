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
package org.panifex.module.api.i18n;

import java.net.URL;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A template {@link LocaleService} implementation which can be used
 * to locate extra resources for {@link org.zkoss.util.resource.Labels Labels}.
 * <p>
 * The implementation should provide {@link java.util.Locale Locale} to which
 * the extra resources relate.
 * <p>
 * It should provide {@link org.osgi.framework.BundleContext BundleContext} to the
 * AbstractLocaleService from the blueprint container. It can be done by the next example:
 * <blockquote><pre><code>
 * {@literal @}org.apache.aries.blueprint.annotation.Inject(ref = "blueprintBundleContext")
 * private BundleContext bundleContext;
 * 
 * {@literal @}Override
 * public final void setBundleContext(BundleContext bundleContext) {
 *    setBundleContext0(bundleContext);
 * }
 * </code></pre></blockquote>
 */
public abstract class AbstractLocaleService implements LocaleService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final Locale supportedLocale;
    
    /**
     * The collection of the extra resources for {@link org.zkoss.util.resource.Labels Labels}.
     */
    private final Set<String> resources;
    
    private BundleContext bundleContext;
    
    /**
     * Constructs a new AbstractLocaleService for the supported {@link java.util.Locale Locale}.
     * <p>
     * The resources set is empty, so the resources should be added by 
     * {@link AbstractLocaleService#addResource(String) addResource(String)} method.
     * 
     * @param locale the supported {@link java.util.Locale Locale}
     */
    protected AbstractLocaleService(Locale locale) {
        this(locale, new HashSet<String>());
    }
    
    /**
     * Constructs a new AbstractLocaleService for the supported {@link java.util.Locale Locale}.
     * <p>
     * Additional resources could be added by {@link AbstractLocaleService#addResource(String) addResource(String)} 
     * method.
     * 
     * @param locale the supported {@link java.util.Locale Locale} 
     * @param resources the extra resources for {@link org.zkoss.util.resource.Labels Labels}
     */
    protected AbstractLocaleService(Locale locale, Set<String> resources) {
        if (locale == null) {
            throw new IllegalArgumentException("locale must not be null");
        }
        if (resources == null) {
            throw new IllegalArgumentException("Resources must not be null");
        }
        this.supportedLocale = locale;
        this.resources = resources;
    }

    /**
     * An abstract method for binding the {@link org.osgi.framework.BundleContext BundleContext}
     * to the AbstractLocaleService.
     * <p>
     * It should be implemented by the next example:
     * <blockquote><pre><code>
     * {@literal @}Override
     * public final void setBundleContext(BundleContext bundleContext) {
     *    setBundleContext0(bundleContext);
     * }
     * </code></pre></blockquote>
     * 
     * @param bundleContext
     */
    public abstract void setBundleContext(BundleContext bundleContext);
    
    protected final void setBundleContext0(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }
    
    /**
     * Adds an additional resource location.
     * 
     * @param resource the additional resource location
     */
    protected final void addResource(String resource) {
        resources.add(resource);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final Locale supportedLocale() {
        return supportedLocale;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final Set<URL> locates() {
        Set<URL> set = new HashSet<>();
        
        for (String resource : resources) {
            URL url = getBundle().getResource(resource);
            if (url != null) {
                set.add(url);
            } else {
                log.error("Unable to find resource: {}", resource);
            }
        }
        
        return set;
    }
    
    /**
     * Return the {@link org.osgi.framework.Bundle Bundle} from the
     * associated {@link org.osgi.framework.BundleContext BundleContext}.
     * 
     * @return the {@link org.osgi.framework.Bundle Bundle}
     */
    private Bundle getBundle() {
        return bundleContext.getBundle();
    }
}
