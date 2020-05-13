---
title: SimToc Extension Spec
author: Vladimir Schneider
version: 0.9.0
date: '2016-06-30'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## SimToc

The Sim TOC tag has the following format: `[TOC style]: # "Title"` and includes all following
lines until a blank line. <!--SimTocContent-->

Lines after the TOC tag are added to the `SimTocContent` child node of the SimToc block.

The intention for this tag is to have the `SimTocContent` updated to reflect the content of the
document.

1. `style` consists of space separated list of options:

   * `levels=levelList` where level list is a comma separated list of levels or ranges. Default
     is to include heading levels 2 and 3. If a range is specified using `-` as separator
     between numeric values then the included range is from first numeric to last numeric
     inclusive. If first numeric is missing then 1 will be used in its place. If last numeric is
     missing then 6 will be used in its place.
   
     Examples:
     * `levels=1` include level 1 only, same as `levels=1,1`
     * `levels=2` include level 2 only, same as `levels=2,2`
     * `levels=3` include levels 2 and 3
     * `levels=4` include levels 2,3 and 4
     * `levels=2-4` include levels 2,3 and 4. same as `levels=4`
     * `levels=2-4,5` include levels 2,3,4 and 5
     * `levels=1,3` include levels 1 and 3
     * `levels=-3` include levels 1 to 3
     * `levels=3-` include levels 3 to 6

   * `html` generate HTML version of the TOC

   * `markdown` generate Markdown version of the TOC

   * `text` to only include the text of the heading

   * `formatted` to include text and inline formatting

   * `hierarchy` to render as hierarchical list in order of appearance in the document

   * `flat` to render as a flat list in order of appearance in the document

   * `reversed` to render as a flat list in order of appearance in the document

   * `sort-up` to render as a flat list sorted alphabetically by heading text only, no inlines

   * `sort-down` to render as a flat list sorted reversed alphabetically by heading text only,
     no inlines

   * `bullet` to use a bullet list for the TOC items

   * `numbered` to use a numbered list for TOC items

2. `"Title"` specifies the text for the table of contents heading. If omitted or blank then no
   heading will be generated for the table of contents. `#` prefix in the title will specify the
   header level to use for the heading above the table of contents listing. If no `#` prefix is

no spaces after first bracket

```````````````````````````````` example SimToc: 1
[ TOC]: #  
.
.
Document[0, 11]
  Reference[0, 9] refOpen:[0, 1, "["] ref:[2, 5, "TOC"] refClose:[5, 7, "]:"] url:[8, 9, "#"]
````````````````````````````````


plain is ok

```````````````````````````````` example SimToc: 2
[TOC]: #  
.
.
Document[0, 10]
  SimTocBlock[0, 10] openingMarker:[0, 1] tocKeyword:[1, 4] closingMarker:[4, 6] anchorMarker:[7, 8, "#"]
````````````````````````````````


Default case sensitive

```````````````````````````````` example SimToc: 3
[toc]: #

# Heading **some bold** 1
## Heading 1.1 _some italic_

.
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
.
Document[0, 66]
  Reference[0, 8] refOpen:[0, 1, "["] ref:[1, 4, "toc"] refClose:[4, 6, "]:"] url:[7, 8, "#"]
  Heading[10, 35] textOpen:[10, 11, "#"] text:[12, 35, "Heading **some bold** 1"]
    Text[12, 20] chars:[12, 20, "Heading "]
    StrongEmphasis[20, 33] textOpen:[20, 22, "**"] text:[22, 31, "some bold"] textClose:[31, 33, "**"]
      Text[22, 31] chars:[22, 31, "some bold"]
    Text[33, 35] chars:[33, 35, " 1"]
  Heading[36, 64] textOpen:[36, 38, "##"] text:[39, 64, "Heading 1.1 _some italic_"]
    Text[39, 51] chars:[39, 51, "Headi …  1.1 "]
    Emphasis[51, 64] textOpen:[51, 52, "_"] text:[52, 63, "some italic"] textClose:[63, 64, "_"]
      Text[52, 63] chars:[52, 63, "some  … talic"]
````````````````````````````````


case insensitive

```````````````````````````````` example(SimToc: 4) options(not-case-sensitive)
[toc]: # 

# Heading **some bold** 1
## Heading 1.1 _some italic_

.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a></li>
  </ul>
</div>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
.
Document[0, 67]
  SimTocBlock[0, 10] openingMarker:[0, 1] tocKeyword:[1, 4] closingMarker:[4, 6] anchorMarker:[7, 8, "#"]
  Heading[11, 36] textOpen:[11, 12, "#"] text:[13, 36, "Heading **some bold** 1"]
    Text[13, 21] chars:[13, 21, "Heading "]
    StrongEmphasis[21, 34] textOpen:[21, 23, "**"] text:[23, 32, "some bold"] textClose:[32, 34, "**"]
      Text[23, 32] chars:[23, 32, "some bold"]
    Text[34, 36] chars:[34, 36, " 1"]
  Heading[37, 65] textOpen:[37, 39, "##"] text:[40, 65, "Heading 1.1 _some italic_"]
    Text[40, 52] chars:[40, 52, "Headi …  1.1 "]
    Emphasis[52, 65] textOpen:[52, 53, "_"] text:[53, 64, "some italic"] textClose:[64, 65, "_"]
      Text[53, 64] chars:[53, 64, "some  … talic"]
````````````````````````````````


accepts all text for style

```````````````````````````````` example SimToc: 5
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
.
.
Document[0, 70]
  SimTocBlock[0, 70] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 6
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  

# Heading 1
.
<h1 id="heading-1">Heading 1</h1>
.
Document[0, 83]
  SimTocBlock[0, 71] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
  Heading[72, 83] textOpen:[72, 73, "#"] text:[74, 83, "Heading 1"]
    Text[74, 83] chars:[74, 83, "Heading 1"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 7
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  

- asfdasfd
.
<ul>
  <li>asfdasfd</li>
</ul>
.
Document[0, 82]
  SimTocBlock[0, 71] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
  BulletList[72, 82] isTight
    BulletListItem[72, 82] open:[72, 73, "-"] isTight
      Paragraph[74, 82]
        Text[74, 82] chars:[74, 82, "asfdasfd"]
````````````````````````````````


```````````````````````````````` example SimToc: 8
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  

<div>
</div>
.
<div>
</div>
.
Document[0, 84]
  SimTocBlock[0, 71] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
  HtmlBlock[72, 84]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 9
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
# Heading 1
## Heading 1.1
- afdasfdsadf
- asfdasfd
.
<div>
  <h1>Table of Contents</h1>
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
Document[0, 122]
  SimTocBlock[0, 82] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 82]
      Heading[71, 82] textOpen:[71, 72, "#"] text:[73, 82, "Heading 1"]
  Heading[83, 97] textOpen:[83, 85, "##"] text:[86, 97, "Heading 1.1"]
    Text[86, 97] chars:[86, 97, "Headi … g 1.1"]
  BulletList[98, 122] isTight
    BulletListItem[98, 112] open:[98, 99, "-"] isTight
      Paragraph[100, 112]
        Text[100, 111] chars:[100, 111, "afdas … dsadf"]
    BulletListItem[112, 122] open:[112, 113, "-"] isTight
      Paragraph[114, 122]
        Text[114, 122] chars:[114, 122, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 10
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
Document[0, 108]
  SimTocBlock[0, 82] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 82]
      Heading[71, 82] textOpen:[71, 72, "#"] text:[73, 82, "Heading 1"]
  BulletList[84, 108] isTight
    BulletListItem[84, 98] open:[84, 85, "-"] isTight
      Paragraph[86, 98]
        Text[86, 97] chars:[86, 97, "afdas … dsadf"]
    BulletListItem[98, 108] open:[98, 99, "-"] isTight
      Paragraph[100, 108]
        Text[100, 108] chars:[100, 108, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 11
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
- afdasfdsadf
- asfdasfd

# Heading 1
.
<h1 id="heading-1">Heading 1</h1>
.
Document[0, 108]
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

```````````````````````````````` example SimToc: 12
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
Document[0, 156]
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
  BulletList[146, 156] isTight
    BulletListItem[146, 156] open:[146, 147, "-"] isTight
      Paragraph[148, 156]
        Text[148, 156] chars:[148, 156, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 13
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
Document[0, 178]
  SimTocBlock[0, 130] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 130]
      HtmlBlock[71, 130]
  IndentedCodeBlock[141, 164]
  HtmlBlock[164, 178]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 14
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
Document[0, 186]
  SimTocBlock[0, 186] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 186]
      HtmlBlock[71, 186]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 15
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
<div>
  <h1>Content</h1>
</div>
- afdasfdsadf
- asfdasfd
.
.
Document[0, 127]
  SimTocBlock[0, 127] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 127]
      HtmlBlock[71, 127]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example SimToc: 16
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
<div>
  <h1>Content</h1>
</div>
<div>
  <h1>Content</h1>
</div>
.
.
Document[0, 134]
  SimTocBlock[0, 134] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 134]
      HtmlBlock[71, 134]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 17) options(blank-line-spacer)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  

# Heading 1
.
.
Document[0, 83]
  SimTocBlock[0, 83] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 83]
      Heading[72, 83] textOpen:[72, 73, "#"] text:[74, 83, "Heading 1"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 18) options(blank-line-spacer)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  

- asfdasfd
.
.
Document[0, 82]
  SimTocBlock[0, 82] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 82]
      BulletList[72, 82] isTight
        BulletListItem[72, 82] open:[72, 73, "-"] isTight
          Paragraph[74, 82]
            Text[74, 82] chars:[74, 82, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 19) options(blank-line-spacer)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  

<div>
</div>
.
.
Document[0, 84]
  SimTocBlock[0, 84] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 84]
      HtmlBlock[72, 84]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 20) options(blank-line-spacer)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
# Heading 1
## Heading 1.1
- afdasfdsadf
- asfdasfd
.
<div>
  <h1>Table of Contents</h1>
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
Document[0, 122]
  SimTocBlock[0, 82] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 82]
      Heading[71, 82] textOpen:[71, 72, "#"] text:[73, 82, "Heading 1"]
  Heading[83, 97] textOpen:[83, 85, "##"] text:[86, 97, "Heading 1.1"]
    Text[86, 97] chars:[86, 97, "Headi … g 1.1"]
  BulletList[98, 122] isTight
    BulletListItem[98, 112] open:[98, 99, "-"] isTight
      Paragraph[100, 112]
        Text[100, 111] chars:[100, 111, "afdas … dsadf"]
    BulletListItem[112, 122] open:[112, 113, "-"] isTight
      Paragraph[114, 122]
        Text[114, 122] chars:[114, 122, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 21) options(blank-line-spacer)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  

# Heading 1
## Heading 1.1
- afdasfdsadf
- asfdasfd
.
<div>
  <h1>Table of Contents</h1>
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
  SimTocBlock[0, 83] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 83]
      Heading[72, 83] textOpen:[72, 73, "#"] text:[74, 83, "Heading 1"]
  Heading[84, 98] textOpen:[84, 86, "##"] text:[87, 98, "Heading 1.1"]
    Text[87, 98] chars:[87, 98, "Headi … g 1.1"]
  BulletList[99, 123] isTight
    BulletListItem[99, 113] open:[99, 100, "-"] isTight
      Paragraph[101, 113]
        Text[101, 112] chars:[101, 112, "afdas … dsadf"]
    BulletListItem[113, 123] open:[113, 114, "-"] isTight
      Paragraph[115, 123]
        Text[115, 123] chars:[115, 123, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 22) options(blank-line-spacer)
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
Document[0, 108]
  SimTocBlock[0, 82] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 82]
      Heading[71, 82] textOpen:[71, 72, "#"] text:[73, 82, "Heading 1"]
  BulletList[84, 108] isTight
    BulletListItem[84, 98] open:[84, 85, "-"] isTight
      Paragraph[86, 98]
        Text[86, 97] chars:[86, 97, "afdas … dsadf"]
    BulletListItem[98, 108] open:[98, 99, "-"] isTight
      Paragraph[100, 108]
        Text[100, 108] chars:[100, 108, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 23) options(blank-line-spacer)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
- afdasfdsadf
- asfdasfd

# Heading 1
.
<h1 id="heading-1">Heading 1</h1>
.
Document[0, 108]
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

```````````````````````````````` example(SimToc: 24) options(blank-line-spacer)
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
Document[0, 156]
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
  BulletList[146, 156] isTight
    BulletListItem[146, 156] open:[146, 147, "-"] isTight
      Paragraph[148, 156]
        Text[148, 156] chars:[148, 156, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 25) options(blank-line-spacer)
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
  BulletList[147, 157] isTight
    BulletListItem[147, 157] open:[147, 148, "-"] isTight
      Paragraph[149, 157]
        Text[149, 157] chars:[149, 157, "asfdasfd"]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, no blank lines

```````````````````````````````` example(SimToc: 26) options(blank-line-spacer)
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
Document[0, 178]
  SimTocBlock[0, 130] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 130]
      HtmlBlock[71, 130]
  IndentedCodeBlock[141, 164]
  HtmlBlock[164, 178]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 27) options(blank-line-spacer)
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
Document[0, 186]
  SimTocBlock[0, 186] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 186]
      HtmlBlock[71, 186]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 28) options(blank-line-spacer)
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
      HtmlBlock[72, 187]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 29) options(blank-line-spacer)
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
Document[0, 188]
  SimTocBlock[0, 71] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
  HtmlBlock[73, 188]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 30) options(blank-line-spacer)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
<div>
  <h1>Content</h1>
</div>
- afdasfdsadf
- asfdasfd
.
.
Document[0, 127]
  SimTocBlock[0, 127] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 127]
      HtmlBlock[71, 127]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 31) options(blank-line-spacer)
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
      HtmlBlock[72, 128]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 32) options(blank-line-spacer)
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
Document[0, 129]
  SimTocBlock[0, 71] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
  HtmlBlock[73, 129]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 33) options(blank-line-spacer)
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
Document[0, 135]
  SimTocBlock[0, 103] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 103]
      HtmlBlock[71, 103]
  HtmlBlock[104, 135]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 34) options(blank-line-spacer)
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
  SimTocBlock[0, 104] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
    SimTocContent[71, 104]
      HtmlBlock[72, 104]
  HtmlBlock[105, 136]
````````````````````````````````


Absorbs only valid combinations, HTML or Heading with List, with one leading blank line

```````````````````````````````` example(SimToc: 35) options(blank-line-spacer)
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
Document[0, 137]
  SimTocBlock[0, 71] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
  HtmlBlock[73, 105]
  HtmlBlock[106, 137]
````````````````````````````````


Default rendering with emphasis

```````````````````````````````` example SimToc: 36
[TOC]:#  

# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**
.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
      <ul>
        <li><a href="#heading-111">Heading 1.1.1</a></li>
        <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
      </ul>
    </li>
  </ul>
</div>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
.
Document[0, 125]
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


Default rendering with div class

```````````````````````````````` example(SimToc: 37) options(div-class)
[TOC]:#  

# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**
.
<div class="content-class">
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
      <ul>
        <li><a href="#heading-111">Heading 1.1.1</a></li>
        <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
      </ul>
    </li>
  </ul>
</div>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
.
Document[0, 125]
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


Default rendering with div class, list class

```````````````````````````````` example(SimToc: 38) options(div-class, list-class)
[TOC]:#  

# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**
.
<div class="content-class">
  <h1>Table of Contents</h1>
  <ul class="list-class">
    <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
      <ul>
        <li><a href="#heading-111">Heading 1.1.1</a></li>
        <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
      </ul>
    </li>
  </ul>
</div>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
.
Document[0, 125]
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


Default rendering with list class

```````````````````````````````` example(SimToc: 39) options(list-class)
[TOC]:#  

# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**
.
<div>
  <h1>Table of Contents</h1>
  <ul class="list-class">
    <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
      <ul>
        <li><a href="#heading-111">Heading 1.1.1</a></li>
        <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
      </ul>
    </li>
  </ul>
</div>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
.
Document[0, 125]
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

```````````````````````````````` example(SimToc: 40) options(with-option-list)
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: # 
.
.
Document[0, 69]
  SimTocBlock[0, 69] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68, "#"]
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

```````````````````````````````` example SimToc: 41
[TOC levels=3]:# ""
[TOC levels=3]:# ''
.
.
Document[0, 39]
  SimTocBlock[0, 20] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 13] closingMarker:[13, 15] anchorMarker:[15, 16, "#"] openingTitleMarker:[17, 18, "\""] title:[18, 18] closingTitleMarker:[18, 19, "\""]
  SimTocBlock[20, 39] openingMarker:[20, 21] tocKeyword:[21, 24] style:[25, 33] closingMarker:[33, 35] anchorMarker:[35, 36, "#"] openingTitleMarker:[37, 38, "'"] title:[38, 38] closingTitleMarker:[38, 39, "'"]
````````````````````````````````


options, title

```````````````````````````````` example SimToc: 42
[TOC levels=3]:# "title"
[TOC levels=3]:# 'title'
.
.
Document[0, 49]
  SimTocBlock[0, 25] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 13] closingMarker:[13, 15] anchorMarker:[15, 16, "#"] openingTitleMarker:[17, 18, "\""] title:[18, 23, "title"] closingTitleMarker:[23, 24, "\""]
  SimTocBlock[25, 49] openingMarker:[25, 26] tocKeyword:[26, 29] style:[30, 38] closingMarker:[38, 40] anchorMarker:[40, 41, "#"] openingTitleMarker:[42, 43, "'"] title:[43, 48, "title"] closingTitleMarker:[48, 49, "'"]
````````````````````````````````


options, markers, empty title

```````````````````````````````` example SimToc: 43
[TOC levels=3]:# "## "
[TOC levels=3]:# '## '
.
.
Document[0, 45]
  SimTocBlock[0, 23] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 13] closingMarker:[13, 15] anchorMarker:[15, 16, "#"] openingTitleMarker:[17, 18, "\""] title:[18, 21, "## "] closingTitleMarker:[21, 22, "\""]
  SimTocBlock[23, 45] openingMarker:[23, 24] tocKeyword:[24, 27] style:[28, 36] closingMarker:[36, 38] anchorMarker:[38, 39, "#"] openingTitleMarker:[40, 41, "'"] title:[41, 44, "## "] closingTitleMarker:[44, 45, "'"]
````````````````````````````````


options, markers, title

```````````````````````````````` example SimToc: 44
[TOC levels=3]:# "##title"
[TOC levels=3]:# '##title'
.
.
Document[0, 53]
  SimTocBlock[0, 27] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 13] closingMarker:[13, 15] anchorMarker:[15, 16, "#"] openingTitleMarker:[17, 18, "\""] title:[18, 25, "##title"] closingTitleMarker:[25, 26, "\""]
  SimTocBlock[27, 53] openingMarker:[27, 28] tocKeyword:[28, 31] style:[32, 40] closingMarker:[40, 42] anchorMarker:[42, 43, "#"] openingTitleMarker:[44, 45, "'"] title:[45, 52, "##title"] closingTitleMarker:[52, 53, "'"]
````````````````````````````````


options, markers, title

```````````````````````````````` example SimToc: 45
[TOC levels=3]:# "## title"
[TOC levels=3]:# '## title'
.
.
Document[0, 55]
  SimTocBlock[0, 28] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 13] closingMarker:[13, 15] anchorMarker:[15, 16, "#"] openingTitleMarker:[17, 18, "\""] title:[18, 26, "## title"] closingTitleMarker:[26, 27, "\""]
  SimTocBlock[28, 55] openingMarker:[28, 29] tocKeyword:[29, 32] style:[33, 41] closingMarker:[41, 43] anchorMarker:[43, 44, "#"] openingTitleMarker:[45, 46, "'"] title:[46, 54, "## title"] closingTitleMarker:[54, 55, "'"]
````````````````````````````````


options, title with escaped chars

```````````````````````````````` example SimToc: 46
## Header 2
### Header 3

[TOC levels=3]:# "title\"s"
[TOC levels=3]:# 'title\'s'
.
<h2 id="header-2">Header 2</h2>
<h3 id="header-3">Header 3</h3>
<div>
  <h1>title&quot;s</h1>
  <ul>
    <li><a href="#header-2">Header 2</a>
      <ul>
        <li><a href="#header-3">Header 3</a></li>
      </ul>
    </li>
  </ul>
</div>
<div>
  <h1>title's</h1>
  <ul>
    <li><a href="#header-2">Header 2</a>
      <ul>
        <li><a href="#header-3">Header 3</a></li>
      </ul>
    </li>
  </ul>
</div>
.
Document[0, 81]
  Heading[0, 11] textOpen:[0, 2, "##"] text:[3, 11, "Header 2"]
    Text[3, 11] chars:[3, 11, "Header 2"]
  Heading[12, 24] textOpen:[12, 15, "###"] text:[16, 24, "Header 3"]
    Text[16, 24] chars:[16, 24, "Header 3"]
  SimTocBlock[26, 54] openingMarker:[26, 27] tocKeyword:[27, 30] style:[31, 39] closingMarker:[39, 41] anchorMarker:[41, 42, "#"] openingTitleMarker:[43, 44, "\""] title:[44, 52, "title\\"s"] closingTitleMarker:[52, 53, "\""]
  SimTocBlock[54, 81] openingMarker:[54, 55] tocKeyword:[55, 58] style:[59, 67] closingMarker:[67, 69] anchorMarker:[69, 70, "#"] openingTitleMarker:[71, 72, "'"] title:[72, 80, "title\'s"] closingTitleMarker:[80, 81, "'"]
````````````````````````````````


start with missing first level

```````````````````````````````` example SimToc: 47
[TOC levels=1-6]:#  


## Header 1
# Header 2
### Header 3
## Header 4
# Header 5
.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#header-1">Header 1</a></li>
    <li><a href="#header-2">Header 2</a>
      <ul>
        <li><a href="#header-3">Header 3</a></li>
        <li><a href="#header-4">Header 4</a></li>
      </ul>
    </li>
    <li><a href="#header-5">Header 5</a></li>
  </ul>
</div>
<h2 id="header-1">Header 1</h2>
<h1 id="header-2">Header 2</h1>
<h3 id="header-3">Header 3</h3>
<h2 id="header-4">Header 4</h2>
<h1 id="header-5">Header 5</h1>
.
Document[0, 81]
  SimTocBlock[0, 21] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 15] closingMarker:[15, 17] anchorMarker:[17, 18, "#"]
  Heading[23, 34] textOpen:[23, 25, "##"] text:[26, 34, "Header 1"]
    Text[26, 34] chars:[26, 34, "Header 1"]
  Heading[35, 45] textOpen:[35, 36, "#"] text:[37, 45, "Header 2"]
    Text[37, 45] chars:[37, 45, "Header 2"]
  Heading[46, 58] textOpen:[46, 49, "###"] text:[50, 58, "Header 3"]
    Text[50, 58] chars:[50, 58, "Header 3"]
  Heading[59, 70] textOpen:[59, 61, "##"] text:[62, 70, "Header 4"]
    Text[62, 70] chars:[62, 70, "Header 4"]
  Heading[71, 81] textOpen:[71, 72, "#"] text:[73, 81, "Header 5"]
    Text[73, 81] chars:[73, 81, "Header 5"]
````````````````````````````````


start with missing first 2 levels

```````````````````````````````` example SimToc: 48
[TOC levels=1-6]:#  


### Header 1
## Header 2
### Header 3
# Header 4
.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#header-1">Header 1</a></li>
    <li><a href="#header-2">Header 2</a>
      <ul>
        <li><a href="#header-3">Header 3</a></li>
      </ul>
    </li>
    <li><a href="#header-4">Header 4</a></li>
  </ul>
</div>
<h3 id="header-1">Header 1</h3>
<h2 id="header-2">Header 2</h2>
<h3 id="header-3">Header 3</h3>
<h1 id="header-4">Header 4</h1>
.
Document[0, 71]
  SimTocBlock[0, 21] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 15] closingMarker:[15, 17] anchorMarker:[17, 18, "#"]
  Heading[23, 35] textOpen:[23, 26, "###"] text:[27, 35, "Header 1"]
    Text[27, 35] chars:[27, 35, "Header 1"]
  Heading[36, 47] textOpen:[36, 38, "##"] text:[39, 47, "Header 2"]
    Text[39, 47] chars:[39, 47, "Header 2"]
  Heading[48, 60] textOpen:[48, 51, "###"] text:[52, 60, "Header 3"]
    Text[52, 60] chars:[52, 60, "Header 3"]
  Heading[61, 71] textOpen:[61, 62, "#"] text:[63, 71, "Header 4"]
    Text[63, 71] chars:[63, 71, "Header 4"]
````````````````````````````````


Text only style

```````````````````````````````` example SimToc: 49
[TOC text]:#


# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**

.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#heading-11-some-italic">Heading 1.1 some italic</a>
      <ul>
        <li><a href="#heading-111">Heading 1.1.1</a></li>
        <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  some bold italic</a></li>
      </ul>
    </li>
  </ul>
</div>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
.
Document[0, 131]
  SimTocBlock[0, 13] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 9] closingMarker:[9, 11] anchorMarker:[11, 12, "#"]
  Heading[15, 40] textOpen:[15, 16, "#"] text:[17, 40, "Heading **some bold** 1"]
    Text[17, 25] chars:[17, 25, "Heading "]
    StrongEmphasis[25, 38] textOpen:[25, 27, "**"] text:[27, 36, "some bold"] textClose:[36, 38, "**"]
      Text[27, 36] chars:[27, 36, "some bold"]
    Text[38, 40] chars:[38, 40, " 1"]
  Heading[41, 69] textOpen:[41, 43, "##"] text:[44, 69, "Heading 1.1 _some italic_"]
    Text[44, 56] chars:[44, 56, "Headi …  1.1 "]
    Emphasis[56, 69] textOpen:[56, 57, "_"] text:[57, 68, "some italic"] textClose:[68, 69, "_"]
      Text[57, 68] chars:[57, 68, "some  … talic"]
  Heading[70, 87] textOpen:[70, 73, "###"] text:[74, 87, "Heading 1.1.1"]
    Text[74, 87] chars:[74, 87, "Headi … 1.1.1"]
  Heading[88, 129] textOpen:[88, 91, "###"] text:[92, 129, "Heading 1.1.2  **_some bold italic_**"]
    Text[92, 107] chars:[92, 107, "Headi … 1.2  "]
    StrongEmphasis[107, 129] textOpen:[107, 109, "**"] text:[109, 127, "_some bold italic_"] textClose:[127, 129, "**"]
      Emphasis[109, 127] textOpen:[109, 110, "_"] text:[110, 126, "some bold italic"] textClose:[126, 127, "_"]
        Text[110, 126] chars:[110, 126, "some  … talic"]
````````````````````````````````


Text and inlines style

```````````````````````````````` example(SimToc: 50) options(text-only)
[TOC format]:#  


# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**

.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
      <ul>
        <li><a href="#heading-111">Heading 1.1.1</a></li>
        <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
      </ul>
    </li>
  </ul>
</div>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
.
Document[0, 135]
  SimTocBlock[0, 17] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 11] closingMarker:[11, 13] anchorMarker:[13, 14, "#"]
  Heading[19, 44] textOpen:[19, 20, "#"] text:[21, 44, "Heading **some bold** 1"]
    Text[21, 29] chars:[21, 29, "Heading "]
    StrongEmphasis[29, 42] textOpen:[29, 31, "**"] text:[31, 40, "some bold"] textClose:[40, 42, "**"]
      Text[31, 40] chars:[31, 40, "some bold"]
    Text[42, 44] chars:[42, 44, " 1"]
  Heading[45, 73] textOpen:[45, 47, "##"] text:[48, 73, "Heading 1.1 _some italic_"]
    Text[48, 60] chars:[48, 60, "Headi …  1.1 "]
    Emphasis[60, 73] textOpen:[60, 61, "_"] text:[61, 72, "some italic"] textClose:[72, 73, "_"]
      Text[61, 72] chars:[61, 72, "some  … talic"]
  Heading[74, 91] textOpen:[74, 77, "###"] text:[78, 91, "Heading 1.1.1"]
    Text[78, 91] chars:[78, 91, "Headi … 1.1.1"]
  Heading[92, 133] textOpen:[92, 95, "###"] text:[96, 133, "Heading 1.1.2  **_some bold italic_**"]
    Text[96, 111] chars:[96, 111, "Headi … 1.2  "]
    StrongEmphasis[111, 133] textOpen:[111, 113, "**"] text:[113, 131, "_some bold italic_"] textClose:[131, 133, "**"]
      Emphasis[113, 131] textOpen:[113, 114, "_"] text:[114, 130, "some bold italic"] textClose:[130, 131, "_"]
        Text[114, 130] chars:[114, 130, "some  … talic"]
````````````````````````````````


Text only, flat

```````````````````````````````` example(SimToc: 51) options(text-only, flat)
[TOC]:#


# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**

.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#heading-11-some-italic">Heading 1.1 some italic</a></li>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  some bold italic</a></li>
  </ul>
</div>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
.
Document[0, 126]
  SimTocBlock[0, 8] openingMarker:[0, 1] tocKeyword:[1, 4] closingMarker:[4, 6] anchorMarker:[6, 7, "#"]
  Heading[10, 35] textOpen:[10, 11, "#"] text:[12, 35, "Heading **some bold** 1"]
    Text[12, 20] chars:[12, 20, "Heading "]
    StrongEmphasis[20, 33] textOpen:[20, 22, "**"] text:[22, 31, "some bold"] textClose:[31, 33, "**"]
      Text[22, 31] chars:[22, 31, "some bold"]
    Text[33, 35] chars:[33, 35, " 1"]
  Heading[36, 64] textOpen:[36, 38, "##"] text:[39, 64, "Heading 1.1 _some italic_"]
    Text[39, 51] chars:[39, 51, "Headi …  1.1 "]
    Emphasis[51, 64] textOpen:[51, 52, "_"] text:[52, 63, "some italic"] textClose:[63, 64, "_"]
      Text[52, 63] chars:[52, 63, "some  … talic"]
  Heading[65, 82] textOpen:[65, 68, "###"] text:[69, 82, "Heading 1.1.1"]
    Text[69, 82] chars:[69, 82, "Headi … 1.1.1"]
  Heading[83, 124] textOpen:[83, 86, "###"] text:[87, 124, "Heading 1.1.2  **_some bold italic_**"]
    Text[87, 102] chars:[87, 102, "Headi … 1.2  "]
    StrongEmphasis[102, 124] textOpen:[102, 104, "**"] text:[104, 122, "_some bold italic_"] textClose:[122, 124, "**"]
      Emphasis[104, 122] textOpen:[104, 105, "_"] text:[105, 121, "some bold italic"] textClose:[121, 122, "_"]
        Text[105, 121] chars:[105, 121, "some  … talic"]
````````````````````````````````


Text and inlines, flat

```````````````````````````````` example(SimToc: 52) options(flat)
[TOC]:#


# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**

.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a></li>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
  </ul>
</div>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
.
Document[0, 126]
  SimTocBlock[0, 8] openingMarker:[0, 1] tocKeyword:[1, 4] closingMarker:[4, 6] anchorMarker:[6, 7, "#"]
  Heading[10, 35] textOpen:[10, 11, "#"] text:[12, 35, "Heading **some bold** 1"]
    Text[12, 20] chars:[12, 20, "Heading "]
    StrongEmphasis[20, 33] textOpen:[20, 22, "**"] text:[22, 31, "some bold"] textClose:[31, 33, "**"]
      Text[22, 31] chars:[22, 31, "some bold"]
    Text[33, 35] chars:[33, 35, " 1"]
  Heading[36, 64] textOpen:[36, 38, "##"] text:[39, 64, "Heading 1.1 _some italic_"]
    Text[39, 51] chars:[39, 51, "Headi …  1.1 "]
    Emphasis[51, 64] textOpen:[51, 52, "_"] text:[52, 63, "some italic"] textClose:[63, 64, "_"]
      Text[52, 63] chars:[52, 63, "some  … talic"]
  Heading[65, 82] textOpen:[65, 68, "###"] text:[69, 82, "Heading 1.1.1"]
    Text[69, 82] chars:[69, 82, "Headi … 1.1.1"]
  Heading[83, 124] textOpen:[83, 86, "###"] text:[87, 124, "Heading 1.1.2  **_some bold italic_**"]
    Text[87, 102] chars:[87, 102, "Headi … 1.2  "]
    StrongEmphasis[102, 124] textOpen:[102, 104, "**"] text:[104, 122, "_some bold italic_"] textClose:[122, 124, "**"]
      Emphasis[104, 122] textOpen:[104, 105, "_"] text:[105, 121, "some bold italic"] textClose:[121, 122, "_"]
        Text[105, 121] chars:[105, 121, "some  … talic"]
````````````````````````````````


Text and inlines, hierarchy

```````````````````````````````` example(SimToc: 53) options(flat)
[TOC hierarchy]:#


# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**

.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
      <ul>
        <li><a href="#heading-111">Heading 1.1.1</a></li>
        <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
      </ul>
    </li>
  </ul>
</div>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
.
Document[0, 136]
  SimTocBlock[0, 18] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 14] closingMarker:[14, 16] anchorMarker:[16, 17, "#"]
  Heading[20, 45] textOpen:[20, 21, "#"] text:[22, 45, "Heading **some bold** 1"]
    Text[22, 30] chars:[22, 30, "Heading "]
    StrongEmphasis[30, 43] textOpen:[30, 32, "**"] text:[32, 41, "some bold"] textClose:[41, 43, "**"]
      Text[32, 41] chars:[32, 41, "some bold"]
    Text[43, 45] chars:[43, 45, " 1"]
  Heading[46, 74] textOpen:[46, 48, "##"] text:[49, 74, "Heading 1.1 _some italic_"]
    Text[49, 61] chars:[49, 61, "Headi …  1.1 "]
    Emphasis[61, 74] textOpen:[61, 62, "_"] text:[62, 73, "some italic"] textClose:[73, 74, "_"]
      Text[62, 73] chars:[62, 73, "some  … talic"]
  Heading[75, 92] textOpen:[75, 78, "###"] text:[79, 92, "Heading 1.1.1"]
    Text[79, 92] chars:[79, 92, "Headi … 1.1.1"]
  Heading[93, 134] textOpen:[93, 96, "###"] text:[97, 134, "Heading 1.1.2  **_some bold italic_**"]
    Text[97, 112] chars:[97, 112, "Headi … 1.2  "]
    StrongEmphasis[112, 134] textOpen:[112, 114, "**"] text:[114, 132, "_some bold italic_"] textClose:[132, 134, "**"]
      Emphasis[114, 132] textOpen:[114, 115, "_"] text:[115, 131, "some bold italic"] textClose:[131, 132, "_"]
        Text[115, 131] chars:[115, 131, "some  … talic"]
````````````````````````````````


Text and inlines, flat

```````````````````````````````` example(SimToc: 54) options(flat-reversed)
[TOC]:#


# Heading **some bold** 1
## Heading 1.1 _some italic_
### Heading 1.1.2  **_some bold italic_**
### Heading 1.1.1

.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
    <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a></li>
  </ul>
</div>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
<h3 id="heading-111">Heading 1.1.1</h3>
.
Document[0, 126]
  SimTocBlock[0, 8] openingMarker:[0, 1] tocKeyword:[1, 4] closingMarker:[4, 6] anchorMarker:[6, 7, "#"]
  Heading[10, 35] textOpen:[10, 11, "#"] text:[12, 35, "Heading **some bold** 1"]
    Text[12, 20] chars:[12, 20, "Heading "]
    StrongEmphasis[20, 33] textOpen:[20, 22, "**"] text:[22, 31, "some bold"] textClose:[31, 33, "**"]
      Text[22, 31] chars:[22, 31, "some bold"]
    Text[33, 35] chars:[33, 35, " 1"]
  Heading[36, 64] textOpen:[36, 38, "##"] text:[39, 64, "Heading 1.1 _some italic_"]
    Text[39, 51] chars:[39, 51, "Headi …  1.1 "]
    Emphasis[51, 64] textOpen:[51, 52, "_"] text:[52, 63, "some italic"] textClose:[63, 64, "_"]
      Text[52, 63] chars:[52, 63, "some  … talic"]
  Heading[65, 106] textOpen:[65, 68, "###"] text:[69, 106, "Heading 1.1.2  **_some bold italic_**"]
    Text[69, 84] chars:[69, 84, "Headi … 1.2  "]
    StrongEmphasis[84, 106] textOpen:[84, 86, "**"] text:[86, 104, "_some bold italic_"] textClose:[104, 106, "**"]
      Emphasis[86, 104] textOpen:[86, 87, "_"] text:[87, 103, "some bold italic"] textClose:[103, 104, "_"]
        Text[87, 103] chars:[87, 103, "some  … talic"]
  Heading[107, 124] textOpen:[107, 110, "###"] text:[111, 124, "Heading 1.1.1"]
    Text[111, 124] chars:[111, 124, "Headi … 1.1.1"]
````````````````````````````````


Text and inlines, flat-reversed

```````````````````````````````` example(SimToc: 55) options(flat-reversed)
[TOC]:#


# Heading **some bold** 1
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_**
## Heading 1.1 _some italic_

.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
  </ul>
</div>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h3 id="heading-111">Heading 1.1.1</h3>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
.
Document[0, 126]
  SimTocBlock[0, 8] openingMarker:[0, 1] tocKeyword:[1, 4] closingMarker:[4, 6] anchorMarker:[6, 7, "#"]
  Heading[10, 35] textOpen:[10, 11, "#"] text:[12, 35, "Heading **some bold** 1"]
    Text[12, 20] chars:[12, 20, "Heading "]
    StrongEmphasis[20, 33] textOpen:[20, 22, "**"] text:[22, 31, "some bold"] textClose:[31, 33, "**"]
      Text[22, 31] chars:[22, 31, "some bold"]
    Text[33, 35] chars:[33, 35, " 1"]
  Heading[36, 53] textOpen:[36, 39, "###"] text:[40, 53, "Heading 1.1.1"]
    Text[40, 53] chars:[40, 53, "Headi … 1.1.1"]
  Heading[54, 95] textOpen:[54, 57, "###"] text:[58, 95, "Heading 1.1.2  **_some bold italic_**"]
    Text[58, 73] chars:[58, 73, "Headi … 1.2  "]
    StrongEmphasis[73, 95] textOpen:[73, 75, "**"] text:[75, 93, "_some bold italic_"] textClose:[93, 95, "**"]
      Emphasis[75, 93] textOpen:[75, 76, "_"] text:[76, 92, "some bold italic"] textClose:[92, 93, "_"]
        Text[76, 92] chars:[76, 92, "some  … talic"]
  Heading[96, 124] textOpen:[96, 98, "##"] text:[99, 124, "Heading 1.1 _some italic_"]
    Text[99, 111] chars:[99, 111, "Headi …  1.1 "]
    Emphasis[111, 124] textOpen:[111, 112, "_"] text:[112, 123, "some italic"] textClose:[123, 124, "_"]
      Text[112, 123] chars:[112, 123, "some  … talic"]
````````````````````````````````


Text only, sorted

```````````````````````````````` example(SimToc: 56) options(text-only, sorted)
[TOC]:#


## Heading 1.1 _some italic_
# Heading **some bold** 1
### Heading 1.1.2  **_some bold italic_**
### Heading 1.1.1

.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#heading-11-some-italic">Heading 1.1 some italic</a></li>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  some bold italic</a></li>
  </ul>
</div>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
<h3 id="heading-111">Heading 1.1.1</h3>
.
Document[0, 126]
  SimTocBlock[0, 8] openingMarker:[0, 1] tocKeyword:[1, 4] closingMarker:[4, 6] anchorMarker:[6, 7, "#"]
  Heading[10, 38] textOpen:[10, 12, "##"] text:[13, 38, "Heading 1.1 _some italic_"]
    Text[13, 25] chars:[13, 25, "Headi …  1.1 "]
    Emphasis[25, 38] textOpen:[25, 26, "_"] text:[26, 37, "some italic"] textClose:[37, 38, "_"]
      Text[26, 37] chars:[26, 37, "some  … talic"]
  Heading[39, 64] textOpen:[39, 40, "#"] text:[41, 64, "Heading **some bold** 1"]
    Text[41, 49] chars:[41, 49, "Heading "]
    StrongEmphasis[49, 62] textOpen:[49, 51, "**"] text:[51, 60, "some bold"] textClose:[60, 62, "**"]
      Text[51, 60] chars:[51, 60, "some bold"]
    Text[62, 64] chars:[62, 64, " 1"]
  Heading[65, 106] textOpen:[65, 68, "###"] text:[69, 106, "Heading 1.1.2  **_some bold italic_**"]
    Text[69, 84] chars:[69, 84, "Headi … 1.2  "]
    StrongEmphasis[84, 106] textOpen:[84, 86, "**"] text:[86, 104, "_some bold italic_"] textClose:[104, 106, "**"]
      Emphasis[86, 104] textOpen:[86, 87, "_"] text:[87, 103, "some bold italic"] textClose:[103, 104, "_"]
        Text[87, 103] chars:[87, 103, "some  … talic"]
  Heading[107, 124] textOpen:[107, 110, "###"] text:[111, 124, "Heading 1.1.1"]
    Text[111, 124] chars:[111, 124, "Headi … 1.1.1"]
````````````````````````````````


Text and inlines, sorted

```````````````````````````````` example(SimToc: 57) options(sorted)
[TOC]:#


### Heading 1.1.2  **_some bold italic_**
### Heading 1.1.1
## Heading 1.1 _some italic_
# Heading **some bold** 1

.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a></li>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
  </ul>
</div>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
<h3 id="heading-111">Heading 1.1.1</h3>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
.
Document[0, 126]
  SimTocBlock[0, 8] openingMarker:[0, 1] tocKeyword:[1, 4] closingMarker:[4, 6] anchorMarker:[6, 7, "#"]
  Heading[10, 51] textOpen:[10, 13, "###"] text:[14, 51, "Heading 1.1.2  **_some bold italic_**"]
    Text[14, 29] chars:[14, 29, "Headi … 1.2  "]
    StrongEmphasis[29, 51] textOpen:[29, 31, "**"] text:[31, 49, "_some bold italic_"] textClose:[49, 51, "**"]
      Emphasis[31, 49] textOpen:[31, 32, "_"] text:[32, 48, "some bold italic"] textClose:[48, 49, "_"]
        Text[32, 48] chars:[32, 48, "some  … talic"]
  Heading[52, 69] textOpen:[52, 55, "###"] text:[56, 69, "Heading 1.1.1"]
    Text[56, 69] chars:[56, 69, "Headi … 1.1.1"]
  Heading[70, 98] textOpen:[70, 72, "##"] text:[73, 98, "Heading 1.1 _some italic_"]
    Text[73, 85] chars:[73, 85, "Headi …  1.1 "]
    Emphasis[85, 98] textOpen:[85, 86, "_"] text:[86, 97, "some italic"] textClose:[97, 98, "_"]
      Text[86, 97] chars:[86, 97, "some  … talic"]
  Heading[99, 124] textOpen:[99, 100, "#"] text:[101, 124, "Heading **some bold** 1"]
    Text[101, 109] chars:[101, 109, "Heading "]
    StrongEmphasis[109, 122] textOpen:[109, 111, "**"] text:[111, 120, "some bold"] textClose:[120, 122, "**"]
      Text[111, 120] chars:[111, 120, "some bold"]
    Text[122, 124] chars:[122, 124, " 1"]
````````````````````````````````


Text only, sorted

```````````````````````````````` example(SimToc: 58) options(text-only, sorted-reversed)
[TOC]:#


## Heading 1.1 _some italic_
# Heading **some bold** 1
### Heading 1.1.2  **_some bold italic_**
### Heading 1.1.1

.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  some bold italic</a></li>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-11-some-italic">Heading 1.1 some italic</a></li>
  </ul>
</div>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
<h3 id="heading-111">Heading 1.1.1</h3>
.
Document[0, 126]
  SimTocBlock[0, 8] openingMarker:[0, 1] tocKeyword:[1, 4] closingMarker:[4, 6] anchorMarker:[6, 7, "#"]
  Heading[10, 38] textOpen:[10, 12, "##"] text:[13, 38, "Heading 1.1 _some italic_"]
    Text[13, 25] chars:[13, 25, "Headi …  1.1 "]
    Emphasis[25, 38] textOpen:[25, 26, "_"] text:[26, 37, "some italic"] textClose:[37, 38, "_"]
      Text[26, 37] chars:[26, 37, "some  … talic"]
  Heading[39, 64] textOpen:[39, 40, "#"] text:[41, 64, "Heading **some bold** 1"]
    Text[41, 49] chars:[41, 49, "Heading "]
    StrongEmphasis[49, 62] textOpen:[49, 51, "**"] text:[51, 60, "some bold"] textClose:[60, 62, "**"]
      Text[51, 60] chars:[51, 60, "some bold"]
    Text[62, 64] chars:[62, 64, " 1"]
  Heading[65, 106] textOpen:[65, 68, "###"] text:[69, 106, "Heading 1.1.2  **_some bold italic_**"]
    Text[69, 84] chars:[69, 84, "Headi … 1.2  "]
    StrongEmphasis[84, 106] textOpen:[84, 86, "**"] text:[86, 104, "_some bold italic_"] textClose:[104, 106, "**"]
      Emphasis[86, 104] textOpen:[86, 87, "_"] text:[87, 103, "some bold italic"] textClose:[103, 104, "_"]
        Text[87, 103] chars:[87, 103, "some  … talic"]
  Heading[107, 124] textOpen:[107, 110, "###"] text:[111, 124, "Heading 1.1.1"]
    Text[111, 124] chars:[111, 124, "Headi … 1.1.1"]
````````````````````````````````


Text and inlines, sorted reversed

```````````````````````````````` example(SimToc: 59) options(sorted-reversed)
[TOC]:#


### Heading 1.1.2  **_some bold italic_**
### Heading 1.1.1
## Heading 1.1 _some italic_
# Heading **some bold** 1

.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
    <li><a href="#heading-111">Heading 1.1.1</a></li>
    <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a></li>
  </ul>
</div>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
<h3 id="heading-111">Heading 1.1.1</h3>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
.
Document[0, 126]
  SimTocBlock[0, 8] openingMarker:[0, 1] tocKeyword:[1, 4] closingMarker:[4, 6] anchorMarker:[6, 7, "#"]
  Heading[10, 51] textOpen:[10, 13, "###"] text:[14, 51, "Heading 1.1.2  **_some bold italic_**"]
    Text[14, 29] chars:[14, 29, "Headi … 1.2  "]
    StrongEmphasis[29, 51] textOpen:[29, 31, "**"] text:[31, 49, "_some bold italic_"] textClose:[49, 51, "**"]
      Emphasis[31, 49] textOpen:[31, 32, "_"] text:[32, 48, "some bold italic"] textClose:[48, 49, "_"]
        Text[32, 48] chars:[32, 48, "some  … talic"]
  Heading[52, 69] textOpen:[52, 55, "###"] text:[56, 69, "Heading 1.1.1"]
    Text[56, 69] chars:[56, 69, "Headi … 1.1.1"]
  Heading[70, 98] textOpen:[70, 72, "##"] text:[73, 98, "Heading 1.1 _some italic_"]
    Text[73, 85] chars:[73, 85, "Headi …  1.1 "]
    Emphasis[85, 98] textOpen:[85, 86, "_"] text:[86, 97, "some italic"] textClose:[97, 98, "_"]
      Text[86, 97] chars:[86, 97, "some  … talic"]
  Heading[99, 124] textOpen:[99, 100, "#"] text:[101, 124, "Heading **some bold** 1"]
    Text[101, 109] chars:[101, 109, "Heading "]
    StrongEmphasis[109, 122] textOpen:[109, 111, "**"] text:[111, 120, "some bold"] textClose:[120, 122, "**"]
      Text[111, 120] chars:[111, 120, "some bold"]
    Text[122, 124] chars:[122, 124, " 1"]
````````````````````````````````


Text and inlines, unsorted

```````````````````````````````` example(SimToc: 60) options(sorted)
[TOC hierarchy]:#


### Heading 1.1.2  **_some bold italic_**
## Heading 1.1 _some italic_
### Heading 1.1.1
# Heading **some bold** 1

.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></a></li>
    <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
      <ul>
        <li><a href="#heading-111">Heading 1.1.1</a></li>
      </ul>
    </li>
  </ul>
</div>
<h3 id="heading-112--some-bold-italic">Heading 1.1.2  <strong><em>some bold italic</em></strong></h3>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
.
Document[0, 136]
  SimTocBlock[0, 18] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 14] closingMarker:[14, 16] anchorMarker:[16, 17, "#"]
  Heading[20, 61] textOpen:[20, 23, "###"] text:[24, 61, "Heading 1.1.2  **_some bold italic_**"]
    Text[24, 39] chars:[24, 39, "Headi … 1.2  "]
    StrongEmphasis[39, 61] textOpen:[39, 41, "**"] text:[41, 59, "_some bold italic_"] textClose:[59, 61, "**"]
      Emphasis[41, 59] textOpen:[41, 42, "_"] text:[42, 58, "some bold italic"] textClose:[58, 59, "_"]
        Text[42, 58] chars:[42, 58, "some  … talic"]
  Heading[62, 90] textOpen:[62, 64, "##"] text:[65, 90, "Heading 1.1 _some italic_"]
    Text[65, 77] chars:[65, 77, "Headi …  1.1 "]
    Emphasis[77, 90] textOpen:[77, 78, "_"] text:[78, 89, "some italic"] textClose:[89, 90, "_"]
      Text[78, 89] chars:[78, 89, "some  … talic"]
  Heading[91, 108] textOpen:[91, 94, "###"] text:[95, 108, "Heading 1.1.1"]
    Text[95, 108] chars:[95, 108, "Headi … 1.1.1"]
  Heading[109, 134] textOpen:[109, 110, "#"] text:[111, 134, "Heading **some bold** 1"]
    Text[111, 119] chars:[111, 119, "Heading "]
    StrongEmphasis[119, 132] textOpen:[119, 121, "**"] text:[121, 130, "some bold"] textClose:[130, 132, "**"]
      Text[121, 130] chars:[121, 130, "some bold"]
    Text[132, 134] chars:[132, 134, " 1"]
````````````````````````````````


Typographic chars included

```````````````````````````````` example(SimToc: 61) options(sorted)
[TOC hierarchy]:#


### Heading's 1.1.2  **_some bold italic_**
## Heading 1.1 _some italic_
### Heading 1.1.1
# Heading **some bold** 1

.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#headings-112--some-bold-italic">Heading's 1.1.2  <strong><em>some bold italic</em></strong></a></li>
    <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
      <ul>
        <li><a href="#heading-111">Heading 1.1.1</a></li>
      </ul>
    </li>
  </ul>
</div>
<h3 id="headings-112--some-bold-italic">Heading's 1.1.2  <strong><em>some bold italic</em></strong></h3>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
.
Document[0, 138]
  SimTocBlock[0, 18] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 14] closingMarker:[14, 16] anchorMarker:[16, 17, "#"]
  Heading[20, 63] textOpen:[20, 23, "###"] text:[24, 63, "Heading's 1.1.2  **_some bold italic_**"]
    Text[24, 41] chars:[24, 41, "Headi … 1.2  "]
    StrongEmphasis[41, 63] textOpen:[41, 43, "**"] text:[43, 61, "_some bold italic_"] textClose:[61, 63, "**"]
      Emphasis[43, 61] textOpen:[43, 44, "_"] text:[44, 60, "some bold italic"] textClose:[60, 61, "_"]
        Text[44, 60] chars:[44, 60, "some  … talic"]
  Heading[64, 92] textOpen:[64, 66, "##"] text:[67, 92, "Heading 1.1 _some italic_"]
    Text[67, 79] chars:[67, 79, "Headi …  1.1 "]
    Emphasis[79, 92] textOpen:[79, 80, "_"] text:[80, 91, "some italic"] textClose:[91, 92, "_"]
      Text[80, 91] chars:[80, 91, "some  … talic"]
  Heading[93, 110] textOpen:[93, 96, "###"] text:[97, 110, "Heading 1.1.1"]
    Text[97, 110] chars:[97, 110, "Headi … 1.1.1"]
  Heading[111, 136] textOpen:[111, 112, "#"] text:[113, 136, "Heading **some bold** 1"]
    Text[113, 121] chars:[113, 121, "Heading "]
    StrongEmphasis[121, 134] textOpen:[121, 123, "**"] text:[123, 132, "some bold"] textClose:[132, 134, "**"]
      Text[123, 132] chars:[123, 132, "some bold"]
    Text[134, 136] chars:[134, 136, " 1"]
````````````````````````````````


With Typographic extension included

```````````````````````````````` example(SimToc: 62) options(typographic)
[TOC hierarchy]:#


### Heading's 1.1.2  **_some bold italic_**
## Heading 1.1 _some italic_
### Heading 1.1.1
# Heading **some bold** 1

.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#headings-112--some-bold-italic">Heading&rsquo;s 1.1.2  <strong><em>some bold italic</em></strong></a></li>
    <li><a href="#heading-11-some-italic">Heading 1.1 <em>some italic</em></a>
      <ul>
        <li><a href="#heading-111">Heading 1.1.1</a></li>
      </ul>
    </li>
  </ul>
</div>
<h3 id="headings-112--some-bold-italic">Heading&rsquo;s 1.1.2  <strong><em>some bold italic</em></strong></h3>
<h2 id="heading-11-some-italic">Heading 1.1 <em>some italic</em></h2>
<h3 id="heading-111">Heading 1.1.1</h3>
<h1 id="heading-some-bold-1">Heading <strong>some bold</strong> 1</h1>
.
Document[0, 138]
  SimTocBlock[0, 18] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 14] closingMarker:[14, 16] anchorMarker:[16, 17, "#"]
  Heading[20, 63] textOpen:[20, 23, "###"] text:[24, 63, "Heading's 1.1.2  **_some bold italic_**"]
    Text[24, 31] chars:[24, 31, "Heading"]
    TypographicSmarts[31, 32] typographic: &rsquo; 
    Text[32, 41] chars:[32, 41, "s 1.1.2  "]
    StrongEmphasis[41, 63] textOpen:[41, 43, "**"] text:[43, 61, "_some bold italic_"] textClose:[61, 63, "**"]
      Emphasis[43, 61] textOpen:[43, 44, "_"] text:[44, 60, "some bold italic"] textClose:[60, 61, "_"]
        Text[44, 60] chars:[44, 60, "some  … talic"]
  Heading[64, 92] textOpen:[64, 66, "##"] text:[67, 92, "Heading 1.1 _some italic_"]
    Text[67, 79] chars:[67, 79, "Headi …  1.1 "]
    Emphasis[79, 92] textOpen:[79, 80, "_"] text:[80, 91, "some italic"] textClose:[91, 92, "_"]
      Text[80, 91] chars:[80, 91, "some  … talic"]
  Heading[93, 110] textOpen:[93, 96, "###"] text:[97, 110, "Heading 1.1.1"]
    Text[97, 110] chars:[97, 110, "Headi … 1.1.1"]
  Heading[111, 136] textOpen:[111, 112, "#"] text:[113, 136, "Heading **some bold** 1"]
    Text[113, 121] chars:[113, 121, "Heading "]
    StrongEmphasis[121, 134] textOpen:[121, 123, "**"] text:[123, 132, "some bold"] textClose:[132, 134, "**"]
      Text[123, 132] chars:[123, 132, "some bold"]
    Text[134, 136] chars:[134, 136, " 1"]
````````````````````````````````


```````````````````````````````` example(SimToc: 63) options(github)
[TOC hierarchy]:#


### Heading_add
## Heading2_add_more

.
<div>
  <h1>Table of Contents</h1>
  <ul>
    <li><a href="#heading_add">Heading_add</a></li>
    <li><a href="#heading2_add_more">Heading2_add_more</a></li>
  </ul>
</div>
<h3 id="heading_add">Heading_add</h3>
<h2 id="heading2_add_more">Heading2_add_more</h2>
.
Document[0, 58]
  SimTocBlock[0, 18] openingMarker:[0, 1] tocKeyword:[1, 4] style:[5, 14] closingMarker:[14, 16] anchorMarker:[16, 17, "#"]
  Heading[20, 35] textOpen:[20, 23, "###"] text:[24, 35, "Heading_add"]
    Text[24, 35] chars:[24, 35, "Headi … g_add"]
  Heading[36, 56] textOpen:[36, 38, "##"] text:[39, 56, "Heading2_add_more"]
    Text[39, 56] chars:[39, 56, "Headi … _more"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
## Header 2
### Header 3

[TOC levels=3]:# "titles"
.
<h2 id="header-2" md-pos="3-11">Header 2</h2>
<h3 id="header-3" md-pos="16-24">Header 3</h3>
<div md-pos="26-51">
  <h1>titles</h1>
  <ul>
    <li><a href="#header-2">Header 2</a>
      <ul>
        <li><a href="#header-3">Header 3</a></li>
      </ul>
    </li>
  </ul>
</div>
.
Document[0, 51]
  Heading[0, 11] textOpen:[0, 2, "##"] text:[3, 11, "Header 2"]
    Text[3, 11] chars:[3, 11, "Header 2"]
  Heading[12, 24] textOpen:[12, 15, "###"] text:[16, 24, "Header 3"]
    Text[16, 24] chars:[16, 24, "Header 3"]
  SimTocBlock[26, 51] openingMarker:[26, 27] tocKeyword:[27, 30] style:[31, 39] closingMarker:[39, 41] anchorMarker:[41, 42, "#"] openingTitleMarker:[43, 44, "\""] title:[44, 50, "titles"] closingTitleMarker:[50, 51, "\""]
````````````````````````````````


