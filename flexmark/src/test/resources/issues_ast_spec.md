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
    ImageRef[1, 15] textOpen:[1, 2, "["] text:[2, 10, "alt text"] textClose:[10, 11, "]"] referenceOpen:[11, 12, "["] reference:[12, 14, "id"] referenceClose:[14, 15, "]"]
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
    ImageRef[47, 61] textOpen:[47, 48, "["] text:[48, 56, "alt text"] textClose:[56, 57, "]"] referenceOpen:[57, 58, "["] reference:[58, 60, "id"] referenceClose:[60, 61, "]"]
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
    ImageRef[39, 53] textOpen:[39, 40, "["] text:[40, 48, "alt text"] textClose:[48, 49, "]"] referenceOpen:[49, 50, "["] reference:[50, 52, "id"] referenceClose:[52, 53, "]"]
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
    ImageRef[1, 15] textOpen:[1, 2, "["] text:[2, 10, "alt text"] textClose:[10, 11, "]"] referenceOpen:[11, 12, "["] reference:[12, 14, "id"] referenceClose:[14, 15, "]"]
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


