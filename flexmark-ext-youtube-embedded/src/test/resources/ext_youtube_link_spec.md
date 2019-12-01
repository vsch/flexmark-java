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

```````````````````````````````` example YouTube embedded link transformer: 1
[test](https://www.youtube.com/watch?v=GYem-BGEhaY)
.
<p><a href="https://www.youtube.com/watch?v=GYem-BGEhaY">test</a></p>
.
Document[0, 51]
  Paragraph[0, 51]
    Link[0, 51] textOpen:[0, 1, "["] text:[1, 5, "test"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 50, "https://www.youtube.com/watch?v=GYem-BGEhaY"] pageRef:[7, 50, "https://www.youtube.com/watch?v=GYem-BGEhaY"] linkClose:[50, 51, ")"]
      Text[1, 5] chars:[1, 5, "test"]
````````````````````````````````


```````````````````````````````` example YouTube embedded link transformer: 2
@[test](https://www.youtube.com/watch?v=GYem-BGEhaY)
.
<p><iframe src="https://www.youtube.com/embed/GYem-BGEhaY" width="420" height="315" class="youtube-embedded" allowfullscreen="true" frameborder="0"></iframe></p>
.
Document[0, 52]
  Paragraph[0, 52]
    Text[0, 0]
    YouTubeLink[0, 52] textOpen:[0, 2, "@["] text:[2, 6, "test"] textClose:[6, 7, "]"] linkOpen:[7, 8, "("] url:[8, 51, "https://www.youtube.com/watch?v=GYem-BGEhaY"] linkClose:[51, 52, ")"]
      Text[2, 6] chars:[2, 6, "test"]
````````````````````````````````


Ignore escaped `@`

```````````````````````````````` example YouTube embedded link transformer: 3
\@[test](https://www.youtube.com/watch?v=GYem-BGEhaY)
.
<p>@<a href="https://www.youtube.com/watch?v=GYem-BGEhaY">test</a></p>
.
Document[0, 53]
  Paragraph[0, 53]
    Text[0, 2] chars:[0, 2, "\@"]
    Link[2, 53] textOpen:[2, 3, "["] text:[3, 7, "test"] textClose:[7, 8, "]"] linkOpen:[8, 9, "("] url:[9, 52, "https://www.youtube.com/watch?v=GYem-BGEhaY"] pageRef:[9, 52, "https://www.youtube.com/watch?v=GYem-BGEhaY"] linkClose:[52, 53, ")"]
      Text[3, 7] chars:[3, 7, "test"]
````````````````````````````````


Ignore escaped `@`

```````````````````````````````` example YouTube embedded link transformer: 4
\\\@[test](https://www.youtube.com/watch?v=GYem-BGEhaY)
.
<p>\@<a href="https://www.youtube.com/watch?v=GYem-BGEhaY">test</a></p>
.
Document[0, 55]
  Paragraph[0, 55]
    Text[0, 4] chars:[0, 4, "\\\@"]
    Link[4, 55] textOpen:[4, 5, "["] text:[5, 9, "test"] textClose:[9, 10, "]"] linkOpen:[10, 11, "("] url:[11, 54, "https://www.youtube.com/watch?v=GYem-BGEhaY"] pageRef:[11, 54, "https://www.youtube.com/watch?v=GYem-BGEhaY"] linkClose:[54, 55, ")"]
      Text[5, 9] chars:[5, 9, "test"]
````````````````````````````````


Don't ignore escaped `\\\\`

```````````````````````````````` example YouTube embedded link transformer: 5
\\@[test](https://www.youtube.com/watch?v=GYem-BGEhaY)
.
<p>\<iframe src="https://www.youtube.com/embed/GYem-BGEhaY" width="420" height="315" class="youtube-embedded" allowfullscreen="true" frameborder="0"></iframe></p>
.
Document[0, 54]
  Paragraph[0, 54]
    Text[0, 2] chars:[0, 2, "\\"]
    YouTubeLink[2, 54] textOpen:[2, 4, "@["] text:[4, 8, "test"] textClose:[8, 9, "]"] linkOpen:[9, 10, "("] url:[10, 53, "https://www.youtube.com/watch?v=GYem-BGEhaY"] linkClose:[53, 54, ")"]
      Text[4, 8] chars:[4, 8, "test"]
````````````````````````````````


New form, without start time

```````````````````````````````` example YouTube embedded link transformer: 6
@[test](https://youtu.be/BaA0hZ406YY)
.
<p><iframe src="https://www.youtube-nocookie.com/embed/BaA0hZ406YY" width="560" height="315" class="youtube-embedded" allowfullscreen="true" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"></iframe></p>
.
Document[0, 37]
  Paragraph[0, 37]
    Text[0, 0]
    YouTubeLink[0, 37] textOpen:[0, 2, "@["] text:[2, 6, "test"] textClose:[6, 7, "]"] linkOpen:[7, 8, "("] url:[8, 36, "https://youtu.be/BaA0hZ406YY"] linkClose:[36, 37, ")"]
      Text[2, 6] chars:[2, 6, "test"]
````````````````````````````````


New form, with start time

```````````````````````````````` example YouTube embedded link transformer: 7
@[test](https://youtu.be/BaA0hZ406YY?t=123)
.
<p><iframe src="https://www.youtube-nocookie.com/embed/BaA0hZ406YY?start=123" width="560" height="315" class="youtube-embedded" allowfullscreen="true" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"></iframe></p>
.
Document[0, 43]
  Paragraph[0, 43]
    Text[0, 0]
    YouTubeLink[0, 43] textOpen:[0, 2, "@["] text:[2, 6, "test"] textClose:[6, 7, "]"] linkOpen:[7, 8, "("] url:[8, 42, "https://youtu.be/BaA0hZ406YY?t=123"] linkClose:[42, 43, ")"]
      Text[2, 6] chars:[2, 6, "test"]
````````````````````````````````


