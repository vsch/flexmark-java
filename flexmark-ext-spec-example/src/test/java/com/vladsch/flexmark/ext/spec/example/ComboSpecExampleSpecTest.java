package com.vladsch.flexmark.ext.spec.example;

import com.vladsch.flexmark.ext.spec.example.internal.RenderAs;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboSpecExampleSpecTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/ext_spec_example_ast_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(SpecExampleExtension.SPEC_EXAMPLE_BREAK, SpecReader.EXAMPLE_TEST_BREAK)
            .set(SpecExampleExtension.SPEC_TYPE_BREAK, SpecReader.TYPE_TEST_BREAK)
            .set(SpecExampleExtension.SPEC_EXAMPLE_RENDER_AS, RenderAs.SECTIONS)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(Parser.EXTENSIONS, Collections.singleton(SpecExampleExtension.create()));

    private static final Map<String, DataHolder> optionsMap = new HashMap<String, DataHolder>();
    static {
        optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        optionsMap.put("no-language-prefix", new MutableDataSet().set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX, ""));
        optionsMap.put("no-option-nodes", new MutableDataSet().set(SpecExampleExtension.SPEC_OPTION_NODES, false));
        optionsMap.put("as-def-list", new MutableDataSet().set(SpecExampleExtension.SPEC_EXAMPLE_RENDER_AS, RenderAs.DEFINITION_LIST));
        optionsMap.put("as-fenced-code", new MutableDataSet().set(SpecExampleExtension.SPEC_EXAMPLE_RENDER_AS, RenderAs.FENCED_CODE));
    }

    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    private static DataHolder optionsSet(String optionSet) {
        if (optionSet == null) return null;
        return optionsMap.get(optionSet);
    }

    public ComboSpecExampleSpecTest(SpecExample example) {
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
