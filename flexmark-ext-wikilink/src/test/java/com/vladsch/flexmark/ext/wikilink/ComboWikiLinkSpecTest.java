package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.junit.runners.Parameterized;

import java.util.*;

public class ComboWikiLinkSpecTest extends ComboSpecTestCase {
    static final String SPEC_RESOURCE = "/ext_wikilink_ast_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(Parser.EXTENSIONS, Arrays.asList(WikiLinkExtension.create(), TypographicExtension.create()));

    private static final Map<String, DataHolder> optionsMap = new HashMap<String, DataHolder>();
    static {
        optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        optionsMap.put("links-first", new MutableDataSet().set(WikiLinkExtension.LINK_FIRST_SYNTAX, true));
        optionsMap.put("link-ext", new MutableDataSet().set(WikiLinkExtension.LINK_FILE_EXTENSION, ".html"));
        optionsMap.put("link-prefix", new MutableDataSet().set(WikiLinkExtension.LINK_PREFIX, "/prefix/"));
        optionsMap.put("image-ext", new MutableDataSet().set(WikiLinkExtension.IMAGE_FILE_EXTENSION, ".png"));
        optionsMap.put("image-prefix", new MutableDataSet().set(WikiLinkExtension.IMAGE_PREFIX, "/images/"));
        optionsMap.put("wiki-images", new MutableDataSet().set(WikiLinkExtension.IMAGE_LINKS, true));
        optionsMap.put("allow-inlines", new MutableDataSet().set(WikiLinkExtension.ALLOW_INLINES, true));
        optionsMap.put("allow-anchors", new MutableDataSet().set(WikiLinkExtension.ALLOW_ANCHORS, true));
        optionsMap.put("allow-pipe-escape", new MutableDataSet().set(WikiLinkExtension.ALLOW_PIPE_ESCAPE, true));
        optionsMap.put("allow-anchor-escape", new MutableDataSet().set(WikiLinkExtension.ALLOW_ANCHOR_ESCAPE, true));
        optionsMap.put("custom-link-escape", new MutableDataSet().set(WikiLinkExtension.LINK_ESCAPE_CHARS, " +<>").set(WikiLinkExtension.LINK_REPLACE_CHARS, "____"));
    }

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    static DataHolder optionsSet(String optionSet) {
        if (optionSet == null) return null;
        return optionsMap.get(optionSet);
    }

    public ComboWikiLinkSpecTest(SpecExample example) {
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
}
