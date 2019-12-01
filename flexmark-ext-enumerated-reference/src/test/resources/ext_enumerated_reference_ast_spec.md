---
title: EnumeratedReference Extension Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Enumerated Reference

Converts `[@type:reference]` to enumerated reference based on type pattern defined in enumerated
reference definition of the form:

    Type content [#]

Where [#] is replaced by the ordinal for the actual reference in the document. [@] is equivalent
to [@] when there is no id. It is treated as a placeholder for the ordinal number for the given
type. Outside of a enumerated reference definition it will render `0`

:information_source: `reference` must not start with a digit.

no spaces between brackets

```````````````````````````````` example Enumerated Reference: 1
![Fig](http://example.com/test.png){#fig:test}  
[#fig:test]

![Fig](http://example.com/test.png){#fig:test2}  
[#fig:test2]

| table |
|-------|
| data  |
[[#tbl:test] caption]
{#tbl:test}

See [@fig:test2]

See [@fig:test]

See [@tbl:test]


[@fig]: Figure [#].

[@tbl]: Table [#].

.
<p><img src="http://example.com/test.png" alt="Fig" id="fig:test" /><br />
Figure 1.</p>
<p><img src="http://example.com/test.png" alt="Fig" id="fig:test2" /><br />
Figure 2.</p>
<table id="tbl:test">
  <thead>
    <tr><th>table</th></tr>
  </thead>
  <tbody>
    <tr><td>data</td></tr>
  </tbody>
  <caption>Table 1. caption</caption>
</table>
<p>See <a href="#fig:test2" title="Figure 2.">Figure 2.</a></p>
<p>See <a href="#fig:test" title="Figure 1.">Figure 1.</a></p>
<p>See <a href="#tbl:test" title="Table 1.">Table 1.</a></p>
.
Document[0, 285]
  Paragraph[0, 61] isTrailingBlankLine
    Image[0, 35] textOpen:[0, 2, "!["] text:[2, 5, "Fig"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 34, "http://example.com/test.png"] pageRef:[7, 34, "http://example.com/test.png"] linkClose:[34, 35, ")"]
      Text[2, 5] chars:[2, 5, "Fig"]
    AttributesNode[35, 46] textOpen:[35, 36, "{"] text:[36, 45, "#fig:test"] textClose:[45, 46, "}"]
      AttributeNode[36, 45] name:[36, 37, "#"] value:[37, 45, "fig:test"] isImplicit isId
    HardLineBreak[46, 49]
    EnumeratedReferenceText[49, 60] textOpen:[49, 51, "[#"] text:[51, 59, "fig:test"] textClose:[59, 60, "]"]
      Text[51, 59] chars:[51, 59, "fig:test"]
  Paragraph[62, 125] isTrailingBlankLine
    Image[62, 97] textOpen:[62, 64, "!["] text:[64, 67, "Fig"] textClose:[67, 68, "]"] linkOpen:[68, 69, "("] url:[69, 96, "http://example.com/test.png"] pageRef:[69, 96, "http://example.com/test.png"] linkClose:[96, 97, ")"]
      Text[64, 67] chars:[64, 67, "Fig"]
    AttributesNode[97, 109] textOpen:[97, 98, "{"] text:[98, 108, "#fig:test2"] textClose:[108, 109, "}"]
      AttributeNode[98, 108] name:[98, 99, "#"] value:[99, 108, "fig:test2"] isImplicit isId
    HardLineBreak[109, 112]
    EnumeratedReferenceText[112, 124] textOpen:[112, 114, "[#"] text:[114, 123, "fig:test2"] textClose:[123, 124, "]"]
      Text[114, 123] chars:[114, 123, "fig:test2"]
  TableBlock[126, 177]
    TableHead[126, 135]
      TableRow[126, 135] rowNumber=1
        TableCell[126, 135] header textOpen:[126, 127, "|"] text:[128, 133, "table"] textClose:[134, 135, "|"]
          Text[128, 133] chars:[128, 133, "table"]
    TableSeparator[136, 145]
      TableRow[136, 145]
        TableCell[136, 145] textOpen:[136, 137, "|"] text:[137, 144, "-------"] textClose:[144, 145, "|"]
          Text[137, 144] chars:[137, 144, "-------"]
    TableBody[146, 155]
      TableRow[146, 155] rowNumber=1
        TableCell[146, 155] textOpen:[146, 147, "|"] text:[148, 152, "data"] textClose:[154, 155, "|"]
          Text[148, 152] chars:[148, 152, "data"]
    TableCaption[156, 177] textOpen:[156, 157, "["] text:[157, 176, "[#tbl:test] caption"] textClose:[176, 177, "]"]
      EnumeratedReferenceText[157, 168] textOpen:[157, 159, "[#"] text:[159, 167, "tbl:test"] textClose:[167, 168, "]"]
        Text[159, 167] chars:[159, 167, "tbl:test"]
      Text[168, 176] chars:[168, 176, " caption"]
  Paragraph[178, 190] isTrailingBlankLine
    AttributesNode[178, 189] textOpen:[178, 179, "{"] text:[179, 188, "#tbl:test"] textClose:[188, 189, "}"]
      AttributeNode[179, 188] name:[179, 180, "#"] value:[180, 188, "tbl:test"] isImplicit isId
  Paragraph[191, 208] isTrailingBlankLine
    Text[191, 195] chars:[191, 195, "See "]
    EnumeratedReferenceLink[195, 207] textOpen:[195, 197, "[@"] text:[197, 206, "fig:test2"] textClose:[206, 207, "]"]
      Text[197, 206] chars:[197, 206, "fig:test2"]
  Paragraph[209, 225] isTrailingBlankLine
    Text[209, 213] chars:[209, 213, "See "]
    EnumeratedReferenceLink[213, 224] textOpen:[213, 215, "[@"] text:[215, 223, "fig:test"] textClose:[223, 224, "]"]
      Text[215, 223] chars:[215, 223, "fig:test"]
  Paragraph[226, 242] isTrailingBlankLine
    Text[226, 230] chars:[226, 230, "See "]
    EnumeratedReferenceLink[230, 241] textOpen:[230, 232, "[@"] text:[232, 240, "tbl:test"] textClose:[240, 241, "]"]
      Text[232, 240] chars:[232, 240, "tbl:test"]
  EnumeratedReferenceBlock[244, 264] open:[244, 246] text:[246, 249] close:[249, 251] enumeratedReference:[252, 264]
    Paragraph[252, 264]
      Text[252, 259] chars:[252, 259, "Figure "]
      EnumeratedReferenceText[259, 262] textOpen:[259, 261, "[#"] text:[261, 261] textClose:[261, 262, "]"]
      Text[262, 263] chars:[262, 263, "."]
  EnumeratedReferenceBlock[265, 284] open:[265, 267] text:[267, 270] close:[270, 272] enumeratedReference:[273, 284]
    Paragraph[273, 284]
      Text[273, 279] chars:[273, 279, "Table "]
      EnumeratedReferenceText[279, 282] textOpen:[279, 281, "[#"] text:[281, 281] textClose:[281, 282, "]"]
      Text[282, 283] chars:[282, 283, "."]
````````````````````````````````


missing enum ref definition

```````````````````````````````` example Enumerated Reference: 2
abc{#fig:test}

[#fig:test]

[@fig:test]

.
<p><span id="fig:test">abc</span></p>
<p>fig 1</p>
<p><a href="#fig:test" title="fig 1">fig 1</a></p>
.
Document[0, 42]
  Paragraph[0, 15] isTrailingBlankLine
    TextBase[0, 3] chars:[0, 3, "abc"]
      Text[0, 3] chars:[0, 3, "abc"]
    AttributesNode[3, 14] textOpen:[3, 4, "{"] text:[4, 13, "#fig:test"] textClose:[13, 14, "}"]
      AttributeNode[4, 13] name:[4, 5, "#"] value:[5, 13, "fig:test"] isImplicit isId
  Paragraph[16, 28] isTrailingBlankLine
    EnumeratedReferenceText[16, 27] textOpen:[16, 18, "[#"] text:[18, 26, "fig:test"] textClose:[26, 27, "]"]
      Text[18, 26] chars:[18, 26, "fig:test"]
  Paragraph[29, 41] isTrailingBlankLine
    EnumeratedReferenceLink[29, 40] textOpen:[29, 31, "[@"] text:[31, 39, "fig:test"] textClose:[39, 40, "]"]
      Text[31, 39] chars:[31, 39, "fig:test"]
````````````````````````````````


starting with digit is not a reference

```````````````````````````````` example Enumerated Reference: 3
[#123, GitHub Issue]

[#123, GitHub Issue]: https://github.com/vsch/flexmark-java/issues/123

.
<p><a href="https://github.com/vsch/flexmark-java/issues/123">#123, GitHub Issue</a></p>
.
Document[0, 94]
  Paragraph[0, 21] isTrailingBlankLine
    LinkRef[0, 20] referenceOpen:[0, 1, "["] reference:[1, 19, "#123, GitHub Issue"] referenceClose:[19, 20, "]"]
      Text[1, 19] chars:[1, 19, "#123, … Issue"]
  Reference[22, 92] refOpen:[22, 23, "["] ref:[23, 41, "#123, GitHub Issue"] refClose:[41, 43, "]:"] url:[44, 92, "https://github.com/vsch/flexmark-java/issues/123"]
````````````````````````````````


## Heading

Allow using empty format ref in heading

```````````````````````````````` example Heading: 1
# [#hdr] Numbered Heading
    
[@hdr]: [#].

.
<h1>1. Numbered Heading</h1>
.
Document[0, 45]
  Heading[0, 25] textOpen:[0, 1, "#"] text:[2, 25, "[#hdr] Numbered Heading"]
    EnumeratedReferenceText[2, 8] textOpen:[2, 4, "[#"] text:[4, 7, "hdr"] textClose:[7, 8, "]"]
      Text[4, 7] chars:[4, 7, "hdr"]
    Text[8, 25] chars:[8, 25, " Numb … ading"]
  EnumeratedReferenceBlock[31, 44] open:[31, 33] text:[33, 36] close:[36, 38] enumeratedReference:[39, 44]
    Paragraph[39, 44]
      EnumeratedReferenceText[39, 42] textOpen:[39, 41, "[#"] text:[41, 41] textClose:[41, 42, "]"]
      Text[42, 43] chars:[42, 43, "."]
````````````````````````````````


```````````````````````````````` example Heading: 2
# [#hdr] Numbered Heading
    
# [#hdr] Numbered Heading
    
[@hdr]: [#].

.
<h1>1. Numbered Heading</h1>
<h1>2. Numbered Heading</h1>
.
Document[0, 76]
  Heading[0, 25] textOpen:[0, 1, "#"] text:[2, 25, "[#hdr] Numbered Heading"]
    EnumeratedReferenceText[2, 8] textOpen:[2, 4, "[#"] text:[4, 7, "hdr"] textClose:[7, 8, "]"]
      Text[4, 7] chars:[4, 7, "hdr"]
    Text[8, 25] chars:[8, 25, " Numb … ading"]
  Heading[31, 56] textOpen:[31, 32, "#"] text:[33, 56, "[#hdr] Numbered Heading"]
    EnumeratedReferenceText[33, 39] textOpen:[33, 35, "[#"] text:[35, 38, "hdr"] textClose:[38, 39, "]"]
      Text[35, 38] chars:[35, 38, "hdr"]
    Text[39, 56] chars:[39, 56, " Numb … ading"]
  EnumeratedReferenceBlock[62, 75] open:[62, 64] text:[64, 67] close:[67, 69] enumeratedReference:[70, 75]
    Paragraph[70, 75]
      EnumeratedReferenceText[70, 73] textOpen:[70, 72, "[#"] text:[72, 72] textClose:[72, 73, "]"]
      Text[73, 74] chars:[73, 74, "."]
````````````````````````````````


Compound numbering

```````````````````````````````` example Heading: 3
## [#hdr1:hdr2:] Numbered Heading 0.1
    
# [#hdr1] Numbered Heading 1
    
## [#hdr1:hdr2:] Numbered Heading 1.1
    
# [#hdr1] Numbered Heading 2
    
## [#hdr1:hdr2:] Numbered Heading 2.1
    
[@hdr1]: [#].
    
[@hdr2]: [#].

.
<h2>0.1. Numbered Heading 0.1</h2>
<h1>1. Numbered Heading 1</h1>
<h2>1.1. Numbered Heading 1.1</h2>
<h1>2. Numbered Heading 2</h1>
<h2>2.1. Numbered Heading 2.1</h2>
.
Document[0, 231]
  Heading[0, 37] textOpen:[0, 2, "##"] text:[3, 37, "[#hdr1:hdr2:] Numbered Heading 0.1"]
    EnumeratedReferenceText[3, 16] textOpen:[3, 5, "[#"] text:[5, 15, "hdr1:hdr2:"] textClose:[15, 16, "]"]
      Text[5, 15] chars:[5, 15, "hdr1:hdr2:"]
    Text[16, 37] chars:[16, 37, " Numb … g 0.1"]
  Heading[43, 71] textOpen:[43, 44, "#"] text:[45, 71, "[#hdr1] Numbered Heading 1"]
    EnumeratedReferenceText[45, 52] textOpen:[45, 47, "[#"] text:[47, 51, "hdr1"] textClose:[51, 52, "]"]
      Text[47, 51] chars:[47, 51, "hdr1"]
    Text[52, 71] chars:[52, 71, " Numb … ing 1"]
  Heading[77, 114] textOpen:[77, 79, "##"] text:[80, 114, "[#hdr1:hdr2:] Numbered Heading 1.1"]
    EnumeratedReferenceText[80, 93] textOpen:[80, 82, "[#"] text:[82, 92, "hdr1:hdr2:"] textClose:[92, 93, "]"]
      Text[82, 92] chars:[82, 92, "hdr1:hdr2:"]
    Text[93, 114] chars:[93, 114, " Numb … g 1.1"]
  Heading[120, 148] textOpen:[120, 121, "#"] text:[122, 148, "[#hdr1] Numbered Heading 2"]
    EnumeratedReferenceText[122, 129] textOpen:[122, 124, "[#"] text:[124, 128, "hdr1"] textClose:[128, 129, "]"]
      Text[124, 128] chars:[124, 128, "hdr1"]
    Text[129, 148] chars:[129, 148, " Numb … ing 2"]
  Heading[154, 191] textOpen:[154, 156, "##"] text:[157, 191, "[#hdr1:hdr2:] Numbered Heading 2.1"]
    EnumeratedReferenceText[157, 170] textOpen:[157, 159, "[#"] text:[159, 169, "hdr1:hdr2:"] textClose:[169, 170, "]"]
      Text[159, 169] chars:[159, 169, "hdr1:hdr2:"]
    Text[170, 191] chars:[170, 191, " Numb … g 2.1"]
  EnumeratedReferenceBlock[197, 211] open:[197, 199] text:[199, 203] close:[203, 205] enumeratedReference:[206, 211]
    Paragraph[206, 211]
      EnumeratedReferenceText[206, 209] textOpen:[206, 208, "[#"] text:[208, 208] textClose:[208, 209, "]"]
      Text[209, 210] chars:[209, 210, "."]
  EnumeratedReferenceBlock[216, 230] open:[216, 218] text:[218, 222] close:[222, 224] enumeratedReference:[225, 230]
    Paragraph[225, 230]
      EnumeratedReferenceText[225, 228] textOpen:[225, 227, "[#"] text:[227, 227] textClose:[227, 228, "]"]
      Text[228, 229] chars:[228, 229, "."]
````````````````````````````````


`.` appended by default if last element for format is empty Enumerated Reference Text or Link

```````````````````````````````` example Heading: 4
# [#hdr1] Numbered Heading 1
    
## [#hdr1:hdr2:] Numbered Heading 1.1
    
# [#hdr1] Numbered Heading 2
    
## [#hdr1:hdr2:] Numbered Heading 2.1
    
[@hdr1]: [#]
    
[@hdr2]: [#]

.
<h1>1 Numbered Heading 1</h1>
<h2>1.1 Numbered Heading 1.1</h2>
<h1>2 Numbered Heading 2</h1>
<h2>2.1 Numbered Heading 2.1</h2>
.
Document[0, 186]
  Heading[0, 28] textOpen:[0, 1, "#"] text:[2, 28, "[#hdr1] Numbered Heading 1"]
    EnumeratedReferenceText[2, 9] textOpen:[2, 4, "[#"] text:[4, 8, "hdr1"] textClose:[8, 9, "]"]
      Text[4, 8] chars:[4, 8, "hdr1"]
    Text[9, 28] chars:[9, 28, " Numb … ing 1"]
  Heading[34, 71] textOpen:[34, 36, "##"] text:[37, 71, "[#hdr1:hdr2:] Numbered Heading 1.1"]
    EnumeratedReferenceText[37, 50] textOpen:[37, 39, "[#"] text:[39, 49, "hdr1:hdr2:"] textClose:[49, 50, "]"]
      Text[39, 49] chars:[39, 49, "hdr1:hdr2:"]
    Text[50, 71] chars:[50, 71, " Numb … g 1.1"]
  Heading[77, 105] textOpen:[77, 78, "#"] text:[79, 105, "[#hdr1] Numbered Heading 2"]
    EnumeratedReferenceText[79, 86] textOpen:[79, 81, "[#"] text:[81, 85, "hdr1"] textClose:[85, 86, "]"]
      Text[81, 85] chars:[81, 85, "hdr1"]
    Text[86, 105] chars:[86, 105, " Numb … ing 2"]
  Heading[111, 148] textOpen:[111, 113, "##"] text:[114, 148, "[#hdr1:hdr2:] Numbered Heading 2.1"]
    EnumeratedReferenceText[114, 127] textOpen:[114, 116, "[#"] text:[116, 126, "hdr1:hdr2:"] textClose:[126, 127, "]"]
      Text[116, 126] chars:[116, 126, "hdr1:hdr2:"]
    Text[127, 148] chars:[127, 148, " Numb … g 2.1"]
  EnumeratedReferenceBlock[154, 167] open:[154, 156] text:[156, 160] close:[160, 162] enumeratedReference:[163, 167]
    Paragraph[163, 167]
      EnumeratedReferenceText[163, 166] textOpen:[163, 165, "[#"] text:[165, 165] textClose:[165, 166, "]"]
  EnumeratedReferenceBlock[172, 185] open:[172, 174] text:[174, 178] close:[178, 180] enumeratedReference:[181, 185]
    Paragraph[181, 185]
      EnumeratedReferenceText[181, 184] textOpen:[181, 183, "[#"] text:[183, 183] textClose:[183, 184, "]"]
````````````````````````````````


```````````````````````````````` example Heading: 5
# [#hd1] Heading 1

# [#hd1] Heading 2

# [#hd1] Heading 3

[@hd1]: [#].
.
<h1>1. Heading 1</h1>
<h1>2. Heading 2</h1>
<h1>3. Heading 3</h1>
.
Document[0, 72]
  Heading[0, 18] textOpen:[0, 1, "#"] text:[2, 18, "[#hd1] Heading 1"]
    EnumeratedReferenceText[2, 8] textOpen:[2, 4, "[#"] text:[4, 7, "hd1"] textClose:[7, 8, "]"]
      Text[4, 7] chars:[4, 7, "hd1"]
    Text[8, 18] chars:[8, 18, " Heading 1"]
  Heading[20, 38] textOpen:[20, 21, "#"] text:[22, 38, "[#hd1] Heading 2"]
    EnumeratedReferenceText[22, 28] textOpen:[22, 24, "[#"] text:[24, 27, "hd1"] textClose:[27, 28, "]"]
      Text[24, 27] chars:[24, 27, "hd1"]
    Text[28, 38] chars:[28, 38, " Heading 2"]
  Heading[40, 58] textOpen:[40, 41, "#"] text:[42, 58, "[#hd1] Heading 3"]
    EnumeratedReferenceText[42, 48] textOpen:[42, 44, "[#"] text:[44, 47, "hd1"] textClose:[47, 48, "]"]
      Text[44, 47] chars:[44, 47, "hd1"]
    Text[48, 58] chars:[48, 58, " Heading 3"]
  EnumeratedReferenceBlock[60, 72] open:[60, 62] text:[62, 65] close:[65, 67] enumeratedReference:[68, 72]
    Paragraph[68, 72]
      EnumeratedReferenceText[68, 71] textOpen:[68, 70, "[#"] text:[70, 70] textClose:[70, 71, "]"]
      Text[71, 72] chars:[71, 72, "."]
````````````````````````````````


```````````````````````````````` example Heading: 6
# [#hd1] Heading 1

## [#hd1:hd2:] Heading 1.1

### [#hd1:hd2:hd3:] Heading 1.1.1

### [#hd1:hd2:hd3:] Heading 1.1.2

## [#hd1:hd2:] Heading 1.2

### [#hd1:hd2:hd3:] Heading 1.2.1

### [#hd1:hd2:hd3:] Heading 1.2.2

# [#hd1] Heading 2

## [#hd1:hd2:] Heading 2.1

### [#hd1:hd2:hd3:] Heading 2.1.1

### [#hd1:hd2:hd3:] Heading 2.1.2

[@hd1]: [#].
[@hd2]: [#].
[@hd3]: [#].
.
<h1>1. Heading 1</h1>
<h2>1.1. Heading 1.1</h2>
<h3>1.1.1. Heading 1.1.1</h3>
<h3>1.1.2. Heading 1.1.2</h3>
<h2>1.2. Heading 1.2</h2>
<h3>1.2.1. Heading 1.2.1</h3>
<h3>1.2.2. Heading 1.2.2</h3>
<h1>2. Heading 2</h1>
<h2>2.1. Heading 2.1</h2>
<h3>2.1.1. Heading 2.1.1</h3>
<h3>2.1.2. Heading 2.1.2</h3>
.
Document[0, 372]
  Heading[0, 18] textOpen:[0, 1, "#"] text:[2, 18, "[#hd1] Heading 1"]
    EnumeratedReferenceText[2, 8] textOpen:[2, 4, "[#"] text:[4, 7, "hd1"] textClose:[7, 8, "]"]
      Text[4, 7] chars:[4, 7, "hd1"]
    Text[8, 18] chars:[8, 18, " Heading 1"]
  Heading[20, 46] textOpen:[20, 22, "##"] text:[23, 46, "[#hd1:hd2:] Heading 1.1"]
    EnumeratedReferenceText[23, 34] textOpen:[23, 25, "[#"] text:[25, 33, "hd1:hd2:"] textClose:[33, 34, "]"]
      Text[25, 33] chars:[25, 33, "hd1:hd2:"]
    Text[34, 46] chars:[34, 46, " Head … g 1.1"]
  Heading[48, 81] textOpen:[48, 51, "###"] text:[52, 81, "[#hd1:hd2:hd3:] Heading 1.1.1"]
    EnumeratedReferenceText[52, 67] textOpen:[52, 54, "[#"] text:[54, 66, "hd1:hd2:hd3:"] textClose:[66, 67, "]"]
      Text[54, 66] chars:[54, 66, "hd1:h … :hd3:"]
    Text[67, 81] chars:[67, 81, " Head … 1.1.1"]
  Heading[83, 116] textOpen:[83, 86, "###"] text:[87, 116, "[#hd1:hd2:hd3:] Heading 1.1.2"]
    EnumeratedReferenceText[87, 102] textOpen:[87, 89, "[#"] text:[89, 101, "hd1:hd2:hd3:"] textClose:[101, 102, "]"]
      Text[89, 101] chars:[89, 101, "hd1:h … :hd3:"]
    Text[102, 116] chars:[102, 116, " Head … 1.1.2"]
  Heading[118, 144] textOpen:[118, 120, "##"] text:[121, 144, "[#hd1:hd2:] Heading 1.2"]
    EnumeratedReferenceText[121, 132] textOpen:[121, 123, "[#"] text:[123, 131, "hd1:hd2:"] textClose:[131, 132, "]"]
      Text[123, 131] chars:[123, 131, "hd1:hd2:"]
    Text[132, 144] chars:[132, 144, " Head … g 1.2"]
  Heading[146, 179] textOpen:[146, 149, "###"] text:[150, 179, "[#hd1:hd2:hd3:] Heading 1.2.1"]
    EnumeratedReferenceText[150, 165] textOpen:[150, 152, "[#"] text:[152, 164, "hd1:hd2:hd3:"] textClose:[164, 165, "]"]
      Text[152, 164] chars:[152, 164, "hd1:h … :hd3:"]
    Text[165, 179] chars:[165, 179, " Head … 1.2.1"]
  Heading[181, 214] textOpen:[181, 184, "###"] text:[185, 214, "[#hd1:hd2:hd3:] Heading 1.2.2"]
    EnumeratedReferenceText[185, 200] textOpen:[185, 187, "[#"] text:[187, 199, "hd1:hd2:hd3:"] textClose:[199, 200, "]"]
      Text[187, 199] chars:[187, 199, "hd1:h … :hd3:"]
    Text[200, 214] chars:[200, 214, " Head … 1.2.2"]
  Heading[216, 234] textOpen:[216, 217, "#"] text:[218, 234, "[#hd1] Heading 2"]
    EnumeratedReferenceText[218, 224] textOpen:[218, 220, "[#"] text:[220, 223, "hd1"] textClose:[223, 224, "]"]
      Text[220, 223] chars:[220, 223, "hd1"]
    Text[224, 234] chars:[224, 234, " Heading 2"]
  Heading[236, 262] textOpen:[236, 238, "##"] text:[239, 262, "[#hd1:hd2:] Heading 2.1"]
    EnumeratedReferenceText[239, 250] textOpen:[239, 241, "[#"] text:[241, 249, "hd1:hd2:"] textClose:[249, 250, "]"]
      Text[241, 249] chars:[241, 249, "hd1:hd2:"]
    Text[250, 262] chars:[250, 262, " Head … g 2.1"]
  Heading[264, 297] textOpen:[264, 267, "###"] text:[268, 297, "[#hd1:hd2:hd3:] Heading 2.1.1"]
    EnumeratedReferenceText[268, 283] textOpen:[268, 270, "[#"] text:[270, 282, "hd1:hd2:hd3:"] textClose:[282, 283, "]"]
      Text[270, 282] chars:[270, 282, "hd1:h … :hd3:"]
    Text[283, 297] chars:[283, 297, " Head … 2.1.1"]
  Heading[299, 332] textOpen:[299, 302, "###"] text:[303, 332, "[#hd1:hd2:hd3:] Heading 2.1.2"]
    EnumeratedReferenceText[303, 318] textOpen:[303, 305, "[#"] text:[305, 317, "hd1:hd2:hd3:"] textClose:[317, 318, "]"]
      Text[305, 317] chars:[305, 317, "hd1:h … :hd3:"]
    Text[318, 332] chars:[318, 332, " Head … 2.1.2"]
  EnumeratedReferenceBlock[334, 347] open:[334, 336] text:[336, 339] close:[339, 341] enumeratedReference:[342, 347]
    Paragraph[342, 347]
      EnumeratedReferenceText[342, 345] textOpen:[342, 344, "[#"] text:[344, 344] textClose:[344, 345, "]"]
      Text[345, 346] chars:[345, 346, "."]
  EnumeratedReferenceBlock[347, 360] open:[347, 349] text:[349, 352] close:[352, 354] enumeratedReference:[355, 360]
    Paragraph[355, 360]
      EnumeratedReferenceText[355, 358] textOpen:[355, 357, "[#"] text:[357, 357] textClose:[357, 358, "]"]
      Text[358, 359] chars:[358, 359, "."]
  EnumeratedReferenceBlock[360, 372] open:[360, 362] text:[362, 365] close:[365, 367] enumeratedReference:[368, 372]
    Paragraph[368, 372]
      EnumeratedReferenceText[368, 371] textOpen:[368, 370, "[#"] text:[370, 370] textClose:[370, 371, "]"]
      Text[371, 372] chars:[371, 372, "."]
````````````````````````````````


No block elements, only text parsing so the following is not a list item

```````````````````````````````` example Heading: 7
# [#hd1] Heading 1

[@hd1]: * list [#]
.
<h1>* list 1 Heading 1</h1>
.
Document[0, 38]
  Heading[0, 18] textOpen:[0, 1, "#"] text:[2, 18, "[#hd1] Heading 1"]
    EnumeratedReferenceText[2, 8] textOpen:[2, 4, "[#"] text:[4, 7, "hd1"] textClose:[7, 8, "]"]
      Text[4, 7] chars:[4, 7, "hd1"]
    Text[8, 18] chars:[8, 18, " Heading 1"]
  EnumeratedReferenceBlock[20, 38] open:[20, 22] text:[22, 25] close:[25, 27] enumeratedReference:[28, 38]
    Paragraph[28, 38]
      Text[28, 35] chars:[28, 35, "* list "]
      EnumeratedReferenceText[35, 38] textOpen:[35, 37, "[#"] text:[37, 37] textClose:[37, 38, "]"]
````````````````````````````````


```````````````````````````````` example Heading: 8
# [#hd1] Heading 1

[@hd1]: *list* [#]
.
<h1><em>list</em> 1 Heading 1</h1>
.
Document[0, 38]
  Heading[0, 18] textOpen:[0, 1, "#"] text:[2, 18, "[#hd1] Heading 1"]
    EnumeratedReferenceText[2, 8] textOpen:[2, 4, "[#"] text:[4, 7, "hd1"] textClose:[7, 8, "]"]
      Text[4, 7] chars:[4, 7, "hd1"]
    Text[8, 18] chars:[8, 18, " Heading 1"]
  EnumeratedReferenceBlock[20, 38] open:[20, 22] text:[22, 25] close:[25, 27] enumeratedReference:[28, 38]
    Paragraph[28, 38]
      Emphasis[28, 34] textOpen:[28, 29, "*"] text:[29, 33, "list"] textClose:[33, 34, "*"]
        Text[29, 33] chars:[29, 33, "list"]
      Text[34, 35] chars:[34, 35, " "]
      EnumeratedReferenceText[35, 38] textOpen:[35, 37, "[#"] text:[37, 37] textClose:[37, 38, "]"]
````````````````````````````````


```````````````````````````````` example Heading: 9
# [#hd1] Heading 1

[hd1]: https://example.com
[@hd1]: *list* [#]
.
<h1><em>list</em> 1 Heading 1</h1>
.
Document[0, 65]
  Heading[0, 18] textOpen:[0, 1, "#"] text:[2, 18, "[#hd1] Heading 1"]
    EnumeratedReferenceText[2, 8] textOpen:[2, 4, "[#"] text:[4, 7, "hd1"] textClose:[7, 8, "]"]
      Text[4, 7] chars:[4, 7, "hd1"]
    Text[8, 18] chars:[8, 18, " Heading 1"]
  Reference[20, 46] refOpen:[20, 21, "["] ref:[21, 24, "hd1"] refClose:[24, 26, "]:"] url:[27, 46, "https://example.com"]
  EnumeratedReferenceBlock[47, 65] open:[47, 49] text:[49, 52] close:[52, 54] enumeratedReference:[55, 65]
    Paragraph[55, 65]
      Emphasis[55, 61] textOpen:[55, 56, "*"] text:[56, 60, "list"] textClose:[60, 61, "*"]
        Text[56, 60] chars:[56, 60, "list"]
      Text[61, 62] chars:[61, 62, " "]
      EnumeratedReferenceText[62, 65] textOpen:[62, 64, "[#"] text:[64, 64] textClose:[64, 65, "]"]
````````````````````````````````


```````````````````````````````` example Heading: 10
# [#hd1] Heading 1

[hd1]: https://example.com
[@hd1]: *list* [#]
Following paragraph text
.
<h1><em>list</em> 1 Heading 1</h1>
<p>Following paragraph text</p>
.
Document[0, 90]
  Heading[0, 18] textOpen:[0, 1, "#"] text:[2, 18, "[#hd1] Heading 1"]
    EnumeratedReferenceText[2, 8] textOpen:[2, 4, "[#"] text:[4, 7, "hd1"] textClose:[7, 8, "]"]
      Text[4, 7] chars:[4, 7, "hd1"]
    Text[8, 18] chars:[8, 18, " Heading 1"]
  Reference[20, 46] refOpen:[20, 21, "["] ref:[21, 24, "hd1"] refClose:[24, 26, "]:"] url:[27, 46, "https://example.com"]
  EnumeratedReferenceBlock[47, 66] open:[47, 49] text:[49, 52] close:[52, 54] enumeratedReference:[55, 66]
    Paragraph[55, 66]
      Emphasis[55, 61] textOpen:[55, 56, "*"] text:[56, 60, "list"] textClose:[60, 61, "*"]
        Text[56, 60] chars:[56, 60, "list"]
      Text[61, 62] chars:[61, 62, " "]
      EnumeratedReferenceText[62, 65] textOpen:[62, 64, "[#"] text:[64, 64] textClose:[64, 65, "]"]
  Paragraph[66, 90]
    Text[66, 90] chars:[66, 90, "Follo …  text"]
````````````````````````````````


## Compound

```````````````````````````````` example Compound: 1
# [#hd1] Heading 1

![image-base64.png](https://github.com/vsch/MarkdownTest/raw/master/image-base64.png) {#hd1:fig:img1} 
[#hd1:fig:img1]

# [#hd1] Heading 2

![image-base64.png](https://github.com/vsch/MarkdownTest/raw/master/image-base64.png) {#hd1:fig:img2} 
[#hd1:fig:img2]

[@hd1]: [#].
[@fig]: Figure [#].
.
<h1>1. Heading 1</h1>
<p><img src="https://github.com/vsch/MarkdownTest/raw/master/image-base64.png" alt="image-base64.png" /><span id="hd1:fig:img1"> </span>
Figure 1.1.</p>
<h1>2. Heading 2</h1>
<p><img src="https://github.com/vsch/MarkdownTest/raw/master/image-base64.png" alt="image-base64.png" /><span id="hd1:fig:img2"> </span>
Figure 2.1.</p>
.
Document[0, 312]
  Heading[0, 18] textOpen:[0, 1, "#"] text:[2, 18, "[#hd1] Heading 1"]
    EnumeratedReferenceText[2, 8] textOpen:[2, 4, "[#"] text:[4, 7, "hd1"] textClose:[7, 8, "]"]
      Text[4, 7] chars:[4, 7, "hd1"]
    Text[8, 18] chars:[8, 18, " Heading 1"]
  Paragraph[20, 139] isTrailingBlankLine
    Image[20, 105] textOpen:[20, 22, "!["] text:[22, 38, "image-base64.png"] textClose:[38, 39, "]"] linkOpen:[39, 40, "("] url:[40, 104, "https://github.com/vsch/MarkdownTest/raw/master/image-base64.png"] pageRef:[40, 104, "https://github.com/vsch/MarkdownTest/raw/master/image-base64.png"] linkClose:[104, 105, ")"]
      Text[22, 38] chars:[22, 38, "image … 4.png"]
    TextBase[105, 106] chars:[105, 106, " "]
      Text[105, 106] chars:[105, 106, " "]
    AttributesNode[106, 121] textOpen:[106, 107, "{"] text:[107, 120, "#hd1:fig:img1"] textClose:[120, 121, "}"]
      AttributeNode[107, 120] name:[107, 108, "#"] value:[108, 120, "hd1:fig:img1"] isImplicit isId
    SoftLineBreak[122, 123]
    EnumeratedReferenceText[123, 138] textOpen:[123, 125, "[#"] text:[125, 137, "hd1:fig:img1"] textClose:[137, 138, "]"]
      Text[125, 137] chars:[125, 137, "hd1:f … :img1"]
  Heading[140, 158] textOpen:[140, 141, "#"] text:[142, 158, "[#hd1] Heading 2"]
    EnumeratedReferenceText[142, 148] textOpen:[142, 144, "[#"] text:[144, 147, "hd1"] textClose:[147, 148, "]"]
      Text[144, 147] chars:[144, 147, "hd1"]
    Text[148, 158] chars:[148, 158, " Heading 2"]
  Paragraph[160, 279] isTrailingBlankLine
    Image[160, 245] textOpen:[160, 162, "!["] text:[162, 178, "image-base64.png"] textClose:[178, 179, "]"] linkOpen:[179, 180, "("] url:[180, 244, "https://github.com/vsch/MarkdownTest/raw/master/image-base64.png"] pageRef:[180, 244, "https://github.com/vsch/MarkdownTest/raw/master/image-base64.png"] linkClose:[244, 245, ")"]
      Text[162, 178] chars:[162, 178, "image … 4.png"]
    TextBase[245, 246] chars:[245, 246, " "]
      Text[245, 246] chars:[245, 246, " "]
    AttributesNode[246, 261] textOpen:[246, 247, "{"] text:[247, 260, "#hd1:fig:img2"] textClose:[260, 261, "}"]
      AttributeNode[247, 260] name:[247, 248, "#"] value:[248, 260, "hd1:fig:img2"] isImplicit isId
    SoftLineBreak[262, 263]
    EnumeratedReferenceText[263, 278] textOpen:[263, 265, "[#"] text:[265, 277, "hd1:fig:img2"] textClose:[277, 278, "]"]
      Text[265, 277] chars:[265, 277, "hd1:f … :img2"]
  EnumeratedReferenceBlock[280, 293] open:[280, 282] text:[282, 285] close:[285, 287] enumeratedReference:[288, 293]
    Paragraph[288, 293]
      EnumeratedReferenceText[288, 291] textOpen:[288, 290, "[#"] text:[290, 290] textClose:[290, 291, "]"]
      Text[291, 292] chars:[291, 292, "."]
  EnumeratedReferenceBlock[293, 312] open:[293, 295] text:[295, 298] close:[298, 300] enumeratedReference:[301, 312]
    Paragraph[301, 312]
      Text[301, 308] chars:[301, 308, "Figure "]
      EnumeratedReferenceText[308, 311] textOpen:[308, 310, "[#"] text:[310, 310] textClose:[310, 311, "]"]
      Text[311, 312] chars:[311, 312, "."]
````````````````````````````````


