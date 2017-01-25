package com.vladsch.flexmark.ext.ins;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.ins.internal.InsDelimiterProcessor;
import com.vladsch.flexmark.ext.ins.internal.InsJiraRenderer;
import com.vladsch.flexmark.ext.ins.internal.InsNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
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
        switch (rendererType) {
            case "HTML":
                rendererBuilder.nodeRendererFactory(new InsNodeRenderer.Factory());
                break;
            case "JIRA":
            case "YOUTRACK":
                rendererBuilder.nodeRendererFactory(new InsJiraRenderer.Factory());
                break;
        }
    }
}
