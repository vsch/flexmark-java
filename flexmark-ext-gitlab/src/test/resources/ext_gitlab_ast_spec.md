---
title: GitLab Extension Spec
author: Vladimir Schneider
version: 1.0
date: '2018-08-30'
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
<blockquote>
  <p>Block Quote Contents</p>
  <p>A paragraph</p>
</blockquote>
.
Document[0, 41]
  GitLabBlockQuote[0, 41] open:[0, 3, ">>>"] openTrail:[3, 4, "\n"] close:[38, 41, ">>>"] closeTrail:[41, 41]
    Paragraph[4, 25] isTrailingBlankLine
      Text[4, 24] chars:[4, 24, "Block … tents"]
    Paragraph[26, 38]
      Text[26, 37] chars:[26, 37, "A par … graph"]
````````````````````````````````


```````````````````````````````` example Block Quotes: 2
>>>
Block Quote Contents
>>>
.
<blockquote>
  <p>Block Quote Contents</p>
</blockquote>
.
Document[0, 28]
  GitLabBlockQuote[0, 28] open:[0, 3, ">>>"] openTrail:[3, 4, "\n"] close:[25, 28, ">>>"] closeTrail:[28, 28]
    Paragraph[4, 25]
      Text[4, 24] chars:[4, 24, "Block … tents"]
````````````````````````````````


```````````````````````````````` example(Block Quotes: 3) options(no-quotes)
>>>
Block Quote Contents
>>>
.
<blockquote>
  <blockquote>
    <blockquote>
    </blockquote>
  </blockquote>
</blockquote>
<p>Block Quote Contents</p>
<blockquote>
  <blockquote>
    <blockquote>
    </blockquote>
  </blockquote>
</blockquote>
.
Document[0, 28]
  BlockQuote[0, 4] marker:[0, 1, ">"]
    BlockQuote[0, 4] marker:[1, 2, ">"]
      BlockQuote[0, 4] marker:[2, 3, ">"]
  Paragraph[4, 25]
    Text[4, 24] chars:[4, 24, "Block … tents"]
  BlockQuote[25, 28] marker:[25, 26, ">"]
    BlockQuote[25, 28] marker:[26, 27, ">"]
      BlockQuote[25, 28] marker:[27, 28, ">"]
````````````````````````````````


nested quotes

```````````````````````````````` example Block Quotes: 4
>>>
Block Quote Contents
>>>
Nested Block Quote Contents
>>>
>>>
.
<blockquote>
  <p>Block Quote Contents</p>
</blockquote>
<p>Nested Block Quote Contents</p>
<blockquote>
</blockquote>
.
Document[0, 64]
  GitLabBlockQuote[0, 29] open:[0, 3, ">>>"] openTrail:[3, 4, "\n"] close:[25, 28, ">>>"] closeTrail:[28, 29, "\n"]
    Paragraph[4, 25]
      Text[4, 24] chars:[4, 24, "Block … tents"]
  Paragraph[29, 57]
    Text[29, 56] chars:[29, 56, "Neste … tents"]
  GitLabBlockQuote[57, 64] open:[57, 60, ">>>"] openTrail:[60, 61, "\n"] close:[61, 64, ">>>"] closeTrail:[64, 64]
````````````````````````````````


```````````````````````````````` example(Block Quotes: 5) options(no-nested-quotes)
>>>
Block Quote Contents
>>>
Nested Block Quote Contents
>>>
>>>
.
<blockquote>
  <p>Block Quote Contents</p>
</blockquote>
<p>Nested Block Quote Contents</p>
<blockquote>
</blockquote>
.
Document[0, 64]
  GitLabBlockQuote[0, 29] open:[0, 3, ">>>"] openTrail:[3, 4, "\n"] close:[25, 28, ">>>"] closeTrail:[28, 29, "\n"]
    Paragraph[4, 25]
      Text[4, 24] chars:[4, 24, "Block … tents"]
  Paragraph[29, 57]
    Text[29, 56] chars:[29, 56, "Neste … tents"]
  GitLabBlockQuote[57, 64] open:[57, 60, ">>>"] openTrail:[60, 61, "\n"] close:[61, 64, ">>>"] closeTrail:[64, 64]
````````````````````````````````


```````````````````````````````` example Block Quotes: 6
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
<blockquote>
  <p>Block Quote Contents</p>
</blockquote>
<p>Nested Block Quote Contents</p>
<blockquote>
  <p>Some more text</p>
</blockquote>
<p>Another Nested Block Quote Contents</p>
<blockquote>
</blockquote>
.
Document[0, 123]
  GitLabBlockQuote[0, 29] open:[0, 3, ">>>"] openTrail:[3, 4, "\n"] close:[25, 28, ">>>"] closeTrail:[28, 29, "\n"]
    Paragraph[4, 25]
      Text[4, 24] chars:[4, 24, "Block … tents"]
  Paragraph[29, 57]
    Text[29, 56] chars:[29, 56, "Neste … tents"]
  GitLabBlockQuote[57, 80] open:[57, 60, ">>>"] openTrail:[60, 61, "\n"] close:[76, 79, ">>>"] closeTrail:[79, 80, "\n"]
    Paragraph[61, 76]
      Text[61, 75] chars:[61, 75, "Some  …  text"]
  Paragraph[80, 116]
    Text[80, 115] chars:[80, 115, "Anoth … tents"]
  GitLabBlockQuote[116, 123] open:[116, 119, ">>>"] openTrail:[119, 120, "\n"] close:[120, 123, ">>>"] closeTrail:[123, 123]
````````````````````````````````


## Inline

unmatched

```````````````````````````````` example Inline: 1
{+inserted-}
.
<p>{+inserted-}</p>
.
Document[0, 12]
  Paragraph[0, 12]
    Text[0, 12] chars:[0, 12, "{+ins … ted-}"]
````````````````````````````````


```````````````````````````````` example Inline: 2
[+inserted-]
.
<p>[+inserted-]</p>
.
Document[0, 12]
  Paragraph[0, 12]
    Text[0, 12] chars:[0, 12, "[+ins … ted-]"]
````````````````````````````````


```````````````````````````````` example Inline: 3
{+inserted+]
.
<p>{+inserted+]</p>
.
Document[0, 12]
  Paragraph[0, 12]
    Text[0, 12] chars:[0, 12, "{+ins … ted+]"]
````````````````````````````````


```````````````````````````````` example Inline: 4
[+inserted+}
.
<p>[+inserted+}</p>
.
Document[0, 12]
  Paragraph[0, 12]
    Text[0, 12] chars:[0, 12, "[+ins … ted+}"]
````````````````````````````````


```````````````````````````````` example Inline: 5
{-deleted+}
.
<p>{-deleted+}</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 11] chars:[0, 11, "{-del … ted+}"]
````````````````````````````````


```````````````````````````````` example Inline: 6
[-deleted+]
.
<p>[-deleted+]</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 11] chars:[0, 11, "[-del … ted+]"]
````````````````````````````````


```````````````````````````````` example Inline: 7
{-deleted-]
.
<p>{-deleted-]</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 11] chars:[0, 11, "{-del … ted-]"]
````````````````````````````````


```````````````````````````````` example Inline: 8
[-deleted-}
.
<p>[-deleted-}</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 11] chars:[0, 11, "[-del … ted-}"]
````````````````````````````````


matched

```````````````````````````````` example Inline: 9
{+inserted+}
.
<p><ins>inserted</ins></p>
.
Document[0, 12]
  Paragraph[0, 12]
    GitLabIns[0, 12] textOpen:[0, 2, "{+"] text:[2, 10, "inserted"] textClose:[10, 12, "+}"]
      Text[2, 10] chars:[2, 10, "inserted"]
````````````````````````````````


```````````````````````````````` example Inline: 10
[+inserted+]
.
<p><ins>inserted</ins></p>
.
Document[0, 12]
  Paragraph[0, 12]
    GitLabIns[0, 12] textOpen:[0, 2, "[+"] text:[2, 10, "inserted"] textClose:[10, 12, "+]"]
      Text[2, 10] chars:[2, 10, "inserted"]
````````````````````````````````


```````````````````````````````` example Inline: 11
{-deleted-}
.
<p><del>deleted</del></p>
.
Document[0, 11]
  Paragraph[0, 11]
    GitLabDel[0, 11] textOpen:[0, 2, "{-"] text:[2, 9, "deleted"] textClose:[9, 11, "-}"]
      Text[2, 9] chars:[2, 9, "deleted"]
````````````````````````````````


```````````````````````````````` example Inline: 12
[-deleted-]
.
<p><del>deleted</del></p>
.
Document[0, 11]
  Paragraph[0, 11]
    GitLabDel[0, 11] textOpen:[0, 2, "[-"] text:[2, 9, "deleted"] textClose:[9, 11, "-]"]
      Text[2, 9] chars:[2, 9, "deleted"]
````````````````````````````````


disabled

```````````````````````````````` example(Inline: 13) options(no-ins)
{+inserted+}
[+inserted+]
{-deleted-}
[-deleted-]
.
<p>{+inserted+}
[+inserted+]
<del>deleted</del>
<del>deleted</del></p>
.
Document[0, 49]
  Paragraph[0, 49]
    Text[0, 12] chars:[0, 12, "{+ins … ted+}"]
    SoftLineBreak[12, 13]
    LinkRef[13, 25] referenceOpen:[13, 14, "["] reference:[14, 24, "+inserted+"] referenceClose:[24, 25, "]"]
      Text[14, 24] chars:[14, 24, "+inserted+"]
    SoftLineBreak[25, 26]
    GitLabDel[26, 37] textOpen:[26, 28, "{-"] text:[28, 35, "deleted"] textClose:[35, 37, "-}"]
      Text[28, 35] chars:[28, 35, "deleted"]
    SoftLineBreak[37, 38]
    GitLabDel[38, 49] textOpen:[38, 40, "[-"] text:[40, 47, "deleted"] textClose:[47, 49, "-]"]
      Text[40, 47] chars:[40, 47, "deleted"]
````````````````````````````````


```````````````````````````````` example(Inline: 14) options(no-del)
{+inserted+}
[+inserted+]
{-deleted-}
[-deleted-]
.
<p><ins>inserted</ins>
<ins>inserted</ins>
{-deleted-}
[-deleted-]</p>
.
Document[0, 49]
  Paragraph[0, 49]
    GitLabIns[0, 12] textOpen:[0, 2, "{+"] text:[2, 10, "inserted"] textClose:[10, 12, "+}"]
      Text[2, 10] chars:[2, 10, "inserted"]
    SoftLineBreak[12, 13]
    GitLabIns[13, 25] textOpen:[13, 15, "[+"] text:[15, 23, "inserted"] textClose:[23, 25, "+]"]
      Text[15, 23] chars:[15, 23, "inserted"]
    SoftLineBreak[25, 26]
    Text[26, 37] chars:[26, 37, "{-del … ted-}"]
    SoftLineBreak[37, 38]
    LinkRef[38, 49] referenceOpen:[38, 39, "["] reference:[39, 48, "-deleted-"] referenceClose:[48, 49, "]"]
      Text[39, 48] chars:[39, 48, "-deleted-"]
````````````````````````````````


```````````````````````````````` example(Inline: 15) options(no-ins, no-del)
{+inserted+}
[+inserted+]
{-deleted-}
[-deleted-]
.
<p>{+inserted+}
[+inserted+]
{-deleted-}
[-deleted-]</p>
.
Document[0, 49]
  Paragraph[0, 49]
    Text[0, 12] chars:[0, 12, "{+ins … ted+}"]
    SoftLineBreak[12, 13]
    LinkRef[13, 25] referenceOpen:[13, 14, "["] reference:[14, 24, "+inserted+"] referenceClose:[24, 25, "]"]
      Text[14, 24] chars:[14, 24, "+inserted+"]
    SoftLineBreak[25, 26]
    Text[26, 37] chars:[26, 37, "{-del … ted-}"]
    SoftLineBreak[37, 38]
    LinkRef[38, 49] referenceOpen:[38, 39, "["] reference:[39, 48, "-deleted-"] referenceClose:[48, 49, "]"]
      Text[39, 48] chars:[39, 48, "-deleted-"]
````````````````````````````````


nested

```````````````````````````````` example Inline: 16
[+inserted [-deleted-]+]
.
<p><ins>inserted <del>deleted</del></ins></p>
.
Document[0, 24]
  Paragraph[0, 24]
    GitLabIns[0, 24] textOpen:[0, 2, "[+"] text:[2, 22, "inserted [-deleted-]"] textClose:[22, 24, "+]"]
      Text[2, 11] chars:[2, 11, "inserted "]
      GitLabDel[11, 22] textOpen:[11, 13, "[-"] text:[13, 20, "deleted"] textClose:[20, 22, "-]"]
        Text[13, 20] chars:[13, 20, "deleted"]
````````````````````````````````


```````````````````````````````` example Inline: 17
*[+**inserted** [-deleted-]+]*
.
<p><em><ins><strong>inserted</strong> <del>deleted</del></ins></em></p>
.
Document[0, 30]
  Paragraph[0, 30]
    Emphasis[0, 30] textOpen:[0, 1, "*"] text:[1, 29, "[+**inserted** [-deleted-]+]"] textClose:[29, 30, "*"]
      GitLabIns[1, 29] textOpen:[1, 3, "[+"] text:[3, 27, "**inserted** [-deleted-]"] textClose:[27, 29, "+]"]
        StrongEmphasis[3, 15] textOpen:[3, 5, "**"] text:[5, 13, "inserted"] textClose:[13, 15, "**"]
          Text[5, 13] chars:[5, 13, "inserted"]
        Text[15, 16] chars:[15, 16, " "]
        GitLabDel[16, 27] textOpen:[16, 18, "[-"] text:[18, 25, "deleted"] textClose:[25, 27, "-]"]
          Text[18, 25] chars:[18, 25, "deleted"]
````````````````````````````````


## Inline Math

```````````````````````````````` example Inline Math: 1
$`a^2+b^2=c^2`$
.
<p><span class="katex">a^2+b^2=c^2</span></p>
.
Document[0, 15]
  Paragraph[0, 15]
    GitLabInlineMath[0, 15] textOpen:[0, 2, "$`"] text:[2, 13, "a^2+b^2=c^2"] textClose:[13, 15, "`$"]
````````````````````````````````


```````````````````````````````` example Inline Math: 2
Prefix $`a^2+b^2=c^2`$ suffix
.
<p>Prefix <span class="katex">a^2+b^2=c^2</span> suffix</p>
.
Document[0, 29]
  Paragraph[0, 29]
    Text[0, 7] chars:[0, 7, "Prefix "]
    GitLabInlineMath[7, 22] textOpen:[7, 9, "$`"] text:[9, 20, "a^2+b^2=c^2"] textClose:[20, 22, "`$"]
    Text[22, 29] chars:[22, 29, " suffix"]
````````````````````````````````


```````````````````````````````` example Inline Math: 3
prefix $`\Pr\left[\sum_{i=1}^k X_i > c \right] \leq 2^{-\Omega(c^2 k)}$$ $$\sum_{i=1}^\infty \frac{1}{2^i} = 1`$
.
<p>prefix <span class="katex">\Pr\left[\sum_{i=1}^k X_i &gt; c \right] \leq 2^{-\Omega(c^2 k)}$$ $$\sum_{i=1}^\infty \frac{1}{2^i} = 1</span></p>
.
Document[0, 112]
  Paragraph[0, 112]
    Text[0, 7] chars:[0, 7, "prefix "]
    GitLabInlineMath[7, 112] textOpen:[7, 9, "$`"] text:[9, 110, "\Pr\left[\sum_{i=1}^k X_i > c \right] \leq 2^{-\Omega(c^2 k)}$$ $$\sum_{i=1}^\infty \frac{1}{2^i} = 1"] textClose:[110, 112, "`$"]
````````````````````````````````


allow split lines

```````````````````````````````` example Inline Math: 4
prefix $`\Pr\left[\sum_{i=1}^k X_i > c \right] \leq 2^{-\Omega(c^2 k)}$$ 
$$\sum_{i=1}^\infty \frac{1}{2^i} = 1`$
.
<p>prefix <span class="katex">\Pr\left[\sum_{i=1}^k X_i &gt; c \right] \leq 2^{-\Omega(c^2 k)}$$ 
$$\sum_{i=1}^\infty \frac{1}{2^i} = 1</span></p>
.
Document[0, 113]
  Paragraph[0, 113]
    Text[0, 7] chars:[0, 7, "prefix "]
    GitLabInlineMath[7, 113] textOpen:[7, 9, "$`"] text:[9, 111, "\Pr\left[\sum_{i=1}^k X_i > c \right] \leq 2^{-\Omega(c^2 k)}$$ \n$$\sum_{i=1}^\infty \frac{1}{2^i} = 1"] textClose:[111, 113, "`$"]
````````````````````````````````


## Fenced Code Math

```````````````````````````````` example Fenced Code Math: 1
```math
a^2+b^2=c^2
```
.
<div class="katex">
a^2+b^2=c^2
</div>
.
Document[0, 23]
  FencedCodeBlock[0, 23] open:[0, 3, "```"] info:[3, 7, "math"] content:[8, 20] lines[1] close:[20, 23, "```"]
    Text[8, 20] chars:[8, 20, "a^2+b … =c^2\n"]
````````````````````````````````


```````````````````````````````` example(Fenced Code Math: 2) options(math-latex)
```math
a^2+b^2=c^2
```
.
<div class="katex">
a^2+b^2=c^2
</div>
.
Document[0, 23]
  FencedCodeBlock[0, 23] open:[0, 3, "```"] info:[3, 7, "math"] content:[8, 20] lines[1] close:[20, 23, "```"]
    Text[8, 20] chars:[8, 20, "a^2+b … =c^2\n"]
````````````````````````````````


```````````````````````````````` example(Fenced Code Math: 3) options(math-latex)
```latex
a^2+b^2=c^2
```
.
<div class="katex">
a^2+b^2=c^2
</div>
.
Document[0, 24]
  FencedCodeBlock[0, 24] open:[0, 3, "```"] info:[3, 8, "latex"] content:[9, 21] lines[1] close:[21, 24, "```"]
    Text[9, 21] chars:[9, 21, "a^2+b … =c^2\n"]
````````````````````````````````


```````````````````````````````` example(Fenced Code Math: 4) options(no-math)
```math
a^2+b^2=c^2
```
.
<pre><code class="language-math">a^2+b^2=c^2
</code></pre>
.
Document[0, 23]
  FencedCodeBlock[0, 23] open:[0, 3, "```"] info:[3, 7, "math"] content:[8, 20] lines[1] close:[20, 23, "```"]
    Text[8, 20] chars:[8, 20, "a^2+b … =c^2\n"]
````````````````````````````````


```````````````````````````````` example(Fenced Code Math: 5) options(math-class)
```math
a^2+b^2=c^2
```
.
<div class="math-class">
a^2+b^2=c^2
</div>
.
Document[0, 23]
  FencedCodeBlock[0, 23] open:[0, 3, "```"] info:[3, 7, "math"] content:[8, 20] lines[1] close:[20, 23, "```"]
    Text[8, 20] chars:[8, 20, "a^2+b … =c^2\n"]
````````````````````````````````


```````````````````````````````` example(Fenced Code Math: 6) options(math-class, math-latex)
```math
a^2+b^2=c^2
```
.
<div class="math-class">
a^2+b^2=c^2
</div>
.
Document[0, 23]
  FencedCodeBlock[0, 23] open:[0, 3, "```"] info:[3, 7, "math"] content:[8, 20] lines[1] close:[20, 23, "```"]
    Text[8, 20] chars:[8, 20, "a^2+b … =c^2\n"]
````````````````````````````````


```````````````````````````````` example(Fenced Code Math: 7) options(math-class, math-latex)
```latex
a^2+b^2=c^2
```
.
<div class="math-class">
a^2+b^2=c^2
</div>
.
Document[0, 24]
  FencedCodeBlock[0, 24] open:[0, 3, "```"] info:[3, 8, "latex"] content:[9, 21] lines[1] close:[21, 24, "```"]
    Text[9, 21] chars:[9, 21, "a^2+b … =c^2\n"]
````````````````````````````````


```````````````````````````````` example(Fenced Code Math: 8) options(block-delimiters)
```math-here
a^2+b^2=c^2
```
.
<div class="katex">
a^2+b^2=c^2
</div>
.
Document[0, 28]
  FencedCodeBlock[0, 28] open:[0, 3, "```"] info:[3, 12, "math-here"] content:[13, 25] lines[1] close:[25, 28, "```"]
    Text[13, 25] chars:[13, 25, "a^2+b … =c^2\n"]
````````````````````````````````


```````````````````````````````` example(Fenced Code Math: 9) options(code-content-block)
```math
a^2+b^2=c^2
```
.
<div class="katex">
a^2+b^2=c^2
</div>
.
Document[0, 23]
  FencedCodeBlock[0, 23] open:[0, 3, "```"] info:[3, 7, "math"] content:[8, 20] lines[1] close:[20, 23, "```"]
    CodeBlock[8, 20]
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
<div class="mermaid">
graph TD;
  A--&gt;B;
  A--&gt;C;
  B--&gt;D;
  C--&gt;D;
</div>
.
Document[0, 60]
  FencedCodeBlock[0, 60] open:[0, 3, "```"] info:[3, 10, "mermaid"] content:[11, 57] lines[5] close:[57, 60, "```"]
    Text[11, 57] chars:[11, 57, "graph … ->D;\n"]
````````````````````````````````


```````````````````````````````` example(Fenced Code Mermaid: 2) options(mermaid-alias)
```mermaid
graph TD;
  A-->B;
  A-->C;
  B-->D;
  C-->D;
```
.
<div class="mermaid">
graph TD;
  A--&gt;B;
  A--&gt;C;
  B--&gt;D;
  C--&gt;D;
</div>
.
Document[0, 60]
  FencedCodeBlock[0, 60] open:[0, 3, "```"] info:[3, 10, "mermaid"] content:[11, 57] lines[5] close:[57, 60, "```"]
    Text[11, 57] chars:[11, 57, "graph … ->D;\n"]
````````````````````````````````


```````````````````````````````` example(Fenced Code Mermaid: 3) options(mermaid-alias)
```alias
graph TD;
  A-->B;
  A-->C;
  B-->D;
  C-->D;
```
.
<div class="mermaid">
graph TD;
  A--&gt;B;
  A--&gt;C;
  B--&gt;D;
  C--&gt;D;
</div>
.
Document[0, 58]
  FencedCodeBlock[0, 58] open:[0, 3, "```"] info:[3, 8, "alias"] content:[9, 55] lines[5] close:[55, 58, "```"]
    Text[9, 55] chars:[9, 55, "graph … ->D;\n"]
````````````````````````````````


```````````````````````````````` example(Fenced Code Mermaid: 4) options(no-mermaid)
```mermaid
graph TD;
  A-->B;
  A-->C;
  B-->D;
  C-->D;
```
.
<pre><code class="language-mermaid">graph TD;
  A--&gt;B;
  A--&gt;C;
  B--&gt;D;
  C--&gt;D;
</code></pre>
.
Document[0, 60]
  FencedCodeBlock[0, 60] open:[0, 3, "```"] info:[3, 10, "mermaid"] content:[11, 57] lines[5] close:[57, 60, "```"]
    Text[11, 57] chars:[11, 57, "graph … ->D;\n"]
````````````````````````````````


```````````````````````````````` example(Fenced Code Mermaid: 5) options(mermaid-class)
```mermaid
graph TD;
  A-->B;
  A-->C;
  B-->D;
  C-->D;
```
.
<div class="mermaid-class">
graph TD;
  A--&gt;B;
  A--&gt;C;
  B--&gt;D;
  C--&gt;D;
</div>
.
Document[0, 60]
  FencedCodeBlock[0, 60] open:[0, 3, "```"] info:[3, 10, "mermaid"] content:[11, 57] lines[5] close:[57, 60, "```"]
    Text[11, 57] chars:[11, 57, "graph … ->D;\n"]
````````````````````````````````


```````````````````````````````` example(Fenced Code Mermaid: 6) options(mermaid-class, mermaid-alias)
```mermaid
graph TD;
  A-->B;
  A-->C;
  B-->D;
  C-->D;
```
.
<div class="mermaid-class">
graph TD;
  A--&gt;B;
  A--&gt;C;
  B--&gt;D;
  C--&gt;D;
</div>
.
Document[0, 60]
  FencedCodeBlock[0, 60] open:[0, 3, "```"] info:[3, 10, "mermaid"] content:[11, 57] lines[5] close:[57, 60, "```"]
    Text[11, 57] chars:[11, 57, "graph … ->D;\n"]
````````````````````````````````


```````````````````````````````` example(Fenced Code Mermaid: 7) options(mermaid-class, mermaid-alias)
```alias
graph TD;
  A-->B;
  A-->C;
  B-->D;
  C-->D;
```
.
<div class="mermaid-class">
graph TD;
  A--&gt;B;
  A--&gt;C;
  B--&gt;D;
  C--&gt;D;
</div>
.
Document[0, 58]
  FencedCodeBlock[0, 58] open:[0, 3, "```"] info:[3, 8, "alias"] content:[9, 55] lines[5] close:[55, 58, "```"]
    Text[9, 55] chars:[9, 55, "graph … ->D;\n"]
````````````````````````````````


```````````````````````````````` example(Fenced Code Mermaid: 8) options(block-delimiters)
```mermaid-here
graph TD;
  A-->B;
  A-->C;
  B-->D;
  C-->D;
```
.
<div class="mermaid">
graph TD;
  A--&gt;B;
  A--&gt;C;
  B--&gt;D;
  C--&gt;D;
</div>
.
Document[0, 65]
  FencedCodeBlock[0, 65] open:[0, 3, "```"] info:[3, 15, "mermaid-here"] content:[16, 62] lines[5] close:[62, 65, "```"]
    Text[16, 62] chars:[16, 62, "graph … ->D;\n"]
````````````````````````````````


```````````````````````````````` example(Fenced Code Mermaid: 9) options(code-content-block)
```mermaid
graph TD;
  A-->B;
  A-->C;
  B-->D;
  C-->D;
```
.
<div class="mermaid">
graph TD;
  A--&gt;B;
  A--&gt;C;
  B--&gt;D;
  C--&gt;D;
</div>
.
Document[0, 60]
  FencedCodeBlock[0, 60] open:[0, 3, "```"] info:[3, 10, "mermaid"] content:[11, 57] lines[5] close:[57, 60, "```"]
    CodeBlock[11, 57]
````````````````````````````````


## Video Images

```````````````````````````````` example Video Images: 1
![Video](video.mp4)
.
<p>
<div class="video-container">
  <video src="video.mp4" width="400" controls="true"></video>
  <p><a href="video.mp4" target="_blank" rel="noopener noreferrer" title="Download 'Video'">Video</a></p>
</div>
</p>
.
Document[0, 19]
  Paragraph[0, 19]
    Image[0, 19] textOpen:[0, 2, "!["] text:[2, 7, "Video"] textClose:[7, 8, "]"] linkOpen:[8, 9, "("] url:[9, 18, "video.mp4"] pageRef:[9, 18, "video.mp4"] linkClose:[18, 19, ")"]
      Text[2, 7] chars:[2, 7, "Video"]
````````````````````````````````


no video rendering

```````````````````````````````` example(Video Images: 2) options(no-video)
![Video](video.mp4)
.
<p><img src="video.mp4" alt="Video" /></p>
.
Document[0, 19]
  Paragraph[0, 19]
    Image[0, 19] textOpen:[0, 2, "!["] text:[2, 7, "Video"] textClose:[7, 8, "]"] linkOpen:[8, 9, "("] url:[9, 18, "video.mp4"] pageRef:[9, 18, "video.mp4"] linkClose:[18, 19, ")"]
      Text[2, 7] chars:[2, 7, "Video"]
````````````````````````````````


no video link

```````````````````````````````` example(Video Images: 3) options(no-video-link)
![Video](video.mp4)
.
<p>
<div class="video-container">
  <video src="video.mp4" width="400" controls="true"></video>
</div>
</p>
.
Document[0, 19]
  Paragraph[0, 19]
    Image[0, 19] textOpen:[0, 2, "!["] text:[2, 7, "Video"] textClose:[7, 8, "]"] linkOpen:[8, 9, "("] url:[9, 18, "video.mp4"] pageRef:[9, 18, "video.mp4"] linkClose:[18, 19, ")"]
      Text[2, 7] chars:[2, 7, "Video"]
````````````````````````````````


custom class

```````````````````````````````` example(Video Images: 4) options(video-class)
![Video](video.mp4)
.
<p>
<div class="video-class">
  <video src="video.mp4" width="400" controls="true"></video>
  <p><a href="video.mp4" target="_blank" rel="noopener noreferrer" title="Download 'Video'">Video</a></p>
</div>
</p>
.
Document[0, 19]
  Paragraph[0, 19]
    Image[0, 19] textOpen:[0, 2, "!["] text:[2, 7, "Video"] textClose:[7, 8, "]"] linkOpen:[8, 9, "("] url:[9, 18, "video.mp4"] pageRef:[9, 18, "video.mp4"] linkClose:[18, 19, ")"]
      Text[2, 7] chars:[2, 7, "Video"]
````````````````````````````````


custom extensions

```````````````````````````````` example(Video Images: 5) options(video-extensions)
![Video](video.tst)
.
<p>
<div class="video-container">
  <video src="video.tst" width="400" controls="true"></video>
  <p><a href="video.tst" target="_blank" rel="noopener noreferrer" title="Download 'Video'">Video</a></p>
</div>
</p>
.
Document[0, 19]
  Paragraph[0, 19]
    Image[0, 19] textOpen:[0, 2, "!["] text:[2, 7, "Video"] textClose:[7, 8, "]"] linkOpen:[8, 9, "("] url:[9, 18, "video.tst"] pageRef:[9, 18, "video.tst"] linkClose:[18, 19, ")"]
      Text[2, 7] chars:[2, 7, "Video"]
````````````````````````````````


```````````````````````````````` example(Video Images: 6) options(video-extensions)
![Video](video.mp4)
.
<p><img src="video.mp4" alt="Video" /></p>
.
Document[0, 19]
  Paragraph[0, 19]
    Image[0, 19] textOpen:[0, 2, "!["] text:[2, 7, "Video"] textClose:[7, 8, "]"] linkOpen:[8, 9, "("] url:[9, 18, "video.mp4"] pageRef:[9, 18, "video.mp4"] linkClose:[18, 19, ")"]
      Text[2, 7] chars:[2, 7, "Video"]
````````````````````````````````


custom link text

```````````````````````````````` example(Video Images: 7) options(video-link-format)
![Video](video.mp4)
.
<p>
<div class="video-container">
  <video src="video.mp4" width="400" controls="true"></video>
  <p><a href="video.mp4" target="_blank" rel="noopener noreferrer" title="Get Video 'Video'">Video</a></p>
</div>
</p>
.
Document[0, 19]
  Paragraph[0, 19]
    Image[0, 19] textOpen:[0, 2, "!["] text:[2, 7, "Video"] textClose:[7, 8, "]"] linkOpen:[8, 9, "("] url:[9, 18, "video.mp4"] pageRef:[9, 18, "video.mp4"] linkClose:[18, 19, ")"]
      Text[2, 7] chars:[2, 7, "Video"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
>>>
Block Quote Contents
>>>
Nested Block Quote Contents
>>>
>>>
.
<blockquote>
  <p md-pos="4-25">Block Quote Contents</p>
</blockquote>
<p md-pos="29-57">Nested Block Quote Contents</p>
<blockquote>
</blockquote>
.
Document[0, 64]
  GitLabBlockQuote[0, 29] open:[0, 3, ">>>"] openTrail:[3, 4, "\n"] close:[25, 28, ">>>"] closeTrail:[28, 29, "\n"]
    Paragraph[4, 25]
      Text[4, 24] chars:[4, 24, "Block … tents"]
  Paragraph[29, 57]
    Text[29, 56] chars:[29, 56, "Neste … tents"]
  GitLabBlockQuote[57, 64] open:[57, 60, ">>>"] openTrail:[60, 61, "\n"] close:[61, 64, ">>>"] closeTrail:[64, 64]
````````````````````````````````


## Issue MN-xxx

GitHub does not convert non-ascii heading text to lowercase while GitLab does

```````````````````````````````` example Issue MN-xxx: 1
## Тест Заголовок
.
<h2 id="тест-заголовок">Тест Заголовок</h2>
.
Document[0, 17]
  Heading[0, 17] textOpen:[0, 2, "##"] text:[3, 17, "Тест Заголовок"]
    Text[3, 17] chars:[3, 17, "Тест  … ловок"]
````````````````````````````````


GitHub does not convert non-ascii heading text to lowercase while GitLab does

```````````````````````````````` example Issue MN-xxx: 2
## Test Heading
.
<h2 id="test-heading">Test Heading</h2>
.
Document[0, 15]
  Heading[0, 15] textOpen:[0, 2, "##"] text:[3, 15, "Test Heading"]
    Text[3, 15] chars:[3, 15, "Test  … ading"]
````````````````````````````````


