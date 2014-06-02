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
package org.panifex.web.impl.view.settings;

import java.util.List;

import org.panifex.module.api.content.AbstractContent;
import org.panifex.module.api.settings.SettingsContent;
import org.panifex.web.impl.i18n.SettingsLabels;
import org.panifex.web.impl.settings.SettingsContentManager;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

/**
 * A SettingsContentView renders the settings screen which is composed
 * by registered {@link org.panifex.module.api.settings.SettingsContent SettingsContent}s.
 * <p>
 * Each SettingsContent are placed in the own {@link org.zkoss.zul.Tab Tab}.
 */
public final class SettingsContentView extends AbstractContent {

    public static final String ID = "org.panifex.web.impl.view.settings.SettingsContentView";

    public SettingsContentView() {
        super(SettingsLabels.VIEW_TITLE, ID);
    }

    @Override
    public void createBody(Object parent) {
        Div body = new Div();
        ((Component) parent).appendChild(body);

        createTabbox(body);
    }

    private void createTabbox(Component body) {
        Tabbox tabbox = new Tabbox();
        body.appendChild(tabbox);

        Tabs tabs = new Tabs();
        tabbox.appendChild(tabs);

        Tabpanels tabpanels = new Tabpanels();
        tabbox.appendChild(tabpanels);

        List<SettingsContent> contents = SettingsContentManager.getContents();

        for (SettingsContent content : contents) {
            Tab tab = new Tab(Labels.getLabel(content.getTitle()));
            tabs.appendChild(tab);

            Tabpanel panel = new Tabpanel();
            tabpanels.appendChild(panel);
            content.createBody(panel);
        }
    }
}
