---
title: Macros Extension
author: Vladimir Schneider
version: 1.0
date: '2018-09-07'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Macros

Converts macros text to macro definitions and macro references.

Macro Definitions are block elements which can contain any markdown element

Macro definitions start with a line of `>>>macroName` where macro name consists of a-z, A-Z,
0-9, - or _ and is terminated by a line with `<<<`. All text between these lines is parsed as
markdown blocks. Macro definition blocks can only be defined at the document level without any
indentation and are not rendered in the output.

Macro reference is an inline element of the form `<<<macroName>>>` and is replaced by the macro
definition rendered content, including any block elements. This implies that it is possible to
include block elements where they are normally not possible using plain markdown.

When a macro definition contains a single paragraph node then it will be rendered as text
contained by the paragraph without the `<p></p>` wrapper to allow plain text to be rendered as
an inline text.

If a macro reference contains an undefined `macroName` then the node will be rendered as plain
text consisting of `<<<macroName>>>`

Recursive macro references are resolved by stopping macro expansion on the first macro reference
which refers to a macro definition which is currently being expanded. The first recursive macro
reference will result in no expansion.

invalid macro names are ignored

```````````````````````````````` example Macros: 1
>>>macro name
<<<

.
<blockquote>
  <blockquote>
    <blockquote>
      <p>macro name
      &lt;&lt;&lt;</p>
    </blockquote>
  </blockquote>
</blockquote>
.
Document[0, 19]
  BlockQuote[0, 18] marker:[0, 1, ">"]
    BlockQuote[1, 18] marker:[1, 2, ">"]
      BlockQuote[2, 18] marker:[2, 3, ">"]
        Paragraph[3, 18]
          Text[3, 13] chars:[3, 13, "macro name"]
          SoftLineBreak[13, 14]
          Text[14, 17] chars:[14, 17, "<<<"]
````````````````````````````````


cannot be indented

```````````````````````````````` example Macros: 2
 >>>macro
<<<

.
<blockquote>
  <blockquote>
    <blockquote>
      <p>macro
      &lt;&lt;&lt;</p>
    </blockquote>
  </blockquote>
</blockquote>
.
Document[0, 15]
  BlockQuote[1, 14] marker:[1, 2, ">"]
    BlockQuote[2, 14] marker:[2, 3, ">"]
      BlockQuote[3, 14] marker:[3, 4, ">"]
        Paragraph[4, 14]
          Text[4, 9] chars:[4, 9, "macro"]
          SoftLineBreak[9, 10]
          Text[10, 13] chars:[10, 13, "<<<"]
````````````````````````````````


cannot be in other elements

```````````````````````````````` example Macros: 3
1. list item

   >>>macro
   <<<

.
<ol>
  <li>
    <p>list item</p>
    <blockquote>
      <blockquote>
        <blockquote>
          <p>macro
          &lt;&lt;&lt;</p>
        </blockquote>
      </blockquote>
    </blockquote>
  </li>
</ol>
.
Document[0, 34]
  OrderedList[0, 33] isLoose delimiter:'.'
    OrderedListItem[0, 33] open:[0, 2, "1."] isLoose hadBlankLineAfter
      Paragraph[3, 13] isTrailingBlankLine
        Text[3, 12] chars:[3, 12, "list item"]
      BlockQuote[17, 33] marker:[17, 18, ">"]
        BlockQuote[18, 33] marker:[18, 19, ">"]
          BlockQuote[19, 33] marker:[19, 20, ">"]
            Paragraph[20, 33]
              Text[20, 25] chars:[20, 25, "macro"]
              SoftLineBreak[25, 26]
              Text[29, 32] chars:[29, 32, "<<<"]
````````````````````````````````


takes valid characters

```````````````````````````````` example Macros: 4
>>>abc-xyzABC_XYZ09
<<<

.
.
Document[0, 25]
  MacroDefinitionBlock[0, 24] open:[0, 3, ">>>"] name:[3, 19, "abc-xyzABC_XYZ09"] openTrail:[19, 19] close:[20, 23, "<<<"] closeTrail:[23, 24, "\n"]
````````````````````````````````


empty macro content is allowed

```````````````````````````````` example Macros: 5
>>>macro
<<<

Plain text <<<macro>>>
.
<p>Plain text </p>
.
Document[0, 36]
  MacroDefinitionBlock[0, 13] open:[0, 3, ">>>"] name:[3, 8, "macro"] openTrail:[8, 8] close:[9, 12, "<<<"] closeTrail:[12, 13, "\n"]
  Paragraph[14, 36]
    Text[14, 25] chars:[14, 25, "Plain … text "]
    MacroReference[25, 36] textOpen:[25, 28, "<<<"] text:[28, 33, "macro"] textClose:[33, 36, ">>>"]
````````````````````````````````


simple text

```````````````````````````````` example Macros: 6
>>>macro
simple text
<<<

Plain text <<<macro>>>
.
<p>Plain text simple text</p>
.
Document[0, 48]
  MacroDefinitionBlock[0, 25] open:[0, 3, ">>>"] name:[3, 8, "macro"] openTrail:[8, 8] close:[21, 24, "<<<"] closeTrail:[24, 25, "\n"]
    Paragraph[9, 21]
      Text[9, 20] chars:[9, 20, "simpl …  text"]
  Paragraph[26, 48]
    Text[26, 37] chars:[26, 37, "Plain … text "]
    MacroReference[37, 48] textOpen:[37, 40, "<<<"] text:[40, 45, "macro"] textClose:[45, 48, ">>>"]
````````````````````````````````


complex content

```````````````````````````````` example Macros: 7
>>>macro
* list item 1
* list item 2


| heading     |
|:------------|
| column data |
<<<

Plain text <<<macro>>>
.
<p>Plain text 
<ul>
  <li>list item 1</li>
  <li>list item 2</li>
</ul>
<table>
  <thead>
    <tr><th align="left">heading</th></tr>
  </thead>
  <tbody>
    <tr><td align="left">column data</td></tr>
  </tbody>
</table>
</p>
.
Document[0, 114]
  MacroDefinitionBlock[0, 91] open:[0, 3, ">>>"] name:[3, 8, "macro"] openTrail:[8, 8] close:[87, 90, "<<<"] closeTrail:[90, 91, "\n"]
    BulletList[9, 37] isTight
      BulletListItem[9, 23] open:[9, 10, "*"] isTight
        Paragraph[11, 23]
          Text[11, 22] chars:[11, 22, "list  … tem 1"]
      BulletListItem[23, 37] open:[23, 24, "*"] isTight hadBlankLineAfter
        Paragraph[25, 37] isTrailingBlankLine
          Text[25, 36] chars:[25, 36, "list  … tem 2"]
    TableBlock[39, 87]
      TableHead[39, 54]
        TableRow[39, 54] rowNumber=1
          TableCell[39, 54] LEFT header textOpen:[39, 40, "|"] text:[41, 48, "heading"] textClose:[53, 54, "|"]
            Text[41, 48] chars:[41, 48, "heading"]
      TableSeparator[55, 70]
        TableRow[55, 70]
          TableCell[55, 70] LEFT textOpen:[55, 56, "|"] text:[56, 69, ":------------"] textClose:[69, 70, "|"]
            Text[56, 69] chars:[56, 69, ":---- … -----"]
      TableBody[71, 86]
        TableRow[71, 86] rowNumber=1
          TableCell[71, 86] LEFT textOpen:[71, 72, "|"] text:[73, 84, "column data"] textClose:[85, 86, "|"]
            Text[73, 84] chars:[73, 84, "colum …  data"]
  Paragraph[92, 114]
    Text[92, 103] chars:[92, 103, "Plain … text "]
    MacroReference[103, 114] textOpen:[103, 106, "<<<"] text:[106, 111, "macro"] textClose:[111, 114, ">>>"]
````````````````````````````````


macro tables

```````````````````````````````` example Macros: 8
>>>macro
| heading     |
|:------------|
| column `data` |
| column **data** 2 |
<<<

<<<macro>>>
.
<p>
<table>
  <thead>
    <tr><th align="left">heading</th></tr>
  </thead>
  <tbody>
    <tr><td align="left">column <code>data</code></td></tr>
    <tr><td align="left">column <strong>data</strong> 2</td></tr>
  </tbody>
</table>
</p>
.
Document[0, 97]
  MacroDefinitionBlock[0, 85] open:[0, 3, ">>>"] name:[3, 8, "macro"] openTrail:[8, 8] close:[81, 84, "<<<"] closeTrail:[84, 85, "\n"]
    TableBlock[9, 81]
      TableHead[9, 24]
        TableRow[9, 24] rowNumber=1
          TableCell[9, 24] LEFT header textOpen:[9, 10, "|"] text:[11, 18, "heading"] textClose:[23, 24, "|"]
            Text[11, 18] chars:[11, 18, "heading"]
      TableSeparator[25, 40]
        TableRow[25, 40]
          TableCell[25, 40] LEFT textOpen:[25, 26, "|"] text:[26, 39, ":------------"] textClose:[39, 40, "|"]
            Text[26, 39] chars:[26, 39, ":---- … -----"]
      TableBody[41, 80]
        TableRow[41, 58] rowNumber=1
          TableCell[41, 58] LEFT textOpen:[41, 42, "|"] text:[43, 56, "column `data`"] textClose:[57, 58, "|"]
            Text[43, 50] chars:[43, 50, "column "]
            Code[50, 56] textOpen:[50, 51, "`"] text:[51, 55, "data"] textClose:[55, 56, "`"]
              Text[51, 55] chars:[51, 55, "data"]
            Text[56, 56]
        TableRow[59, 80] rowNumber=2
          TableCell[59, 80] LEFT textOpen:[59, 60, "|"] text:[61, 78, "column **data** 2"] textClose:[79, 80, "|"]
            Text[61, 68] chars:[61, 68, "column "]
            StrongEmphasis[68, 76] textOpen:[68, 70, "**"] text:[70, 74, "data"] textClose:[74, 76, "**"]
              Text[70, 74] chars:[70, 74, "data"]
            Text[76, 78] chars:[76, 78, " 2"]
  Paragraph[86, 97]
    MacroReference[86, 97] textOpen:[86, 89, "<<<"] text:[89, 94, "macro"] textClose:[94, 97, ">>>"]
````````````````````````````````


complex content for tables

```````````````````````````````` example Macros: 9
>>>macro
| heading     |
|:------------|
| column `data` |
| column **data** 2 |
<<<

| outer first  | outer heading |
|:-------------|:--------------|
| Regular Text | <<<macro>>>   |
.
<table>
  <thead>
    <tr><th align="left">outer first</th><th align="left">outer heading</th></tr>
  </thead>
  <tbody>
    <tr><td align="left">Regular Text</td><td align="left">
    <table>
      <thead>
        <tr><th align="left">heading</th></tr>
      </thead>
      <tbody>
        <tr><td align="left">column <code>data</code></td></tr>
        <tr><td align="left">column <strong>data</strong> 2</td></tr>
      </tbody>
    </table>
    </td></tr>
  </tbody>
</table>
.
Document[0, 184]
  MacroDefinitionBlock[0, 85] open:[0, 3, ">>>"] name:[3, 8, "macro"] openTrail:[8, 8] close:[81, 84, "<<<"] closeTrail:[84, 85, "\n"]
    TableBlock[9, 81]
      TableHead[9, 24]
        TableRow[9, 24] rowNumber=1
          TableCell[9, 24] LEFT header textOpen:[9, 10, "|"] text:[11, 18, "heading"] textClose:[23, 24, "|"]
            Text[11, 18] chars:[11, 18, "heading"]
      TableSeparator[25, 40]
        TableRow[25, 40]
          TableCell[25, 40] LEFT textOpen:[25, 26, "|"] text:[26, 39, ":------------"] textClose:[39, 40, "|"]
            Text[26, 39] chars:[26, 39, ":---- … -----"]
      TableBody[41, 80]
        TableRow[41, 58] rowNumber=1
          TableCell[41, 58] LEFT textOpen:[41, 42, "|"] text:[43, 56, "column `data`"] textClose:[57, 58, "|"]
            Text[43, 50] chars:[43, 50, "column "]
            Code[50, 56] textOpen:[50, 51, "`"] text:[51, 55, "data"] textClose:[55, 56, "`"]
              Text[51, 55] chars:[51, 55, "data"]
            Text[56, 56]
        TableRow[59, 80] rowNumber=2
          TableCell[59, 80] LEFT textOpen:[59, 60, "|"] text:[61, 78, "column **data** 2"] textClose:[79, 80, "|"]
            Text[61, 68] chars:[61, 68, "column "]
            StrongEmphasis[68, 76] textOpen:[68, 70, "**"] text:[70, 74, "data"] textClose:[74, 76, "**"]
              Text[70, 74] chars:[70, 74, "data"]
            Text[76, 78] chars:[76, 78, " 2"]
  TableBlock[86, 184]
    TableHead[86, 118]
      TableRow[86, 118] rowNumber=1
        TableCell[86, 102] LEFT header textOpen:[86, 87, "|"] text:[88, 99, "outer first"] textClose:[101, 102, "|"]
          Text[88, 99] chars:[88, 99, "outer … first"]
        TableCell[102, 118] LEFT header text:[103, 116, "outer heading"] textClose:[117, 118, "|"]
          Text[103, 116] chars:[103, 116, "outer … ading"]
    TableSeparator[119, 151]
      TableRow[119, 151]
        TableCell[119, 135] LEFT textOpen:[119, 120, "|"] text:[120, 134, ":-------------"] textClose:[134, 135, "|"]
          Text[120, 134] chars:[120, 134, ":---- … -----"]
        TableCell[135, 151] LEFT text:[135, 150, ":--------------"] textClose:[150, 151, "|"]
          Text[135, 150] chars:[135, 150, ":---- … -----"]
    TableBody[152, 184]
      TableRow[152, 184] rowNumber=1
        TableCell[152, 168] LEFT textOpen:[152, 153, "|"] text:[154, 166, "Regular Text"] textClose:[167, 168, "|"]
          Text[154, 166] chars:[154, 166, "Regul …  Text"]
        TableCell[168, 184] LEFT text:[169, 180, "<<<macro>>>"] textClose:[183, 184, "|"]
          MacroReference[169, 180] textOpen:[169, 172, "<<<"] text:[172, 177, "macro"] textClose:[177, 180, ">>>"]
          Text[180, 180]
````````````````````````````````


GitLab multi-line block quotes

```````````````````````````````` example Macros: 10
>>>macro
>>>
Block Quote
>>>
<<<

Plain text <<<macro>>>
.
<p>Plain text 
<blockquote>
  <p>Block Quote</p>
</blockquote>
</p>
.
Document[0, 56]
  MacroDefinitionBlock[0, 33] open:[0, 3, ">>>"] name:[3, 8, "macro"] openTrail:[8, 8] close:[29, 32, "<<<"] closeTrail:[32, 33, "\n"]
    GitLabBlockQuote[9, 29] open:[9, 12, ">>>"] openTrail:[12, 13, "\n"] close:[25, 28, ">>>"] closeTrail:[28, 29, "\n"]
      Paragraph[13, 25]
        Text[13, 24] chars:[13, 24, "Block … Quote"]
  Paragraph[34, 56]
    Text[34, 45] chars:[34, 45, "Plain … text "]
    MacroReference[45, 56] textOpen:[45, 48, "<<<"] text:[48, 53, "macro"] textClose:[53, 56, ">>>"]
````````````````````````````````


Nested GitLab multi-line block quotes

```````````````````````````````` example Macros: 11
>>>macro
>>>
Block Quote
>>>
Nested Block Quote
>>>
>>>
<<<

Plain text <<<macro>>>
.
<p>Plain text 
<blockquote>
  <p>Block Quote</p>
</blockquote>
<p>Nested Block Quote</p>
<blockquote>
</blockquote>
</p>
.
Document[0, 83]
  MacroDefinitionBlock[0, 60] open:[0, 3, ">>>"] name:[3, 8, "macro"] openTrail:[8, 8] close:[56, 59, "<<<"] closeTrail:[59, 60, "\n"]
    GitLabBlockQuote[9, 29] open:[9, 12, ">>>"] openTrail:[12, 13, "\n"] close:[25, 28, ">>>"] closeTrail:[28, 29, "\n"]
      Paragraph[13, 25]
        Text[13, 24] chars:[13, 24, "Block … Quote"]
    Paragraph[29, 48]
      Text[29, 47] chars:[29, 47, "Neste … Quote"]
    GitLabBlockQuote[48, 56] open:[48, 51, ">>>"] openTrail:[51, 52, "\n"] close:[52, 55, ">>>"] closeTrail:[55, 56, "\n"]
  Paragraph[61, 83]
    Text[61, 72] chars:[61, 72, "Plain … text "]
    MacroReference[72, 83] textOpen:[72, 75, "<<<"] text:[75, 80, "macro"] textClose:[80, 83, ">>>"]
````````````````````````````````


## Keep Type

```````````````````````````````` example(Keep Type: 1) options(references-keep-first)
>>>macro1
simple 1/1 text
<<<

Plain text <<<macro3>>>

>>>macro2
simple 2 text
<<<

Plain text <<<macro1>>>

>>>macro3
simple 3 text
<<<

>>>macro1
simple 1/2 text
<<<

.
<p>Plain text simple 3 text</p>
<p>Plain text simple 1/1 text</p>
````````````````````````````````


```````````````````````````````` example(Keep Type: 2) options(references-keep-last)
>>>macro1
simple 1/1 text
<<<

Plain text <<<macro3>>>

>>>macro2
simple 2 text
<<<

Plain text <<<macro1>>>

>>>macro3
simple 3 text
<<<

>>>macro1
simple 1/2 text
<<<

.
<p>Plain text simple 3 text</p>
<p>Plain text simple 1/2 text</p>
````````````````````````````````


## Recursive

Recursion cut short

```````````````````````````````` example Recursive: 1
>>>macro1
Macro 1
<<<macro2>>>
<<<

>>>macro2
Macro 2
<<<macro1>>>
<<<

Plain text <<<macro1>>>

Plain text <<<macro2>>>
.
<p>Plain text Macro 1
Macro 2
</p>
<p>Plain text Macro 2
Macro 1
</p>
.
Document[0, 120]
  MacroDefinitionBlock[0, 35] open:[0, 3, ">>>"] name:[3, 9, "macro1"] openTrail:[9, 9] close:[31, 34, "<<<"] closeTrail:[34, 35, "\n"]
    Paragraph[10, 31]
      Text[10, 17] chars:[10, 17, "Macro 1"]
      SoftLineBreak[17, 18]
      MacroReference[18, 30] textOpen:[18, 21, "<<<"] text:[21, 27, "macro2"] textClose:[27, 30, ">>>"]
  MacroDefinitionBlock[36, 71] open:[36, 39, ">>>"] name:[39, 45, "macro2"] openTrail:[45, 45] close:[67, 70, "<<<"] closeTrail:[70, 71, "\n"]
    Paragraph[46, 67]
      Text[46, 53] chars:[46, 53, "Macro 2"]
      SoftLineBreak[53, 54]
      MacroReference[54, 66] textOpen:[54, 57, "<<<"] text:[57, 63, "macro1"] textClose:[63, 66, ">>>"]
  Paragraph[72, 96] isTrailingBlankLine
    Text[72, 83] chars:[72, 83, "Plain … text "]
    MacroReference[83, 95] textOpen:[83, 86, "<<<"] text:[86, 92, "macro1"] textClose:[92, 95, ">>>"]
  Paragraph[97, 120]
    Text[97, 108] chars:[97, 108, "Plain … text "]
    MacroReference[108, 120] textOpen:[108, 111, "<<<"] text:[111, 117, "macro2"] textClose:[117, 120, ">>>"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
>>>macro
>>>
Block Quote
>>>
Nested Block Quote
>>>
>>>
<<<

Plain text <<<macro>>>
.
<p md-pos="61-83">Plain text 
<blockquote>
  <p md-pos="13-25">Block Quote</p>
</blockquote>
<p md-pos="29-48">Nested Block Quote</p>
<blockquote>
</blockquote>
</p>
.
Document[0, 83]
  MacroDefinitionBlock[0, 60] open:[0, 3, ">>>"] name:[3, 8, "macro"] openTrail:[8, 8] close:[56, 59, "<<<"] closeTrail:[59, 60, "\n"]
    GitLabBlockQuote[9, 29] open:[9, 12, ">>>"] openTrail:[12, 13, "\n"] close:[25, 28, ">>>"] closeTrail:[28, 29, "\n"]
      Paragraph[13, 25]
        Text[13, 24] chars:[13, 24, "Block … Quote"]
    Paragraph[29, 48]
      Text[29, 47] chars:[29, 47, "Neste … Quote"]
    GitLabBlockQuote[48, 56] open:[48, 51, ">>>"] openTrail:[51, 52, "\n"] close:[52, 55, ">>>"] closeTrail:[55, 56, "\n"]
  Paragraph[61, 83]
    Text[61, 72] chars:[61, 72, "Plain … text "]
    MacroReference[72, 83] textOpen:[72, 75, "<<<"] text:[75, 80, "macro"] textClose:[80, 83, ">>>"]
````````````````````````````````


