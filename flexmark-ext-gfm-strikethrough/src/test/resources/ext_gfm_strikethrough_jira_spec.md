---
title: Gfm Strikethrough Extension JIRA Spec
author: Vladimir Schneider
version: 0.1
date: '2016-09-24'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Gfm Strikethrough JIRA 

Converts ~~text~~ to strikethrough of text

The tests here are converted to commonmark spec.txt format and AST
expected results added.

```````````````````````````````` example Gfm Strikethrough JIRA: 1
~~foo~~
.
-foo-

.
Document[0, 8]
  Paragraph[0, 8]
    Strikethrough[0, 7] textOpen:[0, 2, "~~"] text:[2, 5, "foo"] textClose:[5, 7, "~~"]
      Text[2, 5] chars:[2, 5, "foo"]
````````````````````````````````

