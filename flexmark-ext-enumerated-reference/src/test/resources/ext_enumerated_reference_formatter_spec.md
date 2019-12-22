---
title: EnumeratedReference Extension Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Enumerated Reference

Converts `[@type:reference]` to enumerated reference based on type pattern defined in enumerated
reference definition of the form:

[@type]: Type content [#]

Where [#] is replaced by the ordinal for the actual reference in the document. [@] is equivalent
to [@] when there is no id. It is treated as a placeholder for the ordinal number for the given
type. Outside of a enumerated reference definition it will render `0`

```````````````````````````````` example Enumerated Reference: 1
![Fig](http://example.com/test.png){#fig:test}  
[#fig:test]

![Fig](http://example.com/test.png){#fig:test2}  
[#fig:test2]

| table |
|-------|
| data  |
[[#tbl:test] caption]
{#tbl:test}

See [@fig:test2]

See [@fig:test]

See [@tbl:test]


[@fig]: Figure [#].

[@tbl]: Table [#].

.
![Fig](http://example.com/test.png){#fig:test}  
[#fig:test]

![Fig](http://example.com/test.png){#fig:test2}  
[#fig:test2]

| table |
|-------|
| data  |
[[#tbl:test] caption]
{#tbl:test}

See [@fig:test2]

See [@fig:test]

See [@tbl:test]


[@fig]: Figure [#].

[@tbl]: Table [#].

````````````````````````````````

* [ ] Test: Sort and placement

