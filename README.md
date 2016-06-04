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

Four days later, I have the parser converted and passing all tests. Nothing is optimized in the
new code but I decided to run the primitive benchmark to see how much performance was lost.

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

Here are some basic benchmarking results:

| File             | commonmark-java | flexmark-java | intellij-markdown |    pegdown |
|:-----------------|----------------:|--------------:|------------------:|-----------:|
| commonMarkSpec   |        42.343ms |      72.332ms |         588.993ms |  622.279ms |
| hang-pegdown     |         0.012ms |       0.024ms |           0.028ms |  653.111ms |
| table-format     |         1.324ms |       1.658ms |           3.419ms |   25.505ms |
| VERSION          |         1.234ms |       1.625ms |           3.582ms |   49.494ms |
| README-SLOW      |         0.702ms |       0.906ms |           1.634ms |   17.338ms |
| hang-pegdown2    |         0.013ms |       0.016ms |           0.027ms | 1300.878ms |
| wrap             |         4.479ms |       9.088ms |          14.649ms |   95.989ms |
| markdown_example |        21.056ms |      26.393ms |         210.310ms | 1085.997ms |
| spec             |         8.703ms |      12.056ms |          35.188ms |  332.175ms |
| table            |         0.125ms |       0.144ms |           0.676ms |    4.169ms |

Ratio of performance:

| File             | commonmark-java | flexmark-java | intellij-markdown |   pegdown |
|:-----------------|----------------:|--------------:|------------------:|----------:|
| commonMarkSpec   |            1.00 |          1.71 |             13.91 |     14.70 |
| hang-pegdown     |            1.00 |          1.98 |              2.30 |  53102.74 |
| table-format     |            1.00 |          1.25 |              2.58 |     19.27 |
| VERSION          |            1.00 |          1.32 |              2.90 |     40.12 |
| README-SLOW      |            1.00 |          1.29 |              2.33 |     24.70 |
| hang-pegdown2    |            1.00 |          1.28 |              2.15 | 102738.72 |
| wrap             |            1.00 |          2.03 |              3.27 |     21.43 |
| markdown_example |            1.00 |          1.25 |              9.99 |     51.58 |
| spec             |            1.00 |          1.39 |              4.04 |     38.17 |
| table            |            1.00 |          1.15 |              5.42 |     33.47 |
| ---------------- | --------------- | ------------- | ----------------- | --------- |
| overall          |            1.00 |          1.55 |             10.73 |     52.34 |

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
[GitHub wiki in IntelliJ IDE]: ../../wiki/Adding-GitHub-Wiki-to-IntelliJ-Project
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
[Version Notes]: resources/META-INF/VERSION.md
[vsch/pegdown]: https://github.com/vsch/pegdown/tree/develop
[WebStorm]: http://www.jetbrains.com/webstorm
[Wiki]: ../../wiki

