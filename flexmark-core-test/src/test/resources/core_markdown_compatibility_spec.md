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

```````````````````````````````` example Bullet Mismatch Starts a New List: 1
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
      Paragraph[16, 21] isTrailingBlankLine
        Text[16, 20] chars:[16, 20, "item"]
````````````````````````````````


## Ordered List Item Sets List Start

Test to see if ordered list item will set list start if not one

```````````````````````````````` example Ordered List Item Sets List Start: 1
2. Non One Start Item
.
<ol>
    <li>Non One Start Item</li>
</ol>
.
Document[0, 21]
  OrderedList[0, 21] isTight start:2 delimiter:'.'
    OrderedListItem[0, 21] open:[0, 2, "2."] isTight
      Paragraph[3, 21]
        Text[3, 21] chars:[3, 21, "Non O …  Item"]
````````````````````````````````


## Mismatched List Item Type Handling

Test how mismatches in item types are handled

```````````````````````````````` example Mismatched List Item Type Handling: 1
- Bullet List
1. With Ordered Item
.
<ul>
    <li>Bullet List</li>
    <li>With Ordered Item</li>
</ul>
.
Document[0, 34]
  BulletList[0, 34] isTight
    BulletListItem[0, 14] open:[0, 1, "-"] isTight
      Paragraph[2, 14]
        Text[2, 13] chars:[2, 13, "Bulle …  List"]
    OrderedListItem[14, 34] open:[14, 16, "1."] isTight
      Paragraph[17, 34]
        Text[17, 34] chars:[17, 34, "With  …  Item"]
````````````````````````````````


```````````````````````````````` example Mismatched List Item Type Handling: 2
1. Ordered Item
- With Bullet List
.
<ol>
    <li>Ordered Item</li>
    <li>With Bullet List</li>
</ol>
.
Document[0, 34]
  OrderedList[0, 34] isTight delimiter:'.'
    OrderedListItem[0, 16] open:[0, 2, "1."] isTight
      Paragraph[3, 16]
        Text[3, 15] chars:[3, 15, "Order …  Item"]
    BulletListItem[16, 34] open:[16, 17, "-"] isTight
      Paragraph[18, 34]
        Text[18, 34] chars:[18, 34, "With  …  List"]
````````````````````````````````


## Loose Item Handling

Tests how all tight items are generated

```````````````````````````````` example Loose Item Handling: 1
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
Document[0, 38]
  BulletList[0, 38] isTight
    BulletListItem[0, 9] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 19] open:[9, 10, "-"] isTight
      Paragraph[11, 19]
        Text[11, 17] chars:[11, 17, "item 2"]
    BulletListItem[19, 29] open:[19, 20, "-"] isTight
      Paragraph[21, 29]
        Text[21, 27] chars:[21, 27, "item 3"]
    BulletListItem[29, 38] open:[29, 30, "-"] isTight
      Paragraph[31, 38]
        Text[31, 37] chars:[31, 37, "item 4"]
````````````````````````````````


Test to see how trailing blank after item determines looseness

```````````````````````````````` example Loose Item Handling: 2
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
Document[0, 39]
  BulletList[0, 39] isTight
    BulletListItem[0, 9] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[2, 9] isTrailingBlankLine
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[10, 20] open:[10, 11, "-"] isLoose
      Paragraph[12, 20]
        Text[12, 18] chars:[12, 18, "item 2"]
    BulletListItem[20, 30] open:[20, 21, "-"] isTight
      Paragraph[22, 30]
        Text[22, 28] chars:[22, 28, "item 3"]
    BulletListItem[30, 39] open:[30, 31, "-"] isTight
      Paragraph[32, 39]
        Text[32, 38] chars:[32, 38, "item 4"]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 3
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
Document[0, 39]
  BulletList[0, 39] isTight
    BulletListItem[0, 9] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 19] open:[9, 10, "-"] isLoose hadBlankLineAfter
      Paragraph[11, 19] isTrailingBlankLine
        Text[11, 17] chars:[11, 17, "item 2"]
    BulletListItem[20, 30] open:[20, 21, "-"] isLoose
      Paragraph[22, 30]
        Text[22, 28] chars:[22, 28, "item 3"]
    BulletListItem[30, 39] open:[30, 31, "-"] isTight
      Paragraph[32, 39]
        Text[32, 38] chars:[32, 38, "item 4"]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 4
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
Document[0, 39]
  BulletList[0, 39] isTight
    BulletListItem[0, 9] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 19] open:[9, 10, "-"] isTight
      Paragraph[11, 19]
        Text[11, 17] chars:[11, 17, "item 2"]
    BulletListItem[19, 29] open:[19, 20, "-"] isLoose hadBlankLineAfter
      Paragraph[21, 29] isTrailingBlankLine
        Text[21, 27] chars:[21, 27, "item 3"]
    BulletListItem[30, 39] open:[30, 31, "-"] isLoose
      Paragraph[32, 39]
        Text[32, 38] chars:[32, 38, "item 4"]
````````````````````````````````


Test looseness with child items

```````````````````````````````` example Loose Item Handling: 5
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
Document[0, 101]
  BulletList[0, 101] isTight
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
    BulletListItem[76, 101] open:[76, 77, "-"] isTight
      Paragraph[78, 86]
        Text[78, 84] chars:[78, 84, "item 4"]
      BulletList[90, 101] isTight
        BulletListItem[90, 101] open:[90, 91, "-"] isTight
          Paragraph[92, 101]
            Text[92, 100] chars:[92, 100, "item 4.1"]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 6
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
Document[0, 102]
  BulletList[0, 102] isTight
    BulletListItem[0, 25] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[2, 9] isTrailingBlankLine
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
    BulletListItem[77, 102] open:[77, 78, "-"] isTight
      Paragraph[79, 87]
        Text[79, 85] chars:[79, 85, "item 4"]
      BulletList[91, 102] isTight
        BulletListItem[91, 102] open:[91, 92, "-"] isTight
          Paragraph[93, 102]
            Text[93, 101] chars:[93, 101, "item 4.1"]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 7
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
Document[0, 102]
  BulletList[0, 102] isTight
    BulletListItem[0, 24] open:[0, 1, "-"] isLoose
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[13, 24] isTight
        BulletListItem[13, 24] open:[13, 14, "-"] isTight hadBlankLineAfter
          Paragraph[15, 24] isTrailingBlankLine
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
    BulletListItem[77, 102] open:[77, 78, "-"] isTight
      Paragraph[79, 87]
        Text[79, 85] chars:[79, 85, "item 4"]
      BulletList[91, 102] isTight
        BulletListItem[91, 102] open:[91, 92, "-"] isTight
          Paragraph[93, 102]
            Text[93, 101] chars:[93, 101, "item 4.1"]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 8
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
Document[0, 102]
  BulletList[0, 102] isTight
    BulletListItem[0, 24] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[13, 24] isTight
        BulletListItem[13, 24] open:[13, 14, "-"] isTight
          Paragraph[15, 24]
            Text[15, 23] chars:[15, 23, "item 1.1"]
    BulletListItem[24, 51] open:[24, 25, "-"] isLoose hadBlankLineAfter
      Paragraph[26, 34] isTrailingBlankLine
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
    BulletListItem[77, 102] open:[77, 78, "-"] isTight
      Paragraph[79, 87]
        Text[79, 85] chars:[79, 85, "item 4"]
      BulletList[91, 102] isTight
        BulletListItem[91, 102] open:[91, 92, "-"] isTight
          Paragraph[93, 102]
            Text[93, 101] chars:[93, 101, "item 4.1"]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 9
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
Document[0, 102]
  BulletList[0, 102] isTight
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
          Paragraph[40, 50] isTrailingBlankLine
            Text[40, 48] chars:[40, 48, "item 2.1"]
    BulletListItem[51, 77] open:[51, 52, "-"] isLoose
      Paragraph[53, 61]
        Text[53, 59] chars:[53, 59, "item 3"]
      BulletList[65, 77] isTight
        BulletListItem[65, 77] open:[65, 66, "-"] isTight
          Paragraph[67, 77]
            Text[67, 75] chars:[67, 75, "item 3.1"]
    BulletListItem[77, 102] open:[77, 78, "-"] isTight
      Paragraph[79, 87]
        Text[79, 85] chars:[79, 85, "item 4"]
      BulletList[91, 102] isTight
        BulletListItem[91, 102] open:[91, 92, "-"] isTight
          Paragraph[93, 102]
            Text[93, 101] chars:[93, 101, "item 4.1"]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 10
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
    BulletListItem[50, 77] open:[50, 51, "-"] isLoose hadBlankLineAfter
      Paragraph[52, 60] isTrailingBlankLine
        Text[52, 58] chars:[52, 58, "item 3"]
      BulletList[65, 77] isTight
        BulletListItem[65, 77] open:[65, 66, "-"] isTight
          Paragraph[67, 77]
            Text[67, 75] chars:[67, 75, "item 3.1"]
    BulletListItem[77, 102] open:[77, 78, "-"] isTight
      Paragraph[79, 87]
        Text[79, 85] chars:[79, 85, "item 4"]
      BulletList[91, 102] isTight
        BulletListItem[91, 102] open:[91, 92, "-"] isTight
          Paragraph[93, 102]
            Text[93, 101] chars:[93, 101, "item 4.1"]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 11
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
    BulletListItem[50, 76] open:[50, 51, "-"] isLoose
      Paragraph[52, 60]
        Text[52, 58] chars:[52, 58, "item 3"]
      BulletList[64, 76] isTight
        BulletListItem[64, 76] open:[64, 65, "-"] isTight hadBlankLineAfter
          Paragraph[66, 76] isTrailingBlankLine
            Text[66, 74] chars:[66, 74, "item 3.1"]
    BulletListItem[77, 102] open:[77, 78, "-"] isLoose
      Paragraph[79, 87]
        Text[79, 85] chars:[79, 85, "item 4"]
      BulletList[91, 102] isTight
        BulletListItem[91, 102] open:[91, 92, "-"] isTight
          Paragraph[93, 102]
            Text[93, 101] chars:[93, 101, "item 4.1"]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 12
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
    BulletListItem[76, 102] open:[76, 77, "-"] isLoose hadBlankLineAfter
      Paragraph[78, 86] isTrailingBlankLine
        Text[78, 84] chars:[78, 84, "item 4"]
      BulletList[91, 102] isTight
        BulletListItem[91, 102] open:[91, 92, "-"] isTight
          Paragraph[93, 102]
            Text[93, 101] chars:[93, 101, "item 4.1"]
````````````````````````````````


## List Item Interrupts Paragraph

Test to see which list items can interrupt paragraphs.

```````````````````````````````` example List Item Interrupts Paragraph: 1
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
  Paragraph[0, 43] isTrailingBlankLine
    Text[0, 35] chars:[0, 35, "Bulle … graph"]
    SoftLineBreak[35, 36]
    Text[36, 42] chars:[36, 42, "* item"]
  Paragraph[44, 101] isTrailingBlankLine
    Text[44, 96] chars:[44, 96, "Empty … graph"]
    SoftLineBreak[97, 98]
    Text[98, 99] chars:[98, 99, "*"]
  Paragraph[102, 160] isTrailingBlankLine
    Text[102, 157] chars:[102, 157, "Empty … graph"]
    SoftLineBreak[157, 158]
    Text[158, 159] chars:[158, 159, "*"]
  Paragraph[161, 215] isTrailingBlankLine
    Text[161, 202] chars:[161, 202, "Numbe … graph"]
    SoftLineBreak[202, 203]
    Text[203, 214] chars:[203, 214, "1. on …  item"]
  Paragraph[216, 279] isTrailingBlankLine
    Text[216, 274] chars:[216, 274, "Empty … graph"]
    SoftLineBreak[274, 275]
    Text[275, 277] chars:[275, 277, "1."]
  Paragraph[280, 345] isTrailingBlankLine
    Text[280, 341] chars:[280, 341, "Empty … graph"]
    SoftLineBreak[341, 342]
    Text[342, 344] chars:[342, 344, "1."]
  Paragraph[346, 408] isTrailingBlankLine
    Text[346, 391] chars:[346, 391, "Numbe … graph"]
    SoftLineBreak[391, 392]
    Text[392, 407] chars:[392, 407, "2. no …  item"]
  Paragraph[409, 476] isTrailingBlankLine
    Text[409, 471] chars:[409, 471, "Empty … graph"]
    SoftLineBreak[471, 472]
    Text[472, 474] chars:[472, 474, "2."]
  Paragraph[477, 546] isTrailingBlankLine
    Text[477, 542] chars:[477, 542, "Empty … graph"]
    SoftLineBreak[542, 543]
    Text[543, 545] chars:[543, 545, "2."]
````````````````````````````````


Test to see which list items can interrupt another bullet list item's paragraphs

```````````````````````````````` example List Item Interrupts Paragraph: 2
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
      Paragraph[62, 67] isTrailingBlankLine
        Text[62, 66] chars:[62, 66, "item"]
  Paragraph[68, 79] isTrailingBlankLine
    Text[68, 78] chars:[68, 78, "list break"]
  BulletList[80, 160] isTight
    BulletListItem[80, 160] open:[80, 81, "*"] isTight hadBlankLineAfter
      Paragraph[82, 160] isTrailingBlankLine
        Text[82, 156] chars:[82, 156, "Empty …  item"]
        SoftLineBreak[156, 157]
        Text[157, 158] chars:[157, 158, "*"]
  Paragraph[161, 172] isTrailingBlankLine
    Text[161, 171] chars:[161, 171, "list break"]
  BulletList[173, 255] isTight
    BulletListItem[173, 255] open:[173, 174, "*"] isTight hadBlankLineAfter
      Paragraph[175, 255] isTrailingBlankLine
        Text[175, 252] chars:[175, 252, "Empty …  item"]
        SoftLineBreak[252, 253]
        Text[253, 254] chars:[253, 254, "*"]
  Paragraph[256, 267] isTrailingBlankLine
    Text[256, 266] chars:[256, 266, "list break"]
  BulletList[268, 346] isTight
    BulletListItem[268, 334] open:[268, 269, "*"] isTight
      Paragraph[270, 334]
        Text[270, 333] chars:[270, 333, "Numbe …  item"]
    OrderedListItem[334, 346] open:[334, 336, "1."] isTight hadBlankLineAfter
      Paragraph[337, 346] isTrailingBlankLine
        Text[337, 345] chars:[337, 345, "one item"]
  Paragraph[347, 358] isTrailingBlankLine
    Text[347, 357] chars:[347, 357, "list break"]
  BulletList[359, 446] isTight
    BulletListItem[359, 446] open:[359, 360, "*"] isTight hadBlankLineAfter
      Paragraph[361, 446] isTrailingBlankLine
        Text[361, 441] chars:[361, 441, "Empty …  item"]
        SoftLineBreak[441, 442]
        Text[442, 444] chars:[442, 444, "1."]
  Paragraph[447, 458] isTrailingBlankLine
    Text[447, 457] chars:[447, 457, "list break"]
  BulletList[459, 548] isTight
    BulletListItem[459, 548] open:[459, 460, "*"] isTight hadBlankLineAfter
      Paragraph[461, 548] isTrailingBlankLine
        Text[461, 544] chars:[461, 544, "Empty …  item"]
        SoftLineBreak[544, 545]
        Text[545, 547] chars:[545, 547, "1."]
  Paragraph[549, 560] isTrailingBlankLine
    Text[549, 559] chars:[549, 559, "list break"]
  BulletList[561, 647] isTight
    BulletListItem[561, 631] open:[561, 562, "*"] isTight
      Paragraph[563, 631]
        Text[563, 630] chars:[563, 630, "Numbe …  item"]
    OrderedListItem[631, 647] open:[631, 633, "2."] isTight hadBlankLineAfter
      Paragraph[634, 647] isTrailingBlankLine
        Text[634, 646] chars:[634, 646, "non-o …  item"]
  Paragraph[648, 659] isTrailingBlankLine
    Text[648, 658] chars:[648, 658, "list break"]
  BulletList[660, 751] isTight
    BulletListItem[660, 751] open:[660, 661, "*"] isTight hadBlankLineAfter
      Paragraph[662, 751] isTrailingBlankLine
        Text[662, 746] chars:[662, 746, "Empty …  item"]
        SoftLineBreak[746, 747]
        Text[747, 749] chars:[747, 749, "2."]
  Paragraph[752, 763] isTrailingBlankLine
    Text[752, 762] chars:[752, 762, "list break"]
  BulletList[764, 857] isTight
    BulletListItem[764, 857] open:[764, 765, "*"] isTight hadBlankLineAfter
      Paragraph[766, 857] isTrailingBlankLine
        Text[766, 853] chars:[766, 853, "Empty …  item"]
        SoftLineBreak[853, 854]
        Text[854, 856] chars:[854, 856, "2."]
````````````````````````````````


Test to see which list items can interrupt another numbered list item's paragraphs

```````````````````````````````` example List Item Interrupts Paragraph: 3
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
      Paragraph[65, 70] isTrailingBlankLine
        Text[65, 69] chars:[65, 69, "item"]
  Paragraph[71, 82] isTrailingBlankLine
    Text[71, 81] chars:[71, 81, "list break"]
  OrderedList[83, 166] isTight delimiter:'.'
    OrderedListItem[83, 166] open:[83, 85, "1."] isTight hadBlankLineAfter
      Paragraph[86, 166] isTrailingBlankLine
        Text[86, 162] chars:[86, 162, "Empty …  item"]
        SoftLineBreak[162, 163]
        Text[163, 164] chars:[163, 164, "*"]
  Paragraph[167, 178] isTrailingBlankLine
    Text[167, 177] chars:[167, 177, "list break"]
  OrderedList[179, 264] isTight delimiter:'.'
    OrderedListItem[179, 264] open:[179, 181, "1."] isTight hadBlankLineAfter
      Paragraph[182, 264] isTrailingBlankLine
        Text[182, 261] chars:[182, 261, "Empty …  item"]
        SoftLineBreak[261, 262]
        Text[262, 263] chars:[262, 263, "*"]
  Paragraph[265, 276] isTrailingBlankLine
    Text[265, 275] chars:[265, 275, "list break"]
  OrderedList[277, 358] isTight delimiter:'.'
    OrderedListItem[277, 346] open:[277, 279, "1."] isTight
      Paragraph[280, 346]
        Text[280, 345] chars:[280, 345, "Numbe …  item"]
    OrderedListItem[346, 358] open:[346, 348, "1."] isTight hadBlankLineAfter
      Paragraph[349, 358] isTrailingBlankLine
        Text[349, 357] chars:[349, 357, "one item"]
  Paragraph[359, 370] isTrailingBlankLine
    Text[359, 369] chars:[359, 369, "list break"]
  OrderedList[371, 462] isTight delimiter:'.'
    OrderedListItem[371, 462] open:[371, 373, "1."] isTight hadBlankLineAfter
      Paragraph[374, 462] isTrailingBlankLine
        Text[374, 456] chars:[374, 456, "Empty …  item"]
        SoftLineBreak[457, 458]
        Text[458, 460] chars:[458, 460, "1."]
  Paragraph[463, 474] isTrailingBlankLine
    Text[463, 473] chars:[463, 473, "list break"]
  OrderedList[475, 568] isTight delimiter:'.'
    OrderedListItem[475, 568] open:[475, 477, "1."] isTight hadBlankLineAfter
      Paragraph[478, 568] isTrailingBlankLine
        Text[478, 563] chars:[478, 563, "Empty …  item"]
        SoftLineBreak[564, 565]
        Text[565, 567] chars:[565, 567, "1."]
  Paragraph[569, 580] isTrailingBlankLine
    Text[569, 579] chars:[569, 579, "list break"]
  OrderedList[581, 670] isTight delimiter:'.'
    OrderedListItem[581, 654] open:[581, 583, "1."] isTight
      Paragraph[584, 654]
        Text[584, 653] chars:[584, 653, "Numbe …  item"]
    OrderedListItem[654, 670] open:[654, 656, "2."] isTight hadBlankLineAfter
      Paragraph[657, 670] isTrailingBlankLine
        Text[657, 669] chars:[657, 669, "non-o …  item"]
  Paragraph[671, 682] isTrailingBlankLine
    Text[671, 681] chars:[671, 681, "list break"]
  OrderedList[683, 777] isTight delimiter:'.'
    OrderedListItem[683, 777] open:[683, 685, "1."] isTight hadBlankLineAfter
      Paragraph[686, 777] isTrailingBlankLine
        Text[686, 772] chars:[686, 772, "Empty …  item"]
        SoftLineBreak[772, 773]
        Text[773, 775] chars:[773, 775, "2."]
  Paragraph[778, 789] isTrailingBlankLine
    Text[778, 788] chars:[778, 788, "list break"]
  OrderedList[790, 886] isTight delimiter:'.'
    OrderedListItem[790, 886] open:[790, 792, "1."] isTight hadBlankLineAfter
      Paragraph[793, 886] isTrailingBlankLine
        Text[793, 882] chars:[793, 882, "Empty …  item"]
        SoftLineBreak[882, 883]
        Text[883, 885] chars:[883, 885, "2."]
````````````````````````````````


## List Item Indent Handling

Test how list indentation is determined

```````````````````````````````` example List Item Indent Handling: 1
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
Document[0, 135]
  BulletList[0, 135] isTight
    BulletListItem[0, 135] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[10, 135] isTight
        BulletListItem[10, 19] open:[10, 11, "*"] isTight
          Paragraph[12, 19]
            Text[12, 18] chars:[12, 18, "item 2"]
        BulletListItem[21, 30] open:[21, 22, "*"] isTight
          Paragraph[23, 30]
            Text[23, 29] chars:[23, 29, "item 3"]
        BulletListItem[33, 42] open:[33, 34, "*"] isTight
          Paragraph[35, 42]
            Text[35, 41] chars:[35, 41, "item 4"]
        BulletListItem[46, 135] open:[46, 47, "*"] isTight
          Paragraph[48, 55]
            Text[48, 54] chars:[48, 54, "item 5"]
          BulletList[60, 135] isTight
            BulletListItem[60, 69] open:[60, 61, "*"] isTight
              Paragraph[62, 69]
                Text[62, 68] chars:[62, 68, "item 6"]
            BulletListItem[75, 84] open:[75, 76, "*"] isTight
              Paragraph[77, 84]
                Text[77, 83] chars:[77, 83, "item 7"]
            BulletListItem[91, 100] open:[91, 92, "*"] isTight
              Paragraph[93, 100]
                Text[93, 99] chars:[93, 99, "item 8"]
            BulletListItem[108, 135] open:[108, 109, "*"] isTight
              Paragraph[110, 117]
                Text[110, 116] chars:[110, 116, "item 9"]
              BulletList[126, 135] isTight
                BulletListItem[126, 135] open:[126, 127, "*"] isTight
                  Paragraph[128, 135]
                    Text[128, 135] chars:[128, 135, "item 10"]
````````````````````````````````


Test if list indentation is determined on marker indent or content indent. If this and above
test differ in list structure, then content indent is used. Otherwise, marker indent.

```````````````````````````````` example List Item Indent Handling: 2
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
Document[0, 145]
  BulletList[0, 145] isTight
    BulletListItem[0, 145] open:[0, 1, "*"] isTight
      Paragraph[3, 10]
        Text[3, 9] chars:[3, 9, "item 1"]
      BulletList[11, 145] isTight
        BulletListItem[11, 21] open:[11, 12, "*"] isTight
          Paragraph[14, 21]
            Text[14, 20] chars:[14, 20, "item 2"]
        BulletListItem[23, 33] open:[23, 24, "*"] isTight
          Paragraph[26, 33]
            Text[26, 32] chars:[26, 32, "item 3"]
        BulletListItem[36, 46] open:[36, 37, "*"] isTight
          Paragraph[39, 46]
            Text[39, 45] chars:[39, 45, "item 4"]
        BulletListItem[50, 145] open:[50, 51, "*"] isTight
          Paragraph[53, 60]
            Text[53, 59] chars:[53, 59, "item 5"]
          BulletList[65, 145] isTight
            BulletListItem[65, 75] open:[65, 66, "*"] isTight
              Paragraph[68, 75]
                Text[68, 74] chars:[68, 74, "item 6"]
            BulletListItem[81, 91] open:[81, 82, "*"] isTight
              Paragraph[84, 91]
                Text[84, 90] chars:[84, 90, "item 7"]
            BulletListItem[98, 108] open:[98, 99, "*"] isTight
              Paragraph[101, 108]
                Text[101, 107] chars:[101, 107, "item 8"]
            BulletListItem[116, 145] open:[116, 117, "*"] isTight
              Paragraph[119, 126]
                Text[119, 125] chars:[119, 125, "item 9"]
              BulletList[135, 145] isTight
                BulletListItem[135, 145] open:[135, 136, "*"] isTight
                  Paragraph[138, 145]
                    Text[138, 145] chars:[138, 145, "item 10"]
````````````````````````````````


Test to see if having a blank line in list item makes a difference on indent column calcualtion.
If this list structure is the same as the one without blank lines, then had blank line status
does not affect indentation level.

```````````````````````````````` example List Item Indent Handling: 3
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
Document[0, 180]
  BulletList[0, 180] isTight
    BulletListItem[0, 180] open:[0, 1, "*"] isLoose hadBlankLineAfter
      Paragraph[2, 9] isTrailingBlankLine
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[11, 180] isTight
        BulletListItem[11, 20] open:[11, 12, "*"] isLoose hadBlankLineAfter
          Paragraph[13, 20] isTrailingBlankLine
            Text[13, 19] chars:[13, 19, "item 2"]
        BulletListItem[24, 33] open:[24, 25, "*"] isLoose hadBlankLineAfter
          Paragraph[26, 33] isTrailingBlankLine
            Text[26, 32] chars:[26, 32, "item 3"]
        BulletListItem[39, 48] open:[39, 40, "*"] isLoose hadBlankLineAfter
          Paragraph[41, 48] isTrailingBlankLine
            Text[41, 47] chars:[41, 47, "item 4"]
        BulletListItem[56, 180] open:[56, 57, "*"] isLoose hadBlankLineAfter
          Paragraph[58, 65] isTrailingBlankLine
            Text[58, 64] chars:[58, 64, "item 5"]
          BulletList[75, 180] isTight
            BulletListItem[75, 84] open:[75, 76, "*"] isLoose hadBlankLineAfter
              Paragraph[77, 84] isTrailingBlankLine
                Text[77, 83] chars:[77, 83, "item 6"]
            BulletListItem[96, 105] open:[96, 97, "*"] isLoose hadBlankLineAfter
              Paragraph[98, 105] isTrailingBlankLine
                Text[98, 104] chars:[98, 104, "item 7"]
            BulletListItem[119, 128] open:[119, 120, "*"] isLoose hadBlankLineAfter
              Paragraph[121, 128] isTrailingBlankLine
                Text[121, 127] chars:[121, 127, "item 8"]
            BulletListItem[144, 180] open:[144, 145, "*"] isLoose hadBlankLineAfter
              Paragraph[146, 153] isTrailingBlankLine
                Text[146, 152] chars:[146, 152, "item 9"]
              BulletList[171, 180] isTight
                BulletListItem[171, 180] open:[171, 172, "*"] isTight
                  Paragraph[173, 180]
                    Text[173, 180] chars:[173, 180, "item 10"]
````````````````````````````````


Test to see if first item indent affect list indentation processing, if structure differs from
same list but without leading first item space then yes.

```````````````````````````````` example List Item Indent Handling: 4
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
Document[0, 145]
  BulletList[1, 145] isTight
    BulletListItem[1, 145] open:[1, 2, "*"] isTight
      Paragraph[3, 10]
        Text[3, 9] chars:[3, 9, "item 1"]
      BulletList[12, 145] isTight
        BulletListItem[12, 21] open:[12, 13, "*"] isTight
          Paragraph[14, 21]
            Text[14, 20] chars:[14, 20, "item 2"]
        BulletListItem[24, 33] open:[24, 25, "*"] isTight
          Paragraph[26, 33]
            Text[26, 32] chars:[26, 32, "item 3"]
        BulletListItem[37, 145] open:[37, 38, "*"] isTight
          Paragraph[39, 46]
            Text[39, 45] chars:[39, 45, "item 4"]
          BulletList[51, 145] isTight
            BulletListItem[51, 60] open:[51, 52, "*"] isTight
              Paragraph[53, 60]
                Text[53, 59] chars:[53, 59, "item 5"]
            BulletListItem[66, 75] open:[66, 67, "*"] isTight
              Paragraph[68, 75]
                Text[68, 74] chars:[68, 74, "item 6"]
            BulletListItem[82, 91] open:[82, 83, "*"] isTight
              Paragraph[84, 91]
                Text[84, 90] chars:[84, 90, "item 7"]
            BulletListItem[99, 145] open:[99, 100, "*"] isTight
              Paragraph[101, 108]
                Text[101, 107] chars:[101, 107, "item 8"]
              BulletList[117, 145] isTight
                BulletListItem[117, 126] open:[117, 118, "*"] isTight
                  Paragraph[119, 126]
                    Text[119, 125] chars:[119, 125, "item 9"]
                BulletListItem[136, 145] open:[136, 137, "*"] isTight
                  Paragraph[138, 145]
                    Text[138, 145] chars:[138, 145, "item 10"]
````````````````````````````````


Test to see if absolute column position or relative from first parent list is used for
indentation. If the block quoted list structure is the same as non-block quoted structure then
indent is relative column based.

```````````````````````````````` example List Item Indent Handling: 5
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
Document[0, 185]
  BlockQuote[0, 185] marker:[0, 1, ">"]
    BlockQuote[2, 185] marker:[2, 3, ">"]
      BulletList[5, 185] isTight
        BulletListItem[5, 185] open:[5, 6, "*"] isTight
          Paragraph[7, 14]
            Text[7, 13] chars:[7, 13, "item 1"]
          BulletList[20, 185] isTight
            BulletListItem[20, 29] open:[20, 21, "*"] isTight
              Paragraph[22, 29]
                Text[22, 28] chars:[22, 28, "item 2"]
            BulletListItem[36, 45] open:[36, 37, "*"] isTight
              Paragraph[38, 45]
                Text[38, 44] chars:[38, 44, "item 3"]
            BulletListItem[53, 185] open:[53, 54, "*"] isTight
              Paragraph[55, 62]
                Text[55, 61] chars:[55, 61, "item 4"]
              BulletList[71, 185] isTight
                BulletListItem[71, 80] open:[71, 72, "*"] isTight
                  Paragraph[73, 80]
                    Text[73, 79] chars:[73, 79, "item 5"]
                BulletListItem[90, 99] open:[90, 91, "*"] isTight
                  Paragraph[92, 99]
                    Text[92, 98] chars:[92, 98, "item 6"]
                BulletListItem[110, 119] open:[110, 111, "*"] isTight
                  Paragraph[112, 119]
                    Text[112, 118] chars:[112, 118, "item 7"]
                BulletListItem[131, 185] open:[131, 132, "*"] isTight
                  Paragraph[133, 140]
                    Text[133, 139] chars:[133, 139, "item 8"]
                  BulletList[153, 185] isTight
                    BulletListItem[153, 162] open:[153, 154, "*"] isTight
                      Paragraph[155, 162]
                        Text[155, 161] chars:[155, 161, "item 9"]
                    BulletListItem[176, 185] open:[176, 177, "*"] isTight
                      Paragraph[178, 185]
                        Text[178, 185] chars:[178, 185, "item 10"]
````````````````````````````````


Test to confirm that it is first list indent processing, not first child of list that differs
from sub-lists.

```````````````````````````````` example List Item Indent Handling: 6
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
Document[0, 59]
  BulletList[0, 59] isTight
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
    BulletListItem[30, 59] open:[30, 31, "*"] isTight
      Paragraph[32, 39]
        Text[32, 38] chars:[32, 38, "item 3"]
      BulletList[40, 59] isTight
        BulletListItem[40, 49] open:[40, 41, "*"] isTight
          Paragraph[42, 49]
            Text[42, 48] chars:[42, 48, "item 4"]
        BulletListItem[51, 59] open:[51, 52, "*"] isTight
          Paragraph[53, 59]
            Text[53, 59] chars:[53, 59, "item 5"]
````````````````````````````````


Test where lazy continuation affects list item processing.

```````````````````````````````` example List Item Indent Handling: 7
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
Document[0, 50]
  BulletList[0, 50] isTight
    BulletListItem[0, 25] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[16, 25] isTight
        BulletListItem[16, 25] open:[16, 17, "*"] isTight
          Paragraph[18, 25]
            Text[18, 24] chars:[18, 24, "item 2"]
    BulletListItem[25, 50] open:[25, 26, "*"] isTight
      Paragraph[27, 50]
        Text[27, 33] chars:[27, 33, "item 3"]
        SoftLineBreak[33, 34]
        Text[42, 50] chars:[42, 50, "* item 4"]
````````````````````````````````


Test if it is `first first list` indent processing, or first direct parent list processing that
affects sub-list indentation. But `Markdown.pl` cannot properly process block quotes in list
items. See:
[Bugs](#bugs)

So first list it is and the HTML here is from the emulator not Markdown.pl

```````````````````````````````` example List Item Indent Handling: 8
* item 1
 * item 2
  * item 3
    > * item 4
    >  * item 5
    >   * item 6
.
<ul>
    <li>item 1
        <ul>
            <li>item 2</li>
            <li>item 3</li>
        </ul>
        <blockquote>
            <ul>
                <li>item 4
                    <ul>
                        <li>item 5</li>
                        <li>item 6</li>
                    </ul>
                </li>
            </ul>
        </blockquote>
    </li>
</ul>
.
Document[0, 77]
  BulletList[0, 77] isTight
    BulletListItem[0, 77] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[10, 30] isTight
        BulletListItem[10, 19] open:[10, 11, "*"] isTight
          Paragraph[12, 19]
            Text[12, 18] chars:[12, 18, "item 2"]
        BulletListItem[21, 30] open:[21, 22, "*"] isTight
          Paragraph[23, 30]
            Text[23, 29] chars:[23, 29, "item 3"]
      BlockQuote[34, 77] marker:[34, 35, ">"]
        BulletList[36, 77] isTight
          BulletListItem[36, 77] open:[36, 37, "*"] isTight
            Paragraph[38, 45]
              Text[38, 44] chars:[38, 44, "item 4"]
            BulletList[52, 77] isTight
              BulletListItem[52, 61] open:[52, 53, "*"] isTight
                Paragraph[54, 61]
                  Text[54, 60] chars:[54, 60, "item 5"]
              BulletListItem[69, 77] open:[69, 70, "*"] isTight
                Paragraph[71, 77]
                  Text[71, 77] chars:[71, 77, "item 6"]
````````````````````````````````


```````````````````````````````` example List Item Indent Handling: 9
* item 1
 * item 2
  * item 3
     > * item 4
     >  * item 5
     >   * item 6
.
<ul>
    <li>item 1
        <ul>
            <li>item 2</li>
            <li>item 3
                <blockquote>
                    <ul>
                        <li>item 4
                            <ul>
                                <li>item 5</li>
                                <li>item 6</li>
                            </ul>
                        </li>
                    </ul>
                </blockquote>
            </li>
        </ul>
    </li>
</ul>
.
Document[0, 80]
  BulletList[0, 80] isTight
    BulletListItem[0, 80] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[10, 80] isTight
        BulletListItem[10, 19] open:[10, 11, "*"] isTight
          Paragraph[12, 19]
            Text[12, 18] chars:[12, 18, "item 2"]
        BulletListItem[21, 80] open:[21, 22, "*"] isTight
          Paragraph[23, 30]
            Text[23, 29] chars:[23, 29, "item 3"]
          BlockQuote[35, 80] marker:[35, 36, ">"]
            BulletList[37, 80] isTight
              BulletListItem[37, 80] open:[37, 38, "*"] isTight
                Paragraph[39, 46]
                  Text[39, 45] chars:[39, 45, "item 4"]
                BulletList[54, 80] isTight
                  BulletListItem[54, 63] open:[54, 55, "*"] isTight
                    Paragraph[56, 63]
                      Text[56, 62] chars:[56, 62, "item 5"]
                  BulletListItem[72, 80] open:[72, 73, "*"] isTight
                    Paragraph[74, 80]
                      Text[74, 80] chars:[74, 80, "item 6"]
````````````````````````````````


Block quote needs blank line before. This is the output `Markdown.pl` is trying to output but
failing miserably by outputing invalid HTML, however `Markdown.pl 1.0.2b8` can handle it.

```````````````````````````````` example List Item Indent Handling: 10
* item 1
 * item 2
  * item 3
  
    > * item 4
    >  * item 5
    >   * item 6
.
<ul>
    <li>
        <p>item 1</p>
        <ul>
            <li>item 2</li>
            <li>item 3</li>
        </ul>
        <blockquote>
            <ul>
                <li>item 4
                    <ul>
                        <li>item 5</li>
                        <li>item 6</li>
                    </ul>
                </li>
            </ul>
        </blockquote>
    </li>
</ul>
.
Document[0, 80]
  BulletList[0, 80] isTight
    BulletListItem[0, 80] open:[0, 1, "*"] isLoose hadBlankLine
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[10, 30] isTight
        BulletListItem[10, 19] open:[10, 11, "*"] isTight
          Paragraph[12, 19]
            Text[12, 18] chars:[12, 18, "item 2"]
        BulletListItem[21, 30] open:[21, 22, "*"] isTight hadBlankLineAfter
          Paragraph[23, 30] isTrailingBlankLine
            Text[23, 29] chars:[23, 29, "item 3"]
      BlockQuote[37, 80] marker:[37, 38, ">"]
        BulletList[39, 80] isTight
          BulletListItem[39, 80] open:[39, 40, "*"] isTight
            Paragraph[41, 48]
              Text[41, 47] chars:[41, 47, "item 4"]
            BulletList[55, 80] isTight
              BulletListItem[55, 64] open:[55, 56, "*"] isTight
                Paragraph[57, 64]
                  Text[57, 63] chars:[57, 63, "item 5"]
              BulletListItem[72, 80] open:[72, 73, "*"] isTight
                Paragraph[74, 80]
                  Text[74, 80] chars:[74, 80, "item 6"]
````````````````````````````````


Actual `Markdown.pl` output, handles this case properly

```````````````````````````````` example List Item Indent Handling: 11
* item 1
 * item 2
  * item 3
  
     > * item 4
     >  * item 5
     >   * item 6
.
<ul>
    <li>
        <p>item 1</p>
        <ul>
            <li>item 2</li>
            <li>
                <p>item 3</p>
                <blockquote>
                    <ul>
                        <li>item 4
                            <ul>
                                <li>item 5</li>
                                <li>item 6</li>
                            </ul>
                        </li>
                    </ul>
                </blockquote>
            </li>
        </ul>
    </li>
</ul>
.
Document[0, 83]
  BulletList[0, 83] isTight
    BulletListItem[0, 83] open:[0, 1, "*"] isLoose hadBlankLine
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[10, 83] isTight
        BulletListItem[10, 19] open:[10, 11, "*"] isTight
          Paragraph[12, 19]
            Text[12, 18] chars:[12, 18, "item 2"]
        BulletListItem[21, 83] open:[21, 22, "*"] isLoose hadBlankLineAfter
          Paragraph[23, 30] isTrailingBlankLine
            Text[23, 29] chars:[23, 29, "item 3"]
          BlockQuote[38, 83] marker:[38, 39, ">"]
            BulletList[40, 83] isTight
              BulletListItem[40, 83] open:[40, 41, "*"] isTight
                Paragraph[42, 49]
                  Text[42, 48] chars:[42, 48, "item 4"]
                BulletList[57, 83] isTight
                  BulletListItem[57, 66] open:[57, 58, "*"] isTight
                    Paragraph[59, 66]
                      Text[59, 65] chars:[59, 65, "item 5"]
                  BulletListItem[75, 83] open:[75, 76, "*"] isTight
                    Paragraph[77, 83]
                      Text[77, 83] chars:[77, 83, "item 6"]
````````````````````````````````


Test shows where the boundary switch to indented code occurs. First paragraph is a paragraph,
the second is indented code.

```````````````````````````````` example List Item Indent Handling: 12
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
      Paragraph[4, 9] isTrailingBlankLine
        Text[4, 8] chars:[4, 8, "test"]
      Paragraph[17, 33] isTrailingBlankLine
        Text[17, 32] chars:[17, 32, "item  …  para"]
      IndentedCodeBlock[42, 56]
    BulletListItem[67, 159] open:[67, 68, "-"] isLoose hadBlankLine
      Paragraph[71, 76]
        Text[71, 75] chars:[71, 75, "test"]
      BulletList[80, 159] isTight
        BulletListItem[80, 159] open:[80, 81, "-"] isLoose hadBlankLineAfter
          Paragraph[82, 91] isTrailingBlankLine
            Text[82, 90] chars:[82, 90, "sub item"]
          Paragraph[103, 123] isTrailingBlankLine
            Text[103, 122] chars:[103, 122, "sub i …  para"]
          IndentedCodeBlock[136, 159]
  ThematicBreak[170, 173]
  OrderedList[175, 330] isTight delimiter:'.'
    OrderedListItem[175, 236] open:[175, 177, "1."] isLoose hadBlankLineAfter
      Paragraph[179, 184] isTrailingBlankLine
        Text[179, 183] chars:[179, 183, "test"]
      Paragraph[192, 208] isTrailingBlankLine
        Text[192, 207] chars:[192, 207, "item  …  para"]
      IndentedCodeBlock[217, 236]
    OrderedListItem[237, 330] open:[237, 239, "1."] isLoose hadBlankLine
      Paragraph[241, 246]
        Text[241, 245] chars:[241, 245, "test"]
      OrderedList[250, 330] isTight delimiter:'.'
        OrderedListItem[250, 330] open:[250, 252, "1."] isLoose hadBlankLineAfter
          Paragraph[253, 262] isTrailingBlankLine
            Text[253, 261] chars:[253, 261, "sub item"]
          Paragraph[274, 294] isTrailingBlankLine
            Text[274, 293] chars:[274, 293, "sub i …  para"]
          IndentedCodeBlock[307, 330]
````````````````````````````````


More extensive test to show where the boundary switch to indented code occurs. Sub-items first
paragraph is a paragraph, the second is indented code

```````````````````````````````` example List Item Indent Handling: 13
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
Document[0, 981]
  BulletList[0, 981] isTight
    BulletListItem[0, 981] open:[0, 1, "*"] isLoose hadBlankLineAfter
      Paragraph[2, 9] isTrailingBlankLine
        Text[2, 8] chars:[2, 8, "item 1"]
      Paragraph[16, 28] isTrailingBlankLine
        Text[16, 27] chars:[16, 27, "item  … ara 1"]
      Paragraph[34, 46] isTrailingBlankLine
        Text[34, 45] chars:[34, 45, "item  … ara 2"]
      Paragraph[54, 66] isTrailingBlankLine
        Text[54, 65] chars:[54, 65, "item  … ara 3"]
      Paragraph[76, 88] isTrailingBlankLine
        Text[76, 87] chars:[76, 87, "item  … ara 4"]
      Paragraph[100, 112] isTrailingBlankLine
        Text[100, 111] chars:[100, 111, "item  … ara 5"]
      Paragraph[126, 138] isTrailingBlankLine
        Text[126, 137] chars:[126, 137, "item  … ara 6"]
      IndentedCodeBlock[154, 166]
      BulletList[176, 185] isTight
        BulletListItem[176, 185] open:[176, 177, "*"] isTight hadBlankLineAfter
          Paragraph[178, 185] isTrailingBlankLine
            Text[178, 184] chars:[178, 184, "item 2"]
      Paragraph[193, 205] isTrailingBlankLine
        Text[193, 204] chars:[193, 204, "item  … ara 1"]
      Paragraph[213, 225] isTrailingBlankLine
        Text[213, 224] chars:[213, 224, "item  … ara 2"]
      Paragraph[235, 247] isTrailingBlankLine
        Text[235, 246] chars:[235, 246, "item  … ara 3"]
      Paragraph[259, 271] isTrailingBlankLine
        Text[259, 270] chars:[259, 270, "item  … ara 4"]
      Paragraph[285, 297] isTrailingBlankLine
        Text[285, 296] chars:[285, 296, "item  … ara 5"]
      IndentedCodeBlock[313, 355]
      BulletList[366, 375] isTight
        BulletListItem[366, 375] open:[366, 367, "*"] isTight hadBlankLineAfter
          Paragraph[368, 375] isTrailingBlankLine
            Text[368, 374] chars:[368, 374, "item 3"]
      Paragraph[384, 396] isTrailingBlankLine
        Text[384, 395] chars:[384, 395, "item  … ara 1"]
      Paragraph[406, 418] isTrailingBlankLine
        Text[406, 417] chars:[406, 417, "item  … ara 2"]
      Paragraph[430, 442] isTrailingBlankLine
        Text[430, 441] chars:[430, 441, "item  … ara 3"]
      Paragraph[456, 468] isTrailingBlankLine
        Text[456, 467] chars:[456, 467, "item  … ara 4"]
      IndentedCodeBlock[484, 558]
      BulletList[570, 981] isTight
        BulletListItem[570, 775] open:[570, 571, "*"] isLoose hadBlankLineAfter
          Paragraph[572, 579] isTrailingBlankLine
            Text[572, 578] chars:[572, 578, "item 4"]
          Paragraph[589, 601] isTrailingBlankLine
            Text[589, 600] chars:[589, 600, "item  … ara 1"]
          Paragraph[613, 625] isTrailingBlankLine
            Text[613, 624] chars:[613, 624, "item  … ara 2"]
          Paragraph[639, 651] isTrailingBlankLine
            Text[639, 650] chars:[639, 650, "item  … ara 3"]
          Paragraph[667, 679] isTrailingBlankLine
            Text[667, 678] chars:[667, 678, "item  … ara 4"]
          Paragraph[697, 709] isTrailingBlankLine
            Text[697, 708] chars:[697, 708, "item  … ara 5"]
          Paragraph[729, 741] isTrailingBlankLine
            Text[729, 740] chars:[729, 740, "item  … ara 6"]
          Paragraph[763, 775] isTrailingBlankLine
            Text[763, 774] chars:[763, 774, "item  … ara 7"]
        BulletListItem[788, 981] open:[788, 789, "*"] isLoose hadBlankLineAfter
          Paragraph[790, 797] isTrailingBlankLine
            Text[790, 796] chars:[790, 796, "item 5"]
          Paragraph[808, 820] isTrailingBlankLine
            Text[808, 819] chars:[808, 819, "item  … ara 1"]
          Paragraph[830, 842] isTrailingBlankLine
            Text[830, 841] chars:[830, 841, "item  … ara 2"]
          Paragraph[854, 866] isTrailingBlankLine
            Text[854, 865] chars:[854, 865, "item  … ara 3"]
          Paragraph[880, 892] isTrailingBlankLine
            Text[880, 891] chars:[880, 891, "item  … ara 4"]
          Paragraph[908, 920] isTrailingBlankLine
            Text[908, 919] chars:[908, 919, "item  … ara 5"]
          Paragraph[938, 950] isTrailingBlankLine
            Text[938, 949] chars:[938, 949, "item  … ara 6"]
          IndentedCodeBlock[970, 981]
````````````````````````````````


Test for how items with indent > first list item's indent but < previous item's content indent
are handled. Mainly, if they are handled in a weird way of treating the item as a sub-item of
the previous list item. There was one that did it that way, GitHub comments if I can remember
right, but now they switched to commonmark list handling with mods. Guess it is now GFC--GitHub
Flavoured Commonmark.

```````````````````````````````` example List Item Indent Handling: 14
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
Document[0, 32]
  BulletList[0, 32] isTight
    BulletListItem[0, 32] open:[0, 1, "*"] isTight
      Paragraph[3, 10]
        Text[3, 9] chars:[3, 9, "item 1"]
      BulletList[13, 32] isTight
        BulletListItem[13, 22] open:[13, 14, "*"] isTight
          Paragraph[15, 22]
            Text[15, 21] chars:[15, 21, "item 2"]
        BulletListItem[24, 32] open:[24, 25, "*"] isTight
          Paragraph[26, 32]
            Text[26, 32] chars:[26, 32, "item 3"]
````````````````````````````````


Test how headings in list items are handled, leading space allowed or not

```````````````````````````````` example List Item Indent Handling: 15
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
      Paragraph[2, 9] isTrailingBlankLine
        Text[2, 8] chars:[2, 8, "item 1"]
      Heading[12, 23] textOpen:[12, 13, "#"] text:[14, 23, "Heading 1"]
        Text[14, 23] chars:[14, 23, "Heading 1"]
      Heading[28, 40] textOpen:[28, 30, "##"] text:[31, 40, "Heading 2"]
        Text[31, 40] chars:[31, 40, "Heading 2"]
      Heading[46, 59] textOpen:[46, 49, "###"] text:[50, 59, "Heading 3"]
        Text[50, 59] chars:[50, 59, "Heading 3"]
      Paragraph[66, 81] isTrailingBlankLine
        Text[66, 80] chars:[66, 80, "####  … ing 4"]
      Paragraph[88, 104] isTrailingBlankLine
        Text[88, 103] chars:[88, 103, "##### … ing 5"]
      Paragraph[112, 129] isTrailingBlankLine
        Text[112, 128] chars:[112, 128, "##### … ing 6"]
      BulletList[132, 141] isTight
        BulletListItem[132, 141] open:[132, 133, "*"] isTight hadBlankLineAfter
          Paragraph[134, 141] isTrailingBlankLine
            Text[134, 140] chars:[134, 140, "item 2"]
      Heading[146, 157] textOpen:[146, 147, "#"] text:[148, 157, "Heading 1"]
        Text[148, 157] chars:[148, 157, "Heading 1"]
      Paragraph[168, 181] isTrailingBlankLine
        Text[168, 180] chars:[168, 180, "## He … ing 2"]
      Paragraph[192, 206] isTrailingBlankLine
        Text[192, 205] chars:[192, 205, "### H … ing 3"]
      Paragraph[218, 233] isTrailingBlankLine
        Text[218, 232] chars:[218, 232, "####  … ing 3"]
      IndentedCodeBlock[246, 295]
````````````````````````````````


```````````````````````````````` example List Item Indent Handling: 16
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
    BulletListItem[0, 79] open:[0, 1, "-"] isLoose hadBlankLine
      Paragraph[4, 9]
        Text[4, 8] chars:[4, 8, "test"]
      BulletList[13, 79] isTight
        BulletListItem[13, 79] open:[13, 14, "-"] isLoose hadBlankLineAfter
          Paragraph[15, 24] isTrailingBlankLine
            Text[15, 23] chars:[15, 23, "sub item"]
          Paragraph[34, 54] isTrailingBlankLine
            Text[34, 53] chars:[34, 53, "sub i …  para"]
          Paragraph[65, 79] isTrailingBlankLine
            Text[65, 78] chars:[65, 78, "inden …  code"]
  ThematicBreak[90, 93]
  OrderedList[95, 177] isTight delimiter:'.'
    OrderedListItem[95, 177] open:[95, 97, "1."] isLoose hadBlankLine
      Paragraph[99, 104]
        Text[99, 103] chars:[99, 103, "test"]
      OrderedList[108, 177] isTight delimiter:'.'
        OrderedListItem[108, 177] open:[108, 110, "1."] isLoose hadBlankLineAfter
          Paragraph[111, 120] isTrailingBlankLine
            Text[111, 119] chars:[111, 119, "sub item"]
          Paragraph[131, 151] isTrailingBlankLine
            Text[131, 150] chars:[131, 150, "sub i …  para"]
          Paragraph[163, 177] isTrailingBlankLine
            Text[163, 176] chars:[163, 176, "inden …  code"]
````````````````````````````````


## Bugs

Block quotes in list items mess up parsing and HTML generation

```````````````````````````````` example(Bugs: 1) options(IGNORE)
* item 1
 * item 2
  * item 3
    > * item 5
    >  * item 5
    >   * item 6
.
<ul>
    <li>item 1
        <ul>
            <li>item 2</li>
            <li>item 3
                <blockquote>
                    <ul>
                        <li>item 4
                            <ul>
                                <li>item 5</li>
                                <li>item 6</li>
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


The output of `Markdown.pl` is invalid HTML.

```````````````````````````````` example(Bugs: 2) options(IGNORE)
* item 1
 * item 2
  * item 3
  
    > * item 4
    >  * item 5
    >   * item 6
.
<ul>
    <li>
        <p>item 1</p>
        <ul>
            <li>item 2</li>
            <li>item 3</li>
        </ul>
        <p>
            <blockquote>
                <ul>
                    <li>item 4</p>
        <ul>
            <li>item 5</li>
            <li>item 6</li>
        </ul>
        </li>
        </ul>
        <p>
            </blockquote>
    </li>
</ul>
</p>
.
````````````````````````````````


Here the block quote messes up parsing and HTML generation

```````````````````````````````` example(Bugs: 3) options(IGNORE)
* item 1
 * item 2
  * item 3
    > > * item 4
    > >  * item 5
    > >   * item 6
.
<ul>
    <li>item 1
        <ul>
            <li>item 2</li>
            <li>item 3
                <blockquote>
                    <blockquote>
                        <ul>
                            <li>item 4
                                <ul>
                                    <li>item 5</li>
                                    <li>item 6</li>
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

```````````````````````````````` example(Bugs: 4) options(IGNORE)
* item 1
 * item 2
  * item 3
  
    > > * item 4
    > >  * item 5
    > >   * item 6
.
<ul>
    <li>
        <p>item 1</p>
        <ul>
            <li>item 2</li>
            <li>item 3</li>
        </ul>
        <blockquote>
            <blockquote>
                <ul>
                    <li>item 4
                        <ul>
                            <li>item 5</li>
                            <li>item 6</li>
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

```````````````````````````````` example(Bugs: 5) options(IGNORE)
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


Whether block quotes can interrupt item paragraph, another bug

```````````````````````````````` example(Bugs: 6) options(IGNORE)
* item 1
  > block quoted text

<!-- list break -->

1. item 1
   > block quoted text

.
<ul>
    <li>item 1
        <blockquote>
            <p>block quoted text</li>
</ul>
<p>61c18c861b29c31c0688446a83f451b8</p>
</blockquote>
</p>
<p>
    <ol>
        <li>item 1</p>
<blockquote>
    <p>block quoted text</li>
        </ol>
    </p>
</blockquote>
.
````````````````````````````````


Whether block quotes can interrupt item paragraph, another bug

```````````````````````````````` example(Bugs: 7) options(IGNORE)
* item 1
  > block quoted text

---

1. item 1
   > block quoted text

.
<ul>
    <li>item 1
        <blockquote>
            <p>block quoted text</li>
</ul>
<p>
    <hr />
</p>
</blockquote>
</p>
<p>
    <ol>
        <li>item 1</p>
<blockquote>
    <p>block quoted text</li>
        </ol>
    </p>
</blockquote>
.
````````````````````````````````


## Block quote parsing

Whether blank lines are required to start a block quote

```````````````````````````````` example Block quote parsing: 1
paragraph text
> block quoted text
.
<p>paragraph text</p>
<blockquote>
    <p>block quoted text</p>
</blockquote>
.
Document[0, 34]
  Paragraph[0, 15]
    Text[0, 14] chars:[0, 14, "parag …  text"]
  BlockQuote[15, 34] marker:[15, 16, ">"]
    Paragraph[17, 34]
      Text[17, 34] chars:[17, 34, "block …  text"]
````````````````````````````````


Whether blank lines are required to start a block quote

```````````````````````````````` example Block quote parsing: 2
paragraph text

> block quoted text
.
<p>paragraph text</p>
<blockquote>
    <p>block quoted text</p>
</blockquote>
.
Document[0, 35]
  Paragraph[0, 15] isTrailingBlankLine
    Text[0, 14] chars:[0, 14, "parag …  text"]
  BlockQuote[16, 35] marker:[16, 17, ">"]
    Paragraph[18, 35]
      Text[18, 35] chars:[18, 35, "block …  text"]
````````````````````````````````


Whether leading spaces are allowed before block quote marker

```````````````````````````````` example Block quote parsing: 3
 > block quote paragraph text
.
<blockquote>
    <p>block quote paragraph text</p>
</blockquote>
.
Document[0, 29]
  BlockQuote[1, 29] marker:[1, 2, ">"]
    Paragraph[3, 29]
      Text[3, 29] chars:[3, 29, "block …  text"]
````````````````````````````````


Whether trailing spaces are required after block quote marker

```````````````````````````````` example Block quote parsing: 4
>block quote paragraph text
.
<blockquote>
    <p>block quote paragraph text</p>
</blockquote>
.
Document[0, 27]
  BlockQuote[0, 27] marker:[0, 1, ">"]
    Paragraph[1, 27]
      Text[1, 27] chars:[1, 27, "block …  text"]
````````````````````````````````


Whether blank lines are ignored and treated as if prefixed with block quote

```````````````````````````````` example Block quote parsing: 5
> block quoted text

> more block quoted text
.
<blockquote>
    <p>block quoted text</p>
    <p>more block quoted text</p>
</blockquote>
.
Document[0, 45]
  BlockQuote[0, 45] marker:[0, 1, ">"]
    Paragraph[2, 20] isTrailingBlankLine
      Text[2, 19] chars:[2, 19, "block …  text"]
    Paragraph[23, 45]
      Text[23, 45] chars:[23, 45, "more  …  text"]
````````````````````````````````


Whether block quotes continue to a blank line

```````````````````````````````` example Block quote parsing: 6
> block quoted text
lazy continuation

another paragraph
.
<blockquote>
    <p>block quoted text lazy continuation</p>
</blockquote>
<p>another paragraph</p>
.
Document[0, 56]
  BlockQuote[0, 38] marker:[0, 1, ">"]
    Paragraph[2, 38] isTrailingBlankLine
      Text[2, 19] chars:[2, 19, "block …  text"]
      SoftLineBreak[19, 20]
      Text[20, 37] chars:[20, 37, "lazy  … ation"]
  Paragraph[39, 56]
    Text[39, 56] chars:[39, 56, "anoth … graph"]
````````````````````````````````


Whether block quotes can interrupt item paragraph, it attempts to do it so we clean it up

```````````````````````````````` example Block quote parsing: 7
* item 1
  > block quoted text

<!-- list break -->

1. item 1
   > block quoted text

.
<ul>
    <li>item 1
        <blockquote>
            <p>block quoted text</p>
        </blockquote>
    </li>
</ul>
<!-- list break -->
<ol>
    <li>item 1
        <blockquote>
            <p>block quoted text</p>
        </blockquote>
    </li>
</ol>
.
Document[0, 87]
  BulletList[0, 31] isTight
    BulletListItem[0, 31] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BlockQuote[11, 31] marker:[11, 12, ">"]
        Paragraph[13, 31] isTrailingBlankLine
          Text[13, 30] chars:[13, 30, "block …  text"]
  HtmlCommentBlock[32, 52]
  OrderedList[53, 86] isTight delimiter:'.'
    OrderedListItem[53, 86] open:[53, 55, "1."] isTight
      Paragraph[56, 63]
        Text[56, 62] chars:[56, 62, "item 1"]
      BlockQuote[66, 86] marker:[66, 67, ">"]
        Paragraph[68, 86] isTrailingBlankLine
          Text[68, 85] chars:[68, 85, "block …  text"]
````````````````````````````````


Whether block quotes can interrupt item paragraph

```````````````````````````````` example Block quote parsing: 8
* item 1

  > block quoted text

<!-- list break -->

1. item 1

   > block quoted text

.
<ul>
    <li>
        <p>item 1</p>
        <blockquote>
            <p>block quoted text</p>
        </blockquote>
    </li>
</ul>
<!-- list break -->
<ol>
    <li>
        <p>item 1</p>
        <blockquote>
            <p>block quoted text</p>
        </blockquote>
    </li>
</ol>
.
Document[0, 89]
  BulletList[0, 32] isTight
    BulletListItem[0, 32] open:[0, 1, "*"] isLoose hadBlankLineAfter
      Paragraph[2, 9] isTrailingBlankLine
        Text[2, 8] chars:[2, 8, "item 1"]
      BlockQuote[12, 32] marker:[12, 13, ">"]
        Paragraph[14, 32] isTrailingBlankLine
          Text[14, 31] chars:[14, 31, "block …  text"]
  HtmlCommentBlock[33, 53]
  OrderedList[54, 88] isTight delimiter:'.'
    OrderedListItem[54, 88] open:[54, 56, "1."] isLoose hadBlankLineAfter
      Paragraph[57, 64] isTrailingBlankLine
        Text[57, 63] chars:[57, 63, "item 1"]
      BlockQuote[68, 88] marker:[68, 69, ">"]
        Paragraph[70, 88] isTrailingBlankLine
          Text[70, 87] chars:[70, 87, "block …  text"]
````````````````````````````````


Whether block quotes with leading space can interrupt item paragraphs, it tries

```````````````````````````````` example Block quote parsing: 9
* item 1
   > block quoted text

<!-- list break -->

1. item 1
    > block quoted text

.
<ul>
    <li>item 1
        <blockquote>
            <p>block quoted text</p>
        </blockquote>
    </li>
</ul>
<!-- list break -->
<ol>
    <li>item 1
        <blockquote>
            <p>block quoted text</p>
        </blockquote>
    </li>
</ol>
.
Document[0, 89]
  BulletList[0, 32] isTight
    BulletListItem[0, 32] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BlockQuote[12, 32] marker:[12, 13, ">"]
        Paragraph[14, 32] isTrailingBlankLine
          Text[14, 31] chars:[14, 31, "block …  text"]
  HtmlCommentBlock[33, 53]
  OrderedList[54, 88] isTight delimiter:'.'
    OrderedListItem[54, 88] open:[54, 56, "1."] isTight
      Paragraph[57, 64]
        Text[57, 63] chars:[57, 63, "item 1"]
      BlockQuote[68, 88] marker:[68, 69, ">"]
        Paragraph[70, 88] isTrailingBlankLine
          Text[70, 87] chars:[70, 87, "block …  text"]
````````````````````````````````


Whether block quotes with leading space can interrupt item paragraph

```````````````````````````````` example Block quote parsing: 10
* item 1

   > block quoted text

<!-- list break -->

1. item 1

    > block quoted text

.
<ul>
    <li>
        <p>item 1</p>
        <blockquote>
            <p>block quoted text</p>
        </blockquote>
    </li>
</ul>
<!-- list break -->
<ol>
    <li>
        <p>item 1</p>
        <blockquote>
            <p>block quoted text</p>
        </blockquote>
    </li>
</ol>
.
Document[0, 91]
  BulletList[0, 33] isTight
    BulletListItem[0, 33] open:[0, 1, "*"] isLoose hadBlankLineAfter
      Paragraph[2, 9] isTrailingBlankLine
        Text[2, 8] chars:[2, 8, "item 1"]
      BlockQuote[13, 33] marker:[13, 14, ">"]
        Paragraph[15, 33] isTrailingBlankLine
          Text[15, 32] chars:[15, 32, "block …  text"]
  HtmlCommentBlock[34, 54]
  OrderedList[55, 90] isTight delimiter:'.'
    OrderedListItem[55, 90] open:[55, 57, "1."] isLoose hadBlankLineAfter
      Paragraph[58, 65] isTrailingBlankLine
        Text[58, 64] chars:[58, 64, "item 1"]
      BlockQuote[70, 90] marker:[70, 71, ">"]
        Paragraph[72, 90] isTrailingBlankLine
          Text[72, 89] chars:[72, 89, "block …  text"]
````````````````````````````````


Whether block quotes without trailing space can interrupt item paragraphs, it tries

```````````````````````````````` example Block quote parsing: 11
* item 1
  >block quoted text

<!-- list break -->

1. item 1
   >block quoted text

.
<ul>
    <li>item 1
        <blockquote>
            <p>block quoted text</p>
        </blockquote>
    </li>
</ul>
<!-- list break -->
<ol>
    <li>item 1
        <blockquote>
            <p>block quoted text</p>
        </blockquote>
    </li>
</ol>
.
Document[0, 85]
  BulletList[0, 30] isTight
    BulletListItem[0, 30] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BlockQuote[11, 30] marker:[11, 12, ">"]
        Paragraph[12, 30] isTrailingBlankLine
          Text[12, 29] chars:[12, 29, "block …  text"]
  HtmlCommentBlock[31, 51]
  OrderedList[52, 84] isTight delimiter:'.'
    OrderedListItem[52, 84] open:[52, 54, "1."] isTight
      Paragraph[55, 62]
        Text[55, 61] chars:[55, 61, "item 1"]
      BlockQuote[65, 84] marker:[65, 66, ">"]
        Paragraph[66, 84] isTrailingBlankLine
          Text[66, 83] chars:[66, 83, "block …  text"]
````````````````````````````````


Whether block quotes without trailing space can interrupt item paragraph

```````````````````````````````` example Block quote parsing: 12
* item 1

   >block quoted text

<!-- list break -->

1. item 1

   >block quoted text

.
<ul>
    <li>
        <p>item 1</p>
        <blockquote>
            <p>block quoted text</p>
        </blockquote>
    </li>
</ul>
<!-- list break -->
<ol>
    <li>
        <p>item 1</p>
        <blockquote>
            <p>block quoted text</p>
        </blockquote>
    </li>
</ol>
.
Document[0, 88]
  BulletList[0, 32] isTight
    BulletListItem[0, 32] open:[0, 1, "*"] isLoose hadBlankLineAfter
      Paragraph[2, 9] isTrailingBlankLine
        Text[2, 8] chars:[2, 8, "item 1"]
      BlockQuote[13, 32] marker:[13, 14, ">"]
        Paragraph[14, 32] isTrailingBlankLine
          Text[14, 31] chars:[14, 31, "block …  text"]
  HtmlCommentBlock[33, 53]
  OrderedList[54, 87] isTight delimiter:'.'
    OrderedListItem[54, 87] open:[54, 56, "1."] isLoose hadBlankLineAfter
      Paragraph[57, 64] isTrailingBlankLine
        Text[57, 63] chars:[57, 63, "item 1"]
      BlockQuote[68, 87] marker:[68, 69, ">"]
        Paragraph[69, 87] isTrailingBlankLine
          Text[69, 86] chars:[69, 86, "block …  text"]
````````````````````````````````


Test to make sure content indented deeply nested lists process correctly

```````````````````````````````` example Block quote parsing: 13
- item 1
  - item 2
    - item 3
      - item 4
        - item 5
          - item 6
            - item 7
              - item 8
                - item 9

.
<ul>
    <li>item 1
        <ul>
            <li>item 2</li>
            <li>item 3
                <ul>
                    <li>item 4</li>
                    <li>item 5
                        <ul>
                            <li>item 6</li>
                            <li>item 7
                                <ul>
                                    <li>item 8</li>
                                    <li>item 9</li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul>
    </li>
</ul>
.
Document[0, 154]
  BulletList[0, 153] isTight
    BulletListItem[0, 153] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[11, 153] isTight
        BulletListItem[11, 20] open:[11, 12, "-"] isTight
          Paragraph[13, 20]
            Text[13, 19] chars:[13, 19, "item 2"]
        BulletListItem[24, 153] open:[24, 25, "-"] isTight
          Paragraph[26, 33]
            Text[26, 32] chars:[26, 32, "item 3"]
          BulletList[39, 153] isTight
            BulletListItem[39, 48] open:[39, 40, "-"] isTight
              Paragraph[41, 48]
                Text[41, 47] chars:[41, 47, "item 4"]
            BulletListItem[56, 153] open:[56, 57, "-"] isTight
              Paragraph[58, 65]
                Text[58, 64] chars:[58, 64, "item 5"]
              BulletList[75, 153] isTight
                BulletListItem[75, 84] open:[75, 76, "-"] isTight
                  Paragraph[77, 84]
                    Text[77, 83] chars:[77, 83, "item 6"]
                BulletListItem[96, 153] open:[96, 97, "-"] isTight
                  Paragraph[98, 105]
                    Text[98, 104] chars:[98, 104, "item 7"]
                  BulletList[119, 153] isTight
                    BulletListItem[119, 128] open:[119, 120, "-"] isTight
                      Paragraph[121, 128]
                        Text[121, 127] chars:[121, 127, "item 8"]
                    BulletListItem[144, 153] open:[144, 145, "-"] isTight hadBlankLineAfter
                      Paragraph[146, 153] isTrailingBlankLine
                        Text[146, 152] chars:[146, 152, "item 9"]
````````````````````````````````


```````````````````````````````` example Block quote parsing: 14
1. item 1
   1. item 2
      1. item 3
         1. item 4
            1. item 5
               1. item 6
                  1. item 7
                     1. item 8
                        1. item 9

.
<ol>
    <li>item 1
        <ol>
            <li>item 2
                <ol>
                    <li>item 3
                        <ol>
                            <li>item 4</li>
                            <li>item 5
                                <ol>
                                    <li>item 6
                                        <ol>
                                            <li>item 7
                                                <ol>
                                                    <li>item 8</li>
                                                    <li>item 9</li>
                                                </ol>
                                            </li>
                                        </ol>
                                    </li>
                                </ol>
                            </li>
                        </ol>
                    </li>
                </ol>
            </li>
        </ol>
    </li>
</ol>
.
Document[0, 199]
  OrderedList[0, 198] isTight delimiter:'.'
    OrderedListItem[0, 198] open:[0, 2, "1."] isTight
      Paragraph[3, 10]
        Text[3, 9] chars:[3, 9, "item 1"]
      OrderedList[13, 198] isTight delimiter:'.'
        OrderedListItem[13, 198] open:[13, 15, "1."] isTight
          Paragraph[16, 23]
            Text[16, 22] chars:[16, 22, "item 2"]
          OrderedList[29, 198] isTight delimiter:'.'
            OrderedListItem[29, 198] open:[29, 31, "1."] isTight
              Paragraph[32, 39]
                Text[32, 38] chars:[32, 38, "item 3"]
              OrderedList[48, 198] isTight delimiter:'.'
                OrderedListItem[48, 58] open:[48, 50, "1."] isTight
                  Paragraph[51, 58]
                    Text[51, 57] chars:[51, 57, "item 4"]
                OrderedListItem[70, 198] open:[70, 72, "1."] isTight
                  Paragraph[73, 80]
                    Text[73, 79] chars:[73, 79, "item 5"]
                  OrderedList[95, 198] isTight delimiter:'.'
                    OrderedListItem[95, 198] open:[95, 97, "1."] isTight
                      Paragraph[98, 105]
                        Text[98, 104] chars:[98, 104, "item 6"]
                      OrderedList[123, 198] isTight delimiter:'.'
                        OrderedListItem[123, 198] open:[123, 125, "1."] isTight
                          Paragraph[126, 133]
                            Text[126, 132] chars:[126, 132, "item 7"]
                          OrderedList[154, 198] isTight delimiter:'.'
                            OrderedListItem[154, 164] open:[154, 156, "1."] isTight
                              Paragraph[157, 164]
                                Text[157, 163] chars:[157, 163, "item 8"]
                            OrderedListItem[188, 198] open:[188, 190, "1."] isTight hadBlankLineAfter
                              Paragraph[191, 198] isTrailingBlankLine
                                Text[191, 197] chars:[191, 197, "item 9"]
````````````````````````````````


## HTML Parsing

```````````````````````````````` example HTML Parsing: 1
<div>

  This is html text

</div>

This is markdown paragraph
.
<div>

  This is html text

</div>
<p>This is markdown paragraph</p>
.
Document[0, 62]
  HtmlBlock[0, 35]
  Paragraph[36, 62]
    Text[36, 62] chars:[36, 62, "This  … graph"]
````````````````````````````````


```````````````````````````````` example HTML Parsing: 2
<div><strong>

  This is html text

</div>

This is markdown paragraph
.
<div><strong>

  This is html text

</div>
<p>This is markdown paragraph</p>
.
Document[0, 70]
  HtmlBlock[0, 43]
  Paragraph[44, 70]
    Text[44, 70] chars:[44, 70, "This  … graph"]
````````````````````````````````


```````````````````````````````` example HTML Parsing: 3
<div>
  <!--

  This is comment

  -->
  
  This is html text

</div>

This is markdown paragraph
.
<div>
  <!--

  This is comment

  -->
  
  This is html text

</div>
<p>This is markdown paragraph</p>
.
Document[0, 97]
  HtmlBlock[0, 70]
  Paragraph[71, 97]
    Text[71, 97] chars:[71, 97, "This  … graph"]
````````````````````````````````


```````````````````````````````` example HTML Parsing: 4
<hr>
# Heading
.
<hr>
<h1>Heading</h1>
.
Document[0, 14]
  HtmlBlock[0, 5]
  Heading[5, 14] textOpen:[5, 6, "#"] text:[7, 14, "Heading"]
    Text[7, 14] chars:[7, 14, "Heading"]
````````````````````````````````


```````````````````````````````` example HTML Parsing: 5
<div attr
    attr1="test"
>

    html text
    
</div>    

.
<div attr
    attr1="test"
>

    html text
    
</div>    
.
Document[0, 61]
  HtmlBlock[0, 60]
````````````````````````````````


```````````````````````````````` example HTML Parsing: 6
<div attr
    attr1="test"
    
>

    html text
    
</div>    

.
<div attr
    attr1="test"
    
>

    html text
    
</div>    
.
Document[0, 66]
  HtmlBlock[0, 65]
````````````````````````````````


```````````````````````````````` example HTML Parsing: 7
<div>
  <div>

    <div>nested-2-levels</div>

    <div>nested-2-levels</div>
  
  </div>
</div>

.
<div>
  <div>

    <div>nested-2-levels</div>

    <div>nested-2-levels</div>
  
  </div>
</div>
.
Document[0, 98]
  HtmlBlock[0, 97]
````````````````````````````````


```````````````````````````````` example HTML Parsing: 8
<p>par</p>
    <ul>
      <li>list item</li>
    </ul>
.
<p>par</p>
<pre><code>&lt;ul&gt;
  &lt;li&gt;list item&lt;/li&gt;
&lt;/ul&gt;
</code></pre>
.
Document[0, 54]
  HtmlBlock[0, 11]
  IndentedCodeBlock[15, 54]
````````````````````````````````


