---
title: Tables Extension Manipulator Spec
author: Vladimir Schneider
version: 0.1
date: '2018-12-07'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Manipulator 

Tests for the table formatter `TableManipulator` interface which allows manipulation of tables
prior to generating the formatted output.

These also test the methods for `MarkdownTable` which is used for actual table manipulations

## Delete Row

```````````````````````````````` example(Delete Row: 1) options(delete-row-1-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Delete Row: 2) options(delete-row-2-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Delete Row: 3) options(delete-row-3-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Delete Row: 4) options(delete-row-4-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Delete Row: 5) options(delete-row-5-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |

````````````````````````````````


```````````````````````````````` example(Delete Row: 6) options(delete-row-6-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


## Delete Rows

```````````````````````````````` example(Delete Rows: 1) options(delete-row-1-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
|----------|----------|----------|----------|----------|
| Data 1.1 | Data 1.2 | Data 1.3 | Data 1.4 | Data 1.5 |
| Data 2.1 | Data 2.2 | Data 2.3 | Data 2.4 | Data 2.5 |
| Data 3.1 | Data 3.2 | Data 3.3 | Data 3.4 | Data 3.5 |

````````````````````````````````


```````````````````````````````` example(Delete Rows: 2) options(delete-row-2-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
|------------|------------|------------|------------|------------|
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Delete Rows: 3) options(delete-row-3-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Delete Rows: 4) options(delete-row-4-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |

````````````````````````````````


```````````````````````````````` example(Delete Rows: 5) options(delete-row-5-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |

````````````````````````````````


```````````````````````````````` example(Delete Rows: 6) options(delete-row-6-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


## Insert Row

```````````````````````````````` example(Insert Row: 1) options(insert-row-1-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
|            |            |            |            |            |
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Row: 2) options(insert-row-2-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
|            |            |            |            |            |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Row: 3) options(insert-row-3-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
|            |            |            |            |            |
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Row: 4) options(insert-row-4-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
|            |            |            |            |            |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Row: 5) options(insert-row-5-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
|            |            |            |            |            |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Row: 6) options(insert-row-6-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |
|            |            |            |            |            |

````````````````````````````````


## Insert Rows

```````````````````````````````` example(Insert Rows: 1) options(insert-row-1-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
|            |            |            |            |            |
|            |            |            |            |            |
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Rows: 2) options(insert-row-2-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
|            |            |            |            |            |
|            |            |            |            |            |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Rows: 3) options(insert-row-3-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
|            |            |            |            |            |
|            |            |            |            |            |
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Rows: 4) options(insert-row-4-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
|            |            |            |            |            |
|            |            |            |            |            |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Rows: 5) options(insert-row-5-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
|            |            |            |            |            |
|            |            |            |            |            |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Rows: 6) options(insert-row-6-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |
|            |            |            |            |            |
|            |            |            |            |            |

````````````````````````````````


## Delete Col

```````````````````````````````` example(Delete Col: 1) options(delete-col-1-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|
| Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Delete Col: 2) options(delete-col-2-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|
| Data 1.1   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Delete Col: 3) options(delete-col-3-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Delete Col: 4) options(delete-col-4-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.5 |
|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Delete Col: 5) options(delete-col-5-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 |
|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   |

````````````````````````````````


```````````````````````````````` example(Delete Col: 6) options(delete-col-6-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


## Delete Cols

```````````````````````````````` example(Delete Cols: 1) options(delete-col-1-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|
| Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Delete Cols: 2) options(delete-col-2-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.4 | Header 2.5 |
|------------|------------|------------|
| Data 1.1   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Delete Cols: 3) options(delete-col-3-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.5 |
|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Delete Cols: 4) options(delete-col-4-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 |
| Header 2.1 | Header 2.2 | Header 2.3 |
|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   |
| Data 2.1   | Data 2.2   | Data 2.3   |
| Data 3.1   | Data 3.2   | Data 3.3   |

````````````````````````````````


```````````````````````````````` example(Delete Cols: 5) options(delete-col-5-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 |
|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   |

````````````````````````````````


```````````````````````````````` example(Delete Cols: 6) options(delete-col-6-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


## Insert Col

```````````````````````````````` example(Insert Col: 1) options(insert-col-1-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
|   | Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
|   | Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|---|------------|------------|------------|------------|------------|
|   | Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
|   | Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
|   | Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Col: 2) options(insert-col-2-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 |   | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 |   | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|---|------------|------------|------------|------------|
| Data 1.1   |   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   |   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   |   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Col: 3) options(insert-col-3-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 |   | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 |   | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|---|------------|------------|------------|
| Data 1.1   | Data 1.2   |   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   |   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   |   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Col: 4) options(insert-col-4-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 |   | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 |   | Header 2.4 | Header 2.5 |
|------------|------------|------------|---|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   |   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   |   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   |   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Col: 5) options(insert-col-5-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 |   | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 |   | Header 2.5 |
|------------|------------|------------|------------|---|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   |   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   |   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   |   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Col: 6) options(insert-col-6-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |   |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |   |
|------------|------------|------------|------------|------------|---|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |   |

````````````````````````````````


## Insert Cols

```````````````````````````````` example(Insert Cols: 1) options(insert-col-1-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
|   |   | Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
|   |   | Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|---|---|------------|------------|------------|------------|------------|
|   |   | Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
|   |   | Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
|   |   | Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Cols: 2) options(insert-col-2-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 |   |   | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 |   |   | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|---|---|------------|------------|------------|------------|
| Data 1.1   |   |   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   |   |   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   |   |   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Cols: 3) options(insert-col-3-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 |   |   | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 |   |   | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|---|---|------------|------------|------------|
| Data 1.1   | Data 1.2   |   |   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   |   |   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   |   |   | Data 3.3   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Cols: 4) options(insert-col-4-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 |   |   | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 |   |   | Header 2.4 | Header 2.5 |
|------------|------------|------------|---|---|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   |   |   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   |   |   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   |   |   | Data 3.4   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Cols: 5) options(insert-col-5-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 |   |   | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 |   |   | Header 2.5 |
|------------|------------|------------|------------|---|---|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   |   |   | Data 1.5   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   |   |   | Data 2.5   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   |   |   | Data 3.5   |

````````````````````````````````


```````````````````````````````` example(Insert Cols: 6) options(insert-col-6-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  | Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  | Data 2.2  | Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  | Data 3.2  | Data 3.3  | Data 3.4  | Data 3.5  |

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |   |   |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |   |   |
|------------|------------|------------|------------|------------|---|---|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |   |   |
| Data 2.1   | Data 2.2   | Data 2.3   | Data 2.4   | Data 2.5   |   |   |
| Data 3.1   | Data 3.2   | Data 3.3   | Data 3.4   | Data 3.5   |   |   |

````````````````````````````````


## Delete Col Spans

```````````````````````````````` example(Delete Col Spans: 1) options(delete-col-1-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|
| Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1               || Data 3.4   | Data 3.5   |
| Data 4.1                           ||| Data 4.5   |
| Data 5.1                                       ||||
|                                                ||||
| Data 6.2               || Data 6.4   | Data 6.5   |
| Data 7.2                           ||| Data 7.5   |
| Data 8.2                                       ||||
| Data 9.2   | Data 9.3               || Data 9.5   |
| Data 10.2  | Data 10.3                          |||
| Data 11.2  | Data 11.3  | Data 11.4              ||

````````````````````````````````


```````````````````````````````` example(Delete Col Spans: 2) options(delete-col-2-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|
| Data 1.1   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1               || Data 3.4   | Data 3.5   |
| Data 4.1                           ||| Data 4.5   |
| Data 5.1                                       ||||
|                                                ||||
| Data 6.1   | Data 6.2   | Data 6.4   | Data 6.5   |
| Data 7.1   | Data 7.2               || Data 7.5   |
| Data 8.1   | Data 8.2                           |||
| Data 9.1   | Data 9.3               || Data 9.5   |
| Data 10.1  | Data 10.3                          |||
| Data 11.1  | Data 11.3  | Data 11.4              ||

````````````````````````````````


```````````````````````````````` example(Delete Col Spans: 3) options(delete-col-3-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.2 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.4   | Data 1.5   |
| Data 2.1               || Data 2.4   | Data 2.5   |
| Data 3.1               || Data 3.4   | Data 3.5   |
| Data 4.1                           ||| Data 4.5   |
| Data 5.1                                       ||||
|                                                ||||
| Data 6.1   | Data 6.2   | Data 6.4   | Data 6.5   |
| Data 7.1   | Data 7.2               || Data 7.5   |
| Data 8.1   | Data 8.2                           |||
| Data 9.1   | Data 9.2   | Data 9.3   | Data 9.5   |
| Data 10.1  | Data 10.2  | Data 10.3              ||
| Data 11.1  | Data 11.2  | Data 11.4              ||

````````````````````````````````


```````````````````````````````` example(Delete Col Spans: 4) options(delete-col-4-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.5 |
|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.5   |
| Data 2.1               || Data 2.3   | Data 2.5   |
| Data 3.1                           ||| Data 3.5   |
| Data 4.1                           ||| Data 4.5   |
| Data 5.1                                       ||||
|                                                ||||
| Data 6.1   | Data 6.2               || Data 6.5   |
| Data 7.1   | Data 7.2               || Data 7.5   |
| Data 8.1   | Data 8.2                           |||
| Data 9.1   | Data 9.2   | Data 9.3   | Data 9.5   |
| Data 10.1  | Data 10.2  | Data 10.3              ||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  |

````````````````````````````````


```````````````````````````````` example(Delete Col Spans: 5) options(delete-col-5-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 |
|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   |
| Data 2.1               || Data 2.3   | Data 2.4   |
| Data 3.1                           ||| Data 3.4   |
| Data 4.1                                       ||||
| Data 5.1                                       ||||
|                                                ||||
| Data 6.1   | Data 6.2               || Data 6.4   |
| Data 7.1   | Data 7.2                           |||
| Data 8.1   | Data 8.2                           |||
| Data 9.1   | Data 9.2   | Data 9.3               ||
| Data 10.1  | Data 10.2  | Data 10.3              ||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  |

````````````````````````````````


```````````````````````````````` example(Delete Col Spans: 6) options(delete-col-6-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1               || Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1                           ||| Data 3.4   | Data 3.5   |
| Data 4.1                                       |||| Data 4.5   |
| Data 5.1                                                   |||||
|                                                            |||||
| Data 6.1   | Data 6.2               || Data 6.4   | Data 6.5   |
| Data 7.1   | Data 7.2                           ||| Data 7.5   |
| Data 8.1   | Data 8.2                                       ||||
| Data 9.1   | Data 9.2   | Data 9.3               || Data 9.5   |
| Data 10.1  | Data 10.2  | Data 10.3                          |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4              ||

````````````````````````````````


## Delete Cols Span

```````````````````````````````` example(Delete Cols Span: 1) options(delete-col-1-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|
| Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.4   | Data 3.5   |
| Data 4.1               || Data 4.5   |
| Data 5.1                           |||
|                                    |||
| Data 6.2   | Data 6.4   | Data 6.5   |
| Data 7.2               || Data 7.5   |
| Data 8.2                           |||
| Data 9.3               || Data 9.5   |
| Data 10.3                          |||
| Data 11.3  | Data 11.4              ||

````````````````````````````````


```````````````````````````````` example(Delete Cols Span: 2) options(delete-col-2-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.4 | Header 2.5 |
|------------|------------|------------|
| Data 1.1   | Data 1.4   | Data 1.5   |
| Data 2.1   | Data 2.4   | Data 2.5   |
| Data 3.1   | Data 3.4   | Data 3.5   |
| Data 4.1               || Data 4.5   |
| Data 5.1                           |||
|                                    |||
| Data 6.1   | Data 6.4   | Data 6.5   |
| Data 7.1   | Data 7.2   | Data 7.5   |
| Data 8.1   | Data 8.2               ||
| Data 9.1   | Data 9.3   | Data 9.5   |
| Data 10.1  | Data 10.3              ||
| Data 11.1  | Data 11.4              ||

````````````````````````````````


```````````````````````````````` example(Delete Cols Span: 3) options(delete-col-3-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.2 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.5 |
|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.5   |
| Data 2.1               || Data 2.5   |
| Data 3.1               || Data 3.5   |
| Data 4.1               || Data 4.5   |
| Data 5.1                           |||
|                                    |||
| Data 6.1   | Data 6.2   | Data 6.5   |
| Data 7.1   | Data 7.2   | Data 7.5   |
| Data 8.1   | Data 8.2               ||
| Data 9.1   | Data 9.2   | Data 9.5   |
| Data 10.1  | Data 10.2  | Data 10.3  |
| Data 11.1  | Data 11.2  | Data 11.4  |

````````````````````````````````


```````````````````````````````` example(Delete Cols Span: 4) options(delete-col-4-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.2 | Header 1.3 |
| Header 2.1 | Header 2.2 | Header 2.3 |
|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   |
| Data 2.1               || Data 2.3   |
| Data 3.1                           |||
| Data 4.1                           |||
| Data 5.1                           |||
|                                    |||
| Data 6.1   | Data 6.2               ||
| Data 7.1   | Data 7.2               ||
| Data 8.1   | Data 8.2               ||
| Data 9.1   | Data 9.2   | Data 9.3   |
| Data 10.1  | Data 10.2  | Data 10.3  |
| Data 11.1  | Data 11.2  | Data 11.3  |

````````````````````````````````


```````````````````````````````` example(Delete Cols Span: 5) options(delete-col-5-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 |
|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   |
| Data 2.1               || Data 2.3   | Data 2.4   |
| Data 3.1                           ||| Data 3.4   |
| Data 4.1                                       ||||
| Data 5.1                                       ||||
|                                                ||||
| Data 6.1   | Data 6.2               || Data 6.4   |
| Data 7.1   | Data 7.2                           |||
| Data 8.1   | Data 8.2                           |||
| Data 9.1   | Data 9.2   | Data 9.3               ||
| Data 10.1  | Data 10.2  | Data 10.3              ||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  |

````````````````````````````````


```````````````````````````````` example(Delete Cols Span: 6) options(delete-col-6-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|------------|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1               || Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1                           ||| Data 3.4   | Data 3.5   |
| Data 4.1                                       |||| Data 4.5   |
| Data 5.1                                                   |||||
|                                                            |||||
| Data 6.1   | Data 6.2               || Data 6.4   | Data 6.5   |
| Data 7.1   | Data 7.2                           ||| Data 7.5   |
| Data 8.1   | Data 8.2                                       ||||
| Data 9.1   | Data 9.2   | Data 9.3               || Data 9.5   |
| Data 10.1  | Data 10.2  | Data 10.3                          |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4              ||

````````````````````````````````


## Insert Col Span

```````````````````````````````` example(Insert Col Span: 1) options(insert-col-1-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
|   | Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
|   | Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|---|------------|------------|------------|------------|------------|
|   | Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
|   | Data 2.1               || Data 2.3   | Data 2.4   | Data 2.5   |
|   | Data 3.1                           ||| Data 3.4   | Data 3.5   |
|   | Data 4.1                                       |||| Data 4.5   |
|   | Data 5.1                                                   |||||
|   |                                                            |||||
|   | Data 6.1   | Data 6.2               || Data 6.4   | Data 6.5   |
|   | Data 7.1   | Data 7.2                           ||| Data 7.5   |
|   | Data 8.1   | Data 8.2                                       ||||
|   | Data 9.1   | Data 9.2   | Data 9.3               || Data 9.5   |
|   | Data 10.1  | Data 10.2  | Data 10.3                          |||
|   | Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4              ||

````````````````````````````````


```````````````````````````````` example(Insert Col Span: 2) options(insert-col-2-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 |   | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 |   | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|---|------------|------------|------------|------------|
| Data 1.1   |   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1                  ||| Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1                              |||| Data 3.4   | Data 3.5   |
| Data 4.1                                          ||||| Data 4.5   |
| Data 5.1                                                      ||||||
|                                                               ||||||
| Data 6.1   |   | Data 6.2               || Data 6.4   | Data 6.5   |
| Data 7.1   |   | Data 7.2                           ||| Data 7.5   |
| Data 8.1   |   | Data 8.2                                       ||||
| Data 9.1   |   | Data 9.2   | Data 9.3               || Data 9.5   |
| Data 10.1  |   | Data 10.2  | Data 10.3                          |||
| Data 11.1  |   | Data 11.2  | Data 11.3  | Data 11.4              ||

````````````````````````````````


```````````````````````````````` example(Insert Col Span: 3) options(insert-col-3-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.2 |   | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 |   | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|---|------------|------------|------------|
| Data 1.1   | Data 1.2   |   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1               ||   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1                              |||| Data 3.4   | Data 3.5   |
| Data 4.1                                          ||||| Data 4.5   |
| Data 5.1                                                      ||||||
|                                                               ||||||
| Data 6.1   | Data 6.2                  ||| Data 6.4   | Data 6.5   |
| Data 7.1   | Data 7.2                              |||| Data 7.5   |
| Data 8.1   | Data 8.2                                          |||||
| Data 9.1   | Data 9.2   |   | Data 9.3               || Data 9.5   |
| Data 10.1  | Data 10.2  |   | Data 10.3                          |||
| Data 11.1  | Data 11.2  |   | Data 11.3  | Data 11.4              ||

````````````````````````````````


```````````````````````````````` example(Insert Col Span: 4) options(insert-col-4-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.2 | Header 1.3 |   | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 |   | Header 2.4 | Header 2.5 |
|------------|------------|------------|---|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   |   | Data 1.4   | Data 1.5   |
| Data 2.1               || Data 2.3   |   | Data 2.4   | Data 2.5   |
| Data 3.1                           |||   | Data 3.4   | Data 3.5   |
| Data 4.1                                          ||||| Data 4.5   |
| Data 5.1                                                      ||||||
|                                                               ||||||
| Data 6.1   | Data 6.2               ||   | Data 6.4   | Data 6.5   |
| Data 7.1   | Data 7.2                              |||| Data 7.5   |
| Data 8.1   | Data 8.2                                          |||||
| Data 9.1   | Data 9.2   | Data 9.3                  ||| Data 9.5   |
| Data 10.1  | Data 10.2  | Data 10.3                             ||||
| Data 11.1  | Data 11.2  | Data 11.3  |   | Data 11.4              ||

````````````````````````````````


```````````````````````````````` example(Insert Col Span: 5) options(insert-col-5-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 |   | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 |   | Header 2.5 |
|------------|------------|------------|------------|---|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   |   | Data 1.5   |
| Data 2.1               || Data 2.3   | Data 2.4   |   | Data 2.5   |
| Data 3.1                           ||| Data 3.4   |   | Data 3.5   |
| Data 4.1                                       ||||   | Data 4.5   |
| Data 5.1                                                      ||||||
|                                                               ||||||
| Data 6.1   | Data 6.2               || Data 6.4   |   | Data 6.5   |
| Data 7.1   | Data 7.2                           |||   | Data 7.5   |
| Data 8.1   | Data 8.2                                          |||||
| Data 9.1   | Data 9.2   | Data 9.3               ||   | Data 9.5   |
| Data 10.1  | Data 10.2  | Data 10.3                             ||||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4                 |||

````````````````````````````````


```````````````````````````````` example(Insert Col Span: 6) options(insert-col-6-1)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |   |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |   |
|------------|------------|------------|------------|------------|---|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |   |
| Data 2.1               || Data 2.3   | Data 2.4   | Data 2.5   |   |
| Data 3.1                           ||| Data 3.4   | Data 3.5   |   |
| Data 4.1                                       |||| Data 4.5   |   |
| Data 5.1                                                   |||||   |
|                                                            |||||   |
| Data 6.1   | Data 6.2               || Data 6.4   | Data 6.5   |   |
| Data 7.1   | Data 7.2                           ||| Data 7.5   |   |
| Data 8.1   | Data 8.2                                       ||||   |
| Data 9.1   | Data 9.2   | Data 9.3               || Data 9.5   |   |
| Data 10.1  | Data 10.2  | Data 10.3                          |||   |
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4              ||   |

````````````````````````````````


## Insert Cols Span

```````````````````````````````` example(Insert Cols Span: 1) options(insert-col-1-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
|   |   | Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
|   |   | Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|---|---|------------|------------|------------|------------|------------|
|   |   | Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
|   |   | Data 2.1               || Data 2.3   | Data 2.4   | Data 2.5   |
|   |   | Data 3.1                           ||| Data 3.4   | Data 3.5   |
|   |   | Data 4.1                                       |||| Data 4.5   |
|   |   | Data 5.1                                                   |||||
|   |   |                                                            |||||
|   |   | Data 6.1   | Data 6.2               || Data 6.4   | Data 6.5   |
|   |   | Data 7.1   | Data 7.2                           ||| Data 7.5   |
|   |   | Data 8.1   | Data 8.2                                       ||||
|   |   | Data 9.1   | Data 9.2   | Data 9.3               || Data 9.5   |
|   |   | Data 10.1  | Data 10.2  | Data 10.3                          |||
|   |   | Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4              ||

````````````````````````````````


```````````````````````````````` example(Insert Cols Span: 2) options(insert-col-2-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 |   |   | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 |   |   | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|---|---|------------|------------|------------|------------|
| Data 1.1   |   |   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1                     |||| Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1                                 ||||| Data 3.4   | Data 3.5   |
| Data 4.1                                             |||||| Data 4.5   |
| Data 5.1                                                         |||||||
|                                                                  |||||||
| Data 6.1   |   |   | Data 6.2               || Data 6.4   | Data 6.5   |
| Data 7.1   |   |   | Data 7.2                           ||| Data 7.5   |
| Data 8.1   |   |   | Data 8.2                                       ||||
| Data 9.1   |   |   | Data 9.2   | Data 9.3               || Data 9.5   |
| Data 10.1  |   |   | Data 10.2  | Data 10.3                          |||
| Data 11.1  |   |   | Data 11.2  | Data 11.3  | Data 11.4              ||

````````````````````````````````


```````````````````````````````` example(Insert Cols Span: 3) options(insert-col-3-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.2 |   |   | Header 1.3 | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 |   |   | Header 2.3 | Header 2.4 | Header 2.5 |
|------------|------------|---|---|------------|------------|------------|
| Data 1.1   | Data 1.2   |   |   | Data 1.3   | Data 1.4   | Data 1.5   |
| Data 2.1               ||   |   | Data 2.3   | Data 2.4   | Data 2.5   |
| Data 3.1                                 ||||| Data 3.4   | Data 3.5   |
| Data 4.1                                             |||||| Data 4.5   |
| Data 5.1                                                         |||||||
|                                                                  |||||||
| Data 6.1   | Data 6.2                     |||| Data 6.4   | Data 6.5   |
| Data 7.1   | Data 7.2                                 ||||| Data 7.5   |
| Data 8.1   | Data 8.2                                             ||||||
| Data 9.1   | Data 9.2   |   |   | Data 9.3               || Data 9.5   |
| Data 10.1  | Data 10.2  |   |   | Data 10.3                          |||
| Data 11.1  | Data 11.2  |   |   | Data 11.3  | Data 11.4              ||

````````````````````````````````


```````````````````````````````` example(Insert Cols Span: 4) options(insert-col-4-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.2 | Header 1.3 |   |   | Header 1.4 | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 |   |   | Header 2.4 | Header 2.5 |
|------------|------------|------------|---|---|------------|------------|
| Data 1.1   | Data 1.2   | Data 1.3   |   |   | Data 1.4   | Data 1.5   |
| Data 2.1               || Data 2.3   |   |   | Data 2.4   | Data 2.5   |
| Data 3.1                           |||   |   | Data 3.4   | Data 3.5   |
| Data 4.1                                             |||||| Data 4.5   |
| Data 5.1                                                         |||||||
|                                                                  |||||||
| Data 6.1   | Data 6.2               ||   |   | Data 6.4   | Data 6.5   |
| Data 7.1   | Data 7.2                                 ||||| Data 7.5   |
| Data 8.1   | Data 8.2                                             ||||||
| Data 9.1   | Data 9.2   | Data 9.3                     |||| Data 9.5   |
| Data 10.1  | Data 10.2  | Data 10.3                                |||||
| Data 11.1  | Data 11.2  | Data 11.3  |   |   | Data 11.4              ||

````````````````````````````````


```````````````````````````````` example(Insert Cols Span: 5) options(insert-col-5-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 |   |   | Header 1.5 |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 |   |   | Header 2.5 |
|------------|------------|------------|------------|---|---|------------|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   |   |   | Data 1.5   |
| Data 2.1               || Data 2.3   | Data 2.4   |   |   | Data 2.5   |
| Data 3.1                           ||| Data 3.4   |   |   | Data 3.5   |
| Data 4.1                                       ||||   |   | Data 4.5   |
| Data 5.1                                                         |||||||
|                                                                  |||||||
| Data 6.1   | Data 6.2               || Data 6.4   |   |   | Data 6.5   |
| Data 7.1   | Data 7.2                           |||   |   | Data 7.5   |
| Data 8.1   | Data 8.2                                             ||||||
| Data 9.1   | Data 9.2   | Data 9.3               ||   |   | Data 9.5   |
| Data 10.1  | Data 10.2  | Data 10.3                                |||||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4                    ||||

````````````````````````````````


```````````````````````````````` example(Insert Cols Span: 6) options(insert-col-6-2)
| Header 1.1  | Header 1.2  | Header 1.3  | Header 1.4  | Header 1.5  |
| Header 2.1  | Header 2.2  | Header 2.3  | Header 2.4  | Header 2.5  |
|---|---|---|---|---|
| Data 1.1  |    Data 1.2  | Data 1.3  | Data 1.4  | Data 1.5  |
| Data 2.1  ||   Data 2.3  | Data 2.4  | Data 2.5  |
| Data 3.1  |||  Data 3.4  | Data 3.5  |
| Data 4.1  |||| Data 4.5  |
| Data 5.1  |||||
| |||||
| Data 6.1  | Data 6.2  ||  Data 6.4  | Data 6.5  |
| Data 7.1  | Data 7.2  ||| Data 7.5  |
| Data 8.1  | Data 8.2  ||||
| Data 9.1  | Data 9.2  | Data 9.3  || Data 9.5  |
| Data 10.1  | Data 10.2  | Data 10.3  |||
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4  ||

.
| Header 1.1 | Header 1.2 | Header 1.3 | Header 1.4 | Header 1.5 |   |   |
| Header 2.1 | Header 2.2 | Header 2.3 | Header 2.4 | Header 2.5 |   |   |
|------------|------------|------------|------------|------------|---|---|
| Data 1.1   | Data 1.2   | Data 1.3   | Data 1.4   | Data 1.5   |   |   |
| Data 2.1               || Data 2.3   | Data 2.4   | Data 2.5   |   |   |
| Data 3.1                           ||| Data 3.4   | Data 3.5   |   |   |
| Data 4.1                                       |||| Data 4.5   |   |   |
| Data 5.1                                                   |||||   |   |
|                                                            |||||   |   |
| Data 6.1   | Data 6.2               || Data 6.4   | Data 6.5   |   |   |
| Data 7.1   | Data 7.2                           ||| Data 7.5   |   |   |
| Data 8.1   | Data 8.2                                       ||||   |   |
| Data 9.1   | Data 9.2   | Data 9.3               || Data 9.5   |   |   |
| Data 10.1  | Data 10.2  | Data 10.3                          |||   |   |
| Data 11.1  | Data 11.2  | Data 11.3  | Data 11.4              ||   |   |

````````````````````````````````


