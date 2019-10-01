package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.ext.toc.internal.TocOptions;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboTocSpecTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/ext_toc_ast_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(HtmlRenderer.RENDER_HEADER_ID, true)
            .set(Parser.EXTENSIONS, Collections.singletonList(TocExtension.create()));

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        optionsMap.put("text-only", new MutableDataSet().set(TocExtension.IS_TEXT_ONLY, true));
        optionsMap.put("formatted", new MutableDataSet().set(TocExtension.IS_TEXT_ONLY, false));
        optionsMap.put("hierarchy", new MutableDataSet().set(TocExtension.LIST_TYPE, TocOptions.ListType.HIERARCHY));
        optionsMap.put("flat", new MutableDataSet().set(TocExtension.LIST_TYPE, TocOptions.ListType.FLAT));
        optionsMap.put("flat-reversed", new MutableDataSet().set(TocExtension.LIST_TYPE, TocOptions.ListType.FLAT_REVERSED));
        optionsMap.put("sorted", new MutableDataSet().set(TocExtension.LIST_TYPE, TocOptions.ListType.SORTED));
        optionsMap.put("sorted-reversed", new MutableDataSet().set(TocExtension.LIST_TYPE, TocOptions.ListType.SORTED_REVERSED));
        optionsMap.put("numbered", new MutableDataSet().set(TocExtension.IS_NUMBERED, true));
        optionsMap.put("levels-2", new MutableDataSet().set(TocExtension.LEVELS, 1 << 2));
        optionsMap.put("title", new MutableDataSet().set(TocExtension.TITLE, "Table of Contents"));
        optionsMap.put("div-class", new MutableDataSet().set(TocExtension.DIV_CLASS, "content-class"));
        optionsMap.put("list-class", new MutableDataSet().set(TocExtension.LIST_CLASS, "list-class"));
        optionsMap.put("not-case-sensitive", new MutableDataSet().set(TocExtension.CASE_SENSITIVE_TOC_TAG, false));
    }

    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    private static DataHolder optionsSet(String optionSet) {
        if (optionSet == null) return null;
        return optionsMap.get(optionSet);
    }

    public ComboTocSpecTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
    }

    @Override
    public DataHolder options(String optionSet) {
        return optionsSet(optionSet);
    }

    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    public Parser parser() {
        return PARSER;
    }

    @Override
    public HtmlRenderer renderer() {
        return RENDERER;
    }
}
