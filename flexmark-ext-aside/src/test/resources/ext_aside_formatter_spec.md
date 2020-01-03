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
| | block quote
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
|| block quote
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
        Text[4, 15] chars:[4, 15, "block … quote"]
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
| | block quote
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
|| block quote
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
| | block quote
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
| | block quote
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
  | block quote
  lazy continuation
.
paragraph text
lazy continuation
* list item

  | block quote
  | lazy continuation

````````````````````````````````


```````````````````````````````` example(Aside: 8) options(no-block-quote-blank-lines)
paragraph text 
lazy continuation
* list item
  | block quote
  lazy continuation
.
paragraph text
lazy continuation
* list item
  | block quote
  | lazy continuation

````````````````````````````````


as first child of empty item

```````````````````````````````` example Aside: 9
* | block quote
1. | block quote  
.
* | block quote

1. | block quote

````````````````````````````````


#### Compact Space

```````````````````````````````` example(Aside - Compact Space: 1) options(block-quote-compact-with-space)
|| 1. numbered item 1 
||    numbered item 1 
||
.
|| 1. numbered item 1
||    numbered item 1

````````````````````````````````


```````````````````````````````` example(Aside - Compact Space: 2) options(block-quote-compact-with-space)
|| numbered item 1 
|| continuation
||
.
|| numbered item 1
|| continuation

````````````````````````````````


```````````````````````````````` example(Aside - Compact Space: 3) options(block-quote-compact-with-space)
|| block quote
lazy continuation
.
|| block quote
|| lazy continuation

````````````````````````````````


#### Compact

```````````````````````````````` example(Aside - Compact: 1) options(block-quote-compact)
|| 1. numbered item 1 
||    numbered item 1 
||
.
||1. numbered item 1
||   numbered item 1

````````````````````````````````


```````````````````````````````` example(Aside - Compact: 2) options(block-quote-compact)
|| numbered item 1 
|| continuation
||
.
||numbered item 1
||continuation

````````````````````````````````


```````````````````````````````` example(Aside - Compact: 3) options(block-quote-compact)
paragraph text 
lazy continuation
* list item
  || block quote
  lazy continuation
.
paragraph text
lazy continuation
* list item

  ||block quote
  ||lazy continuation

````````````````````````````````


#### Spaced

```````````````````````````````` example(Aside - Spaced: 1) options(block-quote-spaced)
|| 1. numbered item 1 
||    numbered item 1 
||
.
| | 1. numbered item 1
| |    numbered item 1

````````````````````````````````


```````````````````````````````` example(Aside - Spaced: 2) options(block-quote-spaced)
|| numbered item 1 
|| continuation
||
.
| | numbered item 1
| | continuation

````````````````````````````````


```````````````````````````````` example(Aside - Spaced: 3) options(block-quote-spaced)
paragraph text 
lazy continuation
* list item
  || block quote
  lazy continuation
.
paragraph text
lazy continuation
* list item

  | | block quote
  | | lazy continuation

````````````````````````````````


```````````````````````````````` example(Aside - Spaced: 4) options(block-quote-spaced)
paragraph text 
lazy continuation
* list item
  || block quote
  lazy continuation
.
paragraph text
lazy continuation
* list item

  | | block quote
  | | lazy continuation

````````````````````````````````


## Combined


### Quote Aside

#### Compact Space


```````````````````````````````` example(Combined - Quote Aside - Compact Space: 1) options(block-quote-compact-with-space)
* item
| 1. asdlf asdfl fdsa
|
| 2. > 1. asdfasdf
|    > 2. asdfdsaf
|    > 3. asdfsadf
|    > 4. asdfasdfsdfsdafdsflsadfkjlsdfkj lsafdj l;sajfd ;lsajfd
|    >    ;lsajkdf ;lajsfd ;lsajdf ;lsjkfd
|    > 5. 
|    > 6. sdasdfsa df;lasj df;lksjdf ;asldfj
|    > 7. alsdfkj al;sdfj las;kdfj l;asdfj l;asjfdl;asjkdf l;asjkdf
|    >    l;asjf d;lasjdf ;lasj fd;lasj df;lasj dfl;asj dfla;sj dfl;asj
|    >    dfl;saj dfla;sdfj
|    >    1. asdfdsf
|    >    2. asdfasdf
|    >       1. asdfasfd >|Test
|    >       2. asdfasfd >|Test
|    >    3. asdfasdf
|    >    4. asdfsdf
|    > 8. asdfdsf
|    >    1. asdfdsf
|    > 9. asdfasdfdsaf
.
* item

| 1. asdlf asdfl fdsa
|
| 2. > 1. asdfasdf
|    > 2. asdfdsaf
|    > 3. asdfsadf
|    > 4. asdfasdfsdfsdafdsflsadfkjlsdfkj lsafdj l;sajfd ;lsajfd
|    >    ;lsajkdf ;lajsfd ;lsajdf ;lsjkfd
|    > 5. 6. sdasdfsa df;lasj df;lksjdf ;asldfj
|    > 7. alsdfkj al;sdfj las;kdfj l;asdfj l;asjfdl;asjkdf l;asjkdf
|    >    l;asjf d;lasjdf ;lasj fd;lasj df;lasj dfl;asj dfla;sj dfl;asj
|    >    dfl;saj dfla;sdfj
|    >    1. asdfdsf
|    >    2. asdfasdf
|    >       1. asdfasfd >|Test
|    >       2. asdfasfd >|Test
|    >    3. asdfasdf
|    >    4. asdfsdf
|    > 8. asdfdsf
|    >    1. asdfdsf
|    > 9. asdfasdfdsaf

````````````````````````````````


```````````````````````````````` example(Combined - Quote Aside - Compact Space: 2) options(block-quote-compact-with-space)
>| 1. numbered item 1 
>|    numbered item 1 
>|
.
>| 1. numbered item 1
>|    numbered item 1

````````````````````````````````


```````````````````````````````` example(Combined - Quote Aside - Compact Space: 3) options(block-quote-compact-with-space)
>| numbered item 1 
>| continuation
>|
.
>| numbered item 1
>| continuation

````````````````````````````````


```````````````````````````````` example(Combined - Quote Aside - Compact Space: 4) options(block-quote-compact-with-space)
>| block quote
lazy continuation
.
>| block quote
>| lazy continuation

````````````````````````````````


#### Compact

```````````````````````````````` example(Combined - Quote Aside - Compact: 1) options(block-quote-compact)
>| 1. numbered item 1 
>|    numbered item 1 
>|
.
>|1. numbered item 1
>|   numbered item 1

````````````````````````````````


```````````````````````````````` example(Combined - Quote Aside - Compact: 2) options(block-quote-compact)
>| numbered item 1 
>| continuation
>|
.
>|numbered item 1
>|continuation

````````````````````````````````


```````````````````````````````` example(Combined - Quote Aside - Compact: 3) options(block-quote-compact)
paragraph text 
lazy continuation
* list item
  >| block quote
  lazy continuation
.
paragraph text
lazy continuation
* list item

  >|block quote
  >|lazy continuation

````````````````````````````````


#### Spaced

```````````````````````````````` example(Combined - Quote Aside - Spaced: 1) options(block-quote-spaced)
>| 1. numbered item 1 
>|    numbered item 1 
>|
.
> | 1. numbered item 1
> |    numbered item 1

````````````````````````````````


```````````````````````````````` example(Combined - Quote Aside - Spaced: 2) options(block-quote-spaced)
>| numbered item 1 
>| continuation
>|
.
> | numbered item 1
> | continuation

````````````````````````````````


```````````````````````````````` example(Combined - Quote Aside - Spaced: 3) options(block-quote-spaced)
paragraph text 
lazy continuation
* list item
  >| block quote
  lazy continuation
.
paragraph text
lazy continuation
* list item

  > | block quote
  > | lazy continuation

````````````````````````````````


```````````````````````````````` example(Combined - Quote Aside - Spaced: 4) options(block-quote-spaced)
paragraph text 
lazy continuation
* list item
  >| block quote
  lazy continuation
.
paragraph text
lazy continuation
* list item

  > | block quote
  > | lazy continuation

````````````````````````````````


### Aside Quote

#### Compact Space

```````````````````````````````` example(Combined - Aside Quote - Compact Space: 1) options(block-quote-compact-with-space)
|> 1. numbered item 1 
|>    numbered item 1 
|>
.
|> 1. numbered item 1
|>    numbered item 1

````````````````````````````````


```````````````````````````````` example(Combined - Aside Quote - Compact Space: 2) options(block-quote-compact-with-space)
|> numbered item 1 
|> continuation
|>
.
|> numbered item 1
|> continuation

````````````````````````````````


```````````````````````````````` example(Combined - Aside Quote - Compact Space: 3) options(block-quote-compact-with-space)
|> block quote
lazy continuation
.
|> block quote
|> lazy continuation

````````````````````````````````


#### Compact

```````````````````````````````` example(Combined - Aside Quote - Compact: 1) options(block-quote-compact)
|> 1. numbered item 1 
|>    numbered item 1 
|>
.
|>1. numbered item 1
|>   numbered item 1

````````````````````````````````


```````````````````````````````` example(Combined - Aside Quote - Compact: 2) options(block-quote-compact)
|> numbered item 1 
|> continuation
|>
.
|>numbered item 1
|>continuation

````````````````````````````````


```````````````````````````````` example(Combined - Aside Quote - Compact: 3) options(block-quote-compact)
paragraph text 
lazy continuation
* list item
  |> block quote
  lazy continuation
.
paragraph text
lazy continuation
* list item

  |>block quote
  |>lazy continuation

````````````````````````````````


#### Spaced

```````````````````````````````` example(Combined - Aside Quote - Spaced: 1) options(block-quote-spaced)
|> 1. numbered item 1 
|>    numbered item 1 
|>
.
| > 1. numbered item 1
| >    numbered item 1

````````````````````````````````


```````````````````````````````` example(Combined - Aside Quote - Spaced: 2) options(block-quote-spaced)
|> numbered item 1 
|> continuation
|>
.
| > numbered item 1
| > continuation

````````````````````````````````


```````````````````````````````` example(Combined - Aside Quote - Spaced: 3) options(block-quote-spaced)
paragraph text 
lazy continuation
* list item
  |> block quote
  lazy continuation
.
paragraph text
lazy continuation
* list item

  | > block quote
  | > lazy continuation

````````````````````````````````


```````````````````````````````` example(Combined - Aside Quote - Spaced: 4) options(block-quote-spaced)
paragraph text 
lazy continuation
* list item
  |> block quote
  lazy continuation
.
paragraph text
lazy continuation
* list item

  | > block quote
  | > lazy continuation

````````````````````````````````


