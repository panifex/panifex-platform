/*******************************************************************************
 * Panifex platform
 * Copyright (C) 2015  Mario Krizmanic
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
package org.panifex.web.zk.runtime.html;

import org.panifex.web.spi.html.PasswordField;
import org.zkoss.zul.Textbox;

/**
 * A field that is used to enter secret text information like passwords. The entered text
 * is not displayed on the screen.
 */
public class ZkPasswordField extends Textbox implements PasswordField {

    /**
     * Creates a new password field component.
     *
     * @param id the unique id for component that is used for client-side testing purpose
     */
    public ZkPasswordField(String id) {
        setId(id);
        setType("password");
    }

    @Override
    public void addStyleName(String styleName) {
        ZkHtmlComponentUtil.addStyleName(styleName, this);
    }

    @Override
    public void removeStyleName(String styleName) {
        ZkHtmlComponentUtil.removeStyleName(styleName, this);
    }

}
