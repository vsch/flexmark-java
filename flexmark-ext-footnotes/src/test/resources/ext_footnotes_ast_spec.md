---
title: Footnotes Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Footnotes

Converts footnote references and definitions to footnotes in the HTML page. A footnote is a link
reference starting with a ^ with the definition being the same: \[^ref]: footnote.

A footnote definition is a container block that can contain any element as long as it is
indented by 4 spaces or a tab from the indent level of the footnote.

basic

```````````````````````````````` example Footnotes: 1
text [^footnote] embedded.

[^footnote]: footnote text
with continuation

.
<p>text <sup id="fnref-1"><a class="footnote-ref" href="#fn-1">1</a></sup> embedded.</p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>footnote text
      with continuation</p>
      <a href="#fnref-1" class="footnote-backref">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 74]
  Paragraph[0, 27] isTrailingBlankLine
    Text[0, 5] chars:[0, 5, "text "]
    Footnote[5, 16] ordinal: 1  textOpen:[5, 7, "[^"] text:[7, 15, "footnote"] textClose:[15, 16, "]"]
      Text[7, 15] chars:[7, 15, "footnote"]
    Text[16, 26] chars:[16, 26, " embedded."]
  FootnoteBlock[28, 73] ordinal: 1  open:[28, 30] text:[30, 38] close:[38, 40] footnote:[41, 73]
    Paragraph[41, 73] isTrailingBlankLine
      Text[41, 54] chars:[41, 54, "footn …  text"]
      SoftLineBreak[54, 55]
      Text[55, 72] chars:[55, 72, "with  … ation"]
````````````````````````````````


undefined

```````````````````````````````` example Footnotes: 2
text [^undefined] embedded.

[^footnote]: footnote text
with continuation


.
<p>text [^undefined] embedded.</p>
.
Document[0, 76]
  Paragraph[0, 28] isTrailingBlankLine
    Text[0, 5] chars:[0, 5, "text "]
    Footnote[5, 17] ordinal: 0  textOpen:[5, 7, "[^"] text:[7, 16, "undefined"] textClose:[16, 17, "]"]
      Text[7, 16] chars:[7, 16, "undefined"]
    Text[17, 27] chars:[17, 27, " embedded."]
  FootnoteBlock[29, 74] ordinal: 0  open:[29, 31] text:[31, 39] close:[39, 41] footnote:[42, 74]
    Paragraph[42, 74] isTrailingBlankLine
      Text[42, 55] chars:[42, 55, "footn …  text"]
      SoftLineBreak[55, 56]
      Text[56, 73] chars:[56, 73, "with  … ation"]
````````````````````````````````


duplicated

```````````````````````````````` example Footnotes: 3
text [^footnote] embedded.

[^footnote]: footnote text
with continuation

[^footnote]: duplicated footnote text
with continuation

.
<p>text <sup id="fnref-1"><a class="footnote-ref" href="#fn-1">1</a></sup> embedded.</p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>footnote text
      with continuation</p>
      <a href="#fnref-1" class="footnote-backref">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 131]
  Paragraph[0, 27] isTrailingBlankLine
    Text[0, 5] chars:[0, 5, "text "]
    Footnote[5, 16] ordinal: 1  textOpen:[5, 7, "[^"] text:[7, 15, "footnote"] textClose:[15, 16, "]"]
      Text[7, 15] chars:[7, 15, "footnote"]
    Text[16, 26] chars:[16, 26, " embedded."]
  FootnoteBlock[28, 73] ordinal: 1  open:[28, 30] text:[30, 38] close:[38, 40] footnote:[41, 73]
    Paragraph[41, 73] isTrailingBlankLine
      Text[41, 54] chars:[41, 54, "footn …  text"]
      SoftLineBreak[54, 55]
      Text[55, 72] chars:[55, 72, "with  … ation"]
  FootnoteBlock[74, 130] ordinal: 0  open:[74, 76] text:[76, 84] close:[84, 86] footnote:[87, 130]
    Paragraph[87, 130] isTrailingBlankLine
      Text[87, 111] chars:[87, 111, "dupli …  text"]
      SoftLineBreak[111, 112]
      Text[112, 129] chars:[112, 129, "with  … ation"]
````````````````````````````````


nested

```````````````````````````````` example Footnotes: 4
text [^footnote] embedded.

[^footnote]: footnote text with [^another] embedded footnote
with continuation

[^another]: footnote text
with continuation

.
<p>text <sup id="fnref-1"><a class="footnote-ref" href="#fn-1">1</a></sup> embedded.</p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>footnote text with <sup id="fnref-2"><a class="footnote-ref" href="#fn-2">2</a></sup> embedded footnote
      with continuation</p>
      <a href="#fnref-1" class="footnote-backref">&#8617;</a>
    </li>
    <li id="fn-2">
      <p>footnote text
      with continuation</p>
      <a href="#fnref-2" class="footnote-backref">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 153]
  Paragraph[0, 27] isTrailingBlankLine
    Text[0, 5] chars:[0, 5, "text "]
    Footnote[5, 16] ordinal: 1  textOpen:[5, 7, "[^"] text:[7, 15, "footnote"] textClose:[15, 16, "]"]
      Text[7, 15] chars:[7, 15, "footnote"]
    Text[16, 26] chars:[16, 26, " embedded."]
  FootnoteBlock[28, 107] ordinal: 1  open:[28, 30] text:[30, 38] close:[38, 40] footnote:[41, 107]
    Paragraph[41, 107] isTrailingBlankLine
      Text[41, 60] chars:[41, 60, "footn … with "]
      Footnote[60, 70] ordinal: 2  textOpen:[60, 62, "[^"] text:[62, 69, "another"] textClose:[69, 70, "]"]
        Text[62, 69] chars:[62, 69, "another"]
      Text[70, 88] chars:[70, 88, " embe … tnote"]
      SoftLineBreak[88, 89]
      Text[89, 106] chars:[89, 106, "with  … ation"]
  FootnoteBlock[108, 152] ordinal: 2  open:[108, 110] text:[110, 117] close:[117, 119] footnote:[120, 152]
    Paragraph[120, 152] isTrailingBlankLine
      Text[120, 133] chars:[120, 133, "footn …  text"]
      SoftLineBreak[133, 134]
      Text[134, 151] chars:[134, 151, "with  … ation"]
````````````````````````````````


circular

```````````````````````````````` example Footnotes: 5
text [^footnote] embedded.

[^footnote]: footnote text with [^another] embedded footnote
with continuation

[^another]: footnote text with [^another] embedded footnote
with continuation

.
<p>text <sup id="fnref-1"><a class="footnote-ref" href="#fn-1">1</a></sup> embedded.</p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>footnote text with <sup id="fnref-2"><a class="footnote-ref" href="#fn-2">2</a></sup> embedded footnote
      with continuation</p>
      <a href="#fnref-1" class="footnote-backref">&#8617;</a>
    </li>
    <li id="fn-2">
      <p>footnote text with <sup id="fnref-2-1"><a class="footnote-ref" href="#fn-2">2</a></sup> embedded footnote
      with continuation</p>
      <a href="#fnref-2" class="footnote-backref">&#8617;</a>
      <a href="#fnref-2-1" class="footnote-backref">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 187]
  Paragraph[0, 27] isTrailingBlankLine
    Text[0, 5] chars:[0, 5, "text "]
    Footnote[5, 16] ordinal: 1  textOpen:[5, 7, "[^"] text:[7, 15, "footnote"] textClose:[15, 16, "]"]
      Text[7, 15] chars:[7, 15, "footnote"]
    Text[16, 26] chars:[16, 26, " embedded."]
  FootnoteBlock[28, 107] ordinal: 1  open:[28, 30] text:[30, 38] close:[38, 40] footnote:[41, 107]
    Paragraph[41, 107] isTrailingBlankLine
      Text[41, 60] chars:[41, 60, "footn … with "]
      Footnote[60, 70] ordinal: 2  textOpen:[60, 62, "[^"] text:[62, 69, "another"] textClose:[69, 70, "]"]
        Text[62, 69] chars:[62, 69, "another"]
      Text[70, 88] chars:[70, 88, " embe … tnote"]
      SoftLineBreak[88, 89]
      Text[89, 106] chars:[89, 106, "with  … ation"]
  FootnoteBlock[108, 186] ordinal: 2  open:[108, 110] text:[110, 117] close:[117, 119] footnote:[120, 186]
    Paragraph[120, 186] isTrailingBlankLine
      Text[120, 139] chars:[120, 139, "footn … with "]
      Footnote[139, 149] ordinal: 2  textOpen:[139, 141, "[^"] text:[141, 148, "another"] textClose:[148, 149, "]"]
        Text[141, 148] chars:[141, 148, "another"]
      Text[149, 167] chars:[149, 167, " embe … tnote"]
      SoftLineBreak[167, 168]
      Text[168, 185] chars:[168, 185, "with  … ation"]
````````````````````````````````


compound

```````````````````````````````` example Footnotes: 6
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
Document[0, 261]
  Paragraph[0, 42] isTrailingBlankLine
    Text[0, 29] chars:[0, 29, "This  … tnote"]
    Footnote[29, 40] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 39, "footnote"] textClose:[39, 40, "]"]
      Text[31, 39] chars:[31, 39, "footnote"]
    Text[40, 41] chars:[40, 41, "."]
  FootnoteBlock[43, 261] ordinal: 1  open:[43, 45] text:[45, 53] close:[53, 55] footnote:[56, 261]
    Paragraph[56, 145] isTrailingBlankLine
      Text[56, 89] chars:[56, 89, "This  … note."]
      SoftLineBreak[89, 90]
      Text[90, 121] chars:[90, 121, "with  … line "]
      Emphasis[121, 129] textOpen:[121, 122, "_"] text:[122, 128, "italic"] textClose:[128, 129, "_"]
        Text[122, 128] chars:[122, 128, "italic"]
      Text[129, 133] chars:[129, 133, " and"]
      SoftLineBreak[134, 135]
      StrongEmphasis[135, 143] textOpen:[135, 137, "**"] text:[137, 141, "bold"] textClose:[141, 143, "**"]
        Text[137, 141] chars:[137, 141, "bold"]
      Text[143, 144] chars:[143, 144, "."]
    Paragraph[150, 231] isTrailingBlankLine
      Text[150, 192] chars:[150, 192, "Multi … other"]
      SoftLineBreak[193, 194]
      Text[198, 230] chars:[198, 230, "markd … ists."]
    BulletList[240, 261] isTight
      BulletListItem[240, 249] open:[240, 241, "-"] isTight
        Paragraph[242, 249]
          Text[242, 248] chars:[242, 248, "item 1"]
      BulletListItem[253, 261] open:[253, 254, "-"] isTight
        Paragraph[255, 261]
          Text[255, 261] chars:[255, 261, "item 2"]
````````````````````````````````


Not a footnote nor a footnote definition if space between [ and ^.

```````````````````````````````` example Footnotes: 7
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
Document[0, 264]
  Paragraph[0, 44] isTrailingBlankLine
    Text[0, 30] chars:[0, 30, "This  … tnote"]
    LinkRef[30, 42] referenceOpen:[30, 31, "["] reference:[32, 41, "^footnote"] referenceClose:[41, 42, "]"]
      Text[32, 41] chars:[32, 41, "^footnote"]
    Text[42, 43] chars:[42, 43, "."]
  Paragraph[45, 148] isTrailingBlankLine
    LinkRef[45, 57] referenceOpen:[45, 46, "["] reference:[47, 56, "^footnote"] referenceClose:[56, 57, "]"]
      Text[47, 56] chars:[47, 56, "^footnote"]
    Text[57, 92] chars:[57, 92, ": Thi … note."]
    SoftLineBreak[92, 93]
    Text[93, 124] chars:[93, 124, "with  … line "]
    Emphasis[124, 132] textOpen:[124, 125, "_"] text:[125, 131, "italic"] textClose:[131, 132, "_"]
      Text[125, 131] chars:[125, 131, "italic"]
    Text[132, 136] chars:[132, 136, " and"]
    SoftLineBreak[137, 138]
    StrongEmphasis[138, 146] textOpen:[138, 140, "**"] text:[140, 144, "bold"] textClose:[144, 146, "**"]
      Text[140, 144] chars:[140, 144, "bold"]
    Text[146, 147] chars:[146, 147, "."]
  IndentedCodeBlock[153, 264]
````````````````````````````````


Unused footnotes are not used and do not show up on the page.

```````````````````````````````` example Footnotes: 8
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
Document[0, 350]
  Paragraph[0, 35] isTrailingBlankLine
    Text[0, 29] chars:[0, 29, "This  … tnote"]
    Footnote[29, 33] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 32, "2"] textClose:[32, 33, "]"]
      Text[31, 32] chars:[31, 32, "2"]
    Text[33, 34] chars:[33, 34, "."]
  FootnoteBlock[36, 138] ordinal: 0  open:[36, 38] text:[38, 39] close:[39, 41] footnote:[42, 138]
    Paragraph[42, 138] isTrailingBlankLine
      Text[42, 82] chars:[42, 82, "This  … note."]
      SoftLineBreak[82, 83]
      Text[83, 114] chars:[83, 114, "with  … line "]
      Emphasis[114, 122] textOpen:[114, 115, "_"] text:[115, 121, "italic"] textClose:[121, 122, "_"]
        Text[115, 121] chars:[115, 121, "italic"]
      Text[122, 126] chars:[122, 126, " and"]
      SoftLineBreak[127, 128]
      StrongEmphasis[128, 136] textOpen:[128, 130, "**"] text:[130, 134, "bold"] textClose:[134, 136, "**"]
        Text[130, 134] chars:[130, 134, "bold"]
      Text[136, 137] chars:[136, 137, "."]
  FootnoteBlock[139, 350] ordinal: 1  open:[139, 141] text:[141, 142] close:[142, 144] footnote:[145, 350]
    Paragraph[145, 234] isTrailingBlankLine
      Text[145, 178] chars:[145, 178, "This  … note."]
      SoftLineBreak[178, 179]
      Text[179, 210] chars:[179, 210, "with  … line "]
      Emphasis[210, 218] textOpen:[210, 211, "_"] text:[211, 217, "italic"] textClose:[217, 218, "_"]
        Text[211, 217] chars:[211, 217, "italic"]
      Text[218, 222] chars:[218, 222, " and"]
      SoftLineBreak[223, 224]
      StrongEmphasis[224, 232] textOpen:[224, 226, "**"] text:[226, 230, "bold"] textClose:[230, 232, "**"]
        Text[226, 230] chars:[226, 230, "bold"]
      Text[232, 233] chars:[232, 233, "."]
    Paragraph[239, 320] isTrailingBlankLine
      Text[239, 281] chars:[239, 281, "Multi … other"]
      SoftLineBreak[282, 283]
      Text[287, 319] chars:[287, 319, "markd … ists."]
    BulletList[329, 350] isTight
      BulletListItem[329, 338] open:[329, 330, "-"] isTight
        Paragraph[331, 338]
          Text[331, 337] chars:[331, 337, "item 1"]
      BulletListItem[342, 350] open:[342, 343, "-"] isTight
        Paragraph[344, 350]
          Text[344, 350] chars:[344, 350, "item 2"]
````````````````````````````````


Undefined footnotes are rendered as if they were text, with emphasis left as is.

```````````````````````````````` example Footnotes: 9
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
Document[0, 265]
  Paragraph[0, 46] isTrailingBlankLine
    Text[0, 29] chars:[0, 29, "This  … tnote"]
    Footnote[29, 44] ordinal: 0  textOpen:[29, 31, "[^"] text:[31, 43, "**footnote**"] textClose:[43, 44, "]"]
      StrongEmphasis[31, 43] textOpen:[31, 33, "**"] text:[33, 41, "footnote"] textClose:[41, 43, "**"]
        Text[33, 41] chars:[33, 41, "footnote"]
    Text[44, 45] chars:[44, 45, "."]
  FootnoteBlock[47, 265] ordinal: 0  open:[47, 49] text:[49, 57] close:[57, 59] footnote:[60, 265]
    Paragraph[60, 149] isTrailingBlankLine
      Text[60, 93] chars:[60, 93, "This  … note."]
      SoftLineBreak[93, 94]
      Text[94, 125] chars:[94, 125, "with  … line "]
      Emphasis[125, 133] textOpen:[125, 126, "_"] text:[126, 132, "italic"] textClose:[132, 133, "_"]
        Text[126, 132] chars:[126, 132, "italic"]
      Text[133, 137] chars:[133, 137, " and"]
      SoftLineBreak[138, 139]
      StrongEmphasis[139, 147] textOpen:[139, 141, "**"] text:[141, 145, "bold"] textClose:[145, 147, "**"]
        Text[141, 145] chars:[141, 145, "bold"]
      Text[147, 148] chars:[147, 148, "."]
    Paragraph[154, 235] isTrailingBlankLine
      Text[154, 196] chars:[154, 196, "Multi … other"]
      SoftLineBreak[197, 198]
      Text[202, 234] chars:[202, 234, "markd … ists."]
    BulletList[244, 265] isTight
      BulletListItem[244, 253] open:[244, 245, "-"] isTight
        Paragraph[246, 253]
          Text[246, 252] chars:[246, 252, "item 1"]
      BulletListItem[257, 265] open:[257, 258, "-"] isTight
        Paragraph[259, 265]
          Text[259, 265] chars:[259, 265, "item 2"]
````````````````````````````````


Footnote numbers are assigned in order of their reference in the document

```````````````````````````````` example Footnotes: 10
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
Document[0, 376]
  Paragraph[0, 61] isTrailingBlankLine
    Text[0, 29] chars:[0, 29, "This  … tnote"]
    Footnote[29, 33] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 32, "2"] textClose:[32, 33, "]"]
      Text[31, 32] chars:[31, 32, "2"]
    Text[33, 54] chars:[33, 54, ". Fol … other"]
    Footnote[54, 58] ordinal: 2  textOpen:[54, 56, "[^"] text:[56, 57, "1"] textClose:[57, 58, "]"]
      Text[56, 57] chars:[56, 57, "1"]
    Text[58, 59] chars:[58, 59, "."]
  FootnoteBlock[62, 164] ordinal: 2  open:[62, 64] text:[64, 65] close:[65, 67] footnote:[68, 164]
    Paragraph[68, 164] isTrailingBlankLine
      Text[68, 108] chars:[68, 108, "This  … note."]
      SoftLineBreak[108, 109]
      Text[109, 140] chars:[109, 140, "with  … line "]
      Emphasis[140, 148] textOpen:[140, 141, "_"] text:[141, 147, "italic"] textClose:[147, 148, "_"]
        Text[141, 147] chars:[141, 147, "italic"]
      Text[148, 152] chars:[148, 152, " and"]
      SoftLineBreak[153, 154]
      StrongEmphasis[154, 162] textOpen:[154, 156, "**"] text:[156, 160, "bold"] textClose:[160, 162, "**"]
        Text[156, 160] chars:[156, 160, "bold"]
      Text[162, 163] chars:[162, 163, "."]
  FootnoteBlock[165, 376] ordinal: 1  open:[165, 167] text:[167, 168] close:[168, 170] footnote:[171, 376]
    Paragraph[171, 260] isTrailingBlankLine
      Text[171, 204] chars:[171, 204, "This  … note."]
      SoftLineBreak[204, 205]
      Text[205, 236] chars:[205, 236, "with  … line "]
      Emphasis[236, 244] textOpen:[236, 237, "_"] text:[237, 243, "italic"] textClose:[243, 244, "_"]
        Text[237, 243] chars:[237, 243, "italic"]
      Text[244, 248] chars:[244, 248, " and"]
      SoftLineBreak[249, 250]
      StrongEmphasis[250, 258] textOpen:[250, 252, "**"] text:[252, 256, "bold"] textClose:[256, 258, "**"]
        Text[252, 256] chars:[252, 256, "bold"]
      Text[258, 259] chars:[258, 259, "."]
    Paragraph[265, 346] isTrailingBlankLine
      Text[265, 307] chars:[265, 307, "Multi … other"]
      SoftLineBreak[308, 309]
      Text[313, 345] chars:[313, 345, "markd … ists."]
    BulletList[355, 376] isTight
      BulletListItem[355, 364] open:[355, 356, "-"] isTight
        Paragraph[357, 364]
          Text[357, 363] chars:[357, 363, "item 1"]
      BulletListItem[368, 376] open:[368, 369, "-"] isTight
        Paragraph[370, 376]
          Text[370, 376] chars:[370, 376, "item 2"]
````````````````````````````````


Footnotes can contain references to other footnotes.

```````````````````````````````` example Footnotes: 11
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
Document[0, 384]
  Paragraph[0, 37] isTrailingBlankLine
    Text[0, 29] chars:[0, 29, "This  … tnote"]
    Footnote[29, 33] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 32, "2"] textClose:[32, 33, "]"]
      Text[31, 32] chars:[31, 32, "2"]
    Text[33, 34] chars:[33, 34, "."]
  FootnoteBlock[38, 384] ordinal: 1  open:[38, 40] text:[40, 41] close:[41, 43] footnote:[44, 384]
    Paragraph[44, 133] isTrailingBlankLine
      Text[44, 77] chars:[44, 77, "This  … note."]
      SoftLineBreak[77, 78]
      Text[78, 109] chars:[78, 109, "with  … line "]
      Emphasis[109, 117] textOpen:[109, 110, "_"] text:[110, 116, "italic"] textClose:[116, 117, "_"]
        Text[110, 116] chars:[110, 116, "italic"]
      Text[117, 121] chars:[117, 121, " and"]
      SoftLineBreak[122, 123]
      StrongEmphasis[123, 131] textOpen:[123, 125, "**"] text:[125, 129, "bold"] textClose:[129, 131, "**"]
        Text[125, 129] chars:[125, 129, "bold"]
      Text[131, 132] chars:[131, 132, "."]
    Paragraph[138, 237] isTrailingBlankLine
      Text[138, 180] chars:[138, 180, "Multi … other"]
      SoftLineBreak[181, 182]
      Text[186, 231] chars:[186, 231, "markd … notes"]
      Footnote[231, 235] ordinal: 2  textOpen:[231, 233, "[^"] text:[233, 234, "1"] textClose:[234, 235, "]"]
        Text[233, 234] chars:[233, 234, "1"]
      Text[235, 236] chars:[235, 236, "."]
    BulletList[246, 268] isTight
      BulletListItem[246, 255] open:[246, 247, "-"] isTight
        Paragraph[248, 255]
          Text[248, 254] chars:[248, 254, "item 1"]
      BulletListItem[259, 268] open:[259, 260, "-"] isTight hadBlankLineAfter
        Paragraph[261, 268] isTrailingBlankLine
          Text[261, 267] chars:[261, 267, "item 2"]
    FootnoteBlock[277, 384] ordinal: 2  open:[277, 279] text:[279, 280] close:[280, 282] footnote:[283, 384]
      Paragraph[283, 384]
        Text[283, 321] chars:[283, 321, "This  … note."]
        SoftLineBreak[321, 322]
        Text[326, 357] chars:[326, 357, "with  … line "]
        Emphasis[357, 365] textOpen:[357, 358, "_"] text:[358, 364, "italic"] textClose:[364, 365, "_"]
          Text[358, 364] chars:[358, 364, "italic"]
        Text[365, 369] chars:[365, 369, " and"]
        SoftLineBreak[370, 371]
        StrongEmphasis[375, 383] textOpen:[375, 377, "**"] text:[377, 381, "bold"] textClose:[381, 383, "**"]
          Text[377, 381] chars:[377, 381, "bold"]
        Text[383, 384] chars:[383, 384, "."]
````````````````````````````````


Customized strings

```````````````````````````````` example(Footnotes: 12) options(custom)
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
Document[0, 384]
  Paragraph[0, 37] isTrailingBlankLine
    Text[0, 29] chars:[0, 29, "This  … tnote"]
    Footnote[29, 33] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 32, "2"] textClose:[32, 33, "]"]
      Text[31, 32] chars:[31, 32, "2"]
    Text[33, 34] chars:[33, 34, "."]
  FootnoteBlock[38, 384] ordinal: 1  open:[38, 40] text:[40, 41] close:[41, 43] footnote:[44, 384]
    Paragraph[44, 133] isTrailingBlankLine
      Text[44, 77] chars:[44, 77, "This  … note."]
      SoftLineBreak[77, 78]
      Text[78, 109] chars:[78, 109, "with  … line "]
      Emphasis[109, 117] textOpen:[109, 110, "_"] text:[110, 116, "italic"] textClose:[116, 117, "_"]
        Text[110, 116] chars:[110, 116, "italic"]
      Text[117, 121] chars:[117, 121, " and"]
      SoftLineBreak[122, 123]
      StrongEmphasis[123, 131] textOpen:[123, 125, "**"] text:[125, 129, "bold"] textClose:[129, 131, "**"]
        Text[125, 129] chars:[125, 129, "bold"]
      Text[131, 132] chars:[131, 132, "."]
    Paragraph[138, 237] isTrailingBlankLine
      Text[138, 180] chars:[138, 180, "Multi … other"]
      SoftLineBreak[181, 182]
      Text[186, 231] chars:[186, 231, "markd … notes"]
      Footnote[231, 235] ordinal: 2  textOpen:[231, 233, "[^"] text:[233, 234, "1"] textClose:[234, 235, "]"]
        Text[233, 234] chars:[233, 234, "1"]
      Text[235, 236] chars:[235, 236, "."]
    BulletList[246, 268] isTight
      BulletListItem[246, 255] open:[246, 247, "-"] isTight
        Paragraph[248, 255]
          Text[248, 254] chars:[248, 254, "item 1"]
      BulletListItem[259, 268] open:[259, 260, "-"] isTight hadBlankLineAfter
        Paragraph[261, 268] isTrailingBlankLine
          Text[261, 267] chars:[261, 267, "item 2"]
    FootnoteBlock[277, 384] ordinal: 2  open:[277, 279] text:[279, 280] close:[280, 282] footnote:[283, 384]
      Paragraph[283, 384]
        Text[283, 321] chars:[283, 321, "This  … note."]
        SoftLineBreak[321, 322]
        Text[326, 357] chars:[326, 357, "with  … line "]
        Emphasis[357, 365] textOpen:[357, 358, "_"] text:[358, 364, "italic"] textClose:[364, 365, "_"]
          Text[358, 364] chars:[358, 364, "italic"]
        Text[365, 369] chars:[365, 369, " and"]
        SoftLineBreak[370, 371]
        StrongEmphasis[375, 383] textOpen:[375, 377, "**"] text:[377, 381, "bold"] textClose:[381, 383, "**"]
          Text[377, 381] chars:[377, 381, "bold"]
        Text[383, 384] chars:[383, 384, "."]
````````````````````````````````


Customized link ref class

```````````````````````````````` example(Footnotes: 13) options(link-class-none, back-link-class-text)
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
Document[0, 77]
  Paragraph[0, 37] isTrailingBlankLine
    Text[0, 29] chars:[0, 29, "This  … tnote"]
    Footnote[29, 33] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 32, "2"] textClose:[32, 33, "]"]
      Text[31, 32] chars:[31, 32, "2"]
    Text[33, 34] chars:[33, 34, "."]
  FootnoteBlock[38, 77] ordinal: 1  open:[38, 40] text:[40, 41] close:[41, 43] footnote:[44, 77]
    Paragraph[44, 77]
      Text[44, 77] chars:[44, 77, "This  … note."]
````````````````````````````````


Customized link ref class

```````````````````````````````` example(Footnotes: 14) options(link-class-text, back-link-class-none)
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
Document[0, 77]
  Paragraph[0, 37] isTrailingBlankLine
    Text[0, 29] chars:[0, 29, "This  … tnote"]
    Footnote[29, 33] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 32, "2"] textClose:[32, 33, "]"]
      Text[31, 32] chars:[31, 32, "2"]
    Text[33, 34] chars:[33, 34, "."]
  FootnoteBlock[38, 77] ordinal: 1  open:[38, 40] text:[40, 41] close:[41, 43] footnote:[44, 77]
    Paragraph[44, 77]
      Text[44, 77] chars:[44, 77, "This  … note."]
````````````````````````````````


Parser emulation family indent handling is ignored. Otherwise the indent can be huge.

```````````````````````````````` example Footnotes: 15
This paragraph has a footnote[^2].  

[^2]: This is the body of the footnote.

      Another paragraph of the footnote
      
    Also a paragraph of the footnote
    
        indented code of the footnote
.
<p>This paragraph has a footnote<sup id="fnref-1"><a class="footnote-ref" href="#fn-1">1</a></sup>.</p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>This is the body of the footnote.</p>
      <p>Another paragraph of the footnote</p>
      <p>Also a paragraph of the footnote</p>
      <pre><code>indented code of the footnote
</code></pre>
      <a href="#fnref-1" class="footnote-backref">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 205]
  Paragraph[0, 37] isTrailingBlankLine
    Text[0, 29] chars:[0, 29, "This  … tnote"]
    Footnote[29, 33] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 32, "2"] textClose:[32, 33, "]"]
      Text[31, 32] chars:[31, 32, "2"]
    Text[33, 34] chars:[33, 34, "."]
  FootnoteBlock[38, 205] ordinal: 1  open:[38, 40] text:[40, 41] close:[41, 43] footnote:[44, 205]
    Paragraph[44, 78] isTrailingBlankLine
      Text[44, 77] chars:[44, 77, "This  … note."]
    Paragraph[85, 119] isTrailingBlankLine
      Text[85, 118] chars:[85, 118, "Anoth … tnote"]
    Paragraph[130, 163] isTrailingBlankLine
      Text[130, 162] chars:[130, 162, "Also  … tnote"]
    IndentedCodeBlock[176, 205]
````````````````````````````````


List item indent is used

```````````````````````````````` example(Footnotes: 16) options(item-indent-8)
This paragraph has a footnote[^2].  

[^2]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
       Not a paragraph of the footnote
.
<p>This paragraph has a footnote<sup id="fnref-1"><a class="footnote-ref" href="#fn-1">1</a></sup>.</p>
<p>Not a paragraph of the footnote</p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>This is the body of the footnote.</p>
      <p>Another paragraph of the footnote</p>
      <p>indented code of the footnote</p>
      <a href="#fnref-1" class="footnote-backref">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 213]
  Paragraph[0, 37] isTrailingBlankLine
    Text[0, 29] chars:[0, 29, "This  … tnote"]
    Footnote[29, 33] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 32, "2"] textClose:[32, 33, "]"]
      Text[31, 32] chars:[31, 32, "2"]
    Text[33, 34] chars:[33, 34, "."]
  FootnoteBlock[38, 168] ordinal: 1  open:[38, 40] text:[40, 41] close:[41, 43] footnote:[44, 168]
    Paragraph[44, 78] isTrailingBlankLine
      Text[44, 77] chars:[44, 77, "This  … note."]
    Paragraph[87, 121] isTrailingBlankLine
      Text[87, 120] chars:[87, 120, "Anoth … tnote"]
    Paragraph[138, 168] isTrailingBlankLine
      Text[138, 167] chars:[138, 167, "inden … tnote"]
  Paragraph[182, 213]
    Text[182, 213] chars:[182, 213, "Not a … tnote"]
````````````````````````````````


Parses indented tables

```````````````````````````````` example Footnotes: 17
Paragraph text[^1]

[^1]: Footnote
      
      | table |
      |-------|
      | data  |

.
<p>Paragraph text<sup id="fnref-1"><a class="footnote-ref" href="#fn-1">1</a></sup></p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>Footnote</p>
      <table>
        <thead>
          <tr><th>table</th></tr>
        </thead>
        <tbody>
          <tr><td>data</td></tr>
        </tbody>
      </table>
      <a href="#fnref-1" class="footnote-backref">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 91]
  Paragraph[0, 19] isTrailingBlankLine
    Text[0, 14] chars:[0, 14, "Parag …  text"]
    Footnote[14, 18] ordinal: 1  textOpen:[14, 16, "[^"] text:[16, 17, "1"] textClose:[17, 18, "]"]
      Text[16, 17] chars:[16, 17, "1"]
  FootnoteBlock[20, 90] ordinal: 1  open:[20, 22] text:[22, 23] close:[23, 25] footnote:[26, 90]
    Paragraph[26, 35] isTrailingBlankLine
      Text[26, 34] chars:[26, 34, "Footnote"]
    TableBlock[48, 90]
      TableHead[48, 57]
        TableRow[48, 57] rowNumber=1
          TableCell[48, 57] header textOpen:[48, 49, "|"] text:[50, 55, "table"] textClose:[56, 57, "|"]
            Text[50, 55] chars:[50, 55, "table"]
      TableSeparator[64, 73]
        TableRow[64, 73]
          TableCell[64, 73] textOpen:[64, 65, "|"] text:[65, 72, "-------"] textClose:[72, 73, "|"]
            Text[65, 72] chars:[65, 72, "-------"]
      TableBody[80, 89]
        TableRow[80, 89] rowNumber=1
          TableCell[80, 89] textOpen:[80, 81, "|"] text:[82, 86, "data"] textClose:[88, 89, "|"]
            Text[82, 86] chars:[82, 86, "data"]
````````````````````````````````


Parses tables

```````````````````````````````` example Footnotes: 18
Paragraph text[^1]

[^1]: Footnote
      
    | table |
    |-------|
    | data  |

.
<p>Paragraph text<sup id="fnref-1"><a class="footnote-ref" href="#fn-1">1</a></sup></p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>Footnote</p>
      <table>
        <thead>
          <tr><th>table</th></tr>
        </thead>
        <tbody>
          <tr><td>data</td></tr>
        </tbody>
      </table>
      <a href="#fnref-1" class="footnote-backref">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 85]
  Paragraph[0, 19] isTrailingBlankLine
    Text[0, 14] chars:[0, 14, "Parag …  text"]
    Footnote[14, 18] ordinal: 1  textOpen:[14, 16, "[^"] text:[16, 17, "1"] textClose:[17, 18, "]"]
      Text[16, 17] chars:[16, 17, "1"]
  FootnoteBlock[20, 84] ordinal: 1  open:[20, 22] text:[22, 23] close:[23, 25] footnote:[26, 84]
    Paragraph[26, 35] isTrailingBlankLine
      Text[26, 34] chars:[26, 34, "Footnote"]
    TableBlock[46, 84]
      TableHead[46, 55]
        TableRow[46, 55] rowNumber=1
          TableCell[46, 55] header textOpen:[46, 47, "|"] text:[48, 53, "table"] textClose:[54, 55, "|"]
            Text[48, 53] chars:[48, 53, "table"]
      TableSeparator[60, 69]
        TableRow[60, 69]
          TableCell[60, 69] textOpen:[60, 61, "|"] text:[61, 68, "-------"] textClose:[68, 69, "|"]
            Text[61, 68] chars:[61, 68, "-------"]
      TableBody[74, 83]
        TableRow[74, 83] rowNumber=1
          TableCell[74, 83] textOpen:[74, 75, "|"] text:[76, 80, "data"] textClose:[82, 83, "|"]
            Text[76, 80] chars:[76, 80, "data"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
This paragraph has a footnote[^2].  

[^2]: This is the body of the footnote.
.
<p md-pos="0-37">This paragraph has a footnote<sup id="fnref-1" md-pos="29-33"><a class="footnote-ref" href="#fn-1">1</a></sup>.</p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p md-pos="44-77">This is the body of the footnote.</p>
      <a href="#fnref-1" class="footnote-backref">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 77]
  Paragraph[0, 37] isTrailingBlankLine
    Text[0, 29] chars:[0, 29, "This  … tnote"]
    Footnote[29, 33] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 32, "2"] textClose:[32, 33, "]"]
      Text[31, 32] chars:[31, 32, "2"]
    Text[33, 34] chars:[33, 34, "."]
  FootnoteBlock[38, 77] ordinal: 1  open:[38, 40] text:[40, 41] close:[41, 43] footnote:[44, 77]
    Paragraph[44, 77]
      Text[44, 77] chars:[44, 77, "This  … note."]
````````````````````````````````


## Issue 75

Issue #75, Incorrect footnote link

Actually it is incorrect reference parsing with `\r\n`

```````````````````````````````` example Issue 75: 1
Incorrect: amazon[^2]⏎

[^1]: http://www.google.com/⏎
⏎
[^2]: http://www.amazon.com/
.
<p>Incorrect: amazon<sup id="fnref-1"><a class="footnote-ref" href="#fn-1">1</a></sup></p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>http://www.amazon.com/</p>
      <a href="#fnref-1" class="footnote-backref">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 84]
  Paragraph[0, 23] isTrailingBlankLine
    Text[0, 17] chars:[0, 17, "Incor … mazon"]
    Footnote[17, 21] ordinal: 1  textOpen:[17, 19, "[^"] text:[19, 20, "2"] textClose:[20, 21, "]"]
      Text[19, 20] chars:[19, 20, "2"]
  FootnoteBlock[24, 54] ordinal: 0  open:[24, 26] text:[26, 27] close:[27, 29] footnote:[30, 54]
    Paragraph[30, 54] isTrailingBlankLine
      Text[30, 52] chars:[30, 52, "http: … .com/"]
  FootnoteBlock[56, 84] ordinal: 1  open:[56, 58] text:[58, 59] close:[59, 61] footnote:[62, 84]
    Paragraph[62, 84]
      Text[62, 84] chars:[62, 84, "http: … .com/"]
````````````````````````````````


```````````````````````````````` example Issue 75: 2
Incorrect: amazon[^2]

[^1]: http://www.google.com/

[^2]: http://www.amazon.com/
.
<p>Incorrect: amazon<sup id="fnref-1"><a class="footnote-ref" href="#fn-1">1</a></sup></p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>http://www.amazon.com/</p>
      <a href="#fnref-1" class="footnote-backref">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 81]
  Paragraph[0, 22] isTrailingBlankLine
    Text[0, 17] chars:[0, 17, "Incor … mazon"]
    Footnote[17, 21] ordinal: 1  textOpen:[17, 19, "[^"] text:[19, 20, "2"] textClose:[20, 21, "]"]
      Text[19, 20] chars:[19, 20, "2"]
  FootnoteBlock[23, 52] ordinal: 0  open:[23, 25] text:[25, 26] close:[26, 28] footnote:[29, 52]
    Paragraph[29, 52] isTrailingBlankLine
      Text[29, 51] chars:[29, 51, "http: … .com/"]
  FootnoteBlock[53, 81] ordinal: 1  open:[53, 55] text:[55, 56] close:[56, 58] footnote:[59, 81]
    Paragraph[59, 81]
      Text[59, 81] chars:[59, 81, "http: … .com/"]
````````````````````````````````


## Issue 244

Issue #244

```````````````````````````````` example Issue 244: 1
Duplicated footnote reference[^id].

reference[^id]

[^id]: Footnote text.
.
<p>Duplicated footnote reference<sup id="fnref-1"><a class="footnote-ref" href="#fn-1">1</a></sup>.</p>
<p>reference<sup id="fnref-1-1"><a class="footnote-ref" href="#fn-1">1</a></sup></p>
<div class="footnotes">
  <hr />
  <ol>
    <li id="fn-1">
      <p>Footnote text.</p>
      <a href="#fnref-1" class="footnote-backref">&#8617;</a>
      <a href="#fnref-1-1" class="footnote-backref">&#8617;</a>
    </li>
  </ol>
</div>
.
Document[0, 74]
  Paragraph[0, 36] isTrailingBlankLine
    Text[0, 29] chars:[0, 29, "Dupli … rence"]
    Footnote[29, 34] ordinal: 1  textOpen:[29, 31, "[^"] text:[31, 33, "id"] textClose:[33, 34, "]"]
      Text[31, 33] chars:[31, 33, "id"]
    Text[34, 35] chars:[34, 35, "."]
  Paragraph[37, 52] isTrailingBlankLine
    Text[37, 46] chars:[37, 46, "reference"]
    Footnote[46, 51] ordinal: 1  textOpen:[46, 48, "[^"] text:[48, 50, "id"] textClose:[50, 51, "]"]
      Text[48, 50] chars:[48, 50, "id"]
  FootnoteBlock[53, 74] ordinal: 1  open:[53, 55] text:[55, 57] close:[57, 59] footnote:[60, 74]
    Paragraph[60, 74]
      Text[60, 74] chars:[60, 74, "Footn … text."]
````````````````````````````````


