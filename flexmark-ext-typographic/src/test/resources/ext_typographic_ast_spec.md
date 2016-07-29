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

- single quoted `'some text'` to `&lsquo;some text&rsquo;` &lsquo;some text&rsquo;
- double quoted `"some text"` to `&ldquo;some text&rdquo;` &ldquo;some text&rdquo;
- double angle quoted `<<some text>>` to `&laquo;some text&raquo;` &laquo;some text&raquo;

basic quotes

```````````````````````````````` example(TypographicQuotes: 1) options(IGNORE)
Sample "double" 'single' <<angle>> "l'ordre" 'l'ordre'
.
<p>Sample &quot;double&quot; &lsquo;single&rsquo; &lt;<angle>&gt; &quot;l&lsquo;ordre&quot; &lsquo;l&rsquo;ordre&rsquo;</p>
.
Document[0, 55]
  Paragraph[0, 55]
    Text[0, 16] chars:[0, 16, "Sampl"..."ble" "]
    TypographicQuotes[16, 24] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[16, 17, "'"] text:[17, 23, "single"] textClose:[23, 24, "'"]
      Text[17, 23] chars:[17, 23, "single"]
    Text[24, 26] chars:[24, 26, " <"]
    HtmlInline[26, 33] chars:[26, 33, "<angle>"]
    Text[33, 37] chars:[33, 37, "> "l"]
    TypographicQuotes[37, 54] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[37, 38, "'"] text:[38, 53, "ordre" 'l'ordre"] textClose:[53, 54, "'"]
      Text[38, 45] chars:[38, 45, "ordre" "]
      TypographicQuotes[45, 48] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[45, 46, "'"] text:[46, 47, "l"] textClose:[47, 48, "'"]
        Text[46, 47] chars:[46, 47, "l"]
      Text[48, 53] chars:[48, 53, "ordre"]
````````````````````````````````


escaped quotes

```````````````````````````````` example(TypographicQuotes: 2) options(IGNORE)
Sample \"double\" \'single\' \<<angle\>> \"l'ordre" \'l'ordre\'
.
<p>Sample &quot;double&quot; &lsquo;single&rsquo; &lt;<angle>&gt; &quot;l&lsquo;ordre&quot; &lsquo;l&rsquo;ordre&rsquo;</p>
.
Document[0, 55]
  Paragraph[0, 55]
    Text[0, 16] chars:[0, 16, "Sampl"..."ble" "]
    TypographicQuotes[16, 24] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[16, 17, "'"] text:[17, 23, "single"] textClose:[23, 24, "'"]
      Text[17, 23] chars:[17, 23, "single"]
    Text[24, 26] chars:[24, 26, " <"]
    HtmlInline[26, 33] chars:[26, 33, "<angle>"]
    Text[33, 37] chars:[33, 37, "> "l"]
    TypographicQuotes[37, 54] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[37, 38, "'"] text:[38, 53, "ordre" 'l'ordre"] textClose:[53, 54, "'"]
      Text[38, 45] chars:[38, 45, "ordre" "]
      TypographicQuotes[45, 48] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[45, 46, "'"] text:[46, 47, "l"] textClose:[47, 48, "'"]
        Text[46, 47] chars:[46, 47, "l"]
      Text[48, 53] chars:[48, 53, "ordre"]
````````````````````````````````


basic quotes turned off

```````````````````````````````` example(TypographicQuotes: 3) options(no-quotes, IGNORE)
Sample "double" 'single' <<angle>> "l'ordre" 'l'ordre'
.
<p>Sample &quot;double&quot; &lsquo;single&rsquo; &lt;<angle>&gt; &quot;l&lsquo;ordre&quot; &lsquo;l&rsquo;ordre&rsquo;</p>
.
Document[0, 55]
  Paragraph[0, 55]
    Text[0, 16] chars:[0, 16, "Sampl"..."ble" "]
    TypographicQuotes[16, 24] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[16, 17, "'"] text:[17, 23, "single"] textClose:[23, 24, "'"]
      Text[17, 23] chars:[17, 23, "single"]
    Text[24, 26] chars:[24, 26, " <"]
    HtmlInline[26, 33] chars:[26, 33, "<angle>"]
    Text[33, 37] chars:[33, 37, "> "l"]
    TypographicQuotes[37, 54] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[37, 38, "'"] text:[38, 53, "ordre" 'l'ordre"] textClose:[53, 54, "'"]
      Text[38, 45] chars:[38, 45, "ordre" "]
      TypographicQuotes[45, 48] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[45, 46, "'"] text:[46, 47, "l"] textClose:[47, 48, "'"]
        Text[46, 47] chars:[46, 47, "l"]
      Text[48, 53] chars:[48, 53, "ordre"]
````````````````````````````````


## Typographic Smarts  

Converts:

- `'` to apostrophe `&rsquo;` &rsquo;
- `...` and `. . .` to ellipsis `&hellip;` &hellip;
- `--` en dash `&ndash;` &ndash;
- `---` em dash `&mdash;` &mdash;

to TypographicSmarts nodes

basic

```````````````````````````````` example(Typographic Smarts: 1) options(IGNORE)
Sample with l'existence, from 1...2 and so on. . . 

en--dash and em---dash
.
<p>Expected rendered HTML</p>
.
````````````````````````````````


escaped smarts

```````````````````````````````` example(Typographic Smarts: 2) options(IGNORE)
Sample with l\'existence, from 1\...2 and so on\. . . 

en\--dash and em\---dash
.
<p>Expected rendered HTML</p>
.
````````````````````````````````


basic smarts turned off

```````````````````````````````` example(Typographic Smarts: 3) options(no-smarts, IGNORE)
Sample with l'existence, from 1...2 and so on. . . 

en--dash and em---dash
.
<p>Expected rendered HTML</p>
.
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos, IGNORE)
Sample with l'existence, from 1...2 and so on. . . 

en--dash and em---dash
.
<p>Expected rendered HTML</p>
.
````````````````````````````````


