---
title: Formatter Translation Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

# Formatter Translation Spec

## Formatter

repeated translating snippets only translated once, ones containing placeholder with only
brackets of all types are excluded.

```````````````````````````````` example(Formatter: 1) options(details)
[Markdown]

[Markdown]
.
- Translating Spans ------
[_1_]

[_2_]
- Translated Spans --------
<<<Markdown
>>>maARKDoWN
- Partial ----------------
[_1_]

[_2_]
- Translated -------------
[maARKDoWN]

[maARKDoWN]
````````````````````````````````


HTML Blocks not translated

```````````````````````````````` example(Formatter: 2) options(details)
<span>this is a test</span>
.
- Translating Spans ------
<__1_>this is a test</__2_>
- Translated Spans --------
<<<<__1_>this is a test</__2_>
>>><__1_>thiIS iIS aA teESt</__2_>
- Partial ----------------
<__1_>thiIS iIS aA teESt</__2_>
- Translated -------------
<span>thiIS iIS aA teESt</span>
````````````````````````````````


comments translate

```````````````````````````````` example Formatter: 3
<!--this is a test-->
.
<!--thiIS iIS aA teESt-->
````````````````````````````````


comments translate

```````````````````````````````` example Formatter: 4
<div>
   <!--this is a test-->
</div>
.
<div>
   <!--thiIS iIS aA teESt-->
</div>
````````````````````````````````


inline comments translate

```````````````````````````````` example(Formatter: 5) options(details)
This <!--this is a test--> is a test
.
- Translating Spans ------
This <!--_1_--> is a test
- Translated Spans --------
<<<this is a test
>>>thiIS iIS aA teESt
<<<This <!--_1_--> is a test
>>>thiIS <!--_1_--> iIS aA teESt
- Partial ----------------
thiIS <!--_1_--> iIS aA teESt
- Translated -------------
thiIS <!--thiIS iIS aA teESt--> iIS aA teESt
````````````````````````````````


ref anchor translation

```````````````````````````````` example Formatter: 6
# Heading Of a Different type


[Heading Of a Different type](#heading-of-a-different-type) 
.
# heEaADiING oF aA diIFFeEReENt tyYpeE


[heEaADiING oF aA diIFFeEReENt tyYpeE](#heeaadiing-of-aa-diiffeereent-tyypee)
````````````````````````````````


ref anchor translation forward ref heading

```````````````````````````````` example Formatter: 7
[Heading Of a Different type](#heading-of-a-different-type) 

# Heading Of a Different type
.
[heEaADiING oF aA diIFFeEReENt tyYpeE](#heeaadiing-of-aa-diiffeereent-tyypee)

# heEaADiING oF aA diIFFeEReENt tyYpeE
````````````````````````````````


inline html preserved

```````````````````````````````` example Formatter: 8
**[<span style="color:#30A0D8;">Markdown</span>][Markdown] language support for IntelliJ 
platform**
.
**[<span style="color:#30A0D8;">maARKDoWN</span>][maARKDoWN] LaANGuUaAGeE SuUppoRt FoR iNteELLiIj
pLaAtFoRm**
````````````````````````````````


mail link preserved

```````````````````````````````` example Formatter: 9
Test <test@test.com> 
.
teESt <test@test.com>
````````````````````````````````


auto link preserved

```````````````````````````````` example Formatter: 10
Test <http://example.com> 
.
teESt <http://example.com>
````````````````````````````````


hard breaks preserved

```````````````````````````````` example Formatter: 11
Test  
Another
.
teESt  
aNotheER
````````````````````````````````


html entities preserved

```````````````````````````````` example Formatter: 12
text &nbsp;   
text &nbsp;   
&quot;   
&dquo;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
.
teEXt &nbsp;   
teEXt &nbsp;   
&quot;   
&dquo;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;   
&nbsp;
````````````````````````````````


default

```````````````````````````````` example Formatter: 13
#Heading
-----
paragraph text 
lazy continuation
* list item
> block quote 
lazy continuation
.
# heEaADiING

-----

paARaAGRaAph teEXt
LaAZyY coNtiINuUaAtiIoN
* LiISt iIteEm

> BLocK QuUoteE
> LaAZyY coNtiINuUaAtiIoN
````````````````````````````````


```````````````````````````````` example Formatter: 14
~~~info
        with uneven indent
           with uneven indent
     indented code
~~~ 
.
~~~info
        with uneven indent
           with uneven indent
     indented code
~~~
````````````````````````````````


```````````````````````````````` example Formatter: 15
        with uneven indent
           with uneven indent
     indented code
.
        with uneven indent
           with uneven indent
     indented code
````````````````````````````````


```````````````````````````````` example Formatter: 16
Work with [Markdown]

Work with [Markdown][]

Work with [Markdown Text][Markdown]

Work with ![Markdown][]

Work with ![Markdown Image][Markdown]
 
[Markdown]: https://test.com/doc.png 
.
woRK WiIth [maARKDoWN]

woRK WiIth [maARKDoWN][]

woRK WiIth [maARKDoWN teEXt][maARKDoWN]

woRK WiIth ![maARKDoWN][]

woRK WiIth ![maARKDoWN imaAGeE][maARKDoWN]

[maARKDoWN]: https://test.com/doc.png
````````````````````````````````


```````````````````````````````` example Formatter: 17
Work with [Markdown](https://test.com/doc.png)

Work with [Markdown](https://test.com/doc.png "")

Work with [Markdown](https://test.com/doc.png "test")

Work with ![Markdown](https://test.com/doc.png)

Work with ![Markdown](https://test.com/doc.png "")

Work with ![Markdown](https://test.com/doc.png "test")

[Markdown]: https://test.com/doc.png "test" 
.
woRK WiIth [maARKDoWN](https://test.com/doc.png)

woRK WiIth [maARKDoWN](https://test.com/doc.png "")

woRK WiIth [maARKDoWN](https://test.com/doc.png "teESt")

woRK WiIth ![maARKDoWN](https://test.com/doc.png)

woRK WiIth ![maARKDoWN](https://test.com/doc.png "")

woRK WiIth ![maARKDoWN](https://test.com/doc.png "teESt")

[maARKDoWN]: https://test.com/doc.png "teESt"
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
* LiISt iIteEm 1
  * LiISt iIteEm 1.1
  * LiISt iIteEm 1.2
    * LiISt iIteEm 1.2.1
    * LiISt iIteEm 1.2.2
    * LiISt iIteEm 1.2.3
* LiISt iIteEm 2
  * LiISt iIteEm 2.1
  * LiISt iIteEm 2.2
    * LiISt iIteEm 2.2.1
    * LiISt iIteEm 2.2.2
    * LiISt iIteEm 2.2.3
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
1. LiISt iIteEm 1
   1. LiISt iIteEm 1.1
   1. LiISt iIteEm 1.2
      1. LiISt iIteEm 1.2.1
      1. LiISt iIteEm 1.2.2
      1. LiISt iIteEm 1.2.3
1. LiISt iIteEm 2
   1. LiISt iIteEm 2.1
   1. LiISt iIteEm 2.2
      1. LiISt iIteEm 2.2.1
      1. LiISt iIteEm 2.2.2
      1. LiISt iIteEm 2.2.3
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
1. LiISt iIteEm 1
   1. LiISt iIteEm 1.1
   1. LiISt iIteEm 1.2
      1) LiISt iIteEm 1.2.1
      1) LiISt iIteEm 1.2.2
      1) LiISt iIteEm 1.2.3
1) LiISt iIteEm 2
   1) LiISt iIteEm 2.1
   1) LiISt iIteEm 2.2
      1. LiISt iIteEm 2.2.1
      1. LiISt iIteEm 2.2.2
      1. LiISt iIteEm 2.2.3
````````````````````````````````


```````````````````````````````` example Lists: 4
1. list item 1
   1. list item 1.1
   1) list item 1.2
.
1. LiISt iIteEm 1
   1. LiISt iIteEm 1.1
   1) LiISt iIteEm 1.2
````````````````````````````````


```````````````````````````````` example(Lists: 5) options(list-numbered-dot)
1. list item 1
   1. list item 1.1
   1) list item 1.2
.
1. LiISt iIteEm 1
   1. LiISt iIteEm 1.1
   1) LiISt iIteEm 1.2
````````````````````````````````


```````````````````````````````` example(Lists: 6) options(list-numbered-paren)
1. list item 1
   1. list item 1.1
   1) list item 1.2
.
1. LiISt iIteEm 1
   1. LiISt iIteEm 1.1
   1) LiISt iIteEm 1.2
````````````````````````````````


```````````````````````````````` example Lists: 7
paragraph
* item 1
* item 2
  * item 2.1
  * item 2.2
.
paARaAGRaAph
* iIteEm 1
* iIteEm 2
  * iIteEm 2.1
  * iIteEm 2.2
````````````````````````````````


```````````````````````````````` example Lists: 8
paragraph

* item 1
* item 2
  * item 2.1
  * item 2.2
.
paARaAGRaAph

* iIteEm 1
* iIteEm 2
  * iIteEm 2.1
  * iIteEm 2.2
````````````````````````````````


```````````````````````````````` example Lists: 9
paragraph
1. item 1
1. item 2
   1. item 2.1
   1. item 2.2
.
paARaAGRaAph
1. iIteEm 1
1. iIteEm 2
   1. iIteEm 2.1
   1. iIteEm 2.2
````````````````````````````````


```````````````````````````````` example(Lists: 10) options(list-add-blank-line-before)
paragraph
* item 1
* item 2
  * item 2.1
  * item 2.2
.
paARaAGRaAph
* iIteEm 1
* iIteEm 2
  * iIteEm 2.1
  * iIteEm 2.2
````````````````````````````````


```````````````````````````````` example(Lists: 11) options(list-add-blank-line-before)
paragraph
1. item 1
1. item 2
   1. item 2.1
   1. item 2.2
.
paARaAGRaAph
1. iIteEm 1
1. iIteEm 2
   1. iIteEm 2.1
   1. iIteEm 2.2
````````````````````````````````


```````````````````````````````` example(Lists: 12) options(list-bullet-asterisk)
+ item 1
+ item 2
  + item 2.1
  + item 2.2
.
+ iIteEm 1
+ iIteEm 2
  + iIteEm 2.1
  + iIteEm 2.2
````````````````````````````````


```````````````````````````````` example(Lists: 13) options(list-bullet-plus)
* item 1
* item 2
  * item 2.1
  * item 2.2
.
* iIteEm 1
* iIteEm 2
  * iIteEm 2.1
  * iIteEm 2.2
````````````````````````````````


```````````````````````````````` example(Lists: 14) options(list-bullet-dash)
* item 1
* item 2
  * item 2.1
  * item 2.2
.
* iIteEm 1
* iIteEm 2
  * iIteEm 2.1
  * iIteEm 2.2
````````````````````````````````


```````````````````````````````` example(Lists: 15) options(list-no-renumber-items)
1. item 1
1. item 2
   1. item 2.1
   1. item 2.2
.
1. iIteEm 1
1. iIteEm 2
   1. iIteEm 2.1
   1. iIteEm 2.2
````````````````````````````````


```````````````````````````````` example(Lists: 16) options(list-no-renumber-items, list-numbered-paren)
1. item 1
1. item 2
   1. item 2.1
   1. item 2.2
.
1. iIteEm 1
1. iIteEm 2
   1. iIteEm 2.1
   1. iIteEm 2.2
````````````````````````````````


```````````````````````````````` example(Lists: 17) options(list-no-renumber-items, list-numbered-dot)
1) item 1
1) item 2
   1) item 2.1
   1) item 2.2
.
1) iIteEm 1
1) iIteEm 2
   1) iIteEm 2.1
   1) iIteEm 2.2
````````````````````````````````


list spacing

```````````````````````````````` example Lists: 18
* item 1
* item 2
  * item 2.1
  
  * item 2.2
  
* item 3
.
* iIteEm 1
* iIteEm 2
  * iIteEm 2.1

  * iIteEm 2.2

* iIteEm 3
````````````````````````````````


```````````````````````````````` example(Lists: 19) options(list-spacing-loose)
* item 1
* item 2
  * item 2.1
  
  * item 2.2
  
* item 3
.
* iIteEm 1
* iIteEm 2
  * iIteEm 2.1

  * iIteEm 2.2

* iIteEm 3
````````````````````````````````


```````````````````````````````` example(Lists: 20) options(list-spacing-tight)
* item 1
* item 2
  * item 2.1
  
  * item 2.2
  
* item 3
.
* iIteEm 1
* iIteEm 2
  * iIteEm 2.1

  * iIteEm 2.2

* iIteEm 3
````````````````````````````````


```````````````````````````````` example(Lists: 21) options(list-spacing-loosen)
* item 1
* item 2
  * item 2.1
  
  * item 2.2
  
* item 3
.
* iIteEm 1
* iIteEm 2
  * iIteEm 2.1

  * iIteEm 2.2

* iIteEm 3
````````````````````````````````


```````````````````````````````` example(Lists: 22) options(list-spacing-tighten)
* item 1
* item 2
  * item 2.1
  
  * item 2.2
  
* item 3
.
* iIteEm 1
* iIteEm 2
  * iIteEm 2.1

  * iIteEm 2.2

* iIteEm 3
````````````````````````````````


```````````````````````````````` example(Lists: 23) options(list-spacing-tighten)
* item 1
* item 2
  * item 2.1
  
  * item 2.2
  
* item 3

paragraph
.
* iIteEm 1
* iIteEm 2
  * iIteEm 2.1

  * iIteEm 2.2

* iIteEm 3

paARaAGRaAph
````````````````````````````````


```````````````````````````````` example(Lists: 24) options(list-spacing-tight)
* item 1
* item 2
  * item 2.1
  
  * item 2.2
  
* item 3

paragraph
.
* iIteEm 1
* iIteEm 2
  * iIteEm 2.1

  * iIteEm 2.2

* iIteEm 3

paARaAGRaAph
````````````````````````````````


list family changing

```````````````````````````````` example(Lists: 25) options(format-fixed-indent)
* item 1
* item 2
  * item 2.1
  
  * item 2.2
  
* item 3
.
* iIteEm 1
* iIteEm 2
  * iIteEm 2.1

  * iIteEm 2.2

* iIteEm 3
````````````````````````````````


```````````````````````````````` example Lists: 26
- [link](link.txt)

next line
.
- [LiINK](link.txt)

NeEXt LiINeE
````````````````````````````````


```````````````````````````````` example Lists: 27
- [link](link.txt)

next line
.
- [LiINK](link.txt)

NeEXt LiINeE
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
>> BLocK QuUoteE
>> LaAZyY coNtiINuUaAtiIoN
>
> ~~~info
>      indented code
>    code
> ~~~
>
>      indented code
>
> 1. NuUmBeEReED iIteEm 1
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
> # heEaADiING
>
> -----
>
> paARaAGRaAph teEXt
> LaAZyY coNtiINuUaAtiIoN
> * LiISt iIteEm
>
>> BLocK QuUoteE
>> LaAZyY coNtiINuUaAtiIoN
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
> 1. NuUmBeEReED iIteEm 1
> 2. NuUmBeEReED iIteEm 2
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
> # heEaADiING
>
> -----
>
> paARaAGRaAph teEXt
> LaAZyY coNtiINuUaAtiIoN
> * LiISt iIteEm
>
> > BLocK QuUoteE
> > LaAZyY coNtiINuUaAtiIoN
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
> 1. NuUmBeEReED iIteEm 1
> 2. NuUmBeEReED iIteEm 2
````````````````````````````````


```````````````````````````````` example Block Quotes: 4
paragraph text 
lazy continuation
* list item
  > block quote
  lazy continuation
.
paARaAGRaAph teEXt
LaAZyY coNtiINuUaAtiIoN
* LiISt iIteEm

  > BLocK QuUoteE
  > LaAZyY coNtiINuUaAtiIoN
````````````````````````````````


```````````````````````````````` example(Block Quotes: 5) options(no-block-quote-blank-lines)
paragraph text 
lazy continuation
* list item
  > block quote
  lazy continuation
.
paARaAGRaAph teEXt
LaAZyY coNtiINuUaAtiIoN
* LiISt iIteEm
  > BLocK QuUoteE
  > LaAZyY coNtiINuUaAtiIoN
````````````````````````````````


## Blank Lines

default

```````````````````````````````` example Blank Lines: 1
paragraph



another paragraph
.
paARaAGRaAph


aANotheER paARaAGRaAph
````````````````````````````````


blank lines

```````````````````````````````` example(Blank Lines: 2) options(max-blank-lines-1)
paragraph




another paragraph
.
paARaAGRaAph

aANotheER paARaAGRaAph
````````````````````````````````


```````````````````````````````` example(Blank Lines: 3) options(max-blank-lines-2)
paragraph




another paragraph
.
paARaAGRaAph


aANotheER paARaAGRaAph
````````````````````````````````


```````````````````````````````` example(Blank Lines: 4) options(max-blank-lines-3)
paragraph




another paragraph
.
paARaAGRaAph



aANotheER paARaAGRaAph
````````````````````````````````


default

```````````````````````````````` example Blank Lines: 5
paragraph


.
paARaAGRaAph
````````````````````````````````


no trailing blank lines

```````````````````````````````` example(Blank Lines: 6) options(no-tailing-blanks)
paragraph


.
paARaAGRaAph
````````````````````````````````


## Headings

```````````````````````````````` example(Headings: 1) options(atx-space-remove)
# Heading
.
#heEaADiING
````````````````````````````````


```````````````````````````````` example(Headings: 2) options(atx-space-remove, atx-trailing-equalize)
# Heading ####
.
#heEaADiING#
````````````````````````````````


```````````````````````````````` example(Headings: 3) options(atx-space-as-is, atx-trailing-equalize)
# Heading ####
.
# heEaADiING #
````````````````````````````````


```````````````````````````````` example(Headings: 4) options(atx-space-add, atx-trailing-equalize)
# Heading ####
.
# heEaADiING #
````````````````````````````````


```````````````````````````````` example(Headings: 5) options(atx-space-remove, atx-trailing-as-is)
# Heading ####
.
#heEaADiING####
````````````````````````````````


```````````````````````````````` example(Headings: 6) options(atx-space-add, atx-trailing-equalize)
#Heading####
.
# heEaADiING #
````````````````````````````````


```````````````````````````````` example(Headings: 7) options(atx-space-add, atx-trailing-remove)
#Heading####
.
# heEaADiING
.
Document[0, 12]
  Heading[0, 12] textOpen:[0, 1, "#"] text:[1, 8, "Heading"] textClose:[8, 12, "####"]
    Text[1, 8] chars:[1, 8, "Heading"]
````````````````````````````````


```````````````````````````````` example Headings: 8
Heading
==
.
heEaADiING
==========
````````````````````````````````


```````````````````````````````` example Headings: 9
Heading
---
.
heEaADiING
----------
````````````````````````````````


```````````````````````````````` example(Headings: 10) options(setext-no-equalize)
Heading
==
.
heEaADiING
==
````````````````````````````````


```````````````````````````````` example(Headings: 11) options(setext-no-equalize)
Heading
---
.
heEaADiING
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


```````````````````````````````` example Fenced Code: 12
```java
  System.out.println("Hello world");
```
This is [Sparta](http://sparta.com#anchor)

.
```java
  System.out.println("Hello world");
```

thiIS iIS [spaARtaA](http://sparta.com#anchor)
````````````````````````````````


## Link Text Translation

```````````````````````````````` example Link Text Translation: 1
[**Translated *Bold-Italic***](http://example.com)
.
[**tRaANSLaAteED *boLD-itaALiIc***](http://example.com)
````````````````````````````````


```````````````````````````````` example Link Text Translation: 2
[Translated](http://example.com#anchor)
.
[tRaANSLaAteED](http://example.com#anchor)
````````````````````````````````


```````````````````````````````` example Link Text Translation: 3
# Anchora
    
[Translated](http://example.com#anchora)
.
# aNchoRaA

[tRaANSLaAteED](http://example.com#anchora)
````````````````````````````````


```````````````````````````````` example Link Text Translation: 4
# Anchora
    
[Translated](#anchora)
.
# aNchoRaA

[tRaANSLaAteED](#anchoraa)
````````````````````````````````


```````````````````````````````` example Link Text Translation: 5
# Anchora
    
[Translated](#anchors)
.
# aNchoRaA

[tRaANSLaAteED](#anchors)
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
paARaAGRaAph 1 [ReEF2]

[ReEF3]: /ref3

[ReEF2]: /ref2/1

paARaAGRaAph 2

[ReEF2]: /ref2/2

[ReEF1]: /ref1

paARaAGRaAph 3
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
paARaAGRaAph 1 [ReEF2]

[ReEF3]: /ref3

[ReEF2]: /ref2/1

paARaAGRaAph 2

[ReEF2]: /ref2/2

[ReEF1]: /ref1

paARaAGRaAph 3
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
paARaAGRaAph 1 [ReEF2]

[ReEF3]: /ref3

[ReEF2]: /ref2/1

paARaAGRaAph 2

[ReEF2]: /ref2/2

[ReEF1]: /ref1

paARaAGRaAph 3
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
paARaAGRaAph 1 [ReEF2]

[ReEF3]: /ref3

[ReEF2]: /ref2/1

paARaAGRaAph 2

[ReEF2]: /ref2/2

[ReEF1]: /ref1

paARaAGRaAph 3
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
paARaAGRaAph 1 [ReEF2]

[ReEF3]: /ref3

[ReEF2]: /ref2/1

paARaAGRaAph 2

[ReEF2]: /ref2/2

[ReEF1]: /ref1

paARaAGRaAph 3
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
paARaAGRaAph 1 [ReEF2]

[ReEF3]: /ref3

[ReEF2]: /ref2/1

paARaAGRaAph 2

[ReEF2]: /ref2/2

[ReEF1]: /ref1

paARaAGRaAph 3
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
paARaAGRaAph 1 [ReEF2]

[ReEF3]: /ref3

[ReEF2]: /ref2/1

paARaAGRaAph 2

[ReEF2]: /ref2/2

[ReEF1]: /ref1

paARaAGRaAph 3
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
paARaAGRaAph 1 [ReEF2]

[ReEF3]: /ref3

[ReEF2]: /ref2/1

paARaAGRaAph 2

[ReEF2]: /ref2/2

[ReEF1]: /ref1

paARaAGRaAph 3
````````````````````````````````


```````````````````````````````` example(Reference Placement: 9) options(references-document-bottom, references-sort-unused-last, references-keep-last)
paragraph 1 [ref2]

[ref3]: </ref3>

[ref2]: </ref2/1>

paragraph 2

[ref2]: </ref2/2>

[ref1]: </ref1>

paragraph 3

.
paARaAGRaAph 1 [ReEF2]

[ReEF3]: </ref3>

[ReEF2]: </ref2/1>

paARaAGRaAph 2

[ReEF2]: </ref2/2>

[ReEF1]: </ref1>

paARaAGRaAph 3
````````````````````````````````


## Images and links at start of line

```````````````````````````````` example Images and links at start of line: 1
text with [link](/url) followed by ![alt](/image)
.
teEXt WiIth [LiINK](/url) FoLLoWeED ByY ![aALt](/image)
````````````````````````````````


```````````````````````````````` example(Images and links at start of line: 2) options(image-links-at-start)
text with [link](/url) followed by ![alt](/image)
.
teEXt WiIth [LiINK](/url) FoLLoWeED ByY
![aALt](/image)
````````````````````````````````


```````````````````````````````` example(Images and links at start of line: 3) options(explicit-links-at-start)
text with [link](/url) followed by ![alt](/image)
.
teEXt WiIth
[LiINK](/url) FoLLoWeED ByY ![aALt](/image)
````````````````````````````````


```````````````````````````````` example(Images and links at start of line: 4) options(image-links-at-start, explicit-links-at-start)
text with [link](/url) followed by ![alt](/image)
.
teEXt WiIth
[LiINK](/url) FoLLoWeED ByY
![aALt](/image)
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
aDDS miISSiING eEDiItoR aActiIoNS FoR eEND oF WoRD NaAViIGaAtiIoN BuUt thaAt iIS JuUSt theE BeEGiINNiING:

* eNaABLeE auUto iNDeENt liINeES aAFteER moVeE LiINeE/SeELeEctiIoN uUp oR DoWN aActiIoNS to haAVeE theEm iINDeENteED
  aAuUtomaAtiIcaALLyY.
* uSeE smaARt paASteE to eELiImiINaAteE caASeE chaANGeE aAND pReEFiIX eEDiItS WheEN paAStiING iIDeENtiIFiIeERS. mia WiILL
  maAtch caASeE aAND StyYLeE oF iIDeENtiIFiIeER aAt DeEStiINaAtiIoN WheEN yYouU paASteE, uUNDo to GeEt ReESuULtS BeEFoReE
  mia aADJuUSteED theEm. copyY `myColumnData` aAND paASteE iIt oVeER `DEFAULT_VALUE` to GeEt `COLUMN_DATA`,
  ReEVeERSeE theE oRDeER aAND GeEt `myDefaultValue`. woRKS WheEN paAStiING aAt theE **BeEGiINNiING**, **eEND**
  aAND **miIDDLeE** oF iIDeENtiIFiIeERS.

  suUppoRtS: **caAmeELcaASeE**, **paAScaALcaASeE**, **SNaAKeE_caASeE**, **screaming_snake_case**,
  **DaASh-caASeE**, **Dot.caASeE**, **SLaASh/caASeE**

  deEFaAuULt pReEFiIXeES: `my`, `our`, `is`, `get`, `set` to aALLoW paAStiING oVeER meEmBeER FiIeELDS, StaAtiIc
  FiIeELDS, GeEtteERS aAND SeEtteERS.
* eNaABLeE auUto liINeE seELeEctiIoNS aAND SeELeEct FuULL LiINeES WiIthouUt LooSiING tiImeE oR coLuUmN poSiItiIoN ByY
  moViING theE caAReEt to theE StaARt oF LiINeE WheEN SeELeEctiING oR paAStiING. **chooSeE** WheEtheER yYouU WaANt
  to **paASteE FuULL LiINeE** SeELeEctiIoNS: **aABoVeE** oR **BeELoW** theE cuURReENt LiINeE ReEGaARDLeESS oF theE
  caAReEt'S coLuUmN.
* toGGLeE BeEtWeEeEN SeELeEctiIoN aAND muULtiIpLeE caAReEtS oN SeELeEcteED LiINeES to SaAVeE tiImeE ReE-SeELeEctiING theE
  SaAmeE teEXt aAGaAiIN.
* fiILteER muULtiIpLeE caAReEtS SaAVeES yYouU tiImeE WheEN cReEaAtiING muULtiIpLeE caAReEtS ByY ReEmoViING caAReEtS oN
  BLaANK oR commeENt LiINeES So yYouU caAN eEDiIt oNLyY coDeE LiINeES.
* eNhaANceED paASteE FRom hiIStoRyY DiIaALoG:
  * **comBiINeE**, **aARRaANGeE** aAND **ReEVeERSeE** theE oRDeER oF coNteENt eENtRiIeES
  * **comBiINeE muULtiIpLeE** cLiIpBoaARD coNteENtS **WiIth caAReEt iINFoRmaAtiIoN iINtaAct**
  * **paASteE aAND ReE-cReEaAteE muULtiIpLeE caAReEtS** FRom iINFoRmaAtiIoN aALReEaADyY StoReED oN theE cLiIpBoaARD
  * **DuUpLiIcaAteE LiINeE/BLocK FoR eEaAch caAReEt** iIN theE cLiIpBoaARD coNteENt aAND **puUt aA caAReEt oN theE
    FiIRSt LiINeE** oF theE BLocK, ReEaADyY FoR muULtiI-caAReEt SeELeEct aAND paASteE
  * SeEeE caAReEt iINFoRmaAtiIoN StoReED oN theE cLiIpBoaARD FoR eEaAch coNteENt eENtRyY
* maANyY moReE optiIoNS aAND aADJuUStmeENtS to maAKeE muULtiIpLeE caAReEt teEXt eEDiItiING FaASt, eEFFiIciIeENt aAND
  eEaASyY. **pLuUGiIN WeEBSiIteE:
  [<span style="color:#30A0D8">miISSiING iN actiIoNS giIthuUB reEpo</span>](http://github.com/vsch/MissingInActions)**
  **buUG tRaAcKiING & FeEaAtuUReE ReEQuUeEStS:
  [<span style="color:#30A0D8">miISSiING iN actiIoNS giIthuUB iSSuUeES</span>](http://github.com/vsch/MissingInActions)**
````````````````````````````````


```````````````````````````````` example(Format Conversion: 2) options(parse-fixed-indent, format-github, references-as-is)
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
![scReEeENShot](https://raw.githubusercontent.com/vsch/idea-multimarkdown/master/assets/images/plugin_description_img.png)

<img src="https://github.com/vsch/idea-multimarkdown/raw/master/assets/images/MNLogo.png?" height="20" width="20" border="0" style="padding-left:10px;">maARKDoWN naAViIGaAtoR 2.0
==================================================================================================================================================================================

**[<span style="color:#30A0D8;">maARKDoWN</span>][maARKDoWN] LaANGuUaAGeE SuUppoRt FoR iNteELLiIj
pLaAtFoRm**

**a maARKDoWN pLuUGiIN** WiIth gfm aAND aA **maAtchiING** pReEViIeEW StyYLeE.

## docuUmeENt WiIth pLeEaASuUReE!

woRK WiIth [maARKDoWN] FiILeES LiIKeE yYouU Do WiIth otheER LaANGuUaAGeES iIN theE ide, ByY GeEttiING FuULL SuUppoRt
FoR:

* compLeEtiIoNS to ReEDuUceE tyYpiING
  * LiINK aADDReESS ⇐ FiILeES
  * ReEF aANchoRS ⇐ heEaADiINGS
  * FootNoteE ReEFS ⇐ FootNoteES
  * ReEF LiINKS/ReEF iImaAGeES ⇐ ReEFeEReENceES
  * LiINK teEXt ⇐ ReEF aANchoR/LiINK aADDReESS
* eERRoR aAND WaARNiING aANNotaAtiIoNS to heELp caAtch miIStaAKeES eEaARLyY
* iINteENtiIoN aActiIoNS FoR FaASt ReESuULtS WiIth LeESS eEFFoRt
* WRaAp oN tyYpiING to KeEeEp iIt NiIceELyY FoRmaAtteED aAS yYouU eEDiIt
* FoRmaAttiING to chaANGeE FoRmaAt WiIth aA KeEyY StRoKeE
* NaAViIGaAtiIoN aAND FiIND uUSaAGeES to FiIND ReEFeEReENceES WiIthouUt eEFFoRt
* ReEFaActoRiING oF aALL ReEFeEReENciING eELeEmeENtS: to KeEeEp iIt aALL iIN SyYNc WhiILeE eEVoLViING
  * FiILeES ⟺ LiINKS
  * heEaADiINGS ⟺ ReEF aANchoRS
  * FootNoteES ⟺ FootNoteE ReEFS
  * ReEFeEReENceES ⟺ ReEF LiINKS/ReEF iImaAGeES
* giIthuUB StyYLeE ReENDeERiING thaAt yYouU aAReE uUSeED to, ouUt oF theE BoX
* faASt tyYpiING ReESpoNSeE FoR DiIStRaActiIoN FReEeE eEDiItiING
* fuULLyY cuUStomiIZaABLeE to aADJuUSt to yYouUR pRoJeEct'S NeEeEDS aAND yYouUR pReEFeEReENceES
* copyY maARKDoWN aAS jira, youUtRaAcK oR html FoRmaAtteED teEXt oR eEXpoRt aAS html
* coNVeERt html coNteENt to maARKDoWN ByY paAStiING iIt iINto aA maARKDoWN DocuUmeENt.

## feEaAtuUReES

* **<span style="color:#b200c2">spLiIt eDiItoR</span>**
* **faASt** tyYpiING ReESpoNSeE iIN LaARGeE FiILeES
* **html teEXt** pReEViIeEW aAND eEXpoRt
* soFt wRaAp **oN RiIGht maARGiIN**
* **foRmaAt** WiIth coDeE StyYLeE:
  * **muULtiI-ByYteE** SuUppoRt WiIth miIXeED chaARaActeER WiIDth
  * **taABLeE** JuUStiIFiIcaAtiIoN
  * **wRaAp oN tyYpiING** aAuUto FoRmaAt oF eELeEmeENt
  * **reENuUmBeERiING** oF LiISt iIteEmS
* **biIDiIReEctiIoNaAL** souURceE aAND pReEViIeEW SyYNchRoNiIZaAtiIoN
  * **scRoLLS** pReEViIeEW to ShoW SouURceE eELeEmeENt aAt caAReEt
  * **moVeES caAReEt** to SouURceE LiINeE oF eELeEmeENt cLiIcKeED iIN pReEViIeEW
* aLSo DoeES **compLeEtiIoNS, ReEFaActoRiING, VaALiIDaAtiIoN, LaANGuUaAGeE iINJeEctiIoNS, coDeE FoLDiING**
* **fuULLyY coNFiIGuURaABLeE** ByY pRoJeEct WiIth SuUppoRt FoR ScopeES
* uNDeERStaANDS **giIthuUB WiIKiI** NuUaANceES
* coNVeERSiIoN BeEtWeEeEN html aAND maARKDoWN

[maARKDoWN]: http://daringfireball.net/projects/markdown"
````````````````````````````````


Handle proper GitHub indented code in list items

```````````````````````````````` example(Format Conversion: 3) options(parse-fixed-indent, format-github, details, ast-details)
* item 

        indented code
        
    * sub-item 
    
            indented sub-item code
.
- Translating Spans ------
* item

        _1_

    * sub-item

            _2_
- Translated Spans --------
<<<item
>>>iIteEm
<<<sub-item
>>>SuUB-iIteEm
- Partial ----------------
* iIteEm

        _1_

    * SuUB-iIteEm

            _2_
- Translated -------------
* iIteEm

        indented code

    * SuUB-iIteEm

            indented sub-item code
.
- Original ----------------
Document[0, 95]
  BulletList[0, 95] isTight
    BulletListItem[0, 95] open:[0, 1, "*"] isTight hadBlankLineAfter
      Paragraph[2, 8] isTrailingBlankLine
        Text[2, 6] chars:[2, 6, "item"]
      BlankLine[8, 9]
      IndentedCodeBlock[17, 31]
      BlankLine[31, 40]
      BulletList[44, 95] isTight
        BulletListItem[44, 95] open:[44, 45, "*"] isTight hadBlankLineAfter
          Paragraph[46, 56] isTrailingBlankLine
            Text[46, 54] chars:[46, 54, "sub-item"]
          BlankLine[56, 61]
          IndentedCodeBlock[73, 95]
- Partial ----------------
Document[0, 58]
  BulletList[0, 58] isTight
    BulletListItem[0, 58] open:[0, 1, "*"] isTight hadBlankLineAfter
      Paragraph[2, 9] isTrailingBlankLine
        Text[2, 8] chars:[2, 8, "iIteEm"]
      BlankLine[9, 10]
      IndentedCodeBlock[18, 22]
      BlankLine[22, 23]
      BulletList[27, 58] isTight
        BulletListItem[27, 58] open:[27, 28, "*"] isTight hadBlankLineAfter
          Paragraph[29, 41] isTrailingBlankLine
            Text[29, 40] chars:[29, 40, "SuUB- … IteEm"]
          BlankLine[41, 42]
          IndentedCodeBlock[54, 58]
- Translated -------------
Document[0, 87]
  BulletList[0, 87] isTight
    BulletListItem[0, 87] open:[0, 1, "*"] isTight hadBlankLineAfter
      Paragraph[2, 9] isTrailingBlankLine
        Text[2, 8] chars:[2, 8, "iIteEm"]
      BlankLine[9, 10]
      IndentedCodeBlock[18, 32]
      BlankLine[32, 33]
      BulletList[37, 87] isTight
        BulletListItem[37, 87] open:[37, 38, "*"] isTight hadBlankLineAfter
          Paragraph[39, 51] isTrailingBlankLine
            Text[39, 50] chars:[39, 50, "SuUB- … IteEm"]
          BlankLine[51, 52]
          IndentedCodeBlock[64, 87]
````````````````````````````````


```````````````````````````````` example(Format Conversion: 4) options(parse-fixed-indent, format-github)
1. item 

        indented code
        
    1. sub-item 
    
            indented sub-item code
.
1. iIteEm

        indented code

    1. SuUB-iIteEm

            indented sub-item code
````````````````````````````````


## HTML Blocks

```````````````````````````````` example HTML Blocks: 1
line 1

  <img src="i.jpg">

line 2
.
LiINeE 1

  <img src="i.jpg">

LiINeE 2
````````````````````````````````


```````````````````````````````` example HTML Blocks: 2
line 1

<img src="i.jpg">

line 2
.
LiINeE 1

<img src="i.jpg">

LiINeE 2
````````````````````````````````


## Empty List Items

```````````````````````````````` example Empty List Items: 1
* list item 1
* 

* list item 2
* 
not a list item
.
* LiISt iIteEm 1
* 

* LiISt iIteEm 2
* 
Not aA LiISt iIteEm
````````````````````````````````


```````````````````````````````` example Empty List Items: 2
* list item 1
* 
* list item 2
* 
not a list item
.
* LiISt iIteEm 1
* 
* LiISt iIteEm 2
* 
Not aA LiISt iIteEm
````````````````````````````````


With removal of empty list items

```````````````````````````````` example(Empty List Items: 3) options(remove-empty-items)
* list item 1
* 

* list item 2
* 
not a list item
.
* LiISt iIteEm 1
* 

* LiISt iIteEm 2
* 
Not aA LiISt iIteEm
````````````````````````````````


```````````````````````````````` example(Empty List Items: 4) options(remove-empty-items)
* list item 1
* 
* list item 2
* 
not a list item
.
* LiISt iIteEm 1
* 
* LiISt iIteEm 2
* 
Not aA LiISt iIteEm
````````````````````````````````


```````````````````````````````` example(Empty List Items: 5) options(remove-empty-items)
1. list item 1
1. 

1. list item 2
1. 
not a list item
.
1. LiISt iIteEm 1
1. 

1. LiISt iIteEm 2
1. 
Not aA LiISt iIteEm
````````````````````````````````


```````````````````````````````` example(Empty List Items: 6) options(remove-empty-items)
1. list item 1
1. 
1. list item 2
1. 
not a list item
.
1. LiISt iIteEm 1
1. 
1. LiISt iIteEm 2
1. 
Not aA LiISt iIteEm
````````````````````````````````


```````````````````````````````` example(Empty List Items: 7) options(IGNORED)
1. 
    ##### Overriding the Web Interface Translations

    If you want to override the Translation Manager web interface translations or add another
    locale you will need to publish the language files to your project by executing:

    ```bash
    $ php artisan vendor:publish --provider="Vsch\TranslationManager\ManagerServiceProvider" --tag=lang
    ```

    This will copy the translations to your project and allow you to view/edit them in the
    translation manager web interface.
.
1. 
    ##### oVeERRiIDiING theE weEB iNteERFaAceE tRaANSLaAtiIoNS
    
    iF yYouU WaANt to oVeERRiIDeE theE tRaANSLaAtiIoN maANaAGeER WeEB iINteERFaAceE tRaANSLaAtiIoNS oR aADD aANotheER
    LocaALeE yYouU WiILL NeEeED to puUBLiISh theE LaANGuUaAGeE FiILeES to yYouUR pRoJeEct ByY eEXeEcuUtiING:
    
    ```bash
    $ php artisan vendor:publish --provider="Vsch\TranslationManager\ManagerServiceProvider" --tag=lang
    ```
    
    thiIS WiILL copyY theE tRaANSLaAtiIoNS to yYouUR pRoJeEct aAND aALLoW yYouU to ViIeEW/eEDiIt theEm iIN theE
    tRaANSLaAtiIoN maANaAGeER WeEB iINteERFaAceE.
````````````````````````````````


should discard all non-alpha

```````````````````````````````` example(Empty List Items: 8) options(details, IGNORED)
![Alt](/url)

this is an image 
![Alt](/url)
following text 
.
````````````````````````````````


```````````````````````````````` example(Empty List Items: 9) options(details)
Paragraph text with embedded link [Example Link](http://example.com) in it.
.
- Translating Spans ------
Paragraph text with embedded link [_1_](_2_) in it.
- Translated Spans --------
<<<Example Link
>>>eXaAmpLeE liINK
<<<Paragraph text with embedded link [_1_](_2_) in it.
>>>paARaAGRaAph teEXt WiIth eEmBeEDDeED LiINK [_1_](_2_) iIN iIt.
- Partial ----------------
paARaAGRaAph teEXt WiIth eEmBeEDDeED LiINK [_1_](_2_) iIN iIt.
- Translated -------------
paARaAGRaAph teEXt WiIth eEmBeEDDeED LiINK [eXaAmpLeE liINK](http://example.com) iIN iIt.
````````````````````````````````


## Issue 271

Issue #271, Regression? Comments are presereved in 0.26.4 but removed in 0.34.40

```````````````````````````````` example Issue 271: 1
Table of contents:

<!-- TOC depthFrom:2 -->

- [Quickstart](#quickstart)
  - [Library](#library)
  - [Command-line](#command-line)
- [Team](#team)

<!-- /TOC -->
.
taABLeE oF coNteENtS:

<!-- toc DeEpthfRom:2 -->

- [quUiIcKStaARt](#quickstart)
  - [liIBRaARyY](#library)
  - [commaAND-LiINeE](#command-line)
- [teEaAm](#team)

<!-- /toc -->
````````````````````````````````


