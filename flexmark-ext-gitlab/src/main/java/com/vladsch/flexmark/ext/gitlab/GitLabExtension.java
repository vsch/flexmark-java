package com.vladsch.flexmark.ext.gitlab;

import com.vladsch.flexmark.ext.gitlab.internal.*;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Extension for git_labs
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * </p>
 * <p>
 * The parsed GitLab Flavoured Markdown
 * </p>
 */
public class GitLabExtension implements Parser.ParserExtension
        , HtmlRenderer.HtmlRendererExtension
        , Formatter.FormatterExtension
        // , Parser.ReferenceHoldingExtension
{
    final private static String[] DEFAULT_MATH_LANGUAGES = {"math"};
    final private static String[] DEFAULT_MERMAID_LANGUAGES = {"mermaid"};
    
    final public static DataKey<Boolean> INS_PARSER = new DataKey<>("INS_PARSER", true);
    final public static DataKey<Boolean> DEL_PARSER = new DataKey<>("DEL_PARSER", true);
    final public static DataKey<Boolean> BLOCK_QUOTE_PARSER = new DataKey<>("BLOCK_QUOTE_PARSER", true);
    final public static DataKey<Boolean> NESTED_BLOCK_QUOTES = new DataKey<>("NESTED_BLOCK_QUOTES", true);
    final public static DataKey<Boolean> INLINE_MATH_PARSER = new DataKey<>("INLINE_MATH_PARSER", true);
    final public static DataKey<Boolean> RENDER_BLOCK_MATH = new DataKey<>("RENDER_BLOCK_MATH", true);
    final public static DataKey<Boolean> RENDER_BLOCK_MERMAID = new DataKey<>("RENDER_BLOCK_MERMAID", true);
    final public static DataKey<Boolean> RENDER_VIDEO_IMAGES = new DataKey<>("RENDER_VIDEO_IMAGES", true);
    final public static DataKey<Boolean> RENDER_VIDEO_LINK = new DataKey<>("RENDER_VIDEO_LINK", true);

    final public static DataKey<String[]> MATH_LANGUAGES = new DataKey<>("MATH_LANGUAGES", DEFAULT_MATH_LANGUAGES);
    final public static DataKey<String[]> MERMAID_LANGUAGES = new DataKey<>("MERMAID_LANGUAGES", DEFAULT_MERMAID_LANGUAGES);
    final public static DataKey<String> INLINE_MATH_CLASS = new DataKey<>("INLINE_MATH_CLASS", "katex");
    final public static DataKey<String> BLOCK_MATH_CLASS = new DataKey<>("BLOCK_MATH_CLASS", "katex");
    final public static DataKey<String> BLOCK_MERMAID_CLASS = new DataKey<>("BLOCK_MERMAID_CLASS", "mermaid");
    final public static DataKey<String> VIDEO_IMAGE_CLASS = new DataKey<>("VIDEO_IMAGE_CLASS", "video-container");
    final public static DataKey<String> VIDEO_IMAGE_LINK_TEXT_FORMAT = new DataKey<>("VIDEO_IMAGE_LINK_TEXT_FORMAT", "Download '%s'");

    /**
     * @deprecated use {@link HtmlRenderer#FENCED_CODE_LANGUAGE_DELIMITERS} instead
     */
    @Deprecated
    final public static DataKey<String> BLOCK_INFO_DELIMITERS = HtmlRenderer.FENCED_CODE_LANGUAGE_DELIMITERS;
    final public static DataKey<String> VIDEO_IMAGE_EXTENSIONS = new DataKey<>("VIDEO_IMAGE_EXTENSIONS", "mp4,m4v,mov,webm,ogv");

    private GitLabExtension() {
    }

    public static GitLabExtension create() {
        return new GitLabExtension();
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Formatter.Builder formatterBuilder) {
        formatterBuilder.nodeFormatterFactory(new GitLabNodeFormatter.Factory());
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        GitLabOptions options = new GitLabOptions(parserBuilder);
        if (options.blockQuoteParser) {
            parserBuilder.customBlockParserFactory(new GitLabBlockQuoteParser.Factory());
        }

        if (options.delParser || options.insParser) {
            parserBuilder.customInlineParserExtensionFactory(new GitLabInlineParser.Factory());
        }

        if (options.inlineMathParser) {
            parserBuilder.customInlineParserExtensionFactory(new GitLabInlineMathParser.Factory());
        }
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new GitLabNodeRenderer.Factory());
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
            //rendererBuilder.nodeRendererFactory(new GitLabJiraRenderer.Factory());
        }
    }
}
