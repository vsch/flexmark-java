package com.vladsch.flexmark.ext.emoji;

import com.vladsch.flexmark.ext.emoji.internal.EmojiDelimiterProcessor;
import com.vladsch.flexmark.ext.emoji.internal.EmojiJiraRenderer;
import com.vladsch.flexmark.ext.emoji.internal.EmojiNodeFormatter;
import com.vladsch.flexmark.ext.emoji.internal.EmojiNodeRenderer;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Extension for emoji shortcuts using Emoji-Cheat-Sheet.com.
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed emoji shortcuts text regions are turned into {@link Emoji} nodes.
 */
public class EmojiExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension, Formatter.FormatterExtension {
    final public static DataKey<String> ATTR_ALIGN = new DataKey<>("ATTR_ALIGN", "absmiddle");
    final public static DataKey<String> ATTR_IMAGE_SIZE = new DataKey<>("ATTR_IMAGE_SIZE", "20");
    final public static DataKey<String> ATTR_IMAGE_CLASS = new DataKey<>("ATTR_IMAGE_CLASS", "");
    final public static DataKey<String> ROOT_IMAGE_PATH = new DataKey<>("ROOT_IMAGE_PATH", "/img/");
    final public static DataKey<EmojiShortcutType> USE_SHORTCUT_TYPE = new DataKey<>("USE_SHORTCUT_TYPE", EmojiShortcutType.EMOJI_CHEAT_SHEET);
    final public static DataKey<EmojiImageType> USE_IMAGE_TYPE = new DataKey<>("USE_IMAGE_TYPE", EmojiImageType.IMAGE_ONLY);
    final public static DataKey<Boolean> USE_UNICODE_FILE_NAMES = new DataKey<>("USE_UNICODE_FILE_NAMES", false);

    private EmojiExtension() {
    }

    public static EmojiExtension create() {
        return new EmojiExtension();
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Formatter.Builder formatterBuilder) {
        formatterBuilder.nodeFormatterFactory(new EmojiNodeFormatter.Factory());
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new EmojiDelimiterProcessor());
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new EmojiNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
            htmlRendererBuilder.nodeRendererFactory(new EmojiJiraRenderer.Factory());
        }
    }
}
