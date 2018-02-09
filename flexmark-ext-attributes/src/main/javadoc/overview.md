**flexmark-java extension for attributes processing**

Converts attributes `{...}` syntax into attributes AST nodes and adds an attribute provider to
set attributes for immediately preceding node sibling node.

If the preceding sibling node is `Text` then the attributes are applied to the parent node.

If the attributes node is the first node of a paragraph then the attributes will be applied to
the paragraph's parent node. Effectively putting attributes element first in a paragraph allows
you to set the attributes of list items, block quotes or other paragraph containers.

The attributes is a space separated list of attribute syntax of one of the following:

* `name=value`
* `name='value'`
* `name="value"`
* `#id`
* `.class`

AttributesNode which start with `#` such as `#id-string` are equivalent to `id="id-string"` and
`.class-name` are equivalent to `class="class-name"`

NOTE: class values are accumulated as a space separated list. Other attributes override any
previous values.
