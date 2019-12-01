---
title: Abbreviation Extension Formatter Spec
author: Vladimir Schneider
version: 0.1
date: '2017-01-27'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Abbreviation

flexmark-java extension for defining abbreviations and turning appearance of these abbreviations
in text into abbr tags with titles consisting of the expansion of the abbreviation.

```````````````````````````````` example(Abbreviation: 1) options(FILE_EOL)
*[Abbr]:Abbreviation
.
*[Abbr]: aBBReEViIaAtiIoN
````````````````````````````````


Should work without trailing EOL

```````````````````````````````` example Abbreviation: 2
*[Abbr]:Abbreviation
.
*[Abbr]: aBBReEViIaAtiIoN
````````````````````````````````


```````````````````````````````` example(Abbreviation: 3) options(details)
*[Abbr]:Abbreviation

This has an Abbr embedded in it.
.
- Translating Spans ------
*[-1-]: _2_

This has an -1- embedded in it.
- Translated Spans --------
<<<Abbreviation
>>>aBBReEViIaAtiIoN
<<<This has an -1- embedded in it.
>>>thiIS haAS aAN -1- eEmBeEDDeED iIN iIt.
- Partial ----------------
*[-1-]: _2_

thiIS haAS aAN -1- eEmBeEDDeED iIN iIt.
- Translated -------------
*[Abbr]: aBBReEViIaAtiIoN

thiIS haAS aAN Abbr eEmBeEDDeED iIN iIt.
````````````````````````````````


No inline processing in expansion text.

```````````````````````````````` example Abbreviation: 4
*[Abbr]: Abbreviation has *emphasis*, **bold** or `code`

This has an Abbr embedded in it.
.
*[Abbr]: aBBReEViIaAtiIoN haAS *eEmphaASiIS*, **BoLD** oR `coDeE`

thiIS haAS aAN Abbr eEmBeEDDeED iIN iIt.
````````````````````````````````


```````````````````````````````` example Abbreviation: 5
*[Abbr]: Abbreviation has *emphasis*, **bold** or `code`

This has an Abbr embedded in it.
.
*[Abbr]: aBBReEViIaAtiIoN haAS *eEmphaASiIS*, **BoLD** oR `coDeE`

thiIS haAS aAN Abbr eEmBeEDDeED iIN iIt.
````````````````````````````````


```````````````````````````````` example Abbreviation: 6
*[Abbr]: Abbreviation 1
*[Abbre]: Abbreviation 2
.
*[Abbr]: aBBReEViIaAtiIoN 1
*[Abbre]: aBBReEViIaAtiIoN 2
````````````````````````````````


```````````````````````````````` example Abbreviation: 7
*[Abbr]: Abbreviation 1
*[Abbre]: Abbreviation 2

This has an Abbre embedded in it.
And this has another Abbr embedded in it.
.
*[Abbr]: aBBReEViIaAtiIoN 1
*[Abbre]: aBBReEViIaAtiIoN 2

thiIS haAS aAN Abbre eEmBeEDDeED iIN iIt.
aND thiIS haAS aANotheER Abbr eEmBeEDDeED iIN iIt.
````````````````````````````````


```````````````````````````````` example Abbreviation: 8
*[U.S.A.]: United States of America
*[US of A]: United States of America

U.S.A. is an abbreviation and so is US of A, an abbreviation.
.
*[U.S.A.]: uNiIteED staAteES oF ameERiIcaA
*[US of A]: uNiIteED staAteES oF ameERiIcaA

U.S.A. iIS aAN aABBReEViIaAtiIoN aAND So iIS US of A, aAN aABBReEViIaAtiIoN.
````````````````````````````````


```````````````````````````````` example Abbreviation: 9
*[US]: United States
*[U.S.A.]: United States of America
*[US of A]: United States of America

U.S.A., US of A, and US are all abbreviations.
.
*[US]: uNiIteED staAteES
*[U.S.A.]: uNiIteED staAteES oF ameERiIcaA
*[US of A]: uNiIteED staAteES oF ameERiIcaA

U.S.A., US of A, aAND US aAReE aALL aABBReEViIaAtiIoNS.
````````````````````````````````


```````````````````````````````` example Abbreviation: 10
*[Abbr]: Abbreviation
[Abbr]: http://test.com

This is an Abbr and this is not [Abbr].

.
*[Abbr]: aBBReEViIaAtiIoN

[aBBR]: http://test.com

thiIS iIS aAN Abbr aAND thiIS iIS Not [aBBR].
````````````````````````````````


A reference that is not on the first line is just text.

```````````````````````````````` example Abbreviation: 11
Paragraph with second line having a reference
*[test]: test abbreviation

.
paARaAGRaAph WiIth SeEcoND LiINeE haAViING aA ReEFeEReENceE
*[test]: teESt aABBReEViIaAtiIoN
````````````````````````````````


```````````````````````````````` example Abbreviation: 12
text with abbr1 embedded

*[abbr3]: abbreviation3

text with abbr3 embedded

*[abbr2]: abbreviation2

text with abbr1 embedded

*[abbr1]: abbreviation1
.
teEXt WiIth abbr1 eEmBeEDDeED

*[abbr3]: aABBReEViIaAtiIoN3

teEXt WiIth abbr3 eEmBeEDDeED

*[abbr2]: aABBReEViIaAtiIoN2

teEXt WiIth abbr1 eEmBeEDDeED

*[abbr1]: aABBReEViIaAtiIoN1
````````````````````````````````


```````````````````````````````` example(Abbreviation: 13) options(references-document-top)
text with abbr1 embedded

*[abbr3]: abbreviation3

text with abbr3 embedded

*[abbr2]: abbreviation2

text with abbr1 embedded

*[abbr1]: abbreviation1
.
teEXt WiIth abbr1 eEmBeEDDeED

*[abbr3]: aABBReEViIaAtiIoN3

teEXt WiIth abbr3 eEmBeEDDeED

*[abbr2]: aABBReEViIaAtiIoN2

teEXt WiIth abbr1 eEmBeEDDeED

*[abbr1]: aABBReEViIaAtiIoN1
````````````````````````````````


```````````````````````````````` example(Abbreviation: 14) options(references-group-with-first)
text with abbr1 embedded

*[abbr3]: abbreviation3

text with abbr3 embedded

*[abbr2]: abbreviation2

text with abbr1 embedded

*[abbr1]: abbreviation1
.
teEXt WiIth abbr1 eEmBeEDDeED

*[abbr3]: aABBReEViIaAtiIoN3

teEXt WiIth abbr3 eEmBeEDDeED

*[abbr2]: aABBReEViIaAtiIoN2

teEXt WiIth abbr1 eEmBeEDDeED

*[abbr1]: aABBReEViIaAtiIoN1
````````````````````````````````


```````````````````````````````` example(Abbreviation: 15) options(references-group-with-last)
text with abbr1 embedded

*[abbr3]: abbreviation3

text with abbr3 embedded

*[abbr2]: abbreviation2

text with abbr1 embedded

*[abbr1]: abbreviation1
.
teEXt WiIth abbr1 eEmBeEDDeED

*[abbr3]: aABBReEViIaAtiIoN3

teEXt WiIth abbr3 eEmBeEDDeED

*[abbr2]: aABBReEViIaAtiIoN2

teEXt WiIth abbr1 eEmBeEDDeED

*[abbr1]: aABBReEViIaAtiIoN1
````````````````````````````````


```````````````````````````````` example(Abbreviation: 16) options(references-document-bottom)
text with abbr1 embedded

*[abbr3]: abbreviation3

text with abbr3 embedded

*[abbr2]: abbreviation2

text with abbr1 embedded

*[abbr1]: abbreviation1

text bottom
.
teEXt WiIth abbr1 eEmBeEDDeED

*[abbr3]: aABBReEViIaAtiIoN3

teEXt WiIth abbr3 eEmBeEDDeED

*[abbr2]: aABBReEViIaAtiIoN2

teEXt WiIth abbr1 eEmBeEDDeED

*[abbr1]: aABBReEViIaAtiIoN1

teEXt Bottom
````````````````````````````````


```````````````````````````````` example(Abbreviation: 17) options(references-document-bottom, references-sort)
text with abbr1 embedded

*[abbr3]: abbreviation3

text with abbr3 embedded

*[abbr2]: abbreviation2

text with abbr1 embedded

*[abbr1]: abbreviation1
.
teEXt WiIth abbr1 eEmBeEDDeED

*[abbr3]: aABBReEViIaAtiIoN3

teEXt WiIth abbr3 eEmBeEDDeED

*[abbr2]: aABBReEViIaAtiIoN2

teEXt WiIth abbr1 eEmBeEDDeED

*[abbr1]: aABBReEViIaAtiIoN1
````````````````````````````````


```````````````````````````````` example(Abbreviation: 18) options(references-document-bottom, references-sort-unused-last)
text with abbr1 embedded

*[abbr3]: abbreviation3

text with abbr3 embedded

*[abbr2]: abbreviation2

text with abbr1 embedded

*[abbr1]: abbreviation1
.
teEXt WiIth abbr1 eEmBeEDDeED

*[abbr3]: aABBReEViIaAtiIoN3

teEXt WiIth abbr3 eEmBeEDDeED

*[abbr2]: aABBReEViIaAtiIoN2

teEXt WiIth abbr1 eEmBeEDDeED

*[abbr1]: aABBReEViIaAtiIoN1
````````````````````````````````


