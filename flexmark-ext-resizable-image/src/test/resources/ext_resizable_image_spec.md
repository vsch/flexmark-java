---
title: Resizable Image Extension Spec
author:
version:
date: '2022-01-19'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Image size transformer

### Image 30x30

```````````````````````````````` example Image size transformer - Image 30x30: 1
![Image_30x30](./test-image.jpg =30x30)
.
<p><img src="./test-image.jpg" alt="Image_30x30" width="30px" height="30px"></img></p>
.
Document[0, 39]
  Paragraph[0, 39]
    ResizableImage[2, 38]
````````````````````````````````


### Image 512x512

```````````````````````````````` example Image size transformer - Image 512x512: 1
![Image_512x512](./test-image.jpg =512x512)
.
<p><img src="./test-image.jpg" alt="Image_512x512" width="512px" height="512px"></img></p>
.
Document[0, 43]
  Paragraph[0, 43]
    ResizableImage[2, 42]
````````````````````````````````


### Image full

```````````````````````````````` example Image size transformer - Image full: 1
![Image_full](./test-image.jpg)
.
<p><img src="./test-image.jpg" alt="Image_full"></img></p>
.
Document[0, 31]
  Paragraph[0, 31]
    ResizableImage[2, 30]
````````````````````````````````


### Embedded in links

```````````````````````````````` example Image size transformer - Embedded in links: 1
[![Image_full](./test-image.jpg =512x512)](bar)
.
<p><a href="bar"><img src="./test-image.jpg" alt="Image_full" width="512px" height="512px"></img></a></p>
.
Document[0, 47]
  Paragraph[0, 47]
    Link[0, 47] textOpen:[0, 1, "["] text:[1, 41, "![Image_full](./test-image.jpg =512x512)"] textClose:[41, 42, "]"] linkOpen:[42, 43, "("] url:[43, 46, "bar"] pageRef:[43, 46, "bar"] linkClose:[46, 47, ")"]
      ResizableImage[3, 40]
````````````````````````````````


```````````````````````````````` example Image size transformer - Embedded in links: 2
[![Image_full](./test-image.jpg)](bar)
.
<p><a href="bar"><img src="./test-image.jpg" alt="Image_full"></img></a></p>
.
Document[0, 38]
  Paragraph[0, 38]
    Link[0, 38] textOpen:[0, 1, "["] text:[1, 32, "![Image_full](./test-image.jpg)"] textClose:[32, 33, "]"] linkOpen:[33, 34, "("] url:[34, 37, "bar"] pageRef:[34, 37, "bar"] linkClose:[37, 38, ")"]
      ResizableImage[3, 31]
````````````````````````````````


