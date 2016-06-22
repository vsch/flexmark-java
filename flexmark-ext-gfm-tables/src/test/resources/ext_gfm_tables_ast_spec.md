---
title: Gfm Tables Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Gfm Tables Extension

Converts pipe separated tables to html tables

The tests here are converted to commonmark spec.txt format and AST expected results added.

```````````````````````````````` example Gfm Tables Extension: 1
Abc|Def
.
<p>Abc|Def</p>
.
Document[0, 8]
  Paragraph[0, 8]
    Text[0, 7] chars:[0, 7, "Abc|Def"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 2
Abc | Def
.
<p>Abc | Def</p>
.
Document[0, 10]
  Paragraph[0, 10]
    Text[0, 9] chars:[0, 9, "Abc | Def"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 3
Abc|Def
-|-
.
<p>Abc|Def
-|-</p>
.
Document[0, 12]
  Paragraph[0, 12]
    Text[0, 7] chars:[0, 7, "Abc|Def"]
    SoftLineBreak[7, 8]
    Text[8, 11] chars:[8, 11, "-|-"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 4
Abc|Def
--|--
.
<p>Abc|Def
--|--</p>
.
Document[0, 14]
  Paragraph[0, 14]
    Text[0, 7] chars:[0, 7, "Abc|Def"]
    SoftLineBreak[7, 8]
    Text[8, 13] chars:[8, 13, "--|--"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 5
Abc|Def
 |---|---
.
<p>Abc|Def
|---|---</p>
.
Document[0, 18]
  Paragraph[0, 18]
    Text[0, 7] chars:[0, 7, "Abc|Def"]
    SoftLineBreak[7, 8]
    Text[9, 17] chars:[9, 17, "|---|---"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 6
No
Abc|Def
---|---
.
<p>No
Abc|Def
---|---</p>
.
Document[0, 19]
  Paragraph[0, 19]
    Text[0, 2] chars:[0, 2, "No"]
    SoftLineBreak[2, 3]
    Text[3, 10] chars:[3, 10, "Abc|Def"]
    SoftLineBreak[10, 11]
    Text[11, 18] chars:[11, 18, "---|---"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 7
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
Document[0, 16]
  TableBlock[0, 16]
    TableHead[0, 7]
      TableRow[0, 7]
        TableCell[0, 3] header textOpen:[0, 0] text:[0, 3, "Abc"] textClose:[0, 0]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header textOpen:[0, 0] text:[4, 7, "Def"] textClose:[0, 0]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 11] textOpen:[0, 0] text:[8, 11, "---"] textClose:[0, 0]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] textOpen:[0, 0] text:[12, 15, "---"] textClose:[0, 0]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[0, 0]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 8
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
Document[0, 10]
  TableBlock[0, 10]
    TableHead[0, 4]
      TableRow[0, 4]
        TableCell[1, 4] header textOpen:[0, 0] text:[1, 4, "Abc"] textClose:[0, 0]
          Text[1, 4] chars:[1, 4, "Abc"]
    TableSeparator[5, 9]
      TableRow[5, 9]
        TableCell[6, 9] textOpen:[0, 0] text:[6, 9, "---"] textClose:[0, 0]
          Text[6, 9] chars:[6, 9, "---"]
    TableBody[0, 0]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 9
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
Document[0, 12]
  TableBlock[0, 12]
    TableHead[0, 5]
      TableRow[0, 5]
        TableCell[1, 4] header textOpen:[0, 0] text:[1, 4, "Abc"] textClose:[0, 0]
          Text[1, 4] chars:[1, 4, "Abc"]
    TableSeparator[6, 11]
      TableRow[6, 11]
        TableCell[7, 10] textOpen:[0, 0] text:[7, 10, "---"] textClose:[0, 0]
          Text[7, 10] chars:[7, 10, "---"]
    TableBody[0, 0]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 10
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
Document[0, 10]
  TableBlock[0, 10]
    TableHead[0, 4]
      TableRow[0, 4]
        TableCell[0, 3] header textOpen:[0, 0] text:[0, 3, "Abc"] textClose:[0, 0]
          Text[0, 3] chars:[0, 3, "Abc"]
    TableSeparator[5, 9]
      TableRow[5, 9]
        TableCell[5, 8] textOpen:[0, 0] text:[5, 8, "---"] textClose:[0, 0]
          Text[5, 8] chars:[5, 8, "---"]
    TableBody[0, 0]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 11
|Abc
---
.
<h2>|Abc</h2>
.
Document[0, 9]
  Heading[0, 8] textOpen:[0, 0] text:[0, 4, "|Abc"] textClose:[5, 8, "---"]
    Text[0, 4] chars:[0, 4, "|Abc"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 12
Abc
|---
.
<p>Abc
|---</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 3] chars:[0, 3, "Abc"]
    SoftLineBreak[3, 4]
    Text[4, 8] chars:[4, 8, "|---"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 13
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
Document[0, 13]
  TableBlock[0, 13]
    TableHead[0, 4]
      TableRow[0, 4]
        TableCell[1, 4] header textOpen:[0, 0] text:[1, 4, "Abc"] textClose:[0, 0]
          Text[1, 4] chars:[1, 4, "Abc"]
    TableSeparator[5, 9]
      TableRow[5, 9]
        TableCell[6, 9] textOpen:[0, 0] text:[6, 9, "---"] textClose:[0, 0]
          Text[6, 9] chars:[6, 9, "---"]
    TableBody[10, 12]
      TableRow[10, 12]
        TableCell[11, 12] textOpen:[0, 0] text:[11, 12, "1"] textClose:[0, 0]
          Text[11, 12] chars:[11, 12, "1"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 14
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
Document[0, 16]
  TableBlock[0, 16]
    TableHead[0, 5]
      TableRow[0, 5]
        TableCell[1, 4] header textOpen:[0, 0] text:[1, 4, "Abc"] textClose:[0, 0]
          Text[1, 4] chars:[1, 4, "Abc"]
    TableSeparator[6, 11]
      TableRow[6, 11]
        TableCell[7, 10] textOpen:[0, 0] text:[7, 10, "---"] textClose:[0, 0]
          Text[7, 10] chars:[7, 10, "---"]
    TableBody[12, 15]
      TableRow[12, 15]
        TableCell[13, 14] textOpen:[0, 0] text:[13, 14, "1"] textClose:[0, 0]
          Text[13, 14] chars:[13, 14, "1"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 15
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
Document[0, 13]
  TableBlock[0, 13]
    TableHead[0, 4]
      TableRow[0, 4]
        TableCell[0, 3] header textOpen:[0, 0] text:[0, 3, "Abc"] textClose:[0, 0]
          Text[0, 3] chars:[0, 3, "Abc"]
    TableSeparator[5, 9]
      TableRow[5, 9]
        TableCell[5, 8] textOpen:[0, 0] text:[5, 8, "---"] textClose:[0, 0]
          Text[5, 8] chars:[5, 8, "---"]
    TableBody[10, 12]
      TableRow[10, 12]
        TableCell[10, 11] textOpen:[0, 0] text:[10, 11, "1"] textClose:[0, 0]
          Text[10, 11] chars:[10, 11, "1"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 16
|Abc
---
|1
.
<h2>|Abc</h2>
<p>|1</p>
.
Document[0, 12]
  Heading[0, 8] textOpen:[0, 0] text:[0, 4, "|Abc"] textClose:[5, 8, "---"]
    Text[0, 4] chars:[0, 4, "|Abc"]
  Paragraph[9, 12]
    Text[9, 11] chars:[9, 11, "|1"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 17
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
Document[0, 12]
  TableBlock[0, 10]
    TableHead[0, 4]
      TableRow[0, 4]
        TableCell[1, 4] header textOpen:[0, 0] text:[1, 4, "Abc"] textClose:[0, 0]
          Text[1, 4] chars:[1, 4, "Abc"]
    TableSeparator[5, 9]
      TableRow[5, 9]
        TableCell[6, 9] textOpen:[0, 0] text:[6, 9, "---"] textClose:[0, 0]
          Text[6, 9] chars:[6, 9, "---"]
    TableBody[0, 0]
  Paragraph[10, 12]
    Text[10, 11] chars:[10, 11, "1"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 18
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
Document[0, 20]
  TableBlock[0, 20]
    TableHead[0, 7]
      TableRow[0, 7]
        TableCell[0, 3] header textOpen:[0, 0] text:[0, 3, "Abc"] textClose:[0, 0]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header textOpen:[0, 0] text:[4, 7, "Def"] textClose:[0, 0]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 11] textOpen:[0, 0] text:[8, 11, "---"] textClose:[0, 0]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] textOpen:[0, 0] text:[12, 15, "---"] textClose:[0, 0]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 19]
      TableRow[16, 19]
        TableCell[16, 17] textOpen:[0, 0] text:[16, 17, "1"] textClose:[0, 0]
          Text[16, 17] chars:[16, 17, "1"]
        TableCell[18, 19] textOpen:[0, 0] text:[18, 19, "2"] textClose:[0, 0]
          Text[18, 19] chars:[18, 19, "2"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 19
Abc|Def|Ghi
---|---
1|2|3
.
<p>Abc|Def|Ghi
---|---
1|2|3</p>
.
Document[0, 26]
  Paragraph[0, 26]
    Text[0, 11] chars:[0, 11, "Abc|D"..."f|Ghi"]
    SoftLineBreak[11, 12]
    Text[12, 19] chars:[12, 19, "---|---"]
    SoftLineBreak[19, 20]
    Text[20, 25] chars:[20, 25, "1|2|3"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 20
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
Document[0, 30]
  TableBlock[1, 30]
    TableHead[1, 11]
      TableRow[1, 11]
        TableCell[1, 4] header textOpen:[0, 0] text:[1, 4, "Abc"] textClose:[0, 0]
          Text[1, 4] chars:[1, 4, "Abc"]
        TableCell[8, 11] header textOpen:[0, 0] text:[8, 11, "Def"] textClose:[0, 0]
          Text[8, 11] chars:[8, 11, "Def"]
    TableSeparator[12, 22]
      TableRow[12, 22]
        TableCell[13, 16] textOpen:[0, 0] text:[13, 16, "---"] textClose:[0, 0]
          Text[13, 16] chars:[13, 16, "---"]
        TableCell[19, 22] textOpen:[0, 0] text:[19, 22, "---"] textClose:[0, 0]
          Text[19, 22] chars:[19, 22, "---"]
    TableBody[23, 29]
      TableRow[23, 29]
        TableCell[24, 25] textOpen:[0, 0] text:[24, 25, "1"] textClose:[0, 0]
          Text[24, 25] chars:[24, 25, "1"]
        TableCell[28, 29] textOpen:[0, 0] text:[28, 29, "2"] textClose:[0, 0]
          Text[28, 29] chars:[28, 29, "2"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 21
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
Document[0, 24]
  TableBlock[0, 24]
    TableHead[0, 7]
      TableRow[0, 7]
        TableCell[0, 3] header textOpen:[0, 0] text:[0, 3, "Abc"] textClose:[0, 0]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header textOpen:[0, 0] text:[4, 7, "Def"] textClose:[0, 0]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 11] textOpen:[0, 0] text:[8, 11, "---"] textClose:[0, 0]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] textOpen:[0, 0] text:[12, 15, "---"] textClose:[0, 0]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 23]
      TableRow[16, 23]
        TableCell[20, 21] textOpen:[0, 0] text:[20, 21, "1"] textClose:[0, 0]
          Text[20, 21] chars:[20, 21, "1"]
        TableCell[22, 23] textOpen:[0, 0] text:[22, 23, "2"] textClose:[0, 0]
          Text[22, 23] chars:[22, 23, "2"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 22
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
Document[0, 26]
  TableBlock[0, 26]
    TableHead[0, 9]
      TableRow[0, 9]
        TableCell[1, 4] header textOpen:[0, 0] text:[1, 4, "Abc"] textClose:[0, 0]
          Text[1, 4] chars:[1, 4, "Abc"]
        TableCell[5, 8] header textOpen:[0, 0] text:[5, 8, "Def"] textClose:[0, 0]
          Text[5, 8] chars:[5, 8, "Def"]
    TableSeparator[10, 19]
      TableRow[10, 19]
        TableCell[11, 14] textOpen:[0, 0] text:[11, 14, "---"] textClose:[0, 0]
          Text[11, 14] chars:[11, 14, "---"]
        TableCell[15, 18] textOpen:[0, 0] text:[15, 18, "---"] textClose:[0, 0]
          Text[15, 18] chars:[15, 18, "---"]
    TableBody[20, 25]
      TableRow[20, 25]
        TableCell[21, 22] textOpen:[0, 0] text:[21, 22, "1"] textClose:[0, 0]
          Text[21, 22] chars:[21, 22, "1"]
        TableCell[23, 24] textOpen:[0, 0] text:[23, 24, "2"] textClose:[0, 0]
          Text[23, 24] chars:[23, 24, "2"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 23
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
Document[0, 22]
  TableBlock[0, 22]
    TableHead[0, 9]
      TableRow[0, 9]
        TableCell[0, 5] header textOpen:[0, 0] text:[0, 5, "*Abc*"] textClose:[0, 0]
          Emphasis[0, 5] textOpen:[0, 1, "*"] text:[1, 4, "Abc"] textClose:[4, 5, "*"]
            Text[1, 4] chars:[1, 4, "Abc"]
        TableCell[6, 9] header textOpen:[0, 0] text:[6, 9, "Def"] textClose:[0, 0]
          Text[6, 9] chars:[6, 9, "Def"]
    TableSeparator[10, 17]
      TableRow[10, 17]
        TableCell[10, 13] textOpen:[0, 0] text:[10, 13, "---"] textClose:[0, 0]
          Text[10, 13] chars:[10, 13, "---"]
        TableCell[14, 17] textOpen:[0, 0] text:[14, 17, "---"] textClose:[0, 0]
          Text[14, 17] chars:[14, 17, "---"]
    TableBody[18, 21]
      TableRow[18, 21]
        TableCell[18, 19] textOpen:[0, 0] text:[18, 19, "1"] textClose:[0, 0]
          Text[18, 19] chars:[18, 19, "1"]
        TableCell[20, 21] textOpen:[0, 0] text:[20, 21, "2"] textClose:[0, 0]
          Text[20, 21] chars:[20, 21, "2"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 24
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
Document[0, 25]
  TableBlock[0, 25]
    TableHead[0, 7]
      TableRow[0, 7]
        TableCell[0, 3] header textOpen:[0, 0] text:[0, 3, "Abc"] textClose:[0, 0]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header textOpen:[0, 0] text:[4, 7, "Def"] textClose:[0, 0]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 11] textOpen:[0, 0] text:[8, 11, "---"] textClose:[0, 0]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] textOpen:[0, 0] text:[12, 15, "---"] textClose:[0, 0]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 24]
      TableRow[16, 24]
        TableCell[16, 19] textOpen:[0, 0] text:[16, 19, "1\\"] textClose:[0, 0]
          Text[16, 19] chars:[16, 19, "1\\"]
        TableCell[20, 21] textOpen:[0, 0] text:[20, 21, "2"] textClose:[0, 0]
          Text[20, 21] chars:[20, 21, "2"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 25
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
Document[0, 24]
  TableBlock[0, 24]
    TableHead[0, 7]
      TableRow[0, 7]
        TableCell[0, 3] header textOpen:[0, 0] text:[0, 3, "Abc"] textClose:[0, 0]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header textOpen:[0, 0] text:[4, 7, "Def"] textClose:[0, 0]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 11] textOpen:[0, 0] text:[8, 11, "---"] textClose:[0, 0]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] textOpen:[0, 0] text:[12, 15, "---"] textClose:[0, 0]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 23]
      TableRow[16, 23]
        TableCell[16, 21] textOpen:[0, 0] text:[16, 21, "1\\\\"] textClose:[0, 0]
          Text[16, 21] chars:[16, 21, "1\\\\"]
        TableCell[22, 23] textOpen:[0, 0] text:[22, 23, "2"] textClose:[0, 0]
          Text[22, 23] chars:[22, 23, "2"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 26
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
Document[0, 21]
  TableBlock[0, 21]
    TableHead[0, 7]
      TableRow[0, 7]
        TableCell[0, 3] LEFT header textOpen:[0, 0] text:[0, 3, "Abc"] textClose:[0, 0]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header textOpen:[0, 0] text:[4, 7, "Def"] textClose:[0, 0]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 16]
      TableRow[8, 16]
        TableCell[8, 12] LEFT textOpen:[0, 0] text:[8, 12, ":---"] textClose:[0, 0]
          Text[8, 12] chars:[8, 12, ":---"]
        TableCell[13, 16] textOpen:[0, 0] text:[13, 16, "---"] textClose:[0, 0]
          Text[13, 16] chars:[13, 16, "---"]
    TableBody[17, 20]
      TableRow[17, 20]
        TableCell[17, 18] LEFT textOpen:[0, 0] text:[17, 18, "1"] textClose:[0, 0]
          Text[17, 18] chars:[17, 18, "1"]
        TableCell[19, 20] textOpen:[0, 0] text:[19, 20, "2"] textClose:[0, 0]
          Text[19, 20] chars:[19, 20, "2"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 27
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
Document[0, 21]
  TableBlock[0, 21]
    TableHead[0, 7]
      TableRow[0, 7]
        TableCell[0, 3] RIGHT header textOpen:[0, 0] text:[0, 3, "Abc"] textClose:[0, 0]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header textOpen:[0, 0] text:[4, 7, "Def"] textClose:[0, 0]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 16]
      TableRow[8, 16]
        TableCell[8, 12] RIGHT textOpen:[0, 0] text:[8, 12, "---:"] textClose:[0, 0]
          Text[8, 12] chars:[8, 12, "---:"]
        TableCell[13, 16] textOpen:[0, 0] text:[13, 16, "---"] textClose:[0, 0]
          Text[13, 16] chars:[13, 16, "---"]
    TableBody[17, 20]
      TableRow[17, 20]
        TableCell[17, 18] RIGHT textOpen:[0, 0] text:[17, 18, "1"] textClose:[0, 0]
          Text[17, 18] chars:[17, 18, "1"]
        TableCell[19, 20] textOpen:[0, 0] text:[19, 20, "2"] textClose:[0, 0]
          Text[19, 20] chars:[19, 20, "2"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 28
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
Document[0, 22]
  TableBlock[0, 22]
    TableHead[0, 7]
      TableRow[0, 7]
        TableCell[0, 3] CENTER header textOpen:[0, 0] text:[0, 3, "Abc"] textClose:[0, 0]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header textOpen:[0, 0] text:[4, 7, "Def"] textClose:[0, 0]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 17]
      TableRow[8, 17]
        TableCell[8, 13] CENTER textOpen:[0, 0] text:[8, 13, ":---:"] textClose:[0, 0]
          Text[8, 13] chars:[8, 13, ":---:"]
        TableCell[14, 17] textOpen:[0, 0] text:[14, 17, "---"] textClose:[0, 0]
          Text[14, 17] chars:[14, 17, "---"]
    TableBody[18, 21]
      TableRow[18, 21]
        TableCell[18, 19] CENTER textOpen:[0, 0] text:[18, 19, "1"] textClose:[0, 0]
          Text[18, 19] chars:[18, 19, "1"]
        TableCell[20, 21] textOpen:[0, 0] text:[20, 21, "2"] textClose:[0, 0]
          Text[20, 21] chars:[20, 21, "2"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 29
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
Document[0, 22]
  TableBlock[0, 22]
    TableHead[0, 7]
      TableRow[0, 7]
        TableCell[0, 3] header textOpen:[0, 0] text:[0, 3, "Abc"] textClose:[0, 0]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] CENTER header textOpen:[0, 0] text:[4, 7, "Def"] textClose:[0, 0]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 17]
      TableRow[8, 17]
        TableCell[8, 11] textOpen:[0, 0] text:[8, 11, "---"] textClose:[0, 0]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 17] CENTER textOpen:[0, 0] text:[12, 17, ":---:"] textClose:[0, 0]
          Text[12, 17] chars:[12, 17, ":---:"]
    TableBody[18, 21]
      TableRow[18, 21]
        TableCell[18, 19] textOpen:[0, 0] text:[18, 19, "1"] textClose:[0, 0]
          Text[18, 19] chars:[18, 19, "1"]
        TableCell[20, 21] CENTER textOpen:[0, 0] text:[20, 21, "2"] textClose:[0, 0]
          Text[20, 21] chars:[20, 21, "2"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 30
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
Document[0, 23]
  TableBlock[0, 23]
    TableHead[0, 7]
      TableRow[0, 7]
        TableCell[0, 3] LEFT header textOpen:[0, 0] text:[0, 3, "Abc"] textClose:[0, 0]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header textOpen:[0, 0] text:[4, 7, "Def"] textClose:[0, 0]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 18]
      TableRow[8, 18]
        TableCell[9, 13] LEFT textOpen:[0, 0] text:[9, 13, ":---"] textClose:[0, 0]
          Text[9, 13] chars:[9, 13, ":---"]
        TableCell[15, 18] textOpen:[0, 0] text:[15, 18, "---"] textClose:[0, 0]
          Text[15, 18] chars:[15, 18, "---"]
    TableBody[19, 22]
      TableRow[19, 22]
        TableCell[19, 20] LEFT textOpen:[0, 0] text:[19, 20, "1"] textClose:[0, 0]
          Text[19, 20] chars:[19, 20, "1"]
        TableCell[21, 22] textOpen:[0, 0] text:[21, 22, "2"] textClose:[0, 0]
          Text[21, 22] chars:[21, 22, "2"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 31
Abc|Def
: ---|--- 
.
<p>Abc|Def
: ---|---</p>
.
Document[0, 19]
  Paragraph[0, 19]
    Text[0, 7] chars:[0, 7, "Abc|Def"]
    SoftLineBreak[7, 8]
    Text[8, 17] chars:[8, 17, ": ---|---"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 32
Abc|Def
--- :|---
.
<p>Abc|Def
--- :|---</p>
.
Document[0, 18]
  Paragraph[0, 18]
    Text[0, 7] chars:[0, 7, "Abc|Def"]
    SoftLineBreak[7, 8]
    Text[8, 17] chars:[8, 17, "--- :|---"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 33
Abc|Def
---|: ---
.
<p>Abc|Def
---|: ---</p>
.
Document[0, 18]
  Paragraph[0, 18]
    Text[0, 7] chars:[0, 7, "Abc|Def"]
    SoftLineBreak[7, 8]
    Text[8, 17] chars:[8, 17, "---|: ---"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 34
Abc|Def
---|--- :
.
<p>Abc|Def
---|--- :</p>
.
Document[0, 18]
  Paragraph[0, 18]
    Text[0, 7] chars:[0, 7, "Abc|Def"]
    SoftLineBreak[7, 8]
    Text[8, 17] chars:[8, 17, "---|--- :"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 35
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
Document[0, 22]
  TableBlock[0, 22]
    TableHead[0, 7]
      TableRow[0, 7]
        TableCell[0, 3] header textOpen:[0, 0] text:[0, 3, "Abc"] textClose:[0, 0]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header textOpen:[0, 0] text:[4, 7, "Def"] textClose:[0, 0]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 11] textOpen:[0, 0] text:[8, 11, "---"] textClose:[0, 0]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] textOpen:[0, 0] text:[12, 15, "---"] textClose:[0, 0]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 21]
      TableRow[16, 21]
        TableCell[16, 17] textOpen:[0, 0] text:[16, 17, "1"] textClose:[0, 0]
          Text[16, 17] chars:[16, 17, "1"]
        TableCell[18, 19] textOpen:[0, 0] text:[18, 19, "2"] textClose:[0, 0]
          Text[18, 19] chars:[18, 19, "2"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 36
Abc|Def|Ghi
---|---|---
1|2
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th><th>Ghi</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td><td></td></tr>
  </tbody>
</table>
.
Document[0, 28]
  TableBlock[0, 28]
    TableHead[0, 11]
      TableRow[0, 11]
        TableCell[0, 3] header textOpen:[0, 0] text:[0, 3, "Abc"] textClose:[0, 0]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header textOpen:[0, 0] text:[4, 7, "Def"] textClose:[0, 0]
          Text[4, 7] chars:[4, 7, "Def"]
        TableCell[8, 11] header textOpen:[0, 0] text:[8, 11, "Ghi"] textClose:[0, 0]
          Text[8, 11] chars:[8, 11, "Ghi"]
    TableSeparator[12, 23]
      TableRow[12, 23]
        TableCell[12, 15] textOpen:[0, 0] text:[12, 15, "---"] textClose:[0, 0]
          Text[12, 15] chars:[12, 15, "---"]
        TableCell[16, 19] textOpen:[0, 0] text:[16, 19, "---"] textClose:[0, 0]
          Text[16, 19] chars:[16, 19, "---"]
        TableCell[20, 23] textOpen:[0, 0] text:[20, 23, "---"] textClose:[0, 0]
          Text[20, 23] chars:[20, 23, "---"]
    TableBody[24, 27]
      TableRow[24, 27]
        TableCell[24, 25] textOpen:[0, 0] text:[24, 25, "1"] textClose:[0, 0]
          Text[24, 25] chars:[24, 25, "1"]
        TableCell[26, 27] textOpen:[0, 0] text:[26, 27, "2"] textClose:[0, 0]
          Text[26, 27] chars:[26, 27, "2"]
        TableCell[0, 0] textOpen:[0, 0] text:[0, 0] textClose:[0, 0]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 37
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
Document[0, 26]
  BlockQuote[0, 26] marker:[0, 1, ">"]
    TableBlock[2, 26]
      TableHead[2, 9]
        TableRow[2, 9]
          TableCell[2, 5] header textOpen:[0, 0] text:[2, 5, "Abc"] textClose:[0, 0]
            Text[2, 5] chars:[2, 5, "Abc"]
          TableCell[6, 9] header textOpen:[0, 0] text:[6, 9, "Def"] textClose:[0, 0]
            Text[6, 9] chars:[6, 9, "Def"]
      TableSeparator[12, 19]
        TableRow[12, 19]
          TableCell[12, 15] textOpen:[0, 0] text:[12, 15, "---"] textClose:[0, 0]
            Text[12, 15] chars:[12, 15, "---"]
          TableCell[16, 19] textOpen:[0, 0] text:[16, 19, "---"] textClose:[0, 0]
            Text[16, 19] chars:[16, 19, "---"]
      TableBody[22, 25]
        TableRow[22, 25]
          TableCell[22, 23] textOpen:[0, 0] text:[22, 23, "1"] textClose:[0, 0]
            Text[22, 23] chars:[22, 23, "1"]
          TableCell[24, 25] textOpen:[0, 0] text:[24, 25, "2"] textClose:[0, 0]
            Text[24, 25] chars:[24, 25, "2"]
````````````````````````````````


```````````````````````````````` example Gfm Tables Extension: 38
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
Document[0, 40]
  TableBlock[0, 20]
    TableHead[0, 7]
      TableRow[0, 7]
        TableCell[0, 3] header textOpen:[0, 0] text:[0, 3, "Abc"] textClose:[0, 0]
          Text[0, 3] chars:[0, 3, "Abc"]
        TableCell[4, 7] header textOpen:[0, 0] text:[4, 7, "Def"] textClose:[0, 0]
          Text[4, 7] chars:[4, 7, "Def"]
    TableSeparator[8, 15]
      TableRow[8, 15]
        TableCell[8, 11] textOpen:[0, 0] text:[8, 11, "---"] textClose:[0, 0]
          Text[8, 11] chars:[8, 11, "---"]
        TableCell[12, 15] textOpen:[0, 0] text:[12, 15, "---"] textClose:[0, 0]
          Text[12, 15] chars:[12, 15, "---"]
    TableBody[16, 19]
      TableRow[16, 19]
        TableCell[16, 17] textOpen:[0, 0] text:[16, 17, "1"] textClose:[0, 0]
          Text[16, 17] chars:[16, 17, "1"]
        TableCell[18, 19] textOpen:[0, 0] text:[18, 19, "2"] textClose:[0, 0]
          Text[18, 19] chars:[18, 19, "2"]
  Paragraph[20, 40]
    Text[20, 39] chars:[20, 39, "table"..." over"]
````````````````````````````````


inlines should be processed

```````````````````````````````` example Gfm Tables Extension: 39
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
Document[0, 68]
  TableBlock[0, 35]
    TableHead[0, 13]
      TableRow[0, 13]
        TableCell[0, 7] header textOpen:[0, 0] text:[0, 7, "**Abc**"] textClose:[0, 0]
          StrongEmphasis[0, 7] textOpen:[0, 2, "**"] text:[2, 5, "Abc"] textClose:[5, 7, "**"]
            Text[2, 5] chars:[2, 5, "Abc"]
        TableCell[8, 13] header textOpen:[0, 0] text:[8, 13, "_Def_"] textClose:[0, 0]
          Emphasis[8, 13] textOpen:[8, 9, "_"] text:[9, 12, "Def"] textClose:[12, 13, "_"]
            Text[9, 12] chars:[9, 12, "Def"]
    TableSeparator[14, 21]
      TableRow[14, 21]
        TableCell[14, 17] textOpen:[0, 0] text:[14, 17, "---"] textClose:[0, 0]
          Text[14, 17] chars:[14, 17, "---"]
        TableCell[18, 21] textOpen:[0, 0] text:[18, 21, "---"] textClose:[0, 0]
          Text[18, 21] chars:[18, 21, "---"]
    TableBody[22, 34]
      TableRow[22, 34]
        TableCell[22, 27] textOpen:[0, 0] text:[22, 27, "[ref]"] textClose:[0, 0]
          LinkRef[22, 27] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[22, 23, "["] reference:[23, 26, "ref"] referenceClose:[26, 27, "]"]
            Text[23, 26] chars:[23, 26, "ref"]
        TableCell[28, 34] textOpen:[0, 0] text:[28, 34, "`code`"] textClose:[0, 0]
          Code[28, 34] textOpen:[28, 29, "`"] text:[29, 33, "code"] textClose:[33, 34, "`"]
  Paragraph[35, 55]
    Text[35, 54] chars:[35, 54, "table"..." over"]
  Reference[56, 67] refOpen:[56, 57, "["] ref:[57, 60, "ref"] refClose:[60, 62, "]:"] urlOpen:[0, 0] url:[63, 67, "/url"] urlClose:[0, 0] titleOpen:[0, 0] title:[0, 0] titleClose:[0, 0]
````````````````````````````````


