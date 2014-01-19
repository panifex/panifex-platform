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
package org.panifex.web.impl.settings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Destroy;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.ReferenceList;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.panifex.module.api.settings.SettingsContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This manager keeps references to active {@link org.panifex.module.api.settings.SettingsContent SettingsContent}
 * instances.
 */
@Bean(id = SettingsContentManager.ID, factoryMethod = "init")
@ReferenceListener
public final class SettingsContentManager {

    public final static String ID = "org.panifex.web.impl.settings.SettingsContentManager";
    
    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(SettingsContentManager.class);
    
    /**
     * The singleton instance of {@link SettingsContentManager} class.
     */
    private static volatile SettingsContentManager manager;
    
    /**
     * The references of the {@link org.panifex.module.api.settings.SettingsContent SettingsContent}.
     */
    @Inject
    @ReferenceList(
        availability = "optional", 
        serviceInterface = SettingsContent.class, 
        referenceListeners = @ReferenceListener(ref = ID))
    private List<SettingsContent> contents = new ArrayList<>();
    
    /**
     * Private construct which protects any other class from instantiating.
     */
    private SettingsContentManager() {
    }
    
    /**
     * Binds the {@link org.panifex.module.api.settings.SettingsContent SettingsContent} 
     * to the manager.
     * 
     * @param content the {@link org.panifex.module.api.settings.SettingsContent SettingsContent} to be binded to
     */
    @Bind
    public void bind(SettingsContent content) {
        log.debug("Bind settings content: {}", content);
        contents.add(content);
    }
    
    /**
     * Unbinds the {@link org.panifex.module.api.settings.SettingsContent SettingsContent}
     * from the manager.
     * 
     * @param content the {@link org.panifex.module.api.settings.SettingsContent SettingsContent} to be unbinded from
     */
    @Unbind
    public void unbind(SettingsContent content) {
        log.debug("Unbind settings content: {}", content);
        contents.remove(content);
    }
    
    /**
     * Returns the collections of currently registered {@link org.panifex.module.api.settings.SettingsContent SettingsContent}
     * instances.
     * 
     * @return the collections of registered {@link org.panifex.module.api.settings.SettingsContent SettingsContent} instances
     */
    public static List<SettingsContent> getContents() {
        return Collections.unmodifiableList(manager.contents);
    }
    
    /**
     * Factory method which initializes {@link SettingsContentManager}.
     * 
     * @return the {@link SettingsContentManager} singleton instance
     */
    public static SettingsContentManager init() {
        log.debug("Initialize SecurityServiceManager");
        if (manager == null) {
            synchronized(SettingsContentManager.class) {
                if (manager == null) {
                    manager = new SettingsContentManager();
                }
            }
        }
        return manager;
    }
    
    /**
     * Frees linked objects.
     */
    @Destroy
    public void destroy() {
        manager = null;
    }
}
