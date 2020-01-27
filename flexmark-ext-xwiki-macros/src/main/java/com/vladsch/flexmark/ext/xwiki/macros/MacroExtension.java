package com.vladsch.flexmark.ext.xwiki.macros;

import com.vladsch.flexmark.ext.xwiki.macros.internal.MacroBlockParser;
import com.vladsch.flexmark.ext.xwiki.macros.internal.MacroInlineParser;
import com.vladsch.flexmark.ext.xwiki.macros.internal.MacroNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Extension for macros
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed macros text is turned into {@link Macro} nodes.
 */

// TODO: Rename this class to XWikiMacroExtension
public class MacroExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    final public static DataKey<Boolean> ENABLE_INLINE_MACROS = new DataKey<>("ENABLE_INLINE_MACROS", true);
    final public static DataKey<Boolean> ENABLE_BLOCK_MACROS = new DataKey<>("ENABLE_BLOCK_MACROS", true);
    final public static DataKey<Boolean> ENABLE_RENDERING = new DataKey<>("ENABLE_RENDERING", false);

    private MacroExtension() {
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        htmlRendererBuilder.nodeRendererFactory(new MacroNodeRenderer.Factory());
    }

    public static MacroExtension create() {
        return new MacroExtension();
    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        if (ENABLE_BLOCK_MACROS.get(parserBuilder)) parserBuilder.customBlockParserFactory(new MacroBlockParser.Factory());
        if (ENABLE_INLINE_MACROS.get(parserBuilder)) parserBuilder.customInlineParserExtensionFactory(new MacroInlineParser.Factory());
    }
}
