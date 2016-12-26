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

```````````````````````````````` example Abbreviation: 1
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
Document[0, 91]
  AbbreviationBlock[0, 56] open:[0, 2] text:[2, 6] close:[6, 8] abbreviation:[9, 56]
  Paragraph[58, 91]
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
  Paragraph[47, 87]
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
  Paragraph[0, 73]
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
  Paragraph[0, 24]
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
  Paragraph[0, 24]
    TextBase[0, 23] chars:[0, 23, "text  … edded"]
      Text[0, 10] chars:[0, 10, "text with "]
      Abbreviation[10, 14] chars:[10, 14, "abbr"]
      Text[14, 23] chars:[14, 23, " embedded"]
  AbbreviationBlock[25, 46] open:[25, 27] text:[27, 31] close:[31, 33] abbreviation:[34, 46]
````````````````````````````````


