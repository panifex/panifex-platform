package org.panifex.platform.web.impl.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.bind.annotation.Init;

@Init(superclass = true)
public class MainVM extends AbstractVM {

	private Logger log = LoggerFactory.getLogger(MainVM.class);
	
	@Override
	protected Logger getLogger() {
		return log;
	}

}
