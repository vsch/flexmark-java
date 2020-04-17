---
title: Jekyll Tag Extension Formatter Spec
author: Vladimir Schneider
version: 1.0
date: '2018-09-07'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Jekyll Tag

Converts jekyll tag text to JekyllTag nodes.


```````````````````````````````` example Jekyll Tag: 1
{% tag %}
.
{% tag %}
.
Document[0, 9]
  JekyllTagBlock[9, 9]
    JekyllTag[0, 9] open:[0, 2, "{%"] tag:[3, 6, "tag"] parameters:[7, 7] close:[7, 9, "%}"]
````````````````````````````````


some tags

```````````````````````````````` example Jekyll Tag: 2
{% tag paramters %}
.
{% tag paramters %}
.
Document[0, 19]
  JekyllTagBlock[19, 19]
    JekyllTag[0, 19] open:[0, 2, "{%"] tag:[3, 6, "tag"] parameters:[7, 16, "paramters"] close:[17, 19, "%}"]
````````````````````````````````


inline tags

```````````````````````````````` example Jekyll Tag: 3
 {% tag %}
.
 {% tag %}
.
Document[0, 10]
  Paragraph[1, 10]
    JekyllTag[1, 10] open:[1, 3, "{%"] tag:[4, 7, "tag"] parameters:[8, 8] close:[8, 10, "%}"]
````````````````````````````````


inline tags

```````````````````````````````` example Jekyll Tag: 4
 {% tag paramters %}
.
 {% tag paramters %}
.
Document[0, 20]
  Paragraph[1, 20]
    JekyllTag[1, 20] open:[1, 3, "{%"] tag:[4, 7, "tag"] parameters:[8, 17, "paramters"] close:[18, 20, "%}"]
````````````````````````````````


no blocks

```````````````````````````````` example(Jekyll Tag: 5) options(no-blocks)
{% tag %}
.
{% tag %}
.
Document[0, 9]
  Paragraph[0, 9]
    JekyllTag[0, 9] open:[0, 2, "{%"] tag:[3, 6, "tag"] parameters:[7, 7] close:[7, 9, "%}"]
````````````````````````````````


no blocks no inlines

```````````````````````````````` example(Jekyll Tag: 6) options(no-blocks, no-inlines)
{% tag %}
.
{% tag %}
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 9] chars:[0, 9, "{% tag %}"]
````````````````````````````````


no inlines

```````````````````````````````` example(Jekyll Tag: 7) options(no-inlines)
 {% tag %}
.
{% tag %}
.
Document[0, 10]
  Paragraph[1, 10]
    Text[1, 10] chars:[1, 10, "{% tag %}"]
````````````````````````````````


include

```````````````````````````````` example Jekyll Tag: 8
{% include  %}
.
{% include  %}
.
Document[0, 14]
  JekyllTagBlock[14, 14]
    JekyllTag[0, 14] open:[0, 2, "{%"] tag:[3, 10, "include"] parameters:[12, 12] close:[12, 14, "%}"]
````````````````````````````````


include

```````````````````````````````` example(Jekyll Tag: 9) options(dummy-identifier)
{% include ⎮ %}
.
{% include ⎮ %}
.
Document[0, 15]
  JekyllTagBlock[15, 15]
    JekyllTag[0, 15] open:[0, 2, "{%"] tag:[3, 10, "include"] parameters:[11, 12, "%1f"] close:[13, 15, "%}"]
````````````````````````````````


include

```````````````````````````````` example(Jekyll Tag: 10) options(includes, embed-includes)
{% include test.html %}
.
<h1>Heading 1</h1>
<p>test text</p>

.
Document[0, 23]
  JekyllTagBlock[23, 23]
    JekyllTag[0, 23] open:[0, 2, "{%"] tag:[3, 10, "include"] parameters:[11, 20, "test.html"] close:[21, 23, "%}"]
    HtmlBlock[0, 36]
````````````````````````````````


include

```````````````````````````````` example(Jekyll Tag: 11) options(includes)
{% include test.html %}
.
{% include test.html %}
.
Document[0, 23]
  JekyllTagBlock[23, 23]
    JekyllTag[0, 23] open:[0, 2, "{%"] tag:[3, 10, "include"] parameters:[11, 20, "test.html"] close:[21, 23, "%}"]
````````````````````````````````


