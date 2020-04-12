---
title: DocxRenderer
author:
version:
date: '2017-12-07'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Formatter

```````````````````````````````` example(Formatter: 1) options(item-indent-1)
Text

1. numbered list one
 - unnumbered list
 unnumbered list cont. same line
 - unnumbered list  
 unnumbered list cont. next line

 numbered list one cont. after unnumbered list
.
Text

1. numbered list one
 - unnumbered list
  unnumbered list cont. same line
 - unnumbered list  
  unnumbered list cont. next line

 numbered list one cont. after unnumbered list

.
Document[0, 180]
  Paragraph[0, 5] isTrailingBlankLine
    Text[0, 4] chars:[0, 4, "Text"]
  BlankLine[5, 6]
  OrderedList[6, 180] isTight delimiter:'.'
    OrderedListItem[6, 180] open:[6, 8, "1."] isTight hadBlankLine
      Paragraph[9, 27]
        Text[9, 26] chars:[9, 26, "numbe … t one"]
      BulletList[28, 133] isTight
        BulletListItem[28, 79] open:[28, 29, "-"] isTight
          Paragraph[30, 79]
            Text[30, 45] chars:[30, 45, "unnum …  list"]
            SoftLineBreak[45, 46]
            Text[47, 78] chars:[47, 78, "unnum …  line"]
        BulletListItem[80, 133] open:[80, 81, "-"] isTight hadBlankLineAfter
          Paragraph[82, 133] isTrailingBlankLine
            Text[82, 97] chars:[82, 97, "unnum …  list"]
            HardLineBreak[97, 100]
            Text[101, 132] chars:[101, 132, "unnum …  line"]
      BlankLine[133, 134]
      Paragraph[135, 180]
        Text[135, 180] chars:[135, 180, "numbe …  list"]
````````````````````````````````


## Append References

Without append just include

```````````````````````````````` example(Append References: 1) options(no-append-references)
Text with footnote[^footnote] abbr, link ref [ref], image ref ![image], []

Enumerated Ref{#enum:this}

See [@enum:this]

.
Text with footnote[^footnote] abbr, link ref [ref], image ref ![image], []

Enumerated Ref{#enum:this}

See [@enum:this]

.
Document[0, 122]
  Paragraph[0, 75] isTrailingBlankLine
    Text[0, 18] chars:[0, 18, "Text  … tnote"]
    Footnote[18, 29] ordinal: 0  textOpen:[18, 20, "[^"] text:[20, 28, "footnote"] textClose:[28, 29, "]"]
      Text[20, 28] chars:[20, 28, "footnote"]
    TextBase[29, 45] chars:[29, 45, " abbr …  ref "]
      Text[29, 30] chars:[29, 30, " "]
      Abbreviation[30, 34] chars:[30, 34, "abbr"]
      Text[34, 45] chars:[34, 45, ", lin …  ref "]
    LinkRef[45, 50] referenceOpen:[45, 46, "["] reference:[46, 49, "ref"] referenceClose:[49, 50, "]"]
      Text[46, 49] chars:[46, 49, "ref"]
    Text[50, 62] chars:[50, 62, ", ima …  ref "]
    ImageRef[62, 70] referenceOpen:[62, 64, "!["] reference:[64, 69, "image"] referenceClose:[69, 70, "]"]
      Text[64, 69] chars:[64, 69, "image"]
    Text[70, 72] chars:[70, 72, ", "]
    LinkRef[72, 74] referenceOpen:[72, 73, "["] reference:[73, 73] referenceClose:[73, 74, "]"]
  BlankLine[75, 76]
  Paragraph[76, 103] isTrailingBlankLine
    TextBase[76, 90] chars:[76, 90, "Enume … d Ref"]
      Text[76, 90] chars:[76, 90, "Enume … d Ref"]
    AttributesNode[90, 102] textOpen:[90, 91, "{"] text:[91, 101, "#enum:this"] textClose:[101, 102, "}"]
      AttributeNode[91, 101] name:[91, 92, "#"] value:[92, 101, "enum:this"] isImplicit isId
  BlankLine[103, 104]
  Paragraph[104, 121] isTrailingBlankLine
    Text[104, 108] chars:[104, 108, "See "]
    EnumeratedReferenceLink[108, 120] textOpen:[108, 110, "[@"] text:[110, 119, "enum:this"] textClose:[119, 120, "]"]
      Text[110, 119] chars:[110, 119, "enum:this"]
  BlankLine[121, 122]
````````````````````````````````


With append just include

```````````````````````````````` example(Append References: 2) options(append-references)
Text with footnote[^footnote] abbr, link ref [ref], image ref ![image], <<<macro1>>>

Enumerated Ref{#enum:this}

See [@enum:this]
 
.
Text with footnote[^footnote] abbr, link ref [ref], image ref ![image], <<<macro1>>>

Enumerated Ref{#enum:this}

See [@enum:this]

[image]: ./included.png
[ref]: ./link.md

*[abbr]: Abbreviation

[@enum]: Enumerated Reference [#]

[^footnote]: Included footnote
    with some extras

>>>macro1
macro text
<<<

.
Document[0, 133]
  Paragraph[0, 85] isTrailingBlankLine
    Text[0, 18] chars:[0, 18, "Text  … tnote"]
    Footnote[18, 29] ordinal: 0  textOpen:[18, 20, "[^"] text:[20, 28, "footnote"] textClose:[28, 29, "]"]
      Text[20, 28] chars:[20, 28, "footnote"]
    TextBase[29, 45] chars:[29, 45, " abbr …  ref "]
      Text[29, 30] chars:[29, 30, " "]
      Abbreviation[30, 34] chars:[30, 34, "abbr"]
      Text[34, 45] chars:[34, 45, ", lin …  ref "]
    LinkRef[45, 50] referenceOpen:[45, 46, "["] reference:[46, 49, "ref"] referenceClose:[49, 50, "]"]
      Text[46, 49] chars:[46, 49, "ref"]
    Text[50, 62] chars:[50, 62, ", ima …  ref "]
    ImageRef[62, 70] referenceOpen:[62, 64, "!["] reference:[64, 69, "image"] referenceClose:[69, 70, "]"]
      Text[64, 69] chars:[64, 69, "image"]
    Text[70, 72] chars:[70, 72, ", "]
    MacroReference[72, 84] textOpen:[72, 75, "<<<"] text:[75, 81, "macro1"] textClose:[81, 84, ">>>"]
  BlankLine[85, 86]
  Paragraph[86, 113] isTrailingBlankLine
    TextBase[86, 100] chars:[86, 100, "Enume … d Ref"]
      Text[86, 100] chars:[86, 100, "Enume … d Ref"]
    AttributesNode[100, 112] textOpen:[100, 101, "{"] text:[101, 111, "#enum:this"] textClose:[111, 112, "}"]
      AttributeNode[101, 111] name:[101, 102, "#"] value:[102, 111, "enum:this"] isImplicit isId
  BlankLine[113, 114]
  Paragraph[114, 131] isTrailingBlankLine
    Text[114, 118] chars:[114, 118, "See "]
    EnumeratedReferenceLink[118, 130] textOpen:[118, 120, "[@"] text:[120, 129, "enum:this"] textClose:[129, 130, "]"]
      Text[120, 129] chars:[120, 129, "enum:this"]
  BlankLine[131, 133]
````````````````````````````````


## Issue

### xxx-01

When no soft line breaks are used, soft line breaks in inline code, quotes, and other inline
elements should be removed also

```````````````````````````````` example(Issue - xxx-01: 1) options(no-soft-breaks)
"broken quoted
string"
.
"broken quoted string"
.
Document[0, 22]
  Paragraph[0, 22]
    TypographicQuotes[0, 22] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[0, 1, "\""] text:[1, 21, "broken quoted\nstring"] textClose:[21, 22, "\""]
      Text[1, 14] chars:[1, 14, "broke … uoted"]
      SoftLineBreak[14, 15]
      Text[15, 21] chars:[15, 21, "string"]
````````````````````````````````


```````````````````````````````` example(Issue - xxx-01: 2) options(no-soft-breaks)
`broken inline code 
string`
.
`broken inline code string`
.
Document[0, 28]
  Paragraph[0, 28]
    Code[0, 28] textOpen:[0, 1, "`"] text:[1, 27, "broke … n inline code \nstring"] textClose:[27, 28, "`"]
      Text[1, 27] chars:[1, 27, "broke … tring"]
````````````````````````````````


### xxx-02

Autolinks wrapped in `<>` during merge

```````````````````````````````` example Issue - xxx-02: 1
test@example.com
.
test@example.com
.
Document[0, 16]
  Paragraph[0, 16]
    TextBase[0, 16] chars:[0, 16, "test@ … e.com"]
      MailLink[0, 16] text:[0, 16, "test@example.com"]
        Text[0, 16] chars:[0, 16, "test@ … e.com"]
````````````````````````````````


### xxx-03

Overlapping transformed range when using SegmentBuilder appending

```````````````````````````````` example Issue - xxx-03: 1
# Changelog

### 0.4
- browser support

### 0.3
- passing more information into {Function} config
- API change: returning {Object} with code, ast, comments and tokens attached instead of just a code {String}
- comments support

### 0.2 
- upgrade to Esprima 1.0.0

### 0.1
- first working version

.
# Changelog

### 0.4

- browser support

### 0.3

- passing more information into {Function} config
- API change: returning {Object} with code, ast, comments and tokens attached instead of just a code {String}
- comments support

### 0.2

- upgrade to Esprima 1.0.0

### 0.1

- first working version

````````````````````````````````


```````````````````````````````` example(Issue - xxx-03: 2) options(use-builder)
# Changelog

### 0.4
- browser support

### 0.3
- passing more information into {Function} config
- API change: returning {Object} with code, ast, comments and tokens attached instead of just a code {String}
- comments support

### 0.2 
- upgrade to Esprima 1.0.0

### 0.1
- first working version

.
# Changelog

### 0.4

- browser support

### 0.3

- passing more information into {Function} config
- API change: returning {Object} with code, ast, comments and tokens attached instead of just a code {String}
- comments support

### 0.2

- upgrade to Esprima 1.0.0

### 0.1

- first working version

````````````````````````````````


### xxx-04

missing apostrophe from heading text

```````````````````````````````` example(Issue - xxx-04: 1) options(spacer, text-only)
## Header 2
### Header 3
### What you didn't know you were missing

[TOC]: # ""

### title"s
- [Header 2](#header-2)
    - [Header 3](#header-3)
.
## Header 2

### Header 3

### What you didn't know you were missing

[TOC]: # ""

- [Header 2](#header-2)
    - [Header 3](#header-3)
    - [What you didn't know you were missing](#what-you-didnt-know-you-were-missing)
.
Document[0, 144]
  Heading[0, 11] textOpen:[0, 2, "##"] text:[3, 11, "Header 2"]
    AnchorLink[3, 11]
      Text[3, 11] chars:[3, 11, "Header 2"]
  Heading[12, 24] textOpen:[12, 15, "###"] text:[16, 24, "Header 3"]
    AnchorLink[16, 24]
      Text[16, 24] chars:[16, 24, "Header 3"]
  Heading[25, 66] textOpen:[25, 28, "###"] text:[29, 66, "What you didn't know you were missing"]
    AnchorLink[29, 66]
      Text[29, 42] chars:[29, 42, "What  …  didn"]
      TypographicSmarts[42, 43] typographic: &rsquo; 
      Text[43, 66] chars:[43, 66, "t kno … ssing"]
  BlankLine[67, 68]
  SimTocBlock[68, 144] openingMarker:[68, 69] tocKeyword:[69, 72] closingMarker:[72, 74] anchorMarker:[75, 76, "#"] openingTitleMarker:[77, 78, "\""] title:[78, 78] closingTitleMarker:[78, 79, "\""]
    SimTocContent[80, 144]
      Heading[81, 92] textOpen:[81, 84, "###"] text:[85, 92, "title\"s"]
        AnchorLink[0, 0]
      BulletList[93, 144] isTight
        BulletListItem[93, 144] open:[93, 94, "-"] isTight
          Paragraph[95, 117]
            Link[95, 116] textOpen:[95, 96, "["] text:[96, 104, "Header 2"] textClose:[104, 105, "]"] linkOpen:[105, 106, "("] url:[106, 115, "#header-2"] pageRef:[106, 106] anchorMarker:[106, 107, "#"] anchorRef:[107, 115, "header-2"] linkClose:[115, 116, ")"]
              Text[96, 104] chars:[96, 104, "Header 2"]
          BulletList[121, 144] isTight
            BulletListItem[121, 144] open:[121, 122, "-"] isTight
              Paragraph[123, 144]
                Link[123, 144] textOpen:[123, 124, "["] text:[124, 132, "Header 3"] textClose:[132, 133, "]"] linkOpen:[133, 134, "("] url:[134, 143, "#header-3"] pageRef:[134, 134] anchorMarker:[134, 135, "#"] anchorRef:[135, 143, "header-3"] linkClose:[143, 144, ")"]
                  Text[124, 132] chars:[124, 132, "Header 3"]
````````````````````````````````


### xxx-05

with aside enabled pipe should be escaped

```````````````````````````````` example(Issue - xxx-05: 1) options(margin[46])
Text wrapping pipe `|` should be escaped when | is wrapped to start of line.
.
Text wrapping pipe `|` should be escaped when
\| is wrapped to start of line.
````````````````````````````````


### xxx-06

Make sure `&nbsp;` is not left in heading reference links

```````````````````````````````` example(Issue - xxx-06: 1) options(margin[46], no-anchor-links)
## 2.0. References [There is a {} reference showing but is not copy-able]
[There is a {} reference showing but is not copy-able]: https://www.example.com/0
.
## 2.0. References [There is a {} reference showing but is not copy-able]

[There is a {} reference showing but is not copy-able]: https://www.example.com/0

.
Document[0, 155]
  Heading[0, 73] textOpen:[0, 2, "##"] text:[3, 73, "2.0. References [There is a {} reference showing but is not copy-able]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 73] referenceOpen:[19, 20, "["] reference:[20, 72, "There is a {} reference showing but is not copy-able"] referenceClose:[72, 73, "]"]
      Text[20, 72] chars:[20, 72, "There … -able"]
  Reference[74, 155] refOpen:[74, 75, "["] ref:[75, 127, "There is a {} reference showing but is not copy-able"] refClose:[127, 129, "]:"] url:[130, 155, "https://www.example.com/0"]
````````````````````````````````

all is fine with anchor links

```````````````````````````````` example(Issue - xxx-06: 2) options(margin[46])
## 2.0. References [There is a {} reference showing but is not copy-able]
[There is a {} reference showing but is not copy-able]: https://www.example.com/0
.
## 2.0. References [There is a {} reference showing but is not copy-able]

[There is a {} reference showing but is not copy-able]: https://www.example.com/0

.
Document[0, 155]
  Heading[0, 73] textOpen:[0, 2, "##"] text:[3, 73, "2.0. References [There is a {} reference showing but is not copy-able]"]
    AnchorLink[3, 73]
      Text[3, 19] chars:[3, 19, "2.0.  … nces "]
      LinkRef[19, 73] referenceOpen:[19, 20, "["] reference:[20, 72, "There is a {} reference showing but is not copy-able"] referenceClose:[72, 73, "]"]
        Text[20, 72] chars:[20, 72, "There … -able"]
  Reference[74, 155] refOpen:[74, 75, "["] ref:[75, 127, "There is a {} reference showing but is not copy-able"] refClose:[127, 129, "]:"] url:[130, 155, "https://www.example.com/0"]
````````````````````````````````


