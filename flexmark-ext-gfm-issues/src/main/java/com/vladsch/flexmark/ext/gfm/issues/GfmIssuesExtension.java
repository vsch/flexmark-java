package com.vladsch.flexmark.ext.gfm.issues;

import com.vladsch.flexmark.ext.gfm.issues.internal.GfmIssuesInlineParserExtension;
import com.vladsch.flexmark.ext.gfm.issues.internal.GfmIssuesJiraRenderer;
import com.vladsch.flexmark.ext.gfm.issues.internal.GfmIssuesNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;

/**
 * Extension for GitHub Issues
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed GitHub issue text is turned into {@link GfmIssue} nodes.
 */
public class GfmIssuesExtension implements Parser.ParserExtension
        , HtmlRenderer.HtmlRendererExtension
{
    public static final DataKey<String> GIT_HUB_ISSUES_URL_ROOT = new DataKey<>("GIT_HUB_ISSUES_URL_ROOT", "issues");
    public static final DataKey<String> GIT_HUB_ISSUE_URL_PREFIX = new DataKey<>("GIT_HUB_ISSUE_URL_PREFIX", "/");
    public static final DataKey<String> GIT_HUB_ISSUE_URL_SUFFIX = new DataKey<>("GIT_HUB_ISSUE_URL_SUFFIX", "");
    public static final DataKey<String> GIT_HUB_ISSUE_HTML_PREFIX = new DataKey<>("GIT_HUB_ISSUE_HTML_PREFIX", "");
    public static final DataKey<String> GIT_HUB_ISSUE_HTML_SUFFIX = new DataKey<>("GIT_HUB_ISSUE_HTML_SUFFIX", "");

    private GfmIssuesExtension() {
    }

    public static GfmIssuesExtension create() {
        return new GfmIssuesExtension();
    }

    @Override
    public void rendererOptions(MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customInlineParserExtensionFactory(new GfmIssuesInlineParserExtension.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererBuilder.isRendererType("HTML")) {
            rendererBuilder.nodeRendererFactory(new GfmIssuesNodeRenderer.Factory());
        } else if (rendererBuilder.isRendererType("JIRA")) {
            rendererBuilder.nodeRendererFactory(new GfmIssuesJiraRenderer.Factory());
        }
    }
}
