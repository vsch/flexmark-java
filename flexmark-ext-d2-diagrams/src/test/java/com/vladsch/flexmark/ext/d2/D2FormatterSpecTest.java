package com.vladsch.flexmark.ext.d2;

import com.vladsch.flexmark.core.test.util.FormatterSpecTest;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class D2FormatterSpecTest extends FormatterSpecTest {
    final private static String SPEC_RESOURCE = "/d2_formatter_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(D2Extension.create()))
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = placementAndSortOptions(
            Parser.REFERENCES_KEEP,
            Formatter.REFERENCE_PLACEMENT,
            Formatter.REFERENCE_SORT);

    public D2FormatterSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<Object[]> data = getTestData(RESOURCE_LOCATION);
        return data;
    }
}
