/*******************************************************************************
 * Panifex platform
 * Copyright (C) 2015  Mario Krizmanic
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
package org.panifex.web.vaadin.runtime.html;

import org.panifex.web.spi.html.GuiFactory;
import org.panifex.web.spi.html.HorizontalLayout;
import org.panifex.web.spi.html.HtmlComponent;
import org.panifex.web.spi.html.TextField;
import org.panifex.web.spi.html.VerticalLayout;

import com.vaadin.data.util.BeanItem;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

public class VaadinGuiFactory implements GuiFactory<VaadinRequest> {

    private ThreadLocal<BeanItem<Object>> beanItemThreadLocal = new ThreadLocal<>();

    @Override
    public void setPageContent(VaadinRequest request, HtmlComponent content) {
        if (Component.class.isInstance(content)) {
            Component component = (Component) content;
            UI.getCurrent().setContent(component);
        } else {
            String msg = new StringBuilder("Only ").
                    append(Component.class.getCanonicalName()).
                    append(" instance may be set as page content").
                    toString();
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public void initViewModelBinding(Object viewModel, HtmlComponent content) {
        BeanItem<Object> beanItem = new BeanItem<>(viewModel);
        beanItemThreadLocal.set(beanItem);
    }

    @Override
    public HorizontalLayout createHorizontalLayout() {
        return new VaadinHorizontalLayout();
    }

    @Override
    public VerticalLayout createVerticalLayout() {
        return new VaadinVerticalLayout();
    }

    @Override
    public TextField createTextField() {
        return new VaadinTextField();
    }

}
