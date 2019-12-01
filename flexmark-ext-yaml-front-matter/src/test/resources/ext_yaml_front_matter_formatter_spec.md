---
title: Yaml Front Matter Extension Formatting Spec
author:
version:
date: '2017-01-28'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Yaml Front Matter

Converts yaml front_matter text

```````````````````````````````` example Yaml Front Matter: 1
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

.
Document[0, 159]
  YamlFrontMatterBlock[0, 159]
    YamlFrontMatterNode[4, 43]
      YamlFrontMatterValue[11, 43]
    YamlFrontMatterNode[44, 50]
    YamlFrontMatterNode[53, 60]
    YamlFrontMatterNode[63, 81]
      YamlFrontMatterValue[69, 81]
    YamlFrontMatterNode[82, 155]
      YamlFrontMatterValue[91, 155]
````````````````````````````````


preserve whitespaces in block

```````````````````````````````` example Yaml Front Matter: 2
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
  YamlFrontMatterBlock[0, 189]
    YamlFrontMatterNode[4, 79]
      YamlFrontMatterValue[17, 29]
      YamlFrontMatterValue[49, 79]
````````````````````````````````


```````````````````````````````` example(Yaml Front Matter: 3) options(references-document-top)
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


