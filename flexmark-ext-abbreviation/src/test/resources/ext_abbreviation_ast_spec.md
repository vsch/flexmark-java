---
title: Abbreviation Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Abbreviation

flexmark-java extension for defining abbreviations and turning appearance of these abbreviations
in text into abbr tags with titles consisting of the expansion of the abbreviation.

```````````````````````````````` example(Abbreviation: 1) options(FILE_EOL)
*[Abbr]:Abbreviation
.
.
Document[0, 21]
  AbbreviationBlock[0, 20] open:[0, 2] text:[2, 6] close:[6, 8] abbreviation:[8, 20]
````````````````````````````````


Should work without trailing EOL

```````````````````````````````` example(Abbreviation: 2) options(NO_FILE_EOL)
*[Abbr]:Abbreviation
.
.
Document[0, 20]
  AbbreviationBlock[0, 20] open:[0, 2] text:[2, 6] close:[6, 8] abbreviation:[8, 20]
````````````````````````````````


```````````````````````````````` example Abbreviation: 3
*[Abbr]:Abbreviation

This has an Abbr embedded in it.
.
<p>This has an <abbr title="Abbreviation">Abbr</abbr> embedded in it.</p>
.
Document[0, 55]
  AbbreviationBlock[0, 20] open:[0, 2] text:[2, 6] close:[6, 8] abbreviation:[8, 20]
  Paragraph[22, 55]
    TextBase[22, 54] chars:[22, 54, "This  … n it."]
      Text[22, 34] chars:[22, 34, "This  … s an "]
      Abbreviation[34, 38] chars:[34, 38, "Abbr"]
      Text[38, 54] chars:[38, 54, " embe … n it."]
````````````````````````````````


No inline processing in expansion text.

```````````````````````````````` example Abbreviation: 4
*[Abbr]: Abbreviation has *emphasis*, **bold** or `code`

This has an Abbr embedded in it.
.
<p>This has an <abbr title="Abbreviation has *emphasis*, **bold** or `code`">Abbr</abbr> embedded in it.</p>
.
Document[0, 91]
  AbbreviationBlock[0, 56] open:[0, 2] text:[2, 6] close:[6, 8] abbreviation:[9, 56]
  Paragraph[58, 91]
    TextBase[58, 90] chars:[58, 90, "This  … n it."]
      Text[58, 70] chars:[58, 70, "This  … s an "]
      Abbreviation[70, 74] chars:[70, 74, "Abbr"]
      Text[74, 90] chars:[74, 90, " embe … n it."]
````````````````````````````````


```````````````````````````````` example(Abbreviation: 5) options(links)
*[Abbr]: Abbreviation has *emphasis*, **bold** or `code`

This has an Abbr embedded in it.
.
<p>This has an <a href="#" title="Abbreviation has *emphasis*, **bold** or `code`">Abbr</a> embedded in it.</p>
.
Document[0, 90]
  AbbreviationBlock[0, 56] open:[0, 2] text:[2, 6] close:[6, 8] abbreviation:[9, 56]
  Paragraph[58, 90]
    TextBase[58, 90] chars:[58, 90, "This  … n it."]
      Text[58, 70] chars:[58, 70, "This  … s an "]
      Abbreviation[70, 74] chars:[70, 74, "Abbr"]
      Text[74, 90] chars:[74, 90, " embe … n it."]
````````````````````````````````


```````````````````````````````` example Abbreviation: 6
*[Abbr]: Abbreviation 1
*[Abbre]: Abbreviation 2
.
.
Document[0, 49]
  AbbreviationBlock[0, 23] open:[0, 2] text:[2, 6] close:[6, 8] abbreviation:[9, 23]
  AbbreviationBlock[24, 48] open:[24, 26] text:[26, 31] close:[31, 33] abbreviation:[34, 48]
````````````````````````````````


```````````````````````````````` example Abbreviation: 7
*[Abbr]: Abbreviation 1
*[Abbre]: Abbreviation 2

This has an Abbre embedded in it.
And this has another Abbr embedded in it.
.
<p>This has an <abbr title="Abbreviation 2">Abbre</abbr> embedded in it.
And this has another <abbr title="Abbreviation 1">Abbr</abbr> embedded in it.</p>
.
Document[0, 126]
  AbbreviationBlock[0, 23] open:[0, 2] text:[2, 6] close:[6, 8] abbreviation:[9, 23]
  AbbreviationBlock[24, 48] open:[24, 26] text:[26, 31] close:[31, 33] abbreviation:[34, 48]
  Paragraph[50, 126]
    TextBase[50, 83] chars:[50, 83, "This  … n it."]
      Text[50, 62] chars:[50, 62, "This  … s an "]
      Abbreviation[62, 67] chars:[62, 67, "Abbre"]
      Text[67, 83] chars:[67, 83, " embe … n it."]
    SoftLineBreak[83, 84]
    TextBase[84, 125] chars:[84, 125, "And t … n it."]
      Text[84, 105] chars:[84, 105, "And t … ther "]
      Abbreviation[105, 109] chars:[105, 109, "Abbr"]
      Text[109, 125] chars:[109, 125, " embe … n it."]
````````````````````````````````


```````````````````````````````` example Abbreviation: 8
*[U.S.A.]: United States of America
*[US of A]: United States of America

U.S.A. is an abbreviation and so is US of A, an abbreviation.
.
<p><abbr title="United States of America">U.S.A.</abbr> is an abbreviation and so is <abbr title="United States of America">US of A</abbr>, an abbreviation.</p>
.
Document[0, 136]
  AbbreviationBlock[0, 35] open:[0, 2] text:[2, 8] close:[8, 10] abbreviation:[11, 35]
  AbbreviationBlock[36, 72] open:[36, 38] text:[38, 45] close:[45, 47] abbreviation:[48, 72]
  Paragraph[74, 136]
    TextBase[74, 135] chars:[74, 135, "U.S.A … tion."]
      Abbreviation[74, 80] chars:[74, 80, "U.S.A."]
      Text[80, 110] chars:[80, 110, " is a … o is "]
      Abbreviation[110, 117] chars:[110, 117, "US of A"]
      Text[117, 135] chars:[117, 135, ", an  … tion."]
````````````````````````````````


```````````````````````````````` example Abbreviation: 9
*[US]: United States
*[U.S.A.]: United States of America
*[US of A]: United States of America

U.S.A., US of A, and US are all abbreviations.
.
<p><abbr title="United States of America">U.S.A.</abbr>, <abbr title="United States of America">US of A</abbr>, and <abbr title="United States">US</abbr> are all abbreviations.</p>
.
Document[0, 142]
  AbbreviationBlock[0, 20] open:[0, 2] text:[2, 4] close:[4, 6] abbreviation:[7, 20]
  AbbreviationBlock[21, 56] open:[21, 23] text:[23, 29] close:[29, 31] abbreviation:[32, 56]
  AbbreviationBlock[57, 93] open:[57, 59] text:[59, 66] close:[66, 68] abbreviation:[69, 93]
  Paragraph[95, 142]
    TextBase[95, 141] chars:[95, 141, "U.S.A … ions."]
      Abbreviation[95, 101] chars:[95, 101, "U.S.A."]
      Text[101, 103] chars:[101, 103, ", "]
      Abbreviation[103, 110] chars:[103, 110, "US of A"]
      Text[110, 116] chars:[110, 116, ", and "]
      Abbreviation[116, 118] chars:[116, 118, "US"]
      Text[118, 141] chars:[118, 141, " are  … ions."]
````````````````````````````````


```````````````````````````````` example Abbreviation: 10
*[Abbr]: Abbreviation
[Abbr]: http://test.com

This is an Abbr and this is not [Abbr].

.
<p>This is an <abbr title="Abbreviation">Abbr</abbr> and this is not <a href="http://test.com">Abbr</a>.</p>
.
Document[0, 88]
  AbbreviationBlock[0, 21] open:[0, 2] text:[2, 6] close:[6, 8] abbreviation:[9, 21]
  Reference[22, 45] refOpen:[22, 23, "["] ref:[23, 27, "Abbr"] refClose:[27, 29, "]:"] url:[30, 45, "http://test.com"]
  Paragraph[47, 87] isTrailingBlankLine
    TextBase[47, 79] chars:[47, 79, "This  …  not "]
      Text[47, 58] chars:[47, 58, "This  … s an "]
      Abbreviation[58, 62] chars:[58, 62, "Abbr"]
      Text[62, 79] chars:[62, 79, " and  …  not "]
    LinkRef[79, 85] referenceOpen:[79, 80, "["] reference:[80, 84, "Abbr"] referenceClose:[84, 85, "]"]
      Text[80, 84] chars:[80, 84, "Abbr"]
    Text[85, 86] chars:[85, 86, "."]
````````````````````````````````


A reference that is not on the first line is just text.

```````````````````````````````` example Abbreviation: 11
Paragraph with second line having a reference
*[test]: test abbreviation

.
<p>Paragraph with second line having a reference
*[test]: test abbreviation</p>
.
Document[0, 74]
  Paragraph[0, 73] isTrailingBlankLine
    Text[0, 45] chars:[0, 45, "Parag … rence"]
    SoftLineBreak[45, 46]
    Text[46, 47] chars:[46, 47, "*"]
    LinkRef[47, 53] referenceOpen:[47, 48, "["] reference:[48, 52, "test"] referenceClose:[52, 53, "]"]
      Text[48, 52] chars:[48, 52, "test"]
    Text[53, 72] chars:[53, 72, ": tes … ation"]
````````````````````````````````


simple use case

```````````````````````````````` example Abbreviation: 12
text with abbr embedded

*[abbr]: abbreviation

.
<p>text with <abbr title="abbreviation">abbr</abbr> embedded</p>
.
Document[0, 48]
  Paragraph[0, 24] isTrailingBlankLine
    TextBase[0, 23] chars:[0, 23, "text  … edded"]
      Text[0, 10] chars:[0, 10, "text with "]
      Abbreviation[10, 14] chars:[10, 14, "abbr"]
      Text[14, 23] chars:[14, 23, " embedded"]
  AbbreviationBlock[25, 46] open:[25, 27] text:[27, 31] close:[31, 33] abbreviation:[34, 46]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
text with abbr embedded

*[abbr]: abbreviation

.
<p md-pos="0-24">text with <abbr title="abbreviation" md-pos="10-14">abbr</abbr> embedded</p>
.
Document[0, 48]
  Paragraph[0, 24] isTrailingBlankLine
    TextBase[0, 23] chars:[0, 23, "text  … edded"]
      Text[0, 10] chars:[0, 10, "text with "]
      Abbreviation[10, 14] chars:[10, 14, "abbr"]
      Text[14, 23] chars:[14, 23, " embedded"]
  AbbreviationBlock[25, 46] open:[25, 27] text:[27, 31] close:[31, 33] abbreviation:[34, 46]
````````````````````````````````


## Typographic quotes interaction 

Typographic quotes and smarts will break up text and make it not match abbreviations

```````````````````````````````` example Typographic quotes interaction: 1
Some text about HTML, SGML and HTML4.

Let's talk about the U.S.A., (É.U. or É.-U. d'A. in French).

*[HTML4]: Hyper Text Markup Language version 4
*[HTML]: Hyper Text Markup Language
*[SGML]: Standard Generalized Markup Language
*[U.S.A.]: United States of America
*[É.U.]: États-Unis d'Amérique
*[É.-U. d'A.]: États-Unis d'Amérique

And here we have a CD, some CDs, and some other CD's.

*[CD]: Compact Disk

Let's transfert documents through TCP/IP, using TCP packets.

*[IP]: Internet Protocol
*[TCP]: Transmission Control Protocol
.
<p>Some text about <abbr title="Hyper Text Markup Language">HTML</abbr>, <abbr title="Standard Generalized Markup Language">SGML</abbr> and <abbr title="Hyper Text Markup Language version 4">HTML4</abbr>.</p>
<p>Let&rsquo;s talk about the <abbr title="United States of America">U.S.A.</abbr>, (<abbr title="États-Unis d'Amérique">É.U.</abbr> or É.-U. d&rsquo;A. in French).</p>
<p>And here we have a <abbr title="Compact Disk">CD</abbr>, some CDs, and some other <abbr title="Compact Disk">CD</abbr>&rsquo;s.</p>
<p>Let&rsquo;s transfert documents through <abbr title="Transmission Control Protocol">TCP</abbr>/<abbr title="Internet Protocol">IP</abbr>, using <abbr title="Transmission Control Protocol">TCP</abbr> packets.</p>
.
Document[0, 536]
  Paragraph[0, 38] isTrailingBlankLine
    TextBase[0, 37] chars:[0, 37, "Some  … TML4."]
      Text[0, 16] chars:[0, 16, "Some  … bout "]
      Abbreviation[16, 20] chars:[16, 20, "HTML"]
      Text[20, 22] chars:[20, 22, ", "]
      Abbreviation[22, 26] chars:[22, 26, "SGML"]
      Text[26, 31] chars:[26, 31, " and "]
      Abbreviation[31, 36] chars:[31, 36, "HTML4"]
      Text[36, 37] chars:[36, 37, "."]
  Paragraph[39, 100] isTrailingBlankLine
    Text[39, 42] chars:[39, 42, "Let"]
    TypographicSmarts[42, 43] typographic: &rsquo; 
    TextBase[43, 84] chars:[43, 84, "s tal … -U. d"]
      Text[43, 60] chars:[43, 60, "s tal …  the "]
      Abbreviation[60, 66] chars:[60, 66, "U.S.A."]
      Text[66, 69] chars:[66, 69, ", ("]
      Abbreviation[69, 73] chars:[69, 73, "É.U."]
      Text[73, 84] chars:[73, 84, " or É … -U. d"]
    TypographicSmarts[84, 85] typographic: &rsquo; 
    Text[85, 99] chars:[85, 99, "A. in … nch)."]
  AbbreviationBlock[101, 147] open:[101, 103] text:[103, 108] close:[108, 110] abbreviation:[111, 147]
  AbbreviationBlock[148, 183] open:[148, 150] text:[150, 154] close:[154, 156] abbreviation:[157, 183]
  AbbreviationBlock[184, 229] open:[184, 186] text:[186, 190] close:[190, 192] abbreviation:[193, 229]
  AbbreviationBlock[230, 265] open:[230, 232] text:[232, 238] close:[238, 240] abbreviation:[241, 265]
  AbbreviationBlock[266, 296] open:[266, 268] text:[268, 272] close:[272, 274] abbreviation:[275, 296]
  AbbreviationBlock[297, 333] open:[297, 299] text:[299, 309] close:[309, 311] abbreviation:[312, 333]
  Paragraph[335, 389] isTrailingBlankLine
    TextBase[335, 385] chars:[335, 385, "And h … er CD"]
      Text[335, 354] chars:[335, 354, "And h … ve a "]
      Abbreviation[354, 356] chars:[354, 356, "CD"]
      Text[356, 383] chars:[356, 383, ", som … ther "]
      Abbreviation[383, 385] chars:[383, 385, "CD"]
    TypographicSmarts[385, 386] typographic: &rsquo; 
    Text[386, 388] chars:[386, 388, "s."]
  AbbreviationBlock[390, 409] open:[390, 392] text:[392, 394] close:[394, 396] abbreviation:[397, 409]
  Paragraph[411, 472] isTrailingBlankLine
    Text[411, 414] chars:[411, 414, "Let"]
    TypographicSmarts[414, 415] typographic: &rsquo; 
    TextBase[415, 471] chars:[415, 471, "s tra … kets."]
      Text[415, 445] chars:[415, 445, "s tra … ough "]
      Abbreviation[445, 448] chars:[445, 448, "TCP"]
      Text[448, 449] chars:[448, 449, "/"]
      Abbreviation[449, 451] chars:[449, 451, "IP"]
      Text[451, 459] chars:[451, 459, ", using "]
      Abbreviation[459, 462] chars:[459, 462, "TCP"]
      Text[462, 471] chars:[462, 471, " packets."]
  AbbreviationBlock[473, 497] open:[473, 475] text:[475, 477] close:[477, 479] abbreviation:[480, 497]
  AbbreviationBlock[498, 535] open:[498, 500] text:[500, 503] close:[503, 505] abbreviation:[506, 535]
````````````````````````````````


## Ins extension interaction

```````````````````````````````` example Ins extension interaction: 1
++TCP++
.
<p><ins>TCP</ins></p>
.
Document[0, 8]
  Paragraph[0, 8]
    Ins[0, 7] textOpen:[0, 2, "++"] text:[2, 5, "TCP"] textClose:[5, 7, "++"]
      Text[2, 5] chars:[2, 5, "TCP"]
````````````````````````````````


```````````````````````````````` example Ins extension interaction: 2
~~TCP~~
.
<p><del>TCP</del></p>
.
Document[0, 8]
  Paragraph[0, 8]
    Strikethrough[0, 7] textOpen:[0, 2, "~~"] text:[2, 5, "TCP"] textClose:[5, 7, "~~"]
      Text[2, 5] chars:[2, 5, "TCP"]
````````````````````````````````


```````````````````````````````` example Ins extension interaction: 3
++TCP++

*[TCP]: Transmission Control Protocol
.
<p><ins><abbr title="Transmission Control Protocol">TCP</abbr></ins></p>
.
Document[0, 47]
  Paragraph[0, 8] isTrailingBlankLine
    Ins[0, 7] textOpen:[0, 2, "++"] text:[2, 5, "TCP"] textClose:[5, 7, "++"]
      TextBase[2, 5] chars:[2, 5, "TCP"]
        Abbreviation[2, 5] chars:[2, 5, "TCP"]
  AbbreviationBlock[9, 46] open:[9, 11] text:[11, 14] close:[14, 16] abbreviation:[17, 46]
````````````````````````````````


```````````````````````````````` example Ins extension interaction: 4
~~TCP~~

*[TCP]: Transmission Control Protocol
.
<p><del><abbr title="Transmission Control Protocol">TCP</abbr></del></p>
.
Document[0, 47]
  Paragraph[0, 8] isTrailingBlankLine
    Strikethrough[0, 7] textOpen:[0, 2, "~~"] text:[2, 5, "TCP"] textClose:[5, 7, "~~"]
      TextBase[2, 5] chars:[2, 5, "TCP"]
        Abbreviation[2, 5] chars:[2, 5, "TCP"]
  AbbreviationBlock[9, 46] open:[9, 11] text:[11, 14] close:[14, 16] abbreviation:[17, 46]
````````````````````````````````


