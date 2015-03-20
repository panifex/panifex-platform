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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.itest.web.base.support.html.ButtonElement;
import org.panifex.itest.web.base.support.html.HTMLButtonToButtonAdapter;
import org.panifex.itest.web.base.support.html.HTMLInputToTextInputAdapter;
import org.panifex.itest.web.base.support.html.HtmlDivisionToButtonAdapter;
import org.panifex.itest.web.base.support.html.HtmlTextInputToTextInputAdapter;
import org.panifex.itest.web.base.support.html.TextInputElement;
import org.panifex.test.support.TestSupport;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLButtonElement;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLInputElement;

/**
 * Unit tests for {@link PageletTestSupport} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(SimplePageletTestSupport.class)
public class PageletTestSupportTest extends TestSupport {

    // mocks
    private final PageletTestHelper testHelperMock = createMock(PageletTestHelper.class);

    private final SimplePageletTestSupport testSupport =
            new SimplePageletTestSupport(testHelperMock);

    @Before
    public void setUp() {
        resetAll();
    }

    @Test
    public void testAdaptHTMLButtonElementToButtonElement() throws Exception {
        String elementId = getRandomChars(20);
        HTMLButtonElement htmlButtonElement = createMock(HTMLButtonElement.class);
        HtmlPage htmlPage = createMock(HtmlPage.class);

        // expect getting html element
        expect(testHelperMock.getHtmlElementById(htmlPage, elementId)).andReturn(htmlButtonElement);

        // expect creating adaptor
        HTMLButtonToButtonAdapter adapter = createMockAndExpectNew(
                HTMLButtonToButtonAdapter.class,
                htmlButtonElement);

        // perform test
        replayAll();
        ButtonElement actualButton = testSupport.getButtonElementById(htmlPage, elementId);
        verifyAll();

        assertEquals(adapter, actualButton);
    }

    @Test
    public void testAdaptHtmlDivisionToButtonElement() throws Exception {
        String elementId = getRandomChars(20);
        HtmlDivision htmlDivision = createMock(HtmlDivision.class);
        HtmlPage htmlPage = createMock(HtmlPage.class);

        // expect getting html element
        expect(testHelperMock.getHtmlElementById(htmlPage, elementId)).andReturn(htmlDivision);

        // expect creating adaptor
        HtmlDivisionToButtonAdapter adapter = createMockAndExpectNew(
                HtmlDivisionToButtonAdapter.class,
                htmlDivision);

        // perform test
        replayAll();
        ButtonElement actualButton = testSupport.getButtonElementById(htmlPage, elementId);
        verifyAll();

        assertEquals(adapter, actualButton);
    }

    @Test
    public void testAdaptHTMLInputElementToTextInputElement() throws Exception {
        String elementId = getRandomChars(20);
        HTMLInputElement htmlInput = createMock(HTMLInputElement.class);
        HtmlPage htmlPage = createMock(HtmlPage.class);

        // expect getting html element
        expect(testHelperMock.getHtmlElementById(htmlPage, elementId)).andReturn(htmlInput);

        // expect creating adaptor
        HTMLInputToTextInputAdapter adapter = createMockAndExpectNew(
                HTMLInputToTextInputAdapter.class,
                htmlInput);

        // perform test
        replayAll();
        TextInputElement actualTextInput = testSupport.getTextInputElementById(htmlPage, elementId);
        verifyAll();

        assertEquals(adapter, actualTextInput);
    }

    @Test
    public void testAdaptHtmlTextInputToTextInputElement() throws Exception {
        String elementId = getRandomChars(20);
        HtmlTextInput htmlTextInput = createMock(HtmlTextInput.class);
        HtmlPage htmlPage = createMock(HtmlPage.class);

        // expect getting html element
        expect(testHelperMock.getHtmlElementById(htmlPage, elementId)).andReturn(htmlTextInput);

        // expect creating adaptor
        HtmlTextInputToTextInputAdapter adapter = createMockAndExpectNew(
                HtmlTextInputToTextInputAdapter.class,
                htmlTextInput);

        // perform test
        replayAll();
        TextInputElement actualTextInput = testSupport.getTextInputElementById(htmlPage, elementId);
        verifyAll();

        assertEquals(adapter, actualTextInput);
    }
}
