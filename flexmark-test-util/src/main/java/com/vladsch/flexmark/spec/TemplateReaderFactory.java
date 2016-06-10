package com.vladsch.flexmark.spec;

import java.io.InputStream;

public interface TemplateReaderFactory {
    TemplateReader create(InputStream inputStream);
}
