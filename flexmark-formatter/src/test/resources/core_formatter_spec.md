---
title: Formatter Extension Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Formatter

default

```````````````````````````````` example Formatter: 1
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
Document[0, 304]
  Heading[0, 8] textOpen:[0, 1, "#"] text:[1, 8, "Heading"]
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
  BlankLine[183, 184]
  IndentedCodeBlock[188, 260]
  OrderedList[260, 304] isTight delimiter:'.'
    OrderedListItem[260, 282] open:[260, 262, "1."] isTight
      Paragraph[263, 282]
        Text[263, 278] chars:[263, 278, "numbe … tem 1"]
    OrderedListItem[282, 304] open:[282, 284, "1."] isTight
      Paragraph[285, 304]
        Text[285, 300] chars:[285, 300, "numbe … tem 2"]
````````````````````````````````


```````````````````````````````` example(Formatter: 2) options(atx-space-add, atx-trailing-add, list-no-renumber-items, block-quote-compact, fenced-code-spaced-info, list-bullet-plus, fenced-code-marker-backtick, fenced-code-marker-length, fenced-code-minimize, indented-code-minimize)
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


### Lists

```````````````````````````````` example Lists: 1
* list item 1
  * list item 1.1
  * list item 1.2
    * list item 1.2.1
    * list item 1.2.2
    * list item 1.2.3
* list item 2
  * list item 2.1
  * list item 2.2
    * list item 2.2.1
    * list item 2.2.2
    * list item 2.2.3
.
* list item 1
  * list item 1.1
  * list item 1.2
    * list item 1.2.1
    * list item 1.2.2
    * list item 1.2.3
* list item 2
  * list item 2.1
  * list item 2.2
    * list item 2.2.1
    * list item 2.2.2
    * list item 2.2.3
````````````````````````````````


```````````````````````````````` example Lists: 2
1. list item 1
   1. list item 1.1
   1. list item 1.2
      1. list item 1.2.1
      1. list item 1.2.2
      1. list item 1.2.3
1. list item 2
   1. list item 2.1
   1. list item 2.2
      1. list item 2.2.1
      1. list item 2.2.2
      1. list item 2.2.3
.
1. list item 1
   1. list item 1.1
   2. list item 1.2
      1. list item 1.2.1
      2. list item 1.2.2
      3. list item 1.2.3
2. list item 2
   1. list item 2.1
   2. list item 2.2
      1. list item 2.2.1
      2. list item 2.2.2
      3. list item 2.2.3
````````````````````````````````


```````````````````````````````` example Lists: 3
1. list item 1
   1. list item 1.1
   1. list item 1.2
      1) list item 1.2.1
      1) list item 1.2.2
      1) list item 1.2.3
1) list item 2
   1) list item 2.1
   1) list item 2.2
      1. list item 2.2.1
      1. list item 2.2.2
      1. list item 2.2.3
.
1. list item 1
   1. list item 1.1
   2. list item 1.2
      1) list item 1.2.1
      2) list item 1.2.2
      3) list item 1.2.3
1) list item 2
   1) list item 2.1
   2) list item 2.2
      1. list item 2.2.1
      2. list item 2.2.2
      3. list item 2.2.3
````````````````````````````````


```````````````````````````````` example Lists: 4
1. list item 1
   1. list item 1.1
   1) list item 1.2
.
1. list item 1
   1. list item 1.1
   1) list item 1.2
````````````````````````````````


```````````````````````````````` example(Lists: 5) options(list-numbered-dot)
1. list item 1
   1. list item 1.1
   1) list item 1.2
.
1. list item 1
   1. list item 1.1
   1. list item 1.2
````````````````````````````````


```````````````````````````````` example(Lists: 6) options(list-numbered-paren)
1. list item 1
   1. list item 1.1
   1) list item 1.2
.
1) list item 1
   1) list item 1.1
   1) list item 1.2
````````````````````````````````


### Block Quotes

```````````````````````````````` example(Block Quotes: 1) options(block-quote-compact-with-space, fenced-code-minimize)
> > block quote
> lazy continuation
> 
> ~~~info
>      indented code
>    code
> ~~~ 
> 
>      indented code
> 1. numbered item 1
.
>> block quote
>> lazy continuation
> 
> ~~~info
>   indented code
> code
> ~~~
>     
>      indented code
> 
> 1. numbered item 1

.
Document[0, 131]
  BlockQuote[0, 131] marker:[0, 1, ">"]
    BlockQuote[2, 36] marker:[2, 3, ">"]
      Paragraph[4, 36]
        Text[4, 15] chars:[4, 15, "block … quote"]
        SoftLineBreak[15, 16]
        Text[18, 35] chars:[18, 35, "lazy  … ation"]
    FencedCodeBlock[41, 85] open:[41, 44, "~~~"] info:[44, 48, "info"] content:[51, 80] lines[2] close:[82, 85, "~~~"]
    IndentedCodeBlock[96, 111]
    OrderedList[113, 131] isTight delimiter:'.'
      OrderedListItem[113, 131] open:[113, 115, "1."] isTight
        Paragraph[116, 131]
          Text[116, 131] chars:[116, 131, "numbe … tem 1"]
  BlankLine[36, 39]
````````````````````````````````


```````````````````````````````` example(Block Quotes: 2) options(block-quote-compact-with-space)
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
>         with uneven indent
>            with uneven indent
>      indented code
> ~~~
>     
>         with uneven indent
>            with uneven indent
>      indented code
> 
> 1. numbered item 1
> 2. numbered item 2

````````````````````````````````


```````````````````````````````` example(Block Quotes: 3) options(block-quote-spaced)
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
> > block quote
> > lazy continuation
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
> 
> 1. numbered item 1
> 2. numbered item 2

````````````````````````````````


### Blank Lines

default

```````````````````````````````` example Blank Lines: 1
paragraph



another paragraph
.
paragraph


another paragraph
.
Document[0, 31]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 9] chars:[0, 9, "paragraph"]
  BlankLine[10, 11]
  BlankLine[11, 12]
  BlankLine[12, 13]
  Paragraph[13, 31]
    Text[13, 30] chars:[13, 30, "anoth … graph"]
````````````````````````````````


blank lines

```````````````````````````````` example(Blank Lines: 2) options(max-blank-lines-1)
paragraph




another paragraph
.
paragraph

another paragraph
.
Document[0, 31]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 9] chars:[0, 9, "paragraph"]
  BlankLine[10, 11]
  BlankLine[11, 12]
  BlankLine[12, 13]
  BlankLine[13, 14]
  Paragraph[14, 31]
    Text[14, 31] chars:[14, 31, "anoth … graph"]
````````````````````````````````


```````````````````````````````` example(Blank Lines: 3) options(max-blank-lines-2)
paragraph




another paragraph
.
paragraph


another paragraph
.
Document[0, 31]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 9] chars:[0, 9, "paragraph"]
  BlankLine[10, 11]
  BlankLine[11, 12]
  BlankLine[12, 13]
  BlankLine[13, 14]
  Paragraph[14, 31]
    Text[14, 31] chars:[14, 31, "anoth … graph"]
````````````````````````````````


```````````````````````````````` example(Blank Lines: 4) options(max-blank-lines-3)
paragraph




another paragraph
.
paragraph



another paragraph
.
Document[0, 31]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 9] chars:[0, 9, "paragraph"]
  BlankLine[10, 11]
  BlankLine[11, 12]
  BlankLine[12, 13]
  BlankLine[13, 14]
  Paragraph[14, 31]
    Text[14, 31] chars:[14, 31, "anoth … graph"]
````````````````````````````````


default

```````````````````````````````` example Blank Lines: 5
paragraph


.
paragraph

.
Document[0, 12]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 9] chars:[0, 9, "paragraph"]
  BlankLine[10, 11]
  BlankLine[11, 12]
````````````````````````````````


no trailing blank lines

```````````````````````````````` example(Blank Lines: 6) options(no-tailing-blanks)
paragraph


.
paragraph
.
Document[0, 12]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 9] chars:[0, 9, "paragraph"]
  BlankLine[10, 11]
  BlankLine[11, 12]
````````````````````````````````


### Headings

```````````````````````````````` example(Headings: 1) options(atx-space-remove)
# Heading
.
#Heading

````````````````````````````````


```````````````````````````````` example(Headings: 2) options(atx-space-remove, atx-trailing-equalize)
# Heading ####
.
#Heading#

````````````````````````````````


```````````````````````````````` example(Headings: 3) options(atx-space-as-is, atx-trailing-equalize)
# Heading ####
.
# Heading #

````````````````````````````````


```````````````````````````````` example(Headings: 4) options(atx-space-add, atx-trailing-equalize)
# Heading ####
.
# Heading #

````````````````````````````````


```````````````````````````````` example(Headings: 5) options(atx-space-remove, atx-trailing-as-is)
# Heading ####
.
#Heading####

````````````````````````````````


```````````````````````````````` example(Headings: 6) options(atx-space-add, atx-trailing-equalize)
#Heading####
.
# Heading #

````````````````````````````````


```````````````````````````````` example(Headings: 7) options(atx-space-add, atx-trailing-remove)
#Heading####
.
# Heading

.
Document[0, 12]
  Heading[0, 12] textOpen:[0, 1, "#"] text:[1, 8, "Heading"] textClose:[8, 12, "####"]
    Text[1, 8] chars:[1, 8, "Heading"]
````````````````````````````````


```````````````````````````````` example Headings: 8
Heading
==
.
Heading
=======

````````````````````````````````


```````````````````````````````` example Headings: 9
Heading
---
.
Heading
-------

````````````````````````````````


```````````````````````````````` example(Headings: 10) options(setext-no-equalize)
Heading
==
.
Heading
==

````````````````````````````````


```````````````````````````````` example(Headings: 11) options(setext-no-equalize)
Heading
---
.
Heading
---

````````````````````````````````


### Thematic Break

```````````````````````````````` example Thematic Break: 1
---
.
---

````````````````````````````````


```````````````````````````````` example(Thematic Break: 2) options(thematic-break)
---
.
*** ** * ** ***

````````````````````````````````


### Fenced Code

```````````````````````````````` example Fenced Code: 1
```info
   indented
       text
 closing
``````
.
```info
   indented
       text
 closing
``````

````````````````````````````````


```````````````````````````````` example Fenced Code: 2
~~~info
   indented
       text
 closing
~~~~~~
.
~~~info
   indented
       text
 closing
~~~~~~

````````````````````````````````


```````````````````````````````` example(Fenced Code: 3) options(fenced-code-minimize)
```info
   indented
       text
 closing
``````
.
```info
  indented
      text
closing
``````

````````````````````````````````


```````````````````````````````` example(Fenced Code: 4) options(fenced-code-minimize)
```info
     indented
         text
   closing
``````
.
```info
  indented
      text
closing
``````

````````````````````````````````


```````````````````````````````` example(Fenced Code: 5) options(fenced-code-minimize)
~~~info
   indented
       text
 closing
~~~~~~
.
~~~info
  indented
      text
closing
~~~~~~

````````````````````````````````


```````````````````````````````` example(Fenced Code: 6) options(fenced-code-marker-backtick)
~~~info
   indented
       text
 closing
~~~~~~
.
```info
   indented
       text
 closing
``````

````````````````````````````````


```````````````````````````````` example(Fenced Code: 7) options(fenced-code-marker-tilde)
```info
   indented
       text
 closing
```````
.
~~~info
   indented
       text
 closing
~~~~~~~

````````````````````````````````


```````````````````````````````` example(Fenced Code: 8) options(fenced-code-match-closing)
~~~info
   indented
       text
 closing
~~~~~~
.
~~~info
   indented
       text
 closing
~~~

````````````````````````````````


```````````````````````````````` example(Fenced Code: 9) options(fenced-code-match-closing)
```info
   indented
       text
 closing
```````
.
```info
   indented
       text
 closing
```

````````````````````````````````


```````````````````````````````` example(Fenced Code: 10) options(fenced-code-marker-length)
~~~info
   indented
       text
 closing
~~~~
.
~~~~~~info
   indented
       text
 closing
~~~~~~

````````````````````````````````


```````````````````````````````` example(Fenced Code: 11) options(fenced-code-marker-length)
```info
   indented
       text
 closing
````
.
``````info
   indented
       text
 closing
``````

````````````````````````````````


### Lists

```````````````````````````````` example Lists: 1
paragraph
* item 1
* item 2
  * item 2.1
  * item 2.2
.
paragraph
* item 1
* item 2
  * item 2.1
  * item 2.2
````````````````````````````````


```````````````````````````````` example Lists: 2
paragraph

* item 1
* item 2
  * item 2.1
  * item 2.2
.
paragraph

* item 1
* item 2
  * item 2.1
  * item 2.2
````````````````````````````````


```````````````````````````````` example Lists: 3
paragraph
1. item 1
1. item 2
   1. item 2.1
   1. item 2.2
.
paragraph
1. item 1
2. item 2
   1. item 2.1
   2. item 2.2
````````````````````````````````


```````````````````````````````` example(Lists: 4) options(list-add-blank-line-before)
paragraph
* item 1
* item 2
  * item 2.1
  * item 2.2
.
paragraph

* item 1
* item 2
  * item 2.1
  * item 2.2
````````````````````````````````


```````````````````````````````` example(Lists: 5) options(list-add-blank-line-before)
paragraph
1. item 1
1. item 2
   1. item 2.1
   1. item 2.2
.
paragraph

1. item 1
2. item 2
   1. item 2.1
   2. item 2.2
````````````````````````````````


```````````````````````````````` example(Lists: 6) options(list-bullet-asterisk)
+ item 1
+ item 2
  + item 2.1
  + item 2.2
.
* item 1
* item 2
  * item 2.1
  * item 2.2
````````````````````````````````


```````````````````````````````` example(Lists: 7) options(list-bullet-plus)
* item 1
* item 2
  * item 2.1
  * item 2.2
.
+ item 1
+ item 2
  + item 2.1
  + item 2.2
````````````````````````````````


```````````````````````````````` example(Lists: 8) options(list-bullet-dash)
* item 1
* item 2
  * item 2.1
  * item 2.2
.
- item 1
- item 2
  - item 2.1
  - item 2.2
````````````````````````````````


```````````````````````````````` example(Lists: 9) options(list-no-renumber-items)
1. item 1
1. item 2
   1. item 2.1
   1. item 2.2
.
1. item 1
1. item 2
   1. item 2.1
   1. item 2.2
````````````````````````````````


```````````````````````````````` example(Lists: 10) options(list-no-renumber-items, list-numbered-paren)
1. item 1
1. item 2
   1. item 2.1
   1. item 2.2
.
1) item 1
1) item 2
   1) item 2.1
   1) item 2.2
````````````````````````````````


```````````````````````````````` example(Lists: 11) options(list-no-renumber-items, list-numbered-dot)
1) item 1
1) item 2
   1) item 2.1
   1) item 2.2
.
1. item 1
1. item 2
   1. item 2.1
   1. item 2.2
````````````````````````````````


list spacing

```````````````````````````````` example Lists: 12
* item 1
* item 2
  * item 2.1
  
  * item 2.2
  
* item 3
.
* item 1

* item 2

  * item 2.1

  * item 2.2

* item 3

````````````````````````````````


```````````````````````````````` example(Lists: 13) options(list-spacing-loose)
* item 1
* item 2
  * item 2.1
  
  * item 2.2
  
* item 3
.
* item 1

* item 2

  * item 2.1

  * item 2.2

* item 3
````````````````````````````````


```````````````````````````````` example(Lists: 14) options(list-spacing-tight)
* item 1
* item 2
  * item 2.1
  
  * item 2.2
  
* item 3
.
* item 1
* item 2
  * item 2.1
  * item 2.2
* item 3
````````````````````````````````


```````````````````````````````` example(Lists: 15) options(list-spacing-loosen)
* item 1
* item 2
  * item 2.1
  
  * item 2.2
  
* item 3
.
* item 1

* item 2

  * item 2.1

  * item 2.2

* item 3
````````````````````````````````


```````````````````````````````` example(Lists: 16) options(list-spacing-tighten)
* item 1
* item 2
  * item 2.1
  
  * item 2.2
  
* item 3
.
* item 1
* item 2
  * item 2.1

  * item 2.2
* item 3
````````````````````````````````


```````````````````````````````` example(Lists: 17) options(list-spacing-tighten)
* item 1
* item 2
  * item 2.1
  
  * item 2.2
  
* item 3

paragraph
.
* item 1
* item 2
  * item 2.1

  * item 2.2
* item 3

paragraph
````````````````````````````````


```````````````````````````````` example(Lists: 18) options(list-spacing-tight)
* item 1
* item 2
  * item 2.1
  
  * item 2.2
  
* item 3

paragraph
.
* item 1
* item 2
  * item 2.1
  * item 2.2
* item 3

paragraph
````````````````````````````````


### Reference Placement

default

```````````````````````````````` example Reference Placement: 1
paragraph 1 [ref2]

[ref3]: /ref3

[ref2]: /ref2/1

paragraph 2

[ref2]: /ref2/2

[ref1]: /ref1

paragraph 3

.
paragraph 1 [ref2]

[ref3]: /ref3

[ref2]: /ref2/1

paragraph 2

[ref2]: /ref2/2

[ref1]: /ref1

paragraph 3

````````````````````````````````


```````````````````````````````` example(Reference Placement: 2) options(references-document-top)
paragraph 1 [ref2]

[ref3]: /ref3

[ref2]: /ref2/1

paragraph 2

[ref2]: /ref2/2

[ref1]: /ref1

paragraph 3

.
[ref3]: /ref3
[ref2]: /ref2/1
[ref2]: /ref2/2
[ref1]: /ref1

paragraph 1 [ref2]

paragraph 2

paragraph 3

````````````````````````````````


```````````````````````````````` example(Reference Placement: 3) options(references-document-bottom)
paragraph 1 [ref2]

[ref3]: /ref3

[ref2]: /ref2/1

paragraph 2

[ref2]: /ref2/2

[ref1]: /ref1

paragraph 3

.
paragraph 1 [ref2]

paragraph 2

paragraph 3

[ref3]: /ref3
[ref2]: /ref2/1
[ref2]: /ref2/2
[ref1]: /ref1

````````````````````````````````


```````````````````````````````` example(Reference Placement: 4) options(references-group-with-first)
paragraph 1 [ref2]

[ref3]: /ref3

[ref2]: /ref2/1

paragraph 2

[ref2]: /ref2/2

[ref1]: /ref1

paragraph 3

.
paragraph 1 [ref2]

[ref3]: /ref3
[ref2]: /ref2/1
[ref2]: /ref2/2
[ref1]: /ref1

paragraph 2

paragraph 3

````````````````````````````````


```````````````````````````````` example(Reference Placement: 5) options(references-group-with-last)
paragraph 1 [ref2]

[ref3]: /ref3

[ref2]: /ref2/1

paragraph 2

[ref2]: /ref2/2

[ref1]: /ref1

paragraph 3

.
paragraph 1 [ref2]

paragraph 2

[ref3]: /ref3
[ref2]: /ref2/1
[ref2]: /ref2/2
[ref1]: /ref1

paragraph 3

````````````````````````````````


```````````````````````````````` example(Reference Placement: 6) options(references-document-bottom, references-sort)
paragraph 1 [ref2]

[ref3]: /ref3

[ref2]: /ref2/1

paragraph 2

[ref2]: /ref2/2

[ref1]: /ref1

paragraph 3

.
paragraph 1 [ref2]

paragraph 2

paragraph 3

[ref1]: /ref1
[ref2]: /ref2/1
[ref2]: /ref2/2
[ref3]: /ref3

````````````````````````````````


```````````````````````````````` example(Reference Placement: 7) options(references-document-bottom, references-sort-unused-last)
paragraph 1 [ref2]

[ref3]: /ref3

[ref2]: /ref2/1

paragraph 2

[ref2]: /ref2/2

[ref1]: /ref1

paragraph 3

.
paragraph 1 [ref2]

paragraph 2

paragraph 3

[ref2]: /ref2/1
[ref1]: /ref1
[ref2]: /ref2/2
[ref3]: /ref3

````````````````````````````````


```````````````````````````````` example(Reference Placement: 8) options(references-document-bottom, references-sort-unused-last, references-keep-last)
paragraph 1 [ref2]

[ref3]: /ref3

[ref2]: /ref2/1

paragraph 2

[ref2]: /ref2/2

[ref1]: /ref1

paragraph 3

.
paragraph 1 [ref2]

paragraph 2

paragraph 3

[ref2]: /ref2/2
[ref1]: /ref1
[ref2]: /ref2/1
[ref3]: /ref3

````````````````````````````````


