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
package org.panifex.web.impl.locale;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.panifex.module.api.locale.LocaleService;
import org.zkoss.util.resource.LabelLocator;

/**
 * A builder which builds {@link org.zkoss.util.resource.LabelLocator LabelLocator}
 * based on the provided {@link org.panifex.module.api.locale.LocaleService LocaleService}.
 */
final class LabelLocatorBuilder {

    private final Locale supportedLocale;
    private final Set<URL> locates;
    
    /**
     * Construct a new LabelLocatorBuilder.
     * 
     * @param localeService the provided {@link org.panifex.module.api.locale.LocaleService LocaleService}
     */
    public LabelLocatorBuilder(LocaleService localeService) {
        if (localeService == null) {
            throw new NullPointerException("LocaleService must not be null.");
        }
        supportedLocale = localeService.supportedLocale();
        if (supportedLocale == null) {
            throw new IllegalArgumentException("LocaleService must return at least one supported locale.");
        }
        locates = localeService.locates();
        if (locates == null) {
            throw new IllegalArgumentException("LocalService must return at least one input stream.");
        }
    }
    
    /**
     * Builds the collection of {@link org.zkoss.util.resource.LabelLocator LabelLocator}
     * based on the provided {@link org.panifex.module.api.locale.LocaleService LocaleService}.
     * 
     * @return the collection of {@link org.zkoss.util.resource.LabelLocator LabelLocator}
     */
    public List<LabelLocator> build() {
        List<LabelLocator> locators = new ArrayList<>();
        
        for (URL url : locates) {
            // create LabelLocatorImpl
            LabelLocatorImpl locator = new LabelLocatorImpl(
                supportedLocale, 
                url);
            
            locators.add(locator);
        }
        
        return locators;
    }
}
