---
title: JiraConverter Spec
author:
version:
date: '2016-09-23'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

# JiraConverter

Converts markdown to JIRA formatting

## Headings

Atx headings

```````````````````````````````` example Headings: 1
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
Document[0, 86]
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

```````````````````````````````` example Headings: 2
Heading 1
===========

Heading 2
-----------
.
h1. Heading 1

h2. Heading 2

.
Document[0, 44]
  Heading[0, 21] text:[0, 9, "Heading 1"] textClose:[10, 21, "==========="]
    Text[0, 9] chars:[0, 9, "Heading 1"]
  Heading[23, 44] text:[23, 32, "Heading 2"] textClose:[33, 44, "-----------"]
    Text[23, 32] chars:[23, 32, "Heading 2"]
````````````````````````````````


## Text formatting

Simple

```````````````````````````````` example Text formatting: 1
Text formatting *emphasis*, **strong emphasis**, `inline code`
.
Text formatting _emphasis_, *strong emphasis*, {{inline code}}

.
Document[0, 62]
  Paragraph[0, 62]
    Text[0, 16] chars:[0, 16, "Text  … ting "]
    Emphasis[16, 26] textOpen:[16, 17, "*"] text:[17, 25, "emphasis"] textClose:[25, 26, "*"]
      Text[17, 25] chars:[17, 25, "emphasis"]
    Text[26, 28] chars:[26, 28, ", "]
    StrongEmphasis[28, 47] textOpen:[28, 30, "**"] text:[30, 45, "strong emphasis"] textClose:[45, 47, "**"]
      Text[30, 45] chars:[30, 45, "stron … hasis"]
    Text[47, 49] chars:[47, 49, ", "]
    Code[49, 62] textOpen:[49, 50, "`"] text:[50, 61, "inlin … e code"] textClose:[61, 62, "`"]
      Text[50, 61] chars:[50, 61, "inlin …  code"]
````````````````````````````````


Links

```````````````````````````````` example Text formatting: 2
Text with [link](http://link.com), image ![image](http://link.com/image.png), mail <vladimir@vladsch.com>
.
Text with [link|http://link.com], image !http://link.com/image.png!, mail [vladimir@vladsch.com|mailto:vladimir@vladsch.com]

.
Document[0, 105]
  Paragraph[0, 105]
    Text[0, 10] chars:[0, 10, "Text with "]
    Link[10, 33] textOpen:[10, 11, "["] text:[11, 15, "link"] textClose:[15, 16, "]"] linkOpen:[16, 17, "("] url:[17, 32, "http://link.com"] pageRef:[17, 32, "http://link.com"] linkClose:[32, 33, ")"]
      Text[11, 15] chars:[11, 15, "link"]
    Text[33, 41] chars:[33, 41, ", image "]
    Image[41, 76] textOpen:[41, 43, "!["] text:[43, 48, "image"] textClose:[48, 49, "]"] linkOpen:[49, 50, "("] url:[50, 75, "http://link.com/image.png"] pageRef:[50, 75, "http://link.com/image.png"] linkClose:[75, 76, ")"]
      Text[43, 48] chars:[43, 48, "image"]
    Text[76, 83] chars:[76, 83, ", mail "]
    MailLink[83, 105] textOpen:[83, 84, "<"] text:[84, 104, "vladimir@vladsch.com"] textClose:[104, 105, ">"]
````````````````````````````````


```````````````````````````````` example Text formatting: 3
Text with [link], image ![image], mail <vladimir@vladsch.com>

[link]: http://link.com
[image]: http://link.com/image.png
.
Text with [link|http://link.com], image !http://link.com/image.png!, mail [vladimir@vladsch.com|mailto:vladimir@vladsch.com]

.
Document[0, 121]
  Paragraph[0, 62] isTrailingBlankLine
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


```````````````````````````````` example Text formatting: 4
[inline link with title](http://google.com "Google")
.
[inline link with title|http://google.com|Google]

.
Document[0, 52]
  Paragraph[0, 52]
    Link[0, 52] textOpen:[0, 1, "["] text:[1, 23, "inline link with title"] textClose:[23, 24, "]"] linkOpen:[24, 25, "("] url:[25, 42, "http://google.com"] pageRef:[25, 42, "http://google.com"] titleOpen:[43, 44, "\""] title:[44, 50, "Google"] titleClose:[50, 51, "\""] linkClose:[51, 52, ")"]
      Text[1, 23] chars:[1, 23, "inlin … title"]
````````````````````````````````


## Block quotes

Single line block quote

```````````````````````````````` example Block quotes: 1
> simple block quote
.
{quote}
simple block quote
{quote}

.
Document[0, 20]
  BlockQuote[0, 20] marker:[0, 1, ">"]
    Paragraph[2, 20]
      Text[2, 20] chars:[2, 20, "simpl … quote"]
````````````````````````````````


Simple

```````````````````````````````` example Block quotes: 2
> simple block quote
> paragraph.
.
{quote}
simple block quote paragraph.
{quote}

.
Document[0, 33]
  BlockQuote[0, 33] marker:[0, 1, ">"]
    Paragraph[2, 33]
      Text[2, 20] chars:[2, 20, "simpl … quote"]
      SoftLineBreak[20, 21]
      Text[23, 33] chars:[23, 33, "paragraph."]
````````````````````````````````


Multi-paragraph

```````````````````````````````` example Block quotes: 3
> simple block quote
> paragraph.
>
> another block quote
> paragraph.
.
{quote}
simple block quote paragraph.

another block quote paragraph.
{quote}

.
Document[0, 70]
  BlockQuote[0, 70] marker:[0, 1, ">"]
    Paragraph[2, 34] isTrailingBlankLine
      Text[2, 20] chars:[2, 20, "simpl … quote"]
      SoftLineBreak[20, 21]
      Text[23, 33] chars:[23, 33, "paragraph."]
    Paragraph[38, 70]
      Text[38, 57] chars:[38, 57, "anoth … quote"]
      SoftLineBreak[57, 58]
      Text[60, 70] chars:[60, 70, "paragraph."]
````````````````````````````````


Thematic break

```````````````````````````````` example Block quotes: 4
Some text

---
More Text
.
Some text

----

More Text

.
Document[0, 24]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 9] chars:[0, 9, "Some text"]
  ThematicBreak[11, 14]
  Paragraph[15, 24]
    Text[15, 24] chars:[15, 24, "More Text"]
````````````````````````````````


## Lists

Bullet lists

```````````````````````````````` example Lists: 1
- item 1
- item 2
with lazy continuation
.
* item 1
* item 2 with lazy continuation

.
Document[0, 40]
  BulletList[0, 40] isTight
    BulletListItem[0, 9] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 40] open:[9, 10, "-"] isTight
      Paragraph[11, 40]
        Text[11, 17] chars:[11, 17, "item 2"]
        SoftLineBreak[17, 18]
        Text[18, 40] chars:[18, 40, "with  … ation"]
````````````````````````````````


Ordered lists

```````````````````````````````` example Lists: 2
1. item 1
2. item 2
with lazy continuation
.
# item 1
# item 2 with lazy continuation

.
Document[0, 42]
  OrderedList[0, 42] isTight delimiter:'.'
    OrderedListItem[0, 10] open:[0, 2, "1."] isTight
      Paragraph[3, 10]
        Text[3, 9] chars:[3, 9, "item 1"]
    OrderedListItem[10, 42] open:[10, 12, "2."] isTight
      Paragraph[13, 42]
        Text[13, 19] chars:[13, 19, "item 2"]
        SoftLineBreak[19, 20]
        Text[20, 42] chars:[20, 42, "with  … ation"]
````````````````````````````````


Nested lists

```````````````````````````````` example Lists: 3
- item 1
lazy continuation
  - sub-item 1
    - sub-sub-item 1
  - sub-item 2
    - sub-sub-item 1
- item 2
  - sub-item 1
.
* item 1 lazy continuation
** sub-item 1
*** sub-sub-item 1
** sub-item 2
*** sub-sub-item 1
* item 2
** sub-item 1

.
Document[0, 122]
  BulletList[0, 122] isTight
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
    BulletListItem[99, 122] open:[99, 100, "-"] isTight
      Paragraph[101, 108]
        Text[101, 107] chars:[101, 107, "item 2"]
      BulletList[110, 122] isTight
        BulletListItem[110, 122] open:[110, 111, "-"] isTight
          Paragraph[112, 122]
            Text[112, 122] chars:[112, 122, "sub-item 1"]
````````````````````````````````


Nested lists

```````````````````````````````` example Lists: 4
1. item 1
lazy continuation
   1. sub-item 1
      1. sub-sub-item 1
   1. sub-item 2
      1. sub-sub-item 1
1. item 2
   1. sub-item 1
.
# item 1 lazy continuation
## sub-item 1
### sub-sub-item 1
## sub-item 2
### sub-sub-item 1
# item 2
## sub-item 1

.
Document[0, 136]
  OrderedList[0, 136] isTight delimiter:'.'
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
    OrderedListItem[110, 136] open:[110, 112, "1."] isTight
      Paragraph[113, 120]
        Text[113, 119] chars:[113, 119, "item 2"]
      OrderedList[123, 136] isTight delimiter:'.'
        OrderedListItem[123, 136] open:[123, 125, "1."] isTight
          Paragraph[126, 136]
            Text[126, 136] chars:[126, 136, "sub-item 1"]
````````````````````````````````


Loose Nested lists

```````````````````````````````` example(Lists: 5) options(list-no-auto-loose)
1. item 1
lazy continuation

   1. sub-item 1
      1. sub-sub-item 1
   1. sub-item 2
      1. sub-sub-item 1

1. item 2

   1. sub-item 1

.
# item 1 lazy continuation

## sub-item 1
### sub-sub-item 1
## sub-item 2
### sub-sub-item 1

# item 2
## sub-item 1

.
Document[0, 141]
  OrderedList[0, 140] isTight delimiter:'.'
    OrderedListItem[0, 111] open:[0, 2, "1."] isLoose hadBlankLineAfter
      Paragraph[3, 28] isTrailingBlankLine
        Text[3, 9] chars:[3, 9, "item 1"]
        SoftLineBreak[9, 10]
        Text[10, 27] chars:[10, 27, "lazy  … ation"]
      OrderedList[32, 111] isTight delimiter:'.'
        OrderedListItem[32, 70] open:[32, 34, "1."] isTight
          Paragraph[35, 46]
            Text[35, 45] chars:[35, 45, "sub-item 1"]
          OrderedList[52, 70] isTight delimiter:'.'
            OrderedListItem[52, 70] open:[52, 54, "1."] isTight
              Paragraph[55, 70]
                Text[55, 69] chars:[55, 69, "sub-s … tem 1"]
        OrderedListItem[73, 111] open:[73, 75, "1."] isTight
          Paragraph[76, 87]
            Text[76, 86] chars:[76, 86, "sub-item 2"]
          OrderedList[93, 111] isTight delimiter:'.'
            OrderedListItem[93, 111] open:[93, 95, "1."] isTight hadBlankLineAfter
              Paragraph[96, 111] isTrailingBlankLine
                Text[96, 110] chars:[96, 110, "sub-s … tem 1"]
    OrderedListItem[112, 140] open:[112, 114, "1."] isTight hadBlankLineAfter
      Paragraph[115, 122] isTrailingBlankLine
        Text[115, 121] chars:[115, 121, "item 2"]
      OrderedList[126, 140] isTight delimiter:'.'
        OrderedListItem[126, 140] open:[126, 128, "1."] isTight hadBlankLineAfter
          Paragraph[129, 140] isTrailingBlankLine
            Text[129, 139] chars:[129, 139, "sub-item 1"]
````````````````````````````````

Mixed nested lists

```````````````````````````````` example Lists: 6
1. item 1
   * sub-item 1a
      1. sub-sub-item 1a.1
   * sub-item 1b
      1. sub-sub-item 1b.1
2. item 2
   1. sub-item 2.1

* list 2, item a
* list 2, item b
* list 2, item c
  1. item c.1
  2. item c.2
.
# item 1
#* sub-item 1a
#*# sub-sub-item 1a.1
#* sub-item 1b
#*# sub-sub-item 1b.1
# item 2
## sub-item 2.1

* list 2, item a
* list 2, item b
* list 2, item c
*# item c.1
*# item c.2

.
Document[0, 206]
  OrderedList[0, 127] isTight delimiter:'.'
    OrderedListItem[0, 98] open:[0, 2, "1."] isTight
      Paragraph[3, 10]
        Text[3, 9] chars:[3, 9, "item 1"]
      BulletList[13, 98] isTight
        BulletListItem[13, 54] open:[13, 14, "*"] isTight
          Paragraph[15, 27]
            Text[15, 26] chars:[15, 26, "sub-i … em 1a"]
          OrderedList[33, 54] isTight delimiter:'.'
            OrderedListItem[33, 54] open:[33, 35, "1."] isTight
              Paragraph[36, 54]
                Text[36, 53] chars:[36, 53, "sub-s …  1a.1"]
        BulletListItem[57, 98] open:[57, 58, "*"] isTight
          Paragraph[59, 71]
            Text[59, 70] chars:[59, 70, "sub-i … em 1b"]
          OrderedList[77, 98] isTight delimiter:'.'
            OrderedListItem[77, 98] open:[77, 79, "1."] isTight
              Paragraph[80, 98]
                Text[80, 97] chars:[80, 97, "sub-s …  1b.1"]
    OrderedListItem[98, 127] open:[98, 100, "2."] isTight
      Paragraph[101, 108]
        Text[101, 107] chars:[101, 107, "item 2"]
      OrderedList[111, 127] isTight delimiter:'.'
        OrderedListItem[111, 127] open:[111, 113, "1."] isTight hadBlankLineAfter
          Paragraph[114, 127] isTrailingBlankLine
            Text[114, 126] chars:[114, 126, "sub-i … m 2.1"]
  BulletList[128, 206] isTight
    BulletListItem[128, 145] open:[128, 129, "*"] isTight
      Paragraph[130, 145]
        Text[130, 144] chars:[130, 144, "list  … tem a"]
    BulletListItem[145, 162] open:[145, 146, "*"] isTight
      Paragraph[147, 162]
        Text[147, 161] chars:[147, 161, "list  … tem b"]
    BulletListItem[162, 206] open:[162, 163, "*"] isTight
      Paragraph[164, 179]
        Text[164, 178] chars:[164, 178, "list  … tem c"]
      OrderedList[181, 206] isTight delimiter:'.'
        OrderedListItem[181, 193] open:[181, 183, "1."] isTight
          Paragraph[184, 193]
            Text[184, 192] chars:[184, 192, "item c.1"]
        OrderedListItem[195, 206] open:[195, 197, "2."] isTight
          Paragraph[198, 206]
            Text[198, 206] chars:[198, 206, "item c.2"]
````````````````````````````````

## Fenced code

plain

```````````````````````````````` example Fenced code: 1
```
fenced code
```

.
{code}
fenced code
{code}

.
Document[0, 21]
  FencedCodeBlock[0, 19] open:[0, 3, "```"] content:[4, 16] lines[1] close:[16, 19, "```"]
    Text[4, 16] chars:[4, 16, "fence … code\n"]
````````````````````````````````


with language

```````````````````````````````` example Fenced code: 2
```kotlin
fenced code
```

.
{code:lang=kotlin}
fenced code
{code}

.
Document[0, 27]
  FencedCodeBlock[0, 25] open:[0, 3, "```"] info:[3, 9, "kotlin"] content:[10, 22] lines[1] close:[22, 25, "```"]
    Text[10, 22] chars:[10, 22, "fence … code\n"]
````````````````````````````````


## Paragraph Spacing

multiple paragraphs

```````````````````````````````` example Paragraph Spacing: 1
paragraph 1
with lazy continuation

paragraph 2
with lazy continuation

* item 1
with lazy continuation
* loose item 2
with lazy continuation

* item 3

    item 3 paragraph
* item 4

paragraph 4

.
paragraph 1 with lazy continuation

paragraph 2 with lazy continuation

* item 1 with lazy continuation

* loose item 2 with lazy continuation

* item 3

item 3 paragraph

* item 4

paragraph 4

.
Document[0, 197]
  Paragraph[0, 35] isTrailingBlankLine
    Text[0, 11] chars:[0, 11, "parag … aph 1"]
    SoftLineBreak[11, 12]
    Text[12, 34] chars:[12, 34, "with  … ation"]
  Paragraph[36, 71] isTrailingBlankLine
    Text[36, 47] chars:[36, 47, "parag … aph 2"]
    SoftLineBreak[47, 48]
    Text[48, 70] chars:[48, 70, "with  … ation"]
  BulletList[72, 183] isLoose
    BulletListItem[72, 104] open:[72, 73, "*"] isLoose
      Paragraph[74, 104]
        Text[74, 80] chars:[74, 80, "item 1"]
        SoftLineBreak[80, 81]
        Text[81, 103] chars:[81, 103, "with  … ation"]
    BulletListItem[104, 142] open:[104, 105, "*"] isLoose hadBlankLineAfter
      Paragraph[106, 142] isTrailingBlankLine
        Text[106, 118] chars:[106, 118, "loose … tem 2"]
        SoftLineBreak[118, 119]
        Text[119, 141] chars:[119, 141, "with  … ation"]
    BulletListItem[143, 174] open:[143, 144, "*"] isLoose hadBlankLineAfter
      Paragraph[145, 152] isTrailingBlankLine
        Text[145, 151] chars:[145, 151, "item 3"]
      Paragraph[157, 174]
        Text[157, 173] chars:[157, 173, "item  … graph"]
    BulletListItem[174, 183] open:[174, 175, "*"] isLoose hadBlankLineAfter
      Paragraph[176, 183] isTrailingBlankLine
        Text[176, 182] chars:[176, 182, "item 4"]
  Paragraph[184, 196] isTrailingBlankLine
    Text[184, 195] chars:[184, 195, "parag … aph 4"]
````````````````````````````````


multiple paragraphs

```````````````````````````````` example(Paragraph Spacing: 2) options(list-no-auto-loose)
paragraph 1
with lazy continuation

paragraph 2
with lazy continuation

* item 1
with lazy continuation
* loose item 2
with lazy continuation

* item 3

    item 3 paragraph
* item 4

paragraph 4

.
paragraph 1 with lazy continuation

paragraph 2 with lazy continuation

* item 1 with lazy continuation
* loose item 2 with lazy continuation

* item 3
item 3 paragraph

* item 4

paragraph 4

.
Document[0, 197]
  Paragraph[0, 35] isTrailingBlankLine
    Text[0, 11] chars:[0, 11, "parag … aph 1"]
    SoftLineBreak[11, 12]
    Text[12, 34] chars:[12, 34, "with  … ation"]
  Paragraph[36, 71] isTrailingBlankLine
    Text[36, 47] chars:[36, 47, "parag … aph 2"]
    SoftLineBreak[47, 48]
    Text[48, 70] chars:[48, 70, "with  … ation"]
  BulletList[72, 183] isTight
    BulletListItem[72, 104] open:[72, 73, "*"] isTight
      Paragraph[74, 104]
        Text[74, 80] chars:[74, 80, "item 1"]
        SoftLineBreak[80, 81]
        Text[81, 103] chars:[81, 103, "with  … ation"]
    BulletListItem[104, 142] open:[104, 105, "*"] isLoose hadBlankLineAfter
      Paragraph[106, 142] isTrailingBlankLine
        Text[106, 118] chars:[106, 118, "loose … tem 2"]
        SoftLineBreak[118, 119]
        Text[119, 141] chars:[119, 141, "with  … ation"]
    BulletListItem[143, 174] open:[143, 144, "*"] isTight hadBlankLineAfter
      Paragraph[145, 152] isTrailingBlankLine
        Text[145, 151] chars:[145, 151, "item 3"]
      Paragraph[157, 174]
        Text[157, 173] chars:[157, 173, "item  … graph"]
    BulletListItem[174, 183] open:[174, 175, "*"] isTight hadBlankLineAfter
      Paragraph[176, 183] isTrailingBlankLine
        Text[176, 182] chars:[176, 182, "item 4"]
  Paragraph[184, 196] isTrailingBlankLine
    Text[184, 195] chars:[184, 195, "parag … aph 4"]
````````````````````````````````


```````````````````````````````` example Paragraph Spacing: 3
Heading One
===========

| day         | time  | spent   |
|:------------|:------|:--------|
| nov. 2. tue | 10:00 | 4h 40m  |
| nov. 3. thu | 11:00 | 4h      |
| nov. 7. mon | 10:20 | 4h 20m  |
| total:      |       | **13h** |

Some paragraph.

Heading Two
-----------

More ~~lines~~ paragraphs.

> A wise man once said…

Who _said_ that?

And On And On
=============

Well now.
.
h1. Heading One

||day||time||spent||
|nov. 2. tue|10:00|4h 40m|
|nov. 3. thu|11:00|4h|
|nov. 7. mon|10:20|4h 20m|
|total:| |*13h*|

Some paragraph.

h2. Heading Two

More -lines- paragraphs.

{quote}
A wise man once said…
{quote}

Who _said_ that?

h1. And On And On

Well now.

.
Document[0, 381]
  Heading[0, 23] text:[0, 11, "Heading One"] textClose:[12, 23, "==========="]
    Text[0, 11] chars:[0, 11, "Headi … g One"]
  TableBlock[25, 229]
    TableHead[25, 58]
      TableRow[25, 58] rowNumber=1
        TableCell[25, 40] LEFT header textOpen:[25, 26, "|"] text:[27, 30, "day"] textClose:[39, 40, "|"]
          Text[27, 30] chars:[27, 30, "day"]
        TableCell[40, 48] LEFT header text:[41, 45, "time"] textClose:[47, 48, "|"]
          Text[41, 45] chars:[41, 45, "time"]
        TableCell[48, 58] LEFT header text:[49, 54, "spent"] textClose:[57, 58, "|"]
          Text[49, 54] chars:[49, 54, "spent"]
    TableSeparator[59, 92]
      TableRow[59, 92]
        TableCell[59, 74] LEFT textOpen:[59, 60, "|"] text:[60, 73, ":------------"] textClose:[73, 74, "|"]
          Text[60, 73] chars:[60, 73, ":---- … -----"]
        TableCell[74, 82] LEFT text:[74, 81, ":------"] textClose:[81, 82, "|"]
          Text[74, 81] chars:[74, 81, ":------"]
        TableCell[82, 92] LEFT text:[82, 91, ":--------"] textClose:[91, 92, "|"]
          Text[82, 91] chars:[82, 91, ":--------"]
    TableBody[93, 228]
      TableRow[93, 126] rowNumber=1
        TableCell[93, 108] LEFT textOpen:[93, 94, "|"] text:[95, 106, "nov. 2. tue"] textClose:[107, 108, "|"]
          Text[95, 106] chars:[95, 106, "nov.  … . tue"]
        TableCell[108, 116] LEFT text:[109, 114, "10:00"] textClose:[115, 116, "|"]
          Text[109, 114] chars:[109, 114, "10:00"]
        TableCell[116, 126] LEFT text:[117, 123, "4h 40m"] textClose:[125, 126, "|"]
          Text[117, 123] chars:[117, 123, "4h 40m"]
      TableRow[127, 160] rowNumber=2
        TableCell[127, 142] LEFT textOpen:[127, 128, "|"] text:[129, 140, "nov. 3. thu"] textClose:[141, 142, "|"]
          Text[129, 140] chars:[129, 140, "nov.  … . thu"]
        TableCell[142, 150] LEFT text:[143, 148, "11:00"] textClose:[149, 150, "|"]
          Text[143, 148] chars:[143, 148, "11:00"]
        TableCell[150, 160] LEFT text:[151, 153, "4h"] textClose:[159, 160, "|"]
          Text[151, 153] chars:[151, 153, "4h"]
      TableRow[161, 194] rowNumber=3
        TableCell[161, 176] LEFT textOpen:[161, 162, "|"] text:[163, 174, "nov. 7. mon"] textClose:[175, 176, "|"]
          Text[163, 174] chars:[163, 174, "nov.  … . mon"]
        TableCell[176, 184] LEFT text:[177, 182, "10:20"] textClose:[183, 184, "|"]
          Text[177, 182] chars:[177, 182, "10:20"]
        TableCell[184, 194] LEFT text:[185, 191, "4h 20m"] textClose:[193, 194, "|"]
          Text[185, 191] chars:[185, 191, "4h 20m"]
      TableRow[195, 228] rowNumber=4
        TableCell[195, 210] LEFT textOpen:[195, 196, "|"] text:[197, 203, "total:"] textClose:[209, 210, "|"]
          Text[197, 203] chars:[197, 203, "total:"]
        TableCell[210, 218] LEFT text:[210, 211, " "] textClose:[217, 218, "|"]
          Text[210, 211] chars:[210, 211, " "]
        TableCell[218, 228] LEFT text:[219, 226, "**13h**"] textClose:[227, 228, "|"]
          StrongEmphasis[219, 226] textOpen:[219, 221, "**"] text:[221, 224, "13h"] textClose:[224, 226, "**"]
            Text[221, 224] chars:[221, 224, "13h"]
          Text[226, 226]
  Paragraph[230, 246] isTrailingBlankLine
    Text[230, 245] chars:[230, 245, "Some  … raph."]
  Heading[247, 270] text:[247, 258, "Heading Two"] textClose:[259, 270, "-----------"]
    Text[247, 258] chars:[247, 258, "Headi … g Two"]
  Paragraph[272, 299] isTrailingBlankLine
    Text[272, 277] chars:[272, 277, "More "]
    Strikethrough[277, 286] textOpen:[277, 279, "~~"] text:[279, 284, "lines"] textClose:[284, 286, "~~"]
      Text[279, 284] chars:[279, 284, "lines"]
    Text[286, 298] chars:[286, 298, " para … aphs."]
  BlockQuote[300, 324] marker:[300, 301, ">"]
    Paragraph[302, 324]
      Text[302, 323] chars:[302, 323, "A wis … said…"]
  Paragraph[325, 342] isTrailingBlankLine
    Text[325, 329] chars:[325, 329, "Who "]
    Emphasis[329, 335] textOpen:[329, 330, "_"] text:[330, 334, "said"] textClose:[334, 335, "_"]
      Text[330, 334] chars:[330, 334, "said"]
    Text[335, 341] chars:[335, 341, " that?"]
  Heading[343, 370] text:[343, 356, "And On And On"] textClose:[357, 370, "============="]
    Text[343, 356] chars:[343, 356, "And O … nd On"]
  Paragraph[372, 381]
    Text[372, 381] chars:[372, 381, "Well now."]
````````````````````````````````


## Tables Extension

Converts pipe separated tables to JIRA tables

```````````````````````````````` example Tables Extension: 1
Abc|Def
---|---
.
||Abc||Def||

.
Document[0, 15]
  TableBlock[0, 15]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] text:[8, 11, "---"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[15, 15]
````````````````````````````````


```````````````````````````````` example Tables Extension: 2
|Abc
|---
|1
.
||Abc||
|1|

.
Document[0, 12]
  TableBlock[0, 12]
    TableHead[0, 4]
      TableRow[0, 4] rowNumber=1
        TableCell[0, 4] header textOpen:[0, 1, "|"] text:[1, 4, "Abc"]
          Text[1, 4] chars:[1, 4, "Abc"]
    TableSeparator[5, 9]
      TableRow[5, 9]
        TableCell[5, 9] textOpen:[5, 6, "|"] text:[6, 9, "---"]
          Text[6, 9] chars:[6, 9, "---"]
    TableBody[10, 12]
      TableRow[10, 12] rowNumber=1
        TableCell[10, 12] textOpen:[10, 11, "|"] text:[11, 12, "1"]
          Text[11, 12] chars:[11, 12, "1"]
````````````````````````````````


```````````````````````````````` example Tables Extension: 3
Abc|Def
---|---
1|2
.
||Abc||Def||
|1|2|

.
Document[0, 19]
  TableBlock[0, 19]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] text:[8, 11, "---"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 19]
      TableRow[16, 19] rowNumber=1
        TableCell[16, 18] text:[16, 17, "1"] textClose:[17, 18, "|"]
          Text[16, 17] chars:[16, 17, "1"]
        TableCell[18, 19] text:[18, 19, "2"]
          Text[18, 19] chars:[18, 19, "2"]
````````````````````````````````


```````````````````````````````` example Tables Extension: 4
Abc|Def|Ghi
---|---
1|2|3
.
||Abc||Def||Ghi||
|1|2|3|

.
Document[0, 25]
  TableBlock[0, 25]
    TableHead[0, 11]
      TableRow[0, 11] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 8] header text:[4, 7, "Def"] textClose:[7, 8, "|"]
          Text[4, 7] chars:[4, 7, "Def"]
        TableCell[8, 11] header text:[8, 11, "Ghi"]
          Text[8, 11] chars:[8, 11, "Ghi"]
    TableSeparator[12, 19]
      TableRow[12, 19]
        TableCell[12, 16] text:[12, 15, "---"] textClose:[15, 16, "|"]
          Text[12, 15] chars:[12, 15, "---"]
        TableCell[16, 19] text:[16, 19, "---"]
          Text[16, 19] chars:[16, 19, "---"]
    TableBody[20, 25]
      TableRow[20, 25] rowNumber=1
        TableCell[20, 22] text:[20, 21, "1"] textClose:[21, 22, "|"]
          Text[20, 21] chars:[20, 21, "1"]
        TableCell[22, 24] text:[22, 23, "2"] textClose:[23, 24, "|"]
          Text[22, 23] chars:[22, 23, "2"]
        TableCell[24, 25] text:[24, 25, "3"]
          Text[24, 25] chars:[24, 25, "3"]
````````````````````````````````


```````````````````````````````` example Tables Extension: 5
*Abc*|Def
---|---
1|2
.
||_Abc_||Def||
|1|2|

.
Document[0, 21]
  TableBlock[0, 21]
    TableHead[0, 9]
      TableRow[0, 9] rowNumber=1
        TableCell[0, 6] header text:[0, 5, "*Abc*"] textClose:[5, 6, "|"]
          Emphasis[0, 5] textOpen:[0, 1, "*"] text:[1, 4, "Abc"] textClose:[4, 5, "*"]
            Text[1, 4] chars:[1, 4, "Abc"]
        TableCell[6, 9] header text:[6, 9, "Def"]
          Text[6, 9] chars:[6, 9, "Def"]
    TableSeparator[10, 17]
      TableRow[10, 17]
        TableCell[10, 14] text:[10, 13, "---"] textClose:[13, 14, "|"]
          Text[10, 13] chars:[10, 13, "---"]
        TableCell[14, 17] text:[14, 17, "---"]
          Text[14, 17] chars:[14, 17, "---"]
    TableBody[18, 21]
      TableRow[18, 21] rowNumber=1
        TableCell[18, 20] text:[18, 19, "1"] textClose:[19, 20, "|"]
          Text[18, 19] chars:[18, 19, "1"]
        TableCell[20, 21] text:[20, 21, "2"]
          Text[20, 21] chars:[20, 21, "2"]
````````````````````````````````


```````````````````````````````` example Tables Extension: 6
> Abc|Def
> ---|---
> 1|2
.
{quote}
||Abc||Def||
|1|2|

{quote}

.
Document[0, 25]
  BlockQuote[0, 25] marker:[0, 1, ">"]
    TableBlock[2, 25]
      TableHead[2, 9]
        TableRow[2, 9] rowNumber=1
          TableCell[2, 6] header text:[2, 5, "Abc"] textClose:[5, 6, "|"]
            Text[2, 5] chars:[2, 5, "Abc"]
          TableCell[6, 9] header text:[6, 9, "Def"]
            Text[6, 9] chars:[6, 9, "Def"]
      TableSeparator[12, 19]
        TableRow[12, 19]
          TableCell[12, 16] text:[12, 15, "---"] textClose:[15, 16, "|"]
            Text[12, 15] chars:[12, 15, "---"]
          TableCell[16, 19] text:[16, 19, "---"]
            Text[16, 19] chars:[16, 19, "---"]
      TableBody[22, 25]
        TableRow[22, 25] rowNumber=1
          TableCell[22, 24] text:[22, 23, "1"] textClose:[23, 24, "|"]
            Text[22, 23] chars:[22, 23, "1"]
          TableCell[24, 25] text:[24, 25, "2"]
            Text[24, 25] chars:[24, 25, "2"]
````````````````````````````````


inlines should be processed

```````````````````````````````` example Tables Extension: 7
**Abc**|_Def_
---|---
[ref]|`code`

[ref]: /url
.
||*Abc*||_Def_||
|[ref|/url]|{{code}}|

.
Document[0, 47]
  TableBlock[0, 35]
    TableHead[0, 13]
      TableRow[0, 13] rowNumber=1
        TableCell[0, 8] header text:[0, 7, "**Abc**"] textClose:[7, 8, "|"]
          StrongEmphasis[0, 7] textOpen:[0, 2, "**"] text:[2, 5, "Abc"] textClose:[5, 7, "**"]
            Text[2, 5] chars:[2, 5, "Abc"]
        TableCell[8, 13] header text:[8, 13, "_Def_"]
          Emphasis[8, 13] textOpen:[8, 9, "_"] text:[9, 12, "Def"] textClose:[12, 13, "_"]
            Text[9, 12] chars:[9, 12, "Def"]
    TableSeparator[14, 21]
      TableRow[14, 21]
        TableCell[14, 18] text:[14, 17, "---"] textClose:[17, 18, "|"]
          Text[14, 17] chars:[14, 17, "---"]
        TableCell[18, 21] text:[18, 21, "---"]
          Text[18, 21] chars:[18, 21, "---"]
    TableBody[22, 34]
      TableRow[22, 34] rowNumber=1
        TableCell[22, 28] text:[22, 27, "[ref]"] textClose:[27, 28, "|"]
          LinkRef[22, 27] referenceOpen:[22, 23, "["] reference:[23, 26, "ref"] referenceClose:[26, 27, "]"]
            Text[23, 26] chars:[23, 26, "ref"]
        TableCell[28, 34] text:[28, 34, "`code`"]
          Code[28, 34] textOpen:[28, 29, "`"] text:[29, 33, "code"] textClose:[33, 34, "`"]
            Text[29, 33] chars:[29, 33, "code"]
  Reference[36, 47] refOpen:[36, 37, "["] ref:[37, 40, "ref"] refClose:[40, 42, "]:"] url:[43, 47, "/url"]
````````````````````````````````


Column spans are created with repeated | pipes one for each additional column to span

```````````````````````````````` example Tables Extension: 8
|Abc|Def
|---|---|
| span ||
.
||Abc||Def||
|span|

.
Document[0, 28]
  TableBlock[0, 28]
    TableHead[0, 8]
      TableRow[0, 8] rowNumber=1
        TableCell[0, 5] header textOpen:[0, 1, "|"] text:[1, 4, "Abc"] textClose:[4, 5, "|"]
          Text[1, 4] chars:[1, 4, "Abc"]
        TableCell[5, 8] header text:[5, 8, "Def"]
          Text[5, 8] chars:[5, 8, "Def"]
    TableSeparator[9, 18]
      TableRow[9, 18]
        TableCell[9, 14] textOpen:[9, 10, "|"] text:[10, 13, "---"] textClose:[13, 14, "|"]
          Text[10, 13] chars:[10, 13, "---"]
        TableCell[14, 18] text:[14, 17, "---"] textClose:[17, 18, "|"]
          Text[14, 17] chars:[14, 17, "---"]
    TableBody[19, 28]
      TableRow[19, 28] rowNumber=1
        TableCell[19, 28] span=2 textOpen:[19, 20, "|"] text:[21, 25, "span"] textClose:[26, 28, "||"]
          Text[21, 25] chars:[21, 25, "span"]
````````````````````````````````


Now we try varying the header lines and make sure we get the right output

```````````````````````````````` example Tables Extension: 9
|Abc|Def
|Hij|Lmn
|---|---|
| span ||
.
||Abc||Def||
||Hij||Lmn||
|span|

.
Document[0, 37]
  TableBlock[0, 37]
    TableHead[0, 17]
      TableRow[0, 8] rowNumber=1
        TableCell[0, 5] header textOpen:[0, 1, "|"] text:[1, 4, "Abc"] textClose:[4, 5, "|"]
          Text[1, 4] chars:[1, 4, "Abc"]
        TableCell[5, 8] header text:[5, 8, "Def"]
          Text[5, 8] chars:[5, 8, "Def"]
      TableRow[9, 17] rowNumber=2
        TableCell[9, 14] header textOpen:[9, 10, "|"] text:[10, 13, "Hij"] textClose:[13, 14, "|"]
          Text[10, 13] chars:[10, 13, "Hij"]
        TableCell[14, 17] header text:[14, 17, "Lmn"]
          Text[14, 17] chars:[14, 17, "Lmn"]
    TableSeparator[18, 27]
      TableRow[18, 27]
        TableCell[18, 23] textOpen:[18, 19, "|"] text:[19, 22, "---"] textClose:[22, 23, "|"]
          Text[19, 22] chars:[19, 22, "---"]
        TableCell[23, 27] text:[23, 26, "---"] textClose:[26, 27, "|"]
          Text[23, 26] chars:[23, 26, "---"]
    TableBody[28, 37]
      TableRow[28, 37] rowNumber=1
        TableCell[28, 37] span=2 textOpen:[28, 29, "|"] text:[30, 34, "span"] textClose:[35, 37, "||"]
          Text[30, 34] chars:[30, 34, "span"]
````````````````````````````````


No header lines

```````````````````````````````` example Tables Extension: 10
|---|---|
| col1 | col2|
.
|col1|col2|

.
Document[0, 24]
  TableBlock[0, 24]
    TableHead[0, 0]
    TableSeparator[0, 9]
      TableRow[0, 9]
        TableCell[0, 5] textOpen:[0, 1, "|"] text:[1, 4, "---"] textClose:[4, 5, "|"]
          Text[1, 4] chars:[1, 4, "---"]
        TableCell[5, 9] text:[5, 8, "---"] textClose:[8, 9, "|"]
          Text[5, 8] chars:[5, 8, "---"]
    TableBody[10, 24]
      TableRow[10, 24] rowNumber=1
        TableCell[10, 18] textOpen:[10, 11, "|"] text:[12, 16, "col1"] textClose:[17, 18, "|"]
          Text[12, 16] chars:[12, 16, "col1"]
        TableCell[18, 24] text:[19, 23, "col2"] textClose:[23, 24, "|"]
          Text[19, 23] chars:[19, 23, "col2"]
````````````````````````````````


No body lines

```````````````````````````````` example Tables Extension: 11
| col1 | col2|
|---|---|
.
||col1||col2||

.
Document[0, 24]
  TableBlock[0, 24]
    TableHead[0, 14]
      TableRow[0, 14] rowNumber=1
        TableCell[0, 8] header textOpen:[0, 1, "|"] text:[2, 6, "col1"] textClose:[7, 8, "|"]
          Text[2, 6] chars:[2, 6, "col1"]
        TableCell[8, 14] header text:[9, 13, "col2"] textClose:[13, 14, "|"]
          Text[9, 13] chars:[9, 13, "col2"]
    TableSeparator[15, 24]
      TableRow[15, 24]
        TableCell[15, 20] textOpen:[15, 16, "|"] text:[16, 19, "---"] textClose:[19, 20, "|"]
          Text[16, 19] chars:[16, 19, "---"]
        TableCell[20, 24] text:[20, 23, "---"] textClose:[23, 24, "|"]
          Text[20, 23] chars:[20, 23, "---"]
    TableBody[24, 24]
````````````````````````````````


multiple tables parsed correctly

```````````````````````````````` example Tables Extension: 12
not a table, followed by a table

| col1 | col2|
|---|---|

| col1 | col2|
|---|---|
| data1 | data2|

not a table, followed by a table

| col11 | col12|
| col21 | col22|
|---|---|
| data1 | data2|

.
not a table, followed by a table

||col1||col2||

||col1||col2||
|data1|data2|

not a table, followed by a table

||col11||col12||
||col21||col22||
|data1|data2|

.
Document[0, 199]
  Paragraph[0, 33] isTrailingBlankLine
    Text[0, 32] chars:[0, 32, "not a … table"]
  TableBlock[34, 59]
    TableHead[34, 48]
      TableRow[34, 48] rowNumber=1
        TableCell[34, 42] header textOpen:[34, 35, "|"] text:[36, 40, "col1"] textClose:[41, 42, "|"]
          Text[36, 40] chars:[36, 40, "col1"]
        TableCell[42, 48] header text:[43, 47, "col2"] textClose:[47, 48, "|"]
          Text[43, 47] chars:[43, 47, "col2"]
    TableSeparator[49, 58]
      TableRow[49, 58]
        TableCell[49, 54] textOpen:[49, 50, "|"] text:[50, 53, "---"] textClose:[53, 54, "|"]
          Text[50, 53] chars:[50, 53, "---"]
        TableCell[54, 58] text:[54, 57, "---"] textClose:[57, 58, "|"]
          Text[54, 57] chars:[54, 57, "---"]
    TableBody[58, 58]
  TableBlock[60, 102]
    TableHead[60, 74]
      TableRow[60, 74] rowNumber=1
        TableCell[60, 68] header textOpen:[60, 61, "|"] text:[62, 66, "col1"] textClose:[67, 68, "|"]
          Text[62, 66] chars:[62, 66, "col1"]
        TableCell[68, 74] header text:[69, 73, "col2"] textClose:[73, 74, "|"]
          Text[69, 73] chars:[69, 73, "col2"]
    TableSeparator[75, 84]
      TableRow[75, 84]
        TableCell[75, 80] textOpen:[75, 76, "|"] text:[76, 79, "---"] textClose:[79, 80, "|"]
          Text[76, 79] chars:[76, 79, "---"]
        TableCell[80, 84] text:[80, 83, "---"] textClose:[83, 84, "|"]
          Text[80, 83] chars:[80, 83, "---"]
    TableBody[85, 101]
      TableRow[85, 101] rowNumber=1
        TableCell[85, 94] textOpen:[85, 86, "|"] text:[87, 92, "data1"] textClose:[93, 94, "|"]
          Text[87, 92] chars:[87, 92, "data1"]
        TableCell[94, 101] text:[95, 100, "data2"] textClose:[100, 101, "|"]
          Text[95, 100] chars:[95, 100, "data2"]
  Paragraph[103, 136] isTrailingBlankLine
    Text[103, 135] chars:[103, 135, "not a … table"]
  TableBlock[137, 198]
    TableHead[137, 170]
      TableRow[137, 153] rowNumber=1
        TableCell[137, 146] header textOpen:[137, 138, "|"] text:[139, 144, "col11"] textClose:[145, 146, "|"]
          Text[139, 144] chars:[139, 144, "col11"]
        TableCell[146, 153] header text:[147, 152, "col12"] textClose:[152, 153, "|"]
          Text[147, 152] chars:[147, 152, "col12"]
      TableRow[154, 170] rowNumber=2
        TableCell[154, 163] header textOpen:[154, 155, "|"] text:[156, 161, "col21"] textClose:[162, 163, "|"]
          Text[156, 161] chars:[156, 161, "col21"]
        TableCell[163, 170] header text:[164, 169, "col22"] textClose:[169, 170, "|"]
          Text[164, 169] chars:[164, 169, "col22"]
    TableSeparator[171, 180]
      TableRow[171, 180]
        TableCell[171, 176] textOpen:[171, 172, "|"] text:[172, 175, "---"] textClose:[175, 176, "|"]
          Text[172, 175] chars:[172, 175, "---"]
        TableCell[176, 180] text:[176, 179, "---"] textClose:[179, 180, "|"]
          Text[176, 179] chars:[176, 179, "---"]
    TableBody[181, 197]
      TableRow[181, 197] rowNumber=1
        TableCell[181, 190] textOpen:[181, 182, "|"] text:[183, 188, "data1"] textClose:[189, 190, "|"]
          Text[183, 188] chars:[183, 188, "data1"]
        TableCell[190, 197] text:[191, 196, "data2"] textClose:[196, 197, "|"]
          Text[191, 196] chars:[191, 196, "data2"]
````````````````````````````````


in item

```````````````````````````````` example(Tables Extension: 13) options(keep-whitespace)
- Add: live templates starting with `.`

  | Element       | Abbreviation    | Expansion                                               |
  |---------------|-----------------|---------------------------------------------------------|
  | Abbreviation  | `.abbreviation` | `*[]: `                                                 |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\`                                       |
  | Explicit link | `.link`         | `[]()`                                                  |
.
* Add: live templates starting with {{.}}

|| Element       || Abbreviation    || Expansion                                               ||
| Abbreviation  | {{.abbreviation}} | {{*[]:}}                                                 |
| Code fence    | {{.codefence}}    | ``` ... ```                                       |
| Explicit link | {{.link}}         | {{[]()}}                                                  |

.
Document[0, 520]
  BulletList[0, 520] isLoose
    BulletListItem[0, 520] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[2, 40] isTrailingBlankLine
        Text[2, 36] chars:[2, 36, "Add:  … with "]
        Code[36, 39] textOpen:[36, 37, "`"] text:[37, 38, "."] textClose:[38, 39, "`"]
          Text[37, 38] chars:[37, 38, "."]
      TableBlock[43, 520]
        TableHead[43, 136]
          TableRow[43, 136] rowNumber=1
            TableCell[43, 60] header textOpen:[43, 44, "|"] text:[44, 59, " Element       "] textClose:[59, 60, "|"]
              Text[44, 59] chars:[44, 59, " Elem …      "]
            TableCell[60, 78] header text:[60, 77, " Abbreviation    "] textClose:[77, 78, "|"]
              Text[60, 77] chars:[60, 77, " Abbr … n    "]
            TableCell[78, 136] header text:[78, 135, " Expansion                                               "] textClose:[135, 136, "|"]
              Text[78, 135] chars:[78, 135, " Expa …      "]
        TableSeparator[139, 232]
          TableRow[139, 232]
            TableCell[139, 156] textOpen:[139, 140, "|"] text:[140, 155, "---------------"] textClose:[155, 156, "|"]
              Text[140, 155] chars:[140, 155, "----- … -----"]
            TableCell[156, 174] text:[156, 173, "-----------------"] textClose:[173, 174, "|"]
              Text[156, 173] chars:[156, 173, "----- … -----"]
            TableCell[174, 232] text:[174, 231, "---------------------------------------------------------"] textClose:[231, 232, "|"]
              Text[174, 231] chars:[174, 231, "----- … -----"]
        TableBody[235, 520]
          TableRow[235, 328] rowNumber=1
            TableCell[235, 252] textOpen:[235, 236, "|"] text:[236, 251, " Abbreviation  "] textClose:[251, 252, "|"]
              Text[236, 251] chars:[236, 251, " Abbr … ion  "]
            TableCell[252, 270] text:[252, 269, " `.abbreviation` "] textClose:[269, 270, "|"]
              Text[252, 253] chars:[252, 253, " "]
              Code[253, 268] textOpen:[253, 254, "`"] text:[254, 267, ".abbr … eviation"] textClose:[267, 268, "`"]
                Text[254, 267] chars:[254, 267, ".abbr … ation"]
              Text[268, 268]
              Text[268, 269] chars:[268, 269, " "]
            TableCell[270, 328] text:[270, 327, " `*[]: `                                                 "] textClose:[327, 328, "|"]
              Text[270, 271] chars:[270, 271, " "]
              Code[271, 278] textOpen:[271, 272, "`"] text:[272, 277, "*[]: "] textClose:[277, 278, "`"]
                Text[272, 277] chars:[272, 277, "*[]: "]
              Text[278, 278]
              Text[278, 327] chars:[278, 327, "      …      "]
          TableRow[331, 424] rowNumber=2
            TableCell[331, 348] textOpen:[331, 332, "|"] text:[332, 347, " Code fence    "] textClose:[347, 348, "|"]
              Text[332, 347] chars:[332, 347, " Code … e    "]
            TableCell[348, 366] text:[348, 365, " `.codefence`    "] textClose:[365, 366, "|"]
              Text[348, 349] chars:[348, 349, " "]
              Code[349, 361] textOpen:[349, 350, "`"] text:[350, 360, ".codefence"] textClose:[360, 361, "`"]
                Text[350, 360] chars:[350, 360, ".codefence"]
              Text[361, 361]
              Text[361, 365] chars:[361, 365, "    "]
            TableCell[366, 424] text:[366, 423, " \`\`\` ... \`\`\`                                       "] textClose:[423, 424, "|"]
              Text[366, 423] chars:[366, 423, " \`\` …      "]
          TableRow[427, 520] rowNumber=3
            TableCell[427, 444] textOpen:[427, 428, "|"] text:[428, 443, " Explicit link "] textClose:[443, 444, "|"]
              Text[428, 443] chars:[428, 443, " Expl … link "]
            TableCell[444, 462] text:[444, 461, " `.link`         "] textClose:[461, 462, "|"]
              Text[444, 445] chars:[444, 445, " "]
              Code[445, 452] textOpen:[445, 446, "`"] text:[446, 451, ".link"] textClose:[451, 452, "`"]
                Text[446, 451] chars:[446, 451, ".link"]
              Text[452, 452]
              Text[452, 461] chars:[452, 461, "         "]
            TableCell[462, 520] text:[462, 519, " `[]()`                                                  "] textClose:[519, 520, "|"]
              Text[462, 463] chars:[462, 463, " "]
              Code[463, 469] textOpen:[463, 464, "`"] text:[464, 468, "[]()"] textClose:[468, 469, "`"]
                Text[464, 468] chars:[464, 468, "[]()"]
              Text[469, 469]
              Text[469, 519] chars:[469, 519, "      …      "]
````````````````````````````````


real life table

```````````````````````````````` example Tables Extension: 14
| Feature                                                                                                                 | Basic | Enhanced |
|:------------------------------------------------------------------------------------------------------------------------|:-----:|:--------:|
| Works with builds 143.2370 or newer, product version IDEA 15.0.6                                                        |   X   |    X     |
| Preview Tab so you can see what the rendered markdown will look like on GitHub.                                         |   X   |    X     |
| Syntax highlighting                                                                                                     |   X   |    X     |
| Table syntax highlighting stripes rows and columns                                                                      |   X   |    X     |
| Support for Default and Darcula color schemes for preview tab                                                           |   X   |    X     |
| Warning and Error Annotations to help you validate wiki link errors                                                     |   X   |    X     |
| Link address completion for wiki links                                                                                  |   X   |    X     |
| Quick Fixes for detected wiki link errors                                                                               |   X   |    X     |
| GFM Task list extension `* [ ]` open task item and `* [x]` completed task item                                          |   X   |    X     |
| Line markers, Find usages, Go To Declaration for rapid navigation to wiki link targets                                  |   X   |    X     |
| Markdown extensions configuration to customize markdown dialects                                                        |   X   |    X     |
| GitHub wiki support makes maintaining GitHub wiki pages easier.                                                         |   X   |    X     |
| GitHub compatible id generation for headers so you can validate your anchor references                                  |   X   |    X     |
| Swing and JavaFX WebView based preview.                                                                                 |   X   |    X     |
| Supports **JavaFX with JetBrains JRE on OS X**                                                                          |   X   |    X     |
| Supports Highlight JS in WebView preview                                                                                |   X   |    X     |
| **Multi-line Image URLs for embedding [gravizo.com] UML diagrams into markdown**                                        |   X   |    X     |
| Live Templates for common markdown elements                                                                             |   X   |    X     |
| **Enhanced Version Benefits**                                                                                           |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Split Editor with Preview or HTML Text modes to view both source and preview                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Toolbar for fast access to frequent operations                                                  |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Language Injections for fenced code, HTML, Jekyll front matter and multi-line URL content       |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Code completions, refactoring, annotations and quick fixes to let you work faster               |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Navigation support with Line markers, Find usages, Go To Declaration for rapid navigation       |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Inspections to help you validate links, anchor refs, footnote refs                              |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Complete GitHub wiki support for all links makes maintaining GitHub wiki pages a breeze         |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Jekyll front matter recognition in markdown documents                                           |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Emoji text to icon conversion using [Emoji Cheat Sheet] or GitHub emoji URLs                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Wrap on typing and table formatting with column alignment                                       |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Character display width used for wrapping and table formatting                                  |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Structure view for Abbreviations, Headers, Tables, Footnotes, References and Document           |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Document formatting with text wrapping, list renumbering, aranging of elements, etc.            |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Table of Contents generation for any markdown parser, with many style options                   |       |    X     |
| **As you type automation**                                                                                              |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Double of bold/emphasis markers and remove inserted ones if a space is typed                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Wrap text blocks to margins and indentation                                                     |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;ATX headers to match trailing `#` marker                                                        |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Setext headers to match marker length to text                                                   |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Format tables to pad column width, column alignment and spanning columns                        |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto insert empty table row on <kbd>ENTER</kbd>                                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty table row/column on <kbd>BACKSPACE</kbd>                                      |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto insert table column when typing before first column or after last column of table          |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Actions to insert: table, row or column; delete: row or column                                  |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto insert list item on <kbd>ENTER</kbd>                                                       |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty list item on <kbd>ENTER</kbd>                                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty list item on <kbd>BACKSPACE</kbd>                                             |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Indent or un-indent list item toolbar buttons and actions                                       |       |    X     |
| **Code Completions**                                                                                                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Absolute link address completions using https:// and file:// formats                            |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Explicit and Image links are GitHub wiki aware                                                  |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;GitHub Issue # Completions after `issues/` link address and in text                             |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;GitHub special links: Issues, Pull requests, Graphs, and Pulse.                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Link address completions for non-markdown files                                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Emoji text shortcuts completion                                                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Java class, field and method completions in inline code elements                                |       |    X     |
| **Intention Actions**                                                                                                   |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Change between relative and absolute https:// link addresses via intention action               |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Change between wiki links and explicit link                                                     |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Intentions for links, wiki links, references and headers                                        |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Intention to format Setext Header marker to match marker length to text                         |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Intention to swap Setext/Atx header format                                                      |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Update table of contents quick fix intention                                                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Intention to edit Table of Contents style options dialog with preview                           |       |    X     |
| **Refactoring**                                                                                                         |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Automatic change from wiki link to explicit link when link target file is moved out of the wiki |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;File move refactoring of contained links. This completes the refactoring feature set            |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Refactoring for /, https:// and file:// absolute link addresses to project files                |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Refactoring of header text with update to referencing anchor link references                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Anchor link reference refactoring with update to referenced header text                         |       |    X     |
.
||Feature||Basic||Enhanced||
|Works with builds 143.2370 or newer, product version IDEA 15.0.6|X|X|
|Preview Tab so you can see what the rendered markdown will look like on GitHub.|X|X|
|Syntax highlighting|X|X|
|Table syntax highlighting stripes rows and columns|X|X|
|Support for Default and Darcula color schemes for preview tab|X|X|
|Warning and Error Annotations to help you validate wiki link errors|X|X|
|Link address completion for wiki links|X|X|
|Quick Fixes for detected wiki link errors|X|X|
|GFM Task list extension {{* [ ]}} open task item and {{* [x]}} completed task item|X|X|
|Line markers, Find usages, Go To Declaration for rapid navigation to wiki link targets|X|X|
|Markdown extensions configuration to customize markdown dialects|X|X|
|GitHub wiki support makes maintaining GitHub wiki pages easier.|X|X|
|GitHub compatible id generation for headers so you can validate your anchor references|X|X|
|Swing and JavaFX WebView based preview.|X|X|
|Supports *JavaFX with JetBrains JRE on OS X*|X|X|
|Supports Highlight JS in WebView preview|X|X|
|*Multi-line Image URLs for embedding [gravizo.com] UML diagrams into markdown*|X|X|
|Live Templates for common markdown elements|X|X|
|*Enhanced Version Benefits*| |X|
|    Split Editor with Preview or HTML Text modes to view both source and preview| |X|
|    Toolbar for fast access to frequent operations| |X|
|    Language Injections for fenced code, HTML, Jekyll front matter and multi-line URL content| |X|
|    Code completions, refactoring, annotations and quick fixes to let you work faster| |X|
|    Navigation support with Line markers, Find usages, Go To Declaration for rapid navigation| |X|
|    Inspections to help you validate links, anchor refs, footnote refs| |X|
|    Complete GitHub wiki support for all links makes maintaining GitHub wiki pages a breeze| |X|
|    Jekyll front matter recognition in markdown documents| |X|
|    Emoji text to icon conversion using [Emoji Cheat Sheet] or GitHub emoji URLs| |X|
|    Wrap on typing and table formatting with column alignment| |X|
|    Character display width used for wrapping and table formatting| |X|
|    Structure view for Abbreviations, Headers, Tables, Footnotes, References and Document| |X|
|    Document formatting with text wrapping, list renumbering, aranging of elements, etc.| |X|
|    Table of Contents generation for any markdown parser, with many style options| |X|
|*As you type automation*| |X|
|    Double of bold/emphasis markers and remove inserted ones if a space is typed| |X|
|    Wrap text blocks to margins and indentation| |X|
|    ATX headers to match trailing {{#}} marker| |X|
|    Setext headers to match marker length to text| |X|
|    Format tables to pad column width, column alignment and spanning columns| |X|
|    Auto insert empty table row on {{<kbd>}}ENTER{{</kbd>}}| |X|
|    Auto delete empty table row/column on {{<kbd>}}BACKSPACE{{</kbd>}}| |X|
|    Auto insert table column when typing before first column or after last column of table| |X|
|    Actions to insert: table, row or column; delete: row or column| |X|
|    Auto insert list item on {{<kbd>}}ENTER{{</kbd>}}| |X|
|    Auto delete empty list item on {{<kbd>}}ENTER{{</kbd>}}| |X|
|    Auto delete empty list item on {{<kbd>}}BACKSPACE{{</kbd>}}| |X|
|    Indent or un-indent list item toolbar buttons and actions| |X|
|*Code Completions*| |X|
|    Absolute link address completions using https:// and file:// formats| |X|
|    Explicit and Image links are GitHub wiki aware| |X|
|    GitHub Issue # Completions after {{issues/}} link address and in text| |X|
|    GitHub special links: Issues, Pull requests, Graphs, and Pulse.| |X|
|    Link address completions for non-markdown files| |X|
|    Emoji text shortcuts completion| |X|
|    Java class, field and method completions in inline code elements| |X|
|*Intention Actions*| |X|
|    Change between relative and absolute https:// link addresses via intention action| |X|
|    Change between wiki links and explicit link| |X|
|    Intentions for links, wiki links, references and headers| |X|
|    Intention to format Setext Header marker to match marker length to text| |X|
|    Intention to swap Setext/Atx header format| |X|
|    Update table of contents quick fix intention| |X|
|    Intention to edit Table of Contents style options dialog with preview| |X|
|*Refactoring*| |X|
|    Automatic change from wiki link to explicit link when link target file is moved out of the wiki| |X|
|    File move refactoring of contained links. This completes the refactoring feature set| |X|
|    Refactoring for /, https:// and file:// absolute link addresses to project files| |X|
|    Refactoring of header text with update to referencing anchor link references| |X|
|    Anchor link reference refactoring with update to referenced header text| |X|

.
Document[0, 10152]
  TableBlock[0, 10152]
    TableHead[0, 142]
      TableRow[0, 142] rowNumber=1
        TableCell[0, 123] LEFT header textOpen:[0, 1, "|"] text:[2, 9, "Feature"] textClose:[122, 123, "|"]
          Text[2, 9] chars:[2, 9, "Feature"]
        TableCell[123, 131] CENTER header text:[124, 129, "Basic"] textClose:[130, 131, "|"]
          Text[124, 129] chars:[124, 129, "Basic"]
        TableCell[131, 142] CENTER header text:[132, 140, "Enhanced"] textClose:[141, 142, "|"]
          Text[132, 140] chars:[132, 140, "Enhanced"]
    TableSeparator[143, 285]
      TableRow[143, 285]
        TableCell[143, 266] LEFT textOpen:[143, 144, "|"] text:[144, 265, ":------------------------------------------------------------------------------------------------------------------------"] textClose:[265, 266, "|"]
          Text[144, 265] chars:[144, 265, ":---- … -----"]
        TableCell[266, 274] CENTER text:[266, 273, ":-----:"] textClose:[273, 274, "|"]
          Text[266, 273] chars:[266, 273, ":-----:"]
        TableCell[274, 285] CENTER text:[274, 284, ":--------:"] textClose:[284, 285, "|"]
          Text[274, 284] chars:[274, 284, ":--------:"]
    TableBody[286, 10152]
      TableRow[286, 428] rowNumber=1
        TableCell[286, 409] LEFT textOpen:[286, 287, "|"] text:[288, 352, "Works with builds 143.2370 or newer, product version IDEA 15.0.6"] textClose:[408, 409, "|"]
          Text[288, 352] chars:[288, 352, "Works … 5.0.6"]
        TableCell[409, 417] CENTER text:[412, 413, "X"] textClose:[416, 417, "|"]
          Text[412, 413] chars:[412, 413, "X"]
        TableCell[417, 428] CENTER text:[421, 422, "X"] textClose:[427, 428, "|"]
          Text[421, 422] chars:[421, 422, "X"]
      TableRow[429, 571] rowNumber=2
        TableCell[429, 552] LEFT textOpen:[429, 430, "|"] text:[431, 510, "Preview Tab so you can see what the rendered markdown will look like on GitHub."] textClose:[551, 552, "|"]
          Text[431, 510] chars:[431, 510, "Previ … tHub."]
        TableCell[552, 560] CENTER text:[555, 556, "X"] textClose:[559, 560, "|"]
          Text[555, 556] chars:[555, 556, "X"]
        TableCell[560, 571] CENTER text:[564, 565, "X"] textClose:[570, 571, "|"]
          Text[564, 565] chars:[564, 565, "X"]
      TableRow[572, 714] rowNumber=3
        TableCell[572, 695] LEFT textOpen:[572, 573, "|"] text:[574, 593, "Syntax highlighting"] textClose:[694, 695, "|"]
          Text[574, 593] chars:[574, 593, "Synta … hting"]
        TableCell[695, 703] CENTER text:[698, 699, "X"] textClose:[702, 703, "|"]
          Text[698, 699] chars:[698, 699, "X"]
        TableCell[703, 714] CENTER text:[707, 708, "X"] textClose:[713, 714, "|"]
          Text[707, 708] chars:[707, 708, "X"]
      TableRow[715, 857] rowNumber=4
        TableCell[715, 838] LEFT textOpen:[715, 716, "|"] text:[717, 767, "Table syntax highlighting stripes rows and columns"] textClose:[837, 838, "|"]
          Text[717, 767] chars:[717, 767, "Table … lumns"]
        TableCell[838, 846] CENTER text:[841, 842, "X"] textClose:[845, 846, "|"]
          Text[841, 842] chars:[841, 842, "X"]
        TableCell[846, 857] CENTER text:[850, 851, "X"] textClose:[856, 857, "|"]
          Text[850, 851] chars:[850, 851, "X"]
      TableRow[858, 1000] rowNumber=5
        TableCell[858, 981] LEFT textOpen:[858, 859, "|"] text:[860, 921, "Support for Default and Darcula color schemes for preview tab"] textClose:[980, 981, "|"]
          Text[860, 921] chars:[860, 921, "Suppo … w tab"]
        TableCell[981, 989] CENTER text:[984, 985, "X"] textClose:[988, 989, "|"]
          Text[984, 985] chars:[984, 985, "X"]
        TableCell[989, 1000] CENTER text:[993, 994, "X"] textClose:[999, 1000, "|"]
          Text[993, 994] chars:[993, 994, "X"]
      TableRow[1001, 1143] rowNumber=6
        TableCell[1001, 1124] LEFT textOpen:[1001, 1002, "|"] text:[1003, 1070, "Warning and Error Annotations to help you validate wiki link errors"] textClose:[1123, 1124, "|"]
          Text[1003, 1070] chars:[1003, 1070, "Warni … rrors"]
        TableCell[1124, 1132] CENTER text:[1127, 1128, "X"] textClose:[1131, 1132, "|"]
          Text[1127, 1128] chars:[1127, 1128, "X"]
        TableCell[1132, 1143] CENTER text:[1136, 1137, "X"] textClose:[1142, 1143, "|"]
          Text[1136, 1137] chars:[1136, 1137, "X"]
      TableRow[1144, 1286] rowNumber=7
        TableCell[1144, 1267] LEFT textOpen:[1144, 1145, "|"] text:[1146, 1184, "Link address completion for wiki links"] textClose:[1266, 1267, "|"]
          Text[1146, 1184] chars:[1146, 1184, "Link  … links"]
        TableCell[1267, 1275] CENTER text:[1270, 1271, "X"] textClose:[1274, 1275, "|"]
          Text[1270, 1271] chars:[1270, 1271, "X"]
        TableCell[1275, 1286] CENTER text:[1279, 1280, "X"] textClose:[1285, 1286, "|"]
          Text[1279, 1280] chars:[1279, 1280, "X"]
      TableRow[1287, 1429] rowNumber=8
        TableCell[1287, 1410] LEFT textOpen:[1287, 1288, "|"] text:[1289, 1330, "Quick Fixes for detected wiki link errors"] textClose:[1409, 1410, "|"]
          Text[1289, 1330] chars:[1289, 1330, "Quick … rrors"]
        TableCell[1410, 1418] CENTER text:[1413, 1414, "X"] textClose:[1417, 1418, "|"]
          Text[1413, 1414] chars:[1413, 1414, "X"]
        TableCell[1418, 1429] CENTER text:[1422, 1423, "X"] textClose:[1428, 1429, "|"]
          Text[1422, 1423] chars:[1422, 1423, "X"]
      TableRow[1430, 1572] rowNumber=9
        TableCell[1430, 1553] LEFT textOpen:[1430, 1431, "|"] text:[1432, 1510, "GFM Task list extension `* [ ]` open task item and `* [x]` completed task item"] textClose:[1552, 1553, "|"]
          Text[1432, 1456] chars:[1432, 1456, "GFM T … sion "]
          Code[1456, 1463] textOpen:[1456, 1457, "`"] text:[1457, 1462, "* [ ]"] textClose:[1462, 1463, "`"]
            Text[1457, 1462] chars:[1457, 1462, "* [ ]"]
          Text[1463, 1483] chars:[1463, 1483, " open …  and "]
          Code[1483, 1490] textOpen:[1483, 1484, "`"] text:[1484, 1489, "* [x]"] textClose:[1489, 1490, "`"]
            Text[1484, 1489] chars:[1484, 1489, "* [x]"]
          Text[1490, 1510] chars:[1490, 1510, " comp …  item"]
        TableCell[1553, 1561] CENTER text:[1556, 1557, "X"] textClose:[1560, 1561, "|"]
          Text[1556, 1557] chars:[1556, 1557, "X"]
        TableCell[1561, 1572] CENTER text:[1565, 1566, "X"] textClose:[1571, 1572, "|"]
          Text[1565, 1566] chars:[1565, 1566, "X"]
      TableRow[1573, 1715] rowNumber=10
        TableCell[1573, 1696] LEFT textOpen:[1573, 1574, "|"] text:[1575, 1661, "Line markers, Find usages, Go To Declaration for rapid navigation to wiki link targets"] textClose:[1695, 1696, "|"]
          Text[1575, 1661] chars:[1575, 1661, "Line  … rgets"]
        TableCell[1696, 1704] CENTER text:[1699, 1700, "X"] textClose:[1703, 1704, "|"]
          Text[1699, 1700] chars:[1699, 1700, "X"]
        TableCell[1704, 1715] CENTER text:[1708, 1709, "X"] textClose:[1714, 1715, "|"]
          Text[1708, 1709] chars:[1708, 1709, "X"]
      TableRow[1716, 1858] rowNumber=11
        TableCell[1716, 1839] LEFT textOpen:[1716, 1717, "|"] text:[1718, 1782, "Markdown extensions configuration to customize markdown dialects"] textClose:[1838, 1839, "|"]
          Text[1718, 1782] chars:[1718, 1782, "Markd … lects"]
        TableCell[1839, 1847] CENTER text:[1842, 1843, "X"] textClose:[1846, 1847, "|"]
          Text[1842, 1843] chars:[1842, 1843, "X"]
        TableCell[1847, 1858] CENTER text:[1851, 1852, "X"] textClose:[1857, 1858, "|"]
          Text[1851, 1852] chars:[1851, 1852, "X"]
      TableRow[1859, 2001] rowNumber=12
        TableCell[1859, 1982] LEFT textOpen:[1859, 1860, "|"] text:[1861, 1924, "GitHub wiki support makes maintaining GitHub wiki pages easier."] textClose:[1981, 1982, "|"]
          Text[1861, 1924] chars:[1861, 1924, "GitHu … sier."]
        TableCell[1982, 1990] CENTER text:[1985, 1986, "X"] textClose:[1989, 1990, "|"]
          Text[1985, 1986] chars:[1985, 1986, "X"]
        TableCell[1990, 2001] CENTER text:[1994, 1995, "X"] textClose:[2000, 2001, "|"]
          Text[1994, 1995] chars:[1994, 1995, "X"]
      TableRow[2002, 2144] rowNumber=13
        TableCell[2002, 2125] LEFT textOpen:[2002, 2003, "|"] text:[2004, 2090, "GitHub compatible id generation for headers so you can validate your anchor references"] textClose:[2124, 2125, "|"]
          Text[2004, 2090] chars:[2004, 2090, "GitHu … ences"]
        TableCell[2125, 2133] CENTER text:[2128, 2129, "X"] textClose:[2132, 2133, "|"]
          Text[2128, 2129] chars:[2128, 2129, "X"]
        TableCell[2133, 2144] CENTER text:[2137, 2138, "X"] textClose:[2143, 2144, "|"]
          Text[2137, 2138] chars:[2137, 2138, "X"]
      TableRow[2145, 2287] rowNumber=14
        TableCell[2145, 2268] LEFT textOpen:[2145, 2146, "|"] text:[2147, 2186, "Swing and JavaFX WebView based preview."] textClose:[2267, 2268, "|"]
          Text[2147, 2186] chars:[2147, 2186, "Swing … view."]
        TableCell[2268, 2276] CENTER text:[2271, 2272, "X"] textClose:[2275, 2276, "|"]
          Text[2271, 2272] chars:[2271, 2272, "X"]
        TableCell[2276, 2287] CENTER text:[2280, 2281, "X"] textClose:[2286, 2287, "|"]
          Text[2280, 2281] chars:[2280, 2281, "X"]
      TableRow[2288, 2430] rowNumber=15
        TableCell[2288, 2411] LEFT textOpen:[2288, 2289, "|"] text:[2290, 2336, "Supports **JavaFX with JetBrains JRE on OS X**"] textClose:[2410, 2411, "|"]
          Text[2290, 2299] chars:[2290, 2299, "Supports "]
          StrongEmphasis[2299, 2336] textOpen:[2299, 2301, "**"] text:[2301, 2334, "JavaFX with JetBrains JRE on OS X"] textClose:[2334, 2336, "**"]
            Text[2301, 2334] chars:[2301, 2334, "JavaF …  OS X"]
          Text[2336, 2336]
        TableCell[2411, 2419] CENTER text:[2414, 2415, "X"] textClose:[2418, 2419, "|"]
          Text[2414, 2415] chars:[2414, 2415, "X"]
        TableCell[2419, 2430] CENTER text:[2423, 2424, "X"] textClose:[2429, 2430, "|"]
          Text[2423, 2424] chars:[2423, 2424, "X"]
      TableRow[2431, 2573] rowNumber=16
        TableCell[2431, 2554] LEFT textOpen:[2431, 2432, "|"] text:[2433, 2473, "Supports Highlight JS in WebView preview"] textClose:[2553, 2554, "|"]
          Text[2433, 2473] chars:[2433, 2473, "Suppo … eview"]
        TableCell[2554, 2562] CENTER text:[2557, 2558, "X"] textClose:[2561, 2562, "|"]
          Text[2557, 2558] chars:[2557, 2558, "X"]
        TableCell[2562, 2573] CENTER text:[2566, 2567, "X"] textClose:[2572, 2573, "|"]
          Text[2566, 2567] chars:[2566, 2567, "X"]
      TableRow[2574, 2716] rowNumber=17
        TableCell[2574, 2697] LEFT textOpen:[2574, 2575, "|"] text:[2576, 2656, "**Multi-line Image URLs for embedding [gravizo.com] UML diagrams into markdown**"] textClose:[2696, 2697, "|"]
          StrongEmphasis[2576, 2656] textOpen:[2576, 2578, "**"] text:[2578, 2654, "Multi-line Image URLs for embedding [gravizo.com] UML diagrams into markdown"] textClose:[2654, 2656, "**"]
            Text[2578, 2614] chars:[2578, 2614, "Multi … ding "]
            LinkRef[2614, 2627] referenceOpen:[2614, 2615, "["] reference:[2615, 2626, "gravizo.com"] referenceClose:[2626, 2627, "]"]
              Text[2615, 2626] chars:[2615, 2626, "gravi … o.com"]
            Text[2627, 2654] chars:[2627, 2654, " UML  … kdown"]
          Text[2656, 2656]
        TableCell[2697, 2705] CENTER text:[2700, 2701, "X"] textClose:[2704, 2705, "|"]
          Text[2700, 2701] chars:[2700, 2701, "X"]
        TableCell[2705, 2716] CENTER text:[2709, 2710, "X"] textClose:[2715, 2716, "|"]
          Text[2709, 2710] chars:[2709, 2710, "X"]
      TableRow[2717, 2859] rowNumber=18
        TableCell[2717, 2840] LEFT textOpen:[2717, 2718, "|"] text:[2719, 2762, "Live Templates for common markdown elements"] textClose:[2839, 2840, "|"]
          Text[2719, 2762] chars:[2719, 2762, "Live  … ments"]
        TableCell[2840, 2848] CENTER text:[2843, 2844, "X"] textClose:[2847, 2848, "|"]
          Text[2843, 2844] chars:[2843, 2844, "X"]
        TableCell[2848, 2859] CENTER text:[2852, 2853, "X"] textClose:[2858, 2859, "|"]
          Text[2852, 2853] chars:[2852, 2853, "X"]
      TableRow[2860, 3002] rowNumber=19
        TableCell[2860, 2983] LEFT textOpen:[2860, 2861, "|"] text:[2862, 2891, "**Enhanced Version Benefits**"] textClose:[2982, 2983, "|"]
          StrongEmphasis[2862, 2891] textOpen:[2862, 2864, "**"] text:[2864, 2889, "Enhanced Version Benefits"] textClose:[2889, 2891, "**"]
            Text[2864, 2889] chars:[2864, 2889, "Enhan … efits"]
          Text[2891, 2891]
        TableCell[2983, 2991] CENTER text:[2983, 2984, " "] textClose:[2990, 2991, "|"]
          Text[2983, 2984] chars:[2983, 2984, " "]
        TableCell[2991, 3002] CENTER text:[2995, 2996, "X"] textClose:[3001, 3002, "|"]
          Text[2995, 2996] chars:[2995, 2996, "X"]
      TableRow[3003, 3145] rowNumber=20
        TableCell[3003, 3126] LEFT textOpen:[3003, 3004, "|"] text:[3005, 3105, "&nbsp;&nbsp;&nbsp;&nbsp;Split Editor with Preview or HTML Text modes to view both source and preview"] textClose:[3125, 3126, "|"]
          HtmlEntity[3005, 3011] "&nbsp;"
          HtmlEntity[3011, 3017] "&nbsp;"
          HtmlEntity[3017, 3023] "&nbsp;"
          HtmlEntity[3023, 3029] "&nbsp;"
          Text[3029, 3105] chars:[3029, 3105, "Split … eview"]
        TableCell[3126, 3134] CENTER text:[3126, 3127, " "] textClose:[3133, 3134, "|"]
          Text[3126, 3127] chars:[3126, 3127, " "]
        TableCell[3134, 3145] CENTER text:[3138, 3139, "X"] textClose:[3144, 3145, "|"]
          Text[3138, 3139] chars:[3138, 3139, "X"]
      TableRow[3146, 3288] rowNumber=21
        TableCell[3146, 3269] LEFT textOpen:[3146, 3147, "|"] text:[3148, 3218, "&nbsp;&nbsp;&nbsp;&nbsp;Toolbar for fast access to frequent operations"] textClose:[3268, 3269, "|"]
          HtmlEntity[3148, 3154] "&nbsp;"
          HtmlEntity[3154, 3160] "&nbsp;"
          HtmlEntity[3160, 3166] "&nbsp;"
          HtmlEntity[3166, 3172] "&nbsp;"
          Text[3172, 3218] chars:[3172, 3218, "Toolb … tions"]
        TableCell[3269, 3277] CENTER text:[3269, 3270, " "] textClose:[3276, 3277, "|"]
          Text[3269, 3270] chars:[3269, 3270, " "]
        TableCell[3277, 3288] CENTER text:[3281, 3282, "X"] textClose:[3287, 3288, "|"]
          Text[3281, 3282] chars:[3281, 3282, "X"]
      TableRow[3289, 3431] rowNumber=22
        TableCell[3289, 3412] LEFT textOpen:[3289, 3290, "|"] text:[3291, 3404, "&nbsp;&nbsp;&nbsp;&nbsp;Language Injections for fenced code, HTML, Jekyll front matter and multi-line URL content"] textClose:[3411, 3412, "|"]
          HtmlEntity[3291, 3297] "&nbsp;"
          HtmlEntity[3297, 3303] "&nbsp;"
          HtmlEntity[3303, 3309] "&nbsp;"
          HtmlEntity[3309, 3315] "&nbsp;"
          Text[3315, 3404] chars:[3315, 3404, "Langu … ntent"]
        TableCell[3412, 3420] CENTER text:[3412, 3413, " "] textClose:[3419, 3420, "|"]
          Text[3412, 3413] chars:[3412, 3413, " "]
        TableCell[3420, 3431] CENTER text:[3424, 3425, "X"] textClose:[3430, 3431, "|"]
          Text[3424, 3425] chars:[3424, 3425, "X"]
      TableRow[3432, 3574] rowNumber=23
        TableCell[3432, 3555] LEFT textOpen:[3432, 3433, "|"] text:[3434, 3539, "&nbsp;&nbsp;&nbsp;&nbsp;Code completions, refactoring, annotations and quick fixes to let you work faster"] textClose:[3554, 3555, "|"]
          HtmlEntity[3434, 3440] "&nbsp;"
          HtmlEntity[3440, 3446] "&nbsp;"
          HtmlEntity[3446, 3452] "&nbsp;"
          HtmlEntity[3452, 3458] "&nbsp;"
          Text[3458, 3539] chars:[3458, 3539, "Code  … aster"]
        TableCell[3555, 3563] CENTER text:[3555, 3556, " "] textClose:[3562, 3563, "|"]
          Text[3555, 3556] chars:[3555, 3556, " "]
        TableCell[3563, 3574] CENTER text:[3567, 3568, "X"] textClose:[3573, 3574, "|"]
          Text[3567, 3568] chars:[3567, 3568, "X"]
      TableRow[3575, 3717] rowNumber=24
        TableCell[3575, 3698] LEFT textOpen:[3575, 3576, "|"] text:[3577, 3690, "&nbsp;&nbsp;&nbsp;&nbsp;Navigation support with Line markers, Find usages, Go To Declaration for rapid navigation"] textClose:[3697, 3698, "|"]
          HtmlEntity[3577, 3583] "&nbsp;"
          HtmlEntity[3583, 3589] "&nbsp;"
          HtmlEntity[3589, 3595] "&nbsp;"
          HtmlEntity[3595, 3601] "&nbsp;"
          Text[3601, 3690] chars:[3601, 3690, "Navig … ation"]
        TableCell[3698, 3706] CENTER text:[3698, 3699, " "] textClose:[3705, 3706, "|"]
          Text[3698, 3699] chars:[3698, 3699, " "]
        TableCell[3706, 3717] CENTER text:[3710, 3711, "X"] textClose:[3716, 3717, "|"]
          Text[3710, 3711] chars:[3710, 3711, "X"]
      TableRow[3718, 3860] rowNumber=25
        TableCell[3718, 3841] LEFT textOpen:[3718, 3719, "|"] text:[3720, 3810, "&nbsp;&nbsp;&nbsp;&nbsp;Inspections to help you validate links, anchor refs, footnote refs"] textClose:[3840, 3841, "|"]
          HtmlEntity[3720, 3726] "&nbsp;"
          HtmlEntity[3726, 3732] "&nbsp;"
          HtmlEntity[3732, 3738] "&nbsp;"
          HtmlEntity[3738, 3744] "&nbsp;"
          Text[3744, 3810] chars:[3744, 3810, "Inspe …  refs"]
        TableCell[3841, 3849] CENTER text:[3841, 3842, " "] textClose:[3848, 3849, "|"]
          Text[3841, 3842] chars:[3841, 3842, " "]
        TableCell[3849, 3860] CENTER text:[3853, 3854, "X"] textClose:[3859, 3860, "|"]
          Text[3853, 3854] chars:[3853, 3854, "X"]
      TableRow[3861, 4003] rowNumber=26
        TableCell[3861, 3984] LEFT textOpen:[3861, 3862, "|"] text:[3863, 3974, "&nbsp;&nbsp;&nbsp;&nbsp;Complete GitHub wiki support for all links makes maintaining GitHub wiki pages a breeze"] textClose:[3983, 3984, "|"]
          HtmlEntity[3863, 3869] "&nbsp;"
          HtmlEntity[3869, 3875] "&nbsp;"
          HtmlEntity[3875, 3881] "&nbsp;"
          HtmlEntity[3881, 3887] "&nbsp;"
          Text[3887, 3974] chars:[3887, 3974, "Compl … reeze"]
        TableCell[3984, 3992] CENTER text:[3984, 3985, " "] textClose:[3991, 3992, "|"]
          Text[3984, 3985] chars:[3984, 3985, " "]
        TableCell[3992, 4003] CENTER text:[3996, 3997, "X"] textClose:[4002, 4003, "|"]
          Text[3996, 3997] chars:[3996, 3997, "X"]
      TableRow[4004, 4146] rowNumber=27
        TableCell[4004, 4127] LEFT textOpen:[4004, 4005, "|"] text:[4006, 4083, "&nbsp;&nbsp;&nbsp;&nbsp;Jekyll front matter recognition in markdown documents"] textClose:[4126, 4127, "|"]
          HtmlEntity[4006, 4012] "&nbsp;"
          HtmlEntity[4012, 4018] "&nbsp;"
          HtmlEntity[4018, 4024] "&nbsp;"
          HtmlEntity[4024, 4030] "&nbsp;"
          Text[4030, 4083] chars:[4030, 4083, "Jekyl … ments"]
        TableCell[4127, 4135] CENTER text:[4127, 4128, " "] textClose:[4134, 4135, "|"]
          Text[4127, 4128] chars:[4127, 4128, " "]
        TableCell[4135, 4146] CENTER text:[4139, 4140, "X"] textClose:[4145, 4146, "|"]
          Text[4139, 4140] chars:[4139, 4140, "X"]
      TableRow[4147, 4289] rowNumber=28
        TableCell[4147, 4270] LEFT textOpen:[4147, 4148, "|"] text:[4149, 4249, "&nbsp;&nbsp;&nbsp;&nbsp;Emoji text to icon conversion using [Emoji Cheat Sheet] or GitHub emoji URLs"] textClose:[4269, 4270, "|"]
          HtmlEntity[4149, 4155] "&nbsp;"
          HtmlEntity[4155, 4161] "&nbsp;"
          HtmlEntity[4161, 4167] "&nbsp;"
          HtmlEntity[4167, 4173] "&nbsp;"
          Text[4173, 4209] chars:[4173, 4209, "Emoji … sing "]
          LinkRef[4209, 4228] referenceOpen:[4209, 4210, "["] reference:[4210, 4227, "Emoji Cheat Sheet"] referenceClose:[4227, 4228, "]"]
            Text[4210, 4227] chars:[4210, 4227, "Emoji … Sheet"]
          Text[4228, 4249] chars:[4228, 4249, " or G …  URLs"]
        TableCell[4270, 4278] CENTER text:[4270, 4271, " "] textClose:[4277, 4278, "|"]
          Text[4270, 4271] chars:[4270, 4271, " "]
        TableCell[4278, 4289] CENTER text:[4282, 4283, "X"] textClose:[4288, 4289, "|"]
          Text[4282, 4283] chars:[4282, 4283, "X"]
      TableRow[4290, 4432] rowNumber=29
        TableCell[4290, 4413] LEFT textOpen:[4290, 4291, "|"] text:[4292, 4373, "&nbsp;&nbsp;&nbsp;&nbsp;Wrap on typing and table formatting with column alignment"] textClose:[4412, 4413, "|"]
          HtmlEntity[4292, 4298] "&nbsp;"
          HtmlEntity[4298, 4304] "&nbsp;"
          HtmlEntity[4304, 4310] "&nbsp;"
          HtmlEntity[4310, 4316] "&nbsp;"
          Text[4316, 4373] chars:[4316, 4373, "Wrap  … nment"]
        TableCell[4413, 4421] CENTER text:[4413, 4414, " "] textClose:[4420, 4421, "|"]
          Text[4413, 4414] chars:[4413, 4414, " "]
        TableCell[4421, 4432] CENTER text:[4425, 4426, "X"] textClose:[4431, 4432, "|"]
          Text[4425, 4426] chars:[4425, 4426, "X"]
      TableRow[4433, 4575] rowNumber=30
        TableCell[4433, 4556] LEFT textOpen:[4433, 4434, "|"] text:[4435, 4521, "&nbsp;&nbsp;&nbsp;&nbsp;Character display width used for wrapping and table formatting"] textClose:[4555, 4556, "|"]
          HtmlEntity[4435, 4441] "&nbsp;"
          HtmlEntity[4441, 4447] "&nbsp;"
          HtmlEntity[4447, 4453] "&nbsp;"
          HtmlEntity[4453, 4459] "&nbsp;"
          Text[4459, 4521] chars:[4459, 4521, "Chara … tting"]
        TableCell[4556, 4564] CENTER text:[4556, 4557, " "] textClose:[4563, 4564, "|"]
          Text[4556, 4557] chars:[4556, 4557, " "]
        TableCell[4564, 4575] CENTER text:[4568, 4569, "X"] textClose:[4574, 4575, "|"]
          Text[4568, 4569] chars:[4568, 4569, "X"]
      TableRow[4576, 4718] rowNumber=31
        TableCell[4576, 4699] LEFT textOpen:[4576, 4577, "|"] text:[4578, 4687, "&nbsp;&nbsp;&nbsp;&nbsp;Structure view for Abbreviations, Headers, Tables, Footnotes, References and Document"] textClose:[4698, 4699, "|"]
          HtmlEntity[4578, 4584] "&nbsp;"
          HtmlEntity[4584, 4590] "&nbsp;"
          HtmlEntity[4590, 4596] "&nbsp;"
          HtmlEntity[4596, 4602] "&nbsp;"
          Text[4602, 4687] chars:[4602, 4687, "Struc … ument"]
        TableCell[4699, 4707] CENTER text:[4699, 4700, " "] textClose:[4706, 4707, "|"]
          Text[4699, 4700] chars:[4699, 4700, " "]
        TableCell[4707, 4718] CENTER text:[4711, 4712, "X"] textClose:[4717, 4718, "|"]
          Text[4711, 4712] chars:[4711, 4712, "X"]
      TableRow[4719, 4861] rowNumber=32
        TableCell[4719, 4842] LEFT textOpen:[4719, 4720, "|"] text:[4721, 4829, "&nbsp;&nbsp;&nbsp;&nbsp;Document formatting with text wrapping, list renumbering, aranging of elements, etc."] textClose:[4841, 4842, "|"]
          HtmlEntity[4721, 4727] "&nbsp;"
          HtmlEntity[4727, 4733] "&nbsp;"
          HtmlEntity[4733, 4739] "&nbsp;"
          HtmlEntity[4739, 4745] "&nbsp;"
          Text[4745, 4829] chars:[4745, 4829, "Docum …  etc."]
        TableCell[4842, 4850] CENTER text:[4842, 4843, " "] textClose:[4849, 4850, "|"]
          Text[4842, 4843] chars:[4842, 4843, " "]
        TableCell[4850, 4861] CENTER text:[4854, 4855, "X"] textClose:[4860, 4861, "|"]
          Text[4854, 4855] chars:[4854, 4855, "X"]
      TableRow[4862, 5004] rowNumber=33
        TableCell[4862, 4985] LEFT textOpen:[4862, 4863, "|"] text:[4864, 4965, "&nbsp;&nbsp;&nbsp;&nbsp;Table of Contents generation for any markdown parser, with many style options"] textClose:[4984, 4985, "|"]
          HtmlEntity[4864, 4870] "&nbsp;"
          HtmlEntity[4870, 4876] "&nbsp;"
          HtmlEntity[4876, 4882] "&nbsp;"
          HtmlEntity[4882, 4888] "&nbsp;"
          Text[4888, 4965] chars:[4888, 4965, "Table … tions"]
        TableCell[4985, 4993] CENTER text:[4985, 4986, " "] textClose:[4992, 4993, "|"]
          Text[4985, 4986] chars:[4985, 4986, " "]
        TableCell[4993, 5004] CENTER text:[4997, 4998, "X"] textClose:[5003, 5004, "|"]
          Text[4997, 4998] chars:[4997, 4998, "X"]
      TableRow[5005, 5147] rowNumber=34
        TableCell[5005, 5128] LEFT textOpen:[5005, 5006, "|"] text:[5007, 5033, "**As you type automation**"] textClose:[5127, 5128, "|"]
          StrongEmphasis[5007, 5033] textOpen:[5007, 5009, "**"] text:[5009, 5031, "As you type automation"] textClose:[5031, 5033, "**"]
            Text[5009, 5031] chars:[5009, 5031, "As yo … ation"]
          Text[5033, 5033]
        TableCell[5128, 5136] CENTER text:[5128, 5129, " "] textClose:[5135, 5136, "|"]
          Text[5128, 5129] chars:[5128, 5129, " "]
        TableCell[5136, 5147] CENTER text:[5140, 5141, "X"] textClose:[5146, 5147, "|"]
          Text[5140, 5141] chars:[5140, 5141, "X"]
      TableRow[5148, 5290] rowNumber=35
        TableCell[5148, 5271] LEFT textOpen:[5148, 5149, "|"] text:[5150, 5250, "&nbsp;&nbsp;&nbsp;&nbsp;Double of bold/emphasis markers and remove inserted ones if a space is typed"] textClose:[5270, 5271, "|"]
          HtmlEntity[5150, 5156] "&nbsp;"
          HtmlEntity[5156, 5162] "&nbsp;"
          HtmlEntity[5162, 5168] "&nbsp;"
          HtmlEntity[5168, 5174] "&nbsp;"
          Text[5174, 5250] chars:[5174, 5250, "Doubl … typed"]
        TableCell[5271, 5279] CENTER text:[5271, 5272, " "] textClose:[5278, 5279, "|"]
          Text[5271, 5272] chars:[5271, 5272, " "]
        TableCell[5279, 5290] CENTER text:[5283, 5284, "X"] textClose:[5289, 5290, "|"]
          Text[5283, 5284] chars:[5283, 5284, "X"]
      TableRow[5291, 5433] rowNumber=36
        TableCell[5291, 5414] LEFT textOpen:[5291, 5292, "|"] text:[5293, 5360, "&nbsp;&nbsp;&nbsp;&nbsp;Wrap text blocks to margins and indentation"] textClose:[5413, 5414, "|"]
          HtmlEntity[5293, 5299] "&nbsp;"
          HtmlEntity[5299, 5305] "&nbsp;"
          HtmlEntity[5305, 5311] "&nbsp;"
          HtmlEntity[5311, 5317] "&nbsp;"
          Text[5317, 5360] chars:[5317, 5360, "Wrap  … ation"]
        TableCell[5414, 5422] CENTER text:[5414, 5415, " "] textClose:[5421, 5422, "|"]
          Text[5414, 5415] chars:[5414, 5415, " "]
        TableCell[5422, 5433] CENTER text:[5426, 5427, "X"] textClose:[5432, 5433, "|"]
          Text[5426, 5427] chars:[5426, 5427, "X"]
      TableRow[5434, 5576] rowNumber=37
        TableCell[5434, 5557] LEFT textOpen:[5434, 5435, "|"] text:[5436, 5500, "&nbsp;&nbsp;&nbsp;&nbsp;ATX headers to match trailing `#` marker"] textClose:[5556, 5557, "|"]
          HtmlEntity[5436, 5442] "&nbsp;"
          HtmlEntity[5442, 5448] "&nbsp;"
          HtmlEntity[5448, 5454] "&nbsp;"
          HtmlEntity[5454, 5460] "&nbsp;"
          Text[5460, 5490] chars:[5460, 5490, "ATX h … ling "]
          Code[5490, 5493] textOpen:[5490, 5491, "`"] text:[5491, 5492, "#"] textClose:[5492, 5493, "`"]
            Text[5491, 5492] chars:[5491, 5492, "#"]
          Text[5493, 5500] chars:[5493, 5500, " marker"]
        TableCell[5557, 5565] CENTER text:[5557, 5558, " "] textClose:[5564, 5565, "|"]
          Text[5557, 5558] chars:[5557, 5558, " "]
        TableCell[5565, 5576] CENTER text:[5569, 5570, "X"] textClose:[5575, 5576, "|"]
          Text[5569, 5570] chars:[5569, 5570, "X"]
      TableRow[5577, 5719] rowNumber=38
        TableCell[5577, 5700] LEFT textOpen:[5577, 5578, "|"] text:[5579, 5648, "&nbsp;&nbsp;&nbsp;&nbsp;Setext headers to match marker length to text"] textClose:[5699, 5700, "|"]
          HtmlEntity[5579, 5585] "&nbsp;"
          HtmlEntity[5585, 5591] "&nbsp;"
          HtmlEntity[5591, 5597] "&nbsp;"
          HtmlEntity[5597, 5603] "&nbsp;"
          Text[5603, 5648] chars:[5603, 5648, "Setex …  text"]
        TableCell[5700, 5708] CENTER text:[5700, 5701, " "] textClose:[5707, 5708, "|"]
          Text[5700, 5701] chars:[5700, 5701, " "]
        TableCell[5708, 5719] CENTER text:[5712, 5713, "X"] textClose:[5718, 5719, "|"]
          Text[5712, 5713] chars:[5712, 5713, "X"]
      TableRow[5720, 5862] rowNumber=39
        TableCell[5720, 5843] LEFT textOpen:[5720, 5721, "|"] text:[5722, 5818, "&nbsp;&nbsp;&nbsp;&nbsp;Format tables to pad column width, column alignment and spanning columns"] textClose:[5842, 5843, "|"]
          HtmlEntity[5722, 5728] "&nbsp;"
          HtmlEntity[5728, 5734] "&nbsp;"
          HtmlEntity[5734, 5740] "&nbsp;"
          HtmlEntity[5740, 5746] "&nbsp;"
          Text[5746, 5818] chars:[5746, 5818, "Forma … lumns"]
        TableCell[5843, 5851] CENTER text:[5843, 5844, " "] textClose:[5850, 5851, "|"]
          Text[5843, 5844] chars:[5843, 5844, " "]
        TableCell[5851, 5862] CENTER text:[5855, 5856, "X"] textClose:[5861, 5862, "|"]
          Text[5855, 5856] chars:[5855, 5856, "X"]
      TableRow[5863, 6005] rowNumber=40
        TableCell[5863, 5986] LEFT textOpen:[5863, 5864, "|"] text:[5865, 5936, "&nbsp;&nbsp;&nbsp;&nbsp;Auto insert empty table row on <kbd>ENTER</kbd>"] textClose:[5985, 5986, "|"]
          HtmlEntity[5865, 5871] "&nbsp;"
          HtmlEntity[5871, 5877] "&nbsp;"
          HtmlEntity[5877, 5883] "&nbsp;"
          HtmlEntity[5883, 5889] "&nbsp;"
          Text[5889, 5920] chars:[5889, 5920, "Auto  … w on "]
          HtmlInline[5920, 5925] chars:[5920, 5925, "<kbd>"]
          Text[5925, 5930] chars:[5925, 5930, "ENTER"]
          HtmlInline[5930, 5936] chars:[5930, 5936, "</kbd>"]
          Text[5936, 5936]
        TableCell[5986, 5994] CENTER text:[5986, 5987, " "] textClose:[5993, 5994, "|"]
          Text[5986, 5987] chars:[5986, 5987, " "]
        TableCell[5994, 6005] CENTER text:[5998, 5999, "X"] textClose:[6004, 6005, "|"]
          Text[5998, 5999] chars:[5998, 5999, "X"]
      TableRow[6006, 6148] rowNumber=41
        TableCell[6006, 6129] LEFT textOpen:[6006, 6007, "|"] text:[6008, 6090, "&nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty table row/column on <kbd>BACKSPACE</kbd>"] textClose:[6128, 6129, "|"]
          HtmlEntity[6008, 6014] "&nbsp;"
          HtmlEntity[6014, 6020] "&nbsp;"
          HtmlEntity[6020, 6026] "&nbsp;"
          HtmlEntity[6026, 6032] "&nbsp;"
          Text[6032, 6070] chars:[6032, 6070, "Auto  … n on "]
          HtmlInline[6070, 6075] chars:[6070, 6075, "<kbd>"]
          Text[6075, 6084] chars:[6075, 6084, "BACKSPACE"]
          HtmlInline[6084, 6090] chars:[6084, 6090, "</kbd>"]
          Text[6090, 6090]
        TableCell[6129, 6137] CENTER text:[6129, 6130, " "] textClose:[6136, 6137, "|"]
          Text[6129, 6130] chars:[6129, 6130, " "]
        TableCell[6137, 6148] CENTER text:[6141, 6142, "X"] textClose:[6147, 6148, "|"]
          Text[6141, 6142] chars:[6141, 6142, "X"]
      TableRow[6149, 6291] rowNumber=42
        TableCell[6149, 6272] LEFT textOpen:[6149, 6150, "|"] text:[6151, 6261, "&nbsp;&nbsp;&nbsp;&nbsp;Auto insert table column when typing before first column or after last column of table"] textClose:[6271, 6272, "|"]
          HtmlEntity[6151, 6157] "&nbsp;"
          HtmlEntity[6157, 6163] "&nbsp;"
          HtmlEntity[6163, 6169] "&nbsp;"
          HtmlEntity[6169, 6175] "&nbsp;"
          Text[6175, 6261] chars:[6175, 6261, "Auto  … table"]
        TableCell[6272, 6280] CENTER text:[6272, 6273, " "] textClose:[6279, 6280, "|"]
          Text[6272, 6273] chars:[6272, 6273, " "]
        TableCell[6280, 6291] CENTER text:[6284, 6285, "X"] textClose:[6290, 6291, "|"]
          Text[6284, 6285] chars:[6284, 6285, "X"]
      TableRow[6292, 6434] rowNumber=43
        TableCell[6292, 6415] LEFT textOpen:[6292, 6293, "|"] text:[6294, 6380, "&nbsp;&nbsp;&nbsp;&nbsp;Actions to insert: table, row or column; delete: row or column"] textClose:[6414, 6415, "|"]
          HtmlEntity[6294, 6300] "&nbsp;"
          HtmlEntity[6300, 6306] "&nbsp;"
          HtmlEntity[6306, 6312] "&nbsp;"
          HtmlEntity[6312, 6318] "&nbsp;"
          Text[6318, 6380] chars:[6318, 6380, "Actio … olumn"]
        TableCell[6415, 6423] CENTER text:[6415, 6416, " "] textClose:[6422, 6423, "|"]
          Text[6415, 6416] chars:[6415, 6416, " "]
        TableCell[6423, 6434] CENTER text:[6427, 6428, "X"] textClose:[6433, 6434, "|"]
          Text[6427, 6428] chars:[6427, 6428, "X"]
      TableRow[6435, 6577] rowNumber=44
        TableCell[6435, 6558] LEFT textOpen:[6435, 6436, "|"] text:[6437, 6502, "&nbsp;&nbsp;&nbsp;&nbsp;Auto insert list item on <kbd>ENTER</kbd>"] textClose:[6557, 6558, "|"]
          HtmlEntity[6437, 6443] "&nbsp;"
          HtmlEntity[6443, 6449] "&nbsp;"
          HtmlEntity[6449, 6455] "&nbsp;"
          HtmlEntity[6455, 6461] "&nbsp;"
          Text[6461, 6486] chars:[6461, 6486, "Auto  … m on "]
          HtmlInline[6486, 6491] chars:[6486, 6491, "<kbd>"]
          Text[6491, 6496] chars:[6491, 6496, "ENTER"]
          HtmlInline[6496, 6502] chars:[6496, 6502, "</kbd>"]
          Text[6502, 6502]
        TableCell[6558, 6566] CENTER text:[6558, 6559, " "] textClose:[6565, 6566, "|"]
          Text[6558, 6559] chars:[6558, 6559, " "]
        TableCell[6566, 6577] CENTER text:[6570, 6571, "X"] textClose:[6576, 6577, "|"]
          Text[6570, 6571] chars:[6570, 6571, "X"]
      TableRow[6578, 6720] rowNumber=45
        TableCell[6578, 6701] LEFT textOpen:[6578, 6579, "|"] text:[6580, 6651, "&nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty list item on <kbd>ENTER</kbd>"] textClose:[6700, 6701, "|"]
          HtmlEntity[6580, 6586] "&nbsp;"
          HtmlEntity[6586, 6592] "&nbsp;"
          HtmlEntity[6592, 6598] "&nbsp;"
          HtmlEntity[6598, 6604] "&nbsp;"
          Text[6604, 6635] chars:[6604, 6635, "Auto  … m on "]
          HtmlInline[6635, 6640] chars:[6635, 6640, "<kbd>"]
          Text[6640, 6645] chars:[6640, 6645, "ENTER"]
          HtmlInline[6645, 6651] chars:[6645, 6651, "</kbd>"]
          Text[6651, 6651]
        TableCell[6701, 6709] CENTER text:[6701, 6702, " "] textClose:[6708, 6709, "|"]
          Text[6701, 6702] chars:[6701, 6702, " "]
        TableCell[6709, 6720] CENTER text:[6713, 6714, "X"] textClose:[6719, 6720, "|"]
          Text[6713, 6714] chars:[6713, 6714, "X"]
      TableRow[6721, 6863] rowNumber=46
        TableCell[6721, 6844] LEFT textOpen:[6721, 6722, "|"] text:[6723, 6798, "&nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty list item on <kbd>BACKSPACE</kbd>"] textClose:[6843, 6844, "|"]
          HtmlEntity[6723, 6729] "&nbsp;"
          HtmlEntity[6729, 6735] "&nbsp;"
          HtmlEntity[6735, 6741] "&nbsp;"
          HtmlEntity[6741, 6747] "&nbsp;"
          Text[6747, 6778] chars:[6747, 6778, "Auto  … m on "]
          HtmlInline[6778, 6783] chars:[6778, 6783, "<kbd>"]
          Text[6783, 6792] chars:[6783, 6792, "BACKSPACE"]
          HtmlInline[6792, 6798] chars:[6792, 6798, "</kbd>"]
          Text[6798, 6798]
        TableCell[6844, 6852] CENTER text:[6844, 6845, " "] textClose:[6851, 6852, "|"]
          Text[6844, 6845] chars:[6844, 6845, " "]
        TableCell[6852, 6863] CENTER text:[6856, 6857, "X"] textClose:[6862, 6863, "|"]
          Text[6856, 6857] chars:[6856, 6857, "X"]
      TableRow[6864, 7006] rowNumber=47
        TableCell[6864, 6987] LEFT textOpen:[6864, 6865, "|"] text:[6866, 6947, "&nbsp;&nbsp;&nbsp;&nbsp;Indent or un-indent list item toolbar buttons and actions"] textClose:[6986, 6987, "|"]
          HtmlEntity[6866, 6872] "&nbsp;"
          HtmlEntity[6872, 6878] "&nbsp;"
          HtmlEntity[6878, 6884] "&nbsp;"
          HtmlEntity[6884, 6890] "&nbsp;"
          Text[6890, 6947] chars:[6890, 6947, "Inden … tions"]
        TableCell[6987, 6995] CENTER text:[6987, 6988, " "] textClose:[6994, 6995, "|"]
          Text[6987, 6988] chars:[6987, 6988, " "]
        TableCell[6995, 7006] CENTER text:[6999, 7000, "X"] textClose:[7005, 7006, "|"]
          Text[6999, 7000] chars:[6999, 7000, "X"]
      TableRow[7007, 7149] rowNumber=48
        TableCell[7007, 7130] LEFT textOpen:[7007, 7008, "|"] text:[7009, 7029, "**Code Completions**"] textClose:[7129, 7130, "|"]
          StrongEmphasis[7009, 7029] textOpen:[7009, 7011, "**"] text:[7011, 7027, "Code Completions"] textClose:[7027, 7029, "**"]
            Text[7011, 7027] chars:[7011, 7027, "Code  … tions"]
          Text[7029, 7029]
        TableCell[7130, 7138] CENTER text:[7130, 7131, " "] textClose:[7137, 7138, "|"]
          Text[7130, 7131] chars:[7130, 7131, " "]
        TableCell[7138, 7149] CENTER text:[7142, 7143, "X"] textClose:[7148, 7149, "|"]
          Text[7142, 7143] chars:[7142, 7143, "X"]
      TableRow[7150, 7292] rowNumber=49
        TableCell[7150, 7273] LEFT textOpen:[7150, 7151, "|"] text:[7152, 7244, "&nbsp;&nbsp;&nbsp;&nbsp;Absolute link address completions using https:// and file:// formats"] textClose:[7272, 7273, "|"]
          HtmlEntity[7152, 7158] "&nbsp;"
          HtmlEntity[7158, 7164] "&nbsp;"
          HtmlEntity[7164, 7170] "&nbsp;"
          HtmlEntity[7170, 7176] "&nbsp;"
          Text[7176, 7244] chars:[7176, 7244, "Absol … rmats"]
        TableCell[7273, 7281] CENTER text:[7273, 7274, " "] textClose:[7280, 7281, "|"]
          Text[7273, 7274] chars:[7273, 7274, " "]
        TableCell[7281, 7292] CENTER text:[7285, 7286, "X"] textClose:[7291, 7292, "|"]
          Text[7285, 7286] chars:[7285, 7286, "X"]
      TableRow[7293, 7435] rowNumber=50
        TableCell[7293, 7416] LEFT textOpen:[7293, 7294, "|"] text:[7295, 7365, "&nbsp;&nbsp;&nbsp;&nbsp;Explicit and Image links are GitHub wiki aware"] textClose:[7415, 7416, "|"]
          HtmlEntity[7295, 7301] "&nbsp;"
          HtmlEntity[7301, 7307] "&nbsp;"
          HtmlEntity[7307, 7313] "&nbsp;"
          HtmlEntity[7313, 7319] "&nbsp;"
          Text[7319, 7365] chars:[7319, 7365, "Expli … aware"]
        TableCell[7416, 7424] CENTER text:[7416, 7417, " "] textClose:[7423, 7424, "|"]
          Text[7416, 7417] chars:[7416, 7417, " "]
        TableCell[7424, 7435] CENTER text:[7428, 7429, "X"] textClose:[7434, 7435, "|"]
          Text[7428, 7429] chars:[7428, 7429, "X"]
      TableRow[7436, 7578] rowNumber=51
        TableCell[7436, 7559] LEFT textOpen:[7436, 7437, "|"] text:[7438, 7529, "&nbsp;&nbsp;&nbsp;&nbsp;GitHub Issue # Completions after `issues/` link address and in text"] textClose:[7558, 7559, "|"]
          HtmlEntity[7438, 7444] "&nbsp;"
          HtmlEntity[7444, 7450] "&nbsp;"
          HtmlEntity[7450, 7456] "&nbsp;"
          HtmlEntity[7456, 7462] "&nbsp;"
          Text[7462, 7495] chars:[7462, 7495, "GitHu … fter "]
          Code[7495, 7504] textOpen:[7495, 7496, "`"] text:[7496, 7503, "issues/"] textClose:[7503, 7504, "`"]
            Text[7496, 7503] chars:[7496, 7503, "issues/"]
          Text[7504, 7529] chars:[7504, 7529, " link …  text"]
        TableCell[7559, 7567] CENTER text:[7559, 7560, " "] textClose:[7566, 7567, "|"]
          Text[7559, 7560] chars:[7559, 7560, " "]
        TableCell[7567, 7578] CENTER text:[7571, 7572, "X"] textClose:[7577, 7578, "|"]
          Text[7571, 7572] chars:[7571, 7572, "X"]
      TableRow[7579, 7721] rowNumber=52
        TableCell[7579, 7702] LEFT textOpen:[7579, 7580, "|"] text:[7581, 7668, "&nbsp;&nbsp;&nbsp;&nbsp;GitHub special links: Issues, Pull requests, Graphs, and Pulse."] textClose:[7701, 7702, "|"]
          HtmlEntity[7581, 7587] "&nbsp;"
          HtmlEntity[7587, 7593] "&nbsp;"
          HtmlEntity[7593, 7599] "&nbsp;"
          HtmlEntity[7599, 7605] "&nbsp;"
          Text[7605, 7668] chars:[7605, 7668, "GitHu … ulse."]
        TableCell[7702, 7710] CENTER text:[7702, 7703, " "] textClose:[7709, 7710, "|"]
          Text[7702, 7703] chars:[7702, 7703, " "]
        TableCell[7710, 7721] CENTER text:[7714, 7715, "X"] textClose:[7720, 7721, "|"]
          Text[7714, 7715] chars:[7714, 7715, "X"]
      TableRow[7722, 7864] rowNumber=53
        TableCell[7722, 7845] LEFT textOpen:[7722, 7723, "|"] text:[7724, 7795, "&nbsp;&nbsp;&nbsp;&nbsp;Link address completions for non-markdown files"] textClose:[7844, 7845, "|"]
          HtmlEntity[7724, 7730] "&nbsp;"
          HtmlEntity[7730, 7736] "&nbsp;"
          HtmlEntity[7736, 7742] "&nbsp;"
          HtmlEntity[7742, 7748] "&nbsp;"
          Text[7748, 7795] chars:[7748, 7795, "Link  … files"]
        TableCell[7845, 7853] CENTER text:[7845, 7846, " "] textClose:[7852, 7853, "|"]
          Text[7845, 7846] chars:[7845, 7846, " "]
        TableCell[7853, 7864] CENTER text:[7857, 7858, "X"] textClose:[7863, 7864, "|"]
          Text[7857, 7858] chars:[7857, 7858, "X"]
      TableRow[7865, 8007] rowNumber=54
        TableCell[7865, 7988] LEFT textOpen:[7865, 7866, "|"] text:[7867, 7922, "&nbsp;&nbsp;&nbsp;&nbsp;Emoji text shortcuts completion"] textClose:[7987, 7988, "|"]
          HtmlEntity[7867, 7873] "&nbsp;"
          HtmlEntity[7873, 7879] "&nbsp;"
          HtmlEntity[7879, 7885] "&nbsp;"
          HtmlEntity[7885, 7891] "&nbsp;"
          Text[7891, 7922] chars:[7891, 7922, "Emoji … etion"]
        TableCell[7988, 7996] CENTER text:[7988, 7989, " "] textClose:[7995, 7996, "|"]
          Text[7988, 7989] chars:[7988, 7989, " "]
        TableCell[7996, 8007] CENTER text:[8000, 8001, "X"] textClose:[8006, 8007, "|"]
          Text[8000, 8001] chars:[8000, 8001, "X"]
      TableRow[8008, 8150] rowNumber=55
        TableCell[8008, 8131] LEFT textOpen:[8008, 8009, "|"] text:[8010, 8098, "&nbsp;&nbsp;&nbsp;&nbsp;Java class, field and method completions in inline code elements"] textClose:[8130, 8131, "|"]
          HtmlEntity[8010, 8016] "&nbsp;"
          HtmlEntity[8016, 8022] "&nbsp;"
          HtmlEntity[8022, 8028] "&nbsp;"
          HtmlEntity[8028, 8034] "&nbsp;"
          Text[8034, 8098] chars:[8034, 8098, "Java  … ments"]
        TableCell[8131, 8139] CENTER text:[8131, 8132, " "] textClose:[8138, 8139, "|"]
          Text[8131, 8132] chars:[8131, 8132, " "]
        TableCell[8139, 8150] CENTER text:[8143, 8144, "X"] textClose:[8149, 8150, "|"]
          Text[8143, 8144] chars:[8143, 8144, "X"]
      TableRow[8151, 8293] rowNumber=56
        TableCell[8151, 8274] LEFT textOpen:[8151, 8152, "|"] text:[8153, 8174, "**Intention Actions**"] textClose:[8273, 8274, "|"]
          StrongEmphasis[8153, 8174] textOpen:[8153, 8155, "**"] text:[8155, 8172, "Intention Actions"] textClose:[8172, 8174, "**"]
            Text[8155, 8172] chars:[8155, 8172, "Inten … tions"]
          Text[8174, 8174]
        TableCell[8274, 8282] CENTER text:[8274, 8275, " "] textClose:[8281, 8282, "|"]
          Text[8274, 8275] chars:[8274, 8275, " "]
        TableCell[8282, 8293] CENTER text:[8286, 8287, "X"] textClose:[8292, 8293, "|"]
          Text[8286, 8287] chars:[8286, 8287, "X"]
      TableRow[8294, 8436] rowNumber=57
        TableCell[8294, 8417] LEFT textOpen:[8294, 8295, "|"] text:[8296, 8401, "&nbsp;&nbsp;&nbsp;&nbsp;Change between relative and absolute https:// link addresses via intention action"] textClose:[8416, 8417, "|"]
          HtmlEntity[8296, 8302] "&nbsp;"
          HtmlEntity[8302, 8308] "&nbsp;"
          HtmlEntity[8308, 8314] "&nbsp;"
          HtmlEntity[8314, 8320] "&nbsp;"
          Text[8320, 8401] chars:[8320, 8401, "Chang … ction"]
        TableCell[8417, 8425] CENTER text:[8417, 8418, " "] textClose:[8424, 8425, "|"]
          Text[8417, 8418] chars:[8417, 8418, " "]
        TableCell[8425, 8436] CENTER text:[8429, 8430, "X"] textClose:[8435, 8436, "|"]
          Text[8429, 8430] chars:[8429, 8430, "X"]
      TableRow[8437, 8579] rowNumber=58
        TableCell[8437, 8560] LEFT textOpen:[8437, 8438, "|"] text:[8439, 8506, "&nbsp;&nbsp;&nbsp;&nbsp;Change between wiki links and explicit link"] textClose:[8559, 8560, "|"]
          HtmlEntity[8439, 8445] "&nbsp;"
          HtmlEntity[8445, 8451] "&nbsp;"
          HtmlEntity[8451, 8457] "&nbsp;"
          HtmlEntity[8457, 8463] "&nbsp;"
          Text[8463, 8506] chars:[8463, 8506, "Chang …  link"]
        TableCell[8560, 8568] CENTER text:[8560, 8561, " "] textClose:[8567, 8568, "|"]
          Text[8560, 8561] chars:[8560, 8561, " "]
        TableCell[8568, 8579] CENTER text:[8572, 8573, "X"] textClose:[8578, 8579, "|"]
          Text[8572, 8573] chars:[8572, 8573, "X"]
      TableRow[8580, 8722] rowNumber=59
        TableCell[8580, 8703] LEFT textOpen:[8580, 8581, "|"] text:[8582, 8662, "&nbsp;&nbsp;&nbsp;&nbsp;Intentions for links, wiki links, references and headers"] textClose:[8702, 8703, "|"]
          HtmlEntity[8582, 8588] "&nbsp;"
          HtmlEntity[8588, 8594] "&nbsp;"
          HtmlEntity[8594, 8600] "&nbsp;"
          HtmlEntity[8600, 8606] "&nbsp;"
          Text[8606, 8662] chars:[8606, 8662, "Inten … aders"]
        TableCell[8703, 8711] CENTER text:[8703, 8704, " "] textClose:[8710, 8711, "|"]
          Text[8703, 8704] chars:[8703, 8704, " "]
        TableCell[8711, 8722] CENTER text:[8715, 8716, "X"] textClose:[8721, 8722, "|"]
          Text[8715, 8716] chars:[8715, 8716, "X"]
      TableRow[8723, 8865] rowNumber=60
        TableCell[8723, 8846] LEFT textOpen:[8723, 8724, "|"] text:[8725, 8820, "&nbsp;&nbsp;&nbsp;&nbsp;Intention to format Setext Header marker to match marker length to text"] textClose:[8845, 8846, "|"]
          HtmlEntity[8725, 8731] "&nbsp;"
          HtmlEntity[8731, 8737] "&nbsp;"
          HtmlEntity[8737, 8743] "&nbsp;"
          HtmlEntity[8743, 8749] "&nbsp;"
          Text[8749, 8820] chars:[8749, 8820, "Inten …  text"]
        TableCell[8846, 8854] CENTER text:[8846, 8847, " "] textClose:[8853, 8854, "|"]
          Text[8846, 8847] chars:[8846, 8847, " "]
        TableCell[8854, 8865] CENTER text:[8858, 8859, "X"] textClose:[8864, 8865, "|"]
          Text[8858, 8859] chars:[8858, 8859, "X"]
      TableRow[8866, 9008] rowNumber=61
        TableCell[8866, 8989] LEFT textOpen:[8866, 8867, "|"] text:[8868, 8934, "&nbsp;&nbsp;&nbsp;&nbsp;Intention to swap Setext/Atx header format"] textClose:[8988, 8989, "|"]
          HtmlEntity[8868, 8874] "&nbsp;"
          HtmlEntity[8874, 8880] "&nbsp;"
          HtmlEntity[8880, 8886] "&nbsp;"
          HtmlEntity[8886, 8892] "&nbsp;"
          Text[8892, 8934] chars:[8892, 8934, "Inten … ormat"]
        TableCell[8989, 8997] CENTER text:[8989, 8990, " "] textClose:[8996, 8997, "|"]
          Text[8989, 8990] chars:[8989, 8990, " "]
        TableCell[8997, 9008] CENTER text:[9001, 9002, "X"] textClose:[9007, 9008, "|"]
          Text[9001, 9002] chars:[9001, 9002, "X"]
      TableRow[9009, 9151] rowNumber=62
        TableCell[9009, 9132] LEFT textOpen:[9009, 9010, "|"] text:[9011, 9079, "&nbsp;&nbsp;&nbsp;&nbsp;Update table of contents quick fix intention"] textClose:[9131, 9132, "|"]
          HtmlEntity[9011, 9017] "&nbsp;"
          HtmlEntity[9017, 9023] "&nbsp;"
          HtmlEntity[9023, 9029] "&nbsp;"
          HtmlEntity[9029, 9035] "&nbsp;"
          Text[9035, 9079] chars:[9035, 9079, "Updat … ntion"]
        TableCell[9132, 9140] CENTER text:[9132, 9133, " "] textClose:[9139, 9140, "|"]
          Text[9132, 9133] chars:[9132, 9133, " "]
        TableCell[9140, 9151] CENTER text:[9144, 9145, "X"] textClose:[9150, 9151, "|"]
          Text[9144, 9145] chars:[9144, 9145, "X"]
      TableRow[9152, 9294] rowNumber=63
        TableCell[9152, 9275] LEFT textOpen:[9152, 9153, "|"] text:[9154, 9247, "&nbsp;&nbsp;&nbsp;&nbsp;Intention to edit Table of Contents style options dialog with preview"] textClose:[9274, 9275, "|"]
          HtmlEntity[9154, 9160] "&nbsp;"
          HtmlEntity[9160, 9166] "&nbsp;"
          HtmlEntity[9166, 9172] "&nbsp;"
          HtmlEntity[9172, 9178] "&nbsp;"
          Text[9178, 9247] chars:[9178, 9247, "Inten … eview"]
        TableCell[9275, 9283] CENTER text:[9275, 9276, " "] textClose:[9282, 9283, "|"]
          Text[9275, 9276] chars:[9275, 9276, " "]
        TableCell[9283, 9294] CENTER text:[9287, 9288, "X"] textClose:[9293, 9294, "|"]
          Text[9287, 9288] chars:[9287, 9288, "X"]
      TableRow[9295, 9437] rowNumber=64
        TableCell[9295, 9418] LEFT textOpen:[9295, 9296, "|"] text:[9297, 9312, "**Refactoring**"] textClose:[9417, 9418, "|"]
          StrongEmphasis[9297, 9312] textOpen:[9297, 9299, "**"] text:[9299, 9310, "Refactoring"] textClose:[9310, 9312, "**"]
            Text[9299, 9310] chars:[9299, 9310, "Refac … oring"]
          Text[9312, 9312]
        TableCell[9418, 9426] CENTER text:[9418, 9419, " "] textClose:[9425, 9426, "|"]
          Text[9418, 9419] chars:[9418, 9419, " "]
        TableCell[9426, 9437] CENTER text:[9430, 9431, "X"] textClose:[9436, 9437, "|"]
          Text[9430, 9431] chars:[9430, 9431, "X"]
      TableRow[9438, 9580] rowNumber=65
        TableCell[9438, 9561] LEFT textOpen:[9438, 9439, "|"] text:[9440, 9559, "&nbsp;&nbsp;&nbsp;&nbsp;Automatic change from wiki link to explicit link when link target file is moved out of the wiki"] textClose:[9560, 9561, "|"]
          HtmlEntity[9440, 9446] "&nbsp;"
          HtmlEntity[9446, 9452] "&nbsp;"
          HtmlEntity[9452, 9458] "&nbsp;"
          HtmlEntity[9458, 9464] "&nbsp;"
          Text[9464, 9559] chars:[9464, 9559, "Autom …  wiki"]
        TableCell[9561, 9569] CENTER text:[9561, 9562, " "] textClose:[9568, 9569, "|"]
          Text[9561, 9562] chars:[9561, 9562, " "]
        TableCell[9569, 9580] CENTER text:[9573, 9574, "X"] textClose:[9579, 9580, "|"]
          Text[9573, 9574] chars:[9573, 9574, "X"]
      TableRow[9581, 9723] rowNumber=66
        TableCell[9581, 9704] LEFT textOpen:[9581, 9582, "|"] text:[9583, 9691, "&nbsp;&nbsp;&nbsp;&nbsp;File move refactoring of contained links. This completes the refactoring feature set"] textClose:[9703, 9704, "|"]
          HtmlEntity[9583, 9589] "&nbsp;"
          HtmlEntity[9589, 9595] "&nbsp;"
          HtmlEntity[9595, 9601] "&nbsp;"
          HtmlEntity[9601, 9607] "&nbsp;"
          Text[9607, 9691] chars:[9607, 9691, "File  … e set"]
        TableCell[9704, 9712] CENTER text:[9704, 9705, " "] textClose:[9711, 9712, "|"]
          Text[9704, 9705] chars:[9704, 9705, " "]
        TableCell[9712, 9723] CENTER text:[9716, 9717, "X"] textClose:[9722, 9723, "|"]
          Text[9716, 9717] chars:[9716, 9717, "X"]
      TableRow[9724, 9866] rowNumber=67
        TableCell[9724, 9847] LEFT textOpen:[9724, 9725, "|"] text:[9726, 9830, "&nbsp;&nbsp;&nbsp;&nbsp;Refactoring for /, https:// and file:// absolute link addresses to project files"] textClose:[9846, 9847, "|"]
          HtmlEntity[9726, 9732] "&nbsp;"
          HtmlEntity[9732, 9738] "&nbsp;"
          HtmlEntity[9738, 9744] "&nbsp;"
          HtmlEntity[9744, 9750] "&nbsp;"
          Text[9750, 9830] chars:[9750, 9830, "Refac … files"]
        TableCell[9847, 9855] CENTER text:[9847, 9848, " "] textClose:[9854, 9855, "|"]
          Text[9847, 9848] chars:[9847, 9848, " "]
        TableCell[9855, 9866] CENTER text:[9859, 9860, "X"] textClose:[9865, 9866, "|"]
          Text[9859, 9860] chars:[9859, 9860, "X"]
      TableRow[9867, 10009] rowNumber=68
        TableCell[9867, 9990] LEFT textOpen:[9867, 9868, "|"] text:[9869, 9969, "&nbsp;&nbsp;&nbsp;&nbsp;Refactoring of header text with update to referencing anchor link references"] textClose:[9989, 9990, "|"]
          HtmlEntity[9869, 9875] "&nbsp;"
          HtmlEntity[9875, 9881] "&nbsp;"
          HtmlEntity[9881, 9887] "&nbsp;"
          HtmlEntity[9887, 9893] "&nbsp;"
          Text[9893, 9969] chars:[9893, 9969, "Refac … ences"]
        TableCell[9990, 9998] CENTER text:[9990, 9991, " "] textClose:[9997, 9998, "|"]
          Text[9990, 9991] chars:[9990, 9991, " "]
        TableCell[9998, 10009] CENTER text:[10002, 10003, "X"] textClose:[10008, 10009, "|"]
          Text[10002, 10003] chars:[10002, 10003, "X"]
      TableRow[10010, 10152] rowNumber=69
        TableCell[10010, 10133] LEFT textOpen:[10010, 10011, "|"] text:[10012, 10107, "&nbsp;&nbsp;&nbsp;&nbsp;Anchor link reference refactoring with update to referenced header text"] textClose:[10132, 10133, "|"]
          HtmlEntity[10012, 10018] "&nbsp;"
          HtmlEntity[10018, 10024] "&nbsp;"
          HtmlEntity[10024, 10030] "&nbsp;"
          HtmlEntity[10030, 10036] "&nbsp;"
          Text[10036, 10107] chars:[10036, 10107, "Ancho …  text"]
        TableCell[10133, 10141] CENTER text:[10133, 10134, " "] textClose:[10140, 10141, "|"]
          Text[10133, 10134] chars:[10133, 10134, " "]
        TableCell[10141, 10152] CENTER text:[10145, 10146, "X"] textClose:[10151, 10152, "|"]
          Text[10145, 10146] chars:[10145, 10146, "X"]
````````````````````````````````


## GFM options

invalid table:

```````````````````````````````` example(GFM options: 1) options(gfm)
| A | B | C |
|-----------|
| a | b | c |
| b | a | c |
.
| A | B | C | |-----------| | a | b | c | | b | a | c |

.
Document[0, 55]
  Paragraph[0, 55]
    Text[0, 13] chars:[0, 13, "| A | … | C |"]
    SoftLineBreak[13, 14]
    Text[14, 27] chars:[14, 27, "|---- … ----|"]
    SoftLineBreak[27, 28]
    Text[28, 41] chars:[28, 41, "| a | … | c |"]
    SoftLineBreak[41, 42]
    Text[42, 55] chars:[42, 55, "| b | … | c |"]
````````````````````````````````


invalid table:

```````````````````````````````` example(GFM options: 2) options(gfm)
| A | B | C |
| a | b | c |
| b | a | c |
.
| A | B | C | | a | b | c | | b | a | c |

.
Document[0, 41]
  Paragraph[0, 41]
    Text[0, 13] chars:[0, 13, "| A | … | C |"]
    SoftLineBreak[13, 14]
    Text[14, 27] chars:[14, 27, "| a | … | c |"]
    SoftLineBreak[27, 28]
    Text[28, 41] chars:[28, 41, "| b | … | c |"]
````````````````````````````````


## WikiLinks

Converts wikilink of the forms: [[link]], [[link|text]] and [[text|link]] to links in the HTML
page.

simple wiki link

```````````````````````````````` example WikiLinks: 1
[[wiki link]]
.
[wiki link|wiki-link]

.
Document[0, 13]
  Paragraph[0, 13]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[11, 13, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
````````````````````````````````


wiki link with text

```````````````````````````````` example WikiLinks: 2
[[wiki text|wiki link]]
.
[wiki text|wiki-link]

.
Document[0, 23]
  Paragraph[0, 23]
    WikiLink[0, 23] linkOpen:[0, 2, "[["] link:[12, 21, "wiki link"] pageRef:[12, 21, "wiki link"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[21, 23, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
````````````````````````````````


wiki link with text, links first option

```````````````````````````````` example(WikiLinks: 3) options(links-first)
[[wiki link|wiki text]]
.
[wiki text|wiki-link]

.
Document[0, 23]
  Paragraph[0, 23]
    WikiLink[0, 23] linkOpen:[0, 2, "[["] text:[12, 21, "wiki text"] textSep:[11, 12, "|"] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[21, 23, "]]"]
      Text[12, 21] chars:[12, 21, "wiki text"]
````````````````````````````````


With empty anchor ref

```````````````````````````````` example WikiLinks: 4
[[wiki link#]] 
.
[wiki link|wiki-link#]

.
Document[0, 15]
  Paragraph[0, 15]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 12] text:[2, 11, "wiki link"] linkClose:[12, 14, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
````````````````````````````````


With Anchor ref

```````````````````````````````` example WikiLinks: 5
[[wiki link#anchor-ref]] 
.
[wiki link|wiki-link#anchor-ref]

.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] text:[2, 11, "wiki link"] linkClose:[22, 24, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example WikiLinks: 6
[[wiki text|wiki link#]] 
.
[wiki text|wiki-link#]

.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[12, 22, "wiki link#"] pageRef:[12, 21, "wiki link"] anchorMarker:[21, 22, "#"] anchorRef:[22, 22] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[22, 24, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
````````````````````````````````


With text, anchor ref

```````````````````````````````` example WikiLinks: 7
[[wiki text|wiki link#anchor-ref]] 
.
[wiki text|wiki-link#anchor-ref]

.
Document[0, 35]
  Paragraph[0, 35]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] link:[12, 32, "wiki link#anchor-ref"] pageRef:[12, 21, "wiki link"] anchorMarker:[21, 22, "#"] anchorRef:[22, 32, "anchor-ref"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[32, 34, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
````````````````````````````````


## Ins Tests

```````````````````````````````` example Ins Tests: 1
++Paragraph with *emphasis* and __strong emphasis__++                                                             
.
+Paragraph with _emphasis_ and *strong emphasis*+

.
Document[0, 114]
  Paragraph[0, 114]
    Ins[0, 53] textOpen:[0, 2, "++"] text:[2, 51, "Paragraph with *emphasis* and __strong emphasis__"] textClose:[51, 53, "++"]
      Text[2, 17] chars:[2, 17, "Parag … with "]
      Emphasis[17, 27] textOpen:[17, 18, "*"] text:[18, 26, "emphasis"] textClose:[26, 27, "*"]
        Text[18, 26] chars:[18, 26, "emphasis"]
      Text[27, 32] chars:[27, 32, " and "]
      StrongEmphasis[32, 51] textOpen:[32, 34, "__"] text:[34, 49, "strong emphasis"] textClose:[49, 51, "__"]
        Text[34, 49] chars:[34, 49, "stron … hasis"]
````````````````````````````````


```````````````````````````````` example Ins Tests: 2
> underline ++that++                                                           
.
{quote}
underline +that+
{quote}

.
Document[0, 79]
  BlockQuote[0, 79] marker:[0, 1, ">"]
    Paragraph[2, 79]
      Text[2, 12] chars:[2, 12, "underline "]
      Ins[12, 20] textOpen:[12, 14, "++"] text:[14, 18, "that"] textClose:[18, 20, "++"]
        Text[14, 18] chars:[14, 18, "that"]
````````````````````````````````


## Subscript Tests

```````````````````````````````` example Subscript Tests: 1
~Paragraph with *emphasis* and __strong emphasis__~
.
~Paragraph with _emphasis_ and *strong emphasis*~

.
Document[0, 51]
  Paragraph[0, 51]
    Subscript[0, 51] textOpen:[0, 1, "~"] text:[1, 50, "Parag … raph with *emphasis* and __strong emphasis__"] textClose:[50, 51, "~"]
      Text[1, 16] chars:[1, 16, "Parag … with "]
      Emphasis[16, 26] textOpen:[16, 17, "*"] text:[17, 25, "emphasis"] textClose:[25, 26, "*"]
        Text[17, 25] chars:[17, 25, "emphasis"]
      Text[26, 31] chars:[26, 31, " and "]
      StrongEmphasis[31, 50] textOpen:[31, 33, "__"] text:[33, 48, "strong emphasis"] textClose:[48, 50, "__"]
        Text[33, 48] chars:[33, 48, "stron … hasis"]
````````````````````````````````


```````````````````````````````` example Subscript Tests: 2
> underline ~that~                                                           
.
{quote}
underline ~that~
{quote}

.
Document[0, 77]
  BlockQuote[0, 77] marker:[0, 1, ">"]
    Paragraph[2, 77]
      Text[2, 12] chars:[2, 12, "underline "]
      Subscript[12, 18] textOpen:[12, 13, "~"] text:[13, 17, "that"] textClose:[17, 18, "~"]
        Text[13, 17] chars:[13, 17, "that"]
````````````````````````````````


