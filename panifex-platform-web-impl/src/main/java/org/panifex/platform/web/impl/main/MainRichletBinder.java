package org.panifex.platform.web.impl.main;

import org.apache.aries.blueprint.annotation.Bean;
import org.apache.aries.blueprint.annotation.Bind;
import org.apache.aries.blueprint.annotation.Inject;
import org.apache.aries.blueprint.annotation.Reference;
import org.apache.aries.blueprint.annotation.ReferenceListener;
import org.apache.aries.blueprint.annotation.Unbind;
import org.panifex.platform.web.servlet.ZkLayoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Bean(id = MainRichletBinder.ID)
@ReferenceListener
public class MainRichletBinder {

	private Logger log = LoggerFactory.getLogger(MainRichletBinder.class);
	
	public final static String ID = "org.panifex.platform.web.impl.main.MainRichletBinder";
	
	@Inject
	@Reference(availability = "optional", serviceInterface = ZkLayoutService.class, referenceListeners = @ReferenceListener(ref = ID))
	private ZkLayoutService zkLayoutService;
	
	@Bind
	public void bind(ZkLayoutService zkLayoutService) {
		log.debug("Bind Zk layout service: {}", zkLayoutService);
		this.zkLayoutService = zkLayoutService;
		
		zkLayoutService.addRichlet(MainRichlet.class,  "/main");
	}
	
	@Unbind
	public void unbind(ZkLayoutService zkLayoutServlet) {
		this.zkLayoutService = null;
	}
}
