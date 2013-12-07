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
package org.panifex.module.dashboard.impl;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Service;
import org.panifex.module.api.content.AbstractContent;
import org.panifex.module.api.content.Content;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;

@Bean(id = DashboardContent.ID)
@Service(interfaces = Content.class)
public class DashboardContent extends AbstractContent {

    public final static String ID = "org.panifex.module.dashboard.impl.DashboardContent";
    
    public DashboardContent() {
        super("Dashboard", ID, true);
    }

    @Override
    public Component createBody() {
        return new Div();
    }
}
