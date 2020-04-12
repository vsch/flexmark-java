package com.vladsch.flexmark.util.data;

import com.vladsch.flexmark.util.misc.Extension;

import java.util.Collection;

public class SharedDataKeys {

    // BuilderBase
    final public static DataKey<Collection<Extension>> EXTENSIONS = new DataKey<>("EXTENSIONS", Extension.EMPTY_LIST);

    // Parser
    final public static DataKey<Boolean> HEADING_NO_ATX_SPACE = new DataKey<>("HEADING_NO_ATX_SPACE", false);
    // used to set escaping of # at start independent of HEADING_NO_ATX_SPACE setting if desired
    final public static DataKey<Boolean> ESCAPE_HEADING_NO_ATX_SPACE = new DataKey<>("ESCAPE_HEADING_NO_ATX_SPACE", false, HEADING_NO_ATX_SPACE::get);
    final public static DataKey<Boolean> HTML_FOR_TRANSLATOR = new DataKey<>("HTML_FOR_TRANSLATOR", false);
    final public static DataKey<Boolean> INTELLIJ_DUMMY_IDENTIFIER = new DataKey<>("INTELLIJ_DUMMY_IDENTIFIER", false);
    final public static DataKey<Boolean> PARSE_INNER_HTML_COMMENTS = new DataKey<>("PARSE_INNER_HTML_COMMENTS", false);
    final public static DataKey<Boolean> BLANK_LINES_IN_AST = new DataKey<>("BLANK_LINES_IN_AST", false);
    final public static DataKey<String> TRANSLATION_HTML_BLOCK_TAG_PATTERN = new DataKey<>("TRANSLATION_HTML_BLOCK_TAG_PATTERN", "___(?:\\d+)_");
    final public static DataKey<String> TRANSLATION_HTML_INLINE_TAG_PATTERN = new DataKey<>("TRANSLATION_HTML_INLINE_TAG_PATTERN", "__(?:\\d+)_");
    final public static DataKey<String> TRANSLATION_AUTOLINK_TAG_PATTERN = new DataKey<>("TRANSLATION_AUTOLINK_TAG_PATTERN", "____(?:\\d+)_");

    final public static DataKey<Integer> RENDERER_MAX_TRAILING_BLANK_LINES = new DataKey<>("RENDERER_MAX_TRAILING_BLANK_LINES", 1);
    final public static DataKey<Integer> RENDERER_MAX_BLANK_LINES = new DataKey<>("RENDERER_MAX_BLANK_LINES", 1);
    final public static DataKey<Integer> INDENT_SIZE = new DataKey<>("INDENT_SIZE", 0);
    final public static DataKey<Boolean> PERCENT_ENCODE_URLS = new DataKey<>("PERCENT_ENCODE_URLS", false);
    final public static DataKey<Boolean> HEADER_ID_GENERATOR_RESOLVE_DUPES = new DataKey<>("HEADER_ID_GENERATOR_RESOLVE_DUPES", true);
    final public static DataKey<String> HEADER_ID_GENERATOR_TO_DASH_CHARS = new DataKey<>("HEADER_ID_GENERATOR_TO_DASH_CHARS", " -_");
    final public static DataKey<String> HEADER_ID_GENERATOR_NON_DASH_CHARS = new DataKey<>("HEADER_ID_GENERATOR_NON_DASH_CHARS", "");
    final public static DataKey<Boolean> HEADER_ID_GENERATOR_NO_DUPED_DASHES = new DataKey<>("HEADER_ID_GENERATOR_NO_DUPED_DASHES", false);
    final public static DataKey<Boolean> HEADER_ID_GENERATOR_NON_ASCII_TO_LOWERCASE = new DataKey<>("HEADER_ID_GENERATOR_NON_ASCII_TO_LOWERCASE", true);
    final public static DataKey<Boolean> HEADER_ID_REF_TEXT_TRIM_LEADING_SPACES = new DataKey<>("HEADER_ID_REF_TEXT_TRIM_LEADING_SPACES", true);
    final public static DataKey<Boolean> HEADER_ID_REF_TEXT_TRIM_TRAILING_SPACES = new DataKey<>("HEADER_ID_REF_TEXT_TRIM_TRAILING_SPACES", true);
    final public static DataKey<Boolean> HEADER_ID_ADD_EMOJI_SHORTCUT = new DataKey<>("HEADER_ID_ADD_EMOJI_SHORTCUT", false);
    final public static DataKey<Boolean> RENDER_HEADER_ID = new DataKey<>("RENDER_HEADER_ID", false);
    final public static DataKey<Boolean> GENERATE_HEADER_ID = new DataKey<>("GENERATE_HEADER_ID", true);
    final public static DataKey<Boolean> DO_NOT_RENDER_LINKS = new DataKey<>("DO_NOT_RENDER_LINKS", false);

    // Formatter
    final public static DataKey<Integer> FORMATTER_MAX_BLANK_LINES = new DataKey<>("FORMATTER_MAX_BLANK_LINES", 2);
    final public static DataKey<Integer> FORMATTER_MAX_TRAILING_BLANK_LINES = new DataKey<>("FORMATTER_MAX_TRAILING_BLANK_LINES", 1);
    final public static DataKey<Boolean> BLOCK_QUOTE_BLANK_LINES = new DataKey<>("BLOCK_QUOTE_BLANK_LINES", true);

    final public static DataKey<Boolean> APPLY_SPECIAL_LEAD_IN_HANDLERS = new DataKey<>("APPLY_SPECIAL_LEAD_IN_HANDLERS", true);
    final public static DataKey<Boolean> ESCAPE_SPECIAL_CHARS = new DataKey<>("ESCAPE_SPECIAL_CHARS", true, APPLY_SPECIAL_LEAD_IN_HANDLERS::get);
    final public static DataKey<Boolean> ESCAPE_NUMBERED_LEAD_IN = new DataKey<>("ESCAPE_NUMBERED_LEAD_IN", true, APPLY_SPECIAL_LEAD_IN_HANDLERS::get);
    final public static DataKey<Boolean> UNESCAPE_SPECIAL_CHARS = new DataKey<>("UNESCAPE_SPECIAL_CHARS", true, APPLY_SPECIAL_LEAD_IN_HANDLERS::get);
    final public static DataKey<Boolean> RUNNING_TESTS = new DataKey<>("RUNNING_TESTS", false);
}
