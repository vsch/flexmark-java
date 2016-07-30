---
title: Toc Extension Spec
author: 
version: 
date: '2016-06-30'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Toc  

Converts `[TOC levels=#]` text to TocBlock nodes. Where levels= followed by a digit is optional
and specifies the maximum heading level to render in the table of contents. If omitted defaults
to 3.

no spaces between brackets

```````````````````````````````` example Toc: 1
[ TOC]  

[ TOC ] 

[TOC ]
.
<p>[ TOC]</p>
<p>[ TOC ]</p>
<p>[TOC ]</p>
.
Document[0, 27]
  Paragraph[0, 9]
    LinkRef[0, 6] referenceOpen:[0, 1, "["] reference:[2, 5, "TOC"] referenceClose:[5, 6, "]"]
      Text[1, 5] chars:[1, 5, " TOC"]
  Paragraph[10, 19]
    LinkRef[10, 17] referenceOpen:[10, 11, "["] reference:[12, 15, "TOC"] referenceClose:[16, 17, "]"]
      Text[11, 16] chars:[11, 16, " TOC "]
  Paragraph[20, 27]
    LinkRef[20, 26] referenceOpen:[20, 21, "["] reference:[21, 24, "TOC"] referenceClose:[25, 26, "]"]
      Text[21, 25] chars:[21, 25, "TOC "]
````````````````````````````````


Invalid level

```````````````````````````````` example Toc: 2
[TOC levels=0]  

[TOC levels=7]  

[TOC levels=8]  

[TOC levels=9]  

# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**
## Heading 1.2
## Heading 1.3
# Heading 2
### Heading 2.0.1
### Heading 2.0.2
.
<ul>
  <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
  <ul>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
  </ul>
  </li>
  <li><a href="#heading-12">Heading 1.2</a></li>
  <li><a href="#heading-13">Heading 1.3</a>
  <ul>
    <li><a href="#heading-201">Heading 2.0.1</a></li>
    <li><a href="#heading-202">Heading 2.0.2</a></li>
  </ul>
  </li>
</ul>
<ul>
  <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
  <ul>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
  </ul>
  </li>
  <li><a href="#heading-12">Heading 1.2</a></li>
  <li><a href="#heading-13">Heading 1.3</a>
  <ul>
    <li><a href="#heading-201">Heading 2.0.1</a></li>
    <li><a href="#heading-202">Heading 2.0.2</a></li>
  </ul>
  </li>
</ul>
<ul>
  <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
  <ul>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
  </ul>
  </li>
  <li><a href="#heading-12">Heading 1.2</a></li>
  <li><a href="#heading-13">Heading 1.3</a>
  <ul>
    <li><a href="#heading-201">Heading 2.0.1</a></li>
    <li><a href="#heading-202">Heading 2.0.2</a></li>
  </ul>
  </li>
</ul>
<ul>
  <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
  <ul>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
  </ul>
  </li>
  <li><a href="#heading-12">Heading 1.2</a></li>
  <li><a href="#heading-13">Heading 1.3</a>
  <ul>
    <li><a href="#heading-201">Heading 2.0.1</a></li>
    <li><a href="#heading-202">Heading 2.0.2</a></li>
  </ul>
  </li>
</ul>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
<h2 id="heading-12">Heading 1.2</h2>
<h2 id="heading-13">Heading 1.3</h2>
<h1 id="heading-2">Heading 2</h1>
<h3 id="heading-201">Heading 2.0.1</h3>
<h3 id="heading-202">Heading 2.0.2</h3>
.
Document[0, 265]
  TocBlock[0, 17] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 13] closingMarker:[13, 14]
  TocBlock[18, 35] openingMarker:[18, 19] tocKeyword:[19, 22] style:[23, 31] closingMarker:[31, 32]
  TocBlock[36, 53] openingMarker:[36, 37] tocKeyword:[37, 40] style:[41, 49] closingMarker:[49, 50]
  TocBlock[54, 71] openingMarker:[54, 55] tocKeyword:[55, 58] style:[59, 67] closingMarker:[67, 68]
  Heading[72, 97] textOpen:[72, 73, "#"] text:[74, 97, "Heading **some bold** 1"]
    Text[74, 82] chars:[74, 82, "Heading "]
    StrongEmphasis[82, 95] textOpen:[82, 84, "**"] text:[84, 93, "some bold"] textClose:[93, 95, "**"]
      Text[84, 93] chars:[84, 93, "some bold"]
    Text[95, 97] chars:[95, 97, " 1"]
  Heading[98, 126] textOpen:[98, 100, "##"] text:[101, 126, "Heading 1.1 _some italic_"]
    Text[101, 113] chars:[101, 113, "Headi …  1.1 "]
    Emphasis[113, 126] textOpen:[113, 114, "_"] text:[114, 125, "some italic"] textClose:[125, 126, "_"]
      Text[114, 125] chars:[114, 125, "some  … talic"]
  Heading[127, 144] textOpen:[127, 130, "###"] text:[131, 144, "Heading 1.1.1"]
    Text[131, 144] chars:[131, 144, "Headi … 1.1.1"]
  Heading[145, 186] textOpen:[145, 148, "###"] text:[149, 186, "Heading 1.1.2  **_some bold italic_**"]
    Text[149, 164] chars:[149, 164, "Headi … 1.2  "]
    StrongEmphasis[164, 186] textOpen:[164, 166, "**"] text:[166, 184, "_some bold italic_"] textClose:[184, 186, "**"]
      Emphasis[166, 184] textOpen:[166, 167, "_"] text:[167, 183, "some bold italic"] textClose:[183, 184, "_"]
        Text[167, 183] chars:[167, 183, "some  … talic"]
  Heading[187, 201] textOpen:[187, 189, "##"] text:[190, 201, "Heading 1.2"]
    Text[190, 201] chars:[190, 201, "Headi … g 1.2"]
  Heading[202, 216] textOpen:[202, 204, "##"] text:[205, 216, "Heading 1.3"]
    Text[205, 216] chars:[205, 216, "Headi … g 1.3"]
  Heading[217, 228] textOpen:[217, 218, "#"] text:[219, 228, "Heading 2"]
    Text[219, 228] chars:[219, 228, "Heading 2"]
  Heading[229, 246] textOpen:[229, 232, "###"] text:[233, 246, "Heading 2.0.1"]
    Text[233, 246] chars:[233, 246, "Headi … 2.0.1"]
  Heading[247, 264] textOpen:[247, 250, "###"] text:[251, 264, "Heading 2.0.2"]
    Text[251, 264] chars:[251, 264, "Headi … 2.0.2"]
````````````````````````````````


Default rendering

```````````````````````````````` example Toc: 3
[TOC]  

# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**
## Heading 1.2
## Heading 1.3
# Heading 2
### Heading 2.0.1
### Heading 2.0.2

.
<ul>
  <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
  <ul>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
  </ul>
  </li>
  <li><a href="#heading-12">Heading 1.2</a></li>
  <li><a href="#heading-13">Heading 1.3</a>
  <ul>
    <li><a href="#heading-201">Heading 2.0.1</a></li>
    <li><a href="#heading-202">Heading 2.0.2</a></li>
  </ul>
  </li>
</ul>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
<h2 id="heading-12">Heading 1.2</h2>
<h2 id="heading-13">Heading 1.3</h2>
<h1 id="heading-2">Heading 2</h1>
<h3 id="heading-201">Heading 2.0.1</h3>
<h3 id="heading-202">Heading 2.0.2</h3>
.
Document[0, 203]
  TocBlock[0, 8] openingMarker:[0, 1] tocKeyword:[1, 4] closingMarker:[4, 5]
  Heading[9, 34] textOpen:[9, 10, "#"] text:[11, 34, "Heading **some bold** 1"]
    Text[11, 19] chars:[11, 19, "Heading "]
    StrongEmphasis[19, 32] textOpen:[19, 21, "**"] text:[21, 30, "some bold"] textClose:[30, 32, "**"]
      Text[21, 30] chars:[21, 30, "some bold"]
    Text[32, 34] chars:[32, 34, " 1"]
  Heading[35, 63] textOpen:[35, 37, "##"] text:[38, 63, "Heading 1.1 _some italic_"]
    Text[38, 50] chars:[38, 50, "Headi …  1.1 "]
    Emphasis[50, 63] textOpen:[50, 51, "_"] text:[51, 62, "some italic"] textClose:[62, 63, "_"]
      Text[51, 62] chars:[51, 62, "some  … talic"]
  Heading[64, 81] textOpen:[64, 67, "###"] text:[68, 81, "Heading 1.1.1"]
    Text[68, 81] chars:[68, 81, "Headi … 1.1.1"]
  Heading[82, 123] textOpen:[82, 85, "###"] text:[86, 123, "Heading 1.1.2  **_some bold italic_**"]
    Text[86, 101] chars:[86, 101, "Headi … 1.2  "]
    StrongEmphasis[101, 123] textOpen:[101, 103, "**"] text:[103, 121, "_some bold italic_"] textClose:[121, 123, "**"]
      Emphasis[103, 121] textOpen:[103, 104, "_"] text:[104, 120, "some bold italic"] textClose:[120, 121, "_"]
        Text[104, 120] chars:[104, 120, "some  … talic"]
  Heading[124, 138] textOpen:[124, 126, "##"] text:[127, 138, "Heading 1.2"]
    Text[127, 138] chars:[127, 138, "Headi … g 1.2"]
  Heading[139, 153] textOpen:[139, 141, "##"] text:[142, 153, "Heading 1.3"]
    Text[142, 153] chars:[142, 153, "Headi … g 1.3"]
  Heading[154, 165] textOpen:[154, 155, "#"] text:[156, 165, "Heading 2"]
    Text[156, 165] chars:[156, 165, "Heading 2"]
  Heading[166, 183] textOpen:[166, 169, "###"] text:[170, 183, "Heading 2.0.1"]
    Text[170, 183] chars:[170, 183, "Headi … 2.0.1"]
  Heading[184, 201] textOpen:[184, 187, "###"] text:[188, 201, "Heading 2.0.2"]
    Text[188, 201] chars:[188, 201, "Headi … 2.0.2"]
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
  <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
  <ul>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
  </ul>
  </li>
</ul>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
.
Document[0, 124]
  TocBlock[0, 8] openingMarker:[0, 1] tocKeyword:[1, 4] closingMarker:[4, 5]
  Heading[9, 34] textOpen:[9, 10, "#"] text:[11, 34, "Heading **some bold** 1"]
    Text[11, 19] chars:[11, 19, "Heading "]
    StrongEmphasis[19, 32] textOpen:[19, 21, "**"] text:[21, 30, "some bold"] textClose:[30, 32, "**"]
      Text[21, 30] chars:[21, 30, "some bold"]
    Text[32, 34] chars:[32, 34, " 1"]
  Heading[35, 63] textOpen:[35, 37, "##"] text:[38, 63, "Heading 1.1 _some italic_"]
    Text[38, 50] chars:[38, 50, "Headi …  1.1 "]
    Emphasis[50, 63] textOpen:[50, 51, "_"] text:[51, 62, "some italic"] textClose:[62, 63, "_"]
      Text[51, 62] chars:[51, 62, "some  … talic"]
  Heading[64, 81] textOpen:[64, 67, "###"] text:[68, 81, "Heading 1.1.1"]
    Text[68, 81] chars:[68, 81, "Headi … 1.1.1"]
  Heading[82, 123] textOpen:[82, 85, "###"] text:[86, 123, "Heading 1.1.2  **_some bold italic_**"]
    Text[86, 101] chars:[86, 101, "Headi … 1.2  "]
    StrongEmphasis[101, 123] textOpen:[101, 103, "**"] text:[103, 121, "_some bold italic_"] textClose:[121, 123, "**"]
      Emphasis[103, 121] textOpen:[103, 104, "_"] text:[104, 120, "some bold italic"] textClose:[120, 121, "_"]
        Text[104, 120] chars:[104, 120, "some  … talic"]
````````````````````````````````


Set levels rendering

```````````````````````````````` example Toc: 5
[TOC levels=1]  

---

[TOC levels=2]  

# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**
## Heading 1.2
## Heading 1.3
# Heading 2
### Heading 2.0.1
### Heading 2.0.2

.
<ul>
  <li><a href="#heading-some-bold-1">Heading <strong>some bold</strong> 1</a></li>
  <li><a href="#heading-2">Heading 2</a></li>
</ul>
<hr />
<ul>
  <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a></li>
  <li><a href="#heading-12">Heading 1.2</a></li>
  <li><a href="#heading-13">Heading 1.3</a></li>
</ul>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
<h2 id="heading-12">Heading 1.2</h2>
<h2 id="heading-13">Heading 1.3</h2>
<h1 id="heading-2">Heading 2</h1>
<h3 id="heading-201">Heading 2.0.1</h3>
<h3 id="heading-202">Heading 2.0.2</h3>
.
Document[0, 235]
  TocBlock[0, 17] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 13] closingMarker:[13, 14]
  ThematicBreak[18, 21]
  TocBlock[23, 40] openingMarker:[23, 24] tocKeyword:[24, 27] style:[28, 36] closingMarker:[36, 37]
  Heading[41, 66] textOpen:[41, 42, "#"] text:[43, 66, "Heading **some bold** 1"]
    Text[43, 51] chars:[43, 51, "Heading "]
    StrongEmphasis[51, 64] textOpen:[51, 53, "**"] text:[53, 62, "some bold"] textClose:[62, 64, "**"]
      Text[53, 62] chars:[53, 62, "some bold"]
    Text[64, 66] chars:[64, 66, " 1"]
  Heading[67, 95] textOpen:[67, 69, "##"] text:[70, 95, "Heading 1.1 _some italic_"]
    Text[70, 82] chars:[70, 82, "Headi …  1.1 "]
    Emphasis[82, 95] textOpen:[82, 83, "_"] text:[83, 94, "some italic"] textClose:[94, 95, "_"]
      Text[83, 94] chars:[83, 94, "some  … talic"]
  Heading[96, 113] textOpen:[96, 99, "###"] text:[100, 113, "Heading 1.1.1"]
    Text[100, 113] chars:[100, 113, "Headi … 1.1.1"]
  Heading[114, 155] textOpen:[114, 117, "###"] text:[118, 155, "Heading 1.1.2  **_some bold italic_**"]
    Text[118, 133] chars:[118, 133, "Headi … 1.2  "]
    StrongEmphasis[133, 155] textOpen:[133, 135, "**"] text:[135, 153, "_some bold italic_"] textClose:[153, 155, "**"]
      Emphasis[135, 153] textOpen:[135, 136, "_"] text:[136, 152, "some bold italic"] textClose:[152, 153, "_"]
        Text[136, 152] chars:[136, 152, "some  … talic"]
  Heading[156, 170] textOpen:[156, 158, "##"] text:[159, 170, "Heading 1.2"]
    Text[159, 170] chars:[159, 170, "Headi … g 1.2"]
  Heading[171, 185] textOpen:[171, 173, "##"] text:[174, 185, "Heading 1.3"]
    Text[174, 185] chars:[174, 185, "Headi … g 1.3"]
  Heading[186, 197] textOpen:[186, 187, "#"] text:[188, 197, "Heading 2"]
    Text[188, 197] chars:[188, 197, "Heading 2"]
  Heading[198, 215] textOpen:[198, 201, "###"] text:[202, 215, "Heading 2.0.1"]
    Text[202, 215] chars:[202, 215, "Headi … 2.0.1"]
  Heading[216, 233] textOpen:[216, 219, "###"] text:[220, 233, "Heading 2.0.2"]
    Text[220, 233] chars:[220, 233, "Headi … 2.0.2"]
````````````````````````````````


Invalid levels take default

```````````````````````````````` example Toc: 6
[TOC levels=0]  

[TOC levels=7]  

[TOC levels=8]  

[TOC levels=9]  

# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**
## Heading 1.2
## Heading 1.3
# Heading 2
### Heading 2.0.1
### Heading 2.0.2
.
<ul>
  <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
  <ul>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
  </ul>
  </li>
  <li><a href="#heading-12">Heading 1.2</a></li>
  <li><a href="#heading-13">Heading 1.3</a>
  <ul>
    <li><a href="#heading-201">Heading 2.0.1</a></li>
    <li><a href="#heading-202">Heading 2.0.2</a></li>
  </ul>
  </li>
</ul>
<ul>
  <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
  <ul>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
  </ul>
  </li>
  <li><a href="#heading-12">Heading 1.2</a></li>
  <li><a href="#heading-13">Heading 1.3</a>
  <ul>
    <li><a href="#heading-201">Heading 2.0.1</a></li>
    <li><a href="#heading-202">Heading 2.0.2</a></li>
  </ul>
  </li>
</ul>
<ul>
  <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
  <ul>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
  </ul>
  </li>
  <li><a href="#heading-12">Heading 1.2</a></li>
  <li><a href="#heading-13">Heading 1.3</a>
  <ul>
    <li><a href="#heading-201">Heading 2.0.1</a></li>
    <li><a href="#heading-202">Heading 2.0.2</a></li>
  </ul>
  </li>
</ul>
<ul>
  <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
  <ul>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
  </ul>
  </li>
  <li><a href="#heading-12">Heading 1.2</a></li>
  <li><a href="#heading-13">Heading 1.3</a>
  <ul>
    <li><a href="#heading-201">Heading 2.0.1</a></li>
    <li><a href="#heading-202">Heading 2.0.2</a></li>
  </ul>
  </li>
</ul>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
<h2 id="heading-12">Heading 1.2</h2>
<h2 id="heading-13">Heading 1.3</h2>
<h1 id="heading-2">Heading 2</h1>
<h3 id="heading-201">Heading 2.0.1</h3>
<h3 id="heading-202">Heading 2.0.2</h3>
.
Document[0, 265]
  TocBlock[0, 17] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 13] closingMarker:[13, 14]
  TocBlock[18, 35] openingMarker:[18, 19] tocKeyword:[19, 22] style:[23, 31] closingMarker:[31, 32]
  TocBlock[36, 53] openingMarker:[36, 37] tocKeyword:[37, 40] style:[41, 49] closingMarker:[49, 50]
  TocBlock[54, 71] openingMarker:[54, 55] tocKeyword:[55, 58] style:[59, 67] closingMarker:[67, 68]
  Heading[72, 97] textOpen:[72, 73, "#"] text:[74, 97, "Heading **some bold** 1"]
    Text[74, 82] chars:[74, 82, "Heading "]
    StrongEmphasis[82, 95] textOpen:[82, 84, "**"] text:[84, 93, "some bold"] textClose:[93, 95, "**"]
      Text[84, 93] chars:[84, 93, "some bold"]
    Text[95, 97] chars:[95, 97, " 1"]
  Heading[98, 126] textOpen:[98, 100, "##"] text:[101, 126, "Heading 1.1 _some italic_"]
    Text[101, 113] chars:[101, 113, "Headi …  1.1 "]
    Emphasis[113, 126] textOpen:[113, 114, "_"] text:[114, 125, "some italic"] textClose:[125, 126, "_"]
      Text[114, 125] chars:[114, 125, "some  … talic"]
  Heading[127, 144] textOpen:[127, 130, "###"] text:[131, 144, "Heading 1.1.1"]
    Text[131, 144] chars:[131, 144, "Headi … 1.1.1"]
  Heading[145, 186] textOpen:[145, 148, "###"] text:[149, 186, "Heading 1.1.2  **_some bold italic_**"]
    Text[149, 164] chars:[149, 164, "Headi … 1.2  "]
    StrongEmphasis[164, 186] textOpen:[164, 166, "**"] text:[166, 184, "_some bold italic_"] textClose:[184, 186, "**"]
      Emphasis[166, 184] textOpen:[166, 167, "_"] text:[167, 183, "some bold italic"] textClose:[183, 184, "_"]
        Text[167, 183] chars:[167, 183, "some  … talic"]
  Heading[187, 201] textOpen:[187, 189, "##"] text:[190, 201, "Heading 1.2"]
    Text[190, 201] chars:[190, 201, "Headi … g 1.2"]
  Heading[202, 216] textOpen:[202, 204, "##"] text:[205, 216, "Heading 1.3"]
    Text[205, 216] chars:[205, 216, "Headi … g 1.3"]
  Heading[217, 228] textOpen:[217, 218, "#"] text:[219, 228, "Heading 2"]
    Text[219, 228] chars:[219, 228, "Heading 2"]
  Heading[229, 246] textOpen:[229, 232, "###"] text:[233, 246, "Heading 2.0.1"]
    Text[233, 246] chars:[233, 246, "Headi … 2.0.1"]
  Heading[247, 264] textOpen:[247, 250, "###"] text:[251, 264, "Heading 2.0.2"]
    Text[251, 264] chars:[251, 264, "Headi … 2.0.2"]
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
  <li><a href="#heading-11-some-italic">Heading 1.1 some italic</a>
  <ul>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  some bold italic</a></li>
  </ul>
  </li>
</ul>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
.
Document[0, 125]
  TocBlock[0, 8] openingMarker:[0, 1] tocKeyword:[1, 4] closingMarker:[4, 5]
  Heading[9, 34] textOpen:[9, 10, "#"] text:[11, 34, "Heading **some bold** 1"]
    Text[11, 19] chars:[11, 19, "Heading "]
    StrongEmphasis[19, 32] textOpen:[19, 21, "**"] text:[21, 30, "some bold"] textClose:[30, 32, "**"]
      Text[21, 30] chars:[21, 30, "some bold"]
    Text[32, 34] chars:[32, 34, " 1"]
  Heading[35, 63] textOpen:[35, 37, "##"] text:[38, 63, "Heading 1.1 _some italic_"]
    Text[38, 50] chars:[38, 50, "Headi …  1.1 "]
    Emphasis[50, 63] textOpen:[50, 51, "_"] text:[51, 62, "some italic"] textClose:[62, 63, "_"]
      Text[51, 62] chars:[51, 62, "some  … talic"]
  Heading[64, 81] textOpen:[64, 67, "###"] text:[68, 81, "Heading 1.1.1"]
    Text[68, 81] chars:[68, 81, "Headi … 1.1.1"]
  Heading[82, 123] textOpen:[82, 85, "###"] text:[86, 123, "Heading 1.1.2  **_some bold italic_**"]
    Text[86, 101] chars:[86, 101, "Headi … 1.2  "]
    StrongEmphasis[101, 123] textOpen:[101, 103, "**"] text:[103, 121, "_some bold italic_"] textClose:[121, 123, "**"]
      Emphasis[103, 121] textOpen:[103, 104, "_"] text:[104, 120, "some bold italic"] textClose:[120, 121, "_"]
        Text[104, 120] chars:[104, 120, "some  … talic"]
````````````````````````````````


Text only style

```````````````````````````````` example Toc: 8
[TOC text]  

# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**

.
<ul>
  <li><a href="#heading-11-some-italic">Heading 1.1 some italic</a>
  <ul>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  some bold italic</a></li>
  </ul>
  </li>
</ul>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
.
Document[0, 130]
  TocBlock[0, 13] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 9] closingMarker:[9, 10]
  Heading[14, 39] textOpen:[14, 15, "#"] text:[16, 39, "Heading **some bold** 1"]
    Text[16, 24] chars:[16, 24, "Heading "]
    StrongEmphasis[24, 37] textOpen:[24, 26, "**"] text:[26, 35, "some bold"] textClose:[35, 37, "**"]
      Text[26, 35] chars:[26, 35, "some bold"]
    Text[37, 39] chars:[37, 39, " 1"]
  Heading[40, 68] textOpen:[40, 42, "##"] text:[43, 68, "Heading 1.1 _some italic_"]
    Text[43, 55] chars:[43, 55, "Headi …  1.1 "]
    Emphasis[55, 68] textOpen:[55, 56, "_"] text:[56, 67, "some italic"] textClose:[67, 68, "_"]
      Text[56, 67] chars:[56, 67, "some  … talic"]
  Heading[69, 86] textOpen:[69, 72, "###"] text:[73, 86, "Heading 1.1.1"]
    Text[73, 86] chars:[73, 86, "Headi … 1.1.1"]
  Heading[87, 128] textOpen:[87, 90, "###"] text:[91, 128, "Heading 1.1.2  **_some bold italic_**"]
    Text[91, 106] chars:[91, 106, "Headi … 1.2  "]
    StrongEmphasis[106, 128] textOpen:[106, 108, "**"] text:[108, 126, "_some bold italic_"] textClose:[126, 128, "**"]
      Emphasis[108, 126] textOpen:[108, 109, "_"] text:[109, 125, "some bold italic"] textClose:[125, 126, "_"]
        Text[109, 125] chars:[109, 125, "some  … talic"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
## Header 2
### Header 3

[TOC levels=3]
.
<h2 id="header-2" md-pos="3-11">Header 2</h2>
<h3 id="header-3" md-pos="16-24">Header 3</h3>
<div md-pos="26-40">
  <h1></h1>
  <ul>
    <li><a href="#header-3">Header 3</a></li>
  </ul>
</div>
.
Document[0, 41]
  Heading[0, 11] textOpen:[0, 2, "##"] text:[3, 11, "Header 2"]
    Text[3, 11] chars:[3, 11, "Header 2"]
  Heading[12, 24] textOpen:[12, 15, "###"] text:[16, 24, "Header 3"]
    Text[16, 24] chars:[16, 24, "Header 3"]
  TocBlock[26, 41] openingMarker:[26, 27] tocKeyword:[27, 30] style:[31, 39] closingMarker:[39, 40]
````````````````````````````````


