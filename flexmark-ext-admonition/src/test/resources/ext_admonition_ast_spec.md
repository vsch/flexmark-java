---
title: Admonition Extension Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Admonition

To create block-styled side content. For complete documentation please see the
[Admonition Extension, Material for MkDocs](https://squidfunk.github.io/mkdocs-material/extensions/admonition/)
documentation. (*Personal opinion: Material for MkDocs is eye-candy. If you have not taken a
look at it, you are missing out on a visual treat.*)

#### Block-Styled Side Content

    !!! qualifier "Optional Title"
        block content 

#### No-Heading Content

    !!! qualifier ""
        block content 

#### Collapsible Block-Styled Side Content:

##### Collapsed by default

    ??? qualifier "Optional Title"
        block content 

##### Open by default

    ???+ qualifier "Optional Title"
         block content 

SVG images and stylesheet included for default qualifiers. User definable qualifiers can be
added by specifying recognized style qualifiers, their aliases and image mapping.

Qualifiers are used to select the icon and the color of the block.

* abstract style
  * `abstract`
  * `summary`
  * `tldr`
* bug style
  * `bug`
* danger style
  * `danger`
  * `error`
* example style
  * `example`
  * `snippet`
* fail style
  * `failure`
  * `fail`
  * `missing`
* faq style
  * `question`
  * `help`
  * `faq`
* info style
  * `info`
  * `todo`
* note style
  * `note`
  * `seealso`
* quote style
  * `quote`
  * `cite`
* success style
  * `success`
  * `check`
  * `done`
* tip style
  * `tip`
  * `hint`
  * `important`
* warning style
  * `warning`
  * `caution`
  * `attention`

### Basic Tests

```````````````````````````````` example Admonition - Basic Tests: 1
!!! note
    first child paragraph  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-note" /></svg><span>Note</span>
</div>
<div class="adm-body">
  <p>first child paragraph</p>
</div>
</div>
.
Document[0, 36]
  AdmonitionBlock[0, 36] open:[0, 3, "!!!"] info:[4, 8, "note"]
    Paragraph[13, 36]
      Text[13, 34] chars:[13, 34, "first … graph"]
````````````````````````````````


with title

```````````````````````````````` example Admonition - Basic Tests: 2
!!! note "Title"
    first child paragraph  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-note" /></svg><span>Title</span>
</div>
<div class="adm-body">
  <p>first child paragraph</p>
</div>
</div>
.
Document[0, 44]
  AdmonitionBlock[0, 44] open:[0, 3, "!!!"] info:[4, 8, "note"] titleOpen:[9, 10, "\""] title:[10, 15, "Title"] titleClose:[15, 16, "\""]
    Paragraph[21, 44]
      Text[21, 42] chars:[21, 42, "first … graph"]
````````````````````````````````


no heading

```````````````````````````````` example Admonition - Basic Tests: 3
!!! note ""
    first child paragraph  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-body">
  <p>first child paragraph</p>
</div>
</div>
.
Document[0, 39]
  AdmonitionBlock[0, 39] open:[0, 3, "!!!"] info:[4, 8, "note"] titleOpen:[9, 10, "\""] title:[10, 10] titleClose:[10, 11, "\""]
    Paragraph[16, 39]
      Text[16, 37] chars:[16, 37, "first … graph"]
````````````````````````````````


collapsed

```````````````````````````````` example Admonition - Basic Tests: 4
??? note
    first child paragraph  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note adm-collapsed">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-note" /></svg><span>Note</span>
</div>
<div class="adm-body">
  <p>first child paragraph</p>
</div>
</div>
.
Document[0, 36]
  AdmonitionBlock[0, 36] open:[0, 3, "???"] info:[4, 8, "note"]
    Paragraph[13, 36]
      Text[13, 34] chars:[13, 34, "first … graph"]
````````````````````````````````


collapsed with title

```````````````````````````````` example Admonition - Basic Tests: 5
??? note "Title"
    first child paragraph  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note adm-collapsed">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-note" /></svg><span>Title</span>
</div>
<div class="adm-body">
  <p>first child paragraph</p>
</div>
</div>
.
Document[0, 44]
  AdmonitionBlock[0, 44] open:[0, 3, "???"] info:[4, 8, "note"] titleOpen:[9, 10, "\""] title:[10, 15, "Title"] titleClose:[15, 16, "\""]
    Paragraph[21, 44]
      Text[21, 42] chars:[21, 42, "first … graph"]
````````````````````````````````


collapsed with empty title

```````````````````````````````` example Admonition - Basic Tests: 6
??? note ""
    first child paragraph  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-body">
  <p>first child paragraph</p>
</div>
</div>
.
Document[0, 39]
  AdmonitionBlock[0, 39] open:[0, 3, "???"] info:[4, 8, "note"] titleOpen:[9, 10, "\""] title:[10, 10] titleClose:[10, 11, "\""]
    Paragraph[16, 39]
      Text[16, 37] chars:[16, 37, "first … graph"]
````````````````````````````````


open

```````````````````````````````` example Admonition - Basic Tests: 7
???+ note
    first child paragraph  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note adm-open">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-note" /></svg><span>Note</span>
</div>
<div class="adm-body">
  <p>first child paragraph</p>
</div>
</div>
.
Document[0, 37]
  AdmonitionBlock[0, 37] open:[0, 4, "???+"] info:[5, 9, "note"]
    Paragraph[14, 37]
      Text[14, 35] chars:[14, 35, "first … graph"]
````````````````````````````````


open with title

```````````````````````````````` example Admonition - Basic Tests: 8
???+ note "Title"
    first child paragraph  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note adm-open">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-note" /></svg><span>Title</span>
</div>
<div class="adm-body">
  <p>first child paragraph</p>
</div>
</div>
.
Document[0, 45]
  AdmonitionBlock[0, 45] open:[0, 4, "???+"] info:[5, 9, "note"] titleOpen:[10, 11, "\""] title:[11, 16, "Title"] titleClose:[16, 17, "\""]
    Paragraph[22, 45]
      Text[22, 43] chars:[22, 43, "first … graph"]
````````````````````````````````


open with empty title

```````````````````````````````` example Admonition - Basic Tests: 9
???+ note ""
    first child paragraph  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-body">
  <p>first child paragraph</p>
</div>
</div>
.
Document[0, 40]
  AdmonitionBlock[0, 40] open:[0, 4, "???+"] info:[5, 9, "note"] titleOpen:[10, 11, "\""] title:[11, 11] titleClose:[11, 12, "\""]
    Paragraph[17, 40]
      Text[17, 38] chars:[17, 38, "first … graph"]
````````````````````````````````


in block quote

```````````````````````````````` example Admonition - Basic Tests: 10
> ???+ note ""
>     first child paragraph  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<blockquote>
  <div class="adm-block adm-note">
  <div class="adm-body">
    <p>first child paragraph</p>
  </div>
  </div>
</blockquote>
.
Document[0, 44]
  BlockQuote[0, 44] marker:[0, 1, ">"]
    AdmonitionBlock[2, 44] open:[2, 6, "???+"] info:[7, 11, "note"] titleOpen:[12, 13, "\""] title:[13, 13] titleClose:[13, 14, "\""]
      Paragraph[21, 44]
        Text[21, 42] chars:[21, 42, "first … graph"]
````````````````````````````````


indented 1

```````````````````````````````` example Admonition - Basic Tests: 11
 ???+ note ""
    first child paragraph  
    
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-body">
  <p>first child paragraph</p>
</div>
</div>
.
Document[0, 47]
  AdmonitionBlock[1, 42] open:[1, 5, "???+"] info:[6, 10, "note"] titleOpen:[11, 12, "\""] title:[12, 12] titleClose:[12, 13, "\""]
    Paragraph[18, 42] isTrailingBlankLine
      Text[18, 39] chars:[18, 39, "first … graph"]
````````````````````````````````


indented 2

```````````````````````````````` example Admonition - Basic Tests: 12
  ???+ note ""
    first child paragraph  
    
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-body">
  <p>first child paragraph</p>
</div>
</div>
.
Document[0, 48]
  AdmonitionBlock[2, 43] open:[2, 6, "???+"] info:[7, 11, "note"] titleOpen:[12, 13, "\""] title:[13, 13] titleClose:[13, 14, "\""]
    Paragraph[19, 43] isTrailingBlankLine
      Text[19, 40] chars:[19, 40, "first … graph"]
````````````````````````````````


indented 3

```````````````````````````````` example Admonition - Basic Tests: 13
   ???+ note ""
    first child paragraph  
    
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-body">
  <p>first child paragraph</p>
</div>
</div>
.
Document[0, 49]
  AdmonitionBlock[3, 44] open:[3, 7, "???+"] info:[8, 12, "note"] titleOpen:[13, 14, "\""] title:[14, 14] titleClose:[14, 15, "\""]
    Paragraph[20, 44] isTrailingBlankLine
      Text[20, 41] chars:[20, 41, "first … graph"]
````````````````````````````````


indented 4

```````````````````````````````` example Admonition - Basic Tests: 14
    ???+ note ""
        first child paragraph  
    
.
<pre><code>???+ note &quot;&quot;
    first child paragraph  
</code></pre>
.
Document[0, 54]
  IndentedCodeBlock[4, 49]
````````````````````````````````


nested

```````````````````````````````` example Admonition - Basic Tests: 15
???+ note ""
    first child paragraph  
    
    !!! note
        embedded
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-body">
  <p>first child paragraph</p>
  <div class="adm-block adm-note">
  <div class="adm-heading">
  <svg class="adm-icon"><use xlink:href="#adm-note" /></svg><span>Note</span>
  </div>
  <div class="adm-body">
    <p>embedded</p>
  </div>
  </div>
</div>
</div>
.
Document[0, 75]
  AdmonitionBlock[0, 75] open:[0, 4, "???+"] info:[5, 9, "note"] titleOpen:[10, 11, "\""] title:[11, 11] titleClose:[11, 12, "\""]
    Paragraph[17, 41] isTrailingBlankLine
      Text[17, 38] chars:[17, 38, "first … graph"]
    AdmonitionBlock[50, 75] open:[50, 53, "!!!"] info:[54, 58, "note"]
      Paragraph[67, 75]
        Text[67, 75] chars:[67, 75, "embedded"]
````````````````````````````````


with multiple children

```````````````````````````````` example Admonition - Basic Tests: 16
!!! note "Title" 

    ## Heading 2
    
    * bullet list item 1
    * bullet list item 2
    * bullet list item 3
    
    
    | Table Heading  |
    |---|
    | Table Data  |

.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-note" /></svg><span>Title</span>
</div>
<div class="adm-body">
  <h2>Heading 2</h2>
  <ul>
    <li>bullet list item 1</li>
    <li>bullet list item 2</li>
    <li>bullet list item 3</li>
  </ul>
  <table>
    <thead>
      <tr><th>Table Heading</th></tr>
    </thead>
    <tbody>
      <tr><td>Table Data</td></tr>
    </tbody>
  </table>
</div>
</div>
.
Document[0, 180]
  AdmonitionBlock[0, 179] open:[0, 3, "!!!"] info:[4, 8, "note"] titleOpen:[9, 10, "\""] title:[10, 15, "Title"] titleClose:[15, 16, "\""]
    Heading[23, 35] textOpen:[23, 25, "##"] text:[26, 35, "Heading 2"]
      Text[26, 35] chars:[26, 35, "Heading 2"]
    BulletList[45, 116] isTight
      BulletListItem[45, 66] open:[45, 46, "*"] isTight
        Paragraph[47, 66]
          Text[47, 65] chars:[47, 65, "bulle … tem 1"]
      BulletListItem[70, 91] open:[70, 71, "*"] isTight
        Paragraph[72, 91]
          Text[72, 90] chars:[72, 90, "bulle … tem 2"]
      BulletListItem[95, 116] open:[95, 96, "*"] isTight hadBlankLineAfter
        Paragraph[97, 116] isTrailingBlankLine
          Text[97, 115] chars:[97, 115, "bulle … tem 3"]
    TableBlock[130, 179]
      TableHead[130, 148]
        TableRow[130, 148] rowNumber=1
          TableCell[130, 148] header textOpen:[130, 131, "|"] text:[132, 145, "Table Heading"] textClose:[147, 148, "|"]
            Text[132, 145] chars:[132, 145, "Table … ading"]
      TableSeparator[153, 158]
        TableRow[153, 158]
          TableCell[153, 158] textOpen:[153, 154, "|"] text:[154, 157, "---"] textClose:[157, 158, "|"]
            Text[154, 157] chars:[154, 157, "---"]
      TableBody[163, 178]
        TableRow[163, 178] rowNumber=1
          TableCell[163, 178] textOpen:[163, 164, "|"] text:[165, 175, "Table Data"] textClose:[177, 178, "|"]
            Text[165, 175] chars:[165, 175, "Table Data"]
````````````````````````````````


collapsed with multiple children

```````````````````````````````` example Admonition - Basic Tests: 17
??? example 

    ## Heading 2
    
    * bullet list item 1
    * bullet list item 2
    * bullet list item 3
    
    
    | Table Heading  |
    |---|
    | Table Data  |

.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-example">
    <svg enable-background='new 0 0 24 24' viewBox='0 0 24 24' xmlns='http://www.w3.org/2000/svg'>
    <g fill="currentColor">
    <path d='m8 5.2h13v2.4h-13z'/>
    <path d='m4.4 8.4c-.1 0-.2-.1-.2-.2v-2c-.1 0-.2 0-.2.1-.2.1-.4.1-.7.2 0 0 0 0-.1 0h-.1c-.1 0-.1-.1-.1-.2v-.8c0-.1.1-.2.1-.2.4-.1.7-.2.9-.4.2-.1.3-.3.4-.5 0-.1.1-.1.2-.1h.8c.1 0 .2.1.2.2v3.7c0 .1-.1.2-.2.2z'/>
    <path d='m8 10.8h13v2.4h-13z'/>
    <path d='m3 14.1c-.1 0-.1 0-.1-.1s-.1-.1 0-.2c0-.3.2-.6.4-.9s.5-.6 1-1c.3-.3.5-.4.5-.5.1-.1.1-.1.1-.2s0-.1-.1-.1c0 0-.1-.1-.2-.1s-.1 0-.2.1c0 0-.1.1-.1.3 0 .1-.1.2-.2.2 0 0-1-.1-1-.1-.1 0-.1 0-.1-.1 0 0 0-.1 0-.2 0-.3.1-.6.3-.8.1-.2.3-.3.5-.4.1 0 .4-.1.8-.1s.7 0 .9.1.4.2.6.4c.1.2.2.4.2.7s-.1.5-.2.7-.4.5-.8.7c-.1.1-.2.1-.2.2h1c.1 0 .2.1.2.2v.8c0 .1-.1.2-.2.2h-3.1z'/>
    <path d='m8 16.4h13v2.4h-13z'/>
    <path d='m4.5 19.7c-.4 0-.6 0-.9-.1-.2-.1-.4-.2-.6-.4 0-.2-.1-.4-.2-.7 0-.1 0-.1 0-.2s.1-.1.1-.1 1-.1 1-.1c.1 0 .2.1.2.2 0 .2.1.3.1.3s.1.1.2.1.1 0 .2-.1.1-.1.1-.3c0-.1 0-.2-.1-.3 0 0-.1-.1-.2-.1 0 0-.1 0-.3.1 0 0 0 0-.1 0h-.1c.1 0 .1-.1.1-.1v-.8-.1l-1-.2c-.1 0-.1 0-.1-.1 0 0 0-.1 0-.2.1-.2.2-.5.5-.7s.6-.3 1.1-.3.9.1 1.2.3c.2.3.3.5.3.9 0 .2 0 .3-.1.5 0 .1-.1.1-.1.2.1.1.3.2.4.4s.1.3.1.5-.1.5-.2.7-.3.4-.6.5c-.3.2-.6.2-1 .2zm-.1-2.7c.1 0 .1 0 .2-.1 0 0 .1-.1.1-.2s0-.1 0-.1 0 0-.1 0-.1 0-.2.1c0 0-.1.1-.1.3v.1c-.1-.2 0-.1.1-.1z'/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-example adm-collapsed">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-example" /></svg><span>Example</span>
</div>
<div class="adm-body">
  <h2>Heading 2</h2>
  <ul>
    <li>bullet list item 1</li>
    <li>bullet list item 2</li>
    <li>bullet list item 3</li>
  </ul>
  <table>
    <thead>
      <tr><th>Table Heading</th></tr>
    </thead>
    <tbody>
      <tr><td>Table Data</td></tr>
    </tbody>
  </table>
</div>
</div>
.
Document[0, 175]
  AdmonitionBlock[0, 174] open:[0, 3, "???"] info:[4, 11, "example"]
    Heading[18, 30] textOpen:[18, 20, "##"] text:[21, 30, "Heading 2"]
      Text[21, 30] chars:[21, 30, "Heading 2"]
    BulletList[40, 111] isTight
      BulletListItem[40, 61] open:[40, 41, "*"] isTight
        Paragraph[42, 61]
          Text[42, 60] chars:[42, 60, "bulle … tem 1"]
      BulletListItem[65, 86] open:[65, 66, "*"] isTight
        Paragraph[67, 86]
          Text[67, 85] chars:[67, 85, "bulle … tem 2"]
      BulletListItem[90, 111] open:[90, 91, "*"] isTight hadBlankLineAfter
        Paragraph[92, 111] isTrailingBlankLine
          Text[92, 110] chars:[92, 110, "bulle … tem 3"]
    TableBlock[125, 174]
      TableHead[125, 143]
        TableRow[125, 143] rowNumber=1
          TableCell[125, 143] header textOpen:[125, 126, "|"] text:[127, 140, "Table Heading"] textClose:[142, 143, "|"]
            Text[127, 140] chars:[127, 140, "Table … ading"]
      TableSeparator[148, 153]
        TableRow[148, 153]
          TableCell[148, 153] textOpen:[148, 149, "|"] text:[149, 152, "---"] textClose:[152, 153, "|"]
            Text[149, 152] chars:[149, 152, "---"]
      TableBody[158, 173]
        TableRow[158, 173] rowNumber=1
          TableCell[158, 173] textOpen:[158, 159, "|"] text:[160, 170, "Table Data"] textClose:[172, 173, "|"]
            Text[160, 170] chars:[160, 170, "Table Data"]
````````````````````````````````


with lazy continuation of first paragraph

```````````````````````````````` example Admonition - Basic Tests: 18
!!! note
lazy continuation 
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-note" /></svg><span>Note</span>
</div>
<div class="adm-body">
  <p>lazy continuation</p>
</div>
</div>
.
Document[0, 27]
  AdmonitionBlock[0, 27] open:[0, 3, "!!!"] info:[4, 8, "note"]
    Paragraph[9, 27]
      Text[9, 26] chars:[9, 26, "lazy  … ation"]
````````````````````````````````


lazy continuation disabled

```````````````````````````````` example(Admonition - Basic Tests: 19) options(no-lazy-continuation)
!!! note
lazy continuation 
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-note" /></svg><span>Note</span>
</div>
<div class="adm-body"></div>
</div>
<p>lazy continuation</p>
.
Document[0, 27]
  AdmonitionBlock[0, 8] open:[0, 3, "!!!"] info:[4, 8, "note"]
  Paragraph[9, 27]
    Text[9, 26] chars:[9, 26, "lazy  … ation"]
````````````````````````````````


with leading space

```````````````````````````````` example Admonition - Basic Tests: 20
  !!! note
lazy continuation 
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-note" /></svg><span>Note</span>
</div>
<div class="adm-body">
  <p>lazy continuation</p>
</div>
</div>
.
Document[0, 29]
  AdmonitionBlock[2, 29] open:[2, 5, "!!!"] info:[6, 10, "note"]
    Paragraph[11, 29]
      Text[11, 28] chars:[11, 28, "lazy  … ation"]
````````````````````````````````


```````````````````````````````` example(Admonition - Basic Tests: 21) options(no-lead-space)
  !!! note
lazy continuation 
.
<p>!!! note
lazy continuation</p>
.
Document[0, 29]
  Paragraph[2, 29]
    Text[2, 10] chars:[2, 10, "!!! note"]
    SoftLineBreak[10, 11]
    Text[11, 28] chars:[11, 28, "lazy  … ation"]
````````````````````````````````


Inside block quote

```````````````````````````````` example Admonition - Basic Tests: 22
> !!! note
> lazy continuation 
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<blockquote>
  <div class="adm-block adm-note">
  <div class="adm-heading">
  <svg class="adm-icon"><use xlink:href="#adm-note" /></svg><span>Note</span>
  </div>
  <div class="adm-body">
    <p>lazy continuation</p>
  </div>
  </div>
</blockquote>
.
Document[0, 31]
  BlockQuote[0, 31] marker:[0, 1, ">"]
    AdmonitionBlock[2, 31] open:[2, 5, "!!!"] info:[6, 10, "note"]
      Paragraph[13, 31]
        Text[13, 30] chars:[13, 30, "lazy  … ation"]
````````````````````````````````


Try all qualifiers

```````````````````````````````` example Admonition - Basic Tests: 23
!!! abstract
    abstract  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-abstract">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m4.8 4.7h14.4v2.4h-14.4z"/>
    <path d="m4.8 8.7h14.4v2.4h-14.4z"/>
    <path d="m4.8 12.7h14.4v2.4h-14.4z"/>
    <path d="m4.8 16.7h7.2v2.4h-7.2z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-abstract">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-abstract" /></svg><span>Abstract</span>
</div>
<div class="adm-body">
  <p>abstract</p>
</div>
</div>
.
Document[0, 27]
  AdmonitionBlock[0, 27] open:[0, 3, "!!!"] info:[4, 12, "abstract"]
    Paragraph[17, 27]
      Text[17, 25] chars:[17, 25, "abstract"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 24
!!! summary
    summary  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-abstract">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m4.8 4.7h14.4v2.4h-14.4z"/>
    <path d="m4.8 8.7h14.4v2.4h-14.4z"/>
    <path d="m4.8 12.7h14.4v2.4h-14.4z"/>
    <path d="m4.8 16.7h7.2v2.4h-7.2z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-abstract">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-abstract" /></svg><span>Summary</span>
</div>
<div class="adm-body">
  <p>summary</p>
</div>
</div>
.
Document[0, 25]
  AdmonitionBlock[0, 25] open:[0, 3, "!!!"] info:[4, 11, "summary"]
    Paragraph[16, 25]
      Text[16, 23] chars:[16, 23, "summary"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 25
!!! tldr
    tldr  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-abstract">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m4.8 4.7h14.4v2.4h-14.4z"/>
    <path d="m4.8 8.7h14.4v2.4h-14.4z"/>
    <path d="m4.8 12.7h14.4v2.4h-14.4z"/>
    <path d="m4.8 16.7h7.2v2.4h-7.2z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-abstract">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-abstract" /></svg><span>TLDR</span>
</div>
<div class="adm-body">
  <p>tldr</p>
</div>
</div>
.
Document[0, 19]
  AdmonitionBlock[0, 19] open:[0, 3, "!!!"] info:[4, 8, "tldr"]
    Paragraph[13, 19]
      Text[13, 17] chars:[13, 17, "tldr"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 26
!!! bug
    bug  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-bug">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m16.7 13.9-4.8-1.9-4.8 1.8v3.7l4.8 1.8 4.8-1.8zm-4.8 3.4-2-.8v-1.5l2-.8 2 .8v1.5z"/>
    <path d="m16.7 8.9-4.8-1.8-4.8 1.8v3.7l4.8 1.8 4.8-1.8zm-4.8 3.4-2-.8v-1.5l2-.8 2 .8v1.5z"/>
    <path d="m4.5 9.4v1.8h2l1.2-.9-1.2-.9z"/>
    <path d="m4.5 12.4v1.8h2l1.2-.9-1.2-.9z"/>
    <path d="m4.5 15.4v1.7h2l1.2-.8-1.2-.9z"/>
    <path d="m15 5 1.4 1-1.2 1.8-1.2.3-.2-1.2z"/>
    <path d="m8.8 5-1.4 1 1.2 1.8 1.2.2.3-1.2z"/>
    <path d="m19.5 9.4v1.8h-2.1l-1.2-.9 1.2-.9z"/>
    <path d="m19.5 12.4v1.8h-2.1l-1.2-.9 1.2-.9z"/>
    <path d="m19.5 15.4v1.7h-2.1l-1.2-.8 1.2-.9z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-bug">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-bug" /></svg><span>Bug</span>
</div>
<div class="adm-body">
  <p>bug</p>
</div>
</div>
.
Document[0, 17]
  AdmonitionBlock[0, 17] open:[0, 3, "!!!"] info:[4, 7, "bug"]
    Paragraph[12, 17]
      Text[12, 15] chars:[12, 15, "bug"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 27
!!! danger
    danger  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-danger">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m8.5 4h8.5l-4 8h3l-7.1 9.5 1.6-8.5h-1.5z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-danger">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-danger" /></svg><span>Danger</span>
</div>
<div class="adm-body">
  <p>danger</p>
</div>
</div>
.
Document[0, 23]
  AdmonitionBlock[0, 23] open:[0, 3, "!!!"] info:[4, 10, "danger"]
    Paragraph[15, 23]
      Text[15, 21] chars:[15, 21, "danger"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 28
!!! error
    error  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-danger">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m8.5 4h8.5l-4 8h3l-7.1 9.5 1.6-8.5h-1.5z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-danger">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-danger" /></svg><span>Error</span>
</div>
<div class="adm-body">
  <p>error</p>
</div>
</div>
.
Document[0, 21]
  AdmonitionBlock[0, 21] open:[0, 3, "!!!"] info:[4, 9, "error"]
    Paragraph[14, 21]
      Text[14, 19] chars:[14, 19, "error"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 29
!!! example
    example  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-example">
    <svg enable-background='new 0 0 24 24' viewBox='0 0 24 24' xmlns='http://www.w3.org/2000/svg'>
    <g fill="currentColor">
    <path d='m8 5.2h13v2.4h-13z'/>
    <path d='m4.4 8.4c-.1 0-.2-.1-.2-.2v-2c-.1 0-.2 0-.2.1-.2.1-.4.1-.7.2 0 0 0 0-.1 0h-.1c-.1 0-.1-.1-.1-.2v-.8c0-.1.1-.2.1-.2.4-.1.7-.2.9-.4.2-.1.3-.3.4-.5 0-.1.1-.1.2-.1h.8c.1 0 .2.1.2.2v3.7c0 .1-.1.2-.2.2z'/>
    <path d='m8 10.8h13v2.4h-13z'/>
    <path d='m3 14.1c-.1 0-.1 0-.1-.1s-.1-.1 0-.2c0-.3.2-.6.4-.9s.5-.6 1-1c.3-.3.5-.4.5-.5.1-.1.1-.1.1-.2s0-.1-.1-.1c0 0-.1-.1-.2-.1s-.1 0-.2.1c0 0-.1.1-.1.3 0 .1-.1.2-.2.2 0 0-1-.1-1-.1-.1 0-.1 0-.1-.1 0 0 0-.1 0-.2 0-.3.1-.6.3-.8.1-.2.3-.3.5-.4.1 0 .4-.1.8-.1s.7 0 .9.1.4.2.6.4c.1.2.2.4.2.7s-.1.5-.2.7-.4.5-.8.7c-.1.1-.2.1-.2.2h1c.1 0 .2.1.2.2v.8c0 .1-.1.2-.2.2h-3.1z'/>
    <path d='m8 16.4h13v2.4h-13z'/>
    <path d='m4.5 19.7c-.4 0-.6 0-.9-.1-.2-.1-.4-.2-.6-.4 0-.2-.1-.4-.2-.7 0-.1 0-.1 0-.2s.1-.1.1-.1 1-.1 1-.1c.1 0 .2.1.2.2 0 .2.1.3.1.3s.1.1.2.1.1 0 .2-.1.1-.1.1-.3c0-.1 0-.2-.1-.3 0 0-.1-.1-.2-.1 0 0-.1 0-.3.1 0 0 0 0-.1 0h-.1c.1 0 .1-.1.1-.1v-.8-.1l-1-.2c-.1 0-.1 0-.1-.1 0 0 0-.1 0-.2.1-.2.2-.5.5-.7s.6-.3 1.1-.3.9.1 1.2.3c.2.3.3.5.3.9 0 .2 0 .3-.1.5 0 .1-.1.1-.1.2.1.1.3.2.4.4s.1.3.1.5-.1.5-.2.7-.3.4-.6.5c-.3.2-.6.2-1 .2zm-.1-2.7c.1 0 .1 0 .2-.1 0 0 .1-.1.1-.2s0-.1 0-.1 0 0-.1 0-.1 0-.2.1c0 0-.1.1-.1.3v.1c-.1-.2 0-.1.1-.1z'/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-example">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-example" /></svg><span>Example</span>
</div>
<div class="adm-body">
  <p>example</p>
</div>
</div>
.
Document[0, 25]
  AdmonitionBlock[0, 25] open:[0, 3, "!!!"] info:[4, 11, "example"]
    Paragraph[16, 25]
      Text[16, 23] chars:[16, 23, "example"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 30
!!! snippet
    snippet  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-example">
    <svg enable-background='new 0 0 24 24' viewBox='0 0 24 24' xmlns='http://www.w3.org/2000/svg'>
    <g fill="currentColor">
    <path d='m8 5.2h13v2.4h-13z'/>
    <path d='m4.4 8.4c-.1 0-.2-.1-.2-.2v-2c-.1 0-.2 0-.2.1-.2.1-.4.1-.7.2 0 0 0 0-.1 0h-.1c-.1 0-.1-.1-.1-.2v-.8c0-.1.1-.2.1-.2.4-.1.7-.2.9-.4.2-.1.3-.3.4-.5 0-.1.1-.1.2-.1h.8c.1 0 .2.1.2.2v3.7c0 .1-.1.2-.2.2z'/>
    <path d='m8 10.8h13v2.4h-13z'/>
    <path d='m3 14.1c-.1 0-.1 0-.1-.1s-.1-.1 0-.2c0-.3.2-.6.4-.9s.5-.6 1-1c.3-.3.5-.4.5-.5.1-.1.1-.1.1-.2s0-.1-.1-.1c0 0-.1-.1-.2-.1s-.1 0-.2.1c0 0-.1.1-.1.3 0 .1-.1.2-.2.2 0 0-1-.1-1-.1-.1 0-.1 0-.1-.1 0 0 0-.1 0-.2 0-.3.1-.6.3-.8.1-.2.3-.3.5-.4.1 0 .4-.1.8-.1s.7 0 .9.1.4.2.6.4c.1.2.2.4.2.7s-.1.5-.2.7-.4.5-.8.7c-.1.1-.2.1-.2.2h1c.1 0 .2.1.2.2v.8c0 .1-.1.2-.2.2h-3.1z'/>
    <path d='m8 16.4h13v2.4h-13z'/>
    <path d='m4.5 19.7c-.4 0-.6 0-.9-.1-.2-.1-.4-.2-.6-.4 0-.2-.1-.4-.2-.7 0-.1 0-.1 0-.2s.1-.1.1-.1 1-.1 1-.1c.1 0 .2.1.2.2 0 .2.1.3.1.3s.1.1.2.1.1 0 .2-.1.1-.1.1-.3c0-.1 0-.2-.1-.3 0 0-.1-.1-.2-.1 0 0-.1 0-.3.1 0 0 0 0-.1 0h-.1c.1 0 .1-.1.1-.1v-.8-.1l-1-.2c-.1 0-.1 0-.1-.1 0 0 0-.1 0-.2.1-.2.2-.5.5-.7s.6-.3 1.1-.3.9.1 1.2.3c.2.3.3.5.3.9 0 .2 0 .3-.1.5 0 .1-.1.1-.1.2.1.1.3.2.4.4s.1.3.1.5-.1.5-.2.7-.3.4-.6.5c-.3.2-.6.2-1 .2zm-.1-2.7c.1 0 .1 0 .2-.1 0 0 .1-.1.1-.2s0-.1 0-.1 0 0-.1 0-.1 0-.2.1c0 0-.1.1-.1.3v.1c-.1-.2 0-.1.1-.1z'/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-example">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-example" /></svg><span>Snippet</span>
</div>
<div class="adm-body">
  <p>snippet</p>
</div>
</div>
.
Document[0, 25]
  AdmonitionBlock[0, 25] open:[0, 3, "!!!"] info:[4, 11, "snippet"]
    Paragraph[16, 25]
      Text[16, 23] chars:[16, 23, "snippet"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 31
!!! failure
    failure  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-fail">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m6.5 4.8 5.4 5.2c.3.3.7.3 1 0l5.4-5.7c.3-.3.8-.3 1.1.1l1.4 1.9c.2.3.2.7-.1 1l-5.9 5c-.3.3-.3.8 0 1.1l5 4.8c.2.2.3.6.1.9l-.9 1.6c-.2.4-.8.5-1.1.2l-5.6-5.1c-.3-.2-.7-.2-1 0l-5.3 4.8c-.4.3-.9.2-1.1-.2l-.9-2.4c-.1-.3-.1-.6.2-.8l4.5-3.9c.3-.3.3-.8 0-1.1l-5-4.7c-.3-.3-.3-.7 0-1l1.8-1.8c.3-.2.7-.2 1 .1z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-fail">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-fail" /></svg><span>Failure</span>
</div>
<div class="adm-body">
  <p>failure</p>
</div>
</div>
.
Document[0, 25]
  AdmonitionBlock[0, 25] open:[0, 3, "!!!"] info:[4, 11, "failure"]
    Paragraph[16, 25]
      Text[16, 23] chars:[16, 23, "failure"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 32
!!! fail
    fail  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-fail">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m6.5 4.8 5.4 5.2c.3.3.7.3 1 0l5.4-5.7c.3-.3.8-.3 1.1.1l1.4 1.9c.2.3.2.7-.1 1l-5.9 5c-.3.3-.3.8 0 1.1l5 4.8c.2.2.3.6.1.9l-.9 1.6c-.2.4-.8.5-1.1.2l-5.6-5.1c-.3-.2-.7-.2-1 0l-5.3 4.8c-.4.3-.9.2-1.1-.2l-.9-2.4c-.1-.3-.1-.6.2-.8l4.5-3.9c.3-.3.3-.8 0-1.1l-5-4.7c-.3-.3-.3-.7 0-1l1.8-1.8c.3-.2.7-.2 1 .1z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-fail">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-fail" /></svg><span>Fail</span>
</div>
<div class="adm-body">
  <p>fail</p>
</div>
</div>
.
Document[0, 19]
  AdmonitionBlock[0, 19] open:[0, 3, "!!!"] info:[4, 8, "fail"]
    Paragraph[13, 19]
      Text[13, 17] chars:[13, 17, "fail"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 33
!!! missing
    missing  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-fail">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m6.5 4.8 5.4 5.2c.3.3.7.3 1 0l5.4-5.7c.3-.3.8-.3 1.1.1l1.4 1.9c.2.3.2.7-.1 1l-5.9 5c-.3.3-.3.8 0 1.1l5 4.8c.2.2.3.6.1.9l-.9 1.6c-.2.4-.8.5-1.1.2l-5.6-5.1c-.3-.2-.7-.2-1 0l-5.3 4.8c-.4.3-.9.2-1.1-.2l-.9-2.4c-.1-.3-.1-.6.2-.8l4.5-3.9c.3-.3.3-.8 0-1.1l-5-4.7c-.3-.3-.3-.7 0-1l1.8-1.8c.3-.2.7-.2 1 .1z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-fail">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-fail" /></svg><span>Missing</span>
</div>
<div class="adm-body">
  <p>missing</p>
</div>
</div>
.
Document[0, 25]
  AdmonitionBlock[0, 25] open:[0, 3, "!!!"] info:[4, 11, "missing"]
    Paragraph[16, 25]
      Text[16, 23] chars:[16, 23, "missing"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 34
!!! question
    question  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-faq">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m20.4 12.2c0 4.5-3.7 8.2-8.2 8.2s-8.2-3.7-8.2-8.2 3.7-8.2 8.2-8.2 8.2 3.7 8.2 8.2zm-4.2-2.9c-.1-1.3-1-2.5-2.3-2.9-.8-.2-1.6-.1-2.4.1-.6.1-1.5.2-1.8.6-.4.5.2 1 .6 1.3.6.4 1.1-.1 1.8-.3s3-.7 2.4.8c-.5 1.1-2 1.9-3 2.6-.4.3-.9.6-1.2 1-.5.6.1 1 .7 1.4.4.3.9.2 1.2-.1.5-.5 1-.9 1.6-1.3 1.1-.7 2.5-1.6 2.4-3.2-.2-1.3 0 .7 0 0zm-2.8 6.8c-.3-.5-1.3-1.3-1.9-1.1-.4.1-.4.4-.5.8-.1.2-.4.6-.3.8.1.4 1.5 1.3 1.9 1 .1-.1.9-1.4.8-1.5 0-.1.1.1 0 0z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-faq">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-faq" /></svg><span>Question</span>
</div>
<div class="adm-body">
  <p>question</p>
</div>
</div>
.
Document[0, 27]
  AdmonitionBlock[0, 27] open:[0, 3, "!!!"] info:[4, 12, "question"]
    Paragraph[17, 27]
      Text[17, 25] chars:[17, 25, "question"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 35
!!! help
    help  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-faq">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m20.4 12.2c0 4.5-3.7 8.2-8.2 8.2s-8.2-3.7-8.2-8.2 3.7-8.2 8.2-8.2 8.2 3.7 8.2 8.2zm-4.2-2.9c-.1-1.3-1-2.5-2.3-2.9-.8-.2-1.6-.1-2.4.1-.6.1-1.5.2-1.8.6-.4.5.2 1 .6 1.3.6.4 1.1-.1 1.8-.3s3-.7 2.4.8c-.5 1.1-2 1.9-3 2.6-.4.3-.9.6-1.2 1-.5.6.1 1 .7 1.4.4.3.9.2 1.2-.1.5-.5 1-.9 1.6-1.3 1.1-.7 2.5-1.6 2.4-3.2-.2-1.3 0 .7 0 0zm-2.8 6.8c-.3-.5-1.3-1.3-1.9-1.1-.4.1-.4.4-.5.8-.1.2-.4.6-.3.8.1.4 1.5 1.3 1.9 1 .1-.1.9-1.4.8-1.5 0-.1.1.1 0 0z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-faq">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-faq" /></svg><span>Help</span>
</div>
<div class="adm-body">
  <p>help</p>
</div>
</div>
.
Document[0, 19]
  AdmonitionBlock[0, 19] open:[0, 3, "!!!"] info:[4, 8, "help"]
    Paragraph[13, 19]
      Text[13, 17] chars:[13, 17, "help"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 36
!!! faq
    faq  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-faq">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m20.4 12.2c0 4.5-3.7 8.2-8.2 8.2s-8.2-3.7-8.2-8.2 3.7-8.2 8.2-8.2 8.2 3.7 8.2 8.2zm-4.2-2.9c-.1-1.3-1-2.5-2.3-2.9-.8-.2-1.6-.1-2.4.1-.6.1-1.5.2-1.8.6-.4.5.2 1 .6 1.3.6.4 1.1-.1 1.8-.3s3-.7 2.4.8c-.5 1.1-2 1.9-3 2.6-.4.3-.9.6-1.2 1-.5.6.1 1 .7 1.4.4.3.9.2 1.2-.1.5-.5 1-.9 1.6-1.3 1.1-.7 2.5-1.6 2.4-3.2-.2-1.3 0 .7 0 0zm-2.8 6.8c-.3-.5-1.3-1.3-1.9-1.1-.4.1-.4.4-.5.8-.1.2-.4.6-.3.8.1.4 1.5 1.3 1.9 1 .1-.1.9-1.4.8-1.5 0-.1.1.1 0 0z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-faq">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-faq" /></svg><span>Faq</span>
</div>
<div class="adm-body">
  <p>faq</p>
</div>
</div>
.
Document[0, 17]
  AdmonitionBlock[0, 17] open:[0, 3, "!!!"] info:[4, 7, "faq"]
    Paragraph[12, 17]
      Text[12, 15] chars:[12, 15, "faq"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 37
!!! info
    info  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-info">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m20.5 11.8c0 4.5-3.7 8.2-8.2 8.2s-8.2-3.7-8.2-8.2 3.7-8.2 8.2-8.2 8.2 3.7 8.2 8.2zm-6.7 4c-.1-.1-.2-.2-.2-.3v-4.5c0-.1.1-.2.2-.4s.2-.3.2-.3 0-.1-.1-.1c-.1-.1-.5-.2-1.2-.4-.7-.1-1.2-.2-1.5-.2-.5 0-.7 0-.7.1s.1.3.2.6v4.7c0 .3-.1.5-.2.6-.2.2-.2.3-.2.3.1.1.5.3 1.2.4.6.1 1.2.2 1.6.2h.3c.4-.1.5-.2.5-.5.1 0 .1-.1-.1-.2zm-.9-8.6c-.7-.1-1.2-.2-1.5-.2-.2 0-.5.2-.7.4s-.3.4-.3.6c0 .1 0 .1.1.2s.6.2 1.5.4c1 .3 1.5.4 1.6.3.1 0 .2-.2.3-.5s.2-.5.2-.6v-.1c-.1-.1-.5-.3-1.2-.5z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-info">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-info" /></svg><span>Info</span>
</div>
<div class="adm-body">
  <p>info</p>
</div>
</div>
.
Document[0, 19]
  AdmonitionBlock[0, 19] open:[0, 3, "!!!"] info:[4, 8, "info"]
    Paragraph[13, 19]
      Text[13, 17] chars:[13, 17, "info"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 38
!!! todo
    todo  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-info">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m20.5 11.8c0 4.5-3.7 8.2-8.2 8.2s-8.2-3.7-8.2-8.2 3.7-8.2 8.2-8.2 8.2 3.7 8.2 8.2zm-6.7 4c-.1-.1-.2-.2-.2-.3v-4.5c0-.1.1-.2.2-.4s.2-.3.2-.3 0-.1-.1-.1c-.1-.1-.5-.2-1.2-.4-.7-.1-1.2-.2-1.5-.2-.5 0-.7 0-.7.1s.1.3.2.6v4.7c0 .3-.1.5-.2.6-.2.2-.2.3-.2.3.1.1.5.3 1.2.4.6.1 1.2.2 1.6.2h.3c.4-.1.5-.2.5-.5.1 0 .1-.1-.1-.2zm-.9-8.6c-.7-.1-1.2-.2-1.5-.2-.2 0-.5.2-.7.4s-.3.4-.3.6c0 .1 0 .1.1.2s.6.2 1.5.4c1 .3 1.5.4 1.6.3.1 0 .2-.2.3-.5s.2-.5.2-.6v-.1c-.1-.1-.5-.3-1.2-.5z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-info">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-info" /></svg><span>To Do</span>
</div>
<div class="adm-body">
  <p>todo</p>
</div>
</div>
.
Document[0, 19]
  AdmonitionBlock[0, 19] open:[0, 3, "!!!"] info:[4, 8, "todo"]
    Paragraph[13, 19]
      Text[13, 17] chars:[13, 17, "todo"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 39
!!! note
    note  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-note" /></svg><span>Note</span>
</div>
<div class="adm-body">
  <p>note</p>
</div>
</div>
.
Document[0, 19]
  AdmonitionBlock[0, 19] open:[0, 3, "!!!"] info:[4, 8, "note"]
    Paragraph[13, 19]
      Text[13, 17] chars:[13, 17, "note"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 40
!!! seealso
    seealso  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-note" /></svg><span>See Also</span>
</div>
<div class="adm-body">
  <p>seealso</p>
</div>
</div>
.
Document[0, 25]
  AdmonitionBlock[0, 25] open:[0, 3, "!!!"] info:[4, 11, "seealso"]
    Paragraph[16, 25]
      Text[16, 23] chars:[16, 23, "seealso"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 41
!!! quote
    quote  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-quote">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path fill="currentColor" d="m10.7 13.1c-.6.6-1.5 1.1-2.8 1.4-.3.1-.5 0-.9-.3-.3-.3-.5-.5-.5-.8 0-.1.1-.2.2-.2.3-.1.6-.4.8-1s.4-1.1.4-1.8c0-1.3-.5-2.2-1.5-2.6-.6-.2-.9-.7-.9-1.4 0-.2 0-.3.1-.4.1-.3.7-.7 1.6-1.2s1.6-.7 1.9-.6c.8.2 1.5.8 2.1 1.9.5 1.1.8 2.3.8 3.5 0 1.4-.4 2.6-1.3 3.5zm6.6 0c-.6.6-1.5 1.1-2.8 1.4-.3.1-.5 0-.9-.3-.3-.3-.5-.5-.5-.8 0-.1.1-.2.2-.2.3-.1.6-.4.8-1s.4-1.1.4-1.8c0-1.3-.5-2.2-1.5-2.6-.6-.3-.8-.7-.8-1.5 0-.2 0-.3.1-.4.1-.3.7-.7 1.6-1.2s1.6-.7 1.9-.6c.8.2 1.5.8 2.1 1.9s.9 2.2.9 3.4c-.2 1.6-.6 2.8-1.5 3.7z"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-quote">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-quote" /></svg><span>Quote</span>
</div>
<div class="adm-body">
  <p>quote</p>
</div>
</div>
.
Document[0, 21]
  AdmonitionBlock[0, 21] open:[0, 3, "!!!"] info:[4, 9, "quote"]
    Paragraph[14, 21]
      Text[14, 19] chars:[14, 19, "quote"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 42
!!! cite
    cite  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-quote">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path fill="currentColor" d="m10.7 13.1c-.6.6-1.5 1.1-2.8 1.4-.3.1-.5 0-.9-.3-.3-.3-.5-.5-.5-.8 0-.1.1-.2.2-.2.3-.1.6-.4.8-1s.4-1.1.4-1.8c0-1.3-.5-2.2-1.5-2.6-.6-.2-.9-.7-.9-1.4 0-.2 0-.3.1-.4.1-.3.7-.7 1.6-1.2s1.6-.7 1.9-.6c.8.2 1.5.8 2.1 1.9.5 1.1.8 2.3.8 3.5 0 1.4-.4 2.6-1.3 3.5zm6.6 0c-.6.6-1.5 1.1-2.8 1.4-.3.1-.5 0-.9-.3-.3-.3-.5-.5-.5-.8 0-.1.1-.2.2-.2.3-.1.6-.4.8-1s.4-1.1.4-1.8c0-1.3-.5-2.2-1.5-2.6-.6-.3-.8-.7-.8-1.5 0-.2 0-.3.1-.4.1-.3.7-.7 1.6-1.2s1.6-.7 1.9-.6c.8.2 1.5.8 2.1 1.9s.9 2.2.9 3.4c-.2 1.6-.6 2.8-1.5 3.7z"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-quote">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-quote" /></svg><span>Cite</span>
</div>
<div class="adm-body">
  <p>cite</p>
</div>
</div>
.
Document[0, 19]
  AdmonitionBlock[0, 19] open:[0, 3, "!!!"] info:[4, 8, "cite"]
    Paragraph[13, 19]
      Text[13, 17] chars:[13, 17, "cite"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 43
!!! success
    success  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-success">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m3.3 14.3 1.4-2.8c.2-.4.7-.5 1-.2l4.7 4.9c.3.3.7.2 1-.1l7.1-10.2c.2-.3.7-.4 1 0l1.9 2.4c.2.2.2.6 0 .8l-9.7 10.9c-.2.2-.6.3-.8.1l-7.5-5.1c-.2-.1-.3-.5-.1-.7z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-success">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-success" /></svg><span>Success</span>
</div>
<div class="adm-body">
  <p>success</p>
</div>
</div>
.
Document[0, 25]
  AdmonitionBlock[0, 25] open:[0, 3, "!!!"] info:[4, 11, "success"]
    Paragraph[16, 25]
      Text[16, 23] chars:[16, 23, "success"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 44
!!! check
    check  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-success">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m3.3 14.3 1.4-2.8c.2-.4.7-.5 1-.2l4.7 4.9c.3.3.7.2 1-.1l7.1-10.2c.2-.3.7-.4 1 0l1.9 2.4c.2.2.2.6 0 .8l-9.7 10.9c-.2.2-.6.3-.8.1l-7.5-5.1c-.2-.1-.3-.5-.1-.7z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-success">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-success" /></svg><span>Check</span>
</div>
<div class="adm-body">
  <p>check</p>
</div>
</div>
.
Document[0, 21]
  AdmonitionBlock[0, 21] open:[0, 3, "!!!"] info:[4, 9, "check"]
    Paragraph[14, 21]
      Text[14, 19] chars:[14, 19, "check"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 45
!!! done
    done  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-success">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m3.3 14.3 1.4-2.8c.2-.4.7-.5 1-.2l4.7 4.9c.3.3.7.2 1-.1l7.1-10.2c.2-.3.7-.4 1 0l1.9 2.4c.2.2.2.6 0 .8l-9.7 10.9c-.2.2-.6.3-.8.1l-7.5-5.1c-.2-.1-.3-.5-.1-.7z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-success">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-success" /></svg><span>Done</span>
</div>
<div class="adm-body">
  <p>done</p>
</div>
</div>
.
Document[0, 19]
  AdmonitionBlock[0, 19] open:[0, 3, "!!!"] info:[4, 8, "done"]
    Paragraph[13, 19]
      Text[13, 17] chars:[13, 17, "done"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 46
!!! tip
    tip  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-tip">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m12.3 10c2.8-.7 4.2-3.8 2.1-7.4 8.1 3.2 7.5 18.1-2.6 18.4-8.1.2-9.8-11.4-4.4-14.8.2 3.3 2.8 4.3 4.9 3.8zm-3.2 5.8c.2 1.6 2.5 1.9 3.6 1.5 2.8-1 2.5-4 2.1-5.1-.8 1.4-1.3 1.7-2.2 2s-3.7-.7-3.5 1.6z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-tip">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-tip" /></svg><span>Tip</span>
</div>
<div class="adm-body">
  <p>tip</p>
</div>
</div>
.
Document[0, 17]
  AdmonitionBlock[0, 17] open:[0, 3, "!!!"] info:[4, 7, "tip"]
    Paragraph[12, 17]
      Text[12, 15] chars:[12, 15, "tip"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 47
!!! hint
    hint  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-tip">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m12.3 10c2.8-.7 4.2-3.8 2.1-7.4 8.1 3.2 7.5 18.1-2.6 18.4-8.1.2-9.8-11.4-4.4-14.8.2 3.3 2.8 4.3 4.9 3.8zm-3.2 5.8c.2 1.6 2.5 1.9 3.6 1.5 2.8-1 2.5-4 2.1-5.1-.8 1.4-1.3 1.7-2.2 2s-3.7-.7-3.5 1.6z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-tip">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-tip" /></svg><span>Hint</span>
</div>
<div class="adm-body">
  <p>hint</p>
</div>
</div>
.
Document[0, 19]
  AdmonitionBlock[0, 19] open:[0, 3, "!!!"] info:[4, 8, "hint"]
    Paragraph[13, 19]
      Text[13, 17] chars:[13, 17, "hint"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 48
!!! important
    important  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-tip">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m12.3 10c2.8-.7 4.2-3.8 2.1-7.4 8.1 3.2 7.5 18.1-2.6 18.4-8.1.2-9.8-11.4-4.4-14.8.2 3.3 2.8 4.3 4.9 3.8zm-3.2 5.8c.2 1.6 2.5 1.9 3.6 1.5 2.8-1 2.5-4 2.1-5.1-.8 1.4-1.3 1.7-2.2 2s-3.7-.7-3.5 1.6z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-tip">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-tip" /></svg><span>Important</span>
</div>
<div class="adm-body">
  <p>important</p>
</div>
</div>
.
Document[0, 29]
  AdmonitionBlock[0, 29] open:[0, 3, "!!!"] info:[4, 13, "important"]
    Paragraph[18, 29]
      Text[18, 27] chars:[18, 27, "important"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 49
!!! warning
    warning  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-warning">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m3.1 18.5 8.1-14c.4-.7 1.4-.7 1.8 0l8.1 14c.4.7-.1 1.6-.9 1.6h-16.1c-.9 0-1.4-.9-1-1.6zm10.6-4.3c0-.1 0-.2 0-.2v-4.6c0-.4 0-.6 0-.6-.1-.1-.3-.2-.7-.3s-.7-.1-1-.1c-.1 0-.2 0-.3 0-.3.1-.5.2-.5.4v4.2s-.1.1-.2.4-.1.4 0 .5c.3.2.7.4 1.2.6s.9.3 1.2.3h.2c.2 0 .3-.2.3-.5-.2 0-.2-.1-.2-.1zm-.8 1.4c-.5-.2-.9-.2-1.2-.2s-.4.1-.5.4v.9c0 .2 0 .3-.1.4-.1.2-.2.3-.1.4s.5.3 1.2.5c.6.2 1 .2 1.1.2.2 0 .4-.1.4-.3s.1-.4.1-.7c0-.1 0-.3.1-.7 0-.3 0-.5 0-.5-.2-.1-.5-.2-1-.4z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-warning">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-warning" /></svg><span>Warning</span>
</div>
<div class="adm-body">
  <p>warning</p>
</div>
</div>
.
Document[0, 25]
  AdmonitionBlock[0, 25] open:[0, 3, "!!!"] info:[4, 11, "warning"]
    Paragraph[16, 25]
      Text[16, 23] chars:[16, 23, "warning"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 50
!!! caution
    caution  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-warning">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m3.1 18.5 8.1-14c.4-.7 1.4-.7 1.8 0l8.1 14c.4.7-.1 1.6-.9 1.6h-16.1c-.9 0-1.4-.9-1-1.6zm10.6-4.3c0-.1 0-.2 0-.2v-4.6c0-.4 0-.6 0-.6-.1-.1-.3-.2-.7-.3s-.7-.1-1-.1c-.1 0-.2 0-.3 0-.3.1-.5.2-.5.4v4.2s-.1.1-.2.4-.1.4 0 .5c.3.2.7.4 1.2.6s.9.3 1.2.3h.2c.2 0 .3-.2.3-.5-.2 0-.2-.1-.2-.1zm-.8 1.4c-.5-.2-.9-.2-1.2-.2s-.4.1-.5.4v.9c0 .2 0 .3-.1.4-.1.2-.2.3-.1.4s.5.3 1.2.5c.6.2 1 .2 1.1.2.2 0 .4-.1.4-.3s.1-.4.1-.7c0-.1 0-.3.1-.7 0-.3 0-.5 0-.5-.2-.1-.5-.2-1-.4z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-warning">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-warning" /></svg><span>Caution</span>
</div>
<div class="adm-body">
  <p>caution</p>
</div>
</div>
.
Document[0, 25]
  AdmonitionBlock[0, 25] open:[0, 3, "!!!"] info:[4, 11, "caution"]
    Paragraph[16, 25]
      Text[16, 23] chars:[16, 23, "caution"]
````````````````````````````````


```````````````````````````````` example Admonition - Basic Tests: 51
!!! attention
    attention  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-warning">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m3.1 18.5 8.1-14c.4-.7 1.4-.7 1.8 0l8.1 14c.4.7-.1 1.6-.9 1.6h-16.1c-.9 0-1.4-.9-1-1.6zm10.6-4.3c0-.1 0-.2 0-.2v-4.6c0-.4 0-.6 0-.6-.1-.1-.3-.2-.7-.3s-.7-.1-1-.1c-.1 0-.2 0-.3 0-.3.1-.5.2-.5.4v4.2s-.1.1-.2.4-.1.4 0 .5c.3.2.7.4 1.2.6s.9.3 1.2.3h.2c.2 0 .3-.2.3-.5-.2 0-.2-.1-.2-.1zm-.8 1.4c-.5-.2-.9-.2-1.2-.2s-.4.1-.5.4v.9c0 .2 0 .3-.1.4-.1.2-.2.3-.1.4s.5.3 1.2.5c.6.2 1 .2 1.1.2.2 0 .4-.1.4-.3s.1-.4.1-.7c0-.1 0-.3.1-.7 0-.3 0-.5 0-.5-.2-.1-.5-.2-1-.4z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-warning">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-warning" /></svg><span>Attention</span>
</div>
<div class="adm-body">
  <p>attention</p>
</div>
</div>
.
Document[0, 29]
  AdmonitionBlock[0, 29] open:[0, 3, "!!!"] info:[4, 13, "attention"]
    Paragraph[18, 29]
      Text[18, 27] chars:[18, 27, "attention"]
````````````````````````````````


## IntelliJ

```````````````````````````````` example(IntelliJ: 1) options(intellij)
!!! ⎮attention
    attention  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-note" /></svg><span>⎮attention</span>
</div>
<div class="adm-body">
  <p>attention</p>
</div>
</div>
.
Document[0, 30]
  AdmonitionBlock[0, 30] open:[0, 3, "!!!"] info:[4, 14, "%1fattention"]
    Paragraph[19, 30]
      Text[19, 28] chars:[19, 28, "attention"]
````````````````````````````````


```````````````````````````````` example(IntelliJ: 2) options(intellij)
!!! atte⎮ntion
    attention  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-note" /></svg><span>Atte⎮ntion</span>
</div>
<div class="adm-body">
  <p>attention</p>
</div>
</div>
.
Document[0, 30]
  AdmonitionBlock[0, 30] open:[0, 3, "!!!"] info:[4, 14, "atte%1fntion"]
    Paragraph[19, 30]
      Text[19, 28] chars:[19, 28, "attention"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
!!! caution
    caution  
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-warning">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <path d="m3.1 18.5 8.1-14c.4-.7 1.4-.7 1.8 0l8.1 14c.4.7-.1 1.6-.9 1.6h-16.1c-.9 0-1.4-.9-1-1.6zm10.6-4.3c0-.1 0-.2 0-.2v-4.6c0-.4 0-.6 0-.6-.1-.1-.3-.2-.7-.3s-.7-.1-1-.1c-.1 0-.2 0-.3 0-.3.1-.5.2-.5.4v4.2s-.1.1-.2.4-.1.4 0 .5c.3.2.7.4 1.2.6s.9.3 1.2.3h.2c.2 0 .3-.2.3-.5-.2 0-.2-.1-.2-.1zm-.8 1.4c-.5-.2-.9-.2-1.2-.2s-.4.1-.5.4v.9c0 .2 0 .3-.1.4-.1.2-.2.3-.1.4s.5.3 1.2.5c.6.2 1 .2 1.1.2.2 0 .4-.1.4-.3s.1-.4.1-.7c0-.1 0-.3.1-.7 0-.3 0-.5 0-.5-.2-.1-.5-.2-1-.4z" fill="currentColor"/>
    </svg>
  </symbol>
</svg>
<div md-pos="0-25" class="adm-block adm-warning">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-warning" /></svg><span>Caution</span>
</div>
<div class="adm-body">
  <p md-pos="16-25">caution</p>
</div>
</div>
.
Document[0, 25]
  AdmonitionBlock[0, 25] open:[0, 3, "!!!"] info:[4, 11, "caution"]
    Paragraph[16, 25]
      Text[16, 23] chars:[16, 23, "caution"]
````````````````````````````````


## Issue 247

Issue #247, Admonition Expression may lack a part of the text.

```````````````````````````````` example Issue 247: 1
!!! Note
→123456

→abcdef
.
<svg xmlns="http://www.w3.org/2000/svg" class="adm-hidden">
  <symbol id="adm-note">
    <svg viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
    <g fill="currentColor">
    <path d="m15.4 5h4.5v2.7h-4.5z" transform="matrix(-.7071 -.7071 .7071 -.7071 25.7188 23.288)"/>
    <path d="m13.9 7 3.1 3.1-9.5 9.6-3.5.3.3-3.5z"/>
    </g>
    </svg>
  </symbol>
</svg>
<div class="adm-block adm-note">
<div class="adm-heading">
<svg class="adm-icon"><use xlink:href="#adm-note" /></svg><span>Note</span>
</div>
<div class="adm-body">
  <p>123456</p>
  <p>abcdef</p>
</div>
</div>
.
Document[0, 25]
  AdmonitionBlock[0, 25] open:[0, 3, "!!!"] info:[4, 8, "Note"]
    Paragraph[10, 17] isTrailingBlankLine
      Text[10, 16] chars:[10, 16, "123456"]
    Paragraph[19, 25]
      Text[19, 25] chars:[19, 25, "abcdef"]
````````````````````````````````


