package com.vladsch.flexmark.ext.attributes;

import com.vladsch.flexmark.core.test.util.TranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.spec.ResourceLocation;
import com.vladsch.flexmark.test.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.*;

public class ComboAttributesTranslationFormatterSpecTest extends TranslationFormatterSpecTest {
    private static final String SPEC_RESOURCE = "/ext_attributes_translation_format_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(AttributesExtension.create()))
            .toImmutable();

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
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

    @Override
    public @NotNull ResourceLocation getSpecResourceLocation() {
        return ResourceLocation.of(SPEC_RESOURCE);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
    }
}
