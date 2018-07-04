---
title: GitHubUsers Extension Spec
author: Vladimir Schneider
version:
date: '2017-07-20'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## GfmUsers

Converts GitHub `@user` syntax to GitHubUser nodes that renders as a link to the user account
page

Only valid names

```````````````````````````````` example GfmUsers: 1
abc @abcdef1.asdasdf xyz
.
<p>abc @abcdef1.asdasdf xyz</p>
.
Document[0, 25]
  Paragraph[0, 25]
    Text[0, 24] chars:[0, 24, "abc @ … f xyz"]
````````````````````````````````


Max 39 characters

```````````````````````````````` example GfmUsers: 2
abc @1234567890123456789012345678901234567890
.
<p>abc @1234567890123456789012345678901234567890</p>
.
Document[0, 46]
  Paragraph[0, 46]
    Text[0, 45] chars:[0, 45, "abc @ … 67890"]
````````````````````````````````


Max 39 characters

```````````````````````````````` example GfmUsers: 3
abc @123456789012345678901234567890123456789
.
<p>abc <a href="https://github.com/123456789012345678901234567890123456789"><strong>@123456789012345678901234567890123456789</strong></a></p>
.
Document[0, 45]
  Paragraph[0, 45]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmUser[4, 44] textOpen:[4, 5, "@"] text:[5, 44, "123456789012345678901234567890123456789"]
````````````````````````````````


```````````````````````````````` example GfmUsers: 4
abc @123-4 xyz
.
<p>abc <a href="https://github.com/123-4"><strong>@123-4</strong></a> xyz</p>
.
Document[0, 15]
  Paragraph[0, 15]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmUser[4, 10] textOpen:[4, 5, "@"] text:[5, 10, "123-4"]
    Text[10, 14] chars:[10, 14, " xyz"]
````````````````````````````````


```````````````````````````````` example GfmUsers: 5
abc @123
.
<p>abc <a href="https://github.com/123"><strong>@123</strong></a></p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmUser[4, 8] textOpen:[4, 5, "@"] text:[5, 8, "123"]
````````````````````````````````


test CR/LF

```````````````````````````````` example GfmUsers: 6
abc @123⏎

.
<p>abc <a href="https://github.com/123"><strong>@123</strong></a></p>
.
Document[0, 11]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 4] chars:[0, 4, "abc "]
    GfmUser[4, 8] textOpen:[4, 5, "@"] text:[5, 8, "123"]
````````````````````````````````


test CR

```````````````````````````````` example(GfmUsers: 7) options(NO_FILE_EOL)
abc @123⏎
.
<p>abc <a href="https://github.com/123"><strong>@123</strong></a></p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmUser[4, 8] textOpen:[4, 5, "@"] text:[5, 8, "123"]
````````````````````````````````


```````````````````````````````` example GfmUsers: 8
abc @123

.
<p>abc <a href="https://github.com/123"><strong>@123</strong></a></p>
.
Document[0, 10]
  Paragraph[0, 9] isTrailingBlankLine
    Text[0, 4] chars:[0, 4, "abc "]
    GfmUser[4, 8] textOpen:[4, 5, "@"] text:[5, 8, "123"]
````````````````````````````````


```````````````````````````````` example GfmUsers: 9
abc @123 xyz
.
<p>abc <a href="https://github.com/123"><strong>@123</strong></a> xyz</p>
.
Document[0, 13]
  Paragraph[0, 13]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmUser[4, 8] textOpen:[4, 5, "@"] text:[5, 8, "123"]
    Text[8, 12] chars:[8, 12, " xyz"]
````````````````````````````````


```````````````````````````````` example GfmUsers: 10
abc @123
xyz
.
<p>abc <a href="https://github.com/123"><strong>@123</strong></a>
xyz</p>
.
Document[0, 13]
  Paragraph[0, 13]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmUser[4, 8] textOpen:[4, 5, "@"] text:[5, 8, "123"]
    SoftLineBreak[8, 9]
    Text[9, 12] chars:[9, 12, "xyz"]
````````````````````````````````


```````````````````````````````` example(GfmUsers: 11) options(root)
@123
.
<p><a href="http://github.com/123"><strong>@123</strong></a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmUser[0, 4] textOpen:[0, 1, "@"] text:[1, 4, "123"]
````````````````````````````````


```````````````````````````````` example(GfmUsers: 12) options(root, prefix)
@123
.
<p><a href="http://github.com?user=123"><strong>@123</strong></a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmUser[0, 4] textOpen:[0, 1, "@"] text:[1, 4, "123"]
````````````````````````````````


```````````````````````````````` example(GfmUsers: 13) options(root, suffix)
@123
.
<p><a href="http://github.com/123&amp;"><strong>@123</strong></a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmUser[0, 4] textOpen:[0, 1, "@"] text:[1, 4, "123"]
````````````````````````````````


```````````````````````````````` example(GfmUsers: 14) options(root, prefix, suffix)
@123
.
<p><a href="http://github.com?user=123&amp;"><strong>@123</strong></a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmUser[0, 4] textOpen:[0, 1, "@"] text:[1, 4, "123"]
````````````````````````````````


```````````````````````````````` example(GfmUsers: 15) options(root, prefix, suffix, plain)
@123
.
<p><a href="http://github.com?user=123&amp;">@123</a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmUser[0, 4] textOpen:[0, 1, "@"] text:[1, 4, "123"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
@123
.
<p md-pos="0-4"><a md-pos="0-4" href="https://github.com/123"><strong>@123</strong></a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmUser[0, 4] textOpen:[0, 1, "@"] text:[1, 4, "123"]
````````````````````````````````


