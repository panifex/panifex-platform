package org.panifex.web.impl.view.settings;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.panifex.module.api.settings.SettingsContent;
import org.panifex.test.support.TestSupport;
import org.panifex.web.impl.settings.SettingsContentManager;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

/**
 * Unit tests for {@link SettingsContentView} class.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
    Labels.class,
    SettingsContentManager.class,
    SettingsContentView.class})
public class SettingsContentViewTest extends TestSupport {

    private SettingsContentView view = new SettingsContentView();

    @Before
    public void setUp() {
        mockStatic(Labels.class);
        mockStatic(SettingsContentManager.class);
    }

    @Test
    public void testCreateTabboxInCaseSettingContentThrowsException() throws Exception {
        // mocks
        Component body = createMock(Component.class);
        SettingsContent content1 = createMock(SettingsContent.class);
        SettingsContent content2 = createMock(SettingsContent.class);

        // variables
        List<SettingsContent> contents = new ArrayList<>();
        contents.add(content1);
        contents.add(content2);
        String content1Title = getRandomChars(20);
        String content2Title = getRandomChars(20);
        String content1Label = getRandomChars(20);
        String content2Label = getRandomChars(20);

        // expect getting active settings contents
        expect(SettingsContentManager.getContents()).andReturn(contents);

        // expect creating parent div
        Div div = createMockAndExpectNew(Div.class);
        expect(body.appendChild(div)).andReturn(true);

        // expect creating tab box
        Tabbox tabbox = createMockAndExpectNew(Tabbox.class);
        expect(div.appendChild(tabbox)).andReturn(true);

        // expect creating tabs
        Tabs tabs = createMockAndExpectNew(Tabs.class);
        expect(tabbox.appendChild(tabs)).andReturn(true);

        // expect creating tabpanels
        Tabpanels tabpanels = createMockAndExpectNew(Tabpanels.class);
        expect(tabbox.appendChild(tabpanels)).andReturn(true);

        // expect getting internationalized labels
        expect(content1.getTitle()).andReturn(content1Title);
        expect(content2.getTitle()).andReturn(content2Title);
        expect(Labels.getLabel(content1Title)).andReturn(content1Label);
        expect(Labels.getLabel(content2Title)).andReturn(content2Label);

        // expect creating tabs
        Tab tab1 = createMockAndExpectNew(Tab.class, content1Label);
        expect(tabs.appendChild(tab1)).andReturn(true);
        Tab tab2 = createMockAndExpectNew(Tab.class, content2Label);
        expect(tabs.appendChild(tab2)).andReturn(true);

        // expect creating tab panels
        Tabpanel panel1 = createMockAndExpectNew(Tabpanel.class);
        expect(tabpanels.appendChild(panel1)).andReturn(true);
        Tabpanel panel2 = createMockAndExpectNew(Tabpanel.class);
        expect(tabpanels.appendChild(panel2)).andReturn(true);

        // expect creating content bodies
        content1.createBody(panel1);
        expectLastCall().andThrow(new RuntimeException());

        content2.createBody(panel2);

        // perform test
        replayAll();
        view.createBody(body);
        verifyAll();
    }
}
