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
package org.panifex.web.impl.content;

import org.apache.aries.blueprint.annotation.Bean;
import org.panifex.module.api.content.Content;
import org.zkoss.zhtml.H1;
import org.zkoss.zhtml.Text;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;

@Bean(id = ContentUiFactory.ID)
public class ContentUiFactory {

    public final static String ID = "org.panifex.web.impl.content.ContentUiFactory";
    
    public void render(Component parent, Content content) {
        final Div root = new Div();
        parent.appendChild(root);
        
        final Div contentHeader = new Div();
        contentHeader.setSclass("content-header");
        root.appendChild(contentHeader);
        
        final H1 title = new H1();
        contentHeader.appendChild(title);
        
        final Text titleText = new Text(content.getTitle());
        title.appendChild(titleText);
        
        final Div body = new Div();
        body.setSclass("content-body");
        root.appendChild(body);
        
        content.createBody(body);
    }
}
