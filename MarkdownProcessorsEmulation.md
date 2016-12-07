Markdown Processors Emulation
=============================

Parsers are classified by "Family" for their list processing characteristics, by far the
greatest point of deviation between all markdown parsers. They differ the greatest on how they
determining whether a text line is:

- indented code of the current item or parent list item's
- lazy continuation of current item
- next list item
- current item's sub-item

Some processors, like Kramdown and Markdown, use the list's first item indent to determine when
text is an item, lazy continuation or indented code. CommonMark (spec.txt 0.27),
uses the last item's content indent for this determination. MultiMarkdown, uses a
fixed indent of 4 spaces from left edge or last block quote marker to make that determination.

After trying to work this out by trial and error and only having moderate success, I decided to
make more rigorous to reduce interaction between options and to be able tweak individual family
emulation accuracy.

The following definitions are used:

- `index`: offset from start of line being processed
- `line indent`: index of first non-blank from index set by parent element for the line
- `line column`: would be index of first non-blank if tabs were expanded to 4 space boundaries
- `item indent`: `line indent` of first line of item (one with list item marker)
- `item column`: `line column` of first line of item (one with list item marker)
- `item content offset`: item prefix length + # of trailing white spaces following the item prefix 
- `item content indent`: `item indent` + `item content offset`
- `item content column`: `item column` + `item content offset`
- `list indent`: first item's `item indent`
- `list column`: first item's `item column`
- `list content indent`: first item's `item content indent`
- `list content column`: first item's `item content column`
- `list last indent`: last item's `item indent`
- `list last column`: last item's `item column`
- `list last content indent`: last item's `item content indent`
- `list last content column`: last item's `item content column`
- `list nesting`: number of direct list ancestors of an item (counting stops on non-list or
  non-list-item parent block), always >= 1 since every list item has a list parent
- `first parent list`: last list block after non-list or non-list item block

Family types:

- CommonMark: version 0.27 of the spec, all common mark parsers
    - Definitions/Defaults:
        - `ITEM_INDENT` = 4 <!-- not used -->
        - `CODE_INDENT` = 4 
        - `NEW_ITEM_CODE_INDENT` = 4 
        - `current indent` = `line indent`
    - Start List Conditions:
        - `current indent` < `CODE_INDENT`
            - `item content indent` >= `NEW_ITEM_CODE_INDENT`: empty item + indented code
            - otherwise: new list with new item
    - Continuation Conditions:
        - `current indent` >= `list last content indent` + `CODE_INDENT`: indented code
        - `current indent` >= `list last content indent`: sub-item
        - `current indent` >= `list indent`: list item

- FixedIndent: Pandoc, MultiMarkdown, Pegdown
    - Definitions/Defaults:
        - `ITEM_INDENT` = 4
        - `CODE_INDENT` = 8
        - `current indent` = line indent
    - Start List Conditions:
        - `current indent` < `ITEM_INDENT`: new list with new item
    - Continuation Conditions:
         - `current indent` >= `CODE_INDENT`: indented code
         - `current indent` >= `ITEM_INDENT`: sub-item
         - otherwise: list item

- Kramdown:
    - Definitions/Defaults:
        - `ITEM_INDENT` = 4
        - `CODE_INDENT` = 8
        - `current indent` = `line indent`
        - `LISTS_ITEM_MARKER_SPACE` = true
    - Start List Conditions:
        - `current indent` < `ITEM_INDENT`: new list with new item
    - Continuation Conditions:
        - `current indent` >=  `item content indent`: sub-item
        - `current indent` >= `list indent` + `ITEM_INDENT`
             - hadBlankLine: end current item, keep loose status, indented code
             - !hadBlankLine: lazy continuation
        - `current indent` >= `list indent` + `CODE_INDENT`: indented code
        - `current indent` >= `list indent`: list item

- Markdown:
    - Definitions/Defaults:
        - `ITEM_INDENT` = 4 
        - `CODE_INDENT` = 8 
        - `current indent` = `line indent`
    - Start List Conditions:
        - `current indent` < `ITEM_INDENT`: new list with new item
    - Continuation Conditions:
        - `current indent` >= `list indent` + `CODE_INDENT`: 
            - if had blank line in item && have previous list item parent: then let it have it
            - otherwise: lazy continuation of last list item
        - `current indent` > `list indent`: sub-item
        - `current indent` == `list indent`: list item

Parser configuration parameters, parser emulation family sets defaults but these can be modified
to tweak parser behaviour:

- [ ] item indent columns: `Parser.ITEM_INDENT`, `ListOptions.itemIndent`
- [ ] item code indent column: `Parser.CODE_INDENT`, `ListOptions.codeIndent`
- [ ] new item code indent column: `Parser.NEW_ITEM_CODE_INDENT`, `ListOptions.newItemCodeIndent`
- [ ] list items require explicit space after marker `Parser.LISTS_ITEM_MARKER_SPACE`, `ListOptions.itemMarkerSpace`
- [ ] mismatch item type starts a new list: `Parser.LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST`, `ListOptions.itemTypeMismatchToNewList`
- [ ] mismatch item type start a sub-list: `Parser.LISTS_ITEM_TYPE_MISMATCH_TO_SUB_LIST`, `ListOptions.itemTypeMismatchToSubList`
- [ ] bullet or ordered item delimiter mismatch starts a new list: `Parser.LISTS_DELIMITER_MISMATCH_TO_NEW_LIST`, `ListOptions.delimiterMismatchToNewList`
- [ ] ordered items only with `.` after digit, otherwise `)` is also allowed: `Parser.LISTS_ORDERED_ITEM_DOT_ONLY`, `ListOptions.orderedItemDotOnly`
- [ ] first ordered item prefix sets start number of list: `Parser.LISTS_ORDERED_LIST_MANUAL_START`, `ListOptions.orderedListManualStart` 
- [ ] item is loose if it has trailing blank line in it or its last child: `Parser.LISTS_LOOSE_WHEN_HAS_TRAILING_BLANK_LINE`, `ListOptions.looseWhenHasTrailingBlankLine`
- [ ] item is loose if it contains a blank line after its item text: `Parser.LISTS_LOOSE_WHEN_BLANK_FOLLOWS_ITEM_PARAGRAPH`, `ListOptions.looseWhenBlankFollowsItemParagraph`
- [ ] item is loose if it or previous item is loose: `Parser.LISTS_LOOSE_ON_PREV_LOOSE_ITEM`, `ListOptions.looseOnPrevLooseItem` 
- [ ] all items are loose if any in the list are loose: `Parser.LISTS_AUTO_LOOSE`, `ListOptions.autoLoose`
- [ ] auto loose list setting `Parser.LISTS_AUTO_LOOSE` only applies to simple 1 level lists: `Parser.LISTS_AUTO_LOOSE_ONE_LEVEL_LISTS`, `ListOptions.autoLooseOneLevelLists`

- [ ] bullet item can interrupt a paragraph: `Parser.LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH`, `ListOptions.bulletItemInterruptsParagraph`
- [ ] ordered item can interrupt a paragraph: `Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH`, `ListOptions.orderedItemInterruptsParagraph`
- [ ] ordered non 1 item can interrupt a paragraph: `Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH`, `ListOptions.orderedNonOneItemInterruptsParagraph`
- [ ] empty bullet item can interrupt a paragraph: `Parser.LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_PARAGRAPH`, `ListOptions.emptyBulletItemInterruptsParagraph`
- [ ] empty ordered item can interrupt a paragraph: `Parser.LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_PARAGRAPH`, `ListOptions.emptyOrderedItemInterruptsParagraph`
- [ ] empty ordered non 1 item can interrupt a paragraph: `Parser.LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH`, `ListOptions.emptyOrderedNonOneItemInterruptsParagraph`
- [ ] bullet item can interrupt a paragraph of a list item: `Parser.LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.bulletItemInterruptsItemParagraph`
- [ ] ordered item can interrupt a paragraph of a list item: `Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.orderedItemInterruptsItemParagraph`
- [ ] ordered non 1 item can interrupt a paragraph of a list item: `Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.orderedNonOneItemInterruptsItemParagraph`
- [ ] empty bullet item can interrupt a paragraph of a list item: `Parser.LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.emptyBulletItemInterruptsItemParagraph`
- [ ] empty ordered non 1 item can interrupt a paragraph of a list item: `Parser.LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.emptyOrderedItemInterruptsItemParagraph`
- [ ] empty ordered item can interrupt a paragraph of a list item: `Parser.LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.emptyOrderedNonOneItemInterruptsItemParagraph`
- [ ] empty bullet sub-item can interrupt a paragraph of a list item: `Parser.LISTS_EMPTY_BULLET_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.emptyBulletSubItemInterruptsItemParagraph`
- [ ] empty ordered non 1 sub-item can interrupt a paragraph of a list item: `Parser.LISTS_EMPTY_ORDERED_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.emptyOrderedSubItemInterruptsItemParagraph`
- [ ] empty ordered sub-item can interrupt a paragraph of a list item: `Parser.LISTS_EMPTY_ORDERED_NON_ONE_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.emptyOrderedNonOneSubItemInterruptsItemParagraph`
