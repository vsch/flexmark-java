---
title: WikiLinks Extension JIRA Spec
author: Vladimir Schneider
version: 0.1
date: '2016-09-24'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)' 
...

---

## WikiLinks

Converts wikilink of the forms: [[link]], [[link|text]] and [[text|link]] to links in the HTML
page.

simple wiki link

```````````````````````````````` example WikiLinks: 1
[[wiki link]]
.
[wiki link|wiki-link]

.
Document[0, 14]
  Paragraph[0, 14]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[11, 13, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
````````````````````````````````


wiki link with text

```````````````````````````````` example WikiLinks: 2
[[wiki text|wiki link]]
.
[wiki text|wiki-link]

.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 23] linkOpen:[0, 2, "[["] link:[12, 21, "wiki link"] pageRef:[12, 21, "wiki link"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[21, 23, "]]"]
      Text[2, 21] chars:[2, 21, "wiki  …  link"]
````````````````````````````````


wiki link with text, links first option

```````````````````````````````` example(WikiLinks: 3) options(links-first)
[[wiki link|wiki text]]
.
[wiki text|wiki-link]

.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 23] linkOpen:[0, 2, "[["] text:[12, 21, "wiki text"] textSep:[11, 12, "|"] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[21, 23, "]]"]
      Text[2, 21] chars:[2, 21, "wiki  …  text"]
````````````````````````````````


With empty anchor ref

```````````````````````````````` example WikiLinks: 4
[[wiki link#]] 
.
[wiki link|wiki-link#]

.
Document[0, 16]
  Paragraph[0, 16]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 12] linkClose:[12, 14, "]]"]
      Text[2, 12] chars:[2, 12, "wiki link#"]
````````````````````````````````


With Anchor ref

```````````````````````````````` example WikiLinks: 5
[[wiki link#anchor-ref]] 
.
[wiki link|wiki-link#anchor-ref]

.
Document[0, 26]
  Paragraph[0, 26]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … r-ref"]
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example WikiLinks: 6
[[wiki text|wiki link#]] 
.
[wiki text|wiki-link#]

.
Document[0, 26]
  Paragraph[0, 26]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[12, 22, "wiki link#"] pageRef:[12, 21, "wiki link"] anchorMarker:[21, 22, "#"] anchorRef:[22, 22] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … link#"]
````````````````````````````````


With text, anchor ref

```````````````````````````````` example WikiLinks: 7
[[wiki text|wiki link#anchor-ref]] 
.
[wiki text|wiki-link#anchor-ref]

.
Document[0, 36]
  Paragraph[0, 36]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] link:[12, 32, "wiki link#anchor-ref"] pageRef:[12, 21, "wiki link"] anchorMarker:[21, 22, "#"] anchorRef:[22, 32, "anchor-ref"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[32, 34, "]]"]
      Text[2, 32] chars:[2, 32, "wiki  … r-ref"]
````````````````````````````````


