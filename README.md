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
implementation of [Markdown Processor Family Emulation](#markdown-processor-emulation)

Motivation for this project was the need to replace [pegdown] parser in my [Markdown Navigator]
plugin for JetBrains IDEs. [pegdown] has a great feature set but its speed in general is less
than ideal and for pathological input either hangs or practically hangs during parsing.

[![Build status](https://travis-ci.org/vsch/flexmark-java.svg?branch=master)](https://travis-ci.org/vsch/flexmark-java)
[![Maven Central status](https://img.shields.io/maven-central/v/com.vladsch.flexmark/flexmark.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.vladsch.flexmark%22)

<!-- [![codecov](https://codecov.io/gh/vsch/flexmark-java/branch/master/graph/badge.svg)](https://codecov.io/gh/vsch/flexmark-java) -->

### Requirements

* Java 7 or above
* Android compatibility to be added
* The project is on Maven: `com.vladsch.flexmark`
* The core has no dependencies; for extensions, see below

  The API is still evolving to accommodate new extensions and functionality.

### Quick Start

You need to add `flexmark-all` as a dependency which includes core and all modules to the
following sample:

```xml
<dependency>
    <groupId>com.vladsch.flexmark</groupId>
    <artifactId>flexmark-all</artifactId>
    <version>0.22.4</version>
</dependency>
```

Source:
[BasicSample.java](flexmark-java-samples/src/com/vladsch/flexmark/samples/BasicSample.java)

```java
package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.util.Arrays;

public class BasicSample {
    public static void main(String[] args) {
        MutableDataSet options = new MutableDataSet();

        // uncomment to set optional extensions
        //options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), StrikethroughExtension.create()));

        // uncomment to convert soft-breaks to hard breaks
        //options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse("This is *Sparta*");
        String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
        System.out.println(html);
    }
}
```

More information can be found in the documentation:  
[Wiki Home](../../wiki)  
&nbsp;&nbsp;&nbsp;&nbsp;[Usage Examples](../../wiki/Usage)  
&nbsp;&nbsp;&nbsp;&nbsp;[Extension Details](../../wiki/Extensions)  
&nbsp;&nbsp;&nbsp;&nbsp;[Writing Extensions](../../wiki/Writing-Extensions)

### Pegdown Migration Helper

`PegdownOptionsAdapter` class converts pegdown `Extensions.*` flags to flexmark options and
extensions list. Pegdown `Extensions.java` is included for convenience and new options not found
in pegdown 1.6.0. These are located in `flexmark-profile-pegdown` module but you can grab the
source from this repo: [PegdownOptionsAdapter.java], [Extensions.java] and make your own
version, modified to your project's needs.

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
            Extensions.ALL
    );

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    // use the PARSER to parse and RENDERER to render with pegdown compatibility
}
```

Default flexmark-java pegdown emulation uses less strict HTML block parsing which interrupts an
HTML block on a blank line. Pegdown only interrupts an HTML block on a blank line if all tags in
the HTML block are closed.

To get closer to original pegdown HTML block parsing behavior use the method which takes a
`boolean strictHtml` argument:

```java
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.DataHolder;

public class PegdownOptions {
    static final DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(true,
            Extensions.ALL
    );

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    // use the PARSER to parse and RENDERER to render with pegdown compatibility
}
```

A sample with a
[custom link resolver](https://github.com/vsch/flexmark-java/blob/master/flexmark-java-samples/src/com/vladsch/flexmark/samples/PegdownCustomLinkResolverOptions.java)
is also available.

:information_source: [flexmark-java] has many more extensions and configuration options than
[pegdown] in addition to extensions available in pegdown 1.6.0.
[Available Extensions via PegdownOptionsAdapter](../../wiki/Pegdown-Migration#available-extensions-via-pegdownoptionsadapter)

### Latest Additions

* Deep HTML block parsing option for better handling of raw text tags that come after other tags
  and for [pegdown] HTML block parsing compatibility.
* `flexmark-all` module that includes: core, all extensions, formatter, JIRA and YouTrack
  converters, pegdown profile module and HTML to Markdown conversion.
* [PDF converter module](https://github.com/vsch/flexmark-java/wiki/Extensions#pdf-output-module)
  [Usage: PDF Output](https://github.com/vsch/flexmark-java/wiki/Usage#pdf-output) using
  [Open HTML To PDF]
* [Typographic Extension Module](https://github.com/vsch/flexmark-java/wiki/Extensions#typographic)
  implemented
* [XWiki Macros Extension Module](https://github.com/vsch/flexmark-java/wiki/Extensions#xwiki-macro-extension)
* [Jekyll tags Extension Module](https://github.com/vsch/flexmark-java/wiki/Extensions#jekyll-tags)
* [HTML to Markdown Converter Module](https://github.com/vsch/flexmark-java/wiki/Extensions#html-to-markdown)
* [Maven Markdown Page Generator Plugin](https://github.com/vsch/markdown-page-generator-plugin)
* [Markdown Formatter](https://github.com/vsch/flexmark-java/wiki/Markdown-Formatter) module to
  output AST as markdown with formatting options.
* [Table Extension](https://github.com/vsch/flexmark-java/wiki/Extensions#tables) for
  [Markdown Formatter](https://github.com/vsch/flexmark-java/wiki/Markdown-Formatter) with
  column width and alignment of markdown tables:

  <table>
      <thead> <tr><th>Input</th> <th>Output</th> </tr> </thead>
      <tr><td>
      <pre><code class="language-markdown">day|time|spent
  :---|:---:|--:
  nov. 2. tue|10:00|4h 40m
  nov. 3. thu|11:00|4h
  nov. 7. mon|10:20|4h 20m
  total:|| 13h</code></pre>
      </td><td>
      <pre><code class="language-markdown">| day         | time  |   spent |
  |:------------|:-----:|--------:|
  | nov. 2. tue | 10:00 |  4h 40m |
  | nov. 3. thu | 11:00 |      4h |
  | nov. 7. mon | 10:20 |  4h 20m |
  | total:             ||     13h |</code></pre>
      </td></tr>
  </table>

### Releases, Bug Fixes, Enhancements and Support

I use flexmark-java as the parser for [Markdown Navigator] plugin for JetBrains IDEs. I tend to
use the latest, unreleased version to fix bugs or get improvements. So if you find a bug that is
a show stopper for your project or see a bug in [github issues page] marked `fixed for next
release` that is affecting your project then please let me know and I may be able to promptly
make a new release to address your issue. Otherwise, I will let bug fixes and enhancements
accumulate thinking no one is affected by what is already fixed.

#### Extension points in the API are many and numerous

There are many extension options in the API with their intended use. If your extension lines up
with the right API, the task is usually very short and sweet. If your extension uses the API in
an unintended fashion or does not follow expected housekeeping protocols, you may find it an
uphill battle with a rat's nest of if/else condition handling and fixing one bug only leading to
creating another one.

Generally, if it takes more than a few dozen lines to add a simple extension, then either you
are going about it wrong or the API is missing an extension point. If you look at all the
implemented extensions you will see that most are a few lines of code other than boiler plate
dictated by the API. That is the goal for this library: provide an extensible core that makes
writing extensions a breeze.

The larger extensions are `flexmark-ext-tables` and `flexmark-ext-spec-example`, the meat of
both is around 200 lines of code. You can use them as a guide post for size estimating your
extension.

My own experience adding extensions shows that sometimes a new type of extension is best
addressed with an API enhancement to make its implementation seamless, or a by fixing a bug that
was not visible before the extension excersized the API in just the right way. Your intended
extension may just be the one requiring such an approach.

#### Don't hesitate to open an issue if you can't find the answer

The takeaway is: if you want to implement an extension or a feature please don't hesitate to
open an issue and I will give you pointers on the best way to go about it. It may save you a lot
of time by letting me improve the API to address your extension's needs before you put a lot of
fruitless effort into it.

I do ask that you realize that I am chief cook and bottle washer on this project, without an
iota of Vulcan Mind Melding skills. I do ask that you describe what you want to implement
because I can't read your mind and do some reconnaissance background work around the source code
and documentation because I cannot transfer what I know to you, without your willing effort.

#### Consulting is available

If you have a commercial application and don't want to write the extension(s) yourself or want
to reduce the time and effort of implementing extensions and integrating flexmark-java, feel
free to contact me. I am available on a consulting/contracting basis, [All about me].

### Markdown Processor Emulation

Despite its name, commonmark is neither a superset nor a subset of other markdown flavors.
Rather, it proposes a standard, unambiguous syntax specification for the original, "core"
Markdown, thus effectively introducing yet another flavor. While flexmark is by default
commonmark compliant, its parser can be tweaked in various ways. The sets of tweaks required to
emulate the most commonly used markdown parsers around are available in flexmark as
ParserEmulationProfiles.

As the name `ParserEmulationProfile` implies, it's only the parser that is adjusted to the
specific markdown flavor. Applying the profile does not add features beyond those available in
commonmark. If you want to use flexmark to fully emulate another markdown processor's behavior,
you have to adjust the parser and configure the flexmark extensions that provide the additional
features available in the parser that you want to emulate.

A rewrite of the list parser to better control emulation of other markdown processors as per
[Markdown Processors Emulation](MarkdownProcessorsEmulation.md) is complete. Addition of
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

* [CommonMark] (spec 0.27)
  * [ ] &nbsp;[League/CommonMark]
  * [GitHub] Comments
* [ ] [Jekyll]
* [Markdown.pl][Markdown]
  * [ ] &nbsp;[Php Markdown Extra]
  * [GitHub] Docs (old GitHub markdown parser)
* [Kramdown]
* FixedIndent
  * [MultiMarkdown]
  * [Pegdown]

:information_source: profiles to encapsulate configuration details for variants within the
family were added in 0.11.0:

* CommonMark (default for family): `ParserEmulationProfile.COMMONMARK`
* FixedIndent (default for family): `ParserEmulationProfile.FIXED_INDENT`
* GitHub Comments (just CommonMark): `ParserEmulationProfile.COMMONMARK`
* Old GitHub Docs: `ParserEmulationProfile.GITHUB_DOC`
* Kramdown (default for family): `ParserEmulationProfile.KRAMDOWN`
* Markdown.pl (default for family): `ParserEmulationProfile.MARKDOWN`
* MultiMarkdown: `ParserEmulationProfile.MULTI_MARKDOWN`
* Pegdown, with pegdown extensions use `PegdownOptionsAdapter` in `flexmark-profile-pegdown`
* Pegdown, without pegdown extensions `ParserEmulationProfile.PEGDOWN`

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
| AST elements with source position                                                | :heavy_check_mark:                                               | :heavy_check_mark:                                                | :heavy_check_mark: with some errors and idiosyncrasies               |
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

* Parser options, items marked as a task item are to be implemented the rest are complete:
  * Typographic
    * Quotes
    * Smarts
  * GitHub Extensions
    * Fenced code blocks
    * Anchor links for headers with auto id generation
    * Table Spans option to be implemented for tables extension
    * Wiki Links with GitHub and Creole syntax
    * Emoji Shortcuts with use GitHub emoji URL option
  * GitHub Syntax
    * Strikethrough
    * Task Lists
    * No Atx Header Space
    * No Header indents
    * Hard Wraps (achieved with SOFT_BREAK option changed to `"<br />"`)
    * Relaxed HR Rules Option
    * Wiki links
  * Publishing
    * Abbreviations
    * Footnotes
    * Definitions
    * Table of Contents
  * Suppress
    * inline HTML: all, non-comments, comments
    * HTML blocks: all, non-comments, comments
  * Processor Extensions
    * Jekyll front matter
    * Jekyll tag elements, with support for `{% include file %}`,
      [Include Markdown and HTML File Content]
    * GitBook link URL encoding. Not applicable
    * HTML comment nodes: Block and Inline
    * Multi-line Image URLs
    * Spec Example Element
  * Commonmark Syntax suppression
    * Manual loose lists
    * Numbered lists always start with 1.
    * Fixed list item indent, items must be indented by at least 4 spaces
    * Relaxed list start option, allow lists to start when not preceded by a blank line.

I am very pleased with the decision to switch to [commonmark-java] based parser for my own
projects. Even though I had to do major surgery on its innards to get full source position
tracking and AST that matches source elements, it is a pleasure to work with and is now a
pleasure to extend. If you don't need source level element AST or the rest of what flexmark-java
added and [CommonMark] is your target markdown parser then I encourage you to use
[commonmark-java] as it is an excellent choice for your needs and its performance does not
suffer for the overhead of features that you will not use.

Benchmarks
----------

Latest, Jan 28, 2017 flexmark-java 0.13.1, intellij-markdown from CE EAP 2017, commonmark-java
0.8.0:

| File             | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:-----------------|----------------:|--------------:|------------------:|----------:|
| README-SLOW      |         0.420ms |       0.812ms |           2.027ms |  15.483ms |
| VERSION          |         0.743ms |       1.425ms |           4.057ms |  42.936ms |
| commonMarkSpec   |        31.025ms |      44.465ms |         600.654ms | 575.131ms |
| markdown_example |         8.490ms |      10.502ms |         223.593ms | 983.640ms |
| spec             |         4.719ms |       6.249ms |          35.883ms | 307.176ms |
| table            |         0.229ms |       0.623ms |           0.800ms |   3.642ms |
| table-format     |         1.385ms |       2.881ms |           4.150ms |  23.592ms |
| wrap             |         3.804ms |       4.589ms |          16.609ms |  86.383ms |

Ratios of above:

| File             | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:-----------------|----------------:|--------------:|------------------:|----------:|
| README-SLOW      |            1.00 |          1.93 |              4.83 |     36.88 |
| VERSION          |            1.00 |          1.92 |              5.46 |     57.78 |
| commonMarkSpec   |            1.00 |          1.43 |             19.36 |     18.54 |
| markdown_example |            1.00 |          1.24 |             26.34 |    115.86 |
| spec             |            1.00 |          1.32 |              7.60 |     65.09 |
| table            |            1.00 |          2.72 |              3.49 |     15.90 |
| table-format     |            1.00 |          2.08 |              3.00 |     17.03 |
| wrap             |            1.00 |          1.21 |              4.37 |     22.71 |
| **overall**      |        **1.00** |      **1.41** |         **17.47** | **40.11** |

| File             | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:-----------------|----------------:|--------------:|------------------:|----------:|
| README-SLOW      |            0.52 |          1.00 |              2.50 |     19.07 |
| VERSION          |            0.52 |          1.00 |              2.85 |     30.12 |
| commonMarkSpec   |            0.70 |          1.00 |             13.51 |     12.93 |
| markdown_example |            0.81 |          1.00 |             21.29 |     93.66 |
| spec             |            0.76 |          1.00 |              5.74 |     49.15 |
| table            |            0.37 |          1.00 |              1.28 |      5.85 |
| table-format     |            0.48 |          1.00 |              1.44 |      8.19 |
| wrap             |            0.83 |          1.00 |              3.62 |     18.83 |
| **overall**      |        **0.71** |      **1.00** |         **12.41** | **28.48** |

---

Because these two files represent the pathological input for pegdown, I no longer run them as
part of the benchmark to prevent skewing of the results. The results are here for posterity.

| File          | commonmark-java | flexmark-java | intellij-markdown |    pegdown |
|:--------------|----------------:|--------------:|------------------:|-----------:|
| hang-pegdown  |         0.082ms |       0.326ms |           0.342ms |  659.138ms |
| hang-pegdown2 |         0.048ms |       0.235ms |           0.198ms | 1312.944ms |

Ratios of above:

| File          | commonmark-java | flexmark-java | intellij-markdown |      pegdown |
|:--------------|----------------:|--------------:|------------------:|-------------:|
| hang-pegdown  |            1.00 |          3.98 |              4.17 |      8048.38 |
| hang-pegdown2 |            1.00 |          4.86 |              4.10 |     27207.32 |
| **overall**   |        **1.00** |      **4.30** |          **4.15** | **15151.91** |

| File          | commonmark-java | flexmark-java | intellij-markdown |     pegdown |
|:--------------|----------------:|--------------:|------------------:|------------:|
| hang-pegdown  |            0.25 |          1.00 |              1.05 |     2024.27 |
| hang-pegdown2 |            0.21 |          1.00 |              0.84 |     5594.73 |
| **overall**   |        **0.23** |      **1.00** |          **0.96** | **3519.73** |

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

* Add tests for new features and bug fixes, preferably in the
  [ast_spec.md](flexmark-test-util/src/main/resources/ast_spec.md) format
* Follow the existing style to make merging easier, as much as possible: 4 space indent,
  trailing spaces trimmed.

* * *

License
-------

Copyright (c) 2015-2016 Atlassian and others.

Copyright (c) 2016-2017, Vladimir Schneider,

BSD (2-clause) licensed, see [LICENSE.txt] file.

[All about me]: https://vladsch.com/about
[CommonMark]: http://commonmark.org/
[commonmark-java]: https://github.com/atlassian/commonmark-java
[commonMarkSpec.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/commonMarkSpec.md
[Extensions.java]: flexmark-profile-pegdown/src/main/java/com/vladsch/flexmark/profiles/pegdown/Extensions.java
[flexmark-java]: https://github.com/vsch/flexmark-java
[GitHub]: https://github.com/vsch/laravel-translation-manager
[GitHub Issues page]: ../../issues
[hang-pegdown.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/hang-pegdown.md
[hang-pegdown2.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/hang-pegdown2.md
[Include Markdown and HTML File Content]: ../../wiki/Usage#include-markdown-and-html-file-content
[intellij-markdown]: https://github.com/valich/intellij-markdown
[Jekyll]: https://jekyllrb.com
[Kramdown]: http://kramdown.gettalong.org/
[League/CommonMark]: https://github.com/thephpleague/commonmark
[LICENSE.txt]: LICENSE.txt
[Markdown]: https://daringfireball.net/projects/markdown/
[Markdown Navigator]: http://vladsch.com/product/markdown-navigator
[MultiMarkdown]: http://fletcherpenney.net/multimarkdown/
[Open HTML To PDF]: https://github.com/danfickle/openhtmltopdf
[pegdown]: http://pegdown.org
[Pegdown - Achilles heel of the Markdown Navigator plugin]: http://vladsch.com/blog/15
[PegdownOptionsAdapter.java]: flexmark-profile-pegdown/src/main/java/com/vladsch/flexmark/profiles/pegdown/PegdownOptionsAdapter.java
[PHP Markdown Extra]: http://michelf.com/projects/php-markdown/extra/#abbr
[spec.txt]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/spec.md
[VERSION.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/VERSION.md
[wrap.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/wrap.md
[.gitignore]: http://hsz.mobi
[Android Studio]: http://developer.android.com/sdk/installing/studio.html
[AppCode]: http://www.jetbrains.com/objc
[autolink-java]: https://github.com/robinst/autolink-java
[CLion]: https://www.jetbrains.com/clion
[commonmark.js]: https://github.com/jgm/commonmark.js
[Craig's List]: http://montreal.en.craigslist.ca/
[DataGrip]: https://www.jetbrains.com/datagrip
[flexmark-java on Maven]: https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.vladsch.flexmark%22
[flexmark-java wiki]: ../../wiki
[gfm-tables]: https://help.github.com/articles/organizing-information-with-tables/
[GitHub Flavoured Markdown]: https://help.github.com/articles/basic-writing-and-formatting-syntax/
[Github-flavoured-Markdown]: http://github.github.com/github-flavored-markdown/
[idea-markdown]: https://github.com/nicoulaj/idea-markdown
[IntelliJ IDEA]: http://www.jetbrains.com/idea
[JetBrains plugin comment and rate page]: https://plugins.jetbrains.com/plugin/writeComment?pr=&pluginId=7896
[JetBrains plugin page]: https://plugins.jetbrains.com/plugin?pr=&pluginId=7896
[Kotlin]: http://kotlinlang.org
[Maven Central]: https://search.maven.org/#search|ga|1|g%3A%22com.atlassian.commonmark%22
[Maven Central status]: https://img.shields.io/maven-central/v/com.vladsch.flexmark/flexmark.svg
[nicoulaj]: https://github.com/nicoulaj
[nicoulaj/idea-markdown plugin]: https://github.com/nicoulaj/idea-markdown
[Pandoc]: http://pandoc.org/MANUAL.html#pandocs-markdown
[PHP Markdown Extra: definition list]: http://michelf.com/projects/php-markdown/extra/#def-list
[PHP Markdown Extra: fenced code]: http://michelf.com/projects/php-markdown/extra/#fenced-code-blocks
[PHP Markdown Extra: tables]: http://michelf.com/projects/php-markdown/extra/#table
[PhpExtra]: https://michelf.ca/projects/php-markdown/extra/
[PhpStorm]: http://www.jetbrains.com/phpstorm
[Pipe Table Formatter]: https://github.com/anton-dev-ua/PipeTableFormatter
[PyCharm]: http://www.jetbrains.com/pycharm
[RubyMine]: http://www.jetbrains.com/ruby
[Semantic Versioning]: http://semver.org/
[sirthias]: https://github.com/sirthias
[table.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/table.md
[vsch/pegdown]: https://github.com/vsch/pegdown/tree/develop
[WebStorm]: http://www.jetbrains.com/webstorm

