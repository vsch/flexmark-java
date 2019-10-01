package com.vladsch.flexmark.ext.jekyll.tag;

import com.vladsch.flexmark.ext.jekyll.tag.internal.JekyllTagBlockParser;
import com.vladsch.flexmark.ext.jekyll.tag.internal.JekyllTagInlineParserExtension;
import com.vladsch.flexmark.ext.jekyll.tag.internal.JekyllTagNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.DataValueFactory;
import com.vladsch.flexmark.util.data.MutableDataHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Extension for jekyll_tags
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed jekyll_tag text is turned into {@link JekyllTag} nodes.
 */
public class JekyllTagExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    public static final DataKey<Boolean> ENABLE_INLINE_TAGS = new DataKey<>("ENABLE_INLINE_TAGS", true);
    public static final DataKey<Boolean> ENABLE_BLOCK_TAGS = new DataKey<>("ENABLE_BLOCK_TAGS", true);
    public static final DataKey<Boolean> ENABLE_RENDERING = new DataKey<>("ENABLE_RENDERING", false);
    public static final DataKey<Boolean> LIST_INCLUDES_ONLY = new DataKey<>("LIST_INCLUDES_ONLY", true);
    public static final DataKey<Map<String, String>> INCLUDED_HTML = new DataKey<>("INCLUDED_HTML", (Map<String, String>) null);
    //public static final DataKey<Map<String, Document>> INCLUDED_DOCUMENTS = new DataKey<Map<String, Document>>("INCLUDED_DOCUMENTS", (Map<String, Document>) null);
    public static final DataKey<List<JekyllTag>> TAG_LIST = new DataKey<>("TAG_LIST", options -> new ArrayList<>());

    private JekyllTagExtension() {
    }

    public static JekyllTagExtension create() {
        return new JekyllTagExtension();
    }

    @Override
    public void rendererOptions(MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        if (parserBuilder.get(ENABLE_BLOCK_TAGS)) parserBuilder.customBlockParserFactory(new JekyllTagBlockParser.Factory());
        if (parserBuilder.get(ENABLE_INLINE_TAGS)) parserBuilder.customInlineParserExtensionFactory(new JekyllTagInlineParserExtension.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if ("HTML".equals(rendererType)) {
            rendererBuilder.nodeRendererFactory(new JekyllTagNodeRenderer.Factory());
        }
    }
}
