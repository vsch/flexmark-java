package com.vladsch.flexmark.ext.attributes;

import com.vladsch.flexmark.core.test.util.FormatterSpecTest;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.*;

public class ComboAttributesFormatterSpecTest extends FormatterSpecTest {
    private static final String SPEC_RESOURCE = "/ext_attributes_format_spec.md";
    public static final @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(AttributesExtension.create()))
            .set(Parser.LISTS_AUTO_LOOSE, false)
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
        optionsMap.put("attributes-spaces-as-is", new MutableDataSet().set(AttributesExtension.FORMAT_ATTRIBUTES_SPACES, DiscretionaryText.AS_IS));
        optionsMap.put("attributes-spaces-add", new MutableDataSet().set(AttributesExtension.FORMAT_ATTRIBUTES_SPACES, DiscretionaryText.ADD));
        optionsMap.put("attributes-spaces-remove", new MutableDataSet().set(AttributesExtension.FORMAT_ATTRIBUTES_SPACES, DiscretionaryText.REMOVE));
        optionsMap.put("sep-spaces-as-is", new MutableDataSet().set(AttributesExtension.FORMAT_ATTRIBUTE_EQUAL_SPACE, DiscretionaryText.AS_IS));
        optionsMap.put("sep-spaces-add", new MutableDataSet().set(AttributesExtension.FORMAT_ATTRIBUTE_EQUAL_SPACE, DiscretionaryText.ADD));
        optionsMap.put("sep-spaces-remove", new MutableDataSet().set(AttributesExtension.FORMAT_ATTRIBUTE_EQUAL_SPACE, DiscretionaryText.REMOVE));
        optionsMap.put("value-quotes-as-is", new MutableDataSet().set(AttributesExtension.FORMAT_ATTRIBUTE_VALUE_QUOTES, AttributeValueQuotes.AS_IS));
        optionsMap.put("value-quotes-no-quotes-single-preferred", new MutableDataSet().set(AttributesExtension.FORMAT_ATTRIBUTE_VALUE_QUOTES, AttributeValueQuotes.NO_QUOTES_SINGLE_PREFERRED));
        optionsMap.put("value-quotes-no-quotes-double-preferred", new MutableDataSet().set(AttributesExtension.FORMAT_ATTRIBUTE_VALUE_QUOTES, AttributeValueQuotes.NO_QUOTES_DOUBLE_PREFERRED));
        optionsMap.put("value-quotes-single-preferred", new MutableDataSet().set(AttributesExtension.FORMAT_ATTRIBUTE_VALUE_QUOTES, AttributeValueQuotes.SINGLE_PREFERRED));
        optionsMap.put("value-quotes-double-preferred", new MutableDataSet().set(AttributesExtension.FORMAT_ATTRIBUTE_VALUE_QUOTES, AttributeValueQuotes.DOUBLE_PREFERRED));
        optionsMap.put("value-quotes-single-quotes", new MutableDataSet().set(AttributesExtension.FORMAT_ATTRIBUTE_VALUE_QUOTES, AttributeValueQuotes.SINGLE_QUOTES));
        optionsMap.put("value-quotes-double-quotes", new MutableDataSet().set(AttributesExtension.FORMAT_ATTRIBUTE_VALUE_QUOTES, AttributeValueQuotes.DOUBLE_QUOTES));
        optionsMap.put("combine-consecutive", new MutableDataSet().set(AttributesExtension.FORMAT_ATTRIBUTES_COMBINE_CONSECUTIVE, true));
        optionsMap.put("sort-attributes", new MutableDataSet().set(AttributesExtension.FORMAT_SORT_ATTRIBUTES, true));
        optionsMap.put("id-as-is", new MutableDataSet().set(AttributesExtension.FORMAT_ID_ATTRIBUTE, AttributeImplicitName.AS_IS));
        optionsMap.put("id-implicit", new MutableDataSet().set(AttributesExtension.FORMAT_ID_ATTRIBUTE, AttributeImplicitName.IMPLICIT_PREFERED));
        optionsMap.put("id-explicit", new MutableDataSet().set(AttributesExtension.FORMAT_ID_ATTRIBUTE, AttributeImplicitName.EXPLICIT_PREFERED));
        optionsMap.put("class-as-is", new MutableDataSet().set(AttributesExtension.FORMAT_CLASS_ATTRIBUTE, AttributeImplicitName.AS_IS));
        optionsMap.put("class-implicit", new MutableDataSet().set(AttributesExtension.FORMAT_CLASS_ATTRIBUTE, AttributeImplicitName.IMPLICIT_PREFERED));
        optionsMap.put("class-explicit", new MutableDataSet().set(AttributesExtension.FORMAT_CLASS_ATTRIBUTE, AttributeImplicitName.EXPLICIT_PREFERED));
    }

    public ComboAttributesFormatterSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
