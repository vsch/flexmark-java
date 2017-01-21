---
title: FlexmarkHtmlParser Extension Spec
author: 
version: 
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## FlexmarkHtmlParser  

Converts flexmark_html_parser text to FlexmarkHtmlParser nodes.

no spaces between brackets

```````````````````````````````` example(FlexmarkHtmlParser: 1) options(option1, IGNORE)
Sample  text
.
<p>Expected rendered HTML</p>
.
````````````````````````````````


```````````````````````````````` example(FlexmarkHtmlParser: 2) options(FAIL)
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


