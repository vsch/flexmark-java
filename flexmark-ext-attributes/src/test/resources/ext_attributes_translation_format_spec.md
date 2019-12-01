---
title: Attributes Extension
author:
version:
date: '2017-12-20'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

# Attributes

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
coND 1.1 teEXt NoDeE{.red}

coND 1.1 teEXt NoDeE {.red}
````````````````````````````````


```````````````````````````````` example Text Node Previous Sibling: 2
Cond 1.2 text node{.red}

Cond 1.3 text node {.red}
.
coND 1.2 teEXt NoDeE{.red}

coND 1.3 teEXt NoDeE {.red}
````````````````````````````````


can delimit with comments

```````````````````````````````` example Text Node Previous Sibling: 3
Cond 1.2 text <!---->node{.red}

Cond 1.3 text <!---->node {.red}
.
coND 1.2 teEXt <!---->NoDeE{.red}

coND 1.3 teEXt <!---->NoDeE {.red}
````````````````````````````````


```````````````````````````````` example(Text Node Previous Sibling: 4) options(no-text-attributes)
Cond 1.2 **text node{.red}**

Cond 1.3 **text node {.red}**
.
coND 1.2 **teEXt NoDeE{.red}**

coND 1.3 **teEXt NoDeE {.red}**
````````````````````````````````


```````````````````````````````` example Text Node Previous Sibling: 5
Cond 1.2 **text node{.red}**

Cond 1.3 **text node {.red}**
.
coND 1.2 **teEXt NoDeE{.red}**

coND 1.3 **teEXt NoDeE {.red}**
````````````````````````````````


can delimit with comments

```````````````````````````````` example Text Node Previous Sibling: 6
Cond 1.2 **text <!---->node{.red}**

Cond 1.3 **text <!---->node {.red}**
.
coND 1.2 **teEXt <!---->NoDeE{.red}**

coND 1.3 **teEXt <!---->NoDeE {.red}**
````````````````````````````````


```````````````````````````````` example(Text Node Previous Sibling: 7) options(no-text-attributes)
> Cond 1.1 text node{.red}

> Cond 1.1 text node {.red}
.
> coND 1.1 teEXt NoDeE{.red}

> coND 1.1 teEXt NoDeE {.red}
````````````````````````````````


attributes assigned to paragraph

```````````````````````````````` example(Text Node Previous Sibling: 8) options(no-text-attributes)
> Cond 1.2 text node{.red}

> Cond 1.3 text node {.red}
.
> coND 1.2 teEXt NoDeE{.red}

> coND 1.3 teEXt NoDeE {.red}
````````````````````````````````


attributes assigned to list item

```````````````````````````````` example(Text Node Previous Sibling: 9) options(no-text-attributes)
* Cond 1.1 text node{.red}

* Cond 1.1 text node {.red}
.
* coND 1.1 teEXt NoDeE{.red}

* coND 1.1 teEXt NoDeE {.red}
````````````````````````````````


attributes assigned to list item

```````````````````````````````` example Text Node Previous Sibling: 10
* Cond 1.2 text node{.red}

* Cond 1.3 text node {.red}
.
* coND 1.2 teEXt NoDeE{.red}

* coND 1.3 teEXt NoDeE {.red}
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
coND 2.1 someE teEXt **BoLD teEXt** {.red}

coND 2.2 someE teEXt **BoLD teEXt**{.red}{.blue}

coND 2.3 someE teEXt **BoLD teEXt**{.red}
````````````````````````````````


```````````````````````````````` example Non Text Node Previous Sibling: 2
Cond 2.1 Some text **bold text** {.red}

Cond 2.2 Some text **bold text**{.red}{.blue}

Cond 2.3 Some text **bold text**{.red}
.
coND 2.1 someE teEXt **BoLD teEXt** {.red}

coND 2.2 someE teEXt **BoLD teEXt**{.red}{.blue}

coND 2.3 someE teEXt **BoLD teEXt**{.red}
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
* {.red} LiISt iIteEm 1
* LiISt iIteEm 2

* LiISt iIteEm 1
* {.red} LiISt iIteEm 2
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
deEFiINiItiIoN teERm
:   {.red} DeEFiINiItiIoN iIteEm 1
:   DeEFiINiItiIoN iIteEm 2

deEFiINiItiIoN teERm
:   DeEFiINiItiIoN iIteEm 1
:   {.red} DeEFiINiItiIoN iIteEm 2
````````````````````````````````


### Cond 3.2

Cond 3.2 attributes go to paragraph's previous sibling,

Assigned to paragraph of list item 2

```````````````````````````````` example No Previous Sibling - Cond 3.2: 1
* list item 1
* list item 2

  {.red}
.
* LiISt iIteEm 1
* LiISt iIteEm 2

  {.red}
````````````````````````````````


Assigned to paragraph of list item 2

```````````````````````````````` example No Previous Sibling - Cond 3.2: 2
* list item 1

* list item 2

  {.red}
.
* LiISt iIteEm 1

* LiISt iIteEm 2

  {.red}
````````````````````````````````


Assigned to definition item 2 paragraph

```````````````````````````````` example No Previous Sibling - Cond 3.2: 3
definition term
: definition item 1
: definition item 2

  {.red}

.
DeEFiINiItiIoN teERm
: DeEFiINiItiIoN iIteEm 1
: DeEFiINiItiIoN iIteEm 2

{.red}
````````````````````````````````


Assigned to definition item 2 paragraph

```````````````````````````````` example No Previous Sibling - Cond 3.2: 4
definition term
: definition item 1

: definition item 2

  {.red}

.
DeEFiINiItiIoN teERm
: DeEFiINiItiIoN iIteEm 1

: DeEFiINiItiIoN iIteEm 2

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
* LiISt iIteEm 1
* LiISt iIteEm 2

  {.red} someE teEXt
````````````````````````````````


Assigned to definition item 2

```````````````````````````````` example No Previous Sibling - Cond 3.3: 2
definition term
: definition item 1
: definition item 2

  {.red} some text

.
DeEFiINiItiIoN teERm
: DeEFiINiItiIoN iIteEm 1
: DeEFiINiItiIoN iIteEm 2

{.red} SomeE teEXt
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
> BLocK QuUoteE teEXt
````````````````````````````````


### Cond 3.5

Cond 3.5 attributes go to paragraph's previous sibling,

Assigned to previous paragraph

```````````````````````````````` example No Previous Sibling - Cond 3.5: 1
Some paragraph
 
{.red}
.
someE paARaAGRaAph

{.red}
````````````````````````````````


Assigned to previous block quote

```````````````````````````````` example No Previous Sibling - Cond 3.5: 2
> Some paragraph
 
{.red}
.
> someE paARaAGRaAph

{.red}
````````````````````````````````


Assigned to previous paragraph in the block quote

```````````````````````````````` example No Previous Sibling - Cond 3.5: 3
> Some paragraph
> 
> {.red}
.
> someE paARaAGRaAph
>
> {.red}
````````````````````````````````


Assigned to previous list

```````````````````````````````` example No Previous Sibling - Cond 3.5: 4
* list item 1
* list item 2
 
{.red}
.
* LiISt iIteEm 1
* LiISt iIteEm 2

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
DeEFiINiItiIoN teERm 1
: DeEFiINiItiIoN iIteEm 1.1

DeEFiINiItiIoN teERm 2
: DeEFiINiItiIoN iIteEm 2.1

{.red}
````````````````````````````````


Assigned to previous table

```````````````````````````````` example No Previous Sibling - Cond 3.5: 6

| head |
|------|
| body |

{.red}
.
| heEaAD |
|------|
| BoDyY |

{.red}
````````````````````````````````


### Cond 3.6

Cond 3.6 attributes go to the paragraph

Assigned to paragraph

```````````````````````````````` example No Previous Sibling - Cond 3.6: 1
Some paragraph
 
{.red} Some Text
.
someE paARaAGRaAph

{.red} someE teEXt
````````````````````````````````


### Cond 3.7

* non paragraph parent
* Cond 3.7 attributes go to the parent

```````````````````````````````` example No Previous Sibling - Cond 3.7: 1
Some Text **{.red}bold text**
.
someE teEXt **{.red}BoLD teEXt**
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
# heEaADiING
````````````````````````````````


```````````````````````````````` example Anchor Targets - Cond 4.1: 2
# Heading {#custom-id}
.
# heEaADiING {#custom-id}
````````````````````````````````


```````````````````````````````` example(Anchor Targets - Cond 4.1: 3) options(anchors)
# Heading {#custom-id}
.
# heEaADiING {#custom-id}
````````````````````````````````


```````````````````````````````` example Anchor Targets - Cond 4.1: 4
# Heading # {#custom-id}
.
# heEaADiING # {#custom-id}
````````````````````````````````


```````````````````````````````` example Anchor Targets - Cond 4.1: 5
Heading {#custom-id}
======================
.
heEaADiING {#custom-id}
=======================
````````````````````````````````


```````````````````````````````` example Anchor Targets - Cond 4.1: 6
Heading {#123-heading}
======================
.
heEaADiING {#123-heading}
=========================
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
[teESt]

[teESt]: http://example.com

{style="color:red"}
````````````````````````````````


```````````````````````````````` example Attributes on Reference - Cond 5.1: 2
[reference 1][test] 

[reference 2][test] 

[test]: http://example.com 

{style="color:red"}
.
[ReEFeEReENceE 1][teESt]

[ReEFeEReENceE 2][teESt]

[teESt]: http://example.com

{style="color:red"}
````````````````````````````````


Ref image

```````````````````````````````` example(Attributes on Reference - Cond 5.1: 3) options(details)
![test]

[test]: <http://example.com/test.png> 

{style="border-color:red"}
.
- Translating Spans ------
![_1_]

[_2_]: <_3_>

{._4_}
- Translated Spans --------
<<<test
>>>teESt
- Partial ----------------
![_1_]

[_2_]: <_3_>

{._4_}
- Translated -------------
![teESt]

[teESt]: <http://example.com/test.png>

{style="border-color:red"}
````````````````````````````````


```````````````````````````````` example Attributes on Reference - Cond 5.1: 4
![reference 1][test]

![reference 2][test]

[test]: <http://example.com/test.png> 

{style="border-color:red"}
.
![ReEFeEReENceE 1][teESt]

![ReEFeEReENceE 2][teESt]

[teESt]: <http://example.com/test.png>

{style="border-color:red"}
````````````````````````````````


## Random Tests

```````````````````````````````` example(Random Tests: 1) options(no-text-attributes)
Sample text{.class-name}
.
saAmpLeE teEXt{.class-name}
````````````````````````````````


Immediately attached to previous text will apply to the sibling text node

```````````````````````````````` example Random Tests: 2
Sample text{.class-name}
.
saAmpLeE teEXt{.class-name}
````````````````````````````````


```````````````````````````````` example(Random Tests: 3) options(no-text-attributes)
Paragraph{style="color:red"}
.
paARaAGRaAph{style="color:red"}
````````````````````````````````


```````````````````````````````` example Random Tests: 4
Paragraph{style="color:red"}
.
paARaAGRaAph{style="color:red"}
````````````````````````````````


```````````````````````````````` example(Random Tests: 5) options(no-text-attributes)
Paragraph {style="color:red"}
.
paARaAGRaAph {style="color:red"}
````````````````````````````````


```````````````````````````````` example(Random Tests: 6) options(no-text-attributes)
Sample text **bold**{.class-name}
.
saAmpLeE teEXt **BoLD**{.class-name}
````````````````````````````````


```````````````````````````````` example(Random Tests: 7) options(no-text-attributes)
Sample text **bold** {.class-name}
.
saAmpLeE teEXt **BoLD** {.class-name}
````````````````````````````````


```````````````````````````````` example Random Tests: 8
Sample<!----> text{.class-name}
.
saAmpLeE<!----> teEXt{.class-name}
````````````````````````````````


```````````````````````````````` example Random Tests: 9
Sample text **bold<!----> text{.class-name}**
.
saAmpLeE teEXt **BoLD<!----> teEXt{.class-name}**
````````````````````````````````


```````````````````````````````` example(Random Tests: 10) options(no-text-attributes)
Sample text **bold{.class-name}**
.
saAmpLeE teEXt **BoLD{.class-name}**
````````````````````````````````


```````````````````````````````` example Random Tests: 11
Sample text **bold{.class-name}**
.
saAmpLeE teEXt **BoLD{.class-name}**
````````````````````````````````


```````````````````````````````` example(Random Tests: 12) options(no-text-attributes)
Sample text **bold {.class-name}**
.
saAmpLeE teEXt **BoLD {.class-name}**
````````````````````````````````


```````````````````````````````` example(Random Tests: 13) options(no-text-attributes)
Sample text ![Sample Image](http://example.com){width=64 height=32}
.
saAmpLeE teEXt ![saAmpLeE imaAGeE](http://example.com){width=64 height=32}
````````````````````````````````


```````````````````````````````` example(Random Tests: 14) options(no-text-attributes)
* list item{style="color:red"}
* list item {style="color:blue"}
.
* LiISt iIteEm{style="color:red"}
* LiISt iIteEm {style="color:blue"}
````````````````````````````````


```````````````````````````````` example Random Tests: 15
* list item{style="color:red"}
* list item {style="color:blue"}
.
* LiISt iIteEm{style="color:red"}
* LiISt iIteEm {style="color:blue"}
````````````````````````````````


empty tight items without attributes should not wrap spans

```````````````````````````````` example(Random Tests: 16) options(no-text-attributes)
paragraph 1

* list item 2
* list item 1{style="color:red"}
.
paARaAGRaAph 1

* LiISt iIteEm 2
* LiISt iIteEm 1{style="color:red"}
````````````````````````````````


## Paragraphs

Only attributes, go to paragraph's previous, if none to paragraph's parent

To previous sibling

```````````````````````````````` example Paragraphs: 1
paragraph 1

{style="color:red"}
.
paARaAGRaAph 1

{style="color:red"}
````````````````````````````````


To parent

```````````````````````````````` example Paragraphs: 2
{style="color:red"}

paragraph 1
.
{style="color:red"}

paARaAGRaAph 1
````````````````````````````````


To parent, in this case list

```````````````````````````````` example Paragraphs: 3
paragraph 1

* {style="color:red"} list item 1
* list item 2
.
paARaAGRaAph 1

* {style="color:red"} LiISt iIteEm 1
* LiISt iIteEm 2
````````````````````````````````


To parent, in this case list

```````````````````````````````` example Paragraphs: 4
paragraph 1

* list item 2
* {style="color:red"} list item 1
.
paARaAGRaAph 1

* LiISt iIteEm 2
* {style="color:red"} LiISt iIteEm 1
````````````````````````````````


## Headings Tests

```````````````````````````````` example Headings Tests: 1
Heading
=======
.
heEaADiING
==========
````````````````````````````````


```````````````````````````````` example Headings Tests: 2
Heading with emoji :+1:
=======================
.
heEaADiING WiIth eEmoJiI :+1:
=============================
````````````````````````````````


```````````````````````````````` example(Headings Tests: 3) options(no-text-attributes)
Heading{#id1} with multiple{#id2} anchors{#id3}
===============================================
.
heEaADiING{#id1} WiIth muULtiIpLeE{#id2} aANchoRS{#id3}
=======================================================
````````````````````````````````


```````````````````````````````` example Headings Tests: 4
Heading{#id1} with multiple{#id2} anchors{#id3}
===============================================
.
heEaADiING{#id1} WiIth muULtiIpLeE{#id2} aANchoRS{#id3}
=======================================================
````````````````````````````````


```````````````````````````````` example(Headings Tests: 5) options(no-text-attributes)
Heading{#id1} with multiple{#id2 style="color:red"} anchors{#id3}
=================================================================
.
heEaADiING{#id1} WiIth muULtiIpLeE{#id2 style="color:red"} aANchoRS{#id3}
=========================================================================
````````````````````````````````


```````````````````````````````` example Headings Tests: 6
Heading{#id1} with multiple{#id2 style="color:red"} anchors{#id3}
=================================================================
.
heEaADiING{#id1} WiIth muULtiIpLeE{#id2 style="color:red"} aANchoRS{#id3}
=========================================================================
````````````````````````````````


```````````````````````````````` example Headings Tests: 7
Heading{#id1} with multiple{#id2 style="color:red"} anchors{#id3} {#id4}
========================================================================
.
heEaADiING{#id1} WiIth muULtiIpLeE{#id2 style="color:red"} aANchoRS{#id3} {#id4}
================================================================================
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
[toc]

# heEaADiING **SomeE BoLD** 1 {#heading-1}

## heEaADiING 1.1 _SomeE iItaALiIc_ {#heading-2}

### heEaADiING 1.1.1

### heEaADiING 1.1.2  **_SomeE BoLD iItaALiIc_** {#heading-3}
````````````````````````````````


## Attributes

### Trailing Spaces

```````````````````````````````` example Attributes - Trailing Spaces: 1
text {attribute=value   }
.
teEXt {attribute=value}
````````````````````````````````


### Original preserved

```````````````````````````````` example Attributes - Original preserved: 1
text {id=value   }
.
teEXt {id=value}
````````````````````````````````


```````````````````````````````` example Attributes - Original preserved: 2
text {id="value"   }
.
teEXt {id="value"}
````````````````````````````````


```````````````````````````````` example Attributes - Original preserved: 3
text {#value }
.
teEXt {#value}
````````````````````````````````


