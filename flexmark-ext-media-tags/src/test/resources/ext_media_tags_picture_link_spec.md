---
title: Media Tags Extension Picture Link Spec
author: Cornelia Ada Schultz
version: 1.0
date: '2018-07-02'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Media Tags picture link transformer

Transforms links prefixed with 'P' into a picture media tag. (Special thanks to
https://www.w3schools.com/tags/tryit.asp?filename=tryhtml5_picture ).

```````````````````````````````` example Media Tags picture link transformer: 1
!P[Flowers](https://www.w3schools.com/tags/img_orange_flowers.jpg)
.
<p><picture><img src="https://www.w3schools.com/tags/img_orange_flowers.jpg" alt="Flowers" /></picture></p>
.
Document[0, 66]
  Paragraph[0, 66]
    PictureLink[0, 66] textOpen:[0, 3, "!P["] text:[3, 10, "Flowers"] textClose:[10, 11, "]"] linkOpen:[11, 12, "("] url:[12, 65, "https://www.w3schools.com/tags/img_orange_flowers.jpg"] linkClose:[65, 66, ")"]
      Text[3, 10] chars:[3, 10, "Flowers"]
````````````````````````````````


Multiple sources can be specified by separating with a vertical bar '|'. Image sources cascade
together, the last source is the one directly referenced in the <img> tag.

```````````````````````````````` example Media Tags picture link transformer: 2
!P[Flowers](https://www.w3schools.com/tags/img_white_flower.jpg|https://www.w3schools.com/tags/img_orange_flowers.jpg)
.
<p><picture><source srcset="https://www.w3schools.com/tags/img_white_flower.jpg" /><img src="https://www.w3schools.com/tags/img_orange_flowers.jpg" alt="Flowers" /></picture></p>
.
Document[0, 118]
  Paragraph[0, 118]
    PictureLink[0, 118] textOpen:[0, 3, "!P["] text:[3, 10, "Flowers"] textClose:[10, 11, "]"] linkOpen:[11, 12, "("] url:[12, 117, "https://www.w3schools.com/tags/img_white_flower.jpg|https://www.w3schools.com/tags/img_orange_flowers.jpg"] linkClose:[117, 118, ")"]
      Text[3, 10] chars:[3, 10, "Flowers"]
````````````````````````````````


```````````````````````````````` example Media Tags picture link transformer: 3
!P[Flowers](https://www.w3schools.com/tags/img_pink_flowers.jpg|https://www.w3schools.com/tags/img_white_flower.jpg|https://www.w3schools.com/tags/img_orange_flowers.jpg)
.
<p><picture><source srcset="https://www.w3schools.com/tags/img_pink_flowers.jpg" /><source srcset="https://www.w3schools.com/tags/img_white_flower.jpg" /><img src="https://www.w3schools.com/tags/img_orange_flowers.jpg" alt="Flowers" /></picture></p>
.
Document[0, 170]
  Paragraph[0, 170]
    PictureLink[0, 170] textOpen:[0, 3, "!P["] text:[3, 10, "Flowers"] textClose:[10, 11, "]"] linkOpen:[11, 12, "("] url:[12, 169, "https://www.w3schools.com/tags/img_pink_flowers.jpg|https://www.w3schools.com/tags/img_white_flower.jpg|https://www.w3schools.com/tags/img_orange_flowers.jpg"] linkClose:[169, 170, ")"]
      Text[3, 10] chars:[3, 10, "Flowers"]
````````````````````````````````


Ignore escaped prefix '\P'.

```````````````````````````````` example Media Tags picture link transformer: 4
\!P[Flowers](https://www.w3schools.com/tags/img_orange_flowers.jpg)
.
<p>!P<a href="https://www.w3schools.com/tags/img_orange_flowers.jpg">Flowers</a></p>
.
Document[0, 67]
  Paragraph[0, 67]
    Text[0, 3] chars:[0, 3, "\!P"]
    Link[3, 67] textOpen:[3, 4, "["] text:[4, 11, "Flowers"] textClose:[11, 12, "]"] linkOpen:[12, 13, "("] url:[13, 66, "https://www.w3schools.com/tags/img_orange_flowers.jpg"] pageRef:[13, 66, "https://www.w3schools.com/tags/img_orange_flowers.jpg"] linkClose:[66, 67, ")"]
      Text[4, 11] chars:[4, 11, "Flowers"]
````````````````````````````````


Ignore escaped prefix '\\\P'.

```````````````````````````````` example Media Tags picture link transformer: 5
\\\!P[Flowers](https://www.w3schools.com/tags/img_orange_flowers.jpg)
.
<p>\!P<a href="https://www.w3schools.com/tags/img_orange_flowers.jpg">Flowers</a></p>
.
Document[0, 69]
  Paragraph[0, 69]
    Text[0, 5] chars:[0, 5, "\\\!P"]
    Link[5, 69] textOpen:[5, 6, "["] text:[6, 13, "Flowers"] textClose:[13, 14, "]"] linkOpen:[14, 15, "("] url:[15, 68, "https://www.w3schools.com/tags/img_orange_flowers.jpg"] pageRef:[15, 68, "https://www.w3schools.com/tags/img_orange_flowers.jpg"] linkClose:[68, 69, ")"]
      Text[6, 13] chars:[6, 13, "Flowers"]
````````````````````````````````


Don't ignore escaped prefix '\\P'.

```````````````````````````````` example Media Tags picture link transformer: 6
\\!P[Flowers](https://www.w3schools.com/tags/img_orange_flowers.jpg)
.
<p>\<picture><img src="https://www.w3schools.com/tags/img_orange_flowers.jpg" alt="Flowers" /></picture></p>
.
Document[0, 68]
  Paragraph[0, 68]
    Text[0, 2] chars:[0, 2, "\\"]
    PictureLink[2, 68] textOpen:[2, 5, "!P["] text:[5, 12, "Flowers"] textClose:[12, 13, "]"] linkOpen:[13, 14, "("] url:[14, 67, "https://www.w3schools.com/tags/img_orange_flowers.jpg"] linkClose:[67, 68, ")"]
      Text[5, 12] chars:[5, 12, "Flowers"]
````````````````````````````````

