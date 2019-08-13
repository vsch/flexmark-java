package com.vladsch.flexmark.ext.gfm.users;

import com.vladsch.flexmark.ext.gfm.users.internal.GfmUsersInlineParserExtension;
import com.vladsch.flexmark.ext.gfm.users.internal.GfmUsersJiraRenderer;
import com.vladsch.flexmark.ext.gfm.users.internal.GfmUsersNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;

/**
 * Extension for GitHub Users
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed GitHub user text is turned into {@link GfmUser} nodes.
 */
public class GfmUsersExtension implements Parser.ParserExtension
        , HtmlRenderer.HtmlRendererExtension
{
    public static final DataKey<String> GIT_HUB_USERS_URL_ROOT = new DataKey<>("GIT_HUB_USERS_URL_ROOT", "https://github.com");
    public static final DataKey<String> GIT_HUB_USER_URL_PREFIX = new DataKey<>("GIT_HUB_USER_URL_PREFIX", "/");
    public static final DataKey<String> GIT_HUB_USER_URL_SUFFIX = new DataKey<>("GIT_HUB_USER_URL_SUFFIX", "");
    public static final DataKey<String> GIT_HUB_USER_HTML_PREFIX = new DataKey<>("GIT_HUB_USER_HTML_PREFIX", "<strong>");
    public static final DataKey<String> GIT_HUB_USER_HTML_SUFFIX = new DataKey<>("GIT_HUB_USER_HTML_SUFFIX", "</strong>");

    private GfmUsersExtension() {
    }

    public static Extension create() {
        return new GfmUsersExtension();
    }

    @Override
    public void rendererOptions(MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customInlineParserExtensionFactory(new GfmUsersInlineParserExtension.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererBuilder.isRendererType("HTML")) {
            rendererBuilder.nodeRendererFactory(new GfmUsersNodeRenderer.Factory());
        } else if (rendererBuilder.isRendererType("JIRA")) {
            rendererBuilder.nodeRendererFactory(new GfmUsersJiraRenderer.Factory());
        }
    }
}
