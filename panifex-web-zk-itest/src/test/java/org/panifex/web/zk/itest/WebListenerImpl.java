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
package org.panifex.web.zk.itest;

import org.ops4j.pax.web.service.spi.WebEvent;
import org.ops4j.pax.web.service.spi.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebListenerImpl implements WebListener {

    protected Logger log = LoggerFactory.getLogger(getClass());

    private boolean event;

    @Override
    public void webEvent(WebEvent webEvent) {
        log.info("Got event: " + webEvent);
        if (webEvent.getType() == WebEvent.DEPLOYED) {
            event = true;
        } else if (webEvent.getType() == WebEvent.UNDEPLOYED) {
            event = false;
        }
    }

    public boolean gotEvent() {
        return event;
    }

}
