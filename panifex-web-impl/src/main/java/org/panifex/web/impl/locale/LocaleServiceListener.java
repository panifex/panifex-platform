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

import java.util.ArrayList;
import java.util.List;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Destroy;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.ReferenceList;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.panifex.module.api.locale.LocaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.LabelLocator;
import org.zkoss.util.resource.Labels;

/**
 * A {@link org.panifex.module.api.locale.LocaleService LocaleService} reference
 * listener which registers/unregisters new resources which is used to load the 
 * Locale-dependent labels.
 */
@Bean(id = LocaleServiceListener.ID, factoryMethod = "init")
@ReferenceListener
public final class LocaleServiceListener {

    public static final String ID = "org.panifex.web.impl.local.LocalServiceListener";
    
    private static Logger log = LoggerFactory.getLogger(LocaleServiceListener.class);;
    
    private static volatile LocaleServiceListener instance;
    
    @Inject
    @ReferenceList(
        availability = "optional",
        serviceInterface = LocaleService.class,
        referenceListeners = @ReferenceListener(ref = ID))
    private List<LocaleService> localeServices = new ArrayList<>();
    
    /**
     * Private construct which protects any other class from instantiating.
     */
    private LocaleServiceListener() {
    }

    /**
     * Factory method which initializes {@link LocaleServiceListener}.
     * 
     * @return the {@link LocaleServiceListener} singleton instance
     */
    public static LocaleServiceListener init() {
        log.debug("Initialize LocalServiceListener");
        if (instance == null) {
            synchronized (LocaleServiceListener.class) {
                if (instance == null) {
                    instance = new LocaleServiceListener();
                }
            }
        }
        return instance;
    }
    
    /**
     * Frees linked objects.
     */
    @Destroy
    public void destroy() {
        instance = null;
    }
    
    /**
     * Registers the {@link org.panifex.module.api.locale.LocaleService LocaleService}
     * which is used to load the Locale-dependent labels.
     * 
     * @param localeService the provided {@link org.panifex.module.api.locale.LocaleService LocaleService}
     */
    @Bind
    public void bind(LocaleService localeService) {
        log.debug("Bind LocaleService: {}", localeService);
        localeServices.add(localeService);
        
        registerLocaleService(localeService);
    }

    /**
     * Unregisters the {@link org.panifex.module.api.locale.LocaleService LocaleService}
     * which is used to load the Locale-dependent labels.
     * 
     * @param localeService the provided {@link org.panifex.module.api.locale.LocaleService LocaleService}
     */
    @Unbind
    public void unbind(LocaleService localeService) {
        log.debug("Unbind LocaleService: {}", localeService);
        localeServices.remove(localeService);
        
        // TODO
    }

    /**
     * Registers the {@link org.panifex.module.api.locale.LocaleService LocaleService}
     * to the {@link org.zkoss.util.resource.Labels Labels}.
     * <p>
     * This method uses {@link LabelLocatorBuilder} for building 
     * {@link org.zkoss.util.resource.LabelLocator LabelLocator} based
     * on the provided {@link org.panifex.module.api.locale.LocaleService LocaleService}.
     * 
     * @param localeService the provided {@link org.panifex.module.api.locale.LocaleService LocaleService}
     */
    private void registerLocaleService(LocaleService localeService) {
        List<LabelLocator> locators = new LabelLocatorBuilder(localeService).build();
        for (LabelLocator locator : locators) {
            log.debug("Register LabelLocator: {}", locator);
            Labels.register(locator);
        }
    }
}
