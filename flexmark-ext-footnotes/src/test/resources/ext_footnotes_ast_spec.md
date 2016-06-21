---
title: Footnotes Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Footnotes

Converts footnote references and definitions to footnotes in the HTML page. A footnote is a
link reference starting with a ^ with the definition being the same: [^ref]: footnote.

A footnote definition is a container block that can contain any element as long as it is
indented by 4 spaces or a tab from the indent level of the footnote.

```````````````````````````````` example Footnotes: 1
This paragraph has a footnote[^footnote].

[^footnote]: This is the body of the footnote.
with continuation text. Inline _italic_ and 
**bold**.

    Multiple paragraphs are supported as other 
    markdown elements such as lists.
    
    - item 1
    - item 2
.
<p>This paragraph has a footnote<sup id="fnref-1"><a class="footnote-ref" href="#fn-1">1</a></sup>.</p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>This is the body of the footnote.
      with continuation text. Inline <em>italic</em> and
      <strong>bold</strong>.</p>
      <p>Multiple paragraphs are supported as other
      markdown elements such as lists.</p>
      <ul>
        <li>item 1</li>
        <li>item 2</li>
      </ul>
      <a href="#fnref-1" class="footnote-backref">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 262]
  Paragraph[0, 42]
    Text[0, 29] chars:[0, 29, "This "..."tnote"]
    Footnote[29, 40] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 39, "footnote"] textClose:[39, 40, "]"]
      Text[31, 39] chars:[31, 39, "footnote"]
    Text[40, 41] chars:[40, 41, "."]
  FootnoteBlock[43, 89] ordinal: 1  open:[43, 45] text:[45, 53] close:[53, 55] footnote:[56, 89]
    Paragraph[56, 145]
      Text[56, 89] chars:[56, 89, "This "..."note."]
      SoftLineBreak[89, 90]
      Text[90, 121] chars:[90, 121, "with "..."line "]
      Emphasis[121, 129] textOpen:[121, 122, "_"] text:[122, 128, "italic"] textClose:[128, 129, "_"]
        Text[122, 128] chars:[122, 128, "italic"]
      Text[129, 133] chars:[129, 133, " and"]
      SoftLineBreak[134, 135]
      StrongEmphasis[135, 143] textOpen:[135, 137, "**"] text:[137, 141, "bold"] textClose:[141, 143, "**"]
        Text[137, 141] chars:[137, 141, "bold"]
      Text[143, 144] chars:[143, 144, "."]
    Paragraph[150, 231]
      Text[150, 192] chars:[150, 192, "Multi"..."other"]
      SoftLineBreak[193, 194]
      Text[198, 230] chars:[198, 230, "markd"..."ists."]
    BulletList[240, 262] isTight=true
      BulletListItem[240, 249] open:[240, 241, "-"]
        Paragraph[242, 249]
          Text[242, 248] chars:[242, 248, "item 1"]
      BulletListItem[253, 262] open:[253, 254, "-"]
        Paragraph[255, 262]
          Text[255, 261] chars:[255, 261, "item 2"]
````````````````````````````````


Not a footnote nor a footnote definition if space between [ and ^.

```````````````````````````````` example Footnotes: 2
This paragraph has no footnote[ ^footnote].

[ ^footnote]: This is the body of the footnote.
with continuation text. Inline _italic_ and 
**bold**.

    Multiple paragraphs are supported as other 
    markdown elements such as lists.
    
    - item 1
    - item 2
.
<p>This paragraph has no footnote[ ^footnote].</p>
<p>[ ^footnote]: This is the body of the footnote.
with continuation text. Inline <em>italic</em> and
<strong>bold</strong>.</p>
<pre><code>Multiple paragraphs are supported as other 
markdown elements such as lists.

- item 1
- item 2
</code></pre>
.
Document[0, 265]
  Paragraph[0, 44]
    Text[0, 30] chars:[0, 30, "This "..."tnote"]
    LinkRef[30, 42] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[30, 31, "["] reference:[32, 41, "^footnote"] referenceClose:[41, 42, "]"]
      Text[31, 41] chars:[31, 41, " ^footnote"]
    Text[42, 43] chars:[42, 43, "."]
  Paragraph[45, 148]
    LinkRef[45, 57] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[45, 46, "["] reference:[47, 56, "^footnote"] referenceClose:[56, 57, "]"]
      Text[46, 56] chars:[46, 56, " ^footnote"]
    Text[57, 92] chars:[57, 92, ": Thi"..."note."]
    SoftLineBreak[92, 93]
    Text[93, 124] chars:[93, 124, "with "..."line "]
    Emphasis[124, 132] textOpen:[124, 125, "_"] text:[125, 131, "italic"] textClose:[131, 132, "_"]
      Text[125, 131] chars:[125, 131, "italic"]
    Text[132, 136] chars:[132, 136, " and"]
    SoftLineBreak[137, 138]
    StrongEmphasis[138, 146] textOpen:[138, 140, "**"] text:[140, 144, "bold"] textClose:[144, 146, "**"]
      Text[140, 144] chars:[140, 144, "bold"]
    Text[146, 147] chars:[146, 147, "."]
  IndentedCodeBlock[153, 265]
````````````````````````````````


Unused footnotes are not used and do not show up on the page.

```````````````````````````````` example Footnotes: 3
This paragraph has a footnote[^2].

[^1]: This is the body of the unused footnote.
with continuation text. Inline _italic_ and 
**bold**.

[^2]: This is the body of the footnote.
with continuation text. Inline _italic_ and 
**bold**.

    Multiple paragraphs are supported as other 
    markdown elements such as lists.
    
    - item 1
    - item 2
.
<p>This paragraph has a footnote<sup id="fnref-1"><a class="footnote-ref" href="#fn-1">1</a></sup>.</p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>This is the body of the footnote.
      with continuation text. Inline <em>italic</em> and
      <strong>bold</strong>.</p>
      <p>Multiple paragraphs are supported as other
      markdown elements such as lists.</p>
      <ul>
        <li>item 1</li>
        <li>item 2</li>
      </ul>
      <a href="#fnref-1" class="footnote-backref">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 351]
  Paragraph[0, 35]
    Text[0, 29] chars:[0, 29, "This "..."tnote"]
    Footnote[29, 33] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 32, "2"] textClose:[32, 33, "]"]
      Text[31, 32] chars:[31, 32, "2"]
    Text[33, 34] chars:[33, 34, "."]
  FootnoteBlock[36, 82] ordinal: 0  open:[36, 38] text:[38, 39] close:[39, 41] footnote:[42, 82]
    Paragraph[42, 138]
      Text[42, 82] chars:[42, 82, "This "..."note."]
      SoftLineBreak[82, 83]
      Text[83, 114] chars:[83, 114, "with "..."line "]
      Emphasis[114, 122] textOpen:[114, 115, "_"] text:[115, 121, "italic"] textClose:[121, 122, "_"]
        Text[115, 121] chars:[115, 121, "italic"]
      Text[122, 126] chars:[122, 126, " and"]
      SoftLineBreak[127, 128]
      StrongEmphasis[128, 136] textOpen:[128, 130, "**"] text:[130, 134, "bold"] textClose:[134, 136, "**"]
        Text[130, 134] chars:[130, 134, "bold"]
      Text[136, 137] chars:[136, 137, "."]
  FootnoteBlock[139, 178] ordinal: 1  open:[139, 141] text:[141, 142] close:[142, 144] footnote:[145, 178]
    Paragraph[145, 234]
      Text[145, 178] chars:[145, 178, "This "..."note."]
      SoftLineBreak[178, 179]
      Text[179, 210] chars:[179, 210, "with "..."line "]
      Emphasis[210, 218] textOpen:[210, 211, "_"] text:[211, 217, "italic"] textClose:[217, 218, "_"]
        Text[211, 217] chars:[211, 217, "italic"]
      Text[218, 222] chars:[218, 222, " and"]
      SoftLineBreak[223, 224]
      StrongEmphasis[224, 232] textOpen:[224, 226, "**"] text:[226, 230, "bold"] textClose:[230, 232, "**"]
        Text[226, 230] chars:[226, 230, "bold"]
      Text[232, 233] chars:[232, 233, "."]
    Paragraph[239, 320]
      Text[239, 281] chars:[239, 281, "Multi"..."other"]
      SoftLineBreak[282, 283]
      Text[287, 319] chars:[287, 319, "markd"..."ists."]
    BulletList[329, 351] isTight=true
      BulletListItem[329, 338] open:[329, 330, "-"]
        Paragraph[331, 338]
          Text[331, 337] chars:[331, 337, "item 1"]
      BulletListItem[342, 351] open:[342, 343, "-"]
        Paragraph[344, 351]
          Text[344, 350] chars:[344, 350, "item 2"]
````````````````````````````````


Undefined footnotes are rendered as if they were text, with emphasis left as is.

```````````````````````````````` example Footnotes: 4
This paragraph has a footnote[^**footnote**].

[^footnote]: This is the body of the footnote.
with continuation text. Inline _italic_ and 
**bold**.

    Multiple paragraphs are supported as other 
    markdown elements such as lists.
    
    - item 1
    - item 2
.
<p>This paragraph has a footnote[^<strong>footnote</strong>].</p>
.
Document[0, 266]
  Paragraph[0, 46]
    Text[0, 29] chars:[0, 29, "This "..."tnote"]
    Footnote[29, 44] ordinal: 0  textOpen:[29, 31, "[^"] text:[31, 43, "**footnote**"] textClose:[43, 44, "]"]
      StrongEmphasis[31, 43] textOpen:[31, 33, "**"] text:[33, 41, "footnote"] textClose:[41, 43, "**"]
        Text[33, 41] chars:[33, 41, "footnote"]
    Text[44, 45] chars:[44, 45, "."]
  FootnoteBlock[47, 93] ordinal: 0  open:[47, 49] text:[49, 57] close:[57, 59] footnote:[60, 93]
    Paragraph[60, 149]
      Text[60, 93] chars:[60, 93, "This "..."note."]
      SoftLineBreak[93, 94]
      Text[94, 125] chars:[94, 125, "with "..."line "]
      Emphasis[125, 133] textOpen:[125, 126, "_"] text:[126, 132, "italic"] textClose:[132, 133, "_"]
        Text[126, 132] chars:[126, 132, "italic"]
      Text[133, 137] chars:[133, 137, " and"]
      SoftLineBreak[138, 139]
      StrongEmphasis[139, 147] textOpen:[139, 141, "**"] text:[141, 145, "bold"] textClose:[145, 147, "**"]
        Text[141, 145] chars:[141, 145, "bold"]
      Text[147, 148] chars:[147, 148, "."]
    Paragraph[154, 235]
      Text[154, 196] chars:[154, 196, "Multi"..."other"]
      SoftLineBreak[197, 198]
      Text[202, 234] chars:[202, 234, "markd"..."ists."]
    BulletList[244, 266] isTight=true
      BulletListItem[244, 253] open:[244, 245, "-"]
        Paragraph[246, 253]
          Text[246, 252] chars:[246, 252, "item 1"]
      BulletListItem[257, 266] open:[257, 258, "-"]
        Paragraph[259, 266]
          Text[259, 265] chars:[259, 265, "item 2"]
````````````````````````````````


Footnote numbers are assigned in order of their reference in the document

```````````````````````````````` example Footnotes: 5
This paragraph has a footnote[^2]. Followed by another[^1]. 

[^1]: This is the body of the unused footnote.
with continuation text. Inline _italic_ and 
**bold**.

[^2]: This is the body of the footnote.
with continuation text. Inline _italic_ and 
**bold**.

    Multiple paragraphs are supported as other 
    markdown elements such as lists.
    
    - item 1
    - item 2
.
<p>This paragraph has a footnote<sup id="fnref-1"><a class="footnote-ref" href="#fn-1">1</a></sup>. Followed by another<sup id="fnref-2"><a class="footnote-ref" href="#fn-2">2</a></sup>.</p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>This is the body of the footnote.
      with continuation text. Inline <em>italic</em> and
      <strong>bold</strong>.</p>
      <p>Multiple paragraphs are supported as other
      markdown elements such as lists.</p>
      <ul>
        <li>item 1</li>
        <li>item 2</li>
      </ul>
      <a href="#fnref-1" class="footnote-backref">&#8617;</a>
    </li>
    <li id="fn-2">
      <p>This is the body of the unused footnote.
      with continuation text. Inline <em>italic</em> and
      <strong>bold</strong>.</p>
      <a href="#fnref-2" class="footnote-backref">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 377]
  Paragraph[0, 61]
    Text[0, 29] chars:[0, 29, "This "..."tnote"]
    Footnote[29, 33] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 32, "2"] textClose:[32, 33, "]"]
      Text[31, 32] chars:[31, 32, "2"]
    Text[33, 54] chars:[33, 54, ". Fol"..."other"]
    Footnote[54, 58] ordinal: 2  textOpen:[54, 56, "[^"] text:[56, 57, "1"] textClose:[57, 58, "]"]
      Text[56, 57] chars:[56, 57, "1"]
    Text[58, 59] chars:[58, 59, "."]
  FootnoteBlock[62, 108] ordinal: 2  open:[62, 64] text:[64, 65] close:[65, 67] footnote:[68, 108]
    Paragraph[68, 164]
      Text[68, 108] chars:[68, 108, "This "..."note."]
      SoftLineBreak[108, 109]
      Text[109, 140] chars:[109, 140, "with "..."line "]
      Emphasis[140, 148] textOpen:[140, 141, "_"] text:[141, 147, "italic"] textClose:[147, 148, "_"]
        Text[141, 147] chars:[141, 147, "italic"]
      Text[148, 152] chars:[148, 152, " and"]
      SoftLineBreak[153, 154]
      StrongEmphasis[154, 162] textOpen:[154, 156, "**"] text:[156, 160, "bold"] textClose:[160, 162, "**"]
        Text[156, 160] chars:[156, 160, "bold"]
      Text[162, 163] chars:[162, 163, "."]
  FootnoteBlock[165, 204] ordinal: 1  open:[165, 167] text:[167, 168] close:[168, 170] footnote:[171, 204]
    Paragraph[171, 260]
      Text[171, 204] chars:[171, 204, "This "..."note."]
      SoftLineBreak[204, 205]
      Text[205, 236] chars:[205, 236, "with "..."line "]
      Emphasis[236, 244] textOpen:[236, 237, "_"] text:[237, 243, "italic"] textClose:[243, 244, "_"]
        Text[237, 243] chars:[237, 243, "italic"]
      Text[244, 248] chars:[244, 248, " and"]
      SoftLineBreak[249, 250]
      StrongEmphasis[250, 258] textOpen:[250, 252, "**"] text:[252, 256, "bold"] textClose:[256, 258, "**"]
        Text[252, 256] chars:[252, 256, "bold"]
      Text[258, 259] chars:[258, 259, "."]
    Paragraph[265, 346]
      Text[265, 307] chars:[265, 307, "Multi"..."other"]
      SoftLineBreak[308, 309]
      Text[313, 345] chars:[313, 345, "markd"..."ists."]
    BulletList[355, 377] isTight=true
      BulletListItem[355, 364] open:[355, 356, "-"]
        Paragraph[357, 364]
          Text[357, 363] chars:[357, 363, "item 1"]
      BulletListItem[368, 377] open:[368, 369, "-"]
        Paragraph[370, 377]
          Text[370, 376] chars:[370, 376, "item 2"]
````````````````````````````````


Footnotes can contain references to other footnotes.

```````````````````````````````` example Footnotes: 6
This paragraph has a footnote[^2].  

[^2]: This is the body of the footnote.
with continuation text. Inline _italic_ and 
**bold**.

    Multiple paragraphs are supported as other 
    markdown elements such as lists and footnotes[^1].
    
    - item 1
    - item 2
    
    [^1]: This is the body of a nested footnote.
    with continuation text. Inline _italic_ and 
    **bold**.
.
<p>This paragraph has a footnote<sup id="fnref-1"><a class="footnote-ref" href="#fn-1">1</a></sup>.</p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>This is the body of the footnote.
      with continuation text. Inline <em>italic</em> and
      <strong>bold</strong>.</p>
      <p>Multiple paragraphs are supported as other
      markdown elements such as lists and footnotes<sup id="fnref-2"><a class="footnote-ref" href="#fn-2">2</a></sup>.</p>
      <ul>
        <li>item 1</li>
        <li>item 2</li>
      </ul>
      <a href="#fnref-1" class="footnote-backref">&#8617;</a>
    </li>
    <li id="fn-2">
      <p>This is the body of a nested footnote.
      with continuation text. Inline <em>italic</em> and
      <strong>bold</strong>.</p>
      <a href="#fnref-2" class="footnote-backref">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 385]
  Paragraph[0, 37]
    Text[0, 29] chars:[0, 29, "This "..."tnote"]
    Footnote[29, 33] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 32, "2"] textClose:[32, 33, "]"]
      Text[31, 32] chars:[31, 32, "2"]
    Text[33, 34] chars:[33, 34, "."]
  FootnoteBlock[38, 77] ordinal: 1  open:[38, 40] text:[40, 41] close:[41, 43] footnote:[44, 77]
    Paragraph[44, 133]
      Text[44, 77] chars:[44, 77, "This "..."note."]
      SoftLineBreak[77, 78]
      Text[78, 109] chars:[78, 109, "with "..."line "]
      Emphasis[109, 117] textOpen:[109, 110, "_"] text:[110, 116, "italic"] textClose:[116, 117, "_"]
        Text[110, 116] chars:[110, 116, "italic"]
      Text[117, 121] chars:[117, 121, " and"]
      SoftLineBreak[122, 123]
      StrongEmphasis[123, 131] textOpen:[123, 125, "**"] text:[125, 129, "bold"] textClose:[129, 131, "**"]
        Text[125, 129] chars:[125, 129, "bold"]
      Text[131, 132] chars:[131, 132, "."]
    Paragraph[138, 237]
      Text[138, 180] chars:[138, 180, "Multi"..."other"]
      SoftLineBreak[181, 182]
      Text[186, 231] chars:[186, 231, "markd"..."notes"]
      Footnote[231, 235] ordinal: 2  textOpen:[231, 233, "[^"] text:[233, 234, "1"] textClose:[234, 235, "]"]
        Text[233, 234] chars:[233, 234, "1"]
      Text[235, 236] chars:[235, 236, "."]
    BulletList[246, 268] isTight=true
      BulletListItem[246, 255] open:[246, 247, "-"]
        Paragraph[248, 255]
          Text[248, 254] chars:[248, 254, "item 1"]
      BulletListItem[259, 268] open:[259, 260, "-"]
        Paragraph[261, 268]
          Text[261, 267] chars:[261, 267, "item 2"]
    FootnoteBlock[277, 321] ordinal: 2  open:[277, 279] text:[279, 280] close:[280, 282] footnote:[283, 321]
      Paragraph[283, 385]
        Text[283, 321] chars:[283, 321, "This "..."note."]
        SoftLineBreak[321, 322]
        Text[326, 357] chars:[326, 357, "with "..."line "]
        Emphasis[357, 365] textOpen:[357, 358, "_"] text:[358, 364, "italic"] textClose:[364, 365, "_"]
          Text[358, 364] chars:[358, 364, "italic"]
        Text[365, 369] chars:[365, 369, " and"]
        SoftLineBreak[370, 371]
        StrongEmphasis[375, 383] textOpen:[375, 377, "**"] text:[377, 381, "bold"] textClose:[381, 383, "**"]
          Text[377, 381] chars:[377, 381, "bold"]
        Text[383, 384] chars:[383, 384, "."]
````````````````````````````````


Customized strings

```````````````````````````````` example(Footnotes: 7) options(custom)
This paragraph has a footnote[^2].  

[^2]: This is the body of the footnote.
with continuation text. Inline _italic_ and 
**bold**.

    Multiple paragraphs are supported as other 
    markdown elements such as lists and footnotes[^1].
    
    - item 1
    - item 2
    
    [^1]: This is the body of a nested footnote.
    with continuation text. Inline _italic_ and 
    **bold**.
.
<p>This paragraph has a footnote<sup id="fnref-1"><a class="footnote-ref" href="#fn-1">[1]</a></sup>.</p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>This is the body of the footnote.
      with continuation text. Inline <em>italic</em> and
      <strong>bold</strong>.</p>
      <p>Multiple paragraphs are supported as other
      markdown elements such as lists and footnotes<sup id="fnref-2"><a class="footnote-ref" href="#fn-2">[2]</a></sup>.</p>
      <ul>
        <li>item 1</li>
        <li>item 2</li>
      </ul>
      <a href="#fnref-1" class="footnote-backref">&lt;back&gt;</a>
    </li>
    <li id="fn-2">
      <p>This is the body of a nested footnote.
      with continuation text. Inline <em>italic</em> and
      <strong>bold</strong>.</p>
      <a href="#fnref-2" class="footnote-backref">&lt;back&gt;</a>
    </li>
  </ol>
</div>
.
Document[0, 385]
  Paragraph[0, 37]
    Text[0, 29] chars:[0, 29, "This "..."tnote"]
    Footnote[29, 33] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 32, "2"] textClose:[32, 33, "]"]
      Text[31, 32] chars:[31, 32, "2"]
    Text[33, 34] chars:[33, 34, "."]
  FootnoteBlock[38, 77] ordinal: 1  open:[38, 40] text:[40, 41] close:[41, 43] footnote:[44, 77]
    Paragraph[44, 133]
      Text[44, 77] chars:[44, 77, "This "..."note."]
      SoftLineBreak[77, 78]
      Text[78, 109] chars:[78, 109, "with "..."line "]
      Emphasis[109, 117] textOpen:[109, 110, "_"] text:[110, 116, "italic"] textClose:[116, 117, "_"]
        Text[110, 116] chars:[110, 116, "italic"]
      Text[117, 121] chars:[117, 121, " and"]
      SoftLineBreak[122, 123]
      StrongEmphasis[123, 131] textOpen:[123, 125, "**"] text:[125, 129, "bold"] textClose:[129, 131, "**"]
        Text[125, 129] chars:[125, 129, "bold"]
      Text[131, 132] chars:[131, 132, "."]
    Paragraph[138, 237]
      Text[138, 180] chars:[138, 180, "Multi"..."other"]
      SoftLineBreak[181, 182]
      Text[186, 231] chars:[186, 231, "markd"..."notes"]
      Footnote[231, 235] ordinal: 2  textOpen:[231, 233, "[^"] text:[233, 234, "1"] textClose:[234, 235, "]"]
        Text[233, 234] chars:[233, 234, "1"]
      Text[235, 236] chars:[235, 236, "."]
    BulletList[246, 268] isTight=true
      BulletListItem[246, 255] open:[246, 247, "-"]
        Paragraph[248, 255]
          Text[248, 254] chars:[248, 254, "item 1"]
      BulletListItem[259, 268] open:[259, 260, "-"]
        Paragraph[261, 268]
          Text[261, 267] chars:[261, 267, "item 2"]
    FootnoteBlock[277, 321] ordinal: 2  open:[277, 279] text:[279, 280] close:[280, 282] footnote:[283, 321]
      Paragraph[283, 385]
        Text[283, 321] chars:[283, 321, "This "..."note."]
        SoftLineBreak[321, 322]
        Text[326, 357] chars:[326, 357, "with "..."line "]
        Emphasis[357, 365] textOpen:[357, 358, "_"] text:[358, 364, "italic"] textClose:[364, 365, "_"]
          Text[358, 364] chars:[358, 364, "italic"]
        Text[365, 369] chars:[365, 369, " and"]
        SoftLineBreak[370, 371]
        StrongEmphasis[375, 383] textOpen:[375, 377, "**"] text:[377, 381, "bold"] textClose:[381, 383, "**"]
          Text[377, 381] chars:[377, 381, "bold"]
        Text[383, 384] chars:[383, 384, "."]
````````````````````````````````


Customized link ref class

```````````````````````````````` example(Footnotes: 8) options(link-class-none, back-link-class-text)
This paragraph has a footnote[^2].  

[^2]: This is the body of the footnote.
.
<p>This paragraph has a footnote<sup id="fnref-1"><a href="#fn-1">1</a></sup>.</p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>This is the body of the footnote.</p>
      <a href="#fnref-1" class="text">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 78]
  Paragraph[0, 37]
    Text[0, 29] chars:[0, 29, "This "..."tnote"]
    Footnote[29, 33] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 32, "2"] textClose:[32, 33, "]"]
      Text[31, 32] chars:[31, 32, "2"]
    Text[33, 34] chars:[33, 34, "."]
  FootnoteBlock[38, 77] ordinal: 1  open:[38, 40] text:[40, 41] close:[41, 43] footnote:[44, 77]
    Paragraph[44, 78]
      Text[44, 77] chars:[44, 77, "This "..."note."]
````````````````````````````````


Customized link ref class

```````````````````````````````` example(Footnotes: 9) options(link-class-text, back-link-class-none)
This paragraph has a footnote[^2].  

[^2]: This is the body of the footnote.
.
<p>This paragraph has a footnote<sup id="fnref-1"><a class="text" href="#fn-1">1</a></sup>.</p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>This is the body of the footnote.</p>
      <a href="#fnref-1">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 78]
  Paragraph[0, 37]
    Text[0, 29] chars:[0, 29, "This "..."tnote"]
    Footnote[29, 33] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 32, "2"] textClose:[32, 33, "]"]
      Text[31, 32] chars:[31, 32, "2"]
    Text[33, 34] chars:[33, 34, "."]
  FootnoteBlock[38, 77] ordinal: 1  open:[38, 40] text:[40, 41] close:[41, 43] footnote:[44, 77]
    Paragraph[44, 78]
      Text[44, 77] chars:[44, 77, "This "..."note."]
````````````````````````````````


