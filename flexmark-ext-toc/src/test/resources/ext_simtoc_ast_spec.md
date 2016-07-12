---
title: SimToc Extension Spec
author: 
version: 
date: '2016-06-30'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

    ```java
    class dummy {
    //    `String test = "SimTocContent"`;
    }
    ```

    ```markdown
    String test = "SimTocContent";
    ```

## SimToc

The Sim TOC tag has the following format: `[TOC style]: # "Title"` and includes all following
lines until a blank line. <!--SimTocContent-->

Lines after the TOC tag are added to the `SimTocContent` child node of the SimToc block.

The intention for this tag is to have the `SimTocContent` updated to reflect the content of the
document.

1. `style` consists of space separated list of options:

    - `levels=levelList` where level list is a comma separated list of levels or ranges. Default
      is to include heading levels 2 and 3. Examples:
        - `levels=4` include levels 2,3 and 4
        - `levels=2-4` include levels 2,3 and 4. same as `levels=4`
        - `levels=2-4,5` include levels 2,3,4 and 5
        - `levels=1,3` include levels 1 and 3

    - `html` generate HTML version of the TOC

    - `markdown` generate Markdown version of the TOC

    - `text` to only include the text of the heading

    - `formatted` to include text and inline formatting

    - `bullet` to use a bullet list for the TOC items

    - `numbered` to use a numbered list for TOC items

2. `"Title"` specifies the text for the table of contents heading. If omitted or blank then no
   heading will be generated for the table of contents. `#` prefix in the title will specify the
   header level to use for the heading above the table of contents listing. If no `#` prefix is
   used then the heading level will be taken from project settings.

no spaces after first bracket

```````````````````````````````` example SimToc: 1
[ TOC]: #  
.
.
Document[0, 12]
  Reference[0, 9] refOpen:[0, 1, "["] ref:[2, 5, "TOC"] refClose:[5, 7, "]:"] urlOpen:[0, 0] url:[8, 9, "#"] urlClose:[0, 0] titleOpen:[0, 0] title:[0, 0] titleClose:[0, 0]
````````````````````````````````


plain is ok

```````````````````````````````` example SimToc: 2
[TOC]: #  
.
.
Document[0, 11]
  SimTocBlock[0, 11] openingMarker:[0, 1] tocKeyword:[1, 4] style:[0, 0] closingMarker:[4, 6] anchorMarker:[7, 8] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
````````````````````````````````


accepts all text for style

```````````````````````````````` example SimToc: 3
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
.
.
Document[0, 71]
  SimTocBlock[0, 71] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 4
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  

# Heading 1
.
<h1 id="heading-1">Heading 1</h1>
.
Document[0, 84]
  SimTocBlock[0, 71] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
  Heading[72, 83] textOpen:[72, 73, "#"] text:[74, 83, "Heading 1"] textClose:[0, 0]
    Text[74, 83] chars:[74, 83, "Heading 1"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 5
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  

- asfdasfd
.
<ul>
  <li>asfdasfd</li>
</ul>
.
Document[0, 83]
  SimTocBlock[0, 71] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
  BulletList[72, 83] isTight=true
    BulletListItem[72, 83] open:[72, 73, "-"]
      Paragraph[74, 83]
        Text[74, 82] chars:[74, 82, "asfdasfd"]
````````````````````````````````


```````````````````````````````` example SimToc: 6
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  

<div>
</div>
.
<div>
</div>
.
Document[0, 85]
  SimTocBlock[0, 71] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
  HtmlBlock[72, 85]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 7
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
# Heading 1
## Heading 1.1
- afdasfdsadf
- asfdasfd
.
<div><h1>Table of Contents</h1>
  <ol>
    <li><a href="#heading-11">Heading 1.1</a></li>
  </ol>
</div>
<h2 id="heading-11">Heading 1.1</h2>
<ul>
  <li>afdasfdsadf</li>
  <li>asfdasfd</li>
</ul>
.
Document[0, 123]
  SimTocBlock[0, 82] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
    SimTocContent[71, 82]
      Heading[71, 82] textOpen:[71, 72, "#"] text:[73, 82, "Heading 1"] textClose:[0, 0]
  Heading[83, 97] textOpen:[83, 85, "##"] text:[86, 97, "Heading 1.1"] textClose:[0, 0]
    Text[86, 97] chars:[86, 97, "Headi"..."g 1.1"]
  BulletList[98, 123] isTight=true
    BulletListItem[98, 112] open:[98, 99, "-"]
      Paragraph[100, 112]
        Text[100, 111] chars:[100, 111, "afdas"..."dsadf"]
    BulletListItem[112, 123] open:[112, 113, "-"]
      Paragraph[114, 123]
        Text[114, 122] chars:[114, 122, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 8
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
# Heading 1

- afdasfdsadf
- asfdasfd
.
<ul>
  <li>afdasfdsadf</li>
  <li>asfdasfd</li>
</ul>
.
Document[0, 109]
  SimTocBlock[0, 82] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
    SimTocContent[71, 82]
      Heading[71, 82] textOpen:[71, 72, "#"] text:[73, 82, "Heading 1"] textClose:[0, 0]
  BulletList[84, 109] isTight=true
    BulletListItem[84, 98] open:[84, 85, "-"]
      Paragraph[86, 98]
        Text[86, 97] chars:[86, 97, "afdas"..."dsadf"]
    BulletListItem[98, 109] open:[98, 99, "-"]
      Paragraph[100, 109]
        Text[100, 108] chars:[100, 108, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 9
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
- afdasfdsadf
- asfdasfd

# Heading 1
.
<h1 id="heading-1">Heading 1</h1>
.
Document[0, 109]
  SimTocBlock[0, 96] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
    SimTocContent[71, 96]
      BulletList[71, 96] isTight=true
        BulletListItem[71, 85] open:[71, 72, "-"]
          Paragraph[73, 85]
            Text[73, 84] chars:[73, 84, "afdas"..."dsadf"]
        BulletListItem[85, 96] open:[85, 86, "-"]
          Paragraph[87, 96]
            Text[87, 95] chars:[87, 95, "asfdasfd"]
  Heading[97, 108] textOpen:[97, 98, "#"] text:[99, 108, "Heading 1"] textClose:[0, 0]
    Text[99, 108] chars:[99, 108, "Heading 1"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 10
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
# Heading 1
- afdasfdsadf
- asfdasfd
    - afdasfdsadf
    - asfdasfd
    
- asfdasfd
.
<ul>
  <li>asfdasfd</li>
</ul>
.
Document[0, 157]
  SimTocBlock[0, 141] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
    SimTocContent[71, 141]
      Heading[71, 82] textOpen:[71, 72, "#"] text:[73, 82, "Heading 1"] textClose:[0, 0]
      BulletList[83, 141] isTight=true
        BulletListItem[83, 97] open:[83, 84, "-"]
          Paragraph[85, 97]
            Text[85, 96] chars:[85, 96, "afdas"..."dsadf"]
        BulletListItem[97, 141] open:[97, 98, "-"]
          Paragraph[99, 108]
            Text[99, 107] chars:[99, 107, "asfdasfd"]
          BulletList[112, 141] isTight=true
            BulletListItem[112, 126] open:[112, 113, "-"]
              Paragraph[114, 126]
                Text[114, 125] chars:[114, 125, "afdas"..."dsadf"]
            BulletListItem[130, 141] open:[130, 131, "-"]
              Paragraph[132, 141]
                Text[132, 140] chars:[132, 140, "asfdasfd"]
  BulletList[146, 157] isTight=true
    BulletListItem[146, 157] open:[146, 147, "-"]
      Paragraph[148, 157]
        Text[148, 156] chars:[148, 156, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 11
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
<div>
  <h1>Content</h1>
  <ul>
      <li>blah blah</li>  
      
      <li>blah blah</li>  
  </ul>
</div>
.
<pre><code>  &lt;li&gt;blah blah&lt;/li&gt;  
</code></pre>
  </ul>
</div>
.
Document[0, 179]
  SimTocBlock[0, 130] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
    SimTocContent[71, 130]
      HtmlBlock[71, 130]
  IndentedCodeBlock[141, 164]
  HtmlBlock[164, 179]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 12
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
<div>
  <h1>Content</h1>
  <ul>
      <li>blah blah</li>  
      <li>blah blah</li>  
  </ul>
</div>
## Heading 1.1
.
.
Document[0, 187]
  SimTocBlock[0, 187] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
    SimTocContent[71, 187]
      HtmlBlock[71, 187]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 13
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
<div>
  <h1>Content</h1>
</div>
- afdasfdsadf
- asfdasfd
.
.
Document[0, 128]
  SimTocBlock[0, 128] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
    SimTocContent[71, 128]
      HtmlBlock[71, 128]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 14
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
<div>
  <h1>Content</h1>
</div>
<div>
  <h1>Content</h1>
</div>
.
.
Document[0, 135]
  SimTocBlock[0, 135] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
    SimTocContent[71, 135]
      HtmlBlock[71, 135]
````````````````````````````````


Default rendering with emphasis

```````````````````````````````` example SimToc: 15
[TOC]:#  

# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**
.
<div><h1>Table of Contents</h1>
  <ul>
    <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
    <ul>
      <li><a href="#heading-111">Heading 1.1.1</a></li>
      <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
    </ul></li>
  </ul>
</div>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
.
Document[0, 126]
  SimTocBlock[0, 10] openingMarker:[0, 1] tocKeyword:[1, 4] style:[0, 0] closingMarker:[4, 6] anchorMarker:[6, 7] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
  Heading[11, 36] textOpen:[11, 12, "#"] text:[13, 36, "Heading **some bold** 1"] textClose:[0, 0]
    Text[13, 21] chars:[13, 21, "Heading "]
    StrongEmphasis[21, 34] textOpen:[21, 23, "**"] text:[23, 32, "some bold"] textClose:[32, 34, "**"]
      Text[23, 32] chars:[23, 32, "some bold"]
    Text[34, 36] chars:[34, 36, " 1"]
  Heading[37, 65] textOpen:[37, 39, "##"] text:[40, 65, "Heading 1.1 _some italic_"] textClose:[0, 0]
    Text[40, 52] chars:[40, 52, "Headi"..." 1.1 "]
    Emphasis[52, 65] textOpen:[52, 53, "_"] text:[53, 64, "some italic"] textClose:[64, 65, "_"]
      Text[53, 64] chars:[53, 64, "some "..."talic"]
  Heading[66, 83] textOpen:[66, 69, "###"] text:[70, 83, "Heading 1.1.1"] textClose:[0, 0]
    Text[70, 83] chars:[70, 83, "Headi"..."1.1.1"]
  Heading[84, 125] textOpen:[84, 87, "###"] text:[88, 125, "Heading 1.1.2  **_some bold italic_**"] textClose:[0, 0]
    Text[88, 103] chars:[88, 103, "Headi"..."1.2  "]
    StrongEmphasis[103, 125] textOpen:[103, 105, "**"] text:[105, 123, "_some bold italic_"] textClose:[123, 125, "**"]
      Emphasis[105, 123] textOpen:[105, 106, "_"] text:[106, 122, "some bold italic"] textClose:[122, 123, "_"]
        Text[106, 122] chars:[106, 122, "some "..."talic"]
````````````````````````````````


With option nodes in the ast

```````````````````````````````` example(SimToc: 16) options(with-option-list)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: # 
.
.
Document[0, 70]
  SimTocBlock[0, 70] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
    SimTocOptionList[5, 64]
      SimTocOption[5, 19] chars:[5, 19, "level"..."b,c,d"]
      SimTocOption[20, 24] chars:[20, 24, "html"]
      SimTocOption[25, 33] chars:[25, 33, "markdown"]
      SimTocOption[34, 38] chars:[34, 38, "text"]
      SimTocOption[39, 48] chars:[39, 48, "formatted"]
      SimTocOption[49, 55] chars:[49, 55, "bullet"]
      SimTocOption[56, 64] chars:[56, 64, "numbered"]
````````````````````````````````


