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


simple wiki link with anchor ref

```````````````````````````````` example WikiLinks: 4
[[wiki link#anchor-ref]]
.
<p><a href="wiki-link#anchor-ref">wiki link#anchor-ref</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 22, "wiki link#anchor-ref"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … r-ref"]
````````````````````````````````


simple wiki link with anchor ref

```````````````````````````````` example(WikiLinks: 5) options(allow-anchors)
[[wiki link#anchor-ref]]
.
<p><a href="wiki-link#anchor-ref">wiki link</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] text:[2, 11, "wiki link"] linkClose:[22, 24, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
````````````````````````````````


simple wiki link with anchor-ref

```````````````````````````````` example(WikiLinks: 6) options(allow-anchors, allow-anchor-escape)
[[wiki link#anchor-ref]]
.
<p><a href="wiki-link#anchor-ref">wiki link</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] text:[2, 11, "wiki link"] linkClose:[22, 24, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
````````````````````````````````


simple wiki link with escaped anchor-ref

```````````````````````````````` example WikiLinks: 7
[[wiki link#anchor-ref]]
[[wiki link\#anchor-ref]]
[[wiki link\\#anchor-ref]]
[[wiki link\\\#anchor-ref]]
.
<p><a href="wiki-link#anchor-ref">wiki link#anchor-ref</a>
<a href="wiki-link#anchor-ref">wiki link#anchor-ref</a>
<a href="wiki-link\#anchor-ref">wiki link\#anchor-ref</a>
<a href="wiki-link\#anchor-ref">wiki link\#anchor-ref</a></p>
.
Document[0, 106]
  Paragraph[0, 106]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 22, "wiki link#anchor-ref"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … r-ref"]
    SoftLineBreak[24, 25]
    WikiLink[25, 50] linkOpen:[25, 27, "[["] link:[27, 48, "wiki link\#anchor-ref"] pageRef:[27, 48, "wiki link\#anchor-ref"] linkClose:[48, 50, "]]"]
      Text[27, 48] chars:[27, 48, "wiki  … r-ref"]
    SoftLineBreak[50, 51]
    WikiLink[51, 77] linkOpen:[51, 53, "[["] link:[53, 75, "wiki link\\#anchor-ref"] pageRef:[53, 75, "wiki link\\#anchor-ref"] linkClose:[75, 77, "]]"]
      Text[53, 75] chars:[53, 75, "wiki  … r-ref"]
    SoftLineBreak[77, 78]
    WikiLink[78, 105] linkOpen:[78, 80, "[["] link:[80, 103, "wiki link\\\#anchor-ref"] pageRef:[80, 103, "wiki link\\\#anchor-ref"] linkClose:[103, 105, "]]"]
      Text[80, 103] chars:[80, 103, "wiki  … r-ref"]
````````````````````````````````


simple wiki link with escaped anchor-ref

```````````````````````````````` example(WikiLinks: 8) options(allow-anchors)
[[wiki link#anchor-ref]]
[[wiki link\#anchor-ref]]
[[wiki link\\#anchor-ref]]
[[wiki link\\\#anchor-ref]]
.
<p><a href="wiki-link#anchor-ref">wiki link</a>
<a href="wiki-link#anchor-ref">wiki link\</a>
<a href="wiki-link\#anchor-ref">wiki link\</a>
<a href="wiki-link\#anchor-ref">wiki link\\</a></p>
.
Document[0, 105]
  Paragraph[0, 105]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] text:[2, 11, "wiki link"] linkClose:[22, 24, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
    SoftLineBreak[24, 25]
    WikiLink[25, 50] linkOpen:[25, 27, "[["] link:[27, 48, "wiki link\#anchor-ref"] pageRef:[27, 37, "wiki link\"] anchorMarker:[37, 38, "#"] anchorRef:[38, 48, "anchor-ref"] text:[27, 37, "wiki link\"] linkClose:[48, 50, "]]"]
      Text[27, 37] chars:[27, 37, "wiki link\"]
    SoftLineBreak[50, 51]
    WikiLink[51, 77] linkOpen:[51, 53, "[["] link:[53, 75, "wiki link\\#anchor-ref"] pageRef:[53, 64, "wiki link\\"] anchorMarker:[64, 65, "#"] anchorRef:[65, 75, "anchor-ref"] text:[53, 64, "wiki link\\"] linkClose:[75, 77, "]]"]
      Text[53, 64] chars:[53, 64, "wiki  … ink\\"]
    SoftLineBreak[77, 78]
    WikiLink[78, 105] linkOpen:[78, 80, "[["] link:[80, 103, "wiki link\\\#anchor-ref"] pageRef:[80, 92, "wiki link\\\"] anchorMarker:[92, 93, "#"] anchorRef:[93, 103, "anchor-ref"] text:[80, 92, "wiki link\\\"] linkClose:[103, 105, "]]"]
      Text[80, 92] chars:[80, 92, "wiki  … nk\\\"]
````````````````````````````````


simple wiki link with escaped anchor-ref

```````````````````````````````` example(WikiLinks: 9) options(allow-anchors, allow-anchor-escape)
[[wiki link#anchor-ref]]
[[wiki link\#anchor-ref]]
[[wiki link\\#anchor-ref]]
[[wiki link\\\#anchor-ref]]
.
<p><a href="wiki-link#anchor-ref">wiki link</a>
<a href="wiki-link#anchor-ref">wiki link#anchor-ref</a>
<a href="wiki-link\#anchor-ref">wiki link\</a>
<a href="wiki-link\#anchor-ref">wiki link\#anchor-ref</a></p>
.
Document[0, 105]
  Paragraph[0, 105]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] text:[2, 11, "wiki link"] linkClose:[22, 24, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
    SoftLineBreak[24, 25]
    WikiLink[25, 50] linkOpen:[25, 27, "[["] link:[27, 48, "wiki link\#anchor-ref"] pageRef:[27, 48, "wiki link\#anchor-ref"] linkClose:[48, 50, "]]"]
      Text[27, 48] chars:[27, 48, "wiki  … r-ref"]
    SoftLineBreak[50, 51]
    WikiLink[51, 77] linkOpen:[51, 53, "[["] link:[53, 75, "wiki link\\#anchor-ref"] pageRef:[53, 64, "wiki link\\"] anchorMarker:[64, 65, "#"] anchorRef:[65, 75, "anchor-ref"] text:[53, 64, "wiki link\\"] linkClose:[75, 77, "]]"]
      Text[53, 64] chars:[53, 64, "wiki  … ink\\"]
    SoftLineBreak[77, 78]
    WikiLink[78, 105] linkOpen:[78, 80, "[["] link:[80, 103, "wiki link\\\#anchor-ref"] pageRef:[80, 103, "wiki link\\\#anchor-ref"] linkClose:[103, 105, "]]"]
      Text[80, 103] chars:[80, 103, "wiki  … r-ref"]
````````````````````````````````


wiki link with text

```````````````````````````````` example WikiLinks: 10
[[wiki text|wiki link]]
.
<p><a href="wiki-link">wiki text</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 23] linkOpen:[0, 2, "[["] link:[12, 21, "wiki link"] pageRef:[12, 21, "wiki link"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[21, 23, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
````````````````````````````````


wiki link with text, links first option

```````````````````````````````` example(WikiLinks: 11) options(links-first)
[[wiki link|wiki text]]
.
<p><a href="wiki-link">wiki text</a></p>
.
Document[0, 23]
  Paragraph[0, 23]
    WikiLink[0, 23] linkOpen:[0, 2, "[["] text:[12, 21, "wiki text"] textSep:[11, 12, "|"] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[21, 23, "]]"]
      Text[12, 21] chars:[12, 21, "wiki text"]
````````````````````````````````


wiki link with text

```````````````````````````````` example WikiLinks: 12
[[wiki text\|wiki link]]
.
<p><a href="wiki-link">wiki text\</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[13, 22, "wiki link"] pageRef:[13, 22, "wiki link"] textSep:[12, 13, "|"] text:[2, 12, "wiki text\"] linkClose:[22, 24, "]]"]
      Text[2, 12] chars:[2, 12, "wiki text\"]
````````````````````````````````


wiki link with text, links first option

```````````````````````````````` example(WikiLinks: 13) options(links-first)
[[wiki link\|wiki text]]
.
<p><a href="wiki-link\">wiki text</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] text:[13, 22, "wiki text"] textSep:[12, 13, "|"] link:[2, 12, "wiki link\"] pageRef:[2, 12, "wiki link\"] linkClose:[22, 24, "]]"]
      Text[13, 22] chars:[13, 22, "wiki text"]
````````````````````````````````


wiki link with text

```````````````````````````````` example(WikiLinks: 14) options(allow-pipe-escape)
[[wiki text\|wiki link]]
.
<p><a href="wiki-text|wiki-link">wiki text|wiki link</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki text\|wiki link"] pageRef:[2, 22, "wiki text\|wiki link"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  …  link"]
````````````````````````````````


wiki link with text, links first option

```````````````````````````````` example(WikiLinks: 15) options(links-first, allow-pipe-escape)
[[wiki link\|wiki text]]
.
<p><a href="wiki-link|wiki-text">wiki link|wiki text</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link\|wiki text"] pageRef:[2, 22, "wiki link\|wiki text"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  …  text"]
````````````````````````````````


wiki link with text, muliple pipes with escapes

```````````````````````````````` example WikiLinks: 16
[[wiki text|more text|wiki link]]
[[wiki text\|more text|wiki link]]
[[wiki text\\|more text|wiki link]]
[[wiki text\\\|more text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki text\|more text\|wiki link]]
.
<p><a href="more-text|wiki-link">wiki text</a>
<a href="more-text|wiki-link">wiki text\</a>
<a href="more-text|wiki-link">wiki text\</a>
<a href="more-text|wiki-link">wiki text\\</a>
<a href="more-text|wiki-link">wiki text</a>
<a href="more-text|wiki-link">wiki text\</a></p>
.
Document[0, 213]
  Paragraph[0, 213]
    WikiLink[0, 33] linkOpen:[0, 2, "[["] link:[12, 31, "more text|wiki link"] pageRef:[12, 31, "more text|wiki link"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[31, 33, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
    SoftLineBreak[33, 34]
    WikiLink[34, 68] linkOpen:[34, 36, "[["] link:[47, 66, "more text|wiki link"] pageRef:[47, 66, "more text|wiki link"] textSep:[46, 47, "|"] text:[36, 46, "wiki text\"] linkClose:[66, 68, "]]"]
      Text[36, 46] chars:[36, 46, "wiki text\"]
    SoftLineBreak[68, 69]
    WikiLink[69, 104] linkOpen:[69, 71, "[["] link:[83, 102, "more text|wiki link"] pageRef:[83, 102, "more text|wiki link"] textSep:[82, 83, "|"] text:[71, 82, "wiki text\\"] linkClose:[102, 104, "]]"]
      Text[71, 82] chars:[71, 82, "wiki  … ext\\"]
    SoftLineBreak[104, 105]
    WikiLink[105, 141] linkOpen:[105, 107, "[["] link:[120, 139, "more text|wiki link"] pageRef:[120, 139, "more text|wiki link"] textSep:[119, 120, "|"] text:[107, 119, "wiki text\\\"] linkClose:[139, 141, "]]"]
      Text[107, 119] chars:[107, 119, "wiki  … xt\\\"]
    SoftLineBreak[141, 142]
    WikiLink[142, 176] linkOpen:[142, 144, "[["] link:[154, 174, "more text\|wiki link"] pageRef:[154, 174, "more text\|wiki link"] textSep:[153, 154, "|"] text:[144, 153, "wiki text"] linkClose:[174, 176, "]]"]
      Text[144, 153] chars:[144, 153, "wiki text"]
    SoftLineBreak[176, 177]
    WikiLink[177, 212] linkOpen:[177, 179, "[["] link:[190, 210, "more text\|wiki link"] pageRef:[190, 210, "more text\|wiki link"] textSep:[189, 190, "|"] text:[179, 189, "wiki text\"] linkClose:[210, 212, "]]"]
      Text[179, 189] chars:[179, 189, "wiki text\"]
````````````````````````````````


wiki link with text, multiple pipes with escapes, links first option

```````````````````````````````` example(WikiLinks: 17) options(links-first)
[[wiki link|wiki text|more text]]
[[wiki link\|wiki text|more text]]
[[wiki text\\|more text|wiki link]]
[[wiki text\\\|more text|wiki link]]
[[wiki link|wiki text\|more text]]
[[wiki link\|wiki text\|more text]]
.
<p><a href="wiki-link|wiki-text">more text</a>
<a href="wiki-link|wiki-text">more text</a>
<a href="wiki-text\|more-text">wiki link</a>
<a href="wiki-text\|more-text">wiki link</a>
<a href="wiki-link|wiki-text\">more text</a>
<a href="wiki-link|wiki-text\">more text</a></p>
.
Document[0, 212]
  Paragraph[0, 212]
    WikiLink[0, 33] linkOpen:[0, 2, "[["] text:[22, 31, "more text"] textSep:[21, 22, "|"] link:[2, 21, "wiki link|wiki text"] pageRef:[2, 21, "wiki link|wiki text"] linkClose:[31, 33, "]]"]
      Text[22, 31] chars:[22, 31, "more text"]
    SoftLineBreak[33, 34]
    WikiLink[34, 68] linkOpen:[34, 36, "[["] text:[57, 66, "more text"] textSep:[56, 57, "|"] link:[36, 56, "wiki link\|wiki text"] pageRef:[36, 56, "wiki link\|wiki text"] linkClose:[66, 68, "]]"]
      Text[57, 66] chars:[57, 66, "more text"]
    SoftLineBreak[68, 69]
    WikiLink[69, 104] linkOpen:[69, 71, "[["] text:[93, 102, "wiki link"] textSep:[92, 93, "|"] link:[71, 92, "wiki text\\|more text"] pageRef:[71, 92, "wiki text\\|more text"] linkClose:[102, 104, "]]"]
      Text[93, 102] chars:[93, 102, "wiki link"]
    SoftLineBreak[104, 105]
    WikiLink[105, 141] linkOpen:[105, 107, "[["] text:[130, 139, "wiki link"] textSep:[129, 130, "|"] link:[107, 129, "wiki text\\\|more text"] pageRef:[107, 129, "wiki text\\\|more text"] linkClose:[139, 141, "]]"]
      Text[130, 139] chars:[130, 139, "wiki link"]
    SoftLineBreak[141, 142]
    WikiLink[142, 176] linkOpen:[142, 144, "[["] text:[165, 174, "more text"] textSep:[164, 165, "|"] link:[144, 164, "wiki link|wiki text\"] pageRef:[144, 164, "wiki link|wiki text\"] linkClose:[174, 176, "]]"]
      Text[165, 174] chars:[165, 174, "more text"]
    SoftLineBreak[176, 177]
    WikiLink[177, 212] linkOpen:[177, 179, "[["] text:[201, 210, "more text"] textSep:[200, 201, "|"] link:[179, 200, "wiki link\|wiki text\"] pageRef:[179, 200, "wiki link\|wiki text\"] linkClose:[210, 212, "]]"]
      Text[201, 210] chars:[201, 210, "more text"]
````````````````````````````````


wiki link with text, muliple pipes with escapes

```````````````````````````````` example(WikiLinks: 18) options(allow-pipe-escape)
[[wiki text|more text|wiki link]]
[[wiki text\|more text|wiki link]]
[[wiki text\\|more text|wiki link]]
[[wiki text\\\|more text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki text\|more text\|wiki link]]
.
<p><a href="more-text|wiki-link">wiki text</a>
<a href="wiki-link">wiki text|more text</a>
<a href="more-text|wiki-link">wiki text\</a>
<a href="wiki-link">wiki text\|more text</a>
<a href="more-text|wiki-link">wiki text</a>
<a href="wiki-text|more-text|wiki-link">wiki text|more text|wiki link</a></p>
.
Document[0, 212]
  Paragraph[0, 212]
    WikiLink[0, 33] linkOpen:[0, 2, "[["] link:[12, 31, "more text|wiki link"] pageRef:[12, 31, "more text|wiki link"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[31, 33, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
    SoftLineBreak[33, 34]
    WikiLink[34, 68] linkOpen:[34, 36, "[["] link:[57, 66, "wiki link"] pageRef:[57, 66, "wiki link"] textSep:[56, 57, "|"] text:[36, 56, "wiki text\|more text"] linkClose:[66, 68, "]]"]
      Text[36, 56] chars:[36, 56, "wiki  …  text"]
    SoftLineBreak[68, 69]
    WikiLink[69, 104] linkOpen:[69, 71, "[["] link:[83, 102, "more text|wiki link"] pageRef:[83, 102, "more text|wiki link"] textSep:[82, 83, "|"] text:[71, 82, "wiki text\\"] linkClose:[102, 104, "]]"]
      Text[71, 82] chars:[71, 82, "wiki  … ext\\"]
    SoftLineBreak[104, 105]
    WikiLink[105, 141] linkOpen:[105, 107, "[["] link:[130, 139, "wiki link"] pageRef:[130, 139, "wiki link"] textSep:[129, 130, "|"] text:[107, 129, "wiki text\\\|more text"] linkClose:[139, 141, "]]"]
      Text[107, 129] chars:[107, 129, "wiki  …  text"]
    SoftLineBreak[141, 142]
    WikiLink[142, 176] linkOpen:[142, 144, "[["] link:[154, 174, "more text\|wiki link"] pageRef:[154, 174, "more text\|wiki link"] textSep:[153, 154, "|"] text:[144, 153, "wiki text"] linkClose:[174, 176, "]]"]
      Text[144, 153] chars:[144, 153, "wiki text"]
    SoftLineBreak[176, 177]
    WikiLink[177, 212] linkOpen:[177, 179, "[["] link:[179, 210, "wiki text\|more text\|wiki link"] pageRef:[179, 210, "wiki text\|more text\|wiki link"] linkClose:[210, 212, "]]"]
      Text[179, 210] chars:[179, 210, "wiki  …  link"]
````````````````````````````````


wiki link with text, multiple pipes with escapes, links first option

```````````````````````````````` example(WikiLinks: 19) options(links-first, allow-pipe-escape)
[[wiki link|wiki text|more text]]
[[wiki link\|wiki text|more text]]
[[wiki text\\|more text|wiki link]]
[[wiki text\\\|more text|wiki link]]
[[wiki link|wiki text\|more text]]
[[wiki link\|wiki text\|more text]]
.
<p><a href="wiki-link|wiki-text">more text</a>
<a href="wiki-link|wiki-text">more text</a>
<a href="wiki-text\|more-text">wiki link</a>
<a href="wiki-text\|more-text">wiki link</a>
<a href="wiki-link">wiki text|more text</a>
<a href="wiki-link|wiki-text|more-text">wiki link|wiki text|more text</a></p>
.
Document[0, 212]
  Paragraph[0, 212]
    WikiLink[0, 33] linkOpen:[0, 2, "[["] text:[22, 31, "more text"] textSep:[21, 22, "|"] link:[2, 21, "wiki link|wiki text"] pageRef:[2, 21, "wiki link|wiki text"] linkClose:[31, 33, "]]"]
      Text[22, 31] chars:[22, 31, "more text"]
    SoftLineBreak[33, 34]
    WikiLink[34, 68] linkOpen:[34, 36, "[["] text:[57, 66, "more text"] textSep:[56, 57, "|"] link:[36, 56, "wiki link\|wiki text"] pageRef:[36, 56, "wiki link\|wiki text"] linkClose:[66, 68, "]]"]
      Text[57, 66] chars:[57, 66, "more text"]
    SoftLineBreak[68, 69]
    WikiLink[69, 104] linkOpen:[69, 71, "[["] text:[93, 102, "wiki link"] textSep:[92, 93, "|"] link:[71, 92, "wiki text\\|more text"] pageRef:[71, 92, "wiki text\\|more text"] linkClose:[102, 104, "]]"]
      Text[93, 102] chars:[93, 102, "wiki link"]
    SoftLineBreak[104, 105]
    WikiLink[105, 141] linkOpen:[105, 107, "[["] text:[130, 139, "wiki link"] textSep:[129, 130, "|"] link:[107, 129, "wiki text\\\|more text"] pageRef:[107, 129, "wiki text\\\|more text"] linkClose:[139, 141, "]]"]
      Text[130, 139] chars:[130, 139, "wiki link"]
    SoftLineBreak[141, 142]
    WikiLink[142, 176] linkOpen:[142, 144, "[["] text:[154, 174, "wiki text\|more text"] textSep:[153, 154, "|"] link:[144, 153, "wiki link"] pageRef:[144, 153, "wiki link"] linkClose:[174, 176, "]]"]
      Text[154, 174] chars:[154, 174, "wiki  …  text"]
    SoftLineBreak[176, 177]
    WikiLink[177, 212] linkOpen:[177, 179, "[["] link:[179, 210, "wiki link\|wiki text\|more text"] pageRef:[179, 210, "wiki link\|wiki text\|more text"] linkClose:[210, 212, "]]"]
      Text[179, 210] chars:[179, 210, "wiki  …  text"]
````````````````````````````````


simple wiki link with inlines disabled

```````````````````````````````` example WikiLinks: 20
[[**wiki link**]]
.
<p><a href="**wiki-link**">**wiki link**</a></p>
.
Document[0, 18]
  Paragraph[0, 18]
    WikiLink[0, 17] linkOpen:[0, 2, "[["] link:[2, 15, "**wiki link**"] pageRef:[2, 15, "**wiki link**"] linkClose:[15, 17, "]]"]
      Text[2, 15] chars:[2, 15, "**wik … ink**"]
````````````````````````````````


simple wiki link with inlines

```````````````````````````````` example(WikiLinks: 21) options(allow-inlines)
[[**wiki link**]]
.
<p><a href="wiki-link"><strong>wiki link</strong></a></p>
.
Document[0, 17]
  Paragraph[0, 17]
    WikiLink[0, 17] linkOpen:[0, 2, "[["] link:[4, 13, "wiki link"] pageRef:[4, 13, "wiki link"] linkClose:[15, 17, "]]"]
      StrongEmphasis[2, 15] textOpen:[2, 4, "**"] text:[4, 13, "wiki link"] textClose:[13, 15, "**"]
        Text[4, 13] chars:[4, 13, "wiki link"]
````````````````````````````````


wiki link with text with inlines

```````````````````````````````` example(WikiLinks: 22) options(allow-inlines)
[[**wiki text**|wiki link]]
.
<p><a href="wiki-link"><strong>wiki text</strong></a></p>
.
Document[0, 27]
  Paragraph[0, 27]
    WikiLink[0, 27] linkOpen:[0, 2, "[["] link:[16, 25, "wiki link"] pageRef:[16, 25, "wiki link"] textSep:[15, 16, "|"] text:[2, 15, "**wiki text**"] linkClose:[25, 27, "]]"]
      StrongEmphasis[2, 15] textOpen:[2, 4, "**"] text:[4, 13, "wiki text"] textClose:[13, 15, "**"]
        Text[4, 13] chars:[4, 13, "wiki text"]
````````````````````````````````


wiki link with text with inlines split

```````````````````````````````` example(WikiLinks: 23) options(allow-inlines)
[[**wiki text|wiki** link]]
.
<p><a href="wiki**-link">**wiki text</a></p>
.
Document[0, 27]
  Paragraph[0, 27]
    WikiLink[0, 27] linkOpen:[0, 2, "[["] link:[14, 25, "wiki** link"] pageRef:[14, 25, "wiki** link"] textSep:[13, 14, "|"] text:[2, 13, "**wiki text"] linkClose:[25, 27, "]]"]
      Text[2, 13] chars:[2, 13, "**wik …  text"]
````````````````````````````````


wiki link with text, links first option with inlines

```````````````````````````````` example(WikiLinks: 24) options(links-first, allow-inlines)
[[wiki link|**wiki text**]]
.
<p><a href="wiki-link"><strong>wiki text</strong></a></p>
.
Document[0, 27]
  Paragraph[0, 27]
    WikiLink[0, 27] linkOpen:[0, 2, "[["] text:[12, 25, "**wiki text**"] textSep:[11, 12, "|"] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[25, 27, "]]"]
      StrongEmphasis[12, 25] textOpen:[12, 14, "**"] text:[14, 23, "wiki text"] textClose:[23, 25, "**"]
        Text[14, 23] chars:[14, 23, "wiki text"]
````````````````````````````````


wiki link with text, links first option with inlines split

```````````````````````````````` example(WikiLinks: 25) options(links-first, allow-inlines)
[[wiki **link|wiki** text]]
.
<p><a href="wiki-**link">wiki** text</a></p>
.
Document[0, 27]
  Paragraph[0, 27]
    WikiLink[0, 27] linkOpen:[0, 2, "[["] text:[14, 25, "wiki** text"] textSep:[13, 14, "|"] link:[2, 13, "wiki **link"] pageRef:[2, 13, "wiki **link"] linkClose:[25, 27, "]]"]
      Text[14, 25] chars:[14, 25, "wiki* …  text"]
````````````````````````````````


wiki link with text, muliple pipes with escapes

```````````````````````````````` example(WikiLinks: 26) options(allow-inlines)
[[wiki text|more text|wiki link]]
[[wiki text\|more text|wiki link]]
[[wiki **text\|more** text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki **text|more** text\|wiki link]]
[[wiki text\|more text\|wiki link]]
[[wiki **text\|more** text\|wiki link]]
.
<p><a href="more-text|wiki-link">wiki text</a>
<a href="more-text|wiki-link">wiki text\</a>
<a href="more**-text|wiki-link">wiki **text\</a>
<a href="more-text|wiki-link">wiki text</a>
<a href="more**-text|wiki-link">wiki **text</a>
<a href="more-text|wiki-link">wiki text\</a>
<a href="more**-text|wiki-link">wiki **text\</a></p>
.
Document[0, 257]
  Paragraph[0, 257]
    WikiLink[0, 33] linkOpen:[0, 2, "[["] link:[12, 31, "more text|wiki link"] pageRef:[12, 31, "more text|wiki link"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[31, 33, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
    SoftLineBreak[33, 34]
    WikiLink[34, 68] linkOpen:[34, 36, "[["] link:[47, 66, "more text|wiki link"] pageRef:[47, 66, "more text|wiki link"] textSep:[46, 47, "|"] text:[36, 46, "wiki text\"] linkClose:[66, 68, "]]"]
      Text[36, 46] chars:[36, 46, "wiki text\"]
    SoftLineBreak[68, 69]
    WikiLink[69, 107] linkOpen:[69, 71, "[["] link:[84, 105, "more** text|wiki link"] pageRef:[84, 105, "more** text|wiki link"] textSep:[83, 84, "|"] text:[71, 83, "wiki **text\"] linkClose:[105, 107, "]]"]
      Text[71, 83] chars:[71, 83, "wiki  … text\"]
    SoftLineBreak[107, 108]
    WikiLink[108, 142] linkOpen:[108, 110, "[["] link:[120, 140, "more text\|wiki link"] pageRef:[120, 140, "more text\|wiki link"] textSep:[119, 120, "|"] text:[110, 119, "wiki text"] linkClose:[140, 142, "]]"]
      Text[110, 119] chars:[110, 119, "wiki text"]
    SoftLineBreak[142, 143]
    WikiLink[143, 181] linkOpen:[143, 145, "[["] link:[157, 179, "more** text\|wiki link"] pageRef:[157, 179, "more** text\|wiki link"] textSep:[156, 157, "|"] text:[145, 156, "wiki **text"] linkClose:[179, 181, "]]"]
      Text[145, 156] chars:[145, 156, "wiki  … *text"]
    SoftLineBreak[181, 182]
    WikiLink[182, 217] linkOpen:[182, 184, "[["] link:[195, 215, "more text\|wiki link"] pageRef:[195, 215, "more text\|wiki link"] textSep:[194, 195, "|"] text:[184, 194, "wiki text\"] linkClose:[215, 217, "]]"]
      Text[184, 194] chars:[184, 194, "wiki text\"]
    SoftLineBreak[217, 218]
    WikiLink[218, 257] linkOpen:[218, 220, "[["] link:[233, 255, "more** text\|wiki link"] pageRef:[233, 255, "more** text\|wiki link"] textSep:[232, 233, "|"] text:[220, 232, "wiki **text\"] linkClose:[255, 257, "]]"]
      Text[220, 232] chars:[220, 232, "wiki  … text\"]
````````````````````````````````


wiki link with text, multiple pipes with escapes, links first option

```````````````````````````````` example(WikiLinks: 27) options(links-first, allow-inlines)
[[wiki link|wiki text|more text]]
[[wiki link\|wiki text|more text]]
[[wiki **text\|more** text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki **text|more** text\|wiki link]]
[[wiki link\|wiki text\|more text]]
[[wiki **link\|wiki** text\|more text]]
.
<p><a href="wiki-link|wiki-text">more text</a>
<a href="wiki-link|wiki-text">more text</a>
<a href="wiki-**text|more**-text">wiki link</a>
<a href="wiki-text|more-text\">wiki link</a>
<a href="wiki-**text|more**-text\">wiki link</a>
<a href="wiki-link|wiki-text\">more text</a>
<a href="wiki-**link|wiki**-text\">more text</a></p>
.
Document[0, 257]
  Paragraph[0, 257]
    WikiLink[0, 33] linkOpen:[0, 2, "[["] text:[22, 31, "more text"] textSep:[21, 22, "|"] link:[2, 21, "wiki link|wiki text"] pageRef:[2, 21, "wiki link|wiki text"] linkClose:[31, 33, "]]"]
      Text[22, 31] chars:[22, 31, "more text"]
    SoftLineBreak[33, 34]
    WikiLink[34, 68] linkOpen:[34, 36, "[["] text:[57, 66, "more text"] textSep:[56, 57, "|"] link:[36, 56, "wiki link\|wiki text"] pageRef:[36, 56, "wiki link\|wiki text"] linkClose:[66, 68, "]]"]
      Text[57, 66] chars:[57, 66, "more text"]
    SoftLineBreak[68, 69]
    WikiLink[69, 107] linkOpen:[69, 71, "[["] text:[96, 105, "wiki link"] textSep:[95, 96, "|"] link:[71, 95, "wiki **text\|more** text"] pageRef:[71, 95, "wiki **text\|more** text"] linkClose:[105, 107, "]]"]
      Text[96, 105] chars:[96, 105, "wiki link"]
    SoftLineBreak[107, 108]
    WikiLink[108, 142] linkOpen:[108, 110, "[["] text:[131, 140, "wiki link"] textSep:[130, 131, "|"] link:[110, 130, "wiki text|more text\"] pageRef:[110, 130, "wiki text|more text\"] linkClose:[140, 142, "]]"]
      Text[131, 140] chars:[131, 140, "wiki link"]
    SoftLineBreak[142, 143]
    WikiLink[143, 181] linkOpen:[143, 145, "[["] text:[170, 179, "wiki link"] textSep:[169, 170, "|"] link:[145, 169, "wiki **text|more** text\"] pageRef:[145, 169, "wiki **text|more** text\"] linkClose:[179, 181, "]]"]
      Text[170, 179] chars:[170, 179, "wiki link"]
    SoftLineBreak[181, 182]
    WikiLink[182, 217] linkOpen:[182, 184, "[["] text:[206, 215, "more text"] textSep:[205, 206, "|"] link:[184, 205, "wiki link\|wiki text\"] pageRef:[184, 205, "wiki link\|wiki text\"] linkClose:[215, 217, "]]"]
      Text[206, 215] chars:[206, 215, "more text"]
    SoftLineBreak[217, 218]
    WikiLink[218, 257] linkOpen:[218, 220, "[["] text:[246, 255, "more text"] textSep:[245, 246, "|"] link:[220, 245, "wiki **link\|wiki** text\"] pageRef:[220, 245, "wiki **link\|wiki** text\"] linkClose:[255, 257, "]]"]
      Text[246, 255] chars:[246, 255, "more text"]
````````````````````````````````


wiki link with text, muliple pipes with escapes

```````````````````````````````` example(WikiLinks: 28) options(allow-pipe-escape, allow-inlines)
[[wiki text|more text|wiki link]]
[[wiki text\|more text|wiki link]]
[[wiki **text\|more** text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki **text|more** text\|wiki link]]
[[wiki text\|more text\|wiki link]]
[[wiki **text\|more** text\|wiki link]]
.
<p><a href="more-text|wiki-link">wiki text</a>
<a href="wiki-link">wiki text|more text</a>
<a href="wiki-link">wiki <strong>text|more</strong> text</a>
<a href="more-text|wiki-link">wiki text</a>
<a href="more**-text|wiki-link">wiki **text</a>
<a href="wiki-text|more-text|wiki-link">wiki text|more text|wiki link</a>
<a href="wiki-text|more-text|wiki-link">wiki <strong>text|more</strong> text|wiki link</a></p>
.
Document[0, 257]
  Paragraph[0, 257]
    WikiLink[0, 33] linkOpen:[0, 2, "[["] link:[12, 31, "more text|wiki link"] pageRef:[12, 31, "more text|wiki link"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[31, 33, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
    SoftLineBreak[33, 34]
    WikiLink[34, 68] linkOpen:[34, 36, "[["] link:[57, 66, "wiki link"] pageRef:[57, 66, "wiki link"] textSep:[56, 57, "|"] text:[36, 56, "wiki text\|more text"] linkClose:[66, 68, "]]"]
      Text[36, 56] chars:[36, 56, "wiki  …  text"]
    SoftLineBreak[68, 69]
    WikiLink[69, 107] linkOpen:[69, 71, "[["] link:[96, 105, "wiki link"] pageRef:[96, 105, "wiki link"] textSep:[95, 96, "|"] text:[71, 95, "wiki **text\|more** text"] linkClose:[105, 107, "]]"]
      Text[71, 76] chars:[71, 76, "wiki "]
      StrongEmphasis[76, 90] textOpen:[76, 78, "**"] text:[78, 88, "text\|more"] textClose:[88, 90, "**"]
        Text[78, 88] chars:[78, 88, "text\|more"]
      Text[90, 95] chars:[90, 95, " text"]
    SoftLineBreak[107, 108]
    WikiLink[108, 142] linkOpen:[108, 110, "[["] link:[120, 140, "more text\|wiki link"] pageRef:[120, 140, "more text\|wiki link"] textSep:[119, 120, "|"] text:[110, 119, "wiki text"] linkClose:[140, 142, "]]"]
      Text[110, 119] chars:[110, 119, "wiki text"]
    SoftLineBreak[142, 143]
    WikiLink[143, 181] linkOpen:[143, 145, "[["] link:[157, 179, "more** text\|wiki link"] pageRef:[157, 179, "more** text\|wiki link"] textSep:[156, 157, "|"] text:[145, 156, "wiki **text"] linkClose:[179, 181, "]]"]
      Text[145, 156] chars:[145, 156, "wiki  … *text"]
    SoftLineBreak[181, 182]
    WikiLink[182, 217] linkOpen:[182, 184, "[["] link:[184, 215, "wiki text\|more text\|wiki link"] pageRef:[184, 215, "wiki text\|more text\|wiki link"] linkClose:[215, 217, "]]"]
      Text[184, 215] chars:[184, 215, "wiki  …  link"]
    SoftLineBreak[217, 218]
    WikiLink[218, 257] linkOpen:[218, 220, "[["] link:[220, 255, "wiki text\|more text\|wiki link"] pageRef:[220, 255, "wiki text\|more text\|wiki link"] linkClose:[255, 257, "]]"]
      Text[220, 225] chars:[220, 225, "wiki "]
      StrongEmphasis[225, 239] textOpen:[225, 227, "**"] text:[227, 237, "text\|more"] textClose:[237, 239, "**"]
        Text[227, 237] chars:[227, 237, "text\|more"]
      Text[239, 255] chars:[239, 255, " text …  link"]
````````````````````````````````


wiki link with text, multiple pipes with escapes, links first option

```````````````````````````````` example(WikiLinks: 29) options(links-first, allow-pipe-escape, allow-inlines)
[[wiki link|wiki text|more text]]
[[wiki link\|wiki text|more text]]
[[wiki **text\|more** text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki **text|more** text\|wiki link]]
[[wiki link\|wiki text\|more text]]
[[wiki **link\|wiki** text\|more text]]
.
<p><a href="wiki-link|wiki-text">more text</a>
<a href="wiki-link|wiki-text">more text</a>
<a href="wiki-**text|more**-text">wiki link</a>
<a href="wiki-text">more text|wiki link</a>
<a href="wiki-**text">more** text|wiki link</a>
<a href="wiki-link|wiki-text|more-text">wiki link|wiki text|more text</a>
<a href="wiki-link|wiki-text|more-text">wiki <strong>link|wiki</strong> text|more text</a></p>
.
Document[0, 257]
  Paragraph[0, 257]
    WikiLink[0, 33] linkOpen:[0, 2, "[["] text:[22, 31, "more text"] textSep:[21, 22, "|"] link:[2, 21, "wiki link|wiki text"] pageRef:[2, 21, "wiki link|wiki text"] linkClose:[31, 33, "]]"]
      Text[22, 31] chars:[22, 31, "more text"]
    SoftLineBreak[33, 34]
    WikiLink[34, 68] linkOpen:[34, 36, "[["] text:[57, 66, "more text"] textSep:[56, 57, "|"] link:[36, 56, "wiki link\|wiki text"] pageRef:[36, 56, "wiki link\|wiki text"] linkClose:[66, 68, "]]"]
      Text[57, 66] chars:[57, 66, "more text"]
    SoftLineBreak[68, 69]
    WikiLink[69, 107] linkOpen:[69, 71, "[["] text:[96, 105, "wiki link"] textSep:[95, 96, "|"] link:[71, 95, "wiki **text\|more** text"] pageRef:[71, 95, "wiki **text\|more** text"] linkClose:[105, 107, "]]"]
      Text[96, 105] chars:[96, 105, "wiki link"]
    SoftLineBreak[107, 108]
    WikiLink[108, 142] linkOpen:[108, 110, "[["] text:[120, 140, "more text\|wiki link"] textSep:[119, 120, "|"] link:[110, 119, "wiki text"] pageRef:[110, 119, "wiki text"] linkClose:[140, 142, "]]"]
      Text[120, 140] chars:[120, 140, "more  …  link"]
    SoftLineBreak[142, 143]
    WikiLink[143, 181] linkOpen:[143, 145, "[["] text:[157, 179, "more** text\|wiki link"] textSep:[156, 157, "|"] link:[145, 156, "wiki **text"] pageRef:[145, 156, "wiki **text"] linkClose:[179, 181, "]]"]
      Text[157, 179] chars:[157, 179, "more* …  link"]
    SoftLineBreak[181, 182]
    WikiLink[182, 217] linkOpen:[182, 184, "[["] link:[184, 215, "wiki link\|wiki text\|more text"] pageRef:[184, 215, "wiki link\|wiki text\|more text"] linkClose:[215, 217, "]]"]
      Text[184, 215] chars:[184, 215, "wiki  …  text"]
    SoftLineBreak[217, 218]
    WikiLink[218, 257] linkOpen:[218, 220, "[["] link:[220, 255, "wiki link\|wiki text\|more text"] pageRef:[220, 255, "wiki link\|wiki text\|more text"] linkClose:[255, 257, "]]"]
      Text[220, 225] chars:[220, 225, "wiki "]
      StrongEmphasis[225, 239] textOpen:[225, 227, "**"] text:[227, 237, "link\|wiki"] textClose:[237, 239, "**"]
        Text[227, 237] chars:[227, 237, "link\|wiki"]
      Text[239, 255] chars:[239, 255, " text …  text"]
````````````````````````````````


simple wiki link with ! before

```````````````````````````````` example WikiLinks: 30
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

```````````````````````````````` example WikiLinks: 31
![[wiki text|wiki link]]
.
<p>!<a href="wiki-link">wiki text</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    Text[0, 1] chars:[0, 1, "!"]
    WikiLink[1, 24] linkOpen:[1, 3, "[["] link:[13, 22, "wiki link"] pageRef:[13, 22, "wiki link"] textSep:[12, 13, "|"] text:[3, 12, "wiki text"] linkClose:[22, 24, "]]"]
      Text[3, 12] chars:[3, 12, "wiki text"]
````````````````````````````````


reference following will be a reference, even if not defined

```````````````````````````````` example WikiLinks: 32
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

```````````````````````````````` example WikiLinks: 33
[[wiki link]][ref]

[ref]: /url
.
<p><a href="wiki-link">wiki link</a><a href="/url">ref</a></p>
.
Document[0, 32]
  Paragraph[0, 19] isTrailingBlankLine
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[11, 13, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
    LinkRef[13, 18] referenceOpen:[13, 14, "["] reference:[14, 17, "ref"] referenceClose:[17, 18, "]"]
      Text[14, 17] chars:[14, 17, "ref"]
  Reference[20, 31] refOpen:[20, 21, "["] ref:[21, 24, "ref"] refClose:[24, 26, "]:"] url:[27, 31, "/url"]
````````````````````````````````


dummy reference following will be an empty reference

```````````````````````````````` example WikiLinks: 34
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

```````````````````````````````` example WikiLinks: 35
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

```````````````````````````````` example WikiLinks: 36
[[not wiki link][]]
.
<p>[[not wiki link][]]</p>
.
Document[0, 20]
  Paragraph[0, 20]
    Text[0, 1] chars:[0, 1, "["]
    LinkRef[1, 18] referenceOpen:[1, 2, "["] reference:[2, 15, "not wiki link"] referenceClose:[15, 16, "]"] textOpen:[16, 17, "["] textClose:[17, 18, "]"]
      Text[2, 15] chars:[2, 15, "not w …  link"]
    Text[18, 19] chars:[18, 19, "]"]
````````````````````````````````


```````````````````````````````` example WikiLinks: 37
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

```````````````````````````````` example WikiLinks: 38
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

```````````````````````````````` example(WikiLinks: 39) options(link-ext)
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

```````````````````````````````` example(WikiLinks: 40) options(link-prefix)
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

```````````````````````````````` example WikiLinks: 41
[[wiki link#]] 
.
<p><a href="wiki-link#">wiki link#</a></p>
.
Document[0, 16]
  Paragraph[0, 16]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 12, "wiki link#"] linkClose:[12, 14, "]]"]
      Text[2, 12] chars:[2, 12, "wiki link#"]
````````````````````````````````


With empty anchor ref

```````````````````````````````` example(WikiLinks: 42) options(allow-anchors)
[[wiki link#]] 
.
<p><a href="wiki-link#">wiki link</a></p>
.
Document[0, 15]
  Paragraph[0, 15]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 12] text:[2, 11, "wiki link"] linkClose:[12, 14, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
````````````````````````````````


With Anchor ref

```````````````````````````````` example WikiLinks: 43
[[wiki link#anchor-ref]] 
.
<p><a href="wiki-link#anchor-ref">wiki link#anchor-ref</a></p>
.
Document[0, 26]
  Paragraph[0, 26]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 22, "wiki link#anchor-ref"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … r-ref"]
````````````````````````````````


With Anchor ref

```````````````````````````````` example(WikiLinks: 44) options(allow-anchors)
[[wiki link#anchor-ref]] 
.
<p><a href="wiki-link#anchor-ref">wiki link</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] text:[2, 11, "wiki link"] linkClose:[22, 24, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example WikiLinks: 45
[[wiki text|wiki link#]] 
.
<p><a href="wiki-link#">wiki text</a></p>
.
Document[0, 26]
  Paragraph[0, 26]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[12, 22, "wiki link#"] pageRef:[12, 22, "wiki link#"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[22, 24, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example(WikiLinks: 46) options(allow-anchors)
[[wiki text|wiki link#]] 
.
<p><a href="wiki-link#">wiki text</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[12, 22, "wiki link#"] pageRef:[12, 21, "wiki link"] anchorMarker:[21, 22, "#"] anchorRef:[22, 22] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[22, 24, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
````````````````````````````````


With text, anchor ref

```````````````````````````````` example WikiLinks: 47
[[wiki text|wiki link#anchor-ref]] 
.
<p><a href="wiki-link#anchor-ref">wiki text</a></p>
.
Document[0, 36]
  Paragraph[0, 36]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] link:[12, 32, "wiki link#anchor-ref"] pageRef:[12, 32, "wiki link#anchor-ref"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[32, 34, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
````````````````````````````````


With text, anchor ref

```````````````````````````````` example(WikiLinks: 48) options(allow-anchors)
[[wiki text|wiki link#anchor-ref]] 
.
<p><a href="wiki-link#anchor-ref">wiki text</a></p>
.
Document[0, 35]
  Paragraph[0, 35]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] link:[12, 32, "wiki link#anchor-ref"] pageRef:[12, 21, "wiki link"] anchorMarker:[21, 22, "#"] anchorRef:[22, 32, "anchor-ref"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[32, 34, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
````````````````````````````````


Links first, with text, empty anchor ref

```````````````````````````````` example(WikiLinks: 49) options(links-first)
[[wiki link#|wiki text]] 
.
<p><a href="wiki-link#">wiki text</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] text:[13, 22, "wiki text"] textSep:[12, 13, "|"] link:[2, 12, "wiki link#"] pageRef:[2, 12, "wiki link#"] linkClose:[22, 24, "]]"]
      Text[13, 22] chars:[13, 22, "wiki text"]
````````````````````````````````


Links first, with text, anchor ref

```````````````````````````````` example(WikiLinks: 50) options(links-first)
[[wiki link#anchor-ref|wiki text]] 
.
<p><a href="wiki-link#anchor-ref">wiki text</a></p>
.
Document[0, 35]
  Paragraph[0, 35]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] text:[23, 32, "wiki text"] textSep:[22, 23, "|"] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 22, "wiki link#anchor-ref"] linkClose:[32, 34, "]]"]
      Text[23, 32] chars:[23, 32, "wiki text"]
````````````````````````````````


Links first, with text, anchor ref

```````````````````````````````` example(WikiLinks: 51) options(links-first, allow-anchors)
[[wiki link#anchor-ref|wiki text]] 
.
<p><a href="wiki-link#anchor-ref">wiki text</a></p>
.
Document[0, 35]
  Paragraph[0, 35]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] text:[23, 32, "wiki text"] textSep:[22, 23, "|"] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] linkClose:[32, 34, "]]"]
      Text[23, 32] chars:[23, 32, "wiki text"]
````````````````````````````````


Custom extension with empty Anchor ref

```````````````````````````````` example(WikiLinks: 52) options(link-ext)
[[wiki link#]] 
.
<p><a href="wiki-link.html#">wiki link#</a></p>
.
Document[0, 15]
  Paragraph[0, 15]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 12, "wiki link#"] linkClose:[12, 14, "]]"]
      Text[2, 12] chars:[2, 12, "wiki link#"]
````````````````````````````````


Custom extension with empty Anchor ref

```````````````````````````````` example(WikiLinks: 53) options(link-ext, allow-anchors)
[[wiki link#]] 
.
<p><a href="wiki-link.html#">wiki link</a></p>
.
Document[0, 15]
  Paragraph[0, 15]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 12] text:[2, 11, "wiki link"] linkClose:[12, 14, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
````````````````````````````````


Custom extension with anchor ref

```````````````````````````````` example(WikiLinks: 54) options(link-ext)
[[wiki link#anchor-ref]] 
.
<p><a href="wiki-link.html#anchor-ref">wiki link#anchor-ref</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 22, "wiki link#anchor-ref"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … r-ref"]
````````````````````````````````


Custom extension with anchor ref

```````````````````````````````` example(WikiLinks: 55) options(link-ext, allow-anchors)
[[wiki link#anchor-ref]] 
.
<p><a href="wiki-link.html#anchor-ref">wiki link</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] text:[2, 11, "wiki link"] linkClose:[22, 24, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
````````````````````````````````


Custom prefix with empty anchor ref

```````````````````````````````` example(WikiLinks: 56) options(link-prefix)
[[wiki link#]]
.
<p><a href="/prefix/wiki-link#">wiki link#</a></p>
.
Document[0, 14]
  Paragraph[0, 14]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 12, "wiki link#"] linkClose:[12, 14, "]]"]
      Text[2, 12] chars:[2, 12, "wiki link#"]
````````````````````````````````


Custom prefix with empty anchor ref

```````````````````````````````` example(WikiLinks: 57) options(link-prefix, allow-anchors)
[[wiki link#]]
.
<p><a href="/prefix/wiki-link#">wiki link</a></p>
.
Document[0, 14]
  Paragraph[0, 14]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 12] text:[2, 11, "wiki link"] linkClose:[12, 14, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
````````````````````````````````


Custom prefix with anchor ref

```````````````````````````````` example(WikiLinks: 58) options(link-prefix)
[[wiki link#anchor-ref]]
.
<p><a href="/prefix/wiki-link#anchor-ref">wiki link#anchor-ref</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 22, "wiki link#anchor-ref"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … r-ref"]
````````````````````````````````


Custom prefix with anchor ref

```````````````````````````````` example(WikiLinks: 59) options(link-prefix, allow-anchors)
[[wiki link#anchor-ref]]
.
<p><a href="/prefix/wiki-link#anchor-ref">wiki link</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] text:[2, 11, "wiki link"] linkClose:[22, 24, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
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
      Text[2, 11] chars:[2, 11, "wiki text"]
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
      Text[12, 21] chars:[12, 21, "wiki text"]
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
      Text[3, 11] chars:[3, 11, "alt text"]
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
      Text[14, 22] chars:[14, 22, "alt text"]
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
  Paragraph[0, 21] isTrailingBlankLine
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
<p>![[not wiki image][]]</p>
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
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[11, 22, "wiki image#"] pageRef:[11, 22, "wiki image#"] textSep:[10, 11, "|"] text:[2, 10, "alt text"] linkClose:[22, 24, "]]"]
      Text[2, 10] chars:[2, 10, "alt text"]
````````````````````````````````


With text, anchor ref

```````````````````````````````` example(WikiImages: 20) options(wiki-images)
[[alt text|wiki image#anchor-ref]] 
.
<p><a href="wiki-image#anchor-ref">alt text</a></p>
.
Document[0, 35]
  Paragraph[0, 35]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] link:[11, 32, "wiki image#anchor-ref"] pageRef:[11, 32, "wiki image#anchor-ref"] textSep:[10, 11, "|"] text:[2, 10, "alt text"] linkClose:[32, 34, "]]"]
      Text[2, 10] chars:[2, 10, "alt text"]
````````````````````````````````


Links first, with text, empty anchor ref

```````````````````````````````` example(WikiImages: 21) options(wiki-images, links-first)
[[wiki image#|alt text]] 
.
<p><a href="wiki-image#">alt text</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] text:[14, 22, "alt text"] textSep:[13, 14, "|"] link:[2, 13, "wiki image#"] pageRef:[2, 13, "wiki image#"] linkClose:[22, 24, "]]"]
      Text[14, 22] chars:[14, 22, "alt text"]
````````````````````````````````


Links first, with text, anchor ref

```````````````````````````````` example(WikiImages: 22) options(wiki-images, links-first)
[[wiki image#anchor-ref|alt text]] 
.
<p><a href="wiki-image#anchor-ref">alt text</a></p>
.
Document[0, 35]
  Paragraph[0, 35]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] text:[24, 32, "alt text"] textSep:[23, 24, "|"] link:[2, 23, "wiki image#anchor-ref"] pageRef:[2, 23, "wiki image#anchor-ref"] linkClose:[32, 34, "]]"]
      Text[24, 32] chars:[24, 32, "alt text"]
````````````````````````````````


Custom extension with empty Anchor ref

```````````````````````````````` example(WikiImages: 23) options(wiki-images, image-ext)
![[wiki image#]] 
.
<p><img src="wiki-image.png#" alt="wiki image#" /></p>
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
<p><img src="wiki-image.png#anchor-ref" alt="wiki image#anchor-ref" /></p>
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
      Text[3, 11] chars:[3, 11, "alt text"]
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
      Text[14, 22] chars:[14, 22, "alt text"]
````````````````````````````````


