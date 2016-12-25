---
title: Subscript Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Subscript Extension

Converts ~text~ to subscript of text

The tests here are converted to commonmark spec.txt format and AST expected results added.

```````````````````````````````` example Subscript Extension: 1
~foo~
.
<p><sub>foo</sub></p>
.
Document[0, 6]
  Paragraph[0, 6]
    Subscript[0, 5] textOpen:[0, 1, "~"] text:[1, 4, "foo"] textClose:[4, 5, "~"]
      Text[1, 4] chars:[1, 4, "foo"]
````````````````````````````````


```````````````````````````````` example Subscript Extension: 2
~~foo~~
.
<p><sub><sub>foo</sub></sub></p>
.
Document[0, 8]
  Paragraph[0, 8]
    Subscript[0, 7] textOpen:[0, 1, "~"] text:[1, 6, "~foo~"] textClose:[6, 7, "~"]
      Subscript[1, 6] textOpen:[1, 2, "~"] text:[2, 5, "foo"] textClose:[5, 6, "~"]
        Text[2, 5] chars:[2, 5, "foo"]
````````````````````````````````


```````````````````````````````` example Subscript Extension: 3
foo ~~~~
.
<p>foo ~~~~</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 8] chars:[0, 8, "foo ~~~~"]
````````````````````````````````


```````````````````````````````` example Subscript Extension: 4
~~foo
.
<p>~~foo</p>
.
Document[0, 6]
  Paragraph[0, 6]
    Text[0, 5] chars:[0, 5, "~~foo"]
````````````````````````````````


```````````````````````````````` example Subscript Extension: 5
foo~~
.
<p>foo~~</p>
.
Document[0, 6]
  Paragraph[0, 6]
    Text[0, 5] chars:[0, 5, "foo~~"]
````````````````````````````````


```````````````````````````````` example Subscript Extension: 6
~~~foo~~~
.
<p><sub><sub><sub>foo</sub></sub></sub></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Subscript[0, 9] textOpen:[0, 1, "~"] text:[1, 8, "~~foo~~"] textClose:[8, 9, "~"]
      Subscript[1, 8] textOpen:[1, 2, "~"] text:[2, 7, "~foo~"] textClose:[7, 8, "~"]
        Subscript[2, 7] textOpen:[2, 3, "~"] text:[3, 6, "foo"] textClose:[6, 7, "~"]
          Text[3, 6] chars:[3, 6, "foo"]
````````````````````````````````


```````````````````````````````` example Subscript Extension: 7
~~foo~~~
.
<p><sub><sub>foo</sub></sub>~</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Subscript[0, 7] textOpen:[0, 1, "~"] text:[1, 6, "~foo~"] textClose:[6, 7, "~"]
      Subscript[1, 6] textOpen:[1, 2, "~"] text:[2, 5, "foo"] textClose:[5, 6, "~"]
        Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 8] chars:[7, 8, "~"]
````````````````````````````````


```````````````````````````````` example Subscript Extension: 8
~foo~bar~

~foo~~bar~

~foo~~~bar~

~foo~~~~bar~

~foo~~~~~bar~

~foo~~~~~~bar~

~foo~~~~~~~bar~
.
<p><sub>foo</sub>bar~</p>
<p><sub>foo</sub><sub>bar</sub></p>
<p><sub>foo</sub>~<sub>bar</sub></p>
<p><sub>foo</sub>~~<sub>bar</sub></p>
<p><sub>foo</sub>~~~<sub>bar</sub></p>
<p><sub>foo</sub>~~~~<sub>bar</sub></p>
<p><sub>foo</sub>~~~~~<sub>bar</sub></p>
.
Document[0, 97]
  Paragraph[0, 10]
    Subscript[0, 5] textOpen:[0, 1, "~"] text:[1, 4, "foo"] textClose:[4, 5, "~"]
      Text[1, 4] chars:[1, 4, "foo"]
    Text[5, 9] chars:[5, 9, "bar~"]
  Paragraph[11, 22]
    Subscript[11, 16] textOpen:[11, 12, "~"] text:[12, 15, "foo"] textClose:[15, 16, "~"]
      Text[12, 15] chars:[12, 15, "foo"]
    Subscript[16, 21] textOpen:[16, 17, "~"] text:[17, 20, "bar"] textClose:[20, 21, "~"]
      Text[17, 20] chars:[17, 20, "bar"]
  Paragraph[23, 35]
    Subscript[23, 28] textOpen:[23, 24, "~"] text:[24, 27, "foo"] textClose:[27, 28, "~"]
      Text[24, 27] chars:[24, 27, "foo"]
    Text[28, 29] chars:[28, 29, "~"]
    Subscript[29, 34] textOpen:[29, 30, "~"] text:[30, 33, "bar"] textClose:[33, 34, "~"]
      Text[30, 33] chars:[30, 33, "bar"]
  Paragraph[36, 49]
    Subscript[36, 41] textOpen:[36, 37, "~"] text:[37, 40, "foo"] textClose:[40, 41, "~"]
      Text[37, 40] chars:[37, 40, "foo"]
    Text[41, 43] chars:[41, 43, "~~"]
    Subscript[43, 48] textOpen:[43, 44, "~"] text:[44, 47, "bar"] textClose:[47, 48, "~"]
      Text[44, 47] chars:[44, 47, "bar"]
  Paragraph[50, 64]
    Subscript[50, 55] textOpen:[50, 51, "~"] text:[51, 54, "foo"] textClose:[54, 55, "~"]
      Text[51, 54] chars:[51, 54, "foo"]
    Text[55, 58] chars:[55, 58, "~~~"]
    Subscript[58, 63] textOpen:[58, 59, "~"] text:[59, 62, "bar"] textClose:[62, 63, "~"]
      Text[59, 62] chars:[59, 62, "bar"]
  Paragraph[65, 80]
    Subscript[65, 70] textOpen:[65, 66, "~"] text:[66, 69, "foo"] textClose:[69, 70, "~"]
      Text[66, 69] chars:[66, 69, "foo"]
    Text[70, 74] chars:[70, 74, "~~~~"]
    Subscript[74, 79] textOpen:[74, 75, "~"] text:[75, 78, "bar"] textClose:[78, 79, "~"]
      Text[75, 78] chars:[75, 78, "bar"]
  Paragraph[81, 97]
    Subscript[81, 86] textOpen:[81, 82, "~"] text:[82, 85, "foo"] textClose:[85, 86, "~"]
      Text[82, 85] chars:[82, 85, "foo"]
    Text[86, 91] chars:[86, 91, "~~~~~"]
    Subscript[91, 96] textOpen:[91, 92, "~"] text:[92, 95, "bar"] textClose:[95, 96, "~"]
      Text[92, 95] chars:[92, 95, "bar"]
````````````````````````````````


```````````````````````````````` example Subscript Extension: 9
~Paragraph with *emphasis* and __strong emphasis__~
.
<p><sub>Paragraph with <em>emphasis</em> and <strong>strong emphasis</strong></sub></p>
.
Document[0, 52]
  Paragraph[0, 52]
    Subscript[0, 51] textOpen:[0, 1, "~"] text:[1, 50, "Parag … raph with *emphasis* and __strong emphasis__"] textClose:[50, 51, "~"]
      Text[1, 16] chars:[1, 16, "Parag … with "]
      Emphasis[16, 26] textOpen:[16, 17, "*"] text:[17, 25, "emphasis"] textClose:[25, 26, "*"]
        Text[17, 25] chars:[17, 25, "emphasis"]
      Text[26, 31] chars:[26, 31, " and "]
      StrongEmphasis[31, 50] textOpen:[31, 33, "__"] text:[33, 48, "strong emphasis"] textClose:[48, 50, "__"]
        Text[33, 48] chars:[33, 48, "stron … hasis"]
````````````````````````````````


```````````````````````````````` example Subscript Extension: 10
> strike ~~that~~
.
<blockquote>
<p>strike <sub><sub>that</sub></sub></p>
</blockquote>
.
Document[0, 18]
  BlockQuote[0, 18] marker:[0, 1, ">"]
    Paragraph[2, 18]
      Text[2, 9] chars:[2, 9, "strike "]
      Subscript[9, 17] textOpen:[9, 10, "~"] text:[10, 16, "~that~"] textClose:[16, 17, "~"]
        Subscript[10, 16] textOpen:[10, 11, "~"] text:[11, 15, "that"] textClose:[15, 16, "~"]
          Text[11, 15] chars:[11, 15, "that"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
~Paragraph with *emphasis* and __strong emphasis__~
.
<p md-pos="0-52"><sub md-pos="1-50">Paragraph with <em md-pos="17-25">emphasis</em> and <strong md-pos="33-48">strong emphasis</strong></sub></p>
.
Document[0, 52]
  Paragraph[0, 52]
    Subscript[0, 51] textOpen:[0, 1, "~"] text:[1, 50, "Parag … raph with *emphasis* and __strong emphasis__"] textClose:[50, 51, "~"]
      Text[1, 16] chars:[1, 16, "Parag … with "]
      Emphasis[16, 26] textOpen:[16, 17, "*"] text:[17, 25, "emphasis"] textClose:[25, 26, "*"]
        Text[17, 25] chars:[17, 25, "emphasis"]
      Text[26, 31] chars:[26, 31, " and "]
      StrongEmphasis[31, 50] textOpen:[31, 33, "__"] text:[33, 48, "strong emphasis"] textClose:[48, 50, "__"]
        Text[33, 48] chars:[33, 48, "stron … hasis"]
````````````````````````````````


