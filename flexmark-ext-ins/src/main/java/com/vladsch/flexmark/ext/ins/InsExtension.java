package com.vladsch.flexmark.ext.ins;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.ins.internal.InsDelimiterProcessor;
import com.vladsch.flexmark.ext.ins.internal.InsJiraRenderer;
import com.vladsch.flexmark.ext.ins.internal.InsNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for ins
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed ins text is turned into {@link Ins} nodes.
 * </p>
 */
public class InsExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    public static final DataKey<String> INS_STYLE_HTML_OPEN = new DataKey<String>("INS_STYLE_HTML_OPEN", (String)null);
    public static final DataKey<String> INS_STYLE_HTML_CLOSE = new DataKey<String>("INS_STYLE_HTML_CLOSE", (String)null);

    private InsExtension() {
    }

    public static Extension create() {
        return new InsExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new InsDelimiterProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererBuilder.isRendererType("HTML")) {
            rendererBuilder.nodeRendererFactory(new InsNodeRenderer.Factory());
        } else if (rendererBuilder.isRendererType("JIRA")) {
            rendererBuilder.nodeRendererFactory(new InsJiraRenderer.Factory());
        }
    }
}
