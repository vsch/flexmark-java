---
title: SpecExample Formatter Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Spec Example

```````````````````````````````` example Spec Example: 1
---
title: Intentions Spec
author:
version:
date: '2019-10-14'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

```````````````` example
Markdown only
````````````````
.
---
title: Intentions Spec
author:
version:
date: '2019-10-14'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

```````````````` example : 1
Markdown only
…
````````````````


````````````````````````````````


```````````````````````````````` example Spec Example: 2
---
title: Intentions Spec
author:
version:
date: '2019-10-14'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Section    
    
```````````````` example
Markdown only
````````````````
.
---
title: Intentions Spec
author:
version:
date: '2019-10-14'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Section

```````````````` example Section: 1
Markdown only
…
````````````````


````````````````````````````````


```````````````````````````````` example Spec Example: 3
---
title: Intentions Spec
author:
version:
date: '2019-10-14'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Section    
    
```````````````` example options
Markdown only
````````````````
.
---
title: Intentions Spec
author:
version:
date: '2019-10-14'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Section

```````````````` example Section: 1
Markdown only
…
````````````````


````````````````````````````````


```````````````````````````````` example Spec Example: 4
---
title: Intentions Spec
author:
version:
date: '2019-10-14'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Section    
    
```````````````` example options test
Markdown only
````````````````
.
---
title: Intentions Spec
author:
version:
date: '2019-10-14'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Section

```````````````` example(Section: 1) options(test)
Markdown only
…
````````````````


````````````````````````````````


```````````````````````````````` example Spec Example: 5
---
title: Intentions Spec
author:
version:
date: '2019-10-14'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Section ##
    
```````````````` example options test
Markdown only
````````````````
.
---
title: Intentions Spec
author:
version:
date: '2019-10-14'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Section ##

```````````````` example(Section: 1) options(test)
Markdown only
…
````````````````


````````````````````````````````


```````````````````````````````` example Spec Example: 6
---
title: Intentions Spec
author:
version:
date: '2019-10-14'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Section##
    
```````````````` example options test
Markdown only
````````````````
.
---
title: Intentions Spec
author:
version:
date: '2019-10-14'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Section ##

```````````````` example(Section: 1) options(test)
Markdown only
…
````````````````


````````````````````````````````

preserve blank lines and spaces

```````````````````````````````` example Spec Example: 7
---
title: Intentions Spec
author:
version:
date: '2019-10-14'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Section##
    
```````````````` example options test
Markdown only
…

    
    
…



````````````````
.
---
title: Intentions Spec
author:
version:
date: '2019-10-14'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Section ##

```````````````` example(Section: 1) options(test)
Markdown only
…

    
    
…



````````````````


.
Document[0, 246]
  JekyllFrontMatterBlock[0, 140] open:[0, 3] content:[4, 137] close:[137, 140]
  BlankLine[141, 142]
  Heading[142, 154] textOpen:[142, 144, "##"] text:[145, 152, "Section"] textClose:[152, 154, "##"]
    Text[145, 152] chars:[145, 152, "Section"]
  BlankLine[155, 160]
  SpecExampleBlock[160, 246] openingMarker:[160, 176] exampleKeyword:[177, 184] optionsKeyword:[185, 192] options:[193, 197] source:[198, 211] htmlSeparator:[212, 214] html:[214, 224] astSeparator:[225, 227] ast:[227, 230] closingMarker:[230, 246]
    SpecExampleOptionsList[193, 197] chars:[193, 197, "test"]
      SpecExampleOption[193, 197] chars:[193, 197, "test"]
    SpecExampleSource[198, 211] chars:[198, 211, "Markd …  only"]
    SpecExampleSeparator[212, 214] chars:[212, 214, "…\n"]
    SpecExampleHtml[214, 224] chars:[214, 224, "\n    \n    "]
    SpecExampleSeparator[225, 227] chars:[225, 227, "…\n"]
    SpecExampleAst[227, 230] chars:[227, 230, "\n\n\n"]
````````````````````````````````


```````````````````````````````` example Spec Example: 8
---
title: Intentions Spec
author:
version:
date: '2019-10-14'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Section    
    
some leading text    

```````````````` example options test
[Sample Link Spaces](link" with spaces.html 
'title')
…
<p><a href="link%22%20with%20spaces.html" title="title">Sample Link Spaces</a></p>
…
Document[0, 53]
  Paragraph[0, 53]
    Link[0, 53] textOpen:[0, 1, "["] text:[1, 19, "Sample Link Spaces"] textClose:[19, 20, "]"] linkOpen:[20, 21, "("] url:[21, 43, "link\" with spaces.html"] pageRef:[21, 43, "link\" with spaces.html"] titleOpen:[45, 46, "'"] title:[46, 51, "title"] titleClose:[51, 52, "'"] linkClose:[52, 53, ")"]
      Text[1, 19] chars:[1, 19, "Sampl … paces"]
````````````````

```````````````` example options test
[Sample Link Spaces](link" with spaces.html 
'title')
…
<p><a href="link%22%20with%20spaces.html" title="title">Sample Link Spaces</a></p>
…
Document[0, 53]
  Paragraph[0, 53]
    Link[0, 53] textOpen:[0, 1, "["] text:[1, 19, "Sample Link Spaces"] textClose:[19, 20, "]"] linkOpen:[20, 21, "("] url:[21, 43, "link\" with spaces.html"] pageRef:[21, 43, "link\" with spaces.html"] titleOpen:[45, 46, "'"] title:[46, 51, "title"] titleClose:[51, 52, "'"] linkClose:[52, 53, ")"]
      Text[1, 19] chars:[1, 19, "Sampl … paces"]
````````````````
.
---
title: Intentions Spec
author:
version:
date: '2019-10-14'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Section

some leading text

```````````````` example(Section: 1) options(test)
[Sample Link Spaces](link" with spaces.html 
'title')
…
<p><a href="link%22%20with%20spaces.html" title="title">Sample Link Spaces</a></p>
…
Document[0, 53]
  Paragraph[0, 53]
    Link[0, 53] textOpen:[0, 1, "["] text:[1, 19, "Sample Link Spaces"] textClose:[19, 20, "]"] linkOpen:[20, 21, "("] url:[21, 43, "link\" with spaces.html"] pageRef:[21, 43, "link\" with spaces.html"] titleOpen:[45, 46, "'"] title:[46, 51, "title"] titleClose:[51, 52, "'"] linkClose:[52, 53, ")"]
      Text[1, 19] chars:[1, 19, "Sampl … paces"]
````````````````


```````````````` example(Section: 2) options(test)
[Sample Link Spaces](link" with spaces.html 
'title')
…
<p><a href="link%22%20with%20spaces.html" title="title">Sample Link Spaces</a></p>
…
Document[0, 53]
  Paragraph[0, 53]
    Link[0, 53] textOpen:[0, 1, "["] text:[1, 19, "Sample Link Spaces"] textClose:[19, 20, "]"] linkOpen:[20, 21, "("] url:[21, 43, "link\" with spaces.html"] pageRef:[21, 43, "link\" with spaces.html"] titleOpen:[45, 46, "'"] title:[46, 51, "title"] titleClose:[51, 52, "'"] linkClose:[52, 53, ")"]
      Text[1, 19] chars:[1, 19, "Sampl … paces"]
````````````````


````````````````````````````````


