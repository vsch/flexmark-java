package com.vladsch.flexmark.test.util;

import static org.junit.Assert.assertEquals;

import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecReader;
import org.junit.Test;

public abstract class FullSpecTestCase extends RenderingTestCase {

  public DumpSpecReader create(ResourceLocation location) {
    return SpecReader.create(
        location,
        (stream, fileUrl) -> new DumpSpecReader(stream, this, fileUrl, compoundSections()));
  }

  protected boolean compoundSections() {
    return false;
  }

  protected abstract ResourceLocation getSpecResourceLocation();

  protected void fullTestSpecStarting() {}

  protected void fullTestSpecComplete() {}

  @Test
  public void testSpecExample() {
    ResourceLocation location = getSpecResourceLocation();
    if (location.isNull()) {
      return;
    }

    fullTestSpecStarting();
    DumpSpecReader reader = create(location);
    reader.readExamples();
    fullTestSpecComplete();

    String actual = reader.getFullSpec();
    String expected = reader.getExpectedFullSpec();

    if (!reader.getFileUrl().isEmpty()) {
      assertEquals(reader.getFileUrl(), expected, actual);
    } else {
      assertEquals(expected, actual);
    }
  }
}
