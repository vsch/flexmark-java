flexmark-java extension for application specific macros

Macros come in two forms: block and inline. Block macros can include multi-line content,
including blank lines. The content can contain other markdown elements and is parsed for inline
markdown content.

Inline macros can contain multi-line content but not blank lines because they are fully
contained in a single paragraph block.

Opening and closing block macro markers must be the first and last non-blank content on a line.
Other than this distinction there is no difference in handling macros by the extension between
inline and block macros. This means that inline macros will always be part of a paragraph block
content, even if the inline macro is the only content. If these inline macros are desired to be
processed as block macros then a node post processor should be used to transform these
paragraphs to block macros.

### Block Macros

```markdown
{{macroName attribute1=value attribute2 attribute3="value any content"}} 
content of the macro block, can include other block macros

blank lines and any other markdown element
{{/macroName}}
```

```markdown
{{macroName attribute3="value with possible spaces"}}content{{/macroName}}
```

Block Macros without content:

```markdown
{{macroName attribute1=value attribute2}}{{/macroName}}
```

```markdown
{{macroName attribute1=value attribute2 /}}
```

```markdown
{{macroName /}}
```

General form of an inline macro is the same as the block form except the opening and/or closing
marker has a blank or non-blank text prefix and/or non blank text suffix, or both opening and
closing markers are contained on the same line.

### Inline Macros:

A non-indenting space should be included before the opening and closing inline macro to prevent
it from being interpreted as a block macro.

:information_source: Non-indenting space below is replaced with `․`

```markdown
․{{macroName attribute4='another format'}} 
possibly containing other lines, but no blank lines 
․{{/macroName}}
```

```markdown
prefix text {{macroName attribute1=value}}
content possibly containing other lines, but no blank lines
lines {{/macroName}}
```

Single line inline macros:

```markdown
․{{macroName attribute3="value with possible spaces"}}content{{/macroName}}
```

Inline Macros without content:

```markdown
․{{macroName attribute1=value attribute2}}{{/macroName}}
```

```markdown
․{{macroName attribute1=value attribute2 /}}
```

```markdown
․{{macroName /}}
```

AST for block macro will start with the `Macro` node and if there was a closing marker then a
`MacroClose` will be the last child node.

Macro attributes, if any, are included as child blocks of the `Macro` node.
