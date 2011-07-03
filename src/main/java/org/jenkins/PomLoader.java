package org.jenkins;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.maven.model.io.DefaultModelReader;
import org.apache.maven.model.io.ModelParseException;
import org.apache.maven.model.io.ModelReader;
import org.apache.maven.project.MavenProject;


public abstract class PomLoader {

    public final MavenProject load(String url) {
        InputStream input = null;
        try {
            ModelReader modelReader = createModelReader();
            try {
                input = doOpenPom(url);
                return readMavenProject(modelReader, input);
            } catch (ModelParseException e) {
                throw new PomLoaderException("Could not parse POM file: " + e, e);
            } catch (IOException e) {
                throw new PomLoaderException("Could not read POM file: " + e, e);
            }
        } finally {
            if (input != null) {
                IOUtils.closeQuietly(input);
            }
        }
    }

    protected MavenProject readMavenProject(ModelReader modelReader, InputStream input) throws IOException, ModelParseException {
        return new MavenProject(modelReader.read(input, null));
    }

    protected DefaultModelReader createModelReader() {
        return new DefaultModelReader();
    }

    protected abstract InputStream doOpenPom(String location) throws IOException;
}
