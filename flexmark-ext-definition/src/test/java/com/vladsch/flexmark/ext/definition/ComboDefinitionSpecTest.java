package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

public class ComboDefinitionSpecTest extends RendererSpecTest {
  private static final String SPEC_RESOURCE = "/ext_definition_ast_spec.md";
  private static final DataHolder OPTIONS =
      new MutableDataSet()
          .set(Parser.EXTENSIONS, Collections.singleton(DefinitionExtension.create()))
          .toImmutable();

  private static final Map<String, DataHolder> optionsMap = new HashMap<>();

  static {
    optionsMap.put("blank-lines-in-ast", new MutableDataSet().set(Parser.BLANK_LINES_IN_AST, true));
    optionsMap.put("no-auto-loose", new MutableDataSet().set(Parser.LISTS_AUTO_LOOSE, false));
    optionsMap.put(
        "break-list",
        new MutableDataSet().set(DefinitionExtension.DOUBLE_BLANK_LINE_BREAKS_LIST, true));
    optionsMap.put(
        "suppress-format-eol",
        new MutableDataSet()
            .set(HtmlRenderer.HTML_BLOCK_OPEN_TAG_EOL, false)
            .set(HtmlRenderer.HTML_BLOCK_CLOSE_TAG_EOL, false)
            .set(HtmlRenderer.INDENT_SIZE, 0));
  }

  public ComboDefinitionSpecTest(@NotNull SpecExample example) {
    super(example, optionsMap, OPTIONS);
  }

  @Parameterized.Parameters(name = "{0}")
  public static List<Object[]> data() {
    return getTestData(ResourceLocation.of(SPEC_RESOURCE));
  }
}
