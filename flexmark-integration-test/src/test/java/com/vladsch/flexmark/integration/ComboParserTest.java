package com.vladsch.flexmark.integration;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.internal.util.MutableDataSet;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.*;

public class ComboParserTest extends ComboSpecTestCase {
    static final String SPEC_RESOURCE = "/ext_integration_ast_spec.txt";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(Parser.EXTENSIONS, Arrays.asList(
                    TablesExtension.create(),
                    TaskListExtension.create(),
                    FootnoteExtension.create(),
                    WikiLinkExtension.create(),
                    AutolinkExtension.create(),
                    AbbreviationExtension.create(),
                    EmojiExtension.create(),
                    StrikethroughExtension.create()
            ));

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("gfm", new MutableDataSet()
                .set(TablesExtension.COLUMN_SPANS, false)
                .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
                .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
                .set(TablesExtension.HEADER_SEPARATOR_COLUMNS, true)
        );
    }

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    static DataHolder optionsSet(String optionSet) {
        if (optionSet == null) return null;

        return optionsMap.get(optionSet);
    }

    public ComboParserTest(SpecExample example) {
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

    //@Test
    public void testWrap() throws Exception {
        if (!example.isFullSpecExample()) return;

        final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();
        final Parser PARSER = Parser.builder(OPTIONS).build();

        String source = readResource("/wrap.md");
        String html = readResource("/wrap.html");

        assertRendering(source, html);
    }

    @Test
    public void testSpecTxt() throws Exception {
        if (!example.isFullSpecExample()) return;

        final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();
        final Parser PARSER = Parser.builder(OPTIONS).build();

        String source = readResource("/commonMarkSpec.md");
        Node node = PARSER.parse(source);
        //String html = readResource("/table.html");
        //assertRendering(source, html);
    }

}
