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
'levels=0' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC]
  full: [TOC levels=3 bullet formatted]
'levels=1' => TocOptions[levels=<1>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC levels=1]
  full: [TOC levels=1 bullet formatted]
'levels=2' => TocOptions[levels=<2>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC levels=2,2]
  full: [TOC levels=2,2 bullet formatted]
'levels=3' => TocOptions[levels=<3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC levels=3,3]
  full: [TOC levels=3,3 bullet formatted]
'levels=4' => TocOptions[levels=<4>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC levels=4,4]
  full: [TOC levels=4,4 bullet formatted]
'levels=5' => TocOptions[levels=<5>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC levels=5,5]
  full: [TOC levels=5,5 bullet formatted]
'levels=6' => TocOptions[levels=<6>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC levels=6,6]
  full: [TOC levels=6,6 bullet formatted]
'levels=7' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC]
  full: [TOC levels=3 bullet formatted]
'levels=8' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC]
  full: [TOC levels=3 bullet formatted]
'levels=9' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC]
  full: [TOC levels=3 bullet formatted]
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
'levels=1-3 ' => TocOptions[levels=<1,2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC levels=1-3]
  full: [TOC levels=1-3 bullet formatted]
'levels=3-1 ' => TocOptions[levels=<1,2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC levels=1-3]
  full: [TOC levels=1-3 bullet formatted]
'levels=0-9' => TocOptions[levels=<1,2,3,4,5,6>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC levels=1-6]
  full: [TOC levels=1-6 bullet formatted]
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
.
'html' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC]
  full: [TOC levels=3 bullet formatted]
'markdown' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC]
  full: [TOC levels=3 bullet formatted]
'numbered' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=true,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC numbered]
  full: [TOC levels=3 numbered formatted]
'bullet' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC]
  full: [TOC levels=3 bullet formatted]
'text' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=true,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC text]
  full: [TOC levels=3 bullet text]
'formatted' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC]
  full: [TOC levels=3 bullet formatted]
.
Example[0, 45]
  Style[0, 4]
    TocOptions[0, 4] status:ERROR
      Message[0, 4] message:Option html does not match any of: levels, bullet, numbered, text, formatted
  Style[5, 13]
    TocOptions[5, 13] status:ERROR
      Message[5, 13] message:Option markdown does not match any of: levels, bullet, numbered, text, formatted
  Style[14, 22]
    TocOptions[14, 22] status:VALID
  Style[23, 29]
    TocOptions[23, 29] status:VALID
  Style[30, 34]
    TocOptions[30, 34] status:VALID
  Style[35, 44]
    TocOptions[35, 44] status:VALID
````````````````````````````````


other options, using sim-toc

```````````````````````````````` example(TocOptionsParser: 5) options(sim-toc)
html
markdown
numbered
bullet
text
formatted
.
'html' => TocOptions[levels=<2,3>,  isHtml=true,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC html]: #
  full: [TOC levels=3 html bullet formatted]: # "# Table of Contents"
'markdown' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC]: #
  full: [TOC levels=3 markdown bullet formatted]: # "# Table of Contents"
'numbered' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=true,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC numbered]: #
  full: [TOC levels=3 markdown numbered formatted]: # "# Table of Contents"
'bullet' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC]: #
  full: [TOC levels=3 markdown bullet formatted]: # "# Table of Contents"
'text' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=true,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC text]: #
  full: [TOC levels=3 markdown bullet text]: # "# Table of Contents"
'formatted' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC]: #
  full: [TOC levels=3 markdown bullet formatted]: # "# Table of Contents"
.
Example[0, 45]
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
'htmls' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC]
  full: [TOC levels=3 bullet formatted]
'markdowns' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC]
  full: [TOC levels=3 bullet formatted]
'snumbered' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC]
  full: [TOC levels=3 bullet formatted]
'bbullet' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC]
  full: [TOC levels=3 bullet formatted]
'stext' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC]
  full: [TOC levels=3 bullet formatted]
'sformatted' => TocOptions[levels=<2,3>,  isHtml=false,  isTextOnly=false,  isNumbered=false,  title="Table of Contents",  titleLevel=1,  rawTitleLevel=1,  rawTitle="Table of Contents", ]
  diff: [TOC]
  full: [TOC levels=3 bullet formatted]
.
Example[0, 51]
  Style[0, 5]
    TocOptions[0, 5] status:ERROR
      Message[0, 5] message:Option htmls does not match any of: levels, bullet, numbered, text, formatted
  Style[6, 15]
    TocOptions[6, 15] status:ERROR
      Message[6, 15] message:Option markdowns does not match any of: levels, bullet, numbered, text, formatted
  Style[16, 25]
    TocOptions[16, 25] status:ERROR
      Message[16, 25] message:Option snumbered does not match any of: levels, bullet, numbered, text, formatted
  Style[26, 33]
    TocOptions[26, 33] status:ERROR
      Message[26, 33] message:Option bbullet does not match any of: levels, bullet, numbered, text, formatted
  Style[34, 39]
    TocOptions[34, 39] status:ERROR
      Message[34, 39] message:Option stext does not match any of: levels, bullet, numbered, text, formatted
  Style[40, 50]
    TocOptions[40, 50] status:ERROR
      Message[40, 50] message:Option sformatted does not match any of: levels, bullet, numbered, text, formatted
````````````````````````````````


