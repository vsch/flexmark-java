---
title: Media Tags Extension Video Link Spec
author: Cornelia Ada Schultz
version: 1.0
date: '2018-07-02'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Media Tags video link transformer

Transforms links prefixed with 'V' into a video media tag. (Special thanks to
http://techslides.com/sample-webm-ogg-and-mp4-video-files-for-html5 ).

Media type is guessed based on extension.

```````````````````````````````` example Media Tags video link transformer: 1
!V[Sample Video](http://techslides.com/demos/sample-videos/small.mp4)
.
<p><video title="Sample Video" controls=""><source src="http://techslides.com/demos/sample-videos/small.mp4" type="video/mp4" />Your browser does not support the video element.</video></p>
.
Document[0, 69]
  Paragraph[0, 69]
    VideoLink[0, 69] textOpen:[0, 3, "!V["] text:[3, 15, "Sample Video"] textClose:[15, 16, "]"] linkOpen:[16, 17, "("] url:[17, 68, "http://techslides.com/demos/sample-videos/small.mp4"] linkClose:[68, 69, ")"]
      Text[3, 15] chars:[3, 15, "Sampl … Video"]
````````````````````````````````


```````````````````````````````` example Media Tags video link transformer: 2
!V[Sample Video](http://techslides.com/demos/sample-videos/small.webm)
.
<p><video title="Sample Video" controls=""><source src="http://techslides.com/demos/sample-videos/small.webm" type="video/webm" />Your browser does not support the video element.</video></p>
.
Document[0, 70]
  Paragraph[0, 70]
    VideoLink[0, 70] textOpen:[0, 3, "!V["] text:[3, 15, "Sample Video"] textClose:[15, 16, "]"] linkOpen:[16, 17, "("] url:[17, 69, "http://techslides.com/demos/sample-videos/small.webm"] linkClose:[69, 70, ")"]
      Text[3, 15] chars:[3, 15, "Sampl … Video"]
````````````````````````````````


```````````````````````````````` example Media Tags video link transformer: 3
!V[Sample Video](http://techslides.com/demos/sample-videos/small.ogv)
.
<p><video title="Sample Video" controls=""><source src="http://techslides.com/demos/sample-videos/small.ogv" type="video/ogg" />Your browser does not support the video element.</video></p>
.
Document[0, 69]
  Paragraph[0, 69]
    VideoLink[0, 69] textOpen:[0, 3, "!V["] text:[3, 15, "Sample Video"] textClose:[15, 16, "]"] linkOpen:[16, 17, "("] url:[17, 68, "http://techslides.com/demos/sample-videos/small.ogv"] linkClose:[68, 69, ")"]
      Text[3, 15] chars:[3, 15, "Sampl … Video"]
````````````````````````````````


```````````````````````````````` example Media Tags video link transformer: 4
!V[Sample Video](http://techslides.com/demos/sample-videos/small.3gp)
.
<p><video title="Sample Video" controls=""><source src="http://techslides.com/demos/sample-videos/small.3gp" type="video/3gp" />Your browser does not support the video element.</video></p>
.
Document[0, 69]
  Paragraph[0, 69]
    VideoLink[0, 69] textOpen:[0, 3, "!V["] text:[3, 15, "Sample Video"] textClose:[15, 16, "]"] linkOpen:[16, 17, "("] url:[17, 68, "http://techslides.com/demos/sample-videos/small.3gp"] linkClose:[68, 69, ")"]
      Text[3, 15] chars:[3, 15, "Sampl … Video"]
````````````````````````````````


Unknown media types results in no type data for source.

```````````````````````````````` example Media Tags video link transformer: 5
!V[Sample Video](http://techslides.com/demos/sample-videos/small.flv)
.
<p><video title="Sample Video" controls=""><source src="http://techslides.com/demos/sample-videos/small.flv" />Your browser does not support the video element.</video></p>
.
Document[0, 69]
  Paragraph[0, 69]
    VideoLink[0, 69] textOpen:[0, 3, "!V["] text:[3, 15, "Sample Video"] textClose:[15, 16, "]"] linkOpen:[16, 17, "("] url:[17, 68, "http://techslides.com/demos/sample-videos/small.flv"] linkClose:[68, 69, ")"]
      Text[3, 15] chars:[3, 15, "Sampl … Video"]
````````````````````````````````


Multiple sources can be specified by separating with a vertical bar '|'.

```````````````````````````````` example Media Tags video link transformer: 6
!V[Sample Video](http://techslides.com/demos/sample-videos/small.mp4|http://techslides.com/demos/sample-videos/small.webm)
.
<p><video title="Sample Video" controls=""><source src="http://techslides.com/demos/sample-videos/small.mp4" type="video/mp4" /><source src="http://techslides.com/demos/sample-videos/small.webm" type="video/webm" />Your browser does not support the video element.</video></p>
.
Document[0, 122]
  Paragraph[0, 122]
    VideoLink[0, 122] textOpen:[0, 3, "!V["] text:[3, 15, "Sample Video"] textClose:[15, 16, "]"] linkOpen:[16, 17, "("] url:[17, 121, "http://techslides.com/demos/sample-videos/small.mp4|http://techslides.com/demos/sample-videos/small.webm"] linkClose:[121, 122, ")"]
      Text[3, 15] chars:[3, 15, "Sampl … Video"]
````````````````````````````````


Ignore escaped prefix '\V'.

```````````````````````````````` example Media Tags video link transformer: 7
\!V[Sample Video](http://techslides.com/demos/sample-videos/small.mp4)
.
<p>!V<a href="http://techslides.com/demos/sample-videos/small.mp4">Sample Video</a></p>
.
Document[0, 70]
  Paragraph[0, 70]
    Text[0, 3] chars:[0, 3, "\!V"]
    Link[3, 70] textOpen:[3, 4, "["] text:[4, 16, "Sample Video"] textClose:[16, 17, "]"] linkOpen:[17, 18, "("] url:[18, 69, "http://techslides.com/demos/sample-videos/small.mp4"] pageRef:[18, 69, "http://techslides.com/demos/sample-videos/small.mp4"] linkClose:[69, 70, ")"]
      Text[4, 16] chars:[4, 16, "Sampl … Video"]
````````````````````````````````


Ignore escaped prefix '\\\V'.

```````````````````````````````` example Media Tags video link transformer: 8
\\\!V[Sample Video](http://techslides.com/demos/sample-videos/small.mp4)
.
<p>\!V<a href="http://techslides.com/demos/sample-videos/small.mp4">Sample Video</a></p>
.
Document[0, 72]
  Paragraph[0, 72]
    Text[0, 5] chars:[0, 5, "\\\!V"]
    Link[5, 72] textOpen:[5, 6, "["] text:[6, 18, "Sample Video"] textClose:[18, 19, "]"] linkOpen:[19, 20, "("] url:[20, 71, "http://techslides.com/demos/sample-videos/small.mp4"] pageRef:[20, 71, "http://techslides.com/demos/sample-videos/small.mp4"] linkClose:[71, 72, ")"]
      Text[6, 18] chars:[6, 18, "Sampl … Video"]
````````````````````````````````


Don't ignore escaped prefix '\\V'.

```````````````````````````````` example Media Tags video link transformer: 9
\\!V[Sample Video](http://techslides.com/demos/sample-videos/small.mp4)
.
<p>\<video title="Sample Video" controls=""><source src="http://techslides.com/demos/sample-videos/small.mp4" type="video/mp4" />Your browser does not support the video element.</video></p>
.
Document[0, 71]
  Paragraph[0, 71]
    Text[0, 2] chars:[0, 2, "\\"]
    VideoLink[2, 71] textOpen:[2, 5, "!V["] text:[5, 17, "Sample Video"] textClose:[17, 18, "]"] linkOpen:[18, 19, "("] url:[19, 70, "http://techslides.com/demos/sample-videos/small.mp4"] linkClose:[70, 71, ")"]
      Text[5, 17] chars:[5, 17, "Sampl … Video"]
````````````````````````````````

