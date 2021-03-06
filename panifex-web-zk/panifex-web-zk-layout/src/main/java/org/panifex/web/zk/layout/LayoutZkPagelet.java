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
package org.panifex.web.zk.layout;

import org.osgi.service.blueprint.container.BlueprintContainer;
import org.panifex.module.zk.api.GenericZkPagelet;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zul.Div;

public abstract class LayoutZkPagelet extends GenericZkPagelet {

    protected LayoutZkPagelet() {
    }

    protected LayoutZkPagelet(BlueprintContainer container) {
        super(container);
    }

    @Override
    public final void service(Page page) {
        //PageCtrl pageCtrl = (PageCtrl) page;
        //pageCtrl.addAfterHeadTags(css("/css/bootstrap/css/bootstrap.min.css"));

        Div main = new Div();
        main.setId("main");
        main.setPage(page);
        createComponents(page, main);

        //Script s = new Script();
        //s.setSrc("../css/bootstrap/js/bootstrap.min.js");
        //s.setParent(main);
    }

    private void createComponents(Page page, Component parent) {
        createLogo(parent);
        createContent(page, parent);
    }

    private void createLogo(Component parent) {

        final Div header = new Div();
        header.setSclass("header");
        parent.appendChild(header);

        final Div fill = new Div();
        fill.setSclass("fill");
        header.appendChild(fill);

        //final A logo = new A();
        //logo.setHref(Labels.getLabel(ApplicationLabels.APPLICATION_NAME));
        //logo.setImage("../img/panifex_top_logo.png"); // TODO it have to be configurable
        //fill.appendChild(logo);
    }

    /*private String css(String css) {
        return new StringBuilder("<link ").
                append("rel=\"stylesheet\" ").
                append("type=\"text/css\" ").
                append("href=\"").
                append(css).
                append("\"/>").
                toString();
    }*/

    protected abstract void createContent(Page page, Component parent);
}
