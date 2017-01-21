**flexmark-java extension** for defining **abbreviations** and turning appearance of these
abbreviations in text into `<abbr>` with titles consisting of the expansion of the abbreviation

Syntax:

```markdown
This is an abbr of the abbreviation word

*[abbr]: abbreviation
```

Results in:

```html
<p>This is an <abbr title="abbreviation">abbr</abbr> of the abbreviation word</p>
```

See {@link com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension}
