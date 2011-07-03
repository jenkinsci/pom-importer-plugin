package org.jenkins;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class PomLoaders {

    public static PomLoader forUrl(String url) {
        if (url.startsWith("http")) {
            return new HttpLoader();
        }
        if (url.startsWith("svn")) {
            throw new UnsupportedOperationException("svn support not implemented yet");
        }
        if (url.startsWith("git")) {
            throw new UnsupportedOperationException("git support not implemented yet");
        }
        return new FileLoader();
    }

    private PomLoaders() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static class HttpLoader extends PomLoader {

        @Override
        public InputStream doOpenPom(String location) throws IOException {
            // TODO: use HTTP Client or whatever Jenkins might provide
            return new URL(location).openStream();
        }
    }

    public static class FileLoader extends PomLoader {

        @Override
        public InputStream doOpenPom(String location) throws IOException {
            return new BufferedInputStream(new FileInputStream(location));
        }
    }
}
