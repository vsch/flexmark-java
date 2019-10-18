package com.vladsch.flexmark.test.spec;

import java.io.InputStream;

public interface TemplateReaderFactory {
    TemplateReader create(InputStream inputStream);
}
