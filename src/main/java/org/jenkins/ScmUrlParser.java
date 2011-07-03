package org.jenkins;

import hudson.ExtensionPoint;
import hudson.scm.SCM;

/**
 * Strategy interface for parsing Maven SCM URLs. Extensible, so you can add
 * your own implementations.
 *
 * @author Eric Weikl
 * @author Ulli Hafner
 */
public interface ScmUrlParser extends ExtensionPoint {

    /**
     * Indicates this implementation supports the provided URL format. The provided
     * {@code connection} can be empty, but will never be {@code null}.
     *
     * @param connection the Maven SCM URL, e.g. {@code scm:svn:http://example.com/trunk}
     * @return {@code true} if this implementation supports the format, otherwise {@code false}
     */
    boolean accepts(String connection);

    /**
     * Creates a new {@link SCM} instance using the given Maven SVN connection URL.
     * Implementations should throw an {@link IllegalArgumentException} if the URL
     * cannot be interpreted. The provided {@code connection} can be empty, but
     * will never be {@code null}.
     *
     * @param connection the Maven SCM URL, e.g. {@code scm:svn:http://example.com/trunk}
     * @return a new {@link SCM} instance
     * @throws IllegalArgumentException if the format is illegal
     */
    SCM parse(String connection) throws IllegalArgumentException;

}

