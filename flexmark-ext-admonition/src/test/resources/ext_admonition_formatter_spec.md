---
title: Admonition Extension Formatter Spec
author: Vladimir Schneider
version: 0.1
date: '2018-03-05'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Admonition Extension

### Basic Tests

```````````````````````````````` example Admonition Extension - Basic Tests: 1
!!! note
    first child paragraph  
.
!!! note
    first child paragraph

````````````````````````````````


with title

```````````````````````````````` example Admonition Extension - Basic Tests: 2
!!! note "Title"
    first child paragraph  
.
!!! note "Title"
    first child paragraph

````````````````````````````````


no heading

```````````````````````````````` example Admonition Extension - Basic Tests: 3
!!! note ""
    first child paragraph  
.
!!! note ""
    first child paragraph

````````````````````````````````


collapsed

```````````````````````````````` example Admonition Extension - Basic Tests: 4
??? note
    first child paragraph  
.
??? note
    first child paragraph

````````````````````````````````


collapsed with title

```````````````````````````````` example Admonition Extension - Basic Tests: 5
??? note "Title"
    first child paragraph  
.
??? note "Title"
    first child paragraph

````````````````````````````````


collapsed with empty title

```````````````````````````````` example Admonition Extension - Basic Tests: 6
??? note ""
    first child paragraph  
.
??? note ""
    first child paragraph

````````````````````````````````


open

```````````````````````````````` example Admonition Extension - Basic Tests: 7
???+ note
    first child paragraph  
.
???+ note
    first child paragraph

````````````````````````````````


open with title

```````````````````````````````` example Admonition Extension - Basic Tests: 8
???+ note "Title"
    first child paragraph  
.
???+ note "Title"
    first child paragraph

````````````````````````````````


open with empty title

```````````````````````````````` example Admonition Extension - Basic Tests: 9
???+ note ""
    first child paragraph  
.
???+ note ""
    first child paragraph

````````````````````````````````


indented 1

```````````````````````````````` example Admonition Extension - Basic Tests: 10
 ???+ note ""
    first child paragraph  
    
.
???+ note ""
    first child paragraph

````````````````````````````````


indented 2

```````````````````````````````` example Admonition Extension - Basic Tests: 11
  ???+ note ""
    first child paragraph  
    
.
???+ note ""
    first child paragraph

````````````````````````````````


indented 3

```````````````````````````````` example Admonition Extension - Basic Tests: 12
   ???+ note ""
    first child paragraph  
    
.
???+ note ""
    first child paragraph

````````````````````````````````


indented 4

```````````````````````````````` example Admonition Extension - Basic Tests: 13
    ???+ note ""
        first child paragraph  
    
.
    ???+ note ""
        first child paragraph  

````````````````````````````````


nested

```````````````````````````````` example Admonition Extension - Basic Tests: 14
???+ note ""
    first child paragraph  
    
    !!! note
        embedded
.
???+ note ""
    first child paragraph

    !!! note
        embedded

````````````````````````````````


with multiple children

```````````````````````````````` example Admonition Extension - Basic Tests: 15
!!! note "Title" 

    ## Heading 2
    
    * bullet list item 1
    * bullet list item 2
    * bullet list item 3
    
    
    | Table Heading  |
    |---|
    | Table Data  |

.
!!! note "Title"

    ## Heading 2

    * bullet list item 1
    * bullet list item 2
    * bullet list item 3


    | Table Heading  |
    |---|
    | Table Data  |

````````````````````````````````


collapsed with multiple children

```````````````````````````````` example Admonition Extension - Basic Tests: 16
??? example 

    ## Heading 2
    
    * bullet list item 1
    * bullet list item 2
    * bullet list item 3
    
    
    | Table Heading  |
    |---|
    | Table Data  |

.
??? example

    ## Heading 2

    * bullet list item 1
    * bullet list item 2
    * bullet list item 3


    | Table Heading  |
    |---|
    | Table Data  |

````````````````````````````````


with lazy continuation of first paragraph

```````````````````````````````` example Admonition Extension - Basic Tests: 17
!!! note
lazy continuation 
.
!!! note
    lazy continuation

````````````````````````````````


