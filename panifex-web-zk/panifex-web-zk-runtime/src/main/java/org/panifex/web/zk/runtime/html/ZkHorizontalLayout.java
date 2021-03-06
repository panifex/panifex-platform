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

import org.panifex.web.spi.html.HorizontalLayout;
import org.panifex.web.spi.html.HtmlComponent;
import org.zkoss.zul.Hlayout;

/**
 * A horizontal layout component.
 */
public class ZkHorizontalLayout extends Hlayout implements HorizontalLayout {

    /**
     * Creates a horizontal layout container.
     *
     * @param id the unique id for component that is used for client-side testing purpose
     */
    public ZkHorizontalLayout(String id) {
        setId(id);
    }

    @Override
    public void addHtmlComponent(HtmlComponent htmlComp) {
        ZkHtmlComponentUtil.addComponentToContainer(htmlComp, this);
    }

    @Override
    public void removeHtmlComponent(HtmlComponent htmlComp) {
        ZkHtmlComponentUtil.removeComponentFromContainer(htmlComp, this);
    }

    @Override
    public void addStyleName(String styleName) {
        ZkHtmlComponentUtil.addStyleName(styleName, this);
    }

    @Override
    public void removeStyleName(String styleName) {
        ZkHtmlComponentUtil.removeStyleName(styleName, this);
    }
}
