package org.jenkins;

import hudson.maven.MavenModuleSet;
import hudson.model.Hudson;
import hudson.scm.SCM;

import java.io.IOException;
import java.util.Collection;

import org.apache.maven.project.MavenProject;

import com.google.common.collect.Lists;

/**
 * FIXME: Document type MavenImport.
 *
 * @author Eric Weikl
 * @author Ulli Hafner
 */
public class MavenImport {
    public void importPom(final String pom) throws IOException {
        // read pom
        MavenProject project = new MavenProject();
        // create Jenkins Job

        MavenModuleSet mavenJob = Hudson.getInstance().createProject(MavenModuleSet.class, pom);
        mavenJob.setDescription(project.getDescription());

//        Scm scm = project.getScm();
//        String connection = scm.getConnection();
        SCM jenkinsScm = createScm("scm:svn:https://faktorlogik.de:18080/svn/faktorlogik/trunk/${project.artifactId}");
        if (jenkinsScm != null) {
            mavenJob.setScm(jenkinsScm);
        }
        mavenJob.save();
    }

    private SCM createScm(final String connection) {
        Collection<ScmUrlParser> parsers = getAllScmParsers();
        for (ScmUrlParser parser : parsers) {
            if (parser.accepts(connection)) {
                return parser.parse(connection);
            }
        }
        return null;
    }

    private Collection<ScmUrlParser> getAllScmParsers() {
        Hudson instance = Hudson.getInstance();
        if (instance == null) {
            return Lists.newArrayList();
        }
        return instance.getExtensionList(ScmUrlParser.class);
    }
}

