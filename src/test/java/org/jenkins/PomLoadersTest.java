package org.jenkins;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.InputStream;

import org.apache.maven.project.MavenProject;
import org.jenkins.PomLoaders.FileLoader;
import org.jenkins.PomLoaders.HttpLoader;
import org.junit.Test;


public class PomLoadersTest {

    @Test
    public void shouldReturnFileLoader() throws Exception {
        PomLoader loader = PomLoaders.forUrl("/home/example/pom.xml");
        assertThat(loader, is(notNullValue()));
        assertThat(loader, is(instanceOf(FileLoader.class)));
    }

    @Test
    public void shouldReturnHttpLoader() throws Exception {
        PomLoader loader = PomLoaders.forUrl("http://svn.example.com/pom.xml");
        assertThat(loader, is(notNullValue()));
        assertThat(loader, is(instanceOf(HttpLoader.class)));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void svnUrlsCurrentlyUnsupported() throws Exception {
        PomLoaders.forUrl("svn://svn.example.com/pom.xml");
    }

    @Test
    public void shouldLoadPomFromClasspath() throws Exception {
        MavenProject pom = new ClassPathLoader().load("svn-pom.xml");
        assertPopulated(pom);
    }

    @Test
    public void shouldLoadPomFromFile() throws Exception {
        String absolutePath = Thread.currentThread().getContextClassLoader().getResource("svn-pom.xml").getFile();
        MavenProject pom = new FileLoader().load(absolutePath);
        assertPopulated(pom);
    }

    private void assertPopulated(MavenProject pom) {
        assertThat(pom.getGroupId(), is("com.example"));
        assertThat(pom.getArtifactId(), is("my-project"));
        assertThat(pom.getName(), is("My Example Project"));
        assertThat(pom.getDescription(), is("This is an example project."));
        assertThat(pom.getScm().getConnection(), is("scm:svn:http://svn.example.com/myproject/trunk"));
        assertThat(pom.getScm().getDeveloperConnection(), is("scm:svn:http://svn.example.com/myproject/trunk"));
    }


    private final class ClassPathLoader extends PomLoader {

        @Override
        protected InputStream doOpenPom(String location) {
            return Thread.currentThread().getContextClassLoader().getResourceAsStream(location);
        }
    }
}