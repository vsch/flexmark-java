---
title: JekyllFrontMatter Extension Spec
author:
version:
date: '2016-06-06'
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
.
Document[0, 159]
  JekyllFrontMatterBlock[0, 159] open:[0, 3] content:[4, 156] close:[156, 159]
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
.
Document[0, 159]
  JekyllFrontMatterBlock[0, 159] open:[0, 3] content:[4, 156] close:[156, 159]
````````````````````````````````


preserve whitespaces in block

```````````````````````````````` example FlexmarkFrontMatter: 2
---
linters:
  - name: linter
    rules:
      - name: UseIsNanNotNanComparison
        url:  https://github.com/HairyFotr/linter/blob/master/src/test/scala/LinterPluginTest.scala#L1930
---
.
.
Document[0, 189]
  JekyllFrontMatterBlock[0, 189] open:[0, 3] content:[4, 186] close:[186, 189]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
---
title: JekyllFrontMatter Extension Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
---
.
.
Document[0, 157]
  JekyllFrontMatterBlock[0, 157] open:[0, 3] content:[4, 154] close:[154, 157]
````````````````````````````````


