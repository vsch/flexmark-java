---
title: Core Extra Test Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Extra tests

Should be ignored

```````````````````````````````` example(Extra tests: 1) options(IGNORE)
```markdown
---
```
.
<pre><code class="language-markdown">---
</code></pre>
.
Document[0, 20]
  FencedCodeBlock[0, 19] open:[0, 3, "```"] info:[3, 11, "markdown"] content:[12, 16] lines[3] close:[16, 19, "```"]
````````````````````````````````


Should fail

```````````````````````````````` example(Extra tests: 2) options(FAIL)
```markdown
abc
```
.
<pre><code class="language-markdown">---
</code></pre>
.
Document[0, 20]
  FencedCodeBlock[0, 19] open:[0, 3, "```"] info:[3, 11, "markdown"] content:[12, 16] lines[3] close:[16, 19, "```"]
````````````````````````````````


Code fence starting with setext header marker

```````````````````````````````` example Extra tests: 3
```markdown
---
```
.
<pre><code class="language-markdown">---
</code></pre>
.
Document[0, 20]
  FencedCodeBlock[0, 19] open:[0, 3, "```"] info:[3, 11, "markdown"] content:[12, 16] lines[3] close:[16, 19, "```"]
````````````````````````````````


```````````````````````````````` example Extra tests: 4
```markdown
===
```
.
<pre><code class="language-markdown">===
</code></pre>
.
Document[0, 20]
  FencedCodeBlock[0, 19] open:[0, 3, "```"] info:[3, 11, "markdown"] content:[12, 16] lines[3] close:[16, 19, "```"]
````````````````````````````````


Make sure indentation is properly implemented

```````````````````````````````` example Extra tests: 5
> - item 1
> - item 2
>     1. item 1
>     2. item 2
.
<blockquote>
  <ul>
    <li>item 1</li>
    <li>item 2
      <ol>
        <li>item 1</li>
        <li>item 2</li>
      </ol>
    </li>
  </ul>
</blockquote>
.
Document[0, 54]
  BlockQuote[0, 54] marker:[0, 1, ">"]
    BulletList[2, 54] isTight=true
      BulletListItem[2, 11] open:[2, 3, "-"]
        Paragraph[4, 11]
          Text[4, 10] chars:[4, 10, "item 1"]
      BulletListItem[13, 54] open:[13, 14, "-"]
        Paragraph[15, 22]
          Text[15, 21] chars:[15, 21, "item 2"]
        OrderedList[28, 54] isTight=true
          OrderedListItem[28, 38] open:[28, 30, "1."]
            Paragraph[31, 38]
              Text[31, 37] chars:[31, 37, "item 1"]
          OrderedListItem[44, 54] open:[44, 46, "2."]
            Paragraph[47, 54]
              Text[47, 53] chars:[47, 53, "item 2"]
````````````````````````````````


Make sure indentation is properly implemented

```````````````````````````````` example Extra tests: 6
> - item 1
>
> - item 2
>
>     1. item 1
>
>     2. item 2
.
<blockquote>
  <ul>
    <li>
      <p>item 1</p>
    </li>
    <li>
      <p>item 2</p>
      <ol>
        <li>
          <p>item 1</p>
        </li>
        <li>
          <p>item 2</p>
        </li>
      </ol>
    </li>
  </ul>
</blockquote>
.
Document[0, 60]
  BlockQuote[0, 60] marker:[0, 1, ">"]
    BulletList[2, 60] isTight=false
      BulletListItem[2, 11] open:[2, 3, "-"]
        Paragraph[4, 11]
          Text[4, 10] chars:[4, 10, "item 1"]
      BulletListItem[15, 60] open:[15, 16, "-"]
        Paragraph[17, 24]
          Text[17, 23] chars:[17, 23, "item 2"]
        OrderedList[32, 60] isTight=false
          OrderedListItem[32, 42] open:[32, 34, "1."]
            Paragraph[35, 42]
              Text[35, 41] chars:[35, 41, "item 1"]
          OrderedListItem[50, 60] open:[50, 52, "2."]
            Paragraph[53, 60]
              Text[53, 59] chars:[53, 59, "item 2"]
````````````````````````````````


## Reference Repository Keep First tests

Test repository KEEP_FIRST behavior, meaning the first reference def is used

```````````````````````````````` example Reference Repository Keep First tests: 1
[ref]

[ref]: /url1
[ref]: /url2
[ref]: /url3
.
<p><a href="/url1">ref</a></p>
.
Document[0, 46]
  Paragraph[0, 6]
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "ref"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "ref"]
  Reference[7, 19] refOpen:[7, 8, "["] ref:[8, 11, "ref"] refClose:[11, 13, "]:"] url:[14, 19, "/url1"]
  Reference[20, 32] refOpen:[20, 21, "["] ref:[21, 24, "ref"] refClose:[24, 26, "]:"] url:[27, 32, "/url2"]
  Reference[33, 45] refOpen:[33, 34, "["] ref:[34, 37, "ref"] refClose:[37, 39, "]:"] url:[40, 45, "/url3"]
````````````````````````````````


## Reference Repository Keep Last tests

Test repository KEEP_LAST behavior, meaning the last reference def is used

```````````````````````````````` example(Reference Repository Keep Last tests: 1) options(keep-last)
[ref]

[ref]: /url1
[ref]: /url2
[ref]: /url3
.
<p><a href="/url3">ref</a></p>
.
Document[0, 46]
  Paragraph[0, 6]
    LinkRef[0, 5] referenceOpen:[0, 1, "["] reference:[1, 4, "ref"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "ref"]
  Reference[7, 19] refOpen:[7, 8, "["] ref:[8, 11, "ref"] refClose:[11, 13, "]:"] url:[14, 19, "/url1"]
  Reference[20, 32] refOpen:[20, 21, "["] ref:[21, 24, "ref"] refClose:[24, 26, "]:"] url:[27, 32, "/url2"]
  Reference[33, 45] refOpen:[33, 34, "["] ref:[34, 37, "ref"] refClose:[37, 39, "]:"] url:[40, 45, "/url3"]
````````````````````````````````


## ATX Header options

Allow atx headers without a space between # and the title

```````````````````````````````` example(ATX Header options: 1) options(hdr-no-atx-space)
#Heading
##Heading
###Heading
####Heading
#####Heading
######Heading

#Heading #
##Heading #
###Heading #
####Heading #
#####Heading #
######Heading #
.
<h1>Heading</h1>
<h2>Heading</h2>
<h3>Heading</h3>
<h4>Heading</h4>
<h5>Heading</h5>
<h6>Heading</h6>
<h1>Heading</h1>
<h2>Heading</h2>
<h3>Heading</h3>
<h4>Heading</h4>
<h5>Heading</h5>
<h6>Heading</h6>
.
Document[0, 151]
  Heading[0, 8] textOpen:[0, 1, "#"] text:[1, 8, "Heading"]
    Text[1, 8] chars:[1, 8, "Heading"]
  Heading[9, 18] textOpen:[9, 11, "##"] text:[11, 18, "Heading"]
    Text[11, 18] chars:[11, 18, "Heading"]
  Heading[19, 29] textOpen:[19, 22, "###"] text:[22, 29, "Heading"]
    Text[22, 29] chars:[22, 29, "Heading"]
  Heading[30, 41] textOpen:[30, 34, "####"] text:[34, 41, "Heading"]
    Text[34, 41] chars:[34, 41, "Heading"]
  Heading[42, 54] textOpen:[42, 47, "#####"] text:[47, 54, "Heading"]
    Text[47, 54] chars:[47, 54, "Heading"]
  Heading[55, 68] textOpen:[55, 61, "######"] text:[61, 68, "Heading"]
    Text[61, 68] chars:[61, 68, "Heading"]
  Heading[70, 80] textOpen:[70, 71, "#"] text:[71, 78, "Heading"] textClose:[79, 80, "#"]
    Text[71, 78] chars:[71, 78, "Heading"]
  Heading[81, 92] textOpen:[81, 83, "##"] text:[83, 90, "Heading"] textClose:[91, 92, "#"]
    Text[83, 90] chars:[83, 90, "Heading"]
  Heading[93, 105] textOpen:[93, 96, "###"] text:[96, 103, "Heading"] textClose:[104, 105, "#"]
    Text[96, 103] chars:[96, 103, "Heading"]
  Heading[106, 119] textOpen:[106, 110, "####"] text:[110, 117, "Heading"] textClose:[118, 119, "#"]
    Text[110, 117] chars:[110, 117, "Heading"]
  Heading[120, 134] textOpen:[120, 125, "#####"] text:[125, 132, "Heading"] textClose:[133, 134, "#"]
    Text[125, 132] chars:[125, 132, "Heading"]
  Heading[135, 150] textOpen:[135, 141, "######"] text:[141, 148, "Heading"] textClose:[149, 150, "#"]
    Text[141, 148] chars:[141, 148, "Heading"]
````````````````````````````````


Don't allow leading spaces

```````````````````````````````` example(ATX Header options: 2) options(hdr-no-lead-space)
 # Heading
 ## Heading
 ### Heading
 #### Heading
 ##### Heading
 ###### Heading
.
<p># Heading
## Heading
### Heading
#### Heading
##### Heading
###### Heading</p>
.
Document[0, 81]
  Paragraph[1, 81]
    Text[1, 10] chars:[1, 10, "# Heading"]
    SoftLineBreak[10, 11]
    Text[12, 22] chars:[12, 22, "## Heading"]
    SoftLineBreak[22, 23]
    Text[24, 35] chars:[24, 35, "### H … ading"]
    SoftLineBreak[35, 36]
    Text[37, 49] chars:[37, 49, "####  … ading"]
    SoftLineBreak[49, 50]
    Text[51, 64] chars:[51, 64, "##### … ading"]
    SoftLineBreak[64, 65]
    Text[66, 80] chars:[66, 80, "##### … ading"]
````````````````````````````````


Don't allow leading spaces

```````````````````````````````` example(ATX Header options: 3) options(hdr-no-lead-space, hdr-no-atx-space)
 # Heading
 ## Heading
 ### Heading
 #### Heading
 ##### Heading
 ###### Heading
.
<p># Heading
## Heading
### Heading
#### Heading
##### Heading
###### Heading</p>
.
Document[0, 81]
  Paragraph[1, 81]
    Text[1, 10] chars:[1, 10, "# Heading"]
    SoftLineBreak[10, 11]
    Text[12, 22] chars:[12, 22, "## Heading"]
    SoftLineBreak[22, 23]
    Text[24, 35] chars:[24, 35, "### H … ading"]
    SoftLineBreak[35, 36]
    Text[37, 49] chars:[37, 49, "####  … ading"]
    SoftLineBreak[49, 50]
    Text[51, 64] chars:[51, 64, "##### … ading"]
    SoftLineBreak[64, 65]
    Text[66, 80] chars:[66, 80, "##### … ading"]
````````````````````````````````


## List Options

### List - Fixed Indent

Options defined: list-fixed-indent, list-no-break, list-no-loose, list-no-start to change the
behavior of list parser and renderer

Default processing

```````````````````````````````` example List - Fixed Indent: 1
* item 1
 * item 2
  * item 3
   * item 4
    * sub item 1
     * sub item 2
      * sub item 3
       * sub item 4
        * sub sub item 1
.
<ul>
  <li>item 1</li>
  <li>item 2</li>
  <li>item 3</li>
  <li>item 4</li>
  <li>sub item 1</li>
  <li>sub item 2</li>
  <li>sub item 3</li>
  <li>sub item 4</li>
  <li>sub sub item 1</li>
</ul>
.
Document[0, 141]
  BulletList[0, 141] isTight=true
    BulletListItem[0, 9] open:[0, 1, "*"]
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[10, 19] open:[10, 11, "*"]
      Paragraph[12, 19]
        Text[12, 18] chars:[12, 18, "item 2"]
    BulletListItem[21, 30] open:[21, 22, "*"]
      Paragraph[23, 30]
        Text[23, 29] chars:[23, 29, "item 3"]
    BulletListItem[33, 42] open:[33, 34, "*"]
      Paragraph[35, 42]
        Text[35, 41] chars:[35, 41, "item 4"]
    BulletListItem[46, 59] open:[46, 47, "*"]
      Paragraph[48, 59]
        Text[48, 58] chars:[48, 58, "sub item 1"]
    BulletListItem[64, 77] open:[64, 65, "*"]
      Paragraph[66, 77]
        Text[66, 76] chars:[66, 76, "sub item 2"]
    BulletListItem[83, 96] open:[83, 84, "*"]
      Paragraph[85, 96]
        Text[85, 95] chars:[85, 95, "sub item 3"]
    BulletListItem[103, 116] open:[103, 104, "*"]
      Paragraph[105, 116]
        Text[105, 115] chars:[105, 115, "sub item 4"]
    BulletListItem[124, 141] open:[124, 125, "*"]
      Paragraph[126, 141]
        Text[126, 140] chars:[126, 140, "sub s … tem 1"]
````````````````````````````````


Fixed indentation only (4 spaces)

```````````````````````````````` example(List - Fixed Indent: 2) options(list-fixed-indent)
* item 1
 * item 2
  * item 3
   * item 4
    * sub item 1
     * sub item 2
      * sub item 3
       * sub item 4
        * sub sub item 1
.
<ul>
  <li>item 1</li>
  <li>item 2</li>
  <li>item 3</li>
  <li>item 4
    <ul>
      <li>sub item 1</li>
      <li>sub item 2</li>
      <li>sub item 3</li>
      <li>sub item 4
        <ul>
          <li>sub sub item 1</li>
        </ul>
      </li>
    </ul>
  </li>
</ul>
.
Document[0, 141]
  BulletList[0, 141] isTight=true
    BulletListItem[0, 9] open:[0, 1, "*"]
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[10, 19] open:[10, 11, "*"]
      Paragraph[12, 19]
        Text[12, 18] chars:[12, 18, "item 2"]
    BulletListItem[21, 30] open:[21, 22, "*"]
      Paragraph[23, 30]
        Text[23, 29] chars:[23, 29, "item 3"]
    BulletListItem[33, 141] open:[33, 34, "*"]
      Paragraph[35, 42]
        Text[35, 41] chars:[35, 41, "item 4"]
      BulletList[46, 141] isTight=true
        BulletListItem[46, 59] open:[46, 47, "*"]
          Paragraph[48, 59]
            Text[48, 58] chars:[48, 58, "sub item 1"]
        BulletListItem[64, 77] open:[64, 65, "*"]
          Paragraph[66, 77]
            Text[66, 76] chars:[66, 76, "sub item 2"]
        BulletListItem[83, 96] open:[83, 84, "*"]
          Paragraph[85, 96]
            Text[85, 95] chars:[85, 95, "sub item 3"]
        BulletListItem[103, 141] open:[103, 104, "*"]
          Paragraph[105, 116]
            Text[105, 115] chars:[105, 115, "sub item 4"]
          BulletList[124, 141] isTight=true
            BulletListItem[124, 141] open:[124, 125, "*"]
              Paragraph[126, 141]
                Text[126, 140] chars:[126, 140, "sub s … tem 1"]
````````````````````````````````


Fixed indentation with code blocks

```````````````````````````````` example(List - Fixed Indent: 3) options(list-fixed-indent)
* item 1
    
    this is not code
    
        this is code
 * item 2
    
    this is not code
    
        this is code
  * item 3
    
    this is not code
    
        this is code
   * item 4
    
    this is not code
    
        this is code
    * sub item 1
    
        this is not code
    
            this is code
     * sub item 2
    
        this is not code
    
            this is code
      * sub item 3
    
        this is not code
    
            this is code
       * sub item 4
    
        this is not code
    
            this is code
        * sub sub item 1
    
            this is not code
        
                this is code
.
<ul>
  <li>
    <p>item 1</p>
    <p>this is not code</p>
    <pre><code>this is code
</code></pre>
  </li>
  <li>
    <p>item 2</p>
    <p>this is not code</p>
    <pre><code>this is code
</code></pre>
  </li>
  <li>
    <p>item 3</p>
    <p>this is not code</p>
    <pre><code>this is code
</code></pre>
  </li>
  <li>
    <p>item 4</p>
    <p>this is not code</p>
    <pre><code>this is code
</code></pre>
    <ul>
      <li>
        <p>sub item 1</p>
        <p>this is not code</p>
        <pre><code>this is code
</code></pre>
      </li>
      <li>
        <p>sub item 2</p>
        <p>this is not code</p>
        <pre><code>this is code
</code></pre>
      </li>
      <li>
        <p>sub item 3</p>
        <p>this is not code</p>
        <pre><code>this is code
</code></pre>
      </li>
      <li>
        <p>sub item 4</p>
        <p>this is not code</p>
        <pre><code>this is code
</code></pre>
        <ul>
          <li>
            <p>sub sub item 1</p>
            <p>this is not code</p>
            <pre><code>this is code
</code></pre>
          </li>
        </ul>
      </li>
    </ul>
  </li>
</ul>
.
Document[0, 661]
  BulletList[0, 661] isTight=false
    BulletListItem[0, 61] open:[0, 1, "*"]
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      Paragraph[18, 35]
        Text[18, 34] chars:[18, 34, "this  …  code"]
      IndentedCodeBlock[48, 61]
    BulletListItem[62, 123] open:[62, 63, "*"]
      Paragraph[64, 71]
        Text[64, 70] chars:[64, 70, "item 2"]
      Paragraph[80, 97]
        Text[80, 96] chars:[80, 96, "this  …  code"]
      IndentedCodeBlock[110, 123]
    BulletListItem[125, 186] open:[125, 126, "*"]
      Paragraph[127, 134]
        Text[127, 133] chars:[127, 133, "item 3"]
      Paragraph[143, 160]
        Text[143, 159] chars:[143, 159, "this  …  code"]
      IndentedCodeBlock[173, 186]
    BulletListItem[189, 661] open:[189, 190, "*"]
      Paragraph[191, 198]
        Text[191, 197] chars:[191, 197, "item 4"]
      Paragraph[207, 224]
        Text[207, 223] chars:[207, 223, "this  …  code"]
      IndentedCodeBlock[237, 250]
      BulletList[254, 661] isTight=false
        BulletListItem[254, 327] open:[254, 255, "*"]
          Paragraph[256, 267]
            Text[256, 266] chars:[256, 266, "sub item 1"]
          Paragraph[280, 297]
            Text[280, 296] chars:[280, 296, "this  …  code"]
          IndentedCodeBlock[314, 327]
        BulletListItem[332, 405] open:[332, 333, "*"]
          Paragraph[334, 345]
            Text[334, 344] chars:[334, 344, "sub item 2"]
          Paragraph[358, 375]
            Text[358, 374] chars:[358, 374, "this  …  code"]
          IndentedCodeBlock[392, 405]
        BulletListItem[411, 484] open:[411, 412, "*"]
          Paragraph[413, 424]
            Text[413, 423] chars:[413, 423, "sub item 3"]
          Paragraph[437, 454]
            Text[437, 453] chars:[437, 453, "this  …  code"]
          IndentedCodeBlock[471, 484]
        BulletListItem[491, 661] open:[491, 492, "*"]
          Paragraph[493, 504]
            Text[493, 503] chars:[493, 503, "sub item 4"]
          Paragraph[517, 534]
            Text[517, 533] chars:[517, 533, "this  …  code"]
          IndentedCodeBlock[551, 564]
          BulletList[572, 661] isTight=false
            BulletListItem[572, 661] open:[572, 573, "*"]
              Paragraph[574, 589]
                Text[574, 588] chars:[574, 588, "sub s … tem 1"]
              Paragraph[606, 623]
                Text[606, 622] chars:[606, 622, "this  …  code"]
              IndentedCodeBlock[648, 661]
````````````````````````````````


### List - No Auto Loose

With auto loose setting for list

```````````````````````````````` example List - No Auto Loose: 1
* item 1
* item 2
* item 3
    * sub item 1
    
    * sub item 2
    
    * sub item 3
* item 4
.
<ul>
  <li>item 1</li>
  <li>item 2</li>
  <li>item 3
    <ul>
      <li>
        <p>sub item 1</p>
      </li>
      <li>
        <p>sub item 2</p>
      </li>
      <li>
        <p>sub item 3</p>
      </li>
    </ul>
  </li>
  <li>item 4</li>
</ul>
.
Document[0, 97]
  BulletList[0, 97] isTight=true
    BulletListItem[0, 9] open:[0, 1, "*"]
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 18] open:[9, 10, "*"]
      Paragraph[11, 18]
        Text[11, 17] chars:[11, 17, "item 2"]
    BulletListItem[18, 88] open:[18, 19, "*"]
      Paragraph[20, 27]
        Text[20, 26] chars:[20, 26, "item 3"]
      BulletList[31, 88] isTight=false
        BulletListItem[31, 44] open:[31, 32, "*"]
          Paragraph[33, 44]
            Text[33, 43] chars:[33, 43, "sub item 1"]
        BulletListItem[53, 66] open:[53, 54, "*"]
          Paragraph[55, 66]
            Text[55, 65] chars:[55, 65, "sub item 2"]
        BulletListItem[75, 88] open:[75, 76, "*"]
          Paragraph[77, 88]
            Text[77, 87] chars:[77, 87, "sub item 3"]
    BulletListItem[88, 97] open:[88, 89, "*"]
      Paragraph[90, 97]
        Text[90, 96] chars:[90, 96, "item 4"]
````````````````````````````````


Without auto loose setting for list

```````````````````````````````` example(List - No Auto Loose: 2) options(list-no-loose)
* item 1
* item 2
* item 3
    * sub item 1
    
    * sub item 2
    
    * sub item 3
* item 4
.
<ul>
  <li>item 1</li>
  <li>item 2</li>
  <li>item 3
    <ul>
      <li>
        <p>sub item 1</p>
      </li>
      <li>sub item 2</li>
      <li>sub item 3</li>
    </ul>
  </li>
  <li>item 4</li>
</ul>
.
Document[0, 97]
  BulletList[0, 97] isTight=true
    BulletListItem[0, 9] open:[0, 1, "*"]
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 18] open:[9, 10, "*"]
      Paragraph[11, 18]
        Text[11, 17] chars:[11, 17, "item 2"]
    BulletListItem[18, 88] open:[18, 19, "*"]
      Paragraph[20, 27]
        Text[20, 26] chars:[20, 26, "item 3"]
      BulletList[31, 88] isTight=true
        BulletListItem[31, 44] open:[31, 32, "*"]
          Paragraph[33, 44]
            Text[33, 43] chars:[33, 43, "sub item 1"]
        BulletListItem[53, 66] open:[53, 54, "*"]
          Paragraph[55, 66]
            Text[55, 65] chars:[55, 65, "sub item 2"]
        BulletListItem[75, 88] open:[75, 76, "*"]
          Paragraph[77, 88]
            Text[77, 87] chars:[77, 87, "sub item 3"]
    BulletListItem[88, 97] open:[88, 89, "*"]
      Paragraph[90, 97]
        Text[90, 96] chars:[90, 96, "item 4"]
````````````````````````````````


### List - No Break on Double Blank Line

With break all lists on two blank lines

```````````````````````````````` example List - No Break on Double Blank Line: 1
* item 1
* item 2
    * sub item 1
    * sub item 2
    
    
* item 4
* item 5
.
<ul>
  <li>item 1</li>
  <li>item 2
    <ul>
      <li>sub item 1</li>
      <li>sub item 2</li>
    </ul>
  </li>
</ul>
<ul>
  <li>item 4</li>
  <li>item 5</li>
</ul>
.
Document[0, 80]
  BulletList[0, 52] isTight=true
    BulletListItem[0, 9] open:[0, 1, "*"]
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 52] open:[9, 10, "*"]
      Paragraph[11, 18]
        Text[11, 17] chars:[11, 17, "item 2"]
      BulletList[22, 52] isTight=true
        BulletListItem[22, 35] open:[22, 23, "*"]
          Paragraph[24, 35]
            Text[24, 34] chars:[24, 34, "sub item 1"]
        BulletListItem[39, 52] open:[39, 40, "*"]
          Paragraph[41, 52]
            Text[41, 51] chars:[41, 51, "sub item 2"]
  BulletList[62, 80] isTight=true
    BulletListItem[62, 71] open:[62, 63, "*"]
      Paragraph[64, 71]
        Text[64, 70] chars:[64, 70, "item 4"]
    BulletListItem[71, 80] open:[71, 72, "*"]
      Paragraph[73, 80]
        Text[73, 79] chars:[73, 79, "item 5"]
````````````````````````````````


Without break all lists on two blank lines

```````````````````````````````` example(List - No Break on Double Blank Line: 2) options(list-no-break)
* item 1
* item 2
    * sub item 1
    * sub item 2
    
    
* item 4
* item 5
.
<ul>
  <li>
    <p>item 1</p>
  </li>
  <li>
    <p>item 2</p>
    <ul>
      <li>sub item 1</li>
      <li>sub item 2</li>
    </ul>
  </li>
  <li>
    <p>item 4</p>
  </li>
  <li>
    <p>item 5</p>
  </li>
</ul>
.
Document[0, 80]
  BulletList[0, 80] isTight=false
    BulletListItem[0, 9] open:[0, 1, "*"]
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 52] open:[9, 10, "*"]
      Paragraph[11, 18]
        Text[11, 17] chars:[11, 17, "item 2"]
      BulletList[22, 52] isTight=true
        BulletListItem[22, 35] open:[22, 23, "*"]
          Paragraph[24, 35]
            Text[24, 34] chars:[24, 34, "sub item 1"]
        BulletListItem[39, 52] open:[39, 40, "*"]
          Paragraph[41, 52]
            Text[41, 51] chars:[41, 51, "sub item 2"]
    BulletListItem[62, 71] open:[62, 63, "*"]
      Paragraph[64, 71]
        Text[64, 70] chars:[64, 70, "item 4"]
    BulletListItem[71, 80] open:[71, 72, "*"]
      Paragraph[73, 80]
        Text[73, 79] chars:[73, 79, "item 5"]
````````````````````````````````


Without break all lists on two blank lines no auto loose

```````````````````````````````` example(List - No Break on Double Blank Line: 3) options(list-no-loose, list-no-break)
* item 1
* item 2
    * sub item 1
    * sub item 2
    
    
* item 4
* item 5
.
<ul>
  <li>item 1</li>
  <li>
    <p>item 2</p>
    <ul>
      <li>sub item 1</li>
      <li>sub item 2</li>
    </ul>
  </li>
  <li>item 4</li>
  <li>item 5</li>
</ul>
.
Document[0, 80]
  BulletList[0, 80] isTight=true
    BulletListItem[0, 9] open:[0, 1, "*"]
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 52] open:[9, 10, "*"]
      Paragraph[11, 18]
        Text[11, 17] chars:[11, 17, "item 2"]
      BulletList[22, 52] isTight=true
        BulletListItem[22, 35] open:[22, 23, "*"]
          Paragraph[24, 35]
            Text[24, 34] chars:[24, 34, "sub item 1"]
        BulletListItem[39, 52] open:[39, 40, "*"]
          Paragraph[41, 52]
            Text[41, 51] chars:[41, 51, "sub item 2"]
    BulletListItem[62, 71] open:[62, 63, "*"]
      Paragraph[64, 71]
        Text[64, 70] chars:[64, 70, "item 4"]
    BulletListItem[71, 80] open:[71, 72, "*"]
      Paragraph[73, 80]
        Text[73, 79] chars:[73, 79, "item 5"]
````````````````````````````````


With break all lists on two blank lines

```````````````````````````````` example List - No Break on Double Blank Line: 4
* item 1
* item 2
    * sub item 1
    * sub item 2
    
    
    * sub item 3
    * sub item 4
* item 4
* item 5
.
<ul>
  <li>item 1</li>
  <li>item 2
    <ul>
      <li>sub item 1</li>
      <li>sub item 2</li>
    </ul>
  </li>
</ul>
<pre><code>* sub item 3
* sub item 4
</code></pre>
<ul>
  <li>item 4</li>
  <li>item 5</li>
</ul>
.
Document[0, 114]
  BulletList[0, 52] isTight=true
    BulletListItem[0, 9] open:[0, 1, "*"]
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 52] open:[9, 10, "*"]
      Paragraph[11, 18]
        Text[11, 17] chars:[11, 17, "item 2"]
      BulletList[22, 52] isTight=true
        BulletListItem[22, 35] open:[22, 23, "*"]
          Paragraph[24, 35]
            Text[24, 34] chars:[24, 34, "sub item 1"]
        BulletListItem[39, 52] open:[39, 40, "*"]
          Paragraph[41, 52]
            Text[41, 51] chars:[41, 51, "sub item 2"]
  IndentedCodeBlock[66, 96]
  BulletList[96, 114] isTight=true
    BulletListItem[96, 105] open:[96, 97, "*"]
      Paragraph[98, 105]
        Text[98, 104] chars:[98, 104, "item 4"]
    BulletListItem[105, 114] open:[105, 106, "*"]
      Paragraph[107, 114]
        Text[107, 113] chars:[107, 113, "item 5"]
````````````````````````````````


Without break on two blank lines

```````````````````````````````` example(List - No Break on Double Blank Line: 5) options(list-no-break)
* item 1
* item 2
    * sub item 1
    * sub item 2
    
    
    * sub item 3
    * sub item 4
* item 4
* item 5
.
<ul>
  <li>item 1</li>
  <li>item 2
    <ul>
      <li>
        <p>sub item 1</p>
      </li>
      <li>
        <p>sub item 2</p>
      </li>
      <li>
        <p>sub item 3</p>
      </li>
      <li>
        <p>sub item 4</p>
      </li>
    </ul>
  </li>
  <li>item 4</li>
  <li>item 5</li>
</ul>
.
Document[0, 114]
  BulletList[0, 114] isTight=true
    BulletListItem[0, 9] open:[0, 1, "*"]
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 96] open:[9, 10, "*"]
      Paragraph[11, 18]
        Text[11, 17] chars:[11, 17, "item 2"]
      BulletList[22, 96] isTight=false
        BulletListItem[22, 35] open:[22, 23, "*"]
          Paragraph[24, 35]
            Text[24, 34] chars:[24, 34, "sub item 1"]
        BulletListItem[39, 52] open:[39, 40, "*"]
          Paragraph[41, 52]
            Text[41, 51] chars:[41, 51, "sub item 2"]
        BulletListItem[66, 79] open:[66, 67, "*"]
          Paragraph[68, 79]
            Text[68, 78] chars:[68, 78, "sub item 3"]
        BulletListItem[83, 96] open:[83, 84, "*"]
          Paragraph[85, 96]
            Text[85, 95] chars:[85, 95, "sub item 4"]
    BulletListItem[96, 105] open:[96, 97, "*"]
      Paragraph[98, 105]
        Text[98, 104] chars:[98, 104, "item 4"]
    BulletListItem[105, 114] open:[105, 106, "*"]
      Paragraph[107, 114]
        Text[107, 113] chars:[107, 113, "item 5"]
````````````````````````````````


### List - No Bullet Match

With bullet matching for items within a list

```````````````````````````````` example List - No Bullet Match: 1
- item 1
* item 1
+ item 1
.
<ul>
  <li>item 1</li>
</ul>
<ul>
  <li>item 1</li>
</ul>
<ul>
  <li>item 1</li>
</ul>
.
Document[0, 27]
  BulletList[0, 9] isTight=true
    BulletListItem[0, 9] open:[0, 1, "-"]
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
  BulletList[9, 18] isTight=true
    BulletListItem[9, 18] open:[9, 10, "*"]
      Paragraph[11, 18]
        Text[11, 17] chars:[11, 17, "item 1"]
  BulletList[18, 27] isTight=true
    BulletListItem[18, 27] open:[18, 19, "+"]
      Paragraph[20, 27]
        Text[20, 26] chars:[20, 26, "item 1"]
````````````````````````````````


Without bullet matching for items within a list

```````````````````````````````` example(List - No Bullet Match: 2) options(list-no-bullet-match)
- item 1
* item 2
+ item 3
.
<ul>
  <li>item 1</li>
  <li>item 2</li>
  <li>item 3</li>
</ul>
.
Document[0, 27]
  BulletList[0, 27] isTight=true
    BulletListItem[0, 9] open:[0, 1, "-"]
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[9, 18] open:[9, 10, "*"]
      Paragraph[11, 18]
        Text[11, 17] chars:[11, 17, "item 2"]
    BulletListItem[18, 27] open:[18, 19, "+"]
      Paragraph[20, 27]
        Text[20, 26] chars:[20, 26, "item 3"]
````````````````````````````````


### List - No Manual Start Numbering

With start

```````````````````````````````` example List - No Manual Start Numbering: 1
2. item 1
1. item 2
3. item 3
.
<ol start="2">
  <li>item 1</li>
  <li>item 2</li>
  <li>item 3</li>
</ol>
.
Document[0, 30]
  OrderedList[0, 30] isTight=true
    OrderedListItem[0, 10] open:[0, 2, "2."]
      Paragraph[3, 10]
        Text[3, 9] chars:[3, 9, "item 1"]
    OrderedListItem[10, 20] open:[10, 12, "1."]
      Paragraph[13, 20]
        Text[13, 19] chars:[13, 19, "item 2"]
    OrderedListItem[20, 30] open:[20, 22, "3."]
      Paragraph[23, 30]
        Text[23, 29] chars:[23, 29, "item 3"]
````````````````````````````````


Without start

```````````````````````````````` example(List - No Manual Start Numbering: 2) options(list-no-start)
2. item 1
1. item 1
1. item 1
.
<ol>
  <li>item 1</li>
  <li>item 1</li>
  <li>item 1</li>
</ol>
.
Document[0, 30]
  OrderedList[0, 30] isTight=true
    OrderedListItem[0, 10] open:[0, 2, "2."]
      Paragraph[3, 10]
        Text[3, 9] chars:[3, 9, "item 1"]
    OrderedListItem[10, 20] open:[10, 12, "1."]
      Paragraph[13, 20]
        Text[13, 19] chars:[13, 19, "item 1"]
    OrderedListItem[20, 30] open:[20, 22, "1."]
      Paragraph[23, 30]
        Text[23, 29] chars:[23, 29, "item 1"]
````````````````````````````````


### List - Relaxed Item Start

With relaxed start. Lists can start without preceding blank lines.

```````````````````````````````` example List - Relaxed Item Start: 1
This is a paragraph
2. item 1
1. item 2

This is a paragraph 
- item 1
- item 2
.
<p>This is a paragraph</p>
<ol start="2">
  <li>item 1</li>
  <li>item 2</li>
</ol>
<p>This is a paragraph</p>
<ul>
  <li>item 1</li>
  <li>item 2</li>
</ul>
.
Document[0, 80]
  Paragraph[0, 20]
    Text[0, 19] chars:[0, 19, "This  … graph"]
  OrderedList[20, 40] isTight=true
    OrderedListItem[20, 30] open:[20, 22, "2."]
      Paragraph[23, 30]
        Text[23, 29] chars:[23, 29, "item 1"]
    OrderedListItem[30, 40] open:[30, 32, "1."]
      Paragraph[33, 40]
        Text[33, 39] chars:[33, 39, "item 2"]
  Paragraph[41, 62]
    Text[41, 60] chars:[41, 60, "This  … graph"]
  BulletList[62, 80] isTight=true
    BulletListItem[62, 71] open:[62, 63, "-"]
      Paragraph[64, 71]
        Text[64, 70] chars:[64, 70, "item 1"]
    BulletListItem[71, 80] open:[71, 72, "-"]
      Paragraph[73, 80]
        Text[73, 79] chars:[73, 79, "item 2"]
````````````````````````````````


Without relaxed start. Lists start only if preceded by a blank line.

```````````````````````````````` example(List - Relaxed Item Start: 2) options(list-no-relaxed-start)
This is a paragraph
2. item 1
1. item 2

This is a paragraph 
- item 1
- item 2

- this is a list
- item 1
- item 2
.
<p>This is a paragraph
2. item 1
1. item 2</p>
<p>This is a paragraph
- item 1
- item 2</p>
<ul>
  <li>this is a list</li>
  <li>item 1</li>
  <li>item 2</li>
</ul>
.
Document[0, 116]
  Paragraph[0, 40]
    Text[0, 19] chars:[0, 19, "This  … graph"]
    SoftLineBreak[19, 20]
    Text[20, 29] chars:[20, 29, "2. item 1"]
    SoftLineBreak[29, 30]
    Text[30, 39] chars:[30, 39, "1. item 2"]
  Paragraph[41, 80]
    Text[41, 60] chars:[41, 60, "This  … graph"]
    SoftLineBreak[61, 62]
    Text[62, 70] chars:[62, 70, "- item 1"]
    SoftLineBreak[70, 71]
    Text[71, 79] chars:[71, 79, "- item 2"]
  BulletList[81, 116] isTight=true
    BulletListItem[81, 98] open:[81, 82, "-"]
      Paragraph[83, 98]
        Text[83, 97] chars:[83, 97, "this  …  list"]
    BulletListItem[98, 107] open:[98, 99, "-"]
      Paragraph[100, 107]
        Text[100, 106] chars:[100, 106, "item 1"]
    BulletListItem[107, 116] open:[107, 108, "-"]
      Paragraph[109, 116]
        Text[109, 115] chars:[109, 115, "item 2"]
````````````````````````````````


Without start

```````````````````````````````` example(List - Relaxed Item Start: 3) options(list-no-start)
2. item 1
1. item 1
1. item 1
.
<ol>
  <li>item 1</li>
  <li>item 1</li>
  <li>item 1</li>
</ol>
.
Document[0, 30]
  OrderedList[0, 30] isTight=true
    OrderedListItem[0, 10] open:[0, 2, "2."]
      Paragraph[3, 10]
        Text[3, 9] chars:[3, 9, "item 1"]
    OrderedListItem[10, 20] open:[10, 12, "1."]
      Paragraph[13, 20]
        Text[13, 19] chars:[13, 19, "item 1"]
    OrderedListItem[20, 30] open:[20, 22, "1."]
      Paragraph[23, 30]
        Text[23, 29] chars:[23, 29, "item 1"]
````````````````````````````````


### List - Relaxed Item Start

With relaxed start. Lists can start without preceding blank lines.

```````````````````````````````` example List - Relaxed Item Start: 1
This is a paragraph
2. item 1
1. item 2

This is a paragraph 
- item 1
- item 2
.
<p>This is a paragraph</p>
<ol start="2">
  <li>item 1</li>
  <li>item 2</li>
</ol>
<p>This is a paragraph</p>
<ul>
  <li>item 1</li>
  <li>item 2</li>
</ul>
.
Document[0, 80]
  Paragraph[0, 20]
    Text[0, 19] chars:[0, 19, "This  … graph"]
  OrderedList[20, 40] isTight=true
    OrderedListItem[20, 30] open:[20, 22, "2."]
      Paragraph[23, 30]
        Text[23, 29] chars:[23, 29, "item 1"]
    OrderedListItem[30, 40] open:[30, 32, "1."]
      Paragraph[33, 40]
        Text[33, 39] chars:[33, 39, "item 2"]
  Paragraph[41, 62]
    Text[41, 60] chars:[41, 60, "This  … graph"]
  BulletList[62, 80] isTight=true
    BulletListItem[62, 71] open:[62, 63, "-"]
      Paragraph[64, 71]
        Text[64, 70] chars:[64, 70, "item 1"]
    BulletListItem[71, 80] open:[71, 72, "-"]
      Paragraph[73, 80]
        Text[73, 79] chars:[73, 79, "item 2"]
````````````````````````````````


Without relaxed start. Lists start only if preceded by a blank line.

```````````````````````````````` example(List - Relaxed Item Start: 2) options(list-no-relaxed-start)
This is a paragraph
2. item 1
1. item 2

This is a paragraph 
- item 1
- item 2

- this is a list
- item 1
- item 2
.
<p>This is a paragraph
2. item 1
1. item 2</p>
<p>This is a paragraph
- item 1
- item 2</p>
<ul>
  <li>this is a list</li>
  <li>item 1</li>
  <li>item 2</li>
</ul>
.
Document[0, 116]
  Paragraph[0, 40]
    Text[0, 19] chars:[0, 19, "This  … graph"]
    SoftLineBreak[19, 20]
    Text[20, 29] chars:[20, 29, "2. item 1"]
    SoftLineBreak[29, 30]
    Text[30, 39] chars:[30, 39, "1. item 2"]
  Paragraph[41, 80]
    Text[41, 60] chars:[41, 60, "This  … graph"]
    SoftLineBreak[61, 62]
    Text[62, 70] chars:[62, 70, "- item 1"]
    SoftLineBreak[70, 71]
    Text[71, 79] chars:[71, 79, "- item 2"]
  BulletList[81, 116] isTight=true
    BulletListItem[81, 98] open:[81, 82, "-"]
      Paragraph[83, 98]
        Text[83, 97] chars:[83, 97, "this  …  list"]
    BulletListItem[98, 107] open:[98, 99, "-"]
      Paragraph[100, 107]
        Text[100, 106] chars:[100, 106, "item 1"]
    BulletListItem[107, 116] open:[107, 108, "-"]
      Paragraph[109, 116]
        Text[109, 115] chars:[109, 115, "item 2"]
````````````````````````````````


### Thematic Break - No Relaxed Rules

With relaxed rules. Thematic break can occur without a preceding blank line. Applies to
non-dashed thematic break, dashes are a heading.

```````````````````````````````` example Thematic Break - No Relaxed Rules: 1
This is a paragraph
***
.
<p>This is a paragraph</p>
<hr />
.
Document[0, 24]
  Paragraph[0, 20]
    Text[0, 19] chars:[0, 19, "This  … graph"]
  ThematicBreak[20, 23]
````````````````````````````````


Without relaxed rules. Thematic break must be preceded by a blank line. Applies to non-dashed
thematic break, dashes are a heading.

```````````````````````````````` example(Thematic Break - No Relaxed Rules: 2) options(thematic-break-no-relaxed-start)
This is a paragraph
***
.
<p>This is a paragraph
***</p>
.
Document[0, 24]
  Paragraph[0, 24]
    Text[0, 19] chars:[0, 19, "This  … graph"]
    SoftLineBreak[19, 20]
    Text[20, 23] chars:[20, 23, "***"]
````````````````````````````````


### HTML Options

#### HTML Encode Options

Default pass it all through

```````````````````````````````` example HTML Encode Options: 1
<div>
<p>paragraph</p>
</div>

<!-- html comment block -->
<p>paragraph</p>

This is a paragraph with html <span style="color:red;">Test</span> and an html comment <!-- comment --> embedded in it.
.
<div>
<p>paragraph</p>
</div>
<!-- html comment block -->
<p>paragraph</p>
<p>This is a paragraph with html <span style="color:red;">Test</span> and an html comment <!-- comment --> embedded in it.</p>
.
Document[0, 197]
  HtmlBlock[0, 30]
  HtmlCommentBlock[31, 59]
  HtmlBlock[59, 76]
  Paragraph[77, 197]
    Text[77, 107] chars:[77, 107, "This  … html "]
    HtmlInline[107, 132] chars:[107, 132, "<span … ed;\">"]
    Text[132, 136] chars:[132, 136, "Test"]
    HtmlInline[136, 143] chars:[136, 143, "</span>"]
    Text[143, 164] chars:[143, 164, " and  … ment "]
    HtmlInlineComment[164, 180] chars:[164, 180, "<!--  … t -->"]
    Text[180, 196] chars:[180, 196, " embe … n it."]
````````````````````````````````


Encode all html

```````````````````````````````` example(HTML Encode Options: 2) options(escape-html)
<div>
<p>paragraph</p>
</div>

<!-- html comment block -->
<p>paragraph</p>

This is a paragraph with html <span style="color:red;">Test</span> and an html comment <!-- comment --> embedded in it.
.
&lt;div&gt;
&lt;p&gt;paragraph&lt;/p&gt;
&lt;/div&gt;
&lt;!-- html comment block --&gt;
&lt;p&gt;paragraph&lt;/p&gt;
<p>This is a paragraph with html &lt;span style=&quot;color:red;&quot;&gt;Test&lt;/span&gt; and an html comment &lt;!-- comment --&gt; embedded in it.</p>
.
Document[0, 197]
  HtmlBlock[0, 30]
  HtmlCommentBlock[31, 59]
  HtmlBlock[59, 76]
  Paragraph[77, 197]
    Text[77, 107] chars:[77, 107, "This  … html "]
    HtmlInline[107, 132] chars:[107, 132, "<span … ed;\">"]
    Text[132, 136] chars:[132, 136, "Test"]
    HtmlInline[136, 143] chars:[136, 143, "</span>"]
    Text[143, 164] chars:[143, 164, " and  … ment "]
    HtmlInlineComment[164, 180] chars:[164, 180, "<!--  … t -->"]
    Text[180, 196] chars:[180, 196, " embe … n it."]
````````````````````````````````


Encode html blocks

```````````````````````````````` example(HTML Encode Options: 3) options(escape-html-blocks)
<div>
<p>paragraph</p>
</div>

<!-- html comment block -->
<p>paragraph</p>

This is a paragraph with html <span style="color:red;">Test</span> and an html comment <!-- comment --> embedded in it.
.
&lt;div&gt;
&lt;p&gt;paragraph&lt;/p&gt;
&lt;/div&gt;
&lt;!-- html comment block --&gt;
&lt;p&gt;paragraph&lt;/p&gt;
<p>This is a paragraph with html <span style="color:red;">Test</span> and an html comment <!-- comment --> embedded in it.</p>
.
Document[0, 197]
  HtmlBlock[0, 30]
  HtmlCommentBlock[31, 59]
  HtmlBlock[59, 76]
  Paragraph[77, 197]
    Text[77, 107] chars:[77, 107, "This  … html "]
    HtmlInline[107, 132] chars:[107, 132, "<span … ed;\">"]
    Text[132, 136] chars:[132, 136, "Test"]
    HtmlInline[136, 143] chars:[136, 143, "</span>"]
    Text[143, 164] chars:[143, 164, " and  … ment "]
    HtmlInlineComment[164, 180] chars:[164, 180, "<!--  … t -->"]
    Text[180, 196] chars:[180, 196, " embe … n it."]
````````````````````````````````


Encode html block comments

```````````````````````````````` example(HTML Encode Options: 4) options(escape-html-comment-blocks)
<div>
<p>paragraph</p>
</div>

<!-- html comment block -->
<p>paragraph</p>

This is a paragraph with html <span style="color:red;">Test</span> and an html comment <!-- comment --> embedded in it.
.
<div>
<p>paragraph</p>
</div>
&lt;!-- html comment block --&gt;
<p>paragraph</p>
<p>This is a paragraph with html <span style="color:red;">Test</span> and an html comment <!-- comment --> embedded in it.</p>
.
Document[0, 197]
  HtmlBlock[0, 30]
  HtmlCommentBlock[31, 59]
  HtmlBlock[59, 76]
  Paragraph[77, 197]
    Text[77, 107] chars:[77, 107, "This  … html "]
    HtmlInline[107, 132] chars:[107, 132, "<span … ed;\">"]
    Text[132, 136] chars:[132, 136, "Test"]
    HtmlInline[136, 143] chars:[136, 143, "</span>"]
    Text[143, 164] chars:[143, 164, " and  … ment "]
    HtmlInlineComment[164, 180] chars:[164, 180, "<!--  … t -->"]
    Text[180, 196] chars:[180, 196, " embe … n it."]
````````````````````````````````


Encode inline html

```````````````````````````````` example(HTML Encode Options: 5) options(escape-inline-html)
<div>
<p>paragraph</p>
</div>

<!-- html comment block -->
<p>paragraph</p>

This is a paragraph with html <span style="color:red;">Test</span> and an html comment <!-- comment --> embedded in it.
.
<div>
<p>paragraph</p>
</div>
<!-- html comment block -->
<p>paragraph</p>
<p>This is a paragraph with html &lt;span style=&quot;color:red;&quot;&gt;Test&lt;/span&gt; and an html comment &lt;!-- comment --&gt; embedded in it.</p>
.
Document[0, 197]
  HtmlBlock[0, 30]
  HtmlCommentBlock[31, 59]
  HtmlBlock[59, 76]
  Paragraph[77, 197]
    Text[77, 107] chars:[77, 107, "This  … html "]
    HtmlInline[107, 132] chars:[107, 132, "<span … ed;\">"]
    Text[132, 136] chars:[132, 136, "Test"]
    HtmlInline[136, 143] chars:[136, 143, "</span>"]
    Text[143, 164] chars:[143, 164, " and  … ment "]
    HtmlInlineComment[164, 180] chars:[164, 180, "<!--  … t -->"]
    Text[180, 196] chars:[180, 196, " embe … n it."]
````````````````````````````````


Encode inline html comments

```````````````````````````````` example(HTML Encode Options: 6) options(escape-inline-html-comments)
<div>
<p>paragraph</p>
</div>

<!-- html comment block -->
<p>paragraph</p>

This is a paragraph with html <span style="color:red;">Test</span> and an html comment <!-- comment --> embedded in it.
.
<div>
<p>paragraph</p>
</div>
<!-- html comment block -->
<p>paragraph</p>
<p>This is a paragraph with html <span style="color:red;">Test</span> and an html comment &lt;!-- comment --&gt; embedded in it.</p>
.
Document[0, 197]
  HtmlBlock[0, 30]
  HtmlCommentBlock[31, 59]
  HtmlBlock[59, 76]
  Paragraph[77, 197]
    Text[77, 107] chars:[77, 107, "This  … html "]
    HtmlInline[107, 132] chars:[107, 132, "<span … ed;\">"]
    Text[132, 136] chars:[132, 136, "Test"]
    HtmlInline[136, 143] chars:[136, 143, "</span>"]
    Text[143, 164] chars:[143, 164, " and  … ment "]
    HtmlInlineComment[164, 180] chars:[164, 180, "<!--  … t -->"]
    Text[180, 196] chars:[180, 196, " embe … n it."]
````````````````````````````````


#### HTML Suppress Options

Suppress all html

```````````````````````````````` example(HTML Suppress Options: 1) options(suppress-html)
<div>
<p>paragraph</p>
</div>

<!-- html comment block -->
<p>paragraph</p>

This is a paragraph with html <span style="color:red;">Test</span> and an html comment <!-- comment --> embedded in it.
.
<p>This is a paragraph with html Test and an html comment  embedded in it.</p>
.
Document[0, 197]
  HtmlBlock[0, 30]
  HtmlCommentBlock[31, 59]
  HtmlBlock[59, 76]
  Paragraph[77, 197]
    Text[77, 107] chars:[77, 107, "This  … html "]
    HtmlInline[107, 132] chars:[107, 132, "<span … ed;\">"]
    Text[132, 136] chars:[132, 136, "Test"]
    HtmlInline[136, 143] chars:[136, 143, "</span>"]
    Text[143, 164] chars:[143, 164, " and  … ment "]
    HtmlInlineComment[164, 180] chars:[164, 180, "<!--  … t -->"]
    Text[180, 196] chars:[180, 196, " embe … n it."]
````````````````````````````````


Suppress html blocks

```````````````````````````````` example(HTML Suppress Options: 2) options(suppress-html-blocks)
<div>
<p>paragraph</p>
</div>

<!-- html comment block -->
<p>paragraph</p>

This is a paragraph with html <span style="color:red;">Test</span> and an html comment <!-- comment --> embedded in it.
.
<p>This is a paragraph with html <span style="color:red;">Test</span> and an html comment <!-- comment --> embedded in it.</p>
.
Document[0, 197]
  HtmlBlock[0, 30]
  HtmlCommentBlock[31, 59]
  HtmlBlock[59, 76]
  Paragraph[77, 197]
    Text[77, 107] chars:[77, 107, "This  … html "]
    HtmlInline[107, 132] chars:[107, 132, "<span … ed;\">"]
    Text[132, 136] chars:[132, 136, "Test"]
    HtmlInline[136, 143] chars:[136, 143, "</span>"]
    Text[143, 164] chars:[143, 164, " and  … ment "]
    HtmlInlineComment[164, 180] chars:[164, 180, "<!--  … t -->"]
    Text[180, 196] chars:[180, 196, " embe … n it."]
````````````````````````````````


Suppress html comment blocks

```````````````````````````````` example(HTML Suppress Options: 3) options(suppress-html-comment-blocks)
<div>
<p>paragraph</p>
</div>

<!-- html comment block -->
<p>paragraph</p>

This is a paragraph with html <span style="color:red;">Test</span> and an html comment <!-- comment --> embedded in it.
.
<div>
<p>paragraph</p>
</div>
<p>paragraph</p>
<p>This is a paragraph with html <span style="color:red;">Test</span> and an html comment <!-- comment --> embedded in it.</p>
.
Document[0, 197]
  HtmlBlock[0, 30]
  HtmlCommentBlock[31, 59]
  HtmlBlock[59, 76]
  Paragraph[77, 197]
    Text[77, 107] chars:[77, 107, "This  … html "]
    HtmlInline[107, 132] chars:[107, 132, "<span … ed;\">"]
    Text[132, 136] chars:[132, 136, "Test"]
    HtmlInline[136, 143] chars:[136, 143, "</span>"]
    Text[143, 164] chars:[143, 164, " and  … ment "]
    HtmlInlineComment[164, 180] chars:[164, 180, "<!--  … t -->"]
    Text[180, 196] chars:[180, 196, " embe … n it."]
````````````````````````````````


Suppress inline html

```````````````````````````````` example(HTML Suppress Options: 4) options(suppress-inline-html)
<div>
<p>paragraph</p>
</div>

<!-- html comment block -->
<p>paragraph</p>

This is a paragraph with html <span style="color:red;">Test</span> and an html comment <!-- comment --> embedded in it.
.
<div>
<p>paragraph</p>
</div>
<!-- html comment block -->
<p>paragraph</p>
<p>This is a paragraph with html Test and an html comment  embedded in it.</p>
.
Document[0, 197]
  HtmlBlock[0, 30]
  HtmlCommentBlock[31, 59]
  HtmlBlock[59, 76]
  Paragraph[77, 197]
    Text[77, 107] chars:[77, 107, "This  … html "]
    HtmlInline[107, 132] chars:[107, 132, "<span … ed;\">"]
    Text[132, 136] chars:[132, 136, "Test"]
    HtmlInline[136, 143] chars:[136, 143, "</span>"]
    Text[143, 164] chars:[143, 164, " and  … ment "]
    HtmlInlineComment[164, 180] chars:[164, 180, "<!--  … t -->"]
    Text[180, 196] chars:[180, 196, " embe … n it."]
````````````````````````````````


Suppress inline html comments

```````````````````````````````` example(HTML Suppress Options: 5) options(suppress-inline-html-comments)
<div>
<p>paragraph</p>
</div>

<!-- html comment block -->
<p>paragraph</p>

This is a paragraph with html <span style="color:red;">Test</span> and an html comment <!-- comment --> embedded in it.
.
<div>
<p>paragraph</p>
</div>
<!-- html comment block -->
<p>paragraph</p>
<p>This is a paragraph with html <span style="color:red;">Test</span> and an html comment  embedded in it.</p>
.
Document[0, 197]
  HtmlBlock[0, 30]
  HtmlCommentBlock[31, 59]
  HtmlBlock[59, 76]
  Paragraph[77, 197]
    Text[77, 107] chars:[77, 107, "This  … html "]
    HtmlInline[107, 132] chars:[107, 132, "<span … ed;\">"]
    Text[132, 136] chars:[132, 136, "Test"]
    HtmlInline[136, 143] chars:[136, 143, "</span>"]
    Text[143, 164] chars:[143, 164, " and  … ment "]
    HtmlInlineComment[164, 180] chars:[164, 180, "<!--  … t -->"]
    Text[180, 196] chars:[180, 196, " embe … n it."]
````````````````````````````````


## HTML Parse Inner Comments

Html comments in block

```````````````````````````````` example HTML Parse Inner Comments: 1
<!-- HTML Comment -->
<div>
</div>
.
<!-- HTML Comment -->
<div>
</div>
.
Document[0, 35]
  HtmlCommentBlock[0, 22]
  HtmlBlock[22, 35]
````````````````````````````````


Html comments in block

```````````````````````````````` example HTML Parse Inner Comments: 2
<div>
    <!-- HTML Comment -->
</div>
.
<div>
    <!-- HTML Comment -->
</div>
.
Document[0, 39]
  HtmlBlock[0, 39]
````````````````````````````````


Html comments in block

```````````````````````````````` example HTML Parse Inner Comments: 3
<div>
</div>
<!-- HTML Comment -->
.
<div>
</div>
<!-- HTML Comment -->
.
Document[0, 35]
  HtmlBlock[0, 35]
````````````````````````````````


Html comments in block, parse inner comments

```````````````````````````````` example(HTML Parse Inner Comments: 4) options(parse-inner-comments)
<!-- HTML Comment -->
<div>
</div>
.
<!-- HTML Comment -->
<div>
</div>
.
Document[0, 35]
  HtmlCommentBlock[0, 22]
  HtmlBlock[22, 35]
````````````````````````````````


Html comments in block, parse inner comments

```````````````````````````````` example(HTML Parse Inner Comments: 5) options(parse-inner-comments)
<div>
    <!-- HTML Comment -->
</div>
.
.
Document[0, 39]
  HtmlBlock[0, 39]
    HtmlInnerBlock[0, 10] chars:[0, 10, "<div>\n    "]
    HtmlInnerBlockComment[10, 31] chars:[10, 31, "<!--  … t -->"]
    HtmlInnerBlock[31, 38] chars:[31, 38, "\n</div>"]
````````````````````````````````


Html comments in block, parse inner comments

```````````````````````````````` example(HTML Parse Inner Comments: 6) options(parse-inner-comments)
<div>
</div>
<!-- HTML Comment -->
.
.
Document[0, 35]
  HtmlBlock[0, 35]
    HtmlInnerBlock[0, 13] chars:[0, 13, "<div> … div>\n"]
    HtmlInnerBlockComment[13, 34] chars:[13, 34, "<!--  … t -->"]
````````````````````````````````


## Inline HTML

kbd tag

```````````````````````````````` example Inline HTML: 1
text with <kbd>ENTER</kbd> embedded
.
<p>text with <kbd>ENTER</kbd> embedded</p>
.
Document[0, 36]
  Paragraph[0, 36]
    Text[0, 10] chars:[0, 10, "text with "]
    HtmlInline[10, 15] chars:[10, 15, "<kbd>"]
    Text[15, 20] chars:[15, 20, "ENTER"]
    HtmlInline[20, 26] chars:[20, 26, "</kbd>"]
    Text[26, 35] chars:[26, 35, " embedded"]
````````````````````````````````


## GFM compatibility

### GFM Emphasis

Emphasis around inline code spans

```````````````````````````````` example GFM Emphasis: 1
please add  `add_gtest(`**`your_unittest`**` `**`your_unittest_unittest.cc`**` )`
.
<p>please add  <code>add_gtest(</code><strong><code>your_unittest</code></strong><code></code><strong><code>your_unittest_unittest.cc</code></strong><code>)</code></p>
.
Document[0, 82]
  Paragraph[0, 82]
    Text[0, 12] chars:[0, 12, "pleas … add  "]
    Code[12, 24] textOpen:[12, 13, "`"] text:[13, 23, "add_gtest("] textClose:[23, 24, "`"]
    StrongEmphasis[24, 43] textOpen:[24, 26, "**"] text:[26, 41, "`your_unittest`"] textClose:[41, 43, "**"]
      Code[26, 41] textOpen:[26, 27, "`"] text:[27, 40, "your_ … unittest"] textClose:[40, 41, "`"]
    Code[43, 46] textOpen:[43, 44, "`"] text:[44, 45, " "] textClose:[45, 46, "`"]
    StrongEmphasis[46, 77] textOpen:[46, 48, "**"] text:[48, 75, "`your_unittest_unittest.cc`"] textClose:[75, 77, "**"]
      Code[48, 75] textOpen:[48, 49, "`"] text:[49, 74, "your_ … unittest_unittest.cc"] textClose:[74, 75, "`"]
    Code[77, 81] textOpen:[77, 78, "`"] text:[78, 80, " )"] textClose:[80, 81, "`"]
````````````````````````````````


Some weird commonmark processing of emphasis

```````````````````````````````` example(GFM Emphasis: 2) options(relaxed-emphasis, IGNORE)
**bold*bold-italic*bold**
.
<p><strong>bold<em>bold-italic</em>bold</strong></p>
.
Document[0, 26]
  Paragraph[0, 26]
    StrongEmphasis[0, 19] textOpen:[0, 2, "**"] text:[2, 23, "bold*bold-italic*bold"] textClose:[23, 25, "*"]
      Text[2, 6] chars:[2, 6, "bold"]
      Emphasis[7, 20] textOpen:[7, 8, "*"] text:[8, 19, "bold-italic"] textClose:[19, 20, "*"]
        Text[8, 19] chars:[7, 18, "bold-"..."talic"]
      Text[19, 23] chars:[19, 23, "bold"]
````````````````````````````````


This works as expected:

```````````````````````````````` example GFM Emphasis: 3
**bold *bold-italic* bold**
.
<p><strong>bold <em>bold-italic</em> bold</strong></p>
.
Document[0, 28]
  Paragraph[0, 28]
    StrongEmphasis[0, 27] textOpen:[0, 2, "**"] text:[2, 25, "bold *bold-italic* bold"] textClose:[25, 27, "**"]
      Text[2, 7] chars:[2, 7, "bold "]
      Emphasis[7, 20] textOpen:[7, 8, "*"] text:[8, 19, "bold-italic"] textClose:[19, 20, "*"]
        Text[8, 19] chars:[8, 19, "bold- … talic"]
      Text[20, 25] chars:[20, 25, " bold"]
````````````````````````````````


code mixed with emphasis:

```````````````````````````````` example GFM Emphasis: 4
`code with `**`bold`**` inside`
.
<p><code>code with</code><strong><code>bold</code></strong><code>inside</code></p>
.
Document[0, 32]
  Paragraph[0, 32]
    Code[0, 12] textOpen:[0, 1, "`"] text:[1, 11, "code with "] textClose:[11, 12, "`"]
    StrongEmphasis[12, 22] textOpen:[12, 14, "**"] text:[14, 20, "`bold`"] textClose:[20, 22, "**"]
      Code[14, 20] textOpen:[14, 15, "`"] text:[15, 19, "bold"] textClose:[19, 20, "`"]
    Code[22, 31] textOpen:[22, 23, "`"] text:[23, 30, " inside"] textClose:[30, 31, "`"]
````````````````````````````````


## Fenced Code Options

Change language class prefix

```````````````````````````````` example(Fenced Code Options: 1) options(no-class-prefix)
```text
plain text
```
.
<pre><code class="text">plain text
</code></pre>
.
Document[0, 23]
  FencedCodeBlock[0, 22] open:[0, 3, "```"] info:[3, 7, "text"] content:[8, 19] lines[3] close:[19, 22, "```"]
````````````````````````````````


empty, no info

```````````````````````````````` example Fenced Code Options: 2
```

```
.
<pre><code>
</code></pre>
.
Document[0, 9]
  FencedCodeBlock[0, 8] open:[0, 3, "```"] content:[4, 5] lines[3] close:[5, 8, "```"]
````````````````````````````````


empty, no info, blank line follows

```````````````````````````````` example Fenced Code Options: 3
```

```

.
<pre><code>
</code></pre>
.
Document[0, 10]
  FencedCodeBlock[0, 8] open:[0, 3, "```"] content:[4, 5] lines[3] close:[5, 8, "```"]
````````````````````````````````


empty, info

```````````````````````````````` example Fenced Code Options: 4
```info

```
.
<pre><code class="language-info">
</code></pre>
.
Document[0, 13]
  FencedCodeBlock[0, 12] open:[0, 3, "```"] info:[3, 7, "info"] content:[8, 9] lines[3] close:[9, 12, "```"]
````````````````````````````````


empty, info, blank line follows

```````````````````````````````` example Fenced Code Options: 5
```info

```

.
<pre><code class="language-info">
</code></pre>
.
Document[0, 14]
  FencedCodeBlock[0, 12] open:[0, 3, "```"] info:[3, 7, "info"] content:[8, 9] lines[3] close:[9, 12, "```"]
````````````````````````````````


## Anchor links option

Change language class prefix

```````````````````````````````` example Anchor links option: 1
inline anchor <a id="test" href="#"></a><em></em> test 
.
<p>inline anchor <a id="test" href="#"></a><em></em> test</p>
.
Document[0, 56]
  Paragraph[0, 56]
    Text[0, 14] chars:[0, 14, "inlin … chor "]
    HtmlInline[14, 36] chars:[14, 36, "<a id … =\"#\">"]
    HtmlInline[36, 40] chars:[36, 40, "</a>"]
    HtmlInline[40, 44] chars:[40, 44, "<em>"]
    HtmlInline[44, 49] chars:[44, 49, "</em>"]
    Text[49, 54] chars:[49, 54, " test"]
````````````````````````````````


## Thematic Break

Break with trailing spaces

```````````````````````````````` example Thematic Break: 1
---                 
.
<hr />
.
Document[0, 21]
  ThematicBreak[0, 20]
````````````````````````````````


## Image links

```````````````````````````````` example Image links: 1
![alt](/url) 
.
<p><img src="/url" alt="alt" /></p>
.
Document[0, 14]
  Paragraph[0, 14]
    Image[0, 12] textOpen:[0, 2, "!["] text:[2, 5, "alt"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 11, "/url"] pageRef:[7, 11, "/url"] linkClose:[11, 12, ")"]
      Text[2, 5] chars:[2, 5, "alt"]
````````````````````````````````


## Fenced Code

Option not to match closing fence characters to opening ones

```````````````````````````````` example(Fenced Code: 1) options(unmatched-fence)
```
proper unmatched fenced code
~~~
.
<pre><code>proper unmatched fenced code
</code></pre>
.
Document[0, 37]
  FencedCodeBlock[0, 36] open:[0, 3, "```"] content:[4, 33] lines[3] close:[33, 36, "~~~"]
````````````````````````````````


```````````````````````````````` example(Fenced Code: 2) options(unmatched-fence)
~~~
proper unmatched fenced code
```
.
<pre><code>proper unmatched fenced code
</code></pre>
.
Document[0, 37]
  FencedCodeBlock[0, 36] open:[0, 3, "~~~"] content:[4, 33] lines[3] close:[33, 36, "```"]
````````````````````````````````


non empty, info, blank line follows, unmatched

```````````````````````````````` example(Fenced Code: 3) options(unmatched-fence)
```info
some text
~~~

.
<pre><code class="language-info">some text
</code></pre>
.
Document[0, 23]
  FencedCodeBlock[0, 21] open:[0, 3, "```"] info:[3, 7, "info"] content:[8, 18] lines[3] close:[18, 21, "~~~"]
````````````````````````````````


## IntelliJ Dummy Identifier 

allow dummy identifier in url and text

```````````````````````````````` example(IntelliJ Dummy Identifier: 1) options(dummy-identifier)
[⎮text](/ur⎮l)

![al⎮t](/url⎮)

.
<p><a href="/ur%1Fl">⎮text</a></p>
<p><img src="/url%1F" alt="al⎮t" /></p>
.
Document[0, 32]
  Paragraph[0, 15]
    Link[0, 14] textOpen:[0, 1, "["] text:[1, 6, "%1ftext"] textClose:[6, 7, "]"] linkOpen:[7, 8, "("] url:[8, 13, "/ur%1fl"] pageRef:[8, 13, "/ur%1fl"] linkClose:[13, 14, ")"]
      Text[1, 6] chars:[1, 6, "%1ftext"]
  Paragraph[16, 31]
    Image[16, 30] textOpen:[16, 18, "!["] text:[18, 22, "al%1ft"] textClose:[22, 23, "]"] linkOpen:[23, 24, "("] url:[24, 29, "/url%1f"] pageRef:[24, 29, "/url%1f"] linkClose:[29, 30, ")"]
      Text[18, 22] chars:[18, 22, "al%1ft"]
````````````````````````````````


## Indented Code Options 

no trim trailing blank lines

```````````````````````````````` example Indented Code Options: 1
    code
    code line
    
    
    
.
<pre><code>code
code line
</code></pre>
.
Document[0, 38]
  IndentedCodeBlock[4, 38]
````````````````````````````````


trim trailing blank lines

```````````````````````````````` example(Indented Code Options: 2) options(code-trim-trailing)
    code
    code line
    
    
    
.
<pre><code>code
code line
</code></pre>
.
Document[0, 38]
  IndentedCodeBlock[4, 23]
````````````````````````````````


