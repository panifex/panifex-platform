package org.panifex.platform.web.impl.sidebar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.ReferenceList;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.panifex.platform.module.api.sidebar.AbstractSidebarItem;
import org.panifex.platform.module.api.sidebar.Sidebar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sidebar manager builds a sidebar menu based on registered sidebar services.
 * 
 * @see Sidebar
 */
@Bean(id = SidebarManager.ID)
@ReferenceListener
public class SidebarManager {

    private Logger log = LoggerFactory.getLogger(SidebarManager.class);

    public final static String ID = "org.panifex.platform.web.impl.sidebar.SidebarManager";

    @Inject
    @ReferenceList(availability = "optional", serviceInterface = Sidebar.class, referenceListeners = @ReferenceListener(ref = ID))
    private Sidebar sidebar;

    private Set<Sidebar> sidebarItems = new HashSet<>();

    @Bind
    public void bind(Sidebar sidebar) {
        log.debug("Bind sidebar: {}", sidebar);
        sidebarItems.add(sidebar);
    }

    @Unbind
    public void unbind(Sidebar sidebar) {
        log.debug("Unbind sidebar: {}", sidebar);
        sidebarItems.remove(sidebar);
    }

    public List<AbstractSidebarItem> getSidebarItems() {

        List<AbstractSidebarItem> list = new ArrayList<>();

        return list;
    }

}
