# BASIC SYNTAX
---

# h1 Heading
## h2 Heading
### h3 Heading
#### h4 Heading
##### h5 Heading
###### h6 Heading

Alternate syntax heading level 1
===============
Alternate syntax heading level 2
---------------

## Paragraphs & Line Breaks

I really like using Markdown.

I think I'll use it to format all of my documents from now on.

This is the first line with 2 spaces at the end.  
And this is the second line.


## Horizontal Rules

___

---

***


## Emphasis

**This is bold text**

__This is bold text__

*This is italic text*

_This is italic text_

This text is ***bold and italic***

This text is ___bold and italic___

This text is __*bold and italic*__

This text is **_bold and italic_**.


## Blockquotes


> Blockquotes can also be nested...
>> ...by using additional greater-than signs right next to each other...
> > > ...or with spaces between arrows.

> #### Blockquotes can be used with other markdown elements
>
> - Revenue was off the chart.
> - Profits were higher than ever.
>
>  *Everything* is going according to **plan**.


## Lists

Unordered

+ Create a list by starting a line with `+`, `-`, or `*`
+ Sub-lists are made by indenting 2 spaces:
  - Marker character change forces new list start:
    * Ac tristique libero volutpat at
    + Facilisis in pretium nisl aliquet
    - Nulla volutpat aliquam velit
+ Very easy!

Ordered

1. Lorem ipsum dolor sit amet
2. Consectetur adipiscing elit
3. Integer molestie lorem at massa


1. You can use sequential numbers...
1. ...or keep all the numbers as `1.`

Start numbering with offset:

57. foo
1. bar



## Code

Inline `code`

Indented code

    // Some comments
    line 1 of code
    line 2 of code
    line 3 of code


Block code "fences"

```
Sample text here...
```

Syntax highlighting

``` js
var foo = function (bar) {
  return bar++;
};

console.log(foo(5));
```

## Tables (TablesExtension)

| Option | Description |
| ------ | ----------- |
| data   | path to data files to supply the data that will be passed into templates. |
| engine | engine to be used for processing templates. Handlebars is the default. |
| ext    | extension to be used for dest files. |

Alignment 
 
| Syntax      | Description | Test Text     |
| :---        |    :----:   |          ---: |
| Header      | Title       | Here's this   |
| Paragraph   | Text        | And more      |

| Markdown Table | With Extra Features Test|
| -------- | --------: |
| [link](https://jsoup.org/apidocs/org/jsoup/nodes/Element.html#attr(java.lang.String,java.lang.String)) | 
| `code` | ~~strikethrough~~ |
| *italics* | **emphasis** |


## Links

[link text](http://dev.nodeca.com)

[link with title](http://nodeca.github.io/pica/demo/ "title text!")

<https://www.markdownguide.org>

<fake@example.com>

In a hole in the ground there lived a hobbit. Not a nasty, dirty, wet hole, filled with the ends
of worms and an oozy smell, nor yet a dry, bare, sandy hole with nothing in it to sit down on or to
eat: it was a [hobbit-hole][1], and that means comfort.

[1]: <https://en.wikipedia.org/wiki/Hobbit#Lifestyle> "Hobbit lifestyles"

[relative link, gitub](../README.md)


## Images

![Minion](https://octodex.github.com/images/minion.png)
![Stormtroopocat](https://octodex.github.com/images/stormtroopocat.jpg "The Stormtroopocat")

Like links, Images also have a footnote style syntax

![Alt text][id]

With a reference later in the document defining the URL location:

[id]: https://octodex.github.com/images/dojocat.jpg  "The Dojocat"

![relative image link, github](test-image.jpg)


## Escaping Characters

\* Without the backslash, this would be a bullet in an unordered list.


# OTHER PLUGINS
---

## StrikethroughSubscriptExtension & SuperscriptExtension

- ~~Strikethrough~~
- 19^th^
- H~2~O


## InsExtension

hey ++you++ will be rendered as inserted text


## TaskListExtension

- [x] This is done
- [ ] This is not done yet
- [ ] This is not done yet


## FootnoteExtension

Footnote 1 link[^first].

Footnote 2 link[^second].


Duplicated footnote reference[^second].

[^first]: Footnote **can have markup**

    and multiple paragraphs.

[^second]: Footnote text.


## WikiLinkExtension

[[pages/viewpage.action?pageId=65546]]


## DefinitionExtension

Term 1

:   Definition 1
with lazy continuation.

Term 2 with *inline markup*

:   Definition 2

        { some code, part of Definition 2 }

    Third paragraph of definition 2.

_Compact style:_

Term 1
  ~ Definition 1

Term 2
  ~ Definition 2a
  ~ Definition 2b


## AnchorLinkExtension

[this should link to the top](#h1-heading)


## AutoLinkExtension

Autoconverted link https://github.com/nodeca/pica


## YouTubeLinkExtension

[youtube video](https://www.youtube.com/watch?v=VSv64fV0LDk)

TOFIX: This plugin doesn't work. Has no effect on the HTML.


## TOCExtension

[TOC levels=1,2]

[TOC levels=1,2]: # "my table of contents"
