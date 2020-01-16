---
title: Formatter No Blank Lines in AST Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

# Formatter No Blank Lines in AST Spec

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
  FencedCodeBlock[94, 181] open:[94, 97, "~~~"] info:[97, 101, "info"] content:[102, 178] lines[3] close:[178, 181, "~~~"]
    Text[102, 178] chars:[102, 178, "      … code\n"]
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


```````````````````````````````` example(Formatter: 3) options(format-fixed-indent, atx-space-add, atx-trailing-add, list-no-renumber-items, block-quote-compact-with-space, fenced-code-spaced-info, list-bullet-plus, fenced-code-marker-backtick, fenced-code-marker-length, fenced-code-minimize, indented-code-minimize)
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
   
   ~~~info
           with uneven indent
              with uneven indent
        indented code
   ~~~ 
   
           with uneven indent
              with uneven indent
        indented code
.
# Heading #

-----

paragraph text
lazy continuation
+ list item

    > block quote
    > lazy continuation

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

    `````` info
       with uneven indent
          with uneven indent
    indented code
    ``````

           with uneven indent
              with uneven indent
        indented code

````````````````````````````````


## Lists

```````````````````````````````` example Lists: 1
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


```````````````````````````````` example Lists: 2
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


```````````````````````````````` example Lists: 3
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


```````````````````````````````` example Lists: 4
1. list item 1
   1. list item 1.1
   1) list item 1.2
.
1. list item 1
   1. list item 1.1
   1) list item 1.2

````````````````````````````````


```````````````````````````````` example(Lists: 5) options(list-numbered-dot)
1. list item 1
   1. list item 1.1
   1) list item 1.2
.
1. list item 1
   1. list item 1.1
   1. list item 1.2

````````````````````````````````


```````````````````````````````` example(Lists: 6) options(list-numbered-paren)
1. list item 1
   1. list item 1.1
   1) list item 1.2
.
1) list item 1
   1) list item 1.1
   1) list item 1.2

````````````````````````````````


```````````````````````````````` example Lists: 7
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


```````````````````````````````` example Lists: 8
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


```````````````````````````````` example Lists: 9
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


```````````````````````````````` example(Lists: 10) options(list-add-blank-line-before)
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


```````````````````````````````` example(Lists: 11) options(list-add-blank-line-before)
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


```````````````````````````````` example(Lists: 12) options(list-bullet-asterisk)
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


```````````````````````````````` example(Lists: 13) options(list-bullet-plus)
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


```````````````````````````````` example(Lists: 14) options(list-bullet-dash)
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


```````````````````````````````` example(Lists: 15) options(list-no-renumber-items)
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


```````````````````````````````` example(Lists: 16) options(list-no-renumber-items, list-numbered-paren)
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


```````````````````````````````` example(Lists: 17) options(list-no-renumber-items, list-numbered-dot)
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

```````````````````````````````` example Lists: 18
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


```````````````````````````````` example(Lists: 19) options(list-spacing-loose)
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


```````````````````````````````` example(Lists: 20) options(list-spacing-tight)
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


```````````````````````````````` example(Lists: 21) options(list-spacing-loosen)
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


```````````````````````````````` example(Lists: 22) options(list-spacing-tighten)
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


```````````````````````````````` example(Lists: 23) options(list-spacing-tighten)
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


```````````````````````````````` example(Lists: 24) options(list-spacing-tight)
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


list family changing

```````````````````````````````` example(Lists: 25) options(format-fixed-indent)
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


```````````````````````````````` example Lists: 26
- [link](link.txt)

next line
.
- [link](link.txt)

next line
````````````````````````````````


```````````````````````````````` example Lists: 27
- [link](link.txt)

next line
.
- [link](link.txt)

next line
````````````````````````````````


## Block Quotes

```````````````````````````````` example(Block Quotes: 1) options(block-quote-compact-with-space, fenced-code-minimize)
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
>     indented code
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
      Text[51, 80] chars:[51, 80, "      … code\n"]
    IndentedCodeBlock[96, 111]
    OrderedList[113, 131] isTight delimiter:'.'
      OrderedListItem[113, 131] open:[113, 115, "1."] isTight
        Paragraph[116, 131]
          Text[116, 131] chars:[116, 131, "numbe … tem 1"]
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


```````````````````````````````` example(Block Quotes: 3) options(block-quote-spaced)
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


```````````````````````````````` example Block Quotes: 4
paragraph text 
lazy continuation
* list item
  > block quote
  lazy continuation
.
paragraph text
lazy continuation
* list item

  > block quote
  > lazy continuation

````````````````````````````````


```````````````````````````````` example(Block Quotes: 5) options(no-block-quote-blank-lines)
paragraph text 
lazy continuation
* list item
  > block quote
  lazy continuation
.
paragraph text
lazy continuation
* list item
  > block quote
  > lazy continuation

````````````````````````````````


## Blank Lines

default

```````````````````````````````` example Blank Lines: 1
paragraph



another paragraph
.
paragraph

another paragraph
.
Document[0, 30]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 9] chars:[0, 9, "paragraph"]
  Paragraph[13, 30]
    Text[13, 30] chars:[13, 30, "anoth … graph"]
````````````````````````````````


blank lines

```````````````````````````````` example(Blank Lines: 2) options(max-blank-lines-1)
paragraph




another paragraph
.
paragraph

another paragraph
.
Document[0, 31]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 9] chars:[0, 9, "paragraph"]
  Paragraph[14, 31]
    Text[14, 31] chars:[14, 31, "anoth … graph"]
````````````````````````````````


```````````````````````````````` example(Blank Lines: 3) options(max-blank-lines-2)
paragraph




another paragraph
.
paragraph

another paragraph
.
Document[0, 31]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 9] chars:[0, 9, "paragraph"]
  Paragraph[14, 31]
    Text[14, 31] chars:[14, 31, "anoth … graph"]
````````````````````````````````


```````````````````````````````` example(Blank Lines: 4) options(max-blank-lines-3)
paragraph




another paragraph
.
paragraph

another paragraph
.
Document[0, 31]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 9] chars:[0, 9, "paragraph"]
  Paragraph[14, 31]
    Text[14, 31] chars:[14, 31, "anoth … graph"]
````````````````````````````````


default

```````````````````````````````` example Blank Lines: 5
paragraph


.
paragraph

.
Document[0, 12]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 9] chars:[0, 9, "paragraph"]
````````````````````````````````


no trailing blank lines

```````````````````````````````` example(Blank Lines: 6) options(no-tailing-blanks)
paragraph


.
paragraph
.
Document[0, 12]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 9] chars:[0, 9, "paragraph"]
````````````````````````````````


## Headings

```````````````````````````````` example(Headings: 1) options(atx-space-remove)
# Heading
.
#Heading

````````````````````````````````


```````````````````````````````` example(Headings: 2) options(atx-space-remove, atx-trailing-equalize)
# Heading ####
.
#Heading#

````````````````````````````````


```````````````````````````````` example(Headings: 3) options(atx-space-as-is, atx-trailing-equalize)
# Heading ####
.
# Heading #

````````````````````````````````


```````````````````````````````` example(Headings: 4) options(atx-space-add, atx-trailing-equalize)
# Heading ####
.
# Heading #

````````````````````````````````


```````````````````````````````` example(Headings: 5) options(atx-space-remove, atx-trailing-as-is)
# Heading ####
.
#Heading####

````````````````````````````````


```````````````````````````````` example(Headings: 6) options(atx-space-add, atx-trailing-equalize)
#Heading####
.
# Heading #

````````````````````````````````


```````````````````````````````` example(Headings: 7) options(atx-space-add, atx-trailing-remove)
#Heading####
.
# Heading

.
Document[0, 12]
  Heading[0, 12] textOpen:[0, 1, "#"] text:[1, 8, "Heading"] textClose:[8, 12, "####"]
    Text[1, 8] chars:[1, 8, "Heading"]
````````````````````````````````


```````````````````````````````` example Headings: 8
Heading
==
.
Heading
=======

````````````````````````````````


```````````````````````````````` example Headings: 9
Heading
---
.
Heading
-------

````````````````````````````````


```````````````````````````````` example(Headings: 10) options(setext-no-equalize)
Heading
==
.
Heading
==

````````````````````````````````


```````````````````````````````` example(Headings: 11) options(setext-no-equalize)
Heading
---
.
Heading
---

````````````````````````````````


## Thematic Break

```````````````````````````````` example Thematic Break: 1
---
.
---

````````````````````````````````


```````````````````````````````` example(Thematic Break: 2) options(thematic-break)
---
.
*** ** * ** ***

````````````````````````````````


## Fenced Code

```````````````````````````````` example Fenced Code: 1
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
```

````````````````````````````````


```````````````````````````````` example Fenced Code: 2
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


```````````````````````````````` example(Fenced Code: 3) options(fenced-code-minimize)
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
```

````````````````````````````````


```````````````````````````````` example(Fenced Code: 4) options(fenced-code-minimize)
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
```

````````````````````````````````


```````````````````````````````` example(Fenced Code: 5) options(fenced-code-minimize)
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


```````````````````````````````` example(Fenced Code: 6) options(fenced-code-marker-backtick)
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
```

````````````````````````````````


```````````````````````````````` example(Fenced Code: 7) options(fenced-code-marker-tilde)
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
~~~

````````````````````````````````


```````````````````````````````` example(Fenced Code: 8) options(fenced-code-match-closing)
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


```````````````````````````````` example(Fenced Code: 9) options(fenced-code-match-closing)
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


```````````````````````````````` example(Fenced Code: 10) options(fenced-code-marker-length)
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


```````````````````````````````` example(Fenced Code: 11) options(fenced-code-marker-length)
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


## Reference Placement

default

```````````````````````````````` example Reference Placement: 1
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


```````````````````````````````` example(Reference Placement: 2) options(references-document-top)
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


```````````````````````````````` example(Reference Placement: 3) options(references-document-bottom)
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


```````````````````````````````` example(Reference Placement: 4) options(references-group-with-first)
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


```````````````````````````````` example(Reference Placement: 5) options(references-group-with-last)
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


```````````````````````````````` example(Reference Placement: 6) options(references-document-bottom, references-sort)
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


```````````````````````````````` example(Reference Placement: 7) options(references-document-bottom, references-sort-unused-last)
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


```````````````````````````````` example(Reference Placement: 8) options(references-document-bottom, references-sort-unused-last, references-keep-last)
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


## Images and links at start of line

```````````````````````````````` example Images and links at start of line: 1
text with [link](/url) followed by ![alt](/image)
.
text with [link](/url) followed by ![alt](/image)
````````````````````````````````


```````````````````````````````` example(Images and links at start of line: 2) options(image-links-at-start)
text with [link](/url) followed by ![alt](/image)
.
text with [link](/url) followed by 
![alt](/image)
````````````````````````````````


```````````````````````````````` example(Images and links at start of line: 3) options(explicit-links-at-start)
text with [link](/url) followed by ![alt](/image)
.
text with 
[link](/url) followed by ![alt](/image)
````````````````````````````````


```````````````````````````````` example(Images and links at start of line: 4) options(image-links-at-start, explicit-links-at-start)
text with [link](/url) followed by ![alt](/image)
.
text with 
[link](/url) followed by 
![alt](/image)
````````````````````````````````


## Format Conversion

```````````````````````````````` example Format Conversion: 1
Adds missing editor actions for end of word navigation but that is just the beginning:

* Enable Auto Indent Lines after move line/selection up or down actions to have them indented
  automatically.
* Use Smart Paste to eliminate case change and prefix edits when pasting identifiers. MIA will
  match case and style of identifier at destination when you paste, undo to get results before
  MIA adjusted them. Copy `myColumnData` and paste it over `DEFAULT_VALUE` to get `COLUMN_DATA`,
  reverse the order and get `myDefaultValue`. Works when pasting at the **beginning**, **end**
  and **middle** of identifiers.

  Supports: **camelCase**, **PascalCase**, **snake_case**, **SCREAMING_SNAKE_CASE**,
  **dash-case**, **dot.case**, **slash/case**

  Default prefixes: `my`, `our`, `is`, `get`, `set` to allow pasting over member fields, static
  fields, getters and setters.
* Enable Auto Line Selections and select full lines without loosing time or column position by
  moving the caret to the start of line when selecting or pasting. **Choose** whether you want
  to **paste full line** selections: **above** or **below** the current line regardless of the
  caret's column.
* Toggle between selection and multiple carets on selected lines to save time re-selecting the
  same text again.
* Filter multiple carets saves you time when creating multiple carets by removing carets on
  blank or comment lines so you can edit only code lines.
* Enhanced Paste from History dialog:
  * **combine**, **arrange** and **reverse** the order of content entries
  * **combine multiple** clipboard contents **with caret information intact**
  * **paste and re-create multiple carets** from information already stored on the clipboard
  * **duplicate line/block for each caret** in the clipboard content and **put a caret on the
    first line** of the block, ready for multi-caret select and paste
  * see caret information stored on the clipboard for each content entry
* Many more options and adjustments to make multiple caret text editing fast, efficient and
  easy. **Plugin website:
  [<span style="color:#30A0D8">Missing In Actions GitHub Repo</span>](http://github.com/vsch/MissingInActions)**
  **Bug tracking & feature requests:
  [<span style="color:#30A0D8">Missing In Actions GitHub Issues</span>](http://github.com/vsch/MissingInActions)**
.
Adds missing editor actions for end of word navigation but that is just the beginning:

* Enable Auto Indent Lines after move line/selection up or down actions to have them indented
  automatically.

* Use Smart Paste to eliminate case change and prefix edits when pasting identifiers. MIA will
  match case and style of identifier at destination when you paste, undo to get results before
  MIA adjusted them. Copy `myColumnData` and paste it over `DEFAULT_VALUE` to get `COLUMN_DATA`,
  reverse the order and get `myDefaultValue`. Works when pasting at the **beginning**, **end**
  and **middle** of identifiers.

  Supports: **camelCase**, **PascalCase**, **snake_case**, **SCREAMING_SNAKE_CASE**,
  **dash-case**, **dot.case**, **slash/case**

  Default prefixes: `my`, `our`, `is`, `get`, `set` to allow pasting over member fields, static
  fields, getters and setters.

* Enable Auto Line Selections and select full lines without loosing time or column position by
  moving the caret to the start of line when selecting or pasting. **Choose** whether you want
  to **paste full line** selections: **above** or **below** the current line regardless of the
  caret's column.

* Toggle between selection and multiple carets on selected lines to save time re-selecting the
  same text again.

* Filter multiple carets saves you time when creating multiple carets by removing carets on
  blank or comment lines so you can edit only code lines.

* Enhanced Paste from History dialog:

  * **combine**, **arrange** and **reverse** the order of content entries
  * **combine multiple** clipboard contents **with caret information intact**
  * **paste and re-create multiple carets** from information already stored on the clipboard
  * **duplicate line/block for each caret** in the clipboard content and **put a caret on the
    first line** of the block, ready for multi-caret select and paste
  * see caret information stored on the clipboard for each content entry
* Many more options and adjustments to make multiple caret text editing fast, efficient and
  easy. **Plugin website:
  [<span style="color:#30A0D8">Missing In Actions GitHub Repo</span>](http://github.com/vsch/MissingInActions)**
  **Bug tracking & feature requests:
  [<span style="color:#30A0D8">Missing In Actions GitHub Issues</span>](http://github.com/vsch/MissingInActions)**

````````````````````````````````


```````````````````````````````` example(Format Conversion: 2) options(parse-github, format-fixed-indent)
Adds missing editor actions for end of word navigation but that is just the beginning:

* Enable Auto Indent Lines after move line/selection up or down actions to have them indented
  automatically.
* Use Smart Paste to eliminate case change and prefix edits when pasting identifiers. MIA will
  match case and style of identifier at destination when you paste, undo to get results before
  MIA adjusted them. Copy `myColumnData` and paste it over `DEFAULT_VALUE` to get `COLUMN_DATA`,
  reverse the order and get `myDefaultValue`. Works when pasting at the **beginning**, **end**
  and **middle** of identifiers.

  Supports: **camelCase**, **PascalCase**, **snake_case**, **SCREAMING_SNAKE_CASE**,
  **dash-case**, **dot.case**, **slash/case**

  Default prefixes: `my`, `our`, `is`, `get`, `set` to allow pasting over member fields, static
  fields, getters and setters.
* Enable Auto Line Selections and select full lines without loosing time or column position by
  moving the caret to the start of line when selecting or pasting. **Choose** whether you want
  to **paste full line** selections: **above** or **below** the current line regardless of the
  caret's column.
* Toggle between selection and multiple carets on selected lines to save time re-selecting the
  same text again.
* Filter multiple carets saves you time when creating multiple carets by removing carets on
  blank or comment lines so you can edit only code lines.
* Enhanced Paste from History dialog:
  * **combine**, **arrange** and **reverse** the order of content entries
  * **combine multiple** clipboard contents **with caret information intact**
  * **paste and re-create multiple carets** from information already stored on the clipboard
  * **duplicate line/block for each caret** in the clipboard content and **put a caret on the
    first line** of the block, ready for multi-caret select and paste
  * see caret information stored on the clipboard for each content entry
* Many more options and adjustments to make multiple caret text editing fast, efficient and
  easy. **Plugin website:
  [<span style="color:#30A0D8">Missing In Actions GitHub Repo</span>](http://github.com/vsch/MissingInActions)**
  **Bug tracking & feature requests:
  [<span style="color:#30A0D8">Missing In Actions GitHub Issues</span>](http://github.com/vsch/MissingInActions)**
.
Adds missing editor actions for end of word navigation but that is just the beginning:

* Enable Auto Indent Lines after move line/selection up or down actions to have them indented
    automatically.

* Use Smart Paste to eliminate case change and prefix edits when pasting identifiers. MIA will
    match case and style of identifier at destination when you paste, undo to get results before
    MIA adjusted them. Copy `myColumnData` and paste it over `DEFAULT_VALUE` to get `COLUMN_DATA`,
    reverse the order and get `myDefaultValue`. Works when pasting at the **beginning**, **end**
    and **middle** of identifiers.

    Supports: **camelCase**, **PascalCase**, **snake_case**, **SCREAMING_SNAKE_CASE**,
    **dash-case**, **dot.case**, **slash/case**

    Default prefixes: `my`, `our`, `is`, `get`, `set` to allow pasting over member fields, static
    fields, getters and setters.

* Enable Auto Line Selections and select full lines without loosing time or column position by
    moving the caret to the start of line when selecting or pasting. **Choose** whether you want
    to **paste full line** selections: **above** or **below** the current line regardless of the
    caret's column.

* Toggle between selection and multiple carets on selected lines to save time re-selecting the
    same text again.

* Filter multiple carets saves you time when creating multiple carets by removing carets on
    blank or comment lines so you can edit only code lines.

* Enhanced Paste from History dialog:

    * **combine**, **arrange** and **reverse** the order of content entries
    * **combine multiple** clipboard contents **with caret information intact**
    * **paste and re-create multiple carets** from information already stored on the clipboard
    * **duplicate line/block for each caret** in the clipboard content and **put a caret on the
        first line** of the block, ready for multi-caret select and paste
    * see caret information stored on the clipboard for each content entry
* Many more options and adjustments to make multiple caret text editing fast, efficient and
    easy. **Plugin website:
    [<span style="color:#30A0D8">Missing In Actions GitHub Repo</span>](http://github.com/vsch/MissingInActions)**
    **Bug tracking & feature requests:
    [<span style="color:#30A0D8">Missing In Actions GitHub Issues</span>](http://github.com/vsch/MissingInActions)**

````````````````````````````````


```````````````````````````````` example(Format Conversion: 3) options(parse-fixed-indent, format-github)
Adds missing editor actions for end of word navigation but that is just the beginning:

* Enable Auto Indent Lines after move line/selection up or down actions to have them indented
    automatically.
* Use Smart Paste to eliminate case change and prefix edits when pasting identifiers. MIA will
    match case and style of identifier at destination when you paste, undo to get results before
    MIA adjusted them. Copy `myColumnData` and paste it over `DEFAULT_VALUE` to get `COLUMN_DATA`,
    reverse the order and get `myDefaultValue`. Works when pasting at the **beginning**, **end**
    and **middle** of identifiers.

    Supports: **camelCase**, **PascalCase**, **snake_case**, **SCREAMING_SNAKE_CASE**,
    **dash-case**, **dot.case**, **slash/case**

    Default prefixes: `my`, `our`, `is`, `get`, `set` to allow pasting over member fields, static
    fields, getters and setters.
* Enable Auto Line Selections and select full lines without loosing time or column position by
    moving the caret to the start of line when selecting or pasting. **Choose** whether you want
    to **paste full line** selections: **above** or **below** the current line regardless of the
    caret's column.
* Toggle between selection and multiple carets on selected lines to save time re-selecting the
    same text again.
* Filter multiple carets saves you time when creating multiple carets by removing carets on
    blank or comment lines so you can edit only code lines.
* Enhanced Paste from History dialog:
    * **combine**, **arrange** and **reverse** the order of content entries
    * **combine multiple** clipboard contents **with caret information intact**
    * **paste and re-create multiple carets** from information already stored on the clipboard
    * **duplicate line/block for each caret** in the clipboard content and **put a caret on the
        first line** of the block, ready for multi-caret select and paste
    * see caret information stored on the clipboard for each content entry
* Many more options and adjustments to make multiple caret text editing fast, efficient and
    easy. **Plugin website:
    [<span style="color:#30A0D8">Missing In Actions GitHub Repo</span>](http://github.com/vsch/MissingInActions)**
    **Bug tracking & feature requests:
    [<span style="color:#30A0D8">Missing In Actions GitHub Issues</span>](http://github.com/vsch/MissingInActions)**
.
Adds missing editor actions for end of word navigation but that is just the beginning:

* Enable Auto Indent Lines after move line/selection up or down actions to have them indented
  automatically.
* Use Smart Paste to eliminate case change and prefix edits when pasting identifiers. MIA will
  match case and style of identifier at destination when you paste, undo to get results before
  MIA adjusted them. Copy `myColumnData` and paste it over `DEFAULT_VALUE` to get `COLUMN_DATA`,
  reverse the order and get `myDefaultValue`. Works when pasting at the **beginning**, **end**
  and **middle** of identifiers.

  Supports: **camelCase**, **PascalCase**, **snake_case**, **SCREAMING_SNAKE_CASE**,
  **dash-case**, **dot.case**, **slash/case**

  Default prefixes: `my`, `our`, `is`, `get`, `set` to allow pasting over member fields, static
  fields, getters and setters.

* Enable Auto Line Selections and select full lines without loosing time or column position by
  moving the caret to the start of line when selecting or pasting. **Choose** whether you want
  to **paste full line** selections: **above** or **below** the current line regardless of the
  caret's column.
* Toggle between selection and multiple carets on selected lines to save time re-selecting the
  same text again.
* Filter multiple carets saves you time when creating multiple carets by removing carets on
  blank or comment lines so you can edit only code lines.
* Enhanced Paste from History dialog:
  * **combine**, **arrange** and **reverse** the order of content entries
  * **combine multiple** clipboard contents **with caret information intact**
  * **paste and re-create multiple carets** from information already stored on the clipboard
  * **duplicate line/block for each caret** in the clipboard content and **put a caret on the
    first line** of the block, ready for multi-caret select and paste
  * see caret information stored on the clipboard for each content entry
* Many more options and adjustments to make multiple caret text editing fast, efficient and
  easy. **Plugin website:
  [<span style="color:#30A0D8">Missing In Actions GitHub Repo</span>](http://github.com/vsch/MissingInActions)**
  **Bug tracking & feature requests:
  [<span style="color:#30A0D8">Missing In Actions GitHub Issues</span>](http://github.com/vsch/MissingInActions)**

````````````````````````````````


```````````````````````````````` example(Format Conversion: 4) options(parse-fixed-indent, format-github, references-as-is)
![Screenshot](https://raw.githubusercontent.com/vsch/idea-multimarkdown/master/assets/images/plugin_description_img.png)

<img src="https://github.com/vsch/idea-multimarkdown/raw/master/assets/images/MNLogo.png?" height="20" width="20" border="0" style="padding-left:10px;">Markdown Navigator 2.0
==============================================================================================================================================================================

**[<span style="color:#30A0D8;">Markdown</span>][Markdown] language support for IntelliJ
platform**

**A Markdown plugin** with GFM and a **matching** preview style.

## Document with pleasure!

Work with [Markdown] files like you do with other languages in the IDE, by getting full support
for:

* completions to reduce typing
  * link address ⇐ files
  * ref anchors ⇐ headings
  * footnote refs ⇐ footnotes
  * ref links/ref images ⇐ references
  * link text ⇐ ref anchor/link address
* error and warning annotations to help catch mistakes early
* intention actions for fast results with less effort
* wrap on typing to keep it nicely formatted as you edit
* formatting to change format with a key stroke
* navigation and find usages to find references without effort
* refactoring of all referencing elements: to keep it all in sync while evolving
  * files ⟺ links
  * headings ⟺ ref anchors
  * footnotes ⟺ footnote refs
  * references ⟺ ref links/ref images
* GitHub style rendering that you are used to, out of the box
* Fast typing response for distraction free editing
* Fully customizable to adjust to your project's needs and your preferences
* Copy Markdown as JIRA, YouTrack or HTML formatted text or export as HTML
* Convert HTML content to Markdown by pasting it into a Markdown document.

## Features

* **<span style="color:#b200c2">Split Editor</span>**
* **Fast** typing response in large files
* **HTML text** preview and export
* Soft Wrap **on right margin**
* **Format** with code style:
  * **Multi-byte** support with mixed character width
  * **Table** justification
  * **Wrap on typing** auto format of element
  * **Renumbering** of list items
* **Bidirectional** Source and Preview synchronization
  * **Scrolls** preview to show source element at caret
  * **Moves caret** to source line of element clicked in preview
* Also does **completions, refactoring, validation, language injections, code folding**
* **Fully configurable** by project with support for scopes
* Understands **GitHub wiki** nuances
* Conversion between HTML and Markdown

[Markdown]: http://daringfireball.net/projects/markdown"
.
![Screenshot](https://raw.githubusercontent.com/vsch/idea-multimarkdown/master/assets/images/plugin_description_img.png)

<img src="https://github.com/vsch/idea-multimarkdown/raw/master/assets/images/MNLogo.png?" height="20" width="20" border="0" style="padding-left:10px;">Markdown Navigator 2.0
==============================================================================================================================================================================

**[<span style="color:#30A0D8;">Markdown</span>][Markdown] language support for IntelliJ
platform**

**A Markdown plugin** with GFM and a **matching** preview style.

## Document with pleasure!

Work with [Markdown] files like you do with other languages in the IDE, by getting full support
for:

* completions to reduce typing
* link address ⇐ files
* ref anchors ⇐ headings
* footnote refs ⇐ footnotes
* ref links/ref images ⇐ references
* link text ⇐ ref anchor/link address
* error and warning annotations to help catch mistakes early
* intention actions for fast results with less effort
* wrap on typing to keep it nicely formatted as you edit
* formatting to change format with a key stroke
* navigation and find usages to find references without effort
* refactoring of all referencing elements: to keep it all in sync while evolving
* files ⟺ links
* headings ⟺ ref anchors
* footnotes ⟺ footnote refs
* references ⟺ ref links/ref images
* GitHub style rendering that you are used to, out of the box
* Fast typing response for distraction free editing
* Fully customizable to adjust to your project's needs and your preferences
* Copy Markdown as JIRA, YouTrack or HTML formatted text or export as HTML
* Convert HTML content to Markdown by pasting it into a Markdown document.

## Features

* **<span style="color:#b200c2">Split Editor</span>**
* **Fast** typing response in large files
* **HTML text** preview and export
* Soft Wrap **on right margin**
* **Format** with code style:
* **Multi-byte** support with mixed character width
* **Table** justification
* **Wrap on typing** auto format of element
* **Renumbering** of list items
* **Bidirectional** Source and Preview synchronization
* **Scrolls** preview to show source element at caret
* **Moves caret** to source line of element clicked in preview
* Also does **completions, refactoring, validation, language injections, code folding**
* **Fully configurable** by project with support for scopes
* Understands **GitHub wiki** nuances
* Conversion between HTML and Markdown

[Markdown]: http://daringfireball.net/projects/markdown"

````````````````````````````````


Handle proper GitHub indented code in list items

```````````````````````````````` example(Format Conversion: 5) options(parse-fixed-indent, format-github)
* item 

        indented code
        
    * sub-item 
    
            indented sub-item code
.
* item

        indented code

  * sub-item

          indented sub-item code

````````````````````````````````


```````````````````````````````` example(Format Conversion: 6) options(parse-fixed-indent, format-github)
1. item 

        indented code
        
    1. sub-item 
    
            indented sub-item code
.
1. item

        indented code

   1. sub-item

           indented sub-item code

````````````````````````````````


## HTML Blocks

```````````````````````````````` example HTML Blocks: 1
line 1

  <img src="i.jpg">

line 2
.
line 1

  <img src="i.jpg">

line 2
.
Document[0, 35]
  Paragraph[0, 7] isTrailingBlankLine
    Text[0, 6] chars:[0, 6, "line 1"]
  HtmlBlock[8, 28]
  Paragraph[29, 35]
    Text[29, 35] chars:[29, 35, "line 2"]
````````````````````````````````


```````````````````````````````` example HTML Blocks: 2
line 1

<img src="i.jpg">

line 2
.
line 1

<img src="i.jpg">

line 2
.
Document[0, 33]
  Paragraph[0, 7] isTrailingBlankLine
    Text[0, 6] chars:[0, 6, "line 1"]
  HtmlBlock[8, 26]
  Paragraph[27, 33]
    Text[27, 33] chars:[27, 33, "line 2"]
````````````````````````````````


```````````````````````````````` example HTML Blocks: 3
* [Table Extension](https://github.com/vsch/flexmark-java/wiki/Extensions#tables) for
  [Markdown Formatter](https://github.com/vsch/flexmark-java/wiki/Markdown-Formatter) with
  column width and alignment of markdown tables:

  <table>
      <thead> <tr><th>Input</th> <th>Output</th> </tr> </thead>
      <tr><td>
      <pre><code class="language-markdown">day|time|spent
  :---|:---:|--:
  nov. 2. tue|10:00|4h 40m
  nov. 3. thu|11:00|4h
  nov. 7. mon|10:20|4h 20m
  total:|| 13h</code></pre>
      </td><td>
      <pre><code class="language-markdown">| day         | time  |   spent |
  |:------------|:-----:|--------:|
  | nov. 2. tue | 10:00 |  4h 40m |
  | nov. 3. thu | 11:00 |      4h |
  | nov. 7. mon | 10:20 |  4h 20m |
  | total:             ||     13h |</code></pre>
      </td></tr>
  </table>
.
* [Table Extension](https://github.com/vsch/flexmark-java/wiki/Extensions#tables) for
  [Markdown Formatter](https://github.com/vsch/flexmark-java/wiki/Markdown-Formatter) with
  column width and alignment of markdown tables:

  <table>
      <thead> <tr><th>Input</th> <th>Output</th> </tr> </thead>
      <tr><td>
      <pre><code class="language-markdown">day|time|spent
  :---|:---:|--:
  nov. 2. tue|10:00|4h 40m
  nov. 3. thu|11:00|4h
  nov. 7. mon|10:20|4h 20m
  total:|| 13h</code></pre>
      </td><td>
      <pre><code class="language-markdown">| day         | time  |   spent |
  |:------------|:-----:|--------:|
  | nov. 2. tue | 10:00 |  4h 40m |
  | nov. 3. thu | 11:00 |      4h |
  | nov. 7. mon | 10:20 |  4h 20m |
  | total:             ||     13h |</code></pre>
      </td></tr>
  </table>

````````````````````````````````


## Empty List Items

```````````````````````````````` example Empty List Items: 1
* list item 1
* 

* list item 2
* 
not a list item
.
* list item 1

* 

* list item 2

* 

not a list item
.
Document[0, 50]
  BulletList[0, 33] isLoose
    BulletListItem[0, 14] open:[0, 1, "*"] isLoose
      Paragraph[2, 14]
        Text[2, 13] chars:[2, 13, "list  … tem 1"]
    BulletListItem[14, 15] open:[14, 15, "*"] isLoose hadBlankLineAfter
    BulletListItem[18, 32] open:[18, 19, "*"] isLoose
      Paragraph[20, 32]
        Text[20, 31] chars:[20, 31, "list  … tem 2"]
    BulletListItem[32, 33] open:[32, 33, "*"] isLoose
  Paragraph[35, 50]
    Text[35, 50] chars:[35, 50, "not a …  item"]
````````````````````````````````


```````````````````````````````` example Empty List Items: 2
* list item 1
* 
* list item 2
* 
not a list item
.
* list item 1
* 
* list item 2
* 

not a list item
.
Document[0, 49]
  BulletList[0, 32] isTight
    BulletListItem[0, 14] open:[0, 1, "*"] isTight
      Paragraph[2, 14]
        Text[2, 13] chars:[2, 13, "list  … tem 1"]
    BulletListItem[14, 15] open:[14, 15, "*"] isTight
    BulletListItem[17, 31] open:[17, 18, "*"] isTight
      Paragraph[19, 31]
        Text[19, 30] chars:[19, 30, "list  … tem 2"]
    BulletListItem[31, 32] open:[31, 32, "*"] isTight
  Paragraph[34, 49]
    Text[34, 49] chars:[34, 49, "not a …  item"]
````````````````````````````````


With removal of empty list items

```````````````````````````````` example(Empty List Items: 3) options(remove-empty-items)
* list item 1
* 

* list item 2
* 
not a list item
.
* list item 1

* list item 2

not a list item
.
Document[0, 50]
  BulletList[0, 33] isLoose
    BulletListItem[0, 14] open:[0, 1, "*"] isLoose
      Paragraph[2, 14]
        Text[2, 13] chars:[2, 13, "list  … tem 1"]
    BulletListItem[14, 15] open:[14, 15, "*"] isLoose hadBlankLineAfter
    BulletListItem[18, 32] open:[18, 19, "*"] isLoose
      Paragraph[20, 32]
        Text[20, 31] chars:[20, 31, "list  … tem 2"]
    BulletListItem[32, 33] open:[32, 33, "*"] isLoose
  Paragraph[35, 50]
    Text[35, 50] chars:[35, 50, "not a …  item"]
````````````````````````````````


```````````````````````````````` example(Empty List Items: 4) options(remove-empty-items)
* list item 1
* 
* list item 2
* 
not a list item
.
* list item 1
* list item 2

not a list item
.
Document[0, 49]
  BulletList[0, 32] isTight
    BulletListItem[0, 14] open:[0, 1, "*"] isTight
      Paragraph[2, 14]
        Text[2, 13] chars:[2, 13, "list  … tem 1"]
    BulletListItem[14, 15] open:[14, 15, "*"] isTight
    BulletListItem[17, 31] open:[17, 18, "*"] isTight
      Paragraph[19, 31]
        Text[19, 30] chars:[19, 30, "list  … tem 2"]
    BulletListItem[31, 32] open:[31, 32, "*"] isTight
  Paragraph[34, 49]
    Text[34, 49] chars:[34, 49, "not a …  item"]
````````````````````````````````


```````````````````````````````` example(Empty List Items: 5) options(remove-empty-items)
1. list item 1
1. 

1. list item 2
1. 
not a list item
.
1. list item 1

2. list item 2

not a list item
.
Document[0, 54]
  OrderedList[0, 37] isLoose delimiter:'.'
    OrderedListItem[0, 15] open:[0, 2, "1."] isLoose
      Paragraph[3, 15]
        Text[3, 14] chars:[3, 14, "list  … tem 1"]
    OrderedListItem[15, 17] open:[15, 17, "1."] isLoose hadBlankLineAfter
    OrderedListItem[20, 35] open:[20, 22, "1."] isLoose
      Paragraph[23, 35]
        Text[23, 34] chars:[23, 34, "list  … tem 2"]
    OrderedListItem[35, 37] open:[35, 37, "1."] isLoose
  Paragraph[39, 54]
    Text[39, 54] chars:[39, 54, "not a …  item"]
````````````````````````````````


```````````````````````````````` example(Empty List Items: 6) options(remove-empty-items)
1. list item 1
1. 
1. list item 2
1. 
not a list item
.
1. list item 1
2. list item 2

not a list item
.
Document[0, 53]
  OrderedList[0, 36] isTight delimiter:'.'
    OrderedListItem[0, 15] open:[0, 2, "1."] isTight
      Paragraph[3, 15]
        Text[3, 14] chars:[3, 14, "list  … tem 1"]
    OrderedListItem[15, 17] open:[15, 17, "1."] isTight
    OrderedListItem[19, 34] open:[19, 21, "1."] isTight
      Paragraph[22, 34]
        Text[22, 33] chars:[22, 33, "list  … tem 2"]
    OrderedListItem[34, 36] open:[34, 36, "1."] isTight
  Paragraph[38, 53]
    Text[38, 53] chars:[38, 53, "not a …  item"]
````````````````````````````````


## Format Control

```````````````````````````````` example(Format Control: 1) options(formatter-tags-enabled, margin[72])

<!-- @formatter:off -->

<!-- @formatter:on -->
.
<!-- @formatter:off -->
<!-- @formatter:on -->
````````````````````````````````


```````````````````````````````` example(Format Control: 2) options(formatter-tags-enabled, margin[72])
Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod
tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At

<!-- @formatter:off -->

Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod
tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At

<!-- @formatter:on -->

Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod
tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At
.
Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy
eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam
voluptua. At

<!-- @formatter:off -->
Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod
tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At
<!-- @formatter:on -->
Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy
eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam
voluptua. At
````````````````````````````````


```````````````````````````````` example(Format Control: 3) options(formatter-tags-enabled, fenced-code-minimize, margin[72])
<!-- @formatter:off -->

* `fun appendListQuery(out: Appendable, params: Array<out Pair<String, Any?>>, alias: String? = null): Appendable`

<!-- @formatter:on -->

* `fun appendListQuery(out: Appendable, params: Array<out Pair<String, Any?>>, alias: String? = null): Appendable`

```kotlin
   data class ValidData(
      val processId: Int,
   )
```
.
<!-- @formatter:off -->
* `fun appendListQuery(out: Appendable, params: Array<out Pair<String, Any?>>, alias: String? = null): Appendable`
<!-- @formatter:on -->
* `fun appendListQuery(out: Appendable, params: Array<out Pair<String,
  Any?>>, alias: String? = null): Appendable`

```kotlin
data class ValidData(
   val processId: Int,
)
```

````````````````````````````````


```````````````````````````````` example(Format Control: 4) options(formatter-tags-enabled, margin[72])
* Add: to `BasedSequence`
  <!-- @formatter:off -->
  * Add: `BasedSequence.extendToEndOfLine(CharSequence eolChars, boolean includeEol)` - extend end to end of line in basedSequence
  * Add: `BasedSequence.extendToStartOfLine(CharSequence eolChars, boolean includeEol)` - extend start to start of line in basedSequence
  <!-- @formatter:on -->
  * Add: `BasedSequence.extendToStartOfLine(CharSequence eolChars, boolean includeEol)` - extend start to start of line in basedSequence
.
* Add: to `BasedSequence`
  <!-- @formatter:off -->
  * Add: `BasedSequence.extendToEndOfLine(CharSequence eolChars, boolean includeEol)` - extend end to end of line in basedSequence
  * Add: `BasedSequence.extendToStartOfLine(CharSequence eolChars, boolean includeEol)` - extend start to start of line in basedSequence
  <!-- @formatter:on -->
  * Add: `BasedSequence.extendToStartOfLine(CharSequence eolChars,
    boolean includeEol)` - extend start to start of line in
    basedSequence

````````````````````````````````


