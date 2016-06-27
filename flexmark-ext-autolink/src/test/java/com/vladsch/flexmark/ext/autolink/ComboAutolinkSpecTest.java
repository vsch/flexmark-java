package com.vladsch.flexmark.ext.autolink;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.internal.util.collection.MutableDataSet;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.*;

public class ComboAutolinkSpecTest extends ComboSpecTestCase {
    static final String SPEC_RESOURCE = "/ext_autolink_ast_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(Parser.EXTENSIONS, Collections.singleton(AutolinkExtension.create()));

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        //        optionsMap.put("custom", new MutableDataSet()
        //                .set(AutolinkExtension.AUTOLINK, value)
        //        );
    }

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    static DataHolder optionsSet(String optionSet) {
        if (optionSet == null) return null;
        return optionsMap.get(optionSet);
    }

    public ComboAutolinkSpecTest(SpecExample example) {
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

    @Test
    public void testSpecTxt() throws Exception {
        if (!example.isFullSpecExample()) return;

        final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();
        final Parser PARSER = Parser.builder(OPTIONS).build();

        String source = readResource("/spec.txt");
        Node node = PARSER.parse(source);
        //String html = readResource("/table.html");
        //assertRendering(source, html);
    }
}
