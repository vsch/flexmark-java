package com.vladsch.flexmark.core.test.util.renderer;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class CoreRendererSpecTest extends RendererSpecTest {
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.OBFUSCATE_EMAIL_RANDOM, false)
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        ArrayList<String> userTags = new ArrayList<>(Parser.HTML_BLOCK_TAGS.get(null));
        userTags.add("tag");
        optionsMap.put("heading-ids", new MutableDataSet().set(HtmlRenderer.RENDER_HEADER_ID, true));
        optionsMap.put("heading-ids-no-dupe-dashes", new MutableDataSet().set(HtmlRenderer.HEADER_ID_GENERATOR_NO_DUPED_DASHES, true));
        optionsMap.put("heading-ids-no-non-ascii-lowercase", new MutableDataSet().set(HtmlRenderer.HEADER_ID_GENERATOR_NON_ASCII_TO_LOWERCASE, false));
        optionsMap.put("heading-ids-no-trim-start", new MutableDataSet().set(HtmlRenderer.HEADER_ID_REF_TEXT_TRIM_LEADING_SPACES, false));
        optionsMap.put("heading-ids-no-trim-end", new MutableDataSet().set(HtmlRenderer.HEADER_ID_REF_TEXT_TRIM_TRAILING_SPACES, false));
        optionsMap.put("heading-ids-emoji", new MutableDataSet().set(HtmlRenderer.HEADER_ID_ADD_EMOJI_SHORTCUT, true));
        optionsMap.put("heading-ids-github", new MutableDataSet()
                .set(HtmlRenderer.HEADER_ID_GENERATOR_TO_DASH_CHARS, " -")
                .set(HtmlRenderer.HEADER_ID_GENERATOR_NON_DASH_CHARS, "_")
                .set(HtmlRenderer.HEADER_ID_GENERATOR_NON_ASCII_TO_LOWERCASE, false)

                // GitHub does not trim trailing spaces of Ref Links in Headings for ID generation
                .set(HtmlRenderer.HEADER_ID_REF_TEXT_TRIM_TRAILING_SPACES, false)
                // GitHub adds emoji shortcut text to heading id
                .set(HtmlRenderer.HEADER_ID_ADD_EMOJI_SHORTCUT, true)
        );
        
        optionsMap.put("user-block-tags", new MutableDataSet().set(Parser.HTML_BLOCK_TAGS, userTags));
        optionsMap.put("obfuscate-email", new MutableDataSet().set(HtmlRenderer.OBFUSCATE_EMAIL, true));
        optionsMap.put("keep-blank-lines", new MutableDataSet().set(Parser.BLANK_LINES_IN_AST, true));
        optionsMap.put("keep-last", new MutableDataSet().set(Parser.REFERENCES_KEEP, KeepType.LAST));
        optionsMap.put("jekyll-macros-in-urls", new MutableDataSet().set(Parser.PARSE_JEKYLL_MACROS_IN_URLS, true));
        optionsMap.put("hdr-no-atx-space", new MutableDataSet().set(Parser.HEADING_NO_ATX_SPACE, true));
        optionsMap.put("no-empty-heading-without-space", new MutableDataSet().set(Parser.HEADING_NO_EMPTY_HEADING_WITHOUT_SPACE, true));
        optionsMap.put("hdr-no-lead-space", new MutableDataSet().set(Parser.HEADING_NO_LEAD_SPACE, true));
        optionsMap.put("list-no-break", new MutableDataSet().set(Parser.LISTS_END_ON_DOUBLE_BLANK, false));
        optionsMap.put("list-break", new MutableDataSet().set(Parser.LISTS_END_ON_DOUBLE_BLANK, true));
        optionsMap.put("list-no-loose", new MutableDataSet().set(Parser.LISTS_AUTO_LOOSE, false));
        optionsMap.put("list-loose-if-prev", new MutableDataSet().set(Parser.LISTS_LOOSE_WHEN_PREV_HAS_TRAILING_BLANK_LINE, true));
        optionsMap.put("list-no-start", new MutableDataSet().set(Parser.LISTS_ORDERED_LIST_MANUAL_START, false));
        optionsMap.put("list-no-bullet-match", new MutableDataSet().set(Parser.LISTS_DELIMITER_MISMATCH_TO_NEW_LIST, false));
        optionsMap.put("list-no-type-match", new MutableDataSet().set(Parser.LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST, false));
        optionsMap.put("bullet-no-para-break", new MutableDataSet().set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH, false));
        optionsMap.put("bullet-no-item-break", new MutableDataSet().set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH, false));
        optionsMap.put("empty-bullet-item-break", new MutableDataSet().set(Parser.LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true));
        optionsMap.put("empty-bullet-no-sub-item-break", new MutableDataSet().set(Parser.LISTS_EMPTY_BULLET_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH, false));
        optionsMap.put("empty-bullet-sub-item-break", new MutableDataSet().set(Parser.LISTS_EMPTY_BULLET_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true));
        optionsMap.put("ordered-no-para-break", new MutableDataSet().set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH, false));
        optionsMap.put("ordered-non-1-para-break", new MutableDataSet().set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH, true));
        optionsMap.put("ordered-no-non-1-para-break", new MutableDataSet().set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH, false));
        optionsMap.put("ordered-no-item-break", new MutableDataSet().set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH, false));
        optionsMap.put("ordered-non-1-item-break", new MutableDataSet().set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true));
        optionsMap.put("ordered-no-non-1-item-break", new MutableDataSet().set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH, false));
        optionsMap.put("list-item-mismatch-to-subitem", new MutableDataSet().set(Parser.LISTS_ITEM_TYPE_MISMATCH_TO_SUB_LIST, true));
        optionsMap.put("thematic-break-no-relaxed-start", new MutableDataSet().set(Parser.THEMATIC_BREAK_RELAXED_START, false));
        optionsMap.put("test-completions", new MutableDataSet().set(Parser.THEMATIC_BREAK_RELAXED_START, false).set(HtmlRenderer.SUPPRESS_INLINE_HTML, true));
        optionsMap.put("escape-html", new MutableDataSet().set(HtmlRenderer.ESCAPE_HTML, true));
        optionsMap.put("escape-html-blocks", new MutableDataSet().set(HtmlRenderer.ESCAPE_HTML_BLOCKS, true));
        optionsMap.put("escape-html-comment-blocks", new MutableDataSet().set(HtmlRenderer.ESCAPE_HTML_COMMENT_BLOCKS, true));
        optionsMap.put("escape-inline-html", new MutableDataSet().set(HtmlRenderer.ESCAPE_INLINE_HTML, true));
        optionsMap.put("escape-inline-html-comments", new MutableDataSet().set(HtmlRenderer.ESCAPE_INLINE_HTML_COMMENTS, true));
        optionsMap.put("suppress-html", new MutableDataSet().set(HtmlRenderer.SUPPRESS_HTML, true));
        optionsMap.put("suppress-html-blocks", new MutableDataSet().set(HtmlRenderer.SUPPRESS_HTML_BLOCKS, true));
        optionsMap.put("suppress-html-comment-blocks", new MutableDataSet().set(HtmlRenderer.SUPPRESS_HTML_COMMENT_BLOCKS, true));
        optionsMap.put("suppress-inline-html", new MutableDataSet().set(HtmlRenderer.SUPPRESS_INLINE_HTML, true));
        optionsMap.put("suppress-inline-html-comments", new MutableDataSet().set(HtmlRenderer.SUPPRESS_INLINE_HTML_COMMENTS, true));
        optionsMap.put("no-class-prefix", new MutableDataSet().set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX, ""));
        
        HashMap<String, String> map = new HashMap<>();
        map.put("latex", "math");
        optionsMap.put("class-map-latex", new MutableDataSet().set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_MAP, map));
        
        optionsMap.put("no-language-class", new MutableDataSet().set(HtmlRenderer.FENCED_CODE_NO_LANGUAGE_CLASS, "nohighlight"));
        optionsMap.put("parse-inner-comments", new MutableDataSet().set(Parser.PARSE_INNER_HTML_COMMENTS, true));
        optionsMap.put("multi-line-image-url", new MutableDataSet().set(Parser.PARSE_MULTI_LINE_IMAGE_URLS, true));
        optionsMap.put("unmatched-fence", new MutableDataSet().set(Parser.MATCH_CLOSING_FENCE_CHARACTERS, false));
        optionsMap.put("dummy-identifier", new MutableDataSet().set(Parser.INTELLIJ_DUMMY_IDENTIFIER, true));
        optionsMap.put("code-no-trim-trailing", new MutableDataSet().set(Parser.INDENTED_CODE_NO_TRAILING_BLANK_LINES, false));
        optionsMap.put("ordered-dot-only", new MutableDataSet().set(Parser.LISTS_ORDERED_ITEM_DOT_ONLY, true));
        optionsMap.put("block-quote-extend", new MutableDataSet().set(Parser.BLOCK_QUOTE_EXTEND_TO_BLANK_LINE, true));
        optionsMap.put("block-ignore-blank", new MutableDataSet().set(Parser.BLOCK_QUOTE_IGNORE_BLANK_LINE, true));
        optionsMap.put("html-block-start-only-on-block-tags", new MutableDataSet().set(Parser.HTML_BLOCK_START_ONLY_ON_BLOCK_TAGS, true));
        optionsMap.put("setext-marker-length", new MutableDataSet().set(Parser.HEADING_SETEXT_MARKER_LENGTH, 3));
        optionsMap.put("src-pos-lines", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_PARAGRAPH_LINES, true).set(Parser.CODE_SOFT_LINE_BREAKS, true));
        optionsMap.put("src-pos-lines-splice", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_PARAGRAPH_LINES, true).set(Parser.CODE_SOFT_LINE_BREAKS, true).set(HtmlRenderer.INLINE_CODE_SPLICE_CLASS, "line-spliced"));
        optionsMap.put("src-wrap-html", new MutableDataSet().set(HtmlRenderer.SOURCE_WRAP_HTML, true));
        optionsMap.put("src-wrap-blocks", new MutableDataSet().set(HtmlRenderer.SOURCE_WRAP_HTML_BLOCKS, true));
        optionsMap.put("hard-line-break-limit", new MutableDataSet().set(Parser.HARD_LINE_BREAK_LIMIT, true));
        optionsMap.put("code-content-block", new MutableDataSet().set(Parser.FENCED_CODE_CONTENT_BLOCK, true));
        optionsMap.put("style-strong-emphasis", new MutableDataSet().set(HtmlRenderer.STRONG_EMPHASIS_STYLE_HTML_OPEN, "<span class=\"text-bold\">").set(HtmlRenderer.STRONG_EMPHASIS_STYLE_HTML_CLOSE, "</span>"));
        optionsMap.put("style-emphasis", new MutableDataSet().set(HtmlRenderer.EMPHASIS_STYLE_HTML_OPEN, "<span class=\"text-italic\">").set(HtmlRenderer.EMPHASIS_STYLE_HTML_CLOSE, "</span>"));
        optionsMap.put("style-code", new MutableDataSet().set(HtmlRenderer.CODE_STYLE_HTML_OPEN, "<span class=\"text-code\">").set(HtmlRenderer.CODE_STYLE_HTML_CLOSE, "</span>"));
        optionsMap.put("url-spaces", new MutableDataSet().set(Parser.SPACE_IN_LINK_URLS, true));
        optionsMap.put("url-jekyll-macros", new MutableDataSet().set(Parser.PARSE_JEKYLL_MACROS_IN_URLS, true));
        optionsMap.put("suppress-format-eol", new MutableDataSet().set(HtmlRenderer.HTML_BLOCK_OPEN_TAG_EOL, false).set(HtmlRenderer.HTML_BLOCK_CLOSE_TAG_EOL, false).set(HtmlRenderer.INDENT_SIZE, 0));
        optionsMap.put("no-unescape-entities", new MutableDataSet().set(HtmlRenderer.UNESCAPE_HTML_ENTITIES, false));
        optionsMap.put("space-in-link-elements", new MutableDataSet().set(Parser.SPACE_IN_LINK_ELEMENTS, true));
        optionsMap.put("www-auto-link-element", new MutableDataSet().set(Parser.WWW_AUTO_LINK_ELEMENT, true));
        optionsMap.put("directional-punctuation", new MutableDataSet().set(Parser.INLINE_DELIMITER_DIRECTIONAL_PUNCTUATIONS, true));
        optionsMap.put("deep-html-parsing", new MutableDataSet().set(Parser.HTML_BLOCK_DEEP_PARSER, true));
        optionsMap.put("code-soft-breaks", new MutableDataSet().set(Parser.CODE_SOFT_LINE_BREAKS, true).set(HtmlRenderer.SOFT_BREAK, "\n"));
        optionsMap.put("code-soft-break-spaces", new MutableDataSet().set(Parser.CODE_SOFT_LINE_BREAKS, true).set(HtmlRenderer.SOFT_BREAK, " \t"));
        optionsMap.put("spec-027", new MutableDataSet().set(Parser.STRONG_WRAPS_EMPHASIS, true));
        optionsMap.put("custom-list-marker", new MutableDataSet().set(Parser.LISTS_ITEM_PREFIX_CHARS, "*/"));
        optionsMap.put("no-p-tags", new MutableDataSet().set(HtmlRenderer.NO_P_TAGS_USE_BR, true));
        optionsMap.put("list-markdown-navigator", new MutableDataSet()
                .set(Parser.LISTS_AUTO_LOOSE, false)
                .set(Parser.LISTS_AUTO_LOOSE, false)
                .set(Parser.LISTS_DELIMITER_MISMATCH_TO_NEW_LIST, false)
                .set(Parser.LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST, false)
                .set(Parser.LISTS_ITEM_TYPE_MISMATCH_TO_SUB_LIST, false)
                .set(Parser.LISTS_END_ON_DOUBLE_BLANK, false)
                .set(Parser.LISTS_ORDERED_ITEM_DOT_ONLY, true)
                .set(Parser.LISTS_ORDERED_LIST_MANUAL_START, false)

                .set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH, true)
                .set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH, true)
                .set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH, true)
                .set(Parser.LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_PARAGRAPH, true)
                .set(Parser.LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_PARAGRAPH, true)
                .set(Parser.LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH, true)
                .set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true)
                .set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true)
                .set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true)
                .set(Parser.LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true)
                .set(Parser.LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true)
                .set(Parser.LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true)
                .set(Parser.LISTS_EMPTY_BULLET_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true)
                .set(Parser.LISTS_EMPTY_ORDERED_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true)
                .set(Parser.LISTS_EMPTY_ORDERED_NON_ONE_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true)
        );
    }
    public CoreRendererSpecTest(@NotNull SpecExample example, @Nullable Map<String, ? extends DataHolder> optionMap, @Nullable DataHolder... defaultOptions) {
        super(example, ComboSpecTestCase.optionsMaps(optionsMap, optionMap), ComboSpecTestCase.dataHolders(OPTIONS, defaultOptions));
    }
}
