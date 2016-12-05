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
    <li>
        <p>item 3</p>
    </li>
    <li>
        <p>item 4</p>
    </li>
</ul>
.
Document[0, 40]
  BulletList[0, 40] isLoose
    BulletListItem[0, 9] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[10, 20] open:[10, 11, "-"] isLoose
      Paragraph[12, 20]
        Text[12, 18] chars:[12, 18, "item 2"]
    BulletListItem[20, 30] open:[20, 21, "-"] isLoose
      Paragraph[22, 30]
        Text[22, 28] chars:[22, 28, "item 3"]
    BulletListItem[30, 40] open:[30, 31, "-"] isLoose
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
.
Document[0, 40]
  BulletList[0, 40] isLoose
    BulletListItem[0, 9] open:[0, 1, "-"] isLoose
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 19] open:[9, 10, "-"] isLoose hadBlankLineAfter
      Paragraph[11, 19]
        Text[11, 17] chars:[11, 17, "item 2"]
    BulletListItem[20, 30] open:[20, 21, "-"] isLoose
      Paragraph[22, 30]
        Text[22, 28] chars:[22, 28, "item 3"]
    BulletListItem[30, 40] open:[30, 31, "-"] isLoose
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
.
Document[0, 40]
  BulletList[0, 40] isLoose
    BulletListItem[0, 9] open:[0, 1, "-"] isLoose
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 19] open:[9, 10, "-"] isLoose
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

Empty bullet item can interrupt paragraph
* 

Numbered one item can interrupt paragraph
1. one item

Empty Numbered one item can interrupt paragraph
1. 

Numbered non-one item can interrupt paragraph
2. non-one item

Empty Numbered non-one item can interrupt paragraph
2. 

.
<p>Bullet item can interrupt paragraph * item</p>
<p>Empty bullet item can interrupt paragraph *</p>
<p>Numbered one item can interrupt paragraph 1. one item</p>
<p>Empty Numbered one item can interrupt paragraph 1.</p>
<p>Numbered non-one item can interrupt paragraph 2. non-one item</p>
<p>Empty Numbered non-one item can interrupt paragraph 2.</p>
.
Document[0, 318]
  Paragraph[0, 43]
    Text[0, 35] chars:[0, 35, "Bulle … graph"]
    SoftLineBreak[35, 36]
    Text[36, 42] chars:[36, 42, "* item"]
  Paragraph[44, 89]
    Text[44, 85] chars:[44, 85, "Empty … graph"]
    SoftLineBreak[85, 86]
    Text[86, 87] chars:[86, 87, "*"]
  Paragraph[90, 144]
    Text[90, 131] chars:[90, 131, "Numbe … graph"]
    SoftLineBreak[131, 132]
    Text[132, 143] chars:[132, 143, "1. on …  item"]
  Paragraph[145, 197]
    Text[145, 192] chars:[145, 192, "Empty … graph"]
    SoftLineBreak[192, 193]
    Text[193, 195] chars:[193, 195, "1."]
  Paragraph[198, 260]
    Text[198, 243] chars:[198, 243, "Numbe … graph"]
    SoftLineBreak[243, 244]
    Text[244, 259] chars:[244, 259, "2. no …  item"]
  Paragraph[261, 317]
    Text[261, 312] chars:[261, 312, "Empty … graph"]
    SoftLineBreak[312, 313]
    Text[313, 315] chars:[313, 315, "2."]
````````````````````````````````



Test to see which list items can interrupt another bullet list item's paragraphs

```````````````````````````````` example List Item Interrupts Paragraph: 2
* Bullet item can interrupt paragraph of a bullet list item
* item

<!--List Break-->

* Empty bullet item can interrupt paragraph of a bullet list item
* 

<!--List Break-->

* Numbered one item can interrupt paragraph of a bullet list item
1. one item

<!--List Break-->

* Empty Numbered one item can interrupt paragraph of a bullet list item
1. 

<!--List Break-->

* Numbered non-one item can interrupt paragraph of a bullet list item
2. non-one item

<!--List Break-->

* Empty Numbered non-one item can interrupt paragraph of a bullet list item
2. 

.
<ul>
    <li>Bullet item can interrupt paragraph of a bullet list item</li>
    <li>item</li>
</ul>
<!--List Break-->
<ul>
    <li>Empty bullet item can interrupt paragraph of a bullet list item</li>
    <li></li>
</ul>
<!--List Break-->
<ul>
    <li>Numbered one item can interrupt paragraph of a bullet list item</li>
    <li>one item</li>
</ul>
<!--List Break-->
<ul>
    <li>Empty Numbered one item can interrupt paragraph of a bullet list item</li>
    <li></li>
</ul>
<!--List Break-->
<ul>
    <li>Numbered non-one item can interrupt paragraph of a bullet list item</li>
    <li>non-one item</li>
</ul>
<!--List Break-->
<ul>
    <li>Empty Numbered non-one item can interrupt paragraph of a bullet list item</li>
    <li></li>
</ul>
.
Document[0, 557]
  BulletList[0, 67] isTight
    BulletListItem[0, 60] open:[0, 1, "*"] isTight
      Paragraph[2, 60]
        Text[2, 59] chars:[2, 59, "Bulle …  item"]
    BulletListItem[60, 67] open:[60, 61, "*"] isTight hadBlankLineAfter
      Paragraph[62, 67]
        Text[62, 66] chars:[62, 66, "item"]
  HtmlCommentBlock[68, 86]
  BulletList[87, 154] isTight
    BulletListItem[87, 153] open:[87, 88, "*"] isTight
      Paragraph[89, 153]
        Text[89, 152] chars:[89, 152, "Empty …  item"]
    BulletListItem[153, 154] open:[153, 154, "*"] isTight hadBlankLineAfter
  HtmlCommentBlock[157, 175]
  BulletList[176, 254] isTight
    BulletListItem[176, 242] open:[176, 177, "*"] isTight
      Paragraph[178, 242]
        Text[178, 241] chars:[178, 241, "Numbe …  item"]
    OrderedListItem[242, 254] open:[242, 244, "1."] isTight hadBlankLineAfter
      Paragraph[245, 254]
        Text[245, 253] chars:[245, 253, "one item"]
  HtmlCommentBlock[255, 273]
  BulletList[274, 348] isTight
    BulletListItem[274, 346] open:[274, 275, "*"] isTight
      Paragraph[276, 346]
        Text[276, 345] chars:[276, 345, "Empty …  item"]
    OrderedListItem[346, 348] open:[346, 348, "1."] isTight hadBlankLineAfter
  HtmlCommentBlock[351, 369]
  BulletList[370, 456] isTight
    BulletListItem[370, 440] open:[370, 371, "*"] isTight
      Paragraph[372, 440]
        Text[372, 439] chars:[372, 439, "Numbe …  item"]
    OrderedListItem[440, 456] open:[440, 442, "2."] isTight hadBlankLineAfter
      Paragraph[443, 456]
        Text[443, 455] chars:[443, 455, "non-o …  item"]
  HtmlCommentBlock[457, 475]
  BulletList[476, 554] isTight
    BulletListItem[476, 552] open:[476, 477, "*"] isTight
      Paragraph[478, 552]
        Text[478, 551] chars:[478, 551, "Empty …  item"]
    OrderedListItem[552, 554] open:[552, 554, "2."] isTight hadBlankLineAfter
````````````````````````````````



Test to see which list items can interrupt another numbered list item's paragraphs

```````````````````````````````` example List Item Interrupts Paragraph: 3
1. Bullet item can interrupt paragraph of a numbered list item
* item

<!--List Break-->

1. Empty bullet item can interrupt paragraph of a numbered list item
* 

<!--List Break-->

1. Numbered one item can interrupt paragraph of a numbered list item
1. one item

<!--List Break-->

1. Empty Numbered one item can interrupt paragraph of a numbered list item 
1.

<!--List Break-->

1. Numbered non-one item can interrupt paragraph of a numbered list item
2. non-one item

<!--List Break-->

1. Empty Numbered non-one item can interrupt paragraph of a numbered list item
2. 

.
<ol>
    <li>Bullet item can interrupt paragraph of a numbered list item</li>
    <li>item</li>
</ol>
<!--List Break-->
<ol>
    <li>Empty bullet item can interrupt paragraph of a numbered list item</li>
    <li></li>
</ol>
<!--List Break-->
<ol>
    <li>Numbered one item can interrupt paragraph of a numbered list item</li>
    <li>one item</li>
</ol>
<!--List Break-->
<ol>
    <li>Empty Numbered one item can interrupt paragraph of a numbered list item</li>
    <li></li>
</ol>
<!--List Break-->
<ol>
    <li>Numbered non-one item can interrupt paragraph of a numbered list item</li>
    <li>non-one item</li>
</ol>
<!--List Break-->
<ol>
    <li>Empty Numbered non-one item can interrupt paragraph of a numbered list item</li>
    <li></li>
</ol>
.
Document[0, 575]
  OrderedList[0, 70] isTight delimiter:'.'
    OrderedListItem[0, 63] open:[0, 2, "1."] isTight
      Paragraph[3, 63]
        Text[3, 62] chars:[3, 62, "Bulle …  item"]
    BulletListItem[63, 70] open:[63, 64, "*"] isTight hadBlankLineAfter
      Paragraph[65, 70]
        Text[65, 69] chars:[65, 69, "item"]
  HtmlCommentBlock[71, 89]
  OrderedList[90, 160] isTight delimiter:'.'
    OrderedListItem[90, 159] open:[90, 92, "1."] isTight
      Paragraph[93, 159]
        Text[93, 158] chars:[93, 158, "Empty …  item"]
    BulletListItem[159, 160] open:[159, 160, "*"] isTight hadBlankLineAfter
  HtmlCommentBlock[163, 181]
  OrderedList[182, 263] isTight delimiter:'.'
    OrderedListItem[182, 251] open:[182, 184, "1."] isTight
      Paragraph[185, 251]
        Text[185, 250] chars:[185, 250, "Numbe …  item"]
    OrderedListItem[251, 263] open:[251, 253, "1."] isTight hadBlankLineAfter
      Paragraph[254, 263]
        Text[254, 262] chars:[254, 262, "one item"]
  HtmlCommentBlock[264, 282]
  OrderedList[283, 361] isTight delimiter:'.'
    OrderedListItem[283, 359] open:[283, 285, "1."] isTight
      Paragraph[286, 359]
        Text[286, 357] chars:[286, 357, "Empty …  item"]
    OrderedListItem[359, 361] open:[359, 361, "1."] isTight hadBlankLineAfter
  HtmlCommentBlock[363, 381]
  OrderedList[382, 471] isTight delimiter:'.'
    OrderedListItem[382, 455] open:[382, 384, "1."] isTight
      Paragraph[385, 455]
        Text[385, 454] chars:[385, 454, "Numbe …  item"]
    OrderedListItem[455, 471] open:[455, 457, "2."] isTight hadBlankLineAfter
      Paragraph[458, 471]
        Text[458, 470] chars:[458, 470, "non-o …  item"]
  HtmlCommentBlock[472, 490]
  OrderedList[491, 572] isTight delimiter:'.'
    OrderedListItem[491, 570] open:[491, 493, "1."] isTight
      Paragraph[494, 570]
        Text[494, 569] chars:[494, 569, "Empty …  item"]
    OrderedListItem[570, 572] open:[570, 572, "2."] isTight hadBlankLineAfter
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
    <li>item 4
        <ul>
            <li>item 5</li>
            <li>item 6</li>
            <li>item 7</li>
            <li>item 8
                <ul>
                    <li>item 9</li>
                </ul>
            </li>
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
    BulletListItem[33, 42] open:[33, 34, "*"] isTight
      Paragraph[35, 42]
        Text[35, 41] chars:[35, 41, "item 4"]
    BulletListItem[46, 55] open:[46, 47, "*"] isTight
      Paragraph[48, 55]
        Text[48, 54] chars:[48, 54, "item 5"]
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
        <ul>
            <li>
                <p>item 5</p>
            </li>
            <li>
                <p>item 6</p>
            </li>
            <li>
                <p>item 7</p>
            </li>
            <li>
                <p>item 8</p>
                <ul>
                    <li>item 9</li>
                </ul>
            </li>
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
    BulletListItem[33, 42] open:[33, 34, "*"] isTight
      Paragraph[35, 42]
        Text[35, 41] chars:[35, 41, "item 4"]
    BulletListItem[46, 55] open:[46, 47, "*"] isTight
      Paragraph[48, 55]
        Text[48, 54] chars:[48, 54, "item 5"]
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
    <li>item 4
        <ul>
            <li>item 5</li>
            <li>item 6</li>
            <li>item 7</li>
            <li>item 8
                <ul>
                    <li>item 9</li>
                </ul>
            </li>
        </ul>
    </li>
</ul>
.
Document[0, 125]
  BulletList[0, 125] isTight
    BulletListItem[0, 9] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[10, 20] open:[10, 11, "*"] isTight
      Paragraph[13, 20]
        Text[13, 19] chars:[13, 19, "item 2"]
    BulletListItem[22, 32] open:[22, 23, "*"] isTight
      Paragraph[25, 32]
        Text[25, 31] chars:[25, 31, "item 3"]
    BulletListItem[35, 45] open:[35, 36, "*"] isTight
      Paragraph[38, 45]
        Text[38, 44] chars:[38, 44, "item 4"]
    BulletListItem[49, 59] open:[49, 50, "*"] isTight
      Paragraph[52, 59]
        Text[52, 58] chars:[52, 58, "item 5"]
    BulletListItem[64, 74] open:[64, 65, "*"] isTight
      Paragraph[67, 74]
        Text[67, 73] chars:[67, 73, "item 6"]
    BulletListItem[80, 90] open:[80, 81, "*"] isTight
      Paragraph[83, 90]
        Text[83, 89] chars:[83, 89, "item 7"]
    BulletListItem[97, 107] open:[97, 98, "*"] isTight
      Paragraph[100, 107]
        Text[100, 106] chars:[100, 106, "item 8"]
    BulletListItem[115, 125] open:[115, 116, "*"] isTight
      Paragraph[118, 125]
        Text[118, 124] chars:[118, 124, "item 9"]
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
                <p>indented code</p>
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
                <p>indented code</p>
            </li>
        </ol>
    </li>
</ol>
.
Document[0, 167]
  BulletList[0, 79] isTight
    BulletListItem[0, 79] open:[0, 1, "-"] isTight
      Paragraph[4, 9]
        Text[4, 8] chars:[4, 8, "test"]
      BulletList[13, 79] isLoose
        BulletListItem[13, 79] open:[13, 14, "-"] isLoose
          Paragraph[15, 24]
            Text[15, 23] chars:[15, 23, "sub item"]
          Paragraph[34, 54]
            Text[34, 53] chars:[34, 53, "sub i …  para"]
          IndentedCodeBlock[65, 79]
  ThematicBreak[79, 82]
  OrderedList[84, 166] isTight delimiter:'.'
    OrderedListItem[84, 166] open:[84, 86, "1."] isTight
      Paragraph[88, 93]
        Text[88, 92] chars:[88, 92, "test"]
      OrderedList[97, 166] isLoose delimiter:'.'
        OrderedListItem[97, 166] open:[97, 99, "1."] isLoose
          Paragraph[100, 109]
            Text[100, 108] chars:[100, 108, "sub item"]
          Paragraph[120, 140]
            Text[120, 139] chars:[120, 139, "sub i …  para"]
          IndentedCodeBlock[152, 166]
````````````````````````````````


Test shows where the boundary switch to indented code occurs. Sub-items first paragraph is a
paragraph, the second is indented code

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
    <li>item 1</li>
</ul>
<p>item para 1</p>
<p>item para 2</p>
<pre><code>item para 3

 item para 4

  item para 5

   item para 6

    item para 7
</code></pre>

<ul>
    <li>item 2</li>
</ul>
<p>item para 1</p>
<pre><code>item para 2

 item para 3

  item para 4

   item para 5

    item para 6

     item para 7
</code></pre>

<ul>
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
<pre><code>item para 4

 item para 5

  item para 6

   item para 7
</code></pre>

        <ul>
            <li>item 5</li>
        </ul>
        <p>item para 1</p>
        <p>item para 2</p>
<pre><code>item para 3

 item para 4

  item para 5

   item para 6

    item para 7
</code></pre>
    </li>
</ul>
.
Document[0, 982]
  BulletList[0, 982] isLoose
    BulletListItem[0, 166] open:[0, 1, "*"] isLoose
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
    BulletListItem[176, 355] open:[176, 177, "*"] isLoose
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
    BulletListItem[366, 558] open:[366, 367, "*"] isLoose
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
    BulletListItem[570, 775] open:[570, 571, "*"] isLoose
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
    BulletListItem[788, 982] open:[788, 789, "*"] isLoose
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
      IndentedCodeBlock[908, 982]
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
    <li>item 1</li>
    <li>item 2</li>
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
    <li>item 1</li>
</ul>
<h1 id="heading-1">Heading 1</h1>
<h2 id="heading-2">Heading 2</h2>
<pre><code>### Heading 3

 #### Heading 4

  ##### Heading 5

   ###### Heading 6
</code></pre>
<ul>
    <li>
        <p>item 2</p>
        <h1 id="heading-1-1">Heading 1</h1>
        <h2 id="heading-2-1">Heading 2</h2>
        <h3 id="heading-3">Heading 3</h3>
        <h4 id="heading-3-1">Heading 3</h4>
        <pre><code>##### Heading 5

 ###### Heading 6
</code></pre>
    </li>
</ul>
.
Document[0, 296]
  BulletList[0, 9] isTight
    BulletListItem[0, 9] open:[0, 1, "*"] isTight hadBlankLineAfter
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
  Heading[12, 23] textOpen:[12, 13, "#"] text:[14, 23, "Heading 1"]
    Text[14, 23] chars:[14, 23, "Heading 1"]
  Heading[28, 40] textOpen:[28, 30, "##"] text:[31, 40, "Heading 2"]
    Text[31, 40] chars:[31, 40, "Heading 2"]
  IndentedCodeBlock[46, 129]
  BulletList[132, 295] isLoose
    BulletListItem[132, 295] open:[132, 133, "*"] isLoose hadBlankLineAfter
      Paragraph[134, 141]
        Text[134, 140] chars:[134, 140, "item 2"]
      Heading[146, 157] textOpen:[146, 147, "#"] text:[148, 157, "Heading 1"]
        Text[148, 157] chars:[148, 157, "Heading 1"]
      Heading[168, 180] textOpen:[168, 170, "##"] text:[171, 180, "Heading 2"]
        Text[171, 180] chars:[171, 180, "Heading 2"]
      Heading[192, 205] textOpen:[192, 195, "###"] text:[196, 205, "Heading 3"]
        Text[196, 205] chars:[196, 205, "Heading 3"]
      Heading[218, 232] textOpen:[218, 222, "####"] text:[223, 232, "Heading 3"]
        Text[223, 232] chars:[223, 232, "Heading 3"]
      IndentedCodeBlock[246, 295]
````````````````````````````````



