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

```````````````````````````````` example Text: 1
Expected rendered HTML
.
<p>Expected rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Text: 2
Expected rendered HTML  
Another line
.
<p>Expected rendered HTML<br>Another line</p>
````````````````````````````````


```````````````````````````````` example Text: 3
Expected rendered HTML  

Another line
.
<p>Expected rendered HTML<br><br>Another line</p>
````````````````````````````````


```````````````````````````````` example Text: 4
15 days ago by
.
<relative-time datetime="2018-07-31T12:16:08Z" title="Jul 31, 2018, 8:16 AM GMT-4" style="box-sizing: border-box;">15 days ago</relative-time><span class="Apple-converted-space"> </span>by<span class="Apple-converted-space"> </span>
````````````````````````````````


## Emphasis

```````````````````````````````` example Emphasis: 1
**Expected** rendered HTML
.
<p><strong>Expected</strong> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 2
**Expected** rendered HTML
.
<p><b>Expected</b> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 3
*Expected* rendered HTML
.
<p><em>Expected</em> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 4
*Expected* rendered HTML
.
<p><i>Expected</i> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 5
++Expected++ rendered HTML
.
<p><ins>Expected</ins> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 6
~~Expected~~ rendered HTML
.
<p><del>Expected</del> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 7
~Expected~ rendered HTML
.
<p><sub>Expected</sub> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 8
H~2~O
.
<p>H<sub>2</sub>O</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 9
^Expected^ rendered HTML
.
<p><sup>Expected</sup> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 10
H^2^O
.
<p>H<sup>2</sup>O</p>
````````````````````````````````


```````````````````````````````` example Emphasis: 11
`Expected` rendered HTML
.
<p><code>Expected</code> rendered HTML</p>
````````````````````````````````


``Expected `a``

```````````````````````````````` example Emphasis: 12
``Expected `a`` rendered HTML
.
<p><code>Expected `a</code> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 13) options(text-ext-inline-strong)
Expected rendered HTML
.
<p><strong>Expected</strong> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 14) options(text-ext-inline-strong)
Expected rendered HTML
.
<p><b>Expected</b> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 15) options(text-ext-inline-emphasis)
Expected rendered HTML
.
<p><em>Expected</em> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 16) options(text-ext-inline-emphasis)
Expected rendered HTML
.
<p><i>Expected</i> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 17) options(text-ext-inline-ins)
Expected rendered HTML
.
<p><ins>Expected</ins> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 18) options(text-ext-inline-del)
Expected rendered HTML
.
<p><del>Expected</del> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 19) options(text-ext-inline-sub)
Expected rendered HTML
.
<p><sub>Expected</sub> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 20) options(text-ext-inline-sub)
H2O
.
<p>H<sub>2</sub>O</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 21) options(text-ext-inline-sup)
Expected rendered HTML
.
<p><sup>Expected</sup> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 22) options(text-ext-inline-sup)
H2O
.
<p>H<sup>2</sup>O</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 23) options(text-ext-inline-code)
Expected rendered HTML
.
<p><code>Expected</code> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 24) options(text-ext-inline-strong)
Expected rendered HTML
.
<p><strong>Expected</strong> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 25) options(text-ext-inline-strong)
Expected rendered HTML
.
<p><b>Expected</b> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 26) options(text-ext-inline-emphasis)
Expected rendered HTML
.
<p><em>Expected</em> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 27) options(text-ext-inline-emphasis)
Expected rendered HTML
.
<p><i>Expected</i> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 28) options(text-ext-inline-ins)
Expected rendered HTML
.
<p><ins>Expected</ins> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 29) options(text-ext-inline-del)
Expected rendered HTML
.
<p><del>Expected</del> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 30) options(text-ext-inline-sub)
Expected rendered HTML
.
<p><sub>Expected</sub> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 31) options(text-ext-inline-sub)
H2O
.
<p>H<sub>2</sub>O</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 32) options(text-ext-inline-sup)
Expected rendered HTML
.
<p><sup>Expected</sup> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 33) options(text-ext-inline-sup)
H2O
.
<p>H<sup>2</sup>O</p>
````````````````````````````````


```````````````````````````````` example(Emphasis: 34) options(text-ext-inline-code)
Expected rendered HTML
.
<p><code>Expected</code> rendered HTML</p>
````````````````````````````````


``Expected `a``

```````````````````````````````` example(Emphasis: 35) options(text-ext-inline-code)
Expected `a rendered HTML
.
<p><code>Expected `a</code> rendered HTML</p>
````````````````````````````````


## HTML Emphasis

```````````````````````````````` example(HTML Emphasis: 1) options(html-ext-inline-strong)
<strong>Expected</strong> rendered HTML
.
<p><strong>Expected</strong> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(HTML Emphasis: 2) options(html-ext-inline-strong)
<b>Expected</b> rendered HTML
.
<p><b>Expected</b> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(HTML Emphasis: 3) options(html-ext-inline-emphasis)
<em>Expected</em> rendered HTML
.
<p><em>Expected</em> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(HTML Emphasis: 4) options(html-ext-inline-emphasis)
<i>Expected</i> rendered HTML
.
<p><i>Expected</i> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(HTML Emphasis: 5) options(html-ext-inline-ins)
<ins>Expected</ins> rendered HTML
.
<p><ins>Expected</ins> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(HTML Emphasis: 6) options(html-ext-inline-del)
<del>Expected</del> rendered HTML
.
<p><del>Expected</del> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(HTML Emphasis: 7) options(html-ext-inline-sub)
<sub>Expected</sub> rendered HTML
.
<p><sub>Expected</sub> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(HTML Emphasis: 8) options(html-ext-inline-sub)
H<sub>2</sub>O
.
<p>H<sub>2</sub>O</p>
````````````````````````````````


```````````````````````````````` example(HTML Emphasis: 9) options(html-ext-inline-sup)
<sup>Expected</sup> rendered HTML
.
<p><sup>Expected</sup> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(HTML Emphasis: 10) options(html-ext-inline-sup)
H<sup>2</sup>O
.
<p>H<sup>2</sup>O</p>
````````````````````````````````


```````````````````````````````` example(HTML Emphasis: 11) options(html-ext-inline-code)
<code>Expected</code> rendered HTML
.
<p><code>Expected</code> rendered HTML</p>
````````````````````````````````


``Expected `a``

```````````````````````````````` example(HTML Emphasis: 12) options(html-ext-inline-code)
<code>Expected \`a</code> rendered HTML
.
<p><code>Expected `a</code> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(HTML Emphasis: 13) options(html-ext-inline-strong, html-ext-inline-emphasis)
<strong>Expected <em>Nested</em></strong> rendered HTML
.
<p><strong>Expected <em>Nested</em></strong> rendered HTML</p>
````````````````````````````````


## Nested Emphasis

```````````````````````````````` example(Nested Emphasis: 1) options(text-ext-inline-strong, html-ext-inline-emphasis)
Expected <em>Nested</em> rendered HTML
.
<p><strong>Expected <em>Nested</em></strong> rendered HTML</p>
````````````````````````````````


```````````````````````````````` example(Nested Emphasis: 2) options(html-ext-inline-emphasis)
**Expected <em>Nested</em>** rendered HTML
.
<p><strong>Expected <em>Nested</em></strong> rendered HTML</p>
````````````````````````````````


## Bullet Lists

```````````````````````````````` example Bullet Lists: 1
* item
.
<ul>
<li>item</li>
</ul>
````````````````````````````````


```````````````````````````````` example Bullet Lists: 2
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


```````````````````````````````` example Bullet Lists: 3
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


```````````````````````````````` example Bullet Lists: 4
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


## Escape Special Chars

Escape special chars

```````````````````````````````` example Escape Special Chars: 1
The following instructions are for **\*nix** type systems, specifically this is a Linux example.
.
<span style="caret-color: rgb(36, 41, 46); color: rgb(36, 41, 46); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-size: 16px; font-style: normal; font-variant-caps: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: auto; word-spacing: 0px; -webkit-text-size-adjust: auto; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration: none; display: inline !important; float: none;">The following instructions are for<span class="Apple-converted-space"> </span></span><strong
    style="box-sizing: border-box; font-weight: 600; caret-color: rgb(36, 41, 46); color: rgb(36, 41, 46); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-size: 16px; font-style: normal; font-variant-caps: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: auto; word-spacing: 0px; -webkit-text-size-adjust: auto; -webkit-text-stroke-width: 0px; text-decoration: none;">*nix</strong><span
    style="caret-color: rgb(36, 41, 46); color: rgb(36, 41, 46); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-size: 16px; font-style: normal; font-variant-caps: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: auto; word-spacing: 0px; -webkit-text-size-adjust: auto; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration: none; display: inline !important; float: none;"><span class="Apple-converted-space"> </span>type systems, specifically this is a Linux example.</span>
````````````````````````````````


Escape special chars skipped

```````````````````````````````` example(Escape Special Chars: 2) options(skip-char-escape)
The following instructions are for ***nix** type systems, specifically this is a Linux example.
.
<span style="caret-color: rgb(36, 41, 46); color: rgb(36, 41, 46); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-size: 16px; font-style: normal; font-variant-caps: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: auto; word-spacing: 0px; -webkit-text-size-adjust: auto; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration: none; display: inline !important; float: none;">The following instructions are for<span class="Apple-converted-space"> </span></span><strong
    style="box-sizing: border-box; font-weight: 600; caret-color: rgb(36, 41, 46); color: rgb(36, 41, 46); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-size: 16px; font-style: normal; font-variant-caps: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: auto; word-spacing: 0px; -webkit-text-size-adjust: auto; -webkit-text-stroke-width: 0px; text-decoration: none;">*nix</strong><span
    style="caret-color: rgb(36, 41, 46); color: rgb(36, 41, 46); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-size: 16px; font-style: normal; font-variant-caps: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: auto; word-spacing: 0px; -webkit-text-size-adjust: auto; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration: none; display: inline !important; float: none;"><span class="Apple-converted-space"> </span>type systems, specifically this is a Linux example.</span>
````````````````````````````````


```````````````````````````````` example(Escape Special Chars: 3) options(skip-char-escape)
| c | d | | - | - | | *a | b* | | `e | f` | | [g | h](http://a.com) |
.
<meta charset='utf-8'><span style="color: rgb(51, 51, 51); font-family: Consolas, Menlo, Monaco, &quot;Lucida Console&quot;, &quot;Liberation Mono&quot;, &quot;DejaVu Sans Mono&quot;, &quot;Bitstream Vera Sans Mono&quot;, &quot;Courier New&quot;, monospace; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: pre; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(249, 249, 249); text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;">| c | d |
| - | - |
| </span><span class="hljs-emphasis" style="color: rgb(51, 51, 51); font-family: Consolas, Menlo, Monaco, &quot;Lucida Console&quot;, &quot;Liberation Mono&quot;, &quot;DejaVu Sans Mono&quot;, &quot;Bitstream Vera Sans Mono&quot;, &quot;Courier New&quot;, monospace; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: pre; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-decoration-style: initial; text-decoration-color: initial;">*a | b*</span><span style="color: rgb(51, 51, 51); font-family: Consolas, Menlo, Monaco, &quot;Lucida Console&quot;, &quot;Liberation Mono&quot;, &quot;DejaVu Sans Mono&quot;, &quot;Bitstream Vera Sans Mono&quot;, &quot;Courier New&quot;, monospace; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: pre; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(249, 249, 249); text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;"> |
| </span><span class="hljs-code" style="color: rgb(51, 51, 51); font-family: Consolas, Menlo, Monaco, &quot;Lucida Console&quot;, &quot;Liberation Mono&quot;, &quot;DejaVu Sans Mono&quot;, &quot;Bitstream Vera Sans Mono&quot;, &quot;Courier New&quot;, monospace; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: pre; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-decoration-style: initial; text-decoration-color: initial;">`e | f`</span><span style="color: rgb(51, 51, 51); font-family: Consolas, Menlo, Monaco, &quot;Lucida Console&quot;, &quot;Liberation Mono&quot;, &quot;DejaVu Sans Mono&quot;, &quot;Bitstream Vera Sans Mono&quot;, &quot;Courier New&quot;, monospace; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: pre; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(249, 249, 249); text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;"> |
| [</span><span class="hljs-string" style="color: rgb(221, 17, 68); font-family: Consolas, Menlo, Monaco, &quot;Lucida Console&quot;, &quot;Liberation Mono&quot;, &quot;DejaVu Sans Mono&quot;, &quot;Bitstream Vera Sans Mono&quot;, &quot;Courier New&quot;, monospace; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: pre; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-decoration-style: initial; text-decoration-color: initial;">g | h</span><span style="color: rgb(51, 51, 51); font-family: Consolas, Menlo, Monaco, &quot;Lucida Console&quot;, &quot;Liberation Mono&quot;, &quot;DejaVu Sans Mono&quot;, &quot;Bitstream Vera Sans Mono&quot;, &quot;Courier New&quot;, monospace; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: pre; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(249, 249, 249); text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;">](</span><span class="hljs-link" style="color: rgb(51, 51, 51); font-family: Consolas, Menlo, Monaco, &quot;Lucida Console&quot;, &quot;Liberation Mono&quot;, &quot;DejaVu Sans Mono&quot;, &quot;Bitstream Vera Sans Mono&quot;, &quot;Courier New&quot;, monospace; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: pre; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; text-decoration-style: initial; text-decoration-color: initial;">http://a.com</span><span style="color: rgb(51, 51, 51); font-family: Consolas, Menlo, Monaco, &quot;Lucida Console&quot;, &quot;Liberation Mono&quot;, &quot;DejaVu Sans Mono&quot;, &quot;Bitstream Vera Sans Mono&quot;, &quot;Courier New&quot;, monospace; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: pre; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(249, 249, 249); text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;">) |</span>
````````````````````````````````


```````````````````````````````` example Escape Special Chars: 4
[535 Closed](https://github.com/vsch/idea-multimarkdown/issues?q=is%3Aissue+is%3Aclosed)  
Author  
Labels  
Projects  
Milestones
Assignee Sort  
*  
  [Emoji suggestions do not filter as you type](https://github.com/vsch/idea-multimarkdown/issues/624)[bug](https://github.com/vsch/idea-multimarkdown/issues?q=is%3Aissue+is%3Aopen+label%3Abug "bug")  
  #624 opened 15 days ago by [jcfranco](https://github.com/vsch/idea-multimarkdown/issues?q=is%3Aissue+is%3Aopen+author%3Ajcfranco "Open issues created by jcfranco")  
  [1](https://github.com/vsch/idea-multimarkdown/issues/624)
*  
  [Directory linking occasionally broken](https://github.com/vsch/idea-multimarkdown/issues/623)[bug](https://github.com/vsch/idea-multimarkdown/issues?q=is%3Aissue+is%3Aopen+label%3Abug "bug") [fix available](https://github.com/vsch/idea-multimarkdown/issues?q=is%3Aissue+is%3Aopen+label%3A%22fix+available%22 "fix available") [fixed for next release](https://github.com/vsch/idea-multimarkdown/issues?q=is%3Aissue+is%3Aopen+label%3A%22fixed+for+next+release%22 "fixed for next release")  
  #623 opened 15 days ago by [mhaas](https://github.com/vsch/idea-multimarkdown/issues?q=is%3Aissue+is%3Aopen+author%3Amhaas "Open issues created by mhaas")  
  [4](https://github.com/vsch/idea-multimarkdown/issues/623)
* 

.
<div class="table-list-header" id="js-issues-toolbar" style="box-sizing: border-box; position: relative; margin-top: 20px; background-color: rgb(246, 248, 250); border: 1px solid rgb(225, 228, 232); border-top-left-radius: 3px; border-top-right-radius: 3px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px; caret-color: rgb(36, 41, 46); color: rgb(36, 41, 46); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-size: 14px; font-style: normal; font-variant-caps: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: auto; word-spacing: 0px; -webkit-text-size-adjust: auto; -webkit-text-stroke-width: 0px; text-decoration: none;">
  <div class="table-list-filters" style="box-sizing: border-box;">
    <div class="table-list-header-toggle states float-left pl-3" style="box-sizing: border-box; float: left !important; padding-left: 16px !important;"><br class="Apple-interchange-newline"><span class="Apple-converted-space"> </span><a href="https://github.com/vsch/idea-multimarkdown/issues?q=is%3Aissue+is%3Aclosed" class="btn-link " style="box-sizing: border-box; background-color: transparent; color: rgb(88, 96, 105); text-decoration: none; display: inline-block; padding: 13px 0px; font-size: inherit; white-space: nowrap; cursor: pointer; -webkit-user-select: none; border: 0px; -webkit-appearance: none; position: relative; font-weight: 400; margin-left: 10px;">
      <svg class="octicon octicon-check" viewBox="0 0 12 16" version="1.1" width="12" height="16" aria-hidden="true">
        <path fill-rule="evenodd" d="M12 5l-8 8-4-4 1.5-1.5L4 10l6.5-6.5L12 5z"></path>
      </svg>
      <span class="Apple-converted-space"> </span>535 Closed</a></div>
    <div class="table-list-header-toggle float-right" style="box-sizing: border-box; float: right !important;">
      <details class="details-reset details-overlay float-left select-menu" style="box-sizing: border-box; display: block; float: left !important; position: relative;">
        <summary class="btn-link select-menu-button" data-hotkey="u" aria-haspopup="menu" style="box-sizing: border-box; display: inline-block; cursor: pointer; padding: 13px 15px; font-size: inherit; color: rgb(88, 96, 105); text-decoration: none; white-space: nowrap; -webkit-user-select: none; background-color: transparent; border: 0px; -webkit-appearance: none; list-style: none; position: relative; font-weight: 400;">Author<span class="Apple-converted-space"> </span></summary>
      </details>
      <div class="float-left select-menu label-select-menu js-menu-container js-select-menu js-load-contents" data-contents-url="/vsch/idea-multimarkdown/issues/show_menu_content?partial=issues%2Ffilters%2Flabels_content&amp;q=is%3Aissue+is%3Aopen" style="box-sizing: border-box; float: left !important; position: relative;">
        <button class="btn-link select-menu-button js-menu-target" type="button" aria-haspopup="true" aria-expanded="false" data-hotkey="l" style="box-sizing: border-box; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-caps: inherit; font-weight: 400; font-stretch: inherit; line-height: inherit; margin: 0px; overflow: visible; text-transform: none; -webkit-appearance: button; cursor: pointer; border-top-left-radius: 0px; border-top-right-radius: 0px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px; display: inline-block; padding: 13px 15px; color: rgb(88, 96, 105); text-decoration: none; white-space: nowrap; -webkit-user-select: none; background-color: transparent; border: 0px; position: relative;">Labels<span class="Apple-converted-space"> </span></button>
      </div>
      <div class="float-left select-menu js-menu-container js-select-menu js-load-contents" data-contents-url="/vsch/idea-multimarkdown/issues/show_menu_content?partial=issues%2Ffilters%2Fprojects_content&amp;pulls_only=false&amp;q=is%3Aissue+is%3Aopen" style="box-sizing: border-box; float: left !important; position: relative;">
        <button class="btn-link select-menu-button js-menu-target" type="button" aria-haspopup="true" aria-expanded="false" data-hotkey="m" style="box-sizing: border-box; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-caps: inherit; font-weight: 400; font-stretch: inherit; line-height: inherit; margin: 0px; overflow: visible; text-transform: none; -webkit-appearance: button; cursor: pointer; border-top-left-radius: 0px; border-top-right-radius: 0px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px; display: inline-block; padding: 13px 15px; color: rgb(88, 96, 105); text-decoration: none; white-space: nowrap; -webkit-user-select: none; background-color: transparent; border: 0px; position: relative;">Projects<span class="Apple-converted-space"> </span></button>
      </div>
      <div class="float-left select-menu js-menu-container js-select-menu js-load-contents" data-contents-url="/vsch/idea-multimarkdown/issues/show_menu_content?partial=issues%2Ffilters%2Fmilestones_content&amp;q=is%3Aissue+is%3Aopen" style="box-sizing: border-box; float: left !important; position: relative;">
        <button class="btn-link select-menu-button js-menu-target" type="button" aria-haspopup="true" aria-expanded="false" data-hotkey="m" style="box-sizing: border-box; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-caps: inherit; font-weight: 400; font-stretch: inherit; line-height: inherit; margin: 0px; overflow: visible; text-transform: none; -webkit-appearance: button; cursor: pointer; border-top-left-radius: 0px; border-top-right-radius: 0px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px; display: inline-block; padding: 13px 15px; color: rgb(88, 96, 105); text-decoration: none; white-space: nowrap; -webkit-user-select: none; background-color: transparent; border: 0px; position: relative;">Milestones<span class="Apple-converted-space"> </span></button>
      </div>
      <details class="details-reset details-overlay float-left select-menu" style="box-sizing: border-box; display: block; float: left !important; position: relative;">
        <summary class="btn-link select-menu-button" data-hotkey="a" aria-haspopup="menu" style="box-sizing: border-box; display: inline-block; cursor: pointer; padding: 13px 15px; font-size: inherit; color: rgb(88, 96, 105); text-decoration: none; white-space: nowrap; -webkit-user-select: none; background-color: transparent; border: 0px; -webkit-appearance: none; list-style: none; position: relative; font-weight: 400;">Assignee<span class="Apple-converted-space"> </span></summary>
      </details>
      <details class="details-reset details-overlay select-menu float-left js-dropdown-details js-select-menu" style="box-sizing: border-box; display: block; float: left !important; position: relative;">
        <summary class="btn-link select-menu-button" aria-haspopup="true" style="box-sizing: border-box; display: inline-block; cursor: pointer; padding: 13px 15px; font-size: inherit; color: rgb(88, 96, 105); text-decoration: none; white-space: nowrap; -webkit-user-select: none; background-color: transparent; border: 0px; -webkit-appearance: none; list-style: none; position: relative; font-weight: 400;">Sort<span class="sort-label" style="box-sizing: border-box;"></span><span class="Apple-converted-space"> </span></summary>
      </details>
    </div>
  </div>
</div>
<div class="border-right border-bottom border-left"
     style="box-sizing: border-box; border-right-width: 1px !important; border-right-style: solid !important; border-right-color: rgb(225, 228, 232) !important; border-bottom-width: 1px !important; border-bottom-style: solid !important; border-bottom-color: rgb(225, 228, 232) !important; border-left-width: 1px !important; border-left-style: solid !important; border-left-color: rgb(225, 228, 232) !important; caret-color: rgb(36, 41, 46); color: rgb(36, 41, 46); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-size: 14px; font-style: normal; font-variant-caps: normal; font-weight: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: auto; word-spacing: 0px; -webkit-text-size-adjust: auto; -webkit-text-stroke-width: 0px; text-decoration: none;">
  <ul class="js-navigation-container js-active-navigation-container" style="box-sizing: border-box; padding-left: 0px; margin-top: 0px; margin-bottom: 0px;">
    <li id="issue_624" class="Box-row Box-row--focus-gray p-0 js-navigation-item js-issue-row selectable read" data-id="346271155" aria-selected="false" style="box-sizing: border-box; padding: 0px !important; margin-top: -1px; list-style-type: none; border-top-width: 1px; border-top-style: solid; border-top-color: transparent; border-top-left-radius: 2px; border-top-right-radius: 2px;">
      <div class="d-table table-fixed width-full Box-row--drag-hide position-relative" style="box-sizing: border-box; position: relative !important; width: 977.6470336914063px; display: table !important; table-layout: fixed !important;"><label class="float-left py-2 pl-3" style="box-sizing: border-box; font-weight: 600; float: left !important; padding-top: 8px !important; padding-bottom: 8px !important; padding-left: 16px !important;"><input type="checkbox" class="js-check-all-item js-issues-list-check" name="issues[]" value="624" style="box-sizing: border-box; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-caps: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; margin: 0px; overflow: visible; padding: 0px;"></label>
        <div class="float-left pt-2 pl-3" style="box-sizing: border-box; float: left !important; padding-top: 8px !important; padding-left: 16px !important;"><span class="tooltipped tooltipped-n" aria-label="Open issue" style="box-sizing: border-box; position: relative;"><svg class="octicon octicon-issue-opened open" viewBox="0 0 14 16" version="1.1" width="14" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7 2.3c3.14 0 5.7 2.56 5.7 5.7s-2.56 5.7-5.7 5.7A5.71 5.71 0 0 1 1.3 8c0-3.14 2.56-5.7 5.7-5.7zM7 1C3.14 1 0 4.14 0 8s3.14 7 7 7 7-3.14 7-7-3.14-7-7-7zm1 3H6v5h2V4zm0 6H6v2h2v-2z"></path></svg></span></div>
        <div class="float-left col-9 lh-condensed p-2" style="box-sizing: border-box; width: 733.2352905273438px; float: left !important; padding: 8px !important; line-height: 1.25 !important;"><a href="https://github.com/vsch/idea-multimarkdown/issues/624" class="link-gray-dark v-align-middle no-underline h4 js-navigation-open" style="box-sizing: border-box; background-color: transparent; color: rgb(36, 41, 46) !important; text-decoration: none !important; vertical-align: middle !important; font-size: 16px !important; font-weight: 600 !important;">Emoji suggestions do not filter as you type<span class="Apple-converted-space"> </span></a><span class="labels lh-default" style="box-sizing: border-box; line-height: 1.5 !important; position: relative;"><a class="d-inline-block IssueLabel v-align-text-top" title="bug" href="https://github.com/vsch/idea-multimarkdown/issues?q=is%3Aissue+is%3Aopen+label%3Abug"
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          style="box-sizing: border-box; background-color: rgb(252, 41, 41); color: rgb(0, 0, 0); text-decoration: none; vertical-align: text-top !important; display: inline-block !important; height: 20px; padding: 0.15em 4px; font-size: 12px; font-weight: 600; line-height: 15px; border-top-left-radius: 2px; border-top-right-radius: 2px; border-bottom-right-radius: 2px; border-bottom-left-radius: 2px; box-shadow: rgba(27, 31, 35, 0.117647) 0px -1px 0px inset;">bug</a></span>
          <div class="mt-1 text-small text-gray" style="box-sizing: border-box; color: rgb(88, 96, 105) !important; margin-top: 4px !important; font-size: 12px !important;"><span class="opened-by" style="box-sizing: border-box;">#624 opened<span class="Apple-converted-space"> </span><relative-time datetime="2018-07-31T16:48:32Z" title="Jul 31, 2018, 12:48 PM GMT-4" style="box-sizing: border-box;">15 days ago</relative-time><span class="Apple-converted-space"> </span>by<span class="Apple-converted-space"> </span><a class="muted-link" title="Open issues created by jcfranco" data-hovercard-user-id="197440" data-octo-click="hovercard-link-click" data-octo-dimensions="link_type:self" href="https://github.com/vsch/idea-multimarkdown/issues?q=is%3Aissue+is%3Aopen+author%3Ajcfranco" aria-describedby="hovercard-aria-description" style="box-sizing: border-box; background-color: transparent; color: rgb(88, 96, 105) !important; text-decoration: none;">jcfranco</a><span
              class="Apple-converted-space"> </span></span><span class="issue-meta-section css-truncate issue-milestone ml-2" style="box-sizing: border-box; margin-left: 8px !important; max-width: 240px;"></span></div>
        </div>
        <div class="float-right col-2" style="box-sizing: border-box; width: 162.94117736816406px; float: right !important;">
          <div class="float-left col-7 pt-2 pr-3 text-right" style="box-sizing: border-box; width: 95.03675842285156px; float: left !important; padding-top: 8px !important; padding-right: 16px !important; text-align: right !important;">
            <div class="AvatarStack AvatarStack--right " style="box-sizing: border-box; position: relative; min-width: 26px; height: 20px;">
              <div class="AvatarStack-body tooltipped tooltipped-sw tooltipped-multiline tooltipped-align-right-1 mt-1" aria-label="Assigned to " style="box-sizing: border-box; position: absolute; margin-top: 4px !important; display: flex; background-color: rgb(255, 255, 255); right: 0px; flex-direction: row-reverse; background-position: initial initial; background-repeat: initial initial;"></div>
            </div>
          </div>
          <div class="float-right col-5 no-wrap pt-2 pr-3 text-right" style="box-sizing: border-box; width: 67.88602447509766px; float: right !important; padding-top: 8px !important; padding-right: 16px !important; text-align: right !important; white-space: nowrap !important;"><a href="https://github.com/vsch/idea-multimarkdown/issues/624" class="muted-link" aria-label="1 comment" style="box-sizing: border-box; background-color: transparent; color: rgb(88, 96, 105) !important; text-decoration: none;">
            <svg class="octicon octicon-comment v-align-middle" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true">
              <path fill-rule="evenodd" d="M14 1H2c-.55 0-1 .45-1 1v8c0 .55.45 1 1 1h2v3.5L7.5 11H14c.55 0 1-.45 1-1V2c0-.55-.45-1-1-1zm0 9H7l-2 2v-2H2V2h12v8z"></path>
            </svg>
            <span class="Apple-converted-space"> </span><span class="text-small text-bold" style="box-sizing: border-box; font-size: 12px !important; font-weight: 600 !important;">1</span></a></div>
        </div>
      </div>
    </li>
    <li id="issue_623" class="Box-row Box-row--focus-gray p-0 js-navigation-item js-issue-row selectable read navigation-focus" data-id="346161540" aria-selected="true" style="box-sizing: border-box; padding: 0px !important; margin-top: -1px; list-style-type: none; border-top-width: 1px; border-top-style: solid; border-top-color: rgb(225, 228, 232); background-color: rgb(246, 248, 250);">
      <div class="d-table table-fixed width-full Box-row--drag-hide position-relative" style="box-sizing: border-box; position: relative !important; width: 977.6470336914063px; display: table !important; table-layout: fixed !important;"><label class="float-left py-2 pl-3" style="box-sizing: border-box; font-weight: 600; float: left !important; padding-top: 8px !important; padding-bottom: 8px !important; padding-left: 16px !important;"><input type="checkbox" class="js-check-all-item js-issues-list-check" name="issues[]" value="623" style="box-sizing: border-box; font-family: inherit; font-size: inherit; font-style: inherit; font-variant-caps: inherit; font-weight: inherit; font-stretch: inherit; line-height: inherit; margin: 0px; overflow: visible; padding: 0px;"></label>
        <div class="float-left pt-2 pl-3" style="box-sizing: border-box; float: left !important; padding-top: 8px !important; padding-left: 16px !important;"><span class="tooltipped tooltipped-n" aria-label="Open issue" style="box-sizing: border-box; position: relative;"><svg class="octicon octicon-issue-opened open" viewBox="0 0 14 16" version="1.1" width="14" height="16" aria-hidden="true"><path fill-rule="evenodd" d="M7 2.3c3.14 0 5.7 2.56 5.7 5.7s-2.56 5.7-5.7 5.7A5.71 5.71 0 0 1 1.3 8c0-3.14 2.56-5.7 5.7-5.7zM7 1C3.14 1 0 4.14 0 8s3.14 7 7 7 7-3.14 7-7-3.14-7-7-7zm1 3H6v5h2V4zm0 6H6v2h2v-2z"></path></svg></span></div>
        <div class="float-left col-9 lh-condensed p-2" style="box-sizing: border-box; width: 733.2352905273438px; float: left !important; padding: 8px !important; line-height: 1.25 !important;"><a href="https://github.com/vsch/idea-multimarkdown/issues/623" class="link-gray-dark v-align-middle no-underline h4 js-navigation-open" style="box-sizing: border-box; background-color: transparent; color: rgb(36, 41, 46) !important; text-decoration: none !important; vertical-align: middle !important; font-size: 16px !important; font-weight: 600 !important;">Directory linking occasionally broken<span class="Apple-converted-space"> </span></a><span class="labels lh-default" style="box-sizing: border-box; line-height: 1.5 !important; position: relative;"><a class="d-inline-block IssueLabel v-align-text-top" title="bug" href="https://github.com/vsch/idea-multimarkdown/issues?q=is%3Aissue+is%3Aopen+label%3Abug"
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    style="box-sizing: border-box; background-color: rgb(252, 41, 41); color: rgb(0, 0, 0); text-decoration: none; vertical-align: text-top !important; display: inline-block !important; height: 20px; padding: 0.15em 4px; font-size: 12px; font-weight: 600; line-height: 15px; border-top-left-radius: 2px; border-top-right-radius: 2px; border-bottom-right-radius: 2px; border-bottom-left-radius: 2px; box-shadow: rgba(27, 31, 35, 0.117647) 0px -1px 0px inset;">bug</a><span
            class="Apple-converted-space"> </span><a class="d-inline-block IssueLabel v-align-text-top" title="fix available" href="https://github.com/vsch/idea-multimarkdown/issues?q=is%3Aissue+is%3Aopen+label%3A%22fix+available%22" style="box-sizing: border-box; background-color: rgb(83, 25, 231); color: rgb(255, 255, 255); text-decoration: none; vertical-align: text-top !important; display: inline-block !important; height: 20px; padding: 0.15em 4px; font-size: 12px; font-weight: 600; line-height: 15px; border-top-left-radius: 2px; border-top-right-radius: 2px; border-bottom-right-radius: 2px; border-bottom-left-radius: 2px; box-shadow: rgba(27, 31, 35, 0.117647) 0px -1px 0px inset;">fix available</a><span class="Apple-converted-space"> </span><a class="d-inline-block IssueLabel v-align-text-top" title="fixed for next release" href="https://github.com/vsch/idea-multimarkdown/issues?q=is%3Aissue+is%3Aopen+label%3A%22fixed+for+next+release%22"
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       style="box-sizing: border-box; background-color: rgb(145, 115, 230); color: rgb(0, 0, 0); text-decoration: none; vertical-align: text-top !important; display: inline-block !important; height: 20px; padding: 0.15em 4px; font-size: 12px; font-weight: 600; line-height: 15px; border-top-left-radius: 2px; border-top-right-radius: 2px; border-bottom-right-radius: 2px; border-bottom-left-radius: 2px; box-shadow: rgba(27, 31, 35, 0.117647) 0px -1px 0px inset;">fixed for next release</a></span>
          <div class="mt-1 text-small text-gray" style="box-sizing: border-box; color: rgb(88, 96, 105) !important; margin-top: 4px !important; font-size: 12px !important;"><span class="opened-by" style="box-sizing: border-box;">#623 opened<span class="Apple-converted-space"> </span><relative-time datetime="2018-07-31T12:16:08Z" title="Jul 31, 2018, 8:16 AM GMT-4" style="box-sizing: border-box;">15 days ago</relative-time><span class="Apple-converted-space"> </span>by<span class="Apple-converted-space"> </span><a class="muted-link" title="Open issues created by mhaas" data-hovercard-user-id="260872" data-octo-click="hovercard-link-click" data-octo-dimensions="link_type:self" href="https://github.com/vsch/idea-multimarkdown/issues?q=is%3Aissue+is%3Aopen+author%3Amhaas" aria-describedby="hovercard-aria-description" style="box-sizing: border-box; background-color: transparent; color: rgb(88, 96, 105) !important; text-decoration: none;">mhaas</a><span
              class="Apple-converted-space"> </span></span><span class="issue-meta-section css-truncate issue-milestone ml-2" style="box-sizing: border-box; margin-left: 8px !important; max-width: 240px;"></span></div>
        </div>
        <div class="float-right col-2" style="box-sizing: border-box; width: 162.94117736816406px; float: right !important;">
          <div class="float-left col-7 pt-2 pr-3 text-right" style="box-sizing: border-box; width: 95.03675842285156px; float: left !important; padding-top: 8px !important; padding-right: 16px !important; text-align: right !important;">
            <div class="AvatarStack AvatarStack--right " style="box-sizing: border-box; position: relative; min-width: 26px; height: 20px;">
              <div class="AvatarStack-body tooltipped tooltipped-sw tooltipped-multiline tooltipped-align-right-1 mt-1" aria-label="Assigned to " style="box-sizing: border-box; position: absolute; margin-top: 4px !important; display: flex; background-color: rgb(246, 251, 255); right: 0px; flex-direction: row-reverse; background-position: initial initial; background-repeat: initial initial;"></div>
            </div>
          </div>
          <div class="float-right col-5 no-wrap pt-2 pr-3 text-right" style="box-sizing: border-box; width: 67.88602447509766px; float: right !important; padding-top: 8px !important; padding-right: 16px !important; text-align: right !important; white-space: nowrap !important;"><a href="https://github.com/vsch/idea-multimarkdown/issues/623" class="muted-link" aria-label="4 comments" style="box-sizing: border-box; background-color: transparent; color: rgb(88, 96, 105) !important; text-decoration: none;">
            <svg class="octicon octicon-comment v-align-middle" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true">
              <path fill-rule="evenodd" d="M14 1H2c-.55 0-1 .45-1 1v8c0 .55.45 1 1 1h2v3.5L7.5 11H14c.55 0 1-.45 1-1V2c0-.55-.45-1-1-1zm0 9H7l-2 2v-2H2V2h12v8z"></path>
            </svg>
            <span class="Apple-converted-space"> </span><span class="text-small text-bold" style="box-sizing: border-box; font-size: 12px !important; font-weight: 600 !important;">4</span></a></div>
        </div>
      </div>
    </li>
    <li id="issue_622" class="Box-row Box-row--focus-gray p-0 js-navigation-item js-issue-row selectable read" data-id="345118696" aria-selected="false" style="box-sizing: border-box; padding: 0px !important; margin-top: -1px; list-style-type: none; border-top-width: 1px; border-top-style: solid; border-top-color: rgb(225, 228, 232);"></li>
  </ul>
</div><br class="Apple-interchange-newline">
````````````````````````````````


## Numbered Lists

```````````````````````````````` example Numbered Lists: 1
1. item
.
<ol>
<li>item</li>
</ol>
````````````````````````````````


```````````````````````````````` example Numbered Lists: 2
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


```````````````````````````````` example Numbered Lists: 3
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


```````````````````````````````` example Numbered Lists: 4
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

```````````````````````````````` example Task List Items: 1
* [ ] item
* [x] item

.
<ul>
<li><input type="checkbox">item</li>
<li><input type="checkbox" checked>item</li>
</ul>
<input type="checkbox">
````````````````````````````````


```````````````````````````````` example Task List Items: 2
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

```````````````````````````````` example Block Quotes: 1
> Expected rendered HTML
.
<blockquote>
  <p>Expected rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 2
> **Expected** rendered HTML
.
<blockquote>
  <p><strong>Expected</strong> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 3
> **Expected** rendered HTML
.
<blockquote>
  <p><b>Expected</b> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 4
> *Expected* rendered HTML
.
<blockquote>
  <p><em>Expected</em> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 5
> *Expected* rendered HTML
.
<blockquote>
  <p><i>Expected</i> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 6
> ++Expected++ rendered HTML
.
<blockquote>
  <p><ins>Expected</ins> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 7
> ~~Expected~~ rendered HTML
.
<blockquote>
  <p><del>Expected</del> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 8
> ~Expected~ rendered HTML
.
<blockquote>
  <p><sub>Expected</sub> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 9
> ^Expected^ rendered HTML
.
<blockquote>
  <p><sup>Expected</sup> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 10
> `Expected` rendered HTML
.
<blockquote>
  <p><code>Expected</code> rendered HTML</p>
</blockquote>
````````````````````````````````


``Expected `a``

```````````````````````````````` example Block Quotes: 11
> ``Expected `a`` rendered HTML
.
<blockquote>
  <p><code>Expected `a</code> rendered HTML</p>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 12
> * item
.
<blockquote>
 <ul>
 <li>item</li>
 </ul>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 13
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


```````````````````````````````` example Block Quotes: 14
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


```````````````````````````````` example Block Quotes: 15
* item 1

  > * item 1
  >
  > * item 2

* item 2

.
<ul>
<li>
  <p>item 1</p>
<blockquote>
  <ul>
  <li><p>item 1</p></li>
  <li><p>item 2</p></li>
  </ul>
</blockquote>
</li>
<li><p>item 2</p></li>
</ul>
````````````````````````````````


```````````````````````````````` example Block Quotes: 16
* item 1

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


```````````````````````````````` example Block Quotes: 17
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


```````````````````````````````` example Block Quotes: 18
> 1. item
.
<blockquote>
<ol>
<li>item</li>
</ol>
</blockquote>
````````````````````````````````


```````````````````````````````` example Block Quotes: 19
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


```````````````````````````````` example Block Quotes: 20
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


```````````````````````````````` example Block Quotes: 21
1. item 1

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


```````````````````````````````` example Block Quotes: 22
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

```````````````````````````````` example Headings: 1
Heading
=======

.
<h1>Heading</h1>
````````````````````````````````


```````````````````````````````` example Headings: 2
Heading
-------

.
<h2>Heading</h2>
````````````````````````````````


```````````````````````````````` example Headings: 3
### Heading

.
<h3>Heading</h3>
````````````````````````````````


```````````````````````````````` example Headings: 4
#### Heading

.
<h4>Heading</h4>
````````````````````````````````


```````````````````````````````` example Headings: 5
##### Heading

.
<h5>Heading</h5>
````````````````````````````````


```````````````````````````````` example Headings: 6
###### Heading

.
<h6>Heading</h6>
````````````````````````````````


## Thematic Break

```````````````````````````````` example Thematic Break: 1
*** ** * ** ***

.
<hr>
````````````````````````````````


## Links

not links

```````````````````````````````` example Links: 1
Text
.
<a attr="http://example.com">Text</a>
````````````````````````````````


```````````````````````````````` example Links: 2
Text **Bold**
.
<a attr="http://example.com">Text <b>Bold</b></a>
````````````````````````````````


links

```````````````````````````````` example Links: 3
[](http://example.com)
.
<a href="http://example.com"></a>
````````````````````````````````


```````````````````````````````` example Links: 4
[Text](http://example.com)
.
<a href="http://example.com">Text</a>
````````````````````````````````


```````````````````````````````` example Links: 5
[Text](http://example.com "Title")
.
<a href="http://example.com" title="Title">Text</a>
````````````````````````````````


```````````````````````````````` example Links: 6
[Text **Bold**](http://example.com)
.
<a href="http://example.com">Text <b>Bold</b></a>
````````````````````````````````


```````````````````````````````` example Links: 7
[\[Text **Bold**\]](http://example.com)
.
<a href="http://example.com">[Text <b>Bold</b>]</a>
````````````````````````````````


auto links

```````````````````````````````` example Links: 8
<http://example.com>
.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 9) options(wrap-autolinks)
<http://example.com>
.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 10) options(no-wrap-autolinks)
http://example.com
.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


```````````````````````````````` example Links: 11
[http://example.com](http://example.com "Title")
.
<a href="http://example.com" title="Title">http://example.com</a>
````````````````````````````````


```````````````````````````````` example Links: 12
<http://example.com>
.
<a href="http://example.com" title="">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 13) options(no-autolinks)
[http://example.com](http://example.com)
.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


No links

```````````````````````````````` example(Links: 14) options(no-autolinks, links-none)
.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 15) options(links-none)
.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


As text links

```````````````````````````````` example(Links: 16) options(no-autolinks, links-text)
http://example.com
.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 17) options(links-text)
<http://example.com>
.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


custom resolver

```````````````````````````````` example(Links: 18) options(links-text, link-resolver)
<https://example.com>
.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 19) options(links-text)
http://example.com
.
<a href="http://example.com" title="Title">http://example.com</a>
````````````````````````````````


custom resolver

```````````````````````````````` example(Links: 20) options(links-text, link-resolver)
https://example.com
.
<a href="http://example.com" title="Title">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 21) options(links-text)
\[Text **Bold**\]
.
<a href="http://example.com">[Text <b>Bold</b>]</a>
````````````````````````````````


custom resolver

```````````````````````````````` example(Links: 22) options(link-resolver)
[](https://example.com)
.
<a href="http://example.com"></a>
````````````````````````````````


As html

```````````````````````````````` example(Links: 23) options(no-autolinks, links-html)
<a href="http://example.com">http://example.com</a>
.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 24) options(links-html)
<a href="http://example.com">http://example.com</a>
.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 25) options(no-autolinks, links-html, link-resolver)
<a href="http://example.com">http://example.com</a>
.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 26) options(links-html, link-resolver)
<a href="http://example.com">http://example.com</a>
.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 27) options(links-html)
<a href="http://example.com" title="Title">http://example.com</a>
.
<a href="http://example.com" title="Title">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 28) options(links-html)
<a href="http://example.com">\[Text <b>Bold</b>\]</a>
.
<a href="http://example.com">[Text <b>Bold</b>]</a>
````````````````````````````````


As ref

```````````````````````````````` example(Links: 29) options(no-autolinks, links-ref)
[http://example.com][]

[http://example.com]: http://example.com

.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 30) options(links-ref)
<http://example.com>
.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


custom resolver

```````````````````````````````` example(Links: 31) options(links-ref, link-resolver)
[http://example.com][]

[http://example.com]: https://example.com 'Title'

.
<a href="http://example.com" title="Title">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 32) options(links-ref, link-resolver)
<https://example.com>
.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 33) options(links-ref)
[\[Text **Bold**\]][]

[\[Text **Bold**\]]: http://example.com

.
<a href="http://example.com">[Text <b>Bold</b>]</a>
````````````````````````````````


```````````````````````````````` example(Links: 34) options(links-ref)
[![alt](image.png)](http://example.com)
.
<a href="http://example.com"><img src="image.png" alt="alt"></a>
````````````````````````````````


As ref re-use document

```````````````````````````````` example(Links: 35) options(no-autolinks, links-ref, for-document)
[http://example.com][example.com]
.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


```````````````````````````````` example(Links: 36) options(links-none)
.
<a href="http://example.com">http://example.com</a>
````````````````````````````````


```````````````````````````````` example Links: 37
[](#30xxx93---bug-fix-release)
.
<a href="#30xxx93---bug-fix-release"></a>
````````````````````````````````


## Images

Not images

```````````````````````````````` example Images: 1
.
<img id="http://example.com">
````````````````````````````````


```````````````````````````````` example Images: 2
![](http://example.com/image.png)
.
<img src="http://example.com/image.png">
````````````````````````````````


```````````````````````````````` example Images: 3
![](http://example.com/image.png)
.
<img src="http://example.com/image.png" alt="">
````````````````````````````````


```````````````````````````````` example Images: 4
![Alt](http://example.com/image.png)
.
<img src="http://example.com/image.png" alt="Alt">
````````````````````````````````


```````````````````````````````` example(Images: 5) options(link-resolver)
![Alt](https://example.com/image.png)
.
<img src="http://example.com/image.png" alt="Alt">
````````````````````````````````


```````````````````````````````` example Images: 6
![](http://example.com/image.png "Title")
.
<img src="http://example.com/image.png" title="Title">
````````````````````````````````


```````````````````````````````` example(Images: 7) options(link-resolver)
![](https://example.com/image.png "Title")
.
<img src="http://example.com/image.png" title="Title">
````````````````````````````````


```````````````````````````````` example Images: 8
![](http://example.com/image.png "Title")
.
<img src="http://example.com/image.png" alt="" title="Title">
````````````````````````````````


```````````````````````````````` example Images: 9
![Alt](http://example.com/image.png "Title")
.
<img src="http://example.com/image.png" alt="Alt" title="Title">
````````````````````````````````


Multi-line URL

```````````````````````````````` example Images: 10
![alt](http://latex.codecogs.com/gif.latex?
\begin{align*}
x^2 + y^2 &= 1 \\
y &= \sqrt{1 - x^2} \\
\end{align*}
"title")
.
<img src="http://latex.codecogs.com/gif.latex?%5Cbegin%7Balign*%7D%0Ax%5E2%20%2B%20y%5E2%20&amp;=%201%20%5C%5C%0Ay%20&amp;=%20%5Csqrt%7B1%20-%20x%5E2%7D%20%5C%5C%0A%5Cend%7Balign*%7D%0A" alt="alt" title="title" />
````````````````````````````````


```````````````````````````````` example(Images: 11) options(link-resolver)
![alt](https://latex.codecogs.com/gif.latex?
\begin{align*}
x^2 + y^2 &= 1 \\
y &= \sqrt{1 - x^2} \\
\end{align*}
"title")
.
<img src="http://latex.codecogs.com/gif.latex?%5Cbegin%7Balign*%7D%0Ax%5E2%20%2B%20y%5E2%20&amp;=%201%20%5C%5C%0Ay%20&amp;=%20%5Csqrt%7B1%20-%20x%5E2%7D%20%5C%5C%0A%5Cend%7Balign*%7D%0A" alt="alt" title="title" />
````````````````````````````````


No Images

```````````````````````````````` example(Images: 12) options(img-none)
.
<img src="http://example.com/image.png" title="Title">
````````````````````````````````


```````````````````````````````` example(Images: 13) options(img-none)
.
<img src="http://example.com/image.png" alt="" title="Title">
````````````````````````````````


```````````````````````````````` example(Images: 14) options(img-none)
.
<img src="http://example.com/image.png" alt="Alt" title="Title">
````````````````````````````````


Multi-line URL

```````````````````````````````` example(Images: 15) options(img-none)
.
<img src="http://latex.codecogs.com/gif.latex?%5Cbegin%7Balign*%7D%0Ax%5E2%20%2B%20y%5E2%20&amp;=%201%20%5C%5C%0Ay%20&amp;=%20%5Csqrt%7B1%20-%20x%5E2%7D%20%5C%5C%0A%5Cend%7Balign*%7D%0A" alt="alt" title="title" />
````````````````````````````````


Html images

```````````````````````````````` example(Images: 16) options(img-html)
<img src="http://example.com/image.png" title="Title">
.
<img src="http://example.com/image.png" title="Title">
````````````````````````````````


```````````````````````````````` example(Images: 17) options(img-html)
<img src="http://example.com/image.png" alt="" title="Title">
.
<img src="http://example.com/image.png" alt="" title="Title">
````````````````````````````````


```````````````````````````````` example(Images: 18) options(img-html)
<img src="http://example.com/image.png" alt="Alt" title="Title">
.
<img src="http://example.com/image.png" alt="Alt" title="Title">
````````````````````````````````


```````````````````````````````` example(Images: 19) options(img-html, link-resolver)
<img src="http://example.com/image.png" alt="Alt" title="Title">
.
<img src="http://example.com/image.png" alt="Alt" title="Title">
````````````````````````````````


Multi-line URL

```````````````````````````````` example(Images: 20) options(img-html)
<img src="http://latex.codecogs.com/gif.latex?%5Cbegin%7Balign*%7D%0Ax%5E2%20%2B%20y%5E2%20&amp;=%201%20%5C%5C%0Ay%20&amp;=%20%5Csqrt%7B1%20-%20x%5E2%7D%20%5C%5C%0A%5Cend%7Balign*%7D%0A" alt="alt" title="title">
.
<img src="http://latex.codecogs.com/gif.latex?%5Cbegin%7Balign*%7D%0Ax%5E2%20%2B%20y%5E2%20&amp;=%201%20%5C%5C%0Ay%20&amp;=%20%5Csqrt%7B1%20-%20x%5E2%7D%20%5C%5C%0A%5Cend%7Balign*%7D%0A" alt="alt" title="title" />
````````````````````````````````


text only images

```````````````````````````````` example(Images: 21) options(img-text)
Title
.
<img src="http://example.com/image.png" title="Title">
````````````````````````````````


```````````````````````````````` example(Images: 22) options(img-text)
Title
.
<img src="http://example.com/image.png" alt="" title="Title">
````````````````````````````````


```````````````````````````````` example(Images: 23) options(img-text)
Alt
.
<img src="http://example.com/image.png" alt="Alt" title="Title">
````````````````````````````````


Multi-line URL

```````````````````````````````` example(Images: 24) options(img-text)
alt
.
<img src="http://latex.codecogs.com/gif.latex?%5Cbegin%7Balign*%7D%0Ax%5E2%20%2B%20y%5E2%20&amp;=%201%20%5C%5C%0Ay%20&amp;=%20%5Csqrt%7B1%20-%20x%5E2%7D%20%5C%5C%0A%5Cend%7Balign*%7D%0A" alt="alt" title="title" />
````````````````````````````````


ref images

```````````````````````````````` example(Images: 25) options(img-ref)
![image][]

[image]: http://example.com/image.png 'Title'

.
<img src="http://example.com/image.png" title="Title">
````````````````````````````````


```````````````````````````````` example(Images: 26) options(img-ref, link-resolver)
![image][]

[image]: https://example.com/image.png 'Title'

.
<img src="http://example.com/image.png" title="Title">
````````````````````````````````


```````````````````````````````` example(Images: 27) options(img-ref)
![image][]

[image]: http://example.com/image.png 'Title'

.
<img src="http://example.com/image.png" alt="" title="Title">
````````````````````````````````


```````````````````````````````` example(Images: 28) options(img-ref)
![Alt][]

[Alt]: http://example.com/image.png 'Title'

.
<img src="http://example.com/image.png" alt="Alt" title="Title">
````````````````````````````````


Multi-line URL

```````````````````````````````` example(Images: 29) options(img-ref)
![alt](http://latex.codecogs.com/gif.latex?
\begin{align*}
x^2 + y^2 &= 1 \\
y &= \sqrt{1 - x^2} \\
\end{align*}
"title")
.
<img src="http://latex.codecogs.com/gif.latex?%5Cbegin%7Balign*%7D%0Ax%5E2%20%2B%20y%5E2%20&amp;=%201%20%5C%5C%0Ay%20&amp;=%20%5Csqrt%7B1%20-%20x%5E2%7D%20%5C%5C%0A%5Cend%7Balign*%7D%0A" alt="alt" title="title" />
````````````````````````````````


As ref re-use document

```````````````````````````````` example(Images: 30) options(img-ref, for-document)
![Alt][example image]
.
<img src="http://example.com/image.png" alt="Alt" title="Title">
````````````````````````````````


none

```````````````````````````````` example(Images: 31) options(img-none)
.
<img src="http://example.com/image.png" alt="Alt" title="Title">
````````````````````````````````


## Tables

```````````````````````````````` example Tables: 1
.
<table>
  <thead></thead>
  <tbody></tbody>
</table>
````````````````````````````````


```````````````````````````````` example Tables: 2
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

```````````````````````````````` example Tables: 3
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

```````````````````````````````` example Tables: 4
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


```````````````````````````````` example Tables: 5
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


```````````````````````````````` example Tables: 6
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


```````````````````````````````` example Tables: 7
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


```````````````````````````````` example Tables: 8
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


```````````````````````````````` example Tables: 9
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


```````````````````````````````` example Tables: 10
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


```````````````````````````````` example Tables: 11
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


```````````````````````````````` example Tables: 12
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


```````````````````````````````` example Tables: 13
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


```````````````````````````````` example Tables: 14
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


```````````````````````````````` example Tables: 15
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


```````````````````````````````` example Tables: 16
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


```````````````````````````````` example Tables: 17
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

```````````````````````````````` example Tables: 18
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

```````````````````````````````` example Tables: 19
| Abc | Def |
|-----|-----|-----|
| \`  |     | abc |

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

```````````````````````````````` example Tables: 20
|   Abc   | Def |
|---------|-----|
| \*\*def | abc |

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


```````````````````````````````` example Tables: 21
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


```````````````````````````````` example Tables: 22
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


```````````````````````````````` example Tables: 23
|  Abc  | Def |
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


```````````````````````````````` example Tables: 24
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


```````````````````````````````` example Tables: 25
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


```````````````````````````````` example Tables: 26
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


```````````````````````````````` example Tables: 27
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


```````````````````````````````` example Tables: 28
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


```````````````````````````````` example Tables: 29
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

```````````````````````````````` example Tables: 30
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


```````````````````````````````` example Tables: 31
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


```````````````````````````````` example Tables: 32
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


```````````````````````````````` example Tables: 33
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

```````````````````````````````` example Tables: 34
|   **Abc**   | *Def*  |
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

```````````````````````````````` example Tables: 35
| **Abc** **test** |  *Def* *Def*  |
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

```````````````````````````````` example Tables: 36
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

```````````````````````````````` example Tables: 37
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

```````````````````````````````` example Tables: 38
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

```````````````````````````````` example Tables: 39
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

```````````````````````````````` example Tables: 40
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

```````````````````````````````` example Tables: 41
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

```````````````````````````````` example Tables: 42
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

```````````````````````````````` example Tables: 43
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

```````````````````````````````` example Tables: 44
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

```````````````````````````````` example Tables: 45
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

```````````````````````````````` example Tables: 46
* Add: live templates starting with `.`

  |    Element    |  Abbreviation   |     Expansion     |
  |---------------|-----------------|-------------------|
  | Abbreviation  | `.abbreviation` | `*[]:`            |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\` |
  | Explicit link | `.link`         | `[]()`            |

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

```````````````````````````````` example Tables: 47
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
| **Multi-line Image URLs for embedding \[gravizo.com\] UML diagrams into markdown**              |   X   |    X     |
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
| Emoji text to icon conversion using \[Emoji Cheat Sheet\] or GitHub emoji URLs                  |       |    X     |
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

```````````````````````````````` example(Tables: 48) options(nbsp)
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
| **Multi-line Image URLs for embedding \[gravizo.com\] UML diagrams into markdown**                                      |   X   |    X     |
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
| &nbsp;&nbsp;&nbsp;&nbsp;Emoji text to icon conversion using \[Emoji Cheat Sheet\] or GitHub emoji URLs                  |       |    X     |
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

```````````````````````````````` example Tables: 49
| Result |    Description     | Entity Name |
|--------|--------------------|-------------|
|        | non-breaking space | \&nbsp;     |
| \<     | less than          | \&lt;       |
| \>     | greater than       | \&gt;       |
| \&     | ampersand          | \&amp;      |

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

```````````````````````````````` example Tables: 50
| ID |  e-mail  |   Name   | Visits | Trials |                 Points                 |||| Discount On Next License |
| ID |  e-mail  |   Name   | Visits | Trials | Earned | Complimentary | Used | Available | Discount On Next License |
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

```````````````````````````````` example Definition Lists: 1
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

```````````````````````````````` example Definition Lists: 2
Definition Term
:   definition item
.
<dl>
  <dt>Definition Term</dt>
  <dd>definition item</dd>
</dl>
````````````````````````````````


A simple definition list:

```````````````````````````````` example Definition Lists: 3
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

```````````````````````````````` example Definition Lists: 4
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

```````````````````````````````` example Definition Lists: 5
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

```````````````````````````````` example Definition Lists: 6
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

```````````````````````````````` example Definition Lists: 7
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

```````````````````````````````` example Definition Lists: 8
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

```````````````````````````````` example Definition Lists: 9
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

```````````````````````````````` example Definition Lists: 10
Definition **Term**
:   definition `item`
.
<dl>
  <dt>Definition <strong>Term</strong></dt>
  <dd>definition <code>item</code></dd>
</dl>
````````````````````````````````


inlines will be split

```````````````````````````````` example Definition Lists: 11
Definition \*\*Term
Another\*\* Definition Term
:   definition `item`
.
<dl>
  <dt>Definition **Term</dt>
  <dt>Another** Definition Term</dt>
  <dd>definition <code>item</code></dd>
</dl>
````````````````````````````````


don't include preceding blank lines

```````````````````````````````` example Definition Lists: 12
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

```````````````````````````````` example Definition Lists: 13
Definition **Term**
:   definition `item`

    paragraph

    * bullet item
      * sub item


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

```````````````````````````````` example Definition Lists: 14
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

```````````````````````````````` example Definition Lists: 15
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


```````````````````````````````` example Definition Lists: 16
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


```````````````````````````````` example Definition Lists: 17
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


```````````````````````````````` example Definition Lists: 18
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

```````````````````````````````` example Fenced Code: 1
```text
plain text
```

.
<pre><code class="text">plain text
</code></pre>
````````````````````````````````


empty, no info

```````````````````````````````` example Fenced Code: 2
```

```

.
<pre><code>
</code></pre>
````````````````````````````````


empty, no info, blank line follows

```````````````````````````````` example Fenced Code: 3
```

```

.
<pre><code>
</code></pre>
````````````````````````````````


empty, info

```````````````````````````````` example Fenced Code: 4
```info

```

.
<pre><code class="language-info">
</code></pre>
````````````````````````````````


empty, info, blank line follows

```````````````````````````````` example Fenced Code: 5
```info

```

.
<pre><code class="language-info">
</code></pre>
````````````````````````````````


non-break space replacement

```````````````````````````````` example Fenced Code: 6
```info
 
```

.
<pre><code class="language-info"> 
</code></pre>
````````````````````````````````


non-break space replacement, custom replaced with space

```````````````````````````````` example(Fenced Code: 7) options(nbsp)
```info
 
```

.
<pre><code class="language-info"> 
</code></pre>
````````````````````````````````


```````````````````````````````` example Fenced Code: 8
`````java
some back ticks in the ````code````
`````

.
<pre><code class="java">some back ticks in the ````code````
</code></pre>
````````````````````````````````


```````````````````````````````` example Fenced Code: 9
> `````java
>     some back ticks in the ````code````
> `````

.
<blockquote>
<pre><code class="java">    some back ticks in the ````code````
</code></pre>
</blockquote>
````````````````````````````````


special characters are not escaped inside nested html elements

```````````````````````````````` example Fenced Code: 10
```text
special symbols: \*~^&<>[]|`
```

.
<pre><code class="text">special symbols: <b>\*~^&<>[]|`</b>
</code></pre>
````````````````````````````````

## Skipped Fenced Code

```````````````````````````````` example(Skipped Fenced Code: 1) options(skip-fenced-code)
>         some back ticks in the ````code````

.
<blockquote>
<pre><code class="java">    some back ticks in the ````code````
</code></pre>
</blockquote>
````````````````````````````````


```````````````````````````````` example Skipped Fenced Code: 2
* item

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


```````````````````````````````` example Skipped Fenced Code: 3
* item

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

```````````````````````````````` example(Skipped Fenced Code: 4) options(code-emphasis)
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

```````````````````````````````` example Skipped Fenced Code: 5
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

```````````````````````````````` example Emoji Shortcuts: 1
:heavy_check_mark:
.
<g-emoji alias="heavy_check_mark" fallback-src="https://github.githubassets.com/images/icons/emoji/unicode/2714.png" ios-version="6.0">✔️</g-emoji>
````````````````````````````````


```````````````````````````````` example Emoji Shortcuts: 2
:heavy_check_mark:
.
<g-emoji fallback-src="https://github.githubassets.com/images/icons/emoji/unicode/2714.png" ios-version="6.0">✔️</g-emoji>
````````````````````````````````


```````````````````````````````` example Emoji Shortcuts: 3
:heavy_check_mark:
.
<img src="file:/Users/vlad/Library/Application Support/IdeaIC2017-1-EAP/idea-multimarkdown/emojis/heavy_check_mark.png" alt="emoji symbols:heavy_check_mark" height="20" width="20" align="absmiddle" />
````````````````````````````````


```````````````````````````````` example Emoji Shortcuts: 4
:heavy_check_mark:
.
<img src="https://github.githubassets.com/images/icons/emoji/unicode/2714.png" alt="emoji symbols:heavy_check_mark" height="20" width="20" align="absmiddle" />
````````````````````````````````


## Typographics

```````````````````````````````` example Typographics: 1
"
.
“
````````````````````````````````


```````````````````````````````` example Typographics: 2
"
.
”
````````````````````````````````


```````````````````````````````` example Typographics: 3
"
.
&ldquo;
````````````````````````````````


```````````````````````````````` example Typographics: 4
"
.
&rdquo;
````````````````````````````````


```````````````````````````````` example Typographics: 5
'
.
‘
````````````````````````````````


```````````````````````````````` example Typographics: 6
'
.
’
````````````````````````````````


```````````````````````````````` example Typographics: 7
'
.
&lsquo;
````````````````````````````````


```````````````````````````````` example Typographics: 8
'
.
&rsquo;
````````````````````````````````


```````````````````````````````` example Typographics: 9
'
.
&apos;
````````````````````````````````


```````````````````````````````` example Typographics: 10
\<\<
.
«
````````````````````````````````


```````````````````````````````` example Typographics: 11
\<\<
.
&laquo;
````````````````````````````````


```````````````````````````````` example Typographics: 12
\>\>
.
»
````````````````````````````````


```````````````````````````````` example Typographics: 13
\>\>
.
&raquo;
````````````````````````````````


```````````````````````````````` example Typographics: 14
...
.
…
````````````````````````````````


```````````````````````````````` example Typographics: 15
...
.
&hellip;
````````````````````````````````


```````````````````````````````` example Typographics: 16
--
.
–
````````````````````````````````


```````````````````````````````` example Typographics: 17
--
.
&endash;
````````````````````````````````


```````````````````````````````` example Typographics: 18
---
.
—
````````````````````````````````


```````````````````````````````` example Typographics: 19
---
.
&emdash;
````````````````````````````````


## Custom Typographics

```````````````````````````````` example(Custom Typographics: 1) options(typo-map)
''
.
“
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 2) options(typo-map)
''
.
”
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 3) options(typo-map)
''
.
&ldquo;
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 4) options(typo-map)
''
.
&rdquo;
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 5) options(typo-map)
'
.
‘
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 6) options(typo-map)
'
.
’
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 7) options(typo-map)
'
.
&lsquo;
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 8) options(typo-map)
'
.
&rsquo;
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 9) options(typo-map)
'
.
&apos;
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 10) options(typo-map)
\<\<\<\<
.
«
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 11) options(typo-map)
\<\<\<\<
.
&laquo;
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 12) options(typo-map)
\>\>\>\>
.
»
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 13) options(typo-map)
\>\>\>\>
.
&raquo;
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 14) options(typo-map)
etc.
.
…
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 15) options(typo-map)
etc.
.
&hellip;
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 16) options(typo-map)
--
.
–
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 17) options(typo-map)
--
.
&endash;
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 18) options(typo-map)
---
.
—
````````````````````````````````


```````````````````````````````` example(Custom Typographics: 19) options(typo-map)
---
.
&emdash;
````````````````````````````````


