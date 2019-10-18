package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.test.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.SpecExampleRenderer;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboWikiLinkSpecTest extends ComboSpecTestCase {
    static final String SPEC_RESOURCE = "/ext_wikilink_ast_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(Parser.EXTENSIONS, Arrays.asList(WikiLinkExtension.create(), TypographicExtension.create()));

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        optionsMap.put("links-first", new MutableDataSet().set(WikiLinkExtension.LINK_FIRST_SYNTAX, true));
        optionsMap.put("link-ext", new MutableDataSet().set(WikiLinkExtension.LINK_FILE_EXTENSION, ".html"));
        optionsMap.put("link-prefix", new MutableDataSet().set(WikiLinkExtension.LINK_PREFIX, "/prefix/"));
        optionsMap.put("link-prefix-absolute", new MutableDataSet().set(WikiLinkExtension.LINK_PREFIX, "/relative/").set(WikiLinkExtension.LINK_PREFIX_ABSOLUTE, "/absolute/"));
        optionsMap.put("image-ext", new MutableDataSet().set(WikiLinkExtension.IMAGE_FILE_EXTENSION, ".png"));
        optionsMap.put("image-prefix", new MutableDataSet().set(WikiLinkExtension.IMAGE_PREFIX, "/images/"));
        optionsMap.put("image-prefix-absolute", new MutableDataSet().set(WikiLinkExtension.IMAGE_PREFIX, "/relative/images/").set(WikiLinkExtension.IMAGE_PREFIX_ABSOLUTE, "/absolute/images/"));
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
        return getTestData(SPEC_RESOURCE);
    }

    @Nullable
    @Override
    public DataHolder options(String option) {
        return optionsMap.get(option);
    }

    @NotNull
    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = combineOptions(OPTIONS, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, Parser.builder(combinedOptions).build(), HtmlRenderer.builder(combinedOptions).build(), true);
    }
}
