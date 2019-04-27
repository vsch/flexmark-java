package com.vladsch.flexmark.ext.emoji;

import com.vladsch.flexmark.ext.emoji.internal.EmojiDelimiterProcessor;
import com.vladsch.flexmark.ext.emoji.internal.EmojiJiraRenderer;
import com.vladsch.flexmark.ext.emoji.internal.EmojiNodeFormatter;
import com.vladsch.flexmark.ext.emoji.internal.EmojiNodeRenderer;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;

/**
 * Extension for emoji shortcuts using Emoji-Cheat-Sheet.com.
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed emoji shortcuts text regions are turned into {@link Emoji} nodes.
 */
public class EmojiExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension, Formatter.FormatterExtension {
    public static final DataKey<String> ATTR_ALIGN = new DataKey<>("ATTR_ALIGN", "absmiddle");
    public static final DataKey<String> ATTR_IMAGE_SIZE = new DataKey<>("ATTR_IMAGE_SIZE", "20");
    public static final DataKey<String> ATTR_IMAGE_CLASS = new DataKey<>("ATTR_IMAGE_CLASS", "");
    public static final DataKey<String> ROOT_IMAGE_PATH = new DataKey<>("ROOT_IMAGE_PATH", "/img/");
    public static final DataKey<EmojiShortcutType> USE_SHORTCUT_TYPE = new DataKey<>("USE_SHORTCUT_TYPE", EmojiShortcutType.EMOJI_CHEAT_SHEET);
    public static final DataKey<EmojiImageType> USE_IMAGE_TYPE = new DataKey<>("USE_IMAGE_TYPE", EmojiImageType.IMAGE_ONLY);

    private EmojiExtension() {
    }

    public static Extension create() {
        return new EmojiExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public void extend(final Formatter.Builder builder) {
        builder.nodeFormatterFactory(new EmojiNodeFormatter.Factory());
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new EmojiDelimiterProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererBuilder.isRendererType("HTML")) {
            rendererBuilder.nodeRendererFactory(new EmojiNodeRenderer.Factory());
        } else if (rendererBuilder.isRendererType("JIRA")) {
            rendererBuilder.nodeRendererFactory(new EmojiJiraRenderer.Factory());
        }
    }
}
