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
Abc|Def
````````````````````````````````


```````````````````````````````` example Tables Extension: 3
Abc | Def
.
Abc | Def
````````````````````````````````


```````````````````````````````` example Tables Extension: 4
Abc|Def
-|-
.
Abc|Def
-|-
````````````````````````````````


```````````````````````````````` example Tables Extension: 5
Abc|Def
--|--
.
Abc|Def
--|--
````````````````````````````````


paragraph lines that look like table separator should be left indented

```````````````````````````````` example Tables Extension: 6
Abc|Def
 |---|---
.
| Abc | Def |
|-----|-----|

````````````````````````````````


```````````````````````````````` example Tables Extension: 7
No
Abc|Def
---|---
.
No
Abc|Def
---|---
````````````````````````````````


```````````````````````````````` example Tables Extension: 8
Abc|Def
---|---
.
| Abc | Def |
|-----|-----|

````````````````````````````````


Separator columns need to be at least 3 characters, not 3 dashes

```````````````````````````````` example Tables Extension: 9
Abc|Def
:--|---
.
| Abc | Def |
|:----|-----|

````````````````````````````````


Separator columns need to be at least 3 characters, not 3 dashes

```````````````````````````````` example Tables Extension: 10
Abc|Def
--:|---
.
| Abc | Def |
|----:|-----|

````````````````````````````````


```````````````````````````````` example Tables Extension: 11
Abc|Def
:-:|---
.
| Abc | Def |
|:---:|-----|

````````````````````````````````


```````````````````````````````` example Tables Extension: 12
|Abc
|---
.
| Abc |
|-----|

````````````````````````````````


```````````````````````````````` example Tables Extension: 13
|Abc|
|---|
.
| Abc |
|-----|

````````````````````````````````


```````````````````````````````` example Tables Extension: 14
Abc|
---|
.
| Abc |
|-----|

````````````````````````````````


```````````````````````````````` example Tables Extension: 15
|Abc
---
.
|Abc
----

````````````````````````````````


```````````````````````````````` example Tables Extension: 16
Abc
|---
.
Abc
|---
````````````````````````````````


```````````````````````````````` example Tables Extension: 17
|Abc
|---
|1
.
| Abc |
|-----|
| 1   |

````````````````````````````````


```````````````````````````````` example Tables Extension: 18
|Abc|
|---|
|1|
.
| Abc |
|-----|
| 1   |

````````````````````````````````


```````````````````````````````` example Tables Extension: 19
Abc|
---|
1|
.
| Abc |
|-----|
| 1   |

````````````````````````````````


```````````````````````````````` example Tables Extension: 20
|Abc
---
|1
.
|Abc
----

|1
````````````````````````````````


```````````````````````````````` example Tables Extension: 21
|Abc
|---
1
.
| Abc |
|-----|

1
````````````````````````````````


```````````````````````````````` example Tables Extension: 22
Abc|Def
---|---
1|2
.
| Abc | Def |
|-----|-----|
| 1   | 2   |

````````````````````````````````


```````````````````````````````` example Tables Extension: 23
Abc|Def|Ghi
---|---
1|2|3
.
| Abc | Def | Ghi |
|-----|-----|-----|
| 1   | 2   | 3   |

````````````````````````````````


```````````````````````````````` example Tables Extension: 24
 Abc  | Def
 --- | ---
 1 | 2
.
| Abc | Def |
|-----|-----|
| 1   | 2   |

````````````````````````````````


```````````````````````````````` example Tables Extension: 25
Abc|Def
---|---
    1|2
.
| Abc | Def |
|-----|-----|
| 1   | 2   |

````````````````````````````````


```````````````````````````````` example Tables Extension: 26
|Abc|Def|
|---|---|
|1|2|
.
| Abc | Def |
|-----|-----|
| 1   | 2   |

````````````````````````````````


Embedded pipes in inline elements

```````````````````````````````` example Tables Extension: 27
Abc|Def
---|---
`|`|`|`
.
| Abc | Def |
|-----|-----|
| `|` | `|` |

````````````````````````````````


unclosed delimiters in cells

```````````````````````````````` example Tables Extension: 28
Abc|Def
---|---
`| | abc
.
| Abc | Def |
|-----|-----|-----|
| `   |     | abc |

````````````````````````````````


unclosed delimiters in cells

```````````````````````````````` example Tables Extension: 29
Abc|Def
---|---
**def | abc
.
|  Abc  | Def |
|-------|-----|
| **def | abc |

````````````````````````````````


```````````````````````````````` example Tables Extension: 30
*Abc*|Def
---|---
1|2
.
| *Abc* | Def |
|-------|-----|
| 1     | 2   |

````````````````````````````````


```````````````````````````````` example Tables Extension: 31
Abc|Def
---|---
1\\|2|20
.
| Abc | Def |
|-----|-----|----|
| 1\\ | 2   | 20 |

````````````````````````````````


Extra column should be truncated when GFM compatibility is selected

```````````````````````````````` example(Tables Extension: 32) options(gfm)
Abc|Def
---|---
1\\|2|20
.
| Abc | Def |
|-----|-----|
| 1\\ | 2   |

````````````````````````````````


```````````````````````````````` example Tables Extension: 33
Abc|Def
---|---
1\\\\|2
.
|  Abc  | Def |
|-------|-----|
| 1\\\\ | 2   |

````````````````````````````````


```````````````````````````````` example Tables Extension: 34
Abc|Def
:---|---
1|2
.
| Abc | Def |
|:----|-----|
| 1   | 2   |

````````````````````````````````


```````````````````````````````` example Tables Extension: 35
Abc|Def
---:|---
1|2
.
| Abc | Def |
|----:|-----|
|   1 | 2   |

````````````````````````````````


```````````````````````````````` example Tables Extension: 36
Abc|Def
:---:|---
1|2
.
| Abc | Def |
|:---:|-----|
|  1  | 2   |

````````````````````````````````


```````````````````````````````` example Tables Extension: 37
Abc|Def
---|:---:
1|2
.
| Abc | Def |
|-----|:---:|
| 1   |  2  |

````````````````````````````````


```````````````````````````````` example Tables Extension: 38
Abc|Def
 :--- |---
1|2
.
| Abc | Def |
|:----|-----|
| 1   | 2   |

````````````````````````````````


```````````````````````````````` example Tables Extension: 39
Abc|Def
--- :|---
.
Abc|Def
--- :|---
````````````````````````````````


```````````````````````````````` example Tables Extension: 40
Abc|Def
---|: ---
.
Abc|Def
---|: ---
````````````````````````````````


```````````````````````````````` example Tables Extension: 41
Abc|Def
---|--- :
.
Abc|Def
---|--- :
````````````````````````````````


```````````````````````````````` example Tables Extension: 42
Abc|Def
---|---
1|2|3
.
| Abc | Def |
|-----|-----|---|
| 1   | 2   | 3 |

````````````````````````````````


Extra columns truncated with GFM compatibility on.

```````````````````````````````` example(Tables Extension: 43) options(gfm)
Abc|Def
---|---
1|2|3
.
| Abc | Def |
|-----|-----|
| 1   | 2   |

````````````````````````````````


```````````````````````````````` example Tables Extension: 44
Abc|Def|Ghi
---|---|---
1|2
.
| Abc | Def | Ghi |
|-----|-----|-----|
| 1   | 2   |

````````````````````````````````


```````````````````````````````` example Tables Extension: 45
> Abc|Def
> ---|---
> 1|2
.
> | Abc | Def |
> |-----|-----|
> | 1   | 2   |

````````````````````````````````


```````````````````````````````` example Tables Extension: 46
Abc|Def
---|---
1|2
table, you are over
.
| Abc | Def |
|-----|-----|
| 1   | 2   |

table, you are over
````````````````````````````````


inlines should be processed

```````````````````````````````` example Tables Extension: 47
**Abc**|_Def_
---|---
[ref]|`code`
table, you are over

[ref]: /url
.
| **Abc** | _Def_  |
|---------|--------|
| [ref]   | `code` |

table, you are over

[ref]: /url

````````````````````````````````


inlines should be processed

```````````````````````````````` example Tables Extension: 48
|**Abc** **test** |_Def_ _Def_
---|---
[ref]|`code` `code`
table, you are over

[ref]: /url
.
| **Abc** **test** |  _Def_ _Def_  |
|------------------|---------------|
| [ref]            | `code` `code` |

table, you are over

[ref]: /url

````````````````````````````````


Column spans are created with repeated | pipes one for each additional column to span

```````````````````````````````` example Tables Extension: 49
|Abc|Def
|---|---|
| span ||
.
| Abc | Def |
|-----|-----|
| span     ||

````````````````````````````````


Now we try varying the header lines and make sure we get the right output

```````````````````````````````` example Tables Extension: 50
|Abc|Def
|Hij|Lmn
|---|---|
| span ||
.
| Abc | Def |
| Hij | Lmn |
|-----|-----|
| span     ||

````````````````````````````````


No header lines

```````````````````````````````` example Tables Extension: 51
|---|---|
| col1 | col2|
.
|------|------|
| col1 | col2 |

````````````````````````````````


No body lines

```````````````````````````````` example Tables Extension: 52
| col1 | col2|
|---|---|
.
| col1 | col2 |
|------|------|

````````````````````````````````


With caption

```````````````````````````````` example Tables Extension: 53
| col1 | col2|
|---|---|
         [Caption **bold** _italic_ `code`]          
.
| col1 | col2 |
|------|------|
[Caption **bold** _italic_ `code`]

````````````````````````````````


With caption

```````````````````````````````` example Tables Extension: 54
| col1 | col2|
|---|---|
[Caption]
.
| col1 | col2 |
|------|------|
[Caption]

````````````````````````````````


With caption but no caption output

```````````````````````````````` example(Tables Extension: 55) options(no-caption)
| col1 | col2|
|---|---|
[Caption]
.
| col1 | col2 |
|------|------|

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
| day         | time  |   spent |
|:------------|:-----:|--------:|
| nov. 2. tue | 10:00 |  4h 40m |
| nov. 3. thu | 11:00 |      4h |
| nov. 7. mon | 10:20 |  4h 20m |
| total:             || **13h** |

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
not a table, followed by a table

| col1 | col2 |
|------|------|

| col1  | col2  |
|-------|-------|
| data1 | data2 |

not a table, followed by a table

| col11 | col12 |
| col21 | col22 |
|-------|-------|
| data1 | data2 |

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
| col11  | col12  | col13  |
| col21  | col22  | col23  |
| col31  | col32  | col33  |
|--------|--------|--------|
| data11 | data12 | data13 |
| data21 | data22 | data23 |
| data31 | data32 | data33 |

````````````````````````````````


keep cell whitespace

```````````````````````````````` example Tables Extension: 59
 Abc  | Def
 --- | ---
 1 | 2
.
| Abc | Def |
|-----|-----|
| 1   | 2   |

````````````````````````````````


Custom class name

```````````````````````````````` example Tables Extension: 60
Abc|Def
---|---
.
| Abc | Def |
|-----|-----|

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
- Add: live templates starting with `.`

  |    Element    |  Abbreviation   |     Expansion     |
  |---------------|-----------------|-------------------|
  | Abbreviation  | `.abbreviation` | `*[]: `           |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\` |
  | Explicit link | `.link`         | `[]()`            |

````````````````````````````````


### Block Quote

```````````````````````````````` example Tables Extension - Block Quote: 1
> | Element       | Abbreviation    | Expansion                                               |
> |---------------|-----------------|---------------------------------------------------------|
> | Abbreviation  | `.abbreviation` | `*[]: `                                                 |
> | Code fence    | `.codefence`    | \`\`\` ... \`\`\`                                       |
> | Explicit link | `.link`         | `[]()`                                                  |
.
> |    Element    |  Abbreviation   |     Expansion     |
> |---------------|-----------------|-------------------|
> | Abbreviation  | `.abbreviation` | `*[]: `           |
> | Code fence    | `.codefence`    | \`\`\` ... \`\`\` |
> | Explicit link | `.link`         | `[]()`            |

````````````````````````````````


```````````````````````````````` example Tables Extension - Block Quote: 2
> | Element       | Abbreviation    | Expansion                                               |
> |---------------|-----------------|---------------------------------------------------------|
> | Abbreviation  | `.abbreviation` | `*[]: `                                                 |
> | Code fence    | `.codefence`    | \`\`\` ... \`\`\`                                       |
> | Explicit link | `.link`         | `[]()`                                                  |
> [caption]
.
> |    Element    |  Abbreviation   |     Expansion     |
> |---------------|-----------------|-------------------|
> | Abbreviation  | `.abbreviation` | `*[]: `           |
> | Code fence    | `.codefence`    | \`\`\` ... \`\`\` |
> | Explicit link | `.link`         | `[]()`            |
> [caption]

````````````````````````````````


real life table

```````````````````````````````` example Tables Extension - Block Quote: 3
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
| &nbsp;&nbsp;&nbsp;&nbsp;Document formatting with text wrapping, list renumbering, aranging of elements, etc.            |       |    X     |
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
| &nbsp;&nbsp;&nbsp;&nbsp;Document formatting with text wrapping, list renumbering, aranging of elements, etc.            |       |    X     |
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

````````````````````````````````


## GFM

invalid table:

```````````````````````````````` example(GFM: 1) options(gfm)
| A | B | C |
|-----------|
| a | b | c |
| b | a | c |
.
| A | B | C |
|-----------|
| a | b | c |
| b | a | c |
````````````````````````````````


invalid table:

```````````````````````````````` example(GFM: 2) options(gfm)
| A | B | C |
| a | b | c |
| b | a | c |
.
| A | B | C |
| a | b | c |
| b | a | c |
````````````````````````````````


```````````````````````````````` example GFM: 3
| Feature                                                                                                                 | Basic | Enhanced |
|:------------------------------------------------------------------------------------------------------------------------|:-----:|:--------:|
| Works with builds 143.2370 or newer, product version IDEA 15.0.6                                                        |   X   |    X     |
| Preview Tab so you can see what the rendered markdown will look like on GitHub.                                         |   X   |    X     |
| Syntax highlighting                                                                                                     |   X   |    X     |
| Table syntax highlighting stripes rows and columns                                                                      |   X   |    X     |
| Support for Default and Darcula color schemes for preview tab                                                           |   X   |    X     |
| Warning and Error Annotations to help you validate wiki link errors                                                     |   X   |    X     |
.
| Feature                                                                         | Basic | Enhanced |
|:--------------------------------------------------------------------------------|:-----:|:--------:|
| Works with builds 143.2370 or newer, product version IDEA 15.0.6                |   X   |    X     |
| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |
| Syntax highlighting                                                             |   X   |    X     |
| Table syntax highlighting stripes rows and columns                              |   X   |    X     |
| Support for Default and Darcula color schemes for preview tab                   |   X   |    X     |
| Warning and Error Annotations to help you validate wiki link errors             |   X   |    X     |

````````````````````````````````


in item

```````````````````````````````` example(GFM: 4) options(gfm)
- Add: live templates starting with `.`    
                                        
  | Element       | Abbreviation    | Expansion                                               |
  |---------------|-----------------|---------------------------------------------------------|
  | Abbreviation  | `.abbreviation` | `*[]: `                                                 |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\`                                       |
  | Explicit link | `.link`         | `[]()`                                                  |
.
- Add: live templates starting with `.`

  |    Element    |  Abbreviation   |     Expansion     |
  |---------------|-----------------|-------------------|
  | Abbreviation  | `.abbreviation` | `*[]: `           |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\` |
  | Explicit link | `.link`         | `[]()`            |

````````````````````````````````


default

```````````````````````````````` example GFM: 5
- Add: live templates starting with `.`    
                                        
  | Element       | Abbreviation    | Expansion                     |
  |---------------|-----------------|------------------------------:|
  | Abbreviation  | `.abbreviation` |           `*[]: `             |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\`             |
  | Explicit link | `.link`         |            `[]()`             |
.
- Add: live templates starting with `.`

  |    Element    |  Abbreviation   |         Expansion |
  |---------------|-----------------|------------------:|
  | Abbreviation  | `.abbreviation` |           `*[]: ` |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\` |
  | Explicit link | `.link`         |            `[]()` |

````````````````````````````````


```````````````````````````````` example(GFM: 6) options(no-alignment)
- Add: live templates starting with `.`    
                                        
  | Element       | Abbreviation    | Expansion                     |
  |---------------|-----------------|------------------------------:|
  | Abbreviation  | `.abbreviation` |           `*[]: `             |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\`             |
  | Explicit link | `.link`         |            `[]()`             |
.
- Add: live templates starting with `.`

  |    Element    |  Abbreviation   |     Expansion     |
  |---------------|-----------------|------------------:|
  | Abbreviation  | `.abbreviation` | `*[]: `           |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\` |
  | Explicit link | `.link`         | `[]()`            |

````````````````````````````````


```````````````````````````````` example(GFM: 7) options(no-width)
- Add: live templates starting with `.`    
                                        
  | Element       | Abbreviation    | Expansion                     |
  |---------------|-----------------|------------------------------:|
  | Abbreviation  | `.abbreviation` |           `*[]: `             |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\`             |
  | Explicit link | `.link`         |            `[]()`             |
.
- Add: live templates starting with `.`

  | Element | Abbreviation | Expansion |
  |---------------|-----------------|------------------:|
  | Abbreviation | `.abbreviation` | `*[]: ` |
  | Code fence | `.codefence` | \`\`\` ... \`\`\` |
  | Explicit link | `.link` | `[]()` |

````````````````````````````````


```````````````````````````````` example(GFM: 8) options(no-alignment, no-width)
- Add: live templates starting with `.`    
                                        
  | Element       | Abbreviation    | Expansion                     |
  |---------------|-----------------|------------------------------:|
  | Abbreviation  | `.abbreviation` |           `*[]: `             |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\`             |
  | Explicit link | `.link`         |            `[]()`             |
.
- Add: live templates starting with `.`

  | Element | Abbreviation | Expansion |
  |---------------|-----------------|------------------:|
  | Abbreviation | `.abbreviation` | `*[]: ` |
  | Code fence | `.codefence` | \`\`\` ... \`\`\` |
  | Explicit link | `.link` | `[]()` |

````````````````````````````````


```````````````````````````````` example(GFM: 9) options(fill-missing-columns)
| col11 | col12|
| col21 | col22|
|---|---|
| data1 | data2|data3
.
| col11 | col12 |       |
| col21 | col22 |       |
|-------|-------|-------|
| data1 | data2 | data3 |

````````````````````````````````


```````````````````````````````` example(GFM: 10) options(left-align-marker-add)
| col11 | col12|
| col21 | col22|
|---|:---|
| data1 | data2|data3
.
| col11 | col12 |
| col21 | col22 |
|:------|:------|:------|
| data1 | data2 | data3 |

````````````````````````````````


```````````````````````````````` example(GFM: 11) options(left-align-marker-remove)
| col11 | col12|
| col21 | col22|
|---|:---|
| data1 | data2|data3
.
| col11 | col12 |
| col21 | col22 |
|-------|-------|-------|
| data1 | data2 | data3 |

````````````````````````````````


```````````````````````````````` example GFM: 12
day|time|spent
:---|:---:|--:
nov. 2. tue|10:00|4h 40m 
nov. 3. thu|11:00|4h
nov. 7. mon|10:20|4h 20m 
total:|| **13h**
.
| day         | time  |   spent |
|:------------|:-----:|--------:|
| nov. 2. tue | 10:00 |  4h 40m |
| nov. 3. thu | 11:00 |      4h |
| nov. 7. mon | 10:20 |  4h 20m |
| total:             || **13h** |

````````````````````````````````


Should match

```````````````````````````````` example(GFM: 13) options(markdown-navigator)
| Feature          | Basic | Enhanced |
|:-----------------|:-----:|:--------:|
| iew Tab soasdsfd |   X   |    X     |
| dfax highig      |   X   |    X     |
| Table syntax h   |   X   |    X     |
| Support for De   |   X   |    X     |
| Warning and Er   |   X   |    X     |
| Link address c   |   X   |    X     |
| Quick Fixes fo   |   X   |    X     |
| GFM Task lis     |   X   |    X     |
| Line markers,    |   X   |    X     |
| Markdown exten   |   X   |    X     |
| GitHub wiki su   |   X   |    X     |
| GitHub compati   |   X   |    X     |
| Swing and Java   |   X   |    X     |
| Supports Highl   |   X   |    X     |
| **Multi-line I   |   X   |    X     |
| Supports **Jav   |   X   |    X     |

.
| Feature          | Basic | Enhanced |
|:-----------------|:-----:|:--------:|
| iew Tab soasdsfd |   X   |    X     |
| dfax highig      |   X   |    X     |
| Table syntax h   |   X   |    X     |
| Support for De   |   X   |    X     |
| Warning and Er   |   X   |    X     |
| Link address c   |   X   |    X     |
| Quick Fixes fo   |   X   |    X     |
| GFM Task lis     |   X   |    X     |
| Line markers,    |   X   |    X     |
| Markdown exten   |   X   |    X     |
| GitHub wiki su   |   X   |    X     |
| GitHub compati   |   X   |    X     |
| Swing and Java   |   X   |    X     |
| Supports Highl   |   X   |    X     |
| **Multi-line I   |   X   |    X     |
| Supports **Jav   |   X   |    X     |

````````````````````````````````


## Diagnostic/3095

Exception in fill table columns when cell text is empty

```````````````````````````````` example(Diagnostic/3095: 1) options(fill-missing-columns)
| one | two | three |
|-----|-----|-------|
||
.
| one | two | three |
|-----|-----|-------|
|     |     |       |

````````````````````````````````


## Tracked Offset

```````````````````````````````` example Tracked Offset: 1
⦙Abc|Def
---|---
Uvw|Xyz
.
| ⦙Abc | Def |
|-----|-----|
| Uvw | Xyz |

````````````````````````````````


```````````````````````````````` example Tracked Offset: 2
some prefix text

⦙Abc|Def
---|---
Uvw|Xyz
.
some prefix text

| ⦙Abc | Def |
|-----|-----|
| Uvw | Xyz |

````````````````````````````````


```````````````````````````````` example Tracked Offset: 3
Abc⦙|Def
---|---
Uvw|Xyz
.
| Abc⦙ | Def |
|-----|-----|
| Uvw | Xyz |

````````````````````````````````


```````````````````````````````` example Tracked Offset: 4
Abc|⦙Def
---|---
Uvw|Xyz
.
| Abc | ⦙Def |
|-----|-----|
| Uvw | Xyz |

````````````````````````````````


```````````````````````````````` example Tracked Offset: 5
Abc|Def⦙
---|---
Uvw|Xyz
.
| Abc | Def⦙ |
|-----|-----|
| Uvw | Xyz |

````````````````````````````````


```````````````````````````````` example Tracked Offset: 6
Abc|Def
⦙---|---
Uvw|Xyz
.
| Abc | Def |
|⦙-----|-----|
| Uvw | Xyz |

````````````````````````````````


```````````````````````````````` example Tracked Offset: 7
Abc|Def
---⦙|---
Uvw|Xyz
.
| Abc | Def |
|-----⦙|-----|
| Uvw | Xyz |

````````````````````````````````


```````````````````````````````` example Tracked Offset: 8
Abc|Def
---|⦙---
Uvw|Xyz
.
| Abc | Def |
|-----|⦙-----|
| Uvw | Xyz |

````````````````````````````````


```````````````````````````````` example Tracked Offset: 9
Abc|Def
---|---⦙
Uvw|Xyz
.
| Abc | Def |
|-----|-----⦙|
| Uvw | Xyz |

````````````````````````````````


```````````````````````````````` example Tracked Offset: 10
Abc|Def
---|---
⦙Uvw|Xyz
.
| Abc | Def |
|-----|-----|
| ⦙Uvw | Xyz |

````````````````````````````````


```````````````````````````````` example Tracked Offset: 11
Abc|Def
---|---
Uvw⦙|Xyz
.
| Abc | Def |
|-----|-----|
| Uvw⦙ | Xyz |

````````````````````````````````


```````````````````````````````` example Tracked Offset: 12
Abc|Def
---|---
Uvw|⦙Xyz
.
| Abc | Def |
|-----|-----|
| Uvw | ⦙Xyz |

````````````````````````````````


```````````````````````````````` example Tracked Offset: 13
Abc|Def
---|---
Uvw|Xyz⦙
.
| Abc | Def |
|-----|-----|
| Uvw | Xyz⦙ |

````````````````````````````````


### Indented

```````````````````````````````` example Tracked Offset - Indented: 1
* item
    
⦙Abc|Def
---|---
.
* item

| ⦙Abc | Def |
|-----|-----|

````````````````````````````````


```````````````````````````````` example Tracked Offset - Indented: 2
* item
    
  Abc⦙|Def
  ---|---
.
* item

  | Abc⦙ | Def |
  |-----|-----|

````````````````````````````````


```````````````````````````````` example Tracked Offset - Indented: 3
* item
    
  Abc|⦙Def
  ---|---
.
* item

  | Abc | ⦙Def |
  |-----|-----|

````````````````````````````````


```````````````````````````````` example Tracked Offset - Indented: 4
* item
    
  Abc|Def⦙
  ---|---
.
* item

  | Abc | Def⦙ |
  |-----|-----|

````````````````````````````````


```````````````````````````````` example Tracked Offset - Indented: 5
* item
    
  Abc|Def
  ⦙---|---
.
* item

  | Abc | Def |
  |⦙-----|-----|

````````````````````````````````


```````````````````````````````` example Tracked Offset - Indented: 6
* item
    
  Abc|Def
  ---⦙|---
.
* item

  | Abc | Def |
  |-----⦙|-----|

````````````````````````````````


```````````````````````````````` example Tracked Offset - Indented: 7
* item
    
  Abc|Def
  ---|⦙---
.
* item

  | Abc | Def |
  |-----|⦙-----|

````````````````````````````````


```````````````````````````````` example Tracked Offset - Indented: 8
* item
    
  Abc|Def
  ---|---⦙
.
* item

  | Abc | Def |
  |-----|-----⦙|

````````````````````````````````


fails in md nav

```````````````````````````````` example(Tracked Offset - Indented: 9) options(left-align-marker-add)
⦙|name|display|title_en|title_de|
|---|---|---|---|
|none| | | |
|Prof.|Prof.|Prof.|Prof.|
|Prof.Dr.|Prof.Dr.|Prof.Dr.|Prof.Dr.|
|Prof.Dr.Dr.|Prof.Dr.Dr.|Prof.Dr.Dr.|Prof.Dr.Dr.|
|Dr.|Dr.|Dr.|Dr.|
|Dr.Dr.|Dr.Dr.|Dr.Dr.|Dr.Dr.|
.
⦙| name        | display     | title_en    | title_de    |
|:------------|:------------|:------------|:------------|
| none        |             |             |             |
| Prof.       | Prof.       | Prof.       | Prof.       |
| Prof.Dr.    | Prof.Dr.    | Prof.Dr.    | Prof.Dr.    |
| Prof.Dr.Dr. | Prof.Dr.Dr. | Prof.Dr.Dr. | Prof.Dr.Dr. |
| Dr.         | Dr.         | Dr.         | Dr.         |
| Dr.Dr.      | Dr.Dr.      | Dr.Dr.      | Dr.Dr.      |

````````````````````````````````


```````````````````````````````` example(Tracked Offset - Indented: 10) options(left-align-marker-add, NO_FILE_EOL)
|name⦙|display|title_en|title_de|
|---|---|---|---|
|none| | | |
|Prof.|Prof.|Prof.|Prof.|
|Prof.Dr.|Prof.Dr.|Prof.Dr.|Prof.Dr.|
|Prof.Dr.Dr.|Prof.Dr.Dr.|Prof.Dr.Dr.|Prof.Dr.Dr.|
|Dr.|Dr.|Dr.|Dr.|
|Dr.Dr.|Dr.Dr.|Dr.Dr.|Dr.Dr.|
.
| name⦙        | display     | title_en    | title_de    |
|:------------|:------------|:------------|:------------|
| none        |             |             |             |
| Prof.       | Prof.       | Prof.       | Prof.       |
| Prof.Dr.    | Prof.Dr.    | Prof.Dr.    | Prof.Dr.    |
| Prof.Dr.Dr. | Prof.Dr.Dr. | Prof.Dr.Dr. | Prof.Dr.Dr. |
| Dr.         | Dr.         | Dr.         | Dr.         |
| Dr.Dr.      | Dr.Dr.      | Dr.Dr.      | Dr.Dr.      |

````````````````````````````````


```````````````````````````````` example(Tracked Offset - Indented: 11) options(left-align-marker-add, NO_FILE_EOL)
|name⦙|display|title_en|title_de|
|---|---|---|---|
|no⦙ne| | | |
|Prof⦙.|Prof.⦙|Prof.|Prof.|
.
| name⦙  | display | title_en | title_de |
|:------|:--------|:---------|:---------|
| no⦙ne  |         |          |          |
| Prof⦙. | Prof.⦙   | Prof.    | Prof.    |

````````````````````````````````


in item

```````````````````````````````` example Tracked Offset - Indented: 12
- Add: live templates starting with `.`

  | Element       | Abbreviation    | Expansion                                               |
  |---------------|-----------------|---------------------------------------------------------|
  | Abbreviation  | `.abbreviation` | `*[]: `                                                 |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\`                                       |
  | Explicit link | `.link`         | `[]()`                                                  |
.
- Add: live templates starting with `.`

  |    Element    |  Abbreviation   |     Expansion     |
  |---------------|-----------------|-------------------|
  | Abbreviation  | `.abbreviation` | `*[]: `           |
  | Code fence    | `.codefence`    | \`\`\` ... \`\`\` |
  | Explicit link | `.link`         | `[]()`            |

````````````````````````````````


## Width Provider

```````````````````````````````` example(Width Provider: 1) options(width-provider, left-align-marker-add)
|   字段名   | 说明               | 是否必须 |   备注   |
|---------|:-----------------|------|--------|
| status  | 状态码              | 是    | String |
| result  | 内容主体             | 否    | Object |
| message | 异常情况的错误信息，供显示给用户 | 否    | String |
[ ]
.
| 字段名   | 说明                        | 是否必须 | 备注    |
|:--------|:---------------------------|:-------|:-------|
| status  | 状态码                      | 是      | String |
| result  | 内容主体                    | 否      | Object |
| message | 异常情况的错误信息，供显示给用户 | 否      | String |
[ ]

````````````````````````````````


