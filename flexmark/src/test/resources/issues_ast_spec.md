---
title: Core Issues Test Spec
author: Vladimir Schneider
version: 0.1
date: '2017-01-09'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Issue 13

#13, Hard line breaks do not work if markdown text/files uses CR LF as line separator

Escape crlf

```````````````````````````````` example Issue 13: 1
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


### Issue 17

#17, IndentedCodeBlock endOffset too large?

one line blank after code should not be kept as part of the code block

```````````````````````````````` example Issue 17: 1
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

```````````````````````````````` example Issue 17: 2
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


## Issue 39

#39, `[Question]` `@` is missed

not a flexmark-java issue.

```````````````````````````````` example Issue 39: 1
@someone
.
<p>@someone</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 8] chars:[0, 8, "@someone"]
````````````````````````````````


## Issue 55

Issue #55, Indented Link Reference Definitions not parsed correctly

```````````````````````````````` example Issue 55: 1
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


## Issue 70

Issue #70, parse failed for angle quotes if the end angle quote follows with a line feed or a
carriage return

```````````````````````````````` example(Issue 70: 1) options(FILE_EOL)
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


```````````````````````````````` example(Issue 70: 2) options(NO_FILE_EOL)
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


```````````````````````````````` example(Issue 70: 3) options(FILE_EOL)
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


```````````````````````````````` example(Issue 70: 4) options(FILE_EOL)
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


emphasis should have text child

```````````````````````````````` example Issue 75: 3
Test text **emphasis**
.
<p>Test text <strong>emphasis</strong></p>
.
Document[0, 23]
  Paragraph[0, 23]
    Text[0, 10] chars:[0, 10, "Test text "]
    StrongEmphasis[10, 22] textOpen:[10, 12, "**"] text:[12, 20, "emphasis"] textClose:[20, 22, "**"]
      Text[12, 20] chars:[12, 20, "emphasis"]
````````````````````````````````


inline code should have text child

```````````````````````````````` example Issue 75: 4
Test text `inline code`
.
<p>Test text <code>inline code</code></p>
.
Document[0, 24]
  Paragraph[0, 24]
    Text[0, 10] chars:[0, 10, "Test text "]
    Code[10, 23] textOpen:[10, 11, "`"] text:[11, 22, "inlin … e code"] textClose:[22, 23, "`"]
      Text[11, 22] chars:[11, 22, "inlin …  code"]
````````````````````````````````


fenced code should have text child

```````````````````````````````` example Issue 75: 5
```info
fenced code
lines
```
.
<pre><code class="language-info">fenced code
lines
</code></pre>
.
Document[0, 30]
  FencedCodeBlock[0, 29] open:[0, 3, "```"] info:[3, 7, "info"] content:[8, 26] lines[2] close:[26, 29, "```"]
    Text[8, 26] chars:[8, 26, "fence … ines\n"]
````````````````````````````````


## Issue 101

Block quote erroneously parsed on closing inline HTML.

Block parsing is done before inline parsing. Therefore at the time a block quote prefix is
detected when the paragraph text is not parsed for inline HTML. Therefore it is not possible to
with the CommonMark architecture to correctly parse.

The only workarounds are to either disable block quotes interrupting paragraphs, requiring block
quotes to be at the start of a block or to not leave dangling `>` at the start of a line.

```````````````````````````````` example(Issue 101: 1) options(FAIL)
<span
class="abc"
data-role="example"
>
some content here 
</span>
.
<p><span
class="abc"
data-role="example">
some content here
</span></p>
.
Document[0, 66]
  Paragraph[0, 66]
    HtmlInline[0, 38] chars:[0, 38, "<span … ple\">"]
    SoftLineBreak[38, 39]
    Text[39, 56] chars:[39, 56, "some  …  here"]
    SoftLineBreak[57, 58]
    HtmlInline[58, 65] chars:[58, 65, "</span>"]
````````````````````````````````


Block quote workaround

```````````````````````````````` example(Issue 101: 2) options(block-no-interrupt-paragraph)
<span
class="abc"
data-role="example"
>
some content here 
</span>
.
<p><span
class="abc"
data-role="example"
>
some content here
</span></p>
.
Document[0, 66]
  Paragraph[0, 66]
    HtmlInline[0, 39] chars:[0, 39, "<span … le\"\n>"]
    SoftLineBreak[39, 40]
    Text[40, 57] chars:[40, 57, "some  …  here"]
    SoftLineBreak[58, 59]
    HtmlInline[59, 66] chars:[59, 66, "</span>"]
````````````````````````````````


Block quote workaround

```````````````````````````````` example Issue 101: 3
<span
class="abc"
data-role="example">
some content here 
</span>
.
<p><span
class="abc"
data-role="example">
some content here
</span></p>
.
Document[0, 66]
  Paragraph[0, 66]
    HtmlInline[0, 38] chars:[0, 38, "<span … ple\">"]
    SoftLineBreak[38, 39]
    Text[39, 56] chars:[39, 56, "some  …  here"]
    SoftLineBreak[57, 58]
    HtmlInline[58, 65] chars:[58, 65, "</span>"]
````````````````````````````````


HTML block parsing

```````````````````````````````` example Issue 101: 4
<!--
-->
<div>
    <p>test</p>
</div>
<div>
    <p>test2</p>
</div>
.
<!--
-->
<div>
    <p>test</p>
</div>
<div>
    <p>test2</p>
</div>
.
Document[0, 68]
  HtmlCommentBlock[0, 9]
  HtmlBlock[9, 68]
````````````````````````````````


HTML block parsing, error only detects `<div>` and not the `<![CDATA[` following it

```````````````````````````````` example Issue 101: 5
<div><![CDATA[
    <div xmlns="http://www.w3.org/1999/html">
    
    </div>
    ]]></div>
.
<div><![CDATA[
    <div xmlns="http://www.w3.org/1999/html">
<pre><code>&lt;/div&gt;
]]&gt;&lt;/div&gt;
</code></pre>
.
Document[0, 91]
  HtmlBlock[0, 61]
  IndentedCodeBlock[70, 91]
````````````````````````````````


HTML block parsing, error only detects `<div>` and not the `<![CDATA[` following it

```````````````````````````````` example Issue 101: 6
<div>
<![CDATA[
    <div xmlns="http://www.w3.org/1999/html">
    
    </div>
]]></div>
.
<div>
<![CDATA[
    <div xmlns="http://www.w3.org/1999/html">
<pre><code>&lt;/div&gt;
</code></pre>
<p>]]&gt;</div></p>
.
Document[0, 88]
  HtmlBlock[0, 62]
  IndentedCodeBlock[71, 78]
  Paragraph[78, 88]
    Text[78, 81] chars:[78, 81, "]]>"]
    HtmlInline[81, 87] chars:[81, 87, "</div>"]
````````````````````````````````


HTML block parsing, error only detects `<div>` and not the `<![CDATA[` following it

```````````````````````````````` example Issue 101: 7
<![CDATA[
    <div xmlns="http://www.w3.org/1999/html">
    
    </div>
]]>
.
<![CDATA[
    <div xmlns="http://www.w3.org/1999/html">
    
    </div>
]]>
.
Document[0, 76]
  HtmlBlock[0, 76]
````````````````````````````````


