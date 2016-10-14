---
title: JiraParser Extension Spec
author: 
version: 
date: '2016-10-03'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## JiraParser  

Converts JIRA formatted text to markdown AST.

1. Headers (`h1.`, ... `h6.`)
2. Text effects:
    - `*strong*` to `**strong**`
    - `_emphasis_` to `*emphasis*` or `_emphasis_`
    - `-deleted-` to `~~strikeout~~`
    - `+inserted+` to `<ins>inserted</ins>`
    - `^superscript^` to `<sup>superscript</sup>`
    - `~subscript~` to `<sub>subscript</sub>`
    - `??citation??` to `<cite>citation</cite>`
    - `{{monospaced}}` to `<code>code</code>`
3. Elements
    - `bq.` to `> block quote`
    - `{quote}` to `> block quote`
    - `----` to thematic break ie. HRule `---`
    - the `--` and `---` don't need conversion since `Smarts` extension does it automatically
    - Links `[url]` and `[label|url]` to `[]()`
    - anchors `{anchor:anchorName}` are a problem, not converted for now
    - Images `!url!` and `!url|ignored!` to `![]()`
4. Lists
    - all nested bullet and numbered list
5. Tables
    - `||header||...||` to `|header|...|` and `|----|-...-|`
    - `|column|...|` as is
6. Other block elements
    - `{noformat}` to verbatim indented code
    - `{code:lang|ignored}...{code}` `{code:title=File.ext|ignored}...{code}` to code fence with
      language determined by the extension of the file used in the `title=` or just the lang
      part.
7. Special characters
    - escape special Jira characters: `{`, `}`
    - optionally convert graphical emoticons to Emoji shortcuts where possible. Left as is where
      no conversion exists.

        ![Jira emoticons](https://github.com/vsch/idea-multimarkdown/raw/master/assets/images/faq/jira/Jira_emoticons.png)

        | Jira | Emoji Icon           | Emoji Shortcut         | Jira      | Emoji Icon                | Emoji Shortcut              |
        |------|----------------------|------------------------|-----------|---------------------------|-----------------------------|
        | :)   | :smile:              | `:smile:`              | (+)       | :heavy_plus_sign:         | `:heavy_plus_sign:`         |
        | :(   | :frowning:           | `:frowning:`           | (-)       | :heavy_minus_sign:        | `:heavy_minus_sign:`        |
        | :P   | :stuck_out_tongue:   | `:stuck_out_tongue:`   | (?)       | :question:                | `:question:`                |
        | :D   | :grinning:           | `:grinning:`           | (on)      | :bulb:                    | `:bulb:`                    |
        | ;)   | :wink:               | `:wink:`               | (off)     | ::                        | `::`                        |
        | (y)  | :thumbsup:           | `:thumbsup:`           | (*)       | :star:                    | `:star:`                    |
        | (n)  | :thumbsdown:         | `:thumbsdown:`         | (*r)      | ::                        | `::`                        |
        | (i)  | :information_source: | `:information_source:` | (*g)      | ::                        | `::`                        |
        | (/)  | :white_check_mark:   | `:white_check_mark:`   | (*b)      | ::                        | `::`                        |
        | (x)  | :x:                  | `:x:`                  | (*y)      | :star:                    | `:star:`                    |
        | (!)  | :warning:            | `:warning:`            | (flag)    | :triangular_flag_on_post: | `:triangular_flag_on_post:` |
        |      |                      |                        | (flagoff) | :crossed_flags:           | `:crossed_flags:`           |

## Headings

1. Headers (`h1.`, ... `h6.`)

Headings with spaces after marker

```````````````````````````````` example Headings: 1
h1. Heading 1
h2. Heading 2
h3. Heading 3
h4. Heading 4
h5. Heading 5
h6. Heading 6
.
<h1>Heading 1</h1>
<h2>Heading 2</h2>
<h3>Heading 3</h3>
<h4>Heading 4</h4>
<h5>Heading 5</h5>
<h6>Heading 6</h6>
.
````````````````````````````````


Heading without spaces after marker

```````````````````````````````` example Headings: 2
h1.Heading 1
h2.Heading 2
h3.Heading 3
h4.Heading 4
h5.Heading 5
h6.Heading 6
.
<h1>Heading 1</h1>
<h2>Heading 2</h2>
<h3>Heading 3</h3>
<h4>Heading 4</h4>
<h5>Heading 5</h5>
<h6>Heading 6</h6>
.
````````````````````````````````


Heading with leading spaces before marker

```````````````````````````````` example Headings: 3
 h1. Heading 1
  h2. Heading 2
   h3. Heading 3
    h4. Heading 4
     h5. Heading 5
      h6. Heading 6
.
<h1>Heading 1</h1>
<h2>Heading 2</h2>
<h3>Heading 3</h3>
<h4>Heading 4</h4>
<h5>Heading 5</h5>
<h6>Heading 6</h6>
.
````````````````````````````````


## Inline formatting

1. Text effects:
    - `*strong*` to `**strong**`
    - `_emphasis_` to `*emphasis*` or `_emphasis_`
    - `-deleted-` to `~~strikeout~~`
    - `+inserted+` to `<ins>inserted</ins>`
    - `^superscript^` to `<sup>superscript</sup>`
    - `~subscript~` to `<sub>subscript</sub>`
    - `??citation??` to `<cite>citation</cite>`
    - `{{monospaced}}` to `<code>code</code>`
    - the `--` and `---` don't need conversion since `Smarts` extension does it automatically

Bold, italic, strikeout, code

```````````````````````````````` example Inline formatting: 1
Paragraph with markdown equivalents *bold*, _italic_, -strikeout-, {{code}}
Paragraph with no markdown equivalents +inserted+, ^superscript^ , ~subscript~, ??citation?? 
.
<p>Paragraph with markdown equivalents <strong>bold</strong>, <em>italic</em>, <del>strikeout</del>, <code>code</code></p>
<p>Paragraph with no markdown equivalents <ins>inserted</ins>, <sup>superscript</sup>, <sub>subscript</sub>, <cite>citation</cite></p>
.
````````````````````````````````


Smarts `--` en dash, and `---` em dash

```````````````````````````````` example Inline formatting: 2
Paragraph with en dash -- and em dash ---
.
<p>Paragraph with en dash &ndash; and em dash &mdash;</p>
.
````````````````````````````````


## Block Elements

1. Elements
    - `bq.` to `> block quote`
    - `{quote}` to `> block quote`
    - `----` to thematic break ie. HRule `---`
    - Links `[url]` and `[label|url]` to `[]()`
    - Images `!url!` and `!url|ignored!` to `![]()`
    - anchors `{anchor:anchorName}` are a problem, not converted for now

## Block Quotes

Single line block quote

```````````````````````````````` example Block Quotes: 1
paragraph line
bq. Single line block quote
paragraph line
.
.
````````````````````````````````


Single line block quote, without space

```````````````````````````````` example Block Quotes: 2
paragraph line
bq.Single line block quote
paragraph line
.
.
````````````````````````````````


Multi line block quote, missing closing mark

```````````````````````````````` example Block Quotes: 3
paragraph line
{quote}
Multi line block quote
Another line
paragraph line
.
.
````````````````````````````````


Single line block quote

```````````````````````````````` example Block Quotes: 4
paragraph line
{quote}Single line block quote{quote}
paragraph line
.
.
````````````````````````````````


Multi line block quote

```````````````````````````````` example Block Quotes: 5
paragraph line
{quote}
Multi line block quote
Another line
{quote}
paragraph line
.
.
````````````````````````````````


## Thematic Break

```````````````````````````````` example Thematic Break: 1
Paragraph
----
Another Paragraph
.
.
````````````````````````````````


## Links

Explicit links [link.and.text] or [link text|link.url] to `a` links

```````````````````````````````` example Links: 1
[/url]
.
.
````````````````````````````````


Explicit links [link text|link.url] to `a` links

```````````````````````````````` example Links: 2
[link label|/url]
.
.
````````````````````````````````


## Links

Explicit links [link.and.text] to `a` links

```````````````````````````````` example Links: 1
[/url]
.
<p><a href="/url">/url</a></p>
.
````````````````````````````````


Explicit links [link text|link.url] to `a` links

```````````````````````````````` example Links: 2
[link label|/url]
<p><a href="/url">link label</a></p>
.
.
````````````````````````````````


## Images

Image links !image! to `img`

```````````````````````````````` example Images: 1
!/url/img.png!
.
<p><img src="/url/img.png"></p>
.
````````````````````````````````


Explicit links !image|ignored! to `img`

```````````````````````````````` example Images: 2
<p><img src="/url/img.png"></p>
.
.
````````````````````````````````


## Anchors

Anchors `{anchor:anchorName}` are a problem, not converted for now

```````````````````````````````` example(Anchors: 1) options(IGNORE)
{anchor:anchorName}
.
.
````````````````````````````````


## Lists

1. Lists
    - all nested bullet and numbered list

Simple bullet list

```````````````````````````````` example Lists: 1
* item 1
* item 2
.
.
````````````````````````````````


Loose bullet list

```````````````````````````````` example Lists: 2
* item 1

* item 2
.
.
````````````````````````````````


Multiline bullet list

```````````````````````````````` example Lists: 3
* item 1
continuation paragraph
* item 2
.
.
````````````````````````````````


Nested bullet list

```````````````````````````````` example Lists: 4
* item 1
continuation paragraph
** sub-item 1
** sub-item 2
* item 2
.
.
````````````````````````````````


no breaks between nested bullet list items

```````````````````````````````` example Lists: 5
* item 1
continuation paragraph
** sub-item 1

** sub-item 2
* item 2
.
.
````````````````````````````````


Simple numbered list

```````````````````````````````` example Lists: 6
# item 1
# item 2
.
.
````````````````````````````````


Loose numbered list

```````````````````````````````` example Lists: 7
# item 1

# item 2
.
.
````````````````````````````````


Multiline numbered list

```````````````````````````````` example Lists: 8
# item 1
continuation paragraph
# item 2
.
.
````````````````````````````````


Nested numbered list

```````````````````````````````` example Lists: 9
# item 1
continuation paragraph
## sub-item 1
## sub-item 2
# item 2
.
.
````````````````````````````````


no breaks between nested numbered list items

```````````````````````````````` example Lists: 10
# item 1
continuation paragraph
## sub-item 1

## sub-item 2
# item 2
.
.
````````````````````````````````


## Tables

1. Tables
    - `||header||...||` to `|header|...|` and `|----|-...-|`
    - `|column|...|` as is

Header only table

```````````````````````````````` example Tables: 1
||header||
.
.
````````````````````````````````


Body only table

```````````````````````````````` example Tables: 2
|row 1|
.
.
````````````````````````````````


Simple table

```````````````````````````````` example Tables: 3
||header||
|column|
.
.
````````````````````````````````


Multi row table

```````````````````````````````` example Tables: 4
||header1||
|row 1|
|row 2|
.
.
````````````````````````````````


Multi header table

```````````````````````````````` example Tables: 5
||header1||
||header2||
|row 1|
.
.
````````````````````````````````


Unbalanced columns

```````````````````````````````` example Tables: 6
||header1.1||header1.2||
||header2||
|body 1.1|body 1.2|body 1.3|
.
.
````````````````````````````````


## Code Blocks

1. Other block elements
    - `{noformat}` to verbatim indented code
    - `{code:lang|ignored}...{code}` `{code:title=File.ext|ignored}...{code}` to code fence with
      language determined by the extension of the file used in the `title=` or just the lang
      part.

Verbatim text

```````````````````````````````` example Code Blocks: 1
{noformat}
verbatim text
{noformat}
.
.
````````````````````````````````


Simple fenced code

```````````````````````````````` example Code Blocks: 2
{code}
verbatim text
{code}
.
.
````````````````````````````````


Fenced code with language

```````````````````````````````` example Code Blocks: 3
{code:lang}
verbatim text
{code}
.
.
````````````````````````````````


Fenced code with file extension

```````````````````````````````` example Code Blocks: 4
{code:file.ext}
verbatim text
{code}
.
.
````````````````````````````````


Fenced code with language, ignored pipe

```````````````````````````````` example Code Blocks: 5
{code:lang|ignored}
verbatim text
{code}
.
.
````````````````````````````````


Fenced code with file extension, ignored pipe

```````````````````````````````` example Code Blocks: 6
{code:file.ext|ignored}
verbatim text
{code}
.
.
````````````````````````````````


## Special characters

1. Special characters
    - escape special Jira characters: `{`, `}`

```````````````````````````````` example Special characters: 1
Paragraph \{quote\}
.
<p>Paragraph {quote}</p>
.
````````````````````````````````


## Emoji

1. Emoji
    - optionally convert graphical emoticons to Emoji shortcuts where possible. Left as is where
      no conversion exists.

        ![Jira emoticons](https://github.com/vsch/idea-multimarkdown/raw/master/assets/images/faq/jira/Jira_emoticons.png)

        | Jira | Emoji Icon           | Emoji Shortcut         | Jira      | Emoji Icon                | Emoji Shortcut              |
        |------|----------------------|------------------------|-----------|---------------------------|-----------------------------|
        | :)   | :smile:              | `:smile:`              | (+)       | :heavy_plus_sign:         | `:heavy_plus_sign:`         |
        | :(   | :frowning:           | `:frowning:`           | (-)       | :heavy_minus_sign:        | `:heavy_minus_sign:`        |
        | :P   | :stuck_out_tongue:   | `:stuck_out_tongue:`   | (?)       | :question:                | `:question:`                |
        | :D   | :grinning:           | `:grinning:`           | (on)      | :bulb:                    | `:bulb:`                    |
        | ;)   | :wink:               | `:wink:`               | (off)     | ::                        | `::`                        |
        | (y)  | :thumbsup:           | `:thumbsup:`           | (*)       | :star:                    | `:star:`                    |
        | (n)  | :thumbsdown:         | `:thumbsdown:`         | (*r)      | ::                        | `::`                        |
        | (i)  | :information_source: | `:information_source:` | (*g)      | ::                        | `::`                        |
        | (/)  | :white_check_mark:   | `:white_check_mark:`   | (*b)      | ::                        | `::`                        |
        | (x)  | :x:                  | `:x:`                  | (*y)      | :star:                    | `:star:`                    |
        | (!)  | :warning:            | `:warning:`            | (flag)    | :triangular_flag_on_post: | `:triangular_flag_on_post:` |
        |      |                      |                        | (flagoff) | :crossed_flags:           | `:crossed_flags:`           |

Emoji Conversion

```````````````````````````````` example Emoji: 1
|| Jira || Emoji Icon         || Jira     || Emoji Icon               ||
| :)   | :smile:              | (+)       | :heavy_plus_sign:         |
| :(   | :frowning:           | (-)       | :heavy_minus_sign:        |
| :P   | :stuck_out_tongue:   | (?)       | :question:                |
| :D   | :grinning:           | (on)      | :bulb:                    |
| ;)   | :wink:               | (off)     |                           |
| (y)  | :thumbsup:           | (*)       | :star:                    |
| (n)  | :thumbsdown:         | (*r)      |                           |
| (i)  | :information_source: | (*g)      |                           |
| (/)  | :white_check_mark:   | (*b)      |                           |
| (x)  | :x:                  | (*y)      | :star:                    |
| (!)  | :warning:            | (flag)    | :triangular_flag_on_post: |
|      |                      | (flagoff) | :crossed_flags:           |
.
.
````````````````````````````````


## Source Position Attribute

```````````````````````````````` example(Source Position Attribute: 1) options(src-pos, IGNORE)
.
.
````````````````````````````````


