---
title: Media Tags Extension Embed Link Spec
author: Cornelia Ada Schultz
version: 1.0
date: '2018-07-02'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Media Tags embed link transformer

Transforms links prefixed with 'E' into an embed media tag. (Special thanks to
http://condor.depaul.edu/sjost/hci430/flash-examples.htm ).

```````````````````````````````` example Media Tags embed link transformer: 1
!E[Snowy Flash Animation](http://condor.depaul.edu/sjost/hci430/flash-examples/swf/snow.swf)
.
<p><embed title="Snowy Flash Animation" src="http://condor.depaul.edu/sjost/hci430/flash-examples/swf/snow.swf" /></p>
.
Document[0, 92]
  Paragraph[0, 92]
    EmbedLink[0, 92] textOpen:[0, 3, "!E["] text:[3, 24, "Snowy Flash Animation"] textClose:[24, 25, "]"] linkOpen:[25, 26, "("] url:[26, 91, "http://condor.depaul.edu/sjost/hci430/flash-examples/swf/snow.swf"] linkClose:[91, 92, ")"]
      Text[3, 24] chars:[3, 24, "Snowy … ation"]
````````````````````````````````


Ignore escaped prefix '\E'.

```````````````````````````````` example Media Tags embed link transformer: 2
\!E[Snowy Flash Animation](http://condor.depaul.edu/sjost/hci430/flash-examples/swf/snow.swf)
.
<p>!E<a href="http://condor.depaul.edu/sjost/hci430/flash-examples/swf/snow.swf">Snowy Flash Animation</a></p>
.
Document[0, 93]
  Paragraph[0, 93]
    Text[0, 3] chars:[0, 3, "\!E"]
    Link[3, 93] textOpen:[3, 4, "["] text:[4, 25, "Snowy Flash Animation"] textClose:[25, 26, "]"] linkOpen:[26, 27, "("] url:[27, 92, "http://condor.depaul.edu/sjost/hci430/flash-examples/swf/snow.swf"] pageRef:[27, 92, "http://condor.depaul.edu/sjost/hci430/flash-examples/swf/snow.swf"] linkClose:[92, 93, ")"]
      Text[4, 25] chars:[4, 25, "Snowy … ation"]
````````````````````````````````


Ignore escaped prefix '\\\E'.

```````````````````````````````` example Media Tags embed link transformer: 3
\\\!E[Snowy Flash Animation](http://condor.depaul.edu/sjost/hci430/flash-examples/swf/snow.swf)
.
<p>\!E<a href="http://condor.depaul.edu/sjost/hci430/flash-examples/swf/snow.swf">Snowy Flash Animation</a></p>
.
Document[0, 95]
  Paragraph[0, 95]
    Text[0, 5] chars:[0, 5, "\\\!E"]
    Link[5, 95] textOpen:[5, 6, "["] text:[6, 27, "Snowy Flash Animation"] textClose:[27, 28, "]"] linkOpen:[28, 29, "("] url:[29, 94, "http://condor.depaul.edu/sjost/hci430/flash-examples/swf/snow.swf"] pageRef:[29, 94, "http://condor.depaul.edu/sjost/hci430/flash-examples/swf/snow.swf"] linkClose:[94, 95, ")"]
      Text[6, 27] chars:[6, 27, "Snowy … ation"]
````````````````````````````````


Don't ignore escaped prefix '\\E'.

```````````````````````````````` example Media Tags embed link transformer: 4
\\!E[Snowy Flash Animation](http://condor.depaul.edu/sjost/hci430/flash-examples/swf/snow.swf)
.
<p>\<embed title="Snowy Flash Animation" src="http://condor.depaul.edu/sjost/hci430/flash-examples/swf/snow.swf" /></p>
.
Document[0, 94]
  Paragraph[0, 94]
    Text[0, 2] chars:[0, 2, "\\"]
    EmbedLink[2, 94] textOpen:[2, 5, "!E["] text:[5, 26, "Snowy Flash Animation"] textClose:[26, 27, "]"] linkOpen:[27, 28, "("] url:[28, 93, "http://condor.depaul.edu/sjost/hci430/flash-examples/swf/snow.swf"] linkClose:[93, 94, ")"]
      Text[5, 26] chars:[5, 26, "Snowy … ation"]
````````````````````````````````

