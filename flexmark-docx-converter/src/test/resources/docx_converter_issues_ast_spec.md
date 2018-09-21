---
title: DocxConverter Extension Spec
author: Vladimir Schneider
version: 1.0
date: '2017-11-23'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## DocxConverter Issues

Issue related tests

### Issue 176

Issue #176, docx-converter pandoc emulation mode

```````````````````````````````` example Issue 176: 1
### header
1. List item started from 1
### header
1. List item started from 1
1. List item continued 2
### test
1. List item started from 1
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="header"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">header</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">List item started from 1</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="header-1"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">header</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="5"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">List item started from 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="5"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">List item continued 2</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="3" w:name="test"/>
    <w:bookmarkEnd w:id="3"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">test</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="6"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">List item started from 1</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Issue - Footnote relationship parts

Hyperlinks in footnotes would cause invalid document

```````````````````````````````` example Issue - Footnote relationship parts: 1
Footnote [^1]

[^1]: Link [www.example](http://www.example.com) 
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Footnote </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="FootnoteReference"/>
            </w:rPr>
            <w:footnoteReference w:id="1"/>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="1">
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Footnote"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="FootnoteReference"/>
            </w:rPr>
            <w:footnoteRef/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">→</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">Link </w:t>
        </w:r>
        <w:hyperlink r:id="rId1">
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">www.example</w:t>
            </w:r>
        </w:hyperlink>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


Images in footnotes would cause invalid document

```````````````````````````````` example(Issue - Footnote relationship parts: 2) options(IGNORED)
Footnote [^1]

[^1]: Link ![flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Footnote </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="FootnoteReference"/>
            </w:rPr>
            <w:footnoteReference w:id="1"/>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="1">
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Footnote"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="FootnoteReference"/>
            </w:rPr>
            <w:footnoteRef/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">→</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">Link </w:t>
        </w:r>
        <w:r>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="541871" cy="541871"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="flexmark-icon-logo" id="0" name="Image1"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="1" name="Image1"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId1"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="541871" cy="541871"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


## Issue - Footnote reference

Formatting applied to reference gets applied to footnote block

```````````````````````````````` example Issue - Footnote reference: 1
Footnote **[^1]**

[^1]: Should be plain text 

.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Footnote </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="FootnoteReference"/>
            </w:rPr>
            <w:footnoteReference w:id="1"/>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="1">
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Footnote"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="FootnoteReference"/>
            </w:rPr>
            <w:footnoteRef/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">→</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">Should be plain text</w:t>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


## flexmark-empty-template

```````````````````````````````` example flexmark-empty-template: 1
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
   document look good, with a duplicate "Heading" style there is guarantee which one will be
   used when documents are generated from this template.
3. All Heading... styles inherit from docx style "Heading", to change the heading font you
   should change this style.
4. In the document pages change styles given by name after `docx style "..."` do not yet change
   the text.
5. Change page footer/header text and formatting, as you like. They are used as is in the
   conversion process. Make sure you have at least 3 pages so you can see styles for first page,
   even page and odd page.
6. When done changing styles and headers/footers, delete any document page content you don't
   want to be included in every conversion. You can do select all and delete if all the content
   will come from markdown.
7. Save it under the name you wish to use as the empty document template for conversion.
8. You can make changes to styles directly in the saved document but if you deleted the text
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

| TABLE_HEADING docx style named "Table Heading"   | TABLE_HEADING  |
|--------------------------------------------------|----------------|
| TABLE_CONTENTS docx style named "Table Contents" | TABLE_CONTENTS |
| TABLE_CONTENTS                                   | TABLE_CONTENTS |
[TABLE_CAPTION docx style named "Table Caption"]

---

**The following can only be used for previewing**

Changes can be made but this has to be done in the flexmark-java docx converter `empty.xml`
resource. Then conversion with this basic template should be run against the `empty.md` file to
generate this docx starter template.

Bullet list (default bullet list style)

* Bullet Level 1
  * Bullet Level 2
    * Bullet Level 3
      * Bullet Level 4
        * Bullet Level 5
          * Bullet Level 6
            * Bullet Level 7
              * Bullet Level 8
                * Bullet Level 9

Numbered List (default numbered list style)

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

.
<w:body>
    <w:bookmarkStart w:id="1" w:name="markdown-to-docx-empty-document-0"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Markdown to DOCX Empty Document  Template</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">This document is used to generate an empty document template for the docx conversion.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">The page content is ignored and only used to show/modify all the styles used by the markdown</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">conversion process.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">DO NOT CHANGE PAGE CONTENT IN THIS DOCUMENT</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">Changes you can make that will be used to generate DOCX from markdown:</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">All styles inherit from docx style "Normal" you should first adjust the font family and font</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">size of this style, if desired.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">You may have two "Heading" styles. Open Format/Styles and delete one of the duplicates. Keep</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">the one that is closest to what you want for headings. </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">NOTE:</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> even if the headings in this</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">document look good, with a duplicate "Heading" style there is guarantee which one will be</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">used when documents are generated from this template.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">All Heading... styles inherit from docx style "Heading", to change the heading font you</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">should change this style.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">In the document pages change styles given by name after </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="SourceText"/>
            </w:rPr>
            <w:t xml:space="preserve">docx style "..."</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> do not yet change</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">the text.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Change page footer/header text and formatting, as you like. They are used as is in the</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">conversion process. Make sure you have at least 3 pages so you can see styles for first page,</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">even page and odd page.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">When done changing styles and headers/footers, delete any document page content you don't</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">want to be included in every conversion. You can do select all and delete if all the content</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">will come from markdown.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Save it under the name you wish to use as the empty document template for conversion.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">You can make changes to styles directly in the saved document but if you deleted the text</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">with these styles, you will not be able to preview the results.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">The following document content can be used to preview changes to formatting styles and make</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">changes to them.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="HorizontalLine"/>
        </w:pPr>
        <w:r/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">Heading Styles</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-1-docx-style-heading-1"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 1 docx style "Heading 1"</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="3" w:name="heading-2-docx-style-heading-2"/>
    <w:bookmarkEnd w:id="3"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 2 docx style "Heading 2"</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="4" w:name="heading-3-docx-style-heading-3"/>
    <w:bookmarkEnd w:id="4"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 3 docx style "Heading 3"</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="5" w:name="heading-4-docx-style-heading-4"/>
    <w:bookmarkEnd w:id="5"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading4"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 4 docx style "Heading 4"</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="6" w:name="heading-5-docx-style-heading-5"/>
    <w:bookmarkEnd w:id="6"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading5"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 5 docx style "Heading 5"</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="7" w:name="heading-6-docx-style-heading-6"/>
    <w:bookmarkEnd w:id="7"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading6"/>
            <w:numPr>
                <w:ilvl w:val="5"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 6 docx style "Heading 6"</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="HorizontalLine"/>
        </w:pPr>
        <w:r/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">Horizontal Line Style</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">HORIZONTAL_LINE_STYLE docx style named "Horizontal Line" generates the following horizontal rule</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="HorizontalLine"/>
        </w:pPr>
        <w:r/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">Paragraph Styles</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">LOOSE_PARAGRAPH_STYLE docx style named "Paragraph Text Body" is used for text and loosely spaced</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">list item content</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="5"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">loose list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="5"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">loose list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">TIGHT_PARAGRAPH_STYLE docx style named "Body Text" is used for tightly spaced list item content</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="6"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">tight list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="6"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">tight list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">BLOCK_QUOTE_STYLE docx style named "Quotations" is used for block quotes</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">ASIDE_BLOCK_STYLE docx style named "AsideBlock" is used for aside blocks</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="240" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="PreformattedText"/>
            <w:spacing w:after="0" w:before="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">PREFORMATTED_TEXT_STYLE docx style named "Preformatted Text" used for code fence and indented code</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="240" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="HorizontalLine"/>
        </w:pPr>
        <w:r/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">Character styles used in text formatting:</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="7"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">BOLD_STYLE - docx style named "Strong Emphasis" plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">sample</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> plain</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="7"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">ITALIC_STYLE - docx style named "Emphasis" plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">sample</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> plain</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="7"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">STRIKE_THROUGH_STYLE - docx style named "Strikethrough" plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
            </w:rPr>
            <w:t xml:space="preserve">sample</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> plain</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="7"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">SUBSCRIPT_STYLE - docx style named "Subscript" plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
            </w:rPr>
            <w:t xml:space="preserve">sample</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> plain</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="7"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">SUPERSCRIPT_STYLE - docx style named "Superscript" plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
            </w:rPr>
            <w:t xml:space="preserve">sample</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> plain</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="7"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">INS_STYLE - docx style named "Underlined" plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
            </w:rPr>
            <w:t xml:space="preserve">sample</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> plain</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="7"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">INLINE_CODE_STYLE - docx style named "Source Text" plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="SourceText"/>
            </w:rPr>
            <w:t xml:space="preserve">sample</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> plain</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="7"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">HYPERLINK_STYLE - docx style named "Hyperlink" plain </w:t>
        </w:r>
        <w:hyperlink r:id="rId3">
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">sample</w:t>
            </w:r>
        </w:hyperlink>
        <w:r>
            <w:t xml:space="preserve"> plain</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="7"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">FOOTNOTE_ANCHOR_STYLE - docx style named "Footnote Reference" plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="FootnoteReference"/>
            </w:rPr>
            <w:footnoteReference w:id="1"/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> plain</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="HorizontalLine"/>
        </w:pPr>
        <w:r/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">Table Styles</w:t>
        </w:r>
    </w:p>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="120"/>
            <w:tblBorders>
                <w:top w:color="000001" w:space="0" w:sz="2" w:val="single"/>
                <w:left w:color="000001" w:space="0" w:sz="2" w:val="single"/>
                <w:bottom w:color="000001" w:space="0" w:sz="2" w:val="single"/>
                <w:right w:color="000001" w:space="0" w:sz="2" w:val="single"/>
                <w:insideH w:color="000001" w:space="0" w:sz="2" w:val="single"/>
                <w:insideV w:color="000001" w:space="0" w:sz="2" w:val="single"/>
            </w:tblBorders>
            <w:tblCellMar>
                <w:top w:type="dxa" w:w="80"/>
                <w:left w:type="dxa" w:w="80"/>
                <w:bottom w:type="dxa" w:w="80"/>
                <w:right w:type="dxa" w:w="80"/>
            </w:tblCellMar>
            <w:tblLook w:firstColumn="1" w:firstRow="1" w:lastColumn="0"
                w:lastRow="0" w:noHBand="0" w:noVBand="1" w:val="04a0"/>
        </w:tblPr>
        <w:tr>
            <w:trPr>
                <w:tblHeader/>
            </w:trPr>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">TABLE_HEADING docx style named "Table Heading"</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">TABLE_HEADING</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
        <w:tr>
            <w:trPr/>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">TABLE_CONTENTS docx style named "Table Contents"</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">TABLE_CONTENTS</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
        <w:tr>
            <w:trPr/>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">TABLE_CONTENTS</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">TABLE_CONTENTS</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TableCaption"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">TABLE_CAPTION docx style named "Table Caption"</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="HorizontalLine"/>
        </w:pPr>
        <w:r/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">The following can only be used for previewing</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Changes can be made but this has to be done in the flexmark-java docx converter </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="SourceText"/>
            </w:rPr>
            <w:t xml:space="preserve">empty.xml</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">resource. Then conversion with this basic template should be run against the </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="SourceText"/>
            </w:rPr>
            <w:t xml:space="preserve">empty.md</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> file to</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">generate this docx starter template.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Bullet list (default bullet list style)</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="8"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Bullet Level 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="9"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Bullet Level 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="10"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Bullet Level 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="11"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Bullet Level 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="12"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Bullet Level 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="5"/>
                <w:numId w:val="13"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Bullet Level 6</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="6"/>
                <w:numId w:val="14"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Bullet Level 7</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="7"/>
                <w:numId w:val="15"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Bullet Level 8</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="8"/>
                <w:numId w:val="16"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Bullet Level 9</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Numbered List (default numbered list style)</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="17"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Numbered Level 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="18"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Numbered Level 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="19"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Numbered Level 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="20"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Numbered Level 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="21"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Numbered Level 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="5"/>
                <w:numId w:val="22"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Numbered Level 6</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="6"/>
                <w:numId w:val="23"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Numbered Level 7</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="7"/>
                <w:numId w:val="24"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Numbered Level 8</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="8"/>
                <w:numId w:val="25"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Numbered Level 9</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Block Quoted Bullet list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="26"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="467" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">Bullet Level 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="27"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="20" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="687" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">Bullet Level 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="28"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="31" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="907" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">Bullet Level 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="29"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="31" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="1147" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">Bullet Level 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Block Quoted Numbered List</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="30"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="523" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">Numbered Level 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="31"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="23" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="803" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">Numbered Level 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="32"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="31" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="1083" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">Numbered Level 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="33"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="31" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="1363" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">Numbered Level 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Aside Block Bullet list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="34"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="467" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Bullet Level 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="35"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="20" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="687" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Bullet Level 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="36"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="31" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="907" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Bullet Level 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="37"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="31" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="1147" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Bullet Level 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Aside Block Numbered List</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="38"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="523" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Numbered Level 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="39"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="23" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="803" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Numbered Level 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="40"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="31" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="1083" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Numbered Level 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="31" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="1363" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Numbered Level 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="1">
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Footnote"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="FootnoteReference"/>
            </w:rPr>
            <w:footnoteRef/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">→</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">FOOTNOTE_STYLE named "Footnote" Text Sample</w:t>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


## Heading Bookmarks

```````````````````````````````` example Heading Bookmarks: 1
# Heading 1

Some text

## Heading 2

[Heading 1 reference with **formatting**](#heading-1) 
[Heading 2 reference with **formatting**](#heading-2) 

.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading-1"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Some text</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-2"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:hyperlink w:anchor="heading-1">
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">Heading 1 reference with </w:t>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="StrongEmphasis"/>
                    <w:color w:themeColor="hyperlink" w:val="0366D6"/>
                    <w:u w:val="single"/>
                </w:rPr>
                <w:t xml:space="preserve">formatting</w:t>
            </w:r>
        </w:hyperlink>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:hyperlink w:anchor="heading-2">
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">Heading 2 reference with </w:t>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="StrongEmphasis"/>
                    <w:color w:themeColor="hyperlink" w:val="0366D6"/>
                    <w:u w:val="single"/>
                </w:rPr>
                <w:t xml:space="preserve">formatting</w:t>
            </w:r>
        </w:hyperlink>
    </w:p>
</w:body>
````````````````````````````````


## Missing Local Hyperlink

```````````````````````````````` example Missing Local Hyperlink: 1
[text](#missing)

.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:hyperlink w:anchor="missing" w:tooltip="Missing target id: #missing">
            <w:r>
                <w:rPr>
                    <w:color w:themeColor="hyperlink" w:val="0366D6"/>
                    <w:highlight w:val="red"/>
                    <w:u w:val="single"/>
                </w:rPr>
                <w:t xml:space="preserve">text</w:t>
            </w:r>
        </w:hyperlink>
    </w:p>
</w:body>
````````````````````````````````


custom color

```````````````````````````````` example(Missing Local Hyperlink: 2) options(yellow-missing-hyperlink)
[text](#missing)

.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:hyperlink w:anchor="missing" w:tooltip="Missing target id: #missing">
            <w:r>
                <w:rPr>
                    <w:color w:themeColor="hyperlink" w:val="0366D6"/>
                    <w:highlight w:val="yellow"/>
                    <w:u w:val="single"/>
                </w:rPr>
                <w:t xml:space="preserve">text</w:t>
            </w:r>
        </w:hyperlink>
    </w:p>
</w:body>
````````````````````````````````


suppress

```````````````````````````````` example(Missing Local Hyperlink: 3) options(no-missing-hyperlink)
[text](#missing)

.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:hyperlink w:anchor="missing" w:tooltip="Missing target id: #missing">
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">text</w:t>
            </w:r>
        </w:hyperlink>
    </w:p>
</w:body>
````````````````````````````````


wrong bookmark destination

```````````````````````````````` example Missing Local Hyperlink: 4
text{#tmp:test}

See [@tmp:test]

```
text{#tmp:test}

See [@tmp:test]
```
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:bookmarkStart w:id="1" w:name="tmp:test"/>
        <w:r>
            <w:t xml:space="preserve">text</w:t>
        </w:r>
        <w:bookmarkEnd w:id="1"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">See </w:t>
        </w:r>
        <w:hyperlink w:anchor="tmp:test" w:tooltip="tmp 1">
            <w:r>
                <w:fldChar w:fldCharType="begin"/>
            </w:r>
            <w:r>
                <w:instrText xml:space="preserve"> HYPERLINK "#tmp:test" \o "tmp 1" </w:instrText>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="separate"/>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">tmp 1</w:t>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="end"/>
            </w:r>
        </w:hyperlink>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="240" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="PreformattedText"/>
            <w:spacing w:after="0" w:before="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">text{#tmp:test}</w:t>
        </w:r>
        <w:r>
            <w:br/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"/>
        </w:r>
        <w:r>
            <w:br/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">See [@tmp:test]</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="240" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
.
Document[0, 75]
  Paragraph[0, 16] isTrailingBlankLine
    TextBase[0, 4] chars:[0, 4, "text"]
      Text[0, 4] chars:[0, 4, "text"]
    AttributesNode[4, 15] textOpen:[4, 5, "{"] text:[5, 14, "#tmp:test"] textClose:[14, 15, "}"]
      AttributeNode[5, 14] name:[5, 6, "#"] value:[6, 14, "tmp:test"] isImplicit isId
  Paragraph[17, 33] isTrailingBlankLine
    Text[17, 21] chars:[17, 21, "See "]
    EnumeratedReferenceLink[21, 32] textOpen:[21, 23, "[@"] text:[23, 31, "tmp:test"] textClose:[31, 32, "]"]
      Text[23, 31] chars:[23, 31, "tmp:test"]
  FencedCodeBlock[34, 74] open:[34, 37, "```"] content:[38, 71] lines[3] close:[71, 74, "```"]
    Text[38, 71] chars:[38, 71, "text{ … est]\n"]
````````````````````````````````


## Issue Autolinks

Rendering autolinks NPE

```````````````````````````````` example Issue Autolinks: 1
www.example.com
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:hyperlink r:id="rId3">
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">www.example.com</w:t>
            </w:r>
        </w:hyperlink>
    </w:p>
</w:body>
````````````````````````````````


## Emoji

```````````````````````````````` example Emoji: 1
TEXT :+1: TEXT

.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">TEXT </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-2"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="146685" cy="146685"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji people:+1" id="0" name="Image1"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="1" name="Image1"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="146685" cy="146685"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> TEXT</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Emoji: 2
# Heading 1 :+1:

## Heading 2 :+1:

### Heading 3 :+1:

#### Heading 4 :+1:

##### Heading 5 :+1:

###### Heading 6 :+1:

TEXT :+1: TEXT

.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading-1"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 1 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-5"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="306705" cy="306705"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji people:+1" id="0" name="Image1"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="1" name="Image1"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="306705" cy="306705"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-2"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 2 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-4"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="266700" cy="266700"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji people:+1" id="2" name="Image2"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="3" name="Image2"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId4"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="266700" cy="266700"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="3" w:name="heading-3"/>
    <w:bookmarkEnd w:id="3"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 3 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-4"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="226695" cy="226695"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji people:+1" id="4" name="Image3"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="5" name="Image3"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId5"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="226695" cy="226695"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="4" w:name="heading-4"/>
    <w:bookmarkEnd w:id="4"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading4"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 4 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-3"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="186690" cy="186690"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji people:+1" id="6" name="Image4"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="7" name="Image4"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId6"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="186690" cy="186690"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="5" w:name="heading-5"/>
    <w:bookmarkEnd w:id="5"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading5"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 5 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-2"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="146685" cy="146685"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji people:+1" id="8" name="Image5"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="9" name="Image5"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId7"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="146685" cy="146685"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="6" w:name="heading-6"/>
    <w:bookmarkEnd w:id="6"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading6"/>
            <w:numPr>
                <w:ilvl w:val="5"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 6 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-2"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="146685" cy="146685"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji people:+1" id="10" name="Image6"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="11" name="Image6"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId8"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="146685" cy="146685"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">TEXT </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-2"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="146685" cy="146685"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji people:+1" id="12" name="Image7"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="13" name="Image7"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId9"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="146685" cy="146685"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> TEXT</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Emoji: 3) options(emoji-github)
# Heading 1 :+1:

## Heading 2 :+1:

### Heading 3 :+1:

#### Heading 4 :+1:

##### Heading 5 :+1:

###### Heading 6 :+1:

TEXT :+1: TEXT

.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading-1"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 1 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-5"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="306705" cy="306705"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji people:+1" id="0" name="Image1"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="1" name="Image1"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="306705" cy="306705"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-2"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 2 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-4"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="266700" cy="266700"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji people:+1" id="2" name="Image2"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="3" name="Image2"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId4"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="266700" cy="266700"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="3" w:name="heading-3"/>
    <w:bookmarkEnd w:id="3"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 3 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-4"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="226695" cy="226695"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji people:+1" id="4" name="Image3"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="5" name="Image3"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId5"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="226695" cy="226695"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="4" w:name="heading-4"/>
    <w:bookmarkEnd w:id="4"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading4"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 4 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-3"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="186690" cy="186690"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji people:+1" id="6" name="Image4"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="7" name="Image4"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId6"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="186690" cy="186690"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="5" w:name="heading-5"/>
    <w:bookmarkEnd w:id="5"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading5"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 5 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-2"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="146685" cy="146685"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji people:+1" id="8" name="Image5"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="9" name="Image5"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId7"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="146685" cy="146685"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="6" w:name="heading-6"/>
    <w:bookmarkEnd w:id="6"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading6"/>
            <w:numPr>
                <w:ilvl w:val="5"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 6 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-2"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="146685" cy="146685"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji people:+1" id="10" name="Image6"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="11" name="Image6"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId8"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="146685" cy="146685"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">TEXT </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-2"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="146685" cy="146685"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji people:+1" id="12" name="Image7"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="13" name="Image7"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId9"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="146685" cy="146685"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> TEXT</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Emoji: 4) options(emoji-github)
# Heading 1 :basecamp:

## Heading 2 :basecamp:

### Heading 3 :basecamp:

#### Heading 4 :basecamp:

##### Heading 5 :basecamp:

###### Heading 6 :basecamp:

TEXT :basecamp: TEXT

.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading-1"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 1 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-5"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="306705" cy="306705"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji symbols:basecamp" id="0" name="Image1"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="1" name="Image1"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="306705" cy="306705"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-2"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 2 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-4"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="266700" cy="266700"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji symbols:basecamp" id="2" name="Image2"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="3" name="Image2"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId4"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="266700" cy="266700"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="3" w:name="heading-3"/>
    <w:bookmarkEnd w:id="3"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 3 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-4"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="226695" cy="226695"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji symbols:basecamp" id="4" name="Image3"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="5" name="Image3"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId5"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="226695" cy="226695"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="4" w:name="heading-4"/>
    <w:bookmarkEnd w:id="4"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading4"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 4 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-3"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="186690" cy="186690"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji symbols:basecamp" id="6" name="Image4"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="7" name="Image4"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId6"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="186690" cy="186690"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="5" w:name="heading-5"/>
    <w:bookmarkEnd w:id="5"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading5"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 5 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-2"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="146685" cy="146685"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji symbols:basecamp" id="8" name="Image5"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="9" name="Image5"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId7"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="146685" cy="146685"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="6" w:name="heading-6"/>
    <w:bookmarkEnd w:id="6"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading6"/>
            <w:numPr>
                <w:ilvl w:val="5"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 6 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-2"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="146685" cy="146685"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji symbols:basecamp" id="10" name="Image6"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="11" name="Image6"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId8"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="146685" cy="146685"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">TEXT </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-2"/>
            </w:rPr>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="146685" cy="146685"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="emoji symbols:basecamp" id="12" name="Image7"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="13" name="Image7"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId9"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="146685" cy="146685"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> TEXT</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Emoji: 5) options(emoji-unicode)
# Heading 1 :+1:

## Heading 2 :+1:

### Heading 3 :+1:

#### Heading 4 :+1:

##### Heading 5 :+1:

###### Heading 6 :+1:

TEXT :+1: TEXT

.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading-1"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 1 </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">&#x1f44d;</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-2"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 2 </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">&#x1f44d;</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="3" w:name="heading-3"/>
    <w:bookmarkEnd w:id="3"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 3 </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">&#x1f44d;</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="4" w:name="heading-4"/>
    <w:bookmarkEnd w:id="4"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading4"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 4 </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">&#x1f44d;</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="5" w:name="heading-5"/>
    <w:bookmarkEnd w:id="5"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading5"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 5 </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">&#x1f44d;</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="6" w:name="heading-6"/>
    <w:bookmarkEnd w:id="6"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading6"/>
            <w:numPr>
                <w:ilvl w:val="5"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 6 </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">&#x1f44d;</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">TEXT </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">&#x1f44d;</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> TEXT</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Image CRC Error

```````````````````````````````` example(Image CRC Error: 1) options(IGNORED)
![crc-error](/images/crc-error.png)
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="6332220" cy="6020565"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="crc-error" id="0" name="Image1"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="1" name="Image1"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="6332220" cy="6020565"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## www. link

unprefixed

```````````````````````````````` example(www. link: 1) options(no-www-prefix)
[www.example](www.example.com) 
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:hyperlink r:id="rId3">
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">www.example</w:t>
            </w:r>
        </w:hyperlink>
    </w:p>
</w:body>
````````````````````````````````


prefixed

```````````````````````````````` example www. link: 2
[www.example](www.example.com) 
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:hyperlink r:id="rId3">
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">www.example</w:t>
            </w:r>
        </w:hyperlink>
    </w:p>
</w:body>
````````````````````````````````


