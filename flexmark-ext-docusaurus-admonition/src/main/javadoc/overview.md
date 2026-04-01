# flexmark-ext-docusaurus-admonition

Extension for [Docusaurus Admonitions](https://docusaurus.io/docs/markdown-features/admonitions).

This extension renders Docusaurus admonitions into following HTML:

```html
<div class="docusaurus-admonition docusaurus-admonition-type">
    <!-- any legal markdown here -->
</div>
```

Please note that `-type` in class name is replaced with actual type of the admonition eg. `note`, `tip` etc.

Nested admonitions are supported.

Custom admonitions are supported by replacing `-type` in class with custom adminition type. It means that the following Markdown:

```md

:::my-custom-admonition
Text here.
:::

```

Will be rendered as:

```html
<div class="docusaurus-admonition docusaurus-admonition-my-custom-admonition">
    <p>Text here.</p>
</div>
```

Admonitions' titles are not supported now.
