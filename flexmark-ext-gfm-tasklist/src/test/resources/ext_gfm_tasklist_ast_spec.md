---
title: Gfm TaskList Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-18'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Gfm Task List Core

not task lis items

```````````````````````````````` example(Gfm Task List Core: 1) options(marker-space)
- [ ]abc
.
<ul>
  <li>[ ]abc</li>
</ul>
.
Document[0, 8]
  BulletList[0, 8] isTight
    BulletListItem[0, 8] open:[0, 1, "-"] isTight
      Paragraph[2, 8]
        LinkRef[2, 5] referenceOpen:[2, 3, "["] reference:[4, 4] referenceClose:[4, 5, "]"]
          Text[4, 4]
        Text[5, 8] chars:[5, 8, "abc"]
````````````````````````````````


task list item

```````````````````````````````` example Gfm Task List Core: 2
- [ ]abc
.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;abc</li>
</ul>
.
Document[0, 8]
  BulletList[0, 8] isTight
    TaskListItem[0, 8] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[5, 8]
        Text[5, 8] chars:[5, 8, "abc"]
````````````````````````````````


not task lis items

```````````````````````````````` example(Gfm Task List Core: 3) options(marker-space)
- []
- [x ]
- [ x]
- [ x ]
- [a]
- [ ]abc
- [x]abc
- [x]abc
- a[ ] task
- b[x] task
- c[x] task
.
<ul>
  <li>[]</li>
  <li>[x ]</li>
  <li>[ x]</li>
  <li>[ x ]</li>
  <li>[a]</li>
  <li>[ ]abc</li>
  <li>[x]abc</li>
  <li>[x]abc</li>
  <li>a[ ] task</li>
  <li>b[x] task</li>
  <li>c[x] task</li>
</ul>
.
Document[0, 95]
  BulletList[0, 95] isTight
    BulletListItem[0, 5] open:[0, 1, "-"] isTight
      Paragraph[2, 5]
        LinkRef[2, 4] referenceOpen:[2, 3, "["] reference:[3, 3] referenceClose:[3, 4, "]"]
    BulletListItem[5, 12] open:[5, 6, "-"] isTight
      Paragraph[7, 12]
        LinkRef[7, 11] referenceOpen:[7, 8, "["] reference:[8, 9, "x"] referenceClose:[10, 11, "]"]
          Text[8, 9] chars:[8, 9, "x"]
    BulletListItem[12, 19] open:[12, 13, "-"] isTight
      Paragraph[14, 19]
        LinkRef[14, 18] referenceOpen:[14, 15, "["] reference:[16, 17, "x"] referenceClose:[17, 18, "]"]
          Text[16, 17] chars:[16, 17, "x"]
    BulletListItem[19, 27] open:[19, 20, "-"] isTight
      Paragraph[21, 27]
        LinkRef[21, 26] referenceOpen:[21, 22, "["] reference:[23, 24, "x"] referenceClose:[25, 26, "]"]
          Text[23, 24] chars:[23, 24, "x"]
    BulletListItem[27, 33] open:[27, 28, "-"] isTight
      Paragraph[29, 33]
        LinkRef[29, 32] referenceOpen:[29, 30, "["] reference:[30, 31, "a"] referenceClose:[31, 32, "]"]
          Text[30, 31] chars:[30, 31, "a"]
    BulletListItem[33, 42] open:[33, 34, "-"] isTight
      Paragraph[35, 42]
        LinkRef[35, 38] referenceOpen:[35, 36, "["] reference:[37, 37] referenceClose:[37, 38, "]"]
          Text[37, 37]
        Text[38, 41] chars:[38, 41, "abc"]
    BulletListItem[42, 51] open:[42, 43, "-"] isTight
      Paragraph[44, 51]
        LinkRef[44, 47] referenceOpen:[44, 45, "["] reference:[45, 46, "x"] referenceClose:[46, 47, "]"]
          Text[45, 46] chars:[45, 46, "x"]
        Text[47, 50] chars:[47, 50, "abc"]
    BulletListItem[51, 60] open:[51, 52, "-"] isTight
      Paragraph[53, 60]
        LinkRef[53, 56] referenceOpen:[53, 54, "["] reference:[54, 55, "x"] referenceClose:[55, 56, "]"]
          Text[54, 55] chars:[54, 55, "x"]
        Text[56, 59] chars:[56, 59, "abc"]
    BulletListItem[60, 72] open:[60, 61, "-"] isTight
      Paragraph[62, 72]
        Text[62, 63] chars:[62, 63, "a"]
        LinkRef[63, 66] referenceOpen:[63, 64, "["] reference:[65, 65] referenceClose:[65, 66, "]"]
          Text[65, 65]
        Text[66, 71] chars:[66, 71, " task"]
    BulletListItem[72, 84] open:[72, 73, "-"] isTight
      Paragraph[74, 84]
        Text[74, 75] chars:[74, 75, "b"]
        LinkRef[75, 78] referenceOpen:[75, 76, "["] reference:[76, 77, "x"] referenceClose:[77, 78, "]"]
          Text[76, 77] chars:[76, 77, "x"]
        Text[78, 83] chars:[78, 83, " task"]
    BulletListItem[84, 95] open:[84, 85, "-"] isTight
      Paragraph[86, 95]
        Text[86, 87] chars:[86, 87, "c"]
        LinkRef[87, 90] referenceOpen:[87, 88, "["] reference:[88, 89, "x"] referenceClose:[89, 90, "]"]
          Text[88, 89] chars:[88, 89, "x"]
        Text[90, 95] chars:[90, 95, " task"]
````````````````````````````````


empty task list items

```````````````````````````````` example Gfm Task List Core: 4
- [ ]
-  [X]
-    [x]

* [ ]

* [x]

* [X]

+ [ ]
+ [x]
+ [X]
.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;</li>
</ul>
<ul>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;</p>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;</p>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;</p>
  </li>
</ul>
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;</li>
</ul>
.
Document[0, 61]
  BulletList[0, 21] isTight
    TaskListItem[0, 5] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
    TaskListItem[6, 12] open:[6, 7, "-"] openSuffix:[9, 12, "[X]"] isTight isDone
    TaskListItem[13, 21] open:[13, 14, "-"] openSuffix:[18, 21, "[x]"] isTight hadBlankLineAfter isDone
  BulletList[23, 42] isLoose
    TaskListItem[23, 28] open:[23, 24, "*"] openSuffix:[25, 28, "[ ]"] isLoose hadBlankLineAfter isNotDone
    TaskListItem[30, 35] open:[30, 31, "*"] openSuffix:[32, 35, "[x]"] isLoose hadBlankLineAfter isDone
    TaskListItem[37, 42] open:[37, 38, "*"] openSuffix:[39, 42, "[X]"] isLoose hadBlankLineAfter isDone
  BulletList[44, 61] isTight
    TaskListItem[44, 49] open:[44, 45, "+"] openSuffix:[46, 49, "[ ]"] isTight isNotDone
    TaskListItem[50, 55] open:[50, 51, "+"] openSuffix:[52, 55, "[x]"] isTight isDone
    TaskListItem[56, 61] open:[56, 57, "+"] openSuffix:[58, 61, "[X]"] isTight isDone
````````````````````````````````


non empty task list items

```````````````````````````````` example Gfm Task List Core: 5
- [ ] task
- [X] task
- [x] task

* [ ] task

* [x] task

* [X] task

+ [ ] task
+ [x] task
+ [X] task
.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</li>
</ul>
<ul>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</p>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</p>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</p>
  </li>
</ul>
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</li>
</ul>
.
Document[0, 102]
  BulletList[0, 33] isTight
    TaskListItem[0, 11] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 22] open:[11, 12, "-"] openSuffix:[13, 16, "[X]"] isTight isDone
      Paragraph[17, 22]
        Text[17, 21] chars:[17, 21, "task"]
    TaskListItem[22, 33] open:[22, 23, "-"] openSuffix:[24, 27, "[x]"] isTight hadBlankLineAfter isDone
      Paragraph[28, 33] isTrailingBlankLine
        Text[28, 32] chars:[28, 32, "task"]
  BulletList[34, 69] isLoose
    TaskListItem[34, 45] open:[34, 35, "*"] openSuffix:[36, 39, "[ ]"] isLoose hadBlankLineAfter isNotDone
      Paragraph[40, 45] isTrailingBlankLine
        Text[40, 44] chars:[40, 44, "task"]
    TaskListItem[46, 57] open:[46, 47, "*"] openSuffix:[48, 51, "[x]"] isLoose hadBlankLineAfter isDone
      Paragraph[52, 57] isTrailingBlankLine
        Text[52, 56] chars:[52, 56, "task"]
    TaskListItem[58, 69] open:[58, 59, "*"] openSuffix:[60, 63, "[X]"] isLoose hadBlankLineAfter isDone
      Paragraph[64, 69] isTrailingBlankLine
        Text[64, 68] chars:[64, 68, "task"]
  BulletList[70, 102] isTight
    TaskListItem[70, 81] open:[70, 71, "+"] openSuffix:[72, 75, "[ ]"] isTight isNotDone
      Paragraph[76, 81]
        Text[76, 80] chars:[76, 80, "task"]
    TaskListItem[81, 92] open:[81, 82, "+"] openSuffix:[83, 86, "[x]"] isTight isDone
      Paragraph[87, 92]
        Text[87, 91] chars:[87, 91, "task"]
    TaskListItem[92, 102] open:[92, 93, "+"] openSuffix:[94, 97, "[X]"] isTight isDone
      Paragraph[98, 102]
        Text[98, 102] chars:[98, 102, "task"]
````````````````````````````````


non empty task list items with inlines

```````````````````````````````` example Gfm Task List Core: 6
- [ ] task **emphasis**
- [X] task **emphasis**
- [x] task **emphasis**

* [ ] task **emphasis**

* [x] task **emphasis**

* [X] task **emphasis**

+ [ ] task **emphasis**
+ [x] task **emphasis**
+ [X] task **emphasis**
.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task <strong>emphasis</strong></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task <strong>emphasis</strong></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task <strong>emphasis</strong></li>
</ul>
<ul>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task <strong>emphasis</strong></p>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task <strong>emphasis</strong></p>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task <strong>emphasis</strong></p>
  </li>
</ul>
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task <strong>emphasis</strong></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task <strong>emphasis</strong></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task <strong>emphasis</strong></li>
</ul>
.
Document[0, 219]
  BulletList[0, 72] isTight
    TaskListItem[0, 24] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 24]
        Text[6, 11] chars:[6, 11, "task "]
        StrongEmphasis[11, 23] textOpen:[11, 13, "**"] text:[13, 21, "emphasis"] textClose:[21, 23, "**"]
          Text[13, 21] chars:[13, 21, "emphasis"]
    TaskListItem[24, 48] open:[24, 25, "-"] openSuffix:[26, 29, "[X]"] isTight isDone
      Paragraph[30, 48]
        Text[30, 35] chars:[30, 35, "task "]
        StrongEmphasis[35, 47] textOpen:[35, 37, "**"] text:[37, 45, "emphasis"] textClose:[45, 47, "**"]
          Text[37, 45] chars:[37, 45, "emphasis"]
    TaskListItem[48, 72] open:[48, 49, "-"] openSuffix:[50, 53, "[x]"] isTight hadBlankLineAfter isDone
      Paragraph[54, 72] isTrailingBlankLine
        Text[54, 59] chars:[54, 59, "task "]
        StrongEmphasis[59, 71] textOpen:[59, 61, "**"] text:[61, 69, "emphasis"] textClose:[69, 71, "**"]
          Text[61, 69] chars:[61, 69, "emphasis"]
  BulletList[73, 147] isLoose
    TaskListItem[73, 97] open:[73, 74, "*"] openSuffix:[75, 78, "[ ]"] isLoose hadBlankLineAfter isNotDone
      Paragraph[79, 97] isTrailingBlankLine
        Text[79, 84] chars:[79, 84, "task "]
        StrongEmphasis[84, 96] textOpen:[84, 86, "**"] text:[86, 94, "emphasis"] textClose:[94, 96, "**"]
          Text[86, 94] chars:[86, 94, "emphasis"]
    TaskListItem[98, 122] open:[98, 99, "*"] openSuffix:[100, 103, "[x]"] isLoose hadBlankLineAfter isDone
      Paragraph[104, 122] isTrailingBlankLine
        Text[104, 109] chars:[104, 109, "task "]
        StrongEmphasis[109, 121] textOpen:[109, 111, "**"] text:[111, 119, "emphasis"] textClose:[119, 121, "**"]
          Text[111, 119] chars:[111, 119, "emphasis"]
    TaskListItem[123, 147] open:[123, 124, "*"] openSuffix:[125, 128, "[X]"] isLoose hadBlankLineAfter isDone
      Paragraph[129, 147] isTrailingBlankLine
        Text[129, 134] chars:[129, 134, "task "]
        StrongEmphasis[134, 146] textOpen:[134, 136, "**"] text:[136, 144, "emphasis"] textClose:[144, 146, "**"]
          Text[136, 144] chars:[136, 144, "emphasis"]
  BulletList[148, 219] isTight
    TaskListItem[148, 172] open:[148, 149, "+"] openSuffix:[150, 153, "[ ]"] isTight isNotDone
      Paragraph[154, 172]
        Text[154, 159] chars:[154, 159, "task "]
        StrongEmphasis[159, 171] textOpen:[159, 161, "**"] text:[161, 169, "emphasis"] textClose:[169, 171, "**"]
          Text[161, 169] chars:[161, 169, "emphasis"]
    TaskListItem[172, 196] open:[172, 173, "+"] openSuffix:[174, 177, "[x]"] isTight isDone
      Paragraph[178, 196]
        Text[178, 183] chars:[178, 183, "task "]
        StrongEmphasis[183, 195] textOpen:[183, 185, "**"] text:[185, 193, "emphasis"] textClose:[193, 195, "**"]
          Text[185, 193] chars:[185, 193, "emphasis"]
    TaskListItem[196, 219] open:[196, 197, "+"] openSuffix:[198, 201, "[X]"] isTight isDone
      Paragraph[202, 219]
        Text[202, 207] chars:[202, 207, "task "]
        StrongEmphasis[207, 219] textOpen:[207, 209, "**"] text:[209, 217, "emphasis"] textClose:[217, 219, "**"]
          Text[209, 217] chars:[209, 217, "emphasis"]
````````````````````````````````


nested task items

```````````````````````````````` example Gfm Task List Core: 7
- non-task item
  - [ ] task item
  
- [ ] task item
  - [ ] task item
  
- [ ] task item
  - non task item
.
<ul>
  <li>
    <p>non-task item</p>
    <ul>
      <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task item</li>
    </ul>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task item
    <ul>
      <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task item</li>
    </ul>
    </p>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task item
    <ul>
      <li>non task item</li>
    </ul>
    </p>
  </li>
</ul>
.
Document[0, 107]
  BulletList[0, 107] isLoose
    BulletListItem[0, 34] open:[0, 1, "-"] isLoose
      Paragraph[2, 16]
        Text[2, 15] chars:[2, 15, "non-t …  item"]
      BulletList[18, 34] isTight
        TaskListItem[18, 34] open:[18, 19, "-"] openSuffix:[20, 23, "[ ]"] isTight hadBlankLineAfter isNotDone
          Paragraph[24, 34] isTrailingBlankLine
            Text[24, 33] chars:[24, 33, "task item"]
    TaskListItem[37, 71] open:[37, 38, "-"] openSuffix:[39, 42, "[ ]"] isLoose isNotDone
      Paragraph[43, 53]
        Text[43, 52] chars:[43, 52, "task item"]
      BulletList[55, 71] isTight
        TaskListItem[55, 71] open:[55, 56, "-"] openSuffix:[57, 60, "[ ]"] isTight hadBlankLineAfter isNotDone
          Paragraph[61, 71] isTrailingBlankLine
            Text[61, 70] chars:[61, 70, "task item"]
    TaskListItem[74, 107] open:[74, 75, "-"] openSuffix:[76, 79, "[ ]"] isLoose isNotDone
      Paragraph[80, 90]
        Text[80, 89] chars:[80, 89, "task item"]
      BulletList[92, 107] isTight
        BulletListItem[92, 107] open:[92, 93, "-"] isTight
          Paragraph[94, 107]
            Text[94, 107] chars:[94, 107, "non t …  item"]
````````````````````````````````


## Gfm Task List Options

task list item class

```````````````````````````````` example(Gfm Task List Options: 1) options(item-class)
- [ ] task
- [x] task
.
<ul>
  <li><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</li>
  <li><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</li>
</ul>
.
Document[0, 21]
  BulletList[0, 21] isTight
    TaskListItem[0, 11] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 21] open:[11, 12, "-"] openSuffix:[13, 16, "[x]"] isTight isDone
      Paragraph[17, 21]
        Text[17, 21] chars:[17, 21, "task"]
````````````````````````````````


task list item class on loose list

```````````````````````````````` example(Gfm Task List Options: 2) options(item-class)
- [ ] task

- [x] task
.
<ul>
  <li>
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</p>
  </li>
  <li>
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</p>
  </li>
</ul>
.
Document[0, 22]
  BulletList[0, 22] isLoose
    TaskListItem[0, 11] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isLoose hadBlankLineAfter isNotDone
      Paragraph[6, 11] isTrailingBlankLine
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[12, 22] open:[12, 13, "-"] openSuffix:[14, 17, "[x]"] isLoose isDone
      Paragraph[18, 22]
        Text[18, 22] chars:[18, 22, "task"]
````````````````````````````````


loose task list item class

```````````````````````````````` example(Gfm Task List Options: 3) options(loose-class)
- [ ] task
- [x] task
.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</li>
</ul>
.
Document[0, 21]
  BulletList[0, 21] isTight
    TaskListItem[0, 11] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 21] open:[11, 12, "-"] openSuffix:[13, 16, "[x]"] isTight isDone
      Paragraph[17, 21]
        Text[17, 21] chars:[17, 21, "task"]
````````````````````````````````


loose task list item class on loose list

```````````````````````````````` example(Gfm Task List Options: 4) options(loose-class)
- [ ] task

- [x] task
.
<ul>
  <li>
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</p>
  </li>
  <li>
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</p>
  </li>
</ul>
.
Document[0, 22]
  BulletList[0, 22] isLoose
    TaskListItem[0, 11] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isLoose hadBlankLineAfter isNotDone
      Paragraph[6, 11] isTrailingBlankLine
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[12, 22] open:[12, 13, "-"] openSuffix:[14, 17, "[x]"] isLoose isDone
      Paragraph[18, 22]
        Text[18, 22] chars:[18, 22, "task"]
````````````````````````````````


loose task list item class

```````````````````````````````` example(Gfm Task List Options: 5) options(loose-class, open-item-class, closed-item-class)
- [ ] task
- [x] task
.
<ul>
  <li class="task-list-item open-task"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</li>
  <li class="task-list-item closed-task"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</li>
</ul>
.
Document[0, 21]
  BulletList[0, 21] isTight
    TaskListItem[0, 11] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 21] open:[11, 12, "-"] openSuffix:[13, 16, "[x]"] isTight isDone
      Paragraph[17, 21]
        Text[17, 21] chars:[17, 21, "task"]
````````````````````````````````


loose task list item class on loose list

```````````````````````````````` example(Gfm Task List Options: 6) options(loose-class, open-item-class, closed-item-class)
- [ ] task

- [x] task
.
<ul>
  <li class="open-task">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</p>
  </li>
  <li class="closed-task">
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</p>
  </li>
</ul>
.
Document[0, 22]
  BulletList[0, 22] isLoose
    TaskListItem[0, 11] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isLoose hadBlankLineAfter isNotDone
      Paragraph[6, 11] isTrailingBlankLine
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[12, 22] open:[12, 13, "-"] openSuffix:[14, 17, "[x]"] isLoose isDone
      Paragraph[18, 22]
        Text[18, 22] chars:[18, 22, "task"]
````````````````````````````````


loose task list item class

```````````````````````````````` example(Gfm Task List Options: 7) options(open-item-class, closed-item-class)
- [ ] task
- [x] task
.
<ul>
  <li class="task-list-item open-task"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</li>
  <li class="task-list-item closed-task"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</li>
</ul>
.
Document[0, 21]
  BulletList[0, 21] isTight
    TaskListItem[0, 11] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 21] open:[11, 12, "-"] openSuffix:[13, 16, "[x]"] isTight isDone
      Paragraph[17, 21]
        Text[17, 21] chars:[17, 21, "task"]
````````````````````````````````


loose task list item class on loose list

```````````````````````````````` example(Gfm Task List Options: 8) options(open-item-class, closed-item-class)
- [ ] task

- [x] task
.
<ul>
  <li class="task-list-item open-task">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</p>
  </li>
  <li class="task-list-item closed-task">
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</p>
  </li>
</ul>
.
Document[0, 22]
  BulletList[0, 22] isLoose
    TaskListItem[0, 11] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isLoose hadBlankLineAfter isNotDone
      Paragraph[6, 11] isTrailingBlankLine
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[12, 22] open:[12, 13, "-"] openSuffix:[14, 17, "[x]"] isLoose isDone
      Paragraph[18, 22]
        Text[18, 22] chars:[18, 22, "task"]
````````````````````````````````


task list item class and p class

```````````````````````````````` example(Gfm Task List Options: 9) options(item-class, p-class)
- [ ] task
- [x] task
.
<ul>
  <li><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</li>
  <li><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</li>
</ul>
.
Document[0, 21]
  BulletList[0, 21] isTight
    TaskListItem[0, 11] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 21] open:[11, 12, "-"] openSuffix:[13, 16, "[x]"] isTight isDone
      Paragraph[17, 21]
        Text[17, 21] chars:[17, 21, "task"]
````````````````````````````````


task list item class and p class on loose list

```````````````````````````````` example(Gfm Task List Options: 10) options(item-class, p-class)
- [ ] task

- [x] task
.
<ul>
  <li>
    <p class="task-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</p>
  </li>
  <li>
    <p class="task-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</p>
  </li>
</ul>
.
Document[0, 22]
  BulletList[0, 22] isLoose
    TaskListItem[0, 11] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isLoose hadBlankLineAfter isNotDone
      Paragraph[6, 11] isTrailingBlankLine
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[12, 22] open:[12, 13, "-"] openSuffix:[14, 17, "[x]"] isLoose isDone
      Paragraph[18, 22]
        Text[18, 22] chars:[18, 22, "task"]
````````````````````````````````


custom marker task list item class and p class

```````````````````````````````` example(Gfm Task List Options: 11) options(item-class, p-class, done)
- [ ] task
- [x] task
.
<ul>
  <li><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</li>
  <li><span class="taskitem">X</span>task</li>
</ul>
.
Document[0, 21]
  BulletList[0, 21] isTight
    TaskListItem[0, 11] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 21] open:[11, 12, "-"] openSuffix:[13, 16, "[x]"] isTight isDone
      Paragraph[17, 21]
        Text[17, 21] chars:[17, 21, "task"]
````````````````````````````````


task list item class and p class on loose list

```````````````````````````````` example(Gfm Task List Options: 12) options(item-class, p-class, done)
- [ ] task

- [x] task
.
<ul>
  <li>
    <p class="task-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</p>
  </li>
  <li>
    <p class="task-item"><span class="taskitem">X</span>task</p>
  </li>
</ul>
.
Document[0, 22]
  BulletList[0, 22] isLoose
    TaskListItem[0, 11] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isLoose hadBlankLineAfter isNotDone
      Paragraph[6, 11] isTrailingBlankLine
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[12, 22] open:[12, 13, "-"] openSuffix:[14, 17, "[x]"] isLoose isDone
      Paragraph[18, 22]
        Text[18, 22] chars:[18, 22, "task"]
````````````````````````````````


custom marker task list item class and p class

```````````````````````````````` example(Gfm Task List Options: 13) options(item-class, p-class, done, not-done)
- [ ] task
- [x] task
.
<ul>
  <li><span class="taskitem">O</span>task</li>
  <li><span class="taskitem">X</span>task</li>
</ul>
.
Document[0, 21]
  BulletList[0, 21] isTight
    TaskListItem[0, 11] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 21] open:[11, 12, "-"] openSuffix:[13, 16, "[x]"] isTight isDone
      Paragraph[17, 21]
        Text[17, 21] chars:[17, 21, "task"]
````````````````````````````````


task list item class and p class on loose list

```````````````````````````````` example(Gfm Task List Options: 14) options(item-class, p-class, done, not-done)
- [ ] task

- [x] task
.
<ul>
  <li>
    <p class="task-item"><span class="taskitem">O</span>task</p>
  </li>
  <li>
    <p class="task-item"><span class="taskitem">X</span>task</p>
  </li>
</ul>
.
Document[0, 22]
  BulletList[0, 22] isLoose
    TaskListItem[0, 11] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isLoose hadBlankLineAfter isNotDone
      Paragraph[6, 11] isTrailingBlankLine
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[12, 22] open:[12, 13, "-"] openSuffix:[14, 17, "[x]"] isLoose isDone
      Paragraph[18, 22]
        Text[18, 22] chars:[18, 22, "task"]
````````````````````````````````


task list item on ordered list item

```````````````````````````````` example Gfm Task List Options: 15
1. [ ] task
2. [x] task
.
<ol>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</li>
</ol>
.
Document[0, 23]
  OrderedList[0, 23] isTight delimiter:'.'
    TaskListItem[0, 12] open:[0, 2, "1."] openSuffix:[3, 6, "[ ]"] isTight isOrderedItem isNotDone
      Paragraph[7, 12]
        Text[7, 11] chars:[7, 11, "task"]
    TaskListItem[12, 23] open:[12, 14, "2."] openSuffix:[15, 18, "[x]"] isTight isOrderedItem isDone
      Paragraph[19, 23]
        Text[19, 23] chars:[19, 23, "task"]
````````````````````````````````


task list item on ordered list item without ordered list item conversion

```````````````````````````````` example(Gfm Task List Options: 16) options(no-ordered-items)
1. [ ] task
2. [x] task
.
<ol>
  <li>[ ] task</li>
  <li>[x] task</li>
</ol>
.
Document[0, 23]
  OrderedList[0, 23] isTight delimiter:'.'
    OrderedListItem[0, 12] open:[0, 2, "1."] isTight
      Paragraph[3, 12]
        LinkRef[3, 6] referenceOpen:[3, 4, "["] reference:[5, 5] referenceClose:[5, 6, "]"]
          Text[5, 5]
        Text[6, 11] chars:[6, 11, " task"]
    OrderedListItem[12, 23] open:[12, 14, "2."] isTight
      Paragraph[15, 23]
        LinkRef[15, 18] referenceOpen:[15, 16, "["] reference:[16, 17, "x"] referenceClose:[17, 18, "]"]
          Text[16, 17] chars:[16, 17, "x"]
        Text[18, 23] chars:[18, 23, " task"]
````````````````````````````````


content indent is after task marker

```````````````````````````````` example Gfm Task List Options: 17
- [ ] task

  paragraph child item
.
<ul>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task
    <p>paragraph child item</p>
    </p>
  </li>
</ul>
.
Document[0, 34]
  BulletList[0, 34] isLoose
    TaskListItem[0, 34] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isLoose hadBlankLineAfter isNotDone
      Paragraph[6, 11] isTrailingBlankLine
        Text[6, 10] chars:[6, 10, "task"]
      Paragraph[14, 34]
        Text[14, 34] chars:[14, 34, "parag …  item"]
````````````````````````````````


content indent is after task marker

```````````````````````````````` example Gfm Task List Options: 18
- [ ] task

  paragraph child item

<!-- -->

1. [ ] task

   paragraph child item
.
<ul>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task
    <p>paragraph child item</p>
    </p>
  </li>
</ul>
<!-- -->
<ol>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task
    <p>paragraph child item</p>
    </p>
  </li>
</ol>
.
Document[0, 82]
  BulletList[0, 35] isLoose
    TaskListItem[0, 35] open:[0, 1, "-"] openSuffix:[2, 5, "[ ]"] isLoose hadBlankLineAfter isNotDone
      Paragraph[6, 11] isTrailingBlankLine
        Text[6, 10] chars:[6, 10, "task"]
      Paragraph[14, 35] isTrailingBlankLine
        Text[14, 34] chars:[14, 34, "parag …  item"]
  HtmlCommentBlock[36, 45]
  OrderedList[46, 82] isLoose delimiter:'.'
    TaskListItem[46, 82] open:[46, 48, "1."] openSuffix:[49, 52, "[ ]"] isLoose hadBlankLineAfter isOrderedItem isNotDone
      Paragraph[53, 58] isTrailingBlankLine
        Text[53, 57] chars:[53, 57, "task"]
      Paragraph[62, 82]
        Text[62, 82] chars:[62, 82, "parag …  item"]
````````````````````````````````


when content indent > code indent becomes code

```````````````````````````````` example Gfm Task List Options: 19
-     task
-     [ ] task

<!-- -->

1.     task
1.     [ ] task
.
<ul>
  <li>
    <pre><code>task
</code></pre>
  </li>
  <li>
    <pre><code>[ ] task
</code></pre>
  </li>
</ul>
<!-- -->
<ol>
  <li>
    <pre><code>task
</code></pre>
  </li>
  <li>
    <pre><code>[ ] task
</code></pre>
  </li>
</ol>
.
Document[0, 64]
  BulletList[0, 26] isTight
    BulletListItem[0, 11] open:[0, 1, "-"] isTight
      IndentedCodeBlock[6, 11]
    BulletListItem[11, 26] open:[11, 12, "-"] isTight hadBlankLineAfter
      IndentedCodeBlock[17, 26]
  HtmlCommentBlock[27, 36]
  OrderedList[37, 64] isTight delimiter:'.'
    OrderedListItem[37, 49] open:[37, 39, "1."] isTight
      IndentedCodeBlock[44, 49]
    OrderedListItem[49, 64] open:[49, 51, "1."] isTight
      IndentedCodeBlock[56, 64]
````````````````````````````````


when content indent > code indent becomes code

```````````````````````````````` example Gfm Task List Options: 20
-     task
- [ ]      task

<!-- -->

1.     task
1. [ ]     task
.
<ul>
  <li>
    <pre><code>task
</code></pre>
  </li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;
    <pre><code> task
</code></pre>
  </li>
</ul>
<!-- -->
<ol>
  <li>
    <pre><code>task
</code></pre>
  </li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;
    <pre><code>task
</code></pre>
  </li>
</ol>
.
Document[0, 65]
  BulletList[0, 27] isTight
    BulletListItem[0, 11] open:[0, 1, "-"] isTight
      IndentedCodeBlock[6, 11]
    TaskListItem[11, 27] open:[11, 12, "-"] openSuffix:[13, 16, "[ ]"] isTight hadBlankLineAfter isNotDone
      IndentedCodeBlock[21, 27]
  HtmlCommentBlock[28, 37]
  OrderedList[38, 65] isTight delimiter:'.'
    OrderedListItem[38, 50] open:[38, 40, "1."] isTight
      IndentedCodeBlock[45, 50]
    TaskListItem[50, 65] open:[50, 52, "1."] openSuffix:[53, 56, "[ ]"] isTight isOrderedItem isNotDone
      IndentedCodeBlock[61, 65]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
1. [ ] task
2. [x] task
<!-- -->
- [ ] task
- [x] task
.
<ol>
  <li class="task-list-item" md-pos="7-12"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</li>
  <li class="task-list-item" md-pos="19-24"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</li>
</ol>
<!-- -->
<ul>
  <li class="task-list-item" md-pos="39-44"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task</li>
  <li class="task-list-item" md-pos="50-54"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task</li>
</ul>
.
Document[0, 54]
  OrderedList[0, 24] isTight delimiter:'.'
    TaskListItem[0, 12] open:[0, 2, "1."] openSuffix:[3, 6, "[ ]"] isTight isOrderedItem isNotDone
      Paragraph[7, 12]
        Text[7, 11] chars:[7, 11, "task"]
    TaskListItem[12, 24] open:[12, 14, "2."] openSuffix:[15, 18, "[x]"] isTight isOrderedItem isDone
      Paragraph[19, 24]
        Text[19, 23] chars:[19, 23, "task"]
  HtmlCommentBlock[24, 33]
  BulletList[33, 54] isTight
    TaskListItem[33, 44] open:[33, 34, "-"] openSuffix:[35, 38, "[ ]"] isTight isNotDone
      Paragraph[39, 44]
        Text[39, 43] chars:[39, 43, "task"]
    TaskListItem[44, 54] open:[44, 45, "-"] openSuffix:[46, 49, "[x]"] isTight isDone
      Paragraph[50, 54]
        Text[50, 54] chars:[50, 54, "task"]
````````````````````````````````


Wrap individual lines in source position spans tight items

```````````````````````````````` example(Source Position Attribute: 2) options(src-pos, src-pos-lines)
1. [ ] item
with multiple lazy lines
all should be src pos wrapped
<!-- -->
- [ ] item
with multiple lazy lines
all should be src pos wrapped
.
<ol>
  <li class="task-list-item" md-pos="0-67"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;<span md-pos="7-11">item</span>
    <span md-pos="12-36">with multiple lazy lines</span>
    <span md-pos="37-66">all should be src pos wrapped</span></li>
</ol>
<!-- -->
<ul>
  <li class="task-list-item" md-pos="76-141"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;<span md-pos="82-86">item</span>
    <span md-pos="87-111">with multiple lazy lines</span>
    <span md-pos="112-141">all should be src pos wrapped</span></li>
</ul>
.
Document[0, 141]
  OrderedList[0, 67] isTight delimiter:'.'
    TaskListItem[0, 67] open:[0, 2, "1."] openSuffix:[3, 6, "[ ]"] isTight isOrderedItem isNotDone
      Paragraph[7, 67]
        Text[7, 11] chars:[7, 11, "item"]
        SoftLineBreak[11, 12]
        Text[12, 36] chars:[12, 36, "with  … lines"]
        SoftLineBreak[36, 37]
        Text[37, 66] chars:[37, 66, "all s … apped"]
  HtmlCommentBlock[67, 76]
  BulletList[76, 141] isTight
    TaskListItem[76, 141] open:[76, 77, "-"] openSuffix:[78, 81, "[ ]"] isTight isNotDone
      Paragraph[82, 141]
        Text[82, 86] chars:[82, 86, "item"]
        SoftLineBreak[86, 87]
        Text[87, 111] chars:[87, 111, "with  … lines"]
        SoftLineBreak[111, 112]
        Text[112, 141] chars:[112, 141, "all s … apped"]
````````````````````````````````


Wrap individual lines in source position spans loose items

```````````````````````````````` example(Source Position Attribute: 3) options(src-pos, src-pos-lines)
1. [ ] item

1. [ ] item
with multiple lazy lines
all should be src pos wrapped
<!-- -->
- [ ] item

- [ ] item
with multiple lazy lines
all should be src pos wrapped
.
<ol>
  <li class="task-list-item">
    <p md-pos="0-12"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;<span md-pos="7-11">item</span></p>
  </li>
  <li class="task-list-item">
    <p md-pos="13-80"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;<span md-pos="20-24">item</span>
    <span md-pos="25-49">with multiple lazy lines</span>
    <span md-pos="50-79">all should be src pos wrapped</span></p>
  </li>
</ol>
<!-- -->
<ul>
  <li class="task-list-item">
    <p md-pos="89-100"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;<span md-pos="95-99">item</span></p>
  </li>
  <li class="task-list-item">
    <p md-pos="101-166"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;<span md-pos="107-111">item</span>
    <span md-pos="112-136">with multiple lazy lines</span>
    <span md-pos="137-166">all should be src pos wrapped</span></p>
  </li>
</ul>
.
Document[0, 166]
  OrderedList[0, 80] isLoose delimiter:'.'
    TaskListItem[0, 12] open:[0, 2, "1."] openSuffix:[3, 6, "[ ]"] isLoose hadBlankLineAfter isOrderedItem isNotDone
      Paragraph[7, 12] isTrailingBlankLine
        Text[7, 11] chars:[7, 11, "item"]
    TaskListItem[13, 80] open:[13, 15, "1."] openSuffix:[16, 19, "[ ]"] isLoose isOrderedItem isNotDone
      Paragraph[20, 80]
        Text[20, 24] chars:[20, 24, "item"]
        SoftLineBreak[24, 25]
        Text[25, 49] chars:[25, 49, "with  … lines"]
        SoftLineBreak[49, 50]
        Text[50, 79] chars:[50, 79, "all s … apped"]
  HtmlCommentBlock[80, 89]
  BulletList[89, 166] isLoose
    TaskListItem[89, 100] open:[89, 90, "-"] openSuffix:[91, 94, "[ ]"] isLoose hadBlankLineAfter isNotDone
      Paragraph[95, 100] isTrailingBlankLine
        Text[95, 99] chars:[95, 99, "item"]
    TaskListItem[101, 166] open:[101, 102, "-"] openSuffix:[103, 106, "[ ]"] isLoose isNotDone
      Paragraph[107, 166]
        Text[107, 111] chars:[107, 111, "item"]
        SoftLineBreak[111, 112]
        Text[112, 136] chars:[112, 136, "with  … lines"]
        SoftLineBreak[136, 137]
        Text[137, 166] chars:[137, 166, "all s … apped"]
````````````````````````````````


Deep indent testing, commonmark

```````````````````````````````` example Source Position Attribute: 4
* [ ] item 1
* [ ]  item 2
* [ ]   item 3
* [ ]    item 4
* [ ]     item 5
* [ ]      item 6
* [ ]       item 7
* [ ]        item 8
* [ ]         item 9
.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 1</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 2</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 3</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 4</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;
    <pre><code>item 5
</code></pre>
  </li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;
    <pre><code> item 6
</code></pre>
  </li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;
    <pre><code>  item 7
</code></pre>
  </li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;
    <pre><code>   item 8
</code></pre>
  </li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;
    <pre><code>    item 9
</code></pre>
  </li>
</ul>
.
Document[0, 152]
  BulletList[0, 152] isTight
    TaskListItem[0, 13] open:[0, 1, "*"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 13]
        Text[6, 12] chars:[6, 12, "item 1"]
    TaskListItem[13, 27] open:[13, 14, "*"] openSuffix:[15, 18, "[ ]"] isTight isNotDone
      Paragraph[20, 27]
        Text[20, 26] chars:[20, 26, "item 2"]
    TaskListItem[27, 42] open:[27, 28, "*"] openSuffix:[29, 32, "[ ]"] isTight isNotDone
      Paragraph[35, 42]
        Text[35, 41] chars:[35, 41, "item 3"]
    TaskListItem[42, 58] open:[42, 43, "*"] openSuffix:[44, 47, "[ ]"] isTight isNotDone
      Paragraph[51, 58]
        Text[51, 57] chars:[51, 57, "item 4"]
    TaskListItem[58, 75] open:[58, 59, "*"] openSuffix:[60, 63, "[ ]"] isTight isNotDone
      IndentedCodeBlock[68, 75]
    TaskListItem[75, 93] open:[75, 76, "*"] openSuffix:[77, 80, "[ ]"] isTight isNotDone
      IndentedCodeBlock[85, 93]
    TaskListItem[93, 112] open:[93, 94, "*"] openSuffix:[95, 98, "[ ]"] isTight isNotDone
      IndentedCodeBlock[103, 112]
    TaskListItem[112, 132] open:[112, 113, "*"] openSuffix:[114, 117, "[ ]"] isTight isNotDone
      IndentedCodeBlock[122, 132]
    TaskListItem[132, 152] open:[132, 133, "*"] openSuffix:[134, 137, "[ ]"] isTight isNotDone
      IndentedCodeBlock[142, 152]
````````````````````````````````


Deep indent testing, kramdown

```````````````````````````````` example(Source Position Attribute: 5) options(kramdown)
* [ ] item 1
* [ ]  item 2
* [ ]   item 3
* [ ]    item 4
* [ ]     item 5
* [ ]      item 6
* [ ]       item 7
* [ ]        item 8
* [ ]         item 9
.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 1</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 2</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 3</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 4</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 5</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 6</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 7</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 8</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 9</li>
</ul>
.
Document[0, 152]
  BulletList[0, 152] isTight
    TaskListItem[0, 13] open:[0, 1, "*"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 13]
        Text[6, 12] chars:[6, 12, "item 1"]
    TaskListItem[13, 27] open:[13, 14, "*"] openSuffix:[15, 18, "[ ]"] isTight isNotDone
      Paragraph[20, 27]
        Text[20, 26] chars:[20, 26, "item 2"]
    TaskListItem[27, 42] open:[27, 28, "*"] openSuffix:[29, 32, "[ ]"] isTight isNotDone
      Paragraph[35, 42]
        Text[35, 41] chars:[35, 41, "item 3"]
    TaskListItem[42, 58] open:[42, 43, "*"] openSuffix:[44, 47, "[ ]"] isTight isNotDone
      Paragraph[51, 58]
        Text[51, 57] chars:[51, 57, "item 4"]
    TaskListItem[58, 75] open:[58, 59, "*"] openSuffix:[60, 63, "[ ]"] isTight isNotDone
      Paragraph[68, 75]
        Text[68, 74] chars:[68, 74, "item 5"]
    TaskListItem[75, 93] open:[75, 76, "*"] openSuffix:[77, 80, "[ ]"] isTight isNotDone
      Paragraph[86, 93]
        Text[86, 92] chars:[86, 92, "item 6"]
    TaskListItem[93, 112] open:[93, 94, "*"] openSuffix:[95, 98, "[ ]"] isTight isNotDone
      Paragraph[105, 112]
        Text[105, 111] chars:[105, 111, "item 7"]
    TaskListItem[112, 132] open:[112, 113, "*"] openSuffix:[114, 117, "[ ]"] isTight isNotDone
      Paragraph[125, 132]
        Text[125, 131] chars:[125, 131, "item 8"]
    TaskListItem[132, 152] open:[132, 133, "*"] openSuffix:[134, 137, "[ ]"] isTight isNotDone
      Paragraph[146, 152]
        Text[146, 152] chars:[146, 152, "item 9"]
````````````````````````````````


Deep indent testing, markdown

```````````````````````````````` example(Source Position Attribute: 6) options(markdown)
* [ ] item 1
* [ ]  item 2
* [ ]   item 3
* [ ]    item 4
* [ ]     item 5
* [ ]      item 6
* [ ]       item 7
* [ ]        item 8
* [ ]         item 9
.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 1</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 2</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 3</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 4</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 5</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 6</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 7</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 8</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 9</li>
</ul>
.
Document[0, 152]
  BulletList[0, 152] isTight
    TaskListItem[0, 13] open:[0, 1, "*"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 13]
        Text[6, 12] chars:[6, 12, "item 1"]
    TaskListItem[13, 27] open:[13, 14, "*"] openSuffix:[15, 18, "[ ]"] isTight isNotDone
      Paragraph[20, 27]
        Text[20, 26] chars:[20, 26, "item 2"]
    TaskListItem[27, 42] open:[27, 28, "*"] openSuffix:[29, 32, "[ ]"] isTight isNotDone
      Paragraph[35, 42]
        Text[35, 41] chars:[35, 41, "item 3"]
    TaskListItem[42, 58] open:[42, 43, "*"] openSuffix:[44, 47, "[ ]"] isTight isNotDone
      Paragraph[51, 58]
        Text[51, 57] chars:[51, 57, "item 4"]
    TaskListItem[58, 75] open:[58, 59, "*"] openSuffix:[60, 63, "[ ]"] isTight isNotDone
      Paragraph[68, 75]
        Text[68, 74] chars:[68, 74, "item 5"]
    TaskListItem[75, 93] open:[75, 76, "*"] openSuffix:[77, 80, "[ ]"] isTight isNotDone
      Paragraph[86, 93]
        Text[86, 92] chars:[86, 92, "item 6"]
    TaskListItem[93, 112] open:[93, 94, "*"] openSuffix:[95, 98, "[ ]"] isTight isNotDone
      Paragraph[105, 112]
        Text[105, 111] chars:[105, 111, "item 7"]
    TaskListItem[112, 132] open:[112, 113, "*"] openSuffix:[114, 117, "[ ]"] isTight isNotDone
      Paragraph[125, 132]
        Text[125, 131] chars:[125, 131, "item 8"]
    TaskListItem[132, 152] open:[132, 133, "*"] openSuffix:[134, 137, "[ ]"] isTight isNotDone
      Paragraph[146, 152]
        Text[146, 152] chars:[146, 152, "item 9"]
````````````````````````````````


Treat content as starting after the suffix.

```````````````````````````````` example(Source Position Attribute: 7) options(no-suffix-content)
* [ ] item
  * [ ] item 2
    * [ ] item 2
.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 2</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 2</li>
</ul>
.
Document[0, 42]
  BulletList[0, 42] isTight
    TaskListItem[0, 11] open:[0, 1, "*"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "item"]
    TaskListItem[13, 26] open:[13, 14, "*"] openSuffix:[15, 18, "[ ]"] isTight isNotDone
      Paragraph[19, 26]
        Text[19, 25] chars:[19, 25, "item 2"]
    TaskListItem[30, 42] open:[30, 31, "*"] openSuffix:[32, 35, "[ ]"] isTight isNotDone
      Paragraph[36, 42]
        Text[36, 42] chars:[36, 42, "item 2"]
````````````````````````````````


Treat content as starting at the suffix.

```````````````````````````````` example Source Position Attribute: 8
* [ ] item
  * [ ] item 2
    * [ ] item 2
.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item
    <ul>
      <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 2
        <ul>
          <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 2</li>
        </ul>
      </li>
    </ul>
  </li>
</ul>
.
Document[0, 42]
  BulletList[0, 42] isTight
    TaskListItem[0, 42] open:[0, 1, "*"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "item"]
      BulletList[13, 42] isTight
        TaskListItem[13, 42] open:[13, 14, "*"] openSuffix:[15, 18, "[ ]"] isTight isNotDone
          Paragraph[19, 26]
            Text[19, 25] chars:[19, 25, "item 2"]
          BulletList[30, 42] isTight
            TaskListItem[30, 42] open:[30, 31, "*"] openSuffix:[32, 35, "[ ]"] isTight isNotDone
              Paragraph[36, 42]
                Text[36, 42] chars:[36, 42, "item 2"]
````````````````````````````````


## Issues

### 382

Issue [#382, Is there an option for number of whitespaces needed to create sub-lists?]

```````````````````````````````` example Issues - 382: 1
* [ ] First item
* [ ] Second item


* [ ] Third item separated by 2 newlines

.
<ul>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;First item</p>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;Second item</p>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;Third item separated by 2 newlines</p>
  </li>
</ul>
````````````````````````````````


```````````````````````````````` example Issues - 382: 2
* [ ] First item
                * [ ] Second item

.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;First item
    * [ ] Second item</li>
</ul>
````````````````````````````````


[#382, Is there an option for number of whitespaces needed to create sub-lists?]: https://github.com/vsch/flexmark-java/issues/382

