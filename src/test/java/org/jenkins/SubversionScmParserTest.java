package org.jenkins;

import static org.junit.Assert.*;
import hudson.scm.SCM;
import hudson.scm.SubversionSCM;

import org.junit.Test;

/**
 * FIXME: Document type SubversionScmParserTest.
 *
 * @author Ulli Hafner
 */
public class SubversionScmParserTest {
    @Test
    public void testScmConnectionAccepts() {
        SubversionScmParser parser = new SubversionScmParser();

        assertTrue(parser.accepts("scm:svn:https://faktorlogik.de:18080/svn/faktorlogik/trunk/${project.artifactId}"));
        assertFalse(parser.accepts("scm:git:bla"));
    }

    @Test
    public void testScmConnectionCreation() {
        SubversionScmParser parser = new SubversionScmParser();

        SCM scm = parser.parse("scm:svn:https://faktorlogik.de:18080/svn/faktorlogik/trunk/${project.artifactId}");
        assertNotNull(scm);

        assertEquals(scm.getClass(), SubversionSCM.class);
    }
}