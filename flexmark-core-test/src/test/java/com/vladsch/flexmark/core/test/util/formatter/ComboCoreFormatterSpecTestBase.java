package com.vladsch.flexmark.core.test.util.formatter;

import com.vladsch.flexmark.core.test.util.FormatterSpecTest;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import java.util.HashMap;
import java.util.Map;

abstract class ComboCoreFormatterSpecTestBase extends FormatterSpecTest {
  private static final Map<String, DataHolder> optionsMap = new HashMap<>();

  ComboCoreFormatterSpecTestBase(
      SpecExample example,
      Map<String, ? extends DataHolder> optionMap,
      DataHolder... defaultOptions) {
    super(example, ComboSpecTestCase.optionsMaps(optionsMap, optionMap), defaultOptions);
  }
}
