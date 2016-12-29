---
title: WikiLinks Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## WikiLinks

Converts wikilink of the forms: [[link]], [[link|text]] and [[text|link]] to links in the HTML
page.

no spaces between brackets

```````````````````````````````` example WikiLinks: 1
[ [not wiki link]]
.
<p>[ [not wiki link]]</p>
.
Document[0, 19]
  Paragraph[0, 19]
    Text[0, 2] chars:[0, 2, "[ "]
    LinkRef[2, 17] referenceOpen:[2, 3, "["] reference:[3, 16, "not wiki link"] referenceClose:[16, 17, "]"]
      Text[3, 16] chars:[3, 16, "not w …  link"]
    Text[17, 18] chars:[17, 18, "]"]
````````````````````````````````


no spaces between brackets

```````````````````````````````` example WikiLinks: 2
[[not wiki link] ]
.
<p>[[not wiki link] ]</p>
.
Document[0, 19]
  Paragraph[0, 19]
    Text[0, 1] chars:[0, 1, "["]
    LinkRef[1, 16] referenceOpen:[1, 2, "["] reference:[2, 15, "not wiki link"] referenceClose:[15, 16, "]"]
      Text[2, 15] chars:[2, 15, "not w …  link"]
    Text[16, 18] chars:[16, 18, " ]"]
````````````````````````````````


simple wiki link

```````````````````````````````` example WikiLinks: 3
[[wiki link]]
.
<p><a href="wiki-link">wiki link</a></p>
.
Document[0, 14]
  Paragraph[0, 14]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[11, 13, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
````````````````````````````````


wiki link with text

```````````````````````````````` example WikiLinks: 4
[[wiki text|wiki link]]
.
<p><a href="wiki-link">wiki text</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 23] linkOpen:[0, 2, "[["] link:[12, 21, "wiki link"] pageRef:[12, 21, "wiki link"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[21, 23, "]]"]
      Text[2, 21] chars:[2, 21, "wiki  …  link"]
````````````````````````````````


wiki link with text, links first option

```````````````````````````````` example(WikiLinks: 5) options(links-first)
[[wiki link|wiki text]]
.
<p><a href="wiki-link">wiki text</a></p>
.
Document[0, 23]
  Paragraph[0, 23]
    WikiLink[0, 23] linkOpen:[0, 2, "[["] text:[12, 21, "wiki text"] textSep:[11, 12, "|"] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[21, 23, "]]"]
      Text[2, 21] chars:[2, 21, "wiki  …  text"]
````````````````````````````````


simple wiki link with ! before

```````````````````````````````` example WikiLinks: 6
![[wiki link]]
.
<p>!<a href="wiki-link">wiki link</a></p>
.
Document[0, 15]
  Paragraph[0, 15]
    Text[0, 1] chars:[0, 1, "!"]
    WikiLink[1, 14] linkOpen:[1, 3, "[["] link:[3, 12, "wiki link"] pageRef:[3, 12, "wiki link"] linkClose:[12, 14, "]]"]
      Text[3, 12] chars:[3, 12, "wiki link"]
````````````````````````````````


wiki link with text with ! before

```````````````````````````````` example WikiLinks: 7
![[wiki text|wiki link]]
.
<p>!<a href="wiki-link">wiki text</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    Text[0, 1] chars:[0, 1, "!"]
    WikiLink[1, 24] linkOpen:[1, 3, "[["] link:[13, 22, "wiki link"] pageRef:[13, 22, "wiki link"] textSep:[12, 13, "|"] text:[3, 12, "wiki text"] linkClose:[22, 24, "]]"]
      Text[3, 22] chars:[3, 22, "wiki  …  link"]
````````````````````````````````


reference following will be a reference, even if not defined

```````````````````````````````` example WikiLinks: 8
[[wiki link]][ref]
.
<p><a href="wiki-link">wiki link</a>[ref]</p>
.
Document[0, 19]
  Paragraph[0, 19]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[11, 13, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
    LinkRef[13, 18] referenceOpen:[13, 14, "["] reference:[14, 17, "ref"] referenceClose:[17, 18, "]"]
      Text[14, 17] chars:[14, 17, "ref"]
````````````````````````````````


reference following will be a reference

```````````````````````````````` example WikiLinks: 9
[[wiki link]][ref]

[ref]: /url
.
<p><a href="wiki-link">wiki link</a><a href="/url">ref</a></p>
.
Document[0, 32]
  Paragraph[0, 19]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[11, 13, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
    LinkRef[13, 18] referenceOpen:[13, 14, "["] reference:[14, 17, "ref"] referenceClose:[17, 18, "]"]
      Text[14, 17] chars:[14, 17, "ref"]
  Reference[20, 31] refOpen:[20, 21, "["] ref:[21, 24, "ref"] refClose:[24, 26, "]:"] url:[27, 31, "/url"]
````````````````````````````````


dummy reference following will be an empty reference

```````````````````````````````` example WikiLinks: 10
[[wiki link]][]
.
<p><a href="wiki-link">wiki link</a>[]</p>
.
Document[0, 16]
  Paragraph[0, 16]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[11, 13, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
    LinkRef[13, 15] referenceOpen:[13, 14, "["] reference:[14, 14] referenceClose:[14, 15, "]"]
````````````````````````````````


reference inside is not a wiki link but a link ref with brackets around it

```````````````````````````````` example WikiLinks: 11
[[not wiki link][ref]]
.
<p>[[not wiki link][ref]]</p>
.
Document[0, 23]
  Paragraph[0, 23]
    Text[0, 1] chars:[0, 1, "["]
    LinkRef[1, 21] textOpen:[1, 2, "["] text:[2, 15, "not wiki link"] textClose:[15, 16, "]"] referenceOpen:[16, 17, "["] reference:[17, 20, "ref"] referenceClose:[20, 21, "]"]
      Text[2, 15] chars:[2, 15, "not w …  link"]
    Text[21, 22] chars:[21, 22, "]"]
````````````````````````````````


dummy reference inside is not a wiki link but a link ref with brackets around it

```````````````````````````````` example WikiLinks: 12
[[not wiki link][]]
.
<p>[[not wiki link]]</p>
.
Document[0, 20]
  Paragraph[0, 20]
    Text[0, 1] chars:[0, 1, "["]
    LinkRef[1, 18] referenceOpen:[1, 2, "["] reference:[2, 15, "not wiki link"] referenceClose:[15, 16, "]"] textOpen:[16, 17, "["] textClose:[17, 18, "]"]
      Text[2, 15] chars:[2, 15, "not w …  link"]
    Text[18, 19] chars:[18, 19, "]"]
````````````````````````````````


```````````````````````````````` example WikiLinks: 13
[[wiki link]] [^link][ref] [[^wiki link]]
.
<p><a href="wiki-link">wiki link</a> [^link][ref] <a href="^wiki-link">^wiki link</a></p>
.
Document[0, 42]
  Paragraph[0, 42]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[11, 13, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
    Text[13, 14] chars:[13, 14, " "]
    LinkRef[14, 26] textOpen:[14, 15, "["] text:[15, 20, "^link"] textClose:[20, 21, "]"] referenceOpen:[21, 22, "["] reference:[22, 25, "ref"] referenceClose:[25, 26, "]"]
      Text[15, 20] chars:[15, 20, "^link"]
    Text[26, 27] chars:[26, 27, " "]
    WikiLink[27, 41] linkOpen:[27, 29, "[["] link:[29, 39, "^wiki link"] pageRef:[29, 39, "^wiki link"] linkClose:[39, 41, "]]"]
      Text[29, 39] chars:[29, 39, "^wiki link"]
````````````````````````````````


Exclamation before is just text

```````````````````````````````` example WikiLinks: 14
![[wiki link]] [^link][ref] [[^wiki link]] [[wiki]][ref]
.
<p>!<a href="wiki-link">wiki link</a> [^link][ref] <a href="^wiki-link">^wiki link</a> <a href="wiki">wiki</a>[ref]</p>
.
Document[0, 57]
  Paragraph[0, 57]
    Text[0, 1] chars:[0, 1, "!"]
    WikiLink[1, 14] linkOpen:[1, 3, "[["] link:[3, 12, "wiki link"] pageRef:[3, 12, "wiki link"] linkClose:[12, 14, "]]"]
      Text[3, 12] chars:[3, 12, "wiki link"]
    Text[14, 15] chars:[14, 15, " "]
    LinkRef[15, 27] textOpen:[15, 16, "["] text:[16, 21, "^link"] textClose:[21, 22, "]"] referenceOpen:[22, 23, "["] reference:[23, 26, "ref"] referenceClose:[26, 27, "]"]
      Text[16, 21] chars:[16, 21, "^link"]
    Text[27, 28] chars:[27, 28, " "]
    WikiLink[28, 42] linkOpen:[28, 30, "[["] link:[30, 40, "^wiki link"] pageRef:[30, 40, "^wiki link"] linkClose:[40, 42, "]]"]
      Text[30, 40] chars:[30, 40, "^wiki link"]
    Text[42, 43] chars:[42, 43, " "]
    WikiLink[43, 51] linkOpen:[43, 45, "[["] link:[45, 49, "wiki"] pageRef:[45, 49, "wiki"] linkClose:[49, 51, "]]"]
      Text[45, 49] chars:[45, 49, "wiki"]
    LinkRef[51, 56] referenceOpen:[51, 52, "["] reference:[52, 55, "ref"] referenceClose:[55, 56, "]"]
      Text[52, 55] chars:[52, 55, "ref"]
````````````````````````````````


Custom extension

```````````````````````````````` example(WikiLinks: 15) options(link-ext)
[[wiki link]] [^link][ref] [[^wiki link]]
.
<p><a href="wiki-link.html">wiki link</a> [^link][ref] <a href="^wiki-link.html">^wiki link</a></p>
.
Document[0, 41]
  Paragraph[0, 41]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[11, 13, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
    Text[13, 14] chars:[13, 14, " "]
    LinkRef[14, 26] textOpen:[14, 15, "["] text:[15, 20, "^link"] textClose:[20, 21, "]"] referenceOpen:[21, 22, "["] reference:[22, 25, "ref"] referenceClose:[25, 26, "]"]
      Text[15, 20] chars:[15, 20, "^link"]
    Text[26, 27] chars:[26, 27, " "]
    WikiLink[27, 41] linkOpen:[27, 29, "[["] link:[29, 39, "^wiki link"] pageRef:[29, 39, "^wiki link"] linkClose:[39, 41, "]]"]
      Text[29, 39] chars:[29, 39, "^wiki link"]
````````````````````````````````


Custom prefix

```````````````````````````````` example(WikiLinks: 16) options(link-prefix)
[[wiki link]] [^link][ref] [[^wiki link]]
.
<p><a href="/prefix/wiki-link">wiki link</a> [^link][ref] <a href="/prefix/^wiki-link">^wiki link</a></p>
.
Document[0, 41]
  Paragraph[0, 41]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[11, 13, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
    Text[13, 14] chars:[13, 14, " "]
    LinkRef[14, 26] textOpen:[14, 15, "["] text:[15, 20, "^link"] textClose:[20, 21, "]"] referenceOpen:[21, 22, "["] reference:[22, 25, "ref"] referenceClose:[25, 26, "]"]
      Text[15, 20] chars:[15, 20, "^link"]
    Text[26, 27] chars:[26, 27, " "]
    WikiLink[27, 41] linkOpen:[27, 29, "[["] link:[29, 39, "^wiki link"] pageRef:[29, 39, "^wiki link"] linkClose:[39, 41, "]]"]
      Text[29, 39] chars:[29, 39, "^wiki link"]
````````````````````````````````


With empty anchor ref

```````````````````````````````` example WikiLinks: 17
[[wiki link#]] 
.
<p><a href="wiki-link#">wiki link</a></p>
.
Document[0, 16]
  Paragraph[0, 16]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 12] linkClose:[12, 14, "]]"]
      Text[2, 12] chars:[2, 12, "wiki link#"]
````````````````````````````````


With Anchor ref

```````````````````````````````` example WikiLinks: 18
[[wiki link#anchor-ref]] 
.
<p><a href="wiki-link#anchor-ref">wiki link</a></p>
.
Document[0, 26]
  Paragraph[0, 26]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … r-ref"]
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example WikiLinks: 19
[[wiki text|wiki link#]] 
.
<p><a href="wiki-link#">wiki text</a></p>
.
Document[0, 26]
  Paragraph[0, 26]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[12, 22, "wiki link#"] pageRef:[12, 21, "wiki link"] anchorMarker:[21, 22, "#"] anchorRef:[22, 22] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … link#"]
````````````````````````````````


With text, anchor ref

```````````````````````````````` example WikiLinks: 20
[[wiki text|wiki link#anchor-ref]] 
.
<p><a href="wiki-link#anchor-ref">wiki text</a></p>
.
Document[0, 36]
  Paragraph[0, 36]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] link:[12, 32, "wiki link#anchor-ref"] pageRef:[12, 21, "wiki link"] anchorMarker:[21, 22, "#"] anchorRef:[22, 32, "anchor-ref"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[32, 34, "]]"]
      Text[2, 32] chars:[2, 32, "wiki  … r-ref"]
````````````````````````````````


Links first, with text, empty anchor ref

```````````````````````````````` example(WikiLinks: 21) options(links-first)
[[wiki link#|wiki text]] 
.
<p><a href="wiki-link#">wiki text</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] text:[13, 22, "wiki text"] textSep:[12, 13, "|"] link:[2, 12, "wiki link#"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 12] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  …  text"]
````````````````````````````````


Links first, with text, anchor ref

```````````````````````````````` example(WikiLinks: 22) options(links-first)
[[wiki link#anchor-ref|wiki text]] 
.
<p><a href="wiki-link#anchor-ref">wiki text</a></p>
.
Document[0, 35]
  Paragraph[0, 35]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] text:[23, 32, "wiki text"] textSep:[22, 23, "|"] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] linkClose:[32, 34, "]]"]
      Text[2, 32] chars:[2, 32, "wiki  …  text"]
````````````````````````````````


Custom extension with empty Anchor ref

```````````````````````````````` example(WikiLinks: 23) options(link-ext)
[[wiki link#]] 
.
<p><a href="wiki-link.html#">wiki link</a></p>
.
Document[0, 15]
  Paragraph[0, 15]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 12] linkClose:[12, 14, "]]"]
      Text[2, 12] chars:[2, 12, "wiki link#"]
````````````````````````````````


Custom extension with anchor ref

```````````````````````````````` example(WikiLinks: 24) options(link-ext)
[[wiki link#anchor-ref]] 
.
<p><a href="wiki-link.html#anchor-ref">wiki link</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … r-ref"]
````````````````````````````````


Custom prefix with empty anchor ref

```````````````````````````````` example(WikiLinks: 25) options(link-prefix)
[[wiki link#]]
.
<p><a href="/prefix/wiki-link#">wiki link</a></p>
.
Document[0, 14]
  Paragraph[0, 14]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 12] linkClose:[12, 14, "]]"]
      Text[2, 12] chars:[2, 12, "wiki link#"]
````````````````````````````````


Custom prefix with anchor ref

```````````````````````````````` example(WikiLinks: 26) options(link-prefix)
[[wiki link#anchor-ref]]
.
<p><a href="/prefix/wiki-link#anchor-ref">wiki link</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … r-ref"]
````````````````````````````````


## Source Position Attribute

simple wiki link

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
[[wiki link]]
.
<p md-pos="0-13"><a href="wiki-link" md-pos="0-13">wiki link</a></p>
.
Document[0, 13]
  Paragraph[0, 13]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[11, 13, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
````````````````````````````````


wiki link with text

```````````````````````````````` example(Source Position Attribute: 2) options(src-pos)
[[wiki text|wiki link]]
.
<p md-pos="0-23"><a href="wiki-link" md-pos="0-23">wiki text</a></p>
.
Document[0, 23]
  Paragraph[0, 23]
    WikiLink[0, 23] linkOpen:[0, 2, "[["] link:[12, 21, "wiki link"] pageRef:[12, 21, "wiki link"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[21, 23, "]]"]
      Text[2, 21] chars:[2, 21, "wiki  …  link"]
````````````````````````````````


wiki link with text, links first option

```````````````````````````````` example(Source Position Attribute: 3) options(src-pos, links-first)
[[wiki link|wiki text]]
.
<p md-pos="0-23"><a href="wiki-link" md-pos="0-23">wiki text</a></p>
.
Document[0, 23]
  Paragraph[0, 23]
    WikiLink[0, 23] linkOpen:[0, 2, "[["] text:[12, 21, "wiki text"] textSep:[11, 12, "|"] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[21, 23, "]]"]
      Text[2, 21] chars:[2, 21, "wiki  …  text"]
````````````````````````````````


## WikiImages

Converts wiki image of the forms: ![[image]], ![[image|text]] and ![[text|image]] to image
images in the HTML page.

no spaces between brackets

```````````````````````````````` example(WikiImages: 1) options(wiki-images)
![ [not wiki image]]
.
<p>![ [not wiki image]]</p>
.
Document[0, 20]
  Paragraph[0, 20]
    Text[0, 3] chars:[0, 3, "![ "]
    LinkRef[3, 19] referenceOpen:[3, 4, "["] reference:[4, 18, "not wiki image"] referenceClose:[18, 19, "]"]
      Text[4, 18] chars:[4, 18, "not w … image"]
    Text[19, 20] chars:[19, 20, "]"]
````````````````````````````````


no spaces between brackets

```````````````````````````````` example(WikiImages: 2) options(wiki-images)
![[not wiki image] ]
.
<p>![[not wiki image] ]</p>
.
Document[0, 20]
  Paragraph[0, 20]
    Text[0, 2] chars:[0, 2, "!["]
    LinkRef[2, 18] referenceOpen:[2, 3, "["] reference:[3, 17, "not wiki image"] referenceClose:[17, 18, "]"]
      Text[3, 17] chars:[3, 17, "not w … image"]
    Text[18, 20] chars:[18, 20, " ]"]
````````````````````````````````


simple wiki image

```````````````````````````````` example(WikiImages: 3) options(wiki-images)
![[wiki image]]
.
<p><img src="wiki-image" alt="wiki image" /></p>
.
Document[0, 15]
  Paragraph[0, 15]
    WikiImage[0, 15] linkOpen:[0, 3, "![["] link:[3, 13, "wiki image"] pageRef:[3, 13, "wiki image"] linkClose:[13, 15, "]]"]
      Text[3, 13] chars:[3, 13, "wiki image"]
````````````````````````````````


simple wiki image ignored without wiki images option

```````````````````````````````` example WikiImages: 4
![[wiki image]]
.
<p>!<a href="wiki-image">wiki image</a></p>
.
Document[0, 16]
  Paragraph[0, 16]
    Text[0, 1] chars:[0, 1, "!"]
    WikiLink[1, 15] linkOpen:[1, 3, "[["] link:[3, 13, "wiki image"] pageRef:[3, 13, "wiki image"] linkClose:[13, 15, "]]"]
      Text[3, 13] chars:[3, 13, "wiki image"]
````````````````````````````````


wiki image with text

```````````````````````````````` example(WikiImages: 5) options(wiki-images)
![[alt text|wiki image]]
.
<p><img src="wiki-image" alt="alt text" /></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiImage[0, 24] linkOpen:[0, 3, "![["] link:[12, 22, "wiki image"] pageRef:[12, 22, "wiki image"] textSep:[11, 12, "|"] text:[3, 11, "alt text"] linkClose:[22, 24, "]]"]
      Text[3, 22] chars:[3, 22, "alt t … image"]
````````````````````````````````


wiki image with text, images first option

```````````````````````````````` example(WikiImages: 6) options(wiki-images, links-first)
![[wiki image|alt text]]
.
<p><img src="wiki-image" alt="alt text" /></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiImage[0, 24] linkOpen:[0, 3, "![["] text:[14, 22, "alt text"] textSep:[13, 14, "|"] link:[3, 13, "wiki image"] pageRef:[3, 13, "wiki image"] linkClose:[22, 24, "]]"]
      Text[3, 22] chars:[3, 22, "wiki  …  text"]
````````````````````````````````


reference following will be a reference, even if not defined

```````````````````````````````` example(WikiImages: 7) options(wiki-images)
![[wiki image]][ref]
.
<p><img src="wiki-image" alt="wiki image" />[ref]</p>
.
Document[0, 20]
  Paragraph[0, 20]
    WikiImage[0, 15] linkOpen:[0, 3, "![["] link:[3, 13, "wiki image"] pageRef:[3, 13, "wiki image"] linkClose:[13, 15, "]]"]
      Text[3, 13] chars:[3, 13, "wiki image"]
    LinkRef[15, 20] referenceOpen:[15, 16, "["] reference:[16, 19, "ref"] referenceClose:[19, 20, "]"]
      Text[16, 19] chars:[16, 19, "ref"]
````````````````````````````````


reference following will be a reference

```````````````````````````````` example(WikiImages: 8) options(wiki-images)
![[wiki image]][ref]

[ref]: /url
.
<p><img src="wiki-image" alt="wiki image" /><a href="/url">ref</a></p>
.
Document[0, 33]
  Paragraph[0, 21]
    WikiImage[0, 15] linkOpen:[0, 3, "![["] link:[3, 13, "wiki image"] pageRef:[3, 13, "wiki image"] linkClose:[13, 15, "]]"]
      Text[3, 13] chars:[3, 13, "wiki image"]
    LinkRef[15, 20] referenceOpen:[15, 16, "["] reference:[16, 19, "ref"] referenceClose:[19, 20, "]"]
      Text[16, 19] chars:[16, 19, "ref"]
  Reference[22, 33] refOpen:[22, 23, "["] ref:[23, 26, "ref"] refClose:[26, 28, "]:"] url:[29, 33, "/url"]
````````````````````````````````


dummy reference following will be an empty reference

```````````````````````````````` example(WikiImages: 9) options(wiki-images)
![[wiki image]][]
.
<p><img src="wiki-image" alt="wiki image" />[]</p>
.
Document[0, 17]
  Paragraph[0, 17]
    WikiImage[0, 15] linkOpen:[0, 3, "![["] link:[3, 13, "wiki image"] pageRef:[3, 13, "wiki image"] linkClose:[13, 15, "]]"]
      Text[3, 13] chars:[3, 13, "wiki image"]
    LinkRef[15, 17] referenceOpen:[15, 16, "["] reference:[16, 16] referenceClose:[16, 17, "]"]
````````````````````````````````


reference inside is not a wiki image but a image ref with brackets around it

```````````````````````````````` example(WikiImages: 10) options(wiki-images)
![[not wiki image][ref]]
.
<p>![[not wiki image][ref]]</p>
.
Document[0, 24]
  Paragraph[0, 24]
    Text[0, 2] chars:[0, 2, "!["]
    LinkRef[2, 23] textOpen:[2, 3, "["] text:[3, 17, "not wiki image"] textClose:[17, 18, "]"] referenceOpen:[18, 19, "["] reference:[19, 22, "ref"] referenceClose:[22, 23, "]"]
      Text[3, 17] chars:[3, 17, "not w … image"]
    Text[23, 24] chars:[23, 24, "]"]
````````````````````````````````


dummy reference inside is not a wiki image but a image ref with brackets around it

```````````````````````````````` example(WikiImages: 11) options(wiki-images)
![[not wiki image][]]
.
<p>![[not wiki image]]</p>
.
Document[0, 21]
  Paragraph[0, 21]
    Text[0, 2] chars:[0, 2, "!["]
    LinkRef[2, 20] referenceOpen:[2, 3, "["] reference:[3, 17, "not wiki image"] referenceClose:[17, 18, "]"] textOpen:[18, 19, "["] textClose:[19, 20, "]"]
      Text[3, 17] chars:[3, 17, "not w … image"]
    Text[20, 21] chars:[20, 21, "]"]
````````````````````````````````


```````````````````````````````` example(WikiImages: 12) options(wiki-images)
![[wiki image]] ![^image][ref] ![[^wiki image]]
.
<p><img src="wiki-image" alt="wiki image" /> [^image][ref] <img src="^wiki-image" alt="^wiki image" /></p>
.
Document[0, 47]
  Paragraph[0, 47]
    WikiImage[0, 15] linkOpen:[0, 3, "![["] link:[3, 13, "wiki image"] pageRef:[3, 13, "wiki image"] linkClose:[13, 15, "]]"]
      Text[3, 13] chars:[3, 13, "wiki image"]
    Text[15, 16] chars:[15, 16, " "]
    ImageRef[17, 30] textOpen:[17, 18, "["] text:[18, 24, "^image"] textClose:[24, 25, "]"] referenceOpen:[25, 26, "["] reference:[26, 29, "ref"] referenceClose:[29, 30, "]"]
      Text[18, 24] chars:[18, 24, "^image"]
    Text[30, 31] chars:[30, 31, " "]
    WikiImage[31, 47] linkOpen:[31, 34, "![["] link:[34, 45, "^wiki image"] pageRef:[34, 45, "^wiki image"] linkClose:[45, 47, "]]"]
      Text[34, 45] chars:[34, 45, "^wiki … image"]
````````````````````````````````


Custom extension

```````````````````````````````` example(WikiImages: 13) options(wiki-images, image-ext)
[[wiki image]] [^image][ref] [[^wiki image]]
.
<p><a href="wiki-image">wiki image</a> [^image][ref] <a href="^wiki-image">^wiki image</a></p>
.
Document[0, 44]
  Paragraph[0, 44]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki image"] pageRef:[2, 12, "wiki image"] linkClose:[12, 14, "]]"]
      Text[2, 12] chars:[2, 12, "wiki image"]
    Text[14, 15] chars:[14, 15, " "]
    LinkRef[15, 28] textOpen:[15, 16, "["] text:[16, 22, "^image"] textClose:[22, 23, "]"] referenceOpen:[23, 24, "["] reference:[24, 27, "ref"] referenceClose:[27, 28, "]"]
      Text[16, 22] chars:[16, 22, "^image"]
    Text[28, 29] chars:[28, 29, " "]
    WikiLink[29, 44] linkOpen:[29, 31, "[["] link:[31, 42, "^wiki image"] pageRef:[31, 42, "^wiki image"] linkClose:[42, 44, "]]"]
      Text[31, 42] chars:[31, 42, "^wiki … image"]
````````````````````````````````


```````````````````````````````` example(WikiImages: 14) options(wiki-images, image-ext)
![[wiki image]] ![^image][ref] ![[^wiki image]]
.
<p><img src="wiki-image.png" alt="wiki image" /> [^image][ref] <img src="^wiki-image.png" alt="^wiki image" /></p>
.
Document[0, 47]
  Paragraph[0, 47]
    WikiImage[0, 15] linkOpen:[0, 3, "![["] link:[3, 13, "wiki image"] pageRef:[3, 13, "wiki image"] linkClose:[13, 15, "]]"]
      Text[3, 13] chars:[3, 13, "wiki image"]
    Text[15, 16] chars:[15, 16, " "]
    ImageRef[17, 30] textOpen:[17, 18, "["] text:[18, 24, "^image"] textClose:[24, 25, "]"] referenceOpen:[25, 26, "["] reference:[26, 29, "ref"] referenceClose:[29, 30, "]"]
      Text[18, 24] chars:[18, 24, "^image"]
    Text[30, 31] chars:[30, 31, " "]
    WikiImage[31, 47] linkOpen:[31, 34, "![["] link:[34, 45, "^wiki image"] pageRef:[34, 45, "^wiki image"] linkClose:[45, 47, "]]"]
      Text[34, 45] chars:[34, 45, "^wiki … image"]
````````````````````````````````


Custom prefix

```````````````````````````````` example(WikiImages: 15) options(wiki-images, image-prefix)
[[wiki image]] [^image][ref] [[^wiki image]]
.
<p><a href="wiki-image">wiki image</a> [^image][ref] <a href="^wiki-image">^wiki image</a></p>
.
Document[0, 44]
  Paragraph[0, 44]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki image"] pageRef:[2, 12, "wiki image"] linkClose:[12, 14, "]]"]
      Text[2, 12] chars:[2, 12, "wiki image"]
    Text[14, 15] chars:[14, 15, " "]
    LinkRef[15, 28] textOpen:[15, 16, "["] text:[16, 22, "^image"] textClose:[22, 23, "]"] referenceOpen:[23, 24, "["] reference:[24, 27, "ref"] referenceClose:[27, 28, "]"]
      Text[16, 22] chars:[16, 22, "^image"]
    Text[28, 29] chars:[28, 29, " "]
    WikiLink[29, 44] linkOpen:[29, 31, "[["] link:[31, 42, "^wiki image"] pageRef:[31, 42, "^wiki image"] linkClose:[42, 44, "]]"]
      Text[31, 42] chars:[31, 42, "^wiki … image"]
````````````````````````````````


Custom prefix

```````````````````````````````` example(WikiImages: 16) options(wiki-images, image-prefix)
![[wiki image]] ![^image][ref] ![[^wiki image]]
.
<p><img src="/images/wiki-image" alt="wiki image" /> [^image][ref] <img src="/images/^wiki-image" alt="^wiki image" /></p>
.
Document[0, 47]
  Paragraph[0, 47]
    WikiImage[0, 15] linkOpen:[0, 3, "![["] link:[3, 13, "wiki image"] pageRef:[3, 13, "wiki image"] linkClose:[13, 15, "]]"]
      Text[3, 13] chars:[3, 13, "wiki image"]
    Text[15, 16] chars:[15, 16, " "]
    ImageRef[17, 30] textOpen:[17, 18, "["] text:[18, 24, "^image"] textClose:[24, 25, "]"] referenceOpen:[25, 26, "["] reference:[26, 29, "ref"] referenceClose:[29, 30, "]"]
      Text[18, 24] chars:[18, 24, "^image"]
    Text[30, 31] chars:[30, 31, " "]
    WikiImage[31, 47] linkOpen:[31, 34, "![["] link:[34, 45, "^wiki image"] pageRef:[34, 45, "^wiki image"] linkClose:[45, 47, "]]"]
      Text[34, 45] chars:[34, 45, "^wiki … image"]
````````````````````````````````


With empty anchor ref

```````````````````````````````` example(WikiImages: 17) options(wiki-images)
![[wiki image#]] 
.
<p><img src="wiki-image#" alt="wiki image#" /></p>
.
Document[0, 17]
  Paragraph[0, 17]
    WikiImage[0, 16] linkOpen:[0, 3, "![["] link:[3, 14, "wiki image#"] pageRef:[3, 14, "wiki image#"] linkClose:[14, 16, "]]"]
      Text[3, 14] chars:[3, 14, "wiki  … mage#"]
````````````````````````````````


With Anchor ref

```````````````````````````````` example(WikiImages: 18) options(wiki-images)
![[wiki image#anchor-ref]] 
.
<p><img src="wiki-image#anchor-ref" alt="wiki image#anchor-ref" /></p>
.
Document[0, 27]
  Paragraph[0, 27]
    WikiImage[0, 26] linkOpen:[0, 3, "![["] link:[3, 24, "wiki image#anchor-ref"] pageRef:[3, 24, "wiki image#anchor-ref"] linkClose:[24, 26, "]]"]
      Text[3, 24] chars:[3, 24, "wiki  … r-ref"]
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example(WikiImages: 19) options(wiki-images)
[[alt text|wiki image#]] 
.
<p><a href="wiki-image#">alt text</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[11, 22, "wiki image#"] pageRef:[11, 21, "wiki image"] anchorMarker:[21, 22, "#"] anchorRef:[22, 22] textSep:[10, 11, "|"] text:[2, 10, "alt text"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "alt t … mage#"]
````````````````````````````````


With text, anchor ref

```````````````````````````````` example(WikiImages: 20) options(wiki-images)
[[alt text|wiki image#anchor-ref]] 
.
<p><a href="wiki-image#anchor-ref">alt text</a></p>
.
Document[0, 35]
  Paragraph[0, 35]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] link:[11, 32, "wiki image#anchor-ref"] pageRef:[11, 21, "wiki image"] anchorMarker:[21, 22, "#"] anchorRef:[22, 32, "anchor-ref"] textSep:[10, 11, "|"] text:[2, 10, "alt text"] linkClose:[32, 34, "]]"]
      Text[2, 32] chars:[2, 32, "alt t … r-ref"]
````````````````````````````````


Links first, with text, empty anchor ref

```````````````````````````````` example(WikiImages: 21) options(wiki-images, links-first)
[[wiki image#|alt text]] 
.
<p><a href="wiki-image#">alt text</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] text:[14, 22, "alt text"] textSep:[13, 14, "|"] link:[2, 13, "wiki image#"] pageRef:[2, 12, "wiki image"] anchorMarker:[12, 13, "#"] anchorRef:[13, 13] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  …  text"]
````````````````````````````````


Links first, with text, anchor ref

```````````````````````````````` example(WikiImages: 22) options(wiki-images, links-first)
[[wiki image#anchor-ref|alt text]] 
.
<p><a href="wiki-image#anchor-ref">alt text</a></p>
.
Document[0, 35]
  Paragraph[0, 35]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] text:[24, 32, "alt text"] textSep:[23, 24, "|"] link:[2, 23, "wiki image#anchor-ref"] pageRef:[2, 12, "wiki image"] anchorMarker:[12, 13, "#"] anchorRef:[13, 23, "anchor-ref"] linkClose:[32, 34, "]]"]
      Text[2, 32] chars:[2, 32, "wiki  …  text"]
````````````````````````````````


Custom extension with empty Anchor ref

```````````````````````````````` example(WikiImages: 23) options(wiki-images, image-ext)
![[wiki image#]] 
.
<p><img src="wiki-image#.png" alt="wiki image#" /></p>
.
Document[0, 17]
  Paragraph[0, 17]
    WikiImage[0, 16] linkOpen:[0, 3, "![["] link:[3, 14, "wiki image#"] pageRef:[3, 14, "wiki image#"] linkClose:[14, 16, "]]"]
      Text[3, 14] chars:[3, 14, "wiki  … mage#"]
````````````````````````````````


Custom extension with anchor ref

```````````````````````````````` example(WikiImages: 24) options(wiki-images, image-ext)
![[wiki image#anchor-ref]] 
.
<p><img src="wiki-image#anchor-ref.png" alt="wiki image#anchor-ref" /></p>
.
Document[0, 27]
  Paragraph[0, 27]
    WikiImage[0, 26] linkOpen:[0, 3, "![["] link:[3, 24, "wiki image#anchor-ref"] pageRef:[3, 24, "wiki image#anchor-ref"] linkClose:[24, 26, "]]"]
      Text[3, 24] chars:[3, 24, "wiki  … r-ref"]
````````````````````````````````


Custom prefix with empty anchor ref

```````````````````````````````` example(WikiImages: 25) options(wiki-images, image-prefix)
![[wiki image#]]
.
<p><img src="/images/wiki-image#" alt="wiki image#" /></p>
.
Document[0, 16]
  Paragraph[0, 16]
    WikiImage[0, 16] linkOpen:[0, 3, "![["] link:[3, 14, "wiki image#"] pageRef:[3, 14, "wiki image#"] linkClose:[14, 16, "]]"]
      Text[3, 14] chars:[3, 14, "wiki  … mage#"]
````````````````````````````````


Custom prefix with anchor ref

```````````````````````````````` example(WikiImages: 26) options(wiki-images, image-prefix)
![[wiki image#anchor-ref]]
.
<p><img src="/images/wiki-image#anchor-ref" alt="wiki image#anchor-ref" /></p>
.
Document[0, 26]
  Paragraph[0, 26]
    WikiImage[0, 26] linkOpen:[0, 3, "![["] link:[3, 24, "wiki image#anchor-ref"] pageRef:[3, 24, "wiki image#anchor-ref"] linkClose:[24, 26, "]]"]
      Text[3, 24] chars:[3, 24, "wiki  … r-ref"]
````````````````````````````````


## WikiImages Source Position Attribute

simple wiki image

```````````````````````````````` example(WikiImages Source Position Attribute: 1) options(wiki-images, src-pos)
![[wiki image]]
.
<p md-pos="0-15"><img src="wiki-image" alt="wiki image" md-pos="0-15" /></p>
.
Document[0, 15]
  Paragraph[0, 15]
    WikiImage[0, 15] linkOpen:[0, 3, "![["] link:[3, 13, "wiki image"] pageRef:[3, 13, "wiki image"] linkClose:[13, 15, "]]"]
      Text[3, 13] chars:[3, 13, "wiki image"]
````````````````````````````````


wiki image with text

```````````````````````````````` example(WikiImages Source Position Attribute: 2) options(wiki-images, src-pos)
![[alt text|wiki image]]
.
<p md-pos="0-24"><img src="wiki-image" alt="alt text" md-pos="0-24" /></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiImage[0, 24] linkOpen:[0, 3, "![["] link:[12, 22, "wiki image"] pageRef:[12, 22, "wiki image"] textSep:[11, 12, "|"] text:[3, 11, "alt text"] linkClose:[22, 24, "]]"]
      Text[3, 22] chars:[3, 22, "alt t … image"]
````````````````````````````````


wiki image with text, images first option

```````````````````````````````` example(WikiImages Source Position Attribute: 3) options(wiki-images, src-pos, links-first)
![[wiki image|alt text]]
.
<p md-pos="0-24"><img src="wiki-image" alt="alt text" md-pos="0-24" /></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiImage[0, 24] linkOpen:[0, 3, "![["] text:[14, 22, "alt text"] textSep:[13, 14, "|"] link:[3, 13, "wiki image"] pageRef:[3, 13, "wiki image"] linkClose:[22, 24, "]]"]
      Text[3, 22] chars:[3, 22, "wiki  …  text"]
````````````````````````````````


