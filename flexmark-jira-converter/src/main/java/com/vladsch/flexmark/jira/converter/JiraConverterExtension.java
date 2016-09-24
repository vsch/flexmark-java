package com.vladsch.flexmark.jira.converter;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.jira.converter.internal.JiraConverterNodeRenderer;
import com.vladsch.flexmark.parser.Parser;

/**
 * Extension for jira_converters
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The markdown AST is turned into JIRA formatted text
 * </p>
 */
public class JiraConverterExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    // final public static DataKey<JiraConverterRepository> JIRA_CONVERTERS = new DataKey<>("JIRA_CONVERTERS", JiraConverterRepository::new); 
    // final public static DataKey<KeepType> JIRA_CONVERTERS_KEEP = new DataKey<>("JIRA_CONVERTERS_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates 
    // final public static DataKey<Boolean> JIRA_CONVERTER_OPTION1 = new DataKey<>("JIRA_CONVERTER_OPTION1", false); 
    // final public static DataKey<String> JIRA_CONVERTER_OPTION2 = new DataKey<>("JIRA_CONVERTER_OPTION2", "default"); 
    // final public static DataKey<Integer> JIRA_CONVERTER_OPTION3 = new DataKey<>("JIRA_CONVERTER_OPTION3", Integer.MAX_VALUE); 
    // final static public DataKey<String> LOCAL_ONLY_TARGET_CLASS = new DataKey<>("LOCAL_ONLY_TARGET_CLASS", "local-only");
    // final static public DataKey<String> MISSING_TARGET_CLASS = new DataKey<>("MISSING_TARGET_CLASS", "absent");
    final static public LinkStatus LOCAL_ONLY = new LinkStatus("LOCAL_ONLY");

    private JiraConverterExtension() {
    }

    public static Extension create() {
        return new JiraConverterExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("HTML")) {
            rendererBuilder.set(HtmlRenderer.TYPE, "JIRA");
            rendererBuilder.nodeRendererFactory(JiraConverterNodeRenderer::new);
            // rendererBuilder.linkResolverFactory(new JiraConverterLinkResolver.Factory());
            // rendererBuilder.attributeProviderFactory(new JiraConverterAttributeProvider.Factory());
        } else {
            throw new IllegalStateException("Non HTML Renderer is already set to " + rendererType);
        }
    }
}
