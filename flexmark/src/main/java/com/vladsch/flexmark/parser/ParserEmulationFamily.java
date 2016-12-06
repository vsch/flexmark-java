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

    public ListOptions getListOptions() {
        if (this == FIXED_INDENT) {
            return new ListOptions.Mutable()
                    .setParserEmulationFamily(this)
                    .setAutoLoose(true)
                    .setAutoLooseOneLevelLists(true)
                    .setLooseWhenBlankFollowsItemParagraph(true)
                    .setLooseOnPrevLooseItem(false)
                    .setOrderedStart(false)
                    .setBulletMismatchToNewList(false)
                    .setItemTypeMismatchToNewList(false)
                    .setItemTypeMismatchToSubList(false)
                    .setEndOnDoubleBlank(false)
                    .setItemIndent(4)
                    .setCodeIndent(8)
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

                            // TEST: need to test for these
                            .setEmptyBulletSubItemInterruptsItemParagraph(true)
                            .setEmptyOrderedSubItemInterruptsItemParagraph(true)
                            .setEmptyOrderedNonOneSubItemInterruptsItemParagraph(true)
                    )

                    .getListOptions();
        }
        if (this == KRAMDOWN) {
            return new ListOptions.Mutable()
                    .setParserEmulationFamily(this)
                    .setAutoLoose(false)
                    .setLooseWhenBlankFollowsItemParagraph(true)
                    .setLooseOnPrevLooseItem(false)
                    .setOrderedStart(false)
                    .setBulletMismatchToNewList(false)
                    .setItemTypeMismatchToNewList(false)
                    .setItemTypeMismatchToSubList(true)
                    .setEndOnDoubleBlank(false)
                    .setItemIndent(4)
                    .setCodeIndent(8)
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
                    )

                    .getListOptions();
        }
        if (this == MARKDOWN) {
            return new ListOptions.Mutable()
                    .setParserEmulationFamily(this)
                    .setAutoLoose(false)
                    .setLooseOnPrevLooseItem(true)
                    .setLooseWhenBlankFollowsItemParagraph(false)
                    .setOrderedStart(false)
                    .setBulletMismatchToNewList(false)
                    .setItemTypeMismatchToNewList(false)
                    .setItemTypeMismatchToSubList(false)
                    .setEndOnDoubleBlank(false)
                    .setItemIndent(4)
                    .setCodeIndent(8)
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

                            // TEST: need to test for these
                            .setEmptyBulletSubItemInterruptsItemParagraph(true)
                            .setEmptyOrderedSubItemInterruptsItemParagraph(true)
                            .setEmptyOrderedNonOneSubItemInterruptsItemParagraph(true)
                    )

                    .getListOptions();
        }

        return new ListOptions((DataHolder) null);
    }
}
