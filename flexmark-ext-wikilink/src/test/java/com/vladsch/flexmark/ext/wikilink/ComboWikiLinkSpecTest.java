package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboWikiLinkSpecTest extends RendererSpecTest {
    static final String SPEC_RESOURCE = "/ext_wikilink_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Arrays.asList(WikiLinkExtension.create(), TypographicExtension.create()))
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
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
        optionsMap.put("link-text-priority", new MutableDataSet().set(Parser.LINK_TEXT_PRIORITY_OVER_LINK_REF, true));
    }
    public ComboWikiLinkSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
