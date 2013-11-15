package org.panifex.platform.web.impl.sidebar;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.Binder;
import org.zkoss.xel.VariableResolver;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zkmax.zul.Navitem;

public class SidebarTemplate implements Template {

	private Logger log = LoggerFactory.getLogger(SidebarTemplate.class);
	
	private Binder binder;
	
	public SidebarTemplate(Binder binder) {
		this.binder = binder;
	}
	
	@Override
	public Component[] create(Component parent, Component insertBefore,
			VariableResolver resolver, Composer composer) {

		final Navitem navItem = new Navitem();
		binder.addPropertyLoadBindings(navItem, "label", "item.label", null, null, null, null, null);
		binder.addPropertyLoadBindings(navItem, "iconSclass", "item.iconSclass", null, null, null, null, null);
		
		// append to the parent
		navItem.setParent(parent);
		
		Component[] components = new Component[1];
		components[0] = navItem;
		
		return components;
	}

	@Override
	public Map<String, Object> getParameters() {
		Map<String,Object> parameters = new HashMap<>();
		//set binding variable
		parameters.put("var","item");
		
		return parameters;
	}

}
