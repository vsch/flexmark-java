---
title: Ins Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Ins

Converts ++ins++ text to Ins nodes.

```````````````````````````````` example Ins: 1
+foo+                                                             
.
<p>+foo+</p>
.
Document[0, 67]
  Paragraph[0, 67]
    Text[0, 5] chars:[0, 5, "+foo+"]
````````````````````````````````


```````````````````````````````` example Ins: 2
++foo++                                                           
.
<p><ins>foo</ins></p>
.
Document[0, 67]
  Paragraph[0, 67]
    Ins[0, 7] textOpen:[0, 2, "++"] text:[2, 5, "foo"] textClose:[5, 7, "++"]
      Text[2, 5] chars:[2, 5, "foo"]
````````````````````````````````


```````````````````````````````` example Ins: 3
foo ++++                                                          
.
<p>foo ++++</p>
.
Document[0, 67]
  Paragraph[0, 67]
    Text[0, 8] chars:[0, 8, "foo ++++"]
````````````````````````````````


```````````````````````````````` example Ins: 4
++foo                                                             
.
<p>++foo</p>
.
Document[0, 67]
  Paragraph[0, 67]
    Text[0, 5] chars:[0, 5, "++foo"]
````````````````````````````````


```````````````````````````````` example Ins: 5
foo++                                                             
.
<p>foo++</p>
.
Document[0, 67]
  Paragraph[0, 67]
    Text[0, 5] chars:[0, 5, "foo++"]
````````````````````````````````


```````````````````````````````` example Ins: 6
+++foo+++                                                         
.
<p>+<ins>foo</ins>+</p>
.
Document[0, 67]
  Paragraph[0, 67]
    Text[0, 1] chars:[0, 1, "+"]
    Ins[1, 8] textOpen:[1, 3, "++"] text:[3, 6, "foo"] textClose:[6, 8, "++"]
      Text[3, 6] chars:[3, 6, "foo"]
    Text[8, 9] chars:[8, 9, "+"]
````````````````````````````````


```````````````````````````````` example Ins: 7
++foo+++                                                          
.
<p><ins>foo</ins>+</p>
.
Document[0, 67]
  Paragraph[0, 67]
    Ins[0, 7] textOpen:[0, 2, "++"] text:[2, 5, "foo"] textClose:[5, 7, "++"]
      Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 8] chars:[7, 8, "+"]
````````````````````````````````


```````````````````````````````` example Ins: 8
++foo+bar++                                                       
.
<p><ins>foo+bar</ins></p>
.
Document[0, 67]
  Paragraph[0, 67]
    Ins[0, 11] textOpen:[0, 2, "++"] text:[2, 9, "foo+bar"] textClose:[9, 11, "++"]
      Text[2, 9] chars:[2, 9, "foo+bar"]
````````````````````````````````


```````````````````````````````` example Ins: 9
++foo++bar++                                                      
.
<p><ins>foo</ins>bar++</p>
.
Document[0, 67]
  Paragraph[0, 67]
    Ins[0, 7] textOpen:[0, 2, "++"] text:[2, 5, "foo"] textClose:[5, 7, "++"]
      Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 12] chars:[7, 12, "bar++"]
````````````````````````````````


```````````````````````````````` example Ins: 10
++foo+++bar++                                                     
.
<p><ins>foo</ins>+bar++</p>
.
Document[0, 67]
  Paragraph[0, 67]
    Ins[0, 7] textOpen:[0, 2, "++"] text:[2, 5, "foo"] textClose:[5, 7, "++"]
      Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 13] chars:[7, 13, "+bar++"]
````````````````````````````````


```````````````````````````````` example Ins: 11
++foo++++bar++                                                    
.
<p><ins>foo</ins><ins>bar</ins></p>
.
Document[0, 67]
  Paragraph[0, 67]
    Ins[0, 7] textOpen:[0, 2, "++"] text:[2, 5, "foo"] textClose:[5, 7, "++"]
      Text[2, 5] chars:[2, 5, "foo"]
    Ins[7, 14] textOpen:[7, 9, "++"] text:[9, 12, "bar"] textClose:[12, 14, "++"]
      Text[9, 12] chars:[9, 12, "bar"]
````````````````````````````````


```````````````````````````````` example Ins: 12
++foo+++++bar++                                                   
.
<p><ins>foo</ins>+<ins>bar</ins></p>
.
Document[0, 67]
  Paragraph[0, 67]
    Ins[0, 7] textOpen:[0, 2, "++"] text:[2, 5, "foo"] textClose:[5, 7, "++"]
      Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 8] chars:[7, 8, "+"]
    Ins[8, 15] textOpen:[8, 10, "++"] text:[10, 13, "bar"] textClose:[13, 15, "++"]
      Text[10, 13] chars:[10, 13, "bar"]
````````````````````````````````


```````````````````````````````` example Ins: 13
++foo++++++bar++                                                  
.
<p><ins>foo</ins>++<ins>bar</ins></p>
.
Document[0, 67]
  Paragraph[0, 67]
    Ins[0, 7] textOpen:[0, 2, "++"] text:[2, 5, "foo"] textClose:[5, 7, "++"]
      Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 9] chars:[7, 9, "++"]
    Ins[9, 16] textOpen:[9, 11, "++"] text:[11, 14, "bar"] textClose:[14, 16, "++"]
      Text[11, 14] chars:[11, 14, "bar"]
````````````````````````````````


```````````````````````````````` example Ins: 14
++foo+++++++bar++                                                 
.
<p><ins>foo</ins>+++<ins>bar</ins></p>
.
Document[0, 67]
  Paragraph[0, 67]
    Ins[0, 7] textOpen:[0, 2, "++"] text:[2, 5, "foo"] textClose:[5, 7, "++"]
      Text[2, 5] chars:[2, 5, "foo"]
    Text[7, 10] chars:[7, 10, "+++"]
    Ins[10, 17] textOpen:[10, 12, "++"] text:[12, 15, "bar"] textClose:[15, 17, "++"]
      Text[12, 15] chars:[12, 15, "bar"]
````````````````````````````````


```````````````````````````````` example Ins: 15
++Paragraph with *emphasis* and __strong emphasis__++                                                             
.
<p><ins>Paragraph with <em>emphasis</em> and <strong>strong emphasis</strong></ins></p>
.
Document[0, 115]
  Paragraph[0, 115]
    Ins[0, 53] textOpen:[0, 2, "++"] text:[2, 51, "Paragraph with *emphasis* and __strong emphasis__"] textClose:[51, 53, "++"]
      Text[2, 17] chars:[2, 17, "Parag … with "]
      Emphasis[17, 27] textOpen:[17, 18, "*"] text:[18, 26, "emphasis"] textClose:[26, 27, "*"]
        Text[18, 26] chars:[18, 26, "emphasis"]
      Text[27, 32] chars:[27, 32, " and "]
      StrongEmphasis[32, 51] textOpen:[32, 34, "__"] text:[34, 49, "strong emphasis"] textClose:[49, 51, "__"]
        Text[34, 49] chars:[34, 49, "stron … hasis"]
````````````````````````````````


```````````````````````````````` example Ins: 16
> underline ++that++                                                           
.
<blockquote>
  <p>underline <ins>that</ins></p>
</blockquote>
.
Document[0, 80]
  BlockQuote[0, 80] marker:[0, 1, ">"]
    Paragraph[2, 80]
      Text[2, 12] chars:[2, 12, "underline "]
      Ins[12, 20] textOpen:[12, 14, "++"] text:[14, 18, "that"] textClose:[18, 20, "++"]
        Text[14, 18] chars:[14, 18, "that"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
> underline ++that++                                                           
.
<blockquote>
  <p md-pos="2-80">underline <ins>that</ins></p>
</blockquote>
.
Document[0, 80]
  BlockQuote[0, 80] marker:[0, 1, ">"]
    Paragraph[2, 80]
      Text[2, 12] chars:[2, 12, "underline "]
      Ins[12, 20] textOpen:[12, 14, "++"] text:[14, 18, "that"] textClose:[18, 20, "++"]
        Text[14, 18] chars:[14, 18, "that"]
````````````````````````````````


