package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.data.*;
import org.jetbrains.annotations.NotNull;

public enum ParserEmulationProfile implements MutableDataSetter {
    COMMONMARK(null),
    COMMONMARK_0_26(COMMONMARK),
    COMMONMARK_0_27(COMMONMARK),
    COMMONMARK_0_28(COMMONMARK),
    COMMONMARK_0_29(COMMONMARK),
    FIXED_INDENT(null),
    KRAMDOWN(null),
    MARKDOWN(null),
    GITHUB_DOC(MARKDOWN),
    GITHUB(COMMONMARK),
    MULTI_MARKDOWN(FIXED_INDENT),
    PEGDOWN(FIXED_INDENT),
    PEGDOWN_STRICT(FIXED_INDENT),
    ;

    final public ParserEmulationProfile family;

    ParserEmulationProfile(ParserEmulationProfile family) {
        this.family = family == null ? this : family;
    }

    public MutableDataHolder getProfileOptions() {
        MutableDataHolder options = new MutableDataSet();
        setIn(options);
        return options;
    }

    public MutableListOptions getOptions() {
        return getOptions(null);
    }

    /**
     * Key used to hold user pegdown extension selection
     */
    final public static DataKey<Integer> PEGDOWN_EXTENSIONS = new DataKey<>("PEGDOWN_EXTENSIONS", PegdownExtensions.ALL);

    public MutableListOptions getOptions(DataHolder dataHolder) {
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

        if (family == COMMONMARK) {
            if (this == COMMONMARK_0_26) {
                return new MutableListOptions((DataHolder) null).setEndOnDoubleBlank(true);
            }
            else if (this == COMMONMARK_0_28) {
                // IMPORTANT: implement 0.29 as defaults with 0.28 as changes
                //return new MutableListOptions((DataHolder) null).setEndOnDoubleBlank(true);
            }
        }

        // default CommonMark
        return new MutableListOptions((DataHolder) null);
    }

    @NotNull
    @Override
    public MutableDataHolder setIn(@NotNull MutableDataHolder dataHolder) {
        if (this == FIXED_INDENT) {
            getOptions(dataHolder).setIn(dataHolder)
                    .set(Parser.STRONG_WRAPS_EMPHASIS, true)
                    .set(Parser.LINKS_ALLOW_MATCHED_PARENTHESES, false)
            ;
        } else if (this == KRAMDOWN) {
            getOptions(dataHolder).setIn(dataHolder);
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
            getOptions(dataHolder).setIn(dataHolder);
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
            getOptions(dataHolder).setIn(dataHolder);
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

                    // github change for heading Ids
                    .set(HtmlRenderer.HEADER_ID_GENERATOR_TO_DASH_CHARS, " -")
                    .set(HtmlRenderer.HEADER_ID_GENERATOR_NON_DASH_CHARS, "_")
                    .set(HtmlRenderer.HEADER_ID_GENERATOR_NON_ASCII_TO_LOWERCASE, false)
            ;
        } else if (this == GITHUB) {
            getOptions(dataHolder).setIn(dataHolder);
            dataHolder
                    // github change for heading Ids
                    .set(HtmlRenderer.HEADER_ID_GENERATOR_TO_DASH_CHARS, " -")
                    .set(HtmlRenderer.HEADER_ID_GENERATOR_NON_DASH_CHARS, "_")
                    .set(HtmlRenderer.HEADER_ID_GENERATOR_NON_ASCII_TO_LOWERCASE, false)
            ;
        } else if (this == MULTI_MARKDOWN) {
            getOptions(dataHolder).setIn(dataHolder);
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
            int pegdownExtensions = PEGDOWN_EXTENSIONS.get(dataHolder);

            getOptions(dataHolder).setIn(dataHolder);
            dataHolder
                    .set(Parser.BLOCK_QUOTE_EXTEND_TO_BLANK_LINE, true)
                    .set(Parser.BLOCK_QUOTE_IGNORE_BLANK_LINE, true)
                    .set(Parser.BLOCK_QUOTE_ALLOW_LEADING_SPACE, false)
                    .set(Parser.INDENTED_CODE_NO_TRAILING_BLANK_LINES, true)
                    .set(Parser.HEADING_SETEXT_MARKER_LENGTH, 3)
                    .set(Parser.HEADING_NO_LEAD_SPACE, true)
                    .set(Parser.REFERENCES_KEEP, KeepType.LAST)
                    .set(Parser.PARSE_INNER_HTML_COMMENTS, true)
                    .set(Parser.SPACE_IN_LINK_ELEMENTS, true)

                    .set(HtmlRenderer.OBFUSCATE_EMAIL, true)
                    .set(HtmlRenderer.GENERATE_HEADER_ID, true)
                    //.set(HtmlRenderer.HEADER_ID_GENERATOR_TO_DASH_CHARS, "")
                    .set(HtmlRenderer.HEADER_ID_GENERATOR_NO_DUPED_DASHES, true)
                    .set(HtmlRenderer.HEADER_ID_GENERATOR_RESOLVE_DUPES, false)
                    .set(HtmlRenderer.SOFT_BREAK, " ")
                    .set(Parser.STRONG_WRAPS_EMPHASIS, true)
                    .set(Parser.LINKS_ALLOW_MATCHED_PARENTHESES, false)
            ;

            if (haveAny(pegdownExtensions, PegdownExtensions.ANCHORLINKS)) {
                dataHolder.set(HtmlRenderer.RENDER_HEADER_ID, false);
            }

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
        } else if (this == COMMONMARK_0_28) {
            // set 0.28 parsing rule options
            // IMPORTANT: 0.28/0.29 differences
        }

        return dataHolder;
    }

    public static boolean haveAny(int extensions, int mask) {
        return (extensions & mask) != 0;
    }

    public static boolean haveAll(int extensions, int mask) {
        return (extensions & mask) == mask;
    }
}
