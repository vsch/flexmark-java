package com.vladsch.flexmark.spec;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;

public interface SpecReaderFactory {
    @NotNull SpecReader create(@NotNull InputStream inputStream, @Nullable String fileUrl);
}
