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

```````````````````````````````` example Gfm Strikethrough Extension: 1
~foo~
.
<p>~foo~</p>
.
Document[0, 5]
  Paragraph[0, 5]
    Text[0, 5] chars:[0, 5, "~foo~"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 2
~~foo~~
.
<p><del>foo</del></p>
.
Document[0, 7]
  Paragraph[0, 7]
    Strikethrough[0, 7] textOpen:[0, 2, "~~"] text:[2, 5, "foo"] textClose:[5, 7, "~~"]
      Text[2, 5] chars:[2, 5, "foo"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 3
foo ~~~~
.
<p>foo ~~~~</p>
.
Document[0, 8]
  Paragraph[0, 8]
    Text[0, 8] chars:[0, 8, "foo ~~~~"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 4
~~foo
.
<p>~~foo</p>
.
Document[0, 5]
  Paragraph[0, 5]
    Text[0, 5] chars:[0, 5, "~~foo"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 5
foo~~
.
<p>foo~~</p>
.
Document[0, 5]
  Paragraph[0, 5]
    Text[0, 5] chars:[0, 5, "foo~~"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 6
~~~foo~~~
.
<p>~<del>foo</del>~</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 1] chars:[0, 1, "~"]
    Strikethrough[1, 8] textOpen:[1, 3, "~~"] text:[3, 6, "foo"] textClose:[6, 8, "~~"]
      Text[3, 6] chars:[3, 6, "foo"]
    Text[8, 9] chars:[8, 9, "~"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 7
~~foo~~~
.
<p><del>foo</del>~</p>
.
Document[0, 8]
  Paragraph[0, 8]
    Strikethrough[0, 7] textOpen:[0, 2, "~~"] text:[2, 5, "foo"] textClose:[5, 7, "~~"]
      Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 8] chars:[7, 8, "~"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 8
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
<p><del>foo</del><del>bar</del></p>
<p><del>foo</del>~<del>bar</del></p>
<p><del>foo</del>~~<del>bar</del></p>
<p><del>foo</del>~~~<del>bar</del></p>
.
Document[0, 110]
  Paragraph[0, 12] isTrailingBlankLine
    Strikethrough[0, 11] textOpen:[0, 2, "~~"] text:[2, 9, "foo~bar"] textClose:[9, 11, "~~"]
      Text[2, 9] chars:[2, 9, "foo~bar"]
  Paragraph[13, 26] isTrailingBlankLine
    Strikethrough[13, 20] textOpen:[13, 15, "~~"] text:[15, 18, "foo"] textClose:[18, 20, "~~"]
      Text[15, 18] chars:[15, 18, "foo"]
    Text[20, 25] chars:[20, 25, "bar~~"]
  Paragraph[27, 41] isTrailingBlankLine
    Strikethrough[27, 34] textOpen:[27, 29, "~~"] text:[29, 32, "foo"] textClose:[32, 34, "~~"]
      Text[29, 32] chars:[29, 32, "foo"]
    Text[34, 40] chars:[34, 40, "~bar~~"]
  Paragraph[42, 57] isTrailingBlankLine
    Strikethrough[42, 49] textOpen:[42, 44, "~~"] text:[44, 47, "foo"] textClose:[47, 49, "~~"]
      Text[44, 47] chars:[44, 47, "foo"]
    Strikethrough[49, 56] textOpen:[49, 51, "~~"] text:[51, 54, "bar"] textClose:[54, 56, "~~"]
      Text[51, 54] chars:[51, 54, "bar"]
  Paragraph[58, 74] isTrailingBlankLine
    Strikethrough[58, 65] textOpen:[58, 60, "~~"] text:[60, 63, "foo"] textClose:[63, 65, "~~"]
      Text[60, 63] chars:[60, 63, "foo"]
    Text[65, 66] chars:[65, 66, "~"]
    Strikethrough[66, 73] textOpen:[66, 68, "~~"] text:[68, 71, "bar"] textClose:[71, 73, "~~"]
      Text[68, 71] chars:[68, 71, "bar"]
  Paragraph[75, 92] isTrailingBlankLine
    Strikethrough[75, 82] textOpen:[75, 77, "~~"] text:[77, 80, "foo"] textClose:[80, 82, "~~"]
      Text[77, 80] chars:[77, 80, "foo"]
    Text[82, 84] chars:[82, 84, "~~"]
    Strikethrough[84, 91] textOpen:[84, 86, "~~"] text:[86, 89, "bar"] textClose:[89, 91, "~~"]
      Text[86, 89] chars:[86, 89, "bar"]
  Paragraph[93, 110]
    Strikethrough[93, 100] textOpen:[93, 95, "~~"] text:[95, 98, "foo"] textClose:[98, 100, "~~"]
      Text[95, 98] chars:[95, 98, "foo"]
    Text[100, 103] chars:[100, 103, "~~~"]
    Strikethrough[103, 110] textOpen:[103, 105, "~~"] text:[105, 108, "bar"] textClose:[108, 110, "~~"]
      Text[105, 108] chars:[105, 108, "bar"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 9
~~Paragraph with *emphasis* and __strong emphasis__~~
.
<p><del>Paragraph with <em>emphasis</em> and <strong>strong emphasis</strong></del></p>
.
Document[0, 53]
  Paragraph[0, 53]
    Strikethrough[0, 53] textOpen:[0, 2, "~~"] text:[2, 51, "Parag … raph with *emphasis* and __strong emphasis__"] textClose:[51, 53, "~~"]
      Text[2, 17] chars:[2, 17, "Parag … with "]
      Emphasis[17, 27] textOpen:[17, 18, "*"] text:[18, 26, "emphasis"] textClose:[26, 27, "*"]
        Text[18, 26] chars:[18, 26, "emphasis"]
      Text[27, 32] chars:[27, 32, " and "]
      StrongEmphasis[32, 51] textOpen:[32, 34, "__"] text:[34, 49, "strong emphasis"] textClose:[49, 51, "__"]
        Text[34, 49] chars:[34, 49, "stron … hasis"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 10
> strike ~~that~~
.
<blockquote>
<p>strike <del>that</del></p>
</blockquote>
.
Document[0, 17]
  BlockQuote[0, 17] marker:[0, 1, ">"]
    Paragraph[2, 17]
      Text[2, 9] chars:[2, 9, "strike "]
      Strikethrough[9, 17] textOpen:[9, 11, "~~"] text:[11, 15, "that"] textClose:[15, 17, "~~"]
        Text[11, 15] chars:[11, 15, "that"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
~~Paragraph with *emphasis* and __strong emphasis__~~
.
<p md-pos="0-53"><del md-pos="2-51">Paragraph with <em md-pos="18-26">emphasis</em> and <strong md-pos="34-49">strong emphasis</strong></del></p>
.
Document[0, 53]
  Paragraph[0, 53]
    Strikethrough[0, 53] textOpen:[0, 2, "~~"] text:[2, 51, "Parag … raph with *emphasis* and __strong emphasis__"] textClose:[51, 53, "~~"]
      Text[2, 17] chars:[2, 17, "Parag … with "]
      Emphasis[17, 27] textOpen:[17, 18, "*"] text:[18, 26, "emphasis"] textClose:[26, 27, "*"]
        Text[18, 26] chars:[18, 26, "emphasis"]
      Text[27, 32] chars:[27, 32, " and "]
      StrongEmphasis[32, 51] textOpen:[32, 34, "__"] text:[34, 49, "strong emphasis"] textClose:[49, 51, "__"]
        Text[34, 49] chars:[34, 49, "stron … hasis"]
````````````````````````````````


## Custom Style HTML

```````````````````````````````` example(Custom Style HTML: 1) options(style-strikethrough)
~~strikethrough~~

~subscript~

.
<p><span class="text-strike">strikethrough</span></p>
<p>~subscript~</p>
.
Document[0, 32]
  Paragraph[0, 18] isTrailingBlankLine
    Strikethrough[0, 17] textOpen:[0, 2, "~~"] text:[2, 15, "strik … ethrough"] textClose:[15, 17, "~~"]
      Text[2, 15] chars:[2, 15, "strik … rough"]
  Paragraph[19, 31] isTrailingBlankLine
    Text[19, 30] chars:[19, 30, "~subs … ript~"]
````````````````````````````````


