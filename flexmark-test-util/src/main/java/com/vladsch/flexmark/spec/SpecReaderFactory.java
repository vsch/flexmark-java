package com.vladsch.flexmark.spec;

import java.io.InputStream;
import java.net.URL;

public interface SpecReaderFactory {
    SpecReader create(InputStream inputStream, final URL fileUrl);
}
