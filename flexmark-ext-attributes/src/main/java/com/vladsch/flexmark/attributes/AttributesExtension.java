package com.vladsch.flexmark.attributes;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.attributes.internal.AttributesAttributeProvider;
import com.vladsch.flexmark.attributes.internal.AttributesInlineParserExtension;
import com.vladsch.flexmark.attributes.internal.AttributesNodePostProcessor;
import com.vladsch.flexmark.attributes.internal.NodeAttributeRepository;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.collection.DataValueFactory;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

import java.util.ArrayList;
import java.util.Map;

/**
 * Extension for attributes
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed attributes text is turned into {@link AttributesNode} nodes.
 * </p>
 */
public class AttributesExtension implements Parser.ParserExtension
        , HtmlRenderer.HtmlRendererExtension
        //, Parser.ReferenceHoldingExtension
{
    public static final DataKey<NodeAttributeRepository> NODE_ATTRIBUTES = new DataKey<NodeAttributeRepository>("NODE_ATTRIBUTES", new DataValueFactory<NodeAttributeRepository>() {
        @Override
        public NodeAttributeRepository create(DataHolder options) { return new NodeAttributeRepository(options); }
    });
    public static final DataKey<KeepType> ATTRIBUTES_KEEP = new DataKey<KeepType>("ATTRIBUTES_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates

    private AttributesExtension() {
    }

    public static Extension create() {
        return new AttributesExtension();
    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessorFactory(new AttributesNodePostProcessor.Factory());
        parserBuilder.customInlineParserExtensionFactory(new AttributesInlineParserExtension.Factory());
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {
        if (!options.contains(HtmlRenderer.WRAP_TIGHT_ITEM_PARAGRAPH_IN_SPAN)) {
            // set default to true so tight items get span wrapping which inherit the attributes
            options.set(HtmlRenderer.WRAP_TIGHT_ITEM_PARAGRAPH_IN_SPAN, true);
        }
    }

    @Override
    public void extend(final HtmlRenderer.Builder rendererBuilder, final String rendererType) {
        rendererBuilder.attributeProviderFactory(new AttributesAttributeProvider.Factory());
    }
}
