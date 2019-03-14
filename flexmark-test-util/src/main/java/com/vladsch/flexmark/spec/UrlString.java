package com.vladsch.flexmark.spec;

import java.net.URL;

public class UrlString {
    public static final String TARGET_TEST_CLASSES = "/target/test-classes/";
    public static final String OUT_TEST = "/out/test/";
    public static final String FILE_PROTOCOL = "file://";
    public static final String TEST_RESOURCES = "/test/resources/";
    private final String fileUrl;

    public UrlString(final String fileUrl) {
        this(fileUrl, 0);
    }

    public UrlString(final String fileUrl, int lineNumber) {
        this.fileUrl = (lineNumber > 0) ? fileUrl + ":" + (lineNumber + 1) : fileUrl;
    }

    public UrlString(final URL fileUrl) {
        this(adjustedFileUrl(fileUrl));
    }

    public UrlString(final URL fileUrl, int lineNumber) {
        this(adjustedFileUrl(fileUrl), lineNumber);
    }

    @Override
    public String toString() {
        return fileUrl;
    }

    public static String adjustedFileUrl(final URL url) {
        String externalForm = url.toExternalForm();
        if (externalForm.startsWith("file:/")) {
            String noFileProtocol = externalForm.substring("file:".length());
            if (noFileProtocol.contains(TARGET_TEST_CLASSES)) {
                return noFileProtocol.replace(TARGET_TEST_CLASSES, "/src/test/resources/");
            } else {
                int pos = noFileProtocol.indexOf(OUT_TEST);
                if (pos > 0) {
                    int pathPos = noFileProtocol.indexOf("/", pos + OUT_TEST.length());
                    if (pathPos > 0) {
                        return noFileProtocol.substring(0, pos) + "/" + noFileProtocol.substring(pos + OUT_TEST.length(), pathPos) + "/src/test/resources/" + noFileProtocol.substring(pathPos + 1);
                    }
                }
            }
            return noFileProtocol;
        } else return externalForm;
    }

    public static String fileUrlWithLineNumber(URL fileUrl, int lineNumber) {
        if (fileUrl != null) {
            if (lineNumber > 0) {
                return adjustedFileUrl(fileUrl) + ":" + (lineNumber + 1);
            } else return adjustedFileUrl(fileUrl);
        } else {
            return null;
        }
    }
}
