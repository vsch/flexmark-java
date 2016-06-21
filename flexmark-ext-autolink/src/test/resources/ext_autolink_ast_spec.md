---
title: Autolink Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Autolink 

Autolink extension converts links and e-mail addresses to links in the markdown source.

The tests here are converted to commonmark spec.txt format and AST expected results added.

```````````````````````````````` example Autolink: 1
foo http://one.org/ bar http://two.org/
.
<p>foo <a href="http://one.org/">http://one.org/</a> bar <a href="http://two.org/">http://two.org/</a></p>
.
Document[0, 40]
  Paragraph[0, 40]
    Text[0, 4] chars:[0, 4, "foo "]
    AutoLink[4, 19] textOpen:[0, 0] text:[4, 19, "http://one.org/"] textClose:[0, 0]
      Text[4, 19] chars:[4, 19, "http:"...".org/"]
    Text[19, 24] chars:[19, 24, " bar "]
    AutoLink[24, 39] textOpen:[0, 0] text:[24, 39, "http://two.org/"] textClose:[0, 0]
      Text[24, 39] chars:[24, 39, "http:"...".org/"]
````````````````````````````````


```````````````````````````````` example Autolink: 2
foo http://one.org/ bar `code` baz http://two.org/
.
<p>foo <a href="http://one.org/">http://one.org/</a> bar <code>code</code> baz <a href="http://two.org/">http://two.org/</a></p>
.
Document[0, 51]
  Paragraph[0, 51]
    Text[0, 4] chars:[0, 4, "foo "]
    AutoLink[4, 19] textOpen:[0, 0] text:[4, 19, "http://one.org/"] textClose:[0, 0]
      Text[4, 19] chars:[4, 19, "http:"...".org/"]
    Text[19, 24] chars:[19, 24, " bar "]
    Code[24, 30] textOpen:[24, 25, "`"] text:[25, 29, "code"] textClose:[29, 30, "`"]
    Text[30, 35] chars:[30, 35, " baz "]
    AutoLink[35, 50] textOpen:[0, 0] text:[35, 50, "http://two.org/"] textClose:[0, 0]
      Text[35, 50] chars:[35, 50, "http:"...".org/"]
````````````````````````````````


```````````````````````````````` example Autolink: 3
http://example.com/one. Example 2 (see http://example.com/two). Example 3: http://example.com/foo_(bar)
.
<p><a href="http://example.com/one">http://example.com/one</a>. Example 2 (see <a href="http://example.com/two">http://example.com/two</a>). Example 3: <a href="http://example.com/foo_(bar)">http://example.com/foo_(bar)</a></p>
.
Document[0, 104]
  Paragraph[0, 104]
    AutoLink[0, 22] textOpen:[0, 0] text:[0, 22, "http://example.com/one"] textClose:[0, 0]
      Text[0, 22] chars:[0, 22, "http:"..."m/one"]
    Text[22, 39] chars:[22, 39, ". Exa"..."(see "]
    AutoLink[39, 61] textOpen:[0, 0] text:[39, 61, "http://example.com/two"] textClose:[0, 0]
      Text[39, 61] chars:[39, 61, "http:"..."m/two"]
    Text[61, 75] chars:[61, 75, "). Ex"..."e 3: "]
    AutoLink[75, 103] textOpen:[0, 0] text:[75, 103, "http://example.com/foo_(bar)"] textClose:[0, 0]
      Text[75, 103] chars:[75, 103, "http:"..."(bar)"]
````````````````````````````````


```````````````````````````````` example Autolink: 4
foo@example.com
.
<p><a href="mailto:foo@example.com">foo@example.com</a></p>
.
Document[0, 16]
  Paragraph[0, 16]
    MailLink[0, 15] textOpen:[0, 0] text:[0, 15, "foo@example.com"] textClose:[0, 0]
      Text[0, 15] chars:[0, 15, "foo@e"..."e.com"]
````````````````````````````````


```````````````````````````````` example Autolink: 5
foo@com
.
<p>foo@com</p>
````````````````````````````````


```````````````````````````````` example Autolink: 6
<http://example.com>
.
<p><a href="http://example.com">http://example.com</a></p>
.
Document[0, 21]
  Paragraph[0, 21]
    AutoLink[0, 20] textOpen:[0, 1, "<"] text:[1, 19, "http://example.com"] textClose:[19, 20, ">"]
````````````````````````````````


