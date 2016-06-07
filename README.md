flexmark-java
=============

Rework of [commonmark-java] to generate AST which allows recreating the original source, full
source position references and easier JetBrains Open API PsiTree generation.

Main use is as the parser for [Markdown Navigator] plugin.

This is a work in progress. No attempt is made to keep backward compatibility to the original
project since it does not have mechanisms for full source references and an AST which can be
used to re-create the source.

All attempts are made to keep the blazing speed of the original parser but without sacrificing
the needed functionality.

Also, improve the ability of extensions to modify the behaviour of the parser so that any
dialect of markdown could be implemented through the extension mechanism.

The goal is to have a parser that can be easily extended to be compatible with:

[pegdown][]  
[CommonMark][]  
[GitHub Flavoured Markdown][]  
[Kramdown][]  
[MultiMarkdown][]  
[PhpExtra][]  

Progress so far
---------------

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

I am very pleased that I decided to switch to commonmark-java based parser. Even though I had to
do major surgery on its innards to get full source position tracking and AST that matches the
source, it was a pleasure to work with it and is now a pleasure to extend a parser based ot its
original design.

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
| README-SLOW      |         0.820ms |       1.027ms |           1.836ms |   18.632ms |
| VERSION          |         1.305ms |       1.670ms |           3.754ms |   47.021ms |
| commonMarkSpec   |        44.896ms |      76.843ms |         635.071ms |  641.881ms |
| markdown_example |        20.586ms |      29.454ms |         215.564ms | 1102.196ms |
| spec             |         9.091ms |      12.442ms |          38.830ms |  346.646ms |
| table            |         0.257ms |       0.214ms |           0.663ms |    4.177ms |
| table-format     |         1.450ms |       1.780ms |           3.794ms |   26.014ms |
| wrap             |         4.725ms |       9.794ms |          14.934ms |  103.871ms |

| File             | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:-----------------|----------------:|--------------:|------------------:|----------:|
| README-SLOW      |            1.00 |          1.25 |              2.24 |     22.73 |
| VERSION          |            1.00 |          1.28 |              2.88 |     36.04 |
| commonMarkSpec   |            1.00 |          1.71 |             14.15 |     14.30 |
| markdown_example |            1.00 |          1.43 |             10.47 |     53.54 |
| spec             |            1.00 |          1.37 |              4.27 |     38.13 |
| table            |            1.20 |          1.00 |              3.10 |     19.53 |
| table-format     |            1.00 |          1.23 |              2.62 |     17.94 |
| wrap             |            1.00 |          2.07 |              3.16 |     21.98 |
| -----------      |       --------- |     --------- |         --------- | --------- |
| overall          |            1.00 |          1.60 |             11.01 |     27.57 |

| File             | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:-----------------|----------------:|--------------:|------------------:|----------:|
| README-SLOW      |            0.80 |          1.00 |              1.79 |     18.13 |
| VERSION          |            0.78 |          1.00 |              2.25 |     28.16 |
| commonMarkSpec   |            0.58 |          1.00 |              8.26 |      8.35 |
| markdown_example |            0.70 |          1.00 |              7.32 |     37.42 |
| spec             |            0.73 |          1.00 |              3.12 |     27.86 |
| table            |            1.20 |          1.00 |              3.10 |     19.53 |
| table-format     |            0.81 |          1.00 |              2.13 |     14.62 |
| wrap             |            0.48 |          1.00 |              1.52 |     10.61 |
| -----------      |       --------- |     --------- |         --------- | --------- |
| overall          |            0.62 |          1.00 |              6.86 |     17.19 |


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

