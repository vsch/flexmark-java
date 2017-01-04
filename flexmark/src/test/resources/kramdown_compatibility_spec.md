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
    <li>Bullet List
        <ol>
            <li>With Ordered Item</li>
        </ol>
    </li>
</ul>
.
Document[0, 35]
  BulletList[0, 35] isTight
    BulletListItem[0, 35] open:[0, 1, "-"] isTight
      Paragraph[2, 14]
        Text[2, 13] chars:[2, 13, "Bulle …  List"]
      OrderedList[14, 35] isTight delimiter:'.'
        OrderedListItem[14, 35] open:[14, 16, "1."] isTight
          Paragraph[17, 35]
            Text[17, 34] chars:[17, 34, "With  …  Item"]
````````````````````````````````



```````````````````````````````` example Mismatched List Item Type Handling: 2
1. Ordered Item
- With Bullet List
.
<ol>
    <li>Ordered Item
        <ul>
            <li>With Bullet List</li>
        </ul>
    </li>
</ol>
.
Document[0, 35]
  OrderedList[0, 35] isTight delimiter:'.'
    OrderedListItem[0, 35] open:[0, 2, "1."] isTight
      Paragraph[3, 16]
        Text[3, 15] chars:[3, 15, "Order …  Item"]
      BulletList[16, 35] isTight
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
    <li>item 2</li>
    <li>item 3</li>
    <li>item 4</li>
</ul>
.
Document[0, 40]
  BulletList[0, 40] isTight
    BulletListItem[0, 9] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[10, 20] open:[10, 11, "-"] isTight
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
    <li>item 3</li>
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
    BulletListItem[20, 30] open:[20, 21, "-"] isTight
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
    <li>item 4</li>
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
    BulletListItem[30, 40] open:[30, 31, "-"] isTight
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
Document[0, 103]
  BulletList[0, 103] isTight
    BulletListItem[0, 24] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[13, 24] isTight
        BulletListItem[13, 24] open:[13, 14, "-"] isTight hadBlankLineAfter
          Paragraph[15, 24]
            Text[15, 23] chars:[15, 23, "item 1.1"]
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
        BulletListItem[38, 50] open:[38, 39, "-"] isTight hadBlankLineAfter
          Paragraph[40, 50]
            Text[40, 48] chars:[40, 48, "item 2.1"]
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
        BulletListItem[64, 76] open:[64, 65, "-"] isTight hadBlankLineAfter
          Paragraph[66, 76]
            Text[66, 74] chars:[66, 74, "item 3.1"]
    BulletListItem[77, 103] open:[77, 78, "-"] isTight
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

<!--List Break-->

* Empty bullet item with space can interrupt paragraph of a bullet list item
* 

<!--List Break-->

* Empty bullet item without space can interrupt paragraph of a bullet list item
*

<!--List Break-->

* Numbered one item can interrupt paragraph of a bullet list item
1. one item

<!--List Break-->

* Empty Numbered one item with space can interrupt paragraph of a bullet list item
1. 

<!--List Break-->

* Empty Numbered one item without space can interrupt paragraph of a bullet list item
1.

<!--List Break-->

* Numbered non-one item can interrupt paragraph of a bullet list item
2. non-one item

<!--List Break-->

* Empty Numbered non-one item with space can interrupt paragraph of a bullet list item
2. 

<!--List Break-->

* Empty Numbered non-one item without space can interrupt paragraph of a bullet list item
2.

.
<ul>
    <li>Bullet item can interrupt paragraph of a bullet list item</li>
    <li>item</li>
</ul>
<!--List Break-->
<ul>
    <li>Empty bullet item with space can interrupt paragraph of a bullet list item</li>
    <li></li>
</ul>
<!--List Break-->
<ul>
    <li>Empty bullet item without space can interrupt paragraph of a bullet list item *</li>
</ul>
<!--List Break-->
<ul>
    <li>Numbered one item can interrupt paragraph of a bullet list item
        <ol>
            <li>one item</li>
        </ol>
    </li>
</ul>
<!--List Break-->
<ul>
    <li>Empty Numbered one item with space can interrupt paragraph of a bullet list item
        <ol>
            <li></li>
        </ol>
    </li>
</ul>
<!--List Break-->
<ul>
    <li>Empty Numbered one item without space can interrupt paragraph of a bullet list item 1.</li>
</ul>
<!--List Break-->
<ul>
    <li>Numbered non-one item can interrupt paragraph of a bullet list item
        <ol>
            <li>non-one item</li>
        </ol>
    </li>
</ul>
<!--List Break-->
<ul>
    <li>Empty Numbered non-one item with space can interrupt paragraph of a bullet list item
        <ol>
            <li></li>
        </ol>
    </li>
</ul>
<!--List Break-->
<ul>
    <li>Empty Numbered non-one item without space can interrupt paragraph of a bullet list item 2.</li>
</ul>
.
Document[0, 914]
  BulletList[0, 67] isTight
    BulletListItem[0, 60] open:[0, 1, "*"] isTight
      Paragraph[2, 60]
        Text[2, 59] chars:[2, 59, "Bulle …  item"]
    BulletListItem[60, 67] open:[60, 61, "*"] isTight hadBlankLineAfter
      Paragraph[62, 67]
        Text[62, 66] chars:[62, 66, "item"]
  HtmlCommentBlock[68, 86]
  BulletList[87, 165] isTight
    BulletListItem[87, 164] open:[87, 88, "*"] isTight
      Paragraph[89, 164]
        Text[89, 163] chars:[89, 163, "Empty …  item"]
    BulletListItem[164, 165] open:[164, 165, "*"] isTight hadBlankLineAfter
  HtmlCommentBlock[168, 186]
  BulletList[187, 269] isTight
    BulletListItem[187, 269] open:[187, 188, "*"] isTight hadBlankLineAfter
      Paragraph[189, 269]
        Text[189, 266] chars:[189, 266, "Empty …  item"]
        SoftLineBreak[266, 267]
        Text[267, 268] chars:[267, 268, "*"]
  HtmlCommentBlock[270, 288]
  BulletList[289, 367] isTight
    BulletListItem[289, 367] open:[289, 290, "*"] isTight
      Paragraph[291, 355]
        Text[291, 354] chars:[291, 354, "Numbe …  item"]
      OrderedList[355, 367] isTight delimiter:'.'
        OrderedListItem[355, 367] open:[355, 357, "1."] isTight hadBlankLineAfter
          Paragraph[358, 367]
            Text[358, 366] chars:[358, 366, "one item"]
  HtmlCommentBlock[368, 386]
  BulletList[387, 472] isTight
    BulletListItem[387, 472] open:[387, 388, "*"] isTight
      Paragraph[389, 470]
        Text[389, 469] chars:[389, 469, "Empty …  item"]
      OrderedList[470, 472] isTight delimiter:'.'
        OrderedListItem[470, 472] open:[470, 472, "1."] isTight hadBlankLineAfter
  HtmlCommentBlock[475, 493]
  BulletList[494, 583] isTight
    BulletListItem[494, 583] open:[494, 495, "*"] isTight hadBlankLineAfter
      Paragraph[496, 583]
        Text[496, 579] chars:[496, 579, "Empty …  item"]
        SoftLineBreak[579, 580]
        Text[580, 582] chars:[580, 582, "1."]
  HtmlCommentBlock[584, 602]
  BulletList[603, 689] isTight
    BulletListItem[603, 689] open:[603, 604, "*"] isTight
      Paragraph[605, 673]
        Text[605, 672] chars:[605, 672, "Numbe …  item"]
      OrderedList[673, 689] isTight start:2 delimiter:'.'
        OrderedListItem[673, 689] open:[673, 675, "2."] isTight hadBlankLineAfter
          Paragraph[676, 689]
            Text[676, 688] chars:[676, 688, "non-o …  item"]
  HtmlCommentBlock[690, 708]
  BulletList[709, 798] isTight
    BulletListItem[709, 798] open:[709, 710, "*"] isTight
      Paragraph[711, 796]
        Text[711, 795] chars:[711, 795, "Empty …  item"]
      OrderedList[796, 798] isTight start:2 delimiter:'.'
        OrderedListItem[796, 798] open:[796, 798, "2."] isTight hadBlankLineAfter
  HtmlCommentBlock[801, 819]
  BulletList[820, 913] isTight
    BulletListItem[820, 913] open:[820, 821, "*"] isTight hadBlankLineAfter
      Paragraph[822, 913]
        Text[822, 909] chars:[822, 909, "Empty …  item"]
        SoftLineBreak[909, 910]
        Text[910, 912] chars:[910, 912, "2."]
````````````````````````````````



Test to see which list items can interrupt another numbered list item's paragraphs

```````````````````````````````` example List Item Interrupts Paragraph: 3
1. Bullet item can interrupt paragraph of a numbered list item
* item

<!--List Break-->

1. Empty bullet item with space can interrupt paragraph of a numbered list item
* 

<!--List Break-->

1. Empty bullet item without space can interrupt paragraph of a numbered list item
*

<!--List Break-->

1. Numbered one item can interrupt paragraph of a numbered list item
1. one item

<!--List Break-->

1. Empty Numbered one item with space can interrupt paragraph of a numbered list item 
1. 

<!--List Break-->

1. Empty Numbered one item without space can interrupt paragraph of a numbered list item 
1.

<!--List Break-->

1. Numbered non-one item can interrupt paragraph of a numbered list item
2. non-one item

<!--List Break-->

1. Empty Numbered non-one item with space can interrupt paragraph of a numbered list item
2. 

<!--List Break-->

1. Empty Numbered non-one item without space can interrupt paragraph of a numbered list item
2.

.
<ol>
    <li>Bullet item can interrupt paragraph of a numbered list item
        <ul>
            <li>item</li>
        </ul>
    </li>
</ol>
<!--List Break-->
<ol>
    <li>Empty bullet item with space can interrupt paragraph of a numbered list item
        <ul>
            <li></li>
        </ul>
    </li>
</ol>
<!--List Break-->
<ol>
    <li>Empty bullet item without space can interrupt paragraph of a numbered list item *</li>
</ol>
<!--List Break-->
<ol>
    <li>Numbered one item can interrupt paragraph of a numbered list item</li>
    <li>one item</li>
</ol>
<!--List Break-->
<ol>
    <li>Empty Numbered one item with space can interrupt paragraph of a numbered list item</li>
    <li></li>
</ol>
<!--List Break-->
<ol>
    <li>Empty Numbered one item without space can interrupt paragraph of a numbered list item 1.</li>
</ol>
<!--List Break-->
<ol>
    <li>Numbered non-one item can interrupt paragraph of a numbered list item</li>
    <li>non-one item</li>
</ol>
<!--List Break-->
<ol>
    <li>Empty Numbered non-one item with space can interrupt paragraph of a numbered list item</li>
    <li></li>
</ol>
<!--List Break-->
<ol>
    <li>Empty Numbered non-one item without space can interrupt paragraph of a numbered list item 2.</li>
</ol>
.
Document[0, 943]
  OrderedList[0, 70] isTight delimiter:'.'
    OrderedListItem[0, 70] open:[0, 2, "1."] isTight
      Paragraph[3, 63]
        Text[3, 62] chars:[3, 62, "Bulle …  item"]
      BulletList[63, 70] isTight
        BulletListItem[63, 70] open:[63, 64, "*"] isTight hadBlankLineAfter
          Paragraph[65, 70]
            Text[65, 69] chars:[65, 69, "item"]
  HtmlCommentBlock[71, 89]
  OrderedList[90, 171] isTight delimiter:'.'
    OrderedListItem[90, 171] open:[90, 92, "1."] isTight
      Paragraph[93, 170]
        Text[93, 169] chars:[93, 169, "Empty …  item"]
      BulletList[170, 171] isTight
        BulletListItem[170, 171] open:[170, 171, "*"] isTight hadBlankLineAfter
  HtmlCommentBlock[174, 192]
  OrderedList[193, 278] isTight delimiter:'.'
    OrderedListItem[193, 278] open:[193, 195, "1."] isTight hadBlankLineAfter
      Paragraph[196, 278]
        Text[196, 275] chars:[196, 275, "Empty …  item"]
        SoftLineBreak[275, 276]
        Text[276, 277] chars:[276, 277, "*"]
  HtmlCommentBlock[279, 297]
  OrderedList[298, 379] isTight delimiter:'.'
    OrderedListItem[298, 367] open:[298, 300, "1."] isTight
      Paragraph[301, 367]
        Text[301, 366] chars:[301, 366, "Numbe …  item"]
    OrderedListItem[367, 379] open:[367, 369, "1."] isTight hadBlankLineAfter
      Paragraph[370, 379]
        Text[370, 378] chars:[370, 378, "one item"]
  HtmlCommentBlock[380, 398]
  OrderedList[399, 488] isTight delimiter:'.'
    OrderedListItem[399, 486] open:[399, 401, "1."] isTight
      Paragraph[402, 486]
        Text[402, 484] chars:[402, 484, "Empty …  item"]
    OrderedListItem[486, 488] open:[486, 488, "1."] isTight hadBlankLineAfter
  HtmlCommentBlock[491, 509]
  OrderedList[510, 603] isTight delimiter:'.'
    OrderedListItem[510, 603] open:[510, 512, "1."] isTight hadBlankLineAfter
      Paragraph[513, 603]
        Text[513, 598] chars:[513, 598, "Empty …  item"]
        SoftLineBreak[599, 600]
        Text[600, 602] chars:[600, 602, "1."]
  HtmlCommentBlock[604, 622]
  OrderedList[623, 712] isTight delimiter:'.'
    OrderedListItem[623, 696] open:[623, 625, "1."] isTight
      Paragraph[626, 696]
        Text[626, 695] chars:[626, 695, "Numbe …  item"]
    OrderedListItem[696, 712] open:[696, 698, "2."] isTight hadBlankLineAfter
      Paragraph[699, 712]
        Text[699, 711] chars:[699, 711, "non-o …  item"]
  HtmlCommentBlock[713, 731]
  OrderedList[732, 824] isTight delimiter:'.'
    OrderedListItem[732, 822] open:[732, 734, "1."] isTight
      Paragraph[735, 822]
        Text[735, 821] chars:[735, 821, "Empty …  item"]
    OrderedListItem[822, 824] open:[822, 824, "2."] isTight hadBlankLineAfter
  HtmlCommentBlock[827, 845]
  OrderedList[846, 942] isTight delimiter:'.'
    OrderedListItem[846, 942] open:[846, 848, "1."] isTight hadBlankLineAfter
      Paragraph[849, 942]
        Text[849, 938] chars:[849, 938, "Empty …  item"]
        SoftLineBreak[938, 939]
        Text[939, 941] chars:[939, 941, "2."]
````````````````````````````````



## List Item Indent Handling

Test if list item indent handling for edge cases

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
.
<ul>
    <li>item 1</li>
    <li>item 2</li>
    <li>item 3</li>
    <li>item 4 * item 5
        <ul>
            <li>item 6</li>
            <li>item 7</li>
            <li>item 8</li>
            <li>item 9</li>
        </ul>
    </li>
</ul>
.
Document[0, 117]
  BulletList[0, 117] isTight
    BulletListItem[0, 9] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[10, 19] open:[10, 11, "*"] isTight
      Paragraph[12, 19]
        Text[12, 18] chars:[12, 18, "item 2"]
    BulletListItem[21, 30] open:[21, 22, "*"] isTight
      Paragraph[23, 30]
        Text[23, 29] chars:[23, 29, "item 3"]
    BulletListItem[33, 117] open:[33, 34, "*"] isTight
      Paragraph[35, 55]
        Text[35, 41] chars:[35, 41, "item 4"]
        SoftLineBreak[41, 42]
        Text[46, 54] chars:[46, 54, "* item 5"]
      BulletList[60, 117] isTight
        BulletListItem[60, 69] open:[60, 61, "*"] isTight
          Paragraph[62, 69]
            Text[62, 68] chars:[62, 68, "item 6"]
        BulletListItem[75, 84] open:[75, 76, "*"] isTight
          Paragraph[77, 84]
            Text[77, 83] chars:[77, 83, "item 7"]
        BulletListItem[91, 100] open:[91, 92, "*"] isTight
          Paragraph[93, 100]
            Text[93, 99] chars:[93, 99, "item 8"]
        BulletListItem[108, 117] open:[108, 109, "*"] isTight
          Paragraph[110, 117]
            Text[110, 116] chars:[110, 116, "item 9"]
````````````````````````````````



```````````````````````````````` example List Item Indent Handling: 2
* item 1

 * item 2
 
  * item 3
  
   * item 4
   
    * item 5
    
     * item 6
     
      * item 7
      
       * item 8
       
        * item 9
.
<ul>
    <li>
        <p>item 1</p>
    </li>
    <li>
        <p>item 2</p>
    </li>
    <li>
        <p>item 3</p>
    </li>
    <li>
        <p>item 4</p>
    </li>
</ul>
<pre><code>* item 5

 * item 6
 
  * item 7
  
   * item 8
   
    * item 9
</code></pre>
.
Document[0, 153]
  BulletList[0, 48] isTight
    BulletListItem[0, 9] open:[0, 1, "*"] isLoose hadBlankLineAfter
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[11, 20] open:[11, 12, "*"] isLoose hadBlankLineAfter
      Paragraph[13, 20]
        Text[13, 19] chars:[13, 19, "item 2"]
    BulletListItem[24, 33] open:[24, 25, "*"] isLoose hadBlankLineAfter
      Paragraph[26, 33]
        Text[26, 32] chars:[26, 32, "item 3"]
    BulletListItem[39, 48] open:[39, 40, "*"] isLoose hadBlankLineAfter
      Paragraph[41, 48]
        Text[41, 47] chars:[41, 47, "item 4"]
  IndentedCodeBlock[56, 153]
````````````````````````````````



```````````````````````````````` example List Item Indent Handling: 3
*  item 1
 *  item 2
  *  item 3
   *  item 4
    *  item 5
     *  item 6
      *  item 7
       *  item 8
        *  item 9
.
<ul>
    <li>item 1</li>
    <li>item 2</li>
    <li>item 3</li>
    <li>item 4 *  item 5 *  item 6
        <ul>
            <li>item 7</li>
            <li>item 8</li>
            <li>item 9</li>
        </ul>
    </li>
</ul>
.
Document[0, 126]
  BulletList[0, 126] isTight
    BulletListItem[0, 10] open:[0, 1, "*"] isTight
      Paragraph[3, 10]
        Text[3, 9] chars:[3, 9, "item 1"]
    BulletListItem[11, 21] open:[11, 12, "*"] isTight
      Paragraph[14, 21]
        Text[14, 20] chars:[14, 20, "item 2"]
    BulletListItem[23, 33] open:[23, 24, "*"] isTight
      Paragraph[26, 33]
        Text[26, 32] chars:[26, 32, "item 3"]
    BulletListItem[36, 126] open:[36, 37, "*"] isTight
      Paragraph[39, 75]
        Text[39, 45] chars:[39, 45, "item 4"]
        SoftLineBreak[45, 46]
        Text[50, 59] chars:[50, 59, "*  item 5"]
        SoftLineBreak[59, 60]
        Text[65, 74] chars:[65, 74, "*  item 6"]
      BulletList[81, 126] isTight
        BulletListItem[81, 91] open:[81, 82, "*"] isTight
          Paragraph[84, 91]
            Text[84, 90] chars:[84, 90, "item 7"]
        BulletListItem[98, 108] open:[98, 99, "*"] isTight
          Paragraph[101, 108]
            Text[101, 107] chars:[101, 107, "item 8"]
        BulletListItem[116, 126] open:[116, 117, "*"] isTight
          Paragraph[119, 126]
            Text[119, 125] chars:[119, 125, "item 9"]
````````````````````````````````



Test shows where the boundary switch to indented code occurs. Sub-items first paragraph is a
paragraph, the second is indented code

```````````````````````````````` example List Item Indent Handling: 4
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
    <li>test
        <ul>
            <li>
                <p>sub item</p>
                <p>sub item child para</p>
                <pre><code>indented code
</code></pre>
            </li>
        </ul>
    </li>
</ul>
<hr />
<ol>
    <li>test
        <ol>
            <li>
                <p>sub item</p>
                <p>sub item child para</p>
                <pre><code>indented code
</code></pre>
            </li>
        </ol>
    </li>
</ol>
.
Document[0, 168]
  BulletList[0, 79] isTight
    BulletListItem[0, 79] open:[0, 1, "-"] isTight
      Paragraph[4, 9]
        Text[4, 8] chars:[4, 8, "test"]
      BulletList[13, 79] isTight
        BulletListItem[13, 79] open:[13, 14, "-"] isLoose hadBlankLineAfter
          Paragraph[15, 24]
            Text[15, 23] chars:[15, 23, "sub item"]
          Paragraph[34, 54]
            Text[34, 53] chars:[34, 53, "sub i …  para"]
          IndentedCodeBlock[65, 79]
  ThematicBreak[80, 83]
  OrderedList[85, 167] isTight delimiter:'.'
    OrderedListItem[85, 167] open:[85, 87, "1."] isTight
      Paragraph[89, 94]
        Text[89, 93] chars:[89, 93, "test"]
      OrderedList[98, 167] isTight delimiter:'.'
        OrderedListItem[98, 167] open:[98, 100, "1."] isLoose hadBlankLineAfter
          Paragraph[101, 110]
            Text[101, 109] chars:[101, 109, "sub item"]
          Paragraph[121, 141]
            Text[121, 140] chars:[121, 140, "sub i …  para"]
          IndentedCodeBlock[153, 167]
````````````````````````````````



More extensive test to show where the boundary switch to indented code occurs. Sub-items first
paragraph is a paragraph, the second is indented code

```````````````````````````````` example List Item Indent Handling: 5
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
        <pre><code>item para 5

 item para 6

  item para 7
</code></pre>
    </li>
    <li>
        <p>item 2</p>
        <p>item para 1</p>
        <p>item para 2</p>
        <p>item para 3</p>
        <p>item para 4</p>
        <pre><code>item para 5

 item para 6

  item para 7
</code></pre>
    </li>
    <li>
        <p>item 3</p>
        <p>item para 1</p>
        <p>item para 2</p>
        <p>item para 3</p>
        <p>item para 4</p>
        <pre><code>item para 5

 item para 6

  item para 7
</code></pre>
    </li>
    <li>
        <p>item 4</p>
        <p>item para 1</p>
        <p>item para 2</p>
        <p>item para 3</p>
        <p>item para 4</p>
        <pre><code>item para 5

 item para 6

  item para 7
</code></pre>
    </li>
</ul>
<pre><code>* item 5

  item para 1

   item para 2

    item para 3

     item para 4
 
      item para 5
  
       item para 6
   
        item para 7
</code></pre>
.
Document[0, 982]
  BulletList[0, 775] isTight
    BulletListItem[0, 166] open:[0, 1, "*"] isLoose hadBlankLineAfter
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
      IndentedCodeBlock[100, 166]
    BulletListItem[176, 355] open:[176, 177, "*"] isLoose hadBlankLineAfter
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
      IndentedCodeBlock[285, 355]
    BulletListItem[366, 558] open:[366, 367, "*"] isLoose hadBlankLineAfter
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
      IndentedCodeBlock[697, 775]
  IndentedCodeBlock[788, 982]
````````````````````````````````



Test for how items with indent > first list item's indent but < previous item's content indent
are handled. Mainly, if they are handled in a weird way of treating the item as a sub-item of
the previous list item. There was one that did it that way, GitHub comments if I can remember
right, but now they switched to commonmark list handling with mods. Guess it is now GFC--GitHub
Flavoured Commonmark.

```````````````````````````````` example List Item Indent Handling: 6
*  item 1
   * item 2
  * item 3
.
<ul>
    <li>item 1
        <ul>
            <li>item 2</li>
        </ul>
    </li>
    <li>item 3</li>
</ul>
.
Document[0, 33]
  BulletList[0, 33] isTight
    BulletListItem[0, 22] open:[0, 1, "*"] isTight
      Paragraph[3, 10]
        Text[3, 9] chars:[3, 9, "item 1"]
      BulletList[13, 22] isTight
        BulletListItem[13, 22] open:[13, 14, "*"] isTight
          Paragraph[15, 22]
            Text[15, 21] chars:[15, 21, "item 2"]
    BulletListItem[24, 33] open:[24, 25, "*"] isTight
      Paragraph[26, 33]
        Text[26, 32] chars:[26, 32, "item 3"]
````````````````````````````````



Test how headings in list items are handled, leading space allowed or not

```````````````````````````````` example List Item Indent Handling: 7
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
        <h1 id="heading-1">Heading 1</h1>
        <p>## Heading 2</p>
        <p>### Heading 3</p>
        <p>#### Heading 4</p>
        <pre><code>##### Heading 5

 ###### Heading 6
</code></pre>
        <ul>
            <li>
                <p>item 2</p>
                <h1 id="heading-1-1">Heading 1</h1>
                <p>## Heading 2</p>
                <p>### Heading 3</p>
                <p>#### Heading 3</p>
                <pre><code>##### Heading 5

 ###### Heading 6
</code></pre>
            </li>
        </ul>
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
      Paragraph[28, 41]
        Text[28, 40] chars:[28, 40, "## He … ing 2"]
      Paragraph[46, 60]
        Text[46, 59] chars:[46, 59, "### H … ing 3"]
      Paragraph[66, 81]
        Text[66, 80] chars:[66, 80, "####  … ing 4"]
      IndentedCodeBlock[88, 129]
      BulletList[132, 295] isTight
        BulletListItem[132, 295] open:[132, 133, "*"] isLoose hadBlankLineAfter
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


```````````````````````````````` example List Item Indent Handling: 8
* item 1
*  item 2
*   item 3
*    item 4
*     item 5
*      item 6
*       item 7
*        item 8
*         item 9
.
<ul>
    <li>item 1</li>
    <li>item 2</li>
    <li>item 3</li>
    <li>item 4</li>
    <li>item 5</li>
    <li>item 6</li>
    <li>item 7</li>
    <li>item 8</li>
    <li>item 9</li>
</ul>
.
Document[0, 117]
  BulletList[0, 117] isTight
    BulletListItem[0, 9] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 19] open:[9, 10, "*"] isTight
      Paragraph[12, 19]
        Text[12, 18] chars:[12, 18, "item 2"]
    BulletListItem[19, 30] open:[19, 20, "*"] isTight
      Paragraph[23, 30]
        Text[23, 29] chars:[23, 29, "item 3"]
    BulletListItem[30, 42] open:[30, 31, "*"] isTight
      Paragraph[35, 42]
        Text[35, 41] chars:[35, 41, "item 4"]
    BulletListItem[42, 55] open:[42, 43, "*"] isTight
      Paragraph[48, 55]
        Text[48, 54] chars:[48, 54, "item 5"]
    BulletListItem[55, 69] open:[55, 56, "*"] isTight
      Paragraph[62, 69]
        Text[62, 68] chars:[62, 68, "item 6"]
    BulletListItem[69, 84] open:[69, 70, "*"] isTight
      Paragraph[77, 84]
        Text[77, 83] chars:[77, 83, "item 7"]
    BulletListItem[84, 100] open:[84, 85, "*"] isTight
      Paragraph[93, 100]
        Text[93, 99] chars:[93, 99, "item 8"]
    BulletListItem[100, 117] open:[100, 101, "*"] isTight
      Paragraph[110, 117]
        Text[110, 116] chars:[110, 116, "item 9"]
````````````````````````````````



