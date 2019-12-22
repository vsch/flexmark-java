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
*[Abbr]: Abbreviation

````````````````````````````````


Should work without trailing EOL

```````````````````````````````` example Abbreviation: 2
*[Abbr]:Abbreviation
.
*[Abbr]: Abbreviation

````````````````````````````````


```````````````````````````````` example Abbreviation: 3
*[Abbr]:Abbreviation

This has an Abbr embedded in it.
.
*[Abbr]: Abbreviation

This has an Abbr embedded in it.
````````````````````````````````


No inline processing in expansion text.

```````````````````````````````` example Abbreviation: 4
*[Abbr]: Abbreviation has *emphasis*, **bold** or `code`

This has an Abbr embedded in it.
.
*[Abbr]: Abbreviation has *emphasis*, **bold** or `code`

This has an Abbr embedded in it.
````````````````````````````````


```````````````````````````````` example Abbreviation: 5
*[Abbr]: Abbreviation has *emphasis*, **bold** or `code`

This has an Abbr embedded in it.
.
*[Abbr]: Abbreviation has *emphasis*, **bold** or `code`

This has an Abbr embedded in it.
````````````````````````````````


```````````````````````````````` example Abbreviation: 6
*[Abbr]: Abbreviation 1
*[Abbre]: Abbreviation 2
.
*[Abbr]: Abbreviation 1
*[Abbre]: Abbreviation 2

````````````````````````````````


```````````````````````````````` example Abbreviation: 7
*[Abbr]: Abbreviation 1
*[Abbre]: Abbreviation 2

This has an Abbre embedded in it.
And this has another Abbr embedded in it.
.
*[Abbr]: Abbreviation 1
*[Abbre]: Abbreviation 2

This has an Abbre embedded in it.
And this has another Abbr embedded in it.
````````````````````````````````


```````````````````````````````` example Abbreviation: 8
*[U.S.A.]: United States of America
*[US of A]: United States of America

U.S.A. is an abbreviation and so is US of A, an abbreviation.
.
*[U.S.A.]: United States of America
*[US of A]: United States of America

U.S.A. is an abbreviation and so is US of A, an abbreviation.
````````````````````````````````


```````````````````````````````` example Abbreviation: 9
*[US]: United States
*[U.S.A.]: United States of America
*[US of A]: United States of America

U.S.A., US of A, and US are all abbreviations.
.
*[US]: United States
*[U.S.A.]: United States of America
*[US of A]: United States of America

U.S.A., US of A, and US are all abbreviations.
````````````````````````````````


```````````````````````````````` example Abbreviation: 10
*[Abbr]: Abbreviation
[Abbr]: http://test.com

This is an Abbr and this is not [Abbr].

.
*[Abbr]: Abbreviation

[Abbr]: http://test.com

This is an Abbr and this is not [Abbr].

````````````````````````````````


A reference that is not on the first line is just text.

```````````````````````````````` example Abbreviation: 11
Paragraph with second line having a reference
*[test]: test abbreviation

.
Paragraph with second line having a reference

*[test]: test abbreviation

````````````````````````````````


```````````````````````````````` example Abbreviation: 12
text with abbr1 embedded

*[abbr3]: abbreviation3

text with abbr3 embedded

*[abbr2]: abbreviation2

text with abbr1 embedded

*[abbr1]: abbreviation1
.
text with abbr1 embedded

*[abbr3]: abbreviation3

text with abbr3 embedded

*[abbr2]: abbreviation2

text with abbr1 embedded

*[abbr1]: abbreviation1

````````````````````````````````


```````````````````````````````` example(Abbreviation: 13) options(references-document-top)
text with abbr1 embedded

*[abbr3]: abbreviation3

text with abbr3 embedded

*[abbr2]: abbreviation2

text with abbr1 embedded

*[abbr1]: abbreviation1
.
*[abbr3]: abbreviation3
*[abbr2]: abbreviation2
*[abbr1]: abbreviation1

text with abbr1 embedded

text with abbr3 embedded

text with abbr1 embedded

````````````````````````````````


```````````````````````````````` example(Abbreviation: 14) options(references-group-with-first)
text with abbr1 embedded

*[abbr3]: abbreviation3

text with abbr3 embedded

*[abbr2]: abbreviation2

text with abbr1 embedded

*[abbr1]: abbreviation1
.
text with abbr1 embedded

*[abbr3]: abbreviation3
*[abbr2]: abbreviation2
*[abbr1]: abbreviation1

text with abbr3 embedded

text with abbr1 embedded

````````````````````````````````


```````````````````````````````` example(Abbreviation: 15) options(references-group-with-last)
text with abbr1 embedded

*[abbr3]: abbreviation3

text with abbr3 embedded

*[abbr2]: abbreviation2

text with abbr1 embedded

*[abbr1]: abbreviation1
.
text with abbr1 embedded

text with abbr3 embedded

text with abbr1 embedded

*[abbr3]: abbreviation3
*[abbr2]: abbreviation2
*[abbr1]: abbreviation1

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
text with abbr1 embedded

text with abbr3 embedded

text with abbr1 embedded

text bottom

*[abbr3]: abbreviation3
*[abbr2]: abbreviation2
*[abbr1]: abbreviation1

````````````````````````````````


```````````````````````````````` example(Abbreviation: 17) options(references-document-bottom, references-sort)
text with abbr1 embedded

*[abbr3]: abbreviation3

text with abbr3 embedded

*[abbr2]: abbreviation2

text with abbr1 embedded

*[abbr1]: abbreviation1
.
text with abbr1 embedded

text with abbr3 embedded

text with abbr1 embedded

*[abbr1]: abbreviation1
*[abbr2]: abbreviation2
*[abbr3]: abbreviation3

````````````````````````````````


```````````````````````````````` example(Abbreviation: 18) options(references-document-bottom, references-sort-unused-last)
text with abbr1 embedded

*[abbr3]: abbreviation3

text with abbr3 embedded

*[abbr2]: abbreviation2

text with abbr1 embedded

*[abbr1]: abbreviation1
.
text with abbr1 embedded

text with abbr3 embedded

text with abbr1 embedded

*[abbr1]: abbreviation1
*[abbr3]: abbreviation3
*[abbr2]: abbreviation2

````````````````````````````````


```````````````````````````````` example(Abbreviation: 19) options(references-document-bottom, references-sort-delete-unused)
text with abbr1 embedded

*[abbr3]: abbreviation3

text with abbr3 embedded

*[abbr2]: abbreviation2

text with abbr1 embedded

*[abbr1]: abbreviation1
.
text with abbr1 embedded

text with abbr3 embedded

text with abbr1 embedded

*[abbr1]: abbreviation1
*[abbr3]: abbreviation3

````````````````````````````````


```````````````````````````````` example(Abbreviation: 20) options(references-document-bottom, references-delete-unused)
text with abbr1 embedded

*[abbr3]: abbreviation3

text with abbr3 embedded

*[abbr2]: abbreviation2

text with abbr1 embedded

*[abbr1]: abbreviation1
.
text with abbr1 embedded

text with abbr3 embedded

text with abbr1 embedded

*[abbr3]: abbreviation3
*[abbr1]: abbreviation1

````````````````````````````````


