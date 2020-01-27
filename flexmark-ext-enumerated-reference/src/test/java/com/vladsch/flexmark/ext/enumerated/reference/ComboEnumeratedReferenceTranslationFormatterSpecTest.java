package com.vladsch.flexmark.ext.enumerated.reference;

import com.vladsch.flexmark.core.test.util.TranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ComboEnumeratedReferenceTranslationFormatterSpecTest extends TranslationFormatterSpecTest {
    final private static String SPEC_RESOURCE = "/ext_enumerated_reference_translation_formatter_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Arrays.asList(EnumeratedReferenceExtension.create(), AttributesExtension.create()))
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = placementAndSortOptions(
            EnumeratedReferenceExtension.ENUMERATED_REFERENCES_KEEP,
            EnumeratedReferenceExtension.ENUMERATED_REFERENCE_PLACEMENT,
            EnumeratedReferenceExtension.ENUMERATED_REFERENCE_SORT
    );

    public ComboEnumeratedReferenceTranslationFormatterSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
