package com.vladsch.flexmark.ext.attributes;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.escaped.character.EscapedCharacterExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboExtAttributesSpecTest extends RendererSpecTest {
    final private static String SPEC_RESOURCE = "/ext_attributes_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.RENDER_HEADER_ID, true)
            .set(AttributesExtension.ASSIGN_TEXT_ATTRIBUTES, true)
            .set(Parser.EXTENSIONS, Arrays.asList(
                    AttributesExtension.create(),
                    TocExtension.create(),
                    EmojiExtension.create(),
                    DefinitionExtension.create(),
                    EscapedCharacterExtension.create(),
                    TypographicExtension.create(),
                    TablesExtension.create()
            ))
            .toImmutable();

    final private static Map<String, MutableDataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("anchors", new MutableDataSet()
                .set(Parser.EXTENSIONS, Arrays.asList(AnchorLinkExtension.create(), AttributesExtension.create(), TocExtension.create(), EmojiExtension.create()))
                .set(AnchorLinkExtension.ANCHORLINKS_WRAP_TEXT, false)
                .set(HtmlRenderer.RENDER_HEADER_ID, false)
        );
        optionsMap.put("text-attributes", new MutableDataSet().set(AttributesExtension.ASSIGN_TEXT_ATTRIBUTES, true));
        optionsMap.put("no-text-attributes", new MutableDataSet().set(AttributesExtension.ASSIGN_TEXT_ATTRIBUTES, false));
        optionsMap.put("dont-wrap-non-attributes", new MutableDataSet().set(AttributesExtension.WRAP_NON_ATTRIBUTE_TEXT, false));
        optionsMap.put("empty-implicit-delimiters", new MutableDataSet().set(AttributesExtension.USE_EMPTY_IMPLICIT_AS_SPAN_DELIMITER, true));
        optionsMap.put("no-info-attributes", new MutableDataSet().set(AttributesExtension.FENCED_CODE_INFO_ATTRIBUTES, false));
        optionsMap.put("info-attributes", new MutableDataSet().set(AttributesExtension.FENCED_CODE_INFO_ATTRIBUTES, true));
        optionsMap.put("fenced-code-to-both", new MutableDataSet().set(AttributesExtension.FENCED_CODE_ADD_ATTRIBUTES, FencedCodeAddType.ADD_TO_PRE_CODE));
        optionsMap.put("fenced-code-to-pre", new MutableDataSet().set(AttributesExtension.FENCED_CODE_ADD_ATTRIBUTES, FencedCodeAddType.ADD_TO_PRE));
        optionsMap.put("fenced-code-to-code", new MutableDataSet().set(AttributesExtension.FENCED_CODE_ADD_ATTRIBUTES, FencedCodeAddType.ADD_TO_CODE));
        optionsMap.put("custom", new MutableDataSet().set(CUSTOM_OPTION, (option, text) -> null));
    }
    public ComboExtAttributesSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
