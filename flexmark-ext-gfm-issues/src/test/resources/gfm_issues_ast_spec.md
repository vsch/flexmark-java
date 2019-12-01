---
title: GitHubIssues Extension Spec
author: Vladimir Schneider
version:
date: '2017-07-20'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## GfmIssues

Converts GitHub `#issue` syntax to GitHubIssue nodes that renders as a link to the issue

Only digits allowed

```````````````````````````````` example GfmIssues: 1
abc #123abc xyz
.
<p>abc #123abc xyz</p>
.
Document[0, 15]
  Paragraph[0, 15]
    Text[0, 15] chars:[0, 15, "abc # … c xyz"]
````````````````````````````````


```````````````````````````````` example GfmIssues: 2
abc #123-4 xyz
.
<p>abc <a href="issues/123">#123</a>-4 xyz</p>
.
Document[0, 14]
  Paragraph[0, 14]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmIssue[4, 8] textOpen:[4, 5, "#"] text:[5, 8, "123"]
    Text[8, 14] chars:[8, 14, "-4 xyz"]
````````````````````````````````


```````````````````````````````` example GfmIssues: 3
abc #123
.
<p>abc <a href="issues/123">#123</a></p>
.
Document[0, 8]
  Paragraph[0, 8]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmIssue[4, 8] textOpen:[4, 5, "#"] text:[5, 8, "123"]
````````````````````````````````


```````````````````````````````` example GfmIssues: 4
abc #123

.
<p>abc <a href="issues/123">#123</a></p>
.
Document[0, 10]
  Paragraph[0, 9] isTrailingBlankLine
    Text[0, 4] chars:[0, 4, "abc "]
    GfmIssue[4, 8] textOpen:[4, 5, "#"] text:[5, 8, "123"]
````````````````````````````````


make sure cr/lf work

```````````````````````````````` example GfmIssues: 5
abc #123⏎

.
<p>abc <a href="issues/123">#123</a></p>
.
Document[0, 11]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 4] chars:[0, 4, "abc "]
    GfmIssue[4, 8] textOpen:[4, 5, "#"] text:[5, 8, "123"]
````````````````````````````````


make sure cr works

```````````````````````````````` example(GfmIssues: 6) options(NO_FILE_EOL)
abc #123⏎
.
<p>abc <a href="issues/123">#123</a></p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmIssue[4, 8] textOpen:[4, 5, "#"] text:[5, 8, "123"]
````````````````````````````````


```````````````````````````````` example GfmIssues: 7
abc #123 xyz
.
<p>abc <a href="issues/123">#123</a> xyz</p>
.
Document[0, 12]
  Paragraph[0, 12]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmIssue[4, 8] textOpen:[4, 5, "#"] text:[5, 8, "123"]
    Text[8, 12] chars:[8, 12, " xyz"]
````````````````````````````````


```````````````````````````````` example GfmIssues: 8
abc #123
xyz
.
<p>abc <a href="issues/123">#123</a>
xyz</p>
.
Document[0, 12]
  Paragraph[0, 12]
    Text[0, 4] chars:[0, 4, "abc "]
    GfmIssue[4, 8] textOpen:[4, 5, "#"] text:[5, 8, "123"]
    SoftLineBreak[8, 9]
    Text[9, 12] chars:[9, 12, "xyz"]
````````````````````````````````


```````````````````````````````` example(GfmIssues: 9) options(root)
#123
.
<p><a href="https://github.com/vsch/flexmark-java/issues/123">#123</a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmIssue[0, 4] textOpen:[0, 1, "#"] text:[1, 4, "123"]
````````````````````````````````


```````````````````````````````` example(GfmIssues: 10) options(root, prefix)
#123
.
<p><a href="https://github.com/vsch/flexmark-java/issues?issue=123">#123</a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmIssue[0, 4] textOpen:[0, 1, "#"] text:[1, 4, "123"]
````````````````````````````````


```````````````````````````````` example(GfmIssues: 11) options(root, suffix)
#123
.
<p><a href="https://github.com/vsch/flexmark-java/issues/123&amp;">#123</a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmIssue[0, 4] textOpen:[0, 1, "#"] text:[1, 4, "123"]
````````````````````````````````


```````````````````````````````` example(GfmIssues: 12) options(root, prefix, suffix)
#123
.
<p><a href="https://github.com/vsch/flexmark-java/issues?issue=123&amp;">#123</a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmIssue[0, 4] textOpen:[0, 1, "#"] text:[1, 4, "123"]
````````````````````````````````


```````````````````````````````` example(GfmIssues: 13) options(root, prefix, suffix, bold)
#123
.
<p><a href="https://github.com/vsch/flexmark-java/issues?issue=123&amp;"><strong>#123</strong></a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmIssue[0, 4] textOpen:[0, 1, "#"] text:[1, 4, "123"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
#123
.
<p md-pos="0-4"><a md-pos="0-4" href="issues/123">#123</a></p>
.
Document[0, 4]
  Paragraph[0, 4]
    GfmIssue[0, 4] textOpen:[0, 1, "#"] text:[1, 4, "123"]
````````````````````````````````


## Issue 252

Issue #252, GfmUser and GfmIssue are not recognized if immediately followed by non-space
character

```````````````````````````````` example(Issue 252: 1) options(root, prefix, suffix)
Hello, @world, and #1!
.
<p>Hello, @world, and <a href="https://github.com/vsch/flexmark-java/issues?issue=1&amp;">#1</a>!</p>
.
Document[0, 22]
  Paragraph[0, 22]
    Text[0, 19] chars:[0, 19, "Hello …  and "]
    GfmIssue[19, 21] textOpen:[19, 20, "#"] text:[20, 21, "1"]
    Text[21, 22] chars:[21, 22, "!"]
````````````````````````````````


