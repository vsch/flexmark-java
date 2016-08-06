---
title: Util Extension Spec
author: 
version: 
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Util  

Converts util text to Util nodes.

no spaces between brackets

```````````````````````````````` example(Util: 1) options(option1, IGNORE)
Sample  text
.
<p>Expected rendered HTML</p>
.
````````````````````````````````


```````````````````````````````` example(Util: 2) options(FAIL)
&#X01; &#X234564; fail
.
<p> �</p>
.
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos, IGNORE)
.
.
````````````````````````````````


