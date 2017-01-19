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

```````````````````````````````` example Jekyll Tag: 1
{% %}
.
<p>{% %}</p>
.
Document[0, 6]
  Paragraph[0, 6]
    Text[0, 5] chars:[0, 5, "{% %}"]
````````````````````````````````


some tags

```````````````````````````````` example Jekyll Tag: 2
{% tag %}
.
.
Document[0, 10]
  JekyllTagBlock[9, 10]
    JekyllTag[0, 9] open:[0, 2, "{%"] tag:[3, 6, "tag"] parameters:[7, 7] close:[7, 9, "%}"]
````````````````````````````````


some tags

```````````````````````````````` example Jekyll Tag: 3
{% tag paramters %}
.
.
Document[0, 20]
  JekyllTagBlock[19, 20]
    JekyllTag[0, 19] open:[0, 2, "{%"] tag:[3, 6, "tag"] parameters:[7, 16, "paramters"] close:[17, 19, "%}"]
````````````````````````````````


inline tags

```````````````````````````````` example Jekyll Tag: 4
 {% tag %}
.
<p></p>
.
Document[0, 11]
  Paragraph[1, 11]
    JekyllTag[1, 10] open:[1, 3, "{%"] tag:[4, 7, "tag"] parameters:[8, 8] close:[8, 10, "%}"]
````````````````````````````````


inline tags

```````````````````````````````` example Jekyll Tag: 5
 {% tag paramters %}
.
<p></p>
.
Document[0, 21]
  Paragraph[1, 21]
    JekyllTag[1, 20] open:[1, 3, "{%"] tag:[4, 7, "tag"] parameters:[8, 17, "paramters"] close:[18, 20, "%}"]
````````````````````````````````


no blocks

```````````````````````````````` example(Jekyll Tag: 6) options(no-blocks)
{% tag %}
.
.
Document[0, 9]
  JekyllTagBlock[9, 9]
    JekyllTag[0, 9] open:[0, 2, "{%"] tag:[3, 6, "tag"] parameters:[7, 7] close:[7, 9, "%}"]
````````````````````````````````


no blocks no inlines

```````````````````````````````` example(Jekyll Tag: 7) options(no-blocks, no-inlines)
{% tag %}
.
.
Document[0, 9]
  JekyllTagBlock[9, 9]
    JekyllTag[0, 9] open:[0, 2, "{%"] tag:[3, 6, "tag"] parameters:[7, 7] close:[7, 9, "%}"]
````````````````````````````````


no inlines

```````````````````````````````` example(Jekyll Tag: 8) options(no-inlines)
 {% tag %}
.
<p>{% tag %}</p>
.
Document[0, 10]
  Paragraph[1, 10]
    Text[1, 10] chars:[1, 10, "{% tag %}"]
````````````````````````````````


