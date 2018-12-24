package com.vladsch.flexmark.ext.autolink;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.*;

public class ComboAutolinkSpecTest extends ComboSpecTestCase {
    static final String SPEC_RESOURCE = "/ext_autolink_ast_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(Parser.EXTENSIONS, Collections.singleton(AutolinkExtension.create()));

    private static final Map<String, DataHolder> optionsMap = new HashMap<String, DataHolder>();
    static {
        optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        optionsMap.put("no-autolink", new MutableDataSet().set(Parser.EXTENSIONS, Collections.EMPTY_LIST));
        optionsMap.put("ignore-google", new MutableDataSet().set(AutolinkExtension.IGNORE_LINKS, "www.google.com"));
        optionsMap.put("intellij-dummy", new MutableDataSet().set(Parser.INTELLIJ_DUMMY_IDENTIFIER, true));
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
        List<Object[]> data = new ArrayList<Object[]>();

        // NULL example runs full spec test
        data.add(new Object[] { SpecExample.NULL });

        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
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
