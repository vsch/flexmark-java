---
title: WikiLinks Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)' 
...

---

## WikiLinks

Converts wikilink of the forms: [[link]], [[link|text]] and
[[text|link]] to links in the HTML page.  

no spaces between brackets

```````````````````````````````` example WikiLinks: 1
[ [not wiki link]]
.
<p>[ [not wiki link]]</p>
.
Document[0, 19]
  Paragraph[0, 19]
    Text[0, 2] chars:[0, 2, "[ "]
    LinkRef[2, 17] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[2, 3, "["] reference:[3, 16, "not wiki link"] referenceClose:[16, 17, "]"]
      Text[3, 16] chars:[3, 16, "not w"..." link"]
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
    LinkRef[1, 16] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[1, 2, "["] reference:[2, 15, "not wiki link"] referenceClose:[15, 16, "]"]
      Text[2, 15] chars:[2, 15, "not w"..." link"]
    Text[16, 18] chars:[16, 18, " ]"]
````````````````````````````````


simple wiki link

```````````````````````````````` example WikiLinks: 3
[[wiki link]]
.
<p><a href="wiki-link.md">wiki link</a></p>
.
Document[0, 14]
  Paragraph[0, 14]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[11, 13, "]]"]
      Text[1, 12] chars:[1, 12, "[wiki"..."link]"]
````````````````````````````````


wiki link with text

```````````````````````````````` example WikiLinks: 4
[[wiki text|wiki link]]
.
<p><a href="wiki-link.md">wiki text</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 23] linkOpen:[0, 2, "[["] link:[12, 21, "wiki link"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[21, 23, "]]"]
      Text[1, 22] chars:[1, 22, "[wiki"..."link]"]
````````````````````````````````


wiki link with text, links first option

```````````````````````````````` example(WikiLinks: 5) options(links-first)
[[wiki link|wiki text]]
.
<p><a href="wiki-link.md">wiki text</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 23] linkOpen:[0, 2, "[["] text:[12, 21, "wiki text"] textSep:[11, 12, "|"] link:[2, 11, "wiki link"] linkClose:[21, 23, "]]"]
      Text[1, 22] chars:[1, 22, "[wiki"..."text]"]
````````````````````````````````


simple wiki link with ! before

```````````````````````````````` example WikiLinks: 6
![[wiki link]]
.
<p>!<a href="wiki-link.md">wiki link</a></p>
.
Document[0, 15]
  Paragraph[0, 15]
    Text[0, 1] chars:[0, 1, "!"]
    WikiLink[1, 14] linkOpen:[1, 3, "[["] link:[3, 12, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[12, 14, "]]"]
      Text[2, 13] chars:[2, 13, "[wiki"..."link]"]
````````````````````````````````


wiki link with text with ! before

```````````````````````````````` example WikiLinks: 7
![[wiki text|wiki link]]
.
<p>!<a href="wiki-link.md">wiki text</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    Text[0, 1] chars:[0, 1, "!"]
    WikiLink[1, 24] linkOpen:[1, 3, "[["] link:[13, 22, "wiki link"] textSep:[12, 13, "|"] text:[3, 12, "wiki text"] linkClose:[22, 24, "]]"]
      Text[2, 23] chars:[2, 23, "[wiki"..."link]"]
````````````````````````````````


reference following will be a reference, even if not defined

```````````````````````````````` example WikiLinks: 8
[[wiki link]][ref]
.
<p><a href="wiki-link.md">wiki link</a>[ref]</p>
.
Document[0, 19]
  Paragraph[0, 19]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[11, 13, "]]"]
      Text[1, 12] chars:[1, 12, "[wiki"..."link]"]
    LinkRef[13, 18] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[13, 14, "["] reference:[14, 17, "ref"] referenceClose:[17, 18, "]"]
      Text[14, 17] chars:[14, 17, "ref"]
````````````````````````````````


reference following will be a reference

```````````````````````````````` example WikiLinks: 9
[[wiki link]][ref]

[ref]: /url
.
<p><a href="wiki-link.md">wiki link</a><a href="/url">ref</a></p>
.
Document[0, 32]
  Paragraph[0, 19]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[11, 13, "]]"]
      Text[1, 12] chars:[1, 12, "[wiki"..."link]"]
    LinkRef[13, 18] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[13, 14, "["] reference:[14, 17, "ref"] referenceClose:[17, 18, "]"]
      Text[14, 17] chars:[14, 17, "ref"]
  Reference[20, 31] refOpen:[20, 21, "["] ref:[21, 24, "ref"] refClose:[24, 26, "]:"] urlOpen:[0, 0] url:[27, 31, "/url"] urlClose:[0, 0] titleOpen:[0, 0] title:[0, 0] titleClose:[0, 0]
````````````````````````````````


dummy reference following will be an empty reference

```````````````````````````````` example WikiLinks: 10
[[wiki link]][]
.
<p><a href="wiki-link.md">wiki link</a>[]</p>
.
Document[0, 16]
  Paragraph[0, 16]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[11, 13, "]]"]
      Text[1, 12] chars:[1, 12, "[wiki"..."link]"]
    LinkRef[13, 15] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[13, 14, "["] reference:[14, 14] referenceClose:[14, 15, "]"]
````````````````````````````````


reference inside is not a wiki link but a link ref with brackets around
it

```````````````````````````````` example WikiLinks: 11
[[not wiki link][ref]]
.
<p>[[not wiki link][ref]]</p>
.
Document[0, 23]
  Paragraph[0, 23]
    Text[0, 1] chars:[0, 1, "["]
    LinkRef[1, 21] textOpen:[1, 2, "["] text:[2, 15, "not wiki link"] textClose:[15, 16, "]"] referenceOpen:[16, 17, "["] reference:[17, 20, "ref"] referenceClose:[20, 21, "]"]
      Text[2, 15] chars:[2, 15, "not w"..." link"]
    Text[21, 22] chars:[21, 22, "]"]
````````````````````````````````


dummy reference inside is not a wiki link but a link ref with brackets
around it

```````````````````````````````` example WikiLinks: 12
[[not wiki link][]]
.
<p>[[not wiki link]]</p>
.
Document[0, 20]
  Paragraph[0, 20]
    Text[0, 1] chars:[0, 1, "["]
    LinkRef[1, 16] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[1, 2, "["] reference:[2, 15, "not wiki link"] referenceClose:[15, 16, "]"]
      Text[2, 15] chars:[2, 15, "not w"..." link"]
    Text[18, 19] chars:[18, 19, "]"]
````````````````````````````````


```````````````````````````````` example WikiLinks: 13
[[wiki link]] [^link][ref] [[^wiki link]]
.
<p><a href="wiki-link.md">wiki link</a> [^link][ref] <a href="^wiki-link.md">^wiki link</a></p>
.
Document[0, 42]
  Paragraph[0, 42]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[11, 13, "]]"]
      Text[1, 12] chars:[1, 12, "[wiki"..."link]"]
    Text[13, 14] chars:[13, 14, " "]
    LinkRef[14, 26] textOpen:[14, 15, "["] text:[15, 20, "^link"] textClose:[20, 21, "]"] referenceOpen:[21, 22, "["] reference:[22, 25, "ref"] referenceClose:[25, 26, "]"]
      Text[15, 20] chars:[15, 20, "^link"]
    Text[26, 27] chars:[26, 27, " "]
    WikiLink[27, 41] linkOpen:[27, 29, "[["] link:[29, 39, "^wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[39, 41, "]]"]
      Text[28, 40] chars:[28, 40, "[^wik"..."link]"]
````````````````````````````````


Exclamation before is just text

```````````````````````````````` example WikiLinks: 14
![[wiki link]] [^link][ref] [[^wiki link]] [[wiki]][ref]
.
<p>!<a href="wiki-link.md">wiki link</a> [^link][ref] <a href="^wiki-link.md">^wiki link</a> <a href="wiki.md">wiki</a>[ref]</p>
.
Document[0, 57]
  Paragraph[0, 57]
    Text[0, 1] chars:[0, 1, "!"]
    WikiLink[1, 14] linkOpen:[1, 3, "[["] link:[3, 12, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[12, 14, "]]"]
      Text[2, 13] chars:[2, 13, "[wiki"..."link]"]
    Text[14, 15] chars:[14, 15, " "]
    LinkRef[15, 27] textOpen:[15, 16, "["] text:[16, 21, "^link"] textClose:[21, 22, "]"] referenceOpen:[22, 23, "["] reference:[23, 26, "ref"] referenceClose:[26, 27, "]"]
      Text[16, 21] chars:[16, 21, "^link"]
    Text[27, 28] chars:[27, 28, " "]
    WikiLink[28, 42] linkOpen:[28, 30, "[["] link:[30, 40, "^wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[40, 42, "]]"]
      Text[29, 41] chars:[29, 41, "[^wik"..."link]"]
    Text[42, 43] chars:[42, 43, " "]
    WikiLink[43, 51] linkOpen:[43, 45, "[["] link:[45, 49, "wiki"] textSep:[0, 0] text:[0, 0] linkClose:[49, 51, "]]"]
      Text[44, 50] chars:[44, 50, "[wiki]"]
    LinkRef[51, 56] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[51, 52, "["] reference:[52, 55, "ref"] referenceClose:[55, 56, "]"]
      Text[52, 55] chars:[52, 55, "ref"]
````````````````````````````````

Custom extension

```````````````````````````````` example(WikiLinks: 15) options(custom-ext)
[[wiki link]] [^link][ref] [[^wiki link]]
.
<p><a href="wiki-link">wiki link</a> [^link][ref] <a href="^wiki-link">^wiki link</a></p>
.
Document[0, 42]
  Paragraph[0, 42]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[11, 13, "]]"]
      Text[1, 12] chars:[1, 12, "[wiki"..."link]"]
    Text[13, 14] chars:[13, 14, " "]
    LinkRef[14, 26] textOpen:[14, 15, "["] text:[15, 20, "^link"] textClose:[20, 21, "]"] referenceOpen:[21, 22, "["] reference:[22, 25, "ref"] referenceClose:[25, 26, "]"]
      Text[15, 20] chars:[15, 20, "^link"]
    Text[26, 27] chars:[26, 27, " "]
    WikiLink[27, 41] linkOpen:[27, 29, "[["] link:[29, 39, "^wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[39, 41, "]]"]
      Text[28, 40] chars:[28, 40, "[^wik"..."link]"]
````````````````````````````````


Custom prefix

```````````````````````````````` example(WikiLinks: 16) options(custom-prefix)
[[wiki link]] [^link][ref] [[^wiki link]]
.
<p><a href="/prefix/wiki-link.md">wiki link</a> [^link][ref] <a href="/prefix/^wiki-link.md">^wiki link</a></p>
.
Document[0, 42]
  Paragraph[0, 42]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[11, 13, "]]"]
      Text[1, 12] chars:[1, 12, "[wiki"..."link]"]
    Text[13, 14] chars:[13, 14, " "]
    LinkRef[14, 26] textOpen:[14, 15, "["] text:[15, 20, "^link"] textClose:[20, 21, "]"] referenceOpen:[21, 22, "["] reference:[22, 25, "ref"] referenceClose:[25, 26, "]"]
      Text[15, 20] chars:[15, 20, "^link"]
    Text[26, 27] chars:[26, 27, " "]
    WikiLink[27, 41] linkOpen:[27, 29, "[["] link:[29, 39, "^wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[39, 41, "]]"]
      Text[28, 40] chars:[28, 40, "[^wik"..."link]"]
````````````````````````````````


With empty anchor ref

```````````````````````````````` example WikiLinks: 14
[[wiki link#]] 
.
<p><a href="wiki-link.md#">wiki link</a></p>
.
Document[0, 57]
  Paragraph[0, 57]
    Text[0, 1] chars:[0, 1, "!"]
    WikiLink[1, 14] linkOpen:[1, 3, "[["] link:[3, 12, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[12, 14, "]]"]
      Text[2, 13] chars:[2, 13, "[wiki"..."link]"]
````````````````````````````````

With Anchor ref

```````````````````````````````` example WikiLinks: 14
[[wiki link#anchor-ref]] 
.
<p><a href="wiki-link.md#anchor-ref">wiki link</a></p>
.
Document[0, 57]
  Paragraph[0, 57]
    Text[0, 1] chars:[0, 1, "!"]
    WikiLink[1, 14] linkOpen:[1, 3, "[["] link:[3, 12, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[12, 14, "]]"]
      Text[2, 13] chars:[2, 13, "[wiki"..."link]"]
````````````````````````````````

With text, empty anchor ref

```````````````````````````````` example WikiLinks: 14
[[wiki text|wiki link#]] 
.
<p><a href="wiki-link.md#">wiki text</a></p>
.
Document[0, 57]
  Paragraph[0, 57]
    Text[0, 1] chars:[0, 1, "!"]
    WikiLink[1, 14] linkOpen:[1, 3, "[["] link:[3, 12, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[12, 14, "]]"]
      Text[2, 13] chars:[2, 13, "[wiki"..."link]"]
````````````````````````````````

With text, anchor ref

```````````````````````````````` example WikiLinks: 14
[[wiki text|wiki link#anchor-ref]] 
.
<p><a href="wiki-link.md#anchor-ref">wiki text</a></p>
.
Document[0, 57]
  Paragraph[0, 57]
    Text[0, 1] chars:[0, 1, "!"]
    WikiLink[1, 14] linkOpen:[1, 3, "[["] link:[3, 12, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[12, 14, "]]"]
      Text[2, 13] chars:[2, 13, "[wiki"..."link]"]
````````````````````````````````

Links first, with text, empty anchor ref

```````````````````````````````` example WikiLinks: 14 options(links-first)
[[wiki link#|wiki text]] 
.
<p><a href="wiki-link.md#">wiki text</a></p>
.
Document[0, 57]
  Paragraph[0, 57]
    Text[0, 1] chars:[0, 1, "!"]
    WikiLink[1, 14] linkOpen:[1, 3, "[["] link:[3, 12, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[12, 14, "]]"]
      Text[2, 13] chars:[2, 13, "[wiki"..."link]"]
````````````````````````````````

Links first, with text, anchor ref

```````````````````````````````` example WikiLinks: 14 options(links-first)
[[wiki link#anchor-ref|wiki text]] 
.
<p><a href="wiki-link.md#anchor-ref">wiki text</a></p>
.
Document[0, 57]
  Paragraph[0, 57]
    Text[0, 1] chars:[0, 1, "!"]
    WikiLink[1, 14] linkOpen:[1, 3, "[["] link:[3, 12, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[12, 14, "]]"]
      Text[2, 13] chars:[2, 13, "[wiki"..."link]"]
````````````````````````````````

Custom extension with empty Anchor ref

```````````````````````````````` example(WikiLinks: 15) options(custom-ext)
[[wiki link#]] 
.
<p><a href="wiki-link#">wiki link</a></p>
.
Document[0, 42]
  Paragraph[0, 42]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[11, 13, "]]"]
      Text[1, 12] chars:[1, 12, "[wiki"..."link]"]
    Text[13, 14] chars:[13, 14, " "]
    LinkRef[14, 26] textOpen:[14, 15, "["] text:[15, 20, "^link"] textClose:[20, 21, "]"] referenceOpen:[21, 22, "["] reference:[22, 25, "ref"] referenceClose:[25, 26, "]"]
      Text[15, 20] chars:[15, 20, "^link"]
    Text[26, 27] chars:[26, 27, " "]
    WikiLink[27, 41] linkOpen:[27, 29, "[["] link:[29, 39, "^wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[39, 41, "]]"]
      Text[28, 40] chars:[28, 40, "[^wik"..."link]"]
````````````````````````````````


Custom extension with anchor ref

```````````````````````````````` example(WikiLinks: 15) options(custom-ext)
[[wiki link#anchor-ref]] 
.
<p><a href="wiki-link#anchor-ref">wiki link</a></p>
.
Document[0, 42]
  Paragraph[0, 42]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[11, 13, "]]"]
      Text[1, 12] chars:[1, 12, "[wiki"..."link]"]
    Text[13, 14] chars:[13, 14, " "]
    LinkRef[14, 26] textOpen:[14, 15, "["] text:[15, 20, "^link"] textClose:[20, 21, "]"] referenceOpen:[21, 22, "["] reference:[22, 25, "ref"] referenceClose:[25, 26, "]"]
      Text[15, 20] chars:[15, 20, "^link"]
    Text[26, 27] chars:[26, 27, " "]
    WikiLink[27, 41] linkOpen:[27, 29, "[["] link:[29, 39, "^wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[39, 41, "]]"]
      Text[28, 40] chars:[28, 40, "[^wik"..."link]"]
````````````````````````````````


Custom prefix with empty anchor ref

```````````````````````````````` example(WikiLinks: 16) options(custom-prefix)
[[wiki link#]]
.
<p><a href="/prefix/wiki-link.md#">wiki link</a></p>
.
Document[0, 42]
  Paragraph[0, 42]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[11, 13, "]]"]
      Text[1, 12] chars:[1, 12, "[wiki"..."link]"]
    Text[13, 14] chars:[13, 14, " "]
    LinkRef[14, 26] textOpen:[14, 15, "["] text:[15, 20, "^link"] textClose:[20, 21, "]"] referenceOpen:[21, 22, "["] reference:[22, 25, "ref"] referenceClose:[25, 26, "]"]
      Text[15, 20] chars:[15, 20, "^link"]
    Text[26, 27] chars:[26, 27, " "]
    WikiLink[27, 41] linkOpen:[27, 29, "[["] link:[29, 39, "^wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[39, 41, "]]"]
      Text[28, 40] chars:[28, 40, "[^wik"..."link]"]
````````````````````````````````


Custom prefix with anchor ref

```````````````````````````````` example(WikiLinks: 16) options(custom-prefix)
[[wiki link#anchor-ref]]
.
<p><a href="/prefix/wiki-link.md#anchor-ref">wiki link</a></p>
.
Document[0, 42]
  Paragraph[0, 42]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[11, 13, "]]"]
      Text[1, 12] chars:[1, 12, "[wiki"..."link]"]
    Text[13, 14] chars:[13, 14, " "]
    LinkRef[14, 26] textOpen:[14, 15, "["] text:[15, 20, "^link"] textClose:[20, 21, "]"] referenceOpen:[21, 22, "["] reference:[22, 25, "ref"] referenceClose:[25, 26, "]"]
      Text[15, 20] chars:[15, 20, "^link"]
    Text[26, 27] chars:[26, 27, " "]
    WikiLink[27, 41] linkOpen:[27, 29, "[["] link:[29, 39, "^wiki link"] textSep:[0, 0] text:[0, 0] linkClose:[39, 41, "]]"]
      Text[28, 40] chars:[28, 40, "[^wik"..."link]"]
````````````````````````````````


