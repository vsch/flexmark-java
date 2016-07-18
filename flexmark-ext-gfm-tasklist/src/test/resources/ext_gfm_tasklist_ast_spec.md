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
  BulletList[0, 96] isTight=true
    BulletListItem[0, 5] open:[0, 1, "-"]
      Paragraph[2, 5]
        LinkRef[2, 4] referenceOpen:[2, 3, "["] reference:[3, 3] referenceClose:[3, 4, "]"]
    BulletListItem[5, 12] open:[5, 6, "-"]
      Paragraph[7, 12]
        LinkRef[7, 11] referenceOpen:[7, 8, "["] reference:[8, 9, "x"] referenceClose:[10, 11, "]"]
          Text[8, 10] chars:[8, 10, "x "]
    BulletListItem[12, 19] open:[12, 13, "-"]
      Paragraph[14, 19]
        LinkRef[14, 18] referenceOpen:[14, 15, "["] reference:[16, 17, "x"] referenceClose:[17, 18, "]"]
          Text[15, 17] chars:[15, 17, " x"]
    BulletListItem[19, 27] open:[19, 20, "-"]
      Paragraph[21, 27]
        LinkRef[21, 26] referenceOpen:[21, 22, "["] reference:[23, 24, "x"] referenceClose:[25, 26, "]"]
          Text[22, 25] chars:[22, 25, " x "]
    BulletListItem[27, 33] open:[27, 28, "-"]
      Paragraph[29, 33]
        LinkRef[29, 32] referenceOpen:[29, 30, "["] reference:[30, 31, "a"] referenceClose:[31, 32, "]"]
          Text[30, 31] chars:[30, 31, "a"]
    BulletListItem[33, 42] open:[33, 34, "-"]
      Paragraph[35, 42]
        LinkRef[35, 38] referenceOpen:[35, 36, "["] reference:[37, 37] referenceClose:[37, 38, "]"]
          Text[36, 37] chars:[36, 37, " "]
        Text[38, 41] chars:[38, 41, "abc"]
    BulletListItem[42, 51] open:[42, 43, "-"]
      Paragraph[44, 51]
        LinkRef[44, 47] referenceOpen:[44, 45, "["] reference:[45, 46, "x"] referenceClose:[46, 47, "]"]
          Text[45, 46] chars:[45, 46, "x"]
        Text[47, 50] chars:[47, 50, "abc"]
    BulletListItem[51, 60] open:[51, 52, "-"]
      Paragraph[53, 60]
        LinkRef[53, 56] referenceOpen:[53, 54, "["] reference:[54, 55, "x"] referenceClose:[55, 56, "]"]
          Text[54, 55] chars:[54, 55, "x"]
        Text[56, 59] chars:[56, 59, "abc"]
    BulletListItem[60, 72] open:[60, 61, "-"]
      Paragraph[62, 72]
        Text[62, 63] chars:[62, 63, "a"]
        LinkRef[63, 66] referenceOpen:[63, 64, "["] reference:[65, 65] referenceClose:[65, 66, "]"]
          Text[64, 65] chars:[64, 65, " "]
        Text[66, 71] chars:[66, 71, " task"]
    BulletListItem[72, 84] open:[72, 73, "-"]
      Paragraph[74, 84]
        Text[74, 75] chars:[74, 75, "b"]
        LinkRef[75, 78] referenceOpen:[75, 76, "["] reference:[76, 77, "x"] referenceClose:[77, 78, "]"]
          Text[76, 77] chars:[76, 77, "x"]
        Text[78, 83] chars:[78, 83, " task"]
    BulletListItem[84, 96] open:[84, 85, "-"]
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
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled"></input></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input></li>
</ul>
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled"></input></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input></li>
</ul>
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled"></input></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input></li>
</ul>
.
Document[0, 62]
  BulletList[0, 22] isTight=true
    TaskListItem[0, 6] open:[0, 1, "-"] taskOpen:[2, 5, "[ ]"]
    TaskListItem[6, 13] open:[6, 7, "-"] taskOpen:[9, 12, "[X]"]
    TaskListItem[13, 22] open:[13, 14, "-"] taskOpen:[18, 21, "[x]"]
  BulletList[23, 43] isTight=false
    TaskListItem[23, 29] open:[23, 24, "*"] taskOpen:[25, 28, "[ ]"]
    TaskListItem[30, 36] open:[30, 31, "*"] taskOpen:[32, 35, "[x]"]
    TaskListItem[37, 43] open:[37, 38, "*"] taskOpen:[39, 42, "[X]"]
  BulletList[44, 62] isTight=true
    TaskListItem[44, 50] open:[44, 45, "+"] taskOpen:[46, 49, "[ ]"]
    TaskListItem[50, 56] open:[50, 51, "+"] taskOpen:[52, 55, "[x]"]
    TaskListItem[56, 62] open:[56, 57, "+"] taskOpen:[58, 61, "[X]"]
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
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled"></input>task</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task</li>
</ul>
<ul>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled"></input>task</p>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task</p>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task</p>
  </li>
</ul>
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled"></input>task</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task</li>
</ul>
.
Document[0, 103]
  BulletList[0, 33] isTight=true
    TaskListItem[0, 11] open:[0, 1, "-"] taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 22] open:[11, 12, "-"] taskOpen:[13, 16, "[X]"]
      Paragraph[17, 22]
        Text[17, 21] chars:[17, 21, "task"]
    TaskListItem[22, 33] open:[22, 23, "-"] taskOpen:[24, 27, "[x]"]
      Paragraph[28, 33]
        Text[28, 32] chars:[28, 32, "task"]
  BulletList[34, 69] isTight=false
    TaskListItem[34, 45] open:[34, 35, "*"] taskOpen:[36, 39, "[ ]"]
      Paragraph[40, 45]
        Text[40, 44] chars:[40, 44, "task"]
    TaskListItem[46, 57] open:[46, 47, "*"] taskOpen:[48, 51, "[x]"]
      Paragraph[52, 57]
        Text[52, 56] chars:[52, 56, "task"]
    TaskListItem[58, 69] open:[58, 59, "*"] taskOpen:[60, 63, "[X]"]
      Paragraph[64, 69]
        Text[64, 68] chars:[64, 68, "task"]
  BulletList[70, 103] isTight=true
    TaskListItem[70, 81] open:[70, 71, "+"] taskOpen:[72, 75, "[ ]"]
      Paragraph[76, 81]
        Text[76, 80] chars:[76, 80, "task"]
    TaskListItem[81, 92] open:[81, 82, "+"] taskOpen:[83, 86, "[x]"]
      Paragraph[87, 92]
        Text[87, 91] chars:[87, 91, "task"]
    TaskListItem[92, 103] open:[92, 93, "+"] taskOpen:[94, 97, "[X]"]
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
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled"></input>task <strong>emphasis</strong></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task <strong>emphasis</strong></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task <strong>emphasis</strong></li>
</ul>
<ul>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled"></input>task <strong>emphasis</strong></p>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task <strong>emphasis</strong></p>
  </li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task <strong>emphasis</strong></p>
  </li>
</ul>
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled"></input>task <strong>emphasis</strong></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task <strong>emphasis</strong></li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task <strong>emphasis</strong></li>
</ul>
.
Document[0, 220]
  BulletList[0, 72] isTight=true
    TaskListItem[0, 24] open:[0, 1, "-"] taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 24]
        Text[6, 11] chars:[6, 11, "task "]
        StrongEmphasis[11, 23] textOpen:[11, 13, "**"] text:[13, 21, "emphasis"] textClose:[21, 23, "**"]
          Text[13, 21] chars:[13, 21, "emphasis"]
    TaskListItem[24, 48] open:[24, 25, "-"] taskOpen:[26, 29, "[X]"]
      Paragraph[30, 48]
        Text[30, 35] chars:[30, 35, "task "]
        StrongEmphasis[35, 47] textOpen:[35, 37, "**"] text:[37, 45, "emphasis"] textClose:[45, 47, "**"]
          Text[37, 45] chars:[37, 45, "emphasis"]
    TaskListItem[48, 72] open:[48, 49, "-"] taskOpen:[50, 53, "[x]"]
      Paragraph[54, 72]
        Text[54, 59] chars:[54, 59, "task "]
        StrongEmphasis[59, 71] textOpen:[59, 61, "**"] text:[61, 69, "emphasis"] textClose:[69, 71, "**"]
          Text[61, 69] chars:[61, 69, "emphasis"]
  BulletList[73, 147] isTight=false
    TaskListItem[73, 97] open:[73, 74, "*"] taskOpen:[75, 78, "[ ]"]
      Paragraph[79, 97]
        Text[79, 84] chars:[79, 84, "task "]
        StrongEmphasis[84, 96] textOpen:[84, 86, "**"] text:[86, 94, "emphasis"] textClose:[94, 96, "**"]
          Text[86, 94] chars:[86, 94, "emphasis"]
    TaskListItem[98, 122] open:[98, 99, "*"] taskOpen:[100, 103, "[x]"]
      Paragraph[104, 122]
        Text[104, 109] chars:[104, 109, "task "]
        StrongEmphasis[109, 121] textOpen:[109, 111, "**"] text:[111, 119, "emphasis"] textClose:[119, 121, "**"]
          Text[111, 119] chars:[111, 119, "emphasis"]
    TaskListItem[123, 147] open:[123, 124, "*"] taskOpen:[125, 128, "[X]"]
      Paragraph[129, 147]
        Text[129, 134] chars:[129, 134, "task "]
        StrongEmphasis[134, 146] textOpen:[134, 136, "**"] text:[136, 144, "emphasis"] textClose:[144, 146, "**"]
          Text[136, 144] chars:[136, 144, "emphasis"]
  BulletList[148, 220] isTight=true
    TaskListItem[148, 172] open:[148, 149, "+"] taskOpen:[150, 153, "[ ]"]
      Paragraph[154, 172]
        Text[154, 159] chars:[154, 159, "task "]
        StrongEmphasis[159, 171] textOpen:[159, 161, "**"] text:[161, 169, "emphasis"] textClose:[169, 171, "**"]
          Text[161, 169] chars:[161, 169, "emphasis"]
    TaskListItem[172, 196] open:[172, 173, "+"] taskOpen:[174, 177, "[x]"]
      Paragraph[178, 196]
        Text[178, 183] chars:[178, 183, "task "]
        StrongEmphasis[183, 195] textOpen:[183, 185, "**"] text:[185, 193, "emphasis"] textClose:[193, 195, "**"]
          Text[185, 193] chars:[185, 193, "emphasis"]
    TaskListItem[196, 220] open:[196, 197, "+"] taskOpen:[198, 201, "[X]"]
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
  <li><input type="checkbox" class="task-list-item-checkbox" disabled="disabled"></input>task</li>
  <li><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task</li>
</ul>
.
Document[0, 22]
  BulletList[0, 22] isTight=true
    TaskListItem[0, 11] open:[0, 1, "-"] taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 22] open:[11, 12, "-"] taskOpen:[13, 16, "[x]"]
      Paragraph[17, 22]
        Text[17, 21] chars:[17, 21, "task"]
````````````````````````````````


task list item class on loose list

```````````````````````````````` example(Gfm Task List Options: 2) options(item-class)
- [ ] task

- [x] task
.
<ul>
  <li>
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled"></input>task</p>
  </li>
  <li>
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task</p>
  </li>
</ul>
.
Document[0, 23]
  BulletList[0, 23] isTight=false
    TaskListItem[0, 11] open:[0, 1, "-"] taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[12, 23] open:[12, 13, "-"] taskOpen:[14, 17, "[x]"]
      Paragraph[18, 23]
        Text[18, 22] chars:[18, 22, "task"]
````````````````````````````````


loose task list item class

```````````````````````````````` example(Gfm Task List Options: 3) options(loose-class)
- [ ] task
- [x] task
.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled"></input>task</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task</li>
</ul>
.
Document[0, 22]
  BulletList[0, 22] isTight=true
    TaskListItem[0, 11] open:[0, 1, "-"] taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 22] open:[11, 12, "-"] taskOpen:[13, 16, "[x]"]
      Paragraph[17, 22]
        Text[17, 21] chars:[17, 21, "task"]
````````````````````````````````


loose task list item class on loose list

```````````````````````````````` example(Gfm Task List Options: 4) options(loose-class)
- [ ] task

- [x] task
.
<ul>
  <li>
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled"></input>task</p>
  </li>
  <li>
    <p><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task</p>
  </li>
</ul>
.
Document[0, 23]
  BulletList[0, 23] isTight=false
    TaskListItem[0, 11] open:[0, 1, "-"] taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[12, 23] open:[12, 13, "-"] taskOpen:[14, 17, "[x]"]
      Paragraph[18, 23]
        Text[18, 22] chars:[18, 22, "task"]
````````````````````````````````


task list item class and p class

```````````````````````````````` example(Gfm Task List Options: 5) options(item-class, p-class)
- [ ] task
- [x] task
.
<ul>
  <li><input type="checkbox" class="task-list-item-checkbox" disabled="disabled"></input>task</li>
  <li><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task</li>
</ul>
.
Document[0, 22]
  BulletList[0, 22] isTight=true
    TaskListItem[0, 11] open:[0, 1, "-"] taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 22] open:[11, 12, "-"] taskOpen:[13, 16, "[x]"]
      Paragraph[17, 22]
        Text[17, 21] chars:[17, 21, "task"]
````````````````````````````````


task list item class and p class on loose list

```````````````````````````````` example(Gfm Task List Options: 6) options(item-class, p-class)
- [ ] task

- [x] task
.
<ul>
  <li>
    <p class="task-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled"></input>task</p>
  </li>
  <li>
    <p class="task-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled"></input>task</p>
  </li>
</ul>
.
Document[0, 23]
  BulletList[0, 23] isTight=false
    TaskListItem[0, 11] open:[0, 1, "-"] taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[12, 23] open:[12, 13, "-"] taskOpen:[14, 17, "[x]"]
      Paragraph[18, 23]
        Text[18, 22] chars:[18, 22, "task"]
````````````````````````````````


custom marker task list item class and p class

```````````````````````````````` example(Gfm Task List Options: 7) options(item-class, p-class, done)
- [ ] task
- [x] task
.
<ul>
  <li><input type="checkbox" class="task-list-item-checkbox" disabled="disabled"></input>task</li>
  <li><span class="taskitem">X</span>task</li>
</ul>
.
Document[0, 22]
  BulletList[0, 22] isTight=true
    TaskListItem[0, 11] open:[0, 1, "-"] taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 22] open:[11, 12, "-"] taskOpen:[13, 16, "[x]"]
      Paragraph[17, 22]
        Text[17, 21] chars:[17, 21, "task"]
````````````````````````````````


task list item class and p class on loose list

```````````````````````````````` example(Gfm Task List Options: 8) options(item-class, p-class, done)
- [ ] task

- [x] task
.
<ul>
  <li>
    <p class="task-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled"></input>task</p>
  </li>
  <li>
    <p class="task-item"><span class="taskitem">X</span>task</p>
  </li>
</ul>
.
Document[0, 23]
  BulletList[0, 23] isTight=false
    TaskListItem[0, 11] open:[0, 1, "-"] taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[12, 23] open:[12, 13, "-"] taskOpen:[14, 17, "[x]"]
      Paragraph[18, 23]
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
Document[0, 22]
  BulletList[0, 22] isTight=true
    TaskListItem[0, 11] open:[0, 1, "-"] taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[11, 22] open:[11, 12, "-"] taskOpen:[13, 16, "[x]"]
      Paragraph[17, 22]
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
Document[0, 23]
  BulletList[0, 23] isTight=false
    TaskListItem[0, 11] open:[0, 1, "-"] taskOpen:[2, 5, "[ ]"]
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
    TaskListItem[12, 23] open:[12, 13, "-"] taskOpen:[14, 17, "[x]"]
      Paragraph[18, 23]
        Text[18, 22] chars:[18, 22, "task"]
````````````````````````````````


