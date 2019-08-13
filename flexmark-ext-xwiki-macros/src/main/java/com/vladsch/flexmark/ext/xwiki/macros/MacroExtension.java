package com.vladsch.flexmark.ext.xwiki.macros;

import com.vladsch.flexmark.ext.xwiki.macros.internal.MacroBlockParser;
import com.vladsch.flexmark.ext.xwiki.macros.internal.MacroInlineParser;
import com.vladsch.flexmark.ext.xwiki.macros.internal.MacroNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;

/**
 * Extension for macros
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed macros text is turned into {@link Macro} nodes.
 */

// TODO: Rename this class to XWikiMacroExtension
public class MacroExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    public static final DataKey<Boolean> ENABLE_INLINE_MACROS = new DataKey<>("ENABLE_INLINE_MACROS", true);
    public static final DataKey<Boolean> ENABLE_BLOCK_MACROS = new DataKey<>("ENABLE_BLOCK_MACROS", true);
    public static final DataKey<Boolean> ENABLE_RENDERING = new DataKey<>("ENABLE_RENDERING", false);

    private MacroExtension() {
    }

    @Override
    public void rendererOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        rendererBuilder.nodeRendererFactory(new MacroNodeRenderer.Factory());
    }

    public static Extension create() {
        return new MacroExtension();
    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        if (ENABLE_BLOCK_MACROS.getFrom(parserBuilder)) parserBuilder.customBlockParserFactory(new MacroBlockParser.Factory());
        if (ENABLE_INLINE_MACROS.getFrom(parserBuilder)) parserBuilder.customInlineParserExtensionFactory(new MacroInlineParser.Factory());
    }
}
