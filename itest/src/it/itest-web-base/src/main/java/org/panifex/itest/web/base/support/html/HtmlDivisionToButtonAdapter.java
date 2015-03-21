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
package org.panifex.itest.web.base.support.html;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;

/**
 *
 * @author mariokr
 *
 */
public class HtmlDivisionToButtonAdapter implements ButtonElement {

    private final HtmlDivision htmlDivision;

    public HtmlDivisionToButtonAdapter(HtmlDivision htmlDivision) {
        this.htmlDivision = htmlDivision;
    }

    @Override
    public void click() throws Exception {
        // double click because single click is not registered by division element
        htmlDivision.dblClick();
    }
}