package org.jenkins;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import hudson.scm.SCM;
import hudson.scm.SubversionSCM;

import org.junit.Test;


public class SubversionScmParserTest {

    private SubversionScmParser parser = new SubversionScmParser();
    private String svnUrl = "scm:svn:https://example.com/svn/trunk";

    @Test
    public void shouldAcceptSvnUrls() {
        assertThat(parser.accepts(svnUrl), is(true));
    }

    @Test
    public void shouldNotAcceptNonSvnUrls() throws Exception {
        assertThat(parser.accepts("scm:git:bla"), is(false));
    }

    @Test
    public void shouldCreateNewSubversionSCMInstance() {
        SCM scm = parser.parse(svnUrl);
        assertThat(scm, is(notNullValue()));
        assertThat(scm, is(instanceOf(SubversionSCM.class)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailIfUnsupportedFormat() throws Exception {
        parser.parse("scm:git:bla");
    }
}