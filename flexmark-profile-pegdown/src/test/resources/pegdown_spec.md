---
title: Pegdown Basic Compatibility Spec
author: Vladimir Schneider
version: 0.2
date: '2017-01-07'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

Tests processor compatibility flags for list processing where most of the differences are to be
found.

## Bullet Mismatch Starts a New List

Test to see if bullet mismatch starts a new list

```````````````````````````````` example Bullet Mismatch Starts a New List: 1
- item
+ item
* item

.
<ul>
  <li>item</li>
  <li>item</li>
  <li>item</li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


## Ordered List Item Sets List Start

Test to see if ordered list item will set list start if not one

```````````````````````````````` example Ordered List Item Sets List Start: 1
2. Non One Start Item
.
<ol>
  <li>Non One Start Item</li>
</ol>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


## Mismatched List Item Type Handling

Test how mismatches in item types are handled

```````````````````````````````` example Mismatched List Item Type Handling: 1
- Bullet List
1. With Ordered Item
.
<ul>
  <li>Bullet List</li>
</ul>
<ol>
  <li>With Ordered Item</li>
</ol>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


```````````````````````````````` example Mismatched List Item Type Handling: 2
1. Ordered Item
- With Bullet List
.
<ol>
  <li>Ordered Item</li>
</ol>
<ul>
  <li>With Bullet List</li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


## Loose Item Handling

Tests how all tight items are generated

```````````````````````````````` example Loose Item Handling: 1
- item 1
- item 2 
- item 3 
- item 4 
.
<ul>
  <li>item 1</li>
  <li>item 2</li>
  <li>item 3</li>
  <li>item 4</li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Test to see how trailing blank after item determines looseness

```````````````````````````````` example Loose Item Handling: 2
- item 1

- item 2 
- item 3 
- item 4 
.
<ul>
  <li>
  <p>item 1</p></li>
  <li>
  <p>item 2 </p></li>
  <li>item 3</li>
  <li>item 4</li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 3
- item 1
- item 2 

- item 3 
- item 4 
.
<ul>
  <li>item 1</li>
  <li>item 2</li>
  <li>
  <p>item 3 </p></li>
  <li>item 4</li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 4
- item 1
- item 2 
- item 3 

- item 4 
.
<ul>
  <li>item 1</li>
  <li>item 2</li>
  <li>item 3</li>
  <li>
  <p>item 4 </p></li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Test looseness with child items

```````````````````````````````` example Loose Item Handling: 5
- item 1
    - item 1.1
- item 2 
    - item 2.1 
- item 3 
    - item 3.1 
- item 4 
    - item 4.1 
.
<ul>
  <li>item 1
    <ul>
      <li>item 1.1</li>
    </ul>
  </li>
  <li>item 2
    <ul>
      <li>item 2.1</li>
    </ul>
  </li>
  <li>item 3
    <ul>
      <li>item 3.1</li>
    </ul>
  </li>
  <li>item 4
    <ul>
      <li>item 4.1</li>
    </ul>
  </li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 6
- item 1

    - item 1.1
- item 2 
    - item 2.1 
- item 3 
    - item 3.1 
- item 4 
    - item 4.1 
.
<ul>
  <li>item 1
    <ul>
      <li>
      <p>item 1.1</p></li>
    </ul>
  </li>
  <li>item 2
    <ul>
      <li>item 2.1</li>
    </ul>
  </li>
  <li>item 3
    <ul>
      <li>item 3.1</li>
    </ul>
  </li>
  <li>item 4
    <ul>
      <li>item 4.1</li>
    </ul>
  </li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 7
- item 1
    - item 1.1

- item 2 
    - item 2.1 
- item 3 
    - item 3.1 
- item 4 
    - item 4.1 
.
<ul>
  <li>
    <p>item 1</p>
    <ul>
      <li>item 1.1</li>
    </ul>
  </li>
  <li>
    <p>item 2 </p>
    <ul>
      <li>item 2.1</li>
    </ul>
  </li>
  <li>item 3
    <ul>
      <li>item 3.1</li>
    </ul>
  </li>
  <li>item 4
    <ul>
      <li>item 4.1</li>
    </ul>
  </li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 8
- item 1
    - item 1.1
- item 2 

    - item 2.1 
- item 3 
    - item 3.1 
- item 4 
    - item 4.1 
.
<ul>
  <li>item 1
    <ul>
      <li>item 1.1</li>
    </ul>
  </li>
  <li>item 2
    <ul>
      <li>
      <p>item 2.1</p></li>
    </ul>
  </li>
  <li>item 3
    <ul>
      <li>item 3.1</li>
    </ul>
  </li>
  <li>item 4
    <ul>
      <li>item 4.1</li>
    </ul>
  </li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 9
- item 1
    - item 1.1
- item 2 
    - item 2.1 

- item 3 
    - item 3.1 
- item 4 
    - item 4.1 
.
<ul>
  <li>item 1
    <ul>
      <li>item 1.1</li>
    </ul>
  </li>
  <li>item 2
    <ul>
      <li>item 2.1</li>
    </ul>
  </li>
  <li>
    <p>item 3 </p>
    <ul>
      <li>item 3.1</li>
    </ul>
  </li>
  <li>item 4
    <ul>
      <li>item 4.1</li>
    </ul>
  </li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 10
- item 1
    - item 1.1
- item 2 
    - item 2.1 
- item 3 

    - item 3.1 
- item 4 
    - item 4.1 
.
<ul>
  <li>item 1
    <ul>
      <li>item 1.1</li>
    </ul>
  </li>
  <li>item 2
    <ul>
      <li>item 2.1</li>
    </ul>
  </li>
  <li>item 3
    <ul>
      <li>
      <p>item 3.1</p></li>
    </ul>
  </li>
  <li>item 4
    <ul>
      <li>item 4.1</li>
    </ul>
  </li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 11
- item 1
    - item 1.1
- item 2 
    - item 2.1 
- item 3 
    - item 3.1 

- item 4 
    - item 4.1 
.
<ul>
  <li>item 1
    <ul>
      <li>item 1.1</li>
    </ul>
  </li>
  <li>item 2
    <ul>
      <li>item 2.1</li>
    </ul>
  </li>
  <li>item 3
    <ul>
      <li>item 3.1</li>
    </ul>
  </li>
  <li>
    <p>item 4 </p>
    <ul>
      <li>item 4.1</li>
    </ul>
  </li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 12
- item 1
    - item 1.1
- item 2 
    - item 2.1 
- item 3 
    - item 3.1 
- item 4 

    - item 4.1 
.
<ul>
  <li>item 1
    <ul>
      <li>item 1.1</li>
    </ul>
  </li>
  <li>item 2
    <ul>
      <li>item 2.1</li>
    </ul>
  </li>
  <li>item 3
    <ul>
      <li>item 3.1</li>
    </ul>
  </li>
  <li>item 4
    <ul>
      <li>
      <p>item 4.1</p></li>
    </ul>
  </li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


## List Item Interrupts Paragraph

Test to see which list items can interrupt paragraphs.

```````````````````````````````` example List Item Interrupts Paragraph: 1
Bullet item can interrupt paragraph
* item

Empty bullet item with space can interrupt paragraph 
* 

Empty bullet item without space can interrupt paragraph
*

Numbered one item can interrupt paragraph
1. one item

Empty Numbered one item with space can interrupt paragraph
1. 

Empty Numbered one item without space can interrupt paragraph
1.

Numbered non-one item can interrupt paragraph
2. non-one item

Empty Numbered non-one item with space can interrupt paragraph
2. 

Empty Numbered non-one item without space can interrupt paragraph
2.

.
<p>Bullet item can interrupt paragraph * item</p>
<p>Empty bullet item with space can interrupt paragraph * </p>
<p>Empty bullet item without space can interrupt paragraph *</p>
<p>Numbered one item can interrupt paragraph 1. one item</p>
<p>Empty Numbered one item with space can interrupt paragraph 1. </p>
<p>Empty Numbered one item without space can interrupt paragraph 1.</p>
<p>Numbered non-one item can interrupt paragraph 2. non-one item</p>
<p>Empty Numbered non-one item with space can interrupt paragraph 2. </p>
<p>Empty Numbered non-one item without space can interrupt paragraph 2.</p>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Test to see which list items can interrupt another bullet list item's paragraphs

```````````````````````````````` example List Item Interrupts Paragraph: 2
* Bullet item can interrupt paragraph of a bullet list item
* item

<!--List Break-->

* Empty bullet item with space can interrupt paragraph of a bullet list item
* 

<!--List Break-->

* Empty bullet item without space can interrupt paragraph of a bullet list item
*

<!--List Break-->

* Numbered one item can interrupt paragraph of a bullet list item
1. one item

<!--List Break-->

* Empty Numbered one item with space can interrupt paragraph of a bullet list item
1. 

<!--List Break-->

* Empty Numbered one item without space can interrupt paragraph of a bullet list item
1.

<!--List Break-->

* Numbered non-one item can interrupt paragraph of a bullet list item
2. non-one item

<!--List Break-->

* Empty Numbered non-one item with space can interrupt paragraph of a bullet list item
2. 

<!--List Break-->

* Empty Numbered non-one item without space can interrupt paragraph of a bullet list item
2.

.
<ul>
  <li>Bullet item can interrupt paragraph of a bullet list item</li>
  <li>item</li>
</ul>
<!--List Break-->
<ul>
  <li>Empty bullet item with space can interrupt paragraph of a bullet list item</li>
  <li></li>
</ul>
<!--List Break-->
<ul>
  <li>Empty bullet item without space can interrupt paragraph of a bullet list item *</li>
</ul>
<!--List Break-->
<ul>
  <li>Numbered one item can interrupt paragraph of a bullet list item</li>
</ul>
<ol>
  <li>one item</li>
</ol>
<!--List Break-->
<ul>
  <li>Empty Numbered one item with space can interrupt paragraph of a bullet list item</li>
</ul>
<ol>
  <li></li>
</ol>
<!--List Break-->
<ul>
  <li>Empty Numbered one item without space can interrupt paragraph of a bullet list item 1.</li>
</ul>
<!--List Break-->
<ul>
  <li>Numbered non-one item can interrupt paragraph of a bullet list item</li>
</ul>
<ol>
  <li>non-one item</li>
</ol>
<!--List Break-->
<ul>
  <li>Empty Numbered non-one item with space can interrupt paragraph of a bullet list item</li>
</ul>
<ol>
  <li></li>
</ol>
<!--List Break-->
<ul>
  <li>Empty Numbered non-one item without space can interrupt paragraph of a bullet list item 2.</li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Test to see which list items can interrupt another numbered list item's paragraphs

```````````````````````````````` example List Item Interrupts Paragraph: 3
1. Bullet item can interrupt paragraph of a numbered list item
* item

<!--List Break-->

1. Empty bullet item with space can interrupt paragraph of a numbered list item
* 

<!--List Break-->

1. Empty bullet item without space can interrupt paragraph of a numbered list item
*

<!--List Break-->

1. Numbered one item can interrupt paragraph of a numbered list item
1. one item

<!--List Break-->

1. Empty Numbered one item with space can interrupt paragraph of a numbered list item 
1. 

<!--List Break-->

1. Empty Numbered one item without space can interrupt paragraph of a numbered list item 
1.

<!--List Break-->

1. Numbered non-one item can interrupt paragraph of a numbered list item
2. non-one item

<!--List Break-->

1. Empty Numbered non-one item with space can interrupt paragraph of a numbered list item
2. 

<!--List Break-->

1. Empty Numbered non-one item without space can interrupt paragraph of a numbered list item
2.

.
<ol>
  <li>Bullet item can interrupt paragraph of a numbered list item</li>
</ol>
<ul>
  <li>item</li>
</ul>
<!--List Break-->
<ol>
  <li>Empty bullet item with space can interrupt paragraph of a numbered list item</li>
</ol>
<ul>
  <li></li>
</ul>
<!--List Break-->
<ol>
  <li>Empty bullet item without space can interrupt paragraph of a numbered list item *</li>
</ol>
<!--List Break-->
<ol>
  <li>Numbered one item can interrupt paragraph of a numbered list item</li>
  <li>one item</li>
</ol>
<!--List Break-->
<ol>
  <li>Empty Numbered one item with space can interrupt paragraph of a numbered list item</li>
  <li></li>
</ol>
<!--List Break-->
<ol>
  <li>Empty Numbered one item without space can interrupt paragraph of a numbered list item 1.</li>
</ol>
<!--List Break-->
<ol>
  <li>Numbered non-one item can interrupt paragraph of a numbered list item</li>
  <li>non-one item</li>
</ol>
<!--List Break-->
<ol>
  <li>Empty Numbered non-one item with space can interrupt paragraph of a numbered list item</li>
  <li></li>
</ol>
<!--List Break-->
<ol>
  <li>Empty Numbered non-one item without space can interrupt paragraph of a numbered list item 2.</li>
</ol>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


## List Item Indent Handling

Test if list item indent handling for edge cases

```````````````````````````````` example List Item Indent Handling: 1
* item 1
 * item 2
  * item 3
   * item 4
    * item 5
     * item 6
      * item 7
       * item 8
        * item 9
.
<ul>
  <li>item 1</li>
  <li>item 2</li>
  <li>item 3</li>
  <li>item 4
    <ul>
      <li>item 5</li>
      <li>item 6</li>
      <li>item 7</li>
      <li>item 8
        <ul>
          <li>item 9</li>
        </ul>
      </li>
    </ul>
  </li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


```````````````````````````````` example List Item Indent Handling: 2
* item 1

 * item 2
 
  * item 3
  
   * item 4
   
    * item 5
    
     * item 6
     
      * item 7
      
       * item 8
       
        * item 9
.
<ul>
  <li>
  <p>item 1</p></li>
  <li>
  <p>item 2</p></li>
  <li>
  <p>item 3</p></li>
  <li>
    <p>item 4</p>
    <ul>
      <li>
      <p>item 5</p></li>
      <li>
      <p>item 6</p></li>
      <li>
      <p>item 7</p></li>
      <li>
        <p>item 8</p>
        <ul>
          <li>
          <p>item 9</p></li>
        </ul>
      </li>
    </ul>
  </li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


```````````````````````````````` example List Item Indent Handling: 3
*  item 1
 *  item 2
  *  item 3
   *  item 4
    *  item 5
     *  item 6
      *  item 7
       *  item 8
        *  item 9
.
<ul>
  <li>item 1</li>
  <li>item 2</li>
  <li>item 3</li>
  <li>item 4
    <ul>
      <li>item 5</li>
      <li>item 6</li>
      <li>item 7</li>
      <li>item 8
        <ul>
          <li>item 9</li>
        </ul>
      </li>
    </ul>
  </li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Test shows where the boundary switch to indented code occurs. Sub-items first paragraph is a
paragraph, the second is indented code

```````````````````````````````` example List Item Indent Handling: 4
-   test
    - sub item

         sub item child para

          indented code
          
---

1.  test
    1. sub item

          sub item child para

           indented code

.
<ul>
  <li>test
    <ul>
      <li>sub item
        <p>sub item child para</p>
        <p>indented code</p>
      </li>
    </ul>
  </li>
</ul>
<hr/>
<ol>
  <li>test
    <ol>
      <li>sub item
        <p>sub item child para</p>
        <p>indented code</p>
      </li>
    </ol>
  </li>
</ol>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Test shows where the boundary switch to indented code occurs. Sub-items first paragraph is a
paragraph, the second is indented code

More extensive test to show where the boundary switch to indented code occurs. Sub-items first
paragraph is a paragraph, the second is indented code

```````````````````````````````` example List Item Indent Handling: 5
* item 1
    
  item para 1
  
   item para 2
   
    item para 3
    
     item para 4
     
      item para 5
      
       item para 6
       
        item para 7
        
 * item 2
    
   item para 1
   
    item para 2
    
     item para 3
     
      item para 4
      
       item para 5
       
        item para 6
        
         item para 7
        
  * item 3
    
    item para 1
    
     item para 2
     
      item para 3
      
       item para 4
       
        item para 5
        
         item para 6
         
          item para 7
        
   * item 4
    
     item para 1
     
      item para 2
      
       item para 3
       
        item para 4
        
         item para 5
         
          item para 6
          
           item para 7
        
    * item 5
    
      item para 1
  
       item para 2
   
        item para 3
    
         item para 4
     
          item para 5
      
           item para 6
       
            item para 7
.
<ul>
  <li>item 1</li>
</ul>
<p>item para 1</p>
<p>item para 2</p>
<pre><code>item para 3

 item para 4

  item para 5

   item para 6

    item para 7
</code></pre>
<ul>
  <li>item 2</li>
</ul>
<p>item para 1</p>
<pre><code>item para 2

 item para 3

  item para 4

   item para 5

    item para 6

     item para 7
</code></pre>
<ul>
  <li>
    <p>item 3</p>
    <p>item para 1</p>
    <p>item para 2</p>
    <p>item para 3</p>
    <p>item para 4</p>
    <pre><code>item para 5

 item para 6

  item para 7
</code></pre>
  </li>
  <li>
    <p>item 4</p>
    <p>item para 1</p>
    <p>item para 2</p>
    <p>item para 3</p>
    <pre><code>item para 4

 item para 5

  item para 6

   item para 7
</code></pre>
    <ul>
      <li>item 5</li>
    </ul>
    <p>item para 1</p>
    <p>item para 2</p>
    <pre><code>item para 3

 item para 4

  item para 5

   item para 6

    item para 7
</code></pre>
  </li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Test for how items with indent > first list item's indent but < previous item's content indent
are handled. Mainly, if they are handled in a weird way of treating the item as a sub-item of
the previous list item. There was one that did it that way, GitHub comments if I can remember
right, but now they switched to commonmark list handling with mods. Guess it is now GFC--GitHub
Flavoured Commonmark.

```````````````````````````````` example List Item Indent Handling: 6
*  item 1
   * item 2
  * item 3
.
<ul>
  <li>item 1</li>
  <li>item 2</li>
  <li>item 3</li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Test how headings in list items are handled, leading space allowed or not

```````````````````````````````` example List Item Indent Handling: 7
* item 1

    # Heading 1
    
     ## Heading 2
    
      ### Heading 3
    
       #### Heading 4
    
        ##### Heading 5
    
         ###### Heading 6

    * item 2

        # Heading 1
        
         ## Heading 2
        
          ### Heading 3
        
           #### Heading 3
        
            ##### Heading 5
          
             ###### Heading 6

.
<ul>
  <li>item 1
    <h1>Heading 1</h1>
    <p>## Heading 2</p>
    <p>### Heading 3</p>
    <p>#### Heading 4</p>
    <pre><code>##### Heading 5

 ###### Heading 6
</code></pre>
    <ul>
      <li>item 2
        <h1>Heading 1</h1>
        <p>## Heading 2</p>
        <p>### Heading 3</p>
        <p>#### Heading 3</p>
        <pre><code>##### Heading 5

 ###### Heading 6
</code></pre>
      </li>
    </ul>
  </li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


## Block quote parsing

Whether blank lines are required to start a block quote

```````````````````````````````` example Block quote parsing: 1
paragraph text
> block quoted text
.
<p>paragraph text</p>
<blockquote>
  <p>block quoted text</p>
</blockquote>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Whether blank lines are required to start a block quote

```````````````````````````````` example Block quote parsing: 2
paragraph text

> block quoted text
.
<p>paragraph text</p>
<blockquote>
  <p>block quoted text</p>
</blockquote>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Whether blank lines are ignored and treated as if prefixed with block quote

```````````````````````````````` example Block quote parsing: 3
> block quoted text

> more block quoted text
.
<blockquote>
  <p>block quoted text</p>
  <p>more block quoted text</p>
</blockquote>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Whether block quotes continue to a blank line

```````````````````````````````` example Block quote parsing: 4
> block quoted text
lazy continuation

another paragraph
.
<blockquote>
  <p>block quoted text lazy continuation</p>
</blockquote>
<p>another paragraph</p>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Whether leading spaces are allowed before block quote marker

```````````````````````````````` example Block quote parsing: 5
 > block quote paragraph text
.
<p>&gt; block quote paragraph text</p>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Whether trailing spaces are required after block quote marker

```````````````````````````````` example Block quote parsing: 6
>block quote paragraph text
.
<blockquote>
  <p>block quote paragraph text</p>
</blockquote>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Whether block quotes can interrupt item paragraph

```````````````````````````````` example Block quote parsing: 7
* item 1
    > block quoted text

<!-- list break -->

1. item 1
    > block quoted text

.
<ul>
  <li>
  <p>item 1</p>
  <blockquote>
    <p>block quoted text</p>
  </blockquote></li>
</ul>
<!-- list break -->
<ol>
  <li>
  <p>item 1</p>
  <blockquote>
    <p>block quoted text</p>
  </blockquote></li>
</ol>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Whether block quotes can interrupt item paragraph

```````````````````````````````` example Block quote parsing: 8
* item 1

    > block quoted text

<!-- list break -->

1. item 1

    > block quoted text

.
<ul>
  <li>item 1
    <blockquote>
      <p>block quoted text</p>
    </blockquote>
  </li>
</ul>
<!-- list break -->
<ol>
  <li>item 1
    <blockquote>
      <p>block quoted text</p>
    </blockquote>
  </li>
</ol>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Whether block quotes with leading space can interrupt item paragraphs

```````````````````````````````` example Block quote parsing: 9
* item 1
     > block quoted text

<!-- list break -->

1. item 1
     > block quoted text

.
<ul>
  <li>item 1  &gt; block quoted text</li>
</ul>
<!-- list break -->
<ol>
  <li>item 1  &gt; block quoted text</li>
</ol>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Whether block quotes with leading space can interrupt item paragraph

```````````````````````````````` example Block quote parsing: 10
* item 1

     > block quoted text

<!-- list break -->

1. item 1

     > block quoted text

.
<ul>
  <li>item 1
    <p>&gt; block quoted text</p>
  </li>
</ul>
<!-- list break -->
<ol>
  <li>item 1
    <p>&gt; block quoted text</p>
  </li>
</ol>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Whether block quotes without trailing space can interrupt item paragraphs

```````````````````````````````` example Block quote parsing: 11
* item 1
    >block quoted text

<!-- list break -->

1. item 1
    >block quoted text

.
<ul>
  <li>
  <p>item 1</p>
  <blockquote>
    <p>block quoted text</p>
  </blockquote></li>
</ul>
<!-- list break -->
<ol>
  <li>
  <p>item 1</p>
  <blockquote>
    <p>block quoted text</p>
  </blockquote></li>
</ol>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Whether block quotes without trailing space can interrupt item paragraph

```````````````````````````````` example Block quote parsing: 12
* item 1

    >block quoted text

<!-- list break -->

1. item 1

    >block quoted text

.
<ul>
  <li>item 1
    <blockquote>
      <p>block quoted text</p>
    </blockquote>
  </li>
</ul>
<!-- list break -->
<ol>
  <li>item 1
    <blockquote>
      <p>block quoted text</p>
    </blockquote>
  </li>
</ol>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Test to make sure content indented deeply nested lists process correctly

```````````````````````````````` example Block quote parsing: 13
- item 1
    - item 2
        - item 3
            - item 4
                - item 5
                    - item 6
                        - item 7
                            - item 8
                                - item 9

.
<ul>
  <li>item 1
    <ul>
      <li>item 2
        <ul>
          <li>item 3
            <ul>
              <li>item 4
                <ul>
                  <li>item 5
                    <ul>
                      <li>item 6
                        <ul>
                          <li>item 7
                            <ul>
                              <li>item 8
                                <ul>
                                  <li>item 9</li>
                                </ul>
                              </li>
                            </ul>
                          </li>
                        </ul>
                      </li>
                    </ul>
                  </li>
                </ul>
              </li>
            </ul>
          </li>
        </ul>
      </li>
    </ul>
  </li>
</ul>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


```````````````````````````````` example Block quote parsing: 14
1. item 1
    1. item 2
        1. item 3
            1. item 4
                1. item 5
                    1. item 6
                        1. item 7
                            1. item 8
                                1. item 9

.
<ol>
  <li>item 1
    <ol>
      <li>item 2
        <ol>
          <li>item 3
            <ol>
              <li>item 4
                <ol>
                  <li>item 5
                    <ol>
                      <li>item 6
                        <ol>
                          <li>item 7
                            <ol>
                              <li>item 8
                                <ol>
                                  <li>item 9</li>
                                </ol>
                              </li>
                            </ol>
                          </li>
                        </ol>
                      </li>
                    </ol>
                  </li>
                </ol>
              </li>
            </ol>
          </li>
        </ol>
      </li>
    </ol>
  </li>
</ol>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


## Table

Multi-line code span

```````````````````````````````` example Table: 1
|header1|header2|header3|
|-------|----|------|
| line | ```{
"key1": "xxx",
"key2": [
"xxx",
"zzz"
]
} ```
| ```{
"key1": "xxx",
"key2": "xxx"
}```|
.
<p>|header1|header2|header3| |-------|----|------| | line | <code>{
&quot;key1&quot;: &quot;xxx&quot;,
&quot;key2&quot;: [
&quot;xxx&quot;,
&quot;zzz&quot;
]
}</code> | <code>{
&quot;key1&quot;: &quot;xxx&quot;,
&quot;key2&quot;: &quot;xxx&quot;
}</code>|</p>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


Multi-line code span in paragraph

```````````````````````````````` example Table: 2
line ```{
"key1": "xxx",
"key2": [
"xxx",
"zzz"
]
} ```
.
<p>line <code>{
&quot;key1&quot;: &quot;xxx&quot;,
&quot;key2&quot;: [
&quot;xxx&quot;,
&quot;zzz&quot;
]
}</code></p>
.
PegdownParser$PegdownRootNode[0, 0]
````````````````````````````````


