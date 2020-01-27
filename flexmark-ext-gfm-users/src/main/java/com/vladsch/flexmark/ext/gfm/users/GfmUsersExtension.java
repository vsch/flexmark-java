package com.vladsch.flexmark.ext.gfm.users;

import com.vladsch.flexmark.ext.gfm.users.internal.GfmUsersInlineParserExtension;
import com.vladsch.flexmark.ext.gfm.users.internal.GfmUsersJiraRenderer;
import com.vladsch.flexmark.ext.gfm.users.internal.GfmUsersNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

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
    final public static DataKey<String> GIT_HUB_USERS_URL_ROOT = new DataKey<>("GIT_HUB_USERS_URL_ROOT", "https://github.com");
    final public static DataKey<String> GIT_HUB_USER_URL_PREFIX = new DataKey<>("GIT_HUB_USER_URL_PREFIX", "/");
    final public static DataKey<String> GIT_HUB_USER_URL_SUFFIX = new DataKey<>("GIT_HUB_USER_URL_SUFFIX", "");
    final public static DataKey<String> GIT_HUB_USER_HTML_PREFIX = new DataKey<>("GIT_HUB_USER_HTML_PREFIX", "<strong>");
    final public static DataKey<String> GIT_HUB_USER_HTML_SUFFIX = new DataKey<>("GIT_HUB_USER_HTML_SUFFIX", "</strong>");

    private GfmUsersExtension() {
    }

    public static GfmUsersExtension create() {
        return new GfmUsersExtension();
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customInlineParserExtensionFactory(new GfmUsersInlineParserExtension.Factory());
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new GfmUsersNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
            htmlRendererBuilder.nodeRendererFactory(new GfmUsersJiraRenderer.Factory());
        }
    }
}
