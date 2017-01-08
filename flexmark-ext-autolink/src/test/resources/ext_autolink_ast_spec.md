---
title: Autolink Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Autolink

Autolink extension converts links and e-mail addresses to links in the markdown source.

The tests here are converted to commonmark spec.txt format and AST expected results added.

```````````````````````````````` example Autolink: 1
foo http://one.org/ bar http://two.org/
.
<p>foo <a href="http://one.org/">http://one.org/</a> bar <a href="http://two.org/">http://two.org/</a></p>
.
Document[0, 40]
  Paragraph[0, 40]
    TextBase[0, 39] chars:[0, 39, "foo h … .org/"]
      Text[0, 4] chars:[0, 4, "foo "]
      AutoLink[4, 19] text:[4, 19, "http://one.org/"]
        Text[4, 19] chars:[4, 19, "http: … .org/"]
      Text[19, 24] chars:[19, 24, " bar "]
      AutoLink[24, 39] text:[24, 39, "http://two.org/"]
        Text[24, 39] chars:[24, 39, "http: … .org/"]
````````````````````````````````


```````````````````````````````` example Autolink: 2
foo http://one.org/ bar `code` baz http://two.org/
.
<p>foo <a href="http://one.org/">http://one.org/</a> bar <code>code</code> baz <a href="http://two.org/">http://two.org/</a></p>
.
Document[0, 51]
  Paragraph[0, 51]
    TextBase[0, 24] chars:[0, 24, "foo h …  bar "]
      Text[0, 4] chars:[0, 4, "foo "]
      AutoLink[4, 19] text:[4, 19, "http://one.org/"]
        Text[4, 19] chars:[4, 19, "http: … .org/"]
      Text[19, 24] chars:[19, 24, " bar "]
    Code[24, 30] textOpen:[24, 25, "`"] text:[25, 29, "code"] textClose:[29, 30, "`"]
    TextBase[30, 50] chars:[30, 50, " baz  … .org/"]
      Text[30, 35] chars:[30, 35, " baz "]
      AutoLink[35, 50] text:[35, 50, "http://two.org/"]
        Text[35, 50] chars:[35, 50, "http: … .org/"]
````````````````````````````````


```````````````````````````````` example(Autolink: 3) options(no-autolink)
http://example.com/one. Example 2 (see http://example.com/two). Example 3: http://example.com/foo_(bar)
.
<p>http://example.com/one. Example 2 (see http://example.com/two). Example 3: http://example.com/foo_(bar)</p>
.
Document[0, 103]
  Paragraph[0, 103]
    Text[0, 103] chars:[0, 103, "http: … (bar)"]
````````````````````````````````


```````````````````````````````` example Autolink: 4
http://example.com/one. Example 2 (see http://example.com/two). Example 3: http://example.com/foo_(bar)
.
<p><a href="http://example.com/one">http://example.com/one</a>. Example 2 (see <a href="http://example.com/two">http://example.com/two</a>). Example 3: <a href="http://example.com/foo_(bar)">http://example.com/foo_(bar)</a></p>
.
Document[0, 104]
  Paragraph[0, 104]
    TextBase[0, 103] chars:[0, 103, "http: … (bar)"]
      AutoLink[0, 22] text:[0, 22, "http://example.com/one"]
        Text[0, 22] chars:[0, 22, "http: … m/one"]
      Text[22, 39] chars:[22, 39, ". Exa … (see "]
      AutoLink[39, 61] text:[39, 61, "http://example.com/two"]
        Text[39, 61] chars:[39, 61, "http: … m/two"]
      Text[61, 75] chars:[61, 75, "). Ex … e 3: "]
      AutoLink[75, 103] text:[75, 103, "http://example.com/foo_(bar)"]
        Text[75, 103] chars:[75, 103, "http: … (bar)"]
````````````````````````````````


```````````````````````````````` example Autolink: 5
foo@example.com
.
<p><a href="mailto:foo@example.com">foo@example.com</a></p>
.
Document[0, 16]
  Paragraph[0, 16]
    TextBase[0, 15] chars:[0, 15, "foo@e … e.com"]
      MailLink[0, 15] text:[0, 15, "foo@example.com"]
        Text[0, 15] chars:[0, 15, "foo@e … e.com"]
````````````````````````````````


```````````````````````````````` example Autolink: 6
foo@com
.
<p>foo@com</p>
````````````````````````````````


```````````````````````````````` example Autolink: 7
<http://example.com>
.
<p><a href="http://example.com">http://example.com</a></p>
.
Document[0, 21]
  Paragraph[0, 21]
    AutoLink[0, 20] textOpen:[0, 1, "<"] text:[1, 19, "http://example.com"] textClose:[19, 20, ">"]
````````````````````````````````


Issue #37, How to add attribute 'class' to AutoLink node

```````````````````````````````` example Autolink: 8
https://google.com/abc?hello=456&world=789
.
<p><a href="https://google.com/abc?hello=456&amp;world=789">https://google.com/abc?hello=456&amp;world=789</a></p>
.
Document[0, 43]
  Paragraph[0, 43]
    TextBase[0, 42] chars:[0, 42, "https … d=789"]
      AutoLink[0, 42] text:[0, 42, "https://google.com/abc?hello=456&world=789"]
        Text[0, 42] chars:[0, 42, "https … d=789"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
foo@example.com
http://example.com
.
<p md-pos="0-34"><a md-pos="0-15" href="mailto:foo@example.com">foo@example.com</a>
<a md-pos="16-34" href="http://example.com">http://example.com</a></p>
.
Document[0, 34]
  Paragraph[0, 34]
    TextBase[0, 15] chars:[0, 15, "foo@e … e.com"]
      MailLink[0, 15] text:[0, 15, "foo@example.com"]
        Text[0, 15] chars:[0, 15, "foo@e … e.com"]
    SoftLineBreak[15, 16]
    TextBase[16, 34] chars:[16, 34, "http: … e.com"]
      AutoLink[16, 34] text:[16, 34, "http://example.com"]
        Text[16, 34] chars:[16, 34, "http: … e.com"]
````````````````````````````````


