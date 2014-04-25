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
package org.panifex.web.impl.view.login;

import org.panifex.web.impl.servlet.ZkLayoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangePasswordFormRichletBinder {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public final static String ID = "org.panifex.web.impl.view.login.ChangePasswordFormRichletBinder";

    private final static String PATH = "/changepassword";
    
    public void bind(ZkLayoutService zkLayoutService) throws ClassNotFoundException {
        log.debug("Bind Zk layout service: {}", zkLayoutService);

        zkLayoutService.addRichlet(ChangePasswordFormRichlet.class, PATH);
    }

    public void unbind(ZkLayoutService zkLayoutService) {
        log.debug("Unbind Zk layout service: {}", zkLayoutService);
        if (zkLayoutService != null) {
            // unregister change password form richlet
            zkLayoutService.addRichlet(null, PATH);
        }
    }
}
