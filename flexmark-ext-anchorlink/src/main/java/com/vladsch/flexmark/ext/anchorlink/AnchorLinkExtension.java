package com.vladsch.flexmark.ext.anchorlink;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.anchorlink.internal.AnchorLinkNodePostProcessor;
import com.vladsch.flexmark.ext.anchorlink.internal.AnchorLinkNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.internal.util.collection.DataKey;
import com.vladsch.flexmark.parser.Parser;

/**
 * Extension for anchorlinkss
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed anchorlink text is turned into {@link AnchorLink} nodes.
 * </p>
 */
public class AnchorLinkExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    final public static DataKey<Boolean> ANCHORLINKS_NO_WRAP_TEXT = new DataKey<>("ANCHORLINKS_NO_WRAP_TEXT", false);
    final public static DataKey<String> ANCHORLINKS_TEXT_PREFIX = new DataKey<>("ANCHORLINKS_TEXT_PREFIX", "");
    final public static DataKey<String> ANCHORLINKS_TEXT_SUFFIX = new DataKey<>("ANCHORLINKS_TEXT_SUFFIX", "");
    final public static DataKey<String> ANCHORLINKS_ANCHOR_CLASS = new DataKey<>("ANCHORLINKS_ANCHOR_CLASS", "");
    final public static DataKey<Boolean> ANCHORLINKS_SET_NAME = new DataKey<>("ANCHORLINKS_SET_NAME", false);
    final public static DataKey<Boolean> ANCHORLINKS_SET_ID = new DataKey<>("ANCHORLINKS_SET_ID", true);
    final public static DataKey<Boolean> ANCHORLINKS_NO_BLOCK_QUOTE = new DataKey<>("ANCHORLINKS_NO_BLOCK_QUOTE", false);

    private AnchorLinkExtension() {
    }

    public static Extension create() {
        return new AnchorLinkExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        //parserBuilder.blockPreProcessorFactory(new AnchorLinkBlockPreProcessor.Factory());
        parserBuilder.postProcessorFactory(new AnchorLinkNodePostProcessor.Factory(parserBuilder));
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(AnchorLinkNodeRenderer::new);
    }
}
