---
title: EscapedCharacter Extension Spec
author: 
version: 
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## EscapedCharacter  

Converts escaped_character text to EscapedCharacter nodes.  


```````````````````````````````` example EscapedCharacter: 1
Sample  \t\e\x\t and \\ \- \~
.
<p>Sample  \t\e\x\t and \ - ~</p>
.
Document[0, 30]
  Paragraph[0, 30]
    TextBase[0, 29] chars:[0, 29, "Sampl … \- \~"]
      Text[0, 21] chars:[0, 21, "Sampl …  and "]
      EscapedCharacter[21, 23] textOpen:[21, 22, "\"] text:[22, 23, "\"]
      Text[23, 24] chars:[23, 24, " "]
      EscapedCharacter[24, 26] textOpen:[24, 25, "\"] text:[25, 26, "-"]
      Text[26, 27] chars:[26, 27, " "]
      EscapedCharacter[27, 29] textOpen:[27, 28, "\"] text:[28, 29, "~"]
````````````````````````````````

## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
Sample  \t\e\x\t and \\ \- \~
.
<p md-pos="0-29">Sample  \t\e\x\t and \ - ~</p>
.
Document[0, 30]
  Paragraph[0, 30]
    TextBase[0, 29] chars:[0, 29, "Sampl … \- \~"]
      Text[0, 21] chars:[0, 21, "Sampl …  and "]
      EscapedCharacter[21, 23] textOpen:[21, 22, "\"] text:[22, 23, "\"]
      Text[23, 24] chars:[23, 24, " "]
      EscapedCharacter[24, 26] textOpen:[24, 25, "\"] text:[25, 26, "-"]
      Text[26, 27] chars:[26, 27, " "]
      EscapedCharacter[27, 29] textOpen:[27, 28, "\"] text:[28, 29, "~"]
````````````````````````````````


