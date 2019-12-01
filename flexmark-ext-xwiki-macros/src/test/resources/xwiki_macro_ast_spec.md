---
title: Macros Extension Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Macros

Converts macros text to macros nodes.

not a block macro

```````````````````````````````` example Macros: 1
 {{macro}}
content
{{/macro}}
.
<p>{{macro}}
content
{{/macro}}</p>
.
Document[0, 29]
  Paragraph[1, 29]
    Macro[1, 29] open:[1, 3, "{{"] name:[3, 8, "macro"] close:[8, 10, "}}"] macroContent:[10, 19, "\ncontent\n"]
      SoftLineBreak[10, 11]
      Text[11, 18] chars:[11, 18, "content"]
      SoftLineBreak[18, 19]
      MacroClose[19, 29] nameOpen:[19, 22, "{{/"] name:[22, 27, "macro"] nameClose:[27, 29, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 2
 {{macro}} content {{/macro}}
.
<p>{{macro}} content {{/macro}}</p>
.
Document[0, 29]
  Paragraph[1, 29]
    Macro[1, 29] open:[1, 3, "{{"] name:[3, 8, "macro"] close:[8, 10, "}}"] macroContent:[10, 19, " content "]
      Text[10, 19] chars:[10, 19, " content "]
      MacroClose[19, 29] nameOpen:[19, 22, "{{/"] name:[22, 27, "macro"] nameClose:[27, 29, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 3
 {{macro/}}
.
<p>{{macro/}}</p>
.
Document[0, 11]
  Paragraph[1, 11]
    Macro[1, 11] open:[1, 3, "{{"] name:[3, 8, "macro"] close:[8, 11, "/}}"] isClosed
````````````````````````````````


```````````````````````````````` example Macros: 4
needs blank line before
{{macro}}
content
{{/macro}}
.
<p>needs blank line before
{{macro}}
content
{{/macro}}</p>
.
Document[0, 52]
  Paragraph[0, 52]
    Text[0, 23] chars:[0, 23, "needs … efore"]
    SoftLineBreak[23, 24]
    Macro[24, 52] open:[24, 26, "{{"] name:[26, 31, "macro"] close:[31, 33, "}}"] macroContent:[33, 42, "\ncontent\n"]
      SoftLineBreak[33, 34]
      Text[34, 41] chars:[34, 41, "content"]
      SoftLineBreak[41, 42]
      MacroClose[42, 52] nameOpen:[42, 45, "{{/"] name:[45, 50, "macro"] nameClose:[50, 52, "}}"]
````````````````````````````````


Converts macros text to macros nodes.

```````````````````````````````` example Macros: 5
{{macro}}
content
{{/macro}}
.
{{macro}}
<p>content</p>
{{/macro}}
.
Document[0, 28]
  MacroBlock[0, 28] macroContent:[10, 18, "content\n"]
    Macro[0, 9] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[7, 9, "}}"] isBlockMacro macroContent:[9, 9]
    Paragraph[10, 18]
      Text[10, 17] chars:[10, 17, "content"]
    MacroClose[18, 28] nameOpen:[18, 21, "{{/"] name:[21, 26, "macro"] nameClose:[26, 28, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 6
{{macro}}
content

with blank line
{{/macro}}
.
{{macro}}
<p>content</p>
<p>with blank line</p>
{{/macro}}
.
Document[0, 45]
  MacroBlock[0, 45] macroContent:[10, 35, "content\n\nwith blank line\n"]
    Macro[0, 9] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[7, 9, "}}"] isBlockMacro macroContent:[9, 9]
    Paragraph[10, 18] isTrailingBlankLine
      Text[10, 17] chars:[10, 17, "content"]
    Paragraph[19, 35]
      Text[19, 34] chars:[19, 34, "with  …  line"]
    MacroClose[35, 45] nameOpen:[35, 38, "{{/"] name:[38, 43, "macro"] nameClose:[43, 45, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 7
{{macro}}
content

{{macro}}
nested content

nested with blank line
{{/macro}}
with blank line
{{/macro}}
.
{{macro}}
<p>content</p>
{{macro}}
<p>nested content</p>
<p>nested with blank line</p>
{{/macro}}
<p>with blank line</p>
{{/macro}}
.
Document[0, 105]
  MacroBlock[0, 105] macroContent:[10, 95, "content\n\n{{macro}}\nnested content\n\nnested with blank line\n{{/macro}}\nwith blank line\n"]
    Macro[0, 9] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[7, 9, "}}"] isBlockMacro macroContent:[9, 9]
    Paragraph[10, 18] isTrailingBlankLine
      Text[10, 17] chars:[10, 17, "content"]
    MacroBlock[19, 78] macroContent:[29, 68, "nested content\n\nnested with blank line\n"]
      Macro[19, 28] open:[19, 21, "{{"] name:[21, 26, "macro"] close:[26, 28, "}}"] isBlockMacro macroContent:[28, 28]
      Paragraph[29, 44] isTrailingBlankLine
        Text[29, 43] chars:[29, 43, "neste … ntent"]
      Paragraph[45, 68]
        Text[45, 67] chars:[45, 67, "neste …  line"]
      MacroClose[68, 78] nameOpen:[68, 71, "{{/"] name:[71, 76, "macro"] nameClose:[76, 78, "}}"]
    Paragraph[79, 95]
      Text[79, 94] chars:[79, 94, "with  …  line"]
    MacroClose[95, 105] nameOpen:[95, 98, "{{/"] name:[98, 103, "macro"] nameClose:[103, 105, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 8
{{macro}}
content
- bullet item
  text

<!-- list break -->

1. numbered item

with blank line
{{/macro}}
.
{{macro}}
<p>content</p>
<ul>
  <li>bullet item
    text</li>
</ul>
<!-- list break -->
<ol>
  <li>numbered item</li>
</ol>
<p>with blank line</p>
{{/macro}}
.
Document[0, 105]
  MacroBlock[0, 105] macroContent:[10, 95, "content\n- bullet item\n  text\n\n<!-- list break -->\n\n1. numbered item\n\nwith blank line\n"]
    Macro[0, 9] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[7, 9, "}}"] isBlockMacro macroContent:[9, 9]
    Paragraph[10, 18]
      Text[10, 17] chars:[10, 17, "content"]
    BulletList[18, 39] isTight
      BulletListItem[18, 39] open:[18, 19, "-"] isTight hadBlankLineAfter
        Paragraph[20, 39] isTrailingBlankLine
          Text[20, 31] chars:[20, 31, "bulle …  item"]
          SoftLineBreak[31, 32]
          Text[34, 38] chars:[34, 38, "text"]
    HtmlCommentBlock[40, 60]
    OrderedList[61, 78] isTight delimiter:'.'
      OrderedListItem[61, 78] open:[61, 63, "1."] isTight hadBlankLineAfter
        Paragraph[64, 78] isTrailingBlankLine
          Text[64, 77] chars:[64, 77, "numbe …  item"]
    Paragraph[79, 95]
      Text[79, 94] chars:[79, 94, "with  …  line"]
    MacroClose[95, 105] nameOpen:[95, 98, "{{/"] name:[98, 103, "macro"] nameClose:[103, 105, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 9
{{macro/}}
.
{{macro/}}
.
Document[0, 10]
  MacroBlock[0, 10] isClosed
    Macro[0, 10] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[7, 10, "/}}"] isClosed isBlockMacro
````````````````````````````````


```````````````````````````````` example Macros: 10
{{macro /}}
.
{{macro /}}
.
Document[0, 11]
  MacroBlock[0, 11] isClosed
    Macro[0, 11] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[8, 11, "/}}"] isClosed isBlockMacro
````````````````````````````````


```````````````````````````````` example Macros: 11
{{macro}}{{/macro}}
.
{{macro}}{{/macro}}
.
Document[0, 19]
  MacroBlock[0, 19]
    Macro[0, 9] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[7, 9, "}}"] isBlockMacro macroContent:[9, 9]
    MacroClose[9, 19] nameOpen:[9, 12, "{{/"] name:[12, 17, "macro"] nameClose:[17, 19, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 12
{{macro}}text content{{/macro}}
.
{{macro}}text content{{/macro}}
.
Document[0, 31]
  MacroBlock[0, 31] macroContent:[9, 21, "text content"]
    Macro[0, 9] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[7, 9, "}}"] isBlockMacro macroContent:[9, 9]
    Text[9, 21] chars:[9, 21, "text  … ntent"]
    MacroClose[21, 31] nameOpen:[21, 24, "{{/"] name:[24, 29, "macro"] nameClose:[29, 31, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 13
{{macro}}text **bold** content{{/macro}}
.
{{macro}}text <strong>bold</strong> content{{/macro}}
.
Document[0, 40]
  MacroBlock[0, 40] macroContent:[9, 30, "text **bold** content"]
    Macro[0, 9] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[7, 9, "}}"] isBlockMacro macroContent:[9, 9]
    Text[9, 14] chars:[9, 14, "text "]
    StrongEmphasis[14, 22] textOpen:[14, 16, "**"] text:[16, 20, "bold"] textClose:[20, 22, "**"]
      Text[16, 20] chars:[16, 20, "bold"]
    Text[22, 30] chars:[22, 30, " content"]
    MacroClose[30, 40] nameOpen:[30, 33, "{{/"] name:[33, 38, "macro"] nameClose:[38, 40, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 14
{{macro attribute}}text{{/macro}}
.
{{macro attribute}}text{{/macro}}
.
Document[0, 33]
  MacroBlock[0, 33] macroContent:[19, 23, "text"]
    Macro[0, 19] open:[0, 2, "{{"] name:[2, 7, "macro"] attributes:[8, 17, "attribute"] close:[17, 19, "}}"] isBlockMacro macroContent:[19, 19]
      MacroAttribute[8, 17] attribute:[8, 17, "attribute"]
    Text[19, 23] chars:[19, 23, "text"]
    MacroClose[23, 33] nameOpen:[23, 26, "{{/"] name:[26, 31, "macro"] nameClose:[31, 33, "}}"]
````````````````````````````````


not a valid attribute, now not a macro block

```````````````````````````````` example Macros: 15
{{macro attribute=}}text{{/macro}}
.
<p>{{macro attribute=}}text{{/macro}}</p>
.
Document[0, 34]
  Paragraph[0, 34]
    Text[0, 34] chars:[0, 34, "{{mac … cro}}"]
````````````````````````````````


```````````````````````````````` example Macros: 16
{{macro attribute=""}}text{{/macro}}
.
{{macro attribute=&quot;&quot;}}text{{/macro}}
.
Document[0, 36]
  MacroBlock[0, 36] macroContent:[22, 26, "text"]
    Macro[0, 22] open:[0, 2, "{{"] name:[2, 7, "macro"] attributes:[8, 20, "attribute=\"\""] close:[20, 22, "}}"] isBlockMacro macroContent:[22, 22]
      MacroAttribute[8, 20] attribute:[8, 17, "attribute"] separator:[17, 18, "="] valueOpen:[18, 19, "\""] value:[19, 19] valueClose:[19, 20, "\""]
    Text[22, 26] chars:[22, 26, "text"]
    MacroClose[26, 36] nameOpen:[26, 29, "{{/"] name:[29, 34, "macro"] nameClose:[34, 36, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 17
{{macro attribute=''}}text{{/macro}}
.
{{macro attribute=''}}text{{/macro}}
.
Document[0, 36]
  MacroBlock[0, 36] macroContent:[22, 26, "text"]
    Macro[0, 22] open:[0, 2, "{{"] name:[2, 7, "macro"] attributes:[8, 20, "attribute=''"] close:[20, 22, "}}"] isBlockMacro macroContent:[22, 22]
      MacroAttribute[8, 20] attribute:[8, 17, "attribute"] separator:[17, 18, "="] valueOpen:[18, 19, "'"] value:[19, 19] valueClose:[19, 20, "'"]
    Text[22, 26] chars:[22, 26, "text"]
    MacroClose[26, 36] nameOpen:[26, 29, "{{/"] name:[29, 34, "macro"] nameClose:[34, 36, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 18
{{macro attribute="value"}}text{{/macro}}
.
{{macro attribute=&quot;value&quot;}}text{{/macro}}
.
Document[0, 41]
  MacroBlock[0, 41] macroContent:[27, 31, "text"]
    Macro[0, 27] open:[0, 2, "{{"] name:[2, 7, "macro"] attributes:[8, 25, "attribute=\"value\""] close:[25, 27, "}}"] isBlockMacro macroContent:[27, 27]
      MacroAttribute[8, 25] attribute:[8, 17, "attribute"] separator:[17, 18, "="] valueOpen:[18, 19, "\""] value:[19, 24, "value"] valueClose:[24, 25, "\""]
    Text[27, 31] chars:[27, 31, "text"]
    MacroClose[31, 41] nameOpen:[31, 34, "{{/"] name:[34, 39, "macro"] nameClose:[39, 41, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 19
{{macro attribute='value'}}text{{/macro}}
.
{{macro attribute='value'}}text{{/macro}}
.
Document[0, 41]
  MacroBlock[0, 41] macroContent:[27, 31, "text"]
    Macro[0, 27] open:[0, 2, "{{"] name:[2, 7, "macro"] attributes:[8, 25, "attribute='value'"] close:[25, 27, "}}"] isBlockMacro macroContent:[27, 27]
      MacroAttribute[8, 25] attribute:[8, 17, "attribute"] separator:[17, 18, "="] valueOpen:[18, 19, "'"] value:[19, 24, "value"] valueClose:[24, 25, "'"]
    Text[27, 31] chars:[27, 31, "text"]
    MacroClose[31, 41] nameOpen:[31, 34, "{{/"] name:[34, 39, "macro"] nameClose:[39, 41, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 20
{{macro disabled class="aClass" attribute='value' mixed='test "quoted"'}}text{{/macro}}
.
{{macro disabled class=&quot;aClass&quot; attribute='value' mixed='test &quot;quoted&quot;'}}text{{/macro}}
.
Document[0, 87]
  MacroBlock[0, 87] macroContent:[73, 77, "text"]
    Macro[0, 73] open:[0, 2, "{{"] name:[2, 7, "macro"] attributes:[8, 71, "disabled class=\"aClass\" attribute='value' mixed='test \"quoted\"'"] close:[71, 73, "}}"] isBlockMacro macroContent:[73, 73]
      MacroAttribute[8, 16] attribute:[8, 16, "disabled"]
      MacroAttribute[17, 31] attribute:[17, 22, "class"] separator:[22, 23, "="] valueOpen:[23, 24, "\""] value:[24, 30, "aClass"] valueClose:[30, 31, "\""]
      MacroAttribute[32, 49] attribute:[32, 41, "attribute"] separator:[41, 42, "="] valueOpen:[42, 43, "'"] value:[43, 48, "value"] valueClose:[48, 49, "'"]
      MacroAttribute[50, 71] attribute:[50, 55, "mixed"] separator:[55, 56, "="] valueOpen:[56, 57, "'"] value:[57, 70, "test \"quoted\""] valueClose:[70, 71, "'"]
    Text[73, 77] chars:[73, 77, "text"]
    MacroClose[77, 87] nameOpen:[77, 80, "{{/"] name:[80, 85, "macro"] nameClose:[85, 87, "}}"]
````````````````````````````````


```````````````````````````````` example(Macros: 21) options(no-rendering)
{{macro disabled class="aClass" attribute='value' mixed='test "quoted"'}}text{{/macro}}
.
.
Document[0, 87]
  MacroBlock[0, 87] macroContent:[73, 77, "text"]
    Macro[0, 73] open:[0, 2, "{{"] name:[2, 7, "macro"] attributes:[8, 71, "disabled class=\"aClass\" attribute='value' mixed='test \"quoted\"'"] close:[71, 73, "}}"] isBlockMacro macroContent:[73, 73]
      MacroAttribute[8, 16] attribute:[8, 16, "disabled"]
      MacroAttribute[17, 31] attribute:[17, 22, "class"] separator:[22, 23, "="] valueOpen:[23, 24, "\""] value:[24, 30, "aClass"] valueClose:[30, 31, "\""]
      MacroAttribute[32, 49] attribute:[32, 41, "attribute"] separator:[41, 42, "="] valueOpen:[42, 43, "'"] value:[43, 48, "value"] valueClose:[48, 49, "'"]
      MacroAttribute[50, 71] attribute:[50, 55, "mixed"] separator:[55, 56, "="] valueOpen:[56, 57, "'"] value:[57, 70, "test \"quoted\""] valueClose:[70, 71, "'"]
    Text[73, 77] chars:[73, 77, "text"]
    MacroClose[77, 87] nameOpen:[77, 80, "{{/"] name:[80, 85, "macro"] nameClose:[85, 87, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 22
 {{macro}}text **bold** content{{/macro}}
.
<p>{{macro}}text <strong>bold</strong> content{{/macro}}</p>
.
Document[0, 41]
  Paragraph[1, 41]
    Macro[1, 41] open:[1, 3, "{{"] name:[3, 8, "macro"] close:[8, 10, "}}"] macroContent:[10, 31, "text **bold** content"]
      Text[10, 15] chars:[10, 15, "text "]
      StrongEmphasis[15, 23] textOpen:[15, 17, "**"] text:[17, 21, "bold"] textClose:[21, 23, "**"]
        Text[17, 21] chars:[17, 21, "bold"]
      Text[23, 31] chars:[23, 31, " content"]
      MacroClose[31, 41] nameOpen:[31, 34, "{{/"] name:[34, 39, "macro"] nameClose:[39, 41, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 23
 **{{macro}}text *italic* content{{/macro}}**
.
<p><strong>{{macro}}text <em>italic</em> content{{/macro}}</strong></p>
.
Document[0, 45]
  Paragraph[1, 45]
    StrongEmphasis[1, 45] textOpen:[1, 3, "**"] text:[3, 43, "{{macro}}text *italic* content{{/macro}}"] textClose:[43, 45, "**"]
      Macro[3, 43] open:[3, 5, "{{"] name:[5, 10, "macro"] close:[10, 12, "}}"] macroContent:[12, 33, "text *italic* content"]
        Text[12, 17] chars:[12, 17, "text "]
        Emphasis[17, 25] textOpen:[17, 18, "*"] text:[18, 24, "italic"] textClose:[24, 25, "*"]
          Text[18, 24] chars:[18, 24, "italic"]
        Text[25, 33] chars:[25, 33, " content"]
        MacroClose[33, 43] nameOpen:[33, 36, "{{/"] name:[36, 41, "macro"] nameClose:[41, 43, "}}"]
````````````````````````````````


```````````````````````````````` example(Macros: 24) options(no-rendering)
 **{{macro}}text *italic* content{{/macro}}**
.
<p><strong></strong></p>
.
Document[0, 45]
  Paragraph[1, 45]
    StrongEmphasis[1, 45] textOpen:[1, 3, "**"] text:[3, 43, "{{macro}}text *italic* content{{/macro}}"] textClose:[43, 45, "**"]
      Macro[3, 43] open:[3, 5, "{{"] name:[5, 10, "macro"] close:[10, 12, "}}"] macroContent:[12, 33, "text *italic* content"]
        Text[12, 17] chars:[12, 17, "text "]
        Emphasis[17, 25] textOpen:[17, 18, "*"] text:[18, 24, "italic"] textClose:[24, 25, "*"]
          Text[18, 24] chars:[18, 24, "italic"]
        Text[25, 33] chars:[25, 33, " content"]
        MacroClose[33, 43] nameOpen:[33, 36, "{{/"] name:[36, 41, "macro"] nameClose:[41, 43, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 25
 **{{macro class="aClass" style="margin:0" disabled}}text *italic* content{{/macro}}**
.
<p><strong>{{macro class=&quot;aClass&quot; style=&quot;margin:0&quot; disabled}}text <em>italic</em> content{{/macro}}</strong></p>
.
Document[0, 86]
  Paragraph[1, 86]
    StrongEmphasis[1, 86] textOpen:[1, 3, "**"] text:[3, 84, "{{macro class=\"aClass\" style=\"margin:0\" disabled}}text *italic* content{{/macro}}"] textClose:[84, 86, "**"]
      Macro[3, 84] open:[3, 5, "{{"] name:[5, 10, "macro"] attributes:[11, 51, "class=\"aClass\" style=\"margin:0\" disabled"] close:[51, 53, "}}"] macroContent:[53, 74, "text *italic* content"]
        MacroAttribute[11, 25] attribute:[11, 16, "class"] separator:[16, 17, "="] valueOpen:[17, 18, "\""] value:[18, 24, "aClass"] valueClose:[24, 25, "\""]
        MacroAttribute[26, 42] attribute:[26, 31, "style"] separator:[31, 32, "="] valueOpen:[32, 33, "\""] value:[33, 41, "margin:0"] valueClose:[41, 42, "\""]
        MacroAttribute[43, 51] attribute:[43, 51, "disabled"]
        Text[53, 58] chars:[53, 58, "text "]
        Emphasis[58, 66] textOpen:[58, 59, "*"] text:[59, 65, "italic"] textClose:[65, 66, "*"]
          Text[59, 65] chars:[59, 65, "italic"]
        Text[66, 74] chars:[66, 74, " content"]
        MacroClose[74, 84] nameOpen:[74, 77, "{{/"] name:[77, 82, "macro"] nameClose:[82, 84, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 26
 {{macro}}nested{{macro}}content{{/macro}}{{/macro}}
.
<p>{{macro}}nested{{macro}}content{{/macro}}{{/macro}}</p>
.
Document[0, 52]
  Paragraph[1, 52]
    Macro[1, 52] open:[1, 3, "{{"] name:[3, 8, "macro"] close:[8, 10, "}}"] macroContent:[10, 42, "nested{{macro}}content{{/macro}}"]
      Text[10, 16] chars:[10, 16, "nested"]
      Macro[16, 42] open:[16, 18, "{{"] name:[18, 23, "macro"] close:[23, 25, "}}"] macroContent:[25, 32, "content"]
        Text[25, 32] chars:[25, 32, "content"]
        MacroClose[32, 42] nameOpen:[32, 35, "{{/"] name:[35, 40, "macro"] nameClose:[40, 42, "}}"]
      MacroClose[42, 52] nameOpen:[42, 45, "{{/"] name:[45, 50, "macro"] nameClose:[50, 52, "}}"]
````````````````````````````````


macros are automatically closed

```````````````````````````````` example Macros: 27
 {{macro}}nested{{macro}}content
.
<p>{{macro}}nested{{macro}}content</p>
.
Document[0, 32]
  Paragraph[1, 32]
    Macro[1, 32] open:[1, 3, "{{"] name:[3, 8, "macro"] close:[8, 10, "}}"] macroContent:[10, 16, "nested"]
      Text[10, 16] chars:[10, 16, "nested"]
      Macro[16, 32] open:[16, 18, "{{"] name:[18, 23, "macro"] close:[23, 25, "}}"] macroContent:[25, 25]
        Text[25, 32] chars:[25, 32, "content"]
````````````````````````````````


macros are automatically closed

```````````````````````````````` example Macros: 28
 {{macro1}}nested{{macro2}}content{{/macro1}}
.
<p>{{macro1}}nested{{macro2}}content{{/macro1}}</p>
.
Document[0, 45]
  Paragraph[1, 45]
    Macro[1, 45] open:[1, 3, "{{"] name:[3, 9, "macro1"] close:[9, 11, "}}"] macroContent:[11, 34, "nested{{macro2}}content"]
      Text[11, 17] chars:[11, 17, "nested"]
      Macro[17, 34] open:[17, 19, "{{"] name:[19, 25, "macro2"] close:[25, 27, "}}"] macroContent:[27, 27]
        Text[27, 34] chars:[27, 34, "content"]
      MacroClose[34, 45] nameOpen:[34, 37, "{{/"] name:[37, 43, "macro1"] nameClose:[43, 45, "}}"]
````````````````````````````````


Incorrect input

```````````````````````````````` example Macros: 29
{{mymacro par1="val1" par2=2}}
{{/nomacro}} is not end
and even {{mymacro}} is just plain content
{{/mymacro}}

Markdown
{{/mymacro}}
.
{{mymacro par1=&quot;val1&quot; par2=2}}
<p>{{/nomacro}} is not end
and even {{mymacro}} is just plain content</p>
{{/mymacro}}
<p>Markdown
{{/mymacro}}</p>
.
Document[0, 133]
  MacroBlock[0, 110] macroContent:[31, 98, "{{/nomacro}} is not end\nand even {{mymacro}} is just plain content\n"]
    Macro[0, 30] open:[0, 2, "{{"] name:[2, 9, "mymacro"] attributes:[10, 28, "par1=\"val1\" par2=2"] close:[28, 30, "}}"] isBlockMacro macroContent:[30, 30]
      MacroAttribute[10, 21] attribute:[10, 14, "par1"] separator:[14, 15, "="] valueOpen:[15, 16, "\""] value:[16, 20, "val1"] valueClose:[20, 21, "\""]
      MacroAttribute[22, 28] attribute:[22, 26, "par2"] separator:[26, 27, "="] value:[27, 28, "2"]
    Paragraph[31, 98]
      Text[31, 54] chars:[31, 54, "{{/no … t end"]
      SoftLineBreak[54, 55]
      Text[55, 64] chars:[55, 64, "and even "]
      Macro[64, 97] open:[64, 66, "{{"] name:[66, 73, "mymacro"] close:[73, 75, "}}"] macroContent:[75, 75]
        Text[75, 97] chars:[75, 97, " is j … ntent"]
    MacroClose[98, 110] nameOpen:[98, 101, "{{/"] name:[101, 108, "mymacro"] nameClose:[108, 110, "}}"]
  Paragraph[112, 133]
    Text[112, 120] chars:[112, 120, "Markdown"]
    SoftLineBreak[120, 121]
    Text[121, 133] chars:[121, 133, "{{/my … cro}}"]
````````````````````````````````


Corrected input

```````````````````````````````` example Macros: 30
{{mymacro par1="val1" par2=2}}
{{/nomacro}} is not end
and even {{mymacro}} is just plain content
 {{/mymacro}}

Markdown
{{/mymacro}}
.
{{mymacro par1=&quot;val1&quot; par2=2}}
<p>{{/nomacro}} is not end
and even {{mymacro}} is just plain content
{{/mymacro}}</p>
<p>Markdown</p>
{{/mymacro}}
.
Document[0, 134]
  MacroBlock[0, 134] macroContent:[31, 122, "{{/nomacro}} is not end\nand even {{mymacro}} is just plain content\n {{/mymacro}}\n\nMarkdown\n"]
    Macro[0, 30] open:[0, 2, "{{"] name:[2, 9, "mymacro"] attributes:[10, 28, "par1=\"val1\" par2=2"] close:[28, 30, "}}"] isBlockMacro macroContent:[30, 30]
      MacroAttribute[10, 21] attribute:[10, 14, "par1"] separator:[14, 15, "="] valueOpen:[15, 16, "\""] value:[16, 20, "val1"] valueClose:[20, 21, "\""]
      MacroAttribute[22, 28] attribute:[22, 26, "par2"] separator:[26, 27, "="] value:[27, 28, "2"]
    Paragraph[31, 112] isTrailingBlankLine
      Text[31, 54] chars:[31, 54, "{{/no … t end"]
      SoftLineBreak[54, 55]
      Text[55, 64] chars:[55, 64, "and even "]
      Macro[64, 111] open:[64, 66, "{{"] name:[66, 73, "mymacro"] close:[73, 75, "}}"] macroContent:[75, 99, " is just plain content\n "]
        Text[75, 97] chars:[75, 97, " is j … ntent"]
        SoftLineBreak[97, 98]
        MacroClose[99, 111] nameOpen:[99, 102, "{{/"] name:[102, 109, "mymacro"] nameClose:[109, 111, "}}"]
    Paragraph[113, 122]
      Text[113, 121] chars:[113, 121, "Markdown"]
    MacroClose[122, 134] nameOpen:[122, 125, "{{/"] name:[125, 132, "mymacro"] nameClose:[132, 134, "}}"]
````````````````````````````````


