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
import org.panifex.web.impl.view.layout.AbstractRichlet;
import org.panifex.web.impl.view.layout.AbstractVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;

/**
 * MainRichlet draws main window.
 * 
 */
public class MainRichlet extends AbstractRichlet {

    private Logger log = LoggerFactory.getLogger(MainRichlet.class);
    
    @Override
    protected Component createContent() {
        log.debug("Create content");
        
        final Div content = new Div();
        content.setId("content");
        content.setSclass("content");
        
        content.appendChild(ContentManager.getManager().render(""));
        
        return content;
    }

    @Override
    protected AbstractVM getViewModel() {
        return new MainVM();
    }

}
