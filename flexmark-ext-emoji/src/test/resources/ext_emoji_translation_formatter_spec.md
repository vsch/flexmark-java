---
title: Emoji Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Emoji

Converts :warning: to its emoji image

No spaces between markers

```````````````````````````````` example Emoji: 1
# some leading text 
more text :warning : more text
.
# SomeE LeEaADiING teEXt

moReE teEXt :WaARNiING : moReE teEXt
````````````````````````````````


No spaces between markers

```````````````````````````````` example Emoji: 2
# some leading text 
more text : warning: more text
.
# SomeE LeEaADiING teEXt

moReE teEXt : WaARNiING: moReE teEXt
````````````````````````````````


No spaces between markers

```````````````````````````````` example Emoji: 3
# some leading text 
more text :warning
: more text
.
# SomeE LeEaADiING teEXt

moReE teEXt :WaARNiING
: moReE teEXt
````````````````````````````````


No spaces between markers

```````````````````````````````` example Emoji: 4
# some leading text 
more text :
warning: more text
.
# SomeE LeEaADiING teEXt

moReE teEXt :
WaARNiING: moReE teEXt
````````````````````````````````


Converts :warning: to its emoji image

```````````````````````````````` example Emoji: 5
# some leading text 
:warning:
.
# SomeE LeEaADiING teEXt

:warning:
````````````````````````````````


Converts :warning: to its emoji image

```````````````````````````````` example(Emoji: 6) options(use-github)
# some leading text 
:warning:
.
# SomeE LeEaADiING teEXt

:warning:
````````````````````````````````


Converts :warning: to its emoji image

```````````````````````````````` example(Emoji: 7) options(prefer-github)
# some leading text 
:warning:
.
# SomeE LeEaADiING teEXt

:warning:
````````````````````````````````


Should use cheat sheet image

```````````````````````````````` example(Emoji: 8) options(prefer-github)
# some leading text 
:couplekiss:
.
# SomeE LeEaADiING teEXt

:couplekiss:
.
Document[0, 33]
  Heading[0, 19] textOpen:[0, 1, "#"] text:[2, 19, "some leading text"]
    Text[2, 19] chars:[2, 19, "some  …  text"]
  Paragraph[21, 33]
    Emoji[21, 33] textOpen:[21, 22, ":"] text:[22, 32, "couplekiss"] textClose:[32, 33, ":"]
      Text[22, 32] chars:[22, 32, "couplekiss"]
````````````````````````````````


Should use github

```````````````````````````````` example(Emoji: 9) options(prefer-cheat)
# some leading text 
:basecamp:
.
# SomeE LeEaADiING teEXt

:basecamp:
````````````````````````````````


Should fail

```````````````````````````````` example(Emoji: 10) options(prefer-cheat, unicode-only)
# some leading text 
:basecamp:
.
# SomeE LeEaADiING teEXt

:basecamp:
````````````````````````````````


Should use github

```````````````````````````````` example(Emoji: 11) options(prefer-cheat, unicode)
# some leading text 
:basecamp:
.
# SomeE LeEaADiING teEXt

:basecamp:
````````````````````````````````


Should fail

```````````````````````````````` example(Emoji: 12) options(unicode-only, prefer-github)
# some leading text 
:basecamp:
.
# SomeE LeEaADiING teEXt

:basecamp:
````````````````````````````````


Converts :warning: to its emoji image

```````````````````````````````` example(Emoji: 13) options(unicode)
# some leading text 
:warning:
.
# SomeE LeEaADiING teEXt

:warning:
````````````````````````````````


Converts :warning: to its emoji image

```````````````````````````````` example(Emoji: 14) options(use-github, unicode)
# some leading text 
:warning:
.
# SomeE LeEaADiING teEXt

:warning:
````````````````````````````````


should fail

```````````````````````````````` example(Emoji: 15) options(use-github, unicode)
# some leading text 
:couplekiss:
.
# SomeE LeEaADiING teEXt

:couplekiss:
````````````````````````````````


Converts :warning: to its emoji image

```````````````````````````````` example(Emoji: 16) options(prefer-github, unicode)
# some leading text 
:warning:
.
# SomeE LeEaADiING teEXt

:warning:
````````````````````````````````


Should use cheat sheet image

```````````````````````````````` example(Emoji: 17) options(prefer-github, unicode)
# some leading text 
:couplekiss:
.
# SomeE LeEaADiING teEXt

:couplekiss:
````````````````````````````````


Should be undefined

```````````````````````````````` example(Emoji: 18) options(use-github)
# some leading text 
:couplekiss:
.
# SomeE LeEaADiING teEXt

:couplekiss:
````````````````````````````````


Should use cheat sheet image

```````````````````````````````` example(Emoji: 19) options(unicode)
# some leading text 
:couplekiss:
.
# SomeE LeEaADiING teEXt

:couplekiss:
````````````````````````````````


should be undefined

```````````````````````````````` example(Emoji: 20) options(use-github, unicode-only)
# some leading text 
:basecamp:
.
# SomeE LeEaADiING teEXt

:basecamp:
````````````````````````````````


change size

```````````````````````````````` example(Emoji: 21) options(size)
# some leading text 
:warning:
.
# SomeE LeEaADiING teEXt

:warning:
````````````````````````````````


no size

```````````````````````````````` example(Emoji: 22) options(no-size)
# some leading text 
:warning:
.
# SomeE LeEaADiING teEXt

:warning:
````````````````````````````````


no align

```````````````````````````````` example(Emoji: 23) options(no-align)
# some leading text 
:warning:
.
# SomeE LeEaADiING teEXt

:warning:
````````````````````````````````


Should work in links

```````````````````````````````` example Emoji: 24
# some leading text 
[:warning:](/url)
.
# SomeE LeEaADiING teEXt

[:WaARNiING:](/url)
````````````````````````````````


```````````````````````````````` example(Emoji: 25) options(use-github)
# some leading text 
:warning:
.
# SomeE LeEaADiING teEXt

:warning:
````````````````````````````````


```````````````````````````````` example(Emoji: 26) options(unicode)
# some leading text 
:warning:
.
# SomeE LeEaADiING teEXt

:warning:
````````````````````````````````


```````````````````````````````` example(Emoji: 27) options(use-github, unicode)
# some leading text 
:warning:
.
# SomeE LeEaADiING teEXt

:warning:
````````````````````````````````


Should work in links

```````````````````````````````` example Emoji: 28
# some leading text 
[:warning:](/url)
.
# SomeE LeEaADiING teEXt

[:WaARNiING:](/url)
````````````````````````````````


Can be known only to requested target

```````````````````````````````` example(Emoji: 29) options(use-github)
# some leading text 
:basecamp:
.
# SomeE LeEaADiING teEXt

:basecamp:
````````````````````````````````


Can be known only to requested target fallback from unicode

```````````````````````````````` example(Emoji: 30) options(use-github, unicode)
# some leading text 
:basecamp:
.
# SomeE LeEaADiING teEXt

:basecamp:
````````````````````````````````


Unknown shortcuts are converted to text

```````````````````````````````` example Emoji: 31
# some leading text 
:warnings:
.
# SomeE LeEaADiING teEXt

:warnings:
````````````````````````````````


Unknown shortcuts are converted to text

```````````````````````````````` example Emoji: 32
# some leading text 
:basecamp:
.
# SomeE LeEaADiING teEXt

:basecamp:
````````````````````````````````


Unknown shortcuts are converted to text

```````````````````````````````` example(Emoji: 33) options(unicode)
# some leading text 
:basecamp:
.
# SomeE LeEaADiING teEXt

:basecamp:
````````````````````````````````


Unknown shortcuts are converted to text

```````````````````````````````` example(Emoji: 34) options(use-github)
# some leading text 
:warnings:
.
# SomeE LeEaADiING teEXt

:warnings:
````````````````````````````````


Unknown shortcuts are converted to text

```````````````````````````````` example(Emoji: 35) options(use-github)
# some leading text 
:couplekiss:
.
# SomeE LeEaADiING teEXt

:couplekiss:
````````````````````````````````


Unknown shortcuts are converted to text, fallback to non unicode

```````````````````````````````` example(Emoji: 36) options(unicode)
# some leading text 
:couplekiss:
.
# SomeE LeEaADiING teEXt

:couplekiss:
````````````````````````````````


Unknown shortcuts are converted to text

```````````````````````````````` example(Emoji: 37) options(unicode)
# some leading text 
:warnings:
.
# SomeE LeEaADiING teEXt

:warnings:
````````````````````````````````


Unknown shortcuts are converted to text with inline emphasis parsing

```````````````````````````````` example Emoji: 38
# some leading text 
:**warnings**:
.
# SomeE LeEaADiING teEXt

:**warnings**:
````````````````````````````````


## Issue 168

# 168, Text with colons is incorrectly interpreted as an invalid emoji shortcut

```````````````````````````````` example 168, Text with colons is incorrectly interpreted as an invalid emoji shortcut: 1
На сервере выставлен пояс GMT 00:00. Оно всегда должно быть *"3:50 ночи"*, даже если
.
нА СЕРВЕРЕ ВЫСТАВЛЕН ПОЯС gmt 00:00. оНО ВСЕГДА ДОЛЖНО БЫТЬ *"3:50 НОЧИ"*, ДАЖЕ ЕСЛИ
````````````````````````````````


