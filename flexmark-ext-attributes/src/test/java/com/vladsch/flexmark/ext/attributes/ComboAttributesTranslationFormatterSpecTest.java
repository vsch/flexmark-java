package com.vladsch.flexmark.ext.attributes;

import com.vladsch.flexmark.core.test.util.TranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.*;

public class ComboAttributesTranslationFormatterSpecTest extends TranslationFormatterSpecTest {
    final private static String SPEC_RESOURCE = "/ext_attributes_translation_format_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(AttributesExtension.create()))
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("anchors", new MutableDataSet()
                .set(Parser.EXTENSIONS, Arrays.asList(AnchorLinkExtension.create(), AttributesExtension.create(), TocExtension.create(), EmojiExtension.create()))
                .set(AnchorLinkExtension.ANCHORLINKS_WRAP_TEXT, false)
                .set(HtmlRenderer.RENDER_HEADER_ID, false)
        );
        optionsMap.put("text-attributes", new MutableDataSet().set(AttributesExtension.ASSIGN_TEXT_ATTRIBUTES, true));
        optionsMap.put("no-text-attributes", new MutableDataSet().set(AttributesExtension.ASSIGN_TEXT_ATTRIBUTES, false));
    }
    //
    public ComboAttributesTranslationFormatterSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
