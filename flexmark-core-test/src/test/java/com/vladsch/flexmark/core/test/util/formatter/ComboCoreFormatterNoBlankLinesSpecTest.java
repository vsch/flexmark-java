package com.vladsch.flexmark.core.test.util.formatter;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import java.util.List;
import org.junit.runners.Parameterized;

public class ComboCoreFormatterNoBlankLinesSpecTest extends ComboCoreFormatterSpecTestBase {
  private static final String SPEC_RESOURCE = "/core_formatter_no_blanklines_spec.md";
  private static final DataHolder OPTIONS =
      new MutableDataSet().set(Parser.BLANK_LINES_IN_AST, false).toImmutable();

  public ComboCoreFormatterNoBlankLinesSpecTest(SpecExample example) {
    super(example, null, OPTIONS);
  }

  @Parameterized.Parameters(name = "{0}")
  public static List<Object[]> data() {
    return ComboSpecTestCase.getTestData(ResourceLocation.of(SPEC_RESOURCE));
  }
}
