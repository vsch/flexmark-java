Markdown Processors Emulation
=============================

Despite its name, commonmark is neither a superset nor a subset of other markdown flavors.
Rather, it proposes a standard, unambiguous syntax specification for the original, "core"
Markdown, thus effectively introducing yet another flavor. While flexmark is by default
commonmark compliant, its parser can be tweaked in various ways. The sets of tweaks required to
emulate the most commonly used markdown parsers around are available in flexmark as
ParserEmulationProfiles.

As the name `ParserEmulationProfile` implies, it's only the parser that is adjusted to the
specific markdown flavor. Applying the profile does not add features beyond those available in
commonmark. If you want to use flexmark to fully emulate another markdown processor's behavior,
you have to adjust the parser and configure the flexmark extensions that provide the additional
features available in the parser that you want to emulate.

Parsers are classified by "Family" for their list processing characteristics, by far the
greatest point of deviation between all markdown parsers. They differ the greatest on how they
determining whether a text line is:

* indented code of the current item or parent list item's
* lazy continuation of current item
* next list item
* current item's sub-item

Some processors, like Kramdown and Markdown, use the list's first item indent to determine when
text is an item, lazy continuation or indented code. CommonMark, uses the last
item's content indent for this determination. MultiMarkdown, uses a fixed indent of 4 spaces
from left edge or last block quote marker to make that determination.

After trying to work this out by trial and error and only having moderate success, I decided to
take more rigorous approach to reduce interaction between options and to be able tweak
individual family emulation accuracy.

The following definitions are used:

* `index`: offset from start of line being processed
* `line indent`: index of first non-blank from index set by parent element for the line
* `line column`: would be index of first non-blank if tabs were expanded to 4 space boundaries
* `item indent`: `line indent` of first line of item (one with list item marker)
* `item column`: `line column` of first line of item (one with list item marker)
* `item content offset`: item prefix length + # of trailing white spaces following the item
  prefix
* `item content indent`: `item indent` + `item content offset`
* `item content column`: `item column` + `item content offset`
* `list indent`: first item's `item indent`
* `list column`: first item's `item column`
* `list content indent`: first item's `item content indent`
* `list content column`: first item's `item content column`
* `list last indent`: last item's `item indent`
* `list last column`: last item's `item column`
* `list last content indent`: last item's `item content indent`
* `list last content column`: last item's `item content column`
* `list nesting`: number of direct list ancestors of an item (counting stops on non-list or
  non-list-item parent block), always >= 1 since every list item has a list parent
* `first parent list`: last list block after non-list or non-list item block

Family type rough behavior, details are in the code for `ListBlockParser` and `ListItemParser`:

* CommonMark: of the spec, all common mark parsers
  * Definitions/Defaults:
    * `ITEM_INDENT` = 4 <!-- not used -->
    * `CODE_INDENT` = 4
    * `NEW_ITEM_CODE_INDENT` = 4
    * `current indent` = `line indent`
  * Start List Conditions:
    * `current indent` < `CODE_INDENT`
      * `item content indent` >= `NEW_ITEM_CODE_INDENT`: empty item + indented code
      * otherwise: new list with new item
  * Continuation Conditions:
    * `current indent` >= `list last content indent` + `CODE_INDENT`: indented code
    * `current indent` >= `list last content indent`: sub-item
    * `current indent` >= `list indent`: list item

* FixedIndent: Pandoc, MultiMarkdown, Pegdown
  * Definitions/Defaults:
    * `ITEM_INDENT` = 4
    * `CODE_INDENT` = 8
    * `current indent` = line indent
  * Start List Conditions:
    * `current indent` < `ITEM_INDENT`: new list with new item
  * Continuation Conditions:
    * `current indent` >= `CODE_INDENT`: item content
    * `current indent` >= `ITEM_INDENT`: sub-item or content
    * otherwise: list item or not ours

* Kramdown:
  * Definitions/Defaults:
    * `ITEM_INDENT` = 4
    * `CODE_INDENT` = 8
    * `current indent` = `line indent`
  * Start List Conditions:
    * `current indent` < `ITEM_INDENT`: new list with new item
  * Continuation Conditions:
    * `current indent` >= `item content indent`: sub-item or content
    * `current indent` >= `list indent` + `ITEM_INDENT`
      * hadBlankLine: end current item, keep loose status, item content
      * !hadBlankLine: lazy continuation
    * `current indent` >= `list indent` + `CODE_INDENT`: item content
    * `current indent` >= `list indent`: list item or not ours

* Markdown:
  * Definitions/Defaults:
    * `ITEM_INDENT` = 4
    * `CODE_INDENT` = 8
    * `current indent` = `line indent`
  * Start List Conditions:
    * `current indent` < `ITEM_INDENT`: new list with new item
  * Continuation Conditions:
    * `current indent` >= `CODE_INDENT`: item content
    * `current indent` > `ITEM_INDENT`: sub-item or content
    * `current indent` > `list indent`: sub-item or content
    * otherwise: list item or not ours

##### List Parsing Options

Parser configuration parameters, parser emulation family sets defaults but these can be modified
to tweak parser behaviour:

<!-- @formatter:off -->

- [ ] item indent columns: `Parser.ITEM_INDENT`, `ListOptions.itemIndent`
- [ ] item code indent column: `Parser.CODE_INDENT`, `ListOptions.codeIndent`
- [ ] new item code indent column: `Parser.NEW_ITEM_CODE_INDENT`, `ListOptions.newItemCodeIndent`
- [ ] list items require explicit space after marker `Parser.LISTS_ITEM_MARKER_SPACE`, `ListOptions.itemMarkerSpace`
- [ ] mismatch item type starts a new list: `Parser.LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST`, `ListOptions.itemTypeMismatchToNewList`
- [ ] mismatch item type start a sub-list: `Parser.LISTS_ITEM_TYPE_MISMATCH_TO_SUB_LIST`, `ListOptions.itemTypeMismatchToSubList`
- [ ] bullet or ordered item delimiter mismatch starts a new list: `Parser.LISTS_DELIMITER_MISMATCH_TO_NEW_LIST`, `ListOptions.delimiterMismatchToNewList`
- [ ] ordered items only with `.` after digit, otherwise `)` is also allowed: `Parser.LISTS_ORDERED_ITEM_DOT_ONLY`, `ListOptions.orderedItemDotOnly`
- [ ] first ordered item prefix sets start number of list: `Parser.LISTS_ORDERED_LIST_MANUAL_START`, `ListOptions.orderedListManualStart`
- [ ] item is loose if it contains a blank line after its item text: `Parser.LISTS_LOOSE_WHEN_BLANK_FOLLOWS_ITEM_PARAGRAPH`, `ListOptions.looseWhenBlankFollowsItemParagraph`
- [ ] item is loose if it or previous item is loose: `Parser.LISTS_LOOSE_ON_PREV_LOOSE_ITEM`, `ListOptions.looseOnPrevLooseItem`
- [ ] item is loose if it has loose sub-item: `Parser.LISTS_LOOSE_WHEN_HAS_LOOSE_SUB_ITEM`, `ListOptions.looseWhenHasLooseSubItem`
- [ ] item is loose if it has trailing blank line in it or its last child: `Parser.LISTS_LOOSE_WHEN_HAS_TRAILING_BLANK_LINE`, `ListOptions.looseWhenHasTrailingBlankLine`
- [ ] all items are loose if any in the list are loose: `Parser.LISTS_AUTO_LOOSE`, `ListOptions.autoLoose`
- [ ] auto loose list setting `Parser.LISTS_AUTO_LOOSE` only applies to simple 1 level lists: `Parser.LISTS_AUTO_LOOSE_ONE_LEVEL_LISTS`, `ListOptions.autoLooseOneLevelLists`

<!-- @formatter:on -->

:warning: If both `LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST` and
`LISTS_ITEM_TYPE_MISMATCH_TO_SUB_LIST` are set to true then a new list will be created if the
item had a blank line, otherwise a sub-list is created.

##### List Item Paragraph Interruption Options

<!-- @formatter:off -->

- [ ] bullet item can interrupt a paragraph: `Parser.LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH`, `ListOptions.itemInterrupt.bulletItemInterruptsParagraph`
- [ ] ordered item can interrupt a paragraph: `Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH`, `ListOptions.itemInterrupt.orderedItemInterruptsParagraph`
- [ ] ordered non 1 item can interrupt a paragraph: `Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH`, `ListOptions.itemInterrupt.orderedNonOneItemInterruptsParagraph`
- [ ] empty bullet item can interrupt a paragraph: `Parser.LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_PARAGRAPH`, `ListOptions.itemInterrupt.emptyBulletItemInterruptsParagraph`
- [ ] empty ordered item can interrupt a paragraph: `Parser.LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_PARAGRAPH`, `ListOptions.itemInterrupt.emptyOrderedItemInterruptsParagraph`
- [ ] empty ordered non 1 item can interrupt a paragraph: `Parser.LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH`, `ListOptions.itemInterrupt.emptyOrderedNonOneItemInterruptsParagraph`
- [ ] bullet item can interrupt a paragraph of a list item: `Parser.LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.itemInterrupt.bulletItemInterruptsItemParagraph`
- [ ] ordered item can interrupt a paragraph of a list item: `Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.itemInterrupt.orderedItemInterruptsItemParagraph`
- [ ] ordered non 1 item can interrupt a paragraph of a list item: `Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.itemInterrupt.orderedNonOneItemInterruptsItemParagraph`
- [ ] empty bullet item can interrupt a paragraph of a list item: `Parser.LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.itemInterrupt.emptyBulletItemInterruptsItemParagraph`
- [ ] empty ordered non 1 item can interrupt a paragraph of a list item: `Parser.LISTS_EMPTY_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.itemInterrupt.emptyOrderedItemInterruptsItemParagraph`
- [ ] empty ordered item can interrupt a paragraph of a list item: `Parser.LISTS_EMPTY_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.itemInterrupt.emptyOrderedNonOneItemInterruptsItemParagraph`
- [ ] empty bullet sub-item can interrupt a paragraph of a list item: `Parser.LISTS_EMPTY_BULLET_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.itemInterrupt.emptyBulletSubItemInterruptsItemParagraph`
- [ ] empty ordered non 1 sub-item can interrupt a paragraph of a list item: `Parser.LISTS_EMPTY_ORDERED_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.itemInterrupt.emptyOrderedSubItemInterruptsItemParagraph`
- [ ] empty ordered sub-item can interrupt a paragraph of a list item: `Parser.LISTS_EMPTY_ORDERED_NON_ONE_SUB_ITEM_INTERRUPTS_ITEM_PARAGRAPH`, `ListOptions.itemInterrupt.emptyOrderedNonOneSubItemInterruptsItemParagraph`

<!-- @formatter:on -->
