package org.panifex.module.api;

/**
 * Creates all necessary components for a given page in response to user's
 * request.<p/>
 */
public interface Pagelet<Config, Request> {

    void init(Config config);

    void service(Request request);
}
