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

```````````````````````````````` example GfmUsers: 1
abc @abcdef1.asdasdf xyz
.
<p>abc <a href="https://github.com/abcdef1"><strong>@abcdef1</strong></a>.asdasdf xyz</p>
.
Document[0, 24]
  Paragraph[0, 24]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmUser[4, 12] textOpen:[4, 5, "@"] text:[5, 12, "abcdef1"]
    Text[12, 24] chars:[12, 24, ".asda … f xyz"]
````````````````````````````````


No e-mails, Issue #316,

```````````````````````````````` example GfmUsers: 2
abc test@test.com
.
<p>abc test@test.com</p>
.
Document[0, 17]
  Paragraph[0, 17]
    Text[0, 17] chars:[0, 17, "abc t … t.com"]
````````````````````````````````


```````````````````````````````` example GfmUsers: 3
abc_test@test.com
.
<p>abc_test@test.com</p>
.
Document[0, 17]
  Paragraph[0, 17]
    Text[0, 17] chars:[0, 17, "abc_t … t.com"]
````````````````````````````````


Max 39 characters

```````````````````````````````` example GfmUsers: 4
abc @1234567890123456789012345678901234567890
.
<p>abc @1234567890123456789012345678901234567890</p>
.
Document[0, 45]
  Paragraph[0, 45]
    Text[0, 45] chars:[0, 45, "abc @ … 67890"]
````````````````````````````````


Max 39 characters

```````````````````````````````` example GfmUsers: 5
abc @123456789012345678901234567890123456789
.
<p>abc <a href="https://github.com/123456789012345678901234567890123456789"><strong>@123456789012345678901234567890123456789</strong></a></p>
.
Document[0, 44]
  Paragraph[0, 44]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmUser[4, 44] textOpen:[4, 5, "@"] text:[5, 44, "123456789012345678901234567890123456789"]
````````````````````````````````


```````````````````````````````` example GfmUsers: 6
abc @123-4 xyz
.
<p>abc <a href="https://github.com/123-4"><strong>@123-4</strong></a> xyz</p>
.
Document[0, 14]
  Paragraph[0, 14]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmUser[4, 10] textOpen:[4, 5, "@"] text:[5, 10, "123-4"]
    Text[10, 14] chars:[10, 14, " xyz"]
````````````````````````````````


```````````````````````````````` example GfmUsers: 7
abc @123
.
<p>abc <a href="https://github.com/123"><strong>@123</strong></a></p>
.
Document[0, 8]
  Paragraph[0, 8]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmUser[4, 8] textOpen:[4, 5, "@"] text:[5, 8, "123"]
````````````````````````````````


test CR/LF

```````````````````````````````` example GfmUsers: 8
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

```````````````````````````````` example(GfmUsers: 9) options(NO_FILE_EOL)
abc @123⏎
.
<p>abc <a href="https://github.com/123"><strong>@123</strong></a></p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmUser[4, 8] textOpen:[4, 5, "@"] text:[5, 8, "123"]
````````````````````````````````


```````````````````````````````` example GfmUsers: 10
abc @123

.
<p>abc <a href="https://github.com/123"><strong>@123</strong></a></p>
.
Document[0, 10]
  Paragraph[0, 9] isTrailingBlankLine
    Text[0, 4] chars:[0, 4, "abc "]
    GfmUser[4, 8] textOpen:[4, 5, "@"] text:[5, 8, "123"]
````````````````````````````````


```````````````````````````````` example GfmUsers: 11
abc @123 xyz
.
<p>abc <a href="https://github.com/123"><strong>@123</strong></a> xyz</p>
.
Document[0, 12]
  Paragraph[0, 12]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmUser[4, 8] textOpen:[4, 5, "@"] text:[5, 8, "123"]
    Text[8, 12] chars:[8, 12, " xyz"]
````````````````````````````````


```````````````````````````````` example GfmUsers: 12
abc @123
xyz
.
<p>abc <a href="https://github.com/123"><strong>@123</strong></a>
xyz</p>
.
Document[0, 12]
  Paragraph[0, 12]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmUser[4, 8] textOpen:[4, 5, "@"] text:[5, 8, "123"]
    SoftLineBreak[8, 9]
    Text[9, 12] chars:[9, 12, "xyz"]
````````````````````````````````


```````````````````````````````` example(GfmUsers: 13) options(root)
@123
.
<p><a href="http://github.com/123"><strong>@123</strong></a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmUser[0, 4] textOpen:[0, 1, "@"] text:[1, 4, "123"]
````````````````````````````````


```````````````````````````````` example(GfmUsers: 14) options(root, prefix)
@123
.
<p><a href="http://github.com?user=123"><strong>@123</strong></a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmUser[0, 4] textOpen:[0, 1, "@"] text:[1, 4, "123"]
````````````````````````````````


```````````````````````````````` example(GfmUsers: 15) options(root, suffix)
@123
.
<p><a href="http://github.com/123&amp;"><strong>@123</strong></a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmUser[0, 4] textOpen:[0, 1, "@"] text:[1, 4, "123"]
````````````````````````````````


```````````````````````````````` example(GfmUsers: 16) options(root, prefix, suffix)
@123
.
<p><a href="http://github.com?user=123&amp;"><strong>@123</strong></a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmUser[0, 4] textOpen:[0, 1, "@"] text:[1, 4, "123"]
````````````````````````````````


```````````````````````````````` example(GfmUsers: 17) options(root, prefix, suffix, plain)
@123
.
<p><a href="http://github.com?user=123&amp;">@123</a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmUser[0, 4] textOpen:[0, 1, "@"] text:[1, 4, "123"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
@123
.
<p md-pos="0-4"><a md-pos="0-4" href="https://github.com/123"><strong>@123</strong></a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmUser[0, 4] textOpen:[0, 1, "@"] text:[1, 4, "123"]
````````````````````````````````


## Issue 252

Issue #252, GfmUser and GfmIssue are not recognized if immediately followed by non-space
character

```````````````````````````````` example(Issue 252: 1) options(root, prefix, suffix, plain)
Hello, @world, and #1!
.
<p>Hello, <a href="http://github.com?user=world&amp;">@world</a>, and #1!</p>
.
Document[0, 22]
  Paragraph[0, 22]
    Text[0, 7] chars:[0, 7, "Hello, "]
    GfmUser[7, 13] textOpen:[7, 8, "@"] text:[8, 13, "world"]
    Text[13, 22] chars:[13, 22, ", and #1!"]
````````````````````````````````


