---
title: WikiLinks Formatter Extension Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## WikiLinks

Converts wikilink of the forms: `[[link]]`, `[[link|text]]` and `[[text|link]]` to links in the
HTML page.

simple wiki link

```````````````````````````````` example WikiLinks: 1
[[wiki link]]
.
[[wiki link]]
````````````````````````````````


simple wiki link with anchor ref

```````````````````````````````` example WikiLinks: 2
[[wiki link#anchor-ref]]
.
[[wiki link#anchor-ref]]
````````````````````````````````


simple wiki link with anchor ref

```````````````````````````````` example(WikiLinks: 3) options(allow-anchors)
[[wiki link#anchor-ref]]
.
[[wiki linkwiki link#anchor-ref]]
````````````````````````````````


simple wiki link with anchor-ref

```````````````````````````````` example(WikiLinks: 4) options(allow-anchors, allow-anchor-escape)
[[wiki link#anchor-ref]]
.
[[wiki linkwiki link#anchor-ref]]
````````````````````````````````


simple wiki link with escaped anchor-ref

```````````````````````````````` example WikiLinks: 5
[[wiki link#anchor-ref]]
[[wiki link\#anchor-ref]]
[[wiki link\\#anchor-ref]]
[[wiki link\\\#anchor-ref]]
.
[[wiki link#anchor-ref]]
[[wiki link\#anchor-ref]]
[[wiki link\\#anchor-ref]]
[[wiki link\\\#anchor-ref]]
````````````````````````````````


simple wiki link with escaped anchor-ref

```````````````````````````````` example(WikiLinks: 6) options(allow-anchors)
[[wiki link#anchor-ref]]
[[wiki link\#anchor-ref]]
[[wiki link\\#anchor-ref]]
[[wiki link\\\#anchor-ref]]
.
[[wiki linkwiki link#anchor-ref]]
[[wiki link\wiki link\#anchor-ref]]
[[wiki link\\wiki link\\#anchor-ref]]
[[wiki link\\\wiki link\\\#anchor-ref]]
````````````````````````````````


simple wiki link with escaped anchor-ref

```````````````````````````````` example(WikiLinks: 7) options(allow-anchors, allow-anchor-escape)
[[wiki link#anchor-ref]]
[[wiki link\#anchor-ref]]
[[wiki link\\#anchor-ref]]
[[wiki link\\\#anchor-ref]]
.
[[wiki linkwiki link#anchor-ref]]
[[wiki link\#anchor-ref]]
[[wiki link\\wiki link\\#anchor-ref]]
[[wiki link\\\#anchor-ref]]
````````````````````````````````


wiki link with text

```````````````````````````````` example WikiLinks: 8
[[wiki text|wiki link]]
.
[[wiki text|wiki link]]
````````````````````````````````


wiki link with text, links first option

```````````````````````````````` example(WikiLinks: 9) options(links-first)
[[wiki link|wiki text]]
.
[[wiki link|wiki text]]
````````````````````````````````


wiki link with text

```````````````````````````````` example WikiLinks: 10
[[wiki text\|wiki link]]
.
[[wiki text\|wiki link]]
````````````````````````````````


wiki link with text, links first option

```````````````````````````````` example(WikiLinks: 11) options(links-first)
[[wiki link\|wiki text]]
.
[[wiki link\|wiki text]]
````````````````````````````````


wiki link with text

```````````````````````````````` example(WikiLinks: 12) options(allow-pipe-escape)
[[wiki text\|wiki link]]
.
[[wiki text\|wiki link]]
````````````````````````````````


wiki link with text, links first option

```````````````````````````````` example(WikiLinks: 13) options(links-first, allow-pipe-escape)
[[wiki link\|wiki text]]
.
[[wiki link\|wiki text]]
````````````````````````````````


wiki link with text, muliple pipes with escapes

```````````````````````````````` example WikiLinks: 14
[[wiki text|more text|wiki link]]
[[wiki text\|more text|wiki link]]
[[wiki text\\|more text|wiki link]]
[[wiki text\\\|more text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki text\|more text\|wiki link]]
.
[[wiki text|more text|wiki link]]
[[wiki text\|more text|wiki link]]
[[wiki text\\|more text|wiki link]]
[[wiki text\\\|more text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki text\|more text\|wiki link]]
````````````````````````````````


wiki link with text, multiple pipes with escapes, links first option

```````````````````````````````` example(WikiLinks: 15) options(links-first)
[[wiki link|wiki text|more text]]
[[wiki link\|wiki text|more text]]
[[wiki text\\|more text|wiki link]]
[[wiki text\\\|more text|wiki link]]
[[wiki link|wiki text\|more text]]
[[wiki link\|wiki text\|more text]]
.
[[wiki link|wiki text|more text]]
[[wiki link\|wiki text|more text]]
[[wiki text\\|more text|wiki link]]
[[wiki text\\\|more text|wiki link]]
[[wiki link|wiki text\|more text]]
[[wiki link\|wiki text\|more text]]
````````````````````````````````


wiki link with text, muliple pipes with escapes

```````````````````````````````` example(WikiLinks: 16) options(allow-pipe-escape)
[[wiki text|more text|wiki link]]
[[wiki text\|more text|wiki link]]
[[wiki text\\|more text|wiki link]]
[[wiki text\\\|more text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki text\|more text\|wiki link]]
.
[[wiki text|more text|wiki link]]
[[wiki text\|more text|wiki link]]
[[wiki text\\|more text|wiki link]]
[[wiki text\\\|more text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki text\|more text\|wiki link]]
````````````````````````````````


wiki link with text, multiple pipes with escapes, links first option

```````````````````````````````` example(WikiLinks: 17) options(links-first, allow-pipe-escape)
[[wiki link|wiki text|more text]]
[[wiki link\|wiki text|more text]]
[[wiki text\\|more text|wiki link]]
[[wiki text\\\|more text|wiki link]]
[[wiki link|wiki text\|more text]]
[[wiki link\|wiki text\|more text]]
.
[[wiki link|wiki text|more text]]
[[wiki link\|wiki text|more text]]
[[wiki text\\|more text|wiki link]]
[[wiki text\\\|more text|wiki link]]
[[wiki link|wiki text\|more text]]
[[wiki link\|wiki text\|more text]]
````````````````````````````````


simple wiki link with inlines disabled

```````````````````````````````` example WikiLinks: 18
[[**wiki link**]]
.
[[**wiki link**]]
````````````````````````````````


simple wiki link with inlines

```````````````````````````````` example(WikiLinks: 19) options(allow-inlines)
[[**wiki link**]]
.
[[wiki link]]
````````````````````````````````


wiki link with text with inlines

```````````````````````````````` example(WikiLinks: 20) options(allow-inlines)
[[**wiki text**|wiki link]]
.
[[**wiki text**|wiki link]]
````````````````````````````````


wiki link with text with inlines split

```````````````````````````````` example(WikiLinks: 21) options(allow-inlines)
[[**wiki text|wiki** link]]
.
[[**wiki text|wiki** link]]
````````````````````````````````


wiki link with text, links first option with inlines

```````````````````````````````` example(WikiLinks: 22) options(links-first, allow-inlines)
[[wiki link|**wiki text**]]
.
[[wiki link|**wiki text**]]
````````````````````````````````


wiki link with text, links first option with inlines split

```````````````````````````````` example(WikiLinks: 23) options(links-first, allow-inlines)
[[wiki **link|wiki** text]]
.
[[wiki **link|wiki** text]]
````````````````````````````````


wiki link with text, muliple pipes with escapes

```````````````````````````````` example(WikiLinks: 24) options(allow-inlines)
[[wiki text|more text|wiki link]]
[[wiki text\|more text|wiki link]]
[[wiki **text\|more** text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki **text|more** text\|wiki link]]
[[wiki text\|more text\|wiki link]]
[[wiki **text\|more** text\|wiki link]]
.
[[wiki text|more text|wiki link]]
[[wiki text\|more text|wiki link]]
[[wiki **text\|more** text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki **text|more** text\|wiki link]]
[[wiki text\|more text\|wiki link]]
[[wiki **text\|more** text\|wiki link]]
````````````````````````````````


wiki link with text, multiple pipes with escapes, links first option

```````````````````````````````` example(WikiLinks: 25) options(links-first, allow-inlines)
[[wiki link|wiki text|more text]]
[[wiki link\|wiki text|more text]]
[[wiki **text\|more** text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki **text|more** text\|wiki link]]
[[wiki link\|wiki text\|more text]]
[[wiki **link\|wiki** text\|more text]]
.
[[wiki link|wiki text|more text]]
[[wiki link\|wiki text|more text]]
[[wiki **text\|more** text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki **text|more** text\|wiki link]]
[[wiki link\|wiki text\|more text]]
[[wiki **link\|wiki** text\|more text]]
````````````````````````````````


wiki link with text, muliple pipes with escapes

```````````````````````````````` example(WikiLinks: 26) options(allow-pipe-escape, allow-inlines)
[[wiki text|more text|wiki link]]
[[wiki text\|more text|wiki link]]
[[wiki **text\|more** text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki **text|more** text\|wiki link]]
[[wiki text\|more text\|wiki link]]
[[wiki **text\|more** text\|wiki link]]
.
[[wiki text|more text|wiki link]]
[[wiki text\|more text|wiki link]]
[[wiki **text\|more** text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki **text|more** text\|wiki link]]
[[wiki text\|more text\|wiki link]]
[[wiki text\|more text\|wiki link]]
````````````````````````````````


wiki link with text, multiple pipes with escapes, links first option

```````````````````````````````` example(WikiLinks: 27) options(links-first, allow-pipe-escape, allow-inlines)
[[wiki link|wiki text|more text]]
[[wiki link\|wiki text|more text]]
[[wiki **text\|more** text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki **text|more** text\|wiki link]]
[[wiki link\|wiki text\|more text]]
[[wiki **link\|wiki** text\|more text]]
.
[[wiki link|wiki text|more text]]
[[wiki link\|wiki text|more text]]
[[wiki **text\|more** text|wiki link]]
[[wiki text|more text\|wiki link]]
[[wiki **text|more** text\|wiki link]]
[[wiki link\|wiki text\|more text]]
[[wiki link\|wiki text\|more text]]
````````````````````````````````


simple wiki link with ! before

```````````````````````````````` example WikiLinks: 28
![[wiki link]]
.
![[wiki link]]
````````````````````````````````


wiki link with text with ! before

```````````````````````````````` example WikiLinks: 29
![[wiki text|wiki link]]
.
![[wiki text|wiki link]]
````````````````````````````````


reference following will be a reference, even if not defined

```````````````````````````````` example WikiLinks: 30
[[wiki link]][ref]
.
[[wiki link]][ref]
````````````````````````````````


reference following will be a reference

```````````````````````````````` example WikiLinks: 31
[[wiki link]][ref]

[ref]: /url
.
[[wiki link]][ref]

[ref]: /url

````````````````````````````````


dummy reference following will be an empty reference

```````````````````````````````` example WikiLinks: 32
[[wiki link]][]
.
[[wiki link]][]
````````````````````````````````


reference inside is not a wiki link but a link ref with brackets around it

```````````````````````````````` example WikiLinks: 33
[[not wiki link][ref]]
.
[[not wiki link][ref]]
````````````````````````````````


dummy reference inside is not a wiki link but a link ref with brackets around it

```````````````````````````````` example WikiLinks: 34
[[not wiki link][]]
.
[[not wiki link][]]
````````````````````````````````


```````````````````````````````` example WikiLinks: 35
[[wiki link]] [^link][ref] [[^wiki link]]
.
[[wiki link]] [^link][ref] [[^wiki link]]
````````````````````````````````


Exclamation before is just text

```````````````````````````````` example WikiLinks: 36
![[wiki link]] [^link][ref] [[^wiki link]] [[wiki]][ref]
.
![[wiki link]] [^link][ref] [[^wiki link]] [[wiki]][ref]
````````````````````````````````


Custom extension

```````````````````````````````` example(WikiLinks: 37) options(link-ext)
[[wiki link]] [^link][ref] [[^wiki link]]
.
[[wiki link]] [^link][ref] [[^wiki link]]
````````````````````````````````


Custom prefix

```````````````````````````````` example(WikiLinks: 38) options(link-prefix)
[[wiki link]] [^link][ref] [[^wiki link]]
.
[[wiki link]] [^link][ref] [[^wiki link]]
````````````````````````````````


With empty anchor ref

```````````````````````````````` example WikiLinks: 39
[[wiki link#]] 
.
[[wiki link#]]
````````````````````````````````


With empty anchor ref

```````````````````````````````` example(WikiLinks: 40) options(allow-anchors)
[[wiki link#]] 
.
[[wiki linkwiki link#]]
````````````````````````````````


With Anchor ref

```````````````````````````````` example WikiLinks: 41
[[wiki link#anchor-ref]] 
.
[[wiki link#anchor-ref]]
````````````````````````````````


With Anchor ref

```````````````````````````````` example(WikiLinks: 42) options(allow-anchors)
[[wiki link#anchor-ref]] 
.
[[wiki linkwiki link#anchor-ref]]
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example WikiLinks: 43
[[wiki text|wiki link#]] 
.
[[wiki text|wiki link#]]
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example(WikiLinks: 44) options(allow-anchors)
[[wiki text|wiki link#]] 
.
[[wiki text|wiki link#]]
````````````````````````````````


With text, anchor ref

```````````````````````````````` example WikiLinks: 45
[[wiki text|wiki link#anchor-ref]] 
.
[[wiki text|wiki link#anchor-ref]]
````````````````````````````````


With text, anchor ref

```````````````````````````````` example(WikiLinks: 46) options(allow-anchors)
[[wiki text|wiki link#anchor-ref]] 
.
[[wiki text|wiki link#anchor-ref]]
````````````````````````````````


Links first, with text, empty anchor ref

```````````````````````````````` example(WikiLinks: 47) options(links-first)
[[wiki link#|wiki text]] 
.
[[wiki link#|wiki text]]
````````````````````````````````


Links first, with text, anchor ref

```````````````````````````````` example(WikiLinks: 48) options(links-first)
[[wiki link#anchor-ref|wiki text]] 
.
[[wiki link#anchor-ref|wiki text]]
````````````````````````````````


Links first, with text, anchor ref

```````````````````````````````` example(WikiLinks: 49) options(links-first, allow-anchors)
[[wiki link#anchor-ref|wiki text]] 
.
[[wiki link#anchor-ref|wiki text]]
````````````````````````````````


Custom extension with empty Anchor ref

```````````````````````````````` example(WikiLinks: 50) options(link-ext)
[[wiki link#]] 
.
[[wiki link#]]
````````````````````````````````


Custom extension with empty Anchor ref

```````````````````````````````` example(WikiLinks: 51) options(link-ext, allow-anchors)
[[wiki link#]] 
.
[[wiki linkwiki link#]]
````````````````````````````````


Custom extension with anchor ref

```````````````````````````````` example(WikiLinks: 52) options(link-ext)
[[wiki link#anchor-ref]] 
.
[[wiki link#anchor-ref]]
````````````````````````````````


Custom extension with anchor ref

```````````````````````````````` example(WikiLinks: 53) options(link-ext, allow-anchors)
[[wiki link#anchor-ref]] 
.
[[wiki linkwiki link#anchor-ref]]
````````````````````````````````


Custom prefix with empty anchor ref

```````````````````````````````` example(WikiLinks: 54) options(link-prefix)
[[wiki link#]]
.
[[wiki link#]]
````````````````````````````````


Custom prefix with empty anchor ref

```````````````````````````````` example(WikiLinks: 55) options(link-prefix, allow-anchors)
[[wiki link#]]
.
[[wiki linkwiki link#]]
````````````````````````````````


Custom prefix with anchor ref

```````````````````````````````` example(WikiLinks: 56) options(link-prefix)
[[wiki link#anchor-ref]]
.
[[wiki link#anchor-ref]]
````````````````````````````````


Custom prefix with anchor ref

```````````````````````````````` example(WikiLinks: 57) options(link-prefix, allow-anchors)
[[wiki link#anchor-ref]]
.
[[wiki linkwiki link#anchor-ref]]
````````````````````````````````


Custom link escape

With configuration options `LINK_ESCAPE_CHARS` and `LINK_REPLACE_CHARS` it is possible to change
the link escaping. `LINK_ESCAPE_CHARS` gives the character to escape in links.
`LINK_REPLACE_CHARS` gives the correponding replacement characters.

```````````````````````````````` example(WikiLinks: 58) options(custom-link-escape)
See [[My Page]].
.
See [[My Page]].
````````````````````````````````


Links to virtual directories

With configuration options `LINK_ESCAPE_CHARS` and `LINK_REPLACE_CHARS` it is possible to create
wiki links to virtual directories (links containing the `/` character.

```````````````````````````````` example(WikiLinks: 59) options(custom-link-escape)
See [[directory/WikiPage]].
.
See [[directory/WikiPage]].
````````````````````````````````


Absolute link prefix

```````````````````````````````` example(WikiLinks: 60) options(custom-link-escape, link-prefix-absolute)
[[Page]]

[[dir/Page]]

[[/Page]]

[[/dir/Page]]
.
[[Page]]

[[dir/Page]]

[[/Page]]

[[/dir/Page]]
````````````````````````````````


Absolute image prefix

```````````````````````````````` example(WikiLinks: 61) options(wiki-images, custom-link-escape, image-prefix-absolute)
![[Img]]

![[dir/Img]]

![[/Img]]

![[/dir/Img]]
.
![[Img]]

![[dir/Img]]

![[/Img]]

![[/dir/Img]]
````````````````````````````````


### Wrapping

```````````````````````````````` example(WikiLinks - Wrapping: 1) options(margin[40])
[[Frequent Source of Problems: More than one Markdown plugin is enabled|Troubleshooting Common Problems#frequent-source-of-problems]]
.
[[Frequent Source of Problems: More than one Markdown plugin is enabled|Troubleshooting Common Problems#frequent-source-of-problems]]
````````````````````````````````


## WikiImages

Converts wiki image of the forms: `![[image]]`, `![[image|text]]` and `![[text|image]]` to image
images in the HTML page.

no spaces between brackets

simple wiki image

```````````````````````````````` example(WikiImages: 1) options(wiki-images)
![[wiki image]]
.
![[wiki image]]
````````````````````````````````


simple wiki image ignored without wiki images option

```````````````````````````````` example WikiImages: 2
![[wiki image]]
.
![[wiki image]]
````````````````````````````````


wiki image with text

```````````````````````````````` example(WikiImages: 3) options(wiki-images)
![[alt text|wiki image]]
.
![[alt text|wiki image]]
````````````````````````````````


wiki image with text, images first option

```````````````````````````````` example(WikiImages: 4) options(wiki-images, links-first)
![[wiki image|alt text]]
.
![[wiki image|alt text]]
````````````````````````````````


reference following will be a reference, even if not defined

```````````````````````````````` example(WikiImages: 5) options(wiki-images)
![[wiki image]][ref]
.
![[wiki image]][ref]
````````````````````````````````


reference following will be a reference

```````````````````````````````` example(WikiImages: 6) options(wiki-images)
![[wiki image]][ref]

[ref]: /url
.
![[wiki image]][ref]

[ref]: /url

````````````````````````````````


dummy reference following will be an empty reference

```````````````````````````````` example(WikiImages: 7) options(wiki-images)
![[wiki image]][]
.
![[wiki image]][]
````````````````````````````````


reference inside is not a wiki image but a image ref with brackets around it

```````````````````````````````` example(WikiImages: 8) options(wiki-images)
![[not wiki image][ref]]
.
![[not wiki image][ref]]
````````````````````````````````


dummy reference inside is not a wiki image but a image ref with brackets around it

```````````````````````````````` example(WikiImages: 9) options(wiki-images)
![[not wiki image][]]
.
![[not wiki image][]]
````````````````````````````````


```````````````````````````````` example(WikiImages: 10) options(wiki-images)
![[wiki image]] ![^image][ref] ![[^wiki image]]
.
![[wiki image]] ![^image][ref] ![[^wiki image]]
````````````````````````````````


Custom extension

```````````````````````````````` example(WikiImages: 11) options(wiki-images, image-ext)
[[wiki image]] [^image][ref] [[^wiki image]]
.
[[wiki image]] [^image][ref] [[^wiki image]]
````````````````````````````````


```````````````````````````````` example(WikiImages: 12) options(wiki-images, image-ext)
![[wiki image]] ![^image][ref] ![[^wiki image]]
.
![[wiki image]] ![^image][ref] ![[^wiki image]]
````````````````````````````````


Custom prefix

```````````````````````````````` example(WikiImages: 13) options(wiki-images, image-prefix)
[[wiki image]] [^image][ref] [[^wiki image]]
.
[[wiki image]] [^image][ref] [[^wiki image]]
````````````````````````````````


Custom prefix

```````````````````````````````` example(WikiImages: 14) options(wiki-images, image-prefix)
![[wiki image]] ![^image][ref] ![[^wiki image]]
.
![[wiki image]] ![^image][ref] ![[^wiki image]]
````````````````````````````````


With empty anchor ref

```````````````````````````````` example(WikiImages: 15) options(wiki-images)
![[wiki image#]] 
.
![[wiki image#]]
````````````````````````````````


With Anchor ref

```````````````````````````````` example(WikiImages: 16) options(wiki-images)
![[wiki image#anchor-ref]] 
.
![[wiki image#anchor-ref]]
````````````````````````````````


With text, empty anchor ref

```````````````````````````````` example(WikiImages: 17) options(wiki-images)
![[alt text|wiki image#]] 
.
![[alt text|wiki image#]]
````````````````````````````````


With text, anchor ref

```````````````````````````````` example(WikiImages: 18) options(wiki-images)
![[alt text|wiki image#anchor-ref]] 
.
![[alt text|wiki image#anchor-ref]]
````````````````````````````````


Links first, with text, empty anchor ref

```````````````````````````````` example(WikiImages: 19) options(wiki-images, links-first)
![[wiki image#|alt text]] 
.
![[wiki image#|alt text]]
````````````````````````````````


Links first, with text, anchor ref

```````````````````````````````` example(WikiImages: 20) options(wiki-images, links-first)
![[wiki image#anchor-ref|alt text]] 
.
![[wiki image#anchor-ref|alt text]]
````````````````````````````````


Custom extension with empty Anchor ref

```````````````````````````````` example(WikiImages: 21) options(wiki-images, image-ext)
![[wiki image#]] 
.
![[wiki image#]]
````````````````````````````````


Custom extension with anchor ref

```````````````````````````````` example(WikiImages: 22) options(wiki-images, image-ext)
![[wiki image#anchor-ref]] 
.
![[wiki image#anchor-ref]]
````````````````````````````````


Custom prefix with empty anchor ref

```````````````````````````````` example(WikiImages: 23) options(wiki-images, image-prefix)
![[wiki image#]]
.
![[wiki image#]]
````````````````````````````````


Custom prefix with anchor ref

```````````````````````````````` example(WikiImages: 24) options(wiki-images, image-prefix)
![[wiki image#anchor-ref]]
.
![[wiki image#anchor-ref]]
````````````````````````````````


### Wrapping

```````````````````````````````` example(WikiImages - Wrapping: 1) options(margin[40])
![[Frequent Source of Problems: More than one Markdown plugin is enabled|Troubleshooting Common Problems#frequent-source-of-problems]]
.
![[Frequent Source of Problems: More than one Markdown plugin is enabled|Troubleshooting Common Problems#frequent-source-of-problems]]
````````````````````````````````


