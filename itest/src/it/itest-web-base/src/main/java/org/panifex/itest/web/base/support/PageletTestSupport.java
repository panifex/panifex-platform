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
package org.panifex.itest.web.base.support;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.OptionUtils;
import org.panifex.itest.web.base.support.html.ButtonElement;
import org.panifex.itest.web.base.support.html.HTMLButtonToButtonAdapter;
import org.panifex.itest.web.base.support.html.HTMLInputToTextInputAdapter;
import org.panifex.itest.web.base.support.html.HtmlDivisionToButtonAdapter;
import org.panifex.itest.web.base.support.html.HtmlTextInputToTextInputAdapter;
import org.panifex.itest.web.base.support.html.TextInputElement;
import org.panifex.test.support.IWebTestSupport;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLButtonElement;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLInputElement;

public abstract class PageletTestSupport extends IWebTestSupport {

    private final PageletTestHelper testHelper;

    public PageletTestSupport(PageletTestHelper testHelper) {
        this.testHelper = testHelper;
    }

    @Override
    protected Option[] webConfigure() {
        return OptionUtils.combine(
                super.webConfigure(),

                mavenBundle("org.panifex.itest.web", "itest-web-base").versionAsInProject());
    }

    public final ButtonElement getButtonElementById(final HtmlPage htmlPage, final String id) {
        Object element = testHelper.getHtmlElementById(htmlPage, id);

        if (HTMLButtonElement.class.isInstance(element)) {
            return new HTMLButtonToButtonAdapter((HTMLButtonElement) element);
        } else if (HtmlDivision.class.isInstance(element)) {
            return new HtmlDivisionToButtonAdapter((HtmlDivision) element);
        } else {
            throw new ClassCastException("Unexpected class: " + element.getClass().getCanonicalName());
        }
    }

    public final TextInputElement getTextInputElementById(final HtmlPage htmlPage, final String id) {
        Object element = testHelper.getHtmlElementById(htmlPage, id);

        if (HTMLInputElement.class.isInstance(element)) {
            return new HTMLInputToTextInputAdapter((HTMLInputElement) element);
        } else if (HtmlTextInput.class.isInstance(element)) {
            return new HtmlTextInputToTextInputAdapter((HtmlTextInput) element);
        } else {
            throw new ClassCastException("Unexpected class: " + element.getClass().getCanonicalName());
        }
    }
}
