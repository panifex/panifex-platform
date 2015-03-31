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

import java.util.ArrayDeque;
import java.util.Deque;

import org.panifex.web.spi.html.Button;
import org.panifex.web.spi.html.FieldComponent;
import org.panifex.web.spi.html.GuiFactory;
import org.panifex.web.spi.html.HorizontalLayout;
import org.panifex.web.spi.html.HtmlComponent;
import org.panifex.web.spi.html.TextField;
import org.panifex.web.spi.html.VerticalLayout;

import com.vaadin.data.Property.Viewer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

public class VaadinGuiFactory implements GuiFactory<VaadinRequest> {

    private ThreadLocal<Deque<BeanItem<Object>>> beanItemThreadLocal = new ThreadLocal<>();

    @Override
    public void setPageContent(VaadinRequest request, HtmlComponent htmlComp) {
        Component comp = VaadinHtmlComponentUtil.castHtmlComponent(htmlComp, Component.class);
        UI.getCurrent().setContent(comp);
    }

    @Override
    public void initViewModelBinding(Object viewModel, HtmlComponent htmlComp) {
        Deque<BeanItem<Object>> deque = beanItemThreadLocal.get();
        if (deque == null) {
            deque = new ArrayDeque<>();
            beanItemThreadLocal.set(deque);
        }

        BeanItem<Object> beanItem = new BeanItem<>(viewModel);
        deque.add(beanItem);
    }

    @Override
    public void bindProperty(Object viewModel, String propertyId, FieldComponent<?> fieldComp) {
        BeanItem<Object> beanItem = beanItemThreadLocal.get().getLast();
        if (viewModel != beanItem.getBean()) {
            throw new IllegalStateException("Bind property only on last registered bean item");
        }

        Viewer viewer = VaadinHtmlComponentUtil.castHtmlComponent(fieldComp, Viewer.class);
        viewer.setPropertyDataSource(beanItem.getItemProperty(propertyId));
    }

    @Override
    public Button createButton() {
        return new VaadinButton();
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
