package com.vladsch.flexmark.jira.parser;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.parser.Parser;

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
    // public static final DataKey<JiraParserRepository> JIRA_PARSERS = new DataKey<>("JIRA_PARSERS", JiraParserRepository::new);
    // public static final DataKey<KeepType> JIRA_PARSERS_KEEP = new DataKey<>("JIRA_PARSERS_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates
    //public static final DataKey<Boolean> JIRA_PARSER_OPTION1 = new DataKey<>("JIRA_PARSER_OPTION1", false);
    //public static final DataKey<String> JIRA_PARSER_OPTION2 = new DataKey<>("JIRA_PARSER_OPTION2", "default");
    //public static final DataKey<Integer> JIRA_PARSER_OPTION3 = new DataKey<>("JIRA_PARSER_OPTION3", Integer.MAX_VALUE);
    // public static final DataKey<String> LOCAL_ONLY_TARGET_CLASS = new DataKey<>("LOCAL_ONLY_TARGET_CLASS", "local-only");
    // public static final DataKey<String> MISSING_TARGET_CLASS = new DataKey<>("MISSING_TARGET_CLASS", "absent");
    public static final LinkStatus LOCAL_ONLY = new LinkStatus("LOCAL_ONLY");

    private JiraParserExtension() {
    }

    public static Extension create() {
        return new JiraParserExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("JIRA") || rendererType.equals("YOUTRACK")) {
            // rendererBuilder.nodeRendererFactory(JiraParserJiraRenderer::new);
        } else if (rendererType.equals("HTML")) {
            // rendererBuilder.nodeRendererFactory(JiraParserNodeRenderer::new);
            // rendererBuilder.linkResolverFactory(new JiraParserLinkResolver.Factory());
            // rendererBuilder.attributeProviderFactory(new JiraParserAttributeProvider.Factory());
        }
    }
}
