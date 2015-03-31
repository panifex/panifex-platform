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

import org.panifex.web.spi.html.GuiFactory;
import org.panifex.web.spi.html.HorizontalLayout;
import org.panifex.web.spi.html.HtmlComponent;
import org.panifex.web.spi.html.VerticalLayout;
import org.zkoss.bind.DefaultBinder;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;

public class ZkGuiFactory implements GuiFactory<Page> {

    public static final String VM_BIND_ID = "VM";

    private ThreadLocal<DefaultBinder> binderThreadLocal = new ThreadLocal<>();

    @Override
    public void setPageContent(Page request, HtmlComponent content) {
        Component component = ZkHtmlComponentUtil.castHtmlComponent(content);
        component.setPage(request);
    }

    @Override
    public void initViewModelBinding(Object viewModel, HtmlComponent content) {
        Component component = ZkHtmlComponentUtil.castHtmlComponent(content);

        // init binder
        DefaultBinder binder = new DefaultBinder();
        binder.init(component, viewModel, null);
        component.setAttribute(VM_BIND_ID, viewModel);
        binderThreadLocal.set(binder);
    }

    @Override
    public HorizontalLayout createHorizontalLayout() {
        return new ZkHorizontalLayout();
    }

    @Override
    public VerticalLayout createVerticalLayout() {
        return new ZkVerticalLayout();
    }
}
