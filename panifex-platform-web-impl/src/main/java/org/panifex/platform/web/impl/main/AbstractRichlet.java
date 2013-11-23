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
package org.panifex.platform.web.impl.main;

import org.panifex.platform.module.api.sidebar.SidebarItem;
import org.panifex.platform.web.impl.sidebar.SidebarCommandTemplate;
import org.panifex.platform.web.impl.sidebar.SidebarNodeTemplate;
import org.zkoss.bind.Binder;
import org.zkoss.bind.DefaultBinder;
import org.zkoss.text.MessageFormats;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.sys.PageCtrl;
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zul.A;
import org.zkoss.zul.Div;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Script;

/**
 * The abstract richlet which creates core layout.
 * 
 */
public abstract class AbstractRichlet extends GenericRichlet {

    private Binder binder;

    @Override
    public final void service(Page page) throws Exception {
        PageCtrl pageCtrl = (PageCtrl) page;
        pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/bootstrap/css/bootstrap.min.css\"/>");
        pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/content-header.css.dsp\"/>");
        pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/index.css.dsp\"/>");
        pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/login.css.dsp\"/>");
        pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/grey.css.dsp\"/>");
        pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/sidebar.css.dsp\"/>");
        pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/user-nav.css.dsp\"/>");

        page.setTitle(Labels.getLabel("application.name"));

        final Div main = new Div();

        // initialize Binder
        binder = new DefaultBinder();
        binder.init(main, getViewModel(), null);
        main.setAttribute("vm", binder.getViewModel());

        // create components
        createComponents(main);
        
        // bind form events
        binder.addCommandBinding(main, Events.ON_BOOKMARK_CHANGE, "'onBookmarkChange'", null);

        Script s = new Script();
        s.setSrc("../css/bootstrap/js/bootstrap.min.js");
        s.setParent(main);

        main.setPage(page);
        binder.loadComponent(main, true);

    }

    private void createComponents(Component main) {
        main.appendChild(createLogo());
        main.appendChild(createUserNav());
        main.appendChild(createSidebar());
        main.appendChild(createContent());
    }

    private Component createLogo() {

        final Div header = new Div();
        header.setSclass("header");

        final Div fill = new Div();
        fill.setSclass("fill");
        header.appendChild(fill);

        final A logo = new A();
        logo.setHref(Labels.getLabel("application.url"));
        logo.setImage("../img/panifex_top_logo.png");
        fill.appendChild(logo);

        return header;
    }

    private Component createUserNav() {
        final Div userNav = new Div();
        userNav.setSclass("user-nav");

        final Menubar menubar = new Menubar();
        menubar.setParent(userNav);

        final Menuitem logout = new Menuitem(getLabel("main.form.button.logout.label"));
        logout.setIconSclass("icon icon-white icon-share-alt");
        logout.setParent(menubar);
        binder.addCommandBinding(logout, Events.ON_CLICK, "'logout'", null);
        binder.addPropertyLoadBindings(logout, "visible", "vm.isUserLoggedIn", null, null, null,
                null, null);

        return userNav;
    }

    private Component createSidebar() {
        final Div sidebar = new Div();
        sidebar.setSclass("sidebar");

        final Navbar navBar = new Navbar();
        binder.addChildrenLoadBindings(navBar, "vm.sidebarItems", null, null, null, null, null);
        binder.setTemplate(navBar, "$CHILDREN$", "each.type", null);
        navBar.setTemplate(SidebarItem.COMMAND, new SidebarCommandTemplate(binder));
        navBar.setTemplate(SidebarItem.NODE, new SidebarNodeTemplate(binder));
        navBar.setParent(sidebar);

        return sidebar;
    }

    protected abstract Component createContent();

    protected abstract AbstractVM getViewModel();

    /**
     * Returns the label of the specified key based on the current Locale, or null if not found.
     * 
     * <p>
     * The current locale is given by {@link org.zkoss.util.Locales#getCurrent}.
     * 
     * @see #getSegmentedLabels
     * 
     * @return the label of the specified key based on the current Locale, or null if not found
     */
    protected String getLabel(String key) {
        return Labels.getLabel(key);
    }

    /**
     * Returns the label of the specified key and formats it with the specified argument, or null if
     * not found.
     * 
     * <p>
     * It first uses {@link #getLabel(String)} to load the label. Then, it, if not null, invokes
     * {@link MessageFormats#format} to format it.
     * 
     * <p>
     * The current locale is given by {@link org.zkoss.util.Locales#getCurrent}.
     * 
     * @return the label of the specified key based on the current Locale, or null if not found
     */
    protected String getLabel(String key, Object[] args) {
        return Labels.getLabel(key, args);
    }

    /**
     * Returns the root binder.
     * 
     * @return the root binder
     */
    protected Binder getBinder() {
        return binder;
    }

}
