package org.jenkins;

import hudson.Extension;
import hudson.scm.SCM;
import hudson.scm.SubversionSCM;

import org.apache.commons.lang.StringUtils;

/**
 * FIXME: Document type SubversionScmParser.
 *
 * @author Ulli Hafner
 */
@Extension
public class SubversionScmParser implements ScmUrlParser {
    /** {@inheritDoc} */
    public boolean accepts(final String connection) {
        return StringUtils.contains(connection, "scm:svn:");
    }

    /** {@inheritDoc} */
    public SCM parse(final String connection) {
        String url = StringUtils.substringAfter(connection, "scm:svn:");
        return new SubversionSCM(url);
    }

}

