---
title: Parser Compatibility Spec
author: Vladimir Schneider
version: 0.1
date: '2016-12-03'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

Extra Tests for various processor compatibility flags for list processing where most of the
differences are to be found.

### Default

Default processing for commonmark here as reference for the rest

Test to see which list items can interrupt paragraphs.   

- MultiMarkdown: none
- CommonMark: non-empty bullets, non-empty one item 
- Pandoc: none
- Kramdown: none
- Markdown: none
- GFM: bullets 
- GFC: non-empty bullets, non-empty one item
- Php Markdown Extra: none
- League/CommonMark: all

```````````````````````````````` example Default: 1
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

- Markdown: non-empty bullets, non-empty numbered item
- MultiMarkdown: all
- CommonMark: all, mismatch to new list 
- League/CommonMark: all, mismatch to new list
- Pandoc: all, mismatch to new list
- Php Markdown Extra: all, mismatch to new list
- Kramdown: all, mismatch to sub-list
- GFM: all
- GFC: all, mismatch to new list


```````````````````````````````` example Default: 2
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

- Markdown: non-empty bullets, non-empty numbered item
- MultiMarkdown: all
- CommonMark: all, mismatch to new list 
- League/CommonMark: all, mismatch to new list
- Pandoc: all, mismatch to new list
- Php Markdown Extra: all, mismatch to new list
- Kramdown: all, mismatch to sub-list
- GFM: all
- GFC: all, mismatch to new list

```````````````````````````````` example Default: 3
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


Test to see if bullet mismatch starts a new list

- Markdown: no
- MultiMarkdown: no
- CommonMark: yes
- League/CommonMark: no
- Pandoc: no
- Php Markdown Extra: no
- Kramdown: no
- GFM: no
- GFC: yes

```````````````````````````````` example Default: 3
- item
+ item
* item

.
.
````````````````````````````````



Test if list item indent handling is purely previous item content based or depends on list's
first item's indent level. If the

```````````````````````````````` example Default: 4
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



```````````````````````````````` example Default: 5
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



Test to confirm if list item indent handling is purely previous item content based or depends on
list's first item's indent level. If the

```````````````````````````````` example Default: 6
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

```````````````````````````````` example Default: 7
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

```````````````````````````````` example Default: 8
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

```````````````````````````````` example Default: 9
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

```````````````````````````````` example Default: 10
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



## Lists - CommonMark - GitHub Comments

new GitHub commonmark comments

```````````````````````````````` example Lists - CommonMark - GitHub Comments: 1
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



Test to confirm if list item indent handling is purely previous item content based or depends on
list's first item's indent level. If the

```````````````````````````````` example Lists - CommonMark - GitHub Comments: 2
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

```````````````````````````````` example Lists - CommonMark - GitHub Comments: 3
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

```````````````````````````````` example Lists - CommonMark - GitHub Comments: 4
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

```````````````````````````````` example Lists - CommonMark - GitHub Comments: 5
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

```````````````````````````````` example Lists - CommonMark - GitHub Comments: 6
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



## Lists - First Item Indent Based SubList - Markdown

Indent less than list's first item indent + 4 is a list item, >= is a sub item

- showdown 0.3.1
- Markdown.pl 1.0.1
- Markdown.pl 1.0.2b8
- RedCarpet 3.1.2
- PHP Markdown 1.0.2
- PHP Markdown Extra 1.2.8
- Parsedown 1.6.0
- s9e\TextFormatter (Fatdown/PHP)

```````````````````````````````` example(Lists - First Item Indent Based SubList - Markdown: 1) options(list-first-item-indent-based-content-indent, lists-not-item-indent-relative-to-last-item)
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
    <li>item 1
        <ul>
            <li>item 2</li>
            <li>item 3</li>
            <li>item 4</li>
            <li>item 5
                <ul>
                    <li>item 6</li>
                    <li>item 7</li>
                    <li>item 8</li>
                    <li>item 9</li>
                </ul>
            </li>
        </ul>
    </li>
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



Test to confirm if list item indent handling is purely previous item content based or depends on
list's first item's indent level. If the

```````````````````````````````` example(Lists - First Item Indent Based SubList - Markdown: 2) options(list-first-item-indent-based-content-indent, lists-not-item-indent-relative-to-last-item)
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
    <li>item 1
        <ul>
            <li>item 2</li>
            <li>item 3</li>
            <li>item 4</li>
            <li>item 5
                <ul>
                    <li>item 6</li>
                    <li>item 7</li>
                    <li>item 8</li>
                    <li>item 9</li>
                </ul>
            </li>
        </ul>
    </li>
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

```````````````````````````````` example(Lists - First Item Indent Based SubList - Markdown: 3) options(list-first-item-indent-based-content-indent, lists-not-item-indent-relative-to-last-item)
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
    <li>
        <p>test</p>
        <ul>
            <li>
                <p>sub item</p>
                <p>sub item child para</p>
            </li>
        </ul>
    </li>
</ul>

<h2>          indented code</h2>

<ol>
    <li>
        <p>test</p>
        <ol>
            <li>
                <p>sub item</p>
                <p>sub item child para</p>
                <p>indented code</p>
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

```````````````````````````````` example(Lists - First Item Indent Based SubList - Markdown: 4) options(list-first-item-indent-based-content-indent, lists-not-item-indent-relative-to-last-item)
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
        <p>item para 5</p>
        <p>item para 6</p>
<pre><code>item para 7
</code></pre>

        <ul>
            <li>item 2</li>
        </ul>
        <p>item para 1</p>
        <p>item para 2</p>
        <p>item para 3</p>
        <p>item para 4</p>
        <p>item para 5</p>
<pre><code>item para 6


 item para 7
</code></pre>

        <ul>
            <li>item 3</li>
        </ul>
        <p>item para 1</p>
        <p>item para 2</p>
        <p>item para 3</p>
        <p>item para 4</p>
<pre><code>item para 5


 item para 6


  item para 7
</code></pre>

        <ul>
            <li>
                <p>item 4</p>
                <p>item para 1</p>
                <p>item para 2</p>
                <p>item para 3</p>
                <p>item para 4</p>
                <p>item para 5</p>
                <p>item para 6</p>
                <p>item para 7</p>
            </li>
            <li>
                <p>item 5</p>
                <p>item para 1</p>
                <p>item para 2</p>
                <p>item para 3</p>
                <p>item para 4</p>
                <p>item para 5</p>
                <p>item para 6</p>
<pre><code>item para 7
</code></pre>
            </li>
        </ul>
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

```````````````````````````````` example(Lists - First Item Indent Based SubList - Markdown: 5) options(list-first-item-indent-based-content-indent, lists-not-item-indent-relative-to-last-item)
*  item 1
   * item 2
  * item 3
.
<ul>
    <li>item 1
        <ul>
            <li>item 2</li>
            <li>item 3</li>
        </ul>
    </li>
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

```````````````````````````````` example(Lists - First Item Indent Based SubList - Markdown: 6) options(list-first-item-indent-based-content-indent, lists-not-item-indent-relative-to-last-item)
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
        <p>#### Heading 4</p>
        <p>##### Heading 5</p>
        <p>###### Heading 6</p>
        <ul>
            <li>item 2</li>
        </ul>
        
<h1>Heading 1</h1>
        <p>## Heading 2</p>
        <p>### Heading 3</p>
        <p>#### Heading 3</p>
        <p><pre><code>##### Heading 5</p>
<p>###### Heading 6
</code></pre>
    </li>
</ul>
</p>
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



## Lists - Fixed 4 Indent - MultiMarkdown

Fixed indentation only (4 spaces)

- pandoc (strict) 1.17.2
- pandoc 1.17.2
- lunamark 0.4.0
- RDiscount 2.1.7
- Python-Markdown 2.6.5
- Minima 0.8.0a3_20140907
- MultiMarkdown 5.1.0
- pegdown

```````````````````````````````` example(Lists - Fixed 4 Indent - MultiMarkdown: 1) options(list-fixed-indent, lists-not-item-indent-relative-to-last-item)
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



Test to confirm if list item indent handling is purely previous item content based or depends on
list's first item's indent level. If the

```````````````````````````````` example(Lists - Fixed 4 Indent - MultiMarkdown: 2) options(list-fixed-indent, lists-not-item-indent-relative-to-last-item)
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

```````````````````````````````` example(Lists - Fixed 4 Indent - MultiMarkdown: 3) options(list-fixed-indent, lists-not-item-indent-relative-to-last-item)
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
            </li>
        </ul>
    </li>
</ul>
<pre><code>      indented code
</code></pre>

<hr />
<ol>
    <li>test
        <ol>
            <li>
                <p>sub item</p>
                <p>sub item child para</p>
                <p>indented code</p>
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

```````````````````````````````` example(Lists - Fixed 4 Indent - MultiMarkdown: 4) options(list-fixed-indent, lists-not-item-indent-relative-to-last-item)
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

```````````````````````````````` example(Lists - Fixed 4 Indent - MultiMarkdown: 5) options(list-fixed-indent, lists-not-item-indent-relative-to-last-item)
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
Document[0, 33]
  BulletList[0, 33] isTight
    BulletListItem[0, 10] open:[0, 1, "*"] isTight
      Paragraph[3, 10]
        Text[3, 9] chars:[3, 9, "item 1"]
    BulletListItem[13, 22] open:[13, 14, "*"] isTight
      Paragraph[15, 22]
        Text[15, 21] chars:[15, 21, "item 2"]
    BulletListItem[24, 33] open:[24, 25, "*"] isTight
      Paragraph[26, 33]
        Text[26, 32] chars:[26, 32, "item 3"]
````````````````````````````````



Test how headings in list items are handled, leading space allowed or not

```````````````````````````````` example(Lists - Fixed 4 Indent - MultiMarkdown: 6) options(list-fixed-indent)
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
  <li>item 1</li>
</ul>
<h1>Heading 1</h1>
<h2>Heading 2</h2>
<pre><code>### Heading 3

 #### Heading 4

  ##### Heading 5

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



### List - First Item Indent Based Limit - kramdown

List content indent option makes list processing depend on the first item's indent. Mainly, if
the potential list item's indent >= (list first item indent) + (list first item indent offset)
then it is treated as lazy continuation of the previous item. Otherwise, it is precessed

Default offset is 4

kramdown 1.2.0

```````````````````````````````` example(List - First Item Indent Based Limit - kramdown: 1) options(kramdown)
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
  * item 5
    <ul>
      <li>item 6</li>
      <li>item 7</li>
      <li>item 8</li>
      <li>item 9</li>
    </ul>
  </li>
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
    BulletListItem[33, 117] open:[33, 34, "*"] isTight
      Paragraph[35, 55]
        Text[35, 41] chars:[35, 41, "item 4"]
        SoftLineBreak[41, 42]
        Text[46, 54] chars:[46, 54, "* item 5"]
      BulletList[60, 117] isTight
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



```````````````````````````````` example List - First Item Indent Based Limit - kramdown: 2
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
        <p>item 1</p>
    </li>
    <li>
        <p>item 2</p>
    </li>
    <li>
        <p>item 3</p>
    </li>
    <li>
        <p>item 4</p>
    </li>
</ul>
<pre><code>* item 5

 * item 6
 
  * item 7
  
   * item 8
   
    * item 9
</code></pre>
.
````````````````````````````````


Test to confirm if list item indent handling is purely previous item content based or depends on
list's first item's indent level. If the

```````````````````````````````` example(List - First Item Indent Based Limit - kramdown: 3) options(kramdown)
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
  <li>item 4
  *  item 5
  *  item 6
    <ul>
      <li>item 7</li>
      <li>item 8</li>
      <li>item 9</li>
    </ul>
  </li>
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
    BulletListItem[35, 125] open:[35, 36, "*"] isTight
      Paragraph[38, 74]
        Text[38, 44] chars:[38, 44, "item 4"]
        SoftLineBreak[44, 45]
        Text[49, 58] chars:[49, 58, "*  item 5"]
        SoftLineBreak[58, 59]
        Text[64, 73] chars:[64, 73, "*  item 6"]
      BulletList[80, 125] isTight
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

```````````````````````````````` example(List - First Item Indent Based Limit - kramdown: 4) options(kramdown)
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

```````````````````````````````` example(List - First Item Indent Based Limit - kramdown: 5) options(kramdown)
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
</ul>
<pre><code>* item 5

  item para 1

   item para 2

    item para 3

     item para 4
 
      item para 5
  
       item para 6
   
        item para 7
</code></pre>
.
Document[0, 982]
  BulletList[0, 775] isLoose
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
  IndentedCodeBlock[788, 982]
````````````````````````````````



Test for how items with indent > first list item's indent but < previous item's content indent
are handled. Mainly, if they are handled in a weird way of treating the item as a sub-item of
the previous list item. There was one that did it that way, GitHub comments if I can remember
right, but now they switched to commonmark list handling with mods. Guess it is now GFC--GitHub
Flavoured Commonmark.

```````````````````````````````` example(List - First Item Indent Based Limit - kramdown: 6) options(kramdown)
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

```````````````````````````````` example(List - First Item Indent Based Limit - kramdown: 7) options(kramdown)
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
    <p>## Heading 2</p>
    <p>### Heading 3</p>
    <p>#### Heading 4</p>
    <pre><code>##### Heading 5

 ###### Heading 6
</code></pre>
    <ul>
      <li>
        <p>item 2</p>
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
Document[0, 296]
  BulletList[0, 295] isLoose
    BulletListItem[0, 295] open:[0, 1, "*"] isLoose
      Paragraph[2, 9]
        Text[2, 8] chars:[2, 8, "item 1"]
      Heading[12, 23] textOpen:[12, 13, "#"] text:[14, 23, "Heading 1"]
        Text[14, 23] chars:[14, 23, "Heading 1"]
      Paragraph[28, 41]
        Text[28, 40] chars:[28, 40, "## He … ing 2"]
      Paragraph[46, 60]
        Text[46, 59] chars:[46, 59, "### H … ing 3"]
      Paragraph[66, 81]
        Text[66, 80] chars:[66, 80, "####  … ing 4"]
      IndentedCodeBlock[88, 129]
      BulletList[132, 295] isLoose
        BulletListItem[132, 295] open:[132, 133, "*"] isLoose
          Paragraph[134, 141]
            Text[134, 140] chars:[134, 140, "item 2"]
          Heading[146, 157] textOpen:[146, 147, "#"] text:[148, 157, "Heading 1"]
            Text[148, 157] chars:[148, 157, "Heading 1"]
          Paragraph[168, 181]
            Text[168, 180] chars:[168, 180, "## He … ing 2"]
          Paragraph[192, 206]
            Text[192, 205] chars:[192, 205, "### H … ing 3"]
          Paragraph[218, 233]
            Text[218, 232] chars:[218, 232, "####  … ing 3"]
          IndentedCodeBlock[246, 295]
````````````````````````````````



## Lists - Markdown Navigator

Embedded headings Markdown Navigator options

```````````````````````````````` example(Lists - Markdown Navigator: 1) options(list-markdown-navigator)
1. Some Lists
    
    # Test

.
<ol>
  <li>Some Lists
  <h1>Test</h1>
  </li>
</ol>
.
Document[0, 31]
  OrderedList[0, 29] isTight delimiter:'.'
    OrderedListItem[0, 29] open:[0, 2, "1."] isTight
      Paragraph[3, 14]
        Text[3, 13] chars:[3, 13, "Some Lists"]
      Heading[23, 29] textOpen:[23, 24, "#"] text:[25, 29, "Test"]
        Text[25, 29] chars:[25, 29, "Test"]
````````````````````````````````



