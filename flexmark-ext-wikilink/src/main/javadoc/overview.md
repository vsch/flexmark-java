flexmark-java extension for wiki links

Converts references that are wrapped in `[[]]` into wiki links with optional text separated by
`|`.

Will also convert `![[]]` to image links if `IMAGE_LINKS` extension option is enabled.

Options:

- `DISABLE_RENDERING` default `false`, if true then rendering of wiki links is disabled and they
  will render as plain text of the element node

- `IMAGE_PREFIX` default `""`, prefix to add to wiki link page reference

- `IMAGE_LINKS` default `false`, true will enable `![[]]` image link syntax

- `IMAGE_FILE_EXTENSION` default `""`, extension to be added to wiki image file refs

- `LINK_FIRST_SYNTAX` default `false`, if true then `[[page ref|link text]]` syntax is used,
  otherwise `[[link text|page ref]]` syntax. Affects both link and image wiki references.

- `LINK_PREFIX` default `""`, prefix to add to wiki link page reference

- `LINK_FILE_EXTENSION` default `""`, extension to be added to wiki link page refs

