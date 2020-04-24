---
title: JekyllTag Extension Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Jekyll Tag

Converts jekyll tag text to JekyllTag nodes.

no empty tags

```````````````````````````````` example Jekyll Tag: 1
{% %}
.
<p>{% %}</p>
.
Document[0, 5]
  Paragraph[0, 5]
    Text[0, 5] chars:[0, 5, "{% %}"]
````````````````````````````````


some tags

```````````````````````````````` example Jekyll Tag: 2
{% tag %}
.
.
Document[0, 9]
  JekyllTagBlock[9, 9]
    JekyllTag[0, 9] open:[0, 2, "{%"] tag:[3, 6, "tag"] parameters:[7, 7] close:[7, 9, "%}"]
````````````````````````````````


some tags

```````````````````````````````` example Jekyll Tag: 3
{% tag paramters %}
.
.
Document[0, 19]
  JekyllTagBlock[19, 19]
    JekyllTag[0, 19] open:[0, 2, "{%"] tag:[3, 6, "tag"] parameters:[7, 16, "paramters"] close:[17, 19, "%}"]
````````````````````````````````


inline tags

```````````````````````````````` example Jekyll Tag: 4
 {% tag %}
.
<p></p>
.
Document[0, 10]
  Paragraph[1, 10]
    JekyllTag[1, 10] open:[1, 3, "{%"] tag:[4, 7, "tag"] parameters:[8, 8] close:[8, 10, "%}"]
````````````````````````````````


inline tags

```````````````````````````````` example Jekyll Tag: 5
 {% tag paramters %}
.
<p></p>
.
Document[0, 20]
  Paragraph[1, 20]
    JekyllTag[1, 20] open:[1, 3, "{%"] tag:[4, 7, "tag"] parameters:[8, 17, "paramters"] close:[18, 20, "%}"]
````````````````````````````````


no blocks

```````````````````````````````` example(Jekyll Tag: 6) options(no-blocks)
{% tag %}
.
<p></p>
.
Document[0, 9]
  Paragraph[0, 9]
    JekyllTag[0, 9] open:[0, 2, "{%"] tag:[3, 6, "tag"] parameters:[7, 7] close:[7, 9, "%}"]
````````````````````````````````


no blocks no inlines

```````````````````````````````` example(Jekyll Tag: 7) options(no-blocks, no-inlines)
{% tag %}
.
<p>{% tag %}</p>
.
Document[0, 9]
  Paragraph[0, 9]
    Text[0, 9] chars:[0, 9, "{% tag %}"]
````````````````````````````````


no inlines

```````````````````````````````` example(Jekyll Tag: 8) options(no-inlines)
 {% tag %}
.
<p>{% tag %}</p>
.
Document[0, 10]
  Paragraph[1, 10]
    Text[1, 10] chars:[1, 10, "{% tag %}"]
````````````````````````````````


include

```````````````````````````````` example Jekyll Tag: 9
{% include  %}
.
.
Document[0, 14]
  JekyllTagBlock[14, 14]
    JekyllTag[0, 14] open:[0, 2, "{%"] tag:[3, 10, "include"] parameters:[12, 12] close:[12, 14, "%}"]
````````````````````````````````


include

```````````````````````````````` example(Jekyll Tag: 10) options(dummy-identifier)
{% include ⎮ %}
.
.
Document[0, 15]
  JekyllTagBlock[15, 15]
    JekyllTag[0, 15] open:[0, 2, "{%"] tag:[3, 10, "include"] parameters:[11, 12, "%1f"] close:[13, 15, "%}"]
````````````````````````````````


include

```````````````````````````````` example(Jekyll Tag: 11) options(includes)
{% include test.html %}
.
.
Document[0, 23]
  JekyllTagBlock[23, 23]
    JekyllTag[0, 23] open:[0, 2, "{%"] tag:[3, 10, "include"] parameters:[11, 20, "test.html"] close:[21, 23, "%}"]
````````````````````````````````


```````````````````````````````` example(Jekyll Tag: 12) options(includes, embed-includes)
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

```````````````````````````````` example(Jekyll Tag: 13) options(includes)
{% include links.html %}
.
.
Document[0, 24]
  JekyllTagBlock[24, 24]
    JekyllTag[0, 24] open:[0, 2, "{%"] tag:[3, 10, "include"] parameters:[11, 21, "links.html"] close:[22, 24, "%}"]
````````````````````````````````


inline includes

```````````````````````````````` example(Jekyll Tag: 14) options(includes, embed-includes)
test {% include test.html %} text
.
<p>test 
<h1>Heading 1</h1>
<p>test text</p>
text</p>
.
Document[0, 33]
  Paragraph[0, 33]
    Text[0, 5] chars:[0, 5, "test "]
    JekyllTag[5, 28] open:[5, 7, "{%"] tag:[8, 15, "include"] parameters:[16, 25, "test.html"] close:[26, 28, "%}"]
      HtmlBlock[0, 36]
    Text[28, 33] chars:[28, 33, " text"]
````````````````````````````````


inline includes

```````````````````````````````` example(Jekyll Tag: 15) options(includes, embed-includes)
test {% include test2.md %} text
.
<p>test Included Text text</p>
.
Document[0, 32]
  Paragraph[0, 32]
    Text[0, 5] chars:[0, 5, "test "]
    JekyllTag[5, 27] open:[5, 7, "{%"] tag:[8, 15, "include"] parameters:[16, 24, "test2.md"] close:[25, 27, "%}"]
      Text[0, 13] chars:[0, 13, "Inclu …  Text"]
    Text[27, 32] chars:[27, 32, " text"]
````````````````````````````````


