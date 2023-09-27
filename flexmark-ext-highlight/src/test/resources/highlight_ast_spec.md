---
title: Highlight Extension Spec
author: Marcos Pereira
version: 0.1
date: '2023-09-27'
license: '[CC-BY-SA 4.0](https://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Highlight

Converts ==highlight== text to Highlight nodes.

```````````````````````````````` example Highlight: 1
=foo=                                                             
.
<p>=foo=</p>
.
Document[0, 66]
  Paragraph[0, 66]
    Text[0, 5] chars:[0, 5, "=foo="]
````````````````````````````````


```````````````````````````````` example Highlight: 2
==foo==                                                           
.
<p><mark>foo</mark></p>
.
Document[0, 66]
  Paragraph[0, 66]
    Highlight[0, 7] textOpen:[0, 2, "=="] text:[2, 5, "foo"] textClose:[5, 7, "=="]
      Text[2, 5] chars:[2, 5, "foo"]
````````````````````````````````


```````````````````````````````` example Highlight: 3
foo ====                                                          
.
<p>foo ====</p>
.
Document[0, 66]
  Paragraph[0, 66]
    Text[0, 8] chars:[0, 8, "foo ===="]
````````````````````````````````


```````````````````````````````` example Highlight: 4
==foo                                                             
.
<p>==foo</p>
.
Document[0, 66]
  Paragraph[0, 66]
    Text[0, 5] chars:[0, 5, "==foo"]
````````````````````````````````


```````````````````````````````` example Highlight: 5
foo==                                                             
.
<p>foo==</p>
.
Document[0, 66]
  Paragraph[0, 66]
    Text[0, 5] chars:[0, 5, "foo=="]
````````````````````````````````


```````````````````````````````` example Highlight: 6
===foo===                                                         
.
<p>=<mark>foo</mark>=</p>
.
Document[0, 66]
  Paragraph[0, 66]
    Text[0, 1] chars:[0, 1, "="]
    Highlight[1, 8] textOpen:[1, 3, "=="] text:[3, 6, "foo"] textClose:[6, 8, "=="]
      Text[3, 6] chars:[3, 6, "foo"]
    Text[8, 9] chars:[8, 9, "="]
````````````````````````````````


```````````````````````````````` example Highlight: 7
==foo===                                                          
.
<p><mark>foo</mark>=</p>
.
Document[0, 66]
  Paragraph[0, 66]
    Highlight[0, 7] textOpen:[0, 2, "=="] text:[2, 5, "foo"] textClose:[5, 7, "=="]
      Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 8] chars:[7, 8, "="]
````````````````````````````````


```````````````````````````````` example Highlight: 8
==foo=bar==                                                       
.
<p><mark>foo=bar</mark></p>
.
Document[0, 66]
  Paragraph[0, 66]
    Highlight[0, 11] textOpen:[0, 2, "=="] text:[2, 9, "foo=bar"] textClose:[9, 11, "=="]
      Text[2, 9] chars:[2, 9, "foo=bar"]
````````````````````````````````


```````````````````````````````` example Highlight: 9
==foo==bar==                                                      
.
<p><mark>foo</mark>bar==</p>
.
Document[0, 66]
  Paragraph[0, 66]
    Highlight[0, 7] textOpen:[0, 2, "=="] text:[2, 5, "foo"] textClose:[5, 7, "=="]
      Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 12] chars:[7, 12, "bar=="]
````````````````````````````````


```````````````````````````````` example Highlight: 10
==foo===bar==                                                     
.
<p><mark>foo</mark>=bar==</p>
.
Document[0, 66]
  Paragraph[0, 66]
    Highlight[0, 7] textOpen:[0, 2, "=="] text:[2, 5, "foo"] textClose:[5, 7, "=="]
      Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 13] chars:[7, 13, "=bar=="]
````````````````````````````````


```````````````````````````````` example Highlight: 11
==foo====bar==                                                    
.
<p><mark>foo</mark><mark>bar</mark></p>
.
Document[0, 66]
  Paragraph[0, 66]
    Highlight[0, 7] textOpen:[0, 2, "=="] text:[2, 5, "foo"] textClose:[5, 7, "=="]
      Text[2, 5] chars:[2, 5, "foo"]
    Highlight[7, 14] textOpen:[7, 9, "=="] text:[9, 12, "bar"] textClose:[12, 14, "=="]
      Text[9, 12] chars:[9, 12, "bar"]
````````````````````````````````


```````````````````````````````` example Highlight: 12
==foo=====bar==                                                   
.
<p><mark>foo</mark>=<mark>bar</mark></p>
.
Document[0, 66]
  Paragraph[0, 66]
    Highlight[0, 7] textOpen:[0, 2, "=="] text:[2, 5, "foo"] textClose:[5, 7, "=="]
      Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 8] chars:[7, 8, "="]
    Highlight[8, 15] textOpen:[8, 10, "=="] text:[10, 13, "bar"] textClose:[13, 15, "=="]
      Text[10, 13] chars:[10, 13, "bar"]
````````````````````````````````


```````````````````````````````` example Highlight: 13
==foo======bar==                                                  
.
<p><mark>foo</mark>==<mark>bar</mark></p>
.
Document[0, 66]
  Paragraph[0, 66]
    Highlight[0, 7] textOpen:[0, 2, "=="] text:[2, 5, "foo"] textClose:[5, 7, "=="]
      Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 9] chars:[7, 9, "=="]
    Highlight[9, 16] textOpen:[9, 11, "=="] text:[11, 14, "bar"] textClose:[14, 16, "=="]
      Text[11, 14] chars:[11, 14, "bar"]
````````````````````````````````


```````````````````````````````` example Highlight: 14
==foo=======bar==                                                 
.
<p><mark>foo</mark>===<mark>bar</mark></p>
.
Document[0, 66]
  Paragraph[0, 66]
    Highlight[0, 7] textOpen:[0, 2, "=="] text:[2, 5, "foo"] textClose:[5, 7, "=="]
      Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 10] chars:[7, 10, "==="]
    Highlight[10, 17] textOpen:[10, 12, "=="] text:[12, 15, "bar"] textClose:[15, 17, "=="]
      Text[12, 15] chars:[12, 15, "bar"]
````````````````````````````````


```````````````````````````````` example Highlight: 15
==Paragraph with *emphasis* and __strong emphasis__==                                                             
.
<p><mark>Paragraph with <em>emphasis</em> and <strong>strong emphasis</strong></mark></p>
.
Document[0, 114]
  Paragraph[0, 114]
    Highlight[0, 53] textOpen:[0, 2, "=="] text:[2, 51, "Paragraph with *emphasis* and __strong emphasis__"] textClose:[51, 53, "=="]
      Text[2, 17] chars:[2, 17, "Parag … with "]
      Emphasis[17, 27] textOpen:[17, 18, "*"] text:[18, 26, "emphasis"] textClose:[26, 27, "*"]
        Text[18, 26] chars:[18, 26, "emphasis"]
      Text[27, 32] chars:[27, 32, " and "]
      StrongEmphasis[32, 51] textOpen:[32, 34, "__"] text:[34, 49, "strong emphasis"] textClose:[49, 51, "__"]
        Text[34, 49] chars:[34, 49, "stron … hasis"]
````````````````````````````````


```````````````````````````````` example Highlight: 16
> underline ==that==                                                           
.
<blockquote>
  <p>underline <mark>that</mark></p>
</blockquote>
.
Document[0, 79]
  BlockQuote[0, 79] marker:[0, 1, ">"]
    Paragraph[2, 79]
      Text[2, 12] chars:[2, 12, "underline "]
      Highlight[12, 20] textOpen:[12, 14, "=="] text:[14, 18, "that"] textClose:[18, 20, "=="]
        Text[14, 18] chars:[14, 18, "that"]
````````````````````````````````


```````````````````````````````` example Highlight: 17
Some title
==

==foo==                                                           
.
<h1>Some title</h1>
<p><mark>foo</mark></p>
.
Document[0, 81]
  Heading[0, 13] text:[0, 10, "Some title"] textClose:[11, 13, "=="]
    Text[0, 10] chars:[0, 10, "Some title"]
  Paragraph[15, 81]
    Highlight[15, 22] textOpen:[15, 17, "=="] text:[17, 20, "foo"] textClose:[20, 22, "=="]
      Text[17, 20] chars:[17, 20, "foo"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
> underline ==that==                                                           
.
<blockquote>
  <p md-pos="2-79">underline <mark md-pos="14-18">that</mark></p>
</blockquote>
.
Document[0, 79]
  BlockQuote[0, 79] marker:[0, 1, ">"]
    Paragraph[2, 79]
      Text[2, 12] chars:[2, 12, "underline "]
      Highlight[12, 20] textOpen:[12, 14, "=="] text:[14, 18, "that"] textClose:[18, 20, "=="]
        Text[14, 18] chars:[14, 18, "that"]
````````````````````````````````


## Custom Style HTML

```````````````````````````````` example(Custom Style HTML: 1) options(style-highlight)
==highlight==

.
<p><span class="text-highlight">highlight</span></p>
.
Document[0, 15]
  Paragraph[0, 14] isTrailingBlankLine
    Highlight[0, 13] textOpen:[0, 2, "=="] text:[2, 11, "highlight"] textClose:[11, 13, "=="]
      Text[2, 11] chars:[2, 11, "highlight"]
````````````````````````````````


