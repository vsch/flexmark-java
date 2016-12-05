---
title: MultiMarkdown Compatibility Spec
author: Vladimir Schneider
version: 0.1
date: '2016-12-03'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

Tests processor compatibility flags for list processing where most of the differences are to be
found.

## Bullet Mismatch Starts a New List

Test to see if bullet mismatch starts a new list

```````````````````````````````` example Bullet Mismatch Starts a New List: 1
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
````````````````````````````````



## Ordered List Item Sets List Start

Test to see if ordered list item will set list start if not one

```````````````````````````````` example Ordered List Item Sets List Start: 1
2. Non One Start Item
.
<ol>
    <li>Non One Start Item</li>
</ol>
.
````````````````````````````````



## Mismatched List Item Type Handling

Test how mismatches in item types are handled

```````````````````````````````` example Mismatched List Item Type Handling: 1
- Bullet List
1. With Ordered Item
.
<ul>
    <li>Bullet List</li>
    <li>With Ordered Item</li>
</ul>
.
````````````````````````````````



```````````````````````````````` example Mismatched List Item Type Handling: 2
1. Ordered Item
- With Bullet List
.
<ol>
    <li>Ordered Item</li>
    <li>With Bullet List</li>
</ol>
.
````````````````````````````````



## Loose Item Handling

Tests how all tight items are generated

```````````````````````````````` example Loose Item Handling: 1
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
````````````````````````````````


Test to see how trailing blank after item determines looseness 

```````````````````````````````` example Loose Item Handling: 2
- item 1

- item 2 
- item 3 
- item 4 
.
.
````````````````````````````````



```````````````````````````````` example Loose Item Handling: 3
- item 1
- item 2 

- item 3 
- item 4 
.
.
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 3
- item 1
- item 2 
- item 3 

- item 4 
.
.
````````````````````````````````


Test looseness with child items

```````````````````````````````` example Loose Item Handling: 5
- item 1
    - item 1.1
- item 2 
    - item 2.1 
- item 3 
    - item 3.1 
- item 4 
    - item 4.1 
.
.
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 6
- item 1

    - item 1.1
- item 2 
    - item 2.1 
- item 3 
    - item 3.1 
- item 4 
    - item 4.1 
.
.
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 7
- item 1
    - item 1.1

- item 2 
    - item 2.1 
- item 3 
    - item 3.1 
- item 4 
    - item 4.1 
.
.
````````````````````````````````



```````````````````````````````` example Loose Item Handling: 8
- item 1
    - item 1.1
- item 2 

    - item 2.1 
- item 3 
    - item 3.1 
- item 4 
    - item 4.1 
.
.
````````````````````````````````



```````````````````````````````` example Loose Item Handling: 9
- item 1
    - item 1.1
- item 2 
    - item 2.1 

- item 3 
    - item 3.1 
- item 4 
    - item 4.1 
.
.
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 9
- item 1
    - item 1.1
- item 2 
    - item 2.1 
- item 3 

    - item 3.1 
- item 4 
    - item 4.1 
.
.
````````````````````````````````



```````````````````````````````` example Loose Item Handling: 10
- item 1
    - item 1.1
- item 2 
    - item 2.1 
- item 3 
    - item 3.1 

- item 4 
    - item 4.1 
.
.
````````````````````````````````


```````````````````````````````` example Loose Item Handling: 10
- item 1
    - item 1.1
- item 2 
    - item 2.1 
- item 3 
    - item 3.1 
- item 4 

    - item 4.1 
.
.
````````````````````````````````



## List Item Interrupts Paragraph

Test to see which list items can interrupt paragraphs.   

```````````````````````````````` example List Item Interrupts Paragraph: 1
Bullet item can interrupt paragraph
* item

Empty bullet item can interrupt paragraph
* 

Numbered one item can interrupt paragraph
1. one item

Empty Numbered one item can interrupt paragraph
1. 

Numbered non-one item can interrupt paragraph
2. non-one item

Empty Numbered non-one item can interrupt paragraph
2. 

.
.
````````````````````````````````



Test to see which list items can interrupt another bullet list item's paragraphs

```````````````````````````````` example List Item Interrupts Paragraph: 2
* Bullet item can interrupt paragraph of a bullet list item
* item

<!--List Break-->

* Empty bullet item can interrupt paragraph of a bullet list item
* 

<!--List Break-->

* Numbered one item can interrupt paragraph of a bullet list item
1. one item

<!--List Break-->

* Empty Numbered one item can interrupt paragraph of a bullet list item
1. 

<!--List Break-->

* Numbered non-one item can interrupt paragraph of a bullet list item
2. non-one item

<!--List Break-->

* Empty Numbered non-one item can interrupt paragraph of a bullet list item
2. 

.
.
````````````````````````````````



Test to see which list items can interrupt another numbered list item's paragraphs

```````````````````````````````` example List Item Interrupts Paragraph: 3
1. Bullet item can interrupt paragraph of a numbered list item
* item

<!--List Break-->

1. Empty bullet item can interrupt paragraph of a numbered list item
* 

<!--List Break-->

1. Numbered one item can interrupt paragraph of a numbered list item
1. one item

<!--List Break-->

1. Empty Numbered one item can interrupt paragraph of a numbered list item 
1.

<!--List Break-->

1. Numbered non-one item can interrupt paragraph of a numbered list item
2. non-one item

<!--List Break-->

1. Empty Numbered non-one item can interrupt paragraph of a numbered list item
2. 

.
.
````````````````````````````````



## List Item Indent Handling

Test if list item indent handling for edge cases

```````````````````````````````` example List Item Indent Handling: 1
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
  <li>item 4</li>
  <li>item 5</li>
  <li>item 6</li>
  <li>item 7</li>
  <li>item 8</li>
  <li>item 9</li>
</ul>
.
Document[0, 117]
  BulletList[0, 117] isTight
    BulletListItem[0, 9] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[10, 19] open:[10, 11, "*"] isTight
      Paragraph[12, 19]
        Text[12, 18] chars:[12, 18, "item 2"]
    BulletListItem[21, 30] open:[21, 22, "*"] isTight
      Paragraph[23, 30]
        Text[23, 29] chars:[23, 29, "item 3"]
    BulletListItem[33, 42] open:[33, 34, "*"] isTight
      Paragraph[35, 42]
        Text[35, 41] chars:[35, 41, "item 4"]
    BulletListItem[46, 55] open:[46, 47, "*"] isTight
      Paragraph[48, 55]
        Text[48, 54] chars:[48, 54, "item 5"]
    BulletListItem[60, 69] open:[60, 61, "*"] isTight
      Paragraph[62, 69]
        Text[62, 68] chars:[62, 68, "item 6"]
    BulletListItem[75, 84] open:[75, 76, "*"] isTight
      Paragraph[77, 84]
        Text[77, 83] chars:[77, 83, "item 7"]
    BulletListItem[91, 100] open:[91, 92, "*"] isTight
      Paragraph[93, 100]
        Text[93, 99] chars:[93, 99, "item 8"]
    BulletListItem[108, 117] open:[108, 109, "*"] isTight
      Paragraph[110, 117]
        Text[110, 116] chars:[110, 116, "item 9"]
````````````````````````````````



```````````````````````````````` example List Item Indent Handling: 2
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
  <li>item 4</li>
  <li>item 5</li>
  <li>item 6</li>
  <li>item 7</li>
  <li>item 8</li>
  <li>item 9</li>
</ul>
.
Document[0, 117]
  BulletList[0, 117] isTight
    BulletListItem[0, 9] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[10, 19] open:[10, 11, "*"] isTight
      Paragraph[12, 19]
        Text[12, 18] chars:[12, 18, "item 2"]
    BulletListItem[21, 30] open:[21, 22, "*"] isTight
      Paragraph[23, 30]
        Text[23, 29] chars:[23, 29, "item 3"]
    BulletListItem[33, 42] open:[33, 34, "*"] isTight
      Paragraph[35, 42]
        Text[35, 41] chars:[35, 41, "item 4"]
    BulletListItem[46, 55] open:[46, 47, "*"] isTight
      Paragraph[48, 55]
        Text[48, 54] chars:[48, 54, "item 5"]
    BulletListItem[60, 69] open:[60, 61, "*"] isTight
      Paragraph[62, 69]
        Text[62, 68] chars:[62, 68, "item 6"]
    BulletListItem[75, 84] open:[75, 76, "*"] isTight
      Paragraph[77, 84]
        Text[77, 83] chars:[77, 83, "item 7"]
    BulletListItem[91, 100] open:[91, 92, "*"] isTight
      Paragraph[93, 100]
        Text[93, 99] chars:[93, 99, "item 8"]
    BulletListItem[108, 117] open:[108, 109, "*"] isTight
      Paragraph[110, 117]
        Text[110, 116] chars:[110, 116, "item 9"]
````````````````````````````````



```````````````````````````````` example List Item Indent Handling: 3
* item 1
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
  <li>item 4</li>
  <li>item 5</li>
  <li>item 6</li>
  <li>item 7</li>
  <li>item 8</li>
  <li>item 9</li>
</ul>
.
Document[0, 125]
  BulletList[0, 125] isTight
    BulletListItem[0, 9] open:[0, 1, "*"] isTight
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
    BulletListItem[10, 20] open:[10, 11, "*"] isTight
      Paragraph[13, 20]
        Text[13, 19] chars:[13, 19, "item 2"]
    BulletListItem[22, 32] open:[22, 23, "*"] isTight
      Paragraph[25, 32]
        Text[25, 31] chars:[25, 31, "item 3"]
    BulletListItem[35, 45] open:[35, 36, "*"] isTight
      Paragraph[38, 45]
        Text[38, 44] chars:[38, 44, "item 4"]
    BulletListItem[49, 59] open:[49, 50, "*"] isTight
      Paragraph[52, 59]
        Text[52, 58] chars:[52, 58, "item 5"]
    BulletListItem[64, 74] open:[64, 65, "*"] isTight
      Paragraph[67, 74]
        Text[67, 73] chars:[67, 73, "item 6"]
    BulletListItem[80, 90] open:[80, 81, "*"] isTight
      Paragraph[83, 90]
        Text[83, 89] chars:[83, 89, "item 7"]
    BulletListItem[97, 107] open:[97, 98, "*"] isTight
      Paragraph[100, 107]
        Text[100, 106] chars:[100, 106, "item 8"]
    BulletListItem[115, 125] open:[115, 116, "*"] isTight
      Paragraph[118, 125]
        Text[118, 124] chars:[118, 124, "item 9"]
````````````````````````````````



Test shows where the boundary switch to indented code occurs. Sub-items first paragraph is a
paragraph, the second is indented code

```````````````````````````````` example List Item Indent Handling: 4
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
      <li>
        <p>sub item</p>
        <p>sub item child para</p>
        <pre><code>indented code
</code></pre>
      </li>
    </ul>
  </li>
</ul>
<hr />
<ol>
  <li>test
    <ol>
      <li>
        <p>sub item</p>
        <p>sub item child para</p>
        <pre><code>indented code
</code></pre>
      </li>
    </ol>
  </li>
</ol>
.
Document[0, 167]
  BulletList[0, 79] isTight
    BulletListItem[0, 79] open:[0, 1, "-"] isTight
      Paragraph[4, 9]
        Text[4, 8] chars:[4, 8, "test"]
      BulletList[13, 79] isLoose
        BulletListItem[13, 79] open:[13, 14, "-"] isLoose
          Paragraph[15, 24]
            Text[15, 23] chars:[15, 23, "sub item"]
          Paragraph[34, 54]
            Text[34, 53] chars:[34, 53, "sub i …  para"]
          IndentedCodeBlock[65, 79]
  ThematicBreak[79, 82]
  OrderedList[84, 166] isTight delimiter:'.'
    OrderedListItem[84, 166] open:[84, 86, "1."] isTight
      Paragraph[88, 93]
        Text[88, 92] chars:[88, 92, "test"]
      OrderedList[97, 166] isLoose delimiter:'.'
        OrderedListItem[97, 166] open:[97, 99, "1."] isLoose
          Paragraph[100, 109]
            Text[100, 108] chars:[100, 108, "sub item"]
          Paragraph[120, 140]
            Text[120, 139] chars:[120, 139, "sub i …  para"]
          IndentedCodeBlock[152, 166]
````````````````````````````````



More extensive test to show where the boundary switch to indented code occurs. Sub-items first
paragraph is a paragraph, the second is indented code

```````````````````````````````` example List Item Indent Handling: 5
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
  <li>
    <p>item 1</p>
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
    <p>item 2</p>
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
    <p>item para 4</p>
    <pre><code>item para 5

 item para 6

  item para 7
</code></pre>
  </li>
  <li>
    <p>item 5</p>
    <p>item para 1</p>
    <p>item para 2</p>
    <p>item para 3</p>
    <p>item para 4</p>
    <pre><code>item para 5

 item para 6

  item para 7
</code></pre>
  </li>
</ul>
.
Document[0, 982]
  BulletList[0, 982] isLoose
    BulletListItem[0, 166] open:[0, 1, "*"] isLoose
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      Paragraph[16, 28]
        Text[16, 27] chars:[16, 27, "item  … ara 1"]
      Paragraph[34, 46]
        Text[34, 45] chars:[34, 45, "item  … ara 2"]
      Paragraph[54, 66]
        Text[54, 65] chars:[54, 65, "item  … ara 3"]
      Paragraph[76, 88]
        Text[76, 87] chars:[76, 87, "item  … ara 4"]
      IndentedCodeBlock[100, 166]
    BulletListItem[176, 355] open:[176, 177, "*"] isLoose
      Paragraph[178, 185]
        Text[178, 184] chars:[178, 184, "item 2"]
      Paragraph[193, 205]
        Text[193, 204] chars:[193, 204, "item  … ara 1"]
      Paragraph[213, 225]
        Text[213, 224] chars:[213, 224, "item  … ara 2"]
      Paragraph[235, 247]
        Text[235, 246] chars:[235, 246, "item  … ara 3"]
      Paragraph[259, 271]
        Text[259, 270] chars:[259, 270, "item  … ara 4"]
      IndentedCodeBlock[285, 355]
    BulletListItem[366, 558] open:[366, 367, "*"] isLoose
      Paragraph[368, 375]
        Text[368, 374] chars:[368, 374, "item 3"]
      Paragraph[384, 396]
        Text[384, 395] chars:[384, 395, "item  … ara 1"]
      Paragraph[406, 418]
        Text[406, 417] chars:[406, 417, "item  … ara 2"]
      Paragraph[430, 442]
        Text[430, 441] chars:[430, 441, "item  … ara 3"]
      Paragraph[456, 468]
        Text[456, 467] chars:[456, 467, "item  … ara 4"]
      IndentedCodeBlock[484, 558]
    BulletListItem[570, 775] open:[570, 571, "*"] isLoose
      Paragraph[572, 579]
        Text[572, 578] chars:[572, 578, "item 4"]
      Paragraph[589, 601]
        Text[589, 600] chars:[589, 600, "item  … ara 1"]
      Paragraph[613, 625]
        Text[613, 624] chars:[613, 624, "item  … ara 2"]
      Paragraph[639, 651]
        Text[639, 650] chars:[639, 650, "item  … ara 3"]
      Paragraph[667, 679]
        Text[667, 678] chars:[667, 678, "item  … ara 4"]
      IndentedCodeBlock[697, 775]
    BulletListItem[788, 982] open:[788, 789, "*"] isLoose
      Paragraph[790, 797]
        Text[790, 796] chars:[790, 796, "item 5"]
      Paragraph[808, 820]
        Text[808, 819] chars:[808, 819, "item  … ara 1"]
      Paragraph[830, 842]
        Text[830, 841] chars:[830, 841, "item  … ara 2"]
      Paragraph[854, 866]
        Text[854, 865] chars:[854, 865, "item  … ara 3"]
      Paragraph[880, 892]
        Text[880, 891] chars:[880, 891, "item  … ara 4"]
      IndentedCodeBlock[908, 982]
````````````````````````````````



Test for how items with indent > first list item's indent but < previous item's content indent
are handled. Mainly, if they are handled in a weird way of treating the item as a sub-item of
the previous list item. There was one that did it that way, GitHub comments if I can remember
right, but now they switched to commonmark list handling with mods. Guess it is now GFC--GitHub
Flavoured Commonmark.

```````````````````````````````` example List Item Indent Handling: 6
*  item 1
   * item 2
  * item 3
.
<ul>
  <li>item 1
    <ul>
      <li>item 2</li>
    </ul>
  </li>
  <li>item 3</li>
</ul>
.
Document[0, 33]
  BulletList[0, 33] isTight
    BulletListItem[0, 22] open:[0, 1, "*"] isTight
      Paragraph[3, 10]
        Text[3, 9] chars:[3, 9, "item 1"]
      BulletList[13, 22] isTight
        BulletListItem[13, 22] open:[13, 14, "*"] isTight
          Paragraph[15, 22]
            Text[15, 21] chars:[15, 21, "item 2"]
    BulletListItem[24, 33] open:[24, 25, "*"] isTight
      Paragraph[26, 33]
        Text[26, 32] chars:[26, 32, "item 3"]
````````````````````````````````



Test how headings in list items are handled, leading space allowed or not

```````````````````````````````` example List Item Indent Handling: 7
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
  <li>
    <p>item 1</p>
    <h1>Heading 1</h1>
    <h2>Heading 2</h2>
    <h3>Heading 3</h3>
    <h4>Heading 4</h4>
    <pre><code>##### Heading 5

 ###### Heading 6
</code></pre>
    <ul>
      <li>
        <p>item 2</p>
        <h1>Heading 1</h1>
        <h2>Heading 2</h2>
        <h3>Heading 3</h3>
        <h4>Heading 3</h4>
        <pre><code>##### Heading 5

 ###### Heading 6
</code></pre>
      </li>
    </ul>
  </li>
</ul>
.
Document[0, 296]
  BulletList[0, 295] isLoose
    BulletListItem[0, 295] open:[0, 1, "*"] isLoose
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      Heading[12, 23] textOpen:[12, 13, "#"] text:[14, 23, "Heading 1"]
        Text[14, 23] chars:[14, 23, "Heading 1"]
      Heading[28, 40] textOpen:[28, 30, "##"] text:[31, 40, "Heading 2"]
        Text[31, 40] chars:[31, 40, "Heading 2"]
      Heading[46, 59] textOpen:[46, 49, "###"] text:[50, 59, "Heading 3"]
        Text[50, 59] chars:[50, 59, "Heading 3"]
      Heading[66, 80] textOpen:[66, 70, "####"] text:[71, 80, "Heading 4"]
        Text[71, 80] chars:[71, 80, "Heading 4"]
      IndentedCodeBlock[88, 129]
      BulletList[132, 295] isLoose
        BulletListItem[132, 295] open:[132, 133, "*"] isLoose
          Paragraph[134, 141]
            Text[134, 140] chars:[134, 140, "item 2"]
          Heading[146, 157] textOpen:[146, 147, "#"] text:[148, 157, "Heading 1"]
            Text[148, 157] chars:[148, 157, "Heading 1"]
          Heading[168, 180] textOpen:[168, 170, "##"] text:[171, 180, "Heading 2"]
            Text[171, 180] chars:[171, 180, "Heading 2"]
          Heading[192, 205] textOpen:[192, 195, "###"] text:[196, 205, "Heading 3"]
            Text[196, 205] chars:[196, 205, "Heading 3"]
          Heading[218, 232] textOpen:[218, 222, "####"] text:[223, 232, "Heading 3"]
            Text[223, 232] chars:[223, 232, "Heading 3"]
          IndentedCodeBlock[246, 295]
````````````````````````````````



