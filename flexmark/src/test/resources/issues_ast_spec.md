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

