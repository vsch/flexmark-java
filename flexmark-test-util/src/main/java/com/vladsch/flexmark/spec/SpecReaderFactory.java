package com.vladsch.flexmark.spec;

import java.io.InputStream;

public interface SpecReaderFactory {
    SpecReader create(InputStream inputStream, final String fileUrl);
}
