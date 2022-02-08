### flexmark-ext-admonition 

**flexmark-java extension for [Admonition Extension, Material for MkDocs] syntax processing**

To create block-styled side content. For complete documentation please see the
[Admonition Extension, Material for MkDocs] documentation. (*Personal opinion: Material for
MkDocs is eye-candy. If you have not taken a look at it, you are missing out on a visual
treat.*)

#### Block-Styled Side Content 

    !!! qualifier "Optional Title"
        block content 

#### No-Heading Content

    !!! qualifier ""
        block content 

#### Collapsible Block-Styled Side Content: 

##### Collapsed by default

    ??? qualifier "Optional Title"
        block content 

##### Open by default

    ???+ qualifier "Optional Title"
         block content 

SVG images and stylesheet included for default qualifiers. User definable qualifiers can be
added by specifying recognized style qualifiers, their aliases and image mapping. 

Qualifiers are used to select the icon and the color of the block.

<table>
<caption>Qualifiers</caption>
<tr><td>

* abstract style
    * `abstract`
    * `summary`
    * `tldr`
* bug style
    * `bug`
* danger style
    * `danger`
    * `error`
* example style
    * `example`
    * `snippet`
* fail style
    * `failure`
    * `fail`
    * `missing`
* faq style
    * `question`
    * `help`
    * `faq`
* info style
  * `info`
  * `todo`
* note style
    * `note`
    * `seealso`
* quote style
    * `quote`
    * `cite`
* success style
    * `success`
    * `check`
    * `done`
* tip style
    * `tip`
    * `hint`
    * `important`
* warning style
    * `warning`
    * `caution`
    * `attention`

</td><td>

<img src="https://github.com/vsch/flexmark-java/raw/master/flexmark-ext-admonition/src/main/javadoc/AdmonitionExample.png" alt="AdmonitionExample.png" height="1000"/>

</td></tr>
</table>


[Admonition Extension, Material for MkDocs]: https://squidfunk.github.io/mkdocs-material/extensions/admonition/

