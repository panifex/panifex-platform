package org.panifex.platform.web.impl.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.HtmlNativeComponent;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.sys.PageCtrl;
import org.zkoss.zul.A;
import org.zkoss.zul.Div;
import org.zkoss.zul.Style;

public class LoginRichlet extends GenericRichlet {

	private Logger log = LoggerFactory.getLogger(LoginRichlet.class);
	
	@Override
	public void service(Page page) throws Exception {
		log.debug("Creating login page");
		PageCtrl pageCtrl = (PageCtrl) page;
		pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/index.css.dsp\"/>");
		
		page.setTitle("Hello Mario!!!");
		final Div body = new Div();
		
		final Div header = new Div();
		header.setSclass("header");
		header.setParent(body);
		HtmlNativeComponent h1 = new HtmlNativeComponent("h1");
		h1.setParent(header);
		A a = new A("admin1");
		a.setParent(h1);
		body.setPage(page);
	}

}
 