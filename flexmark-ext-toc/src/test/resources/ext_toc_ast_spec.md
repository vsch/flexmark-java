---
title: Toc Extension Spec
author: 
version: 
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Toc  

Converts `[TOC level=#]` text to TocBlock nodes. Where level= followed by a digit is optional
and specifies the maximum heading level to render in the table of contents. If omitted defaults
to 3. 

no spaces between brackets

```````````````````````````````` example Toc: 1
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

```````````````````````````````` example Toc: 2
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
<h1>Heading 1</h1>
<h2>Heading 1.1</h2>
<h3>Heading 1.1.1</h3>
<h3>Heading 1.1.2</h3>
<h2>Heading 1.2</h2>
<h2>Heading 1.3</h2>
<h1>Heading 2</h1>
<h3>Heading 2.0.1</h3>
<h3>Heading 2.0.2</h3>
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

```````````````````````````````` example Toc: 3
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
<h1>Heading 1</h1>
<h2>Heading 1.1</h2>
<h3>Heading 1.1.1</h3>
<h3>Heading 1.1.2</h3>
<h2>Heading 1.2</h2>
<h2>Heading 1.3</h2>
<h1>Heading 2</h1>
<h3>Heading 2.0.1</h3>
<h3>Heading 2.0.2</h3>
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


Set levels rendering

```````````````````````````````` example Toc: 4
[TOC level=1]  

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
<h1>Heading 1</h1>
<h2>Heading 1.1</h2>
<h3>Heading 1.1.1</h3>
<h3>Heading 1.1.2</h3>
<h2>Heading 1.2</h2>
<h2>Heading 1.3</h2>
<h1>Heading 2</h1>
<h3>Heading 2.0.1</h3>
<h3>Heading 2.0.2</h3>
.
Document[0, 176]
  TocBlock[0, 13] open:[0, 4] levelMarker:[5, 11] level:[11, 12] close:[12, 13]
  TocBlock[17, 30] open:[17, 21] levelMarker:[22, 28] level:[28, 29] close:[29, 30]
  Heading[34, 45] textOpen:[34, 35, "#"] text:[36, 45, "Heading 1"] textClose:[0, 0]
    Text[36, 45] chars:[36, 45, "Heading 1"]
  Heading[46, 60] textOpen:[46, 48, "##"] text:[49, 60, "Heading 1.1"] textClose:[0, 0]
    Text[49, 60] chars:[49, 60, "Headi"..."g 1.1"]
  Heading[61, 78] textOpen:[61, 64, "###"] text:[65, 78, "Heading 1.1.1"] textClose:[0, 0]
    Text[65, 78] chars:[65, 78, "Headi"..."1.1.1"]
  Heading[79, 96] textOpen:[79, 82, "###"] text:[83, 96, "Heading 1.1.2"] textClose:[0, 0]
    Text[83, 96] chars:[83, 96, "Headi"..."1.1.2"]
  Heading[97, 111] textOpen:[97, 99, "##"] text:[100, 111, "Heading 1.2"] textClose:[0, 0]
    Text[100, 111] chars:[100, 111, "Headi"..."g 1.2"]
  Heading[112, 126] textOpen:[112, 114, "##"] text:[115, 126, "Heading 1.3"] textClose:[0, 0]
    Text[115, 126] chars:[115, 126, "Headi"..."g 1.3"]
  Heading[127, 138] textOpen:[127, 128, "#"] text:[129, 138, "Heading 2"] textClose:[0, 0]
    Text[129, 138] chars:[129, 138, "Heading 2"]
  Heading[139, 156] textOpen:[139, 142, "###"] text:[143, 156, "Heading 2.0.1"] textClose:[0, 0]
    Text[143, 156] chars:[143, 156, "Headi"..."2.0.1"]
  Heading[157, 174] textOpen:[157, 160, "###"] text:[161, 174, "Heading 2.0.2"] textClose:[0, 0]
    Text[161, 174] chars:[161, 174, "Headi"..."2.0.2"]
````````````````````````````````


