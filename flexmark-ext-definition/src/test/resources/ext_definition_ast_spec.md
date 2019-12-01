---
title: Definition List Extension Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Definition List Extension

flexmark-java extension for definition list processing

Converts definition syntax of
[Php Markdown Extra Definition List](https://michelf.ca/projects/php-markdown/extra/#def-list)
into `<dl></dl>` html

Definition items can be preceded by `:` or `~`

Definition list is one or more definition terms with one or more definitions.

```markdown
Definition Term
: Definition of above term
: Another definition of above term
```

Definitions terms can have no definitions if they are not the last term before a definition:

```markdown
Definition Term without definition  
Definition Term  
: Definition of above term  
: Another definition of above term
```

A blank line between the definition term and definition, or the definition term and previous
definition term will create a "loose" definition by wrapping its text in `<p></p>`.

```markdown
Definition Term

: Loose Definition of above term  
: Tight definition of above term

: Another loose definition term
```

Will create:

```html
<dl>
  <dt>Definition Term</dt>
  <dd><p>Loose Definition of above term</p></dd>
  <dd>Tight definition of above term</dd>
  <dd><p>Another loose definition term</p></dd>
</dl>
```

Lazy continuation of definitions is supported and definition terms which follow a definition
must be separated from it by a blank line.

```markdown
Definition Term  
: Definition for Definition Term

Another Definition Term  
: Definition for Another Definition Term
```

Inline markdown is allowed in both terms and definitions. Definitions can contain any other
markdown elements provided these are indented as per list indentation rules.

Multiple definition terms cannot be separated by blank lines since all but the last term will be
treated as paragraph text:

```markdown
Not a definition term.

Neither is this.

Definition Term
Another Definition Term

: Definition
```

Inline markdown is allowed in both terms and definitions. Definitions can contain any other
markdown elements provided these are indented as per list indentation rules.

Paragraph block lines are split into definition terms before inline processing it is possible
that an inline markup starts on one line and finishes on another. If this paragraph is converted
to definition terms then the markup will be ignored and its opening marker attributed to one
definition term and its closing marker to the following one.

This is an example of split markdown inlines across definition terms:

```markdown
Definition **Term
Another** Definition Term
: definition `item`
```

Will result in the following HTML:

```html
<dl>
   <dt>Definition **Term</dt>
   <dt>Another** Definition Term</dt>
   <dd>definition <code>item</code></dd>
</dl>
```

---

not a definition

```````````````````````````````` example Definition List Extension: 1
: definition item 
.
<p>: definition item</p>
.
Document[0, 18]
  Paragraph[0, 18]
    Text[0, 17] chars:[0, 17, ": def …  item"]
````````````````````````````````


not a definition

```````````````````````````````` example Definition List Extension: 2
- bullet item
: definition item 
.
<ul>
  <li>bullet item
    : definition item</li>
</ul>
.
Document[0, 32]
  BulletList[0, 32] isTight
    BulletListItem[0, 32] open:[0, 1, "-"] isTight
      Paragraph[2, 32]
        Text[2, 13] chars:[2, 13, "bulle …  item"]
        SoftLineBreak[13, 14]
        Text[14, 31] chars:[14, 31, ": def …  item"]
````````````````````````````````


not a definition

```````````````````````````````` example Definition List Extension: 3
1. numbered item
: definition item 
.
<ol>
  <li>numbered item
    : definition item</li>
</ol>
.
Document[0, 35]
  OrderedList[0, 35] isTight delimiter:'.'
    OrderedListItem[0, 35] open:[0, 2, "1."] isTight
      Paragraph[3, 35]
        Text[3, 16] chars:[3, 16, "numbe …  item"]
        SoftLineBreak[16, 17]
        Text[17, 34] chars:[17, 34, ": def …  item"]
````````````````````````````````


not a definition

```````````````````````````````` example Definition List Extension: 4
## Header
: definition item 
.
<h2>Header</h2>
<p>: definition item</p>
.
Document[0, 28]
  Heading[0, 9] textOpen:[0, 2, "##"] text:[3, 9, "Header"]
    Text[3, 9] chars:[3, 9, "Header"]
  Paragraph[10, 28]
    Text[10, 27] chars:[10, 27, ": def …  item"]
````````````````````````````````


not a definition

```````````````````````````````` example Definition List Extension: 5
```markdown
some stuff
```

: definition item 
.
<pre><code class="language-markdown">some stuff
</code></pre>
<p>: definition item</p>
.
Document[0, 46]
  FencedCodeBlock[0, 26] open:[0, 3, "```"] info:[3, 11, "markdown"] content:[12, 23] lines[1] close:[23, 26, "```"]
    Text[12, 23] chars:[12, 23, "some  … tuff\n"]
  Paragraph[28, 46]
    Text[28, 45] chars:[28, 45, ": def …  item"]
````````````````````````````````


not a definition in a lazy block quote

```````````````````````````````` example Definition List Extension: 6
> definition
: definition item 
.
<blockquote>
  <p>definition
  : definition item</p>
</blockquote>
.
Document[0, 31]
  BlockQuote[0, 31] marker:[0, 1, ">"]
    Paragraph[2, 31]
      Text[2, 12] chars:[2, 12, "definition"]
      SoftLineBreak[12, 13]
      Text[13, 30] chars:[13, 30, ": def …  item"]
````````````````````````````````


a definition in a block quote

```````````````````````````````` example Definition List Extension: 7
> Definition Term
> : definition item 
.
<blockquote>
  <dl>
    <dt>Definition Term</dt>
    <dd>definition item</dd>
  </dl>
</blockquote>
.
Document[0, 38]
  BlockQuote[0, 38] marker:[0, 1, ">"]
    DefinitionList[2, 38] isTight
      DefinitionTerm[2, 18]
        Paragraph[2, 18]
          Text[2, 17] chars:[2, 17, "Defin …  Term"]
      DefinitionItem[20, 38] open:[20, 21, ":"] isTight
        Paragraph[22, 38]
          Text[22, 37] chars:[22, 37, "defin …  item"]
````````````````````````````````


simple list

```````````````````````````````` example Definition List Extension: 8
Definition Term
: definition item 
.
<dl>
  <dt>Definition Term</dt>
  <dd>definition item</dd>
</dl>
.
Document[0, 34]
  DefinitionList[0, 34] isTight
    DefinitionTerm[0, 16]
      Paragraph[0, 16]
        Text[0, 15] chars:[0, 15, "Defin …  Term"]
    DefinitionItem[16, 34] open:[16, 17, ":"] isTight
      Paragraph[18, 34]
        Text[18, 33] chars:[18, 33, "defin …  item"]
````````````````````````````````


simple list with a tab

```````````````````````````````` example Definition List Extension: 9
Definition Term
:→definition item 
.
<dl>
  <dt>Definition Term</dt>
  <dd>definition item</dd>
</dl>
.
Document[0, 34]
  DefinitionList[0, 34] isTight
    DefinitionTerm[0, 16]
      Paragraph[0, 16]
        Text[0, 15] chars:[0, 15, "Defin …  Term"]
    DefinitionItem[16, 34] open:[16, 17, ":"] isTight
      Paragraph[18, 34]
        Text[18, 33] chars:[18, 33, "defin …  item"]
````````````````````````````````


A simple definition list:

```````````````````````````````` example Definition List Extension: 10
Term 1
:   Definition 1

Term 2
:   Definition 2

.
<dl>
  <dt>Term 1</dt>
  <dd>Definition 1</dd>
  <dt>Term 2</dt>
  <dd>Definition 2</dd>
</dl>
.
Document[0, 50]
  DefinitionList[0, 49] isTight
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionItem[7, 24] open:[7, 8, ":"] isTight hadBlankLineAfter
      Paragraph[11, 24] isTrailingBlankLine
        Text[11, 23] chars:[11, 23, "Defin … ion 1"]
    DefinitionTerm[25, 32]
      Paragraph[25, 32]
        Text[25, 31] chars:[25, 31, "Term 2"]
    DefinitionItem[32, 49] open:[32, 33, ":"] isTight hadBlankLineAfter
      Paragraph[36, 49] isTrailingBlankLine
        Text[36, 48] chars:[36, 48, "Defin … ion 2"]
````````````````````````````````


With multiple terms:

```````````````````````````````` example Definition List Extension: 11
Term 1
Term 2
:   Definition 1

Term 3
Term 4
:   Definition 2

.
<dl>
  <dt>Term 1</dt>
  <dt>Term 2</dt>
  <dd>Definition 1</dd>
  <dt>Term 3</dt>
  <dt>Term 4</dt>
  <dd>Definition 2</dd>
</dl>
.
Document[0, 64]
  DefinitionList[0, 63] isTight
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionTerm[7, 14]
      Paragraph[7, 14]
        Text[7, 13] chars:[7, 13, "Term 2"]
    DefinitionItem[14, 31] open:[14, 15, ":"] isTight hadBlankLineAfter
      Paragraph[18, 31] isTrailingBlankLine
        Text[18, 30] chars:[18, 30, "Defin … ion 1"]
    DefinitionTerm[32, 39]
      Paragraph[32, 39]
        Text[32, 38] chars:[32, 38, "Term 3"]
    DefinitionTerm[39, 46]
      Paragraph[39, 46]
        Text[39, 45] chars:[39, 45, "Term 4"]
    DefinitionItem[46, 63] open:[46, 47, ":"] isTight hadBlankLineAfter
      Paragraph[50, 63] isTrailingBlankLine
        Text[50, 62] chars:[50, 62, "Defin … ion 2"]
````````````````````````````````


With multiple terms, broken into two lists:

```````````````````````````````` example(Definition List Extension: 12) options(break-list)
Term 1
Term 2
:   Definition 1


Term 3
Term 4
:   Definition 2

.
<dl>
  <dt>Term 1</dt>
  <dt>Term 2</dt>
  <dd>Definition 1</dd>
</dl><dl>
  <dt>Term 3</dt>
  <dt>Term 4</dt>
  <dd>Definition 2</dd>
</dl>
.
Document[0, 65]
  DefinitionList[0, 31] isTight
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionTerm[7, 14]
      Paragraph[7, 14]
        Text[7, 13] chars:[7, 13, "Term 2"]
    DefinitionItem[14, 31] open:[14, 15, ":"] isTight hadBlankLineAfter
      Paragraph[18, 31] isTrailingBlankLine
        Text[18, 30] chars:[18, 30, "Defin … ion 1"]
  DefinitionList[33, 64] isTight
    DefinitionTerm[33, 40]
      Paragraph[33, 40]
        Text[33, 39] chars:[33, 39, "Term 3"]
    DefinitionTerm[40, 47]
      Paragraph[40, 47]
        Text[40, 46] chars:[40, 46, "Term 4"]
    DefinitionItem[47, 64] open:[47, 48, ":"] isTight hadBlankLineAfter
      Paragraph[51, 64] isTrailingBlankLine
        Text[51, 63] chars:[51, 63, "Defin … ion 2"]
````````````````````````````````


With multiple terms, broken into two lists:

```````````````````````````````` example(Definition List Extension: 13) options(break-list)
Term 1
Term 2
:   Definition 1


:   Definition 2

.
<dl>
  <dt>Term 1</dt>
  <dt>Term 2</dt>
  <dd>Definition 1</dd>
</dl>
<p>:   Definition 2</p>
.
Document[0, 51]
  DefinitionList[0, 31] isTight
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionTerm[7, 14]
      Paragraph[7, 14]
        Text[7, 13] chars:[7, 13, "Term 2"]
    DefinitionItem[14, 31] open:[14, 15, ":"] isTight hadBlankLineAfter
      Paragraph[18, 31] isTrailingBlankLine
        Text[18, 30] chars:[18, 30, "Defin … ion 1"]
  Paragraph[33, 50] isTrailingBlankLine
    Text[33, 49] chars:[33, 49, ":   D … ion 2"]
````````````````````````````````


With multiple terms, not broken into two lists:

```````````````````````````````` example Definition List Extension: 14
Term 1
Term 2
:   Definition 1


Term 3
Term 4
:   Definition 2

.
<dl>
  <dt>Term 1</dt>
  <dt>Term 2</dt>
  <dd>Definition 1</dd>
  <dt>Term 3</dt>
  <dt>Term 4</dt>
  <dd>Definition 2</dd>
</dl>
.
Document[0, 65]
  DefinitionList[0, 64] isTight
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionTerm[7, 14]
      Paragraph[7, 14]
        Text[7, 13] chars:[7, 13, "Term 2"]
    DefinitionItem[14, 31] open:[14, 15, ":"] isTight hadBlankLineAfter
      Paragraph[18, 31] isTrailingBlankLine
        Text[18, 30] chars:[18, 30, "Defin … ion 1"]
    DefinitionTerm[33, 40]
      Paragraph[33, 40]
        Text[33, 39] chars:[33, 39, "Term 3"]
    DefinitionTerm[40, 47]
      Paragraph[40, 47]
        Text[40, 46] chars:[40, 46, "Term 4"]
    DefinitionItem[47, 64] open:[47, 48, ":"] isTight hadBlankLineAfter
      Paragraph[51, 64] isTrailingBlankLine
        Text[51, 63] chars:[51, 63, "Defin … ion 2"]
````````````````````````````````


With multiple terms, not broken into two lists:

```````````````````````````````` example Definition List Extension: 15
Term 1
Term 2
:   Definition 1


:   Definition 2

.
<dl>
  <dt>Term 1</dt>
  <dt>Term 2</dt>
  <dd>
    <p>Definition 1</p>
  </dd>
  <dd>
    <p>Definition 2</p>
  </dd>
</dl>
.
Document[0, 51]
  DefinitionList[0, 50] isLoose
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionTerm[7, 14]
      Paragraph[7, 14]
        Text[7, 13] chars:[7, 13, "Term 2"]
    DefinitionItem[14, 31] open:[14, 15, ":"] isLoose hadBlankLineAfter
      Paragraph[18, 31] isTrailingBlankLine
        Text[18, 30] chars:[18, 30, "Defin … ion 1"]
    DefinitionItem[33, 50] open:[33, 34, ":"] isLoose hadBlankLineAfter
      Paragraph[37, 50] isTrailingBlankLine
        Text[37, 49] chars:[37, 49, "Defin … ion 2"]
````````````````````````````````


With multiple terms, broken into two lists:

```````````````````````````````` example(Definition List Extension: 16) options(break-list)
Term 1
:   Definition 1 (paragraph)


:   Text out of definition (paragraph)
.
<dl>
  <dt>Term 1</dt>
  <dd>Definition 1 (paragraph)</dd>
</dl>
<p>:   Text out of definition (paragraph)</p>
.
Document[0, 76]
  DefinitionList[0, 36] isTight
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionItem[7, 36] open:[7, 8, ":"] isTight hadBlankLineAfter
      Paragraph[11, 36] isTrailingBlankLine
        Text[11, 35] chars:[11, 35, "Defin … raph)"]
  Paragraph[38, 76]
    Text[38, 76] chars:[38, 76, ":   T … raph)"]
````````````````````````````````


With multiple terms, not broken into two lists:

```````````````````````````````` example Definition List Extension: 17
Term 1
:   Definition 1 (paragraph)


:   Text out of definition (paragraph)
.
<dl>
  <dt>Term 1</dt>
  <dd>
    <p>Definition 1 (paragraph)</p>
  </dd>
  <dd>
    <p>Text out of definition (paragraph)</p>
  </dd>
</dl>
.
Document[0, 76]
  DefinitionList[0, 76] isLoose
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionItem[7, 36] open:[7, 8, ":"] isLoose hadBlankLineAfter
      Paragraph[11, 36] isTrailingBlankLine
        Text[11, 35] chars:[11, 35, "Defin … raph)"]
    DefinitionItem[38, 76] open:[38, 39, ":"] isLoose
      Paragraph[42, 76]
        Text[42, 76] chars:[42, 76, "Text  … raph)"]
````````````````````````````````


With multiple definitions:

```````````````````````````````` example Definition List Extension: 18
Term 1
:   Definition 1
:   Definition 2

Term 2
:   Definition 3
:   Definition 4

.
<dl>
  <dt>Term 1</dt>
  <dd>Definition 1</dd>
  <dd>Definition 2</dd>
  <dt>Term 2</dt>
  <dd>Definition 3</dd>
  <dd>Definition 4</dd>
</dl>
.
Document[0, 84]
  DefinitionList[0, 83] isTight
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionItem[7, 24] open:[7, 8, ":"] isTight
      Paragraph[11, 24]
        Text[11, 23] chars:[11, 23, "Defin … ion 1"]
    DefinitionItem[24, 41] open:[24, 25, ":"] isTight hadBlankLineAfter
      Paragraph[28, 41] isTrailingBlankLine
        Text[28, 40] chars:[28, 40, "Defin … ion 2"]
    DefinitionTerm[42, 49]
      Paragraph[42, 49]
        Text[42, 48] chars:[42, 48, "Term 2"]
    DefinitionItem[49, 66] open:[49, 50, ":"] isTight
      Paragraph[53, 66]
        Text[53, 65] chars:[53, 65, "Defin … ion 3"]
    DefinitionItem[66, 83] open:[66, 67, ":"] isTight hadBlankLineAfter
      Paragraph[70, 83] isTrailingBlankLine
        Text[70, 82] chars:[70, 82, "Defin … ion 4"]
````````````````````````````````


With multiple lines per definition:

```````````````````````````````` example Definition List Extension: 19
Term 1
:   Definition 1 line 1 ...
Definition 1 line 2
:   Definition 2 line 1 ...
Definition 2 line 2

Term 2
:   Definition 3 line 2 ...
    Definition 3 line 2
:   Definition 4 line 2 ...
    Definition 4 line 2

.
<dl>
  <dt>Term 1</dt>
  <dd>Definition 1 line 1 ...
    Definition 1 line 2</dd>
  <dd>Definition 2 line 1 ...
    Definition 2 line 2</dd>
  <dt>Term 2</dt>
  <dd>Definition 3 line 2 ...
    Definition 3 line 2</dd>
  <dd>Definition 4 line 2 ...
    Definition 4 line 2</dd>
</dl>
.
Document[0, 216]
  DefinitionList[0, 215] isTight
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionItem[7, 55] open:[7, 8, ":"] isTight
      Paragraph[11, 55]
        Text[11, 34] chars:[11, 34, "Defin … 1 ..."]
        SoftLineBreak[34, 35]
        Text[35, 54] chars:[35, 54, "Defin … ine 2"]
    DefinitionItem[55, 103] open:[55, 56, ":"] isTight hadBlankLineAfter
      Paragraph[59, 103] isTrailingBlankLine
        Text[59, 82] chars:[59, 82, "Defin … 1 ..."]
        SoftLineBreak[82, 83]
        Text[83, 102] chars:[83, 102, "Defin … ine 2"]
    DefinitionTerm[104, 111]
      Paragraph[104, 111]
        Text[104, 110] chars:[104, 110, "Term 2"]
    DefinitionItem[111, 163] open:[111, 112, ":"] isTight
      Paragraph[115, 163]
        Text[115, 138] chars:[115, 138, "Defin … 2 ..."]
        SoftLineBreak[138, 139]
        Text[143, 162] chars:[143, 162, "Defin … ine 2"]
    DefinitionItem[163, 215] open:[163, 164, ":"] isTight hadBlankLineAfter
      Paragraph[167, 215] isTrailingBlankLine
        Text[167, 190] chars:[167, 190, "Defin … 2 ..."]
        SoftLineBreak[190, 191]
        Text[195, 214] chars:[195, 214, "Defin … ine 2"]
````````````````````````````````


With paragraphs:

```````````````````````````````` example Definition List Extension: 20
Term 1

:   Definition 1 (paragraph)

Term 2

:   Definition 2 (paragraph)

.
<dl>
  <dt>Term 1</dt>
  <dd>
    <p>Definition 1 (paragraph)</p>
  </dd>
  <dt>Term 2</dt>
  <dd>
    <p>Definition 2 (paragraph)</p>
  </dd>
</dl>
.
Document[0, 76]
  DefinitionList[0, 75] isLoose
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionItem[8, 37] open:[8, 9, ":"] isLoose hadBlankLineAfter
      Paragraph[12, 37] isTrailingBlankLine
        Text[12, 36] chars:[12, 36, "Defin … raph)"]
    DefinitionTerm[38, 45]
      Paragraph[38, 45]
        Text[38, 44] chars:[38, 44, "Term 2"]
    DefinitionItem[46, 75] open:[46, 47, ":"] isLoose hadBlankLineAfter
      Paragraph[50, 75] isTrailingBlankLine
        Text[50, 74] chars:[50, 74, "Defin … raph)"]
````````````````````````````````


With multiple paragraphs:

```````````````````````````````` example Definition List Extension: 21
Term 1

:   Definition 1 paragraph 1 line 1 ...
→Definition 1 paragraph 1 line 2

    Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2

Term 2

:   Definition 1 paragraph 1 line 1 ...
Definition 1 paragraph 1 line 2 (lazy)

    Definition 1 paragraph 2 line 1 ...
Definition 1 paragraph 2 line 2 (lazy)

.
<dl>
  <dt>Term 1</dt>
  <dd>
    <p>Definition 1 paragraph 1 line 1 ...
    Definition 1 paragraph 1 line 2</p>
    <p>Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2</p>
  </dd>
  <dt>Term 2</dt>
  <dd>
    <p>Definition 1 paragraph 1 line 1 ...
    Definition 1 paragraph 1 line 2 (lazy)</p>
    <p>Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2 (lazy)</p>
  </dd>
</dl>
.
Document[0, 327]
  DefinitionList[0, 326] isLoose
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionItem[8, 158] open:[8, 9, ":"] isLoose hadBlankLineAfter
      Paragraph[12, 81] isTrailingBlankLine
        Text[12, 47] chars:[12, 47, "Defin … 1 ..."]
        SoftLineBreak[47, 48]
        Text[49, 80] chars:[49, 80, "Defin … ine 2"]
      Paragraph[86, 158] isTrailingBlankLine
        Text[86, 121] chars:[86, 121, "Defin … 1 ..."]
        SoftLineBreak[121, 122]
        Text[126, 157] chars:[126, 157, "Defin … ine 2"]
    DefinitionTerm[159, 166]
      Paragraph[159, 166]
        Text[159, 165] chars:[159, 165, "Term 2"]
    DefinitionItem[167, 326] open:[167, 168, ":"] isLoose hadBlankLineAfter
      Paragraph[171, 246] isTrailingBlankLine
        Text[171, 206] chars:[171, 206, "Defin … 1 ..."]
        SoftLineBreak[206, 207]
        Text[207, 245] chars:[207, 245, "Defin … lazy)"]
      Paragraph[251, 326] isTrailingBlankLine
        Text[251, 286] chars:[251, 286, "Defin … 1 ..."]
        SoftLineBreak[286, 287]
        Text[287, 325] chars:[287, 325, "Defin … lazy)"]
````````````````````````````````


A mix:

```````````````````````````````` example Definition List Extension: 22
Term 1
Term 2

:   Definition 1 paragraph 1 line 1 ...
Definition 1 paragraph 1 line 2 (lazy)
    
    Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2

:   Definition 2 paragraph 1 line 1 ...
Definition 2 paragraph 1 line 2 (lazy)

Term 3
:   Definition 3 (no paragraph)
:   Definition 4 (no paragraph)
:   Definition 5 line 1 ...
    Definition 5 line 2 (no paragraph)

:   Definition 6 paragraph 1 line 1 ...
Definition 6 paragraph 1 line 2
:   Definition 7 (no paragraph)
:   Definition 8 paragraph 1 line 1 (forced paragraph) ...
    Definition 8 paragraph 1 line 2
    
    Definition 8 paragraph 2 line 1
    
Term 4
:   Definition 9 paragraph 1 line 1 (forced paragraph) ...
    Definition 9 paragraph 1 line 2
    
    Definition 9 paragraph 2 line 1
:   Definition 10 (no paragraph)
.
<dl>
  <dt>Term 1</dt>
  <dt>Term 2</dt>
  <dd>
    <p>Definition 1 paragraph 1 line 1 ...
    Definition 1 paragraph 1 line 2 (lazy)</p>
    <p>Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2</p>
  </dd>
  <dd>
    <p>Definition 2 paragraph 1 line 1 ...
    Definition 2 paragraph 1 line 2 (lazy)</p>
  </dd>
  <dt>Term 3</dt>
  <dd>
    <p>Definition 3 (no paragraph)</p>
  </dd>
  <dd>
    <p>Definition 4 (no paragraph)</p>
  </dd>
  <dd>
    <p>Definition 5 line 1 ...
    Definition 5 line 2 (no paragraph)</p>
  </dd>
  <dd>
    <p>Definition 6 paragraph 1 line 1 ...
    Definition 6 paragraph 1 line 2</p>
  </dd>
  <dd>
    <p>Definition 7 (no paragraph)</p>
  </dd>
  <dd>
    <p>Definition 8 paragraph 1 line 1 (forced paragraph) ...
    Definition 8 paragraph 1 line 2</p>
    <p>Definition 8 paragraph 2 line 1</p>
  </dd>
  <dt>Term 4</dt>
  <dd>
    <p>Definition 9 paragraph 1 line 1 (forced paragraph) ...
    Definition 9 paragraph 1 line 2</p>
    <p>Definition 9 paragraph 2 line 1</p>
  </dd>
  <dd>
    <p>Definition 10 (no paragraph)</p>
  </dd>
</dl>
.
Document[0, 815]
  DefinitionList[0, 815] isLoose
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionTerm[7, 14]
      Paragraph[7, 14]
        Text[7, 13] chars:[7, 13, "Term 2"]
    DefinitionItem[15, 175] open:[15, 16, ":"] isLoose hadBlankLineAfter
      Paragraph[19, 94] isTrailingBlankLine
        Text[19, 54] chars:[19, 54, "Defin … 1 ..."]
        SoftLineBreak[54, 55]
        Text[55, 93] chars:[55, 93, "Defin … lazy)"]
      Paragraph[103, 175] isTrailingBlankLine
        Text[103, 138] chars:[103, 138, "Defin … 1 ..."]
        SoftLineBreak[138, 139]
        Text[143, 174] chars:[143, 174, "Defin … ine 2"]
    DefinitionItem[176, 255] open:[176, 177, ":"] isLoose hadBlankLineAfter
      Paragraph[180, 255] isTrailingBlankLine
        Text[180, 215] chars:[180, 215, "Defin … 1 ..."]
        SoftLineBreak[215, 216]
        Text[216, 254] chars:[216, 254, "Defin … lazy)"]
    DefinitionTerm[256, 263]
      Paragraph[256, 263]
        Text[256, 262] chars:[256, 262, "Term 3"]
    DefinitionItem[263, 295] open:[263, 264, ":"] isLoose
      Paragraph[267, 295]
        Text[267, 294] chars:[267, 294, "Defin … raph)"]
    DefinitionItem[295, 327] open:[295, 296, ":"] isLoose
      Paragraph[299, 327]
        Text[299, 326] chars:[299, 326, "Defin … raph)"]
    DefinitionItem[327, 394] open:[327, 328, ":"] isLoose hadBlankLineAfter
      Paragraph[331, 394] isTrailingBlankLine
        Text[331, 354] chars:[331, 354, "Defin … 1 ..."]
        SoftLineBreak[354, 355]
        Text[359, 393] chars:[359, 393, "Defin … raph)"]
    DefinitionItem[395, 467] open:[395, 396, ":"] isLoose
      Paragraph[399, 467]
        Text[399, 434] chars:[399, 434, "Defin … 1 ..."]
        SoftLineBreak[434, 435]
        Text[435, 466] chars:[435, 466, "Defin … ine 2"]
    DefinitionItem[467, 499] open:[467, 468, ":"] isLoose
      Paragraph[471, 499]
        Text[471, 498] chars:[471, 498, "Defin … raph)"]
    DefinitionItem[499, 635] open:[499, 500, ":"] isLoose hadBlankLineAfter
      Paragraph[503, 594] isTrailingBlankLine
        Text[503, 557] chars:[503, 557, "Defin … ) ..."]
        SoftLineBreak[557, 558]
        Text[562, 593] chars:[562, 593, "Defin … ine 2"]
      Paragraph[603, 635] isTrailingBlankLine
        Text[603, 634] chars:[603, 634, "Defin … ine 1"]
    DefinitionTerm[640, 647]
      Paragraph[640, 647]
        Text[640, 646] chars:[640, 646, "Term 4"]
    DefinitionItem[647, 783] open:[647, 648, ":"] isLoose hadBlankLineAfter
      Paragraph[651, 742] isTrailingBlankLine
        Text[651, 705] chars:[651, 705, "Defin … ) ..."]
        SoftLineBreak[705, 706]
        Text[710, 741] chars:[710, 741, "Defin … ine 2"]
      Paragraph[751, 783]
        Text[751, 782] chars:[751, 782, "Defin … ine 1"]
    DefinitionItem[783, 815] open:[783, 784, ":"] isLoose
      Paragraph[787, 815]
        Text[787, 815] chars:[787, 815, "Defin … raph)"]
````````````````````````````````


inlines allowed

```````````````````````````````` example Definition List Extension: 23
Definition **Term**
: definition `item` 
.
<dl>
  <dt>Definition <strong>Term</strong></dt>
  <dd>definition <code>item</code></dd>
</dl>
.
Document[0, 40]
  DefinitionList[0, 40] isTight
    DefinitionTerm[0, 20]
      Paragraph[0, 20]
        Text[0, 11] chars:[0, 11, "Defin … tion "]
        StrongEmphasis[11, 19] textOpen:[11, 13, "**"] text:[13, 17, "Term"] textClose:[17, 19, "**"]
          Text[13, 17] chars:[13, 17, "Term"]
    DefinitionItem[20, 40] open:[20, 21, ":"] isTight
      Paragraph[22, 40]
        Text[22, 33] chars:[22, 33, "defin … tion "]
        Code[33, 39] textOpen:[33, 34, "`"] text:[34, 38, "item"] textClose:[38, 39, "`"]
          Text[34, 38] chars:[34, 38, "item"]
````````````````````````````````


inlines will be split

```````````````````````````````` example Definition List Extension: 24
Definition **Term 
Another** Definition Term 
: definition `item`
.
<dl>
  <dt>Definition **Term</dt>
  <dt>Another** Definition Term</dt>
  <dd>definition <code>item</code></dd>
</dl>
.
Document[0, 65]
  DefinitionList[0, 65] isTight
    DefinitionTerm[0, 19]
      Paragraph[0, 19]
        Text[0, 17] chars:[0, 17, "Defin … *Term"]
    DefinitionTerm[19, 46]
      Paragraph[19, 46]
        Text[19, 44] chars:[19, 44, "Anoth …  Term"]
    DefinitionItem[46, 65] open:[46, 47, ":"] isTight
      Paragraph[48, 65]
        Text[48, 59] chars:[48, 59, "defin … tion "]
        Code[59, 65] textOpen:[59, 60, "`"] text:[60, 64, "item"] textClose:[64, 65, "`"]
          Text[60, 64] chars:[60, 64, "item"]
````````````````````````````````


don't include preceding blank lines

```````````````````````````````` example Definition List Extension: 25
- bullet item


Definition Term 
: definition item
.
<ul>
  <li>bullet item</li>
</ul>
<dl>
  <dt>Definition Term</dt>
  <dd>definition item</dd>
</dl>
.
Document[0, 50]
  BulletList[0, 14] isTight
    BulletListItem[0, 14] open:[0, 1, "-"] isTight hadBlankLineAfter
      Paragraph[2, 14] isTrailingBlankLine
        Text[2, 13] chars:[2, 13, "bulle …  item"]
  DefinitionList[16, 50] isTight
    DefinitionTerm[16, 33]
      Paragraph[16, 33]
        Text[16, 31] chars:[16, 31, "Defin …  Term"]
    DefinitionItem[33, 50] open:[33, 34, ":"] isTight
      Paragraph[35, 50]
        Text[35, 50] chars:[35, 50, "defin …  item"]
````````````````````````````````


nested elements allowed

```````````````````````````````` example Definition List Extension: 26
Definition **Term**
: definition `item` 
    
  paragraph
    
  - bullet item
    - sub item
      
  > block quote    
  
.
<dl>
  <dt>Definition <strong>Term</strong></dt>
  <dd>definition <code>item</code>
    <p>paragraph</p>
    <ul>
      <li>bullet item
        <ul>
          <li>sub item</li>
        </ul>
      </li>
    </ul>
    <blockquote>
      <p>block quote</p>
    </blockquote>
  </dd>
</dl>
.
Document[0, 124]
  DefinitionList[0, 121] isTight
    DefinitionTerm[0, 20]
      Paragraph[0, 20]
        Text[0, 11] chars:[0, 11, "Defin … tion "]
        StrongEmphasis[11, 19] textOpen:[11, 13, "**"] text:[13, 17, "Term"] textClose:[17, 19, "**"]
          Text[13, 17] chars:[13, 17, "Term"]
    DefinitionItem[20, 121] open:[20, 21, ":"] isTight hadBlankLineAfter
      Paragraph[22, 41] isTrailingBlankLine
        Text[22, 33] chars:[22, 33, "defin … tion "]
        Code[33, 39] textOpen:[33, 34, "`"] text:[34, 38, "item"] textClose:[38, 39, "`"]
          Text[34, 38] chars:[34, 38, "item"]
      Paragraph[48, 58] isTrailingBlankLine
        Text[48, 57] chars:[48, 57, "paragraph"]
      BulletList[65, 94] isTight
        BulletListItem[65, 94] open:[65, 66, "-"] isTight
          Paragraph[67, 79]
            Text[67, 78] chars:[67, 78, "bulle …  item"]
          BulletList[83, 94] isTight
            BulletListItem[83, 94] open:[83, 84, "-"] isTight hadBlankLineAfter
              Paragraph[85, 94] isTrailingBlankLine
                Text[85, 93] chars:[85, 93, "sub item"]
      BlockQuote[103, 121] marker:[103, 104, ">"]
        Paragraph[105, 121]
          Text[105, 116] chars:[105, 116, "block … quote"]
````````````````````````````````


With disparate looseness with auto-loose

```````````````````````````````` example Definition List Extension: 27
Term 1

:   Definition 1 (paragraph)

Term 2
:   Definition 2 (paragraph)

.
<dl>
  <dt>Term 1</dt>
  <dd>
    <p>Definition 1 (paragraph)</p>
  </dd>
  <dt>Term 2</dt>
  <dd>
    <p>Definition 2 (paragraph)</p>
  </dd>
</dl>
.
Document[0, 75]
  DefinitionList[0, 74] isLoose
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionItem[8, 37] open:[8, 9, ":"] isLoose hadBlankLineAfter
      Paragraph[12, 37] isTrailingBlankLine
        Text[12, 36] chars:[12, 36, "Defin … raph)"]
    DefinitionTerm[38, 45]
      Paragraph[38, 45]
        Text[38, 44] chars:[38, 44, "Term 2"]
    DefinitionItem[45, 74] open:[45, 46, ":"] isLoose hadBlankLineAfter
      Paragraph[49, 74] isTrailingBlankLine
        Text[49, 73] chars:[49, 73, "Defin … raph)"]
````````````````````````````````


With disparate looseness without auto-loose

```````````````````````````````` example(Definition List Extension: 28) options(no-auto-loose)
Term 1

:   Definition 1 (paragraph)

Term 2
:   Definition 2 (paragraph)

.
<dl>
  <dt>Term 1</dt>
  <dd>
    <p>Definition 1 (paragraph)</p>
  </dd>
  <dt>Term 2</dt>
  <dd>Definition 2 (paragraph)</dd>
</dl>
.
Document[0, 75]
  DefinitionList[0, 74] isTight
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionItem[8, 37] open:[8, 9, ":"] isLoose hadBlankLineAfter
      Paragraph[12, 37] isTrailingBlankLine
        Text[12, 36] chars:[12, 36, "Defin … raph)"]
    DefinitionTerm[38, 45]
      Paragraph[38, 45]
        Text[38, 44] chars:[38, 44, "Term 2"]
    DefinitionItem[45, 74] open:[45, 46, ":"] isTight hadBlankLineAfter
      Paragraph[49, 74] isTrailingBlankLine
        Text[49, 73] chars:[49, 73, "Defin … raph)"]
````````````````````````````````


```````````````````````````````` example Definition List Extension: 29
Definition Term
: Definition of above term
: Another definition of above term
.
<dl>
  <dt>Definition Term</dt>
  <dd>Definition of above term</dd>
  <dd>Another definition of above term</dd>
</dl>
.
Document[0, 77]
  DefinitionList[0, 77] isTight
    DefinitionTerm[0, 16]
      Paragraph[0, 16]
        Text[0, 15] chars:[0, 15, "Defin …  Term"]
    DefinitionItem[16, 43] open:[16, 17, ":"] isTight
      Paragraph[18, 43]
        Text[18, 42] chars:[18, 42, "Defin …  term"]
    DefinitionItem[43, 77] open:[43, 44, ":"] isTight
      Paragraph[45, 77]
        Text[45, 77] chars:[45, 77, "Anoth …  term"]
````````````````````````````````


```````````````````````````````` example Definition List Extension: 30
Definition Term
: Definition of above term
: Another definition of above term

.
<dl>
  <dt>Definition Term</dt>
  <dd>Definition of above term</dd>
  <dd>Another definition of above term</dd>
</dl>
.
Document[0, 79]
  DefinitionList[0, 78] isTight
    DefinitionTerm[0, 16]
      Paragraph[0, 16]
        Text[0, 15] chars:[0, 15, "Defin …  Term"]
    DefinitionItem[16, 43] open:[16, 17, ":"] isTight
      Paragraph[18, 43]
        Text[18, 42] chars:[18, 42, "Defin …  term"]
    DefinitionItem[43, 78] open:[43, 44, ":"] isTight hadBlankLineAfter
      Paragraph[45, 78] isTrailingBlankLine
        Text[45, 77] chars:[45, 77, "Anoth …  term"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
Definition Term
: definition item 
.
<dl>
  <dt md-pos="0-16">Definition Term</dt>
  <dd md-pos="16-34">definition item</dd>
</dl>
.
Document[0, 34]
  DefinitionList[0, 34] isTight
    DefinitionTerm[0, 16]
      Paragraph[0, 16]
        Text[0, 15] chars:[0, 15, "Defin …  Term"]
    DefinitionItem[16, 34] open:[16, 17, ":"] isTight
      Paragraph[18, 34]
        Text[18, 33] chars:[18, 33, "defin …  item"]
````````````````````````````````


## Blank Lines

```````````````````````````````` example(Blank Lines: 1) options(blank-lines-in-ast)
Term 1
Term 2

:   Definition 1 paragraph 1 line 1 ...
Definition 1 paragraph 1 line 2 (lazy)
.
<dl>
  <dt>Term 1</dt>
  <dt>Term 2</dt>
  <dd>
    <p>Definition 1 paragraph 1 line 1 ...
    Definition 1 paragraph 1 line 2 (lazy)</p>
  </dd>
</dl>
.
Document[0, 93]
  DefinitionList[0, 93] isLoose
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionTerm[7, 14]
      Paragraph[7, 14]
        Text[7, 13] chars:[7, 13, "Term 2"]
    BlankLine[14, 15]
    DefinitionItem[15, 93] open:[15, 16, ":"] isLoose
      Paragraph[19, 93]
        Text[19, 54] chars:[19, 54, "Defin … 1 ..."]
        SoftLineBreak[54, 55]
        Text[55, 93] chars:[55, 93, "Defin … lazy)"]
````````````````````````````````


```````````````````````````````` example(Blank Lines: 2) options(blank-lines-in-ast)
Term 1
Term 2

:   Definition 1 paragraph 1 line 1 ...
Definition 1 paragraph 1 line 2 (lazy)
    
    Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2

.
<dl>
  <dt>Term 1</dt>
  <dt>Term 2</dt>
  <dd>
    <p>Definition 1 paragraph 1 line 1 ...
    Definition 1 paragraph 1 line 2 (lazy)</p>
    <p>Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2</p>
  </dd>
</dl>
.
Document[0, 176]
  DefinitionList[0, 175] isLoose
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionTerm[7, 14]
      Paragraph[7, 14]
        Text[7, 13] chars:[7, 13, "Term 2"]
    BlankLine[14, 15]
    DefinitionItem[15, 175] open:[15, 16, ":"] isLoose hadBlankLineAfter
      Paragraph[19, 94] isTrailingBlankLine
        Text[19, 54] chars:[19, 54, "Defin … 1 ..."]
        SoftLineBreak[54, 55]
        Text[55, 93] chars:[55, 93, "Defin … lazy)"]
      BlankLine[94, 99]
      Paragraph[103, 175] isTrailingBlankLine
        Text[103, 138] chars:[103, 138, "Defin … 1 ..."]
        SoftLineBreak[138, 139]
        Text[143, 174] chars:[143, 174, "Defin … ine 2"]
  BlankLine[175, 176]
````````````````````````````````


```````````````````````````````` example(Blank Lines: 3) options(blank-lines-in-ast)
Term 1
Term 2

:   Definition 1 paragraph 1 line 1 ...
Definition 1 paragraph 1 line 2 (lazy)
    
    Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2

:   Definition 2 paragraph 1 line 1 ...
Definition 2 paragraph 1 line 2 (lazy)

Term 3
:   Definition 3 (no paragraph)
:   Definition 4 (no paragraph)
:   Definition 5 line 1 ...
    Definition 5 line 2 (no paragraph)

:   Definition 6 paragraph 1 line 1 ...
Definition 6 paragraph 1 line 2
:   Definition 7 (no paragraph)
:   Definition 8 paragraph 1 line 1 (forced paragraph) ...
    Definition 8 paragraph 1 line 2
    
    Definition 8 paragraph 2 line 1
    
Term 4
:   Definition 9 paragraph 1 line 1 (forced paragraph) ...
    Definition 9 paragraph 1 line 2
    
    Definition 9 paragraph 2 line 1
:   Definition 10 (no paragraph)
.
<dl>
  <dt>Term 1</dt>
  <dt>Term 2</dt>
  <dd>
    <p>Definition 1 paragraph 1 line 1 ...
    Definition 1 paragraph 1 line 2 (lazy)</p>
    <p>Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2</p>
  </dd>
  <dd>
    <p>Definition 2 paragraph 1 line 1 ...
    Definition 2 paragraph 1 line 2 (lazy)</p>
  </dd>
  <dt>Term 3</dt>
  <dd>
    <p>Definition 3 (no paragraph)</p>
  </dd>
  <dd>
    <p>Definition 4 (no paragraph)</p>
  </dd>
  <dd>
    <p>Definition 5 line 1 ...
    Definition 5 line 2 (no paragraph)</p>
  </dd>
  <dd>
    <p>Definition 6 paragraph 1 line 1 ...
    Definition 6 paragraph 1 line 2</p>
  </dd>
  <dd>
    <p>Definition 7 (no paragraph)</p>
  </dd>
  <dd>
    <p>Definition 8 paragraph 1 line 1 (forced paragraph) ...
    Definition 8 paragraph 1 line 2</p>
    <p>Definition 8 paragraph 2 line 1</p>
  </dd>
  <dt>Term 4</dt>
  <dd>
    <p>Definition 9 paragraph 1 line 1 (forced paragraph) ...
    Definition 9 paragraph 1 line 2</p>
    <p>Definition 9 paragraph 2 line 1</p>
  </dd>
  <dd>
    <p>Definition 10 (no paragraph)</p>
  </dd>
</dl>
.
Document[0, 815]
  DefinitionList[0, 815] isLoose
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionTerm[7, 14]
      Paragraph[7, 14]
        Text[7, 13] chars:[7, 13, "Term 2"]
    BlankLine[14, 15]
    DefinitionItem[15, 175] open:[15, 16, ":"] isLoose hadBlankLineAfter
      Paragraph[19, 94] isTrailingBlankLine
        Text[19, 54] chars:[19, 54, "Defin … 1 ..."]
        SoftLineBreak[54, 55]
        Text[55, 93] chars:[55, 93, "Defin … lazy)"]
      BlankLine[94, 99]
      Paragraph[103, 175] isTrailingBlankLine
        Text[103, 138] chars:[103, 138, "Defin … 1 ..."]
        SoftLineBreak[138, 139]
        Text[143, 174] chars:[143, 174, "Defin … ine 2"]
    BlankLine[175, 176]
    DefinitionItem[176, 255] open:[176, 177, ":"] isLoose hadBlankLineAfter
      Paragraph[180, 255] isTrailingBlankLine
        Text[180, 215] chars:[180, 215, "Defin … 1 ..."]
        SoftLineBreak[215, 216]
        Text[216, 254] chars:[216, 254, "Defin … lazy)"]
    BlankLine[255, 256]
    DefinitionTerm[256, 263]
      Paragraph[256, 263]
        Text[256, 262] chars:[256, 262, "Term 3"]
    DefinitionItem[263, 295] open:[263, 264, ":"] isLoose
      Paragraph[267, 295]
        Text[267, 294] chars:[267, 294, "Defin … raph)"]
    DefinitionItem[295, 327] open:[295, 296, ":"] isLoose
      Paragraph[299, 327]
        Text[299, 326] chars:[299, 326, "Defin … raph)"]
    DefinitionItem[327, 394] open:[327, 328, ":"] isLoose hadBlankLineAfter
      Paragraph[331, 394] isTrailingBlankLine
        Text[331, 354] chars:[331, 354, "Defin … 1 ..."]
        SoftLineBreak[354, 355]
        Text[359, 393] chars:[359, 393, "Defin … raph)"]
    BlankLine[394, 395]
    DefinitionItem[395, 467] open:[395, 396, ":"] isLoose
      Paragraph[399, 467]
        Text[399, 434] chars:[399, 434, "Defin … 1 ..."]
        SoftLineBreak[434, 435]
        Text[435, 466] chars:[435, 466, "Defin … ine 2"]
    DefinitionItem[467, 499] open:[467, 468, ":"] isLoose
      Paragraph[471, 499]
        Text[471, 498] chars:[471, 498, "Defin … raph)"]
    DefinitionItem[499, 635] open:[499, 500, ":"] isLoose hadBlankLineAfter
      Paragraph[503, 594] isTrailingBlankLine
        Text[503, 557] chars:[503, 557, "Defin … ) ..."]
        SoftLineBreak[557, 558]
        Text[562, 593] chars:[562, 593, "Defin … ine 2"]
      BlankLine[594, 599]
      Paragraph[603, 635] isTrailingBlankLine
        Text[603, 634] chars:[603, 634, "Defin … ine 1"]
    BlankLine[635, 640]
    DefinitionTerm[640, 647]
      Paragraph[640, 647]
        Text[640, 646] chars:[640, 646, "Term 4"]
    DefinitionItem[647, 783] open:[647, 648, ":"] isLoose hadBlankLineAfter
      Paragraph[651, 742] isTrailingBlankLine
        Text[651, 705] chars:[651, 705, "Defin … ) ..."]
        SoftLineBreak[705, 706]
        Text[710, 741] chars:[710, 741, "Defin … ine 2"]
      BlankLine[742, 747]
      Paragraph[751, 783]
        Text[751, 782] chars:[751, 782, "Defin … ine 1"]
    DefinitionItem[783, 815] open:[783, 784, ":"] isLoose
      Paragraph[787, 815]
        Text[787, 815] chars:[787, 815, "Defin … raph)"]
````````````````````````````````


Check indentation, there is a bug, this should parse as nested list items. it was not.

```````````````````````````````` example Blank Lines: 4
Definition
: 1. dd asdlfalsdfj asldfj las;dfj l;asfdj als fjdl;asj fd;lajs
       fdl;ajsdf l;ajsfdl;ajsf dl;asjfd l;ajsdf l;asdjfl;asdfj l;asfdj

     2. asldfaslfdjasdfl;jasdfl;j

.
<dl>
  <dt>Definition</dt>
  <dd>
    <ol>
      <li>
        <p>dd asdlfalsdfj asldfj las;dfj l;asfdj als fjdl;asj fd;lajs
        fdl;ajsdf l;ajsfdl;ajsf dl;asjfd l;ajsdf l;asdjfl;asdfj l;asfdj</p>
        <ol start="2">
          <li>asldfaslfdjasdfl;jasdfl;j</li>
        </ol>
      </li>
    </ol>
  </dd>
</dl>
.
Document[0, 182]
  DefinitionList[0, 181] isTight
    DefinitionTerm[0, 11]
      Paragraph[0, 11]
        Text[0, 10] chars:[0, 10, "Definition"]
    DefinitionItem[11, 181] open:[11, 12, ":"] isTight hadBlankLineAfter
      OrderedList[13, 181] isLoose delimiter:'.'
        OrderedListItem[13, 181] open:[13, 15, "1."] isLoose hadBlankLineAfter
          Paragraph[16, 146] isTrailingBlankLine
            Text[16, 74] chars:[16, 74, "dd as … ;lajs"]
            SoftLineBreak[74, 75]
            Text[82, 145] chars:[82, 145, "fdl;a … asfdj"]
          OrderedList[152, 181] isTight start:2 delimiter:'.'
            OrderedListItem[152, 181] open:[152, 154, "2."] isTight hadBlankLineAfter
              Paragraph[155, 181] isTrailingBlankLine
                Text[155, 180] chars:[155, 180, "asldf … dfl;j"]
````````````````````````````````


```````````````````````````````` example(Blank Lines: 5) options(blank-lines-in-ast)
Definition
: 1. dd asdlfalsdfj asldfj las;dfj l;asfdj als fjdl;asj fd;lajs
       fdl;ajsdf l;ajsfdl;ajsf dl;asjfd l;ajsdf l;asdjfl;asdfj l;asfdj

     2. asldfaslfdjasdfl;jasdfl;j

.
<dl>
  <dt>Definition</dt>
  <dd>
    <ol>
      <li>
        <p>dd asdlfalsdfj asldfj las;dfj l;asfdj als fjdl;asj fd;lajs
        fdl;ajsdf l;ajsfdl;ajsf dl;asjfd l;ajsdf l;asdjfl;asdfj l;asfdj</p>
        <ol start="2">
          <li>asldfaslfdjasdfl;jasdfl;j</li>
        </ol>
      </li>
    </ol>
  </dd>
</dl>
.
Document[0, 182]
  DefinitionList[0, 181] isTight
    DefinitionTerm[0, 11]
      Paragraph[0, 11]
        Text[0, 10] chars:[0, 10, "Definition"]
    DefinitionItem[11, 181] open:[11, 12, ":"] isTight hadBlankLineAfter
      OrderedList[13, 181] isLoose delimiter:'.'
        OrderedListItem[13, 181] open:[13, 15, "1."] isLoose hadBlankLineAfter
          Paragraph[16, 146] isTrailingBlankLine
            Text[16, 74] chars:[16, 74, "dd as … ;lajs"]
            SoftLineBreak[74, 75]
            Text[82, 145] chars:[82, 145, "fdl;a … asfdj"]
          BlankLine[146, 147]
          OrderedList[152, 181] isTight start:2 delimiter:'.'
            OrderedListItem[152, 181] open:[152, 154, "2."] isTight hadBlankLineAfter
              Paragraph[155, 181] isTrailingBlankLine
                Text[155, 180] chars:[155, 180, "asldf … dfl;j"]
  BlankLine[181, 182]
````````````````````````````````


## Suppress HTML block tag EOL

```````````````````````````````` example(Suppress HTML block tag EOL: 1) options(suppress-format-eol)
Term 1
Term 2

:   Definition 1 paragraph 1 line 1 ...
Definition 1 paragraph 1 line 2 (lazy)
    
    Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2

:   Definition 2 paragraph 1 line 1 ...
Definition 2 paragraph 1 line 2 (lazy)

Term 3
:   Definition 3 (no paragraph)
:   Definition 4 (no paragraph)
:   Definition 5 line 1 ...
    Definition 5 line 2 (no paragraph)

:   Definition 6 paragraph 1 line 1 ...
Definition 6 paragraph 1 line 2
:   Definition 7 (no paragraph)
:   Definition 8 paragraph 1 line 1 (forced paragraph) ...
    Definition 8 paragraph 1 line 2
    
    Definition 8 paragraph 2 line 1
    
Term 4
:   Definition 9 paragraph 1 line 1 (forced paragraph) ...
    Definition 9 paragraph 1 line 2
    
    Definition 9 paragraph 2 line 1
:   Definition 10 (no paragraph)
.
<dl>
<dt>Term 1</dt><dt>Term 2</dt><dd>
<p>Definition 1 paragraph 1 line 1 ...
Definition 1 paragraph 1 line 2 (lazy)</p><p>Definition 1 paragraph 2 line 1 ...
Definition 1 paragraph 2 line 2</p>
</dd><dd>
<p>Definition 2 paragraph 1 line 1 ...
Definition 2 paragraph 1 line 2 (lazy)</p>
</dd><dt>Term 3</dt><dd>
<p>Definition 3 (no paragraph)</p>
</dd><dd>
<p>Definition 4 (no paragraph)</p>
</dd><dd>
<p>Definition 5 line 1 ...
Definition 5 line 2 (no paragraph)</p>
</dd><dd>
<p>Definition 6 paragraph 1 line 1 ...
Definition 6 paragraph 1 line 2</p>
</dd><dd>
<p>Definition 7 (no paragraph)</p>
</dd><dd>
<p>Definition 8 paragraph 1 line 1 (forced paragraph) ...
Definition 8 paragraph 1 line 2</p><p>Definition 8 paragraph 2 line 1</p>
</dd><dt>Term 4</dt><dd>
<p>Definition 9 paragraph 1 line 1 (forced paragraph) ...
Definition 9 paragraph 1 line 2</p><p>Definition 9 paragraph 2 line 1</p>
</dd><dd>
<p>Definition 10 (no paragraph)</p>
</dd>
</dl>
.
Document[0, 815]
  DefinitionList[0, 815] isLoose
    DefinitionTerm[0, 7]
      Paragraph[0, 7]
        Text[0, 6] chars:[0, 6, "Term 1"]
    DefinitionTerm[7, 14]
      Paragraph[7, 14]
        Text[7, 13] chars:[7, 13, "Term 2"]
    DefinitionItem[15, 175] open:[15, 16, ":"] isLoose hadBlankLineAfter
      Paragraph[19, 94] isTrailingBlankLine
        Text[19, 54] chars:[19, 54, "Defin … 1 ..."]
        SoftLineBreak[54, 55]
        Text[55, 93] chars:[55, 93, "Defin … lazy)"]
      Paragraph[103, 175] isTrailingBlankLine
        Text[103, 138] chars:[103, 138, "Defin … 1 ..."]
        SoftLineBreak[138, 139]
        Text[143, 174] chars:[143, 174, "Defin … ine 2"]
    DefinitionItem[176, 255] open:[176, 177, ":"] isLoose hadBlankLineAfter
      Paragraph[180, 255] isTrailingBlankLine
        Text[180, 215] chars:[180, 215, "Defin … 1 ..."]
        SoftLineBreak[215, 216]
        Text[216, 254] chars:[216, 254, "Defin … lazy)"]
    DefinitionTerm[256, 263]
      Paragraph[256, 263]
        Text[256, 262] chars:[256, 262, "Term 3"]
    DefinitionItem[263, 295] open:[263, 264, ":"] isLoose
      Paragraph[267, 295]
        Text[267, 294] chars:[267, 294, "Defin … raph)"]
    DefinitionItem[295, 327] open:[295, 296, ":"] isLoose
      Paragraph[299, 327]
        Text[299, 326] chars:[299, 326, "Defin … raph)"]
    DefinitionItem[327, 394] open:[327, 328, ":"] isLoose hadBlankLineAfter
      Paragraph[331, 394] isTrailingBlankLine
        Text[331, 354] chars:[331, 354, "Defin … 1 ..."]
        SoftLineBreak[354, 355]
        Text[359, 393] chars:[359, 393, "Defin … raph)"]
    DefinitionItem[395, 467] open:[395, 396, ":"] isLoose
      Paragraph[399, 467]
        Text[399, 434] chars:[399, 434, "Defin … 1 ..."]
        SoftLineBreak[434, 435]
        Text[435, 466] chars:[435, 466, "Defin … ine 2"]
    DefinitionItem[467, 499] open:[467, 468, ":"] isLoose
      Paragraph[471, 499]
        Text[471, 498] chars:[471, 498, "Defin … raph)"]
    DefinitionItem[499, 635] open:[499, 500, ":"] isLoose hadBlankLineAfter
      Paragraph[503, 594] isTrailingBlankLine
        Text[503, 557] chars:[503, 557, "Defin … ) ..."]
        SoftLineBreak[557, 558]
        Text[562, 593] chars:[562, 593, "Defin … ine 2"]
      Paragraph[603, 635] isTrailingBlankLine
        Text[603, 634] chars:[603, 634, "Defin … ine 1"]
    DefinitionTerm[640, 647]
      Paragraph[640, 647]
        Text[640, 646] chars:[640, 646, "Term 4"]
    DefinitionItem[647, 783] open:[647, 648, ":"] isLoose hadBlankLineAfter
      Paragraph[651, 742] isTrailingBlankLine
        Text[651, 705] chars:[651, 705, "Defin … ) ..."]
        SoftLineBreak[705, 706]
        Text[710, 741] chars:[710, 741, "Defin … ine 2"]
      Paragraph[751, 783]
        Text[751, 782] chars:[751, 782, "Defin … ine 1"]
    DefinitionItem[783, 815] open:[783, 784, ":"] isLoose
      Paragraph[787, 815]
        Text[787, 815] chars:[787, 815, "Defin … raph)"]
````````````````````````````````


## Issue

Issue #172, Markdown parser cutting words out

```````````````````````````````` example Issue: 1
Commit Message Length Configuration 
===================================
                                                                                                                                          
The maximum lengths of the subject and message body can be
configured in the standard Gerrit config file `gerrit.config`.
The defaults are 50 characters for the summary and 72 characters
for the description, as recommended by the
[git tutorial](https://kernel.googlesource.com/pub/scm/git/git/+/927a503cd07718ea0f700052043f383253904a56/Documentation/tutorial.txt#64)
and [expanded upon by Tim Pope](http://www.tpope.net/node/106).
                                                                                                                                          
commitmessage.maxSubjectLength
:→Maximum length of the commit message's subject line. Defaults
→to 50 if not specified or less than 0.
                                                                                                                                          
commitmessage.maxLineLength
:→Maximum length of a line in the commit message's body. Defaults
→to 72 if not specified or less than 0.
                                                                                                                                          
commitmessage.longLinesThreshold
:→Percentage of commit message lines allowed to exceed the
→maximum length before a warning or error is generated. Defaults
→to 33 if not specified or less than 0.
                                                                                                                                          
commitmessage.rejectTooLong
:→If set to `true`, reject commits whose subject or line
→length exceeds the maximum allowed length. If not
→specified, defaults to `false`.
.
<h1>Commit Message Length Configuration</h1>
<p>The maximum lengths of the subject and message body can be
configured in the standard Gerrit config file <code>gerrit.config</code>.
The defaults are 50 characters for the summary and 72 characters
for the description, as recommended by the
<a href="https://kernel.googlesource.com/pub/scm/git/git/+/927a503cd07718ea0f700052043f383253904a56/Documentation/tutorial.txt#64">git tutorial</a>
and <a href="http://www.tpope.net/node/106">expanded upon by Tim Pope</a>.</p>
<dl>
  <dt>commitmessage.maxSubjectLength</dt>
  <dd>Maximum length of the commit message's subject line. Defaults
    to 50 if not specified or less than 0.</dd>
  <dt>commitmessage.maxLineLength</dt>
  <dd>Maximum length of a line in the commit message's body. Defaults
    to 72 if not specified or less than 0.</dd>
  <dt>commitmessage.longLinesThreshold</dt>
  <dd>Percentage of commit message lines allowed to exceed the
    maximum length before a warning or error is generated. Defaults
    to 33 if not specified or less than 0.</dd>
  <dt>commitmessage.rejectTooLong</dt>
  <dd>If set to <code>true</code>, reject commits whose subject or line
    length exceeds the maximum allowed length. If not
    specified, defaults to <code>false</code>.</dd>
</dl>
.
Document[0, 1833]
  Heading[0, 72] text:[0, 35, "Commit Message Length Configuration"] textClose:[37, 72, "==================================="]
    Text[0, 35] chars:[0, 35, "Commi … ation"]
  Paragraph[212, 643] isTrailingBlankLine
    Text[212, 270] chars:[212, 270, "The m … an be"]
    SoftLineBreak[270, 271]
    Text[271, 317] chars:[271, 317, "confi … file "]
    Code[317, 332] textOpen:[317, 318, "`"] text:[318, 331, "gerri … t.config"] textClose:[331, 332, "`"]
      Text[318, 331] chars:[318, 331, "gerri … onfig"]
    Text[332, 333] chars:[332, 333, "."]
    SoftLineBreak[333, 334]
    Text[334, 398] chars:[334, 398, "The d … cters"]
    SoftLineBreak[398, 399]
    Text[399, 441] chars:[399, 441, "for t … y the"]
    SoftLineBreak[441, 442]
    Link[442, 578] textOpen:[442, 443, "["] text:[443, 455, "git tutorial"] textClose:[455, 456, "]"] linkOpen:[456, 457, "("] url:[457, 577, "https://kernel.googlesource.com/pub/scm/git/git/+/927a503cd07718ea0f700052043f383253904a56/Documentation/tutorial.txt#64"] pageRef:[457, 574, "https://kernel.googlesource.com/pub/scm/git/git/+/927a503cd07718ea0f700052043f383253904a56/Documentation/tutorial.txt"] anchorMarker:[574, 575, "#"] anchorRef:[575, 577, "64"] linkClose:[577, 578, ")"]
      Text[443, 455] chars:[443, 455, "git t … orial"]
    SoftLineBreak[578, 579]
    Text[579, 583] chars:[579, 583, "and "]
    Link[583, 641] textOpen:[583, 584, "["] text:[584, 609, "expanded upon by Tim Pope"] textClose:[609, 610, "]"] linkOpen:[610, 611, "("] url:[611, 640, "http://www.tpope.net/node/106"] pageRef:[611, 640, "http://www.tpope.net/node/106"] linkClose:[640, 641, ")"]
      Text[584, 609] chars:[584, 609, "expan …  Pope"]
    Text[641, 642] chars:[641, 642, "."]
  DefinitionList[782, 1833] isTight
    DefinitionTerm[782, 813]
      Paragraph[782, 813]
        Text[782, 812] chars:[782, 812, "commi … ength"]
    DefinitionItem[813, 917] open:[813, 814, ":"] isTight hadBlankLineAfter
      Paragraph[815, 917] isTrailingBlankLine
        Text[815, 876] chars:[815, 876, "Maxim … aults"]
        SoftLineBreak[876, 877]
        Text[878, 916] chars:[878, 916, "to 50 … an 0."]
    DefinitionTerm[1056, 1084]
      Paragraph[1056, 1084]
        Text[1056, 1083] chars:[1056, 1083, "commi … ength"]
    DefinitionItem[1084, 1190] open:[1084, 1085, ":"] isTight hadBlankLineAfter
      Paragraph[1086, 1190] isTrailingBlankLine
        Text[1086, 1149] chars:[1086, 1149, "Maxim … aults"]
        SoftLineBreak[1149, 1150]
        Text[1151, 1189] chars:[1151, 1189, "to 72 … an 0."]
    DefinitionTerm[1329, 1362]
      Paragraph[1329, 1362]
        Text[1329, 1361] chars:[1329, 1361, "commi … shold"]
    DefinitionItem[1362, 1526] open:[1362, 1363, ":"] isTight hadBlankLineAfter
      Paragraph[1364, 1526] isTrailingBlankLine
        Text[1364, 1420] chars:[1364, 1420, "Perce … d the"]
        SoftLineBreak[1420, 1421]
        Text[1422, 1485] chars:[1422, 1485, "maxim … aults"]
        SoftLineBreak[1485, 1486]
        Text[1487, 1525] chars:[1487, 1525, "to 33 … an 0."]
    DefinitionTerm[1665, 1693]
      Paragraph[1665, 1693]
        Text[1665, 1692] chars:[1665, 1692, "commi … oLong"]
    DefinitionItem[1693, 1833] open:[1693, 1694, ":"] isTight
      Paragraph[1695, 1833]
        Text[1695, 1705] chars:[1695, 1705, "If set to "]
        Code[1705, 1711] textOpen:[1705, 1706, "`"] text:[1706, 1710, "true"] textClose:[1710, 1711, "`"]
          Text[1706, 1710] chars:[1706, 1710, "true"]
        Text[1711, 1749] chars:[1711, 1749, ", rej …  line"]
        SoftLineBreak[1749, 1750]
        Text[1751, 1800] chars:[1751, 1800, "lengt … f not"]
        SoftLineBreak[1800, 1801]
        Text[1802, 1825] chars:[1802, 1825, "speci … s to "]
        Code[1825, 1832] textOpen:[1825, 1826, "`"] text:[1826, 1831, "false"] textClose:[1831, 1832, "`"]
          Text[1826, 1831] chars:[1826, 1831, "false"]
        Text[1832, 1833] chars:[1832, 1833, "."]
````````````````````````````````


