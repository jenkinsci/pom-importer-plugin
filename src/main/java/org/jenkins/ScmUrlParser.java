package org.jenkins;

import hudson.ExtensionPoint;
import hudson.scm.SCM;

/**
 * FIXME: Document type ScmUrlParser.
 *
 * @author Ulli Hafner
 */
public interface ScmUrlParser extends ExtensionPoint {

    /**
     * FIXME: Document method accepts
     * @param connection
     * @return
     */
    boolean accepts(String connection);

    /**
     * FIXME: Document method parse
     * @param connection
     * @return
     */
    SCM parse(String connection);

}

