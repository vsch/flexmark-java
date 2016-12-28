---
title: Definition Extension Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Definition

flexmark-java extension for definition list processing

Converts definition syntax of
[Php Markdown Extra Def List](https://michelf.ca/projects/php-markdown/extra/#def-list) into
`<dl><dt></dt><dd></dd></dl>` html

Converts definition lists into definition list nodes. Definition terms have an optional `:` at
the end. Definition items can be preceded by `:` or `~`

Definition list is one or more definition terms with one or more definitions.

```markdown
Definition Term
: Definition of above term
: Another definition of above term
```

Definitions terms can have not definitions if they are not the last term before a definition:

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

Paragraph block lines are split into definition terms before inline processing it is possible
that an inline markup starts on one line and finishes on another. If this paragraph is converted
to definition terms then the markup will be ignored and its opening marker attributed to one
definition term and its closing marker to the following one.

not a definition

```````````````````````````````` example Definition: 1
: definition item 
.
<p>: definition item</p>
.
Document[0, 19]
  Paragraph[0, 19]
    Text[0, 17] chars:[0, 17, ": def …  item"]
````````````````````````````````


not a definition

```````````````````````````````` example Definition: 2
- bullet item
: definition item 
.
<ul>
  <li>bullet item
  : definition item</li>
</ul>
.
Document[0, 33]
  BulletList[0, 33] isTight
    BulletListItem[0, 33] open:[0, 1, "-"] isTight
      Paragraph[2, 33]
        Text[2, 13] chars:[2, 13, "bulle …  item"]
        SoftLineBreak[13, 14]
        Text[14, 31] chars:[14, 31, ": def …  item"]
````````````````````````````````


not a definition

```````````````````````````````` example Definition: 3
1. numbered item
: definition item 
.
<ol>
  <li>numbered item
  : definition item</li>
</ol>
.
Document[0, 36]
  OrderedList[0, 36] isTight delimiter:'.'
    OrderedListItem[0, 36] open:[0, 2, "1."] isTight
      Paragraph[3, 36]
        Text[3, 16] chars:[3, 16, "numbe …  item"]
        SoftLineBreak[16, 17]
        Text[17, 34] chars:[17, 34, ": def …  item"]
````````````````````````````````


not a definition

```````````````````````````````` example Definition: 4
## Header
: definition item 
.
<h2>Header</h2>
<p>: definition item</p>
.
Document[0, 29]
  Heading[0, 9] textOpen:[0, 2, "##"] text:[3, 9, "Header"]
    Text[3, 9] chars:[3, 9, "Header"]
  Paragraph[10, 29]
    Text[10, 27] chars:[10, 27, ": def …  item"]
````````````````````````````````


not a definition

```````````````````````````````` example Definition: 5
```markdown
some stuff
```

: definition item 
.
<pre><code class="language-markdown">some stuff
</code></pre>
<p>: definition item</p>
.
Document[0, 47]
  FencedCodeBlock[0, 26] open:[0, 3, "```"] info:[3, 11, "markdown"] content:[12, 23] lines[1] close:[23, 26, "```"]
  Paragraph[28, 47]
    Text[28, 45] chars:[28, 45, ": def …  item"]
````````````````````````````````


not a definition in a lazy block quote

```````````````````````````````` example Definition: 6
> definition
: definition item 
.
<blockquote>
  <p>definition
  : definition item</p>
</blockquote>
.
Document[0, 32]
  BlockQuote[0, 32] marker:[0, 1, ">"]
    Paragraph[2, 32]
      Text[2, 12] chars:[2, 12, "definition"]
      SoftLineBreak[12, 13]
      Text[13, 30] chars:[13, 30, ": def …  item"]
````````````````````````````````


a definition in a block quote

```````````````````````````````` example Definition: 7
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
Document[0, 39]
  BlockQuote[0, 39] marker:[0, 1, ">"]
    Paragraph[2, 18]
      Text[2, 17] chars:[2, 17, "Defin …  Term"]
    DefinitionItem[20, 39] open:[20, 21, ":"] isTight
      Paragraph[22, 39]
        Text[22, 37] chars:[22, 37, "defin …  item"]
````````````````````````````````


simple list

```````````````````````````````` example Definition: 8
Definition Term
: definition item 
.
<dl>
   <dt>Definition Term</dt>
   <dd>definition item</dd>
</dl>
.
Document[0, 35]
  Paragraph[0, 16]
    Text[0, 15] chars:[0, 15, "Defin …  Term"]
  DefinitionItem[16, 35] open:[16, 17, ":"] isTight
    Paragraph[18, 35]
      Text[18, 33] chars:[18, 33, "defin …  item"]
````````````````````````````````


A simple definition list:

```````````````````````````````` example Definition: 9
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
  Paragraph[0, 7]
    Text[0, 6] chars:[0, 6, "Term 1"]
  DefinitionItem[7, 24] open:[7, 8, ":"] isTight hadBlankLineAfter
    Paragraph[11, 24]
      Text[11, 23] chars:[11, 23, "Defin … ion 1"]
  Paragraph[25, 32]
    Text[25, 31] chars:[25, 31, "Term 2"]
  DefinitionItem[32, 49] open:[32, 33, ":"] isTight hadBlankLineAfter
    Paragraph[36, 49]
      Text[36, 48] chars:[36, 48, "Defin … ion 2"]
````````````````````````````````


With multiple terms:

```````````````````````````````` example Definition: 10
Term 1
Term 2
:   Definition 1

Term 3
Term 4
:   Definition 2

.
.
Document[0, 64]
  Paragraph[0, 14]
    Text[0, 6] chars:[0, 6, "Term 1"]
    SoftLineBreak[6, 7]
    Text[7, 13] chars:[7, 13, "Term 2"]
  DefinitionItem[14, 31] open:[14, 15, ":"] isTight hadBlankLineAfter
    Paragraph[18, 31]
      Text[18, 30] chars:[18, 30, "Defin … ion 1"]
  Paragraph[32, 46]
    Text[32, 38] chars:[32, 38, "Term 3"]
    SoftLineBreak[38, 39]
    Text[39, 45] chars:[39, 45, "Term 4"]
  DefinitionItem[46, 63] open:[46, 47, ":"] isTight hadBlankLineAfter
    Paragraph[50, 63]
      Text[50, 62] chars:[50, 62, "Defin … ion 2"]
````````````````````````````````


With multiple definitions:

```````````````````````````````` example Definition: 11
Term 1
:   Definition 1
:   Definition 2

Term 2
:   Definition 3
:   Definition 4

.
.
Document[0, 84]
  Paragraph[0, 7]
    Text[0, 6] chars:[0, 6, "Term 1"]
  DefinitionItem[7, 24] open:[7, 8, ":"] isTight
    Paragraph[11, 24]
      Text[11, 23] chars:[11, 23, "Defin … ion 1"]
  DefinitionItem[24, 41] open:[24, 25, ":"] isTight hadBlankLineAfter
    Paragraph[28, 41]
      Text[28, 40] chars:[28, 40, "Defin … ion 2"]
  Paragraph[42, 49]
    Text[42, 48] chars:[42, 48, "Term 2"]
  DefinitionItem[49, 66] open:[49, 50, ":"] isTight
    Paragraph[53, 66]
      Text[53, 65] chars:[53, 65, "Defin … ion 3"]
  DefinitionItem[66, 83] open:[66, 67, ":"] isTight hadBlankLineAfter
    Paragraph[70, 83]
      Text[70, 82] chars:[70, 82, "Defin … ion 4"]
````````````````````````````````


With multiple lines per definition:

```````````````````````````````` example Definition: 12
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
.
Document[0, 216]
  Paragraph[0, 7]
    Text[0, 6] chars:[0, 6, "Term 1"]
  DefinitionItem[7, 55] open:[7, 8, ":"] isTight
    Paragraph[11, 55]
      Text[11, 34] chars:[11, 34, "Defin … 1 ..."]
      SoftLineBreak[34, 35]
      Text[35, 54] chars:[35, 54, "Defin … ine 2"]
  DefinitionItem[55, 103] open:[55, 56, ":"] isTight hadBlankLineAfter
    Paragraph[59, 103]
      Text[59, 82] chars:[59, 82, "Defin … 1 ..."]
      SoftLineBreak[82, 83]
      Text[83, 102] chars:[83, 102, "Defin … ine 2"]
  Paragraph[104, 111]
    Text[104, 110] chars:[104, 110, "Term 2"]
  DefinitionItem[111, 163] open:[111, 112, ":"] isTight
    Paragraph[115, 163]
      Text[115, 138] chars:[115, 138, "Defin … 2 ..."]
      SoftLineBreak[138, 139]
      Text[143, 162] chars:[143, 162, "Defin … ine 2"]
  DefinitionItem[163, 215] open:[163, 164, ":"] isTight hadBlankLineAfter
    Paragraph[167, 215]
      Text[167, 190] chars:[167, 190, "Defin … 2 ..."]
      SoftLineBreak[190, 191]
      Text[195, 214] chars:[195, 214, "Defin … ine 2"]
````````````````````````````````


With paragraphs:

```````````````````````````````` example Definition: 13
Term 1

:   Definition 1 (paragraph)

Term 2

:   Definition 2 (paragraph)

.
.
Document[0, 76]
  Paragraph[0, 7]
    Text[0, 6] chars:[0, 6, "Term 1"]
  DefinitionItem[8, 37] open:[8, 9, ":"] isLoose hadBlankLineAfter
    Paragraph[12, 37]
      Text[12, 36] chars:[12, 36, "Defin … raph)"]
  Paragraph[38, 45]
    Text[38, 44] chars:[38, 44, "Term 2"]
  DefinitionItem[46, 75] open:[46, 47, ":"] isLoose hadBlankLineAfter
    Paragraph[50, 75]
      Text[50, 74] chars:[50, 74, "Defin … raph)"]
````````````````````````````````


With multiple paragraphs:

```````````````````````````````` example Definition: 14
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
.
Document[0, 327]
  Paragraph[0, 7]
    Text[0, 6] chars:[0, 6, "Term 1"]
  DefinitionItem[8, 158] open:[8, 9, ":"] isLoose hadBlankLineAfter
    Paragraph[12, 81]
      Text[12, 47] chars:[12, 47, "Defin … 1 ..."]
      SoftLineBreak[47, 48]
      Text[49, 80] chars:[49, 80, "Defin … ine 2"]
    Paragraph[86, 158]
      Text[86, 121] chars:[86, 121, "Defin … 1 ..."]
      SoftLineBreak[121, 122]
      Text[126, 157] chars:[126, 157, "Defin … ine 2"]
  Paragraph[159, 166]
    Text[159, 165] chars:[159, 165, "Term 2"]
  DefinitionItem[167, 326] open:[167, 168, ":"] isLoose hadBlankLineAfter
    Paragraph[171, 246]
      Text[171, 206] chars:[171, 206, "Defin … 1 ..."]
      SoftLineBreak[206, 207]
      Text[207, 245] chars:[207, 245, "Defin … lazy)"]
    Paragraph[251, 326]
      Text[251, 286] chars:[251, 286, "Defin … 1 ..."]
      SoftLineBreak[286, 287]
      Text[287, 325] chars:[287, 325, "Defin … lazy)"]
````````````````````````````````


A mix:

```````````````````````````````` example Definition: 15
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
.
Document[0, 816]
  Paragraph[0, 14]
    Text[0, 6] chars:[0, 6, "Term 1"]
    SoftLineBreak[6, 7]
    Text[7, 13] chars:[7, 13, "Term 2"]
  DefinitionItem[15, 175] open:[15, 16, ":"] isLoose hadBlankLineAfter
    Paragraph[19, 94]
      Text[19, 54] chars:[19, 54, "Defin … 1 ..."]
      SoftLineBreak[54, 55]
      Text[55, 93] chars:[55, 93, "Defin … lazy)"]
    Paragraph[103, 175]
      Text[103, 138] chars:[103, 138, "Defin … 1 ..."]
      SoftLineBreak[138, 139]
      Text[143, 174] chars:[143, 174, "Defin … ine 2"]
  DefinitionItem[176, 255] open:[176, 177, ":"] isLoose hadBlankLineAfter
    Paragraph[180, 255]
      Text[180, 215] chars:[180, 215, "Defin … 1 ..."]
      SoftLineBreak[215, 216]
      Text[216, 254] chars:[216, 254, "Defin … lazy)"]
  Paragraph[256, 263]
    Text[256, 262] chars:[256, 262, "Term 3"]
  DefinitionItem[263, 295] open:[263, 264, ":"] isTight
    Paragraph[267, 295]
      Text[267, 294] chars:[267, 294, "Defin … raph)"]
  DefinitionItem[295, 327] open:[295, 296, ":"] isTight
    Paragraph[299, 327]
      Text[299, 326] chars:[299, 326, "Defin … raph)"]
  DefinitionItem[327, 394] open:[327, 328, ":"] isTight hadBlankLineAfter
    Paragraph[331, 394]
      Text[331, 354] chars:[331, 354, "Defin … 1 ..."]
      SoftLineBreak[354, 355]
      Text[359, 393] chars:[359, 393, "Defin … raph)"]
  DefinitionItem[395, 467] open:[395, 396, ":"] isLoose
    Paragraph[399, 467]
      Text[399, 434] chars:[399, 434, "Defin … 1 ..."]
      SoftLineBreak[434, 435]
      Text[435, 466] chars:[435, 466, "Defin … ine 2"]
  DefinitionItem[467, 499] open:[467, 468, ":"] isTight
    Paragraph[471, 499]
      Text[471, 498] chars:[471, 498, "Defin … raph)"]
  DefinitionItem[499, 635] open:[499, 500, ":"] isTight hadBlankLineAfter
    Paragraph[503, 594]
      Text[503, 557] chars:[503, 557, "Defin … ) ..."]
      SoftLineBreak[557, 558]
      Text[562, 593] chars:[562, 593, "Defin … ine 2"]
    Paragraph[603, 635]
      Text[603, 634] chars:[603, 634, "Defin … ine 1"]
  Paragraph[640, 647]
    Text[640, 646] chars:[640, 646, "Term 4"]
  DefinitionItem[647, 783] open:[647, 648, ":"] isTight hadBlankLineAfter
    Paragraph[651, 742]
      Text[651, 705] chars:[651, 705, "Defin … ) ..."]
      SoftLineBreak[705, 706]
      Text[710, 741] chars:[710, 741, "Defin … ine 2"]
    Paragraph[751, 783]
      Text[751, 782] chars:[751, 782, "Defin … ine 1"]
  DefinitionItem[783, 816] open:[783, 784, ":"] isTight
    Paragraph[787, 816]
      Text[787, 815] chars:[787, 815, "Defin … raph)"]
````````````````````````````````


inlines allowed

```````````````````````````````` example Definition: 16
Definition **Term**
: definition `item` 
.
<dl>
   <dt>Definition Term</dt>
   <dd>definition item</dd>
</dl>
.
Document[0, 41]
  Paragraph[0, 20]
    Text[0, 11] chars:[0, 11, "Defin … tion "]
    StrongEmphasis[11, 19] textOpen:[11, 13, "**"] text:[13, 17, "Term"] textClose:[17, 19, "**"]
      Text[13, 17] chars:[13, 17, "Term"]
  DefinitionItem[20, 41] open:[20, 21, ":"] isTight
    Paragraph[22, 41]
      Text[22, 33] chars:[22, 33, "defin … tion "]
      Code[33, 39] textOpen:[33, 34, "`"] text:[34, 38, "item"] textClose:[38, 39, "`"]
````````````````````````````````


inlines will be split

```````````````````````````````` example Definition: 17
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
Document[0, 66]
  Paragraph[0, 46]
    Text[0, 11] chars:[0, 11, "Defin … tion "]
    StrongEmphasis[11, 28] textOpen:[11, 13, "**"] text:[13, 26, "Term \nAnother"] textClose:[26, 28, "**"]
      Text[13, 17] chars:[13, 17, "Term"]
      SoftLineBreak[18, 19]
      Text[19, 26] chars:[19, 26, "Another"]
    Text[28, 44] chars:[28, 44, " Defi …  Term"]
  DefinitionItem[46, 66] open:[46, 47, ":"] isTight
    Paragraph[48, 66]
      Text[48, 59] chars:[48, 59, "defin … tion "]
      Code[59, 65] textOpen:[59, 60, "`"] text:[60, 64, "item"] textClose:[64, 65, "`"]
````````````````````````````````


nested elements allowed

```````````````````````````````` example Definition: 18
Definition **Term**
: definition `item` 
    
  paragraph
    
  - bullet item
    - sub item
      
  > block quote    
  
.
<dl>
   <dt>Definition <strong>Term</strong></dt>
   <dd>definition <code>item</code></dd>
</dl>
.
Document[0, 124]
  Paragraph[0, 20]
    Text[0, 11] chars:[0, 11, "Defin … tion "]
    StrongEmphasis[11, 19] textOpen:[11, 13, "**"] text:[13, 17, "Term"] textClose:[17, 19, "**"]
      Text[13, 17] chars:[13, 17, "Term"]
  DefinitionItem[20, 121] open:[20, 21, ":"] isTight hadBlankLineAfter
    Paragraph[22, 41]
      Text[22, 33] chars:[22, 33, "defin … tion "]
      Code[33, 39] textOpen:[33, 34, "`"] text:[34, 38, "item"] textClose:[38, 39, "`"]
    Paragraph[48, 58]
      Text[48, 57] chars:[48, 57, "paragraph"]
    BulletList[65, 94] isTight
      BulletListItem[65, 94] open:[65, 66, "-"] isTight
        Paragraph[67, 79]
          Text[67, 78] chars:[67, 78, "bulle …  item"]
        BulletList[83, 94] isTight
          BulletListItem[83, 94] open:[83, 84, "-"] isTight hadBlankLineAfter
            Paragraph[85, 94]
              Text[85, 93] chars:[85, 93, "sub item"]
    BlockQuote[103, 121] marker:[103, 104, ">"]
      Paragraph[105, 121]
        Text[105, 116] chars:[105, 116, "block … quote"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos, IGNORE)
Definition Term
: definition item 
.
<dl>
   <dt>Definition Term</dt>
   <dd>definition item</dd>
</dl>
.
````````````````````````````````


