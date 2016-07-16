![](/assets/images/flexmark-icon-logo%402x.png) flexmark-java
=============================================================

flexmark-java is a fork of [commonmark-java] project, modified to generate an AST which reflects
all the elements in the original source, full source position tracking for all elements in the
AST and easier JetBrains Open API PsiTree generation.

The API was changed to allow more granular control of the parsing process and optimized for
parsing with a large number of installed extensions.

Motivation for this was the need to replace [pegdown] parser in [Markdown Navigator] plugin.
[pegdown] has a great feature set but its speed in general is less than ideal and for
pathological input either hangs or practically hangs during parsing.

[commonmark-java] has an excellent parsing architecture that is easy to understand and extend.
The goal was to ensure that adding source position tracking in the AST would not change the ease
of parsing and generating the AST more than absolutely necessary.

Reasons for choosing [commonmark-java] as the parser are: speed, ease of understanding, ease of
extending and are detailed in [Pegdown - Achilles heel of the Markdown Navigator plugin]. Now
that I have reworked the core and added a few extensions I am extremely satisfied with my
choice.

Another goal was to improve the ability of extensions to modify parser behavior so that any
dialect of markdown could be implemented through the extension mechanism. An extensible options
API was added to allow setting of all options in one place. Parser, renderer and extensions use
these options for configuration, including disabling some core block parsers.

This is a work in progress with many API changes. No attempt is made to keep backward API
compatibility to the original project and until the feature set is mostly complete, not even to
earlier versions of this project.

### Requirements

* Java 8 or above
* The core has no dependencies; for extensions, see below

### Changes from commonmark-java project

- The project is not yet on Maven
- Java compatibility raised to 1.8 so that lambdas could be used
- Android compatibility neglected for now
- No attempt is made to keep API backward compatibility to the original project.

    This is a work in progress with many API changes.

### Feature Comparison

| Feature                                                                          | flexmark-java                                                    | commmonmark-java                                                  | pegdown                                                              |
|----------------------------------------------------------------------------------|:-----------------------------------------------------------------|:------------------------------------------------------------------|:---------------------------------------------------------------------|
| Relative parse time (less is better)                                             | :heavy_check_mark: 1x                                            | :heavy_check_mark: 0.6x to 0.7x                                   | :x: 25x average, 20,000x to ∞ for pathological input [(2)](#2)       |
| All source elements in the AST                                                   | :heavy_check_mark:                                               | :x:                                                               | :heavy_check_mark:                                                   |
| AST elements with source position                                                | :heavy_check_mark:                                               | :x:                                                               | :heavy_check_mark: with some errors and idiosyncrasies               |
| AST can be easily manipulated                                                    | :heavy_check_mark: AST post processing is an extension mechanism | :heavy_check_mark: AST post processing is an extension mechanism  | :x: not an option. No node's parent information, children as List<>. |
| AST elements have detailed source position for all parts                         | :heavy_check_mark:                                               | :x:                                                               | :x: only node start/end                                              |
| Can disable core parsing features                                                | :heavy_check_mark:                                               | :x:                                                               | :x:                                                                  |
| Core parser implemented via the extension API                                    | :heavy_check_mark:                                               | :x: `instanceOf` tests for specific block parser and node classes | :x: core exposes few extension points                                |
| Easy to understand and modify parser implementation                              | :heavy_check_mark:                                               | :heavy_check_mark:                                                | :x: one massive PEG parser with complex interactions [(2)](#2)       |
| Parsing of block elements is independent from each other                         | :heavy_check_mark: [(1)](#1)                                     | :heavy_check_mark: [(1)](#1)                                      | :x: everything in one PEG grammar                                    |
| Uniform configuration across: parser, renderer and all extensions                | :heavy_check_mark:                                               | :x: none beyond extension list                                    | :x: `int` bit flags for core, none for extensions                    |
| Parsing performance optimized for use with extensions                            | :heavy_check_mark:                                               | :x: parsing performance for core, extensions do what they can     | :x: performance is not a feature                                     |
| Feature rich with many configuration options and extensions out of the box       | :heavy_check_mark:                                               | :x: limited extensions, no options                                | :heavy_check_mark:                                                   |
| Dependency definitions for processors to guarantee the right order of processing | :heavy_check_mark:                                               | :x: order specified by extension list ordering, error prone       | :x: not applicable, core defines where extension processing is added |

<!--
|  | :x: | :x: | :x: |
|  | :x: | :x: | :x: |
|  | :x: | :x: | :x: |
|  | :x: | :x: | :x: |
|  | :x: | :x: | :x: |
|--|:----|:----|:----|
|  | :x: | :x: | :x: |
-->

###### (1) 

pathological input of 10,000 `[` parses in 11ms, 10,000 nested `[` `]` parse in 450ms

###### (2) 

pathological input of 17 `[` parses in 650ms, 18 `[` in 1300ms

Progress
--------

- Removed all `instanceOf` block parser and node tests from `DocumentParser` making it
  completely agnostic to classes and relies instead on the values returned from `BlockParser`
  and `Node` and `Block` interfaces.

- Optimized post processor processing to eliminate each processor from having to traverse the
  AST looking for nodes of interest. Parse time for large file (500k bytes, 10k lines) went from
  1.39x commonmark-java down to 1.05x-1.20x range.

- Took a few days to add some flexmark-java extension related functionality to Markdown
  Navigator to make working with test spec files and extension modules easier. I will move this
  out into a separate plugin.

- Wiki added [flexmark-java wiki]

- Add `BlockPreProcessor` interface for efficient node replacement before inline processing is
  done. Could be handled with `PostProcessor` but would require each one traverse the full AST.
  This way they are run only on the required nodes.

- Add `ParagraphPreProcessor` interface for generic removal of definitions/other nodes from
  beginnings of paragraphs, like reference definitions are done in [commonmark-java] but made
  generic to be extensible and accessible to extensions.

- Add dependencies between paragraph pre processors so that their order is defined by the
  extension. That way a paragraph pre-processor can be sure that a pre-processor on whose output
  it depends on has been run before it is invoked.

- Unified options architecture to configure: parser, renderer and any custom extensions. This
  includes the list of extensions to use. Making a single argument configure the environment.
  These are also available during parsing and rendering phases for use by extensions.

- Test architecture based on original `spec.txt` augmented with:
    - expected AST so it is validated by tests
    - options can be specified for individual tests so that one file can validate all options
      available for the extension/core feature.
    - full spec file generated with expected HTML and AST replaced with generated counterparts
      to make updating expected test results easier for new or modified tests.
    - section and example number added to each example opening line for cross referencing test
      results to test source.

- Rework `HtmlRenderer` to allow inserting rendered HTML into different parts of the generated
  HTML document. Now can generate HTML for top/bottom of document.

- Enhance `HtmlWriter` to make it easier to generate indented html and eliminate the need to
  implement attribute map and boiler plate render children method in custom node renderers.

- Add `ParagraphPreProcessor` interface to allow customizing of block processing of paragraph
  blocks on closing. Effectively, the mechanism of removing reference definitions from the start
  of the paragraph was generalized to be usable by any block and extensible.

- Add `LinkRefProcessor` interface to allow customizing parsing of link refs for custom nodes,
  such as footnotes `[^]` and wiki links `[[]]` that affect parsing which could not be done with
  a post processor extension.

- Parser options to be implemented:
    - GitHub Extensions
        - [x] Fenced code blocks
        - [x] Anchor links for headers with auto id generation
        - [x] Table Spans option to be implemented for tables extension
        - [x] Wiki Links with GitHub and Creole syntax
        - [x] Emoji Shortcuts with use GitHub emoji URL option
    - GitHub Syntax
        - [x] Strikethrough
        - [x] Task Lists
        - [x] No Atx Header Space
        - [x] No Header indents
        - [x] Hard Wraps (achieved with SOFT_BREAK option changed to `"<br />"`)
        - [x] Relaxed HR Rules Option
        - [x] Wiki links
    - Publishing
        - [x] Abbreviations
        - [x] Footnotes
        - [ ] Definitions
        - [x] Table of Contents
    - Typographic
        - [ ] Quotes
        - [ ] Smarts
    - Suppress
        - [x] inline HTML: all, non-comments, comments
        - [x] HTML blocks: all, non-comments, comments
    - Processor Extensions
        - [ ] Jekyll front matter
        - [ ] GitBook link URL encoding
        - [x] HTML comment nodes: Block and Inline
        - [ ] Multi-line Image URLs
        - [x] Spec Example Element
    - Commonmark Syntax suppression
        - [x] Manual loose lists
        - [x] Numbered lists always start with 1.
        - [x] Fixed list item indent, items must be indented by at least 4 spaces
        - [x] Relaxed list start option, allow lists to start when not preceded by a blank line.

- AST is built based on Nodes in the source not nodes needed for HTML generation. New nodes:
    - `Reference`
    - `Image`
    - `LinkRef`
    - `ImageRef`
    - `AutoLink`
    - `MailLink`
    - `Emphasis`
    - `StrongEmphasis`
    - `HtmlEntity`

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

I realized that previous results had the code running commonmark-java and flexmark-java parsing
and rendering, while intellij-markdown and pegdown were only running parsing. Also,
commonmark-java was only running with ext-gfm-tables but to make it more fair to pegdown I added
ext-gfm-strikethrough and disabled auto-link extension for all parsers that have the option
since it causes significant parser slow-down for all parsers:

I also added a flexmark-java configured with all extensions, except for auto-links, for an idea
of how extra extensions affect performance.

| File             | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:-----------------|----------------:|--------------:|------------------:|----------:|
| README-SLOW      |         0.445ms |       0.734ms |           1.666ms |  14.480ms |
| VERSION          |         0.801ms |       1.057ms |           3.952ms |  40.997ms |
| commonMarkSpec   |        33.477ms |      51.230ms |         593.408ms | 567.285ms |
| markdown_example |         9.457ms |       9.989ms |         210.871ms | 941.851ms |
| spec             |         5.081ms |       6.589ms |          34.630ms | 285.644ms |
| table            |         0.238ms |       0.433ms |           0.655ms |   3.358ms |
| table-format     |         1.580ms |       2.241ms |           3.862ms |  22.149ms |
| wrap             |         3.348ms |       6.891ms |          14.997ms |  82.089ms |

Ratios of above:

| File             | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:-----------------|----------------:|--------------:|------------------:|----------:|
| README-SLOW      |            1.00 |          1.65 |              3.74 |     32.53 |
| VERSION          |            1.00 |          1.32 |              4.93 |     51.17 |
| commonMarkSpec   |            1.00 |          1.53 |             17.73 |     16.95 |
| markdown_example |            1.00 |          1.06 |             22.30 |     99.59 |
| spec             |            1.00 |          1.30 |              6.82 |     56.22 |
| table            |            1.00 |          1.81 |              2.75 |     14.08 |
| table-format     |            1.00 |          1.42 |              2.44 |     14.02 |
| wrap             |            1.00 |          2.06 |              4.48 |     24.52 |
| -----------      |       --------- |     --------- |         --------- | --------- |
| overall          |            1.00 |          1.45 |             15.87 |     35.97 |

| File             | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:-----------------|----------------:|--------------:|------------------:|----------:|
| README-SLOW      |            0.61 |          1.00 |              2.27 |     19.73 |
| VERSION          |            0.76 |          1.00 |              3.74 |     38.79 |
| commonMarkSpec   |            0.65 |          1.00 |             11.58 |     11.07 |
| markdown_example |            0.95 |          1.00 |             21.11 |     94.29 |
| spec             |            0.77 |          1.00 |              5.26 |     43.35 |
| table            |            0.55 |          1.00 |              1.51 |      7.76 |
| table-format     |            0.71 |          1.00 |              1.72 |      9.89 |
| wrap             |            0.49 |          1.00 |              2.18 |     11.91 |
| -----------      |       --------- |     --------- |         --------- | --------- |
| overall          |            0.69 |          1.00 |             10.91 |     24.73 |

Because these two files represent the pathological input for pegdown, I no longer run them as
part of the benchmark to prevent skewing of the results. The results are here for posterity.

| File          | commonmark-java | flexmark-java | intellij-markdown |    pegdown |
|:--------------|----------------:|--------------:|------------------:|-----------:|
| hang-pegdown  |         0.099ms |       0.194ms |           0.353ms |  646.659ms |
| hang-pegdown2 |         0.061ms |       0.113ms |           0.220ms | 1297.536ms |

| File          | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:--------------|----------------:|--------------:|------------------:|----------:|
| hang-pegdown  |            1.00 |          1.96 |              3.56 |   6524.52 |
| hang-pegdown2 |            1.00 |          1.85 |              3.61 |  21230.71 |
| -----------   |       --------- |     --------- |         --------- | --------- |
| overall       |            1.00 |          1.92 |              3.58 |  12133.93 |

| File          | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:--------------|----------------:|--------------:|------------------:|----------:|
| hang-pegdown  |            0.51 |          1.00 |              1.81 |   3325.63 |
| hang-pegdown2 |            0.54 |          1.00 |              1.95 |  11488.93 |
| -----------   |       --------- |     --------- |         --------- | --------- |
| overall       |            0.52 |          1.00 |              1.86 |   6324.95 |

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

[Markdown Navigator]: http://vladsch.com/product/markdown-navigator
[Pegdown - Achilles heel of the Markdown Navigator plugin]: http://vladsch.com/blog/15
[VERSION.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/VERSION.md
[commonMarkSpec.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/commonMarkSpec.md
[commonmark-java]: https://github.com/atlassian/commonmark-java
[flexmark-java wiki]: ../../wiki
[hang-pegdown.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/hang-pegdown.md
[hang-pegdown2.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/hang-pegdown2.md
[intellij-markdown]: https://github.com/valich/intellij-markdown 
[pegdown]: http://pegdown.org
[spec.txt]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/spec.md
[wrap.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/wrap.md
[.gitignore]: http://hsz.mobi
[Android Studio]: http://developer.android.com/sdk/installing/studio.html
[AppCode]: http://www.jetbrains.com/objc
[CLion]: https://www.jetbrains.com/clion
[CommonMark]: http://commonmark.org/
[Craig's List]: http://montreal.en.craigslist.ca/
[DataGrip]: https://www.jetbrains.com/datagrip
[GitHub]: https://github.com/vsch/laravel-translation-manager
[GitHub Flavoured Markdown]: https://help.github.com/articles/basic-writing-and-formatting-syntax/
[GitHub Issues page]: ../../issues
[IntelliJ IDEA]: http://www.jetbrains.com/idea
[JetBrains plugin comment and rate page]: https://plugins.jetbrains.com/plugin/writeComment?pr=&pluginId=7896
[JetBrains plugin page]: https://plugins.jetbrains.com/plugin?pr=&pluginId=7896
[Kotlin]: http://kotlinlang.org
[Kramdown]: http://kramdown.gettalong.org/
[Markdown]: https://daringfireball.net/projects/markdown/
[Maven Central]: https://search.maven.org/#search|ga|1|g%3A%22com.atlassian.commonmark%22
[MultiMarkdown]: http://fletcherpenney.net/multimarkdown/
[PhpExtra]: https://michelf.ca/projects/php-markdown/extra/
[PhpStorm]: http://www.jetbrains.com/phpstorm
[Pipe Table Formatter]: https://github.com/anton-dev-ua/PipeTableFormatter
[PyCharm]: http://www.jetbrains.com/pycharm
[RubyMine]: http://www.jetbrains.com/ruby
[Semantic Versioning]: http://semver.org/
[WebStorm]: http://www.jetbrains.com/webstorm
[autolink-java]: https://github.com/robinst/autolink-java
[commonmark.js]: https://github.com/jgm/commonmark.js
[flexmark-java]: https://github.com/vsch/flexmark-java
[gfm-tables]: https://help.github.com/articles/organizing-information-with-tables/
[idea-markdown]: https://github.com/nicoulaj/idea-markdown
[nicoulaj]: https://github.com/nicoulaj
[nicoulaj/idea-markdown plugin]: https://github.com/nicoulaj/idea-markdown
[sirthias]: https://github.com/sirthias
[table.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/table.md
[vsch/pegdown]: https://github.com/vsch/pegdown/tree/develop

