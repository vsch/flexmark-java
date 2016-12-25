package com.vladsch.flexmark.ext.aside;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.aside.internal.AsideBlockParser;
import com.vladsch.flexmark.ext.aside.internal.AsideNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

/**
 * Extension for ext_asides
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed pipe prefixed text is turned into {@link AsideBlock} nodes.
 * </p>
 */
public class AsideExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    public static final DataKey<Boolean> EXTEND_TO_BLANK_LINE = new DataKey<>("EXTEND_TO_BLANK_LINE", false);
    public static final DataKey<Boolean> IGNORE_BLANK_LINE = new DataKey<>("IGNORE_BLANK_LINE", false);
    public static final LinkStatus LOCAL_ONLY = new LinkStatus("LOCAL_ONLY");

    private AsideExtension() {
    }

    public static Extension create() {
        return new AsideExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new AsideBlockParser.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("JIRA") || rendererType.equals("YOUTRACK")) {
            // rendererBuilder.nodeRendererFactory(ExtAsideJiraRenderer::new);
        } else if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(new NodeRendererFactory() {
                @Override
                public NodeRenderer create(DataHolder options) {
                    return new AsideNodeRenderer(options);
                }
            });
            // rendererBuilder.linkResolverFactory(new ExtAsideLinkResolver.Factory());
            // rendererBuilder.attributeProviderFactory(new ExtAsideAttributeProvider.Factory());
        }
    }
}
