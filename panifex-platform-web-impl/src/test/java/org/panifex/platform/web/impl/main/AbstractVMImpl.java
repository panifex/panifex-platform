package org.panifex.platform.web.impl.main;

import org.panifex.platform.web.impl.main.AbstractVM;
import org.slf4j.Logger;

public class AbstractVMImpl extends AbstractVM {

    private final Logger log;

    AbstractVMImpl(Logger log) {
        this.log = log;
    }

    @Override
    protected Logger getLogger() {
        return log;
    }
}
