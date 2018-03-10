**Extension for tables using "|" pipes with optional spanning columns and table caption**

Converts table elements of the form:

```markdown

|    Heading Centered    | Heading Left Aligned   |  Heading Centered  |   Heading Right Aligned |
|------------------------|:-----------------------|:------------------:|------------------------:|
| Cell text left aligned | Cell text left aligned | Cell text centered | Cell text right aligned |
| cell 21                | cell 22                |      cell 22       |                 cell 22 |

```

Depending on the specified option tables may have multiple header rows, number of columns in
table body does not need to match header or separator line columns and column spans can be
specified by adding consecutive `|` at the end of the cell. Each pipe represents column span
count `||` spans two columns, `|||` three, etc.

Additionally, missing columns may be either left as is or filled in with empty elements.

When column span syntax is enabled, empty table cells must have at least one space. Otherwise
they will be interpreted as column spans for the previous cell.

:warning: Table elements must be preceded by a blank line to be processed by this extension.
