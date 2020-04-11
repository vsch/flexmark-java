package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.*;

public class ComboFootnotesSpecTest extends RendererSpecTest {
    static final String SPEC_RESOURCE = "/ext_footnotes_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Arrays.asList(FootnoteExtension.create(), TablesExtension.create()))
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("custom", new MutableDataSet()
                .set(FootnoteExtension.FOOTNOTE_REF_PREFIX, "[")
                .set(FootnoteExtension.FOOTNOTE_REF_SUFFIX, "]")
                .set(FootnoteExtension.FOOTNOTE_BACK_REF_STRING, "&lt;back&gt;")
        );
        optionsMap.put("link-class-none", new MutableDataSet().set(FootnoteExtension.FOOTNOTE_LINK_REF_CLASS, ""));
        optionsMap.put("link-class-text", new MutableDataSet().set(FootnoteExtension.FOOTNOTE_LINK_REF_CLASS, "text"));
        optionsMap.put("back-link-class-none", new MutableDataSet().set(FootnoteExtension.FOOTNOTE_BACK_LINK_REF_CLASS, ""));
        optionsMap.put("back-link-class-text", new MutableDataSet().set(FootnoteExtension.FOOTNOTE_BACK_LINK_REF_CLASS, "text"));
        optionsMap.put("item-indent-8", new MutableDataSet().set(Parser.LISTS_ITEM_INDENT, 8));
    }
    public ComboFootnotesSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
