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

import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

/**
 * Adapts {@link HtmlTextInput} to {@link TextInputElement}.
 */
public class HtmlTextInputToTextInputAdapter implements TextInputElement {

    private final HtmlTextInput textInput;

    public HtmlTextInputToTextInputAdapter(HtmlTextInput textInput) {
        this.textInput = textInput;
    }

    @Override
    public String getValueAttribute() {
        return textInput.getValueAttribute();
    }

    @Override
    public void setValueAttribute(String newValue) {
        textInput.setValueAttribute(newValue);
    }
}
