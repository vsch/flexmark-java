---
title: Tables Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

# Tables Extension

## Basic

Converts pipe separated tables to html tables with optional column spans and multiple header
lines and table caption.

```````````````````````````````` example Basic: 1
-------|-------------
.
<table>
  <thead></thead>
  <tbody></tbody>
</table>
.
Document[0, 21]
  TableBlock[0, 21]
    TableHead[0, 0]
    TableSeparator[0, 21]
      TableRow[0, 21]
        TableCell[0, 8] text:[0, 7, "-------"] textClose:[7, 8, "|"]
          Text[0, 7] chars:[0, 7, "-------"]
        TableCell[8, 21] text:[8, 21, "-------------"]
          Text[8, 21] chars:[8, 21, "----- … -----"]
    TableBody[21, 21]
````````````````````````````````


```````````````````````````````` example Basic: 2
Abc|Def
.
<p>Abc|Def</p>
.
Document[0, 7]
  Paragraph[0, 7]
    Text[0, 7] chars:[0, 7, "Abc|Def"]
````````````````````````````````


```````````````````````````````` example Basic: 3
Abc | Def
.
<p>Abc | Def</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 9] chars:[0, 9, "Abc | Def"]
````````````````````````````````


```````````````````````````````` example Basic: 4
Abc|Def
-|-
.
<p>Abc|Def
-|-</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 7] chars:[0, 7, "Abc|Def"]
    SoftLineBreak[7, 8]
    Text[8, 11] chars:[8, 11, "-|-"]
````````````````````````````````


```````````````````````````````` example Basic: 5
Abc|Def
--|--
.
<p>Abc|Def
--|--</p>
.
Document[0, 13]
  Paragraph[0, 13]
    Text[0, 7] chars:[0, 7, "Abc|Def"]
    SoftLineBreak[7, 8]
    Text[8, 13] chars:[8, 13, "--|--"]
````````````````````````````````

Leading indent now allowed

```````````````````````````````` example Basic: 6
Abc|Def
 |---|---
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody></tbody>
</table>
.
Document[0, 17]
  TableBlock[0, 17]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 17]
      TableRow[8, 17]
        TableCell[9, 14] textOpen:[9, 10, "|"] text:[10, 13, "---"] textClose:[13, 14, "|"]
          Text[10, 13] chars:[10, 13, "---"]
        TableCell[14, 17] text:[14, 17, "---"]
          Text[14, 17] chars:[14, 17, "---"]
    TableBody[17, 17]
````````````````````````````````


```````````````````````````````` example Basic: 7
No
Abc|Def
---|---
.
<p>No
Abc|Def
---|---</p>
.
Document[0, 18]
  Paragraph[0, 18]
    Text[0, 2] chars:[0, 2, "No"]
    SoftLineBreak[2, 3]
    Text[3, 10] chars:[3, 10, "Abc|Def"]
    SoftLineBreak[10, 11]
    Text[11, 18] chars:[11, 18, "---|---"]
````````````````````````````````


```````````````````````````````` example Basic: 8
Abc|Def
---|---
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody></tbody>
</table>
.
Document[0, 15]
  TableBlock[0, 15]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] text:[8, 11, "---"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[15, 15]
````````````````````````````````


Separator columns need to be at least 3 characters, not 3 dashes

```````````````````````````````` example Basic: 9
Abc|Def
:--|---
.
<table>
  <thead>
    <tr><th align="left">Abc</th><th>Def</th></tr>
  </thead>
  <tbody></tbody>
</table>
.
Document[0, 15]
  TableBlock[0, 15]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] LEFT header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] LEFT text:[8, 11, ":--"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, ":--"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[15, 15]
````````````````````````````````


Separator columns need to be at least 3 characters, not 3 dashes

```````````````````````````````` example Basic: 10
Abc|Def
--:|---
.
<table>
  <thead>
    <tr><th align="right">Abc</th><th>Def</th></tr>
  </thead>
  <tbody></tbody>
</table>
.
Document[0, 15]
  TableBlock[0, 15]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] RIGHT header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] RIGHT text:[8, 11, "--:"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, "--:"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[15, 15]
````````````````````````````````


```````````````````````````````` example Basic: 11
Abc|Def
:-:|---
.
<table>
  <thead>
    <tr><th align="center">Abc</th><th>Def</th></tr>
  </thead>
  <tbody></tbody>
</table>
.
Document[0, 15]
  TableBlock[0, 15]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] CENTER header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] CENTER text:[8, 11, ":-:"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, ":-:"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[15, 15]
````````````````````````````````


```````````````````````````````` example Basic: 12
|Abc
|---
.
<table>
  <thead>
    <tr><th>Abc</th></tr>
  </thead>
  <tbody></tbody>
</table>
.
Document[0, 9]
  TableBlock[0, 9]
    TableHead[0, 4]
      TableRow[0, 4] rowNumber=1
        TableCell[0, 4] header textOpen:[0, 1, "|"] text:[1, 4, "Abc"]
          Text[1, 4] chars:[1, 4, "Abc"]
    TableSeparator[5, 9]
      TableRow[5, 9]
        TableCell[5, 9] textOpen:[5, 6, "|"] text:[6, 9, "---"]
          Text[6, 9] chars:[6, 9, "---"]
    TableBody[9, 9]
````````````````````````````````


```````````````````````````````` example Basic: 13
|Abc|
|---|
.
<table>
  <thead>
    <tr><th>Abc</th></tr>
  </thead>
  <tbody></tbody>
</table>
.
Document[0, 11]
  TableBlock[0, 11]
    TableHead[0, 5]
      TableRow[0, 5] rowNumber=1
        TableCell[0, 5] header textOpen:[0, 1, "|"] text:[1, 4, "Abc"] textClose:[4, 5, "|"]
          Text[1, 4] chars:[1, 4, "Abc"]
    TableSeparator[6, 11]
      TableRow[6, 11]
        TableCell[6, 11] textOpen:[6, 7, "|"] text:[7, 10, "---"] textClose:[10, 11, "|"]
          Text[7, 10] chars:[7, 10, "---"]
    TableBody[11, 11]
````````````````````````````````


```````````````````````````````` example Basic: 14
Abc|
---|
.
<table>
  <thead>
    <tr><th>Abc</th></tr>
  </thead>
  <tbody></tbody>
</table>
.
Document[0, 9]
  TableBlock[0, 9]
    TableHead[0, 4]
      TableRow[0, 4] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
    TableSeparator[5, 9]
      TableRow[5, 9]
        TableCell[5, 9] text:[5, 8, "---"] textClose:[8, 9, "|"]
          Text[5, 8] chars:[5, 8, "---"]
    TableBody[9, 9]
````````````````````````````````


```````````````````````````````` example Basic: 15
|Abc
---
.
<h2>|Abc</h2>
.
Document[0, 8]
  Heading[0, 8] text:[0, 4, "|Abc"] textClose:[5, 8, "---"]
    Text[0, 4] chars:[0, 4, "|Abc"]
````````````````````````````````


```````````````````````````````` example Basic: 16
Abc
|---
.
<p>Abc
|---</p>
.
Document[0, 8]
  Paragraph[0, 8]
    Text[0, 3] chars:[0, 3, "Abc"]
    SoftLineBreak[3, 4]
    Text[4, 8] chars:[4, 8, "|---"]
````````````````````````````````


```````````````````````````````` example Basic: 17
|Abc
|---
|1
.
<table>
  <thead>
    <tr><th>Abc</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td></tr>
  </tbody>
</table>
.
Document[0, 12]
  TableBlock[0, 12]
    TableHead[0, 4]
      TableRow[0, 4] rowNumber=1
        TableCell[0, 4] header textOpen:[0, 1, "|"] text:[1, 4, "Abc"]
          Text[1, 4] chars:[1, 4, "Abc"]
    TableSeparator[5, 9]
      TableRow[5, 9]
        TableCell[5, 9] textOpen:[5, 6, "|"] text:[6, 9, "---"]
          Text[6, 9] chars:[6, 9, "---"]
    TableBody[10, 12]
      TableRow[10, 12] rowNumber=1
        TableCell[10, 12] textOpen:[10, 11, "|"] text:[11, 12, "1"]
          Text[11, 12] chars:[11, 12, "1"]
````````````````````````````````


```````````````````````````````` example Basic: 18
|Abc|
|---|
|1|
.
<table>
  <thead>
    <tr><th>Abc</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td></tr>
  </tbody>
</table>
.
Document[0, 15]
  TableBlock[0, 15]
    TableHead[0, 5]
      TableRow[0, 5] rowNumber=1
        TableCell[0, 5] header textOpen:[0, 1, "|"] text:[1, 4, "Abc"] textClose:[4, 5, "|"]
          Text[1, 4] chars:[1, 4, "Abc"]
    TableSeparator[6, 11]
      TableRow[6, 11]
        TableCell[6, 11] textOpen:[6, 7, "|"] text:[7, 10, "---"] textClose:[10, 11, "|"]
          Text[7, 10] chars:[7, 10, "---"]
    TableBody[12, 15]
      TableRow[12, 15] rowNumber=1
        TableCell[12, 15] textOpen:[12, 13, "|"] text:[13, 14, "1"] textClose:[14, 15, "|"]
          Text[13, 14] chars:[13, 14, "1"]
````````````````````````````````


```````````````````````````````` example Basic: 19
Abc|
---|
1|
.
<table>
  <thead>
    <tr><th>Abc</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td></tr>
  </tbody>
</table>
.
Document[0, 12]
  TableBlock[0, 12]
    TableHead[0, 4]
      TableRow[0, 4] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
    TableSeparator[5, 9]
      TableRow[5, 9]
        TableCell[5, 9] text:[5, 8, "---"] textClose:[8, 9, "|"]
          Text[5, 8] chars:[5, 8, "---"]
    TableBody[10, 12]
      TableRow[10, 12] rowNumber=1
        TableCell[10, 12] text:[10, 11, "1"] textClose:[11, 12, "|"]
          Text[10, 11] chars:[10, 11, "1"]
````````````````````````````````


```````````````````````````````` example Basic: 20
|Abc
---
|1
.
<h2>|Abc</h2>
<p>|1</p>
.
Document[0, 11]
  Heading[0, 8] text:[0, 4, "|Abc"] textClose:[5, 8, "---"]
    Text[0, 4] chars:[0, 4, "|Abc"]
  Paragraph[9, 11]
    Text[9, 11] chars:[9, 11, "|1"]
````````````````````````````````


```````````````````````````````` example Basic: 21
|Abc
|---
1
.
<table>
  <thead>
    <tr><th>Abc</th></tr>
  </thead>
  <tbody></tbody>
</table>
<p>1</p>
.
Document[0, 11]
  TableBlock[0, 10]
    TableHead[0, 4]
      TableRow[0, 4] rowNumber=1
        TableCell[0, 4] header textOpen:[0, 1, "|"] text:[1, 4, "Abc"]
          Text[1, 4] chars:[1, 4, "Abc"]
    TableSeparator[5, 9]
      TableRow[5, 9]
        TableCell[5, 9] textOpen:[5, 6, "|"] text:[6, 9, "---"]
          Text[6, 9] chars:[6, 9, "---"]
    TableBody[9, 9]
  Paragraph[10, 11]
    Text[10, 11] chars:[10, 11, "1"]
````````````````````````````````


```````````````````````````````` example Basic: 22
Abc|Def
---|---
1|2
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td></tr>
  </tbody>
</table>
.
Document[0, 19]
  TableBlock[0, 19]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] text:[8, 11, "---"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 19]
      TableRow[16, 19] rowNumber=1
        TableCell[16, 18] text:[16, 17, "1"] textClose:[17, 18, "|"]
          Text[16, 17] chars:[16, 17, "1"]
        TableCell[18, 19] text:[18, 19, "2"]
          Text[18, 19] chars:[18, 19, "2"]
````````````````````````````````


```````````````````````````````` example Basic: 23
Abc|Def|Ghi
---|---
1|2|3
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th><th>Ghi</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td><td>3</td></tr>
  </tbody>
</table>
.
Document[0, 25]
  TableBlock[0, 25]
    TableHead[0, 11]
      TableRow[0, 11] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 8] header text:[4, 7, "Def"] textClose:[7, 8, "|"]
          Text[4, 7] chars:[4, 7, "Def"]
        TableCell[8, 11] header text:[8, 11, "Ghi"]
          Text[8, 11] chars:[8, 11, "Ghi"]
    TableSeparator[12, 19]
      TableRow[12, 19]
        TableCell[12, 16] text:[12, 15, "---"] textClose:[15, 16, "|"]
          Text[12, 15] chars:[12, 15, "---"]
        TableCell[16, 19] text:[16, 19, "---"]
          Text[16, 19] chars:[16, 19, "---"]
    TableBody[20, 25]
      TableRow[20, 25] rowNumber=1
        TableCell[20, 22] text:[20, 21, "1"] textClose:[21, 22, "|"]
          Text[20, 21] chars:[20, 21, "1"]
        TableCell[22, 24] text:[22, 23, "2"] textClose:[23, 24, "|"]
          Text[22, 23] chars:[22, 23, "2"]
        TableCell[24, 25] text:[24, 25, "3"]
          Text[24, 25] chars:[24, 25, "3"]
````````````````````````````````


```````````````````````````````` example Basic: 24
 Abc  | Def
 --- | ---
 1 | 2
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td></tr>
  </tbody>
</table>
.
Document[0, 29]
  TableBlock[1, 29]
    TableHead[1, 11]
      TableRow[1, 11] rowNumber=1
        TableCell[1, 7] header text:[1, 4, "Abc"] textClose:[6, 7, "|"]
          Text[1, 4] chars:[1, 4, "Abc"]
        TableCell[7, 11] header text:[8, 11, "Def"]
          Text[8, 11] chars:[8, 11, "Def"]
    TableSeparator[13, 22]
      TableRow[13, 22]
        TableCell[13, 18] text:[13, 16, "---"] textClose:[17, 18, "|"]
          Text[13, 16] chars:[13, 16, "---"]
        TableCell[18, 22] text:[19, 22, "---"]
          Text[19, 22] chars:[19, 22, "---"]
    TableBody[24, 29]
      TableRow[24, 29] rowNumber=1
        TableCell[24, 27] text:[24, 25, "1"] textClose:[26, 27, "|"]
          Text[24, 25] chars:[24, 25, "1"]
        TableCell[27, 29] text:[28, 29, "2"]
          Text[28, 29] chars:[28, 29, "2"]
````````````````````````````````


```````````````````````````````` example Basic: 25
Abc|Def
---|---
    1|2
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td></tr>
  </tbody>
</table>
.
Document[0, 23]
  TableBlock[0, 23]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] text:[8, 11, "---"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 23]
      TableRow[16, 23] rowNumber=1
        TableCell[20, 22] text:[20, 21, "1"] textClose:[21, 22, "|"]
          Text[20, 21] chars:[20, 21, "1"]
        TableCell[22, 23] text:[22, 23, "2"]
          Text[22, 23] chars:[22, 23, "2"]
````````````````````````````````


```````````````````````````````` example Basic: 26
|Abc|Def|
|---|---|
|1|2|
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td></tr>
  </tbody>
</table>
.
Document[0, 25]
  TableBlock[0, 25]
    TableHead[0, 9]
      TableRow[0, 9] rowNumber=1
        TableCell[0, 5] header textOpen:[0, 1, "|"] text:[1, 4, "Abc"] textClose:[4, 5, "|"]
          Text[1, 4] chars:[1, 4, "Abc"]
        TableCell[5, 9] header text:[5, 8, "Def"] textClose:[8, 9, "|"]
          Text[5, 8] chars:[5, 8, "Def"]
    TableSeparator[10, 19]
      TableRow[10, 19]
        TableCell[10, 15] textOpen:[10, 11, "|"] text:[11, 14, "---"] textClose:[14, 15, "|"]
          Text[11, 14] chars:[11, 14, "---"]
        TableCell[15, 19] text:[15, 18, "---"] textClose:[18, 19, "|"]
          Text[15, 18] chars:[15, 18, "---"]
    TableBody[20, 25]
      TableRow[20, 25] rowNumber=1
        TableCell[20, 23] textOpen:[20, 21, "|"] text:[21, 22, "1"] textClose:[22, 23, "|"]
          Text[21, 22] chars:[21, 22, "1"]
        TableCell[23, 25] text:[23, 24, "2"] textClose:[24, 25, "|"]
          Text[23, 24] chars:[23, 24, "2"]
````````````````````````````````


Embedded pipes in inline elements

```````````````````````````````` example Basic: 27
Abc|Def
---|---
`|`|`|`
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td><code>|</code></td><td><code>|</code></td></tr>
  </tbody>
</table>
.
Document[0, 23]
  TableBlock[0, 23]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] text:[8, 11, "---"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 23]
      TableRow[16, 23] rowNumber=1
        TableCell[16, 20] text:[16, 19, "`|`"] textClose:[19, 20, "|"]
          Code[16, 19] textOpen:[16, 17, "`"] text:[17, 18, "|"] textClose:[18, 19, "`"]
            Text[17, 18] chars:[17, 18, "|"]
        TableCell[20, 23] text:[20, 23, "`|`"]
          Code[20, 23] textOpen:[20, 21, "`"] text:[21, 22, "|"] textClose:[22, 23, "`"]
            Text[21, 22] chars:[21, 22, "|"]
````````````````````````````````


unclosed delimiters in cells

```````````````````````````````` example Basic: 28
Abc|Def
---|---
`| | abc
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>`</td><td> </td><td>abc</td></tr>
  </tbody>
</table>
.
Document[0, 24]
  TableBlock[0, 24]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] text:[8, 11, "---"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 24]
      TableRow[16, 24] rowNumber=1
        TableCell[16, 18] text:[16, 17, "`"] textClose:[17, 18, "|"]
          Text[16, 17] chars:[16, 17, "`"]
        TableCell[18, 20] text:[18, 19, " "] textClose:[19, 20, "|"]
          Text[18, 19] chars:[18, 19, " "]
        TableCell[20, 24] text:[21, 24, "abc"]
          Text[21, 24] chars:[21, 24, "abc"]
````````````````````````````````


unclosed delimiters in cells

```````````````````````````````` example Basic: 29
Abc|Def
---|---
**def | abc
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>**def</td><td>abc</td></tr>
  </tbody>
</table>
.
Document[0, 27]
  TableBlock[0, 27]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] text:[8, 11, "---"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 27]
      TableRow[16, 27] rowNumber=1
        TableCell[16, 23] text:[16, 21, "**def"] textClose:[22, 23, "|"]
          Text[16, 21] chars:[16, 21, "**def"]
        TableCell[23, 27] text:[24, 27, "abc"]
          Text[24, 27] chars:[24, 27, "abc"]
````````````````````````````````


```````````````````````````````` example Basic: 30
*Abc*|Def
---|---
1|2
.
<table>
  <thead>
    <tr><th><em>Abc</em></th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td></tr>
  </tbody>
</table>
.
Document[0, 21]
  TableBlock[0, 21]
    TableHead[0, 9]
      TableRow[0, 9] rowNumber=1
        TableCell[0, 6] header text:[0, 5, "*Abc*"] textClose:[5, 6, "|"]
          Emphasis[0, 5] textOpen:[0, 1, "*"] text:[1, 4, "Abc"] textClose:[4, 5, "*"]
            Text[1, 4] chars:[1, 4, "Abc"]
        TableCell[6, 9] header text:[6, 9, "Def"]
          Text[6, 9] chars:[6, 9, "Def"]
    TableSeparator[10, 17]
      TableRow[10, 17]
        TableCell[10, 14] text:[10, 13, "---"] textClose:[13, 14, "|"]
          Text[10, 13] chars:[10, 13, "---"]
        TableCell[14, 17] text:[14, 17, "---"]
          Text[14, 17] chars:[14, 17, "---"]
    TableBody[18, 21]
      TableRow[18, 21] rowNumber=1
        TableCell[18, 20] text:[18, 19, "1"] textClose:[19, 20, "|"]
          Text[18, 19] chars:[18, 19, "1"]
        TableCell[20, 21] text:[20, 21, "2"]
          Text[20, 21] chars:[20, 21, "2"]
````````````````````````````````


```````````````````````````````` example Basic: 31
Abc|Def
---|---
1\\|2|20
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1\</td><td>2</td><td>20</td></tr>
  </tbody>
</table>
.
Document[0, 24]
  TableBlock[0, 24]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] text:[8, 11, "---"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 24]
      TableRow[16, 24] rowNumber=1
        TableCell[16, 20] text:[16, 19, "1\\"] textClose:[19, 20, "|"]
          Text[16, 19] chars:[16, 19, "1\\"]
        TableCell[20, 22] text:[20, 21, "2"] textClose:[21, 22, "|"]
          Text[20, 21] chars:[20, 21, "2"]
        TableCell[22, 24] text:[22, 24, "20"]
          Text[22, 24] chars:[22, 24, "20"]
````````````````````````````````


Extra column should be truncated when GFM compatibility is selected

```````````````````````````````` example(Basic: 32) options(gfm)
Abc|Def
---|---
1\\|2|20
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1\</td><td>2</td></tr>
  </tbody>
</table>
.
Document[0, 24]
  TableBlock[0, 24]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] text:[8, 11, "---"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 24]
      TableRow[16, 24] rowNumber=1
        TableCell[16, 20] text:[16, 19, "1\\"] textClose:[19, 20, "|"]
          Text[16, 19] chars:[16, 19, "1\\"]
        TableCell[20, 22] text:[20, 21, "2"] textClose:[21, 22, "|"]
          Text[20, 21] chars:[20, 21, "2"]
````````````````````````````````


```````````````````````````````` example Basic: 33
Abc|Def
---|---
1\\\\|2
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1\\</td><td>2</td></tr>
  </tbody>
</table>
.
Document[0, 23]
  TableBlock[0, 23]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] text:[8, 11, "---"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 23]
      TableRow[16, 23] rowNumber=1
        TableCell[16, 22] text:[16, 21, "1\\\\"] textClose:[21, 22, "|"]
          Text[16, 21] chars:[16, 21, "1\\\\"]
        TableCell[22, 23] text:[22, 23, "2"]
          Text[22, 23] chars:[22, 23, "2"]
````````````````````````````````


```````````````````````````````` example Basic: 34
Abc|Def
:---|---
1|2
.
<table>
  <thead>
    <tr><th align="left">Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td align="left">1</td><td>2</td></tr>
  </tbody>
</table>
.
Document[0, 20]
  TableBlock[0, 20]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] LEFT header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 16]
      TableRow[8, 16]
        TableCell[8, 13] LEFT text:[8, 12, ":---"] textClose:[12, 13, "|"]
          Text[8, 12] chars:[8, 12, ":---"]
        TableCell[13, 16] text:[13, 16, "---"]
          Text[13, 16] chars:[13, 16, "---"]
    TableBody[17, 20]
      TableRow[17, 20] rowNumber=1
        TableCell[17, 19] LEFT text:[17, 18, "1"] textClose:[18, 19, "|"]
          Text[17, 18] chars:[17, 18, "1"]
        TableCell[19, 20] text:[19, 20, "2"]
          Text[19, 20] chars:[19, 20, "2"]
````````````````````````````````


```````````````````````````````` example Basic: 35
Abc|Def
---:|---
1|2
.
<table>
  <thead>
    <tr><th align="right">Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td align="right">1</td><td>2</td></tr>
  </tbody>
</table>
.
Document[0, 20]
  TableBlock[0, 20]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] RIGHT header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 16]
      TableRow[8, 16]
        TableCell[8, 13] RIGHT text:[8, 12, "---:"] textClose:[12, 13, "|"]
          Text[8, 12] chars:[8, 12, "---:"]
        TableCell[13, 16] text:[13, 16, "---"]
          Text[13, 16] chars:[13, 16, "---"]
    TableBody[17, 20]
      TableRow[17, 20] rowNumber=1
        TableCell[17, 19] RIGHT text:[17, 18, "1"] textClose:[18, 19, "|"]
          Text[17, 18] chars:[17, 18, "1"]
        TableCell[19, 20] text:[19, 20, "2"]
          Text[19, 20] chars:[19, 20, "2"]
````````````````````````````````


```````````````````````````````` example Basic: 36
Abc|Def
:---:|---
1|2
.
<table>
  <thead>
    <tr><th align="center">Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td align="center">1</td><td>2</td></tr>
  </tbody>
</table>
.
Document[0, 21]
  TableBlock[0, 21]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] CENTER header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 17]
      TableRow[8, 17]
        TableCell[8, 14] CENTER text:[8, 13, ":---:"] textClose:[13, 14, "|"]
          Text[8, 13] chars:[8, 13, ":---:"]
        TableCell[14, 17] text:[14, 17, "---"]
          Text[14, 17] chars:[14, 17, "---"]
    TableBody[18, 21]
      TableRow[18, 21] rowNumber=1
        TableCell[18, 20] CENTER text:[18, 19, "1"] textClose:[19, 20, "|"]
          Text[18, 19] chars:[18, 19, "1"]
        TableCell[20, 21] text:[20, 21, "2"]
          Text[20, 21] chars:[20, 21, "2"]
````````````````````````````````


```````````````````````````````` example Basic: 37
Abc|Def
---|:---:
1|2
.
<table>
  <thead>
    <tr><th>Abc</th><th align="center">Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td align="center">2</td></tr>
  </tbody>
</table>
.
Document[0, 21]
  TableBlock[0, 21]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] CENTER header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 17]
      TableRow[8, 17]
        TableCell[8, 12] text:[8, 11, "---"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 17] CENTER text:[12, 17, ":---:"]
          Text[12, 17] chars:[12, 17, ":---:"]
    TableBody[18, 21]
      TableRow[18, 21] rowNumber=1
        TableCell[18, 20] text:[18, 19, "1"] textClose:[19, 20, "|"]
          Text[18, 19] chars:[18, 19, "1"]
        TableCell[20, 21] CENTER text:[20, 21, "2"]
          Text[20, 21] chars:[20, 21, "2"]
````````````````````````````````


```````````````````````````````` example Basic: 38
Abc|Def
 :--- |---
1|2
.
<table>
  <thead>
    <tr><th align="left">Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td align="left">1</td><td>2</td></tr>
  </tbody>
</table>
.
Document[0, 22]
  TableBlock[0, 22]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] LEFT header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 18]
      TableRow[8, 18]
        TableCell[9, 15] LEFT text:[9, 13, ":---"] textClose:[14, 15, "|"]
          Text[9, 13] chars:[9, 13, ":---"]
        TableCell[15, 18] text:[15, 18, "---"]
          Text[15, 18] chars:[15, 18, "---"]
    TableBody[19, 22]
      TableRow[19, 22] rowNumber=1
        TableCell[19, 21] LEFT text:[19, 20, "1"] textClose:[20, 21, "|"]
          Text[19, 20] chars:[19, 20, "1"]
        TableCell[21, 22] text:[21, 22, "2"]
          Text[21, 22] chars:[21, 22, "2"]
````````````````````````````````


```````````````````````````````` example Basic: 39
Abc|Def
--- :|---
.
<p>Abc|Def
--- :|---</p>
.
Document[0, 17]
  Paragraph[0, 17]
    Text[0, 7] chars:[0, 7, "Abc|Def"]
    SoftLineBreak[7, 8]
    Text[8, 17] chars:[8, 17, "--- :|---"]
````````````````````````````````


```````````````````````````````` example Basic: 40
Abc|Def
---|: ---
.
<p>Abc|Def
---|: ---</p>
.
Document[0, 17]
  Paragraph[0, 17]
    Text[0, 7] chars:[0, 7, "Abc|Def"]
    SoftLineBreak[7, 8]
    Text[8, 17] chars:[8, 17, "---|: ---"]
````````````````````````````````


```````````````````````````````` example Basic: 41
Abc|Def
---|--- :
.
<p>Abc|Def
---|--- :</p>
.
Document[0, 17]
  Paragraph[0, 17]
    Text[0, 7] chars:[0, 7, "Abc|Def"]
    SoftLineBreak[7, 8]
    Text[8, 17] chars:[8, 17, "---|--- :"]
````````````````````````````````


```````````````````````````````` example Basic: 42
Abc|Def
---|---
1|2|3
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td><td>3</td></tr>
  </tbody>
</table>
.
Document[0, 21]
  TableBlock[0, 21]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] text:[8, 11, "---"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 21]
      TableRow[16, 21] rowNumber=1
        TableCell[16, 18] text:[16, 17, "1"] textClose:[17, 18, "|"]
          Text[16, 17] chars:[16, 17, "1"]
        TableCell[18, 20] text:[18, 19, "2"] textClose:[19, 20, "|"]
          Text[18, 19] chars:[18, 19, "2"]
        TableCell[20, 21] text:[20, 21, "3"]
          Text[20, 21] chars:[20, 21, "3"]
````````````````````````````````


Extra columns truncated with GFM compatibility on.

```````````````````````````````` example(Basic: 43) options(gfm)
Abc|Def
---|---
1|2|3
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td></tr>
  </tbody>
</table>
.
Document[0, 21]
  TableBlock[0, 21]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] text:[8, 11, "---"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 21]
      TableRow[16, 21] rowNumber=1
        TableCell[16, 18] text:[16, 17, "1"] textClose:[17, 18, "|"]
          Text[16, 17] chars:[16, 17, "1"]
        TableCell[18, 20] text:[18, 19, "2"] textClose:[19, 20, "|"]
          Text[18, 19] chars:[18, 19, "2"]
````````````````````````````````


```````````````````````````````` example Basic: 44
Abc|Def|Ghi
---|---|---
1|2
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th><th>Ghi</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td></tr>
  </tbody>
</table>
.
Document[0, 27]
  TableBlock[0, 27]
    TableHead[0, 11]
      TableRow[0, 11] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 8] header text:[4, 7, "Def"] textClose:[7, 8, "|"]
          Text[4, 7] chars:[4, 7, "Def"]
        TableCell[8, 11] header text:[8, 11, "Ghi"]
          Text[8, 11] chars:[8, 11, "Ghi"]
    TableSeparator[12, 23]
      TableRow[12, 23]
        TableCell[12, 16] text:[12, 15, "---"] textClose:[15, 16, "|"]
          Text[12, 15] chars:[12, 15, "---"]
        TableCell[16, 20] text:[16, 19, "---"] textClose:[19, 20, "|"]
          Text[16, 19] chars:[16, 19, "---"]
        TableCell[20, 23] text:[20, 23, "---"]
          Text[20, 23] chars:[20, 23, "---"]
    TableBody[24, 27]
      TableRow[24, 27] rowNumber=1
        TableCell[24, 26] text:[24, 25, "1"] textClose:[25, 26, "|"]
          Text[24, 25] chars:[24, 25, "1"]
        TableCell[26, 27] text:[26, 27, "2"]
          Text[26, 27] chars:[26, 27, "2"]
````````````````````````````````


```````````````````````````````` example Basic: 45
> Abc|Def
> ---|---
> 1|2
.
<blockquote>
  <table>
    <thead>
      <tr><th>Abc</th><th>Def</th></tr>
    </thead>
    <tbody>
      <tr><td>1</td><td>2</td></tr>
    </tbody>
  </table>
</blockquote>
.
Document[0, 25]
  BlockQuote[0, 25] marker:[0, 1, ">"]
    TableBlock[2, 25]
      TableHead[2, 9]
        TableRow[2, 9] rowNumber=1
          TableCell[2, 6] header text:[2, 5, "Abc"] textClose:[5, 6, "|"]
            Text[2, 5] chars:[2, 5, "Abc"]
          TableCell[6, 9] header text:[6, 9, "Def"]
            Text[6, 9] chars:[6, 9, "Def"]
      TableSeparator[12, 19]
        TableRow[12, 19]
          TableCell[12, 16] text:[12, 15, "---"] textClose:[15, 16, "|"]
            Text[12, 15] chars:[12, 15, "---"]
          TableCell[16, 19] text:[16, 19, "---"]
            Text[16, 19] chars:[16, 19, "---"]
      TableBody[22, 25]
        TableRow[22, 25] rowNumber=1
          TableCell[22, 24] text:[22, 23, "1"] textClose:[23, 24, "|"]
            Text[22, 23] chars:[22, 23, "1"]
          TableCell[24, 25] text:[24, 25, "2"]
            Text[24, 25] chars:[24, 25, "2"]
````````````````````````````````


```````````````````````````````` example Basic: 46
Abc|Def
---|---
1|2
table, you are over
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td></tr>
  </tbody>
</table>
<p>table, you are over</p>
.
Document[0, 39]
  TableBlock[0, 20]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] text:[8, 11, "---"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 19]
      TableRow[16, 19] rowNumber=1
        TableCell[16, 18] text:[16, 17, "1"] textClose:[17, 18, "|"]
          Text[16, 17] chars:[16, 17, "1"]
        TableCell[18, 19] text:[18, 19, "2"]
          Text[18, 19] chars:[18, 19, "2"]
  Paragraph[20, 39]
    Text[20, 39] chars:[20, 39, "table …  over"]
````````````````````````````````


inlines should be processed

```````````````````````````````` example Basic: 47
**Abc**|_Def_
---|---
[ref]|`code`
table, you are over

[ref]: /url
.
<table>
  <thead>
    <tr><th><strong>Abc</strong></th><th><em>Def</em></th></tr>
  </thead>
  <tbody>
    <tr><td><a href="/url">ref</a></td><td><code>code</code></td></tr>
  </tbody>
</table>
<p>table, you are over</p>
.
Document[0, 67]
  TableBlock[0, 35]
    TableHead[0, 13]
      TableRow[0, 13] rowNumber=1
        TableCell[0, 8] header text:[0, 7, "**Abc**"] textClose:[7, 8, "|"]
          StrongEmphasis[0, 7] textOpen:[0, 2, "**"] text:[2, 5, "Abc"] textClose:[5, 7, "**"]
            Text[2, 5] chars:[2, 5, "Abc"]
        TableCell[8, 13] header text:[8, 13, "_Def_"]
          Emphasis[8, 13] textOpen:[8, 9, "_"] text:[9, 12, "Def"] textClose:[12, 13, "_"]
            Text[9, 12] chars:[9, 12, "Def"]
    TableSeparator[14, 21]
      TableRow[14, 21]
        TableCell[14, 18] text:[14, 17, "---"] textClose:[17, 18, "|"]
          Text[14, 17] chars:[14, 17, "---"]
        TableCell[18, 21] text:[18, 21, "---"]
          Text[18, 21] chars:[18, 21, "---"]
    TableBody[22, 34]
      TableRow[22, 34] rowNumber=1
        TableCell[22, 28] text:[22, 27, "[ref]"] textClose:[27, 28, "|"]
          LinkRef[22, 27] referenceOpen:[22, 23, "["] reference:[23, 26, "ref"] referenceClose:[26, 27, "]"]
            Text[23, 26] chars:[23, 26, "ref"]
        TableCell[28, 34] text:[28, 34, "`code`"]
          Code[28, 34] textOpen:[28, 29, "`"] text:[29, 33, "code"] textClose:[33, 34, "`"]
            Text[29, 33] chars:[29, 33, "code"]
  Paragraph[35, 55] isTrailingBlankLine
    Text[35, 54] chars:[35, 54, "table …  over"]
  Reference[56, 67] refOpen:[56, 57, "["] ref:[57, 60, "ref"] refClose:[60, 62, "]:"] url:[63, 67, "/url"]
````````````````````````````````


inlines should be processed

```````````````````````````````` example Basic: 48
|**Abc** **test** |_Def_ _Def_
---|---
[ref]|`code` `code`
table, you are over

[ref]: /url
.
<table>
  <thead>
    <tr><th><strong>Abc</strong> <strong>test</strong></th><th><em>Def</em> <em>Def</em></th></tr>
  </thead>
  <tbody>
    <tr><td><a href="/url">ref</a></td><td><code>code</code> <code>code</code></td></tr>
  </tbody>
</table>
<p>table, you are over</p>
.
Document[0, 91]
  TableBlock[0, 59]
    TableHead[0, 30]
      TableRow[0, 30] rowNumber=1
        TableCell[0, 19] header textOpen:[0, 1, "|"] text:[1, 17, "**Abc** **test**"] textClose:[18, 19, "|"]
          StrongEmphasis[1, 8] textOpen:[1, 3, "**"] text:[3, 6, "Abc"] textClose:[6, 8, "**"]
            Text[3, 6] chars:[3, 6, "Abc"]
          Text[8, 9] chars:[8, 9, " "]
          StrongEmphasis[9, 17] textOpen:[9, 11, "**"] text:[11, 15, "test"] textClose:[15, 17, "**"]
            Text[11, 15] chars:[11, 15, "test"]
          Text[17, 17]
        TableCell[19, 30] header text:[19, 30, "_Def_ _Def_"]
          Emphasis[19, 24] textOpen:[19, 20, "_"] text:[20, 23, "Def"] textClose:[23, 24, "_"]
            Text[20, 23] chars:[20, 23, "Def"]
          Text[24, 25] chars:[24, 25, " "]
          Emphasis[25, 30] textOpen:[25, 26, "_"] text:[26, 29, "Def"] textClose:[29, 30, "_"]
            Text[26, 29] chars:[26, 29, "Def"]
    TableSeparator[31, 38]
      TableRow[31, 38]
        TableCell[31, 35] text:[31, 34, "---"] textClose:[34, 35, "|"]
          Text[31, 34] chars:[31, 34, "---"]
        TableCell[35, 38] text:[35, 38, "---"]
          Text[35, 38] chars:[35, 38, "---"]
    TableBody[39, 58]
      TableRow[39, 58] rowNumber=1
        TableCell[39, 45] text:[39, 44, "[ref]"] textClose:[44, 45, "|"]
          LinkRef[39, 44] referenceOpen:[39, 40, "["] reference:[40, 43, "ref"] referenceClose:[43, 44, "]"]
            Text[40, 43] chars:[40, 43, "ref"]
        TableCell[45, 58] text:[45, 58, "`code` `code`"]
          Code[45, 51] textOpen:[45, 46, "`"] text:[46, 50, "code"] textClose:[50, 51, "`"]
            Text[46, 50] chars:[46, 50, "code"]
          Text[51, 52] chars:[51, 52, " "]
          Code[52, 58] textOpen:[52, 53, "`"] text:[53, 57, "code"] textClose:[57, 58, "`"]
            Text[53, 57] chars:[53, 57, "code"]
  Paragraph[59, 79] isTrailingBlankLine
    Text[59, 78] chars:[59, 78, "table …  over"]
  Reference[80, 91] refOpen:[80, 81, "["] ref:[81, 84, "ref"] refClose:[84, 86, "]:"] url:[87, 91, "/url"]
````````````````````````````````


Column spans are created with repeated | pipes one for each additional column to span

```````````````````````````````` example Basic: 49
|Abc|Def
|---|---|
| span ||
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td colspan="2">span</td></tr>
  </tbody>
</table>
.
Document[0, 28]
  TableBlock[0, 28]
    TableHead[0, 8]
      TableRow[0, 8] rowNumber=1
        TableCell[0, 5] header textOpen:[0, 1, "|"] text:[1, 4, "Abc"] textClose:[4, 5, "|"]
          Text[1, 4] chars:[1, 4, "Abc"]
        TableCell[5, 8] header text:[5, 8, "Def"]
          Text[5, 8] chars:[5, 8, "Def"]
    TableSeparator[9, 18]
      TableRow[9, 18]
        TableCell[9, 14] textOpen:[9, 10, "|"] text:[10, 13, "---"] textClose:[13, 14, "|"]
          Text[10, 13] chars:[10, 13, "---"]
        TableCell[14, 18] text:[14, 17, "---"] textClose:[17, 18, "|"]
          Text[14, 17] chars:[14, 17, "---"]
    TableBody[19, 28]
      TableRow[19, 28] rowNumber=1
        TableCell[19, 28] span=2 textOpen:[19, 20, "|"] text:[21, 25, "span"] textClose:[26, 28, "||"]
          Text[21, 25] chars:[21, 25, "span"]
````````````````````````````````


Now we try varying the header lines and make sure we get the right output

```````````````````````````````` example Basic: 50
|Abc|Def
|Hij|Lmn
|---|---|
| span ||
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
    <tr><th>Hij</th><th>Lmn</th></tr>
  </thead>
  <tbody>
    <tr><td colspan="2">span</td></tr>
  </tbody>
</table>
.
Document[0, 37]
  TableBlock[0, 37]
    TableHead[0, 17]
      TableRow[0, 8] rowNumber=1
        TableCell[0, 5] header textOpen:[0, 1, "|"] text:[1, 4, "Abc"] textClose:[4, 5, "|"]
          Text[1, 4] chars:[1, 4, "Abc"]
        TableCell[5, 8] header text:[5, 8, "Def"]
          Text[5, 8] chars:[5, 8, "Def"]
      TableRow[9, 17] rowNumber=2
        TableCell[9, 14] header textOpen:[9, 10, "|"] text:[10, 13, "Hij"] textClose:[13, 14, "|"]
          Text[10, 13] chars:[10, 13, "Hij"]
        TableCell[14, 17] header text:[14, 17, "Lmn"]
          Text[14, 17] chars:[14, 17, "Lmn"]
    TableSeparator[18, 27]
      TableRow[18, 27]
        TableCell[18, 23] textOpen:[18, 19, "|"] text:[19, 22, "---"] textClose:[22, 23, "|"]
          Text[19, 22] chars:[19, 22, "---"]
        TableCell[23, 27] text:[23, 26, "---"] textClose:[26, 27, "|"]
          Text[23, 26] chars:[23, 26, "---"]
    TableBody[28, 37]
      TableRow[28, 37] rowNumber=1
        TableCell[28, 37] span=2 textOpen:[28, 29, "|"] text:[30, 34, "span"] textClose:[35, 37, "||"]
          Text[30, 34] chars:[30, 34, "span"]
````````````````````````````````


No header lines

```````````````````````````````` example Basic: 51
|---|---|
| col1 | col2|
.
<table>
  <thead></thead>
  <tbody>
    <tr><td>col1</td><td>col2</td></tr>
  </tbody>
</table>
.
Document[0, 24]
  TableBlock[0, 24]
    TableHead[0, 0]
    TableSeparator[0, 9]
      TableRow[0, 9]
        TableCell[0, 5] textOpen:[0, 1, "|"] text:[1, 4, "---"] textClose:[4, 5, "|"]
          Text[1, 4] chars:[1, 4, "---"]
        TableCell[5, 9] text:[5, 8, "---"] textClose:[8, 9, "|"]
          Text[5, 8] chars:[5, 8, "---"]
    TableBody[10, 24]
      TableRow[10, 24] rowNumber=1
        TableCell[10, 18] textOpen:[10, 11, "|"] text:[12, 16, "col1"] textClose:[17, 18, "|"]
          Text[12, 16] chars:[12, 16, "col1"]
        TableCell[18, 24] text:[19, 23, "col2"] textClose:[23, 24, "|"]
          Text[19, 23] chars:[19, 23, "col2"]
````````````````````````````````


No body lines

```````````````````````````````` example Basic: 52
| col1 | col2|
|---|---|
.
<table>
  <thead>
    <tr><th>col1</th><th>col2</th></tr>
  </thead>
  <tbody></tbody>
</table>
.
Document[0, 24]
  TableBlock[0, 24]
    TableHead[0, 14]
      TableRow[0, 14] rowNumber=1
        TableCell[0, 8] header textOpen:[0, 1, "|"] text:[2, 6, "col1"] textClose:[7, 8, "|"]
          Text[2, 6] chars:[2, 6, "col1"]
        TableCell[8, 14] header text:[9, 13, "col2"] textClose:[13, 14, "|"]
          Text[9, 13] chars:[9, 13, "col2"]
    TableSeparator[15, 24]
      TableRow[15, 24]
        TableCell[15, 20] textOpen:[15, 16, "|"] text:[16, 19, "---"] textClose:[19, 20, "|"]
          Text[16, 19] chars:[16, 19, "---"]
        TableCell[20, 24] text:[20, 23, "---"] textClose:[23, 24, "|"]
          Text[20, 23] chars:[20, 23, "---"]
    TableBody[24, 24]
````````````````````````````````


With caption

```````````````````````````````` example Basic: 53
| col1 | col2|
|---|---|
         [Caption **bold** _italic_ `code`]          
.
<table>
  <thead>
    <tr><th>col1</th><th>col2</th></tr>
  </thead>
  <tbody></tbody>
  <caption>Caption <strong>bold</strong> <em>italic</em> <code>code</code></caption>
</table>
.
Document[0, 78]
  TableBlock[0, 68]
    TableHead[0, 14]
      TableRow[0, 14] rowNumber=1
        TableCell[0, 8] header textOpen:[0, 1, "|"] text:[2, 6, "col1"] textClose:[7, 8, "|"]
          Text[2, 6] chars:[2, 6, "col1"]
        TableCell[8, 14] header text:[9, 13, "col2"] textClose:[13, 14, "|"]
          Text[9, 13] chars:[9, 13, "col2"]
    TableSeparator[15, 24]
      TableRow[15, 24]
        TableCell[15, 20] textOpen:[15, 16, "|"] text:[16, 19, "---"] textClose:[19, 20, "|"]
          Text[16, 19] chars:[16, 19, "---"]
        TableCell[20, 24] text:[20, 23, "---"] textClose:[23, 24, "|"]
          Text[20, 23] chars:[20, 23, "---"]
    TableBody[24, 24]
    TableCaption[34, 68] textOpen:[34, 35, "["] text:[35, 67, "Caption **bold** _italic_ `code`"] textClose:[67, 68, "]"]
      Text[35, 43] chars:[35, 43, "Caption "]
      StrongEmphasis[43, 51] textOpen:[43, 45, "**"] text:[45, 49, "bold"] textClose:[49, 51, "**"]
        Text[45, 49] chars:[45, 49, "bold"]
      Text[51, 52] chars:[51, 52, " "]
      Emphasis[52, 60] textOpen:[52, 53, "_"] text:[53, 59, "italic"] textClose:[59, 60, "_"]
        Text[53, 59] chars:[53, 59, "italic"]
      Text[60, 61] chars:[60, 61, " "]
      Code[61, 67] textOpen:[61, 62, "`"] text:[62, 66, "code"] textClose:[66, 67, "`"]
        Text[62, 66] chars:[62, 66, "code"]
````````````````````````````````


With caption but no caption parsing

```````````````````````````````` example(Basic: 54) options(no-caption)
| col1 | col2|
|---|---|
[Caption]
.
<table>
  <thead>
    <tr><th>col1</th><th>col2</th></tr>
  </thead>
  <tbody></tbody>
</table>
<p>[Caption]</p>
.
Document[0, 34]
  TableBlock[0, 25]
    TableHead[0, 14]
      TableRow[0, 14] rowNumber=1
        TableCell[0, 8] header textOpen:[0, 1, "|"] text:[2, 6, "col1"] textClose:[7, 8, "|"]
          Text[2, 6] chars:[2, 6, "col1"]
        TableCell[8, 14] header text:[9, 13, "col2"] textClose:[13, 14, "|"]
          Text[9, 13] chars:[9, 13, "col2"]
    TableSeparator[15, 24]
      TableRow[15, 24]
        TableCell[15, 20] textOpen:[15, 16, "|"] text:[16, 19, "---"] textClose:[19, 20, "|"]
          Text[16, 19] chars:[16, 19, "---"]
        TableCell[20, 24] text:[20, 23, "---"] textClose:[23, 24, "|"]
          Text[20, 23] chars:[20, 23, "---"]
    TableBody[24, 24]
  Paragraph[25, 34]
    LinkRef[25, 34] referenceOpen:[25, 26, "["] reference:[26, 33, "Caption"] referenceClose:[33, 34, "]"]
      Text[26, 33] chars:[26, 33, "Caption"]
````````````````````````````````


Alignment should be taken from column after span is added

```````````````````````````````` example Basic: 55
| day         | time  |   spent |
|:------------|:-----:|--------:|
| nov. 2. tue | 10:00 |  4h 40m |
| nov. 3. thu | 11:00 |      4h |
| nov. 7. mon | 10:20 |  4h 20m |
| total:             || **13h** |
.
<table>
  <thead>
    <tr><th align="left">day</th><th align="center">time</th><th align="right">spent</th></tr>
  </thead>
  <tbody>
    <tr><td align="left">nov. 2. tue</td><td align="center">10:00</td><td align="right">4h 40m</td></tr>
    <tr><td align="left">nov. 3. thu</td><td align="center">11:00</td><td align="right">4h</td></tr>
    <tr><td align="left">nov. 7. mon</td><td align="center">10:20</td><td align="right">4h 20m</td></tr>
    <tr><td align="left" colspan="2">total:</td><td align="right"><strong>13h</strong></td></tr>
  </tbody>
</table>
.
Document[0, 203]
  TableBlock[0, 203]
    TableHead[0, 33]
      TableRow[0, 33] rowNumber=1
        TableCell[0, 15] LEFT header textOpen:[0, 1, "|"] text:[2, 5, "day"] textClose:[14, 15, "|"]
          Text[2, 5] chars:[2, 5, "day"]
        TableCell[15, 23] CENTER header text:[16, 20, "time"] textClose:[22, 23, "|"]
          Text[16, 20] chars:[16, 20, "time"]
        TableCell[23, 33] RIGHT header text:[26, 31, "spent"] textClose:[32, 33, "|"]
          Text[26, 31] chars:[26, 31, "spent"]
    TableSeparator[34, 67]
      TableRow[34, 67]
        TableCell[34, 49] LEFT textOpen:[34, 35, "|"] text:[35, 48, ":------------"] textClose:[48, 49, "|"]
          Text[35, 48] chars:[35, 48, ":---- … -----"]
        TableCell[49, 57] CENTER text:[49, 56, ":-----:"] textClose:[56, 57, "|"]
          Text[49, 56] chars:[49, 56, ":-----:"]
        TableCell[57, 67] RIGHT text:[57, 66, "--------:"] textClose:[66, 67, "|"]
          Text[57, 66] chars:[57, 66, "--------:"]
    TableBody[68, 203]
      TableRow[68, 101] rowNumber=1
        TableCell[68, 83] LEFT textOpen:[68, 69, "|"] text:[70, 81, "nov. 2. tue"] textClose:[82, 83, "|"]
          Text[70, 81] chars:[70, 81, "nov.  … . tue"]
        TableCell[83, 91] CENTER text:[84, 89, "10:00"] textClose:[90, 91, "|"]
          Text[84, 89] chars:[84, 89, "10:00"]
        TableCell[91, 101] RIGHT text:[93, 99, "4h 40m"] textClose:[100, 101, "|"]
          Text[93, 99] chars:[93, 99, "4h 40m"]
      TableRow[102, 135] rowNumber=2
        TableCell[102, 117] LEFT textOpen:[102, 103, "|"] text:[104, 115, "nov. 3. thu"] textClose:[116, 117, "|"]
          Text[104, 115] chars:[104, 115, "nov.  … . thu"]
        TableCell[117, 125] CENTER text:[118, 123, "11:00"] textClose:[124, 125, "|"]
          Text[118, 123] chars:[118, 123, "11:00"]
        TableCell[125, 135] RIGHT text:[131, 133, "4h"] textClose:[134, 135, "|"]
          Text[131, 133] chars:[131, 133, "4h"]
      TableRow[136, 169] rowNumber=3
        TableCell[136, 151] LEFT textOpen:[136, 137, "|"] text:[138, 149, "nov. 7. mon"] textClose:[150, 151, "|"]
          Text[138, 149] chars:[138, 149, "nov.  … . mon"]
        TableCell[151, 159] CENTER text:[152, 157, "10:20"] textClose:[158, 159, "|"]
          Text[152, 157] chars:[152, 157, "10:20"]
        TableCell[159, 169] RIGHT text:[161, 167, "4h 20m"] textClose:[168, 169, "|"]
          Text[161, 167] chars:[161, 167, "4h 20m"]
      TableRow[170, 203] rowNumber=4
        TableCell[170, 193] LEFT span=2 textOpen:[170, 171, "|"] text:[172, 178, "total:"] textClose:[191, 193, "||"]
          Text[172, 178] chars:[172, 178, "total:"]
        TableCell[193, 203] RIGHT text:[194, 201, "**13h**"] textClose:[202, 203, "|"]
          StrongEmphasis[194, 201] textOpen:[194, 196, "**"] text:[196, 199, "13h"] textClose:[199, 201, "**"]
            Text[196, 199] chars:[196, 199, "13h"]
          Text[201, 201]
````````````````````````````````


### Leading Space

Allow non-indenting leading space

```````````````````````````````` example Basic - Leading Space: 1
   | Abc | Def |
|:----|:---:|
  | Ghi | Jkl |
.
<table>
  <thead>
    <tr><th align="left">Abc</th><th align="center">Def</th></tr>
  </thead>
  <tbody>
    <tr><td align="left">Ghi</td><td align="center">Jkl</td></tr>
  </tbody>
</table>
.
Document[0, 46]
  TableBlock[3, 46]
    TableHead[3, 16]
      TableRow[3, 16] rowNumber=1
        TableCell[3, 10] LEFT header textOpen:[3, 4, "|"] text:[5, 8, "Abc"] textClose:[9, 10, "|"]
          Text[5, 8] chars:[5, 8, "Abc"]
        TableCell[10, 16] CENTER header text:[11, 14, "Def"] textClose:[15, 16, "|"]
          Text[11, 14] chars:[11, 14, "Def"]
    TableSeparator[17, 30]
      TableRow[17, 30]
        TableCell[17, 24] LEFT textOpen:[17, 18, "|"] text:[18, 23, ":----"] textClose:[23, 24, "|"]
          Text[18, 23] chars:[18, 23, ":----"]
        TableCell[24, 30] CENTER text:[24, 29, ":---:"] textClose:[29, 30, "|"]
          Text[24, 29] chars:[24, 29, ":---:"]
    TableBody[33, 46]
      TableRow[33, 46] rowNumber=1
        TableCell[33, 40] LEFT textOpen:[33, 34, "|"] text:[35, 38, "Ghi"] textClose:[39, 40, "|"]
          Text[35, 38] chars:[35, 38, "Ghi"]
        TableCell[40, 46] CENTER text:[41, 44, "Jkl"] textClose:[45, 46, "|"]
          Text[41, 44] chars:[41, 44, "Jkl"]
````````````````````````````````


Do not allow indenting leading space, handled automatically since it is indented code not
paragraph.

```````````````````````````````` example Basic - Leading Space: 2
    | Abc | Def |
    |:----|:---:|
    | Ghi | Jkl |
.
<pre><code>| Abc | Def |
|:----|:---:|
| Ghi | Jkl |
</code></pre>
.
Document[0, 53]
  IndentedCodeBlock[4, 53]
````````````````````````````````


## In Lists

Table in list should be parsed.

```````````````````````````````` example In Lists: 1
* another item
  
  | table |
  |-------|
  | data  |
.
<ul>
  <li>
    <p>another item</p>
    <table>
      <thead>
        <tr><th>table</th></tr>
      </thead>
      <tbody>
        <tr><td>data</td></tr>
      </tbody>
    </table>
  </li>
</ul>
.
Document[0, 53]
  BulletList[0, 53] isLoose
    BulletListItem[0, 53] open:[0, 1, "*"] isLoose hadBlankLineAfter
      Paragraph[2, 15] isTrailingBlankLine
        Text[2, 14] chars:[2, 14, "anoth …  item"]
      TableBlock[20, 53]
        TableHead[20, 29]
          TableRow[20, 29] rowNumber=1
            TableCell[20, 29] header textOpen:[20, 21, "|"] text:[22, 27, "table"] textClose:[28, 29, "|"]
              Text[22, 27] chars:[22, 27, "table"]
        TableSeparator[32, 41]
          TableRow[32, 41]
            TableCell[32, 41] textOpen:[32, 33, "|"] text:[33, 40, "-------"] textClose:[40, 41, "|"]
              Text[33, 40] chars:[33, 40, "-------"]
        TableBody[44, 53]
          TableRow[44, 53] rowNumber=1
            TableCell[44, 53] textOpen:[44, 45, "|"] text:[46, 50, "data"] textClose:[52, 53, "|"]
              Text[46, 50] chars:[46, 50, "data"]
````````````````````````````````


## Inlines

```````````````````````````````` example Inlines: 1
| c | d |
| --- | --- |
| *a | b* |
| `e | f` |
| [g | h](http://a.com) |
.
<table>
  <thead>
    <tr><th>c</th><th>d</th></tr>
  </thead>
  <tbody>
    <tr><td>*a</td><td>b*</td></tr>
    <tr><td><code>e | f</code></td></tr>
    <tr><td><a href="http://a.com">g | h</a></td></tr>
  </tbody>
</table>
.
Document[0, 73]
  TableBlock[0, 73]
    TableHead[0, 9]
      TableRow[0, 9] rowNumber=1
        TableCell[0, 5] header textOpen:[0, 1, "|"] text:[2, 3, "c"] textClose:[4, 5, "|"]
          Text[2, 3] chars:[2, 3, "c"]
        TableCell[5, 9] header text:[6, 7, "d"] textClose:[8, 9, "|"]
          Text[6, 7] chars:[6, 7, "d"]
    TableSeparator[10, 23]
      TableRow[10, 23]
        TableCell[10, 17] textOpen:[10, 11, "|"] text:[12, 15, "---"] textClose:[16, 17, "|"]
          Text[12, 15] chars:[12, 15, "---"]
        TableCell[17, 23] text:[18, 21, "---"] textClose:[22, 23, "|"]
          Text[18, 21] chars:[18, 21, "---"]
    TableBody[24, 73]
      TableRow[24, 35] rowNumber=1
        TableCell[24, 30] textOpen:[24, 25, "|"] text:[26, 28, "*a"] textClose:[29, 30, "|"]
          Text[26, 28] chars:[26, 28, "*a"]
        TableCell[30, 35] text:[31, 33, "b*"] textClose:[34, 35, "|"]
          Text[31, 33] chars:[31, 33, "b*"]
      TableRow[36, 47] rowNumber=2
        TableCell[36, 47] textOpen:[36, 37, "|"] text:[38, 45, "`e | f`"] textClose:[46, 47, "|"]
          Code[38, 45] textOpen:[38, 39, "`"] text:[39, 44, "e | f"] textClose:[44, 45, "`"]
            Text[39, 44] chars:[39, 44, "e | f"]
          Text[45, 45]
      TableRow[48, 73] rowNumber=3
        TableCell[48, 73] textOpen:[48, 49, "|"] text:[50, 71, "[g | h](http://a.com)"] textClose:[72, 73, "|"]
          Link[50, 71] textOpen:[50, 51, "["] text:[51, 56, "g | h"] textClose:[56, 57, "]"] linkOpen:[57, 58, "("] url:[58, 70, "http://a.com"] pageRef:[58, 70, "http://a.com"] linkClose:[70, 71, ")"]
            Text[51, 56] chars:[51, 56, "g | h"]
          Text[71, 71]
````````````````````````````````


## Multiple

multiple tables parsed correctly

```````````````````````````````` example Multiple: 1
not a table, followed by a table

| col1 | col2|
|---|---|

| col1 | col2|
|---|---|
| data1 | data2|

not a table, followed by a table

| col11 | col12|
| col21 | col22|
|---|---|
| data1 | data2|

.
<p>not a table, followed by a table</p>
<table>
  <thead>
    <tr><th>col1</th><th>col2</th></tr>
  </thead>
  <tbody></tbody>
</table>
<table>
  <thead>
    <tr><th>col1</th><th>col2</th></tr>
  </thead>
  <tbody>
    <tr><td>data1</td><td>data2</td></tr>
  </tbody>
</table>
<p>not a table, followed by a table</p>
<table>
  <thead>
    <tr><th>col11</th><th>col12</th></tr>
    <tr><th>col21</th><th>col22</th></tr>
  </thead>
  <tbody>
    <tr><td>data1</td><td>data2</td></tr>
  </tbody>
</table>
.
Document[0, 199]
  Paragraph[0, 33] isTrailingBlankLine
    Text[0, 32] chars:[0, 32, "not a … table"]
  TableBlock[34, 59]
    TableHead[34, 48]
      TableRow[34, 48] rowNumber=1
        TableCell[34, 42] header textOpen:[34, 35, "|"] text:[36, 40, "col1"] textClose:[41, 42, "|"]
          Text[36, 40] chars:[36, 40, "col1"]
        TableCell[42, 48] header text:[43, 47, "col2"] textClose:[47, 48, "|"]
          Text[43, 47] chars:[43, 47, "col2"]
    TableSeparator[49, 58]
      TableRow[49, 58]
        TableCell[49, 54] textOpen:[49, 50, "|"] text:[50, 53, "---"] textClose:[53, 54, "|"]
          Text[50, 53] chars:[50, 53, "---"]
        TableCell[54, 58] text:[54, 57, "---"] textClose:[57, 58, "|"]
          Text[54, 57] chars:[54, 57, "---"]
    TableBody[58, 58]
  TableBlock[60, 102]
    TableHead[60, 74]
      TableRow[60, 74] rowNumber=1
        TableCell[60, 68] header textOpen:[60, 61, "|"] text:[62, 66, "col1"] textClose:[67, 68, "|"]
          Text[62, 66] chars:[62, 66, "col1"]
        TableCell[68, 74] header text:[69, 73, "col2"] textClose:[73, 74, "|"]
          Text[69, 73] chars:[69, 73, "col2"]
    TableSeparator[75, 84]
      TableRow[75, 84]
        TableCell[75, 80] textOpen:[75, 76, "|"] text:[76, 79, "---"] textClose:[79, 80, "|"]
          Text[76, 79] chars:[76, 79, "---"]
        TableCell[80, 84] text:[80, 83, "---"] textClose:[83, 84, "|"]
          Text[80, 83] chars:[80, 83, "---"]
    TableBody[85, 101]
      TableRow[85, 101] rowNumber=1
        TableCell[85, 94] textOpen:[85, 86, "|"] text:[87, 92, "data1"] textClose:[93, 94, "|"]
          Text[87, 92] chars:[87, 92, "data1"]
        TableCell[94, 101] text:[95, 100, "data2"] textClose:[100, 101, "|"]
          Text[95, 100] chars:[95, 100, "data2"]
  Paragraph[103, 136] isTrailingBlankLine
    Text[103, 135] chars:[103, 135, "not a … table"]
  TableBlock[137, 198]
    TableHead[137, 170]
      TableRow[137, 153] rowNumber=1
        TableCell[137, 146] header textOpen:[137, 138, "|"] text:[139, 144, "col11"] textClose:[145, 146, "|"]
          Text[139, 144] chars:[139, 144, "col11"]
        TableCell[146, 153] header text:[147, 152, "col12"] textClose:[152, 153, "|"]
          Text[147, 152] chars:[147, 152, "col12"]
      TableRow[154, 170] rowNumber=2
        TableCell[154, 163] header textOpen:[154, 155, "|"] text:[156, 161, "col21"] textClose:[162, 163, "|"]
          Text[156, 161] chars:[156, 161, "col21"]
        TableCell[163, 170] header text:[164, 169, "col22"] textClose:[169, 170, "|"]
          Text[164, 169] chars:[164, 169, "col22"]
    TableSeparator[171, 180]
      TableRow[171, 180]
        TableCell[171, 176] textOpen:[171, 172, "|"] text:[172, 175, "---"] textClose:[175, 176, "|"]
          Text[172, 175] chars:[172, 175, "---"]
        TableCell[176, 180] text:[176, 179, "---"] textClose:[179, 180, "|"]
          Text[176, 179] chars:[176, 179, "---"]
    TableBody[181, 197]
      TableRow[181, 197] rowNumber=1
        TableCell[181, 190] textOpen:[181, 182, "|"] text:[183, 188, "data1"] textClose:[189, 190, "|"]
          Text[183, 188] chars:[183, 188, "data1"]
        TableCell[190, 197] text:[191, 196, "data2"] textClose:[196, 197, "|"]
          Text[191, 196] chars:[191, 196, "data2"]
````````````````````````````````


multi row/column

```````````````````````````````` example Multiple: 2
| col11 | col12| col13|
| col21 | col22| col23|
| col31 | col32| col33|
|---|---|---|
| data11 | data12| data13|
| data21 | data22| data23|
| data31 | data32| data33|

.
<table>
  <thead>
    <tr><th>col11</th><th>col12</th><th>col13</th></tr>
    <tr><th>col21</th><th>col22</th><th>col23</th></tr>
    <tr><th>col31</th><th>col32</th><th>col33</th></tr>
  </thead>
  <tbody>
    <tr><td>data11</td><td>data12</td><td>data13</td></tr>
    <tr><td>data21</td><td>data22</td><td>data23</td></tr>
    <tr><td>data31</td><td>data32</td><td>data33</td></tr>
  </tbody>
</table>
.
Document[0, 168]
  TableBlock[0, 167]
    TableHead[0, 71]
      TableRow[0, 23] rowNumber=1
        TableCell[0, 9] header textOpen:[0, 1, "|"] text:[2, 7, "col11"] textClose:[8, 9, "|"]
          Text[2, 7] chars:[2, 7, "col11"]
        TableCell[9, 16] header text:[10, 15, "col12"] textClose:[15, 16, "|"]
          Text[10, 15] chars:[10, 15, "col12"]
        TableCell[16, 23] header text:[17, 22, "col13"] textClose:[22, 23, "|"]
          Text[17, 22] chars:[17, 22, "col13"]
      TableRow[24, 47] rowNumber=2
        TableCell[24, 33] header textOpen:[24, 25, "|"] text:[26, 31, "col21"] textClose:[32, 33, "|"]
          Text[26, 31] chars:[26, 31, "col21"]
        TableCell[33, 40] header text:[34, 39, "col22"] textClose:[39, 40, "|"]
          Text[34, 39] chars:[34, 39, "col22"]
        TableCell[40, 47] header text:[41, 46, "col23"] textClose:[46, 47, "|"]
          Text[41, 46] chars:[41, 46, "col23"]
      TableRow[48, 71] rowNumber=3
        TableCell[48, 57] header textOpen:[48, 49, "|"] text:[50, 55, "col31"] textClose:[56, 57, "|"]
          Text[50, 55] chars:[50, 55, "col31"]
        TableCell[57, 64] header text:[58, 63, "col32"] textClose:[63, 64, "|"]
          Text[58, 63] chars:[58, 63, "col32"]
        TableCell[64, 71] header text:[65, 70, "col33"] textClose:[70, 71, "|"]
          Text[65, 70] chars:[65, 70, "col33"]
    TableSeparator[72, 85]
      TableRow[72, 85]
        TableCell[72, 77] textOpen:[72, 73, "|"] text:[73, 76, "---"] textClose:[76, 77, "|"]
          Text[73, 76] chars:[73, 76, "---"]
        TableCell[77, 81] text:[77, 80, "---"] textClose:[80, 81, "|"]
          Text[77, 80] chars:[77, 80, "---"]
        TableCell[81, 85] text:[81, 84, "---"] textClose:[84, 85, "|"]
          Text[81, 84] chars:[81, 84, "---"]
    TableBody[86, 166]
      TableRow[86, 112] rowNumber=1
        TableCell[86, 96] textOpen:[86, 87, "|"] text:[88, 94, "data11"] textClose:[95, 96, "|"]
          Text[88, 94] chars:[88, 94, "data11"]
        TableCell[96, 104] text:[97, 103, "data12"] textClose:[103, 104, "|"]
          Text[97, 103] chars:[97, 103, "data12"]
        TableCell[104, 112] text:[105, 111, "data13"] textClose:[111, 112, "|"]
          Text[105, 111] chars:[105, 111, "data13"]
      TableRow[113, 139] rowNumber=2
        TableCell[113, 123] textOpen:[113, 114, "|"] text:[115, 121, "data21"] textClose:[122, 123, "|"]
          Text[115, 121] chars:[115, 121, "data21"]
        TableCell[123, 131] text:[124, 130, "data22"] textClose:[130, 131, "|"]
          Text[124, 130] chars:[124, 130, "data22"]
        TableCell[131, 139] text:[132, 138, "data23"] textClose:[138, 139, "|"]
          Text[132, 138] chars:[132, 138, "data23"]
      TableRow[140, 166] rowNumber=3
        TableCell[140, 150] textOpen:[140, 141, "|"] text:[142, 148, "data31"] textClose:[149, 150, "|"]
          Text[142, 148] chars:[142, 148, "data31"]
        TableCell[150, 158] text:[151, 157, "data32"] textClose:[157, 158, "|"]
          Text[151, 157] chars:[151, 157, "data32"]
        TableCell[158, 166] text:[159, 165, "data33"] textClose:[165, 166, "|"]
          Text[159, 165] chars:[159, 165, "data33"]
````````````````````````````````


keep cell whitespace

```````````````````````````````` example(Multiple: 3) options(keep-whitespace)
 Abc  | Def
 --- | ---
 1 | 2
.
<table>
  <thead>
    <tr><th>Abc  </th><th> Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1 </td><td> 2</td></tr>
  </tbody>
</table>
.
Document[0, 29]
  TableBlock[1, 29]
    TableHead[1, 11]
      TableRow[1, 11] rowNumber=1
        TableCell[1, 7] header text:[1, 6, "Abc  "] textClose:[6, 7, "|"]
          Text[1, 6] chars:[1, 6, "Abc  "]
        TableCell[7, 11] header text:[7, 11, " Def"]
          Text[7, 11] chars:[7, 11, " Def"]
    TableSeparator[13, 22]
      TableRow[13, 22]
        TableCell[13, 18] text:[13, 17, "--- "] textClose:[17, 18, "|"]
          Text[13, 17] chars:[13, 17, "--- "]
        TableCell[18, 22] text:[18, 22, " ---"]
          Text[18, 22] chars:[18, 22, " ---"]
    TableBody[24, 29]
      TableRow[24, 29] rowNumber=1
        TableCell[24, 27] text:[24, 26, "1 "] textClose:[26, 27, "|"]
          Text[24, 26] chars:[24, 26, "1 "]
        TableCell[27, 29] text:[27, 29, " 2"]
          Text[27, 29] chars:[27, 29, " 2"]
````````````````````````````````


Custom class name

```````````````````````````````` example(Multiple: 4) options(class-name)
Abc|Def
---|---
.
<table class="table-class">
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody></tbody>
</table>
.
Document[0, 15]
  TableBlock[0, 15]
    TableHead[0, 7]
      TableRow[0, 7] rowNumber=1
        TableCell[0, 4] header text:[0, 3, "Abc"] textClose:[3, 4, "|"]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header text:[4, 7, "Def"]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 12] text:[8, 11, "---"] textClose:[11, 12, "|"]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] text:[12, 15, "---"]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[15, 15]
````````````````````````````````


in item

```````````````````````````````` example(Multiple: 5) options(keep-whitespace)
- Add: live templates starting with `.`    
                                        
  | Element       | Abbreviation    | Expansion                                               |
  |---------------|-----------------|---------------------------------------------------------|
  | Abbreviation  | `.abbreviation` | `*[]: `                                                 |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\`                                       |
  | Explicit link | `.link`         | `[]()`                                                  |
.
<ul>
  <li>
    <p>Add: live templates starting with <code>.</code></p>
    <table>
      <thead>
        <tr><th> Element       </th><th> Abbreviation    </th><th> Expansion                                               </th></tr>
      </thead>
      <tbody>
        <tr><td> Abbreviation  </td><td> <code>.abbreviation</code> </td><td> <code>*[]:</code>                                                 </td></tr>
        <tr><td> Code fence    </td><td> <code>.codefence</code>    </td><td> ``` ... ```                                       </td></tr>
        <tr><td> Explicit link </td><td> <code>.link</code>         </td><td> <code>[]()</code>                                                  </td></tr>
      </tbody>
    </table>
  </li>
</ul>
.
Document[0, 564]
  BulletList[0, 564] isLoose
    BulletListItem[0, 564] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[2, 44] isTrailingBlankLine
        Text[2, 36] chars:[2, 36, "Add:  … with "]
        Code[36, 39] textOpen:[36, 37, "`"] text:[37, 38, "."] textClose:[38, 39, "`"]
          Text[37, 38] chars:[37, 38, "."]
      TableBlock[87, 564]
        TableHead[87, 180]
          TableRow[87, 180] rowNumber=1
            TableCell[87, 104] header textOpen:[87, 88, "|"] text:[88, 103, " Element       "] textClose:[103, 104, "|"]
              Text[88, 103] chars:[88, 103, " Elem …      "]
            TableCell[104, 122] header text:[104, 121, " Abbreviation    "] textClose:[121, 122, "|"]
              Text[104, 121] chars:[104, 121, " Abbr … n    "]
            TableCell[122, 180] header text:[122, 179, " Expansion                                               "] textClose:[179, 180, "|"]
              Text[122, 179] chars:[122, 179, " Expa …      "]
        TableSeparator[183, 276]
          TableRow[183, 276]
            TableCell[183, 200] textOpen:[183, 184, "|"] text:[184, 199, "---------------"] textClose:[199, 200, "|"]
              Text[184, 199] chars:[184, 199, "----- … -----"]
            TableCell[200, 218] text:[200, 217, "-----------------"] textClose:[217, 218, "|"]
              Text[200, 217] chars:[200, 217, "----- … -----"]
            TableCell[218, 276] text:[218, 275, "---------------------------------------------------------"] textClose:[275, 276, "|"]
              Text[218, 275] chars:[218, 275, "----- … -----"]
        TableBody[279, 564]
          TableRow[279, 372] rowNumber=1
            TableCell[279, 296] textOpen:[279, 280, "|"] text:[280, 295, " Abbreviation  "] textClose:[295, 296, "|"]
              Text[280, 295] chars:[280, 295, " Abbr … ion  "]
            TableCell[296, 314] text:[296, 313, " `.abbreviation` "] textClose:[313, 314, "|"]
              Text[296, 297] chars:[296, 297, " "]
              Code[297, 312] textOpen:[297, 298, "`"] text:[298, 311, ".abbr … eviation"] textClose:[311, 312, "`"]
                Text[298, 311] chars:[298, 311, ".abbr … ation"]
              Text[312, 312]
              Text[312, 313] chars:[312, 313, " "]
            TableCell[314, 372] text:[314, 371, " `*[]: `                                                 "] textClose:[371, 372, "|"]
              Text[314, 315] chars:[314, 315, " "]
              Code[315, 322] textOpen:[315, 316, "`"] text:[316, 321, "*[]: "] textClose:[321, 322, "`"]
                Text[316, 321] chars:[316, 321, "*[]: "]
              Text[322, 322]
              Text[322, 371] chars:[322, 371, "      …      "]
          TableRow[375, 468] rowNumber=2
            TableCell[375, 392] textOpen:[375, 376, "|"] text:[376, 391, " Code fence    "] textClose:[391, 392, "|"]
              Text[376, 391] chars:[376, 391, " Code … e    "]
            TableCell[392, 410] text:[392, 409, " `.codefence`    "] textClose:[409, 410, "|"]
              Text[392, 393] chars:[392, 393, " "]
              Code[393, 405] textOpen:[393, 394, "`"] text:[394, 404, ".codefence"] textClose:[404, 405, "`"]
                Text[394, 404] chars:[394, 404, ".codefence"]
              Text[405, 405]
              Text[405, 409] chars:[405, 409, "    "]
            TableCell[410, 468] text:[410, 467, " \`\`\` ... \`\`\`                                       "] textClose:[467, 468, "|"]
              Text[410, 467] chars:[410, 467, " \`\` …      "]
          TableRow[471, 564] rowNumber=3
            TableCell[471, 488] textOpen:[471, 472, "|"] text:[472, 487, " Explicit link "] textClose:[487, 488, "|"]
              Text[472, 487] chars:[472, 487, " Expl … link "]
            TableCell[488, 506] text:[488, 505, " `.link`         "] textClose:[505, 506, "|"]
              Text[488, 489] chars:[488, 489, " "]
              Code[489, 496] textOpen:[489, 490, "`"] text:[490, 495, ".link"] textClose:[495, 496, "`"]
                Text[490, 495] chars:[490, 495, ".link"]
              Text[496, 496]
              Text[496, 505] chars:[496, 505, "         "]
            TableCell[506, 564] text:[506, 563, " `[]()`                                                  "] textClose:[563, 564, "|"]
              Text[506, 507] chars:[506, 507, " "]
              Code[507, 513] textOpen:[507, 508, "`"] text:[508, 512, "[]()"] textClose:[512, 513, "`"]
                Text[508, 512] chars:[508, 512, "[]()"]
              Text[513, 513]
              Text[513, 563] chars:[513, 563, "      …      "]
````````````````````````````````


real life table

```````````````````````````````` example Multiple: 6
| Feature                                                                                                                 | Basic | Enhanced |
|:------------------------------------------------------------------------------------------------------------------------|:-----:|:--------:|
| Works with builds 143.2370 or newer, product version IDEA 15.0.6                                                        |   X   |    X     |
| Preview Tab so you can see what the rendered markdown will look like on GitHub.                                         |   X   |    X     |
| Syntax highlighting                                                                                                     |   X   |    X     |
| Table syntax highlighting stripes rows and columns                                                                      |   X   |    X     |
| Support for Default and Darcula color schemes for preview tab                                                           |   X   |    X     |
| Warning and Error Annotations to help you validate wiki link errors                                                     |   X   |    X     |
| Link address completion for wiki links                                                                                  |   X   |    X     |
| Quick Fixes for detected wiki link errors                                                                               |   X   |    X     |
| GFM Task list extension `* [ ]` open task item and `* [x]` completed task item                                          |   X   |    X     |
| Line markers, Find usages, Go To Declaration for rapid navigation to wiki link targets                                  |   X   |    X     |
| Markdown extensions configuration to customize markdown dialects                                                        |   X   |    X     |
| GitHub wiki support makes maintaining GitHub wiki pages easier.                                                         |   X   |    X     |
| GitHub compatible id generation for headers so you can validate your anchor references                                  |   X   |    X     |
| Swing and JavaFX WebView based preview.                                                                                 |   X   |    X     |
| Supports **JavaFX with JetBrains JRE on OS X**                                                                          |   X   |    X     |
| Supports Highlight JS in WebView preview                                                                                |   X   |    X     |
| **Multi-line Image URLs for embedding [gravizo.com] UML diagrams into markdown**                                        |   X   |    X     |
| Live Templates for common markdown elements                                                                             |   X   |    X     |
| **Enhanced Version Benefits**                                                                                           |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Split Editor with Preview or HTML Text modes to view both source and preview                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Toolbar for fast access to frequent operations                                                  |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Language Injections for fenced code, HTML, Jekyll front matter and multi-line URL content       |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Code completions, refactoring, annotations and quick fixes to let you work faster               |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Navigation support with Line markers, Find usages, Go To Declaration for rapid navigation       |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Inspections to help you validate links, anchor refs, footnote refs                              |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Complete GitHub wiki support for all links makes maintaining GitHub wiki pages a breeze         |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Jekyll front matter recognition in markdown documents                                           |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Emoji text to icon conversion using [Emoji Cheat Sheet] or GitHub emoji URLs                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Wrap on typing and table formatting with column alignment                                       |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Character display width used for wrapping and table formatting                                  |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Structure view for Abbreviations, Headers, Tables, Footnotes, References and Document           |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Document formatting with text wrapping, list renumbering, aranging of elements, etc.            |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Table of Contents generation for any markdown parser, with many style options                   |       |    X     |
| **As you type automation**                                                                                              |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Double of bold/emphasis markers and remove inserted ones if a space is typed                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Wrap text blocks to margins and indentation                                                     |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;ATX headers to match trailing `#` marker                                                        |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Setext headers to match marker length to text                                                   |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Format tables to pad column width, column alignment and spanning columns                        |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto insert empty table row on <kbd>ENTER</kbd>                                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty table row/column on <kbd>BACKSPACE</kbd>                                      |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto insert table column when typing before first column or after last column of table          |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Actions to insert: table, row or column; delete: row or column                                  |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto insert list item on <kbd>ENTER</kbd>                                                       |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty list item on <kbd>ENTER</kbd>                                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty list item on <kbd>BACKSPACE</kbd>                                             |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Indent or un-indent list item toolbar buttons and actions                                       |       |    X     |
| **Code Completions**                                                                                                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Absolute link address completions using https:// and file:// formats                            |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Explicit and Image links are GitHub wiki aware                                                  |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;GitHub Issue # Completions after `issues/` link address and in text                             |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;GitHub special links: Issues, Pull requests, Graphs, and Pulse.                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Link address completions for non-markdown files                                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Emoji text shortcuts completion                                                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Java class, field and method completions in inline code elements                                |       |    X     |
| **Intention Actions**                                                                                                   |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Change between relative and absolute https:// link addresses via intention action               |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Change between wiki links and explicit link                                                     |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Intentions for links, wiki links, references and headers                                        |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Intention to format Setext Header marker to match marker length to text                         |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Intention to swap Setext/Atx header format                                                      |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Update table of contents quick fix intention                                                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Intention to edit Table of Contents style options dialog with preview                           |       |    X     |
| **Refactoring**                                                                                                         |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Automatic change from wiki link to explicit link when link target file is moved out of the wiki |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;File move refactoring of contained links. This completes the refactoring feature set            |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Refactoring for /, https:// and file:// absolute link addresses to project files                |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Refactoring of header text with update to referencing anchor link references                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Anchor link reference refactoring with update to referenced header text                         |       |    X     |
.
<table>
  <thead>
    <tr><th align="left">Feature</th><th align="center">Basic</th><th align="center">Enhanced</th></tr>
  </thead>
  <tbody>
    <tr><td align="left">Works with builds 143.2370 or newer, product version IDEA 15.0.6</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Preview Tab so you can see what the rendered markdown will look like on GitHub.</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Syntax highlighting</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Table syntax highlighting stripes rows and columns</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Support for Default and Darcula color schemes for preview tab</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Warning and Error Annotations to help you validate wiki link errors</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Link address completion for wiki links</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Quick Fixes for detected wiki link errors</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">GFM Task list extension <code>* [ ]</code> open task item and <code>* [x]</code> completed task item</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Line markers, Find usages, Go To Declaration for rapid navigation to wiki link targets</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Markdown extensions configuration to customize markdown dialects</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">GitHub wiki support makes maintaining GitHub wiki pages easier.</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">GitHub compatible id generation for headers so you can validate your anchor references</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Swing and JavaFX WebView based preview.</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Supports <strong>JavaFX with JetBrains JRE on OS X</strong></td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Supports Highlight JS in WebView preview</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left"><strong>Multi-line Image URLs for embedding [gravizo.com] UML diagrams into markdown</strong></td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Live Templates for common markdown elements</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left"><strong>Enhanced Version Benefits</strong></td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Split Editor with Preview or HTML Text modes to view both source and preview</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Toolbar for fast access to frequent operations</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Language Injections for fenced code, HTML, Jekyll front matter and multi-line URL content</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Code completions, refactoring, annotations and quick fixes to let you work faster</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Navigation support with Line markers, Find usages, Go To Declaration for rapid navigation</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Inspections to help you validate links, anchor refs, footnote refs</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Complete GitHub wiki support for all links makes maintaining GitHub wiki pages a breeze</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Jekyll front matter recognition in markdown documents</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Emoji text to icon conversion using [Emoji Cheat Sheet] or GitHub emoji URLs</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Wrap on typing and table formatting with column alignment</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Character display width used for wrapping and table formatting</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Structure view for Abbreviations, Headers, Tables, Footnotes, References and Document</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Document formatting with text wrapping, list renumbering, aranging of elements, etc.</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Table of Contents generation for any markdown parser, with many style options</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left"><strong>As you type automation</strong></td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Double of bold/emphasis markers and remove inserted ones if a space is typed</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Wrap text blocks to margins and indentation</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    ATX headers to match trailing <code>#</code> marker</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Setext headers to match marker length to text</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Format tables to pad column width, column alignment and spanning columns</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Auto insert empty table row on <kbd>ENTER</kbd></td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Auto delete empty table row/column on <kbd>BACKSPACE</kbd></td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Auto insert table column when typing before first column or after last column of table</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Actions to insert: table, row or column; delete: row or column</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Auto insert list item on <kbd>ENTER</kbd></td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Auto delete empty list item on <kbd>ENTER</kbd></td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Auto delete empty list item on <kbd>BACKSPACE</kbd></td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Indent or un-indent list item toolbar buttons and actions</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left"><strong>Code Completions</strong></td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Absolute link address completions using https:// and file:// formats</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Explicit and Image links are GitHub wiki aware</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    GitHub Issue # Completions after <code>issues/</code> link address and in text</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    GitHub special links: Issues, Pull requests, Graphs, and Pulse.</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Link address completions for non-markdown files</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Emoji text shortcuts completion</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Java class, field and method completions in inline code elements</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left"><strong>Intention Actions</strong></td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Change between relative and absolute https:// link addresses via intention action</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Change between wiki links and explicit link</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Intentions for links, wiki links, references and headers</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Intention to format Setext Header marker to match marker length to text</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Intention to swap Setext/Atx header format</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Update table of contents quick fix intention</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Intention to edit Table of Contents style options dialog with preview</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left"><strong>Refactoring</strong></td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Automatic change from wiki link to explicit link when link target file is moved out of the wiki</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    File move refactoring of contained links. This completes the refactoring feature set</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Refactoring for /, https:// and file:// absolute link addresses to project files</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Refactoring of header text with update to referencing anchor link references</td><td align="center"> </td><td align="center">X</td></tr>
    <tr><td align="left">    Anchor link reference refactoring with update to referenced header text</td><td align="center"> </td><td align="center">X</td></tr>
  </tbody>
</table>
.
Document[0, 10152]
  TableBlock[0, 10152]
    TableHead[0, 142]
      TableRow[0, 142] rowNumber=1
        TableCell[0, 123] LEFT header textOpen:[0, 1, "|"] text:[2, 9, "Feature"] textClose:[122, 123, "|"]
          Text[2, 9] chars:[2, 9, "Feature"]
        TableCell[123, 131] CENTER header text:[124, 129, "Basic"] textClose:[130, 131, "|"]
          Text[124, 129] chars:[124, 129, "Basic"]
        TableCell[131, 142] CENTER header text:[132, 140, "Enhanced"] textClose:[141, 142, "|"]
          Text[132, 140] chars:[132, 140, "Enhanced"]
    TableSeparator[143, 285]
      TableRow[143, 285]
        TableCell[143, 266] LEFT textOpen:[143, 144, "|"] text:[144, 265, ":------------------------------------------------------------------------------------------------------------------------"] textClose:[265, 266, "|"]
          Text[144, 265] chars:[144, 265, ":---- … -----"]
        TableCell[266, 274] CENTER text:[266, 273, ":-----:"] textClose:[273, 274, "|"]
          Text[266, 273] chars:[266, 273, ":-----:"]
        TableCell[274, 285] CENTER text:[274, 284, ":--------:"] textClose:[284, 285, "|"]
          Text[274, 284] chars:[274, 284, ":--------:"]
    TableBody[286, 10152]
      TableRow[286, 428] rowNumber=1
        TableCell[286, 409] LEFT textOpen:[286, 287, "|"] text:[288, 352, "Works with builds 143.2370 or newer, product version IDEA 15.0.6"] textClose:[408, 409, "|"]
          Text[288, 352] chars:[288, 352, "Works … 5.0.6"]
        TableCell[409, 417] CENTER text:[412, 413, "X"] textClose:[416, 417, "|"]
          Text[412, 413] chars:[412, 413, "X"]
        TableCell[417, 428] CENTER text:[421, 422, "X"] textClose:[427, 428, "|"]
          Text[421, 422] chars:[421, 422, "X"]
      TableRow[429, 571] rowNumber=2
        TableCell[429, 552] LEFT textOpen:[429, 430, "|"] text:[431, 510, "Preview Tab so you can see what the rendered markdown will look like on GitHub."] textClose:[551, 552, "|"]
          Text[431, 510] chars:[431, 510, "Previ … tHub."]
        TableCell[552, 560] CENTER text:[555, 556, "X"] textClose:[559, 560, "|"]
          Text[555, 556] chars:[555, 556, "X"]
        TableCell[560, 571] CENTER text:[564, 565, "X"] textClose:[570, 571, "|"]
          Text[564, 565] chars:[564, 565, "X"]
      TableRow[572, 714] rowNumber=3
        TableCell[572, 695] LEFT textOpen:[572, 573, "|"] text:[574, 593, "Syntax highlighting"] textClose:[694, 695, "|"]
          Text[574, 593] chars:[574, 593, "Synta … hting"]
        TableCell[695, 703] CENTER text:[698, 699, "X"] textClose:[702, 703, "|"]
          Text[698, 699] chars:[698, 699, "X"]
        TableCell[703, 714] CENTER text:[707, 708, "X"] textClose:[713, 714, "|"]
          Text[707, 708] chars:[707, 708, "X"]
      TableRow[715, 857] rowNumber=4
        TableCell[715, 838] LEFT textOpen:[715, 716, "|"] text:[717, 767, "Table syntax highlighting stripes rows and columns"] textClose:[837, 838, "|"]
          Text[717, 767] chars:[717, 767, "Table … lumns"]
        TableCell[838, 846] CENTER text:[841, 842, "X"] textClose:[845, 846, "|"]
          Text[841, 842] chars:[841, 842, "X"]
        TableCell[846, 857] CENTER text:[850, 851, "X"] textClose:[856, 857, "|"]
          Text[850, 851] chars:[850, 851, "X"]
      TableRow[858, 1000] rowNumber=5
        TableCell[858, 981] LEFT textOpen:[858, 859, "|"] text:[860, 921, "Support for Default and Darcula color schemes for preview tab"] textClose:[980, 981, "|"]
          Text[860, 921] chars:[860, 921, "Suppo … w tab"]
        TableCell[981, 989] CENTER text:[984, 985, "X"] textClose:[988, 989, "|"]
          Text[984, 985] chars:[984, 985, "X"]
        TableCell[989, 1000] CENTER text:[993, 994, "X"] textClose:[999, 1000, "|"]
          Text[993, 994] chars:[993, 994, "X"]
      TableRow[1001, 1143] rowNumber=6
        TableCell[1001, 1124] LEFT textOpen:[1001, 1002, "|"] text:[1003, 1070, "Warning and Error Annotations to help you validate wiki link errors"] textClose:[1123, 1124, "|"]
          Text[1003, 1070] chars:[1003, 1070, "Warni … rrors"]
        TableCell[1124, 1132] CENTER text:[1127, 1128, "X"] textClose:[1131, 1132, "|"]
          Text[1127, 1128] chars:[1127, 1128, "X"]
        TableCell[1132, 1143] CENTER text:[1136, 1137, "X"] textClose:[1142, 1143, "|"]
          Text[1136, 1137] chars:[1136, 1137, "X"]
      TableRow[1144, 1286] rowNumber=7
        TableCell[1144, 1267] LEFT textOpen:[1144, 1145, "|"] text:[1146, 1184, "Link address completion for wiki links"] textClose:[1266, 1267, "|"]
          Text[1146, 1184] chars:[1146, 1184, "Link  … links"]
        TableCell[1267, 1275] CENTER text:[1270, 1271, "X"] textClose:[1274, 1275, "|"]
          Text[1270, 1271] chars:[1270, 1271, "X"]
        TableCell[1275, 1286] CENTER text:[1279, 1280, "X"] textClose:[1285, 1286, "|"]
          Text[1279, 1280] chars:[1279, 1280, "X"]
      TableRow[1287, 1429] rowNumber=8
        TableCell[1287, 1410] LEFT textOpen:[1287, 1288, "|"] text:[1289, 1330, "Quick Fixes for detected wiki link errors"] textClose:[1409, 1410, "|"]
          Text[1289, 1330] chars:[1289, 1330, "Quick … rrors"]
        TableCell[1410, 1418] CENTER text:[1413, 1414, "X"] textClose:[1417, 1418, "|"]
          Text[1413, 1414] chars:[1413, 1414, "X"]
        TableCell[1418, 1429] CENTER text:[1422, 1423, "X"] textClose:[1428, 1429, "|"]
          Text[1422, 1423] chars:[1422, 1423, "X"]
      TableRow[1430, 1572] rowNumber=9
        TableCell[1430, 1553] LEFT textOpen:[1430, 1431, "|"] text:[1432, 1510, "GFM Task list extension `* [ ]` open task item and `* [x]` completed task item"] textClose:[1552, 1553, "|"]
          Text[1432, 1456] chars:[1432, 1456, "GFM T … sion "]
          Code[1456, 1463] textOpen:[1456, 1457, "`"] text:[1457, 1462, "* [ ]"] textClose:[1462, 1463, "`"]
            Text[1457, 1462] chars:[1457, 1462, "* [ ]"]
          Text[1463, 1483] chars:[1463, 1483, " open …  and "]
          Code[1483, 1490] textOpen:[1483, 1484, "`"] text:[1484, 1489, "* [x]"] textClose:[1489, 1490, "`"]
            Text[1484, 1489] chars:[1484, 1489, "* [x]"]
          Text[1490, 1510] chars:[1490, 1510, " comp …  item"]
        TableCell[1553, 1561] CENTER text:[1556, 1557, "X"] textClose:[1560, 1561, "|"]
          Text[1556, 1557] chars:[1556, 1557, "X"]
        TableCell[1561, 1572] CENTER text:[1565, 1566, "X"] textClose:[1571, 1572, "|"]
          Text[1565, 1566] chars:[1565, 1566, "X"]
      TableRow[1573, 1715] rowNumber=10
        TableCell[1573, 1696] LEFT textOpen:[1573, 1574, "|"] text:[1575, 1661, "Line markers, Find usages, Go To Declaration for rapid navigation to wiki link targets"] textClose:[1695, 1696, "|"]
          Text[1575, 1661] chars:[1575, 1661, "Line  … rgets"]
        TableCell[1696, 1704] CENTER text:[1699, 1700, "X"] textClose:[1703, 1704, "|"]
          Text[1699, 1700] chars:[1699, 1700, "X"]
        TableCell[1704, 1715] CENTER text:[1708, 1709, "X"] textClose:[1714, 1715, "|"]
          Text[1708, 1709] chars:[1708, 1709, "X"]
      TableRow[1716, 1858] rowNumber=11
        TableCell[1716, 1839] LEFT textOpen:[1716, 1717, "|"] text:[1718, 1782, "Markdown extensions configuration to customize markdown dialects"] textClose:[1838, 1839, "|"]
          Text[1718, 1782] chars:[1718, 1782, "Markd … lects"]
        TableCell[1839, 1847] CENTER text:[1842, 1843, "X"] textClose:[1846, 1847, "|"]
          Text[1842, 1843] chars:[1842, 1843, "X"]
        TableCell[1847, 1858] CENTER text:[1851, 1852, "X"] textClose:[1857, 1858, "|"]
          Text[1851, 1852] chars:[1851, 1852, "X"]
      TableRow[1859, 2001] rowNumber=12
        TableCell[1859, 1982] LEFT textOpen:[1859, 1860, "|"] text:[1861, 1924, "GitHub wiki support makes maintaining GitHub wiki pages easier."] textClose:[1981, 1982, "|"]
          Text[1861, 1924] chars:[1861, 1924, "GitHu … sier."]
        TableCell[1982, 1990] CENTER text:[1985, 1986, "X"] textClose:[1989, 1990, "|"]
          Text[1985, 1986] chars:[1985, 1986, "X"]
        TableCell[1990, 2001] CENTER text:[1994, 1995, "X"] textClose:[2000, 2001, "|"]
          Text[1994, 1995] chars:[1994, 1995, "X"]
      TableRow[2002, 2144] rowNumber=13
        TableCell[2002, 2125] LEFT textOpen:[2002, 2003, "|"] text:[2004, 2090, "GitHub compatible id generation for headers so you can validate your anchor references"] textClose:[2124, 2125, "|"]
          Text[2004, 2090] chars:[2004, 2090, "GitHu … ences"]
        TableCell[2125, 2133] CENTER text:[2128, 2129, "X"] textClose:[2132, 2133, "|"]
          Text[2128, 2129] chars:[2128, 2129, "X"]
        TableCell[2133, 2144] CENTER text:[2137, 2138, "X"] textClose:[2143, 2144, "|"]
          Text[2137, 2138] chars:[2137, 2138, "X"]
      TableRow[2145, 2287] rowNumber=14
        TableCell[2145, 2268] LEFT textOpen:[2145, 2146, "|"] text:[2147, 2186, "Swing and JavaFX WebView based preview."] textClose:[2267, 2268, "|"]
          Text[2147, 2186] chars:[2147, 2186, "Swing … view."]
        TableCell[2268, 2276] CENTER text:[2271, 2272, "X"] textClose:[2275, 2276, "|"]
          Text[2271, 2272] chars:[2271, 2272, "X"]
        TableCell[2276, 2287] CENTER text:[2280, 2281, "X"] textClose:[2286, 2287, "|"]
          Text[2280, 2281] chars:[2280, 2281, "X"]
      TableRow[2288, 2430] rowNumber=15
        TableCell[2288, 2411] LEFT textOpen:[2288, 2289, "|"] text:[2290, 2336, "Supports **JavaFX with JetBrains JRE on OS X**"] textClose:[2410, 2411, "|"]
          Text[2290, 2299] chars:[2290, 2299, "Supports "]
          StrongEmphasis[2299, 2336] textOpen:[2299, 2301, "**"] text:[2301, 2334, "JavaFX with JetBrains JRE on OS X"] textClose:[2334, 2336, "**"]
            Text[2301, 2334] chars:[2301, 2334, "JavaF …  OS X"]
          Text[2336, 2336]
        TableCell[2411, 2419] CENTER text:[2414, 2415, "X"] textClose:[2418, 2419, "|"]
          Text[2414, 2415] chars:[2414, 2415, "X"]
        TableCell[2419, 2430] CENTER text:[2423, 2424, "X"] textClose:[2429, 2430, "|"]
          Text[2423, 2424] chars:[2423, 2424, "X"]
      TableRow[2431, 2573] rowNumber=16
        TableCell[2431, 2554] LEFT textOpen:[2431, 2432, "|"] text:[2433, 2473, "Supports Highlight JS in WebView preview"] textClose:[2553, 2554, "|"]
          Text[2433, 2473] chars:[2433, 2473, "Suppo … eview"]
        TableCell[2554, 2562] CENTER text:[2557, 2558, "X"] textClose:[2561, 2562, "|"]
          Text[2557, 2558] chars:[2557, 2558, "X"]
        TableCell[2562, 2573] CENTER text:[2566, 2567, "X"] textClose:[2572, 2573, "|"]
          Text[2566, 2567] chars:[2566, 2567, "X"]
      TableRow[2574, 2716] rowNumber=17
        TableCell[2574, 2697] LEFT textOpen:[2574, 2575, "|"] text:[2576, 2656, "**Multi-line Image URLs for embedding [gravizo.com] UML diagrams into markdown**"] textClose:[2696, 2697, "|"]
          StrongEmphasis[2576, 2656] textOpen:[2576, 2578, "**"] text:[2578, 2654, "Multi-line Image URLs for embedding [gravizo.com] UML diagrams into markdown"] textClose:[2654, 2656, "**"]
            Text[2578, 2614] chars:[2578, 2614, "Multi … ding "]
            LinkRef[2614, 2627] referenceOpen:[2614, 2615, "["] reference:[2615, 2626, "gravizo.com"] referenceClose:[2626, 2627, "]"]
              Text[2615, 2626] chars:[2615, 2626, "gravi … o.com"]
            Text[2627, 2654] chars:[2627, 2654, " UML  … kdown"]
          Text[2656, 2656]
        TableCell[2697, 2705] CENTER text:[2700, 2701, "X"] textClose:[2704, 2705, "|"]
          Text[2700, 2701] chars:[2700, 2701, "X"]
        TableCell[2705, 2716] CENTER text:[2709, 2710, "X"] textClose:[2715, 2716, "|"]
          Text[2709, 2710] chars:[2709, 2710, "X"]
      TableRow[2717, 2859] rowNumber=18
        TableCell[2717, 2840] LEFT textOpen:[2717, 2718, "|"] text:[2719, 2762, "Live Templates for common markdown elements"] textClose:[2839, 2840, "|"]
          Text[2719, 2762] chars:[2719, 2762, "Live  … ments"]
        TableCell[2840, 2848] CENTER text:[2843, 2844, "X"] textClose:[2847, 2848, "|"]
          Text[2843, 2844] chars:[2843, 2844, "X"]
        TableCell[2848, 2859] CENTER text:[2852, 2853, "X"] textClose:[2858, 2859, "|"]
          Text[2852, 2853] chars:[2852, 2853, "X"]
      TableRow[2860, 3002] rowNumber=19
        TableCell[2860, 2983] LEFT textOpen:[2860, 2861, "|"] text:[2862, 2891, "**Enhanced Version Benefits**"] textClose:[2982, 2983, "|"]
          StrongEmphasis[2862, 2891] textOpen:[2862, 2864, "**"] text:[2864, 2889, "Enhanced Version Benefits"] textClose:[2889, 2891, "**"]
            Text[2864, 2889] chars:[2864, 2889, "Enhan … efits"]
          Text[2891, 2891]
        TableCell[2983, 2991] CENTER text:[2983, 2984, " "] textClose:[2990, 2991, "|"]
          Text[2983, 2984] chars:[2983, 2984, " "]
        TableCell[2991, 3002] CENTER text:[2995, 2996, "X"] textClose:[3001, 3002, "|"]
          Text[2995, 2996] chars:[2995, 2996, "X"]
      TableRow[3003, 3145] rowNumber=20
        TableCell[3003, 3126] LEFT textOpen:[3003, 3004, "|"] text:[3005, 3105, "&nbsp;&nbsp;&nbsp;&nbsp;Split Editor with Preview or HTML Text modes to view both source and preview"] textClose:[3125, 3126, "|"]
          HtmlEntity[3005, 3011] "&nbsp;"
          HtmlEntity[3011, 3017] "&nbsp;"
          HtmlEntity[3017, 3023] "&nbsp;"
          HtmlEntity[3023, 3029] "&nbsp;"
          Text[3029, 3105] chars:[3029, 3105, "Split … eview"]
        TableCell[3126, 3134] CENTER text:[3126, 3127, " "] textClose:[3133, 3134, "|"]
          Text[3126, 3127] chars:[3126, 3127, " "]
        TableCell[3134, 3145] CENTER text:[3138, 3139, "X"] textClose:[3144, 3145, "|"]
          Text[3138, 3139] chars:[3138, 3139, "X"]
      TableRow[3146, 3288] rowNumber=21
        TableCell[3146, 3269] LEFT textOpen:[3146, 3147, "|"] text:[3148, 3218, "&nbsp;&nbsp;&nbsp;&nbsp;Toolbar for fast access to frequent operations"] textClose:[3268, 3269, "|"]
          HtmlEntity[3148, 3154] "&nbsp;"
          HtmlEntity[3154, 3160] "&nbsp;"
          HtmlEntity[3160, 3166] "&nbsp;"
          HtmlEntity[3166, 3172] "&nbsp;"
          Text[3172, 3218] chars:[3172, 3218, "Toolb … tions"]
        TableCell[3269, 3277] CENTER text:[3269, 3270, " "] textClose:[3276, 3277, "|"]
          Text[3269, 3270] chars:[3269, 3270, " "]
        TableCell[3277, 3288] CENTER text:[3281, 3282, "X"] textClose:[3287, 3288, "|"]
          Text[3281, 3282] chars:[3281, 3282, "X"]
      TableRow[3289, 3431] rowNumber=22
        TableCell[3289, 3412] LEFT textOpen:[3289, 3290, "|"] text:[3291, 3404, "&nbsp;&nbsp;&nbsp;&nbsp;Language Injections for fenced code, HTML, Jekyll front matter and multi-line URL content"] textClose:[3411, 3412, "|"]
          HtmlEntity[3291, 3297] "&nbsp;"
          HtmlEntity[3297, 3303] "&nbsp;"
          HtmlEntity[3303, 3309] "&nbsp;"
          HtmlEntity[3309, 3315] "&nbsp;"
          Text[3315, 3404] chars:[3315, 3404, "Langu … ntent"]
        TableCell[3412, 3420] CENTER text:[3412, 3413, " "] textClose:[3419, 3420, "|"]
          Text[3412, 3413] chars:[3412, 3413, " "]
        TableCell[3420, 3431] CENTER text:[3424, 3425, "X"] textClose:[3430, 3431, "|"]
          Text[3424, 3425] chars:[3424, 3425, "X"]
      TableRow[3432, 3574] rowNumber=23
        TableCell[3432, 3555] LEFT textOpen:[3432, 3433, "|"] text:[3434, 3539, "&nbsp;&nbsp;&nbsp;&nbsp;Code completions, refactoring, annotations and quick fixes to let you work faster"] textClose:[3554, 3555, "|"]
          HtmlEntity[3434, 3440] "&nbsp;"
          HtmlEntity[3440, 3446] "&nbsp;"
          HtmlEntity[3446, 3452] "&nbsp;"
          HtmlEntity[3452, 3458] "&nbsp;"
          Text[3458, 3539] chars:[3458, 3539, "Code  … aster"]
        TableCell[3555, 3563] CENTER text:[3555, 3556, " "] textClose:[3562, 3563, "|"]
          Text[3555, 3556] chars:[3555, 3556, " "]
        TableCell[3563, 3574] CENTER text:[3567, 3568, "X"] textClose:[3573, 3574, "|"]
          Text[3567, 3568] chars:[3567, 3568, "X"]
      TableRow[3575, 3717] rowNumber=24
        TableCell[3575, 3698] LEFT textOpen:[3575, 3576, "|"] text:[3577, 3690, "&nbsp;&nbsp;&nbsp;&nbsp;Navigation support with Line markers, Find usages, Go To Declaration for rapid navigation"] textClose:[3697, 3698, "|"]
          HtmlEntity[3577, 3583] "&nbsp;"
          HtmlEntity[3583, 3589] "&nbsp;"
          HtmlEntity[3589, 3595] "&nbsp;"
          HtmlEntity[3595, 3601] "&nbsp;"
          Text[3601, 3690] chars:[3601, 3690, "Navig … ation"]
        TableCell[3698, 3706] CENTER text:[3698, 3699, " "] textClose:[3705, 3706, "|"]
          Text[3698, 3699] chars:[3698, 3699, " "]
        TableCell[3706, 3717] CENTER text:[3710, 3711, "X"] textClose:[3716, 3717, "|"]
          Text[3710, 3711] chars:[3710, 3711, "X"]
      TableRow[3718, 3860] rowNumber=25
        TableCell[3718, 3841] LEFT textOpen:[3718, 3719, "|"] text:[3720, 3810, "&nbsp;&nbsp;&nbsp;&nbsp;Inspections to help you validate links, anchor refs, footnote refs"] textClose:[3840, 3841, "|"]
          HtmlEntity[3720, 3726] "&nbsp;"
          HtmlEntity[3726, 3732] "&nbsp;"
          HtmlEntity[3732, 3738] "&nbsp;"
          HtmlEntity[3738, 3744] "&nbsp;"
          Text[3744, 3810] chars:[3744, 3810, "Inspe …  refs"]
        TableCell[3841, 3849] CENTER text:[3841, 3842, " "] textClose:[3848, 3849, "|"]
          Text[3841, 3842] chars:[3841, 3842, " "]
        TableCell[3849, 3860] CENTER text:[3853, 3854, "X"] textClose:[3859, 3860, "|"]
          Text[3853, 3854] chars:[3853, 3854, "X"]
      TableRow[3861, 4003] rowNumber=26
        TableCell[3861, 3984] LEFT textOpen:[3861, 3862, "|"] text:[3863, 3974, "&nbsp;&nbsp;&nbsp;&nbsp;Complete GitHub wiki support for all links makes maintaining GitHub wiki pages a breeze"] textClose:[3983, 3984, "|"]
          HtmlEntity[3863, 3869] "&nbsp;"
          HtmlEntity[3869, 3875] "&nbsp;"
          HtmlEntity[3875, 3881] "&nbsp;"
          HtmlEntity[3881, 3887] "&nbsp;"
          Text[3887, 3974] chars:[3887, 3974, "Compl … reeze"]
        TableCell[3984, 3992] CENTER text:[3984, 3985, " "] textClose:[3991, 3992, "|"]
          Text[3984, 3985] chars:[3984, 3985, " "]
        TableCell[3992, 4003] CENTER text:[3996, 3997, "X"] textClose:[4002, 4003, "|"]
          Text[3996, 3997] chars:[3996, 3997, "X"]
      TableRow[4004, 4146] rowNumber=27
        TableCell[4004, 4127] LEFT textOpen:[4004, 4005, "|"] text:[4006, 4083, "&nbsp;&nbsp;&nbsp;&nbsp;Jekyll front matter recognition in markdown documents"] textClose:[4126, 4127, "|"]
          HtmlEntity[4006, 4012] "&nbsp;"
          HtmlEntity[4012, 4018] "&nbsp;"
          HtmlEntity[4018, 4024] "&nbsp;"
          HtmlEntity[4024, 4030] "&nbsp;"
          Text[4030, 4083] chars:[4030, 4083, "Jekyl … ments"]
        TableCell[4127, 4135] CENTER text:[4127, 4128, " "] textClose:[4134, 4135, "|"]
          Text[4127, 4128] chars:[4127, 4128, " "]
        TableCell[4135, 4146] CENTER text:[4139, 4140, "X"] textClose:[4145, 4146, "|"]
          Text[4139, 4140] chars:[4139, 4140, "X"]
      TableRow[4147, 4289] rowNumber=28
        TableCell[4147, 4270] LEFT textOpen:[4147, 4148, "|"] text:[4149, 4249, "&nbsp;&nbsp;&nbsp;&nbsp;Emoji text to icon conversion using [Emoji Cheat Sheet] or GitHub emoji URLs"] textClose:[4269, 4270, "|"]
          HtmlEntity[4149, 4155] "&nbsp;"
          HtmlEntity[4155, 4161] "&nbsp;"
          HtmlEntity[4161, 4167] "&nbsp;"
          HtmlEntity[4167, 4173] "&nbsp;"
          Text[4173, 4209] chars:[4173, 4209, "Emoji … sing "]
          LinkRef[4209, 4228] referenceOpen:[4209, 4210, "["] reference:[4210, 4227, "Emoji Cheat Sheet"] referenceClose:[4227, 4228, "]"]
            Text[4210, 4227] chars:[4210, 4227, "Emoji … Sheet"]
          Text[4228, 4249] chars:[4228, 4249, " or G …  URLs"]
        TableCell[4270, 4278] CENTER text:[4270, 4271, " "] textClose:[4277, 4278, "|"]
          Text[4270, 4271] chars:[4270, 4271, " "]
        TableCell[4278, 4289] CENTER text:[4282, 4283, "X"] textClose:[4288, 4289, "|"]
          Text[4282, 4283] chars:[4282, 4283, "X"]
      TableRow[4290, 4432] rowNumber=29
        TableCell[4290, 4413] LEFT textOpen:[4290, 4291, "|"] text:[4292, 4373, "&nbsp;&nbsp;&nbsp;&nbsp;Wrap on typing and table formatting with column alignment"] textClose:[4412, 4413, "|"]
          HtmlEntity[4292, 4298] "&nbsp;"
          HtmlEntity[4298, 4304] "&nbsp;"
          HtmlEntity[4304, 4310] "&nbsp;"
          HtmlEntity[4310, 4316] "&nbsp;"
          Text[4316, 4373] chars:[4316, 4373, "Wrap  … nment"]
        TableCell[4413, 4421] CENTER text:[4413, 4414, " "] textClose:[4420, 4421, "|"]
          Text[4413, 4414] chars:[4413, 4414, " "]
        TableCell[4421, 4432] CENTER text:[4425, 4426, "X"] textClose:[4431, 4432, "|"]
          Text[4425, 4426] chars:[4425, 4426, "X"]
      TableRow[4433, 4575] rowNumber=30
        TableCell[4433, 4556] LEFT textOpen:[4433, 4434, "|"] text:[4435, 4521, "&nbsp;&nbsp;&nbsp;&nbsp;Character display width used for wrapping and table formatting"] textClose:[4555, 4556, "|"]
          HtmlEntity[4435, 4441] "&nbsp;"
          HtmlEntity[4441, 4447] "&nbsp;"
          HtmlEntity[4447, 4453] "&nbsp;"
          HtmlEntity[4453, 4459] "&nbsp;"
          Text[4459, 4521] chars:[4459, 4521, "Chara … tting"]
        TableCell[4556, 4564] CENTER text:[4556, 4557, " "] textClose:[4563, 4564, "|"]
          Text[4556, 4557] chars:[4556, 4557, " "]
        TableCell[4564, 4575] CENTER text:[4568, 4569, "X"] textClose:[4574, 4575, "|"]
          Text[4568, 4569] chars:[4568, 4569, "X"]
      TableRow[4576, 4718] rowNumber=31
        TableCell[4576, 4699] LEFT textOpen:[4576, 4577, "|"] text:[4578, 4687, "&nbsp;&nbsp;&nbsp;&nbsp;Structure view for Abbreviations, Headers, Tables, Footnotes, References and Document"] textClose:[4698, 4699, "|"]
          HtmlEntity[4578, 4584] "&nbsp;"
          HtmlEntity[4584, 4590] "&nbsp;"
          HtmlEntity[4590, 4596] "&nbsp;"
          HtmlEntity[4596, 4602] "&nbsp;"
          Text[4602, 4687] chars:[4602, 4687, "Struc … ument"]
        TableCell[4699, 4707] CENTER text:[4699, 4700, " "] textClose:[4706, 4707, "|"]
          Text[4699, 4700] chars:[4699, 4700, " "]
        TableCell[4707, 4718] CENTER text:[4711, 4712, "X"] textClose:[4717, 4718, "|"]
          Text[4711, 4712] chars:[4711, 4712, "X"]
      TableRow[4719, 4861] rowNumber=32
        TableCell[4719, 4842] LEFT textOpen:[4719, 4720, "|"] text:[4721, 4829, "&nbsp;&nbsp;&nbsp;&nbsp;Document formatting with text wrapping, list renumbering, aranging of elements, etc."] textClose:[4841, 4842, "|"]
          HtmlEntity[4721, 4727] "&nbsp;"
          HtmlEntity[4727, 4733] "&nbsp;"
          HtmlEntity[4733, 4739] "&nbsp;"
          HtmlEntity[4739, 4745] "&nbsp;"
          Text[4745, 4829] chars:[4745, 4829, "Docum …  etc."]
        TableCell[4842, 4850] CENTER text:[4842, 4843, " "] textClose:[4849, 4850, "|"]
          Text[4842, 4843] chars:[4842, 4843, " "]
        TableCell[4850, 4861] CENTER text:[4854, 4855, "X"] textClose:[4860, 4861, "|"]
          Text[4854, 4855] chars:[4854, 4855, "X"]
      TableRow[4862, 5004] rowNumber=33
        TableCell[4862, 4985] LEFT textOpen:[4862, 4863, "|"] text:[4864, 4965, "&nbsp;&nbsp;&nbsp;&nbsp;Table of Contents generation for any markdown parser, with many style options"] textClose:[4984, 4985, "|"]
          HtmlEntity[4864, 4870] "&nbsp;"
          HtmlEntity[4870, 4876] "&nbsp;"
          HtmlEntity[4876, 4882] "&nbsp;"
          HtmlEntity[4882, 4888] "&nbsp;"
          Text[4888, 4965] chars:[4888, 4965, "Table … tions"]
        TableCell[4985, 4993] CENTER text:[4985, 4986, " "] textClose:[4992, 4993, "|"]
          Text[4985, 4986] chars:[4985, 4986, " "]
        TableCell[4993, 5004] CENTER text:[4997, 4998, "X"] textClose:[5003, 5004, "|"]
          Text[4997, 4998] chars:[4997, 4998, "X"]
      TableRow[5005, 5147] rowNumber=34
        TableCell[5005, 5128] LEFT textOpen:[5005, 5006, "|"] text:[5007, 5033, "**As you type automation**"] textClose:[5127, 5128, "|"]
          StrongEmphasis[5007, 5033] textOpen:[5007, 5009, "**"] text:[5009, 5031, "As you type automation"] textClose:[5031, 5033, "**"]
            Text[5009, 5031] chars:[5009, 5031, "As yo … ation"]
          Text[5033, 5033]
        TableCell[5128, 5136] CENTER text:[5128, 5129, " "] textClose:[5135, 5136, "|"]
          Text[5128, 5129] chars:[5128, 5129, " "]
        TableCell[5136, 5147] CENTER text:[5140, 5141, "X"] textClose:[5146, 5147, "|"]
          Text[5140, 5141] chars:[5140, 5141, "X"]
      TableRow[5148, 5290] rowNumber=35
        TableCell[5148, 5271] LEFT textOpen:[5148, 5149, "|"] text:[5150, 5250, "&nbsp;&nbsp;&nbsp;&nbsp;Double of bold/emphasis markers and remove inserted ones if a space is typed"] textClose:[5270, 5271, "|"]
          HtmlEntity[5150, 5156] "&nbsp;"
          HtmlEntity[5156, 5162] "&nbsp;"
          HtmlEntity[5162, 5168] "&nbsp;"
          HtmlEntity[5168, 5174] "&nbsp;"
          Text[5174, 5250] chars:[5174, 5250, "Doubl … typed"]
        TableCell[5271, 5279] CENTER text:[5271, 5272, " "] textClose:[5278, 5279, "|"]
          Text[5271, 5272] chars:[5271, 5272, " "]
        TableCell[5279, 5290] CENTER text:[5283, 5284, "X"] textClose:[5289, 5290, "|"]
          Text[5283, 5284] chars:[5283, 5284, "X"]
      TableRow[5291, 5433] rowNumber=36
        TableCell[5291, 5414] LEFT textOpen:[5291, 5292, "|"] text:[5293, 5360, "&nbsp;&nbsp;&nbsp;&nbsp;Wrap text blocks to margins and indentation"] textClose:[5413, 5414, "|"]
          HtmlEntity[5293, 5299] "&nbsp;"
          HtmlEntity[5299, 5305] "&nbsp;"
          HtmlEntity[5305, 5311] "&nbsp;"
          HtmlEntity[5311, 5317] "&nbsp;"
          Text[5317, 5360] chars:[5317, 5360, "Wrap  … ation"]
        TableCell[5414, 5422] CENTER text:[5414, 5415, " "] textClose:[5421, 5422, "|"]
          Text[5414, 5415] chars:[5414, 5415, " "]
        TableCell[5422, 5433] CENTER text:[5426, 5427, "X"] textClose:[5432, 5433, "|"]
          Text[5426, 5427] chars:[5426, 5427, "X"]
      TableRow[5434, 5576] rowNumber=37
        TableCell[5434, 5557] LEFT textOpen:[5434, 5435, "|"] text:[5436, 5500, "&nbsp;&nbsp;&nbsp;&nbsp;ATX headers to match trailing `#` marker"] textClose:[5556, 5557, "|"]
          HtmlEntity[5436, 5442] "&nbsp;"
          HtmlEntity[5442, 5448] "&nbsp;"
          HtmlEntity[5448, 5454] "&nbsp;"
          HtmlEntity[5454, 5460] "&nbsp;"
          Text[5460, 5490] chars:[5460, 5490, "ATX h … ling "]
          Code[5490, 5493] textOpen:[5490, 5491, "`"] text:[5491, 5492, "#"] textClose:[5492, 5493, "`"]
            Text[5491, 5492] chars:[5491, 5492, "#"]
          Text[5493, 5500] chars:[5493, 5500, " marker"]
        TableCell[5557, 5565] CENTER text:[5557, 5558, " "] textClose:[5564, 5565, "|"]
          Text[5557, 5558] chars:[5557, 5558, " "]
        TableCell[5565, 5576] CENTER text:[5569, 5570, "X"] textClose:[5575, 5576, "|"]
          Text[5569, 5570] chars:[5569, 5570, "X"]
      TableRow[5577, 5719] rowNumber=38
        TableCell[5577, 5700] LEFT textOpen:[5577, 5578, "|"] text:[5579, 5648, "&nbsp;&nbsp;&nbsp;&nbsp;Setext headers to match marker length to text"] textClose:[5699, 5700, "|"]
          HtmlEntity[5579, 5585] "&nbsp;"
          HtmlEntity[5585, 5591] "&nbsp;"
          HtmlEntity[5591, 5597] "&nbsp;"
          HtmlEntity[5597, 5603] "&nbsp;"
          Text[5603, 5648] chars:[5603, 5648, "Setex …  text"]
        TableCell[5700, 5708] CENTER text:[5700, 5701, " "] textClose:[5707, 5708, "|"]
          Text[5700, 5701] chars:[5700, 5701, " "]
        TableCell[5708, 5719] CENTER text:[5712, 5713, "X"] textClose:[5718, 5719, "|"]
          Text[5712, 5713] chars:[5712, 5713, "X"]
      TableRow[5720, 5862] rowNumber=39
        TableCell[5720, 5843] LEFT textOpen:[5720, 5721, "|"] text:[5722, 5818, "&nbsp;&nbsp;&nbsp;&nbsp;Format tables to pad column width, column alignment and spanning columns"] textClose:[5842, 5843, "|"]
          HtmlEntity[5722, 5728] "&nbsp;"
          HtmlEntity[5728, 5734] "&nbsp;"
          HtmlEntity[5734, 5740] "&nbsp;"
          HtmlEntity[5740, 5746] "&nbsp;"
          Text[5746, 5818] chars:[5746, 5818, "Forma … lumns"]
        TableCell[5843, 5851] CENTER text:[5843, 5844, " "] textClose:[5850, 5851, "|"]
          Text[5843, 5844] chars:[5843, 5844, " "]
        TableCell[5851, 5862] CENTER text:[5855, 5856, "X"] textClose:[5861, 5862, "|"]
          Text[5855, 5856] chars:[5855, 5856, "X"]
      TableRow[5863, 6005] rowNumber=40
        TableCell[5863, 5986] LEFT textOpen:[5863, 5864, "|"] text:[5865, 5936, "&nbsp;&nbsp;&nbsp;&nbsp;Auto insert empty table row on <kbd>ENTER</kbd>"] textClose:[5985, 5986, "|"]
          HtmlEntity[5865, 5871] "&nbsp;"
          HtmlEntity[5871, 5877] "&nbsp;"
          HtmlEntity[5877, 5883] "&nbsp;"
          HtmlEntity[5883, 5889] "&nbsp;"
          Text[5889, 5920] chars:[5889, 5920, "Auto  … w on "]
          HtmlInline[5920, 5925] chars:[5920, 5925, "<kbd>"]
          Text[5925, 5930] chars:[5925, 5930, "ENTER"]
          HtmlInline[5930, 5936] chars:[5930, 5936, "</kbd>"]
          Text[5936, 5936]
        TableCell[5986, 5994] CENTER text:[5986, 5987, " "] textClose:[5993, 5994, "|"]
          Text[5986, 5987] chars:[5986, 5987, " "]
        TableCell[5994, 6005] CENTER text:[5998, 5999, "X"] textClose:[6004, 6005, "|"]
          Text[5998, 5999] chars:[5998, 5999, "X"]
      TableRow[6006, 6148] rowNumber=41
        TableCell[6006, 6129] LEFT textOpen:[6006, 6007, "|"] text:[6008, 6090, "&nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty table row/column on <kbd>BACKSPACE</kbd>"] textClose:[6128, 6129, "|"]
          HtmlEntity[6008, 6014] "&nbsp;"
          HtmlEntity[6014, 6020] "&nbsp;"
          HtmlEntity[6020, 6026] "&nbsp;"
          HtmlEntity[6026, 6032] "&nbsp;"
          Text[6032, 6070] chars:[6032, 6070, "Auto  … n on "]
          HtmlInline[6070, 6075] chars:[6070, 6075, "<kbd>"]
          Text[6075, 6084] chars:[6075, 6084, "BACKSPACE"]
          HtmlInline[6084, 6090] chars:[6084, 6090, "</kbd>"]
          Text[6090, 6090]
        TableCell[6129, 6137] CENTER text:[6129, 6130, " "] textClose:[6136, 6137, "|"]
          Text[6129, 6130] chars:[6129, 6130, " "]
        TableCell[6137, 6148] CENTER text:[6141, 6142, "X"] textClose:[6147, 6148, "|"]
          Text[6141, 6142] chars:[6141, 6142, "X"]
      TableRow[6149, 6291] rowNumber=42
        TableCell[6149, 6272] LEFT textOpen:[6149, 6150, "|"] text:[6151, 6261, "&nbsp;&nbsp;&nbsp;&nbsp;Auto insert table column when typing before first column or after last column of table"] textClose:[6271, 6272, "|"]
          HtmlEntity[6151, 6157] "&nbsp;"
          HtmlEntity[6157, 6163] "&nbsp;"
          HtmlEntity[6163, 6169] "&nbsp;"
          HtmlEntity[6169, 6175] "&nbsp;"
          Text[6175, 6261] chars:[6175, 6261, "Auto  … table"]
        TableCell[6272, 6280] CENTER text:[6272, 6273, " "] textClose:[6279, 6280, "|"]
          Text[6272, 6273] chars:[6272, 6273, " "]
        TableCell[6280, 6291] CENTER text:[6284, 6285, "X"] textClose:[6290, 6291, "|"]
          Text[6284, 6285] chars:[6284, 6285, "X"]
      TableRow[6292, 6434] rowNumber=43
        TableCell[6292, 6415] LEFT textOpen:[6292, 6293, "|"] text:[6294, 6380, "&nbsp;&nbsp;&nbsp;&nbsp;Actions to insert: table, row or column; delete: row or column"] textClose:[6414, 6415, "|"]
          HtmlEntity[6294, 6300] "&nbsp;"
          HtmlEntity[6300, 6306] "&nbsp;"
          HtmlEntity[6306, 6312] "&nbsp;"
          HtmlEntity[6312, 6318] "&nbsp;"
          Text[6318, 6380] chars:[6318, 6380, "Actio … olumn"]
        TableCell[6415, 6423] CENTER text:[6415, 6416, " "] textClose:[6422, 6423, "|"]
          Text[6415, 6416] chars:[6415, 6416, " "]
        TableCell[6423, 6434] CENTER text:[6427, 6428, "X"] textClose:[6433, 6434, "|"]
          Text[6427, 6428] chars:[6427, 6428, "X"]
      TableRow[6435, 6577] rowNumber=44
        TableCell[6435, 6558] LEFT textOpen:[6435, 6436, "|"] text:[6437, 6502, "&nbsp;&nbsp;&nbsp;&nbsp;Auto insert list item on <kbd>ENTER</kbd>"] textClose:[6557, 6558, "|"]
          HtmlEntity[6437, 6443] "&nbsp;"
          HtmlEntity[6443, 6449] "&nbsp;"
          HtmlEntity[6449, 6455] "&nbsp;"
          HtmlEntity[6455, 6461] "&nbsp;"
          Text[6461, 6486] chars:[6461, 6486, "Auto  … m on "]
          HtmlInline[6486, 6491] chars:[6486, 6491, "<kbd>"]
          Text[6491, 6496] chars:[6491, 6496, "ENTER"]
          HtmlInline[6496, 6502] chars:[6496, 6502, "</kbd>"]
          Text[6502, 6502]
        TableCell[6558, 6566] CENTER text:[6558, 6559, " "] textClose:[6565, 6566, "|"]
          Text[6558, 6559] chars:[6558, 6559, " "]
        TableCell[6566, 6577] CENTER text:[6570, 6571, "X"] textClose:[6576, 6577, "|"]
          Text[6570, 6571] chars:[6570, 6571, "X"]
      TableRow[6578, 6720] rowNumber=45
        TableCell[6578, 6701] LEFT textOpen:[6578, 6579, "|"] text:[6580, 6651, "&nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty list item on <kbd>ENTER</kbd>"] textClose:[6700, 6701, "|"]
          HtmlEntity[6580, 6586] "&nbsp;"
          HtmlEntity[6586, 6592] "&nbsp;"
          HtmlEntity[6592, 6598] "&nbsp;"
          HtmlEntity[6598, 6604] "&nbsp;"
          Text[6604, 6635] chars:[6604, 6635, "Auto  … m on "]
          HtmlInline[6635, 6640] chars:[6635, 6640, "<kbd>"]
          Text[6640, 6645] chars:[6640, 6645, "ENTER"]
          HtmlInline[6645, 6651] chars:[6645, 6651, "</kbd>"]
          Text[6651, 6651]
        TableCell[6701, 6709] CENTER text:[6701, 6702, " "] textClose:[6708, 6709, "|"]
          Text[6701, 6702] chars:[6701, 6702, " "]
        TableCell[6709, 6720] CENTER text:[6713, 6714, "X"] textClose:[6719, 6720, "|"]
          Text[6713, 6714] chars:[6713, 6714, "X"]
      TableRow[6721, 6863] rowNumber=46
        TableCell[6721, 6844] LEFT textOpen:[6721, 6722, "|"] text:[6723, 6798, "&nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty list item on <kbd>BACKSPACE</kbd>"] textClose:[6843, 6844, "|"]
          HtmlEntity[6723, 6729] "&nbsp;"
          HtmlEntity[6729, 6735] "&nbsp;"
          HtmlEntity[6735, 6741] "&nbsp;"
          HtmlEntity[6741, 6747] "&nbsp;"
          Text[6747, 6778] chars:[6747, 6778, "Auto  … m on "]
          HtmlInline[6778, 6783] chars:[6778, 6783, "<kbd>"]
          Text[6783, 6792] chars:[6783, 6792, "BACKSPACE"]
          HtmlInline[6792, 6798] chars:[6792, 6798, "</kbd>"]
          Text[6798, 6798]
        TableCell[6844, 6852] CENTER text:[6844, 6845, " "] textClose:[6851, 6852, "|"]
          Text[6844, 6845] chars:[6844, 6845, " "]
        TableCell[6852, 6863] CENTER text:[6856, 6857, "X"] textClose:[6862, 6863, "|"]
          Text[6856, 6857] chars:[6856, 6857, "X"]
      TableRow[6864, 7006] rowNumber=47
        TableCell[6864, 6987] LEFT textOpen:[6864, 6865, "|"] text:[6866, 6947, "&nbsp;&nbsp;&nbsp;&nbsp;Indent or un-indent list item toolbar buttons and actions"] textClose:[6986, 6987, "|"]
          HtmlEntity[6866, 6872] "&nbsp;"
          HtmlEntity[6872, 6878] "&nbsp;"
          HtmlEntity[6878, 6884] "&nbsp;"
          HtmlEntity[6884, 6890] "&nbsp;"
          Text[6890, 6947] chars:[6890, 6947, "Inden … tions"]
        TableCell[6987, 6995] CENTER text:[6987, 6988, " "] textClose:[6994, 6995, "|"]
          Text[6987, 6988] chars:[6987, 6988, " "]
        TableCell[6995, 7006] CENTER text:[6999, 7000, "X"] textClose:[7005, 7006, "|"]
          Text[6999, 7000] chars:[6999, 7000, "X"]
      TableRow[7007, 7149] rowNumber=48
        TableCell[7007, 7130] LEFT textOpen:[7007, 7008, "|"] text:[7009, 7029, "**Code Completions**"] textClose:[7129, 7130, "|"]
          StrongEmphasis[7009, 7029] textOpen:[7009, 7011, "**"] text:[7011, 7027, "Code Completions"] textClose:[7027, 7029, "**"]
            Text[7011, 7027] chars:[7011, 7027, "Code  … tions"]
          Text[7029, 7029]
        TableCell[7130, 7138] CENTER text:[7130, 7131, " "] textClose:[7137, 7138, "|"]
          Text[7130, 7131] chars:[7130, 7131, " "]
        TableCell[7138, 7149] CENTER text:[7142, 7143, "X"] textClose:[7148, 7149, "|"]
          Text[7142, 7143] chars:[7142, 7143, "X"]
      TableRow[7150, 7292] rowNumber=49
        TableCell[7150, 7273] LEFT textOpen:[7150, 7151, "|"] text:[7152, 7244, "&nbsp;&nbsp;&nbsp;&nbsp;Absolute link address completions using https:// and file:// formats"] textClose:[7272, 7273, "|"]
          HtmlEntity[7152, 7158] "&nbsp;"
          HtmlEntity[7158, 7164] "&nbsp;"
          HtmlEntity[7164, 7170] "&nbsp;"
          HtmlEntity[7170, 7176] "&nbsp;"
          Text[7176, 7244] chars:[7176, 7244, "Absol … rmats"]
        TableCell[7273, 7281] CENTER text:[7273, 7274, " "] textClose:[7280, 7281, "|"]
          Text[7273, 7274] chars:[7273, 7274, " "]
        TableCell[7281, 7292] CENTER text:[7285, 7286, "X"] textClose:[7291, 7292, "|"]
          Text[7285, 7286] chars:[7285, 7286, "X"]
      TableRow[7293, 7435] rowNumber=50
        TableCell[7293, 7416] LEFT textOpen:[7293, 7294, "|"] text:[7295, 7365, "&nbsp;&nbsp;&nbsp;&nbsp;Explicit and Image links are GitHub wiki aware"] textClose:[7415, 7416, "|"]
          HtmlEntity[7295, 7301] "&nbsp;"
          HtmlEntity[7301, 7307] "&nbsp;"
          HtmlEntity[7307, 7313] "&nbsp;"
          HtmlEntity[7313, 7319] "&nbsp;"
          Text[7319, 7365] chars:[7319, 7365, "Expli … aware"]
        TableCell[7416, 7424] CENTER text:[7416, 7417, " "] textClose:[7423, 7424, "|"]
          Text[7416, 7417] chars:[7416, 7417, " "]
        TableCell[7424, 7435] CENTER text:[7428, 7429, "X"] textClose:[7434, 7435, "|"]
          Text[7428, 7429] chars:[7428, 7429, "X"]
      TableRow[7436, 7578] rowNumber=51
        TableCell[7436, 7559] LEFT textOpen:[7436, 7437, "|"] text:[7438, 7529, "&nbsp;&nbsp;&nbsp;&nbsp;GitHub Issue # Completions after `issues/` link address and in text"] textClose:[7558, 7559, "|"]
          HtmlEntity[7438, 7444] "&nbsp;"
          HtmlEntity[7444, 7450] "&nbsp;"
          HtmlEntity[7450, 7456] "&nbsp;"
          HtmlEntity[7456, 7462] "&nbsp;"
          Text[7462, 7495] chars:[7462, 7495, "GitHu … fter "]
          Code[7495, 7504] textOpen:[7495, 7496, "`"] text:[7496, 7503, "issues/"] textClose:[7503, 7504, "`"]
            Text[7496, 7503] chars:[7496, 7503, "issues/"]
          Text[7504, 7529] chars:[7504, 7529, " link …  text"]
        TableCell[7559, 7567] CENTER text:[7559, 7560, " "] textClose:[7566, 7567, "|"]
          Text[7559, 7560] chars:[7559, 7560, " "]
        TableCell[7567, 7578] CENTER text:[7571, 7572, "X"] textClose:[7577, 7578, "|"]
          Text[7571, 7572] chars:[7571, 7572, "X"]
      TableRow[7579, 7721] rowNumber=52
        TableCell[7579, 7702] LEFT textOpen:[7579, 7580, "|"] text:[7581, 7668, "&nbsp;&nbsp;&nbsp;&nbsp;GitHub special links: Issues, Pull requests, Graphs, and Pulse."] textClose:[7701, 7702, "|"]
          HtmlEntity[7581, 7587] "&nbsp;"
          HtmlEntity[7587, 7593] "&nbsp;"
          HtmlEntity[7593, 7599] "&nbsp;"
          HtmlEntity[7599, 7605] "&nbsp;"
          Text[7605, 7668] chars:[7605, 7668, "GitHu … ulse."]
        TableCell[7702, 7710] CENTER text:[7702, 7703, " "] textClose:[7709, 7710, "|"]
          Text[7702, 7703] chars:[7702, 7703, " "]
        TableCell[7710, 7721] CENTER text:[7714, 7715, "X"] textClose:[7720, 7721, "|"]
          Text[7714, 7715] chars:[7714, 7715, "X"]
      TableRow[7722, 7864] rowNumber=53
        TableCell[7722, 7845] LEFT textOpen:[7722, 7723, "|"] text:[7724, 7795, "&nbsp;&nbsp;&nbsp;&nbsp;Link address completions for non-markdown files"] textClose:[7844, 7845, "|"]
          HtmlEntity[7724, 7730] "&nbsp;"
          HtmlEntity[7730, 7736] "&nbsp;"
          HtmlEntity[7736, 7742] "&nbsp;"
          HtmlEntity[7742, 7748] "&nbsp;"
          Text[7748, 7795] chars:[7748, 7795, "Link  … files"]
        TableCell[7845, 7853] CENTER text:[7845, 7846, " "] textClose:[7852, 7853, "|"]
          Text[7845, 7846] chars:[7845, 7846, " "]
        TableCell[7853, 7864] CENTER text:[7857, 7858, "X"] textClose:[7863, 7864, "|"]
          Text[7857, 7858] chars:[7857, 7858, "X"]
      TableRow[7865, 8007] rowNumber=54
        TableCell[7865, 7988] LEFT textOpen:[7865, 7866, "|"] text:[7867, 7922, "&nbsp;&nbsp;&nbsp;&nbsp;Emoji text shortcuts completion"] textClose:[7987, 7988, "|"]
          HtmlEntity[7867, 7873] "&nbsp;"
          HtmlEntity[7873, 7879] "&nbsp;"
          HtmlEntity[7879, 7885] "&nbsp;"
          HtmlEntity[7885, 7891] "&nbsp;"
          Text[7891, 7922] chars:[7891, 7922, "Emoji … etion"]
        TableCell[7988, 7996] CENTER text:[7988, 7989, " "] textClose:[7995, 7996, "|"]
          Text[7988, 7989] chars:[7988, 7989, " "]
        TableCell[7996, 8007] CENTER text:[8000, 8001, "X"] textClose:[8006, 8007, "|"]
          Text[8000, 8001] chars:[8000, 8001, "X"]
      TableRow[8008, 8150] rowNumber=55
        TableCell[8008, 8131] LEFT textOpen:[8008, 8009, "|"] text:[8010, 8098, "&nbsp;&nbsp;&nbsp;&nbsp;Java class, field and method completions in inline code elements"] textClose:[8130, 8131, "|"]
          HtmlEntity[8010, 8016] "&nbsp;"
          HtmlEntity[8016, 8022] "&nbsp;"
          HtmlEntity[8022, 8028] "&nbsp;"
          HtmlEntity[8028, 8034] "&nbsp;"
          Text[8034, 8098] chars:[8034, 8098, "Java  … ments"]
        TableCell[8131, 8139] CENTER text:[8131, 8132, " "] textClose:[8138, 8139, "|"]
          Text[8131, 8132] chars:[8131, 8132, " "]
        TableCell[8139, 8150] CENTER text:[8143, 8144, "X"] textClose:[8149, 8150, "|"]
          Text[8143, 8144] chars:[8143, 8144, "X"]
      TableRow[8151, 8293] rowNumber=56
        TableCell[8151, 8274] LEFT textOpen:[8151, 8152, "|"] text:[8153, 8174, "**Intention Actions**"] textClose:[8273, 8274, "|"]
          StrongEmphasis[8153, 8174] textOpen:[8153, 8155, "**"] text:[8155, 8172, "Intention Actions"] textClose:[8172, 8174, "**"]
            Text[8155, 8172] chars:[8155, 8172, "Inten … tions"]
          Text[8174, 8174]
        TableCell[8274, 8282] CENTER text:[8274, 8275, " "] textClose:[8281, 8282, "|"]
          Text[8274, 8275] chars:[8274, 8275, " "]
        TableCell[8282, 8293] CENTER text:[8286, 8287, "X"] textClose:[8292, 8293, "|"]
          Text[8286, 8287] chars:[8286, 8287, "X"]
      TableRow[8294, 8436] rowNumber=57
        TableCell[8294, 8417] LEFT textOpen:[8294, 8295, "|"] text:[8296, 8401, "&nbsp;&nbsp;&nbsp;&nbsp;Change between relative and absolute https:// link addresses via intention action"] textClose:[8416, 8417, "|"]
          HtmlEntity[8296, 8302] "&nbsp;"
          HtmlEntity[8302, 8308] "&nbsp;"
          HtmlEntity[8308, 8314] "&nbsp;"
          HtmlEntity[8314, 8320] "&nbsp;"
          Text[8320, 8401] chars:[8320, 8401, "Chang … ction"]
        TableCell[8417, 8425] CENTER text:[8417, 8418, " "] textClose:[8424, 8425, "|"]
          Text[8417, 8418] chars:[8417, 8418, " "]
        TableCell[8425, 8436] CENTER text:[8429, 8430, "X"] textClose:[8435, 8436, "|"]
          Text[8429, 8430] chars:[8429, 8430, "X"]
      TableRow[8437, 8579] rowNumber=58
        TableCell[8437, 8560] LEFT textOpen:[8437, 8438, "|"] text:[8439, 8506, "&nbsp;&nbsp;&nbsp;&nbsp;Change between wiki links and explicit link"] textClose:[8559, 8560, "|"]
          HtmlEntity[8439, 8445] "&nbsp;"
          HtmlEntity[8445, 8451] "&nbsp;"
          HtmlEntity[8451, 8457] "&nbsp;"
          HtmlEntity[8457, 8463] "&nbsp;"
          Text[8463, 8506] chars:[8463, 8506, "Chang …  link"]
        TableCell[8560, 8568] CENTER text:[8560, 8561, " "] textClose:[8567, 8568, "|"]
          Text[8560, 8561] chars:[8560, 8561, " "]
        TableCell[8568, 8579] CENTER text:[8572, 8573, "X"] textClose:[8578, 8579, "|"]
          Text[8572, 8573] chars:[8572, 8573, "X"]
      TableRow[8580, 8722] rowNumber=59
        TableCell[8580, 8703] LEFT textOpen:[8580, 8581, "|"] text:[8582, 8662, "&nbsp;&nbsp;&nbsp;&nbsp;Intentions for links, wiki links, references and headers"] textClose:[8702, 8703, "|"]
          HtmlEntity[8582, 8588] "&nbsp;"
          HtmlEntity[8588, 8594] "&nbsp;"
          HtmlEntity[8594, 8600] "&nbsp;"
          HtmlEntity[8600, 8606] "&nbsp;"
          Text[8606, 8662] chars:[8606, 8662, "Inten … aders"]
        TableCell[8703, 8711] CENTER text:[8703, 8704, " "] textClose:[8710, 8711, "|"]
          Text[8703, 8704] chars:[8703, 8704, " "]
        TableCell[8711, 8722] CENTER text:[8715, 8716, "X"] textClose:[8721, 8722, "|"]
          Text[8715, 8716] chars:[8715, 8716, "X"]
      TableRow[8723, 8865] rowNumber=60
        TableCell[8723, 8846] LEFT textOpen:[8723, 8724, "|"] text:[8725, 8820, "&nbsp;&nbsp;&nbsp;&nbsp;Intention to format Setext Header marker to match marker length to text"] textClose:[8845, 8846, "|"]
          HtmlEntity[8725, 8731] "&nbsp;"
          HtmlEntity[8731, 8737] "&nbsp;"
          HtmlEntity[8737, 8743] "&nbsp;"
          HtmlEntity[8743, 8749] "&nbsp;"
          Text[8749, 8820] chars:[8749, 8820, "Inten …  text"]
        TableCell[8846, 8854] CENTER text:[8846, 8847, " "] textClose:[8853, 8854, "|"]
          Text[8846, 8847] chars:[8846, 8847, " "]
        TableCell[8854, 8865] CENTER text:[8858, 8859, "X"] textClose:[8864, 8865, "|"]
          Text[8858, 8859] chars:[8858, 8859, "X"]
      TableRow[8866, 9008] rowNumber=61
        TableCell[8866, 8989] LEFT textOpen:[8866, 8867, "|"] text:[8868, 8934, "&nbsp;&nbsp;&nbsp;&nbsp;Intention to swap Setext/Atx header format"] textClose:[8988, 8989, "|"]
          HtmlEntity[8868, 8874] "&nbsp;"
          HtmlEntity[8874, 8880] "&nbsp;"
          HtmlEntity[8880, 8886] "&nbsp;"
          HtmlEntity[8886, 8892] "&nbsp;"
          Text[8892, 8934] chars:[8892, 8934, "Inten … ormat"]
        TableCell[8989, 8997] CENTER text:[8989, 8990, " "] textClose:[8996, 8997, "|"]
          Text[8989, 8990] chars:[8989, 8990, " "]
        TableCell[8997, 9008] CENTER text:[9001, 9002, "X"] textClose:[9007, 9008, "|"]
          Text[9001, 9002] chars:[9001, 9002, "X"]
      TableRow[9009, 9151] rowNumber=62
        TableCell[9009, 9132] LEFT textOpen:[9009, 9010, "|"] text:[9011, 9079, "&nbsp;&nbsp;&nbsp;&nbsp;Update table of contents quick fix intention"] textClose:[9131, 9132, "|"]
          HtmlEntity[9011, 9017] "&nbsp;"
          HtmlEntity[9017, 9023] "&nbsp;"
          HtmlEntity[9023, 9029] "&nbsp;"
          HtmlEntity[9029, 9035] "&nbsp;"
          Text[9035, 9079] chars:[9035, 9079, "Updat … ntion"]
        TableCell[9132, 9140] CENTER text:[9132, 9133, " "] textClose:[9139, 9140, "|"]
          Text[9132, 9133] chars:[9132, 9133, " "]
        TableCell[9140, 9151] CENTER text:[9144, 9145, "X"] textClose:[9150, 9151, "|"]
          Text[9144, 9145] chars:[9144, 9145, "X"]
      TableRow[9152, 9294] rowNumber=63
        TableCell[9152, 9275] LEFT textOpen:[9152, 9153, "|"] text:[9154, 9247, "&nbsp;&nbsp;&nbsp;&nbsp;Intention to edit Table of Contents style options dialog with preview"] textClose:[9274, 9275, "|"]
          HtmlEntity[9154, 9160] "&nbsp;"
          HtmlEntity[9160, 9166] "&nbsp;"
          HtmlEntity[9166, 9172] "&nbsp;"
          HtmlEntity[9172, 9178] "&nbsp;"
          Text[9178, 9247] chars:[9178, 9247, "Inten … eview"]
        TableCell[9275, 9283] CENTER text:[9275, 9276, " "] textClose:[9282, 9283, "|"]
          Text[9275, 9276] chars:[9275, 9276, " "]
        TableCell[9283, 9294] CENTER text:[9287, 9288, "X"] textClose:[9293, 9294, "|"]
          Text[9287, 9288] chars:[9287, 9288, "X"]
      TableRow[9295, 9437] rowNumber=64
        TableCell[9295, 9418] LEFT textOpen:[9295, 9296, "|"] text:[9297, 9312, "**Refactoring**"] textClose:[9417, 9418, "|"]
          StrongEmphasis[9297, 9312] textOpen:[9297, 9299, "**"] text:[9299, 9310, "Refactoring"] textClose:[9310, 9312, "**"]
            Text[9299, 9310] chars:[9299, 9310, "Refac … oring"]
          Text[9312, 9312]
        TableCell[9418, 9426] CENTER text:[9418, 9419, " "] textClose:[9425, 9426, "|"]
          Text[9418, 9419] chars:[9418, 9419, " "]
        TableCell[9426, 9437] CENTER text:[9430, 9431, "X"] textClose:[9436, 9437, "|"]
          Text[9430, 9431] chars:[9430, 9431, "X"]
      TableRow[9438, 9580] rowNumber=65
        TableCell[9438, 9561] LEFT textOpen:[9438, 9439, "|"] text:[9440, 9559, "&nbsp;&nbsp;&nbsp;&nbsp;Automatic change from wiki link to explicit link when link target file is moved out of the wiki"] textClose:[9560, 9561, "|"]
          HtmlEntity[9440, 9446] "&nbsp;"
          HtmlEntity[9446, 9452] "&nbsp;"
          HtmlEntity[9452, 9458] "&nbsp;"
          HtmlEntity[9458, 9464] "&nbsp;"
          Text[9464, 9559] chars:[9464, 9559, "Autom …  wiki"]
        TableCell[9561, 9569] CENTER text:[9561, 9562, " "] textClose:[9568, 9569, "|"]
          Text[9561, 9562] chars:[9561, 9562, " "]
        TableCell[9569, 9580] CENTER text:[9573, 9574, "X"] textClose:[9579, 9580, "|"]
          Text[9573, 9574] chars:[9573, 9574, "X"]
      TableRow[9581, 9723] rowNumber=66
        TableCell[9581, 9704] LEFT textOpen:[9581, 9582, "|"] text:[9583, 9691, "&nbsp;&nbsp;&nbsp;&nbsp;File move refactoring of contained links. This completes the refactoring feature set"] textClose:[9703, 9704, "|"]
          HtmlEntity[9583, 9589] "&nbsp;"
          HtmlEntity[9589, 9595] "&nbsp;"
          HtmlEntity[9595, 9601] "&nbsp;"
          HtmlEntity[9601, 9607] "&nbsp;"
          Text[9607, 9691] chars:[9607, 9691, "File  … e set"]
        TableCell[9704, 9712] CENTER text:[9704, 9705, " "] textClose:[9711, 9712, "|"]
          Text[9704, 9705] chars:[9704, 9705, " "]
        TableCell[9712, 9723] CENTER text:[9716, 9717, "X"] textClose:[9722, 9723, "|"]
          Text[9716, 9717] chars:[9716, 9717, "X"]
      TableRow[9724, 9866] rowNumber=67
        TableCell[9724, 9847] LEFT textOpen:[9724, 9725, "|"] text:[9726, 9830, "&nbsp;&nbsp;&nbsp;&nbsp;Refactoring for /, https:// and file:// absolute link addresses to project files"] textClose:[9846, 9847, "|"]
          HtmlEntity[9726, 9732] "&nbsp;"
          HtmlEntity[9732, 9738] "&nbsp;"
          HtmlEntity[9738, 9744] "&nbsp;"
          HtmlEntity[9744, 9750] "&nbsp;"
          Text[9750, 9830] chars:[9750, 9830, "Refac … files"]
        TableCell[9847, 9855] CENTER text:[9847, 9848, " "] textClose:[9854, 9855, "|"]
          Text[9847, 9848] chars:[9847, 9848, " "]
        TableCell[9855, 9866] CENTER text:[9859, 9860, "X"] textClose:[9865, 9866, "|"]
          Text[9859, 9860] chars:[9859, 9860, "X"]
      TableRow[9867, 10009] rowNumber=68
        TableCell[9867, 9990] LEFT textOpen:[9867, 9868, "|"] text:[9869, 9969, "&nbsp;&nbsp;&nbsp;&nbsp;Refactoring of header text with update to referencing anchor link references"] textClose:[9989, 9990, "|"]
          HtmlEntity[9869, 9875] "&nbsp;"
          HtmlEntity[9875, 9881] "&nbsp;"
          HtmlEntity[9881, 9887] "&nbsp;"
          HtmlEntity[9887, 9893] "&nbsp;"
          Text[9893, 9969] chars:[9893, 9969, "Refac … ences"]
        TableCell[9990, 9998] CENTER text:[9990, 9991, " "] textClose:[9997, 9998, "|"]
          Text[9990, 9991] chars:[9990, 9991, " "]
        TableCell[9998, 10009] CENTER text:[10002, 10003, "X"] textClose:[10008, 10009, "|"]
          Text[10002, 10003] chars:[10002, 10003, "X"]
      TableRow[10010, 10152] rowNumber=69
        TableCell[10010, 10133] LEFT textOpen:[10010, 10011, "|"] text:[10012, 10107, "&nbsp;&nbsp;&nbsp;&nbsp;Anchor link reference refactoring with update to referenced header text"] textClose:[10132, 10133, "|"]
          HtmlEntity[10012, 10018] "&nbsp;"
          HtmlEntity[10018, 10024] "&nbsp;"
          HtmlEntity[10024, 10030] "&nbsp;"
          HtmlEntity[10030, 10036] "&nbsp;"
          Text[10036, 10107] chars:[10036, 10107, "Ancho …  text"]
        TableCell[10133, 10141] CENTER text:[10133, 10134, " "] textClose:[10140, 10141, "|"]
          Text[10133, 10134] chars:[10133, 10134, " "]
        TableCell[10141, 10152] CENTER text:[10145, 10146, "X"] textClose:[10151, 10152, "|"]
          Text[10145, 10146] chars:[10145, 10146, "X"]
````````````````````````````````


## GFM options

invalid table:

```````````````````````````````` example(GFM options: 1) options(gfm)
| A | B | C |
|-----------|
| a | b | c |
| b | a | c |
.
<p>| A | B | C |
|-----------|
| a | b | c |
| b | a | c |</p>
.
Document[0, 55]
  Paragraph[0, 55]
    Text[0, 13] chars:[0, 13, "| A | … | C |"]
    SoftLineBreak[13, 14]
    Text[14, 27] chars:[14, 27, "|---- … ----|"]
    SoftLineBreak[27, 28]
    Text[28, 41] chars:[28, 41, "| a | … | c |"]
    SoftLineBreak[41, 42]
    Text[42, 55] chars:[42, 55, "| b | … | c |"]
````````````````````````````````


invalid table:

```````````````````````````````` example(GFM options: 2) options(gfm)
| A | B | C |
| a | b | c |
| b | a | c |
.
<p>| A | B | C |
| a | b | c |
| b | a | c |</p>
.
Document[0, 41]
  Paragraph[0, 41]
    Text[0, 13] chars:[0, 13, "| A | … | C |"]
    SoftLineBreak[13, 14]
    Text[14, 27] chars:[14, 27, "| a | … | c |"]
    SoftLineBreak[27, 28]
    Text[28, 41] chars:[28, 41, "| b | … | c |"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
| Feature                                                                                                                 | Basic | Enhanced |
|:------------------------------------------------------------------------------------------------------------------------|:-----:|:--------:|
| Works with builds 143.2370 or newer, product version IDEA 15.0.6                                                        |   X   |    X     |
| Preview Tab so you can see what the rendered markdown will look like on GitHub.                                         |   X   |    X     |
| Syntax highlighting                                                                                                     |   X   |    X     |
| Table syntax highlighting stripes rows and columns                                                                      |   X   |    X     |
| Support for Default and Darcula color schemes for preview tab                                                           |   X   |    X     |
| Warning and Error Annotations to help you validate wiki link errors                                                     |   X   |    X     |
.
<table md-pos="0-1143">
  <thead>
    <tr md-pos="0-142"><th align="left" md-pos="2-9">Feature</th><th align="center" md-pos="124-129">Basic</th><th align="center" md-pos="132-140">Enhanced</th></tr>
  </thead>
  <tbody>
    <tr md-pos="286-428"><td align="left" md-pos="288-352">Works with builds 143.2370 or newer, product version IDEA 15.0.6</td><td align="center" md-pos="412-413">X</td><td align="center" md-pos="421-422">X</td></tr>
    <tr md-pos="429-571"><td align="left" md-pos="431-510">Preview Tab so you can see what the rendered markdown will look like on GitHub.</td><td align="center" md-pos="555-556">X</td><td align="center" md-pos="564-565">X</td></tr>
    <tr md-pos="572-714"><td align="left" md-pos="574-593">Syntax highlighting</td><td align="center" md-pos="698-699">X</td><td align="center" md-pos="707-708">X</td></tr>
    <tr md-pos="715-857"><td align="left" md-pos="717-767">Table syntax highlighting stripes rows and columns</td><td align="center" md-pos="841-842">X</td><td align="center" md-pos="850-851">X</td></tr>
    <tr md-pos="858-1000"><td align="left" md-pos="860-921">Support for Default and Darcula color schemes for preview tab</td><td align="center" md-pos="984-985">X</td><td align="center" md-pos="993-994">X</td></tr>
    <tr md-pos="1001-1143"><td align="left" md-pos="1003-1070">Warning and Error Annotations to help you validate wiki link errors</td><td align="center" md-pos="1127-1128">X</td><td align="center" md-pos="1136-1137">X</td></tr>
  </tbody>
</table>
.
Document[0, 1143]
  TableBlock[0, 1143]
    TableHead[0, 142]
      TableRow[0, 142] rowNumber=1
        TableCell[0, 123] LEFT header textOpen:[0, 1, "|"] text:[2, 9, "Feature"] textClose:[122, 123, "|"]
          Text[2, 9] chars:[2, 9, "Feature"]
        TableCell[123, 131] CENTER header text:[124, 129, "Basic"] textClose:[130, 131, "|"]
          Text[124, 129] chars:[124, 129, "Basic"]
        TableCell[131, 142] CENTER header text:[132, 140, "Enhanced"] textClose:[141, 142, "|"]
          Text[132, 140] chars:[132, 140, "Enhanced"]
    TableSeparator[143, 285]
      TableRow[143, 285]
        TableCell[143, 266] LEFT textOpen:[143, 144, "|"] text:[144, 265, ":------------------------------------------------------------------------------------------------------------------------"] textClose:[265, 266, "|"]
          Text[144, 265] chars:[144, 265, ":---- … -----"]
        TableCell[266, 274] CENTER text:[266, 273, ":-----:"] textClose:[273, 274, "|"]
          Text[266, 273] chars:[266, 273, ":-----:"]
        TableCell[274, 285] CENTER text:[274, 284, ":--------:"] textClose:[284, 285, "|"]
          Text[274, 284] chars:[274, 284, ":--------:"]
    TableBody[286, 1143]
      TableRow[286, 428] rowNumber=1
        TableCell[286, 409] LEFT textOpen:[286, 287, "|"] text:[288, 352, "Works with builds 143.2370 or newer, product version IDEA 15.0.6"] textClose:[408, 409, "|"]
          Text[288, 352] chars:[288, 352, "Works … 5.0.6"]
        TableCell[409, 417] CENTER text:[412, 413, "X"] textClose:[416, 417, "|"]
          Text[412, 413] chars:[412, 413, "X"]
        TableCell[417, 428] CENTER text:[421, 422, "X"] textClose:[427, 428, "|"]
          Text[421, 422] chars:[421, 422, "X"]
      TableRow[429, 571] rowNumber=2
        TableCell[429, 552] LEFT textOpen:[429, 430, "|"] text:[431, 510, "Preview Tab so you can see what the rendered markdown will look like on GitHub."] textClose:[551, 552, "|"]
          Text[431, 510] chars:[431, 510, "Previ … tHub."]
        TableCell[552, 560] CENTER text:[555, 556, "X"] textClose:[559, 560, "|"]
          Text[555, 556] chars:[555, 556, "X"]
        TableCell[560, 571] CENTER text:[564, 565, "X"] textClose:[570, 571, "|"]
          Text[564, 565] chars:[564, 565, "X"]
      TableRow[572, 714] rowNumber=3
        TableCell[572, 695] LEFT textOpen:[572, 573, "|"] text:[574, 593, "Syntax highlighting"] textClose:[694, 695, "|"]
          Text[574, 593] chars:[574, 593, "Synta … hting"]
        TableCell[695, 703] CENTER text:[698, 699, "X"] textClose:[702, 703, "|"]
          Text[698, 699] chars:[698, 699, "X"]
        TableCell[703, 714] CENTER text:[707, 708, "X"] textClose:[713, 714, "|"]
          Text[707, 708] chars:[707, 708, "X"]
      TableRow[715, 857] rowNumber=4
        TableCell[715, 838] LEFT textOpen:[715, 716, "|"] text:[717, 767, "Table syntax highlighting stripes rows and columns"] textClose:[837, 838, "|"]
          Text[717, 767] chars:[717, 767, "Table … lumns"]
        TableCell[838, 846] CENTER text:[841, 842, "X"] textClose:[845, 846, "|"]
          Text[841, 842] chars:[841, 842, "X"]
        TableCell[846, 857] CENTER text:[850, 851, "X"] textClose:[856, 857, "|"]
          Text[850, 851] chars:[850, 851, "X"]
      TableRow[858, 1000] rowNumber=5
        TableCell[858, 981] LEFT textOpen:[858, 859, "|"] text:[860, 921, "Support for Default and Darcula color schemes for preview tab"] textClose:[980, 981, "|"]
          Text[860, 921] chars:[860, 921, "Suppo … w tab"]
        TableCell[981, 989] CENTER text:[984, 985, "X"] textClose:[988, 989, "|"]
          Text[984, 985] chars:[984, 985, "X"]
        TableCell[989, 1000] CENTER text:[993, 994, "X"] textClose:[999, 1000, "|"]
          Text[993, 994] chars:[993, 994, "X"]
      TableRow[1001, 1143] rowNumber=6
        TableCell[1001, 1124] LEFT textOpen:[1001, 1002, "|"] text:[1003, 1070, "Warning and Error Annotations to help you validate wiki link errors"] textClose:[1123, 1124, "|"]
          Text[1003, 1070] chars:[1003, 1070, "Warni … rrors"]
        TableCell[1124, 1132] CENTER text:[1127, 1128, "X"] textClose:[1131, 1132, "|"]
          Text[1127, 1128] chars:[1127, 1128, "X"]
        TableCell[1132, 1143] CENTER text:[1136, 1137, "X"] textClose:[1142, 1143, "|"]
          Text[1136, 1137] chars:[1136, 1137, "X"]
````````````````````````````````


in item

```````````````````````````````` example(Source Position Attribute: 2) options(src-pos, keep-whitespace)
- Add: live templates starting with `.`    
                                        
  | Element       | Abbreviation    | Expansion                                               |
  |---------------|-----------------|---------------------------------------------------------|
  | Abbreviation  | `.abbreviation` | `*[]: `                                                 |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\`                                       |
  | Explicit link | `.link`         | `[]()`                                                  |
.
<ul>
  <li md-pos="0-564">
    <p md-pos="2-44">Add: live templates starting with <code md-pos="37-38">.</code></p>
    <table md-pos="87-564">
      <thead>
        <tr md-pos="87-180"><th md-pos="88-103"> Element       </th><th md-pos="104-121"> Abbreviation    </th><th md-pos="122-179"> Expansion                                               </th></tr>
      </thead>
      <tbody>
        <tr md-pos="279-372"><td md-pos="280-295"> Abbreviation  </td><td md-pos="296-313"> <code md-pos="298-311">.abbreviation</code> </td><td md-pos="314-371"> <code md-pos="316-321">*[]:</code>                                                 </td></tr>
        <tr md-pos="375-468"><td md-pos="376-391"> Code fence    </td><td md-pos="392-409"> <code md-pos="394-404">.codefence</code>    </td><td md-pos="410-467"> ``` ... ```                                       </td></tr>
        <tr md-pos="471-564"><td md-pos="472-487"> Explicit link </td><td md-pos="488-505"> <code md-pos="490-495">.link</code>         </td><td md-pos="506-563"> <code md-pos="508-512">[]()</code>                                                  </td></tr>
      </tbody>
    </table>
  </li>
</ul>
.
Document[0, 564]
  BulletList[0, 564] isLoose
    BulletListItem[0, 564] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[2, 44] isTrailingBlankLine
        Text[2, 36] chars:[2, 36, "Add:  … with "]
        Code[36, 39] textOpen:[36, 37, "`"] text:[37, 38, "."] textClose:[38, 39, "`"]
          Text[37, 38] chars:[37, 38, "."]
      TableBlock[87, 564]
        TableHead[87, 180]
          TableRow[87, 180] rowNumber=1
            TableCell[87, 104] header textOpen:[87, 88, "|"] text:[88, 103, " Element       "] textClose:[103, 104, "|"]
              Text[88, 103] chars:[88, 103, " Elem …      "]
            TableCell[104, 122] header text:[104, 121, " Abbreviation    "] textClose:[121, 122, "|"]
              Text[104, 121] chars:[104, 121, " Abbr … n    "]
            TableCell[122, 180] header text:[122, 179, " Expansion                                               "] textClose:[179, 180, "|"]
              Text[122, 179] chars:[122, 179, " Expa …      "]
        TableSeparator[183, 276]
          TableRow[183, 276]
            TableCell[183, 200] textOpen:[183, 184, "|"] text:[184, 199, "---------------"] textClose:[199, 200, "|"]
              Text[184, 199] chars:[184, 199, "----- … -----"]
            TableCell[200, 218] text:[200, 217, "-----------------"] textClose:[217, 218, "|"]
              Text[200, 217] chars:[200, 217, "----- … -----"]
            TableCell[218, 276] text:[218, 275, "---------------------------------------------------------"] textClose:[275, 276, "|"]
              Text[218, 275] chars:[218, 275, "----- … -----"]
        TableBody[279, 564]
          TableRow[279, 372] rowNumber=1
            TableCell[279, 296] textOpen:[279, 280, "|"] text:[280, 295, " Abbreviation  "] textClose:[295, 296, "|"]
              Text[280, 295] chars:[280, 295, " Abbr … ion  "]
            TableCell[296, 314] text:[296, 313, " `.abbreviation` "] textClose:[313, 314, "|"]
              Text[296, 297] chars:[296, 297, " "]
              Code[297, 312] textOpen:[297, 298, "`"] text:[298, 311, ".abbr … eviation"] textClose:[311, 312, "`"]
                Text[298, 311] chars:[298, 311, ".abbr … ation"]
              Text[312, 312]
              Text[312, 313] chars:[312, 313, " "]
            TableCell[314, 372] text:[314, 371, " `*[]: `                                                 "] textClose:[371, 372, "|"]
              Text[314, 315] chars:[314, 315, " "]
              Code[315, 322] textOpen:[315, 316, "`"] text:[316, 321, "*[]: "] textClose:[321, 322, "`"]
                Text[316, 321] chars:[316, 321, "*[]: "]
              Text[322, 322]
              Text[322, 371] chars:[322, 371, "      …      "]
          TableRow[375, 468] rowNumber=2
            TableCell[375, 392] textOpen:[375, 376, "|"] text:[376, 391, " Code fence    "] textClose:[391, 392, "|"]
              Text[376, 391] chars:[376, 391, " Code … e    "]
            TableCell[392, 410] text:[392, 409, " `.codefence`    "] textClose:[409, 410, "|"]
              Text[392, 393] chars:[392, 393, " "]
              Code[393, 405] textOpen:[393, 394, "`"] text:[394, 404, ".codefence"] textClose:[404, 405, "`"]
                Text[394, 404] chars:[394, 404, ".codefence"]
              Text[405, 405]
              Text[405, 409] chars:[405, 409, "    "]
            TableCell[410, 468] text:[410, 467, " \`\`\` ... \`\`\`                                       "] textClose:[467, 468, "|"]
              Text[410, 467] chars:[410, 467, " \`\` …      "]
          TableRow[471, 564] rowNumber=3
            TableCell[471, 488] textOpen:[471, 472, "|"] text:[472, 487, " Explicit link "] textClose:[487, 488, "|"]
              Text[472, 487] chars:[472, 487, " Expl … link "]
            TableCell[488, 506] text:[488, 505, " `.link`         "] textClose:[505, 506, "|"]
              Text[488, 489] chars:[488, 489, " "]
              Code[489, 496] textOpen:[489, 490, "`"] text:[490, 495, ".link"] textClose:[495, 496, "`"]
                Text[490, 495] chars:[490, 495, ".link"]
              Text[496, 496]
              Text[496, 505] chars:[496, 505, "         "]
            TableCell[506, 564] text:[506, 563, " `[]()`                                                  "] textClose:[563, 564, "|"]
              Text[506, 507] chars:[506, 507, " "]
              Code[507, 513] textOpen:[507, 508, "`"] text:[508, 512, "[]()"] textClose:[512, 513, "`"]
                Text[508, 512] chars:[508, 512, "[]()"]
              Text[513, 513]
              Text[513, 563] chars:[513, 563, "      …      "]
````````````````````````````````


Issue #95, TextCollectingVisitor collects 2nd row of table heading

```````````````````````````````` example Source Position Attribute: 3
| First Header  | Second Header |
| ------------- | ------------- |
| Content Cell  | Content Cell  |

| Left-aligned | Center-aligned | Right-aligned |
| :---         |     :---:      |          ---: |
| git status   | git status     | git status    |
| git diff     | git diff       | git diff      |
.
<table>
  <thead>
    <tr><th>First Header</th><th>Second Header</th></tr>
  </thead>
  <tbody>
    <tr><td>Content Cell</td><td>Content Cell</td></tr>
  </tbody>
</table>
<table>
  <thead>
    <tr><th align="left">Left-aligned</th><th align="center">Center-aligned</th><th align="right">Right-aligned</th></tr>
  </thead>
  <tbody>
    <tr><td align="left">git status</td><td align="center">git status</td><td align="right">git status</td></tr>
    <tr><td align="left">git diff</td><td align="center">git diff</td><td align="right">git diff</td></tr>
  </tbody>
</table>
.
Document[0, 302]
  TableBlock[0, 102]
    TableHead[0, 33]
      TableRow[0, 33] rowNumber=1
        TableCell[0, 17] header textOpen:[0, 1, "|"] text:[2, 14, "First Header"] textClose:[16, 17, "|"]
          Text[2, 14] chars:[2, 14, "First … eader"]
        TableCell[17, 33] header text:[18, 31, "Second Header"] textClose:[32, 33, "|"]
          Text[18, 31] chars:[18, 31, "Secon … eader"]
    TableSeparator[34, 67]
      TableRow[34, 67]
        TableCell[34, 51] textOpen:[34, 35, "|"] text:[36, 49, "-------------"] textClose:[50, 51, "|"]
          Text[36, 49] chars:[36, 49, "----- … -----"]
        TableCell[51, 67] text:[52, 65, "-------------"] textClose:[66, 67, "|"]
          Text[52, 65] chars:[52, 65, "----- … -----"]
    TableBody[68, 101]
      TableRow[68, 101] rowNumber=1
        TableCell[68, 85] textOpen:[68, 69, "|"] text:[70, 82, "Content Cell"] textClose:[84, 85, "|"]
          Text[70, 82] chars:[70, 82, "Conte …  Cell"]
        TableCell[85, 101] text:[86, 98, "Content Cell"] textClose:[100, 101, "|"]
          Text[86, 98] chars:[86, 98, "Conte …  Cell"]
  TableBlock[103, 302]
    TableHead[103, 152]
      TableRow[103, 152] rowNumber=1
        TableCell[103, 119] LEFT header textOpen:[103, 104, "|"] text:[105, 117, "Left-aligned"] textClose:[118, 119, "|"]
          Text[105, 117] chars:[105, 117, "Left- … igned"]
        TableCell[119, 136] CENTER header text:[120, 134, "Center-aligned"] textClose:[135, 136, "|"]
          Text[120, 134] chars:[120, 134, "Cente … igned"]
        TableCell[136, 152] RIGHT header text:[137, 150, "Right-aligned"] textClose:[151, 152, "|"]
          Text[137, 150] chars:[137, 150, "Right … igned"]
    TableSeparator[153, 202]
      TableRow[153, 202]
        TableCell[153, 169] LEFT textOpen:[153, 154, "|"] text:[155, 159, ":---"] textClose:[168, 169, "|"]
          Text[155, 159] chars:[155, 159, ":---"]
        TableCell[169, 186] CENTER text:[174, 179, ":---:"] textClose:[185, 186, "|"]
          Text[174, 179] chars:[174, 179, ":---:"]
        TableCell[186, 202] RIGHT text:[196, 200, "---:"] textClose:[201, 202, "|"]
          Text[196, 200] chars:[196, 200, "---:"]
    TableBody[203, 302]
      TableRow[203, 252] rowNumber=1
        TableCell[203, 219] LEFT textOpen:[203, 204, "|"] text:[205, 215, "git status"] textClose:[218, 219, "|"]
          Text[205, 215] chars:[205, 215, "git status"]
        TableCell[219, 236] CENTER text:[220, 230, "git status"] textClose:[235, 236, "|"]
          Text[220, 230] chars:[220, 230, "git status"]
        TableCell[236, 252] RIGHT text:[237, 247, "git status"] textClose:[251, 252, "|"]
          Text[237, 247] chars:[237, 247, "git status"]
      TableRow[253, 302] rowNumber=2
        TableCell[253, 269] LEFT textOpen:[253, 254, "|"] text:[255, 263, "git diff"] textClose:[268, 269, "|"]
          Text[255, 263] chars:[255, 263, "git diff"]
        TableCell[269, 286] CENTER text:[270, 278, "git diff"] textClose:[285, 286, "|"]
          Text[270, 278] chars:[270, 278, "git diff"]
        TableCell[286, 302] RIGHT text:[287, 295, "git diff"] textClose:[301, 302, "|"]
          Text[287, 295] chars:[287, 295, "git diff"]
````````````````````````````````


## Emphasis in cell

```````````````````````````````` example Emphasis in cell: 1
| Column 1            | Column 2            |
|---------------------|---------------------|
| ___________________ | ___________________ |
| ___________________ | ___________________ |

.
<table>
  <thead>
    <tr><th>Column 1</th><th>Column 2</th></tr>
  </thead>
  <tbody>
    <tr><td>___________________</td><td>___________________</td></tr>
    <tr><td>___________________</td><td>___________________</td></tr>
  </tbody>
</table>
.
Document[0, 185]
  TableBlock[0, 184]
    TableHead[0, 45]
      TableRow[0, 45] rowNumber=1
        TableCell[0, 23] header textOpen:[0, 1, "|"] text:[2, 10, "Column 1"] textClose:[22, 23, "|"]
          Text[2, 10] chars:[2, 10, "Column 1"]
        TableCell[23, 45] header text:[24, 32, "Column 2"] textClose:[44, 45, "|"]
          Text[24, 32] chars:[24, 32, "Column 2"]
    TableSeparator[46, 91]
      TableRow[46, 91]
        TableCell[46, 69] textOpen:[46, 47, "|"] text:[47, 68, "---------------------"] textClose:[68, 69, "|"]
          Text[47, 68] chars:[47, 68, "----- … -----"]
        TableCell[69, 91] text:[69, 90, "---------------------"] textClose:[90, 91, "|"]
          Text[69, 90] chars:[69, 90, "----- … -----"]
    TableBody[92, 183]
      TableRow[92, 137] rowNumber=1
        TableCell[92, 115] textOpen:[92, 93, "|"] text:[94, 113, "___________________"] textClose:[114, 115, "|"]
          Text[94, 113] chars:[94, 113, "_____ … _____"]
        TableCell[115, 137] text:[116, 135, "___________________"] textClose:[136, 137, "|"]
          Text[116, 135] chars:[116, 135, "_____ … _____"]
      TableRow[138, 183] rowNumber=2
        TableCell[138, 161] textOpen:[138, 139, "|"] text:[140, 159, "___________________"] textClose:[160, 161, "|"]
          Text[140, 159] chars:[140, 159, "_____ … _____"]
        TableCell[161, 183] text:[162, 181, "___________________"] textClose:[182, 183, "|"]
          Text[162, 181] chars:[162, 181, "_____ … _____"]
````````````````````````````````


## DoNotDecorate

```````````````````````````````` example DoNotDecorate: 1
| Abc Long -- 'quoted' |
|----------------------|
| Def Short --- "quoted" |
.
<table>
  <thead>
    <tr><th>Abc Long -- 'quoted'</th></tr>
  </thead>
  <tbody>
    <tr><td>Def Short --- &quot;quoted&quot;</td></tr>
  </tbody>
</table>
.
Document[0, 76]
  TableBlock[0, 76]
    TableHead[0, 24]
      TableRow[0, 24] rowNumber=1
        TableCell[0, 24] header textOpen:[0, 1, "|"] text:[2, 22, "Abc Long -- 'quoted'"] textClose:[23, 24, "|"]
          Text[2, 22] chars:[2, 22, "Abc L … oted'"]
    TableSeparator[25, 49]
      TableRow[25, 49]
        TableCell[25, 49] textOpen:[25, 26, "|"] text:[26, 48, "----------------------"] textClose:[48, 49, "|"]
          Text[26, 48] chars:[26, 48, "----- … -----"]
    TableBody[50, 76]
      TableRow[50, 76] rowNumber=1
        TableCell[50, 76] textOpen:[50, 51, "|"] text:[52, 74, "Def Short --- \"quoted\""] textClose:[75, 76, "|"]
          Text[52, 74] chars:[52, 74, "Def S … oted\""]
````````````````````````````````


Typographic should not process separator nodes

```````````````````````````````` example(DoNotDecorate: 2) options(typographic)
| Abc Long -- 'quoted' |
|----------------------|
| Def Short --- "quoted" |
.
<table>
  <thead>
    <tr><th>Abc Long &ndash; &lsquo;quoted&rsquo;</th></tr>
  </thead>
  <tbody>
    <tr><td>Def Short &mdash; &ldquo;quoted&rdquo;</td></tr>
  </tbody>
</table>
.
Document[0, 76]
  TableBlock[0, 76]
    TableHead[0, 24]
      TableRow[0, 24] rowNumber=1
        TableCell[0, 24] header textOpen:[0, 1, "|"] text:[2, 22, "Abc Long -- 'quoted'"] textClose:[23, 24, "|"]
          Text[2, 11] chars:[2, 11, "Abc Long "]
          TypographicSmarts[11, 13] typographic: &ndash; 
          Text[13, 14] chars:[13, 14, " "]
          TypographicQuotes[14, 22] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[14, 15, "'"] text:[15, 21, "quoted"] textClose:[21, 22, "'"]
            Text[15, 21] chars:[15, 21, "quoted"]
          Text[22, 22]
    TableSeparator[25, 49]
      TableRow[25, 49]
        TableCell[25, 49] textOpen:[25, 26, "|"] text:[26, 48, "----------------------"] textClose:[48, 49, "|"]
          Text[26, 48] chars:[26, 48, "----- … -----"]
    TableBody[50, 76]
      TableRow[50, 76] rowNumber=1
        TableCell[50, 76] textOpen:[50, 51, "|"] text:[52, 74, "Def Short --- \"quoted\""] textClose:[75, 76, "|"]
          Text[52, 62] chars:[52, 62, "Def Short "]
          TypographicSmarts[62, 65] typographic: &mdash; 
          Text[65, 66] chars:[65, 66, " "]
          TypographicQuotes[66, 74] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[66, 67, "\""] text:[67, 73, "quoted"] textClose:[73, 74, "\""]
            Text[67, 73] chars:[67, 73, "quoted"]
          Text[74, 74]
````````````````````````````````


## Issue

### 106

Issue #106, Table placed after code block does not parsed

```````````````````````````````` example Issue - 106: 1
Place specified block in the specified point.
`BLOCK_SET <Block> <Location> [Physics]`

| Parameter | Parameter value | Description |
|-----------|-----------------|-------------|
|`<Block>` |`block:<BlockType>` | Block defined using item format (block types and material data supported) |
|`<Location>` | `loc:<Location>` | Location where block will be placed |
|`[Physics]` |`physics:<true\|false>` |Apply physic to block nearby. Default - true. |
.
<p>Place specified block in the specified point.
<code>BLOCK_SET &lt;Block&gt; &lt;Location&gt; [Physics]</code></p>
<table>
  <thead>
    <tr><th>Parameter</th><th>Parameter value</th><th>Description</th></tr>
  </thead>
  <tbody>
    <tr><td><code>&lt;Block&gt;</code></td><td><code>block:&lt;BlockType&gt;</code></td><td>Block defined using item format (block types and material data supported)</td></tr>
    <tr><td><code>&lt;Location&gt;</code></td><td><code>loc:&lt;Location&gt;</code></td><td>Location where block will be placed</td></tr>
    <tr><td><code>[Physics]</code></td><td><code>physics:&lt;true\|false&gt;</code></td><td>Apply physic to block nearby. Default - true.</td></tr>
  </tbody>
</table>
.
Document[0, 449]
  Paragraph[0, 87] isTrailingBlankLine
    Text[0, 45] chars:[0, 45, "Place … oint."]
    SoftLineBreak[45, 46]
    Code[46, 86] textOpen:[46, 47, "`"] text:[47, 85, "BLOCK … _SET <Block> <Location> [Physics]"] textClose:[85, 86, "`"]
      Text[47, 85] chars:[47, 85, "BLOCK … sics]"]
  TableBlock[88, 449]
    TableHead[88, 133]
      TableRow[88, 133] rowNumber=1
        TableCell[88, 101] header textOpen:[88, 89, "|"] text:[90, 99, "Parameter"] textClose:[100, 101, "|"]
          Text[90, 99] chars:[90, 99, "Parameter"]
        TableCell[101, 119] header text:[102, 117, "Parameter value"] textClose:[118, 119, "|"]
          Text[102, 117] chars:[102, 117, "Param … value"]
        TableCell[119, 133] header text:[120, 131, "Description"] textClose:[132, 133, "|"]
          Text[120, 131] chars:[120, 131, "Descr … ption"]
    TableSeparator[134, 179]
      TableRow[134, 179]
        TableCell[134, 147] textOpen:[134, 135, "|"] text:[135, 146, "-----------"] textClose:[146, 147, "|"]
          Text[135, 146] chars:[135, 146, "----- … -----"]
        TableCell[147, 165] text:[147, 164, "-----------------"] textClose:[164, 165, "|"]
          Text[147, 164] chars:[147, 164, "----- … -----"]
        TableCell[165, 179] text:[165, 178, "-------------"] textClose:[178, 179, "|"]
          Text[165, 178] chars:[165, 178, "----- … -----"]
    TableBody[180, 449]
      TableRow[180, 289] rowNumber=1
        TableCell[180, 192] textOpen:[180, 181, "|"] text:[181, 190, "`<Block>`"] textClose:[191, 192, "|"]
          Code[181, 190] textOpen:[181, 182, "`"] text:[182, 189, "<Block>"] textClose:[189, 190, "`"]
            Text[182, 189] chars:[182, 189, "<Block>"]
          Text[190, 190]
        TableCell[192, 213] text:[192, 211, "`block:<BlockType>`"] textClose:[212, 213, "|"]
          Code[192, 211] textOpen:[192, 193, "`"] text:[193, 210, "block … :<BlockType>"] textClose:[210, 211, "`"]
            Text[193, 210] chars:[193, 210, "block … Type>"]
          Text[211, 211]
        TableCell[213, 289] text:[214, 287, "Block defined using item format (block types and material data supported)"] textClose:[288, 289, "|"]
          Text[214, 287] chars:[214, 287, "Block … rted)"]
      TableRow[290, 362] rowNumber=2
        TableCell[290, 305] textOpen:[290, 291, "|"] text:[291, 303, "`<Location>`"] textClose:[304, 305, "|"]
          Code[291, 303] textOpen:[291, 292, "`"] text:[292, 302, "<Location>"] textClose:[302, 303, "`"]
            Text[292, 302] chars:[292, 302, "<Location>"]
          Text[303, 303]
        TableCell[305, 324] text:[306, 322, "`loc:<Location>`"] textClose:[323, 324, "|"]
          Code[306, 322] textOpen:[306, 307, "`"] text:[307, 321, "loc:< … Location>"] textClose:[321, 322, "`"]
            Text[307, 321] chars:[307, 321, "loc:< … tion>"]
          Text[322, 322]
        TableCell[324, 362] text:[325, 360, "Location where block will be placed"] textClose:[361, 362, "|"]
          Text[325, 360] chars:[325, 360, "Locat … laced"]
      TableRow[363, 449] rowNumber=3
        TableCell[363, 377] textOpen:[363, 364, "|"] text:[364, 375, "`[Physics]`"] textClose:[376, 377, "|"]
          Code[364, 375] textOpen:[364, 365, "`"] text:[365, 374, "[Physics]"] textClose:[374, 375, "`"]
            Text[365, 374] chars:[365, 374, "[Physics]"]
          Text[375, 375]
        TableCell[377, 402] text:[377, 400, "`physics:<true\|false>`"] textClose:[401, 402, "|"]
          Code[377, 400] textOpen:[377, 378, "`"] text:[378, 399, "physi … cs:<true\|false>"] textClose:[399, 400, "`"]
            Text[378, 399] chars:[378, 399, "physi … alse>"]
          Text[400, 400]
        TableCell[402, 449] text:[402, 447, "Apply physic to block nearby. Default - true."] textClose:[448, 449, "|"]
          Text[402, 447] chars:[402, 447, "Apply … true."]
````````````````````````````````


### 125

Min Column Dashes

```````````````````````````````` example(Issue - 125: 1) options(min-dashes-2)
|Vowels|dd|
|------|--|
|aeiou |a |
|AEIOU |e |
.
<table>
  <thead>
    <tr><th>Vowels</th><th>dd</th></tr>
  </thead>
  <tbody>
    <tr><td>aeiou</td><td>a</td></tr>
    <tr><td>AEIOU</td><td>e</td></tr>
  </tbody>
</table>
.
Document[0, 47]
  TableBlock[0, 47]
    TableHead[0, 11]
      TableRow[0, 11] rowNumber=1
        TableCell[0, 8] header textOpen:[0, 1, "|"] text:[1, 7, "Vowels"] textClose:[7, 8, "|"]
          Text[1, 7] chars:[1, 7, "Vowels"]
        TableCell[8, 11] header text:[8, 10, "dd"] textClose:[10, 11, "|"]
          Text[8, 10] chars:[8, 10, "dd"]
    TableSeparator[12, 23]
      TableRow[12, 23]
        TableCell[12, 20] textOpen:[12, 13, "|"] text:[13, 19, "------"] textClose:[19, 20, "|"]
          Text[13, 19] chars:[13, 19, "------"]
        TableCell[20, 23] text:[20, 22, "--"] textClose:[22, 23, "|"]
          Text[20, 22] chars:[20, 22, "--"]
    TableBody[24, 47]
      TableRow[24, 35] rowNumber=1
        TableCell[24, 32] textOpen:[24, 25, "|"] text:[25, 30, "aeiou"] textClose:[31, 32, "|"]
          Text[25, 30] chars:[25, 30, "aeiou"]
        TableCell[32, 35] text:[32, 33, "a"] textClose:[34, 35, "|"]
          Text[32, 33] chars:[32, 33, "a"]
      TableRow[36, 47] rowNumber=2
        TableCell[36, 44] textOpen:[36, 37, "|"] text:[37, 42, "AEIOU"] textClose:[43, 44, "|"]
          Text[37, 42] chars:[37, 42, "AEIOU"]
        TableCell[44, 47] text:[44, 45, "e"] textClose:[46, 47, "|"]
          Text[44, 45] chars:[44, 45, "e"]
````````````````````````````````


```````````````````````````````` example(Issue - 125: 2) options(min-dashes-1)
|Vowels|d|
|------|-|
|aeiou |a |
|AEIOU |e |
.
<table>
  <thead>
    <tr><th>Vowels</th><th>d</th></tr>
  </thead>
  <tbody>
    <tr><td>aeiou</td><td>a</td></tr>
    <tr><td>AEIOU</td><td>e</td></tr>
  </tbody>
</table>
.
Document[0, 45]
  TableBlock[0, 45]
    TableHead[0, 10]
      TableRow[0, 10] rowNumber=1
        TableCell[0, 8] header textOpen:[0, 1, "|"] text:[1, 7, "Vowels"] textClose:[7, 8, "|"]
          Text[1, 7] chars:[1, 7, "Vowels"]
        TableCell[8, 10] header text:[8, 9, "d"] textClose:[9, 10, "|"]
          Text[8, 9] chars:[8, 9, "d"]
    TableSeparator[11, 21]
      TableRow[11, 21]
        TableCell[11, 19] textOpen:[11, 12, "|"] text:[12, 18, "------"] textClose:[18, 19, "|"]
          Text[12, 18] chars:[12, 18, "------"]
        TableCell[19, 21] text:[19, 20, "-"] textClose:[20, 21, "|"]
          Text[19, 20] chars:[19, 20, "-"]
    TableBody[22, 45]
      TableRow[22, 33] rowNumber=1
        TableCell[22, 30] textOpen:[22, 23, "|"] text:[23, 28, "aeiou"] textClose:[29, 30, "|"]
          Text[23, 28] chars:[23, 28, "aeiou"]
        TableCell[30, 33] text:[30, 31, "a"] textClose:[32, 33, "|"]
          Text[30, 31] chars:[30, 31, "a"]
      TableRow[34, 45] rowNumber=2
        TableCell[34, 42] textOpen:[34, 35, "|"] text:[35, 40, "AEIOU"] textClose:[41, 42, "|"]
          Text[35, 40] chars:[35, 40, "AEIOU"]
        TableCell[42, 45] text:[42, 43, "e"] textClose:[44, 45, "|"]
          Text[42, 43] chars:[42, 43, "e"]
````````````````````````````````


### 135

Issue #135, Render problem when there is a line below the table that can be interpreted as
setext heading.

```````````````````````````````` example(Issue - 135: 1) options(FAIL)
|a|b|c|
|---|---|---|
|1|2|3|
---
.
<table>
  <thead>
    <tr><th>a</th><th>b</th><th>c</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td><td>3</td></tr>
  </tbody>
</table>
<hr />
.
Document[0, 34]
  TableBlock[0, 30]
    TableHead[0, 7]
      TableRow[0, 7]
        TableCell[1, 2] header text:[1, 2, "a"]
          Text[1, 2] chars:[1, 2, "a"]
        TableCell[3, 4] header text:[3, 4, "b"]
          Text[3, 4] chars:[3, 4, "b"]
        TableCell[5, 6] header text:[5, 6, "c"]
          Text[5, 6] chars:[5, 6, "c"]
    TableSeparator[8, 21]
      TableRow[8, 21]
        TableCell[9, 12] text:[9, 12, "---"]
          Text[9, 12] chars:[9, 12, "---"]
        TableCell[13, 16] text:[13, 16, "---"]
          Text[13, 16] chars:[13, 16, "---"]
        TableCell[17, 20] text:[17, 20, "---"]
          Text[17, 20] chars:[17, 20, "---"]
    TableBody[22, 29]
      TableRow[22, 29]
        TableCell[23, 24] text:[23, 24, "1"]
          Text[23, 24] chars:[23, 24, "1"]
        TableCell[25, 26] text:[25, 26, "2"]
          Text[25, 26] chars:[25, 26, "2"]
        TableCell[27, 28] text:[27, 28, "3"]
          Text[27, 28] chars:[27, 28, "3"]
  ThematicBreak[30, 33]
````````````````````````````````


parsed as heading because table processing occurs on paragraphs only

```````````````````````````````` example Issue - 135: 2
|a|b|c|
|---|---|---|
|1|2|3|
---
.
<h2>|a|b|c|
|---|---|---|
|1|2|3|</h2>
.
Document[0, 33]
  Heading[0, 33] text:[0, 29, "|a|b|c|\n|---|---|---|\n|1|2|3|"] textClose:[30, 33, "---"]
    Text[0, 7] chars:[0, 7, "|a|b|c|"]
    SoftLineBreak[7, 8]
    Text[8, 21] chars:[8, 21, "|---| … |---|"]
    SoftLineBreak[21, 22]
    Text[22, 29] chars:[22, 29, "|1|2|3|"]
````````````````````````````````


parsed as heading because table processing occurs on paragraphs only

```````````````````````````````` example Issue - 135: 3
|a|b|c|
|---|---|---|
|1|2|3|
===
.
<h1>|a|b|c|
|---|---|---|
|1|2|3|</h1>
.
Document[0, 33]
  Heading[0, 33] text:[0, 29, "|a|b|c|\n|---|---|---|\n|1|2|3|"] textClose:[30, 33, "==="]
    Text[0, 7] chars:[0, 7, "|a|b|c|"]
    SoftLineBreak[7, 8]
    Text[8, 21] chars:[8, 21, "|---| … |---|"]
    SoftLineBreak[21, 22]
    Text[22, 29] chars:[22, 29, "|1|2|3|"]
````````````````````````````````


### 216

Issue #216, Wrong source positions for Tables with empty TableHead/TableBody

```````````````````````````````` example Issue - 216: 1

|---|---|
.
<table>
  <thead></thead>
  <tbody></tbody>
</table>
.
Document[0, 10]
  TableBlock[1, 10]
    TableHead[1, 1]
    TableSeparator[1, 10]
      TableRow[1, 10]
        TableCell[1, 6] textOpen:[1, 2, "|"] text:[2, 5, "---"] textClose:[5, 6, "|"]
          Text[2, 5] chars:[2, 5, "---"]
        TableCell[6, 10] text:[6, 9, "---"] textClose:[9, 10, "|"]
          Text[6, 9] chars:[6, 9, "---"]
    TableBody[10, 10]
````````````````````````````````


```````````````````````````````` example Issue - 216: 2

|---|---|
|a|b|
.
<table>
  <thead></thead>
  <tbody>
    <tr><td>a</td><td>b</td></tr>
  </tbody>
</table>
.
Document[0, 16]
  TableBlock[1, 16]
    TableHead[1, 1]
    TableSeparator[1, 10]
      TableRow[1, 10]
        TableCell[1, 6] textOpen:[1, 2, "|"] text:[2, 5, "---"] textClose:[5, 6, "|"]
          Text[2, 5] chars:[2, 5, "---"]
        TableCell[6, 10] text:[6, 9, "---"] textClose:[9, 10, "|"]
          Text[6, 9] chars:[6, 9, "---"]
    TableBody[11, 16]
      TableRow[11, 16] rowNumber=1
        TableCell[11, 14] textOpen:[11, 12, "|"] text:[12, 13, "a"] textClose:[13, 14, "|"]
          Text[12, 13] chars:[12, 13, "a"]
        TableCell[14, 16] text:[14, 15, "b"] textClose:[15, 16, "|"]
          Text[14, 15] chars:[14, 15, "b"]
````````````````````````````````


```````````````````````````````` example Issue - 216: 3
foo

---|---

.
<p>foo</p>
<table>
  <thead></thead>
  <tbody></tbody>
</table>
.
Document[0, 14]
  Paragraph[0, 4] isTrailingBlankLine
    Text[0, 3] chars:[0, 3, "foo"]
  TableBlock[5, 13]
    TableHead[5, 5]
    TableSeparator[5, 12]
      TableRow[5, 12]
        TableCell[5, 9] text:[5, 8, "---"] textClose:[8, 9, "|"]
          Text[5, 8] chars:[5, 8, "---"]
        TableCell[9, 12] text:[9, 12, "---"]
          Text[9, 12] chars:[9, 12, "---"]
    TableBody[12, 12]
````````````````````````````````


### xxx-1

Parse sub sequences

```````````````````````````````` example(Issue - xxx-1: 1) options(sub-parse)
| Left                                   |  Right | Center  |
| Left                                   |  Right | Center  |
|:---------------------------------------|-------:|:-------:|
| ditem 1 can be longer                  | 125.30 |   yes   |
| item 2  this is a test of table formng |        1,234.00 ||
| item 3 much longer format              |  10.50 |  maybe  |
| item 4 short                           |  34.10 | not now |

.
<table>
  <thead>
    <tr><th align="left">Left</th><th align="right">Right</th><th align="center">Center</th></tr>
    <tr><th align="left">Left</th><th align="right">Right</th><th align="center">Center</th></tr>
  </thead>
  <tbody>
    <tr><td align="left">ditem 1 can be longer</td><td align="right">125.30</td><td align="center">yes</td></tr>
    <tr><td align="left">item 2  this is a test of table formng</td><td align="right" colspan="2">1,234.00</td></tr>
    <tr><td align="left">item 3 much longer format</td><td align="right">10.50</td><td align="center">maybe</td></tr>
    <tr><td align="left">item 4 short</td><td align="right">34.10</td><td align="center">not now</td></tr>
  </tbody>
</table>
.
Document[0, 435]
  TableBlock[0, 434]
    TableHead[0, 123]
      TableRow[0, 61] rowNumber=1
        TableCell[0, 42] LEFT header textOpen:[0, 1, "|"] text:[2, 6, "Left"] textClose:[41, 42, "|"]
          Text[2, 6] chars:[2, 6, "Left"]
        TableCell[42, 51] RIGHT header text:[44, 49, "Right"] textClose:[50, 51, "|"]
          Text[44, 49] chars:[44, 49, "Right"]
        TableCell[51, 61] CENTER header text:[52, 58, "Center"] textClose:[60, 61, "|"]
          Text[52, 58] chars:[52, 58, "Center"]
      TableRow[62, 123] rowNumber=2
        TableCell[62, 104] LEFT header textOpen:[62, 63, "|"] text:[64, 68, "Left"] textClose:[103, 104, "|"]
          Text[64, 68] chars:[64, 68, "Left"]
        TableCell[104, 113] RIGHT header text:[106, 111, "Right"] textClose:[112, 113, "|"]
          Text[106, 111] chars:[106, 111, "Right"]
        TableCell[113, 123] CENTER header text:[114, 120, "Center"] textClose:[122, 123, "|"]
          Text[114, 120] chars:[114, 120, "Center"]
    TableSeparator[124, 185]
      TableRow[124, 185]
        TableCell[124, 166] LEFT textOpen:[124, 125, "|"] text:[125, 165, ":---------------------------------------"] textClose:[165, 166, "|"]
          Text[125, 165] chars:[125, 165, ":---- … -----"]
        TableCell[166, 175] RIGHT text:[166, 174, "-------:"] textClose:[174, 175, "|"]
          Text[166, 174] chars:[166, 174, "-------:"]
        TableCell[175, 185] CENTER text:[175, 184, ":-------:"] textClose:[184, 185, "|"]
          Text[175, 184] chars:[175, 184, ":-------:"]
    TableBody[186, 433]
      TableRow[186, 247] rowNumber=1
        TableCell[186, 228] LEFT textOpen:[186, 187, "|"] text:[188, 209, "ditem 1 can be longer"] textClose:[227, 228, "|"]
          Text[188, 209] chars:[188, 209, "ditem … onger"]
        TableCell[228, 237] RIGHT text:[229, 235, "125.30"] textClose:[236, 237, "|"]
          Text[229, 235] chars:[229, 235, "125.30"]
        TableCell[237, 247] CENTER text:[240, 243, "yes"] textClose:[246, 247, "|"]
          Text[240, 243] chars:[240, 243, "yes"]
      TableRow[248, 309] rowNumber=2
        TableCell[248, 290] LEFT textOpen:[248, 249, "|"] text:[250, 288, "item 2  this is a test of table formng"] textClose:[289, 290, "|"]
          Text[250, 288] chars:[250, 288, "item  … ormng"]
        TableCell[290, 309] RIGHT span=2 text:[298, 306, "1,234.00"] textClose:[307, 309, "||"]
          Text[298, 306] chars:[298, 306, "1,234.00"]
      TableRow[310, 371] rowNumber=3
        TableCell[310, 352] LEFT textOpen:[310, 311, "|"] text:[312, 337, "item 3 much longer format"] textClose:[351, 352, "|"]
          Text[312, 337] chars:[312, 337, "item  … ormat"]
        TableCell[352, 361] RIGHT text:[354, 359, "10.50"] textClose:[360, 361, "|"]
          Text[354, 359] chars:[354, 359, "10.50"]
        TableCell[361, 371] CENTER text:[363, 368, "maybe"] textClose:[370, 371, "|"]
          Text[363, 368] chars:[363, 368, "maybe"]
      TableRow[372, 433] rowNumber=4
        TableCell[372, 414] LEFT textOpen:[372, 373, "|"] text:[374, 386, "item 4 short"] textClose:[413, 414, "|"]
          Text[374, 386] chars:[374, 386, "item  … short"]
        TableCell[414, 423] RIGHT text:[416, 421, "34.10"] textClose:[422, 423, "|"]
          Text[416, 421] chars:[416, 421, "34.10"]
        TableCell[423, 433] CENTER text:[424, 431, "not now"] textClose:[432, 433, "|"]
          Text[424, 431] chars:[424, 431, "not now"]
````````````````````````````````


### xxx-2

Table does not parse properly if no EOL at end of base sequence

```````````````````````````````` example(Issue - xxx-2: 1) options(NO_FILE_EOL)
|name|display|title_en|title_de|
|---|---|---|---|
|none| |
.
<table>
  <thead>
    <tr><th>name</th><th>display</th><th>title_en</th><th>title_de</th></tr>
  </thead>
  <tbody>
    <tr><td>none</td><td> </td></tr>
  </tbody>
</table>
.
Document[0, 59]
  TableBlock[0, 59]
    TableHead[0, 32]
      TableRow[0, 32] rowNumber=1
        TableCell[0, 6] header textOpen:[0, 1, "|"] text:[1, 5, "name"] textClose:[5, 6, "|"]
          Text[1, 5] chars:[1, 5, "name"]
        TableCell[6, 14] header text:[6, 13, "display"] textClose:[13, 14, "|"]
          Text[6, 13] chars:[6, 13, "display"]
        TableCell[14, 23] header text:[14, 22, "title_en"] textClose:[22, 23, "|"]
          Text[14, 22] chars:[14, 22, "title_en"]
        TableCell[23, 32] header text:[23, 31, "title_de"] textClose:[31, 32, "|"]
          Text[23, 31] chars:[23, 31, "title_de"]
    TableSeparator[33, 50]
      TableRow[33, 50]
        TableCell[33, 38] textOpen:[33, 34, "|"] text:[34, 37, "---"] textClose:[37, 38, "|"]
          Text[34, 37] chars:[34, 37, "---"]
        TableCell[38, 42] text:[38, 41, "---"] textClose:[41, 42, "|"]
          Text[38, 41] chars:[38, 41, "---"]
        TableCell[42, 46] text:[42, 45, "---"] textClose:[45, 46, "|"]
          Text[42, 45] chars:[42, 45, "---"]
        TableCell[46, 50] text:[46, 49, "---"] textClose:[49, 50, "|"]
          Text[46, 49] chars:[46, 49, "---"]
    TableBody[51, 59]
      TableRow[51, 59] rowNumber=1
        TableCell[51, 57] textOpen:[51, 52, "|"] text:[52, 56, "none"] textClose:[56, 57, "|"]
          Text[52, 56] chars:[52, 56, "none"]
        TableCell[57, 59] text:[57, 58, " "] textClose:[58, 59, "|"]
          Text[57, 58] chars:[57, 58, " "]
````````````````````````````````


Parse sub sequences

```````````````````````````````` example(Issue - xxx-2: 2) options(strip-indent)
> > | Left                                   |  Right | Center  |
> > | Left                                   |  Right | Center  |
> > |:---------------------------------------|-------:|:-------:|
> > | ditem 1 can be longer                  | 125.30 |   yes   |
> > | item 2  this is a test of table formng |        1,234.00 ||
> > | item 3 much longer format              |  10.50 |  maybe  |
> > | item 4 short                           |  34.10 | not now |

.
<table>
  <thead>
    <tr><th align="left">Left</th><th align="right">Right</th><th align="center">Center</th></tr>
    <tr><th align="left">Left</th><th align="right">Right</th><th align="center">Center</th></tr>
  </thead>
  <tbody>
    <tr><td align="left">ditem 1 can be longer</td><td align="right">125.30</td><td align="center">yes</td></tr>
    <tr><td align="left">item 2  this is a test of table formng</td><td align="right" colspan="2">1,234.00</td></tr>
    <tr><td align="left">item 3 much longer format</td><td align="right">10.50</td><td align="center">maybe</td></tr>
    <tr><td align="left">item 4 short</td><td align="right">34.10</td><td align="center">not now</td></tr>
  </tbody>
</table>
.
Document[0, 435]
  TableBlock[0, 434]
    TableHead[0, 123]
      TableRow[0, 61] rowNumber=1
        TableCell[0, 42] LEFT header textOpen:[0, 1, "|"] text:[2, 6, "Left"] textClose:[41, 42, "|"]
          Text[2, 6] chars:[2, 6, "Left"]
        TableCell[42, 51] RIGHT header text:[44, 49, "Right"] textClose:[50, 51, "|"]
          Text[44, 49] chars:[44, 49, "Right"]
        TableCell[51, 61] CENTER header text:[52, 58, "Center"] textClose:[60, 61, "|"]
          Text[52, 58] chars:[52, 58, "Center"]
      TableRow[62, 123] rowNumber=2
        TableCell[62, 104] LEFT header textOpen:[62, 63, "|"] text:[64, 68, "Left"] textClose:[103, 104, "|"]
          Text[64, 68] chars:[64, 68, "Left"]
        TableCell[104, 113] RIGHT header text:[106, 111, "Right"] textClose:[112, 113, "|"]
          Text[106, 111] chars:[106, 111, "Right"]
        TableCell[113, 123] CENTER header text:[114, 120, "Center"] textClose:[122, 123, "|"]
          Text[114, 120] chars:[114, 120, "Center"]
    TableSeparator[124, 185]
      TableRow[124, 185]
        TableCell[124, 166] LEFT textOpen:[124, 125, "|"] text:[125, 165, ":---------------------------------------"] textClose:[165, 166, "|"]
          Text[125, 165] chars:[125, 165, ":---- … -----"]
        TableCell[166, 175] RIGHT text:[166, 174, "-------:"] textClose:[174, 175, "|"]
          Text[166, 174] chars:[166, 174, "-------:"]
        TableCell[175, 185] CENTER text:[175, 184, ":-------:"] textClose:[184, 185, "|"]
          Text[175, 184] chars:[175, 184, ":-------:"]
    TableBody[186, 433]
      TableRow[186, 247] rowNumber=1
        TableCell[186, 228] LEFT textOpen:[186, 187, "|"] text:[188, 209, "ditem 1 can be longer"] textClose:[227, 228, "|"]
          Text[188, 209] chars:[188, 209, "ditem … onger"]
        TableCell[228, 237] RIGHT text:[229, 235, "125.30"] textClose:[236, 237, "|"]
          Text[229, 235] chars:[229, 235, "125.30"]
        TableCell[237, 247] CENTER text:[240, 243, "yes"] textClose:[246, 247, "|"]
          Text[240, 243] chars:[240, 243, "yes"]
      TableRow[248, 309] rowNumber=2
        TableCell[248, 290] LEFT textOpen:[248, 249, "|"] text:[250, 288, "item 2  this is a test of table formng"] textClose:[289, 290, "|"]
          Text[250, 288] chars:[250, 288, "item  … ormng"]
        TableCell[290, 309] RIGHT span=2 text:[298, 306, "1,234.00"] textClose:[307, 309, "||"]
          Text[298, 306] chars:[298, 306, "1,234.00"]
      TableRow[310, 371] rowNumber=3
        TableCell[310, 352] LEFT textOpen:[310, 311, "|"] text:[312, 337, "item 3 much longer format"] textClose:[351, 352, "|"]
          Text[312, 337] chars:[312, 337, "item  … ormat"]
        TableCell[352, 361] RIGHT text:[354, 359, "10.50"] textClose:[360, 361, "|"]
          Text[354, 359] chars:[354, 359, "10.50"]
        TableCell[361, 371] CENTER text:[363, 368, "maybe"] textClose:[370, 371, "|"]
          Text[363, 368] chars:[363, 368, "maybe"]
      TableRow[372, 433] rowNumber=4
        TableCell[372, 414] LEFT textOpen:[372, 373, "|"] text:[374, 386, "item 4 short"] textClose:[413, 414, "|"]
          Text[374, 386] chars:[374, 386, "item  … short"]
        TableCell[414, 423] RIGHT text:[416, 421, "34.10"] textClose:[422, 423, "|"]
          Text[416, 421] chars:[416, 421, "34.10"]
        TableCell[423, 433] CENTER text:[424, 431, "not now"] textClose:[432, 433, "|"]
          Text[424, 431] chars:[424, 431, "not now"]
````````````````````````````````


Parse sub sequences

Add prefix/suffix to text so document ast shifts by 14 chars

```````````````````````````````` example(Issue - xxx-2: 3) options(sub-parse, strip-indent)
> > | Left                                   |  Right | Center  |
> > | Left                                   |  Right | Center  |
> > |:---------------------------------------|-------:|:-------:|
> > | ditem 1 can be longer                  | 125.30 |   yes   |
> > | item 2  this is a test of table formng |        1,234.00 ||
> > | item 3 much longer format              |  10.50 |  maybe  |
> > | item 4 short                           |  34.10 | not now |

.
<table>
  <thead>
    <tr><th align="left">Left</th><th align="right">Right</th><th align="center">Center</th></tr>
    <tr><th align="left">Left</th><th align="right">Right</th><th align="center">Center</th></tr>
  </thead>
  <tbody>
    <tr><td align="left">ditem 1 can be longer</td><td align="right">125.30</td><td align="center">yes</td></tr>
    <tr><td align="left">item 2  this is a test of table formng</td><td align="right" colspan="2">1,234.00</td></tr>
    <tr><td align="left">item 3 much longer format</td><td align="right">10.50</td><td align="center">maybe</td></tr>
    <tr><td align="left">item 4 short</td><td align="right">34.10</td><td align="center">not now</td></tr>
  </tbody>
</table>
.
Document[0, 435]
  TableBlock[0, 434]
    TableHead[0, 123]
      TableRow[0, 61] rowNumber=1
        TableCell[0, 42] LEFT header textOpen:[0, 1, "|"] text:[2, 6, "Left"] textClose:[41, 42, "|"]
          Text[2, 6] chars:[2, 6, "Left"]
        TableCell[42, 51] RIGHT header text:[44, 49, "Right"] textClose:[50, 51, "|"]
          Text[44, 49] chars:[44, 49, "Right"]
        TableCell[51, 61] CENTER header text:[52, 58, "Center"] textClose:[60, 61, "|"]
          Text[52, 58] chars:[52, 58, "Center"]
      TableRow[62, 123] rowNumber=2
        TableCell[62, 104] LEFT header textOpen:[62, 63, "|"] text:[64, 68, "Left"] textClose:[103, 104, "|"]
          Text[64, 68] chars:[64, 68, "Left"]
        TableCell[104, 113] RIGHT header text:[106, 111, "Right"] textClose:[112, 113, "|"]
          Text[106, 111] chars:[106, 111, "Right"]
        TableCell[113, 123] CENTER header text:[114, 120, "Center"] textClose:[122, 123, "|"]
          Text[114, 120] chars:[114, 120, "Center"]
    TableSeparator[124, 185]
      TableRow[124, 185]
        TableCell[124, 166] LEFT textOpen:[124, 125, "|"] text:[125, 165, ":---------------------------------------"] textClose:[165, 166, "|"]
          Text[125, 165] chars:[125, 165, ":---- … -----"]
        TableCell[166, 175] RIGHT text:[166, 174, "-------:"] textClose:[174, 175, "|"]
          Text[166, 174] chars:[166, 174, "-------:"]
        TableCell[175, 185] CENTER text:[175, 184, ":-------:"] textClose:[184, 185, "|"]
          Text[175, 184] chars:[175, 184, ":-------:"]
    TableBody[186, 433]
      TableRow[186, 247] rowNumber=1
        TableCell[186, 228] LEFT textOpen:[186, 187, "|"] text:[188, 209, "ditem 1 can be longer"] textClose:[227, 228, "|"]
          Text[188, 209] chars:[188, 209, "ditem … onger"]
        TableCell[228, 237] RIGHT text:[229, 235, "125.30"] textClose:[236, 237, "|"]
          Text[229, 235] chars:[229, 235, "125.30"]
        TableCell[237, 247] CENTER text:[240, 243, "yes"] textClose:[246, 247, "|"]
          Text[240, 243] chars:[240, 243, "yes"]
      TableRow[248, 309] rowNumber=2
        TableCell[248, 290] LEFT textOpen:[248, 249, "|"] text:[250, 288, "item 2  this is a test of table formng"] textClose:[289, 290, "|"]
          Text[250, 288] chars:[250, 288, "item  … ormng"]
        TableCell[290, 309] RIGHT span=2 text:[298, 306, "1,234.00"] textClose:[307, 309, "||"]
          Text[298, 306] chars:[298, 306, "1,234.00"]
      TableRow[310, 371] rowNumber=3
        TableCell[310, 352] LEFT textOpen:[310, 311, "|"] text:[312, 337, "item 3 much longer format"] textClose:[351, 352, "|"]
          Text[312, 337] chars:[312, 337, "item  … ormat"]
        TableCell[352, 361] RIGHT text:[354, 359, "10.50"] textClose:[360, 361, "|"]
          Text[354, 359] chars:[354, 359, "10.50"]
        TableCell[361, 371] CENTER text:[363, 368, "maybe"] textClose:[370, 371, "|"]
          Text[363, 368] chars:[363, 368, "maybe"]
      TableRow[372, 433] rowNumber=4
        TableCell[372, 414] LEFT textOpen:[372, 373, "|"] text:[374, 386, "item 4 short"] textClose:[413, 414, "|"]
          Text[374, 386] chars:[374, 386, "item  … short"]
        TableCell[414, 423] RIGHT text:[416, 421, "34.10"] textClose:[422, 423, "|"]
          Text[416, 421] chars:[416, 421, "34.10"]
        TableCell[423, 433] CENTER text:[424, 431, "not now"] textClose:[432, 433, "|"]
          Text[424, 431] chars:[424, 431, "not now"]
````````````````````````````````


### 365

Issue [#365, table does not render correctly]

```````````````````````````````` example Issue - 365: 1
name|age
-|-
Tom|23
Jack|25
.
<p>name|age
-|-
Tom|23
Jack|25</p>
.
Document[0, 27]
  Paragraph[0, 27]
    Text[0, 8] chars:[0, 8, "name|age"]
    SoftLineBreak[8, 9]
    Text[9, 12] chars:[9, 12, "-|-"]
    SoftLineBreak[12, 13]
    Text[13, 19] chars:[13, 19, "Tom|23"]
    SoftLineBreak[19, 20]
    Text[20, 27] chars:[20, 27, "Jack|25"]
````````````````````````````````


```````````````````````````````` example(Issue - 365: 2) options(min-dashes-1)
name|age
-|-
Tom|23
Jack|25
.
<table>
  <thead>
    <tr><th>name</th><th>age</th></tr>
  </thead>
  <tbody>
    <tr><td>Tom</td><td>23</td></tr>
    <tr><td>Jack</td><td>25</td></tr>
  </tbody>
</table>
.
Document[0, 27]
  TableBlock[0, 27]
    TableHead[0, 8]
      TableRow[0, 8] rowNumber=1
        TableCell[0, 5] header text:[0, 4, "name"] textClose:[4, 5, "|"]
          Text[0, 4] chars:[0, 4, "name"]
        TableCell[5, 8] header text:[5, 8, "age"]
          Text[5, 8] chars:[5, 8, "age"]
    TableSeparator[9, 12]
      TableRow[9, 12]
        TableCell[9, 11] text:[9, 10, "-"] textClose:[10, 11, "|"]
          Text[9, 10] chars:[9, 10, "-"]
        TableCell[11, 12] text:[11, 12, "-"]
          Text[11, 12] chars:[11, 12, "-"]
    TableBody[13, 27]
      TableRow[13, 19] rowNumber=1
        TableCell[13, 17] text:[13, 16, "Tom"] textClose:[16, 17, "|"]
          Text[13, 16] chars:[13, 16, "Tom"]
        TableCell[17, 19] text:[17, 19, "23"]
          Text[17, 19] chars:[17, 19, "23"]
      TableRow[20, 27] rowNumber=2
        TableCell[20, 25] text:[20, 24, "Jack"] textClose:[24, 25, "|"]
          Text[20, 24] chars:[20, 24, "Jack"]
        TableCell[25, 27] text:[25, 27, "25"]
          Text[25, 27] chars:[25, 27, "25"]
````````````````````````````````


### 376

Issue [#376, convert markdown to html], inline delimiters ignore custom chars

```````````````````````````````` example Issue - 376: 1
| header1 | header2 | header3 |
|---------|---------|---------|
| 1*2     | 2*1     | *1*     |
.
<table>
  <thead>
    <tr><th>header1</th><th>header2</th><th>header3</th></tr>
  </thead>
  <tbody>
    <tr><td>1*2</td><td>2*1</td><td><em>1</em></td></tr>
  </tbody>
</table>
.
Document[0, 95]
  TableBlock[0, 95]
    TableHead[0, 31]
      TableRow[0, 31] rowNumber=1
        TableCell[0, 11] header textOpen:[0, 1, "|"] text:[2, 9, "header1"] textClose:[10, 11, "|"]
          Text[2, 9] chars:[2, 9, "header1"]
        TableCell[11, 21] header text:[12, 19, "header2"] textClose:[20, 21, "|"]
          Text[12, 19] chars:[12, 19, "header2"]
        TableCell[21, 31] header text:[22, 29, "header3"] textClose:[30, 31, "|"]
          Text[22, 29] chars:[22, 29, "header3"]
    TableSeparator[32, 63]
      TableRow[32, 63]
        TableCell[32, 43] textOpen:[32, 33, "|"] text:[33, 42, "---------"] textClose:[42, 43, "|"]
          Text[33, 42] chars:[33, 42, "---------"]
        TableCell[43, 53] text:[43, 52, "---------"] textClose:[52, 53, "|"]
          Text[43, 52] chars:[43, 52, "---------"]
        TableCell[53, 63] text:[53, 62, "---------"] textClose:[62, 63, "|"]
          Text[53, 62] chars:[53, 62, "---------"]
    TableBody[64, 95]
      TableRow[64, 95] rowNumber=1
        TableCell[64, 75] textOpen:[64, 65, "|"] text:[66, 69, "1*2"] textClose:[74, 75, "|"]
          Text[66, 69] chars:[66, 69, "1*2"]
        TableCell[75, 85] text:[76, 79, "2*1"] textClose:[84, 85, "|"]
          Text[76, 79] chars:[76, 79, "2*1"]
        TableCell[85, 95] text:[86, 89, "*1*"] textClose:[94, 95, "|"]
          Emphasis[86, 89] textOpen:[86, 87, "*"] text:[87, 88, "1"] textClose:[88, 89, "*"]
            Text[87, 88] chars:[87, 88, "1"]
          Text[89, 89]
````````````````````````````````


[#365, table does not render correctly]: https://github.com/vsch/flexmark-java/issues/365
[#376, convert markdown to html]: https://github.com/vsch/flexmark-java/issues/376

