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

```````````````````````````````` example Yaml Front Matter: 1
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
Document[0, 160]
  YamlFrontMatterBlock[0, 159]
    YamlFrontMatterNode[0, 0]
    YamlFrontMatterNode[0, 0]
    YamlFrontMatterNode[0, 0]
    YamlFrontMatterNode[0, 0]
    YamlFrontMatterNode[0, 0]
````````````````````````````````


```````````````````````````````` example(Yaml Front Matter: 2) options(references-document-top)
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


