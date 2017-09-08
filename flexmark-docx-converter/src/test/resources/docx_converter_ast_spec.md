---
title: DocxConverter Extension Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## DocxConverter

Converts markdown to docx

### Paragraphs

basic text

```````````````````````````````` example(Paragraphs: 1) options(IGNORES)
Sample text
.
<p>Expected rendered HTML</p>
.
````````````````````````````````


```````````````````````````````` example(Paragraphs: 2) options(IGNORES)
**Bold** text
.
<p>Expected rendered HTML</p>
.
````````````````````````````````


```````````````````````````````` example(Paragraphs: 3) options(IGNORES)
*Italic* text
.
<p>Expected rendered HTML</p>
.
````````````````````````````````


```````````````````````````````` example(Paragraphs: 4) options(IGNORES)
***Bold-Italic*** text
.
<p>Expected rendered HTML</p>
.
````````````````````````````````


```````````````````````````````` example(Paragraphs: 5) options(IGNORES)
~~strike-through~~ text
.
<p>Expected rendered HTML</p>
.
````````````````````````````````


```````````````````````````````` example(Paragraphs: 6) options(IGNORES)
~subscript~ text
.
<p>Expected rendered HTML</p>
.
````````````````````````````````


```````````````````````````````` example(Paragraphs: 7) options(IGNORES)
^superscript^ text
.
<p>Expected rendered HTML</p>
.
````````````````````````````````


```````````````````````````````` example(Paragraphs: 8) options(IGNORES)
++underline++ text
.
<p>Expected rendered HTML</p>
.
````````````````````````````````


## Lists

```````````````````````````````` example(Lists: 1) options(IGNORES)
* list 1

  with some text

  * list 2
  
    with some text

.
.
````````````````````````````````


## Links

Web URL

```````````````````````````````` example(Links: 1) options(IGNORES)
[flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 

with some text
.
.
````````````````````````````````


## Images

Web URL

```````````````````````````````` example(Images: 1) options(IGNORES)
![flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 

with some text
.
.
````````````````````````````````


Local File

```````````````````````````````` example(Images: 2) options(IGNORES)
![flexmark-icon-logo](file:///Users/vlad/src/flexmark-java/assets/images/flexmark-icon-logo@2x.png "Title: flexmark-java logo") 

with some text
.
.
````````````````````````````````


Relative Path

```````````````````````````````` example(Images: 3) options(url, IGNORES)
![flexmark-icon-logo](assets/images/flexmark-icon-logo@2x.png) 

with some text
.
.
````````````````````````````````


## Block Quotes

with hard break

```````````````````````````````` example(Block Quotes: 1) options(IGNORES)
> block quote 1  
> with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
.
.
````````````````````````````````


with child paragraphs

```````````````````````````````` example(Block Quotes: 2) options(IGNORES)
> block quote 1  
>
> with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
.
.
````````````````````````````````


with child block quote

```````````````````````````````` example(Block Quotes: 3) options(IGNORES)
> block quote 1  
>
>> another block quote
.
.
````````````````````````````````


with child paragraphs

```````````````````````````````` example(Block Quotes: 4) options(IGNORES)
> block quote 1  
>
> with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
>> another block quote
.
.
````````````````````````````````


## Mixed

```````````````````````````````` example(Mixed: 1) options(IGNORES)
# Heading 1 _italic_

Some Text

## Heading 2 **bold**

Some Text

### Heading 3 ~~strike-through~~

Some Text

#### Heading 4 `inline-code`

Some Text

##### Heading 5

Some Text

###### Heading 6

Some Text

------

Sample paragraph with **bold** *italic* ***bold-italic*** ++underline++ ~~strike-through~~
^superscript^ ~subscript~ `inline-code`

* list 1

  ![flexmark-icon-logo](file:///Users/vlad/src/flexmark-java/assets/images/flexmark-icon-logo@2x.png)

  with some text

  * list 2
  
    with some text

    * list 3
      * list 4
        * list 5
          * list 6
            * list 7
              * list 8

<!--  -->

1. list 1
   1. list 2
      1. list 3
         1. list 4
            1. list 5
               1. list 6
                  1. list 7
                     1. list 8
1. list item 2
   1. list item 2
        
Some plain text        

> block quote 1  
> with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
> > block quote 2
> > > block quote 3
> > > > block quote 4
> > > > > block quote 5
> > > > > > block quote 6
> > > > > > > block quote 7
> > > > > > > > block quote 8

```
pre-formatted code
code
```


| Combined header    ||
| Header 1 | Header 2 |
|----------|----------|
| Data 1   | Data 2   |
| Combined data      ||

.
.
````````````````````````````````


## Tables

```````````````````````````````` example Tables: 1
| Combined header    ||
| Header 1 | Header 2 |
|----------|----------|
| Data 1   | Data 2   |
| Combined data      ||
.
.
````````````````````````````````


