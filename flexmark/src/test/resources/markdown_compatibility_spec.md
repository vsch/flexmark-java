---
title: MultiMarkdown Compatibility Spec
author: Vladimir Schneider
version: 0.1
date: '2016-12-03'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

Tests processor compatibility flags for list processing where most of the differences are to be
found.

## Bullet Mismatch Starts a New List

Test to see if bullet mismatch starts a new list

```````````````````````````````` example Bullet Mismatch Starts a New List: 1
- item
+ item
* item

.
<ul>
    <li>item</li>
    <li>item</li>
    <li>item</li>
</ul>
.
Document[0, 22]
  BulletList[0, 21] isTight
    BulletListItem[0, 7] open:[0, 1, "-"] isTight
      Paragraph[2, 7]
        Text[2, 6] chars:[2, 6, "item"]
    BulletListItem[7, 14] open:[7, 8, "+"] isTight
      Paragraph[9, 14]
        Text[9, 13] chars:[9, 13, "item"]
    BulletListItem[14, 21] open:[14, 15, "*"] isTight hadBlankLineAfter
      Paragraph[16, 21]
        Text[16, 20] chars:[16, 20, "item"]
````````````````````````````````



## Ordered List Item Sets List Start

Test to see if ordered list item will set list start if not one

```````````````````````````````` example Ordered List Item Sets List Start: 1
2. Non One Start Item
.
<ol>
    <li>Non One Start Item</li>
</ol>
.
Document[0, 22]
  OrderedList[0, 22] isTight start:2 delimiter:'.'
    OrderedListItem[0, 22] open:[0, 2, "2."] isTight
      Paragraph[3, 22]
        Text[3, 21] chars:[3, 21, "Non O …  Item"]
````````````````````````````````



## Mismatched List Item Type Handling

Test how mismatches in item types are handled

```````````````````````````````` example Mismatched List Item Type Handling: 1
- Bullet List
1. With Ordered Item
.
<ul>
    <li>Bullet List</li>
    <li>With Ordered Item</li>
</ul>
.
Document[0, 35]
  BulletList[0, 35] isTight
    BulletListItem[0, 14] open:[0, 1, "-"] isTight
      Paragraph[2, 14]
        Text[2, 13] chars:[2, 13, "Bulle …  List"]
    OrderedListItem[14, 35] open:[14, 16, "1."] isTight
      Paragraph[17, 35]
        Text[17, 34] chars:[17, 34, "With  …  Item"]
````````````````````````````````



```````````````````````````````` example Mismatched List Item Type Handling: 2
1. Ordered Item
- With Bullet List
.
<ol>
    <li>Ordered Item</li>
    <li>With Bullet List</li>
</ol>
.
Document[0, 35]
  OrderedList[0, 35] isTight delimiter:'.'
    OrderedListItem[0, 16] open:[0, 2, "1."] isTight
      Paragraph[3, 16]
        Text[3, 15] chars:[3, 15, "Order …  Item"]
    BulletListItem[16, 35] open:[16, 17, "-"] isTight
      Paragraph[18, 35]
        Text[18, 34] chars:[18, 34, "With  …  List"]
````````````````````````````````



## Loose Item Handling

Tests how all tight items are generated

```````````````````````````````` example Loose Item Handling: 1
- item 1
- item 2 
- item 3 
- item 4 
.
<ul>
    <li>item 1</li>
    <li>item 2</li>
    <li>item 3</li>
    <li>item 4</li>
</ul>
.
Document[0, 39]
  BulletList[0, 39] isTight
    BulletListItem[0, 9] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 19] open:[9, 10, "-"] isTight
      Paragraph[11, 19]
        Text[11, 17] chars:[11, 17, "item 2"]
    BulletListItem[19, 29] open:[19, 20, "-"] isTight
      Paragraph[21, 29]
        Text[21, 27] chars:[21, 27, "item 3"]
    BulletListItem[29, 39] open:[29, 30, "-"] isTight
      Paragraph[31, 39]
        Text[31, 37] chars:[31, 37, "item 4"]
````````````````````````````````



Test to see how trailing blank after item determines looseness

```````````````````````````````` example Loose Item Handling: 2
- item 1

- item 2 
- item 3 
- item 4 
.
<ul>
    <li>
        <p>item 1</p>
    </li>
    <li>
        <p>item 2</p>
    </li>
    <li>item 3</li>
    <li>item 4</li>
</ul>
.
Document[0, 40]
  BulletList[0, 40] isTight
    BulletListItem[0, 9] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[10, 20] open:[10, 11, "-"] isLoose
      Paragraph[12, 20]
        Text[12, 18] chars:[12, 18, "item 2"]
    BulletListItem[20, 30] open:[20, 21, "-"] isTight
      Paragraph[22, 30]
        Text[22, 28] chars:[22, 28, "item 3"]
    BulletListItem[30, 40] open:[30, 31, "-"] isTight
      Paragraph[32, 40]
        Text[32, 38] chars:[32, 38, "item 4"]
````````````````````````````````



```````````````````````````````` example Loose Item Handling: 3
- item 1
- item 2 

- item 3 
- item 4 
.
<ul>
    <li>item 1</li>
    <li>
        <p>item 2</p>
    </li>
    <li>
        <p>item 3</p>
    </li>
    <li>item 4</li>
</ul>
.
Document[0, 40]
  BulletList[0, 40] isTight
    BulletListItem[0, 9] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 19] open:[9, 10, "-"] isLoose hadBlankLineAfter
      Paragraph[11, 19]
        Text[11, 17] chars:[11, 17, "item 2"]
    BulletListItem[20, 30] open:[20, 21, "-"] isLoose
      Paragraph[22, 30]
        Text[22, 28] chars:[22, 28, "item 3"]
    BulletListItem[30, 40] open:[30, 31, "-"] isTight
      Paragraph[32, 40]
        Text[32, 38] chars:[32, 38, "item 4"]
````````````````````````````````



```````````````````````````````` example Loose Item Handling: 4
- item 1
- item 2 
- item 3 

- item 4 
.
<ul>
    <li>item 1</li>
    <li>item 2</li>
    <li>
        <p>item 3</p>
    </li>
    <li>
        <p>item 4</p>
    </li>
</ul>
.
Document[0, 40]
  BulletList[0, 40] isTight
    BulletListItem[0, 9] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 19] open:[9, 10, "-"] isTight
      Paragraph[11, 19]
        Text[11, 17] chars:[11, 17, "item 2"]
    BulletListItem[19, 29] open:[19, 20, "-"] isLoose hadBlankLineAfter
      Paragraph[21, 29]
        Text[21, 27] chars:[21, 27, "item 3"]
    BulletListItem[30, 40] open:[30, 31, "-"] isLoose
      Paragraph[32, 40]
        Text[32, 38] chars:[32, 38, "item 4"]
````````````````````````````````



Test looseness with child items

```````````````````````````````` example Loose Item Handling: 5
- item 1
    - item 1.1
- item 2 
    - item 2.1 
- item 3 
    - item 3.1 
- item 4 
    - item 4.1 
.
<ul>
    <li>item 1
        <ul>
            <li>item 1.1</li>
        </ul>
    </li>
    <li>item 2
        <ul>
            <li>item 2.1</li>
        </ul>
    </li>
    <li>item 3
        <ul>
            <li>item 3.1</li>
        </ul>
    </li>
    <li>item 4
        <ul>
            <li>item 4.1</li>
        </ul>
    </li>
</ul>
.
Document[0, 102]
  BulletList[0, 102] isTight
    BulletListItem[0, 24] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[13, 24] isTight
        BulletListItem[13, 24] open:[13, 14, "-"] isTight
          Paragraph[15, 24]
            Text[15, 23] chars:[15, 23, "item 1.1"]
    BulletListItem[24, 50] open:[24, 25, "-"] isTight
      Paragraph[26, 34]
        Text[26, 32] chars:[26, 32, "item 2"]
      BulletList[38, 50] isTight
        BulletListItem[38, 50] open:[38, 39, "-"] isTight
          Paragraph[40, 50]
            Text[40, 48] chars:[40, 48, "item 2.1"]
    BulletListItem[50, 76] open:[50, 51, "-"] isTight
      Paragraph[52, 60]
        Text[52, 58] chars:[52, 58, "item 3"]
      BulletList[64, 76] isTight
        BulletListItem[64, 76] open:[64, 65, "-"] isTight
          Paragraph[66, 76]
            Text[66, 74] chars:[66, 74, "item 3.1"]
    BulletListItem[76, 102] open:[76, 77, "-"] isTight
      Paragraph[78, 86]
        Text[78, 84] chars:[78, 84, "item 4"]
      BulletList[90, 102] isTight
        BulletListItem[90, 102] open:[90, 91, "-"] isTight
          Paragraph[92, 102]
            Text[92, 100] chars:[92, 100, "item 4.1"]
````````````````````````````````



```````````````````````````````` example Loose Item Handling: 6
- item 1

    - item 1.1
- item 2 
    - item 2.1 
- item 3 
    - item 3.1 
- item 4 
    - item 4.1 
.
<ul>
    <li>
        <p>item 1</p>
        <ul>
            <li>item 1.1</li>
        </ul>
    </li>
    <li>item 2
        <ul>
            <li>item 2.1</li>
        </ul>
    </li>
    <li>item 3
        <ul>
            <li>item 3.1</li>
        </ul>
    </li>
    <li>item 4
        <ul>
            <li>item 4.1</li>
        </ul>
    </li>
</ul>
.
Document[0, 103]
  BulletList[0, 103] isTight
    BulletListItem[0, 25] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[14, 25] isTight
        BulletListItem[14, 25] open:[14, 15, "-"] isTight
          Paragraph[16, 25]
            Text[16, 24] chars:[16, 24, "item 1.1"]
    BulletListItem[25, 51] open:[25, 26, "-"] isTight
      Paragraph[27, 35]
        Text[27, 33] chars:[27, 33, "item 2"]
      BulletList[39, 51] isTight
        BulletListItem[39, 51] open:[39, 40, "-"] isTight
          Paragraph[41, 51]
            Text[41, 49] chars:[41, 49, "item 2.1"]
    BulletListItem[51, 77] open:[51, 52, "-"] isTight
      Paragraph[53, 61]
        Text[53, 59] chars:[53, 59, "item 3"]
      BulletList[65, 77] isTight
        BulletListItem[65, 77] open:[65, 66, "-"] isTight
          Paragraph[67, 77]
            Text[67, 75] chars:[67, 75, "item 3.1"]
    BulletListItem[77, 103] open:[77, 78, "-"] isTight
      Paragraph[79, 87]
        Text[79, 85] chars:[79, 85, "item 4"]
      BulletList[91, 103] isTight
        BulletListItem[91, 103] open:[91, 92, "-"] isTight
          Paragraph[93, 103]
            Text[93, 101] chars:[93, 101, "item 4.1"]
````````````````````````````````



```````````````````````````````` example Loose Item Handling: 7
- item 1
    - item 1.1

- item 2 
    - item 2.1 
- item 3 
    - item 3.1 
- item 4 
    - item 4.1 
.
<ul>
    <li>
        <p>item 1</p>
        <ul>
            <li>item 1.1</li>
        </ul>
    </li>
    <li>
        <p>item 2</p>
        <ul>
            <li>item 2.1</li>
        </ul>
    </li>
    <li>item 3
        <ul>
            <li>item 3.1</li>
        </ul>
    </li>
    <li>item 4
        <ul>
            <li>item 4.1</li>
        </ul>
    </li>
</ul>
.
Document[0, 103]
  BulletList[0, 103] isTight
    BulletListItem[0, 24] open:[0, 1, "-"] isLoose
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[13, 24] isTight
        BulletListItem[13, 24] open:[13, 14, "-"] isTight hadBlankLineAfter
          Paragraph[15, 24]
            Text[15, 23] chars:[15, 23, "item 1.1"]
    BulletListItem[25, 51] open:[25, 26, "-"] isLoose
      Paragraph[27, 35]
        Text[27, 33] chars:[27, 33, "item 2"]
      BulletList[39, 51] isTight
        BulletListItem[39, 51] open:[39, 40, "-"] isTight
          Paragraph[41, 51]
            Text[41, 49] chars:[41, 49, "item 2.1"]
    BulletListItem[51, 77] open:[51, 52, "-"] isTight
      Paragraph[53, 61]
        Text[53, 59] chars:[53, 59, "item 3"]
      BulletList[65, 77] isTight
        BulletListItem[65, 77] open:[65, 66, "-"] isTight
          Paragraph[67, 77]
            Text[67, 75] chars:[67, 75, "item 3.1"]
    BulletListItem[77, 103] open:[77, 78, "-"] isTight
      Paragraph[79, 87]
        Text[79, 85] chars:[79, 85, "item 4"]
      BulletList[91, 103] isTight
        BulletListItem[91, 103] open:[91, 92, "-"] isTight
          Paragraph[93, 103]
            Text[93, 101] chars:[93, 101, "item 4.1"]
````````````````````````````````



```````````````````````````````` example Loose Item Handling: 8
- item 1
    - item 1.1
- item 2 

    - item 2.1 
- item 3 
    - item 3.1 
- item 4 
    - item 4.1 
.
<ul>
    <li>item 1
        <ul>
            <li>item 1.1</li>
        </ul>
    </li>
    <li>
        <p>item 2</p>
        <ul>
            <li>item 2.1</li>
        </ul>
    </li>
    <li>item 3
        <ul>
            <li>item 3.1</li>
        </ul>
    </li>
    <li>item 4
        <ul>
            <li>item 4.1</li>
        </ul>
    </li>
</ul>
.
Document[0, 103]
  BulletList[0, 103] isTight
    BulletListItem[0, 24] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[13, 24] isTight
        BulletListItem[13, 24] open:[13, 14, "-"] isTight
          Paragraph[15, 24]
            Text[15, 23] chars:[15, 23, "item 1.1"]
    BulletListItem[24, 51] open:[24, 25, "-"] isLoose hadBlankLineAfter
      Paragraph[26, 34]
        Text[26, 32] chars:[26, 32, "item 2"]
      BulletList[39, 51] isTight
        BulletListItem[39, 51] open:[39, 40, "-"] isTight
          Paragraph[41, 51]
            Text[41, 49] chars:[41, 49, "item 2.1"]
    BulletListItem[51, 77] open:[51, 52, "-"] isTight
      Paragraph[53, 61]
        Text[53, 59] chars:[53, 59, "item 3"]
      BulletList[65, 77] isTight
        BulletListItem[65, 77] open:[65, 66, "-"] isTight
          Paragraph[67, 77]
            Text[67, 75] chars:[67, 75, "item 3.1"]
    BulletListItem[77, 103] open:[77, 78, "-"] isTight
      Paragraph[79, 87]
        Text[79, 85] chars:[79, 85, "item 4"]
      BulletList[91, 103] isTight
        BulletListItem[91, 103] open:[91, 92, "-"] isTight
          Paragraph[93, 103]
            Text[93, 101] chars:[93, 101, "item 4.1"]
````````````````````````````````



```````````````````````````````` example Loose Item Handling: 9
- item 1
    - item 1.1
- item 2 
    - item 2.1 

- item 3 
    - item 3.1 
- item 4 
    - item 4.1 
.
<ul>
    <li>item 1
        <ul>
            <li>item 1.1</li>
        </ul>
    </li>
    <li>
        <p>item 2</p>
        <ul>
            <li>item 2.1</li>
        </ul>
    </li>
    <li>
        <p>item 3</p>
        <ul>
            <li>item 3.1</li>
        </ul>
    </li>
    <li>item 4
        <ul>
            <li>item 4.1</li>
        </ul>
    </li>
</ul>
.
Document[0, 103]
  BulletList[0, 103] isTight
    BulletListItem[0, 24] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[13, 24] isTight
        BulletListItem[13, 24] open:[13, 14, "-"] isTight
          Paragraph[15, 24]
            Text[15, 23] chars:[15, 23, "item 1.1"]
    BulletListItem[24, 50] open:[24, 25, "-"] isLoose
      Paragraph[26, 34]
        Text[26, 32] chars:[26, 32, "item 2"]
      BulletList[38, 50] isTight
        BulletListItem[38, 50] open:[38, 39, "-"] isTight hadBlankLineAfter
          Paragraph[40, 50]
            Text[40, 48] chars:[40, 48, "item 2.1"]
    BulletListItem[51, 77] open:[51, 52, "-"] isLoose
      Paragraph[53, 61]
        Text[53, 59] chars:[53, 59, "item 3"]
      BulletList[65, 77] isTight
        BulletListItem[65, 77] open:[65, 66, "-"] isTight
          Paragraph[67, 77]
            Text[67, 75] chars:[67, 75, "item 3.1"]
    BulletListItem[77, 103] open:[77, 78, "-"] isTight
      Paragraph[79, 87]
        Text[79, 85] chars:[79, 85, "item 4"]
      BulletList[91, 103] isTight
        BulletListItem[91, 103] open:[91, 92, "-"] isTight
          Paragraph[93, 103]
            Text[93, 101] chars:[93, 101, "item 4.1"]
````````````````````````````````



```````````````````````````````` example Loose Item Handling: 10
- item 1
    - item 1.1
- item 2 
    - item 2.1 
- item 3 

    - item 3.1 
- item 4 
    - item 4.1 
.
<ul>
    <li>item 1
        <ul>
            <li>item 1.1</li>
        </ul>
    </li>
    <li>item 2
        <ul>
            <li>item 2.1</li>
        </ul>
    </li>
    <li>
        <p>item 3</p>
        <ul>
            <li>item 3.1</li>
        </ul>
    </li>
    <li>item 4
        <ul>
            <li>item 4.1</li>
        </ul>
    </li>
</ul>
.
Document[0, 103]
  BulletList[0, 103] isTight
    BulletListItem[0, 24] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[13, 24] isTight
        BulletListItem[13, 24] open:[13, 14, "-"] isTight
          Paragraph[15, 24]
            Text[15, 23] chars:[15, 23, "item 1.1"]
    BulletListItem[24, 50] open:[24, 25, "-"] isTight
      Paragraph[26, 34]
        Text[26, 32] chars:[26, 32, "item 2"]
      BulletList[38, 50] isTight
        BulletListItem[38, 50] open:[38, 39, "-"] isTight
          Paragraph[40, 50]
            Text[40, 48] chars:[40, 48, "item 2.1"]
    BulletListItem[50, 77] open:[50, 51, "-"] isLoose hadBlankLineAfter
      Paragraph[52, 60]
        Text[52, 58] chars:[52, 58, "item 3"]
      BulletList[65, 77] isTight
        BulletListItem[65, 77] open:[65, 66, "-"] isTight
          Paragraph[67, 77]
            Text[67, 75] chars:[67, 75, "item 3.1"]
    BulletListItem[77, 103] open:[77, 78, "-"] isTight
      Paragraph[79, 87]
        Text[79, 85] chars:[79, 85, "item 4"]
      BulletList[91, 103] isTight
        BulletListItem[91, 103] open:[91, 92, "-"] isTight
          Paragraph[93, 103]
            Text[93, 101] chars:[93, 101, "item 4.1"]
````````````````````````````````



```````````````````````````````` example Loose Item Handling: 11
- item 1
    - item 1.1
- item 2 
    - item 2.1 
- item 3 
    - item 3.1 

- item 4 
    - item 4.1 
.
<ul>
    <li>item 1
        <ul>
            <li>item 1.1</li>
        </ul>
    </li>
    <li>item 2
        <ul>
            <li>item 2.1</li>
        </ul>
    </li>
    <li>
        <p>item 3</p>
        <ul>
            <li>item 3.1</li>
        </ul>
    </li>
    <li>
        <p>item 4</p>
        <ul>
            <li>item 4.1</li>
        </ul>
    </li>
</ul>
.
Document[0, 103]
  BulletList[0, 103] isTight
    BulletListItem[0, 24] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[13, 24] isTight
        BulletListItem[13, 24] open:[13, 14, "-"] isTight
          Paragraph[15, 24]
            Text[15, 23] chars:[15, 23, "item 1.1"]
    BulletListItem[24, 50] open:[24, 25, "-"] isTight
      Paragraph[26, 34]
        Text[26, 32] chars:[26, 32, "item 2"]
      BulletList[38, 50] isTight
        BulletListItem[38, 50] open:[38, 39, "-"] isTight
          Paragraph[40, 50]
            Text[40, 48] chars:[40, 48, "item 2.1"]
    BulletListItem[50, 76] open:[50, 51, "-"] isLoose
      Paragraph[52, 60]
        Text[52, 58] chars:[52, 58, "item 3"]
      BulletList[64, 76] isTight
        BulletListItem[64, 76] open:[64, 65, "-"] isTight hadBlankLineAfter
          Paragraph[66, 76]
            Text[66, 74] chars:[66, 74, "item 3.1"]
    BulletListItem[77, 103] open:[77, 78, "-"] isLoose
      Paragraph[79, 87]
        Text[79, 85] chars:[79, 85, "item 4"]
      BulletList[91, 103] isTight
        BulletListItem[91, 103] open:[91, 92, "-"] isTight
          Paragraph[93, 103]
            Text[93, 101] chars:[93, 101, "item 4.1"]
````````````````````````````````



```````````````````````````````` example Loose Item Handling: 12
- item 1
    - item 1.1
- item 2 
    - item 2.1 
- item 3 
    - item 3.1 
- item 4 

    - item 4.1 
.
<ul>
    <li>item 1
        <ul>
            <li>item 1.1</li>
        </ul>
    </li>
    <li>item 2
        <ul>
            <li>item 2.1</li>
        </ul>
    </li>
    <li>item 3
        <ul>
            <li>item 3.1</li>
        </ul>
    </li>
    <li>
        <p>item 4</p>
        <ul>
            <li>item 4.1</li>
        </ul>
    </li>
</ul>
.
Document[0, 103]
  BulletList[0, 103] isTight
    BulletListItem[0, 24] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[13, 24] isTight
        BulletListItem[13, 24] open:[13, 14, "-"] isTight
          Paragraph[15, 24]
            Text[15, 23] chars:[15, 23, "item 1.1"]
    BulletListItem[24, 50] open:[24, 25, "-"] isTight
      Paragraph[26, 34]
        Text[26, 32] chars:[26, 32, "item 2"]
      BulletList[38, 50] isTight
        BulletListItem[38, 50] open:[38, 39, "-"] isTight
          Paragraph[40, 50]
            Text[40, 48] chars:[40, 48, "item 2.1"]
    BulletListItem[50, 76] open:[50, 51, "-"] isTight
      Paragraph[52, 60]
        Text[52, 58] chars:[52, 58, "item 3"]
      BulletList[64, 76] isTight
        BulletListItem[64, 76] open:[64, 65, "-"] isTight
          Paragraph[66, 76]
            Text[66, 74] chars:[66, 74, "item 3.1"]
    BulletListItem[76, 103] open:[76, 77, "-"] isLoose hadBlankLineAfter
      Paragraph[78, 86]
        Text[78, 84] chars:[78, 84, "item 4"]
      BulletList[91, 103] isTight
        BulletListItem[91, 103] open:[91, 92, "-"] isTight
          Paragraph[93, 103]
            Text[93, 101] chars:[93, 101, "item 4.1"]
````````````````````````````````



## List Item Interrupts Paragraph

Test to see which list items can interrupt paragraphs.

```````````````````````````````` example List Item Interrupts Paragraph: 1
Bullet item can interrupt paragraph
* item

Empty bullet item with space can interrupt paragraph 
* 

Empty bullet item without space can interrupt paragraph
*

Numbered one item can interrupt paragraph
1. one item

Empty Numbered one item with space can interrupt paragraph
1. 

Empty Numbered one item without space can interrupt paragraph
1.

Numbered non-one item can interrupt paragraph
2. non-one item

Empty Numbered non-one item with space can interrupt paragraph
2. 

Empty Numbered non-one item without space can interrupt paragraph
2.

.
<p>Bullet item can interrupt paragraph * item</p>
<p>Empty bullet item with space can interrupt paragraph *</p>
<p>Empty bullet item without space can interrupt paragraph *</p>
<p>Numbered one item can interrupt paragraph 1. one item</p>
<p>Empty Numbered one item with space can interrupt paragraph 1.</p>
<p>Empty Numbered one item without space can interrupt paragraph 1.</p>
<p>Numbered non-one item can interrupt paragraph 2. non-one item</p>
<p>Empty Numbered non-one item with space can interrupt paragraph 2.</p>
<p>Empty Numbered non-one item without space can interrupt paragraph 2.</p>
.
Document[0, 547]
  Paragraph[0, 43]
    Text[0, 35] chars:[0, 35, "Bulle … graph"]
    SoftLineBreak[35, 36]
    Text[36, 42] chars:[36, 42, "* item"]
  Paragraph[44, 101]
    Text[44, 96] chars:[44, 96, "Empty … graph"]
    SoftLineBreak[97, 98]
    Text[98, 99] chars:[98, 99, "*"]
  Paragraph[102, 160]
    Text[102, 157] chars:[102, 157, "Empty … graph"]
    SoftLineBreak[157, 158]
    Text[158, 159] chars:[158, 159, "*"]
  Paragraph[161, 215]
    Text[161, 202] chars:[161, 202, "Numbe … graph"]
    SoftLineBreak[202, 203]
    Text[203, 214] chars:[203, 214, "1. on …  item"]
  Paragraph[216, 279]
    Text[216, 274] chars:[216, 274, "Empty … graph"]
    SoftLineBreak[274, 275]
    Text[275, 277] chars:[275, 277, "1."]
  Paragraph[280, 345]
    Text[280, 341] chars:[280, 341, "Empty … graph"]
    SoftLineBreak[341, 342]
    Text[342, 344] chars:[342, 344, "1."]
  Paragraph[346, 408]
    Text[346, 391] chars:[346, 391, "Numbe … graph"]
    SoftLineBreak[391, 392]
    Text[392, 407] chars:[392, 407, "2. no …  item"]
  Paragraph[409, 476]
    Text[409, 471] chars:[409, 471, "Empty … graph"]
    SoftLineBreak[471, 472]
    Text[472, 474] chars:[472, 474, "2."]
  Paragraph[477, 546]
    Text[477, 542] chars:[477, 542, "Empty … graph"]
    SoftLineBreak[542, 543]
    Text[543, 545] chars:[543, 545, "2."]
````````````````````````````````



Test to see which list items can interrupt another bullet list item's paragraphs

```````````````````````````````` example List Item Interrupts Paragraph: 2
* Bullet item can interrupt paragraph of a bullet list item
* item

list break

* Empty bullet item with space can interrupt paragraph of a bullet list item
* 

list break

* Empty bullet item without space can interrupt paragraph of a bullet list item
*

list break

* Numbered one item can interrupt paragraph of a bullet list item
1. one item

list break

* Empty Numbered one item with space can interrupt paragraph of a bullet list item
1. 

list break

* Empty Numbered one item without space can interrupt paragraph of a bullet list item
1.

list break

* Numbered non-one item can interrupt paragraph of a bullet list item
2. non-one item

list break

* Empty Numbered non-one item with space can interrupt paragraph of a bullet list item
2. 

list break

* Empty Numbered non-one item without space can interrupt paragraph of a bullet list item
2.

.
<ul>
    <li>Bullet item can interrupt paragraph of a bullet list item</li>
    <li>item</li>
</ul>
<p>list break</p>
<ul>
    <li>Empty bullet item with space can interrupt paragraph of a bullet list item *</li>
</ul>
<p>list break</p>
<ul>
    <li>Empty bullet item without space can interrupt paragraph of a bullet list item *</li>
</ul>
<p>list break</p>
<ul>
    <li>Numbered one item can interrupt paragraph of a bullet list item</li>
    <li>one item</li>
</ul>
<p>list break</p>
<ul>
    <li>Empty Numbered one item with space can interrupt paragraph of a bullet list item 1.</li>
</ul>
<p>list break</p>
<ul>
    <li>Empty Numbered one item without space can interrupt paragraph of a bullet list item 1.</li>
</ul>
<p>list break</p>
<ul>
    <li>Numbered non-one item can interrupt paragraph of a bullet list item</li>
    <li>non-one item</li>
</ul>
<p>list break</p>
<ul>
    <li>Empty Numbered non-one item with space can interrupt paragraph of a bullet list item 2.</li>
</ul>
<p>list break</p>
<ul>
    <li>Empty Numbered non-one item without space can interrupt paragraph of a bullet list item 2.</li>
</ul>
.
Document[0, 858]
  BulletList[0, 67] isTight
    BulletListItem[0, 60] open:[0, 1, "*"] isTight
      Paragraph[2, 60]
        Text[2, 59] chars:[2, 59, "Bulle …  item"]
    BulletListItem[60, 67] open:[60, 61, "*"] isTight hadBlankLineAfter
      Paragraph[62, 67]
        Text[62, 66] chars:[62, 66, "item"]
  Paragraph[68, 79]
    Text[68, 78] chars:[68, 78, "list break"]
  BulletList[80, 160] isTight
    BulletListItem[80, 160] open:[80, 81, "*"] isTight hadBlankLineAfter
      Paragraph[82, 160]
        Text[82, 156] chars:[82, 156, "Empty …  item"]
        SoftLineBreak[156, 157]
        Text[157, 158] chars:[157, 158, "*"]
  Paragraph[161, 172]
    Text[161, 171] chars:[161, 171, "list break"]
  BulletList[173, 255] isTight
    BulletListItem[173, 255] open:[173, 174, "*"] isTight hadBlankLineAfter
      Paragraph[175, 255]
        Text[175, 252] chars:[175, 252, "Empty …  item"]
        SoftLineBreak[252, 253]
        Text[253, 254] chars:[253, 254, "*"]
  Paragraph[256, 267]
    Text[256, 266] chars:[256, 266, "list break"]
  BulletList[268, 346] isTight
    BulletListItem[268, 334] open:[268, 269, "*"] isTight
      Paragraph[270, 334]
        Text[270, 333] chars:[270, 333, "Numbe …  item"]
    OrderedListItem[334, 346] open:[334, 336, "1."] isTight hadBlankLineAfter
      Paragraph[337, 346]
        Text[337, 345] chars:[337, 345, "one item"]
  Paragraph[347, 358]
    Text[347, 357] chars:[347, 357, "list break"]
  BulletList[359, 446] isTight
    BulletListItem[359, 446] open:[359, 360, "*"] isTight hadBlankLineAfter
      Paragraph[361, 446]
        Text[361, 441] chars:[361, 441, "Empty …  item"]
        SoftLineBreak[441, 442]
        Text[442, 444] chars:[442, 444, "1."]
  Paragraph[447, 458]
    Text[447, 457] chars:[447, 457, "list break"]
  BulletList[459, 548] isTight
    BulletListItem[459, 548] open:[459, 460, "*"] isTight hadBlankLineAfter
      Paragraph[461, 548]
        Text[461, 544] chars:[461, 544, "Empty …  item"]
        SoftLineBreak[544, 545]
        Text[545, 547] chars:[545, 547, "1."]
  Paragraph[549, 560]
    Text[549, 559] chars:[549, 559, "list break"]
  BulletList[561, 647] isTight
    BulletListItem[561, 631] open:[561, 562, "*"] isTight
      Paragraph[563, 631]
        Text[563, 630] chars:[563, 630, "Numbe …  item"]
    OrderedListItem[631, 647] open:[631, 633, "2."] isTight hadBlankLineAfter
      Paragraph[634, 647]
        Text[634, 646] chars:[634, 646, "non-o …  item"]
  Paragraph[648, 659]
    Text[648, 658] chars:[648, 658, "list break"]
  BulletList[660, 751] isTight
    BulletListItem[660, 751] open:[660, 661, "*"] isTight hadBlankLineAfter
      Paragraph[662, 751]
        Text[662, 746] chars:[662, 746, "Empty …  item"]
        SoftLineBreak[746, 747]
        Text[747, 749] chars:[747, 749, "2."]
  Paragraph[752, 763]
    Text[752, 762] chars:[752, 762, "list break"]
  BulletList[764, 857] isTight
    BulletListItem[764, 857] open:[764, 765, "*"] isTight hadBlankLineAfter
      Paragraph[766, 857]
        Text[766, 853] chars:[766, 853, "Empty …  item"]
        SoftLineBreak[853, 854]
        Text[854, 856] chars:[854, 856, "2."]
````````````````````````````````



Test to see which list items can interrupt another numbered list item's paragraphs

```````````````````````````````` example List Item Interrupts Paragraph: 3
1. Bullet item can interrupt paragraph of a numbered list item
* item

list break

1. Empty bullet item with space can interrupt paragraph of a numbered list item
* 

list break

1. Empty bullet item without space can interrupt paragraph of a numbered list item
*

list break

1. Numbered one item can interrupt paragraph of a numbered list item
1. one item

list break

1. Empty Numbered one item with space can interrupt paragraph of a numbered list item 
1. 

list break

1. Empty Numbered one item without space can interrupt paragraph of a numbered list item 
1.

list break

1. Numbered non-one item can interrupt paragraph of a numbered list item
2. non-one item

list break

1. Empty Numbered non-one item with space can interrupt paragraph of a numbered list item
2. 

list break

1. Empty Numbered non-one item without space can interrupt paragraph of a numbered list item
2.

.
<ol>
    <li>Bullet item can interrupt paragraph of a numbered list item</li>
    <li>item</li>
</ol>
<p>list break</p>
<ol>
    <li>Empty bullet item with space can interrupt paragraph of a numbered list item *</li>
</ol>
<p>list break</p>
<ol>
    <li>Empty bullet item without space can interrupt paragraph of a numbered list item *</li>
</ol>
<p>list break</p>
<ol>
    <li>Numbered one item can interrupt paragraph of a numbered list item</li>
    <li>one item</li>
</ol>
<p>list break</p>
<ol>
    <li>Empty Numbered one item with space can interrupt paragraph of a numbered list item 1.</li>
</ol>
<p>list break</p>
<ol>
    <li>Empty Numbered one item without space can interrupt paragraph of a numbered list item 1.</li>
</ol>
<p>list break</p>
<ol>
    <li>Numbered non-one item can interrupt paragraph of a numbered list item</li>
    <li>non-one item</li>
</ol>
<p>list break</p>
<ol>
    <li>Empty Numbered non-one item with space can interrupt paragraph of a numbered list item 2.</li>
</ol>
<p>list break</p>
<ol>
    <li>Empty Numbered non-one item without space can interrupt paragraph of a numbered list item 2.</li>
</ol>
.
Document[0, 887]
  OrderedList[0, 70] isTight delimiter:'.'
    OrderedListItem[0, 63] open:[0, 2, "1."] isTight
      Paragraph[3, 63]
        Text[3, 62] chars:[3, 62, "Bulle …  item"]
    BulletListItem[63, 70] open:[63, 64, "*"] isTight hadBlankLineAfter
      Paragraph[65, 70]
        Text[65, 69] chars:[65, 69, "item"]
  Paragraph[71, 82]
    Text[71, 81] chars:[71, 81, "list break"]
  OrderedList[83, 166] isTight delimiter:'.'
    OrderedListItem[83, 166] open:[83, 85, "1."] isTight hadBlankLineAfter
      Paragraph[86, 166]
        Text[86, 162] chars:[86, 162, "Empty …  item"]
        SoftLineBreak[162, 163]
        Text[163, 164] chars:[163, 164, "*"]
  Paragraph[167, 178]
    Text[167, 177] chars:[167, 177, "list break"]
  OrderedList[179, 264] isTight delimiter:'.'
    OrderedListItem[179, 264] open:[179, 181, "1."] isTight hadBlankLineAfter
      Paragraph[182, 264]
        Text[182, 261] chars:[182, 261, "Empty …  item"]
        SoftLineBreak[261, 262]
        Text[262, 263] chars:[262, 263, "*"]
  Paragraph[265, 276]
    Text[265, 275] chars:[265, 275, "list break"]
  OrderedList[277, 358] isTight delimiter:'.'
    OrderedListItem[277, 346] open:[277, 279, "1."] isTight
      Paragraph[280, 346]
        Text[280, 345] chars:[280, 345, "Numbe …  item"]
    OrderedListItem[346, 358] open:[346, 348, "1."] isTight hadBlankLineAfter
      Paragraph[349, 358]
        Text[349, 357] chars:[349, 357, "one item"]
  Paragraph[359, 370]
    Text[359, 369] chars:[359, 369, "list break"]
  OrderedList[371, 462] isTight delimiter:'.'
    OrderedListItem[371, 462] open:[371, 373, "1."] isTight hadBlankLineAfter
      Paragraph[374, 462]
        Text[374, 456] chars:[374, 456, "Empty …  item"]
        SoftLineBreak[457, 458]
        Text[458, 460] chars:[458, 460, "1."]
  Paragraph[463, 474]
    Text[463, 473] chars:[463, 473, "list break"]
  OrderedList[475, 568] isTight delimiter:'.'
    OrderedListItem[475, 568] open:[475, 477, "1."] isTight hadBlankLineAfter
      Paragraph[478, 568]
        Text[478, 563] chars:[478, 563, "Empty …  item"]
        SoftLineBreak[564, 565]
        Text[565, 567] chars:[565, 567, "1."]
  Paragraph[569, 580]
    Text[569, 579] chars:[569, 579, "list break"]
  OrderedList[581, 670] isTight delimiter:'.'
    OrderedListItem[581, 654] open:[581, 583, "1."] isTight
      Paragraph[584, 654]
        Text[584, 653] chars:[584, 653, "Numbe …  item"]
    OrderedListItem[654, 670] open:[654, 656, "2."] isTight hadBlankLineAfter
      Paragraph[657, 670]
        Text[657, 669] chars:[657, 669, "non-o …  item"]
  Paragraph[671, 682]
    Text[671, 681] chars:[671, 681, "list break"]
  OrderedList[683, 777] isTight delimiter:'.'
    OrderedListItem[683, 777] open:[683, 685, "1."] isTight hadBlankLineAfter
      Paragraph[686, 777]
        Text[686, 772] chars:[686, 772, "Empty …  item"]
        SoftLineBreak[772, 773]
        Text[773, 775] chars:[773, 775, "2."]
  Paragraph[778, 789]
    Text[778, 788] chars:[778, 788, "list break"]
  OrderedList[790, 886] isTight delimiter:'.'
    OrderedListItem[790, 886] open:[790, 792, "1."] isTight hadBlankLineAfter
      Paragraph[793, 886]
        Text[793, 882] chars:[793, 882, "Empty …  item"]
        SoftLineBreak[882, 883]
        Text[883, 885] chars:[883, 885, "2."]
````````````````````````````````



## List Item Indent Handling

Test how list indentation is determined

```````````````````````````````` example List Item Indent Handling: 1
* item 1
 * item 2
  * item 3
   * item 4
    * item 5
     * item 6
      * item 7
       * item 8
        * item 9
         * item 10
.
<ul>
    <li>item 1
        <ul>
            <li>item 2</li>
            <li>item 3</li>
            <li>item 4</li>
            <li>item 5
                <ul>
                    <li>item 6</li>
                    <li>item 7</li>
                    <li>item 8</li>
                    <li>item 9
                        <ul>
                            <li>item 10</li>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul>
    </li>
</ul>
.
Document[0, 136]
  BulletList[0, 136] isTight
    BulletListItem[0, 136] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[10, 136] isTight
        BulletListItem[10, 19] open:[10, 11, "*"] isTight
          Paragraph[12, 19]
            Text[12, 18] chars:[12, 18, "item 2"]
        BulletListItem[21, 30] open:[21, 22, "*"] isTight
          Paragraph[23, 30]
            Text[23, 29] chars:[23, 29, "item 3"]
        BulletListItem[33, 42] open:[33, 34, "*"] isTight
          Paragraph[35, 42]
            Text[35, 41] chars:[35, 41, "item 4"]
        BulletListItem[46, 136] open:[46, 47, "*"] isTight
          Paragraph[48, 55]
            Text[48, 54] chars:[48, 54, "item 5"]
          BulletList[60, 136] isTight
            BulletListItem[60, 69] open:[60, 61, "*"] isTight
              Paragraph[62, 69]
                Text[62, 68] chars:[62, 68, "item 6"]
            BulletListItem[75, 84] open:[75, 76, "*"] isTight
              Paragraph[77, 84]
                Text[77, 83] chars:[77, 83, "item 7"]
            BulletListItem[91, 100] open:[91, 92, "*"] isTight
              Paragraph[93, 100]
                Text[93, 99] chars:[93, 99, "item 8"]
            BulletListItem[108, 136] open:[108, 109, "*"] isTight
              Paragraph[110, 117]
                Text[110, 116] chars:[110, 116, "item 9"]
              BulletList[126, 136] isTight
                BulletListItem[126, 136] open:[126, 127, "*"] isTight
                  Paragraph[128, 136]
                    Text[128, 135] chars:[128, 135, "item 10"]
````````````````````````````````



Test if list indentation is determined on marker indent or content indent. If this and above
test differ in list structure, then content indent is used. Otherwise, marker indent.

```````````````````````````````` example List Item Indent Handling: 2
*  item 1
 *  item 2
  *  item 3
   *  item 4
    *  item 5
     *  item 6
      *  item 7
       *  item 8
        *  item 9
         *  item 10
.
<ul>
    <li>item 1
        <ul>
            <li>item 2</li>
            <li>item 3</li>
            <li>item 4</li>
            <li>item 5
                <ul>
                    <li>item 6</li>
                    <li>item 7</li>
                    <li>item 8</li>
                    <li>item 9
                        <ul>
                            <li>item 10</li>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul>
    </li>
</ul>
.
Document[0, 146]
  BulletList[0, 146] isTight
    BulletListItem[0, 146] open:[0, 1, "*"] isTight
      Paragraph[3, 10]
        Text[3, 9] chars:[3, 9, "item 1"]
      BulletList[11, 146] isTight
        BulletListItem[11, 21] open:[11, 12, "*"] isTight
          Paragraph[14, 21]
            Text[14, 20] chars:[14, 20, "item 2"]
        BulletListItem[23, 33] open:[23, 24, "*"] isTight
          Paragraph[26, 33]
            Text[26, 32] chars:[26, 32, "item 3"]
        BulletListItem[36, 46] open:[36, 37, "*"] isTight
          Paragraph[39, 46]
            Text[39, 45] chars:[39, 45, "item 4"]
        BulletListItem[50, 146] open:[50, 51, "*"] isTight
          Paragraph[53, 60]
            Text[53, 59] chars:[53, 59, "item 5"]
          BulletList[65, 146] isTight
            BulletListItem[65, 75] open:[65, 66, "*"] isTight
              Paragraph[68, 75]
                Text[68, 74] chars:[68, 74, "item 6"]
            BulletListItem[81, 91] open:[81, 82, "*"] isTight
              Paragraph[84, 91]
                Text[84, 90] chars:[84, 90, "item 7"]
            BulletListItem[98, 108] open:[98, 99, "*"] isTight
              Paragraph[101, 108]
                Text[101, 107] chars:[101, 107, "item 8"]
            BulletListItem[116, 146] open:[116, 117, "*"] isTight
              Paragraph[119, 126]
                Text[119, 125] chars:[119, 125, "item 9"]
              BulletList[135, 146] isTight
                BulletListItem[135, 146] open:[135, 136, "*"] isTight
                  Paragraph[138, 146]
                    Text[138, 145] chars:[138, 145, "item 10"]
````````````````````````````````



Test to see if having a blank line in list item makes a difference on indent column calcualtion.
If this list structure is the same as the one without blank lines, then had blank line status
does not affect indentation level.

```````````````````````````````` example List Item Indent Handling: 3
* item 1

 * item 2
 
  * item 3
  
   * item 4
   
    * item 5
    
     * item 6
     
      * item 7
      
       * item 8
       
        * item 9
        
         * item 10
.
<ul>
    <li>
        <p>item 1</p>
        <ul>
            <li>
                <p>item 2</p>
            </li>
            <li>
                <p>item 3</p>
            </li>
            <li>
                <p>item 4</p>
            </li>
            <li>
                <p>item 5</p>
                <ul>
                    <li>
                        <p>item 6</p>
                    </li>
                    <li>
                        <p>item 7</p>
                    </li>
                    <li>
                        <p>item 8</p>
                    </li>
                    <li>
                        <p>item 9</p>
                        <ul>
                            <li>item 10</li>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul>
    </li>
</ul>
.
Document[0, 181]
  BulletList[0, 181] isTight
    BulletListItem[0, 181] open:[0, 1, "*"] isLoose hadBlankLineAfter
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[11, 181] isTight
        BulletListItem[11, 20] open:[11, 12, "*"] isLoose hadBlankLineAfter
          Paragraph[13, 20]
            Text[13, 19] chars:[13, 19, "item 2"]
        BulletListItem[24, 33] open:[24, 25, "*"] isLoose hadBlankLineAfter
          Paragraph[26, 33]
            Text[26, 32] chars:[26, 32, "item 3"]
        BulletListItem[39, 48] open:[39, 40, "*"] isLoose hadBlankLineAfter
          Paragraph[41, 48]
            Text[41, 47] chars:[41, 47, "item 4"]
        BulletListItem[56, 181] open:[56, 57, "*"] isLoose hadBlankLineAfter
          Paragraph[58, 65]
            Text[58, 64] chars:[58, 64, "item 5"]
          BulletList[75, 181] isTight
            BulletListItem[75, 84] open:[75, 76, "*"] isLoose hadBlankLineAfter
              Paragraph[77, 84]
                Text[77, 83] chars:[77, 83, "item 6"]
            BulletListItem[96, 105] open:[96, 97, "*"] isLoose hadBlankLineAfter
              Paragraph[98, 105]
                Text[98, 104] chars:[98, 104, "item 7"]
            BulletListItem[119, 128] open:[119, 120, "*"] isLoose hadBlankLineAfter
              Paragraph[121, 128]
                Text[121, 127] chars:[121, 127, "item 8"]
            BulletListItem[144, 181] open:[144, 145, "*"] isLoose hadBlankLineAfter
              Paragraph[146, 153]
                Text[146, 152] chars:[146, 152, "item 9"]
              BulletList[171, 181] isTight
                BulletListItem[171, 181] open:[171, 172, "*"] isTight
                  Paragraph[173, 181]
                    Text[173, 180] chars:[173, 180, "item 10"]
````````````````````````````````



Test to see if first item indent affect list indentation processing, if structure differs from
same list but without leading first item space then yes.

```````````````````````````````` example List Item Indent Handling: 4
 * item 1
  * item 2
   * item 3
    * item 4
     * item 5
      * item 6
       * item 7
        * item 8
         * item 9
          * item 10
.
<ul>
    <li>item 1
        <ul>
            <li>item 2</li>
            <li>item 3</li>
            <li>item 4
                <ul>
                    <li>item 5</li>
                    <li>item 6</li>
                    <li>item 7</li>
                    <li>item 8
                        <ul>
                            <li>item 9</li>
                            <li>item 10</li>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul>
    </li>
</ul>
.
Document[0, 146]
  BulletList[1, 146] isTight
    BulletListItem[1, 146] open:[1, 2, "*"] isTight
      Paragraph[3, 10]
        Text[3, 9] chars:[3, 9, "item 1"]
      BulletList[12, 146] isTight
        BulletListItem[12, 21] open:[12, 13, "*"] isTight
          Paragraph[14, 21]
            Text[14, 20] chars:[14, 20, "item 2"]
        BulletListItem[24, 33] open:[24, 25, "*"] isTight
          Paragraph[26, 33]
            Text[26, 32] chars:[26, 32, "item 3"]
        BulletListItem[37, 146] open:[37, 38, "*"] isTight
          Paragraph[39, 46]
            Text[39, 45] chars:[39, 45, "item 4"]
          BulletList[51, 146] isTight
            BulletListItem[51, 60] open:[51, 52, "*"] isTight
              Paragraph[53, 60]
                Text[53, 59] chars:[53, 59, "item 5"]
            BulletListItem[66, 75] open:[66, 67, "*"] isTight
              Paragraph[68, 75]
                Text[68, 74] chars:[68, 74, "item 6"]
            BulletListItem[82, 91] open:[82, 83, "*"] isTight
              Paragraph[84, 91]
                Text[84, 90] chars:[84, 90, "item 7"]
            BulletListItem[99, 146] open:[99, 100, "*"] isTight
              Paragraph[101, 108]
                Text[101, 107] chars:[101, 107, "item 8"]
              BulletList[117, 146] isTight
                BulletListItem[117, 126] open:[117, 118, "*"] isTight
                  Paragraph[119, 126]
                    Text[119, 125] chars:[119, 125, "item 9"]
                BulletListItem[136, 146] open:[136, 137, "*"] isTight
                  Paragraph[138, 146]
                    Text[138, 145] chars:[138, 145, "item 10"]
````````````````````````````````



Test to see if absolute column position or relative from first parent list is used for
indentation. If the block quoted list structure is the same as non-block quoted structure then
indent is relative column based.

```````````````````````````````` example List Item Indent Handling: 5
> >  * item 1
> >   * item 2
> >    * item 3
> >     * item 4
> >      * item 5
> >       * item 6
> >        * item 7
> >         * item 8
> >          * item 9
> >           * item 10
.
<blockquote>
    <blockquote>
        <ul>
            <li>item 1
                <ul>
                    <li>item 2</li>
                    <li>item 3</li>
                    <li>item 4
                        <ul>
                            <li>item 5</li>
                            <li>item 6</li>
                            <li>item 7</li>
                            <li>item 8
                                <ul>
                                    <li>item 9</li>
                                    <li>item 10</li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul>
    </blockquote>
</blockquote>
.
Document[0, 186]
  BlockQuote[0, 186] marker:[0, 1, ">"]
    BlockQuote[2, 186] marker:[2, 3, ">"]
      BulletList[5, 186] isTight
        BulletListItem[5, 186] open:[5, 6, "*"] isTight
          Paragraph[7, 14]
            Text[7, 13] chars:[7, 13, "item 1"]
          BulletList[20, 186] isTight
            BulletListItem[20, 29] open:[20, 21, "*"] isTight
              Paragraph[22, 29]
                Text[22, 28] chars:[22, 28, "item 2"]
            BulletListItem[36, 45] open:[36, 37, "*"] isTight
              Paragraph[38, 45]
                Text[38, 44] chars:[38, 44, "item 3"]
            BulletListItem[53, 186] open:[53, 54, "*"] isTight
              Paragraph[55, 62]
                Text[55, 61] chars:[55, 61, "item 4"]
              BulletList[71, 186] isTight
                BulletListItem[71, 80] open:[71, 72, "*"] isTight
                  Paragraph[73, 80]
                    Text[73, 79] chars:[73, 79, "item 5"]
                BulletListItem[90, 99] open:[90, 91, "*"] isTight
                  Paragraph[92, 99]
                    Text[92, 98] chars:[92, 98, "item 6"]
                BulletListItem[110, 119] open:[110, 111, "*"] isTight
                  Paragraph[112, 119]
                    Text[112, 118] chars:[112, 118, "item 7"]
                BulletListItem[131, 186] open:[131, 132, "*"] isTight
                  Paragraph[133, 140]
                    Text[133, 139] chars:[133, 139, "item 8"]
                  BulletList[153, 186] isTight
                    BulletListItem[153, 162] open:[153, 154, "*"] isTight
                      Paragraph[155, 162]
                        Text[155, 161] chars:[155, 161, "item 9"]
                    BulletListItem[176, 186] open:[176, 177, "*"] isTight
                      Paragraph[178, 186]
                        Text[178, 185] chars:[178, 185, "item 10"]
````````````````````````````````



Test to confirm that it is first list indent processing, not first child of list that differs
from sub-lists.

```````````````````````````````` example List Item Indent Handling: 6
* item 1
 * item 2
  * item 4
* item 3
 * item 4
  * item 5
.
<ul>
    <li>item 1
        <ul>
            <li>item 2</li>
            <li>item 4</li>
        </ul>
    </li>
    <li>item 3
        <ul>
            <li>item 4</li>
            <li>item 5</li>
        </ul>
    </li>
</ul>
.
Document[0, 60]
  BulletList[0, 60] isTight
    BulletListItem[0, 30] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[10, 30] isTight
        BulletListItem[10, 19] open:[10, 11, "*"] isTight
          Paragraph[12, 19]
            Text[12, 18] chars:[12, 18, "item 2"]
        BulletListItem[21, 30] open:[21, 22, "*"] isTight
          Paragraph[23, 30]
            Text[23, 29] chars:[23, 29, "item 4"]
    BulletListItem[30, 60] open:[30, 31, "*"] isTight
      Paragraph[32, 39]
        Text[32, 38] chars:[32, 38, "item 3"]
      BulletList[40, 60] isTight
        BulletListItem[40, 49] open:[40, 41, "*"] isTight
          Paragraph[42, 49]
            Text[42, 48] chars:[42, 48, "item 4"]
        BulletListItem[51, 60] open:[51, 52, "*"] isTight
          Paragraph[53, 60]
            Text[53, 59] chars:[53, 59, "item 5"]
````````````````````````````````



Test where lazy continuation affects list item processing.

```````````````````````````````` example List Item Indent Handling: 7
* item 1
       * item 2
* item 3
        * item 4
.
<ul>
    <li>item 1
        <ul>
            <li>item 2</li>
        </ul>
    </li>
    <li>item 3 * item 4</li>
</ul>
.
Document[0, 51]
  BulletList[0, 51] isTight
    BulletListItem[0, 25] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[16, 25] isTight
        BulletListItem[16, 25] open:[16, 17, "*"] isTight
          Paragraph[18, 25]
            Text[18, 24] chars:[18, 24, "item 2"]
    BulletListItem[25, 51] open:[25, 26, "*"] isTight
      Paragraph[27, 51]
        Text[27, 33] chars:[27, 33, "item 3"]
        SoftLineBreak[33, 34]
        Text[42, 50] chars:[42, 50, "* item 4"]
````````````````````````````````



Test if it is `first first list` indent processing, or first direct parent list processing that
affects sub-list indentation. But `Markdown.pl` cannot properly process block quotes in list
items. See: [Bugs](#bugs)

So first list it is and the HTML here is from the emulator not Markdown.pl

```````````````````````````````` example List Item Indent Handling: 8
* item 1
 * item 2
  * item 4
    > * item 3
    >  * item 4
    >   * item 5
.
<ul>
    <li>item 1
        <ul>
            <li>item 2</li>
            <li>item 4</li>
        </ul>
        <blockquote>
            <ul>
                <li>item 3
                    <ul>
                        <li>item 4</li>
                        <li>item 5</li>
                    </ul>
                </li>
            </ul>
        </blockquote>
    </li>
</ul>
.
Document[0, 78]
  BulletList[0, 78] isTight
    BulletListItem[0, 78] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[10, 30] isTight
        BulletListItem[10, 19] open:[10, 11, "*"] isTight
          Paragraph[12, 19]
            Text[12, 18] chars:[12, 18, "item 2"]
        BulletListItem[21, 30] open:[21, 22, "*"] isTight
          Paragraph[23, 30]
            Text[23, 29] chars:[23, 29, "item 4"]
      BlockQuote[34, 78] marker:[34, 35, ">"]
        BulletList[36, 78] isTight
          BulletListItem[36, 78] open:[36, 37, "*"] isTight
            Paragraph[38, 45]
              Text[38, 44] chars:[38, 44, "item 3"]
            BulletList[52, 78] isTight
              BulletListItem[52, 61] open:[52, 53, "*"] isTight
                Paragraph[54, 61]
                  Text[54, 60] chars:[54, 60, "item 4"]
              BulletListItem[69, 78] open:[69, 70, "*"] isTight
                Paragraph[71, 78]
                  Text[71, 77] chars:[71, 77, "item 5"]
````````````````````````````````



```````````````````````````````` example List Item Indent Handling: 9
* item 1
 * item 2
  * item 4
     > * item 3
     >  * item 4
     >   * item 5
.
<ul>
    <li>item 1
        <ul>
            <li>item 2</li>
            <li>item 4
                <blockquote>
                    <ul>
                        <li>item 3
                            <ul>
                                <li>item 4</li>
                                <li>item 5</li>
                            </ul>
                        </li>
                    </ul>
                </blockquote>
            </li>
        </ul>
    </li>
</ul>
.
Document[0, 81]
  BulletList[0, 81] isTight
    BulletListItem[0, 81] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[10, 81] isTight
        BulletListItem[10, 19] open:[10, 11, "*"] isTight
          Paragraph[12, 19]
            Text[12, 18] chars:[12, 18, "item 2"]
        BulletListItem[21, 81] open:[21, 22, "*"] isTight
          Paragraph[23, 30]
            Text[23, 29] chars:[23, 29, "item 4"]
          BlockQuote[35, 81] marker:[35, 36, ">"]
            BulletList[37, 81] isTight
              BulletListItem[37, 81] open:[37, 38, "*"] isTight
                Paragraph[39, 46]
                  Text[39, 45] chars:[39, 45, "item 3"]
                BulletList[54, 81] isTight
                  BulletListItem[54, 63] open:[54, 55, "*"] isTight
                    Paragraph[56, 63]
                      Text[56, 62] chars:[56, 62, "item 4"]
                  BulletListItem[72, 81] open:[72, 73, "*"] isTight
                    Paragraph[74, 81]
                      Text[74, 80] chars:[74, 80, "item 5"]
````````````````````````````````



Test shows where the boundary switch to indented code occurs. First paragraph is a paragraph,
the second is indented code.

```````````````````````````````` example List Item Indent Handling: 10
-   test

       item child para

        indented code
          
-   test
    - sub item

           sub item child para

            sub item indented code
          
---

1.  test

       item child para

        item indented code

1.  test
    1. sub item

           sub item child para

            sub item indented code

.
<ul>
    <li>
        <p>test</p>
        <p>item child para</p>
        <pre><code>indented code
</code></pre>
    </li>
    <li>
        <p>test</p>
        <ul>
            <li>
                <p>sub item</p>
                <p>sub item child para</p>
                <pre><code>sub item indented code
</code></pre>
            </li>
        </ul>
    </li>
</ul>
<hr />
<ol>
    <li>
        <p>test</p>
        <p>item child para</p>
        <pre><code>item indented code
</code></pre>
    </li>
    <li>
        <p>test</p>
        <ol>
            <li>
                <p>sub item</p>
                <p>sub item child para</p>
                <pre><code>sub item indented code
</code></pre>
            </li>
        </ol>
    </li>
</ol>
.
Document[0, 331]
  BulletList[0, 159] isTight
    BulletListItem[0, 56] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[4, 9]
        Text[4, 8] chars:[4, 8, "test"]
      Paragraph[17, 33]
        Text[17, 32] chars:[17, 32, "item  …  para"]
      IndentedCodeBlock[42, 56]
    BulletListItem[67, 159] open:[67, 68, "-"] isLoose
      Paragraph[71, 76]
        Text[71, 75] chars:[71, 75, "test"]
      BulletList[80, 159] isTight
        BulletListItem[80, 159] open:[80, 81, "-"] isLoose hadBlankLineAfter
          Paragraph[82, 91]
            Text[82, 90] chars:[82, 90, "sub item"]
          Paragraph[103, 123]
            Text[103, 122] chars:[103, 122, "sub i …  para"]
          IndentedCodeBlock[136, 159]
  ThematicBreak[170, 173]
  OrderedList[175, 330] isTight delimiter:'.'
    OrderedListItem[175, 236] open:[175, 177, "1."] isLoose hadBlankLineAfter
      Paragraph[179, 184]
        Text[179, 183] chars:[179, 183, "test"]
      Paragraph[192, 208]
        Text[192, 207] chars:[192, 207, "item  …  para"]
      IndentedCodeBlock[217, 236]
    OrderedListItem[237, 330] open:[237, 239, "1."] isLoose
      Paragraph[241, 246]
        Text[241, 245] chars:[241, 245, "test"]
      OrderedList[250, 330] isTight delimiter:'.'
        OrderedListItem[250, 330] open:[250, 252, "1."] isLoose hadBlankLineAfter
          Paragraph[253, 262]
            Text[253, 261] chars:[253, 261, "sub item"]
          Paragraph[274, 294]
            Text[274, 293] chars:[274, 293, "sub i …  para"]
          IndentedCodeBlock[307, 330]
````````````````````````````````



More extensive test to show where the boundary switch to indented code occurs. Sub-items first
paragraph is a paragraph, the second is indented code

```````````````````````````````` example List Item Indent Handling: 11
* item 1
    
  item para 1
  
   item para 2
   
    item para 3
    
     item para 4
     
      item para 5
      
       item para 6
       
        item para 7
        
 * item 2
    
   item para 1
   
    item para 2
    
     item para 3
     
      item para 4
      
       item para 5
       
        item para 6
        
         item para 7
        
  * item 3
    
    item para 1
    
     item para 2
     
      item para 3
      
       item para 4
       
        item para 5
        
         item para 6
         
          item para 7
        
   * item 4
    
     item para 1
     
      item para 2
      
       item para 3
       
        item para 4
        
         item para 5
         
          item para 6
          
           item para 7
        
    * item 5
    
      item para 1
  
       item para 2
   
        item para 3
    
         item para 4
     
          item para 5
      
           item para 6
       
            item para 7
.
<ul>
    <li>
        <p>item 1</p>
        <p>item para 1</p>
        <p>item para 2</p>
        <p>item para 3</p>
        <p>item para 4</p>
        <p>item para 5</p>
        <p>item para 6</p>
        <pre><code>item para 7
</code></pre>
        <ul>
            <li>item 2</li>
        </ul>
        <p>item para 1</p>
        <p>item para 2</p>
        <p>item para 3</p>
        <p>item para 4</p>
        <p>item para 5</p>
        <pre><code>item para 6

 item para 7
</code></pre>
        <ul>
            <li>item 3</li>
        </ul>
        <p>item para 1</p>
        <p>item para 2</p>
        <p>item para 3</p>
        <p>item para 4</p>
        <pre><code>item para 5

 item para 6

  item para 7
</code></pre>
        <ul>
            <li>
                <p>item 4</p>
                <p>item para 1</p>
                <p>item para 2</p>
                <p>item para 3</p>
                <p>item para 4</p>
                <p>item para 5</p>
                <p>item para 6</p>
                <p>item para 7</p>
            </li>
            <li>
                <p>item 5</p>
                <p>item para 1</p>
                <p>item para 2</p>
                <p>item para 3</p>
                <p>item para 4</p>
                <p>item para 5</p>
                <p>item para 6</p>
                <pre><code>item para 7
</code></pre>
            </li>
        </ul>
    </li>
</ul>
.
Document[0, 982]
  BulletList[0, 982] isTight
    BulletListItem[0, 982] open:[0, 1, "*"] isLoose hadBlankLineAfter
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      Paragraph[16, 28]
        Text[16, 27] chars:[16, 27, "item  … ara 1"]
      Paragraph[34, 46]
        Text[34, 45] chars:[34, 45, "item  … ara 2"]
      Paragraph[54, 66]
        Text[54, 65] chars:[54, 65, "item  … ara 3"]
      Paragraph[76, 88]
        Text[76, 87] chars:[76, 87, "item  … ara 4"]
      Paragraph[100, 112]
        Text[100, 111] chars:[100, 111, "item  … ara 5"]
      Paragraph[126, 138]
        Text[126, 137] chars:[126, 137, "item  … ara 6"]
      IndentedCodeBlock[154, 166]
      BulletList[176, 185] isTight
        BulletListItem[176, 185] open:[176, 177, "*"] isTight hadBlankLineAfter
          Paragraph[178, 185]
            Text[178, 184] chars:[178, 184, "item 2"]
      Paragraph[193, 205]
        Text[193, 204] chars:[193, 204, "item  … ara 1"]
      Paragraph[213, 225]
        Text[213, 224] chars:[213, 224, "item  … ara 2"]
      Paragraph[235, 247]
        Text[235, 246] chars:[235, 246, "item  … ara 3"]
      Paragraph[259, 271]
        Text[259, 270] chars:[259, 270, "item  … ara 4"]
      Paragraph[285, 297]
        Text[285, 296] chars:[285, 296, "item  … ara 5"]
      IndentedCodeBlock[313, 355]
      BulletList[366, 375] isTight
        BulletListItem[366, 375] open:[366, 367, "*"] isTight hadBlankLineAfter
          Paragraph[368, 375]
            Text[368, 374] chars:[368, 374, "item 3"]
      Paragraph[384, 396]
        Text[384, 395] chars:[384, 395, "item  … ara 1"]
      Paragraph[406, 418]
        Text[406, 417] chars:[406, 417, "item  … ara 2"]
      Paragraph[430, 442]
        Text[430, 441] chars:[430, 441, "item  … ara 3"]
      Paragraph[456, 468]
        Text[456, 467] chars:[456, 467, "item  … ara 4"]
      IndentedCodeBlock[484, 558]
      BulletList[570, 982] isTight
        BulletListItem[570, 775] open:[570, 571, "*"] isLoose hadBlankLineAfter
          Paragraph[572, 579]
            Text[572, 578] chars:[572, 578, "item 4"]
          Paragraph[589, 601]
            Text[589, 600] chars:[589, 600, "item  … ara 1"]
          Paragraph[613, 625]
            Text[613, 624] chars:[613, 624, "item  … ara 2"]
          Paragraph[639, 651]
            Text[639, 650] chars:[639, 650, "item  … ara 3"]
          Paragraph[667, 679]
            Text[667, 678] chars:[667, 678, "item  … ara 4"]
          Paragraph[697, 709]
            Text[697, 708] chars:[697, 708, "item  … ara 5"]
          Paragraph[729, 741]
            Text[729, 740] chars:[729, 740, "item  … ara 6"]
          Paragraph[763, 775]
            Text[763, 774] chars:[763, 774, "item  … ara 7"]
        BulletListItem[788, 982] open:[788, 789, "*"] isLoose hadBlankLineAfter
          Paragraph[790, 797]
            Text[790, 796] chars:[790, 796, "item 5"]
          Paragraph[808, 820]
            Text[808, 819] chars:[808, 819, "item  … ara 1"]
          Paragraph[830, 842]
            Text[830, 841] chars:[830, 841, "item  … ara 2"]
          Paragraph[854, 866]
            Text[854, 865] chars:[854, 865, "item  … ara 3"]
          Paragraph[880, 892]
            Text[880, 891] chars:[880, 891, "item  … ara 4"]
          Paragraph[908, 920]
            Text[908, 919] chars:[908, 919, "item  … ara 5"]
          Paragraph[938, 950]
            Text[938, 949] chars:[938, 949, "item  … ara 6"]
          IndentedCodeBlock[970, 982]
````````````````````````````````



Test for how items with indent > first list item's indent but < previous item's content indent
are handled. Mainly, if they are handled in a weird way of treating the item as a sub-item of
the previous list item. There was one that did it that way, GitHub comments if I can remember
right, but now they switched to commonmark list handling with mods. Guess it is now GFC--GitHub
Flavoured Commonmark.

```````````````````````````````` example List Item Indent Handling: 12
*  item 1
   * item 2
  * item 3
.
<ul>
    <li>item 1
        <ul>
            <li>item 2</li>
            <li>item 3</li>
        </ul>
    </li>
</ul>
.
Document[0, 33]
  BulletList[0, 33] isTight
    BulletListItem[0, 33] open:[0, 1, "*"] isTight
      Paragraph[3, 10]
        Text[3, 9] chars:[3, 9, "item 1"]
      BulletList[13, 33] isTight
        BulletListItem[13, 22] open:[13, 14, "*"] isTight
          Paragraph[15, 22]
            Text[15, 21] chars:[15, 21, "item 2"]
        BulletListItem[24, 33] open:[24, 25, "*"] isTight
          Paragraph[26, 33]
            Text[26, 32] chars:[26, 32, "item 3"]
````````````````````````````````



Test how headings in list items are handled, leading space allowed or not

```````````````````````````````` example List Item Indent Handling: 13
* item 1

  # Heading 1

   ## Heading 2

    ### Heading 3

     #### Heading 4

      ##### Heading 5

       ###### Heading 6

  * item 2

    # Heading 1
    
     ## Heading 2
    
      ### Heading 3
    
       #### Heading 3
    
        ##### Heading 5
      
         ###### Heading 6

.
<ul>
    <li>
        <p>item 1</p>
        <h1>Heading 1</h1>
        <h2>Heading 2</h2>
        <h3>Heading 3</h3>
        <p>#### Heading 4</p>
        <p>##### Heading 5</p>
        <p>###### Heading 6</p>
        <ul>
            <li>item 2</li>
        </ul>
        <h1>Heading 1</h1>
        <p>## Heading 2</p>
        <p>### Heading 3</p>
        <p>#### Heading 3</p>
        <pre><code>##### Heading 5

 ###### Heading 6
</code></pre>
    </li>
</ul>
.
Document[0, 296]
  BulletList[0, 295] isTight
    BulletListItem[0, 295] open:[0, 1, "*"] isLoose hadBlankLineAfter
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      Heading[12, 23] textOpen:[12, 13, "#"] text:[14, 23, "Heading 1"]
        Text[14, 23] chars:[14, 23, "Heading 1"]
      Heading[28, 40] textOpen:[28, 30, "##"] text:[31, 40, "Heading 2"]
        Text[31, 40] chars:[31, 40, "Heading 2"]
      Heading[46, 59] textOpen:[46, 49, "###"] text:[50, 59, "Heading 3"]
        Text[50, 59] chars:[50, 59, "Heading 3"]
      Paragraph[66, 81]
        Text[66, 80] chars:[66, 80, "####  … ing 4"]
      Paragraph[88, 104]
        Text[88, 103] chars:[88, 103, "##### … ing 5"]
      Paragraph[112, 129]
        Text[112, 128] chars:[112, 128, "##### … ing 6"]
      BulletList[132, 141] isTight
        BulletListItem[132, 141] open:[132, 133, "*"] isTight hadBlankLineAfter
          Paragraph[134, 141]
            Text[134, 140] chars:[134, 140, "item 2"]
      Heading[146, 157] textOpen:[146, 147, "#"] text:[148, 157, "Heading 1"]
        Text[148, 157] chars:[148, 157, "Heading 1"]
      Paragraph[168, 181]
        Text[168, 180] chars:[168, 180, "## He … ing 2"]
      Paragraph[192, 206]
        Text[192, 205] chars:[192, 205, "### H … ing 3"]
      Paragraph[218, 233]
        Text[218, 232] chars:[218, 232, "####  … ing 3"]
      IndentedCodeBlock[246, 295]
````````````````````````````````



```````````````````````````````` example List Item Indent Handling: 14
-   test
    - sub item

         sub item child para

          indented code
          
---

1.  test
    1. sub item

          sub item child para

           indented code

.
<ul>
    <li>
        <p>test</p>
        <ul>
            <li>
                <p>sub item</p>
                <p>sub item child para</p>
                <p>indented code</p>
            </li>
        </ul>
    </li>
</ul>
<hr />
<ol>
    <li>
        <p>test</p>
        <ol>
            <li>
                <p>sub item</p>
                <p>sub item child para</p>
                <p>indented code</p>
            </li>
        </ol>
    </li>
</ol>
.
Document[0, 178]
  BulletList[0, 79] isTight
    BulletListItem[0, 79] open:[0, 1, "-"] isLoose
      Paragraph[4, 9]
        Text[4, 8] chars:[4, 8, "test"]
      BulletList[13, 79] isTight
        BulletListItem[13, 79] open:[13, 14, "-"] isLoose hadBlankLineAfter
          Paragraph[15, 24]
            Text[15, 23] chars:[15, 23, "sub item"]
          Paragraph[34, 54]
            Text[34, 53] chars:[34, 53, "sub i …  para"]
          Paragraph[65, 79]
            Text[65, 78] chars:[65, 78, "inden …  code"]
  ThematicBreak[90, 93]
  OrderedList[95, 177] isTight delimiter:'.'
    OrderedListItem[95, 177] open:[95, 97, "1."] isLoose
      Paragraph[99, 104]
        Text[99, 103] chars:[99, 103, "test"]
      OrderedList[108, 177] isTight delimiter:'.'
        OrderedListItem[108, 177] open:[108, 110, "1."] isLoose hadBlankLineAfter
          Paragraph[111, 120]
            Text[111, 119] chars:[111, 119, "sub item"]
          Paragraph[131, 151]
            Text[131, 150] chars:[131, 150, "sub i …  para"]
          Paragraph[163, 177]
            Text[163, 176] chars:[163, 176, "inden …  code"]
````````````````````````````````



## Bugs

Block quotes in list items mess up parsing and HTML generation

```````````````````````````````` example(Bugs: 1) options(IGNORE)
* item 1
 * item 2
  * item 4
    > * item 3
    >  * item 4
    >   * item 5
.
<ul>
    <li>item 1
        <ul>
            <li>item 2</li>
            <li>item 4
                <blockquote>
                    <ul>
                        <li>item 3
                            <ul>
                                <li>item 4</li>
                                <li>item 5</li>
                            </ul>
                        </li>
                    </ul>
            </li>
        </ul>
    </li>
</ul>
<p>
    </blockquote>
</p>
.
````````````````````````````````



Here the block quote messes up parsing and HTML generation

```````````````````````````````` example(Bugs: 2) options(IGNORE)
* item 1
 * item 2
  * item 4
    > > * item 3
    > >  * item 4
    > >   * item 5
.
<ul>
    <li>item 1
        <ul>
            <li>item 2</li>
            <li>item 4
                <blockquote>
                    <blockquote>
                        <ul>
                            <li>item 3
                                <ul>
                                    <li>item 4</li>
                                    <li>item 5</li>
                                </ul>
                            </li>
                        </ul>
            </li>
        </ul>
    </li>
</ul>
<p>
    </blockquote>
    </blockquote>
</p>
.
````````````````````````````````



Here the block quote messes up parsing and HTML generation

```````````````````````````````` example(Bugs: 3) options(IGNORE)
* item 1
 * item 2
  * item 4
  
    > > * item 3
    > >  * item 4
    > >   * item 5
.
<ul>
    <li>
        <p>item 1</p>
        <ul>
            <li>item 2</li>
            <li>item 4</li>
        </ul>
        <blockquote>
            <blockquote>
                <ul>
                    <li>item 3
                        <ul>
                            <li>item 4</li>
                            <li>item 5</li>
                        </ul>
                    </li>
                </ul>
            </blockquote>
            <p>
                <p>
        </blockquote>
        </p>
    </li>
</ul>
</p>
.
````````````````````````````````



An item prefix with a space but otherwise empty, instead of lazy continuation becomes something
outside the item as part of the list

```````````````````````````````` example(Bugs: 4) options(IGNORE)
* Empty bullet item with space can interrupt paragraph of a bullet list item
* 

list break

* Empty Numbered one item with space can interrupt paragraph of a bullet list item
1. 

list break

* Empty Numbered non-one item with space can interrupt paragraph of a bullet list item
2. 

.
<ul>
    <li>Empty bullet item with space can interrupt paragraph of a bullet list
        item</li>*</ul>
<p>list break</p>
<ul>
    <li>Empty Numbered one item with space can interrupt paragraph of a bullet
        list item</li>1.</ul>
<p>list break</p>
<ul>
    <li>Empty Numbered non-one item with space can interrupt paragraph of a bullet
        list item</li>2.</ul>
.
````````````````````````````````



