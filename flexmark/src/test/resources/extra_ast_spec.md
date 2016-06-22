---
title: Core Extra Test Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Extra tests

Converts footnote references and definitions to footnotes in the HTML page

Code fence starting with setext header marker

```````````````````````````````` example Extra tests: 1
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


```````````````````````````````` example Extra tests: 2
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

```````````````````````````````` example Extra tests: 3
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

```````````````````````````````` example Extra tests: 4
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

```````````````````````````````` example Reference Repository Keep First tests: 1
[ref]

[ref]: /url1
[ref]: /url2
[ref]: /url3
.
<p><a href="/url1">ref</a></p>
.
Document[0, 46]
  Paragraph[0, 6]
    LinkRef[0, 5] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[0, 1, "["] reference:[1, 4, "ref"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "ref"]
  Reference[7, 19] refOpen:[7, 8, "["] ref:[8, 11, "ref"] refClose:[11, 13, "]:"] urlOpen:[0, 0] url:[14, 19, "/url1"] urlClose:[0, 0] titleOpen:[0, 0] title:[0, 0] titleClose:[0, 0]
  Reference[20, 32] refOpen:[20, 21, "["] ref:[21, 24, "ref"] refClose:[24, 26, "]:"] urlOpen:[0, 0] url:[27, 32, "/url2"] urlClose:[0, 0] titleOpen:[0, 0] title:[0, 0] titleClose:[0, 0]
  Reference[33, 45] refOpen:[33, 34, "["] ref:[34, 37, "ref"] refClose:[37, 39, "]:"] urlOpen:[0, 0] url:[40, 45, "/url3"] urlClose:[0, 0] titleOpen:[0, 0] title:[0, 0] titleClose:[0, 0]
````````````````````````````````


## Reference Repository Keep Last tests

Test repository KEEP_LAST behavior, meaning the last reference def is used

```````````````````````````````` example(Reference Repository Keep Last tests: 1) options(keep-last)
[ref]

[ref]: /url1
[ref]: /url2
[ref]: /url3
.
<p><a href="/url3">ref</a></p>
.
Document[0, 46]
  Paragraph[0, 6]
    LinkRef[0, 5] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[0, 1, "["] reference:[1, 4, "ref"] referenceClose:[4, 5, "]"]
      Text[1, 4] chars:[1, 4, "ref"]
  Reference[7, 19] refOpen:[7, 8, "["] ref:[8, 11, "ref"] refClose:[11, 13, "]:"] urlOpen:[0, 0] url:[14, 19, "/url1"] urlClose:[0, 0] titleOpen:[0, 0] title:[0, 0] titleClose:[0, 0]
  Reference[20, 32] refOpen:[20, 21, "["] ref:[21, 24, "ref"] refClose:[24, 26, "]:"] urlOpen:[0, 0] url:[27, 32, "/url2"] urlClose:[0, 0] titleOpen:[0, 0] title:[0, 0] titleClose:[0, 0]
  Reference[33, 45] refOpen:[33, 34, "["] ref:[34, 37, "ref"] refClose:[37, 39, "]:"] urlOpen:[0, 0] url:[40, 45, "/url3"] urlClose:[0, 0] titleOpen:[0, 0] title:[0, 0] titleClose:[0, 0]
````````````````````````````````


## Emphasis tests

Some weird commonmark processing of emphasis

```````````````````````````````` example(Emphasis tests: 1) options(relaxed-emphasis, IGNORE)
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


Some weird commonmark processing of emphasis

```````````````````````````````` example Emphasis tests: 2
**bold *bold-italic* bold**
.
<p><strong>bold <em>bold-italic</em> bold</strong></p>
.
Document[0, 28]
  Paragraph[0, 28]
    StrongEmphasis[0, 27] textOpen:[0, 2, "**"] text:[2, 25, "bold *bold-italic* bold"] textClose:[25, 27, "**"]
      Text[2, 7] chars:[2, 7, "bold "]
      Emphasis[7, 20] textOpen:[7, 8, "*"] text:[8, 19, "bold-italic"] textClose:[19, 20, "*"]
        Text[8, 19] chars:[8, 19, "bold-"..."talic"]
      Text[20, 25] chars:[20, 25, " bold"]
````````````````````````````````


## ATX Header options

Allow atx headers without a space between # and the title

```````````````````````````````` example(ATX Header options: 1) options(hdr-no-atx-space)
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
Document[0, 1063]
  Heading[0, 8] textOpen:[0, 1, "#"] text:[1, 8, "Heading"] textClose:[0, 0]
    Text[1, 8] chars:[1, 8, "Heading"]
  Heading[85, 94] textOpen:[85, 87, "##"] text:[87, 94, "Heading"] textClose:[0, 0]
    Text[87, 94] chars:[87, 94, "Heading"]
  Heading[171, 181] textOpen:[171, 174, "###"] text:[174, 181, "Heading"] textClose:[0, 0]
    Text[174, 181] chars:[174, 181, "Heading"]
  Heading[258, 269] textOpen:[258, 262, "####"] text:[262, 269, "Heading"] textClose:[0, 0]
    Text[262, 269] chars:[262, 269, "Heading"]
  Heading[346, 358] textOpen:[346, 351, "#####"] text:[351, 358, "Heading"] textClose:[0, 0]
    Text[351, 358] chars:[351, 358, "Heading"]
  Heading[435, 448] textOpen:[435, 441, "######"] text:[441, 448, "Heading"] textClose:[0, 0]
    Text[441, 448] chars:[441, 448, "Heading"]
  Heading[526, 536] textOpen:[526, 527, "#"] text:[527, 534, "Heading"] textClose:[535, 536, "#"]
    Text[527, 534] chars:[527, 534, "Heading"]
  Heading[613, 624] textOpen:[613, 615, "##"] text:[615, 622, "Heading"] textClose:[623, 624, "#"]
    Text[615, 622] chars:[615, 622, "Heading"]
  Heading[701, 713] textOpen:[701, 704, "###"] text:[704, 711, "Heading"] textClose:[712, 713, "#"]
    Text[704, 711] chars:[704, 711, "Heading"]
  Heading[790, 803] textOpen:[790, 794, "####"] text:[794, 801, "Heading"] textClose:[802, 803, "#"]
    Text[794, 801] chars:[794, 801, "Heading"]
  Heading[880, 894] textOpen:[880, 885, "#####"] text:[885, 892, "Heading"] textClose:[893, 894, "#"]
    Text[885, 892] chars:[885, 892, "Heading"]
  Heading[971, 986] textOpen:[971, 977, "######"] text:[977, 984, "Heading"] textClose:[985, 986, "#"]
    Text[977, 984] chars:[977, 984, "Heading"]
````````````````````````````````


Don't allow leading spaces

```````````````````````````````` example(ATX Header options: 2) options(hdr-no-lead-space)
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
    Text[24, 35] chars:[24, 35, "### H"..."ading"]
    SoftLineBreak[35, 36]
    Text[37, 49] chars:[37, 49, "#### "..."ading"]
    SoftLineBreak[49, 50]
    Text[51, 64] chars:[51, 64, "#####"..."ading"]
    SoftLineBreak[64, 65]
    Text[66, 80] chars:[66, 80, "#####"..."ading"]
````````````````````````````````


Don't allow leading spaces

```````````````````````````````` example(ATX Header options: 3) options(hdr-no-lead-space, hdr-no-atx-space)
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
    Text[24, 35] chars:[24, 35, "### H"..."ading"]
    SoftLineBreak[35, 36]
    Text[37, 49] chars:[37, 49, "#### "..."ading"]
    SoftLineBreak[49, 50]
    Text[51, 64] chars:[51, 64, "#####"..."ading"]
    SoftLineBreak[64, 65]
    Text[66, 80] chars:[66, 80, "#####"..."ading"]
````````````````````````````````


## List Options

### List - Fixed Indent

Options defined: list-fixed-indent, list-no-break, list-no-loose, list-no-start to change
the behavior of list parser and renderer

Default processing

```````````````````````````````` example List - Fixed Indent: 1
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
        Text[126, 140] chars:[126, 140, "sub s"..."tem 1"]
````````````````````````````````


Fixed indentation only (4 spaces)

```````````````````````````````` example(List - Fixed Indent: 2) options(list-fixed-indent)
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
                Text[126, 140] chars:[126, 140, "sub s"..."tem 1"]
````````````````````````````````


Fixed indentation with code blocks

```````````````````````````````` example(List - Fixed Indent: 3) options(list-fixed-indent)
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
        Text[18, 34] chars:[18, 34, "this "..." code"]
      IndentedCodeBlock[48, 61]
    BulletListItem[62, 123] open:[62, 63, "*"]
      Paragraph[64, 71]
        Text[64, 70] chars:[64, 70, "item 2"]
      Paragraph[80, 97]
        Text[80, 96] chars:[80, 96, "this "..." code"]
      IndentedCodeBlock[110, 123]
    BulletListItem[125, 186] open:[125, 126, "*"]
      Paragraph[127, 134]
        Text[127, 133] chars:[127, 133, "item 3"]
      Paragraph[143, 160]
        Text[143, 159] chars:[143, 159, "this "..." code"]
      IndentedCodeBlock[173, 186]
    BulletListItem[189, 661] open:[189, 190, "*"]
      Paragraph[191, 198]
        Text[191, 197] chars:[191, 197, "item 4"]
      Paragraph[207, 224]
        Text[207, 223] chars:[207, 223, "this "..." code"]
      IndentedCodeBlock[237, 250]
      BulletList[254, 661] isTight=false
        BulletListItem[254, 327] open:[254, 255, "*"]
          Paragraph[256, 267]
            Text[256, 266] chars:[256, 266, "sub item 1"]
          Paragraph[280, 297]
            Text[280, 296] chars:[280, 296, "this "..." code"]
          IndentedCodeBlock[314, 327]
        BulletListItem[332, 405] open:[332, 333, "*"]
          Paragraph[334, 345]
            Text[334, 344] chars:[334, 344, "sub item 2"]
          Paragraph[358, 375]
            Text[358, 374] chars:[358, 374, "this "..." code"]
          IndentedCodeBlock[392, 405]
        BulletListItem[411, 484] open:[411, 412, "*"]
          Paragraph[413, 424]
            Text[413, 423] chars:[413, 423, "sub item 3"]
          Paragraph[437, 454]
            Text[437, 453] chars:[437, 453, "this "..." code"]
          IndentedCodeBlock[471, 484]
        BulletListItem[491, 661] open:[491, 492, "*"]
          Paragraph[493, 504]
            Text[493, 503] chars:[493, 503, "sub item 4"]
          Paragraph[517, 534]
            Text[517, 533] chars:[517, 533, "this "..." code"]
          IndentedCodeBlock[551, 564]
          BulletList[572, 661] isTight=false
            BulletListItem[572, 661] open:[572, 573, "*"]
              Paragraph[574, 589]
                Text[574, 588] chars:[574, 588, "sub s"..."tem 1"]
              Paragraph[606, 623]
                Text[606, 622] chars:[606, 622, "this "..." code"]
              IndentedCodeBlock[648, 661]
````````````````````````````````


### List - No Auto Loose

With auto loose setting for list

```````````````````````````````` example List - No Auto Loose: 1
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

```````````````````````````````` example(List - No Auto Loose: 2) options(list-no-loose)
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

```````````````````````````````` example List - No Break on Double Blank Line: 1
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

```````````````````````````````` example(List - No Break on Double Blank Line: 2) options(list-no-break)
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


With break all lists on two blank lines

```````````````````````````````` example List - No Break on Double Blank Line: 3
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

```````````````````````````````` example(List - No Break on Double Blank Line: 4) options(list-no-break)
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
  BulletList[96, 114] isTight=true
    BulletListItem[96, 105] open:[96, 97, "*"]
      Paragraph[98, 105]
        Text[98, 104] chars:[98, 104, "item 4"]
    BulletListItem[105, 114] open:[105, 106, "*"]
      Paragraph[107, 114]
        Text[107, 113] chars:[107, 113, "item 5"]
````````````````````````````````


### List - No Manual Start Numbering

With start

```````````````````````````````` example List - No Manual Start Numbering: 1
2. item 1
1. item 1
1. item 1
.
<ol start="2">
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


Without start

```````````````````````````````` example(List - No Manual Start Numbering: 2) options(list-no-start)
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


