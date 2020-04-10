---
title: WikiLinks Formatter Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## WikiLinks

Converts wikilink of the forms: `[[link]]`, `[[link|text]]` and `[[text|link]]` to links in the
HTML page.

simple wiki link

```````````````````````````````` example WikiLinks: 1
[[wiki link]]
.
[[WiIKiI LiINK|wiki link]]
````````````````````````````````


simple wiki link with anchor ref

```````````````````````````````` example WikiLinks: 2
[[wiki link#anchor-ref]]
.
[[WiIKiI LiINK#aANchoR-ReEF|wiki link#anchor-ref]]
.
Document[0, 24]
  Paragraph[0, 24]
    WikiLink[0, 24] linkOpen:[0, 2, "[["] link:[2, 22, "wiki link#anchor-ref"] pageRef:[2, 22, "wiki link#anchor-ref"] linkClose:[22, 24, "]]"]
      Text[2, 22] chars:[2, 22, "wiki  … r-ref"]
````````````````````````````````


simple wiki link with anchor ref

```````````````````````````````` example(WikiLinks: 3) options(allow-anchors)
[[wiki link#anchor-ref]]
.
[[WiIKiI LiINK|wiki link#anchor-ref]]
````````````````````````````````


simple wiki link with anchor-ref

```````````````````````````````` example(WikiLinks: 4) options(allow-anchors, allow-anchor-escape)
[[wiki link#anchor-ref]]
.
[[WiIKiI LiINK|wiki link#anchor-ref]]
````````````````````````````````


simple wiki link with escaped anchor-ref

```````````````````````````````` example WikiLinks: 5
[[wiki link#anchor-ref]]
[[wiki link\#anchor-ref]]
[[wiki link\\#anchor-ref]]
[[wiki link\\\#anchor-ref]]
.
[[WiIKiI LiINK#aANchoR-ReEF|wiki link#anchor-ref]]
[[WiIKiI LiINK#aANchoR-ReEF|wiki link\#anchor-ref]]
[[WiIKiI LiINK\\#aANchoR-ReEF|wiki link\\#anchor-ref]]
[[WiIKiI LiINK\\#aANchoR-ReEF|wiki link\\\#anchor-ref]]
````````````````````````````````


simple wiki link with escaped anchor-ref

```````````````````````````````` example(WikiLinks: 6) options(allow-anchors)
[[wiki link\#anchor-ref]]
.
[[WiIKiI LiINK\\|wiki link\\#anchor-ref]]
.
Document[0, 25]
  Paragraph[0, 25]
    WikiLink[0, 25] linkOpen:[0, 2, "[["] link:[2, 23, "wiki link\#anchor-ref"] pageRef:[2, 12, "wiki link\"] anchorMarker:[12, 13, "#"] anchorRef:[13, 23, "anchor-ref"] text:[2, 12, "wiki link\"] linkClose:[23, 25, "]]"]
      Text[2, 12] chars:[2, 12, "wiki link\"]
````````````````````````````````


simple wiki link with escaped anchor-ref

```````````````````````````````` example(WikiLinks: 7) options(allow-anchors)
[[wiki link\\#anchor-ref]]
.
[[WiIKiI LiINK\\|wiki link\\#anchor-ref]]
.
Document[0, 26]
  Paragraph[0, 26]
    WikiLink[0, 26] linkOpen:[0, 2, "[["] link:[2, 24, "wiki link\\#anchor-ref"] pageRef:[2, 13, "wiki link\\"] anchorMarker:[13, 14, "#"] anchorRef:[14, 24, "anchor-ref"] text:[2, 13, "wiki link\\"] linkClose:[24, 26, "]]"]
      Text[2, 13] chars:[2, 13, "wiki  … ink\\"]
````````````````````````````````


simple wiki link with escaped anchor-ref

```````````````````````````````` example(WikiLinks: 8) options(allow-anchors)
[[wiki link\\\#anchor-ref]]
.
[[WiIKiI LiINK\\\\|wiki link\\\\#anchor-ref]]
.
Document[0, 27]
  Paragraph[0, 27]
    WikiLink[0, 27] linkOpen:[0, 2, "[["] link:[2, 25, "wiki link\\\#anchor-ref"] pageRef:[2, 14, "wiki link\\\"] anchorMarker:[14, 15, "#"] anchorRef:[15, 25, "anchor-ref"] text:[2, 14, "wiki link\\\"] linkClose:[25, 27, "]]"]
      Text[2, 14] chars:[2, 14, "wiki  … nk\\\"]
````````````````````````````````


simple wiki link with escaped anchor-ref

```````````````````````````````` example(WikiLinks: 9) options(allow-anchors)
[[wiki link#anchor-ref]]
[[wiki link\#anchor-ref]]
[[wiki link\\#anchor-ref]]
[[wiki link\\\#anchor-ref]]
.
[[WiIKiI LiINK|wiki link#anchor-ref]]
[[WiIKiI LiINK\\|wiki link\\#anchor-ref]]
[[WiIKiI LiINK\\|wiki link\\#anchor-ref]]
[[WiIKiI LiINK\\\\|wiki link\\\\#anchor-ref]]
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
[[WiIKiI LiINK|wiki link#anchor-ref]]
[[WiIKiI LiINK\#aANchoR-ReEF|wiki link\#anchor-ref]]
[[WiIKiI LiINK\\|wiki link\\#anchor-ref]]
[[WiIKiI LiINK\\\#aANchoR-ReEF|wiki link\\\#anchor-ref]]
````````````````````````````````


wiki link with text

```````````````````````````````` example WikiLinks: 11
[[wiki text|wiki link]]
.
[[WiIKiI teEXt|wiki link]]
````````````````````````````````


wiki link with text, links first option

```````````````````````````````` example(WikiLinks: 12) options(links-first)
[[wiki link|wiki text]]
.
[[wiki link|WiIKiI teEXt]]
````````````````````````````````


wiki link with text

```````````````````````````````` example WikiLinks: 13
[[wiki text\|wiki link]]
.
[[WiIKiI teEXt\\|wiki link]]
````````````````````````````````


wiki link with text, links first option

```````````````````````````````` example(WikiLinks: 14) options(links-first)
[[wiki link\|wiki text]]
.
[[wiki link\\|WiIKiI teEXt]]
````````````````````````````````


wiki link with text

```````````````````````````````` example(WikiLinks: 15) options(allow-pipe-escape)
[[wiki text\|wiki link]]
.
[[WiIKiI teEXt\|WiIKiI LiINK|wiki text\|wiki link]]
````````````````````````````````


wiki link with text, links first option

```````````````````````````````` example(WikiLinks: 16) options(links-first, allow-pipe-escape)
[[wiki link\|wiki text]]
.
[[wiki link\|wiki text|WiIKiI LiINK\|WiIKiI teEXt]]
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
[[WiIKiI teEXt|more text|wiki link]]
[[WiIKiI teEXt\\|more text|wiki link]]
[[WiIKiI teEXt\\|more text|wiki link]]
[[WiIKiI teEXt\\\\|more text|wiki link]]
[[WiIKiI teEXt|more text\|wiki link]]
[[WiIKiI teEXt\\|more text\|wiki link]]
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
[[wiki link|wiki text|moReE teEXt]]
[[wiki link\|wiki text|moReE teEXt]]
[[wiki text\\|more text|WiIKiI LiINK]]
[[wiki text\\\|more text|WiIKiI LiINK]]
[[wiki link|wiki text\\|moReE teEXt]]
[[wiki link\|wiki text\\|moReE teEXt]]
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
[[WiIKiI teEXt|more text\|wiki link]]
[[WiIKiI teEXt\|moReE teEXt|wiki link]]
[[WiIKiI teEXt\\|more text\|wiki link]]
[[WiIKiI teEXt\\\|moReE teEXt|wiki link]]
[[WiIKiI teEXt|more text\|wiki link]]
[[WiIKiI teEXt\|moReE teEXt\|WiIKiI LiINK|wiki text\|more text\|wiki link]]
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
[[wiki link\|wiki text|moReE teEXt]]
[[wiki link\|wiki text|moReE teEXt]]
[[wiki text\\\|more text|WiIKiI LiINK]]
[[wiki text\\\|more text|WiIKiI LiINK]]
[[wiki link|WiIKiI teEXt\|moReE teEXt]]
[[wiki link\|wiki text\|more text|WiIKiI LiINK\|WiIKiI teEXt\|moReE teEXt]]
````````````````````````````````


simple wiki link with inlines disabled

```````````````````````````````` example WikiLinks: 21
[[**wiki link**]]
.
[[**WiIKiI LiINK**|**wiki link**]]
````````````````````````````````


simple wiki link with inlines

```````````````````````````````` example(WikiLinks: 22) options(allow-inlines)
[[**wiki link**]]
.
[[**WiIKiI LiINK**|wiki link]]
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
[[**WiIKiI teEXt**|wiki link]]
````````````````````````````````


wiki link with text with inlines split

```````````````````````````````` example(WikiLinks: 24) options(allow-inlines)
[[**wiki text|wiki** link]]
.
[[**WiIKiI teEXt|wiki** link]]
````````````````````````````````


wiki link with text, links first option with inlines

```````````````````````````````` example(WikiLinks: 25) options(links-first, allow-inlines)
[[wiki link|**wiki text**]]
.
[[wiki link|**WiIKiI teEXt**]]
````````````````````````````````


wiki link with text, links first option with inlines split

```````````````````````````````` example(WikiLinks: 26) options(links-first, allow-inlines)
[[wiki **link|wiki** text]]
.
[[wiki **link|WiIKiI** teEXt]]
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
[[WiIKiI teEXt|more text|wiki link]]
[[WiIKiI teEXt\|more text|wiki link]]
[[WiIKiI **teEXt\|more** text|wiki link]]
[[WiIKiI teEXt|more text\|wiki link]]
[[WiIKiI **teEXt|more** text\|wiki link]]
[[WiIKiI teEXt\|more text\|wiki link]]
[[WiIKiI **teEXt\|more** text\|wiki link]]
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
[[wiki link|wiki text|moReE teEXt]]
[[wiki link\|wiki text|moReE teEXt]]
[[wiki **text\|more** text|WiIKiI LiINK]]
[[wiki text|more text\\|WiIKiI LiINK]]
[[wiki **text|more** text\\|WiIKiI LiINK]]
[[wiki link\|wiki text\\|moReE teEXt]]
[[wiki **link\|wiki** text\\|moReE teEXt]]
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
[[WiIKiI teEXt|more text\|wiki link]]
[[WiIKiI teEXt\|moReE teEXt|wiki link]]
[[WiIKiI **teEXt\|moReE** teEXt|wiki link]]
[[WiIKiI teEXt|more text\|wiki link]]
[[WiIKiI **teEXt|more** text\|wiki link]]
[[WiIKiI teEXt\|moReE teEXt\|WiIKiI LiINK|wiki text\|more text\|wiki link]]
[[WiIKiI **teEXt\|moReE** teEXt\|WiIKiI LiINK|wiki text\|more text\|wiki link]]
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
[[wiki link\|wiki text|moReE teEXt]]
[[wiki link\|wiki text|moReE teEXt]]
[[wiki **text\|more** text|WiIKiI LiINK]]
[[wiki text|moReE teEXt\|WiIKiI LiINK]]
[[wiki **text|moReE** teEXt\|WiIKiI LiINK]]
[[wiki link\|wiki text\|more text|WiIKiI LiINK\|WiIKiI teEXt\|moReE teEXt]]
[[wiki link\|wiki text\|more text|WiIKiI **LiINK\|WiIKiI** teEXt\|moReE teEXt]]
````````````````````````````````


simple wiki link with ! before

```````````````````````````````` example WikiLinks: 31
![[wiki link]]
.
![[WiIKiI LiINK|wiki link]]
````````````````````````````````


wiki link with text with ! before

```````````````````````````````` example WikiLinks: 32
![[wiki text|wiki link]]
.
![[WiIKiI teEXt|wiki link]]
````````````````````````````````


reference following will be a reference, even if not defined

```````````````````````````````` example WikiLinks: 33
[[wiki link]][ref]
.
[[WiIKiI LiINK|wiki link]][ReEF]
````````````````````````````````


reference following will be a reference

```````````````````````````````` example WikiLinks: 34
[[wiki link]][ref]

[ref]: /url
.
[[WiIKiI LiINK|wiki link]][ReEF]

[ReEF]: /url
````````````````````````````````


dummy reference following will be an empty reference

```````````````````````````````` example WikiLinks: 35
[[wiki link]][]
.
[[WiIKiI LiINK|wiki link]][]
````````````````````````````````


reference inside is not a wiki link but a link ref with brackets around it

```````````````````````````````` example WikiLinks: 36
[[not wiki link][ref]]
.
[[Not WiIKiI LiINK][ReEF]]
````````````````````````````````


dummy reference inside is not a wiki link but a link ref with brackets around it

```````````````````````````````` example WikiLinks: 37
[[not wiki link][]]
.
[[Not WiIKiI LiINK][]]
````````````````````````````````


```````````````````````````````` example WikiLinks: 38
[[wiki link]] [^link][ref] [[^wiki link]]
.
[[WiIKiI LiINK|wiki link]] [^LiINK][ReEF] [[^WiIKiI LiINK|^wiki link]]
````````````````````````````````


Exclamation before is just text

```````````````````````````````` example WikiLinks: 39
![[wiki link]] [^link][ref] [[^wiki link]] [[wiki]][ref]
.
![[WiIKiI LiINK|wiki link]] [^LiINK][ReEF] [[^WiIKiI LiINK|^wiki link]] [[WiIKiI|wiki]][ReEF]
````````````````````````````````


Custom extension

```````````````````````````````` example(WikiLinks: 40) options(link-ext)
[[wiki link]] [^link][ref] [[^wiki link]]
.
[[WiIKiI LiINK|wiki link]] [^LiINK][ReEF] [[^WiIKiI LiINK|^wiki link]]
````````````````````````````````


Custom prefix

```````````````````````````````` example(WikiLinks: 41) options(link-prefix)
[[wiki link]] [^link][ref] [[^wiki link]]
.
[[WiIKiI LiINK|wiki link]] [^LiINK][ReEF] [[^WiIKiI LiINK|^wiki link]]
````````````````````````````````


With empty anchor ref

```````````````````````````````` example WikiLinks: 42
[[wiki link#]] 
.
[[WiIKiI LiINK#|wiki link#]]
````````````````````````````````


With empty anchor ref

```````````````````````````````` example(WikiLinks: 43) options(allow-anchors)
[[wiki link#]] 
.
[[WiIKiI LiINK|wiki link#]]
````````````````````````````````


With Anchor ref

```````````````````````````````` example WikiLinks: 44
[[wiki link#anchor-ref]] 
.
[[WiIKiI LiINK#aANchoR-ReEF|wiki link#anchor-ref]]
````````````````````````````````


With Anchor ref

```````````````````````````````` example(WikiLinks: 45) options(allow-anchors)
[[wiki link#anchor-ref]] 
.
[[WiIKiI LiINK|wiki link#anchor-ref]]
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example WikiLinks: 46
[[wiki text|wiki link#]] 
.
[[WiIKiI teEXt|wiki link#]]
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example(WikiLinks: 47) options(allow-anchors)
[[wiki text|wiki link#]] 
.
[[WiIKiI teEXt|wiki link#]]
````````````````````````````````


With text, anchor ref

```````````````````````````````` example WikiLinks: 48
[[wiki text|wiki link#anchor-ref]] 
.
[[WiIKiI teEXt|wiki link#anchor-ref]]
````````````````````````````````


With text, anchor ref

```````````````````````````````` example(WikiLinks: 49) options(allow-anchors)
[[wiki text|wiki link#anchor-ref]] 
.
[[WiIKiI teEXt|wiki link#anchor-ref]]
````````````````````````````````


Links first, with text, empty anchor ref

```````````````````````````````` example(WikiLinks: 50) options(links-first)
[[wiki link#|wiki text]] 
.
[[wiki link#|WiIKiI teEXt]]
````````````````````````````````


Links first, with text, anchor ref

```````````````````````````````` example(WikiLinks: 51) options(links-first)
[[wiki link#anchor-ref|wiki text]] 
.
[[wiki link#anchor-ref|WiIKiI teEXt]]
````````````````````````````````


Links first, with text, anchor ref

```````````````````````````````` example(WikiLinks: 52) options(links-first, allow-anchors)
[[wiki link#anchor-ref|wiki text]] 
.
[[wiki link#anchor-ref|WiIKiI teEXt]]
````````````````````````````````


Custom extension with empty Anchor ref

```````````````````````````````` example(WikiLinks: 53) options(link-ext)
[[wiki link#]] 
.
[[WiIKiI LiINK#|wiki link#]]
````````````````````````````````


Custom extension with empty Anchor ref

```````````````````````````````` example(WikiLinks: 54) options(link-ext, allow-anchors)
[[wiki link#]] 
.
[[WiIKiI LiINK|wiki link#]]
````````````````````````````````


Custom extension with anchor ref

```````````````````````````````` example(WikiLinks: 55) options(link-ext)
[[wiki link#anchor-ref]] 
.
[[WiIKiI LiINK#aANchoR-ReEF|wiki link#anchor-ref]]
````````````````````````````````


Custom extension with anchor ref

```````````````````````````````` example(WikiLinks: 56) options(link-ext, allow-anchors)
[[wiki link#anchor-ref]] 
.
[[WiIKiI LiINK|wiki link#anchor-ref]]
````````````````````````````````


Custom prefix with empty anchor ref

```````````````````````````````` example(WikiLinks: 57) options(link-prefix)
[[wiki link#]]
.
[[WiIKiI LiINK#|wiki link#]]
````````````````````````````````


Custom prefix with empty anchor ref

```````````````````````````````` example(WikiLinks: 58) options(link-prefix, allow-anchors)
[[wiki link#]]
.
[[WiIKiI LiINK|wiki link#]]
````````````````````````````````


Custom prefix with anchor ref

```````````````````````````````` example(WikiLinks: 59) options(link-prefix)
[[wiki link#anchor-ref]]
.
[[WiIKiI LiINK#aANchoR-ReEF|wiki link#anchor-ref]]
````````````````````````````````


Custom prefix with anchor ref

```````````````````````````````` example(WikiLinks: 60) options(link-prefix, allow-anchors)
[[wiki link#anchor-ref]]
.
[[WiIKiI LiINK|wiki link#anchor-ref]]
````````````````````````````````


Custom link escape

With configuration options `LINK_ESCAPE_CHARS` and `LINK_REPLACE_CHARS` it is possible to change
the link escaping. `LINK_ESCAPE_CHARS` gives the character to escape in links.
`LINK_REPLACE_CHARS` gives the correponding replacement characters.

```````````````````````````````` example(WikiLinks: 61) options(custom-link-escape)
See [[My Page]].
.
seEeE [[myY paAGeE|My Page]].
````````````````````````````````


Links to virtual directories

With configuration options `LINK_ESCAPE_CHARS` and `LINK_REPLACE_CHARS` it is possible to create
wiki links to virtual directories (links containing the `/` character.

```````````````````````````````` example(WikiLinks: 62) options(custom-link-escape)
See [[directory/WikiPage]].
.
seEeE [[DiIReEctoRyY/wiIKiIpaAGeE|directory/WikiPage]].
````````````````````````````````


Absolute link prefix

```````````````````````````````` example(WikiLinks: 63) options(custom-link-escape, link-prefix-absolute)
[[Page]]

[[dir/Page]]

[[/Page]]

[[/dir/Page]]
.
[[paAGeE|Page]]

[[DiIR/paAGeE|dir/Page]]

[[/paAGeE|/Page]]

[[/DiIR/paAGeE|/dir/Page]]
````````````````````````````````


Absolute image prefix

```````````````````````````````` example(WikiLinks: 64) options(wiki-images, custom-link-escape, image-prefix-absolute)
![[Img]]

![[dir/Img]]

![[/Img]]

![[/dir/Img]]
.
![[imG|Img]]

![[DiIR/imG|dir/Img]]

![[/imG|/Img]]

![[/DiIR/imG|/dir/Img]]
````````````````````````````````


## WikiImages

Converts wiki image of the forms: `![[image]]`, `![[image|text]]` and `![[text|image]]` to image
images in the HTML page.

no spaces between brackets

simple wiki image

```````````````````````````````` example(WikiImages: 1) options(wiki-images)
![[wiki image]]
.
![[WiIKiI iImaAGeE|wiki image]]
````````````````````````````````


simple wiki image ignored without wiki images option

```````````````````````````````` example WikiImages: 2
![[wiki image]]
.
![[WiIKiI iImaAGeE|wiki image]]
````````````````````````````````


wiki image with text

```````````````````````````````` example(WikiImages: 3) options(wiki-images)
![[alt text|wiki image]]
.
![[aALt teEXt|wiki image]]
````````````````````````````````


wiki image with text, images first option

```````````````````````````````` example(WikiImages: 4) options(wiki-images, links-first)
![[wiki image|alt text]]
.
![[wiki image|aALt teEXt]]
````````````````````````````````


reference following will be a reference, even if not defined

```````````````````````````````` example(WikiImages: 5) options(wiki-images)
![[wiki image]][ref]
.
![[WiIKiI iImaAGeE|wiki image]][ReEF]
````````````````````````````````


reference following will be a reference

```````````````````````````````` example(WikiImages: 6) options(wiki-images)
![[wiki image]][ref]

[ref]: /url
.
![[WiIKiI iImaAGeE|wiki image]][ReEF]

[ReEF]: /url
````````````````````````````````


dummy reference following will be an empty reference

```````````````````````````````` example(WikiImages: 7) options(wiki-images)
![[wiki image]][]
.
![[WiIKiI iImaAGeE|wiki image]][]
````````````````````````````````


reference inside is not a wiki image but a image ref with brackets around it

```````````````````````````````` example(WikiImages: 8) options(wiki-images)
![[not wiki image][ref]]
.
![[Not WiIKiI iImaAGeE][ReEF]]
````````````````````````````````


dummy reference inside is not a wiki image but a image ref with brackets around it

```````````````````````````````` example(WikiImages: 9) options(wiki-images)
![[not wiki image][]]
.
![[Not WiIKiI iImaAGeE][]]
````````````````````````````````


```````````````````````````````` example(WikiImages: 10) options(wiki-images)
![[wiki image]] ![^image][ref] ![[^wiki image]]
.
![[WiIKiI iImaAGeE|wiki image]] ![^iImaAGeE][ReEF] ![[^WiIKiI iImaAGeE|^wiki image]]
````````````````````````````````


Custom extension

```````````````````````````````` example(WikiImages: 11) options(wiki-images, image-ext)
[[wiki image]] [^image][ref] [[^wiki image]]
.
[[WiIKiI iImaAGeE|wiki image]] [^iImaAGeE][ReEF] [[^WiIKiI iImaAGeE|^wiki image]]
````````````````````````````````


```````````````````````````````` example(WikiImages: 12) options(wiki-images, image-ext)
![[wiki image]] ![^image][ref] ![[^wiki image]]
.
![[WiIKiI iImaAGeE|wiki image]] ![^iImaAGeE][ReEF] ![[^WiIKiI iImaAGeE|^wiki image]]
````````````````````````````````


Custom prefix

```````````````````````````````` example(WikiImages: 13) options(wiki-images, image-prefix)
[[wiki image]] [^image][ref] [[^wiki image]]
.
[[WiIKiI iImaAGeE|wiki image]] [^iImaAGeE][ReEF] [[^WiIKiI iImaAGeE|^wiki image]]
````````````````````````````````


Custom prefix

```````````````````````````````` example(WikiImages: 14) options(wiki-images, image-prefix)
![[wiki image]] ![^image][ref] ![[^wiki image]]
.
![[WiIKiI iImaAGeE|wiki image]] ![^iImaAGeE][ReEF] ![[^WiIKiI iImaAGeE|^wiki image]]
````````````````````````````````


With empty anchor ref

```````````````````````````````` example(WikiImages: 15) options(wiki-images)
![[wiki image#]] 
.
![[WiIKiI iImaAGeE#|wiki image#]]
````````````````````````````````


With Anchor ref

```````````````````````````````` example(WikiImages: 16) options(wiki-images)
![[wiki image#anchor-ref]] 
.
![[WiIKiI iImaAGeE#aANchoR-ReEF|wiki image#anchor-ref]]
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example(WikiImages: 17) options(wiki-images)
![[alt text|wiki image#]] 
.
![[aALt teEXt|wiki image#]]
````````````````````````````````


With text, anchor ref

```````````````````````````````` example(WikiImages: 18) options(wiki-images)
![[alt text|wiki image#anchor-ref]] 
.
![[aALt teEXt|wiki image#anchor-ref]]
````````````````````````````````


Links first, with text, empty anchor ref

```````````````````````````````` example(WikiImages: 19) options(wiki-images, links-first)
![[wiki image#|alt text]] 
.
![[wiki image#|aALt teEXt]]
````````````````````````````````


Links first, with text, anchor ref

```````````````````````````````` example(WikiImages: 20) options(wiki-images, links-first)
![[wiki image#anchor-ref|alt text]] 
.
![[wiki image#anchor-ref|aALt teEXt]]
````````````````````````````````


Custom extension with empty Anchor ref

```````````````````````````````` example(WikiImages: 21) options(wiki-images, image-ext)
![[wiki image#]] 
.
![[WiIKiI iImaAGeE#|wiki image#]]
````````````````````````````````


Custom extension with anchor ref

```````````````````````````````` example(WikiImages: 22) options(wiki-images, image-ext)
![[wiki image#anchor-ref]] 
.
![[WiIKiI iImaAGeE#aANchoR-ReEF|wiki image#anchor-ref]]
````````````````````````````````


Custom prefix with empty anchor ref

```````````````````````````````` example(WikiImages: 23) options(wiki-images, image-prefix)
![[wiki image#]]
.
![[WiIKiI iImaAGeE#|wiki image#]]
````````````````````````````````


Custom prefix with anchor ref

```````````````````````````````` example(WikiImages: 24) options(wiki-images, image-prefix)
![[wiki image#anchor-ref]]
.
![[WiIKiI iImaAGeE#aANchoR-ReEF|wiki image#anchor-ref]]
````````````````````````````````


