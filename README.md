![Flexmark Icon Logo](assets/images/flexmark-icon-logo.png) flexmark-java
=========================================================================

**flexmark-java** is a Java implementation of **[CommonMark (spec 0.28)]** parser using the
blocks first, inlines after Markdown parsing architecture.

Its strengths are speed, flexibility, Markdown source element based AST with details of the
source position down to individual characters of lexemes that make up the element and
extensibility.

The API allows granular control of the parsing process and is optimized for parsing with a large
number of installed extensions. The parser and extensions come with plenty of options for parser
behavior and HTML rendering variations. The end goal is to have the parser and renderer be able
to mimic other parsers with great degree of accuracy. This is now partially complete with the
implementation of [Markdown Processor Emulation](#markdown-processor-emulation)

Motivation for this project was the need to replace [pegdown] parser in my [Markdown Navigator]
plugin for JetBrains IDEs. [pegdown] has a great feature set but its speed in general is less
than ideal and for pathological input either hangs or practically hangs during parsing.

:warning: **Version 0.60.0** has breaking changes due to re-organization, renaming, clean up and
optimization of implementation classes. Changes are detailed in
[Version-0.60.0-Changes](../../wiki/Version-0.60.0-Changes).

### master [![Build status](https://travis-ci.org/vsch/flexmark-java.svg?branch=master)](https://travis-ci.org/vsch/flexmark-java)

### latest [![Maven Central status](https://img.shields.io/maven-central/v/com.vladsch.flexmark/flexmark.svg)](https://search.maven.org/search?q=g:com.vladsch.flexmark)<!-- @IGNORE PREVIOUS: link --> [![Build status](https://travis-ci.org/vsch/flexmark-java.svg?branch=0.61.0)](https://travis-ci.org/vsch/flexmark-java) [![Javadocs](https://www.javadoc.io/badge/com.vladsch.flexmark/flexmark.svg)](https://www.javadoc.io/doc/com.vladsch.flexmark/flexmark)

<!-- [![codecov](https://codecov.io/gh/vsch/flexmark-java/branch/master/graph/badge.svg)](https://codecov.io/gh/vsch/flexmark-java) -->

[![GitQ](https://gitq.com/badge.svg)](https://gitq.com/vsch/flexmark-java)

:information_source: Thanks to [Alex Karezin](mailto:javadiagrams@gmail.com) for setting up
[Flexmark Architecture and Dependencies Diagrams](https://sourcespy.com/github/flexmark/) and
<https://sourcespy.com>. You can now get an overview of module dependencies with ability to
drill down to packages and classes, updated from the repository sources. :thumbsup:

### Requirements

* Java 8 or above, Java 9+ compatible
* Android compatibility to be added
* The project is on Maven: `com.vladsch.flexmark`
* The core has no dependencies other than `org.jetbrains:annotations:15.0`. For extensions, see
  extension description below.

  The API is still evolving to accommodate new extensions and functionality.

### Quick Start

For Maven, add `flexmark-all` as a dependency which includes core and all modules to the
following sample:

```xml
<dependency>
    <groupId>com.vladsch.flexmark</groupId>
    <artifactId>flexmark-all</artifactId>
    <version>0.61.0</version>
</dependency>
```

Source:
[BasicSample.java](flexmark-java-samples/src/com/vladsch/flexmark/java/samples/BasicSample.java)

```java
package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;

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

#### Building via Gradle

```shell
compile 'com.vladsch.flexmark:flexmark-all:0.61.0'
```

#### Building with Android Studio

Additional settings due to duplicate files:

```
packagingOptions {
    exclude 'META-INF/LICENSE-LGPL-2.1.txt'
    exclude 'META-INF/LICENSE-LGPL-3.txt'
    exclude 'META-INF/LICENSE-W3C-TEST'
    exclude 'META-INF/DEPENDENCIES'
}
```

More information can be found in the documentation:  
[Wiki Home](../../wiki) &nbsp;&nbsp;&nbsp;&nbsp;[Usage Examples](../../wiki/Usage)
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
import com.vladsch.flexmark.profile.pegdown.Extensions;
import com.vladsch.flexmark.profile.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.data.DataHolder;

public class PegdownOptions {
     final private static DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(
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
import com.vladsch.flexmark.profile.pegdown.Extensions;
import com.vladsch.flexmark.profile.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.data.DataHolder;

public class PegdownOptions {
     final private static DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(true,
            Extensions.ALL
    );

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    // use the PARSER to parse and RENDERER to render with pegdown compatibility
}
```

A sample with a
[custom link resolver](flexmark-java-samples/src/com/vladsch/flexmark/java/samples/PegdownCustomLinkResolverOptions.java)
is also available, which includes link resolver for changing URLs or attributes of links and a
custom node renderer if you need to override the generated link HTML.

:information_source: [flexmark-java] has many more extensions and configuration options than
[pegdown] in addition to extensions available in pegdown 1.6.0.
[Available Extensions via PegdownOptionsAdapter](../../wiki/Pegdown-Migration#available-extensions-via-pegdownoptionsadapter)

### Latest Additions and Changes

* Major reorganization and code cleanup of implementation in version 0.61.0, see
  [Version-0.60.0-Changes](../../wiki/Version-0.60.0-Changes)
* [Flexmark Architecture and Dependencies Diagrams](https://sourcespy.com/github/flexmark/)
  thanks to great work by [Alex Karezin](mailto:javadiagrams@gmail.com) you can get an overview
  of module dependencies with ability to drill down to packages and classes.
* [Merge API](../../wiki/Markdown-Merge-API) to merge multiple markdown documents into a single
  document.
* [Docx Renderer Extension: Limited Attributes Node Handling](../../wiki/Docx-Renderer-Extension#limited-attributes-node-handling)
* Extensible HTML to Markdown Converter module:
  [flexmark-html2md-converter](flexmark-html2md-converter).
  Sample: [HtmlToMarkdownCustomizedSample.java]
* Java9+ module compatibility
* Compound Enumerated References
  [Enumerated References Extension](../../wiki/Enumerated-References-Extension) for creating
  legal numbering for elements and headings.
* [Macros Extension](../../wiki/Macros-Extension) to allow arbitrary markdown content to be
  inserted as block or inline elements, allowing block elements to be used where only inline
  elements are allowed by syntax.
* [Extensions: Gitlab Flavoured Markdown](../../wiki/Extensions#gitlab-flavoured-markdown) for
  parsing and rendering GitLab markdown extensions.
* OSGi module courtesy Dan Klco (GitHub [@klcodanr](https://github.com/klcodanr))
* [Extensions: Media Tags](../../wiki/Extensions#media-tags) Media link transformer extension
  courtesy Cornelia Schultz (GitHub [@CorneliaXaos](https://github.com/CorneliaXaos)) transforms
  links using custom prefixes to Audio, Embed, Picture, and Video HTML5 tags.
* [Translation Helper API](../../wiki/Translation-Helper-API) to make translating markdown
  documents easier.
* [Admonition Extension](../../wiki/Extensions#admonition) To
  create block-styled side content. For complete documentation please see the
  [Admonition Extension, Material for MkDocs] documentation.
* [Enumerated Reference](../../wiki/Extensions#enumerated-reference)
  to create enumerated references for figures, tables and other markdown elements.
* [Attributes Extension](../../wiki/Extensions#attributes) to
  parse attributes of the form `{name name=value name='value' name="value" #id .class-name}`
  attributes.
* [YouTube Embedded Link Transformer](../../wiki/Extensions#youtube-embedded-link-transformer)
  thanks to Vyacheslav N. Boyko (GitHub @bvn13) transforms simple links to youtube videos to
  embedded video iframe HTML.
* [Docx Converter Module](../../wiki/Extensions#docx-converter)
  using the [docx4j] library. How to use: [DocxConverter Sample], how to customize:
  [Customizing Docx Rendering](../../wiki/Customizing-Docx-Rendering)

  Development of this module was sponsored by
  [Johner Institut GmbH](https://www.johner-institut.de).
* Update library to be [CommonMark (spec 0.28)] compliant and add
  `ParserEmulationProfile.COMMONMARK_0_27` and `ParserEmulationProfile.COMMONMARK_0_28` to allow
  selecting a specific spec version options.
* Custom node rendering API with ability to invoke standard rendering for an overridden node,
  allowing custom node renders that only handle special cases and let the rest be rendered as
  usual.
  [PegdownCustomLinkResolverOptions](flexmark-java-samples/src/com/vladsch/flexmark/java/samples/PegdownCustomLinkResolverOptions.java)
* [Gfm Issues](../../wiki/Extensions#gfm-issues) and
  [Gfm Users](../../wiki/Extensions#gfm-users) extensions for
  parsing and rendering `#123` and `@user-name` respectively.
* Deep HTML block parsing option for better handling of raw text tags that come after other tags
  and for [pegdown] HTML block parsing compatibility.
* `flexmark-all` module that includes: core, all extensions, formatter, JIRA and YouTrack
  converters, pegdown profile module and HTML to Markdown conversion.
* [PDF converter module](../../wiki/Extensions#pdf-output-module)
  [Usage: PDF Output](../../wiki/Usage#pdf-output) using
  [Open HTML To PDF]
* [Typographic Extension Module](../../wiki/Extensions#typographic)
  implemented
* [XWiki Macros Extension Module](../../wiki/Extensions#xwiki-macro-extension)
* [Jekyll tags Extension Module](../../wiki/Extensions#jekyll-tags)
* [HTML to Markdown Converter Module](../../wiki/Extensions#html-to-markdown)
* [Maven Markdown Page Generator Plugin](https://github.com/vsch/markdown-page-generator-plugin)
* [Markdown Formatter](../../wiki/Markdown-Formatter) module to
  output AST as markdown with formatting options.
* [Table Extension](../../wiki/Extensions#tables) for
  [Markdown Formatter](../../wiki/Markdown-Formatter) with
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

There are many extension options in the API with their intended use. A good soft-start is the
[`flexmark-java-samples`](flexmark-java-samples)
module which has simple samples for asked for extensions. The next best place is the source of
an existing extension that has similar syntax to what you want to add.

If your extension lines up with the right API, the task is usually very short and sweet. If your
extension uses the API in an unintended fashion or does not follow expected housekeeping
protocols, you may find it an uphill battle with a rat's nest of if/else condition handling and
fixing one bug only leading to creating another one.

Generally, if it takes more than a few dozen lines to add a simple extension, then either you
are going about it wrong or the API is missing an extension point. If you look at all the
implemented extensions you will see that most are a few lines of code other than boiler plate
dictated by the API. That is the goal for this library: provide an extensible core that makes
writing extensions a breeze.

The larger extensions are `flexmark-ext-tables` and `flexmark-ext-spec-example`, the meat of
both is around 200 lines of code. You can use them as a guide post for size estimating your
extension.

My own experience adding extensions shows that sometimes a new type of extension is best
addressed with an API enhancement to make its implementation seamless, or by fixing a bug that
was not visible before the extension stressed the API in just the right way. Your intended
extension may just be the one requiring such an approach.

#### Don't hesitate to open an issue if you can't find the answer

The takeaway is: if you want to implement an extension or a feature please don't hesitate to
open an issue and I will give you pointers on the best way to go about it. It may save you a lot
of time by letting me improve the API to address your extension's needs before you put a lot of
fruitless effort into it.

I do ask that you realize that I am chief cook and bottle washer on this project, without an
iota of Vulcan Mind Melding skills. I do ask that you describe what you want to implement
because I can't read your mind. Please do some reconnaissance background work around the source
code and documentation because I cannot transfer what I know to you, without your willing
effort.

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
`ParserEmulationProfiles`.

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

* [ ] [Jekyll]
* [CommonMark] for latest implemented spec, currently [CommonMark (spec 0.28)]
  * [ ] [League/CommonMark]
  * [CommonMark (spec 0.27)] for specific version compatibility
  * [CommonMark (spec 0.28)] for specific version compatibility
  * [GitHub] Comments
* [Markdown.pl][Markdown]
  * [ ] [Php Markdown Extra]
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
* Pegdown HTML block parsing rules, without pegdown extensions
  `ParserEmulationProfile.PEGDOWN_STRICT`

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

| Feature                                                                          | flexmark-java                                                    | commonmark-java                                                   | pegdown                                                              |
|:---------------------------------------------------------------------------------|:-----------------------------------------------------------------|:------------------------------------------------------------------|:---------------------------------------------------------------------|
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
  [ast_spec.md](flexmark-core-test/src/test/resources/ast_spec.md) format
* Follow the existing style to make merging easier, as much as possible: 4 space indent,
  trailing spaces trimmed.

* * *

License
-------

Copyright (c) 2015-2016 Atlassian and others.

Copyright (c) 2016-2020, Vladimir Schneider,

BSD (2-clause) licensed, see [LICENSE.txt] file.

[Admonition Extension, Material for MkDocs]: https://squidfunk.github.io/mkdocs-material/extensions/admonition/
[All about me]: https://vladsch.com/about
[CommonMark]: https://commonmark.org
[CommonMark (spec 0.27)]: https://spec.commonmark.org/0.27
[CommonMark (spec 0.28)]: https://spec.commonmark.org/0.28
[DocxConverter Sample]: flexmark-java-samples/src/com/vladsch/flexmark/java/samples/DocxConverterCommonMark.java
[Extensions.java]: flexmark-profile-pegdown/src/main/java/com/vladsch/flexmark/profile/pegdown/Extensions.java
[GitHub]: https://github.com/vsch/laravel-translation-manager
[GitHub Issues page]: ../../issues
[HtmlToMarkdownCustomizedSample.java]: flexmark-java-samples/src/com/vladsch/flexmark/java/samples/HtmlToMarkdownCustomizedSample.java
[Include Markdown and HTML File Content]: ../../wiki/Usage#include-markdown-and-html-file-content
[Jekyll]: https://jekyllrb.com
[Kramdown]: https://kramdown.gettalong.org
[LICENSE.txt]: LICENSE.txt
[League/CommonMark]: https://github.com/thephpleague/commonmark
[Markdown]: https://daringfireball.net/projects/markdown/
[Markdown Navigator]: http://vladsch.com/product/markdown-navigator
[MultiMarkdown]: https://fletcherpenney.net/multimarkdown
[Open HTML To PDF]: https://github.com/danfickle/openhtmltopdf
[PHP Markdown Extra]: https://michelf.ca/projects/php-markdown/extra/#abbr
[Pegdown - Achilles heel of the Markdown Navigator plugin]: http://vladsch.com/blog/15
[PegdownOptionsAdapter.java]: flexmark-profile-pegdown/src/main/java/com/vladsch/flexmark/profile/pegdown/PegdownOptionsAdapter.java
[VERSION.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/VERSION.md
[commonmark-java]: https://github.com/atlassian/commonmark-java
[commonMarkSpec.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/commonMarkSpec.md
[docx4j]: https://www.docx4java.org/trac/docx4j
[flexmark-java]: https://github.com/vsch/flexmark-java
[hang-pegdown.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/hang-pegdown.md
[hang-pegdown2.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/hang-pegdown2.md
[intellij-markdown]: https://github.com/valich/intellij-markdown
[pegdown]: http://pegdown.org
[spec.txt]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/spec.md
[wrap.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/wrap.md

