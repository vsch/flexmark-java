package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.util.options.MutableDataSetter;

public enum ParserEmulationProfile implements MutableDataSetter {
    COMMONMARK(null),
    COMMONMARK_0_26(COMMONMARK),
    COMMONMARK_0_27(COMMONMARK),
    COMMONMARK_0_28(COMMONMARK),
    FIXED_INDENT(null),
    KRAMDOWN(null),
    MARKDOWN(null),
    GITHUB_DOC(MARKDOWN),
    MULTI_MARKDOWN(FIXED_INDENT),
    PEGDOWN(FIXED_INDENT),
    PEGDOWN_STRICT(FIXED_INDENT),;

    public final ParserEmulationProfile family;

    ParserEmulationProfile(ParserEmulationProfile family) {
        this.family = family == null ? this : family;
    }

    public MutableDataHolder getProfileOptions() {
        MutableDataHolder options = new MutableDataSet();
        setIn(options);
        return options;
    }

    public MutableListOptions getOptions() {
        if (family == FIXED_INDENT) {
            if (this == MULTI_MARKDOWN) {
                return new MutableListOptions().setParserEmulationFamily(this)
                        .setAutoLoose(true)
                        .setAutoLooseOneLevelLists(true)
                        .setDelimiterMismatchToNewList(false)
                        .setCodeIndent(8)
                        .setEndOnDoubleBlank(false)
                        .setItemIndent(4)
                        .setItemInterrupt(new ListOptions.MutableItemInterrupt()
                                .setBulletItemInterruptsParagraph(false)
                                .setOrderedItemInterruptsParagraph(false)
                                .setOrderedNonOneItemInterruptsParagraph(false)

                                .setEmptyBulletItemInterruptsParagraph(false)
                                .setEmptyOrderedItemInterruptsParagraph(false)
                                .setEmptyOrderedNonOneItemInterruptsParagraph(false)

                                .setBulletItemInterruptsItemParagraph(true)
                                .setOrderedItemInterruptsItemParagraph(true)
                                .setOrderedNonOneItemInterruptsItemParagraph(true)

                                .setEmptyBulletItemInterruptsItemParagraph(true)
                                .setEmptyOrderedItemInterruptsItemParagraph(true)
                                .setEmptyOrderedNonOneItemInterruptsItemParagraph(true)

                                .setEmptyBulletSubItemInterruptsItemParagraph(true)
                                .setEmptyOrderedSubItemInterruptsItemParagraph(true)
                                .setEmptyOrderedNonOneSubItemInterruptsItemParagraph(true)
                        )
                        .setItemMarkerSpace(false)
                        .setItemTypeMismatchToNewList(false)
                        .setItemTypeMismatchToSubList(false)
                        .setLooseWhenBlankLineFollowsItemParagraph(true)
                        .setLooseWhenHasTrailingBlankLine(false)
                        .setLooseWhenHasNonListChildren(true)
                        .setNewItemCodeIndent(Integer.MAX_VALUE)
                        .setOrderedItemDotOnly(true)
                        .setOrderedListManualStart(false)
                        ;
            }
            if (this == PEGDOWN || this == PEGDOWN_STRICT) {
                return new MutableListOptions().setParserEmulationFamily(this)
                        .setAutoLoose(false)
                        .setAutoLooseOneLevelLists(false)
                        .setLooseWhenBlankLineFollowsItemParagraph(false)
                        .setLooseWhenHasLooseSubItem(false)
                        .setLooseWhenHasTrailingBlankLine(false)
                        .setLooseWhenPrevHasTrailingBlankLine(true)
                        .setOrderedListManualStart(false)
                        .setDelimiterMismatchToNewList(false)
                        .setItemTypeMismatchToNewList(true)
                        .setItemTypeMismatchToSubList(false)
                        .setEndOnDoubleBlank(false)
                        .setOrderedItemDotOnly(true)
                        .setItemMarkerSpace(true)
                        .setItemIndent(4)
                        .setCodeIndent(8)
                        .setNewItemCodeIndent(Integer.MAX_VALUE)
                        .setItemInterrupt(new ListOptions.MutableItemInterrupt()
                                .setBulletItemInterruptsParagraph(false)
                                .setOrderedItemInterruptsParagraph(false)
                                .setOrderedNonOneItemInterruptsParagraph(false)

                                .setEmptyBulletItemInterruptsParagraph(false)
                                .setEmptyOrderedItemInterruptsParagraph(false)
                                .setEmptyOrderedNonOneItemInterruptsParagraph(false)

                                .setBulletItemInterruptsItemParagraph(true)
                                .setOrderedItemInterruptsItemParagraph(true)
                                .setOrderedNonOneItemInterruptsItemParagraph(true)

                                .setEmptyBulletItemInterruptsItemParagraph(true)
                                .setEmptyOrderedItemInterruptsItemParagraph(true)
                                .setEmptyOrderedNonOneItemInterruptsItemParagraph(true)

                                .setEmptyBulletSubItemInterruptsItemParagraph(true)
                                .setEmptyOrderedSubItemInterruptsItemParagraph(true)
                                .setEmptyOrderedNonOneSubItemInterruptsItemParagraph(true)
                        );
            }
            return new MutableListOptions().setParserEmulationFamily(this)
                    .setAutoLoose(false)
                    .setAutoLooseOneLevelLists(false)
                    .setLooseWhenBlankLineFollowsItemParagraph(false)
                    .setLooseWhenHasLooseSubItem(false)
                    .setLooseWhenHasTrailingBlankLine(true)
                    .setLooseWhenPrevHasTrailingBlankLine(false)
                    .setLooseWhenLastItemPrevHasTrailingBlankLine(true)
                    .setOrderedListManualStart(false)
                    .setDelimiterMismatchToNewList(false)
                    .setItemTypeMismatchToNewList(false)
                    .setItemTypeMismatchToSubList(false)
                    .setEndOnDoubleBlank(false)
                    .setOrderedItemDotOnly(true)
                    .setItemMarkerSpace(true)
                    .setItemIndent(4)
                    .setCodeIndent(8)
                    .setNewItemCodeIndent(Integer.MAX_VALUE)
                    .setItemInterrupt(new ListOptions.MutableItemInterrupt()
                            .setBulletItemInterruptsParagraph(false)
                            .setOrderedItemInterruptsParagraph(false)
                            .setOrderedNonOneItemInterruptsParagraph(false)

                            .setEmptyBulletItemInterruptsParagraph(false)
                            .setEmptyOrderedItemInterruptsParagraph(false)
                            .setEmptyOrderedNonOneItemInterruptsParagraph(false)

                            .setBulletItemInterruptsItemParagraph(true)
                            .setOrderedItemInterruptsItemParagraph(true)
                            .setOrderedNonOneItemInterruptsItemParagraph(true)

                            .setEmptyBulletItemInterruptsItemParagraph(true)
                            .setEmptyOrderedItemInterruptsItemParagraph(true)
                            .setEmptyOrderedNonOneItemInterruptsItemParagraph(true)

                            .setEmptyBulletSubItemInterruptsItemParagraph(true)
                            .setEmptyOrderedSubItemInterruptsItemParagraph(true)
                            .setEmptyOrderedNonOneSubItemInterruptsItemParagraph(true)
                    );
        }
        if (family == KRAMDOWN) {
            return new MutableListOptions()
                    .setParserEmulationFamily(this)
                    .setAutoLoose(false)
                    .setLooseWhenBlankLineFollowsItemParagraph(true)
                    .setLooseWhenHasLooseSubItem(false)
                    .setLooseWhenHasTrailingBlankLine(false)
                    .setLooseWhenPrevHasTrailingBlankLine(false)
                    .setOrderedListManualStart(false)
                    .setDelimiterMismatchToNewList(false)
                    .setItemTypeMismatchToNewList(true)
                    .setItemTypeMismatchToSubList(true)
                    .setOrderedItemDotOnly(true)
                    .setItemMarkerSpace(true)
                    .setEndOnDoubleBlank(false)
                    .setItemIndent(4)
                    .setCodeIndent(8)
                    .setNewItemCodeIndent(Integer.MAX_VALUE)
                    .setItemInterrupt(new ListOptions.MutableItemInterrupt()
                            .setBulletItemInterruptsParagraph(false)
                            .setOrderedItemInterruptsParagraph(false)
                            .setOrderedNonOneItemInterruptsParagraph(false)

                            .setEmptyBulletItemInterruptsParagraph(false)
                            .setEmptyOrderedItemInterruptsParagraph(false)
                            .setEmptyOrderedNonOneItemInterruptsParagraph(false)

                            .setBulletItemInterruptsItemParagraph(true)
                            .setOrderedItemInterruptsItemParagraph(true)
                            .setOrderedNonOneItemInterruptsItemParagraph(true)

                            .setEmptyBulletItemInterruptsItemParagraph(true)
                            .setEmptyOrderedItemInterruptsItemParagraph(true)
                            .setEmptyOrderedNonOneItemInterruptsItemParagraph(true)

                            .setEmptyBulletSubItemInterruptsItemParagraph(false)
                            .setEmptyOrderedSubItemInterruptsItemParagraph(false)
                            .setEmptyOrderedNonOneSubItemInterruptsItemParagraph(false)
                    );
        }
        if (family == MARKDOWN) {
            if (this == GITHUB_DOC) {
                return new MutableListOptions()
                        .setParserEmulationFamily(this)
                        .setAutoLoose(false)
                        .setLooseWhenBlankLineFollowsItemParagraph(true)
                        .setLooseWhenHasLooseSubItem(true)
                        .setLooseWhenHasTrailingBlankLine(true)
                        .setLooseWhenPrevHasTrailingBlankLine(true)
                        .setLooseWhenContainsBlankLine(false)
                        .setLooseWhenHasNonListChildren(true)
                        .setOrderedListManualStart(false)
                        .setDelimiterMismatchToNewList(false)
                        .setItemTypeMismatchToNewList(false)
                        .setItemTypeMismatchToSubList(false)
                        .setEndOnDoubleBlank(false)
                        .setOrderedItemDotOnly(true)
                        .setItemMarkerSpace(true)
                        .setItemIndent(4)
                        .setCodeIndent(8)
                        .setNewItemCodeIndent(Integer.MAX_VALUE)
                        .setItemInterrupt(new ListOptions.MutableItemInterrupt()
                                .setBulletItemInterruptsParagraph(true)
                                .setOrderedItemInterruptsParagraph(false)
                                .setOrderedNonOneItemInterruptsParagraph(false)

                                .setEmptyBulletItemInterruptsParagraph(true)
                                .setEmptyOrderedItemInterruptsParagraph(false)
                                .setEmptyOrderedNonOneItemInterruptsParagraph(false)

                                .setBulletItemInterruptsItemParagraph(true)
                                .setOrderedItemInterruptsItemParagraph(true)
                                .setOrderedNonOneItemInterruptsItemParagraph(true)

                                .setEmptyBulletItemInterruptsItemParagraph(true)
                                .setEmptyOrderedItemInterruptsItemParagraph(true)
                                .setEmptyOrderedNonOneItemInterruptsItemParagraph(true)

                                .setEmptyBulletSubItemInterruptsItemParagraph(true)
                                .setEmptyOrderedSubItemInterruptsItemParagraph(true)
                                .setEmptyOrderedNonOneSubItemInterruptsItemParagraph(true)
                        );
            }
            return new MutableListOptions()
                    .setParserEmulationFamily(this)
                    .setAutoLoose(false)
                    .setLooseWhenBlankLineFollowsItemParagraph(true)
                    .setLooseWhenHasLooseSubItem(true)
                    .setLooseWhenHasTrailingBlankLine(true)
                    .setLooseWhenPrevHasTrailingBlankLine(true)
                    .setLooseWhenContainsBlankLine(true)
                    .setOrderedListManualStart(false)
                    .setDelimiterMismatchToNewList(false)
                    .setItemTypeMismatchToNewList(false)
                    .setItemTypeMismatchToSubList(false)
                    .setEndOnDoubleBlank(false)
                    .setOrderedItemDotOnly(true)
                    .setItemMarkerSpace(true)
                    .setItemIndent(4)
                    .setCodeIndent(8)
                    .setNewItemCodeIndent(Integer.MAX_VALUE)
                    .setItemInterrupt(new ListOptions.MutableItemInterrupt()
                            .setBulletItemInterruptsParagraph(false)
                            .setOrderedItemInterruptsParagraph(false)
                            .setOrderedNonOneItemInterruptsParagraph(false)

                            .setEmptyBulletItemInterruptsParagraph(false)
                            .setEmptyOrderedItemInterruptsParagraph(false)
                            .setEmptyOrderedNonOneItemInterruptsParagraph(false)

                            .setBulletItemInterruptsItemParagraph(true)
                            .setOrderedItemInterruptsItemParagraph(true)
                            .setOrderedNonOneItemInterruptsItemParagraph(true)

                            .setEmptyBulletItemInterruptsItemParagraph(false)
                            .setEmptyOrderedItemInterruptsItemParagraph(false)
                            .setEmptyOrderedNonOneItemInterruptsItemParagraph(false)

                            .setEmptyBulletSubItemInterruptsItemParagraph(true)
                            .setEmptyOrderedSubItemInterruptsItemParagraph(true)
                            .setEmptyOrderedNonOneSubItemInterruptsItemParagraph(true)
                    );
        }

        return this == COMMONMARK_0_26 ? new MutableListOptions((DataHolder) null).setEndOnDoubleBlank(true) : new MutableListOptions((DataHolder) null);
    }

    @Override
    public MutableDataHolder setIn(final MutableDataHolder dataHolder) {
        if (this == FIXED_INDENT) {
            getOptions().setIn(dataHolder)
                    .set(Parser.STRONG_WRAPS_EMPHASIS, true)
                    .set(Parser.LINKS_ALLOW_MATCHED_PARENTHESES, false)
            ;
        } else if (this == KRAMDOWN) {
            getOptions().setIn(dataHolder);
            dataHolder
                    .set(Parser.HEADING_NO_LEAD_SPACE, true)
                    .set(Parser.BLOCK_QUOTE_INTERRUPTS_PARAGRAPH, false)
                    .set(HtmlRenderer.RENDER_HEADER_ID, true)
                    .set(HtmlRenderer.SOFT_BREAK, " ")

                    // deep HTML parsing for multi-markdown compatibility
                    .set(Parser.HTML_BLOCK_DEEP_PARSER, true)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_NON_BLOCK, true)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_FIRST_OPEN_TAG_ON_ONE_LINE, false)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS, false)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_MARKDOWN_INTERRUPTS_CLOSED, true)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS_PARTIAL_TAG, false)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_INDENTED_CODE_INTERRUPTS, true)
                    .set(Parser.STRONG_WRAPS_EMPHASIS, true)
                    .set(Parser.LINKS_ALLOW_MATCHED_PARENTHESES, false)
            ;
        } else if (this == MARKDOWN) {
            getOptions().setIn(dataHolder);
            dataHolder
                    .set(Parser.HEADING_NO_LEAD_SPACE, true)
                    .set(Parser.BLOCK_QUOTE_IGNORE_BLANK_LINE, true)
                    .set(HtmlRenderer.SOFT_BREAK, " ")

                    // deep HTML parsing for markdown compatibility
                    .set(Parser.HTML_BLOCK_DEEP_PARSER, true)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_NON_BLOCK, true)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_FIRST_OPEN_TAG_ON_ONE_LINE, false)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS, false)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_MARKDOWN_INTERRUPTS_CLOSED, true)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS_PARTIAL_TAG, false)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_INDENTED_CODE_INTERRUPTS, true)
                    .set(Parser.STRONG_WRAPS_EMPHASIS, true)
                    .set(Parser.LINKS_ALLOW_MATCHED_PARENTHESES, false)
            ;
        } else if (this == GITHUB_DOC) {
            getOptions().setIn(dataHolder);
            dataHolder
                    .set(Parser.BLOCK_QUOTE_IGNORE_BLANK_LINE, true)
                    .set(Parser.BLOCK_QUOTE_INTERRUPTS_PARAGRAPH, true)
                    .set(Parser.BLOCK_QUOTE_INTERRUPTS_ITEM_PARAGRAPH, false)
                    .set(Parser.HEADING_NO_LEAD_SPACE, true)

                    // deep HTML parsing for markdown compatibility
                    .set(Parser.HTML_BLOCK_DEEP_PARSER, true)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_NON_BLOCK, true)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_FIRST_OPEN_TAG_ON_ONE_LINE, false)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS, true)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_MARKDOWN_INTERRUPTS_CLOSED, true)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS_PARTIAL_TAG, false)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_INDENTED_CODE_INTERRUPTS, false)
                    .set(Parser.STRONG_WRAPS_EMPHASIS, true)
                    .set(Parser.LINKS_ALLOW_MATCHED_PARENTHESES, false)
            ;
        } else if (this == MULTI_MARKDOWN) {
            getOptions().setIn(dataHolder);
            dataHolder
                    .set(Parser.BLOCK_QUOTE_IGNORE_BLANK_LINE, true)
                    .set(Parser.BLOCK_QUOTE_WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH, false)
                    .set(HtmlRenderer.RENDER_HEADER_ID, true)
                    .set(HtmlRenderer.HEADER_ID_GENERATOR_RESOLVE_DUPES, false)
                    .set(HtmlRenderer.HEADER_ID_GENERATOR_TO_DASH_CHARS, "")
                    .set(HtmlRenderer.HEADER_ID_GENERATOR_NO_DUPED_DASHES, true)
                    .set(HtmlRenderer.SOFT_BREAK, " ")

                    // deep HTML parsing for multi-markdown compatibility
                    .set(Parser.HTML_BLOCK_DEEP_PARSER, true)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_NON_BLOCK, true)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_FIRST_OPEN_TAG_ON_ONE_LINE, false)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS, false)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_MARKDOWN_INTERRUPTS_CLOSED, true)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS_PARTIAL_TAG, false)
                    .set(Parser.HTML_BLOCK_DEEP_PARSE_INDENTED_CODE_INTERRUPTS, true)
                    .set(Parser.STRONG_WRAPS_EMPHASIS, true)
                    .set(Parser.LINKS_ALLOW_MATCHED_PARENTHESES, false)
            ;
        } else if (this == PEGDOWN || this == PEGDOWN_STRICT) {
            getOptions().setIn(dataHolder);
            dataHolder
                    .set(Parser.BLOCK_QUOTE_TO_BLANK_LINE, true)
                    .set(Parser.BLOCK_QUOTE_IGNORE_BLANK_LINE, true)
                    .set(Parser.BLOCK_QUOTE_ALLOW_LEADING_SPACE, false)
                    .set(Parser.INDENTED_CODE_NO_TRAILING_BLANK_LINES, true)
                    .set(Parser.HEADING_SETEXT_MARKER_LENGTH, 3)
                    .set(Parser.HEADING_NO_LEAD_SPACE, true)
                    .set(Parser.REFERENCES_KEEP, KeepType.LAST)
                    .set(Parser.PARSE_INNER_HTML_COMMENTS, true)
                    .set(Parser.SPACE_IN_LINK_ELEMENTS, true)

                    .set(HtmlRenderer.RENDER_HEADER_ID, false)
                    .set(HtmlRenderer.OBFUSCATE_EMAIL, true)
                    .set(HtmlRenderer.GENERATE_HEADER_ID, true)
                    .set(HtmlRenderer.HEADER_ID_GENERATOR_RESOLVE_DUPES, false)
                    //.set(HtmlRenderer.HEADER_ID_GENERATOR_TO_DASH_CHARS, "")
                    .set(HtmlRenderer.HEADER_ID_GENERATOR_NO_DUPED_DASHES, true)
                    .set(HtmlRenderer.SOFT_BREAK, " ")
                    .set(Parser.STRONG_WRAPS_EMPHASIS, true)
                    .set(Parser.LINKS_ALLOW_MATCHED_PARENTHESES, false)
            ;

            if (this == PEGDOWN_STRICT) {
                // deep HTML parsing for pegdown compatibility
                dataHolder
                        .set(Parser.HTML_BLOCK_DEEP_PARSER, true)
                        .set(Parser.HTML_BLOCK_DEEP_PARSE_NON_BLOCK, true)
                        .set(Parser.HTML_BLOCK_DEEP_PARSE_FIRST_OPEN_TAG_ON_ONE_LINE, false)
                        .set(Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS, false)
                        .set(Parser.HTML_BLOCK_DEEP_PARSE_MARKDOWN_INTERRUPTS_CLOSED, true)
                        .set(Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS_PARTIAL_TAG, false)
                        .set(Parser.HTML_BLOCK_DEEP_PARSE_INDENTED_CODE_INTERRUPTS, false)
                ;
            } else {
                dataHolder
                        .set(Parser.HTML_BLOCK_DEEP_PARSER, true)
                        .set(Parser.HTML_BLOCK_DEEP_PARSE_NON_BLOCK, true)
                        .set(Parser.HTML_BLOCK_DEEP_PARSE_FIRST_OPEN_TAG_ON_ONE_LINE, false)
                        .set(Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS, true)
                        .set(Parser.HTML_BLOCK_DEEP_PARSE_MARKDOWN_INTERRUPTS_CLOSED, true)
                        .set(Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS_PARTIAL_TAG, false)
                        .set(Parser.HTML_BLOCK_DEEP_PARSE_INDENTED_CODE_INTERRUPTS, false)
                ;
            }
        } else if (this == COMMONMARK_0_26 || this == COMMONMARK_0_27) {
            // set previous parsing rule options
            dataHolder.set(Parser.STRONG_WRAPS_EMPHASIS, true);
            dataHolder.set(Parser.LINKS_ALLOW_MATCHED_PARENTHESES, false);
        }

        return dataHolder;
    }
}
