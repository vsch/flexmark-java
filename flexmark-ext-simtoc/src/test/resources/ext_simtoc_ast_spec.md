---
title: SimToc Extension Spec
author: 
version: 
date: '2016-06-30'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

```java
class dummy {
    String test = "SimTocContents";
}
```

## SimToc  

The Sim TOC tag has the following format: `[TOC style]: # "Title"` and includes all following
lines until a blank line.

Lines after the TOC tag are added to the `SimTocContent` child node of the SimToc block.

The intention for this tag is to have the `SimTocContent` updated to reflect the content of the
document.

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

2. `"Title"` specifies the text for the table of contents heading. If omitted or blank then no
   heading will be generated for the table of contents. `#` prefix in the title will specify the
   header level to use for the heading above the table of contents listing. If no `#` prefix is
   used then the heading level will be taken from project settings.

no spaces after first bracket

```````````````````````````````` example SimToc: 1
[ TOC]: #  
.
.
Document[0, 12]
  Reference[0, 9] refOpen:[0, 1, "["] ref:[2, 5, "TOC"] refClose:[5, 7, "]:"] urlOpen:[0, 0] url:[8, 9, "#"] urlClose:[0, 0] titleOpen:[0, 0] title:[0, 0] titleClose:[0, 0]
````````````````````````````````


plain is ok

```````````````````````````````` example SimToc: 2
[TOC]: #  
.
.
Document[0, 11]
  SimTocBlock[0, 11] openingMarker:[0, 4] tocKeyword:[0, 0] style:[0, 0] closingMarker:[4, 6] anchorMarker:[7, 8] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
````````````````````````````````


accepts all text for style

```````````````````````````````` example SimToc: 3
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
.
.
Document[0, 71]
  SimTocBlock[0, 71] openingMarker:[0, 4] tocKeyword:[0, 0] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
````````````````````````````````


Absorbs all up to a blank line

```````````````````````````````` example SimToc: 4
[TOC levels=a,b,c,d html markdown text formatted bullet numbered]: #  
# Heading 1
## Heading 1.1
- afdasfdsadf
- asfdasfd
    - asfdasfd
    - asfdasfd
    - asfdasfd
- asfdasfd
- asfdasfd
- asfdasfd

- this is a new list
.
<ul>
  <li>this is a new list</li>
</ul>
.
Document[0, 223]
  SimTocBlock[0, 201] openingMarker:[0, 4] tocKeyword:[0, 0] style:[5, 64] closingMarker:[64, 66] anchorMarker:[67, 68] openingTitleMarker:[0, 0] title:[0, 0] closingTitleMarker:[0, 0]
    SimTocContent[71, 201]
  BulletList[202, 223] isTight=true
    BulletListItem[202, 223] open:[202, 203, "-"]
      Paragraph[204, 223]
        Text[204, 222] chars:[204, 222, "this "..." list"]
````````````````````````````````


