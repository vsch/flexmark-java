package com.vladsch.flexmark.test.spec;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;

public interface SpecReaderFactory<S extends SpecReader> {
    @NotNull S create(@NotNull InputStream inputStream, @Nullable String fileUrl);
}
