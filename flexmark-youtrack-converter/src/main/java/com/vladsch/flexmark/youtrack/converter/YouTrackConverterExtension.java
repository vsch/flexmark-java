package com.vladsch.flexmark.youtrack.converter;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.youtrack.converter.internal.YouTrackConverterNodeRenderer;
import org.jetbrains.annotations.NotNull;

/**
 * Extension for youtrack_converters
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The markdown AST is turned into YOUTRACK formatted text
 * </p>
 */
public class YouTrackConverterExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    private YouTrackConverterExtension() {
    }

    public static YouTrackConverterExtension create() {
        return new YouTrackConverterExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {
        String rendererType = HtmlRenderer.TYPE.get(options);
        if (rendererType.equals("HTML")) {
            // add youtrack equivalence
            HtmlRenderer.addRenderTypeEquivalence(options, "YOUTRACK", "JIRA");
            options.set(HtmlRenderer.TYPE, "YOUTRACK");
        } else if (!rendererType.equals("YOUTRACK")) {
            throw new IllegalStateException("Non HTML Renderer is already set to " + rendererType);
        }
    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("YOUTRACK")) {
            htmlRendererBuilder.nodeRendererFactory(new YouTrackConverterNodeRenderer.Factory());
        } else {
            throw new IllegalStateException("YouTrack Converter Extension used with non YouTrack Renderer " + rendererType);
        }
    }
}
