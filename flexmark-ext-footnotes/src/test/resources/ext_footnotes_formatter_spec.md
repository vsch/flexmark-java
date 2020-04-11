---
title: Footnotes Extension Formatter Spec
author: Vladimir Schneider
version: 0.1
date: '2017-01-27'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Footnotes

Converts footnote references and definitions to footnotes in the HTML page. A footnote is a link
reference starting with a ^ with the definition being the same: `[^ref]: footnote`.

A footnote definition is a container block that can contain any element as long as it is
indented by 4 spaces or a tab from the indent level of the footnote.

basic

```````````````````````````````` example Footnotes: 1
text [^footnote] embedded.

[^footnote]: footnote text
with continuation

.
text [^footnote] embedded.

[^footnote]: footnote text
    with continuation

````````````````````````````````


undefined

```````````````````````````````` example Footnotes: 2
text [^undefined] embedded.

[^footnote]: footnote text
with continuation


.
text [^undefined] embedded.

[^footnote]: footnote text
    with continuation

````````````````````````````````


duplicated

```````````````````````````````` example Footnotes: 3
text [^footnote] embedded.

[^footnote]: footnote text
with continuation

[^footnote]: duplicated footnote text
with continuation

.
text [^footnote] embedded.

[^footnote]: footnote text
    with continuation

[^footnote]: duplicated footnote text
    with continuation

````````````````````````````````


nested

```````````````````````````````` example Footnotes: 4
text [^footnote] embedded.

[^footnote]: footnote text with [^another] embedded footnote
with continuation

[^another]: footnote text
with continuation

.
text [^footnote] embedded.

[^footnote]: footnote text with [^another] embedded footnote
    with continuation

[^another]: footnote text
    with continuation

````````````````````````````````


circular

```````````````````````````````` example Footnotes: 5
text [^footnote] embedded.

[^footnote]: footnote text with [^another] embedded footnote
with continuation

[^another]: footnote text with [^another] embedded footnote
with continuation

.
text [^footnote] embedded.

[^footnote]: footnote text with [^another] embedded footnote
    with continuation

[^another]: footnote text with [^another] embedded footnote
    with continuation

````````````````````````````````


compound

```````````````````````````````` example Footnotes: 6
This paragraph has a footnote[^footnote].

[^footnote]: This is the body of the footnote.
with continuation text. Inline _italic_ and 
**bold**.

    Multiple paragraphs are supported as other 
    markdown elements such as lists.
    
    - item 1
    - item 2
.
This paragraph has a footnote[^footnote].

[^footnote]: This is the body of the footnote.
    with continuation text. Inline _italic_ and
    **bold**.

    Multiple paragraphs are supported as other
    markdown elements such as lists.

    - item 1
    - item 2

````````````````````````````````


Not a footnote nor a footnote definition if space between [ and ^.

```````````````````````````````` example Footnotes: 7
This paragraph has no footnote[ ^footnote].

[ ^footnote]: This is the body of the footnote.
with continuation text. Inline _italic_ and 
**bold**.

    Multiple paragraphs are supported as other 
    markdown elements such as lists.
    
    - item 1
    - item 2
.
This paragraph has no footnote[ ^footnote].

[ ^footnote]: This is the body of the footnote.
with continuation text. Inline _italic_ and
**bold**.

    Multiple paragraphs are supported as other 
    markdown elements such as lists.

    - item 1
    - item 2

````````````````````````````````


Unused footnotes are not used and do not show up on the page.

```````````````````````````````` example Footnotes: 8
This paragraph has a footnote[^2].

[^1]: This is the body of the unused footnote.
with continuation text. Inline _italic_ and 
**bold**.

[^2]: This is the body of the footnote.
with continuation text. Inline _italic_ and 
**bold**.

    Multiple paragraphs are supported as other 
    markdown elements such as lists.
    
    - item 1
    - item 2
.
This paragraph has a footnote[^2].

[^1]: This is the body of the unused footnote.
    with continuation text. Inline _italic_ and
    **bold**.

[^2]: This is the body of the footnote.
    with continuation text. Inline _italic_ and
    **bold**.

    Multiple paragraphs are supported as other
    markdown elements such as lists.

    - item 1
    - item 2

````````````````````````````````


Undefined footnotes are rendered as if they were text, with emphasis left as is.

```````````````````````````````` example Footnotes: 9
This paragraph has a footnote[^**footnote**].

[^footnote]: This is the body of the footnote.
with continuation text. Inline _italic_ and 
**bold**.

    Multiple paragraphs are supported as other 
    markdown elements such as lists.
    
    - item 1
    - item 2
.
This paragraph has a footnote[^**footnote**].

[^footnote]: This is the body of the footnote.
    with continuation text. Inline _italic_ and
    **bold**.

    Multiple paragraphs are supported as other
    markdown elements such as lists.

    - item 1
    - item 2

````````````````````````````````


Footnote numbers are assigned in order of their reference in the document

```````````````````````````````` example Footnotes: 10
This paragraph has a footnote[^2]. Followed by another[^1]. 

[^1]: This is the body of the unused footnote.
with continuation text. Inline _italic_ and 
**bold**.

[^2]: This is the body of the footnote.
with continuation text. Inline _italic_ and 
**bold**.

    Multiple paragraphs are supported as other 
    markdown elements such as lists.
    
    - item 1
    - item 2
.
This paragraph has a footnote[^2]. Followed by another[^1].

[^1]: This is the body of the unused footnote.
    with continuation text. Inline _italic_ and
    **bold**.

[^2]: This is the body of the footnote.
    with continuation text. Inline _italic_ and
    **bold**.

    Multiple paragraphs are supported as other
    markdown elements such as lists.

    - item 1
    - item 2

````````````````````````````````


Footnotes can contain references to other footnotes.

```````````````````````````````` example Footnotes: 11
This paragraph has a footnote[^2].  

[^2]: This is the body of the footnote.
with continuation text. Inline _italic_ and 
**bold**.

    Multiple paragraphs are supported as other 
    markdown elements such as lists and footnotes[^1].
    
    - item 1
    - item 2
    
    [^1]: This is the body of a nested footnote.
    with continuation text. Inline _italic_ and 
    **bold**.
.
This paragraph has a footnote[^2].

[^2]: This is the body of the footnote.
    with continuation text. Inline _italic_ and
    **bold**.

    Multiple paragraphs are supported as other
    markdown elements such as lists and footnotes[^1].

    - item 1
    - item 2

    [^1]: This is the body of a nested footnote.
        with continuation text. Inline _italic_ and
        **bold**.

````````````````````````````````


Customized strings

```````````````````````````````` example Footnotes: 12
This paragraph has a footnote[^2].  

[^2]: This is the body of the footnote.
with continuation text. Inline _italic_ and 
**bold**.

    Multiple paragraphs are supported as other 
    markdown elements such as lists and footnotes[^1].
    
    - item 1
    - item 2
    
    [^1]: This is the body of a nested footnote.
    with continuation text. Inline _italic_ and 
    **bold**.
.
This paragraph has a footnote[^2].

[^2]: This is the body of the footnote.
    with continuation text. Inline _italic_ and
    **bold**.

    Multiple paragraphs are supported as other
    markdown elements such as lists and footnotes[^1].

    - item 1
    - item 2

    [^1]: This is the body of a nested footnote.
        with continuation text. Inline _italic_ and
        **bold**.

````````````````````````````````


Customized link ref class

```````````````````````````````` example Footnotes: 13
This paragraph has a footnote[^2].  

[^2]: This is the body of the footnote.
.
This paragraph has a footnote[^2].

[^2]: This is the body of the footnote.

````````````````````````````````


Customized link ref class

```````````````````````````````` example Footnotes: 14
This paragraph has a footnote[^2].  

[^2]: This is the body of the footnote.
.
This paragraph has a footnote[^2].

[^2]: This is the body of the footnote.

````````````````````````````````


Parser emulation family indent handling is ignored. Otherwise the indent can be huge.

```````````````````````````````` example Footnotes: 15
This paragraph has a footnote[^2].  

[^2]: This is the body of the footnote.

      Another paragraph of the footnote
      
    Also a paragraph of the footnote
    
        indented code of the footnote
.
This paragraph has a footnote[^2].

[^2]: This is the body of the footnote.

    Another paragraph of the footnote

    Also a paragraph of the footnote

        indented code of the footnote

````````````````````````````````


Table formatting

```````````````````````````````` example Footnotes: 16
Paragraph text[^1]

[^1]: Footnote text that will be wrapped and should have a hanging indent to align the overflow
      to the level of the text after the footnote anchor at the left margin.

    * list as part of footnote
    * another item
      
    | table |
    |-------|
    | data  |
.
Paragraph text[^1]

[^1]: Footnote text that will be wrapped and should have a hanging indent to align the overflow
    to the level of the text after the footnote anchor at the left margin.

    * list as part of footnote
    * another item

    | table |
    |-------|
    | data  |

````````````````````````````````


```````````````````````````````` example Footnotes: 17
Paragraph text[^1]

[^1]: Footnote text that will be wrapped and should have a hanging indent to align the overflow
      to the level of the text after the footnote anchor at the left margin.

      * list as part of footnote
      * another item
        
      | table |
      |-------|
      | data  |
.
Paragraph text[^1]

[^1]: Footnote text that will be wrapped and should have a hanging indent to align the overflow
    to the level of the text after the footnote anchor at the left margin.

    * list as part of footnote
    * another item

    | table |
    |-------|
    | data  |

````````````````````````````````


List item indent is used

```````````````````````````````` example Footnotes: 18
This paragraph has a footnote[^2].  

[^2]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
.
This paragraph has a footnote[^2].

[^2]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

Not a paragraph of the footnote
````````````````````````````````


## Placement Options

```````````````````````````````` example Placement Options: 1
This paragraph has a footnote[^1].  

[^2]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
       Not a paragraph of the footnote
       
This paragraph has a footnote[^1].  

[^3]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
       Not a paragraph of the footnote
       
This paragraph has a footnote[^3].  

[^1]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
       Not a paragraph of the footnote
.
This paragraph has a footnote[^1].

[^2]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

    Not a paragraph of the footnote

This paragraph has a footnote[^1].

[^3]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

    Not a paragraph of the footnote

This paragraph has a footnote[^3].

[^1]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

    Not a paragraph of the footnote

````````````````````````````````


```````````````````````````````` example(Placement Options: 2) options(references-document-top)
This paragraph has a footnote[^1].  

[^2]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
       
This paragraph has a footnote[^1].  

[^3]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
       
This paragraph has a footnote[^3].  

[^1]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
.
[^2]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

[^3]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

[^1]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

This paragraph has a footnote[^1].

Not a paragraph of the footnote

This paragraph has a footnote[^1].

Not a paragraph of the footnote

This paragraph has a footnote[^3].

Not a paragraph of the footnote
````````````````````````````````


```````````````````````````````` example(Placement Options: 3) options(references-document-bottom)
This paragraph has a footnote[^1].  

[^2]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
       
This paragraph has a footnote[^1].  

[^3]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
       
This paragraph has a footnote[^3].  

[^1]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
.
This paragraph has a footnote[^1].

Not a paragraph of the footnote

This paragraph has a footnote[^1].

Not a paragraph of the footnote

This paragraph has a footnote[^3].

Not a paragraph of the footnote

[^2]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

[^3]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

[^1]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

````````````````````````````````


```````````````````````````````` example(Placement Options: 4) options(references-group-with-first)
This paragraph has a footnote[^1].  

[^2]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
       
This paragraph has a footnote[^1].  

[^3]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
       
This paragraph has a footnote[^3].  

[^1]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
.
This paragraph has a footnote[^1].

[^2]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

[^3]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

[^1]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

Not a paragraph of the footnote

This paragraph has a footnote[^1].

Not a paragraph of the footnote

This paragraph has a footnote[^3].

Not a paragraph of the footnote
````````````````````````````````


```````````````````````````````` example(Placement Options: 5) options(references-group-with-last)
This paragraph has a footnote[^1].  

[^2]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
       
This paragraph has a footnote[^1].  

[^3]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
       
This paragraph has a footnote[^3].  

[^1]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
.
This paragraph has a footnote[^1].

Not a paragraph of the footnote

This paragraph has a footnote[^1].

Not a paragraph of the footnote

This paragraph has a footnote[^3].

[^2]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

[^3]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

[^1]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

Not a paragraph of the footnote
````````````````````````````````


```````````````````````````````` example(Placement Options: 6) options(references-document-bottom, references-sort)
This paragraph has a footnote[^1].  

[^2]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
       
This paragraph has a footnote[^1].  

[^3]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
       
This paragraph has a footnote[^3].  

[^1]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
.
This paragraph has a footnote[^1].

Not a paragraph of the footnote

This paragraph has a footnote[^1].

Not a paragraph of the footnote

This paragraph has a footnote[^3].

Not a paragraph of the footnote

[^1]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

[^2]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

[^3]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

````````````````````````````````


```````````````````````````````` example(Placement Options: 7) options(references-document-bottom, references-sort-unused-last)
This paragraph has a footnote[^1].  

[^2]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
       
This paragraph has a footnote[^1].  

[^3]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
       
This paragraph has a footnote[^3].  

[^1]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
.
This paragraph has a footnote[^1].

Not a paragraph of the footnote

This paragraph has a footnote[^1].

Not a paragraph of the footnote

This paragraph has a footnote[^3].

Not a paragraph of the footnote

[^1]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

[^3]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

[^2]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

````````````````````````````````


```````````````````````````````` example(Placement Options: 8) options(references-document-bottom, references-sort-delete-unused)
This paragraph has a footnote[^1].  

[^2]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
       
This paragraph has a footnote[^1].  

[^3]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
       
This paragraph has a footnote[^3].  

[^1]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
.
This paragraph has a footnote[^1].

Not a paragraph of the footnote

This paragraph has a footnote[^1].

Not a paragraph of the footnote

This paragraph has a footnote[^3].

Not a paragraph of the footnote

[^1]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

[^3]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

````````````````````````````````


```````````````````````````````` example(Placement Options: 9) options(references-document-bottom, references-delete-unused)
This paragraph has a footnote[^1].  

[^2]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
       
This paragraph has a footnote[^1].  

[^3]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
       
This paragraph has a footnote[^3].  

[^1]: This is the body of the footnote.

        Another paragraph of the footnote
    
            indented code of the footnote
      
Not a paragraph of the footnote
.
This paragraph has a footnote[^1].

Not a paragraph of the footnote

This paragraph has a footnote[^1].

Not a paragraph of the footnote

This paragraph has a footnote[^3].

Not a paragraph of the footnote

[^3]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

[^1]: This is the body of the footnote.

        Another paragraph of the footnote

            indented code of the footnote

````````````````````````````````


## Issue 244

Issue #244

```````````````````````````````` example Issue 244: 1
Duplicated footnote reference[^id].

reference[^id]

[^id]: Footnote text.
.
Duplicated footnote reference[^id].

reference[^id]

[^id]: Footnote text.

.
Document[0, 74]
  Paragraph[0, 36] isTrailingBlankLine
    Text[0, 29] chars:[0, 29, "Dupli … rence"]
    Footnote[29, 34] ordinal: 0  textOpen:[29, 31, "[^"] text:[31, 33, "id"] textClose:[33, 34, "]"]
      Text[31, 33] chars:[31, 33, "id"]
    Text[34, 35] chars:[34, 35, "."]
  BlankLine[36, 37]
  Paragraph[37, 52] isTrailingBlankLine
    Text[37, 46] chars:[37, 46, "reference"]
    Footnote[46, 51] ordinal: 0  textOpen:[46, 48, "[^"] text:[48, 50, "id"] textClose:[50, 51, "]"]
      Text[48, 50] chars:[48, 50, "id"]
  BlankLine[52, 53]
  FootnoteBlock[53, 74] ordinal: 0  open:[53, 55] text:[55, 57] close:[57, 59] footnote:[60, 74]
    Paragraph[60, 74]
      Text[60, 74] chars:[60, 74, "Footn … text."]
````````````````````````````````


