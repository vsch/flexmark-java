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

```````````````````````````````` example Gfm Task List Core: 1
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
Document[0, 96]
  BulletList[0, 96] isTight
    BulletListItem[0, 5] open:[0, 1, "-"] isTight
      Paragraph[2, 5]
        LinkRef[2, 4] referenceOpen:[2, 3, "["] reference:[3, 3] referenceClose:[3, 4, "]"]
    BulletListItem[5, 12] open:[5, 6, "-"] isTight
      Paragraph[7, 12]
        LinkRef[7, 11] referenceOpen:[7, 8, "["] reference:[8, 9, "x"] referenceClose:[10, 11, "]"]
          Text[8, 10] chars:[8, 10, "x "]
    BulletListItem[12, 19] open:[12, 13, "-"] isTight
      Paragraph[14, 19]
        LinkRef[14, 18] referenceOpen:[14, 15, "["] reference:[16, 17, "x"] referenceClose:[17, 18, "]"]
          Text[15, 17] chars:[15, 17, " x"]
    BulletListItem[19, 27] open:[19, 20, "-"] isTight
      Paragraph[21, 27]
        LinkRef[21, 26] referenceOpen:[21, 22, "["] reference:[23, 24, "x"] referenceClose:[25, 26, "]"]
          Text[22, 25] chars:[22, 25, " x "]
    BulletListItem[27, 33] open:[27, 28, "-"] isTight
      Paragraph[29, 33]
        LinkRef[29, 32] referenceOpen:[29, 30, "["] reference:[30, 31, "a"] referenceClose:[31, 32, "]"]
          Text[30, 31] chars:[30, 31, "a"]
    BulletListItem[33, 42] open:[33, 34, "-"] isTight
      Paragraph[35, 42]
        LinkRef[35, 38] referenceOpen:[35, 36, "["] reference:[37, 37] referenceClose:[37, 38, "]"]
          Text[36, 37] chars:[36, 37, " "]
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
          Text[64, 65] chars:[64, 65, " "]
        Text[66, 71] chars:[66, 71, " task"]
    BulletListItem[72, 84] open:[72, 73, "-"] isTight
      Paragraph[74, 84]
        Text[74, 75] chars:[74, 75, "b"]
        LinkRef[75, 78] referenceOpen:[75, 76, "["] reference:[76, 77, "x"] referenceClose:[77, 78, "]"]
          Text[76, 77] chars:[76, 77, "x"]
        Text[78, 83] chars:[78, 83, " task"]
    BulletListItem[84, 96] open:[84, 85, "-"] isTight
      Paragraph[86, 96]
        Text[86, 87] chars:[86, 87, "c"]
        LinkRef[87, 90] referenceOpen:[87, 88, "["] reference:[88, 89, "x"] referenceClose:[89, 90, "]"]
          Text[88, 89] chars:[88, 89, "x"]
        Text[90, 95] chars:[90, 95, " task"]
````````````````````````````````


empty task list items

```````````````````````````````` example Gfm Task List Core: 2
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
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" /></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" /></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" /></li>
</ul>
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" /></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" /></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" /></li>
</ul>
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" /></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" /></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" /></li>
</ul>
.
Document[0, 62]
  BulletList[0, 22] isTight
    TaskListItem[0, 6] open:[0, 1, "-"] isTight taskOpen:[2, 5, "[ ]"]
    TaskListItem[6, 13] open:[6, 7, "-"] isTight taskOpen:[9, 12, "[X]"]
    TaskListItem[13, 22] open:[13, 14, "-"] isTight taskOpen:[18, 21, "[x]"]
  BulletList[23, 43] isLoose
    TaskListItem[23, 29] open:[23, 24, "*"] isLoose taskOpen:[25, 28, "[ ]"]
    TaskListItem[30, 36] open:[30, 31, "*"] isLoose taskOpen:[32, 35, "[x]"]
    TaskListItem[37, 43] open:[37, 38, "*"] isLoose taskOpen:[39, 42, "[X]"]
  BulletList[44, 62] isTight
    TaskListItem[44, 50] open:[44, 45, "+"] isTight taskOpen:[46, 49, "[ ]"]
    TaskListItem[50, 56] open:[50, 51, "+"] isTight taskOpen:[52, 55, "[x]"]
    TaskListItem[56, 62] open:[56, 57, "+"] isTight taskOpen:[58, 61, "[X]"]
````````````````````````````````


non empty task list items

```````````````````````````````` example Gfm Task List Core: 3
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
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" />task</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task</li>
</ul>
<ul>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" />task</p>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task</p>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task</p>
  </li>
</ul>
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" />task</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task</li>
</ul>
.
Document[0, 103]
  BulletList[0, 33] isTight
    TaskListItem[0, 11] open:[0, 1, "-"] isTight taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 22] open:[11, 12, "-"] isTight taskOpen:[13, 16, "[X]"]
      Paragraph[17, 22]
        Text[17, 21] chars:[17, 21, "task"]
    TaskListItem[22, 33] open:[22, 23, "-"] isTight taskOpen:[24, 27, "[x]"]
      Paragraph[28, 33]
        Text[28, 32] chars:[28, 32, "task"]
  BulletList[34, 69] isLoose
    TaskListItem[34, 45] open:[34, 35, "*"] isLoose taskOpen:[36, 39, "[ ]"]
      Paragraph[40, 45]
        Text[40, 44] chars:[40, 44, "task"]
    TaskListItem[46, 57] open:[46, 47, "*"] isLoose taskOpen:[48, 51, "[x]"]
      Paragraph[52, 57]
        Text[52, 56] chars:[52, 56, "task"]
    TaskListItem[58, 69] open:[58, 59, "*"] isLoose taskOpen:[60, 63, "[X]"]
      Paragraph[64, 69]
        Text[64, 68] chars:[64, 68, "task"]
  BulletList[70, 103] isTight
    TaskListItem[70, 81] open:[70, 71, "+"] isTight taskOpen:[72, 75, "[ ]"]
      Paragraph[76, 81]
        Text[76, 80] chars:[76, 80, "task"]
    TaskListItem[81, 92] open:[81, 82, "+"] isTight taskOpen:[83, 86, "[x]"]
      Paragraph[87, 92]
        Text[87, 91] chars:[87, 91, "task"]
    TaskListItem[92, 103] open:[92, 93, "+"] isTight taskOpen:[94, 97, "[X]"]
      Paragraph[98, 103]
        Text[98, 102] chars:[98, 102, "task"]
````````````````````````````````


non empty task list items with inlines

```````````````````````````````` example Gfm Task List Core: 4
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
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" />task <strong>emphasis</strong></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task <strong>emphasis</strong></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task <strong>emphasis</strong></li>
</ul>
<ul>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" />task <strong>emphasis</strong></p>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task <strong>emphasis</strong></p>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task <strong>emphasis</strong></p>
  </li>
</ul>
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" />task <strong>emphasis</strong></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task <strong>emphasis</strong></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task <strong>emphasis</strong></li>
</ul>
.
Document[0, 220]
  BulletList[0, 72] isTight
    TaskListItem[0, 24] open:[0, 1, "-"] isTight taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 24]
        Text[6, 11] chars:[6, 11, "task "]
        StrongEmphasis[11, 23] textOpen:[11, 13, "**"] text:[13, 21, "emphasis"] textClose:[21, 23, "**"]
          Text[13, 21] chars:[13, 21, "emphasis"]
    TaskListItem[24, 48] open:[24, 25, "-"] isTight taskOpen:[26, 29, "[X]"]
      Paragraph[30, 48]
        Text[30, 35] chars:[30, 35, "task "]
        StrongEmphasis[35, 47] textOpen:[35, 37, "**"] text:[37, 45, "emphasis"] textClose:[45, 47, "**"]
          Text[37, 45] chars:[37, 45, "emphasis"]
    TaskListItem[48, 72] open:[48, 49, "-"] isTight taskOpen:[50, 53, "[x]"]
      Paragraph[54, 72]
        Text[54, 59] chars:[54, 59, "task "]
        StrongEmphasis[59, 71] textOpen:[59, 61, "**"] text:[61, 69, "emphasis"] textClose:[69, 71, "**"]
          Text[61, 69] chars:[61, 69, "emphasis"]
  BulletList[73, 147] isLoose
    TaskListItem[73, 97] open:[73, 74, "*"] isLoose taskOpen:[75, 78, "[ ]"]
      Paragraph[79, 97]
        Text[79, 84] chars:[79, 84, "task "]
        StrongEmphasis[84, 96] textOpen:[84, 86, "**"] text:[86, 94, "emphasis"] textClose:[94, 96, "**"]
          Text[86, 94] chars:[86, 94, "emphasis"]
    TaskListItem[98, 122] open:[98, 99, "*"] isLoose taskOpen:[100, 103, "[x]"]
      Paragraph[104, 122]
        Text[104, 109] chars:[104, 109, "task "]
        StrongEmphasis[109, 121] textOpen:[109, 111, "**"] text:[111, 119, "emphasis"] textClose:[119, 121, "**"]
          Text[111, 119] chars:[111, 119, "emphasis"]
    TaskListItem[123, 147] open:[123, 124, "*"] isLoose taskOpen:[125, 128, "[X]"]
      Paragraph[129, 147]
        Text[129, 134] chars:[129, 134, "task "]
        StrongEmphasis[134, 146] textOpen:[134, 136, "**"] text:[136, 144, "emphasis"] textClose:[144, 146, "**"]
          Text[136, 144] chars:[136, 144, "emphasis"]
  BulletList[148, 220] isTight
    TaskListItem[148, 172] open:[148, 149, "+"] isTight taskOpen:[150, 153, "[ ]"]
      Paragraph[154, 172]
        Text[154, 159] chars:[154, 159, "task "]
        StrongEmphasis[159, 171] textOpen:[159, 161, "**"] text:[161, 169, "emphasis"] textClose:[169, 171, "**"]
          Text[161, 169] chars:[161, 169, "emphasis"]
    TaskListItem[172, 196] open:[172, 173, "+"] isTight taskOpen:[174, 177, "[x]"]
      Paragraph[178, 196]
        Text[178, 183] chars:[178, 183, "task "]
        StrongEmphasis[183, 195] textOpen:[183, 185, "**"] text:[185, 193, "emphasis"] textClose:[193, 195, "**"]
          Text[185, 193] chars:[185, 193, "emphasis"]
    TaskListItem[196, 220] open:[196, 197, "+"] isTight taskOpen:[198, 201, "[X]"]
      Paragraph[202, 220]
        Text[202, 207] chars:[202, 207, "task "]
        StrongEmphasis[207, 219] textOpen:[207, 209, "**"] text:[209, 217, "emphasis"] textClose:[217, 219, "**"]
          Text[209, 217] chars:[209, 217, "emphasis"]
````````````````````````````````


## Gfm Task List Options

task list item class

```````````````````````````````` example(Gfm Task List Options: 1) options(item-class)
- [ ] task
- [x] task
.
<ul>
  <li><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" />task</li>
  <li><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task</li>
</ul>
.
Document[0, 21]
  BulletList[0, 21] isTight
    TaskListItem[0, 11] open:[0, 1, "-"] isTight taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 21] open:[11, 12, "-"] isTight taskOpen:[13, 16, "[x]"]
      Paragraph[17, 21]
        Text[17, 21] chars:[17, 21, "task"]
````````````````````````````````


task list item class on loose list

```````````````````````````````` example(Gfm Task List Options: 2) options(item-class)
- [ ] task

- [x] task
.
<ul>
  <li>
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" />task</p>
  </li>
  <li>
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task</p>
  </li>
</ul>
.
Document[0, 22]
  BulletList[0, 22] isLoose
    TaskListItem[0, 11] open:[0, 1, "-"] isLoose taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[12, 22] open:[12, 13, "-"] isLoose taskOpen:[14, 17, "[x]"]
      Paragraph[18, 22]
        Text[18, 22] chars:[18, 22, "task"]
````````````````````````````````


loose task list item class

```````````````````````````````` example(Gfm Task List Options: 3) options(loose-class)
- [ ] task
- [x] task
.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" />task</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task</li>
</ul>
.
Document[0, 21]
  BulletList[0, 21] isTight
    TaskListItem[0, 11] open:[0, 1, "-"] isTight taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 21] open:[11, 12, "-"] isTight taskOpen:[13, 16, "[x]"]
      Paragraph[17, 21]
        Text[17, 21] chars:[17, 21, "task"]
````````````````````````````````


loose task list item class on loose list

```````````````````````````````` example(Gfm Task List Options: 4) options(loose-class)
- [ ] task

- [x] task
.
<ul>
  <li>
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" />task</p>
  </li>
  <li>
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task</p>
  </li>
</ul>
.
Document[0, 22]
  BulletList[0, 22] isLoose
    TaskListItem[0, 11] open:[0, 1, "-"] isLoose taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[12, 22] open:[12, 13, "-"] isLoose taskOpen:[14, 17, "[x]"]
      Paragraph[18, 22]
        Text[18, 22] chars:[18, 22, "task"]
````````````````````````````````


task list item class and p class

```````````````````````````````` example(Gfm Task List Options: 5) options(item-class, p-class)
- [ ] task
- [x] task
.
<ul>
  <li><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" />task</li>
  <li><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task</li>
</ul>
.
Document[0, 21]
  BulletList[0, 21] isTight
    TaskListItem[0, 11] open:[0, 1, "-"] isTight taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 21] open:[11, 12, "-"] isTight taskOpen:[13, 16, "[x]"]
      Paragraph[17, 21]
        Text[17, 21] chars:[17, 21, "task"]
````````````````````````````````


task list item class and p class on loose list

```````````````````````````````` example(Gfm Task List Options: 6) options(item-class, p-class)
- [ ] task

- [x] task
.
<ul>
  <li>
    <p class="task-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" />task</p>
  </li>
  <li>
    <p class="task-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task</p>
  </li>
</ul>
.
Document[0, 22]
  BulletList[0, 22] isLoose
    TaskListItem[0, 11] open:[0, 1, "-"] isLoose taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[12, 22] open:[12, 13, "-"] isLoose taskOpen:[14, 17, "[x]"]
      Paragraph[18, 22]
        Text[18, 22] chars:[18, 22, "task"]
````````````````````````````````


custom marker task list item class and p class

```````````````````````````````` example(Gfm Task List Options: 7) options(item-class, p-class, done)
- [ ] task
- [x] task
.
<ul>
  <li><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" />task</li>
  <li><span class="taskitem">X</span>task</li>
</ul>
.
Document[0, 21]
  BulletList[0, 21] isTight
    TaskListItem[0, 11] open:[0, 1, "-"] isTight taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 21] open:[11, 12, "-"] isTight taskOpen:[13, 16, "[x]"]
      Paragraph[17, 21]
        Text[17, 21] chars:[17, 21, "task"]
````````````````````````````````


task list item class and p class on loose list

```````````````````````````````` example(Gfm Task List Options: 8) options(item-class, p-class, done)
- [ ] task

- [x] task
.
<ul>
  <li>
    <p class="task-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" />task</p>
  </li>
  <li>
    <p class="task-item"><span class="taskitem">X</span>task</p>
  </li>
</ul>
.
Document[0, 22]
  BulletList[0, 22] isLoose
    TaskListItem[0, 11] open:[0, 1, "-"] isLoose taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[12, 22] open:[12, 13, "-"] isLoose taskOpen:[14, 17, "[x]"]
      Paragraph[18, 22]
        Text[18, 22] chars:[18, 22, "task"]
````````````````````````````````


custom marker task list item class and p class

```````````````````````````````` example(Gfm Task List Options: 9) options(item-class, p-class, done, not-done)
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
    TaskListItem[0, 11] open:[0, 1, "-"] isTight taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 21] open:[11, 12, "-"] isTight taskOpen:[13, 16, "[x]"]
      Paragraph[17, 21]
        Text[17, 21] chars:[17, 21, "task"]
````````````````````````````````


task list item class and p class on loose list

```````````````````````````````` example(Gfm Task List Options: 10) options(item-class, p-class, done, not-done)
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
    TaskListItem[0, 11] open:[0, 1, "-"] isLoose taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[12, 22] open:[12, 13, "-"] isLoose taskOpen:[14, 17, "[x]"]
      Paragraph[18, 22]
        Text[18, 22] chars:[18, 22, "task"]
````````````````````````````````


task list item on ordered list item

```````````````````````````````` example Gfm Task List Options: 11
1. [ ] task
2. [x] task
.
<ol>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" />task</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task</li>
</ol>
.
Document[0, 24]
  OrderedList[0, 24] isTight delimiter:'.'
    TaskListItem[0, 12] open:[0, 2, "1."] isTight taskOpen:[3, 6, "[ ]"]
      Paragraph[7, 12]
        Text[7, 11] chars:[7, 11, "task"]
    TaskListItem[12, 24] open:[12, 14, "2."] isTight taskOpen:[15, 18, "[x]"]
      Paragraph[19, 24]
        Text[19, 23] chars:[19, 23, "task"]
````````````````````````````````


task list item on ordered list item without ordered list item conversion

```````````````````````````````` example(Gfm Task List Options: 12) options(no-ordered-items)
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
          Text[4, 5] chars:[4, 5, " "]
        Text[6, 11] chars:[6, 11, " task"]
    OrderedListItem[12, 23] open:[12, 14, "2."] isTight
      Paragraph[15, 23]
        LinkRef[15, 18] referenceOpen:[15, 16, "["] reference:[16, 17, "x"] referenceClose:[17, 18, "]"]
          Text[16, 17] chars:[16, 17, "x"]
        Text[18, 23] chars:[18, 23, " task"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
1. [ ] task
2. [x] task
<!-- -->
- [ ] task
- [x] task
.
<ol>
  <li class="task-list-item" md-pos="7-12"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" />task</li>
  <li class="task-list-item" md-pos="19-24"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task</li>
</ol>
<!-- -->
<ul>
  <li class="task-list-item" md-pos="39-44"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" />task</li>
  <li class="task-list-item" md-pos="50-54"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" />task</li>
</ul>
.
Document[0, 54]
  OrderedList[0, 24] isTight delimiter:'.'
    TaskListItem[0, 12] open:[0, 2, "1."] isTight taskOpen:[3, 6, "[ ]"]
      Paragraph[7, 12]
        Text[7, 11] chars:[7, 11, "task"]
    TaskListItem[12, 24] open:[12, 14, "2."] isTight taskOpen:[15, 18, "[x]"]
      Paragraph[19, 24]
        Text[19, 23] chars:[19, 23, "task"]
  HtmlCommentBlock[24, 33]
  BulletList[33, 54] isTight
    TaskListItem[33, 44] open:[33, 34, "-"] isTight taskOpen:[35, 38, "[ ]"]
      Paragraph[39, 44]
        Text[39, 43] chars:[39, 43, "task"]
    TaskListItem[44, 54] open:[44, 45, "-"] isTight taskOpen:[46, 49, "[x]"]
      Paragraph[50, 54]
        Text[50, 54] chars:[50, 54, "task"]
````````````````````````````````

Wrap individual lines in source position spans tight items

```````````````````````````````` example(Source Position Attribute: 2) options(src-pos, src-pos-lines)
1. [ ] item
with multiple lazy lines
all should be src pos wrapped
<!-- -->
- [ ] item
with multiple lazy lines
all should be src pos wrapped
.
<ol>
  <li class="task-list-item" md-pos="0-67"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" /><span md-pos="7-11">item</span>
  <span md-pos="12-36">with multiple lazy lines</span>
  <span md-pos="37-66">all should be src pos wrapped</span></li>
</ol>
<!-- -->
<ul>
  <li class="task-list-item" md-pos="76-141"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" /><span md-pos="82-86">item</span>
  <span md-pos="87-111">with multiple lazy lines</span>
  <span md-pos="112-141">all should be src pos wrapped</span></li>
</ul>
.
Document[0, 141]
  OrderedList[0, 67] isTight delimiter:'.'
    TaskListItem[0, 67] open:[0, 2, "1."] isTight taskOpen:[3, 6, "[ ]"]
      Paragraph[7, 67]
        Text[7, 11] chars:[7, 11, "item"]
        SoftLineBreak[11, 12]
        Text[12, 36] chars:[12, 36, "with  … lines"]
        SoftLineBreak[36, 37]
        Text[37, 66] chars:[37, 66, "all s … apped"]
  HtmlCommentBlock[67, 76]
  BulletList[76, 141] isTight
    TaskListItem[76, 141] open:[76, 77, "-"] isTight taskOpen:[78, 81, "[ ]"]
      Paragraph[82, 141]
        Text[82, 86] chars:[82, 86, "item"]
        SoftLineBreak[86, 87]
        Text[87, 111] chars:[87, 111, "with  … lines"]
        SoftLineBreak[111, 112]
        Text[112, 141] chars:[112, 141, "all s … apped"]
````````````````````````````````


Wrap individual lines in source position spans loose items

```````````````````````````````` example(Source Position Attribute: 3) options(src-pos, src-pos-lines)
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
    <p md-pos="0-12"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" /><span md-pos="7-11">item</span></p>
  </li>
  <li class="task-list-item">
    <p md-pos="13-80"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" /><span md-pos="20-24">item</span>
    <span md-pos="25-49">with multiple lazy lines</span>
    <span md-pos="50-79">all should be src pos wrapped</span></p>
  </li>
</ol>
<!-- -->
<ul>
  <li class="task-list-item">
    <p md-pos="89-100"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" /><span md-pos="95-99">item</span></p>
  </li>
  <li class="task-list-item">
    <p md-pos="101-166"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" /><span md-pos="107-111">item</span>
    <span md-pos="112-136">with multiple lazy lines</span>
    <span md-pos="137-166">all should be src pos wrapped</span></p>
  </li>
</ul>
.
Document[0, 166]
  OrderedList[0, 80] isLoose delimiter:'.'
    TaskListItem[0, 12] open:[0, 2, "1."] isLoose taskOpen:[3, 6, "[ ]"]
      Paragraph[7, 12]
        Text[7, 11] chars:[7, 11, "item"]
    TaskListItem[13, 80] open:[13, 15, "1."] isLoose taskOpen:[16, 19, "[ ]"]
      Paragraph[20, 80]
        Text[20, 24] chars:[20, 24, "item"]
        SoftLineBreak[24, 25]
        Text[25, 49] chars:[25, 49, "with  … lines"]
        SoftLineBreak[49, 50]
        Text[50, 79] chars:[50, 79, "all s … apped"]
  HtmlCommentBlock[80, 89]
  BulletList[89, 166] isLoose
    TaskListItem[89, 100] open:[89, 90, "-"] isLoose taskOpen:[91, 94, "[ ]"]
      Paragraph[95, 100]
        Text[95, 99] chars:[95, 99, "item"]
    TaskListItem[101, 166] open:[101, 102, "-"] isLoose taskOpen:[103, 106, "[ ]"]
      Paragraph[107, 166]
        Text[107, 111] chars:[107, 111, "item"]
        SoftLineBreak[111, 112]
        Text[112, 136] chars:[112, 136, "with  … lines"]
        SoftLineBreak[136, 137]
        Text[137, 166] chars:[137, 166, "all s … apped"]
````````````````````````````````


