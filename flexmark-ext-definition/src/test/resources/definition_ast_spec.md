---
title: Definition Extension Spec
author: 
version: 
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Definition  

Converts definition lists into definition list nodes. Definition terms have an optional `:` at
the end. Definition items can be preceded by `:` or `~`

No optional : at end

```````````````````````````````` example Definition: 1
Definition Term
: definition item 
.
<dl>
   <dt>Definition Term</dt>
   <dd>definition item</dd>
</dl>
.
````````````````````````````````


Optional : at end

```````````````````````````````` example Definition: 2
Definition Term:
: definition item 
.
<dl>
   <dt>Definition Term</dt>
   <dd>definition item</dd>
</dl>
.
````````````````````````````````


Optional : at end after a space

```````````````````````````````` example Definition: 3
Definition Term :
: definition item 
.
<dl>
   <dt>Definition Term</dt>
   <dd>definition item</dd>
</dl>
.
````````````````````````````````


No optional : at end

```````````````````````````````` example Definition: 4
Definition Term
~ definition item 
.
<dl>
   <dt>Definition Term</dt>
   <dd>definition item</dd>
</dl>
.
````````````````````````````````


Optional : at end

```````````````````````````````` example Definition: 5
Definition Term:
~ definition item 
.
<dl>
   <dt>Definition Term</dt>
   <dd>definition item</dd>
</dl>
.
````````````````````````````````


Optional : at end after a space

```````````````````````````````` example Definition: 6
Definition Term :
~ definition item 
.
<dl>
   <dt>Definition Term</dt>
   <dd>definition item</dd>
</dl>
.
````````````````````````````````


