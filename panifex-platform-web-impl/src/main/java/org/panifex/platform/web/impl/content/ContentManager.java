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
package org.panifex.platform.web.impl.content;

import java.util.HashSet;
import java.util.Set;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.ReferenceList;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.panifex.platform.module.api.content.Content;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Bean(id = ContentManager.ID)
@ReferenceListener
public class ContentManager {

    private Logger log = LoggerFactory.getLogger(ContentManager.class);
    
    public final static String ID = "org.panifex.platform.web.impl.content.ContentManager";
    
    @Inject
    @ReferenceList(availability = "optional", serviceInterface = Content.class, referenceListeners = @ReferenceListener(ref = ID))
    private Content content;
    
    private Set<Content> contents = new HashSet<>();
    
    @Bind
    public void bind(Content content) {
        log.debug("Bind content: {}", content);
        this.content = content;
        contents.add(content);
    }
    
    @Unbind
    public void unbind(Content content) {
        log.debug("Unbind content: {}", content);
        this.content = null;
        contents.remove(content);
    }
}
