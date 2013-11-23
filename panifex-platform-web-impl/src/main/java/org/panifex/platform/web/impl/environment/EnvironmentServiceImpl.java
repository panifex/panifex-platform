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
package org.panifex.platform.web.impl.environment;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Service;
import org.panifex.platform.module.api.environment.EnvironmentService;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.Events;

@Bean(id = EnvironmentServiceImpl.ID)
@Service(interfaces = EnvironmentService.class)
public class EnvironmentServiceImpl implements EnvironmentService {

    public final static String ID = "org.panifex.platform.web.impl.environment.EnvironmentServiceImpl";
    
    private Desktop getDesktop() {
        return Executions.getCurrent().getDesktop();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getBookmark() {
        return getDesktop().getBookmark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBookmark(String bookmark) {
        setBookmark(bookmark, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBookmark(String name, boolean replace) {
        getDesktop().setBookmark(name, replace);
        Events.postEvent(new BookmarkEvent(Events.ON_BOOKMARK_CHANGE, name));
    }

}
