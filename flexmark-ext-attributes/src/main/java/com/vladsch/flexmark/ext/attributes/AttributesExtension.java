package com.vladsch.flexmark.ext.attributes;

import com.vladsch.flexmark.ext.attributes.internal.*;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.RendererBuilder;
import com.vladsch.flexmark.html.RendererExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

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
    public static final DataKey<NodeAttributeRepository> NODE_ATTRIBUTES = new DataKey<>("NODE_ATTRIBUTES", NodeAttributeRepository::new);
    public static final DataKey<KeepType> ATTRIBUTES_KEEP = new DataKey<>("ATTRIBUTES_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates
    public static final DataKey<Boolean> ASSIGN_TEXT_ATTRIBUTES = new DataKey<>("ASSIGN_TEXT_ATTRIBUTES", true); // assign attributes to text if previous is not a space
    public static final DataKey<Boolean> WRAP_NON_ATTRIBUTE_TEXT = new DataKey<>("WRAP_NON_ATTRIBUTE_TEXT", true);
    public static final DataKey<Boolean> USE_EMPTY_IMPLICIT_AS_SPAN_DELIMITER = new DataKey<>("USE_EMPTY_IMPLICIT_AS_SPAN_DELIMITER", false);

    private AttributesExtension() {
    }

    public static Extension create() {
        return new AttributesExtension();
    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessorFactory(new AttributesNodePostProcessor.Factory());
        parserBuilder.customInlineParserExtensionFactory(new AttributesInlineParserExtension.Factory());
    }

    @Override
    public void extend(Formatter.Builder builder) {
        builder.nodeFormatterFactory(new AttributesNodeFormatter.Factory());
    }

    @Override
    public void rendererOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (ASSIGN_TEXT_ATTRIBUTES.getFrom(rendererBuilder)) {
            rendererBuilder.nodeRendererFactory(new AttributesNodeRenderer.Factory());
        }
        rendererBuilder.attributeProviderFactory(new AttributesAttributeProvider.Factory());
    }

    @Override
    public void extend(RendererBuilder rendererBuilder, String rendererType) {
        //rendererBuilder.nodeRendererFactory(new AttributesNodeRenderer.Factory());
        rendererBuilder.attributeProviderFactory(new AttributesAttributeProvider.Factory());
    }
}
