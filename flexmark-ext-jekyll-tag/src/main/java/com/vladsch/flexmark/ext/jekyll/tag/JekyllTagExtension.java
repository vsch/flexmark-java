package com.vladsch.flexmark.ext.jekyll.tag;

import com.vladsch.flexmark.ext.jekyll.tag.internal.*;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.NullableDataKey;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Extension for jekyll_tags
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed jekyll_tag text is turned into {@link JekyllTag} nodes.
 */
public class JekyllTagExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension, Formatter.FormatterExtension {
    final public static DataKey<Boolean> ENABLE_INLINE_TAGS = new DataKey<>("ENABLE_INLINE_TAGS", true);
    final public static DataKey<Boolean> ENABLE_BLOCK_TAGS = new DataKey<>("ENABLE_BLOCK_TAGS", true);
    final public static DataKey<Boolean> ENABLE_RENDERING = new DataKey<>("ENABLE_RENDERING", false);
    final public static DataKey<Boolean> LIST_INCLUDES_ONLY = new DataKey<>("LIST_INCLUDES_ONLY", true);
    final public static DataKey<Boolean> EMBED_INCLUDED_CONTENT = new DataKey<>("EMBED_INCLUDED_CONTENT", true);
    final public static DataKey<List<LinkResolverFactory>> LINK_RESOLVER_FACTORIES = new DataKey<>("LINK_RESOLVER_FACTORIES", Collections.emptyList());
    final public static NullableDataKey<Map<String, String>> INCLUDED_HTML = new NullableDataKey<>("INCLUDED_HTML");
    final public static DataKey<List<JekyllTag>> TAG_LIST = new DataKey<>("TAG_LIST", ArrayList::new);

    private JekyllTagExtension() {
    }

    public static JekyllTagExtension create() {
        return new JekyllTagExtension();
    }

    @Override
    public void extend(Formatter.Builder formatterBuilder) {
        formatterBuilder.nodeFormatterFactory(new JekyllTagNodeFormatter.Factory());
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        if (ENABLE_BLOCK_TAGS.get(parserBuilder)) parserBuilder.customBlockParserFactory(new JekyllTagBlockParser.Factory());
        if (ENABLE_INLINE_TAGS.get(parserBuilder)) parserBuilder.customInlineParserExtensionFactory(new JekyllTagInlineParserExtension.Factory());
        
        Map<String, String> includeMap = INCLUDED_HTML.get(parserBuilder);
        if (includeMap != null && !includeMap.isEmpty() || !LINK_RESOLVER_FACTORIES.get(parserBuilder).isEmpty()) {
            parserBuilder.postProcessorFactory(new IncludeNodePostProcessor.Factory());
        }
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if ("HTML".equals(rendererType)) {
            htmlRendererBuilder.nodeRendererFactory(new JekyllTagNodeRenderer.Factory());
        }
    }
}
