package com.vladsch.flexmark.core.test.util;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import java.util.HashMap;
import java.util.Map;

public abstract class RendererSpecTest extends ComboSpecTestCase {
  private static final DataHolder OPTIONS =
      new MutableDataSet().set(HtmlRenderer.INDENT_SIZE, 2).toImmutable();

  private static final Map<String, DataHolder> optionsMap = new HashMap<>();

  static {
    optionsMap.put(
        "src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
  }

  protected RendererSpecTest(
      SpecExample example,
      Map<String, ? extends DataHolder> optionMap,
      DataHolder... defaultOptions) {
    super(
        example,
        ComboSpecTestCase.optionsMaps(optionsMap, optionMap),
        ComboSpecTestCase.dataHolders(OPTIONS, defaultOptions));
  }

  /**
   * @return false to disable example information in spec
   */
  protected boolean wantExampleInfo() {
    return true;
  }

  @Override
  public final SpecExampleRenderer getSpecExampleRenderer(
      SpecExample example, DataHolder exampleOptions) {
    DataHolder combinedOptions = aggregate(myDefaultOptions, exampleOptions);
    return new FlexmarkSpecExampleRenderer(
        example,
        combinedOptions,
        Parser.builder(combinedOptions).build(),
        HtmlRenderer.builder(combinedOptions).build(),
        wantExampleInfo());
  }
}
