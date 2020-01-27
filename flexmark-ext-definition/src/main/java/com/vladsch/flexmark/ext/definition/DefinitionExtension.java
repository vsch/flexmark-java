package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.ext.definition.internal.*;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.format.options.DefinitionMarker;
import org.jetbrains.annotations.NotNull;

/**
 * Extension for definitions
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed definition text is turned into {@link DefinitionList}, {@link DefinitionTerm} and {@link DefinitionItem} nodes.
 */
public class DefinitionExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension, Formatter.FormatterExtension {
    final public static DataKey<Boolean> COLON_MARKER = new DataKey<>("COLON_MARKER", true);
    final public static DataKey<Integer> MARKER_SPACES = new DataKey<>("MARKER_SPACE", 1);
    final public static DataKey<Boolean> TILDE_MARKER = new DataKey<>("TILDE_MARKER", true);
    final public static DataKey<Boolean> DOUBLE_BLANK_LINE_BREAKS_LIST = new DataKey<>("DOUBLE_BLANK_LINE_BREAKS_LIST", false);

    final public static DataKey<Integer> FORMAT_MARKER_SPACES = new DataKey<>("MARKER_SPACE", 3);
    final public static DataKey<DefinitionMarker> FORMAT_MARKER_TYPE = new DataKey<>("FORMAT_MARKER_TYPE", DefinitionMarker.ANY);

    private DefinitionExtension() {
    }

    public static DefinitionExtension create() {
        return new DefinitionExtension();
    }

    @Override
    public void extend(Formatter.Builder formatterBuilder) {
        formatterBuilder.nodeFormatterFactory(new DefinitionNodeFormatter.Factory());
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new DefinitionItemBlockParser.Factory());
        parserBuilder.blockPreProcessorFactory(new DefinitionListItemBlockPreProcessor.Factory());
        parserBuilder.blockPreProcessorFactory(new DefinitionListBlockPreProcessor.Factory());
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new DefinitionNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
        }
    }
}
