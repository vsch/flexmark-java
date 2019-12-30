---
title: Aside Formatter Spec
author: Vladimir Schneider
version:
date: '2019-12-30'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Aside

Converts text prefixed with `|` to aside tags, same as block quotes, some examples are copied
from the commonmark spec for block quotes with the marker changed to `|`

```````````````````````````````` example(Aside: 1) options(block-quote-compact-with-space, fenced-code-minimize)
| 1. numbered item 1

.
| 1. numbered item 1

.
Document[0, 22]
  AsideBlock[0, 21] marker:[0, 1, "|"]
    OrderedList[2, 21] isTight delimiter:'.'
      OrderedListItem[2, 21] open:[2, 4, "1."] isTight
        Paragraph[5, 21]
          Text[5, 20] chars:[5, 20, "numbe … tem 1"]
  BlankLine[21, 22]
````````````````````````````````


```````````````````````````````` example(Aside: 2) options(block-quote-compact-with-space, fenced-code-minimize)
| 1. numbered item 1
.
| 1. numbered item 1

.
Document[0, 20]
  AsideBlock[0, 20] marker:[0, 1, "|"]
    OrderedList[2, 20] isTight delimiter:'.'
      OrderedListItem[2, 20] open:[2, 4, "1."] isTight
        Paragraph[5, 20]
          Text[5, 20] chars:[5, 20, "numbe … tem 1"]
````````````````````````````````


```````````````````````````````` example(Aside: 3) options(block-quote-compact-with-space, fenced-code-minimize)
| 1. numbered item 1
|
.
| 1. numbered item 1
|
.
Document[0, 22]
  AsideBlock[0, 22] marker:[0, 1, "|"]
    OrderedList[2, 21] isTight delimiter:'.'
      OrderedListItem[2, 21] open:[2, 4, "1."] isTight hadBlankLineAfter
        Paragraph[5, 21] isTrailingBlankLine
          Text[5, 20] chars:[5, 20, "numbe … tem 1"]
    BlankLine[21, 22]
````````````````````````````````


```````````````````````````````` example(Aside: 4) options(block-quote-compact-with-space, fenced-code-minimize)
| | aside block
| lazy continuation
| 
| ~~~info
|      indented code
|    code
| ~~~ 
| 
|      indented code
| 1. numbered item 1
.
|| aside block
|| lazy continuation
|
| ~~~info
|   indented code
| code
| ~~~
|
|     indented code
|
| 1. numbered item 1

.
Document[0, 131]
  AsideBlock[0, 131] marker:[0, 1, "|"]
    AsideBlock[2, 36] marker:[2, 3, "|"]
      Paragraph[4, 36]
        Text[4, 15] chars:[4, 15, "aside … block"]
        SoftLineBreak[15, 16]
        Text[18, 35] chars:[18, 35, "lazy  … ation"]
    BlankLine[36, 39]
    FencedCodeBlock[41, 85] open:[41, 44, "~~~"] info:[44, 48, "info"] content:[51, 80] lines[2] close:[82, 85, "~~~"]
      Text[51, 80] chars:[51, 80, "      … code\n"]
    BlankLine[87, 90]
    IndentedCodeBlock[96, 111]
    OrderedList[113, 131] isTight delimiter:'.'
      OrderedListItem[113, 131] open:[113, 115, "1."] isTight
        Paragraph[116, 131]
          Text[116, 131] chars:[116, 131, "numbe … tem 1"]
````````````````````````````````


```````````````````````````````` example(Aside: 5) options(block-quote-compact-with-space)
| #Heading
| -----
| paragraph text 
| lazy continuation
| * list item
| | aside block
| lazy continuation
| 
| ~~~info
|         with uneven indent
|            with uneven indent
|      indented code
| ~~~ 
| 
|         with uneven indent
|            with uneven indent
|      indented code
| 1. numbered item 1   
| 2. numbered item 2   
.
| # Heading
|
| -----
|
| paragraph text
| lazy continuation
| * list item
|
|| aside block
|| lazy continuation
|
| ~~~info
|    with uneven indent
|       with uneven indent
| indented code
| ~~~
|
|        with uneven indent
|           with uneven indent
|     indented code
|
| 1. numbered item 1
| 2. numbered item 2

````````````````````````````````


```````````````````````````````` example(Aside: 6) options(block-quote-spaced)
| #Heading
| -----
| paragraph text 
| lazy continuation
| * list item
| | aside block
| lazy continuation
| 
| ~~~info
|         with uneven indent
|            with uneven indent
|      indented code
| ~~~ 
| 
|         with uneven indent
|            with uneven indent
|      indented code
| 1. numbered item 1   
| 2. numbered item 2   
.
| # Heading
|
| -----
|
| paragraph text
| lazy continuation
| * list item
|
| | aside block
| | lazy continuation
|
| ~~~info
|    with uneven indent
|       with uneven indent
| indented code
| ~~~
|
|        with uneven indent
|           with uneven indent
|     indented code
|
| 1. numbered item 1
| 2. numbered item 2

````````````````````````````````


```````````````````````````````` example Aside: 7
paragraph text 
lazy continuation
* list item
  | aside block
  lazy continuation
.
paragraph text
lazy continuation
* list item

  | aside block
  | lazy continuation

````````````````````````````````


```````````````````````````````` example(Aside: 8) options(no-block-quote-blank-lines)
paragraph text 
lazy continuation
* list item
  | aside block
  lazy continuation
.
paragraph text
lazy continuation
* list item
  | aside block
  | lazy continuation

````````````````````````````````


as first child of empty item

```````````````````````````````` example Aside: 9
* | aside block
1. | aside block  
.
* | aside block

1. | aside block

````````````````````````````````


### Continuation

#### As First

```````````````````````````````` example(Aside - Continuation - As First: 1) options(block-quote-compact-with-space, block-quote-cont-as-first)
| 1. numbered item 1
|
.
| 1. numbered item 1
|
````````````````````````````````


```````````````````````````````` example(Aside - Continuation - As First: 2) options(block-quote-cont-as-first)
paragraph text 
lazy continuation
* list item
  | aside block
  lazy continuation
.
paragraph text
lazy continuation
* list item

  | aside block
  | lazy continuation

````````````````````````````````


```````````````````````````````` example(Aside - Continuation - As First: 3) options(no-block-quote-blank-lines, block-quote-cont-as-first)
paragraph text 
lazy continuation
* list item
  | aside block
  lazy continuation
.
paragraph text
lazy continuation
* list item
  | aside block
  | lazy continuation

````````````````````````````````


#### Compact

```````````````````````````````` example(Aside - Continuation - Compact: 1) options(block-quote-compact-with-space, block-quote-cont-compact)
| 1. numbered item 1
|
.
| 1. numbered item 1
|
````````````````````````````````


```````````````````````````````` example(Aside - Continuation - Compact: 2) options(block-quote-cont-compact)
paragraph text 
lazy continuation
* list item
  | aside block
  lazy continuation
.
paragraph text
lazy continuation
* list item

  | aside block
  |lazy continuation

````````````````````````````````


```````````````````````````````` example(Aside - Continuation - Compact: 3) options(no-block-quote-blank-lines, block-quote-cont-compact)
paragraph text 
lazy continuation
* list item
  | aside block
  lazy continuation
.
paragraph text
lazy continuation
* list item
  | aside block
  |lazy continuation

````````````````````````````````


#### Compact Space

```````````````````````````````` example(Aside - Continuation - Compact Space: 1) options(block-quote-compact-with-space, block-quote-cont-compact-with-space)
| 1. numbered item 1
|
.
| 1. numbered item 1
|
````````````````````````````````


```````````````````````````````` example(Aside - Continuation - Compact Space: 2) options(block-quote-cont-compact-with-space)
paragraph text 
lazy continuation
* list item
  | aside block
  lazy continuation
.
paragraph text
lazy continuation
* list item

  | aside block
  | lazy continuation

````````````````````````````````


```````````````````````````````` example(Aside - Continuation - Compact Space: 3) options(no-block-quote-blank-lines, block-quote-cont-compact-with-space)
paragraph text 
lazy continuation
* list item
  | aside block
  lazy continuation
.
paragraph text
lazy continuation
* list item
  | aside block
  | lazy continuation

````````````````````````````````


#### Remove

```````````````````````````````` example(Aside - Continuation - Remove: 1) options(block-quote-compact-with-space, block-quote-cont-remove)
| 1. numbered item 1
|
.
| 1. numbered item 1

````````````````````````````````


```````````````````````````````` example(Aside - Continuation - Remove: 2) options(block-quote-cont-remove)
paragraph text 
lazy continuation
* list item
  | aside block
  | lazy continuation
.
paragraph text
lazy continuation
* list item

  | aside block
  lazy continuation

````````````````````````````````


```````````````````````````````` example(Aside - Continuation - Remove: 3) options(no-block-quote-blank-lines, block-quote-cont-remove)
paragraph text 
lazy continuation
* list item
  | aside block
  | lazy continuation
.
paragraph text
lazy continuation
* list item
  | aside block
  lazy continuation

````````````````````````````````


