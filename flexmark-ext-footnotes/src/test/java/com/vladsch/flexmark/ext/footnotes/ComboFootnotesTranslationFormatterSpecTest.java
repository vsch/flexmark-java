package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.core.test.util.TranslationFormatterSpecTest;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.spec.ResourceLocation;
import com.vladsch.flexmark.test.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ComboFootnotesTranslationFormatterSpecTest extends TranslationFormatterSpecTest {
    private static final String SPEC_RESOURCE = "/ext_footnotes_translation_formatter_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(FootnoteExtension.create()))
            .toImmutable();

    private static final Map<String, DataHolder> optionsMap = placementAndSortOptions(FootnoteExtension.FOOTNOTE_PLACEMENT, FootnoteExtension.FOOTNOTE_SORT);

    public ComboFootnotesTranslationFormatterSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Override
    public @NotNull ResourceLocation getSpecResourceLocation() {
        return ResourceLocation.of(SPEC_RESOURCE);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
    }
}
