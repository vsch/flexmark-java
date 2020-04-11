---
title: CommonMark Spec
author: John MacFarlane
version: 0.28
date: '2017-08-01'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

# Introduction

## What is Markdown?

Markdown is a plain text format for writing structured documents,
based on conventions for indicating formatting in email
and usenet posts.  It was developed by John Gruber (with
help from Aaron Swartz) and released in 2004 in the form of a
[syntax description](http://daringfireball.net/projects/markdown/syntax)
and a Perl script (`Markdown.pl`) for converting Markdown to
HTML.  In the next decade, dozens of implementations were
developed in many languages.  Some extended the original
Markdown syntax with conventions for footnotes, tables, and
other document elements.  Some allowed Markdown documents to be
rendered in formats other than HTML.  Websites like Reddit,
StackOverflow, and GitHub had millions of people using Markdown.
And Markdown started to be used beyond the web, to author books,
articles, slide shows, letters, and lecture notes.

What distinguishes Markdown from many other lightweight markup
syntaxes, which are often easier to write, is its readability.
As Gruber writes:

> The overriding design goal for Markdown's formatting syntax is
> to make it as readable as possible. The idea is that a
> Markdown-formatted document should be publishable as-is, as
> plain text, without looking like it's been marked up with tags
> or formatting instructions.
> (<http://daringfireball.net/projects/markdown/>)

The point can be illustrated by comparing a sample of
[AsciiDoc](http://www.methods.co.nz/asciidoc/) with
an equivalent sample of Markdown.  Here is a sample of
AsciiDoc from the AsciiDoc manual:

```
1. List item one.
+
List item one continued with a second paragraph followed by an
Indented block.
+
.................
$ ls *.sh
$ mv *.sh ~/tmp
.................
+
List item continued with a third paragraph.

2. List item two continued with an open block.
+
--
This paragraph is part of the preceding list item.

a. This list is nested and does not require explicit item
continuation.
+
This paragraph is part of the preceding list item.

b. List item b.

This paragraph belongs to item two of the outer list.
--
```

And here is the equivalent in Markdown:
```
1.  List item one.

    List item one continued with a second paragraph followed by an
    Indented block.

        $ ls *.sh
        $ mv *.sh ~/tmp

    List item continued with a third paragraph.

2.  List item two continued with an open block.

    This paragraph is part of the preceding list item.

    1. This list is nested and does not require explicit item continuation.

       This paragraph is part of the preceding list item.

    2. List item b.

    This paragraph belongs to item two of the outer list.
```

The AsciiDoc version is, arguably, easier to write. You don't need
to worry about indentation.  But the Markdown version is much easier
to read.  The nesting of list items is apparent to the eye in the
source, not just in the processed document.

## Why is a spec needed?

John Gruber's [canonical description of Markdown's
syntax](http://daringfireball.net/projects/markdown/syntax)
does not specify the syntax unambiguously.  Here are some examples of
questions it does not answer:

1.  How much indentation is needed for a sublist?  The spec says that
    continuation paragraphs need to be indented four spaces, but is
    not fully explicit about sublists.  It is natural to think that
    they, too, must be indented four spaces, but `Markdown.pl` does
    not require that.  This is hardly a "corner case," and divergences
    between implementations on this issue often lead to surprises for
    users in real documents. (See [this comment by John
    Gruber](http://article.gmane.org/gmane.text.markdown.general/1997).)

2.  Is a blank line needed before a block quote or heading?
    Most implementations do not require the blank line.  However,
    this can lead to unexpected results in hard-wrapped text, and
    also to ambiguities in parsing (note that some implementations
    put the heading inside the blockquote, while others do not).
    (John Gruber has also spoken [in favor of requiring the blank
    lines](http://article.gmane.org/gmane.text.markdown.general/2146).)

3.  Is a blank line needed before an indented code block?
    (`Markdown.pl` requires it, but this is not mentioned in the
    documentation, and some implementations do not require it.)

    ``` markdown
    paragraph
        code?
    ```

4.  What is the exact rule for determining when list items get
    wrapped in `<p>` tags?  Can a list be partially "loose" and partially
    "tight"?  What should we do with a list like this?

    ``` markdown
    1. one

    2. two
    3. three
    ```

    Or this?

    ``` markdown
    1.  one
        - a

        - b
    2.  two
    ```

    (There are some relevant comments by John Gruber
    [here](http://article.gmane.org/gmane.text.markdown.general/2554).)

5.  Can list markers be indented?  Can ordered list markers be right-aligned?

    ``` markdown
     8. item 1
     9. item 2
    10. item 2a
    ```

6.  Is this one list with a thematic break in its second item,
    or two lists separated by a thematic break?

    ``` markdown
    * a
    * * * * *
    * b
    ```

7.  When list markers change from numbers to bullets, do we have
    two lists or one?  (The Markdown syntax description suggests two,
    but the perl scripts and many other implementations produce one.)

    ``` markdown
    1. fee
    2. fie
    -  foe
    -  fum
    ```

8.  What are the precedence rules for the markers of inline structure?
    For example, is the following a valid link, or does the code span
    take precedence ?

    ``` markdown
    [a backtick (`)](/url) and [another backtick (`)](/url).
    ```

9.  What are the precedence rules for markers of emphasis and strong
    emphasis?  For example, how should the following be parsed?

    ``` markdown
    *foo *bar* baz*
    ```

10. What are the precedence rules between block-level and inline-level
    structure?  For example, how should the following be parsed?

    ``` markdown
    - `a long code span can contain a hyphen like this
      - and it can screw things up`
    ```

11. Can list items include section headings?  (`Markdown.pl` does not
    allow this, but does allow blockquotes to include headings.)

    ``` markdown
    - # Heading
    ```

12. Can list items be empty?

    ``` markdown
    * a
    *
    * b
    ```

13. Can link references be defined inside block quotes or list items?

    ``` markdown
    > Blockquote [foo].
    >
    > [foo]: /url
    ```

14. If there are multiple definitions for the same reference, which takes
    precedence?

    ``` markdown
    [foo]: /url1
    [foo]: /url2

    [foo][]
    ```

In the absence of a spec, early implementers consulted `Markdown.pl`
to resolve these ambiguities.  But `Markdown.pl` was quite buggy, and
gave manifestly bad results in many cases, so it was not a
satisfactory replacement for a spec.

Because there is no unambiguous spec, implementations have diverged
considerably.  As a result, users are often surprised to find that
a document that renders one way on one system (say, a github wiki)
renders differently on another (say, converting to docbook using
pandoc).  To make matters worse, because nothing in Markdown counts
as a "syntax error," the divergence often isn't discovered right away.

## About this document

This document attempts to specify Markdown syntax unambiguously.
It contains many examples with side-by-side Markdown and
HTML.  These are intended to double as conformance tests.  An
accompanying script `spec_tests.py` can be used to run the tests
against any Markdown program:

    python test/spec_tests.py --spec spec.txt --program PROGRAM

Since this document describes how Markdown is to be parsed into
an abstract syntax tree, it would have made sense to use an abstract
representation of the syntax tree instead of HTML.  But HTML is capable
of representing the structural distinctions we need to make, and the
choice of HTML for the tests makes it possible to run the tests against
an implementation without writing an abstract syntax tree renderer.

This document is generated from a text file, `spec.txt`, written
in Markdown with a small extension for the side-by-side tests.
The script `tools/makespec.py` can be used to convert `spec.txt` into
HTML or CommonMark (which can then be converted into other formats).

In the examples, the `→` character is used to represent tabs.

# Preliminaries

## Characters and lines

Any sequence of [characters] is a valid CommonMark
document.

A [character](@) is a Unicode code point.  Although some
code points (for example, combining accents) do not correspond to
characters in an intuitive sense, all code points count as characters
for purposes of this spec.

This spec does not specify an encoding; it thinks of lines as composed
of [characters] rather than bytes.  A conforming parser may be limited
to a certain encoding.

A [line](@) is a sequence of zero or more [characters]
other than newline (`U+000A`) or carriage return (`U+000D`),
followed by a [line ending] or by the end of file.

A [line ending](@) is a newline (`U+000A`), a carriage return
(`U+000D`) not followed by a newline, or a carriage return and a
following newline.

A line containing no characters, or a line containing only spaces
(`U+0020`) or tabs (`U+0009`), is called a [blank line](@).

The following definitions of character classes will be used in this spec:

A [whitespace character](@) is a space
(`U+0020`), tab (`U+0009`), newline (`U+000A`), line tabulation (`U+000B`),
form feed (`U+000C`), or carriage return (`U+000D`).

[Whitespace](@) is a sequence of one or more [whitespace
characters].

A [Unicode whitespace character](@) is
any code point in the Unicode `Zs` general category, or a tab (`U+0009`),
carriage return (`U+000D`), newline (`U+000A`), or form feed
(`U+000C`).

[Unicode whitespace](@) is a sequence of one
or more [Unicode whitespace characters].

A [space](@) is `U+0020`.

A [non-whitespace character](@) is any character
that is not a [whitespace character].

An [ASCII punctuation character](@)
is `!`, `"`, `#`, `$`, `%`, `&`, `'`, `(`, `)`,
`*`, `+`, `,`, `-`, `.`, `/`, `:`, `;`, `<`, `=`, `>`, `?`, `@`,
`[`, `\`, `]`, `^`, `_`, `` ` ``, `{`, `|`, `}`, or `~`.

A [punctuation character](@) is an [ASCII
punctuation character] or anything in
the general Unicode categories  `Pc`, `Pd`, `Pe`, `Pf`, `Pi`, `Po`, or `Ps`.

## Tabs

Tabs in lines are not expanded to [spaces].  However,
in contexts where whitespace helps to define block structure,
tabs behave as if they were replaced by spaces with a tab stop
of 4 characters.

Thus, for example, a tab can be used instead of four spaces
in an indented code block.  (Note, however, that internal
tabs are passed through as literal tabs, not expanded to
spaces.)

```````````````````````````````` example Tabs: 1
→foo→baz→→bim
.
<pre><code>foo→baz→→bim
</code></pre>
.
Document[0, 14]
  IndentedCodeBlock[1, 14]
````````````````````````````````


```````````````````````````````` example Tabs: 2
  →foo→baz→→bim
.
<pre><code>foo→baz→→bim
</code></pre>
.
Document[0, 16]
  IndentedCodeBlock[3, 16]
````````````````````````````````


```````````````````````````````` example Tabs: 3
    a→a
    ὐ→a
.
<pre><code>a→a
ὐ→a
</code></pre>
.
Document[0, 16]
  IndentedCodeBlock[4, 16]
````````````````````````````````


In the following example, a continuation paragraph of a list
item is indented with a tab; this has exactly the same effect
as indentation with four spaces would:

```````````````````````````````` example Tabs: 4
  - foo

→bar
.
<ul>
<li>
<p>foo</p>
<p>bar</p>
</li>
</ul>
.
Document[0, 14]
  BulletList[2, 14] isLoose
    BulletListItem[2, 14] open:[2, 3, "-"] isLoose hadBlankLineAfter
      Paragraph[4, 8] isTrailingBlankLine
        Text[4, 7] chars:[4, 7, "foo"]
      Paragraph[10, 14]
        Text[10, 13] chars:[10, 13, "bar"]
````````````````````````````````


```````````````````````````````` example Tabs: 5
- foo

→→bar
.
<ul>
<li>
<p>foo</p>
<pre><code>  bar
</code></pre>
</li>
</ul>
.
Document[0, 13]
  BulletList[0, 13] isLoose
    BulletListItem[0, 13] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[2, 6] isTrailingBlankLine
        Text[2, 5] chars:[2, 5, "foo"]
      IndentedCodeBlock[9, 13]
````````````````````````````````


Normally the `>` that begins a block quote may be followed
optionally by a space, which is not considered part of the
content.  In the following case `>` is followed by a tab,
which is treated as if it were expanded into three spaces.
Since one of these spaces is considered part of the
delimiter, `foo` is considered to be indented six spaces
inside the block quote context, so we get an indented
code block starting with two spaces.

```````````````````````````````` example Tabs: 6
>→→foo
.
<blockquote>
<pre><code>  foo
</code></pre>
</blockquote>
.
Document[0, 7]
  BlockQuote[0, 7] marker:[0, 1, ">"]
    IndentedCodeBlock[3, 7]
````````````````````````````````


```````````````````````````````` example Tabs: 7
-→→foo
.
<ul>
<li>
<pre><code>  foo
</code></pre>
</li>
</ul>
.
Document[0, 7]
  BulletList[0, 7] isTight
    BulletListItem[0, 7] open:[0, 1, "-"] isTight
      IndentedCodeBlock[3, 7]
````````````````````````````````


```````````````````````````````` example Tabs: 8
    foo
→bar
.
<pre><code>foo
bar
</code></pre>
.
Document[0, 13]
  IndentedCodeBlock[4, 13]
````````````````````````````````


```````````````````````````````` example Tabs: 9
 - foo
   - bar
→ - baz
.
<ul>
<li>foo
<ul>
<li>bar
<ul>
<li>baz</li>
</ul>
</li>
</ul>
</li>
</ul>
.
Document[0, 24]
  BulletList[1, 24] isTight
    BulletListItem[1, 24] open:[1, 2, "-"] isTight
      Paragraph[3, 7]
        Text[3, 6] chars:[3, 6, "foo"]
      BulletList[10, 24] isTight
        BulletListItem[10, 24] open:[10, 11, "-"] isTight
          Paragraph[12, 16]
            Text[12, 15] chars:[12, 15, "bar"]
          BulletList[18, 24] isTight
            BulletListItem[18, 24] open:[18, 19, "-"] isTight
              Paragraph[20, 24]
                Text[20, 23] chars:[20, 23, "baz"]
````````````````````````````````


```````````````````````````````` example Tabs: 10
#→Foo
.
<h1>Foo</h1>
````````````````````````````````


```````````````````````````````` example Tabs: 11
*→*→*→
.
<hr />
````````````````````````````````


## Insecure characters

For security reasons, the Unicode character `U+0000` must be replaced
with the REPLACEMENT CHARACTER (`U+FFFD`).

# Blocks and inlines

We can think of a document as a sequence of
[blocks](@)---structural elements like paragraphs, block
quotations, lists, headings, rules, and code blocks.  Some blocks (like
block quotes and list items) contain other blocks; others (like
headings and paragraphs) contain [inline](@) content---text,
links, emphasized text, images, code spans, and so on.

## Precedence

Indicators of block structure always take precedence over indicators
of inline structure.  So, for example, the following is a list with
two items, not a list with one item containing a code span:

```````````````````````````````` example Precedence: 1
- `one
- two`
.
<ul>
<li>`one</li>
<li>two`</li>
</ul>
.
Document[0, 14]
  BulletList[0, 14] isTight
    BulletListItem[0, 7] open:[0, 1, "-"] isTight
      Paragraph[2, 7]
        Text[2, 6] chars:[2, 6, "`one"]
    BulletListItem[7, 14] open:[7, 8, "-"] isTight
      Paragraph[9, 14]
        Text[9, 13] chars:[9, 13, "two`"]
````````````````````````````````


This means that parsing can proceed in two steps:  first, the block
structure of the document can be discerned; second, text lines inside
paragraphs, headings, and other block constructs can be parsed for inline
structure.  The second step requires information about link reference
definitions that will be available only at the end of the first
step.  Note that the first step requires processing lines in sequence,
but the second can be parallelized, since the inline parsing of
one block element does not affect the inline parsing of any other.

## Container blocks and leaf blocks

We can divide blocks into two types:
[container block](@)s,
which can contain other blocks, and [leaf block](@)s,
which cannot.

# Leaf blocks

This section describes the different kinds of leaf block that make up a
Markdown document.

## Thematic breaks

A line consisting of 0-3 spaces of indentation, followed by a sequence
of three or more matching `-`, `_`, or `*` characters, each followed
optionally by any number of spaces, forms a
[thematic break](@).

```````````````````````````````` example Thematic breaks: 1
***
---
___
.
<hr />
<hr />
<hr />
.
Document[0, 12]
  ThematicBreak[0, 3]
  ThematicBreak[4, 7]
  ThematicBreak[8, 11]
````````````````````````````````


Wrong characters:

```````````````````````````````` example Thematic breaks: 2
+++
.
<p>+++</p>
.
Document[0, 4]
  Paragraph[0, 4]
    Text[0, 3] chars:[0, 3, "+++"]
````````````````````````````````


```````````````````````````````` example Thematic breaks: 3
===
.
<p>===</p>
.
Document[0, 4]
  Paragraph[0, 4]
    Text[0, 3] chars:[0, 3, "==="]
````````````````````````````````


Not enough characters:

```````````````````````````````` example Thematic breaks: 4
--
**
__
.
<p>--
**
__</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 2] chars:[0, 2, "--"]
    SoftLineBreak[2, 3]
    Text[3, 5] chars:[3, 5, "**"]
    SoftLineBreak[5, 6]
    Text[6, 8] chars:[6, 8, "__"]
````````````````````````````````


One to three spaces indent are allowed:

```````````````````````````````` example Thematic breaks: 5
 ***
  ***
   ***
.
<hr />
<hr />
<hr />
.
Document[0, 18]
  ThematicBreak[0, 4]
  ThematicBreak[5, 10]
  ThematicBreak[11, 17]
````````````````````````````````


Four spaces is too many:

```````````````````````````````` example Thematic breaks: 6
    ***
.
<pre><code>***
</code></pre>
.
Document[0, 8]
  IndentedCodeBlock[4, 8]
````````````````````````````````


```````````````````````````````` example Thematic breaks: 7
Foo
    ***
.
<p>Foo
***</p>
.
Document[0, 12]
  Paragraph[0, 12]
    Text[0, 3] chars:[0, 3, "Foo"]
    SoftLineBreak[3, 4]
    Text[8, 11] chars:[8, 11, "***"]
````````````````````````````````


More than three characters may be used:

```````````````````````````````` example Thematic breaks: 8
_____________________________________
.
<hr />
.
Document[0, 38]
  ThematicBreak[0, 37]
````````````````````````````````


Spaces are allowed between the characters:

```````````````````````````````` example Thematic breaks: 9
 - - -
.
<hr />
.
Document[0, 7]
  ThematicBreak[0, 6]
````````````````````````````````


```````````````````````````````` example Thematic breaks: 10
 **  * ** * ** * **
.
<hr />
.
Document[0, 20]
  ThematicBreak[0, 19]
````````````````````````````````


```````````````````````````````` example Thematic breaks: 11
-     -      -      -
.
<hr />
.
Document[0, 22]
  ThematicBreak[0, 21]
````````````````````````````````


Spaces are allowed at the end:

```````````````````````````````` example Thematic breaks: 12
- - - -    
.
<hr />
.
Document[0, 12]
  ThematicBreak[0, 11]
````````````````````````````````


However, no other characters may occur in the line:

```````````````````````````````` example Thematic breaks: 13
_ _ _ _ a

a------

---a---
.
<p>_ _ _ _ a</p>
<p>a------</p>
<p>---a---</p>
.
Document[0, 28]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 9] chars:[0, 9, "_ _ _ _ a"]
  Paragraph[11, 19] isTrailingBlankLine
    Text[11, 18] chars:[11, 18, "a------"]
  Paragraph[20, 28]
    Text[20, 27] chars:[20, 27, "---a---"]
````````````````````````````````


It is required that all of the [non-whitespace characters] be the same.
So, this is not a thematic break:

```````````````````````````````` example Thematic breaks: 14
 *-*
.
<p><em>-</em></p>
.
Document[0, 5]
  Paragraph[1, 5]
    Emphasis[1, 4] textOpen:[1, 2, "*"] text:[2, 3, "-"] textClose:[3, 4, "*"]
      Text[2, 3] chars:[2, 3, "-"]
````````````````````````````````


Thematic breaks do not need blank lines before or after:

```````````````````````````````` example Thematic breaks: 15
- foo
***
- bar
.
<ul>
<li>foo</li>
</ul>
<hr />
<ul>
<li>bar</li>
</ul>
.
Document[0, 16]
  BulletList[0, 6] isTight
    BulletListItem[0, 6] open:[0, 1, "-"] isTight
      Paragraph[2, 6]
        Text[2, 5] chars:[2, 5, "foo"]
  ThematicBreak[6, 9]
  BulletList[10, 16] isTight
    BulletListItem[10, 16] open:[10, 11, "-"] isTight
      Paragraph[12, 16]
        Text[12, 15] chars:[12, 15, "bar"]
````````````````````````````````


Thematic breaks can interrupt a paragraph:

```````````````````````````````` example Thematic breaks: 16
Foo
***
bar
.
<p>Foo</p>
<hr />
<p>bar</p>
.
Document[0, 12]
  Paragraph[0, 4]
    Text[0, 3] chars:[0, 3, "Foo"]
  ThematicBreak[4, 7]
  Paragraph[8, 12]
    Text[8, 11] chars:[8, 11, "bar"]
````````````````````````````````


If a line of dashes that meets the above conditions for being a
thematic break could also be interpreted as the underline of a [setext
heading], the interpretation as a
[setext heading] takes precedence. Thus, for example,
this is a setext heading, not a paragraph followed by a thematic break:

```````````````````````````````` example Thematic breaks: 17
Foo
---
bar
.
<h2>Foo</h2>
<p>bar</p>
.
Document[0, 12]
  Heading[0, 7] text:[0, 3, "Foo"] textClose:[4, 7, "---"]
    Text[0, 3] chars:[0, 3, "Foo"]
  Paragraph[8, 12]
    Text[8, 11] chars:[8, 11, "bar"]
````````````````````````````````


When both a thematic break and a list item are possible
interpretations of a line, the thematic break takes precedence:

```````````````````````````````` example Thematic breaks: 18
* Foo
* * *
* Bar
.
<ul>
<li>Foo</li>
</ul>
<hr />
<ul>
<li>Bar</li>
</ul>
.
Document[0, 18]
  BulletList[0, 6] isTight
    BulletListItem[0, 6] open:[0, 1, "*"] isTight
      Paragraph[2, 6]
        Text[2, 5] chars:[2, 5, "Foo"]
  ThematicBreak[6, 11]
  BulletList[12, 18] isTight
    BulletListItem[12, 18] open:[12, 13, "*"] isTight
      Paragraph[14, 18]
        Text[14, 17] chars:[14, 17, "Bar"]
````````````````````````````````


If you want a thematic break in a list item, use a different bullet:

```````````````````````````````` example Thematic breaks: 19
- Foo
- * * *
.
<ul>
<li>Foo</li>
<li>
<hr />
</li>
</ul>
.
Document[0, 14]
  BulletList[0, 13] isTight
    BulletListItem[0, 6] open:[0, 1, "-"] isTight
      Paragraph[2, 6]
        Text[2, 5] chars:[2, 5, "Foo"]
    BulletListItem[6, 13] open:[6, 7, "-"] isTight
      ThematicBreak[8, 13]
````````````````````````````````


## ATX headings

An [ATX heading](@)
consists of a string of characters, parsed as inline content, between an
opening sequence of 1--6 unescaped `#` characters and an optional
closing sequence of any number of unescaped `#` characters.
The opening sequence of `#` characters must be followed by a
[space] or by the end of line. The optional closing sequence of `#`s must be
preceded by a [space] and may be followed by spaces only.  The opening
`#` character may be indented 0-3 spaces.  The raw contents of the
heading are stripped of leading and trailing spaces before being parsed
as inline content.  The heading level is equal to the number of `#`
characters in the opening sequence.

Simple headings:

```````````````````````````````` example ATX headings: 1
# foo
## foo
### foo
#### foo
##### foo
###### foo
.
<h1>foo</h1>
<h2>foo</h2>
<h3>foo</h3>
<h4>foo</h4>
<h5>foo</h5>
<h6>foo</h6>
.
Document[0, 51]
  Heading[0, 5] textOpen:[0, 1, "#"] text:[2, 5, "foo"]
    Text[2, 5] chars:[2, 5, "foo"]
  Heading[6, 12] textOpen:[6, 8, "##"] text:[9, 12, "foo"]
    Text[9, 12] chars:[9, 12, "foo"]
  Heading[13, 20] textOpen:[13, 16, "###"] text:[17, 20, "foo"]
    Text[17, 20] chars:[17, 20, "foo"]
  Heading[21, 29] textOpen:[21, 25, "####"] text:[26, 29, "foo"]
    Text[26, 29] chars:[26, 29, "foo"]
  Heading[30, 39] textOpen:[30, 35, "#####"] text:[36, 39, "foo"]
    Text[36, 39] chars:[36, 39, "foo"]
  Heading[40, 50] textOpen:[40, 46, "######"] text:[47, 50, "foo"]
    Text[47, 50] chars:[47, 50, "foo"]
````````````````````````````````


More than six `#` characters is not a heading:

```````````````````````````````` example ATX headings: 2
####### foo
.
<p>####### foo</p>
.
Document[0, 12]
  Paragraph[0, 12]
    Text[0, 11] chars:[0, 11, "##### … # foo"]
````````````````````````````````


At least one space is required between the `#` characters and the
heading's contents, unless the heading is empty.  Note that many
implementations currently do not require the space.  However, the
space was required by the
[original ATX implementation](http://www.aaronsw.com/2002/atx/atx.py),
and it helps prevent things like the following from being parsed as
headings:

```````````````````````````````` example ATX headings: 3
#5 bolt

#hashtag
.
<p>#5 bolt</p>
<p>#hashtag</p>
.
Document[0, 18]
  Paragraph[0, 8] isTrailingBlankLine
    Text[0, 7] chars:[0, 7, "#5 bolt"]
  Paragraph[9, 18]
    Text[9, 17] chars:[9, 17, "#hashtag"]
````````````````````````````````


This is not a heading, because the first `#` is escaped:

```````````````````````````````` example ATX headings: 4
\## foo
.
<p>## foo</p>
.
Document[0, 8]
  Paragraph[0, 8]
    Text[0, 7] chars:[0, 7, "\## foo"]
````````````````````````````````


Contents are parsed as inlines:

```````````````````````````````` example ATX headings: 5
# foo *bar* \*baz\*
.
<h1>foo <em>bar</em> *baz*</h1>
.
Document[0, 20]
  Heading[0, 19] textOpen:[0, 1, "#"] text:[2, 19, "foo *bar* \*baz\*"]
    Text[2, 6] chars:[2, 6, "foo "]
    Emphasis[6, 11] textOpen:[6, 7, "*"] text:[7, 10, "bar"] textClose:[10, 11, "*"]
      Text[7, 10] chars:[7, 10, "bar"]
    Text[11, 19] chars:[11, 19, " \*baz\*"]
````````````````````````````````


Leading and trailing blanks are ignored in parsing inline content:

```````````````````````````````` example ATX headings: 6
#                  foo                     
.
<h1>foo</h1>
.
Document[0, 44]
  Heading[0, 22] textOpen:[0, 1, "#"] text:[19, 22, "foo"]
    Text[19, 22] chars:[19, 22, "foo"]
````````````````````````````````


One to three spaces indentation are allowed:

```````````````````````````````` example ATX headings: 7
 ### foo
  ## foo
   # foo
.
<h3>foo</h3>
<h2>foo</h2>
<h1>foo</h1>
.
Document[0, 27]
  Heading[1, 8] textOpen:[1, 4, "###"] text:[5, 8, "foo"]
    Text[5, 8] chars:[5, 8, "foo"]
  Heading[11, 17] textOpen:[11, 13, "##"] text:[14, 17, "foo"]
    Text[14, 17] chars:[14, 17, "foo"]
  Heading[21, 26] textOpen:[21, 22, "#"] text:[23, 26, "foo"]
    Text[23, 26] chars:[23, 26, "foo"]
````````````````````````````````


Four spaces are too much:

```````````````````````````````` example ATX headings: 8
    # foo
.
<pre><code># foo
</code></pre>
.
Document[0, 10]
  IndentedCodeBlock[4, 10]
````````````````````````````````


```````````````````````````````` example ATX headings: 9
foo
    # bar
.
<p>foo
# bar</p>
.
Document[0, 14]
  Paragraph[0, 14]
    Text[0, 3] chars:[0, 3, "foo"]
    SoftLineBreak[3, 4]
    Text[8, 13] chars:[8, 13, "# bar"]
````````````````````````````````


A closing sequence of `#` characters is optional:

```````````````````````````````` example ATX headings: 10
## foo ##
  ###   bar    ###
.
<h2>foo</h2>
<h3>bar</h3>
.
Document[0, 29]
  Heading[0, 9] textOpen:[0, 2, "##"] text:[3, 6, "foo"] textClose:[7, 9, "##"]
    Text[3, 6] chars:[3, 6, "foo"]
  Heading[12, 28] textOpen:[12, 15, "###"] text:[18, 21, "bar"] textClose:[25, 28, "###"]
    Text[18, 21] chars:[18, 21, "bar"]
````````````````````````````````


It need not be the same length as the opening sequence:

```````````````````````````````` example ATX headings: 11
# foo ##################################
##### foo ##
.
<h1>foo</h1>
<h5>foo</h5>
.
Document[0, 54]
  Heading[0, 40] textOpen:[0, 1, "#"] text:[2, 5, "foo"] textClose:[6, 40, "##################################"]
    Text[2, 5] chars:[2, 5, "foo"]
  Heading[41, 53] textOpen:[41, 46, "#####"] text:[47, 50, "foo"] textClose:[51, 53, "##"]
    Text[47, 50] chars:[47, 50, "foo"]
````````````````````````````````


Spaces are allowed after the closing sequence:

```````````````````````````````` example ATX headings: 12
### foo ###     
.
<h3>foo</h3>
.
Document[0, 17]
  Heading[0, 11] textOpen:[0, 3, "###"] text:[4, 7, "foo"] textClose:[8, 11, "###"]
    Text[4, 7] chars:[4, 7, "foo"]
````````````````````````````````


A sequence of `#` characters with anything but [spaces] following it
is not a closing sequence, but counts as part of the contents of the
heading:

```````````````````````````````` example ATX headings: 13
### foo ### b
.
<h3>foo ### b</h3>
.
Document[0, 14]
  Heading[0, 13] textOpen:[0, 3, "###"] text:[4, 13, "foo ### b"]
    Text[4, 13] chars:[4, 13, "foo ### b"]
````````````````````````````````


The closing sequence must be preceded by a space:

```````````````````````````````` example ATX headings: 14
# foo#
.
<h1>foo#</h1>
.
Document[0, 7]
  Heading[0, 6] textOpen:[0, 1, "#"] text:[2, 6, "foo#"]
    Text[2, 6] chars:[2, 6, "foo#"]
````````````````````````````````


Backslash-escaped `#` characters do not count as part
of the closing sequence:

```````````````````````````````` example ATX headings: 15
### foo \###
## foo #\##
# foo \#
.
<h3>foo ###</h3>
<h2>foo ###</h2>
<h1>foo #</h1>
.
Document[0, 34]
  Heading[0, 12] textOpen:[0, 3, "###"] text:[4, 12, "foo \###"]
    Text[4, 12] chars:[4, 12, "foo \###"]
  Heading[13, 24] textOpen:[13, 15, "##"] text:[16, 24, "foo #\##"]
    Text[16, 24] chars:[16, 24, "foo #\##"]
  Heading[25, 33] textOpen:[25, 26, "#"] text:[27, 33, "foo \#"]
    Text[27, 33] chars:[27, 33, "foo \#"]
````````````````````````````````


ATX headings need not be separated from surrounding content by blank
lines, and they can interrupt paragraphs:

```````````````````````````````` example ATX headings: 16
****
## foo
****
.
<hr />
<h2>foo</h2>
<hr />
.
Document[0, 17]
  ThematicBreak[0, 4]
  Heading[5, 11] textOpen:[5, 7, "##"] text:[8, 11, "foo"]
    Text[8, 11] chars:[8, 11, "foo"]
  ThematicBreak[12, 16]
````````````````````````````````


```````````````````````````````` example ATX headings: 17
Foo bar
# baz
Bar foo
.
<p>Foo bar</p>
<h1>baz</h1>
<p>Bar foo</p>
.
Document[0, 22]
  Paragraph[0, 8]
    Text[0, 7] chars:[0, 7, "Foo bar"]
  Heading[8, 13] textOpen:[8, 9, "#"] text:[10, 13, "baz"]
    Text[10, 13] chars:[10, 13, "baz"]
  Paragraph[14, 22]
    Text[14, 21] chars:[14, 21, "Bar foo"]
````````````````````````````````


ATX headings can be empty:

```````````````````````````````` example ATX headings: 18
## 
#
### ###
.
<h2></h2>
<h1></h1>
<h3></h3>
.
Document[0, 14]
  Heading[0, 3] textOpen:[0, 2, "##"] text:[3, 3]
  Heading[4, 5] textOpen:[4, 5, "#"] text:[5, 5]
  Heading[6, 13] textOpen:[6, 9, "###"] text:[10, 10] textClose:[10, 13, "###"]
````````````````````````````````


## Setext headings

A [setext heading](@) consists of one or more
lines of text, each containing at least one [non-whitespace
character], with no more than 3 spaces indentation, followed by
a [setext heading underline].  The lines of text must be such
that, were they not followed by the setext heading underline,
they would be interpreted as a paragraph:  they cannot be
interpretable as a [code fence], [ATX heading][ATX headings],
[block quote][block quotes], [thematic break][thematic breaks],
[list item][list items], or [HTML block][HTML blocks].

A [setext heading underline](@) is a sequence of
`=` characters or a sequence of `-` characters, with no more than 3
spaces indentation and any number of trailing spaces.  If a line
containing a single `-` can be interpreted as an
empty [list items], it should be interpreted this way
and not as a [setext heading underline].

The heading is a level 1 heading if `=` characters are used in
the [setext heading underline], and a level 2 heading if `-`
characters are used.  The contents of the heading are the result
of parsing the preceding lines of text as CommonMark inline
content.

In general, a setext heading need not be preceded or followed by a
blank line.  However, it cannot interrupt a paragraph, so when a
setext heading comes after a paragraph, a blank line is needed between
them.

Simple examples:

```````````````````````````````` example Setext headings: 1
Foo *bar*
=========

Foo *bar*
---------
.
<h1>Foo <em>bar</em></h1>
<h2>Foo <em>bar</em></h2>
.
Document[0, 41]
  Heading[0, 19] text:[0, 9, "Foo *bar*"] textClose:[10, 19, "========="]
    Text[0, 4] chars:[0, 4, "Foo "]
    Emphasis[4, 9] textOpen:[4, 5, "*"] text:[5, 8, "bar"] textClose:[8, 9, "*"]
      Text[5, 8] chars:[5, 8, "bar"]
  Heading[21, 40] text:[21, 30, "Foo *bar*"] textClose:[31, 40, "---------"]
    Text[21, 25] chars:[21, 25, "Foo "]
    Emphasis[25, 30] textOpen:[25, 26, "*"] text:[26, 29, "bar"] textClose:[29, 30, "*"]
      Text[26, 29] chars:[26, 29, "bar"]
````````````````````````````````


The content of the header may span more than one line:

```````````````````````````````` example Setext headings: 2
Foo *bar
baz*
====
.
<h1>Foo <em>bar
baz</em></h1>
.
Document[0, 19]
  Heading[0, 18] text:[0, 13, "Foo *bar\nbaz*"] textClose:[14, 18, "===="]
    Text[0, 4] chars:[0, 4, "Foo "]
    Emphasis[4, 13] textOpen:[4, 5, "*"] text:[5, 12, "bar\nbaz"] textClose:[12, 13, "*"]
      Text[5, 8] chars:[5, 8, "bar"]
      SoftLineBreak[8, 9]
      Text[9, 12] chars:[9, 12, "baz"]
````````````````````````````````


The underlining can be any length:

```````````````````````````````` example Setext headings: 3
Foo
-------------------------

Foo
=
.
<h2>Foo</h2>
<h1>Foo</h1>
.
Document[0, 37]
  Heading[0, 29] text:[0, 3, "Foo"] textClose:[4, 29, "-------------------------"]
    Text[0, 3] chars:[0, 3, "Foo"]
  Heading[31, 36] text:[31, 34, "Foo"] textClose:[35, 36, "="]
    Text[31, 34] chars:[31, 34, "Foo"]
````````````````````````````````


The heading content can be indented up to three spaces, and need
not line up with the underlining:

```````````````````````````````` example Setext headings: 4
   Foo
---

  Foo
-----

  Foo
  ===
.
<h2>Foo</h2>
<h2>Foo</h2>
<h1>Foo</h1>
.
Document[0, 37]
  Heading[3, 10] text:[3, 6, "Foo"] textClose:[7, 10, "---"]
    Text[3, 6] chars:[3, 6, "Foo"]
  Heading[14, 23] text:[14, 17, "Foo"] textClose:[18, 23, "-----"]
    Text[14, 17] chars:[14, 17, "Foo"]
  Heading[27, 36] text:[27, 30, "Foo"] textClose:[33, 36, "==="]
    Text[27, 30] chars:[27, 30, "Foo"]
````````````````````````````````


Four spaces indent is too much:

```````````````````````````````` example Setext headings: 5
    Foo
    ---

    Foo
---
.
<pre><code>Foo
---

Foo
</code></pre>
<hr />
.
Document[0, 29]
  IndentedCodeBlock[4, 25]
  ThematicBreak[25, 28]
````````````````````````````````


The setext heading underline can be indented up to three spaces, and
may have trailing spaces:

```````````````````````````````` example Setext headings: 6
Foo
   ----      
.
<h2>Foo</h2>
.
Document[0, 18]
  Heading[0, 11] text:[0, 3, "Foo"] textClose:[7, 11, "----"]
    Text[0, 3] chars:[0, 3, "Foo"]
````````````````````````````````


Four spaces is too much:

```````````````````````````````` example Setext headings: 7
Foo
    ---
.
<p>Foo
---</p>
.
Document[0, 12]
  Paragraph[0, 12]
    Text[0, 3] chars:[0, 3, "Foo"]
    SoftLineBreak[3, 4]
    Text[8, 11] chars:[8, 11, "---"]
````````````````````````````````


The setext heading underline cannot contain internal spaces:

```````````````````````````````` example Setext headings: 8
Foo
= =

Foo
--- -
.
<p>Foo
= =</p>
<p>Foo</p>
<hr />
.
Document[0, 19]
  Paragraph[0, 8] isTrailingBlankLine
    Text[0, 3] chars:[0, 3, "Foo"]
    SoftLineBreak[3, 4]
    Text[4, 7] chars:[4, 7, "= ="]
  Paragraph[9, 13]
    Text[9, 12] chars:[9, 12, "Foo"]
  ThematicBreak[13, 18]
````````````````````````````````


Trailing spaces in the content line do not cause a line break:

```````````````````````````````` example Setext headings: 9
Foo  
-----
.
<h2>Foo</h2>
.
Document[0, 12]
  Heading[0, 11] text:[0, 3, "Foo"] textClose:[6, 11, "-----"]
    Text[0, 3] chars:[0, 3, "Foo"]
````````````````````````````````


Nor does a backslash at the end:

```````````````````````````````` example Setext headings: 10
Foo\
----
.
<h2>Foo\</h2>
.
Document[0, 10]
  Heading[0, 9] text:[0, 4, "Foo\"] textClose:[5, 9, "----"]
    Text[0, 4] chars:[0, 4, "Foo\"]
````````````````````````````````


Since indicators of block structure take precedence over
indicators of inline structure, the following are setext headings:

```````````````````````````````` example Setext headings: 11
`Foo
----
`

<a title="a lot
---
of dashes"/>
.
<h2>`Foo</h2>
<p>`</p>
<h2>&lt;a title=&quot;a lot</h2>
<p>of dashes&quot;/&gt;</p>
.
Document[0, 46]
  Heading[0, 9] text:[0, 4, "`Foo"] textClose:[5, 9, "----"]
    Text[0, 4] chars:[0, 4, "`Foo"]
  Paragraph[10, 12] isTrailingBlankLine
    Text[10, 11] chars:[10, 11, "`"]
  Heading[13, 32] text:[13, 28, "<a title=\"a lot"] textClose:[29, 32, "---"]
    Text[13, 28] chars:[13, 28, "<a ti … a lot"]
  Paragraph[33, 46]
    Text[33, 45] chars:[33, 45, "of da … es\"/>"]
````````````````````````````````


The setext heading underline cannot be a [lazy continuation
line] in a list item or block quote:

```````````````````````````````` example Setext headings: 12
> Foo
---
.
<blockquote>
<p>Foo</p>
</blockquote>
<hr />
.
Document[0, 10]
  BlockQuote[0, 6] marker:[0, 1, ">"]
    Paragraph[2, 6]
      Text[2, 5] chars:[2, 5, "Foo"]
  ThematicBreak[6, 9]
````````````````````````````````


```````````````````````````````` example Setext headings: 13
> foo
bar
===
.
<blockquote>
<p>foo
bar
===</p>
</blockquote>
.
Document[0, 14]
  BlockQuote[0, 14] marker:[0, 1, ">"]
    Paragraph[2, 14]
      Text[2, 5] chars:[2, 5, "foo"]
      SoftLineBreak[5, 6]
      Text[6, 9] chars:[6, 9, "bar"]
      SoftLineBreak[9, 10]
      Text[10, 13] chars:[10, 13, "==="]
````````````````````````````````


```````````````````````````````` example Setext headings: 14
- Foo
---
.
<ul>
<li>Foo</li>
</ul>
<hr />
.
Document[0, 10]
  BulletList[0, 6] isTight
    BulletListItem[0, 6] open:[0, 1, "-"] isTight
      Paragraph[2, 6]
        Text[2, 5] chars:[2, 5, "Foo"]
  ThematicBreak[6, 9]
````````````````````````````````


A blank line is needed between a paragraph and a following
setext heading, since otherwise the paragraph becomes part
of the heading's content:

```````````````````````````````` example Setext headings: 15
Foo
Bar
---
.
<h2>Foo
Bar</h2>
.
Document[0, 12]
  Heading[0, 11] text:[0, 7, "Foo\nBar"] textClose:[8, 11, "---"]
    Text[0, 3] chars:[0, 3, "Foo"]
    SoftLineBreak[3, 4]
    Text[4, 7] chars:[4, 7, "Bar"]
````````````````````````````````


But in general a blank line is not required before or after
setext headings:

```````````````````````````````` example Setext headings: 16
---
Foo
---
Bar
---
Baz
.
<hr />
<h2>Foo</h2>
<h2>Bar</h2>
<p>Baz</p>
.
Document[0, 24]
  ThematicBreak[0, 3]
  Heading[4, 11] text:[4, 7, "Foo"] textClose:[8, 11, "---"]
    Text[4, 7] chars:[4, 7, "Foo"]
  Heading[12, 19] text:[12, 15, "Bar"] textClose:[16, 19, "---"]
    Text[12, 15] chars:[12, 15, "Bar"]
  Paragraph[20, 24]
    Text[20, 23] chars:[20, 23, "Baz"]
````````````````````````````````


Setext headings cannot be empty:

```````````````````````````````` example Setext headings: 17

====
.
<p>====</p>
.
Document[0, 6]
  Paragraph[1, 6]
    Text[1, 5] chars:[1, 5, "===="]
````````````````````````````````


Setext heading text lines must not be interpretable as block
constructs other than paragraphs.  So, the line of dashes
in these examples gets interpreted as a thematic break:

```````````````````````````````` example Setext headings: 18
---
---
.
<hr />
<hr />
.
Document[0, 8]
  ThematicBreak[0, 3]
  ThematicBreak[4, 7]
````````````````````````````````


```````````````````````````````` example Setext headings: 19
- foo
-----
.
<ul>
<li>foo</li>
</ul>
<hr />
.
Document[0, 12]
  BulletList[0, 6] isTight
    BulletListItem[0, 6] open:[0, 1, "-"] isTight
      Paragraph[2, 6]
        Text[2, 5] chars:[2, 5, "foo"]
  ThematicBreak[6, 11]
````````````````````````````````


```````````````````````````````` example Setext headings: 20
    foo
---
.
<pre><code>foo
</code></pre>
<hr />
.
Document[0, 12]
  IndentedCodeBlock[4, 8]
  ThematicBreak[8, 11]
````````````````````````````````


```````````````````````````````` example Setext headings: 21
> foo
-----
.
<blockquote>
<p>foo</p>
</blockquote>
<hr />
.
Document[0, 12]
  BlockQuote[0, 6] marker:[0, 1, ">"]
    Paragraph[2, 6]
      Text[2, 5] chars:[2, 5, "foo"]
  ThematicBreak[6, 11]
````````````````````````````````


If you want a heading with `> foo` as its literal text, you can
use backslash escapes:

```````````````````````````````` example Setext headings: 22
\> foo
------
.
<h2>&gt; foo</h2>
.
Document[0, 14]
  Heading[0, 13] text:[0, 6, "\> foo"] textClose:[7, 13, "------"]
    Text[0, 6] chars:[0, 6, "\> foo"]
````````````````````````````````


**Compatibility note:**  Most existing Markdown implementations
do not allow the text of setext headings to span multiple lines.
But there is no consensus about how to interpret

``` markdown
Foo
bar
---
baz
```

One can find four different interpretations:

1. paragraph "Foo", heading "bar", paragraph "baz"
2. paragraph "Foo bar", thematic break, paragraph "baz"
3. paragraph "Foo bar --- baz"
4. heading "Foo bar", paragraph "baz"

We find interpretation 4 most natural, and interpretation 4
increases the expressive power of CommonMark, by allowing
multiline headings.  Authors who want interpretation 1 can
put a blank line after the first paragraph:

```````````````````````````````` example Setext headings: 23
Foo

bar
---
baz
.
<p>Foo</p>
<h2>bar</h2>
<p>baz</p>
.
Document[0, 17]
  Paragraph[0, 4] isTrailingBlankLine
    Text[0, 3] chars:[0, 3, "Foo"]
  Heading[5, 12] text:[5, 8, "bar"] textClose:[9, 12, "---"]
    Text[5, 8] chars:[5, 8, "bar"]
  Paragraph[13, 17]
    Text[13, 16] chars:[13, 16, "baz"]
````````````````````````````````


Authors who want interpretation 2 can put blank lines around
the thematic break,

```````````````````````````````` example Setext headings: 24
Foo
bar

---

baz
.
<p>Foo
bar</p>
<hr />
<p>baz</p>
.
Document[0, 18]
  Paragraph[0, 8] isTrailingBlankLine
    Text[0, 3] chars:[0, 3, "Foo"]
    SoftLineBreak[3, 4]
    Text[4, 7] chars:[4, 7, "bar"]
  ThematicBreak[9, 12]
  Paragraph[14, 18]
    Text[14, 17] chars:[14, 17, "baz"]
````````````````````````````````


or use a thematic break that cannot count as a [setext heading
underline], such as

```````````````````````````````` example Setext headings: 25
Foo
bar
* * *
baz
.
<p>Foo
bar</p>
<hr />
<p>baz</p>
.
Document[0, 18]
  Paragraph[0, 8]
    Text[0, 3] chars:[0, 3, "Foo"]
    SoftLineBreak[3, 4]
    Text[4, 7] chars:[4, 7, "bar"]
  ThematicBreak[8, 13]
  Paragraph[14, 18]
    Text[14, 17] chars:[14, 17, "baz"]
````````````````````````````````


Authors who want interpretation 3 can use backslash escapes:

```````````````````````````````` example Setext headings: 26
Foo
bar
\---
baz
.
<p>Foo
bar
---
baz</p>
.
Document[0, 17]
  Paragraph[0, 17]
    Text[0, 3] chars:[0, 3, "Foo"]
    SoftLineBreak[3, 4]
    Text[4, 7] chars:[4, 7, "bar"]
    SoftLineBreak[7, 8]
    Text[8, 12] chars:[8, 12, "\---"]
    SoftLineBreak[12, 13]
    Text[13, 16] chars:[13, 16, "baz"]
````````````````````````````````


## Indented code blocks

An [indented code block](@) is composed of one or more
[indented chunks] separated by blank lines.
An [indented chunk](@) is a sequence of non-blank lines,
each indented four or more spaces. The contents of the code block are
the literal contents of the lines, including trailing
[line endings], minus four spaces of indentation.
An indented code block has no [info string].

An indented code block cannot interrupt a paragraph, so there must be
a blank line between a paragraph and a following indented code block.
(A blank line is not needed, however, between a code block and a following
paragraph.)

```````````````````````````````` example Indented code blocks: 1
    a simple
      indented code block
.
<pre><code>a simple
  indented code block
</code></pre>
.
Document[0, 39]
  IndentedCodeBlock[4, 39]
````````````````````````````````


If there is any ambiguity between an interpretation of indentation
as a code block and as indicating that material belongs to a [list
item][list items], the list item interpretation takes precedence:

```````````````````````````````` example Indented code blocks: 2
  - foo

    bar
.
<ul>
<li>
<p>foo</p>
<p>bar</p>
</li>
</ul>
.
Document[0, 17]
  BulletList[2, 17] isLoose
    BulletListItem[2, 17] open:[2, 3, "-"] isLoose hadBlankLineAfter
      Paragraph[4, 8] isTrailingBlankLine
        Text[4, 7] chars:[4, 7, "foo"]
      Paragraph[13, 17]
        Text[13, 16] chars:[13, 16, "bar"]
````````````````````````````````


```````````````````````````````` example Indented code blocks: 3
1.  foo

    - bar
.
<ol>
<li>
<p>foo</p>
<ul>
<li>bar</li>
</ul>
</li>
</ol>
.
Document[0, 19]
  OrderedList[0, 19] isLoose delimiter:'.'
    OrderedListItem[0, 19] open:[0, 2, "1."] isLoose hadBlankLineAfter
      Paragraph[4, 8] isTrailingBlankLine
        Text[4, 7] chars:[4, 7, "foo"]
      BulletList[13, 19] isTight
        BulletListItem[13, 19] open:[13, 14, "-"] isTight
          Paragraph[15, 19]
            Text[15, 18] chars:[15, 18, "bar"]
````````````````````````````````


The contents of a code block are literal text, and do not get parsed
as Markdown:

```````````````````````````````` example Indented code blocks: 4
    <a/>
    *hi*

    - one
.
<pre><code>&lt;a/&gt;
*hi*

- one
</code></pre>
.
Document[0, 29]
  IndentedCodeBlock[4, 29]
````````````````````````````````


Here we have three chunks separated by blank lines:

```````````````````````````````` example Indented code blocks: 5
    chunk1

    chunk2
  
 
 
    chunk3
.
<pre><code>chunk1

chunk2



chunk3
</code></pre>
.
Document[0, 41]
  IndentedCodeBlock[4, 41]
````````````````````````````````


Any initial spaces beyond four will be included in the content, even
in interior blank lines:

```````````````````````````````` example Indented code blocks: 6
    chunk1
      
      chunk2
.
<pre><code>chunk1
  
  chunk2
</code></pre>
.
Document[0, 31]
  IndentedCodeBlock[4, 31]
````````````````````````````````


An indented code block cannot interrupt a paragraph.  (This
allows hanging indents and the like.)

```````````````````````````````` example Indented code blocks: 7
Foo
    bar

.
<p>Foo
bar</p>
.
Document[0, 13]
  Paragraph[0, 12] isTrailingBlankLine
    Text[0, 3] chars:[0, 3, "Foo"]
    SoftLineBreak[3, 4]
    Text[8, 11] chars:[8, 11, "bar"]
````````````````````````````````


However, any non-blank line with fewer than four leading spaces ends
the code block immediately.  So a paragraph may occur immediately
after indented code:

```````````````````````````````` example Indented code blocks: 8
    foo
bar
.
<pre><code>foo
</code></pre>
<p>bar</p>
.
Document[0, 12]
  IndentedCodeBlock[4, 8]
  Paragraph[8, 12]
    Text[8, 11] chars:[8, 11, "bar"]
````````````````````````````````


And indented code can occur immediately before and after other kinds of
blocks:

```````````````````````````````` example Indented code blocks: 9
# Heading
    foo
Heading
------
    foo
----
.
<h1>Heading</h1>
<pre><code>foo
</code></pre>
<h2>Heading</h2>
<pre><code>foo
</code></pre>
<hr />
.
Document[0, 46]
  Heading[0, 9] textOpen:[0, 1, "#"] text:[2, 9, "Heading"]
    Text[2, 9] chars:[2, 9, "Heading"]
  IndentedCodeBlock[14, 18]
  Heading[18, 32] text:[18, 25, "Heading"] textClose:[26, 32, "------"]
    Text[18, 25] chars:[18, 25, "Heading"]
  IndentedCodeBlock[37, 41]
  ThematicBreak[41, 45]
````````````````````````````````


The first line can be indented more than four spaces:

```````````````````````````````` example Indented code blocks: 10
        foo
    bar
.
<pre><code>    foo
bar
</code></pre>
.
Document[0, 20]
  IndentedCodeBlock[4, 20]
````````````````````````````````


Blank lines preceding or following an indented code block
are not included in it:

```````````````````````````````` example Indented code blocks: 11

    
    foo
    

.
<pre><code>foo
</code></pre>
.
Document[0, 20]
  IndentedCodeBlock[10, 14]
````````````````````````````````


Trailing spaces are included in the code block's content:

```````````````````````````````` example Indented code blocks: 12
    foo  
.
<pre><code>foo  
</code></pre>
.
Document[0, 10]
  IndentedCodeBlock[4, 10]
````````````````````````````````



## Fenced code blocks

A [code fence](@) is a sequence
of at least three consecutive backtick characters (`` ` ``) or
tildes (`~`).  (Tildes and backticks cannot be mixed.)
A [fenced code block](@)
begins with a code fence, indented no more than three spaces.

The line with the opening code fence may optionally contain some text
following the code fence; this is trimmed of leading and trailing
spaces and called the [info string](@).
The [info string] may not contain any backtick
characters.  (The reason for this restriction is that otherwise
some inline code would be incorrectly interpreted as the
beginning of a fenced code block.)

The content of the code block consists of all subsequent lines, until
a closing [code fence] of the same type as the code block
began with (backticks or tildes), and with at least as many backticks
or tildes as the opening code fence.  If the leading code fence is
indented N spaces, then up to N spaces of indentation are removed from
each line of the content (if present).  (If a content line is not
indented, it is preserved unchanged.  If it is indented less than N
spaces, all of the indentation is removed.)

The closing code fence may be indented up to three spaces, and may be
followed only by spaces, which are ignored.  If the end of the
containing block (or document) is reached and no closing code fence
has been found, the code block contains all of the lines after the
opening code fence until the end of the containing block (or
document).  (An alternative spec would require backtracking in the
event that a closing code fence is not found.  But this makes parsing
much less efficient, and there seems to be no real down side to the
behavior described here.)

A fenced code block may interrupt a paragraph, and does not require
a blank line either before or after.

The content of a code fence is treated as literal text, not parsed
as inlines.  The first word of the [info string] is typically used to
specify the language of the code sample, and rendered in the `class`
attribute of the `code` tag.  However, this spec does not mandate any
particular treatment of the [info string].

Here is a simple example with backticks:

```````````````````````````````` example Fenced code blocks: 1
```
<
 >
```
.
<pre><code>&lt;
 &gt;
</code></pre>
.
Document[0, 13]
  FencedCodeBlock[0, 12] open:[0, 3, "```"] content:[4, 9] lines[2] close:[9, 12, "```"]
    Text[4, 9] chars:[4, 9, "<\n >\n"]
````````````````````````````````


With tildes:

```````````````````````````````` example Fenced code blocks: 2
~~~
<
 >
~~~
.
<pre><code>&lt;
 &gt;
</code></pre>
.
Document[0, 13]
  FencedCodeBlock[0, 12] open:[0, 3, "~~~"] content:[4, 9] lines[2] close:[9, 12, "~~~"]
    Text[4, 9] chars:[4, 9, "<\n >\n"]
````````````````````````````````


Fewer than three backticks is not enough:

```````````````````````````````` example Fenced code blocks: 3
``
foo
``
.
<p><code>foo</code></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Code[0, 9] textOpen:[0, 2, "``"] text:[2, 7, "\nfoo\n"] textClose:[7, 9, "``"]
      Text[2, 7] chars:[2, 7, "\nfoo\n"]
````````````````````````````````


The closing code fence must use the same character as the opening
fence:

```````````````````````````````` example Fenced code blocks: 4
```
aaa
~~~
```
.
<pre><code>aaa
~~~
</code></pre>
.
Document[0, 16]
  FencedCodeBlock[0, 15] open:[0, 3, "```"] content:[4, 12] lines[2] close:[12, 15, "```"]
    Text[4, 12] chars:[4, 12, "aaa\n~~~\n"]
````````````````````````````````


```````````````````````````````` example Fenced code blocks: 5
~~~
aaa
```
~~~
.
<pre><code>aaa
```
</code></pre>
.
Document[0, 16]
  FencedCodeBlock[0, 15] open:[0, 3, "~~~"] content:[4, 12] lines[2] close:[12, 15, "~~~"]
    Text[4, 12] chars:[4, 12, "aaa\n```\n"]
````````````````````````````````


The closing code fence must be at least as long as the opening fence:

```````````````````````````````` example Fenced code blocks: 6
````
aaa
```
``````
.
<pre><code>aaa
```
</code></pre>
.
Document[0, 20]
  FencedCodeBlock[0, 19] open:[0, 4, "````"] content:[5, 13] lines[2] close:[13, 19, "``````"]
    Text[5, 13] chars:[5, 13, "aaa\n```\n"]
````````````````````````````````


```````````````````````````````` example Fenced code blocks: 7
~~~~
aaa
~~~
~~~~
.
<pre><code>aaa
~~~
</code></pre>
.
Document[0, 18]
  FencedCodeBlock[0, 17] open:[0, 4, "~~~~"] content:[5, 13] lines[2] close:[13, 17, "~~~~"]
    Text[5, 13] chars:[5, 13, "aaa\n~~~\n"]
````````````````````````````````


Unclosed code blocks are closed by the end of the document
(or the enclosing [block quote][block quotes] or [list item][list items]):

```````````````````````````````` example Fenced code blocks: 8
```
.
<pre><code></code></pre>
.
Document[0, 4]
  FencedCodeBlock[0, 4] open:[0, 3, "```"] lines[0]
````````````````````````````````


```````````````````````````````` example Fenced code blocks: 9
`````

```
aaa
.
<pre><code>
```
aaa
</code></pre>
.
Document[0, 15]
  FencedCodeBlock[0, 15] open:[0, 5, "`````"] content:[6, 15] lines[3]
    Text[6, 15] chars:[6, 15, "\n```\naaa\n"]
````````````````````````````````


```````````````````````````````` example Fenced code blocks: 10
> ```
> aaa

bbb
.
<blockquote>
<pre><code>aaa
</code></pre>
</blockquote>
<p>bbb</p>
.
Document[0, 17]
  BlockQuote[0, 12] marker:[0, 1, ">"]
    FencedCodeBlock[2, 12] open:[2, 5, "```"] content:[8, 12] lines[1]
      Text[8, 12] chars:[8, 12, "aaa\n"]
  Paragraph[13, 17]
    Text[13, 16] chars:[13, 16, "bbb"]
````````````````````````````````


A code block can have all empty lines as its content:

```````````````````````````````` example Fenced code blocks: 11
```

  
```
.
<pre><code>
  
</code></pre>
.
Document[0, 12]
  FencedCodeBlock[0, 11] open:[0, 3, "```"] content:[4, 8] lines[2] close:[8, 11, "```"]
    Text[4, 8] chars:[4, 8, "\n  \n"]
````````````````````````````````


A code block can be empty:

```````````````````````````````` example Fenced code blocks: 12
```
```
.
<pre><code></code></pre>
.
Document[0, 8]
  FencedCodeBlock[0, 7] open:[0, 3, "```"] lines[0] close:[4, 7, "```"]
````````````````````````````````


Fences can be indented.  If the opening fence is indented,
content lines will have equivalent opening indentation removed,
if present:

```````````````````````````````` example Fenced code blocks: 13
 ```
 aaa
aaa
```
.
<pre><code>aaa
aaa
</code></pre>
.
Document[0, 18]
  FencedCodeBlock[1, 17] open:[1, 4, "```"] content:[6, 14] lines[2] close:[14, 17, "```"]
    Text[6, 14] chars:[6, 14, "aaa\naaa\n"]
````````````````````````````````


```````````````````````````````` example Fenced code blocks: 14
  ```
aaa
  aaa
aaa
  ```
.
<pre><code>aaa
aaa
aaa
</code></pre>
.
Document[0, 26]
  FencedCodeBlock[2, 25] open:[2, 5, "```"] content:[6, 20] lines[3] close:[22, 25, "```"]
    Text[6, 20] chars:[6, 20, "aaa\na … \naaa\n"]
````````````````````````````````


```````````````````````````````` example Fenced code blocks: 15
   ```
   aaa
    aaa
  aaa
   ```
.
<pre><code>aaa
 aaa
aaa
</code></pre>
.
Document[0, 35]
  FencedCodeBlock[3, 34] open:[3, 6, "```"] content:[10, 28] lines[3] close:[31, 34, "```"]
    Text[10, 28] chars:[10, 28, "aaa\n  … \naaa\n"]
````````````````````````````````


Four spaces indentation produces an indented code block:

```````````````````````````````` example Fenced code blocks: 16
    ```
    aaa
    ```
.
<pre><code>```
aaa
```
</code></pre>
.
Document[0, 24]
  IndentedCodeBlock[4, 24]
````````````````````````````````


Closing fences may be indented by 0-3 spaces, and their indentation
need not match that of the opening fence:

```````````````````````````````` example Fenced code blocks: 17
```
aaa
  ```
.
<pre><code>aaa
</code></pre>
.
Document[0, 14]
  FencedCodeBlock[0, 13] open:[0, 3, "```"] content:[4, 8] lines[1] close:[10, 13, "```"]
    Text[4, 8] chars:[4, 8, "aaa\n"]
````````````````````````````````


```````````````````````````````` example Fenced code blocks: 18
   ```
aaa
  ```
.
<pre><code>aaa
</code></pre>
.
Document[0, 17]
  FencedCodeBlock[3, 16] open:[3, 6, "```"] content:[7, 11] lines[1] close:[13, 16, "```"]
    Text[7, 11] chars:[7, 11, "aaa\n"]
````````````````````````````````


This is not a closing fence, because it is indented 4 spaces:

```````````````````````````````` example Fenced code blocks: 19
```
aaa
    ```
.
<pre><code>aaa
    ```
</code></pre>
.
Document[0, 16]
  FencedCodeBlock[0, 16] open:[0, 3, "```"] content:[4, 16] lines[2]
    Text[4, 16] chars:[4, 16, "aaa\n  …  ```\n"]
````````````````````````````````



Code fences (opening and closing) cannot contain internal spaces:

```````````````````````````````` example Fenced code blocks: 20
``` ```
aaa
.
<p><code></code>
aaa</p>
.
Document[0, 12]
  Paragraph[0, 12]
    Code[0, 7] textOpen:[0, 3, "```"] text:[3, 4, " "] textClose:[4, 7, "```"]
      Text[3, 4] chars:[3, 4, " "]
    SoftLineBreak[7, 8]
    Text[8, 11] chars:[8, 11, "aaa"]
````````````````````````````````


```````````````````````````````` example Fenced code blocks: 21
~~~~~~
aaa
~~~ ~~
.
<pre><code>aaa
~~~ ~~
</code></pre>
.
Document[0, 18]
  FencedCodeBlock[0, 18] open:[0, 6, "~~~~~~"] content:[7, 18] lines[2]
    Text[7, 18] chars:[7, 18, "aaa\n~ … ~ ~~\n"]
````````````````````````````````


Fenced code blocks can interrupt paragraphs, and can be followed
directly by paragraphs, without a blank line between:

```````````````````````````````` example Fenced code blocks: 22
foo
```
bar
```
baz
.
<p>foo</p>
<pre><code>bar
</code></pre>
<p>baz</p>
.
Document[0, 20]
  Paragraph[0, 4]
    Text[0, 3] chars:[0, 3, "foo"]
  FencedCodeBlock[4, 15] open:[4, 7, "```"] content:[8, 12] lines[1] close:[12, 15, "```"]
    Text[8, 12] chars:[8, 12, "bar\n"]
  Paragraph[16, 20]
    Text[16, 19] chars:[16, 19, "baz"]
````````````````````````````````


Other blocks can also occur before and after fenced code blocks
without an intervening blank line:

```````````````````````````````` example Fenced code blocks: 23
foo
---
~~~
bar
~~~
# baz
.
<h2>foo</h2>
<pre><code>bar
</code></pre>
<h1>baz</h1>
.
Document[0, 26]
  Heading[0, 7] text:[0, 3, "foo"] textClose:[4, 7, "---"]
    Text[0, 3] chars:[0, 3, "foo"]
  FencedCodeBlock[8, 19] open:[8, 11, "~~~"] content:[12, 16] lines[1] close:[16, 19, "~~~"]
    Text[12, 16] chars:[12, 16, "bar\n"]
  Heading[20, 25] textOpen:[20, 21, "#"] text:[22, 25, "baz"]
    Text[22, 25] chars:[22, 25, "baz"]
````````````````````````````````


An [info string] can be provided after the opening code fence.
Opening and closing spaces will be stripped, and the first word, prefixed
with `language-`, is used as the value for the `class` attribute of the
`code` element within the enclosing `pre` element.

```````````````````````````````` example Fenced code blocks: 24
```ruby
def foo(x)
  return 3
end
```
.
<pre><code class="language-ruby">def foo(x)
  return 3
end
</code></pre>
.
Document[0, 38]
  FencedCodeBlock[0, 37] open:[0, 3, "```"] info:[3, 7, "ruby"] content:[8, 34] lines[3] close:[34, 37, "```"]
    Text[8, 34] chars:[8, 34, "def f … \nend\n"]
````````````````````````````````


```````````````````````````````` example Fenced code blocks: 25
~~~~    ruby startline=3 $%@#$
def foo(x)
  return 3
end
~~~~~~~
.
<pre><code class="language-ruby">def foo(x)
  return 3
end
</code></pre>
.
Document[0, 65]
  FencedCodeBlock[0, 64] open:[0, 4, "~~~~"] info:[8, 30, "ruby startline=3 $%@#$"] content:[31, 57] lines[3] close:[57, 64, "~~~~~~~"]
    Text[31, 57] chars:[31, 57, "def f … \nend\n"]
````````````````````````````````


```````````````````````````````` example Fenced code blocks: 26
````;
````
.
<pre><code class="language-;"></code></pre>
.
Document[0, 11]
  FencedCodeBlock[0, 10] open:[0, 4, "````"] info:[4, 5, ";"] lines[0] close:[6, 10, "````"]
````````````````````````````````


[Info strings] for backtick code blocks cannot contain backticks:

```````````````````````````````` example Fenced code blocks: 27
``` aa ```
foo
.
<p><code>aa</code>
foo</p>
.
Document[0, 15]
  Paragraph[0, 15]
    Code[0, 10] textOpen:[0, 3, "```"] text:[3, 7, " aa "] textClose:[7, 10, "```"]
      Text[3, 7] chars:[3, 7, " aa "]
    SoftLineBreak[10, 11]
    Text[11, 14] chars:[11, 14, "foo"]
````````````````````````````````


Closing code fences cannot have [info strings]:

```````````````````````````````` example Fenced code blocks: 28
```
``` aaa
```
.
<pre><code>``` aaa
</code></pre>
.
Document[0, 16]
  FencedCodeBlock[0, 15] open:[0, 3, "```"] content:[4, 12] lines[1] close:[12, 15, "```"]
    Text[4, 12] chars:[4, 12, "``` aaa\n"]
````````````````````````````````



## HTML blocks

An [HTML block](@) is a group of lines that is treated
as raw HTML (and will not be escaped in HTML output).

There are seven kinds of [HTML block], which can be defined
by their start and end conditions.  The block begins with a line that
meets a [start condition](@) (after up to three spaces
optional indentation).  It ends with the first subsequent line that
meets a matching [end condition](@), or the last line of
the document or other [container block]), if no line is encountered that meets the
[end condition].  If the first line meets both the [start condition]
and the [end condition], the block will contain just that line.

1.  **Start condition:**  line begins with the string `<script`,
`<pre`, or `<style` (case-insensitive), followed by whitespace,
the string `>`, or the end of the line.\
**End condition:**  line contains an end tag
`</script>`, `</pre>`, or `</style>` (case-insensitive; it
need not match the start tag).

2.  **Start condition:** line begins with the string `<!--`.\
**End condition:**  line contains the string `-->`.

3.  **Start condition:** line begins with the string `<?`.\
**End condition:** line contains the string `?>`.

4.  **Start condition:** line begins with the string `<!`
followed by an uppercase ASCII letter.\
**End condition:** line contains the character `>`.

5.  **Start condition:**  line begins with the string
`<![CDATA[`.\
**End condition:** line contains the string `]]>`.

6.  **Start condition:** line begins the string `<` or `</`
followed by one of the strings (case-insensitive) `address`,
`article`, `aside`, `base`, `basefont`, `blockquote`, `body`,
`caption`, `center`, `col`, `colgroup`, `dd`, `details`, `dialog`,
`dir`, `div`, `dl`, `dt`, `fieldset`, `figcaption`, `figure`,
`footer`, `form`, `frame`, `frameset`,
`h1`, `h2`, `h3`, `h4`, `h5`, `h6`, `head`, `header`, `hr`,
`html`, `iframe`, `legend`, `li`, `link`, `main`, `menu`, `menuitem`,
`meta`, `nav`, `noframes`, `ol`, `optgroup`, `option`, `p`, `param`,
`section`, `source`, `summary`, `table`, `tbody`, `td`,
`tfoot`, `th`, `thead`, `title`, `tr`, `track`, `ul`, followed
by [whitespace], the end of the line, the string `>`, or
the string `/>`.\
**End condition:** line is followed by a [blank line].

7.  **Start condition:**  line begins with a complete [open tag]
or [closing tag] (with any [tag name] other than `script`,
`style`, or `pre`) followed only by [whitespace]
or the end of the line.\
**End condition:** line is followed by a [blank line].

HTML blocks continue until they are closed by their appropriate
[end condition], or the last line of the document or other [container block].
This means any HTML **within an HTML block** that might otherwise be recognised
as a start condition will be ignored by the parser and passed through as-is,
without changing the parser's state.

For instance, `<pre>` within a HTML block started by `<table>` will not affect
the parser state; as the HTML block was started in by start condition 6, it
will end at any blank line. This can be surprising:

```````````````````````````````` example HTML blocks: 1
<table><tr><td>
<pre>
**Hello**,

_world_.
</pre>
</td></tr></table>
.
<table><tr><td>
<pre>
**Hello**,
<p><em>world</em>.
</pre></p>
</td></tr></table>
````````````````````````````````

In this case, the HTML block is terminated by the newline — the `**hello**`
text remains verbatim — and regular parsing resumes, with a paragraph,
emphasised `world` and inline and block HTML following.

All types of [HTML blocks] except type 7 may interrupt
a paragraph.  Blocks of type 7 may not interrupt a paragraph.
(This restriction is intended to prevent unwanted interpretation
of long tags inside a wrapped paragraph as starting HTML blocks.)

Some simple examples follow.  Here are some basic HTML blocks
of type 6:

```````````````````````````````` example HTML blocks: 2
<table>
  <tr>
    <td>
           hi
    </td>
  </tr>
</table>

okay.
.
<table>
  <tr>
    <td>
           hi
    </td>
  </tr>
</table>
<p>okay.</p>
.
Document[0, 72]
  HtmlBlock[0, 65]
  Paragraph[66, 72]
    Text[66, 71] chars:[66, 71, "okay."]
````````````````````````````````


```````````````````````````````` example HTML blocks: 3
 <div>
  *hello*
         <foo><a>
.
 <div>
  *hello*
         <foo><a>
.
Document[0, 35]
  HtmlBlock[0, 35]
````````````````````````````````


A block can also start with a closing tag:

```````````````````````````````` example HTML blocks: 4
</div>
*foo*
.
</div>
*foo*
.
Document[0, 13]
  HtmlBlock[0, 13]
````````````````````````````````


Here we have two HTML blocks with a Markdown paragraph between them:

```````````````````````````````` example HTML blocks: 5
<DIV CLASS="foo">

*Markdown*

</DIV>
.
<DIV CLASS="foo">
<p><em>Markdown</em></p>
</DIV>
.
Document[0, 38]
  HtmlBlock[0, 18]
  Paragraph[19, 30] isTrailingBlankLine
    Emphasis[19, 29] textOpen:[19, 20, "*"] text:[20, 28, "Markdown"] textClose:[28, 29, "*"]
      Text[20, 28] chars:[20, 28, "Markdown"]
  HtmlBlock[31, 38]
````````````````````````````````


The tag on the first line can be partial, as long
as it is split where there would be whitespace:

```````````````````````````````` example HTML blocks: 6
<div id="foo"
  class="bar">
</div>
.
<div id="foo"
  class="bar">
</div>
.
Document[0, 36]
  HtmlBlock[0, 36]
````````````````````````````````


```````````````````````````````` example HTML blocks: 7
<div id="foo" class="bar
  baz">
</div>
.
<div id="foo" class="bar
  baz">
</div>
.
Document[0, 40]
  HtmlBlock[0, 40]
````````````````````````````````


An open tag need not be closed:

```````````````````````````````` example HTML blocks: 8
<div>
*foo*

*bar*
.
<div>
*foo*
<p><em>bar</em></p>
.
Document[0, 19]
  HtmlBlock[0, 12]
  Paragraph[13, 19]
    Emphasis[13, 18] textOpen:[13, 14, "*"] text:[14, 17, "bar"] textClose:[17, 18, "*"]
      Text[14, 17] chars:[14, 17, "bar"]
````````````````````````````````



A partial tag need not even be completed (garbage
in, garbage out):

```````````````````````````````` example HTML blocks: 9
<div id="foo"
*hi*
.
<div id="foo"
*hi*
.
Document[0, 19]
  HtmlBlock[0, 19]
````````````````````````````````


```````````````````````````````` example HTML blocks: 10
<div class
foo
.
<div class
foo
.
Document[0, 15]
  HtmlBlock[0, 15]
````````````````````````````````


The initial tag doesn't even need to be a valid
tag, as long as it starts like one:

```````````````````````````````` example HTML blocks: 11
<div *???-&&&-<---
*foo*
.
<div *???-&&&-<---
*foo*
.
Document[0, 25]
  HtmlBlock[0, 25]
````````````````````````````````


In type 6 blocks, the initial tag need not be on a line by
itself:

```````````````````````````````` example HTML blocks: 12
<div><a href="bar">*foo*</a></div>
.
<div><a href="bar">*foo*</a></div>
.
Document[0, 35]
  HtmlBlock[0, 35]
````````````````````````````````


```````````````````````````````` example HTML blocks: 13
<table><tr><td>
foo
</td></tr></table>
.
<table><tr><td>
foo
</td></tr></table>
.
Document[0, 39]
  HtmlBlock[0, 39]
````````````````````````````````


Everything until the next blank line or end of document
gets included in the HTML block.  So, in the following
example, what looks like a Markdown code block
is actually part of the HTML block, which continues until a blank
line or the end of the document is reached:

```````````````````````````````` example HTML blocks: 14
<div></div>
``` c
int x = 33;
```
.
<div></div>
``` c
int x = 33;
```
.
Document[0, 34]
  HtmlBlock[0, 34]
````````````````````````````````


To start an [HTML block] with a tag that is *not* in the
list of block-level tags in (6), you must put the tag by
itself on the first line (and it must be complete):

```````````````````````````````` example HTML blocks: 15
<a href="foo">
*bar*
</a>
.
<a href="foo">
*bar*
</a>
.
Document[0, 26]
  HtmlBlock[0, 26]
````````````````````````````````


In type 7 blocks, the [tag name] can be anything:

```````````````````````````````` example HTML blocks: 16
<Warning>
*bar*
</Warning>
.
<Warning>
*bar*
</Warning>
.
Document[0, 27]
  HtmlBlock[0, 27]
````````````````````````````````


```````````````````````````````` example HTML blocks: 17
<i class="foo">
*bar*
</i>
.
<i class="foo">
*bar*
</i>
.
Document[0, 27]
  HtmlBlock[0, 27]
````````````````````````````````


```````````````````````````````` example HTML blocks: 18
</ins>
*bar*
.
</ins>
*bar*
.
Document[0, 13]
  HtmlBlock[0, 13]
````````````````````````````````


These rules are designed to allow us to work with tags that
can function as either block-level or inline-level tags.
The `<del>` tag is a nice example.  We can surround content with
`<del>` tags in three different ways.  In this case, we get a raw
HTML block, because the `<del>` tag is on a line by itself:

```````````````````````````````` example HTML blocks: 19
<del>
*foo*
</del>
.
<del>
*foo*
</del>
.
Document[0, 19]
  HtmlBlock[0, 19]
````````````````````````````````


In this case, we get a raw HTML block that just includes
the `<del>` tag (because it ends with the following blank
line).  So the contents get interpreted as CommonMark:

```````````````````````````````` example HTML blocks: 20
<del>

*foo*

</del>
.
<del>
<p><em>foo</em></p>
</del>
.
Document[0, 21]
  HtmlBlock[0, 6]
  Paragraph[7, 13] isTrailingBlankLine
    Emphasis[7, 12] textOpen:[7, 8, "*"] text:[8, 11, "foo"] textClose:[11, 12, "*"]
      Text[8, 11] chars:[8, 11, "foo"]
  HtmlBlock[14, 21]
````````````````````````````````


Finally, in this case, the `<del>` tags are interpreted
as [raw HTML] *inside* the CommonMark paragraph.  (Because
the tag is not on a line by itself, we get inline HTML
rather than an [HTML block].)

```````````````````````````````` example HTML blocks: 21
<del>*foo*</del>
.
<p><del><em>foo</em></del></p>
.
Document[0, 17]
  Paragraph[0, 17]
    HtmlInline[0, 5] chars:[0, 5, "<del>"]
    Emphasis[5, 10] textOpen:[5, 6, "*"] text:[6, 9, "foo"] textClose:[9, 10, "*"]
      Text[6, 9] chars:[6, 9, "foo"]
    HtmlInline[10, 16] chars:[10, 16, "</del>"]
````````````````````````````````


HTML tags designed to contain literal content
(`script`, `style`, `pre`), comments, processing instructions,
and declarations are treated somewhat differently.
Instead of ending at the first blank line, these blocks
end at the first line containing a corresponding end tag.
As a result, these blocks can contain blank lines:

A pre tag (type 1):

```````````````````````````````` example HTML blocks: 22
<pre language="haskell"><code>
import Text.HTML.TagSoup

main :: IO ()
main = print $ parseTags tags
</code></pre>
okay
.
<pre language="haskell"><code>
import Text.HTML.TagSoup

main :: IO ()
main = print $ parseTags tags
</code></pre>
<p>okay</p>
.
Document[0, 120]
  HtmlBlock[0, 115]
  Paragraph[115, 120]
    Text[115, 119] chars:[115, 119, "okay"]
````````````````````````````````


A script tag (type 1):

```````````````````````````````` example HTML blocks: 23
<script type="text/javascript">
// JavaScript example

document.getElementById("demo").innerHTML = "Hello JavaScript!";
</script>
okay
.
<script type="text/javascript">
// JavaScript example

document.getElementById("demo").innerHTML = "Hello JavaScript!";
</script>
<p>okay</p>
.
Document[0, 135]
  HtmlBlock[0, 130]
  Paragraph[130, 135]
    Text[130, 134] chars:[130, 134, "okay"]
````````````````````````````````


A style tag (type 1):

```````````````````````````````` example HTML blocks: 24
<style
  type="text/css">
h1 {color:red;}

p {color:blue;}
</style>
okay
.
<style
  type="text/css">
h1 {color:red;}

p {color:blue;}
</style>
<p>okay</p>
.
Document[0, 73]
  HtmlBlock[0, 68]
  Paragraph[68, 73]
    Text[68, 72] chars:[68, 72, "okay"]
````````````````````````````````


If there is no matching end tag, the block will end at the
end of the document (or the enclosing [block quote][block quotes]
or [list item][list items]):

```````````````````````````````` example HTML blocks: 25
<style
  type="text/css">

foo
.
<style
  type="text/css">

foo
.
Document[0, 31]
  HtmlBlock[0, 31]
````````````````````````````````


```````````````````````````````` example HTML blocks: 26
> <div>
> foo

bar
.
<blockquote>
<div>
foo
</blockquote>
<p>bar</p>
.
Document[0, 19]
  BlockQuote[0, 14] marker:[0, 1, ">"]
    HtmlBlock[2, 14]
  Paragraph[15, 19]
    Text[15, 18] chars:[15, 18, "bar"]
````````````````````````````````


```````````````````````````````` example HTML blocks: 27
- <div>
- foo
.
<ul>
<li>
<div>
</li>
<li>foo</li>
</ul>
.
Document[0, 14]
  BulletList[0, 14] isTight
    BulletListItem[0, 8] open:[0, 1, "-"] isTight
      HtmlBlock[2, 8]
    BulletListItem[8, 14] open:[8, 9, "-"] isTight
      Paragraph[10, 14]
        Text[10, 13] chars:[10, 13, "foo"]
````````````````````````````````


The end tag can occur on the same line as the start tag:

```````````````````````````````` example HTML blocks: 28
<style>p{color:red;}</style>
*foo*
.
<style>p{color:red;}</style>
<p><em>foo</em></p>
.
Document[0, 35]
  HtmlBlock[0, 29]
  Paragraph[29, 35]
    Emphasis[29, 34] textOpen:[29, 30, "*"] text:[30, 33, "foo"] textClose:[33, 34, "*"]
      Text[30, 33] chars:[30, 33, "foo"]
````````````````````````````````


```````````````````````````````` example HTML blocks: 29
<!-- foo -->*bar*
*baz*
.
<!-- foo -->*bar*
<p><em>baz</em></p>
.
Document[0, 24]
  HtmlCommentBlock[0, 18]
  Paragraph[18, 24]
    Emphasis[18, 23] textOpen:[18, 19, "*"] text:[19, 22, "baz"] textClose:[22, 23, "*"]
      Text[19, 22] chars:[19, 22, "baz"]
````````````````````````````````


Note that anything on the last line after the
end tag will be included in the [HTML block]:

```````````````````````````````` example HTML blocks: 30
<script>
foo
</script>1. *bar*
.
<script>
foo
</script>1. *bar*
.
Document[0, 31]
  HtmlBlock[0, 31]
````````````````````````````````


A comment (type 2):

```````````````````````````````` example HTML blocks: 31
<!-- Foo

bar
   baz -->
okay
.
<!-- Foo

bar
   baz -->
<p>okay</p>
.
Document[0, 30]
  HtmlCommentBlock[0, 25]
  Paragraph[25, 30]
    Text[25, 29] chars:[25, 29, "okay"]
````````````````````````````````



A processing instruction (type 3):

```````````````````````````````` example HTML blocks: 32
<?php

  echo '>';

?>
okay
.
<?php

  echo '>';

?>
<p>okay</p>
.
Document[0, 28]
  HtmlBlock[0, 23]
  Paragraph[23, 28]
    Text[23, 27] chars:[23, 27, "okay"]
````````````````````````````````


A declaration (type 4):

```````````````````````````````` example HTML blocks: 33
<!DOCTYPE html>
.
<!DOCTYPE html>
.
Document[0, 16]
  HtmlBlock[0, 16]
````````````````````````````````


CDATA (type 5):

```````````````````````````````` example HTML blocks: 34
<![CDATA[
function matchwo(a,b)
{
  if (a < b && a < 0) then {
    return 1;

  } else {

    return 0;
  }
}
]]>
okay
.
<![CDATA[
function matchwo(a,b)
{
  if (a < b && a < 0) then {
    return 1;

  } else {

    return 0;
  }
}
]]>
<p>okay</p>
.
Document[0, 119]
  HtmlBlock[0, 114]
  Paragraph[114, 119]
    Text[114, 118] chars:[114, 118, "okay"]
````````````````````````````````


The opening tag can be indented 1-3 spaces, but not 4:

```````````````````````````````` example HTML blocks: 35
  <!-- foo -->

    <!-- foo -->
.
  <!-- foo -->
<pre><code>&lt;!-- foo --&gt;
</code></pre>
.
Document[0, 33]
  HtmlCommentBlock[0, 15]
  IndentedCodeBlock[20, 33]
````````````````````````````````


```````````````````````````````` example HTML blocks: 36
  <div>

    <div>
.
  <div>
<pre><code>&lt;div&gt;
</code></pre>
.
Document[0, 19]
  HtmlBlock[0, 8]
  IndentedCodeBlock[13, 19]
````````````````````````````````


An HTML block of types 1--6 can interrupt a paragraph, and need not be
preceded by a blank line.

```````````````````````````````` example HTML blocks: 37
Foo
<div>
bar
</div>
.
<p>Foo</p>
<div>
bar
</div>
.
Document[0, 21]
  Paragraph[0, 4]
    Text[0, 3] chars:[0, 3, "Foo"]
  HtmlBlock[4, 21]
````````````````````````````````


However, a following blank line is needed, except at the end of
a document, and except for blocks of types 1--5, above:

```````````````````````````````` example HTML blocks: 38
<div>
bar
</div>
*foo*
.
<div>
bar
</div>
*foo*
.
Document[0, 23]
  HtmlBlock[0, 23]
````````````````````````````````


HTML blocks of type 7 cannot interrupt a paragraph:

```````````````````````````````` example HTML blocks: 39
Foo
<a href="bar">
baz
.
<p>Foo
<a href="bar">
baz</p>
.
Document[0, 23]
  Paragraph[0, 23]
    Text[0, 3] chars:[0, 3, "Foo"]
    SoftLineBreak[3, 4]
    HtmlInline[4, 18] chars:[4, 18, "<a hr … bar\">"]
    SoftLineBreak[18, 19]
    Text[19, 22] chars:[19, 22, "baz"]
````````````````````````````````


This rule differs from John Gruber's original Markdown syntax
specification, which says:

> The only restrictions are that block-level HTML elements —
> e.g. `<div>`, `<table>`, `<pre>`, `<p>`, etc. — must be separated from
> surrounding content by blank lines, and the start and end tags of the
> block should not be indented with tabs or spaces.

In some ways Gruber's rule is more restrictive than the one given
here:

- It requires that an HTML block be preceded by a blank line.
- It does not allow the start tag to be indented.
- It requires a matching end tag, which it also does not allow to
  be indented.

Most Markdown implementations (including some of Gruber's own) do not
respect all of these restrictions.

There is one respect, however, in which Gruber's rule is more liberal
than the one given here, since it allows blank lines to occur inside
an HTML block.  There are two reasons for disallowing them here.
First, it removes the need to parse balanced tags, which is
expensive and can require backtracking from the end of the document
if no matching end tag is found. Second, it provides a very simple
and flexible way of including Markdown content inside HTML tags:
simply separate the Markdown from the HTML using blank lines:

Compare:

```````````````````````````````` example HTML blocks: 40
<div>

*Emphasized* text.

</div>
.
<div>
<p><em>Emphasized</em> text.</p>
</div>
.
Document[0, 34]
  HtmlBlock[0, 6]
  Paragraph[7, 26] isTrailingBlankLine
    Emphasis[7, 19] textOpen:[7, 8, "*"] text:[8, 18, "Emphasized"] textClose:[18, 19, "*"]
      Text[8, 18] chars:[8, 18, "Emphasized"]
    Text[19, 25] chars:[19, 25, " text."]
  HtmlBlock[27, 34]
````````````````````````````````


```````````````````````````````` example HTML blocks: 41
<div>
*Emphasized* text.
</div>
.
<div>
*Emphasized* text.
</div>
.
Document[0, 32]
  HtmlBlock[0, 32]
````````````````````````````````


Some Markdown implementations have adopted a convention of
interpreting content inside tags as text if the open tag has
the attribute `markdown=1`.  The rule given above seems a simpler and
more elegant way of achieving the same expressive power, which is also
much simpler to parse.

The main potential drawback is that one can no longer paste HTML
blocks into Markdown documents with 100% reliability.  However,
*in most cases* this will work fine, because the blank lines in
HTML are usually followed by HTML block tags.  For example:

```````````````````````````````` example HTML blocks: 42
<table>

<tr>

<td>
Hi
</td>

</tr>

</table>
.
<table>
<tr>
<td>
Hi
</td>
</tr>
</table>
.
Document[0, 46]
  HtmlBlock[0, 8]
  HtmlBlock[9, 14]
  HtmlBlock[15, 29]
  HtmlBlock[30, 36]
  HtmlBlock[37, 46]
````````````````````````````````


There are problems, however, if the inner tags are indented
*and* separated by spaces, as then they will be interpreted as
an indented code block:

```````````````````````````````` example HTML blocks: 43
<table>

  <tr>

    <td>
      Hi
    </td>

  </tr>

</table>
.
<table>
  <tr>
<pre><code>&lt;td&gt;
  Hi
&lt;/td&gt;
</code></pre>
  </tr>
</table>
.
Document[0, 64]
  HtmlBlock[0, 8]
  HtmlBlock[9, 16]
  IndentedCodeBlock[21, 45]
  HtmlBlock[46, 54]
  HtmlBlock[55, 64]
````````````````````````````````


Fortunately, blank lines are usually not necessary and can be
deleted.  The exception is inside `<pre>` tags, but as described
above, raw HTML blocks starting with `<pre>` *can* contain blank
lines.

## Link reference definitions

A [link reference definition](@)
consists of a [link label], indented up to three spaces, followed
by a colon (`:`), optional [whitespace] (including up to one
[line ending]), a [link destination],
optional [whitespace] (including up to one
[line ending]), and an optional [link
title], which if it is present must be separated
from the [link destination] by [whitespace].
No further [non-whitespace characters] may occur on the line.

A [link reference definition]
does not correspond to a structural element of a document.  Instead, it
defines a label which can be used in [reference links]
and reference-style [images] elsewhere in the document.  [Link
reference definitions] can come either before or after the links that use
them.

```````````````````````````````` example Link reference definitions: 1
[foo]: /url "title"

[foo]
.
<p><a href="/url" title="title">foo</a></p>
.
Document[0, 27]
  Reference[0, 19] refOpen:[0, 1, "["] ref:[1, 4, "foo"] refClose:[4, 6, "]:"] url:[7, 11, "/url"] titleOpen:[12, 13, "\""] title:[13, 18, "title"] titleClose:[18, 19, "\""]
  Paragraph[21, 27]
    LinkRef[21, 26] referenceOpen:[21, 22, "["] reference:[22, 25, "foo"] referenceClose:[25, 26, "]"]
      Text[22, 25] chars:[22, 25, "foo"]
````````````````````````````````


```````````````````````````````` example Link reference definitions: 2
   [foo]: 
      /url  
           'the title'  

[foo]
.
<p><a href="/url" title="the title">foo</a></p>
.
Document[0, 56]
  Reference[3, 46] refOpen:[3, 4, "["] ref:[4, 7, "foo"] refClose:[7, 9, "]:"] url:[17, 21, "/url"] titleOpen:[35, 36, "'"] title:[36, 45, "the title"] titleClose:[45, 46, "'"]
  Paragraph[50, 56]
    LinkRef[50, 55] referenceOpen:[50, 51, "["] reference:[51, 54, "foo"] referenceClose:[54, 55, "]"]
      Text[51, 54] chars:[51, 54, "foo"]
````````````````````````````````


```````````````````````````````` example Link reference definitions: 3
[Foo*bar\]]:my_(url) 'title (with parens)'

[Foo*bar\]]
.
<p><a href="my_(url)" title="title (with parens)">Foo*bar]</a></p>
.
Document[0, 56]
  Reference[0, 42] refOpen:[0, 1, "["] ref:[1, 10, "Foo*bar\]"] refClose:[10, 12, "]:"] url:[12, 20, "my_(url)"] titleOpen:[21, 22, "'"] title:[22, 41, "title (with parens)"] titleClose:[41, 42, "'"]
  Paragraph[44, 56]
    LinkRef[44, 55] referenceOpen:[44, 45, "["] reference:[45, 54, "Foo*bar\]"] referenceClose:[54, 55, "]"]
      Text[45, 54] chars:[45, 54, "Foo*bar\]"]
````````````````````````````````


```````````````````````````````` example Link reference definitions: 4
[Foo bar]:
<my%20url>
'title'

[Foo bar]
.
<p><a href="my%20url" title="title">Foo bar</a></p>
.
Document[0, 41]
  Reference[0, 29] refOpen:[0, 1, "["] ref:[1, 8, "Foo bar"] refClose:[8, 10, "]:"] urlOpen:[11, 12, "<"] url:[12, 20, "my%20url"] urlClose:[20, 21, ">"] titleOpen:[22, 23, "'"] title:[23, 28, "title"] titleClose:[28, 29, "'"]
  Paragraph[31, 41]
    LinkRef[31, 40] referenceOpen:[31, 32, "["] reference:[32, 39, "Foo bar"] referenceClose:[39, 40, "]"]
      Text[32, 39] chars:[32, 39, "Foo bar"]
````````````````````````````````


The title may extend over multiple lines:

```````````````````````````````` example Link reference definitions: 5
[foo]: /url '
title
line1
line2
'

[foo]
.
<p><a href="/url" title="
title
line1
line2
">foo</a></p>
.
Document[0, 41]
  Reference[0, 33] refOpen:[0, 1, "["] ref:[1, 4, "foo"] refClose:[4, 6, "]:"] url:[7, 11, "/url"] titleOpen:[12, 13, "'"] title:[13, 32, "\ntitle\nline1\nline2\n"] titleClose:[32, 33, "'"]
  Paragraph[35, 41]
    LinkRef[35, 40] referenceOpen:[35, 36, "["] reference:[36, 39, "foo"] referenceClose:[39, 40, "]"]
      Text[36, 39] chars:[36, 39, "foo"]
````````````````````````````````


However, it may not contain a [blank line]:

```````````````````````````````` example Link reference definitions: 6
[foo]: /url 'title

with blank line'

[foo]
.
<p>[foo]: /url 'title</p>
<p>with blank line'</p>
<p>[foo]</p>
.
Document[0, 44]
  Paragraph[0, 19] isTrailingBlankLine
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
    Text[5, 18] chars:[5, 18, ": /ur … title"]
  Paragraph[20, 37] isTrailingBlankLine
    Text[20, 36] chars:[20, 36, "with  … line'"]
  Paragraph[38, 44]
    LinkRef[38, 43] referenceOpen:[38, 39, "["] reference:[39, 42, "foo"] referenceClose:[42, 43, "]"]
      Text[39, 42] chars:[39, 42, "foo"]
````````````````````````````````


The title may be omitted:

```````````````````````````````` example Link reference definitions: 7
[foo]:
/url

[foo]
.
<p><a href="/url">foo</a></p>
.
Document[0, 19]
  Reference[0, 11] refOpen:[0, 1, "["] ref:[1, 4, "foo"] refClose:[4, 6, "]:"] url:[7, 11, "/url"]
  Paragraph[13, 19]
    LinkRef[13, 18] referenceOpen:[13, 14, "["] reference:[14, 17, "foo"] referenceClose:[17, 18, "]"]
      Text[14, 17] chars:[14, 17, "foo"]
````````````````````````````````


The link destination may not be omitted:

```````````````````````````````` example Link reference definitions: 8
[foo]:

[foo]
.
<p>[foo]:</p>
<p>[foo]</p>
.
Document[0, 14]
  Paragraph[0, 7] isTrailingBlankLine
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
    Text[5, 6] chars:[5, 6, ":"]
  Paragraph[8, 14]
    LinkRef[8, 13] referenceOpen:[8, 9, "["] reference:[9, 12, "foo"] referenceClose:[12, 13, "]"]
      Text[9, 12] chars:[9, 12, "foo"]
````````````````````````````````


Both title and destination can contain backslash escapes
and literal backslashes:

```````````````````````````````` example Link reference definitions: 9
[foo]: /url\bar\*baz "foo\"bar\baz"

[foo]
.
<p><a href="/url%5Cbar*baz" title="foo&quot;bar\baz">foo</a></p>
.
Document[0, 43]
  Reference[0, 35] refOpen:[0, 1, "["] ref:[1, 4, "foo"] refClose:[4, 6, "]:"] url:[7, 20, "/url\bar\*baz"] titleOpen:[21, 22, "\""] title:[22, 34, "foo\\"bar\baz"] titleClose:[34, 35, "\""]
  Paragraph[37, 43]
    LinkRef[37, 42] referenceOpen:[37, 38, "["] reference:[38, 41, "foo"] referenceClose:[41, 42, "]"]
      Text[38, 41] chars:[38, 41, "foo"]
````````````````````````````````


A link can come before its corresponding definition:

```````````````````````````````` example Link reference definitions: 10
[foo]

[foo]: url
.
<p><a href="url">foo</a></p>
.
Document[0, 18]
  Paragraph[0, 6] isTrailingBlankLine
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
  Reference[7, 17] refOpen:[7, 8, "["] ref:[8, 11, "foo"] refClose:[11, 13, "]:"] url:[14, 17, "url"]
````````````````````````````````


If there are several matching definitions, the first one takes
precedence:

```````````````````````````````` example Link reference definitions: 11
[foo]

[foo]: first
[foo]: second
.
<p><a href="first">foo</a></p>
.
Document[0, 34]
  Paragraph[0, 6] isTrailingBlankLine
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
  Reference[7, 19] refOpen:[7, 8, "["] ref:[8, 11, "foo"] refClose:[11, 13, "]:"] url:[14, 19, "first"]
  Reference[20, 33] refOpen:[20, 21, "["] ref:[21, 24, "foo"] refClose:[24, 26, "]:"] url:[27, 33, "second"]
````````````````````````````````


As noted in the section on [Links], matching of labels is
case-insensitive (see [matches]).

```````````````````````````````` example Link reference definitions: 12
[FOO]: /url

[Foo]
.
<p><a href="/url">Foo</a></p>
.
Document[0, 19]
  Reference[0, 11] refOpen:[0, 1, "["] ref:[1, 4, "FOO"] refClose:[4, 6, "]:"] url:[7, 11, "/url"]
  Paragraph[13, 19]
    LinkRef[13, 18] referenceOpen:[13, 14, "["] reference:[14, 17, "Foo"] referenceClose:[17, 18, "]"]
      Text[14, 17] chars:[14, 17, "Foo"]
````````````````````````````````


```````````````````````````````` example Link reference definitions: 13
[ΑΓΩ]: /φου

[αγω]
.
<p><a href="/%CF%86%CE%BF%CF%85">αγω</a></p>
.
Document[0, 19]
  Reference[0, 11] refOpen:[0, 1, "["] ref:[1, 4, "ΑΓΩ"] refClose:[4, 6, "]:"] url:[7, 11, "/φου"]
  Paragraph[13, 19]
    LinkRef[13, 18] referenceOpen:[13, 14, "["] reference:[14, 17, "αγω"] referenceClose:[17, 18, "]"]
      Text[14, 17] chars:[14, 17, "αγω"]
````````````````````````````````


Here is a link reference definition with no corresponding link.
It contributes nothing to the document.

```````````````````````````````` example Link reference definitions: 14
[foo]: /url
.
.
Document[0, 12]
  Reference[0, 11] refOpen:[0, 1, "["] ref:[1, 4, "foo"] refClose:[4, 6, "]:"] url:[7, 11, "/url"]
````````````````````````````````


Here is another one:

```````````````````````````````` example Link reference definitions: 15
[
foo
]: /url
bar
.
<p>bar</p>
.
Document[0, 18]
  Reference[0, 13] refOpen:[0, 1, "["] ref:[2, 5, "foo"] refClose:[6, 8, "]:"] url:[9, 13, "/url"]
  Paragraph[14, 18]
    Text[14, 17] chars:[14, 17, "bar"]
````````````````````````````````


This is not a link reference definition, because there are
[non-whitespace characters] after the title:

```````````````````````````````` example Link reference definitions: 16
[foo]: /url "title" ok
.
<p>[foo]: /url &quot;title&quot; ok</p>
.
Document[0, 23]
  Paragraph[0, 23]
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
    Text[5, 22] chars:[5, 22, ": /ur … e\" ok"]
````````````````````````````````


This is a link reference definition, but it has no title:

```````````````````````````````` example Link reference definitions: 17
[foo]: /url
"title" ok
.
<p>&quot;title&quot; ok</p>
.
Document[0, 23]
  Reference[0, 11] refOpen:[0, 1, "["] ref:[1, 4, "foo"] refClose:[4, 6, "]:"] url:[7, 11, "/url"]
  Paragraph[12, 23]
    Text[12, 22] chars:[12, 22, "\"title\" ok"]
````````````````````````````````


This is not a link reference definition, because it is indented
four spaces:

```````````````````````````````` example Link reference definitions: 18
    [foo]: /url "title"

[foo]
.
<pre><code>[foo]: /url &quot;title&quot;
</code></pre>
<p>[foo]</p>
.
Document[0, 31]
  IndentedCodeBlock[4, 24]
  Paragraph[25, 31]
    LinkRef[25, 30] referenceOpen:[25, 26, "["] reference:[26, 29, "foo"] referenceClose:[29, 30, "]"]
      Text[26, 29] chars:[26, 29, "foo"]
````````````````````````````````


This is not a link reference definition, because it occurs inside
a code block:

```````````````````````````````` example Link reference definitions: 19
```
[foo]: /url
```

[foo]
.
<pre><code>[foo]: /url
</code></pre>
<p>[foo]</p>
.
Document[0, 27]
  FencedCodeBlock[0, 19] open:[0, 3, "```"] content:[4, 16] lines[1] close:[16, 19, "```"]
    Text[4, 16] chars:[4, 16, "[foo] … /url\n"]
  Paragraph[21, 27]
    LinkRef[21, 26] referenceOpen:[21, 22, "["] reference:[22, 25, "foo"] referenceClose:[25, 26, "]"]
      Text[22, 25] chars:[22, 25, "foo"]
````````````````````````````````


A [link reference definition] cannot interrupt a paragraph.

```````````````````````````````` example Link reference definitions: 20
Foo
[bar]: /baz

[bar]
.
<p>Foo
[bar]: /baz</p>
<p>[bar]</p>
.
Document[0, 23]
  Paragraph[0, 16] isTrailingBlankLine
    Text[0, 3] chars:[0, 3, "Foo"]
    SoftLineBreak[3, 4]
    LinkRef[4, 9] referenceOpen:[4, 5, "["] reference:[5, 8, "bar"] referenceClose:[8, 9, "]"]
      Text[5, 8] chars:[5, 8, "bar"]
    Text[9, 15] chars:[9, 15, ": /baz"]
  Paragraph[17, 23]
    LinkRef[17, 22] referenceOpen:[17, 18, "["] reference:[18, 21, "bar"] referenceClose:[21, 22, "]"]
      Text[18, 21] chars:[18, 21, "bar"]
````````````````````````````````


However, it can directly follow other block elements, such as headings
and thematic breaks, and it need not be followed by a blank line.

```````````````````````````````` example Link reference definitions: 21
# [Foo]
[foo]: /url
> bar
.
<h1><a href="/url">Foo</a></h1>
<blockquote>
<p>bar</p>
</blockquote>
.
Document[0, 26]
  Heading[0, 7] textOpen:[0, 1, "#"] text:[2, 7, "[Foo]"]
    LinkRef[2, 7] referenceOpen:[2, 3, "["] reference:[3, 6, "Foo"] referenceClose:[6, 7, "]"]
      Text[3, 6] chars:[3, 6, "Foo"]
  Reference[8, 19] refOpen:[8, 9, "["] ref:[9, 12, "foo"] refClose:[12, 14, "]:"] url:[15, 19, "/url"]
  BlockQuote[20, 26] marker:[20, 21, ">"]
    Paragraph[22, 26]
      Text[22, 25] chars:[22, 25, "bar"]
````````````````````````````````


Several [link reference definitions]
can occur one after another, without intervening blank lines.

```````````````````````````````` example Link reference definitions: 22
[foo]: /foo-url "foo"
[bar]: /bar-url
  "bar"
[baz]: /baz-url

[foo],
[bar],
[baz]
.
<p><a href="/foo-url" title="foo">foo</a>,
<a href="/bar-url" title="bar">bar</a>,
<a href="/baz-url">baz</a></p>
.
Document[0, 83]
  Reference[0, 21] refOpen:[0, 1, "["] ref:[1, 4, "foo"] refClose:[4, 6, "]:"] url:[7, 15, "/foo-url"] titleOpen:[16, 17, "\""] title:[17, 20, "foo"] titleClose:[20, 21, "\""]
  Reference[22, 45] refOpen:[22, 23, "["] ref:[23, 26, "bar"] refClose:[26, 28, "]:"] url:[29, 37, "/bar-url"] titleOpen:[40, 41, "\""] title:[41, 44, "bar"] titleClose:[44, 45, "\""]
  Reference[46, 61] refOpen:[46, 47, "["] ref:[47, 50, "baz"] refClose:[50, 52, "]:"] url:[53, 61, "/baz-url"]
  Paragraph[63, 83]
    LinkRef[63, 68] referenceOpen:[63, 64, "["] reference:[64, 67, "foo"] referenceClose:[67, 68, "]"]
      Text[64, 67] chars:[64, 67, "foo"]
    Text[68, 69] chars:[68, 69, ","]
    SoftLineBreak[69, 70]
    LinkRef[70, 75] referenceOpen:[70, 71, "["] reference:[71, 74, "bar"] referenceClose:[74, 75, "]"]
      Text[71, 74] chars:[71, 74, "bar"]
    Text[75, 76] chars:[75, 76, ","]
    SoftLineBreak[76, 77]
    LinkRef[77, 82] referenceOpen:[77, 78, "["] reference:[78, 81, "baz"] referenceClose:[81, 82, "]"]
      Text[78, 81] chars:[78, 81, "baz"]
````````````````````````````````


[Link reference definitions] can occur
inside block containers, like lists and block quotations.  They
affect the entire document, not just the container in which they
are defined:

```````````````````````````````` example Link reference definitions: 23
[foo]

> [foo]: /url
.
<p><a href="/url">foo</a></p>
<blockquote>
</blockquote>
.
Document[0, 21]
  Paragraph[0, 6] isTrailingBlankLine
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
  BlockQuote[7, 21] marker:[7, 8, ">"]
    Reference[9, 20] refOpen:[9, 10, "["] ref:[10, 13, "foo"] refClose:[13, 15, "]:"] url:[16, 20, "/url"]
````````````````````````````````



## Paragraphs

A sequence of non-blank lines that cannot be interpreted as other
kinds of blocks forms a [paragraph](@).
The contents of the paragraph are the result of parsing the
paragraph's raw content as inlines.  The paragraph's raw content
is formed by concatenating the lines and removing initial and final
[whitespace].

A simple example with two paragraphs:

```````````````````````````````` example Paragraphs: 1
aaa

bbb
.
<p>aaa</p>
<p>bbb</p>
.
Document[0, 9]
  Paragraph[0, 4] isTrailingBlankLine
    Text[0, 3] chars:[0, 3, "aaa"]
  Paragraph[5, 9]
    Text[5, 8] chars:[5, 8, "bbb"]
````````````````````````````````


Paragraphs can contain multiple lines, but no blank lines:

```````````````````````````````` example Paragraphs: 2
aaa
bbb

ccc
ddd
.
<p>aaa
bbb</p>
<p>ccc
ddd</p>
.
Document[0, 17]
  Paragraph[0, 8] isTrailingBlankLine
    Text[0, 3] chars:[0, 3, "aaa"]
    SoftLineBreak[3, 4]
    Text[4, 7] chars:[4, 7, "bbb"]
  Paragraph[9, 17]
    Text[9, 12] chars:[9, 12, "ccc"]
    SoftLineBreak[12, 13]
    Text[13, 16] chars:[13, 16, "ddd"]
````````````````````````````````


Multiple blank lines between paragraph have no effect:

```````````````````````````````` example Paragraphs: 3
aaa


bbb
.
<p>aaa</p>
<p>bbb</p>
.
Document[0, 10]
  Paragraph[0, 4] isTrailingBlankLine
    Text[0, 3] chars:[0, 3, "aaa"]
  Paragraph[6, 10]
    Text[6, 9] chars:[6, 9, "bbb"]
````````````````````````````````


Leading spaces are skipped:

```````````````````````````````` example Paragraphs: 4
  aaa
 bbb
.
<p>aaa
bbb</p>
.
Document[0, 11]
  Paragraph[2, 11]
    Text[2, 5] chars:[2, 5, "aaa"]
    SoftLineBreak[5, 6]
    Text[7, 10] chars:[7, 10, "bbb"]
````````````````````````````````


Lines after the first may be indented any amount, since indented
code blocks cannot interrupt paragraphs.

```````````````````````````````` example Paragraphs: 5
aaa
             bbb
                                       ccc
.
<p>aaa
bbb
ccc</p>
.
Document[0, 64]
  Paragraph[0, 64]
    Text[0, 3] chars:[0, 3, "aaa"]
    SoftLineBreak[3, 4]
    Text[17, 20] chars:[17, 20, "bbb"]
    SoftLineBreak[20, 21]
    Text[60, 63] chars:[60, 63, "ccc"]
````````````````````````````````


However, the first line may be indented at most three spaces,
or an indented code block will be triggered:

```````````````````````````````` example Paragraphs: 6
   aaa
bbb
.
<p>aaa
bbb</p>
.
Document[0, 11]
  Paragraph[3, 11]
    Text[3, 6] chars:[3, 6, "aaa"]
    SoftLineBreak[6, 7]
    Text[7, 10] chars:[7, 10, "bbb"]
````````````````````````````````


```````````````````````````````` example Paragraphs: 7
    aaa
bbb
.
<pre><code>aaa
</code></pre>
<p>bbb</p>
.
Document[0, 12]
  IndentedCodeBlock[4, 8]
  Paragraph[8, 12]
    Text[8, 11] chars:[8, 11, "bbb"]
````````````````````````````````


Final spaces are stripped before inline parsing, so a paragraph
that ends with two or more spaces will not end with a [hard line
break]:

```````````````````````````````` example Paragraphs: 8
aaa     
bbb     
.
<p>aaa<br />
bbb</p>
.
Document[0, 18]
  Paragraph[0, 18]
    Text[0, 3] chars:[0, 3, "aaa"]
    HardLineBreak[3, 9]
    Text[9, 12] chars:[9, 12, "bbb"]
````````````````````````````````


## Blank lines

[Blank lines] between block-level elements are ignored,
except for the role they play in determining whether a [list]
is [tight] or [loose].

Blank lines at the beginning and end of the document are also ignored.

```````````````````````````````` example Blank lines: 1
  

aaa
  

# aaa

  
.
<p>aaa</p>
<h1>aaa</h1>
.
Document[0, 22]
  Paragraph[4, 8] isTrailingBlankLine
    Text[4, 7] chars:[4, 7, "aaa"]
  Heading[12, 17] textOpen:[12, 13, "#"] text:[14, 17, "aaa"]
    Text[14, 17] chars:[14, 17, "aaa"]
````````````````````````````````



# Container blocks

A [container block] is a block that has other
blocks as its contents.  There are two basic kinds of container blocks:
[block quotes] and [list items].
[Lists] are meta-containers for [list items].

We define the syntax for container blocks recursively.  The general
form of the definition is:

> If X is a sequence of blocks, then the result of
> transforming X in such-and-such a way is a container of type Y
> with these blocks as its content.

So, we explain what counts as a block quote or list item by explaining
how these can be *generated* from their contents. This should suffice
to define the syntax, although it does not give a recipe for *parsing*
these constructions.  (A recipe is provided below in the section entitled
[A parsing strategy](#appendix-a-parsing-strategy).)

## Block quotes

A [block quote marker](@)
consists of 0-3 spaces of initial indent, plus (a) the character `>` together
with a following space, or (b) a single character `>` not followed by a space.

The following rules define [block quotes]:

1.  **Basic case.**  If a string of lines *Ls* constitute a sequence
    of blocks *Bs*, then the result of prepending a [block quote
    marker] to the beginning of each line in *Ls*
    is a [block quote](#block-quotes) containing *Bs*.

2.  **Laziness.**  If a string of lines *Ls* constitute a [block
    quote](#block-quotes) with contents *Bs*, then the result of deleting
    the initial [block quote marker] from one or
    more lines in which the next [non-whitespace character] after the [block
    quote marker] is [paragraph continuation
    text] is a block quote with *Bs* as its content.
    [Paragraph continuation text](@) is text
    that will be parsed as part of the content of a paragraph, but does
    not occur at the beginning of the paragraph.

3.  **Consecutiveness.**  A document cannot contain two [block
    quotes] in a row unless there is a [blank line] between them.

Nothing else counts as a [block quote](#block-quotes).

Here is a simple example:

```````````````````````````````` example Block quotes: 1
> # Foo
> bar
> baz
.
<blockquote>
<h1>Foo</h1>
<p>bar
baz</p>
</blockquote>
.
Document[0, 20]
  BlockQuote[0, 20] marker:[0, 1, ">"]
    Heading[2, 7] textOpen:[2, 3, "#"] text:[4, 7, "Foo"]
      Text[4, 7] chars:[4, 7, "Foo"]
    Paragraph[10, 20]
      Text[10, 13] chars:[10, 13, "bar"]
      SoftLineBreak[13, 14]
      Text[16, 19] chars:[16, 19, "baz"]
````````````````````````````````


The spaces after the `>` characters can be omitted:

```````````````````````````````` example Block quotes: 2
># Foo
>bar
> baz
.
<blockquote>
<h1>Foo</h1>
<p>bar
baz</p>
</blockquote>
.
Document[0, 18]
  BlockQuote[0, 18] marker:[0, 1, ">"]
    Heading[1, 6] textOpen:[1, 2, "#"] text:[3, 6, "Foo"]
      Text[3, 6] chars:[3, 6, "Foo"]
    Paragraph[8, 18]
      Text[8, 11] chars:[8, 11, "bar"]
      SoftLineBreak[11, 12]
      Text[14, 17] chars:[14, 17, "baz"]
````````````````````````````````


The `>` characters can be indented 1-3 spaces:

```````````````````````````````` example Block quotes: 3
   > # Foo
   > bar
 > baz
.
<blockquote>
<h1>Foo</h1>
<p>bar
baz</p>
</blockquote>
.
Document[0, 27]
  BlockQuote[3, 27] marker:[3, 4, ">"]
    Heading[5, 10] textOpen:[5, 6, "#"] text:[7, 10, "Foo"]
      Text[7, 10] chars:[7, 10, "Foo"]
    Paragraph[16, 27]
      Text[16, 19] chars:[16, 19, "bar"]
      SoftLineBreak[19, 20]
      Text[23, 26] chars:[23, 26, "baz"]
````````````````````````````````


Four spaces gives us a code block:

```````````````````````````````` example Block quotes: 4
    > # Foo
    > bar
    > baz
.
<pre><code>&gt; # Foo
&gt; bar
&gt; baz
</code></pre>
.
Document[0, 32]
  IndentedCodeBlock[4, 32]
````````````````````````````````


The Laziness clause allows us to omit the `>` before
[paragraph continuation text]:

```````````````````````````````` example Block quotes: 5
> # Foo
> bar
baz
.
<blockquote>
<h1>Foo</h1>
<p>bar
baz</p>
</blockquote>
.
Document[0, 18]
  BlockQuote[0, 18] marker:[0, 1, ">"]
    Heading[2, 7] textOpen:[2, 3, "#"] text:[4, 7, "Foo"]
      Text[4, 7] chars:[4, 7, "Foo"]
    Paragraph[10, 18]
      Text[10, 13] chars:[10, 13, "bar"]
      SoftLineBreak[13, 14]
      Text[14, 17] chars:[14, 17, "baz"]
````````````````````````````````


A block quote can contain some lazy and some non-lazy
continuation lines:

```````````````````````````````` example Block quotes: 6
> bar
baz
> foo
.
<blockquote>
<p>bar
baz
foo</p>
</blockquote>
.
Document[0, 16]
  BlockQuote[0, 16] marker:[0, 1, ">"]
    Paragraph[2, 16]
      Text[2, 5] chars:[2, 5, "bar"]
      SoftLineBreak[5, 6]
      Text[6, 9] chars:[6, 9, "baz"]
      SoftLineBreak[9, 10]
      Text[12, 15] chars:[12, 15, "foo"]
````````````````````````````````


Laziness only applies to lines that would have been continuations of
paragraphs had they been prepended with [block quote markers].
For example, the `> ` cannot be omitted in the second line of

``` markdown
> foo
> ---
```

without changing the meaning:

```````````````````````````````` example Block quotes: 7
> foo
---
.
<blockquote>
<p>foo</p>
</blockquote>
<hr />
.
Document[0, 10]
  BlockQuote[0, 6] marker:[0, 1, ">"]
    Paragraph[2, 6]
      Text[2, 5] chars:[2, 5, "foo"]
  ThematicBreak[6, 9]
````````````````````````````````


Similarly, if we omit the `> ` in the second line of

``` markdown
> - foo
> - bar
```

then the block quote ends after the first line:

```````````````````````````````` example Block quotes: 8
> - foo
- bar
.
<blockquote>
<ul>
<li>foo</li>
</ul>
</blockquote>
<ul>
<li>bar</li>
</ul>
.
Document[0, 14]
  BlockQuote[0, 8] marker:[0, 1, ">"]
    BulletList[2, 8] isTight
      BulletListItem[2, 8] open:[2, 3, "-"] isTight
        Paragraph[4, 8]
          Text[4, 7] chars:[4, 7, "foo"]
  BulletList[8, 14] isTight
    BulletListItem[8, 14] open:[8, 9, "-"] isTight
      Paragraph[10, 14]
        Text[10, 13] chars:[10, 13, "bar"]
````````````````````````````````


For the same reason, we can't omit the `> ` in front of
subsequent lines of an indented or fenced code block:

```````````````````````````````` example Block quotes: 9
>     foo
    bar
.
<blockquote>
<pre><code>foo
</code></pre>
</blockquote>
<pre><code>bar
</code></pre>
.
Document[0, 18]
  BlockQuote[0, 10] marker:[0, 1, ">"]
    IndentedCodeBlock[6, 10]
  IndentedCodeBlock[14, 18]
````````````````````````````````


```````````````````````````````` example Block quotes: 10
> ```
foo
```
.
<blockquote>
<pre><code></code></pre>
</blockquote>
<p>foo</p>
<pre><code></code></pre>
.
Document[0, 14]
  BlockQuote[0, 6] marker:[0, 1, ">"]
    FencedCodeBlock[2, 6] open:[2, 5, "```"] lines[0]
  Paragraph[6, 10]
    Text[6, 9] chars:[6, 9, "foo"]
  FencedCodeBlock[10, 14] open:[10, 13, "```"] lines[0]
````````````````````````````````


Note that in the following case, we have a [lazy
continuation line]:

```````````````````````````````` example Block quotes: 11
> foo
    - bar
.
<blockquote>
<p>foo
- bar</p>
</blockquote>
.
Document[0, 16]
  BlockQuote[0, 16] marker:[0, 1, ">"]
    Paragraph[2, 16]
      Text[2, 5] chars:[2, 5, "foo"]
      SoftLineBreak[5, 6]
      Text[10, 15] chars:[10, 15, "- bar"]
````````````````````````````````


To see why, note that in

``` markdown
> foo
>     - bar
```

the `- bar` is indented too far to start a list, and can't
be an indented code block because indented code blocks cannot
interrupt paragraphs, so it is [paragraph continuation text].

A block quote can be empty:

```````````````````````````````` example Block quotes: 12
>
.
<blockquote>
</blockquote>
.
Document[0, 2]
  BlockQuote[0, 2] marker:[0, 1, ">"]
````````````````````````````````


```````````````````````````````` example Block quotes: 13
>
>  
> 
.
<blockquote>
</blockquote>
.
Document[0, 9]
  BlockQuote[0, 9] marker:[0, 1, ">"]
````````````````````````````````


A block quote can have initial or final blank lines:

```````````````````````````````` example Block quotes: 14
>
> foo
>  
.
<blockquote>
<p>foo</p>
</blockquote>
.
Document[0, 12]
  BlockQuote[0, 12] marker:[0, 1, ">"]
    Paragraph[4, 8] isTrailingBlankLine
      Text[4, 7] chars:[4, 7, "foo"]
````````````````````````````````


A blank line always separates block quotes:

```````````````````````````````` example Block quotes: 15
> foo

> bar
.
<blockquote>
<p>foo</p>
</blockquote>
<blockquote>
<p>bar</p>
</blockquote>
.
Document[0, 13]
  BlockQuote[0, 6] marker:[0, 1, ">"]
    Paragraph[2, 6]
      Text[2, 5] chars:[2, 5, "foo"]
  BlockQuote[7, 13] marker:[7, 8, ">"]
    Paragraph[9, 13]
      Text[9, 12] chars:[9, 12, "bar"]
````````````````````````````````


(Most current Markdown implementations, including John Gruber's
original `Markdown.pl`, will parse this example as a single block quote
with two paragraphs.  But it seems better to allow the author to decide
whether two block quotes or one are wanted.)

Consecutiveness means that if we put these block quotes together,
we get a single block quote:

```````````````````````````````` example Block quotes: 16
> foo
> bar
.
<blockquote>
<p>foo
bar</p>
</blockquote>
.
Document[0, 12]
  BlockQuote[0, 12] marker:[0, 1, ">"]
    Paragraph[2, 12]
      Text[2, 5] chars:[2, 5, "foo"]
      SoftLineBreak[5, 6]
      Text[8, 11] chars:[8, 11, "bar"]
````````````````````````````````


To get a block quote with two paragraphs, use:

```````````````````````````````` example Block quotes: 17
> foo
>
> bar
.
<blockquote>
<p>foo</p>
<p>bar</p>
</blockquote>
.
Document[0, 14]
  BlockQuote[0, 14] marker:[0, 1, ">"]
    Paragraph[2, 6] isTrailingBlankLine
      Text[2, 5] chars:[2, 5, "foo"]
    Paragraph[10, 14]
      Text[10, 13] chars:[10, 13, "bar"]
````````````````````````````````


Block quotes can interrupt paragraphs:

```````````````````````````````` example Block quotes: 18
foo
> bar
.
<p>foo</p>
<blockquote>
<p>bar</p>
</blockquote>
.
Document[0, 10]
  Paragraph[0, 4]
    Text[0, 3] chars:[0, 3, "foo"]
  BlockQuote[4, 10] marker:[4, 5, ">"]
    Paragraph[6, 10]
      Text[6, 9] chars:[6, 9, "bar"]
````````````````````````````````


In general, blank lines are not needed before or after block
quotes:

```````````````````````````````` example Block quotes: 19
> aaa
***
> bbb
.
<blockquote>
<p>aaa</p>
</blockquote>
<hr />
<blockquote>
<p>bbb</p>
</blockquote>
.
Document[0, 16]
  BlockQuote[0, 6] marker:[0, 1, ">"]
    Paragraph[2, 6]
      Text[2, 5] chars:[2, 5, "aaa"]
  ThematicBreak[6, 9]
  BlockQuote[10, 16] marker:[10, 11, ">"]
    Paragraph[12, 16]
      Text[12, 15] chars:[12, 15, "bbb"]
````````````````````````````````


However, because of laziness, a blank line is needed between
a block quote and a following paragraph:

```````````````````````````````` example Block quotes: 20
> bar
baz
.
<blockquote>
<p>bar
baz</p>
</blockquote>
.
Document[0, 10]
  BlockQuote[0, 10] marker:[0, 1, ">"]
    Paragraph[2, 10]
      Text[2, 5] chars:[2, 5, "bar"]
      SoftLineBreak[5, 6]
      Text[6, 9] chars:[6, 9, "baz"]
````````````````````````````````


```````````````````````````````` example Block quotes: 21
> bar

baz
.
<blockquote>
<p>bar</p>
</blockquote>
<p>baz</p>
.
Document[0, 11]
  BlockQuote[0, 6] marker:[0, 1, ">"]
    Paragraph[2, 6]
      Text[2, 5] chars:[2, 5, "bar"]
  Paragraph[7, 11]
    Text[7, 10] chars:[7, 10, "baz"]
````````````````````````````````


```````````````````````````````` example Block quotes: 22
> bar
>
baz
.
<blockquote>
<p>bar</p>
</blockquote>
<p>baz</p>
.
Document[0, 12]
  BlockQuote[0, 8] marker:[0, 1, ">"]
    Paragraph[2, 6] isTrailingBlankLine
      Text[2, 5] chars:[2, 5, "bar"]
  Paragraph[8, 12]
    Text[8, 11] chars:[8, 11, "baz"]
````````````````````````````````


It is a consequence of the Laziness rule that any number
of initial `>`s may be omitted on a continuation line of a
nested block quote:

```````````````````````````````` example Block quotes: 23
> > > foo
bar
.
<blockquote>
<blockquote>
<blockquote>
<p>foo
bar</p>
</blockquote>
</blockquote>
</blockquote>
.
Document[0, 14]
  BlockQuote[0, 14] marker:[0, 1, ">"]
    BlockQuote[2, 14] marker:[2, 3, ">"]
      BlockQuote[4, 14] marker:[4, 5, ">"]
        Paragraph[6, 14]
          Text[6, 9] chars:[6, 9, "foo"]
          SoftLineBreak[9, 10]
          Text[10, 13] chars:[10, 13, "bar"]
````````````````````````````````


```````````````````````````````` example Block quotes: 24
>>> foo
> bar
>>baz
.
<blockquote>
<blockquote>
<blockquote>
<p>foo
bar
baz</p>
</blockquote>
</blockquote>
</blockquote>
.
Document[0, 20]
  BlockQuote[0, 20] marker:[0, 1, ">"]
    BlockQuote[1, 20] marker:[1, 2, ">"]
      BlockQuote[2, 20] marker:[2, 3, ">"]
        Paragraph[4, 20]
          Text[4, 7] chars:[4, 7, "foo"]
          SoftLineBreak[7, 8]
          Text[10, 13] chars:[10, 13, "bar"]
          SoftLineBreak[13, 14]
          Text[16, 19] chars:[16, 19, "baz"]
````````````````````````````````


When including an indented code block in a block quote,
remember that the [block quote marker] includes
both the `>` and a following space.  So *five spaces* are needed after
the `>`:

```````````````````````````````` example Block quotes: 25
>     code

>    not code
.
<blockquote>
<pre><code>code
</code></pre>
</blockquote>
<blockquote>
<p>not code</p>
</blockquote>
.
Document[0, 26]
  BlockQuote[0, 11] marker:[0, 1, ">"]
    IndentedCodeBlock[6, 11]
  BlockQuote[12, 26] marker:[12, 13, ">"]
    Paragraph[17, 26]
      Text[17, 25] chars:[17, 25, "not code"]
````````````````````````````````



## List items

A [list marker](@) is a
[bullet list marker] or an [ordered list marker].

A [bullet list marker](@)
is a `-`, `+`, or `*` character.

An [ordered list marker](@)
is a sequence of 1--9 arabic digits (`0-9`), followed by either a
`.` character or a `)` character.  (The reason for the length
limit is that with 10 digits we start seeing integer overflows
in some browsers.)

The following rules define [list items]:

1.  **Basic case.**  If a sequence of lines *Ls* constitute a sequence of
    blocks *Bs* starting with a [non-whitespace character] and not separated
    from each other by more than one blank line, and *M* is a list
    marker of width *W* followed by 1 ≤ *N* ≤ 4 spaces, then the result
    of prepending *M* and the following spaces to the first line of
    *Ls*, and indenting subsequent lines of *Ls* by *W + N* spaces, is a
    list item with *Bs* as its contents.  The type of the list item
    (bullet or ordered) is determined by the type of its list marker.
    If the list item is ordered, then it is also assigned a start
    number, based on the ordered list marker.

    Exceptions:

    1. When the first list item in a [list] interrupts
       a paragraph---that is, when it starts on a line that would
       otherwise count as [paragraph continuation text]---then (a)
       the lines *Ls* must not begin with a blank line, and (b) if
       the list item is ordered, the start number must be 1.
    2. If any line is a [thematic break][thematic breaks] then
       that line is not a list item.

For example, let *Ls* be the lines

```````````````````````````````` example List items: 1
A paragraph
with two lines.

    indented code

> A block quote.
.
<p>A paragraph
with two lines.</p>
<pre><code>indented code
</code></pre>
<blockquote>
<p>A block quote.</p>
</blockquote>
.
Document[0, 65]
  Paragraph[0, 28] isTrailingBlankLine
    Text[0, 11] chars:[0, 11, "A par … graph"]
    SoftLineBreak[11, 12]
    Text[12, 27] chars:[12, 27, "with  … ines."]
  IndentedCodeBlock[33, 47]
  BlockQuote[48, 65] marker:[48, 49, ">"]
    Paragraph[50, 65]
      Text[50, 64] chars:[50, 64, "A blo … uote."]
````````````````````````````````


And let *M* be the marker `1.`, and *N* = 2.  Then rule #1 says
that the following is an ordered list item with start number 1,
and the same contents as *Ls*:

```````````````````````````````` example List items: 2
1.  A paragraph
    with two lines.

        indented code

    > A block quote.
.
<ol>
<li>
<p>A paragraph
with two lines.</p>
<pre><code>indented code
</code></pre>
<blockquote>
<p>A block quote.</p>
</blockquote>
</li>
</ol>
.
Document[0, 81]
  OrderedList[0, 81] isLoose delimiter:'.'
    OrderedListItem[0, 81] open:[0, 2, "1."] isLoose hadBlankLineAfter
      Paragraph[4, 36] isTrailingBlankLine
        Text[4, 15] chars:[4, 15, "A par … graph"]
        SoftLineBreak[15, 16]
        Text[20, 35] chars:[20, 35, "with  … ines."]
      IndentedCodeBlock[45, 59]
      BlockQuote[64, 81] marker:[64, 65, ">"]
        Paragraph[66, 81]
          Text[66, 80] chars:[66, 80, "A blo … uote."]
````````````````````````````````


The most important thing to notice is that the position of
the text after the list marker determines how much indentation
is needed in subsequent blocks in the list item.  If the list
marker takes up two spaces, and there are three spaces between
the list marker and the next [non-whitespace character], then blocks
must be indented five spaces in order to fall under the list
item.

Here are some examples showing how far content must be indented to be
put under the list item:

```````````````````````````````` example List items: 3
- one

 two
.
<ul>
<li>one</li>
</ul>
<p>two</p>
.
Document[0, 12]
  BulletList[0, 6] isTight
    BulletListItem[0, 6] open:[0, 1, "-"] isTight hadBlankLineAfter
      Paragraph[2, 6] isTrailingBlankLine
        Text[2, 5] chars:[2, 5, "one"]
  Paragraph[8, 12]
    Text[8, 11] chars:[8, 11, "two"]
````````````````````````````````


```````````````````````````````` example List items: 4
- one

  two
.
<ul>
<li>
<p>one</p>
<p>two</p>
</li>
</ul>
.
Document[0, 13]
  BulletList[0, 13] isLoose
    BulletListItem[0, 13] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[2, 6] isTrailingBlankLine
        Text[2, 5] chars:[2, 5, "one"]
      Paragraph[9, 13]
        Text[9, 12] chars:[9, 12, "two"]
````````````````````````````````


```````````````````````````````` example List items: 5
 -    one

     two
.
<ul>
<li>one</li>
</ul>
<pre><code> two
</code></pre>
.
Document[0, 20]
  BulletList[1, 10] isTight
    BulletListItem[1, 10] open:[1, 2, "-"] isTight hadBlankLineAfter
      Paragraph[6, 10] isTrailingBlankLine
        Text[6, 9] chars:[6, 9, "one"]
  IndentedCodeBlock[15, 20]
````````````````````````````````


```````````````````````````````` example List items: 6
 -    one

      two
.
<ul>
<li>
<p>one</p>
<p>two</p>
</li>
</ul>
.
Document[0, 21]
  BulletList[1, 21] isLoose
    BulletListItem[1, 21] open:[1, 2, "-"] isLoose hadBlankLineAfter
      Paragraph[6, 10] isTrailingBlankLine
        Text[6, 9] chars:[6, 9, "one"]
      Paragraph[17, 21]
        Text[17, 20] chars:[17, 20, "two"]
````````````````````````````````


It is tempting to think of this in terms of columns:  the continuation
blocks must be indented at least to the column of the first
[non-whitespace character] after the list marker. However, that is not quite right.
The spaces after the list marker determine how much relative indentation
is needed.  Which column this indentation reaches will depend on
how the list item is embedded in other constructions, as shown by
this example:

```````````````````````````````` example List items: 7
   > > 1.  one
>>
>>     two
.
<blockquote>
<blockquote>
<ol>
<li>
<p>one</p>
<p>two</p>
</li>
</ol>
</blockquote>
</blockquote>
.
Document[0, 29]
  BlockQuote[3, 29] marker:[3, 4, ">"]
    BlockQuote[5, 29] marker:[5, 6, ">"]
      OrderedList[7, 29] isLoose delimiter:'.'
        OrderedListItem[7, 29] open:[7, 9, "1."] isLoose hadBlankLineAfter
          Paragraph[11, 15] isTrailingBlankLine
            Text[11, 14] chars:[11, 14, "one"]
          Paragraph[25, 29]
            Text[25, 28] chars:[25, 28, "two"]
````````````````````````````````


Here `two` occurs in the same column as the list marker `1.`,
but is actually contained in the list item, because there is
sufficient indentation after the last containing blockquote marker.

The converse is also possible.  In the following example, the word `two`
occurs far to the right of the initial text of the list item, `one`, but
it is not considered part of the list item, because it is not indented
far enough past the blockquote marker:

```````````````````````````````` example List items: 8
>>- one
>>
  >  > two
.
<blockquote>
<blockquote>
<ul>
<li>one</li>
</ul>
<p>two</p>
</blockquote>
</blockquote>
.
Document[0, 22]
  BlockQuote[0, 22] marker:[0, 1, ">"]
    BlockQuote[1, 22] marker:[1, 2, ">"]
      BulletList[2, 8] isTight
        BulletListItem[2, 8] open:[2, 3, "-"] isTight hadBlankLineAfter
          Paragraph[4, 8] isTrailingBlankLine
            Text[4, 7] chars:[4, 7, "one"]
      Paragraph[18, 22]
        Text[18, 21] chars:[18, 21, "two"]
````````````````````````````````


Note that at least one space is needed between the list marker and
any following content, so these are not list items:

```````````````````````````````` example List items: 9
-one

2.two
.
<p>-one</p>
<p>2.two</p>
.
Document[0, 12]
  Paragraph[0, 5] isTrailingBlankLine
    Text[0, 4] chars:[0, 4, "-one"]
  Paragraph[6, 12]
    Text[6, 11] chars:[6, 11, "2.two"]
````````````````````````````````


A list item may contain blocks that are separated by more than
one blank line.

```````````````````````````````` example List items: 10
- foo


  bar
.
<ul>
<li>
<p>foo</p>
<p>bar</p>
</li>
</ul>
.
Document[0, 14]
  BulletList[0, 14] isLoose
    BulletListItem[0, 14] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[2, 6] isTrailingBlankLine
        Text[2, 5] chars:[2, 5, "foo"]
      Paragraph[10, 14]
        Text[10, 13] chars:[10, 13, "bar"]
````````````````````````````````


A list item may contain any kind of block:

```````````````````````````````` example List items: 11
1.  foo

    ```
    bar
    ```

    baz

    > bam
.
<ol>
<li>
<p>foo</p>
<pre><code>bar
</code></pre>
<p>baz</p>
<blockquote>
<p>bam</p>
</blockquote>
</li>
</ol>
.
Document[0, 53]
  OrderedList[0, 53] isLoose delimiter:'.'
    OrderedListItem[0, 53] open:[0, 2, "1."] isLoose hadBlankLineAfter
      Paragraph[4, 8] isTrailingBlankLine
        Text[4, 7] chars:[4, 7, "foo"]
      FencedCodeBlock[13, 32] open:[13, 16, "```"] content:[21, 25] lines[1] close:[29, 32, "```"]
        Text[21, 25] chars:[21, 25, "bar\n"]
      Paragraph[38, 42] isTrailingBlankLine
        Text[38, 41] chars:[38, 41, "baz"]
      BlockQuote[47, 53] marker:[47, 48, ">"]
        Paragraph[49, 53]
          Text[49, 52] chars:[49, 52, "bam"]
````````````````````````````````


A list item that contains an indented code block will preserve
empty lines within the code block verbatim.

```````````````````````````````` example List items: 12
- Foo

      bar


      baz
.
<ul>
<li>
<p>Foo</p>
<pre><code>bar


baz
</code></pre>
</li>
</ul>
.
Document[0, 29]
  BulletList[0, 29] isLoose
    BulletListItem[0, 29] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[2, 6] isTrailingBlankLine
        Text[2, 5] chars:[2, 5, "Foo"]
      IndentedCodeBlock[13, 29]
````````````````````````````````


Note that ordered list start numbers must be nine digits or less:

```````````````````````````````` example List items: 13
123456789. ok
.
<ol start="123456789">
<li>ok</li>
</ol>
.
Document[0, 14]
  OrderedList[0, 14] isTight start:123456789 delimiter:'.'
    OrderedListItem[0, 14] open:[0, 10, "123456789."] isTight
      Paragraph[11, 14]
        Text[11, 13] chars:[11, 13, "ok"]
````````````````````````````````


```````````````````````````````` example List items: 14
1234567890. not ok
.
<p>1234567890. not ok</p>
.
Document[0, 19]
  Paragraph[0, 19]
    Text[0, 18] chars:[0, 18, "12345 … ot ok"]
````````````````````````````````


A start number may begin with 0s:

```````````````````````````````` example List items: 15
0. ok
.
<ol start="0">
<li>ok</li>
</ol>
.
Document[0, 6]
  OrderedList[0, 6] isTight delimiter:'.'
    OrderedListItem[0, 6] open:[0, 2, "0."] isTight
      Paragraph[3, 6]
        Text[3, 5] chars:[3, 5, "ok"]
````````````````````````````````


```````````````````````````````` example List items: 16
003. ok
.
<ol start="3">
<li>ok</li>
</ol>
.
Document[0, 8]
  OrderedList[0, 8] isTight start:3 delimiter:'.'
    OrderedListItem[0, 8] open:[0, 4, "003."] isTight
      Paragraph[5, 8]
        Text[5, 7] chars:[5, 7, "ok"]
````````````````````````````````


A start number may not be negative:

```````````````````````````````` example List items: 17
-1. not ok
.
<p>-1. not ok</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 10] chars:[0, 10, "-1. not ok"]
````````````````````````````````



2.  **Item starting with indented code.**  If a sequence of lines *Ls*
    constitute a sequence of blocks *Bs* starting with an indented code
    block and not separated from each other by more than one blank line,
    and *M* is a list marker of width *W* followed by
    one space, then the result of prepending *M* and the following
    space to the first line of *Ls*, and indenting subsequent lines of
    *Ls* by *W + 1* spaces, is a list item with *Bs* as its contents.
    If a line is empty, then it need not be indented.  The type of the
    list item (bullet or ordered) is determined by the type of its list
    marker.  If the list item is ordered, then it is also assigned a
    start number, based on the ordered list marker.

An indented code block will have to be indented four spaces beyond
the edge of the region where text will be included in the list item.
In the following case that is 6 spaces:

```````````````````````````````` example List items: 18
- foo

      bar
.
<ul>
<li>
<p>foo</p>
<pre><code>bar
</code></pre>
</li>
</ul>
.
Document[0, 17]
  BulletList[0, 17] isLoose
    BulletListItem[0, 17] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[2, 6] isTrailingBlankLine
        Text[2, 5] chars:[2, 5, "foo"]
      IndentedCodeBlock[13, 17]
````````````````````````````````


And in this case it is 11 spaces:

```````````````````````````````` example List items: 19
  10.  foo

           bar
.
<ol start="10">
<li>
<p>foo</p>
<pre><code>bar
</code></pre>
</li>
</ol>
.
Document[0, 27]
  OrderedList[2, 27] isLoose start:10 delimiter:'.'
    OrderedListItem[2, 27] open:[2, 5, "10."] isLoose hadBlankLineAfter
      Paragraph[7, 11] isTrailingBlankLine
        Text[7, 10] chars:[7, 10, "foo"]
      IndentedCodeBlock[23, 27]
````````````````````````````````


If the *first* block in the list item is an indented code block,
then by rule #2, the contents must be indented *one* space after the
list marker:

```````````````````````````````` example List items: 20
    indented code

paragraph

    more code
.
<pre><code>indented code
</code></pre>
<p>paragraph</p>
<pre><code>more code
</code></pre>
.
Document[0, 44]
  IndentedCodeBlock[4, 18]
  Paragraph[19, 29] isTrailingBlankLine
    Text[19, 28] chars:[19, 28, "paragraph"]
  IndentedCodeBlock[34, 44]
````````````````````````````````


```````````````````````````````` example List items: 21
1.     indented code

   paragraph

       more code
.
<ol>
<li>
<pre><code>indented code
</code></pre>
<p>paragraph</p>
<pre><code>more code
</code></pre>
</li>
</ol>
.
Document[0, 53]
  OrderedList[0, 53] isLoose delimiter:'.'
    OrderedListItem[0, 53] open:[0, 2, "1."] isLoose hadBlankLineAfter
      IndentedCodeBlock[7, 21]
      Paragraph[25, 35] isTrailingBlankLine
        Text[25, 34] chars:[25, 34, "paragraph"]
      IndentedCodeBlock[43, 53]
````````````````````````````````


Note that an additional space indent is interpreted as space
inside the code block:

```````````````````````````````` example List items: 22
1.      indented code

   paragraph

       more code
.
<ol>
<li>
<pre><code> indented code
</code></pre>
<p>paragraph</p>
<pre><code>more code
</code></pre>
</li>
</ol>
.
Document[0, 54]
  OrderedList[0, 54] isLoose delimiter:'.'
    OrderedListItem[0, 54] open:[0, 2, "1."] isLoose hadBlankLineAfter
      IndentedCodeBlock[7, 22]
      Paragraph[26, 36] isTrailingBlankLine
        Text[26, 35] chars:[26, 35, "paragraph"]
      IndentedCodeBlock[44, 54]
````````````````````````````````


Note that rules #1 and #2 only apply to two cases:  (a) cases
in which the lines to be included in a list item begin with a
[non-whitespace character], and (b) cases in which
they begin with an indented code
block.  In a case like the following, where the first block begins with
a three-space indent, the rules do not allow us to form a list item by
indenting the whole thing and prepending a list marker:

```````````````````````````````` example List items: 23
   foo

bar
.
<p>foo</p>
<p>bar</p>
.
Document[0, 12]
  Paragraph[3, 7] isTrailingBlankLine
    Text[3, 6] chars:[3, 6, "foo"]
  Paragraph[8, 12]
    Text[8, 11] chars:[8, 11, "bar"]
````````````````````````````````


```````````````````````````````` example List items: 24
-    foo

  bar
.
<ul>
<li>foo</li>
</ul>
<p>bar</p>
.
Document[0, 16]
  BulletList[0, 9] isTight
    BulletListItem[0, 9] open:[0, 1, "-"] isTight hadBlankLineAfter
      Paragraph[5, 9] isTrailingBlankLine
        Text[5, 8] chars:[5, 8, "foo"]
  Paragraph[12, 16]
    Text[12, 15] chars:[12, 15, "bar"]
````````````````````````````````


This is not a significant restriction, because when a block begins
with 1-3 spaces indent, the indentation can always be removed without
a change in interpretation, allowing rule #1 to be applied.  So, in
the above case:

```````````````````````````````` example List items: 25
-  foo

   bar
.
<ul>
<li>
<p>foo</p>
<p>bar</p>
</li>
</ul>
.
Document[0, 15]
  BulletList[0, 15] isLoose
    BulletListItem[0, 15] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[3, 7] isTrailingBlankLine
        Text[3, 6] chars:[3, 6, "foo"]
      Paragraph[11, 15]
        Text[11, 14] chars:[11, 14, "bar"]
````````````````````````````````


3.  **Item starting with a blank line.**  If a sequence of lines *Ls*
    starting with a single [blank line] constitute a (possibly empty)
    sequence of blocks *Bs*, not separated from each other by more than
    one blank line, and *M* is a list marker of width *W*,
    then the result of prepending *M* to the first line of *Ls*, and
    indenting subsequent lines of *Ls* by *W + 1* spaces, is a list
    item with *Bs* as its contents.
    If a line is empty, then it need not be indented.  The type of the
    list item (bullet or ordered) is determined by the type of its list
    marker.  If the list item is ordered, then it is also assigned a
    start number, based on the ordered list marker.

Here are some list items that start with a blank line but are not empty:

```````````````````````````````` example List items: 26
-
  foo
-
  ```
  bar
  ```
-
      baz
.
<ul>
<li>foo</li>
<li>
<pre><code>bar
</code></pre>
</li>
<li>
<pre><code>baz
</code></pre>
</li>
</ul>
.
Document[0, 40]
  BulletList[0, 40] isTight
    BulletListItem[0, 8] open:[0, 1, "-"] isTight
      Paragraph[4, 8]
        Text[4, 7] chars:[4, 7, "foo"]
    BulletListItem[8, 27] open:[8, 9, "-"] isTight
      FencedCodeBlock[12, 27] open:[12, 15, "```"] content:[18, 22] lines[1] close:[24, 27, "```"]
        Text[18, 22] chars:[18, 22, "bar\n"]
    BulletListItem[28, 40] open:[28, 29, "-"] isTight
      IndentedCodeBlock[36, 40]
````````````````````````````````


When the list item starts with a blank line, the number of spaces
following the list marker doesn't change the required indentation:

```````````````````````````````` example List items: 27
-   
  foo
.
<ul>
<li>foo</li>
</ul>
````````````````````````````````


A list item can begin with at most one blank line.
In the following example, `foo` is not part of the list
item:

```````````````````````````````` example List items: 28
-

  foo
.
<ul>
<li></li>
</ul>
<p>foo</p>
.
Document[0, 9]
  BulletList[0, 1] isTight
    BulletListItem[0, 1] open:[0, 1, "-"] isTight hadBlankLineAfter
  Paragraph[5, 9]
    Text[5, 8] chars:[5, 8, "foo"]
````````````````````````````````


Here is an empty bullet list item:

```````````````````````````````` example List items: 29
- foo
-
- bar
.
<ul>
<li>foo</li>
<li></li>
<li>bar</li>
</ul>
.
Document[0, 14]
  BulletList[0, 14] isTight
    BulletListItem[0, 6] open:[0, 1, "-"] isTight
      Paragraph[2, 6]
        Text[2, 5] chars:[2, 5, "foo"]
    BulletListItem[6, 7] open:[6, 7, "-"] isTight
    BulletListItem[8, 14] open:[8, 9, "-"] isTight
      Paragraph[10, 14]
        Text[10, 13] chars:[10, 13, "bar"]
````````````````````````````````


It does not matter whether there are spaces following the [list marker]:

```````````````````````````````` example List items: 30
- foo
-   
- bar
.
<ul>
<li>foo</li>
<li></li>
<li>bar</li>
</ul>
.
Document[0, 17]
  BulletList[0, 17] isTight
    BulletListItem[0, 6] open:[0, 1, "-"] isTight
      Paragraph[2, 6]
        Text[2, 5] chars:[2, 5, "foo"]
    BulletListItem[6, 7] open:[6, 7, "-"] isTight
    BulletListItem[11, 17] open:[11, 12, "-"] isTight
      Paragraph[13, 17]
        Text[13, 16] chars:[13, 16, "bar"]
````````````````````````````````


Here is an empty ordered list item:

```````````````````````````````` example List items: 31
1. foo
2.
3. bar
.
<ol>
<li>foo</li>
<li></li>
<li>bar</li>
</ol>
.
Document[0, 17]
  OrderedList[0, 17] isTight delimiter:'.'
    OrderedListItem[0, 7] open:[0, 2, "1."] isTight
      Paragraph[3, 7]
        Text[3, 6] chars:[3, 6, "foo"]
    OrderedListItem[7, 9] open:[7, 9, "2."] isTight
    OrderedListItem[10, 17] open:[10, 12, "3."] isTight
      Paragraph[13, 17]
        Text[13, 16] chars:[13, 16, "bar"]
````````````````````````````````


A list may start or end with an empty list item:

```````````````````````````````` example List items: 32
*
.
<ul>
<li></li>
</ul>
.
Document[0, 2]
  BulletList[0, 1] isTight
    BulletListItem[0, 1] open:[0, 1, "*"] isTight
````````````````````````````````


However, an empty list item cannot interrupt a paragraph:

```````````````````````````````` example List items: 33
foo
*

foo
1.
.
<p>foo
*</p>
<p>foo
1.</p>
````````````````````````````````


4.  **Indentation.**  If a sequence of lines *Ls* constitutes a list item
    according to rule #1, #2, or #3, then the result of indenting each line
    of *Ls* by 1-3 spaces (the same for each line) also constitutes a
    list item with the same contents and attributes.  If a line is
    empty, then it need not be indented.

Indented one space:

```````````````````````````````` example List items: 34
 1.  A paragraph
     with two lines.

         indented code

     > A block quote.
.
<ol>
<li>
<p>A paragraph
with two lines.</p>
<pre><code>indented code
</code></pre>
<blockquote>
<p>A block quote.</p>
</blockquote>
</li>
</ol>
.
Document[0, 85]
  OrderedList[1, 85] isLoose delimiter:'.'
    OrderedListItem[1, 85] open:[1, 3, "1."] isLoose hadBlankLineAfter
      Paragraph[5, 38] isTrailingBlankLine
        Text[5, 16] chars:[5, 16, "A par … graph"]
        SoftLineBreak[16, 17]
        Text[22, 37] chars:[22, 37, "with  … ines."]
      IndentedCodeBlock[48, 62]
      BlockQuote[68, 85] marker:[68, 69, ">"]
        Paragraph[70, 85]
          Text[70, 84] chars:[70, 84, "A blo … uote."]
````````````````````````````````


Indented two spaces:

```````````````````````````````` example List items: 35
  1.  A paragraph
      with two lines.

          indented code

      > A block quote.
.
<ol>
<li>
<p>A paragraph
with two lines.</p>
<pre><code>indented code
</code></pre>
<blockquote>
<p>A block quote.</p>
</blockquote>
</li>
</ol>
.
Document[0, 89]
  OrderedList[2, 89] isLoose delimiter:'.'
    OrderedListItem[2, 89] open:[2, 4, "1."] isLoose hadBlankLineAfter
      Paragraph[6, 40] isTrailingBlankLine
        Text[6, 17] chars:[6, 17, "A par … graph"]
        SoftLineBreak[17, 18]
        Text[24, 39] chars:[24, 39, "with  … ines."]
      IndentedCodeBlock[51, 65]
      BlockQuote[72, 89] marker:[72, 73, ">"]
        Paragraph[74, 89]
          Text[74, 88] chars:[74, 88, "A blo … uote."]
````````````````````````````````


Indented three spaces:

```````````````````````````````` example List items: 36
   1.  A paragraph
       with two lines.

           indented code

       > A block quote.
.
<ol>
<li>
<p>A paragraph
with two lines.</p>
<pre><code>indented code
</code></pre>
<blockquote>
<p>A block quote.</p>
</blockquote>
</li>
</ol>
.
Document[0, 93]
  OrderedList[3, 93] isLoose delimiter:'.'
    OrderedListItem[3, 93] open:[3, 5, "1."] isLoose hadBlankLineAfter
      Paragraph[7, 42] isTrailingBlankLine
        Text[7, 18] chars:[7, 18, "A par … graph"]
        SoftLineBreak[18, 19]
        Text[26, 41] chars:[26, 41, "with  … ines."]
      IndentedCodeBlock[54, 68]
      BlockQuote[76, 93] marker:[76, 77, ">"]
        Paragraph[78, 93]
          Text[78, 92] chars:[78, 92, "A blo … uote."]
````````````````````````````````


Four spaces indent gives a code block:

```````````````````````````````` example List items: 37
    1.  A paragraph
        with two lines.

            indented code

        > A block quote.
.
<pre><code>1.  A paragraph
    with two lines.

        indented code

    &gt; A block quote.
</code></pre>
.
Document[0, 97]
  IndentedCodeBlock[4, 97]
````````````````````````````````



5.  **Laziness.**  If a string of lines *Ls* constitute a [list
    item](#list-items) with contents *Bs*, then the result of deleting
    some or all of the indentation from one or more lines in which the
    next [non-whitespace character] after the indentation is
    [paragraph continuation text] is a
    list item with the same contents and attributes.  The unindented
    lines are called
    [lazy continuation line](@)s.

Here is an example with [lazy continuation lines]:

```````````````````````````````` example List items: 38
  1.  A paragraph
with two lines.

          indented code

      > A block quote.
.
<ol>
<li>
<p>A paragraph
with two lines.</p>
<pre><code>indented code
</code></pre>
<blockquote>
<p>A block quote.</p>
</blockquote>
</li>
</ol>
.
Document[0, 83]
  OrderedList[2, 83] isLoose delimiter:'.'
    OrderedListItem[2, 83] open:[2, 4, "1."] isLoose hadBlankLineAfter
      Paragraph[6, 34] isTrailingBlankLine
        Text[6, 17] chars:[6, 17, "A par … graph"]
        SoftLineBreak[17, 18]
        Text[18, 33] chars:[18, 33, "with  … ines."]
      IndentedCodeBlock[45, 59]
      BlockQuote[66, 83] marker:[66, 67, ">"]
        Paragraph[68, 83]
          Text[68, 82] chars:[68, 82, "A blo … uote."]
````````````````````````````````


Indentation can be partially deleted:

```````````````````````````````` example List items: 39
  1.  A paragraph
    with two lines.
.
<ol>
<li>A paragraph
with two lines.</li>
</ol>
.
Document[0, 38]
  OrderedList[2, 38] isTight delimiter:'.'
    OrderedListItem[2, 38] open:[2, 4, "1."] isTight
      Paragraph[6, 38]
        Text[6, 17] chars:[6, 17, "A par … graph"]
        SoftLineBreak[17, 18]
        Text[22, 37] chars:[22, 37, "with  … ines."]
````````````````````````````````


These examples show how laziness can work in nested structures:

```````````````````````````````` example List items: 40
> 1. > Blockquote
continued here.
.
<blockquote>
<ol>
<li>
<blockquote>
<p>Blockquote
continued here.</p>
</blockquote>
</li>
</ol>
</blockquote>
.
Document[0, 34]
  BlockQuote[0, 34] marker:[0, 1, ">"]
    OrderedList[2, 34] isTight delimiter:'.'
      OrderedListItem[2, 34] open:[2, 4, "1."] isTight
        BlockQuote[5, 34] marker:[5, 6, ">"]
          Paragraph[7, 34]
            Text[7, 17] chars:[7, 17, "Blockquote"]
            SoftLineBreak[17, 18]
            Text[18, 33] chars:[18, 33, "conti … here."]
````````````````````````````````


```````````````````````````````` example List items: 41
> 1. > Blockquote
> continued here.
.
<blockquote>
<ol>
<li>
<blockquote>
<p>Blockquote
continued here.</p>
</blockquote>
</li>
</ol>
</blockquote>
.
Document[0, 36]
  BlockQuote[0, 36] marker:[0, 1, ">"]
    OrderedList[2, 36] isTight delimiter:'.'
      OrderedListItem[2, 36] open:[2, 4, "1."] isTight
        BlockQuote[5, 36] marker:[5, 6, ">"]
          Paragraph[7, 36]
            Text[7, 17] chars:[7, 17, "Blockquote"]
            SoftLineBreak[17, 18]
            Text[20, 35] chars:[20, 35, "conti … here."]
````````````````````````````````



6.  **That's all.** Nothing that is not counted as a list item by rules
    #1--5 counts as a [list item](#list-items).

The rules for sublists follow from the general rules above.  A sublist
must be indented the same number of spaces a paragraph would need to be
in order to be included in the list item.

So, in this case we need two spaces indent:

```````````````````````````````` example List items: 42
- foo
  - bar
    - baz
      - boo
.
<ul>
<li>foo
<ul>
<li>bar
<ul>
<li>baz
<ul>
<li>boo</li>
</ul>
</li>
</ul>
</li>
</ul>
</li>
</ul>
.
Document[0, 36]
  BulletList[0, 36] isTight
    BulletListItem[0, 36] open:[0, 1, "-"] isTight
      Paragraph[2, 6]
        Text[2, 5] chars:[2, 5, "foo"]
      BulletList[8, 36] isTight
        BulletListItem[8, 36] open:[8, 9, "-"] isTight
          Paragraph[10, 14]
            Text[10, 13] chars:[10, 13, "bar"]
          BulletList[18, 36] isTight
            BulletListItem[18, 36] open:[18, 19, "-"] isTight
              Paragraph[20, 24]
                Text[20, 23] chars:[20, 23, "baz"]
              BulletList[30, 36] isTight
                BulletListItem[30, 36] open:[30, 31, "-"] isTight
                  Paragraph[32, 36]
                    Text[32, 35] chars:[32, 35, "boo"]
````````````````````````````````


One is not enough:

```````````````````````````````` example List items: 43
- foo
 - bar
  - baz
   - boo
.
<ul>
<li>foo</li>
<li>bar</li>
<li>baz</li>
<li>boo</li>
</ul>
.
Document[0, 30]
  BulletList[0, 30] isTight
    BulletListItem[0, 6] open:[0, 1, "-"] isTight
      Paragraph[2, 6]
        Text[2, 5] chars:[2, 5, "foo"]
    BulletListItem[7, 13] open:[7, 8, "-"] isTight
      Paragraph[9, 13]
        Text[9, 12] chars:[9, 12, "bar"]
    BulletListItem[15, 21] open:[15, 16, "-"] isTight
      Paragraph[17, 21]
        Text[17, 20] chars:[17, 20, "baz"]
    BulletListItem[24, 30] open:[24, 25, "-"] isTight
      Paragraph[26, 30]
        Text[26, 29] chars:[26, 29, "boo"]
````````````````````````````````


Here we need four, because the list marker is wider:

```````````````````````````````` example List items: 44
10) foo
    - bar
.
<ol start="10">
<li>foo
<ul>
<li>bar</li>
</ul>
</li>
</ol>
.
Document[0, 18]
  OrderedList[0, 18] isTight start:10 delimiter:')'
    OrderedListItem[0, 18] open:[0, 3, "10)"] isTight
      Paragraph[4, 8]
        Text[4, 7] chars:[4, 7, "foo"]
      BulletList[12, 18] isTight
        BulletListItem[12, 18] open:[12, 13, "-"] isTight
          Paragraph[14, 18]
            Text[14, 17] chars:[14, 17, "bar"]
````````````````````````````````


Three is not enough:

```````````````````````````````` example List items: 45
10) foo
   - bar
.
<ol start="10">
<li>foo</li>
</ol>
<ul>
<li>bar</li>
</ul>
.
Document[0, 17]
  OrderedList[0, 8] isTight start:10 delimiter:')'
    OrderedListItem[0, 8] open:[0, 3, "10)"] isTight
      Paragraph[4, 8]
        Text[4, 7] chars:[4, 7, "foo"]
  BulletList[11, 17] isTight
    BulletListItem[11, 17] open:[11, 12, "-"] isTight
      Paragraph[13, 17]
        Text[13, 16] chars:[13, 16, "bar"]
````````````````````````````````


A list may be the first block in a list item:

```````````````````````````````` example List items: 46
- - foo
.
<ul>
<li>
<ul>
<li>foo</li>
</ul>
</li>
</ul>
.
Document[0, 8]
  BulletList[0, 8] isTight
    BulletListItem[0, 8] open:[0, 1, "-"] isTight
      BulletList[2, 8] isTight
        BulletListItem[2, 8] open:[2, 3, "-"] isTight
          Paragraph[4, 8]
            Text[4, 7] chars:[4, 7, "foo"]
````````````````````````````````


```````````````````````````````` example List items: 47
1. - 2. foo
.
<ol>
<li>
<ul>
<li>
<ol start="2">
<li>foo</li>
</ol>
</li>
</ul>
</li>
</ol>
.
Document[0, 12]
  OrderedList[0, 12] isTight delimiter:'.'
    OrderedListItem[0, 12] open:[0, 2, "1."] isTight
      BulletList[3, 12] isTight
        BulletListItem[3, 12] open:[3, 4, "-"] isTight
          OrderedList[5, 12] isTight start:2 delimiter:'.'
            OrderedListItem[5, 12] open:[5, 7, "2."] isTight
              Paragraph[8, 12]
                Text[8, 11] chars:[8, 11, "foo"]
````````````````````````````````


A list item can contain a heading:

```````````````````````````````` example List items: 48
- # Foo
- Bar
  ---
  baz
.
<ul>
<li>
<h1>Foo</h1>
</li>
<li>
<h2>Bar</h2>
baz</li>
</ul>
.
Document[0, 26]
  BulletList[0, 26] isTight
    BulletListItem[0, 7] open:[0, 1, "-"] isTight
      Heading[2, 7] textOpen:[2, 3, "#"] text:[4, 7, "Foo"]
        Text[4, 7] chars:[4, 7, "Foo"]
    BulletListItem[8, 26] open:[8, 9, "-"] isTight
      Heading[10, 19] text:[10, 13, "Bar"] textClose:[16, 19, "---"]
        Text[10, 13] chars:[10, 13, "Bar"]
      Paragraph[22, 26]
        Text[22, 25] chars:[22, 25, "baz"]
````````````````````````````````


### Motivation

John Gruber's Markdown spec says the following about list items:

1. "List markers typically start at the left margin, but may be indented
   by up to three spaces. List markers must be followed by one or more
   spaces or a tab."

2. "To make lists look nice, you can wrap items with hanging indents....
   But if you don't want to, you don't have to."

3. "List items may consist of multiple paragraphs. Each subsequent
   paragraph in a list item must be indented by either 4 spaces or one
   tab."

4. "It looks nice if you indent every line of the subsequent paragraphs,
   but here again, Markdown will allow you to be lazy."

5. "To put a blockquote within a list item, the blockquote's `>`
   delimiters need to be indented."

6. "To put a code block within a list item, the code block needs to be
   indented twice — 8 spaces or two tabs."

These rules specify that a paragraph under a list item must be indented
four spaces (presumably, from the left margin, rather than the start of
the list marker, but this is not said), and that code under a list item
must be indented eight spaces instead of the usual four.  They also say
that a block quote must be indented, but not by how much; however, the
example given has four spaces indentation.  Although nothing is said
about other kinds of block-level content, it is certainly reasonable to
infer that *all* block elements under a list item, including other
lists, must be indented four spaces.  This principle has been called the
*four-space rule*.

The four-space rule is clear and principled, and if the reference
implementation `Markdown.pl` had followed it, it probably would have
become the standard.  However, `Markdown.pl` allowed paragraphs and
sublists to start with only two spaces indentation, at least on the
outer level.  Worse, its behavior was inconsistent: a sublist of an
outer-level list needed two spaces indentation, but a sublist of this
sublist needed three spaces.  It is not surprising, then, that different
implementations of Markdown have developed very different rules for
determining what comes under a list item.  (Pandoc and python-Markdown,
for example, stuck with Gruber's syntax description and the four-space
rule, while discount, redcarpet, marked, PHP Markdown, and others
followed `Markdown.pl`'s behavior more closely.)

Unfortunately, given the divergences between implementations, there
is no way to give a spec for list items that will be guaranteed not
to break any existing documents.  However, the spec given here should
correctly handle lists formatted with either the four-space rule or
the more forgiving `Markdown.pl` behavior, provided they are laid out
in a way that is natural for a human to read.

The strategy here is to let the width and indentation of the list marker
determine the indentation necessary for blocks to fall under the list
item, rather than having a fixed and arbitrary number.  The writer can
think of the body of the list item as a unit which gets indented to the
right enough to fit the list marker (and any indentation on the list
marker).  (The laziness rule, #5, then allows continuation lines to be
unindented if needed.)

This rule is superior, we claim, to any rule requiring a fixed level of
indentation from the margin.  The four-space rule is clear but
unnatural. It is quite unintuitive that

``` markdown
- foo

  bar

  - baz
```

should be parsed as two lists with an intervening paragraph,

``` html
<ul>
<li>foo</li>
</ul>
<p>bar</p>
<ul>
<li>baz</li>
</ul>
```

as the four-space rule demands, rather than a single list,

``` html
<ul>
<li>
<p>foo</p>
<p>bar</p>
<ul>
<li>baz</li>
</ul>
</li>
</ul>
```

The choice of four spaces is arbitrary.  It can be learned, but it is
not likely to be guessed, and it trips up beginners regularly.

Would it help to adopt a two-space rule?  The problem is that such
a rule, together with the rule allowing 1--3 spaces indentation of the
initial list marker, allows text that is indented *less than* the
original list marker to be included in the list item. For example,
`Markdown.pl` parses

``` markdown
   - one

  two
```

as a single list item, with `two` a continuation paragraph:

``` html
<ul>
<li>
<p>one</p>
<p>two</p>
</li>
</ul>
```

and similarly

``` markdown
>   - one
>
>  two
```

as

``` html
<blockquote>
<ul>
<li>
<p>one</p>
<p>two</p>
</li>
</ul>
</blockquote>
```

This is extremely unintuitive.

Rather than requiring a fixed indent from the margin, we could require
a fixed indent (say, two spaces, or even one space) from the list marker (which
may itself be indented).  This proposal would remove the last anomaly
discussed.  Unlike the spec presented above, it would count the following
as a list item with a subparagraph, even though the paragraph `bar`
is not indented as far as the first paragraph `foo`:

``` markdown
 10. foo

   bar  
```

Arguably this text does read like a list item with `bar` as a subparagraph,
which may count in favor of the proposal.  However, on this proposal indented
code would have to be indented six spaces after the list marker.  And this
would break a lot of existing Markdown, which has the pattern:

``` markdown
1.  foo

        indented code
```

where the code is indented eight spaces.  The spec above, by contrast, will
parse this text as expected, since the code block's indentation is measured
from the beginning of `foo`.

The one case that needs special treatment is a list item that *starts*
with indented code.  How much indentation is required in that case, since
we don't have a "first paragraph" to measure from?  Rule #2 simply stipulates
that in such cases, we require one space indentation from the list marker
(and then the normal four spaces for the indented code).  This will match the
four-space rule in cases where the list marker plus its initial indentation
takes four spaces (a common case), but diverge in other cases.

## Lists

A [list](@) is a sequence of one or more
list items [of the same type].  The list items
may be separated by any number of blank lines.

Two list items are [of the same type](@)
if they begin with a [list marker] of the same type.
Two list markers are of the
same type if (a) they are bullet list markers using the same character
(`-`, `+`, or `*`) or (b) they are ordered list numbers with the same
delimiter (either `.` or `)`).

A list is an [ordered list](@)
if its constituent list items begin with
[ordered list markers], and a
[bullet list](@) if its constituent list
items begin with [bullet list markers].

The [start number](@)
of an [ordered list] is determined by the list number of
its initial list item.  The numbers of subsequent list items are
disregarded.

A list is [loose](@) if any of its constituent
list items are separated by blank lines, or if any of its constituent
list items directly contain two block-level elements with a blank line
between them.  Otherwise a list is [tight](@).
(The difference in HTML output is that paragraphs in a loose list are
wrapped in `<p>` tags, while paragraphs in a tight list are not.)

Changing the bullet or ordered list delimiter starts a new list:

```````````````````````````````` example Lists: 1
- foo
- bar
+ baz
.
<ul>
<li>foo</li>
<li>bar</li>
</ul>
<ul>
<li>baz</li>
</ul>
.
Document[0, 18]
  BulletList[0, 12] isTight
    BulletListItem[0, 6] open:[0, 1, "-"] isTight
      Paragraph[2, 6]
        Text[2, 5] chars:[2, 5, "foo"]
    BulletListItem[6, 12] open:[6, 7, "-"] isTight
      Paragraph[8, 12]
        Text[8, 11] chars:[8, 11, "bar"]
  BulletList[12, 18] isTight
    BulletListItem[12, 18] open:[12, 13, "+"] isTight
      Paragraph[14, 18]
        Text[14, 17] chars:[14, 17, "baz"]
````````````````````````````````


```````````````````````````````` example Lists: 2
1. foo
2. bar
3) baz
.
<ol>
<li>foo</li>
<li>bar</li>
</ol>
<ol start="3">
<li>baz</li>
</ol>
.
Document[0, 21]
  OrderedList[0, 14] isTight delimiter:'.'
    OrderedListItem[0, 7] open:[0, 2, "1."] isTight
      Paragraph[3, 7]
        Text[3, 6] chars:[3, 6, "foo"]
    OrderedListItem[7, 14] open:[7, 9, "2."] isTight
      Paragraph[10, 14]
        Text[10, 13] chars:[10, 13, "bar"]
  OrderedList[14, 21] isTight start:3 delimiter:')'
    OrderedListItem[14, 21] open:[14, 16, "3)"] isTight
      Paragraph[17, 21]
        Text[17, 20] chars:[17, 20, "baz"]
````````````````````````````````


In CommonMark, a list can interrupt a paragraph. That is,
no blank line is needed to separate a paragraph from a following
list:

```````````````````````````````` example Lists: 3
Foo
- bar
- baz
.
<p>Foo</p>
<ul>
<li>bar</li>
<li>baz</li>
</ul>
.
Document[0, 16]
  Paragraph[0, 4]
    Text[0, 3] chars:[0, 3, "Foo"]
  BulletList[4, 16] isTight
    BulletListItem[4, 10] open:[4, 5, "-"] isTight
      Paragraph[6, 10]
        Text[6, 9] chars:[6, 9, "bar"]
    BulletListItem[10, 16] open:[10, 11, "-"] isTight
      Paragraph[12, 16]
        Text[12, 15] chars:[12, 15, "baz"]
````````````````````````````````


`Markdown.pl` does not allow this, through fear of triggering a list
via a numeral in a hard-wrapped line:

``` markdown
The number of windows in my house is
14.  The number of doors is 6.
```

Oddly, though, `Markdown.pl` *does* allow a blockquote to
interrupt a paragraph, even though the same considerations might
apply.

In CommonMark, we do allow lists to interrupt paragraphs, for
two reasons.  First, it is natural and not uncommon for people
to start lists without blank lines:

``` markdown
I need to buy
- new shoes
- a coat
- a plane ticket
```

Second, we are attracted to a

> [principle of uniformity](@):
> if a chunk of text has a certain
> meaning, it will continue to have the same meaning when put into a
> container block (such as a list item or blockquote).

(Indeed, the spec for [list items] and [block quotes] presupposes
this principle.) This principle implies that if

``` markdown
  * I need to buy
    - new shoes
    - a coat
    - a plane ticket
```

is a list item containing a paragraph followed by a nested sublist,
as all Markdown implementations agree it is (though the paragraph
may be rendered without `<p>` tags, since the list is "tight"),
then

``` markdown
I need to buy
- new shoes
- a coat
- a plane ticket
```

by itself should be a paragraph followed by a nested sublist.

Since it is well established Markdown practice to allow lists to
interrupt paragraphs inside list items, the [principle of
uniformity] requires us to allow this outside list items as
well.  ([reStructuredText](http://docutils.sourceforge.net/rst.html)
takes a different approach, requiring blank lines before lists
even inside other list items.)

In order to solve of unwanted lists in paragraphs with
hard-wrapped numerals, we allow only lists starting with `1` to
interrupt paragraphs.  Thus,

```````````````````````````````` example Lists: 4
The number of windows in my house is
14.  The number of doors is 6.
.
<p>The number of windows in my house is
14.  The number of doors is 6.</p>
.
Document[0, 68]
  Paragraph[0, 68]
    Text[0, 36] chars:[0, 36, "The n … se is"]
    SoftLineBreak[36, 37]
    Text[37, 67] chars:[37, 67, "14.   … is 6."]
````````````````````````````````


We may still get an unintended result in cases like

```````````````````````````````` example Lists: 5
The number of windows in my house is
1.  The number of doors is 6.
.
<p>The number of windows in my house is</p>
<ol>
<li>The number of doors is 6.</li>
</ol>
.
Document[0, 67]
  Paragraph[0, 37]
    Text[0, 36] chars:[0, 36, "The n … se is"]
  OrderedList[37, 67] isTight delimiter:'.'
    OrderedListItem[37, 67] open:[37, 39, "1."] isTight
      Paragraph[41, 67]
        Text[41, 66] chars:[41, 66, "The n … is 6."]
````````````````````````````````

but this rule should prevent most spurious list captures.

There can be any number of blank lines between items:

```````````````````````````````` example Lists: 6
- foo

- bar


- baz
.
<ul>
<li>
<p>foo</p>
</li>
<li>
<p>bar</p>
</li>
<li>
<p>baz</p>
</li>
</ul>
.
Document[0, 21]
  BulletList[0, 21] isLoose
    BulletListItem[0, 6] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[2, 6] isTrailingBlankLine
        Text[2, 5] chars:[2, 5, "foo"]
    BulletListItem[7, 13] open:[7, 8, "-"] isLoose hadBlankLineAfter
      Paragraph[9, 13] isTrailingBlankLine
        Text[9, 12] chars:[9, 12, "bar"]
    BulletListItem[15, 21] open:[15, 16, "-"] isLoose
      Paragraph[17, 21]
        Text[17, 20] chars:[17, 20, "baz"]
````````````````````````````````

```````````````````````````````` example Lists: 7
- foo
  - bar
    - baz


      bim
.
<ul>
<li>foo
<ul>
<li>bar
<ul>
<li>
<p>baz</p>
<p>bim</p>
</li>
</ul>
</li>
</ul>
</li>
</ul>
.
Document[0, 36]
  BulletList[0, 36] isTight
    BulletListItem[0, 36] open:[0, 1, "-"] isTight hadBlankLine
      Paragraph[2, 6]
        Text[2, 5] chars:[2, 5, "foo"]
      BulletList[8, 36] isTight
        BulletListItem[8, 36] open:[8, 9, "-"] isTight hadBlankLine
          Paragraph[10, 14]
            Text[10, 13] chars:[10, 13, "bar"]
          BulletList[18, 36] isLoose
            BulletListItem[18, 36] open:[18, 19, "-"] isLoose hadBlankLineAfter
              Paragraph[20, 24] isTrailingBlankLine
                Text[20, 23] chars:[20, 23, "baz"]
              Paragraph[32, 36]
                Text[32, 35] chars:[32, 35, "bim"]
````````````````````````````````


To separate consecutive lists of the same type, or to separate a
list from an indented code block that would otherwise be parsed
as a subparagraph of the final list item, you can insert a blank HTML
comment:

```````````````````````````````` example Lists: 8
- foo
- bar

<!-- -->

- baz
- bim
.
<ul>
<li>foo</li>
<li>bar</li>
</ul>
<!-- -->
<ul>
<li>baz</li>
<li>bim</li>
</ul>
.
Document[0, 35]
  BulletList[0, 12] isTight
    BulletListItem[0, 6] open:[0, 1, "-"] isTight
      Paragraph[2, 6]
        Text[2, 5] chars:[2, 5, "foo"]
    BulletListItem[6, 12] open:[6, 7, "-"] isTight hadBlankLineAfter
      Paragraph[8, 12] isTrailingBlankLine
        Text[8, 11] chars:[8, 11, "bar"]
  HtmlCommentBlock[13, 22]
  BulletList[23, 35] isTight
    BulletListItem[23, 29] open:[23, 24, "-"] isTight
      Paragraph[25, 29]
        Text[25, 28] chars:[25, 28, "baz"]
    BulletListItem[29, 35] open:[29, 30, "-"] isTight
      Paragraph[31, 35]
        Text[31, 34] chars:[31, 34, "bim"]
````````````````````````````````


```````````````````````````````` example Lists: 9
-   foo

    notcode

-   foo

<!-- -->

    code
.
<ul>
<li>
<p>foo</p>
<p>notcode</p>
</li>
<li>
<p>foo</p>
</li>
</ul>
<!-- -->
<pre><code>code
</code></pre>
.
Document[0, 50]
  BulletList[0, 30] isLoose
    BulletListItem[0, 21] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[4, 8] isTrailingBlankLine
        Text[4, 7] chars:[4, 7, "foo"]
      Paragraph[13, 21] isTrailingBlankLine
        Text[13, 20] chars:[13, 20, "notcode"]
    BulletListItem[22, 30] open:[22, 23, "-"] isLoose hadBlankLineAfter
      Paragraph[26, 30] isTrailingBlankLine
        Text[26, 29] chars:[26, 29, "foo"]
  HtmlCommentBlock[31, 40]
  IndentedCodeBlock[45, 50]
````````````````````````````````


List items need not be indented to the same level.  The following
list items will be treated as items at the same list level,
since none is indented enough to belong to the previous list
item:

```````````````````````````````` example Lists: 10
- a
 - b
  - c
   - d
    - e
   - f
  - g
 - h
- i
.
<ul>
<li>a</li>
<li>b</li>
<li>c</li>
<li>d</li>
<li>e</li>
<li>f</li>
<li>g</li>
<li>h</li>
<li>i</li>
</ul>
.
Document[0, 52]
  BulletList[0, 52] isTight
    BulletListItem[0, 4] open:[0, 1, "-"] isTight
      Paragraph[2, 4]
        Text[2, 3] chars:[2, 3, "a"]
    BulletListItem[5, 9] open:[5, 6, "-"] isTight
      Paragraph[7, 9]
        Text[7, 8] chars:[7, 8, "b"]
    BulletListItem[11, 15] open:[11, 12, "-"] isTight
      Paragraph[13, 15]
        Text[13, 14] chars:[13, 14, "c"]
    BulletListItem[18, 22] open:[18, 19, "-"] isTight
      Paragraph[20, 22]
        Text[20, 21] chars:[20, 21, "d"]
    BulletListItem[26, 30] open:[26, 27, "-"] isTight
      Paragraph[28, 30]
        Text[28, 29] chars:[28, 29, "e"]
    BulletListItem[33, 37] open:[33, 34, "-"] isTight
      Paragraph[35, 37]
        Text[35, 36] chars:[35, 36, "f"]
    BulletListItem[39, 43] open:[39, 40, "-"] isTight
      Paragraph[41, 43]
        Text[41, 42] chars:[41, 42, "g"]
    BulletListItem[44, 48] open:[44, 45, "-"] isTight
      Paragraph[46, 48]
        Text[46, 47] chars:[46, 47, "h"]
    BulletListItem[48, 52] open:[48, 49, "-"] isTight
      Paragraph[50, 52]
        Text[50, 51] chars:[50, 51, "i"]
````````````````````````````````


```````````````````````````````` example Lists: 11
1. a

  2. b

    3. c
.
<ol>
<li>
<p>a</p>
</li>
<li>
<p>b</p>
</li>
<li>
<p>c</p>
</li>
</ol>
.
Document[0, 23]
  OrderedList[0, 23] isLoose delimiter:'.'
    OrderedListItem[0, 5] open:[0, 2, "1."] isLoose hadBlankLineAfter
      Paragraph[3, 5] isTrailingBlankLine
        Text[3, 4] chars:[3, 4, "a"]
    OrderedListItem[8, 13] open:[8, 10, "2."] isLoose hadBlankLineAfter
      Paragraph[11, 13] isTrailingBlankLine
        Text[11, 12] chars:[11, 12, "b"]
    OrderedListItem[18, 23] open:[18, 20, "3."] isLoose
      Paragraph[21, 23]
        Text[21, 22] chars:[21, 22, "c"]
````````````````````````````````


This is a loose list, because there is a blank line between
two of the list items:

```````````````````````````````` example Lists: 12
- a
- b

- c
.
<ul>
<li>
<p>a</p>
</li>
<li>
<p>b</p>
</li>
<li>
<p>c</p>
</li>
</ul>
.
Document[0, 13]
  BulletList[0, 13] isLoose
    BulletListItem[0, 4] open:[0, 1, "-"] isLoose
      Paragraph[2, 4]
        Text[2, 3] chars:[2, 3, "a"]
    BulletListItem[4, 8] open:[4, 5, "-"] isLoose hadBlankLineAfter
      Paragraph[6, 8] isTrailingBlankLine
        Text[6, 7] chars:[6, 7, "b"]
    BulletListItem[9, 13] open:[9, 10, "-"] isLoose
      Paragraph[11, 13]
        Text[11, 12] chars:[11, 12, "c"]
````````````````````````````````


So is this, with a empty second item:

```````````````````````````````` example Lists: 13
* a
*

* c
.
<ul>
<li>
<p>a</p>
</li>
<li></li>
<li>
<p>c</p>
</li>
</ul>
.
Document[0, 11]
  BulletList[0, 11] isLoose
    BulletListItem[0, 4] open:[0, 1, "*"] isLoose
      Paragraph[2, 4]
        Text[2, 3] chars:[2, 3, "a"]
    BulletListItem[4, 5] open:[4, 5, "*"] isLoose hadBlankLineAfter
    BulletListItem[7, 11] open:[7, 8, "*"] isLoose
      Paragraph[9, 11]
        Text[9, 10] chars:[9, 10, "c"]
````````````````````````````````


These are loose lists, even though there is no space between the items,
because one of the items directly contains two block-level elements
with a blank line between them:

```````````````````````````````` example Lists: 14
- a
- b

  c
- d
.
<ul>
<li>
<p>a</p>
</li>
<li>
<p>b</p>
<p>c</p>
</li>
<li>
<p>d</p>
</li>
</ul>
.
Document[0, 17]
  BulletList[0, 17] isLoose
    BulletListItem[0, 4] open:[0, 1, "-"] isLoose
      Paragraph[2, 4]
        Text[2, 3] chars:[2, 3, "a"]
    BulletListItem[4, 13] open:[4, 5, "-"] isLoose hadBlankLineAfter
      Paragraph[6, 8] isTrailingBlankLine
        Text[6, 7] chars:[6, 7, "b"]
      Paragraph[11, 13]
        Text[11, 12] chars:[11, 12, "c"]
    BulletListItem[13, 17] open:[13, 14, "-"] isLoose
      Paragraph[15, 17]
        Text[15, 16] chars:[15, 16, "d"]
````````````````````````````````


```````````````````````````````` example Lists: 15
- a
- b

  [ref]: /url
- d
.
<ul>
<li>
<p>a</p>
</li>
<li>
<p>b</p>
</li>
<li>
<p>d</p>
</li>
</ul>
.
Document[0, 27]
  BulletList[0, 27] isLoose
    BulletListItem[0, 4] open:[0, 1, "-"] isLoose
      Paragraph[2, 4]
        Text[2, 3] chars:[2, 3, "a"]
    BulletListItem[4, 23] open:[4, 5, "-"] isLoose hadBlankLineAfter
      Paragraph[6, 8] isTrailingBlankLine
        Text[6, 7] chars:[6, 7, "b"]
      Reference[11, 22] refOpen:[11, 12, "["] ref:[12, 15, "ref"] refClose:[15, 17, "]:"] url:[18, 22, "/url"]
    BulletListItem[23, 27] open:[23, 24, "-"] isLoose
      Paragraph[25, 27]
        Text[25, 26] chars:[25, 26, "d"]
````````````````````````````````


This is a tight list, because the blank lines are in a code block:

```````````````````````````````` example Lists: 16
- a
- ```
  b


  ```
- c
.
<ul>
<li>a</li>
<li>
<pre><code>b


</code></pre>
</li>
<li>c</li>
</ul>
.
Document[0, 26]
  BulletList[0, 26] isTight
    BulletListItem[0, 4] open:[0, 1, "-"] isTight
      Paragraph[2, 4]
        Text[2, 3] chars:[2, 3, "a"]
    BulletListItem[4, 21] open:[4, 5, "-"] isTight hadBlankLineAfter
      FencedCodeBlock[6, 21] open:[6, 9, "```"] content:[12, 16] lines[3] close:[18, 21, "```"]
        Text[12, 16] chars:[12, 16, "b\n\n\n"]
    BulletListItem[22, 26] open:[22, 23, "-"] isTight
      Paragraph[24, 26]
        Text[24, 25] chars:[24, 25, "c"]
````````````````````````````````


This is a tight list, because the blank line is between two
paragraphs of a sublist.  So the sublist is loose while
the outer list is tight:

```````````````````````````````` example Lists: 17
- a
  - b

    c
- d
.
<ul>
<li>a
<ul>
<li>
<p>b</p>
<p>c</p>
</li>
</ul>
</li>
<li>d</li>
</ul>
.
Document[0, 21]
  BulletList[0, 21] isTight
    BulletListItem[0, 17] open:[0, 1, "-"] isTight hadBlankLine
      Paragraph[2, 4]
        Text[2, 3] chars:[2, 3, "a"]
      BulletList[6, 17] isLoose
        BulletListItem[6, 17] open:[6, 7, "-"] isLoose hadBlankLineAfter
          Paragraph[8, 10] isTrailingBlankLine
            Text[8, 9] chars:[8, 9, "b"]
          Paragraph[15, 17]
            Text[15, 16] chars:[15, 16, "c"]
    BulletListItem[17, 21] open:[17, 18, "-"] isTight
      Paragraph[19, 21]
        Text[19, 20] chars:[19, 20, "d"]
````````````````````````````````


This is a tight list, because the blank line is inside the
block quote:

```````````````````````````````` example Lists: 18
* a
  > b
  >
* c
.
<ul>
<li>a
<blockquote>
<p>b</p>
</blockquote>
</li>
<li>c</li>
</ul>
.
Document[0, 18]
  BulletList[0, 18] isTight
    BulletListItem[0, 14] open:[0, 1, "*"] isTight
      Paragraph[2, 4]
        Text[2, 3] chars:[2, 3, "a"]
      BlockQuote[6, 14] marker:[6, 7, ">"]
        Paragraph[8, 10] isTrailingBlankLine
          Text[8, 9] chars:[8, 9, "b"]
    BulletListItem[14, 18] open:[14, 15, "*"] isTight
      Paragraph[16, 18]
        Text[16, 17] chars:[16, 17, "c"]
````````````````````````````````


This list is tight, because the consecutive block elements
are not separated by blank lines:

```````````````````````````````` example Lists: 19
- a
  > b
  ```
  c
  ```
- d
.
<ul>
<li>a
<blockquote>
<p>b</p>
</blockquote>
<pre><code>c
</code></pre>
</li>
<li>d</li>
</ul>
.
Document[0, 30]
  BulletList[0, 30] isTight
    BulletListItem[0, 25] open:[0, 1, "-"] isTight
      Paragraph[2, 4]
        Text[2, 3] chars:[2, 3, "a"]
      BlockQuote[6, 10] marker:[6, 7, ">"]
        Paragraph[8, 10]
          Text[8, 9] chars:[8, 9, "b"]
      FencedCodeBlock[12, 25] open:[12, 15, "```"] content:[18, 20] lines[1] close:[22, 25, "```"]
        Text[18, 20] chars:[18, 20, "c\n"]
    BulletListItem[26, 30] open:[26, 27, "-"] isTight
      Paragraph[28, 30]
        Text[28, 29] chars:[28, 29, "d"]
````````````````````````````````


A single-paragraph list is tight:

```````````````````````````````` example Lists: 20
- a
.
<ul>
<li>a</li>
</ul>
.
Document[0, 4]
  BulletList[0, 4] isTight
    BulletListItem[0, 4] open:[0, 1, "-"] isTight
      Paragraph[2, 4]
        Text[2, 3] chars:[2, 3, "a"]
````````````````````````````````


```````````````````````````````` example Lists: 21
- a
  - b
.
<ul>
<li>a
<ul>
<li>b</li>
</ul>
</li>
</ul>
.
Document[0, 10]
  BulletList[0, 10] isTight
    BulletListItem[0, 10] open:[0, 1, "-"] isTight
      Paragraph[2, 4]
        Text[2, 3] chars:[2, 3, "a"]
      BulletList[6, 10] isTight
        BulletListItem[6, 10] open:[6, 7, "-"] isTight
          Paragraph[8, 10]
            Text[8, 9] chars:[8, 9, "b"]
````````````````````````````````


This list is loose, because of the blank line between the
two block elements in the list item:

```````````````````````````````` example Lists: 22
1. ```
   foo
   ```

   bar
.
<ol>
<li>
<pre><code>foo
</code></pre>
<p>bar</p>
</li>
</ol>
.
Document[0, 29]
  OrderedList[0, 29] isLoose delimiter:'.'
    OrderedListItem[0, 29] open:[0, 2, "1."] isLoose hadBlankLineAfter
      FencedCodeBlock[3, 20] open:[3, 6, "```"] content:[10, 14] lines[1] close:[17, 20, "```"]
        Text[10, 14] chars:[10, 14, "foo\n"]
      Paragraph[25, 29]
        Text[25, 28] chars:[25, 28, "bar"]
````````````````````````````````


Here the outer list is loose, the inner list tight:

```````````````````````````````` example Lists: 23
* foo
  * bar

  baz
.
<ul>
<li>
<p>foo</p>
<ul>
<li>bar</li>
</ul>
<p>baz</p>
</li>
</ul>
.
Document[0, 21]
  BulletList[0, 21] isLoose
    BulletListItem[0, 21] open:[0, 1, "*"] isLoose hadBlankLine
      Paragraph[2, 6]
        Text[2, 5] chars:[2, 5, "foo"]
      BulletList[8, 14] isTight
        BulletListItem[8, 14] open:[8, 9, "*"] isTight hadBlankLineAfter
          Paragraph[10, 14] isTrailingBlankLine
            Text[10, 13] chars:[10, 13, "bar"]
      Paragraph[17, 21]
        Text[17, 20] chars:[17, 20, "baz"]
````````````````````````````````


```````````````````````````````` example Lists: 24
- a
  - b
  - c

- d
  - e
  - f
.
<ul>
<li>
<p>a</p>
<ul>
<li>b</li>
<li>c</li>
</ul>
</li>
<li>
<p>d</p>
<ul>
<li>e</li>
<li>f</li>
</ul>
</li>
</ul>
.
Document[0, 33]
  BulletList[0, 33] isLoose
    BulletListItem[0, 16] open:[0, 1, "-"] isLoose
      Paragraph[2, 4]
        Text[2, 3] chars:[2, 3, "a"]
      BulletList[6, 16] isTight
        BulletListItem[6, 10] open:[6, 7, "-"] isTight
          Paragraph[8, 10]
            Text[8, 9] chars:[8, 9, "b"]
        BulletListItem[12, 16] open:[12, 13, "-"] isTight hadBlankLineAfter
          Paragraph[14, 16] isTrailingBlankLine
            Text[14, 15] chars:[14, 15, "c"]
    BulletListItem[17, 33] open:[17, 18, "-"] isLoose
      Paragraph[19, 21]
        Text[19, 20] chars:[19, 20, "d"]
      BulletList[23, 33] isTight
        BulletListItem[23, 27] open:[23, 24, "-"] isTight
          Paragraph[25, 27]
            Text[25, 26] chars:[25, 26, "e"]
        BulletListItem[29, 33] open:[29, 30, "-"] isTight
          Paragraph[31, 33]
            Text[31, 32] chars:[31, 32, "f"]
````````````````````````````````


# Inlines

Inlines are parsed sequentially from the beginning of the character
stream to the end (left to right, in left-to-right languages).
Thus, for example, in

```````````````````````````````` example Inlines: 1
`hi`lo`
.
<p><code>hi</code>lo`</p>
.
Document[0, 8]
  Paragraph[0, 8]
    Code[0, 4] textOpen:[0, 1, "`"] text:[1, 3, "hi"] textClose:[3, 4, "`"]
      Text[1, 3] chars:[1, 3, "hi"]
    Text[4, 7] chars:[4, 7, "lo`"]
````````````````````````````````


`hi` is parsed as code, leaving the backtick at the end as a literal
backtick.

## Backslash escapes

Any ASCII punctuation character may be backslash-escaped:

```````````````````````````````` example Backslash escapes: 1
\!\"\#\$\%\&\'\(\)\*\+\,\-\.\/\:\;\<\=\>\?\@\[\\\]\^\_\`\{\|\}\~
.
<p>!&quot;#$%&amp;'()*+,-./:;&lt;=&gt;?@[\]^_`{|}~</p>
.
Document[0, 65]
  Paragraph[0, 65]
    Text[0, 64] chars:[0, 64, "\!\\"\ … |\}\~"]
````````````````````````````````


Backslashes before other characters are treated as literal
backslashes:

```````````````````````````````` example Backslash escapes: 2
\→\A\a\ \3\φ\«
.
<p>\→\A\a\ \3\φ\«</p>
.
Document[0, 15]
  Paragraph[0, 15]
    Text[0, 14] chars:[0, 14, "\\u2192\A\ … 3\φ\«"]
````````````````````````````````


Escaped characters are treated as regular characters and do
not have their usual Markdown meanings:

```````````````````````````````` example Backslash escapes: 3
\*not emphasized*
\<br/> not a tag
\[not a link](/foo)
\`not code`
1\. not a list
\* not a list
\# not a heading
\[foo]: /url "not a reference"
.
<p>*not emphasized*
&lt;br/&gt; not a tag
[not a link](/foo)
`not code`
1. not a list
* not a list
# not a heading
[foo]: /url &quot;not a reference&quot;</p>
.
Document[0, 144]
  Paragraph[0, 144]
    Text[0, 17] chars:[0, 17, "\*not … ized*"]
    SoftLineBreak[17, 18]
    Text[18, 34] chars:[18, 34, "\<br/ … a tag"]
    SoftLineBreak[34, 35]
    Text[35, 54] chars:[35, 54, "\[not … /foo)"]
    SoftLineBreak[54, 55]
    Text[55, 66] chars:[55, 66, "\`not … code`"]
    SoftLineBreak[66, 67]
    Text[67, 81] chars:[67, 81, "1\. n …  list"]
    SoftLineBreak[81, 82]
    Text[82, 95] chars:[82, 95, "\* no …  list"]
    SoftLineBreak[95, 96]
    Text[96, 112] chars:[96, 112, "\# no … ading"]
    SoftLineBreak[112, 113]
    Text[113, 143] chars:[113, 143, "\[foo … ence\""]
````````````````````````````````


If a backslash is itself escaped, the following character is not:

```````````````````````````````` example Backslash escapes: 4
\\*emphasis*
.
<p>\<em>emphasis</em></p>
.
Document[0, 13]
  Paragraph[0, 13]
    Text[0, 2] chars:[0, 2, "\\"]
    Emphasis[2, 12] textOpen:[2, 3, "*"] text:[3, 11, "emphasis"] textClose:[11, 12, "*"]
      Text[3, 11] chars:[3, 11, "emphasis"]
````````````````````````````````


A backslash at the end of the line is a [hard line break]:

```````````````````````````````` example Backslash escapes: 5
foo\
bar
.
<p>foo<br />
bar</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 3] chars:[0, 3, "foo"]
    HardLineBreak[3, 5]
    Text[5, 8] chars:[5, 8, "bar"]
````````````````````````````````


Backslash escapes do not work in code blocks, code spans, autolinks, or
raw HTML:

```````````````````````````````` example Backslash escapes: 6
`` \[\` ``
.
<p><code>\[\`</code></p>
.
Document[0, 11]
  Paragraph[0, 11]
    Code[0, 10] textOpen:[0, 2, "``"] text:[2, 8, " \[\` "] textClose:[8, 10, "``"]
      Text[2, 8] chars:[2, 8, " \[\` "]
````````````````````````````````


```````````````````````````````` example Backslash escapes: 7
    \[\]
.
<pre><code>\[\]
</code></pre>
.
Document[0, 9]
  IndentedCodeBlock[4, 9]
````````````````````````````````


```````````````````````````````` example Backslash escapes: 8
~~~
\[\]
~~~
.
<pre><code>\[\]
</code></pre>
.
Document[0, 13]
  FencedCodeBlock[0, 12] open:[0, 3, "~~~"] content:[4, 9] lines[1] close:[9, 12, "~~~"]
    Text[4, 9] chars:[4, 9, "\[\]\n"]
````````````````````````````````


```````````````````````````````` example Backslash escapes: 9
<http://example.com?find=\*>
.
<p><a href="http://example.com?find=%5C*">http://example.com?find=\*</a></p>
.
Document[0, 29]
  Paragraph[0, 29]
    AutoLink[0, 28] open:[0, 1, "<"] text:[1, 27, "http://example.com?find=\*"] pageRef:[1, 27, "http://example.com?find=\*"] close:[27, 28, ">"]
````````````````````````````````


```````````````````````````````` example Backslash escapes: 10
<a href="/bar\/)">
.
<a href="/bar\/)">
.
Document[0, 19]
  HtmlBlock[0, 19]
````````````````````````````````


But they work in all other contexts, including URLs and link titles,
link references, and [info strings] in [fenced code blocks]:

```````````````````````````````` example Backslash escapes: 11
[foo](/bar\* "ti\*tle")
.
<p><a href="/bar*" title="ti*tle">foo</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    Link[0, 23] textOpen:[0, 1, "["] text:[1, 4, "foo"] textClose:[4, 5, "]"] linkOpen:[5, 6, "("] url:[6, 12, "/bar\*"] pageRef:[6, 12, "/bar\*"] titleOpen:[13, 14, "\""] title:[14, 21, "ti\*tle"] titleClose:[21, 22, "\""] linkClose:[22, 23, ")"]
      Text[1, 4] chars:[1, 4, "foo"]
````````````````````````````````


```````````````````````````````` example Backslash escapes: 12
[foo]

[foo]: /bar\* "ti\*tle"
.
<p><a href="/bar*" title="ti*tle">foo</a></p>
.
Document[0, 31]
  Paragraph[0, 6] isTrailingBlankLine
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
  Reference[7, 30] refOpen:[7, 8, "["] ref:[8, 11, "foo"] refClose:[11, 13, "]:"] url:[14, 20, "/bar\*"] titleOpen:[21, 22, "\""] title:[22, 29, "ti\*tle"] titleClose:[29, 30, "\""]
````````````````````````````````


```````````````````````````````` example Backslash escapes: 13
``` foo\+bar
foo
```
.
<pre><code class="language-foo+bar">foo
</code></pre>
.
Document[0, 21]
  FencedCodeBlock[0, 20] open:[0, 3, "```"] info:[4, 12, "foo\+bar"] content:[13, 17] lines[1] close:[17, 20, "```"]
    Text[13, 17] chars:[13, 17, "foo\n"]
````````````````````````````````



## Entity and numeric character references

All valid HTML entity references and numeric character
references, except those occuring in code blocks and code spans,
are recognized as such and treated as equivalent to the
corresponding Unicode characters.  Conforming CommonMark parsers
need not store information about whether a particular character
was represented in the source using a Unicode character or
an entity reference.

[Entity references](@) consist of `&` + any of the valid
HTML5 entity names + `;`. The
document <https://html.spec.whatwg.org/multipage/entities.json>
is used as an authoritative source for the valid entity
references and their corresponding code points.

```````````````````````````````` example Entity and numeric character references: 1
&nbsp; &amp; &copy; &AElig; &Dcaron;
&frac34; &HilbertSpace; &DifferentialD;
&ClockwiseContourIntegral; &ngE;
.
<p>  &amp; © Æ Ď
¾ ℋ ⅆ
∲ ≧̸</p>
.
Document[0, 110]
  Paragraph[0, 110]
    HtmlEntity[0, 6] "&nbsp;"
    Text[6, 7] chars:[6, 7, " "]
    HtmlEntity[7, 12] "&amp;"
    Text[12, 13] chars:[12, 13, " "]
    HtmlEntity[13, 19] "&copy;"
    Text[19, 20] chars:[19, 20, " "]
    HtmlEntity[20, 27] "&AElig;"
    Text[27, 28] chars:[27, 28, " "]
    HtmlEntity[28, 36] "&Dcaron;"
    SoftLineBreak[36, 37]
    HtmlEntity[37, 45] "&frac34;"
    Text[45, 46] chars:[45, 46, " "]
    HtmlEntity[46, 60] "&HilbertSpace;"
    Text[60, 61] chars:[60, 61, " "]
    HtmlEntity[61, 76] "&DifferentialD;"
    SoftLineBreak[76, 77]
    HtmlEntity[77, 103] "&ClockwiseContourIntegral;"
    Text[103, 104] chars:[103, 104, " "]
    HtmlEntity[104, 109] "&ngE;"
````````````````````````````````


[Decimal numeric character
references](@)
consist of `&#` + a string of 1--8 arabic digits + `;`. A
numeric character reference is parsed as the corresponding
Unicode character. Invalid Unicode code points will be replaced by
the REPLACEMENT CHARACTER (`U+FFFD`).  For security reasons,
the code point `U+0000` will also be replaced by `U+FFFD`.

```````````````````````````````` example Entity and numeric character references: 2
&#35; &#1234; &#992; &#98765432; &#0;
.
<p># Ӓ Ϡ � �</p>
.
Document[0, 38]
  Paragraph[0, 38]
    HtmlEntity[0, 5] "&#35;"
    Text[5, 6] chars:[5, 6, " "]
    HtmlEntity[6, 13] "&#1234;"
    Text[13, 14] chars:[13, 14, " "]
    HtmlEntity[14, 20] "&#992;"
    Text[20, 21] chars:[20, 21, " "]
    HtmlEntity[21, 32] "&#98765432;"
    Text[32, 33] chars:[32, 33, " "]
    HtmlEntity[33, 37] "&#0;"
````````````````````````````````


[Hexadecimal numeric character
references](@) consist of `&#` +
either `X` or `x` + a string of 1-8 hexadecimal digits + `;`.
They too are parsed as the corresponding Unicode character (this
time specified with a hexadecimal numeral instead of decimal).

```````````````````````````````` example Entity and numeric character references: 3
&#X22; &#XD06; &#xcab;
.
<p>&quot; ആ ಫ</p>
.
Document[0, 23]
  Paragraph[0, 23]
    HtmlEntity[0, 6] "&#X22;"
    Text[6, 7] chars:[6, 7, " "]
    HtmlEntity[7, 14] "&#XD06;"
    Text[14, 15] chars:[14, 15, " "]
    HtmlEntity[15, 22] "&#xcab;"
````````````````````````````````


Here are some nonentities:

```````````````````````````````` example Entity and numeric character references: 4
&nbsp &x; &#; &#x;
&ThisIsNotDefined; &hi?;
.
<p>&amp;nbsp &amp;x; &amp;#; &amp;#x;
&amp;ThisIsNotDefined; &amp;hi?;</p>
.
Document[0, 44]
  Paragraph[0, 44]
    Text[0, 18] chars:[0, 18, "&nbsp …  &#x;"]
    SoftLineBreak[18, 19]
    HtmlEntity[19, 37] "&ThisIsNotDefined;"
    Text[37, 43] chars:[37, 43, " &hi?;"]
````````````````````````````````


Although HTML5 does accept some entity references
without a trailing semicolon (such as `&copy`), these are not
recognized here, because it makes the grammar too ambiguous:

```````````````````````````````` example Entity and numeric character references: 5
&copy
.
<p>&amp;copy</p>
.
Document[0, 6]
  Paragraph[0, 6]
    Text[0, 5] chars:[0, 5, "&copy"]
````````````````````````````````


Strings that are not on the list of HTML5 named entities are not
recognized as entity references either:

```````````````````````````````` example Entity and numeric character references: 6
&MadeUpEntity;
.
<p>&amp;MadeUpEntity;</p>
.
Document[0, 15]
  Paragraph[0, 15]
    HtmlEntity[0, 14] "&MadeUpEntity;"
````````````````````````````````


Entity and numeric character references are recognized in any
context besides code spans or code blocks, including
URLs, [link titles], and [fenced code block][] [info strings]:

```````````````````````````````` example Entity and numeric character references: 7
<a href="&ouml;&ouml;.html">
.
<a href="&ouml;&ouml;.html">
.
Document[0, 29]
  HtmlBlock[0, 29]
````````````````````````````````


```````````````````````````````` example Entity and numeric character references: 8
[foo](/f&ouml;&ouml; "f&ouml;&ouml;")
.
<p><a href="/f%C3%B6%C3%B6" title="föö">foo</a></p>
.
Document[0, 38]
  Paragraph[0, 38]
    Link[0, 37] textOpen:[0, 1, "["] text:[1, 4, "foo"] textClose:[4, 5, "]"] linkOpen:[5, 6, "("] url:[6, 20, "/f&ouml;&ouml;"] pageRef:[6, 20, "/f&ouml;&ouml;"] titleOpen:[21, 22, "\""] title:[22, 35, "f&ouml;&ouml;"] titleClose:[35, 36, "\""] linkClose:[36, 37, ")"]
      Text[1, 4] chars:[1, 4, "foo"]
````````````````````````````````


```````````````````````````````` example Entity and numeric character references: 9
[foo]

[foo]: /f&ouml;&ouml; "f&ouml;&ouml;"
.
<p><a href="/f%C3%B6%C3%B6" title="föö">foo</a></p>
.
Document[0, 45]
  Paragraph[0, 6] isTrailingBlankLine
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
  Reference[7, 44] refOpen:[7, 8, "["] ref:[8, 11, "foo"] refClose:[11, 13, "]:"] url:[14, 28, "/f&ouml;&ouml;"] titleOpen:[29, 30, "\""] title:[30, 43, "f&ouml;&ouml;"] titleClose:[43, 44, "\""]
````````````````````````````````


```````````````````````````````` example Entity and numeric character references: 10
``` f&ouml;&ouml;
foo
```
.
<pre><code class="language-föö">foo
</code></pre>
.
Document[0, 26]
  FencedCodeBlock[0, 25] open:[0, 3, "```"] info:[4, 17, "f&ouml;&ouml;"] content:[18, 22] lines[1] close:[22, 25, "```"]
    Text[18, 22] chars:[18, 22, "foo\n"]
````````````````````````````````


Entity and numeric character references are treated as literal
text in code spans and code blocks:

```````````````````````````````` example Entity and numeric character references: 11
`f&ouml;&ouml;`
.
<p><code>f&amp;ouml;&amp;ouml;</code></p>
.
Document[0, 16]
  Paragraph[0, 16]
    Code[0, 15] textOpen:[0, 1, "`"] text:[1, 14, "f&oum … l;&ouml;"] textClose:[14, 15, "`"]
      Text[1, 14] chars:[1, 14, "f&oum … ouml;"]
````````````````````````````````


```````````````````````````````` example Entity and numeric character references: 12
    f&ouml;f&ouml;
.
<pre><code>f&amp;ouml;f&amp;ouml;
</code></pre>
.
Document[0, 19]
  IndentedCodeBlock[4, 19]
````````````````````````````````


## Code spans

A [backtick string](@)
is a string of one or more backtick characters (`` ` ``) that is neither
preceded nor followed by a backtick.

A [code span](@) begins with a backtick string and ends with
a backtick string of equal length.  The contents of the code span are
the characters between the two backtick strings, with leading and
trailing spaces and [line endings] removed, and
[whitespace] collapsed to single spaces.

This is a simple code span:

```````````````````````````````` example Code spans: 1
`foo`
.
<p><code>foo</code></p>
.
Document[0, 6]
  Paragraph[0, 6]
    Code[0, 5] textOpen:[0, 1, "`"] text:[1, 4, "foo"] textClose:[4, 5, "`"]
      Text[1, 4] chars:[1, 4, "foo"]
````````````````````````````````


Here two backticks are used, because the code contains a backtick.
This example also illustrates stripping of leading and trailing spaces:

```````````````````````````````` example Code spans: 2
`` foo ` bar  ``
.
<p><code>foo ` bar</code></p>
.
Document[0, 17]
  Paragraph[0, 17]
    Code[0, 16] textOpen:[0, 2, "``"] text:[2, 14, " foo  … ` bar  "] textClose:[14, 16, "``"]
      Text[2, 14] chars:[2, 14, " foo  … bar  "]
````````````````````````````````


This example shows the motivation for stripping leading and trailing
spaces:

```````````````````````````````` example Code spans: 3
` `` `
.
<p><code>``</code></p>
.
Document[0, 7]
  Paragraph[0, 7]
    Code[0, 6] textOpen:[0, 1, "`"] text:[1, 5, " `` "] textClose:[5, 6, "`"]
      Text[1, 5] chars:[1, 5, " `` "]
````````````````````````````````


[Line endings] are treated like spaces:

```````````````````````````````` example Code spans: 4
``
foo
``
.
<p><code>foo</code></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Code[0, 9] textOpen:[0, 2, "``"] text:[2, 7, "\nfoo\n"] textClose:[7, 9, "``"]
      Text[2, 7] chars:[2, 7, "\nfoo\n"]
````````````````````````````````


Interior spaces and [line endings] are collapsed into
single spaces, just as they would be by a browser:

```````````````````````````````` example Code spans: 5
`foo   bar
  baz`
.
<p><code>foo bar baz</code></p>
````````````````````````````````


Not all [Unicode whitespace] (for instance, non-breaking space) is
collapsed, however:

```````````````````````````````` example Code spans: 6
`a  b`
.
<p><code>a  b</code></p>
````````````````````````````````


Q: Why not just leave the spaces, since browsers will collapse them
anyway?  A:  Because we might be targeting a non-HTML format, and we
shouldn't rely on HTML-specific rendering assumptions.

(Existing implementations differ in their treatment of internal
spaces and [line endings].  Some, including `Markdown.pl` and
`showdown`, convert an internal [line ending] into a
`<br />` tag.  But this makes things difficult for those who like to
hard-wrap their paragraphs, since a line break in the midst of a code
span will cause an unintended line break in the output.  Others just
leave internal spaces as they are, which is fine if only HTML is being
targeted.)

```````````````````````````````` example Code spans: 7
`foo `` bar`
.
<p><code>foo `` bar</code></p>
.
Document[0, 13]
  Paragraph[0, 13]
    Code[0, 12] textOpen:[0, 1, "`"] text:[1, 11, "foo `` bar"] textClose:[11, 12, "`"]
      Text[1, 11] chars:[1, 11, "foo `` bar"]
````````````````````````````````


Note that backslash escapes do not work in code spans. All backslashes
are treated literally:

```````````````````````````````` example Code spans: 8
`foo\`bar`
.
<p><code>foo\</code>bar`</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Code[0, 6] textOpen:[0, 1, "`"] text:[1, 5, "foo\"] textClose:[5, 6, "`"]
      Text[1, 5] chars:[1, 5, "foo\"]
    Text[6, 10] chars:[6, 10, "bar`"]
````````````````````````````````


Backslash escapes are never needed, because one can always choose a
string of *n* backtick characters as delimiters, where the code does
not contain any strings of exactly *n* backtick characters.

Code span backticks have higher precedence than any other inline
constructs except HTML tags and autolinks.  Thus, for example, this is
not parsed as emphasized text, since the second `*` is part of a code
span:

```````````````````````````````` example Code spans: 9
*foo`*`
.
<p>*foo<code>*</code></p>
.
Document[0, 8]
  Paragraph[0, 8]
    Text[0, 4] chars:[0, 4, "*foo"]
    Code[4, 7] textOpen:[4, 5, "`"] text:[5, 6, "*"] textClose:[6, 7, "`"]
      Text[5, 6] chars:[5, 6, "*"]
````````````````````````````````


And this is not parsed as a link:

```````````````````````````````` example Code spans: 10
[not a `link](/foo`)
.
<p>[not a <code>link](/foo</code>)</p>
.
Document[0, 21]
  Paragraph[0, 21]
    Text[0, 7] chars:[0, 7, "[not a "]
    Code[7, 19] textOpen:[7, 8, "`"] text:[8, 18, "link](/foo"] textClose:[18, 19, "`"]
      Text[8, 18] chars:[8, 18, "link](/foo"]
    Text[19, 20] chars:[19, 20, ")"]
````````````````````````````````


Code spans, HTML tags, and autolinks have the same precedence.
Thus, this is code:

```````````````````````````````` example Code spans: 11
`<a href="`">`
.
<p><code>&lt;a href=&quot;</code>&quot;&gt;`</p>
.
Document[0, 15]
  Paragraph[0, 15]
    Code[0, 11] textOpen:[0, 1, "`"] text:[1, 10, "<a href=\""] textClose:[10, 11, "`"]
      Text[1, 10] chars:[1, 10, "<a href=\""]
    Text[11, 14] chars:[11, 14, "\">`"]
````````````````````````````````


But this is an HTML tag:

```````````````````````````````` example Code spans: 12
<a href="`">`
.
<p><a href="`">`</p>
.
Document[0, 14]
  Paragraph[0, 14]
    HtmlInline[0, 12] chars:[0, 12, "<a hr … =\"`\">"]
    Text[12, 13] chars:[12, 13, "`"]
````````````````````````````````


And this is code:

```````````````````````````````` example Code spans: 13
`<http://foo.bar.`baz>`
.
<p><code>&lt;http://foo.bar.</code>baz&gt;`</p>
.
Document[0, 24]
  Paragraph[0, 24]
    Code[0, 18] textOpen:[0, 1, "`"] text:[1, 17, "<http … ://foo.bar."] textClose:[17, 18, "`"]
      Text[1, 17] chars:[1, 17, "<http … .bar."]
    Text[18, 23] chars:[18, 23, "baz>`"]
````````````````````````````````


But this is an autolink:

```````````````````````````````` example Code spans: 14
<http://foo.bar.`baz>`
.
<p><a href="http://foo.bar.%60baz">http://foo.bar.`baz</a>`</p>
.
Document[0, 23]
  Paragraph[0, 23]
    AutoLink[0, 21] open:[0, 1, "<"] text:[1, 20, "http://foo.bar.`baz"] pageRef:[1, 20, "http://foo.bar.`baz"] close:[20, 21, ">"]
    Text[21, 22] chars:[21, 22, "`"]
````````````````````````````````


When a backtick string is not closed by a matching backtick string,
we just have literal backticks:

```````````````````````````````` example Code spans: 15
```foo``
.
<p>```foo``</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 8] chars:[0, 8, "```foo``"]
````````````````````````````````


```````````````````````````````` example Code spans: 16
`foo
.
<p>`foo</p>
.
Document[0, 5]
  Paragraph[0, 5]
    Text[0, 4] chars:[0, 4, "`foo"]
````````````````````````````````



The following case also illustrates the need for opening and
closing backtick strings to be equal in length:

```````````````````````````````` example Code spans: 17
`foo``bar``
.
<p>`foo<code>bar</code></p>
.
Document[0, 12]
  Paragraph[0, 12]
    Text[0, 4] chars:[0, 4, "`foo"]
    Code[4, 11] textOpen:[4, 6, "``"] text:[6, 9, "bar"] textClose:[9, 11, "``"]
      Text[6, 9] chars:[6, 9, "bar"]
````````````````````````````````


## Emphasis and strong emphasis

John Gruber's original [Markdown syntax
description](http://daringfireball.net/projects/markdown/syntax#em) says:

> Markdown treats asterisks (`*`) and underscores (`_`) as indicators of
> emphasis. Text wrapped with one `*` or `_` will be wrapped with an HTML
> `<em>` tag; double `*`'s or `_`'s will be wrapped with an HTML `<strong>`
> tag.

This is enough for most users, but these rules leave much undecided,
especially when it comes to nested emphasis.  The original
`Markdown.pl` test suite makes it clear that triple `***` and
`___` delimiters can be used for strong emphasis, and most
implementations have also allowed the following patterns:

``` markdown
***strong emph***
***strong** in emph*
***emph* in strong**
**in strong *emph***
*in emph **strong***
```

The following patterns are less widely supported, but the intent
is clear and they are useful (especially in contexts like bibliography
entries):

``` markdown
*emph *with emph* in it*
**strong **with strong** in it**
```

Many implementations have also restricted intraword emphasis to
the `*` forms, to avoid unwanted emphasis in words containing
internal underscores.  (It is best practice to put these in code
spans, but users often do not.)

``` markdown
internal emphasis: foo*bar*baz
no emphasis: foo_bar_baz
```

The rules given below capture all of these patterns, while allowing
for efficient parsing strategies that do not backtrack.

First, some definitions.  A [delimiter run](@) is either
a sequence of one or more `*` characters that is not preceded or
followed by a non-backslash-escaped `*` character, or a sequence
of one or more `_` characters that is not preceded or followed by
a non-backslash-escaped `_` character.

A [left-flanking delimiter run](@) is
a [delimiter run] that is (a) not followed by [Unicode whitespace],
and (b) not followed by a [punctuation character], or
preceded by [Unicode whitespace] or a [punctuation character].
For purposes of this definition, the beginning and the end of
the line count as Unicode whitespace.

A [right-flanking delimiter run](@) is
a [delimiter run] that is (a) not preceded by [Unicode whitespace],
and (b) not preceded by a [punctuation character], or
followed by [Unicode whitespace] or a [punctuation character].
For purposes of this definition, the beginning and the end of
the line count as Unicode whitespace.

Here are some examples of delimiter runs.

  - left-flanking but not right-flanking:

    ```
    ***abc
      _abc
    **"abc"
     _"abc"
    ```

  - right-flanking but not left-flanking:

    ```
     abc***
     abc_
    "abc"**
    "abc"_
    ```

  - Both left and right-flanking:

    ```
     abc***def
    "abc"_"def"
    ```

  - Neither left nor right-flanking:

    ```
    abc *** def
    a _ b
    ```

(The idea of distinguishing left-flanking and right-flanking
delimiter runs based on the character before and the character
after comes from Roopesh Chander's
[vfmd](http://www.vfmd.org/vfmd-spec/specification/#procedure-for-identifying-emphasis-tags).
vfmd uses the terminology "emphasis indicator string" instead of "delimiter
run," and its rules for distinguishing left- and right-flanking runs
are a bit more complex than the ones given here.)

The following rules define emphasis and strong emphasis:

1.  A single `*` character [can open emphasis](@)
    iff (if and only if) it is part of a [left-flanking delimiter run].

2.  A single `_` character [can open emphasis] iff
    it is part of a [left-flanking delimiter run]
    and either (a) not part of a [right-flanking delimiter run]
    or (b) part of a [right-flanking delimiter run]
    preceded by punctuation.

3.  A single `*` character [can close emphasis](@)
    iff it is part of a [right-flanking delimiter run].

4.  A single `_` character [can close emphasis] iff
    it is part of a [right-flanking delimiter run]
    and either (a) not part of a [left-flanking delimiter run]
    or (b) part of a [left-flanking delimiter run]
    followed by punctuation.

5.  A double `**` [can open strong emphasis](@)
    iff it is part of a [left-flanking delimiter run].

6.  A double `__` [can open strong emphasis] iff
    it is part of a [left-flanking delimiter run]
    and either (a) not part of a [right-flanking delimiter run]
    or (b) part of a [right-flanking delimiter run]
    preceded by punctuation.

7.  A double `**` [can close strong emphasis](@)
    iff it is part of a [right-flanking delimiter run].

8.  A double `__` [can close strong emphasis] iff
    it is part of a [right-flanking delimiter run]
    and either (a) not part of a [left-flanking delimiter run]
    or (b) part of a [left-flanking delimiter run]
    followed by punctuation.

9.  Emphasis begins with a delimiter that [can open emphasis] and ends
    with a delimiter that [can close emphasis], and that uses the same
    character (`_` or `*`) as the opening delimiter.  The
    opening and closing delimiters must belong to separate
    [delimiter runs].  If one of the delimiters can both
    open and close emphasis, then the sum of the lengths of the
    delimiter runs containing the opening and closing delimiters
    must not be a multiple of 3.

10. Strong emphasis begins with a delimiter that
    [can open strong emphasis] and ends with a delimiter that
    [can close strong emphasis], and that uses the same character
    (`_` or `*`) as the opening delimiter.  The
    opening and closing delimiters must belong to separate
    [delimiter runs].  If one of the delimiters can both open
    and close strong emphasis, then the sum of the lengths of
    the delimiter runs containing the opening and closing
    delimiters must not be a multiple of 3.

11. A literal `*` character cannot occur at the beginning or end of
    `*`-delimited emphasis or `**`-delimited strong emphasis, unless it
    is backslash-escaped.

12. A literal `_` character cannot occur at the beginning or end of
    `_`-delimited emphasis or `__`-delimited strong emphasis, unless it
    is backslash-escaped.

Where rules 1--12 above are compatible with multiple parsings,
the following principles resolve ambiguity:

13. The number of nestings should be minimized. Thus, for example,
    an interpretation `<strong>...</strong>` is always preferred to
    `<em><em>...</em></em>`.

14. An interpretation `<em><strong>...</strong></em>` is always
    preferred to `<strong><em>...</em></strong>`.

15. When two potential emphasis or strong emphasis spans overlap,
    so that the second begins before the first ends and ends after
    the first ends, the first takes precedence. Thus, for example,
    `*foo _bar* baz_` is parsed as `<em>foo _bar</em> baz_` rather
    than `*foo <em>bar* baz</em>`.

16. When there are two potential emphasis or strong emphasis spans
    with the same closing delimiter, the shorter one (the one that
    opens later) takes precedence. Thus, for example,
    `**foo **bar baz**` is parsed as `**foo <strong>bar baz</strong>`
    rather than `<strong>foo **bar baz</strong>`.

17. Inline code spans, links, images, and HTML tags group more tightly
    than emphasis.  So, when there is a choice between an interpretation
    that contains one of these elements and one that does not, the
    former always wins.  Thus, for example, `*[foo*](bar)` is
    parsed as `*<a href="bar">foo*</a>` rather than as
    `<em>[foo</em>](bar)`.

These rules can be illustrated through a series of examples.

Rule 1:

```````````````````````````````` example Emphasis and strong emphasis: 1
*foo bar*
.
<p><em>foo bar</em></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Emphasis[0, 9] textOpen:[0, 1, "*"] text:[1, 8, "foo bar"] textClose:[8, 9, "*"]
      Text[1, 8] chars:[1, 8, "foo bar"]
````````````````````````````````


This is not emphasis, because the opening `*` is followed by
whitespace, and hence not part of a [left-flanking delimiter run]:

```````````````````````````````` example Emphasis and strong emphasis: 2
a * foo bar*
.
<p>a * foo bar*</p>
.
Document[0, 13]
  Paragraph[0, 13]
    Text[0, 12] chars:[0, 12, "a * f …  bar*"]
````````````````````````````````


This is not emphasis, because the opening `*` is preceded
by an alphanumeric and followed by punctuation, and hence
not part of a [left-flanking delimiter run]:

```````````````````````````````` example Emphasis and strong emphasis: 3
a*"foo"*
.
<p>a*&quot;foo&quot;*</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 8] chars:[0, 8, "a*\"foo\"*"]
````````````````````````````````


Unicode nonbreaking spaces count as whitespace, too:

```````````````````````````````` example Emphasis and strong emphasis: 4
* a *
.
<p>* a *</p>
.
Document[0, 6]
  Paragraph[0, 6]
    Text[0, 5] chars:[0, 5, "* a *"]
````````````````````````````````


Intraword emphasis with `*` is permitted:

```````````````````````````````` example Emphasis and strong emphasis: 5
foo*bar*
.
<p>foo<em>bar</em></p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 3] chars:[0, 3, "foo"]
    Emphasis[3, 8] textOpen:[3, 4, "*"] text:[4, 7, "bar"] textClose:[7, 8, "*"]
      Text[4, 7] chars:[4, 7, "bar"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 6
5*6*78
.
<p>5<em>6</em>78</p>
.
Document[0, 7]
  Paragraph[0, 7]
    Text[0, 1] chars:[0, 1, "5"]
    Emphasis[1, 4] textOpen:[1, 2, "*"] text:[2, 3, "6"] textClose:[3, 4, "*"]
      Text[2, 3] chars:[2, 3, "6"]
    Text[4, 6] chars:[4, 6, "78"]
````````````````````````````````


Rule 2:

```````````````````````````````` example Emphasis and strong emphasis: 7
_foo bar_
.
<p><em>foo bar</em></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Emphasis[0, 9] textOpen:[0, 1, "_"] text:[1, 8, "foo bar"] textClose:[8, 9, "_"]
      Text[1, 8] chars:[1, 8, "foo bar"]
````````````````````````````````


This is not emphasis, because the opening `_` is followed by
whitespace:

```````````````````````````````` example Emphasis and strong emphasis: 8
_ foo bar_
.
<p>_ foo bar_</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 10] chars:[0, 10, "_ foo bar_"]
````````````````````````````````


This is not emphasis, because the opening `_` is preceded
by an alphanumeric and followed by punctuation:

```````````````````````````````` example Emphasis and strong emphasis: 9
a_"foo"_
.
<p>a_&quot;foo&quot;_</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 8] chars:[0, 8, "a_\"foo\"_"]
````````````````````````````````


Emphasis with `_` is not allowed inside words:

```````````````````````````````` example Emphasis and strong emphasis: 10
foo_bar_
.
<p>foo_bar_</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 8] chars:[0, 8, "foo_bar_"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 11
5_6_78
.
<p>5_6_78</p>
.
Document[0, 7]
  Paragraph[0, 7]
    Text[0, 6] chars:[0, 6, "5_6_78"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 12
пристаням_стремятся_
.
<p>пристаням_стремятся_</p>
.
Document[0, 21]
  Paragraph[0, 21]
    Text[0, 20] chars:[0, 20, "прист … ятся_"]
````````````````````````````````


Here `_` does not generate emphasis, because the first delimiter run
is right-flanking and the second left-flanking:

```````````````````````````````` example Emphasis and strong emphasis: 13
aa_"bb"_cc
.
<p>aa_&quot;bb&quot;_cc</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 10] chars:[0, 10, "aa_\"bb\"_cc"]
````````````````````````````````


This is emphasis, even though the opening delimiter is
both left- and right-flanking, because it is preceded by
punctuation:

```````````````````````````````` example Emphasis and strong emphasis: 14
foo-_(bar)_
.
<p>foo-<em>(bar)</em></p>
.
Document[0, 12]
  Paragraph[0, 12]
    Text[0, 4] chars:[0, 4, "foo-"]
    Emphasis[4, 11] textOpen:[4, 5, "_"] text:[5, 10, "(bar)"] textClose:[10, 11, "_"]
      Text[5, 10] chars:[5, 10, "(bar)"]
````````````````````````````````


Rule 3:

This is not emphasis, because the closing delimiter does
not match the opening delimiter:

```````````````````````````````` example Emphasis and strong emphasis: 15
_foo*
.
<p>_foo*</p>
.
Document[0, 6]
  Paragraph[0, 6]
    Text[0, 5] chars:[0, 5, "_foo*"]
````````````````````````````````


This is not emphasis, because the closing `*` is preceded by
whitespace:

```````````````````````````````` example Emphasis and strong emphasis: 16
*foo bar *
.
<p>*foo bar *</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 10] chars:[0, 10, "*foo bar *"]
````````````````````````````````


A newline also counts as whitespace:

```````````````````````````````` example Emphasis and strong emphasis: 17
*foo bar
*
.
<p>*foo bar
*</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 8] chars:[0, 8, "*foo bar"]
    SoftLineBreak[8, 9]
    Text[9, 10] chars:[9, 10, "*"]
````````````````````````````````


This is not emphasis, because the second `*` is
preceded by punctuation and followed by an alphanumeric
(hence it is not part of a [right-flanking delimiter run]:

```````````````````````````````` example Emphasis and strong emphasis: 18
*(*foo)
.
<p>*(*foo)</p>
.
Document[0, 8]
  Paragraph[0, 8]
    Text[0, 7] chars:[0, 7, "*(*foo)"]
````````````````````````````````


The point of this restriction is more easily appreciated
with this example:

```````````````````````````````` example Emphasis and strong emphasis: 19
*(*foo*)*
.
<p><em>(<em>foo</em>)</em></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Emphasis[0, 9] textOpen:[0, 1, "*"] text:[1, 8, "(*foo*)"] textClose:[8, 9, "*"]
      Text[1, 2] chars:[1, 2, "("]
      Emphasis[2, 7] textOpen:[2, 3, "*"] text:[3, 6, "foo"] textClose:[6, 7, "*"]
        Text[3, 6] chars:[3, 6, "foo"]
      Text[7, 8] chars:[7, 8, ")"]
````````````````````````````````


Intraword emphasis with `*` is allowed:

```````````````````````````````` example Emphasis and strong emphasis: 20
*foo*bar
.
<p><em>foo</em>bar</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Emphasis[0, 5] textOpen:[0, 1, "*"] text:[1, 4, "foo"] textClose:[4, 5, "*"]
      Text[1, 4] chars:[1, 4, "foo"]
    Text[5, 8] chars:[5, 8, "bar"]
````````````````````````````````



Rule 4:

This is not emphasis, because the closing `_` is preceded by
whitespace:

```````````````````````````````` example Emphasis and strong emphasis: 21
_foo bar _
.
<p>_foo bar _</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 10] chars:[0, 10, "_foo bar _"]
````````````````````````````````


This is not emphasis, because the second `_` is
preceded by punctuation and followed by an alphanumeric:

```````````````````````````````` example Emphasis and strong emphasis: 22
_(_foo)
.
<p>_(_foo)</p>
.
Document[0, 8]
  Paragraph[0, 8]
    Text[0, 7] chars:[0, 7, "_(_foo)"]
````````````````````````````````


This is emphasis within emphasis:

```````````````````````````````` example Emphasis and strong emphasis: 23
_(_foo_)_
.
<p><em>(<em>foo</em>)</em></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Emphasis[0, 9] textOpen:[0, 1, "_"] text:[1, 8, "(_foo_)"] textClose:[8, 9, "_"]
      Text[1, 2] chars:[1, 2, "("]
      Emphasis[2, 7] textOpen:[2, 3, "_"] text:[3, 6, "foo"] textClose:[6, 7, "_"]
        Text[3, 6] chars:[3, 6, "foo"]
      Text[7, 8] chars:[7, 8, ")"]
````````````````````````````````


Intraword emphasis is disallowed for `_`:

```````````````````````````````` example Emphasis and strong emphasis: 24
_foo_bar
.
<p>_foo_bar</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 8] chars:[0, 8, "_foo_bar"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 25
_пристаням_стремятся
.
<p>_пристаням_стремятся</p>
.
Document[0, 21]
  Paragraph[0, 21]
    Text[0, 20] chars:[0, 20, "_прис … мятся"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 26
_foo_bar_baz_
.
<p><em>foo_bar_baz</em></p>
.
Document[0, 14]
  Paragraph[0, 14]
    Emphasis[0, 13] textOpen:[0, 1, "_"] text:[1, 12, "foo_bar_baz"] textClose:[12, 13, "_"]
      Text[1, 12] chars:[1, 12, "foo_b … r_baz"]
````````````````````````````````


This is emphasis, even though the closing delimiter is
both left- and right-flanking, because it is followed by
punctuation:

```````````````````````````````` example Emphasis and strong emphasis: 27
_(bar)_.
.
<p><em>(bar)</em>.</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Emphasis[0, 7] textOpen:[0, 1, "_"] text:[1, 6, "(bar)"] textClose:[6, 7, "_"]
      Text[1, 6] chars:[1, 6, "(bar)"]
    Text[7, 8] chars:[7, 8, "."]
````````````````````````````````


Rule 5:

```````````````````````````````` example Emphasis and strong emphasis: 28
**foo bar**
.
<p><strong>foo bar</strong></p>
.
Document[0, 12]
  Paragraph[0, 12]
    StrongEmphasis[0, 11] textOpen:[0, 2, "**"] text:[2, 9, "foo bar"] textClose:[9, 11, "**"]
      Text[2, 9] chars:[2, 9, "foo bar"]
````````````````````````````````


This is not strong emphasis, because the opening delimiter is
followed by whitespace:

```````````````````````````````` example Emphasis and strong emphasis: 29
** foo bar**
.
<p>** foo bar**</p>
.
Document[0, 13]
  Paragraph[0, 13]
    Text[0, 12] chars:[0, 12, "** fo … bar**"]
````````````````````````````````


This is not strong emphasis, because the opening `**` is preceded
by an alphanumeric and followed by punctuation, and hence
not part of a [left-flanking delimiter run]:

```````````````````````````````` example Emphasis and strong emphasis: 30
a**"foo"**
.
<p>a**&quot;foo&quot;**</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 10] chars:[0, 10, "a**\"foo\"**"]
````````````````````````````````


Intraword strong emphasis with `**` is permitted:

```````````````````````````````` example Emphasis and strong emphasis: 31
foo**bar**
.
<p>foo<strong>bar</strong></p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 3] chars:[0, 3, "foo"]
    StrongEmphasis[3, 10] textOpen:[3, 5, "**"] text:[5, 8, "bar"] textClose:[8, 10, "**"]
      Text[5, 8] chars:[5, 8, "bar"]
````````````````````````````````


Rule 6:

```````````````````````````````` example Emphasis and strong emphasis: 32
__foo bar__
.
<p><strong>foo bar</strong></p>
.
Document[0, 12]
  Paragraph[0, 12]
    StrongEmphasis[0, 11] textOpen:[0, 2, "__"] text:[2, 9, "foo bar"] textClose:[9, 11, "__"]
      Text[2, 9] chars:[2, 9, "foo bar"]
````````````````````````````````


This is not strong emphasis, because the opening delimiter is
followed by whitespace:

```````````````````````````````` example Emphasis and strong emphasis: 33
__ foo bar__
.
<p>__ foo bar__</p>
.
Document[0, 13]
  Paragraph[0, 13]
    Text[0, 12] chars:[0, 12, "__ fo … bar__"]
````````````````````````````````


A newline counts as whitespace:

```````````````````````````````` example Emphasis and strong emphasis: 34
__
foo bar__
.
<p>__
foo bar__</p>
.
Document[0, 13]
  Paragraph[0, 13]
    Text[0, 2] chars:[0, 2, "__"]
    SoftLineBreak[2, 3]
    Text[3, 12] chars:[3, 12, "foo bar__"]
````````````````````````````````


This is not strong emphasis, because the opening `__` is preceded
by an alphanumeric and followed by punctuation:

```````````````````````````````` example Emphasis and strong emphasis: 35
a__"foo"__
.
<p>a__&quot;foo&quot;__</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 10] chars:[0, 10, "a__\"foo\"__"]
````````````````````````````````


Intraword strong emphasis is forbidden with `__`:

```````````````````````````````` example Emphasis and strong emphasis: 36
foo__bar__
.
<p>foo__bar__</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 10] chars:[0, 10, "foo__bar__"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 37
5__6__78
.
<p>5__6__78</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 8] chars:[0, 8, "5__6__78"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 38
пристаням__стремятся__
.
<p>пристаням__стремятся__</p>
.
Document[0, 23]
  Paragraph[0, 23]
    Text[0, 22] chars:[0, 22, "прист … тся__"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 39
__foo, __bar__, baz__
.
<p><strong>foo, <strong>bar</strong>, baz</strong></p>
.
Document[0, 22]
  Paragraph[0, 22]
    StrongEmphasis[0, 21] textOpen:[0, 2, "__"] text:[2, 19, "foo, __bar__, baz"] textClose:[19, 21, "__"]
      Text[2, 7] chars:[2, 7, "foo, "]
      StrongEmphasis[7, 14] textOpen:[7, 9, "__"] text:[9, 12, "bar"] textClose:[12, 14, "__"]
        Text[9, 12] chars:[9, 12, "bar"]
      Text[14, 19] chars:[14, 19, ", baz"]
````````````````````````````````


This is strong emphasis, even though the opening delimiter is
both left- and right-flanking, because it is preceded by
punctuation:

```````````````````````````````` example Emphasis and strong emphasis: 40
foo-__(bar)__
.
<p>foo-<strong>(bar)</strong></p>
.
Document[0, 14]
  Paragraph[0, 14]
    Text[0, 4] chars:[0, 4, "foo-"]
    StrongEmphasis[4, 13] textOpen:[4, 6, "__"] text:[6, 11, "(bar)"] textClose:[11, 13, "__"]
      Text[6, 11] chars:[6, 11, "(bar)"]
````````````````````````````````



Rule 7:

This is not strong emphasis, because the closing delimiter is preceded
by whitespace:

```````````````````````````````` example Emphasis and strong emphasis: 41
**foo bar **
.
<p>**foo bar **</p>
.
Document[0, 13]
  Paragraph[0, 13]
    Text[0, 12] chars:[0, 12, "**foo … ar **"]
````````````````````````````````


(Nor can it be interpreted as an emphasized `*foo bar *`, because of
Rule 11.)

This is not strong emphasis, because the second `**` is
preceded by punctuation and followed by an alphanumeric:

```````````````````````````````` example Emphasis and strong emphasis: 42
**(**foo)
.
<p>**(**foo)</p>
.
Document[0, 10]
  Paragraph[0, 10]
    Text[0, 9] chars:[0, 9, "**(**foo)"]
````````````````````````````````


The point of this restriction is more easily appreciated
with these examples:

```````````````````````````````` example Emphasis and strong emphasis: 43
*(**foo**)*
.
<p><em>(<strong>foo</strong>)</em></p>
.
Document[0, 12]
  Paragraph[0, 12]
    Emphasis[0, 11] textOpen:[0, 1, "*"] text:[1, 10, "(**foo**)"] textClose:[10, 11, "*"]
      Text[1, 2] chars:[1, 2, "("]
      StrongEmphasis[2, 9] textOpen:[2, 4, "**"] text:[4, 7, "foo"] textClose:[7, 9, "**"]
        Text[4, 7] chars:[4, 7, "foo"]
      Text[9, 10] chars:[9, 10, ")"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 44
**Gomphocarpus (*Gomphocarpus physocarpus*, syn.
*Asclepias physocarpa*)**
.
<p><strong>Gomphocarpus (<em>Gomphocarpus physocarpus</em>, syn.
<em>Asclepias physocarpa</em>)</strong></p>
.
Document[0, 75]
  Paragraph[0, 75]
    StrongEmphasis[0, 74] textOpen:[0, 2, "**"] text:[2, 72, "Gomphocarpus (*Gomphocarpus physocarpus*, syn.\n*Asclepias physocarpa*)"] textClose:[72, 74, "**"]
      Text[2, 16] chars:[2, 16, "Gomph … pus ("]
      Emphasis[16, 42] textOpen:[16, 17, "*"] text:[17, 41, "Gomphocarpus physocarpus"] textClose:[41, 42, "*"]
        Text[17, 41] chars:[17, 41, "Gomph … arpus"]
      Text[42, 48] chars:[42, 48, ", syn."]
      SoftLineBreak[48, 49]
      Emphasis[49, 71] textOpen:[49, 50, "*"] text:[50, 70, "Asclepias physocarpa"] textClose:[70, 71, "*"]
        Text[50, 70] chars:[50, 70, "Ascle … carpa"]
      Text[71, 72] chars:[71, 72, ")"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 45
**foo "*bar*" foo**
.
<p><strong>foo &quot;<em>bar</em>&quot; foo</strong></p>
.
Document[0, 20]
  Paragraph[0, 20]
    StrongEmphasis[0, 19] textOpen:[0, 2, "**"] text:[2, 17, "foo \"*bar*\" foo"] textClose:[17, 19, "**"]
      Text[2, 7] chars:[2, 7, "foo \""]
      Emphasis[7, 12] textOpen:[7, 8, "*"] text:[8, 11, "bar"] textClose:[11, 12, "*"]
        Text[8, 11] chars:[8, 11, "bar"]
      Text[12, 17] chars:[12, 17, "\" foo"]
````````````````````````````````


Intraword emphasis:

```````````````````````````````` example Emphasis and strong emphasis: 46
**foo**bar
.
<p><strong>foo</strong>bar</p>
.
Document[0, 11]
  Paragraph[0, 11]
    StrongEmphasis[0, 7] textOpen:[0, 2, "**"] text:[2, 5, "foo"] textClose:[5, 7, "**"]
      Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 10] chars:[7, 10, "bar"]
````````````````````````````````


Rule 8:

This is not strong emphasis, because the closing delimiter is
preceded by whitespace:

```````````````````````````````` example Emphasis and strong emphasis: 47
__foo bar __
.
<p>__foo bar __</p>
.
Document[0, 13]
  Paragraph[0, 13]
    Text[0, 12] chars:[0, 12, "__foo … ar __"]
````````````````````````````````


This is not strong emphasis, because the second `__` is
preceded by punctuation and followed by an alphanumeric:

```````````````````````````````` example Emphasis and strong emphasis: 48
__(__foo)
.
<p>__(__foo)</p>
.
Document[0, 10]
  Paragraph[0, 10]
    Text[0, 9] chars:[0, 9, "__(__foo)"]
````````````````````````````````


The point of this restriction is more easily appreciated
with this example:

```````````````````````````````` example Emphasis and strong emphasis: 49
_(__foo__)_
.
<p><em>(<strong>foo</strong>)</em></p>
.
Document[0, 12]
  Paragraph[0, 12]
    Emphasis[0, 11] textOpen:[0, 1, "_"] text:[1, 10, "(__foo__)"] textClose:[10, 11, "_"]
      Text[1, 2] chars:[1, 2, "("]
      StrongEmphasis[2, 9] textOpen:[2, 4, "__"] text:[4, 7, "foo"] textClose:[7, 9, "__"]
        Text[4, 7] chars:[4, 7, "foo"]
      Text[9, 10] chars:[9, 10, ")"]
````````````````````````````````


Intraword strong emphasis is forbidden with `__`:

```````````````````````````````` example Emphasis and strong emphasis: 50
__foo__bar
.
<p>__foo__bar</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 10] chars:[0, 10, "__foo__bar"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 51
__пристаням__стремятся
.
<p>__пристаням__стремятся</p>
.
Document[0, 23]
  Paragraph[0, 23]
    Text[0, 22] chars:[0, 22, "__при … мятся"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 52
__foo__bar__baz__
.
<p><strong>foo__bar__baz</strong></p>
.
Document[0, 18]
  Paragraph[0, 18]
    StrongEmphasis[0, 17] textOpen:[0, 2, "__"] text:[2, 15, "foo__bar__baz"] textClose:[15, 17, "__"]
      Text[2, 15] chars:[2, 15, "foo__ … __baz"]
````````````````````````````````


This is strong emphasis, even though the closing delimiter is
both left- and right-flanking, because it is followed by
punctuation:

```````````````````````````````` example Emphasis and strong emphasis: 53
__(bar)__.
.
<p><strong>(bar)</strong>.</p>
.
Document[0, 11]
  Paragraph[0, 11]
    StrongEmphasis[0, 9] textOpen:[0, 2, "__"] text:[2, 7, "(bar)"] textClose:[7, 9, "__"]
      Text[2, 7] chars:[2, 7, "(bar)"]
    Text[9, 10] chars:[9, 10, "."]
````````````````````````````````


Rule 9:

Any nonempty sequence of inline elements can be the contents of an
emphasized span.

```````````````````````````````` example Emphasis and strong emphasis: 54
*foo [bar](/url)*
.
<p><em>foo <a href="/url">bar</a></em></p>
.
Document[0, 18]
  Paragraph[0, 18]
    Emphasis[0, 17] textOpen:[0, 1, "*"] text:[1, 16, "foo [bar](/url)"] textClose:[16, 17, "*"]
      Text[1, 5] chars:[1, 5, "foo "]
      Link[5, 16] textOpen:[5, 6, "["] text:[6, 9, "bar"] textClose:[9, 10, "]"] linkOpen:[10, 11, "("] url:[11, 15, "/url"] pageRef:[11, 15, "/url"] linkClose:[15, 16, ")"]
        Text[6, 9] chars:[6, 9, "bar"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 55
*foo
bar*
.
<p><em>foo
bar</em></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Emphasis[0, 9] textOpen:[0, 1, "*"] text:[1, 8, "foo\nbar"] textClose:[8, 9, "*"]
      Text[1, 4] chars:[1, 4, "foo"]
      SoftLineBreak[4, 5]
      Text[5, 8] chars:[5, 8, "bar"]
````````````````````````````````


In particular, emphasis and strong emphasis can be nested
inside emphasis:

```````````````````````````````` example Emphasis and strong emphasis: 56
_foo __bar__ baz_
.
<p><em>foo <strong>bar</strong> baz</em></p>
.
Document[0, 18]
  Paragraph[0, 18]
    Emphasis[0, 17] textOpen:[0, 1, "_"] text:[1, 16, "foo __bar__ baz"] textClose:[16, 17, "_"]
      Text[1, 5] chars:[1, 5, "foo "]
      StrongEmphasis[5, 12] textOpen:[5, 7, "__"] text:[7, 10, "bar"] textClose:[10, 12, "__"]
        Text[7, 10] chars:[7, 10, "bar"]
      Text[12, 16] chars:[12, 16, " baz"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 57
_foo _bar_ baz_
.
<p><em>foo <em>bar</em> baz</em></p>
.
Document[0, 16]
  Paragraph[0, 16]
    Emphasis[0, 15] textOpen:[0, 1, "_"] text:[1, 14, "foo _bar_ baz"] textClose:[14, 15, "_"]
      Text[1, 5] chars:[1, 5, "foo "]
      Emphasis[5, 10] textOpen:[5, 6, "_"] text:[6, 9, "bar"] textClose:[9, 10, "_"]
        Text[6, 9] chars:[6, 9, "bar"]
      Text[10, 14] chars:[10, 14, " baz"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 58
__foo_ bar_
.
<p><em><em>foo</em> bar</em></p>
.
Document[0, 12]
  Paragraph[0, 12]
    Emphasis[0, 11] textOpen:[0, 1, "_"] text:[1, 10, "_foo_ bar"] textClose:[10, 11, "_"]
      Emphasis[1, 6] textOpen:[1, 2, "_"] text:[2, 5, "foo"] textClose:[5, 6, "_"]
        Text[2, 5] chars:[2, 5, "foo"]
      Text[6, 10] chars:[6, 10, " bar"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 59
*foo *bar**
.
<p><em>foo <em>bar</em></em></p>
.
Document[0, 12]
  Paragraph[0, 12]
    Emphasis[0, 11] textOpen:[0, 1, "*"] text:[1, 10, "foo *bar*"] textClose:[10, 11, "*"]
      Text[1, 5] chars:[1, 5, "foo "]
      Emphasis[5, 10] textOpen:[5, 6, "*"] text:[6, 9, "bar"] textClose:[9, 10, "*"]
        Text[6, 9] chars:[6, 9, "bar"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 60
*foo **bar** baz*
.
<p><em>foo <strong>bar</strong> baz</em></p>
.
Document[0, 18]
  Paragraph[0, 18]
    Emphasis[0, 17] textOpen:[0, 1, "*"] text:[1, 16, "foo **bar** baz"] textClose:[16, 17, "*"]
      Text[1, 5] chars:[1, 5, "foo "]
      StrongEmphasis[5, 12] textOpen:[5, 7, "**"] text:[7, 10, "bar"] textClose:[10, 12, "**"]
        Text[7, 10] chars:[7, 10, "bar"]
      Text[12, 16] chars:[12, 16, " baz"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 61
*foo**bar**baz*
.
<p><em>foo<strong>bar</strong>baz</em></p>
.
Document[0, 16]
  Paragraph[0, 16]
    Emphasis[0, 15] textOpen:[0, 1, "*"] text:[1, 14, "foo**bar**baz"] textClose:[14, 15, "*"]
      Text[1, 4] chars:[1, 4, "foo"]
      StrongEmphasis[4, 11] textOpen:[4, 6, "**"] text:[6, 9, "bar"] textClose:[9, 11, "**"]
        Text[6, 9] chars:[6, 9, "bar"]
      Text[11, 14] chars:[11, 14, "baz"]
````````````````````````````````


Note that in the preceding case, the interpretation

``` markdown
<p><em>foo</em><em>bar<em></em>baz</em></p>
```


is precluded by the condition that a delimiter that
can both open and close (like the `*` after `foo`)
cannot form emphasis if the sum of the lengths of
the delimiter runs containing the opening and
closing delimiters is a multiple of 3.

The same condition ensures that the following
cases are all strong emphasis nested inside
emphasis, even when the interior spaces are
omitted:


```````````````````````````````` example Emphasis and strong emphasis: 62
***foo** bar*
.
<p><em><strong>foo</strong> bar</em></p>
.
Document[0, 14]
  Paragraph[0, 14]
    Emphasis[0, 13] textOpen:[0, 1, "*"] text:[1, 12, "**foo** bar"] textClose:[12, 13, "*"]
      StrongEmphasis[1, 8] textOpen:[1, 3, "**"] text:[3, 6, "foo"] textClose:[6, 8, "**"]
        Text[3, 6] chars:[3, 6, "foo"]
      Text[8, 12] chars:[8, 12, " bar"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 63
*foo **bar***
.
<p><em>foo <strong>bar</strong></em></p>
.
Document[0, 14]
  Paragraph[0, 14]
    Emphasis[0, 13] textOpen:[0, 1, "*"] text:[1, 12, "foo **bar**"] textClose:[12, 13, "*"]
      Text[1, 5] chars:[1, 5, "foo "]
      StrongEmphasis[5, 12] textOpen:[5, 7, "**"] text:[7, 10, "bar"] textClose:[10, 12, "**"]
        Text[7, 10] chars:[7, 10, "bar"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 64
*foo**bar***
.
<p><em>foo<strong>bar</strong></em></p>
.
Document[0, 13]
  Paragraph[0, 13]
    Emphasis[0, 12] textOpen:[0, 1, "*"] text:[1, 11, "foo**bar**"] textClose:[11, 12, "*"]
      Text[1, 4] chars:[1, 4, "foo"]
      StrongEmphasis[4, 11] textOpen:[4, 6, "**"] text:[6, 9, "bar"] textClose:[9, 11, "**"]
        Text[6, 9] chars:[6, 9, "bar"]
````````````````````````````````


Indefinite levels of nesting are possible:

```````````````````````````````` example Emphasis and strong emphasis: 65
*foo **bar *baz* bim** bop*
.
<p><em>foo <strong>bar <em>baz</em> bim</strong> bop</em></p>
.
Document[0, 28]
  Paragraph[0, 28]
    Emphasis[0, 27] textOpen:[0, 1, "*"] text:[1, 26, "foo **bar *baz* bim** bop"] textClose:[26, 27, "*"]
      Text[1, 5] chars:[1, 5, "foo "]
      StrongEmphasis[5, 22] textOpen:[5, 7, "**"] text:[7, 20, "bar *baz* bim"] textClose:[20, 22, "**"]
        Text[7, 11] chars:[7, 11, "bar "]
        Emphasis[11, 16] textOpen:[11, 12, "*"] text:[12, 15, "baz"] textClose:[15, 16, "*"]
          Text[12, 15] chars:[12, 15, "baz"]
        Text[16, 20] chars:[16, 20, " bim"]
      Text[22, 26] chars:[22, 26, " bop"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 66
*foo [*bar*](/url)*
.
<p><em>foo <a href="/url"><em>bar</em></a></em></p>
.
Document[0, 20]
  Paragraph[0, 20]
    Emphasis[0, 19] textOpen:[0, 1, "*"] text:[1, 18, "foo [*bar*](/url)"] textClose:[18, 19, "*"]
      Text[1, 5] chars:[1, 5, "foo "]
      Link[5, 18] textOpen:[5, 6, "["] text:[6, 11, "*bar*"] textClose:[11, 12, "]"] linkOpen:[12, 13, "("] url:[13, 17, "/url"] pageRef:[13, 17, "/url"] linkClose:[17, 18, ")"]
        Emphasis[6, 11] textOpen:[6, 7, "*"] text:[7, 10, "bar"] textClose:[10, 11, "*"]
          Text[7, 10] chars:[7, 10, "bar"]
````````````````````````````````


There can be no empty emphasis or strong emphasis:

```````````````````````````````` example Emphasis and strong emphasis: 67
** is not an empty emphasis
.
<p>** is not an empty emphasis</p>
.
Document[0, 28]
  Paragraph[0, 28]
    Text[0, 27] chars:[0, 27, "** is … hasis"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 68
**** is not an empty strong emphasis
.
<p>**** is not an empty strong emphasis</p>
.
Document[0, 37]
  Paragraph[0, 37]
    Text[0, 36] chars:[0, 36, "****  … hasis"]
````````````````````````````````



Rule 10:

Any nonempty sequence of inline elements can be the contents of an
strongly emphasized span.

```````````````````````````````` example Emphasis and strong emphasis: 69
**foo [bar](/url)**
.
<p><strong>foo <a href="/url">bar</a></strong></p>
.
Document[0, 20]
  Paragraph[0, 20]
    StrongEmphasis[0, 19] textOpen:[0, 2, "**"] text:[2, 17, "foo [bar](/url)"] textClose:[17, 19, "**"]
      Text[2, 6] chars:[2, 6, "foo "]
      Link[6, 17] textOpen:[6, 7, "["] text:[7, 10, "bar"] textClose:[10, 11, "]"] linkOpen:[11, 12, "("] url:[12, 16, "/url"] pageRef:[12, 16, "/url"] linkClose:[16, 17, ")"]
        Text[7, 10] chars:[7, 10, "bar"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 70
**foo
bar**
.
<p><strong>foo
bar</strong></p>
.
Document[0, 12]
  Paragraph[0, 12]
    StrongEmphasis[0, 11] textOpen:[0, 2, "**"] text:[2, 9, "foo\nbar"] textClose:[9, 11, "**"]
      Text[2, 5] chars:[2, 5, "foo"]
      SoftLineBreak[5, 6]
      Text[6, 9] chars:[6, 9, "bar"]
````````````````````````````````


In particular, emphasis and strong emphasis can be nested
inside strong emphasis:

```````````````````````````````` example Emphasis and strong emphasis: 71
__foo _bar_ baz__
.
<p><strong>foo <em>bar</em> baz</strong></p>
.
Document[0, 18]
  Paragraph[0, 18]
    StrongEmphasis[0, 17] textOpen:[0, 2, "__"] text:[2, 15, "foo _bar_ baz"] textClose:[15, 17, "__"]
      Text[2, 6] chars:[2, 6, "foo "]
      Emphasis[6, 11] textOpen:[6, 7, "_"] text:[7, 10, "bar"] textClose:[10, 11, "_"]
        Text[7, 10] chars:[7, 10, "bar"]
      Text[11, 15] chars:[11, 15, " baz"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 72
__foo __bar__ baz__
.
<p><strong>foo <strong>bar</strong> baz</strong></p>
.
Document[0, 20]
  Paragraph[0, 20]
    StrongEmphasis[0, 19] textOpen:[0, 2, "__"] text:[2, 17, "foo __bar__ baz"] textClose:[17, 19, "__"]
      Text[2, 6] chars:[2, 6, "foo "]
      StrongEmphasis[6, 13] textOpen:[6, 8, "__"] text:[8, 11, "bar"] textClose:[11, 13, "__"]
        Text[8, 11] chars:[8, 11, "bar"]
      Text[13, 17] chars:[13, 17, " baz"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 73
____foo__ bar__
.
<p><strong><strong>foo</strong> bar</strong></p>
.
Document[0, 16]
  Paragraph[0, 16]
    StrongEmphasis[0, 15] textOpen:[0, 2, "__"] text:[2, 13, "__foo__ bar"] textClose:[13, 15, "__"]
      StrongEmphasis[2, 9] textOpen:[2, 4, "__"] text:[4, 7, "foo"] textClose:[7, 9, "__"]
        Text[4, 7] chars:[4, 7, "foo"]
      Text[9, 13] chars:[9, 13, " bar"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 74
**foo **bar****
.
<p><strong>foo <strong>bar</strong></strong></p>
.
Document[0, 16]
  Paragraph[0, 16]
    StrongEmphasis[0, 15] textOpen:[0, 2, "**"] text:[2, 13, "foo **bar**"] textClose:[13, 15, "**"]
      Text[2, 6] chars:[2, 6, "foo "]
      StrongEmphasis[6, 13] textOpen:[6, 8, "**"] text:[8, 11, "bar"] textClose:[11, 13, "**"]
        Text[8, 11] chars:[8, 11, "bar"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 75
**foo *bar* baz**
.
<p><strong>foo <em>bar</em> baz</strong></p>
.
Document[0, 18]
  Paragraph[0, 18]
    StrongEmphasis[0, 17] textOpen:[0, 2, "**"] text:[2, 15, "foo *bar* baz"] textClose:[15, 17, "**"]
      Text[2, 6] chars:[2, 6, "foo "]
      Emphasis[6, 11] textOpen:[6, 7, "*"] text:[7, 10, "bar"] textClose:[10, 11, "*"]
        Text[7, 10] chars:[7, 10, "bar"]
      Text[11, 15] chars:[11, 15, " baz"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 76
**foo*bar*baz**
.
<p><strong>foo<em>bar</em>baz</strong></p>
.
Document[0, 16]
  Paragraph[0, 16]
    StrongEmphasis[0, 15] textOpen:[0, 2, "**"] text:[2, 13, "foo*bar*baz"] textClose:[13, 15, "**"]
      Text[2, 5] chars:[2, 5, "foo"]
      Emphasis[5, 10] textOpen:[5, 6, "*"] text:[6, 9, "bar"] textClose:[9, 10, "*"]
        Text[6, 9] chars:[6, 9, "bar"]
      Text[10, 13] chars:[10, 13, "baz"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 77
***foo* bar**
.
<p><strong><em>foo</em> bar</strong></p>
.
Document[0, 14]
  Paragraph[0, 14]
    StrongEmphasis[0, 13] textOpen:[0, 2, "**"] text:[2, 11, "*foo* bar"] textClose:[11, 13, "**"]
      Emphasis[2, 7] textOpen:[2, 3, "*"] text:[3, 6, "foo"] textClose:[6, 7, "*"]
        Text[3, 6] chars:[3, 6, "foo"]
      Text[7, 11] chars:[7, 11, " bar"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 78
**foo *bar***
.
<p><strong>foo <em>bar</em></strong></p>
.
Document[0, 14]
  Paragraph[0, 14]
    StrongEmphasis[0, 13] textOpen:[0, 2, "**"] text:[2, 11, "foo *bar*"] textClose:[11, 13, "**"]
      Text[2, 6] chars:[2, 6, "foo "]
      Emphasis[6, 11] textOpen:[6, 7, "*"] text:[7, 10, "bar"] textClose:[10, 11, "*"]
        Text[7, 10] chars:[7, 10, "bar"]
````````````````````````````````


Indefinite levels of nesting are possible:

```````````````````````````````` example Emphasis and strong emphasis: 79
**foo *bar **baz**
bim* bop**
.
<p><strong>foo <em>bar <strong>baz</strong>
bim</em> bop</strong></p>
.
Document[0, 30]
  Paragraph[0, 30]
    StrongEmphasis[0, 29] textOpen:[0, 2, "**"] text:[2, 27, "foo *bar **baz**\nbim* bop"] textClose:[27, 29, "**"]
      Text[2, 6] chars:[2, 6, "foo "]
      Emphasis[6, 23] textOpen:[6, 7, "*"] text:[7, 22, "bar **baz**\nbim"] textClose:[22, 23, "*"]
        Text[7, 11] chars:[7, 11, "bar "]
        StrongEmphasis[11, 18] textOpen:[11, 13, "**"] text:[13, 16, "baz"] textClose:[16, 18, "**"]
          Text[13, 16] chars:[13, 16, "baz"]
        SoftLineBreak[18, 19]
        Text[19, 22] chars:[19, 22, "bim"]
      Text[23, 27] chars:[23, 27, " bop"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 80
**foo [*bar*](/url)**
.
<p><strong>foo <a href="/url"><em>bar</em></a></strong></p>
.
Document[0, 22]
  Paragraph[0, 22]
    StrongEmphasis[0, 21] textOpen:[0, 2, "**"] text:[2, 19, "foo [*bar*](/url)"] textClose:[19, 21, "**"]
      Text[2, 6] chars:[2, 6, "foo "]
      Link[6, 19] textOpen:[6, 7, "["] text:[7, 12, "*bar*"] textClose:[12, 13, "]"] linkOpen:[13, 14, "("] url:[14, 18, "/url"] pageRef:[14, 18, "/url"] linkClose:[18, 19, ")"]
        Emphasis[7, 12] textOpen:[7, 8, "*"] text:[8, 11, "bar"] textClose:[11, 12, "*"]
          Text[8, 11] chars:[8, 11, "bar"]
````````````````````````````````


There can be no empty emphasis or strong emphasis:

```````````````````````````````` example Emphasis and strong emphasis: 81
__ is not an empty emphasis
.
<p>__ is not an empty emphasis</p>
.
Document[0, 28]
  Paragraph[0, 28]
    Text[0, 27] chars:[0, 27, "__ is … hasis"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 82
____ is not an empty strong emphasis
.
<p>____ is not an empty strong emphasis</p>
.
Document[0, 37]
  Paragraph[0, 37]
    Text[0, 36] chars:[0, 36, "____  … hasis"]
````````````````````````````````



Rule 11:

```````````````````````````````` example Emphasis and strong emphasis: 83
foo ***
.
<p>foo ***</p>
.
Document[0, 8]
  Paragraph[0, 8]
    Text[0, 7] chars:[0, 7, "foo ***"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 84
foo *\**
.
<p>foo <em>*</em></p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 4] chars:[0, 4, "foo "]
    Emphasis[4, 8] textOpen:[4, 5, "*"] text:[5, 7, "\*"] textClose:[7, 8, "*"]
      Text[5, 7] chars:[5, 7, "\*"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 85
foo *_*
.
<p>foo <em>_</em></p>
.
Document[0, 8]
  Paragraph[0, 8]
    Text[0, 4] chars:[0, 4, "foo "]
    Emphasis[4, 7] textOpen:[4, 5, "*"] text:[5, 6, "_"] textClose:[6, 7, "*"]
      Text[5, 6] chars:[5, 6, "_"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 86
foo *****
.
<p>foo *****</p>
.
Document[0, 10]
  Paragraph[0, 10]
    Text[0, 9] chars:[0, 9, "foo *****"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 87
foo **\***
.
<p>foo <strong>*</strong></p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 4] chars:[0, 4, "foo "]
    StrongEmphasis[4, 10] textOpen:[4, 6, "**"] text:[6, 8, "\*"] textClose:[8, 10, "**"]
      Text[6, 8] chars:[6, 8, "\*"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 88
foo **_**
.
<p>foo <strong>_</strong></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Text[0, 4] chars:[0, 4, "foo "]
    StrongEmphasis[4, 9] textOpen:[4, 6, "**"] text:[6, 7, "_"] textClose:[7, 9, "**"]
      Text[6, 7] chars:[6, 7, "_"]
````````````````````````````````


Note that when delimiters do not match evenly, Rule 11 determines
that the excess literal `*` characters will appear outside of the
emphasis, rather than inside it:

```````````````````````````````` example Emphasis and strong emphasis: 89
**foo*
.
<p>*<em>foo</em></p>
.
Document[0, 7]
  Paragraph[0, 7]
    Text[0, 1] chars:[0, 1, "*"]
    Emphasis[1, 6] textOpen:[1, 2, "*"] text:[2, 5, "foo"] textClose:[5, 6, "*"]
      Text[2, 5] chars:[2, 5, "foo"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 90
*foo**
.
<p><em>foo</em>*</p>
.
Document[0, 7]
  Paragraph[0, 7]
    Emphasis[0, 5] textOpen:[0, 1, "*"] text:[1, 4, "foo"] textClose:[4, 5, "*"]
      Text[1, 4] chars:[1, 4, "foo"]
    Text[5, 6] chars:[5, 6, "*"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 91
***foo**
.
<p>*<strong>foo</strong></p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 1] chars:[0, 1, "*"]
    StrongEmphasis[1, 8] textOpen:[1, 3, "**"] text:[3, 6, "foo"] textClose:[6, 8, "**"]
      Text[3, 6] chars:[3, 6, "foo"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 92
****foo*
.
<p>***<em>foo</em></p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 3] chars:[0, 3, "***"]
    Emphasis[3, 8] textOpen:[3, 4, "*"] text:[4, 7, "foo"] textClose:[7, 8, "*"]
      Text[4, 7] chars:[4, 7, "foo"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 93
**foo***
.
<p><strong>foo</strong>*</p>
.
Document[0, 9]
  Paragraph[0, 9]
    StrongEmphasis[0, 7] textOpen:[0, 2, "**"] text:[2, 5, "foo"] textClose:[5, 7, "**"]
      Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 8] chars:[7, 8, "*"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 94
*foo****
.
<p><em>foo</em>***</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Emphasis[0, 5] textOpen:[0, 1, "*"] text:[1, 4, "foo"] textClose:[4, 5, "*"]
      Text[1, 4] chars:[1, 4, "foo"]
    Text[5, 8] chars:[5, 8, "***"]
````````````````````````````````



Rule 12:

```````````````````````````````` example Emphasis and strong emphasis: 95
foo ___
.
<p>foo ___</p>
.
Document[0, 8]
  Paragraph[0, 8]
    Text[0, 7] chars:[0, 7, "foo ___"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 96
foo _\__
.
<p>foo <em>_</em></p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 4] chars:[0, 4, "foo "]
    Emphasis[4, 8] textOpen:[4, 5, "_"] text:[5, 7, "\_"] textClose:[7, 8, "_"]
      Text[5, 7] chars:[5, 7, "\_"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 97
foo _*_
.
<p>foo <em>*</em></p>
.
Document[0, 8]
  Paragraph[0, 8]
    Text[0, 4] chars:[0, 4, "foo "]
    Emphasis[4, 7] textOpen:[4, 5, "_"] text:[5, 6, "*"] textClose:[6, 7, "_"]
      Text[5, 6] chars:[5, 6, "*"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 98
foo _____
.
<p>foo _____</p>
.
Document[0, 10]
  Paragraph[0, 10]
    Text[0, 9] chars:[0, 9, "foo _____"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 99
foo __\___
.
<p>foo <strong>_</strong></p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 4] chars:[0, 4, "foo "]
    StrongEmphasis[4, 10] textOpen:[4, 6, "__"] text:[6, 8, "\_"] textClose:[8, 10, "__"]
      Text[6, 8] chars:[6, 8, "\_"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 100
foo __*__
.
<p>foo <strong>*</strong></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Text[0, 4] chars:[0, 4, "foo "]
    StrongEmphasis[4, 9] textOpen:[4, 6, "__"] text:[6, 7, "*"] textClose:[7, 9, "__"]
      Text[6, 7] chars:[6, 7, "*"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 101
__foo_
.
<p>_<em>foo</em></p>
.
Document[0, 7]
  Paragraph[0, 7]
    Text[0, 1] chars:[0, 1, "_"]
    Emphasis[1, 6] textOpen:[1, 2, "_"] text:[2, 5, "foo"] textClose:[5, 6, "_"]
      Text[2, 5] chars:[2, 5, "foo"]
````````````````````````````````


Note that when delimiters do not match evenly, Rule 12 determines
that the excess literal `_` characters will appear outside of the
emphasis, rather than inside it:

```````````````````````````````` example Emphasis and strong emphasis: 102
_foo__
.
<p><em>foo</em>_</p>
.
Document[0, 7]
  Paragraph[0, 7]
    Emphasis[0, 5] textOpen:[0, 1, "_"] text:[1, 4, "foo"] textClose:[4, 5, "_"]
      Text[1, 4] chars:[1, 4, "foo"]
    Text[5, 6] chars:[5, 6, "_"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 103
___foo__
.
<p>_<strong>foo</strong></p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 1] chars:[0, 1, "_"]
    StrongEmphasis[1, 8] textOpen:[1, 3, "__"] text:[3, 6, "foo"] textClose:[6, 8, "__"]
      Text[3, 6] chars:[3, 6, "foo"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 104
____foo_
.
<p>___<em>foo</em></p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 3] chars:[0, 3, "___"]
    Emphasis[3, 8] textOpen:[3, 4, "_"] text:[4, 7, "foo"] textClose:[7, 8, "_"]
      Text[4, 7] chars:[4, 7, "foo"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 105
__foo___
.
<p><strong>foo</strong>_</p>
.
Document[0, 9]
  Paragraph[0, 9]
    StrongEmphasis[0, 7] textOpen:[0, 2, "__"] text:[2, 5, "foo"] textClose:[5, 7, "__"]
      Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 8] chars:[7, 8, "_"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 106
_foo____
.
<p><em>foo</em>___</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Emphasis[0, 5] textOpen:[0, 1, "_"] text:[1, 4, "foo"] textClose:[4, 5, "_"]
      Text[1, 4] chars:[1, 4, "foo"]
    Text[5, 8] chars:[5, 8, "___"]
````````````````````````````````


Rule 13 implies that if you want emphasis nested directly inside
emphasis, you must use different delimiters:

```````````````````````````````` example Emphasis and strong emphasis: 107
**foo**
.
<p><strong>foo</strong></p>
.
Document[0, 8]
  Paragraph[0, 8]
    StrongEmphasis[0, 7] textOpen:[0, 2, "**"] text:[2, 5, "foo"] textClose:[5, 7, "**"]
      Text[2, 5] chars:[2, 5, "foo"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 108
*_foo_*
.
<p><em><em>foo</em></em></p>
.
Document[0, 8]
  Paragraph[0, 8]
    Emphasis[0, 7] textOpen:[0, 1, "*"] text:[1, 6, "_foo_"] textClose:[6, 7, "*"]
      Emphasis[1, 6] textOpen:[1, 2, "_"] text:[2, 5, "foo"] textClose:[5, 6, "_"]
        Text[2, 5] chars:[2, 5, "foo"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 109
__foo__
.
<p><strong>foo</strong></p>
.
Document[0, 8]
  Paragraph[0, 8]
    StrongEmphasis[0, 7] textOpen:[0, 2, "__"] text:[2, 5, "foo"] textClose:[5, 7, "__"]
      Text[2, 5] chars:[2, 5, "foo"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 110
_*foo*_
.
<p><em><em>foo</em></em></p>
.
Document[0, 8]
  Paragraph[0, 8]
    Emphasis[0, 7] textOpen:[0, 1, "_"] text:[1, 6, "*foo*"] textClose:[6, 7, "_"]
      Emphasis[1, 6] textOpen:[1, 2, "*"] text:[2, 5, "foo"] textClose:[5, 6, "*"]
        Text[2, 5] chars:[2, 5, "foo"]
````````````````````````````````


However, strong emphasis within strong emphasis is possible without
switching delimiters:

```````````````````````````````` example Emphasis and strong emphasis: 111
****foo****
.
<p><strong><strong>foo</strong></strong></p>
.
Document[0, 12]
  Paragraph[0, 12]
    StrongEmphasis[0, 11] textOpen:[0, 2, "**"] text:[2, 9, "**foo**"] textClose:[9, 11, "**"]
      StrongEmphasis[2, 9] textOpen:[2, 4, "**"] text:[4, 7, "foo"] textClose:[7, 9, "**"]
        Text[4, 7] chars:[4, 7, "foo"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 112
____foo____
.
<p><strong><strong>foo</strong></strong></p>
.
Document[0, 12]
  Paragraph[0, 12]
    StrongEmphasis[0, 11] textOpen:[0, 2, "__"] text:[2, 9, "__foo__"] textClose:[9, 11, "__"]
      StrongEmphasis[2, 9] textOpen:[2, 4, "__"] text:[4, 7, "foo"] textClose:[7, 9, "__"]
        Text[4, 7] chars:[4, 7, "foo"]
````````````````````````````````



Rule 13 can be applied to arbitrarily long sequences of
delimiters:

```````````````````````````````` example Emphasis and strong emphasis: 113
******foo******
.
<p><strong><strong><strong>foo</strong></strong></strong></p>
.
Document[0, 16]
  Paragraph[0, 16]
    StrongEmphasis[0, 15] textOpen:[0, 2, "**"] text:[2, 13, "****foo****"] textClose:[13, 15, "**"]
      StrongEmphasis[2, 13] textOpen:[2, 4, "**"] text:[4, 11, "**foo**"] textClose:[11, 13, "**"]
        StrongEmphasis[4, 11] textOpen:[4, 6, "**"] text:[6, 9, "foo"] textClose:[9, 11, "**"]
          Text[6, 9] chars:[6, 9, "foo"]
````````````````````````````````


Rule 14:

```````````````````````````````` example Emphasis and strong emphasis: 114
***foo***
.
<p><em><strong>foo</strong></em></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Emphasis[0, 9] textOpen:[0, 1, "*"] text:[1, 8, "**foo**"] textClose:[8, 9, "*"]
      StrongEmphasis[1, 8] textOpen:[1, 3, "**"] text:[3, 6, "foo"] textClose:[6, 8, "**"]
        Text[3, 6] chars:[3, 6, "foo"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 115
_____foo_____
.
<p><em><strong><strong>foo</strong></strong></em></p>
.
Document[0, 14]
  Paragraph[0, 14]
    Emphasis[0, 13] textOpen:[0, 1, "_"] text:[1, 12, "____foo____"] textClose:[12, 13, "_"]
      StrongEmphasis[1, 12] textOpen:[1, 3, "__"] text:[3, 10, "__foo__"] textClose:[10, 12, "__"]
        StrongEmphasis[3, 10] textOpen:[3, 5, "__"] text:[5, 8, "foo"] textClose:[8, 10, "__"]
          Text[5, 8] chars:[5, 8, "foo"]
````````````````````````````````


Rule 15:

```````````````````````````````` example Emphasis and strong emphasis: 116
*foo _bar* baz_
.
<p><em>foo _bar</em> baz_</p>
.
Document[0, 16]
  Paragraph[0, 16]
    Emphasis[0, 10] textOpen:[0, 1, "*"] text:[1, 9, "foo _bar"] textClose:[9, 10, "*"]
      Text[1, 9] chars:[1, 9, "foo _bar"]
    Text[10, 15] chars:[10, 15, " baz_"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 117
*foo __bar *baz bim__ bam*
.
<p><em>foo <strong>bar *baz bim</strong> bam</em></p>
.
Document[0, 27]
  Paragraph[0, 27]
    Emphasis[0, 26] textOpen:[0, 1, "*"] text:[1, 25, "foo __bar *baz bim__ bam"] textClose:[25, 26, "*"]
      Text[1, 5] chars:[1, 5, "foo "]
      StrongEmphasis[5, 21] textOpen:[5, 7, "__"] text:[7, 19, "bar *baz bim"] textClose:[19, 21, "__"]
        Text[7, 19] chars:[7, 19, "bar * … z bim"]
      Text[21, 25] chars:[21, 25, " bam"]
````````````````````````````````


Rule 16:

```````````````````````````````` example Emphasis and strong emphasis: 118
**foo **bar baz**
.
<p>**foo <strong>bar baz</strong></p>
.
Document[0, 18]
  Paragraph[0, 18]
    Text[0, 6] chars:[0, 6, "**foo "]
    StrongEmphasis[6, 17] textOpen:[6, 8, "**"] text:[8, 15, "bar baz"] textClose:[15, 17, "**"]
      Text[8, 15] chars:[8, 15, "bar baz"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 119
*foo *bar baz*
.
<p>*foo <em>bar baz</em></p>
.
Document[0, 15]
  Paragraph[0, 15]
    Text[0, 5] chars:[0, 5, "*foo "]
    Emphasis[5, 14] textOpen:[5, 6, "*"] text:[6, 13, "bar baz"] textClose:[13, 14, "*"]
      Text[6, 13] chars:[6, 13, "bar baz"]
````````````````````````````````


Rule 17:

```````````````````````````````` example Emphasis and strong emphasis: 120
*[bar*](/url)
.
<p>*<a href="/url">bar*</a></p>
.
Document[0, 14]
  Paragraph[0, 14]
    Text[0, 1] chars:[0, 1, "*"]
    Link[1, 13] textOpen:[1, 2, "["] text:[2, 6, "bar*"] textClose:[6, 7, "]"] linkOpen:[7, 8, "("] url:[8, 12, "/url"] pageRef:[8, 12, "/url"] linkClose:[12, 13, ")"]
      Text[2, 6] chars:[2, 6, "bar*"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 121
_foo [bar_](/url)
.
<p>_foo <a href="/url">bar_</a></p>
.
Document[0, 18]
  Paragraph[0, 18]
    Text[0, 5] chars:[0, 5, "_foo "]
    Link[5, 17] textOpen:[5, 6, "["] text:[6, 10, "bar_"] textClose:[10, 11, "]"] linkOpen:[11, 12, "("] url:[12, 16, "/url"] pageRef:[12, 16, "/url"] linkClose:[16, 17, ")"]
      Text[6, 10] chars:[6, 10, "bar_"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 122
*<img src="foo" title="*"/>
.
<p>*<img src="foo" title="*"/></p>
.
Document[0, 28]
  Paragraph[0, 28]
    Text[0, 1] chars:[0, 1, "*"]
    HtmlInline[1, 27] chars:[1, 27, "<img  … \"*\"/>"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 123
**<a href="**">
.
<p>**<a href="**"></p>
.
Document[0, 16]
  Paragraph[0, 16]
    Text[0, 2] chars:[0, 2, "**"]
    HtmlInline[2, 15] chars:[2, 15, "<a hr … \"**\">"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 124
__<a href="__">
.
<p>__<a href="__"></p>
.
Document[0, 16]
  Paragraph[0, 16]
    Text[0, 2] chars:[0, 2, "__"]
    HtmlInline[2, 15] chars:[2, 15, "<a hr … \"__\">"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 125
*a `*`*
.
<p><em>a <code>*</code></em></p>
.
Document[0, 8]
  Paragraph[0, 8]
    Emphasis[0, 7] textOpen:[0, 1, "*"] text:[1, 6, "a `*`"] textClose:[6, 7, "*"]
      Text[1, 3] chars:[1, 3, "a "]
      Code[3, 6] textOpen:[3, 4, "`"] text:[4, 5, "*"] textClose:[5, 6, "`"]
        Text[4, 5] chars:[4, 5, "*"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 126
_a `_`_
.
<p><em>a <code>_</code></em></p>
.
Document[0, 8]
  Paragraph[0, 8]
    Emphasis[0, 7] textOpen:[0, 1, "_"] text:[1, 6, "a `_`"] textClose:[6, 7, "_"]
      Text[1, 3] chars:[1, 3, "a "]
      Code[3, 6] textOpen:[3, 4, "`"] text:[4, 5, "_"] textClose:[5, 6, "`"]
        Text[4, 5] chars:[4, 5, "_"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 127
**a<http://foo.bar/?q=**>
.
<p>**a<a href="http://foo.bar/?q=**">http://foo.bar/?q=**</a></p>
.
Document[0, 26]
  Paragraph[0, 26]
    Text[0, 3] chars:[0, 3, "**a"]
    AutoLink[3, 25] open:[3, 4, "<"] text:[4, 24, "http://foo.bar/?q=**"] pageRef:[4, 24, "http://foo.bar/?q=**"] close:[24, 25, ">"]
````````````````````````````````


```````````````````````````````` example Emphasis and strong emphasis: 128
__a<http://foo.bar/?q=__>
.
<p>__a<a href="http://foo.bar/?q=__">http://foo.bar/?q=__</a></p>
.
Document[0, 26]
  Paragraph[0, 26]
    Text[0, 3] chars:[0, 3, "__a"]
    AutoLink[3, 25] open:[3, 4, "<"] text:[4, 24, "http://foo.bar/?q=__"] pageRef:[4, 24, "http://foo.bar/?q=__"] close:[24, 25, ">"]
````````````````````````````````



## Links

A link contains [link text] (the visible text), a [link destination]
(the URI that is the link destination), and optionally a [link title].
There are two basic kinds of links in Markdown.  In [inline links] the
destination and title are given immediately after the link text.  In
[reference links] the destination and title are defined elsewhere in
the document.

A [link text](@) consists of a sequence of zero or more
inline elements enclosed by square brackets (`[` and `]`).  The
following rules apply:

- Links may not contain other links, at any level of nesting. If
  multiple otherwise valid link definitions appear nested inside each
  other, the inner-most definition is used.

- Brackets are allowed in the [link text] only if (a) they
  are backslash-escaped or (b) they appear as a matched pair of brackets,
  with an open bracket `[`, a sequence of zero or more inlines, and
  a close bracket `]`.

- Backtick [code spans], [autolinks], and raw [HTML tags] bind more tightly
  than the brackets in link text.  Thus, for example,
  `` [foo`]` `` could not be a link text, since the second `]`
  is part of a code span.

- The brackets in link text bind more tightly than markers for
  [emphasis and strong emphasis]. Thus, for example, `*[foo*](url)` is a link.

A [link destination](@) consists of either

- a sequence of zero or more characters between an opening `<` and a
  closing `>` that contains no spaces, line breaks, or unescaped
  `<` or `>` characters, or

- a nonempty sequence of characters that does not include
  ASCII space or control characters, and includes parentheses
  only if (a) they are backslash-escaped or (b) they are part of
  a balanced pair of unescaped parentheses.  (Implementations
  may impose limits on parentheses nesting to avoid performance
  issues, but at least three levels of nesting should be supported.)

A [link title](@)  consists of either

- a sequence of zero or more characters between straight double-quote
  characters (`"`), including a `"` character only if it is
  backslash-escaped, or

- a sequence of zero or more characters between straight single-quote
  characters (`'`), including a `'` character only if it is
  backslash-escaped, or

- a sequence of zero or more characters between matching parentheses
  (`(...)`), including a `)` character only if it is backslash-escaped.

Although [link titles] may span multiple lines, they may not contain
a [blank line].

An [inline link](@) consists of a [link text] followed immediately
by a left parenthesis `(`, optional [whitespace], an optional
[link destination], an optional [link title] separated from the link
destination by [whitespace], optional [whitespace], and a right
parenthesis `)`. The link's text consists of the inlines contained
in the [link text] (excluding the enclosing square brackets).
The link's URI consists of the link destination, excluding enclosing
`<...>` if present, with backslash-escapes in effect as described
above.  The link's title consists of the link title, excluding its
enclosing delimiters, with backslash-escapes in effect as described
above.

Here is a simple inline link:

```````````````````````````````` example Links: 1
[link](/uri "title")
.
<p><a href="/uri" title="title">link</a></p>
.
Document[0, 21]
  Paragraph[0, 21]
    Link[0, 20] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 11, "/uri"] pageRef:[7, 11, "/uri"] titleOpen:[12, 13, "\""] title:[13, 18, "title"] titleClose:[18, 19, "\""] linkClose:[19, 20, ")"]
      Text[1, 5] chars:[1, 5, "link"]
````````````````````````````````


The title may be omitted:

```````````````````````````````` example Links: 2
[link](/uri)
.
<p><a href="/uri">link</a></p>
.
Document[0, 13]
  Paragraph[0, 13]
    Link[0, 12] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 11, "/uri"] pageRef:[7, 11, "/uri"] linkClose:[11, 12, ")"]
      Text[1, 5] chars:[1, 5, "link"]
````````````````````````````````


Both the title and the destination may be omitted:

```````````````````````````````` example Links: 3
[link]()
.
<p><a href="">link</a></p>
.
Document[0, 9]
  Paragraph[0, 9]
    Link[0, 8] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 7] pageRef:[7, 7] linkClose:[7, 8, ")"]
      Text[1, 5] chars:[1, 5, "link"]
````````````````````````````````


```````````````````````````````` example Links: 4
[link](<>)
.
<p><a href="">link</a></p>
.
Document[0, 11]
  Paragraph[0, 11]
    Link[0, 10] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] urlOpen:[7, 8, "<"] url:[8, 8] urlClose:[8, 9, ">"] pageRef:[8, 8] linkClose:[9, 10, ")"]
      Text[1, 5] chars:[1, 5, "link"]
````````````````````````````````


The destination cannot contain spaces or line breaks,
even if enclosed in pointy brackets:

```````````````````````````````` example Links: 5
[link](/my uri)
.
<p>[link](/my uri)</p>
.
Document[0, 16]
  Paragraph[0, 16]
    LinkRef[0, 6] referenceOpen:[0, 1, "["] reference:[1, 5, "link"] referenceClose:[5, 6, "]"]
      Text[1, 5] chars:[1, 5, "link"]
    Text[6, 15] chars:[6, 15, "(/my uri)"]
````````````````````````````````


```````````````````````````````` example Links: 6
[link](</my uri>)
.
<p>[link](&lt;/my uri&gt;)</p>
.
Document[0, 18]
  Paragraph[0, 18]
    LinkRef[0, 6] referenceOpen:[0, 1, "["] reference:[1, 5, "link"] referenceClose:[5, 6, "]"]
      Text[1, 5] chars:[1, 5, "link"]
    Text[6, 17] chars:[6, 17, "(</my … uri>)"]
````````````````````````````````


```````````````````````````````` example Links: 7
[link](foo
bar)
.
<p>[link](foo
bar)</p>
.
Document[0, 16]
  Paragraph[0, 16]
    LinkRef[0, 6] referenceOpen:[0, 1, "["] reference:[1, 5, "link"] referenceClose:[5, 6, "]"]
      Text[1, 5] chars:[1, 5, "link"]
    Text[6, 10] chars:[6, 10, "(foo"]
    SoftLineBreak[10, 11]
    Text[11, 15] chars:[11, 15, "bar)"]
````````````````````````````````


```````````````````````````````` example Links: 8
[link](<foo
bar>)
.
<p>[link](<foo
bar>)</p>
.
Document[0, 18]
  Paragraph[0, 18]
    LinkRef[0, 6] referenceOpen:[0, 1, "["] reference:[1, 5, "link"] referenceClose:[5, 6, "]"]
      Text[1, 5] chars:[1, 5, "link"]
    Text[6, 7] chars:[6, 7, "("]
    HtmlInline[7, 16] chars:[7, 16, "<foo\nbar>"]
    Text[16, 17] chars:[16, 17, ")"]
````````````````````````````````


Parentheses inside the link destination may be escaped:

```````````````````````````````` example Links: 9
[link](\(foo\))
.
<p><a href="(foo)">link</a></p>
.
Document[0, 16]
  Paragraph[0, 16]
    Link[0, 15] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 14, "\(foo\)"] pageRef:[7, 14, "\(foo\)"] linkClose:[14, 15, ")"]
      Text[1, 5] chars:[1, 5, "link"]
````````````````````````````````


Any number of parentheses are allowed without escaping, as long as they are
balanced:

```````````````````````````````` example Links: 10
[link](foo(and(bar)))
.
<p><a href="foo(and(bar))">link</a></p>
.
Document[0, 22]
  Paragraph[0, 22]
    Link[0, 21] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 20, "foo(and(bar))"] pageRef:[7, 20, "foo(and(bar))"] linkClose:[20, 21, ")"]
      Text[1, 5] chars:[1, 5, "link"]
````````````````````````````````

However, if you have unbalanced parentheses, you need to escape or use the
`<...>` form:

```````````````````````````````` example Links: 11
[link](foo\(and\(bar\))
.
<p><a href="foo(and(bar)">link</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    Link[0, 23] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 22, "foo\(and\(bar\)"] pageRef:[7, 22, "foo\(and\(bar\)"] linkClose:[22, 23, ")"]
      Text[1, 5] chars:[1, 5, "link"]
````````````````````````````````


```````````````````````````````` example Links: 12
[link](<foo(and(bar)>)
.
<p><a href="foo(and(bar)">link</a></p>
.
Document[0, 23]
  Paragraph[0, 23]
    Link[0, 22] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] urlOpen:[7, 8, "<"] url:[8, 20, "foo(and(bar)"] urlClose:[20, 21, ">"] pageRef:[8, 20, "foo(and(bar)"] linkClose:[21, 22, ")"]
      Text[1, 5] chars:[1, 5, "link"]
````````````````````````````````



Parentheses and other symbols can also be escaped, as usual
in Markdown:

```````````````````````````````` example Links: 13
[link](foo\)\:)
.
<p><a href="foo):">link</a></p>
.
Document[0, 16]
  Paragraph[0, 16]
    Link[0, 15] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 14, "foo\)\:"] pageRef:[7, 14, "foo\)\:"] linkClose:[14, 15, ")"]
      Text[1, 5] chars:[1, 5, "link"]
````````````````````````````````


A link can contain fragment identifiers and queries:

```````````````````````````````` example Links: 14
[link](#fragment)

[link](http://example.com#fragment)

[link](http://example.com?foo=3#frag)
.
<p><a href="#fragment">link</a></p>
<p><a href="http://example.com#fragment">link</a></p>
<p><a href="http://example.com?foo=3#frag">link</a></p>
.
Document[0, 94]
  Paragraph[0, 18] isTrailingBlankLine
    Link[0, 17] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 16, "#fragment"] pageRef:[7, 7] anchorMarker:[7, 8, "#"] anchorRef:[8, 16, "fragment"] linkClose:[16, 17, ")"]
      Text[1, 5] chars:[1, 5, "link"]
  Paragraph[19, 55] isTrailingBlankLine
    Link[19, 54] textOpen:[19, 20, "["] text:[20, 24, "link"] textClose:[24, 25, "]"] linkOpen:[25, 26, "("] url:[26, 53, "http://example.com#fragment"] pageRef:[26, 44, "http://example.com"] anchorMarker:[44, 45, "#"] anchorRef:[45, 53, "fragment"] linkClose:[53, 54, ")"]
      Text[20, 24] chars:[20, 24, "link"]
  Paragraph[56, 94]
    Link[56, 93] textOpen:[56, 57, "["] text:[57, 61, "link"] textClose:[61, 62, "]"] linkOpen:[62, 63, "("] url:[63, 92, "http://example.com?foo=3#frag"] pageRef:[63, 87, "http://example.com?foo=3"] anchorMarker:[87, 88, "#"] anchorRef:[88, 92, "frag"] linkClose:[92, 93, ")"]
      Text[57, 61] chars:[57, 61, "link"]
````````````````````````````````


Note that a backslash before a non-escapable character is
just a backslash:

```````````````````````````````` example Links: 15
[link](foo\bar)
.
<p><a href="foo%5Cbar">link</a></p>
.
Document[0, 16]
  Paragraph[0, 16]
    Link[0, 15] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 14, "foo\bar"] pageRef:[7, 14, "foo\bar"] linkClose:[14, 15, ")"]
      Text[1, 5] chars:[1, 5, "link"]
````````````````````````````````


URL-escaping should be left alone inside the destination, as all
URL-escaped characters are also valid URL characters. Entity and
numerical character references in the destination will be parsed
into the corresponding Unicode code points, as usual.  These may
be optionally URL-escaped when written as HTML, but this spec
does not enforce any particular policy for rendering URLs in
HTML or other formats.  Renderers may make different decisions
about how to escape or normalize URLs in the output.

```````````````````````````````` example Links: 16
[link](foo%20b&auml;)
.
<p><a href="foo%20b%C3%A4">link</a></p>
.
Document[0, 22]
  Paragraph[0, 22]
    Link[0, 21] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 20, "foo%20b&auml;"] pageRef:[7, 20, "foo%20b&auml;"] linkClose:[20, 21, ")"]
      Text[1, 5] chars:[1, 5, "link"]
````````````````````````````````


Note that, because titles can often be parsed as destinations,
if you try to omit the destination and keep the title, you'll
get unexpected results:

```````````````````````````````` example Links: 17
[link]("title")
.
<p><a href="%22title%22">link</a></p>
.
Document[0, 16]
  Paragraph[0, 16]
    Link[0, 15] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 14, "\"title\""] pageRef:[7, 14, "\"title\""] linkClose:[14, 15, ")"]
      Text[1, 5] chars:[1, 5, "link"]
````````````````````````````````


Titles may be in single quotes, double quotes, or parentheses:

```````````````````````````````` example Links: 18
[link](/url "title")
[link](/url 'title')
[link](/url (title))
.
<p><a href="/url" title="title">link</a>
<a href="/url" title="title">link</a>
<a href="/url" title="title">link</a></p>
.
Document[0, 63]
  Paragraph[0, 63]
    Link[0, 20] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 11, "/url"] pageRef:[7, 11, "/url"] titleOpen:[12, 13, "\""] title:[13, 18, "title"] titleClose:[18, 19, "\""] linkClose:[19, 20, ")"]
      Text[1, 5] chars:[1, 5, "link"]
    SoftLineBreak[20, 21]
    Link[21, 41] textOpen:[21, 22, "["] text:[22, 26, "link"] textClose:[26, 27, "]"] linkOpen:[27, 28, "("] url:[28, 32, "/url"] pageRef:[28, 32, "/url"] titleOpen:[33, 34, "'"] title:[34, 39, "title"] titleClose:[39, 40, "'"] linkClose:[40, 41, ")"]
      Text[22, 26] chars:[22, 26, "link"]
    SoftLineBreak[41, 42]
    Link[42, 62] textOpen:[42, 43, "["] text:[43, 47, "link"] textClose:[47, 48, "]"] linkOpen:[48, 49, "("] url:[49, 53, "/url"] pageRef:[49, 53, "/url"] titleOpen:[54, 55, "("] title:[55, 60, "title"] titleClose:[60, 61, ")"] linkClose:[61, 62, ")"]
      Text[43, 47] chars:[43, 47, "link"]
````````````````````````````````


Backslash escapes and entity and numeric character references
may be used in titles:

```````````````````````````````` example Links: 19
[link](/url "title \"&quot;")
.
<p><a href="/url" title="title &quot;&quot;">link</a></p>
.
Document[0, 30]
  Paragraph[0, 30]
    Link[0, 29] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 11, "/url"] pageRef:[7, 11, "/url"] titleOpen:[12, 13, "\""] title:[13, 27, "title \\"&quot;"] titleClose:[27, 28, "\""] linkClose:[28, 29, ")"]
      Text[1, 5] chars:[1, 5, "link"]
````````````````````````````````


Titles must be separated from the link using a [whitespace].
Other [Unicode whitespace] like non-breaking space doesn't work.

```````````````````````````````` example Links: 20
[link](/url "title")
.
<p><a href="/url%C2%A0%22title%22">link</a></p>
.
Document[0, 21]
  Paragraph[0, 21]
    Link[0, 20] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 19, "/url \"title\""] pageRef:[7, 19, "/url \"title\""] linkClose:[19, 20, ")"]
      Text[1, 5] chars:[1, 5, "link"]
````````````````````````````````


Nested balanced quotes are not allowed without escaping:

```````````````````````````````` example Links: 21
[link](/url "title "and" title")
.
<p>[link](/url &quot;title &quot;and&quot; title&quot;)</p>
.
Document[0, 33]
  Paragraph[0, 33]
    LinkRef[0, 6] referenceOpen:[0, 1, "["] reference:[1, 5, "link"] referenceClose:[5, 6, "]"]
      Text[1, 5] chars:[1, 5, "link"]
    Text[6, 32] chars:[6, 32, "(/url … tle\")"]
````````````````````````````````


But it is easy to work around this by using a different quote type:

```````````````````````````````` example Links: 22
[link](/url 'title "and" title')
.
<p><a href="/url" title="title &quot;and&quot; title">link</a></p>
.
Document[0, 33]
  Paragraph[0, 33]
    Link[0, 32] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 11, "/url"] pageRef:[7, 11, "/url"] titleOpen:[12, 13, "'"] title:[13, 30, "title \"and\" title"] titleClose:[30, 31, "'"] linkClose:[31, 32, ")"]
      Text[1, 5] chars:[1, 5, "link"]
````````````````````````````````


(Note:  `Markdown.pl` did allow double quotes inside a double-quoted
title, and its test suite included a test demonstrating this.
But it is hard to see a good rationale for the extra complexity this
brings, since there are already many ways---backslash escaping,
entity and numeric character references, or using a different
quote type for the enclosing title---to write titles containing
double quotes.  `Markdown.pl`'s handling of titles has a number
of other strange features.  For example, it allows single-quoted
titles in inline links, but not reference links.  And, in
reference links but not inline links, it allows a title to begin
with `"` and end with `)`.  `Markdown.pl` 1.0.1 even allows
titles with no closing quotation mark, though 1.0.2b8 does not.
It seems preferable to adopt a simple, rational rule that works
the same way in inline links and link reference definitions.)

[Whitespace] is allowed around the destination and title:

```````````````````````````````` example Links: 23
[link](   /uri
  "title"  )
.
<p><a href="/uri" title="title">link</a></p>
.
Document[0, 28]
  Paragraph[0, 28]
    Link[0, 27] textOpen:[0, 1, "["] text:[1, 5, "link"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[10, 14, "/uri"] pageRef:[10, 14, "/uri"] titleOpen:[17, 18, "\""] title:[18, 23, "title"] titleClose:[23, 24, "\""] linkClose:[26, 27, ")"]
      Text[1, 5] chars:[1, 5, "link"]
````````````````````````````````


But it is not allowed between the link text and the
following parenthesis:

```````````````````````````````` example Links: 24
[link] (/uri)
.
<p>[link] (/uri)</p>
.
Document[0, 14]
  Paragraph[0, 14]
    LinkRef[0, 6] referenceOpen:[0, 1, "["] reference:[1, 5, "link"] referenceClose:[5, 6, "]"]
      Text[1, 5] chars:[1, 5, "link"]
    Text[6, 13] chars:[6, 13, " (/uri)"]
````````````````````````````````


The link text may contain balanced brackets, but not unbalanced ones,
unless they are escaped:

```````````````````````````````` example Links: 25
[link [foo [bar]]](/uri)
.
<p><a href="/uri">link [foo [bar]]</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    Link[0, 24] textOpen:[0, 1, "["] text:[1, 17, "link [foo [bar]]"] textClose:[17, 18, "]"] linkOpen:[18, 19, "("] url:[19, 23, "/uri"] pageRef:[19, 23, "/uri"] linkClose:[23, 24, ")"]
      Text[1, 17] chars:[1, 17, "link  … bar]]"]
````````````````````````````````


```````````````````````````````` example Links: 26
[link] bar](/uri)
.
<p>[link] bar](/uri)</p>
.
Document[0, 18]
  Paragraph[0, 18]
    LinkRef[0, 6] referenceOpen:[0, 1, "["] reference:[1, 5, "link"] referenceClose:[5, 6, "]"]
      Text[1, 5] chars:[1, 5, "link"]
    Text[6, 17] chars:[6, 17, " bar] … /uri)"]
````````````````````````````````


```````````````````````````````` example Links: 27
[link [bar](/uri)
.
<p>[link <a href="/uri">bar</a></p>
.
Document[0, 18]
  Paragraph[0, 18]
    Text[0, 6] chars:[0, 6, "[link "]
    Link[6, 17] textOpen:[6, 7, "["] text:[7, 10, "bar"] textClose:[10, 11, "]"] linkOpen:[11, 12, "("] url:[12, 16, "/uri"] pageRef:[12, 16, "/uri"] linkClose:[16, 17, ")"]
      Text[7, 10] chars:[7, 10, "bar"]
````````````````````````````````


```````````````````````````````` example Links: 28
[link \[bar](/uri)
.
<p><a href="/uri">link [bar</a></p>
.
Document[0, 19]
  Paragraph[0, 19]
    Link[0, 18] textOpen:[0, 1, "["] text:[1, 11, "link \[bar"] textClose:[11, 12, "]"] linkOpen:[12, 13, "("] url:[13, 17, "/uri"] pageRef:[13, 17, "/uri"] linkClose:[17, 18, ")"]
      Text[1, 11] chars:[1, 11, "link \[bar"]
````````````````````````````````


The link text may contain inline content:

```````````````````````````````` example Links: 29
[link *foo **bar** `#`*](/uri)
.
<p><a href="/uri">link <em>foo <strong>bar</strong> <code>#</code></em></a></p>
.
Document[0, 31]
  Paragraph[0, 31]
    Link[0, 30] textOpen:[0, 1, "["] text:[1, 23, "link *foo **bar** `#`*"] textClose:[23, 24, "]"] linkOpen:[24, 25, "("] url:[25, 29, "/uri"] pageRef:[25, 29, "/uri"] linkClose:[29, 30, ")"]
      Text[1, 6] chars:[1, 6, "link "]
      Emphasis[6, 23] textOpen:[6, 7, "*"] text:[7, 22, "foo **bar** `#`"] textClose:[22, 23, "*"]
        Text[7, 11] chars:[7, 11, "foo "]
        StrongEmphasis[11, 18] textOpen:[11, 13, "**"] text:[13, 16, "bar"] textClose:[16, 18, "**"]
          Text[13, 16] chars:[13, 16, "bar"]
        Text[18, 19] chars:[18, 19, " "]
        Code[19, 22] textOpen:[19, 20, "`"] text:[20, 21, "#"] textClose:[21, 22, "`"]
          Text[20, 21] chars:[20, 21, "#"]
````````````````````````````````


```````````````````````````````` example Links: 30
[![moon](moon.jpg)](/uri)
.
<p><a href="/uri"><img src="moon.jpg" alt="moon" /></a></p>
.
Document[0, 26]
  Paragraph[0, 26]
    Link[0, 25] textOpen:[0, 1, "["] text:[1, 18, "![moon](moon.jpg)"] textClose:[18, 19, "]"] linkOpen:[19, 20, "("] url:[20, 24, "/uri"] pageRef:[20, 24, "/uri"] linkClose:[24, 25, ")"]
      Image[1, 18] textOpen:[1, 3, "!["] text:[3, 7, "moon"] textClose:[7, 8, "]"] linkOpen:[8, 9, "("] url:[9, 17, "moon.jpg"] pageRef:[9, 17, "moon.jpg"] linkClose:[17, 18, ")"]
        Text[3, 7] chars:[3, 7, "moon"]
````````````````````````````````


However, links may not contain other links, at any level of nesting.

```````````````````````````````` example Links: 31
[foo [bar](/uri)](/uri)
.
<p>[foo <a href="/uri">bar</a>](/uri)</p>
.
Document[0, 24]
  Paragraph[0, 24]
    Text[0, 5] chars:[0, 5, "[foo "]
    Link[5, 16] textOpen:[5, 6, "["] text:[6, 9, "bar"] textClose:[9, 10, "]"] linkOpen:[10, 11, "("] url:[11, 15, "/uri"] pageRef:[11, 15, "/uri"] linkClose:[15, 16, ")"]
      Text[6, 9] chars:[6, 9, "bar"]
    Text[16, 23] chars:[16, 23, "](/uri)"]
````````````````````````````````


```````````````````````````````` example Links: 32
[foo *[bar [baz](/uri)](/uri)*](/uri)
.
<p>[foo <em>[bar <a href="/uri">baz</a>](/uri)</em>](/uri)</p>
.
Document[0, 38]
  Paragraph[0, 38]
    Text[0, 5] chars:[0, 5, "[foo "]
    Emphasis[5, 30] textOpen:[5, 6, "*"] text:[6, 29, "[bar [baz](/uri)](/uri)"] textClose:[29, 30, "*"]
      Text[6, 7] chars:[6, 7, "["]
      Text[7, 11] chars:[7, 11, "bar "]
      Link[11, 22] textOpen:[11, 12, "["] text:[12, 15, "baz"] textClose:[15, 16, "]"] linkOpen:[16, 17, "("] url:[17, 21, "/uri"] pageRef:[17, 21, "/uri"] linkClose:[21, 22, ")"]
        Text[12, 15] chars:[12, 15, "baz"]
      Text[22, 29] chars:[22, 29, "](/uri)"]
    Text[30, 37] chars:[30, 37, "](/uri)"]
````````````````````````````````


```````````````````````````````` example Links: 33
![[[foo](uri1)](uri2)](uri3)
.
<p><img src="uri3" alt="[foo](uri2)" /></p>
.
Document[0, 29]
  Paragraph[0, 29]
    Image[0, 28] textOpen:[0, 2, "!["] text:[2, 21, "[[foo](uri1)](uri2)"] textClose:[21, 22, "]"] linkOpen:[22, 23, "("] url:[23, 27, "uri3"] pageRef:[23, 27, "uri3"] linkClose:[27, 28, ")"]
      Text[2, 3] chars:[2, 3, "["]
      Link[3, 14] textOpen:[3, 4, "["] text:[4, 7, "foo"] textClose:[7, 8, "]"] linkOpen:[8, 9, "("] url:[9, 13, "uri1"] pageRef:[9, 13, "uri1"] linkClose:[13, 14, ")"]
        Text[4, 7] chars:[4, 7, "foo"]
      Text[14, 21] chars:[14, 21, "](uri2)"]
````````````````````````````````


These cases illustrate the precedence of link text grouping over
emphasis grouping:

```````````````````````````````` example Links: 34
*[foo*](/uri)
.
<p>*<a href="/uri">foo*</a></p>
.
Document[0, 14]
  Paragraph[0, 14]
    Text[0, 1] chars:[0, 1, "*"]
    Link[1, 13] textOpen:[1, 2, "["] text:[2, 6, "foo*"] textClose:[6, 7, "]"] linkOpen:[7, 8, "("] url:[8, 12, "/uri"] pageRef:[8, 12, "/uri"] linkClose:[12, 13, ")"]
      Text[2, 6] chars:[2, 6, "foo*"]
````````````````````````````````


```````````````````````````````` example Links: 35
[foo *bar](baz*)
.
<p><a href="baz*">foo *bar</a></p>
.
Document[0, 17]
  Paragraph[0, 17]
    Link[0, 16] textOpen:[0, 1, "["] text:[1, 9, "foo *bar"] textClose:[9, 10, "]"] linkOpen:[10, 11, "("] url:[11, 15, "baz*"] pageRef:[11, 15, "baz*"] linkClose:[15, 16, ")"]
      Text[1, 9] chars:[1, 9, "foo *bar"]
````````````````````````````````


Note that brackets that *aren't* part of links do not take
precedence:

```````````````````````````````` example Links: 36
*foo [bar* baz]
.
<p><em>foo [bar</em> baz]</p>
.
Document[0, 16]
  Paragraph[0, 16]
    Emphasis[0, 10] textOpen:[0, 1, "*"] text:[1, 9, "foo [bar"] textClose:[9, 10, "*"]
      Text[1, 5] chars:[1, 5, "foo "]
      Text[5, 6] chars:[5, 6, "["]
      Text[6, 9] chars:[6, 9, "bar"]
    Text[10, 15] chars:[10, 15, " baz]"]
````````````````````````````````


These cases illustrate the precedence of HTML tags, code spans,
and autolinks over link grouping:

```````````````````````````````` example Links: 37
[foo <bar attr="](baz)">
.
<p>[foo <bar attr="](baz)"></p>
.
Document[0, 25]
  Paragraph[0, 25]
    Text[0, 5] chars:[0, 5, "[foo "]
    HtmlInline[5, 24] chars:[5, 24, "<bar  … az)\">"]
````````````````````````````````


```````````````````````````````` example Links: 38
[foo`](/uri)`
.
<p>[foo<code>](/uri)</code></p>
.
Document[0, 14]
  Paragraph[0, 14]
    Text[0, 4] chars:[0, 4, "[foo"]
    Code[4, 13] textOpen:[4, 5, "`"] text:[5, 12, "](/uri)"] textClose:[12, 13, "`"]
      Text[5, 12] chars:[5, 12, "](/uri)"]
````````````````````````````````


```````````````````````````````` example Links: 39
[foo<http://example.com/?search=](uri)>
.
<p>[foo<a href="http://example.com/?search=%5D(uri)">http://example.com/?search=](uri)</a></p>
.
Document[0, 40]
  Paragraph[0, 40]
    Text[0, 4] chars:[0, 4, "[foo"]
    AutoLink[4, 39] open:[4, 5, "<"] text:[5, 38, "http://example.com/?search=](uri)"] pageRef:[5, 38, "http://example.com/?search=](uri)"] close:[38, 39, ">"]
````````````````````````````````


There are three kinds of [reference link](@)s:
[full](#full-reference-link), [collapsed](#collapsed-reference-link),
and [shortcut](#shortcut-reference-link).

A [full reference link](@)
consists of a [link text] immediately followed by a [link label]
that [matches] a [link reference definition] elsewhere in the document.

A [link label](@)  begins with a left bracket (`[`) and ends
with the first right bracket (`]`) that is not backslash-escaped.
Between these brackets there must be at least one [non-whitespace character].
Unescaped square bracket characters are not allowed inside the
opening and closing square brackets of [link labels].  A link
label can have at most 999 characters inside the square
brackets.

One label [matches](@)
another just in case their normalized forms are equal.  To normalize a
label, strip off the opening and closing brackets,
perform the *Unicode case fold*, strip leading and trailing
[whitespace] and collapse consecutive internal
[whitespace] to a single space.  If there are multiple
matching reference link definitions, the one that comes first in the
document is used.  (It is desirable in such cases to emit a warning.)

The contents of the first link label are parsed as inlines, which are
used as the link's text.  The link's URI and title are provided by the
matching [link reference definition].

Here is a simple example:

```````````````````````````````` example Links: 40
[foo][bar]

[bar]: /url "title"
.
<p><a href="/url" title="title">foo</a></p>
.
Document[0, 32]
  Paragraph[0, 11] isTrailingBlankLine
    LinkRef[0, 10] textOpen:[0, 1, "["] text:[1, 4, "foo"] textClose:[4, 5, "]"] referenceOpen:[5, 6, "["] reference:[6, 9, "bar"] referenceClose:[9, 10, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
  Reference[12, 31] refOpen:[12, 13, "["] ref:[13, 16, "bar"] refClose:[16, 18, "]:"] url:[19, 23, "/url"] titleOpen:[24, 25, "\""] title:[25, 30, "title"] titleClose:[30, 31, "\""]
````````````````````````````````


The rules for the [link text] are the same as with
[inline links].  Thus:

The link text may contain balanced brackets, but not unbalanced ones,
unless they are escaped:

```````````````````````````````` example Links: 41
[link [foo [bar]]][ref]

[ref]: /uri
.
<p><a href="/uri">link [foo [bar]]</a></p>
.
Document[0, 37]
  Paragraph[0, 24] isTrailingBlankLine
    LinkRef[0, 23] textOpen:[0, 1, "["] text:[1, 17, "link [foo [bar]]"] textClose:[17, 18, "]"] referenceOpen:[18, 19, "["] reference:[19, 22, "ref"] referenceClose:[22, 23, "]"]
      Text[1, 17] chars:[1, 17, "link  … bar]]"]
  Reference[25, 36] refOpen:[25, 26, "["] ref:[26, 29, "ref"] refClose:[29, 31, "]:"] url:[32, 36, "/uri"]
````````````````````````````````


```````````````````````````````` example Links: 42
[link \[bar][ref]

[ref]: /uri
.
<p><a href="/uri">link [bar</a></p>
.
Document[0, 31]
  Paragraph[0, 18] isTrailingBlankLine
    LinkRef[0, 17] textOpen:[0, 1, "["] text:[1, 11, "link \[bar"] textClose:[11, 12, "]"] referenceOpen:[12, 13, "["] reference:[13, 16, "ref"] referenceClose:[16, 17, "]"]
      Text[1, 11] chars:[1, 11, "link \[bar"]
  Reference[19, 30] refOpen:[19, 20, "["] ref:[20, 23, "ref"] refClose:[23, 25, "]:"] url:[26, 30, "/uri"]
````````````````````````````````


The link text may contain inline content:

```````````````````````````````` example Links: 43
[link *foo **bar** `#`*][ref]

[ref]: /uri
.
<p><a href="/uri">link <em>foo <strong>bar</strong> <code>#</code></em></a></p>
.
Document[0, 43]
  Paragraph[0, 30] isTrailingBlankLine
    LinkRef[0, 29] textOpen:[0, 1, "["] text:[1, 23, "link *foo **bar** `#`*"] textClose:[23, 24, "]"] referenceOpen:[24, 25, "["] reference:[25, 28, "ref"] referenceClose:[28, 29, "]"]
      Text[1, 6] chars:[1, 6, "link "]
      Emphasis[6, 23] textOpen:[6, 7, "*"] text:[7, 22, "foo **bar** `#`"] textClose:[22, 23, "*"]
        Text[7, 11] chars:[7, 11, "foo "]
        StrongEmphasis[11, 18] textOpen:[11, 13, "**"] text:[13, 16, "bar"] textClose:[16, 18, "**"]
          Text[13, 16] chars:[13, 16, "bar"]
        Text[18, 19] chars:[18, 19, " "]
        Code[19, 22] textOpen:[19, 20, "`"] text:[20, 21, "#"] textClose:[21, 22, "`"]
          Text[20, 21] chars:[20, 21, "#"]
  Reference[31, 42] refOpen:[31, 32, "["] ref:[32, 35, "ref"] refClose:[35, 37, "]:"] url:[38, 42, "/uri"]
````````````````````````````````


```````````````````````````````` example Links: 44
[![moon](moon.jpg)][ref]

[ref]: /uri
.
<p><a href="/uri"><img src="moon.jpg" alt="moon" /></a></p>
.
Document[0, 38]
  Paragraph[0, 25] isTrailingBlankLine
    LinkRef[0, 24] textOpen:[0, 1, "["] text:[1, 18, "![moon](moon.jpg)"] textClose:[18, 19, "]"] referenceOpen:[19, 20, "["] reference:[20, 23, "ref"] referenceClose:[23, 24, "]"]
      Image[1, 18] textOpen:[1, 3, "!["] text:[3, 7, "moon"] textClose:[7, 8, "]"] linkOpen:[8, 9, "("] url:[9, 17, "moon.jpg"] pageRef:[9, 17, "moon.jpg"] linkClose:[17, 18, ")"]
        Text[3, 7] chars:[3, 7, "moon"]
  Reference[26, 37] refOpen:[26, 27, "["] ref:[27, 30, "ref"] refClose:[30, 32, "]:"] url:[33, 37, "/uri"]
````````````````````````````````


However, links may not contain other links, at any level of nesting.

```````````````````````````````` example Links: 45
[foo [bar](/uri)][ref]

[ref]: /uri
.
<p>[foo <a href="/uri">bar</a>]<a href="/uri">ref</a></p>
.
Document[0, 36]
  Paragraph[0, 23] isTrailingBlankLine
    Text[0, 5] chars:[0, 5, "[foo "]
    Link[5, 16] textOpen:[5, 6, "["] text:[6, 9, "bar"] textClose:[9, 10, "]"] linkOpen:[10, 11, "("] url:[11, 15, "/uri"] pageRef:[11, 15, "/uri"] linkClose:[15, 16, ")"]
      Text[6, 9] chars:[6, 9, "bar"]
    Text[16, 17] chars:[16, 17, "]"]
    LinkRef[17, 22] referenceOpen:[17, 18, "["] reference:[18, 21, "ref"] referenceClose:[21, 22, "]"]
      Text[18, 21] chars:[18, 21, "ref"]
  Reference[24, 35] refOpen:[24, 25, "["] ref:[25, 28, "ref"] refClose:[28, 30, "]:"] url:[31, 35, "/uri"]
````````````````````````````````


```````````````````````````````` example Links: 46
[foo *bar [baz][ref]*][ref]

[ref]: /uri
.
<p>[foo <em>bar <a href="/uri">baz</a></em>]<a href="/uri">ref</a></p>
.
Document[0, 41]
  Paragraph[0, 28] isTrailingBlankLine
    Text[0, 5] chars:[0, 5, "[foo "]
    Emphasis[5, 21] textOpen:[5, 6, "*"] text:[6, 20, "bar [baz][ref]"] textClose:[20, 21, "*"]
      Text[6, 10] chars:[6, 10, "bar "]
      LinkRef[10, 20] textOpen:[10, 11, "["] text:[11, 14, "baz"] textClose:[14, 15, "]"] referenceOpen:[15, 16, "["] reference:[16, 19, "ref"] referenceClose:[19, 20, "]"]
        Text[11, 14] chars:[11, 14, "baz"]
    Text[21, 22] chars:[21, 22, "]"]
    LinkRef[22, 27] referenceOpen:[22, 23, "["] reference:[23, 26, "ref"] referenceClose:[26, 27, "]"]
      Text[23, 26] chars:[23, 26, "ref"]
  Reference[29, 40] refOpen:[29, 30, "["] ref:[30, 33, "ref"] refClose:[33, 35, "]:"] url:[36, 40, "/uri"]
````````````````````````````````


(In the examples above, we have two [shortcut reference links]
instead of one [full reference link].)

The following cases illustrate the precedence of link text grouping over
emphasis grouping:

```````````````````````````````` example Links: 47
*[foo*][ref]

[ref]: /uri
.
<p>*<a href="/uri">foo*</a></p>
.
Document[0, 26]
  Paragraph[0, 13] isTrailingBlankLine
    Text[0, 1] chars:[0, 1, "*"]
    LinkRef[1, 12] textOpen:[1, 2, "["] text:[2, 6, "foo*"] textClose:[6, 7, "]"] referenceOpen:[7, 8, "["] reference:[8, 11, "ref"] referenceClose:[11, 12, "]"]
      Text[2, 6] chars:[2, 6, "foo*"]
  Reference[14, 25] refOpen:[14, 15, "["] ref:[15, 18, "ref"] refClose:[18, 20, "]:"] url:[21, 25, "/uri"]
````````````````````````````````


```````````````````````````````` example Links: 48
[foo *bar][ref]

[ref]: /uri
.
<p><a href="/uri">foo *bar</a></p>
.
Document[0, 29]
  Paragraph[0, 16] isTrailingBlankLine
    LinkRef[0, 15] textOpen:[0, 1, "["] text:[1, 9, "foo *bar"] textClose:[9, 10, "]"] referenceOpen:[10, 11, "["] reference:[11, 14, "ref"] referenceClose:[14, 15, "]"]
      Text[1, 9] chars:[1, 9, "foo *bar"]
  Reference[17, 28] refOpen:[17, 18, "["] ref:[18, 21, "ref"] refClose:[21, 23, "]:"] url:[24, 28, "/uri"]
````````````````````````````````


These cases illustrate the precedence of HTML tags, code spans,
and autolinks over link grouping:

```````````````````````````````` example Links: 49
[foo <bar attr="][ref]">

[ref]: /uri
.
<p>[foo <bar attr="][ref]"></p>
.
Document[0, 38]
  Paragraph[0, 25] isTrailingBlankLine
    Text[0, 5] chars:[0, 5, "[foo "]
    HtmlInline[5, 24] chars:[5, 24, "<bar  … ef]\">"]
  Reference[26, 37] refOpen:[26, 27, "["] ref:[27, 30, "ref"] refClose:[30, 32, "]:"] url:[33, 37, "/uri"]
````````````````````````````````


```````````````````````````````` example Links: 50
[foo`][ref]`

[ref]: /uri
.
<p>[foo<code>][ref]</code></p>
.
Document[0, 26]
  Paragraph[0, 13] isTrailingBlankLine
    Text[0, 4] chars:[0, 4, "[foo"]
    Code[4, 12] textOpen:[4, 5, "`"] text:[5, 11, "][ref]"] textClose:[11, 12, "`"]
      Text[5, 11] chars:[5, 11, "][ref]"]
  Reference[14, 25] refOpen:[14, 15, "["] ref:[15, 18, "ref"] refClose:[18, 20, "]:"] url:[21, 25, "/uri"]
````````````````````````````````


```````````````````````````````` example Links: 51
[foo<http://example.com/?search=][ref]>

[ref]: /uri
.
<p>[foo<a href="http://example.com/?search=%5D%5Bref%5D">http://example.com/?search=][ref]</a></p>
.
Document[0, 53]
  Paragraph[0, 40] isTrailingBlankLine
    Text[0, 4] chars:[0, 4, "[foo"]
    AutoLink[4, 39] open:[4, 5, "<"] text:[5, 38, "http://example.com/?search=][ref]"] pageRef:[5, 38, "http://example.com/?search=][ref]"] close:[38, 39, ">"]
  Reference[41, 52] refOpen:[41, 42, "["] ref:[42, 45, "ref"] refClose:[45, 47, "]:"] url:[48, 52, "/uri"]
````````````````````````````````


Matching is case-insensitive:

```````````````````````````````` example Links: 52
[foo][BaR]

[bar]: /url "title"
.
<p><a href="/url" title="title">foo</a></p>
.
Document[0, 32]
  Paragraph[0, 11] isTrailingBlankLine
    LinkRef[0, 10] textOpen:[0, 1, "["] text:[1, 4, "foo"] textClose:[4, 5, "]"] referenceOpen:[5, 6, "["] reference:[6, 9, "BaR"] referenceClose:[9, 10, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
  Reference[12, 31] refOpen:[12, 13, "["] ref:[13, 16, "bar"] refClose:[16, 18, "]:"] url:[19, 23, "/url"] titleOpen:[24, 25, "\""] title:[25, 30, "title"] titleClose:[30, 31, "\""]
````````````````````````````````


Unicode case fold is used:

```````````````````````````````` example Links: 53
[Толпой][Толпой] is a Russian word.

[ТОЛПОЙ]: /url
.
<p><a href="/url">Толпой</a> is a Russian word.</p>
.
Document[0, 52]
  Paragraph[0, 36] isTrailingBlankLine
    LinkRef[0, 16] textOpen:[0, 1, "["] text:[1, 7, "Толпой"] textClose:[7, 8, "]"] referenceOpen:[8, 9, "["] reference:[9, 15, "Толпой"] referenceClose:[15, 16, "]"]
      Text[1, 7] chars:[1, 7, "Толпой"]
    Text[16, 35] chars:[16, 35, " is a … word."]
  Reference[37, 51] refOpen:[37, 38, "["] ref:[38, 44, "ТОЛПОЙ"] refClose:[44, 46, "]:"] url:[47, 51, "/url"]
````````````````````````````````


Consecutive internal [whitespace] is treated as one space for
purposes of determining matching:

```````````````````````````````` example Links: 54
[Foo
  bar]: /url

[Baz][Foo bar]
.
<p><a href="/url">Baz</a></p>
.
Document[0, 34]
  Reference[0, 17] refOpen:[0, 1, "["] ref:[1, 10, "Foo\n  bar"] refClose:[10, 12, "]:"] url:[13, 17, "/url"]
  Paragraph[19, 34]
    LinkRef[19, 33] textOpen:[19, 20, "["] text:[20, 23, "Baz"] textClose:[23, 24, "]"] referenceOpen:[24, 25, "["] reference:[25, 32, "Foo bar"] referenceClose:[32, 33, "]"]
      Text[20, 23] chars:[20, 23, "Baz"]
````````````````````````````````


No [whitespace] is allowed between the [link text] and the
[link label]:

```````````````````````````````` example Links: 55
[foo] [bar]

[bar]: /url "title"
.
<p>[foo] <a href="/url" title="title">bar</a></p>
.
Document[0, 33]
  Paragraph[0, 12] isTrailingBlankLine
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
    Text[5, 6] chars:[5, 6, " "]
    LinkRef[6, 11] referenceOpen:[6, 7, "["] reference:[7, 10, "bar"] referenceClose:[10, 11, "]"]
      Text[7, 10] chars:[7, 10, "bar"]
  Reference[13, 32] refOpen:[13, 14, "["] ref:[14, 17, "bar"] refClose:[17, 19, "]:"] url:[20, 24, "/url"] titleOpen:[25, 26, "\""] title:[26, 31, "title"] titleClose:[31, 32, "\""]
````````````````````````````````


```````````````````````````````` example Links: 56
[foo]
[bar]

[bar]: /url "title"
.
<p>[foo]
<a href="/url" title="title">bar</a></p>
.
Document[0, 33]
  Paragraph[0, 12] isTrailingBlankLine
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
    SoftLineBreak[5, 6]
    LinkRef[6, 11] referenceOpen:[6, 7, "["] reference:[7, 10, "bar"] referenceClose:[10, 11, "]"]
      Text[7, 10] chars:[7, 10, "bar"]
  Reference[13, 32] refOpen:[13, 14, "["] ref:[14, 17, "bar"] refClose:[17, 19, "]:"] url:[20, 24, "/url"] titleOpen:[25, 26, "\""] title:[26, 31, "title"] titleClose:[31, 32, "\""]
````````````````````````````````


This is a departure from John Gruber's original Markdown syntax
description, which explicitly allows whitespace between the link
text and the link label.  It brings reference links in line with
[inline links], which (according to both original Markdown and
this spec) cannot have whitespace after the link text.  More
importantly, it prevents inadvertent capture of consecutive
[shortcut reference links]. If whitespace is allowed between the
link text and the link label, then in the following we will have
a single reference link, not two shortcut reference links, as
intended:

``` markdown
[foo]
[bar]

[foo]: /url1
[bar]: /url2
```

(Note that [shortcut reference links] were introduced by Gruber
himself in a beta version of `Markdown.pl`, but never included
in the official syntax description.  Without shortcut reference
links, it is harmless to allow space between the link text and
link label; but once shortcut references are introduced, it is
too dangerous to allow this, as it frequently leads to
unintended results.)

When there are multiple matching [link reference definitions],
the first is used:

```````````````````````````````` example Links: 57
[foo]: /url1

[foo]: /url2

[bar][foo]
.
<p><a href="/url1">bar</a></p>
.
Document[0, 39]
  Reference[0, 12] refOpen:[0, 1, "["] ref:[1, 4, "foo"] refClose:[4, 6, "]:"] url:[7, 12, "/url1"]
  Reference[14, 26] refOpen:[14, 15, "["] ref:[15, 18, "foo"] refClose:[18, 20, "]:"] url:[21, 26, "/url2"]
  Paragraph[28, 39]
    LinkRef[28, 38] textOpen:[28, 29, "["] text:[29, 32, "bar"] textClose:[32, 33, "]"] referenceOpen:[33, 34, "["] reference:[34, 37, "foo"] referenceClose:[37, 38, "]"]
      Text[29, 32] chars:[29, 32, "bar"]
````````````````````````````````


Note that matching is performed on normalized strings, not parsed
inline content.  So the following does not match, even though the
labels define equivalent inline content:

```````````````````````````````` example Links: 58
[bar][foo\!]

[foo!]: /url
.
<p>[bar][foo!]</p>
.
Document[0, 27]
  Paragraph[0, 13] isTrailingBlankLine
    LinkRef[0, 12] textOpen:[0, 1, "["] text:[1, 4, "bar"] textClose:[4, 5, "]"] referenceOpen:[5, 6, "["] reference:[6, 11, "foo\!"] referenceClose:[11, 12, "]"]
      Text[1, 4] chars:[1, 4, "bar"]
  Reference[14, 26] refOpen:[14, 15, "["] ref:[15, 19, "foo!"] refClose:[19, 21, "]:"] url:[22, 26, "/url"]
````````````````````````````````


[Link labels] cannot contain brackets, unless they are
backslash-escaped:

```````````````````````````````` example Links: 59
[foo][ref[]

[ref[]: /uri
.
<p>[foo][ref[]</p>
<p>[ref[]: /uri</p>
.
Document[0, 26]
  Paragraph[0, 12] isTrailingBlankLine
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
    Text[5, 9] chars:[5, 9, "[ref"]
    LinkRef[9, 11] referenceOpen:[9, 10, "["] reference:[10, 10] referenceClose:[10, 11, "]"]
  Paragraph[13, 26]
    Text[13, 17] chars:[13, 17, "[ref"]
    LinkRef[17, 19] referenceOpen:[17, 18, "["] reference:[18, 18] referenceClose:[18, 19, "]"]
    Text[19, 25] chars:[19, 25, ": /uri"]
````````````````````````````````


```````````````````````````````` example Links: 60
[foo][ref[bar]]

[ref[bar]]: /uri
.
<p>[foo][ref[bar]]</p>
<p>[ref[bar]]: /uri</p>
.
Document[0, 34]
  Paragraph[0, 16] isTrailingBlankLine
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
    Text[5, 9] chars:[5, 9, "[ref"]
    LinkRef[9, 14] referenceOpen:[9, 10, "["] reference:[10, 13, "bar"] referenceClose:[13, 14, "]"]
      Text[10, 13] chars:[10, 13, "bar"]
    Text[14, 15] chars:[14, 15, "]"]
  Paragraph[17, 34]
    Text[17, 21] chars:[17, 21, "[ref"]
    LinkRef[21, 26] referenceOpen:[21, 22, "["] reference:[22, 25, "bar"] referenceClose:[25, 26, "]"]
      Text[22, 25] chars:[22, 25, "bar"]
    Text[26, 33] chars:[26, 33, "]: /uri"]
````````````````````````````````


```````````````````````````````` example Links: 61
[[[foo]]]

[[[foo]]]: /url
.
<p>[[[foo]]]</p>
<p>[[[foo]]]: /url</p>
.
Document[0, 27]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 2] chars:[0, 2, "[["]
    LinkRef[2, 7] referenceOpen:[2, 3, "["] reference:[3, 6, "foo"] referenceClose:[6, 7, "]"]
      Text[3, 6] chars:[3, 6, "foo"]
    Text[7, 9] chars:[7, 9, "]]"]
  Paragraph[11, 27]
    Text[11, 13] chars:[11, 13, "[["]
    LinkRef[13, 18] referenceOpen:[13, 14, "["] reference:[14, 17, "foo"] referenceClose:[17, 18, "]"]
      Text[14, 17] chars:[14, 17, "foo"]
    Text[18, 26] chars:[18, 26, "]]: /url"]
````````````````````````````````


```````````````````````````````` example Links: 62
[foo][ref\[]

[ref\[]: /uri
.
<p><a href="/uri">foo</a></p>
.
Document[0, 28]
  Paragraph[0, 13] isTrailingBlankLine
    LinkRef[0, 12] textOpen:[0, 1, "["] text:[1, 4, "foo"] textClose:[4, 5, "]"] referenceOpen:[5, 6, "["] reference:[6, 11, "ref\["] referenceClose:[11, 12, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
  Reference[14, 27] refOpen:[14, 15, "["] ref:[15, 20, "ref\["] refClose:[20, 22, "]:"] url:[23, 27, "/uri"]
````````````````````````````````


Note that in this example `]` is not backslash-escaped:

```````````````````````````````` example Links: 63
[bar\\]: /uri

[bar\\]
.
<p><a href="/uri">bar\</a></p>
.
Document[0, 23]
  Reference[0, 13] refOpen:[0, 1, "["] ref:[1, 6, "bar\\"] refClose:[6, 8, "]:"] url:[9, 13, "/uri"]
  Paragraph[15, 23]
    LinkRef[15, 22] referenceOpen:[15, 16, "["] reference:[16, 21, "bar\\"] referenceClose:[21, 22, "]"]
      Text[16, 21] chars:[16, 21, "bar\\"]
````````````````````````````````


A [link label] must contain at least one [non-whitespace character]:

```````````````````````````````` example Links: 64
[]

[]: /uri
.
<p>[]</p>
<p>[]: /uri</p>
.
Document[0, 13]
  Paragraph[0, 3] isTrailingBlankLine
    LinkRef[0, 2] referenceOpen:[0, 1, "["] reference:[1, 1] referenceClose:[1, 2, "]"]
  Paragraph[4, 13]
    LinkRef[4, 6] referenceOpen:[4, 5, "["] reference:[5, 5] referenceClose:[5, 6, "]"]
    Text[6, 12] chars:[6, 12, ": /uri"]
````````````````````````````````


```````````````````````````````` example Links: 65
[
 ]

[
 ]: /uri
.
<p>[
]</p>
<p>[
]: /uri</p>
.
Document[0, 17]
  Paragraph[0, 5] isTrailingBlankLine
    LinkRef[0, 4] referenceOpen:[0, 1, "["] reference:[3, 3] referenceClose:[3, 4, "]"]
      SoftLineBreak[1, 2]
  Paragraph[6, 17]
    LinkRef[6, 10] referenceOpen:[6, 7, "["] reference:[9, 9] referenceClose:[9, 10, "]"]
      SoftLineBreak[7, 8]
    Text[10, 16] chars:[10, 16, ": /uri"]
````````````````````````````````


A [collapsed reference link](@)
consists of a [link label] that [matches] a
[link reference definition] elsewhere in the
document, followed by the string `[]`.
The contents of the first link label are parsed as inlines,
which are used as the link's text.  The link's URI and title are
provided by the matching reference link definition.  Thus,
`[foo][]` is equivalent to `[foo][foo]`.

```````````````````````````````` example Links: 66
[foo][]

[foo]: /url "title"
.
<p><a href="/url" title="title">foo</a></p>
.
Document[0, 29]
  Paragraph[0, 8] isTrailingBlankLine
    LinkRef[0, 7] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"] textOpen:[5, 6, "["] textClose:[6, 7, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
  Reference[9, 28] refOpen:[9, 10, "["] ref:[10, 13, "foo"] refClose:[13, 15, "]:"] url:[16, 20, "/url"] titleOpen:[21, 22, "\""] title:[22, 27, "title"] titleClose:[27, 28, "\""]
````````````````````````````````


```````````````````````````````` example Links: 67
[*foo* bar][]

[*foo* bar]: /url "title"
.
<p><a href="/url" title="title"><em>foo</em> bar</a></p>
.
Document[0, 41]
  Paragraph[0, 14] isTrailingBlankLine
    LinkRef[0, 13] referenceOpen:[0, 1, "["] reference:[1, 10, "*foo* bar"] referenceClose:[10, 11, "]"] textOpen:[11, 12, "["] textClose:[12, 13, "]"]
      Emphasis[1, 6] textOpen:[1, 2, "*"] text:[2, 5, "foo"] textClose:[5, 6, "*"]
        Text[2, 5] chars:[2, 5, "foo"]
      Text[6, 10] chars:[6, 10, " bar"]
  Reference[15, 40] refOpen:[15, 16, "["] ref:[16, 25, "*foo* bar"] refClose:[25, 27, "]:"] url:[28, 32, "/url"] titleOpen:[33, 34, "\""] title:[34, 39, "title"] titleClose:[39, 40, "\""]
````````````````````````````````


The link labels are case-insensitive:

```````````````````````````````` example Links: 68
[Foo][]

[foo]: /url "title"
.
<p><a href="/url" title="title">Foo</a></p>
.
Document[0, 29]
  Paragraph[0, 8] isTrailingBlankLine
    LinkRef[0, 7] referenceOpen:[0, 1, "["] reference:[1, 4, "Foo"] referenceClose:[4, 5, "]"] textOpen:[5, 6, "["] textClose:[6, 7, "]"]
      Text[1, 4] chars:[1, 4, "Foo"]
  Reference[9, 28] refOpen:[9, 10, "["] ref:[10, 13, "foo"] refClose:[13, 15, "]:"] url:[16, 20, "/url"] titleOpen:[21, 22, "\""] title:[22, 27, "title"] titleClose:[27, 28, "\""]
````````````````````````````````



As with full reference links, [whitespace] is not
allowed between the two sets of brackets:

```````````````````````````````` example Links: 69
[foo] 
[]

[foo]: /url "title"
.
<p><a href="/url" title="title">foo</a>
[]</p>
.
Document[0, 31]
  Paragraph[0, 10] isTrailingBlankLine
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
    SoftLineBreak[6, 7]
    LinkRef[7, 9] referenceOpen:[7, 8, "["] reference:[8, 8] referenceClose:[8, 9, "]"]
  Reference[11, 30] refOpen:[11, 12, "["] ref:[12, 15, "foo"] refClose:[15, 17, "]:"] url:[18, 22, "/url"] titleOpen:[23, 24, "\""] title:[24, 29, "title"] titleClose:[29, 30, "\""]
````````````````````````````````


A [shortcut reference link](@)
consists of a [link label] that [matches] a
[link reference definition] elsewhere in the
document and is not followed by `[]` or a link label.
The contents of the first link label are parsed as inlines,
which are used as the link's text.  The link's URI and title
are provided by the matching link reference definition.
Thus, `[foo]` is equivalent to `[foo][]`.

```````````````````````````````` example Links: 70
[foo]

[foo]: /url "title"
.
<p><a href="/url" title="title">foo</a></p>
.
Document[0, 27]
  Paragraph[0, 6] isTrailingBlankLine
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
  Reference[7, 26] refOpen:[7, 8, "["] ref:[8, 11, "foo"] refClose:[11, 13, "]:"] url:[14, 18, "/url"] titleOpen:[19, 20, "\""] title:[20, 25, "title"] titleClose:[25, 26, "\""]
````````````````````````````````


```````````````````````````````` example Links: 71
[*foo* bar]

[*foo* bar]: /url "title"
.
<p><a href="/url" title="title"><em>foo</em> bar</a></p>
.
Document[0, 39]
  Paragraph[0, 12] isTrailingBlankLine
    LinkRef[0, 11] referenceOpen:[0, 1, "["] reference:[1, 10, "*foo* bar"] referenceClose:[10, 11, "]"]
      Emphasis[1, 6] textOpen:[1, 2, "*"] text:[2, 5, "foo"] textClose:[5, 6, "*"]
        Text[2, 5] chars:[2, 5, "foo"]
      Text[6, 10] chars:[6, 10, " bar"]
  Reference[13, 38] refOpen:[13, 14, "["] ref:[14, 23, "*foo* bar"] refClose:[23, 25, "]:"] url:[26, 30, "/url"] titleOpen:[31, 32, "\""] title:[32, 37, "title"] titleClose:[37, 38, "\""]
````````````````````````````````


```````````````````````````````` example Links: 72
[[*foo* bar]]

[*foo* bar]: /url "title"
.
<p>[<a href="/url" title="title"><em>foo</em> bar</a>]</p>
.
Document[0, 41]
  Paragraph[0, 14] isTrailingBlankLine
    Text[0, 1] chars:[0, 1, "["]
    LinkRef[1, 12] referenceOpen:[1, 2, "["] reference:[2, 11, "*foo* bar"] referenceClose:[11, 12, "]"]
      Emphasis[2, 7] textOpen:[2, 3, "*"] text:[3, 6, "foo"] textClose:[6, 7, "*"]
        Text[3, 6] chars:[3, 6, "foo"]
      Text[7, 11] chars:[7, 11, " bar"]
    Text[12, 13] chars:[12, 13, "]"]
  Reference[15, 40] refOpen:[15, 16, "["] ref:[16, 25, "*foo* bar"] refClose:[25, 27, "]:"] url:[28, 32, "/url"] titleOpen:[33, 34, "\""] title:[34, 39, "title"] titleClose:[39, 40, "\""]
````````````````````````````````


```````````````````````````````` example Links: 73
[[bar [foo]

[foo]: /url
.
<p>[[bar <a href="/url">foo</a></p>
.
Document[0, 25]
  Paragraph[0, 12] isTrailingBlankLine
    Text[0, 6] chars:[0, 6, "[[bar "]
    LinkRef[6, 11] referenceOpen:[6, 7, "["] reference:[7, 10, "foo"] referenceClose:[10, 11, "]"]
      Text[7, 10] chars:[7, 10, "foo"]
  Reference[13, 24] refOpen:[13, 14, "["] ref:[14, 17, "foo"] refClose:[17, 19, "]:"] url:[20, 24, "/url"]
````````````````````````````````


The link labels are case-insensitive:

```````````````````````````````` example Links: 74
[Foo]

[foo]: /url "title"
.
<p><a href="/url" title="title">Foo</a></p>
.
Document[0, 27]
  Paragraph[0, 6] isTrailingBlankLine
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "Foo"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "Foo"]
  Reference[7, 26] refOpen:[7, 8, "["] ref:[8, 11, "foo"] refClose:[11, 13, "]:"] url:[14, 18, "/url"] titleOpen:[19, 20, "\""] title:[20, 25, "title"] titleClose:[25, 26, "\""]
````````````````````````````````


A space after the link text should be preserved:

```````````````````````````````` example Links: 75
[foo] bar

[foo]: /url
.
<p><a href="/url">foo</a> bar</p>
.
Document[0, 23]
  Paragraph[0, 10] isTrailingBlankLine
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
    Text[5, 9] chars:[5, 9, " bar"]
  Reference[11, 22] refOpen:[11, 12, "["] ref:[12, 15, "foo"] refClose:[15, 17, "]:"] url:[18, 22, "/url"]
````````````````````````````````


If you just want bracketed text, you can backslash-escape the
opening bracket to avoid links:

```````````````````````````````` example Links: 76
\[foo]

[foo]: /url "title"
.
<p>[foo]</p>
.
Document[0, 28]
  Paragraph[0, 7] isTrailingBlankLine
    Text[0, 6] chars:[0, 6, "\[foo]"]
  Reference[8, 27] refOpen:[8, 9, "["] ref:[9, 12, "foo"] refClose:[12, 14, "]:"] url:[15, 19, "/url"] titleOpen:[20, 21, "\""] title:[21, 26, "title"] titleClose:[26, 27, "\""]
````````````````````````````````


Note that this is a link, because a link label ends with the first
following closing bracket:

```````````````````````````````` example Links: 77
[foo*]: /url

*[foo*]
.
<p>*<a href="/url">foo*</a></p>
.
Document[0, 22]
  Reference[0, 12] refOpen:[0, 1, "["] ref:[1, 5, "foo*"] refClose:[5, 7, "]:"] url:[8, 12, "/url"]
  Paragraph[14, 22]
    Text[14, 15] chars:[14, 15, "*"]
    LinkRef[15, 21] referenceOpen:[15, 16, "["] reference:[16, 20, "foo*"] referenceClose:[20, 21, "]"]
      Text[16, 20] chars:[16, 20, "foo*"]
````````````````````````````````


Full and compact references take precedence over shortcut
references:

```````````````````````````````` example Links: 78
[foo][bar]

[foo]: /url1
[bar]: /url2
.
<p><a href="/url2">foo</a></p>
.
Document[0, 38]
  Paragraph[0, 11] isTrailingBlankLine
    LinkRef[0, 10] textOpen:[0, 1, "["] text:[1, 4, "foo"] textClose:[4, 5, "]"] referenceOpen:[5, 6, "["] reference:[6, 9, "bar"] referenceClose:[9, 10, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
  Reference[12, 24] refOpen:[12, 13, "["] ref:[13, 16, "foo"] refClose:[16, 18, "]:"] url:[19, 24, "/url1"]
  Reference[25, 37] refOpen:[25, 26, "["] ref:[26, 29, "bar"] refClose:[29, 31, "]:"] url:[32, 37, "/url2"]
````````````````````````````````


```````````````````````````````` example Links: 79
[foo][]

[foo]: /url1
.
<p><a href="/url1">foo</a></p>
.
Document[0, 22]
  Paragraph[0, 8] isTrailingBlankLine
    LinkRef[0, 7] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"] textOpen:[5, 6, "["] textClose:[6, 7, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
  Reference[9, 21] refOpen:[9, 10, "["] ref:[10, 13, "foo"] refClose:[13, 15, "]:"] url:[16, 21, "/url1"]
````````````````````````````````


Inline links also take precedence:

```````````````````````````````` example Links: 80
[foo]()

[foo]: /url1
.
<p><a href="">foo</a></p>
.
Document[0, 22]
  Paragraph[0, 8] isTrailingBlankLine
    Link[0, 7] textOpen:[0, 1, "["] text:[1, 4, "foo"] textClose:[4, 5, "]"] linkOpen:[5, 6, "("] url:[6, 6] pageRef:[6, 6] linkClose:[6, 7, ")"]
      Text[1, 4] chars:[1, 4, "foo"]
  Reference[9, 21] refOpen:[9, 10, "["] ref:[10, 13, "foo"] refClose:[13, 15, "]:"] url:[16, 21, "/url1"]
````````````````````````````````


```````````````````````````````` example Links: 81
[foo](not a link)

[foo]: /url1
.
<p><a href="/url1">foo</a>(not a link)</p>
.
Document[0, 32]
  Paragraph[0, 18] isTrailingBlankLine
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "foo"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
    Text[5, 17] chars:[5, 17, "(not  … link)"]
  Reference[19, 31] refOpen:[19, 20, "["] ref:[20, 23, "foo"] refClose:[23, 25, "]:"] url:[26, 31, "/url1"]
````````````````````````````````


In the following case `[bar][baz]` is parsed as a reference,
`[foo]` as normal text:

```````````````````````````````` example Links: 82
[foo][bar][baz]

[baz]: /url
.
<p>[foo]<a href="/url">bar</a></p>
.
Document[0, 29]
  Paragraph[0, 16] isTrailingBlankLine
    Text[0, 5] chars:[0, 5, "[foo]"]
    LinkRef[5, 15] textOpen:[5, 6, "["] text:[6, 9, "bar"] textClose:[9, 10, "]"] referenceOpen:[10, 11, "["] reference:[11, 14, "baz"] referenceClose:[14, 15, "]"]
      Text[6, 9] chars:[6, 9, "bar"]
  Reference[17, 28] refOpen:[17, 18, "["] ref:[18, 21, "baz"] refClose:[21, 23, "]:"] url:[24, 28, "/url"]
````````````````````````````````


Here, though, `[foo][bar]` is parsed as a reference, since
`[bar]` is defined:

```````````````````````````````` example Links: 83
[foo][bar][baz]

[baz]: /url1
[bar]: /url2
.
<p><a href="/url2">foo</a><a href="/url1">baz</a></p>
.
Document[0, 43]
  Paragraph[0, 16] isTrailingBlankLine
    LinkRef[0, 10] textOpen:[0, 1, "["] text:[1, 4, "foo"] textClose:[4, 5, "]"] referenceOpen:[5, 6, "["] reference:[6, 9, "bar"] referenceClose:[9, 10, "]"]
      Text[1, 4] chars:[1, 4, "foo"]
    LinkRef[10, 15] referenceOpen:[10, 11, "["] reference:[11, 14, "baz"] referenceClose:[14, 15, "]"]
      Text[11, 14] chars:[11, 14, "baz"]
  Reference[17, 29] refOpen:[17, 18, "["] ref:[18, 21, "baz"] refClose:[21, 23, "]:"] url:[24, 29, "/url1"]
  Reference[30, 42] refOpen:[30, 31, "["] ref:[31, 34, "bar"] refClose:[34, 36, "]:"] url:[37, 42, "/url2"]
````````````````````````````````


Here `[foo]` is not parsed as a shortcut reference, because it
is followed by a link label (even though `[bar]` is not defined):

```````````````````````````````` example Links: 84
[foo][bar][baz]

[baz]: /url1
[foo]: /url2
.
<p>[foo]<a href="/url1">bar</a></p>
.
Document[0, 43]
  Paragraph[0, 16] isTrailingBlankLine
    Text[0, 5] chars:[0, 5, "[foo]"]
    LinkRef[5, 15] textOpen:[5, 6, "["] text:[6, 9, "bar"] textClose:[9, 10, "]"] referenceOpen:[10, 11, "["] reference:[11, 14, "baz"] referenceClose:[14, 15, "]"]
      Text[6, 9] chars:[6, 9, "bar"]
  Reference[17, 29] refOpen:[17, 18, "["] ref:[18, 21, "baz"] refClose:[21, 23, "]:"] url:[24, 29, "/url1"]
  Reference[30, 42] refOpen:[30, 31, "["] ref:[31, 34, "foo"] refClose:[34, 36, "]:"] url:[37, 42, "/url2"]
````````````````````````````````



## Images

Syntax for images is like the syntax for links, with one
difference. Instead of [link text], we have an
[image description](@).  The rules for this are the
same as for [link text], except that (a) an
image description starts with `![` rather than `[`, and
(b) an image description may contain links.
An image description has inline elements
as its contents.  When an image is rendered to HTML,
this is standardly used as the image's `alt` attribute.

```````````````````````````````` example Images: 1
![foo](/url "title")
.
<p><img src="/url" alt="foo" title="title" /></p>
.
Document[0, 21]
  Paragraph[0, 21]
    Image[0, 20] textOpen:[0, 2, "!["] text:[2, 5, "foo"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 11, "/url"] pageRef:[7, 11, "/url"] titleOpen:[12, 13, "\""] title:[13, 18, "title"] titleClose:[18, 19, "\""] linkClose:[19, 20, ")"]
      Text[2, 5] chars:[2, 5, "foo"]
````````````````````````````````


```````````````````````````````` example Images: 2
![foo *bar*]


[foo *bar*]: train.jpg "train & tracks"
.
<p><img src="train.jpg" alt="foo bar" title="train &amp; tracks" /></p>
.
Document[0, 55]
  Paragraph[0, 13] isTrailingBlankLine
    ImageRef[0, 12] referenceOpen:[0, 2, "!["] reference:[2, 11, "foo *bar*"] referenceClose:[11, 12, "]"]
      Text[2, 6] chars:[2, 6, "foo "]
      Emphasis[6, 11] textOpen:[6, 7, "*"] text:[7, 10, "bar"] textClose:[10, 11, "*"]
        Text[7, 10] chars:[7, 10, "bar"]
  Reference[15, 54] refOpen:[15, 16, "["] ref:[16, 25, "foo *bar*"] refClose:[25, 27, "]:"] url:[28, 37, "train.jpg"] titleOpen:[38, 39, "\""] title:[39, 53, "train & tracks"] titleClose:[53, 54, "\""]
````````````````````````````````


```````````````````````````````` example Images: 3
![foo ![bar](/url)](/url2)
.
<p><img src="/url2" alt="foo bar" /></p>
.
Document[0, 27]
  Paragraph[0, 27]
    Image[0, 26] textOpen:[0, 2, "!["] text:[2, 18, "foo ![bar](/url)"] textClose:[18, 19, "]"] linkOpen:[19, 20, "("] url:[20, 25, "/url2"] pageRef:[20, 25, "/url2"] linkClose:[25, 26, ")"]
      Text[2, 6] chars:[2, 6, "foo "]
      Image[6, 18] textOpen:[6, 8, "!["] text:[8, 11, "bar"] textClose:[11, 12, "]"] linkOpen:[12, 13, "("] url:[13, 17, "/url"] pageRef:[13, 17, "/url"] linkClose:[17, 18, ")"]
        Text[8, 11] chars:[8, 11, "bar"]
````````````````````````````````


```````````````````````````````` example Images: 4
![foo [bar](/url)](/url2)
.
<p><img src="/url2" alt="foo bar" /></p>
.
Document[0, 26]
  Paragraph[0, 26]
    Image[0, 25] textOpen:[0, 2, "!["] text:[2, 17, "foo [bar](/url)"] textClose:[17, 18, "]"] linkOpen:[18, 19, "("] url:[19, 24, "/url2"] pageRef:[19, 24, "/url2"] linkClose:[24, 25, ")"]
      Text[2, 6] chars:[2, 6, "foo "]
      Link[6, 17] textOpen:[6, 7, "["] text:[7, 10, "bar"] textClose:[10, 11, "]"] linkOpen:[11, 12, "("] url:[12, 16, "/url"] pageRef:[12, 16, "/url"] linkClose:[16, 17, ")"]
        Text[7, 10] chars:[7, 10, "bar"]
````````````````````````````````


Though this spec is concerned with parsing, not rendering, it is
recommended that in rendering to HTML, only the plain string content
of the [image description] be used.  Note that in
the above example, the alt attribute's value is `foo bar`, not `foo
[bar](/url)` or `foo <a href="/url">bar</a>`.  Only the plain string
content is rendered, without formatting.

```````````````````````````````` example Images: 5
![foo *bar*][]

[foo *bar*]: train.jpg "train & tracks"
.
<p><img src="train.jpg" alt="foo bar" title="train &amp; tracks" /></p>
.
Document[0, 56]
  Paragraph[0, 15] isTrailingBlankLine
    ImageRef[0, 14] referenceOpen:[0, 2, "!["] reference:[2, 11, "foo *bar*"] referenceClose:[11, 12, "]"] textOpen:[12, 13, "["] textClose:[13, 14, "]"]
      Text[2, 6] chars:[2, 6, "foo "]
      Emphasis[6, 11] textOpen:[6, 7, "*"] text:[7, 10, "bar"] textClose:[10, 11, "*"]
        Text[7, 10] chars:[7, 10, "bar"]
  Reference[16, 55] refOpen:[16, 17, "["] ref:[17, 26, "foo *bar*"] refClose:[26, 28, "]:"] url:[29, 38, "train.jpg"] titleOpen:[39, 40, "\""] title:[40, 54, "train & tracks"] titleClose:[54, 55, "\""]
````````````````````````````````


```````````````````````````````` example Images: 6
![foo *bar*][foobar]

[FOOBAR]: train.jpg "train & tracks"
.
<p><img src="train.jpg" alt="foo bar" title="train &amp; tracks" /></p>
.
Document[0, 59]
  Paragraph[0, 21] isTrailingBlankLine
    ImageRef[0, 20] textOpen:[0, 2, "!["] text:[2, 11, "foo *bar*"] textClose:[11, 12, "]"] referenceOpen:[12, 13, "["] reference:[13, 19, "foobar"] referenceClose:[19, 20, "]"]
      Text[2, 6] chars:[2, 6, "foo "]
      Emphasis[6, 11] textOpen:[6, 7, "*"] text:[7, 10, "bar"] textClose:[10, 11, "*"]
        Text[7, 10] chars:[7, 10, "bar"]
  Reference[22, 58] refOpen:[22, 23, "["] ref:[23, 29, "FOOBAR"] refClose:[29, 31, "]:"] url:[32, 41, "train.jpg"] titleOpen:[42, 43, "\""] title:[43, 57, "train & tracks"] titleClose:[57, 58, "\""]
````````````````````````````````


```````````````````````````````` example Images: 7
![foo](train.jpg)
.
<p><img src="train.jpg" alt="foo" /></p>
.
Document[0, 18]
  Paragraph[0, 18]
    Image[0, 17] textOpen:[0, 2, "!["] text:[2, 5, "foo"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 16, "train.jpg"] pageRef:[7, 16, "train.jpg"] linkClose:[16, 17, ")"]
      Text[2, 5] chars:[2, 5, "foo"]
````````````````````````````````


```````````````````````````````` example Images: 8
My ![foo bar](/path/to/train.jpg  "title"   )
.
<p>My <img src="/path/to/train.jpg" alt="foo bar" title="title" /></p>
.
Document[0, 46]
  Paragraph[0, 46]
    Text[0, 3] chars:[0, 3, "My "]
    Image[3, 45] textOpen:[3, 5, "!["] text:[5, 12, "foo bar"] textClose:[12, 13, "]"] linkOpen:[13, 14, "("] url:[14, 32, "/path/to/train.jpg"] pageRef:[14, 32, "/path/to/train.jpg"] titleOpen:[34, 35, "\""] title:[35, 40, "title"] titleClose:[40, 41, "\""] linkClose:[44, 45, ")"]
      Text[5, 12] chars:[5, 12, "foo bar"]
````````````````````````````````


```````````````````````````````` example Images: 9
![foo](<url>)
.
<p><img src="url" alt="foo" /></p>
.
Document[0, 14]
  Paragraph[0, 14]
    Image[0, 13] textOpen:[0, 2, "!["] text:[2, 5, "foo"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] urlOpen:[7, 8, "<"] url:[8, 11, "url"] urlClose:[11, 12, ">"] pageRef:[8, 11, "url"] linkClose:[12, 13, ")"]
      Text[2, 5] chars:[2, 5, "foo"]
````````````````````````````````


```````````````````````````````` example Images: 10
![](/url)
.
<p><img src="/url" alt="" /></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Image[0, 9] textOpen:[0, 2, "!["] text:[2, 2] textClose:[2, 3, "]"] linkOpen:[3, 4, "("] url:[4, 8, "/url"] pageRef:[4, 8, "/url"] linkClose:[8, 9, ")"]
````````````````````````````````


Reference-style:

```````````````````````````````` example Images: 11
![foo][bar]

[bar]: /url
.
<p><img src="/url" alt="foo" /></p>
.
Document[0, 25]
  Paragraph[0, 12] isTrailingBlankLine
    ImageRef[0, 11] textOpen:[0, 2, "!["] text:[2, 5, "foo"] textClose:[5, 6, "]"] referenceOpen:[6, 7, "["] reference:[7, 10, "bar"] referenceClose:[10, 11, "]"]
      Text[2, 5] chars:[2, 5, "foo"]
  Reference[13, 24] refOpen:[13, 14, "["] ref:[14, 17, "bar"] refClose:[17, 19, "]:"] url:[20, 24, "/url"]
````````````````````````````````


```````````````````````````````` example Images: 12
![foo][bar]

[BAR]: /url
.
<p><img src="/url" alt="foo" /></p>
.
Document[0, 25]
  Paragraph[0, 12] isTrailingBlankLine
    ImageRef[0, 11] textOpen:[0, 2, "!["] text:[2, 5, "foo"] textClose:[5, 6, "]"] referenceOpen:[6, 7, "["] reference:[7, 10, "bar"] referenceClose:[10, 11, "]"]
      Text[2, 5] chars:[2, 5, "foo"]
  Reference[13, 24] refOpen:[13, 14, "["] ref:[14, 17, "BAR"] refClose:[17, 19, "]:"] url:[20, 24, "/url"]
````````````````````````````````


Collapsed:

```````````````````````````````` example Images: 13
![foo][]

[foo]: /url "title"
.
<p><img src="/url" alt="foo" title="title" /></p>
.
Document[0, 30]
  Paragraph[0, 9] isTrailingBlankLine
    ImageRef[0, 8] referenceOpen:[0, 2, "!["] reference:[2, 5, "foo"] referenceClose:[5, 6, "]"] textOpen:[6, 7, "["] textClose:[7, 8, "]"]
      Text[2, 5] chars:[2, 5, "foo"]
  Reference[10, 29] refOpen:[10, 11, "["] ref:[11, 14, "foo"] refClose:[14, 16, "]:"] url:[17, 21, "/url"] titleOpen:[22, 23, "\""] title:[23, 28, "title"] titleClose:[28, 29, "\""]
````````````````````````````````


```````````````````````````````` example Images: 14
![*foo* bar][]

[*foo* bar]: /url "title"
.
<p><img src="/url" alt="foo bar" title="title" /></p>
.
Document[0, 42]
  Paragraph[0, 15] isTrailingBlankLine
    ImageRef[0, 14] referenceOpen:[0, 2, "!["] reference:[2, 11, "*foo* bar"] referenceClose:[11, 12, "]"] textOpen:[12, 13, "["] textClose:[13, 14, "]"]
      Emphasis[2, 7] textOpen:[2, 3, "*"] text:[3, 6, "foo"] textClose:[6, 7, "*"]
        Text[3, 6] chars:[3, 6, "foo"]
      Text[7, 11] chars:[7, 11, " bar"]
  Reference[16, 41] refOpen:[16, 17, "["] ref:[17, 26, "*foo* bar"] refClose:[26, 28, "]:"] url:[29, 33, "/url"] titleOpen:[34, 35, "\""] title:[35, 40, "title"] titleClose:[40, 41, "\""]
````````````````````````````````


The labels are case-insensitive:

```````````````````````````````` example Images: 15
![Foo][]

[foo]: /url "title"
.
<p><img src="/url" alt="Foo" title="title" /></p>
.
Document[0, 30]
  Paragraph[0, 9] isTrailingBlankLine
    ImageRef[0, 8] referenceOpen:[0, 2, "!["] reference:[2, 5, "Foo"] referenceClose:[5, 6, "]"] textOpen:[6, 7, "["] textClose:[7, 8, "]"]
      Text[2, 5] chars:[2, 5, "Foo"]
  Reference[10, 29] refOpen:[10, 11, "["] ref:[11, 14, "foo"] refClose:[14, 16, "]:"] url:[17, 21, "/url"] titleOpen:[22, 23, "\""] title:[23, 28, "title"] titleClose:[28, 29, "\""]
````````````````````````````````


As with reference links, [whitespace] is not allowed
between the two sets of brackets:

```````````````````````````````` example Images: 16
![foo] 
[]

[foo]: /url "title"
.
<p><img src="/url" alt="foo" title="title" />
[]</p>
.
Document[0, 32]
  Paragraph[0, 11] isTrailingBlankLine
    ImageRef[0, 6] referenceOpen:[0, 2, "!["] reference:[2, 5, "foo"] referenceClose:[5, 6, "]"]
      Text[2, 5] chars:[2, 5, "foo"]
    SoftLineBreak[7, 8]
    LinkRef[8, 10] referenceOpen:[8, 9, "["] reference:[9, 9] referenceClose:[9, 10, "]"]
  Reference[12, 31] refOpen:[12, 13, "["] ref:[13, 16, "foo"] refClose:[16, 18, "]:"] url:[19, 23, "/url"] titleOpen:[24, 25, "\""] title:[25, 30, "title"] titleClose:[30, 31, "\""]
````````````````````````````````


Shortcut:

```````````````````````````````` example Images: 17
![foo]

[foo]: /url "title"
.
<p><img src="/url" alt="foo" title="title" /></p>
.
Document[0, 28]
  Paragraph[0, 7] isTrailingBlankLine
    ImageRef[0, 6] referenceOpen:[0, 2, "!["] reference:[2, 5, "foo"] referenceClose:[5, 6, "]"]
      Text[2, 5] chars:[2, 5, "foo"]
  Reference[8, 27] refOpen:[8, 9, "["] ref:[9, 12, "foo"] refClose:[12, 14, "]:"] url:[15, 19, "/url"] titleOpen:[20, 21, "\""] title:[21, 26, "title"] titleClose:[26, 27, "\""]
````````````````````````````````


```````````````````````````````` example Images: 18
![*foo* bar]

[*foo* bar]: /url "title"
.
<p><img src="/url" alt="foo bar" title="title" /></p>
.
Document[0, 40]
  Paragraph[0, 13] isTrailingBlankLine
    ImageRef[0, 12] referenceOpen:[0, 2, "!["] reference:[2, 11, "*foo* bar"] referenceClose:[11, 12, "]"]
      Emphasis[2, 7] textOpen:[2, 3, "*"] text:[3, 6, "foo"] textClose:[6, 7, "*"]
        Text[3, 6] chars:[3, 6, "foo"]
      Text[7, 11] chars:[7, 11, " bar"]
  Reference[14, 39] refOpen:[14, 15, "["] ref:[15, 24, "*foo* bar"] refClose:[24, 26, "]:"] url:[27, 31, "/url"] titleOpen:[32, 33, "\""] title:[33, 38, "title"] titleClose:[38, 39, "\""]
````````````````````````````````


Note that link labels cannot contain unescaped brackets:

```````````````````````````````` example Images: 19
![[foo]]

[[foo]]: /url "title"
.
<p>![[foo]]</p>
<p>[[foo]]: /url &quot;title&quot;</p>
.
Document[0, 32]
  Paragraph[0, 9] isTrailingBlankLine
    Text[0, 2] chars:[0, 2, "!["]
    LinkRef[2, 7] referenceOpen:[2, 3, "["] reference:[3, 6, "foo"] referenceClose:[6, 7, "]"]
      Text[3, 6] chars:[3, 6, "foo"]
    Text[7, 8] chars:[7, 8, "]"]
  Paragraph[10, 32]
    Text[10, 11] chars:[10, 11, "["]
    LinkRef[11, 16] referenceOpen:[11, 12, "["] reference:[12, 15, "foo"] referenceClose:[15, 16, "]"]
      Text[12, 15] chars:[12, 15, "foo"]
    Text[16, 31] chars:[16, 31, "]: /u … itle\""]
````````````````````````````````


The link labels are case-insensitive:

```````````````````````````````` example Images: 20
![Foo]

[foo]: /url "title"
.
<p><img src="/url" alt="Foo" title="title" /></p>
.
Document[0, 28]
  Paragraph[0, 7] isTrailingBlankLine
    ImageRef[0, 6] referenceOpen:[0, 2, "!["] reference:[2, 5, "Foo"] referenceClose:[5, 6, "]"]
      Text[2, 5] chars:[2, 5, "Foo"]
  Reference[8, 27] refOpen:[8, 9, "["] ref:[9, 12, "foo"] refClose:[12, 14, "]:"] url:[15, 19, "/url"] titleOpen:[20, 21, "\""] title:[21, 26, "title"] titleClose:[26, 27, "\""]
````````````````````````````````


If you just want a literal `!` followed by bracketed text, you can
backslash-escape the opening `[`:

```````````````````````````````` example Images: 21
!\[foo]

[foo]: /url "title"
.
<p>![foo]</p>
.
Document[0, 29]
  Paragraph[0, 8] isTrailingBlankLine
    Text[0, 7] chars:[0, 7, "!\[foo]"]
  Reference[9, 28] refOpen:[9, 10, "["] ref:[10, 13, "foo"] refClose:[13, 15, "]:"] url:[16, 20, "/url"] titleOpen:[21, 22, "\""] title:[22, 27, "title"] titleClose:[27, 28, "\""]
````````````````````````````````


If you want a link after a literal `!`, backslash-escape the
`!`:

```````````````````````````````` example Images: 22
\![foo]

[foo]: /url "title"
.
<p>!<a href="/url" title="title">foo</a></p>
.
Document[0, 29]
  Paragraph[0, 8] isTrailingBlankLine
    Text[0, 2] chars:[0, 2, "\!"]
    LinkRef[2, 7] referenceOpen:[2, 3, "["] reference:[3, 6, "foo"] referenceClose:[6, 7, "]"]
      Text[3, 6] chars:[3, 6, "foo"]
  Reference[9, 28] refOpen:[9, 10, "["] ref:[10, 13, "foo"] refClose:[13, 15, "]:"] url:[16, 20, "/url"] titleOpen:[21, 22, "\""] title:[22, 27, "title"] titleClose:[27, 28, "\""]
````````````````````````````````


## Autolinks

[Autolink](@)s are absolute URIs and email addresses inside
`<` and `>`. They are parsed as links, with the URL or email address
as the link label.

A [URI autolink](@) consists of `<`, followed by an
[absolute URI] not containing `<`, followed by `>`.  It is parsed as
a link to the URI, with the URI as the link's label.

An [absolute URI](@),
for these purposes, consists of a [scheme] followed by a colon (`:`)
followed by zero or more characters other than ASCII
[whitespace] and control characters, `<`, and `>`.  If
the URI includes these characters, they must be percent-encoded
(e.g. `%20` for a space).

For purposes of this spec, a [scheme](@) is any sequence
of 2--32 characters beginning with an ASCII letter and followed
by any combination of ASCII letters, digits, or the symbols plus
("+"), period ("."), or hyphen ("-").

Here are some valid autolinks:

```````````````````````````````` example Autolinks: 1
<http://foo.bar.baz>
.
<p><a href="http://foo.bar.baz">http://foo.bar.baz</a></p>
.
Document[0, 21]
  Paragraph[0, 21]
    AutoLink[0, 20] open:[0, 1, "<"] text:[1, 19, "http://foo.bar.baz"] pageRef:[1, 19, "http://foo.bar.baz"] close:[19, 20, ">"]
````````````````````````````````


```````````````````````````````` example Autolinks: 2
<http://foo.bar.baz/test?q=hello&id=22&boolean>
.
<p><a href="http://foo.bar.baz/test?q=hello&amp;id=22&amp;boolean">http://foo.bar.baz/test?q=hello&amp;id=22&amp;boolean</a></p>
.
Document[0, 48]
  Paragraph[0, 48]
    AutoLink[0, 47] open:[0, 1, "<"] text:[1, 46, "http://foo.bar.baz/test?q=hello&id=22&boolean"] pageRef:[1, 46, "http://foo.bar.baz/test?q=hello&id=22&boolean"] close:[46, 47, ">"]
````````````````````````````````


```````````````````````````````` example Autolinks: 3
<irc://foo.bar:2233/baz>
.
<p><a href="irc://foo.bar:2233/baz">irc://foo.bar:2233/baz</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    AutoLink[0, 24] open:[0, 1, "<"] text:[1, 23, "irc://foo.bar:2233/baz"] pageRef:[1, 23, "irc://foo.bar:2233/baz"] close:[23, 24, ">"]
````````````````````````````````


Uppercase is also fine:

```````````````````````````````` example Autolinks: 4
<MAILTO:FOO@BAR.BAZ>
.
<p><a href="MAILTO:FOO@BAR.BAZ">MAILTO:FOO@BAR.BAZ</a></p>
.
Document[0, 21]
  Paragraph[0, 21]
    AutoLink[0, 20] open:[0, 1, "<"] text:[1, 19, "MAILTO:FOO@BAR.BAZ"] pageRef:[1, 19, "MAILTO:FOO@BAR.BAZ"] close:[19, 20, ">"]
````````````````````````````````


Note that many strings that count as [absolute URIs] for
purposes of this spec are not valid URIs, because their
schemes are not registered or because of other problems
with their syntax:

```````````````````````````````` example Autolinks: 5
<a+b+c:d>
.
<p><a href="a+b+c:d">a+b+c:d</a></p>
.
Document[0, 10]
  Paragraph[0, 10]
    AutoLink[0, 9] open:[0, 1, "<"] text:[1, 8, "a+b+c:d"] pageRef:[1, 8, "a+b+c:d"] close:[8, 9, ">"]
````````````````````````````````


```````````````````````````````` example Autolinks: 6
<made-up-scheme://foo,bar>
.
<p><a href="made-up-scheme://foo,bar">made-up-scheme://foo,bar</a></p>
.
Document[0, 27]
  Paragraph[0, 27]
    AutoLink[0, 26] open:[0, 1, "<"] text:[1, 25, "made-up-scheme://foo,bar"] pageRef:[1, 25, "made-up-scheme://foo,bar"] close:[25, 26, ">"]
````````````````````````````````


```````````````````````````````` example Autolinks: 7
<http://../>
.
<p><a href="http://../">http://../</a></p>
.
Document[0, 13]
  Paragraph[0, 13]
    AutoLink[0, 12] open:[0, 1, "<"] text:[1, 11, "http://../"] pageRef:[1, 11, "http://../"] close:[11, 12, ">"]
````````````````````````````````


```````````````````````````````` example Autolinks: 8
<localhost:5001/foo>
.
<p><a href="localhost:5001/foo">localhost:5001/foo</a></p>
.
Document[0, 21]
  Paragraph[0, 21]
    AutoLink[0, 20] open:[0, 1, "<"] text:[1, 19, "localhost:5001/foo"] pageRef:[1, 19, "localhost:5001/foo"] close:[19, 20, ">"]
````````````````````````````````


Spaces are not allowed in autolinks:

```````````````````````````````` example Autolinks: 9
<http://foo.bar/baz bim>
.
<p>&lt;http://foo.bar/baz bim&gt;</p>
.
Document[0, 25]
  Paragraph[0, 25]
    Text[0, 24] chars:[0, 24, "<http …  bim>"]
````````````````````````````````


Backslash-escapes do not work inside autolinks:

```````````````````````````````` example Autolinks: 10
<http://example.com/\[\>
.
<p><a href="http://example.com/%5C%5B%5C">http://example.com/\[\</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    AutoLink[0, 24] open:[0, 1, "<"] text:[1, 23, "http://example.com/\[\"] pageRef:[1, 23, "http://example.com/\[\"] close:[23, 24, ">"]
````````````````````````````````


An [email autolink](@)
consists of `<`, followed by an [email address],
followed by `>`.  The link's label is the email address,
and the URL is `mailto:` followed by the email address.

An [email address](@),
for these purposes, is anything that matches
the [non-normative regex from the HTML5
spec](https://html.spec.whatwg.org/multipage/forms.html#e-mail-state-(type=email)):

    /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?
    (?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/

Examples of email autolinks:

```````````````````````````````` example Autolinks: 11
<foo@bar.example.com>
.
<p><a href="mailto:foo@bar.example.com">foo@bar.example.com</a></p>
.
Document[0, 22]
  Paragraph[0, 22]
    MailLink[0, 21] textOpen:[0, 1, "<"] text:[1, 20, "foo@bar.example.com"] textClose:[20, 21, ">"]
````````````````````````````````


```````````````````````````````` example Autolinks: 12
<foo+special@Bar.baz-bar0.com>
.
<p><a href="mailto:foo+special@Bar.baz-bar0.com">foo+special@Bar.baz-bar0.com</a></p>
.
Document[0, 31]
  Paragraph[0, 31]
    MailLink[0, 30] textOpen:[0, 1, "<"] text:[1, 29, "foo+special@Bar.baz-bar0.com"] textClose:[29, 30, ">"]
````````````````````````````````


Backslash-escapes do not work inside email autolinks:

```````````````````````````````` example Autolinks: 13
<foo\+@bar.example.com>
.
<p>&lt;foo+@bar.example.com&gt;</p>
.
Document[0, 24]
  Paragraph[0, 24]
    Text[0, 23] chars:[0, 23, "<foo\ … .com>"]
````````````````````````````````


These are not autolinks:

```````````````````````````````` example Autolinks: 14
<>
.
<p>&lt;&gt;</p>
.
Document[0, 3]
  Paragraph[0, 3]
    Text[0, 2] chars:[0, 2, "<>"]
````````````````````````````````


```````````````````````````````` example Autolinks: 15
< http://foo.bar >
.
<p>&lt; http://foo.bar &gt;</p>
.
Document[0, 19]
  Paragraph[0, 19]
    Text[0, 18] chars:[0, 18, "< htt … bar >"]
````````````````````````````````


```````````````````````````````` example Autolinks: 16
<m:abc>
.
<p>&lt;m:abc&gt;</p>
.
Document[0, 8]
  Paragraph[0, 8]
    Text[0, 7] chars:[0, 7, "<m:abc>"]
````````````````````````````````


```````````````````````````````` example Autolinks: 17
<foo.bar.baz>
.
<p>&lt;foo.bar.baz&gt;</p>
.
Document[0, 14]
  Paragraph[0, 14]
    Text[0, 13] chars:[0, 13, "<foo. … .baz>"]
````````````````````````````````


```````````````````````````````` example Autolinks: 18
http://example.com
.
<p>http://example.com</p>
.
Document[0, 19]
  Paragraph[0, 19]
    Text[0, 18] chars:[0, 18, "http: … e.com"]
````````````````````````````````


```````````````````````````````` example Autolinks: 19
foo@bar.example.com
.
<p>foo@bar.example.com</p>
.
Document[0, 20]
  Paragraph[0, 20]
    Text[0, 19] chars:[0, 19, "foo@b … e.com"]
````````````````````````````````


## Raw HTML

Text between `<` and `>` that looks like an HTML tag is parsed as a
raw HTML tag and will be rendered in HTML without escaping.
Tag and attribute names are not limited to current HTML tags,
so custom tags (and even, say, DocBook tags) may be used.

Here is the grammar for tags:

A [tag name](@) consists of an ASCII letter
followed by zero or more ASCII letters, digits, or
hyphens (`-`).

An [attribute](@) consists of [whitespace],
an [attribute name], and an optional
[attribute value specification].

An [attribute name](@)
consists of an ASCII letter, `_`, or `:`, followed by zero or more ASCII
letters, digits, `_`, `.`, `:`, or `-`.  (Note:  This is the XML
specification restricted to ASCII.  HTML5 is laxer.)

An [attribute value specification](@)
consists of optional [whitespace],
a `=` character, optional [whitespace], and an [attribute
value].

An [attribute value](@)
consists of an [unquoted attribute value],
a [single-quoted attribute value], or a [double-quoted attribute value].

An [unquoted attribute value](@)
is a nonempty string of characters not
including spaces, `"`, `'`, `=`, `<`, `>`, or `` ` ``.

A [single-quoted attribute value](@)
consists of `'`, zero or more
characters not including `'`, and a final `'`.

A [double-quoted attribute value](@)
consists of `"`, zero or more
characters not including `"`, and a final `"`.

An [open tag](@) consists of a `<` character, a [tag name],
zero or more [attributes], optional [whitespace], an optional `/`
character, and a `>` character.

A [closing tag](@) consists of the string `</`, a
[tag name], optional [whitespace], and the character `>`.

An [HTML comment](@) consists of `<!--` + *text* + `-->`,
where *text* does not start with `>` or `->`, does not end with `-`,
and does not contain `--`.  (See the
[HTML5 spec](http://www.w3.org/TR/html5/syntax.html#comments).)

A [processing instruction](@)
consists of the string `<?`, a string
of characters not including the string `?>`, and the string
`?>`.

A [declaration](@) consists of the
string `<!`, a name consisting of one or more uppercase ASCII letters,
[whitespace], a string of characters not including the
character `>`, and the character `>`.

A [CDATA section](@) consists of
the string `<![CDATA[`, a string of characters not including the string
`]]>`, and the string `]]>`.

An [HTML tag](@) consists of an [open tag], a [closing tag],
an [HTML comment], a [processing instruction], a [declaration],
or a [CDATA section].

Here are some simple open tags:

```````````````````````````````` example Raw HTML: 1
<a><bab><c2c>
.
<p><a><bab><c2c></p>
.
Document[0, 14]
  Paragraph[0, 14]
    HtmlInline[0, 3] chars:[0, 3, "<a>"]
    HtmlInline[3, 8] chars:[3, 8, "<bab>"]
    HtmlInline[8, 13] chars:[8, 13, "<c2c>"]
````````````````````````````````


Empty elements:

```````````````````````````````` example Raw HTML: 2
<a/><b2/>
.
<p><a/><b2/></p>
.
Document[0, 10]
  Paragraph[0, 10]
    HtmlInline[0, 4] chars:[0, 4, "<a/>"]
    HtmlInline[4, 9] chars:[4, 9, "<b2/>"]
````````````````````````````````


[Whitespace] is allowed:

```````````````````````````````` example Raw HTML: 3
<a  /><b2
data="foo" >
.
<p><a  /><b2
data="foo" ></p>
.
Document[0, 23]
  Paragraph[0, 23]
    HtmlInline[0, 6] chars:[0, 6, "<a  />"]
    HtmlInline[6, 22] chars:[6, 22, "<b2\nd … oo\" >"]
````````````````````````````````


With attributes:

```````````````````````````````` example Raw HTML: 4
<a foo="bar" bam = 'baz <em>"</em>'
_boolean zoop:33=zoop:33 />
.
<p><a foo="bar" bam = 'baz <em>"</em>'
_boolean zoop:33=zoop:33 /></p>
.
Document[0, 64]
  Paragraph[0, 64]
    HtmlInline[0, 63] chars:[0, 63, "<a fo … 33 />"]
````````````````````````````````


Custom tag names can be used:

```````````````````````````````` example Raw HTML: 5
Foo <responsive-image src="foo.jpg" />
.
<p>Foo <responsive-image src="foo.jpg" /></p>
.
Document[0, 39]
  Paragraph[0, 39]
    Text[0, 4] chars:[0, 4, "Foo "]
    HtmlInline[4, 38] chars:[4, 38, "<resp … g\" />"]
````````````````````````````````


Illegal tag names, not parsed as HTML:

```````````````````````````````` example Raw HTML: 6
<33> <__>
.
<p>&lt;33&gt; &lt;__&gt;</p>
.
Document[0, 10]
  Paragraph[0, 10]
    Text[0, 9] chars:[0, 9, "<33> <__>"]
````````````````````````````````


Illegal attribute names:

```````````````````````````````` example Raw HTML: 7
<a h*#ref="hi">
.
<p>&lt;a h*#ref=&quot;hi&quot;&gt;</p>
.
Document[0, 16]
  Paragraph[0, 16]
    Text[0, 15] chars:[0, 15, "<a h* … \"hi\">"]
````````````````````````````````


Illegal attribute values:

```````````````````````````````` example Raw HTML: 8
<a href="hi'> <a href=hi'>
.
<p>&lt;a href=&quot;hi'&gt; &lt;a href=hi'&gt;</p>
.
Document[0, 27]
  Paragraph[0, 27]
    Text[0, 26] chars:[0, 26, "<a hr … =hi'>"]
````````````````````````````````


Illegal [whitespace]:

```````````````````````````````` example Raw HTML: 9
< a><
foo><bar/ >
.
<p>&lt; a&gt;&lt;
foo&gt;&lt;bar/ &gt;</p>
.
Document[0, 18]
  Paragraph[0, 18]
    Text[0, 5] chars:[0, 5, "< a><"]
    SoftLineBreak[5, 6]
    Text[6, 17] chars:[6, 17, "foo>< … ar/ >"]
````````````````````````````````


Missing [whitespace]:

```````````````````````````````` example Raw HTML: 10
<a href='bar'title=title>
.
<p>&lt;a href='bar'title=title&gt;</p>
.
Document[0, 26]
  Paragraph[0, 26]
    Text[0, 25] chars:[0, 25, "<a hr … itle>"]
````````````````````````````````


Closing tags:

```````````````````````````````` example Raw HTML: 11
</a></foo >
.
<p></a></foo ></p>
.
Document[0, 12]
  Paragraph[0, 12]
    HtmlInline[0, 4] chars:[0, 4, "</a>"]
    HtmlInline[4, 11] chars:[4, 11, "</foo >"]
````````````````````````````````


Illegal attributes in closing tag:

```````````````````````````````` example Raw HTML: 12
</a href="foo">
.
<p>&lt;/a href=&quot;foo&quot;&gt;</p>
.
Document[0, 16]
  Paragraph[0, 16]
    Text[0, 15] chars:[0, 15, "</a h … foo\">"]
````````````````````````````````


Comments:

```````````````````````````````` example Raw HTML: 13
foo <!-- this is a
comment - with hyphen -->
.
<p>foo <!-- this is a
comment - with hyphen --></p>
.
Document[0, 45]
  Paragraph[0, 45]
    Text[0, 4] chars:[0, 4, "foo "]
    HtmlInlineComment[4, 44] chars:[4, 44, "<!--  … n -->"]
````````````````````````````````


```````````````````````````````` example Raw HTML: 14
foo <!-- not a comment -- two hyphens -->
.
<p>foo &lt;!-- not a comment -- two hyphens --&gt;</p>
.
Document[0, 42]
  Paragraph[0, 42]
    Text[0, 41] chars:[0, 41, "foo < … s -->"]
````````````````````````````````


Not comments:

```````````````````````````````` example Raw HTML: 15
foo <!--> foo -->

foo <!-- foo--->
.
<p>foo &lt;!--&gt; foo --&gt;</p>
<p>foo &lt;!-- foo---&gt;</p>
.
Document[0, 36]
  Paragraph[0, 18] isTrailingBlankLine
    Text[0, 17] chars:[0, 17, "foo < … o -->"]
  Paragraph[19, 36]
    Text[19, 35] chars:[19, 35, "foo < … o--->"]
````````````````````````````````


Processing instructions:

```````````````````````````````` example Raw HTML: 16
foo <?php echo $a; ?>
.
<p>foo <?php echo $a; ?></p>
.
Document[0, 22]
  Paragraph[0, 22]
    Text[0, 4] chars:[0, 4, "foo "]
    HtmlInline[4, 21] chars:[4, 21, "<?php … a; ?>"]
````````````````````````````````


Declarations:

```````````````````````````````` example Raw HTML: 17
foo <!ELEMENT br EMPTY>
.
<p>foo <!ELEMENT br EMPTY></p>
.
Document[0, 24]
  Paragraph[0, 24]
    Text[0, 4] chars:[0, 4, "foo "]
    HtmlInline[4, 23] chars:[4, 23, "<!ELE … MPTY>"]
````````````````````````````````


CDATA sections:

```````````````````````````````` example Raw HTML: 18
foo <![CDATA[>&<]]>
.
<p>foo <![CDATA[>&<]]></p>
.
Document[0, 20]
  Paragraph[0, 20]
    Text[0, 4] chars:[0, 4, "foo "]
    HtmlInline[4, 19] chars:[4, 19, "<![CD … &<]]>"]
````````````````````````````````


Entity and numeric character references are preserved in HTML
attributes:

```````````````````````````````` example Raw HTML: 19
foo <a href="&ouml;">
.
<p>foo <a href="&ouml;"></p>
.
Document[0, 22]
  Paragraph[0, 22]
    Text[0, 4] chars:[0, 4, "foo "]
    HtmlInline[4, 21] chars:[4, 21, "<a hr … ml;\">"]
````````````````````````````````


Backslash escapes do not work in HTML attributes:

```````````````````````````````` example Raw HTML: 20
foo <a href="\*">
.
<p>foo <a href="\*"></p>
.
Document[0, 18]
  Paragraph[0, 18]
    Text[0, 4] chars:[0, 4, "foo "]
    HtmlInline[4, 17] chars:[4, 17, "<a hr … \"\*\">"]
````````````````````````````````


```````````````````````````````` example Raw HTML: 21
<a href="\"">
.
<p>&lt;a href=&quot;&quot;&quot;&gt;</p>
.
Document[0, 14]
  Paragraph[0, 14]
    Text[0, 13] chars:[0, 13, "<a hr … \"\\"\">"]
````````````````````````````````


## Hard line breaks

A line break (not in a code span or HTML tag) that is preceded
by two or more spaces and does not occur at the end of a block
is parsed as a [hard line break](@) (rendered
in HTML as a `<br />` tag):

```````````````````````````````` example Hard line breaks: 1
foo  
baz
.
<p>foo<br />
baz</p>
.
Document[0, 10]
  Paragraph[0, 10]
    Text[0, 3] chars:[0, 3, "foo"]
    HardLineBreak[3, 6]
    Text[6, 9] chars:[6, 9, "baz"]
````````````````````````````````


For a more visible alternative, a backslash before the
[line ending] may be used instead of two spaces:

```````````````````````````````` example Hard line breaks: 2
foo\
baz
.
<p>foo<br />
baz</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 3] chars:[0, 3, "foo"]
    HardLineBreak[3, 5]
    Text[5, 8] chars:[5, 8, "baz"]
````````````````````````````````


More than two spaces can be used:

```````````````````````````````` example Hard line breaks: 3
foo       
baz
.
<p>foo<br />
baz</p>
.
Document[0, 15]
  Paragraph[0, 15]
    Text[0, 3] chars:[0, 3, "foo"]
    HardLineBreak[3, 11]
    Text[11, 14] chars:[11, 14, "baz"]
````````````````````````````````


Leading spaces at the beginning of the next line are ignored:

```````````````````````````````` example Hard line breaks: 4
foo  
     bar
.
<p>foo<br />
bar</p>
.
Document[0, 15]
  Paragraph[0, 15]
    Text[0, 3] chars:[0, 3, "foo"]
    HardLineBreak[3, 6]
    Text[11, 14] chars:[11, 14, "bar"]
````````````````````````````````


```````````````````````````````` example Hard line breaks: 5
foo\
     bar
.
<p>foo<br />
bar</p>
.
Document[0, 14]
  Paragraph[0, 14]
    Text[0, 3] chars:[0, 3, "foo"]
    HardLineBreak[3, 5]
    Text[10, 13] chars:[10, 13, "     bar"]
````````````````````````````````


Line breaks can occur inside emphasis, links, and other constructs
that allow inline content:

```````````````````````````````` example Hard line breaks: 6
*foo  
bar*
.
<p><em>foo<br />
bar</em></p>
.
Document[0, 12]
  Paragraph[0, 12]
    Emphasis[0, 11] textOpen:[0, 1, "*"] text:[1, 10, "foo  \nbar"] textClose:[10, 11, "*"]
      Text[1, 4] chars:[1, 4, "foo"]
      HardLineBreak[4, 7]
      Text[7, 10] chars:[7, 10, "bar"]
````````````````````````````````


```````````````````````````````` example Hard line breaks: 7
*foo\
bar*
.
<p><em>foo<br />
bar</em></p>
.
Document[0, 11]
  Paragraph[0, 11]
    Emphasis[0, 10] textOpen:[0, 1, "*"] text:[1, 9, "foo\\nbar"] textClose:[9, 10, "*"]
      Text[1, 4] chars:[1, 4, "foo"]
      HardLineBreak[4, 6]
      Text[6, 9] chars:[6, 9, "bar"]
````````````````````````````````


Line breaks do not occur inside code spans

```````````````````````````````` example Hard line breaks: 8
`code  
span`
.
<p><code>code span</code></p>
.
Document[0, 14]
  Paragraph[0, 14]
    Code[0, 13] textOpen:[0, 1, "`"] text:[1, 12, "code  …  \nspan"] textClose:[12, 13, "`"]
      Text[1, 12] chars:[1, 12, "code  … \nspan"]
````````````````````````````````


```````````````````````````````` example Hard line breaks: 9
`code\
span`
.
<p><code>code\ span</code></p>
.
Document[0, 13]
  Paragraph[0, 13]
    Code[0, 12] textOpen:[0, 1, "`"] text:[1, 11, "code\\nspan"] textClose:[11, 12, "`"]
      Text[1, 11] chars:[1, 11, "code\\nspan"]
````````````````````````````````


or HTML tags:

```````````````````````````````` example Hard line breaks: 10
<a href="foo  
bar">
.
<p><a href="foo  
bar"></p>
.
Document[0, 21]
  Paragraph[0, 21]
    HtmlInline[0, 20] chars:[0, 20, "<a hr … bar\">"]
````````````````````````````````


```````````````````````````````` example Hard line breaks: 11
<a href="foo\
bar">
.
<p><a href="foo\
bar"></p>
.
Document[0, 20]
  Paragraph[0, 20]
    HtmlInline[0, 19] chars:[0, 19, "<a hr … bar\">"]
````````````````````````````````


Hard line breaks are for separating inline content within a block.
Neither syntax for hard line breaks works at the end of a paragraph or
other block element:

```````````````````````````````` example Hard line breaks: 12
foo\
.
<p>foo\</p>
.
Document[0, 5]
  Paragraph[0, 5]
    Text[0, 4] chars:[0, 4, "foo\"]
````````````````````````````````


```````````````````````````````` example Hard line breaks: 13
foo  
.
<p>foo</p>
.
Document[0, 6]
  Paragraph[0, 6]
    Text[0, 3] chars:[0, 3, "foo"]
````````````````````````````````


```````````````````````````````` example Hard line breaks: 14
### foo\
.
<h3>foo\</h3>
.
Document[0, 9]
  Heading[0, 8] textOpen:[0, 3, "###"] text:[4, 8, "foo\"]
    Text[4, 8] chars:[4, 8, "foo\"]
````````````````````````````````


```````````````````````````````` example Hard line breaks: 15
### foo  
.
<h3>foo</h3>
.
Document[0, 10]
  Heading[0, 7] textOpen:[0, 3, "###"] text:[4, 7, "foo"]
    Text[4, 7] chars:[4, 7, "foo"]
````````````````````````````````


## Soft line breaks

A regular line break (not in a code span or HTML tag) that is not
preceded by two or more spaces or a backslash is parsed as a
[softbreak](@).  (A softbreak may be rendered in HTML either as a
[line ending] or as a space. The result will be the same in
browsers. In the examples here, a [line ending] will be used.)

```````````````````````````````` example Soft line breaks: 1
foo
baz
.
<p>foo
baz</p>
.
Document[0, 8]
  Paragraph[0, 8]
    Text[0, 3] chars:[0, 3, "foo"]
    SoftLineBreak[3, 4]
    Text[4, 7] chars:[4, 7, "baz"]
````````````````````````````````


Spaces at the end of the line and beginning of the next line are
removed:

```````````````````````````````` example Soft line breaks: 2
foo 
 baz
.
<p>foo
baz</p>
.
Document[0, 10]
  Paragraph[0, 10]
    Text[0, 3] chars:[0, 3, "foo"]
    SoftLineBreak[4, 5]
    Text[6, 9] chars:[6, 9, "baz"]
````````````````````````````````


A conforming parser may render a soft line break in HTML either as a
line break or as a space.

A renderer may also provide an option to render soft line breaks
as hard line breaks.

## Textual content

Any characters not given an interpretation by the above rules will
be parsed as plain textual content.

```````````````````````````````` example Textual content: 1
hello $.;'there
.
<p>hello $.;'there</p>
.
Document[0, 16]
  Paragraph[0, 16]
    Text[0, 15] chars:[0, 15, "hello … there"]
````````````````````````````````


```````````````````````````````` example Textual content: 2
Foo χρῆν
.
<p>Foo χρῆν</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 8] chars:[0, 8, "Foo χρῆν"]
````````````````````````````````


Internal spaces are preserved verbatim:

```````````````````````````````` example Textual content: 3
Multiple     spaces
.
<p>Multiple     spaces</p>
.
Document[0, 20]
  Paragraph[0, 20]
    Text[0, 19] chars:[0, 19, "Multi … paces"]
````````````````````````````````


<!-- END TESTS -->

# Appendix: A parsing strategy

In this appendix we describe some features of the parsing strategy
used in the CommonMark reference implementations.

## Overview

Parsing has two phases:

1. In the first phase, lines of input are consumed and the block
structure of the document---its division into paragraphs, block quotes,
list items, and so on---is constructed.  Text is assigned to these
blocks but not parsed. Link reference definitions are parsed and a
map of links is constructed.

2. In the second phase, the raw text contents of paragraphs and headings
are parsed into sequences of Markdown inline elements (strings,
code spans, links, emphasis, and so on), using the map of link
references constructed in phase 1.

At each point in processing, the document is represented as a tree of
**blocks**.  The root of the tree is a `document` block.  The `document`
may have any number of other blocks as **children**.  These children
may, in turn, have other blocks as children.  The last child of a block
is normally considered **open**, meaning that subsequent lines of input
can alter its contents.  (Blocks that are not open are **closed**.)
Here, for example, is a possible document tree, with the open blocks
marked by arrows:

``` tree
-> document
  -> block_quote
       paragraph
         "Lorem ipsum dolor\nsit amet."
    -> list (type=bullet tight=true bullet_char=-)
         list_item
           paragraph
             "Qui *quodsi iracundia*"
      -> list_item
        -> paragraph
             "aliquando id"
```

## Phase 1: block structure

Each line that is processed has an effect on this tree.  The line is
analyzed and, depending on its contents, the document may be altered
in one or more of the following ways:

1. One or more open blocks may be closed.
2. One or more new blocks may be created as children of the
   last open block.
3. Text may be added to the last (deepest) open block remaining
   on the tree.

Once a line has been incorporated into the tree in this way,
it can be discarded, so input can be read in a stream.

For each line, we follow this procedure:

1. First we iterate through the open blocks, starting with the
root document, and descending through last children down to the last
open block.  Each block imposes a condition that the line must satisfy
if the block is to remain open.  For example, a block quote requires a
`>` character.  A paragraph requires a non-blank line.
In this phase we may match all or just some of the open
blocks.  But we cannot close unmatched blocks yet, because we may have a
[lazy continuation line].

2.  Next, after consuming the continuation markers for existing
blocks, we look for new block starts (e.g. `>` for a block quote).
If we encounter a new block start, we close any blocks unmatched
in step 1 before creating the new block as a child of the last
matched block.

3.  Finally, we look at the remainder of the line (after block
markers like `>`, list markers, and indentation have been consumed).
This is text that can be incorporated into the last open
block (a paragraph, code block, heading, or raw HTML).

Setext headings are formed when we see a line of a paragraph
that is a [setext heading underline].

Reference link definitions are detected when a paragraph is closed;
the accumulated text lines are parsed to see if they begin with
one or more reference link definitions.  Any remainder becomes a
normal paragraph.

We can see how this works by considering how the tree above is
generated by four lines of Markdown:

``` markdown
> Lorem ipsum dolor
sit amet.
> - Qui *quodsi iracundia*
> - aliquando id
```

At the outset, our document model is just

``` tree
-> document
```

The first line of our text,

``` markdown
> Lorem ipsum dolor
```

causes a `block_quote` block to be created as a child of our
open `document` block, and a `paragraph` block as a child of
the `block_quote`.  Then the text is added to the last open
block, the `paragraph`:

``` tree
-> document
  -> block_quote
    -> paragraph
         "Lorem ipsum dolor"
```

The next line,

``` markdown
sit amet.
```

is a "lazy continuation" of the open `paragraph`, so it gets added
to the paragraph's text:

``` tree
-> document
  -> block_quote
    -> paragraph
         "Lorem ipsum dolor\nsit amet."
```

The third line,

``` markdown
> - Qui *quodsi iracundia*
```

causes the `paragraph` block to be closed, and a new `list` block
opened as a child of the `block_quote`.  A `list_item` is also
added as a child of the `list`, and a `paragraph` as a child of
the `list_item`.  The text is then added to the new `paragraph`:

``` tree
-> document
  -> block_quote
       paragraph
         "Lorem ipsum dolor\nsit amet."
    -> list (type=bullet tight=true bullet_char=-)
      -> list_item
        -> paragraph
             "Qui *quodsi iracundia*"
```

The fourth line,

``` markdown
> - aliquando id
```

causes the `list_item` (and its child the `paragraph`) to be closed,
and a new `list_item` opened up as child of the `list`.  A `paragraph`
is added as a child of the new `list_item`, to contain the text.
We thus obtain the final tree:

``` tree
-> document
  -> block_quote
       paragraph
         "Lorem ipsum dolor\nsit amet."
    -> list (type=bullet tight=true bullet_char=-)
         list_item
           paragraph
             "Qui *quodsi iracundia*"
      -> list_item
        -> paragraph
             "aliquando id"
```

## Phase 2: inline structure

Once all of the input has been parsed, all open blocks are closed.

We then "walk the tree," visiting every node, and parse raw
string contents of paragraphs and headings as inlines.  At this
point we have seen all the link reference definitions, so we can
resolve reference links as we go.

``` tree
document
  block_quote
    paragraph
      str "Lorem ipsum dolor"
      softbreak
      str "sit amet."
    list (type=bullet tight=true bullet_char=-)
      list_item
        paragraph
          str "Qui "
          emph
            str "quodsi iracundia"
      list_item
        paragraph
          str "aliquando id"
```

Notice how the [line ending] in the first paragraph has
been parsed as a `softbreak`, and the asterisks in the first list item
have become an `emph`.

### An algorithm for parsing nested emphasis and links

By far the trickiest part of inline parsing is handling emphasis,
strong emphasis, links, and images.  This is done using the following
algorithm.

When we're parsing inlines and we hit either

- a run of `*` or `_` characters, or
- a `[` or `![`

we insert a text node with these symbols as its literal content, and we
add a pointer to this text node to the [delimiter stack](@).

The [delimiter stack] is a doubly linked list.  Each
element contains a pointer to a text node, plus information about

- the type of delimiter (`[`, `![`, `*`, `_`)
- the number of delimiters,
- whether the delimiter is "active" (all are active to start), and
- whether the delimiter is a potential opener, a potential closer,
  or both (which depends on what sort of characters precede
  and follow the delimiters).

When we hit a `]` character, we call the *look for link or image*
procedure (see below).

When we hit the end of the input, we call the *process emphasis*
procedure (see below), with `stack_bottom` = NULL.

#### *look for link or image*

Starting at the top of the delimiter stack, we look backwards
through the stack for an opening `[` or `![` delimiter.

- If we don't find one, we return a literal text node `]`.

- If we do find one, but it's not *active*, we remove the inactive
  delimiter from the stack, and return a literal text node `]`.

- If we find one and it's active, then we parse ahead to see if
  we have an inline link/image, reference link/image, compact reference
  link/image, or shortcut reference link/image.

  + If we don't, then we remove the opening delimiter from the
    delimiter stack and return a literal text node `]`.

  + If we do, then

    * We return a link or image node whose children are the inlines
      after the text node pointed to by the opening delimiter.

    * We run *process emphasis* on these inlines, with the `[` opener
      as `stack_bottom`.

    * We remove the opening delimiter.

    * If we have a link (and not an image), we also set all
      `[` delimiters before the opening delimiter to *inactive*.  (This
      will prevent us from getting links within links.)

#### *process emphasis*

Parameter `stack_bottom` sets a lower bound to how far we
descend in the [delimiter stack].  If it is NULL, we can
go all the way to the bottom.  Otherwise, we stop before
visiting `stack_bottom`.

Let `current_position` point to the element on the [delimiter stack]
just above `stack_bottom` (or the first element if `stack_bottom`
is NULL).

We keep track of the `openers_bottom` for each delimiter
type (`*`, `_`).  Initialize this to `stack_bottom`.

Then we repeat the following until we run out of potential
closers:

- Move `current_position` forward in the delimiter stack (if needed)
  until we find the first potential closer with delimiter `*` or `_`.
  (This will be the potential closer closest
  to the beginning of the input -- the first one in parse order.)

- Now, look back in the stack (staying above `stack_bottom` and
  the `openers_bottom` for this delimiter type) for the
  first matching potential opener ("matching" means same delimiter).

- If one is found:

  + Figure out whether we have emphasis or strong emphasis:
    if both closer and opener spans have length >= 2, we have
    strong, otherwise regular.

  + Insert an emph or strong emph node accordingly, after
    the text node corresponding to the opener.

  + Remove any delimiters between the opener and closer from
    the delimiter stack.

  + Remove 1 (for regular emph) or 2 (for strong emph) delimiters
    from the opening and closing text nodes.  If they become empty
    as a result, remove them and remove the corresponding element
    of the delimiter stack.  If the closing node is removed, reset
    `current_position` to the next element in the stack.

- If none in found:

  + Set `openers_bottom` to the element before `current_position`.
    (We know that there are no openers for this kind of closer up to and
    including this point, so this puts a lower bound on future searches.)

  + If the closer at `current_position` is not a potential opener,
    remove it from the delimiter stack (since we know it can't
    be a closer either).

  + Advance `current_position` to the next element in the stack.

After we're done, we remove all delimiters above `stack_bottom` from the
delimiter stack.

