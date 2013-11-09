package org.panifex.platform.web.servlet;

import java.util.Map;

import org.apache.aries.blueprint.annotation.Bean;
import org.zkoss.zk.ui.Richlet;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;
import org.zkoss.zk.ui.http.WebManager;
import org.zkoss.zk.ui.util.Configuration;

@Bean(id = ZkLayoutServiceImpl.ID)
public class ZkLayoutServiceImpl extends DHtmlLayoutServlet implements ZkLayoutService {

	public final static String ID = "org.panifex.platform.web.servlet.ZkLayoutServlet";
	
	/**
	 * Generated serial ID
	 */
	private static final long serialVersionUID = 4082554202918040779L;

	/**
	 * Gets Zk framework configuration which can be used for registering
	 * richlets, etc.
	 * 
	 * @return Zk framework configuration
	 */
	private Configuration getConfiguration() {
		return WebManager.getWebManager(getServletContext()).getWebApp().getConfiguration();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Object addRichlet(Class<? extends Richlet> richlet, String path) {
		return addRichlet(richlet, path, null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Object addRichlet(Class<? extends Richlet> richlet, String path, Map<String, String> params) {
		// get the configuration
		Configuration config = getConfiguration();
		
		// register Richlet
		String richletName = richlet.getName();
		Object previousRichlet = config.addRichlet(richletName, richletName, params);
		config.addRichletMapping(richletName, path);
		
		return previousRichlet;
	}
}
