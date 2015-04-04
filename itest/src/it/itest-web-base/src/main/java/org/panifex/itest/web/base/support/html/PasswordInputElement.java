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

/**
 * Wrapper for the HTML element "input".
 */
public interface PasswordInputElement {

    /**
     * <p>Return the value of the attribute "value". Refer to the
     * <a href='http://www.w3.org/TR/html401/'>HTML 4.01</a>
     * documentation for details on the use of this attribute.</p>
     *
     * @return the value of the attribute "value" or an empty string if that attribute isn't defined
     */
    String getValueAttribute();

    /**
     * Sets the content of the "value" attribute, executing onchange handlers if appropriate.
     * This method returns the page contained by this element's window after the value is set,
     * which may or may not be the same as the original page.
     * @param newValue the new content
     */
    void setValueAttribute(final String newValue);
}
