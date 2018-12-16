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


## Issue 109 

Issue #109, Image Ref missing title tag in rendered HTML

```````````````````````````````` example Issue 109: 1
![alt text][id]

[id]: /images/icons/up_16.gif "Title"
.
<p><img src="/images/icons/up_16.gif" alt="alt text" title="Title" /></p>
.
Document[0, 55]
  Paragraph[0, 16] isTrailingBlankLine
    ImageRef[0, 15] textOpen:[0, 2, "!["] text:[2, 10, "alt text"] textClose:[10, 11, "]"] referenceOpen:[11, 12, "["] reference:[12, 14, "id"] referenceClose:[14, 15, "]"]
      Text[2, 10] chars:[2, 10, "alt text"]
  Reference[17, 54] refOpen:[17, 18, "["] ref:[18, 20, "id"] refClose:[20, 22, "]:"] url:[23, 46, "/images/icons/up_16.gif"] titleOpen:[47, 48, "\""] title:[48, 53, "Title"] titleClose:[53, 54, "\""]
````````````````````````````````


Minimal case of url resolver bug.

```````````````````````````````` example Issue 109: 2
![alt text](/images/icons/up_16.gif "Title")

![alt text][id]

[id]: /images/icons/up_16.gif "Title"
.
<p><img src="/images/icons/up_16.gif" alt="alt text" title="Title" /></p>
<p><img src="/images/icons/up_16.gif" alt="alt text" title="Title" /></p>
.
Document[0, 101]
  Paragraph[0, 45] isTrailingBlankLine
    Image[0, 44] textOpen:[0, 2, "!["] text:[2, 10, "alt text"] textClose:[10, 11, "]"] linkOpen:[11, 12, "("] url:[12, 35, "/images/icons/up_16.gif"] pageRef:[12, 35, "/images/icons/up_16.gif"] titleOpen:[36, 37, "\""] title:[37, 42, "Title"] titleClose:[42, 43, "\""] linkClose:[43, 44, ")"]
      Text[2, 10] chars:[2, 10, "alt text"]
  Paragraph[46, 62] isTrailingBlankLine
    ImageRef[46, 61] textOpen:[46, 48, "!["] text:[48, 56, "alt text"] textClose:[56, 57, "]"] referenceOpen:[57, 58, "["] reference:[58, 60, "id"] referenceClose:[60, 61, "]"]
      Text[48, 56] chars:[48, 56, "alt text"]
  Reference[63, 100] refOpen:[63, 64, "["] ref:[64, 66, "id"] refClose:[66, 68, "]:"] url:[69, 92, "/images/icons/up_16.gif"] titleOpen:[93, 94, "\""] title:[94, 99, "Title"] titleClose:[99, 100, "\""]
````````````````````````````````


```````````````````````````````` example Issue 109: 3
![alt text](/images/icons/up_16.gif)

![alt text][id]

[id]: /images/icons/up_16.gif "Title"
.
<p><img src="/images/icons/up_16.gif" alt="alt text" /></p>
<p><img src="/images/icons/up_16.gif" alt="alt text" title="Title" /></p>
.
Document[0, 93]
  Paragraph[0, 37] isTrailingBlankLine
    Image[0, 36] textOpen:[0, 2, "!["] text:[2, 10, "alt text"] textClose:[10, 11, "]"] linkOpen:[11, 12, "("] url:[12, 35, "/images/icons/up_16.gif"] pageRef:[12, 35, "/images/icons/up_16.gif"] linkClose:[35, 36, ")"]
      Text[2, 10] chars:[2, 10, "alt text"]
  Paragraph[38, 54] isTrailingBlankLine
    ImageRef[38, 53] textOpen:[38, 40, "!["] text:[40, 48, "alt text"] textClose:[48, 49, "]"] referenceOpen:[49, 50, "["] reference:[50, 52, "id"] referenceClose:[52, 53, "]"]
      Text[40, 48] chars:[40, 48, "alt text"]
  Reference[55, 92] refOpen:[55, 56, "["] ref:[56, 58, "id"] refClose:[58, 60, "]:"] url:[61, 84, "/images/icons/up_16.gif"] titleOpen:[85, 86, "\""] title:[86, 91, "Title"] titleClose:[91, 92, "\""]
````````````````````````````````


```````````````````````````````` example Issue 109: 4
![alt text][id]

[id]: /images/icons/up_16.gif "Title"

![alt text](/images/icons/up_16.gif)
.
<p><img src="/images/icons/up_16.gif" alt="alt text" title="Title" /></p>
<p><img src="/images/icons/up_16.gif" alt="alt text" /></p>
.
Document[0, 93]
  Paragraph[0, 16] isTrailingBlankLine
    ImageRef[0, 15] textOpen:[0, 2, "!["] text:[2, 10, "alt text"] textClose:[10, 11, "]"] referenceOpen:[11, 12, "["] reference:[12, 14, "id"] referenceClose:[14, 15, "]"]
      Text[2, 10] chars:[2, 10, "alt text"]
  Reference[17, 54] refOpen:[17, 18, "["] ref:[18, 20, "id"] refClose:[20, 22, "]:"] url:[23, 46, "/images/icons/up_16.gif"] titleOpen:[47, 48, "\""] title:[48, 53, "Title"] titleClose:[53, 54, "\""]
  Paragraph[56, 93]
    Image[56, 92] textOpen:[56, 58, "!["] text:[58, 66, "alt text"] textClose:[66, 67, "]"] linkOpen:[67, 68, "("] url:[68, 91, "/images/icons/up_16.gif"] pageRef:[68, 91, "/images/icons/up_16.gif"] linkClose:[91, 92, ")"]
      Text[58, 66] chars:[58, 66, "alt text"]
````````````````````````````````


```````````````````````````````` example Issue 109: 5
[alt text][id]

[id]: /images/icons/up_16.gif "Title"
.
<p><a href="/images/icons/up_16.gif" title="Title">alt text</a></p>
.
Document[0, 54]
  Paragraph[0, 15] isTrailingBlankLine
    LinkRef[0, 14] textOpen:[0, 1, "["] text:[1, 9, "alt text"] textClose:[9, 10, "]"] referenceOpen:[10, 11, "["] reference:[11, 13, "id"] referenceClose:[13, 14, "]"]
      Text[1, 9] chars:[1, 9, "alt text"]
  Reference[16, 53] refOpen:[16, 17, "["] ref:[17, 19, "id"] refClose:[19, 21, "]:"] url:[22, 45, "/images/icons/up_16.gif"] titleOpen:[46, 47, "\""] title:[47, 52, "Title"] titleClose:[52, 53, "\""]
````````````````````````````````


Minimal case of url resolver bug.

```````````````````````````````` example Issue 109: 6
[alt text](/images/icons/up_16.gif "Title")

[alt text][id]

[id]: /images/icons/up_16.gif "Title"
.
<p><a href="/images/icons/up_16.gif" title="Title">alt text</a></p>
<p><a href="/images/icons/up_16.gif" title="Title">alt text</a></p>
.
Document[0, 99]
  Paragraph[0, 44] isTrailingBlankLine
    Link[0, 43] textOpen:[0, 1, "["] text:[1, 9, "alt text"] textClose:[9, 10, "]"] linkOpen:[10, 11, "("] url:[11, 34, "/images/icons/up_16.gif"] pageRef:[11, 34, "/images/icons/up_16.gif"] titleOpen:[35, 36, "\""] title:[36, 41, "Title"] titleClose:[41, 42, "\""] linkClose:[42, 43, ")"]
      Text[1, 9] chars:[1, 9, "alt text"]
  Paragraph[45, 60] isTrailingBlankLine
    LinkRef[45, 59] textOpen:[45, 46, "["] text:[46, 54, "alt text"] textClose:[54, 55, "]"] referenceOpen:[55, 56, "["] reference:[56, 58, "id"] referenceClose:[58, 59, "]"]
      Text[46, 54] chars:[46, 54, "alt text"]
  Reference[61, 98] refOpen:[61, 62, "["] ref:[62, 64, "id"] refClose:[64, 66, "]:"] url:[67, 90, "/images/icons/up_16.gif"] titleOpen:[91, 92, "\""] title:[92, 97, "Title"] titleClose:[97, 98, "\""]
````````````````````````````````


```````````````````````````````` example Issue 109: 7
[alt text](/images/icons/up_16.gif)

[alt text][id]

[id]: /images/icons/up_16.gif "Title"
.
<p><a href="/images/icons/up_16.gif">alt text</a></p>
<p><a href="/images/icons/up_16.gif" title="Title">alt text</a></p>
.
Document[0, 91]
  Paragraph[0, 36] isTrailingBlankLine
    Link[0, 35] textOpen:[0, 1, "["] text:[1, 9, "alt text"] textClose:[9, 10, "]"] linkOpen:[10, 11, "("] url:[11, 34, "/images/icons/up_16.gif"] pageRef:[11, 34, "/images/icons/up_16.gif"] linkClose:[34, 35, ")"]
      Text[1, 9] chars:[1, 9, "alt text"]
  Paragraph[37, 52] isTrailingBlankLine
    LinkRef[37, 51] textOpen:[37, 38, "["] text:[38, 46, "alt text"] textClose:[46, 47, "]"] referenceOpen:[47, 48, "["] reference:[48, 50, "id"] referenceClose:[50, 51, "]"]
      Text[38, 46] chars:[38, 46, "alt text"]
  Reference[53, 90] refOpen:[53, 54, "["] ref:[54, 56, "id"] refClose:[56, 58, "]:"] url:[59, 82, "/images/icons/up_16.gif"] titleOpen:[83, 84, "\""] title:[84, 89, "Title"] titleClose:[89, 90, "\""]
````````````````````````````````


```````````````````````````````` example Issue 109: 8
[alt text][id]

[id]: /images/icons/up_16.gif "Title"

[alt text](/images/icons/up_16.gif)
.
<p><a href="/images/icons/up_16.gif" title="Title">alt text</a></p>
<p><a href="/images/icons/up_16.gif">alt text</a></p>
.
Document[0, 91]
  Paragraph[0, 15] isTrailingBlankLine
    LinkRef[0, 14] textOpen:[0, 1, "["] text:[1, 9, "alt text"] textClose:[9, 10, "]"] referenceOpen:[10, 11, "["] reference:[11, 13, "id"] referenceClose:[13, 14, "]"]
      Text[1, 9] chars:[1, 9, "alt text"]
  Reference[16, 53] refOpen:[16, 17, "["] ref:[17, 19, "id"] refClose:[19, 21, "]:"] url:[22, 45, "/images/icons/up_16.gif"] titleOpen:[46, 47, "\""] title:[47, 52, "Title"] titleClose:[52, 53, "\""]
  Paragraph[55, 91]
    Link[55, 90] textOpen:[55, 56, "["] text:[56, 64, "alt text"] textClose:[64, 65, "]"] linkOpen:[65, 66, "("] url:[66, 89, "/images/icons/up_16.gif"] pageRef:[66, 89, "/images/icons/up_16.gif"] linkClose:[89, 90, ")"]
      Text[56, 64] chars:[56, 64, "alt text"]
````````````````````````````````


## Issue MN-456

Issue MN-456

```````````````````````````````` example Issue MN-456: 1
```{r, echo=FALSE}
summary(cars)
```
.
<pre><code class="language-{r,">summary(cars)
</code></pre>
.
Document[0, 37]
  FencedCodeBlock[0, 36] open:[0, 3, "```"] info:[3, 18, "{r, echo=FALSE}"] content:[19, 33] lines[1] close:[33, 36, "```"]
    Text[19, 33] chars:[19, 33, "summa … ars)\n"]
````````````````````````````````


## Issue 116

Issue #116, Can I use flexmark to turn a Markdown string into a List of Texts for use in a
JavaFX TextFlow?

```````````````````````````````` example Issue 116: 1
So _this_ is italic and **this** is bold
.
<p>So <em>this</em> is italic and <strong>this</strong> is bold</p>
.
Document[0, 41]
  Paragraph[0, 41]
    Text[0, 3] chars:[0, 3, "So "]
    Emphasis[3, 9] textOpen:[3, 4, "_"] text:[4, 8, "this"] textClose:[8, 9, "_"]
      Text[4, 8] chars:[4, 8, "this"]
    Text[9, 24] chars:[9, 24, " is i …  and "]
    StrongEmphasis[24, 32] textOpen:[24, 26, "**"] text:[26, 30, "this"] textClose:[30, 32, "**"]
      Text[26, 30] chars:[26, 30, "this"]
    Text[32, 40] chars:[32, 40, " is bold"]
````````````````````````````````


## Issue xxx

Fixed Spaces parsing ignores blank line after last list item and does not check if previous item
is loose either. Making it impossible to make the last item loose.

```````````````````````````````` example(Issue xxx: 1) options(fixed-indent)
### Logic

1. If no parameters,
    1. then remove the current cart item entirely

2. If only count,
    1. and current cart item exists,
    1. reduce quantity from the current item,
    1. and remove entirely if below 1.

3. If item has product, it is a cart item qualifier
    1. If remove exists, remove those options from the qualified cart item
    1. Else if count and cart item exists, reduce quantity of cart item
    1. Else no cart item found

4. If item has no product,
    1. And current cart item exists, remove options from cart item
    1. Else no cart item exists, or options don't match product, nothing was removed
.
<h3>Logic</h3>
<ol>
  <li>
    <p>If no parameters,</p>
    <ol>
      <li>then remove the current cart item entirely</li>
    </ol>
  </li>
  <li>
    <p>If only count,</p>
    <ol>
      <li>and current cart item exists,</li>
      <li>reduce quantity from the current item,</li>
      <li>and remove entirely if below 1.</li>
    </ol>
  </li>
  <li>
    <p>If item has product, it is a cart item qualifier</p>
    <ol>
      <li>If remove exists, remove those options from the qualified cart item</li>
      <li>Else if count and cart item exists, reduce quantity of cart item</li>
      <li>Else no cart item found</li>
    </ol>
  </li>
  <li>
    <p>If item has no product,</p>
    <ol>
      <li>And current cart item exists, remove options from cart item</li>
      <li>Else no cart item exists, or options don't match product, nothing was removed</li>
    </ol>
  </li>
</ol>
.
Document[0, 633]
  Heading[0, 9] textOpen:[0, 3, "###"] text:[4, 9, "Logic"]
    Text[4, 9] chars:[4, 9, "Logic"]
  OrderedList[11, 633] isTight delimiter:'.'
    OrderedListItem[11, 82] open:[11, 13, "1."] isLoose
      Paragraph[14, 32]
        Text[14, 31] chars:[14, 31, "If no … ters,"]
      OrderedList[36, 82] isTight delimiter:'.'
        OrderedListItem[36, 82] open:[36, 38, "1."] isTight hadBlankLineAfter
          Paragraph[39, 82] isTrailingBlankLine
            Text[39, 81] chars:[39, 81, "then  … irely"]
    OrderedListItem[83, 223] open:[83, 85, "2."] isLoose
      Paragraph[86, 101]
        Text[86, 100] chars:[86, 100, "If on … ount,"]
      OrderedList[105, 223] isTight delimiter:'.'
        OrderedListItem[105, 138] open:[105, 107, "1."] isTight
          Paragraph[108, 138]
            Text[108, 137] chars:[108, 137, "and c … ists,"]
        OrderedListItem[142, 184] open:[142, 144, "1."] isTight
          Paragraph[145, 184]
            Text[145, 183] chars:[145, 183, "reduc … item,"]
        OrderedListItem[188, 223] open:[188, 190, "1."] isTight hadBlankLineAfter
          Paragraph[191, 223] isTrailingBlankLine
            Text[191, 222] chars:[191, 222, "and r … ow 1."]
    OrderedListItem[224, 454] open:[224, 226, "3."] isLoose
      Paragraph[227, 276]
        Text[227, 275] chars:[227, 275, "If it … ifier"]
      OrderedList[280, 454] isTight delimiter:'.'
        OrderedListItem[280, 351] open:[280, 282, "1."] isTight
          Paragraph[283, 351]
            Text[283, 350] chars:[283, 350, "If re …  item"]
        OrderedListItem[355, 423] open:[355, 357, "1."] isTight
          Paragraph[358, 423]
            Text[358, 422] chars:[358, 422, "Else  …  item"]
        OrderedListItem[427, 454] open:[427, 429, "1."] isTight hadBlankLineAfter
          Paragraph[430, 454] isTrailingBlankLine
            Text[430, 453] chars:[430, 453, "Else  … found"]
    OrderedListItem[455, 633] open:[455, 457, "4."] isLoose
      Paragraph[458, 482]
        Text[458, 481] chars:[458, 481, "If it … duct,"]
      OrderedList[486, 633] isTight delimiter:'.'
        OrderedListItem[486, 549] open:[486, 488, "1."] isTight
          Paragraph[489, 549]
            Text[489, 548] chars:[489, 548, "And c …  item"]
        OrderedListItem[553, 633] open:[553, 555, "1."] isTight
          Paragraph[556, 633]
            Text[556, 633] chars:[556, 633, "Else  … moved"]
````````````````````````````````


## Issue 136

Issue #136, Tasklist display issue

```````````````````````````````` example Issue 136: 1
Task List
- [x] Task 1
- [ ] Task 2
- [x] Task 3
.
<p>Task List</p>
<ul>
  <li>[x] Task 1</li>
  <li>[ ] Task 2</li>
  <li>[x] Task 3</li>
</ul>
.
Document[0, 49]
  Paragraph[0, 10]
    Text[0, 9] chars:[0, 9, "Task List"]
  BulletList[10, 49] isTight
    BulletListItem[10, 23] open:[10, 11, "-"] isTight
      Paragraph[12, 23]
        LinkRef[12, 15] referenceOpen:[12, 13, "["] reference:[13, 14, "x"] referenceClose:[14, 15, "]"]
          Text[13, 14] chars:[13, 14, "x"]
        Text[15, 22] chars:[15, 22, " Task 1"]
    BulletListItem[23, 36] open:[23, 24, "-"] isTight
      Paragraph[25, 36]
        LinkRef[25, 28] referenceOpen:[25, 26, "["] reference:[27, 27] referenceClose:[27, 28, "]"]
          Text[26, 27] chars:[26, 27, " "]
        Text[28, 35] chars:[28, 35, " Task 2"]
    BulletListItem[36, 49] open:[36, 37, "-"] isTight
      Paragraph[38, 49]
        LinkRef[38, 41] referenceOpen:[38, 39, "["] reference:[39, 40, "x"] referenceClose:[40, 41, "]"]
          Text[39, 40] chars:[39, 40, "x"]
        Text[41, 48] chars:[41, 48, " Task 3"]
````````````````````````````````


## Issue 132

Issue #132, order list <ol> without "start=" ?

```````````````````````````````` example Issue 132: 1
**Ordered**
1. Item 1
2. Item 2
3. Item 3
<!-- -->
1. [Example.com](http://www.example.com)
2. [Google](http://www.google.com)
3. [Yahoo!](http://www.yahoo.com)
4. [Another Example.com](http://www.example.com)
.
<p><strong>Ordered</strong></p>
<ol>
  <li>Item 1</li>
  <li>Item 2</li>
  <li>Item 3</li>
</ol>
<!-- -->
<ol>
  <li><a href="http://www.example.com">Example.com</a></li>
  <li><a href="http://www.google.com">Google</a></li>
  <li><a href="http://www.yahoo.com">Yahoo!</a></li>
  <li><a href="http://www.example.com">Another Example.com</a></li>
</ol>
.
Document[0, 210]
  Paragraph[0, 12]
    StrongEmphasis[0, 11] textOpen:[0, 2, "**"] text:[2, 9, "Ordered"] textClose:[9, 11, "**"]
      Text[2, 9] chars:[2, 9, "Ordered"]
  OrderedList[12, 42] isTight delimiter:'.'
    OrderedListItem[12, 22] open:[12, 14, "1."] isTight
      Paragraph[15, 22]
        Text[15, 21] chars:[15, 21, "Item 1"]
    OrderedListItem[22, 32] open:[22, 24, "2."] isTight
      Paragraph[25, 32]
        Text[25, 31] chars:[25, 31, "Item 2"]
    OrderedListItem[32, 42] open:[32, 34, "3."] isTight
      Paragraph[35, 42]
        Text[35, 41] chars:[35, 41, "Item 3"]
  HtmlCommentBlock[42, 51]
  OrderedList[51, 210] isTight delimiter:'.'
    OrderedListItem[51, 92] open:[51, 53, "1."] isTight
      Paragraph[54, 92]
        Link[54, 91] textOpen:[54, 55, "["] text:[55, 66, "Example.com"] textClose:[66, 67, "]"] linkOpen:[67, 68, "("] url:[68, 90, "http://www.example.com"] pageRef:[68, 90, "http://www.example.com"] linkClose:[90, 91, ")"]
          Text[55, 66] chars:[55, 66, "Examp … e.com"]
    OrderedListItem[92, 127] open:[92, 94, "2."] isTight
      Paragraph[95, 127]
        Link[95, 126] textOpen:[95, 96, "["] text:[96, 102, "Google"] textClose:[102, 103, "]"] linkOpen:[103, 104, "("] url:[104, 125, "http://www.google.com"] pageRef:[104, 125, "http://www.google.com"] linkClose:[125, 126, ")"]
          Text[96, 102] chars:[96, 102, "Google"]
    OrderedListItem[127, 161] open:[127, 129, "3."] isTight
      Paragraph[130, 161]
        Link[130, 160] textOpen:[130, 131, "["] text:[131, 137, "Yahoo!"] textClose:[137, 138, "]"] linkOpen:[138, 139, "("] url:[139, 159, "http://www.yahoo.com"] pageRef:[139, 159, "http://www.yahoo.com"] linkClose:[159, 160, ")"]
          Text[131, 137] chars:[131, 137, "Yahoo!"]
    OrderedListItem[161, 210] open:[161, 163, "4."] isTight
      Paragraph[164, 210]
        Link[164, 209] textOpen:[164, 165, "["] text:[165, 184, "Another Example.com"] textClose:[184, 185, "]"] linkOpen:[185, 186, "("] url:[186, 208, "http://www.example.com"] pageRef:[186, 208, "http://www.example.com"] linkClose:[208, 209, ")"]
          Text[165, 184] chars:[165, 184, "Anoth … e.com"]
````````````````````````````````


end on double blank line

```````````````````````````````` example(Issue 132: 2) options(list-break)
**Ordered**
1. Item 1
2. Item 2
3. Item 3


1. [Example.com](http://www.example.com)
2. [Google](http://www.google.com)
3. [Yahoo!](http://www.yahoo.com)
4. [Another Example.com](http://www.example.com)
.
<p><strong>Ordered</strong></p>
<ol>
  <li>Item 1</li>
  <li>Item 2</li>
  <li>Item 3</li>
</ol>
<ol>
  <li><a href="http://www.example.com">Example.com</a></li>
  <li><a href="http://www.google.com">Google</a></li>
  <li><a href="http://www.yahoo.com">Yahoo!</a></li>
  <li><a href="http://www.example.com">Another Example.com</a></li>
</ol>
.
Document[0, 202]
  Paragraph[0, 12]
    StrongEmphasis[0, 11] textOpen:[0, 2, "**"] text:[2, 9, "Ordered"] textClose:[9, 11, "**"]
      Text[2, 9] chars:[2, 9, "Ordered"]
  OrderedList[12, 42] isTight delimiter:'.'
    OrderedListItem[12, 22] open:[12, 14, "1."] isTight
      Paragraph[15, 22]
        Text[15, 21] chars:[15, 21, "Item 1"]
    OrderedListItem[22, 32] open:[22, 24, "2."] isTight
      Paragraph[25, 32]
        Text[25, 31] chars:[25, 31, "Item 2"]
    OrderedListItem[32, 42] open:[32, 34, "3."] isTight hadBlankLineAfter
      Paragraph[35, 42] isTrailingBlankLine
        Text[35, 41] chars:[35, 41, "Item 3"]
  OrderedList[44, 202] isTight delimiter:'.'
    OrderedListItem[44, 85] open:[44, 46, "1."] isTight
      Paragraph[47, 85]
        Link[47, 84] textOpen:[47, 48, "["] text:[48, 59, "Example.com"] textClose:[59, 60, "]"] linkOpen:[60, 61, "("] url:[61, 83, "http://www.example.com"] pageRef:[61, 83, "http://www.example.com"] linkClose:[83, 84, ")"]
          Text[48, 59] chars:[48, 59, "Examp … e.com"]
    OrderedListItem[85, 120] open:[85, 87, "2."] isTight
      Paragraph[88, 120]
        Link[88, 119] textOpen:[88, 89, "["] text:[89, 95, "Google"] textClose:[95, 96, "]"] linkOpen:[96, 97, "("] url:[97, 118, "http://www.google.com"] pageRef:[97, 118, "http://www.google.com"] linkClose:[118, 119, ")"]
          Text[89, 95] chars:[89, 95, "Google"]
    OrderedListItem[120, 154] open:[120, 122, "3."] isTight
      Paragraph[123, 154]
        Link[123, 153] textOpen:[123, 124, "["] text:[124, 130, "Yahoo!"] textClose:[130, 131, "]"] linkOpen:[131, 132, "("] url:[132, 152, "http://www.yahoo.com"] pageRef:[132, 152, "http://www.yahoo.com"] linkClose:[152, 153, ")"]
          Text[124, 130] chars:[124, 130, "Yahoo!"]
    OrderedListItem[154, 202] open:[154, 156, "4."] isTight
      Paragraph[157, 202]
        Link[157, 202] textOpen:[157, 158, "["] text:[158, 177, "Another Example.com"] textClose:[177, 178, "]"] linkOpen:[178, 179, "("] url:[179, 201, "http://www.example.com"] pageRef:[179, 201, "http://www.example.com"] linkClose:[201, 202, ")"]
          Text[158, 177] chars:[158, 177, "Anoth … e.com"]
````````````````````````````````


## Issue 156

Issue #156, Render image embedded inside a link when using a base URL

without the custom URL extension it works

```````````````````````````````` example Issue 156: 1
[![Build Status](https://ci.tensorflow.org/buildStatus/icon?job=tensorflow-master-cpu)](https://ci.tensorflow.org/job/tensorflow-master-cpu) 
.
<p><a href="https://ci.tensorflow.org/job/tensorflow-master-cpu"><img src="https://ci.tensorflow.org/buildStatus/icon?job=tensorflow-master-cpu" alt="Build Status" /></a></p>
.
Document[0, 142]
  Paragraph[0, 142]
    Link[0, 140] textOpen:[0, 1, "["] text:[1, 86, "![Build Status](https://ci.tensorflow.org/buildStatus/icon?job=tensorflow-master-cpu)"] textClose:[86, 87, "]"] linkOpen:[87, 88, "("] url:[88, 139, "https://ci.tensorflow.org/job/tensorflow-master-cpu"] pageRef:[88, 139, "https://ci.tensorflow.org/job/tensorflow-master-cpu"] linkClose:[139, 140, ")"]
      Image[1, 86] textOpen:[1, 3, "!["] text:[3, 15, "Build Status"] textClose:[15, 16, "]"] linkOpen:[16, 17, "("] url:[17, 85, "https://ci.tensorflow.org/buildStatus/icon?job=tensorflow-master-cpu"] pageRef:[17, 85, "https://ci.tensorflow.org/buildStatus/icon?job=tensorflow-master-cpu"] linkClose:[85, 86, ")"]
        Text[3, 15] chars:[3, 15, "Build … tatus"]
````````````````````````````````


## Issue 158

Issue #158, HTML comments parse as blocks despite having following text

```````````````````````````````` example Issue 158: 1
<!--Comment--> Markdown text.
.
<!--Comment--> Markdown text.
.
Document[0, 30]
  HtmlCommentBlock[0, 30]
````````````````````````````````


```````````````````````````````` example(Issue 158: 2) options(html-comment-full-lines)
<!--Comment--> Markdown text.
.
<p><!--Comment--> Markdown text.</p>
.
Document[0, 29]
  Paragraph[0, 29]
    HtmlInlineComment[0, 14] chars:[0, 14, "<!--C … nt-->"]
    Text[14, 29] chars:[14, 29, " Mark … text."]
````````````````````````````````


The opening tag can be indented 1-3 spaces, but not 4:

```````````````````````````````` example Issue 158: 3
  <!-- foo -->

    <!-- foo -->
.
  <!-- foo -->
<pre><code>&lt;!-- foo --&gt;
</code></pre>
.
Document[0, 33]
  HtmlCommentBlock[0, 15]
  IndentedCodeBlock[20, 33]
````````````````````````````````


```````````````````````````````` example Issue 158: 4
<!-- Foo

bar
   baz -->
okay
.
<!-- Foo

bar
   baz -->
<p>okay</p>
.
Document[0, 30]
  HtmlCommentBlock[0, 25]
  Paragraph[25, 30]
    Text[25, 29] chars:[25, 29, "okay"]
````````````````````````````````


## Issue 225 

Issue #225, BlankLine nodes within IndentedCodeBlock

```````````````````````````````` example(Issue 225: 1) options(keep-blank-lines)
    indented code line 1
    
    indented code line 2
    
end of indented code    
    
.
<pre><code>indented code line 1

indented code line 2
</code></pre>
<p>end of indented code</p>
.
Document[0, 90]
  IndentedCodeBlock[4, 55]
  BlankLine[55, 60]
  Paragraph[60, 85] isTrailingBlankLine
    Text[60, 80] chars:[60, 80, "end o …  code"]
  BlankLine[85, 90]
````````````````````````````````


```````````````````````````````` example(Issue 225: 2) options(keep-blank-lines)
```
fenced code line 1

fenced code line 2

```
    
end of indented code    
    
.
<pre><code>fenced code line 1

fenced code line 2

</code></pre>
<p>end of indented code</p>
.
Document[0, 83]
  FencedCodeBlock[0, 47] open:[0, 3, "```"] content:[4, 44] lines[4] close:[44, 47, "```"]
    Text[4, 44] chars:[4, 44, "fence … e 2\n\n"]
  BlankLine[48, 53]
  Paragraph[53, 78] isTrailingBlankLine
    Text[53, 73] chars:[53, 73, "end o …  code"]
  BlankLine[78, 83]
````````````````````````````````


## Issue 221

Issue #221, XSS: Javascript execution through links

```````````````````````````````` example Issue 221: 1
[Something New](javascript:alert(1))
.
<p>Something New</p>
.
Document[0, 37]
  Paragraph[0, 37]
    Link[0, 36] textOpen:[0, 1, "["] text:[1, 14, "Something New"] textClose:[14, 15, "]"] linkOpen:[15, 16, "("] url:[16, 35, "javascript:alert(1)"] pageRef:[16, 35, "javascript:alert(1)"] linkClose:[35, 36, ")"]
      Text[1, 14] chars:[1, 14, "Somet … g New"]
````````````````````````````````


```````````````````````````````` example(Issue 221: 2) options(allow-javascript)
[Something New](javascript:alert(1))
.
<p><a href="javascript:alert(1)">Something New</a></p>
.
Document[0, 36]
  Paragraph[0, 36]
    Link[0, 36] textOpen:[0, 1, "["] text:[1, 14, "Something New"] textClose:[14, 15, "]"] linkOpen:[15, 16, "("] url:[16, 35, "javascript:alert(1)"] pageRef:[16, 35, "javascript:alert(1)"] linkClose:[35, 36, ")"]
      Text[1, 14] chars:[1, 14, "Somet … g New"]
````````````````````````````````


## Issue 243

Issue #243

```````````````````````````````` example Issue 243: 1
![Alt text][id]

[id]: https://www.example.com/img.png "test"
.
<p><img src="https://www.example.com/img.png" alt="Alt text" title="test" /></p>
.
Document[0, 62]
  Paragraph[0, 16] isTrailingBlankLine
    ImageRef[0, 15] textOpen:[0, 2, "!["] text:[2, 10, "Alt text"] textClose:[10, 11, "]"] referenceOpen:[11, 12, "["] reference:[12, 14, "id"] referenceClose:[14, 15, "]"]
      Text[2, 10] chars:[2, 10, "Alt text"]
  Reference[17, 61] refOpen:[17, 18, "["] ref:[18, 20, "id"] refClose:[20, 22, "]:"] url:[23, 54, "https://www.example.com/img.png"] titleOpen:[55, 56, "\""] title:[56, 60, "test"] titleClose:[60, 61, "\""]
````````````````````````````````


bare image

```````````````````````````````` example Issue 243: 2
![id][]

[id]: https://www.example.com/img.png "test"
.
<p><img src="https://www.example.com/img.png" alt="id" title="test" /></p>
.
Document[0, 54]
  Paragraph[0, 8] isTrailingBlankLine
    ImageRef[0, 7] referenceOpen:[0, 2, "!["] reference:[2, 4, "id"] referenceClose:[4, 5, "]"] textOpen:[5, 6, "["] textClose:[6, 7, "]"]
      Text[2, 4] chars:[2, 4, "id"]
  Reference[9, 53] refOpen:[9, 10, "["] ref:[10, 12, "id"] refClose:[12, 14, "]:"] url:[15, 46, "https://www.example.com/img.png"] titleOpen:[47, 48, "\""] title:[48, 52, "test"] titleClose:[52, 53, "\""]
````````````````````````````````


Issue 246

Issue #246

```````````````````````````````` example Issue 243: 3
* May 23 - [[Ahnlab] [KR] Andariel Group Trend Report](http://download.ahnlab.com/kr/site/library/[Report]Andariel_Threat_Group.pdf)
.
<ul>
  <li>May 23 - <a href="http://download.ahnlab.com/kr/site/library/%5BReport%5DAndariel_Threat_Group.pdf">[Ahnlab] [KR] Andariel Group Trend Report</a></li>
</ul>
.
Document[0, 133]
  BulletList[0, 133] isTight
    BulletListItem[0, 133] open:[0, 1, "*"] isTight
      Paragraph[2, 133]
        Text[2, 11] chars:[2, 11, "May 23 - "]
        Link[11, 132] textOpen:[11, 12, "["] text:[12, 53, "[Ahnlab] [KR] Andariel Group Trend Report"] textClose:[53, 54, "]"] linkOpen:[54, 55, "("] url:[55, 131, "http://download.ahnlab.com/kr/site/library/[Report]Andariel_Threat_Group.pdf"] pageRef:[55, 131, "http://download.ahnlab.com/kr/site/library/[Report]Andariel_Threat_Group.pdf"] linkClose:[131, 132, ")"]
          Text[12, 53] chars:[12, 53, "[Ahnl … eport"]
````````````````````````````````


## Issue 254 

Issue #254, customized HTML_BLOCK_TAGS Parser option seems not taken into account

```````````````````````````````` example(Issue 254: 1) options(custom-html-block)
<warp10-warpscript-widget>

//There should not be no <p> here
**no md**

%>
</warp10-warpscript-widget>
.
<warp10-warpscript-widget>

//There should not be no <p> here
**no md**

%>
</warp10-warpscript-widget>
.
Document[0, 103]
  HtmlBlock[0, 103]
````````````````````````````````


without custom tags

```````````````````````````````` example(Issue 254: 2) options(deep-html-parser)
<warp10-warpscript-widget>

//There should not be no <p> here
**no md**

%>
</warp10-warpscript-widget>
.
<p><warp10-warpscript-widget></p>
<p>//There should not be no <p> here
<strong>no md</strong></p>
<p>%&gt;
</warp10-warpscript-widget></p>
.
Document[0, 103]
  Paragraph[0, 27] isTrailingBlankLine
    HtmlInline[0, 26] chars:[0, 26, "<warp … dget>"]
  Paragraph[28, 72] isTrailingBlankLine
    Text[28, 53] chars:[28, 53, "//The … e no "]
    HtmlInline[53, 56] chars:[53, 56, "<p>"]
    Text[56, 61] chars:[56, 61, " here"]
    SoftLineBreak[61, 62]
    StrongEmphasis[62, 71] textOpen:[62, 64, "**"] text:[64, 69, "no md"] textClose:[69, 71, "**"]
      Text[64, 69] chars:[64, 69, "no md"]
  Paragraph[73, 103]
    Text[73, 75] chars:[73, 75, "%>"]
    SoftLineBreak[75, 76]
    HtmlInline[76, 103] chars:[76, 103, "</war … dget>"]
````````````````````````````````


## Issue 262

```````````````````````````````` example Issue 262: 1
<div>
<div>

# test 1

</div>
<div>

# test 2

</div>
</div>
.
<div>
<div>
<h1>test 1</h1>
</div>
<div>
<h1>test 2</h1>
</div>
</div>
.
Document[0, 61]
  HtmlBlock[0, 12]
  Heading[13, 21] textOpen:[13, 14, "#"] text:[15, 21, "test 1"]
    Text[15, 21] chars:[15, 21, "test 1"]
  HtmlBlock[23, 36]
  Heading[37, 45] textOpen:[37, 38, "#"] text:[39, 45, "test 2"]
    Text[39, 45] chars:[39, 45, "test 2"]
  HtmlBlock[47, 61]
````````````````````````````````


```````````````````````````````` example(Issue 262: 2) options(deep-html-parser)
<div>
<div>

# test 1

</div>
<div>

# test 2

</div>
</div>
.
<div>
<div>

# test 1

</div>
<div>

# test 2

</div>
</div>
.
Document[0, 60]
  HtmlBlock[0, 60]
````````````````````````````````


## Issue xxx-1

Issue xxx-1, leading spaces tab not part of indented code, which is correct

```````````````````````````````` example Issue xxx-1: 1
    @Test
    public void testPipeInTable() {
.
<pre><code>@Test
public void testPipeInTable() {
</code></pre>
.
Document[0, 46]
  IndentedCodeBlock[4, 46]
````````````````````````````````


```````````````````````````````` example Issue xxx-1: 2
 →@Test
 →public void testPipeInTable() {
.
<pre><code>@Test
public void testPipeInTable() {
</code></pre>
.
Document[0, 42]
  IndentedCodeBlock[2, 42]
````````````````````````````````


```````````````````````````````` example Issue xxx-1: 3
  →@Test
  →public void testPipeInTable() {
.
<pre><code>@Test
public void testPipeInTable() {
</code></pre>
.
Document[0, 44]
  IndentedCodeBlock[3, 44]
````````````````````````````````


```````````````````````````````` example Issue xxx-1: 4
   →@Test
   →public void testPipeInTable() {
.
<pre><code>@Test
public void testPipeInTable() {
</code></pre>
.
Document[0, 46]
  IndentedCodeBlock[4, 46]
````````````````````````````````


```````````````````````````````` example Issue xxx-1: 5
    →@Test
    →public void testPipeInTable() {
.
<pre><code>→@Test
→public void testPipeInTable() {
</code></pre>
.
Document[0, 48]
  IndentedCodeBlock[4, 48]
````````````````````````````````


## Issue 271

Issue #271, Regression? Comments are presereved in 0.26.4 but removed in 0.34.40

```````````````````````````````` example Issue 271: 1
<!-- TOC depthFrom:2 -->

- [Quickstart](#quickstart)
  - [Library](#library)
  - [Command-line](#command-line)

<!-- /TOC -->

.
<!-- TOC depthFrom:2 -->
<ul>
  <li><a href="#quickstart">Quickstart</a>
    <ul>
      <li><a href="#library">Library</a></li>
      <li><a href="#command-line">Command-line</a></li>
    </ul>
  </li>
</ul>
<!-- /TOC -->
.
Document[0, 128]
  HtmlCommentBlock[0, 25]
  BulletList[26, 112] isTight
    BulletListItem[26, 112] open:[26, 27, "-"] isTight
      Paragraph[28, 54]
        Link[28, 53] textOpen:[28, 29, "["] text:[29, 39, "Quickstart"] textClose:[39, 40, "]"] linkOpen:[40, 41, "("] url:[41, 52, "#quickstart"] pageRef:[41, 41] anchorMarker:[41, 42, "#"] anchorRef:[42, 52, "quickstart"] linkClose:[52, 53, ")"]
          Text[29, 39] chars:[29, 39, "Quickstart"]
      BulletList[56, 112] isTight
        BulletListItem[56, 78] open:[56, 57, "-"] isTight
          Paragraph[58, 78]
            Link[58, 77] textOpen:[58, 59, "["] text:[59, 66, "Library"] textClose:[66, 67, "]"] linkOpen:[67, 68, "("] url:[68, 76, "#library"] pageRef:[68, 68] anchorMarker:[68, 69, "#"] anchorRef:[69, 76, "library"] linkClose:[76, 77, ")"]
              Text[59, 66] chars:[59, 66, "Library"]
        BulletListItem[80, 112] open:[80, 81, "-"] isTight hadBlankLineAfter
          Paragraph[82, 112] isTrailingBlankLine
            Link[82, 111] textOpen:[82, 83, "["] text:[83, 95, "Command-line"] textClose:[95, 96, "]"] linkOpen:[96, 97, "("] url:[97, 110, "#command-line"] pageRef:[97, 97] anchorMarker:[97, 98, "#"] anchorRef:[98, 110, "command-line"] linkClose:[110, 111, ")"]
              Text[83, 95] chars:[83, 95, "Comma … -line"]
  HtmlCommentBlock[113, 127]
````````````````````````````````


## Issue xxx-2

Issue xxx-2, leading spaces tab not part of indented code, which is correct

```````````````````````````````` example Issue xxx-2: 1
[ www.google.com  ](https://www.google.com)
.
<p><a href="https://www.google.com"> www.google.com  </a></p>
.
Document[0, 44]
  Paragraph[0, 44]
    Link[0, 43] textOpen:[0, 1, "["] text:[1, 18, " www.google.com  "] textClose:[18, 19, "]"] linkOpen:[19, 20, "("] url:[20, 42, "https://www.google.com"] pageRef:[20, 42, "https://www.google.com"] linkClose:[42, 43, ")"]
      Text[1, 18] chars:[1, 18, " www. … com  "]
````````````````````````````````


## Issue xxx-3

Issue xxx-3, pre and code have spaces between them in generated html for fenced code, but only
with pass-through html writer option

```````````````````````````````` example(Issue xxx-3: 1) options(pass-through)
* Fix: back tab changes indent to wrong prefix, causing to mess up the list, caret marked by
  `|`:

  ```markdown
  7. Element types are:
     1. class - sub-types: 
        1. class, 
        2. interface, 
        3. enum, 
        4. singleton (effectively constructor becomes private)
     2. static member - sub-types: 
        1. field, function,
           1. | 
     3. member - all variations: constructor, field, function,
  ```
.
<ul>
<li>
<p>Fix: back tab changes indent to wrong prefix, causing to mess up the list, caret marked by
<code>|</code>:</p>
<pre><code class="language-markdown">7. Element types are:
   1. class - sub-types: 
      1. class, 
      2. interface, 
      3. enum, 
      4. singleton (effectively constructor becomes private)
   2. static member - sub-types: 
      1. field, function,
         1. | 
   3. member - all variations: constructor, field, function,
</code></pre>
</li>
</ul>
.
Document[0, 439]
  BulletList[0, 439] isLoose
    BulletListItem[0, 439] open:[0, 1, "*"] isLoose hadBlankLineAfter
      Paragraph[2, 100] isTrailingBlankLine
        Text[2, 92] chars:[2, 92, "Fix:  … ed by"]
        SoftLineBreak[92, 93]
        Code[95, 98] textOpen:[95, 96, "`"] text:[96, 97, "|"] textClose:[97, 98, "`"]
          Text[96, 97] chars:[96, 97, "|"]
        Text[98, 99] chars:[98, 99, ":"]
      FencedCodeBlock[103, 439] open:[103, 106, "```"] info:[106, 114, "markdown"] content:[117, 434] lines[10] close:[436, 439, "```"]
        Text[117, 434] chars:[117, 434, "7. El … ion,\n"]
````````````````````````````````


## Issue xxx-4

Parsing a sub-sequence as the document input

```````````````````````````````` example(Issue xxx-4: 1) options(sub-parse)
* Fix: back tab changes indent to wrong prefix, causing to mess up the list, caret marked by
  `|`:

  ```markdown
  7. Element types are:
     1. class - sub-types: 
        1. class, 
        2. interface, 
        3. enum, 
        4. singleton (effectively constructor becomes private)
     2. static member - sub-types: 
        1. field, function,
           1. | 
     3. member - all variations: constructor, field, function,
  ```
.
<ul>
  <li>
    <p>Fix: back tab changes indent to wrong prefix, causing to mess up the list, caret marked by
    <code>|</code>:</p>
    <pre><code class="language-markdown">7. Element types are:
   1. class - sub-types: 
      1. class, 
      2. interface, 
      3. enum, 
      4. singleton (effectively constructor becomes private)
   2. static member - sub-types: 
      1. field, function,
         1. | 
   3. member - all variations: constructor, field, function,
</code></pre>
  </li>
</ul>
.
Document[14, 454]
  BulletList[14, 453] isLoose
    BulletListItem[14, 453] open:[14, 15, "*"] isLoose hadBlankLineAfter
      Paragraph[16, 114] isTrailingBlankLine
        Text[16, 106] chars:[16, 106, "Fix:  … ed by"]
        SoftLineBreak[106, 107]
        Code[109, 112] textOpen:[109, 110, "`"] text:[110, 111, "|"] textClose:[111, 112, "`"]
          Text[110, 111] chars:[110, 111, "|"]
        Text[112, 113] chars:[112, 113, ":"]
      FencedCodeBlock[117, 453] open:[117, 120, "```"] info:[120, 128, "markdown"] content:[131, 448] lines[10] close:[450, 453, "```"]
        Text[131, 448] chars:[131, 448, "7. El … ion,\n"]
````````````````````````````````


```````````````````````````````` example(Issue xxx-4: 2) options(strip-indent)
> > * Fix: back tab changes indent to wrong prefix, causing to mess up the list, caret marked by
> >   `|`:
> > 
> >   ```markdown
> >   7. Element types are:
> >      1. class - sub-types: 
> >         1. class, 
> >         2. interface, 
> >         3. enum, 
> >         4. singleton (effectively constructor becomes private)
> >      2. static member - sub-types: 
> >         1. field, function,
> >            1. | 
> >      3. member - all variations: constructor, field, function,
> >   ```
.
<ul>
  <li>
    <p>Fix: back tab changes indent to wrong prefix, causing to mess up the list, caret marked by
    <code>|</code>:</p>
    <pre><code class="language-markdown">7. Element types are:
   1. class - sub-types: 
      1. class, 
      2. interface, 
      3. enum, 
      4. singleton (effectively constructor becomes private)
   2. static member - sub-types: 
      1. field, function,
         1. | 
   3. member - all variations: constructor, field, function,
</code></pre>
  </li>
</ul>
.
Document[4, 499]
  BulletList[4, 499] isLoose
    BulletListItem[4, 499] open:[4, 5, "*"] isLoose hadBlankLineAfter
      Paragraph[6, 108] isTrailingBlankLine
        Text[6, 96] chars:[6, 96, "Fix:  … ed by"]
        SoftLineBreak[96, 97]
        Code[103, 106] textOpen:[103, 104, "`"] text:[104, 105, "|"] textClose:[105, 106, "`"]
          Text[104, 105] chars:[104, 105, "|"]
        Text[106, 107] chars:[106, 107, ":"]
      FencedCodeBlock[119, 499] open:[119, 122, "```"] info:[122, 130, "markdown"] content:[137, 490] lines[10] close:[496, 499, "```"]
        Text[137, 490] chars:[137, 490, "7. El … ion,\n"]
````````````````````````````````


```````````````````````````````` example(Issue xxx-4: 3) options(sub-parse, strip-indent)
> > * Fix: back tab changes indent to wrong prefix, causing to mess up the list, caret marked by
> >   `|`:
> > 
> >   ```markdown
> >   7. Element types are:
> >      1. class - sub-types: 
> >         1. class, 
> >         2. interface, 
> >         3. enum, 
> >         4. singleton (effectively constructor becomes private)
> >      2. static member - sub-types: 
> >         1. field, function,
> >            1. | 
> >      3. member - all variations: constructor, field, function,
> >   ```
.
<ul>
  <li>
    <p>Fix: back tab changes indent to wrong prefix, causing to mess up the list, caret marked by
    <code>|</code>:</p>
    <pre><code class="language-markdown">7. Element types are:
   1. class - sub-types: 
      1. class, 
      2. interface, 
      3. enum, 
      4. singleton (effectively constructor becomes private)
   2. static member - sub-types: 
      1. field, function,
         1. | 
   3. member - all variations: constructor, field, function,
</code></pre>
  </li>
</ul>
.
Document[18, 514]
  BulletList[18, 513] isLoose
    BulletListItem[18, 513] open:[18, 19, "*"] isLoose hadBlankLineAfter
      Paragraph[20, 122] isTrailingBlankLine
        Text[20, 110] chars:[20, 110, "Fix:  … ed by"]
        SoftLineBreak[110, 111]
        Code[117, 120] textOpen:[117, 118, "`"] text:[118, 119, "|"] textClose:[119, 120, "`"]
          Text[118, 119] chars:[118, 119, "|"]
        Text[120, 121] chars:[120, 121, ":"]
      FencedCodeBlock[133, 513] open:[133, 136, "```"] info:[136, 144, "markdown"] content:[151, 504] lines[10] close:[510, 513, "```"]
        Text[151, 504] chars:[151, 504, "7. El … ion,\n"]
````````````````````````````````


## Issue xxx-01

Blank line at end of block quote not included in block quote

```````````````````````````````` example(Issue xxx-01: 1) options(keep-blank-lines)
> - Item
>
>       class Dummy {
>           private final int intValue;
> 
>           public Dummy(int intValue) {
>               this.intValue = intValue;
>           }
>       }
>
> #### Header
>
10. item
1. items

.
<blockquote>
  <ul>
    <li>
      <p>Item</p>
      <pre><code>class Dummy {
    private final int intValue;

    public Dummy(int intValue) {
        this.intValue = intValue;
    }
}
</code></pre>
    </li>
  </ul>
  <h4>Header</h4>
</blockquote>
<ol start="10">
  <li>item</li>
  <li>items</li>
</ol>
.
Document[0, 220]
  BlockQuote[0, 201] marker:[0, 1, ">"]
    BulletList[2, 183] isLoose
      BulletListItem[2, 183] open:[2, 3, "-"] isLoose hadBlankLineAfter
        Paragraph[4, 9] isTrailingBlankLine
          Text[4, 8] chars:[4, 8, "Item"]
        BlankLine[9, 11]
        IndentedCodeBlock[19, 183]
    BlankLine[183, 185]
    Heading[187, 198] textOpen:[187, 191, "####"] text:[192, 198, "Header"]
      Text[192, 198] chars:[192, 198, "Header"]
    BlankLine[199, 201]
  OrderedList[201, 219] isTight start:10 delimiter:'.'
    OrderedListItem[201, 210] open:[201, 204, "10."] isTight
      Paragraph[205, 210]
        Text[205, 209] chars:[205, 209, "item"]
    OrderedListItem[210, 219] open:[210, 212, "1."] isTight hadBlankLineAfter
      Paragraph[213, 219] isTrailingBlankLine
        Text[213, 218] chars:[213, 218, "items"]
  BlankLine[219, 220]
````````````````````````````````


```````````````````````````````` example(Issue xxx-01: 2) options(keep-blank-lines)
> #### Header
>

.
<blockquote>
  <h4>Header</h4>
</blockquote>
.
Document[0, 17]
  BlockQuote[0, 16] marker:[0, 1, ">"]
    Heading[2, 13] textOpen:[2, 6, "####"] text:[7, 13, "Header"]
      Text[7, 13] chars:[7, 13, "Header"]
    BlankLine[14, 16]
  BlankLine[16, 17]
````````````````````````````````


```````````````````````````````` example Issue xxx-01: 3
> #### Header
>

.
<blockquote>
  <h4>Header</h4>
</blockquote>
.
Document[0, 17]
  BlockQuote[0, 16] marker:[0, 1, ">"]
    Heading[2, 13] textOpen:[2, 6, "####"] text:[7, 13, "Header"]
      Text[7, 13] chars:[7, 13, "Header"]
    BlankLine[14, 16]
````````````````````````````````


Trailing blank lines in list should go to block quote not document

```````````````````````````````` example Issue xxx-01: 4
> 1. The first character of an end tag must be a U+003C LESS-THAN SIGN character (\<).
> 

.
<blockquote>
  <ol>
    <li>The first character of an end tag must be a U+003C LESS-THAN SIGN character (&lt;).</li>
  </ol>
</blockquote>
.
Document[0, 91]
  BlockQuote[0, 90] marker:[0, 1, ">"]
    OrderedList[2, 87] isTight delimiter:'.'
      OrderedListItem[2, 87] open:[2, 4, "1."] isTight hadBlankLineAfter
        Paragraph[5, 87] isTrailingBlankLine
          Text[5, 86] chars:[5, 86, "The f … (\<)."]
    BlankLine[87, 90]
````````````````````````````````


```````````````````````````````` example(Issue xxx-01: 5) options(keep-blank-lines)
> 1. The first character of an end tag must be a U+003C LESS-THAN SIGN character (\<).
> 

.
<blockquote>
  <ol>
    <li>The first character of an end tag must be a U+003C LESS-THAN SIGN character (&lt;).</li>
  </ol>
</blockquote>
.
Document[0, 91]
  BlockQuote[0, 90] marker:[0, 1, ">"]
    OrderedList[2, 87] isTight delimiter:'.'
      OrderedListItem[2, 87] open:[2, 4, "1."] isTight hadBlankLineAfter
        Paragraph[5, 87] isTrailingBlankLine
          Text[5, 86] chars:[5, 86, "The f … (\<)."]
    BlankLine[87, 90]
  BlankLine[90, 91]
````````````````````````````````


```````````````````````````````` example Issue xxx-01: 6
> ##### 8.1.2.2. End tags
> 
> End tags must have the following format:
> 
> 1. The first character of an end tag must be a U+003C LESS-THAN SIGN character (\<).
> 
> 2. The second character of an end tag must be a U+002F SOLIDUS character (/).
> 
> 3. The next few characters of an end tag must be the element's
>    [tag name](https://www.w3.org/TR/html5/syntax.html#tag-name).
> 
> 4. After the tag name, there may be one or more
>    [space characters](https://www.w3.org/TR/html5/infrastructure.html#space-characters).
> 
> 5. Finally, end tags must be closed by a U+003E GREATER-THAN SIGN character (\>).
> 

.
<blockquote>
  <h5>8.1.2.2. End tags</h5>
  <p>End tags must have the following format:</p>
  <ol>
    <li>
      <p>The first character of an end tag must be a U+003C LESS-THAN SIGN character (&lt;).</p>
    </li>
    <li>
      <p>The second character of an end tag must be a U+002F SOLIDUS character (/).</p>
    </li>
    <li>
      <p>The next few characters of an end tag must be the element's
      <a href="https://www.w3.org/TR/html5/syntax.html#tag-name">tag name</a>.</p>
    </li>
    <li>
      <p>After the tag name, there may be one or more
      <a href="https://www.w3.org/TR/html5/infrastructure.html#space-characters">space characters</a>.</p>
    </li>
    <li>
      <p>Finally, end tags must be closed by a U+003E GREATER-THAN SIGN character (&gt;).</p>
    </li>
  </ol>
</blockquote>
.
Document[0, 615]
  BlockQuote[0, 614] marker:[0, 1, ">"]
    Heading[2, 25] textOpen:[2, 7, "#####"] text:[8, 25, "8.1.2.2. End tags"]
      Text[8, 25] chars:[8, 25, "8.1.2 …  tags"]
    BlankLine[26, 29]
    Paragraph[31, 72] isTrailingBlankLine
      Text[31, 71] chars:[31, 71, "End t … rmat:"]
    BlankLine[72, 75]
    OrderedList[77, 611] isLoose delimiter:'.'
      OrderedListItem[77, 162] open:[77, 79, "1."] isLoose hadBlankLineAfter
        Paragraph[80, 162] isTrailingBlankLine
          Text[80, 161] chars:[80, 161, "The f … (\<)."]
      OrderedListItem[167, 245] open:[167, 169, "2."] isLoose hadBlankLineAfter
        Paragraph[170, 245] isTrailingBlankLine
          Text[170, 244] chars:[170, 244, "The s …  (/)."]
      OrderedListItem[250, 380] open:[250, 252, "3."] isLoose hadBlankLineAfter
        Paragraph[253, 380] isTrailingBlankLine
          Text[253, 312] chars:[253, 312, "The n … ent's"]
          SoftLineBreak[312, 313]
          Link[318, 378] textOpen:[318, 319, "["] text:[319, 327, "tag name"] textClose:[327, 328, "]"] linkOpen:[328, 329, "("] url:[329, 377, "https://www.w3.org/TR/html5/syntax.html#tag-name"] pageRef:[329, 368, "https://www.w3.org/TR/html5/syntax.html"] anchorMarker:[368, 369, "#"] anchorRef:[369, 377, "tag-name"] linkClose:[377, 378, ")"]
            Text[319, 327] chars:[319, 327, "tag name"]
          Text[378, 379] chars:[378, 379, "."]
      OrderedListItem[385, 524] open:[385, 387, "4."] isLoose hadBlankLineAfter
        Paragraph[388, 524] isTrailingBlankLine
          Text[388, 432] chars:[388, 432, "After …  more"]
          SoftLineBreak[432, 433]
          Link[438, 522] textOpen:[438, 439, "["] text:[439, 455, "space characters"] textClose:[455, 456, "]"] linkOpen:[456, 457, "("] url:[457, 521, "https://www.w3.org/TR/html5/infrastructure.html#space-characters"] pageRef:[457, 504, "https://www.w3.org/TR/html5/infrastructure.html"] anchorMarker:[504, 505, "#"] anchorRef:[505, 521, "space-characters"] linkClose:[521, 522, ")"]
            Text[439, 455] chars:[439, 455, "space … cters"]
          Text[522, 523] chars:[522, 523, "."]
      OrderedListItem[529, 611] open:[529, 531, "5."] isLoose hadBlankLineAfter
        Paragraph[532, 611] isTrailingBlankLine
          Text[532, 610] chars:[532, 610, "Final … (\>)."]
    BlankLine[611, 614]
````````````````````````````````


```````````````````````````````` example(Issue xxx-01: 7) options(keep-blank-lines)
> ##### 8.1.2.2. End tags
> 
> End tags must have the following format:
> 
> 1. The first character of an end tag must be a U+003C LESS-THAN SIGN character (\<).
> 
> 2. The second character of an end tag must be a U+002F SOLIDUS character (/).
> 
> 3. The next few characters of an end tag must be the element's
>    [tag name](https://www.w3.org/TR/html5/syntax.html#tag-name).
> 
> 4. After the tag name, there may be one or more
>    [space characters](https://www.w3.org/TR/html5/infrastructure.html#space-characters).
> 
> 5. Finally, end tags must be closed by a U+003E GREATER-THAN SIGN character (\>).
> 

.
<blockquote>
  <h5>8.1.2.2. End tags</h5>
  <p>End tags must have the following format:</p>
  <ol>
    <li>
      <p>The first character of an end tag must be a U+003C LESS-THAN SIGN character (&lt;).</p>
    </li>
    <li>
      <p>The second character of an end tag must be a U+002F SOLIDUS character (/).</p>
    </li>
    <li>
      <p>The next few characters of an end tag must be the element's
      <a href="https://www.w3.org/TR/html5/syntax.html#tag-name">tag name</a>.</p>
    </li>
    <li>
      <p>After the tag name, there may be one or more
      <a href="https://www.w3.org/TR/html5/infrastructure.html#space-characters">space characters</a>.</p>
    </li>
    <li>
      <p>Finally, end tags must be closed by a U+003E GREATER-THAN SIGN character (&gt;).</p>
    </li>
  </ol>
</blockquote>
.
Document[0, 615]
  BlockQuote[0, 614] marker:[0, 1, ">"]
    Heading[2, 25] textOpen:[2, 7, "#####"] text:[8, 25, "8.1.2.2. End tags"]
      Text[8, 25] chars:[8, 25, "8.1.2 …  tags"]
    BlankLine[26, 29]
    Paragraph[31, 72] isTrailingBlankLine
      Text[31, 71] chars:[31, 71, "End t … rmat:"]
    BlankLine[72, 75]
    OrderedList[77, 611] isLoose delimiter:'.'
      OrderedListItem[77, 162] open:[77, 79, "1."] isLoose hadBlankLineAfter
        Paragraph[80, 162] isTrailingBlankLine
          Text[80, 161] chars:[80, 161, "The f … (\<)."]
      BlankLine[162, 165]
      OrderedListItem[167, 245] open:[167, 169, "2."] isLoose hadBlankLineAfter
        Paragraph[170, 245] isTrailingBlankLine
          Text[170, 244] chars:[170, 244, "The s …  (/)."]
      BlankLine[245, 248]
      OrderedListItem[250, 380] open:[250, 252, "3."] isLoose hadBlankLineAfter
        Paragraph[253, 380] isTrailingBlankLine
          Text[253, 312] chars:[253, 312, "The n … ent's"]
          SoftLineBreak[312, 313]
          Link[318, 378] textOpen:[318, 319, "["] text:[319, 327, "tag name"] textClose:[327, 328, "]"] linkOpen:[328, 329, "("] url:[329, 377, "https://www.w3.org/TR/html5/syntax.html#tag-name"] pageRef:[329, 368, "https://www.w3.org/TR/html5/syntax.html"] anchorMarker:[368, 369, "#"] anchorRef:[369, 377, "tag-name"] linkClose:[377, 378, ")"]
            Text[319, 327] chars:[319, 327, "tag name"]
          Text[378, 379] chars:[378, 379, "."]
      BlankLine[380, 383]
      OrderedListItem[385, 524] open:[385, 387, "4."] isLoose hadBlankLineAfter
        Paragraph[388, 524] isTrailingBlankLine
          Text[388, 432] chars:[388, 432, "After …  more"]
          SoftLineBreak[432, 433]
          Link[438, 522] textOpen:[438, 439, "["] text:[439, 455, "space characters"] textClose:[455, 456, "]"] linkOpen:[456, 457, "("] url:[457, 521, "https://www.w3.org/TR/html5/infrastructure.html#space-characters"] pageRef:[457, 504, "https://www.w3.org/TR/html5/infrastructure.html"] anchorMarker:[504, 505, "#"] anchorRef:[505, 521, "space-characters"] linkClose:[521, 522, ")"]
            Text[439, 455] chars:[439, 455, "space … cters"]
          Text[522, 523] chars:[522, 523, "."]
      BlankLine[524, 527]
      OrderedListItem[529, 611] open:[529, 531, "5."] isLoose hadBlankLineAfter
        Paragraph[532, 611] isTrailingBlankLine
          Text[532, 610] chars:[532, 610, "Final … (\>)."]
    BlankLine[611, 614]
  BlankLine[614, 615]
````````````````````````````````


