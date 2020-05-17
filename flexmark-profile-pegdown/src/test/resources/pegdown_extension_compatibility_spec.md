---
title: Pegdown with Extensions Compatibility Spec
author: Vladimir Schneider
version: 0.2
date: '2017-01-07'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Markdown elements

### empty text

empty text

```````````````````````````````` example Markdown elements - empty text: 1
.
````````````````````````````````


### Abbreviation

`Abbreviation` `AbbreviationBlock`

```````````````````````````````` example Markdown elements - Abbreviation: 1
text with abbr embedded

*[abbr]: abbreviation

.
<p>text with <abbr title="abbreviation">abbr</abbr> embedded</p>
````````````````````````````````


```````````````````````````````` example Markdown elements - Abbreviation: 2
*[abbr]: abbreviation

text with abbr embedded
.
<p>text with <abbr title="abbreviation">abbr</abbr> embedded</p>
````````````````````````````````


```````````````````````````````` example Markdown elements - Abbreviation: 3
*[Abbr]:Abbreviation
.
````````````````````````````````


```````````````````````````````` example Markdown elements - Abbreviation: 4
*[Abbr]:Abbreviation

This has an Abbr embedded in it.
.
<p>This has an <abbr title="Abbreviation">Abbr</abbr> embedded in it.</p>
````````````````````````````````


No inline processing in expansion text.

```````````````````````````````` example Markdown elements - Abbreviation: 5
*[Abbr]: Abbreviation has *emphasis*, **bold** or `code`

This has an Abbr embedded in it.
.
<p>This has an <abbr title="Abbreviation has *emphasis*, **bold** or `code`">Abbr</abbr> embedded in it.</p>
````````````````````````````````


```````````````````````````````` example Markdown elements - Abbreviation: 6
*[Abbr]: Abbreviation 1
*[Abbre]: Abbreviation 2
.
````````````````````````````````


```````````````````````````````` example Markdown elements - Abbreviation: 7
*[Abbr]: Abbreviation 1
*[Abbre]: Abbreviation 2

This has an Abbre embedded in it.
And this has another Abbr embedded in it.
.
<p>This has an <abbr title="Abbreviation 2">Abbre</abbr> embedded in it. And this has another <abbr title="Abbreviation 1">Abbr</abbr> embedded in it.</p>
````````````````````````````````


```````````````````````````````` example Markdown elements - Abbreviation: 8
*[U.S.A.]: United States of America
*[US of A]: United States of America

U.S.A. is an abbreviation and so is US of A, an abbreviation.
.
<p><abbr title="United States of America">U.S.A.</abbr> is an abbreviation and so is <abbr title="United States of America">US of A</abbr>, an abbreviation.</p>
````````````````````````````````


```````````````````````````````` example Markdown elements - Abbreviation: 9
*[US]: United States
*[U.S.A.]: United States of America
*[US of A]: United States of America

U.S.A., US of A, and US are all abbreviations.
.
<p><abbr title="United States of America">U.S.A.</abbr>, <abbr title="United States of America">US of A</abbr>, and <abbr title="United States">US</abbr> are all abbreviations.</p>
````````````````````````````````


```````````````````````````````` example Markdown elements - Abbreviation: 10
*[Abbr]: Abbreviation
[Abbr]: http://test.com

This is an Abbr and this is not [Abbr].

.
<p>This is an <abbr title="Abbreviation">Abbr</abbr> and this is not <a href="http://test.com">Abbr</a>.</p>
````````````````````````````````


An abbreviation that is not on the first line is just text.

```````````````````````````````` example Markdown elements - Abbreviation: 11
Paragraph with second line having a reference
*[test]: test abbreviation

.
<p>Paragraph with second line having a reference</p>
````````````````````````````````


### AnchorLink

`AnchorLink`

```````````````````````````````` example(Markdown elements - AnchorLink: 1) options(anchor-links)
Setext Heading 1
===
Setext Heading 2
---
# Heading 1
## Heading 2
### Heading 3
#### Heading 4
##### Heading 5
###### Heading 6
.
<h1><a href="#setext-heading-1" id="setext-heading-1">Setext Heading 1</a></h1>
<h2><a href="#setext-heading-2" id="setext-heading-2">Setext Heading 2</a></h2>
<h1><a href="#heading-1" id="heading-1">Heading 1</a></h1>
<h2><a href="#heading-2" id="heading-2">Heading 2</a></h2>
<h3><a href="#heading-3" id="heading-3">Heading 3</a></h3>
<h4><a href="#heading-4" id="heading-4">Heading 4</a></h4>
<h5><a href="#heading-5" id="heading-5">Heading 5</a></h5>
<h6><a href="#heading-6" id="heading-6">Heading 6</a></h6>
````````````````````````````````


### AutoLink

`AutoLink`

Wraped autolink

```````````````````````````````` example Markdown elements - AutoLink: 1
text <http://autolink.com> embedded

.
<p>text <a href="http://autolink.com">http://autolink.com</a> embedded</p>
````````````````````````````````


Plain autolink

```````````````````````````````` example Markdown elements - AutoLink: 2
text http://autolink.com embedded

.
<p>text <a href="http://autolink.com">http://autolink.com</a> embedded</p>
````````````````````````````````


mail autolink

```````````````````````````````` example Markdown elements - AutoLink: 3
text example@example.com

.
<p>text <a href="&#109;&#x61;i&#x6c;&#116;&#x6f;&#58;&#101;xa&#x6d;&#x70;&#x6c;&#x65;&#64;ex&#97;&#109;&#112;&#x6c;&#x65;.&#x63;&#x6f;&#109;">&#x65;&#x78;&#x61;&#x6d;&#112;&#108;&#x65;&#x40;&#x65;&#x78;&#97;&#x6d;&#x70;&#108;&#101;&#46;&#99;&#111;&#x6d;</a></p>
````````````````````````````````


### BlockQuote

`BlockQuote`

Lazy continuation, no prefix

```````````````````````````````` example Markdown elements - BlockQuote: 1
> block quote
with lazy continuation
>
.
<blockquote>
  <p>block quote with lazy continuation</p>
</blockquote>
````````````````````````````````


Lazy continuation, with prefix

```````````````````````````````` example Markdown elements - BlockQuote: 2
> block quote
> with lazy continuation
>
.
<blockquote>
  <p>block quote with lazy continuation</p>
</blockquote>
````````````````````````````````


Nested, Lazy continuation, no prefix

```````````````````````````````` example Markdown elements - BlockQuote: 3
>> block quote
with lazy continuation
>>
.
<blockquote>
  <blockquote>
    <p>block quote with lazy continuation</p>
  </blockquote>
</blockquote>
````````````````````````````````


Nested, Lazy continuation, with prefix

```````````````````````````````` example Markdown elements - BlockQuote: 4
>> block quote
>> with lazy continuation
>>
.
<blockquote>
  <blockquote>
    <p>block quote with lazy continuation</p>
  </blockquote>
</blockquote>
````````````````````````````````


Nested, Lazy continuation less, with prefix

```````````````````````````````` example Markdown elements - BlockQuote: 5
>> block quote
> with lazy continuation
>> 
.
<blockquote>
  <blockquote>
    <p>block quote with lazy continuation</p>
  </blockquote>
</blockquote>
````````````````````````````````


Nested, Lazy continuation more, with prefix

```````````````````````````````` example Markdown elements - BlockQuote: 6
>> block quote
>> with lazy continuation
>> 
.
<blockquote>
  <blockquote>
    <p>block quote with lazy continuation</p>
  </blockquote>
</blockquote>
````````````````````````````````


Nested, Lazy continuation less, with prefix

```````````````````````````````` example Markdown elements - BlockQuote: 7
>> block quote
with lazy continuation
>>
>> more text
.
<blockquote>
  <blockquote>
    <p>block quote with lazy continuation</p>
    <p>more text</p>
  </blockquote>
</blockquote>
````````````````````````````````


Nested, Lazy continuation, with prefix

```````````````````````````````` example Markdown elements - BlockQuote: 8
>> block quote
>> with lazy continuation
>>
>> more text
.
<blockquote>
  <blockquote>
    <p>block quote with lazy continuation</p>
    <p>more text</p>
  </blockquote>
</blockquote>
````````````````````````````````


Nested, Lazy continuation less, with prefix

```````````````````````````````` example Markdown elements - BlockQuote: 9
>> block quote
> with lazy continuation
>>
>> more text
.
<blockquote>
  <blockquote>
    <p>block quote with lazy continuation</p>
    <p>more text</p>
  </blockquote>
</blockquote>
````````````````````````````````


### BulletList

`BulletList` `BulletListItem` `TaskListItem` `TaskListItemMarker`

empty

```````````````````````````````` example Markdown elements - BulletList: 1
+ 

.
<ul>
  <li></li>
</ul>
````````````````````````````````


empty, generates exception

```````````````````````````````` example(Markdown elements - BulletList: 2) options(IGNORE)
- [ ] 

.
<ul>
  <li></li>
</ul>
````````````````````````````````


nested

```````````````````````````````` example Markdown elements - BulletList: 3
- item 1
* item 2
    - item 2.1
+ item 3
.
<ul>
  <li>item 1</li>
  <li>item 2
    <ul>
      <li>item 2.1</li>
    </ul>
  </li>
  <li>item 3</li>
</ul>
````````````````````````````````


nested some loose

```````````````````````````````` example Markdown elements - BulletList: 4
- item 1

* item 2
    - item 2.1
+ item 3
.
<ul>
  <li>
    <p>item 1</p>
  </li>
  <li>
    <p>item 2</p>
    <ul>
      <li>item 2.1</li>
    </ul>
  </li>
  <li>item 3</li>
</ul>
````````````````````````````````


nested loose

```````````````````````````````` example Markdown elements - BulletList: 5
- item 1

* item 2
    - item 2.1
    
+ item 3
.
<ul>
  <li>
    <p>item 1</p>
  </li>
  <li>
    <p>item 2</p>
    <ul>
      <li>item 2.1</li>
    </ul>
  </li>
  <li>
    <p>item 3</p>
  </li>
</ul>
````````````````````````````````


tight nested loose

```````````````````````````````` example Markdown elements - BulletList: 6
- item 1
* item 2

    - item 2.1
    
    - item 2.1
+ item 3
.
<ul>
  <li>item 1</li>
  <li>item 2
    <ul>
      <li>
        <p>item 2.1</p>
      </li>
      <li>
        <p>item 2.1</p>
      </li>
    </ul>
  </li>
  <li>item 3</li>
</ul>
````````````````````````````````


nested task item

```````````````````````````````` example Markdown elements - BulletList: 7
- item 1
* item 2
    - [ ] item 2.1
    - item 2.2
+ item 3
.
<ul>
  <li>item 1</li>
  <li>item 2
    <ul>
      <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 2.1</li>
      <li>item 2.2</li>
    </ul>
  </li>
  <li>item 3</li>
</ul>
````````````````````````````````


task item, nested

```````````````````````````````` example Markdown elements - BulletList: 8
- item 1
* item 2
* [ ] item 2
    - item 2.1
    - item 2.2
+ item 3
.
<ul>
  <li>item 1</li>
  <li>item 2</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 2
    <ul>
      <li>item 2.1</li>
      <li>item 2.2</li>
    </ul>
  </li>
  <li>item 3</li>
</ul>
````````````````````````````````


task item, nested task item

```````````````````````````````` example Markdown elements - BulletList: 9
- item 1
* item 2
* [ ] item 2
    - item 2.1
    - [ ] item 2.2
+ item 3
.
<ul>
  <li>item 1</li>
  <li>item 2</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 2
    <ul>
      <li>item 2.1</li>
      <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 2.2</li>
    </ul>
  </li>
  <li>item 3</li>
</ul>
````````````````````````````````


A bullet list after an ordered list

```````````````````````````````` example Markdown elements - BulletList: 10
2. item 1
1. item 2
5. [ ] tem 3

- item 1
- item 2
- [ ] item 3
.
<ol>
  <li>item 1</li>
  <li>item 2</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;tem 3</li>
</ol>
<ul>
  <li>item 1</li>
  <li>item 2</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 3</li>
</ul>
````````````````````````````````


Bullet items must have a blank line before them when preceded by paragraph but should not append
following child paragraph

```````````````````````````````` example Markdown elements - BulletList: 11
- item 1 paragraph
    * sublist
- item 2 paragraph

    paragraph
.
<ul>
  <li>item 1 paragraph
    <ul>
      <li>sublist</li>
    </ul>
  </li>
  <li>item 2 paragraph
    <p>paragraph</p>
  </li>
</ul>
````````````````````````````````


Bullet items can have headings as children

```````````````````````````````` example Markdown elements - BulletList: 12
- Some Lists
    
    # Test
.
<ul>
  <li>Some Lists
    <h1><a href="#test" id="test"></a>Test</h1>
  </li>
</ul>
````````````````````````````````


### Code

`Code`

Plain text with unterminated or empty code

```````````````````````````````` example Markdown elements - Code: 1
First line
Second line ``
Last line
.
<p>First line Second line `` Last line</p>
````````````````````````````````


Plain text with simple code

```````````````````````````````` example Markdown elements - Code: 2
First line
Second line `code`
Last line
.
<p>First line Second line <code>code</code> Last line</p>
````````````````````````````````


Plain text with code with embedded looking HTML comment

```````````````````````````````` example Markdown elements - Code: 3
First line
Second line `<!--code-->`
Last line
.
<p>First line Second line <code>&lt;!--code--&gt;</code> Last line</p>
````````````````````````````````


### DefinitionItem

`DefinitionList` `DefinitionTerm` `DefinitionItem`

No optional : at end

```````````````````````````````` example Markdown elements - DefinitionItem: 1
Definition Term
: definition item 
.
<dl>
  <dt>Definition Term</dt>
  <dd>definition item</dd>
</dl>
````````````````````````````````


```````````````````````````````` example Markdown elements - DefinitionItem: 2
Definition Term
~ definition item 
.
<dl>
  <dt>Definition Term</dt>
  <dd>definition item</dd>
</dl>
````````````````````````````````


A simple definition list:

```````````````````````````````` example Markdown elements - DefinitionItem: 3
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

```````````````````````````````` example Markdown elements - DefinitionItem: 4
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

```````````````````````````````` example Markdown elements - DefinitionItem: 5
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

```````````````````````````````` example Markdown elements - DefinitionItem: 6
Term 1
:   Definition 1 line 1 
Definition 1 line 2
:   Definition 2 line 1 
Definition 2 line 2

Term 2
:   Definition 3 line 2 
    Definition 3 line 2
:   Definition 4 line 2 
    Definition 4 line 2

.
<dl>
  <dt>Term 1</dt>
  <dd>Definition 1 line 1 Definition 1 line 2</dd>
  <dd>Definition 2 line 1 Definition 2 line 2</dd>
  <dt>Term 2</dt>
  <dd>Definition 3 line 2 Definition 3 line 2</dd>
  <dd>Definition 4 line 2 Definition 4 line 2</dd>
</dl>
````````````````````````````````


With paragraphs:

```````````````````````````````` example Markdown elements - DefinitionItem: 7
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

```````````````````````````````` example Markdown elements - DefinitionItem: 8
Term 1

:   Definition 1 paragraph 1 line 1 
→Definition 1 paragraph 1 line 2

    Definition 1 paragraph 2 line 1 
    Definition 1 paragraph 2 line 2

Term 2

:   Definition 1 paragraph 1 line 1 
Definition 1 paragraph 1 line 2 (lazy)

    Definition 1 paragraph 2 line 1 
Definition 1 paragraph 2 line 2 (lazy)

.
<dl>
  <dt>Term 1</dt>
  <dd>
    <p>Definition 1 paragraph 1 line 1 Definition 1 paragraph 1 line 2</p>
    <p>Definition 1 paragraph 2 line 1 Definition 1 paragraph 2 line 2</p>
  </dd>
  <dt>Term 2</dt>
  <dd>
    <p>Definition 1 paragraph 1 line 1 Definition 1 paragraph 1 line 2 (lazy)</p>
    <p>Definition 1 paragraph 2 line 1 Definition 1 paragraph 2 line 2 (lazy)</p>
  </dd>
</dl>
````````````````````````````````


A mix:

```````````````````````````````` example Markdown elements - DefinitionItem: 9
Term 1
Term 2

:   Definition 1 paragraph 1 line 1 
Definition 1 paragraph 1 line 2 (lazy)
    
    Definition 1 paragraph 2 line 1 
    Definition 1 paragraph 2 line 2

:   Definition 2 paragraph 1 line 1 
Definition 2 paragraph 1 line 2 (lazy)

Term 3
:   Definition 3 (no paragraph)
:   Definition 4 (no paragraph)
:   Definition 5 line 1
    Definition 5 line 2 (no paragraph)

:   Definition 6 paragraph 1 line 1 
Definition 6 paragraph 1 line 2
:   Definition 7 (no paragraph)
:   Definition 8 paragraph 1 line 1 (forced paragraph) 
    Definition 8 paragraph 1 line 2
    
    Definition 8 paragraph 2 line 1
    
Term 4
:   Definition 9 paragraph 1 line 1 (forced paragraph) 
    Definition 9 paragraph 1 line 2
    
    Definition 9 paragraph 2 line 1
:   Definition 10 (no paragraph)
.
<dl>
  <dt>Term 1</dt>
  <dt>Term 2</dt>
  <dd>
    <p>Definition 1 paragraph 1 line 1 Definition 1 paragraph 1 line 2 (lazy)</p>
    <p>Definition 1 paragraph 2 line 1 Definition 1 paragraph 2 line 2</p>
  </dd>
  <dd>
    <p>Definition 2 paragraph 1 line 1 Definition 2 paragraph 1 line 2 (lazy)</p>
  </dd>
  <dt>Term 3</dt>
  <dd>Definition 3 (no paragraph)</dd>
  <dd>Definition 4 (no paragraph)</dd>
  <dd>Definition 5 line 1 Definition 5 line 2 (no paragraph)</dd>
  <dd>
    <p>Definition 6 paragraph 1 line 1 Definition 6 paragraph 1 line 2</p>
  </dd>
  <dd>Definition 7 (no paragraph)</dd>
  <dd>Definition 8 paragraph 1 line 1 (forced paragraph) Definition 8 paragraph 1 line 2
    <p>Definition 8 paragraph 2 line 1</p>
  </dd>
  <dt>Term 4</dt>
  <dd>Definition 9 paragraph 1 line 1 (forced paragraph) Definition 9 paragraph 1 line 2
    <p>Definition 9 paragraph 2 line 1</p>
  </dd>
  <dd>Definition 10 (no paragraph)</dd>
</dl>
````````````````````````````````


### Emphasis

`Emphasis`

underscore

```````````````````````````````` example Markdown elements - Emphasis: 1
_italic_
text _italic_ embedded 
_italic_ embedded 
text _italic_ 
.
<p><em>italic</em> text <em>italic</em> embedded <em>italic</em> embedded text <em>italic</em></p>
````````````````````````````````


asterisk

```````````````````````````````` example Markdown elements - Emphasis: 2
*italic*
text *italic* embedded 
*italic* embedded 
text *italic* 
.
<p><em>italic</em> text <em>italic</em> embedded <em>italic</em> embedded text <em>italic</em></p>
````````````````````````````````


### EscapedCharacter

`EscapedCharacter`

```````````````````````````````` example Markdown elements - EscapedCharacter: 1
\\  \* \~ \t \"
.
<p>\  * ~ \t &quot;</p>
````````````````````````````````


### FencedCodeBlock

`FencedCodeBlock`

empty, no info

```````````````````````````````` example Markdown elements - FencedCodeBlock: 1
```

```
.
<pre><code>
</code></pre>
````````````````````````````````


unterminated

```````````````````````````````` example Markdown elements - FencedCodeBlock: 2
```
.
<pre><code></code></pre>
````````````````````````````````


empty, no info, blank line follows

```````````````````````````````` example Markdown elements - FencedCodeBlock: 3
```

```

.
<pre><code>
</code></pre>
````````````````````````````````


empty, info

```````````````````````````````` example Markdown elements - FencedCodeBlock: 4
```info

```
.
<pre><code class="info">
</code></pre>
````````````````````````````````


empty, info, blank line follows

```````````````````````````````` example Markdown elements - FencedCodeBlock: 5
```info

```

.
<pre><code class="info">
</code></pre>
````````````````````````````````


non empty, no info, blank line follows

```````````````````````````````` example Markdown elements - FencedCodeBlock: 6
```
some text
```

.
<pre><code>some text
</code></pre>
````````````````````````````````


non empty, info

```````````````````````````````` example Markdown elements - FencedCodeBlock: 7
```info
some text
```
.
<pre><code class="info">some text
</code></pre>
````````````````````````````````


non empty, info, blank line follows

```````````````````````````````` example Markdown elements - FencedCodeBlock: 8
```info
some text
```

.
<pre><code class="info">some text
</code></pre>
````````````````````````````````


non empty, info, blank line follows, unmatched

```````````````````````````````` example Markdown elements - FencedCodeBlock: 9
```info
some text
~~~

.
<pre><code class="info">some text
</code></pre>
````````````````````````````````


### HardLineBreak

`HardLineBreak`

minimal

```````````````````````````````` example(Markdown elements - HardLineBreak: 1) options(hard-breaks)
text with hard line break  
more text
.
<p>text with hard line break<br />
more text</p>
````````````````````````````````


non minimal

```````````````````````````````` example(Markdown elements - HardLineBreak: 2) options(hard-breaks)
text with hard line break   
more text
.
<p>text with hard line break<br />
more text</p>
````````````````````````````````


### Heading

`Heading`

Setext 1

```````````````````````````````` example(Markdown elements - Heading: 1) options(anchor-links)
Heading 1
===
.
<h1><a href="#heading-1" id="heading-1">Heading 1</a></h1>
````````````````````````````````


Setext 2

```````````````````````````````` example(Markdown elements - Heading: 2) options(anchor-links)
Heading 2
---
.
<h2><a href="#heading-2" id="heading-2">Heading 2</a></h2>
````````````````````````````````


Setext 1 with inlines

```````````````````````````````` example(Markdown elements - Heading: 3) options(anchor-links)
Heading 1 **bold** _italic_ `code` 
==================================
.
<h1><a href="#heading-1-bold-italic-code" id="heading-1-bold-italic-code">Heading 1 <strong>bold</strong> <em>italic</em> <code>code</code></a></h1>
````````````````````````````````


Setext 2 with inliines

```````````````````````````````` example(Markdown elements - Heading: 4) options(anchor-links)
Heading 2 **bold** _italic_ `code` 
----------------------------------
.
<h2><a href="#heading-2-bold-italic-code" id="heading-2-bold-italic-code">Heading 2 <strong>bold</strong> <em>italic</em> <code>code</code></a></h2>
````````````````````````````````


Atx 1

```````````````````````````````` example(Markdown elements - Heading: 5) options(anchor-links)
# Heading 1
# Heading 1 Tail #
.
<h1><a href="#heading-1" id="heading-1">Heading 1</a></h1>
<h1><a href="#heading-1-tail" id="heading-1-tail">Heading 1 Tail</a></h1>
````````````````````````````````


Atx 1 with inlines

```````````````````````````````` example(Markdown elements - Heading: 6) options(anchor-links)
# Heading 1 **bold** _italic_ `code`
.
<h1><a href="#heading-1-bold-italic-code" id="heading-1-bold-italic-code">Heading 1 <strong>bold</strong> <em>italic</em> <code>code</code></a></h1>
````````````````````````````````


Atx 2

```````````````````````````````` example(Markdown elements - Heading: 7) options(anchor-links)
## Heading 2
## Heading 2 Tail #
.
<h2><a href="#heading-2" id="heading-2">Heading 2</a></h2>
<h2><a href="#heading-2-tail" id="heading-2-tail">Heading 2 Tail</a></h2>
````````````````````````````````


Atx 3

```````````````````````````````` example(Markdown elements - Heading: 8) options(anchor-links)
### Heading 3
### Heading 3 Tail #
.
<h3><a href="#heading-3" id="heading-3">Heading 3</a></h3>
<h3><a href="#heading-3-tail" id="heading-3-tail">Heading 3 Tail</a></h3>
````````````````````````````````


Atx 4

```````````````````````````````` example(Markdown elements - Heading: 9) options(anchor-links)
#### Heading 4
#### Heading 4 Tail #
.
<h4><a href="#heading-4" id="heading-4">Heading 4</a></h4>
<h4><a href="#heading-4-tail" id="heading-4-tail">Heading 4 Tail</a></h4>
````````````````````````````````


Atx 5

```````````````````````````````` example(Markdown elements - Heading: 10) options(anchor-links)
##### Heading 5
##### Heading 5 Tail #
.
<h5><a href="#heading-5" id="heading-5">Heading 5</a></h5>
<h5><a href="#heading-5-tail" id="heading-5-tail">Heading 5 Tail</a></h5>
````````````````````````````````


Atx 6

```````````````````````````````` example(Markdown elements - Heading: 11) options(anchor-links)
###### Heading 6
###### Heading 6 Tail #
.
<h6><a href="#heading-6" id="heading-6">Heading 6</a></h6>
<h6><a href="#heading-6-tail" id="heading-6-tail">Heading 6 Tail</a></h6>
````````````````````````````````


### HtmlBlock

`HtmlBlock`

Html Blocks

```````````````````````````````` example Markdown elements - HtmlBlock: 1
<div>
  <ul>
    <li>item</li>
  </ul>
</div>
.
<div>
  <ul>
    <li>item</li>
  </ul>
</div>
````````````````````````````````


### HtmlCommentBlock

`HtmlCommentBlock`

empty

```````````````````````````````` example Markdown elements - HtmlCommentBlock: 1
<!---->
.
<!---->
````````````````````````````````


non-empty, no whitespace

```````````````````````````````` example Markdown elements - HtmlCommentBlock: 2
<!--test-->
.
<!--test-->
````````````````````````````````


non-empty whitespace

```````````````````````````````` example Markdown elements - HtmlCommentBlock: 3
<!-- test -->
.
<!-- test -->
````````````````````````````````


### HtmlEntity

`HtmlEntity`

named

```````````````````````````````` example Markdown elements - HtmlEntity: 1
&nbsp;
.
<p> </p>
````````````````````````````````


numbered

```````````````````````````````` example Markdown elements - HtmlEntity: 2
&#10;
.
<p>
</p>
````````````````````````````````


named embedded

```````````````````````````````` example Markdown elements - HtmlEntity: 3
text with &nbsp; embedded
.
<p>text with   embedded</p>
````````````````````````````````


numbered embedded

```````````````````````````````` example Markdown elements - HtmlEntity: 4
text with &#10; embedded
.
<p>text with 
embedded</p>
````````````````````````````````


named embedded, no whitespace

```````````````````````````````` example Markdown elements - HtmlEntity: 5
text with&nbsp;embedded
.
<p>text with embedded</p>
````````````````````````````````


numbered embedded, no whitespace

```````````````````````````````` example Markdown elements - HtmlEntity: 6
text with&#10;embedded
.
<p>text with
embedded</p>
````````````````````````````````


### HtmlInline

`HtmlInline`

empty

```````````````````````````````` example Markdown elements - HtmlInline: 1
<span></span>
.
<p><span></span></p>
````````````````````````````````


non-empty

```````````````````````````````` example Markdown elements - HtmlInline: 2
<span>span</span>
.
<p><span>span</span></p>
````````````````````````````````


empty leading

```````````````````````````````` example Markdown elements - HtmlInline: 3
<span></span> embedded
.
<p><span></span> embedded</p>
````````````````````````````````


non-empty leading

```````````````````````````````` example Markdown elements - HtmlInline: 4
<span>span</span> embedded
.
<p><span>span</span> embedded</p>
````````````````````````````````


empty embedded

```````````````````````````````` example Markdown elements - HtmlInline: 5
text with <span></span> embedded
.
<p>text with <span></span> embedded</p>
````````````````````````````````


non-empty embedded

```````````````````````````````` example Markdown elements - HtmlInline: 6
text with <span>span</span> embedded
.
<p>text with <span>span</span> embedded</p>
````````````````````````````````


empty trailing

```````````````````````````````` example Markdown elements - HtmlInline: 7
text with <span></span>
.
<p>text with <span></span></p>
````````````````````````````````


non-empty trailing

```````````````````````````````` example Markdown elements - HtmlInline: 8
text with <span>span</span>
.
<p>text with <span>span</span></p>
````````````````````````````````


### HtmlInlineComment

`HtmlInlineComment`

Plain text with empty HTML comment

```````````````````````````````` example Markdown elements - HtmlInlineComment: 1
First line
Second line <!---->
Last line
.
<p>First line Second line <!----> Last line</p>
````````````````````````````````


Html Inline with comment

```````````````````````````````` example Markdown elements - HtmlInlineComment: 2
text <div><!-- HTML Comment --></div> some more text
.
<p>text <div><!-- HTML Comment --></div> some more text</p>
````````````````````````````````


Plain text with simple HTML comment

```````````````````````````````` example Markdown elements - HtmlInlineComment: 3
First line
Second line <!--simple-->
Last line
.
<p>First line Second line <!--simple--> Last line</p>
````````````````````````````````


Plain text with unterminated HTML comment

```````````````````````````````` example Markdown elements - HtmlInlineComment: 4
First line
Second line <!--simple
Last line
.
<p>First line Second line &lt;!&ndash;simple Last line</p>
````````````````````````````````


Plain text with HTML comment with embedded looking code

```````````````````````````````` example Markdown elements - HtmlInlineComment: 5
First line
Second line <!--`code`-->
Last line
.
<p>First line Second line <!--`code`--> Last line</p>
````````````````````````````````


### HtmlInnerBlock

`HtmlInnerBlock` `HtmlInnerBlockComment`

Html Blocks

```````````````````````````````` example Markdown elements - HtmlInnerBlock: 1
<div>
    <!-- HTML Comment -->
</div>
.
<div>
    <!-- HTML Comment -->
</div>
.
Document[0, 38]
  HtmlBlock[0, 38]
    HtmlInnerBlock[0, 10] chars:[0, 10, "<div>\n    "]
    HtmlInnerBlockComment[10, 31] chars:[10, 31, "<!--  … t -->"]
    HtmlInnerBlock[31, 38] chars:[31, 38, "\n</div>"]
````````````````````````````````


### Image

`Image`

plain

```````````````````````````````` example Markdown elements - Image: 1
![alt](/url) 
.
<p><img src="/url" alt="alt" /></p>
````````````````````````````````


embedded

```````````````````````````````` example Markdown elements - Image: 2
text with ![alt](/url) embedded 
.
<p>text with <img src="/url" alt="alt" /> embedded</p>
````````````````````````````````


### ImageRef

`ImageRef`

basic

```````````````````````````````` example Markdown elements - ImageRef: 1
[ref]: /url

![ref]
.
<p><img src="/url" alt="ref" /></p>
````````````````````````````````


undefined

```````````````````````````````` example Markdown elements - ImageRef: 2
[ref2]: /url2

![ref]
.
<p>![ref]</p>
````````````````````````````````


duplicate

```````````````````````````````` example Markdown elements - ImageRef: 3
[ref]: /url1
[ref]: /url2

![ref]
.
<p><img src="/url2" alt="ref" /></p>
````````````````````````````````


dummy ref

```````````````````````````````` example Markdown elements - ImageRef: 4
[ref]: /url1

![ref][]
.
<p><img src="/url1" alt="ref" /></p>
````````````````````````````````


### IndentedCodeBlock

`IndentedCodeBlock`

basic

```````````````````````````````` example Markdown elements - IndentedCodeBlock: 1
    code
.
<pre><code>code
</code></pre>
````````````````````````````````


multi line

```````````````````````````````` example Markdown elements - IndentedCodeBlock: 2
    code
        more code
.
<pre><code>code
    more code
</code></pre>
````````````````````````````````


multi line, blanks

```````````````````````````````` example Markdown elements - IndentedCodeBlock: 3
    code
    
        more code
.
<pre><code>code

    more code
</code></pre>
````````````````````````````````


tabbed

```````````````````````````````` example Markdown elements - IndentedCodeBlock: 4
→code
.
<pre><code>code
</code></pre>
````````````````````````````````


multi line

```````````````````````````````` example Markdown elements - IndentedCodeBlock: 5
→code
→→more code
.
<pre><code>code
→more code
</code></pre>
````````````````````````````````


multi line, blanks

```````````````````````````````` example Markdown elements - IndentedCodeBlock: 6
→code

→→more code
.
<pre><code>code

→more code
</code></pre>
````````````````````````````````


trailing blank lines

```````````````````````````````` example Markdown elements - IndentedCodeBlock: 7
    code
    more code
    
    
.
<pre><code>code
more code
</code></pre>
````````````````````````````````


### JekyllFrontMatterBlock

`JekyllFrontMatterBlock` FlexmarkFrontMatter

Jekyll front matter

```````````````````````````````` example(Markdown elements - JekyllFrontMatterBlock: 1) options(FAIL)
---
title: SimToc Extension Spec
author: 
version: 
date: '2016-06-30'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
---

.

````````````````````````````````


flexmark front matter

```````````````````````````````` example(Markdown elements - JekyllFrontMatterBlock: 2) options(FAIL)
---
title: SimToc Extension Spec
author: 
version: 
date: '2016-06-30'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

.
<hr/>
````````````````````````````````


### Link

`Link`

basic

```````````````````````````````` example Markdown elements - Link: 1
[text](/url) 
.
<p><a href="/url">text</a></p>
````````````````````````````````


emphasis

```````````````````````````````` example Markdown elements - Link: 2
[**text**](/url) 
.
<p><a href="/url"><strong>text</strong></a></p>
````````````````````````````````


code

```````````````````````````````` example Markdown elements - Link: 3
[`text`](/url) 
.
<p><a href="/url"><code>text</code></a></p>
````````````````````````````````


image

```````````````````````````````` example Markdown elements - Link: 4
[![alt](/url2)](/url) 
.
<p><a href="/url"><img src="/url2" alt="alt" /></a></p>
````````````````````````````````


basic embedded

```````````````````````````````` example Markdown elements - Link: 5
text with [text](/url) embedded 
.
<p>text with <a href="/url">text</a> embedded</p>
````````````````````````````````


header embedded

```````````````````````````````` example(Markdown elements - Link: 6) options(anchor-links)
# Heading [text](/url) 
.
<h1><a href="#heading-text" id="heading-text">Heading <a href="/url">text</a></a></h1>
````````````````````````````````


header embedded

```````````````````````````````` example(Markdown elements - Link: 7) options(anchor-links)
Heading [text](/url) 
---
.
<h2><a href="#heading-text" id="heading-text">Heading <a href="/url">text</a></a></h2>
````````````````````````````````


header image embedded

```````````````````````````````` example(Markdown elements - Link: 8) options(anchor-links)
# Heading [![alt](/url2)](/url) 
.
<h1><a href="#heading-alt" id="heading-alt">Heading <a href="/url"><img src="/url2" alt="alt" /></a></a></h1>
````````````````````````````````


header image embedded

```````````````````````````````` example(Markdown elements - Link: 9) options(anchor-links)
Heading [![alt](/url2)](/url) 
---
.
<h2><a href="#heading-alt" id="heading-alt">Heading <a href="/url"><img src="/url2" alt="alt" /></a></a></h2>
````````````````````````````````


### LinkRef

`LinkRef`

basic

```````````````````````````````` example Markdown elements - LinkRef: 1
[ref]: /url

[ref]
.
<p><a href="/url">ref</a></p>
````````````````````````````````


undefined

```````````````````````````````` example Markdown elements - LinkRef: 2
[ref2]: /url2

[ref]
.
<p>[ref]</p>
````````````````````````````````


duplicate

```````````````````````````````` example Markdown elements - LinkRef: 3
[ref]: /url1
[ref]: /url2

[ref]
.
<p><a href="/url2">ref</a></p>
````````````````````````````````


dummy ref

```````````````````````````````` example Markdown elements - LinkRef: 4
[ref]: /url1

[ref][]
.
<p><a href="/url1">ref</a></p>
````````````````````````````````


### MailLink

`MailLink`

basic, comparison may fail because of random obfuscation

```````````````````````````````` example(Markdown elements - MailLink: 1) options(IGNORE)
name@url.dom
.
<p><a href="mailto:&#110;a&#109;&#x65;&#x40;&#x75;&#x72;&#108;&#x2e;&#x64;o&#109;">&#110;a&#109;&#x65;&#x40;&#x75;&#x72;&#108;&#x2e;&#x64;o&#109;</a></p>
````````````````````````````````


basic leading

```````````````````````````````` example(Markdown elements - MailLink: 2) options(IGNORE)
name@url.dom embedded
.
<p><a href="mailto:&#x6e;&#97;m&#101;&#64;&#117;r&#108;.&#x64;&#111;&#x6d;">&#x6e;&#97;m&#101;&#64;&#117;r&#108;.&#x64;&#111;&#x6d;</a> embedded</p>
````````````````````````````````


basic embedded

```````````````````````````````` example(Markdown elements - MailLink: 3) options(IGNORE)
text with name@url.dom embedded
.
<p>text with <a href="mailto:&#x6e;&#97;&#109;e&#x40;&#x75;r&#108;&#x2e;&#x64;&#111;&#109;">&#x6e;&#97;&#109;e&#x40;&#x75;r&#108;&#x2e;&#x64;&#111;&#109;</a> embedded</p>
````````````````````````````````


basic trailing

```````````````````````````````` example(Markdown elements - MailLink: 4) options(IGNORE)
text with name@url.dom
.
<p>text with <a href="mailto:&#110;&#97;&#109;&#x65;&#x40;&#117;&#114;&#108;&#46;&#x64;&#111;&#109;">&#110;&#97;&#109;&#x65;&#x40;&#117;&#114;&#108;&#46;&#x64;&#111;&#109;</a></p>
````````````````````````````````


### OrderedList

`OrderedList` `OrderedListItem`

empty

```````````````````````````````` example Markdown elements - OrderedList: 1
1. 

.
<ol>
  <li></li>
</ol>
````````````````````````````````


empty task list

```````````````````````````````` example Markdown elements - OrderedList: 2
1. [ ]

.
<ol>
  <li>[ ]</li>
</ol>
````````````````````````````````


nested

```````````````````````````````` example Markdown elements - OrderedList: 3
4. item 1
3. item 2
    2. item 2.1
1. item 3
.
<ol>
  <li>item 1</li>
  <li>item 2
    <ol>
      <li>item 2.1</li>
    </ol>
  </li>
  <li>item 3</li>
</ol>
````````````````````````````````


nested some loose

```````````````````````````````` example Markdown elements - OrderedList: 4
4. item 1

3. item 2
    2. item 2.1
1. item 3
.
<ol>
  <li>
    <p>item 1</p>
  </li>
  <li>
    <p>item 2</p>
    <ol>
      <li>item 2.1</li>
    </ol>
  </li>
  <li>item 3</li>
</ol>
````````````````````````````````


nested loose

```````````````````````````````` example Markdown elements - OrderedList: 5
4. item 1

3. item 2
    2. item 2.1
    
1. item 3
.
<ol>
  <li>
    <p>item 1</p>
  </li>
  <li>
    <p>item 2</p>
    <ol>
      <li>item 2.1</li>
    </ol>
  </li>
  <li>
    <p>item 3</p>
  </li>
</ol>
````````````````````````````````


tight nested loose

```````````````````````````````` example Markdown elements - OrderedList: 6
4. item 1
3. item 2

    2. item 2.1
    
    3. item 2.1
1. item 3
.
<ol>
  <li>item 1</li>
  <li>item 2
    <ol>
      <li>
        <p>item 2.1</p>
      </li>
      <li>
        <p>item 2.1</p>
      </li>
    </ol>
  </li>
  <li>item 3</li>
</ol>
````````````````````````````````


An ordered list after bullet list

```````````````````````````````` example Markdown elements - OrderedList: 7
- item 1
- item 2
- [ ] item 3

2. item 1
1. item 2
5. [ ] item 3
.
<ul>
  <li>item 1</li>
  <li>item 2</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 3</li>
</ul>
<ol>
  <li>item 1</li>
  <li>item 2</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;item 3</li>
</ol>
````````````````````````````````


no parens delimiter

```````````````````````````````` example Markdown elements - OrderedList: 8
2. item 1
1. item 2

3) item 3
.
<ol>
  <li>item 1</li>
  <li>item 2</li>
</ol>
<p>3) item 3</p>
````````````````````````````````


### Paragraph

`Paragraph` `Text` `TextBase`

Plain text should return the whole input

```````````````````````````````` example Markdown elements - Paragraph: 1
First line
Second line
Last line
.
<p>First line Second line Last line</p>
````````````````````````````````


Item text in a tight list should have no para wrapper.

```````````````````````````````` example Markdown elements - Paragraph: 2
* first item 
* first item 
.
<ul>
  <li>first item</li>
  <li>first item</li>
</ul>
````````````````````````````````


Paragraphs Following the item text should have paragraph wrappers

```````````````````````````````` example Markdown elements - Paragraph: 3
* first item 
    
    Para wrapped
    
    Para wrapped
* first item 
.
<ul>
  <li>first item
    <p>Para wrapped</p>
    <p>Para wrapped</p>
  </li>
  <li>first item</li>
</ul>
````````````````````````````````


### Reference

`Reference`

```````````````````````````````` example Markdown elements - Reference: 1
[url1]: /url1
[url2]: /url2
.
````````````````````````````````


Footnote looking references with footnotes disabled

```````````````````````````````` example Markdown elements - Reference: 2
[^url1]: /url1
[^url2]: /url2
.
````````````````````````````````


Footnote looking references with footnotes disabled

```````````````````````````````` example Markdown elements - Reference: 3
this is not a footnote[^]. And this is a footnote[^A].

this is an undefined footnote [^undef]

[^]: undefined
[^A]: footnote A

.
<p>this is not a footnote[^]. And this is a footnote<sup id="fnref-1"><a class="footnote-ref" href="#fn-1">1</a></sup>.</p>
<p>this is an undefined footnote [^undef]</p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>footnote A</p>
      <a href="#fnref-1" class="footnote-backref">&#8617;</a>
    </li>
  </ol>
</div>
````````````````````````````````


### SoftLineBreak

`SoftLineBreak`

```````````````````````````````` example Markdown elements - SoftLineBreak: 1
line 1
line 2
line 3
.
<p>line 1 line 2 line 3</p>
````````````````````````````````


### Strikethrough

`Strikethrough`

basic

```````````````````````````````` example Markdown elements - Strikethrough: 1
~italic~
text ~italic~ embedded 
~italic~ embedded 
text ~italic~ 
.
<p><sub>italic</sub> text <sub>italic</sub> embedded <sub>italic</sub> embedded text <sub>italic</sub></p>
````````````````````````````````


### StrongEmphasis

`StrongEmphasis`

basic

```````````````````````````````` example Markdown elements - StrongEmphasis: 1
**italic**
text **italic** embedded 
**italic** embedded 
text **italic** 
.
<p><strong>italic</strong> text <strong>italic</strong> embedded <strong>italic</strong> embedded text <strong>italic</strong></p>
````````````````````````````````


### TableBlock

`TableBlock` `TableBody` `TableCaption` `TableCell` `TableHead` `TableRow` `TableSeparator`

```````````````````````````````` example Markdown elements - TableBlock: 1
Abc|Def
---|---
1|2
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

```````````````````````````````` example Markdown elements - TableBlock: 2
|**Abc** **test** |_Def_ _Def_
---|---
[ref]|`code` `code`
table, you are over

[ref]: /url
.
<table>
  <thead>
    <tr><th><strong>Abc</strong> <strong>test</strong> </th><th><em>Def</em> <em>Def</em></th></tr>
  </thead>
  <tbody>
    <tr><td><a href="/url">ref</a></td><td><code>code</code> <code>code</code></td></tr>
  </tbody>
</table>
<p>table, you are over</p>
````````````````````````````````


Column spans are created with repeated | pipes one for each additional column to span

```````````````````````````````` example Markdown elements - TableBlock: 3
|Abc|Def
|---|---|
| span ||
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
  </thead>
  <tbody>
    <tr><td colspan="2"> span </td></tr>
  </tbody>
</table>
````````````````````````````````


Now we try varying the header lines and make sure we get the right output

```````````````````````````````` example Markdown elements - TableBlock: 4
|Abc|Def
|Hij|Lmn
|---|---|
| span ||
.
<table>
  <thead>
    <tr><th>Abc</th><th>Def</th></tr>
    <tr><th>Hij</th><th>Lmn</th></tr>
  </thead>
  <tbody>
    <tr><td colspan="2"> span </td></tr>
  </tbody>
</table>
````````````````````````````````


No header lines

```````````````````````````````` example Markdown elements - TableBlock: 5
|---|---|
| col1 | col2|
.
<table>
  <thead></thead>
  <tbody>
    <tr><td> col1 </td><td> col2</td></tr>
  </tbody>
</table>
````````````````````````````````


No body lines

```````````````````````````````` example Markdown elements - TableBlock: 6
| col1 | col2|
|---|---|
.
<table>
  <thead>
    <tr><th> col1 </th><th> col2</th></tr>
  </thead>
  <tbody></tbody>
</table>
````````````````````````````````


multiple tables parsed correctly

```````````````````````````````` example Markdown elements - TableBlock: 7
not a table, followed by a table

| col1 | col2|
|---|---|

| col1 | col2|
|---|---|
| data1 | data2|

not a table, followed by a table

| col11 | col12|
| col21 | col22|
|---|---|
| data1 | data2|

.
<p>not a table, followed by a table</p>
<table>
  <thead>
    <tr><th> col1 </th><th> col2</th></tr>
  </thead>
  <tbody></tbody>
</table>
<table>
  <thead>
    <tr><th> col1 </th><th> col2</th></tr>
  </thead>
  <tbody>
    <tr><td> data1 </td><td> data2</td></tr>
  </tbody>
</table>
<p>not a table, followed by a table</p>
<table>
  <thead>
    <tr><th> col11 </th><th> col12</th></tr>
    <tr><th> col21 </th><th> col22</th></tr>
  </thead>
  <tbody>
    <tr><td> data1 </td><td> data2</td></tr>
  </tbody>
</table>
````````````````````````````````


multi row/column

```````````````````````````````` example Markdown elements - TableBlock: 8
| col11 | col12| col13|
| col21 | col22| col23|
| col31 | col32| col33|
|---|---|---|
| data11 | data12| data13|
| data21 | data22| data23|
| data31 | data32| data33|

.
<table>
  <thead>
    <tr><th> col11 </th><th> col12</th><th> col13</th></tr>
    <tr><th> col21 </th><th> col22</th><th> col23</th></tr>
    <tr><th> col31 </th><th> col32</th><th> col33</th></tr>
  </thead>
  <tbody>
    <tr><td> data11 </td><td> data12</td><td> data13</td></tr>
    <tr><td> data21 </td><td> data22</td><td> data23</td></tr>
    <tr><td> data31 </td><td> data32</td><td> data33</td></tr>
  </tbody>
</table>
````````````````````````````````


real life table

```````````````````````````````` example Markdown elements - TableBlock: 9
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
    <tr><th align="left"> Feature                                                                                                                 </th><th align="center"> Basic </th><th align="center"> Enhanced </th></tr>
  </thead>
  <tbody>
    <tr><td align="left"> Works with builds 143.2370 or newer, product version IDEA 15.0.6                                                        </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> Preview Tab so you can see what the rendered markdown will look like on GitHub.                                         </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> Syntax highlighting                                                                                                     </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> Table syntax highlighting stripes rows and columns                                                                      </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> Support for Default and Darcula color schemes for preview tab                                                           </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> Warning and Error Annotations to help you validate wiki link errors                                                     </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> Link address completion for wiki links                                                                                  </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> Quick Fixes for detected wiki link errors                                                                               </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> GFM Task list extension <code>* [ ]</code> open task item and <code>* [x]</code> completed task item                                          </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> Line markers, Find usages, Go To Declaration for rapid navigation to wiki link targets                                  </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> Markdown extensions configuration to customize markdown dialects                                                        </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> GitHub wiki support makes maintaining GitHub wiki pages easier.                                                         </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> GitHub compatible id generation for headers so you can validate your anchor references                                  </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> Swing and JavaFX WebView based preview.                                                                                 </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> Supports <strong>JavaFX with JetBrains JRE on OS X</strong>                                                                          </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> Supports Highlight JS in WebView preview                                                                                </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> <strong>Multi-line Image URLs for embedding [gravizo.com] UML diagrams into markdown</strong>                                        </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> Live Templates for common markdown elements                                                                             </td><td align="center">   X   </td><td align="center">    X     </td></tr>
    <tr><td align="left"> <strong>Enhanced Version Benefits</strong>                                                                                           </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Split Editor with Preview or HTML Text modes to view both source and preview                    </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Toolbar for fast access to frequent operations                                                  </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Language Injections for fenced code, HTML, Jekyll front matter and multi-line URL content       </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Code completions, refactoring, annotations and quick fixes to let you work faster               </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Navigation support with Line markers, Find usages, Go To Declaration for rapid navigation       </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Inspections to help you validate links, anchor refs, footnote refs                              </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Complete GitHub wiki support for all links makes maintaining GitHub wiki pages a breeze         </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Jekyll front matter recognition in markdown documents                                           </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Emoji text to icon conversion using [Emoji Cheat Sheet] or GitHub emoji URLs                    </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Wrap on typing and table formatting with column alignment                                       </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Character display width used for wrapping and table formatting                                  </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Structure view for Abbreviations, Headers, Tables, Footnotes, References and Document           </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Document formatting with text wrapping, list renumbering, aranging of elements, etc.            </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Table of Contents generation for any markdown parser, with many style options                   </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left"> <strong>As you type automation</strong>                                                                                              </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Double of bold/emphasis markers and remove inserted ones if a space is typed                    </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Wrap text blocks to margins and indentation                                                     </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     ATX headers to match trailing <code>#</code> marker                                                        </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Setext headers to match marker length to text                                                   </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Format tables to pad column width, column alignment and spanning columns                        </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Auto insert empty table row on <kbd>ENTER</kbd>                                                 </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Auto delete empty table row/column on <kbd>BACKSPACE</kbd>                                      </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Auto insert table column when typing before first column or after last column of table          </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Actions to insert: table, row or column; delete: row or column                                  </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Auto insert list item on <kbd>ENTER</kbd>                                                       </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Auto delete empty list item on <kbd>ENTER</kbd>                                                 </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Auto delete empty list item on <kbd>BACKSPACE</kbd>                                             </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Indent or un-indent list item toolbar buttons and actions                                       </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left"> <strong>Code Completions</strong>                                                                                                    </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Absolute link address completions using <a href="https://">https://</a> and <a href="file://">file://</a> formats                            </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Explicit and Image links are GitHub wiki aware                                                  </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     GitHub Issue # Completions after <code>issues/</code> link address and in text                             </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     GitHub special links: Issues, Pull requests, Graphs, and Pulse.                                 </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Link address completions for non-markdown files                                                 </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Emoji text shortcuts completion                                                                 </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Java class, field and method completions in inline code elements                                </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left"> <strong>Intention Actions</strong>                                                                                                   </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Change between relative and absolute <a href="https://">https://</a> link addresses via intention action               </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Change between wiki links and explicit link                                                     </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Intentions for links, wiki links, references and headers                                        </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Intention to format Setext Header marker to match marker length to text                         </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Intention to swap Setext/Atx header format                                                      </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Update table of contents quick fix intention                                                    </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Intention to edit Table of Contents style options dialog with preview                           </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left"> <strong>Refactoring</strong>                                                                                                         </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Automatic change from wiki link to explicit link when link target file is moved out of the wiki </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     File move refactoring of contained links. This completes the refactoring feature set            </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Refactoring for /, <a href="https://">https://</a> and <a href="file://">file://</a> absolute link addresses to project files                </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Refactoring of header text with update to referencing anchor link references                    </td><td align="center">       </td><td align="center">    X     </td></tr>
    <tr><td align="left">     Anchor link reference refactoring with update to referenced header text                         </td><td align="center">       </td><td align="center">    X     </td></tr>
  </tbody>
</table>
````````````````````````````````


Multi-line code span

```````````````````````````````` example Markdown elements - TableBlock: 10
|header1|header2|header3|
|-------|----|------|
| line | ```{
"key1": "xxx",
"key2": [
"xxx",
"zzz"
]
} ```
| ```{
"key1": "xxx",
"key2": "xxx"
}```|
.
<table>
  <thead>
    <tr><th>header1</th><th>header2</th><th>header3</th></tr>
  </thead>
  <tbody>
    <tr><td> line </td><td> ```{</td></tr>
  </tbody>
</table>
<p>&ldquo;key1&rdquo;: &ldquo;xxx&rdquo;, &ldquo;key2&rdquo;: [ &ldquo;xxx&rdquo;, &ldquo;zzz&rdquo; ] } <code>|</code>{ &ldquo;key1&rdquo;: &ldquo;xxx&rdquo;, &ldquo;key2&rdquo;: &ldquo;xxx&rdquo; }```|</p>
.
Document[0, 149]
  TableBlock[0, 62]
    TableHead[0, 25]
      TableRow[0, 25] rowNumber=1
        TableCell[0, 9] header textOpen:[0, 1, "|"] text:[1, 8, "header1"] textClose:[8, 9, "|"]
          Text[1, 8] chars:[1, 8, "header1"]
        TableCell[9, 17] header text:[9, 16, "header2"] textClose:[16, 17, "|"]
          Text[9, 16] chars:[9, 16, "header2"]
        TableCell[17, 25] header text:[17, 24, "header3"] textClose:[24, 25, "|"]
          Text[17, 24] chars:[17, 24, "header3"]
    TableSeparator[26, 47]
      TableRow[26, 47]
        TableCell[26, 35] textOpen:[26, 27, "|"] text:[27, 34, "-------"] textClose:[34, 35, "|"]
          Text[27, 34] chars:[27, 34, "-------"]
        TableCell[35, 40] text:[35, 39, "----"] textClose:[39, 40, "|"]
          Text[35, 39] chars:[35, 39, "----"]
        TableCell[40, 47] text:[40, 46, "------"] textClose:[46, 47, "|"]
          Text[40, 46] chars:[40, 46, "------"]
    TableBody[48, 61]
      TableRow[48, 61] rowNumber=1
        TableCell[48, 56] textOpen:[48, 49, "|"] text:[49, 55, " line "] textClose:[55, 56, "|"]
          Text[49, 55] chars:[49, 55, " line "]
        TableCell[56, 61] text:[56, 61, " ```{"]
          Text[56, 61] chars:[56, 61, " ```{"]
  Paragraph[62, 149]
    TypographicQuotes[62, 68] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[62, 63, "\""] text:[63, 67, "key1"] textClose:[67, 68, "\""]
      Text[63, 67] chars:[63, 67, "key1"]
    Text[68, 70] chars:[68, 70, ": "]
    TypographicQuotes[70, 75] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[70, 71, "\""] text:[71, 74, "xxx"] textClose:[74, 75, "\""]
      Text[71, 74] chars:[71, 74, "xxx"]
    Text[75, 76] chars:[75, 76, ","]
    SoftLineBreak[76, 77]
    TypographicQuotes[77, 83] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[77, 78, "\""] text:[78, 82, "key2"] textClose:[82, 83, "\""]
      Text[78, 82] chars:[78, 82, "key2"]
    Text[83, 85] chars:[83, 85, ": "]
    LinkRef[85, 101] referenceOpen:[85, 86, "["] reference:[87, 99, "\"xxx\",\n\"zzz\""] referenceClose:[100, 101, "]"]
      SoftLineBreak[86, 87]
      TypographicQuotes[87, 92] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[87, 88, "\""] text:[88, 91, "xxx"] textClose:[91, 92, "\""]
        Text[88, 91] chars:[88, 91, "xxx"]
      Text[92, 93] chars:[92, 93, ","]
      SoftLineBreak[93, 94]
      TypographicQuotes[94, 99] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[94, 95, "\""] text:[95, 98, "zzz"] textClose:[98, 99, "\""]
        Text[95, 98] chars:[95, 98, "zzz"]
      SoftLineBreak[99, 100]
    SoftLineBreak[101, 102]
    Text[102, 104] chars:[102, 104, "} "]
    Code[104, 113] textOpen:[104, 107, "```"] text:[107, 110, "\n| "] textClose:[110, 113, "```"]
      Text[107, 110] chars:[107, 110, "\n| "]
    Text[113, 114] chars:[113, 114, "{"]
    SoftLineBreak[114, 115]
    TypographicQuotes[115, 121] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[115, 116, "\""] text:[116, 120, "key1"] textClose:[120, 121, "\""]
      Text[116, 120] chars:[116, 120, "key1"]
    Text[121, 123] chars:[121, 123, ": "]
    TypographicQuotes[123, 128] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[123, 124, "\""] text:[124, 127, "xxx"] textClose:[127, 128, "\""]
      Text[124, 127] chars:[124, 127, "xxx"]
    Text[128, 129] chars:[128, 129, ","]
    SoftLineBreak[129, 130]
    TypographicQuotes[130, 136] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[130, 131, "\""] text:[131, 135, "key2"] textClose:[135, 136, "\""]
      Text[131, 135] chars:[131, 135, "key2"]
    Text[136, 138] chars:[136, 138, ": "]
    TypographicQuotes[138, 143] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[138, 139, "\""] text:[139, 142, "xxx"] textClose:[142, 143, "\""]
      Text[139, 142] chars:[139, 142, "xxx"]
    SoftLineBreak[143, 144]
    Text[144, 149] chars:[144, 149, "}```|"]
````````````````````````````````


Multi-line code span with multi-line table rows

```````````````````````````````` example(Markdown elements - TableBlock: 11) options(FAIL)
|header1|header2|header3|
|-------|----|------|
| line | ```{
"key1": "xxx",
"key2": [
"xxx",
"zzz"
]
} ```
| ```{
"key1": "xxx",
"key2": "xxx"
}```|
.
<p>|header1|header2|header3| |-------|----|------| | line | <code>{
&quot;key1&quot;: &quot;xxx&quot;,
&quot;key2&quot;: [
&quot;xxx&quot;,
&quot;zzz&quot;
]
}</code> | <code>{
&quot;key1&quot;: &quot;xxx&quot;,
&quot;key2&quot;: &quot;xxx&quot;
}</code>|</p>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Multi-line code span in paragraph

```````````````````````````````` example Markdown elements - TableBlock: 12
line ```{
"key1": "xxx",
"key2": [
"xxx",
"zzz"
]
} ```
.
<p>line <code>{ &quot;key1&quot;: &quot;xxx&quot;, &quot;key2&quot;: [ &quot;xxx&quot;, &quot;zzz&quot; ] }</code></p>
.
Document[0, 55]
  Paragraph[0, 55]
    Text[0, 5] chars:[0, 5, "line "]
    Code[5, 55] textOpen:[5, 8, "```"] text:[8, 52, "{\n\"ke … y1\": \"xxx\",\n\"key2\": [\n\"xxx\",\n\"zzz\"\n]\n} "] textClose:[52, 55, "```"]
      Text[8, 52] chars:[8, 52, "{\n\"ke … \n]\n} "]
````````````````````````````````


### ThematicBreak

`ThematicBreak`

```````````````````````````````` example Markdown elements - ThematicBreak: 1
---
.
<hr />
````````````````````````````````


### TypographicQuotes

`TypographicQuotes`

basic quotes

```````````````````````````````` example Markdown elements - TypographicQuotes: 1
Sample "double" 'single' <<angle>> "l'ordre" 'l'ordre'
.
<p>Sample &ldquo;double&rdquo; &lsquo;single&rsquo; &laquo;angle&raquo; &ldquo;l&rsquo;ordre&rdquo; &lsquo;l&rsquo;ordre&rsquo;</p>
````````````````````````````````


escaped quotes

```````````````````````````````` example Markdown elements - TypographicQuotes: 2
Sample \"double\" \'single\' \<<angle\>> \"l\'ordre\" \'l\'ordre\'
.
<p>Sample &quot;double&quot; 'single' &lt;&lt;angle&gt;&gt; &quot;l'ordre&quot; 'l'ordre'</p>
````````````````````````````````


### TypographicSmarts

`TypographicSmarts`

basic

```````````````````````````````` example Markdown elements - TypographicSmarts: 1
Sample with l'existence, from 1...2 and so on. . . 

en--dash and em---dash
.
<p>Sample with l&rsquo;existence, from 1&hellip;2 and so on&hellip;</p>
<p>en&ndash;dash and em&mdash;dash</p>
````````````````````````````````


escaped smarts

```````````````````````````````` example Markdown elements - TypographicSmarts: 2
Sample with l\'existence, from 1\...2 and so on\. . . 

en\--dash and em\---dash
.
<p>Sample with l'existence, from 1...2 and so on. . .</p>
<p>en--dash and em-&ndash;dash</p>
````````````````````````````````


### WikiLink

`WikiLink`

no spaces between brackets

```````````````````````````````` example Markdown elements - WikiLink: 1
[ [not wiki link]]
.
<p>[ [not wiki link]]</p>
````````````````````````````````


no spaces between brackets

```````````````````````````````` example Markdown elements - WikiLink: 2
[[not wiki link] ]
.
<p>[[not wiki link] ]</p>
````````````````````````````````


simple wiki link

```````````````````````````````` example Markdown elements - WikiLink: 3
[[wiki link]]
.
<p><a href="wiki-link">wiki link</a></p>
````````````````````````````````


wiki link with text

```````````````````````````````` example Markdown elements - WikiLink: 4
[[wiki text|wiki link]]
.
<p><a href="wiki-link">wiki text</a></p>
````````````````````````````````


simple wiki link with ! before

```````````````````````````````` example Markdown elements - WikiLink: 5
![[wiki link]]
.
<p>!<a href="wiki-link">wiki link</a></p>
````````````````````````````````


wiki link with text with ! before

```````````````````````````````` example Markdown elements - WikiLink: 6
![[wiki text|wiki link]]
.
<p>!<a href="wiki-link">wiki text</a></p>
````````````````````````````````


reference following will be a reference, even if not defined

```````````````````````````````` example Markdown elements - WikiLink: 7
[[wiki link]][ref]
.
<p><a href="wiki-link">wiki link</a>[ref]</p>
````````````````````````````````


reference following will be a reference

```````````````````````````````` example Markdown elements - WikiLink: 8
[[wiki link]][ref]

[ref]: /url
.
<p><a href="wiki-link">wiki link</a><a href="/url">ref</a></p>
````````````````````````````````


dummy reference following will be an empty reference

```````````````````````````````` example Markdown elements - WikiLink: 9
[[wiki link]][]
.
<p><a href="wiki-link">wiki link</a>[]</p>
````````````````````````````````


reference inside is not a wiki link but a link ref with brackets around it

```````````````````````````````` example Markdown elements - WikiLink: 10
[[not wiki link][ref]]
.
<p>[[not wiki link][ref]]</p>
````````````````````````````````


dummy reference inside is not a wiki link but a link ref with brackets around it

```````````````````````````````` example Markdown elements - WikiLink: 11
[[not wiki link][]]
.
<p>[[not wiki link][]]</p>
````````````````````````````````


```````````````````````````````` example Markdown elements - WikiLink: 12
[[wiki link]] [^link][ref] [[^wiki link]]
.
<p><a href="wiki-link">wiki link</a> [^link][ref] <a href="%5Ewiki-link">^wiki link</a></p>
````````````````````````````````


Exclamation before is just text

```````````````````````````````` example Markdown elements - WikiLink: 13
![[wiki link]] [^link][ref] [[^wiki link]] [[wiki]][ref]
.
<p>!<a href="wiki-link">wiki link</a> [^link][ref] <a href="%5Ewiki-link">^wiki link</a> <a href="wiki">wiki</a>[ref]</p>
````````````````````````````````


With empty anchor ref

```````````````````````````````` example Markdown elements - WikiLink: 14
[[wiki link#]] 
.
<p><a href="wiki-link#">wiki link</a></p>
````````````````````````````````


With Anchor ref

```````````````````````````````` example Markdown elements - WikiLink: 15
[[wiki link#anchor-ref]] 
.
<p><a href="wiki-link#anchor-ref">wiki link</a></p>
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example Markdown elements - WikiLink: 16
[[wiki text|wiki link#]] 
.
<p><a href="wiki-link#">wiki text</a></p>
````````````````````````````````


With text, anchor ref

```````````````````````````````` example Markdown elements - WikiLink: 17
[[wiki text|wiki link#anchor-ref]] 
.
<p><a href="wiki-link#anchor-ref">wiki text</a></p>
````````````````````````````````


#### No Wiki Ref Anchors

With empty anchor ref

```````````````````````````````` example(Markdown elements - WikiLink - No Wiki Ref Anchors: 1) options(no-wiki-ref-anchors)
[[wiki link#]] 
.
<p><a href="wiki-link%23">wiki link#</a></p>
````````````````````````````````


With Anchor ref

```````````````````````````````` example(Markdown elements - WikiLink - No Wiki Ref Anchors: 2) options(no-wiki-ref-anchors)
[[wiki link#anchor-ref]] 
.
<p><a href="wiki-link%23anchor-ref">wiki link#anchor-ref</a></p>
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example(Markdown elements - WikiLink - No Wiki Ref Anchors: 3) options(no-wiki-ref-anchors)
[[wiki text|wiki link#]] 
.
<p><a href="wiki-link%23">wiki text</a></p>
````````````````````````````````


With text, anchor ref

```````````````````````````````` example(Markdown elements - WikiLink - No Wiki Ref Anchors: 4) options(no-wiki-ref-anchors)
[[wiki text|wiki link#anchor-ref]] 
.
<p><a href="wiki-link%23anchor-ref">wiki text</a></p>
````````````````````````````````


### Multi-Line Image URL

encoding of &, =

```````````````````````````````` example Markdown elements - Multi-Line Image URL: 1
![ref](/url1?
one = 1 & line
) trailing text
.
<p><img src="/url1?one%20=%201%20&amp;%20line%0A" alt="ref" /> trailing text</p>
````````````````````````````````


encoding of +

```````````````````````````````` example Markdown elements - Multi-Line Image URL: 2
![ref](/url1?
one = 1 + line
) trailing text
.
<p><img src="/url1?one%20=%201%20%2B%20line%0A" alt="ref" /> trailing text</p>
````````````````````````````````


### Extra Style Elements

```````````````````````````````` example Markdown elements - Extra Style Elements: 1
~subscript~ ^superscript^ ++inserted++
.
<p><sub>subscript</sub> <sup>superscript</sup> <ins>inserted</ins></p>
````````````````````````````````


### Gfm Task List Items

```````````````````````````````` example Markdown elements - Gfm Task List Items: 1
- [ ] task item
- [x] task item
- [x] task item
.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task item</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task item</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task item</li>
</ul>
````````````````````````````````


```````````````````````````````` example Markdown elements - Gfm Task List Items: 2
1. [ ] task item
1. [x] task item
1. [x] task item
.
<ol>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;task item</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task item</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;task item</li>
</ol>
````````````````````````````````


### Table Of Contents

Text and inlines, hierarchy

```````````````````````````````` example Markdown elements - Table Of Contents: 1
[TOC hierarchy] 

# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**

.
<ul>
  <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
    <ul>
      <li><a href="#heading-111">Heading 1.1.1</a></li>
      <li><a href="#heading-112-some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
    </ul>
  </li>
</ul>
<h1><a href="#heading-some-bold-1" id="heading-some-bold-1"></a>Heading <strong>some bold</strong> 1</h1>
<h2><a href="#heading-11-some-italic" id="heading-11-some-italic"></a>Heading 1.1 <em>some italic</em></h2>
<h3><a href="#heading-111" id="heading-111"></a>Heading 1.1.1</h3>
<h3><a href="#heading-112-some-bold-italic" id="heading-112-some-bold-italic"></a>Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
````````````````````````````````


Text only, sorted

```````````````````````````````` example Markdown elements - Table Of Contents: 2
[TOC text sorted] 

## Heading 1.1 _some italic_
# Heading **some bold** 1
### Heading 1.1.2  **_some bold italic_**
### Heading 1.1.1

.
<ul>
  <li><a href="#heading-11-some-italic">Heading 1.1 some italic</a>
    <ul>
      <li><a href="#heading-112-some-bold-italic">Heading 1.1.2  some bold italic</a></li>
      <li><a href="#heading-111">Heading 1.1.1</a></li>
    </ul>
  </li>
</ul>
<h2><a href="#heading-11-some-italic" id="heading-11-some-italic"></a>Heading 1.1 <em>some italic</em></h2>
<h1><a href="#heading-some-bold-1" id="heading-some-bold-1"></a>Heading <strong>some bold</strong> 1</h1>
<h3><a href="#heading-112-some-bold-italic" id="heading-112-some-bold-italic"></a>Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
<h3><a href="#heading-111" id="heading-111"></a>Heading 1.1.1</h3>
````````````````````````````````


Text only, reverse sorted

```````````````````````````````` example Markdown elements - Table Of Contents: 3
[TOC text decreasing] 

## Heading 1.1 _some italic_
# Heading **some bold** 1
### Heading 1.1.2  **_some bold italic_**
### Heading 1.1.1

.
<ul>
  <li><a href="#heading-112-some-bold-italic">Heading 1.1.2  some bold italic</a></li>
  <li><a href="#heading-111">Heading 1.1.1</a></li>
  <li><a href="#heading-11-some-italic">Heading 1.1 some italic</a></li>
</ul>
<h2><a href="#heading-11-some-italic" id="heading-11-some-italic"></a>Heading 1.1 <em>some italic</em></h2>
<h1><a href="#heading-some-bold-1" id="heading-some-bold-1"></a>Heading <strong>some bold</strong> 1</h1>
<h3><a href="#heading-112-some-bold-italic" id="heading-112-some-bold-italic"></a>Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
<h3><a href="#heading-111" id="heading-111"></a>Heading 1.1.1</h3>
````````````````````````````````


Text and inlines, sorted

```````````````````````````````` example Markdown elements - Table Of Contents: 4
[TOC increasing] 

### Heading 1.1.2  **_some bold italic_**
### Heading 1.1.1
## Heading 1.1 _some italic_
# Heading **some bold** 1

.
<ul>
  <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a></li>
  <li><a href="#heading-111">Heading 1.1.1</a></li>
  <li><a href="#heading-112-some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
</ul>
<h3><a href="#heading-112-some-bold-italic" id="heading-112-some-bold-italic"></a>Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
<h3><a href="#heading-111" id="heading-111"></a>Heading 1.1.1</h3>
<h2><a href="#heading-11-some-italic" id="heading-11-some-italic"></a>Heading 1.1 <em>some italic</em></h2>
<h1><a href="#heading-some-bold-1" id="heading-some-bold-1"></a>Heading <strong>some bold</strong> 1</h1>
````````````````````````````````


Text and inlines, unsorted

```````````````````````````````` example Markdown elements - Table Of Contents: 5
[TOC flat] 

### Heading 1.1.2  **_some bold italic_**
## Heading 1.1 _some italic_
### Heading 1.1.1
# Heading **some bold** 1

.
<ul>
  <li><a href="#heading-112-some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
  <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a></li>
  <li><a href="#heading-111">Heading 1.1.1</a></li>
</ul>
<h3><a href="#heading-112-some-bold-italic" id="heading-112-some-bold-italic"></a>Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
<h2><a href="#heading-11-some-italic" id="heading-11-some-italic"></a>Heading 1.1 <em>some italic</em></h2>
<h3><a href="#heading-111" id="heading-111"></a>Heading 1.1.1</h3>
<h1><a href="#heading-some-bold-1" id="heading-some-bold-1"></a>Heading <strong>some bold</strong> 1</h1>
````````````````````````````````


```````````````````````````````` example(Markdown elements - Table Of Contents: 6) options(hard-breaks)
paragraph with
hard breaks
option
.
<p>paragraph with<br />
hard breaks<br />
option</p>
````````````````````````````````


## Issues

### xxx

options for plain text rendering

```````````````````````````````` example Issues - xxx: 1
* Enable Auto Indent Lines after move line/selection up or down actions to have them indented
  automatically.
* Use Smart Paste to eliminate case change and prefix edits when pasting identifiers. MIA will
  match case and style of identifier at destination when you paste, undo to get results before
  MIA adjusted them. Copy `myColumnData` and paste it over `DEFAULT_VALUE` to get `COLUMN_DATA`,
  reverse the order and get `myDefaultValue`. Works when pasting at the **beginning**, **end**
  and **middle** of identifiers.

    Supports: **camelCase**, **PascalCase**, **snake_case**, **SCREAMING_SNAKE_CASE**,
    **dash-case**, **dot.case**, **slash/case**

    Default prefixes: `my`, `our`, `is`, `get`, `set` to allow pasting over member fields,
    static fields, getters and setters.
* Enable Auto Line Selections and select full lines without loosing time or column position by
  moving the caret to the start of line when selecting or pasting. **Choose** whether you want
  to **paste full line** selections: **above** or **below** the current line regardless of the
  caret's column.
.
<ul>
  <li>Enable Auto Indent Lines after move line/selection up or down actions to have them indented automatically.</li>
  <li>Use Smart Paste to eliminate case change and prefix edits when pasting identifiers. MIA will match case and style of identifier at destination when you paste, undo to get results before MIA adjusted them. Copy <code>myColumnData</code> and paste it over <code>DEFAULT_VALUE</code> to get <code>COLUMN_DATA</code>, reverse the order and get <code>myDefaultValue</code>. Works when pasting at the <strong>beginning</strong>, <strong>end</strong> and <strong>middle</strong> of identifiers.
    <p>Supports: <strong>camelCase</strong>, <strong>PascalCase</strong>, <strong>snake_case</strong>, <strong>SCREAMING_SNAKE_CASE</strong>, <strong>dash-case</strong>, <strong>dot.case</strong>, <strong>slash/case</strong></p>
    <p>Default prefixes: <code>my</code>, <code>our</code>, <code>is</code>, <code>get</code>, <code>set</code> to allow pasting over member fields, static fields, getters and setters.</p>
  </li>
  <li>Enable Auto Line Selections and select full lines without loosing time or column position by moving the caret to the start of line when selecting or pasting. <strong>Choose</strong> whether you want to <strong>paste full line</strong> selections: <strong>above</strong> or <strong>below</strong> the current line regardless of the caret&rsquo;s column.</li>
</ul>
.
Document[0, 1096]
  BulletList[0, 1096] isTight
    BulletListItem[0, 111] open:[0, 1, "*"] isTight
      Paragraph[2, 111]
        Text[2, 93] chars:[2, 93, "Enabl … ented"]
        SoftLineBreak[93, 94]
        Text[96, 110] chars:[96, 110, "autom … ally."]
    BulletListItem[111, 794] open:[111, 112, "*"] isTight hadBlankLineAfter
      Paragraph[113, 526] isTrailingBlankLine
        Text[113, 205] chars:[113, 205, "Use S …  will"]
        SoftLineBreak[205, 206]
        Text[208, 300] chars:[208, 300, "match … efore"]
        SoftLineBreak[300, 301]
        Text[303, 327] chars:[303, 327, "MIA a … Copy "]
        Code[327, 341] textOpen:[327, 328, "`"] text:[328, 340, "myCol … umnData"] textClose:[340, 341, "`"]
          Text[328, 340] chars:[328, 340, "myCol … nData"]
        Text[341, 360] chars:[341, 360, " and  … over "]
        Code[360, 375] textOpen:[360, 361, "`"] text:[361, 374, "DEFAU … LT_VALUE"] textClose:[374, 375, "`"]
          Text[361, 374] chars:[361, 374, "DEFAU … VALUE"]
        Text[375, 383] chars:[375, 383, " to get "]
        Code[383, 396] textOpen:[383, 384, "`"] text:[384, 395, "COLUM … N_DATA"] textClose:[395, 396, "`"]
          Text[384, 395] chars:[384, 395, "COLUM … _DATA"]
        Text[396, 397] chars:[396, 397, ","]
        SoftLineBreak[397, 398]
        Text[400, 426] chars:[400, 426, "rever …  get "]
        Code[426, 442] textOpen:[426, 427, "`"] text:[427, 441, "myDef … aultValue"] textClose:[441, 442, "`"]
          Text[427, 441] chars:[427, 441, "myDef … Value"]
        Text[442, 470] chars:[442, 470, ". Wor …  the "]
        StrongEmphasis[470, 483] textOpen:[470, 472, "**"] text:[472, 481, "beginning"] textClose:[481, 483, "**"]
          Text[472, 481] chars:[472, 481, "beginning"]
        Text[483, 485] chars:[483, 485, ", "]
        StrongEmphasis[485, 492] textOpen:[485, 487, "**"] text:[487, 490, "end"] textClose:[490, 492, "**"]
          Text[487, 490] chars:[487, 490, "end"]
        SoftLineBreak[492, 493]
        Text[495, 499] chars:[495, 499, "and "]
        StrongEmphasis[499, 509] textOpen:[499, 501, "**"] text:[501, 507, "middle"] textClose:[507, 509, "**"]
          Text[501, 507] chars:[501, 507, "middle"]
        Text[509, 525] chars:[509, 525, " of i … iers."]
      Paragraph[531, 662] isTrailingBlankLine
        Text[531, 541] chars:[531, 541, "Supports: "]
        StrongEmphasis[541, 554] textOpen:[541, 543, "**"] text:[543, 552, "camelCase"] textClose:[552, 554, "**"]
          Text[543, 552] chars:[543, 552, "camelCase"]
        Text[554, 556] chars:[554, 556, ", "]
        StrongEmphasis[556, 570] textOpen:[556, 558, "**"] text:[558, 568, "PascalCase"] textClose:[568, 570, "**"]
          Text[558, 568] chars:[558, 568, "PascalCase"]
        Text[570, 572] chars:[570, 572, ", "]
        StrongEmphasis[572, 586] textOpen:[572, 574, "**"] text:[574, 584, "snake_case"] textClose:[584, 586, "**"]
          Text[574, 584] chars:[574, 584, "snake_case"]
        Text[586, 588] chars:[586, 588, ", "]
        StrongEmphasis[588, 612] textOpen:[588, 590, "**"] text:[590, 610, "SCREAMING_SNAKE_CASE"] textClose:[610, 612, "**"]
          Text[590, 610] chars:[590, 610, "SCREA … _CASE"]
        Text[612, 613] chars:[612, 613, ","]
        SoftLineBreak[613, 614]
        StrongEmphasis[618, 631] textOpen:[618, 620, "**"] text:[620, 629, "dash-case"] textClose:[629, 631, "**"]
          Text[620, 629] chars:[620, 629, "dash-case"]
        Text[631, 633] chars:[631, 633, ", "]
        StrongEmphasis[633, 645] textOpen:[633, 635, "**"] text:[635, 643, "dot.case"] textClose:[643, 645, "**"]
          Text[635, 643] chars:[635, 643, "dot.case"]
        Text[645, 647] chars:[645, 647, ", "]
        StrongEmphasis[647, 661] textOpen:[647, 649, "**"] text:[649, 659, "slash/case"] textClose:[659, 661, "**"]
          Text[649, 659] chars:[649, 659, "slash/case"]
      Paragraph[667, 794]
        Text[667, 685] chars:[667, 685, "Defau … xes: "]
        Code[685, 689] textOpen:[685, 686, "`"] text:[686, 688, "my"] textClose:[688, 689, "`"]
          Text[686, 688] chars:[686, 688, "my"]
        Text[689, 691] chars:[689, 691, ", "]
        Code[691, 696] textOpen:[691, 692, "`"] text:[692, 695, "our"] textClose:[695, 696, "`"]
          Text[692, 695] chars:[692, 695, "our"]
        Text[696, 698] chars:[696, 698, ", "]
        Code[698, 702] textOpen:[698, 699, "`"] text:[699, 701, "is"] textClose:[701, 702, "`"]
          Text[699, 701] chars:[699, 701, "is"]
        Text[702, 704] chars:[702, 704, ", "]
        Code[704, 709] textOpen:[704, 705, "`"] text:[705, 708, "get"] textClose:[708, 709, "`"]
          Text[705, 708] chars:[705, 708, "get"]
        Text[709, 711] chars:[709, 711, ", "]
        Code[711, 716] textOpen:[711, 712, "`"] text:[712, 715, "set"] textClose:[715, 716, "`"]
          Text[712, 715] chars:[712, 715, "set"]
        Text[716, 753] chars:[716, 753, " to a … elds,"]
        SoftLineBreak[753, 754]
        Text[758, 793] chars:[758, 793, "stati … ters."]
    BulletListItem[794, 1096] open:[794, 795, "*"] isTight
      Paragraph[796, 1096]
        Text[796, 888] chars:[796, 888, "Enabl … on by"]
        SoftLineBreak[888, 889]
        Text[891, 956] chars:[891, 956, "movin … ing. "]
        StrongEmphasis[956, 966] textOpen:[956, 958, "**"] text:[958, 964, "Choose"] textClose:[964, 966, "**"]
          Text[958, 964] chars:[958, 964, "Choose"]
        Text[966, 983] chars:[966, 983, " whet …  want"]
        SoftLineBreak[983, 984]
        Text[986, 989] chars:[986, 989, "to "]
        StrongEmphasis[989, 1008] textOpen:[989, 991, "**"] text:[991, 1006, "paste full line"] textClose:[1006, 1008, "**"]
          Text[991, 1006] chars:[991, 1006, "paste …  line"]
        Text[1008, 1021] chars:[1008, 1021, " sele … ons: "]
        StrongEmphasis[1021, 1030] textOpen:[1021, 1023, "**"] text:[1023, 1028, "above"] textClose:[1028, 1030, "**"]
          Text[1023, 1028] chars:[1023, 1028, "above"]
        Text[1030, 1034] chars:[1030, 1034, " or "]
        StrongEmphasis[1034, 1043] textOpen:[1034, 1036, "**"] text:[1036, 1041, "below"] textClose:[1041, 1043, "**"]
          Text[1036, 1041] chars:[1036, 1041, "below"]
        Text[1043, 1078] chars:[1043, 1078, " the  … f the"]
        SoftLineBreak[1078, 1079]
        Text[1081, 1086] chars:[1081, 1086, "caret"]
        TypographicSmarts[1086, 1087] typographic: &rsquo; 
        Text[1087, 1096] chars:[1087, 1096, "s column."]
````````````````````````````````


### 70

Issue #70, parse failed for angle quotes if the end angle quote follows with a line feed or a
carriage return

```````````````````````````````` example(Issues - 70: 1) options(FILE_EOL)
<<test>>
.
<p>&laquo;test&raquo;</p>
.
Document[0, 9]
  Paragraph[0, 9]
    TypographicQuotes[0, 8] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[0, 2, "<<"] text:[2, 6, "test"] textClose:[6, 8, ">>"]
      Text[2, 6] chars:[2, 6, "test"]
````````````````````````````````


```````````````````````````````` example(Issues - 70: 2) options(NO_FILE_EOL)
<<test>>⏎
.
<p>&laquo;test&raquo;</p>
.
Document[0, 9]
  Paragraph[0, 9]
    TypographicQuotes[0, 8] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[0, 2, "<<"] text:[2, 6, "test"] textClose:[6, 8, ">>"]
      Text[2, 6] chars:[2, 6, "test"]
````````````````````````````````


```````````````````````````````` example(Issues - 70: 3) options(FILE_EOL)
<<test>>⏎
.
<p>&laquo;test&raquo;</p>
.
Document[0, 10]
  Paragraph[0, 10]
    TypographicQuotes[0, 8] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[0, 2, "<<"] text:[2, 6, "test"] textClose:[6, 8, ">>"]
      Text[2, 6] chars:[2, 6, "test"]
````````````````````````````````


```````````````````````````````` example(Issues - 70: 4) options(FILE_EOL)
<<test>>

.
<p>&laquo;test&raquo;</p>
.
Document[0, 10]
  Paragraph[0, 9] isTrailingBlankLine
    TypographicQuotes[0, 8] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[0, 2, "<<"] text:[2, 6, "test"] textClose:[6, 8, ">>"]
      Text[2, 6] chars:[2, 6, "test"]
````````````````````````````````


### 136

Issue #136, Tasklist display issue

```````````````````````````````` example(Issues - 136: 1) options(no-anchor-links)
Task List

- [x] Task 1
- [ ] Task 2
- [x] Task 3
.
<p>Task List</p>
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;Task 1</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;Task 2</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;Task 3</li>
</ul>
.
Document[0, 49]
  Paragraph[0, 10] isTrailingBlankLine
    Text[0, 9] chars:[0, 9, "Task List"]
  BulletList[11, 49] isTight
    TaskListItem[11, 24] open:[11, 12, "-"] openSuffix:[13, 16, "[x]"] isTight isDone
      Paragraph[17, 24]
        Text[17, 23] chars:[17, 23, "Task 1"]
    TaskListItem[24, 37] open:[24, 25, "-"] openSuffix:[26, 29, "[ ]"] isTight isNotDone
      Paragraph[30, 37]
        Text[30, 36] chars:[30, 36, "Task 2"]
    TaskListItem[37, 49] open:[37, 38, "-"] openSuffix:[39, 42, "[x]"] isTight isDone
      Paragraph[43, 49]
        Text[43, 49] chars:[43, 49, "Task 3"]
````````````````````````````````


```````````````````````````````` example(Issues - 136: 2) options(no-anchor-links)
# Task List
- [x] Task 1
- [ ] Task 2
- [x] Task 3
.
<h1>Task List</h1>
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;Task 1</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;Task 2</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" checked="checked" disabled="disabled" readonly="readonly" />&nbsp;Task 3</li>
</ul>
.
Document[0, 50]
  Heading[0, 11] textOpen:[0, 1, "#"] text:[2, 11, "Task List"]
    Text[2, 11] chars:[2, 11, "Task List"]
  BulletList[12, 50] isTight
    TaskListItem[12, 25] open:[12, 13, "-"] openSuffix:[14, 17, "[x]"] isTight isDone
      Paragraph[18, 25]
        Text[18, 24] chars:[18, 24, "Task 1"]
    TaskListItem[25, 38] open:[25, 26, "-"] openSuffix:[27, 30, "[ ]"] isTight isNotDone
      Paragraph[31, 38]
        Text[31, 37] chars:[31, 37, "Task 2"]
    TaskListItem[38, 50] open:[38, 39, "-"] openSuffix:[40, 43, "[x]"] isTight isDone
      Paragraph[44, 50]
        Text[44, 50] chars:[44, 50, "Task 3"]
````````````````````````````````


### 376

```````````````````````````````` example Issues - 376: 1
test1|test2|test3|test4|test5|test6
:------:|:------:|:------:|:------:|:------:|:------:|:------:
1*1 | 1*1|1 |3.8%|40.6|523|78
1*10|1*10|24 |82.7%|72.3|360|1134.1
.
<table>
  <thead>
    <tr><th align="center">test1</th><th align="center">test2</th><th align="center">test3</th><th align="center">test4</th><th align="center">test5</th><th align="center">test6</th></tr>
  </thead>
  <tbody>
    <tr><td align="center">1*1 </td><td align="center"> 1*1</td><td align="center">1 </td><td align="center">3.8%</td><td align="center">40.6</td><td align="center">523</td><td align="center">78</td></tr>
    <tr><td align="center">1*10</td><td align="center">1*10</td><td align="center">24 </td><td align="center">82.7%</td><td align="center">72.3</td><td align="center">360</td><td align="center">1134.1</td></tr>
  </tbody>
</table>
.
Document[0, 164]
  TableBlock[0, 164]
    TableHead[0, 35]
      TableRow[0, 35] rowNumber=1
        TableCell[0, 6] CENTER header text:[0, 5, "test1"] textClose:[5, 6, "|"]
          Text[0, 5] chars:[0, 5, "test1"]
        TableCell[6, 12] CENTER header text:[6, 11, "test2"] textClose:[11, 12, "|"]
          Text[6, 11] chars:[6, 11, "test2"]
        TableCell[12, 18] CENTER header text:[12, 17, "test3"] textClose:[17, 18, "|"]
          Text[12, 17] chars:[12, 17, "test3"]
        TableCell[18, 24] CENTER header text:[18, 23, "test4"] textClose:[23, 24, "|"]
          Text[18, 23] chars:[18, 23, "test4"]
        TableCell[24, 30] CENTER header text:[24, 29, "test5"] textClose:[29, 30, "|"]
          Text[24, 29] chars:[24, 29, "test5"]
        TableCell[30, 35] CENTER header text:[30, 35, "test6"]
          Text[30, 35] chars:[30, 35, "test6"]
    TableSeparator[36, 98]
      TableRow[36, 98]
        TableCell[36, 45] CENTER text:[36, 44, ":------:"] textClose:[44, 45, "|"]
          Text[36, 44] chars:[36, 44, ":------:"]
        TableCell[45, 54] CENTER text:[45, 53, ":------:"] textClose:[53, 54, "|"]
          Text[45, 53] chars:[45, 53, ":------:"]
        TableCell[54, 63] CENTER text:[54, 62, ":------:"] textClose:[62, 63, "|"]
          Text[54, 62] chars:[54, 62, ":------:"]
        TableCell[63, 72] CENTER text:[63, 71, ":------:"] textClose:[71, 72, "|"]
          Text[63, 71] chars:[63, 71, ":------:"]
        TableCell[72, 81] CENTER text:[72, 80, ":------:"] textClose:[80, 81, "|"]
          Text[72, 80] chars:[72, 80, ":------:"]
        TableCell[81, 90] CENTER text:[81, 89, ":------:"] textClose:[89, 90, "|"]
          Text[81, 89] chars:[81, 89, ":------:"]
        TableCell[90, 98] CENTER text:[90, 98, ":------:"]
          Text[90, 98] chars:[90, 98, ":------:"]
    TableBody[99, 164]
      TableRow[99, 128] rowNumber=1
        TableCell[99, 104] CENTER text:[99, 103, "1*1 "] textClose:[103, 104, "|"]
          Text[99, 103] chars:[99, 103, "1*1 "]
        TableCell[104, 109] CENTER text:[104, 108, " 1*1"] textClose:[108, 109, "|"]
          Text[104, 108] chars:[104, 108, " 1*1"]
        TableCell[109, 112] CENTER text:[109, 111, "1 "] textClose:[111, 112, "|"]
          Text[109, 111] chars:[109, 111, "1 "]
        TableCell[112, 117] CENTER text:[112, 116, "3.8%"] textClose:[116, 117, "|"]
          Text[112, 116] chars:[112, 116, "3.8%"]
        TableCell[117, 122] CENTER text:[117, 121, "40.6"] textClose:[121, 122, "|"]
          Text[117, 121] chars:[117, 121, "40.6"]
        TableCell[122, 126] CENTER text:[122, 125, "523"] textClose:[125, 126, "|"]
          Text[122, 125] chars:[122, 125, "523"]
        TableCell[126, 128] CENTER text:[126, 128, "78"]
          Text[126, 128] chars:[126, 128, "78"]
      TableRow[129, 164] rowNumber=2
        TableCell[129, 134] CENTER text:[129, 133, "1*10"] textClose:[133, 134, "|"]
          Text[129, 133] chars:[129, 133, "1*10"]
        TableCell[134, 139] CENTER text:[134, 138, "1*10"] textClose:[138, 139, "|"]
          Text[134, 138] chars:[134, 138, "1*10"]
        TableCell[139, 143] CENTER text:[139, 142, "24 "] textClose:[142, 143, "|"]
          Text[139, 142] chars:[139, 142, "24 "]
        TableCell[143, 149] CENTER text:[143, 148, "82.7%"] textClose:[148, 149, "|"]
          Text[143, 148] chars:[143, 148, "82.7%"]
        TableCell[149, 154] CENTER text:[149, 153, "72.3"] textClose:[153, 154, "|"]
          Text[149, 153] chars:[149, 153, "72.3"]
        TableCell[154, 158] CENTER text:[154, 157, "360"] textClose:[157, 158, "|"]
          Text[154, 157] chars:[154, 157, "360"]
        TableCell[158, 164] CENTER text:[158, 164, "1134.1"]
          Text[158, 164] chars:[158, 164, "1134.1"]
````````````````````````````````


### 382

Issue [#382, Is there an option for number of whitespaces needed to create sub-lists?]

```````````````````````````````` example Issues - 382: 1
* [ ] First item
* [ ] Second item


* [ ] Third item separated by 2 newlines

.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;First item</li>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;Second item</li>
  <li class="task-list-item">
    <p><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;Third item separated by 2 newlines</p>
  </li>
</ul>
````````````````````````````````


pegdown converts second item to lazy continuation because it is indented by more than 4 spaces

```````````````````````````````` example Issues - 382: 2
* [ ] First item
        * [ ] Second item

.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;First item * [ ] Second item</li>
</ul>
````````````````````````````````


```````````````````````````````` example Issues - 382: 3
* [ ] First item
            * [ ] Second item

.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;First item * [ ] Second item</li>
</ul>
````````````````````````````````


```````````````````````````````` example Issues - 382: 4
* [ ] First item

        * [ ] Second item

.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;First item
    <pre><code>* [ ] Second item
</code></pre>
  </li>
</ul>
````````````````````````````````


```````````````````````````````` example Issues - 382: 5
* [ ] First item

           * [ ] Second item

.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;First item
    <pre><code>   * [ ] Second item
</code></pre>
  </li>
</ul>
````````````````````````````````


```````````````````````````````` example Issues - 382: 6
* [ ] First item

            * [ ] Second item

.
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;First item
    <pre><code>    * [ ] Second item
</code></pre>
  </li>
</ul>
````````````````````````````````


## Issues-xxx

### 001

Issue markdown page generator

```````````````````````````````` example Issues-xxx - 001: 1
### hello `<html>` and `<body>`
.
<h3><a href="#hello-html-and-body" id="hello-html-and-body"></a>hello <code>&lt;html&gt;</code> and <code>&lt;body&gt;</code></h3>
.
Document[0, 31]
  Heading[0, 31] textOpen:[0, 3, "###"] text:[4, 31, "hello `<html>` and `<body>`"]
    AnchorLink[4, 4]
    Text[4, 10] chars:[4, 10, "hello "]
    Code[10, 18] textOpen:[10, 11, "`"] text:[11, 17, "<html>"] textClose:[17, 18, "`"]
      Text[11, 17] chars:[11, 17, "<html>"]
    Text[18, 23] chars:[18, 23, " and "]
    Code[23, 31] textOpen:[23, 24, "`"] text:[24, 30, "<body>"] textClose:[30, 31, "`"]
      Text[24, 30] chars:[24, 30, "<body>"]
````````````````````````````````


[#382, Is there an option for number of whitespaces needed to create sub-lists?]: https://github.com/vsch/flexmark-java/issues/382

