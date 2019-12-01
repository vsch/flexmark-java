---
title: Typographic Extension Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## TypographicQuotes

Converts:

* single quoted `'some text'` to `&lsquo;some text&rsquo;` &lsquo;some text&rsquo;
* double quoted `"some text"` to `&ldquo;some text&rdquo;` &ldquo;some text&rdquo;
* double angle quoted `<<some text>>` to `&laquo;some text&raquo;` &laquo;some text&raquo;

not quotes

```````````````````````````````` example TypographicQuotes: 1
a'l'ordre'b
.
<p>a&rsquo;l&rsquo;ordre&rsquo;b</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 1] chars:[0, 1, "a"]
    TypographicSmarts[1, 2] typographic: &rsquo; 
    Text[2, 3] chars:[2, 3, "l"]
    TypographicSmarts[3, 4] typographic: &rsquo; 
    Text[4, 9] chars:[4, 9, "ordre"]
    TypographicSmarts[9, 10] typographic: &rsquo; 
    Text[10, 11] chars:[10, 11, "b"]
````````````````````````````````


not quoted

```````````````````````````````` example TypographicQuotes: 2
a"l"ordre"b
.
<p>a&quot;l&quot;ordre&quot;b</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 11] chars:[0, 11, "a\"l\"o … dre\"b"]
````````````````````````````````


basic quotes

```````````````````````````````` example TypographicQuotes: 3
'l'ordre'
.
<p>&lsquo;l&rsquo;ordre&rsquo;</p>
.
Document[0, 9]
  Paragraph[0, 9]
    TypographicQuotes[0, 9] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[0, 1, "'"] text:[1, 8, "l'ordre"] textClose:[8, 9, "'"]
      Text[1, 2] chars:[1, 2, "l"]
      TypographicSmarts[2, 3] typographic: &rsquo; 
      Text[3, 8] chars:[3, 8, "ordre"]
````````````````````````````````


```````````````````````````````` example TypographicQuotes: 4
'l''ordre'
.
<p>&lsquo;l''ordre&rsquo;</p>
.
Document[0, 10]
  Paragraph[0, 10]
    TypographicQuotes[0, 10] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[0, 1, "'"] text:[1, 9, "l''ordre"] textClose:[9, 10, "'"]
      Text[1, 9] chars:[1, 9, "l''ordre"]
````````````````````````````````


```````````````````````````````` example TypographicQuotes: 5
**'l'ordre'**
.
<p><strong>&lsquo;l&rsquo;ordre&rsquo;</strong></p>
.
Document[0, 13]
  Paragraph[0, 13]
    StrongEmphasis[0, 13] textOpen:[0, 2, "**"] text:[2, 11, "'l'ordre'"] textClose:[11, 13, "**"]
      TypographicQuotes[2, 11] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[2, 3, "'"] text:[3, 10, "l'ordre"] textClose:[10, 11, "'"]
        Text[3, 4] chars:[3, 4, "l"]
        TypographicSmarts[4, 5] typographic: &rsquo; 
        Text[5, 10] chars:[5, 10, "ordre"]
````````````````````````````````


```````````````````````````````` example TypographicQuotes: 6
'**l'ordre**'
.
<p>&lsquo;<strong>l&rsquo;ordre</strong>&rsquo;</p>
.
Document[0, 13]
  Paragraph[0, 13]
    TypographicQuotes[0, 13] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[0, 1, "'"] text:[1, 12, "**l'ordre**"] textClose:[12, 13, "'"]
      StrongEmphasis[1, 12] textOpen:[1, 3, "**"] text:[3, 10, "l'ordre"] textClose:[10, 12, "**"]
        Text[3, 4] chars:[3, 4, "l"]
        TypographicSmarts[4, 5] typographic: &rsquo; 
        Text[5, 10] chars:[5, 10, "ordre"]
````````````````````````````````


```````````````````````````````` example TypographicQuotes: 7
**'l''ordre'**
.
<p><strong>&lsquo;l''ordre&rsquo;</strong></p>
.
Document[0, 14]
  Paragraph[0, 14]
    StrongEmphasis[0, 14] textOpen:[0, 2, "**"] text:[2, 12, "'l''ordre'"] textClose:[12, 14, "**"]
      TypographicQuotes[2, 12] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[2, 3, "'"] text:[3, 11, "l''ordre"] textClose:[11, 12, "'"]
        Text[3, 11] chars:[3, 11, "l''ordre"]
````````````````````````````````


```````````````````````````````` example TypographicQuotes: 8
**"l'ordre"**
.
<p><strong>&ldquo;l&rsquo;ordre&rdquo;</strong></p>
.
Document[0, 13]
  Paragraph[0, 13]
    StrongEmphasis[0, 13] textOpen:[0, 2, "**"] text:[2, 11, "\"l'ordre\""] textClose:[11, 13, "**"]
      TypographicQuotes[2, 11] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[2, 3, "\""] text:[3, 10, "l'ordre"] textClose:[10, 11, "\""]
        Text[3, 4] chars:[3, 4, "l"]
        TypographicSmarts[4, 5] typographic: &rsquo; 
        Text[5, 10] chars:[5, 10, "ordre"]
````````````````````````````````


```````````````````````````````` example TypographicQuotes: 9
"**l'ordre**"
.
<p>&ldquo;<strong>l&rsquo;ordre</strong>&rdquo;</p>
.
Document[0, 13]
  Paragraph[0, 13]
    TypographicQuotes[0, 13] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[0, 1, "\""] text:[1, 12, "**l'ordre**"] textClose:[12, 13, "\""]
      StrongEmphasis[1, 12] textOpen:[1, 3, "**"] text:[3, 10, "l'ordre"] textClose:[10, 12, "**"]
        Text[3, 4] chars:[3, 4, "l"]
        TypographicSmarts[4, 5] typographic: &rsquo; 
        Text[5, 10] chars:[5, 10, "ordre"]
````````````````````````````````


```````````````````````````````` example TypographicQuotes: 10
**"l''ordre"**
.
<p><strong>&ldquo;l''ordre&rdquo;</strong></p>
.
Document[0, 14]
  Paragraph[0, 14]
    StrongEmphasis[0, 14] textOpen:[0, 2, "**"] text:[2, 12, "\"l''ordre\""] textClose:[12, 14, "**"]
      TypographicQuotes[2, 12] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[2, 3, "\""] text:[3, 11, "l''ordre"] textClose:[11, 12, "\""]
        Text[3, 11] chars:[3, 11, "l''ordre"]
````````````````````````````````


```````````````````````````````` example TypographicQuotes: 11
a's and b's
.
<p>a&rsquo;s and b&rsquo;s</p>
.
Document[0, 11]
  Paragraph[0, 11]
    Text[0, 1] chars:[0, 1, "a"]
    TypographicSmarts[1, 2] typographic: &rsquo; 
    Text[2, 9] chars:[2, 9, "s and b"]
    TypographicSmarts[9, 10] typographic: &rsquo; 
    Text[10, 11] chars:[10, 11, "s"]
````````````````````````````````


```````````````````````````````` example TypographicQuotes: 12
'Twas the night before Christmas.
.
<p>&rsquo;Twas the night before Christmas.</p>
.
Document[0, 33]
  Paragraph[0, 33]
    TypographicSmarts[0, 1] typographic: &rsquo; 
    Text[1, 33] chars:[1, 33, "Twas  … tmas."]
````````````````````````````````


```````````````````````````````` example TypographicQuotes: 13
Sample "double" 'single' <<angle>> "l'ordre" 'l'ordre' <span>test</span>
.
<p>Sample &ldquo;double&rdquo; &lsquo;single&rsquo; &laquo;angle&raquo; &ldquo;l&rsquo;ordre&rdquo; &lsquo;l&rsquo;ordre&rsquo; <span>test</span></p>
.
Document[0, 72]
  Paragraph[0, 72]
    Text[0, 7] chars:[0, 7, "Sample "]
    TypographicQuotes[7, 15] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[7, 8, "\""] text:[8, 14, "double"] textClose:[14, 15, "\""]
      Text[8, 14] chars:[8, 14, "double"]
    Text[15, 16] chars:[15, 16, " "]
    TypographicQuotes[16, 24] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[16, 17, "'"] text:[17, 23, "single"] textClose:[23, 24, "'"]
      Text[17, 23] chars:[17, 23, "single"]
    Text[24, 25] chars:[24, 25, " "]
    TypographicQuotes[25, 34] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[25, 27, "<<"] text:[27, 32, "angle"] textClose:[32, 34, ">>"]
      Text[27, 32] chars:[27, 32, "angle"]
    Text[34, 35] chars:[34, 35, " "]
    TypographicQuotes[35, 44] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[35, 36, "\""] text:[36, 43, "l'ordre"] textClose:[43, 44, "\""]
      Text[36, 37] chars:[36, 37, "l"]
      TypographicSmarts[37, 38] typographic: &rsquo; 
      Text[38, 43] chars:[38, 43, "ordre"]
    Text[44, 45] chars:[44, 45, " "]
    TypographicQuotes[45, 54] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[45, 46, "'"] text:[46, 53, "l'ordre"] textClose:[53, 54, "'"]
      Text[46, 47] chars:[46, 47, "l"]
      TypographicSmarts[47, 48] typographic: &rsquo; 
      Text[48, 53] chars:[48, 53, "ordre"]
    Text[54, 55] chars:[54, 55, " "]
    HtmlInline[55, 61] chars:[55, 61, "<span>"]
    Text[61, 65] chars:[61, 65, "test"]
    HtmlInline[65, 72] chars:[65, 72, "</span>"]
````````````````````````````````


escaped quotes

```````````````````````````````` example TypographicQuotes: 14
Sample \"double\" \'single\' \<<angle\>> \"l'ordre" \'l'ordre\'
.
<p>Sample &quot;double&quot; 'single' &lt;&lt;angle&gt;&gt; &quot;l&rsquo;ordre&quot; 'l&rsquo;ordre'</p>
.
Document[0, 63]
  Paragraph[0, 63]
    Text[0, 44] chars:[0, 44, "Sampl … > \\"l"]
    TypographicSmarts[44, 45] typographic: &rsquo; 
    Text[45, 55] chars:[45, 55, "ordre\" \'l"]
    TypographicSmarts[55, 56] typographic: &rsquo; 
    Text[56, 63] chars:[56, 63, "ordre\'"]
````````````````````````````````


escaped quotes

```````````````````````````````` example TypographicQuotes: 15
Sample \"double\" \'single\' \<<angle>\> \"l'ordre" \'l'ordre\'
.
<p>Sample &quot;double&quot; 'single' &lt;<angle>&gt; &quot;l&rsquo;ordre&quot; 'l&rsquo;ordre'</p>
.
Document[0, 63]
  Paragraph[0, 63]
    Text[0, 31] chars:[0, 31, "Sampl … \' \<"]
    HtmlInline[31, 38] chars:[31, 38, "<angle>"]
    Text[38, 44] chars:[38, 44, "\> \\"l"]
    TypographicSmarts[44, 45] typographic: &rsquo; 
    Text[45, 55] chars:[45, 55, "ordre\" \'l"]
    TypographicSmarts[55, 56] typographic: &rsquo; 
    Text[56, 63] chars:[56, 63, "ordre\'"]
````````````````````````````````


basic quotes turned off

```````````````````````````````` example(TypographicQuotes: 16) options(no-quotes)
Sample "double" 'single' <<angle>> "l'ordre" 'l'ordre'
.
<p>Sample &quot;double&quot; 'single' &lt;<angle>&gt; &quot;l'ordre&quot; 'l'ordre'</p>
.
Document[0, 54]
  Paragraph[0, 54]
    Text[0, 26] chars:[0, 26, "Sampl … le' <"]
    HtmlInline[26, 33] chars:[26, 33, "<angle>"]
    Text[33, 54] chars:[33, 54, "> \"l' … rdre'"]
````````````````````````````````


## Typographic Smarts

Converts:

* `'` to apostrophe `&apos;` &apos;
* `...` and `. . .` to ellipsis `&hellip;` &hellip;
* `--` en dash `&ndash;` &ndash;
* `---` em dash `&mdash;` &mdash;

to TypographicSmarts nodes

basic

```````````````````````````````` example Typographic Smarts: 1
Sample with l'existence, from 1...2 and so on. . . 

en--dash and em---dash
.
<p>Sample with l&rsquo;existence, from 1&hellip;2 and so on&hellip;</p>
<p>en&ndash;dash and em&mdash;dash</p>
.
Document[0, 75]
  Paragraph[0, 52] isTrailingBlankLine
    Text[0, 13] chars:[0, 13, "Sampl … ith l"]
    TypographicSmarts[13, 14] typographic: &rsquo; 
    Text[14, 31] chars:[14, 31, "exist … rom 1"]
    TypographicSmarts[31, 34] typographic: &hellip; 
    Text[34, 45] chars:[34, 45, "2 and … so on"]
    TypographicSmarts[45, 50] typographic: &hellip; 
  Paragraph[53, 75]
    Text[53, 55] chars:[53, 55, "en"]
    TypographicSmarts[55, 57] typographic: &ndash; 
    Text[57, 68] chars:[57, 68, "dash  … nd em"]
    TypographicSmarts[68, 71] typographic: &mdash; 
    Text[71, 75] chars:[71, 75, "dash"]
````````````````````````````````


escaped smarts

```````````````````````````````` example Typographic Smarts: 2
Sample with l\'existence, from 1\...2 and so on\. . . 

en\--dash and em\---dash
.
<p>Sample with l'existence, from 1...2 and so on. . .</p>
<p>en--dash and em-&ndash;dash</p>
.
Document[0, 80]
  Paragraph[0, 55] isTrailingBlankLine
    Text[0, 53] chars:[0, 53, "Sampl … . . ."]
  Paragraph[56, 80]
    Text[56, 74] chars:[56, 74, "en\-- …  em\-"]
    TypographicSmarts[74, 76] typographic: &ndash; 
    Text[76, 80] chars:[76, 80, "dash"]
````````````````````````````````


escaped smarts

```````````````````````````````` example Typographic Smarts: 3
Sample with l\'existence, from 1\...2 and so on\. . . 

en-\-dash and em-\--dash
.
<p>Sample with l'existence, from 1...2 and so on. . .</p>
<p>en--dash and em---dash</p>
.
Document[0, 80]
  Paragraph[0, 55] isTrailingBlankLine
    Text[0, 53] chars:[0, 53, "Sampl … . . ."]
  Paragraph[56, 80]
    Text[56, 80] chars:[56, 80, "en-\- … -dash"]
````````````````````````````````


escaped smarts

```````````````````````````````` example Typographic Smarts: 4
Sample with l\'existence, from 1\...2 and so on\. . . 

en-\-dash and em--\-dash
.
<p>Sample with l'existence, from 1...2 and so on. . .</p>
<p>en--dash and em&ndash;-dash</p>
.
Document[0, 80]
  Paragraph[0, 55] isTrailingBlankLine
    Text[0, 53] chars:[0, 53, "Sampl … . . ."]
  Paragraph[56, 80]
    Text[56, 72] chars:[56, 72, "en-\- … nd em"]
    TypographicSmarts[72, 74] typographic: &ndash; 
    Text[74, 80] chars:[74, 80, "\-dash"]
````````````````````````````````


basic smarts turned off

```````````````````````````````` example(Typographic Smarts: 5) options(no-smarts)
Sample with l'existence, from 1...2 and so on. . . 

en--dash and em---dash
.
<p>Sample with l'existence, from 1...2 and so on. . .</p>
<p>en--dash and em---dash</p>
.
Document[0, 75]
  Paragraph[0, 52] isTrailingBlankLine
    Text[0, 50] chars:[0, 50, "Sampl … . . ."]
  Paragraph[53, 75]
    Text[53, 75] chars:[53, 75, "en--d … -dash"]
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos)
Sample with l'existence, from 1...2 and so on. . . 

en--dash and em---dash
.
<p md-pos="0-52">Sample with l&rsquo;existence, from 1&hellip;2 and so on&hellip;</p>
<p md-pos="53-75">en&ndash;dash and em&mdash;dash</p>
.
Document[0, 75]
  Paragraph[0, 52] isTrailingBlankLine
    Text[0, 13] chars:[0, 13, "Sampl … ith l"]
    TypographicSmarts[13, 14] typographic: &rsquo; 
    Text[14, 31] chars:[14, 31, "exist … rom 1"]
    TypographicSmarts[31, 34] typographic: &hellip; 
    Text[34, 45] chars:[34, 45, "2 and … so on"]
    TypographicSmarts[45, 50] typographic: &hellip; 
  Paragraph[53, 75]
    Text[53, 55] chars:[53, 55, "en"]
    TypographicSmarts[55, 57] typographic: &ndash; 
    Text[57, 68] chars:[57, 68, "dash  … nd em"]
    TypographicSmarts[68, 71] typographic: &mdash; 
    Text[71, 75] chars:[71, 75, "dash"]
````````````````````````````````


## Issue #70

Issue #70, parse failed for angle quotes if the end angle quote follows with a line feed or a
carriage return

```````````````````````````````` example(Issue #70: 1) options(FILE_EOL)
<<test>>
.
<p>&laquo;test&raquo;</p>
.
Document[0, 9]
  Paragraph[0, 9]
    TypographicQuotes[0, 8] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[0, 2, "<<"] text:[2, 6, "test"] textClose:[6, 8, ">>"]
      Text[2, 6] chars:[2, 6, "test"]
````````````````````````````````


```````````````````````````````` example(Issue #70: 2) options(NO_FILE_EOL)
<<test>>⏎
.
<p>&laquo;test&raquo;</p>
.
Document[0, 9]
  Paragraph[0, 9]
    TypographicQuotes[0, 8] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[0, 2, "<<"] text:[2, 6, "test"] textClose:[6, 8, ">>"]
      Text[2, 6] chars:[2, 6, "test"]
````````````````````````````````


```````````````````````````````` example(Issue #70: 3) options(FILE_EOL)
<<test>>⏎
.
<p>&laquo;test&raquo;</p>
.
Document[0, 10]
  Paragraph[0, 10]
    TypographicQuotes[0, 8] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[0, 2, "<<"] text:[2, 6, "test"] textClose:[6, 8, ">>"]
      Text[2, 6] chars:[2, 6, "test"]
````````````````````````````````


```````````````````````````````` example(Issue #70: 4) options(FILE_EOL)
<<test>>

.
<p>&laquo;test&raquo;</p>
.
Document[0, 10]
  Paragraph[0, 9] isTrailingBlankLine
    TypographicQuotes[0, 8] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[0, 2, "<<"] text:[2, 6, "test"] textClose:[6, 8, ">>"]
      Text[2, 6] chars:[2, 6, "test"]
````````````````````````````````


## Issue 72

Issue #72, Multiple angle quotes not being handled correctly

```````````````````````````````` example Issue 72: 1
<<test>><<test1>>

.
<p>&laquo;test&raquo;&laquo;test1&raquo;</p>
.
Document[0, 19]
  Paragraph[0, 18] isTrailingBlankLine
    TypographicQuotes[0, 8] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[0, 2, "<<"] text:[2, 6, "test"] textClose:[6, 8, ">>"]
      Text[2, 6] chars:[2, 6, "test"]
    TypographicQuotes[8, 17] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[8, 10, "<<"] text:[10, 15, "test1"] textClose:[15, 17, ">>"]
      Text[10, 15] chars:[10, 15, "test1"]
````````````````````````````````


```````````````````````````````` example(Issue 72: 2) options(FILE_EOL)
<<test>>
abcd
<<test>>
.
<p>&laquo;test&raquo;
abcd
&laquo;test&raquo;</p>
.
Document[0, 23]
  Paragraph[0, 23]
    TypographicQuotes[0, 8] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[0, 2, "<<"] text:[2, 6, "test"] textClose:[6, 8, ">>"]
      Text[2, 6] chars:[2, 6, "test"]
    SoftLineBreak[8, 9]
    Text[9, 13] chars:[9, 13, "abcd"]
    SoftLineBreak[13, 14]
    TypographicQuotes[14, 22] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[14, 16, "<<"] text:[16, 20, "test"] textClose:[20, 22, ">>"]
      Text[16, 20] chars:[16, 20, "test"]
````````````````````````````````


```````````````````````````````` example(Issue 72: 3) options(FILE_EOL)
<<test>>abcd

<<test>>
.
<p>&laquo;test&raquo;abcd</p>
<p>&laquo;test&raquo;</p>
.
Document[0, 23]
  Paragraph[0, 13] isTrailingBlankLine
    TypographicQuotes[0, 8] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[0, 2, "<<"] text:[2, 6, "test"] textClose:[6, 8, ">>"]
      Text[2, 6] chars:[2, 6, "test"]
    Text[8, 12] chars:[8, 12, "abcd"]
  Paragraph[14, 23]
    TypographicQuotes[14, 22] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[14, 16, "<<"] text:[16, 20, "test"] textClose:[20, 22, ">>"]
      Text[16, 20] chars:[16, 20, "test"]
````````````````````````````````


test handling of double quotes and angle quotes

```````````````````````````````` example Issue 72: 4
"<<test>>""<<test1>>"

.
<p>&ldquo;&laquo;test&raquo;&rdquo;&ldquo;&laquo;test1&raquo;&rdquo;</p>
.
Document[0, 23]
  Paragraph[0, 22] isTrailingBlankLine
    TypographicQuotes[0, 10] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[0, 1, "\""] text:[1, 9, "<<test>>"] textClose:[9, 10, "\""]
      TypographicQuotes[1, 9] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[1, 3, "<<"] text:[3, 7, "test"] textClose:[7, 9, ">>"]
        Text[3, 7] chars:[3, 7, "test"]
    TypographicQuotes[10, 21] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[10, 11, "\""] text:[11, 20, "<<test1>>"] textClose:[20, 21, "\""]
      TypographicQuotes[11, 20] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[11, 13, "<<"] text:[13, 18, "test1"] textClose:[18, 20, ">>"]
        Text[13, 18] chars:[13, 18, "test1"]
````````````````````````````````


## Issue 74

Issue #74, Angle quotes again :)

```````````````````````````````` example(Issue 74: 1) options(NO_FILE_EOL)
<<test>>abcd<</test1>>
.
<p>&laquo;test&raquo;abcd&laquo;/test1&raquo;</p>
.
Document[0, 22]
  Paragraph[0, 22]
    TypographicQuotes[0, 8] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[0, 2, "<<"] text:[2, 6, "test"] textClose:[6, 8, ">>"]
      Text[2, 6] chars:[2, 6, "test"]
    Text[8, 12] chars:[8, 12, "abcd"]
    TypographicQuotes[12, 22] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[12, 14, "<<"] text:[14, 20, "/test1"] textClose:[20, 22, ">>"]
      Text[14, 20] chars:[14, 20, "/test1"]
````````````````````````````````


```````````````````````````````` example(Issue 74: 2) options(NO_FILE_EOL)
<<test>>abcd<<:test1>>
.
<p>&laquo;test&raquo;abcd&laquo;:test1&raquo;</p>
.
Document[0, 22]
  Paragraph[0, 22]
    TypographicQuotes[0, 8] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[0, 2, "<<"] text:[2, 6, "test"] textClose:[6, 8, ">>"]
      Text[2, 6] chars:[2, 6, "test"]
    Text[8, 12] chars:[8, 12, "abcd"]
    TypographicQuotes[12, 22] typographicOpening: &laquo;  typographicClosing: &raquo;  textOpen:[12, 14, "<<"] text:[14, 20, ":test1"] textClose:[20, 22, ">>"]
      Text[14, 20] chars:[14, 20, ":test1"]
````````````````````````````````


