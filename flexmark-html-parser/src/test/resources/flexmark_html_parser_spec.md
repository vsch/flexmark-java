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
^Expected^ rendered HTML
.
<p><sup>Expected</sup> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 9
`Expected` rendered HTML
.
<p><code>Expected</code> rendered HTML</p>
````````````````````````````````


``Expected `a``

```````````````````````````````` example Emphasis: 10
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
*** * ** * ***
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
| Abc  | Def |
|------|-----|
| span      ||
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
| Abc  | Def |
| Hij  | Lmn |
|------|-----|
| span      ||
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

```````````````````````````````` example Tables: 48
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
:   Definition 1 line 1 ... Definition 1 line 2
:   Definition 2 line 1 ... Definition 2 line 2

Term 2
:   Definition 3 line 2 ... Definition 3 line 2
:   Definition 4 line 2 ... Definition 4 line 2
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

:   Definition 1 paragraph 1 line 1 ... Definition 1 paragraph 1 line 2

    Definition 1 paragraph 2 line 1 ... Definition 1 paragraph 2 line 2

Term 2

:   Definition 1 paragraph 1 line 1 ... Definition 1 paragraph 1 line 2 (lazy)

    Definition 1 paragraph 2 line 1 ... Definition 1 paragraph 2 line 2 (lazy)
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

:   Definition 1 paragraph 1 line 1 ... Definition 1 paragraph 1 line 2 (lazy)

    Definition 1 paragraph 2 line 1 ... Definition 1 paragraph 2 line 2

:   Definition 2 paragraph 1 line 1 ... Definition 2 paragraph 1 line 2 (lazy)

Term 3

:   Definition 3 (no paragraph)

:   Definition 4 (no paragraph)

:   Definition 5 line 1 ... Definition 5 line 2 (no paragraph)

:   Definition 6 paragraph 1 line 1 ... Definition 6 paragraph 1 line 2

:   Definition 7 (no paragraph)

:   Definition 8 paragraph 1 line 1 (forced paragraph) ... Definition 8 paragraph 1 line 2

    Definition 8 paragraph 2 line 1

Term 4

:   Definition 9 paragraph 1 line 1 (forced paragraph) ... Definition 9 paragraph 1 line 2

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
:   Definition of above term Another definition of above term
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
Text&nbsp;

Heading
=======
.
<p>Text&nbsp;</p>
<h1>Heading</h1>
````````````````````````````````


Headings need blank line

```````````````````````````````` example Issue Tests: 3
**Attention!&nbsp;**Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple [form](https://tech.yandex.com/key/form.xml?service=trnsl).
.
<div class="warning">
  <strong>Attention!&nbsp;</strong>Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple <a target="_blank" href="https://tech.yandex.com/key/form.xml?service=trnsl">form</a>.
</div>
````````````````````````````````


handle `pre` without `code`

```````````````````````````````` example Issue Tests: 4
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


```````````````````````````````` example Issue Tests: 5
<header class="comment__header">
priyanka • [3 years ago](http://www.rgagnon.com/javadetails/java-convert-html-to-pdf-using-yahp.html#comment-1055568391 "Monday, September 23, 2013 5:49 AM")
</header>

Though i am not getting any error ...no pdf is getting created
see more
<footer class="comment__footer">
<menu class="comment-footer__menu">
* [ 2 ](# "")
* •
* [ Reply](#)
* •
* Share ›
  * <button class="share__button" data-action="share:twitter">Twitter</button>
  * <button class="share__button" data-action="share:facebook">Facebook</button>
  * <input class="share__button" value="http://disq.us/p/hgghs7" name="Link" title="Click to copy post link" data-action="copy-link" readonly>
* [](#)
</menu>
</footer>
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

```````````````````````````````` example Issue Tests: 6
.
<iframe id="dsq-app4" name="dsq-app4" allowtransparency="true" frameborder="0" scrolling="no" tabindex="0" title="Disqus" width="100%" src="http://disqusads.com/ads-iframe/taboola/?category=tech&amp;stories_allowed=0&amp;service=dynamic&amp;safetylevel=20&amp;display_allowed=1&amp;video_allowed=0&amp;provider=taboola&amp;thumbnails_allowed=1&amp;experiment=network_default&amp;variant=fallthrough&amp;display_only=0&amp;t=1485117195&amp;links_allowed=1&amp;position=bottom&amp;shortname=realshowto&amp;display_bidding_allowed=0&amp;forum_shortname=realshowto&amp;forum_pk=1341383&amp;anchorColor=%23039be5&amp;colorScheme=light&amp;sourceUrl=http%3A%2F%2Fwww.rgagnon.com%2Fjavadetails%2Fjava-convert-html-to-pdf-using-yahp.html&amp;typeface=sans-serif&amp;disqus_version=9464b90" style="box-sizing: inherit; width: 1px !important; min-width: 100%; border: none !important; overflow: hidden !important; height: 586px !important;"></iframe>
````````````````````````````````


