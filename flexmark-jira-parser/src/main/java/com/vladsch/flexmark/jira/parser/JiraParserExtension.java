package com.vladsch.flexmark.jira.parser;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for jira_parsers
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed jira formatted text is turned into markdown AST nodes.
 * </p>
 */
public class JiraParserExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    private JiraParserExtension() {
    }

    public static Extension create() {
        return new JiraParserExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("HTML")) {//rendererBuilder.nodeRendererFactory(new NodeRendererFactory() {
            //    @Override
            //    public NodeRenderer create(DataHolder options) {
            //        return new AbbreviationNodeRenderer(options);
            //    }
            //});

        } else if (rendererType.equals("JIRA") || rendererType.equals("YOUTRACK")) {
        }
    }
}
