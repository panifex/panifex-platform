package org.panifex.platform.web.impl.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;

public class LoginRichlet extends GenericRichlet {

	private Logger log = LoggerFactory.getLogger(LoginRichlet.class);
	
	@Override
	public void service(Page page) throws Exception {
		page.setTitle("Hello Mario!!!");
	}

}
 