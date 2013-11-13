package org.panifex.platform.web.impl.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;

public class MainRichlet extends AbstractRichlet {

	private Logger log = LoggerFactory.getLogger(MainRichlet.class);

	@Override
	protected Component createContent() {
		log.debug("Create content");
		final Div content = new Div();
		return content;
	
	}

	@Override
	protected AbstractVM getViewModel() {
		return new MainVM();
	}

}
