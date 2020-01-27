package com.vladsch.flexmark.ext.attributes;

import com.vladsch.flexmark.ext.attributes.internal.*;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.RendererBuilder;
import com.vladsch.flexmark.html.RendererExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import org.jetbrains.annotations.NotNull;

/**
 * Extension for attributes
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed attributes text is turned into {@link AttributesNode} nodes.
 */
public class AttributesExtension implements Parser.ParserExtension
        , RendererExtension
        , HtmlRenderer.HtmlRendererExtension
        , Formatter.FormatterExtension
        //, Parser.ReferenceHoldingExtension
{
    final public static DataKey<NodeAttributeRepository> NODE_ATTRIBUTES = new DataKey<>("NODE_ATTRIBUTES", new NodeAttributeRepository(null), NodeAttributeRepository::new);
    final public static DataKey<KeepType> ATTRIBUTES_KEEP = new DataKey<>("ATTRIBUTES_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates
    final public static DataKey<Boolean> ASSIGN_TEXT_ATTRIBUTES = new DataKey<>("ASSIGN_TEXT_ATTRIBUTES", true); // assign attributes to text if previous is not a space
    final public static DataKey<Boolean> FENCED_CODE_INFO_ATTRIBUTES = new DataKey<>("FENCED_CODE_INFO_ATTRIBUTES", false); // assign attributes found at end of fenced code info strings
    final public static DataKey<FencedCodeAddType> FENCED_CODE_ADD_ATTRIBUTES = new DataKey<>("FENCED_CODE_ADD_ATTRIBUTES", FencedCodeAddType.ADD_TO_PRE_CODE); // assign attributes to pre/code tag
    final public static DataKey<Boolean> WRAP_NON_ATTRIBUTE_TEXT = new DataKey<>("WRAP_NON_ATTRIBUTE_TEXT", true);
    final public static DataKey<Boolean> USE_EMPTY_IMPLICIT_AS_SPAN_DELIMITER = new DataKey<>("USE_EMPTY_IMPLICIT_AS_SPAN_DELIMITER", false);

    final public static DataKey<Boolean> FORMAT_ATTRIBUTES_COMBINE_CONSECUTIVE = new DataKey<>("FORMAT_ATTRIBUTES_COMBINE_CONSECUTIVE", false);
    final public static DataKey<Boolean> FORMAT_ATTRIBUTES_SORT = new DataKey<>("FORMAT_ATTRIBUTES_SORT", false);
    final public static DataKey<DiscretionaryText> FORMAT_ATTRIBUTES_SPACES = new DataKey<>("FORMAT_ATTRIBUTES_SPACES", DiscretionaryText.AS_IS); // add spaces after { and before }
    final public static DataKey<DiscretionaryText> FORMAT_ATTRIBUTE_EQUAL_SPACE = new DataKey<>("FORMAT_ATTRIBUTE_EQUAL_SPACE", DiscretionaryText.AS_IS);
    final public static DataKey<AttributeValueQuotes> FORMAT_ATTRIBUTE_VALUE_QUOTES = new DataKey<>("FORMAT_ATTRIBUTE_VALUE_QUOTES", AttributeValueQuotes.AS_IS);
    final public static DataKey<AttributeImplicitName> FORMAT_ATTRIBUTE_ID = new DataKey<>("FORMAT_ATTRIBUTE_ID", AttributeImplicitName.AS_IS);
    final public static DataKey<AttributeImplicitName> FORMAT_ATTRIBUTE_CLASS = new DataKey<>("FORMAT_ATTRIBUTE_CLASS", AttributeImplicitName.AS_IS);

    private AttributesExtension() {
    }

    public static AttributesExtension create() {
        return new AttributesExtension();
    }

    @Override
    public void parserOptions(MutableDataHolder options) {
        if (options.contains(FENCED_CODE_INFO_ATTRIBUTES) && FENCED_CODE_INFO_ATTRIBUTES.get(options) && !options.contains(FENCED_CODE_ADD_ATTRIBUTES)) {
            // change default to pre only, to add to code use attributes after info
            options.set(FENCED_CODE_ADD_ATTRIBUTES, FencedCodeAddType.ADD_TO_PRE);
        }
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessorFactory(new AttributesNodePostProcessor.Factory());
        parserBuilder.customInlineParserExtensionFactory(new AttributesInlineParserExtension.Factory());
    }

    @Override
    public void extend(Formatter.Builder formatterBuilder) {
        formatterBuilder.nodeFormatterFactory(new AttributesNodeFormatter.Factory());
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (ASSIGN_TEXT_ATTRIBUTES.get(htmlRendererBuilder)) {
            htmlRendererBuilder.nodeRendererFactory(new AttributesNodeRenderer.Factory());
        }
        htmlRendererBuilder.attributeProviderFactory(new AttributesAttributeProvider.Factory());
    }

    @Override
    public void extend(@NotNull RendererBuilder rendererBuilder, @NotNull String rendererType) {
        //rendererBuilder.nodeRendererFactory(new AttributesNodeRenderer.Factory());
        rendererBuilder.attributeProviderFactory(new AttributesAttributeProvider.Factory());
    }
}
