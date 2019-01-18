Converts attributes `{...}` syntax into attributes AST nodes and adds an attribute provider to
set attributes for immediately preceding sibling element during HTML rendering.

* Backward compatible with `ASSIGN_TEXT_ATTRIBUTES` option is `false`

  Applied to the preceding element, if that element is plain text then attributes are applied to
  the parent element of the text.

  If the attributes element is the first element of a paragraph then the attributes will be
  applied to the paragraph's parent element. Effectively putting attributes element first in a
  paragraph allows you to set the attributes of list items, block quotes or other paragraph
  containers.

* Default Extended Behavior with `ASSIGN_TEXT_ATTRIBUTES` option is `true`

  If an attribute element is spaced from the previous plain text element `some text
  {attributes}` then attributes are for the parent of the text. If they are attached to the text
  element `some text{attributes}` then they are assigned to the immediately preceding plain text
  span. Within an inline element same rules apply: `**bold text {attr}**` are for the strong
  emphasis span, while `**bold text{attr}**` will wrap the text between the strong emphasis
  tags, in a span with given attributes.

  If a plain text span is interrupted by an HTML comment then it is considered as two separate
  plain text spans. ie. `some <!---->text{attr}` will result in `some <!----><span
  attr>text</span>` rendering.

The attributes is a space separated list of attribute syntax of one of the following:

* `name=value`
* `name='value'`
* `name="value"`
* `#id`
* `.class`

Attribute which starts with `#` such as `#id-string` is equivalent to `id="id-string"` and
`.class-name` are equivalent to `class="class-name"`

**NOTE**: Handling of multiple value assignment for attributes depends on its name:

* `class` values are accumulated as a space (` `) separated list.
* `style` values are accumulated as a semicolon (`;`) separated list.
* all others override any previous values of the same name.

Setting `USE_EMPTY_IMPLICIT_AS_SPAN_DELIMITER` to `true`, will treat `{.}` or `{#}` as markers
for start of closest matching attributes node to give greater control of where attributes are
attached in text.
