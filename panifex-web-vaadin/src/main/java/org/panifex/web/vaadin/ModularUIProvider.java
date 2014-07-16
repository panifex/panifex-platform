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
package org.panifex.web.vaadin;

import org.apache.shiro.util.PatternMatcher;

import com.vaadin.server.DefaultUIProvider;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.ui.UI;

public class ModularUIProvider extends DefaultUIProvider {

    private final PatternMatcher patternMatcher;
    private final VaadinPageletTracker pageletTracker;

    public ModularUIProvider(
            PatternMatcher patternMatcher,
            VaadinPageletTracker pageletTracker) {
        if (patternMatcher == null) {
            throw new IllegalArgumentException("patternMatcher cannot be null");
        }
        if (pageletTracker == null) {
            throw new IllegalArgumentException("pageletTracker cannot be null");
        }
        this.patternMatcher = patternMatcher;
        this.pageletTracker = pageletTracker;
    }

    @Override
    public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
        return PageletAwareUI.class;
    }

    @Override
    public UI createInstance(UICreateEvent event) {
        PageletAwareUI ui = new PageletAwareUI(
                pageletTracker.getPageletMappings(),
                pageletTracker.getPagelets(),
                patternMatcher);
        return ui;
    }

}
