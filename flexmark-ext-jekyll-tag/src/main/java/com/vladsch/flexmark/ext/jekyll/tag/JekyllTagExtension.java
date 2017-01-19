package com.vladsch.flexmark.ext.jekyll.tag;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.jekyll.tag.internal.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.collection.DataValueFactory;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for jekyll_tags
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed jekyll_tag text is turned into {@link JekyllTag} nodes.
 * </p>
 */
public class JekyllTagExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    public static final DataKey<Boolean> ENABLE_INLINE_TAGS = new DataKey<>("ENABLE_INLINE_TAGS", true);
    public static final DataKey<Boolean> ENABLE_BLOCK_TAGS = new DataKey<>("ENABLE_BLOCK_TAGS", true);
    public static final DataKey<Boolean> ENABLE_RENDERING = new DataKey<>("ENABLE_RENDERING", false);

    private JekyllTagExtension() {
    }

    public static Extension create() {
        return new JekyllTagExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        if (parserBuilder.get(ENABLE_BLOCK_TAGS)) parserBuilder.customBlockParserFactory(new JekyllTagBlockParser.Factory());
        if (parserBuilder.get(ENABLE_INLINE_TAGS)) parserBuilder.customInlineParserExtensionFactory(new JekyllTagInlineParserExtension.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if ("HTML".equals(rendererType)) {
            rendererBuilder.nodeRendererFactory(new NodeRendererFactory() {
                @Override
                public NodeRenderer create(DataHolder options) {return new JekyllTagNodeRenderer(options);}
            });
        }
    }
}
