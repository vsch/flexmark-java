---
title: Emoji Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Emoji

Converts :warning: to its emoji image

```````````````````````````````` example Emoji: 1
:warning:
.
<p><img src="/img/warning.png" alt="emoji places:warning" height="20" width="20" align="absmiddle" /></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Emoji[0, 9] textOpen:[0, 1, ":"] text:[1, 8, "warning"] textClose:[8, 9, ":"]
      Text[1, 8] chars:[1, 8, "warning"]
````````````````````````````````


change size

```````````````````````````````` example(Emoji: 2) options(size)
:warning:
.
<p><img src="/img/warning.png" alt="emoji places:warning" height="40" width="40" align="absmiddle" /></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Emoji[0, 9] textOpen:[0, 1, ":"] text:[1, 8, "warning"] textClose:[8, 9, ":"]
      Text[1, 8] chars:[1, 8, "warning"]
````````````````````````````````


no size

```````````````````````````````` example(Emoji: 3) options(no-size)
:warning:
.
<p><img src="/img/warning.png" alt="emoji places:warning" align="absmiddle" /></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Emoji[0, 9] textOpen:[0, 1, ":"] text:[1, 8, "warning"] textClose:[8, 9, ":"]
      Text[1, 8] chars:[1, 8, "warning"]
````````````````````````````````


no align

```````````````````````````````` example(Emoji: 4) options(no-align)
:warning:
.
<p><img src="/img/warning.png" alt="emoji places:warning" height="20" width="20" /></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Emoji[0, 9] textOpen:[0, 1, ":"] text:[1, 8, "warning"] textClose:[8, 9, ":"]
      Text[1, 8] chars:[1, 8, "warning"]
````````````````````````````````


Should work in links

```````````````````````````````` example Emoji: 5
[:warning:](/url)
.
<p><a href="/url"><img src="/img/warning.png" alt="emoji places:warning" height="20" width="20" align="absmiddle" /></a></p>
.
Document[0, 18]
  Paragraph[0, 18]
    Link[0, 17] textOpen:[0, 1, "["] text:[1, 10, ":warning:"] textClose:[10, 11, "]"] linkOpen:[11, 12, "("] url:[12, 16, "/url"] pageRef:[12, 16, "/url"] linkClose:[16, 17, ")"]
      Emoji[1, 10] textOpen:[1, 2, ":"] text:[2, 9, "warning"] textClose:[9, 10, ":"]
        Text[2, 9] chars:[2, 9, "warning"]
````````````````````````````````


```````````````````````````````` example(Emoji: 6) options(url)
:warning:
.
<p><img src="https://assets-cdn.github.com/images/icons/emoji/unicode/26a0.png" alt="emoji places:warning" height="20" width="20" align="absmiddle" /></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Emoji[0, 9] textOpen:[0, 1, ":"] text:[1, 8, "warning"] textClose:[8, 9, ":"]
      Text[1, 8] chars:[1, 8, "warning"]
````````````````````````````````


Should work in links

```````````````````````````````` example Emoji: 7
[:warning:](/url)
.
<p><a href="/url"><img src="/img/warning.png" alt="emoji places:warning" height="20" width="20" align="absmiddle" /></a></p>
.
Document[0, 18]
  Paragraph[0, 18]
    Link[0, 17] textOpen:[0, 1, "["] text:[1, 10, ":warning:"] textClose:[10, 11, "]"] linkOpen:[11, 12, "("] url:[12, 16, "/url"] pageRef:[12, 16, "/url"] linkClose:[16, 17, ")"]
      Emoji[1, 10] textOpen:[1, 2, ":"] text:[2, 9, "warning"] textClose:[9, 10, ":"]
        Text[2, 9] chars:[2, 9, "warning"]
````````````````````````````````


Unknown shortcuts are converted to text

```````````````````````````````` example Emoji: 8
:warnings:
.
<p>:warnings:</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Emoji[0, 10] textOpen:[0, 1, ":"] text:[1, 9, "warnings"] textClose:[9, 10, ":"]
      Text[1, 9] chars:[1, 9, "warnings"]
````````````````````````````````


Unknown shortcuts are converted to text with inline emphasis parsing

```````````````````````````````` example Emoji: 9
:**warnings**:
.
<p>:<strong>warnings</strong>:</p>
.
Document[0, 15]
  Paragraph[0, 15]
    Emoji[0, 14] textOpen:[0, 1, ":"] text:[1, 13, "**warnings**"] textClose:[13, 14, ":"]
      StrongEmphasis[1, 13] textOpen:[1, 3, "**"] text:[3, 11, "warnings"] textClose:[11, 13, "**"]
        Text[3, 11] chars:[3, 11, "warnings"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
:warning:
.
<p md-pos="0-9"><img src="/img/warning.png" alt="emoji places:warning" height="20" width="20" align="absmiddle" /></p>
.
Document[0, 10]
  Paragraph[0, 10]
    Emoji[0, 9] textOpen:[0, 1, ":"] text:[1, 8, "warning"] textClose:[8, 9, ":"]
      Text[1, 8] chars:[1, 8, "warning"]
````````````````````````````````


```````````````````````````````` example(Source Position Attribute: 2) options(src-pos)
[:warning:](/url)
.
<p md-pos="0-17"><a href="/url" md-pos="1-10"><img src="/img/warning.png" alt="emoji places:warning" height="20" width="20" align="absmiddle" /></a></p>
.
Document[0, 18]
  Paragraph[0, 18]
    Link[0, 17] textOpen:[0, 1, "["] text:[1, 10, ":warning:"] textClose:[10, 11, "]"] linkOpen:[11, 12, "("] url:[12, 16, "/url"] pageRef:[12, 16, "/url"] linkClose:[16, 17, ")"]
      Emoji[1, 10] textOpen:[1, 2, ":"] text:[2, 9, "warning"] textClose:[9, 10, ":"]
        Text[2, 9] chars:[2, 9, "warning"]
````````````````````````````````


