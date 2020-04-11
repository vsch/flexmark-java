package com.vladsch.flexmark.core.test.util;

import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.data.SharedDataKeys;
import com.vladsch.flexmark.util.format.options.*;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class FormatterTranslationSpecTestBase extends ComboSpecTestCase {
    final static boolean SKIP_IGNORED_TESTS = true;
    final public static DataKey<Boolean> SHOW_LINE_RANGES = new DataKey<>("SHOW_LINE_RANGES", false);
    final public static DataKey<Character> EDIT_OP_CHAR = new DataKey<>("EDIT_OP_CHAR", SequenceUtils.NUL);
    final public static DataKey<Integer> EDIT_OP = new DataKey<>("EDIT_OP", 0);

    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.BLANK_LINES_IN_AST, true)
            .set(Parser.HEADING_NO_ATX_SPACE, true)
            .toImmutable();

    final private static DataHolder FIXED_INDENT_OPTIONS = new MutableDataSet().setFrom(ParserEmulationProfile.FIXED_INDENT)
            .toMutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("IGNORED", new MutableDataSet().set(TestUtils.IGNORE, SKIP_IGNORED_TESTS));
        optionsMap.put("show-ranges", new MutableDataSet().set(SHOW_LINE_RANGES, true));
        optionsMap.put("running-tests", new MutableDataSet().set(SharedDataKeys.RUNNING_TESTS, true));

        optionsMap.put("insert-char", new MutableDataSet().set(EDIT_OP, 1).set(EDIT_OP_CHAR, '\0'));
        optionsMap.put("insert-space", new MutableDataSet().set(EDIT_OP, 1).set(EDIT_OP_CHAR, ' '));
        optionsMap.put("delete-char", new MutableDataSet().set(EDIT_OP, -1).set(EDIT_OP_CHAR, '\0'));
        optionsMap.put("delete-space", new MutableDataSet().set(EDIT_OP, -1).set(EDIT_OP_CHAR, ' '));
//        optionsMap.put("indent-edit", new MutableDataSet().set(EDIT_INDENT, true));
        optionsMap.put("restore-tracked-spaces", new MutableDataSet().set(Formatter.RESTORE_TRACKED_SPACES, true));
        optionsMap.put("multi-line-image-url", new MutableDataSet().set(Parser.PARSE_MULTI_LINE_IMAGE_URLS, true));

        optionsMap.put("format-fixed-indent", new MutableDataSet().set(Formatter.FORMATTER_EMULATION_PROFILE, ParserEmulationProfile.FIXED_INDENT));
        optionsMap.put("format-content-after-prefix", new MutableDataSet().set(Formatter.LISTS_ITEM_CONTENT_AFTER_SUFFIX, true));
        optionsMap.put("no-list-auto-loose", new MutableDataSet().set(Parser.LISTS_AUTO_LOOSE, false));
        optionsMap.put("parse-fixed-indent", FIXED_INDENT_OPTIONS);
        optionsMap.put("format-github", new MutableDataSet().set(Formatter.FORMATTER_EMULATION_PROFILE, ParserEmulationProfile.GITHUB_DOC));
        optionsMap.put("parse-github", new MutableDataSet().set(Parser.PARSER_EMULATION_PROFILE, ParserEmulationProfile.GITHUB_DOC));
        optionsMap.put("max-blank-lines-1", new MutableDataSet().set(Formatter.MAX_BLANK_LINES, 1));
        optionsMap.put("max-blank-lines-2", new MutableDataSet().set(Formatter.MAX_BLANK_LINES, 2));
        optionsMap.put("max-blank-lines-3", new MutableDataSet().set(Formatter.MAX_BLANK_LINES, 3));
        optionsMap.put("no-tailing-blanks", new MutableDataSet().set(Formatter.MAX_TRAILING_BLANK_LINES, 0));
        optionsMap.put("list-content-after-suffix", new MutableDataSet().set(Formatter.LISTS_ITEM_CONTENT_AFTER_SUFFIX, true));
        optionsMap.put("atx-space-as-is", new MutableDataSet().set(Formatter.SPACE_AFTER_ATX_MARKER, DiscretionaryText.AS_IS));
        optionsMap.put("atx-space-add", new MutableDataSet().set(Formatter.SPACE_AFTER_ATX_MARKER, DiscretionaryText.ADD));
        optionsMap.put("atx-space-remove", new MutableDataSet().set(Formatter.SPACE_AFTER_ATX_MARKER, DiscretionaryText.REMOVE));
        optionsMap.put("heading-any", new MutableDataSet().set(Formatter.HEADING_STYLE, HeadingStyle.AS_IS));
        optionsMap.put("heading-atx", new MutableDataSet().set(Formatter.HEADING_STYLE, HeadingStyle.ATX_PREFERRED));
        optionsMap.put("heading-setext", new MutableDataSet().set(Formatter.HEADING_STYLE, HeadingStyle.SETEXT_PREFERRED));
        optionsMap.put("setext-no-equalize", new MutableDataSet().set(Formatter.SETEXT_HEADING_EQUALIZE_MARKER, false));
        optionsMap.put("atx-trailing-as-is", new MutableDataSet().set(Formatter.ATX_HEADING_TRAILING_MARKER, EqualizeTrailingMarker.AS_IS));
        optionsMap.put("atx-trailing-add", new MutableDataSet().set(Formatter.ATX_HEADING_TRAILING_MARKER, EqualizeTrailingMarker.ADD));
        optionsMap.put("atx-trailing-equalize", new MutableDataSet().set(Formatter.ATX_HEADING_TRAILING_MARKER, EqualizeTrailingMarker.EQUALIZE));
        optionsMap.put("atx-trailing-remove", new MutableDataSet().set(Formatter.ATX_HEADING_TRAILING_MARKER, EqualizeTrailingMarker.REMOVE));
        optionsMap.put("thematic-break", new MutableDataSet().set(Formatter.THEMATIC_BREAK, "*** ** * ** ***"));
        optionsMap.put("no-block-quote-blank-lines", new MutableDataSet().set(Formatter.BLOCK_QUOTE_BLANK_LINES, false));
        optionsMap.put("block-quote-compact", new MutableDataSet().set(Formatter.BLOCK_QUOTE_MARKERS, BlockQuoteMarker.ADD_COMPACT));
        optionsMap.put("block-quote-compact-with-space", new MutableDataSet().set(Formatter.BLOCK_QUOTE_MARKERS, BlockQuoteMarker.ADD_COMPACT_WITH_SPACE));
        optionsMap.put("block-quote-spaced", new MutableDataSet().set(Formatter.BLOCK_QUOTE_MARKERS, BlockQuoteMarker.ADD_SPACED));
        optionsMap.put("indented-code-minimize", new MutableDataSet().set(Formatter.INDENTED_CODE_MINIMIZE_INDENT, true));
        optionsMap.put("fenced-code-minimize", new MutableDataSet().set(Formatter.FENCED_CODE_MINIMIZE_INDENT, true));
        optionsMap.put("fenced-code-match-closing", new MutableDataSet().set(Formatter.FENCED_CODE_MATCH_CLOSING_MARKER, true));
        optionsMap.put("fenced-code-spaced-info", new MutableDataSet().set(Formatter.FENCED_CODE_SPACE_BEFORE_INFO, true));
        optionsMap.put("fenced-code-marker-length", new MutableDataSet().set(Formatter.FENCED_CODE_MARKER_LENGTH, 6));
        optionsMap.put("fenced-code-marker-backtick", new MutableDataSet().set(Formatter.FENCED_CODE_MARKER_TYPE, CodeFenceMarker.BACK_TICK));
        optionsMap.put("fenced-code-marker-tilde", new MutableDataSet().set(Formatter.FENCED_CODE_MARKER_TYPE, CodeFenceMarker.TILDE));
        optionsMap.put("list-add-blank-line-before", new MutableDataSet().set(Formatter.LIST_ADD_BLANK_LINE_BEFORE, true));
        optionsMap.put("list-no-renumber-items", new MutableDataSet().set(Formatter.LIST_RENUMBER_ITEMS, false));
        optionsMap.put("list-reset-first-item", new MutableDataSet().set(Formatter.LIST_RESET_FIRST_ITEM_NUMBER, true));
        optionsMap.put("list-no-delimiter-mismatch-to-new-list", new MutableDataSet().set(Parser.LISTS_DELIMITER_MISMATCH_TO_NEW_LIST, false));
        optionsMap.put("list-no-item-mismatch-to-new-list", new MutableDataSet().set(Parser.LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST, false));
        optionsMap.put("list-bullet-dash", new MutableDataSet().set(Formatter.LIST_BULLET_MARKER, ListBulletMarker.DASH));
        optionsMap.put("list-bullet-asterisk", new MutableDataSet().set(Formatter.LIST_BULLET_MARKER, ListBulletMarker.ASTERISK));
        optionsMap.put("list-bullet-plus", new MutableDataSet().set(Formatter.LIST_BULLET_MARKER, ListBulletMarker.PLUS));
        optionsMap.put("list-numbered-dot", new MutableDataSet().set(Formatter.LIST_NUMBERED_MARKER, ListNumberedMarker.DOT));
        optionsMap.put("list-numbered-paren", new MutableDataSet().set(Formatter.LIST_NUMBERED_MARKER, ListNumberedMarker.PAREN));
        optionsMap.put("list-spacing-as-is", new MutableDataSet().set(Formatter.LIST_SPACING, ListSpacing.AS_IS));
        optionsMap.put("list-spacing-loosen", new MutableDataSet().set(Formatter.LIST_SPACING, ListSpacing.LOOSEN));
        optionsMap.put("list-spacing-tighten", new MutableDataSet().set(Formatter.LIST_SPACING, ListSpacing.TIGHTEN));
        optionsMap.put("list-spacing-loose", new MutableDataSet().set(Formatter.LIST_SPACING, ListSpacing.LOOSE));
        optionsMap.put("list-spacing-tight", new MutableDataSet().set(Formatter.LIST_SPACING, ListSpacing.TIGHT));
        optionsMap.put("references-as-is", new MutableDataSet().set(Formatter.REFERENCE_PLACEMENT, ElementPlacement.AS_IS));
        optionsMap.put("references-document-top", new MutableDataSet().set(Formatter.REFERENCE_PLACEMENT, ElementPlacement.DOCUMENT_TOP));
        optionsMap.put("references-group-with-first", new MutableDataSet().set(Formatter.REFERENCE_PLACEMENT, ElementPlacement.GROUP_WITH_FIRST));
        optionsMap.put("references-group-with-last", new MutableDataSet().set(Formatter.REFERENCE_PLACEMENT, ElementPlacement.GROUP_WITH_LAST));
        optionsMap.put("references-document-bottom", new MutableDataSet().set(Formatter.REFERENCE_PLACEMENT, ElementPlacement.DOCUMENT_BOTTOM));
        optionsMap.put("references-sort", new MutableDataSet().set(Formatter.REFERENCE_SORT, ElementPlacementSort.SORT));
        optionsMap.put("references-sort-unused-last", new MutableDataSet().set(Formatter.REFERENCE_SORT, ElementPlacementSort.SORT_UNUSED_LAST));
        optionsMap.put("references-sort-delete-unused", new MutableDataSet().set(Formatter.REFERENCE_SORT, ElementPlacementSort.SORT_DELETE_UNUSED));
        optionsMap.put("references-delete-unused", new MutableDataSet().set(Formatter.REFERENCE_SORT, ElementPlacementSort.DELETE_UNUSED));
        optionsMap.put("references-keep-last", new MutableDataSet().set(Parser.REFERENCES_KEEP, KeepType.LAST));
        optionsMap.put("image-links-at-start", new MutableDataSet().set(Formatter.KEEP_IMAGE_LINKS_AT_START, true));
        optionsMap.put("explicit-links-at-start", new MutableDataSet().set(Formatter.KEEP_EXPLICIT_LINKS_AT_START, true));
        optionsMap.put("remove-empty-items", new MutableDataSet().set(Formatter.LIST_REMOVE_EMPTY_ITEMS, true));
        optionsMap.put("no-hard-breaks", new MutableDataSet().set(Formatter.KEEP_HARD_LINE_BREAKS, false));
        optionsMap.put("no-soft-breaks", new MutableDataSet().set(Formatter.KEEP_SOFT_LINE_BREAKS, false));
        optionsMap.put("apply-escapers", new MutableDataSet().set(Formatter.APPLY_SPECIAL_LEAD_IN_HANDLERS, true));
        optionsMap.put("no-apply-escapers", new MutableDataSet().set(Formatter.APPLY_SPECIAL_LEAD_IN_HANDLERS, false));

        optionsMap.put("no-list-reset-first-item-number", new MutableDataSet().set(Formatter.LIST_RESET_FIRST_ITEM_NUMBER, false));
        optionsMap.put("list-reset-first-item-number", new MutableDataSet().set(Formatter.LIST_RESET_FIRST_ITEM_NUMBER, true));

        optionsMap.put("formatter-tags-enabled", new MutableDataSet().set(Formatter.FORMATTER_TAGS_ENABLED, true));
        optionsMap.put("formatter-tags-accept-regexp", new MutableDataSet().set(Formatter.FORMATTER_TAGS_ACCEPT_REGEXP, true));
        optionsMap.put("formatter-on-tag-alt", new MutableDataSet().set(Formatter.FORMATTER_ON_TAG, "@format:on"));
        optionsMap.put("formatter-off-tag-alt", new MutableDataSet().set(Formatter.FORMATTER_OFF_TAG, "@format:off"));
        optionsMap.put("formatter-on-tag-regex", new MutableDataSet().set(Formatter.FORMATTER_ON_TAG, "^@format:(?:yes|on|true)$").set(Formatter.FORMATTER_TAGS_ACCEPT_REGEXP, true));
        optionsMap.put("formatter-off-tag-regex", new MutableDataSet().set(Formatter.FORMATTER_OFF_TAG, "^@format:(?:no|off|false)$").set(Formatter.FORMATTER_TAGS_ACCEPT_REGEXP, true));

//        optionsMap.put("continuation-indent-align-to-first", new MutableDataSet().set(Formatter.CONTINUATION_INDENT, ContinuationIndent.ALIGN_TO_FIRST));
//        optionsMap.put("continuation-indent-none", new MutableDataSet().set(Formatter.CONTINUATION_INDENT, ContinuationIndent.NONE));
//        optionsMap.put("continuation-indent-indent-1", new MutableDataSet().set(Formatter.CONTINUATION_INDENT, ContinuationIndent.INDENT_1));
//        optionsMap.put("continuation-indent-indent-2", new MutableDataSet().set(Formatter.CONTINUATION_INDENT, ContinuationIndent.INDENT_2));
//        optionsMap.put("continuation-indent-indent-3", new MutableDataSet().set(Formatter.CONTINUATION_INDENT, ContinuationIndent.INDENT_3));

        optionsMap.put("list-align-numeric-none", new MutableDataSet().set(Formatter.LIST_ALIGN_NUMERIC, ElementAlignment.NONE));
        optionsMap.put("list-align-numeric-left", new MutableDataSet().set(Formatter.LIST_ALIGN_NUMERIC, ElementAlignment.LEFT_ALIGN));
        optionsMap.put("list-align-numeric-right", new MutableDataSet().set(Formatter.LIST_ALIGN_NUMERIC, ElementAlignment.RIGHT_ALIGN));
        optionsMap.put("link-address-pattern", new MutableDataSet().set(Formatter.LINK_MARKER_COMMENT_PATTERN, Pattern.compile("^\\s*@IGNORE PREVIOUS:.*$")));

        optionsMap.put("margin", new MutableDataSet().set(TestUtils.CUSTOM_OPTION, (option, params) -> TestUtils.customIntOption(option, params, FormatterTranslationSpecTestBase::marginOption)));
        optionsMap.put("first-prefix", new MutableDataSet().set(TestUtils.CUSTOM_OPTION, (option, params) -> TestUtils.customStringOption(option, params, FormatterTranslationSpecTestBase::firstIndentOption)));
        optionsMap.put("prefix", new MutableDataSet().set(TestUtils.CUSTOM_OPTION, (option, params) -> TestUtils.customStringOption(option, params, FormatterTranslationSpecTestBase::indentOption)));
    }
    static DataHolder firstIndentOption(@Nullable String params) {
        String value = params != null ? params : "";
        return new MutableDataSet().set(Formatter.DOCUMENT_FIRST_PREFIX, value);
    }

    static DataHolder indentOption(@Nullable String params) {
        String value = params != null ? params : "";
        return new MutableDataSet().set(Formatter.DOCUMENT_PREFIX, value);
    }

    static DataHolder marginOption(@Nullable Integer params) {
        int value = params != null ? params : -1;
        return new MutableDataSet().set(Formatter.RIGHT_MARGIN, value);
    }

    public FormatterTranslationSpecTestBase(@NotNull SpecExample example, @Nullable Map<String, ? extends DataHolder> optionMap, @Nullable DataHolder... defaultOptions) {
        super(example, ComboSpecTestCase.optionsMaps(optionsMap, optionMap), ComboSpecTestCase.dataHolders(OPTIONS, defaultOptions));
    }
}
