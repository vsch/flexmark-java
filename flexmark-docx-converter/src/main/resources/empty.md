## Markdown to DOCX Empty Document  Template

This document is used to generate an empty document template for the docx conversion.

The page content is ignored and only used to show/modify all the styles used by the markdown
conversion process.

**DO NOT CHANGE PAGE CONTENT IN THIS DOCUMENT**

**Changes you can make that will be used to generate DOCX from markdown:**

1. All styles inherit from docx style "Normal" you should first adjust the font family and font
   size of this style, if desired.
2. You may have two "Heading" styles. Open Format/Styles and delete one of the duplicates. Keep
   the one that is closest to what you want for headings. **NOTE:** even if the headings in this
   document look good, with a duplicate "Heading" style there is no guarantee which one will be
   used when documents are generated from this template.
3. All Heading... styles inherit from docx style "Heading", to change the heading font you
   should change this style.
4. Ordered Lists use numbering list style `NumberedList`
5. Unordered Lists use numbering list style `BulletList`
6. In the document pages change styles given by name after `docx style "..."` do not yet change
   the text.
7. Change page footer/header text and formatting, as you like. They are used as is in the
   conversion process. Make sure you have at least 3 pages so you can see styles for first page,
   even page and odd page.
8. When done changing styles and headers/footers, delete any document page content you don't
   want to be included in every conversion. You can do select all and delete if all the content
   will come from markdown.
9. Save it under the name you wish to use as the empty document template for conversion.
10. You can make changes to styles directly in the saved document but if you deleted the text
    with these styles, you will not be able to preview the results.

The following document content can be used to preview changes to formatting styles and make
changes to them.

---

**Heading Styles**

# Heading 1 docx style "Heading 1"

## Heading 2 docx style "Heading 2"

###  Heading 3 docx style "Heading 3"

####  Heading 4 docx style "Heading 4"

#####  Heading 5 docx style "Heading 5"

######  Heading 6 docx style "Heading 6"

---

**Horizontal Line Style**

HORIZONTAL_LINE_STYLE docx style named "Horizontal Line" generates the following horizontal rule

---

**Paragraph Styles**

LOOSE_PARAGRAPH_STYLE docx style named "Paragraph Text Body" is used for text and loosely spaced
list item content

* loose list item

* loose list item

TIGHT_PARAGRAPH_STYLE docx style named "Body Text" is used for tightly spaced list item content

* tight list item
* tight list item

> BLOCK_QUOTE_STYLE docx style named "Quotations" is used for block quotes

| ASIDE_BLOCK_STYLE docx style named "AsideBlock" is used for aside blocks

    PREFORMATTED_TEXT_STYLE docx style named "Preformatted Text" used for code fence and indented code

---

**Character styles used in text formatting:**

* BOLD_STYLE - docx style named "Strong Emphasis" plain **sample** plain
* ITALIC_STYLE - docx style named "Emphasis" plain *sample* plain
* STRIKE_THROUGH_STYLE - docx style named "Strikethrough" plain ~~sample~~ plain
* SUBSCRIPT_STYLE - docx style named "Subscript" plain ~sample~ plain
* SUPERSCRIPT_STYLE - docx style named "Superscript" plain ^sample^ plain
* INS_STYLE - docx style named "Underlined" plain ++sample++ plain
* INLINE_CODE_STYLE - docx style named "Source Text" plain `sample` plain
* HYPERLINK_STYLE - docx style named "Hyperlink" plain [sample](http://example.com) plain
* FOOTNOTE_ANCHOR_STYLE - docx style named "Footnote Reference" plain **[^1]** plain

---

**Table Styles**

|  TABLE_HEADING docx style named "Table Heading"  | TABLE_HEADING  |
|--------------------------------------------------|----------------|
| TABLE_CONTENTS docx style named "Table Contents" | TABLE_CONTENTS |
| TABLE_CONTENTS                                   | TABLE_CONTENTS |
[TABLE_CAPTION docx style named "Table Caption"]

---

**The following can only be used for previewing**

Changes can be made but this has to be done in the flexmark-java docx converter `empty.xml`
resource. Then conversion with this basic template should be run against the `empty.md` file to
generate this docx starter template.

Bullet list (Given by numbering list style `BulletList` or default numbering list style if one
is not given)

* Bullet Level 1
  * Bullet Level 2
    * Bullet Level 3
      * Bullet Level 4
        * Bullet Level 5
          * Bullet Level 6
            * Bullet Level 7
              * Bullet Level 8
                * Bullet Level 9

Numbered List (Given by numbering list style `NumberedList` or default numbering list style if
one is not given)

1. Numbered Level 1
   1. Numbered Level 2
      1. Numbered Level 3
         1. Numbered Level 4
            1. Numbered Level 5
               1. Numbered Level 6
                  1. Numbered Level 7
                     1. Numbered Level 8
                        1. Numbered Level 9

Block Quoted Bullet list

> * Bullet Level 1
>   * Bullet Level 2
>     * Bullet Level 3
>       * Bullet Level 4

Block Quoted Numbered List

> 1. Numbered Level 1
>    1. Numbered Level 2
>       1. Numbered Level 3
>          1. Numbered Level 4

Aside Block Bullet list

| * Bullet Level 1
|   * Bullet Level 2
|     * Bullet Level 3
|       * Bullet Level 4

Aside Block Numbered List

| 1. Numbered Level 1
|    1. Numbered Level 2
|       1. Numbered Level 3
|          1. Numbered Level 4

[^1]: FOOTNOTE_STYLE named "Footnote" Text Sample

