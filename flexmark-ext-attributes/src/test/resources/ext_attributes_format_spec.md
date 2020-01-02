---
title: Attributes Extension
author:
version:
date: '2017-12-20'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Attributes

Converts attributes `{...}` syntax into attributes AST nodes and adds an attribute provider to
set attributes for preceding node based on attribute assignment rules.

The attributes is a space separated list of attribute syntax of one of the following:

* `attr=value`
* `attr='value'`
* `attr="value"`
* `#anchor` : equivalent to `id="anchor"`
* `.class-name` : equivalent to `class="class-name"`

<!--
* `:attr=value` : equivalent to `style="attr: value"`
-->

**NOTE**: Handling of multiple value assignment for attributes depends on its name:

* `class` values are accumulated as a space (` `) separated list.
* `style` values are accumulated as a semicolon (`;`) separated list.
* all others override any previous values of the same name.

The goal for this extension is to give maximum flexibility of assigning attributes to any
element in the markdown hierarchy in an intuitive manner so the target of the assignment can be
determined at a glance.

The attributes are used with a postfix notation, where they define the attributes for preceding
element in the document.

The following terms are used in the specification of rules for determining the attribute owner:

previous sibling : a markdown element preceding the element within its parent element's child
list

no previous sibling : a markdown element which is first within its parent element's child list

next sibling : a markdown element following the element within its parent element's child list

no next sibling : a markdown element which is last within its parent element's child list

paragraph item container : a markdown element which is an item in a parent list element. In
which case the first child paragraph is considered the item's text container and not a regular
paragraph. eg. `ListItem`, `DefinitionItem`, `Footnote`

text element : markdown element representing a contiguous span of undecorated plain text
uninterrupted by any other markdown element.

anchor target node : any node which can contain an `id` or `name` attribute and be a target of
an anchor reference in a link and naturally will compute its own `id` absent one being
explicitly assigned. eg. `Heading`

attached attributes attributes are attached : attributes element without intervening white space
between it and its previous sibling is said to be attached to its previous sibling.

unattached attributes attributes are not attached attributes are unattached : attributes element
with intervening white space between it and its previous sibling is said to be unattached.

There are two modes of attribute assignment determination for text nodes:

## Text Node Previous Sibling

Assignment of attributes to text elements is determined by the `boolean` extension option
`ASSIGN_TEXT_ATTRIBUTES`, by default `true`.

For the rest of the specification the `options` following the example will contain:
`no-text-attributes` if `ASSIGN_TEXT_ATTRIBUTES` is `false`, and `true` otherwise.

* if text assignment is false
  * Cond 1.1 attributes are assigned to the parent of attributes element.
* else
  * if attributes are attached to the previous sibling
    * Cond 1.2 attributes are assigned to the text element
  * else
    * Cond 1.3 attributes are assigned to the text element

attributes assigned to paragraph

```````````````````````````````` example(Text Node Previous Sibling: 1) options(no-text-attributes)
Cond 1.1 text node{.red}

Cond 1.1 text node {.red}
.
Cond 1.1 text node{.red}

Cond 1.1 text node {.red}
````````````````````````````````


```````````````````````````````` example Text Node Previous Sibling: 2
Cond 1.2 text node{.red}

Cond 1.3 text node {.red}
.
Cond 1.2 text node{.red}

Cond 1.3 text node {.red}
````````````````````````````````


can delimit with comments

```````````````````````````````` example Text Node Previous Sibling: 3
Cond 1.2 text <!---->node{.red}

Cond 1.3 text <!---->node {.red}
.
Cond 1.2 text <!---->node{.red}

Cond 1.3 text <!---->node {.red}
````````````````````````````````


```````````````````````````````` example(Text Node Previous Sibling: 4) options(no-text-attributes)
Cond 1.2 **text node{.red}**

Cond 1.3 **text node {.red}**
.
Cond 1.2 **text node{.red}**

Cond 1.3 **text node {.red}**
````````````````````````````````


```````````````````````````````` example Text Node Previous Sibling: 5
Cond 1.2 **text node{.red}**

Cond 1.3 **text node {.red}**
.
Cond 1.2 **text node{.red}**

Cond 1.3 **text node {.red}**
````````````````````````````````


can delimit with comments

```````````````````````````````` example Text Node Previous Sibling: 6
Cond 1.2 **text <!---->node{.red}**

Cond 1.3 **text <!---->node {.red}**
.
Cond 1.2 **text <!---->node{.red}**

Cond 1.3 **text <!---->node {.red}**
````````````````````````````````


```````````````````````````````` example(Text Node Previous Sibling: 7) options(no-text-attributes)
> Cond 1.1 text node{.red}

> Cond 1.1 text node {.red}
.
> Cond 1.1 text node{.red}

> Cond 1.1 text node {.red}

````````````````````````````````


attributes assigned to paragraph

```````````````````````````````` example(Text Node Previous Sibling: 8) options(no-text-attributes)
> Cond 1.2 text node{.red}

> Cond 1.3 text node {.red}
.
> Cond 1.2 text node{.red}

> Cond 1.3 text node {.red}

````````````````````````````````


attributes assigned to list item

```````````````````````````````` example(Text Node Previous Sibling: 9) options(no-text-attributes)
* Cond 1.1 text node{.red}

* Cond 1.1 text node {.red}
.
* Cond 1.1 text node{.red}

* Cond 1.1 text node {.red}

````````````````````````````````


attributes assigned to list item

```````````````````````````````` example Text Node Previous Sibling: 10
* Cond 1.2 text node{.red}

* Cond 1.3 text node {.red}
.
* Cond 1.2 text node{.red}

* Cond 1.3 text node {.red}

````````````````````````````````


## Non Text Node Previous Sibling

* if not attached to previous sibling
  * Cond 2.1 attributes are assigned to the parent element
* else
  * if previous sibling is an attributes element
    * Cond 2.2 attributes are assigned to the same element as previous sibling attributes
  * else
    * Cond 2.3 attributes are assigned to the previous sibling

```````````````````````````````` example(Non Text Node Previous Sibling: 1) options(no-text-attributes)
Cond 2.1 Some text **bold text** {.red}

Cond 2.2 Some text **bold text**{.red}{.blue}

Cond 2.3 Some text **bold text**{.red}
.
Cond 2.1 Some text **bold text** {.red}

Cond 2.2 Some text **bold text**{.red}{.blue}

Cond 2.3 Some text **bold text**{.red}
````````````````````````````````


```````````````````````````````` example Non Text Node Previous Sibling: 2
Cond 2.1 Some text **bold text** {.red}

Cond 2.2 Some text **bold text**{.red}{.blue}

Cond 2.3 Some text **bold text**{.red}
.
Cond 2.1 Some text **bold text** {.red}

Cond 2.2 Some text **bold text**{.red}{.blue}

Cond 2.3 Some text **bold text**{.red}
````````````````````````````````


## No Previous Sibling

* if parent is a paragraph
  * if paragraph parent is a paragraph item container
    * if paragraph has no previous sibling then
      * Cond 3.1 attributes go to the paragraph parent's parent
    * else
      * if paragraph only contains attributes then
        * Cond 3.2 attributes go to paragraph's previous sibling,
      * else
        * Cond 3.3 attributes go to the paragraph
  * else
    * if paragraph only contains attributes then
      * if paragraph has no previous sibling then
        * Cond 3.4 attributes go to paragraph's parent
      * else
        * Cond 3.5 attributes go to paragraph's previous sibling,
    * else
      * Cond 3.6 attributes go to the paragraph
* else
  * Cond 3.7 attributes go to the parent

### Cond 3.1

Cond 3.1 attributes go to the paragraph parent's parent

attributes are assigned to the list element

```````````````````````````````` example No Previous Sibling - Cond 3.1: 1
* {.red} list item 1
* list item 2

* list item 1
* {.red} list item 2
.
* {.red} list item 1
* list item 2

* list item 1
* {.red} list item 2

````````````````````````````````


attributes are assigned to the definition list

```````````````````````````````` example No Previous Sibling - Cond 3.1: 2
Definition Term
:   {.red} definition item 1
:   definition item 2

Definition Term
:   definition item 1
:   {.red} definition item 2
.
Definition Term
:   {.red} definition item 1
:   definition item 2

Definition Term
:   definition item 1
:   {.red} definition item 2
````````````````````````````````


### Cond 3.2

Cond 3.2 attributes go to paragraph's previous sibling,

Assigned to paragraph of list item 2

```````````````````````````````` example No Previous Sibling - Cond 3.2: 1
* list item 1
* list item 2

  {.red}
.
* list item 1
* list item 2

  {.red}

````````````````````````````````


Assigned to paragraph of list item 2

```````````````````````````````` example No Previous Sibling - Cond 3.2: 2
* list item 1

* list item 2

  {.red}
.
* list item 1

* list item 2

  {.red}

````````````````````````````````


Assigned to definition item 2 paragraph

```````````````````````````````` example No Previous Sibling - Cond 3.2: 3
definition term
: definition item 1
: definition item 2

  {.red}

.
definition term
: definition item 1
: definition item 2

{.red}

````````````````````````````````


Assigned to definition item 2 paragraph

```````````````````````````````` example No Previous Sibling - Cond 3.2: 4
definition term
: definition item 1

: definition item 2

  {.red}

.
definition term
: definition item 1

: definition item 2

{.red}

````````````````````````````````


### Cond 3.3

Cond 3.3 attributes go to the paragraph

Assigned to paragraph

```````````````````````````````` example No Previous Sibling - Cond 3.3: 1
* list item 1
* list item 2

  {.red} Some text

.
* list item 1
* list item 2

  {.red} Some text

````````````````````````````````


Assigned to definition item 2

```````````````````````````````` example No Previous Sibling - Cond 3.3: 2
definition term
: definition item 1
: definition item 2

  {.red} some text

.
definition term
: definition item 1
: definition item 2

{.red} some text

````````````````````````````````


### Cond 3.4

* paragraph parent is not a paragraph item container
* if paragraph only contains attributes then
  * if paragraph has no previous sibling then
    * Cond 3.4 attributes go to paragraph's parent

Assigned to block quote

```````````````````````````````` example No Previous Sibling - Cond 3.4: 1
> {.red}
> 
> block quote text
.
> {.red}
>
> block quote text

````````````````````````````````


### Cond 3.5

Cond 3.5 attributes go to paragraph's previous sibling,

Assigned to previous paragraph

```````````````````````````````` example No Previous Sibling - Cond 3.5: 1
Some paragraph
 
{.red}
.
Some paragraph

{.red}
````````````````````````````````


Assigned to previous block quote

```````````````````````````````` example No Previous Sibling - Cond 3.5: 2
> Some paragraph
 
{.red}
.
> Some paragraph

{.red}
````````````````````````````````


Assigned to previous paragraph in the block quote

```````````````````````````````` example No Previous Sibling - Cond 3.5: 3
> Some paragraph
> 
> {.red}
.
> Some paragraph
>
> {.red}

````````````````````````````````


Assigned to previous list

```````````````````````````````` example No Previous Sibling - Cond 3.5: 4
* list item 1
* list item 2
 
{.red}
.
* list item 1
* list item 2

{.red}
````````````````````````````````


Assigned to previous definition list

```````````````````````````````` example No Previous Sibling - Cond 3.5: 5
definition term 1
: definition item 1.1
 
definition term 2
: definition item 2.1
 
{.red}
.
definition term 1
: definition item 1.1

definition term 2
: definition item 2.1

{.red}
````````````````````````````````


Assigned to previous table

```````````````````````````````` example No Previous Sibling - Cond 3.5: 6
| head |
|------|
| body |

{.red}
.
| head |
|------|
| body |

{.red}
````````````````````````````````


### Cond 3.6

Cond 3.6 attributes go to the paragraph

Assigned to paragraph

```````````````````````````````` example No Previous Sibling - Cond 3.6: 1
Some paragraph
 
{.red} Some Text
.
Some paragraph

{.red} Some Text
````````````````````````````````


### Cond 3.7

* non paragraph parent
* Cond 3.7 attributes go to the parent

```````````````````````````````` example No Previous Sibling - Cond 3.7: 1
Some Text **{.red}bold text**
.
Some Text **{.red}bold text**
````````````````````````````````


## Anchor Targets

To allow for customizing id attributes of elements which have their id attribute computed from
their content, attributes have to assign the id attribute to override the computed value.

Cond 4.1 anchor target element get their id attribute from the last attributes element which is
assigned to the element and has an `id` attribute.

### Cond 4.1

```````````````````````````````` example Anchor Targets - Cond 4.1: 1
# Heading
.
# Heading

````````````````````````````````


```````````````````````````````` example Anchor Targets - Cond 4.1: 2
# Heading {#custom-id}
.
# Heading {#custom-id}

````````````````````````````````


```````````````````````````````` example(Anchor Targets - Cond 4.1: 3) options(anchors)
# Heading {#custom-id}
.
# Heading {#custom-id}

````````````````````````````````


```````````````````````````````` example Anchor Targets - Cond 4.1: 4
# Heading # {#custom-id}
.
# Heading # {#custom-id}

````````````````````````````````


```````````````````````````````` example Anchor Targets - Cond 4.1: 5
Heading {#custom-id}
======================
.
Heading {#custom-id}
====================

````````````````````````````````


```````````````````````````````` example Anchor Targets - Cond 4.1: 6
Heading {#123-heading}
======================
.
Heading {#123-heading}
======================

````````````````````````````````


## Attributes on Reference

Cond 5.1 attributes on reference definition are applied to the references

Reference definitions do not allow any text to follow the definition. Therefore the only way to
have an attribute assigned to the definition is to place it as the only element of the following
paragraph text.

### Cond 5.1

```````````````````````````````` example Attributes on Reference - Cond 5.1: 1
[test]

[test]: http://example.com 

{style="color:red"}
.
[test]

[test]: http://example.com

{style="color:red"}
````````````````````````````````


```````````````````````````````` example Attributes on Reference - Cond 5.1: 2
[reference 1][test] 

[reference 2][test] 

[test]: http://example.com 

{style="color:red"}
.
[reference 1][test]

[reference 2][test]

[test]: http://example.com

{style="color:red"}
````````````````````````````````


Ref image

```````````````````````````````` example Attributes on Reference - Cond 5.1: 3
![test]

[test]: <http://example.com/test.png> 

{style="border-color:red"}
.
![test]

[test]: <http://example.com/test.png>

{style="border-color:red"}
````````````````````````````````


```````````````````````````````` example Attributes on Reference - Cond 5.1: 4
![reference 1][test]

![reference 2][test]

[test]: <http://example.com/test.png> 

{style="border-color:red"}
.
![reference 1][test]

![reference 2][test]

[test]: <http://example.com/test.png>

{style="border-color:red"}
````````````````````````````````


## Random Tests

```````````````````````````````` example(Random Tests: 1) options(no-text-attributes)
Sample text{.class-name}
.
Sample text{.class-name}
````````````````````````````````


Immediately attached to previous text will apply to the sibling text node

```````````````````````````````` example Random Tests: 2
Sample text{.class-name}
.
Sample text{.class-name}
````````````````````````````````


```````````````````````````````` example(Random Tests: 3) options(no-text-attributes)
Paragraph{style="color:red"}
.
Paragraph{style="color:red"}
````````````````````````````````


```````````````````````````````` example Random Tests: 4
Paragraph{style="color:red"}
.
Paragraph{style="color:red"}
````````````````````````````````


```````````````````````````````` example(Random Tests: 5) options(no-text-attributes)
Paragraph {style="color:red"}
.
Paragraph {style="color:red"}
````````````````````````````````


```````````````````````````````` example(Random Tests: 6) options(no-text-attributes)
Sample text **bold**{.class-name}
.
Sample text **bold**{.class-name}
````````````````````````````````


```````````````````````````````` example(Random Tests: 7) options(no-text-attributes)
Sample text **bold** {.class-name}
.
Sample text **bold** {.class-name}
````````````````````````````````


```````````````````````````````` example Random Tests: 8
Sample<!----> text{.class-name}
.
Sample<!----> text{.class-name}
````````````````````````````````


```````````````````````````````` example Random Tests: 9
Sample text **bold<!----> text{.class-name}**
.
Sample text **bold<!----> text{.class-name}**
````````````````````````````````


```````````````````````````````` example(Random Tests: 10) options(no-text-attributes)
Sample text **bold{.class-name}**
.
Sample text **bold{.class-name}**
````````````````````````````````


```````````````````````````````` example Random Tests: 11
Sample text **bold{.class-name}**
.
Sample text **bold{.class-name}**
````````````````````````````````


```````````````````````````````` example(Random Tests: 12) options(no-text-attributes)
Sample text **bold {.class-name}**
.
Sample text **bold {.class-name}**
````````````````````````````````


```````````````````````````````` example(Random Tests: 13) options(no-text-attributes)
Sample text ![Sample Image](http://example.com){width=64 height=32}
.
Sample text ![Sample Image](http://example.com){width=64 height=32}
````````````````````````````````


```````````````````````````````` example(Random Tests: 14) options(no-text-attributes)
* list item{style="color:red"}
* list item {style="color:blue"}
.
* list item{style="color:red"}
* list item {style="color:blue"}

````````````````````````````````


```````````````````````````````` example Random Tests: 15
* list item{style="color:red"}
* list item {style="color:blue"}
.
* list item{style="color:red"}
* list item {style="color:blue"}

````````````````````````````````


empty tight items without attributes should not wrap spans

```````````````````````````````` example(Random Tests: 16) options(no-text-attributes)
paragraph 1

* list item 2
* list item 1{style="color:red"}
.
paragraph 1

* list item 2
* list item 1{style="color:red"}

````````````````````````````````


## Paragraphs

Only attributes, go to paragraph's previous, if none to paragraph's parent

To previous sibling

```````````````````````````````` example Paragraphs: 1
paragraph 1

{style="color:red"}
.
paragraph 1

{style="color:red"}
````````````````````````````````


To parent

```````````````````````````````` example Paragraphs: 2
{style="color:red"}

paragraph 1
.
{style="color:red"}

paragraph 1
````````````````````````````````


To parent, in this case list

```````````````````````````````` example Paragraphs: 3
paragraph 1

* {style="color:red"} list item 1
* list item 2
.
paragraph 1

* {style="color:red"} list item 1
* list item 2

````````````````````````````````


To parent, in this case list

```````````````````````````````` example Paragraphs: 4
paragraph 1

* list item 2
* {style="color:red"} list item 1
.
paragraph 1

* list item 2
* {style="color:red"} list item 1

````````````````````````````````


## Headings Tests

```````````````````````````````` example Headings Tests: 1
Heading
=======
.
Heading
=======

````````````````````````````````


```````````````````````````````` example Headings Tests: 2
Heading with emoji :+1:
=======================
.
Heading with emoji :+1:
=======================

````````````````````````````````


```````````````````````````````` example(Headings Tests: 3) options(no-text-attributes)
Heading{#id1} with multiple{#id2} anchors{#id3}
===============================================
.
Heading{#id1} with multiple{#id2} anchors{#id3}
===============================================

````````````````````````````````


```````````````````````````````` example Headings Tests: 4
Heading{#id1} with multiple{#id2} anchors{#id3}
===============================================
.
Heading{#id1} with multiple{#id2} anchors{#id3}
===============================================

````````````````````````````````


```````````````````````````````` example(Headings Tests: 5) options(no-text-attributes)
Heading{#id1} with multiple{#id2 style="color:red"} anchors{#id3}
=================================================================
.
Heading{#id1} with multiple{#id2 style="color:red"} anchors{#id3}
=================================================================

````````````````````````````````


```````````````````````````````` example Headings Tests: 6
Heading{#id1} with multiple{#id2 style="color:red"} anchors{#id3}
=================================================================
.
Heading{#id1} with multiple{#id2 style="color:red"} anchors{#id3}
=================================================================

````````````````````````````````


```````````````````````````````` example Headings Tests: 7
Heading{#id1} with multiple{#id2 style="color:red"} anchors{#id3} {#id4}
========================================================================
.
Heading{#id1} with multiple{#id2 style="color:red"} anchors{#id3} {#id4}
========================================================================

````````````````````````````````


## TOC

Default rendering with emphasis

```````````````````````````````` example TOC: 1
[TOC] 

# Heading **some bold** 1 {#heading-1}
## Heading 1.1 _some italic_ {#heading-2}
### Heading 1.1.1
### Heading 1.1.2  **_some bold italic_** {#heading-3}
.
[TOC]

# Heading **some bold** 1 {#heading-1}

## Heading 1.1 _some italic_ {#heading-2}

### Heading 1.1.1

### Heading 1.1.2  **_some bold italic_** {#heading-3}

````````````````````````````````


## Spaces

### Named

```````````````````````````````` example Spaces - Named: 1
text {id=value}
.
text {id=value}
````````````````````````````````


```````````````````````````````` example Spaces - Named: 2
text {id=value }
.
text {id=value }
````````````````````````````````


```````````````````````````````` example Spaces - Named: 3
text {id=value      }
.
text {id=value }
````````````````````````````````


```````````````````````````````` example Spaces - Named: 4
text { id=value}
.
text { id=value}
````````````````````````````````


```````````````````````````````` example Spaces - Named: 5
text {         id=value}
.
text { id=value}
````````````````````````````````


```````````````````````````````` example Spaces - Named: 6
text { id="value" }
.
text { id="value" }
````````````````````````````````


```````````````````````````````` example Spaces - Named: 7
text {        id="value"       }
.
text { id="value" }
````````````````````````````````


### Id

```````````````````````````````` example Spaces - Id: 1
text {#value}
.
text {#value}
````````````````````````````````


```````````````````````````````` example Spaces - Id: 2
text {#value }
.
text {#value }
````````````````````````````````


```````````````````````````````` example Spaces - Id: 3
text {#value   }
.
text {#value }
````````````````````````````````


```````````````````````````````` example Spaces - Id: 4
text { #value}
.
text { #value}
````````````````````````````````


```````````````````````````````` example Spaces - Id: 5
text {     #value}
.
text { #value}
````````````````````````````````


```````````````````````````````` example Spaces - Id: 6
text { #value }
.
text { #value }
````````````````````````````````


```````````````````````````````` example Spaces - Id: 7
text {   #value  }
.
text { #value }
````````````````````````````````


### Class

```````````````````````````````` example Spaces - Class: 1
text {.value}
.
text {.value}
````````````````````````````````


```````````````````````````````` example Spaces - Class: 2
text {.value }
.
text {.value }
````````````````````````````````


```````````````````````````````` example Spaces - Class: 3
text {.value   }
.
text {.value }
````````````````````````````````


```````````````````````````````` example Spaces - Class: 4
text { .value}
.
text { .value}
````````````````````````````````


```````````````````````````````` example Spaces - Class: 5
text {     .value}
.
text { .value}
````````````````````````````````


```````````````````````````````` example Spaces - Class: 6
text { .value }
.
text { .value }
````````````````````````````````


```````````````````````````````` example Spaces - Class: 7
text {   .value  }
.
text { .value }
````````````````````````````````


## Format

### Brace Spaces

#### As Is

```````````````````````````````` example(Format - Brace Spaces - As Is: 1) options(attributes-spaces-as-is)
text {id=value}
text {id=value }
text { id=value}
text { id=value }
.
text {id=value}
text {id=value }
text { id=value}
text { id=value }
````````````````````````````````


```````````````````````````````` example(Format - Brace Spaces - As Is: 2) options(attributes-spaces-as-is)
text {#value}
text {#value }
text { #value}
text { #value }
.
text {#value}
text {#value }
text { #value}
text { #value }
````````````````````````````````


```````````````````````````````` example(Format - Brace Spaces - As Is: 3) options(attributes-spaces-as-is)
text {.value}
text {.value }
text { .value}
text { .value }
.
text {.value}
text {.value }
text { .value}
text { .value }
````````````````````````````````


### Brace Spaces

#### Add

```````````````````````````````` example(Format - Brace Spaces - Add: 1) options(attributes-spaces-add)
text {id=value}
text {id=value }
text { id=value}
text { id=value }
.
text { id=value }
text { id=value }
text { id=value }
text { id=value }
````````````````````````````````


```````````````````````````````` example(Format - Brace Spaces - Add: 2) options(attributes-spaces-add)
text {#value}
text {#value }
text { #value}
text { #value }
.
text { #value }
text { #value }
text { #value }
text { #value }
````````````````````````````````


```````````````````````````````` example(Format - Brace Spaces - Add: 3) options(attributes-spaces-add)
text {.value}
text {.value }
text { .value}
text { .value }
.
text { .value }
text { .value }
text { .value }
text { .value }
````````````````````````````````


### Brace Spaces

#### Remove

```````````````````````````````` example(Format - Brace Spaces - Remove: 1) options(attributes-spaces-remove)
text {id=value}
text {id=value }
text { id=value}
text { id=value }
.
text {id=value}
text {id=value}
text {id=value}
text {id=value}
````````````````````````````````


```````````````````````````````` example(Format - Brace Spaces - Remove: 2) options(attributes-spaces-remove)
text {#value}
text {#value }
text { #value}
text { #value }
.
text {#value}
text {#value}
text {#value}
text {#value}
````````````````````````````````


```````````````````````````````` example(Format - Brace Spaces - Remove: 3) options(attributes-spaces-remove)
text {.value}
text {.value }
text { .value}
text { .value }
.
text {.value}
text {.value}
text {.value}
text {.value}
````````````````````````````````


### Equals Spaces

#### As Is

```````````````````````````````` example(Format - Equals Spaces - As Is: 1) options(sep-spaces-as-is)
text {id=value}
text {id =value }
text { id= value}
text { id = value }
.
text {id=value}
text {id =value }
text { id= value}
text { id = value }
````````````````````````````````


#### Add

```````````````````````````````` example(Format - Equals Spaces - Add: 1) options(sep-spaces-add)
text {id=value}
text {id =value }
text { id= value}
text { id = value }
.
text {id = value}
text {id = value }
text { id = value}
text { id = value }
````````````````````````````````


#### Remove

```````````````````````````````` example(Format - Equals Spaces - Remove: 1) options(sep-spaces-remove)
text {id=value}
text {id =value }
text { id= value}
text { id = value }
.
text {id=value}
text {id=value }
text { id=value}
text { id=value }
````````````````````````````````


### Quotes

#### AsIs

```````````````````````````````` example(Format - Quotes - AsIs: 1) options(value-quotes-as-is)
text {id=value}
text {id='value'}
text {id="value"}
text {id="va'lue"}
text {id='va"lue'}
text {id='value spaced'}
text {id="value spaced"}
.
text {id=value}
text {id='value'}
text {id="value"}
text {id="va'lue"}
text {id='va"lue'}
text {id='value spaced'}
text {id="value spaced"}
````````````````````````````````


#### AsIs

```````````````````````````````` example(Format - Quotes - AsIs: 1) options(value-quotes-as-is)
text {id=value}
text {id='value'}
text {id="value"}
text {id="va'lue"}
text {id='va"lue'}
text {id='value spaced'}
text {id="value spaced"}
.
text {id=value}
text {id='value'}
text {id="value"}
text {id="va'lue"}
text {id='va"lue'}
text {id='value spaced'}
text {id="value spaced"}
````````````````````````````````


#### NoQuotesSinglePreferred

```````````````````````````````` example(Format - Quotes - NoQuotesSinglePreferred: 1) options(value-quotes-no-quotes-single-preferred)
text {id=value}
text {id='value'}
text {id="value"}
text {id="va'lue"}
text {id='va"lue'}
text {id='value spaced'}
text {id="value spaced"}
.
text {id=value}
text {id=value}
text {id=value}
text {id="va'lue"}
text {id='va"lue'}
text {id='value spaced'}
text {id='value spaced'}
````````````````````````````````


#### NoQuotesDoublePreferred

```````````````````````````````` example(Format - Quotes - NoQuotesDoublePreferred: 1) options(value-quotes-no-quotes-double-preferred)
text {id=value}
text {id='value'}
text {id="value"}
text {id="va'lue"}
text {id='va"lue'}
text {id='value spaced'}
text {id="value spaced"}
.
text {id=value}
text {id=value}
text {id=value}
text {id="va'lue"}
text {id='va"lue'}
text {id="value spaced"}
text {id="value spaced"}
````````````````````````````````


#### SinglePreferred

```````````````````````````````` example(Format - Quotes - SinglePreferred: 1) options(value-quotes-single-preferred)
text {id=value}
text {id='value'}
text {id="value"}
text {id="va'lue"}
text {id='va"lue'}
text {id='value spaced'}
text {id="value spaced"}
.
text {id='value'}
text {id='value'}
text {id='value'}
text {id="va'lue"}
text {id='va"lue'}
text {id='value spaced'}
text {id='value spaced'}
````````````````````````````````


#### DoublePreferred

```````````````````````````````` example(Format - Quotes - DoublePreferred: 1) options(value-quotes-double-preferred)
text {id=value}
text {id='value'}
text {id="value"}
text {id="va'lue"}
text {id='va"lue'}
text {id='value spaced'}
text {id="value spaced"}
.
text {id="value"}
text {id="value"}
text {id="value"}
text {id="va'lue"}
text {id='va"lue'}
text {id="value spaced"}
text {id="value spaced"}
````````````````````````````````


#### SingleQuotes

```````````````````````````````` example(Format - Quotes - SingleQuotes: 1) options(value-quotes-single-quotes)
text {id=value}
text {id='value'}
text {id="value"}
text {id="va'lue"}
text {id='va"lue'}
text {id='value spaced'}
text {id="value spaced"}
.
text {id='value'}
text {id='value'}
text {id='value'}
text {id='va&apos;lue'}
text {id='va"lue'}
text {id='value spaced'}
text {id='value spaced'}
````````````````````````````````


#### DoubleQuotes

```````````````````````````````` example(Format - Quotes - DoubleQuotes: 1) options(value-quotes-double-quotes)
text {id=value}
text {id='value'}
text {id="value"}
text {id="va'lue"}
text {id='va"lue'}
text {id='value spaced'}
text {id="value spaced"}
text {#noQuotes}
text {.noQuotes}
.
text {id="value"}
text {id="value"}
text {id="value"}
text {id="va'lue"}
text {id="va&quot;lue"}
text {id="value spaced"}
text {id="value spaced"}
text {#noQuotes}
text {.noQuotes}
````````````````````````````````


### Combine Consecutive

```````````````````````````````` example(Format - Combine Consecutive: 1) options(combine-consecutive)
text {id=value}{id=value2 .test}
.
text {id=value2 .test}
````````````````````````````````


```````````````````````````````` example(Format - Combine Consecutive: 2) options(combine-consecutive)
text {id=value}{.test}{#text}
.
text {.test #text}
````````````````````````````````


### Sort Attributes

```````````````````````````````` example(Format - Sort Attributes: 1) options(sort-attributes)
text {style="color" .text id=value}
.
text {id=value .text style="color"}
````````````````````````````````


```````````````````````````````` example(Format - Sort Attributes: 2) options(sort-attributes)
text {.text class="more" #value0 style="color:red" style="back:blue" id=value}
.
text {id=value class="text more" style="color:red;back:blue"}
````````````````````````````````


```````````````````````````````` example(Format - Sort Attributes: 3) options(combine-consecutive, sort-attributes)
text {.text}{class="more"}{#value0}{style="color:red"}{style="back:blue"}{id=value}
.
text {id=value class="text more" style="color:red;back:blue"}
````````````````````````````````


### Id-Class

```````````````````````````````` example(Format - Id-Class: 1) options(sort-attributes, id-implicit, class-implicit, value-quotes-double-quotes)
text {style="color" class=text id=value}
.
text {#value .text style="color"}
````````````````````````````````


```````````````````````````````` example(Format - Id-Class: 2) options(sort-attributes, id-implicit, class-implicit, value-quotes-double-quotes)
text {.text .more id=value0 style="color:red" style="back:blue" id=value}
.
text {#value class="text more" style="color:red;back:blue"}
````````````````````````````````


```````````````````````````````` example(Format - Id-Class: 3) options(combine-consecutive, sort-attributes, id-implicit, class-implicit, value-quotes-double-quotes)
text {.text}{class="more"}{#value0}{style="color:red"}{style="back:blue"}{id=value}
.
text {#value class="text more" style="color:red;back:blue"}
````````````````````````````````


```````````````````````````````` example(Format - Id-Class: 4) options(sort-attributes, id-explicit, class-implicit, value-quotes-double-quotes)
text {style="color" class=text #value}
.
text {id="value" .text style="color"}
````````````````````````````````


```````````````````````````````` example(Format - Id-Class: 5) options(sort-attributes, id-explicit, class-implicit, value-quotes-double-quotes)
text {.text class="more" #value0 style="color:red" style="back:blue" id=value}
.
text {id="value" class="text more" style="color:red;back:blue"}
````````````````````````````````


```````````````````````````````` example(Format - Id-Class: 6) options(combine-consecutive, sort-attributes, id-explicit, class-implicit, value-quotes-double-quotes)
text {.text}{class="more"}{#value0}{style="color:red"}{style="back:blue"}{id=value}
.
text {id="value" class="text more" style="color:red;back:blue"}
````````````````````````````````


```````````````````````````````` example(Format - Id-Class: 7) options(sort-attributes, id-explicit, class-explicit, value-quotes-double-quotes)
text {style="color" .text #value}
.
text {id="value" class="text" style="color"}
````````````````````````````````


```````````````````````````````` example(Format - Id-Class: 8) options(sort-attributes, id-explicit, class-explicit, value-quotes-double-quotes)
text {.text class="more" #value0 style="color:red" style="back:blue" id=value}
.
text {id="value" class="text more" style="color:red;back:blue"}
````````````````````````````````


```````````````````````````````` example(Format - Id-Class: 9) options(combine-consecutive, sort-attributes, id-explicit, class-explicit, value-quotes-double-quotes)
text {.text}{class="more"}{#value0}{style="color:red"}{style="back:blue"}{#value}
.
text {id="value" class="text more" style="color:red;back:blue"}
````````````````````````````````


