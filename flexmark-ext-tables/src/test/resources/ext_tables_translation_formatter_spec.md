---
title: Tables Extension Formatter Spec
author: Vladimir Schneider
version: 0.1
date: '2017-01-27'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Tables Extension

Converts pipe separated tables to html tables with optional column spans and multiple header
lines and table caption.

```````````````````````````````` example Tables Extension: 1
-------|-------------
.
|-------|-------------|
````````````````````````````````


```````````````````````````````` example Tables Extension: 2
Abc|Def
.
aBc|deEF
````````````````````````````````


```````````````````````````````` example Tables Extension: 3
Abc | Def
.
aBc | deEF
````````````````````````````````


```````````````````````````````` example Tables Extension: 4
Abc|Def
-|-
.
aBc|deEF
-|-
````````````````````````````````


```````````````````````````````` example Tables Extension: 5
Abc|Def
--|--
.
aBc|deEF
--|--
````````````````````````````````


paragraph lines that look like table separator should be left indented

```````````````````````````````` example Tables Extension: 6
Abc|Def
 |---|---
.
| aBc | deEF |
|---|---|
````````````````````````````````


```````````````````````````````` example Tables Extension: 7
No
Abc|Def
---|---
.
no
aBc|deEF
---|---
````````````````````````````````


```````````````````````````````` example Tables Extension: 8
Abc|Def
---|---
.
| aBc | deEF |
|---|---|
````````````````````````````````


Separator columns need to be at least 3 characters, not 3 dashes

```````````````````````````````` example Tables Extension: 9
Abc|Def
:--|---
.
| aBc | deEF |
|:--|---|
````````````````````````````````


Separator columns need to be at least 3 characters, not 3 dashes

```````````````````````````````` example Tables Extension: 10
Abc|Def
--:|---
.
| aBc | deEF |
|--:|---|
````````````````````````````````


```````````````````````````````` example Tables Extension: 11
Abc|Def
:-:|---
.
| aBc | deEF |
|:-:|---|
````````````````````````````````


```````````````````````````````` example Tables Extension: 12
|Abc
|---
.
| aBc |
|---|
````````````````````````````````


```````````````````````````````` example Tables Extension: 13
|Abc|
|---|
.
| aBc |
|---|
````````````````````````````````


```````````````````````````````` example Tables Extension: 14
Abc|
---|
.
| aBc |
|---|
````````````````````````````````


```````````````````````````````` example Tables Extension: 15
|Abc
---
.
|aBc
----
````````````````````````````````


```````````````````````````````` example Tables Extension: 16
Abc
|---
.
aBc
|---
````````````````````````````````


```````````````````````````````` example Tables Extension: 17
|Abc
|---
|1
.
| aBc |
|---|
| 1 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 18
|Abc|
|---|
|1|
.
| aBc |
|---|
| 1 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 19
Abc|
---|
1|
.
| aBc |
|---|
| 1 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 20
|Abc
---
|1
.
|aBc
----

|1
````````````````````````````````


```````````````````````````````` example Tables Extension: 21
|Abc
|---
1
.
| aBc |
|---|

1
````````````````````````````````


```````````````````````````````` example Tables Extension: 22
Abc|Def
---|---
1|2
.
| aBc | deEF |
|---|---|
| 1 | 2 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 23
Abc|Def|Ghi
---|---
1|2|3
.
| aBc | deEF | ghiI |
|---|---|
| 1 | 2 | 3 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 24
 Abc  | Def
 --- | ---
 1 | 2
.
| aBc | deEF |
|---|---|
| 1 | 2 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 25
Abc|Def
---|---
    1|2
.
| aBc | deEF |
|---|---|
| 1 | 2 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 26
|Abc|Def|
|---|---|
|1|2|
.
| aBc | deEF |
|---|---|
| 1 | 2 |
````````````````````````````````


Embedded pipes in inline elements

```````````````````````````````` example Tables Extension: 27
Abc|Def
---|---
`|`|`|`
.
| aBc | deEF |
|---|---|
| `|` | `|` |
````````````````````````````````


unclosed delimiters in cells

```````````````````````````````` example Tables Extension: 28
Abc|Def
---|---
`| | abc
.
| aBc | deEF |
|---|---|
| ` |  | aABc |
````````````````````````````````


unclosed delimiters in cells

```````````````````````````````` example Tables Extension: 29
Abc|Def
---|---
**def | abc
.
| aBc | deEF |
|---|---|
| **DeEF | aABc |
````````````````````````````````


```````````````````````````````` example Tables Extension: 30
*Abc*|Def
---|---
1|2
.
| *aBc* | deEF |
|---|---|
| 1 | 2 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 31
Abc|Def
---|---
1\\|2|20
.
| aBc | deEF |
|---|---|
| 1\\ | 2 | 20 |
````````````````````````````````


Extra column should be truncated when GFM compatibility is selected

```````````````````````````````` example(Tables Extension: 32) options(gfm)
Abc|Def
---|---
1\\|2|20
.
| aBc | deEF |
|---|---|
| 1\\ | 2 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 33
Abc|Def
---|---
1\\\\|2
.
| aBc | deEF |
|---|---|
| 1\\\\ | 2 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 34
Abc|Def
:---|---
1|2
.
| aBc | deEF |
|:---|---|
| 1 | 2 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 35
Abc|Def
---:|---
1|2
.
| aBc | deEF |
|---:|---|
| 1 | 2 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 36
Abc|Def
:---:|---
1|2
.
| aBc | deEF |
|:---:|---|
| 1 | 2 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 37
Abc|Def
---|:---:
1|2
.
| aBc | deEF |
|---|:---:|
| 1 | 2 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 38
Abc|Def
 :--- |---
1|2
.
| aBc | deEF |
|:---|---|
| 1 | 2 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 39
Abc|Def
--- :|---
.
aBc|deEF
--- :|---
````````````````````````````````


```````````````````````````````` example Tables Extension: 40
Abc|Def
---|: ---
.
aBc|deEF
---|: ---
````````````````````````````````


```````````````````````````````` example Tables Extension: 41
Abc|Def
---|--- :
.
aBc|deEF
---|--- :
````````````````````````````````


```````````````````````````````` example Tables Extension: 42
Abc|Def
---|---
1|2|3
.
| aBc | deEF |
|---|---|
| 1 | 2 | 3 |
````````````````````````````````


Extra columns truncated with GFM compatibility on.

```````````````````````````````` example(Tables Extension: 43) options(gfm)
Abc|Def
---|---
1|2|3
.
| aBc | deEF |
|---|---|
| 1 | 2 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 44
Abc|Def|Ghi
---|---|---
1|2
.
| aBc | deEF | ghiI |
|---|---|---|
| 1 | 2 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 45
> Abc|Def
> ---|---
> 1|2
.
> | aBc | deEF |
> |---|---|
> | 1 | 2 |
````````````````````````````````


```````````````````````````````` example Tables Extension: 46
Abc|Def
---|---
1|2
table, you are over
.
| aBc | deEF |
|---|---|
| 1 | 2 |

taABLeE, yYouU aAReE oVeER
````````````````````````````````


inlines should be processed

```````````````````````````````` example Tables Extension: 47
**Abc**|_Def_
---|---
[ref]|`code`
table, you are over

[ref]: /url
.
| **aBc** | _deEF_ |
|---|---|
| [ReEF] | `code` |

taABLeE, yYouU aAReE oVeER

[ReEF]: /url
````````````````````````````````


inlines should be processed

```````````````````````````````` example Tables Extension: 48
|**Abc** **test** |_Def_ _Def_
---|---
[ref]|`code` `code`
table, you are over

[ref]: /url
.
| **aBc** **teESt** | _deEF_ _deEF_ |
|---|---|
| [ReEF] | `code` `code` |

taABLeE, yYouU aAReE oVeER

[ReEF]: /url
````````````````````````````````


Column spans are created with repeated | pipes one for each additional column to span

```````````````````````````````` example Tables Extension: 49
|Abc|Def
|---|---|
| span ||
.
| aBc | deEF |
|---|---|
| SpaAN ||
````````````````````````````````


Now we try varying the header lines and make sure we get the right output

```````````````````````````````` example Tables Extension: 50
|Abc|Def
|Hij|Lmn
|---|---|
| span ||
.
| aBc | deEF |
| hiIJ | lmN |
|---|---|
| SpaAN ||
````````````````````````````````


No header lines

```````````````````````````````` example Tables Extension: 51
|---|---|
| col1 | col2|
.
|---|---|
| coL1 | coL2 |
````````````````````````````````


No body lines

```````````````````````````````` example Tables Extension: 52
| col1 | col2|
|---|---|
.
| coL1 | coL2 |
|---|---|
````````````````````````````````


With caption

```````````````````````````````` example Tables Extension: 53
| col1 | col2|
|---|---|
         [Caption **bold** _italic_ `code`]          
.
| coL1 | coL2 |
|---|---|
[Caption **bold** _italic_ `code`]
````````````````````````````````


With caption

```````````````````````````````` example Tables Extension: 54
| col1 | col2|
|---|---|
[Caption]
.
| coL1 | coL2 |
|---|---|
[Caption]
````````````````````````````````


With caption but no caption output

```````````````````````````````` example(Tables Extension: 55) options(no-caption)
| col1 | col2|
|---|---|
[Caption]
.
| coL1 | coL2 |
|---|---|
````````````````````````````````


Alignment should be taken from column after span is added

```````````````````````````````` example Tables Extension: 56
| day         | time  |   spent |
|:------------|:-----:|--------:|
| nov. 2. tue | 10:00 |  4h 40m |
| nov. 3. thu | 11:00 |      4h |
| nov. 7. mon | 10:20 |  4h 20m |
| total:             || **13h** |
.
| DaAyY | tiImeE | SpeENt |
|:------------|:-----:|--------:|
| NoV. 2. tuUeE | 10:00 | 4h 40m |
| NoV. 3. thuU | 11:00 | 4h |
| NoV. 7. moN | 10:20 | 4h 20m |
| totaAL: || **13h** |
````````````````````````````````


multiple tables parsed correctly

```````````````````````````````` example Tables Extension: 57
not a table, followed by a table

| col1 | col2|
|---|---|

| col1 | col2|
|---|---|
| data1 | data2|

not a table, followed by a table

| col11 | col12|
| col21 | col22|
|---|---|
| data1 | data2|

.
Not aA taABLeE, FoLLoWeED ByY aA taABLeE

| coL1 | coL2 |
|---|---|

| coL1 | coL2 |
|---|---|
| DaAtaA1 | DaAtaA2 |

Not aA taABLeE, FoLLoWeED ByY aA taABLeE

| coL11 | coL12 |
| coL21 | coL22 |
|---|---|
| DaAtaA1 | DaAtaA2 |
````````````````````````````````


multi row/column

```````````````````````````````` example Tables Extension: 58
| col11 | col12| col13|
| col21 | col22| col23|
| col31 | col32| col33|
|---|---|---|
| data11 | data12| data13|
| data21 | data22| data23|
| data31 | data32| data33|

.
| coL11 | coL12 | coL13 |
| coL21 | coL22 | coL23 |
| coL31 | coL32 | coL33 |
|---|---|---|
| DaAtaA11 | DaAtaA12 | DaAtaA13 |
| DaAtaA21 | DaAtaA22 | DaAtaA23 |
| DaAtaA31 | DaAtaA32 | DaAtaA33 |
````````````````````````````````


keep cell whitespace

```````````````````````````````` example Tables Extension: 59
 Abc  | Def
 --- | ---
 1 | 2
.
| aBc | deEF |
|---|---|
| 1 | 2 |
````````````````````````````````


Custom class name

```````````````````````````````` example Tables Extension: 60
Abc|Def
---|---
.
| aBc | deEF |
|---|---|
````````````````````````````````


in item

```````````````````````````````` example Tables Extension: 61
- Add: live templates starting with `.`    
                                        
  | Element       | Abbreviation    | Expansion                                               |
  |---------------|-----------------|---------------------------------------------------------|
  | Abbreviation  | `.abbreviation` | `*[]: `                                                 |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\`                                       |
  | Explicit link | `.link`         | `[]()`                                                  |
.
- aDD: LiIVeE teEmpLaAteES StaARtiING WiIth `.`

  | eLeEmeENt | aBBReEViIaAtiIoN | eXpaANSiIoN |
  |---------------|-----------------|---------------------------------------------------------|
  | aBBReEViIaAtiIoN | `.abbreviation` | `*[]: ` |
  | coDeE FeENceE | `.codefence` | \`\`\` ... \`\`\` |
  | eXpLiIciIt LiINK | `.link` | `[]()` |
````````````````````````````````


real life table

```````````````````````````````` example Tables Extension: 62
| Feature                                                                                                                 | Basic | Enhanced |
|:------------------------------------------------------------------------------------------------------------------------|:-----:|:--------:|
| Works with builds 143.2370 or newer, product version IDEA 15.0.6                                                        |   X   |    X     |
| Preview Tab so you can see what the rendered markdown will look like on GitHub.                                         |   X   |    X     |
| Syntax highlighting                                                                                                     |   X   |    X     |
| Table syntax highlighting stripes rows and columns                                                                      |   X   |    X     |
| Support for Default and Darcula color schemes for preview tab                                                           |   X   |    X     |
| Warning and Error Annotations to help you validate wiki link errors                                                     |   X   |    X     |
| Link address completion for wiki links                                                                                  |   X   |    X     |
| Quick Fixes for detected wiki link errors                                                                               |   X   |    X     |
| GFM Task list extension `* [ ]` open task item and `* [x]` completed task item                                          |   X   |    X     |
| Line markers, Find usages, Go To Declaration for rapid navigation to wiki link targets                                  |   X   |    X     |
| Markdown extensions configuration to customize markdown dialects                                                        |   X   |    X     |
| GitHub wiki support makes maintaining GitHub wiki pages easier.                                                         |   X   |    X     |
| GitHub compatible id generation for headers so you can validate your anchor references                                  |   X   |    X     |
| Swing and JavaFX WebView based preview.                                                                                 |   X   |    X     |
| Supports **JavaFX with JetBrains JRE on OS X**                                                                          |   X   |    X     |
| Supports Highlight JS in WebView preview                                                                                |   X   |    X     |
| **Multi-line Image URLs for embedding [gravizo.com] UML diagrams into markdown**                                        |   X   |    X     |
| Live Templates for common markdown elements                                                                             |   X   |    X     |
| **Enhanced Version Benefits**                                                                                           |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Split Editor with Preview or HTML Text modes to view both source and preview                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Toolbar for fast access to frequent operations                                                  |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Language Injections for fenced code, HTML, Jekyll front matter and multi-line URL content       |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Code completions, refactoring, annotations and quick fixes to let you work faster               |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Navigation support with Line markers, Find usages, Go To Declaration for rapid navigation       |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Inspections to help you validate links, anchor refs, footnote refs                              |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Complete GitHub wiki support for all links makes maintaining GitHub wiki pages a breeze         |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Jekyll front matter recognition in markdown documents                                           |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Emoji text to icon conversion using [Emoji Cheat Sheet] or GitHub emoji URLs                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Wrap on typing and table formatting with column alignment                                       |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Character display width used for wrapping and table formatting                                  |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Structure view for Abbreviations, Headers, Tables, Footnotes, References and Document           |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Document formatting with text wrapping, list renumbering, arranging of elements, etc.           |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Table of Contents generation for any markdown parser, with many style options                   |       |    X     |
| **As you type automation**                                                                                              |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Double of bold/emphasis markers and remove inserted ones if a space is typed                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Wrap text blocks to margins and indentation                                                     |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;ATX headers to match trailing `#` marker                                                        |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Setext headers to match marker length to text                                                   |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Format tables to pad column width, column alignment and spanning columns                        |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto insert empty table row on <kbd>ENTER</kbd>                                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty table row/column on <kbd>BACKSPACE</kbd>                                      |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto insert table column when typing before first column or after last column of table          |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Actions to insert: table, row or column; delete: row or column                                  |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto insert list item on <kbd>ENTER</kbd>                                                       |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty list item on <kbd>ENTER</kbd>                                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Auto delete empty list item on <kbd>BACKSPACE</kbd>                                             |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Indent or un-indent list item toolbar buttons and actions                                       |       |    X     |
| **Code Completions**                                                                                                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Absolute link address completions using https:// and file:// formats                            |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Explicit and Image links are GitHub wiki aware                                                  |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;GitHub Issue # Completions after `issues/` link address and in text                             |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;GitHub special links: Issues, Pull requests, Graphs, and Pulse.                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Link address completions for non-markdown files                                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Emoji text shortcuts completion                                                                 |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Java class, field and method completions in inline code elements                                |       |    X     |
| **Intention Actions**                                                                                                   |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Change between relative and absolute https:// link addresses via intention action               |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Change between wiki links and explicit link                                                     |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Intentions for links, wiki links, references and headers                                        |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Intention to format Setext Header marker to match marker length to text                         |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Intention to swap Setext/Atx header format                                                      |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Update table of contents quick fix intention                                                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Intention to edit Table of Contents style options dialog with preview                           |       |    X     |
| **Refactoring**                                                                                                         |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Automatic change from wiki link to explicit link when link target file is moved out of the wiki |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;File move refactoring of contained links. This completes the refactoring feature set            |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Refactoring for /, https:// and file:// absolute link addresses to project files                |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Refactoring of header text with update to referencing anchor link references                    |       |    X     |
| &nbsp;&nbsp;&nbsp;&nbsp;Anchor link reference refactoring with update to referenced header text                         |       |    X     |
.
| feEaAtuUReE | baASiIc | eNhaANceED |
|:------------------------------------------------------------------------------------------------------------------------|:-----:|:--------:|
| woRKS WiIth BuUiILDS 143.2370 oR NeEWeER, pRoDuUct VeERSiIoN idea 15.0.6 | x | x |
| pReEViIeEW taAB So yYouU caAN SeEeE WhaAt theE ReENDeEReED maARKDoWN WiILL LooK LiIKeE oN giIthuUB. | x | x |
| syYNtaAX hiIGhLiIGhtiING | x | x |
| taABLeE SyYNtaAX hiIGhLiIGhtiING StRiIpeES RoWS aAND coLuUmNS | x | x |
| suUppoRt FoR deEFaAuULt aAND daARcuULaA coLoR ScheEmeES FoR pReEViIeEW taAB | x | x |
| waARNiING aAND eRRoR aNNotaAtiIoNS to heELp yYouU VaALiIDaAteE WiIKiI LiINK eERRoRS | x | x |
| liINK aADDReESS compLeEtiIoN FoR WiIKiI LiINKS | x | x |
| quUiIcK fiIXeES FoR DeEteEcteED WiIKiI LiINK eERRoRS | x | x |
| gfm taASK LiISt eEXteENSiIoN `* [ ]` opeEN taASK iIteEm aAND `* [x]` compLeEteED taASK iIteEm | x | x |
| liINeE maARKeERS, fiIND uUSaAGeES, go to deEcLaARaAtiIoN FoR RaApiID NaAViIGaAtiIoN to WiIKiI LiINK taARGeEtS | x | x |
| maARKDoWN eEXteENSiIoNS coNFiIGuURaAtiIoN to cuUStomiIZeE maARKDoWN DiIaALeEctS | x | x |
| giIthuUB WiIKiI SuUppoRt maAKeES maAiINtaAiINiING giIthuUB WiIKiI paAGeES eEaASiIeER. | x | x |
| giIthuUB compaAtiIBLeE iID GeENeERaAtiIoN FoR heEaADeERS So yYouU caAN VaALiIDaAteE yYouUR aANchoR ReEFeEReENceES | x | x |
| sWiING aAND jaAVaAfx weEBviIeEW BaASeED pReEViIeEW. | x | x |
| suUppoRtS **jaAVaAfx WiIth jeEtbRaAiINS jre oN os x** | x | x |
| suUppoRtS hiIGhLiIGht js iIN weEBviIeEW pReEViIeEW | x | x |
| **muULtiI-LiINeE imaAGeE urlS FoR eEmBeEDDiING [GRaAViIZo.com] uml DiIaAGRaAmS iINto maARKDoWN** | x | x |
| liIVeE teEmpLaAteES FoR commoN maARKDoWN eELeEmeENtS | x | x |
| **eNhaANceED veERSiIoN beENeEFiItS** |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;spLiIt eDiItoR WiIth pReEViIeEW oR html teEXt moDeES to ViIeEW Both SouURceE aAND pReEViIeEW |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;tooLBaAR FoR FaASt aAcceESS to FReEQuUeENt opeERaAtiIoNS |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;laANGuUaAGeE iNJeEctiIoNS FoR FeENceED coDeE, html, jeEKyYLL FRoNt maAtteER aAND muULtiI-LiINeE url coNteENt |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;coDeE compLeEtiIoNS, ReEFaActoRiING, aANNotaAtiIoNS aAND QuUiIcK FiIXeES to LeEt yYouU WoRK FaASteER |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;naAViIGaAtiIoN SuUppoRt WiIth liINeE maARKeERS, fiIND uUSaAGeES, go to deEcLaARaAtiIoN FoR RaApiID NaAViIGaAtiIoN |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;iNSpeEctiIoNS to heELp yYouU VaALiIDaAteE LiINKS, aANchoR ReEFS, FootNoteE ReEFS |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;compLeEteE giIthuUB WiIKiI SuUppoRt FoR aALL LiINKS maAKeES maAiINtaAiINiING giIthuUB WiIKiI paAGeES aA BReEeEZeE |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;jeEKyYLL FRoNt maAtteER ReEcoGNiItiIoN iIN maARKDoWN DocuUmeENtS |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;emoJiI teEXt to iIcoN coNVeERSiIoN uUSiING [emoJiI cheEaAt sheEeEt] oR giIthuUB eEmoJiI urlS |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;wRaAp oN tyYpiING aAND taABLeE FoRmaAttiING WiIth coLuUmN aALiIGNmeENt |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;chaARaActeER DiISpLaAyY WiIDth uUSeED FoR WRaAppiING aAND taABLeE FoRmaAttiING |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;stRuUctuUReE ViIeEW FoR aBBReEViIaAtiIoNS, heEaADeERS, taABLeES, footNoteES, reEFeEReENceES aAND docuUmeENt |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;docuUmeENt FoRmaAttiING WiIth teEXt WRaAppiING, LiISt ReENuUmBeERiING, aARRaANGiING oF eELeEmeENtS, eEtc. |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;taABLeE oF coNteENtS GeENeERaAtiIoN FoR aANyY maARKDoWN paARSeER, WiIth maANyY StyYLeE optiIoNS |  | x |
| **aS yYouU tyYpeE aAuUtomaAtiIoN** |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;douUBLeE oF BoLD/eEmphaASiIS maARKeERS aAND ReEmoVeE iINSeERteED oNeES iIF aA SpaAceE iIS tyYpeED |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;wRaAp teEXt BLocKS to maARGiINS aAND iINDeENtaAtiIoN |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;atx heEaADeERS to maAtch tRaAiILiING `#` maARKeER |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;seEteEXt heEaADeERS to maAtch maARKeER LeENGth to teEXt |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;foRmaAt taABLeES to paAD coLuUmN WiIDth, coLuUmN aALiIGNmeENt aAND SpaANNiING coLuUmNS |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;auUto iINSeERt eEmptyY taABLeE RoW oN <kbd>enter</kbd> |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;auUto DeELeEteE eEmptyY taABLeE RoW/coLuUmN oN <kbd>backspace</kbd> |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;auUto iINSeERt taABLeE coLuUmN WheEN tyYpiING BeEFoReE FiIRSt coLuUmN oR aAFteER LaASt coLuUmN oF taABLeE |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;actiIoNS to iINSeERt: taABLeE, RoW oR coLuUmN; DeELeEteE: RoW oR coLuUmN |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;auUto iINSeERt LiISt iIteEm oN <kbd>enter</kbd> |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;auUto DeELeEteE eEmptyY LiISt iIteEm oN <kbd>enter</kbd> |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;auUto DeELeEteE eEmptyY LiISt iIteEm oN <kbd>backspace</kbd> |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;iNDeENt oR uUN-iINDeENt LiISt iIteEm tooLBaAR BuUttoNS aAND aActiIoNS |  | x |
| **coDeE compLeEtiIoNS** |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;aBSoLuUteE LiINK aADDReESS compLeEtiIoNS uUSiING httpS:// aAND FiILeE:// FoRmaAtS |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;eXpLiIciIt aAND imaAGeE LiINKS aAReE giIthuUB WiIKiI aAWaAReE |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;giIthuUB iSSuUeE # compLeEtiIoNS aAFteER `issues/` LiINK aADDReESS aAND iIN teEXt |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;giIthuUB SpeEciIaAL LiINKS: iSSuUeES, puULL ReEQuUeEStS, gRaAphS, aAND puULSeE. |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;liINK aADDReESS compLeEtiIoNS FoR NoN-maARKDoWN FiILeES |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;emoJiI teEXt ShoRtcuUtS compLeEtiIoN |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;jaAVaA cLaASS, FiIeELD aAND meEthoD compLeEtiIoNS iIN iINLiINeE coDeE eELeEmeENtS |  | x |
| **iNteENtiIoN actiIoNS** |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;chaANGeE BeEtWeEeEN ReELaAtiIVeE aAND aABSoLuUteE httpS:// LiINK aADDReESSeES ViIaA iINteENtiIoN aActiIoN |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;chaANGeE BeEtWeEeEN WiIKiI LiINKS aAND eEXpLiIciIt LiINK |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;iNteENtiIoNS FoR LiINKS, WiIKiI LiINKS, ReEFeEReENceES aAND heEaADeERS |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;iNteENtiIoN to FoRmaAt seEteEXt heEaADeER maARKeER to maAtch maARKeER LeENGth to teEXt |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;iNteENtiIoN to SWaAp seEteEXt/atX heEaADeER FoRmaAt |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;upDaAteE taABLeE oF coNteENtS QuUiIcK FiIX iINteENtiIoN |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;iNteENtiIoN to eEDiIt taABLeE oF coNteENtS StyYLeE optiIoNS DiIaALoG WiIth pReEViIeEW |  | x |
| **reEFaActoRiING** |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;auUtomaAtiIc chaANGeE FRom WiIKiI LiINK to eEXpLiIciIt LiINK WheEN LiINK taARGeEt FiILeE iIS moVeED ouUt oF theE WiIKiI |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;fiILeE moVeE ReEFaActoRiING oF coNtaAiINeED LiINKS. thiIS compLeEteES theE ReEFaActoRiING FeEaAtuUReE SeEt |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;reEFaActoRiING FoR /, httpS:// aAND FiILeE:// aABSoLuUteE LiINK aADDReESSeES to pRoJeEct FiILeES |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;reEFaActoRiING oF heEaADeER teEXt WiIth uUpDaAteE to ReEFeEReENciING aANchoR LiINK ReEFeEReENceES |  | x |
| &nbsp;&nbsp;&nbsp;&nbsp;aNchoR LiINK ReEFeEReENceE ReEFaActoRiING WiIth uUpDaAteE to ReEFeEReENceED heEaADeER teEXt |  | x |
````````````````````````````````


## GFM options

invalid table:

```````````````````````````````` example(GFM options: 1) options(gfm)
| A | B | C |
|-----------|
| a | b | c |
| b | a | c |
.
| a | b | c |
|-----------|
| aA | B | c |
| B | aA | c |
````````````````````````````````


invalid table:

```````````````````````````````` example(GFM options: 2) options(gfm)
| A | B | C |
| a | b | c |
| b | a | c |
.
| a | b | c |
| aA | B | c |
| B | aA | c |
````````````````````````````````


```````````````````````````````` example GFM options: 3
| Feature                                                                                                                 | Basic | Enhanced |
|:------------------------------------------------------------------------------------------------------------------------|:-----:|:--------:|
| Works with builds 143.2370 or newer, product version IDEA 15.0.6                                                        |   X   |    X     |
| Preview Tab so you can see what the rendered markdown will look like on GitHub.                                         |   X   |    X     |
| Syntax highlighting                                                                                                     |   X   |    X     |
| Table syntax highlighting stripes rows and columns                                                                      |   X   |    X     |
| Support for Default and Darcula color schemes for preview tab                                                           |   X   |    X     |
| Warning and Error Annotations to help you validate wiki link errors                                                     |   X   |    X     |
.
| feEaAtuUReE | baASiIc | eNhaANceED |
|:------------------------------------------------------------------------------------------------------------------------|:-----:|:--------:|
| woRKS WiIth BuUiILDS 143.2370 oR NeEWeER, pRoDuUct VeERSiIoN idea 15.0.6 | x | x |
| pReEViIeEW taAB So yYouU caAN SeEeE WhaAt theE ReENDeEReED maARKDoWN WiILL LooK LiIKeE oN giIthuUB. | x | x |
| syYNtaAX hiIGhLiIGhtiING | x | x |
| taABLeE SyYNtaAX hiIGhLiIGhtiING StRiIpeES RoWS aAND coLuUmNS | x | x |
| suUppoRt FoR deEFaAuULt aAND daARcuULaA coLoR ScheEmeES FoR pReEViIeEW taAB | x | x |
| waARNiING aAND eRRoR aNNotaAtiIoNS to heELp yYouU VaALiIDaAteE WiIKiI LiINK eERRoRS | x | x |
````````````````````````````````


in item

```````````````````````````````` example(GFM options: 4) options(gfm)
- Add: live templates starting with `.`    
                                        
  | Element       | Abbreviation    | Expansion                                               |
  |---------------|-----------------|---------------------------------------------------------|
  | Abbreviation  | `.abbreviation` | `*[]: `                                                 |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\`                                       |
  | Explicit link | `.link`         | `[]()`                                                  |
.
- aDD: LiIVeE teEmpLaAteES StaARtiING WiIth `.`

  | eLeEmeENt | aBBReEViIaAtiIoN | eXpaANSiIoN |
  |---------------|-----------------|---------------------------------------------------------|
  | aBBReEViIaAtiIoN | `.abbreviation` | `*[]: ` |
  | coDeE FeENceE | `.codefence` | \`\`\` ... \`\`\` |
  | eXpLiIciIt LiINK | `.link` | `[]()` |
````````````````````````````````


default

```````````````````````````````` example GFM options: 5
- Add: live templates starting with `.`    
                                        
  | Element       | Abbreviation    | Expansion                     |
  |---------------|-----------------|------------------------------:|
  | Abbreviation  | `.abbreviation` |           `*[]: `             |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\`             |
  | Explicit link | `.link`         |            `[]()`             |
.
- aDD: LiIVeE teEmpLaAteES StaARtiING WiIth `.`

  | eLeEmeENt | aBBReEViIaAtiIoN | eXpaANSiIoN |
  |---------------|-----------------|------------------------------:|
  | aBBReEViIaAtiIoN | `.abbreviation` | `*[]: ` |
  | coDeE FeENceE | `.codefence` | \`\`\` ... \`\`\` |
  | eXpLiIciIt LiINK | `.link` | `[]()` |
````````````````````````````````


```````````````````````````````` example(GFM options: 6) options(no-alignment)
- Add: live templates starting with `.`    
                                        
  | Element       | Abbreviation    | Expansion                     |
  |---------------|-----------------|------------------------------:|
  | Abbreviation  | `.abbreviation` |           `*[]: `             |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\`             |
  | Explicit link | `.link`         |            `[]()`             |
.
- aDD: LiIVeE teEmpLaAteES StaARtiING WiIth `.`

  | eLeEmeENt | aBBReEViIaAtiIoN | eXpaANSiIoN |
  |---------------|-----------------|------------------------------:|
  | aBBReEViIaAtiIoN | `.abbreviation` | `*[]: ` |
  | coDeE FeENceE | `.codefence` | \`\`\` ... \`\`\` |
  | eXpLiIciIt LiINK | `.link` | `[]()` |
````````````````````````````````


```````````````````````````````` example(GFM options: 7) options(no-width)
- Add: live templates starting with `.`    
                                        
  | Element       | Abbreviation    | Expansion                     |
  |---------------|-----------------|------------------------------:|
  | Abbreviation  | `.abbreviation` |           `*[]: `             |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\`             |
  | Explicit link | `.link`         |            `[]()`             |
.
- aDD: LiIVeE teEmpLaAteES StaARtiING WiIth `.`

  | eLeEmeENt | aBBReEViIaAtiIoN | eXpaANSiIoN |
  |---------------|-----------------|------------------------------:|
  | aBBReEViIaAtiIoN | `.abbreviation` | `*[]: ` |
  | coDeE FeENceE | `.codefence` | \`\`\` ... \`\`\` |
  | eXpLiIciIt LiINK | `.link` | `[]()` |
````````````````````````````````


```````````````````````````````` example(GFM options: 8) options(no-alignment, no-width)
- Add: live templates starting with `.`    
                                        
  | Element       | Abbreviation    | Expansion                     |
  |---------------|-----------------|------------------------------:|
  | Abbreviation  | `.abbreviation` |           `*[]: `             |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\`             |
  | Explicit link | `.link`         |            `[]()`             |
.
- aDD: LiIVeE teEmpLaAteES StaARtiING WiIth `.`

  | eLeEmeENt | aBBReEViIaAtiIoN | eXpaANSiIoN |
  |---------------|-----------------|------------------------------:|
  | aBBReEViIaAtiIoN | `.abbreviation` | `*[]: ` |
  | coDeE FeENceE | `.codefence` | \`\`\` ... \`\`\` |
  | eXpLiIciIt LiINK | `.link` | `[]()` |
````````````````````````````````


```````````````````````````````` example(GFM options: 9) options(fill-missing-columns)
| col11 | col12|
| col21 | col22|
|---|---|
| data1 | data2|data3
.
| coL11 | coL12 |
| coL21 | coL22 |
|---|---|
| DaAtaA1 | DaAtaA2 | DaAtaA3 |
````````````````````````````````


```````````````````````````````` example(GFM options: 10) options(left-align-marker-add)
| col11 | col12|
| col21 | col22|
|---|:---|
| data1 | data2|data3
.
| coL11 | coL12 |
| coL21 | coL22 |
|---|:---|
| DaAtaA1 | DaAtaA2 | DaAtaA3 |
````````````````````````````````


```````````````````````````````` example(GFM options: 11) options(left-align-marker-remove)
| col11 | col12|
| col21 | col22|
|---|:---|
| data1 | data2|data3
.
| coL11 | coL12 |
| coL21 | coL22 |
|---|:---|
| DaAtaA1 | DaAtaA2 | DaAtaA3 |
````````````````````````````````


```````````````````````````````` example GFM options: 12
day|time|spent
:---|:---:|--:
nov. 2. tue|10:00|4h 40m 
nov. 3. thu|11:00|4h
nov. 7. mon|10:20|4h 20m 
total:|| **13h**
.
| DaAyY | tiImeE | SpeENt |
|:---|:---:|--:|
| NoV. 2. tuUeE | 10:00 | 4h 40m |
| NoV. 3. thuU | 11:00 | 4h |
| NoV. 7. moN | 10:20 | 4h 20m |
| totaAL: || **13h** |
````````````````````````````````


