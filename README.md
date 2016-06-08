flexmark-java
=============

Rework of [commonmark-java] to generate AST which allows recreating the original source, full
source position references for all nodes and easier JetBrains Open API PsiTree generation.

Main use is as the parser for [Markdown Navigator] plugin.

Improve the ability of extensions to modify the behaviour of the parser so that any dialect of
markdown could be implemented through the extension mechanism.

The goal is to have a parser that can be easily extended to be compatible with:

[pegdown][]  
[CommonMark][]  
[GitHub Flavoured Markdown][]  
[Kramdown][]  
[MultiMarkdown][]  
[PhpExtra][]  

This is a work in progress. No attempt is made to keep backward compatibility to the original
project since it does not have a mechanism for full source references and an AST which can be
used to re-create the source.

Progress so far
---------------

- AST is built based on Nodes in the source not nodes needed for HTML generation. New nodes:
    - Reference
    - Image
    - LinkRef
    - ImageRef
    - Autolink
    - MailLink
    - Emphasis
    - StrongEmphasis
    - HtmlEntity

- Each node has getChars() property which returns a BasedSequence character sequence from which
  you can get start/end offsets into the original source.

- Add `PropertyHolder` interface to store document global properties for things like references,
  abbreviations, footnotes, or anything else that is parsed from the source.

- ParserState a few new mthods:
    - `getPropertyHolder()` returns the property holder for the parse session. Current document
      parser.

    - `getInlineParser()` returns the current parse session's inline processor

    - `getLine()` and `getLineWithEOL()` return the current line being parsed. With or without
      the EOL.

    - `getLineEolLength()` returns the current line's EOL length, usually 1 but can be 2 if
      `"\r\n"` is the current line's sequence.

    - Implements `ParagraphProcessor` interface

- `AbstractBlockParser::closeBlock()` now takes a `ParserState` argument so that any block can
  do processing similar to Paragraph processing of leading References by using the
  `ParagraphProcessor::processParagraph()` method.

- Add `Builder::customInlineParserFactory()` method to allow switching of inline parser.

- Add `Builder::customParagraphProcessor()` method to allow adding custom processing similar to
  `Reference` processing done previously in `ParagraphParser`.

- `InlineParserImpl` has all previously `private` fields and methods set to `protected` so that
  it can be sub-classed for customizations.

- Special processing in document parser for ParagraphParser removed, now can be done by each
  Parser since ParserState.

- Special processing of references was removed from `ParagraphParser::closeBlock()` now it is
  done by a call to `ParserState::processParagraph()`

- Special processing in document parser for ListParser removed, now it is done in the ListParser
  so that it can be customized.

- `spec.txt` now `ast_spec_txt` with an added section to each example that contains the expected
  AST so that the generated AST can be validated.

- Convert all extension tests to spec.txt style driven testing to make generating tests easier
  and to also test for the generated AST

- Add `FullSpecTestCase` to be sub-classed, this one takes the original spec text file and
  replaces the expected html and ast sections with actual ones, then compares the full text of
  the original file to the generated one. Makes it easy to copy actual and do a diff or paste it
  into the expected document for updating the expected values.

- Add `FullSpecTestCase` derived tests to all extensions

- Add `flexmark-ext-abbreviation` to implement abbreviations processing. The extension `create`
  function can take an optional `boolean`, which if true will generate HTML abbreviations as `<a
  href="#" title"Abbreviation Expansion Text">Abbreviation</a>`, otherwise will generate `<abbr
  title"Abbreviation Expansion Text">Abbreviation</abbr>`.  

##### One Week later

All the tests are modified to validate the AST not just the html. The AST contains all parsed
elements with their source location available for every part of the node, not just the node
itself. For example a link will have:

1. `[` textOpeningMarker
2. text
3. `]` textClosingMarker
4. `(` urlOpeningMarker
5. url
6. `"` titleOpeningMarker
7. title
8. `"` titleClosingMarker
9. `)` urlClosingMarker

That way all the bits and pieces are marked if they are needed for syntax highlighting or
anything else.

Whitespace is left out. So all spans of text not in a node are implicitly white space.

I am very pleased with the decision to switch to [commonmark-java] based parser. Even though I
had to do major surgery on its innards to get full source position tracking and AST that matches
the source, it was a pleasure to work with it and is now a pleasure to extend a parser based ot
its original design.

#### Four days later

I have the parser converted and passing all tests. Nothing is optimized in the new code but I
decided to run the primitive benchmark to see how much performance was lost.

The new parser is only 1.6 times slower overall from the original [commonmark-java] parser and
about 7 times faster than [intellij-markdown] which is why I chose to work with commonmark-java
in the first place.

Now I have an easily extensible, in theory, parser that tracks original source position with no
great effort. To map a part of a node to the original source only requires that a
`subSequence()` of the text making it up be stored.

I still have to implement tests to validate that all nodes store the correct location. The
original tests are only concerned with the right characters being generated. I also have to
implement a pegdown compatible parser extensions but that is just effort of writing the code and
porting the pegdown tests to this project.

Overall, I am very pleased with the results and my initial choice of basing the new parser for
[Markdown Navigator] on commonmark-java. The [flexmark-java] project is still in its initial
development mode. I did not get around to renaming the project or the extensions but this will
happen soon.

Latest Benchmarks
-----------------

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

* * * 

License
-------

Copyright (c) 2015-2016 Atlassian and others.

Copyright (c) 2016, Vladimir Schneider,

BSD (2-clause) licensed, see LICENSE.txt file.

[idea-markdown]: https://github.com/nicoulaj/idea-markdown
[commonMarkSpec.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/commonMarkSpec.md
[hang-pegdown.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/hang-pegdown.md
[hang-pegdown2.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/hang-pegdown2.md
[spec.txt]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/spec.md
[table.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/table.md
[VERSION.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/VERSION.md
[wrap.md]: https://github.com/vsch/idea-multimarkdown/blob/master/test/data/performance/wrap.md
[flexmark-java]: https://github.com/vsch/flexmark-java
[.gitignore]: http://hsz.mobi
[Android Studio]: http://developer.android.com/sdk/installing/studio.html
[AppCode]: http://www.jetbrains.com/objc
[autolink-java]: https://github.com/robinst/autolink-java
[CLion]: https://www.jetbrains.com/clion
[commonmark-java]: https://github.com/atlassian/commonmark-java
[commonmark.js]: https://github.com/jgm/commonmark.js
[CommonMark]: http://commonmark.org/
[Craig's List]: http://montreal.en.craigslist.ca/
[DataGrip]: https://www.jetbrains.com/datagrip
[gfm-tables]: https://help.github.com/articles/organizing-information-with-tables/
[GitHub Flavoured Markdown]: https://help.github.com/articles/basic-writing-and-formatting-syntax/
[GitHub Issues page]: ../../issues
[GitHub]: https://github.com/vsch/laravel-translation-manager
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
[vsch/pegdown]: https://github.com/vsch/pegdown/tree/develop
[WebStorm]: http://www.jetbrains.com/webstorm

