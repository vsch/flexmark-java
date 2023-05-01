flexmark-tree-iteration
=======================

**tree-iteration-util** Used to iterate, including recursively, over tree structure elements
using node class or value filters.

### Requirements

* Java 8 or above
* The core has dependency on:
  * `flexmark-java-utils` library.
  * `org.slf4j:slf4j-api:1.7.25` library.
  * `org.jetbrains:annotations:15.0` library.

### latest [![Maven Central status](https://img.shields.io/maven-central/v/com.vladsch.flexmark/flexmark.svg)](https://search.maven.org/search?q=g:com.vladsch.flexmark)<!-- @IGNORE PREVIOUS: link --> [![Javadocs](https://www.javadoc.io/badge/com.vladsch.flexmark/flexmark.svg)](https://www.javadoc.io/doc/com.vladsch.flexmark/flexmark)

### Examples:

### Using tree-iteration-util

Allows iteration over tree structure nodes with ability to filter based on node class or node
attributes and ability to convert nodes to another type.

```java

```

output of above:

```text

```

### Motivation

I needed to iterate over markdown AST tree with ability to compute and/or collect information in
an efficient manner without the caller having to know intimate details of how to traverse the
AST tree to collect the required information.

### Utility Classes

## Contributing

Pull requests, issues and comments welcome :smile:. For pull requests:

* Add tests for new features and bug fixes
* Follow the existing style to make merging easier, as much as possible: 4 space indent,
  trailing spaces trimmed.

* * *

License
-------

Copyright (c) 2019, Vladimir Schneider <vladimir.schneider@gmail.com>,

BSD (2-clause) licensed, see [LICENSE.txt] file.

[LICENSE.txt]: ../LICENSE.txt
[tree-iteration on Maven]: https://central.sonatype.com/search?q=g%3Acom.vladsch.tree-iteration&smo=true

