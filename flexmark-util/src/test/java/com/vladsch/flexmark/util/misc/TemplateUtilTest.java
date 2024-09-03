package com.vladsch.flexmark.util.misc;

import static org.junit.Assert.assertEquals;

import java.util.regex.Pattern;
import org.junit.Test;

public class TemplateUtilTest {
  @Test
  public void test_resolveRefs() {
    Pattern pattern = Pattern.compile("\\$\\{([a-zA-Z_$][a-zA-Z_0-9$]+)}");
    TemplateUtil.MappedResolver resolver = new TemplateUtil.MappedResolver();

    resolver.set("FILE1", "/Users/name/home/file.ext");
    resolver.set("FILE2", "C:\\Users\\name\\home\\file.ext");
    resolver.set("FILE3", "C:\\Users\\name\\home\\$file.ext");

    assertEquals(
        "/Users/name/home/file.ext\nC:\\Users\\name\\home\\file.ext\nC:\\Users\\name\\home\\$file.ext",
        TemplateUtil.resolveRefs("${FILE1}\n${FILE2}\n${FILE3}", pattern, resolver));
  }
}
