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
package org.panifex.web.zk.runtime;

import org.panifex.module.zk.api.GenericZkPagelet;
import org.panifex.module.zk.api.ZkPagelet;
import org.zkoss.zk.ui.Page;

public class ZkRichletDispatcher extends GenericZkPagelet {

    private final ZkPageletTracker pageletTracker;

    public ZkRichletDispatcher(ZkPageletTracker pageletTracker) {
        if (pageletTracker == null) {
            throw new IllegalArgumentException("pageletTracker cannot be null");
        }
        this.pageletTracker = pageletTracker;
    }

    @Override
    public void service(Page request) throws Exception {
        ZkPagelet pagelet = pageletTracker.matchPathToPagelet(request.getRequestPath());
        if (pagelet != null) {
            pagelet.service(request);
        } else {
            // TODO: it should return HTTP 404 - Not found because associated pagelet
            // is not mapped to requested url
            throw new Exception("Not found");
        }
    }
}
