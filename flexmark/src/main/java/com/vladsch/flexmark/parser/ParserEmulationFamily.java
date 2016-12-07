package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.util.options.DataHolder;

public enum ParserEmulationFamily {
    COMMONMARK(0),
    FIXED_INDENT(1),
    KRAMDOWN(2),
    MARKDOWN(3);

    public final int intValue;

    ParserEmulationFamily(int intValue) {
        this.intValue = intValue;
    }

    public MutableListOptions getOptions() {
        if (this == FIXED_INDENT) {
            return new MutableListOptions().setParserEmulationFamily(this)
                    .setAutoLoose(false)
                    .setAutoLooseOneLevelLists(false)
                    .setLooseWhenBlankFollowsItemParagraph(false)
                    .setLooseWhenHasLooseSubItem(false)
                    .setLooseWhenHasTrailingBlankLine(true)
                    .setLooseOnPrevLooseItem(false)
                    .setOrderedStart(false)
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
        if (this == KRAMDOWN) {
            return new MutableListOptions()
                    .setParserEmulationFamily(this)
                    .setAutoLoose(false)
                    .setLooseWhenBlankFollowsItemParagraph(true)
                    .setLooseWhenHasLooseSubItem(false)
                    .setLooseWhenHasTrailingBlankLine(false)
                    .setLooseOnPrevLooseItem(false)
                    .setOrderedStart(false)
                    .setDelimiterMismatchToNewList(false)
                    .setItemTypeMismatchToNewList(false)
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
        if (this == MARKDOWN) {
            return new MutableListOptions()
                    .setParserEmulationFamily(this)
                    .setAutoLoose(false)
                    .setLooseOnPrevLooseItem(true)
                    .setLooseWhenBlankFollowsItemParagraph(true)
                    .setLooseWhenHasLooseSubItem(true)
                    .setLooseWhenHasTrailingBlankLine(true)
                    .setOrderedStart(false)
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

        return new MutableListOptions((DataHolder) null);
    }
}
