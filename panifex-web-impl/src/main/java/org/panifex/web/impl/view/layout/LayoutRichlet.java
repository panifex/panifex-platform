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
package org.panifex.web.impl.view.layout;

import org.panifex.module.api.menu.MenuItem;
import org.panifex.web.impl.i18n.ApplicationLabels;
import org.panifex.web.impl.i18n.SecurityLabels;
import org.panifex.web.impl.i18n.SettingsLabels;
import org.panifex.web.impl.menu.AppMenuConstants;
import org.panifex.web.impl.menu.MenuActionTemplate;
import org.panifex.web.impl.menu.MenuNodeTemplate;
import org.zkoss.bind.Binder;
import org.zkoss.bind.DefaultBinder;
import org.zkoss.bind.impl.ValidationMessagesImpl;
import org.zkoss.bind.sys.ValidationMessages;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
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
public abstract class LayoutRichlet extends GenericRichlet {

    /**
     * Contains the {@link java.lang.String String} constant in which is the view-model binded to the components.
     */
    public static final String VM_BIND_ID = "vm";

    // commands
    private static final String LOGOUT_COMMAND = "'" + LayoutVM.LOGOUT_COMMAND + "'";
    private static final String ON_SETTINGS_CLICK = "'" + LayoutVM.ON_SETTINGS_CLICK +"'";

    // attributes
    private static final String MENU_ITEMS_ATTR = VM_BIND_ID + "." + LayoutVM.MENU_ITEMS_ATTR;
    private static final String IS_USER_LOGGED_IN_ATTR = VM_BIND_ID + "." + LayoutVM.IS_USER_LOGGED_IN;

    private DefaultBinder binder;



    /**
     * Defines the attribute in which is the {@link org.zkoss.bind.sys.ValidationMessages ValidationMessages}
     * binded to the components.
     */
    protected static final String VALIDATION_MESSAGES = "vmsgs";

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
        pageCtrl.addAfterHeadTags("<link rel=\"shortcut icon\" href=\"../img/favicon.ico\"/>");

        page.setTitle(Labels.getLabel("application.name"));
        final Div main = new Div();

        main.setId("main");

        // initialize Binder
        binder = new DefaultBinder();
        binder.init(main, getViewModel(), null);
        main.setAttribute(VM_BIND_ID, binder.getViewModel());

        // set validation messages
        ValidationMessages vmessages = new ValidationMessagesImpl();
        binder.setValidationMessages(vmessages);
        main.setAttribute(VALIDATION_MESSAGES, vmessages);

        // create components
        createComponents(main);

        Script s = new Script();
        s.setSrc("../css/bootstrap/js/bootstrap.min.js");
        s.setParent(main);

        main.setPage(page);
        binder.loadComponent(main, true);
        Selectors.wireEventListeners(main, binder.getViewModel());
        Selectors.wireComponents(main, binder.getViewModel(), true);
    }

    private void createComponents(Component main) {
        createLogo(main);
        createUserNav(main);
        createAppMenu(main);
        createContent(main);
    }

    private void createLogo(Component body) {

        final Div header = new Div();
        header.setSclass("header");
        body.appendChild(header);

        final Div fill = new Div();
        fill.setSclass("fill");
        header.appendChild(fill);

        final A logo = new A();
        logo.setHref(Labels.getLabel(ApplicationLabels.APPLICATION_NAME));
        logo.setImage("../img/panifex_top_logo.png"); // TODO it have to be configurable
        fill.appendChild(logo);
    }

    private void createUserNav(Component body) {
        final Div userNav = new Div();
        userNav.setSclass("user-nav");
        body.appendChild(userNav);

        final Menubar menubar = new Menubar();
        menubar.setParent(userNav);

        addSettingsButton(menubar);
        addLogoutButton(menubar);
    }

    private void addSettingsButton(Menubar menubar) {
        final Menuitem settings = new Menuitem(Labels.getLabel(SettingsLabels.OPEN_SETTINGS_CONTENT_LABEL));
        settings.setId("usernav-settings");
        settings.setIconSclass("glyphicon glyphicon-cog");
        settings.setParent(menubar);
        binder.addCommandBinding(settings, Events.ON_CLICK, ON_SETTINGS_CLICK, null);
    }

    private void addLogoutButton(Menubar menubar) {
        final Menuitem logout = new Menuitem(Labels.getLabel(SecurityLabels.LOGOUT_ACTION_LABEL));
        logout.setId("usernav-logout");
        logout.setIconSclass("glyphicon glyphicon-share-alt");
        logout.setParent(menubar);
        binder.addCommandBinding(logout, Events.ON_CLICK, LOGOUT_COMMAND, null);
        binder.addPropertyLoadBindings(logout, "visible", IS_USER_LOGGED_IN_ATTR, null, null, null,
                null, null);
    }

    private void createAppMenu(Component body) {
        final Div sidebar = new Div();
        body.appendChild(sidebar);

        final Navbar navBar = new Navbar();
        navBar.setId("appmenu");
        navBar.setSclass("sidebar");
        navBar.setOrient("vertical");

        binder.addChildrenLoadBindings(navBar, MENU_ITEMS_ATTR, null, null, null, null, null);
        binder.setTemplate(navBar, "$CHILDREN$", AppMenuConstants.NODE_CHILDREN_CONDITION, null);
        navBar.setTemplate(MenuItem.ACTION, new MenuActionTemplate(binder));
        navBar.setTemplate(MenuItem.NODE, new MenuNodeTemplate(binder));
        navBar.setParent(sidebar);
    }

    protected abstract void createContent(Component parent);

    protected abstract LayoutVM getViewModel();

    /**
     * Returns the root binder.
     *
     * @return the root binder
     */
    protected Binder getBinder() {
        return binder;
    }

}
