
package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.definition.internal.DefinitionItemBlockParser;
import com.vladsch.flexmark.ext.definition.internal.DefinitionListBlockPreProcessor;
import com.vladsch.flexmark.ext.definition.internal.DefinitionListItemBlockPreProcessor;
import com.vladsch.flexmark.ext.definition.internal.DefinitionNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for definitions
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed definition text is turned into {@link DefinitionList}, {@link DefinitionTerm} and {@link DefinitionItem} nodes.
 * </p>
 */
public class DefinitionExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    public static final DataKey<Boolean> COLON_MARKER = new DataKey<>("COLON_MARKER", true);
    public static final DataKey<Integer> MARKER_SPACES = new DataKey<>("MARKER_SPACE", 1);
    public static final DataKey<Boolean> TILDE_MARKER = new DataKey<>("TILDE_MARKER", true);

    // TODO: implement formatter for this extension

    private DefinitionExtension() {
    }

    public static Extension create() {
        return new DefinitionExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new DefinitionItemBlockParser.Factory());
        parserBuilder.blockPreProcessorFactory(new DefinitionListItemBlockPreProcessor.Factory());
        parserBuilder.blockPreProcessorFactory(new DefinitionListBlockPreProcessor.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        switch (rendererType) {
            case "HTML":
                rendererBuilder.nodeRendererFactory(new DefinitionNodeRenderer.Factory());
                break;

            case "JIRA":
            case "YOUTRACK":
                break;
        }
    }
}
