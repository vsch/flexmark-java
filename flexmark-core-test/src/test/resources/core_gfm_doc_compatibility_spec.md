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


```````````````````````````````` example(Mismatched List Item Type Handling: 3) options(FAIL)
1. First ordered list item
2. Another item 
  * Unordered sub-list.
.
<ol>
    <li>First ordered list item</li>
    <li>Another item</li>
</ol>
<ul>
    <li>Unordered sub-list.</li>
</ul>
.
Document[0, 68]
  OrderedList[0, 68] isTight delimiter:'.'
    OrderedListItem[0, 27] open:[0, 2, "1."] isTight
      Paragraph[3, 27]
        Text[3, 26] chars:[3, 26, "First …  item"]
    OrderedListItem[27, 68] open:[27, 29, "2."] isTight
      Paragraph[30, 44]
        Text[30, 42] chars:[30, 42, "Anoth …  item"]
  BulletList[46, 68] isTight
    BulletListItem[46, 68] open:[46, 47, "*"] isTight
      Paragraph[48, 68]
        Text[48, 67] chars:[48, 67, "Unord … list."]
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
Document[0, 93]
  BulletList[0, 93] isTight
    BulletListItem[0, 22] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[11, 22] isTight
        BulletListItem[11, 22] open:[11, 12, "-"] isTight
          Paragraph[13, 22]
            Text[13, 21] chars:[13, 21, "item 1.1"]
    BulletListItem[22, 46] open:[22, 23, "-"] isTight
      Paragraph[24, 32]
        Text[24, 30] chars:[24, 30, "item 2"]
      BulletList[34, 46] isTight
        BulletListItem[34, 46] open:[34, 35, "-"] isTight
          Paragraph[36, 46]
            Text[36, 44] chars:[36, 44, "item 2.1"]
    BulletListItem[46, 70] open:[46, 47, "-"] isTight
      Paragraph[48, 56]
        Text[48, 54] chars:[48, 54, "item 3"]
      BulletList[58, 70] isTight
        BulletListItem[58, 70] open:[58, 59, "-"] isTight
          Paragraph[60, 70]
            Text[60, 68] chars:[60, 68, "item 3.1"]
    BulletListItem[70, 93] open:[70, 71, "-"] isTight
      Paragraph[72, 80]
        Text[72, 78] chars:[72, 78, "item 4"]
      BulletList[82, 93] isTight
        BulletListItem[82, 93] open:[82, 83, "-"] isTight
          Paragraph[84, 93]
            Text[84, 92] chars:[84, 92, "item 4.1"]
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
Document[0, 94]
  BulletList[0, 94] isTight
    BulletListItem[0, 23] open:[0, 1, "-"] isLoose hadBlankLineAfter
      Paragraph[2, 9] isTrailingBlankLine
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[12, 23] isTight
        BulletListItem[12, 23] open:[12, 13, "-"] isTight
          Paragraph[14, 23]
            Text[14, 22] chars:[14, 22, "item 1.1"]
    BulletListItem[23, 47] open:[23, 24, "-"] isTight
      Paragraph[25, 33]
        Text[25, 31] chars:[25, 31, "item 2"]
      BulletList[35, 47] isTight
        BulletListItem[35, 47] open:[35, 36, "-"] isTight
          Paragraph[37, 47]
            Text[37, 45] chars:[37, 45, "item 2.1"]
    BulletListItem[47, 71] open:[47, 48, "-"] isTight
      Paragraph[49, 57]
        Text[49, 55] chars:[49, 55, "item 3"]
      BulletList[59, 71] isTight
        BulletListItem[59, 71] open:[59, 60, "-"] isTight
          Paragraph[61, 71]
            Text[61, 69] chars:[61, 69, "item 3.1"]
    BulletListItem[71, 94] open:[71, 72, "-"] isTight
      Paragraph[73, 81]
        Text[73, 79] chars:[73, 79, "item 4"]
      BulletList[83, 94] isTight
        BulletListItem[83, 94] open:[83, 84, "-"] isTight
          Paragraph[85, 94]
            Text[85, 93] chars:[85, 93, "item 4.1"]
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
Document[0, 94]
  BulletList[0, 94] isTight
    BulletListItem[0, 22] open:[0, 1, "-"] isLoose
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[11, 22] isTight
        BulletListItem[11, 22] open:[11, 12, "-"] isTight hadBlankLineAfter
          Paragraph[13, 22] isTrailingBlankLine
            Text[13, 21] chars:[13, 21, "item 1.1"]
    BulletListItem[23, 47] open:[23, 24, "-"] isLoose
      Paragraph[25, 33]
        Text[25, 31] chars:[25, 31, "item 2"]
      BulletList[35, 47] isTight
        BulletListItem[35, 47] open:[35, 36, "-"] isTight
          Paragraph[37, 47]
            Text[37, 45] chars:[37, 45, "item 2.1"]
    BulletListItem[47, 71] open:[47, 48, "-"] isTight
      Paragraph[49, 57]
        Text[49, 55] chars:[49, 55, "item 3"]
      BulletList[59, 71] isTight
        BulletListItem[59, 71] open:[59, 60, "-"] isTight
          Paragraph[61, 71]
            Text[61, 69] chars:[61, 69, "item 3.1"]
    BulletListItem[71, 94] open:[71, 72, "-"] isTight
      Paragraph[73, 81]
        Text[73, 79] chars:[73, 79, "item 4"]
      BulletList[83, 94] isTight
        BulletListItem[83, 94] open:[83, 84, "-"] isTight
          Paragraph[85, 94]
            Text[85, 93] chars:[85, 93, "item 4.1"]
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
Document[0, 94]
  BulletList[0, 94] isTight
    BulletListItem[0, 22] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[11, 22] isTight
        BulletListItem[11, 22] open:[11, 12, "-"] isTight
          Paragraph[13, 22]
            Text[13, 21] chars:[13, 21, "item 1.1"]
    BulletListItem[22, 47] open:[22, 23, "-"] isLoose hadBlankLineAfter
      Paragraph[24, 32] isTrailingBlankLine
        Text[24, 30] chars:[24, 30, "item 2"]
      BulletList[35, 47] isTight
        BulletListItem[35, 47] open:[35, 36, "-"] isTight
          Paragraph[37, 47]
            Text[37, 45] chars:[37, 45, "item 2.1"]
    BulletListItem[47, 71] open:[47, 48, "-"] isTight
      Paragraph[49, 57]
        Text[49, 55] chars:[49, 55, "item 3"]
      BulletList[59, 71] isTight
        BulletListItem[59, 71] open:[59, 60, "-"] isTight
          Paragraph[61, 71]
            Text[61, 69] chars:[61, 69, "item 3.1"]
    BulletListItem[71, 94] open:[71, 72, "-"] isTight
      Paragraph[73, 81]
        Text[73, 79] chars:[73, 79, "item 4"]
      BulletList[83, 94] isTight
        BulletListItem[83, 94] open:[83, 84, "-"] isTight
          Paragraph[85, 94]
            Text[85, 93] chars:[85, 93, "item 4.1"]
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
Document[0, 94]
  BulletList[0, 94] isTight
    BulletListItem[0, 22] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[11, 22] isTight
        BulletListItem[11, 22] open:[11, 12, "-"] isTight
          Paragraph[13, 22]
            Text[13, 21] chars:[13, 21, "item 1.1"]
    BulletListItem[22, 46] open:[22, 23, "-"] isLoose
      Paragraph[24, 32]
        Text[24, 30] chars:[24, 30, "item 2"]
      BulletList[34, 46] isTight
        BulletListItem[34, 46] open:[34, 35, "-"] isTight hadBlankLineAfter
          Paragraph[36, 46] isTrailingBlankLine
            Text[36, 44] chars:[36, 44, "item 2.1"]
    BulletListItem[47, 71] open:[47, 48, "-"] isLoose
      Paragraph[49, 57]
        Text[49, 55] chars:[49, 55, "item 3"]
      BulletList[59, 71] isTight
        BulletListItem[59, 71] open:[59, 60, "-"] isTight
          Paragraph[61, 71]
            Text[61, 69] chars:[61, 69, "item 3.1"]
    BulletListItem[71, 94] open:[71, 72, "-"] isTight
      Paragraph[73, 81]
        Text[73, 79] chars:[73, 79, "item 4"]
      BulletList[83, 94] isTight
        BulletListItem[83, 94] open:[83, 84, "-"] isTight
          Paragraph[85, 94]
            Text[85, 93] chars:[85, 93, "item 4.1"]
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
Document[0, 94]
  BulletList[0, 94] isTight
    BulletListItem[0, 22] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[11, 22] isTight
        BulletListItem[11, 22] open:[11, 12, "-"] isTight
          Paragraph[13, 22]
            Text[13, 21] chars:[13, 21, "item 1.1"]
    BulletListItem[22, 46] open:[22, 23, "-"] isTight
      Paragraph[24, 32]
        Text[24, 30] chars:[24, 30, "item 2"]
      BulletList[34, 46] isTight
        BulletListItem[34, 46] open:[34, 35, "-"] isTight
          Paragraph[36, 46]
            Text[36, 44] chars:[36, 44, "item 2.1"]
    BulletListItem[46, 71] open:[46, 47, "-"] isLoose hadBlankLineAfter
      Paragraph[48, 56] isTrailingBlankLine
        Text[48, 54] chars:[48, 54, "item 3"]
      BulletList[59, 71] isTight
        BulletListItem[59, 71] open:[59, 60, "-"] isTight
          Paragraph[61, 71]
            Text[61, 69] chars:[61, 69, "item 3.1"]
    BulletListItem[71, 94] open:[71, 72, "-"] isTight
      Paragraph[73, 81]
        Text[73, 79] chars:[73, 79, "item 4"]
      BulletList[83, 94] isTight
        BulletListItem[83, 94] open:[83, 84, "-"] isTight
          Paragraph[85, 94]
            Text[85, 93] chars:[85, 93, "item 4.1"]
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
Document[0, 94]
  BulletList[0, 94] isTight
    BulletListItem[0, 22] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[11, 22] isTight
        BulletListItem[11, 22] open:[11, 12, "-"] isTight
          Paragraph[13, 22]
            Text[13, 21] chars:[13, 21, "item 1.1"]
    BulletListItem[22, 46] open:[22, 23, "-"] isTight
      Paragraph[24, 32]
        Text[24, 30] chars:[24, 30, "item 2"]
      BulletList[34, 46] isTight
        BulletListItem[34, 46] open:[34, 35, "-"] isTight
          Paragraph[36, 46]
            Text[36, 44] chars:[36, 44, "item 2.1"]
    BulletListItem[46, 70] open:[46, 47, "-"] isLoose
      Paragraph[48, 56]
        Text[48, 54] chars:[48, 54, "item 3"]
      BulletList[58, 70] isTight
        BulletListItem[58, 70] open:[58, 59, "-"] isTight hadBlankLineAfter
          Paragraph[60, 70] isTrailingBlankLine
            Text[60, 68] chars:[60, 68, "item 3.1"]
    BulletListItem[71, 94] open:[71, 72, "-"] isLoose
      Paragraph[73, 81]
        Text[73, 79] chars:[73, 79, "item 4"]
      BulletList[83, 94] isTight
        BulletListItem[83, 94] open:[83, 84, "-"] isTight
          Paragraph[85, 94]
            Text[85, 93] chars:[85, 93, "item 4.1"]
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
Document[0, 94]
  BulletList[0, 94] isTight
    BulletListItem[0, 22] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[11, 22] isTight
        BulletListItem[11, 22] open:[11, 12, "-"] isTight
          Paragraph[13, 22]
            Text[13, 21] chars:[13, 21, "item 1.1"]
    BulletListItem[22, 46] open:[22, 23, "-"] isTight
      Paragraph[24, 32]
        Text[24, 30] chars:[24, 30, "item 2"]
      BulletList[34, 46] isTight
        BulletListItem[34, 46] open:[34, 35, "-"] isTight
          Paragraph[36, 46]
            Text[36, 44] chars:[36, 44, "item 2.1"]
    BulletListItem[46, 70] open:[46, 47, "-"] isTight
      Paragraph[48, 56]
        Text[48, 54] chars:[48, 54, "item 3"]
      BulletList[58, 70] isTight
        BulletListItem[58, 70] open:[58, 59, "-"] isTight
          Paragraph[60, 70]
            Text[60, 68] chars:[60, 68, "item 3.1"]
    BulletListItem[70, 94] open:[70, 71, "-"] isLoose hadBlankLineAfter
      Paragraph[72, 80] isTrailingBlankLine
        Text[72, 78] chars:[72, 78, "item 4"]
      BulletList[83, 94] isTight
        BulletListItem[83, 94] open:[83, 84, "-"] isTight
          Paragraph[85, 94]
            Text[85, 93] chars:[85, 93, "item 4.1"]
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
<p>Bullet item can interrupt paragraph</p>
<ul>
    <li>item</li>
</ul>
<p>Empty bullet item with space can interrupt paragraph</p>
<ul>
    <li></li>
</ul>
<p>Empty bullet item without space can interrupt paragraph *</p>
<p>Numbered one item can interrupt paragraph 1. one item</p>
<p>Empty Numbered one item with space can interrupt paragraph 1.</p>
<p>Empty Numbered one item without space can interrupt paragraph 1.</p>
<p>Numbered non-one item can interrupt paragraph 2. non-one item</p>
<p>Empty Numbered non-one item with space can interrupt paragraph 2.</p>
<p>Empty Numbered non-one item without space can interrupt paragraph 2.</p>
.
Document[0, 547]
  Paragraph[0, 36]
    Text[0, 35] chars:[0, 35, "Bulle … graph"]
  BulletList[36, 43] isTight
    BulletListItem[36, 43] open:[36, 37, "*"] isTight hadBlankLineAfter
      Paragraph[38, 43] isTrailingBlankLine
        Text[38, 42] chars:[38, 42, "item"]
  Paragraph[44, 98]
    Text[44, 96] chars:[44, 96, "Empty … graph"]
  BulletList[98, 99] isTight
    BulletListItem[98, 99] open:[98, 99, "*"] isTight hadBlankLineAfter
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
    <li>Numbered one item can interrupt paragraph of a bullet list item</li>
    <li>one item</li>
</ul>
<!--List Break-->
<ul>
    <li>Empty Numbered one item with space can interrupt paragraph of a bullet list item</li>
    <li></li>
</ul>
<!--List Break-->
<ul>
    <li>Empty Numbered one item without space can interrupt paragraph of a bullet list item 1.</li>
</ul>
<!--List Break-->
<ul>
    <li>Numbered non-one item can interrupt paragraph of a bullet list item</li>
    <li>non-one item</li>
</ul>
<!--List Break-->
<ul>
    <li>Empty Numbered non-one item with space can interrupt paragraph of a bullet list item</li>
    <li></li>
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
      Paragraph[62, 67] isTrailingBlankLine
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
      Paragraph[189, 269] isTrailingBlankLine
        Text[189, 266] chars:[189, 266, "Empty …  item"]
        SoftLineBreak[266, 267]
        Text[267, 268] chars:[267, 268, "*"]
  HtmlCommentBlock[270, 288]
  BulletList[289, 367] isTight
    BulletListItem[289, 355] open:[289, 290, "*"] isTight
      Paragraph[291, 355]
        Text[291, 354] chars:[291, 354, "Numbe …  item"]
    OrderedListItem[355, 367] open:[355, 357, "1."] isTight hadBlankLineAfter
      Paragraph[358, 367] isTrailingBlankLine
        Text[358, 366] chars:[358, 366, "one item"]
  HtmlCommentBlock[368, 386]
  BulletList[387, 472] isTight
    BulletListItem[387, 470] open:[387, 388, "*"] isTight
      Paragraph[389, 470]
        Text[389, 469] chars:[389, 469, "Empty …  item"]
    OrderedListItem[470, 472] open:[470, 472, "1."] isTight hadBlankLineAfter
  HtmlCommentBlock[475, 493]
  BulletList[494, 583] isTight
    BulletListItem[494, 583] open:[494, 495, "*"] isTight hadBlankLineAfter
      Paragraph[496, 583] isTrailingBlankLine
        Text[496, 579] chars:[496, 579, "Empty …  item"]
        SoftLineBreak[579, 580]
        Text[580, 582] chars:[580, 582, "1."]
  HtmlCommentBlock[584, 602]
  BulletList[603, 689] isTight
    BulletListItem[603, 673] open:[603, 604, "*"] isTight
      Paragraph[605, 673]
        Text[605, 672] chars:[605, 672, "Numbe …  item"]
    OrderedListItem[673, 689] open:[673, 675, "2."] isTight hadBlankLineAfter
      Paragraph[676, 689] isTrailingBlankLine
        Text[676, 688] chars:[676, 688, "non-o …  item"]
  HtmlCommentBlock[690, 708]
  BulletList[709, 798] isTight
    BulletListItem[709, 796] open:[709, 710, "*"] isTight
      Paragraph[711, 796]
        Text[711, 795] chars:[711, 795, "Empty …  item"]
    OrderedListItem[796, 798] open:[796, 798, "2."] isTight hadBlankLineAfter
  HtmlCommentBlock[801, 819]
  BulletList[820, 913] isTight
    BulletListItem[820, 913] open:[820, 821, "*"] isTight hadBlankLineAfter
      Paragraph[822, 913] isTrailingBlankLine
        Text[822, 909] chars:[822, 909, "Empty …  item"]
        SoftLineBreak[909, 910]
        Text[910, 912] chars:[910, 912, "2."]
````````````````````````````````


Test to see which list items can interrupt another numbered list item's paragraphs

```````````````````````````````` example List Item Interrupts Paragraph: 3
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
    <li>Bullet item can interrupt paragraph of a numbered list item</li>
    <li>item</li>
</ol>
<!--List Break-->
<ol>
    <li>Empty bullet item with space can interrupt paragraph of a numbered list item</li>
    <li></li>
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
    OrderedListItem[0, 63] open:[0, 2, "1."] isTight
      Paragraph[3, 63]
        Text[3, 62] chars:[3, 62, "Bulle …  item"]
    BulletListItem[63, 70] open:[63, 64, "*"] isTight hadBlankLineAfter
      Paragraph[65, 70] isTrailingBlankLine
        Text[65, 69] chars:[65, 69, "item"]
  HtmlCommentBlock[71, 89]
  OrderedList[90, 171] isTight delimiter:'.'
    OrderedListItem[90, 170] open:[90, 92, "1."] isTight
      Paragraph[93, 170]
        Text[93, 169] chars:[93, 169, "Empty …  item"]
    BulletListItem[170, 171] open:[170, 171, "*"] isTight hadBlankLineAfter
  HtmlCommentBlock[174, 192]
  OrderedList[193, 278] isTight delimiter:'.'
    OrderedListItem[193, 278] open:[193, 195, "1."] isTight hadBlankLineAfter
      Paragraph[196, 278] isTrailingBlankLine
        Text[196, 275] chars:[196, 275, "Empty …  item"]
        SoftLineBreak[275, 276]
        Text[276, 277] chars:[276, 277, "*"]
  HtmlCommentBlock[279, 297]
  OrderedList[298, 379] isTight delimiter:'.'
    OrderedListItem[298, 367] open:[298, 300, "1."] isTight
      Paragraph[301, 367]
        Text[301, 366] chars:[301, 366, "Numbe …  item"]
    OrderedListItem[367, 379] open:[367, 369, "1."] isTight hadBlankLineAfter
      Paragraph[370, 379] isTrailingBlankLine
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
      Paragraph[513, 603] isTrailingBlankLine
        Text[513, 598] chars:[513, 598, "Empty …  item"]
        SoftLineBreak[599, 600]
        Text[600, 602] chars:[600, 602, "1."]
  HtmlCommentBlock[604, 622]
  OrderedList[623, 712] isTight delimiter:'.'
    OrderedListItem[623, 696] open:[623, 625, "1."] isTight
      Paragraph[626, 696]
        Text[626, 695] chars:[626, 695, "Numbe …  item"]
    OrderedListItem[696, 712] open:[696, 698, "2."] isTight hadBlankLineAfter
      Paragraph[699, 712] isTrailingBlankLine
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
      Paragraph[849, 942] isTrailingBlankLine
        Text[849, 938] chars:[849, 938, "Empty …  item"]
        SoftLineBreak[938, 939]
        Text[939, 941] chars:[939, 941, "2."]
````````````````````````````````


## List Item Indent Handling

Test how list indentation is determined

Not a complete match. Haven't figured out what rules GitHub uses for indentation removal at each
level.

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
            <li>item 3
                <ul>
                    <li>item 4</li>
                    <li>item 5
                        <ul>
                            <li>item 6</li>
                            <li>item 7
                                <ul>
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
        BulletListItem[21, 135] open:[21, 22, "*"] isTight
          Paragraph[23, 30]
            Text[23, 29] chars:[23, 29, "item 3"]
          BulletList[33, 135] isTight
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
                BulletListItem[75, 135] open:[75, 76, "*"] isTight
                  Paragraph[77, 84]
                    Text[77, 83] chars:[77, 83, "item 7"]
                  BulletList[91, 135] isTight
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


Actual GitHub results. Not matched.

```````````````````````````````` example(List Item Indent Handling: 2) options(FAIL)
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

Not a complete match. Haven't figured out what rules GitHub uses for indentation removal at each
level.

```````````````````````````````` example List Item Indent Handling: 3
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
            <li>item 4
                <ul>
                    <li>item 5</li>
                    <li>item 6</li>
                    <li>item 7
                        <ul>
                            <li>item 8</li>
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
        BulletListItem[36, 145] open:[36, 37, "*"] isTight
          Paragraph[39, 46]
            Text[39, 45] chars:[39, 45, "item 4"]
          BulletList[50, 145] isTight
            BulletListItem[50, 60] open:[50, 51, "*"] isTight
              Paragraph[53, 60]
                Text[53, 59] chars:[53, 59, "item 5"]
            BulletListItem[65, 75] open:[65, 66, "*"] isTight
              Paragraph[68, 75]
                Text[68, 74] chars:[68, 74, "item 6"]
            BulletListItem[81, 145] open:[81, 82, "*"] isTight
              Paragraph[84, 91]
                Text[84, 90] chars:[84, 90, "item 7"]
              BulletList[98, 145] isTight
                BulletListItem[98, 108] open:[98, 99, "*"] isTight
                  Paragraph[101, 108]
                    Text[101, 107] chars:[101, 107, "item 8"]
                BulletListItem[116, 126] open:[116, 117, "*"] isTight
                  Paragraph[119, 126]
                    Text[119, 125] chars:[119, 125, "item 9"]
                BulletListItem[135, 145] open:[135, 136, "*"] isTight
                  Paragraph[138, 145]
                    Text[138, 145] chars:[138, 145, "item 10"]
````````````````````````````````


Actual GitHub results. Not matched.

```````````````````````````````` example(List Item Indent Handling: 4) options(FAIL)
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

Not a complete match. Haven't figured out what rules GitHub uses for indentation removal at each
level.

```````````````````````````````` example List Item Indent Handling: 5
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
                <ul>
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
                                <ul>
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
        BulletListItem[24, 180] open:[24, 25, "*"] isLoose hadBlankLineAfter
          Paragraph[26, 33] isTrailingBlankLine
            Text[26, 32] chars:[26, 32, "item 3"]
          BulletList[39, 180] isTight
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
                BulletListItem[96, 180] open:[96, 97, "*"] isLoose hadBlankLineAfter
                  Paragraph[98, 105] isTrailingBlankLine
                    Text[98, 104] chars:[98, 104, "item 7"]
                  BulletList[119, 180] isTight
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


Actual GitHub results. Not matched.

```````````````````````````````` example(List Item Indent Handling: 6) options(FAIL)
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

Not a complete match. Haven't figured out what rules GitHub uses for indentation removal at each
level.

```````````````````````````````` example List Item Indent Handling: 7
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
            <li>item 3
                <ul>
                    <li>item 4</li>
                    <li>item 5
                        <ul>
                            <li>item 6</li>
                            <li>item 7
                                <ul>
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
        BulletListItem[24, 145] open:[24, 25, "*"] isTight
          Paragraph[26, 33]
            Text[26, 32] chars:[26, 32, "item 3"]
          BulletList[37, 145] isTight
            BulletListItem[37, 46] open:[37, 38, "*"] isTight
              Paragraph[39, 46]
                Text[39, 45] chars:[39, 45, "item 4"]
            BulletListItem[51, 145] open:[51, 52, "*"] isTight
              Paragraph[53, 60]
                Text[53, 59] chars:[53, 59, "item 5"]
              BulletList[66, 145] isTight
                BulletListItem[66, 75] open:[66, 67, "*"] isTight
                  Paragraph[68, 75]
                    Text[68, 74] chars:[68, 74, "item 6"]
                BulletListItem[82, 145] open:[82, 83, "*"] isTight
                  Paragraph[84, 91]
                    Text[84, 90] chars:[84, 90, "item 7"]
                  BulletList[99, 145] isTight
                    BulletListItem[99, 108] open:[99, 100, "*"] isTight
                      Paragraph[101, 108]
                        Text[101, 107] chars:[101, 107, "item 8"]
                    BulletListItem[117, 145] open:[117, 118, "*"] isTight
                      Paragraph[119, 126]
                        Text[119, 125] chars:[119, 125, "item 9"]
                      BulletList[136, 145] isTight
                        BulletListItem[136, 145] open:[136, 137, "*"] isTight
                          Paragraph[138, 145]
                            Text[138, 145] chars:[138, 145, "item 10"]
````````````````````````````````


Actual GitHub results. Not matched.

```````````````````````````````` example(List Item Indent Handling: 8) options(FAIL)
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


Test where lazy continuation affects list item processing.

Not a complete match. Haven't figured out what rules GitHub uses for indentation removal at each
level.

```````````````````````````````` example List Item Indent Handling: 9
* item 1
       * item 2
* item 3
        * item 4
.
<ul>
    <li>item 1 * item 2</li>
    <li>item 3 * item 4</li>
</ul>
.
Document[0, 50]
  BulletList[0, 50] isTight
    BulletListItem[0, 25] open:[0, 1, "*"] isTight
      Paragraph[2, 25]
        Text[2, 8] chars:[2, 8, "item 1"]
        SoftLineBreak[8, 9]
        Text[16, 24] chars:[16, 24, "* item 2"]
    BulletListItem[25, 50] open:[25, 26, "*"] isTight
      Paragraph[27, 50]
        Text[27, 33] chars:[27, 33, "item 3"]
        SoftLineBreak[33, 34]
        Text[42, 50] chars:[42, 50, "* item 4"]
````````````````````````````````


Actual GitHub results. Not matched.

```````````````````````````````` example(List Item Indent Handling: 10) options(FAIL)
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
affects sub-list indentation.

Test if block quote can interrupt item paragraph

```````````````````````````````` example List Item Indent Handling: 11
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
            <li>item 3 &gt; * item 4 &gt;  * item 5 &gt;   * item 6</li>
        </ul>
    </li>
</ul>
.
Document[0, 77]
  BulletList[0, 77] isTight
    BulletListItem[0, 77] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[10, 77] isTight
        BulletListItem[10, 19] open:[10, 11, "*"] isTight
          Paragraph[12, 19]
            Text[12, 18] chars:[12, 18, "item 2"]
        BulletListItem[21, 77] open:[21, 22, "*"] isTight
          Paragraph[23, 77]
            Text[23, 29] chars:[23, 29, "item 3"]
            SoftLineBreak[29, 30]
            Text[34, 44] chars:[34, 44, "> * item 4"]
            SoftLineBreak[44, 45]
            Text[49, 60] chars:[49, 60, ">  *  … tem 5"]
            SoftLineBreak[60, 61]
            Text[65, 77] chars:[65, 77, ">   * … tem 6"]
````````````````````````````````


```````````````````````````````` example List Item Indent Handling: 12
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
            <li>item 3 &gt; * item 4 &gt;  * item 5 &gt;   * item 6</li>
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
          Paragraph[23, 80]
            Text[23, 29] chars:[23, 29, "item 3"]
            SoftLineBreak[29, 30]
            Text[35, 45] chars:[35, 45, "> * item 4"]
            SoftLineBreak[45, 46]
            Text[51, 62] chars:[51, 62, ">  *  … tem 5"]
            SoftLineBreak[62, 63]
            Text[68, 80] chars:[68, 80, ">   * … tem 6"]
````````````````````````````````


```````````````````````````````` example List Item Indent Handling: 13
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
Document[0, 80]
  BulletList[0, 80] isTight
    BulletListItem[0, 80] open:[0, 1, "*"] isLoose hadBlankLine
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[10, 80] isTight
        BulletListItem[10, 19] open:[10, 11, "*"] isTight
          Paragraph[12, 19]
            Text[12, 18] chars:[12, 18, "item 2"]
        BulletListItem[21, 80] open:[21, 22, "*"] isLoose hadBlankLineAfter
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


```````````````````````````````` example List Item Indent Handling: 14
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

```````````````````````````````` example List Item Indent Handling: 15
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


```````````````````````````````` example List Item Indent Handling: 16
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
                <pre><code>  sub item indented code
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
                <pre><code> sub item indented code
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
          IndentedCodeBlock[134, 159]
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
          IndentedCodeBlock[306, 330]
````````````````````````````````


More extensive test to show where the boundary switch to indented code occurs. Sub-items first
paragraph is a paragraph, the second is indented code

Not a complete match to GitHub, see next example

```````````````````````````````` example List Item Indent Handling: 17
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
        <pre><code>  item para 7
</code></pre>
        <ul>
            <li>
                <p>item 2</p>
                <p>item para 1</p>
                <p>item para 2</p>
                <p>item para 3</p>
                <p>item para 4</p>
                <p>item para 5</p>
                <p>item para 6</p>
                <p>item para 7</p>
            </li>
            <li>
                <p>item 3</p>
                <p>item para 1</p>
                <p>item para 2</p>
                <p>item para 3</p>
                <p>item para 4</p>
                <p>item para 5</p>
                <p>item para 6</p>
                <pre><code>  item para 7
</code></pre>
                <ul>
                    <li>item 4</li>
                </ul>
                <p>item para 1</p>
                <p>item para 2</p>
                <p>item para 3</p>
                <p>item para 4</p>
                <p>item para 5</p>
                <pre><code>  item para 6

   item para 7
</code></pre>
                <ul>
                    <li>item 5</li>
                </ul>
                <p>item para 1</p>
                <p>item para 2</p>
                <p>item para 3</p>
                <p>item para 4</p>
                <pre><code>  item para 5

   item para 6

    item para 7
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
      IndentedCodeBlock[152, 166]
      BulletList[176, 981] isTight
        BulletListItem[176, 355] open:[176, 177, "*"] isLoose hadBlankLineAfter
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
          Paragraph[313, 325] isTrailingBlankLine
            Text[313, 324] chars:[313, 324, "item  … ara 6"]
          Paragraph[343, 355] isTrailingBlankLine
            Text[343, 354] chars:[343, 354, "item  … ara 7"]
        BulletListItem[366, 981] open:[366, 367, "*"] isLoose hadBlankLineAfter
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
          Paragraph[484, 496] isTrailingBlankLine
            Text[484, 495] chars:[484, 495, "item  … ara 5"]
          Paragraph[514, 526] isTrailingBlankLine
            Text[514, 525] chars:[514, 525, "item  … ara 6"]
          IndentedCodeBlock[544, 558]
          BulletList[570, 579] isTight
            BulletListItem[570, 579] open:[570, 571, "*"] isTight hadBlankLineAfter
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
          IndentedCodeBlock[727, 775]
          BulletList[788, 797] isTight
            BulletListItem[788, 797] open:[788, 789, "*"] isTight hadBlankLineAfter
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
          IndentedCodeBlock[906, 981]
````````````````````````````````


Cannot generate exact GitHub idiosyncrasies

```````````````````````````````` example(List Item Indent Handling: 18) options(FAIL)
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
            <li>
                <p>item 2</p>
                <p>item para 1</p>
                <p>item para 2</p>
                <p>item para 3</p>
                <p>item para 4</p>
                <p>item para 5</p>
                <p>item para 6</p>
                <p>item para 7</p>
            </li>
            <li>
                <p>item 3</p>
                <p>item para 1</p>
                <p>item para 2</p>
                <p>item para 3</p>
                <p>item para 4</p>
                <p>item para 5</p>
                <p>item para 6</p>
                <pre><code>item para 7
</code></pre>
            </li>
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
````````````````````````````````


Test for how items with indent > first list item's indent but < previous item's content indent
are handled. Mainly, if they are handled in a weird way of treating the item as a sub-item of
the previous list item. There was one that did it that way, GitHub comments if I can remember
right, but now they switched to commonmark list handling with mods. Guess it is now GFC--GitHub
Flavoured Commonmark.

```````````````````````````````` example List Item Indent Handling: 19
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

```````````````````````````````` example List Item Indent Handling: 20
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
       #### Heading 4
        ##### Heading 5
         ###### Heading 6

.
<ul>
    <li>
        <p>item 1</p>
        <h1 id="heading-1">Heading 1</h1>
        <p>## Heading 2 ### Heading 3 #### Heading 4 ##### Heading 5 ###### Heading 6</p>
        <ul>
            <li>
                <p>item 2</p>
                <h1 id="heading-1-1">Heading 1</h1>
                <h2 id="heading-2">Heading 2</h2>
                <h3 id="heading-3">Heading 3</h3>
                <p>#### Heading 4 ##### Heading 5 ###### Heading 6</p>
            </li>
        </ul>
    </li>
</ul>
.
Document[0, 261]
  BulletList[0, 260] isTight
    BulletListItem[0, 260] open:[0, 1, "*"] isLoose
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      Heading[11, 22] textOpen:[11, 12, "#"] text:[13, 22, "Heading 1"]
        Text[13, 22] chars:[13, 22, "Heading 1"]
      Paragraph[26, 123]
        Text[26, 38] chars:[26, 38, "## He … ing 2"]
        SoftLineBreak[38, 39]
        Text[43, 56] chars:[43, 56, "### H … ing 3"]
        SoftLineBreak[56, 57]
        Text[62, 76] chars:[62, 76, "####  … ing 4"]
        SoftLineBreak[76, 77]
        Text[83, 98] chars:[83, 98, "##### … ing 5"]
        SoftLineBreak[98, 99]
        Text[106, 122] chars:[106, 122, "##### … ing 6"]
      BulletList[125, 260] isTight
        BulletListItem[125, 260] open:[125, 126, "*"] isLoose
          Paragraph[127, 134]
            Text[127, 133] chars:[127, 133, "item 2"]
          Heading[138, 149] textOpen:[138, 139, "#"] text:[140, 149, "Heading 1"]
            Text[140, 149] chars:[140, 149, "Heading 1"]
          Heading[155, 167] textOpen:[155, 157, "##"] text:[158, 167, "Heading 2"]
            Text[158, 167] chars:[158, 167, "Heading 2"]
          Heading[174, 187] textOpen:[174, 177, "###"] text:[178, 187, "Heading 3"]
            Text[178, 187] chars:[178, 187, "Heading 3"]
          Paragraph[195, 260] isTrailingBlankLine
            Text[195, 209] chars:[195, 209, "####  … ing 4"]
            SoftLineBreak[209, 210]
            Text[218, 233] chars:[218, 233, "##### … ing 5"]
            SoftLineBreak[233, 234]
            Text[243, 259] chars:[243, 259, "##### … ing 6"]
````````````````````````````````


```````````````````````````````` example List Item Indent Handling: 21
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
       #### Heading 4
        ##### Heading 5
         ###### Heading 6
   * item 3
     # Heading 1
      ## Heading 2
       ### Heading 3
        #### Heading 4
         ##### Heading 5
          ###### Heading 6

.
<ul>
    <li>
        <p>item 1</p>
        <h1 id="heading-1">Heading 1</h1>
        <p>## Heading 2 ### Heading 3 #### Heading 4 ##### Heading 5 ###### Heading 6</p>
        <ul>
            <li>
                <p>item 2</p>
                <h1 id="heading-1-1">Heading 1</h1>
                <h2 id="heading-2">Heading 2</h2>
                <h3 id="heading-3">Heading 3</h3>
                <p>#### Heading 4 ##### Heading 5 ###### Heading 6</p>
                <ul>
                    <li>
                        <p>item 3</p>
                        <h1 id="heading-1-2">Heading 1</h1>
                        <h2 id="heading-2-1">Heading 2</h2>
                        <h3 id="heading-3-1">Heading 3</h3>
                        <h4 id="heading-4">Heading 4</h4>
                        <p>##### Heading 5 ###### Heading 6</p>
                    </li>
                </ul>
            </li>
        </ul>
    </li>
</ul>
.
Document[0, 405]
  BulletList[0, 404] isTight
    BulletListItem[0, 404] open:[0, 1, "*"] isLoose
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      Heading[11, 22] textOpen:[11, 12, "#"] text:[13, 22, "Heading 1"]
        Text[13, 22] chars:[13, 22, "Heading 1"]
      Paragraph[26, 123]
        Text[26, 38] chars:[26, 38, "## He … ing 2"]
        SoftLineBreak[38, 39]
        Text[43, 56] chars:[43, 56, "### H … ing 3"]
        SoftLineBreak[56, 57]
        Text[62, 76] chars:[62, 76, "####  … ing 4"]
        SoftLineBreak[76, 77]
        Text[83, 98] chars:[83, 98, "##### … ing 5"]
        SoftLineBreak[98, 99]
        Text[106, 122] chars:[106, 122, "##### … ing 6"]
      BulletList[125, 404] isTight
        BulletListItem[125, 404] open:[125, 126, "*"] isLoose
          Paragraph[127, 134]
            Text[127, 133] chars:[127, 133, "item 2"]
          Heading[138, 149] textOpen:[138, 139, "#"] text:[140, 149, "Heading 1"]
            Text[140, 149] chars:[140, 149, "Heading 1"]
          Heading[155, 167] textOpen:[155, 157, "##"] text:[158, 167, "Heading 2"]
            Text[158, 167] chars:[158, 167, "Heading 2"]
          Heading[174, 187] textOpen:[174, 177, "###"] text:[178, 187, "Heading 3"]
            Text[178, 187] chars:[178, 187, "Heading 3"]
          Paragraph[195, 260]
            Text[195, 209] chars:[195, 209, "####  … ing 4"]
            SoftLineBreak[209, 210]
            Text[218, 233] chars:[218, 233, "##### … ing 5"]
            SoftLineBreak[233, 234]
            Text[243, 259] chars:[243, 259, "##### … ing 6"]
          BulletList[263, 404] isTight
            BulletListItem[263, 404] open:[263, 264, "*"] isLoose
              Paragraph[265, 272]
                Text[265, 271] chars:[265, 271, "item 3"]
              Heading[277, 288] textOpen:[277, 278, "#"] text:[279, 288, "Heading 1"]
                Text[279, 288] chars:[279, 288, "Heading 1"]
              Heading[295, 307] textOpen:[295, 297, "##"] text:[298, 307, "Heading 2"]
                Text[298, 307] chars:[298, 307, "Heading 2"]
              Heading[315, 328] textOpen:[315, 318, "###"] text:[319, 328, "Heading 3"]
                Text[319, 328] chars:[319, 328, "Heading 3"]
              Heading[337, 351] textOpen:[337, 341, "####"] text:[342, 351, "Heading 4"]
                Text[342, 351] chars:[342, 351, "Heading 4"]
              Paragraph[361, 404] isTrailingBlankLine
                Text[361, 376] chars:[361, 376, "##### … ing 5"]
                SoftLineBreak[376, 377]
                Text[387, 403] chars:[387, 403, "##### … ing 6"]
````````````````````````````````


GitHub processes ATX headings if the list has a blank line, cannot do that. More accurately, can
but this is a KLUDGE in GitHub that is not supported.

```````````````````````````````` example(List Item Indent Handling: 22) options(FAIL)
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
       #### Heading 4
        ##### Heading 5
         ###### Heading 6

.
<ul>
    <li>
        <p>item 1</p>
        <h1 id="heading-1">Heading 1</h1>
        <h2 id="heading-2">Heading 2</h2>
        <h3 id="heading-3">Heading 3</h3>
        <p>#### Heading 4 ##### Heading 5 ###### Heading 6</p>
        <ul>
            <li>item 2 # Heading 1 ## Heading 2 ### Heading 3 #### Heading 4 ##### Heading 5 ###### Heading 6</li>
        </ul>
    </li>
</ul>
.
````````````````````````````````


GitHub processes ATX headings if the list has a blank line, cannot do that. More accurately, can
but this is a KLUDGE in GitHub that is not supported.

```````````````````````````````` example(List Item Indent Handling: 23) options(FAIL)
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
       #### Heading 4
        ##### Heading 5
         ###### Heading 6
         
   * item 3
     # Heading 1
      ## Heading 2
       ### Heading 3
        #### Heading 4
         ##### Heading 5
          ###### Heading 6

.
<ul>
    <li>
        <p>item 1</p>
        <h1 id="heading-1">Heading 1</h1>
        <h2 id="heading-2">Heading 2</h2>
        <h3 id="heading-3">Heading 3</h3>
        <p>#### Heading 4 ##### Heading 5 ###### Heading 6</p>
        <ul>
            <li>
                <p>item 2</p>
                <h1 id="heading-1-1">Heading 1</h1>
                <h2 id="heading-2-1">Heading 2</h2>
                <h3 id="heading-3-1">Heading 3</h3>
                <p>#### Heading 4 ##### Heading 5 ###### Heading 6</p>
            </li>
            <li>
                <p>item 3</p>
                <h1 id="heading-1-2">Heading 1</h1>
                <h2 id="heading-2-2">Heading 2</h2>
                <h3 id="heading-3-2">Heading 3</h3>
                <h4 id="heading-4">Heading 4</h4>
                <p>##### Heading 5 ###### Heading 6</p>
            </li>
        </ul>
    </li>
</ul>
.
````````````````````````````````


Not an exact match, GitHub has idiosyncrasies in its leading space removal that are not
duplicated.

```````````````````````````````` example List Item Indent Handling: 24
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
    
       #### Heading 4
    
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
        <p>##### Heading 5</p>
        <p>###### Heading 6</p>
        <ul>
            <li>
                <p>item 2</p>
                <h1 id="heading-1-1">Heading 1</h1>
                <h2 id="heading-2">Heading 2</h2>
                <h3 id="heading-3">Heading 3</h3>
                <p>#### Heading 4</p>
                <p>##### Heading 5</p>
                <p>###### Heading 6</p>
            </li>
        </ul>
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
      Paragraph[28, 41] isTrailingBlankLine
        Text[28, 40] chars:[28, 40, "## He … ing 2"]
      Paragraph[46, 60] isTrailingBlankLine
        Text[46, 59] chars:[46, 59, "### H … ing 3"]
      Paragraph[66, 81] isTrailingBlankLine
        Text[66, 80] chars:[66, 80, "####  … ing 4"]
      Paragraph[88, 104] isTrailingBlankLine
        Text[88, 103] chars:[88, 103, "##### … ing 5"]
      Paragraph[112, 129] isTrailingBlankLine
        Text[112, 128] chars:[112, 128, "##### … ing 6"]
      BulletList[132, 295] isTight
        BulletListItem[132, 295] open:[132, 133, "*"] isLoose hadBlankLineAfter
          Paragraph[134, 141] isTrailingBlankLine
            Text[134, 140] chars:[134, 140, "item 2"]
          Heading[146, 157] textOpen:[146, 147, "#"] text:[148, 157, "Heading 1"]
            Text[148, 157] chars:[148, 157, "Heading 1"]
          Heading[168, 180] textOpen:[168, 170, "##"] text:[171, 180, "Heading 2"]
            Text[171, 180] chars:[171, 180, "Heading 2"]
          Heading[192, 205] textOpen:[192, 195, "###"] text:[196, 205, "Heading 3"]
            Text[196, 205] chars:[196, 205, "Heading 3"]
          Paragraph[218, 233] isTrailingBlankLine
            Text[218, 232] chars:[218, 232, "####  … ing 4"]
          Paragraph[246, 262] isTrailingBlankLine
            Text[246, 261] chars:[246, 261, "##### … ing 5"]
          Paragraph[278, 295] isTrailingBlankLine
            Text[278, 294] chars:[278, 294, "##### … ing 6"]
````````````````````````````````


GitHub idosyncrasies not duplicated

```````````````````````````````` example(List Item Indent Handling: 25) options(FAIL)
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
    
       #### Heading 4
    
        ##### Heading 5
      
         ###### Heading 6

.
<ul>
    <li>
        <p>item 1</p>
        <h1 id="heading-1">Heading 1</h1>
        <h2 id="heading-2">Heading 2</h2>
        <h3 id="heading-3">Heading 3</h3>
        <p>#### Heading 4</p>
        <p>##### Heading 5</p>
        <p>###### Heading 6</p>
        <ul>
            <li>
                <p>item 2</p>
                <h1 id="heading-1-1">Heading 1</h1>
                <h2 id="heading-2-1">Heading 2</h2>
                <h3 id="heading-3-1">Heading 3</h3>
                <p>#### Heading 4</p>
                <p>##### Heading 5</p>
                <p>###### Heading 6</p>
            </li>
        </ul>
    </li>
</ul>
.
````````````````````````````````


Not an exact match, GitHub has idiosyncrasies in its leading space removal that are not
duplicated.

```````````````````````````````` example List Item Indent Handling: 26
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
    
       #### Heading 4
    
        ##### Heading 5
      
         ###### Heading 6

   * item 3
   
     # Heading 1
     
      ## Heading 2
     
       ### Heading 3
     
        #### Heading 4
     
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
        <p>##### Heading 5</p>
        <p>###### Heading 6</p>
        <ul>
            <li>
                <p>item 2</p>
                <h1 id="heading-1-1">Heading 1</h1>
                <h2 id="heading-2">Heading 2</h2>
                <h3 id="heading-3">Heading 3</h3>
                <p>#### Heading 4</p>
                <p>##### Heading 5</p>
                <p>###### Heading 6</p>
                <ul>
                    <li>item 3</li>
                </ul>
                <h1 id="heading-1-2">Heading 1</h1>
                <h2 id="heading-2-1">Heading 2</h2>
                <p>### Heading 3</p>
                <p>#### Heading 4</p>
                <p>##### Heading 5</p>
                <pre><code>  ###### Heading 6
</code></pre>
            </li>
        </ul>
    </li>
</ul>
.
Document[0, 480]
  BulletList[0, 476] isTight
    BulletListItem[0, 476] open:[0, 1, "*"] isLoose hadBlankLineAfter
      Paragraph[2, 9] isTrailingBlankLine
        Text[2, 8] chars:[2, 8, "item 1"]
      Heading[12, 23] textOpen:[12, 13, "#"] text:[14, 23, "Heading 1"]
        Text[14, 23] chars:[14, 23, "Heading 1"]
      Paragraph[28, 41] isTrailingBlankLine
        Text[28, 40] chars:[28, 40, "## He … ing 2"]
      Paragraph[46, 60] isTrailingBlankLine
        Text[46, 59] chars:[46, 59, "### H … ing 3"]
      Paragraph[66, 81] isTrailingBlankLine
        Text[66, 80] chars:[66, 80, "####  … ing 4"]
      Paragraph[88, 104] isTrailingBlankLine
        Text[88, 103] chars:[88, 103, "##### … ing 5"]
      Paragraph[112, 129] isTrailingBlankLine
        Text[112, 128] chars:[112, 128, "##### … ing 6"]
      BulletList[132, 476] isTight
        BulletListItem[132, 476] open:[132, 133, "*"] isLoose hadBlankLineAfter
          Paragraph[134, 141] isTrailingBlankLine
            Text[134, 140] chars:[134, 140, "item 2"]
          Heading[146, 157] textOpen:[146, 147, "#"] text:[148, 157, "Heading 1"]
            Text[148, 157] chars:[148, 157, "Heading 1"]
          Heading[168, 180] textOpen:[168, 170, "##"] text:[171, 180, "Heading 2"]
            Text[171, 180] chars:[171, 180, "Heading 2"]
          Heading[192, 205] textOpen:[192, 195, "###"] text:[196, 205, "Heading 3"]
            Text[196, 205] chars:[196, 205, "Heading 3"]
          Paragraph[218, 233] isTrailingBlankLine
            Text[218, 232] chars:[218, 232, "####  … ing 4"]
          Paragraph[246, 262] isTrailingBlankLine
            Text[246, 261] chars:[246, 261, "##### … ing 5"]
          Paragraph[278, 295] isTrailingBlankLine
            Text[278, 294] chars:[278, 294, "##### … ing 6"]
          BulletList[299, 308] isTight
            BulletListItem[299, 308] open:[299, 300, "*"] isTight hadBlankLineAfter
              Paragraph[301, 308] isTrailingBlankLine
                Text[301, 307] chars:[301, 307, "item 3"]
          Heading[317, 328] textOpen:[317, 318, "#"] text:[319, 328, "Heading 1"]
            Text[319, 328] chars:[319, 328, "Heading 1"]
          Heading[341, 353] textOpen:[341, 343, "##"] text:[344, 353, "Heading 2"]
            Text[344, 353] chars:[344, 353, "Heading 2"]
          Paragraph[367, 381] isTrailingBlankLine
            Text[367, 380] chars:[367, 380, "### H … ing 3"]
          Paragraph[395, 410] isTrailingBlankLine
            Text[395, 409] chars:[395, 409, "####  … ing 4"]
          Paragraph[425, 441] isTrailingBlankLine
            Text[425, 440] chars:[425, 440, "##### … ing 5"]
          IndentedCodeBlock[457, 476]
````````````````````````````````


GitHub idosyncrasies not duplicated

```````````````````````````````` example(List Item Indent Handling: 27) options(FAIL)
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
    
       #### Heading 4
    
        ##### Heading 5
      
         ###### Heading 6

   * item 3
   
     # Heading 1
     
      ## Heading 2
     
       ### Heading 3
     
        #### Heading 4
     
         ##### Heading 5
       
          ###### Heading 6
   
.
<ul>
    <li>
        <p>item 1</p>
        <h1 id="heading-1">Heading 1</h1>
        <h2 id="heading-2">Heading 2</h2>
        <h3 id="heading-3">Heading 3</h3>
        <p>#### Heading 4</p>
        <p>##### Heading 5</p>
        <p>###### Heading 6</p>
        <ul>
            <li>
                <p>item 2</p>
                <h1 id="heading-1-1">Heading 1</h1>
                <h2 id="heading-2-1">Heading 2</h2>
                <h3 id="heading-3-1">Heading 3</h3>
                <p>#### Heading 4</p>
                <p>##### Heading 5</p>
                <p>###### Heading 6</p>
            </li>
            <li>
                <p>item 3</p>
                <h1 id="heading-1-2">Heading 1</h1>
                <h2 id="heading-2-2">Heading 2</h2>
                <h3 id="heading-3-2">Heading 3</h3>
                <h4 id="heading-4">Heading 4</h4>
                <p>##### Heading 5</p>
                <p>###### Heading 6</p>
            </li>
        </ul>
    </li>
</ul>
.
````````````````````````````````


```````````````````````````````` example List Item Indent Handling: 28
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
Document[0, 116]
  BulletList[0, 116] isTight
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
    BulletListItem[100, 116] open:[100, 101, "*"] isTight
      Paragraph[110, 116]
        Text[110, 116] chars:[110, 116, "item 9"]
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


Whether blank lines are ignored and treated as if prefixed with block quote

```````````````````````````````` example Block quote parsing: 3
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

```````````````````````````````` example Block quote parsing: 4
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


Whether leading spaces are allowed before block quote marker

```````````````````````````````` example Block quote parsing: 5
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

```````````````````````````````` example Block quote parsing: 6
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


Whether block quotes can interrupt item paragraph

```````````````````````````````` example Block quote parsing: 7
* item 1
  > block quoted text

<!-- list break -->

1. item 1
   > block quoted text

.
<ul>
    <li>item 1 &gt; block quoted text</li>
</ul>
<!-- list break -->
<ol>
    <li>item 1 &gt; block quoted text</li>
</ol>
.
Document[0, 87]
  BulletList[0, 31] isTight
    BulletListItem[0, 31] open:[0, 1, "*"] isTight hadBlankLineAfter
      Paragraph[2, 31] isTrailingBlankLine
        Text[2, 8] chars:[2, 8, "item 1"]
        SoftLineBreak[8, 9]
        Text[11, 30] chars:[11, 30, "> blo …  text"]
  HtmlCommentBlock[32, 52]
  OrderedList[53, 86] isTight delimiter:'.'
    OrderedListItem[53, 86] open:[53, 55, "1."] isTight hadBlankLineAfter
      Paragraph[56, 86] isTrailingBlankLine
        Text[56, 62] chars:[56, 62, "item 1"]
        SoftLineBreak[62, 63]
        Text[66, 85] chars:[66, 85, "> blo …  text"]
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


Whether block quotes with leading space can interrupt item paragraphs

```````````````````````````````` example Block quote parsing: 9
* item 1
   > block quoted text

<!-- list break -->

1. item 1
    > block quoted text

.
<ul>
    <li>item 1 &gt; block quoted text</li>
</ul>
<!-- list break -->
<ol>
    <li>item 1 &gt; block quoted text</li>
</ol>
.
Document[0, 89]
  BulletList[0, 32] isTight
    BulletListItem[0, 32] open:[0, 1, "*"] isTight hadBlankLineAfter
      Paragraph[2, 32] isTrailingBlankLine
        Text[2, 8] chars:[2, 8, "item 1"]
        SoftLineBreak[8, 9]
        Text[12, 31] chars:[12, 31, "> blo …  text"]
  HtmlCommentBlock[33, 53]
  OrderedList[54, 88] isTight delimiter:'.'
    OrderedListItem[54, 88] open:[54, 56, "1."] isTight hadBlankLineAfter
      Paragraph[57, 88] isTrailingBlankLine
        Text[57, 63] chars:[57, 63, "item 1"]
        SoftLineBreak[63, 64]
        Text[68, 87] chars:[68, 87, "> blo …  text"]
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


Whether block quotes without trailing space can interrupt item paragraphs

```````````````````````````````` example Block quote parsing: 11
* item 1
  >block quoted text

<!-- list break -->

1. item 1
   >block quoted text

.
<ul>
    <li>item 1 &gt;block quoted text</li>
</ul>
<!-- list break -->
<ol>
    <li>item 1 &gt;block quoted text</li>
</ol>
.
Document[0, 85]
  BulletList[0, 30] isTight
    BulletListItem[0, 30] open:[0, 1, "*"] isTight hadBlankLineAfter
      Paragraph[2, 30] isTrailingBlankLine
        Text[2, 8] chars:[2, 8, "item 1"]
        SoftLineBreak[8, 9]
        Text[11, 29] chars:[11, 29, ">bloc …  text"]
  HtmlCommentBlock[31, 51]
  OrderedList[52, 84] isTight delimiter:'.'
    OrderedListItem[52, 84] open:[52, 54, "1."] isTight hadBlankLineAfter
      Paragraph[55, 84] isTrailingBlankLine
        Text[55, 61] chars:[55, 61, "item 1"]
        SoftLineBreak[61, 62]
        Text[65, 83] chars:[65, 83, ">bloc …  text"]
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


```````````````````````````````` example Block quote parsing: 13
- item 1
  - sub-item 1
    1. sub-sub-item 1
    1. sub-sub-item 2
.
<ul>
    <li>item 1
        <ul>
            <li>sub-item 1
                <ol>
                    <li>sub-sub-item 1</li>
                    <li>sub-sub-item 2</li>
                </ol>
            </li>
        </ul>
    </li>
</ul>
.
Document[0, 67]
  BulletList[0, 67] isTight
    BulletListItem[0, 67] open:[0, 1, "-"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      BulletList[11, 67] isTight
        BulletListItem[11, 67] open:[11, 12, "-"] isTight
          Paragraph[13, 24]
            Text[13, 23] chars:[13, 23, "sub-item 1"]
          OrderedList[28, 67] isTight delimiter:'.'
            OrderedListItem[28, 46] open:[28, 30, "1."] isTight
              Paragraph[31, 46]
                Text[31, 45] chars:[31, 45, "sub-s … tem 1"]
            OrderedListItem[50, 67] open:[50, 52, "1."] isTight
              Paragraph[53, 67]
                Text[53, 67] chars:[53, 67, "sub-s … tem 2"]
````````````````````````````````


Test to make sure content indented deeply nested lists process correctly

```````````````````````````````` example Block quote parsing: 14
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
            <li>item 2
                <ul>
                    <li>item 3
                        <ul>
                            <li>item 4
                                <ul>
                                    <li>item 5
                                        <ul>
                                            <li>item 6
                                                <ul>
                                                    <li>item 7
                                                        <ul>
                                                            <li>item 8
                                                                <ul>
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
        BulletListItem[11, 153] open:[11, 12, "-"] isTight
          Paragraph[13, 20]
            Text[13, 19] chars:[13, 19, "item 2"]
          BulletList[24, 153] isTight
            BulletListItem[24, 153] open:[24, 25, "-"] isTight
              Paragraph[26, 33]
                Text[26, 32] chars:[26, 32, "item 3"]
              BulletList[39, 153] isTight
                BulletListItem[39, 153] open:[39, 40, "-"] isTight
                  Paragraph[41, 48]
                    Text[41, 47] chars:[41, 47, "item 4"]
                  BulletList[56, 153] isTight
                    BulletListItem[56, 153] open:[56, 57, "-"] isTight
                      Paragraph[58, 65]
                        Text[58, 64] chars:[58, 64, "item 5"]
                      BulletList[75, 153] isTight
                        BulletListItem[75, 153] open:[75, 76, "-"] isTight
                          Paragraph[77, 84]
                            Text[77, 83] chars:[77, 83, "item 6"]
                          BulletList[96, 153] isTight
                            BulletListItem[96, 153] open:[96, 97, "-"] isTight
                              Paragraph[98, 105]
                                Text[98, 104] chars:[98, 104, "item 7"]
                              BulletList[119, 153] isTight
                                BulletListItem[119, 153] open:[119, 120, "-"] isTight
                                  Paragraph[121, 128]
                                    Text[121, 127] chars:[121, 127, "item 8"]
                                  BulletList[144, 153] isTight
                                    BulletListItem[144, 153] open:[144, 145, "-"] isTight hadBlankLineAfter
                                      Paragraph[146, 153] isTrailingBlankLine
                                        Text[146, 152] chars:[146, 152, "item 9"]
````````````````````````````````


```````````````````````````````` example Block quote parsing: 15
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
                            <li>item 4
                                <ol>
                                    <li>item 5
                                        <ol>
                                            <li>item 6
                                                <ol>
                                                    <li>item 7
                                                        <ol>
                                                            <li>item 8
                                                                <ol>
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
                OrderedListItem[48, 198] open:[48, 50, "1."] isTight
                  Paragraph[51, 58]
                    Text[51, 57] chars:[51, 57, "item 4"]
                  OrderedList[70, 198] isTight delimiter:'.'
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
                                OrderedListItem[154, 198] open:[154, 156, "1."] isTight
                                  Paragraph[157, 164]
                                    Text[157, 163] chars:[157, 163, "item 8"]
                                  OrderedList[188, 198] isTight delimiter:'.'
                                    OrderedListItem[188, 198] open:[188, 190, "1."] isTight hadBlankLineAfter
                                      Paragraph[191, 198] isTrailingBlankLine
                                        Text[191, 197] chars:[191, 197, "item 9"]
````````````````````````````````


## Issues

# 51, GitHub compatibility, setext headings in list items not properly parsed if marker not

indented

```````````````````````````````` example(51, GitHub compatibility, setext headings in list items not properly parsed if marker not: 1) options(FAIL)
* baz

 Second level heading
---------------------
.
<ul>
    <li>
        <p>baz</p>
        <h2 id="second-level-heading">Second level heading</h2>
    </li>
</ul>
.
Document[0, 52]
  BulletList[0, 51] isTight
    BulletListItem[0, 51] open:[0, 1, "*"] isLoose hadBlankLineAfter
      Paragraph[2, 6] isTrailingBlankLine
        Text[2, 5] chars:[2, 5, "baz"]
      Heading[8, 51] text:[8, 28, "Second level heading"] textClose:[30, 51, "---------------------"]
        Text[8, 28] chars:[8, 28, "Secon … ading"]
````````````````````````````````


options for plain text rendering

```````````````````````````````` example(51, GitHub compatibility, setext headings in list items not properly parsed if marker not: 2) options(no-loose-non-list-children)
* Enable Auto Indent Lines after move line/selection up or down actions to have them indented
  automatically.
* Use Smart Paste to eliminate case change and prefix edits when pasting identifiers. MIA will
  match case and style of identifier at destination when you paste, undo to get results before
  MIA adjusted them. Copy `myColumnData` and paste it over `DEFAULT_VALUE` to get `COLUMN_DATA`,
  reverse the order and get `myDefaultValue`. Works when pasting at the **beginning**, **end**
  and **middle** of identifiers.

    Supports: **camelCase**, **PascalCase**, **snake_case**, **SCREAMING_SNAKE_CASE**,
    **dash-case**, **dot.case**, **slash/case**

    Default prefixes: `my`, `our`, `is`, `get`, `set` to allow pasting over member fields,
    static fields, getters and setters.
* Enable Auto Line Selections and select full lines without loosing time or column position by
  moving the caret to the start of line when selecting or pasting. **Choose** whether you want
  to **paste full line** selections: **above** or **below** the current line regardless of the
  caret's column.
.
<ul>
    <li>Enable Auto Indent Lines after move line/selection up or down actions to have them indented automatically.</li>
    <li>Use Smart Paste to eliminate case change and prefix edits when pasting identifiers. MIA will match case and style of identifier at destination when you paste, undo to get results before MIA adjusted them. Copy <code>myColumnData</code> and paste it over <code>DEFAULT_VALUE</code> to get <code>COLUMN_DATA</code>, reverse the order and get <code>myDefaultValue</code>. Works when pasting at the <strong>beginning</strong>, <strong>end</strong> and <strong>middle</strong> of identifiers.
        <p>Supports: <strong>camelCase</strong>, <strong>PascalCase</strong>, <strong>snake_case</strong>, <strong>SCREAMING_SNAKE_CASE</strong>, <strong>dash-case</strong>, <strong>dot.case</strong>, <strong>slash/case</strong></p>
        <p>Default prefixes: <code>my</code>, <code>our</code>, <code>is</code>, <code>get</code>, <code>set</code> to allow pasting over member fields, static fields, getters and setters.</p>
    </li>
    <li>Enable Auto Line Selections and select full lines without loosing time or column position by moving the caret to the start of line when selecting or pasting. <strong>Choose</strong> whether you want to <strong>paste full line</strong> selections: <strong>above</strong> or <strong>below</strong> the current line regardless of the caret's column.</li>
</ul>
.
Document[0, 1096]
  BulletList[0, 1096] isTight
    BulletListItem[0, 111] open:[0, 1, "*"] isTight
      Paragraph[2, 111]
        Text[2, 93] chars:[2, 93, "Enabl … ented"]
        SoftLineBreak[93, 94]
        Text[96, 110] chars:[96, 110, "autom … ally."]
    BulletListItem[111, 794] open:[111, 112, "*"] isTight hadBlankLineAfter
      Paragraph[113, 526] isTrailingBlankLine
        Text[113, 205] chars:[113, 205, "Use S …  will"]
        SoftLineBreak[205, 206]
        Text[208, 300] chars:[208, 300, "match … efore"]
        SoftLineBreak[300, 301]
        Text[303, 327] chars:[303, 327, "MIA a … Copy "]
        Code[327, 341] textOpen:[327, 328, "`"] text:[328, 340, "myCol … umnData"] textClose:[340, 341, "`"]
          Text[328, 340] chars:[328, 340, "myCol … nData"]
        Text[341, 360] chars:[341, 360, " and  … over "]
        Code[360, 375] textOpen:[360, 361, "`"] text:[361, 374, "DEFAU … LT_VALUE"] textClose:[374, 375, "`"]
          Text[361, 374] chars:[361, 374, "DEFAU … VALUE"]
        Text[375, 383] chars:[375, 383, " to get "]
        Code[383, 396] textOpen:[383, 384, "`"] text:[384, 395, "COLUM … N_DATA"] textClose:[395, 396, "`"]
          Text[384, 395] chars:[384, 395, "COLUM … _DATA"]
        Text[396, 397] chars:[396, 397, ","]
        SoftLineBreak[397, 398]
        Text[400, 426] chars:[400, 426, "rever …  get "]
        Code[426, 442] textOpen:[426, 427, "`"] text:[427, 441, "myDef … aultValue"] textClose:[441, 442, "`"]
          Text[427, 441] chars:[427, 441, "myDef … Value"]
        Text[442, 470] chars:[442, 470, ". Wor …  the "]
        StrongEmphasis[470, 483] textOpen:[470, 472, "**"] text:[472, 481, "beginning"] textClose:[481, 483, "**"]
          Text[472, 481] chars:[472, 481, "beginning"]
        Text[483, 485] chars:[483, 485, ", "]
        StrongEmphasis[485, 492] textOpen:[485, 487, "**"] text:[487, 490, "end"] textClose:[490, 492, "**"]
          Text[487, 490] chars:[487, 490, "end"]
        SoftLineBreak[492, 493]
        Text[495, 499] chars:[495, 499, "and "]
        StrongEmphasis[499, 509] textOpen:[499, 501, "**"] text:[501, 507, "middle"] textClose:[507, 509, "**"]
          Text[501, 507] chars:[501, 507, "middle"]
        Text[509, 525] chars:[509, 525, " of i … iers."]
      Paragraph[531, 662] isTrailingBlankLine
        Text[531, 541] chars:[531, 541, "Supports: "]
        StrongEmphasis[541, 554] textOpen:[541, 543, "**"] text:[543, 552, "camelCase"] textClose:[552, 554, "**"]
          Text[543, 552] chars:[543, 552, "camelCase"]
        Text[554, 556] chars:[554, 556, ", "]
        StrongEmphasis[556, 570] textOpen:[556, 558, "**"] text:[558, 568, "PascalCase"] textClose:[568, 570, "**"]
          Text[558, 568] chars:[558, 568, "PascalCase"]
        Text[570, 572] chars:[570, 572, ", "]
        StrongEmphasis[572, 586] textOpen:[572, 574, "**"] text:[574, 584, "snake_case"] textClose:[584, 586, "**"]
          Text[574, 584] chars:[574, 584, "snake_case"]
        Text[586, 588] chars:[586, 588, ", "]
        StrongEmphasis[588, 612] textOpen:[588, 590, "**"] text:[590, 610, "SCREAMING_SNAKE_CASE"] textClose:[610, 612, "**"]
          Text[590, 610] chars:[590, 610, "SCREA … _CASE"]
        Text[612, 613] chars:[612, 613, ","]
        SoftLineBreak[613, 614]
        StrongEmphasis[618, 631] textOpen:[618, 620, "**"] text:[620, 629, "dash-case"] textClose:[629, 631, "**"]
          Text[620, 629] chars:[620, 629, "dash-case"]
        Text[631, 633] chars:[631, 633, ", "]
        StrongEmphasis[633, 645] textOpen:[633, 635, "**"] text:[635, 643, "dot.case"] textClose:[643, 645, "**"]
          Text[635, 643] chars:[635, 643, "dot.case"]
        Text[645, 647] chars:[645, 647, ", "]
        StrongEmphasis[647, 661] textOpen:[647, 649, "**"] text:[649, 659, "slash/case"] textClose:[659, 661, "**"]
          Text[649, 659] chars:[649, 659, "slash/case"]
      Paragraph[667, 794]
        Text[667, 685] chars:[667, 685, "Defau … xes: "]
        Code[685, 689] textOpen:[685, 686, "`"] text:[686, 688, "my"] textClose:[688, 689, "`"]
          Text[686, 688] chars:[686, 688, "my"]
        Text[689, 691] chars:[689, 691, ", "]
        Code[691, 696] textOpen:[691, 692, "`"] text:[692, 695, "our"] textClose:[695, 696, "`"]
          Text[692, 695] chars:[692, 695, "our"]
        Text[696, 698] chars:[696, 698, ", "]
        Code[698, 702] textOpen:[698, 699, "`"] text:[699, 701, "is"] textClose:[701, 702, "`"]
          Text[699, 701] chars:[699, 701, "is"]
        Text[702, 704] chars:[702, 704, ", "]
        Code[704, 709] textOpen:[704, 705, "`"] text:[705, 708, "get"] textClose:[708, 709, "`"]
          Text[705, 708] chars:[705, 708, "get"]
        Text[709, 711] chars:[709, 711, ", "]
        Code[711, 716] textOpen:[711, 712, "`"] text:[712, 715, "set"] textClose:[715, 716, "`"]
          Text[712, 715] chars:[712, 715, "set"]
        Text[716, 753] chars:[716, 753, " to a … elds,"]
        SoftLineBreak[753, 754]
        Text[758, 793] chars:[758, 793, "stati … ters."]
    BulletListItem[794, 1096] open:[794, 795, "*"] isTight
      Paragraph[796, 1096]
        Text[796, 888] chars:[796, 888, "Enabl … on by"]
        SoftLineBreak[888, 889]
        Text[891, 956] chars:[891, 956, "movin … ing. "]
        StrongEmphasis[956, 966] textOpen:[956, 958, "**"] text:[958, 964, "Choose"] textClose:[964, 966, "**"]
          Text[958, 964] chars:[958, 964, "Choose"]
        Text[966, 983] chars:[966, 983, " whet …  want"]
        SoftLineBreak[983, 984]
        Text[986, 989] chars:[986, 989, "to "]
        StrongEmphasis[989, 1008] textOpen:[989, 991, "**"] text:[991, 1006, "paste full line"] textClose:[1006, 1008, "**"]
          Text[991, 1006] chars:[991, 1006, "paste …  line"]
        Text[1008, 1021] chars:[1008, 1021, " sele … ons: "]
        StrongEmphasis[1021, 1030] textOpen:[1021, 1023, "**"] text:[1023, 1028, "above"] textClose:[1028, 1030, "**"]
          Text[1023, 1028] chars:[1023, 1028, "above"]
        Text[1030, 1034] chars:[1030, 1034, " or "]
        StrongEmphasis[1034, 1043] textOpen:[1034, 1036, "**"] text:[1036, 1041, "below"] textClose:[1041, 1043, "**"]
          Text[1036, 1041] chars:[1036, 1041, "below"]
        Text[1043, 1078] chars:[1043, 1078, " the  … f the"]
        SoftLineBreak[1078, 1079]
        Text[1081, 1096] chars:[1081, 1096, "caret … lumn."]
````````````````````````````````


## Issue 66

# 66, GitHub Doc profile incorrect parsing of following markdown

Heading in fenced code interrupts fenced code

```````````````````````````````` example 66, GitHub Doc profile incorrect parsing of following markdown: 1
* list item
```
# test
# test 2

# some other comment

```

.
<ul>
    <li>list item</li>
</ul>
<pre><code># test
# test 2

# some other comment

</code></pre>
.
Document[0, 60]
  BulletList[0, 12] isTight
    BulletListItem[0, 12] open:[0, 1, "*"] isTight
      Paragraph[2, 12]
        Text[2, 11] chars:[2, 11, "list item"]
  FencedCodeBlock[12, 58] open:[12, 15, "```"] content:[16, 55] lines[5] close:[55, 58, "```"]
    Text[16, 55] chars:[16, 55, "# tes … ent\n\n"]
````````````````````````````````


## Issue 73

# 73, Can't nest code blocks in ordered list

```````````````````````````````` example 73, Can't nest code blocks in ordered list: 1
* list item

  ```
  fenced code
   more code
  ```
.
<ul>
    <li>
        <p>list item</p>
        <pre><code>fenced code
 more code
</code></pre>
    </li>
</ul>
.
Document[0, 51]
  BulletList[0, 51] isTight
    BulletListItem[0, 51] open:[0, 1, "*"] isLoose hadBlankLineAfter
      Paragraph[2, 12] isTrailingBlankLine
        Text[2, 11] chars:[2, 11, "list item"]
      FencedCodeBlock[15, 51] open:[15, 18, "```"] content:[21, 46] lines[2] close:[48, 51, "```"]
        Text[21, 46] chars:[21, 46, "fence … code\n"]
````````````````````````````````


```````````````````````````````` example 73, Can't nest code blocks in ordered list: 2
1. list item

   ```
   fenced code
    more code
   ```
.
<ol>
    <li>
        <p>list item</p>
        <pre><code>fenced code
 more code
</code></pre>
    </li>
</ol>
.
Document[0, 56]
  OrderedList[0, 56] isTight delimiter:'.'
    OrderedListItem[0, 56] open:[0, 2, "1."] isLoose hadBlankLineAfter
      Paragraph[3, 13] isTrailingBlankLine
        Text[3, 12] chars:[3, 12, "list item"]
      FencedCodeBlock[17, 56] open:[17, 20, "```"] content:[24, 50] lines[2] close:[53, 56, "```"]
        Text[24, 50] chars:[24, 50, "fence … code\n"]
````````````````````````````````


## Issue MN-567

Heading Ids preserve `_`

```````````````````````````````` example Issue MN-567: 1
# heading_id

.
<h1 id="heading_id">heading_id</h1>
.
Document[0, 14]
  Heading[0, 12] textOpen:[0, 1, "#"] text:[2, 12, "heading_id"]
    Text[2, 12] chars:[2, 12, "heading_id"]
````````````````````````````````


## Issue MN-xxx

GitHub does not convert non-ascii heading text to lowercase

```````````````````````````````` example Issue MN-xxx: 1
## Тест Заголовок
.
<h2 id="Тест-Заголовок">Тест Заголовок</h2>
.
Document[0, 17]
  Heading[0, 17] textOpen:[0, 2, "##"] text:[3, 17, "Тест Заголовок"]
    Text[3, 17] chars:[3, 17, "Тест  … ловок"]
````````````````````````````````


