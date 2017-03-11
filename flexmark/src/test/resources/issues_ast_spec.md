---
title: Core Issues Test Spec
author: Vladimir Schneider
version: 0.1
date: '2017-01-09'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Issue #13

#13, Hard line breaks do not work if markdown text/files uses CR LF as line separator

Escape crlf

```````````````````````````````` example Issue #13: 1
test\⏎
line after hard break
.
<p>test<br />
line after hard break</p>
.
Document[0, 29]
  Paragraph[0, 29]
    Text[0, 4] chars:[0, 4, "test"]
    HardLineBreak[4, 7]
    Text[7, 28] chars:[7, 28, "line  … break"]
````````````````````````````````


### Issue #17

#17, IndentedCodeBlock endOffset too large?

one line blank after code should not be kept as part of the code block

```````````````````````````````` example Issue #17: 1
→code

some text
.
<pre><code>code
</code></pre>
<p>some text</p>
.
Document[0, 17]
  IndentedCodeBlock[1, 6]
  Paragraph[7, 17]
    Text[7, 16] chars:[7, 16, "some text"]
````````````````````````````````


Blank lines before and after should not be kept

```````````````````````````````` example Issue #17: 2
→code


→code


some text
.
<pre><code>code


code
</code></pre>
<p>some text</p>
.
Document[0, 26]
  IndentedCodeBlock[1, 14]
  Paragraph[16, 26]
    Text[16, 25] chars:[16, 25, "some text"]
````````````````````````````````


## Issue #39

#39, `[Question]` `@` is missed

not a flexmark-java issue.

```````````````````````````````` example Issue #39: 1
@someone
.
<p>@someone</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 8] chars:[0, 8, "@someone"]
````````````````````````````````


Issue #55, Indented Link Reference Definitions not parsed correctly

```````````````````````````````` example Issue #39: 2
This note demonstrates some of what [Markdown][1] is capable of doing.
....
*Note: the fourth item uses the Unicode character for [Roman numeral four][2].*

  [1]: http://daringfireball.net/projects/markdown/
  [2]: http://www.fileformat.info/info/unicode/char/2163/index.htm
.
<p>This note demonstrates some of what <a href="http://daringfireball.net/projects/markdown/">Markdown</a> is capable of doing.
....
<em>Note: the fourth item uses the Unicode character for <a href="http://www.fileformat.info/info/unicode/char/2163/index.htm">Roman numeral four</a>.</em></p>
.
Document[0, 276]
  Paragraph[0, 156] isTrailingBlankLine
    Text[0, 36] chars:[0, 36, "This  … what "]
    LinkRef[36, 49] textOpen:[36, 37, "["] text:[37, 45, "Markdown"] textClose:[45, 46, "]"] referenceOpen:[46, 47, "["] reference:[47, 48, "1"] referenceClose:[48, 49, "]"]
      Text[37, 45] chars:[37, 45, "Markdown"]
    Text[49, 70] chars:[49, 70, " is c … oing."]
    SoftLineBreak[70, 71]
    Text[71, 75] chars:[71, 75, "...."]
    SoftLineBreak[75, 76]
    Emphasis[76, 155] textOpen:[76, 77, "*"] text:[77, 154, "Note: the fourth item uses the Unicode character for [Roman numeral four][2]."] textClose:[154, 155, "*"]
      Text[77, 130] chars:[77, 130, "Note: …  for "]
      LinkRef[130, 153] textOpen:[130, 131, "["] text:[131, 149, "Roman numeral four"] textClose:[149, 150, "]"] referenceOpen:[150, 151, "["] reference:[151, 152, "2"] referenceClose:[152, 153, "]"]
        Text[131, 149] chars:[131, 149, "Roman …  four"]
      Text[153, 154] chars:[153, 154, "."]
  Reference[159, 208] refOpen:[159, 160, "["] ref:[160, 161, "1"] refClose:[161, 163, "]:"] url:[164, 208, "http://daringfireball.net/projects/markdown/"]
  Reference[211, 275] refOpen:[211, 212, "["] ref:[212, 213, "2"] refClose:[213, 215, "]:"] url:[216, 275, "http://www.fileformat.info/info/unicode/char/2163/index.htm"]
````````````````````````````````


## Issue #70

Issue #70, parse failed for angle quotes if the end angle quote follows with a line feed or a
carriage return

```````````````````````````````` example(Issue #70: 1) options(FILE_EOL)
<<test>>
.
<p>&lt;<test>&gt;</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 1] chars:[0, 1, "<"]
    HtmlInline[1, 7] chars:[1, 7, "<test>"]
    Text[7, 8] chars:[7, 8, ">"]
````````````````````````````````


```````````````````````````````` example(Issue #70: 2) options(NO_FILE_EOL)
<<test>>⏎
.
<p>&lt;<test>&gt;</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 1] chars:[0, 1, "<"]
    HtmlInline[1, 7] chars:[1, 7, "<test>"]
    Text[7, 8] chars:[7, 8, ">"]
````````````````````````````````


```````````````````````````````` example(Issue #70: 3) options(FILE_EOL)
<<test>>⏎
.
<p>&lt;<test>&gt;</p>
.
Document[0, 10]
  Paragraph[0, 10]
    Text[0, 1] chars:[0, 1, "<"]
    HtmlInline[1, 7] chars:[1, 7, "<test>"]
    Text[7, 8] chars:[7, 8, ">"]
````````````````````````````````


```````````````````````````````` example(Issue #70: 4) options(FILE_EOL)
<<test>>

.
<p>&lt;<test>&gt;</p>
.
Document[0, 10]
  Paragraph[0, 9] isTrailingBlankLine
    Text[0, 1] chars:[0, 1, "<"]
    HtmlInline[1, 7] chars:[1, 7, "<test>"]
    Text[7, 8] chars:[7, 8, ">"]
````````````````````````````````


## Issue 75

Issue #75, Incorrect footnote link

Actually it is incorrect reference parsing with `\r\n`

```````````````````````````````` example Issue 75: 1
Incorrect: [amazon][2]⏎

[1]: http://www.google.com/⏎
[2]: http://www.amazon.com/
.
<p>Incorrect: <a href="http://www.amazon.com/">amazon</a></p>
.
Document[0, 82]
  Paragraph[0, 24] isTrailingBlankLine
    Text[0, 11] chars:[0, 11, "Incor … ect: "]
    LinkRef[11, 22] textOpen:[11, 12, "["] text:[12, 18, "amazon"] textClose:[18, 19, "]"] referenceOpen:[19, 20, "["] reference:[20, 21, "2"] referenceClose:[21, 22, "]"]
      Text[12, 18] chars:[12, 18, "amazon"]
  Reference[25, 52] refOpen:[25, 26, "["] ref:[26, 27, "1"] refClose:[27, 29, "]:"] url:[30, 52, "http://www.google.com/"]
  Reference[54, 81] refOpen:[54, 55, "["] ref:[55, 56, "2"] refClose:[56, 58, "]:"] url:[59, 81, "http://www.amazon.com/"]
````````````````````````````````


```````````````````````````````` example Issue 75: 2
Incorrect: [amazon][2]

[1]: http://www.google.com/
[2]: http://www.amazon.com/
.
<p>Incorrect: <a href="http://www.amazon.com/">amazon</a></p>
.
Document[0, 80]
  Paragraph[0, 23] isTrailingBlankLine
    Text[0, 11] chars:[0, 11, "Incor … ect: "]
    LinkRef[11, 22] textOpen:[11, 12, "["] text:[12, 18, "amazon"] textClose:[18, 19, "]"] referenceOpen:[19, 20, "["] reference:[20, 21, "2"] referenceClose:[21, 22, "]"]
      Text[12, 18] chars:[12, 18, "amazon"]
  Reference[24, 51] refOpen:[24, 25, "["] ref:[25, 26, "1"] refClose:[26, 28, "]:"] url:[29, 51, "http://www.google.com/"]
  Reference[52, 79] refOpen:[52, 53, "["] ref:[53, 54, "2"] refClose:[54, 56, "]:"] url:[57, 79, "http://www.amazon.com/"]
````````````````````````````````


