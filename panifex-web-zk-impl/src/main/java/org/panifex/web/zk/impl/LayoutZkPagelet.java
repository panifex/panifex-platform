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
package org.panifex.web.zk.impl;

import org.panifex.module.zk.api.GenericZkPagelet;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.sys.PageCtrl;

public abstract class LayoutZkPagelet extends GenericZkPagelet {

    @Override
    public final void service(Page page) {
        PageCtrl pageCtrl = (PageCtrl) page;
        pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/bootstrap/css/bootstrap.min.css\"/>");
    }
}
