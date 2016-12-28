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

```````````````````````````````` example(Definition: 1) options(IGNORE)
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

```````````````````````````````` example(Definition: 2) options(IGNORE)
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

```````````````````````````````` example(Definition: 3) options(IGNORE)
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

```````````````````````````````` example(Definition: 4) options(IGNORE)
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

```````````````````````````````` example(Definition: 5) options(IGNORE)
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

```````````````````````````````` example(Definition: 6) options(IGNORE)
Definition Term :
~ definition item 
.
<dl>
   <dt>Definition Term</dt>
   <dd>definition item</dd>
</dl>
.
````````````````````````````````


A simple definition list:

```````````````````````````````` example(Definition: 7) options(IGNORE)
Term 1
:   Definition 1

Term 2
:   Definition 2

.
.
````````````````````````````````


With multiple terms:

```````````````````````````````` example(Definition: 8) options(IGNORE)
Term 1
Term 2
:   Definition 1

Term 3
Term 4
:   Definition 2

.
.
````````````````````````````````


With multiple definitions:

```````````````````````````````` example(Definition: 9) options(IGNORE)
Term 1
:   Definition 1
:   Definition 2

Term 2
:   Definition 3
:   Definition 4

.
.
````````````````````````````````


With multiple lines per definition:

```````````````````````````````` example(Definition: 10) options(IGNORE)
Term 1
:   Definition 1 line 1 ...
Definition 1 line 2
:   Definition 2 line 1 ...
Definition 2 line 2

Term 2
:   Definition 3 line 2 ...
    Definition 3 line 2
:   Definition 4 line 2 ...
    Definition 4 line 2

.
.
````````````````````````````````


With paragraphs:

```````````````````````````````` example(Definition: 11) options(IGNORE)
Term 1

:   Definition 1 (paragraph)

Term 2

:   Definition 2 (paragraph)

.
.
````````````````````````````````


With multiple paragraphs:

```````````````````````````````` example(Definition: 12) options(IGNORE)
Term 1

:   Definition 1 paragraph 1 line 1 ...
→Definition 1 paragraph 1 line 2

    Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2

Term 2

:   Definition 1 paragraph 1 line 1 ...
Definition 1 paragraph 1 line 2 (lazy)

    Definition 1 paragraph 2 line 1 ...
Definition 1 paragraph 2 line 2 (lazy)

.
.
````````````````````````````````


A mix:

```````````````````````````````` example(Definition: 13) options(IGNORE)
Term 1
Term 2

:   Definition 1 paragraph 1 line 1 ...
Definition 1 paragraph 1 line 2 (lazy)
    
    Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2

:   Definition 2 paragraph 1 line 1 ...
Definition 2 paragraph 1 line 2 (lazy)

Term 3
:   Definition 3 (no paragraph)
:   Definition 4 (no paragraph)
:   Definition 5 line 1 ...
    Definition 5 line 2 (no paragraph)

:   Definition 6 paragraph 1 line 1 ...
Definition 6 paragraph 1 line 2
:   Definition 7 (no paragraph)
:   Definition 8 paragraph 1 line 1 (forced paragraph) ...
    Definition 8 paragraph 1 line 2
    
    Definition 8 paragraph 2 line 1
    
Term 4
:   Definition 9 paragraph 1 line 1 (forced paragraph) ...
    Definition 9 paragraph 1 line 2
    
    Definition 9 paragraph 2 line 1
:   Definition 10 (no paragraph)
.
.
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos, IGNORE)
Definition Term
: definition item 
.
<dl>
   <dt>Definition Term</dt>
   <dd>definition item</dd>
</dl>
.
````````````````````````````````


