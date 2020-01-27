package com.vladsch.flexmark.ext.spec.example;

import com.vladsch.flexmark.core.test.util.FormatterSpecTest;
import com.vladsch.flexmark.ext.jekyll.front.matter.JekyllFrontMatterExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.test.util.spec.SpecReader;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboSpecExampleFormatterSpecTest extends FormatterSpecTest {
    final private static String SPEC_RESOURCE = "/ext_spec_example_formatter_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Arrays.asList(JekyllFrontMatterExtension.create(), SpecExampleExtension.create()))
            .set(Parser.LISTS_AUTO_LOOSE, false)
            .set(SpecExampleExtension.SPEC_EXAMPLE_BREAK, SpecReader.EXAMPLE_TEST_BREAK)
            .set(SpecExampleExtension.SPEC_SECTION_BREAK, SpecReader.SECTION_TEST_BREAK)
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
//        optionsMap.put("class-explicit", new MutableDataSet().set(AttributesExtension.FORMAT_ATTRIBUTE_CLASS, AttributeImplicitName.EXPLICIT_PREFERRED));
    }
    public ComboSpecExampleFormatterSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
