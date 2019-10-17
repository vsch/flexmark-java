package com.vladsch.flexmark.spec;

import org.jetbrains.annotations.NotNull;

public interface FileUrlProvider {
    @NotNull String getFileUrl(int lineNumber);

    FileUrlProvider NULL = (int lineNumber) -> "";
}
