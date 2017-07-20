package com.vladsch.flexmark.ext.gfm.users;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.gfm.users.internal.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for GitHub Users
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed GitHub user text is turned into {@link GfmUser} nodes.
 * </p>
 */
public class GfmUsersExtension implements Parser.ParserExtension
        , HtmlRenderer.HtmlRendererExtension
{
    public static final DataKey<String> GIT_HUB_USERS_URL_ROOT = new DataKey<String>("GIT_HUB_USERS_URL_ROOT", "https://github.com");
    public static final DataKey<String> GIT_HUB_USER_URL_PREFIX = new DataKey<String>("GIT_HUB_USER_URL_PREFIX", "/");
    public static final DataKey<String> GIT_HUB_USER_URL_SUFFIX = new DataKey<String>("GIT_HUB_USER_URL_SUFFIX", "");
    public static final DataKey<String> GIT_HUB_USER_HTML_PREFIX = new DataKey<String>("GIT_HUB_USER_HTML_PREFIX", "<strong>");
    public static final DataKey<String> GIT_HUB_USER_HTML_SUFFIX = new DataKey<String>("GIT_HUB_USER_HTML_SUFFIX", "</strong>");

    private GfmUsersExtension() {
    }

    public static Extension create() {
        return new GfmUsersExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customInlineParserExtensionFactory(new GfmUsersInlineParserExtension.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(new GfmUsersNodeRenderer.Factory());
        } else if (rendererType.equals("JIRA") || rendererType.equals("YOUTRACK")) {
            rendererBuilder.nodeRendererFactory(new GfmUsersJiraRenderer.Factory());
        }
    }
}
