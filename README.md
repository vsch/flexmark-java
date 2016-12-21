![Flexmark Icon Logo](/assets/images/flexmark-icon-logo%402x.png) flexmark-java
===============================================================================

**flexmark-java** is a Java implementation of CommonMark 0.27 spec parser using the blocks
first, inlines after Markdown parsing architecture.

Its strengths are speed, flexibility, Markdown source element based AST with details of the
source position down to individual characters of lexemes that make up the element and
extensibility.

The API allows granular control of the parsing process and is optimized for parsing with a large
number of installed extensions. The parser and extensions come with plenty of options for parser
behavior and HTML rendering variations. The end goal is to have the parser and renderer be able
to mimic other parsers with great degree of accuracy. This is now partially complete with the
implementation of [Markdown Processor Family Emulation](#markdown-processor-family-emulation)

Motivation for this project was the need to replace [pegdown] parser in my [Markdown Navigator]
plugin for JetBrains IDEs. [pegdown] has a great feature set but its speed in general is less
than ideal and for pathological input either hangs or practically hangs during parsing.

### Changes Forced by Downgrade to Java 7

I tried to keep all changes caused by the downgrade in Java language level as mechanical as
possible to make it easier to migrate. The biggest change is the lack of lambda syntax and no
static extension methods in interfaces.

- All VISIT_HANDLERS in *GroupName*Visitor were moved to *GroupName*VisitorExt class. A
  mechanical search and replace from `.VISIT_HANDLERS` to `Ext.VISIT_HANDLERS` should take care
  of the change

- Lack of Lambda syntax will only have an effect if your project language level is downgraded.
  Otherwise, you can keep using lambda syntax.

### Requirements

* Java 7 or above
* The core has no dependencies; for extensions, see below

[![Build status](https://travis-ci.org/vsch/flexmark-java.svg?branch=master)](https://travis-ci.org/vsch/flexmark-java)
[![codecov](https://codecov.io/gh/vsch/flexmark-java/branch/master/graph/badge.svg)](https://codecov.io/gh/vsch/flexmark-java)
[![Maven Central status](https://img.shields.io/maven-central/v/com.vladsch.flexmark/flexmark.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.vladsch.flexmark%22)

### Changes from commonmark-java project

- The project is on Maven: `com.vladsch.flexmark`
- Java compatibility back to 1.7
- Android compatibility to be added
- No attempt is made to keep API backward compatibility to the original project.

    The API has stabilized but some changes may be necessary before 1.0 release.

### Pegdown Migration Helper

I added `com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter` class that converts
pegdown Extensions.* flags to flexmark options and extensions list. Pegdown `Extensions.java` is
included for convenience. These are located in `flexmark-profile-pegdown` module, which will be
added to Maven on the next release. For now you can grab the source from this repo:
[PegdownOptionsAdapter.java] and if you need it [Extensions.java]

You can pass your extension flags to static `PegdownOptionsAdapter.flexmarkOptions(int)` or you
can instantiate `PegdownOptionsAdapter` and use convenience methods to set, add and remove
extension flags. `PegdownOptionsAdapter.getFlexmarkOptions()` will return a fresh copy of
`DataHolder` every time with the options reflecting pegdown extension flags.

```java
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.DataHolder;

public class PegdownOptions {
    static final DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(
            Extensions.ALL | Extensions.MULTI_LINE_IMAGE_URLS
    );

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    // use the PARSER to parse and RENDERER to render with pegdown compatibility
}
```

### Markdown Processor Family Emulation

Latest addition was a rewrite of the list parser to better control emulation of other markdown
processors as per [Markdown Processors Emulation](MarkdownProcessorsEmulation.md). Addition of
processor presets to emulate specific markdown processing behaviour of these parsers is on a
short to do list.

Some emulation families do a better better job of emulating their target than others. Most of
the effort was directed at emulating how these processors parse standard Markdown and list
related parsing specifically. For processors that extend original Markdown, you will need to add
those extensions that are already implemented in flexmark-java to the Parser/Renderer builder
options.

Extensions will be modified to include their own presets for specific processor emulation, if
that processor has an equivalent extension implemented.

If you find a discrepancy please open an issue so it can be addressed.

Major processor families are implemented and some family members also:

- [x] CommonMark (spec 0.27)
      - [x] GitHub Comments
      - [ ] League/CommonMark
- [x] FixedIndent
      - [x] MultiMarkdown
      - [x] Pegdown
- [x] Kramdown
      - [ ] GitHub Docs
      - [ ] Jekyll
- [x] Markdown.pl
      - [ ] Php Markdown Extra

:information_source: profiles to encapsulate configuration details for variants within the
family will follow shortly.

### History and Motivation

**flexmark-java** is a fork of [commonmark-java] project, modified to generate an AST which
reflects all the elements in the original source, full source position tracking for all elements
in the AST and easier JetBrains Open API PsiTree generation.

The API was changed to allow more granular control of the parsing process and optimized for
parsing with a large number of installed extensions. The parser and extensions come with many
tweaking options for parser behavior and HTML rendering variations. The end goal is to have the
parser and renderer be able to mimic other parsers with great degree of accuracy.

Motivation for this was the need to replace [pegdown] parser in [Markdown Navigator] plugin.
[pegdown] has a great feature set but its speed in general is less than ideal and for
pathological input either hangs or practically hangs during parsing.

[commonmark-java] has an excellent parsing architecture that is easy to understand and extend.
The goal was to ensure that adding source position tracking in the AST would not change the ease
of parsing and generating the AST more than absolutely necessary.

Reasons for choosing [commonmark-java] as the parser are: speed, ease of understanding, ease of
extending and speed. More detailed description in
[Pegdown - Achilles heel of the Markdown Navigator plugin]. Now that I have reworked the core
and added a few extensions I am extremely satisfied with my choice.

Another goal was to improve the ability of extensions to modify parser behavior so that any
dialect of markdown could be implemented through the extension mechanism. An extensible options
API was added to allow setting of all options in one place. Parser, renderer and extensions use
these options for configuration, including disabling some core block parsers.

This is a work in progress with many API changes. No attempt is made to keep backward API
compatibility to the original project and until the feature set is mostly complete, not even to
earlier versions of this project.

### Feature Comparison

| Feature                                                                          | flexmark-java                                                    | commmonmark-java                                                  | pegdown                                                              |
|----------------------------------------------------------------------------------|:-----------------------------------------------------------------|:------------------------------------------------------------------|:---------------------------------------------------------------------|
| Relative parse time (less is better)                                             | :heavy_check_mark: 1x [(1)](#1)                                  | :heavy_check_mark: 0.6x to 0.7x [(2)](#2)                         | :x: 25x average, 20,000x to ∞ for pathological input [(3)](#3)       |
| All source elements in the AST                                                   | :heavy_check_mark:                                               | :x:                                                               | :heavy_check_mark:                                                   |
| AST elements with source position                                                | :heavy_check_mark:                                               | :x:                                                               | :heavy_check_mark: with some errors and idiosyncrasies               |
| AST can be easily manipulated                                                    | :heavy_check_mark: AST post processing is an extension mechanism | :heavy_check_mark: AST post processing is an extension mechanism  | :x: not an option. No node's parent information, children as List<>. |
| AST elements have detailed source position for all parts                         | :heavy_check_mark:                                               | :x:                                                               | :x: only node start/end                                              |
| Can disable core parsing features                                                | :heavy_check_mark:                                               | :x:                                                               | :x:                                                                  |
| Core parser implemented via the extension API                                    | :heavy_check_mark:                                               | :x: `instanceOf` tests for specific block parser and node classes | :x: core exposes few extension points                                |
| Easy to understand and modify parser implementation                              | :heavy_check_mark:                                               | :heavy_check_mark:                                                | :x: one PEG parser with complex interactions [(3)](#3)               |
| Parsing of block elements is independent from each other                         | :heavy_check_mark:                                               | :heavy_check_mark:                                                | :x: everything in one PEG grammar                                    |
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

flexmark-java pathological input of 100,000 `[` parses in 68ms, 100,000 `]` in 57ms, 100,000
nested `[` `]` parse in 55ms

###### (2)

commonmark-java pathological input of 100,000 `[` parses in 30ms, 100,000 `]` in 30ms, 100,000
nested `[` `]` parse in 43ms

###### (3)

pegdown pathological input of 17 `[` parses in 650ms, 18 `[` in 1300ms

Progress
--------

- YouTrack renderer added to allow rendering Markdown AST as YouTrack formatted text. Almost the
  same as JIRA but with a few differences.

- JIRA renderer added to allow rendering Markdown AST as JIRA formatted text.

- Parser is mature enough to be used as the parser in the Markdown Navigator plugin. Performance
  improvement is spectacular. Typing with preview enabled is now comfortable, even in larger
  files. Disabling the preview for major editing makes typing response similar to editing plain
  text files.

- Optimized post processor processing to eliminate each processor from having to traverse the
  AST looking for nodes of interest. Parse time for large file (500k bytes, 10k lines) went from
  1.39x commonmark-java down to 1.05x-1.20x range.

- Took a few days to add some flexmark-java extension related functionality to Markdown
  Navigator to make working with test spec files and extension modules easier. I will move this
  out into a separate plugin but it makes working with the test spec files a pleasure.

- Wiki added [flexmark-java wiki]

- Unified options architecture to configure: parser, renderer and any custom extensions. This
  includes the list of extensions to use. Making a single argument configure the environment.
  These are also available during parsing and rendering phases for use by extensions.

    - Add options syntax to ast_spec.md to allow varying parser/renderer options on a per spec
      example basis. This way all options available for the core or extension can be tested in a
      single file.

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
        - [x] Jekyll front matter
        - [x] GitBook link URL encoding. Not applicable
        - [x] HTML comment nodes: Block and Inline
        - [x] Multi-line Image URLs
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

- `spec.txt` now `ast_spec.md` with an added section to each example that contains the expected
  AST so that the generated AST can be validated. The original `spec.txt` is also tested against
  so that any inadvertent changes in the `ast_spec.md` file cause a false pass, they will be
  caught by the original tests in `spec.txt`

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
its original design. If you don't need source level element AST or the rest of what
flexmark-java added and [CommonMark] is your target markdown parser then I encourage you to use
[commonmark-java] as it is an excellent choice for your needs and its performance does not
suffer for the overhead of features that you will not use.

Benchmarks
----------

After upgrading to spec 0.27 compliance and adding parser emulation for various list processing
variations:

| File             | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:-----------------|----------------:|--------------:|------------------:|----------:|
| README-SLOW      |         0.425ms |       1.007ms |           1.664ms |  15.210ms |
| VERSION          |         0.791ms |       1.659ms |           3.871ms |  42.589ms |
| commonMarkSpec   |        31.163ms |      51.462ms |         608.117ms | 593.732ms |
| markdown_example |         8.325ms |      10.002ms |         210.672ms | 981.694ms |
| spec             |         4.685ms |       6.973ms |          34.622ms | 297.876ms |
| table            |         0.230ms |       0.503ms |           0.654ms |   3.477ms |
| table-format     |         1.622ms |       2.689ms |           3.792ms |  22.820ms |
| wrap             |         3.396ms |       7.875ms |          15.658ms |  86.634ms |

Ratios of above:

| File             | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:-----------------|----------------:|--------------:|------------------:|----------:|
| README-SLOW      |            1.00 |          2.37 |              3.92 |     35.79 |
| VERSION          |            1.00 |          2.10 |              4.89 |     53.82 |
| commonMarkSpec   |            1.00 |          1.65 |             19.51 |     19.05 |
| markdown_example |            1.00 |          1.20 |             25.31 |    117.92 |
| spec             |            1.00 |          1.49 |              7.39 |     63.59 |
| table            |            1.00 |          2.19 |              2.85 |     15.14 |
| table-format     |            1.00 |          1.66 |              2.34 |     14.07 |
| wrap             |            1.00 |          2.32 |              4.61 |     25.51 |
| -----------      |       --------- |     --------- |         --------- | --------- |
| overall          |            1.00 |          1.62 |             17.36 |     40.37 |

| File             | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:-----------------|----------------:|--------------:|------------------:|----------:|
| README-SLOW      |            0.42 |          1.00 |              1.65 |     15.11 |
| VERSION          |            0.48 |          1.00 |              2.33 |     25.67 |
| commonMarkSpec   |            0.61 |          1.00 |             11.82 |     11.54 |
| markdown_example |            0.83 |          1.00 |             21.06 |     98.15 |
| spec             |            0.67 |          1.00 |              4.97 |     42.72 |
| table            |            0.46 |          1.00 |              1.30 |      6.92 |
| table-format     |            0.60 |          1.00 |              1.41 |      8.49 |
| wrap             |            0.43 |          1.00 |              1.99 |     11.00 |
| -----------      |       --------- |     --------- |         --------- | --------- |
| overall          |            0.62 |          1.00 |             10.70 |     24.88 |

---

Because these two files represent the pathological input for pegdown, I no longer run them as
part of the benchmark to prevent skewing of the results. The results are here for posterity.

| File          | commonmark-java | flexmark-java | intellij-markdown |    pegdown |
|:--------------|----------------:|--------------:|------------------:|-----------:|
| hang-pegdown  |         0.082ms |       0.326ms |           0.342ms |  659.138ms |
| hang-pegdown2 |         0.048ms |       0.235ms |           0.198ms | 1312.944ms |

Ratios of above:

| File          | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:--------------|----------------:|--------------:|------------------:|----------:|
| hang-pegdown  |            1.00 |          3.98 |              4.17 |   8048.38 |
| hang-pegdown2 |            1.00 |          4.86 |              4.10 |  27207.32 |
| -----------   |       --------- |     --------- |         --------- | --------- |
| overall       |            1.00 |          4.30 |              4.15 |  15151.91 |

| File          | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:--------------|----------------:|--------------:|------------------:|----------:|
| hang-pegdown  |            0.25 |          1.00 |              1.05 |   2024.27 |
| hang-pegdown2 |            0.21 |          1.00 |              0.84 |   5594.73 |
| -----------   |       --------- |     --------- |         --------- | --------- |
| overall       |            0.23 |          1.00 |              0.96 |   3519.73 |

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

[CommonMark]: http://commonmark.org/
[Extensions.java]: flexmark-profile-pegdown/src/main/java/com/vladsch/flexmark/profiles/pegdown/Extensions.java
[Markdown Navigator]: http://vladsch.com/product/markdown-navigator
[Pegdown - Achilles heel of the Markdown Navigator plugin]: http://vladsch.com/blog/15
[PegdownOptionsAdapter.java]: flexmark-profile-pegdown/src/main/java/com/vladsch/flexmark/profiles/pegdown/PegdownOptionsAdapter.java
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
[Maven Central status]: https://img.shields.io/maven-central/v/com.vladsch.flexmark/flexmark.svg
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
[flexmark-java on Maven]: https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.vladsch.flexmark%22
[gfm-tables]: https://help.github.com/articles/organizing-information-with-tables/
[idea-markdown]: https://github.com/nicoulaj/idea-markdown
[nicoulaj]: https://github.com/nicoulaj
[nicoulaj/idea-markdown plugin]: https://github.com/nicoulaj/idea-markdown
[sirthias]: https://github.com/sirthias
[table.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/table.md
[vsch/pegdown]: https://github.com/vsch/pegdown/tree/develop

