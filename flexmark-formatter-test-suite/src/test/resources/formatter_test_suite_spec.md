---
title: Formatter Test Suite
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

# Formatter Test Suite

## Formatter

default

```````````````````````````````` example Formatter: 1
#Heading
-----
paragraph text 
lazy continuation
* list item
> block quote
lazy continuation

~~~info
        with uneven indent
           with uneven indent
     indented code
~~~ 

        with uneven indent
           with uneven indent
     indented code
1. numbered item 1   
1. numbered item 2   
.
# Heading

-----

paragraph text
lazy continuation
* list item

> block quote
> lazy continuation

~~~info
   with uneven indent
      with uneven indent
indented code
~~~

       with uneven indent
          with uneven indent
    indented code

1. numbered item 1
2. numbered item 2

.
Document[0, 303]
  Heading[0, 8] textOpen:[0, 1, "#"] text:[1, 8, "Heading"]
    AnchorLink[1, 8]
      Text[1, 8] chars:[1, 8, "Heading"]
  ThematicBreak[9, 14]
  Paragraph[15, 49]
    Text[15, 29] chars:[15, 29, "parag …  text"]
    SoftLineBreak[30, 31]
    Text[31, 48] chars:[31, 48, "lazy  … ation"]
  BulletList[49, 61] isTight
    BulletListItem[49, 61] open:[49, 50, "*"] isTight
      Paragraph[51, 61]
        Text[51, 60] chars:[51, 60, "list item"]
  BlockQuote[61, 93] marker:[61, 62, ">"]
    Paragraph[63, 93]
      Text[63, 74] chars:[63, 74, "block … quote"]
      SoftLineBreak[74, 75]
      Text[75, 92] chars:[75, 92, "lazy  … ation"]
  BlankLine[93, 94]
  FencedCodeBlock[94, 181] open:[94, 97, "~~~"] info:[97, 101, "info"] content:[102, 178] lines[3] close:[178, 181, "~~~"]
    Text[102, 178] chars:[102, 178, "      … code\n"]
  BlankLine[183, 184]
  IndentedCodeBlock[188, 260]
  OrderedList[260, 303] isTight delimiter:'.'
    OrderedListItem[260, 282] open:[260, 262, "1."] isTight
      Paragraph[263, 282]
        Text[263, 278] chars:[263, 278, "numbe … tem 1"]
    OrderedListItem[282, 303] open:[282, 284, "1."] isTight
      Paragraph[285, 303]
        Text[285, 300] chars:[285, 300, "numbe … tem 2"]
````````````````````````````````


```````````````````````````````` example(Formatter: 2) options(atx-space-add, atx-trailing-add, list-no-renumber-items, block-quote-compact, fenced-code-spaced-info, list-bullet-plus, fenced-code-marker-backtick, fenced-code-marker-length, fenced-code-minimize, indented-code-minimize)
#Heading
-----
paragraph text 
lazy continuation
* list item
> block quote
lazy continuation

~~~info
        with uneven indent
           with uneven indent
     indented code
~~~ 

        with uneven indent
           with uneven indent
     indented code
1. numbered item 1   
1. numbered item 2   
.
# Heading #

-----

paragraph text
lazy continuation
+ list item

>block quote
>lazy continuation

`````` info
   with uneven indent
      with uneven indent
indented code
``````

       with uneven indent
          with uneven indent
    indented code

1. numbered item 1
1. numbered item 2

````````````````````````````````


## Lists

```````````````````````````````` example Lists: 1
* list item 1
* list item 1.1
* list item 1.2
  * list item 1.2.1
  * list item 1.2.1.1
  * list item 1.2.1.2
* list item 2
* list item 2.1
* list item 2.2
  * list item 2.2.1
  * list item 2.2.1.1
  * list item 2.2.1.2
.
* list item 1
* list item 1.1
* list item 1.2
  * list item 1.2.1
  * list item 1.2.1.1
  * list item 1.2.1.2
* list item 2
* list item 2.1
* list item 2.2
  * list item 2.2.1
  * list item 2.2.1.1
  * list item 2.2.1.2

````````````````````````````````


## Block Quotes

```````````````````````````````` example(Block Quotes: 1) options(block-quote-compact-with-space)
> > block quote
> lazy continuation
.
>> block quote
>> lazy continuation

````````````````````````````````


```````````````````````````````` example(Block Quotes: 2) options(block-quote-compact-with-space)
> #Heading
> -----
> paragraph text 
> lazy continuation
> * list item
> > block quote
> lazy continuation
> 
> ~~~info
>         with uneven indent
>            with uneven indent
>      indented code
> ~~~ 
> 
>         with uneven indent
>            with uneven indent
>      indented code
> 1. numbered item 1   
> 2. numbered item 2   
.
> # Heading
>
> -----
>
> paragraph text
> lazy continuation
> * list item
>
>> block quote
>> lazy continuation
>
> ~~~info
>    with uneven indent
>       with uneven indent
> indented code
> ~~~
>
>        with uneven indent
>           with uneven indent
>     indented code
>
> 1. numbered item 1
> 2. numbered item 2

````````````````````````````````


## Task List Items

default

```````````````````````````````` example Task List Items: 1
* list item 1
  * [ ] list item 1.1
  * [x] list item 1.2
       * list item 1.2.1
       * [ ] list item 1.2.1.1
       * [X] list item 1.2.1.2
.
* list item 1
  * [ ] list item 1.1
  * [x] list item 1.2
    * list item 1.2.1
    * [ ] list item 1.2.1.1
    * [X] list item 1.2.1.2

````````````````````````````````


```````````````````````````````` example(Task List Items: 2) options(task-case-lowercase)
* list item 1
  * [ ] list item 1.1
  * [x] list item 1.2
       * list item 1.2.1
       * [ ] list item 1.2.1.1
       * [X] list item 1.2.1.2
.
* list item 1
  * [ ] list item 1.1
  * [x] list item 1.2
    * list item 1.2.1
    * [ ] list item 1.2.1.1
    * [x] list item 1.2.1.2

````````````````````````````````


```````````````````````````````` example(Task List Items: 3) options(task-case-uppercase)
* list item 1
  * [ ] list item 1.1
  * [x] list item 1.2
       * list item 1.2.1
       * [ ] list item 1.2.1.1
       * [X] list item 1.2.1.2
.
* list item 1
  * [ ] list item 1.1
  * [X] list item 1.2
    * list item 1.2.1
    * [ ] list item 1.2.1.1
    * [X] list item 1.2.1.2

````````````````````````````````


## Table of Contents

```````````````````````````````` example Table of Contents: 1
## Header 2
### Header 3

[TOC levels=1-3]:# "title\"s"

### title"s
- [Header 2](#header-2)
    - [Header 3](#header-3)
.
## Header 2

### Header 3

[TOC levels=1-3]: # "title\"s"

# title"s
- [Header 2](#header-2)
  - [Header 3](#header-3)
````````````````````````````````


## Attribute Formatting

```````````````````````````````` example(Attribute Formatting: 1) options(margin[72])
# Sample Attributes

Heading Formatting {#provided-id}
====

Setext heading marker equalization
-----
{#provided-id2}

### Heading with trailing{style='color:red'} markers #


Term 1
: Definition can contain multiple lines with{style="font-weight:800"}
  lazy continuation and wrapping to margins where necessary.

~ Definition can contain multiple lines with
  lazy continuation and wrapping to margins where necessary.

.
# Sample Attributes

Heading Formatting {#provided-id}
=================================

Setext heading marker equalization
----------------------------------

{#provided-id2}

### Heading with trailing{style='color:red'} markers #


Term 1
:   Definition can contain multiple lines with{style="font-weight:800"}
    lazy continuation and wrapping to margins where necessary.

~   Definition can contain multiple lines with lazy continuation and
    wrapping to margins where necessary.

````````````````````````````````


## Jekyll Include

```````````````````````````````` example Jekyll Include: 1
{% include test.md %}
    
.
{% include test.md %}

````````````````````````````````


