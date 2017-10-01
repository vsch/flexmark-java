---
title: Html to Markdown Converter Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Text

Converts HTML to markdown

```````````````````````````````` example Text: 1
Expected rendered HTML

.
<p>Expected rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Text: 2
Expected rendered HTML  
Another line

.
<p>Expected rendered HTML<br>Another line</p>
````````````````````````````````


```````````````````````````````` example Text: 3
Expected rendered HTML  

Another line

.
<p>Expected rendered HTML<br><br>Another line</p>
````````````````````````````````


## Emphasis

```````````````````````````````` example Emphasis: 1
**Expected** rendered HTML

.
<p><strong>Expected</strong> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 2
**Expected** rendered HTML

.
<p><b>Expected</b> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 3
*Expected* rendered HTML

.
<p><em>Expected</em> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 4
*Expected* rendered HTML

.
<p><i>Expected</i> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 5
++Expected++ rendered HTML

.
<p><ins>Expected</ins> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 6
~~Expected~~ rendered HTML

.
<p><del>Expected</del> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 7
~Expected~ rendered HTML

.
<p><sub>Expected</sub> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 8
H~2~O

.
<p>H<sub>2</sub>O</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 9
^Expected^ rendered HTML

.
<p><sup>Expected</sup> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 10
H^2^O

.
<p>H<sup>2</sup>O</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 11
`Expected` rendered HTML

.
<p><code>Expected</code> rendered HTML</p>
````````````````````````````````


``Expected `a``

```````````````````````````````` example Emphasis: 12
``Expected `a`` rendered HTML

.
<p><code>Expected `a</code> rendered HTML</p>
````````````````````````````````


## Bullet Lists

```````````````````````````````` example Bullet Lists: 1
* item

.
<ul>
<li>item</li>
</ul>
````````````````````````````````


```````````````````````````````` example Bullet Lists: 2
* item 1
* item 2
* item 3
* item 4

.
<ul>
<li>item 1</li>
<li>item 2</li>
<li>item 3</li>
<li>item 4</li>
</ul>
````````````````````````````````


```````````````````````````````` example Bullet Lists: 3
* item 1

* item 2

* item 3

* item 4

.
<ul>
<li><p>item 1</p></li>
<li><p>item 2</p></li>
<li><p>item 3</p></li>
<li><p>item 4</p></li>
</ul>
````````````````````````````````


```````````````````````````````` example Bullet Lists: 4
* item 1

  * item 1

  * item 2

  * item 3

  * item 4

* item 2

* item 3

* item 4

.
<ul>
<li>
  <p>item 1</p>
  <ul>
  <li><p>item 1</p></li>
  <li><p>item 2</p></li>
  <li><p>item 3</p></li>
  <li><p>item 4</p></li>
  </ul>
</li>
<li><p>item 2</p></li>
<li><p>item 3</p></li>
<li><p>item 4</p></li>
</ul>
````````````````````````````````


## Numbered Lists

```````````````````````````````` example Numbered Lists: 1
1. item

.
<ol>
<li>item</li>
</ol>
````````````````````````````````


```````````````````````````````` example Numbered Lists: 2
1. item 1
2. item 2
3. item 3
4. item 4

.
<ol>
<li>item 1</li>
<li>item 2</li>
<li>item 3</li>
<li>item 4</li>
</ol>
````````````````````````````````


```````````````````````````````` example Numbered Lists: 3
1. item 1

2. item 2

3. item 3

4. item 4

.
<ol>
<li><p>item 1</p></li>
<li><p>item 2</p></li>
<li><p>item 3</p></li>
<li><p>item 4</p></li>
</ol>
````````````````````````````````


```````````````````````````````` example Numbered Lists: 4
1. item 1

   1. item 1

   2. item 2

   3. item 3

   4. item 4

2. item 2

3. item 3

4. item 4

.
<ol>
<li>
  <p>item 1</p>
  <ol>
  <li><p>item 1</p></li>
  <li><p>item 2</p></li>
  <li><p>item 3</p></li>
  <li><p>item 4</p></li>
  </ol>
</li>
<li><p>item 2</p></li>
<li><p>item 3</p></li>
<li><p>item 4</p></li>
</ol>
````````````````````````````````


## Task List Items

```````````````````````````````` example Task List Items: 1
* [ ] item
* [x] item

.
<ul>
<li><input type="checkbox">item</li>
<li><input type="checkbox" checked>item</li>
</ul>
<input type="checkbox">
````````````````````````````````


```````````````````````````````` example Task List Items: 2
1. [ ] item
2. [x] item

.
<ol>
<li><input type="checkbox">item</li>
<li><input type="checkbox" checked>item</li>
</ol>
<input type="checkbox">
````````````````````````````````


## Block Quotes

```````````````````````````````` example Block Quotes: 1
> Expected rendered HTML

.
<blockquote>
  <p>Expected rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 2
> **Expected** rendered HTML

.
<blockquote>
  <p><strong>Expected</strong> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 3
> **Expected** rendered HTML

.
<blockquote>
  <p><b>Expected</b> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 4
> *Expected* rendered HTML

.
<blockquote>
  <p><em>Expected</em> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 5
> *Expected* rendered HTML

.
<blockquote>
  <p><i>Expected</i> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 6
> ++Expected++ rendered HTML

.
<blockquote>
  <p><ins>Expected</ins> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 7
> ~~Expected~~ rendered HTML

.
<blockquote>
  <p><del>Expected</del> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 8
> ~Expected~ rendered HTML

.
<blockquote>
  <p><sub>Expected</sub> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 9
> ^Expected^ rendered HTML

.
<blockquote>
  <p><sup>Expected</sup> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 10
> `Expected` rendered HTML

.
<blockquote>
  <p><code>Expected</code> rendered HTML</p>
</blockquote>
````````````````````````````````


``Expected `a``

```````````````````````````````` example Block Quotes: 11
> ``Expected `a`` rendered HTML

.
<blockquote>
  <p><code>Expected `a</code> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 12
> * item

.
<blockquote>
 <ul>
 <li>item</li>
 </ul>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 13
> * item 1
> * item 2
> * item 3
> * item 4

.
<blockquote>
<ul>
<li>item 1</li>
<li>item 2</li>
<li>item 3</li>
<li>item 4</li>
</ul>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 14
> * item 1
> 
> * item 2
> 
> * item 3
> 
> * item 4

.
<blockquote>
<ul>
<li><p>item 1</p></li>
<li><p>item 2</p></li>
<li><p>item 3</p></li>
<li><p>item 4</p></li>
</ul>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 15
* item 1
  > 
  > * item 1
  > 
  > * item 2
  > 
  > * item 3
  > 
  > * item 4

* item 2

* item 3

* item 4

.
<ul>
<li>
  <p>item 1</p>
<blockquote>
  <ul>
  <li><p>item 1</p></li>
  <li><p>item 2</p></li>
  <li><p>item 3</p></li>
  <li><p>item 4</p></li>
  </ul>
</blockquote>
</li>
<li><p>item 2</p></li>
<li><p>item 3</p></li>
<li><p>item 4</p></li>
</ul>
````````````````````````````````


```````````````````````````````` example Block Quotes: 16
> * item 1
>   
>   * item 1
>   
>   * item 2
>   
>   * item 3
>   
>   * item 4
> 
> * item 2
> 
> * item 3
> 
> * item 4

.
<blockquote>
<ul>
<li>
  <p>item 1</p>
  <ul>
  <li><p>item 1</p></li>
  <li><p>item 2</p></li>
  <li><p>item 3</p></li>
  <li><p>item 4</p></li>
  </ul>
</li>
<li><p>item 2</p></li>
<li><p>item 3</p></li>
<li><p>item 4</p></li>
</ul>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 17
> 1. item

.
<blockquote>
<ol>
<li>item</li>
</ol>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 18
> 1. item 1
> 2. item 2
> 3. item 3
> 4. item 4

.
<blockquote>
<ol>
<li>item 1</li>
<li>item 2</li>
<li>item 3</li>
<li>item 4</li>
</ol>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 19
> 1. item 1
> 
> 2. item 2
> 
> 3. item 3
> 
> 4. item 4

.
<blockquote>
<ol>
<li><p>item 1</p></li>
<li><p>item 2</p></li>
<li><p>item 3</p></li>
<li><p>item 4</p></li>
</ol>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 20
1. item 1
   > 
   > 1. item 1
   > 
   > 2. item 2
   > 
   > 3. item 3
   > 
   > 4. item 4

2. item 2

3. item 3

4. item 4

.
<ol>
<li>
  <p>item 1</p>
<blockquote>
  <ol>
  <li><p>item 1</p></li>
  <li><p>item 2</p></li>
  <li><p>item 3</p></li>
  <li><p>item 4</p></li>
  </ol>
</blockquote>
</li>
<li><p>item 2</p></li>
<li><p>item 3</p></li>
<li><p>item 4</p></li>
</ol>
````````````````````````````````


```````````````````````````````` example Block Quotes: 21
> 1. item 1
>    
>    1. item 1
>    
>    2. item 2
>    
>    3. item 3
>    
>    4. item 4
> 
> 2. item 2
> 
> 3. item 3
> 
> 4. item 4

.
<blockquote>
<ol>
<li>
  <p>item 1</p>
  <ol>
  <li><p>item 1</p></li>
  <li><p>item 2</p></li>
  <li><p>item 3</p></li>
  <li><p>item 4</p></li>
  </ol>
</li>
<li><p>item 2</p></li>
<li><p>item 3</p></li>
<li><p>item 4</p></li>
</ol>
</blockquote>
````````````````````````````````


## Headings

```````````````````````````````` example Headings: 1
Heading
=======

.
<h1>Heading</h1>
````````````````````````````````


```````````````````````````````` example Headings: 2
Heading
-------

.
<h2>Heading</h2>
````````````````````````````````


```````````````````````````````` example Headings: 3
### Heading

.
<h3>Heading</h3>
````````````````````````````````


```````````````````````````````` example Headings: 4
#### Heading

.
<h4>Heading</h4>
````````````````````````````````


```````````````````````````````` example Headings: 5
##### Heading

.
<h5>Heading</h5>
````````````````````````````````


```````````````````````````````` example Headings: 6
###### Heading

.
<h6>Heading</h6>
````````````````````````````````


## Thematic Break

```````````````````````````````` example Thematic Break: 1
*** ** * ** ***

.
<hr>
````````````````````````````````


## Links

not links

```````````````````````````````` example Links: 1
Text

.
<a id="http://example.com">Text</a>
````````````````````````````````


```````````````````````````````` example Links: 2
Text **Bold**

.
<a id="http://example.com">Text <b>Bold</b></a>
````````````````````````````````


links

```````````````````````````````` example Links: 3
[](http://example.com)

.
<a href="http://example.com"></a>
````````````````````````````````


```````````````````````````````` example Links: 4
[Text](http://example.com)

.
<a href="http://example.com">Text</a>
````````````````````````````````


```````````````````````````````` example Links: 5
[Text](http://example.com "Title")

.
<a href="http://example.com" title="Title">Text</a>
````````````````````````````````


```````````````````````````````` example Links: 6
[Text **Bold**](http://example.com)

.
<a href="http://example.com">Text <b>Bold</b></a>
````````````````````````````````


auto links

```````````````````````````````` example Links: 7
http://example.com

.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 8) options(wrap-autolinks)
<http://example.com>

.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


```````````````````````````````` example Links: 9
[http://example.com](http://example.com "Title")

.
<a href="http://example.com" title="Title">http://example.com</a>
````````````````````````````````


```````````````````````````````` example Links: 10
http://example.com

.
<a href="http://example.com" title="">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 11) options(no-autolinks)
[http://example.com](http://example.com)

.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


## Images

Not images

```````````````````````````````` example Images: 1
.
<img id="http://example.com">
````````````````````````````````


```````````````````````````````` example Images: 2
![](http://example.com)

.
<img src="http://example.com">
````````````````````````````````


```````````````````````````````` example Images: 3
![](http://example.com)

.
<img src="http://example.com" alt="">
````````````````````````````````


```````````````````````````````` example Images: 4
![Alt](http://example.com)

.
<img src="http://example.com" alt="Alt">
````````````````````````````````


```````````````````````````````` example Images: 5
![](http://example.com "Title")

.
<img src="http://example.com" title="Title">
````````````````````````````````


```````````````````````````````` example Images: 6
![](http://example.com "Title")

.
<img src="http://example.com" alt="" title="Title">
````````````````````````````````


```````````````````````````````` example Images: 7
![Alt](http://example.com "Title")

.
<img src="http://example.com" alt="Alt" title="Title">
````````````````````````````````


Multi-line URL

```````````````````````````````` example Images: 8
![alt](http://latex.codecogs.com/gif.latex?
\begin{align*}
x^2 + y^2 &= 1 \\
y &= \sqrt{1 - x^2} \\
\end{align*}
"title")

.
<img src="http://latex.codecogs.com/gif.latex?%5Cbegin%7Balign*%7D%0Ax%5E2%20%2B%20y%5E2%20&amp;=%201%20%5C%5C%0Ay%20&amp;=%20%5Csqrt%7B1%20-%20x%5E2%7D%20%5C%5C%0A%5Cend%7Balign*%7D%0A" alt="alt" title="title" />
````````````````````````````````


## Tables

```````````````````````````````` example Tables: 1
.
<table>
  <thead></thead>
  <tbody></tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 2
| Abc | Def |
|-----|-----|

.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody></tbody>
</table>
````````````````````````````````


Separator columns need to be at least 3 characters, not 3 dashes

```````````````````````````````` example Tables: 3
| Abc | Def |
|:----|-----|

.
<table>
  <thead>
    <tr><th align="left">Abc</th><th>Def</th></tr>
  </thead>
  <tbody></tbody>
</table>
````````````````````````````````


Separator columns need to be at least 3 characters, not 3 dashes

```````````````````````````````` example Tables: 4
| Abc | Def |
|----:|-----|

.
<table>
  <thead>
    <tr><th align="right">Abc</th><th>Def</th></tr>
  </thead>
  <tbody></tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 5
| Abc | Def |
|:---:|-----|

.
<table>
  <thead>
    <tr><th align="center">Abc</th><th>Def</th></tr>
  </thead>
  <tbody></tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 6
| Abc |
|-----|

.
<table>
  <thead>
    <tr><th>Abc</th></tr>
  </thead>
  <tbody></tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 7
| Abc |
|-----|

.
<table>
  <thead>
    <tr><th>Abc</th></tr>
  </thead>
  <tbody></tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 8
| Abc |
|-----|

.
<table>
  <thead>
    <tr><th>Abc</th></tr>
  </thead>
  <tbody></tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 9
| Abc |
|-----|
| 1   |

.
<table>
  <thead>
    <tr><th>Abc</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 10
| Abc |
|-----|
| 1   |

.
<table>
  <thead>
    <tr><th>Abc</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 11
| Abc |
|-----|
| 1   |

.
<table>
  <thead>
    <tr><th>Abc</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 12
| Abc |
|-----|

.
<table>
  <thead>
    <tr><th>Abc</th></tr>
  </thead>
  <tbody></tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 13
| Abc | Def |
|-----|-----|
| 1   | 2   |

.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 14
| Abc | Def | Ghi |
|-----|-----|-----|
| 1   | 2   | 3   |

.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th><th>Ghi</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td><td>3</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 15
| Abc | Def |
|-----|-----|
| 1   | 2   |

.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 16
| Abc | Def |
|-----|-----|
| 1   | 2   |

.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 17
| Abc | Def |
|-----|-----|
| 1   | 2   |

.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td></tr>
  </tbody>
</table>
````````````````````````````````


Embedded pipes in inline elements

```````````````````````````````` example Tables: 18
| Abc | Def |
|-----|-----|
| `|` | `|` |

.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td><code>|</code></td><td><code>|</code></td></tr>
  </tbody>
</table>
````````````````````````````````


unclosed delimiters in cells

```````````````````````````````` example Tables: 19
| Abc | Def |
|-----|-----|-----|
| `   |     | abc |

.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>`</td><td></td><td>abc</td></tr>
  </tbody>
</table>
````````````````````````````````


unclosed delimiters in cells

```````````````````````````````` example Tables: 20
| Abc   | Def |
|-------|-----|
| **def | abc |

.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>**def</td><td>abc</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 21
| *Abc* | Def |
|-------|-----|
| 1     | 2   |

.
<table>
  <thead>
    <tr><th><em>Abc</em></th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 22
| Abc | Def |
|-----|-----|----|
| 1\\ | 2   | 20 |

.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1\</td><td>2</td><td>20</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 23
| Abc   | Def |
|-------|-----|
| 1\\\\ | 2   |

.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1\\</td><td>2</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 24
| Abc | Def |
|:----|-----|
| 1   | 2   |

.
<table>
  <thead>
    <tr><th align="left">Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td align="left">1</td><td>2</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 25
| Abc | Def |
|----:|-----|
|   1 | 2   |

.
<table>
  <thead>
    <tr><th align="right">Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td align="right">1</td><td>2</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 26
| Abc | Def |
|:---:|-----|
|  1  | 2   |

.
<table>
  <thead>
    <tr><th align="center">Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td align="center">1</td><td>2</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 27
| Abc | Def |
|-----|:---:|
| 1   |  2  |

.
<table>
  <thead>
    <tr><th>Abc</th><th align="center">Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td align="center">2</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 28
| Abc | Def |
|:----|-----|
| 1   | 2   |

.
<table>
  <thead>
    <tr><th align="left">Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td align="left">1</td><td>2</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 29
| Abc | Def |
|-----|-----|---|
| 1   | 2   | 3 |

.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td><td>3</td></tr>
  </tbody>
</table>
````````````````````````````````


Extra columns truncated with GFM compatibility on.

```````````````````````````````` example Tables: 30
| Abc | Def |
|-----|-----|
| 1   | 2   |

.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 31
| Abc | Def | Ghi |
|-----|-----|-----|
| 1   | 2   |

.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th><th>Ghi</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td></tr>
  </tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 32
> | Abc | Def |
> |-----|-----|
> | 1   | 2   |

.
<blockquote>
  <table>
    <thead>
      <tr><th>Abc</th><th>Def</th></tr>
    </thead>
    <tbody>
      <tr><td>1</td><td>2</td></tr>
    </tbody>
  </table>
</blockquote>
````````````````````````````````


```````````````````````````````` example Tables: 33
| Abc | Def |
|-----|-----|
| 1   | 2   |

table, you are over

.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1</td><td>2</td></tr>
  </tbody>
</table>
<p>table, you are over</p>
````````````````````````````````


inlines should be processed

```````````````````````````````` example Tables: 34
| **Abc**     | *Def*  |
|-------------|--------|
| [ref](/url) | `code` |

.
<table>
  <thead>
    <tr><th><strong>Abc</strong></th><th><em>Def</em></th></tr>
  </thead>
  <tbody>
    <tr><td><a href="/url">ref</a></td><td><code>code</code></td></tr>
  </tbody>
</table>
````````````````````````````````


inlines should be processed

```````````````````````````````` example Tables: 35
| **Abc** **test** | *Def* *Def*   |
|------------------|---------------|
| [ref](/url)      | `code` `code` |

.
<table>
  <thead>
    <tr><th><strong>Abc</strong> <strong>test</strong></th><th><em>Def</em> <em>Def</em></th></tr>
  </thead>
  <tbody>
    <tr><td><a href="/url">ref</a></td><td><code>code</code> <code>code</code></td></tr>
  </tbody>
</table>
````````````````````````````````


Column spans are created with repeated | pipes one for each additional column to span

```````````````````````````````` example Tables: 36
| Abc | Def |
|-----|-----|
| span     ||

.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td colspan="2">span</td></tr>
  </tbody>
</table>
````````````````````````````````


Now we try varying the header lines and make sure we get the right output

```````````````````````````````` example Tables: 37
| Abc | Def |
| Hij | Lmn |
|-----|-----|
| span     ||

.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
    <tr><th>Hij</th><th>Lmn</th></tr>
  </thead>
  <tbody>
    <tr><td colspan="2">span</td></tr>
  </tbody>
</table>
````````````````````````````````


No header lines

```````````````````````````````` example Tables: 38
|------|------|
| col1 | col2 |

.
<table>
  <thead></thead>
  <tbody>
    <tr><td>col1</td><td>col2</td></tr>
  </tbody>
</table>
````````````````````````````````


No body lines

```````````````````````````````` example Tables: 39
| col1 | col2 |
|------|------|

.
<table>
  <thead>
    <tr><th>col1</th><th>col2</th></tr>
  </thead>
  <tbody></tbody>
</table>
````````````````````````````````


With caption

```````````````````````````````` example Tables: 40
| col1 | col2 |
|------|------|
[Caption **bold** *italic* `code`]

.
<table>
  <thead>
    <tr><th>col1</th><th>col2</th></tr>
  </thead>
  <tbody></tbody>
  <caption>Caption <strong>bold</strong> <em>italic</em> <code>code</code></caption>
</table>
````````````````````````````````


Alignment should be taken from column after span is added

```````````````````````````````` example Tables: 41
| day         | time  |   spent |
|:------------|:-----:|--------:|
| nov. 2. tue | 10:00 |  4h 40m |
| nov. 3. thu | 11:00 |      4h |
| nov. 7. mon | 10:20 |  4h 20m |
| total:             || **13h** |

.
<table>
  <thead>
    <tr><th align="left">day</th><th align="center">time</th><th align="right">spent</th></tr>
  </thead>
  <tbody>
    <tr><td align="left">nov. 2. tue</td><td align="center">10:00</td><td align="right">4h 40m</td></tr>
    <tr><td align="left">nov. 3. thu</td><td align="center">11:00</td><td align="right">4h</td></tr>
    <tr><td align="left">nov. 7. mon</td><td align="center">10:20</td><td align="right">4h 20m</td></tr>
    <tr><td align="left" colspan="2">total:</td><td align="right"><strong>13h</strong></td></tr>
  </tbody>
</table>
````````````````````````````````


multiple tables parsed correctly

```````````````````````````````` example Tables: 42
not a table, followed by a table

| col1 | col2 |
|------|------|

| col1  | col2  |
|-------|-------|
| data1 | data2 |

not a table, followed by a table

| col11 | col12 |
| col21 | col22 |
|-------|-------|
| data1 | data2 |

.
<p>not a table, followed by a table</p>
<table>
  <thead>
    <tr><th>col1</th><th>col2</th></tr>
  </thead>
  <tbody></tbody>
</table>
<table>
  <thead>
    <tr><th>col1</th><th>col2</th></tr>
  </thead>
  <tbody>
    <tr><td>data1</td><td>data2</td></tr>
  </tbody>
</table>
<p>not a table, followed by a table</p>
<table>
  <thead>
    <tr><th>col11</th><th>col12</th></tr>
    <tr><th>col21</th><th>col22</th></tr>
  </thead>
  <tbody>
    <tr><td>data1</td><td>data2</td></tr>
  </tbody>
</table>
````````````````````````````````


multi row/column

```````````````````````````````` example Tables: 43
| col11  | col12  | col13  |
| col21  | col22  | col23  |
| col31  | col32  | col33  |
|--------|--------|--------|
| data11 | data12 | data13 |
| data21 | data22 | data23 |
| data31 | data32 | data33 |

.
<table>
  <thead>
    <tr><th>col11</th><th>col12</th><th>col13</th></tr>
    <tr><th>col21</th><th>col22</th><th>col23</th></tr>
    <tr><th>col31</th><th>col32</th><th>col33</th></tr>
  </thead>
  <tbody>
    <tr><td>data11</td><td>data12</td><td>data13</td></tr>
    <tr><td>data21</td><td>data22</td><td>data23</td></tr>
    <tr><td>data31</td><td>data32</td><td>data33</td></tr>
  </tbody>
</table>
````````````````````````````````


keep cell whitespace

```````````````````````````````` example Tables: 44
| Abc | Def |
|-----|-----|
| 1   | 2   |

.
<table>
  <thead>
    <tr><th>Abc  </th><th> Def</th></tr>
  </thead>
  <tbody>
    <tr><td>1 </td><td> 2</td></tr>
  </tbody>
</table>
````````````````````````````````


Custom class name

```````````````````````````````` example Tables: 45
| Abc | Def |
|-----|-----|

.
<table class="table-class">
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody></tbody>
</table>
````````````````````````````````


in item

```````````````````````````````` example Tables: 46
* Add: live templates starting with `.`

  | Element       | Abbreviation    | Expansion   |
  |---------------|-----------------|-------------|
  | Abbreviation  | `.abbreviation` | `*[]:`      |
  | Code fence    | `.codefence`    | ``` ... ``` |
  | Explicit link | `.link`         | `[]()`      |

.
<ul>
  <li>
    <p>Add: live templates starting with <code>.</code></p>
    <table>
      <thead>
        <tr><th> Element       </th><th> Abbreviation    </th><th> Expansion                                               </th></tr>
      </thead>
      <tbody>
        <tr><td> Abbreviation  </td><td> <code>.abbreviation</code> </td><td> <code>*[]:</code>                                                 </td></tr>
        <tr><td> Code fence    </td><td> <code>.codefence</code>    </td><td> ``` ... ```                                       </td></tr>
        <tr><td> Explicit link </td><td> <code>.link</code>         </td><td> <code>[]()</code>                                                  </td></tr>
      </tbody>
    </table>
  </li>
</ul>
````````````````````````````````


real life table

```````````````````````````````` example Tables: 47
| Feature                                                                                         | Basic | Enhanced |
|:------------------------------------------------------------------------------------------------|:-----:|:--------:|
| Works with builds 143.2370 or newer, product version IDEA 15.0.6                                |   X   |    X     |
| Preview Tab so you can see what the rendered markdown will look like on GitHub.                 |   X   |    X     |
| Syntax highlighting                                                                             |   X   |    X     |
| Table syntax highlighting stripes rows and columns                                              |   X   |    X     |
| Support for Default and Darcula color schemes for preview tab                                   |   X   |    X     |
| Warning and Error Annotations to help you validate wiki link errors                             |   X   |    X     |
| Link address completion for wiki links                                                          |   X   |    X     |
| Quick Fixes for detected wiki link errors                                                       |   X   |    X     |
| GFM Task list extension `* [ ]` open task item and `* [x]` completed task item                  |   X   |    X     |
| Line markers, Find usages, Go To Declaration for rapid navigation to wiki link targets          |   X   |    X     |
| Markdown extensions configuration to customize markdown dialects                                |   X   |    X     |
| GitHub wiki support makes maintaining GitHub wiki pages easier.                                 |   X   |    X     |
| GitHub compatible id generation for headers so you can validate your anchor references          |   X   |    X     |
| Swing and JavaFX WebView based preview.                                                         |   X   |    X     |
| Supports **JavaFX with JetBrains JRE on OS X**                                                  |   X   |    X     |
| Supports Highlight JS in WebView preview                                                        |   X   |    X     |
| **Multi-line Image URLs for embedding [gravizo.com] UML diagrams into markdown**                |   X   |    X     |
| Live Templates for common markdown elements                                                     |   X   |    X     |
| **Enhanced Version Benefits**                                                                   |       |    X     |
| Split Editor with Preview or HTML Text modes to view both source and preview                    |       |    X     |
| Toolbar for fast access to frequent operations                                                  |       |    X     |
| Language Injections for fenced code, HTML, Jekyll front matter and multi-line URL content       |       |    X     |
| Code completions, refactoring, annotations and quick fixes to let you work faster               |       |    X     |
| Navigation support with Line markers, Find usages, Go To Declaration for rapid navigation       |       |    X     |
| Inspections to help you validate links, anchor refs, footnote refs                              |       |    X     |
| Complete GitHub wiki support for all links makes maintaining GitHub wiki pages a breeze         |       |    X     |
| Jekyll front matter recognition in markdown documents                                           |       |    X     |
| Emoji text to icon conversion using [Emoji Cheat Sheet] or GitHub emoji URLs                    |       |    X     |
| Wrap on typing and table formatting with column alignment                                       |       |    X     |
| Character display width used for wrapping and table formatting                                  |       |    X     |
| Structure view for Abbreviations, Headers, Tables, Footnotes, References and Document           |       |    X     |
| Document formatting with text wrapping, list renumbering, aranging of elements, etc.            |       |    X     |
| Table of Contents generation for any markdown parser, with many style options                   |       |    X     |
| **As you type automation**                                                                      |       |    X     |
| Double of bold/emphasis markers and remove inserted ones if a space is typed                    |       |    X     |
| Wrap text blocks to margins and indentation                                                     |       |    X     |
| ATX headers to match trailing `#` marker                                                        |       |    X     |
| Setext headers to match marker length to text                                                   |       |    X     |
| Format tables to pad column width, column alignment and spanning columns                        |       |    X     |
| Auto insert empty table row on <kbd>ENTER</kbd>                                                 |       |    X     |
| Auto delete empty table row/column on <kbd>BACKSPACE</kbd>                                      |       |    X     |
| Auto insert table column when typing before first column or after last column of table          |       |    X     |
| Actions to insert: table, row or column; delete: row or column                                  |       |    X     |
| Auto insert list item on <kbd>ENTER</kbd>                                                       |       |    X     |
| Auto delete empty list item on <kbd>ENTER</kbd>                                                 |       |    X     |
| Auto delete empty list item on <kbd>BACKSPACE</kbd>                                             |       |    X     |
| Indent or un-indent list item toolbar buttons and actions                                       |       |    X     |
| **Code Completions**                                                                            |       |    X     |
| Absolute link address completions using https:// and file:// formats                            |       |    X     |
| Explicit and Image links are GitHub wiki aware                                                  |       |    X     |
| GitHub Issue # Completions after `issues/` link address and in text                             |       |    X     |
| GitHub special links: Issues, Pull requests, Graphs, and Pulse.                                 |       |    X     |
| Link address completions for non-markdown files                                                 |       |    X     |
| Emoji text shortcuts completion                                                                 |       |    X     |
| Java class, field and method completions in inline code elements                                |       |    X     |
| **Intention Actions**                                                                           |       |    X     |
| Change between relative and absolute https:// link addresses via intention action               |       |    X     |
| Change between wiki links and explicit link                                                     |       |    X     |
| Intentions for links, wiki links, references and headers                                        |       |    X     |
| Intention to format Setext Header marker to match marker length to text                         |       |    X     |
| Intention to swap Setext/Atx header format                                                      |       |    X     |
| Update table of contents quick fix intention                                                    |       |    X     |
| Intention to edit Table of Contents style options dialog with preview                           |       |    X     |
| **Refactoring**                                                                                 |       |    X     |
| Automatic change from wiki link to explicit link when link target file is moved out of the wiki |       |    X     |
| File move refactoring of contained links. This completes the refactoring feature set            |       |    X     |
| Refactoring for /, https:// and file:// absolute link addresses to project files                |       |    X     |
| Refactoring of header text with update to referencing anchor link references                    |       |    X     |
| Anchor link reference refactoring with update to referenced header text                         |       |    X     |

.
<table>
  <thead>
    <tr><th align="left">Feature</th><th align="center">Basic</th><th align="center">Enhanced</th></tr>
  </thead>
  <tbody>
    <tr><td align="left">Works with builds 143.2370 or newer, product version IDEA 15.0.6</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Preview Tab so you can see what the rendered markdown will look like on GitHub.</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Syntax highlighting</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Table syntax highlighting stripes rows and columns</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Support for Default and Darcula color schemes for preview tab</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Warning and Error Annotations to help you validate wiki link errors</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Link address completion for wiki links</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Quick Fixes for detected wiki link errors</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">GFM Task list extension <code>* [ ]</code> open task item and <code>* [x]</code> completed task item</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Line markers, Find usages, Go To Declaration for rapid navigation to wiki link targets</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Markdown extensions configuration to customize markdown dialects</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">GitHub wiki support makes maintaining GitHub wiki pages easier.</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">GitHub compatible id generation for headers so you can validate your anchor references</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Swing and JavaFX WebView based preview.</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Supports <strong>JavaFX with JetBrains JRE on OS X</strong></td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Supports Highlight JS in WebView preview</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left"><strong>Multi-line Image URLs for embedding [gravizo.com] UML diagrams into markdown</strong></td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Live Templates for common markdown elements</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left"><strong>Enhanced Version Benefits</strong></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Split Editor with Preview or HTML Text modes to view both source and preview</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Toolbar for fast access to frequent operations</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Language Injections for fenced code, HTML, Jekyll front matter and multi-line URL content</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Code completions, refactoring, annotations and quick fixes to let you work faster</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Navigation support with Line markers, Find usages, Go To Declaration for rapid navigation</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Inspections to help you validate links, anchor refs, footnote refs</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Complete GitHub wiki support for all links makes maintaining GitHub wiki pages a breeze</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Jekyll front matter recognition in markdown documents</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Emoji text to icon conversion using [Emoji Cheat Sheet] or GitHub emoji URLs</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Wrap on typing and table formatting with column alignment</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Character display width used for wrapping and table formatting</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Structure view for Abbreviations, Headers, Tables, Footnotes, References and Document</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Document formatting with text wrapping, list renumbering, aranging of elements, etc.</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Table of Contents generation for any markdown parser, with many style options</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left"><strong>As you type automation</strong></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Double of bold/emphasis markers and remove inserted ones if a space is typed</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Wrap text blocks to margins and indentation</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    ATX headers to match trailing <code>#</code> marker</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Setext headers to match marker length to text</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Format tables to pad column width, column alignment and spanning columns</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Auto insert empty table row on <kbd>ENTER</kbd></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Auto delete empty table row/column on <kbd>BACKSPACE</kbd></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Auto insert table column when typing before first column or after last column of table</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Actions to insert: table, row or column; delete: row or column</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Auto insert list item on <kbd>ENTER</kbd></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Auto delete empty list item on <kbd>ENTER</kbd></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Auto delete empty list item on <kbd>BACKSPACE</kbd></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Indent or un-indent list item toolbar buttons and actions</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left"><strong>Code Completions</strong></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Absolute link address completions using https:// and file:// formats</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Explicit and Image links are GitHub wiki aware</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    GitHub Issue # Completions after <code>issues/</code> link address and in text</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    GitHub special links: Issues, Pull requests, Graphs, and Pulse.</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Link address completions for non-markdown files</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Emoji text shortcuts completion</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Java class, field and method completions in inline code elements</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left"><strong>Intention Actions</strong></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Change between relative and absolute https:// link addresses via intention action</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Change between wiki links and explicit link</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Intentions for links, wiki links, references and headers</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Intention to format Setext Header marker to match marker length to text</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Intention to swap Setext/Atx header format</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Update table of contents quick fix intention</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Intention to edit Table of Contents style options dialog with preview</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left"><strong>Refactoring</strong></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Automatic change from wiki link to explicit link when link target file is moved out of the wiki</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    File move refactoring of contained links. This completes the refactoring feature set</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Refactoring for /, https:// and file:// absolute link addresses to project files</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Refactoring of header text with update to referencing anchor link references</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Anchor link reference refactoring with update to referenced header text</td><td align="center"></td><td align="center">X</td></tr>
  </tbody>
</table>
````````````````````````````````


real life table

```````````````````````````````` example(Tables: 48) options(nbsp)
| Feature                                                                                                                 | Basic | Enhanced |
|:------------------------------------------------------------------------------------------------------------------------|:-----:|:--------:|
| Works with builds 143.2370 or newer, product version IDEA 15.0.6                                                        |   X   |    X     |
| Preview Tab so you can see what the rendered markdown will look like on GitHub.                                         |   X   |    X     |
| Syntax highlighting                                                                                                     |   X   |    X     |
| Table syntax highlighting stripes rows and columns                                                                      |   X   |    X     |
| Support for Default and Darcula color schemes for preview tab                                                           |   X   |    X     |
| Warning and Error Annotations to help you validate wiki link errors                                                     |   X   |    X     |
| Link address completion for wiki links                                                                                  |   X   |    X     |
| Quick Fixes for detected wiki link errors                                                                               |   X   |    X     |
| GFM Task list extension `* [ ]` open task item and `* [x]` completed task item                                          |   X   |    X     |
| Line markers, Find usages, Go To Declaration for rapid navigation to wiki link targets                                  |   X   |    X     |
| Markdown extensions configuration to customize markdown dialects                                                        |   X   |    X     |
| GitHub wiki support makes maintaining GitHub wiki pages easier.                                                         |   X   |    X     |
| GitHub compatible id generation for headers so you can validate your anchor references                                  |   X   |    X     |
| Swing and JavaFX WebView based preview.                                                                                 |   X   |    X     |
| Supports **JavaFX with JetBrains JRE on OS X**                                                                          |   X   |    X     |
| Supports Highlight JS in WebView preview                                                                                |   X   |    X     |
| **Multi-line Image URLs for embedding [gravizo.com] UML diagrams into markdown**                                        |   X   |    X     |
| Live Templates for common markdown elements                                                                             |   X   |    X     |
| **Enhanced Version Benefits**                                                                                           |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Split Editor with Preview or HTML Text modes to view both source and preview                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Toolbar for fast access to frequent operations                                                  |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Language Injections for fenced code, HTML, Jekyll front matter and multi-line URL content       |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Code completions, refactoring, annotations and quick fixes to let you work faster               |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Navigation support with Line markers, Find usages, Go To Declaration for rapid navigation       |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Inspections to help you validate links, anchor refs, footnote refs                              |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Complete GitHub wiki support for all links makes maintaining GitHub wiki pages a breeze         |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Jekyll front matter recognition in markdown documents                                           |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Emoji text to icon conversion using [Emoji Cheat Sheet] or GitHub emoji URLs                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Wrap on typing and table formatting with column alignment                                       |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Character display width used for wrapping and table formatting                                  |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Structure view for Abbreviations, Headers, Tables, Footnotes, References and Document           |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Document formatting with text wrapping, list renumbering, aranging of elements, etc.            |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Table of Contents generation for any markdown parser, with many style options                   |       |    X     |
| **As you type automation**                                                                                              |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Double of bold/emphasis markers and remove inserted ones if a space is typed                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Wrap text blocks to margins and indentation                                                     |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;ATX headers to match trailing `#` marker                                                        |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Setext headers to match marker length to text                                                   |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Format tables to pad column width, column alignment and spanning columns                        |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto insert empty table row on <kbd>ENTER</kbd>                                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty table row/column on <kbd>BACKSPACE</kbd>                                      |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto insert table column when typing before first column or after last column of table          |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Actions to insert: table, row or column; delete: row or column                                  |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto insert list item on <kbd>ENTER</kbd>                                                       |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty list item on <kbd>ENTER</kbd>                                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty list item on <kbd>BACKSPACE</kbd>                                             |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Indent or un-indent list item toolbar buttons and actions                                       |       |    X     |
| **Code Completions**                                                                                                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Absolute link address completions using https:// and file:// formats                            |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Explicit and Image links are GitHub wiki aware                                                  |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;GitHub Issue # Completions after `issues/` link address and in text                             |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;GitHub special links: Issues, Pull requests, Graphs, and Pulse.                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Link address completions for non-markdown files                                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Emoji text shortcuts completion                                                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Java class, field and method completions in inline code elements                                |       |    X     |
| **Intention Actions**                                                                                                   |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Change between relative and absolute https:// link addresses via intention action               |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Change between wiki links and explicit link                                                     |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Intentions for links, wiki links, references and headers                                        |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Intention to format Setext Header marker to match marker length to text                         |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Intention to swap Setext/Atx header format                                                      |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Update table of contents quick fix intention                                                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Intention to edit Table of Contents style options dialog with preview                           |       |    X     |
| **Refactoring**                                                                                                         |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Automatic change from wiki link to explicit link when link target file is moved out of the wiki |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;File move refactoring of contained links. This completes the refactoring feature set            |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Refactoring for /, https:// and file:// absolute link addresses to project files                |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Refactoring of header text with update to referencing anchor link references                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Anchor link reference refactoring with update to referenced header text                         |       |    X     |

.
<table>
  <thead>
    <tr><th align="left">Feature</th><th align="center">Basic</th><th align="center">Enhanced</th></tr>
  </thead>
  <tbody>
    <tr><td align="left">Works with builds 143.2370 or newer, product version IDEA 15.0.6</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Preview Tab so you can see what the rendered markdown will look like on GitHub.</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Syntax highlighting</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Table syntax highlighting stripes rows and columns</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Support for Default and Darcula color schemes for preview tab</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Warning and Error Annotations to help you validate wiki link errors</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Link address completion for wiki links</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Quick Fixes for detected wiki link errors</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">GFM Task list extension <code>* [ ]</code> open task item and <code>* [x]</code> completed task item</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Line markers, Find usages, Go To Declaration for rapid navigation to wiki link targets</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Markdown extensions configuration to customize markdown dialects</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">GitHub wiki support makes maintaining GitHub wiki pages easier.</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">GitHub compatible id generation for headers so you can validate your anchor references</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Swing and JavaFX WebView based preview.</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Supports <strong>JavaFX with JetBrains JRE on OS X</strong></td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Supports Highlight JS in WebView preview</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left"><strong>Multi-line Image URLs for embedding [gravizo.com] UML diagrams into markdown</strong></td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left">Live Templates for common markdown elements</td><td align="center">X</td><td align="center">X</td></tr>
    <tr><td align="left"><strong>Enhanced Version Benefits</strong></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Split Editor with Preview or HTML Text modes to view both source and preview</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Toolbar for fast access to frequent operations</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Language Injections for fenced code, HTML, Jekyll front matter and multi-line URL content</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Code completions, refactoring, annotations and quick fixes to let you work faster</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Navigation support with Line markers, Find usages, Go To Declaration for rapid navigation</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Inspections to help you validate links, anchor refs, footnote refs</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Complete GitHub wiki support for all links makes maintaining GitHub wiki pages a breeze</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Jekyll front matter recognition in markdown documents</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Emoji text to icon conversion using [Emoji Cheat Sheet] or GitHub emoji URLs</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Wrap on typing and table formatting with column alignment</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Character display width used for wrapping and table formatting</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Structure view for Abbreviations, Headers, Tables, Footnotes, References and Document</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Document formatting with text wrapping, list renumbering, aranging of elements, etc.</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Table of Contents generation for any markdown parser, with many style options</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left"><strong>As you type automation</strong></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Double of bold/emphasis markers and remove inserted ones if a space is typed</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Wrap text blocks to margins and indentation</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    ATX headers to match trailing <code>#</code> marker</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Setext headers to match marker length to text</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Format tables to pad column width, column alignment and spanning columns</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Auto insert empty table row on <kbd>ENTER</kbd></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Auto delete empty table row/column on <kbd>BACKSPACE</kbd></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Auto insert table column when typing before first column or after last column of table</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Actions to insert: table, row or column; delete: row or column</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Auto insert list item on <kbd>ENTER</kbd></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Auto delete empty list item on <kbd>ENTER</kbd></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Auto delete empty list item on <kbd>BACKSPACE</kbd></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Indent or un-indent list item toolbar buttons and actions</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left"><strong>Code Completions</strong></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Absolute link address completions using https:// and file:// formats</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Explicit and Image links are GitHub wiki aware</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    GitHub Issue # Completions after <code>issues/</code> link address and in text</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    GitHub special links: Issues, Pull requests, Graphs, and Pulse.</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Link address completions for non-markdown files</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Emoji text shortcuts completion</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Java class, field and method completions in inline code elements</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left"><strong>Intention Actions</strong></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Change between relative and absolute https:// link addresses via intention action</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Change between wiki links and explicit link</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Intentions for links, wiki links, references and headers</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Intention to format Setext Header marker to match marker length to text</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Intention to swap Setext/Atx header format</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Update table of contents quick fix intention</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Intention to edit Table of Contents style options dialog with preview</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left"><strong>Refactoring</strong></td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Automatic change from wiki link to explicit link when link target file is moved out of the wiki</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    File move refactoring of contained links. This completes the refactoring feature set</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Refactoring for /, https:// and file:// absolute link addresses to project files</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Refactoring of header text with update to referencing anchor link references</td><td align="center"></td><td align="center">X</td></tr>
    <tr><td align="left">    Anchor link reference refactoring with update to referenced header text</td><td align="center"></td><td align="center">X</td></tr>
  </tbody>
</table>
````````````````````````````````


Table with `th` in `tbody`

```````````````````````````````` example Tables: 49
| Result | Description        | Entity Name |
|--------|--------------------|-------------|
|        | non-breaking space | &nbsp;      |
| <      | less than          | &lt;        |
| >      | greater than       | &gt;        |
| &      | ampersand          | &amp;       |

.
<table>
  <tbody>
    <tr>
      <th style="padding-left:0">Result</th>
      <th>Description</th>
      <th>Entity Name</th>
    </tr>
    <tr>
      <td style="padding-left:0"></td>
      <td>non-breaking space</td>
      <td>&amp;nbsp;</td>
    </tr>
    <tr>
      <td style="padding-left:0">&lt;</td>
      <td>less than</td>
      <td>&amp;lt;</td>
    </tr>
    <tr>
      <td style="padding-left:0">&gt;</td>
      <td>greater than</td>
      <td>&amp;gt;</td>
    </tr>
    <tr>
      <td style="padding-left:0">&amp;</td>
      <td>ampersand</td>
      <td>&amp;amp;</td>
    </tr>
  </tbody>
</table>
````````````````````````````````


tables with row span cells

```````````````````````````````` example Tables: 50
| ID | e-mail   | Name     | Visits | Trials |                 Points                 |||| Discount On Next License |
| ID | e-mail   | Name     | Visits | Trials | Earned | Complimentary | Used | Available | Discount On Next License |
|----|----------|----------|-------:|-------:|-------:|--------------:|-----:|----------:|:------------------------:|
| 2  | test 1 1 | test 1 2 |     32 |      0 |      0 |             1 |    0 |         1 |           10%            |
| 24 | test 2 1 | test 2 2 |      1 |      0 |      0 |             1 |    0 |         1 |           10%            |

.
<table class="table table-condensed table-responsive">
  <thead>
    <tr>
      <th rowspan="2">ID</th>
      <th rowspan="2">e-mail</th>
      <th rowspan="2">Name</th>
      <th class="text-right" rowspan="2">Visits</th>
      <th class="text-right" rowspan="2">Trials</th>
      <th class="text-center" colspan="4">Points</th>
      <th class="text-center" rowspan="2">Discount On<br>Next License</th>
    </tr>
    <tr>
      <th class="text-right">Earned</th>
      <th class="text-right">Complimentary</th>
      <th class="text-right">Used</th>
      <th class="text-right">Available</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>2</td>
      <td>test 1 1</td>
      <td>test 1 2</td>
      <td class="text-right">32</td>
      <td class="text-right">0</td>
      <td class="text-right">0</td>
      <td class="text-right">1</td>
      <td class="text-right">0</td>
      <td class="text-right">1</td>
      <td class="text-right"> 10%</td>
    </tr>
    <tr>
      <td>24</td>
      <td>test 2 1</td>
      <td>test 2 2</td>
      <td class="text-right">1</td>
      <td class="text-right">0</td>
      <td class="text-right">0</td>
      <td class="text-right">1</td>
      <td class="text-right">0</td>
      <td class="text-right">1</td>
      <td class="text-right"> 10%</td>
    </tr>
  </tbody>
</table>
````````````````````````````````


## Definition Lists

a definition in a block quote

```````````````````````````````` example Definition Lists: 1
> Definition Term
> :   definition item

.
<blockquote>
  <dl>
    <dt>Definition Term</dt>
    <dd>definition item</dd>
  </dl>
</blockquote>
````````````````````````````````


simple list

```````````````````````````````` example Definition Lists: 2
Definition Term
:   definition item

.
<dl>
  <dt>Definition Term</dt>
  <dd>definition item</dd>
</dl>
````````````````````````````````


A simple definition list:

```````````````````````````````` example Definition Lists: 3
Term 1
:   Definition 1

Term 2
:   Definition 2

.
<dl>
  <dt>Term 1</dt>
  <dd>Definition 1</dd>
  <dt>Term 2</dt>
  <dd>Definition 2</dd>
</dl>
````````````````````````````````


With multiple terms:

```````````````````````````````` example Definition Lists: 4
Term 1
Term 2
:   Definition 1

Term 3
Term 4
:   Definition 2

.
<dl>
  <dt>Term 1</dt>
  <dt>Term 2</dt>
  <dd>Definition 1</dd>
  <dt>Term 3</dt>
  <dt>Term 4</dt>
  <dd>Definition 2</dd>
</dl>
````````````````````````````````


With multiple definitions:

```````````````````````````````` example Definition Lists: 5
Term 1
:   Definition 1
:   Definition 2

Term 2
:   Definition 3
:   Definition 4

.
<dl>
  <dt>Term 1</dt>
  <dd>Definition 1</dd>
  <dd>Definition 2</dd>
  <dt>Term 2</dt>
  <dd>Definition 3</dd>
  <dd>Definition 4</dd>
</dl>
````````````````````````````````


With multiple lines per definition:

```````````````````````````````` example Definition Lists: 6
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
<dl>
  <dt>Term 1</dt>
  <dd>Definition 1 line 1 ...
  Definition 1 line 2</dd>
  <dd>Definition 2 line 1 ...
  Definition 2 line 2</dd>
  <dt>Term 2</dt>
  <dd>Definition 3 line 2 ...
  Definition 3 line 2</dd>
  <dd>Definition 4 line 2 ...
  Definition 4 line 2</dd>
</dl>
````````````````````````````````


With paragraphs:

```````````````````````````````` example Definition Lists: 7
Term 1

:   Definition 1 (paragraph)

Term 2

:   Definition 2 (paragraph)

.
<dl>
  <dt>Term 1</dt>
  <dd>
  <p>Definition 1 (paragraph)</p>
  </dd>
  <dt>Term 2</dt>
  <dd>
  <p>Definition 2 (paragraph)</p>
  </dd>
</dl>
````````````````````````````````


With multiple paragraphs:

```````````````````````````````` example Definition Lists: 8
Term 1

:   Definition 1 paragraph 1 line 1 ...
    Definition 1 paragraph 1 line 2

    Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2

Term 2

:   Definition 1 paragraph 1 line 1 ...
    Definition 1 paragraph 1 line 2 (lazy)

    Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2 (lazy)

.
<dl>
  <dt>Term 1</dt>
  <dd>
  <p>Definition 1 paragraph 1 line 1 ...
  Definition 1 paragraph 1 line 2</p>
  <p>Definition 1 paragraph 2 line 1 ...
  Definition 1 paragraph 2 line 2</p>
  </dd>
  <dt>Term 2</dt>
  <dd>
  <p>Definition 1 paragraph 1 line 1 ...
  Definition 1 paragraph 1 line 2 (lazy)</p>
  <p>Definition 1 paragraph 2 line 1 ...
  Definition 1 paragraph 2 line 2 (lazy)</p>
  </dd>
</dl>
````````````````````````````````


A mix:

```````````````````````````````` example Definition Lists: 9
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
<dl>
  <dt>Term 1</dt>
  <dt>Term 2</dt>
  <dd>
  <p>Definition 1 paragraph 1 line 1 ...
  Definition 1 paragraph 1 line 2 (lazy)</p>
  <p>Definition 1 paragraph 2 line 1 ...
  Definition 1 paragraph 2 line 2</p>
  </dd>
  <dd>
  <p>Definition 2 paragraph 1 line 1 ...
  Definition 2 paragraph 1 line 2 (lazy)</p>
  </dd>
  <dt>Term 3</dt>
  <dd>
  <p>Definition 3 (no paragraph)</p>
  </dd>
  <dd>
  <p>Definition 4 (no paragraph)</p>
  </dd>
  <dd>
  <p>Definition 5 line 1 ...
  Definition 5 line 2 (no paragraph)</p>
  </dd>
  <dd>
  <p>Definition 6 paragraph 1 line 1 ...
  Definition 6 paragraph 1 line 2</p>
  </dd>
  <dd>
  <p>Definition 7 (no paragraph)</p>
  </dd>
  <dd>
  <p>Definition 8 paragraph 1 line 1 (forced paragraph) ...
  Definition 8 paragraph 1 line 2</p>
  <p>Definition 8 paragraph 2 line 1</p>
  </dd>
  <dt>Term 4</dt>
  <dd>
  <p>Definition 9 paragraph 1 line 1 (forced paragraph) ...
  Definition 9 paragraph 1 line 2</p>
  <p>Definition 9 paragraph 2 line 1</p>
  </dd>
  <dd>
  <p>Definition 10 (no paragraph)</p>
  </dd>
</dl>
````````````````````````````````


inlines allowed

```````````````````````````````` example Definition Lists: 10
Definition **Term**
:   definition `item`

.
<dl>
  <dt>Definition <strong>Term</strong></dt>
  <dd>definition <code>item</code></dd>
</dl>
````````````````````````````````


inlines will be split

```````````````````````````````` example Definition Lists: 11
Definition **Term
Another** Definition Term
:   definition `item`

.
<dl>
  <dt>Definition **Term</dt>
  <dt>Another** Definition Term</dt>
  <dd>definition <code>item</code></dd>
</dl>
````````````````````````````````


don't include preceding blank lines

```````````````````````````````` example Definition Lists: 12
* bullet item

Definition Term
:   definition item

.
<ul>
  <li>bullet item</li>
</ul>
<dl>
  <dt>Definition Term</dt>
  <dd>definition item</dd>
</dl>
````````````````````````````````


nested elements allowed

```````````````````````````````` example Definition Lists: 13
Definition **Term**
:   definition `item`

    paragraph
    * bullet item
      * sub item
    > 
    > block quote

.
<dl>
  <dt>Definition <strong>Term</strong></dt>
  <dd>definition <code>item</code>
    <p>paragraph</p>
    <ul>
      <li>bullet item
        <ul>
          <li>sub item</li>
        </ul>
      </li>
    </ul>
    <blockquote>
      <p>block quote</p>
    </blockquote>
  </dd>
</dl>
````````````````````````````````


With disparate looseness with auto-loose

```````````````````````````````` example Definition Lists: 14
Term 1

:   Definition 1 (paragraph)

Term 2

:   Definition 2 (paragraph)

.
<dl>
  <dt>Term 1</dt>
  <dd>
  <p>Definition 1 (paragraph)</p>
  </dd>
  <dt>Term 2</dt>
  <dd>
  <p>Definition 2 (paragraph)</p>
  </dd>
</dl>
````````````````````````````````


With disparate looseness without auto-loose

```````````````````````````````` example Definition Lists: 15
Term 1

:   Definition 1 (paragraph)

Term 2
:   Definition 2 (paragraph)

.
<dl>
  <dt>Term 1</dt>
  <dd>
  <p>Definition 1 (paragraph)</p>
  </dd>
  <dt>Term 2</dt>
  <dd>Definition 2 (paragraph)</dd>
</dl>
````````````````````````````````


```````````````````````````````` example Definition Lists: 16
Definition Term
:   Definition of above term
:   Another definition of above term

.
<dl>
  <dt>Definition Term</dt>
  <dd>Definition of above term</dd>
  <dd>Another definition of above term</dd>
</dl>
````````````````````````````````


```````````````````````````````` example Definition Lists: 17
Definition Term
:   Definition of above term
:   Another definition of above term

.
<dl>
  <dt>Definition Term</dt>
  <dd>Definition of above term</dd>
  <dd>Another definition of above term</dd>
</dl>
````````````````````````````````


```````````````````````````````` example Definition Lists: 18
Definition Term
:   Definition of above term
    Another definition of above term

.
<dl>
  <dt>Definition Term</dt>
  <dd>Definition of above term
  Another definition of above term</dd>
</dl>
````````````````````````````````


## Fenced Code

Change language class prefix

```````````````````````````````` example Fenced Code: 1
```text
plain text
```

.
<pre><code class="text">plain text
</code></pre>
````````````````````````````````


empty, no info

```````````````````````````````` example Fenced Code: 2
```

```

.
<pre><code>
</code></pre>
````````````````````````````````


empty, no info, blank line follows

```````````````````````````````` example Fenced Code: 3
```

```

.
<pre><code>
</code></pre>
````````````````````````````````


empty, info

```````````````````````````````` example Fenced Code: 4
```info

```

.
<pre><code class="language-info">
</code></pre>
````````````````````````````````


empty, info, blank line follows

```````````````````````````````` example Fenced Code: 5
```info

```

.
<pre><code class="language-info">
</code></pre>
````````````````````````````````


```````````````````````````````` example Fenced Code: 6
`````java
some back ticks in the ````code````
`````

.
<pre><code class="java">some back ticks in the ````code````
</code></pre>
````````````````````````````````


```````````````````````````````` example Fenced Code: 7
> `````java
>     some back ticks in the ````code````
> `````

.
<blockquote>
<pre><code class="java">    some back ticks in the ````code````
</code></pre>
</blockquote>
````````````````````````````````


```````````````````````````````` example Fenced Code: 8
* item
  > 
  > `````markdown
  > some back ticks in the ````code````
  > * item
  >   * sub-item
  >     
  > `````

.
<ul>
  <li>
    <p>item</p>
    <blockquote>
      <pre><code class="markdown">some back ticks in the ````code````
* item
  * sub-item
    
</code></pre>
    </blockquote>
  </li>
</ul>
````````````````````````````````


```````````````````````````````` example Fenced Code: 9
* item
  >     
  >     some back ticks in the ````code````
  >     * item
  >       * sub-item

.
<ul>
  <li>
    <p>item</p>
    <blockquote>
      <pre><code>some back ticks in the ````code````
* item
  * sub-item
</code></pre>
    </blockquote>
  </li>
</ul>
````````````````````````````````


code with emphasis preserved

```````````````````````````````` example(Fenced Code: 10) options(code-emphasis)
```html
<html>
<body>
<div>
**strong**
*emphasis*
~~del~~
~sub~
^sub^
++sub++
</div>
</body>
</html>
```

.
<pre><code class="html">&lt;html&gt;
&lt;body&gt;
&lt;div&gt;
<strong>strong</strong>
<em>emphasis</em>
<del>del</del>
<sub>sub</sub>
<sup>sub</sup>
<ins>sub</ins>
&lt;/div&gt;
&lt;/body&gt;
&lt;/html&gt;
</code></pre>
````````````````````````````````


code with emphasis not preserved

```````````````````````````````` example Fenced Code: 11
```html
<html>
<body>
<div>
strong
emphasis
del
sub
sub
sub
</div>
</body>
</html>
```

.
<pre><code class="html">&lt;html&gt;
&lt;body&gt;
&lt;div&gt;
<strong>strong</strong>
<em>emphasis</em>
<del>del</del>
<sub>sub</sub>
<sup>sub</sup>
<ins>sub</ins>
&lt;/div&gt;
&lt;/body&gt;
&lt;/html&gt;
</code></pre>
````````````````````````````````


## Emoji Shortcuts

```````````````````````````````` example Emoji Shortcuts: 1
:heavy_check_mark:

.
<g-emoji alias="heavy_check_mark" fallback-src="https://assets-cdn.github.com/images/icons/emoji/unicode/2714.png" ios-version="6.0">✔️</g-emoji>
````````````````````````````````


```````````````````````````````` example Emoji Shortcuts: 2
:heavy_check_mark:

.
<g-emoji fallback-src="https://assets-cdn.github.com/images/icons/emoji/unicode/2714.png" ios-version="6.0">✔️</g-emoji>
````````````````````````````````


```````````````````````````````` example Emoji Shortcuts: 3
:heavy_check_mark:

.
<img src="file:/Users/vlad/Library/Application Support/IdeaIC2017-1-EAP/idea-multimarkdown/emojis/heavy_check_mark.png" alt="emoji symbols:heavy_check_mark" height="20" width="20" align="absmiddle" />
````````````````````````````````


```````````````````````````````` example Emoji Shortcuts: 4
:heavy_check_mark:

.
<img src="https://assets-cdn.github.com/images/icons/emoji/unicode/2714.png" alt="emoji symbols:heavy_check_mark" height="20" width="20" align="absmiddle" />
````````````````````````````````


## Issue Tests

Infinite loop on unwrap

```````````````````````````````` example Issue Tests: 1
:heavy_check_mark:

.
<g-emoji alias="heavy_check_mark" fallback-src="https://assets-cdn.github.com/images/icons/emoji/unicode/2714.png" ios-version="6.0">✔️</g-emoji>
````````````````````````````````


Headings need blank line

```````````````````````````````` example Issue Tests: 2
Text

Heading
=======

.
<p>Text&nbsp;</p>
<h1>Heading</h1>
````````````````````````````````


Headings need blank line

```````````````````````````````` example(Issue Tests: 3) options(nbsp)
Text&nbsp;

Heading
=======

.
<p>Text&nbsp;</p>
<h1>Heading</h1>
````````````````````````````````


Headings need blank line

```````````````````````````````` example Issue Tests: 4
**Attention!** Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple [form](https://tech.yandex.com/key/form.xml?service=trnsl).

.
<div class="warning">
  <strong>Attention!&nbsp;</strong>Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple <a target="_blank" href="https://tech.yandex.com/key/form.xml?service=trnsl">form</a>.
</div>
````````````````````````````````


Headings need blank line

```````````````````````````````` example Issue Tests: 5
**Attention!** Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple [form](https://tech.yandex.com/key/form.xml?service=trnsl).

.
<div class="warning">
  <strong>Attention!  &nbsp;</strong>Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple <a target="_blank" href="https://tech.yandex.com/key/form.xml?service=trnsl">form</a>.
</div>
````````````````````````````````


Headings need blank line

```````````````````````````````` example(Issue Tests: 6) options(nbsp)
**Attention!**&nbsp;Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple [form](https://tech.yandex.com/key/form.xml?service=trnsl).

.
<div class="warning">
  <strong>Attention!  &nbsp;</strong>Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple <a target="_blank" href="https://tech.yandex.com/key/form.xml?service=trnsl">form</a>.
</div>
````````````````````````````````


Headings need blank line

```````````````````````````````` example(Issue Tests: 7) options(nbsp)
**Attention!**&nbsp;Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple [form](https://tech.yandex.com/key/form.xml?service=trnsl).

.
<div class="warning">
  <strong>Attention!&nbsp;</strong>Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple <a target="_blank" href="https://tech.yandex.com/key/form.xml?service=trnsl">form</a>.
</div>
````````````````````````````````


handle `pre` without `code`

```````````````````````````````` example Issue Tests: 8
```
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// http://www.allcolor.org/YaHPConverter/
import org.allcolor.yahp.converter.CYaHPConverter;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer;

public class HtmlToPdf_yahp {
  public  static void main(String ... args ) throws Exception {
    htmlToPdfFile();
  }

  public static void htmlToPdfFile() throws Exception {
    CYaHPConverter converter = new CYaHPConverter();
    File fout = new File("c:/temp/x.pdf");
    FileOutputStream out = new FileOutputStream(fout);
    Map properties = new HashMap();
    List headerFooterList = new ArrayList();

    String str = "<HTML><HEAD></HEAD><BODY><H1>Testing</H1><FORM>" +
                 "check : <INPUT TYPE='checkbox' checked=checked/><br/>"   +
                 "</FORM></BODY></HTML>";

    properties.put(IHtmlToPdfTransformer.PDF_RENDERER_CLASS,
                   IHtmlToPdfTransformer.FLYINGSAUCER_PDF_RENDERER);
    //properties.put(IHtmlToPdfTransformer.FOP_TTF_FONT_PATH, fontPath);
    converter.convertToPdf(str,
          IHtmlToPdfTransformer.A4P,
          headerFooterList,
          "file:///temp/", // root for relative external CSS and IMAGE
          out,
          properties);
    out.flush();
    out.close();
  }
}
```

.
<div class="howtocode"><pre>import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// http://www.allcolor.org/YaHPConverter/
import org.allcolor.yahp.converter.CYaHPConverter;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer;

public class HtmlToPdf_yahp {
  public  static void main(String ... args ) throws Exception {
    htmlToPdfFile();
  }

  public static void htmlToPdfFile() throws Exception {
    CYaHPConverter converter = new CYaHPConverter();
    File fout = new File("c:/temp/x.pdf");
    FileOutputStream out = new FileOutputStream(fout);
    Map properties = new HashMap();
    List headerFooterList = new ArrayList();

    String str = "&lt;HTML&gt;&lt;HEAD&gt;&lt;/HEAD&gt;&lt;BODY&gt;&lt;H1&gt;Testing&lt;/H1&gt;&lt;FORM&gt;" +
                 "check : &lt;INPUT TYPE='checkbox' checked=checked/&gt;&lt;br/&gt;"   +
                 "&lt;/FORM&gt;&lt;/BODY&gt;&lt;/HTML&gt;";

    properties.put(IHtmlToPdfTransformer.PDF_RENDERER_CLASS,
                   IHtmlToPdfTransformer.FLYINGSAUCER_PDF_RENDERER);
    //properties.put(IHtmlToPdfTransformer.FOP_TTF_FONT_PATH, fontPath);
    converter.convertToPdf(str,
          IHtmlToPdfTransformer.A4P,
          headerFooterList,
          "file:///temp/", // root for relative external CSS and IMAGE
          out,
          properties);
    out.flush();
    out.close();
  }
}
</pre></div>
````````````````````````````````


```````````````````````````````` example Issue Tests: 9
priyanka • [3 years ago](http://www.rgagnon.com/javadetails/java-convert-html-to-pdf-using-yahp.html#comment-1055568391 "Monday, September 23, 2013 5:49 AM")

Though i am not getting any error ...no pdf is getting created

see more
* [2](# "")
* •
* [Reply](#)
* •
* Share ›
  * Twitter
  * Facebook
  * 

* 

.
<div class="post-body">
  <header class="comment__header">
<span class="post-byline">
<span class="author">priyanka</span>

</span>

    <span class="post-meta">
<span class="bullet time-ago-bullet" aria-hidden="true">•</span>

<a href="http://www.rgagnon.com/javadetails/java-convert-html-to-pdf-using-yahp.html#comment-1055568391" data-role="relative-time" class="time-ago" title="Monday, September 23, 2013 5:49 AM">3 years ago</a>
</span>

  </header>

  <div class="post-body-inner">
    <div class="post-message-container" data-role="message-container">
      <div class="publisher-anchor-color" data-role="message-content">
        <div class="post-message " data-role="message" dir="auto">
          <p>Though i am not getting any error ...no pdf is getting created</p>
        </div>

        <span class="post-media"><ul data-role="post-media-list"></ul></span>
      </div>
    </div>
    <a class="see-more hidden" title="see more" data-action="see-more">see more</a>
  </div>

  <footer class="comment__footer">
    <menu class="comment-footer__menu">
      <li class="voting" data-role="voting">
        <a href="#" class="vote-up  count-2" data-action="upvote" title="">

          <span class="updatable count" data-role="likes">2</span>
          <span class="control"><i aria-hidden="true" class="icon icon-arrow-2"></i></span>
        </a>
        <span role="button" class="vote-down  count-0" data-action="downvote" title="Vote down">

<span class="control"><i aria-hidden="true" class="icon icon-arrow"></i></span>
</span>
      </li>
      <li class="bullet" aria-hidden="true">•</li>

      <li class="reply" data-role="reply-link">
        <a href="#" data-action="reply">
          <span class="text">Reply</span></a></li>
      <li class="bullet" aria-hidden="true">•</li>

      <li class="comment__share">
        <a class="toggle"><span class="text">Share ›</span></a>
        <ul class="comment-share__buttons">
          <li class="twitter">
            <button class="share__button" data-action="share:twitter">Twitter</button>
          </li>
          <li class="facebook">
            <button class="share__button" data-action="share:facebook">Facebook</button>
          </li>
          <li class="link">
            <input class="share__button" value="http://disq.us/p/hgghs7" name="Link" title="Click to copy post link" data-action="copy-link" readonly="">
          </li>
        </ul>
      </li>

      <li class="realtime" data-role="realtime-notification:1055568391">
        <span style="display:none;" class="realtime-replies"></span>
        <a style="display:none;" href="#" class="realtime-button"></a>
      </li>

    </menu>
  </footer>
</div>
````````````````````````````````


```````````````````````````````` example(Issue Tests: 10) options(output-unknown)
<header class="comment__header"> priyanka • [3 years ago](http://www.rgagnon.com/javadetails/java-convert-html-to-pdf-using-yahp.html#comment-1055568391 "Monday, September 23, 2013 5:49 AM") </header>

Though i am not getting any error ...no pdf is getting created

see more
<footer class="comment__footer"> <menu class="comment-footer__menu">
* [2](# "")
* •
* [Reply](#)
* •
* Share ›
  * <button class="share__button" data-action="share:twitter">Twitter</button>
  * <button class="share__button" data-action="share:facebook">Facebook</button>
  * <input class="share__button" value="http://disq.us/p/hgghs7" name="Link" title="Click to copy post link" data-action="copy-link" readonly>

* 

</menu> </footer>

.
<div class="post-body">
  <header class="comment__header">
<span class="post-byline">
<span class="author">priyanka</span>

</span>

    <span class="post-meta">
<span class="bullet time-ago-bullet" aria-hidden="true">•</span>

<a href="http://www.rgagnon.com/javadetails/java-convert-html-to-pdf-using-yahp.html#comment-1055568391" data-role="relative-time" class="time-ago" title="Monday, September 23, 2013 5:49 AM">3 years ago</a>
</span>

  </header>

  <div class="post-body-inner">
    <div class="post-message-container" data-role="message-container">
      <div class="publisher-anchor-color" data-role="message-content">
        <div class="post-message " data-role="message" dir="auto">
          <p>Though i am not getting any error ...no pdf is getting created</p>
        </div>

        <span class="post-media"><ul data-role="post-media-list"></ul></span>
      </div>
    </div>
    <a class="see-more hidden" title="see more" data-action="see-more">see more</a>
  </div>

  <footer class="comment__footer">
    <menu class="comment-footer__menu">
      <li class="voting" data-role="voting">
        <a href="#" class="vote-up  count-2" data-action="upvote" title="">

          <span class="updatable count" data-role="likes">2</span>
          <span class="control"><i aria-hidden="true" class="icon icon-arrow-2"></i></span>
        </a>
        <span role="button" class="vote-down  count-0" data-action="downvote" title="Vote down">

<span class="control"><i aria-hidden="true" class="icon icon-arrow"></i></span>
</span>
      </li>
      <li class="bullet" aria-hidden="true">•</li>

      <li class="reply" data-role="reply-link">
        <a href="#" data-action="reply">
          <span class="text">Reply</span></a></li>
      <li class="bullet" aria-hidden="true">•</li>

      <li class="comment__share">
        <a class="toggle"><span class="text">Share ›</span></a>
        <ul class="comment-share__buttons">
          <li class="twitter">
            <button class="share__button" data-action="share:twitter">Twitter</button>
          </li>
          <li class="facebook">
            <button class="share__button" data-action="share:facebook">Facebook</button>
          </li>
          <li class="link">
            <input class="share__button" value="http://disq.us/p/hgghs7" name="Link" title="Click to copy post link" data-action="copy-link" readonly="">
          </li>
        </ul>
      </li>

      <li class="realtime" data-role="realtime-notification:1055568391">
        <span style="display:none;" class="realtime-replies"></span>
        <a style="display:none;" href="#" class="realtime-button"></a>
      </li>

    </menu>
  </footer>
</div>
````````````````````````````````


Unwrap iframes

```````````````````````````````` example Issue Tests: 11
.
<iframe id="dsq-app4" name="dsq-app4" allowtransparency="true" frameborder="0" scrolling="no" tabindex="0" title="Disqus" width="100%" src="http://disqusads.com/ads-iframe/taboola/?category=tech&amp;stories_allowed=0&amp;service=dynamic&amp;safetylevel=20&amp;display_allowed=1&amp;video_allowed=0&amp;provider=taboola&amp;thumbnails_allowed=1&amp;experiment=network_default&amp;variant=fallthrough&amp;display_only=0&amp;t=1485117195&amp;links_allowed=1&amp;position=bottom&amp;shortname=realshowto&amp;display_bidding_allowed=0&amp;forum_shortname=realshowto&amp;forum_pk=1341383&amp;anchorColor=%23039be5&amp;colorScheme=light&amp;sourceUrl=http%3A%2F%2Fwww.rgagnon.com%2Fjavadetails%2Fjava-convert-html-to-pdf-using-yahp.html&amp;typeface=sans-serif&amp;disqus_version=9464b90" style="box-sizing: inherit; width: 1px !important; min-width: 100%; border: none !important; overflow: hidden !important; height: 586px !important;"></iframe>
````````````````````````````````


suppress GitHub anchors and octicon svg

```````````````````````````````` example Issue Tests: 12
TEST CASES
==========

.
<h1><a id="user-content-test-cases" class="anchor" href="#test-cases" aria-hidden="true"><svg aria-hidden="true" class="octicon octicon-link" height="16" version="1.1" viewBox="0 0 16 16" width="16"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>TEST CASES</h1>
````````````````````````````````


suppress GitHub anchors and octicon svg

```````````````````````````````` example Issue Tests: 13
TEST CASES
==========

.
<h1><a id="user-content-test-cases" class="anchor" href="file#test-cases" aria-hidden="true"><svg aria-hidden="true" class="octicon octicon-link" height="16" version="1.1" viewBox="0 0 16 16" width="16"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>TEST CASES</h1>
````````````````````````````````


empty headings

```````````````````````````````` example Issue Tests: 14
.
<h1 style="box-sizing: border-box; font-size: 2em; margin: 24px 0px 16px; font-weight: 600; line-height: 1.25; padding-bottom: 0.3em; border-bottom: 1px solid rgb(238, 238, 238); position: relative; padding-right: 0.8em; cursor: pointer; color: rgb(51, 51, 51); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px;"> <a id="user-content-changelog" class="anchor" href="https://github.com/danfickle/openhtmltopdf#changelog" aria-hidden="true" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none; float: left; padding-right: 4px; margin-left: -20px; line-height: 1;"><svg aria-hidden="true" class="octicon octicon-link" height="16" version="1.1" viewBox="0 0 16 16" width="16"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>  </h1><br class="Apple-interchange-newline">
````````````````````````````````


GitHub file list

```````````````````````````````` example Issue Tests: 15
![@danfickle](https://avatars2.githubusercontent.com/u/1415728?v=3&s=40) [danfickle](https://github.com/danfickle) [For](https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91 "For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]") [#52](https://github.com/danfickle/openhtmltopdf/issues/52 "Adobe Reader promts for saving") [- Do not output acroform dict in case where document contains...](https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91 "For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]") ...

|---|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
|   | [docs](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/docs "docs")                                                                                        | [For](https://github.com/danfickle/openhtmltopdf/commit/d41eb81c246083b521f3e63f8685ace3620be3fe "For #8 - Update README and integration guide with RC8 version. [ci skip]") [#8](https://github.com/danfickle/openhtmltopdf/issues/8) [- Update README and integration guide with RC8 version. [ci skip]](https://github.com/danfickle/openhtmltopdf/commit/d41eb81c246083b521f3e63f8685ace3620be3fe "For #8 - Update README and integration guide with RC8 version. [ci skip]")                                                                     | 2 months ago |
|   | [obsolete-archive](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/obsolete-archive "obsolete-archive")                                                    | [Cleanup repository - Move everything obsolete to the obsolete-archive...](https://github.com/danfickle/openhtmltopdf/commit/3ca888aab7c587d16ec25e31db8a5659a0ed75ee "Cleanup repository - Move everything obsolete to the obsolete-archive folder.")                                                                                                                                                                                                                                                                                                | 7 months ago |
|   | [openhtmltopdf-core](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-core "openhtmltopdf-core")                                              | [[maven-release-plugin] prepare for next development iteration](https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec "[maven-release-plugin] prepare for next development iteration")                                                                                                                                                                                                                                                                                                                           | 2 months ago |
|   | [openhtmltopdf-examples](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-examples "openhtmltopdf-examples")                                  | [[maven-release-plugin] prepare for next development iteration](https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec "[maven-release-plugin] prepare for next development iteration")                                                                                                                                                                                                                                                                                                                           | 2 months ago |
|   | [openhtmltopdf-jsoup-dom-converter](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-jsoup-dom-converter "openhtmltopdf-jsoup-dom-converter") | [[maven-release-plugin] prepare for next development iteration](https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec "[maven-release-plugin] prepare for next development iteration")                                                                                                                                                                                                                                                                                                                           | 2 months ago |
|   | [openhtmltopdf-log4j](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-log4j "openhtmltopdf-log4j")                                           | [[maven-release-plugin] prepare for next development iteration](https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec "[maven-release-plugin] prepare for next development iteration")                                                                                                                                                                                                                                                                                                                           | 2 months ago |
|   | [openhtmltopdf-pdfbox](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-pdfbox "openhtmltopdf-pdfbox")                                        | [For](https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91 "For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]") [#52](https://github.com/danfickle/openhtmltopdf/issues/52 "Adobe Reader promts for saving") [- Do not output acroform dict in case where document contains...](https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91 "For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]") | 22 days ago  |
|   | [openhtmltopdf-rtl-support](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-rtl-support "openhtmltopdf-rtl-support")                         | [[maven-release-plugin] prepare for next development iteration](https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec "[maven-release-plugin] prepare for next development iteration")                                                                                                                                                                                                                                                                                                                           | 2 months ago |
|   | [openhtmltopdf-slf4j](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-slf4j "openhtmltopdf-slf4j")                                           | [[maven-release-plugin] prepare for next development iteration](https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec "[maven-release-plugin] prepare for next development iteration")                                                                                                                                                                                                                                                                                                                           | 2 months ago |
|   | [openhtmltopdf-svg-support](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-svg-support "openhtmltopdf-svg-support")                         | [[maven-release-plugin] prepare for next development iteration](https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec "[maven-release-plugin] prepare for next development iteration")                                                                                                                                                                                                                                                                                                                           | 2 months ago |
|   | [tests](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/tests "tests")                                                                                     | [regression snapshot was missing source images, now using Ant to make ...](https://github.com/danfickle/openhtmltopdf/commit/927efc50fb4a8870691a168ea86223ebcb70b4fc "regression snapshot was missing source images, now using Ant to make copy of entire source reference tree and including this in snapshot diff. rebuild R8 snapshot so it includes these files. have ref comparison only report failures.")                                                                                                                                     | 8 years ago  |
|   | [.cvsignore](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/.cvsignore ".cvsignore")                                                                      | [.](https://github.com/danfickle/openhtmltopdf/commit/81951cc933fffdf85a5b5fbe274bd9c181c1daed ".")                                                                                                                                                                                                                                                                                                                                                                                                                                                   | 8 years ago  |
|   | [.gitignore](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/.gitignore ".gitignore")                                                                      | [For](https://github.com/danfickle/openhtmltopdf/commit/04742bbc076613fc809fe05ca254269598f996bf "For #1 - Set up Apache PDF-BOX 2 output device module.") [#1](https://github.com/danfickle/openhtmltopdf/issues/1) [- Set up Apache PDF-BOX 2 output device module.](https://github.com/danfickle/openhtmltopdf/commit/04742bbc076613fc809fe05ca254269598f996bf "For #1 - Set up Apache PDF-BOX 2 output device module.")                                                                                                                           | a year ago   |
|   | [.hgignore](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/.hgignore ".hgignore")                                                                         | [Add .hgignore for those using hg-git](https://github.com/danfickle/openhtmltopdf/commit/011613889106579f70d33e6ec78a77168ee54424 "Add .hgignore for those using hg-git --HG-- rename : .gitignore => .hgignore")                                                                                                                                                                                                                                                                                                                                     | 7 years ago  |
|   | [.travis.yml](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/.travis.yml ".travis.yml")                                                                   | [Continuous integration with travis.](https://github.com/danfickle/openhtmltopdf/commit/6c79ddb644ecc7ab2617480b4b8207de7adec6f3 "Continuous integration with travis.  Primarily so we stay compatible with Java 7.")                                                                                                                                                                                                                                                                                                                                 | 7 months ago |
|   | [LICENSE](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/LICENSE "LICENSE")                                                                               | [For](https://github.com/danfickle/openhtmltopdf/commit/f4dfeb9faf89f4108ed6a50b67392073b9dde02a "For #8 - Update LICENSE.") [#8](https://github.com/danfickle/openhtmltopdf/issues/8) [- Update LICENSE.](https://github.com/danfickle/openhtmltopdf/commit/f4dfeb9faf89f4108ed6a50b67392073b9dde02a "For #8 - Update LICENSE.")                                                                                                                                                                                                                     | 7 months ago |
|   | [LICENSE-LGPL-2.1.txt](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/LICENSE-LGPL-2.1.txt "LICENSE-LGPL-2.1.txt")                                        | [- change (back) license to 2.1 or later](https://github.com/danfickle/openhtmltopdf/commit/21c4dd319d5a7fac397bda10d60bb07a34782f1c "- change (back) license to 2.1 or later - include all LICENSE files in the jars")                                                                                                                                                                                                                                                                                                                               | 2 years ago  |
|   | [LICENSE-LGPL-3.txt](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/LICENSE-LGPL-3.txt "LICENSE-LGPL-3.txt")                                              | [- license update LGPL version 2.1 to 3](https://github.com/danfickle/openhtmltopdf/commit/a1ea2f09424b35ad2988453f70dc820872eb5ce0 "- license update LGPL version 2.1 to 3 - added license text of GPLv3 (mandatory) - links updated - added license information in poms - added license includes for jars")                                                                                                                                                                                                                                         | 2 years ago  |
|   | [LICENSE-W3C-TEST](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/LICENSE-W3C-TEST "LICENSE-W3C-TEST")                                                    | [Added license for W3C tests, as requested on tests website.](https://github.com/danfickle/openhtmltopdf/commit/17e53385724025576684290a62cc05c3183b163e "Added license for W3C tests, as requested on tests website.")                                                                                                                                                                                                                                                                                                                               | 10 years ago |
|   | [README.md](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/README.md "README.md")                                                                         | [For](https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91 "For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]") [#52](https://github.com/danfickle/openhtmltopdf/issues/52 "Adobe Reader promts for saving") [- Do not output acroform dict in case where document contains...](https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91 "For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]") | 22 days ago  |
|   | [pom.xml](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/pom.xml "pom.xml")                                                                               | [[maven-release-plugin] prepare for next development iteration](https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec "[maven-release-plugin] prepare for next development iteration")                                                                                                                                                                                                                                                                                                                           | 2 months ago |

.
<meta charset='utf-8'>
<div class="commit-tease js-details-container" style="box-sizing: border-box; position: relative; padding: 10px; margin-bottom: -1px; font-size: 13px; line-height: 20px; color: rgb(104, 119, 125); background-color: rgb(242, 249, 252); border: 1px solid rgb(201, 230, 242); border-radius: 3px 3px 0px 0px; font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px;">
  <span class="commit-author-section" style="box-sizing: border-box; color: rgb(51, 51, 51);"><img alt="@danfickle" class="avatar" height="20" src="https://avatars2.githubusercontent.com/u/1415728?v=3&amp;s=40" width="20" style="box-sizing: border-box; border-style: none; display: inline-block; overflow: hidden; line-height: 1; vertical-align: middle; border-radius: 3px; margin-top: -1px;"><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle" class="user-mention" rel="author" style="box-sizing: border-box; background-color: transparent; color: rgb(51, 51, 51); text-decoration: none; font-weight: 600; white-space: nowrap;">danfickle</a><span class="Apple-converted-space"> </span></span><a href="https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91" class="message" data-pjax="true" title="For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]" style="box-sizing: border-box; background-color: transparent; color: inherit; text-decoration: none;">For</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/issues/52" class="issue-link js-issue-link" data-id="191574839" data-error-text="Failed to load issue title" data-permission-text="Issue title is private" title="Adobe Reader promts for saving" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">#52</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91" class="message" data-pjax="true" title="For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]" style="box-sizing: border-box; background-color: transparent; color: inherit; text-decoration: none;">- Do not output acroform dict in case where document contains…</a><span class="Apple-converted-space"> </span><span class="hidden-text-expander inline" style="box-sizing: border-box; display: inline-block; position: relative; top: -1px; margin-left: 5px; line-height: 0;"><button type="button" class="ellipsis-expander js-details-target" style="box-sizing: border-box; font-style: inherit; font-variant: inherit; font-weight: bold; font-stretch: inherit; font-size: 12px; line-height: 6px; font-family: inherit; margin: 0px; overflow: visible; text-transform: none; -webkit-appearance: button; cursor: pointer; display: inline-block; height: 12px; padding: 0px 5px 5px; color: rgb(85, 85, 85); text-decoration: none; vertical-align: middle; background: rgb(221, 221, 221); border: 0px; border-radius: 1px;">…</button></span>
</div>
<div class="file-wrap" style="box-sizing: border-box; margin-bottom: 10px; border-width: 0px 1px 1px; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-right-color: rgb(221, 221, 221); border-bottom-color: rgb(221, 221, 221); border-left-color: rgb(221, 221, 221); border-image: initial; border-top-style: initial; border-top-color: initial; border-bottom-right-radius: 3px; border-bottom-left-radius: 3px; color: rgb(51, 51, 51); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);">
  <table class="files js-navigation-container js-active-navigation-container" data-pjax="" style="box-sizing: border-box; border-spacing: 0px; border-collapse: collapse; width: 977.778px; background: rgb(255, 255, 255); border-radius: 2px;">
    <tbody style="box-sizing: border-box;">
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/docs" class="js-navigation-open" id="e3e2a9bfd88566b05001b02a3f51d286-b94e3fb3293e2773a2e0cff1140fa924842a3662" title="docs" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">docs</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);">
          <span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d41eb81c246083b521f3e63f8685ace3620be3fe" class="message" data-pjax="true" title="For #8 - Update README and integration guide with RC8 version. [ci skip]" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">For</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/issues/8" class="issue-link js-issue-link" data-url="https://github.com/danfickle/openhtmltopdf/issues/8" data-id="131634448" data-error-text="Failed to load issue title" data-permission-text="Issue title is private" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">#8</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/commit/d41eb81c246083b521f3e63f8685ace3620be3fe" class="message" data-pjax="true" title="For #8 - Update README and integration guide with RC8 version. [ci skip]" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">- Update README and integration guide with RC8 version. [ci skip]</a></span>
        </td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T11:05:17Z" title="Nov 22, 2016, 6:05 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/obsolete-archive" class="js-navigation-open" id="c68a1d46a8d6446c655338dc04a36187-b659a4b026b17151924c11a94f687d949afa4b5f" title="obsolete-archive" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">obsolete-archive</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/3ca888aab7c587d16ec25e31db8a5659a0ed75ee" class="message" data-pjax="true" title="Cleanup repository - Move everything obsolete to the obsolete-archive folder." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">Cleanup repository - Move everything obsolete to the obsolete-archive…</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-06-25T10:19:05Z" title="Jun 25, 2016, 6:19 AM GMT-4" style="box-sizing: border-box;">7 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-core" class="js-navigation-open" id="43c1644a7dd1cc3e7295e178d19c554a-3293f3b0f6207b4ab1cd264b7adfeecc2df81f22" title="openhtmltopdf-core" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">openhtmltopdf-core</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec" class="message" data-pjax="true" title="[maven-release-plugin] prepare for next development iteration" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">[maven-release-plugin] prepare for next development iteration</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T10:43:19Z" title="Nov 22, 2016, 5:43 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-examples" class="js-navigation-open" id="70804f5dd080109e48eaad24fc5c4097-8a9fa3a342a5f7f15325c5b19bb25f3b96f6126b" title="openhtmltopdf-examples" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">openhtmltopdf-examples</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec" class="message" data-pjax="true" title="[maven-release-plugin] prepare for next development iteration" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">[maven-release-plugin] prepare for next development iteration</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T10:43:19Z" title="Nov 22, 2016, 5:43 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-jsoup-dom-converter" class="js-navigation-open" id="1e7db2f9f0762d128f1281dca6239083-77d726435adb235dac254d0b6d68b5583fee50c4" title="openhtmltopdf-jsoup-dom-converter" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">openhtmltopdf-jsoup-dom-converter</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec" class="message" data-pjax="true" title="[maven-release-plugin] prepare for next development iteration" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">[maven-release-plugin] prepare for next development iteration</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T10:43:19Z" title="Nov 22, 2016, 5:43 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-log4j" class="js-navigation-open" id="680c20366dfd644b5fb886e15c131d0d-16ac99b925ef526d8df63ab6a5d22bab12eeb4f9" title="openhtmltopdf-log4j" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">openhtmltopdf-log4j</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec" class="message" data-pjax="true" title="[maven-release-plugin] prepare for next development iteration" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">[maven-release-plugin] prepare for next development iteration</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T10:43:19Z" title="Nov 22, 2016, 5:43 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-pdfbox" class="js-navigation-open" id="7d9e5aec22a43aab0ee1c67d71cdccd0-a7ffee83109805cb8ff5f75914c92a9e950944de" title="openhtmltopdf-pdfbox" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">openhtmltopdf-pdfbox</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);">
          <span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91" class="message" data-pjax="true" title="For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">For</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/issues/52" class="issue-link js-issue-link" data-id="191574839" data-error-text="Failed to load issue title" data-permission-text="Issue title is private" title="Adobe Reader promts for saving" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">#52</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91" class="message" data-pjax="true" title="For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">- Do not output acroform dict in case where document contains…</a></span>
        </td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2017-01-02T11:11:16Z" title="Jan 2, 2017, 6:11 AM GMT-5" style="box-sizing: border-box;">22 days ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-rtl-support" class="js-navigation-open" id="4f21e98d89412d75c1a1e723231b1a93-0e3e5145104c719670100d02751ff4d96b03ae71" title="openhtmltopdf-rtl-support" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">openhtmltopdf-rtl-support</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec" class="message" data-pjax="true" title="[maven-release-plugin] prepare for next development iteration" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">[maven-release-plugin] prepare for next development iteration</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T10:43:19Z" title="Nov 22, 2016, 5:43 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-slf4j" class="js-navigation-open" id="d510b03c0e9243cec05d086a8bc69dc0-7b3045db1f683da0dd9521d9c6dd6ec2c9374aea" title="openhtmltopdf-slf4j" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">openhtmltopdf-slf4j</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec" class="message" data-pjax="true" title="[maven-release-plugin] prepare for next development iteration" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">[maven-release-plugin] prepare for next development iteration</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T10:43:19Z" title="Nov 22, 2016, 5:43 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-svg-support" class="js-navigation-open" id="0ec99f35d67fead18978c7a2880db8c1-90a1e298cddc2fedd9578556df2bc7d40d4c6568" title="openhtmltopdf-svg-support" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">openhtmltopdf-svg-support</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec" class="message" data-pjax="true" title="[maven-release-plugin] prepare for next development iteration" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">[maven-release-plugin] prepare for next development iteration</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T10:43:19Z" title="Nov 22, 2016, 5:43 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/tests" class="js-navigation-open" id="b61a6d542f9036550ba9c401c80f00ef-5f0ca6a61b4fbe07824e4c7aed64c4839042c695" title="tests" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">tests</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/927efc50fb4a8870691a168ea86223ebcb70b4fc" class="message" data-pjax="true" title="regression snapshot was missing source images, now using Ant to make copy of entire source reference tree and including this in snapshot diff. rebuild R8 snapshot so it includes these files. have ref comparison only report failures." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">regression snapshot was missing source images, now using Ant to make …</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2009-04-17T18:14:50Z" title="Apr 17, 2009, 2:14 PM GMT-4" style="box-sizing: border-box;">8 years ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/.cvsignore" class="js-navigation-open" id="6d2b615139fd238e7c7471805068037e-47f0e8c731d42d4b5a30948b26bd9cce93bed18d" title=".cvsignore" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">.cvsignore</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/81951cc933fffdf85a5b5fbe274bd9c181c1daed" class="message" data-pjax="true" title="." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">.</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2009-04-13T13:36:19Z" title="Apr 13, 2009, 9:36 AM GMT-4" style="box-sizing: border-box;">8 years ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/.gitignore" class="js-navigation-open" id="a084b794bc0759e7a6b77810e01874f2-f170b301ac80705855d2b2f7fa7208dae6f95190" title=".gitignore" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">.gitignore</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);">
          <span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/04742bbc076613fc809fe05ca254269598f996bf" class="message" data-pjax="true" title="For #1 - Set up Apache PDF-BOX 2 output device module." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">For</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/issues/1" class="issue-link js-issue-link" data-url="https://github.com/danfickle/openhtmltopdf/issues/1" data-id="115005751" data-error-text="Failed to load issue title" data-permission-text="Issue title is private" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">#1</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/commit/04742bbc076613fc809fe05ca254269598f996bf" class="message" data-pjax="true" title="For #1 - Set up Apache PDF-BOX 2 output device module." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">- Set up Apache PDF-BOX 2 output device module.</a></span>
        </td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2015-11-04T09:51:21Z" title="Nov 4, 2015, 4:51 AM GMT-5" style="box-sizing: border-box;">a year ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/.hgignore" class="js-navigation-open" id="c8e92ef85cd1579eac1c2ad728e79720-28a4f06ef31cdf35617690a280f8fe45243415d1" title=".hgignore" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">.hgignore</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/011613889106579f70d33e6ec78a77168ee54424" class="message" data-pjax="true" title="Add .hgignore for those using hg-git
--HG--
rename : .gitignore => .hgignore" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">Add .hgignore for those using hg-git</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2010-08-07T10:08:31Z" title="Aug 7, 2010, 6:08 AM GMT-4" style="box-sizing: border-box;">7 years ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/.travis.yml" class="js-navigation-open" id="354f30a63fb0907d4ad57269548329e3-7957a820abc3e86f2a0664ee2879d64e77437beb" title=".travis.yml" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">.travis.yml</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/6c79ddb644ecc7ab2617480b4b8207de7adec6f3" class="message" data-pjax="true" title="Continuous integration with travis.

Primarily so we stay compatible with Java 7." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">Continuous integration with travis.</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-06-25T12:59:30Z" title="Jun 25, 2016, 8:59 AM GMT-4" style="box-sizing: border-box;">7 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/LICENSE" class="js-navigation-open" id="9879d6db96fd29134fc802214163b95a-9756d4317eb74427f88dd3f24b37764cf03dbf72" itemprop="license" title="LICENSE" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">LICENSE</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);">
          <span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/f4dfeb9faf89f4108ed6a50b67392073b9dde02a" class="message" data-pjax="true" title="For #8 - Update LICENSE." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">For</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/issues/8" class="issue-link js-issue-link" data-url="https://github.com/danfickle/openhtmltopdf/issues/8" data-id="131634448" data-error-text="Failed to load issue title" data-permission-text="Issue title is private" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">#8</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/commit/f4dfeb9faf89f4108ed6a50b67392073b9dde02a" class="message" data-pjax="true" title="For #8 - Update LICENSE." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">- Update LICENSE.</a></span>
        </td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-06-25T10:35:55Z" title="Jun 25, 2016, 6:35 AM GMT-4" style="box-sizing: border-box;">7 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/LICENSE-LGPL-2.1.txt" class="js-navigation-open" id="82af9b08b0a2adf5b8dd97639db5a1ff-5ab7695ab8cabe0c5c8a814bb0ab1e8066578fbb" itemprop="license" title="LICENSE-LGPL-2.1.txt" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">LICENSE-LGPL-2.1.txt</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/21c4dd319d5a7fac397bda10d60bb07a34782f1c" class="message" data-pjax="true" title="- change (back) license to 2.1 or later
- include all LICENSE files in the jars" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">- change (back) license to 2.1 or later</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2015-07-15T15:45:49Z" title="Jul 15, 2015, 11:45 AM GMT-4" style="box-sizing: border-box;">2 years ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/LICENSE-LGPL-3.txt" class="js-navigation-open" id="71b1628a315fa537b1b8dc7548a5a43b-02bbb60bc49afc2d6a1bedf96288eab236d80fbd" itemprop="license" title="LICENSE-LGPL-3.txt" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">LICENSE-LGPL-3.txt</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/a1ea2f09424b35ad2988453f70dc820872eb5ce0" class="message" data-pjax="true" title="- license update LGPL version 2.1 to 3
- added license text of GPLv3 (mandatory)
- links updated
- added license information in poms
- added license includes for jars" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">- license update LGPL version 2.1 to 3</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2015-07-15T09:34:52Z" title="Jul 15, 2015, 5:34 AM GMT-4" style="box-sizing: border-box;">2 years ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/LICENSE-W3C-TEST" class="js-navigation-open" id="d04bb24acfc954e4d9d3bca685827fe9-0ee2f1e33aa98a4e15f005e05e0e5abcff7dbd8d" itemprop="license" title="LICENSE-W3C-TEST" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">LICENSE-W3C-TEST</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/17e53385724025576684290a62cc05c3183b163e" class="message" data-pjax="true" title="Added license for W3C tests, as requested on tests website." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">Added license for W3C tests, as requested on tests website.</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2007-07-10T19:52:36Z" title="Jul 10, 2007, 3:52 PM GMT-4" style="box-sizing: border-box;">10 years ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/README.md" class="js-navigation-open" id="04c6e90faac2675aa89e2176d2eec7d8-1ed0fe7dc9e7c33e8b617c062531b4c3e3abe1a9" title="README.md" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">README.md</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);">
          <span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91" class="message" data-pjax="true" title="For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">For</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/issues/52" class="issue-link js-issue-link" data-id="191574839" data-error-text="Failed to load issue title" data-permission-text="Issue title is private" title="Adobe Reader promts for saving" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">#52</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91" class="message" data-pjax="true" title="For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">- Do not output acroform dict in case where document contains…</a></span>
        </td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2017-01-02T11:11:16Z" title="Jan 2, 2017, 6:11 AM GMT-5" style="box-sizing: border-box;">22 days ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item navigation-focus" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118); background: rgb(245, 245, 245);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px; background: rgb(245, 245, 245);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/pom.xml" class="js-navigation-open" id="600376dffeb79835ede4a0b285078036-3d3378a25a49593f03fbb7f81116ca2ca09e270e" title="pom.xml" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">pom.xml</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136); background: rgb(245, 245, 245);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec" class="message" data-pjax="true" title="[maven-release-plugin] prepare for next development iteration" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">[maven-release-plugin] prepare for next development iteration</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap; background: rgb(245, 245, 245);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T10:43:19Z" title="Nov 22, 2016, 5:43 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
    </tbody>
  </table>
</div>
````````````````````````````````


```````````````````````````````` example Issue Tests: 16
* [Version 0.8.3 - Bug Fix and Improvement Release](https://github.com/vsch/MissingInActions#version-083---bug-fix-and-improvement-release)
* [Version 0.8.2 - Enhanced Paste From History](https://github.com/vsch/MissingInActions#version-082---enhanced-paste-from-history)
* [Version 0.8.0 - Mia has come of age!](https://github.com/vsch/MissingInActions#version-080---mia-has-come-of-age)
* [Why Do I Need Mia?](https://github.com/vsch/MissingInActions#why-do-i-need-mia)
  * [What you didn't know you were missing](https://github.com/vsch/MissingInActions#what-you-didnt-know-you-were-missing)
  * [Auto Indent Lines after Move Lines Up/Down](https://github.com/vsch/MissingInActions#auto-indent-lines-after-move-lines-updown)
  * [Auto Line Selections](https://github.com/vsch/MissingInActions#auto-line-selections)

<br />

.
<ul style="box-sizing: border-box; padding-left: 2em; margin-top: 0px; margin-bottom: 16px; color: rgb(51, 51, 51); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px;">
  <li style="box-sizing: border-box;"><a href="https://github.com/vsch/MissingInActions#version-083---bug-fix-and-improvement-release" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Version 0.8.3 - Bug Fix and Improvement Release</a></li>
  <li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#version-082---enhanced-paste-from-history" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Version 0.8.2 - Enhanced Paste From History</a></li>
  <li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#version-080---mia-has-come-of-age" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Version 0.8.0 - Mia has come of age!</a></li>
  <li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#why-do-i-need-mia" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Why Do I Need Mia?</a>
    <ul style="box-sizing: border-box; padding-left: 2em; margin-top: 0px; margin-bottom: 0px;">
      <li style="box-sizing: border-box;"><a href="https://github.com/vsch/MissingInActions#what-you-didnt-know-you-were-missing" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">What you didn't know you were missing</a></li>
      <li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#auto-indent-lines-after-move-lines-updown" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Auto Indent Lines after Move Lines Up/Down</a></li>
      <li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#auto-line-selections" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Auto Line Selections</a></li>
    </ul>
  </li>
</ul>
<br class="Apple-interchange-newline">
````````````````````````````````


```````````````````````````````` example Issue Tests: 17
* [Version 0.8.3 - Bug Fix and Improvement Release](https://github.com/vsch/MissingInActions#version-083---bug-fix-and-improvement-release)
* [Version 0.8.2 - Enhanced Paste From History](https://github.com/vsch/MissingInActions#version-082---enhanced-paste-from-history)
* [Version 0.8.0 - Mia has come of age!](https://github.com/vsch/MissingInActions#version-080---mia-has-come-of-age)
* [Why Do I Need Mia?](https://github.com/vsch/MissingInActions#why-do-i-need-mia)
  * [What you didn't know you were missing](https://github.com/vsch/MissingInActions#what-you-didnt-know-you-were-missing)
  * [Auto Indent Lines after Move Lines Up/Down](https://github.com/vsch/MissingInActions#auto-indent-lines-after-move-lines-updown)
  * [Auto Line Selections](https://github.com/vsch/MissingInActions#auto-line-selections)

<br />

.
<ul style="box-sizing: border-box; padding-left: 2em; margin-top: 0px; margin-bottom: 16px; color: rgb(51, 51, 51); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px;"><li style="box-sizing: border-box;"><a href="https://github.com/vsch/MissingInActions#version-083---bug-fix-and-improvement-release" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Version 0.8.3 - Bug Fix and Improvement Release</a></li><li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#version-082---enhanced-paste-from-history" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Version 0.8.2 - Enhanced Paste From History</a></li><li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#version-080---mia-has-come-of-age" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Version 0.8.0 - Mia has come of age!</a></li><li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#why-do-i-need-mia" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Why Do I Need Mia?</a><ul style="box-sizing: border-box; padding-left: 2em; margin-top: 0px; margin-bottom: 0px;"><li style="box-sizing: border-box;"><a href="https://github.com/vsch/MissingInActions#what-you-didnt-know-you-were-missing" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">What you didn't know you were missing</a></li><li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#auto-indent-lines-after-move-lines-updown" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Auto Indent Lines after Move Lines Up/Down</a></li><li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#auto-line-selections" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Auto Line Selections</a></li></ul></li></ul><br class="Apple-interchange-newline">
````````````````````````````````


don't create a multi-line image url if no new line after ?

```````````````````````````````` example Issue Tests: 18
[![](https://github.com/vsch/MissingInActions/raw/master/resources/icons/Mia_logo@2x.png?)](https://github.com/vsch/MissingInActions/raw/master/resources/icons/Mia_logo@2x.png?)Missing In Actions
===================================================================================================================================================================================================

<br />

.
<meta charset='utf-8'><h1 style="box-sizing: border-box; font-size: 2em; margin-top: 0px !important; margin-right: 0px; margin-bottom: 16px; margin-left: 0px; font-weight: 600; line-height: 1.25; padding-bottom: 0.3em; border-bottom: 1px solid rgb(238, 238, 238); position: relative; padding-right: 0.8em; cursor: pointer; color: rgb(51, 51, 51); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px;"><a href="https://github.com/vsch/MissingInActions/raw/master/resources/icons/Mia_logo@2x.png?" target="_blank" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;"><img src="https://github.com/vsch/MissingInActions/raw/master/resources/icons/Mia_logo@2x.png?" height="32" width="54" border="0" align="absmiddle" style="box-sizing: content-box; border-style: none; max-width: 100%; background-color: rgb(255, 255, 255);"></a>Missing In Actions</h1>
<br class="Apple-interchange-newline">
````````````````````````````````


```````````````````````````````` example Issue Tests: 19
```
  enum class TestFormatter {
    FOO, // I am a comment
    BAR, // I am also a comment
  }
```

.
<pre class="wikicode prettyprint prettyprinted"><span class="pln">  </span><span class="kwd">enum</span><span class="pln"> </span><span class="kwd">class</span><span class="pln"> </span><span class="typ">TestFormatter</span><span class="pln"> </span><span class="pun">{</span><br><span class="pln">    FOO</span><span class="pun">,</span><span class="pln"> </span><span class="com">// I am a comment</span><br><span class="pln">    BAR</span><span class="pun">,</span><span class="pln"> </span><span class="com">// I am also a comment</span><br><span class="pln">  </span><span class="pun">}</span></pre>
````````````````````````````````


```````````````````````````````` example Issue Tests: 20
```
  enum class TestFormatter {
    FOO, // I am a comment BAR, // I am also a comment
  }
```

.
<pre class="wikicode prettyprint prettyprinted"><span class="pln">  </span><span class="kwd">enum</span><span class="pln"> </span><span class="kwd">class</span><span class="pln"> </span><span class="typ">TestFormatter</span><span class="pln"> </span><span class="pun">{</span><br><span class="pln">    FOO</span><span class="pun">,</span><span class="pln"> </span><span class="com">// I am a comment BAR, // I am also a comment</span><br><span class="pln">  </span><span class="pun">}</span></pre>
````````````````````````````````


```````````````````````````````` example Issue Tests: 21
<h3>JavaScript is disabled in your browser or not supported!</h3> View Options
* [Show description](javascript:void(0))
* [Show comments](javascript:void(0))

[Click To Add a Title](javascript:void(0))
==========================================

[project: Kotlin State: -Fixed,-Duplicate,-Rejected,-{As Designed},-Obsolete type: Feature,{Feature Group} refactoring](/issues?q=project%3A+Kotlin+State%3A+-Fixed%2C-Duplicate%2C-Rejected%2C-%7BAs+Designed%7D%2C-Obsolete+++type%3A+Feature%2C%7BFeature+Group%7D++refactoring)
Only the first 2500 issues are exported because of the "Max Issues to Export" setting.

Feature
:   [KT-2615](/issue/KT-2615)
    --- Rename refactoring
:   [KT-2638](/issue/KT-2638)
    --- Inline function/non-trivial property refactoring
:   [KT-4478](/issue/KT-4478)
    --- Refactorings
:   [KT-5201](/issue/KT-5201)
    --- Extract Function: Allow forced extraction by shortcut
:   [KT-5602](/issue/KT-5602)
    --- move class/method refactoring should be able to move file
:   [KT-5804](/issue/KT-5804)
    --- Rename refactoring in Eclipse
:   [KT-5864](/issue/KT-5864)
    --- Refactoring: convert get/set methods to property
:   [KT-6159](/issue/KT-6159)
    --- Inline Method refactoring
:   [KT-6215](/issue/KT-6215)
    --- Converter: provide an option to place multiple java files converted to Kotlin in one Kotlin file
:   [KT-6370](/issue/KT-6370)
    --- Allow inline Java methods by using J2K converter
:   [KT-7107](/issue/KT-7107)
    --- Rename refactoring for labels
:   [KT-7973](/issue/KT-7973)
    --- Introduce parameter refactoring
:   [KT-8178](/issue/KT-8178)
    --- Copy Refactoring
:   [KT-8514](/issue/KT-8514)
    --- Refactor / Rename: "Rename variables" option could be supported for object declaration
:   [KT-8545](/issue/KT-8545)
    --- Rename: in-place mode could be supported for Kotlin
:   [KT-8565](/issue/KT-8565)
    --- Intention to remove default value and inline it in call sites (and vice versa?)
:   [KT-8954](/issue/KT-8954)
    --- Refactor / Copy could have "Update package directive" option
:   [KT-9851](/issue/KT-9851)
    --- Create "Extract/Introduce lateinit var" Refactoring
:   [KT-10346](/issue/KT-10346)
    --- Support `||` in when conditions in "Introduce subject" intention
:   [KT-10642](/issue/KT-10642)
    --- IntelliJ plugin: Support KDoc syntax for all types of comments
:   [KT-11050](/issue/KT-11050)
    --- "Extract constant" refactoring
:   [KT-11882](/issue/KT-11882)
    --- When caret is over a type in Intellij, highlight "var"/"val" which have this type inferred
:   [KT-11883](/issue/KT-11883)
    --- IntelliJ - Extract Variable refactoring - Specify type explicitly by default
:   [KT-12165](/issue/KT-12165)
    --- Add inspections for useless collection operations
:   [KT-12348](/issue/KT-12348)
    --- Rename class refactoring: propose to rename usages in comments
:   [KT-12471](/issue/KT-12471)
    --- Allow initializing `const` properties with enum constants
:   [KT-12713](/issue/KT-12713)
    --- Either/ Result to handle errors without exceptions
:   [KT-12756](/issue/KT-12756)
    --- Support @JvmMultifileClass when @JvmName value matches another file facade class
:   [KT-13309](/issue/KT-13309)
    --- Create field from local variable intention/refactoring
:   [KT-13414](/issue/KT-13414)
    --- Indexed properties
:   [KT-13436](/issue/KT-13436)
    --- Replace 'when' with return: handle case when all branches jump out (return Nothing)
:   [KT-13458](/issue/KT-13458)
    --- Cascade "replace with return" for if/when expressions
:   [KT-13623](/issue/KT-13623)
    --- Enhancement for Quick fix: while in a apply-style block, offer "Create member function" without "this." and similarly allow "Extract Method" refactoring
:   [KT-14137](/issue/KT-14137)
    --- Add intention to convert top level val with object expression to object
:   [KT-14272](/issue/KT-14272)
    --- Support Move function refactoring when instance is not captured
:   [KT-14627](/issue/KT-14627)
    --- Inline parameter refactoring
:   [KT-15638](/issue/KT-15638)
    --- Extract Interface/Superclass: suggest to search and update usages of refactored class (like for Java)
:   [KT-15664](/issue/KT-15664)
    --- Refactoring: extract header from platform implementation
:   [KT-15679](/issue/KT-15679)
    --- Rename refactoring for header/impl interface/class/function/typealias
:   [KT-15867](/issue/KT-15867)
    --- Copy file window imporvements

Feature Group
:   [KT-2637](/issue/KT-2637)
    --- Inline variable/constant refactoring

.
<body class="chrome release-notes ">
<noscript>
&lt;h3&gt;JavaScript is disabled in your browser or not supported!&lt;/h3&gt;
</noscript>
<div cn="l.R.issuesInHtml" id="id_l.R.issuesInHtml">
<button cn="l.R.viewOptions" id="id_l.R.viewOptions" class="jt-menu-button ring-link rn-view rn-view__item" data-toggle-onclick="true">
View Options<span class="ring-font-icon ring-font-icon_caret-down"></span>
</button>
<ul class="jt-menu">
<li>
<a class="rn-view__item" href="javascript:void(0)">
<input name="l.R.description" cn="l.R.description" id="id_l.R.description" class="jt-input" type="checkbox">
<label cn="l.R.descriptionLabel" id="id_l.R.descriptionLabel">Show description</label>
</a>
</li>
<li>
<a class="rn-view__item" href="javascript:void(0)">
<input name="l.R.comments" cn="l.R.comments" id="id_l.R.comments" class="jt-input" type="checkbox">
<label cn="l.R.commentsLabel" id="id_l.R.commentsLabel">Show comments</label>
</a>
</li>
</ul>
<h1>
<a cn="l.R.titleLink" p0="set" id="id_l.R.titleLink_set" href="javascript:void(0)">Click To Add a Title</a>
<span cn="l.R.titleLinkInlineEditor" p0="set" id="id_l.R.titleLinkInlineEditor_set" pid="id_l.R.titleLink_set" eid="id_l.R.titleText_set_title"></span>
</h1>
<div class="rn-query">
<a href="/issues?q=project%3A+Kotlin+State%3A+-Fixed%2C-Duplicate%2C-Rejected%2C-%7BAs+Designed%7D%2C-Obsolete+++type%3A+Feature%2C%7BFeature+Group%7D++refactoring">project: Kotlin State: -Fixed,-Duplicate,-Rejected,-{As Designed},-Obsolete   type: Feature,{Feature Group}  refactoring</a>
</div>
<div cn="l.R.rnc.releaseNotesContent" id="id_l.R.rnc.releaseNotesContent">
<div cn="l.R.rnc.limitExceedMsg" id="id_l.R.rnc.limitExceedMsg" class="infoNote" style="display: none">
Only the first <span class="bold">2500</span> issues are exported because of the "Max Issues to Export" setting. 
</div>
<dl>
<dt class="rn-type">Feature</dt>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-2615">KT-2615</a>
    <span class="rn-sum">— Rename refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-2638">KT-2638</a>
    <span class="rn-sum">— Inline function/non-trivial property refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-4478">KT-4478</a>
    <span class="rn-sum">— Refactorings</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-5201">KT-5201</a>
    <span class="rn-sum">— Extract Function: Allow forced extraction by shortcut</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-5602">KT-5602</a>
    <span class="rn-sum">— move class/method refactoring should be able to move file</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-5804">KT-5804</a>
    <span class="rn-sum">— Rename refactoring in Eclipse</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-5864">KT-5864</a>
    <span class="rn-sum">— Refactoring: convert get/set methods to property</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-6159">KT-6159</a>
    <span class="rn-sum">— Inline Method refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-6215">KT-6215</a>
    <span class="rn-sum">— Converter: provide an option to place multiple java files converted to Kotlin in one Kotlin file</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-6370">KT-6370</a>
    <span class="rn-sum">— Allow inline Java methods by using J2K converter</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-7107">KT-7107</a>
    <span class="rn-sum">— Rename refactoring for labels</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-7973">KT-7973</a>
    <span class="rn-sum">— Introduce parameter refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-8178">KT-8178</a>
    <span class="rn-sum">— Copy Refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-8514">KT-8514</a>
    <span class="rn-sum">— Refactor / Rename: "Rename variables" option could be supported for object declaration</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-8545">KT-8545</a>
    <span class="rn-sum">— Rename: in-place mode could be supported for Kotlin</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-8565">KT-8565</a>
    <span class="rn-sum">— Intention to remove default value and inline it in call sites (and vice versa?)</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-8954">KT-8954</a>
    <span class="rn-sum">— Refactor / Copy could have "Update package directive" option</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-9851">KT-9851</a>
    <span class="rn-sum">— Create "Extract/Introduce lateinit var" Refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-10346">KT-10346</a>
    <span class="rn-sum">— Support `||` in when conditions in "Introduce subject" intention</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-10642">KT-10642</a>
    <span class="rn-sum">— IntelliJ plugin: Support KDoc syntax for all types of comments</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-11050">KT-11050</a>
    <span class="rn-sum">— "Extract constant" refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-11882">KT-11882</a>
    <span class="rn-sum">— When caret is over a type in Intellij, highlight "var"/"val" which have this type inferred</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-11883">KT-11883</a>
    <span class="rn-sum">— IntelliJ - Extract Variable refactoring - Specify type explicitly by default</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-12165">KT-12165</a>
    <span class="rn-sum">— Add inspections for useless collection operations</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-12348">KT-12348</a>
    <span class="rn-sum">— Rename class refactoring: propose to rename usages in comments</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-12471">KT-12471</a>
    <span class="rn-sum">— Allow initializing `const` properties with enum constants</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-12713">KT-12713</a>
    <span class="rn-sum">— Either/ Result to handle errors without exceptions</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-12756">KT-12756</a>
    <span class="rn-sum">— Support @JvmMultifileClass when @JvmName value matches another file facade class</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-13309">KT-13309</a>
    <span class="rn-sum">— Create field from local variable intention/refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-13414">KT-13414</a>
    <span class="rn-sum">— Indexed properties</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-13436">KT-13436</a>
    <span class="rn-sum">— Replace 'when' with return: handle case when all branches jump out (return Nothing)</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-13458">KT-13458</a>
    <span class="rn-sum">— Cascade "replace with return" for if/when expressions</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-13623">KT-13623</a>
    <span class="rn-sum">— Enhancement for Quick fix: while in a apply-style block, offer "Create member function" without "this." and similarly allow "Extract Method" refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-14137">KT-14137</a>
    <span class="rn-sum">— Add intention to convert top level val with object expression to object</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-14272">KT-14272</a>
    <span class="rn-sum">— Support Move function refactoring when instance is not captured</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-14627">KT-14627</a>
    <span class="rn-sum">— Inline parameter refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-15638">KT-15638</a>
    <span class="rn-sum">— Extract Interface/Superclass: suggest to search and update usages of refactored class (like for Java)</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-15664">KT-15664</a>
    <span class="rn-sum">— Refactoring: extract header from platform implementation</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-15679">KT-15679</a>
    <span class="rn-sum">— Rename refactoring for header/impl interface/class/function/typealias</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-15867">KT-15867</a>
    <span class="rn-sum">— Copy file window imporvements</span>
</dd>
<dt class="rn-type">Feature Group</dt>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-2637">KT-2637</a>
    <span class="rn-sum">— Inline variable/constant refactoring</span>
</dd>
</dl>
</div>
</div>
<script type="text/javascript">$(document).ready(function () {
cr.setTemplatePaths([{name: "CustomReleaseNotesIssueView", paths: [{path: "l.R.rnc.CustomReleaseNotesIssueView"}]},{name: "ReleaseNotesContent", paths: [{path: "l.R.rnc"}]},{name: "ReleaseNotes", paths: [{path: "l.R"}]}]);

});

</script>
<script type="text/javascript">$(document).ready(function () {
cr.onDocumentLoad();
});

</script>

<script type="text/javascript">$(document).ready(function () {
Webr.HeartBeat.init();
$("#__RESPONSE_TIME_CONTENT__").replaceWith(document.createTextNode("2974 ms"));
info("End of the response");

});

</script>

</body>
````````````````````````````````


```````````````````````````````` example(Issue Tests: 22) options(no-quotes, no-smarts)
“  
”  
‘  
’  
'  
«  
»  
…  
&endash;  
&emdash;  
“  
”  
‘  
’  
«  
»  
…  
–  
—

.
&ldquo;<br>
&rdquo;<br>
&lsquo;<br>
&rsquo;<br>
&apos;<br>
&laquo;<br>
&raquo;<br>
&hellip;<br>
&endash;<br>
&emdash;<br>
“<br>
”<br>
‘<br>
’<br>
«<br>
»<br>
…<br>
–<br>
—<br>
````````````````````````````````


```````````````````````````````` example(Issue Tests: 23) options(no-quotes)
“  
”  
‘  
’  
'  
«  
»  
...  
--  
---  
“  
”  
‘  
’  
«  
»  
...  
--  
---

.
&ldquo;<br>
&rdquo;<br>
&lsquo;<br>
&rsquo;<br>
&apos;<br>
&laquo;<br>
&raquo;<br>
&hellip;<br>
&endash;<br>
&emdash;<br>
“<br>
”<br>
‘<br>
’<br>
«<br>
»<br>
…<br>
–<br>
—<br>
````````````````````````````````


```````````````````````````````` example(Issue Tests: 24) options(no-smarts)
"  
"  
'  
'  
'  
<<  
>>  
…  
&endash;  
&emdash;  
"  
"  
'  
'  
<<  
>>  
…  
–  
—

.
&ldquo;<br>
&rdquo;<br>
&lsquo;<br>
&rsquo;<br>
&apos;<br>
&laquo;<br>
&raquo;<br>
&hellip;<br>
&endash;<br>
&emdash;<br>
“<br>
”<br>
‘<br>
’<br>
«<br>
»<br>
…<br>
–<br>
—<br>
````````````````````````````````


```````````````````````````````` example Issue Tests: 25
"  
"  
'  
'  
'  
<<  
>>  
...  
--  
---  
"  
"  
'  
'  
<<  
>>  
...  
--  
---

.
&ldquo;<br>
&rdquo;<br>
&lsquo;<br>
&rsquo;<br>
&apos;<br>
&laquo;<br>
&raquo;<br>
&hellip;<br>
&endash;<br>
&emdash;<br>
“<br>
”<br>
‘<br>
’<br>
«<br>
»<br>
…<br>
–<br>
—<br>
````````````````````````````````


## Issue MN-408

Issue #MN-408

```````````````````````````````` example Issue MN-408: 1
* 
* test
* 

.
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
→<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
→<title></title>
→<meta name="generator" content="LibreOffice 5.2.3.3 (MacOSX)"/>
→<style type="text/css">
→→@page { margin: 2cm }
→→p { margin-bottom: 0.25cm; direction: ltr; line-height: 120%; text-align: left; orphans: 2; widows: 2 }
→</style>
</head>
<body lang="de-DE" dir="ltr">
<ul>
→<li/>
→<li>test</li>
→<li></li>
</ul>
</body>
</html>
````````````````````````````````


```````````````````````````````` example Issue MN-408: 2
* 
* Equipment
* 
* Chemicals
* 
* Consumables
* 
* Enzymes
* 
* GMO
* 
* Antibodies
* 
* DNA Constructs
* 
* RNA Constructs
* 
* Vectors
* 
* Oligos

.
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
→<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
→<title></title>
→<meta name="generator" content="LibreOffice 5.2.3.3 (MacOSX)"/>
→<style type="text/css">
→→@page { margin: 2cm }
→→p { margin-bottom: 0.25cm; direction: ltr; line-height: 120%; text-align: left; orphans: 2; widows: 2 }
→</style>
</head>
<body lang="de-DE" dir="ltr">
<ul>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">Equipment</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">Chemicals</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">Consumables</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">Enzymes</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">GMO</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">Antibodies</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">DNA
→Constructs</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">RNA
→Constructs</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">Vectors</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">Oligos</span></p>
</ul>
</body>
</html>
````````````````````````````````


## Issue

Missing link text

```````````````````````````````` example Issue: 1
[Vladimir Reznichenko](https://intellij-support.jetbrains.com/hc/en-us/profiles/1010800390-Vladimir-Reznichenko)

.
<meta charset='utf-8'><a class="_color-black" href="https://intellij-support.jetbrains.com/hc/en-us/profiles/1010800390-Vladimir-Reznichenko" style="background-color: rgb(255, 255, 255); margin: 0px; padding: 0px; border: 0px; font-size: 14px; vertical-align: baseline; cursor: pointer; text-decoration: none; color: rgb(22, 22, 22); font-family: &quot;Gotham SSm A&quot;, &quot;Gotham SSm B&quot;, Helvetica, Arial, sans-serif; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: bold; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px;">Vladimir Reznichenko</a>
````````````````````````````````


## Issue 138

Issue #138, HTML to Markdown converter missing list end for two consecutive lists

```````````````````````````````` example Issue 138: 1
**Ordered**
1. Item 1
2. Item 2
3. Item 3

<!-- -->
1. [Example.com](http://www.example.com)
2. [Google](http://www.google.com)
3. [Yahoo!](http://www.yahoo.com)
4. [Another Example.com](http://www.example.com)

.
<p><strong>Ordered</strong></p>
<ol>
  <li>Item 1</li>
  <li>Item 2</li>
  <li>Item 3</li>
</ol>
<ol>
  <li><a href="http://www.example.com">Example.com</a></li>
  <li><a href="http://www.google.com">Google</a></li>
  <li><a href="http://www.yahoo.com">Yahoo!</a></li>
  <li><a href="http://www.example.com">Another Example.com</a></li>
</ol>
````````````````````````````````


end on double blank line

```````````````````````````````` example(Issue 138: 2) options(list-break)
**Ordered**
1. Item 1
2. Item 2
3. Item 3


1. [Example.com](http://www.example.com)
2. [Google](http://www.google.com)
3. [Yahoo!](http://www.yahoo.com)
4. [Another Example.com](http://www.example.com)

.
<p><strong>Ordered</strong></p>
<ol>
  <li>Item 1</li>
  <li>Item 2</li>
  <li>Item 3</li>
</ol>
<ol>
  <li><a href="http://www.example.com">Example.com</a></li>
  <li><a href="http://www.google.com">Google</a></li>
  <li><a href="http://www.yahoo.com">Yahoo!</a></li>
  <li><a href="http://www.example.com">Another Example.com</a></li>
</ol>
````````````````````````````````


## Issue  149

Issue #149

```````````````````````````````` example Issue  149: 1
.
<div>
<strong>→</strong>
</div>
````````````````````````````````


