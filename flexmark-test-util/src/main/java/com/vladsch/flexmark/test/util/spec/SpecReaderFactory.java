package com.vladsch.flexmark.test.util.spec;

import java.io.InputStream;
import org.jetbrains.annotations.NotNull;

public interface SpecReaderFactory<S extends SpecReader> {
    @NotNull S create(@NotNull InputStream inputStream, @NotNull ResourceLocation location);
}
