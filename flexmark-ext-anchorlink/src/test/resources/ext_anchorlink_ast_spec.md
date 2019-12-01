---
title: AnchorLinks Extension Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## AnchorLinks

Automatically generates anchor links for heading nodes.

Basic, anchor links wrap header text

```````````````````````````````` example AnchorLinks: 1
# Enhanced Edition
.
<h1><a href="#enhanced-edition" id="enhanced-edition" class="anchor">Enhanced Edition</a></h1>
.
Document[0, 18]
  Heading[0, 18] textOpen:[0, 1, "#"] text:[2, 18, "Enhanced Edition"]
    AnchorLink[2, 18]
      Text[2, 18] chars:[2, 18, "Enhan … ition"]
````````````````````````````````


Basic, duplicates

```````````````````````````````` example AnchorLinks: 2
# Enhanced Edition
# Enhanced Edition
.
<h1><a href="#enhanced-edition" id="enhanced-edition" class="anchor">Enhanced Edition</a></h1>
<h1><a href="#enhanced-edition-1" id="enhanced-edition-1" class="anchor">Enhanced Edition</a></h1>
.
Document[0, 37]
  Heading[0, 18] textOpen:[0, 1, "#"] text:[2, 18, "Enhanced Edition"]
    AnchorLink[2, 18]
      Text[2, 18] chars:[2, 18, "Enhan … ition"]
  Heading[19, 37] textOpen:[19, 20, "#"] text:[21, 37, "Enhanced Edition"]
    AnchorLink[21, 37]
      Text[21, 37] chars:[21, 37, "Enhan … ition"]
````````````````````````````````


Basic, duplicates, no look-ahead

```````````````````````````````` example AnchorLinks: 3
# Enhanced Edition
# Enhanced Edition
# Enhanced Edition-1
.
<h1><a href="#enhanced-edition" id="enhanced-edition" class="anchor">Enhanced Edition</a></h1>
<h1><a href="#enhanced-edition-1" id="enhanced-edition-1" class="anchor">Enhanced Edition</a></h1>
<h1><a href="#enhanced-edition-1" id="enhanced-edition-1" class="anchor">Enhanced Edition-1</a></h1>
.
Document[0, 58]
  Heading[0, 18] textOpen:[0, 1, "#"] text:[2, 18, "Enhanced Edition"]
    AnchorLink[2, 18]
      Text[2, 18] chars:[2, 18, "Enhan … ition"]
  Heading[19, 37] textOpen:[19, 20, "#"] text:[21, 37, "Enhanced Edition"]
    AnchorLink[21, 37]
      Text[21, 37] chars:[21, 37, "Enhan … ition"]
  Heading[38, 58] textOpen:[38, 39, "#"] text:[40, 58, "Enhanced Edition-1"]
    AnchorLink[40, 58]
      Text[40, 58] chars:[40, 58, "Enhan … ion-1"]
````````````````````````````````


Basic, anchor links do not wrap header text

```````````````````````````````` example(AnchorLinks: 4) options(no-wrap)
# Enhanced Edition
.
<h1><a href="#enhanced-edition" id="enhanced-edition" class="anchor"></a>Enhanced Edition</h1>
.
Document[0, 18]
  Heading[0, 18] textOpen:[0, 1, "#"] text:[2, 18, "Enhanced Edition"]
    AnchorLink[2, 2]
    Text[2, 18] chars:[2, 18, "Enhan … ition"]
````````````````````````````````


With prefix/suffix, anchor links wrap header text

```````````````````````````````` example(AnchorLinks: 5) options(prefix-suffix)
# Enhanced Edition
.
<h1><a href="#enhanced-edition" id="enhanced-edition" class="anchor"><span class="anchor">Enhanced Edition</span></a></h1>
.
Document[0, 18]
  Heading[0, 18] textOpen:[0, 1, "#"] text:[2, 18, "Enhanced Edition"]
    AnchorLink[2, 18]
      Text[2, 18] chars:[2, 18, "Enhan … ition"]
````````````````````````````````


With prefix/suffix, anchor links do not wrap header text

```````````````````````````````` example(AnchorLinks: 6) options(no-wrap, prefix-suffix)
# Enhanced Edition
.
<h1><a href="#enhanced-edition" id="enhanced-edition" class="anchor"><span class="anchor"></span></a>Enhanced Edition</h1>
.
Document[0, 18]
  Heading[0, 18] textOpen:[0, 1, "#"] text:[2, 18, "Enhanced Edition"]
    AnchorLink[2, 2]
    Text[2, 18] chars:[2, 18, "Enhan … ition"]
````````````````````````````````


With prefix/suffix, anchor links do not wrap header text

```````````````````````````````` example(AnchorLinks: 7) options(no-wrap, prefix-suffix, no-class)
# Enhanced Edition
.
<h1><a href="#enhanced-edition" id="enhanced-edition"><span class="anchor"></span></a>Enhanced Edition</h1>
.
Document[0, 18]
  Heading[0, 18] textOpen:[0, 1, "#"] text:[2, 18, "Enhanced Edition"]
    AnchorLink[2, 2]
    Text[2, 18] chars:[2, 18, "Enhan … ition"]
````````````````````````````````


Set name, with prefix/suffix, anchor links do not wrap header text

```````````````````````````````` example(AnchorLinks: 8) options(no-wrap, prefix-suffix, no-class, set-name)
# Enhanced Edition
.
<h1><a href="#enhanced-edition" id="enhanced-edition" name="enhanced-edition"><span class="anchor"></span></a>Enhanced Edition</h1>
.
Document[0, 18]
  Heading[0, 18] textOpen:[0, 1, "#"] text:[2, 18, "Enhanced Edition"]
    AnchorLink[2, 2]
    Text[2, 18] chars:[2, 18, "Enhan … ition"]
````````````````````````````````


Set name, no id, with prefix/suffix, anchor links do not wrap header text

```````````````````````````````` example(AnchorLinks: 9) options(no-wrap, prefix-suffix, no-class, set-name, no-id)
# Enhanced Edition
.
<h1><a href="#enhanced-edition" name="enhanced-edition"><span class="anchor"></span></a>Enhanced Edition</h1>
.
Document[0, 18]
  Heading[0, 18] textOpen:[0, 1, "#"] text:[2, 18, "Enhanced Edition"]
    AnchorLink[2, 2]
    Text[2, 18] chars:[2, 18, "Enhan … ition"]
````````````````````````````````


Just to test postProcessor exclusions

```````````````````````````````` example(AnchorLinks: 10) options(no-wrap, prefix-suffix, no-class, set-name, no-id)
> # Enhanced Edition
.
<blockquote>
  <h1>Enhanced Edition</h1>
</blockquote>
.
Document[0, 20]
  BlockQuote[0, 20] marker:[0, 1, ">"]
    Heading[2, 20] textOpen:[2, 3, "#"] text:[4, 20, "Enhanced Edition"]
      Text[4, 20] chars:[4, 20, "Enhan … ition"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
# Header
.
<h1 md-pos="2-8"><a href="#header" id="header" class="anchor">Header</a></h1>
.
Document[0, 8]
  Heading[0, 8] textOpen:[0, 1, "#"] text:[2, 8, "Header"]
    AnchorLink[2, 8]
      Text[2, 8] chars:[2, 8, "Header"]
````````````````````````````````


