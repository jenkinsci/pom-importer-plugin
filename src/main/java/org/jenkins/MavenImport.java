package org.jenkins;

import hudson.maven.MavenModuleSet;
import hudson.model.Hudson;
import hudson.scm.SCM;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import jenkins.model.Jenkins;

import org.apache.maven.project.MavenProject;

/**
 * Proof-of-Concept how to create a pre-configured Maven job.
 *
 * @author Eric Weikl
 * @author Ulli Hafner
 */
public class MavenImport {

    public void importPom(final String pom) throws IOException {
        MavenModuleSet mavenJob = createMavenJob(pom);
        PomLoader loader = getLoader(pom);
        MavenProject project = loader.load(pom);
        String scmConnection = project.getScm().getConnection();
        mavenJob.setScm(mapToScm(scmConnection));
        mavenJob.setDescription(project.getDescription());

        mavenJob.save();
    }

    protected PomLoader getLoader(final String pom) {
        return PomLoaders.forUrl(pom);
    }

    private SCM mapToScm(final String connection) {
        Collection<ScmUrlParser> parsers = getAllScmParsers();
        for (ScmUrlParser parser : parsers) {
            if (parser.accepts(connection)) {
                return parser.parse(connection);
            }
        }
        return null;
    }

    protected MavenModuleSet createMavenJob(final String pom) throws IOException {
        return Jenkins.getInstance().createProject(MavenModuleSet.class, pom);
    }

    private Collection<ScmUrlParser> getAllScmParsers() {
        Hudson instance = Hudson.getInstance();
        if (instance == null) {
            return Collections.emptyList();
        }
        return instance.getExtensionList(ScmUrlParser.class);
    }
}
