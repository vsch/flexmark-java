package com.vladsch.flexmark.spec;

import java.net.URL;

public class UrlString {
    private final String fileUrl;

    public UrlString(final String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public UrlString(final URL fileUrl) {
        this.fileUrl = adjustedFileUrl(fileUrl);
    }

    public UrlString(final URL fileUrl, int lineNumber) {
        this.fileUrl = fileUrlWithLineNumber(fileUrl, lineNumber);
    }

    @Override
    public String toString() {
        return fileUrl;
    }

    public static String adjustedFileUrl(final URL url) {
        String externalForm = url.toExternalForm();
        return externalForm.startsWith("file:/") ? "file://" + externalForm.substring("file:".length()).replace("/target/test-classes/","/src/test/resources/") : externalForm;
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
