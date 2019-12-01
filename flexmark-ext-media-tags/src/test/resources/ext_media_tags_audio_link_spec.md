---
title: Media Tags Extension Audio Link Spec
author: Cornelia Ada Schultz
version: 1.0
date: '2018-07-02'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Media Tags audio link transformer

Transforms links prefixed with 'A' into an audio media tag. (Special thanks to
https://hpr.dogphilosophy.net/test/index.php ).

Media type is guessed based on extension.

```````````````````````````````` example Media Tags audio link transformer: 1
!A[Audio Test](https://hpr.dogphilosophy.net/test/opus.opus)
.
<p><audio title="Audio Test" controls=""><source src="https://hpr.dogphilosophy.net/test/opus.opus" type="audio/ogg; codecs=opus" />Your browser does not support the audio element.</audio></p>
.
Document[0, 60]
  Paragraph[0, 60]
    AudioLink[0, 60] textOpen:[0, 3, "!A["] text:[3, 13, "Audio Test"] textClose:[13, 14, "]"] linkOpen:[14, 15, "("] url:[15, 59, "https://hpr.dogphilosophy.net/test/opus.opus"] linkClose:[59, 60, ")"]
      Text[3, 13] chars:[3, 13, "Audio Test"]
````````````````````````````````


```````````````````````````````` example Media Tags audio link transformer: 2
!A[Audio Test](https://hpr.dogphilosophy.net/test/weba.weba)
.
<p><audio title="Audio Test" controls=""><source src="https://hpr.dogphilosophy.net/test/weba.weba" type="audio/webm" />Your browser does not support the audio element.</audio></p>
.
Document[0, 60]
  Paragraph[0, 60]
    AudioLink[0, 60] textOpen:[0, 3, "!A["] text:[3, 13, "Audio Test"] textClose:[13, 14, "]"] linkOpen:[14, 15, "("] url:[15, 59, "https://hpr.dogphilosophy.net/test/weba.weba"] linkClose:[59, 60, ")"]
      Text[3, 13] chars:[3, 13, "Audio Test"]
````````````````````````````````


```````````````````````````````` example Media Tags audio link transformer: 3
!A[Audio Test](https://hpr.dogphilosophy.net/test/webmv2.webm)
.
<p><audio title="Audio Test" controls=""><source src="https://hpr.dogphilosophy.net/test/webmv2.webm" type="audio/webm; codecs=opus" />Your browser does not support the audio element.</audio></p>
.
Document[0, 62]
  Paragraph[0, 62]
    AudioLink[0, 62] textOpen:[0, 3, "!A["] text:[3, 13, "Audio Test"] textClose:[13, 14, "]"] linkOpen:[14, 15, "("] url:[15, 61, "https://hpr.dogphilosophy.net/test/webmv2.webm"] linkClose:[61, 62, ")"]
      Text[3, 13] chars:[3, 13, "Audio Test"]
````````````````````````````````


```````````````````````````````` example Media Tags audio link transformer: 4
!A[Audio Test](https://hpr.dogphilosophy.net/test/ogg.ogg)
.
<p><audio title="Audio Test" controls=""><source src="https://hpr.dogphilosophy.net/test/ogg.ogg" type="audio/ogg" />Your browser does not support the audio element.</audio></p>
.
Document[0, 58]
  Paragraph[0, 58]
    AudioLink[0, 58] textOpen:[0, 3, "!A["] text:[3, 13, "Audio Test"] textClose:[13, 14, "]"] linkOpen:[14, 15, "("] url:[15, 57, "https://hpr.dogphilosophy.net/test/ogg.ogg"] linkClose:[57, 58, ")"]
      Text[3, 13] chars:[3, 13, "Audio Test"]
````````````````````````````````


```````````````````````````````` example Media Tags audio link transformer: 5
!A[Audio Test](https://hpr.dogphilosophy.net/test/mp3.mp3)
.
<p><audio title="Audio Test" controls=""><source src="https://hpr.dogphilosophy.net/test/mp3.mp3" type="audio/mpeg" />Your browser does not support the audio element.</audio></p>
.
Document[0, 58]
  Paragraph[0, 58]
    AudioLink[0, 58] textOpen:[0, 3, "!A["] text:[3, 13, "Audio Test"] textClose:[13, 14, "]"] linkOpen:[14, 15, "("] url:[15, 57, "https://hpr.dogphilosophy.net/test/mp3.mp3"] linkClose:[57, 58, ")"]
      Text[3, 13] chars:[3, 13, "Audio Test"]
````````````````````````````````


```````````````````````````````` example Media Tags audio link transformer: 6
!A[Audio Test](https://hpr.dogphilosophy.net/test/wav.wav)
.
<p><audio title="Audio Test" controls=""><source src="https://hpr.dogphilosophy.net/test/wav.wav" type="audio/wav" />Your browser does not support the audio element.</audio></p>
.
Document[0, 58]
  Paragraph[0, 58]
    AudioLink[0, 58] textOpen:[0, 3, "!A["] text:[3, 13, "Audio Test"] textClose:[13, 14, "]"] linkOpen:[14, 15, "("] url:[15, 57, "https://hpr.dogphilosophy.net/test/wav.wav"] linkClose:[57, 58, ")"]
      Text[3, 13] chars:[3, 13, "Audio Test"]
````````````````````````````````


```````````````````````````````` example Media Tags audio link transformer: 7
!A[Audio Test](https://hpr.dogphilosophy.net/test/flac.flac)
.
<p><audio title="Audio Test" controls=""><source src="https://hpr.dogphilosophy.net/test/flac.flac" type="audio/flac" />Your browser does not support the audio element.</audio></p>
.
Document[0, 60]
  Paragraph[0, 60]
    AudioLink[0, 60] textOpen:[0, 3, "!A["] text:[3, 13, "Audio Test"] textClose:[13, 14, "]"] linkOpen:[14, 15, "("] url:[15, 59, "https://hpr.dogphilosophy.net/test/flac.flac"] linkClose:[59, 60, ")"]
      Text[3, 13] chars:[3, 13, "Audio Test"]
````````````````````````````````


Unknown media types result in no type data for source. This audio file does not actually exist.
(Will return 404 if you build this page and try to navigate to it / load the audio.)

```````````````````````````````` example Media Tags audio link transformer: 8
!A[Audio Test](my-fake-audio-type.custom)
.
<p><audio title="Audio Test" controls=""><source src="my-fake-audio-type.custom" />Your browser does not support the audio element.</audio></p>
.
Document[0, 41]
  Paragraph[0, 41]
    AudioLink[0, 41] textOpen:[0, 3, "!A["] text:[3, 13, "Audio Test"] textClose:[13, 14, "]"] linkOpen:[14, 15, "("] url:[15, 40, "my-fake-audio-type.custom"] linkClose:[40, 41, ")"]
      Text[3, 13] chars:[3, 13, "Audio Test"]
````````````````````````````````


Multiple sources can be specified by separating with a vertical bar '|'.

```````````````````````````````` example Media Tags audio link transformer: 9
!A[Audio Test](https://hpr.dogphilosophy.net/test/opus.opus|https://hpr.dogphilosophy.net/test/weba.weba|https://hpr.dogphilosophy.net/test/webmv2.webm|https://hpr.dogphilosophy.net/test/ogg.ogg|https://hpr.dogphilosophy.net/test/mp3.mp3|https://hpr.dogphilosophy.net/test/wav.wav|https://hpr.dogphilosophy.net/test/flac.flac)
.
<p><audio title="Audio Test" controls=""><source src="https://hpr.dogphilosophy.net/test/opus.opus" type="audio/ogg; codecs=opus" /><source src="https://hpr.dogphilosophy.net/test/weba.weba" type="audio/webm" /><source src="https://hpr.dogphilosophy.net/test/webmv2.webm" type="audio/webm; codecs=opus" /><source src="https://hpr.dogphilosophy.net/test/ogg.ogg" type="audio/ogg" /><source src="https://hpr.dogphilosophy.net/test/mp3.mp3" type="audio/mpeg" /><source src="https://hpr.dogphilosophy.net/test/wav.wav" type="audio/wav" /><source src="https://hpr.dogphilosophy.net/test/flac.flac" type="audio/flac" />Your browser does not support the audio element.</audio></p>
.
Document[0, 326]
  Paragraph[0, 326]
    AudioLink[0, 326] textOpen:[0, 3, "!A["] text:[3, 13, "Audio Test"] textClose:[13, 14, "]"] linkOpen:[14, 15, "("] url:[15, 325, "https://hpr.dogphilosophy.net/test/opus.opus|https://hpr.dogphilosophy.net/test/weba.weba|https://hpr.dogphilosophy.net/test/webmv2.webm|https://hpr.dogphilosophy.net/test/ogg.ogg|https://hpr.dogphilosophy.net/test/mp3.mp3|https://hpr.dogphilosophy.net/test/wav.wav|https://hpr.dogphilosophy.net/test/flac.flac"] linkClose:[325, 326, ")"]
      Text[3, 13] chars:[3, 13, "Audio Test"]
````````````````````````````````


Ignore escaped prefix '\A'.

```````````````````````````````` example Media Tags audio link transformer: 10
\!A[Audio Test](https://hpr.dogphilosophy.net/test/opus.opus)
.
<p>!A<a href="https://hpr.dogphilosophy.net/test/opus.opus">Audio Test</a></p>
.
Document[0, 61]
  Paragraph[0, 61]
    Text[0, 3] chars:[0, 3, "\!A"]
    Link[3, 61] textOpen:[3, 4, "["] text:[4, 14, "Audio Test"] textClose:[14, 15, "]"] linkOpen:[15, 16, "("] url:[16, 60, "https://hpr.dogphilosophy.net/test/opus.opus"] pageRef:[16, 60, "https://hpr.dogphilosophy.net/test/opus.opus"] linkClose:[60, 61, ")"]
      Text[4, 14] chars:[4, 14, "Audio Test"]
````````````````````````````````


Ignore escaped prefix '\\\A'.

```````````````````````````````` example Media Tags audio link transformer: 11
\\\!A[Audio Test](https://hpr.dogphilosophy.net/test/opus.opus)
.
<p>\!A<a href="https://hpr.dogphilosophy.net/test/opus.opus">Audio Test</a></p>
.
Document[0, 63]
  Paragraph[0, 63]
    Text[0, 5] chars:[0, 5, "\\\!A"]
    Link[5, 63] textOpen:[5, 6, "["] text:[6, 16, "Audio Test"] textClose:[16, 17, "]"] linkOpen:[17, 18, "("] url:[18, 62, "https://hpr.dogphilosophy.net/test/opus.opus"] pageRef:[18, 62, "https://hpr.dogphilosophy.net/test/opus.opus"] linkClose:[62, 63, ")"]
      Text[6, 16] chars:[6, 16, "Audio Test"]
````````````````````````````````


Don't ignore escaped prefix '\\A'.

```````````````````````````````` example Media Tags audio link transformer: 12
\\!A[Audio Test](https://hpr.dogphilosophy.net/test/opus.opus)
.
<p>\<audio title="Audio Test" controls=""><source src="https://hpr.dogphilosophy.net/test/opus.opus" type="audio/ogg; codecs=opus" />Your browser does not support the audio element.</audio></p>
.
Document[0, 62]
  Paragraph[0, 62]
    Text[0, 2] chars:[0, 2, "\\"]
    AudioLink[2, 62] textOpen:[2, 5, "!A["] text:[5, 15, "Audio Test"] textClose:[15, 16, "]"] linkOpen:[16, 17, "("] url:[17, 61, "https://hpr.dogphilosophy.net/test/opus.opus"] linkClose:[61, 62, ")"]
      Text[5, 15] chars:[5, 15, "Audio Test"]
````````````````````````````````

