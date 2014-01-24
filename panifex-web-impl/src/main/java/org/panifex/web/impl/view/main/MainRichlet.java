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
package org.panifex.web.impl.view.main;

import org.panifex.web.impl.content.ContentManager;
import org.panifex.web.impl.view.layout.LayoutRichlet;
import org.panifex.web.impl.view.layout.LayoutVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;

/**
 * MainRichlet draws main window.
 * 
 */
public class MainRichlet extends LayoutRichlet {

    private Logger log = LoggerFactory.getLogger(MainRichlet.class);
    
    public static final String URL = "/zk/main";
    
    @Override
    protected void createContent(Component parent) {
        log.debug("Create content");
        
        final Div content = new Div();
        content.setId("content");
        content.setSclass("content");
        parent.appendChild(content);
        
        ContentManager.getManager().render(content, "");
    }

    @Override
    protected LayoutVM getViewModel() {
        return new MainVM();
    }

}
