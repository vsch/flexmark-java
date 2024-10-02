package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.util.spec.TemplateEntry;
import com.vladsch.flexmark.test.util.spec.TemplateReader;
import com.vladsch.flexmark.test.util.spec.TemplateReaderFactory;
import java.io.InputStream;
import org.junit.Test;

public abstract class TemplateTestCase implements TemplateReaderFactory {
  public static final String SPEC_RESOURCE = "/template.txt";

  private DumpTemplateReader dumpTemplateReader;

  @Override
  public TemplateReader create(InputStream inputStream) {
    dumpTemplateReader = new DumpTemplateReader(inputStream, this);
    return dumpTemplateReader;
  }

  public abstract void getExpandedEntry(TemplateEntry entry, StringBuilder sb);

  protected void processTemplate(String template, String expandedTemplate) {}

  /**
   * @return return resource name for the spec to use for the examples of the test
   */
  protected abstract String getTemplateResourceName();

  /**
   * @return return true if template to be dumped to stdout
   */
  protected boolean outputTemplate() {
    return true;
  }

  @Test
  public void testDumpSpec() {
    String specResourcePath = getTemplateResourceName();
    TemplateReader.readEntries(specResourcePath, this);
    String fullSpec = TemplateReader.readSpec(specResourcePath);
    String actual = dumpTemplateReader.getTemplate();
    processTemplate(fullSpec, actual);
  }
}
