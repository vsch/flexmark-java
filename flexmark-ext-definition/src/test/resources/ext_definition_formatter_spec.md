---
title: Definition List Formatter Extension Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Definition List Extension

flexmark-java extension for definition list processing

Converts definition syntax of
[Php Markdown Extra Definition List](https://michelf.ca/projects/php-markdown/extra/#def-list)
into `<dl></dl>` html

Definition items can be preceded by `:` or `~`

Definition list is one or more definition terms with one or more definitions.

```markdown
Definition Term
: Definition of above term
: Another definition of above term
```

Definitions terms can have no definitions if they are not the last term before a definition:

```markdown
Definition Term without definition  
Definition Term  
: Definition of above term  
: Another definition of above term
```

A blank line between the definition term and definition, or the definition term and previous
definition term will create a "loose" definition by wrapping its text in `<p></p>`.

```markdown
Definition Term

: Loose Definition of above term  
: Tight definition of above term

: Another loose definition term
```

Will create:

```html
<dl>
  <dt>Definition Term</dt>
  <dd><p>Loose Definition of above term</p></dd>
  <dd>Tight definition of above term</dd>
  <dd><p>Another loose definition term</p></dd>
</dl>
```

Lazy continuation of definitions is supported and definition terms which follow a definition
must be separated from it by a blank line.

```markdown
Definition Term  
: Definition for Definition Term

Another Definition Term  
: Definition for Another Definition Term
```

Inline markdown is allowed in both terms and definitions. Definitions can contain any other
markdown elements provided these are indented as per list indentation rules.

Multiple definition terms cannot be separated by blank lines since all but the last term will be
treated as paragraph text:

```markdown
Not a definition term.

Neither is this.

Definition Term
Another Definition Term

: Definition
```

Inline markdown is allowed in both terms and definitions. Definitions can contain any other
markdown elements provided these are indented as per list indentation rules.

Paragraph block lines are split into definition terms before inline processing it is possible
that an inline markup starts on one line and finishes on another. If this paragraph is converted
to definition terms then the markup will be ignored and its opening marker attributed to one
definition term and its closing marker to the following one.

This is an example of split markdown inlines across definition terms:

```markdown
Definition **Term
Another** Definition Term
: definition `item`
```

Will result in the following HTML:

```html
<dl>
   <dt>Definition **Term</dt>
   <dt>Another** Definition Term</dt>
   <dd>definition <code>item</code></dd>
</dl>
```

---

not a definition

```````````````````````````````` example Definition List Extension: 1
: definition item 
.
: definition item
````````````````````````````````


not a definition

```````````````````````````````` example Definition List Extension: 2
- bullet item
: definition item 
.
- bullet item
  : definition item
````````````````````````````````


not a definition

```````````````````````````````` example Definition List Extension: 3
1. numbered item
: definition item 
.
1. numbered item
   : definition item
````````````````````````````````


not a definition

```````````````````````````````` example Definition List Extension: 4
## Header
: definition item 
.
## Header

: definition item
````````````````````````````````


not a definition

```````````````````````````````` example Definition List Extension: 5
```markdown
some stuff
```

: definition item 
.
```markdown
some stuff
```

: definition item
````````````````````````````````


not a definition in a lazy block quote

```````````````````````````````` example Definition List Extension: 6
> definition
: definition item 
.
> definition
> : definition item

````````````````````````````````


a definition in a block quote

```````````````````````````````` example Definition List Extension: 7
> Definition Term
> : definition item 
.
> Definition Term
> :   definition item

````````````````````````````````


simple list

```````````````````````````````` example Definition List Extension: 8
Definition Term
: definition item 
.
Definition Term
:   definition item
````````````````````````````````


A simple definition list:

```````````````````````````````` example Definition List Extension: 9
Term 1
:   Definition 1

Term 2
:   Definition 2

.
Term 1
:   Definition 1

Term 2
:   Definition 2

````````````````````````````````


With multiple terms:

```````````````````````````````` example Definition List Extension: 10
Term 1
Term 2
:   Definition 1

Term 3
Term 4
:   Definition 2

.
Term 1
Term 2
:   Definition 1

Term 3
Term 4
:   Definition 2

````````````````````````````````


With multiple definitions:

```````````````````````````````` example Definition List Extension: 11
Term 1
:   Definition 1
:   Definition 2

Term 2
:   Definition 3
:   Definition 4

.
Term 1
:   Definition 1
:   Definition 2

Term 2
:   Definition 3
:   Definition 4

````````````````````````````````


With multiple lines per definition:

```````````````````````````````` example Definition List Extension: 12
Term 1
:   Definition 1 line 1 ...
Definition 1 line 2
:   Definition 2 line 1 ...
Definition 2 line 2

Term 2
:   Definition 3 line 2 ...
    Definition 3 line 2
:   Definition 4 line 2 ...
    Definition 4 line 2

.
Term 1
:   Definition 1 line 1 ...
    Definition 1 line 2
:   Definition 2 line 1 ...
    Definition 2 line 2

Term 2
:   Definition 3 line 2 ...
    Definition 3 line 2
:   Definition 4 line 2 ...
    Definition 4 line 2

````````````````````````````````


With paragraphs:

```````````````````````````````` example Definition List Extension: 13
Term 1

:   Definition 1 (paragraph)

Term 2

:   Definition 2 (paragraph)

.
Term 1

:   Definition 1 (paragraph)

Term 2

:   Definition 2 (paragraph)

````````````````````````````````


With multiple paragraphs:

```````````````````````````````` example Definition List Extension: 14
Term 1

:   Definition 1 paragraph 1 line 1 ...
→Definition 1 paragraph 1 line 2

    Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2

Term 2

:   Definition 1 paragraph 1 line 1 ...
Definition 1 paragraph 1 line 2 (lazy)

    Definition 1 paragraph 2 line 1 ...
Definition 1 paragraph 2 line 2 (lazy)

.
Term 1

:   Definition 1 paragraph 1 line 1 ...
    Definition 1 paragraph 1 line 2

    Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2

Term 2

:   Definition 1 paragraph 1 line 1 ...
    Definition 1 paragraph 1 line 2 (lazy)

    Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2 (lazy)

````````````````````````````````


A mix:

```````````````````````````````` example Definition List Extension: 15
Term 1
Term 2

:   Definition 1 paragraph 1 line 1 ...
Definition 1 paragraph 1 line 2 (lazy)
    
    Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2

:   Definition 2 paragraph 1 line 1 ...
Definition 2 paragraph 1 line 2 (lazy)

Term 3
:   Definition 3 (no paragraph)
:   Definition 4 (no paragraph)
:   Definition 5 line 1 ...
    Definition 5 line 2 (no paragraph)

:   Definition 6 paragraph 1 line 1 ...
Definition 6 paragraph 1 line 2
:   Definition 7 (no paragraph)
:   Definition 8 paragraph 1 line 1 (forced paragraph) ...
    Definition 8 paragraph 1 line 2
    
    Definition 8 paragraph 2 line 1
    
Term 4
:   Definition 9 paragraph 1 line 1 (forced paragraph) ...
    Definition 9 paragraph 1 line 2
    
    Definition 9 paragraph 2 line 1
:   Definition 10 (no paragraph)
.
Term 1
Term 2

:   Definition 1 paragraph 1 line 1 ...
    Definition 1 paragraph 1 line 2 (lazy)

    Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2

:   Definition 2 paragraph 1 line 1 ...
    Definition 2 paragraph 1 line 2 (lazy)

Term 3
:   Definition 3 (no paragraph)
:   Definition 4 (no paragraph)
:   Definition 5 line 1 ...
    Definition 5 line 2 (no paragraph)

:   Definition 6 paragraph 1 line 1 ...
    Definition 6 paragraph 1 line 2

:   Definition 7 (no paragraph)
:   Definition 8 paragraph 1 line 1 (forced paragraph) ...
    Definition 8 paragraph 1 line 2

    Definition 8 paragraph 2 line 1

Term 4
:   Definition 9 paragraph 1 line 1 (forced paragraph) ...
    Definition 9 paragraph 1 line 2

    Definition 9 paragraph 2 line 1

:   Definition 10 (no paragraph)
````````````````````````````````


inlines allowed

```````````````````````````````` example Definition List Extension: 16
Definition **Term**
: definition `item` 
.
Definition **Term**
:   definition `item`
````````````````````````````````


inlines will be split

```````````````````````````````` example Definition List Extension: 17
Definition **Term 
Another** Definition Term 
: definition `item`
.
Definition **Term
Another** Definition Term
:   definition `item`
````````````````````````````````


don't include preceding blank lines

```````````````````````````````` example Definition List Extension: 18
- bullet item


Definition Term 
: definition item
.
- bullet item


Definition Term
:   definition item
````````````````````````````````


nested elements allowed

```````````````````````````````` example Definition List Extension: 19
Definition **Term**
: definition `item` 
    
  paragraph
    
  - bullet item
    - sub item
      
  > block quote    
  
.
Definition **Term**
:   definition `item`

    paragraph

    - bullet item
      - sub item

    > block quote

````````````````````````````````


With disparate looseness with auto-loose

```````````````````````````````` example Definition List Extension: 20
Term 1

:   Definition 1 (paragraph)

Term 2
:   Definition 2 (paragraph)

.
Term 1

:   Definition 1 (paragraph)

Term 2
:   Definition 2 (paragraph)

````````````````````````````````


With disparate looseness without auto-loose

```````````````````````````````` example Definition List Extension: 21
Term 1

:   Definition 1 (paragraph)

Term 2
:   Definition 2 (paragraph)

.
Term 1

:   Definition 1 (paragraph)

Term 2
:   Definition 2 (paragraph)

````````````````````````````````


```````````````````````````````` example Definition List Extension: 22
Definition Term
: Definition of above term
: Another definition of above term
.
Definition Term
:   Definition of above term
:   Another definition of above term
````````````````````````````````


```````````````````````````````` example Definition List Extension: 23
Definition Term
: Definition of above term
: Another definition of above term

.
Definition Term
:   Definition of above term
:   Another definition of above term

````````````````````````````````


```````````````````````````````` example Definition List Extension: 24
Term 1
Term 2

:   Definition 1 paragraph 1 line 1 ...
Definition 1 paragraph 1 line 2 (lazy)
    
    Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2

:   Definition 2 paragraph 1 line 1 ...
Definition 2 paragraph 1 line 2 (lazy)

Term 3
:   Definition 3 (no paragraph)
:   Definition 4 (no paragraph)
:   Definition 5 line 1 ...
    Definition 5 line 2 (no paragraph)

:   Definition 6 paragraph 1 line 1 ...
Definition 6 paragraph 1 line 2
:   Definition 7 (no paragraph)
:   Definition 8 paragraph 1 line 1 (forced paragraph) ...
    Definition 8 paragraph 1 line 2
    
    Definition 8 paragraph 2 line 1
    
Term 4
:   Definition 9 paragraph 1 line 1 (forced paragraph) ...
    Definition 9 paragraph 1 line 2
    
    Definition 9 paragraph 2 line 1
:   Definition 10 (no paragraph)
.
Term 1
Term 2

:   Definition 1 paragraph 1 line 1 ...
    Definition 1 paragraph 1 line 2 (lazy)

    Definition 1 paragraph 2 line 1 ...
    Definition 1 paragraph 2 line 2

:   Definition 2 paragraph 1 line 1 ...
    Definition 2 paragraph 1 line 2 (lazy)

Term 3
:   Definition 3 (no paragraph)
:   Definition 4 (no paragraph)
:   Definition 5 line 1 ...
    Definition 5 line 2 (no paragraph)

:   Definition 6 paragraph 1 line 1 ...
    Definition 6 paragraph 1 line 2

:   Definition 7 (no paragraph)
:   Definition 8 paragraph 1 line 1 (forced paragraph) ...
    Definition 8 paragraph 1 line 2

    Definition 8 paragraph 2 line 1

Term 4
:   Definition 9 paragraph 1 line 1 (forced paragraph) ...
    Definition 9 paragraph 1 line 2

    Definition 9 paragraph 2 line 1

:   Definition 10 (no paragraph)
````````````````````````````````


```````````````````````````````` example(Definition List Extension: 25) options(marker-spaces-1)
Term 1

:   Definition 1 (paragraph)

Term 2
:   Definition 2 (paragraph)

.
Term 1

: Definition 1 (paragraph)

Term 2
: Definition 2 (paragraph)

````````````````````````````````


```````````````````````````````` example(Definition List Extension: 26) options(marker-spaces-1)
Term 1

:  Definition 1 (paragraph)

   * bullet item 

   paragraph
   
   1. numbered item 

Term 2
:   Definition 2 (paragraph)
lazy continuation

    * bullet item 
 
    paragraph
    
    1. numbered item 
.
Term 1

: Definition 1 (paragraph)

  * bullet item

  paragraph

  1. numbered item

Term 2
: Definition 2 (paragraph)
  lazy continuation

  * bullet item

  paragraph

  1. numbered item
````````````````````````````````


```````````````````````````````` example(Definition List Extension: 27) options(marker-spaces-1, format-fixed-indent)
Term 1

:  Definition 1 (paragraph)

   * bullet item 

   paragraph
   
   1. numbered item 

Term 2
:   Definition 2 (paragraph)
lazy continuation

    * bullet item 
 
    paragraph
    
    1. numbered item 
.
Term 1

: Definition 1 (paragraph)

    * bullet item

    paragraph

    1. numbered item

Term 2
: Definition 2 (paragraph)
    lazy continuation

    * bullet item

    paragraph

    1. numbered item
````````````````````````````````


```````````````````````````````` example(Definition List Extension: 28) options(marker-type-tilde)
Term 1

:   Definition 1 (paragraph)

Term 2
:   Definition 2 (paragraph)

.
Term 1

~   Definition 1 (paragraph)

Term 2
~   Definition 2 (paragraph)

````````````````````````````````


```````````````````````````````` example(Definition List Extension: 29) options(marker-type-colon)
Term 1

~   Definition 1 (paragraph)

Term 2
~   Definition 2 (paragraph)

.
Term 1

:   Definition 1 (paragraph)

Term 2
:   Definition 2 (paragraph)

````````````````````````````````


