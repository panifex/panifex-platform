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

import java.util.HashSet;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.panifex.module.api.content.Content;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;

public class ContentManager {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    public final static String ID = "org.panifex.web.impl.content.ContentManager";
    
    private Set<Content> contents = new HashSet<>();
    
    private ContentUiFactory contentUiFactory;
    
    public void bind(Content content) {
        log.debug("Bind content: {}", content);
        contents.add(content);
    }
    
    public void unbind(Content content) {
        log.debug("Unbind content: {}", content);
        contents.remove(content);
    }
    
    public void setContentUiFactory(ContentUiFactory contentUiFactory) {
        this.contentUiFactory = contentUiFactory;
    }
    
    public void render(Component parent, String bookmark) {
        
        Content content = getBookmarkedContent(bookmark);
        
        if (content == null) {
            // if bookmarked content hasn't been found, return empty content
            log.debug("Bookmarked content hasn't been found. The empty content is shown.");
            content = new EmptyContent();
        }
        
        contentUiFactory.render(parent, content);
    }
    
    private Content getBookmarkedContent(String bookmark) {
        for (Content content : contents) {
            if (content.getBookmark() != null) {
                if (content.getBookmark().equalsIgnoreCase(bookmark)) {
                    log.debug("Founded bookmarked content: {}", content);
                    return content;
                }
            }
        }
        return null;
    }
    
    public static ContentManager getManager() {
        ContentManager contentManager;
        
        InitialContext ctx;
        try {
            ctx = new InitialContext();
            contentManager = (ContentManager) ctx.lookup("blueprint:comp/" + ContentManager.ID);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        
        return contentManager;
    }
}
