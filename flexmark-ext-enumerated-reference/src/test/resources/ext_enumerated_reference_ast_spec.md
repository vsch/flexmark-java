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

[@type]: Type content [#]

Where [#] is replaced by the ordinal for the actual reference in the document. [@] is equivalent
to [@] when there is no id. It is treated as a placeholder for the ordinal number for the given
type. Outside of a enumerated reference definition it will render `0` 

no spaces between brackets

```````````````````````````````` example Enumerated Reference: 1
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
<span>Figure 1.</span></p>
<p><img src="http://example.com/test.png" alt="Fig" id="fig:test2" /><br />
<span>Figure 2.</span></p>
<table id="tbl:test">
  <thead>
    <tr><th>table</th></tr>
  </thead>
  <tbody>
    <tr><td>data</td></tr>
  </tbody>
  <caption><span>Table 1.</span> caption</caption>
</table>
<p></p>
<p>See <a href="#fig:test2"><span>Figure 2.</span></a></p>
<p>See <a href="#fig:test"><span>Figure 1.</span></a></p>
<p>See <a href="#tbl:test"><span>Table 1.</span></a></p>
.
Document[0, 285]
  Paragraph[0, 61] isTrailingBlankLine
    Image[0, 35] textOpen:[0, 2, "!["] text:[2, 5, "Fig"] textClose:[5, 6, "]"] linkOpen:[6, 7, "("] url:[7, 34, "http://example.com/test.png"] pageRef:[7, 34, "http://example.com/test.png"] linkClose:[34, 35, ")"]
      Text[2, 5] chars:[2, 5, "Fig"]
    AttributesNode[35, 46] textOpen:[35, 36, "{"] text:[36, 45, "#fig:test"] textClose:[45, 46, "}"]
      AttributeNode[36, 45] value:[36, 45, "#fig:test"] isId
    HardLineBreak[46, 49]
    EnumeratedReferenceText[49, 60] textOpen:[49, 51, "[#"] text:[51, 59, "fig:test"] textClose:[59, 60, "]"]
      Text[51, 59] chars:[51, 59, "fig:test"]
  Paragraph[62, 125] isTrailingBlankLine
    Image[62, 97] textOpen:[62, 64, "!["] text:[64, 67, "Fig"] textClose:[67, 68, "]"] linkOpen:[68, 69, "("] url:[69, 96, "http://example.com/test.png"] pageRef:[69, 96, "http://example.com/test.png"] linkClose:[96, 97, ")"]
      Text[64, 67] chars:[64, 67, "Fig"]
    AttributesNode[97, 109] textOpen:[97, 98, "{"] text:[98, 108, "#fig:test2"] textClose:[108, 109, "}"]
      AttributeNode[98, 108] value:[98, 108, "#fig:test2"] isId
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
      AttributeNode[179, 188] value:[179, 188, "#tbl:test"] isId
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
    Paragraph[252, 264] isTrailingBlankLine
      Text[252, 259] chars:[252, 259, "Figure "]
      EnumeratedReferenceText[259, 262] textOpen:[259, 261, "[#"] text:[261, 261] textClose:[261, 262, "]"]
      Text[262, 263] chars:[262, 263, "."]
  EnumeratedReferenceBlock[265, 284] open:[265, 267] text:[267, 270] close:[270, 272] enumeratedReference:[273, 284]
    Paragraph[273, 284] isTrailingBlankLine
      Text[273, 279] chars:[273, 279, "Table "]
      EnumeratedReferenceText[279, 282] textOpen:[279, 281, "[#"] text:[281, 281] textClose:[281, 282, "]"]
      Text[282, 283] chars:[282, 283, "."]
````````````````````````````````

