---
title: YouTube Link Extension Spec
author:
version:
date: '2017-11-23'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## YouTube embedded link transformer

Transforms links to YouTube to iframe embedded video links

```````````````````````````````` example YouTube embedded link transformer: 1
[test](https://www.youtube.com/watch?v=GYem-BGEhaY)
.
<p><iframe src="https://www.youtube.com/embed/GYem-BGEhaY" width="420" height="315" class="youtube-embedded" allowfullscreen="true" frameborder="0"></iframe></p>
.
Document[0, 52]
  Paragraph[0, 52]
    Link[0, 51] textOpen:[0, 1, "["] text:[1, 5, "test"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 50, "https://www.youtube.com/watch?v=GYem-BGEhaY"] pageRef:[7, 50, "https://www.youtube.com/watch?v=GYem-BGEhaY"] linkClose:[50, 51, ")"]
      Text[1, 5] chars:[1, 5, "test"]
````````````````````````````````

