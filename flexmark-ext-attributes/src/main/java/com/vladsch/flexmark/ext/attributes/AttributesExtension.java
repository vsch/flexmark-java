package com.vladsch.flexmark.ext.attributes;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.attributes.internal.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.RendererBuilder;
import com.vladsch.flexmark.html.RendererExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.collection.DataValueFactory;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

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
        , RendererExtension
        , HtmlRenderer.HtmlRendererExtension
        //, Parser.ReferenceHoldingExtension
{
    public static final DataKey<NodeAttributeRepository> NODE_ATTRIBUTES = new DataKey<NodeAttributeRepository>("NODE_ATTRIBUTES", new DataValueFactory<NodeAttributeRepository>() {
        @Override
        public NodeAttributeRepository create(DataHolder options) { return new NodeAttributeRepository(options); }
    });
    public static final DataKey<KeepType> ATTRIBUTES_KEEP = new DataKey<KeepType>("ATTRIBUTES_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates
    public static final DataKey<Boolean> ASSIGN_TEXT_ATTRIBUTES = new DataKey<Boolean>("ASSIGN_TEXT_ATTRIBUTES", false); // assign attributes to text if previous is not a space

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
        if (!ASSIGN_TEXT_ATTRIBUTES.getFrom(options)) {
            // this is need only if text is not wrapped in TextBase and if any attributes are applied, then it is
            // wrapped in a span
            if (!options.contains(HtmlRenderer.WRAP_TIGHT_ITEM_PARAGRAPH_IN_SPAN)) {
                // set default to true so tight items get span wrapping which inherit the attributes
                options.set(HtmlRenderer.WRAP_TIGHT_ITEM_PARAGRAPH_IN_SPAN, true);
            }
        }
    }

    @Override
    public void extend(final HtmlRenderer.Builder rendererBuilder, final String rendererType) {
        if (ASSIGN_TEXT_ATTRIBUTES.getFrom(rendererBuilder)) {
            rendererBuilder.nodeRendererFactory(new AttributesNodeRenderer.Factory());
        }
        rendererBuilder.attributeProviderFactory(new AttributesAttributeProvider.Factory());
    }

    @Override
    public void extend(final RendererBuilder rendererBuilder, final String rendererType) {
        //rendererBuilder.nodeRendererFactory(new AttributesNodeRenderer.Factory());
        rendererBuilder.attributeProviderFactory(new AttributesAttributeProvider.Factory());
    }
}
