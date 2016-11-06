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

### Numbered List Start Control

    :{1,10}: $1 Start
    $1\. Item 1
    
    profile(commonmark)
    iter($1={1})
    .
    <ol> 
       <li>Item 1</li>
    </ol> 
    .
    iter($1={2,10})
    .
    <ol start="$1"> 
       <li>Item 1</li>
    </ol> 
    .
    profile(gfm)
    .
    <ol> 
       <li>Item 1</li>
    </ol> 
    .
    
### List Indentation

#### Indentation of Sub Lists

    :{1,4}{0,9}: Bullet Lists
     {$2}* {$1}Item $2
     
    profile(commonmark) 
    <ul>
      <li>Item 0</li>
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
    
    profile(gfm) 
    <ul>
      <li>Item 0</li>
      <li>Item 1</li>
      <li>Item 2</li>
      <li>Item 3</li>
      <ul>
        <li>Item 4</li>
        <li>Item 5</li>
        <li>Item 6</li>
        <li>Item 7</li>
        <ul>
          <li>Item 8</li>
          <li>Item 9</li>
        </ul>
      </ul>
    </ul>
    
    :{1,4}{1,4}{0,9}: Numbered Lists
     {$3}$1. {$2}Item $1

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
     
