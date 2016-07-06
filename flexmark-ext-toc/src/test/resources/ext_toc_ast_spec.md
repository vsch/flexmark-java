---
title: Toc Extension Spec
author: 
version: 
date: '2016-06-30'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Toc  

Converts `[TOC level=#]` text to TocBlock nodes. Where level= followed
by a digit is optional and specifies the maximum heading level to render
in the table of contents. If omitted defaults to 3.

no spaces between brackets

```````````````````````````````` example Toc: 1
[ TOC]  

[ TOC ] 

[ TOC ]
.
<p>[ TOC]</p>
<p>[ TOC ]</p>
<p>[ TOC ]</p>
.
Document[0, 28]
  Paragraph[0, 9]
    LinkRef[0, 6] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[0, 1, "["] reference:[2, 5, "TOC"] referenceClose:[5, 6, "]"]
      Text[1, 5] chars:[1, 5, " TOC"]
  Paragraph[10, 19]
    LinkRef[10, 17] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[10, 11, "["] reference:[12, 15, "TOC"] referenceClose:[16, 17, "]"]
      Text[11, 16] chars:[11, 16, " TOC "]
  Paragraph[20, 28]
    LinkRef[20, 27] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[20, 21, "["] reference:[22, 25, "TOC"] referenceClose:[26, 27, "]"]
      Text[21, 26] chars:[21, 26, " TOC "]
````````````````````````````````


Invalid level

```````````````````````````````` example Toc: 2
[TOC level=0]  

[TOC level=7]  

[TOC level=8]  

[TOC level=9]  

# Heading 1
## Heading 1.1
### Heading 1.1.1
### Heading 1.1.2
## Heading 1.2
## Heading 1.3
# Heading 2
### Heading 2.0.1
### Heading 2.0.2
.
<p>[TOC level=0]</p>
<p>[TOC level=7]</p>
<p>[TOC level=8]</p>
<p>[TOC level=9]</p>
<h1 id="heading-1">Heading 1</h1>
<h2 id="heading-11">Heading 1.1</h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112">Heading 1.1.2</h3>
<h2 id="heading-12">Heading 1.2</h2>
<h2 id="heading-13">Heading 1.3</h2>
<h1 id="heading-2">Heading 2</h1>
<h3 id="heading-201">Heading 2.0.1</h3>
<h3 id="heading-202">Heading 2.0.2</h3>
.
Document[0, 209]
  TocBlock[0, 13] open:[0, 4] levelMarker:[5, 11] level:[11, 12] close:[12, 13]
  TocBlock[17, 30] open:[17, 21] levelMarker:[22, 28] level:[28, 29] close:[29, 30]
  TocBlock[34, 47] open:[34, 38] levelMarker:[39, 45] level:[45, 46] close:[46, 47]
  TocBlock[51, 64] open:[51, 55] levelMarker:[56, 62] level:[62, 63] close:[63, 64]
  Heading[68, 79] textOpen:[68, 69, "#"] text:[70, 79, "Heading 1"] textClose:[0, 0]
    Text[70, 79] chars:[70, 79, "Heading 1"]
  Heading[80, 94] textOpen:[80, 82, "##"] text:[83, 94, "Heading 1.1"] textClose:[0, 0]
    Text[83, 94] chars:[83, 94, "Headi"..."g 1.1"]
  Heading[95, 112] textOpen:[95, 98, "###"] text:[99, 112, "Heading 1.1.1"] textClose:[0, 0]
    Text[99, 112] chars:[99, 112, "Headi"..."1.1.1"]
  Heading[113, 130] textOpen:[113, 116, "###"] text:[117, 130, "Heading 1.1.2"] textClose:[0, 0]
    Text[117, 130] chars:[117, 130, "Headi"..."1.1.2"]
  Heading[131, 145] textOpen:[131, 133, "##"] text:[134, 145, "Heading 1.2"] textClose:[0, 0]
    Text[134, 145] chars:[134, 145, "Headi"..."g 1.2"]
  Heading[146, 160] textOpen:[146, 148, "##"] text:[149, 160, "Heading 1.3"] textClose:[0, 0]
    Text[149, 160] chars:[149, 160, "Headi"..."g 1.3"]
  Heading[161, 172] textOpen:[161, 162, "#"] text:[163, 172, "Heading 2"] textClose:[0, 0]
    Text[163, 172] chars:[163, 172, "Heading 2"]
  Heading[173, 190] textOpen:[173, 176, "###"] text:[177, 190, "Heading 2.0.1"] textClose:[0, 0]
    Text[177, 190] chars:[177, 190, "Headi"..."2.0.1"]
  Heading[191, 208] textOpen:[191, 194, "###"] text:[195, 208, "Heading 2.0.2"] textClose:[0, 0]
    Text[195, 208] chars:[195, 208, "Headi"..."2.0.2"]
````````````````````````````````


Default rendering

```````````````````````````````` example Toc: 3
[TOC]  

# Heading 1
## Heading 1.1
### Heading 1.1.1
### Heading 1.1.2
## Heading 1.2
## Heading 1.3
# Heading 2
### Heading 2.0.1
### Heading 2.0.2

.
<ul>
  <li><a href="#heading-1">Heading 1</a>
  <ul>
    <li><a href="#heading-11">Heading 1.1</a>
    <ul>
      <li><a href="#heading-111">Heading 1.1.1</a></li>
      <li><a href="#heading-112">Heading 1.1.2</a></li>
    </ul></li>
    <li><a href="#heading-12">Heading 1.2</a></li>
    <li><a href="#heading-13">Heading 1.3</a></li>
  </ul></li>
  <li><a href="#heading-2">Heading 2</a>
  <ul>
    <ul>
      <li><a href="#heading-201">Heading 2.0.1</a></li>
      <li><a href="#heading-202">Heading 2.0.2</a></li>
    </ul>
  </ul></li>
</ul>
<h1 id="heading-1">Heading 1</h1>
<h2 id="heading-11">Heading 1.1</h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112">Heading 1.1.2</h3>
<h2 id="heading-12">Heading 1.2</h2>
<h2 id="heading-13">Heading 1.3</h2>
<h1 id="heading-2">Heading 2</h1>
<h3 id="heading-201">Heading 2.0.1</h3>
<h3 id="heading-202">Heading 2.0.2</h3>
.
Document[0, 151]
  TocBlock[0, 5] open:[0, 4] levelMarker:[0, 0] level:[0, 0] close:[4, 5]
  Heading[9, 20] textOpen:[9, 10, "#"] text:[11, 20, "Heading 1"] textClose:[0, 0]
    Text[11, 20] chars:[11, 20, "Heading 1"]
  Heading[21, 35] textOpen:[21, 23, "##"] text:[24, 35, "Heading 1.1"] textClose:[0, 0]
    Text[24, 35] chars:[24, 35, "Headi"..."g 1.1"]
  Heading[36, 53] textOpen:[36, 39, "###"] text:[40, 53, "Heading 1.1.1"] textClose:[0, 0]
    Text[40, 53] chars:[40, 53, "Headi"..."1.1.1"]
  Heading[54, 71] textOpen:[54, 57, "###"] text:[58, 71, "Heading 1.1.2"] textClose:[0, 0]
    Text[58, 71] chars:[58, 71, "Headi"..."1.1.2"]
  Heading[72, 86] textOpen:[72, 74, "##"] text:[75, 86, "Heading 1.2"] textClose:[0, 0]
    Text[75, 86] chars:[75, 86, "Headi"..."g 1.2"]
  Heading[87, 101] textOpen:[87, 89, "##"] text:[90, 101, "Heading 1.3"] textClose:[0, 0]
    Text[90, 101] chars:[90, 101, "Headi"..."g 1.3"]
  Heading[102, 113] textOpen:[102, 103, "#"] text:[104, 113, "Heading 2"] textClose:[0, 0]
    Text[104, 113] chars:[104, 113, "Heading 2"]
  Heading[114, 131] textOpen:[114, 117, "###"] text:[118, 131, "Heading 2.0.1"] textClose:[0, 0]
    Text[118, 131] chars:[118, 131, "Headi"..."2.0.1"]
  Heading[132, 149] textOpen:[132, 135, "###"] text:[136, 149, "Heading 2.0.2"] textClose:[0, 0]
    Text[136, 149] chars:[136, 149, "Headi"..."2.0.2"]
````````````````````````````````


Default rendering with emphasis

```````````````````````````````` example Toc: 4
[TOC]  

# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**

.
<ul>
  <li><a href="#heading-some-bold-1">Heading <strong>some bold</strong> 1</a>
  <ul>
    <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
    <ul>
      <li><a href="#heading-111">Heading 1.1.1</a></li>
      <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
    </ul></li>
  </ul></li>
</ul>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
.
Document[0, 125]
  TocBlock[0, 5] open:[0, 4] levelMarker:[0, 0] level:[0, 0] close:[4, 5]
  Heading[9, 34] textOpen:[9, 10, "#"] text:[11, 34, "Heading **some bold** 1"] textClose:[0, 0]
    Text[11, 19] chars:[11, 19, "Heading "]
    StrongEmphasis[19, 32] textOpen:[19, 21, "**"] text:[21, 30, "some bold"] textClose:[30, 32, "**"]
      Text[21, 30] chars:[21, 30, "some bold"]
    Text[32, 34] chars:[32, 34, " 1"]
  Heading[35, 63] textOpen:[35, 37, "##"] text:[38, 63, "Heading 1.1 _some italic_"] textClose:[0, 0]
    Text[38, 50] chars:[38, 50, "Headi"..." 1.1 "]
    Emphasis[50, 63] textOpen:[50, 51, "_"] text:[51, 62, "some italic"] textClose:[62, 63, "_"]
      Text[51, 62] chars:[51, 62, "some "..."talic"]
  Heading[64, 81] textOpen:[64, 67, "###"] text:[68, 81, "Heading 1.1.1"] textClose:[0, 0]
    Text[68, 81] chars:[68, 81, "Headi"..."1.1.1"]
  Heading[82, 123] textOpen:[82, 85, "###"] text:[86, 123, "Heading 1.1.2  **_some bold italic_**"] textClose:[0, 0]
    Text[86, 101] chars:[86, 101, "Headi"..."1.2  "]
    StrongEmphasis[101, 123] textOpen:[101, 103, "**"] text:[103, 121, "_some bold italic_"] textClose:[121, 123, "**"]
      Emphasis[103, 121] textOpen:[103, 104, "_"] text:[104, 120, "some bold italic"] textClose:[120, 121, "_"]
        Text[104, 120] chars:[104, 120, "some "..."talic"]
````````````````````````````````


Set levels rendering

```````````````````````````````` example Toc: 5
[TOC level=1]  

---

[TOC level=2]  

# Heading 1
## Heading 1.1
### Heading 1.1.1
### Heading 1.1.2
## Heading 1.2
## Heading 1.3
# Heading 2
### Heading 2.0.1
### Heading 2.0.2

.
<ul>
  <li><a href="#heading-1">Heading 1</a></li>
  <li><a href="#heading-2">Heading 2</a></li>
</ul>
<hr />
<ul>
  <li><a href="#heading-1">Heading 1</a>
  <ul>
    <li><a href="#heading-11">Heading 1.1</a></li>
    <li><a href="#heading-12">Heading 1.2</a></li>
    <li><a href="#heading-13">Heading 1.3</a></li>
  </ul></li>
  <li><a href="#heading-2">Heading 2</a></li>
</ul>
<h1 id="heading-1">Heading 1</h1>
<h2 id="heading-11">Heading 1.1</h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112">Heading 1.1.2</h3>
<h2 id="heading-12">Heading 1.2</h2>
<h2 id="heading-13">Heading 1.3</h2>
<h1 id="heading-2">Heading 2</h1>
<h3 id="heading-201">Heading 2.0.1</h3>
<h3 id="heading-202">Heading 2.0.2</h3>
.
Document[0, 181]
  TocBlock[0, 13] open:[0, 4] levelMarker:[5, 11] level:[11, 12] close:[12, 13]
  ThematicBreak[17, 20]
  TocBlock[22, 35] open:[22, 26] levelMarker:[27, 33] level:[33, 34] close:[34, 35]
  Heading[39, 50] textOpen:[39, 40, "#"] text:[41, 50, "Heading 1"] textClose:[0, 0]
    Text[41, 50] chars:[41, 50, "Heading 1"]
  Heading[51, 65] textOpen:[51, 53, "##"] text:[54, 65, "Heading 1.1"] textClose:[0, 0]
    Text[54, 65] chars:[54, 65, "Headi"..."g 1.1"]
  Heading[66, 83] textOpen:[66, 69, "###"] text:[70, 83, "Heading 1.1.1"] textClose:[0, 0]
    Text[70, 83] chars:[70, 83, "Headi"..."1.1.1"]
  Heading[84, 101] textOpen:[84, 87, "###"] text:[88, 101, "Heading 1.1.2"] textClose:[0, 0]
    Text[88, 101] chars:[88, 101, "Headi"..."1.1.2"]
  Heading[102, 116] textOpen:[102, 104, "##"] text:[105, 116, "Heading 1.2"] textClose:[0, 0]
    Text[105, 116] chars:[105, 116, "Headi"..."g 1.2"]
  Heading[117, 131] textOpen:[117, 119, "##"] text:[120, 131, "Heading 1.3"] textClose:[0, 0]
    Text[120, 131] chars:[120, 131, "Headi"..."g 1.3"]
  Heading[132, 143] textOpen:[132, 133, "#"] text:[134, 143, "Heading 2"] textClose:[0, 0]
    Text[134, 143] chars:[134, 143, "Heading 2"]
  Heading[144, 161] textOpen:[144, 147, "###"] text:[148, 161, "Heading 2.0.1"] textClose:[0, 0]
    Text[148, 161] chars:[148, 161, "Headi"..."2.0.1"]
  Heading[162, 179] textOpen:[162, 165, "###"] text:[166, 179, "Heading 2.0.2"] textClose:[0, 0]
    Text[166, 179] chars:[166, 179, "Headi"..."2.0.2"]
````````````````````````````````


Invalid level

```````````````````````````````` example(Toc: 6) options(only-valid)
[TOC level=0]  

[TOC level=7]  

[TOC level=8]  

[TOC level=9]  

# Heading 1
## Heading 1.1
### Heading 1.1.1
### Heading 1.1.2
## Heading 1.2
## Heading 1.3
# Heading 2
### Heading 2.0.1
### Heading 2.0.2
.
<p>[TOC level=0]</p>
<p>[TOC level=7]</p>
<p>[TOC level=8]</p>
<p>[TOC level=9]</p>
<h1 id="heading-1">Heading 1</h1>
<h2 id="heading-11">Heading 1.1</h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112">Heading 1.1.2</h3>
<h2 id="heading-12">Heading 1.2</h2>
<h2 id="heading-13">Heading 1.3</h2>
<h1 id="heading-2">Heading 2</h1>
<h3 id="heading-201">Heading 2.0.1</h3>
<h3 id="heading-202">Heading 2.0.2</h3>
.
Document[0, 209]
  Paragraph[0, 16]
    LinkRef[0, 13] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[0, 1, "["] reference:[1, 12, "TOC level=0"] referenceClose:[12, 13, "]"]
      Text[1, 12] chars:[1, 12, "TOC l"..."vel=0"]
  Paragraph[17, 33]
    LinkRef[17, 30] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[17, 18, "["] reference:[18, 29, "TOC level=7"] referenceClose:[29, 30, "]"]
      Text[18, 29] chars:[18, 29, "TOC l"..."vel=7"]
  Paragraph[34, 50]
    LinkRef[34, 47] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[34, 35, "["] reference:[35, 46, "TOC level=8"] referenceClose:[46, 47, "]"]
      Text[35, 46] chars:[35, 46, "TOC l"..."vel=8"]
  Paragraph[51, 67]
    LinkRef[51, 64] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[51, 52, "["] reference:[52, 63, "TOC level=9"] referenceClose:[63, 64, "]"]
      Text[52, 63] chars:[52, 63, "TOC l"..."vel=9"]
  Heading[68, 79] textOpen:[68, 69, "#"] text:[70, 79, "Heading 1"] textClose:[0, 0]
    Text[70, 79] chars:[70, 79, "Heading 1"]
  Heading[80, 94] textOpen:[80, 82, "##"] text:[83, 94, "Heading 1.1"] textClose:[0, 0]
    Text[83, 94] chars:[83, 94, "Headi"..."g 1.1"]
  Heading[95, 112] textOpen:[95, 98, "###"] text:[99, 112, "Heading 1.1.1"] textClose:[0, 0]
    Text[99, 112] chars:[99, 112, "Headi"..."1.1.1"]
  Heading[113, 130] textOpen:[113, 116, "###"] text:[117, 130, "Heading 1.1.2"] textClose:[0, 0]
    Text[117, 130] chars:[117, 130, "Headi"..."1.1.2"]
  Heading[131, 145] textOpen:[131, 133, "##"] text:[134, 145, "Heading 1.2"] textClose:[0, 0]
    Text[134, 145] chars:[134, 145, "Headi"..."g 1.2"]
  Heading[146, 160] textOpen:[146, 148, "##"] text:[149, 160, "Heading 1.3"] textClose:[0, 0]
    Text[149, 160] chars:[149, 160, "Headi"..."g 1.3"]
  Heading[161, 172] textOpen:[161, 162, "#"] text:[163, 172, "Heading 2"] textClose:[0, 0]
    Text[163, 172] chars:[163, 172, "Heading 2"]
  Heading[173, 190] textOpen:[173, 176, "###"] text:[177, 190, "Heading 2.0.1"] textClose:[0, 0]
    Text[177, 190] chars:[177, 190, "Headi"..."2.0.1"]
  Heading[191, 208] textOpen:[191, 194, "###"] text:[195, 208, "Heading 2.0.2"] textClose:[0, 0]
    Text[195, 208] chars:[195, 208, "Headi"..."2.0.2"]
````````````````````````````````


Text only rendering

```````````````````````````````` example(Toc: 7) options(text-only)
[TOC]  

# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**

.
<ul>
  <li><a href="#heading-some-bold-1">Heading some bold 1</a>
  <ul>
    <li><a href="#heading-11-some-italic">Heading 1.1 some italic</a>
    <ul>
      <li><a href="#heading-111">Heading 1.1.1</a></li>
      <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  some bold italic</a></li>
    </ul></li>
  </ul></li>
</ul>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
.
Document[0, 125]
  TocBlock[0, 5] open:[0, 4] levelMarker:[0, 0] level:[0, 0] close:[4, 5]
  Heading[9, 34] textOpen:[9, 10, "#"] text:[11, 34, "Heading **some bold** 1"] textClose:[0, 0]
    Text[11, 19] chars:[11, 19, "Heading "]
    StrongEmphasis[19, 32] textOpen:[19, 21, "**"] text:[21, 30, "some bold"] textClose:[30, 32, "**"]
      Text[21, 30] chars:[21, 30, "some bold"]
    Text[32, 34] chars:[32, 34, " 1"]
  Heading[35, 63] textOpen:[35, 37, "##"] text:[38, 63, "Heading 1.1 _some italic_"] textClose:[0, 0]
    Text[38, 50] chars:[38, 50, "Headi"..." 1.1 "]
    Emphasis[50, 63] textOpen:[50, 51, "_"] text:[51, 62, "some italic"] textClose:[62, 63, "_"]
      Text[51, 62] chars:[51, 62, "some "..."talic"]
  Heading[64, 81] textOpen:[64, 67, "###"] text:[68, 81, "Heading 1.1.1"] textClose:[0, 0]
    Text[68, 81] chars:[68, 81, "Headi"..."1.1.1"]
  Heading[82, 123] textOpen:[82, 85, "###"] text:[86, 123, "Heading 1.1.2  **_some bold italic_**"] textClose:[0, 0]
    Text[86, 101] chars:[86, 101, "Headi"..."1.2  "]
    StrongEmphasis[101, 123] textOpen:[101, 103, "**"] text:[103, 121, "_some bold italic_"] textClose:[121, 123, "**"]
      Emphasis[103, 121] textOpen:[103, 104, "_"] text:[104, 120, "some bold italic"] textClose:[120, 121, "_"]
        Text[104, 120] chars:[104, 120, "some "..."talic"]
````````````````````````````````


