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

```````````````````````````````` example Macros: 1
 {{macro}}
content
{{/macro}}
.
<p>{{macro}}
content
{{/macro}}</p>
.
Document[0, 30]
  Paragraph[1, 30]
    Text[1, 10] chars:[1, 10, "{{macro}}"]
    SoftLineBreak[10, 11]
    Text[11, 18] chars:[11, 18, "content"]
    SoftLineBreak[18, 19]
    Text[19, 29] chars:[19, 29, "{{/macro}}"]
````````````````````````````````


```````````````````````````````` example Macros: 2
 {{macro}} content {{/macro}}
.
<p>{{macro}} content {{/macro}}</p>
.
Document[0, 30]
  Paragraph[1, 30]
    Text[1, 29] chars:[1, 29, "{{mac … cro}}"]
````````````````````````````````


```````````````````````````````` example Macros: 3
 {{macro/}}
.
<p>{{macro/}}</p>
.
Document[0, 12]
  Paragraph[1, 12]
    Text[1, 11] chars:[1, 11, "{{macro/}}"]
````````````````````````````````


Converts macros text to macros nodes.

```````````````````````````````` example Macros: 4
{{macro}}
content
{{/macro}}
.
.
Document[0, 29]
  MacroBlock[0, 28]
    Macro[0, 9] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[7, 9, "}}"]
    Paragraph[10, 18]
      Text[10, 17] chars:[10, 17, "content"]
    MacroClose[18, 28] nameOpen:[18, 21, "{{/"] name:[21, 26, "macro"] nameClose:[26, 28, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 5
{{macro}}
content

with blank line
{{/macro}}
.
.
Document[0, 46]
  MacroBlock[0, 45]
    Macro[0, 9] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[7, 9, "}}"]
    Paragraph[10, 18]
      Text[10, 17] chars:[10, 17, "content"]
    Paragraph[19, 35]
      Text[19, 34] chars:[19, 34, "with  …  line"]
    MacroClose[35, 45] nameOpen:[35, 38, "{{/"] name:[38, 43, "macro"] nameClose:[43, 45, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 6
{{macro}}
content

{{macro}}
nested content

nested with blank line
{{/macro}}
with blank line
{{/macro}}
.
.
Document[0, 106]
  MacroBlock[0, 105]
    Macro[0, 9] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[7, 9, "}}"]
    Paragraph[10, 18]
      Text[10, 17] chars:[10, 17, "content"]
    MacroBlock[19, 78]
      Macro[19, 28] open:[19, 21, "{{"] name:[21, 26, "macro"] close:[26, 28, "}}"]
      Paragraph[29, 44]
        Text[29, 43] chars:[29, 43, "neste … ntent"]
      Paragraph[45, 68]
        Text[45, 67] chars:[45, 67, "neste …  line"]
      MacroClose[68, 78] nameOpen:[68, 71, "{{/"] name:[71, 76, "macro"] nameClose:[76, 78, "}}"]
    Paragraph[79, 95]
      Text[79, 94] chars:[79, 94, "with  …  line"]
    MacroClose[95, 105] nameOpen:[95, 98, "{{/"] name:[98, 103, "macro"] nameClose:[103, 105, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 7
{{macro}}
content
- bullet item
  text

<!-- list break -->

1. numbered item

with blank line
{{/macro}}
.
.
Document[0, 106]
  MacroBlock[0, 105]
    Macro[0, 9] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[7, 9, "}}"]
    Paragraph[10, 18]
      Text[10, 17] chars:[10, 17, "content"]
    BulletList[18, 39] isTight
      BulletListItem[18, 39] open:[18, 19, "-"] isTight hadBlankLineAfter
        Paragraph[20, 39]
          Text[20, 31] chars:[20, 31, "bulle …  item"]
          SoftLineBreak[31, 32]
          Text[34, 38] chars:[34, 38, "text"]
    HtmlCommentBlock[40, 60]
    OrderedList[61, 78] isTight delimiter:'.'
      OrderedListItem[61, 78] open:[61, 63, "1."] isTight hadBlankLineAfter
        Paragraph[64, 78]
          Text[64, 77] chars:[64, 77, "numbe …  item"]
    Paragraph[79, 95]
      Text[79, 94] chars:[79, 94, "with  …  line"]
    MacroClose[95, 105] nameOpen:[95, 98, "{{/"] name:[98, 103, "macro"] nameClose:[103, 105, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 8
{{macro/}}
.
.
Document[0, 11]
  MacroBlock[0, 10]
    Macro[0, 10] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[7, 10, "/}}"]
````````````````````````````````


```````````````````````````````` example Macros: 9
{{macro /}}
.
.
Document[0, 12]
  MacroBlock[0, 11]
    Macro[0, 11] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[8, 11, "/}}"]
````````````````````````````````


```````````````````````````````` example Macros: 10
{{macro}}{{/macro}}
.
.
Document[0, 20]
  MacroBlock[0, 19]
    Macro[0, 9] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[7, 9, "}}"]
    MacroClose[9, 19] nameOpen:[9, 12, "{{/"] name:[12, 17, "macro"] nameClose:[17, 19, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 11
{{macro}}text content{{/macro}}
.
.
Document[0, 32]
  MacroBlock[0, 31]
    Macro[0, 9] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[7, 9, "}}"]
    Text[9, 21] chars:[9, 21, "text  … ntent"]
    MacroClose[21, 31] nameOpen:[21, 24, "{{/"] name:[24, 29, "macro"] nameClose:[29, 31, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 12
{{macro}}text **bold** content{{/macro}}
.
.
Document[0, 41]
  MacroBlock[0, 40]
    Macro[0, 9] open:[0, 2, "{{"] name:[2, 7, "macro"] close:[7, 9, "}}"]
    Text[9, 14] chars:[9, 14, "text "]
    StrongEmphasis[14, 22] textOpen:[14, 16, "**"] text:[16, 20, "bold"] textClose:[20, 22, "**"]
      Text[16, 20] chars:[16, 20, "bold"]
    Text[22, 30] chars:[22, 30, " content"]
    MacroClose[30, 40] nameOpen:[30, 33, "{{/"] name:[33, 38, "macro"] nameClose:[38, 40, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 13
{{macro attribute}}text{{/macro}}
.
.
Document[0, 34]
  MacroBlock[0, 33]
    Macro[0, 19] open:[0, 2, "{{"] name:[2, 7, "macro"] attributes:[8, 17, "attribute"] close:[17, 19, "}}"]
      MacroAttribute[8, 17] attribute:[8, 17, "attribute"]
    Text[19, 23] chars:[19, 23, "text"]
    MacroClose[23, 33] nameOpen:[23, 26, "{{/"] name:[26, 31, "macro"] nameClose:[31, 33, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 14
{{macro attribute=}}text{{/macro}}
.
.
Document[0, 35]
  MacroBlock[0, 34]
    Macro[0, 34] open:[0, 2, "{{"] name:[2, 7, "macro"] attributes:[8, 32, "attribute=}}text{{/macro"] close:[32, 34, "}}"]
      MacroAttribute[8, 32] attribute:[8, 17, "attribute"] separator:[17, 18, "="] value:[18, 32, "}}text{{/macro"]
````````````````````````````````


```````````````````````````````` example Macros: 15
{{macro attribute=""}}text{{/macro}}
.
.
Document[0, 37]
  MacroBlock[0, 36]
    Macro[0, 22] open:[0, 2, "{{"] name:[2, 7, "macro"] attributes:[8, 20, "attribute=\"\""] close:[20, 22, "}}"]
      MacroAttribute[8, 20] attribute:[8, 17, "attribute"] separator:[17, 18, "="] valueOpen:[18, 19, "\""] value:[19, 19] valueClose:[19, 20, "\""]
    Text[22, 26] chars:[22, 26, "text"]
    MacroClose[26, 36] nameOpen:[26, 29, "{{/"] name:[29, 34, "macro"] nameClose:[34, 36, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 16
{{macro attribute=''}}text{{/macro}}
.
.
Document[0, 37]
  MacroBlock[0, 36]
    Macro[0, 22] open:[0, 2, "{{"] name:[2, 7, "macro"] attributes:[8, 20, "attribute=''"] close:[20, 22, "}}"]
      MacroAttribute[8, 20] attribute:[8, 17, "attribute"] separator:[17, 18, "="] valueOpen:[18, 19, "'"] value:[19, 19] valueClose:[19, 20, "'"]
    Text[22, 26] chars:[22, 26, "text"]
    MacroClose[26, 36] nameOpen:[26, 29, "{{/"] name:[29, 34, "macro"] nameClose:[34, 36, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 17
{{macro attribute="value"}}text{{/macro}}
.
.
Document[0, 42]
  MacroBlock[0, 41]
    Macro[0, 27] open:[0, 2, "{{"] name:[2, 7, "macro"] attributes:[8, 25, "attribute=\"value\""] close:[25, 27, "}}"]
      MacroAttribute[8, 25] attribute:[8, 17, "attribute"] separator:[17, 18, "="] valueOpen:[18, 19, "\""] value:[19, 24, "value"] valueClose:[24, 25, "\""]
    Text[27, 31] chars:[27, 31, "text"]
    MacroClose[31, 41] nameOpen:[31, 34, "{{/"] name:[34, 39, "macro"] nameClose:[39, 41, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 18
{{macro attribute='value'}}text{{/macro}}
.
.
Document[0, 42]
  MacroBlock[0, 41]
    Macro[0, 27] open:[0, 2, "{{"] name:[2, 7, "macro"] attributes:[8, 25, "attribute='value'"] close:[25, 27, "}}"]
      MacroAttribute[8, 25] attribute:[8, 17, "attribute"] separator:[17, 18, "="] valueOpen:[18, 19, "'"] value:[19, 24, "value"] valueClose:[24, 25, "'"]
    Text[27, 31] chars:[27, 31, "text"]
    MacroClose[31, 41] nameOpen:[31, 34, "{{/"] name:[34, 39, "macro"] nameClose:[39, 41, "}}"]
````````````````````````````````


```````````````````````````````` example Macros: 19
{{macro disabled class="aClass" attribute='value' mixed='test "quoted"'}}text{{/macro}}
.
.
Document[0, 88]
  MacroBlock[0, 87]
    Macro[0, 73] open:[0, 2, "{{"] name:[2, 7, "macro"] attributes:[8, 71, "disabled class=\"aClass\" attribute='value' mixed='test \"quoted\"'"] close:[71, 73, "}}"]
      MacroAttribute[8, 16] attribute:[8, 16, "disabled"]
      MacroAttribute[17, 31] attribute:[17, 22, "class"] separator:[22, 23, "="] valueOpen:[23, 24, "\""] value:[24, 30, "aClass"] valueClose:[30, 31, "\""]
      MacroAttribute[32, 49] attribute:[32, 41, "attribute"] separator:[41, 42, "="] valueOpen:[42, 43, "'"] value:[43, 48, "value"] valueClose:[48, 49, "'"]
      MacroAttribute[50, 71] attribute:[50, 55, "mixed"] separator:[55, 56, "="] valueOpen:[56, 57, "'"] value:[57, 70, "test \"quoted\""] valueClose:[70, 71, "'"]
    Text[73, 77] chars:[73, 77, "text"]
    MacroClose[77, 87] nameOpen:[77, 80, "{{/"] name:[80, 85, "macro"] nameClose:[85, 87, "}}"]
````````````````````````````````


