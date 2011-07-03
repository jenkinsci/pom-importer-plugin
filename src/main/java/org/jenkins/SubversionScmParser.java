package org.jenkins;

import hudson.Extension;
import hudson.scm.SCM;
import hudson.scm.SubversionSCM;

import org.apache.commons.lang.StringUtils;

/**
 * {@link ScmUrlParser} that can handle Subversion URLs.
 *
 * @author Eric Weikl
 * @author Ulli Hafner
 */
@Extension
public class SubversionScmParser implements ScmUrlParser {

    public boolean accepts(final String connection) {
        return connection.toLowerCase().startsWith("scm:svn:");
    }

    public SCM parse(final String connection) {
        if (!accepts(connection)) {
            throw new IllegalArgumentException("URL '" + connection + "' is not a Subversion URL");
        }
        String url = StringUtils.substringAfter(connection, "scm:svn:");
        return new SubversionSCM(url);
    }
}

