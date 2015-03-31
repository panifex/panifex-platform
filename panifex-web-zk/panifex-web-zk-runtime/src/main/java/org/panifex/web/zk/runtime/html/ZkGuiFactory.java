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
package org.panifex.web.zk.runtime.html;

import java.util.ArrayDeque;
import java.util.Deque;

import org.panifex.web.spi.html.Button;
import org.panifex.web.spi.html.FieldComponent;
import org.panifex.web.spi.html.GuiFactory;
import org.panifex.web.spi.html.HorizontalLayout;
import org.panifex.web.spi.html.HtmlComponent;
import org.panifex.web.spi.html.TextField;
import org.panifex.web.spi.html.VerticalLayout;
import org.zkoss.bind.Binder;
import org.zkoss.bind.DefaultBinder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;

public class ZkGuiFactory implements GuiFactory<Page> {

    /* package */ static final String VM_BIND_ID = "VM";

    private ThreadLocal<Deque<Binder>> binderThreadLocal = new ThreadLocal<>();

    @Override
    public void setPageContent(Page request, HtmlComponent htmlComp) {
        Component comp = ZkHtmlComponentUtil.castHtmlComponent(htmlComp);
        comp.setPage(request);
    }

    @Override
    public void initViewModelBinding(Object viewModel, HtmlComponent htmlComp) {
        Component comp = ZkHtmlComponentUtil.castHtmlComponent(htmlComp);

        Deque<Binder> registeredBinders = binderThreadLocal.get();
        if (registeredBinders == null) {
            registeredBinders = new ArrayDeque<>();
            binderThreadLocal.set(registeredBinders);
        }

        // init binder
        DefaultBinder binder = new DefaultBinder();
        registeredBinders.add(binder);

        binder.init(comp, viewModel, null);
        comp.setAttribute(VM_BIND_ID + registeredBinders.size(), viewModel);
    }

    @Override
    public void bindProperty(Object viewModel, String propertyId, FieldComponent<?> field) {
        Component comp = ZkHtmlComponentUtil.castHtmlComponent(field);

        Deque<Binder> registeredBinders = binderThreadLocal.get();
        if (registeredBinders.isEmpty()) {
            throw new IllegalStateException("Binder must be registered");
        }

        Binder binder = registeredBinders.getLast();
        if (viewModel != binder.getViewModel()) {
            throw new IllegalStateException("Bind property only on last registered bean item");
        }

        int bindId = registeredBinders.size();

        String expr = new StringBuilder(VM_BIND_ID).
                append(bindId).
                append('.').
                append(propertyId).
                toString();

        binder.addPropertyLoadBindings(comp, "value", expr, null, null, null, null, null);
        binder.addPropertySaveBindings(comp, "value", expr, null, null, null, null, null, null, null);
    }

    @Override
    public Button createButton() {
        return new ZkButton();
    }

    @Override
    public HorizontalLayout createHorizontalLayout() {
        return new ZkHorizontalLayout();
    }

    @Override
    public VerticalLayout createVerticalLayout() {
        return new ZkVerticalLayout();
    }

    @Override
    public TextField createTextField() {
        return new ZkTextField();
    }
}
