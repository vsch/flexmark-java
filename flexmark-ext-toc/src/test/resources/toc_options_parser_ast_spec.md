---
title: TocOptionsParser Spec
author:
version:
date: '2016-07-09'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## TocOptionsParser

Used for parsing toc tag style options

The TOC tag has the following format: `[TOC style]: # "Title"`

1. `style` consists of space separated list of options:

    - `levels=levelList` where level list is a comma separated list of levels or ranges. Default
      is to include heading levels 2 and 3. Examples:
        - `levels=4` include levels 2,3 and 4
        - `levels=2-4` include levels 2,3 and 4. same as `levels=4`
        - `levels=2-4,5` include levels 2,3,4 and 5
        - `levels=1,3` include levels 1 and 3

    - `html` generate HTML version of the TOC

    - `markdown` generate Markdown version of the TOC

    - `text` to only include the text of the heading

    - `formatted` to include text and inline formatting

    - `hierarchy` to render as hierarchical list in order of appearance in the document

    - `flat` to render as a flat list in order of appearance in the document

    - `reversed` to render as a flat list in order of appearance in the document

    - `sort-up` to render as a flat list sorted alphabetically by heading text only, no inlines

    - `sort-down` to render as a flat list sorted reversed alphabetically by heading text only,
      no inlines

    - `bullet` to use a bullet list for the TOC items

    - `numbered` to use a numbered list for TOC items

Default

```````````````````````````````` example TocOptionsParser: 1
.
.
Example[0, 0]
````````````````````````````````


Simple levels results in 2- level, or just level if < 2

```````````````````````````````` example TocOptionsParser: 2
levels=0
levels=1
levels=2
levels=3
levels=4
levels=5
levels=6
levels=7
levels=8
levels=9
.
'levels=0' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]
  full: [TOC levels=3 bullet formatted hierarchy]
'levels=1' => TocOptions { levels=2, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC levels=1]
  full: [TOC levels=1 bullet formatted hierarchy]
'levels=2' => TocOptions { levels=4, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC levels=2,2]
  full: [TOC levels=2,2 bullet formatted hierarchy]
'levels=3' => TocOptions { levels=8, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC levels=3,3]
  full: [TOC levels=3,3 bullet formatted hierarchy]
'levels=4' => TocOptions { levels=16, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC levels=4,4]
  full: [TOC levels=4,4 bullet formatted hierarchy]
'levels=5' => TocOptions { levels=32, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC levels=5,5]
  full: [TOC levels=5,5 bullet formatted hierarchy]
'levels=6' => TocOptions { levels=64, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC levels=6,6]
  full: [TOC levels=6,6 bullet formatted hierarchy]
'levels=7' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]
  full: [TOC levels=3 bullet formatted hierarchy]
'levels=8' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]
  full: [TOC levels=3 bullet formatted hierarchy]
'levels=9' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]
  full: [TOC levels=3 bullet formatted hierarchy]
.
Example[0, 90]
  Style[0, 8]
    TocOptions[0, 8] status:IGNORED
      Message[7, 8] message:levels option value 0 is not an integer in the range [1, 6]
  Style[9, 17]
    TocOptions[9, 17] status:VALID
  Style[18, 26]
    TocOptions[18, 26] status:VALID
  Style[27, 35]
    TocOptions[27, 35] status:VALID
  Style[36, 44]
    TocOptions[36, 44] status:VALID
  Style[45, 53]
    TocOptions[45, 53] status:VALID
  Style[54, 62]
    TocOptions[54, 62] status:VALID
  Style[63, 71]
    TocOptions[63, 71] status:IGNORED
      Message[70, 71] message:levels option value 7 is not an integer in the range [1, 6]
  Style[72, 80]
    TocOptions[72, 80] status:IGNORED
      Message[79, 80] message:levels option value 8 is not an integer in the range [1, 6]
  Style[81, 89]
    TocOptions[81, 89] status:IGNORED
      Message[88, 89] message:levels option value 9 is not an integer in the range [1, 6]
````````````````````````````````


Level ranges

```````````````````````````````` example TocOptionsParser: 3
levels=1-3 
levels=3-1 
levels=0-9
.
'levels=1-3 ' => TocOptions { levels=14, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC levels=1-3]
  full: [TOC levels=1-3 bullet formatted hierarchy]
'levels=3-1 ' => TocOptions { levels=14, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC levels=1-3]
  full: [TOC levels=1-3 bullet formatted hierarchy]
'levels=0-9' => TocOptions { levels=126, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC levels=1-6]
  full: [TOC levels=1-6 bullet formatted hierarchy]
.
Example[0, 35]
  Style[0, 11]
    TocOptions[0, 10] status:VALID
  Style[12, 23]
    TocOptions[12, 22] status:VALID
  Style[24, 34]
    TocOptions[24, 34] status:WEAK_WARNING
      Message[31, 34] message:levels option value 0-9 truncated to range [1, 6]
````````````````````````````````


other options, not using sim-toc

```````````````````````````````` example TocOptionsParser: 4
html
markdown
numbered
bullet
text
formatted
hierarchy
flat
reversed
increasing
decreasing
.
'html' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]
  full: [TOC levels=3 bullet formatted hierarchy]
'markdown' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]
  full: [TOC levels=3 bullet formatted hierarchy]
'numbered' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=true, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC numbered]
  full: [TOC levels=3 numbered formatted hierarchy]
'bullet' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]
  full: [TOC levels=3 bullet formatted hierarchy]
'text' => TocOptions { levels=12, isHtml=false, isTextOnly=true, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC text]
  full: [TOC levels=3 bullet text hierarchy]
'formatted' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]
  full: [TOC levels=3 bullet formatted hierarchy]
'hierarchy' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]
  full: [TOC levels=3 bullet formatted hierarchy]
'flat' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=FLAT, rawTitle='Table of Contents' }
  diff: [TOC flat]
  full: [TOC levels=3 bullet formatted flat]
'reversed' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=FLAT_REVERSED, rawTitle='Table of Contents' }
  diff: [TOC reversed]
  full: [TOC levels=3 bullet formatted reversed]
'increasing' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=SORTED, rawTitle='Table of Contents' }
  diff: [TOC increasing]
  full: [TOC levels=3 bullet formatted increasing]
'decreasing' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=SORTED_REVERSED, rawTitle='Table of Contents' }
  diff: [TOC decreasing]
  full: [TOC levels=3 bullet formatted decreasing]
.
Example[0, 91]
  Style[0, 4]
    TocOptions[0, 4] status:ERROR
      Message[0, 4] message:Option html does not match any of: levels, bullet, numbered, text, formatted, hierarchy, flat, reversed, increasing, decreasing
  Style[5, 13]
    TocOptions[5, 13] status:ERROR
      Message[5, 13] message:Option markdown does not match any of: levels, bullet, numbered, text, formatted, hierarchy, flat, reversed, increasing, decreasing
  Style[14, 22]
    TocOptions[14, 22] status:VALID
  Style[23, 29]
    TocOptions[23, 29] status:VALID
  Style[30, 34]
    TocOptions[30, 34] status:VALID
  Style[35, 44]
    TocOptions[35, 44] status:VALID
  Style[45, 54]
    TocOptions[45, 54] status:VALID
  Style[55, 59]
    TocOptions[55, 59] status:VALID
  Style[60, 68]
    TocOptions[60, 68] status:VALID
  Style[69, 79]
    TocOptions[69, 79] status:VALID
  Style[80, 90]
    TocOptions[80, 90] status:VALID
````````````````````````````````


other options, using sim-toc

```````````````````````````````` example(TocOptionsParser: 5) options(sim-toc)
html
markdown
numbered
bullet
text
formatted
hierarchy
flat
reversed
increasing
decreasing
.
'html' => TocOptions { levels=12, isHtml=true, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC html]: #
  full: [TOC levels=3 html bullet formatted hierarchy]: # "# Table of Contents"
'markdown' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]: #
  full: [TOC levels=3 markdown bullet formatted hierarchy]: # "# Table of Contents"
'numbered' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=true, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC numbered]: #
  full: [TOC levels=3 markdown numbered formatted hierarchy]: # "# Table of Contents"
'bullet' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]: #
  full: [TOC levels=3 markdown bullet formatted hierarchy]: # "# Table of Contents"
'text' => TocOptions { levels=12, isHtml=false, isTextOnly=true, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC text]: #
  full: [TOC levels=3 markdown bullet text hierarchy]: # "# Table of Contents"
'formatted' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]: #
  full: [TOC levels=3 markdown bullet formatted hierarchy]: # "# Table of Contents"
'hierarchy' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]: #
  full: [TOC levels=3 markdown bullet formatted hierarchy]: # "# Table of Contents"
'flat' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=FLAT, rawTitle='Table of Contents' }
  diff: [TOC flat]: #
  full: [TOC levels=3 markdown bullet formatted flat]: # "# Table of Contents"
'reversed' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=FLAT_REVERSED, rawTitle='Table of Contents' }
  diff: [TOC reversed]: #
  full: [TOC levels=3 markdown bullet formatted reversed]: # "# Table of Contents"
'increasing' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=SORTED, rawTitle='Table of Contents' }
  diff: [TOC increasing]: #
  full: [TOC levels=3 markdown bullet formatted increasing]: # "# Table of Contents"
'decreasing' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=SORTED_REVERSED, rawTitle='Table of Contents' }
  diff: [TOC decreasing]: #
  full: [TOC levels=3 markdown bullet formatted decreasing]: # "# Table of Contents"
.
Example[0, 91]
  Style[0, 4]
    SimTocOptions[0, 4] status:VALID
  Style[5, 13]
    SimTocOptions[5, 13] status:VALID
  Style[14, 22]
    SimTocOptions[14, 22] status:VALID
  Style[23, 29]
    SimTocOptions[23, 29] status:VALID
  Style[30, 34]
    SimTocOptions[30, 34] status:VALID
  Style[35, 44]
    SimTocOptions[35, 44] status:VALID
  Style[45, 54]
    SimTocOptions[45, 54] status:VALID
  Style[55, 59]
    SimTocOptions[55, 59] status:VALID
  Style[60, 68]
    SimTocOptions[60, 68] status:VALID
  Style[69, 79]
    SimTocOptions[69, 79] status:VALID
  Style[80, 90]
    SimTocOptions[80, 90] status:VALID
````````````````````````````````


ambiguous options don't exist yet

```````````````````````````````` example(TocOptionsParser: 6) options(IGNORE)
html
markdown
numbered
bullet
text
formatted
.
.
````````````````````````````````


invalid options

```````````````````````````````` example TocOptionsParser: 7
htmls
markdowns
snumbered
bbullet
stext
sformatted
.
'htmls' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]
  full: [TOC levels=3 bullet formatted hierarchy]
'markdowns' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]
  full: [TOC levels=3 bullet formatted hierarchy]
'snumbered' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]
  full: [TOC levels=3 bullet formatted hierarchy]
'bbullet' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]
  full: [TOC levels=3 bullet formatted hierarchy]
'stext' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]
  full: [TOC levels=3 bullet formatted hierarchy]
'sformatted' => TocOptions { levels=12, isHtml=false, isTextOnly=false, isNumbered=false, titleLevel=1, title='Table of Contents', rawTitleLevel=1, listType=HIERARCHY, rawTitle='Table of Contents' }
  diff: [TOC]
  full: [TOC levels=3 bullet formatted hierarchy]
.
Example[0, 51]
  Style[0, 5]
    TocOptions[0, 5] status:ERROR
      Message[0, 5] message:Option htmls does not match any of: levels, bullet, numbered, text, formatted, hierarchy, flat, reversed, increasing, decreasing
  Style[6, 15]
    TocOptions[6, 15] status:ERROR
      Message[6, 15] message:Option markdowns does not match any of: levels, bullet, numbered, text, formatted, hierarchy, flat, reversed, increasing, decreasing
  Style[16, 25]
    TocOptions[16, 25] status:ERROR
      Message[16, 25] message:Option snumbered does not match any of: levels, bullet, numbered, text, formatted, hierarchy, flat, reversed, increasing, decreasing
  Style[26, 33]
    TocOptions[26, 33] status:ERROR
      Message[26, 33] message:Option bbullet does not match any of: levels, bullet, numbered, text, formatted, hierarchy, flat, reversed, increasing, decreasing
  Style[34, 39]
    TocOptions[34, 39] status:ERROR
      Message[34, 39] message:Option stext does not match any of: levels, bullet, numbered, text, formatted, hierarchy, flat, reversed, increasing, decreasing
  Style[40, 50]
    TocOptions[40, 50] status:ERROR
      Message[40, 50] message:Option sformatted does not match any of: levels, bullet, numbered, text, formatted, hierarchy, flat, reversed, increasing, decreasing
````````````````````````````````


