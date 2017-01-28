---
title: Formatter Extension Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Task List Items

default

```````````````````````````````` example Task List Items: 1
* list item 1
  * [ ] list item 1.1
  * [x] list item 1.2
        * list item 1.2.1
        * [ ] list item 1.2.2
        * [X] list item 1.2.3
* list item 2
  * list item 2.1
  * [x] list item 2.2
        * list item 2.2.1
        * list item 2.2.2
        * [X] list item 2.2.3
* list item 3
  * [ ] list item 3.1
  * list item 3.2
    * list item 3.2.1
    * [ ] list item 3.2.2
    * list item 3.2.3
* list item 4
  * list item 4.1
  * list item 4.2
    * list item 4.2.1
    * list item 4.2.2
    * list item 4.2.3
* list item 5
  * [ ] list item 5.1
  * [x] list item 5.2
        * list item 5.2.1
        * [ ] list item 5.2.2
        * [X] list item 5.2.3
.
* list item 1
  * [ ] list item 1.1
  * [x] list item 1.2
        * list item 1.2.1
        * [ ] list item 1.2.2
        * [X] list item 1.2.3
* list item 2
  * list item 2.1
  * [x] list item 2.2
        * list item 2.2.1
        * list item 2.2.2
        * [X] list item 2.2.3
* list item 3
  * [ ] list item 3.1
  * list item 3.2
    * list item 3.2.1
    * [ ] list item 3.2.2
    * list item 3.2.3
* list item 4
  * list item 4.1
  * list item 4.2
    * list item 4.2.1
    * list item 4.2.2
    * list item 4.2.3
* list item 5
  * [ ] list item 5.1
  * [x] list item 5.2
        * list item 5.2.1
        * [ ] list item 5.2.2
        * [X] list item 5.2.3
.
Document[0, 664]
  BulletList[0, 664] isTight
    BulletListItem[0, 144] open:[0, 1, "*"] isTight
      Paragraph[2, 14]
        Text[2, 13] chars:[2, 13, "list  … tem 1"]
      BulletList[16, 144] isTight
        TaskListItem[16, 36] open:[16, 17, "*"] openSuffix:[18, 21, "[ ]"] isTight isNotDone
          Paragraph[22, 36]
            Text[22, 35] chars:[22, 35, "list  … m 1.1"]
        TaskListItem[38, 144] open:[38, 39, "*"] openSuffix:[40, 43, "[x]"] isTight isDone
          Paragraph[44, 58]
            Text[44, 57] chars:[44, 57, "list  … m 1.2"]
          BulletList[66, 144] isTight
            BulletListItem[66, 84] open:[66, 67, "*"] isTight
              Paragraph[68, 84]
                Text[68, 83] chars:[68, 83, "list  … 1.2.1"]
            TaskListItem[92, 114] open:[92, 93, "*"] openSuffix:[94, 97, "[ ]"] isTight isNotDone
              Paragraph[98, 114]
                Text[98, 113] chars:[98, 113, "list  … 1.2.2"]
            TaskListItem[122, 144] open:[122, 123, "*"] openSuffix:[124, 127, "[X]"] isTight isDone
              Paragraph[128, 144]
                Text[128, 143] chars:[128, 143, "list  … 1.2.3"]
    BulletListItem[144, 280] open:[144, 145, "*"] isTight
      Paragraph[146, 158]
        Text[146, 157] chars:[146, 157, "list  … tem 2"]
      BulletList[160, 280] isTight
        BulletListItem[160, 176] open:[160, 161, "*"] isTight
          Paragraph[162, 176]
            Text[162, 175] chars:[162, 175, "list  … m 2.1"]
        TaskListItem[178, 280] open:[178, 179, "*"] openSuffix:[180, 183, "[x]"] isTight isDone
          Paragraph[184, 198]
            Text[184, 197] chars:[184, 197, "list  … m 2.2"]
          BulletList[206, 280] isTight
            BulletListItem[206, 224] open:[206, 207, "*"] isTight
              Paragraph[208, 224]
                Text[208, 223] chars:[208, 223, "list  … 2.2.1"]
            BulletListItem[232, 250] open:[232, 233, "*"] isTight
              Paragraph[234, 250]
                Text[234, 249] chars:[234, 249, "list  … 2.2.2"]
            TaskListItem[258, 280] open:[258, 259, "*"] openSuffix:[260, 263, "[X]"] isTight isDone
              Paragraph[264, 280]
                Text[264, 279] chars:[264, 279, "list  … 2.2.3"]
    BulletListItem[280, 404] open:[280, 281, "*"] isTight
      Paragraph[282, 294]
        Text[282, 293] chars:[282, 293, "list  … tem 3"]
      BulletList[296, 404] isTight
        TaskListItem[296, 316] open:[296, 297, "*"] openSuffix:[298, 301, "[ ]"] isTight isNotDone
          Paragraph[302, 316]
            Text[302, 315] chars:[302, 315, "list  … m 3.1"]
        BulletListItem[318, 404] open:[318, 319, "*"] isTight
          Paragraph[320, 334]
            Text[320, 333] chars:[320, 333, "list  … m 3.2"]
          BulletList[338, 404] isTight
            BulletListItem[338, 356] open:[338, 339, "*"] isTight
              Paragraph[340, 356]
                Text[340, 355] chars:[340, 355, "list  … 3.2.1"]
            TaskListItem[360, 382] open:[360, 361, "*"] openSuffix:[362, 365, "[ ]"] isTight isNotDone
              Paragraph[366, 382]
                Text[366, 381] chars:[366, 381, "list  … 3.2.2"]
            BulletListItem[386, 404] open:[386, 387, "*"] isTight
              Paragraph[388, 404]
                Text[388, 403] chars:[388, 403, "list  … 3.2.3"]
    BulletListItem[404, 520] open:[404, 405, "*"] isTight
      Paragraph[406, 418]
        Text[406, 417] chars:[406, 417, "list  … tem 4"]
      BulletList[420, 520] isTight
        BulletListItem[420, 436] open:[420, 421, "*"] isTight
          Paragraph[422, 436]
            Text[422, 435] chars:[422, 435, "list  … m 4.1"]
        BulletListItem[438, 520] open:[438, 439, "*"] isTight
          Paragraph[440, 454]
            Text[440, 453] chars:[440, 453, "list  … m 4.2"]
          BulletList[458, 520] isTight
            BulletListItem[458, 476] open:[458, 459, "*"] isTight
              Paragraph[460, 476]
                Text[460, 475] chars:[460, 475, "list  … 4.2.1"]
            BulletListItem[480, 498] open:[480, 481, "*"] isTight
              Paragraph[482, 498]
                Text[482, 497] chars:[482, 497, "list  … 4.2.2"]
            BulletListItem[502, 520] open:[502, 503, "*"] isTight
              Paragraph[504, 520]
                Text[504, 519] chars:[504, 519, "list  … 4.2.3"]
    BulletListItem[520, 664] open:[520, 521, "*"] isTight
      Paragraph[522, 534]
        Text[522, 533] chars:[522, 533, "list  … tem 5"]
      BulletList[536, 664] isTight
        TaskListItem[536, 556] open:[536, 537, "*"] openSuffix:[538, 541, "[ ]"] isTight isNotDone
          Paragraph[542, 556]
            Text[542, 555] chars:[542, 555, "list  … m 5.1"]
        TaskListItem[558, 664] open:[558, 559, "*"] openSuffix:[560, 563, "[x]"] isTight isDone
          Paragraph[564, 578]
            Text[564, 577] chars:[564, 577, "list  … m 5.2"]
          BulletList[586, 664] isTight
            BulletListItem[586, 604] open:[586, 587, "*"] isTight
              Paragraph[588, 604]
                Text[588, 603] chars:[588, 603, "list  … 5.2.1"]
            TaskListItem[612, 634] open:[612, 613, "*"] openSuffix:[614, 617, "[ ]"] isTight isNotDone
              Paragraph[618, 634]
                Text[618, 633] chars:[618, 633, "list  … 5.2.2"]
            TaskListItem[642, 664] open:[642, 643, "*"] openSuffix:[644, 647, "[X]"] isTight isDone
              Paragraph[648, 664]
                Text[648, 663] chars:[648, 663, "list  … 5.2.3"]
````````````````````````````````


```````````````````````````````` example(Task List Items: 2) options(format-fixed-indent)
* list item 1
  * [ ] list item 1.1
  * [x] list item 1.2
        * list item 1.2.1
        * [ ] list item 1.2.2
        * [X] list item 1.2.3
* list item 2
  * list item 2.1
  * [x] list item 2.2
        * list item 2.2.1
        * list item 2.2.2
        * [X] list item 2.2.3
* list item 3
  * [ ] list item 3.1
  * list item 3.2
    * list item 3.2.1
    * [ ] list item 3.2.2
    * list item 3.2.3
* list item 4
  * list item 4.1
  * list item 4.2
    * list item 4.2.1
    * list item 4.2.2
    * list item 4.2.3
* list item 5
  * [ ] list item 5.1
  * [x] list item 5.2
        * list item 5.2.1
        * [ ] list item 5.2.2
        * [X] list item 5.2.3
.
* list item 1
    * [ ] list item 1.1
    * [x] list item 1.2
        * list item 1.2.1
        * [ ] list item 1.2.2
        * [X] list item 1.2.3
* list item 2
    * list item 2.1
    * [x] list item 2.2
        * list item 2.2.1
        * list item 2.2.2
        * [X] list item 2.2.3
* list item 3
    * [ ] list item 3.1
    * list item 3.2
        * list item 3.2.1
        * [ ] list item 3.2.2
        * list item 3.2.3
* list item 4
    * list item 4.1
    * list item 4.2
        * list item 4.2.1
        * list item 4.2.2
        * list item 4.2.3
* list item 5
    * [ ] list item 5.1
    * [x] list item 5.2
        * list item 5.2.1
        * [ ] list item 5.2.2
        * [X] list item 5.2.3
````````````````````````````````


```````````````````````````````` example(Task List Items: 3) options(task-case-lowercase)
* list item 1
  * [ ] list item 1.1
  * [x] list item 1.2
        * list item 1.2.1
        * [ ] list item 1.2.2
        * [X] list item 1.2.3
* list item 2
  * list item 2.1
  * [x] list item 2.2
        * list item 2.2.1
        * list item 2.2.2
        * [X] list item 2.2.3
* list item 3
  * [ ] list item 3.1
  * list item 3.2
    * list item 3.2.1
    * [ ] list item 3.2.2
    * list item 3.2.3
* list item 4
  * list item 4.1
  * list item 4.2
    * list item 4.2.1
    * list item 4.2.2
    * list item 4.2.3
* list item 5
  * [ ] list item 5.1
  * [x] list item 5.2
        * list item 5.2.1
        * [ ] list item 5.2.2
        * [X] list item 5.2.3
.
* list item 1
  * [ ] list item 1.1
  * [x] list item 1.2
        * list item 1.2.1
        * [ ] list item 1.2.2
        * [x] list item 1.2.3
* list item 2
  * list item 2.1
  * [x] list item 2.2
        * list item 2.2.1
        * list item 2.2.2
        * [x] list item 2.2.3
* list item 3
  * [ ] list item 3.1
  * list item 3.2
    * list item 3.2.1
    * [ ] list item 3.2.2
    * list item 3.2.3
* list item 4
  * list item 4.1
  * list item 4.2
    * list item 4.2.1
    * list item 4.2.2
    * list item 4.2.3
* list item 5
  * [ ] list item 5.1
  * [x] list item 5.2
        * list item 5.2.1
        * [ ] list item 5.2.2
        * [x] list item 5.2.3
````````````````````````````````


```````````````````````````````` example(Task List Items: 4) options(task-case-uppercase)
* list item 1
  * [ ] list item 1.1
  * [x] list item 1.2
        * list item 1.2.1
        * [ ] list item 1.2.2
        * [X] list item 1.2.3
* list item 2
  * list item 2.1
  * [x] list item 2.2
        * list item 2.2.1
        * list item 2.2.2
        * [X] list item 2.2.3
* list item 3
  * [ ] list item 3.1
  * list item 3.2
    * list item 3.2.1
    * [ ] list item 3.2.2
    * list item 3.2.3
* list item 4
  * list item 4.1
  * list item 4.2
    * list item 4.2.1
    * list item 4.2.2
    * list item 4.2.3
* list item 5
  * [ ] list item 5.1
  * [x] list item 5.2
        * list item 5.2.1
        * [ ] list item 5.2.2
        * [X] list item 5.2.3
.
* list item 1
  * [ ] list item 1.1
  * [X] list item 1.2
        * list item 1.2.1
        * [ ] list item 1.2.2
        * [X] list item 1.2.3
* list item 2
  * list item 2.1
  * [X] list item 2.2
        * list item 2.2.1
        * list item 2.2.2
        * [X] list item 2.2.3
* list item 3
  * [ ] list item 3.1
  * list item 3.2
    * list item 3.2.1
    * [ ] list item 3.2.2
    * list item 3.2.3
* list item 4
  * list item 4.1
  * list item 4.2
    * list item 4.2.1
    * list item 4.2.2
    * list item 4.2.3
* list item 5
  * [ ] list item 5.1
  * [X] list item 5.2
        * list item 5.2.1
        * [ ] list item 5.2.2
        * [X] list item 5.2.3
````````````````````````````````


sort undone first

```````````````````````````````` example(Task List Items: 5) options(task-placement-incomplete-first)
* list item 1
  * [ ] list item 1.1
  * [x] list item 1.2
        * list item 1.2.1
        * [ ] list item 1.2.2
        * [X] list item 1.2.3
* list item 2
  * list item 2.1
  * [x] list item 2.2
        * list item 2.2.1
        * list item 2.2.2
        * [X] list item 2.2.3
* list item 3
  * [ ] list item 3.1
  * list item 3.2
    * list item 3.2.1
    * [ ] list item 3.2.2
    * list item 3.2.3
* list item 4
  * list item 4.1
  * list item 4.2
    * list item 4.2.1
    * list item 4.2.2
    * list item 4.2.3
* list item 5
  * [ ] list item 5.1
  * [x] list item 5.2
        * list item 5.2.1
        * [ ] list item 5.2.2
        * [X] list item 5.2.3
.
* list item 1
  * [ ] list item 1.1
  * [x] list item 1.2
        * [ ] list item 1.2.2
        * list item 1.2.1
        * [X] list item 1.2.3
* list item 2
  * list item 2.1
  * [x] list item 2.2
        * list item 2.2.1
        * list item 2.2.2
        * [X] list item 2.2.3
* list item 3
  * [ ] list item 3.1
  * list item 3.2
    * [ ] list item 3.2.2
    * list item 3.2.1
    * list item 3.2.3
* list item 4
  * list item 4.1
  * list item 4.2
    * list item 4.2.1
    * list item 4.2.2
    * list item 4.2.3
* list item 5
  * [ ] list item 5.1
  * [x] list item 5.2
        * [ ] list item 5.2.2
        * list item 5.2.1
        * [X] list item 5.2.3
````````````````````````````````


sort undone first, complete to non-tasks

```````````````````````````````` example(Task List Items: 6) options(task-placement-complete-to-non-task)
* list item 1
  * [ ] list item 1.1
  * [x] list item 1.2
        * list item 1.2.1
        * [ ] list item 1.2.2
        * [X] list item 1.2.3
* list item 2
  * list item 2.1
  * [x] list item 2.2
        * list item 2.2.1
        * list item 2.2.2
        * [X] list item 2.2.3
* list item 3
  * [ ] list item 3.1
  * list item 3.2
    * list item 3.2.1
    * [ ] list item 3.2.2
    * list item 3.2.3
* list item 4
  * list item 4.1
  * list item 4.2
    * list item 4.2.1
    * list item 4.2.2
    * list item 4.2.3
* list item 5
  * [ ] list item 5.1
  * [x] list item 5.2
        * list item 5.2.1
        * [ ] list item 5.2.2
        * [X] list item 5.2.3
.
* list item 1
  * [ ] list item 1.1
  * list item 1.2
    * [ ] list item 1.2.2
    * list item 1.2.1
    * list item 1.2.3
* list item 2
  * list item 2.1
  * list item 2.2
    * list item 2.2.1
    * list item 2.2.2
    * list item 2.2.3
* list item 3
  * [ ] list item 3.1
  * list item 3.2
    * [ ] list item 3.2.2
    * list item 3.2.1
    * list item 3.2.3
* list item 4
  * list item 4.1
  * list item 4.2
    * list item 4.2.1
    * list item 4.2.2
    * list item 4.2.3
* list item 5
  * [ ] list item 5.1
  * list item 5.2
    * [ ] list item 5.2.2
    * list item 5.2.1
    * list item 5.2.3
````````````````````````````````


sort undone or has undone first

```````````````````````````````` example(Task List Items: 7) options(task-placement-incomplete-nested-first)
* list item 1
  * [ ] list item 1.1
  * [x] list item 1.2
        * list item 1.2.1
        * [ ] list item 1.2.2
        * [X] list item 1.2.3
* list item 2
  * list item 2.1
  * [x] list item 2.2
        * list item 2.2.1
        * list item 2.2.2
        * [X] list item 2.2.3
* list item 3
  * [ ] list item 3.1
  * list item 3.2
    * list item 3.2.1
    * [ ] list item 3.2.2
    * list item 3.2.3
* list item 4
  * list item 4.1
  * list item 4.2
    * list item 4.2.1
    * list item 4.2.2
    * list item 4.2.3
* list item 5
  * [ ] list item 5.1
  * [x] list item 5.2
        * list item 5.2.1
        * [ ] list item 5.2.2
        * [X] list item 5.2.3
.
* list item 1
  * [ ] list item 1.1
  * [x] list item 1.2
        * [ ] list item 1.2.2
        * list item 1.2.1
        * [X] list item 1.2.3
* list item 3
  * [ ] list item 3.1
  * list item 3.2
    * [ ] list item 3.2.2
    * list item 3.2.1
    * list item 3.2.3
* list item 5
  * [ ] list item 5.1
  * [x] list item 5.2
        * [ ] list item 5.2.2
        * list item 5.2.1
        * [X] list item 5.2.3
* list item 2
  * list item 2.1
  * [x] list item 2.2
        * list item 2.2.1
        * list item 2.2.2
        * [X] list item 2.2.3
* list item 4
  * list item 4.1
  * list item 4.2
    * list item 4.2.1
    * list item 4.2.2
    * list item 4.2.3
````````````````````````````````


sort undone or has undone first, complete to non-tasks

```````````````````````````````` example(Task List Items: 8) options(task-placement-complete-nested-to-non-task)
* list item 1
  * [ ] list item 1.1
  * [x] list item 1.2
        * list item 1.2.1
        * [ ] list item 1.2.2
        * [X] list item 1.2.3
* list item 2
  * list item 2.1
  * [x] list item 2.2
        * list item 2.2.1
        * list item 2.2.2
        * [X] list item 2.2.3
* list item 3
  * [ ] list item 3.1
  * list item 3.2
    * list item 3.2.1
    * [ ] list item 3.2.2
    * list item 3.2.3
* list item 4
  * list item 4.1
  * list item 4.2
    * list item 4.2.1
    * list item 4.2.2
    * list item 4.2.3
* list item 5
  * [ ] list item 5.1
  * [x] list item 5.2
        * list item 5.2.1
        * [ ] list item 5.2.2
        * [X] list item 5.2.3
.
* list item 1
  * [ ] list item 1.1
  * list item 1.2
    * [ ] list item 1.2.2
    * list item 1.2.1
    * list item 1.2.3
* list item 3
  * [ ] list item 3.1
  * list item 3.2
    * [ ] list item 3.2.2
    * list item 3.2.1
    * list item 3.2.3
* list item 5
  * [ ] list item 5.1
  * list item 5.2
    * [ ] list item 5.2.2
    * list item 5.2.1
    * list item 5.2.3
* list item 2
  * list item 2.1
  * list item 2.2
    * list item 2.2.1
    * list item 2.2.2
    * list item 2.2.3
* list item 4
  * list item 4.1
  * list item 4.2
    * list item 4.2.1
    * list item 4.2.2
    * list item 4.2.3
````````````````````````````````


sort undone or has undone first, complete to non-tasks

```````````````````````````````` example(Task List Items: 9) options(task-placement-incomplete-nested-first)
* list item 2
  * list item 2.1
  * [x] list item 2.2
        * list item 2.2.1
        * list item 2.2.2
        * [X] list item 2.2.3
* list item 3
  * [ ] list item 3.1
  * list item 3.2
    * list item 3.2.1
    * [ ] list item 3.2.2
    * list item 3.2.3
.
* list item 3
  * [ ] list item 3.1
  * list item 3.2
    * [ ] list item 3.2.2
    * list item 3.2.1
    * list item 3.2.3
* list item 2
  * list item 2.1
  * [x] list item 2.2
        * list item 2.2.1
        * list item 2.2.2
        * [X] list item 2.2.3
.
Document[0, 259]
  BulletList[0, 259] isTight
    BulletListItem[0, 136] open:[0, 1, "*"] isTight
      Paragraph[2, 14]
        Text[2, 13] chars:[2, 13, "list  … tem 2"]
      BulletList[16, 136] isTight
        BulletListItem[16, 32] open:[16, 17, "*"] isTight
          Paragraph[18, 32]
            Text[18, 31] chars:[18, 31, "list  … m 2.1"]
        TaskListItem[34, 136] open:[34, 35, "*"] openSuffix:[36, 39, "[x]"] isTight isDone
          Paragraph[40, 54]
            Text[40, 53] chars:[40, 53, "list  … m 2.2"]
          BulletList[62, 136] isTight
            BulletListItem[62, 80] open:[62, 63, "*"] isTight
              Paragraph[64, 80]
                Text[64, 79] chars:[64, 79, "list  … 2.2.1"]
            BulletListItem[88, 106] open:[88, 89, "*"] isTight
              Paragraph[90, 106]
                Text[90, 105] chars:[90, 105, "list  … 2.2.2"]
            TaskListItem[114, 136] open:[114, 115, "*"] openSuffix:[116, 119, "[X]"] isTight isDone
              Paragraph[120, 136]
                Text[120, 135] chars:[120, 135, "list  … 2.2.3"]
    BulletListItem[136, 259] open:[136, 137, "*"] isTight
      Paragraph[138, 150]
        Text[138, 149] chars:[138, 149, "list  … tem 3"]
      BulletList[152, 259] isTight
        TaskListItem[152, 172] open:[152, 153, "*"] openSuffix:[154, 157, "[ ]"] isTight isNotDone
          Paragraph[158, 172]
            Text[158, 171] chars:[158, 171, "list  … m 3.1"]
        BulletListItem[174, 259] open:[174, 175, "*"] isTight
          Paragraph[176, 190]
            Text[176, 189] chars:[176, 189, "list  … m 3.2"]
          BulletList[194, 259] isTight
            BulletListItem[194, 212] open:[194, 195, "*"] isTight
              Paragraph[196, 212]
                Text[196, 211] chars:[196, 211, "list  … 3.2.1"]
            TaskListItem[216, 238] open:[216, 217, "*"] openSuffix:[218, 221, "[ ]"] isTight isNotDone
              Paragraph[222, 238]
                Text[222, 237] chars:[222, 237, "list  … 3.2.2"]
            BulletListItem[242, 259] open:[242, 243, "*"] isTight
              Paragraph[244, 259]
                Text[244, 259] chars:[244, 259, "list  … 3.2.3"]
````````````````````````````````


