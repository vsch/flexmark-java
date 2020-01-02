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

Empty

```````````````````````````````` example SpecExample: 1
```````````````` example
````````````````
.
<hr />
.
Document[0, 41]
  SpecExampleBlock[0, 41] openingMarker:[0, 16] exampleKeyword:[17, 24] closingMarker:[25, 41]
    SpecExampleSource[25, 25]
````````````````````````````````


Empty with `&nbsp;` for space on first line

```````````````````````````````` example SpecExample: 2
```````````````` example
````````````````
.
<hr />
.
Document[0, 41]
  SpecExampleBlock[0, 41] openingMarker:[0, 16] exampleKeyword:[17, 24] closingMarker:[25, 41]
    SpecExampleSource[25, 25]
````````````````````````````````


Single spacer

```````````````````````````````` example SpecExample: 3
```````````````` example
…
````````````````
.
<hr />
.
Document[0, 43]
  SpecExampleBlock[0, 43] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 25] htmlSeparator:[25, 27] html:[27, 27] closingMarker:[27, 43]
    SpecExampleSource[25, 25]
    SpecExampleSeparator[25, 27] chars:[25, 27, "…\n"]
    SpecExampleHtml[27, 27]
````````````````````````````````


Two spacers

```````````````````````````````` example SpecExample: 4
```````````````` example
…
…
````````````````
.
<hr />
.
Document[0, 45]
  SpecExampleBlock[0, 45] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 25] htmlSeparator:[25, 27] html:[27, 27] astSeparator:[27, 29] ast:[29, 29] closingMarker:[29, 45]
    SpecExampleSource[25, 25]
    SpecExampleSeparator[25, 27] chars:[25, 27, "…\n"]
    SpecExampleHtml[27, 27]
    SpecExampleSeparator[27, 29] chars:[27, 29, "…\n"]
    SpecExampleAst[29, 29]
````````````````````````````````


Extra spacer

```````````````````````````````` example SpecExample: 5
```````````````` example
…
…
…
````````````````
.
<hr />
<hr />
<pre><code class="language-text">…
</code></pre>
.
Document[0, 47]
  SpecExampleBlock[0, 47] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 25] htmlSeparator:[25, 27] html:[27, 27] astSeparator:[27, 29] ast:[29, 31] closingMarker:[31, 47]
    SpecExampleSource[25, 25]
    SpecExampleSeparator[25, 27] chars:[25, 27, "…\n"]
    SpecExampleHtml[27, 27]
    SpecExampleSeparator[27, 29] chars:[27, 29, "…\n"]
    SpecExampleAst[29, 31] chars:[29, 31, "…\n"]
````````````````````````````````


Source Only

```````````````````````````````` example SpecExample: 6
```````````````` example
Markdown only
````````````````
.
<hr />
<hr />
<pre><code class="language-markdown">Markdown only
</code></pre>
.
Document[0, 55]
  SpecExampleBlock[0, 55] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 39] closingMarker:[39, 55]
    SpecExampleSource[25, 39] chars:[25, 39, "Markd … only\n"]
````````````````````````````````


Source Only, empty HTML

```````````````````````````````` example SpecExample: 7
```````````````` example
Markdown only
…
````````````````
.
<hr />
<hr />
<pre><code class="language-markdown">Markdown only
</code></pre>
.
Document[0, 57]
  SpecExampleBlock[0, 57] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 39] htmlSeparator:[39, 41] html:[41, 41] closingMarker:[41, 57]
    SpecExampleSource[25, 39] chars:[25, 39, "Markd … only\n"]
    SpecExampleSeparator[39, 41] chars:[39, 41, "…\n"]
    SpecExampleHtml[41, 41]
````````````````````````````````


Source Only, empty HTML and AST

```````````````````````````````` example SpecExample: 8
```````````````` example
Markdown only
…
…
````````````````
.
<hr />
<hr />
<pre><code class="language-markdown">Markdown only
</code></pre>
.
Document[0, 59]
  SpecExampleBlock[0, 59] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 39] htmlSeparator:[39, 41] html:[41, 41] astSeparator:[41, 43] ast:[43, 43] closingMarker:[43, 59]
    SpecExampleSource[25, 39] chars:[25, 39, "Markd … only\n"]
    SpecExampleSeparator[39, 41] chars:[39, 41, "…\n"]
    SpecExampleHtml[41, 41]
    SpecExampleSeparator[41, 43] chars:[41, 43, "…\n"]
    SpecExampleAst[43, 43]
````````````````````````````````


Html Only, no AST

```````````````````````````````` example SpecExample: 9
```````````````` example
…
<pre><code class="language-markdown">Markdown only</code></pre>
<ul>
  <li>List item</li>
</ul>
````````````````
.
<hr />
<hr />
<pre><code class="language-html">&lt;pre&gt;&lt;code class=&quot;language-markdown&quot;&gt;Markdown only&lt;/code&gt;&lt;/pre&gt;
&lt;ul&gt;
  &lt;li&gt;List item&lt;/li&gt;
&lt;/ul&gt;
</code></pre>
<hr />
<div style="border:solid #cccccc 1px;padding:0 20px 10px 20px;"><pre><code class="language-markdown">Markdown only</code></pre>
<ul>
  <li>List item</li>
</ul>
</div>
.
Document[0, 139]
  SpecExampleBlock[0, 139] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 25] htmlSeparator:[25, 27] html:[27, 123] closingMarker:[123, 139]
    SpecExampleSource[25, 25]
    SpecExampleSeparator[25, 27] chars:[25, 27, "…\n"]
    SpecExampleHtml[27, 123] chars:[27, 123, "<pre> … /ul>\n"]
````````````````````````````````


Html Only, empty AST

```````````````````````````````` example SpecExample: 10
```````````````` example
…
<pre><code class="language-markdown">Markdown only</code></pre>
<ul>
  <li>List item</li>
</ul>
…
````````````````
.
<hr />
<hr />
<pre><code class="language-html">&lt;pre&gt;&lt;code class=&quot;language-markdown&quot;&gt;Markdown only&lt;/code&gt;&lt;/pre&gt;
&lt;ul&gt;
  &lt;li&gt;List item&lt;/li&gt;
&lt;/ul&gt;
</code></pre>
<hr />
<div style="border:solid #cccccc 1px;padding:0 20px 10px 20px;"><pre><code class="language-markdown">Markdown only</code></pre>
<ul>
  <li>List item</li>
</ul>
</div>
.
Document[0, 141]
  SpecExampleBlock[0, 141] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 25] htmlSeparator:[25, 27] html:[27, 123] astSeparator:[123, 125] ast:[125, 125] closingMarker:[125, 141]
    SpecExampleSource[25, 25]
    SpecExampleSeparator[25, 27] chars:[25, 27, "…\n"]
    SpecExampleHtml[27, 123] chars:[27, 123, "<pre> … /ul>\n"]
    SpecExampleSeparator[123, 125] chars:[123, 125, "…\n"]
    SpecExampleAst[125, 125]
````````````````````````````````


Ast Only

```````````````````````````````` example SpecExample: 11
```````````````` example
…
…
Document[0, 56]
  SpecExampleBlock[0, 55] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 38] closingMarker:[39, 55]
    SpecExampleSource[25, 38] chars:[25, 38, "Markd"..." only"]
````````````````
.
<hr />
<hr />
<pre><code class="language-text">Document[0, 56]
  SpecExampleBlock[0, 55] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 38] closingMarker:[39, 55]
    SpecExampleSource[25, 38] chars:[25, 38, &quot;Markd&quot;...&quot; only&quot;]
</code></pre>
.
Document[0, 236]
  SpecExampleBlock[0, 236] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 25] htmlSeparator:[25, 27] html:[27, 27] astSeparator:[27, 29] ast:[29, 220] closingMarker:[220, 236]
    SpecExampleSource[25, 25]
    SpecExampleSeparator[25, 27] chars:[25, 27, "…\n"]
    SpecExampleHtml[27, 27]
    SpecExampleSeparator[27, 29] chars:[27, 29, "…\n"]
    SpecExampleAst[29, 220] chars:[29, 220, "Docum … ly\"]\n"]
````````````````````````````````


Source and HTML, no AST

```````````````````````````````` example SpecExample: 12
```````````````` example
Markdown only
…
<pre><code class="language-markdown">Markdown only</code></pre>
<ul>
  <li>List item</li>
</ul>
````````````````
.
<hr />
<hr />
<pre><code class="language-markdown">Markdown only
</code></pre>
<hr />
<pre><code class="language-html">&lt;pre&gt;&lt;code class=&quot;language-markdown&quot;&gt;Markdown only&lt;/code&gt;&lt;/pre&gt;
&lt;ul&gt;
  &lt;li&gt;List item&lt;/li&gt;
&lt;/ul&gt;
</code></pre>
<hr />
<div style="border:solid #cccccc 1px;padding:0 20px 10px 20px;"><pre><code class="language-markdown">Markdown only</code></pre>
<ul>
  <li>List item</li>
</ul>
</div>
.
Document[0, 153]
  SpecExampleBlock[0, 153] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 39] htmlSeparator:[39, 41] html:[41, 137] closingMarker:[137, 153]
    SpecExampleSource[25, 39] chars:[25, 39, "Markd … only\n"]
    SpecExampleSeparator[39, 41] chars:[39, 41, "…\n"]
    SpecExampleHtml[41, 137] chars:[41, 137, "<pre> … /ul>\n"]
````````````````````````````````


Source and HTML, empty AST

```````````````````````````````` example SpecExample: 13
```````````````` example
Markdown only
…
<pre><code class="language-markdown">Markdown only</code></pre>
<ul>
  <li>List item</li>
</ul>
…
````````````````
.
<hr />
<hr />
<pre><code class="language-markdown">Markdown only
</code></pre>
<hr />
<pre><code class="language-html">&lt;pre&gt;&lt;code class=&quot;language-markdown&quot;&gt;Markdown only&lt;/code&gt;&lt;/pre&gt;
&lt;ul&gt;
  &lt;li&gt;List item&lt;/li&gt;
&lt;/ul&gt;
</code></pre>
<hr />
<div style="border:solid #cccccc 1px;padding:0 20px 10px 20px;"><pre><code class="language-markdown">Markdown only</code></pre>
<ul>
  <li>List item</li>
</ul>
</div>
.
Document[0, 155]
  SpecExampleBlock[0, 155] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 39] htmlSeparator:[39, 41] html:[41, 137] astSeparator:[137, 139] ast:[139, 139] closingMarker:[139, 155]
    SpecExampleSource[25, 39] chars:[25, 39, "Markd … only\n"]
    SpecExampleSeparator[39, 41] chars:[39, 41, "…\n"]
    SpecExampleHtml[41, 137] chars:[41, 137, "<pre> … /ul>\n"]
    SpecExampleSeparator[137, 139] chars:[137, 139, "…\n"]
    SpecExampleAst[139, 139]
````````````````````````````````


Source, HTML and AST

```````````````````````````````` example SpecExample: 14
```````````````` example
Markdown only
…
<pre><code class="language-markdown">Markdown only</code></pre>
<ul>
  <li>List item</li>
</ul>
…
Document[0, 56]
  SpecExampleBlock[0, 55] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 38] closingMarker:[39, 55]
    SpecExampleSource[25, 38] chars:[25, 38, "Markd"..." only"]
````````````````
.
<hr />
<hr />
<pre><code class="language-markdown">Markdown only
</code></pre>
<hr />
<pre><code class="language-html">&lt;pre&gt;&lt;code class=&quot;language-markdown&quot;&gt;Markdown only&lt;/code&gt;&lt;/pre&gt;
&lt;ul&gt;
  &lt;li&gt;List item&lt;/li&gt;
&lt;/ul&gt;
</code></pre>
<hr />
<div style="border:solid #cccccc 1px;padding:0 20px 10px 20px;"><pre><code class="language-markdown">Markdown only</code></pre>
<ul>
  <li>List item</li>
</ul>
</div>
<hr />
<pre><code class="language-text">Document[0, 56]
  SpecExampleBlock[0, 55] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 38] closingMarker:[39, 55]
    SpecExampleSource[25, 38] chars:[25, 38, &quot;Markd&quot;...&quot; only&quot;]
</code></pre>
.
Document[0, 346]
  SpecExampleBlock[0, 346] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 39] htmlSeparator:[39, 41] html:[41, 137] astSeparator:[137, 139] ast:[139, 330] closingMarker:[330, 346]
    SpecExampleSource[25, 39] chars:[25, 39, "Markd … only\n"]
    SpecExampleSeparator[39, 41] chars:[39, 41, "…\n"]
    SpecExampleHtml[41, 137] chars:[41, 137, "<pre> … /ul>\n"]
    SpecExampleSeparator[137, 139] chars:[137, 139, "…\n"]
    SpecExampleAst[139, 330] chars:[139, 330, "Docum … ly\"]\n"]
````````````````````````````````


Fenced code option no language prefix

```````````````````````````````` example(SpecExample: 15) options(no-language-prefix)
```````````````` example
Markdown only
…
<pre><code class="language-markdown">Markdown only</code></pre>
<ul>
  <li>List item</li>
</ul>
…
Document[0, 56]
  SpecExampleBlock[0, 55] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 38] closingMarker:[39, 55]
    SpecExampleSource[25, 38] chars:[25, 38, "Markd"..." only"]
````````````````
.
<hr />
<hr />
<pre><code class="markdown">Markdown only
</code></pre>
<hr />
<pre><code class="html">&lt;pre&gt;&lt;code class=&quot;language-markdown&quot;&gt;Markdown only&lt;/code&gt;&lt;/pre&gt;
&lt;ul&gt;
  &lt;li&gt;List item&lt;/li&gt;
&lt;/ul&gt;
</code></pre>
<hr />
<div style="border:solid #cccccc 1px;padding:0 20px 10px 20px;"><pre><code class="language-markdown">Markdown only</code></pre>
<ul>
  <li>List item</li>
</ul>
</div>
<hr />
<pre><code class="text">Document[0, 56]
  SpecExampleBlock[0, 55] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 38] closingMarker:[39, 55]
    SpecExampleSource[25, 38] chars:[25, 38, &quot;Markd&quot;...&quot; only&quot;]
</code></pre>
.
Document[0, 346]
  SpecExampleBlock[0, 346] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 39] htmlSeparator:[39, 41] html:[41, 137] astSeparator:[137, 139] ast:[139, 330] closingMarker:[330, 346]
    SpecExampleSource[25, 39] chars:[25, 39, "Markd … only\n"]
    SpecExampleSeparator[39, 41] chars:[39, 41, "…\n"]
    SpecExampleHtml[41, 137] chars:[41, 137, "<pre> … /ul>\n"]
    SpecExampleSeparator[137, 139] chars:[137, 139, "…\n"]
    SpecExampleAst[139, 330] chars:[139, 330, "Docum … ly\"]\n"]
````````````````````````````````


Plain Coordinates, section

```````````````````````````````` example SpecExample: 16
```````````````` example Section
````````````````
.
<hr />
<h5>Section: </h5>
.
Document[0, 49]
  SpecExampleBlock[0, 49] openingMarker:[0, 16] exampleKeyword:[17, 24] section:[25, 32] closingMarker:[33, 49]
    SpecExampleSource[33, 33]
````````````````````````````````


Plain Coordinates, number

```````````````````````````````` example SpecExample: 17
```````````````` example :number
````````````````
.
<hr />
<h5>: number</h5>
.
Document[0, 49]
  SpecExampleBlock[0, 49] openingMarker:[0, 16] exampleKeyword:[17, 24] numberSeparator:[25, 26] number:[26, 32] closingMarker:[33, 49]
    SpecExampleSource[33, 33]
````````````````````````````````


Plain Coordinates, section and number

```````````````````````````````` example SpecExample: 18
```````````````` example Section:number
…
…
````````````````
.
<hr />
<h5>Section: number</h5>
.
Document[0, 60]
  SpecExampleBlock[0, 60] openingMarker:[0, 16] exampleKeyword:[17, 24] section:[25, 32] numberSeparator:[32, 33] number:[33, 39] source:[40, 40] htmlSeparator:[40, 42] html:[42, 42] astSeparator:[42, 44] ast:[44, 44] closingMarker:[44, 60]
    SpecExampleSource[40, 40]
    SpecExampleSeparator[40, 42] chars:[40, 42, "…\n"]
    SpecExampleHtml[42, 42]
    SpecExampleSeparator[42, 44] chars:[42, 44, "…\n"]
    SpecExampleAst[44, 44]
````````````````````````````````


Wrapped Coordinates, section

```````````````````````````````` example SpecExample: 19
```````````````` example(Section)
````````````````
.
<hr />
<h5>Section: </h5>
.
Document[0, 50]
  SpecExampleBlock[0, 50] openingMarker:[0, 16] exampleKeyword:[17, 24] coordOpeningMarker:[24, 25] section:[25, 32] coordClosingMarker:[32, 33] closingMarker:[34, 50]
    SpecExampleSource[34, 34]
````````````````````````````````


Wrapped Coordinates, number

```````````````````````````````` example SpecExample: 20
```````````````` example(:number)
````````````````
.
<hr />
<h5>: number</h5>
.
Document[0, 50]
  SpecExampleBlock[0, 50] openingMarker:[0, 16] exampleKeyword:[17, 24] coordOpeningMarker:[24, 25] numberSeparator:[25, 26] number:[26, 32] coordClosingMarker:[32, 33] closingMarker:[34, 50]
    SpecExampleSource[34, 34]
````````````````````````````````


Wrapped Coordinates, section and number

```````````````````````````````` example SpecExample: 21
```````````````` example(Section:number)
…
…
````````````````
.
<hr />
<h5>Section: number</h5>
.
Document[0, 61]
  SpecExampleBlock[0, 61] openingMarker:[0, 16] exampleKeyword:[17, 24] coordOpeningMarker:[24, 25] section:[25, 32] numberSeparator:[32, 33] number:[33, 39] coordClosingMarker:[39, 40] source:[41, 41] htmlSeparator:[41, 43] html:[43, 43] astSeparator:[43, 45] ast:[45, 45] closingMarker:[45, 61]
    SpecExampleSource[41, 41]
    SpecExampleSeparator[41, 43] chars:[41, 43, "…\n"]
    SpecExampleHtml[43, 43]
    SpecExampleSeparator[43, 45] chars:[43, 45, "…\n"]
    SpecExampleAst[45, 45]
````````````````````````````````


Wrapped Coordinates, section

```````````````````````````````` example SpecExample: 22
```````````````` example(Section
````````````````
.
<hr />
<h5>Section: </h5>
.
Document[0, 49]
  SpecExampleBlock[0, 49] openingMarker:[0, 16] exampleKeyword:[17, 24] coordOpeningMarker:[24, 25] section:[25, 32] closingMarker:[33, 49]
    SpecExampleSource[33, 33]
````````````````````````````````


Wrapped Coordinates, number

```````````````````````````````` example SpecExample: 23
```````````````` example(:number
````````````````
.
<hr />
<h5>: number</h5>
.
Document[0, 49]
  SpecExampleBlock[0, 49] openingMarker:[0, 16] exampleKeyword:[17, 24] coordOpeningMarker:[24, 25] numberSeparator:[25, 26] number:[26, 32] closingMarker:[33, 49]
    SpecExampleSource[33, 33]
````````````````````````````````


Wrapped Coordinates, section and number

```````````````````````````````` example SpecExample: 24
```````````````` example(Section:number
…
…
````````````````
.
<hr />
<h5>Section: number</h5>
.
Document[0, 60]
  SpecExampleBlock[0, 60] openingMarker:[0, 16] exampleKeyword:[17, 24] coordOpeningMarker:[24, 25] section:[25, 32] numberSeparator:[32, 33] number:[33, 39] source:[40, 40] htmlSeparator:[40, 42] html:[42, 42] astSeparator:[42, 44] ast:[44, 44] closingMarker:[44, 60]
    SpecExampleSource[40, 40]
    SpecExampleSeparator[40, 42] chars:[40, 42, "…\n"]
    SpecExampleHtml[42, 42]
    SpecExampleSeparator[42, 44] chars:[42, 44, "…\n"]
    SpecExampleAst[44, 44]
````````````````````````````````


Wrapped Coordinates, section

```````````````````````````````` example SpecExample: 25
```````````````` example Section)
````````````````
.
<hr />
<h5>Section: </h5>
.
Document[0, 50]
  SpecExampleBlock[0, 50] openingMarker:[0, 16] exampleKeyword:[17, 24] section:[25, 32] coordClosingMarker:[32, 33] closingMarker:[34, 50]
    SpecExampleSource[34, 34]
````````````````````````````````


Wrapped Coordinates, number

```````````````````````````````` example SpecExample: 26
```````````````` example :number)
````````````````
.
<hr />
<h5>: number</h5>
.
Document[0, 50]
  SpecExampleBlock[0, 50] openingMarker:[0, 16] exampleKeyword:[17, 24] numberSeparator:[25, 26] number:[26, 32] coordClosingMarker:[32, 33] closingMarker:[34, 50]
    SpecExampleSource[34, 34]
````````````````````````````````


Wrapped Coordinates, section and number

```````````````````````````````` example SpecExample: 27
```````````````` example Section:number)
…
…
````````````````
.
<hr />
<h5>Section: number</h5>
.
Document[0, 61]
  SpecExampleBlock[0, 61] openingMarker:[0, 16] exampleKeyword:[17, 24] section:[25, 32] numberSeparator:[32, 33] number:[33, 39] coordClosingMarker:[39, 40] source:[41, 41] htmlSeparator:[41, 43] html:[43, 43] astSeparator:[43, 45] ast:[45, 45] closingMarker:[45, 61]
    SpecExampleSource[41, 41]
    SpecExampleSeparator[41, 43] chars:[41, 43, "…\n"]
    SpecExampleHtml[43, 43]
    SpecExampleSeparator[43, 45] chars:[43, 45, "…\n"]
    SpecExampleAst[45, 45]
````````````````````````````````


Plain options

```````````````````````````````` example SpecExample: 28
```````````````` example options
````````````````
.
<hr />
.
Document[0, 49]
  SpecExampleBlock[0, 49] openingMarker:[0, 16] exampleKeyword:[17, 24] optionsKeyword:[25, 32] options:[32, 32] closingMarker:[33, 49]
    SpecExampleOptionsList[32, 32]
    SpecExampleSource[33, 33]
````````````````````````````````


Wrapped options

```````````````````````````````` example SpecExample: 29
```````````````` example options()
````````````````
.
<hr />
.
Document[0, 51]
  SpecExampleBlock[0, 51] openingMarker:[0, 16] exampleKeyword:[17, 24] optionsKeyword:[25, 32] optionsOpeningMarker:[32, 33] options:[33, 33] optionsClosingMarker:[33, 34] closingMarker:[35, 51]
    SpecExampleOptionsList[33, 33]
    SpecExampleSource[35, 35]
````````````````````````````````


Wrapped options

```````````````````````````````` example SpecExample: 30
```````````````` example options(
…
…
````````````````
.
<hr />
.
Document[0, 54]
  SpecExampleBlock[0, 54] openingMarker:[0, 16] exampleKeyword:[17, 24] optionsKeyword:[25, 32] optionsOpeningMarker:[32, 33] options:[33, 33] source:[34, 34] htmlSeparator:[34, 36] html:[36, 36] astSeparator:[36, 38] ast:[38, 38] closingMarker:[38, 54]
    SpecExampleOptionsList[33, 33]
    SpecExampleSource[34, 34]
    SpecExampleSeparator[34, 36] chars:[34, 36, "…\n"]
    SpecExampleHtml[36, 36]
    SpecExampleSeparator[36, 38] chars:[36, 38, "…\n"]
    SpecExampleAst[38, 38]
````````````````````````````````


Unwrapped options as section

```````````````````````````````` example SpecExample: 31
```````````````` example options)
…
…
````````````````
.
<hr />
.
Document[0, 54]
  SpecExampleBlock[0, 54] openingMarker:[0, 16] exampleKeyword:[17, 24] optionsKeyword:[25, 32] options:[32, 32] optionsClosingMarker:[32, 33] source:[34, 34] htmlSeparator:[34, 36] html:[36, 36] astSeparator:[36, 38] ast:[38, 38] closingMarker:[38, 54]
    SpecExampleOptionsList[32, 32]
    SpecExampleSource[34, 34]
    SpecExampleSeparator[34, 36] chars:[34, 36, "…\n"]
    SpecExampleHtml[36, 36]
    SpecExampleSeparator[36, 38] chars:[36, 38, "…\n"]
    SpecExampleAst[38, 38]
````````````````````````````````


Plain options

```````````````````````````````` example SpecExample: 32
```````````````` example options option
````````````````
.
<hr />
.
Document[0, 56]
  SpecExampleBlock[0, 56] openingMarker:[0, 16] exampleKeyword:[17, 24] optionsKeyword:[25, 32] options:[33, 39] closingMarker:[40, 56]
    SpecExampleOptionsList[33, 39] chars:[33, 39, "option"]
      SpecExampleOption[33, 39] chars:[33, 39, "option"]
    SpecExampleSource[40, 40]
````````````````````````````````


Wrapped options

```````````````````````````````` example SpecExample: 33
```````````````` example options(option)
````````````````
.
<hr />
.
Document[0, 57]
  SpecExampleBlock[0, 57] openingMarker:[0, 16] exampleKeyword:[17, 24] optionsKeyword:[25, 32] optionsOpeningMarker:[32, 33] options:[33, 39] optionsClosingMarker:[39, 40] closingMarker:[41, 57]
    SpecExampleOptionsList[33, 39] chars:[33, 39, "option"]
      SpecExampleOption[33, 39] chars:[33, 39, "option"]
    SpecExampleSource[41, 41]
````````````````````````````````


Wrapped options

```````````````````````````````` example SpecExample: 34
```````````````` example options(option
…
…
````````````````
.
<hr />
.
Document[0, 60]
  SpecExampleBlock[0, 60] openingMarker:[0, 16] exampleKeyword:[17, 24] optionsKeyword:[25, 32] optionsOpeningMarker:[32, 33] options:[33, 39] source:[40, 40] htmlSeparator:[40, 42] html:[42, 42] astSeparator:[42, 44] ast:[44, 44] closingMarker:[44, 60]
    SpecExampleOptionsList[33, 39] chars:[33, 39, "option"]
      SpecExampleOption[33, 39] chars:[33, 39, "option"]
    SpecExampleSource[40, 40]
    SpecExampleSeparator[40, 42] chars:[40, 42, "…\n"]
    SpecExampleHtml[42, 42]
    SpecExampleSeparator[42, 44] chars:[42, 44, "…\n"]
    SpecExampleAst[44, 44]
````````````````````````````````


Unwrapped options as section

```````````````````````````````` example SpecExample: 35
```````````````` example options option)
…
…
````````````````
.
<hr />
.
Document[0, 61]
  SpecExampleBlock[0, 61] openingMarker:[0, 16] exampleKeyword:[17, 24] optionsKeyword:[25, 32] options:[33, 39] optionsClosingMarker:[39, 40] source:[41, 41] htmlSeparator:[41, 43] html:[43, 43] astSeparator:[43, 45] ast:[45, 45] closingMarker:[45, 61]
    SpecExampleOptionsList[33, 39] chars:[33, 39, "option"]
      SpecExampleOption[33, 39] chars:[33, 39, "option"]
    SpecExampleSource[41, 41]
    SpecExampleSeparator[41, 43] chars:[41, 43, "…\n"]
    SpecExampleHtml[43, 43]
    SpecExampleSeparator[43, 45] chars:[43, 45, "…\n"]
    SpecExampleAst[45, 45]
````````````````````````````````


Wrapped options

```````````````````````````````` example SpecExample: 36
```````````````` example options(1, 2,, ,  ,3 , 4 )
````````````````
.
<hr />
.
Document[0, 68]
  SpecExampleBlock[0, 68] openingMarker:[0, 16] exampleKeyword:[17, 24] optionsKeyword:[25, 32] optionsOpeningMarker:[32, 33] options:[33, 50] optionsClosingMarker:[50, 51] closingMarker:[52, 68]
    SpecExampleOptionsList[33, 50] chars:[33, 50, "1, 2, …  , 4 "]
      SpecExampleOption[33, 34] chars:[33, 34, "1"]
      SpecExampleOptionSeparator[34, 35]
      SpecExampleOption[36, 37] chars:[36, 37, "2"]
      SpecExampleOptionSeparator[37, 38]
      SpecExampleOptionSeparator[38, 39]
      SpecExampleOptionSeparator[40, 41]
      SpecExampleOptionSeparator[43, 44]
      SpecExampleOption[44, 45] chars:[44, 45, "3"]
      SpecExampleOptionSeparator[46, 47]
      SpecExampleOption[48, 49] chars:[48, 49, "4"]
    SpecExampleSource[52, 52]
````````````````````````````````


Unwrapped coords Wrapped options

```````````````````````````````` example SpecExample: 37
```````````````` example section:number options(1, 2,3 , 4 )
````````````````
.
<hr />
<h5>section: number</h5>
.
Document[0, 77]
  SpecExampleBlock[0, 77] openingMarker:[0, 16] exampleKeyword:[17, 24] section:[25, 32] numberSeparator:[32, 33] number:[33, 39] optionsKeyword:[40, 47] optionsOpeningMarker:[47, 48] options:[48, 59] optionsClosingMarker:[59, 60] closingMarker:[61, 77]
    SpecExampleOptionsList[48, 59] chars:[48, 59, "1, 2, …  , 4 "]
      SpecExampleOption[48, 49] chars:[48, 49, "1"]
      SpecExampleOptionSeparator[49, 50]
      SpecExampleOption[51, 52] chars:[51, 52, "2"]
      SpecExampleOptionSeparator[52, 53]
      SpecExampleOption[53, 54] chars:[53, 54, "3"]
      SpecExampleOptionSeparator[55, 56]
      SpecExampleOption[57, 58] chars:[57, 58, "4"]
    SpecExampleSource[61, 61]
````````````````````````````````


Wrapped coords Wrapped options

```````````````````````````````` example SpecExample: 38
```````````````` example(section:number) options(1, 2,3 , 4 )
````````````````
.
<hr />
<h5>section: number</h5>
.
Document[0, 78]
  SpecExampleBlock[0, 78] openingMarker:[0, 16] exampleKeyword:[17, 24] coordOpeningMarker:[24, 25] section:[25, 32] numberSeparator:[32, 33] number:[33, 39] coordClosingMarker:[39, 40] optionsKeyword:[41, 48] optionsOpeningMarker:[48, 49] options:[49, 60] optionsClosingMarker:[60, 61] closingMarker:[62, 78]
    SpecExampleOptionsList[49, 60] chars:[49, 60, "1, 2, …  , 4 "]
      SpecExampleOption[49, 50] chars:[49, 50, "1"]
      SpecExampleOptionSeparator[50, 51]
      SpecExampleOption[52, 53] chars:[52, 53, "2"]
      SpecExampleOptionSeparator[53, 54]
      SpecExampleOption[54, 55] chars:[54, 55, "3"]
      SpecExampleOptionSeparator[56, 57]
      SpecExampleOption[58, 59] chars:[58, 59, "4"]
    SpecExampleSource[62, 62]
````````````````````````````````


Source, HTML and AST and options

```````````````````````````````` example(SpecExample: 39) options(as-def-list)
```````````````` example(Section: Number) options(option)
Markdown only

- List item
…
<p>Markdown only</p>
<ul>
  <li>List item</li>
</ul>
…
Document[0, 56]
  SpecExampleBlock[0, 55] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 38] closingMarker:[39, 55]
    SpecExampleSource[25, 38] chars:[25, 38, "Markd"..." only"]
````````````````
.
<hr />
<dl>
  <dt>example Section: Number options(option)</dt>
  <dt>Source</dt>
  <dd>
  <pre><code class="language-markdown">Markdown only

- List item
</code></pre>
  </dd>
  <dt>Html</dt>
  <dd>
  <pre><code class="language-html">&lt;p&gt;Markdown only&lt;/p&gt;
&lt;ul&gt;
  &lt;li&gt;List item&lt;/li&gt;
&lt;/ul&gt;
</code></pre>
  </dd>
  <dt>Rendered Html</dt>
  <dd><div style="border:solid #cccccc 1px;padding:0 20px 10px 20px;"><p>Markdown only</p>
<ul>
  <li>List item</li>
</ul>
  </div>
  </dd>
  <dt>AST</dt>
  <dd>
  <pre><code class="language-text">Document[0, 56]
  SpecExampleBlock[0, 55] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 38] closingMarker:[39, 55]
    SpecExampleSource[25, 38] chars:[25, 38, &quot;Markd&quot;...&quot; only&quot;]
</code></pre>
  </dd>
</dl>
.
Document[0, 349]
  SpecExampleBlock[0, 349] openingMarker:[0, 16] exampleKeyword:[17, 24] coordOpeningMarker:[24, 25] section:[25, 32] numberSeparator:[32, 33] number:[34, 40] coordClosingMarker:[40, 41] optionsKeyword:[42, 49] optionsOpeningMarker:[49, 50] options:[50, 56] optionsClosingMarker:[56, 57] source:[58, 85] htmlSeparator:[85, 87] html:[87, 140] astSeparator:[140, 142] ast:[142, 333] closingMarker:[333, 349]
    SpecExampleOptionsList[50, 56] chars:[50, 56, "option"]
      SpecExampleOption[50, 56] chars:[50, 56, "option"]
    SpecExampleSource[58, 85] chars:[58, 85, "Markd … item\n"]
    SpecExampleSeparator[85, 87] chars:[85, 87, "…\n"]
    SpecExampleHtml[87, 140] chars:[87, 140, "<p>Ma … /ul>\n"]
    SpecExampleSeparator[140, 142] chars:[140, 142, "…\n"]
    SpecExampleAst[142, 333] chars:[142, 333, "Docum … ly\"]\n"]
````````````````````````````````


Source, HTML and AST and options

```````````````````````````````` example(SpecExample: 40) options(as-fenced-code)
```````````````` example(Section: Number) options(option)
Markdown only

- List item
…
<p>Markdown only</p>
<ul>
  <li>List item</li>
</ul>
…
Document[0, 56]
  SpecExampleBlock[0, 55] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 38] closingMarker:[39, 55]
    SpecExampleSource[25, 38] chars:[25, 38, "Markd"..." only"]
````````````````
.
<pre><code class="language-text">Markdown only

- List item
…
&lt;p&gt;Markdown only&lt;/p&gt;
&lt;ul&gt;
  &lt;li&gt;List item&lt;/li&gt;
&lt;/ul&gt;
…
Document[0, 56]
  SpecExampleBlock[0, 55] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 38] closingMarker:[39, 55]
    SpecExampleSource[25, 38] chars:[25, 38, &quot;Markd&quot;...&quot; only&quot;]
</code></pre>
.
Document[0, 349]
  SpecExampleBlock[0, 349] openingMarker:[0, 16] exampleKeyword:[17, 24] coordOpeningMarker:[24, 25] section:[25, 32] numberSeparator:[32, 33] number:[34, 40] coordClosingMarker:[40, 41] optionsKeyword:[42, 49] optionsOpeningMarker:[49, 50] options:[50, 56] optionsClosingMarker:[56, 57] source:[58, 85] htmlSeparator:[85, 87] html:[87, 140] astSeparator:[140, 142] ast:[142, 333] closingMarker:[333, 349]
    SpecExampleOptionsList[50, 56] chars:[50, 56, "option"]
      SpecExampleOption[50, 56] chars:[50, 56, "option"]
    SpecExampleSource[58, 85] chars:[58, 85, "Markd … item\n"]
    SpecExampleSeparator[85, 87] chars:[85, 87, "…\n"]
    SpecExampleHtml[87, 140] chars:[87, 140, "<p>Ma … /ul>\n"]
    SpecExampleSeparator[140, 142] chars:[140, 142, "…\n"]
    SpecExampleAst[142, 333] chars:[142, 333, "Docum … ly\"]\n"]
````````````````````````````````


No option nodes

```````````````````````````````` example(SpecExample: 41) options(no-option-nodes)
```````````````` example options(1, 2,, ,  ,3 , 4 )
````````````````
.
<hr />
.
Document[0, 68]
  SpecExampleBlock[0, 68] openingMarker:[0, 16] exampleKeyword:[17, 24] optionsKeyword:[25, 32] optionsOpeningMarker:[32, 33] options:[33, 50] optionsClosingMarker:[50, 51] closingMarker:[52, 68]
    SpecExampleSource[52, 52]
````````````````````````````````


## Source Position Attribute

Render sections

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
```````````````` example(Section: Number) options(option)
Markdown only

- List item
…
<p>Markdown only</p>
<ul>
  <li>List item</li>
</ul>
…
Document[0, 56]
  SpecExampleBlock[0, 55] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 38] closingMarker:[39, 55]
    SpecExampleSource[25, 38] chars:[25, 38, "Markd"..." only"]
````````````````
.
<hr />
<h5>Section: Number</h5>
<hr />
<pre><code class="language-markdown" md-pos="58-84">Markdown only

- List item
</code></pre>
<hr />
<pre><code class="language-html" md-pos="87-139">&lt;p&gt;Markdown only&lt;/p&gt;
&lt;ul&gt;
  &lt;li&gt;List item&lt;/li&gt;
&lt;/ul&gt;
</code></pre>
<hr />
<div style="border:solid #cccccc 1px;padding:0 20px 10px 20px;"><p>Markdown only</p>
<ul>
  <li>List item</li>
</ul>
</div>
<hr />
<pre><code class="language-text" md-pos="142-332">Document[0, 56]
  SpecExampleBlock[0, 55] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 38] closingMarker:[39, 55]
    SpecExampleSource[25, 38] chars:[25, 38, &quot;Markd&quot;...&quot; only&quot;]
</code></pre>
.
Document[0, 349]
  SpecExampleBlock[0, 349] openingMarker:[0, 16] exampleKeyword:[17, 24] coordOpeningMarker:[24, 25] section:[25, 32] numberSeparator:[32, 33] number:[34, 40] coordClosingMarker:[40, 41] optionsKeyword:[42, 49] optionsOpeningMarker:[49, 50] options:[50, 56] optionsClosingMarker:[56, 57] source:[58, 85] htmlSeparator:[85, 87] html:[87, 140] astSeparator:[140, 142] ast:[142, 333] closingMarker:[333, 349]
    SpecExampleOptionsList[50, 56] chars:[50, 56, "option"]
      SpecExampleOption[50, 56] chars:[50, 56, "option"]
    SpecExampleSource[58, 85] chars:[58, 85, "Markd … item\n"]
    SpecExampleSeparator[85, 87] chars:[85, 87, "…\n"]
    SpecExampleHtml[87, 140] chars:[87, 140, "<p>Ma … /ul>\n"]
    SpecExampleSeparator[140, 142] chars:[140, 142, "…\n"]
    SpecExampleAst[142, 333] chars:[142, 333, "Docum … ly\"]\n"]
````````````````````````````````


Fenced code rendering

```````````````````````````````` example(Source Position Attribute: 2) options(src-pos, as-fenced-code)
```````````````` example(Section: Number) options(option)
Markdown only

- List item
…
<p>Markdown only</p>
<ul>
  <li>List item</li>
</ul>
…
Document[0, 56]
  SpecExampleBlock[0, 55] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 38] closingMarker:[39, 55]
    SpecExampleSource[25, 38] chars:[25, 38, "Markd"..." only"]
````````````````
.
<pre><code class="language-text" md-pos="58-332">Markdown only

- List item
…
&lt;p&gt;Markdown only&lt;/p&gt;
&lt;ul&gt;
  &lt;li&gt;List item&lt;/li&gt;
&lt;/ul&gt;
…
Document[0, 56]
  SpecExampleBlock[0, 55] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 38] closingMarker:[39, 55]
    SpecExampleSource[25, 38] chars:[25, 38, &quot;Markd&quot;...&quot; only&quot;]
</code></pre>
.
Document[0, 349]
  SpecExampleBlock[0, 349] openingMarker:[0, 16] exampleKeyword:[17, 24] coordOpeningMarker:[24, 25] section:[25, 32] numberSeparator:[32, 33] number:[34, 40] coordClosingMarker:[40, 41] optionsKeyword:[42, 49] optionsOpeningMarker:[49, 50] options:[50, 56] optionsClosingMarker:[56, 57] source:[58, 85] htmlSeparator:[85, 87] html:[87, 140] astSeparator:[140, 142] ast:[142, 333] closingMarker:[333, 349]
    SpecExampleOptionsList[50, 56] chars:[50, 56, "option"]
      SpecExampleOption[50, 56] chars:[50, 56, "option"]
    SpecExampleSource[58, 85] chars:[58, 85, "Markd … item\n"]
    SpecExampleSeparator[85, 87] chars:[85, 87, "…\n"]
    SpecExampleHtml[87, 140] chars:[87, 140, "<p>Ma … /ul>\n"]
    SpecExampleSeparator[140, 142] chars:[140, 142, "…\n"]
    SpecExampleAst[142, 333] chars:[142, 333, "Docum … ly\"]\n"]
````````````````````````````````


Definition list rendering

```````````````````````````````` example(Source Position Attribute: 3) options(src-pos, as-def-list)
```````````````` example(Section: Number) options(option)
Markdown only

- List item
…
<p>Markdown only</p>
<ul>
  <li>List item</li>
</ul>
…
Document[0, 56]
  SpecExampleBlock[0, 55] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 38] closingMarker:[39, 55]
    SpecExampleSource[25, 38] chars:[25, 38, "Markd"..." only"]
````````````````
.
<hr />
<dl>
  <dt>example Section: Number options(option)</dt>
  <dt>Source</dt>
  <dd>
  <pre><code class="language-markdown" md-pos="58-84">Markdown only

- List item
</code></pre>
  </dd>
  <dt>Html</dt>
  <dd>
  <pre><code class="language-html" md-pos="87-139">&lt;p&gt;Markdown only&lt;/p&gt;
&lt;ul&gt;
  &lt;li&gt;List item&lt;/li&gt;
&lt;/ul&gt;
</code></pre>
  </dd>
  <dt>Rendered Html</dt>
  <dd><div style="border:solid #cccccc 1px;padding:0 20px 10px 20px;"><p>Markdown only</p>
<ul>
  <li>List item</li>
</ul>
  </div>
  </dd>
  <dt>AST</dt>
  <dd>
  <pre><code class="language-text" md-pos="142-332">Document[0, 56]
  SpecExampleBlock[0, 55] openingMarker:[0, 16] exampleKeyword:[17, 24] source:[25, 38] closingMarker:[39, 55]
    SpecExampleSource[25, 38] chars:[25, 38, &quot;Markd&quot;...&quot; only&quot;]
</code></pre>
  </dd>
</dl>
.
Document[0, 349]
  SpecExampleBlock[0, 349] openingMarker:[0, 16] exampleKeyword:[17, 24] coordOpeningMarker:[24, 25] section:[25, 32] numberSeparator:[32, 33] number:[34, 40] coordClosingMarker:[40, 41] optionsKeyword:[42, 49] optionsOpeningMarker:[49, 50] options:[50, 56] optionsClosingMarker:[56, 57] source:[58, 85] htmlSeparator:[85, 87] html:[87, 140] astSeparator:[140, 142] ast:[142, 333] closingMarker:[333, 349]
    SpecExampleOptionsList[50, 56] chars:[50, 56, "option"]
      SpecExampleOption[50, 56] chars:[50, 56, "option"]
    SpecExampleSource[58, 85] chars:[58, 85, "Markd … item\n"]
    SpecExampleSeparator[85, 87] chars:[85, 87, "…\n"]
    SpecExampleHtml[87, 140] chars:[87, 140, "<p>Ma … /ul>\n"]
    SpecExampleSeparator[140, 142] chars:[140, 142, "…\n"]
    SpecExampleAst[142, 333] chars:[142, 333, "Docum … ly\"]\n"]
````````````````````````````````


