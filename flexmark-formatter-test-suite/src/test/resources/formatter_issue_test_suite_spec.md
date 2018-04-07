---
title: DocxRenderer
author:
version:
date: '2017-12-07'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Formatter

```````````````````````````````` example(Formatter: 1) options(item-indent-1)
Text

1. numbered list one
 - unnumbered list
 unnumbered list cont. same line
 - unnumbered list  
 unnumbered list cont. next line

 numbered list one cont. after unnumbered list
.
Text

1. numbered list one
 - unnumbered list
  unnumbered list cont. same line
 - unnumbered list  
  unnumbered list cont. next line

 numbered list one cont. after unnumbered list

.
Document[0, 180]
  Paragraph[0, 5] isTrailingBlankLine
    Text[0, 4] chars:[0, 4, "Text"]
  BlankLine[5, 6]
  OrderedList[6, 180] isTight delimiter:'.'
    OrderedListItem[6, 180] open:[6, 8, "1."] isTight hadBlankLine
      Paragraph[9, 27]
        Text[9, 26] chars:[9, 26, "numbe … t one"]
      BulletList[28, 133] isTight
        BulletListItem[28, 79] open:[28, 29, "-"] isTight
          Paragraph[30, 79]
            Text[30, 45] chars:[30, 45, "unnum …  list"]
            SoftLineBreak[45, 46]
            Text[47, 78] chars:[47, 78, "unnum …  line"]
        BulletListItem[80, 133] open:[80, 81, "-"] isTight hadBlankLineAfter
          Paragraph[82, 133] isTrailingBlankLine
            Text[82, 97] chars:[82, 97, "unnum …  list"]
            HardLineBreak[97, 100]
            Text[101, 132] chars:[101, 132, "unnum …  line"]
      Paragraph[135, 180]
        Text[135, 180] chars:[135, 180, "numbe …  list"]
````````````````````````````````


