---
title: GitLab Extension Formatter Spec
author: Vladimir Schneider
version: 1.0
date: '2018-09-07'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

# GitLab

Parses and renders GitLab Flavoured Markdown.

* Add: video link renderer to convert links to video files to embedded content. The valid video
  extensions are `.mp4`, `.m4v`, `.mov`, `.webm`, and `.ogv`.

  ```
  <div class="video-container">
  <video src="video.mp4" width="400" controls="true"></video>
  <p><a href="video.mp4" target="_blank" rel="noopener noreferrer" title="Download 'Sample Video'">Sample Video</a></p>
  </div>
  ```

* Multiline Block quote delimiters `>>>`

* Deleted text markers `{- -}` or `[- -]`

* Inserted text markers `{+ +}` or `[+ +]`

* Math, inline via ```$``$``` or as fenced code with `math` info string requiring inclusion of
  Katex in the rendered HTML page.

* Graphing via Mermaid as fenced code with `mermaid` info string, via Mermaid inclusion similar
  to Math solution above.

## Block Quotes

```````````````````````````````` example Block Quotes: 1
>>>
Block Quote Contents

A paragraph
>>>
.
>>>
Block Quote Contents

A paragraph
>>>
````````````````````````````````


```````````````````````````````` example Block Quotes: 2
>>>
Block Quote Contents
>>>
.
>>>
Block Quote Contents
>>>
````````````````````````````````


nested quotes

```````````````````````````````` example Block Quotes: 3
>>>
Block Quote Contents
>>>
Nested Block Quote Contents
>>>
>>>
.
>>>
Block Quote Contents
>>>

Nested Block Quote Contents

>>>
>>>
````````````````````````````````


```````````````````````````````` example Block Quotes: 4
>>>
Block Quote Contents
>>>
Nested Block Quote Contents
>>>
Some more text
>>>
Another Nested Block Quote Contents
>>>
>>>
.
>>>
Block Quote Contents
>>>

Nested Block Quote Contents

>>>
Some more text
>>>

Another Nested Block Quote Contents

>>>
>>>
````````````````````````````````


## Inline

matched

```````````````````````````````` example Inline: 1
{+inserted+}
.
{+inserted+}
````````````````````````````````


```````````````````````````````` example Inline: 2
[+inserted+]
.
[+inserted+]
````````````````````````````````


```````````````````````````````` example Inline: 3
{-deleted-}
.
{-deleted-}
````````````````````````````````


```````````````````````````````` example Inline: 4
[-deleted-]
.
[-deleted-]
````````````````````````````````


nested

```````````````````````````````` example Inline: 5
[+inserted [-deleted-]+]
.
[+inserted [-deleted-]+]
````````````````````````````````


```````````````````````````````` example Inline: 6
*[+**inserted** [-deleted-]+]*
.
*[+**inserted** [-deleted-]+]*
````````````````````````````````


## Inline Math

```````````````````````````````` example Inline Math: 1
$`a^2+b^2=c^2`$
.
$`a^2+b^2=c^2`$
````````````````````````````````


```````````````````````````````` example Inline Math: 2
Prefix $`a^2+b^2=c^2`$ suffix
.
Prefix $`a^2+b^2=c^2`$ suffix
````````````````````````````````


```````````````````````````````` example Inline Math: 3
prefix $`\Pr\left[\sum_{i=1}^k X_i > c \right] \leq 2^{-\Omega(c^2 k)}$$ $$\sum_{i=1}^\infty \frac{1}{2^i} = 1`$
.
prefix $`\Pr\left[\sum_{i=1}^k X_i > c \right] \leq 2^{-\Omega(c^2 k)}$$ $$\sum_{i=1}^\infty \frac{1}{2^i} = 1`$
````````````````````````````````


allow split lines

```````````````````````````````` example Inline Math: 4
prefix $`\Pr\left[\sum_{i=1}^k X_i > c \right] \leq 2^{-\Omega(c^2 k)}$$ 
$$\sum_{i=1}^\infty \frac{1}{2^i} = 1`$
.
prefix $`\Pr\left[\sum_{i=1}^k X_i > c \right] \leq 2^{-\Omega(c^2 k)}$$ 
$$\sum_{i=1}^\infty \frac{1}{2^i} = 1`$
````````````````````````````````


## Fenced Code Math

```````````````````````````````` example Fenced Code Math: 1
```math
a^2+b^2=c^2
```
.
```math
a^2+b^2=c^2
```

````````````````````````````````


## Fenced Code Mermaid

```````````````````````````````` example Fenced Code Mermaid: 1
```mermaid
graph TD;
  A-->B;
  A-->C;
  B-->D;
  C-->D;
```
.
```mermaid
graph TD;
  A-->B;
  A-->C;
  B-->D;
  C-->D;
```

````````````````````````````````


## Video Images

```````````````````````````````` example Video Images: 1
![Video](video.mp4)
.
![Video](video.mp4)
````````````````````````````````


