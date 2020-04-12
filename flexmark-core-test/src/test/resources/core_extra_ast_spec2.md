---
title: Extra Core Test Spec
author: Vladimir Schneider
version: 0.2
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

# Extra tests

## Flexmark Spec

## Heading ID

### Default

Heading ID option

```````````````````````````````` example(Heading ID - Default: 1) options(heading-ids)
## 2.0. References [ There is a {} reference showing but is Нет copy-able ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a--reference-showing-but-is-нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 160]
  Heading[0, 75] textOpen:[0, 2, "##"] text:[3, 75, "2.0. References [ There is a {} reference showing but is Нет copy-able ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 75] referenceOpen:[19, 20, "["] reference:[21, 73, "There is a {} reference showing but is Нет copy-able"] referenceClose:[74, 75, "]"]
      Text[21, 73] chars:[21, 73, "There … -able"]
  Reference[77, 158] refOpen:[77, 78, "["] ref:[78, 130, "There is a {} reference showing but is Нет copy-able"] refClose:[130, 132, "]:"] url:[133, 158, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - Default: 2) options(heading-ids)
## 2.0. References [  There is a {} reference showing but is Нет copy-able  ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a--reference-showing-but-is-нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 162]
  Heading[0, 77] textOpen:[0, 2, "##"] text:[3, 77, "2.0. References [  There is a {} reference showing but is Нет copy-able  ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 77] referenceOpen:[19, 20, "["] reference:[22, 74, "There is a {} reference showing but is Нет copy-able"] referenceClose:[76, 77, "]"]
      Text[22, 74] chars:[22, 74, "There … -able"]
  Reference[79, 160] refOpen:[79, 80, "["] ref:[80, 132, "There is a {} reference showing but is Нет copy-able"] refClose:[132, 134, "]:"] url:[135, 160, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - Default: 3) options(heading-ids)
## 2.0. References [ There is a {} reference showing but is Нет copy-able ][ There is a {} reference showing but is Нет copy-able ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a--reference-showing-but-is-нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 216]
  Heading[0, 131] textOpen:[0, 2, "##"] text:[3, 131, "2.0. References [ There is a {} reference showing but is Нет copy-able ][ There is a {} reference showing but is Нет copy-able ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 131] textOpen:[19, 20, "["] text:[21, 73, "There is a {} reference showing but is Нет copy-able"] textClose:[74, 75, "]"] referenceOpen:[75, 76, "["] reference:[77, 129, "There is a {} reference showing but is Нет copy-able"] referenceClose:[130, 131, "]"]
      Text[21, 73] chars:[21, 73, "There … -able"]
  Reference[133, 214] refOpen:[133, 134, "["] ref:[134, 186, "There is a {} reference showing but is Нет copy-able"] refClose:[186, 188, "]:"] url:[189, 214, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - Default: 4) options(heading-ids)
## 2.0. References [  There is a {} reference showing but is Нет copy-able  ][  There is a {} reference showing but is Нет copy-able  ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a--reference-showing-but-is-нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 220]
  Heading[0, 135] textOpen:[0, 2, "##"] text:[3, 135, "2.0. References [  There is a {} reference showing but is Нет copy-able  ][  There is a {} reference showing but is Нет copy-able  ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 135] textOpen:[19, 20, "["] text:[22, 74, "There is a {} reference showing but is Нет copy-able"] textClose:[76, 77, "]"] referenceOpen:[77, 78, "["] reference:[80, 132, "There is a {} reference showing but is Нет copy-able"] referenceClose:[134, 135, "]"]
      Text[22, 74] chars:[22, 74, "There … -able"]
  Reference[137, 218] refOpen:[137, 138, "["] ref:[138, 190, "There is a {} reference showing but is Нет copy-able"] refClose:[190, 192, "]:"] url:[193, 218, "https://www.example.com/0"]
````````````````````````````````


### No Duped Dashes

Heading ID option

```````````````````````````````` example(Heading ID - No Duped Dashes: 1) options(heading-ids, heading-ids-no-dupe-dashes, heading-ids-no-dupe-dashes)
## 2.0. References [ There is a {} reference showing but is Нет copy-able ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a-reference-showing-but-is-нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 160]
  Heading[0, 75] textOpen:[0, 2, "##"] text:[3, 75, "2.0. References [ There is a {} reference showing but is Нет copy-able ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 75] referenceOpen:[19, 20, "["] reference:[21, 73, "There is a {} reference showing but is Нет copy-able"] referenceClose:[74, 75, "]"]
      Text[21, 73] chars:[21, 73, "There … -able"]
  Reference[77, 158] refOpen:[77, 78, "["] ref:[78, 130, "There is a {} reference showing but is Нет copy-able"] refClose:[130, 132, "]:"] url:[133, 158, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - No Duped Dashes: 2) options(heading-ids, heading-ids-no-dupe-dashes)
## 2.0. References [  There is a {} reference showing but is Нет copy-able  ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a-reference-showing-but-is-нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 162]
  Heading[0, 77] textOpen:[0, 2, "##"] text:[3, 77, "2.0. References [  There is a {} reference showing but is Нет copy-able  ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 77] referenceOpen:[19, 20, "["] reference:[22, 74, "There is a {} reference showing but is Нет copy-able"] referenceClose:[76, 77, "]"]
      Text[22, 74] chars:[22, 74, "There … -able"]
  Reference[79, 160] refOpen:[79, 80, "["] ref:[80, 132, "There is a {} reference showing but is Нет copy-able"] refClose:[132, 134, "]:"] url:[135, 160, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - No Duped Dashes: 3) options(heading-ids, heading-ids-no-dupe-dashes)
## 2.0. References [ There is a {} reference showing but is Нет copy-able ][ There is a {} reference showing but is Нет copy-able ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a-reference-showing-but-is-нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 216]
  Heading[0, 131] textOpen:[0, 2, "##"] text:[3, 131, "2.0. References [ There is a {} reference showing but is Нет copy-able ][ There is a {} reference showing but is Нет copy-able ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 131] textOpen:[19, 20, "["] text:[21, 73, "There is a {} reference showing but is Нет copy-able"] textClose:[74, 75, "]"] referenceOpen:[75, 76, "["] reference:[77, 129, "There is a {} reference showing but is Нет copy-able"] referenceClose:[130, 131, "]"]
      Text[21, 73] chars:[21, 73, "There … -able"]
  Reference[133, 214] refOpen:[133, 134, "["] ref:[134, 186, "There is a {} reference showing but is Нет copy-able"] refClose:[186, 188, "]:"] url:[189, 214, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - No Duped Dashes: 4) options(heading-ids, heading-ids-no-dupe-dashes)
## 2.0. References [  There is a {} reference showing but is Нет copy-able  ][  There is a {} reference showing but is Нет copy-able  ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a-reference-showing-but-is-нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 220]
  Heading[0, 135] textOpen:[0, 2, "##"] text:[3, 135, "2.0. References [  There is a {} reference showing but is Нет copy-able  ][  There is a {} reference showing but is Нет copy-able  ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 135] textOpen:[19, 20, "["] text:[22, 74, "There is a {} reference showing but is Нет copy-able"] textClose:[76, 77, "]"] referenceOpen:[77, 78, "["] reference:[80, 132, "There is a {} reference showing but is Нет copy-able"] referenceClose:[134, 135, "]"]
      Text[22, 74] chars:[22, 74, "There … -able"]
  Reference[137, 218] refOpen:[137, 138, "["] ref:[138, 190, "There is a {} reference showing but is Нет copy-able"] refClose:[190, 192, "]:"] url:[193, 218, "https://www.example.com/0"]
````````````````````````````````


### No Lowercase

Heading ID option

```````````````````````````````` example(Heading ID - No Lowercase: 1) options(heading-ids, heading-ids-no-non-ascii-lowercase)
## 2.0. References [ There is a {} reference showing but is Нет copy-able ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a--reference-showing-but-is-Нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 160]
  Heading[0, 75] textOpen:[0, 2, "##"] text:[3, 75, "2.0. References [ There is a {} reference showing but is Нет copy-able ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 75] referenceOpen:[19, 20, "["] reference:[21, 73, "There is a {} reference showing but is Нет copy-able"] referenceClose:[74, 75, "]"]
      Text[21, 73] chars:[21, 73, "There … -able"]
  Reference[77, 158] refOpen:[77, 78, "["] ref:[78, 130, "There is a {} reference showing but is Нет copy-able"] refClose:[130, 132, "]:"] url:[133, 158, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - No Lowercase: 2) options(heading-ids, heading-ids-no-non-ascii-lowercase)
## 2.0. References [  There is a {} reference showing but is Нет copy-able  ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a--reference-showing-but-is-Нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 162]
  Heading[0, 77] textOpen:[0, 2, "##"] text:[3, 77, "2.0. References [  There is a {} reference showing but is Нет copy-able  ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 77] referenceOpen:[19, 20, "["] reference:[22, 74, "There is a {} reference showing but is Нет copy-able"] referenceClose:[76, 77, "]"]
      Text[22, 74] chars:[22, 74, "There … -able"]
  Reference[79, 160] refOpen:[79, 80, "["] ref:[80, 132, "There is a {} reference showing but is Нет copy-able"] refClose:[132, 134, "]:"] url:[135, 160, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - No Lowercase: 3) options(heading-ids, heading-ids-no-non-ascii-lowercase)
## 2.0. References [ There is a {} reference showing but is Нет copy-able ][ There is a {} reference showing but is Нет copy-able ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a--reference-showing-but-is-Нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 216]
  Heading[0, 131] textOpen:[0, 2, "##"] text:[3, 131, "2.0. References [ There is a {} reference showing but is Нет copy-able ][ There is a {} reference showing but is Нет copy-able ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 131] textOpen:[19, 20, "["] text:[21, 73, "There is a {} reference showing but is Нет copy-able"] textClose:[74, 75, "]"] referenceOpen:[75, 76, "["] reference:[77, 129, "There is a {} reference showing but is Нет copy-able"] referenceClose:[130, 131, "]"]
      Text[21, 73] chars:[21, 73, "There … -able"]
  Reference[133, 214] refOpen:[133, 134, "["] ref:[134, 186, "There is a {} reference showing but is Нет copy-able"] refClose:[186, 188, "]:"] url:[189, 214, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - No Lowercase: 4) options(heading-ids, heading-ids-no-non-ascii-lowercase)
## 2.0. References [  There is a {} reference showing but is Нет copy-able  ][  There is a {} reference showing but is Нет copy-able  ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a--reference-showing-but-is-Нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 220]
  Heading[0, 135] textOpen:[0, 2, "##"] text:[3, 135, "2.0. References [  There is a {} reference showing but is Нет copy-able  ][  There is a {} reference showing but is Нет copy-able  ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 135] textOpen:[19, 20, "["] text:[22, 74, "There is a {} reference showing but is Нет copy-able"] textClose:[76, 77, "]"] referenceOpen:[77, 78, "["] reference:[80, 132, "There is a {} reference showing but is Нет copy-able"] referenceClose:[134, 135, "]"]
      Text[22, 74] chars:[22, 74, "There … -able"]
  Reference[137, 218] refOpen:[137, 138, "["] ref:[138, 190, "There is a {} reference showing but is Нет copy-able"] refClose:[190, 192, "]:"] url:[193, 218, "https://www.example.com/0"]
````````````````````````````````


### No Duped Dashes, No Lowercase

Heading ID option

```````````````````````````````` example(Heading ID - No Duped Dashes, No Lowercase: 1) options(heading-ids, heading-ids-no-non-ascii-lowercase, heading-ids-no-dupe-dashes)
## 2.0. References [ There is a {} reference showing but is Нет copy-able ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a-reference-showing-but-is-Нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 160]
  Heading[0, 75] textOpen:[0, 2, "##"] text:[3, 75, "2.0. References [ There is a {} reference showing but is Нет copy-able ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 75] referenceOpen:[19, 20, "["] reference:[21, 73, "There is a {} reference showing but is Нет copy-able"] referenceClose:[74, 75, "]"]
      Text[21, 73] chars:[21, 73, "There … -able"]
  Reference[77, 158] refOpen:[77, 78, "["] ref:[78, 130, "There is a {} reference showing but is Нет copy-able"] refClose:[130, 132, "]:"] url:[133, 158, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - No Duped Dashes, No Lowercase: 2) options(heading-ids, heading-ids-no-non-ascii-lowercase, heading-ids-no-dupe-dashes)
## 2.0. References [  There is a {} reference showing but is Нет copy-able  ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a-reference-showing-but-is-Нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 162]
  Heading[0, 77] textOpen:[0, 2, "##"] text:[3, 77, "2.0. References [  There is a {} reference showing but is Нет copy-able  ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 77] referenceOpen:[19, 20, "["] reference:[22, 74, "There is a {} reference showing but is Нет copy-able"] referenceClose:[76, 77, "]"]
      Text[22, 74] chars:[22, 74, "There … -able"]
  Reference[79, 160] refOpen:[79, 80, "["] ref:[80, 132, "There is a {} reference showing but is Нет copy-able"] refClose:[132, 134, "]:"] url:[135, 160, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - No Duped Dashes, No Lowercase: 3) options(heading-ids, heading-ids-no-non-ascii-lowercase, heading-ids-no-dupe-dashes)
## 2.0. References [ There is a {} reference showing but is Нет copy-able ][ There is a {} reference showing but is Нет copy-able ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a-reference-showing-but-is-Нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 216]
  Heading[0, 131] textOpen:[0, 2, "##"] text:[3, 131, "2.0. References [ There is a {} reference showing but is Нет copy-able ][ There is a {} reference showing but is Нет copy-able ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 131] textOpen:[19, 20, "["] text:[21, 73, "There is a {} reference showing but is Нет copy-able"] textClose:[74, 75, "]"] referenceOpen:[75, 76, "["] reference:[77, 129, "There is a {} reference showing but is Нет copy-able"] referenceClose:[130, 131, "]"]
      Text[21, 73] chars:[21, 73, "There … -able"]
  Reference[133, 214] refOpen:[133, 134, "["] ref:[134, 186, "There is a {} reference showing but is Нет copy-able"] refClose:[186, 188, "]:"] url:[189, 214, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - No Duped Dashes, No Lowercase: 4) options(heading-ids, heading-ids-no-non-ascii-lowercase, heading-ids-no-dupe-dashes)
## 2.0. References [  There is a {} reference showing but is Нет copy-able  ][  There is a {} reference showing but is Нет copy-able  ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a-reference-showing-but-is-Нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 220]
  Heading[0, 135] textOpen:[0, 2, "##"] text:[3, 135, "2.0. References [  There is a {} reference showing but is Нет copy-able  ][  There is a {} reference showing but is Нет copy-able  ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 135] textOpen:[19, 20, "["] text:[22, 74, "There is a {} reference showing but is Нет copy-able"] textClose:[76, 77, "]"] referenceOpen:[77, 78, "["] reference:[80, 132, "There is a {} reference showing but is Нет copy-able"] referenceClose:[134, 135, "]"]
      Text[22, 74] chars:[22, 74, "There … -able"]
  Reference[137, 218] refOpen:[137, 138, "["] ref:[138, 190, "There is a {} reference showing but is Нет copy-able"] refClose:[190, 192, "]:"] url:[193, 218, "https://www.example.com/0"]
````````````````````````````````


### No Trim End

Heading ID option

```````````````````````````````` example(Heading ID - No Trim End: 1) options(heading-ids, heading-ids-no-trim-end)
## 2.0. References [ There is a {} reference showing but is Нет copy-able ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a--reference-showing-but-is-нет-copy-able-">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 160]
  Heading[0, 75] textOpen:[0, 2, "##"] text:[3, 75, "2.0. References [ There is a {} reference showing but is Нет copy-able ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 75] referenceOpen:[19, 20, "["] reference:[21, 73, "There is a {} reference showing but is Нет copy-able"] referenceClose:[74, 75, "]"]
      Text[21, 73] chars:[21, 73, "There … -able"]
  Reference[77, 158] refOpen:[77, 78, "["] ref:[78, 130, "There is a {} reference showing but is Нет copy-able"] refClose:[130, 132, "]:"] url:[133, 158, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - No Trim End: 2) options(heading-ids, heading-ids-no-trim-end)
## 2.0. References [  There is a {} reference showing but is Нет copy-able  ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a--reference-showing-but-is-нет-copy-able--">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 162]
  Heading[0, 77] textOpen:[0, 2, "##"] text:[3, 77, "2.0. References [  There is a {} reference showing but is Нет copy-able  ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 77] referenceOpen:[19, 20, "["] reference:[22, 74, "There is a {} reference showing but is Нет copy-able"] referenceClose:[76, 77, "]"]
      Text[22, 74] chars:[22, 74, "There … -able"]
  Reference[79, 160] refOpen:[79, 80, "["] ref:[80, 132, "There is a {} reference showing but is Нет copy-able"] refClose:[132, 134, "]:"] url:[135, 160, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - No Trim End: 3) options(heading-ids, heading-ids-no-trim-end)
## 2.0. References [ There is a {} reference showing but is Нет copy-able ][ There is a {} reference showing but is Нет copy-able ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a--reference-showing-but-is-нет-copy-able-">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 216]
  Heading[0, 131] textOpen:[0, 2, "##"] text:[3, 131, "2.0. References [ There is a {} reference showing but is Нет copy-able ][ There is a {} reference showing but is Нет copy-able ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 131] textOpen:[19, 20, "["] text:[21, 73, "There is a {} reference showing but is Нет copy-able"] textClose:[74, 75, "]"] referenceOpen:[75, 76, "["] reference:[77, 129, "There is a {} reference showing but is Нет copy-able"] referenceClose:[130, 131, "]"]
      Text[21, 73] chars:[21, 73, "There … -able"]
  Reference[133, 214] refOpen:[133, 134, "["] ref:[134, 186, "There is a {} reference showing but is Нет copy-able"] refClose:[186, 188, "]:"] url:[189, 214, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - No Trim End: 4) options(heading-ids, heading-ids-no-trim-end)
## 2.0. References [  There is a {} reference showing but is Нет copy-able  ][  There is a {} reference showing but is Нет copy-able  ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references-there-is-a--reference-showing-but-is-нет-copy-able--">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 220]
  Heading[0, 135] textOpen:[0, 2, "##"] text:[3, 135, "2.0. References [  There is a {} reference showing but is Нет copy-able  ][  There is a {} reference showing but is Нет copy-able  ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 135] textOpen:[19, 20, "["] text:[22, 74, "There is a {} reference showing but is Нет copy-able"] textClose:[76, 77, "]"] referenceOpen:[77, 78, "["] reference:[80, 132, "There is a {} reference showing but is Нет copy-able"] referenceClose:[134, 135, "]"]
      Text[22, 74] chars:[22, 74, "There … -able"]
  Reference[137, 218] refOpen:[137, 138, "["] ref:[138, 190, "There is a {} reference showing but is Нет copy-able"] refClose:[190, 192, "]:"] url:[193, 218, "https://www.example.com/0"]
````````````````````````````````


### No Trim Start

Heading ID option

```````````````````````````````` example(Heading ID - No Trim Start: 1) options(heading-ids, heading-ids-no-trim-start)
## 2.0. References [ There is a {} reference showing but is Нет copy-able ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references--there-is-a--reference-showing-but-is-нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 160]
  Heading[0, 75] textOpen:[0, 2, "##"] text:[3, 75, "2.0. References [ There is a {} reference showing but is Нет copy-able ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 75] referenceOpen:[19, 20, "["] reference:[21, 73, "There is a {} reference showing but is Нет copy-able"] referenceClose:[74, 75, "]"]
      Text[21, 73] chars:[21, 73, "There … -able"]
  Reference[77, 158] refOpen:[77, 78, "["] ref:[78, 130, "There is a {} reference showing but is Нет copy-able"] refClose:[130, 132, "]:"] url:[133, 158, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - No Trim Start: 2) options(heading-ids, heading-ids-no-trim-start)
## 2.0. References [  There is a {} reference showing but is Нет copy-able  ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references---there-is-a--reference-showing-but-is-нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 162]
  Heading[0, 77] textOpen:[0, 2, "##"] text:[3, 77, "2.0. References [  There is a {} reference showing but is Нет copy-able  ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 77] referenceOpen:[19, 20, "["] reference:[22, 74, "There is a {} reference showing but is Нет copy-able"] referenceClose:[76, 77, "]"]
      Text[22, 74] chars:[22, 74, "There … -able"]
  Reference[79, 160] refOpen:[79, 80, "["] ref:[80, 132, "There is a {} reference showing but is Нет copy-able"] refClose:[132, 134, "]:"] url:[135, 160, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - No Trim Start: 3) options(heading-ids, heading-ids-no-trim-start)
## 2.0. References [ There is a {} reference showing but is Нет copy-able ][ There is a {} reference showing but is Нет copy-able ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references--there-is-a--reference-showing-but-is-нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 216]
  Heading[0, 131] textOpen:[0, 2, "##"] text:[3, 131, "2.0. References [ There is a {} reference showing but is Нет copy-able ][ There is a {} reference showing but is Нет copy-able ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 131] textOpen:[19, 20, "["] text:[21, 73, "There is a {} reference showing but is Нет copy-able"] textClose:[74, 75, "]"] referenceOpen:[75, 76, "["] reference:[77, 129, "There is a {} reference showing but is Нет copy-able"] referenceClose:[130, 131, "]"]
      Text[21, 73] chars:[21, 73, "There … -able"]
  Reference[133, 214] refOpen:[133, 134, "["] ref:[134, 186, "There is a {} reference showing but is Нет copy-able"] refClose:[186, 188, "]:"] url:[189, 214, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - No Trim Start: 4) options(heading-ids, heading-ids-no-trim-start)
## 2.0. References [  There is a {} reference showing but is Нет copy-able  ][  There is a {} reference showing but is Нет copy-able  ]

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="20-references---there-is-a--reference-showing-but-is-нет-copy-able">2.0. References <a href="https://www.example.com/0">There is a {} reference showing but is Нет copy-able</a></h2>
.
Document[0, 220]
  Heading[0, 135] textOpen:[0, 2, "##"] text:[3, 135, "2.0. References [  There is a {} reference showing but is Нет copy-able  ][  There is a {} reference showing but is Нет copy-able  ]"]
    Text[3, 19] chars:[3, 19, "2.0.  … nces "]
    LinkRef[19, 135] textOpen:[19, 20, "["] text:[22, 74, "There is a {} reference showing but is Нет copy-able"] textClose:[76, 77, "]"] referenceOpen:[77, 78, "["] reference:[80, 132, "There is a {} reference showing but is Нет copy-able"] referenceClose:[134, 135, "]"]
      Text[22, 74] chars:[22, 74, "There … -able"]
  Reference[137, 218] refOpen:[137, 138, "["] ref:[138, 190, "There is a {} reference showing but is Нет copy-able"] refClose:[190, 192, "]:"] url:[193, 218, "https://www.example.com/0"]
````````````````````````````````


### Emoji

Heading ID with emoji

```````````````````````````````` example(Heading ID - Emoji: 1) options(heading-ids, heading-ids-emoji)
## Heading with some_underscore

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="heading-with-some-underscore">Heading with some_underscore</h2>
.
Document[0, 116]
  Heading[0, 31] textOpen:[0, 2, "##"] text:[3, 31, "Heading with some_underscore"]
    Text[3, 31] chars:[3, 31, "Headi … score"]
  Reference[33, 114] refOpen:[33, 34, "["] ref:[34, 86, "There is a {} reference showing but is Нет copy-able"] refClose:[86, 88, "]:"] url:[89, 114, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - Emoji: 2) options(heading-ids, heading-ids-emoji)
## Heading with emoji :information_source:

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="heading-with-emoji-information-source">Heading with emoji :information_source:</h2>
.
Document[0, 127]
  Heading[0, 42] textOpen:[0, 2, "##"] text:[3, 42, "Heading with emoji :information_source:"]
    Text[3, 42] chars:[3, 42, "Headi … urce:"]
  Reference[44, 125] refOpen:[44, 45, "["] ref:[45, 97, "There is a {} reference showing but is Нет copy-able"] refClose:[97, 99, "]:"] url:[100, 125, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - Emoji: 3) options(heading-ids, heading-ids-emoji)
## Heading with emoji :+1:

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="heading-with-emoji-1">Heading with emoji :+1:</h2>
.
Document[0, 111]
  Heading[0, 26] textOpen:[0, 2, "##"] text:[3, 26, "Heading with emoji :+1:"]
    Text[3, 26] chars:[3, 26, "Headi …  :+1:"]
  Reference[28, 109] refOpen:[28, 29, "["] ref:[29, 81, "There is a {} reference showing but is Нет copy-able"] refClose:[81, 83, "]:"] url:[84, 109, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - Emoji: 4) options(heading-ids, heading-ids-emoji)
## Heading with emoji :-1:

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="heading-with-emoji--1">Heading with emoji :-1:</h2>
.
Document[0, 111]
  Heading[0, 26] textOpen:[0, 2, "##"] text:[3, 26, "Heading with emoji :-1:"]
    Text[3, 26] chars:[3, 26, "Headi …  :-1:"]
  Reference[28, 109] refOpen:[28, 29, "["] ref:[29, 81, "There is a {} reference showing but is Нет copy-able"] refClose:[81, 83, "]:"] url:[84, 109, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - Emoji: 5) options(heading-ids, heading-ids-emoji)
## Heading with emoji :100:

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="heading-with-emoji-100">Heading with emoji :100:</h2>
.
Document[0, 112]
  Heading[0, 27] textOpen:[0, 2, "##"] text:[3, 27, "Heading with emoji :100:"]
    Text[3, 27] chars:[3, 27, "Headi … :100:"]
  Reference[29, 110] refOpen:[29, 30, "["] ref:[30, 82, "There is a {} reference showing but is Нет copy-able"] refClose:[82, 84, "]:"] url:[85, 110, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - Emoji: 6) options(heading-ids, heading-ids-emoji)
## Heading with emoji :u5272:

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="heading-with-emoji-u5272">Heading with emoji :u5272:</h2>
.
Document[0, 114]
  Heading[0, 29] textOpen:[0, 2, "##"] text:[3, 29, "Heading with emoji :u5272:"]
    Text[3, 29] chars:[3, 29, "Headi … 5272:"]
  Reference[31, 112] refOpen:[31, 32, "["] ref:[32, 84, "There is a {} reference showing but is Нет copy-able"] refClose:[84, 86, "]:"] url:[87, 112, "https://www.example.com/0"]
````````````````````````````````


### GitHub

Heading ID with emoji

```````````````````````````````` example(Heading ID - GitHub: 1) options(heading-ids, heading-ids-github)
## Heading with some_underscore

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="heading-with-some_underscore">Heading with some_underscore</h2>
.
Document[0, 116]
  Heading[0, 31] textOpen:[0, 2, "##"] text:[3, 31, "Heading with some_underscore"]
    Text[3, 31] chars:[3, 31, "Headi … score"]
  Reference[33, 114] refOpen:[33, 34, "["] ref:[34, 86, "There is a {} reference showing but is Нет copy-able"] refClose:[86, 88, "]:"] url:[89, 114, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - GitHub: 2) options(heading-ids, heading-ids-github)
## Heading with emoji :information_source:

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="heading-with-emoji-information_source">Heading with emoji :information_source:</h2>
.
Document[0, 127]
  Heading[0, 42] textOpen:[0, 2, "##"] text:[3, 42, "Heading with emoji :information_source:"]
    Text[3, 42] chars:[3, 42, "Headi … urce:"]
  Reference[44, 125] refOpen:[44, 45, "["] ref:[45, 97, "There is a {} reference showing but is Нет copy-able"] refClose:[97, 99, "]:"] url:[100, 125, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - GitHub: 3) options(heading-ids, heading-ids-github)
## Heading with emoji :+1:

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="heading-with-emoji-1">Heading with emoji :+1:</h2>
.
Document[0, 111]
  Heading[0, 26] textOpen:[0, 2, "##"] text:[3, 26, "Heading with emoji :+1:"]
    Text[3, 26] chars:[3, 26, "Headi …  :+1:"]
  Reference[28, 109] refOpen:[28, 29, "["] ref:[29, 81, "There is a {} reference showing but is Нет copy-able"] refClose:[81, 83, "]:"] url:[84, 109, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - GitHub: 4) options(heading-ids, heading-ids-github)
## Heading with emoji :-1:

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="heading-with-emoji--1">Heading with emoji :-1:</h2>
.
Document[0, 111]
  Heading[0, 26] textOpen:[0, 2, "##"] text:[3, 26, "Heading with emoji :-1:"]
    Text[3, 26] chars:[3, 26, "Headi …  :-1:"]
  Reference[28, 109] refOpen:[28, 29, "["] ref:[29, 81, "There is a {} reference showing but is Нет copy-able"] refClose:[81, 83, "]:"] url:[84, 109, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - GitHub: 5) options(heading-ids, heading-ids-github)
## Heading with emoji :100:

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="heading-with-emoji-100">Heading with emoji :100:</h2>
.
Document[0, 112]
  Heading[0, 27] textOpen:[0, 2, "##"] text:[3, 27, "Heading with emoji :100:"]
    Text[3, 27] chars:[3, 27, "Headi … :100:"]
  Reference[29, 110] refOpen:[29, 30, "["] ref:[30, 82, "There is a {} reference showing but is Нет copy-able"] refClose:[82, 84, "]:"] url:[85, 110, "https://www.example.com/0"]
````````````````````````````````


```````````````````````````````` example(Heading ID - GitHub: 6) options(heading-ids, heading-ids-github)
## Heading with emoji :u5272:

[There is a {} reference showing but is Нет copy-able]: https://www.example.com/0

.
<h2 id="heading-with-emoji-u5272">Heading with emoji :u5272:</h2>
.
Document[0, 114]
  Heading[0, 29] textOpen:[0, 2, "##"] text:[3, 29, "Heading with emoji :u5272:"]
    Text[3, 29] chars:[3, 29, "Headi … 5272:"]
  Reference[31, 112] refOpen:[31, 32, "["] ref:[32, 84, "There is a {} reference showing but is Нет copy-able"] refClose:[84, 86, "]:"] url:[87, 112, "https://www.example.com/0"]
````````````````````````````````


