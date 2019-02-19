**flexmark-java markdown formatter module**

Outputs AST as formatted Markdown with user configurable options.

Options for output control, pass via `DataHolder` taking method `parse()` method. The following
options are available:

Defined in `TaskListExtension` class:

* `FlexmarkHtmlParser.LIST_CONTENT_INDENT`, default `true`, continuation lines of list items and
  definitions indent to content column otherwise 4 spaces
* `FlexmarkHtmlParser.SETEXT_HEADINGS`, default `true`, if true then use Setext headings for h1
  and h2
* `FlexmarkHtmlParser.OUTPUT_UNKNOWN_TAGS`, default `false`, when true unprocessed tags will be
  output, otherwise they are ignored
* `FlexmarkHtmlParser.ORDERED_LIST_DELIMITER`, default `'.'`, delimiter for ordered items
* `FlexmarkHtmlParser.UNORDERED_LIST_DELIMITER`, default `'*'`, delimiter for unordered list
  items
* `FlexmarkHtmlParser.DEFINITION_MARKER_SPACES`, default `3`, min spaces after `:` for
  definitions
* `FlexmarkHtmlParser.MIN_TABLE_SEPARATOR_COLUMN_WIDTH`, default `1`, min 1, minimum number of
  `-` in separator column, excluding alignment colons `:`
* `FlexmarkHtmlParser.MIN_TABLE_SEPARATOR_DASHES`, default `3`, min 3, minimum separator column
  width, including alignment colons `:`
* `FlexmarkHtmlParser.MIN_SETEXT_HEADING_MARKER_LENGTH`, default `3`, min 3, minimum setext
  heading marker length
* `FlexmarkHtmlParser.CODE_INDENT`, default 4 spaces, indent to use for indented code
* `FlexmarkHtmlParser.EOL_IN_TITLE_ATTRIBUTE`, default `" "`, string to use in place of EOL in
  image and link title attribute.
* `FlexmarkHtmlParser.NBSP_TEXT`, default `" "`, string to use in place of non-break-space
* `FlexmarkHtmlParser.THEMATIC_BREAK`, default `"*** ** * ** ***"`, `<hr>` replacement

Uses the excellent [jsoup](https://jsoup.org/) HTML parsing library and emoji shortcuts using
[Emoji-Cheat-Sheet.com](https://www.webfx.com/tools/emoji-cheat-sheet)

