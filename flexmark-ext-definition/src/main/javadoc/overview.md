flexmark-java extension for definition list processing

Converts definition syntax of
[Php Markdown Extra Def List](https://michelf.ca/projects/php-markdown/extra/#def-list) into
`<dl><dt></dt><dd></dd></dl>` html

Converts definition lists into definition list nodes. Definition terms have an optional `:` at
the end. Definition items can be preceded by `:` or `~`

Definition list is one or more definition terms with one or more definitions.

```markdown
Definition Term
: Definition of above term
: Another definition of above term
```

Definitions terms can have not definitions if they are not the last term before a definition:

```markdown
Definition Term without definition  
Definition Term  
: Definition of above term  
: Another definition of above term
```

A blank line between the definition term and definition, or the definition term and previous
definition term will create a "loose" definition by wrapping its text in `<p></p>`.

```markdown
Definition Term

: Loose Definition of above term  
: Tight definition of above term

: Another loose definition term
```

Will create:

```html
<dl>
  <dt>Definition Term</dt>
  <dd><p>Loose Definition of above term</p></dd>
  <dd>Tight definition of above term</dd>
  <dd><p>Another loose definition term</p></dd>
</dl>
```

Lazy continuation of definitions is supported and definition terms which follow a definition
must be separated from it by a blank line.

```markdown
Definition Term  
: Definition for Definition Term

Another Definition Term  
: Definition for Another Definition Term
```

Inline markdown is allowed in both terms and definitions. Definitions can contain any other
markdown elements provided these are indented as per list indentation rules.

Multiple definition terms cannot be separated by blank lines since all but the last term will be
treated as paragraph text:

```markdown
Not a definition term.

Neither is this.

Definition Term
Another Definition Term
: Definition
```

