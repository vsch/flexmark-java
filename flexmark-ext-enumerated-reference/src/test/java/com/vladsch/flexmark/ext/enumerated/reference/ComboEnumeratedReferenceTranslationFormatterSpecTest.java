package com.vladsch.flexmark.ext.enumerated.reference;

import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.formatter.test.ComboTranslationFormatterSpecTestBase;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboEnumeratedReferenceTranslationFormatterSpecTest extends ComboTranslationFormatterSpecTestBase {
    private static final String SPEC_RESOURCE = "/ext_enumerated_reference_translation_formatter_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Arrays.asList(EnumeratedReferenceExtension.create(), AttributesExtension.create()));

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("references-as-is", new MutableDataSet().set(EnumeratedReferenceExtension.ENUMERATED_REFERENCE_PLACEMENT, ElementPlacement.AS_IS));
        optionsMap.put("references-document-top", new MutableDataSet().set(EnumeratedReferenceExtension.ENUMERATED_REFERENCE_PLACEMENT, ElementPlacement.DOCUMENT_TOP));
        optionsMap.put("references-group-with-first", new MutableDataSet().set(EnumeratedReferenceExtension.ENUMERATED_REFERENCE_PLACEMENT, ElementPlacement.GROUP_WITH_FIRST));
        optionsMap.put("references-group-with-last", new MutableDataSet().set(EnumeratedReferenceExtension.ENUMERATED_REFERENCE_PLACEMENT, ElementPlacement.GROUP_WITH_LAST));
        optionsMap.put("references-document-bottom", new MutableDataSet().set(EnumeratedReferenceExtension.ENUMERATED_REFERENCE_PLACEMENT, ElementPlacement.DOCUMENT_BOTTOM));
        optionsMap.put("references-sort", new MutableDataSet().set(EnumeratedReferenceExtension.ENUMERATED_REFERENCE_SORT, ElementPlacementSort.SORT));
        optionsMap.put("references-sort-unused-last", new MutableDataSet().set(EnumeratedReferenceExtension.ENUMERATED_REFERENCE_SORT, ElementPlacementSort.SORT_UNUSED_LAST));
    }
    public ComboEnumeratedReferenceTranslationFormatterSpecTest(SpecExample example) {
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
