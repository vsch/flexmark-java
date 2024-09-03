package com.vladsch.flexmark.ext.yaml.front.matter;

import com.vladsch.flexmark.core.test.util.FormatterSpecTest;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

public class ComboYamlFrontMatterFormatterSpecTest extends FormatterSpecTest {
  private static final String SPEC_RESOURCE = "/ext_yaml_front_matter_formatter_spec.md";
  public static final @NotNull ResourceLocation RESOURCE_LOCATION =
      ResourceLocation.of(SPEC_RESOURCE);
  private static final DataHolder OPTIONS =
      new MutableDataSet()
          .set(Parser.EXTENSIONS, Collections.singleton(YamlFrontMatterExtension.create()))
          .set(Parser.LISTS_AUTO_LOOSE, false)
          .toImmutable();

  private static final Map<String, DataHolder> optionsMap =
      placementAndSortOptions(
          Parser.REFERENCES_KEEP, Formatter.REFERENCE_PLACEMENT, Formatter.REFERENCE_SORT);

  public ComboYamlFrontMatterFormatterSpecTest(@NotNull SpecExample example) {
    super(example, optionsMap, OPTIONS);
  }

  @Parameterized.Parameters(name = "{0}")
  public static List<Object[]> data() {
    return getTestData(RESOURCE_LOCATION);
  }
}
