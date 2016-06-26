![](/assets/images/flexmark-icon-logo%402x.png) flexmark-java 
==================================================================

A rework of [commonmark-java] to generate AST which allows recreating the original source,
full source position references for all elements in the source and easier JetBrains Open API
PsiTree generation.

Motivation for this was the need to replace [pegdown] parser in [Markdown Navigator] plugin.
[pegdown] has a great feature set but its speed in general is not great and for pathological
input either hangs or practically hangs during parsing.

[commonmark-java] has an excellent parsing architecture that is easy to understand and
extend. The goal was to ensure that adding source position tracking in the AST would not
change the ease of parsing and generating the AST more than absolutely necessary.

Reasons for choosing [commonmark-java] as the parser are detailed in
[Pegdown - Achilles heel of the Markdown Navigator plugin]. Now that I have reworked the
core and added a few extensions I am extremely satisfied with my choice.

Another goal was to improve the ability of extensions to modify the behaviour of the parser
so that any dialect of markdown could be implemented through the extension mechanism. This
included adding an easily extensible options mechanism that would allow setting of all
options in one place and having the parser, renderer and extensions use these options to
modify behavior, including disabling some core block parsers.

This is a work in progress with many API changes.

No attempt is made to keep backward API compatibility to the original project.

Progress
--------

- Wiki added [flexmark-java wiki]

- Add `BlockPreProcessor` interface for efficient node replacement before inline processing
  is done. Could be handled with `PostProcessor` but would require each one traverse the
  full AST. This way they are run only on the required nodes.

- Add `ParagraphPreProcessor` interface for generic removal of definitions/other nodes from
  beginnings of paragraphs, like reference definitions are done in [commonmark-java] but
  made generic to be extensible and accessible to extensions.

- Add dependencies between paragraph pre processors so that their order is defined by the
  extension. That way a paragraph pre-processor can be sure that a pre-processor on whose
  output it depends on has been run before it is invoked.

- Unified options architecture to configure: parser, renderer and any custom extensions.
  This includes the list of extensions to use. Making a single argument configure the
  environment. These are also available during parsing and rendering phases for use by
  extensions.

- Test architecture based on original `spec.txt` augmented with:

    - expected AST so it is validated by tests

    - options can be specified for individual tests so that one file can validate all
      options available for the extension/core feature.  

    - full spec file generated with expected HTML and AST replaced with generated
      counterparts to make updating expected test results easier for new or modified tests.

    - section and example number added to each example opening line for cross referencing
      test results to test source.

- Rework `HtmlRenderer` to allow inserting rendered HTML into different parts of the
  generated HTML document. Now can generate HTML for top/bottom of document.

- Enhance `HtmlWriter` to make it easier to generate indented html and eliminate the need to
  implement attribute map and boiler plate render children method in custom node renderers.

- Add `ParagraphPreProcessor` interface to allow customizing of block processing of
  paragraph blocks on closing. Effectively, the mechanism of removing reference definitions
  from the start of the paragraph was generalized to be usable by any block and extensible.

- Add `LinkRefProcessor` interface to allow customizing parsing of link refs for custom
  nodes, such as footnotes `[^]` and wiki links `[[]]` that affect parsing which could not
  be done with a post processor extension.

- Parser options to be implemented:
    - GitHub Extensions
        - [x] Fenced code blocks
        - [ ] Anchor links for headers with auto id generation
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
        - [ ] Table of Contents
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
    - Commonmark Syntax suppression
        - [x] Manual loose lists
        - [x] Numbered lists always start with 1.
        - [x] Fixed list item indent, items must be indented by at least 4 spaces
        - [x] Relaxed list start option, allow lists to start when not preceded by a blank
              line.

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

- `spec.txt` now `ast_spec_txt` with an added section to each example that contains the
  expected AST so that the generated AST can be validated.

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

I am very pleased with the decision to switch to [commonmark-java] based parser. Even though
I had to do major surgery on its innards to get full source position tracking and AST that
matches source elements, it is a pleasure to work with and is now a pleasure to extend a
parser based ot its original design.

Benchmarks
----------

I realized that previous results had the code running commonmark-java and flexmark-java
parsing and rendering, while intellij-markdown and pegdown were only running parsing. Also,
commonmark-java was only running with ext-gfm-tables but to make it more fair to pegdown I
added ext-gfm-strikethrough and disabled auto-link extension for all parsers that have the
option since it causes significant parser slow-down for all parsers:

I also added a flexmark-java configured with all extensions, except for auto-links, for an
idea of how extra extensions affect performance.

| File             | commonmark-java | flexmark-java | flexmark-java-all | intellij-markdown |   pegdown |
|:-----------------|----------------:|--------------:|------------------:|------------------:|----------:|
| README-SLOW      |         0.464ms |       0.767ms |           0.795ms |           1.616ms |  14.467ms |
| VERSION          |         0.785ms |       1.013ms |           1.157ms |           3.537ms |  41.189ms |
| commonMarkSpec   |        31.755ms |      50.024ms |          52.125ms |         584.271ms | 559.061ms |
| markdown_example |         8.927ms |       9.509ms |          10.026ms |         206.310ms | 928.091ms |
| spec             |         4.911ms |       6.149ms |           6.631ms |          33.870ms | 286.549ms |
| table            |         0.228ms |       0.564ms |           0.595ms |           0.653ms |   3.269ms |
| table-format     |         1.245ms |       1.934ms |           2.673ms |           3.602ms |  22.231ms |
| wrap             |         3.298ms |       6.951ms |           7.403ms |          14.687ms |  80.052ms |

Ratios of above:

| File             | commonmark-java | flexmark-java | flexmark-java-all | intellij-markdown |   pegdown |
|:-----------------|----------------:|--------------:|------------------:|------------------:|----------:|
| README-SLOW      |            1.00 |          1.65 |              1.71 |              3.48 |     31.20 |
| VERSION          |            1.00 |          1.29 |              1.47 |              4.50 |     52.46 |
| commonMarkSpec   |            1.00 |          1.58 |              1.64 |             18.40 |     17.61 |
| markdown_example |            1.00 |          1.07 |              1.12 |             23.11 |    103.96 |
| spec             |            1.00 |          1.25 |              1.35 |              6.90 |     58.35 |
| table            |            1.00 |          2.47 |              2.61 |              2.86 |     14.35 |
| table-format     |            1.00 |          1.55 |              2.15 |              2.89 |     17.86 |
| wrap             |            1.00 |          2.11 |              2.24 |              4.45 |     24.27 |
| -----------      |       --------- |     --------- |         --------- |         --------- | --------- |
| overall          |            1.00 |          1.49 |              1.58 |             16.44 |     37.49 |

| File             | commonmark-java | flexmark-java | flexmark-java-all | intellij-markdown |   pegdown |
|:-----------------|----------------:|--------------:|------------------:|------------------:|----------:|
| README-SLOW      |            0.60 |          1.00 |              1.04 |              2.11 |     18.86 |
| VERSION          |            0.77 |          1.00 |              1.14 |              3.49 |     40.65 |
| commonMarkSpec   |            0.63 |          1.00 |              1.04 |             11.68 |     11.18 |
| markdown_example |            0.94 |          1.00 |              1.05 |             21.70 |     97.61 |
| spec             |            0.80 |          1.00 |              1.08 |              5.51 |     46.60 |
| table            |            0.40 |          1.00 |              1.06 |              1.16 |      5.80 |
| table-format     |            0.64 |          1.00 |              1.38 |              1.86 |     11.49 |
| wrap             |            0.47 |          1.00 |              1.06 |              2.11 |     11.52 |
| -----------      |       --------- |     --------- |         --------- |         --------- | --------- |
| overall          |            0.67 |          1.00 |              1.06 |             11.03 |     25.16 |

Because these two files represent the pathological input for pegdown, I no longer run them
as part of the benchmark to prevent skewing of the results. The results are here for
posterity.

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
* [commonMarkSpec.md] is a 33k line file used in [intellij-markdown] test suite for
  performance evaluation.
* [spec.txt] commonmark spec markdown file in the [commonmark-java] project
* [hang-pegdown.md] is a file containing a single line of 17 characters `[[[[[[[[[[[[[[[[[`
  which causes pegdown to go into a hyper-exponential parse time.
* [hang-pegdown2.md] a file containing a single line of 18 characters `[[[[[[[[[[[[[[[[[[`
  which causes pegdown to go into a hyper-exponential parse time.
* [wrap.md] is a file I was using to test wrap on typing performance only to discover that
  it has nothing to do with the wrap on typing code when 0.1 seconds is taken by pegdown to
  parse the file. In the plugin the parsing may happen more than once: syntax highlighter
  pass, psi tree building pass, external annotator.
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
