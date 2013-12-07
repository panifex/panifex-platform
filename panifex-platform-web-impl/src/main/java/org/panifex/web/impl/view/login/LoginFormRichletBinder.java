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

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.panifex.web.impl.servlet.ZkLayoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.lang.Classes;

@Bean(id = LoginFormRichletBinder.ID)
@ReferenceListener
public class LoginFormRichletBinder {

    private Logger log = LoggerFactory.getLogger(LoginFormRichlet.class);

    public final static String ID = "org.panifex.web.impl.login.LoginRichletBinder";

    @Inject
    @Reference(availability = "optional", serviceInterface = ZkLayoutService.class, referenceListeners = @ReferenceListener(ref = ID))
    private ZkLayoutService zkLayoutService;

    @Bind
    public void bind(ZkLayoutService zkLayoutService) {
        log.debug("Bind Zk layout service: {}", zkLayoutService);
        this.zkLayoutService = zkLayoutService;

        zkLayoutService.addRichlet(LoginFormRichlet.class, "/login");
        
        try {
            Classes.forNameByThread(LoginFormRichlet.class.getName());
        } catch (ClassNotFoundException e) {
            log.error("Greska: {}", e);
        }
    }

    @Unbind
    public void unbind(ZkLayoutService zkLayoutServlet) {
        this.zkLayoutService = null;
    }
}
