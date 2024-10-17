package com.vladsch.flexmark.core.test.util.formatter;

import com.vladsch.flexmark.core.test.util.FormatterSpecTest;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class ComboCoreFormatterSpecTestBase extends FormatterSpecTest {
  private static final Map<String, DataHolder> optionsMap = new HashMap<>();

  ComboCoreFormatterSpecTestBase(
      @NotNull SpecExample example,
      @Nullable Map<String, ? extends DataHolder> optionMap,
      @Nullable DataHolder... defaultOptions) {
    super(example, ComboSpecTestCase.optionsMaps(optionsMap, optionMap), defaultOptions);
  }
}
