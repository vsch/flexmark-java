package com.vladsch.flexmark.ext.escaped.character;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.escaped.character.internal.EscapedCharacterNodePostProcessor;
import com.vladsch.flexmark.ext.escaped.character.internal.EscapedCharacterNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for escaped_characters
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed escaped_character text is turned into {@link EscapedCharacter} nodes.
 * </p>
 */
public class EscapedCharacterExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    private EscapedCharacterExtension() {
    }

    public static Extension create() {
        return new EscapedCharacterExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessorFactory(new EscapedCharacterNodePostProcessor.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(new EscapedCharacterNodeRenderer.Factory());
        } else if (rendererType.equals("JIRA") || rendererType.equals("YOUTRACK")) {
        }
    }
}
