package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.util.options.DataHolder;

public enum ParserEmulationFamily {
    COMMONMARK(0),
    MULTI_MARKDOWN(1),
    KRAMDOWN(2),
    MARKDOWN(3);

    public final int intValue;

    ParserEmulationFamily(int intValue) {
        this.intValue = intValue;
    }

    public ListOptions getListOptions() {
        if (this == MULTI_MARKDOWN) {
            return new ListOptions.Mutable()
                    .setParserEmulationFamily(this)
                    .setAutoLoose(false)
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
                    .setLooseOnPrevLooseItem(false)
                    .setOrderedStart(false)
                    .setBulletMismatchToNewList(false)
                    .setItemTypeMismatchToNewList(false)
                    .setItemTypeMismatchToSubList(true)
                    .setEndOnDoubleBlank(false)
                    .setItemIndent(4)
                    .setCodeIndent(8)
                    .setItemInterrupt(new ListOptions.MutableItemInterrupt()
                            .setBulletItemInterruptsParagraph(true)
                            .setOrderedItemInterruptsParagraph(true)
                            .setOrderedNonOneItemInterruptsParagraph(true)

                            .setEmptyBulletItemInterruptsParagraph(true)
                            .setEmptyOrderedItemInterruptsParagraph(true)
                            .setEmptyOrderedNonOneItemInterruptsParagraph(true)

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
        if (this == MARKDOWN) {
            return new ListOptions.Mutable()
                    .setParserEmulationFamily(this)
                    .setAutoLoose(false)
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
