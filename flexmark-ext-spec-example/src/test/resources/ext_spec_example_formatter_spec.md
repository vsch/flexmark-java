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

## Section    
    
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
````````````````````````````````


