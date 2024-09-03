package com.vladsch.flexmark.test.util.spec;

import java.io.InputStream;

public interface TemplateReaderFactory {
  TemplateReader create(InputStream inputStream);
}
