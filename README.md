flexmark-java
=============

A rework of [commonmark-java] to generate AST which allows recreating the original source, full
source position references for all elements in the source and easier JetBrains Open API PsiTree
generation.

Motivation for this was the need to replace [pegdown] parser in [Markdown Navigator] plugin.
[pegdown] has a great feature set but its speed in general is not great and for pathological
input either hangs or practically hangs during parsing.

[commonmark-java] has an excellent parsing architecture that is easy to understand and extend.
The goal was to ensure that adding source position tracking in the AST would not change the ease
of parsing and generating the AST more than absolutely necessary.

Reasons for choosing [commonmark-java] as the parser are detailed in
[Pegdown - Achilles heel of the Markdown Navigator plugin]. Now that I have reworked the core
and added a few extensions I am extremely satisfied with my choice.

Another goal was to improve the ability of extensions to modify the behaviour of the parser so
that any dialect of markdown could be implemented through the extension mechanism. This included
adding an easily extensible options mechanism that would allow setting of all options in one
place and having the parser, renderer and extensions use these options to modify behavior,
including disabling some core block parsers.

This is a work in progress with many API changes.

No attempt is made to keep backward API compatibility to the original project.

Progress so far
---------------

- Wiki added [flexmark-java wiki]

- Unified options architecture to configure: parser, renderer and any custom extensions. This
  includes the list of extensions to use. Making a single argument configure the environment.
  These are also available during parsing and rendering phases for use by extensions.

- Test architecture based on original spec.txt augmented:
    - with expected AST to be validated
    - options can be specified for individual tests so that one file can validate all options
      available for the extension.  
    - full spec file with expected HTML and AST replaced with generated counterparts to make
      updating expected test results easier for new or modified tests. This also adds section
      and example number to each example opening line for cross referencing test results to test
      source.

- Rework HtmlRenderer to allow inserting rendered HTML into different parts of the generated
  HTML document.

- Enhance HtmlWriter to make it easier to generate indented html and eliminate the need to
  implement attribute map and render children handlers.

- Add BlockPreProcessor interface to allow customizing of block close processing of paragraph
  blocks on closing. Effectively, the mechanism of removing reference definitions from the start
  of the paragraph was generalized to be usable by any block and extensible.

- Add `LinkRefProcessor` interface to allow customizing parsing of link refs for custom nodes,
  such as footnotes `[^]` and wiki links `[[]]`.

- Parser options to be implemented:
    - GitHub Extensions
        - [x] Auto links
        - [x] Fenced code blocks
        - [ ] Anchor links for headers with auto id generation
        - [x] Table Spans option to be implemented for gfm-tables extension
        - [x] Wiki Links with GitHub and Creole syntax
        - [x] Emoji Shortcuts with use GitHub emoji URL option
    - GitHub Syntax
        - [x] Strikethrough
        - [ ] Task Lists
        - [ ] No Atx Header Space
        - [x] Hard Wraps (achieved with SOFT_BREAK option changed to `"<br />"`)
        - [ ] Relaxed HR Rules
        - [x] Wiki links
    - Publishing
        - [x] Abbreviations
        - [x] Footnotes
        - [ ] Definitions
        - [ ] Table of Contents
    - Typographic
        - [ ] Quotes
        - [ ] Smarts
    - Suppress
        - [x] inline HTML
        - [x] HTML blocks
    - Processor Extensions
        - [ ] Jekyll front matter
        - [ ] GitBook link URL encoding
        - [ ] HTML comment nodes

- AST is built based on Nodes in the source not nodes needed for HTML generation. New nodes:
    - Reference
    - Image
    - LinkRef
    - ImageRef
    - AutoLink
    - MailLink
    - Emphasis
    - StrongEmphasis
    - HtmlEntity

- `spec.txt` now `ast_spec_txt` with an added section to each example that contains the expected
  AST so that the generated AST can be validated.

        ```````````````````````````````` example Links: 35
        [foo *bar](baz*)
        .
        <p><a href="baz*">foo *bar</a></p>
        .
        Document[0, 17]
          Paragraph[0, 17]
            Link[0, 15] textOpen:[0, 1, "["] text:[1, 9, "foo *bar"] textClose:[9, 10, "]"] linkOpen:[0, 0] urlOpen:[0, 0] url:[11, 15, "baz*"] urlClose:[0, 0] titleOpen:[0, 0] title:[0, 0] titleClose:[0, 0] linkClose:[0, 0]
              Text[1, 9] chars:[1, 9, "foo *bar"]
        ````````````````````````````````
    
Whitespace is left out. So all spans of text not in a node are implicitly white space.

I am very pleased with the decision to switch to [commonmark-java] based parser. Even though I
had to do major surgery on its innards to get full source position tracking and AST that matches
source elements, it is a pleasure to work with and is now a pleasure to extend a parser based ot
its original design.

Benchmarks
----------

Here are some basic benchmarking results:

| File             | commonmark-java | flexmark-java | intellij-markdown |    pegdown |
|:-----------------|----------------:|--------------:|------------------:|-----------:|
| README-SLOW      |         0.733ms |       0.907ms |           1.849ms |   17.046ms |
| VERSION          |         1.265ms |       1.638ms |           3.837ms |   46.608ms |
| commonMarkSpec   |        42.768ms |      71.486ms |         583.770ms |  610.797ms |
| markdown_example |        19.889ms |      25.544ms |         209.726ms | 1061.904ms |
| spec             |         8.613ms |      11.752ms |          33.912ms |  318.577ms |
| table            |         0.238ms |       0.225ms |           0.664ms |    4.220ms |
| table-format     |         1.452ms |       1.802ms |           3.983ms |   24.965ms |
| wrap             |         4.590ms |       9.153ms |          14.637ms |   93.394ms |

| File             | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:-----------------|----------------:|--------------:|------------------:|----------:|
| README-SLOW      |            1.00 |          1.24 |              2.52 |     23.24 |
| VERSION          |            1.00 |          1.29 |              3.03 |     36.85 |
| commonMarkSpec   |            1.00 |          1.67 |             13.65 |     14.28 |
| markdown_example |            1.00 |          1.28 |             10.54 |     53.39 |
| spec             |            1.00 |          1.36 |              3.94 |     36.99 |
| table            |            1.06 |          1.00 |              2.95 |     18.75 |
| table-format     |            1.00 |          1.24 |              2.74 |     17.19 |
| wrap             |            1.00 |          1.99 |              3.19 |     20.35 |
| -----------      |       --------- |     --------- |         --------- | --------- |
| overall          |            1.00 |          1.54 |             10.72 |     27.38 |

| File             | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:-----------------|----------------:|--------------:|------------------:|----------:|
| README-SLOW      |            0.81 |          1.00 |              2.04 |     18.79 |
| VERSION          |            0.77 |          1.00 |              2.34 |     28.45 |
| commonMarkSpec   |            0.60 |          1.00 |              8.17 |      8.54 |
| markdown_example |            0.78 |          1.00 |              8.21 |     41.57 |
| spec             |            0.73 |          1.00 |              2.89 |     27.11 |
| table            |            1.06 |          1.00 |              2.95 |     18.75 |
| table-format     |            0.81 |          1.00 |              2.21 |     13.85 |
| wrap             |            0.50 |          1.00 |              1.60 |     10.20 |
| -----------      |       --------- |     --------- |         --------- | --------- |
| overall          |            0.65 |          1.00 |              6.96 |     17.77 |

* [VERSION.md] is the version log file I use for Markdown Navigator
* [commonMarkSpec.md] is a 33k line file used in [intellij-markdown] test suite for performance
  evaluation.
* [spec.txt] commonmark spec markdown file in the [commonmark-java] project
* [hang-pegdown.md] is a file containing a single line of 17 characters `[[[[[[[[[[[[[[[[[`
  which causes pegdown to go into a hyper-exponential parse time.
* [hang-pegdown2.md] a file containing a single line of 18 characters `[[[[[[[[[[[[[[[[[[` which
  causes pegdown to go into a hyper-exponential parse time.
* [wrap.md] is a file I was using to test wrap on typing performance only to discover that it
  has nothing to do with the wrap on typing code when 0.1 seconds is taken by pegdown to parse
  the file. In the plugin the parsing may happen more than once: syntax highlighter pass, psi
  tree building pass, external annotator.
* markdown_example.md a file with 10,000+ lines containing 500kB+ of text.

Contributing
------------

Pull requests, issues and comments welcome :smile:. For pull requests:

* Add tests for new features and bug fixes, preferably in the ast_spec.txt format
* Follow the existing style to make merging easier, as much as possible: 4 space indent.  

* * * 

License
-------

Copyright (c) 2015-2016 Atlassian and others.

Copyright (c) 2016, Vladimir Schneider,

BSD (2-clause) licensed, see LICENSE.txt file.

[.gitignore]: http://hsz.mobi
[Android Studio]: http://developer.android.com/sdk/installing/studio.html
[AppCode]: http://www.jetbrains.com/objc
[autolink-java]: https://github.com/robinst/autolink-java
[CLion]: https://www.jetbrains.com/clion
[commonmark-java]: https://github.com/atlassian/commonmark-java
[commonmark.js]: https://github.com/jgm/commonmark.js
[CommonMark]: http://commonmark.org/
[commonMarkSpec.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/commonMarkSpec.md
[Craig's List]: http://montreal.en.craigslist.ca/
[DataGrip]: https://www.jetbrains.com/datagrip
[flexmark-java]: https://github.com/vsch/flexmark-java
[gfm-tables]: https://help.github.com/articles/organizing-information-with-tables/
[GitHub Flavoured Markdown]: https://help.github.com/articles/basic-writing-and-formatting-syntax/
[GitHub Issues page]: ../../issues
[GitHub]: https://github.com/vsch/laravel-translation-manager
[hang-pegdown.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/hang-pegdown.md
[hang-pegdown2.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/hang-pegdown2.md
[idea-markdown]: https://github.com/nicoulaj/idea-markdown
[IntelliJ IDEA]: http://www.jetbrains.com/idea
[intellij-markdown]: https://github.com/valich/intellij-markdown 
[JetBrains plugin comment and rate page]: https://plugins.jetbrains.com/plugin/writeComment?pr=&pluginId=7896
[JetBrains plugin page]: https://plugins.jetbrains.com/plugin?pr=&pluginId=7896
[Kotlin]: http://kotlinlang.org
[Kramdown]: http://kramdown.gettalong.org/
[Markdown Navigator]: http://vladsch.com/product/markdown-navigator
[Markdown]: https://daringfireball.net/projects/markdown/
[Maven Central]: https://search.maven.org/#search|ga|1|g%3A%22com.atlassian.commonmark%22
[MultiMarkdown]: http://fletcherpenney.net/multimarkdown/
[nicoulaj/idea-markdown plugin]: https://github.com/nicoulaj/idea-markdown
[nicoulaj]: https://github.com/nicoulaj
[pegdown]: http://pegdown.org
[PhpExtra]: https://michelf.ca/projects/php-markdown/extra/
[PhpStorm]: http://www.jetbrains.com/phpstorm
[Pipe Table Formatter]: https://github.com/anton-dev-ua/PipeTableFormatter
[PyCharm]: http://www.jetbrains.com/pycharm
[RubyMine]: http://www.jetbrains.com/ruby
[Semantic Versioning]: http://semver.org/
[sirthias]: https://github.com/sirthias
[spec.txt]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/spec.md
[table.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/table.md
[VERSION.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/VERSION.md
[vsch/pegdown]: https://github.com/vsch/pegdown/tree/develop
[WebStorm]: http://www.jetbrains.com/webstorm
[wrap.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/wrap.md
[Pegdown - Achilles heel of the Markdown Navigator plugin]: http://vladsch.com/blog/15
[flexmark-java wiki]: ../../wiki
