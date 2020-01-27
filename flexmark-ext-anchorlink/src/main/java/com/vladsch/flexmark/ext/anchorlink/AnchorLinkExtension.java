package com.vladsch.flexmark.ext.anchorlink;

import com.vladsch.flexmark.ext.anchorlink.internal.AnchorLinkNodePostProcessor;
import com.vladsch.flexmark.ext.anchorlink.internal.AnchorLinkNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Extension for anchor links
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed anchorlink text is turned into {@link AnchorLink} nodes.
 */
public class AnchorLinkExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    final public static DataKey<Boolean> ANCHORLINKS_WRAP_TEXT = new DataKey<>("ANCHORLINKS_WRAP_TEXT", true);
    final public static DataKey<String> ANCHORLINKS_TEXT_PREFIX = new DataKey<>("ANCHORLINKS_TEXT_PREFIX", "");
    final public static DataKey<String> ANCHORLINKS_TEXT_SUFFIX = new DataKey<>("ANCHORLINKS_TEXT_SUFFIX", "");
    final public static DataKey<String> ANCHORLINKS_ANCHOR_CLASS = new DataKey<>("ANCHORLINKS_ANCHOR_CLASS", "");
    final public static DataKey<Boolean> ANCHORLINKS_SET_NAME = new DataKey<>("ANCHORLINKS_SET_NAME", false);
    final public static DataKey<Boolean> ANCHORLINKS_SET_ID = new DataKey<>("ANCHORLINKS_SET_ID", true);
    final public static DataKey<Boolean> ANCHORLINKS_NO_BLOCK_QUOTE = new DataKey<>("ANCHORLINKS_NO_BLOCK_QUOTE", false);

    private AnchorLinkExtension() {
    }

    public static AnchorLinkExtension create() {
        return new AnchorLinkExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessorFactory(new AnchorLinkNodePostProcessor.Factory(parserBuilder));
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new AnchorLinkNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
        }
    }
}
