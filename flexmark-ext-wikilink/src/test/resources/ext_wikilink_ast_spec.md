---
title: WikiLinks Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## WikiLinks

Converts wikilink of the forms: `[[link]]`, `[[link|text]]` and `[[text|link]]` to links in the
HTML page.

no spaces between brackets

```````````````````````````````` example WikiLinks: 1
[ [not wiki link]]
.
<p>[ [not wiki link]]</p>
.
Document[0, 18]
  Paragraph[0, 18]
    Text[0, 2] chars:[0, 2, "[ "]
    LinkRef[2, 17] referenceOpen:[2, 3, "["] reference:[3, 16, "not wiki link"] referenceClose:[16, 17, "]"]
      Text[3, 16] chars:[3, 16, "not w …  link"]
    Text[17, 18] chars:[17, 18, "]"]
````````````````````````````````


no spaces between brackets

```````````````````````````````` example WikiLinks: 2
[[not wiki link] ]
.
<p>[[not wiki link] ]</p>
.
Document[0, 18]
  Paragraph[0, 18]
    Text[0, 1] chars:[0, 1, "["]
    LinkRef[1, 16] referenceOpen:[1, 2, "["] reference:[2, 15, "not wiki link"] referenceClose:[15, 16, "]"]
      Text[2, 15] chars:[2, 15, "not w …  link"]
    Text[16, 18] chars:[16, 18, " ]"]
````````````````````````````````


simple wiki link

```````````````````````````````` example WikiLinks: 3
[[wiki link]]
.
<p><a href="wiki-link">wiki link</a></p>
.
Document[0, 13]
  Paragraph[0, 13]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[11, 13, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
````````````````````````````````


simple wiki link with anchor ref

```````````````````````````````` example WikiLinks: 4
[[wiki link#anchor-ref]]
.
<p><a href="wiki-link%23anchor-ref">wiki link#anchor-ref</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 22, "wiki link#anchor-ref"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … r-ref"]
````````````````````````````````


simple wiki link with anchor ref

```````````````````````````````` example(WikiLinks: 5) options(allow-anchors)
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

```````````````````````````````` example(WikiLinks: 6) options(allow-anchors, allow-anchor-escape)
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

```````````````````````````````` example(WikiLinks: 7) options(allow-anchors, allow-anchor-escape)
[[wiki link\#anchor-ref]]
.
<p><a href="wiki-link%23anchor-ref">wiki link#anchor-ref</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 25] linkOpen:[0, 2, "[["] link:[2, 23, "wiki link\#anchor-ref"] pageRef:[2, 23, "wiki link\#anchor-ref"] linkClose:[23, 25, "]]"]
      Text[2, 23] chars:[2, 23, "wiki  … r-ref"]
````````````````````````````````


simple wiki link with escaped anchor-ref

```````````````````````````````` example WikiLinks: 8
[[wiki link#anchor-ref]]
[[wiki link\#anchor-ref]]
[[wiki link\\#anchor-ref]]
[[wiki link\\\#anchor-ref]]
.
<p><a href="wiki-link%23anchor-ref">wiki link#anchor-ref</a>
<a href="wiki-link%23anchor-ref">wiki link#anchor-ref</a>
<a href="wiki-link%5C%23anchor-ref">wiki link\#anchor-ref</a>
<a href="wiki-link%5C%23anchor-ref">wiki link\#anchor-ref</a></p>
.
Document[0, 105]
  Paragraph[0, 105]
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

```````````````````````````````` example(WikiLinks: 9) options(allow-anchors)
[[wiki link#anchor-ref]]
[[wiki link\#anchor-ref]]
[[wiki link\\#anchor-ref]]
[[wiki link\\\#anchor-ref]]
.
<p><a href="wiki-link#anchor-ref">wiki link</a>
<a href="wiki-link#anchor-ref">wiki link\</a>
<a href="wiki-link%5C#anchor-ref">wiki link\</a>
<a href="wiki-link%5C#anchor-ref">wiki link\\</a></p>
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

```````````````````````````````` example(WikiLinks: 10) options(allow-anchors, allow-anchor-escape)
[[wiki link#anchor-ref]]
[[wiki link\#anchor-ref]]
[[wiki link\\#anchor-ref]]
[[wiki link\\\#anchor-ref]]
.
<p><a href="wiki-link#anchor-ref">wiki link</a>
<a href="wiki-link%23anchor-ref">wiki link#anchor-ref</a>
<a href="wiki-link%5C#anchor-ref">wiki link\</a>
<a href="wiki-link%5C%23anchor-ref">wiki link\#anchor-ref</a></p>
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

```````````````````````````````` example WikiLinks: 11
[[wiki text|wiki link]]
.
<p><a href="wiki-link">wiki text</a></p>
.
Document[0, 23]
  Paragraph[0, 23]
    WikiLink[0, 23] linkOpen:[0, 2, "[["] link:[12, 21, "wiki link"] pageRef:[12, 21, "wiki link"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[21, 23, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
````````````````````````````````


wiki link with text, links first option

```````````````````````````````` example(WikiLinks: 12) options(links-first)
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

```````````````````````````````` example WikiLinks: 13
[[wiki text\|wiki link]]
.
<p><a href="wiki-link">wiki text\</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[13, 22, "wiki link"] pageRef:[13, 22, "wiki link"] textSep:[12, 13, "|"] text:[2, 12, "wiki text\"] linkClose:[22, 24, "]]"]
      Text[2, 12] chars:[2, 12, "wiki text\"]
````````````````````````````````


wiki link with text, links first option

```````````````````````````````` example(WikiLinks: 14) options(links-first)
[[wiki link\|wiki text]]
.
<p><a href="wiki-link%5C">wiki text</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] text:[13, 22, "wiki text"] textSep:[12, 13, "|"] link:[2, 12, "wiki link\"] pageRef:[2, 12, "wiki link\"] linkClose:[22, 24, "]]"]
      Text[13, 22] chars:[13, 22, "wiki text"]
````````````````````````````````


wiki link with text

```````````````````````````````` example(WikiLinks: 15) options(allow-pipe-escape)
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

```````````````````````````````` example(WikiLinks: 16) options(links-first, allow-pipe-escape)
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

```````````````````````````````` example WikiLinks: 17
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
Document[0, 212]
  Paragraph[0, 212]
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

```````````````````````````````` example(WikiLinks: 18) options(links-first)
[[wiki link|wiki text|more text]]
[[wiki link\|wiki text|more text]]
[[wiki text\\|more text|wiki link]]
[[wiki text\\\|more text|wiki link]]
[[wiki link|wiki text\|more text]]
[[wiki link\|wiki text\|more text]]
.
<p><a href="wiki-link|wiki-text">more text</a>
<a href="wiki-link|wiki-text">more text</a>
<a href="wiki-text%5C|more-text">wiki link</a>
<a href="wiki-text%5C|more-text">wiki link</a>
<a href="wiki-link|wiki-text%5C">more text</a>
<a href="wiki-link|wiki-text%5C">more text</a></p>
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

```````````````````````````````` example(WikiLinks: 19) options(allow-pipe-escape)
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

```````````````````````````````` example(WikiLinks: 20) options(links-first, allow-pipe-escape)
[[wiki link|wiki text|more text]]
[[wiki link\|wiki text|more text]]
[[wiki text\\|more text|wiki link]]
[[wiki text\\\|more text|wiki link]]
[[wiki link|wiki text\|more text]]
[[wiki link\|wiki text\|more text]]
.
<p><a href="wiki-link|wiki-text">more text</a>
<a href="wiki-link|wiki-text">more text</a>
<a href="wiki-text%5C|more-text">wiki link</a>
<a href="wiki-text%5C|more-text">wiki link</a>
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

```````````````````````````````` example WikiLinks: 21
[[**wiki link**]]
.
<p><a href="**wiki-link**">**wiki link**</a></p>
.
Document[0, 17]
  Paragraph[0, 17]
    WikiLink[0, 17] linkOpen:[0, 2, "[["] link:[2, 15, "**wiki link**"] pageRef:[2, 15, "**wiki link**"] linkClose:[15, 17, "]]"]
      Text[2, 15] chars:[2, 15, "**wik … ink**"]
````````````````````````````````


simple wiki link with inlines

```````````````````````````````` example(WikiLinks: 22) options(allow-inlines)
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

```````````````````````````````` example(WikiLinks: 23) options(allow-inlines)
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

```````````````````````````````` example(WikiLinks: 24) options(allow-inlines)
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

```````````````````````````````` example(WikiLinks: 25) options(links-first, allow-inlines)
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

```````````````````````````````` example(WikiLinks: 26) options(links-first, allow-inlines)
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

```````````````````````````````` example(WikiLinks: 27) options(allow-inlines)
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

```````````````````````````````` example(WikiLinks: 28) options(links-first, allow-inlines)
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
<a href="wiki-text|more-text%5C">wiki link</a>
<a href="wiki-**text|more**-text%5C">wiki link</a>
<a href="wiki-link|wiki-text%5C">more text</a>
<a href="wiki-**link|wiki**-text%5C">more text</a></p>
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

```````````````````````````````` example(WikiLinks: 29) options(allow-pipe-escape, allow-inlines)
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

```````````````````````````````` example(WikiLinks: 30) options(links-first, allow-pipe-escape, allow-inlines)
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

```````````````````````````````` example WikiLinks: 31
![[wiki link]]
.
<p>!<a href="wiki-link">wiki link</a></p>
.
Document[0, 14]
  Paragraph[0, 14]
    Text[0, 1] chars:[0, 1, "!"]
    WikiLink[1, 14] linkOpen:[1, 3, "[["] link:[3, 12, "wiki link"] pageRef:[3, 12, "wiki link"] linkClose:[12, 14, "]]"]
      Text[3, 12] chars:[3, 12, "wiki link"]
````````````````````````````````


wiki link with text with ! before

```````````````````````````````` example WikiLinks: 32
![[wiki text|wiki link]]
.
<p>!<a href="wiki-link">wiki text</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    Text[0, 1] chars:[0, 1, "!"]
    WikiLink[1, 24] linkOpen:[1, 3, "[["] link:[13, 22, "wiki link"] pageRef:[13, 22, "wiki link"] textSep:[12, 13, "|"] text:[3, 12, "wiki text"] linkClose:[22, 24, "]]"]
      Text[3, 12] chars:[3, 12, "wiki text"]
````````````````````````````````


reference following will be a reference, even if not defined

```````````````````````````````` example WikiLinks: 33
[[wiki link]][ref]
.
<p><a href="wiki-link">wiki link</a>[ref]</p>
.
Document[0, 18]
  Paragraph[0, 18]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[11, 13, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
    LinkRef[13, 18] referenceOpen:[13, 14, "["] reference:[14, 17, "ref"] referenceClose:[17, 18, "]"]
      Text[14, 17] chars:[14, 17, "ref"]
````````````````````````````````


reference following will be a reference

```````````````````````````````` example WikiLinks: 34
[[wiki link]][ref]

[ref]: /url
.
<p><a href="wiki-link">wiki link</a><a href="/url">ref</a></p>
.
Document[0, 31]
  Paragraph[0, 19] isTrailingBlankLine
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[11, 13, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
    LinkRef[13, 18] referenceOpen:[13, 14, "["] reference:[14, 17, "ref"] referenceClose:[17, 18, "]"]
      Text[14, 17] chars:[14, 17, "ref"]
  Reference[20, 31] refOpen:[20, 21, "["] ref:[21, 24, "ref"] refClose:[24, 26, "]:"] url:[27, 31, "/url"]
````````````````````````````````


dummy reference following will be an empty reference

```````````````````````````````` example WikiLinks: 35
[[wiki link]][]
.
<p><a href="wiki-link">wiki link</a>[]</p>
.
Document[0, 15]
  Paragraph[0, 15]
    WikiLink[0, 13] linkOpen:[0, 2, "[["] link:[2, 11, "wiki link"] pageRef:[2, 11, "wiki link"] linkClose:[11, 13, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
    LinkRef[13, 15] referenceOpen:[13, 14, "["] reference:[14, 14] referenceClose:[14, 15, "]"]
````````````````````````````````


reference inside is not a wiki link but a link ref with brackets around it

```````````````````````````````` example WikiLinks: 36
[[not wiki link][ref]]
.
<p>[[not wiki link][ref]]</p>
.
Document[0, 22]
  Paragraph[0, 22]
    Text[0, 1] chars:[0, 1, "["]
    LinkRef[1, 21] textOpen:[1, 2, "["] text:[2, 15, "not wiki link"] textClose:[15, 16, "]"] referenceOpen:[16, 17, "["] reference:[17, 20, "ref"] referenceClose:[20, 21, "]"]
      Text[2, 15] chars:[2, 15, "not w …  link"]
    Text[21, 22] chars:[21, 22, "]"]
````````````````````````````````


dummy reference inside is not a wiki link but a link ref with brackets around it

```````````````````````````````` example WikiLinks: 37
[[not wiki link][]]
.
<p>[[not wiki link][]]</p>
.
Document[0, 19]
  Paragraph[0, 19]
    Text[0, 1] chars:[0, 1, "["]
    LinkRef[1, 18] referenceOpen:[1, 2, "["] reference:[2, 15, "not wiki link"] referenceClose:[15, 16, "]"] textOpen:[16, 17, "["] textClose:[17, 18, "]"]
      Text[2, 15] chars:[2, 15, "not w …  link"]
    Text[18, 19] chars:[18, 19, "]"]
````````````````````````````````


```````````````````````````````` example WikiLinks: 38
[[wiki link]] [^link][ref] [[^wiki link]]
.
<p><a href="wiki-link">wiki link</a> [^link][ref] <a href="^wiki-link">^wiki link</a></p>
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


Exclamation before is just text

```````````````````````````````` example WikiLinks: 39
![[wiki link]] [^link][ref] [[^wiki link]] [[wiki]][ref]
.
<p>!<a href="wiki-link">wiki link</a> [^link][ref] <a href="^wiki-link">^wiki link</a> <a href="wiki">wiki</a>[ref]</p>
.
Document[0, 56]
  Paragraph[0, 56]
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

```````````````````````````````` example(WikiLinks: 40) options(link-ext)
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

```````````````````````````````` example(WikiLinks: 41) options(link-prefix)
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

```````````````````````````````` example WikiLinks: 42
[[wiki link#]] 
.
<p><a href="wiki-link%23">wiki link#</a></p>
.
Document[0, 15]
  Paragraph[0, 15]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 12, "wiki link#"] linkClose:[12, 14, "]]"]
      Text[2, 12] chars:[2, 12, "wiki link#"]
````````````````````````````````


With empty anchor ref

```````````````````````````````` example(WikiLinks: 43) options(allow-anchors)
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

```````````````````````````````` example WikiLinks: 44
[[wiki link#anchor-ref]] 
.
<p><a href="wiki-link%23anchor-ref">wiki link#anchor-ref</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 22, "wiki link#anchor-ref"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … r-ref"]
````````````````````````````````


With Anchor ref

```````````````````````````````` example(WikiLinks: 45) options(allow-anchors)
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

```````````````````````````````` example WikiLinks: 46
[[wiki text|wiki link#]] 
.
<p><a href="wiki-link%23">wiki text</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[12, 22, "wiki link#"] pageRef:[12, 22, "wiki link#"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[22, 24, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example(WikiLinks: 47) options(allow-anchors)
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

```````````````````````````````` example WikiLinks: 48
[[wiki text|wiki link#anchor-ref]] 
.
<p><a href="wiki-link%23anchor-ref">wiki text</a></p>
.
Document[0, 35]
  Paragraph[0, 35]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] link:[12, 32, "wiki link#anchor-ref"] pageRef:[12, 32, "wiki link#anchor-ref"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[32, 34, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
````````````````````````````````


With text, anchor ref

```````````````````````````````` example(WikiLinks: 49) options(allow-anchors)
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

```````````````````````````````` example(WikiLinks: 50) options(links-first)
[[wiki link#|wiki text]] 
.
<p><a href="wiki-link%23">wiki text</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] text:[13, 22, "wiki text"] textSep:[12, 13, "|"] link:[2, 12, "wiki link#"] pageRef:[2, 12, "wiki link#"] linkClose:[22, 24, "]]"]
      Text[13, 22] chars:[13, 22, "wiki text"]
````````````````````````````````


Links first, with text, anchor ref

```````````````````````````````` example(WikiLinks: 51) options(links-first)
[[wiki link#anchor-ref|wiki text]] 
.
<p><a href="wiki-link%23anchor-ref">wiki text</a></p>
.
Document[0, 35]
  Paragraph[0, 35]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] text:[23, 32, "wiki text"] textSep:[22, 23, "|"] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 22, "wiki link#anchor-ref"] linkClose:[32, 34, "]]"]
      Text[23, 32] chars:[23, 32, "wiki text"]
````````````````````````````````


Links first, with text, anchor ref

```````````````````````````````` example(WikiLinks: 52) options(links-first, allow-anchors)
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

```````````````````````````````` example(WikiLinks: 53) options(link-ext)
[[wiki link#]] 
.
<p><a href="wiki-link%23.html">wiki link#</a></p>
.
Document[0, 15]
  Paragraph[0, 15]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 12, "wiki link#"] linkClose:[12, 14, "]]"]
      Text[2, 12] chars:[2, 12, "wiki link#"]
````````````````````````````````


Custom extension with empty Anchor ref

```````````````````````````````` example(WikiLinks: 54) options(link-ext, allow-anchors)
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

```````````````````````````````` example(WikiLinks: 55) options(link-ext)
[[wiki link#anchor-ref]] 
.
<p><a href="wiki-link%23anchor-ref.html">wiki link#anchor-ref</a></p>
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 22, "wiki link#anchor-ref"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … r-ref"]
````````````````````````````````


Custom extension with anchor ref

```````````````````````````````` example(WikiLinks: 56) options(link-ext, allow-anchors)
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

```````````````````````````````` example(WikiLinks: 57) options(link-prefix)
[[wiki link#]]
.
<p><a href="/prefix/wiki-link%23">wiki link#</a></p>
.
Document[0, 14]
  Paragraph[0, 14]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 12, "wiki link#"] linkClose:[12, 14, "]]"]
      Text[2, 12] chars:[2, 12, "wiki link#"]
````````````````````````````````


Custom prefix with empty anchor ref

```````````````````````````````` example(WikiLinks: 58) options(link-prefix, allow-anchors)
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

```````````````````````````````` example(WikiLinks: 59) options(link-prefix)
[[wiki link#anchor-ref]]
.
<p><a href="/prefix/wiki-link%23anchor-ref">wiki link#anchor-ref</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 22, "wiki link#anchor-ref"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … r-ref"]
````````````````````````````````


Custom prefix with anchor ref

```````````````````````````````` example(WikiLinks: 60) options(link-prefix, allow-anchors)
[[wiki link#anchor-ref]]
.
<p><a href="/prefix/wiki-link#anchor-ref">wiki link</a></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] text:[2, 11, "wiki link"] linkClose:[22, 24, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
````````````````````````````````


Custom link escape

With configuration options `LINK_ESCAPE_CHARS` and `LINK_REPLACE_CHARS` it is possible to change
the link escaping. `LINK_ESCAPE_CHARS` gives the character to escape in links.
`LINK_REPLACE_CHARS` gives the correponding replacement characters.

```````````````````````````````` example(WikiLinks: 61) options(custom-link-escape)
See [[My Page]].
.
<p>See <a href="My_Page">My Page</a>.</p>
.
Document[0, 16]
  Paragraph[0, 16]
    Text[0, 4] chars:[0, 4, "See "]
    WikiLink[4, 15] linkOpen:[4, 6, "[["] link:[6, 13, "My Page"] pageRef:[6, 13, "My Page"] linkClose:[13, 15, "]]"]
      Text[6, 13] chars:[6, 13, "My Page"]
    Text[15, 16] chars:[15, 16, "."]
````````````````````````````````


Links to virtual directories

With configuration options `LINK_ESCAPE_CHARS` and `LINK_REPLACE_CHARS` it is possible to create
wiki links to virtual directories (links containing the `/` character.

```````````````````````````````` example(WikiLinks: 62) options(custom-link-escape)
See [[directory/WikiPage]].
.
<p>See <a href="directory/WikiPage">directory/WikiPage</a>.</p>
.
Document[0, 27]
  Paragraph[0, 27]
    Text[0, 4] chars:[0, 4, "See "]
    WikiLink[4, 26] linkOpen:[4, 6, "[["] link:[6, 24, "directory/WikiPage"] pageRef:[6, 24, "directory/WikiPage"] linkClose:[24, 26, "]]"]
      Text[6, 24] chars:[6, 24, "direc … iPage"]
    Text[26, 27] chars:[26, 27, "."]
````````````````````````````````


Absolute link prefix

```````````````````````````````` example(WikiLinks: 63) options(custom-link-escape, link-prefix-absolute)
[[Page]]

[[dir/Page]]

[[/Page]]

[[/dir/Page]]
.
<p><a href="/relative/Page">Page</a></p>
<p><a href="/relative/dir/Page">dir/Page</a></p>
<p><a href="/absolute/Page">/Page</a></p>
<p><a href="/absolute/dir/Page">/dir/Page</a></p>
.
Document[0, 48]
  Paragraph[0, 9] isTrailingBlankLine
    WikiLink[0, 8] linkOpen:[0, 2, "[["] link:[2, 6, "Page"] pageRef:[2, 6, "Page"] linkClose:[6, 8, "]]"]
      Text[2, 6] chars:[2, 6, "Page"]
  Paragraph[10, 23] isTrailingBlankLine
    WikiLink[10, 22] linkOpen:[10, 12, "[["] link:[12, 20, "dir/Page"] pageRef:[12, 20, "dir/Page"] linkClose:[20, 22, "]]"]
      Text[12, 20] chars:[12, 20, "dir/Page"]
  Paragraph[24, 34] isTrailingBlankLine
    WikiLink[24, 33] linkOpen:[24, 26, "[["] link:[26, 31, "/Page"] pageRef:[26, 31, "/Page"] linkClose:[31, 33, "]]"]
      Text[26, 31] chars:[26, 31, "/Page"]
  Paragraph[35, 48]
    WikiLink[35, 48] linkOpen:[35, 37, "[["] link:[37, 46, "/dir/Page"] pageRef:[37, 46, "/dir/Page"] linkClose:[46, 48, "]]"]
      Text[37, 46] chars:[37, 46, "/dir/Page"]
````````````````````````````````


### In Links


```````````````````````````````` example WikiLinks - In Links: 1
[[[wiki]]](/url)
.
<p>[<a href="wiki">wiki</a>](/url)</p>
.
Document[0, 16]
  Paragraph[0, 16]
    Text[0, 1] chars:[0, 1, "["]
    WikiLink[1, 9] linkOpen:[1, 3, "[["] link:[3, 7, "wiki"] pageRef:[3, 7, "wiki"] linkClose:[7, 9, "]]"]
      Text[3, 7] chars:[3, 7, "wiki"]
    Text[9, 16] chars:[9, 16, "](/url)"]
````````````````````````````````


no wiki in refs

```````````````````````````````` example(WikiLinks - In Links: 2) options(link-text-priority)
[[[wiki]]](/url)
.
<p><a href="/url">[[wiki]]</a></p>
.
Document[0, 16]
  Paragraph[0, 16]
    Link[0, 16] textOpen:[0, 1, "["] text:[1, 9, "[[wiki]]"] textClose:[9, 10, "]"] linkOpen:[10, 11, "("] url:[11, 15, "/url"] pageRef:[11, 15, "/url"] linkClose:[15, 16, ")"]
      Text[1, 9] chars:[1, 9, "[[wiki]]"]
````````````````````````````````


```````````````````````````````` example WikiLinks - In Links: 3
[[[wiki]]][ref]
    
[ref]: /url
.
<p>[<a href="wiki">wiki</a>]<a href="/url">ref</a></p>
.
Document[0, 32]
  Paragraph[0, 16] isTrailingBlankLine
    Text[0, 1] chars:[0, 1, "["]
    WikiLink[1, 9] linkOpen:[1, 3, "[["] link:[3, 7, "wiki"] pageRef:[3, 7, "wiki"] linkClose:[7, 9, "]]"]
      Text[3, 7] chars:[3, 7, "wiki"]
    Text[9, 10] chars:[9, 10, "]"]
    LinkRef[10, 15] referenceOpen:[10, 11, "["] reference:[11, 14, "ref"] referenceClose:[14, 15, "]"]
      Text[11, 14] chars:[11, 14, "ref"]
  Reference[21, 32] refOpen:[21, 22, "["] ref:[22, 25, "ref"] refClose:[25, 27, "]:"] url:[28, 32, "/url"]
````````````````````````````````


```````````````````````````````` example(WikiLinks - In Links: 4) options(link-text-priority)
[[[wiki]]][ref]
    
[ref]: /url
.
<p>[<a href="wiki">wiki</a>]<a href="/url">ref</a></p>
.
Document[0, 32]
  Paragraph[0, 16] isTrailingBlankLine
    Text[0, 1] chars:[0, 1, "["]
    WikiLink[1, 9] linkOpen:[1, 3, "[["] link:[3, 7, "wiki"] pageRef:[3, 7, "wiki"] linkClose:[7, 9, "]]"]
      Text[3, 7] chars:[3, 7, "wiki"]
    Text[9, 10] chars:[9, 10, "]"]
    LinkRef[10, 15] referenceOpen:[10, 11, "["] reference:[11, 14, "ref"] referenceClose:[14, 15, "]"]
      Text[11, 14] chars:[11, 14, "ref"]
  Reference[21, 32] refOpen:[21, 22, "["] ref:[22, 25, "ref"] refClose:[25, 27, "]:"] url:[28, 32, "/url"]
````````````````````````````````


## Wiki Typographic Text

With empty anchor ref

```````````````````````````````` example Wiki Typographic Text: 1
[[wiki--link#]] 
[[wiki...link#]] 
[[wiki'link#]] 
[['wiki link#']]
.
<p><a href="wiki--link%23">wiki&ndash;link#</a>
<a href="wiki...link%23">wiki&hellip;link#</a>
<a href="wiki'link%23">wiki&rsquo;link#</a>
<a href="'wiki-link%23'">&rsquo;wiki link#&rsquo;</a></p>
.
Document[0, 67]
  Paragraph[0, 67]
    WikiLink[0, 15] linkOpen:[0, 2, "[["] link:[2, 13, "wiki--link#"] pageRef:[2, 13, "wiki--link#"] linkClose:[13, 15, "]]"]
      Text[2, 6] chars:[2, 6, "wiki"]
      TypographicSmarts[6, 8] typographic: &ndash; 
      Text[8, 13] chars:[8, 13, "link#"]
    SoftLineBreak[16, 17]
    WikiLink[17, 33] linkOpen:[17, 19, "[["] link:[19, 31, "wiki...link#"] pageRef:[19, 31, "wiki...link#"] linkClose:[31, 33, "]]"]
      Text[19, 23] chars:[19, 23, "wiki"]
      TypographicSmarts[23, 26] typographic: &hellip; 
      Text[26, 31] chars:[26, 31, "link#"]
    SoftLineBreak[34, 35]
    WikiLink[35, 49] linkOpen:[35, 37, "[["] link:[37, 47, "wiki'link#"] pageRef:[37, 47, "wiki'link#"] linkClose:[47, 49, "]]"]
      Text[37, 41] chars:[37, 41, "wiki"]
      TypographicSmarts[41, 42] typographic: &rsquo; 
      Text[42, 47] chars:[42, 47, "link#"]
    SoftLineBreak[50, 51]
    WikiLink[51, 67] linkOpen:[51, 53, "[["] link:[53, 65, "'wiki link#'"] pageRef:[53, 65, "'wiki link#'"] linkClose:[65, 67, "]]"]
      TypographicSmarts[53, 54] typographic: &rsquo; 
      Text[54, 64] chars:[54, 64, "wiki link#"]
      TypographicSmarts[64, 65] typographic: &rsquo; 
````````````````````````````````


With empty anchor ref

```````````````````````````````` example(Wiki Typographic Text: 2) options(allow-anchors)
[[wiki--link#]] 
[[wiki...link#]] 
[[wiki'link#]] 
[['wiki link#']]
.
<p><a href="wiki--link#">wiki&ndash;link</a>
<a href="wiki...link#">wiki&hellip;link</a>
<a href="wiki'link#">wiki&rsquo;link</a>
<a href="'wiki-link#'">&rsquo;wiki link</a></p>
.
Document[0, 67]
  Paragraph[0, 67]
    WikiLink[0, 15] linkOpen:[0, 2, "[["] link:[2, 13, "wiki--link#"] pageRef:[2, 12, "wiki--link"] anchorMarker:[12, 13, "#"] anchorRef:[13, 13] text:[2, 12, "wiki--link"] linkClose:[13, 15, "]]"]
      Text[2, 6] chars:[2, 6, "wiki"]
      TypographicSmarts[6, 8] typographic: &ndash; 
      Text[8, 12] chars:[8, 12, "link"]
    SoftLineBreak[16, 17]
    WikiLink[17, 33] linkOpen:[17, 19, "[["] link:[19, 31, "wiki...link#"] pageRef:[19, 30, "wiki...link"] anchorMarker:[30, 31, "#"] anchorRef:[31, 31] text:[19, 30, "wiki...link"] linkClose:[31, 33, "]]"]
      Text[19, 23] chars:[19, 23, "wiki"]
      TypographicSmarts[23, 26] typographic: &hellip; 
      Text[26, 30] chars:[26, 30, "link"]
    SoftLineBreak[34, 35]
    WikiLink[35, 49] linkOpen:[35, 37, "[["] link:[37, 47, "wiki'link#"] pageRef:[37, 46, "wiki'link"] anchorMarker:[46, 47, "#"] anchorRef:[47, 47] text:[37, 46, "wiki'link"] linkClose:[47, 49, "]]"]
      Text[37, 41] chars:[37, 41, "wiki"]
      TypographicSmarts[41, 42] typographic: &rsquo; 
      Text[42, 46] chars:[42, 46, "link"]
    SoftLineBreak[50, 51]
    WikiLink[51, 67] linkOpen:[51, 53, "[["] link:[53, 65, "'wiki link#'"] pageRef:[53, 63, "'wiki link"] anchorMarker:[63, 64, "#"] anchorRef:[64, 65, "'"] text:[53, 63, "'wiki link"] linkClose:[65, 67, "]]"]
      TypographicSmarts[53, 54] typographic: &rsquo; 
      Text[54, 63] chars:[54, 63, "wiki link"]
````````````````````````````````


With Anchor ref

```````````````````````````````` example Wiki Typographic Text: 3
[[wiki--link#anchor-ref]] 
[[wiki...link#anchor-ref]] 
[[wiki'link#anchor-ref]] 
[['wiki link'#anchor-ref]] 
.
<p><a href="wiki--link%23anchor-ref">wiki&ndash;link#anchor-ref</a>
<a href="wiki...link%23anchor-ref">wiki&hellip;link#anchor-ref</a>
<a href="wiki'link%23anchor-ref">wiki&rsquo;link#anchor-ref</a>
<a href="'wiki-link'%23anchor-ref">&rsquo;wiki link&rsquo;#anchor-ref</a></p>
.
Document[0, 108]
  Paragraph[0, 108]
    WikiLink[0, 25] linkOpen:[0, 2, "[["] link:[2, 23, "wiki--link#anchor-ref"] pageRef:[2, 23, "wiki--link#anchor-ref"] linkClose:[23, 25, "]]"]
      Text[2, 6] chars:[2, 6, "wiki"]
      TypographicSmarts[6, 8] typographic: &ndash; 
      Text[8, 23] chars:[8, 23, "link# … r-ref"]
    SoftLineBreak[26, 27]
    WikiLink[27, 53] linkOpen:[27, 29, "[["] link:[29, 51, "wiki...link#anchor-ref"] pageRef:[29, 51, "wiki...link#anchor-ref"] linkClose:[51, 53, "]]"]
      Text[29, 33] chars:[29, 33, "wiki"]
      TypographicSmarts[33, 36] typographic: &hellip; 
      Text[36, 51] chars:[36, 51, "link# … r-ref"]
    SoftLineBreak[54, 55]
    WikiLink[55, 79] linkOpen:[55, 57, "[["] link:[57, 77, "wiki'link#anchor-ref"] pageRef:[57, 77, "wiki'link#anchor-ref"] linkClose:[77, 79, "]]"]
      Text[57, 61] chars:[57, 61, "wiki"]
      TypographicSmarts[61, 62] typographic: &rsquo; 
      Text[62, 77] chars:[62, 77, "link# … r-ref"]
    SoftLineBreak[80, 81]
    WikiLink[81, 107] linkOpen:[81, 83, "[["] link:[83, 105, "'wiki link'#anchor-ref"] pageRef:[83, 105, "'wiki link'#anchor-ref"] linkClose:[105, 107, "]]"]
      TypographicSmarts[83, 84] typographic: &rsquo; 
      Text[84, 93] chars:[84, 93, "wiki link"]
      TypographicSmarts[93, 94] typographic: &rsquo; 
      Text[94, 105] chars:[94, 105, "#anch … r-ref"]
````````````````````````````````


With Anchor ref

```````````````````````````````` example(Wiki Typographic Text: 4) options(allow-anchors)
[[wiki--link#anchor-ref]] 
[[wiki...link#anchor-ref]] 
[[wiki'link#anchor-ref]] 
[['wiki link'#anchor-ref]] 
.
<p><a href="wiki--link#anchor-ref">wiki&ndash;link</a>
<a href="wiki...link#anchor-ref">wiki&hellip;link</a>
<a href="wiki'link#anchor-ref">wiki&rsquo;link</a>
<a href="'wiki-link'#anchor-ref">&rsquo;wiki link&rsquo;</a></p>
.
Document[0, 108]
  Paragraph[0, 108]
    WikiLink[0, 25] linkOpen:[0, 2, "[["] link:[2, 23, "wiki--link#anchor-ref"] pageRef:[2, 12, "wiki--link"] anchorMarker:[12, 13, "#"] anchorRef:[13, 23, "anchor-ref"] text:[2, 12, "wiki--link"] linkClose:[23, 25, "]]"]
      Text[2, 6] chars:[2, 6, "wiki"]
      TypographicSmarts[6, 8] typographic: &ndash; 
      Text[8, 12] chars:[8, 12, "link"]
    SoftLineBreak[26, 27]
    WikiLink[27, 53] linkOpen:[27, 29, "[["] link:[29, 51, "wiki...link#anchor-ref"] pageRef:[29, 40, "wiki...link"] anchorMarker:[40, 41, "#"] anchorRef:[41, 51, "anchor-ref"] text:[29, 40, "wiki...link"] linkClose:[51, 53, "]]"]
      Text[29, 33] chars:[29, 33, "wiki"]
      TypographicSmarts[33, 36] typographic: &hellip; 
      Text[36, 40] chars:[36, 40, "link"]
    SoftLineBreak[54, 55]
    WikiLink[55, 79] linkOpen:[55, 57, "[["] link:[57, 77, "wiki'link#anchor-ref"] pageRef:[57, 66, "wiki'link"] anchorMarker:[66, 67, "#"] anchorRef:[67, 77, "anchor-ref"] text:[57, 66, "wiki'link"] linkClose:[77, 79, "]]"]
      Text[57, 61] chars:[57, 61, "wiki"]
      TypographicSmarts[61, 62] typographic: &rsquo; 
      Text[62, 66] chars:[62, 66, "link"]
    SoftLineBreak[80, 81]
    WikiLink[81, 107] linkOpen:[81, 83, "[["] link:[83, 105, "'wiki link'#anchor-ref"] pageRef:[83, 94, "'wiki link'"] anchorMarker:[94, 95, "#"] anchorRef:[95, 105, "anchor-ref"] text:[83, 94, "'wiki link'"] linkClose:[105, 107, "]]"]
      TypographicSmarts[83, 84] typographic: &rsquo; 
      Text[84, 93] chars:[84, 93, "wiki link"]
      TypographicSmarts[93, 94] typographic: &rsquo; 
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example Wiki Typographic Text: 5
[[wiki text|wiki link#]] 
[[wiki--link|wiki--link#]] 
[[wiki...link|wiki...link#]] 
[[wiki'link|wiki'link#]] 
[['wiki link'|'wiki link'#]] 
.
<p><a href="wiki-link%23">wiki text</a>
<a href="wiki--link%23">wiki&ndash;link</a>
<a href="wiki...link%23">wiki&hellip;link</a>
<a href="wiki'link%23">wiki&rsquo;link</a>
<a href="'wiki-link'%23">&rsquo;wiki link&rsquo;</a></p>
.
Document[0, 139]
  Paragraph[0, 139]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[12, 22, "wiki link#"] pageRef:[12, 22, "wiki link#"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[22, 24, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
    SoftLineBreak[25, 26]
    WikiLink[26, 52] linkOpen:[26, 28, "[["] link:[39, 50, "wiki--link#"] pageRef:[39, 50, "wiki--link#"] textSep:[38, 39, "|"] text:[28, 38, "wiki--link"] linkClose:[50, 52, "]]"]
      Text[28, 32] chars:[28, 32, "wiki"]
      TypographicSmarts[32, 34] typographic: &ndash; 
      Text[34, 38] chars:[34, 38, "link"]
    SoftLineBreak[53, 54]
    WikiLink[54, 82] linkOpen:[54, 56, "[["] link:[68, 80, "wiki...link#"] pageRef:[68, 80, "wiki...link#"] textSep:[67, 68, "|"] text:[56, 67, "wiki...link"] linkClose:[80, 82, "]]"]
      Text[56, 60] chars:[56, 60, "wiki"]
      TypographicSmarts[60, 63] typographic: &hellip; 
      Text[63, 67] chars:[63, 67, "link"]
    SoftLineBreak[83, 84]
    WikiLink[84, 108] linkOpen:[84, 86, "[["] link:[96, 106, "wiki'link#"] pageRef:[96, 106, "wiki'link#"] textSep:[95, 96, "|"] text:[86, 95, "wiki'link"] linkClose:[106, 108, "]]"]
      Text[86, 90] chars:[86, 90, "wiki"]
      TypographicSmarts[90, 91] typographic: &rsquo; 
      Text[91, 95] chars:[91, 95, "link"]
    SoftLineBreak[109, 110]
    WikiLink[110, 138] linkOpen:[110, 112, "[["] link:[124, 136, "'wiki link'#"] pageRef:[124, 136, "'wiki link'#"] textSep:[123, 124, "|"] text:[112, 123, "'wiki link'"] linkClose:[136, 138, "]]"]
      TypographicSmarts[112, 113] typographic: &rsquo; 
      Text[113, 122] chars:[113, 122, "wiki link"]
      TypographicSmarts[122, 123] typographic: &rsquo; 
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example(Wiki Typographic Text: 6) options(allow-anchors)
[[wiki text|wiki link#]] 
[[wiki--link|wiki--link#]] 
[[wiki...link|wiki...link#]] 
[[wiki'link|wiki'link#]] 
[['wiki link'|'wiki link'#]] 
.
<p><a href="wiki-link#">wiki text</a>
<a href="wiki--link#">wiki&ndash;link</a>
<a href="wiki...link#">wiki&hellip;link</a>
<a href="wiki'link#">wiki&rsquo;link</a>
<a href="'wiki-link'#">&rsquo;wiki link&rsquo;</a></p>
.
Document[0, 139]
  Paragraph[0, 139]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[12, 22, "wiki link#"] pageRef:[12, 21, "wiki link"] anchorMarker:[21, 22, "#"] anchorRef:[22, 22] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[22, 24, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
    SoftLineBreak[25, 26]
    WikiLink[26, 52] linkOpen:[26, 28, "[["] link:[39, 50, "wiki--link#"] pageRef:[39, 49, "wiki--link"] anchorMarker:[49, 50, "#"] anchorRef:[50, 50] textSep:[38, 39, "|"] text:[28, 38, "wiki--link"] linkClose:[50, 52, "]]"]
      Text[28, 32] chars:[28, 32, "wiki"]
      TypographicSmarts[32, 34] typographic: &ndash; 
      Text[34, 38] chars:[34, 38, "link"]
    SoftLineBreak[53, 54]
    WikiLink[54, 82] linkOpen:[54, 56, "[["] link:[68, 80, "wiki...link#"] pageRef:[68, 79, "wiki...link"] anchorMarker:[79, 80, "#"] anchorRef:[80, 80] textSep:[67, 68, "|"] text:[56, 67, "wiki...link"] linkClose:[80, 82, "]]"]
      Text[56, 60] chars:[56, 60, "wiki"]
      TypographicSmarts[60, 63] typographic: &hellip; 
      Text[63, 67] chars:[63, 67, "link"]
    SoftLineBreak[83, 84]
    WikiLink[84, 108] linkOpen:[84, 86, "[["] link:[96, 106, "wiki'link#"] pageRef:[96, 105, "wiki'link"] anchorMarker:[105, 106, "#"] anchorRef:[106, 106] textSep:[95, 96, "|"] text:[86, 95, "wiki'link"] linkClose:[106, 108, "]]"]
      Text[86, 90] chars:[86, 90, "wiki"]
      TypographicSmarts[90, 91] typographic: &rsquo; 
      Text[91, 95] chars:[91, 95, "link"]
    SoftLineBreak[109, 110]
    WikiLink[110, 138] linkOpen:[110, 112, "[["] link:[124, 136, "'wiki link'#"] pageRef:[124, 135, "'wiki link'"] anchorMarker:[135, 136, "#"] anchorRef:[136, 136] textSep:[123, 124, "|"] text:[112, 123, "'wiki link'"] linkClose:[136, 138, "]]"]
      TypographicSmarts[112, 113] typographic: &rsquo; 
      Text[113, 122] chars:[113, 122, "wiki link"]
      TypographicSmarts[122, 123] typographic: &rsquo; 
````````````````````````````````


With text, anchor ref

```````````````````````````````` example Wiki Typographic Text: 7
[[wiki text|wiki link#anchor-ref]] 
[[wiki--link|wiki--link#anchor-ref]] 
[[wiki...link|wiki...link#anchor-ref]] 
[[wiki'link|wiki'link#anchor-ref]] 
[['wiki link'|'wiki link'#anchor-ref]] 
.
<p><a href="wiki-link%23anchor-ref">wiki text</a>
<a href="wiki--link%23anchor-ref">wiki&ndash;link</a>
<a href="wiki...link%23anchor-ref">wiki&hellip;link</a>
<a href="wiki'link%23anchor-ref">wiki&rsquo;link</a>
<a href="'wiki-link'%23anchor-ref">&rsquo;wiki link&rsquo;</a></p>
.
Document[0, 189]
  Paragraph[0, 189]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] link:[12, 32, "wiki link#anchor-ref"] pageRef:[12, 32, "wiki link#anchor-ref"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[32, 34, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
    SoftLineBreak[35, 36]
    WikiLink[36, 72] linkOpen:[36, 38, "[["] link:[49, 70, "wiki--link#anchor-ref"] pageRef:[49, 70, "wiki--link#anchor-ref"] textSep:[48, 49, "|"] text:[38, 48, "wiki--link"] linkClose:[70, 72, "]]"]
      Text[38, 42] chars:[38, 42, "wiki"]
      TypographicSmarts[42, 44] typographic: &ndash; 
      Text[44, 48] chars:[44, 48, "link"]
    SoftLineBreak[73, 74]
    WikiLink[74, 112] linkOpen:[74, 76, "[["] link:[88, 110, "wiki...link#anchor-ref"] pageRef:[88, 110, "wiki...link#anchor-ref"] textSep:[87, 88, "|"] text:[76, 87, "wiki...link"] linkClose:[110, 112, "]]"]
      Text[76, 80] chars:[76, 80, "wiki"]
      TypographicSmarts[80, 83] typographic: &hellip; 
      Text[83, 87] chars:[83, 87, "link"]
    SoftLineBreak[113, 114]
    WikiLink[114, 148] linkOpen:[114, 116, "[["] link:[126, 146, "wiki'link#anchor-ref"] pageRef:[126, 146, "wiki'link#anchor-ref"] textSep:[125, 126, "|"] text:[116, 125, "wiki'link"] linkClose:[146, 148, "]]"]
      Text[116, 120] chars:[116, 120, "wiki"]
      TypographicSmarts[120, 121] typographic: &rsquo; 
      Text[121, 125] chars:[121, 125, "link"]
    SoftLineBreak[149, 150]
    WikiLink[150, 188] linkOpen:[150, 152, "[["] link:[164, 186, "'wiki link'#anchor-ref"] pageRef:[164, 186, "'wiki link'#anchor-ref"] textSep:[163, 164, "|"] text:[152, 163, "'wiki link'"] linkClose:[186, 188, "]]"]
      TypographicSmarts[152, 153] typographic: &rsquo; 
      Text[153, 162] chars:[153, 162, "wiki link"]
      TypographicSmarts[162, 163] typographic: &rsquo; 
````````````````````````````````


With text, anchor ref

```````````````````````````````` example(Wiki Typographic Text: 8) options(allow-anchors)
[[wiki text|wiki link#anchor-ref]] 
[[wiki--link|wiki--link#anchor-ref]] 
[[wiki...link|wiki...link#anchor-ref]] 
[[wiki'link|wiki'link#anchor-ref]] 
[['wiki link'|'wiki link'#anchor-ref]] 
.
<p><a href="wiki-link#anchor-ref">wiki text</a>
<a href="wiki--link#anchor-ref">wiki&ndash;link</a>
<a href="wiki...link#anchor-ref">wiki&hellip;link</a>
<a href="wiki'link#anchor-ref">wiki&rsquo;link</a>
<a href="'wiki-link'#anchor-ref">&rsquo;wiki link&rsquo;</a></p>
.
Document[0, 189]
  Paragraph[0, 189]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] link:[12, 32, "wiki link#anchor-ref"] pageRef:[12, 21, "wiki link"] anchorMarker:[21, 22, "#"] anchorRef:[22, 32, "anchor-ref"] textSep:[11, 12, "|"] text:[2, 11, "wiki text"] linkClose:[32, 34, "]]"]
      Text[2, 11] chars:[2, 11, "wiki text"]
    SoftLineBreak[35, 36]
    WikiLink[36, 72] linkOpen:[36, 38, "[["] link:[49, 70, "wiki--link#anchor-ref"] pageRef:[49, 59, "wiki--link"] anchorMarker:[59, 60, "#"] anchorRef:[60, 70, "anchor-ref"] textSep:[48, 49, "|"] text:[38, 48, "wiki--link"] linkClose:[70, 72, "]]"]
      Text[38, 42] chars:[38, 42, "wiki"]
      TypographicSmarts[42, 44] typographic: &ndash; 
      Text[44, 48] chars:[44, 48, "link"]
    SoftLineBreak[73, 74]
    WikiLink[74, 112] linkOpen:[74, 76, "[["] link:[88, 110, "wiki...link#anchor-ref"] pageRef:[88, 99, "wiki...link"] anchorMarker:[99, 100, "#"] anchorRef:[100, 110, "anchor-ref"] textSep:[87, 88, "|"] text:[76, 87, "wiki...link"] linkClose:[110, 112, "]]"]
      Text[76, 80] chars:[76, 80, "wiki"]
      TypographicSmarts[80, 83] typographic: &hellip; 
      Text[83, 87] chars:[83, 87, "link"]
    SoftLineBreak[113, 114]
    WikiLink[114, 148] linkOpen:[114, 116, "[["] link:[126, 146, "wiki'link#anchor-ref"] pageRef:[126, 135, "wiki'link"] anchorMarker:[135, 136, "#"] anchorRef:[136, 146, "anchor-ref"] textSep:[125, 126, "|"] text:[116, 125, "wiki'link"] linkClose:[146, 148, "]]"]
      Text[116, 120] chars:[116, 120, "wiki"]
      TypographicSmarts[120, 121] typographic: &rsquo; 
      Text[121, 125] chars:[121, 125, "link"]
    SoftLineBreak[149, 150]
    WikiLink[150, 188] linkOpen:[150, 152, "[["] link:[164, 186, "'wiki link'#anchor-ref"] pageRef:[164, 175, "'wiki link'"] anchorMarker:[175, 176, "#"] anchorRef:[176, 186, "anchor-ref"] textSep:[163, 164, "|"] text:[152, 163, "'wiki link'"] linkClose:[186, 188, "]]"]
      TypographicSmarts[152, 153] typographic: &rsquo; 
      Text[153, 162] chars:[153, 162, "wiki link"]
      TypographicSmarts[162, 163] typographic: &rsquo; 
````````````````````````````````


Links first, with text, empty anchor ref

```````````````````````````````` example(Wiki Typographic Text: 9) options(links-first)
[[wiki link#|wiki text]] 
[[wiki--link#|wiki--link]] 
[[wiki...link#|wiki...link]] 
[[wiki'link#|wiki'link]] 
[['wiki link'#|'wiki link']] 
.
<p><a href="wiki-link%23">wiki text</a>
<a href="wiki--link%23">wiki&ndash;link</a>
<a href="wiki...link%23">wiki&hellip;link</a>
<a href="wiki'link%23">wiki&rsquo;link</a>
<a href="'wiki-link'%23">&rsquo;wiki link&rsquo;</a></p>
.
Document[0, 139]
  Paragraph[0, 139]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] text:[13, 22, "wiki text"] textSep:[12, 13, "|"] link:[2, 12, "wiki link#"] pageRef:[2, 12, "wiki link#"] linkClose:[22, 24, "]]"]
      Text[13, 22] chars:[13, 22, "wiki text"]
    SoftLineBreak[25, 26]
    WikiLink[26, 52] linkOpen:[26, 28, "[["] text:[40, 50, "wiki--link"] textSep:[39, 40, "|"] link:[28, 39, "wiki--link#"] pageRef:[28, 39, "wiki--link#"] linkClose:[50, 52, "]]"]
      Text[40, 44] chars:[40, 44, "wiki"]
      TypographicSmarts[44, 46] typographic: &ndash; 
      Text[46, 50] chars:[46, 50, "link"]
    SoftLineBreak[53, 54]
    WikiLink[54, 82] linkOpen:[54, 56, "[["] text:[69, 80, "wiki...link"] textSep:[68, 69, "|"] link:[56, 68, "wiki...link#"] pageRef:[56, 68, "wiki...link#"] linkClose:[80, 82, "]]"]
      Text[69, 73] chars:[69, 73, "wiki"]
      TypographicSmarts[73, 76] typographic: &hellip; 
      Text[76, 80] chars:[76, 80, "link"]
    SoftLineBreak[83, 84]
    WikiLink[84, 108] linkOpen:[84, 86, "[["] text:[97, 106, "wiki'link"] textSep:[96, 97, "|"] link:[86, 96, "wiki'link#"] pageRef:[86, 96, "wiki'link#"] linkClose:[106, 108, "]]"]
      Text[97, 101] chars:[97, 101, "wiki"]
      TypographicSmarts[101, 102] typographic: &rsquo; 
      Text[102, 106] chars:[102, 106, "link"]
    SoftLineBreak[109, 110]
    WikiLink[110, 138] linkOpen:[110, 112, "[["] text:[125, 136, "'wiki link'"] textSep:[124, 125, "|"] link:[112, 124, "'wiki link'#"] pageRef:[112, 124, "'wiki link'#"] linkClose:[136, 138, "]]"]
      TypographicSmarts[125, 126] typographic: &rsquo; 
      Text[126, 135] chars:[126, 135, "wiki link"]
      TypographicSmarts[135, 136] typographic: &rsquo; 
````````````````````````````````


Links first, with text, anchor ref

```````````````````````````````` example(Wiki Typographic Text: 10) options(links-first)
[[wiki link#anchor-ref|wiki text]] 
[[wiki--link#anchor-ref|wiki--link]] 
[[wiki...link#anchor-ref|wiki...link]] 
[[wiki'link#anchor-ref|wiki'link]] 
[['wiki link'#anchor-ref|'wiki link']] 
.
<p><a href="wiki-link%23anchor-ref">wiki text</a>
<a href="wiki--link%23anchor-ref">wiki&ndash;link</a>
<a href="wiki...link%23anchor-ref">wiki&hellip;link</a>
<a href="wiki'link%23anchor-ref">wiki&rsquo;link</a>
<a href="'wiki-link'%23anchor-ref">&rsquo;wiki link&rsquo;</a></p>
.
Document[0, 189]
  Paragraph[0, 189]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] text:[23, 32, "wiki text"] textSep:[22, 23, "|"] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 22, "wiki link#anchor-ref"] linkClose:[32, 34, "]]"]
      Text[23, 32] chars:[23, 32, "wiki text"]
    SoftLineBreak[35, 36]
    WikiLink[36, 72] linkOpen:[36, 38, "[["] text:[60, 70, "wiki--link"] textSep:[59, 60, "|"] link:[38, 59, "wiki--link#anchor-ref"] pageRef:[38, 59, "wiki--link#anchor-ref"] linkClose:[70, 72, "]]"]
      Text[60, 64] chars:[60, 64, "wiki"]
      TypographicSmarts[64, 66] typographic: &ndash; 
      Text[66, 70] chars:[66, 70, "link"]
    SoftLineBreak[73, 74]
    WikiLink[74, 112] linkOpen:[74, 76, "[["] text:[99, 110, "wiki...link"] textSep:[98, 99, "|"] link:[76, 98, "wiki...link#anchor-ref"] pageRef:[76, 98, "wiki...link#anchor-ref"] linkClose:[110, 112, "]]"]
      Text[99, 103] chars:[99, 103, "wiki"]
      TypographicSmarts[103, 106] typographic: &hellip; 
      Text[106, 110] chars:[106, 110, "link"]
    SoftLineBreak[113, 114]
    WikiLink[114, 148] linkOpen:[114, 116, "[["] text:[137, 146, "wiki'link"] textSep:[136, 137, "|"] link:[116, 136, "wiki'link#anchor-ref"] pageRef:[116, 136, "wiki'link#anchor-ref"] linkClose:[146, 148, "]]"]
      Text[137, 141] chars:[137, 141, "wiki"]
      TypographicSmarts[141, 142] typographic: &rsquo; 
      Text[142, 146] chars:[142, 146, "link"]
    SoftLineBreak[149, 150]
    WikiLink[150, 188] linkOpen:[150, 152, "[["] text:[175, 186, "'wiki link'"] textSep:[174, 175, "|"] link:[152, 174, "'wiki link'#anchor-ref"] pageRef:[152, 174, "'wiki link'#anchor-ref"] linkClose:[186, 188, "]]"]
      TypographicSmarts[175, 176] typographic: &rsquo; 
      Text[176, 185] chars:[176, 185, "wiki link"]
      TypographicSmarts[185, 186] typographic: &rsquo; 
````````````````````````````````


Links first, with text, anchor ref

```````````````````````````````` example(Wiki Typographic Text: 11) options(links-first, allow-anchors)
[[wiki link#anchor-ref|wiki text]] 
[[wiki--link#anchor-ref|wiki--link]] 
[[wiki...link#anchor-ref|wiki...link]] 
[[wiki'link#anchor-ref|wiki'link]] 
[['wiki link'#anchor-ref|'wiki link']] 
.
<p><a href="wiki-link#anchor-ref">wiki text</a>
<a href="wiki--link#anchor-ref">wiki&ndash;link</a>
<a href="wiki...link#anchor-ref">wiki&hellip;link</a>
<a href="wiki'link#anchor-ref">wiki&rsquo;link</a>
<a href="'wiki-link'#anchor-ref">&rsquo;wiki link&rsquo;</a></p>
.
Document[0, 189]
  Paragraph[0, 189]
    WikiLink[0, 34] linkOpen:[0, 2, "[["] text:[23, 32, "wiki text"] textSep:[22, 23, "|"] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] linkClose:[32, 34, "]]"]
      Text[23, 32] chars:[23, 32, "wiki text"]
    SoftLineBreak[35, 36]
    WikiLink[36, 72] linkOpen:[36, 38, "[["] text:[60, 70, "wiki--link"] textSep:[59, 60, "|"] link:[38, 59, "wiki--link#anchor-ref"] pageRef:[38, 48, "wiki--link"] anchorMarker:[48, 49, "#"] anchorRef:[49, 59, "anchor-ref"] linkClose:[70, 72, "]]"]
      Text[60, 64] chars:[60, 64, "wiki"]
      TypographicSmarts[64, 66] typographic: &ndash; 
      Text[66, 70] chars:[66, 70, "link"]
    SoftLineBreak[73, 74]
    WikiLink[74, 112] linkOpen:[74, 76, "[["] text:[99, 110, "wiki...link"] textSep:[98, 99, "|"] link:[76, 98, "wiki...link#anchor-ref"] pageRef:[76, 87, "wiki...link"] anchorMarker:[87, 88, "#"] anchorRef:[88, 98, "anchor-ref"] linkClose:[110, 112, "]]"]
      Text[99, 103] chars:[99, 103, "wiki"]
      TypographicSmarts[103, 106] typographic: &hellip; 
      Text[106, 110] chars:[106, 110, "link"]
    SoftLineBreak[113, 114]
    WikiLink[114, 148] linkOpen:[114, 116, "[["] text:[137, 146, "wiki'link"] textSep:[136, 137, "|"] link:[116, 136, "wiki'link#anchor-ref"] pageRef:[116, 125, "wiki'link"] anchorMarker:[125, 126, "#"] anchorRef:[126, 136, "anchor-ref"] linkClose:[146, 148, "]]"]
      Text[137, 141] chars:[137, 141, "wiki"]
      TypographicSmarts[141, 142] typographic: &rsquo; 
      Text[142, 146] chars:[142, 146, "link"]
    SoftLineBreak[149, 150]
    WikiLink[150, 188] linkOpen:[150, 152, "[["] text:[175, 186, "'wiki link'"] textSep:[174, 175, "|"] link:[152, 174, "'wiki link'#anchor-ref"] pageRef:[152, 163, "'wiki link'"] anchorMarker:[163, 164, "#"] anchorRef:[164, 174, "anchor-ref"] linkClose:[186, 188, "]]"]
      TypographicSmarts[175, 176] typographic: &rsquo; 
      Text[176, 185] chars:[176, 185, "wiki link"]
      TypographicSmarts[185, 186] typographic: &rsquo; 
````````````````````````````````


Custom extension with empty Anchor ref

```````````````````````````````` example(Wiki Typographic Text: 12) options(link-ext)
[[wiki link#]] 
[[wiki--link#]] 
[[wiki...link#]] 
[[wiki'link#]] 
[['wiki link'#]] 
.
<p><a href="wiki-link%23.html">wiki link#</a>
<a href="wiki--link%23.html">wiki&ndash;link#</a>
<a href="wiki...link%23.html">wiki&hellip;link#</a>
<a href="wiki'link%23.html">wiki&rsquo;link#</a>
<a href="'wiki-link'%23.html">&rsquo;wiki link&rsquo;#</a></p>
.
Document[0, 84]
  Paragraph[0, 84]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 12, "wiki link#"] linkClose:[12, 14, "]]"]
      Text[2, 12] chars:[2, 12, "wiki link#"]
    SoftLineBreak[15, 16]
    WikiLink[16, 31] linkOpen:[16, 18, "[["] link:[18, 29, "wiki--link#"] pageRef:[18, 29, "wiki--link#"] linkClose:[29, 31, "]]"]
      Text[18, 22] chars:[18, 22, "wiki"]
      TypographicSmarts[22, 24] typographic: &ndash; 
      Text[24, 29] chars:[24, 29, "link#"]
    SoftLineBreak[32, 33]
    WikiLink[33, 49] linkOpen:[33, 35, "[["] link:[35, 47, "wiki...link#"] pageRef:[35, 47, "wiki...link#"] linkClose:[47, 49, "]]"]
      Text[35, 39] chars:[35, 39, "wiki"]
      TypographicSmarts[39, 42] typographic: &hellip; 
      Text[42, 47] chars:[42, 47, "link#"]
    SoftLineBreak[50, 51]
    WikiLink[51, 65] linkOpen:[51, 53, "[["] link:[53, 63, "wiki'link#"] pageRef:[53, 63, "wiki'link#"] linkClose:[63, 65, "]]"]
      Text[53, 57] chars:[53, 57, "wiki"]
      TypographicSmarts[57, 58] typographic: &rsquo; 
      Text[58, 63] chars:[58, 63, "link#"]
    SoftLineBreak[66, 67]
    WikiLink[67, 83] linkOpen:[67, 69, "[["] link:[69, 81, "'wiki link'#"] pageRef:[69, 81, "'wiki link'#"] linkClose:[81, 83, "]]"]
      TypographicSmarts[69, 70] typographic: &rsquo; 
      Text[70, 79] chars:[70, 79, "wiki link"]
      TypographicSmarts[79, 80] typographic: &rsquo; 
      Text[80, 81] chars:[80, 81, "#"]
````````````````````````````````


Custom extension with empty Anchor ref

```````````````````````````````` example(Wiki Typographic Text: 13) options(link-ext, allow-anchors)
[[wiki link#]] 
[[wiki--link#]] 
[[wiki...link#]] 
[[wiki'link#]] 
[['wiki link'#]] 
.
<p><a href="wiki-link.html#">wiki link</a>
<a href="wiki--link.html#">wiki&ndash;link</a>
<a href="wiki...link.html#">wiki&hellip;link</a>
<a href="wiki'link.html#">wiki&rsquo;link</a>
<a href="'wiki-link'.html#">&rsquo;wiki link&rsquo;</a></p>
.
Document[0, 84]
  Paragraph[0, 84]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 12] text:[2, 11, "wiki link"] linkClose:[12, 14, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
    SoftLineBreak[15, 16]
    WikiLink[16, 31] linkOpen:[16, 18, "[["] link:[18, 29, "wiki--link#"] pageRef:[18, 28, "wiki--link"] anchorMarker:[28, 29, "#"] anchorRef:[29, 29] text:[18, 28, "wiki--link"] linkClose:[29, 31, "]]"]
      Text[18, 22] chars:[18, 22, "wiki"]
      TypographicSmarts[22, 24] typographic: &ndash; 
      Text[24, 28] chars:[24, 28, "link"]
    SoftLineBreak[32, 33]
    WikiLink[33, 49] linkOpen:[33, 35, "[["] link:[35, 47, "wiki...link#"] pageRef:[35, 46, "wiki...link"] anchorMarker:[46, 47, "#"] anchorRef:[47, 47] text:[35, 46, "wiki...link"] linkClose:[47, 49, "]]"]
      Text[35, 39] chars:[35, 39, "wiki"]
      TypographicSmarts[39, 42] typographic: &hellip; 
      Text[42, 46] chars:[42, 46, "link"]
    SoftLineBreak[50, 51]
    WikiLink[51, 65] linkOpen:[51, 53, "[["] link:[53, 63, "wiki'link#"] pageRef:[53, 62, "wiki'link"] anchorMarker:[62, 63, "#"] anchorRef:[63, 63] text:[53, 62, "wiki'link"] linkClose:[63, 65, "]]"]
      Text[53, 57] chars:[53, 57, "wiki"]
      TypographicSmarts[57, 58] typographic: &rsquo; 
      Text[58, 62] chars:[58, 62, "link"]
    SoftLineBreak[66, 67]
    WikiLink[67, 83] linkOpen:[67, 69, "[["] link:[69, 81, "'wiki link'#"] pageRef:[69, 80, "'wiki link'"] anchorMarker:[80, 81, "#"] anchorRef:[81, 81] text:[69, 80, "'wiki link'"] linkClose:[81, 83, "]]"]
      TypographicSmarts[69, 70] typographic: &rsquo; 
      Text[70, 79] chars:[70, 79, "wiki link"]
      TypographicSmarts[79, 80] typographic: &rsquo; 
````````````````````````````````


Custom extension with anchor ref

```````````````````````````````` example(Wiki Typographic Text: 14) options(link-ext)
[[wiki link#anchor-ref]] 
[[wiki--link#anchor-ref]] 
[[wiki...link#anchor-ref]] 
[[wiki'link#anchor-ref]] 
[['wiki link'#anchor-ref]] 
.
<p><a href="wiki-link%23anchor-ref.html">wiki link#anchor-ref</a>
<a href="wiki--link%23anchor-ref.html">wiki&ndash;link#anchor-ref</a>
<a href="wiki...link%23anchor-ref.html">wiki&hellip;link#anchor-ref</a>
<a href="wiki'link%23anchor-ref.html">wiki&rsquo;link#anchor-ref</a>
<a href="'wiki-link'%23anchor-ref.html">&rsquo;wiki link&rsquo;#anchor-ref</a></p>
.
Document[0, 134]
  Paragraph[0, 134]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 22, "wiki link#anchor-ref"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … r-ref"]
    SoftLineBreak[25, 26]
    WikiLink[26, 51] linkOpen:[26, 28, "[["] link:[28, 49, "wiki--link#anchor-ref"] pageRef:[28, 49, "wiki--link#anchor-ref"] linkClose:[49, 51, "]]"]
      Text[28, 32] chars:[28, 32, "wiki"]
      TypographicSmarts[32, 34] typographic: &ndash; 
      Text[34, 49] chars:[34, 49, "link# … r-ref"]
    SoftLineBreak[52, 53]
    WikiLink[53, 79] linkOpen:[53, 55, "[["] link:[55, 77, "wiki...link#anchor-ref"] pageRef:[55, 77, "wiki...link#anchor-ref"] linkClose:[77, 79, "]]"]
      Text[55, 59] chars:[55, 59, "wiki"]
      TypographicSmarts[59, 62] typographic: &hellip; 
      Text[62, 77] chars:[62, 77, "link# … r-ref"]
    SoftLineBreak[80, 81]
    WikiLink[81, 105] linkOpen:[81, 83, "[["] link:[83, 103, "wiki'link#anchor-ref"] pageRef:[83, 103, "wiki'link#anchor-ref"] linkClose:[103, 105, "]]"]
      Text[83, 87] chars:[83, 87, "wiki"]
      TypographicSmarts[87, 88] typographic: &rsquo; 
      Text[88, 103] chars:[88, 103, "link# … r-ref"]
    SoftLineBreak[106, 107]
    WikiLink[107, 133] linkOpen:[107, 109, "[["] link:[109, 131, "'wiki link'#anchor-ref"] pageRef:[109, 131, "'wiki link'#anchor-ref"] linkClose:[131, 133, "]]"]
      TypographicSmarts[109, 110] typographic: &rsquo; 
      Text[110, 119] chars:[110, 119, "wiki link"]
      TypographicSmarts[119, 120] typographic: &rsquo; 
      Text[120, 131] chars:[120, 131, "#anch … r-ref"]
````````````````````````````````


Custom extension with anchor ref

```````````````````````````````` example(Wiki Typographic Text: 15) options(link-ext, allow-anchors)
[[wiki link#anchor-ref]] 
[[wiki--link#anchor-ref]] 
[[wiki...link#anchor-ref]] 
[[wiki'link#anchor-ref]] 
[['wiki link'#anchor-ref]] 
.
<p><a href="wiki-link.html#anchor-ref">wiki link</a>
<a href="wiki--link.html#anchor-ref">wiki&ndash;link</a>
<a href="wiki...link.html#anchor-ref">wiki&hellip;link</a>
<a href="wiki'link.html#anchor-ref">wiki&rsquo;link</a>
<a href="'wiki-link'.html#anchor-ref">&rsquo;wiki link&rsquo;</a></p>
.
Document[0, 134]
  Paragraph[0, 134]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] text:[2, 11, "wiki link"] linkClose:[22, 24, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
    SoftLineBreak[25, 26]
    WikiLink[26, 51] linkOpen:[26, 28, "[["] link:[28, 49, "wiki--link#anchor-ref"] pageRef:[28, 38, "wiki--link"] anchorMarker:[38, 39, "#"] anchorRef:[39, 49, "anchor-ref"] text:[28, 38, "wiki--link"] linkClose:[49, 51, "]]"]
      Text[28, 32] chars:[28, 32, "wiki"]
      TypographicSmarts[32, 34] typographic: &ndash; 
      Text[34, 38] chars:[34, 38, "link"]
    SoftLineBreak[52, 53]
    WikiLink[53, 79] linkOpen:[53, 55, "[["] link:[55, 77, "wiki...link#anchor-ref"] pageRef:[55, 66, "wiki...link"] anchorMarker:[66, 67, "#"] anchorRef:[67, 77, "anchor-ref"] text:[55, 66, "wiki...link"] linkClose:[77, 79, "]]"]
      Text[55, 59] chars:[55, 59, "wiki"]
      TypographicSmarts[59, 62] typographic: &hellip; 
      Text[62, 66] chars:[62, 66, "link"]
    SoftLineBreak[80, 81]
    WikiLink[81, 105] linkOpen:[81, 83, "[["] link:[83, 103, "wiki'link#anchor-ref"] pageRef:[83, 92, "wiki'link"] anchorMarker:[92, 93, "#"] anchorRef:[93, 103, "anchor-ref"] text:[83, 92, "wiki'link"] linkClose:[103, 105, "]]"]
      Text[83, 87] chars:[83, 87, "wiki"]
      TypographicSmarts[87, 88] typographic: &rsquo; 
      Text[88, 92] chars:[88, 92, "link"]
    SoftLineBreak[106, 107]
    WikiLink[107, 133] linkOpen:[107, 109, "[["] link:[109, 131, "'wiki link'#anchor-ref"] pageRef:[109, 120, "'wiki link'"] anchorMarker:[120, 121, "#"] anchorRef:[121, 131, "anchor-ref"] text:[109, 120, "'wiki link'"] linkClose:[131, 133, "]]"]
      TypographicSmarts[109, 110] typographic: &rsquo; 
      Text[110, 119] chars:[110, 119, "wiki link"]
      TypographicSmarts[119, 120] typographic: &rsquo; 
````````````````````````````````


Custom prefix with empty anchor ref

```````````````````````````````` example(Wiki Typographic Text: 16) options(link-prefix)
[[wiki link#]]
[[wiki--link#]]
[[wiki...link#]]
[[wiki'link#]]
[['wiki link'#]]
.
<p><a href="/prefix/wiki-link%23">wiki link#</a>
<a href="/prefix/wiki--link%23">wiki&ndash;link#</a>
<a href="/prefix/wiki...link%23">wiki&hellip;link#</a>
<a href="/prefix/wiki'link%23">wiki&rsquo;link#</a>
<a href="/prefix/'wiki-link'%23">&rsquo;wiki link&rsquo;#</a></p>
.
Document[0, 79]
  Paragraph[0, 79]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 12, "wiki link#"] linkClose:[12, 14, "]]"]
      Text[2, 12] chars:[2, 12, "wiki link#"]
    SoftLineBreak[14, 15]
    WikiLink[15, 30] linkOpen:[15, 17, "[["] link:[17, 28, "wiki--link#"] pageRef:[17, 28, "wiki--link#"] linkClose:[28, 30, "]]"]
      Text[17, 21] chars:[17, 21, "wiki"]
      TypographicSmarts[21, 23] typographic: &ndash; 
      Text[23, 28] chars:[23, 28, "link#"]
    SoftLineBreak[30, 31]
    WikiLink[31, 47] linkOpen:[31, 33, "[["] link:[33, 45, "wiki...link#"] pageRef:[33, 45, "wiki...link#"] linkClose:[45, 47, "]]"]
      Text[33, 37] chars:[33, 37, "wiki"]
      TypographicSmarts[37, 40] typographic: &hellip; 
      Text[40, 45] chars:[40, 45, "link#"]
    SoftLineBreak[47, 48]
    WikiLink[48, 62] linkOpen:[48, 50, "[["] link:[50, 60, "wiki'link#"] pageRef:[50, 60, "wiki'link#"] linkClose:[60, 62, "]]"]
      Text[50, 54] chars:[50, 54, "wiki"]
      TypographicSmarts[54, 55] typographic: &rsquo; 
      Text[55, 60] chars:[55, 60, "link#"]
    SoftLineBreak[62, 63]
    WikiLink[63, 79] linkOpen:[63, 65, "[["] link:[65, 77, "'wiki link'#"] pageRef:[65, 77, "'wiki link'#"] linkClose:[77, 79, "]]"]
      TypographicSmarts[65, 66] typographic: &rsquo; 
      Text[66, 75] chars:[66, 75, "wiki link"]
      TypographicSmarts[75, 76] typographic: &rsquo; 
      Text[76, 77] chars:[76, 77, "#"]
````````````````````````````````


Custom prefix with empty anchor ref

```````````````````````````````` example(Wiki Typographic Text: 17) options(link-prefix, allow-anchors)
[[wiki link#]]
[[wiki--link#]]
[[wiki...link#]]
[[wiki'link#]]
[['wiki link'#]]
.
<p><a href="/prefix/wiki-link#">wiki link</a>
<a href="/prefix/wiki--link#">wiki&ndash;link</a>
<a href="/prefix/wiki...link#">wiki&hellip;link</a>
<a href="/prefix/wiki'link#">wiki&rsquo;link</a>
<a href="/prefix/'wiki-link'#">&rsquo;wiki link&rsquo;</a></p>
.
Document[0, 79]
  Paragraph[0, 79]
    WikiLink[0, 14] linkOpen:[0, 2, "[["] link:[2, 12, "wiki link#"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 12] text:[2, 11, "wiki link"] linkClose:[12, 14, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
    SoftLineBreak[14, 15]
    WikiLink[15, 30] linkOpen:[15, 17, "[["] link:[17, 28, "wiki--link#"] pageRef:[17, 27, "wiki--link"] anchorMarker:[27, 28, "#"] anchorRef:[28, 28] text:[17, 27, "wiki--link"] linkClose:[28, 30, "]]"]
      Text[17, 21] chars:[17, 21, "wiki"]
      TypographicSmarts[21, 23] typographic: &ndash; 
      Text[23, 27] chars:[23, 27, "link"]
    SoftLineBreak[30, 31]
    WikiLink[31, 47] linkOpen:[31, 33, "[["] link:[33, 45, "wiki...link#"] pageRef:[33, 44, "wiki...link"] anchorMarker:[44, 45, "#"] anchorRef:[45, 45] text:[33, 44, "wiki...link"] linkClose:[45, 47, "]]"]
      Text[33, 37] chars:[33, 37, "wiki"]
      TypographicSmarts[37, 40] typographic: &hellip; 
      Text[40, 44] chars:[40, 44, "link"]
    SoftLineBreak[47, 48]
    WikiLink[48, 62] linkOpen:[48, 50, "[["] link:[50, 60, "wiki'link#"] pageRef:[50, 59, "wiki'link"] anchorMarker:[59, 60, "#"] anchorRef:[60, 60] text:[50, 59, "wiki'link"] linkClose:[60, 62, "]]"]
      Text[50, 54] chars:[50, 54, "wiki"]
      TypographicSmarts[54, 55] typographic: &rsquo; 
      Text[55, 59] chars:[55, 59, "link"]
    SoftLineBreak[62, 63]
    WikiLink[63, 79] linkOpen:[63, 65, "[["] link:[65, 77, "'wiki link'#"] pageRef:[65, 76, "'wiki link'"] anchorMarker:[76, 77, "#"] anchorRef:[77, 77] text:[65, 76, "'wiki link'"] linkClose:[77, 79, "]]"]
      TypographicSmarts[65, 66] typographic: &rsquo; 
      Text[66, 75] chars:[66, 75, "wiki link"]
      TypographicSmarts[75, 76] typographic: &rsquo; 
````````````````````````````````


Custom prefix with anchor ref

```````````````````````````````` example(Wiki Typographic Text: 18) options(link-prefix)
[[wiki link#anchor-ref]]
[[wiki--link#anchor-ref]]
[[wiki...link#anchor-ref]]
[[wiki'link#anchor-ref]]
[['wiki link'#anchor-ref]]
.
<p><a href="/prefix/wiki-link%23anchor-ref">wiki link#anchor-ref</a>
<a href="/prefix/wiki--link%23anchor-ref">wiki&ndash;link#anchor-ref</a>
<a href="/prefix/wiki...link%23anchor-ref">wiki&hellip;link#anchor-ref</a>
<a href="/prefix/wiki'link%23anchor-ref">wiki&rsquo;link#anchor-ref</a>
<a href="/prefix/'wiki-link'%23anchor-ref">&rsquo;wiki link&rsquo;#anchor-ref</a></p>
.
Document[0, 129]
  Paragraph[0, 129]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 22, "wiki link#anchor-ref"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … r-ref"]
    SoftLineBreak[24, 25]
    WikiLink[25, 50] linkOpen:[25, 27, "[["] link:[27, 48, "wiki--link#anchor-ref"] pageRef:[27, 48, "wiki--link#anchor-ref"] linkClose:[48, 50, "]]"]
      Text[27, 31] chars:[27, 31, "wiki"]
      TypographicSmarts[31, 33] typographic: &ndash; 
      Text[33, 48] chars:[33, 48, "link# … r-ref"]
    SoftLineBreak[50, 51]
    WikiLink[51, 77] linkOpen:[51, 53, "[["] link:[53, 75, "wiki...link#anchor-ref"] pageRef:[53, 75, "wiki...link#anchor-ref"] linkClose:[75, 77, "]]"]
      Text[53, 57] chars:[53, 57, "wiki"]
      TypographicSmarts[57, 60] typographic: &hellip; 
      Text[60, 75] chars:[60, 75, "link# … r-ref"]
    SoftLineBreak[77, 78]
    WikiLink[78, 102] linkOpen:[78, 80, "[["] link:[80, 100, "wiki'link#anchor-ref"] pageRef:[80, 100, "wiki'link#anchor-ref"] linkClose:[100, 102, "]]"]
      Text[80, 84] chars:[80, 84, "wiki"]
      TypographicSmarts[84, 85] typographic: &rsquo; 
      Text[85, 100] chars:[85, 100, "link# … r-ref"]
    SoftLineBreak[102, 103]
    WikiLink[103, 129] linkOpen:[103, 105, "[["] link:[105, 127, "'wiki link'#anchor-ref"] pageRef:[105, 127, "'wiki link'#anchor-ref"] linkClose:[127, 129, "]]"]
      TypographicSmarts[105, 106] typographic: &rsquo; 
      Text[106, 115] chars:[106, 115, "wiki link"]
      TypographicSmarts[115, 116] typographic: &rsquo; 
      Text[116, 127] chars:[116, 127, "#anch … r-ref"]
````````````````````````````````


Custom prefix with anchor ref

```````````````````````````````` example(Wiki Typographic Text: 19) options(link-prefix, allow-anchors)
[[wiki link#anchor-ref]]
[[wiki--link#anchor-ref]]
[[wiki...link#anchor-ref]]
[[wiki'link#anchor-ref]]
[['wiki link'#anchor-ref]]
.
<p><a href="/prefix/wiki-link#anchor-ref">wiki link</a>
<a href="/prefix/wiki--link#anchor-ref">wiki&ndash;link</a>
<a href="/prefix/wiki...link#anchor-ref">wiki&hellip;link</a>
<a href="/prefix/wiki'link#anchor-ref">wiki&rsquo;link</a>
<a href="/prefix/'wiki-link'#anchor-ref">&rsquo;wiki link&rsquo;</a></p>
.
Document[0, 129]
  Paragraph[0, 129]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 11, "wiki link"] anchorMarker:[11, 12, "#"] anchorRef:[12, 22, "anchor-ref"] text:[2, 11, "wiki link"] linkClose:[22, 24, "]]"]
      Text[2, 11] chars:[2, 11, "wiki link"]
    SoftLineBreak[24, 25]
    WikiLink[25, 50] linkOpen:[25, 27, "[["] link:[27, 48, "wiki--link#anchor-ref"] pageRef:[27, 37, "wiki--link"] anchorMarker:[37, 38, "#"] anchorRef:[38, 48, "anchor-ref"] text:[27, 37, "wiki--link"] linkClose:[48, 50, "]]"]
      Text[27, 31] chars:[27, 31, "wiki"]
      TypographicSmarts[31, 33] typographic: &ndash; 
      Text[33, 37] chars:[33, 37, "link"]
    SoftLineBreak[50, 51]
    WikiLink[51, 77] linkOpen:[51, 53, "[["] link:[53, 75, "wiki...link#anchor-ref"] pageRef:[53, 64, "wiki...link"] anchorMarker:[64, 65, "#"] anchorRef:[65, 75, "anchor-ref"] text:[53, 64, "wiki...link"] linkClose:[75, 77, "]]"]
      Text[53, 57] chars:[53, 57, "wiki"]
      TypographicSmarts[57, 60] typographic: &hellip; 
      Text[60, 64] chars:[60, 64, "link"]
    SoftLineBreak[77, 78]
    WikiLink[78, 102] linkOpen:[78, 80, "[["] link:[80, 100, "wiki'link#anchor-ref"] pageRef:[80, 89, "wiki'link"] anchorMarker:[89, 90, "#"] anchorRef:[90, 100, "anchor-ref"] text:[80, 89, "wiki'link"] linkClose:[100, 102, "]]"]
      Text[80, 84] chars:[80, 84, "wiki"]
      TypographicSmarts[84, 85] typographic: &rsquo; 
      Text[85, 89] chars:[85, 89, "link"]
    SoftLineBreak[102, 103]
    WikiLink[103, 129] linkOpen:[103, 105, "[["] link:[105, 127, "'wiki link'#anchor-ref"] pageRef:[105, 116, "'wiki link'"] anchorMarker:[116, 117, "#"] anchorRef:[117, 127, "anchor-ref"] text:[105, 116, "'wiki link'"] linkClose:[127, 129, "]]"]
      TypographicSmarts[105, 106] typographic: &rsquo; 
      Text[106, 115] chars:[106, 115, "wiki link"]
      TypographicSmarts[115, 116] typographic: &rsquo; 
````````````````````````````````


## Source Position Attribute

simple wiki link

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
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

```````````````````````````````` example(Source Position Attribute: 2) options(src-pos)
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

```````````````````````````````` example(Source Position Attribute: 3) options(src-pos, links-first)
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

Converts wiki image of the forms: `![[image]]`, `![[image|text]]` and `![[text|image]]` to image
images in the HTML page.

no spaces between brackets

```````````````````````````````` example(WikiImages: 1) options(wiki-images)
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

```````````````````````````````` example(WikiImages: 2) options(wiki-images)
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

```````````````````````````````` example(WikiImages: 3) options(wiki-images)
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

```````````````````````````````` example WikiImages: 4
![[wiki image]]
.
<p>!<a href="wiki-image">wiki image</a></p>
.
Document[0, 15]
  Paragraph[0, 15]
    Text[0, 1] chars:[0, 1, "!"]
    WikiLink[1, 15] linkOpen:[1, 3, "[["] link:[3, 13, "wiki image"] pageRef:[3, 13, "wiki image"] linkClose:[13, 15, "]]"]
      Text[3, 13] chars:[3, 13, "wiki image"]
````````````````````````````````


wiki image with text

```````````````````````````````` example(WikiImages: 5) options(wiki-images)
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

```````````````````````````````` example(WikiImages: 6) options(wiki-images, links-first)
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

```````````````````````````````` example(WikiImages: 7) options(wiki-images)
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

```````````````````````````````` example(WikiImages: 8) options(wiki-images)
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

```````````````````````````````` example(WikiImages: 9) options(wiki-images)
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

```````````````````````````````` example(WikiImages: 10) options(wiki-images)
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

```````````````````````````````` example(WikiImages: 11) options(wiki-images)
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


```````````````````````````````` example(WikiImages: 12) options(wiki-images)
![[wiki image]] ![^image][ref] ![[^wiki image]]
.
<p><img src="wiki-image" alt="wiki image" /> ![^image][ref] <img src="^wiki-image" alt="^wiki image" /></p>
.
Document[0, 47]
  Paragraph[0, 47]
    WikiImage[0, 15] linkOpen:[0, 3, "![["] link:[3, 13, "wiki image"] pageRef:[3, 13, "wiki image"] linkClose:[13, 15, "]]"]
      Text[3, 13] chars:[3, 13, "wiki image"]
    Text[15, 16] chars:[15, 16, " "]
    ImageRef[16, 30] textOpen:[16, 18, "!["] text:[18, 24, "^image"] textClose:[24, 25, "]"] referenceOpen:[25, 26, "["] reference:[26, 29, "ref"] referenceClose:[29, 30, "]"]
      Text[18, 24] chars:[18, 24, "^image"]
    Text[30, 31] chars:[30, 31, " "]
    WikiImage[31, 47] linkOpen:[31, 34, "![["] link:[34, 45, "^wiki image"] pageRef:[34, 45, "^wiki image"] linkClose:[45, 47, "]]"]
      Text[34, 45] chars:[34, 45, "^wiki … image"]
````````````````````````````````


Custom extension

```````````````````````````````` example(WikiImages: 13) options(wiki-images, image-ext)
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


```````````````````````````````` example(WikiImages: 14) options(wiki-images, image-ext)
![[wiki image]] ![^image][ref] ![[^wiki image]]
.
<p><img src="wiki-image.png" alt="wiki image" /> ![^image][ref] <img src="^wiki-image.png" alt="^wiki image" /></p>
.
Document[0, 47]
  Paragraph[0, 47]
    WikiImage[0, 15] linkOpen:[0, 3, "![["] link:[3, 13, "wiki image"] pageRef:[3, 13, "wiki image"] linkClose:[13, 15, "]]"]
      Text[3, 13] chars:[3, 13, "wiki image"]
    Text[15, 16] chars:[15, 16, " "]
    ImageRef[16, 30] textOpen:[16, 18, "!["] text:[18, 24, "^image"] textClose:[24, 25, "]"] referenceOpen:[25, 26, "["] reference:[26, 29, "ref"] referenceClose:[29, 30, "]"]
      Text[18, 24] chars:[18, 24, "^image"]
    Text[30, 31] chars:[30, 31, " "]
    WikiImage[31, 47] linkOpen:[31, 34, "![["] link:[34, 45, "^wiki image"] pageRef:[34, 45, "^wiki image"] linkClose:[45, 47, "]]"]
      Text[34, 45] chars:[34, 45, "^wiki … image"]
````````````````````````````````


Custom prefix

```````````````````````````````` example(WikiImages: 15) options(wiki-images, image-prefix)
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

```````````````````````````````` example(WikiImages: 16) options(wiki-images, image-prefix)
![[wiki image]] ![^image][ref] ![[^wiki image]]
.
<p><img src="/images/wiki-image" alt="wiki image" /> ![^image][ref] <img src="/images/^wiki-image" alt="^wiki image" /></p>
.
Document[0, 47]
  Paragraph[0, 47]
    WikiImage[0, 15] linkOpen:[0, 3, "![["] link:[3, 13, "wiki image"] pageRef:[3, 13, "wiki image"] linkClose:[13, 15, "]]"]
      Text[3, 13] chars:[3, 13, "wiki image"]
    Text[15, 16] chars:[15, 16, " "]
    ImageRef[16, 30] textOpen:[16, 18, "!["] text:[18, 24, "^image"] textClose:[24, 25, "]"] referenceOpen:[25, 26, "["] reference:[26, 29, "ref"] referenceClose:[29, 30, "]"]
      Text[18, 24] chars:[18, 24, "^image"]
    Text[30, 31] chars:[30, 31, " "]
    WikiImage[31, 47] linkOpen:[31, 34, "![["] link:[34, 45, "^wiki image"] pageRef:[34, 45, "^wiki image"] linkClose:[45, 47, "]]"]
      Text[34, 45] chars:[34, 45, "^wiki … image"]
````````````````````````````````


With empty anchor ref

```````````````````````````````` example(WikiImages: 17) options(wiki-images)
![[wiki image#]] 
.
<p><img src="wiki-image%23" alt="wiki image#" /></p>
.
Document[0, 17]
  Paragraph[0, 17]
    WikiImage[0, 16] linkOpen:[0, 3, "![["] link:[3, 14, "wiki image#"] pageRef:[3, 14, "wiki image#"] linkClose:[14, 16, "]]"]
      Text[3, 14] chars:[3, 14, "wiki  … mage#"]
````````````````````````````````


With Anchor ref

```````````````````````````````` example(WikiImages: 18) options(wiki-images)
![[wiki image#anchor-ref]] 
.
<p><img src="wiki-image%23anchor-ref" alt="wiki image#anchor-ref" /></p>
.
Document[0, 27]
  Paragraph[0, 27]
    WikiImage[0, 26] linkOpen:[0, 3, "![["] link:[3, 24, "wiki image#anchor-ref"] pageRef:[3, 24, "wiki image#anchor-ref"] linkClose:[24, 26, "]]"]
      Text[3, 24] chars:[3, 24, "wiki  … r-ref"]
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example(WikiImages: 19) options(wiki-images)
![[alt text|wiki image#]] 
.
<p><img src="wiki-image%23" alt="alt text" /></p>
.
Document[0, 26]
  Paragraph[0, 26]
    WikiImage[0, 25] linkOpen:[0, 3, "![["] link:[12, 23, "wiki image#"] pageRef:[12, 23, "wiki image#"] textSep:[11, 12, "|"] text:[3, 11, "alt text"] linkClose:[23, 25, "]]"]
      Text[3, 11] chars:[3, 11, "alt text"]
````````````````````````````````


With text, anchor ref

```````````````````````````````` example(WikiImages: 20) options(wiki-images)
![[alt text|wiki image#anchor-ref]] 
.
<p><img src="wiki-image%23anchor-ref" alt="alt text" /></p>
.
Document[0, 36]
  Paragraph[0, 36]
    WikiImage[0, 35] linkOpen:[0, 3, "![["] link:[12, 33, "wiki image#anchor-ref"] pageRef:[12, 33, "wiki image#anchor-ref"] textSep:[11, 12, "|"] text:[3, 11, "alt text"] linkClose:[33, 35, "]]"]
      Text[3, 11] chars:[3, 11, "alt text"]
````````````````````````````````


Links first, with text, empty anchor ref

```````````````````````````````` example(WikiImages: 21) options(wiki-images, links-first)
![[wiki image#|alt text]] 
.
<p><img src="wiki-image%23" alt="alt text" /></p>
.
Document[0, 26]
  Paragraph[0, 26]
    WikiImage[0, 25] linkOpen:[0, 3, "![["] text:[15, 23, "alt text"] textSep:[14, 15, "|"] link:[3, 14, "wiki image#"] pageRef:[3, 14, "wiki image#"] linkClose:[23, 25, "]]"]
      Text[15, 23] chars:[15, 23, "alt text"]
````````````````````````````````


Links first, with text, anchor ref

```````````````````````````````` example(WikiImages: 22) options(wiki-images, links-first)
![[wiki image#anchor-ref|alt text]] 
.
<p><img src="wiki-image%23anchor-ref" alt="alt text" /></p>
.
Document[0, 36]
  Paragraph[0, 36]
    WikiImage[0, 35] linkOpen:[0, 3, "![["] text:[25, 33, "alt text"] textSep:[24, 25, "|"] link:[3, 24, "wiki image#anchor-ref"] pageRef:[3, 24, "wiki image#anchor-ref"] linkClose:[33, 35, "]]"]
      Text[25, 33] chars:[25, 33, "alt text"]
````````````````````````````````


Custom extension with empty Anchor ref

```````````````````````````````` example(WikiImages: 23) options(wiki-images, image-ext)
![[wiki image#]] 
.
<p><img src="wiki-image%23.png" alt="wiki image#" /></p>
.
Document[0, 17]
  Paragraph[0, 17]
    WikiImage[0, 16] linkOpen:[0, 3, "![["] link:[3, 14, "wiki image#"] pageRef:[3, 14, "wiki image#"] linkClose:[14, 16, "]]"]
      Text[3, 14] chars:[3, 14, "wiki  … mage#"]
````````````````````````````````


Custom extension with anchor ref

```````````````````````````````` example(WikiImages: 24) options(wiki-images, image-ext)
![[wiki image#anchor-ref]] 
.
<p><img src="wiki-image%23anchor-ref.png" alt="wiki image#anchor-ref" /></p>
.
Document[0, 27]
  Paragraph[0, 27]
    WikiImage[0, 26] linkOpen:[0, 3, "![["] link:[3, 24, "wiki image#anchor-ref"] pageRef:[3, 24, "wiki image#anchor-ref"] linkClose:[24, 26, "]]"]
      Text[3, 24] chars:[3, 24, "wiki  … r-ref"]
````````````````````````````````


Custom prefix with empty anchor ref

```````````````````````````````` example(WikiImages: 25) options(wiki-images, image-prefix)
![[wiki image#]]
.
<p><img src="/images/wiki-image%23" alt="wiki image#" /></p>
.
Document[0, 16]
  Paragraph[0, 16]
    WikiImage[0, 16] linkOpen:[0, 3, "![["] link:[3, 14, "wiki image#"] pageRef:[3, 14, "wiki image#"] linkClose:[14, 16, "]]"]
      Text[3, 14] chars:[3, 14, "wiki  … mage#"]
````````````````````````````````


Custom prefix with anchor ref

```````````````````````````````` example(WikiImages: 26) options(wiki-images, image-prefix)
![[wiki image#anchor-ref]]
.
<p><img src="/images/wiki-image%23anchor-ref" alt="wiki image#anchor-ref" /></p>
.
Document[0, 26]
  Paragraph[0, 26]
    WikiImage[0, 26] linkOpen:[0, 3, "![["] link:[3, 24, "wiki image#anchor-ref"] pageRef:[3, 24, "wiki image#anchor-ref"] linkClose:[24, 26, "]]"]
      Text[3, 24] chars:[3, 24, "wiki  … r-ref"]
````````````````````````````````


Absolute image prefix

```````````````````````````````` example(WikiImages: 27) options(wiki-images, custom-link-escape, image-prefix-absolute)
![[Img]]

![[dir/Img]]

![[/Img]]

![[/dir/Img]]
.
<p><img src="/relative/images/Img" alt="Img" /></p>
<p><img src="/relative/images/dir/Img" alt="dir/Img" /></p>
<p><img src="/absolute/images/Img" alt="/Img" /></p>
<p><img src="/absolute/images/dir/Img" alt="/dir/Img" /></p>
.
Document[0, 48]
  Paragraph[0, 9] isTrailingBlankLine
    WikiImage[0, 8] linkOpen:[0, 3, "![["] link:[3, 6, "Img"] pageRef:[3, 6, "Img"] linkClose:[6, 8, "]]"]
      Text[3, 6] chars:[3, 6, "Img"]
  Paragraph[10, 23] isTrailingBlankLine
    WikiImage[10, 22] linkOpen:[10, 13, "![["] link:[13, 20, "dir/Img"] pageRef:[13, 20, "dir/Img"] linkClose:[20, 22, "]]"]
      Text[13, 20] chars:[13, 20, "dir/Img"]
  Paragraph[24, 34] isTrailingBlankLine
    WikiImage[24, 33] linkOpen:[24, 27, "![["] link:[27, 31, "/Img"] pageRef:[27, 31, "/Img"] linkClose:[31, 33, "]]"]
      Text[27, 31] chars:[27, 31, "/Img"]
  Paragraph[35, 48]
    WikiImage[35, 48] linkOpen:[35, 38, "![["] link:[38, 46, "/dir/Img"] pageRef:[38, 46, "/dir/Img"] linkClose:[46, 48, "]]"]
      Text[38, 46] chars:[38, 46, "/dir/Img"]
````````````````````````````````


### In Links


```````````````````````````````` example(WikiImages - In Links: 1) options(wiki-images)
[![[wiki]]](/url)
.
<p><a href="/url"><img src="wiki" alt="wiki" /></a></p>
.
Document[0, 17]
  Paragraph[0, 17]
    Link[0, 17] textOpen:[0, 1, "["] text:[1, 10, "![[wiki]]"] textClose:[10, 11, "]"] linkOpen:[11, 12, "("] url:[12, 16, "/url"] pageRef:[12, 16, "/url"] linkClose:[16, 17, ")"]
      WikiImage[1, 10] linkOpen:[1, 4, "![["] link:[4, 8, "wiki"] pageRef:[4, 8, "wiki"] linkClose:[8, 10, "]]"]
        Text[4, 8] chars:[4, 8, "wiki"]
````````````````````````````````


```````````````````````````````` example(WikiImages - In Links: 2) options(wiki-images, link-text-priority)
[![[wiki]]](/url)
.
<p><a href="/url"><img src="wiki" alt="wiki" /></a></p>
.
Document[0, 17]
  Paragraph[0, 17]
    Link[0, 17] textOpen:[0, 1, "["] text:[1, 10, "![[wiki]]"] textClose:[10, 11, "]"] linkOpen:[11, 12, "("] url:[12, 16, "/url"] pageRef:[12, 16, "/url"] linkClose:[16, 17, ")"]
      WikiImage[1, 10] linkOpen:[1, 4, "![["] link:[4, 8, "wiki"] pageRef:[4, 8, "wiki"] linkClose:[8, 10, "]]"]
        Text[4, 8] chars:[4, 8, "wiki"]
````````````````````````````````


no wiki image in link refs

```````````````````````````````` example(WikiImages - In Links: 3) options(wiki-images)
[![[wiki]]][ref]
    
[ref]: /url
.
<p>[<img src="wiki" alt="wiki" />]<a href="/url">ref</a></p>
.
Document[0, 33]
  Paragraph[0, 17] isTrailingBlankLine
    Text[0, 1] chars:[0, 1, "["]
    WikiImage[1, 10] linkOpen:[1, 4, "![["] link:[4, 8, "wiki"] pageRef:[4, 8, "wiki"] linkClose:[8, 10, "]]"]
      Text[4, 8] chars:[4, 8, "wiki"]
    Text[10, 11] chars:[10, 11, "]"]
    LinkRef[11, 16] referenceOpen:[11, 12, "["] reference:[12, 15, "ref"] referenceClose:[15, 16, "]"]
      Text[12, 15] chars:[12, 15, "ref"]
  Reference[22, 33] refOpen:[22, 23, "["] ref:[23, 26, "ref"] refClose:[26, 28, "]:"] url:[29, 33, "/url"]
````````````````````````````````


```````````````````````````````` example(WikiImages - In Links: 4) options(wiki-images, link-text-priority)
[![[wiki]]][ref]
    
[ref]: /url
.
<p>[<img src="wiki" alt="wiki" />]<a href="/url">ref</a></p>
.
Document[0, 33]
  Paragraph[0, 17] isTrailingBlankLine
    Text[0, 1] chars:[0, 1, "["]
    WikiImage[1, 10] linkOpen:[1, 4, "![["] link:[4, 8, "wiki"] pageRef:[4, 8, "wiki"] linkClose:[8, 10, "]]"]
      Text[4, 8] chars:[4, 8, "wiki"]
    Text[10, 11] chars:[10, 11, "]"]
    LinkRef[11, 16] referenceOpen:[11, 12, "["] reference:[12, 15, "ref"] referenceClose:[15, 16, "]"]
      Text[12, 15] chars:[12, 15, "ref"]
  Reference[22, 33] refOpen:[22, 23, "["] ref:[23, 26, "ref"] refClose:[26, 28, "]:"] url:[29, 33, "/url"]
````````````````````````````````


## WikiImages Source Position Attribute

simple wiki image

```````````````````````````````` example(WikiImages Source Position Attribute: 1) options(wiki-images, src-pos)
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

```````````````````````````````` example(WikiImages Source Position Attribute: 2) options(wiki-images, src-pos)
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

```````````````````````````````` example(WikiImages Source Position Attribute: 3) options(wiki-images, src-pos, links-first)
![[wiki image|alt text]]
.
<p md-pos="0-24"><img src="wiki-image" alt="alt text" md-pos="0-24" /></p>
.
Document[0, 24]
  Paragraph[0, 24]
    WikiImage[0, 24] linkOpen:[0, 3, "![["] text:[14, 22, "alt text"] textSep:[13, 14, "|"] link:[3, 13, "wiki image"] pageRef:[3, 13, "wiki image"] linkClose:[22, 24, "]]"]
      Text[14, 22] chars:[14, 22, "alt text"]
````````````````````````````````


