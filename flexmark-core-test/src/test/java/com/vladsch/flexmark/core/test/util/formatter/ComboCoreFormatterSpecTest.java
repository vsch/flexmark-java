package com.vladsch.flexmark.core.test.util.formatter;

import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

public class ComboCoreFormatterSpecTest extends ComboCoreFormatterSpecTestBase {
  private static final String SPEC_RESOURCE = "/core_formatter_spec.md";

  public ComboCoreFormatterSpecTest(@NotNull SpecExample example) {
    super(example, null);
  }

  @Parameterized.Parameters(name = "{0}")
  public static List<Object[]> data() {
    return getTestData(ResourceLocation.of(SPEC_RESOURCE));
  }
}
