---
title: Aside Extension Spec
author: Vladimir Schneider
version:
date: '2016-09-27'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Aside

Converts text prefixed with `|` to aside tags, same as block quotes, some examples are copied
from the commonmark spec for block quotes with the marker changed to `|`

no trailing pipes allowed, reserved for tables

```````````````````````````````` example Aside: 1
| Sample text |
.
<p>| Sample text |</p>
.
Document[0, 15]
  Paragraph[0, 15]
    Text[0, 15] chars:[0, 15, "| Sam … ext |"]
````````````````````````````````


no trailing pipes allowed, reserved for tables

```````````````````````````````` example Aside: 2
| Sample text |   
.
<p>| Sample text |</p>
.
Document[0, 18]
  Paragraph[0, 18]
    Text[0, 15] chars:[0, 15, "| Sam … ext |"]
````````````````````````````````


```````````````````````````````` example Aside: 3
| # Foo
| bar
| baz
.
<aside>
  <h1>Foo</h1>
  <p>bar
  baz</p>
</aside>
.
Document[0, 19]
  AsideBlock[0, 19] marker:[0, 1, "|"]
    Heading[2, 7] textOpen:[2, 3, "#"] text:[4, 7, "Foo"]
      Text[4, 7] chars:[4, 7, "Foo"]
    Paragraph[10, 19]
      Text[10, 13] chars:[10, 13, "bar"]
      SoftLineBreak[13, 14]
      Text[16, 19] chars:[16, 19, "baz"]
````````````````````````````````


The spaces after the `|` characters can be omitted:

```````````````````````````````` example Aside: 4
|# Foo
|bar
| baz
.
<aside>
  <h1>Foo</h1>
  <p>bar
  baz</p>
</aside>
.
Document[0, 17]
  AsideBlock[0, 17] marker:[0, 1, "|"]
    Heading[1, 6] textOpen:[1, 2, "#"] text:[3, 6, "Foo"]
      Text[3, 6] chars:[3, 6, "Foo"]
    Paragraph[8, 17]
      Text[8, 11] chars:[8, 11, "bar"]
      SoftLineBreak[11, 12]
      Text[14, 17] chars:[14, 17, "baz"]
````````````````````````````````


The `|` characters can be indented 1-3 spaces:

```````````````````````````````` example Aside: 5
   | # Foo
   | bar
 | baz
.
<aside>
  <h1>Foo</h1>
  <p>bar
  baz</p>
</aside>
.
Document[0, 26]
  AsideBlock[3, 26] marker:[3, 4, "|"]
    Heading[5, 10] textOpen:[5, 6, "#"] text:[7, 10, "Foo"]
      Text[7, 10] chars:[7, 10, "Foo"]
    Paragraph[16, 26]
      Text[16, 19] chars:[16, 19, "bar"]
      SoftLineBreak[19, 20]
      Text[23, 26] chars:[23, 26, "baz"]
````````````````````````````````


Four spaces gives us a code block:

```````````````````````````````` example Aside: 6
    | # Foo
    | bar
    | baz
.
<pre><code>| # Foo
| bar
| baz
</code></pre>
.
Document[0, 31]
  IndentedCodeBlock[4, 31]
````````````````````````````````


The Laziness clause allows us to omit the `|` before [paragraph continuation text]:

```````````````````````````````` example Aside: 7
| # Foo
| bar
baz
.
<aside>
  <h1>Foo</h1>
  <p>bar
  baz</p>
</aside>
.
Document[0, 17]
  AsideBlock[0, 17] marker:[0, 1, "|"]
    Heading[2, 7] textOpen:[2, 3, "#"] text:[4, 7, "Foo"]
      Text[4, 7] chars:[4, 7, "Foo"]
    Paragraph[10, 17]
      Text[10, 13] chars:[10, 13, "bar"]
      SoftLineBreak[13, 14]
      Text[14, 17] chars:[14, 17, "baz"]
````````````````````````````````


A block quote can contain some lazy and some non-lazy continuation lines:

```````````````````````````````` example Aside: 8
| bar
baz
| foo
.
<aside>
  <p>bar
  baz
  foo</p>
</aside>
.
Document[0, 15]
  AsideBlock[0, 15] marker:[0, 1, "|"]
    Paragraph[2, 15]
      Text[2, 5] chars:[2, 5, "bar"]
      SoftLineBreak[5, 6]
      Text[6, 9] chars:[6, 9, "baz"]
      SoftLineBreak[9, 10]
      Text[12, 15] chars:[12, 15, "foo"]
````````````````````````````````


Laziness only applies to lines that would have been continuations of paragraphs had they been
prepended with [block quote markers]. For example, the `| ` cannot be omitted in the second line
of

```markdown
| foo
| ---
```

without changing the meaning:

```````````````````````````````` example Aside: 9
| foo
---
.
<aside>
  <p>foo</p>
</aside>
<hr />
.
Document[0, 9]
  AsideBlock[0, 6] marker:[0, 1, "|"]
    Paragraph[2, 6]
      Text[2, 5] chars:[2, 5, "foo"]
  ThematicBreak[6, 9]
````````````````````````````````


Similarly, if we omit the `| ` in the second line of

```markdown
| - foo
| - bar
```

then the block quote ends after the first line:

```````````````````````````````` example Aside: 10
| - foo
- bar
.
<aside>
  <ul>
    <li>foo</li>
  </ul>
</aside>
<ul>
  <li>bar</li>
</ul>
.
Document[0, 13]
  AsideBlock[0, 8] marker:[0, 1, "|"]
    BulletList[2, 8] isTight
      BulletListItem[2, 8] open:[2, 3, "-"] isTight
        Paragraph[4, 8]
          Text[4, 7] chars:[4, 7, "foo"]
  BulletList[8, 13] isTight
    BulletListItem[8, 13] open:[8, 9, "-"] isTight
      Paragraph[10, 13]
        Text[10, 13] chars:[10, 13, "bar"]
````````````````````````````````


For the same reason, we can't omit the `| ` in front of subsequent lines of an indented or
fenced code block:

```````````````````````````````` example Aside: 11
|     foo
    bar
.
<aside>
  <pre><code>foo
</code></pre>
</aside>
<pre><code>bar
</code></pre>
.
Document[0, 17]
  AsideBlock[0, 10] marker:[0, 1, "|"]
    IndentedCodeBlock[6, 10]
  IndentedCodeBlock[14, 17]
````````````````````````````````


```````````````````````````````` example Aside: 12
| ```
foo
```
.
<aside>
  <pre><code></code></pre>
</aside>
<p>foo</p>
<pre><code></code></pre>
.
Document[0, 13]
  AsideBlock[0, 6] marker:[0, 1, "|"]
    FencedCodeBlock[2, 6] open:[2, 5, "```"] lines[0]
  Paragraph[6, 10]
    Text[6, 9] chars:[6, 9, "foo"]
  FencedCodeBlock[10, 13] open:[10, 13, "```"] lines[0]
````````````````````````````````


Note that in the following case, we have a [lazy continuation line]:

```````````````````````````````` example Aside: 13
| foo
    - bar
.
<aside>
  <p>foo
  - bar</p>
</aside>
.
Document[0, 15]
  AsideBlock[0, 15] marker:[0, 1, "|"]
    Paragraph[2, 15]
      Text[2, 5] chars:[2, 5, "foo"]
      SoftLineBreak[5, 6]
      Text[10, 15] chars:[10, 15, "- bar"]
````````````````````````````````


To see why, note that in

```markdown
| foo
|     - bar
```

the `- bar` is indented too far to start a list, and can't be an indented code block because
indented code blocks cannot interrupt paragraphs, so it is [paragraph continuation text].

A block quote can be empty:

```````````````````````````````` example Aside: 14
|
.
<aside></aside>
.
Document[0, 1]
  AsideBlock[0, 1] marker:[0, 1, "|"]
````````````````````````````````


```````````````````````````````` example(Aside: 15) options(blank-lines)
|
.
<aside></aside>
.
Document[0, 2]
  AsideBlock[0, 2] marker:[0, 1, "|"]
    BlankLine[0, 2]
````````````````````````````````


```````````````````````````````` example Aside: 16
|
|  
| 
.
<aside></aside>
.
Document[0, 8]
  AsideBlock[0, 8] marker:[0, 1, "|"]
````````````````````````````````


```````````````````````````````` example(Aside: 17) options(blank-lines)
|
|  
| 
.
<aside></aside>
.
Document[0, 9]
  AsideBlock[0, 9] marker:[0, 1, "|"]
    BlankLine[0, 2]
    BlankLine[2, 6]
    BlankLine[6, 9]
````````````````````````````````


Can have initial or final blank lines:

```````````````````````````````` example Aside: 18
|
| foo
|  
.
<aside>
  <p>foo</p>
</aside>
.
Document[0, 11]
  AsideBlock[0, 11] marker:[0, 1, "|"]
    Paragraph[4, 8] isTrailingBlankLine
      Text[4, 7] chars:[4, 7, "foo"]
````````````````````````````````


```````````````````````````````` example(Aside: 19) options(blank-lines)
|
| foo
|  
.
<aside>
  <p>foo</p>
</aside>
.
Document[0, 12]
  AsideBlock[0, 12] marker:[0, 1, "|"]
    BlankLine[0, 2]
    Paragraph[4, 8] isTrailingBlankLine
      Text[4, 7] chars:[4, 7, "foo"]
    BlankLine[8, 12]
````````````````````````````````


A blank line always separates block quotes:

```````````````````````````````` example Aside: 20
| foo

| bar
.
<aside>
  <p>foo</p>
</aside>
<aside>
  <p>bar</p>
</aside>
.
Document[0, 12]
  AsideBlock[0, 6] marker:[0, 1, "|"]
    Paragraph[2, 6]
      Text[2, 5] chars:[2, 5, "foo"]
  AsideBlock[7, 12] marker:[7, 8, "|"]
    Paragraph[9, 12]
      Text[9, 12] chars:[9, 12, "bar"]
````````````````````````````````


(Most current Markdown implementations, including John Gruber's original `Markdown.pl`, will
parse this example as a single block quote with two paragraphs. But it seems better to allow the
author to decide whether two block quotes or one are wanted.)

Consecutiveness means that if we put these block quotes together, we get a single block quote:

```````````````````````````````` example Aside: 21
| foo
| bar
.
<aside>
  <p>foo
  bar</p>
</aside>
.
Document[0, 11]
  AsideBlock[0, 11] marker:[0, 1, "|"]
    Paragraph[2, 11]
      Text[2, 5] chars:[2, 5, "foo"]
      SoftLineBreak[5, 6]
      Text[8, 11] chars:[8, 11, "bar"]
````````````````````````````````


To get a block with two paragraphs, use:

```````````````````````````````` example Aside: 22
| foo
|
| bar
.
<aside>
  <p>foo</p>
  <p>bar</p>
</aside>
.
Document[0, 13]
  AsideBlock[0, 13] marker:[0, 1, "|"]
    Paragraph[2, 6] isTrailingBlankLine
      Text[2, 5] chars:[2, 5, "foo"]
    Paragraph[10, 13]
      Text[10, 13] chars:[10, 13, "bar"]
````````````````````````````````


```````````````````````````````` example(Aside: 23) options(blank-lines)
| foo
|
| bar
.
<aside>
  <p>foo</p>
  <p>bar</p>
</aside>
.
Document[0, 14]
  AsideBlock[0, 14] marker:[0, 1, "|"]
    Paragraph[2, 6] isTrailingBlankLine
      Text[2, 5] chars:[2, 5, "foo"]
    BlankLine[6, 8]
    Paragraph[10, 14]
      Text[10, 13] chars:[10, 13, "bar"]
````````````````````````````````


Block can interrupt paragraphs:

```````````````````````````````` example Aside: 24
foo
| bar
.
<p>foo</p>
<aside>
  <p>bar</p>
</aside>
.
Document[0, 9]
  Paragraph[0, 4]
    Text[0, 3] chars:[0, 3, "foo"]
  AsideBlock[4, 9] marker:[4, 5, "|"]
    Paragraph[6, 9]
      Text[6, 9] chars:[6, 9, "bar"]
````````````````````````````````


In general, blank lines are not needed before or after marker:

```````````````````````````````` example Aside: 25
| aaa
***
| bbb
.
<aside>
  <p>aaa</p>
</aside>
<hr />
<aside>
  <p>bbb</p>
</aside>
.
Document[0, 15]
  AsideBlock[0, 6] marker:[0, 1, "|"]
    Paragraph[2, 6]
      Text[2, 5] chars:[2, 5, "aaa"]
  ThematicBreak[6, 9]
  AsideBlock[10, 15] marker:[10, 11, "|"]
    Paragraph[12, 15]
      Text[12, 15] chars:[12, 15, "bbb"]
````````````````````````````````


However, because of laziness, a blank line is needed between a block and a following paragraph:

```````````````````````````````` example Aside: 26
| bar
baz
.
<aside>
  <p>bar
  baz</p>
</aside>
.
Document[0, 9]
  AsideBlock[0, 9] marker:[0, 1, "|"]
    Paragraph[2, 9]
      Text[2, 5] chars:[2, 5, "bar"]
      SoftLineBreak[5, 6]
      Text[6, 9] chars:[6, 9, "baz"]
````````````````````````````````


```````````````````````````````` example Aside: 27
| bar

baz
.
<aside>
  <p>bar</p>
</aside>
<p>baz</p>
.
Document[0, 10]
  AsideBlock[0, 6] marker:[0, 1, "|"]
    Paragraph[2, 6]
      Text[2, 5] chars:[2, 5, "bar"]
  Paragraph[7, 10]
    Text[7, 10] chars:[7, 10, "baz"]
````````````````````````````````


```````````````````````````````` example Aside: 28
| bar
|
baz
.
<aside>
  <p>bar</p>
</aside>
<p>baz</p>
.
Document[0, 11]
  AsideBlock[0, 8] marker:[0, 1, "|"]
    Paragraph[2, 6] isTrailingBlankLine
      Text[2, 5] chars:[2, 5, "bar"]
  Paragraph[8, 11]
    Text[8, 11] chars:[8, 11, "baz"]
````````````````````````````````


```````````````````````````````` example(Aside: 29) options(blank-lines)
| bar
|
baz
.
<aside>
  <p>bar</p>
</aside>
<p>baz</p>
.
Document[0, 12]
  AsideBlock[0, 8] marker:[0, 1, "|"]
    Paragraph[2, 6] isTrailingBlankLine
      Text[2, 5] chars:[2, 5, "bar"]
    BlankLine[6, 8]
  Paragraph[8, 12]
    Text[8, 11] chars:[8, 11, "baz"]
````````````````````````````````


It is a consequence of the Laziness rule that any number of initial `|`s may be omitted on a
continuation line of a nested block quote:

```````````````````````````````` example Aside: 30
| | | foo
bar
.
<aside>
  <aside>
    <aside>
      <p>foo
      bar</p>
    </aside>
  </aside>
</aside>
.
Document[0, 13]
  AsideBlock[0, 13] marker:[0, 1, "|"]
    AsideBlock[2, 13] marker:[2, 3, "|"]
      AsideBlock[4, 13] marker:[4, 5, "|"]
        Paragraph[6, 13]
          Text[6, 9] chars:[6, 9, "foo"]
          SoftLineBreak[9, 10]
          Text[10, 13] chars:[10, 13, "bar"]
````````````````````````````````


```````````````````````````````` example Aside: 31
||| foo
| bar
||baz
.
<aside>
  <aside>
    <aside>
      <p>foo
      bar
      baz</p>
    </aside>
  </aside>
</aside>
.
Document[0, 19]
  AsideBlock[0, 19] marker:[0, 1, "|"]
    AsideBlock[1, 19] marker:[1, 2, "|"]
      AsideBlock[2, 19] marker:[2, 3, "|"]
        Paragraph[4, 19]
          Text[4, 7] chars:[4, 7, "foo"]
          SoftLineBreak[7, 8]
          Text[10, 13] chars:[10, 13, "bar"]
          SoftLineBreak[13, 14]
          Text[16, 19] chars:[16, 19, "baz"]
````````````````````````````````


When including an indented code block in a block quote, remember that the [block quote marker]
includes both the `|` and a following space. So *five spaces* are needed after the `|`:

```````````````````````````````` example Aside: 32
|     code

|    not code
.
<aside>
  <pre><code>code
</code></pre>
</aside>
<aside>
  <p>not code</p>
</aside>
.
Document[0, 25]
  AsideBlock[0, 11] marker:[0, 1, "|"]
    IndentedCodeBlock[6, 11]
  AsideBlock[12, 25] marker:[12, 13, "|"]
    Paragraph[17, 25]
      Text[17, 25] chars:[17, 25, "not code"]
````````````````````````````````


Extend block quote to next blank line

```````````````````````````````` example(Aside: 33) options(extend-to-blank-line)
| 1. one
2. two
.
<aside>
  <ol>
    <li>one</li>
    <li>two</li>
  </ol>
</aside>
.
Document[0, 15]
  AsideBlock[0, 15] marker:[0, 1, "|"]
    OrderedList[2, 15] isTight delimiter:'.'
      OrderedListItem[2, 9] open:[2, 4, "1."] isTight
        Paragraph[5, 9]
          Text[5, 8] chars:[5, 8, "one"]
      OrderedListItem[9, 15] open:[9, 11, "2."] isTight
        Paragraph[12, 15]
          Text[12, 15] chars:[12, 15, "two"]
````````````````````````````````


without aside to next blank line causes an interrupted list with a second list after the quote.

```````````````````````````````` example Aside: 34
| 1. one
2. two
.
<aside>
  <ol>
    <li>one</li>
  </ol>
</aside>
<ol start="2">
  <li>two</li>
</ol>
.
Document[0, 15]
  AsideBlock[0, 9] marker:[0, 1, "|"]
    OrderedList[2, 9] isTight delimiter:'.'
      OrderedListItem[2, 9] open:[2, 4, "1."] isTight
        Paragraph[5, 9]
          Text[5, 8] chars:[5, 8, "one"]
  OrderedList[9, 15] isTight start:2 delimiter:'.'
    OrderedListItem[9, 15] open:[9, 11, "2."] isTight
      Paragraph[12, 15]
        Text[12, 15] chars:[12, 15, "two"]
````````````````````````````````


Asides don't ignore interspersing blank lines

```````````````````````````````` example Aside: 35
| Block Quote
|| Nested Quote
||| Another Quote

|||| Nested Quote

.
<aside>
  <p>Block Quote</p>
  <aside>
    <p>Nested Quote</p>
    <aside>
      <p>Another Quote</p>
    </aside>
  </aside>
</aside>
<aside>
  <aside>
    <aside>
      <aside>
        <p>Nested Quote</p>
      </aside>
    </aside>
  </aside>
</aside>
.
Document[0, 68]
  AsideBlock[0, 48] marker:[0, 1, "|"]
    Paragraph[2, 14]
      Text[2, 13] chars:[2, 13, "Block … Quote"]
    AsideBlock[15, 48] marker:[15, 16, "|"]
      Paragraph[17, 30]
        Text[17, 29] chars:[17, 29, "Neste … Quote"]
      AsideBlock[32, 48] marker:[32, 33, "|"]
        Paragraph[34, 48]
          Text[34, 47] chars:[34, 47, "Anoth … Quote"]
  AsideBlock[49, 67] marker:[49, 50, "|"]
    AsideBlock[50, 67] marker:[50, 51, "|"]
      AsideBlock[51, 67] marker:[51, 52, "|"]
        AsideBlock[52, 67] marker:[52, 53, "|"]
          Paragraph[54, 67]
            Text[54, 66] chars:[54, 66, "Neste … Quote"]
````````````````````````````````


Block quotes ignore interspersing blank lines

```````````````````````````````` example(Aside: 36) options(ignore-blank-line)
| Block Quote
|| Nested Quote
||| Another Quote

|||| Nested Quote

.
<aside>
  <p>Block Quote</p>
  <aside>
    <p>Nested Quote</p>
    <aside>
      <p>Another Quote</p>
      <aside>
        <p>Nested Quote</p>
      </aside>
    </aside>
  </aside>
</aside>
.
Document[0, 68]
  AsideBlock[0, 67] marker:[0, 1, "|"]
    Paragraph[2, 14]
      Text[2, 13] chars:[2, 13, "Block … Quote"]
    AsideBlock[15, 67] marker:[15, 16, "|"]
      Paragraph[17, 30]
        Text[17, 29] chars:[17, 29, "Neste … Quote"]
      AsideBlock[32, 67] marker:[32, 33, "|"]
        Paragraph[34, 48] isTrailingBlankLine
          Text[34, 47] chars:[34, 47, "Anoth … Quote"]
        AsideBlock[52, 67] marker:[52, 53, "|"]
          Paragraph[54, 67] isTrailingBlankLine
            Text[54, 66] chars:[54, 66, "Neste … Quote"]
````````````````````````````````


Block quotes ignore interspersing blank lines but don't include any lines without prefix after
blank lines

```````````````````````````````` example(Aside: 37) options(ignore-blank-line, extend-to-blank-line)
| Block Quote 
| this is still block quote 


this is not

.
<aside>
  <p>Block Quote
  this is still block quote</p>
</aside>
<p>this is not</p>
.
Document[0, 59]
  AsideBlock[0, 44] marker:[0, 1, "|"]
    Paragraph[2, 44] isTrailingBlankLine
      Text[2, 13] chars:[2, 13, "Block … Quote"]
      SoftLineBreak[14, 15]
      Text[17, 42] chars:[17, 42, "this  … quote"]
  Paragraph[46, 58] isTrailingBlankLine
    Text[46, 57] chars:[46, 57, "this  … s not"]
````````````````````````````````


## Issue MN-01

blank line in block quoted list breaks list

```````````````````````````````` example(Issue MN-01: 1) options(ignore-blank-line, extend-to-blank-line)
> 1. item
> 2. item

> 3. item

.
<blockquote>
  <ol>
    <li>
      <p>item</p>
    </li>
    <li>
      <p>item</p>
    </li>
    <li>
      <p>item</p>
    </li>
  </ol>
</blockquote>
.
Document[0, 32]
  BlockQuote[0, 31] marker:[0, 1, ">"]
    OrderedList[2, 31] isLoose delimiter:'.'
      OrderedListItem[2, 10] open:[2, 4, "1."] isLoose
        Paragraph[5, 10]
          Text[5, 9] chars:[5, 9, "item"]
      OrderedListItem[12, 20] open:[12, 14, "2."] isLoose hadBlankLineAfter
        Paragraph[15, 20] isTrailingBlankLine
          Text[15, 19] chars:[15, 19, "item"]
      OrderedListItem[23, 31] open:[23, 25, "3."] isLoose hadBlankLineAfter
        Paragraph[26, 31] isTrailingBlankLine
          Text[26, 30] chars:[26, 30, "item"]
````````````````````````````````


```````````````````````````````` example(Issue MN-01: 2) options(ignore-blank-line, extend-to-blank-line)
| 1. item
| 2. item

| 3. item


.
<aside>
  <ol>
    <li>
      <p>item</p>
    </li>
    <li>
      <p>item</p>
    </li>
    <li>
      <p>item</p>
    </li>
  </ol>
</aside>
.
Document[0, 33]
  AsideBlock[0, 31] marker:[0, 1, "|"]
    OrderedList[2, 31] isLoose delimiter:'.'
      OrderedListItem[2, 10] open:[2, 4, "1."] isLoose
        Paragraph[5, 10]
          Text[5, 9] chars:[5, 9, "item"]
      OrderedListItem[12, 20] open:[12, 14, "2."] isLoose hadBlankLineAfter
        Paragraph[15, 20] isTrailingBlankLine
          Text[15, 19] chars:[15, 19, "item"]
      OrderedListItem[23, 31] open:[23, 25, "3."] isLoose hadBlankLineAfter
        Paragraph[26, 31] isTrailingBlankLine
          Text[26, 30] chars:[26, 30, "item"]
````````````````````````````````


```````````````````````````````` example(Issue MN-01: 3) options(extend-to-blank-line)
> 1. item
> 2. item

> 3. item

.
<blockquote>
  <ol>
    <li>item</li>
    <li>item</li>
  </ol>
</blockquote>
<blockquote>
  <ol start="3">
    <li>item</li>
  </ol>
</blockquote>
.
Document[0, 32]
  BlockQuote[0, 20] marker:[0, 1, ">"]
    OrderedList[2, 20] isTight delimiter:'.'
      OrderedListItem[2, 10] open:[2, 4, "1."] isTight
        Paragraph[5, 10]
          Text[5, 9] chars:[5, 9, "item"]
      OrderedListItem[12, 20] open:[12, 14, "2."] isTight
        Paragraph[15, 20]
          Text[15, 19] chars:[15, 19, "item"]
  BlockQuote[21, 31] marker:[21, 22, ">"]
    OrderedList[23, 31] isTight start:3 delimiter:'.'
      OrderedListItem[23, 31] open:[23, 25, "3."] isTight
        Paragraph[26, 31]
          Text[26, 30] chars:[26, 30, "item"]
````````````````````````````````


```````````````````````````````` example(Issue MN-01: 4) options(extend-to-blank-line)
| 1. item
| 2. item

| 3. item


.
<aside>
  <ol>
    <li>item</li>
    <li>item</li>
  </ol>
</aside>
<aside>
  <ol start="3">
    <li>item</li>
  </ol>
</aside>
.
Document[0, 33]
  AsideBlock[0, 20] marker:[0, 1, "|"]
    OrderedList[2, 20] isTight delimiter:'.'
      OrderedListItem[2, 10] open:[2, 4, "1."] isTight
        Paragraph[5, 10]
          Text[5, 9] chars:[5, 9, "item"]
      OrderedListItem[12, 20] open:[12, 14, "2."] isTight
        Paragraph[15, 20]
          Text[15, 19] chars:[15, 19, "item"]
  AsideBlock[21, 31] marker:[21, 22, "|"]
    OrderedList[23, 31] isTight start:3 delimiter:'.'
      OrderedListItem[23, 31] open:[23, 25, "3."] isTight
        Paragraph[26, 31]
          Text[26, 30] chars:[26, 30, "item"]
````````````````````````````````


should match block quote parsing information

```````````````````````````````` example(Issue MN-01: 5) options(ignore-blank-line, extend-to-blank-line)
>>| asdf asldf alsdf asldf asdf as dflasdjflasjdf as;ldf lasdfj asldfj
>>| asdfasdf asdf asdfasfd
> 11. alalsdfkj alsdjf lsa;fdj l;as fd;l sadf;l sajdf;l jasdf;l saldfj
>     da; sdlfja;sldfj ;als fdj;ajs fdl; sadfsdf jal;sfdj dsaf k
> 12. asdlfj al;sfdj adsl;fk lajs fdlas df al;dsfj;asldfj
>     asdf;asdfalsdkfj asldf jaslf;dj
> 13. lasdkjf asl;dfj dsalf; j
> 14. > safddsf
> 15. asdfasfddsf

.
<blockquote>
  <blockquote>
    <aside>
      <p>asdf asldf alsdf asldf asdf as dflasdjflasjdf as;ldf lasdfj asldfj
      asdfasdf asdf asdfasfd
      11. alalsdfkj alsdjf lsa;fdj l;as fd;l sadf;l sajdf;l jasdf;l saldfj
      da; sdlfja;sldfj ;als fdj;ajs fdl; sadfsdf jal;sfdj dsaf k
      12. asdlfj al;sfdj adsl;fk lajs fdlas df al;dsfj;asldfj
      asdf;asdfalsdkfj asldf jaslf;dj
      13. lasdkjf asl;dfj dsalf; j
      14. &gt; safddsf
      15. asdfasfddsf</p>
    </aside>
  </blockquote>
</blockquote>
.
Document[0, 396]
  BlockQuote[0, 395] marker:[0, 1, ">"]
    BlockQuote[1, 395] marker:[1, 2, ">"]
      AsideBlock[2, 395] marker:[2, 3, "|"]
        Paragraph[4, 395] isTrailingBlankLine
          Text[4, 70] chars:[4, 70, "asdf  … sldfj"]
          SoftLineBreak[70, 71]
          Text[75, 97] chars:[75, 97, "asdfa … fasfd"]
          SoftLineBreak[97, 98]
          Text[100, 168] chars:[100, 168, "11. a … aldfj"]
          SoftLineBreak[168, 169]
          Text[175, 233] chars:[175, 233, "da; s … saf k"]
          SoftLineBreak[233, 234]
          Text[236, 291] chars:[236, 291, "12. a … sldfj"]
          SoftLineBreak[291, 292]
          Text[298, 329] chars:[298, 329, "asdf; … lf;dj"]
          SoftLineBreak[329, 330]
          Text[332, 360] chars:[332, 360, "13. l … lf; j"]
          SoftLineBreak[360, 361]
          Text[363, 376] chars:[363, 376, "14. > … fddsf"]
          SoftLineBreak[376, 377]
          Text[379, 394] chars:[379, 394, "15. a … fddsf"]
````````````````````````````````


```````````````````````````````` example(Issue MN-01: 6) options(ignore-blank-line, extend-to-blank-line)
||> asdf asldf alsdf asldf asdf as dflasdjflasjdf as;ldf lasdfj asldfj
||> asdfasdf asdf asdfasfd
| 11. alalsdfkj alsdjf lsa;fdj l;as fd;l sadf;l sajdf;l jasdf;l saldfj
|     da; sdlfja;sldfj ;als fdj;ajs fdl; sadfsdf jal;sfdj dsaf k
| 12. asdlfj al;sfdj adsl;fk lajs fdlas df al;dsfj;asldfj
|     asdf;asdfalsdkfj asldf jaslf;dj
| 13. lasdkjf asl;dfj dsalf; j
| 14. | safddsf
| 15. asdfasfddsf

.
<aside>
  <aside>
    <blockquote>
      <p>asdf asldf alsdf asldf asdf as dflasdjflasjdf as;ldf lasdfj asldfj
      asdfasdf asdf asdfasfd
      11. alalsdfkj alsdjf lsa;fdj l;as fd;l sadf;l sajdf;l jasdf;l saldfj
      da; sdlfja;sldfj ;als fdj;ajs fdl; sadfsdf jal;sfdj dsaf k
      12. asdlfj al;sfdj adsl;fk lajs fdlas df al;dsfj;asldfj
      asdf;asdfalsdkfj asldf jaslf;dj
      13. lasdkjf asl;dfj dsalf; j
      14. | safddsf
      15. asdfasfddsf</p>
    </blockquote>
  </aside>
</aside>
.
Document[0, 396]
  AsideBlock[0, 395] marker:[0, 1, "|"]
    AsideBlock[1, 395] marker:[1, 2, "|"]
      BlockQuote[2, 395] marker:[2, 3, ">"]
        Paragraph[4, 395] isTrailingBlankLine
          Text[4, 70] chars:[4, 70, "asdf  … sldfj"]
          SoftLineBreak[70, 71]
          Text[75, 97] chars:[75, 97, "asdfa … fasfd"]
          SoftLineBreak[97, 98]
          Text[100, 168] chars:[100, 168, "11. a … aldfj"]
          SoftLineBreak[168, 169]
          Text[175, 233] chars:[175, 233, "da; s … saf k"]
          SoftLineBreak[233, 234]
          Text[236, 291] chars:[236, 291, "12. a … sldfj"]
          SoftLineBreak[291, 292]
          Text[298, 329] chars:[298, 329, "asdf; … lf;dj"]
          SoftLineBreak[329, 330]
          Text[332, 360] chars:[332, 360, "13. l … lf; j"]
          SoftLineBreak[360, 361]
          Text[363, 376] chars:[363, 376, "14. | … fddsf"]
          SoftLineBreak[376, 377]
          Text[379, 394] chars:[379, 394, "15. a … fddsf"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
| Block Quote 
| this is still block quote 
.
<aside>
  <p md-pos="2-43">Block Quote
  this is still block quote</p>
</aside>
.
Document[0, 43]
  AsideBlock[0, 43] marker:[0, 1, "|"]
    Paragraph[2, 43]
      Text[2, 13] chars:[2, 13, "Block … Quote"]
      SoftLineBreak[14, 15]
      Text[17, 42] chars:[17, 42, "this  … quote"]
````````````````````````````````


