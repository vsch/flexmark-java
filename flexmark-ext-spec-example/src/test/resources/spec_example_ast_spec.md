---
title: SpecExample Extension Spec
author: 
version: 
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## SpecExample  

Converts spec example text to SpecExample nodes.  

Plain and simple

```````````````````````````````` example SpecExample: 1
```````````````` example
````````````````
.
.
Document[0, 42]
  SpecExampleBlock[0, 41] openingMarker:[0, 16] exampleKeyword:[17, 24] closingMarker:[25, 41]
````````````````````````````````


Plain and simple with nbsp for space on first line

```````````````````````````````` example SpecExample: 2
```````````````` example
````````````````
.
.
Document[0, 42]
  SpecExampleBlock[0, 41] openingMarker:[0, 16] exampleKeyword:[17, 24] closingMarker:[25, 41]
````````````````````````````````


Plain and simple

```````````````````````````````` example SpecExample: 3
```````````````` example
…
````````````````
.
<hr />
.
Document[0, 44]
  SpecExampleBlock[0, 43] openingMarker:[0, 16] exampleKeyword:[17, 24] htmlSeparator:[25, 26] closingMarker:[27, 43]
    SpecExampleSeparator[25, 26] chars:[25, 26, "…"]
````````````````````````````````


Plain and simple

```````````````````````````````` example SpecExample: 4
```````````````` example
…
…
````````````````
.
<hr />
<hr />
.
Document[0, 46]
  SpecExampleBlock[0, 45] openingMarker:[0, 16] exampleKeyword:[17, 24] htmlSeparator:[25, 26] astSeparator:[27, 28] closingMarker:[29, 45]
    SpecExampleSeparator[25, 26] chars:[25, 26, "…"]
    SpecExampleSeparator[27, 28] chars:[27, 28, "…"]
````````````````````````````````


```````````````````````````````` example SpecExample: 5
```````````````` example
…
…
…
````````````````
.
<hr />
<hr />
<pre><code class="language-text">…</code></pre>
.
Document[0, 48]
  SpecExampleBlock[0, 47] openingMarker:[0, 16] exampleKeyword:[17, 24] htmlSeparator:[25, 26] astSeparator:[27, 28] ast:[29, 30] closingMarker:[31, 47]
    SpecExampleSeparator[25, 26] chars:[25, 26, "…"]
    SpecExampleSeparator[27, 28] chars:[27, 28, "…"]
    SpecExampleAst[29, 30] chars:[29, 30, "…"]
````````````````````````````````


