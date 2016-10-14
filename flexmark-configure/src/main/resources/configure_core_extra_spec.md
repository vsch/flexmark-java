---
title: Profile Test Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

# Profile Test Cases

Collection of markdown samples which can be used to determine the markdown parser compatibility
profile.

## Lists

List processing has the greatest number of differences in parsers.

### List Indentation

#### Bullet List - Indentation of Sub Lists

##### Commonmark

leading space indentation

```````````````````````````````` example Commonmark: 1  options()
- Item 1
 - Item 2
  - Item 3
   - Item 4
    - Item 5
     - Item 6
      - Item 7
       - Item 8
        - Item 9
.
<ul>
  <li>Item 1</li>
  <li>Item 2</li>
  <li>Item 3</li>
  <li>Item 4</li>
  <li>Item 5</li>
  <li>Item 6</li>
  <li>Item 7</li>
  <li>Item 8</li>
  <li>Item 9</li>
</ul>
````````````````````````````````


sub-lists start when leading indent > parent leading indent

```````````````````````````````` example(Commonmark: 2) options(list-more-than-parent-sublist)
- Item 1
 - Item 2
  - Item 3
   - Item 4
    - Item 5
     - Item 6
      - Item 7
       - Item 8
        - Item 9
.
<ul>
  <li>Item 1</li>
  <ul>
    <li>Item 2</li>
    <li>Item 3</li>
    <ul>
      <li>Item 4</li>
      <li>Item 5</li>
      <ul>
        <li>Item 6</li>
        <li>Item 7</li>
        <ul>
          <li>Item 8</li>
          <li>Item 9</li>
        </ul>
      </ul>
    </ul>
  </ul>
</ul>
````````````````````````````````


fixed indent, 0-3 no indent, 4-7 sub item, etc

```````````````````````````````` example(Commonmark: 3) options(list-fixed-indent)
- Item 1
 - Item 2
  - Item 3
   - Item 4
    - Item 5
     - Item 6
      - Item 7
       - Item 8
        - Item 9
.
<ul>
    <li>Item 1</li>
    <li>Item 2</li>
    <li>Item 3</li>
    <li>Item 4
        <ul>
            <li>Item 5</li>
            <li>Item 6</li>
            <li>Item 7</li>
            <li>Item 8
                <ul>
                    <li>Item 9</li>
                </ul>
            </li>
        </ul>
    </li>
</ul>
````````````````````````````````


leading sub lists align on parent text

```````````````````````````````` example Commonmark: 4
- Item 1
  - Item 2
    - Item 3
      - Item 4
.
<ul>
  <li>Item 0</li>
  <ul>
    <li>Item 1</li>
    <ul>
      <li>Item 2</li>
      <ul>
        <li>Item 3</li>
        <ul>
          <li>Item 4</li>
        </ul>
      </ul>
    </ul>
  </ul>
</ul>
````````````````````````````````


sub-lists start when leading indent > parent leading indent

```````````````````````````````` example(Commonmark: 5) options(list-more-than-parent-sublist)
- Item 1
  - Item 2
    - Item 3
      - Item 4
.
<ul>
  <li>Item 0</li>
  <ul>
    <li>Item 1</li>
    <ul>
      <li>Item 2</li>
      <ul>
        <li>Item 3</li>
        <ul>
          <li>Item 4</li>
        </ul>
      </ul>
    </ul>
  </ul>
</ul>
````````````````````````````````


fixed indent sub lists

```````````````````````````````` example(Commonmark: 6) options(list-fixed-indent)
- Item 1
  - Item 2
    - Item 3
      - Item 4
.
<ul>
    <li>Item 1</li>
    <li>Item 2
        <ul>
            <li>Item 3</li>
            <li>Item 4</li>
        </ul>
    </li>
</ul>
````````````````````````````````


inverted leading indents

```````````````````````````````` example Commonmark: 7
   - Item 1
  - Item 2
 - Item 3
- Item 4
.
<ul>
  <li>Item 1</li>
  <li>Item 2</li>
  <li>Item 3</li>
  <li>Item 4</li>
</ul>
````````````````````````````````


inverted leading indents with sub-items

```````````````````````````````` example Commonmark: 8
   - Item 1
     - Item 2
  - Item 3
    - Item 4  
 - Item 5
   - Item 6
- Item 7
  - Item 8
.
<ul>
    <li>Item 1
        <ul>
            <li>Item 2</li>
        </ul>
    </li>
    <li>Item 3
        <ul>
            <li>Item 4</li>
        </ul>
    </li>
    <li>Item 5
        <ul>
            <li>Item 6</li>
        </ul>
    </li>
    <li>Item 7
        <ul>
            <li>Item 8</li>
        </ul>
    </li>
</ul>
````````````````````````````````


##### LISTS_SUBLIST_WHEN_INDENTED_FROM_PARENT 

leading sub lists align on parent text, also when > than parent indent

```````````````````````````````` example LISTS_SUBLIST_WHEN_INDENTED_FROM_PARENT: 1
- Item 1
  - Item 2
    - Item 3
      - Item 4
.
<ul>
  <li>Item 0</li>
  <ul>
    <li>Item 1</li>
    <ul>
      <li>Item 2</li>
      <ul>
        <li>Item 3</li>
        <ul>
          <li>Item 4</li>
        </ul>
      </ul>
    </ul>
  </ul>
</ul>
````````````````````````````````


inverted leading indents

```````````````````````````````` example LISTS_SUBLIST_WHEN_INDENTED_FROM_PARENT: 2
   - Item 1
  - Item 2
 - Item 3
- Item 4
.
<ul>
  <li>Item 1</li>
  <li>Item 2</li>
  <li>Item 3</li>
  <li>Item 4</li>
</ul>
````````````````````````````````


inverted leading indents with sub-items

```````````````````````````````` example LISTS_SUBLIST_WHEN_INDENTED_FROM_PARENT: 3
   - Item 1
     - Item 2
  - Item 3
    - Item 4  
 - Item 5
   - Item 6
- Item 7
  - Item 8
.
<ul>
    <li>Item 1
        <ul>
            <li>Item 2</li>
        </ul>
    </li>
    <li>Item 3
        <ul>
            <li>Item 4</li>
        </ul>
    </li>
    <li>Item 5
        <ul>
            <li>Item 6</li>
        </ul>
    </li>
    <li>Item 7
        <ul>
            <li>Item 8</li>
        </ul>
    </li>
</ul>
````````````````````````````````


### Numbered List Start Control

standard start 1

```````````````````````````````` example Numbered List Start Control: 1
1. Item 1
1. Item 2
.
<ol> 
   <li>Item 1</li>
   <li>Item 2</li>
</ol> 
````````````````````````````````


standard start 2

```````````````````````````````` example Numbered List Start Control: 2
2. Item 1
1. Item 2
1. Item 3
.
<ol start="2"> 
   <li>Item 1</li>
   <li>Item 2</li>
</ol> 
````````````````````````````````


no manual start

```````````````````````````````` example(Numbered List Start Control: 3) options(list-no-start)
2. Item 1
1. Item 2
.
<ol> 
   <li>Item 1</li>
   <li>Item 2</li>
</ol> 
````````````````````````````````


leading space indentation commonmark

```````````````````````````````` example Numbered List Start Control: 4
1. Item 1
 1. Item 2
  1. Item 3
   1. Item 4
    1. Item 5
     1. Item 6
      1. Item 7
       1. Item 8
        1. Item 9
.
<ol>
  <li>Item 1</li>
  <li>Item 2</li>
  <li>Item 3</li>
  <li>Item 4</li>
  <li>Item 5</li>
  <li>Item 6</li>
  <li>Item 7</li>
  <li>Item 8</li>
  <li>Item 9</li>
</ol>
````````````````````````````````


leading sub lists align on parent text

```````````````````````````````` example Numbered List Start Control: 5
1. Item 1
   1. Item 2
      1. Item 3
         1. Item 4
.
<ol>
  <li>Item 0</li>
  <ol>
    <li>Item 1</li>
    <ol>
      <li>Item 2</li>
      <ol>
        <li>Item 3</li>
        <ol>
          <li>Item 4</li>
        </ol>
      </ol>
    </ol>
  </ol>
</ol>
````````````````````````````````


sub-lists start when leading indent > parent leading indent

```````````````````````````````` example Numbered List Start Control: 6
1. Item 1
 1. Item 2
  1. Item 3
   1. Item 4
    1. Item 5
     1. Item 6
      1. Item 7
       1. Item 8
        1. Item 9
.
<ul>
  <li>Item 1</li>
  <ul>
    <li>Item 2</li>
    <li>Item 3</li>
    <li>Item 4</li>
    <ul>
      <li>Item 5</li>
      <li>Item 6</li>
      <li>Item 7</li>
      <ul>
        <li>Item 8</li>
        <li>Item 9</li>
      </ul>
    </ul>
  </ul>
</ul>
````````````````````````````````


#### Handling of leading non-indent space

    :{1,3}{1,4}{$1,0}: Bullet Lists leading $1 spaces 
     {$3}* {$2}Item
    
    :{1,3}{1,4}{$1,0}{1,4}: Numbered Lists $1 spaces 
     {$3}$40{$3}. {$2}Item $40{$3}

#### Leading Space in List Child Items

    :{1,4}{1,4}{0,10}: Bullet Lists after marker $1 spaces 
     {$2}* {$1}Item $2
    \n
     {$2} {$3}$3 spaces
    
    :{1,4}{1,4}{0,10}{1,4}: Numbered Lists after marker $1 spaces 
     {$2}$4. {$1}Item $2
    \n
     {$2} {$3}$3 spaces

