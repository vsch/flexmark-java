---
title: Jekyll Front Matter Extension Formatting Spec
author:
version:
date: '2017-01-28'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## JekyllFrontMatter

Converts jekyll_front_matter text to JekyllFrontMatter node.

```````````````````````````````` example JekyllFrontMatter: 1
---
title: JekyllFrontMatter Extension Spec
author: 
version: 
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
---
.
---
title: JekyllFrontMatter Extension Spec
author: 
version: 
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
---

````````````````````````````````


preserve whitespaces in block

```````````````````````````````` example JekyllFrontMatter: 2
---
linters:
  - name: linter
    rules:
      - name: UseIsNanNotNanComparison
        url:  https://github.com/HairyFotr/linter/blob/master/src/test/scala/LinterPluginTest.scala#L1930
---
.
---
linters:
  - name: linter
    rules:
      - name: UseIsNanNotNanComparison
        url:  https://github.com/HairyFotr/linter/blob/master/src/test/scala/LinterPluginTest.scala#L1930
---

.
Document[0, 189]
  JekyllFrontMatterBlock[0, 189] open:[0, 3] content:[4, 186] close:[186, 189]
````````````````````````````````


```````````````````````````````` example(JekyllFrontMatter: 3) options(references-document-top)
---
title: JekyllFrontMatter Extension Spec
author: 
version: 
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
---

[ref1]: /ref1
[ref2]: /ref2
[ref3]: /ref3

.
---
title: JekyllFrontMatter Extension Spec
author: 
version: 
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
---

[ref1]: /ref1
[ref2]: /ref2
[ref3]: /ref3

````````````````````````````````


## FlexmarkFrontMatter

Converts jekyll front matter text to JekyllFrontMatter node.

```````````````````````````````` example FlexmarkFrontMatter: 1
---
title: JekyllFrontMatter Extension Spec
author: 
version: 
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...
.
---
title: JekyllFrontMatter Extension Spec
author: 
version: 
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

````````````````````````````````


```````````````````````````````` example(FlexmarkFrontMatter: 2) options(references-document-top)
---
title: JekyllFrontMatter Extension Spec
author: 
version: 
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

[ref1]: /ref1
[ref2]: /ref2
[ref3]: /ref3

.
---
title: JekyllFrontMatter Extension Spec
author: 
version: 
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

[ref1]: /ref1
[ref2]: /ref2
[ref3]: /ref3

````````````````````````````````


