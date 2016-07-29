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

no spaces after first bracket

```````````````````````````````` example SimToc: 1
[ TOC]: #  
.
.
Document[0, 12]
  Reference[0, 9] refOpen:[0, 1, "["] ref:[2, 5, "TOC"] refClose:[5, 7, "]:"] url:[8, 9, "#"]
````````````````````````````````


plain is ok

```````````````````````````````` example SimToc: 2
[TOC]: #  
.
.
Document[0, 11]
  SimTocBlock[0, 11] openingMarker:[0, 1] tocKeyword:[1, 4] closingMarker:[4, 6] anchorMarker:[7, 8, "#"]
````````````````````````````````


accepts all text for style

```````````````````````````````` example SimToc: 3
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
.
.
Document[0, 71]
  SimTocBlock[0, 71] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 4
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  

# Heading 1
.
<h1 id="heading-1">Heading 1</h1>
.
Document[0, 84]
  SimTocBlock[0, 71] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
  Heading[72, 83] textOpen:[72, 73, "#"] text:[74, 83, "Heading 1"]
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
  SimTocBlock[0, 71] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
  BulletList[72, 83] isTight
    BulletListItem[72, 83] open:[72, 73, "-"] isTight
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
  SimTocBlock[0, 71] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
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
  SimTocBlock[0, 82] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 82]
      Heading[71, 82] textOpen:[71, 72, "#"] text:[73, 82, "Heading 1"]
  Heading[83, 97] textOpen:[83, 85, "##"] text:[86, 97, "Heading 1.1"]
    Text[86, 97] chars:[86, 97, "Headi … g 1.1"]
  BulletList[98, 123] isTight
    BulletListItem[98, 112] open:[98, 99, "-"] isTight
      Paragraph[100, 112]
        Text[100, 111] chars:[100, 111, "afdas … dsadf"]
    BulletListItem[112, 123] open:[112, 113, "-"] isTight
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
  SimTocBlock[0, 82] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 82]
      Heading[71, 82] textOpen:[71, 72, "#"] text:[73, 82, "Heading 1"]
  BulletList[84, 109] isTight
    BulletListItem[84, 98] open:[84, 85, "-"] isTight
      Paragraph[86, 98]
        Text[86, 97] chars:[86, 97, "afdas … dsadf"]
    BulletListItem[98, 109] open:[98, 99, "-"] isTight
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
  SimTocBlock[0, 96] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 96]
      BulletList[71, 96] isTight
        BulletListItem[71, 85] open:[71, 72, "-"] isTight
          Paragraph[73, 85]
            Text[73, 84] chars:[73, 84, "afdas … dsadf"]
        BulletListItem[85, 96] open:[85, 86, "-"] isTight
          Paragraph[87, 96]
            Text[87, 95] chars:[87, 95, "asfdasfd"]
  Heading[97, 108] textOpen:[97, 98, "#"] text:[99, 108, "Heading 1"]
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
  SimTocBlock[0, 141] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 141]
      Heading[71, 82] textOpen:[71, 72, "#"] text:[73, 82, "Heading 1"]
      BulletList[83, 141] isTight
        BulletListItem[83, 97] open:[83, 84, "-"] isTight
          Paragraph[85, 97]
            Text[85, 96] chars:[85, 96, "afdas … dsadf"]
        BulletListItem[97, 141] open:[97, 98, "-"] isTight
          Paragraph[99, 108]
            Text[99, 107] chars:[99, 107, "asfdasfd"]
          BulletList[112, 141] isTight
            BulletListItem[112, 126] open:[112, 113, "-"] isTight
              Paragraph[114, 126]
                Text[114, 125] chars:[114, 125, "afdas … dsadf"]
            BulletListItem[130, 141] open:[130, 131, "-"] isTight
              Paragraph[132, 141]
                Text[132, 140] chars:[132, 140, "asfdasfd"]
  BulletList[146, 157] isTight
    BulletListItem[146, 157] open:[146, 147, "-"] isTight
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
  SimTocBlock[0, 130] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
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
  SimTocBlock[0, 187] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
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
  SimTocBlock[0, 128] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
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
  SimTocBlock[0, 135] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 135]
      HtmlBlock[71, 135]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 15) options(blank-line-spacer)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  

# Heading 1
.
.
Document[0, 84]
  SimTocBlock[0, 83] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 83]
      Heading[72, 83] textOpen:[72, 73, "#"] text:[74, 83, "Heading 1"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 16) options(blank-line-spacer)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  

- asfdasfd
.
.
Document[0, 83]
  SimTocBlock[0, 83] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 83]
      BulletList[72, 83] isTight
        BulletListItem[72, 83] open:[72, 73, "-"] isTight
          Paragraph[74, 83]
            Text[74, 82] chars:[74, 82, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 17) options(blank-line-spacer)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  

<div>
</div>
.
.
Document[0, 85]
  SimTocBlock[0, 85] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 85]
      HtmlBlock[72, 85]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 18) options(blank-line-spacer)
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
  SimTocBlock[0, 82] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 82]
      Heading[71, 82] textOpen:[71, 72, "#"] text:[73, 82, "Heading 1"]
  Heading[83, 97] textOpen:[83, 85, "##"] text:[86, 97, "Heading 1.1"]
    Text[86, 97] chars:[86, 97, "Headi … g 1.1"]
  BulletList[98, 123] isTight
    BulletListItem[98, 112] open:[98, 99, "-"] isTight
      Paragraph[100, 112]
        Text[100, 111] chars:[100, 111, "afdas … dsadf"]
    BulletListItem[112, 123] open:[112, 113, "-"] isTight
      Paragraph[114, 123]
        Text[114, 122] chars:[114, 122, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 19) options(blank-line-spacer)
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
Document[0, 124]
  SimTocBlock[0, 83] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 83]
      Heading[72, 83] textOpen:[72, 73, "#"] text:[74, 83, "Heading 1"]
  Heading[84, 98] textOpen:[84, 86, "##"] text:[87, 98, "Heading 1.1"]
    Text[87, 98] chars:[87, 98, "Headi … g 1.1"]
  BulletList[99, 124] isTight
    BulletListItem[99, 113] open:[99, 100, "-"] isTight
      Paragraph[101, 113]
        Text[101, 112] chars:[101, 112, "afdas … dsadf"]
    BulletListItem[113, 124] open:[113, 114, "-"] isTight
      Paragraph[115, 124]
        Text[115, 123] chars:[115, 123, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 20) options(blank-line-spacer)
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
  SimTocBlock[0, 82] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 82]
      Heading[71, 82] textOpen:[71, 72, "#"] text:[73, 82, "Heading 1"]
  BulletList[84, 109] isTight
    BulletListItem[84, 98] open:[84, 85, "-"] isTight
      Paragraph[86, 98]
        Text[86, 97] chars:[86, 97, "afdas … dsadf"]
    BulletListItem[98, 109] open:[98, 99, "-"] isTight
      Paragraph[100, 109]
        Text[100, 108] chars:[100, 108, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 21) options(blank-line-spacer)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
- afdasfdsadf
- asfdasfd

# Heading 1
.
<h1 id="heading-1">Heading 1</h1>
.
Document[0, 109]
  SimTocBlock[0, 96] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 96]
      BulletList[71, 96] isTight
        BulletListItem[71, 85] open:[71, 72, "-"] isTight
          Paragraph[73, 85]
            Text[73, 84] chars:[73, 84, "afdas … dsadf"]
        BulletListItem[85, 96] open:[85, 86, "-"] isTight
          Paragraph[87, 96]
            Text[87, 95] chars:[87, 95, "asfdasfd"]
  Heading[97, 108] textOpen:[97, 98, "#"] text:[99, 108, "Heading 1"]
    Text[99, 108] chars:[99, 108, "Heading 1"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 22) options(blank-line-spacer)
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
  SimTocBlock[0, 141] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 141]
      Heading[71, 82] textOpen:[71, 72, "#"] text:[73, 82, "Heading 1"]
      BulletList[83, 141] isTight
        BulletListItem[83, 97] open:[83, 84, "-"] isTight
          Paragraph[85, 97]
            Text[85, 96] chars:[85, 96, "afdas … dsadf"]
        BulletListItem[97, 141] open:[97, 98, "-"] isTight
          Paragraph[99, 108]
            Text[99, 107] chars:[99, 107, "asfdasfd"]
          BulletList[112, 141] isTight
            BulletListItem[112, 126] open:[112, 113, "-"] isTight
              Paragraph[114, 126]
                Text[114, 125] chars:[114, 125, "afdas … dsadf"]
            BulletListItem[130, 141] open:[130, 131, "-"] isTight
              Paragraph[132, 141]
                Text[132, 140] chars:[132, 140, "asfdasfd"]
  BulletList[146, 157] isTight
    BulletListItem[146, 157] open:[146, 147, "-"] isTight
      Paragraph[148, 157]
        Text[148, 156] chars:[148, 156, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 23) options(blank-line-spacer)
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
Document[0, 158]
  SimTocBlock[0, 142] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 142]
      Heading[72, 83] textOpen:[72, 73, "#"] text:[74, 83, "Heading 1"]
      BulletList[84, 142] isTight
        BulletListItem[84, 98] open:[84, 85, "-"] isTight
          Paragraph[86, 98]
            Text[86, 97] chars:[86, 97, "afdas … dsadf"]
        BulletListItem[98, 142] open:[98, 99, "-"] isTight
          Paragraph[100, 109]
            Text[100, 108] chars:[100, 108, "asfdasfd"]
          BulletList[113, 142] isTight
            BulletListItem[113, 127] open:[113, 114, "-"] isTight
              Paragraph[115, 127]
                Text[115, 126] chars:[115, 126, "afdas … dsadf"]
            BulletListItem[131, 142] open:[131, 132, "-"] isTight
              Paragraph[133, 142]
                Text[133, 141] chars:[133, 141, "asfdasfd"]
  BulletList[147, 158] isTight
    BulletListItem[147, 158] open:[147, 148, "-"] isTight
      Paragraph[149, 158]
        Text[149, 157] chars:[149, 157, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example(SimToc: 24) options(blank-line-spacer)
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
  SimTocBlock[0, 130] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 130]
      HtmlBlock[71, 130]
  IndentedCodeBlock[141, 164]
  HtmlBlock[164, 179]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 25) options(blank-line-spacer)
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
  SimTocBlock[0, 187] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 187]
      HtmlBlock[71, 187]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 26) options(blank-line-spacer)
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
Document[0, 188]
  SimTocBlock[0, 188] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 188]
      HtmlBlock[72, 188]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 27) options(blank-line-spacer)
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
<div>
  <h1>Content</h1>
  <ul>
      <li>blah blah</li>  
      <li>blah blah</li>  
  </ul>
</div>
## Heading 1.1
.
Document[0, 189]
  SimTocBlock[0, 71] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
  HtmlBlock[73, 189]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 28) options(blank-line-spacer)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
<div>
  <h1>Content</h1>
</div>
- afdasfdsadf
- asfdasfd
.
.
Document[0, 128]
  SimTocBlock[0, 128] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 128]
      HtmlBlock[71, 128]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 29) options(blank-line-spacer)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  

<div>
  <h1>Content</h1>
</div>
- afdasfdsadf
- asfdasfd
.
.
Document[0, 129]
  SimTocBlock[0, 129] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 129]
      HtmlBlock[72, 129]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 30) options(blank-line-spacer)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  


<div>
  <h1>Content</h1>
</div>
- afdasfdsadf
- asfdasfd
.
<div>
  <h1>Content</h1>
</div>
- afdasfdsadf
- asfdasfd
.
Document[0, 130]
  SimTocBlock[0, 71] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
  HtmlBlock[73, 130]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 31) options(blank-line-spacer)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
<div>
  <h1>Content</h1>
</div>

<div>
  <h1>Content</h1>
</div>
.
<div>
  <h1>Content</h1>
</div>
.
Document[0, 136]
  SimTocBlock[0, 103] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 103]
      HtmlBlock[71, 103]
  HtmlBlock[104, 136]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 32) options(blank-line-spacer)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  

<div>
  <h1>Content</h1>
</div>

<div>
  <h1>Content</h1>
</div>
.
<div>
  <h1>Content</h1>
</div>
.
Document[0, 137]
  SimTocBlock[0, 104] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 104]
      HtmlBlock[72, 104]
  HtmlBlock[105, 137]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 33) options(blank-line-spacer)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  


<div>
  <h1>Content</h1>
</div>

<div>
  <h1>Content</h1>
</div>
.
<div>
  <h1>Content</h1>
</div>
<div>
  <h1>Content</h1>
</div>
.
Document[0, 138]
  SimTocBlock[0, 71] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
  HtmlBlock[73, 105]
  HtmlBlock[106, 138]
````````````````````````````````


Default rendering with emphasis

```````````````````````````````` example SimToc: 34
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
  SimTocBlock[0, 10] openingMarker:[0, 1] tocKeyword:[1, 4] closingMarker:[4, 6] anchorMarker:[6, 7, "#"]
  Heading[11, 36] textOpen:[11, 12, "#"] text:[13, 36, "Heading **some bold** 1"]
    Text[13, 21] chars:[13, 21, "Heading "]
    StrongEmphasis[21, 34] textOpen:[21, 23, "**"] text:[23, 32, "some bold"] textClose:[32, 34, "**"]
      Text[23, 32] chars:[23, 32, "some bold"]
    Text[34, 36] chars:[34, 36, " 1"]
  Heading[37, 65] textOpen:[37, 39, "##"] text:[40, 65, "Heading 1.1 _some italic_"]
    Text[40, 52] chars:[40, 52, "Headi …  1.1 "]
    Emphasis[52, 65] textOpen:[52, 53, "_"] text:[53, 64, "some italic"] textClose:[64, 65, "_"]
      Text[53, 64] chars:[53, 64, "some  … talic"]
  Heading[66, 83] textOpen:[66, 69, "###"] text:[70, 83, "Heading 1.1.1"]
    Text[70, 83] chars:[70, 83, "Headi … 1.1.1"]
  Heading[84, 125] textOpen:[84, 87, "###"] text:[88, 125, "Heading 1.1.2  **_some bold italic_**"]
    Text[88, 103] chars:[88, 103, "Headi … 1.2  "]
    StrongEmphasis[103, 125] textOpen:[103, 105, "**"] text:[105, 123, "_some bold italic_"] textClose:[123, 125, "**"]
      Emphasis[105, 123] textOpen:[105, 106, "_"] text:[106, 122, "some bold italic"] textClose:[122, 123, "_"]
        Text[106, 122] chars:[106, 122, "some  … talic"]
````````````````````````````````


With option nodes in the ast

```````````````````````````````` example(SimToc: 35) options(with-option-list)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: # 
.
.
Document[0, 70]
  SimTocBlock[0, 70] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocOptionList[5, 64]
      SimTocOption[5, 19] chars:[5, 19, "level … b,c,d"]
      SimTocOption[20, 24] chars:[20, 24, "html"]
      SimTocOption[25, 33] chars:[25, 33, "markdown"]
      SimTocOption[34, 38] chars:[34, 38, "text"]
      SimTocOption[39, 48] chars:[39, 48, "formatted"]
      SimTocOption[49, 55] chars:[49, 55, "bullet"]
      SimTocOption[56, 64] chars:[56, 64, "numbered"]
````````````````````````````````


options, empty title

```````````````````````````````` example SimToc: 36
[TOC levels=3]:# ""
[TOC levels=3]:# ''
.
.
Document[0, 40]
  SimTocBlock[0, 20] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 13] closingMarker:[13, 15] anchorMarker:[15, 16, "#"] openingTitleMarker:[17, 18, "\""] closingTitleMarker:[18, 19, "\""]
  SimTocBlock[20, 40] openingMarker:[20, 21] tocKeyword:[21, 24] style:[25, 33] closingMarker:[33, 35] anchorMarker:[35, 36, "#"] openingTitleMarker:[37, 38, "'"] closingTitleMarker:[38, 39, "'"]
````````````````````````````````


options, title

```````````````````````````````` example SimToc: 37
[TOC levels=3]:# "title"
[TOC levels=3]:# 'title'
.
.
Document[0, 50]
  SimTocBlock[0, 25] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 13] closingMarker:[13, 15] anchorMarker:[15, 16, "#"] openingTitleMarker:[17, 18, "\""] title:[18, 23, "title"] closingTitleMarker:[23, 24, "\""]
  SimTocBlock[25, 50] openingMarker:[25, 26] tocKeyword:[26, 29] style:[30, 38] closingMarker:[38, 40] anchorMarker:[40, 41, "#"] openingTitleMarker:[42, 43, "'"] title:[43, 48, "title"] closingTitleMarker:[48, 49, "'"]
````````````````````````````````


options, markers, empty title

```````````````````````````````` example SimToc: 38
[TOC levels=3]:# "## "
[TOC levels=3]:# '## '
.
.
Document[0, 46]
  SimTocBlock[0, 23] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 13] closingMarker:[13, 15] anchorMarker:[15, 16, "#"] openingTitleMarker:[17, 18, "\""] title:[18, 21, "## "] closingTitleMarker:[21, 22, "\""]
  SimTocBlock[23, 46] openingMarker:[23, 24] tocKeyword:[24, 27] style:[28, 36] closingMarker:[36, 38] anchorMarker:[38, 39, "#"] openingTitleMarker:[40, 41, "'"] title:[41, 44, "## "] closingTitleMarker:[44, 45, "'"]
````````````````````````````````


options, markers, title

```````````````````````````````` example SimToc: 39
[TOC levels=3]:# "##title"
[TOC levels=3]:# '##title'
.
.
Document[0, 54]
  SimTocBlock[0, 27] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 13] closingMarker:[13, 15] anchorMarker:[15, 16, "#"] openingTitleMarker:[17, 18, "\""] title:[18, 25, "##title"] closingTitleMarker:[25, 26, "\""]
  SimTocBlock[27, 54] openingMarker:[27, 28] tocKeyword:[28, 31] style:[32, 40] closingMarker:[40, 42] anchorMarker:[42, 43, "#"] openingTitleMarker:[44, 45, "'"] title:[45, 52, "##title"] closingTitleMarker:[52, 53, "'"]
````````````````````````````````


options, markers, title

```````````````````````````````` example SimToc: 40
[TOC levels=3]:# "## title"
[TOC levels=3]:# '## title'
.
.
Document[0, 56]
  SimTocBlock[0, 28] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 13] closingMarker:[13, 15] anchorMarker:[15, 16, "#"] openingTitleMarker:[17, 18, "\""] title:[18, 26, "## title"] closingTitleMarker:[26, 27, "\""]
  SimTocBlock[28, 56] openingMarker:[28, 29] tocKeyword:[29, 32] style:[33, 41] closingMarker:[41, 43] anchorMarker:[43, 44, "#"] openingTitleMarker:[45, 46, "'"] title:[46, 54, "## title"] closingTitleMarker:[54, 55, "'"]
````````````````````````````````


options, title with escaped chars

```````````````````````````````` example SimToc: 41
## Header 2
### Header 3

[TOC levels=3]:# "title\"s"
[TOC levels=3]:# 'title\'s'
.
<h2 id="header-2">Header 2</h2>
<h3 id="header-3">Header 3</h3>
<div><h1>title&quot;s</h1>
  <ul>
    <li><a href="#header-3">Header 3</a></li>
  </ul>
</div>
<div><h1>title's</h1>
  <ul>
    <li><a href="#header-3">Header 3</a></li>
  </ul>
</div>
.
Document[0, 82]
  Heading[0, 11] textOpen:[0, 2, "##"] text:[3, 11, "Header 2"]
    Text[3, 11] chars:[3, 11, "Header 2"]
  Heading[12, 24] textOpen:[12, 15, "###"] text:[16, 24, "Header 3"]
    Text[16, 24] chars:[16, 24, "Header 3"]
  SimTocBlock[26, 54] openingMarker:[26, 27] tocKeyword:[27, 30] style:[31, 39] closingMarker:[39, 41] anchorMarker:[41, 42, "#"] openingTitleMarker:[43, 44, "\""] title:[44, 52, "title\\"s"] closingTitleMarker:[52, 53, "\""]
  SimTocBlock[54, 82] openingMarker:[54, 55] tocKeyword:[55, 58] style:[59, 67] closingMarker:[67, 69] anchorMarker:[69, 70, "#"] openingTitleMarker:[71, 72, "'"] title:[72, 80, "title\'s"] closingTitleMarker:[80, 81, "'"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
## Header 2
### Header 3

[TOC levels=3]:# "titles"
.
<h2 id="header-2" md-pos="3-11">Header 2</h2>
<h3 id="header-3" md-pos="16-24">Header 3</h3>
<div md-pos="26-51"><h1>titles</h1>
  <ul>
    <li><a href="#header-3">Header 3</a></li>
  </ul>
</div>
.
Document[0, 52]
  Heading[0, 11] textOpen:[0, 2, "##"] text:[3, 11, "Header 2"]
    Text[3, 11] chars:[3, 11, "Header 2"]
  Heading[12, 24] textOpen:[12, 15, "###"] text:[16, 24, "Header 3"]
    Text[16, 24] chars:[16, 24, "Header 3"]
  SimTocBlock[26, 52] openingMarker:[26, 27] tocKeyword:[27, 30] style:[31, 39] closingMarker:[39, 41] anchorMarker:[41, 42, "#"] openingTitleMarker:[43, 44, "\""] title:[44, 50, "titles"] closingTitleMarker:[50, 51, "\""]
````````````````````````````````


