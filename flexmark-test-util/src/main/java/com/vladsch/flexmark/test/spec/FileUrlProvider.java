package com.vladsch.flexmark.test.spec;

import org.jetbrains.annotations.NotNull;

public interface FileUrlProvider {
    @NotNull String getFileUrl(int lineNumber);

    FileUrlProvider NULL = (int lineNumber) -> "";
}
