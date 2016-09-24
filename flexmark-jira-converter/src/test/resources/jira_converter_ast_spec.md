---
title: JiraConverter Spec
author: 
version: 
date: '2016-09-23'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## JiraConverter  

Converts markdown to JIRA formatting

### Headings

Atx headings

```````````````````````````````` example Headings: 1
# Heading 1
## Heading 2
### Heading 3
#### Heading 4
##### Heading 5
###### Heading 6
.
h1. Heading 1
h2. Heading 2
h3. Heading 3
h4. Heading 4
h5. Heading 5
h6. Heading 6
.
Document[0, 87]
  Heading[0, 11] textOpen:[0, 1, "#"] text:[2, 11, "Heading 1"]
    Text[2, 11] chars:[2, 11, "Heading 1"]
  Heading[12, 24] textOpen:[12, 14, "##"] text:[15, 24, "Heading 2"]
    Text[15, 24] chars:[15, 24, "Heading 2"]
  Heading[25, 38] textOpen:[25, 28, "###"] text:[29, 38, "Heading 3"]
    Text[29, 38] chars:[29, 38, "Heading 3"]
  Heading[39, 53] textOpen:[39, 43, "####"] text:[44, 53, "Heading 4"]
    Text[44, 53] chars:[44, 53, "Heading 4"]
  Heading[54, 69] textOpen:[54, 59, "#####"] text:[60, 69, "Heading 5"]
    Text[60, 69] chars:[60, 69, "Heading 5"]
  Heading[70, 86] textOpen:[70, 76, "######"] text:[77, 86, "Heading 6"]
    Text[77, 86] chars:[77, 86, "Heading 6"]
````````````````````````````````


Setext headings

```````````````````````````````` example Headings: 2
Heading 1
===========

Heading 2
-----------
.
h1. Heading 1
h2. Heading 2
.
Document[0, 45]
  Heading[0, 21] text:[0, 9, "Heading 1"] textClose:[10, 21, "==========="]
    Text[0, 9] chars:[0, 9, "Heading 1"]
  Heading[23, 44] text:[23, 32, "Heading 2"] textClose:[33, 44, "-----------"]
    Text[23, 32] chars:[23, 32, "Heading 2"]
````````````````````````````````


### Text formatting

Simple

```````````````````````````````` example Text formatting: 1
Text formatting *emphasis*, **strong emphasis**, `inline code`
.
Text formatting _emphasis_, *strong emphasis*, {{inline code}}

.
Document[0, 63]
  Paragraph[0, 63]
    Text[0, 16] chars:[0, 16, "Text  … ting "]
    Emphasis[16, 26] textOpen:[16, 17, "*"] text:[17, 25, "emphasis"] textClose:[25, 26, "*"]
      Text[17, 25] chars:[17, 25, "emphasis"]
    Text[26, 28] chars:[26, 28, ", "]
    StrongEmphasis[28, 47] textOpen:[28, 30, "**"] text:[30, 45, "strong emphasis"] textClose:[45, 47, "**"]
      Text[30, 45] chars:[30, 45, "stron … hasis"]
    Text[47, 49] chars:[47, 49, ", "]
    Code[49, 62] textOpen:[49, 50, "`"] text:[50, 61, "inlin … e code"] textClose:[61, 62, "`"]
````````````````````````````````


Links

```````````````````````````````` example Text formatting: 2
Text with [link](http://link.com), image ![image](http://link.com/image.png), mail <vladimir@vladsch.com>
.
Text with [link|http://link.com], image !http://link.com/image.png!, mail [vladimir@vladsch.com|mailto:vladimir@vladsch.com]

.
Document[0, 106]
  Paragraph[0, 106]
    Text[0, 10] chars:[0, 10, "Text with "]
    Link[10, 33] textOpen:[10, 11, "["] text:[11, 15, "link"] textClose:[15, 16, "]"] linkOpen:[16, 17, "("] url:[17, 32, "http://link.com"] pageRef:[17, 32, "http://link.com"] linkClose:[32, 33, ")"]
      Text[11, 15] chars:[11, 15, "link"]
    Text[33, 41] chars:[33, 41, ", image "]
    Image[41, 76] textOpen:[41, 43, "!["] text:[43, 48, "image"] textClose:[48, 49, "]"] linkOpen:[49, 50, "("] url:[50, 75, "http://link.com/image.png"] pageRef:[50, 75, "http://link.com/image.png"] linkClose:[75, 76, ")"]
      Text[43, 48] chars:[43, 48, "image"]
    Text[76, 83] chars:[76, 83, ", mail "]
    MailLink[83, 105] textOpen:[83, 84, "<"] text:[84, 104, "vladimir@vladsch.com"] textClose:[104, 105, ">"]
````````````````````````````````


```````````````````````````````` example Text formatting: 3
Text with [link], image ![image], mail <vladimir@vladsch.com>

[link]: http://link.com
[image]: http://link.com/image.png
.
Text with [link|http://link.com], image !http://link.com/image.png!, mail [vladimir@vladsch.com|mailto:vladimir@vladsch.com]

.
Document[0, 122]
  Paragraph[0, 62]
    Text[0, 10] chars:[0, 10, "Text with "]
    LinkRef[10, 16] referenceOpen:[10, 11, "["] reference:[11, 15, "link"] referenceClose:[15, 16, "]"]
      Text[11, 15] chars:[11, 15, "link"]
    Text[16, 24] chars:[16, 24, ", image "]
    ImageRef[24, 32] referenceOpen:[24, 26, "!["] reference:[26, 31, "image"] referenceClose:[31, 32, "]"]
      Text[26, 31] chars:[26, 31, "image"]
    Text[32, 39] chars:[32, 39, ", mail "]
    MailLink[39, 61] textOpen:[39, 40, "<"] text:[40, 60, "vladimir@vladsch.com"] textClose:[60, 61, ">"]
  Reference[63, 86] refOpen:[63, 64, "["] ref:[64, 68, "link"] refClose:[68, 70, "]:"] url:[71, 86, "http://link.com"]
  Reference[87, 121] refOpen:[87, 88, "["] ref:[88, 93, "image"] refClose:[93, 95, "]:"] url:[96, 121, "http://link.com/image.png"]
````````````````````````````````


### Block quotes

Single line block quote

```````````````````````````````` example Block quotes: 1
> simple block quote 
.
{quote}
simple block quote

{quote}
.
Document[0, 22]
  BlockQuote[0, 22] marker:[0, 1, ">"]
    Paragraph[2, 22]
      Text[2, 20] chars:[2, 20, "simpl … quote"]
````````````````````````````````


Simple

```````````````````````````````` example Block quotes: 2
> simple block quote 
> paragraph.
.
{quote}
simple block quote
paragraph.

{quote}
.
Document[0, 35]
  BlockQuote[0, 35] marker:[0, 1, ">"]
    Paragraph[2, 35]
      Text[2, 20] chars:[2, 20, "simpl … quote"]
      SoftLineBreak[21, 22]
      Text[24, 34] chars:[24, 34, "paragraph."]
````````````````````````````````


Multi-paragraph

```````````````````````````````` example Block quotes: 3
> simple block quote 
> paragraph.
>
> another block quote 
> paragraph.
.
{quote}
simple block quote
paragraph.

another block quote
paragraph.

{quote}
.
Document[0, 73]
  BlockQuote[0, 73] marker:[0, 1, ">"]
    Paragraph[2, 35]
      Text[2, 20] chars:[2, 20, "simpl … quote"]
      SoftLineBreak[21, 22]
      Text[24, 34] chars:[24, 34, "paragraph."]
    Paragraph[39, 73]
      Text[39, 58] chars:[39, 58, "anoth … quote"]
      SoftLineBreak[59, 60]
      Text[62, 72] chars:[62, 72, "paragraph."]
````````````````````````````````


Thematic break

```````````````````````````````` example Block quotes: 4
Some text

---
More Text
.
Some text

----
More Text

.
Document[0, 25]
  Paragraph[0, 10]
    Text[0, 9] chars:[0, 9, "Some text"]
  ThematicBreak[11, 14]
  Paragraph[15, 25]
    Text[15, 24] chars:[15, 24, "More Text"]
````````````````````````````````


### Lists

Bullet lists

```````````````````````````````` example Lists: 1
- item 1
- item 2
with lazy continuation
.
* item 1
* item 2
with lazy continuation
.
Document[0, 41]
  BulletList[0, 41] isTight
    BulletListItem[0, 9] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 41] open:[9, 10, "-"] isTight
      Paragraph[11, 41]
        Text[11, 17] chars:[11, 17, "item 2"]
        SoftLineBreak[17, 18]
        Text[18, 40] chars:[18, 40, "with  … ation"]
````````````````````````````````


Ordered lists

```````````````````````````````` example Lists: 2
1. item 1
2. item 2
with lazy continuation
.
# item 1
# item 2
with lazy continuation
.
Document[0, 43]
  OrderedList[0, 43] isTight delimiter:'.'
    OrderedListItem[0, 10] open:[0, 2, "1."] isTight
      Paragraph[3, 10]
        Text[3, 9] chars:[3, 9, "item 1"]
    OrderedListItem[10, 43] open:[10, 12, "2."] isTight
      Paragraph[13, 43]
        Text[13, 19] chars:[13, 19, "item 2"]
        SoftLineBreak[19, 20]
        Text[20, 42] chars:[20, 42, "with  … ation"]
````````````````````````````````


Nested lists

```````````````````````````````` example Lists: 3
- item 1
lazy continuation
  - sub-item 1
    - sub-sub-item 1
  - sub-item 2
    - sub-sub-item 1
- item 2
  - sub-item 1
.
* item 1
lazy continuation
** sub-item 1
*** sub-sub-item 1
** sub-item 2
*** sub-sub-item 1
* item 2
** sub-item 1
.
Document[0, 123]
  BulletList[0, 123] isTight
    BulletListItem[0, 99] open:[0, 1, "-"] isTight
      Paragraph[2, 27]
        Text[2, 8] chars:[2, 8, "item 1"]
        SoftLineBreak[8, 9]
        Text[9, 26] chars:[9, 26, "lazy  … ation"]
      BulletList[29, 99] isTight
        BulletListItem[29, 63] open:[29, 30, "-"] isTight
          Paragraph[31, 42]
            Text[31, 41] chars:[31, 41, "sub-item 1"]
          BulletList[46, 63] isTight
            BulletListItem[46, 63] open:[46, 47, "-"] isTight
              Paragraph[48, 63]
                Text[48, 62] chars:[48, 62, "sub-s … tem 1"]
        BulletListItem[65, 99] open:[65, 66, "-"] isTight
          Paragraph[67, 78]
            Text[67, 77] chars:[67, 77, "sub-item 2"]
          BulletList[82, 99] isTight
            BulletListItem[82, 99] open:[82, 83, "-"] isTight
              Paragraph[84, 99]
                Text[84, 98] chars:[84, 98, "sub-s … tem 1"]
    BulletListItem[99, 123] open:[99, 100, "-"] isTight
      Paragraph[101, 108]
        Text[101, 107] chars:[101, 107, "item 2"]
      BulletList[110, 123] isTight
        BulletListItem[110, 123] open:[110, 111, "-"] isTight
          Paragraph[112, 123]
            Text[112, 122] chars:[112, 122, "sub-item 1"]
````````````````````````````````


Nested lists

```````````````````````````````` example Lists: 4
1. item 1
lazy continuation
   1. sub-item 1
      1. sub-sub-item 1
   1. sub-item 2
      1. sub-sub-item 1
1. item 2
   1. sub-item 1
.
# item 1
lazy continuation
## sub-item 1
### sub-sub-item 1
## sub-item 2
### sub-sub-item 1
# item 2
## sub-item 1
.
Document[0, 137]
  OrderedList[0, 137] isTight delimiter:'.'
    OrderedListItem[0, 110] open:[0, 2, "1."] isTight
      Paragraph[3, 28]
        Text[3, 9] chars:[3, 9, "item 1"]
        SoftLineBreak[9, 10]
        Text[10, 27] chars:[10, 27, "lazy  … ation"]
      OrderedList[31, 110] isTight delimiter:'.'
        OrderedListItem[31, 69] open:[31, 33, "1."] isTight
          Paragraph[34, 45]
            Text[34, 44] chars:[34, 44, "sub-item 1"]
          OrderedList[51, 69] isTight delimiter:'.'
            OrderedListItem[51, 69] open:[51, 53, "1."] isTight
              Paragraph[54, 69]
                Text[54, 68] chars:[54, 68, "sub-s … tem 1"]
        OrderedListItem[72, 110] open:[72, 74, "1."] isTight
          Paragraph[75, 86]
            Text[75, 85] chars:[75, 85, "sub-item 2"]
          OrderedList[92, 110] isTight delimiter:'.'
            OrderedListItem[92, 110] open:[92, 94, "1."] isTight
              Paragraph[95, 110]
                Text[95, 109] chars:[95, 109, "sub-s … tem 1"]
    OrderedListItem[110, 137] open:[110, 112, "1."] isTight
      Paragraph[113, 120]
        Text[113, 119] chars:[113, 119, "item 2"]
      OrderedList[123, 137] isTight delimiter:'.'
        OrderedListItem[123, 137] open:[123, 125, "1."] isTight
          Paragraph[126, 137]
            Text[126, 136] chars:[126, 136, "sub-item 1"]
````````````````````````````````


