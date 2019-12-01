---
title: Zzzzzz Extension Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Zzzzzz

Converts zzzzzz text to Zzzzzz nodes.

no spaces between brackets

```````````````````````````````` example(Zzzzzz: 1) options(option1, IGNORE)
Sample  text
.
<p>Expected rendered HTML</p>
.
````````````````````````````````


```````````````````````````````` example(Zzzzzz: 2) options(FAIL)
&#X01; &#X234564; fail
.
<p> �</p>
.
````````````````````````````````


```````````````````````````````` example Zzzzzz: 3
> I said *Hello* to him.
.
<blockquote>
  <p>I said <em>Hello</em> to him.</p>
</blockquote>
.
Document[0, 24]
  BlockQuote[0, 24] marker:[0, 1, ">"]
    Paragraph[2, 24]
      TextBase[2, 9] chars:[2, 9, "I said "]
        Text[2, 9] chars:[2, 9, "I said "]
      Emphasis[9, 16] textOpen:[9, 10, "*"] text:[10, 15, "Hello"] textClose:[15, 16, "*"]
        TextBase[10, 15] chars:[10, 15, "Hello"]
          Text[10, 15] chars:[10, 15, "Hello"]
      TextBase[16, 24] chars:[16, 24, " to him."]
        Text[16, 24] chars:[16, 24, " to him."]
````````````````````````````````


```````````````````````````````` example Zzzzzz: 4
(<ul><li>item1</li><li>item2</li></ul>)
.
<p>(<ul><li>item1</li><li>item2</li></ul>)</p>
.
Document[0, 39]
  Paragraph[0, 39]
    TextBase[0, 1] chars:[0, 1, "("]
      Text[0, 1] chars:[0, 1, "("]
    HtmlInline[1, 5] chars:[1, 5, "<ul>"]
    HtmlInline[5, 9] chars:[5, 9, "<li>"]
    TextBase[9, 14] chars:[9, 14, "item1"]
      Text[9, 14] chars:[9, 14, "item1"]
    HtmlInline[14, 19] chars:[14, 19, "</li>"]
    HtmlInline[19, 23] chars:[19, 23, "<li>"]
    TextBase[23, 28] chars:[23, 28, "item2"]
      Text[23, 28] chars:[23, 28, "item2"]
    HtmlInline[28, 33] chars:[28, 33, "</li>"]
    HtmlInline[33, 38] chars:[33, 38, "</ul>"]
    TextBase[38, 39] chars:[38, 39, ")"]
      Text[38, 39] chars:[38, 39, ")"]
````````````````````````````````


```````````````````````````````` example Zzzzzz: 5
| col 1 | col 2 | col 3 |
| ----- | ----- | ----- |
| <ul><li>ul item</li></ul> is *different* than <ol><li>ol item</li></ol> | row 1 col 2 | row 1 col 3 |
.
<table>
  <thead>
    <tr><th>col 1</th><th>col 2</th><th>col 3</th></tr>
  </thead>
  <tbody>
    <tr><td><ul><li>ul item</li></ul> is <em>different</em> than <ol><li>ol item</li></ol></td><td>row 1 col 2</td><td>row 1 col 3</td></tr>
  </tbody>
</table>
.
Document[0, 155]
  TableBlock[0, 155]
    TableHead[0, 25]
      TableRow[0, 25] rowNumber=1
        TableCell[0, 9] header textOpen:[0, 1, "|"] text:[2, 7, "col 1"] textClose:[8, 9, "|"]
          TextBase[2, 7] chars:[2, 7, "col 1"]
            Text[2, 7] chars:[2, 7, "col 1"]
        TableCell[9, 17] header text:[10, 15, "col 2"] textClose:[16, 17, "|"]
          TextBase[10, 15] chars:[10, 15, "col 2"]
            Text[10, 15] chars:[10, 15, "col 2"]
        TableCell[17, 25] header text:[18, 23, "col 3"] textClose:[24, 25, "|"]
          TextBase[18, 23] chars:[18, 23, "col 3"]
            Text[18, 23] chars:[18, 23, "col 3"]
    TableSeparator[26, 51]
      TableRow[26, 51]
        TableCell[26, 35] textOpen:[26, 27, "|"] text:[28, 33, "-----"] textClose:[34, 35, "|"]
          Text[28, 33] chars:[28, 33, "-----"]
        TableCell[35, 43] text:[36, 41, "-----"] textClose:[42, 43, "|"]
          Text[36, 41] chars:[36, 41, "-----"]
        TableCell[43, 51] text:[44, 49, "-----"] textClose:[50, 51, "|"]
          Text[44, 49] chars:[44, 49, "-----"]
    TableBody[52, 155]
      TableRow[52, 155] rowNumber=1
        TableCell[52, 127] textOpen:[52, 53, "|"] text:[54, 125, "<ul><li>ul item</li></ul> is *different* than <ol><li>ol item</li></ol>"] textClose:[126, 127, "|"]
          HtmlInline[54, 58] chars:[54, 58, "<ul>"]
          HtmlInline[58, 62] chars:[58, 62, "<li>"]
          TextBase[62, 69] chars:[62, 69, "ul item"]
            Text[62, 69] chars:[62, 69, "ul item"]
          HtmlInline[69, 74] chars:[69, 74, "</li>"]
          HtmlInline[74, 79] chars:[74, 79, "</ul>"]
          TextBase[79, 83] chars:[79, 83, " is "]
            Text[79, 83] chars:[79, 83, " is "]
          Emphasis[83, 94] textOpen:[83, 84, "*"] text:[84, 93, "different"] textClose:[93, 94, "*"]
            TextBase[84, 93] chars:[84, 93, "different"]
              Text[84, 93] chars:[84, 93, "different"]
          TextBase[94, 100] chars:[94, 100, " than "]
            Text[94, 100] chars:[94, 100, " than "]
          HtmlInline[100, 104] chars:[100, 104, "<ol>"]
          HtmlInline[104, 108] chars:[104, 108, "<li>"]
          TextBase[108, 115] chars:[108, 115, "ol item"]
            Text[108, 115] chars:[108, 115, "ol item"]
          HtmlInline[115, 120] chars:[115, 120, "</li>"]
          HtmlInline[120, 125] chars:[120, 125, "</ol>"]
          TextBase[125, 125]
            Text[125, 125]
        TableCell[127, 141] text:[128, 139, "row 1 col 2"] textClose:[140, 141, "|"]
          TextBase[128, 139] chars:[128, 139, "row 1 … col 2"]
            Text[128, 139] chars:[128, 139, "row 1 … col 2"]
        TableCell[141, 155] text:[142, 153, "row 1 col 3"] textClose:[154, 155, "|"]
          TextBase[142, 153] chars:[142, 153, "row 1 … col 3"]
            Text[142, 153] chars:[142, 153, "row 1 … col 3"]
````````````````````````````````


```````````````````````````````` example Zzzzzz: 6
| col 1 | col 2 | col 3 |
| ----- | ----- | ----- |
| <ul><li>item1</li><li>item2</li></ul> | See the list | from the first column |
.
<table>
  <thead>
    <tr><th>col 1</th><th>col 2</th><th>col 3</th></tr>
  </thead>
  <tbody>
    <tr><td><ul><li>item1</li><li>item2</li></ul></td><td>See the list</td><td>from the first column</td></tr>
  </tbody>
</table>
.
Document[0, 132]
  TableBlock[0, 132]
    TableHead[0, 25]
      TableRow[0, 25] rowNumber=1
        TableCell[0, 9] header textOpen:[0, 1, "|"] text:[2, 7, "col 1"] textClose:[8, 9, "|"]
          TextBase[2, 7] chars:[2, 7, "col 1"]
            Text[2, 7] chars:[2, 7, "col 1"]
        TableCell[9, 17] header text:[10, 15, "col 2"] textClose:[16, 17, "|"]
          TextBase[10, 15] chars:[10, 15, "col 2"]
            Text[10, 15] chars:[10, 15, "col 2"]
        TableCell[17, 25] header text:[18, 23, "col 3"] textClose:[24, 25, "|"]
          TextBase[18, 23] chars:[18, 23, "col 3"]
            Text[18, 23] chars:[18, 23, "col 3"]
    TableSeparator[26, 51]
      TableRow[26, 51]
        TableCell[26, 35] textOpen:[26, 27, "|"] text:[28, 33, "-----"] textClose:[34, 35, "|"]
          Text[28, 33] chars:[28, 33, "-----"]
        TableCell[35, 43] text:[36, 41, "-----"] textClose:[42, 43, "|"]
          Text[36, 41] chars:[36, 41, "-----"]
        TableCell[43, 51] text:[44, 49, "-----"] textClose:[50, 51, "|"]
          Text[44, 49] chars:[44, 49, "-----"]
    TableBody[52, 132]
      TableRow[52, 132] rowNumber=1
        TableCell[52, 93] textOpen:[52, 53, "|"] text:[54, 91, "<ul><li>item1</li><li>item2</li></ul>"] textClose:[92, 93, "|"]
          HtmlInline[54, 58] chars:[54, 58, "<ul>"]
          HtmlInline[58, 62] chars:[58, 62, "<li>"]
          TextBase[62, 67] chars:[62, 67, "item1"]
            Text[62, 67] chars:[62, 67, "item1"]
          HtmlInline[67, 72] chars:[67, 72, "</li>"]
          HtmlInline[72, 76] chars:[72, 76, "<li>"]
          TextBase[76, 81] chars:[76, 81, "item2"]
            Text[76, 81] chars:[76, 81, "item2"]
          HtmlInline[81, 86] chars:[81, 86, "</li>"]
          HtmlInline[86, 91] chars:[86, 91, "</ul>"]
          TextBase[91, 91]
            Text[91, 91]
        TableCell[93, 108] text:[94, 106, "See the list"] textClose:[107, 108, "|"]
          TextBase[94, 106] chars:[94, 106, "See t …  list"]
            Text[94, 106] chars:[94, 106, "See t …  list"]
        TableCell[108, 132] text:[109, 130, "from the first column"] textClose:[131, 132, "|"]
          TextBase[109, 130] chars:[109, 130, "from  … olumn"]
            Text[109, 130] chars:[109, 130, "from  … olumn"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos, IGNORE)
.
.
````````````````````````````````


