package org.panifex.platform.web.impl.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.sys.PageCtrl;
import org.zkoss.zul.Div;

public class MainRichlet extends GenericRichlet {

	private Logger log = LoggerFactory.getLogger(MainRichlet.class);
	
	@Override
	public void service(Page page) throws Exception {
		log.debug("Creating main page");
		PageCtrl pageCtrl = (PageCtrl) page;
		pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/bootstrap/css/bootstrap.min.css\"/>");
		pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/index.css.dsp\"/>");
		pageCtrl.addAfterHeadTags("<link rel=\"stylesheet\" type=\"text/css\" href=\"/grey.css.dsp\"/>");
		
		page.setTitle("Hello Mario!!!");
		
		final Div main = new Div();
		
		main.setPage(page);
	}

}
