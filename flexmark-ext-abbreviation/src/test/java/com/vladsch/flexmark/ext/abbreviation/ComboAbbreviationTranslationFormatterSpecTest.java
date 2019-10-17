package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.formatter.ComboCoreTranslationFormatterSpecTestBase;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboAbbreviationTranslationFormatterSpecTest extends ComboCoreTranslationFormatterSpecTestBase {
    private static final String SPEC_RESOURCE = "/ext_abbreviation_translation_formatter_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(AbbreviationExtension.create()))
            .set(Parser.UNDERSCORE_DELIMITER_PROCESSOR, false);

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("references-as-is", new MutableDataSet().set(AbbreviationExtension.ABBREVIATIONS_PLACEMENT, ElementPlacement.AS_IS));
        optionsMap.put("references-document-top", new MutableDataSet().set(AbbreviationExtension.ABBREVIATIONS_PLACEMENT, ElementPlacement.DOCUMENT_TOP));
        optionsMap.put("references-group-with-first", new MutableDataSet().set(AbbreviationExtension.ABBREVIATIONS_PLACEMENT, ElementPlacement.GROUP_WITH_FIRST));
        optionsMap.put("references-group-with-last", new MutableDataSet().set(AbbreviationExtension.ABBREVIATIONS_PLACEMENT, ElementPlacement.GROUP_WITH_LAST));
        optionsMap.put("references-document-bottom", new MutableDataSet().set(AbbreviationExtension.ABBREVIATIONS_PLACEMENT, ElementPlacement.DOCUMENT_BOTTOM));
        optionsMap.put("references-sort", new MutableDataSet().set(AbbreviationExtension.ABBREVIATIONS_SORT, ElementPlacementSort.SORT));
        optionsMap.put("references-sort-unused-last", new MutableDataSet().set(AbbreviationExtension.ABBREVIATIONS_SORT, ElementPlacementSort.SORT_UNUSED_LAST));
    }
    //
    public ComboAbbreviationTranslationFormatterSpecTest(SpecExample example) {
        super(example, OPTIONS, optionsMap);
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
