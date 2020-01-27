package com.vladsch.flexmark.core.test.util.formatter;

import com.vladsch.flexmark.core.test.util.FormatterSpecTest;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class ComboCoreFormatterSpecTestBase extends FormatterSpecTest {
    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
//    static {
//        optionsMap.put("atx-space-as-is", new MutableDataSet().set(Formatter.SPACE_AFTER_ATX_MARKER, DiscretionaryText.AS_IS));
//    }

    public ComboCoreFormatterSpecTestBase(@NotNull SpecExample example, @Nullable Map<String, ? extends DataHolder> optionMap, @Nullable DataHolder... defaultOptions) {
        super(example, ComboSpecTestCase.optionsMaps(optionsMap, optionMap), defaultOptions);
    }
}
