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

```````````````````````````````` example JekyllFrontMatter: 1
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


```````````````````````````````` example(JekyllFrontMatter: 2) options(references-document-top)
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

```````````````````````````````` example FlexmarkFrontMatter: 1
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


```````````````````````````````` example(FlexmarkFrontMatter: 2) options(references-document-top)
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


