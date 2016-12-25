---
title: Gfm Strikethrough Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Gfm Strikethrough Extension

Converts ~~text~~ to strikethrough of text

The tests here are converted to commonmark spec.txt format and AST expected results added.

```````````````````````````````` example Gfm Strikethrough Extension: 1
~foo~
.
<p><sub>foo</sub></p>
.
Document[0, 6]
  Paragraph[0, 6]
    Subscript[0, 5] textOpen:[0, 1, "~"] text:[1, 4, "foo"] textClose:[4, 5, "~"]
      Text[1, 4] chars:[1, 4, "foo"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 2
~~foo~~
.
<p><del>foo</del></p>
.
Document[0, 8]
  Paragraph[0, 8]
    Strikethrough[0, 7] textOpen:[0, 2, "~~"] text:[2, 5, "foo"] textClose:[5, 7, "~~"]
      Text[2, 5] chars:[2, 5, "foo"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 3
foo ~~~~
.
<p>foo ~~~~</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 8] chars:[0, 8, "foo ~~~~"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 4
~~foo
.
<p>~~foo</p>
.
Document[0, 6]
  Paragraph[0, 6]
    Text[0, 5] chars:[0, 5, "~~foo"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 5
foo~~
.
<p>foo~~</p>
.
Document[0, 6]
  Paragraph[0, 6]
    Text[0, 5] chars:[0, 5, "foo~~"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 6
~~~foo~~~
.
<p><del><sub>foo</sub></del></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Strikethrough[0, 9] textOpen:[0, 2, "~~"] text:[2, 7, "~foo~"] textClose:[7, 9, "~~"]
      Subscript[2, 7] textOpen:[2, 3, "~"] text:[3, 6, "foo"] textClose:[6, 7, "~"]
        Text[3, 6] chars:[3, 6, "foo"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 7
~~foo~~~
.
<p><del>foo</del>~</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Strikethrough[0, 7] textOpen:[0, 2, "~~"] text:[2, 5, "foo"] textClose:[5, 7, "~~"]
      Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 8] chars:[7, 8, "~"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 8
~~foo~bar~~

~~foo~~bar~~

~~foo~~~bar~~

~~foo~~~~bar~~

~~foo~~~~~bar~~

~~foo~~~~~~bar~~

~~foo~~~~~~~bar~~
.
<p><del>foo~bar</del></p>
<p><del>foo</del>bar~~</p>
<p><del>foo</del>~bar~~</p>
<p><del>foo~~~~bar</del></p>
<p><del>foo</del>~<del>bar</del></p>
<p><del>foo</del>~~~~bar~~</p>
<p><del>foo~~~~~~~bar</del></p>
.
Document[0, 111]
  Paragraph[0, 12]
    Strikethrough[0, 11] textOpen:[0, 2, "~~"] text:[2, 9, "foo~bar"] textClose:[9, 11, "~~"]
      Text[2, 9] chars:[2, 9, "foo~bar"]
  Paragraph[13, 26]
    Strikethrough[13, 20] textOpen:[13, 15, "~~"] text:[15, 18, "foo"] textClose:[18, 20, "~~"]
      Text[15, 18] chars:[15, 18, "foo"]
    Text[20, 25] chars:[20, 25, "bar~~"]
  Paragraph[27, 41]
    Strikethrough[27, 34] textOpen:[27, 29, "~~"] text:[29, 32, "foo"] textClose:[32, 34, "~~"]
      Text[29, 32] chars:[29, 32, "foo"]
    Text[34, 40] chars:[34, 40, "~bar~~"]
  Paragraph[42, 57]
    Strikethrough[42, 56] textOpen:[42, 44, "~~"] text:[44, 54, "foo~~~~bar"] textClose:[54, 56, "~~"]
      Text[44, 54] chars:[44, 54, "foo~~~~bar"]
  Paragraph[58, 74]
    Strikethrough[58, 65] textOpen:[58, 60, "~~"] text:[60, 63, "foo"] textClose:[63, 65, "~~"]
      Text[60, 63] chars:[60, 63, "foo"]
    Text[65, 66] chars:[65, 66, "~"]
    Strikethrough[66, 73] textOpen:[66, 68, "~~"] text:[68, 71, "bar"] textClose:[71, 73, "~~"]
      Text[68, 71] chars:[68, 71, "bar"]
  Paragraph[75, 92]
    Strikethrough[75, 82] textOpen:[75, 77, "~~"] text:[77, 80, "foo"] textClose:[80, 82, "~~"]
      Text[77, 80] chars:[77, 80, "foo"]
    Text[82, 91] chars:[82, 91, "~~~~bar~~"]
  Paragraph[93, 111]
    Strikethrough[93, 110] textOpen:[93, 95, "~~"] text:[95, 108, "foo~~ … ~~~~~bar"] textClose:[108, 110, "~~"]
      Text[95, 108] chars:[95, 108, "foo~~ … ~~bar"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 9
~~Paragraph with *emphasis* and __strong emphasis__~~
.
<p><del>Paragraph with <em>emphasis</em> and <strong>strong emphasis</strong></del></p>
.
Document[0, 54]
  Paragraph[0, 54]
    Strikethrough[0, 53] textOpen:[0, 2, "~~"] text:[2, 51, "Parag … raph with *emphasis* and __strong emphasis__"] textClose:[51, 53, "~~"]
      Text[2, 17] chars:[2, 17, "Parag … with "]
      Emphasis[17, 27] textOpen:[17, 18, "*"] text:[18, 26, "emphasis"] textClose:[26, 27, "*"]
        Text[18, 26] chars:[18, 26, "emphasis"]
      Text[27, 32] chars:[27, 32, " and "]
      StrongEmphasis[32, 51] textOpen:[32, 34, "__"] text:[34, 49, "strong emphasis"] textClose:[49, 51, "__"]
        Text[34, 49] chars:[34, 49, "stron … hasis"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 10
> strike ~~that~~
.
<blockquote>
<p>strike <del>that</del></p>
</blockquote>
.
Document[0, 18]
  BlockQuote[0, 18] marker:[0, 1, ">"]
    Paragraph[2, 18]
      Text[2, 9] chars:[2, 9, "strike "]
      Strikethrough[9, 17] textOpen:[9, 11, "~~"] text:[11, 15, "that"] textClose:[15, 17, "~~"]
        Text[11, 15] chars:[11, 15, "that"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
~~Paragraph with *emphasis* and __strong emphasis__~~
.
<p md-pos="0-54"><del md-pos="2-51">Paragraph with <em md-pos="18-26">emphasis</em> and <strong md-pos="34-49">strong emphasis</strong></del></p>
.
Document[0, 54]
  Paragraph[0, 54]
    Strikethrough[0, 53] textOpen:[0, 2, "~~"] text:[2, 51, "Parag … raph with *emphasis* and __strong emphasis__"] textClose:[51, 53, "~~"]
      Text[2, 17] chars:[2, 17, "Parag … with "]
      Emphasis[17, 27] textOpen:[17, 18, "*"] text:[18, 26, "emphasis"] textClose:[26, 27, "*"]
        Text[18, 26] chars:[18, 26, "emphasis"]
      Text[27, 32] chars:[27, 32, " and "]
      StrongEmphasis[32, 51] textOpen:[32, 34, "__"] text:[34, 49, "strong emphasis"] textClose:[49, 51, "__"]
        Text[34, 49] chars:[34, 49, "stron … hasis"]
````````````````````````````````


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
<p><del>foo</del></p>
.
Document[0, 8]
  Paragraph[0, 8]
    Strikethrough[0, 7] textOpen:[0, 2, "~~"] text:[2, 5, "foo"] textClose:[5, 7, "~~"]
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
<p><del><sub>foo</sub></del></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Strikethrough[0, 9] textOpen:[0, 2, "~~"] text:[2, 7, "~foo~"] textClose:[7, 9, "~~"]
      Subscript[2, 7] textOpen:[2, 3, "~"] text:[3, 6, "foo"] textClose:[6, 7, "~"]
        Text[3, 6] chars:[3, 6, "foo"]
````````````````````````````````


```````````````````````````````` example Subscript Extension: 7
~~foo~~~
.
<p><del>foo</del>~</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Strikethrough[0, 7] textOpen:[0, 2, "~~"] text:[2, 5, "foo"] textClose:[5, 7, "~~"]
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
<p><sub>foo~~bar</sub></p>
<p><sub>foo</sub>~~bar~</p>
<p><sub>foo</sub>~~<sub>bar</sub></p>
<p><sub>foo~~~~~bar</sub></p>
<p><sub>foo</sub>~~~~~bar~</p>
<p><sub>foo</sub>~~~~~<sub>bar</sub></p>
.
Document[0, 97]
  Paragraph[0, 10]
    Subscript[0, 5] textOpen:[0, 1, "~"] text:[1, 4, "foo"] textClose:[4, 5, "~"]
      Text[1, 4] chars:[1, 4, "foo"]
    Text[5, 9] chars:[5, 9, "bar~"]
  Paragraph[11, 22]
    Subscript[11, 21] textOpen:[11, 12, "~"] text:[12, 20, "foo~~bar"] textClose:[20, 21, "~"]
      Text[12, 20] chars:[12, 20, "foo~~bar"]
  Paragraph[23, 35]
    Subscript[23, 28] textOpen:[23, 24, "~"] text:[24, 27, "foo"] textClose:[27, 28, "~"]
      Text[24, 27] chars:[24, 27, "foo"]
    Text[28, 34] chars:[28, 34, "~~bar~"]
  Paragraph[36, 49]
    Subscript[36, 41] textOpen:[36, 37, "~"] text:[37, 40, "foo"] textClose:[40, 41, "~"]
      Text[37, 40] chars:[37, 40, "foo"]
    Text[41, 43] chars:[41, 43, "~~"]
    Subscript[43, 48] textOpen:[43, 44, "~"] text:[44, 47, "bar"] textClose:[47, 48, "~"]
      Text[44, 47] chars:[44, 47, "bar"]
  Paragraph[50, 64]
    Subscript[50, 63] textOpen:[50, 51, "~"] text:[51, 62, "foo~~ … ~~~bar"] textClose:[62, 63, "~"]
      Text[51, 62] chars:[51, 62, "foo~~ … ~~bar"]
  Paragraph[65, 80]
    Subscript[65, 70] textOpen:[65, 66, "~"] text:[66, 69, "foo"] textClose:[69, 70, "~"]
      Text[66, 69] chars:[66, 69, "foo"]
    Text[70, 79] chars:[70, 79, "~~~~~bar~"]
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
<p>strike <del>that</del></p>
</blockquote>
.
Document[0, 18]
  BlockQuote[0, 18] marker:[0, 1, ">"]
    Paragraph[2, 18]
      Text[2, 9] chars:[2, 9, "strike "]
      Strikethrough[9, 17] textOpen:[9, 11, "~~"] text:[11, 15, "that"] textClose:[15, 17, "~~"]
        Text[11, 15] chars:[11, 15, "that"]
````````````````````````````````


## Subscript Source Position Attribute

```````````````````````````````` example(Subscript Source Position Attribute: 1) options(src-pos)
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

```````````````````````````````` example Subscript Source Position Attribute: 2
~~DiHydrogen Oxide~~ H~2~O
.
<p><del>DiHydrogen Oxide</del> H<sub>2</sub>O</p>
.
Document[0, 27]
  Paragraph[0, 27]
    Strikethrough[0, 20] textOpen:[0, 2, "~~"] text:[2, 18, "DiHyd … rogen Oxide"] textClose:[18, 20, "~~"]
      Text[2, 18] chars:[2, 18, "DiHyd … Oxide"]
    Text[20, 22] chars:[20, 22, " H"]
    Subscript[22, 25] textOpen:[22, 23, "~"] text:[23, 24, "2"] textClose:[24, 25, "~"]
      Text[23, 24] chars:[23, 24, "2"]
    Text[25, 26] chars:[25, 26, "O"]
````````````````````````````````




