package com.vladsch.flexmark.ext.admonition;

import com.vladsch.flexmark.formatter.test.ComboTranslationFormatterSpecTestBase;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.List;

public class ComboAdmonitionTranslationFormatterSpecTest extends ComboTranslationFormatterSpecTestBase {
    private static final String SPEC_RESOURCE = "/ext_admonition_translation_formatter_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(AdmonitionExtension.create()))
            .set(Parser.LISTS_AUTO_LOOSE, false);

    public ComboAdmonitionTranslationFormatterSpecTest(SpecExample example) {
        super(example, OPTIONS, null);
    }

    @NotNull
    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
    }
}
