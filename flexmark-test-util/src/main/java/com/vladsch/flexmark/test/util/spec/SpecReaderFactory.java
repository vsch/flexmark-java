package com.vladsch.flexmark.test.util.spec;

import java.io.InputStream;

public interface SpecReaderFactory<S extends SpecReader> {

  S create(InputStream inputStream, ResourceLocation location);
}
