package com.vladsch.flexmark.ext.jekyll.tag;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboJekyllTagSpecTest extends RendererSpecTest {
    final private static String SPEC_RESOURCE = "/jekyll_tag_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(JekyllTagExtension.create()))
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("dummy-identifier", new MutableDataSet().set(Parser.INTELLIJ_DUMMY_IDENTIFIER, true));
        optionsMap.put("no-inlines", new MutableDataSet().set(JekyllTagExtension.ENABLE_INLINE_TAGS, false));
        optionsMap.put("no-blocks", new MutableDataSet().set(JekyllTagExtension.ENABLE_BLOCK_TAGS, false));
        Map<String, String> content = new HashMap<>();
        content.put("test.html", "<h1>Heading 1</h1>\n" +
                "<p>test text</p>\n" +
                "");
        content.put("links.html", "");
        optionsMap.put("includes", new MutableDataSet().set(JekyllTagExtension.INCLUDED_HTML, content));
        optionsMap.put("embed-includes", new MutableDataSet().set(JekyllTagExtension.EMBED_INCLUDED_CONTENT, true));
    }
    public ComboJekyllTagSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
