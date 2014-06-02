package org.panifex.web.shiro.itest;

import org.ops4j.pax.web.service.spi.WebEvent;
import org.ops4j.pax.web.service.spi.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebListenerImpl implements WebListener {

    protected Logger log = LoggerFactory.getLogger(getClass());

    private boolean event;

    @Override
    public void webEvent(WebEvent webEvent) {
        log.info("Got event: " + webEvent);
        if (webEvent.getType() == WebEvent.DEPLOYED) {
            event = true;
        } else if (webEvent.getType() == WebEvent.UNDEPLOYED) {
            event = false;
        }
    }

    public boolean gotEvent() {
        return event;
    }

}
