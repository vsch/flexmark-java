package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.internal.util.MutableDataSet;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import org.junit.runners.Parameterized;

import java.util.*;

public class ComboFootnotesSpecTest extends ComboSpecTestCase {
    static final String SPEC_RESOURCE = "/ext_footnotes_ast_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(Parser.EXTENSIONS, Collections.singleton(FootnoteExtension.create()));

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("custom", new MutableDataSet()
                .set(FootnoteExtension.FOOTNOTE_REF_PREFIX, "[")
                .set(FootnoteExtension.FOOTNOTE_REF_SUFFIX, "]")
                .set(FootnoteExtension.FOOTNOTE_BACK_REF_STRING, "&lt;back&gt;")
        );
        optionsMap.put("link-class-none", new MutableDataSet()
                .set(FootnoteExtension.FOOTNOTE_LINK_REF_CLASS, "")
        );
        optionsMap.put("link-class-text", new MutableDataSet()
                .set(FootnoteExtension.FOOTNOTE_LINK_REF_CLASS, "text")
        );
        optionsMap.put("back-link-class-none", new MutableDataSet()
                .set(FootnoteExtension.FOOTNOTE_BACK_LINK_REF_CLASS, "")
        );
        optionsMap.put("back-link-class-text", new MutableDataSet()
                .set(FootnoteExtension.FOOTNOTE_BACK_LINK_REF_CLASS, "text")
        );
    }

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    static DataHolder optionsSet(String optionSet) {
        return optionsMap.get(optionSet);
    }

    public ComboFootnotesSpecTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<SpecExample> examples = SpecReader.readExamples(SPEC_RESOURCE);
        List<Object[]> data = new ArrayList<>();

        // NULL example runs full spec test
        data.add(new Object[] { SpecExample.NULL });

        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    @Override
    protected DataHolder options(String optionSet) {
        return optionsSet(optionSet);
    }

    @Override
    protected String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    protected Parser parser() {
        return PARSER;
    }

    @Override
    protected HtmlRenderer renderer() {
        return RENDERER;
    }
}
