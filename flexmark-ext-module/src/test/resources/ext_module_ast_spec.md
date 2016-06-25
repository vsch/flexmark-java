---
title: Module Extension Spec
author: 
version: 
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Module  

Converts module text to Module nodes.  

uno spaces between brackets

```````````````````````````````` example Module: 1  options option1, IGNORE
Sample  text
.
<p>Expected rendered HTML</p>
.
Document[0, 19]
  Paragraph[0, 19]
    Text[0, 2] chars:[0, 2, "[ "]
    Text[17, 18] chars:[17, 18, "]"]
````````````````````````````````


