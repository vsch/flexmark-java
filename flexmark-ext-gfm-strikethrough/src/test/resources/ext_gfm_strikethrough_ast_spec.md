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

The tests here are converted to commonmark spec.txt format and AST
expected results added.

```````````````````````````````` example Gfm Strikethrough Extension: 1
~foo~
.
<p>~foo~</p>
.
Document[0, 6]
  Paragraph[0, 6]
    Text[0, 5] chars:[0, 5, "~foo~"]
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
<p>~<del>foo</del>~</p>
.
Document[0, 10]
  Paragraph[0, 10]
    Text[0, 1] chars:[0, 1, "~"]
    Strikethrough[1, 8] textOpen:[1, 3, "~~"] text:[3, 6, "foo"] textClose:[6, 8, "~~"]
      Text[3, 6] chars:[3, 6, "foo"]
    Text[6, 7] chars:[6, 7, "~"]
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
~~foo~~~~bar~~
.
<p><del>foo</del><del>bar</del></p>
.
Document[0, 15]
  Paragraph[0, 15]
    Strikethrough[0, 7] textOpen:[0, 2, "~~"] text:[2, 5, "foo"] textClose:[5, 7, "~~"]
      Text[2, 5] chars:[2, 5, "foo"]
    Strikethrough[5, 14] textOpen:[5, 7, "~~"] text:[7, 12, "~~bar"] textClose:[12, 14, "~~"]
      Text[9, 12] chars:[9, 12, "bar"]
````````````````````````````````


```````````````````````````````` example Gfm Strikethrough Extension: 9
~~Paragraph with *emphasis* and __strong emphasis__~~
.
<p><del>Paragraph with <em>emphasis</em> and <strong>strong emphasis</strong></del></p>
.
Document[0, 54]
  Paragraph[0, 54]
    Strikethrough[0, 53] textOpen:[0, 2, "~~"] text:[2, 51, "Parag"..."raph with *emphasis* and __strong emphasis__"] textClose:[51, 53, "~~"]
      Text[2, 17] chars:[2, 17, "Parag"..."with "]
      Emphasis[17, 27] textOpen:[17, 18, "*"] text:[18, 26, "emphasis"] textClose:[26, 27, "*"]
        Text[18, 26] chars:[18, 26, "emphasis"]
      Text[27, 32] chars:[27, 32, " and "]
      StrongEmphasis[32, 51] textOpen:[32, 34, "__"] text:[34, 49, "strong emphasis"] textClose:[49, 51, "__"]
        Text[34, 49] chars:[34, 49, "stron"..."hasis"]
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


