package com.vladsch.flexmark.ext.xwiki.macros;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.xwiki.macros.internal.*;
import com.vladsch.flexmark.ext.xwiki.macros.internal.MacroBlockParser;
import com.vladsch.flexmark.ext.xwiki.macros.internal.MacroNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for macross
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed macros text is turned into {@link Macro} nodes.
 * </p>
 */
public class MacroExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    // public static final DataKey<KeepType> MACROSS_KEEP = new DataKey<>("MACROSS_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates
    public static final DataKey<Boolean> MACROS_OPTION1 = new DataKey<>("MACROS_OPTION1", false);
    public static final DataKey<String> MACROS_OPTION2 = new DataKey<>("MACROS_OPTION2", "default");
    public static final DataKey<Integer> MACROS_OPTION3 = new DataKey<>("MACROS_OPTION3", Integer.MAX_VALUE);
    // public static final DataKey<String> LOCAL_ONLY_TARGET_CLASS = new DataKey<>("LOCAL_ONLY_TARGET_CLASS", "local-only");
    // public static final DataKey<String> MISSING_TARGET_CLASS = new DataKey<>("MISSING_TARGET_CLASS", "absent");
    public static final LinkStatus LOCAL_ONLY = new LinkStatus("LOCAL_ONLY");

    private MacroExtension() {
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(final HtmlRenderer.Builder rendererBuilder, final String rendererType) {
        rendererBuilder.nodeRendererFactory(new NodeRendererFactory() {
            @Override
            public NodeRenderer create(DataHolder options) {return new MacroNodeRenderer(options);}
        });
    }

    public static Extension create() {
        return new MacroExtension();
    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new MacroBlockParser.Factory());
        parserBuilder.customInlineParserExtensionFactory(new MacroInlineParser.Factory());
    }
}
