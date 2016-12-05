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
        - `current indent` = `line indent`
    - Start List Conditions:
        - `item indent` < `CODE_INDENT`: new list with new item
        - `item content indent` >= `CODE_INDENT`: empty item, indented code
    - Continuation Conditions:
        - `current indent` >= `list last content indent` + `CODE_INDENT`: indented code
        - `current indent` >= `list last content indent`: sub-item
        - `current indent` >= `list indent`: list item

- MultiMarkdown: Pandoc, Pegdown, all fixed indent type processors 
    - Definitions/Defaults:
        - `ITEM_INDENT` = 4
        - `CODE_INDENT` = 8 
        - `current indent` = `line column` - `first parent list column` + `first parent list
          indent` - (`list nesting` - 1) * `ITEM_INDENT`
    - Start List Conditions:
        - `current indent` < `ITEM_INDENT`: new list with new item
    - Continuation Conditions:
         - `current indent` >= `CODE_INDENT`: indented code
         - `current indent` >= `ITEM_INDENT`: sub-item
         - `current indent` < `ITEM_INDENT`: list item

- Kramdown:  
    - Definitions/Defaults:
        - `ITEM_INDENT` = 4 
        - `CODE_INDENT` = 8 
        - `current indent` = `line indent`
    - Start List Conditions:
        - `current indent` < `ITEM_INDENT`: new list with new item
    - Continuation Conditions:
        - `current indent` >= `list content indent` + `CODE_INDENT`: indented code
        - `current indent` >= `list content indent` + `ITEM_INDENT`: 
            - if had blank line in item && have previous list item parent: then let it have it
            - otherwise: lazy continuation of last list item
        - `current indent` >= `item content indent`: sub-item
        - `current indent` >= `list content indent`: list item

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

Minor differences, are addressed with options applied on top of the `family` list behavior to
tweak parser emulation:

- [x] bullet item can interrupt a paragraph {C} {GFM} {GFC}
- [x] bullet item can interrupt a paragraph of a list item 
- [x] empty bullet item can interrupt a paragraph
- [ ] empty bullet item can interrupt a paragraph of a list item
- [x] ordered item can interrupt a paragraph
- [x] ordered item can interrupt a paragraph of a list item 
- [x] ordered non 1 item can interrupt a paragraph
- [x] ordered non 1 item can interrupt a paragraph of a list item 
- [ ] empty ordered item can interrupt a paragraph
- [ ] empty ordered item can interrupt a paragraph of a list item 
- [ ] empty ordered non 1 item can interrupt a paragraph
- [ ] empty ordered non 1 item can interrupt a paragraph of a list item 
- [x] mismatch item type continue same list type
- [x] mismatch item type start new list
- [x] mismatch item type start a sub-list
- [x] bullet mismatch starts a new list
- [x] ordered items only with `.` after digit, otherwise `)` is also allowed
- [x] first ordered item prefix sets start number of list 
- [x] item is loose if it has trailing blank line in it or its last child
- [ ] item is loose if previous item has trailing blank line in it or its last child
- [x] item is loose if it or previous item is loose 
- [x] all items are loose if any in the list are loose
