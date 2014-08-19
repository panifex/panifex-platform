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
package org.panifex.web.vaadin.layout;

import org.panifex.module.vaadin.api.GenericVaadinPagelet;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public abstract class LayoutVaadinPagelet extends GenericVaadinPagelet {

    @Override
    public final void service(VaadinRequest request) throws Exception {
        VerticalLayout layout = new VerticalLayout();
        createContent(request, layout);
        UI.getCurrent().setContent(layout);
    }

    protected abstract void createContent(VaadinRequest request, VerticalLayout layout);
}
