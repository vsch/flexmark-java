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

```````````````````````````````` example Task List Items: 1
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
Document[0, 627]
  BulletList[0, 627] isTight
    BulletListItem[0, 132] open:[0, 1, "*"] isTight
      Paragraph[2, 14]
        Text[2, 13] chars:[2, 13, "list  … tem 1"]
      BulletList[16, 132] isTight
        TaskListItem[16, 36] open:[16, 17, "*"] openSuffix:[18, 21, "[ ]"] isTight isNotDone
          Paragraph[22, 36]
            Text[22, 35] chars:[22, 35, "list  … m 1.1"]
        TaskListItem[38, 132] open:[38, 39, "*"] openSuffix:[40, 43, "[x]"] isTight isDone
          Paragraph[44, 58]
            Text[44, 57] chars:[44, 57, "list  … m 1.2"]
          BulletList[62, 132] isTight
            BulletListItem[62, 80] open:[62, 63, "*"] isTight
              Paragraph[64, 80]
                Text[64, 79] chars:[64, 79, "list  … 1.2.1"]
            TaskListItem[84, 106] open:[84, 85, "*"] openSuffix:[86, 89, "[ ]"] isTight isNotDone
              Paragraph[90, 106]
                Text[90, 105] chars:[90, 105, "list  … 1.2.2"]
            TaskListItem[110, 132] open:[110, 111, "*"] openSuffix:[112, 115, "[X]"] isTight isDone
              Paragraph[116, 132]
                Text[116, 131] chars:[116, 131, "list  … 1.2.3"]
    BulletListItem[132, 256] open:[132, 133, "*"] isTight
      Paragraph[134, 146]
        Text[134, 145] chars:[134, 145, "list  … tem 2"]
      BulletList[148, 256] isTight
        BulletListItem[148, 164] open:[148, 149, "*"] isTight
          Paragraph[150, 164]
            Text[150, 163] chars:[150, 163, "list  … m 2.1"]
        TaskListItem[166, 256] open:[166, 167, "*"] openSuffix:[168, 171, "[x]"] isTight isDone
          Paragraph[172, 186]
            Text[172, 185] chars:[172, 185, "list  … m 2.2"]
          BulletList[190, 256] isTight
            BulletListItem[190, 208] open:[190, 191, "*"] isTight
              Paragraph[192, 208]
                Text[192, 207] chars:[192, 207, "list  … 2.2.1"]
            BulletListItem[212, 230] open:[212, 213, "*"] isTight
              Paragraph[214, 230]
                Text[214, 229] chars:[214, 229, "list  … 2.2.2"]
            TaskListItem[234, 256] open:[234, 235, "*"] openSuffix:[236, 239, "[X]"] isTight isDone
              Paragraph[240, 256]
                Text[240, 255] chars:[240, 255, "list  … 2.2.3"]
    BulletListItem[256, 380] open:[256, 257, "*"] isTight
      Paragraph[258, 270]
        Text[258, 269] chars:[258, 269, "list  … tem 3"]
      BulletList[272, 380] isTight
        TaskListItem[272, 292] open:[272, 273, "*"] openSuffix:[274, 277, "[ ]"] isTight isNotDone
          Paragraph[278, 292]
            Text[278, 291] chars:[278, 291, "list  … m 3.1"]
        BulletListItem[294, 380] open:[294, 295, "*"] isTight
          Paragraph[296, 310]
            Text[296, 309] chars:[296, 309, "list  … m 3.2"]
          BulletList[314, 380] isTight
            BulletListItem[314, 332] open:[314, 315, "*"] isTight
              Paragraph[316, 332]
                Text[316, 331] chars:[316, 331, "list  … 3.2.1"]
            TaskListItem[336, 358] open:[336, 337, "*"] openSuffix:[338, 341, "[ ]"] isTight isNotDone
              Paragraph[342, 358]
                Text[342, 357] chars:[342, 357, "list  … 3.2.2"]
            BulletListItem[362, 380] open:[362, 363, "*"] isTight
              Paragraph[364, 380]
                Text[364, 379] chars:[364, 379, "list  … 3.2.3"]
    BulletListItem[380, 496] open:[380, 381, "*"] isTight
      Paragraph[382, 394]
        Text[382, 393] chars:[382, 393, "list  … tem 4"]
      BulletList[396, 496] isTight
        BulletListItem[396, 412] open:[396, 397, "*"] isTight
          Paragraph[398, 412]
            Text[398, 411] chars:[398, 411, "list  … m 4.1"]
        BulletListItem[414, 496] open:[414, 415, "*"] isTight
          Paragraph[416, 430]
            Text[416, 429] chars:[416, 429, "list  … m 4.2"]
          BulletList[434, 496] isTight
            BulletListItem[434, 452] open:[434, 435, "*"] isTight
              Paragraph[436, 452]
                Text[436, 451] chars:[436, 451, "list  … 4.2.1"]
            BulletListItem[456, 474] open:[456, 457, "*"] isTight
              Paragraph[458, 474]
                Text[458, 473] chars:[458, 473, "list  … 4.2.2"]
            BulletListItem[478, 496] open:[478, 479, "*"] isTight
              Paragraph[480, 496]
                Text[480, 495] chars:[480, 495, "list  … 4.2.3"]
    BulletListItem[496, 627] open:[496, 497, "*"] isTight
      Paragraph[498, 510]
        Text[498, 509] chars:[498, 509, "list  … tem 5"]
      BulletList[512, 627] isTight
        TaskListItem[512, 532] open:[512, 513, "*"] openSuffix:[514, 517, "[ ]"] isTight isNotDone
          Paragraph[518, 532]
            Text[518, 531] chars:[518, 531, "list  … m 5.1"]
        TaskListItem[534, 627] open:[534, 535, "*"] openSuffix:[536, 539, "[x]"] isTight isDone
          Paragraph[540, 554]
            Text[540, 553] chars:[540, 553, "list  … m 5.2"]
          BulletList[558, 627] isTight
            BulletListItem[558, 576] open:[558, 559, "*"] isTight
              Paragraph[560, 576]
                Text[560, 575] chars:[560, 575, "list  … 5.2.1"]
            TaskListItem[580, 602] open:[580, 581, "*"] openSuffix:[582, 585, "[ ]"] isTight isNotDone
              Paragraph[586, 602]
                Text[586, 601] chars:[586, 601, "list  … 5.2.2"]
            TaskListItem[606, 627] open:[606, 607, "*"] openSuffix:[608, 611, "[X]"] isTight isDone
              Paragraph[612, 627]
                Text[612, 627] chars:[612, 627, "list  … 5.2.3"]
````````````````````````````````


```````````````````````````````` example(Task List Items: 2) options(format-fixed-indent)
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


```````````````````````````````` example(Task List Items: 3) options(task-case-lowercase)
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


```````````````````````````````` example(Task List Items: 4) options(task-case-uppercase)
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

```````````````````````````````` example(Task List Items: 5) options(task-placement-incomplete-first)
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

```````````````````````````````` example(Task List Items: 6) options(task-placement-complete-to-non-task)
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

```````````````````````````````` example(Task List Items: 7) options(task-placement-incomplete-nested-first)
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

```````````````````````````````` example(Task List Items: 8) options(task-placement-complete-nested-to-non-task)
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

```````````````````````````````` example(Task List Items: 9) options(task-placement-incomplete-nested-first)
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
Document[0, 247]
  BulletList[0, 247] isTight
    BulletListItem[0, 124] open:[0, 1, "*"] isTight
      Paragraph[2, 14]
        Text[2, 13] chars:[2, 13, "list  … tem 2"]
      BulletList[16, 124] isTight
        BulletListItem[16, 32] open:[16, 17, "*"] isTight
          Paragraph[18, 32]
            Text[18, 31] chars:[18, 31, "list  … m 2.1"]
        TaskListItem[34, 124] open:[34, 35, "*"] openSuffix:[36, 39, "[x]"] isTight isDone
          Paragraph[40, 54]
            Text[40, 53] chars:[40, 53, "list  … m 2.2"]
          BulletList[58, 124] isTight
            BulletListItem[58, 76] open:[58, 59, "*"] isTight
              Paragraph[60, 76]
                Text[60, 75] chars:[60, 75, "list  … 2.2.1"]
            BulletListItem[80, 98] open:[80, 81, "*"] isTight
              Paragraph[82, 98]
                Text[82, 97] chars:[82, 97, "list  … 2.2.2"]
            TaskListItem[102, 124] open:[102, 103, "*"] openSuffix:[104, 107, "[X]"] isTight isDone
              Paragraph[108, 124]
                Text[108, 123] chars:[108, 123, "list  … 2.2.3"]
    BulletListItem[124, 247] open:[124, 125, "*"] isTight
      Paragraph[126, 138]
        Text[126, 137] chars:[126, 137, "list  … tem 3"]
      BulletList[140, 247] isTight
        TaskListItem[140, 160] open:[140, 141, "*"] openSuffix:[142, 145, "[ ]"] isTight isNotDone
          Paragraph[146, 160]
            Text[146, 159] chars:[146, 159, "list  … m 3.1"]
        BulletListItem[162, 247] open:[162, 163, "*"] isTight
          Paragraph[164, 178]
            Text[164, 177] chars:[164, 177, "list  … m 3.2"]
          BulletList[182, 247] isTight
            BulletListItem[182, 200] open:[182, 183, "*"] isTight
              Paragraph[184, 200]
                Text[184, 199] chars:[184, 199, "list  … 3.2.1"]
            TaskListItem[204, 226] open:[204, 205, "*"] openSuffix:[206, 209, "[ ]"] isTight isNotDone
              Paragraph[210, 226]
                Text[210, 225] chars:[210, 225, "list  … 3.2.2"]
            BulletListItem[230, 247] open:[230, 231, "*"] isTight
              Paragraph[232, 247]
                Text[232, 247] chars:[232, 247, "list  … 3.2.3"]
````````````````````````````````


## No Suffix Content

```````````````````````````````` example(No Suffix Content: 1) options(no-suffix-content)
* [ ] task
        * item 2
.
* [ ] task
      * item 2

.
Document[0, 27]
  BulletList[0, 27] isTight
    TaskListItem[0, 27] open:[0, 1, "*"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
      BulletList[19, 27] isTight
        BulletListItem[19, 27] open:[19, 20, "*"] isTight
          Paragraph[21, 27]
            Text[21, 27] chars:[21, 27, "item 2"]
````````````````````````````````


```````````````````````````````` example No Suffix Content: 2
* [ ] task
    * item 2
.
* [ ] task
  * item 2

.
Document[0, 23]
  BulletList[0, 23] isTight
    TaskListItem[0, 23] open:[0, 1, "*"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
      BulletList[15, 23] isTight
        BulletListItem[15, 23] open:[15, 16, "*"] isTight
          Paragraph[17, 23]
            Text[17, 23] chars:[17, 23, "item 2"]
````````````````````````````````


```````````````````````````````` example(No Suffix Content: 3) options(list-spacing-loosen)
* [ ] task
    * item 2
    
* [ ] task
* [ ] task
.
* [ ] task

  * item 2

* [ ] task

* [ ] task

.
Document[0, 50]
  BulletList[0, 50] isLoose
    TaskListItem[0, 24] open:[0, 1, "*"] openSuffix:[2, 5, "[ ]"] isLoose isNotDone
      Paragraph[6, 11]
        Text[6, 10] chars:[6, 10, "task"]
      BulletList[15, 24] isTight
        BulletListItem[15, 24] open:[15, 16, "*"] isTight hadBlankLineAfter
          Paragraph[17, 24] isTrailingBlankLine
            Text[17, 23] chars:[17, 23, "item 2"]
    BlankLine[24, 29]
    TaskListItem[29, 40] open:[29, 30, "*"] openSuffix:[31, 34, "[ ]"] isLoose isNotDone
      Paragraph[35, 40]
        Text[35, 39] chars:[35, 39, "task"]
    TaskListItem[40, 50] open:[40, 41, "*"] openSuffix:[42, 45, "[ ]"] isLoose isNotDone
      Paragraph[46, 50]
        Text[46, 50] chars:[46, 50, "task"]
````````````````````````````````


## Empty List Items

```````````````````````````````` example(Empty List Items: 1) options(list-spacing-loosen)
* [ ] list item 1
* [ ] 

* [ ] list item 2
* [ ] 
not a list item
.
* [ ] list item 1

* [ ] 

* [ ] list item 2

* [ ] 

not a list item
.
Document[0, 66]
  BulletList[0, 49] isLoose
    TaskListItem[0, 18] open:[0, 1, "*"] openSuffix:[2, 5, "[ ]"] isLoose isNotDone
      Paragraph[6, 18]
        Text[6, 17] chars:[6, 17, "list  … tem 1"]
    TaskListItem[18, 23] open:[18, 19, "*"] openSuffix:[20, 23, "[ ]"] isLoose hadBlankLineAfter isNotDone
    BlankLine[25, 26]
    TaskListItem[26, 44] open:[26, 27, "*"] openSuffix:[28, 31, "[ ]"] isLoose isNotDone
      Paragraph[32, 44]
        Text[32, 43] chars:[32, 43, "list  … tem 2"]
    TaskListItem[44, 49] open:[44, 45, "*"] openSuffix:[46, 49, "[ ]"] isLoose isNotDone
  Paragraph[51, 66]
    Text[51, 66] chars:[51, 66, "not a …  item"]
````````````````````````````````


```````````````````````````````` example Empty List Items: 2
* [ ] list item 1
* [ ] 
* [ ] list item 2
* [ ] 
not a list item
.
* [ ] list item 1
* [ ] 
* [ ] list item 2
* [ ] 

not a list item
.
Document[0, 65]
  BulletList[0, 48] isTight
    TaskListItem[0, 18] open:[0, 1, "*"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 18]
        Text[6, 17] chars:[6, 17, "list  … tem 1"]
    TaskListItem[18, 23] open:[18, 19, "*"] openSuffix:[20, 23, "[ ]"] isTight isNotDone
    TaskListItem[25, 43] open:[25, 26, "*"] openSuffix:[27, 30, "[ ]"] isTight isNotDone
      Paragraph[31, 43]
        Text[31, 42] chars:[31, 42, "list  … tem 2"]
    TaskListItem[43, 48] open:[43, 44, "*"] openSuffix:[45, 48, "[ ]"] isTight isNotDone
  Paragraph[50, 65]
    Text[50, 65] chars:[50, 65, "not a …  item"]
````````````````````````````````


With removal of empty list items

```````````````````````````````` example(Empty List Items: 3) options(remove-empty-items)
* [ ] list item 1
* [ ] 

* [ ] list item 2
* [ ] 
not a list item
.
* [ ] list item 1

* [ ] list item 2

not a list item
.
Document[0, 66]
  BulletList[0, 49] isLoose
    TaskListItem[0, 18] open:[0, 1, "*"] openSuffix:[2, 5, "[ ]"] isLoose isNotDone
      Paragraph[6, 18]
        Text[6, 17] chars:[6, 17, "list  … tem 1"]
    TaskListItem[18, 23] open:[18, 19, "*"] openSuffix:[20, 23, "[ ]"] isLoose hadBlankLineAfter isNotDone
    BlankLine[25, 26]
    TaskListItem[26, 44] open:[26, 27, "*"] openSuffix:[28, 31, "[ ]"] isLoose isNotDone
      Paragraph[32, 44]
        Text[32, 43] chars:[32, 43, "list  … tem 2"]
    TaskListItem[44, 49] open:[44, 45, "*"] openSuffix:[46, 49, "[ ]"] isLoose isNotDone
  Paragraph[51, 66]
    Text[51, 66] chars:[51, 66, "not a …  item"]
````````````````````````````````


```````````````````````````````` example(Empty List Items: 4) options(remove-empty-items)
* [ ] list item 1
* [ ] 
* [ ] list item 2
* [ ] 
not a list item
.
* [ ] list item 1
* [ ] list item 2

not a list item
.
Document[0, 65]
  BulletList[0, 48] isTight
    TaskListItem[0, 18] open:[0, 1, "*"] openSuffix:[2, 5, "[ ]"] isTight isNotDone
      Paragraph[6, 18]
        Text[6, 17] chars:[6, 17, "list  … tem 1"]
    TaskListItem[18, 23] open:[18, 19, "*"] openSuffix:[20, 23, "[ ]"] isTight isNotDone
    TaskListItem[25, 43] open:[25, 26, "*"] openSuffix:[27, 30, "[ ]"] isTight isNotDone
      Paragraph[31, 43]
        Text[31, 42] chars:[31, 42, "list  … tem 2"]
    TaskListItem[43, 48] open:[43, 44, "*"] openSuffix:[45, 48, "[ ]"] isTight isNotDone
  Paragraph[50, 65]
    Text[50, 65] chars:[50, 65, "not a …  item"]
````````````````````````````````


## To Non-Task

```````````````````````````````` example(To Non-Task: 1) options(prioritized-tasks, list-bullet-asterisk, task-placement-complete-nested-to-non-task, margin[72])
⦙- [x] Was complete low Fix: nasty bug introducing typing delay with preview enabled.
.
⦙* Was complete low Fix: nasty bug introducing typing delay with preview
  enabled.

````````````````````````````````


```````````````````````````````` example(To Non-Task: 2) options(prioritized-tasks, list-bullet-asterisk, task-placement-complete-to-non-task, margin[72])
⦙- [x] Was complete low Fix: nasty bug introducing typing delay with preview enabled.
.
⦙* Was complete low Fix: nasty bug introducing typing delay with preview
  enabled.

````````````````````````````````


## Prioritized

```````````````````````````````` example(Prioritized: 1) options(prioritized-tasks, task-placement-incomplete-nested-first, list-bullet-asterisk)
* Add: 
+ [ ] Add: 
+ [ ] Fix: 
  + [ ] do not 

.
+ [ ] Add:
+ [ ] Fix:
  + [ ] do not
* Add:

````````````````````````````````


do add blank line if blank line is after sub-list in middle of list

```````````````````````````````` example(Prioritized: 2) options(prioritized-tasks, task-placement-incomplete-nested-first, list-bullet-asterisk)
* Add: 
+ [ ] Add: 
+ [ ] Fix: 
  + [ ] do not 

+ [ ] Fix2: 
  + [ ] do not2 
    
.
+ [ ] Add:
+ [ ] Fix:
  + [ ] do not

+ [ ] Fix2:
  + [ ] do not2
* Add:

````````````````````````````````


ordered item priority high

```````````````````````````````` example(Prioritized: 3) options(prioritized-tasks, ordered-task-item-priority-high, task-placement-incomplete-nested-first, list-bullet-asterisk)
* item 
  1. [ ] Add: 
* Add: 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* item
  1. [ ] Add:
* [ ] Fix:
  + [ ] do not
* [ ] Add:
* Add:

````````````````````````````````


```````````````````````````````` example(Prioritized: 4) options(prioritized-tasks, ordered-task-item-priority-normal, task-placement-incomplete-nested-first, list-bullet-asterisk)
* item
  1. [ ] Add: 
* Add: 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* [ ] Fix:
  + [ ] do not
* item
  1. [ ] Add:
* [ ] Add:
* Add:

````````````````````````````````


```````````````````````````````` example(Prioritized: 5) options(prioritized-tasks, ordered-task-item-priority-low, task-placement-incomplete-nested-first, list-bullet-asterisk)
* item
  1. [ ] Add: 
* Add: 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* [ ] Fix:
  + [ ] do not
* [ ] Add:
* item
  1. [ ] Add:
* Add:

````````````````````````````````


do not add blank line if blank line is after end of list

```````````````````````````````` example(Prioritized: 6) options(prioritized-tasks, ordered-task-item-priority-low, task-placement-incomplete-nested-first, list-bullet-asterisk)
* Add: 
+ [ ] Add: 
+ [ ] Fix: 
  + [ ] do not 

.
+ [ ] Add:
+ [ ] Fix:
  + [ ] do not
* Add:

````````````````````````````````


do add blank line if blank line is after sub-list in middle of list

```````````````````````````````` example(Prioritized: 7) options(prioritized-tasks, ordered-task-item-priority-low, task-placement-incomplete-nested-first, list-bullet-asterisk)
* Add: 
+ [ ] Add: 
+ [ ] Fix: 
  + [ ] do not 

+ [ ] Fix2: 
  + [ ] do not2 
    
.
+ [ ] Add:
+ [ ] Fix:
  + [ ] do not

+ [ ] Fix2:
  + [ ] do not2
* Add:

````````````````````````````````


```````````````````````````````` example(Prioritized: 8) options(prioritized-tasks, ordered-task-item-priority-low, task-placement-incomplete-nested-first, list-bullet-asterisk, list-content-after-suffix)
* Add: 
+ [ ] Add: 
+ [ ] Fix: 
  + [ ] do not 

+ [ ] Fix2: 
  + [ ] do not2 
    
.
+ [ ] Add:
+ [ ] Fix:
  + [ ] do not

+ [ ] Fix2:
  + [ ] do not2
* Add:

````````````````````````````````


```````````````````````````````` example(Prioritized: 9) options(margin[72])
* [ ] Fix: [StackTraces_ToDo.txt](https://github.com/vsch/idea-multimarkdown2/blob/master/StackTraces_ToDo.txt)
* [ ] Fix: Move preview settings which are Monitor specific (Grey scale font smoothing) to
      Application settings. Review others that may need moving to IDE shared settings from
      project settings.
    
.
* [ ] Fix:
  [StackTraces_ToDo.txt](https://github.com/vsch/idea-multimarkdown2/blob/master/StackTraces_ToDo.txt)
* [ ] Fix: Move preview settings which are Monitor specific (Grey scale
  font smoothing) to Application settings. Review others that may need
  moving to IDE shared settings from project settings.

````````````````````````````````


```````````````````````````````` example(Prioritized: 10) options(margin[72], list-content-after-suffix)
* [ ] Fix: [StackTraces_ToDo.txt](https://github.com/vsch/idea-multimarkdown2/blob/master/StackTraces_ToDo.txt)
* [ ] Fix: Move preview settings which are Monitor specific (Grey scale font smoothing) to
      Application settings. Review others that may need moving to IDE shared settings from
      project settings.
    
.
* [ ] Fix:
      [StackTraces_ToDo.txt](https://github.com/vsch/idea-multimarkdown2/blob/master/StackTraces_ToDo.txt)
* [ ] Fix: Move preview settings which are Monitor specific (Grey scale
      font smoothing) to Application settings. Review others that may
      need moving to IDE shared settings from project settings.

````````````````````````````````


### Offset Tracking

```````````````````````````````` example(Prioritized - Offset Tracking: 1) options(prioritized-tasks, ordered-task-item-priority-high, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
⦙*⦙ ⦙Item 3⦙ 
* item 1
+ [ ] Item 4 
.
+ [ ] Item 4
⦙*⦙ ⦙Item 3⦙
* item 1

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 2) options(prioritized-tasks, ordered-task-item-priority-normal, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
⦙*⦙ ⦙Item 3⦙ 
- item 1
+ [ ] Item 4 
.
+ [ ] Item 4
⦙*⦙ ⦙Item 3⦙
* item 1

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 3) options(prioritized-tasks, ordered-task-item-priority-low, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
⦙*⦙ ⦙Item 3⦙ 
+ item 1
+ [ ] Item 4 
.
+ [ ] Item 4
⦙*⦙ ⦙Item 3⦙
* item 1

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 4) options(prioritized-tasks, ordered-task-item-priority-high, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
* item 
  ⦙1. [ ] Add: 
* Add: 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* item
  ⦙1. [ ] Add:
* [ ] Fix:
  + [ ] do not
* [ ] Add:
* Add:

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 5) options(prioritized-tasks, ordered-task-item-priority-high, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
* item 
  1⦙. [ ] Add: 
* Add: 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* item
  1⦙. [ ] Add:
* [ ] Fix:
  + [ ] do not
* [ ] Add:
* Add:

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 6) options(prioritized-tasks, ordered-task-item-priority-high, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
* item 
  1.⦙ [ ] Add: 
* Add: 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* item
  1.⦙ [ ] Add:
* [ ] Fix:
  + [ ] do not
* [ ] Add:
* Add:

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 7) options(prioritized-tasks, ordered-task-item-priority-high, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
* item 
  1. ⦙[ ] Add: 
* Add: 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* item
  1. ⦙[ ] Add:
* [ ] Fix:
  + [ ] do not
* [ ] Add:
* Add:

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 8) options(prioritized-tasks, ordered-task-item-priority-high, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
* item 
  1. [⦙ ] Add: 
* Add: 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* item
  1. [⦙ ] Add:
* [ ] Fix:
  + [ ] do not
* [ ] Add:
* Add:

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 9) options(prioritized-tasks, ordered-task-item-priority-high, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
* item 
  1. [ ⦙] Add: 
* Add: 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* item
  1. [ ⦙] Add:
* [ ] Fix:
  + [ ] do not
* [ ] Add:
* Add:

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 10) options(prioritized-tasks, ordered-task-item-priority-high, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
* item 
  1. [ ]⦙ Add: 
* Add: 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* item
  1. [ ]⦙ Add:
* [ ] Fix:
  + [ ] do not
* [ ] Add:
* Add:

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 11) options(prioritized-tasks, ordered-task-item-priority-high, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
* item 
  1. [ ] ⦙Add: 
* Add: 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* item
  1. [ ] ⦙Add:
* [ ] Fix:
  + [ ] do not
* [ ] Add:
* Add:

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 12) options(prioritized-tasks, ordered-task-item-priority-high, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
* item 
  1. [ ] Add⦙: 
* Add: 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* item
  1. [ ] Add⦙:
* [ ] Fix:
  + [ ] do not
* [ ] Add:
* Add:

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 13) options(prioritized-tasks, ordered-task-item-priority-high, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
* item 
  1. [ ] Add:⦙ 
* Add: 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* item
  1. [ ] Add:⦙
* [ ] Fix:
  + [ ] do not
* [ ] Add:
* Add:

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 14) options(prioritized-tasks, ordered-task-item-priority-high, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[31])
* item 
  1. [ ] Add: this text should wrap and resolve caret⦙ 
* Add: 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* item
  1. [ ] Add: this text should
     wrap and resolve caret⦙
* [ ] Fix:
  + [ ] do not
* [ ] Add:
* Add:

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 15) options(prioritized-tasks, ordered-task-item-priority-high, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[31])
* item 
  1. [ ] Add: this text should wrap and resolve caret and even on the third line.⦙ 
* Add: 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* item
  1. [ ] Add: this text should
     wrap and resolve caret and
     even on the third line.⦙
* [ ] Fix:
  + [ ] do not
* [ ] Add:
* Add:

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 16) options(prioritized-tasks, ordered-task-item-priority-high, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
* item 
  ⦙1⦙.⦙ ⦙[⦙ ⦙]⦙ ⦙Add:⦙ 
⦙*⦙ ⦙Add:⦙ 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* item
  ⦙1⦙.⦙ ⦙[⦙ ⦙]⦙ ⦙Add:⦙
* [ ] Fix:
  + [ ] do not
* [ ] Add:
⦙*⦙ ⦙Add:⦙

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 17) options(prioritized-tasks, ordered-task-item-priority-high, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
* item 
  1. [ ] Add: 
* ⦙Add: 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* item
  1. [ ] Add:
* [ ] Fix:
  + [ ] do not
* [ ] Add:
* ⦙Add:

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 18) options(prioritized-tasks, ordered-task-item-priority-normal, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
* item
  ⦙1⦙.⦙ ⦙[⦙ ⦙]⦙ ⦙Add:⦙ 
⦙*⦙ ⦙Add:⦙ 
* [ ] Add: 
* [ ] Fix: 
  + [ ] do not 
.
* [ ] Fix:
  + [ ] do not
* item
  ⦙1⦙.⦙ ⦙[⦙ ⦙]⦙ ⦙Add:⦙
* [ ] Add:
⦙*⦙ ⦙Add:⦙

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 19) options(prioritized-tasks, ordered-task-item-priority-low, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
* item
  1. [ ] ⦙Add1: 
* Add2: 
* [ ] Add3: 
* [ ] Fix: 
  + [ ] do not 
.
* [ ] Fix:
  + [ ] do not
* [ ] Add3:
* item
  1. [ ] ⦙Add1:
* Add2:

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 20) options(prioritized-tasks, ordered-task-item-priority-low, list-bullet-asterisk, task-placement-incomplete-nested-first, margin[96])
* item
  ⦙1⦙.⦙ ⦙[⦙ ⦙]⦙ ⦙Add1:⦙ 
⦙*⦙ ⦙Add2:⦙ 
* [ ] Add3: 
* [ ] Fix: 
  + [ ] do not 
.
* [ ] Fix:
  + [ ] do not
* [ ] Add3:
* item
  ⦙1⦙.⦙ ⦙[⦙ ⦙]⦙ ⦙Add1:⦙
⦙*⦙ ⦙Add2:⦙

````````````````````````````````


```````````````````````````````` example(Prioritized - Offset Tracking: 21) options(prioritized-tasks, list-bullet-asterisk, task-placement-incomplete-nested-first, no-list-auto-loose, format-content-after-prefix, margin[96], list-spacing-tighten)
+ [ ] Fix: alternative GitHub raw link format shows as undefined:
* [ ] Fix: list item context for easy list modifications which always result in correct list:
* [ ] Fix: Add Markdown file templates as per
      [fileTemplates.j2ee](jetbrains://idea/navigate/reference?project=intellij-community.master&fqn=fileTemplates.j2ee)
      and
      [DevKitFileTemplatesFactory](jetbrains://idea/navigate/reference?project=intellij-community.master&fqn=org.jetbrains.idea.devkit.DevKitFileTemplatesFactory)

  ```xml
  <fileTemplateGroup implementation="org.jetbrains.idea.devkit.DevKitFileTemplatesFactory"/>
  ```
.
+ [ ] Fix: alternative GitHub raw link format shows as undefined:
* [ ] Fix: list item context for easy list modifications which always result in correct list:
* [ ] Fix: Add Markdown file templates as per
      [fileTemplates.j2ee](jetbrains://idea/navigate/reference?project=intellij-community.master&fqn=fileTemplates.j2ee)
      and
      [DevKitFileTemplatesFactory](jetbrains://idea/navigate/reference?project=intellij-community.master&fqn=org.jetbrains.idea.devkit.DevKitFileTemplatesFactory)

  ```xml
  <fileTemplateGroup implementation="org.jetbrains.idea.devkit.DevKitFileTemplatesFactory"/>
  ```

````````````````````````````````

