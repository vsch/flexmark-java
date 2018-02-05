**flexmark-java extension for enumerated reference processing**

To create an element anchor or bookmark use the id attribute syntax of `{#type:reference}` after
the element to be referenced. Where `type` is a category used to keep separate numbering within
the category and `reference` is used to uniquely identify an element in the category. The anchor
id of the element will be `type:reference` and this must be used by the enumerated reference
label or link.

To refer to the element in the document use the enumerated reference elements:

1. A reference link syntax `[@type:reference]` converts to a link based on type pattern defined
   in enumerated reference format definition. The target of the link will be an element with a
   `{#type:reference}` attribute assigned to it. The text of the link will be that which is
   defined by the enumerated reference definition for `type`.

2. A reference text syntax `[#type:reference]` converts to text which is defined by the
   enumerated reference definition for `type`. This is to be used where the identifying text of
   the element within the document is needed.

3. One reference definition for each `type` must be included in the document. Syntax `[@type]:
   Label Text [#]` is used to define how to create a label for all elements of the given `type`.
   This usually includes the ordinal position of the element in the document.

   Text **following** the `[@type]: ` is used as the label with the `[@] or [#]` replaced by the
   ordinal number of the element in the document relative to other elements of the same `type`.
   The first element will have ordinal of 1, second 2, etc.

   If a type has no enumerated definition then `type [#]` will be used where `type` is the
   category type and `[#]` is the ordinal of the element in its category. It is equivalent to
   specifying `[@type]: type [#]` in the document.

For example:

```
![Flexmark Icon Logo](https://github.com/vsch/flexmark-java/raw/master/assets/images/flexmark-icon-logo%402x.png){#fig:test}   
[#fig:test]

![Flexmark Icon Logo](https://github.com/vsch/flexmark-java/raw/master/assets/images/flexmark-icon-logo%402x.png){#fig:test2}   
[#fig:test2]

| heading | heading | heading |
|---------|---------|---------|
| data    | data    |         |
[[#tbl:test] caption]
{#tbl:test}

See [@fig:test2]

See [@fig:test]

See [@tbl:test]

[@tbl]: Table [#].

[@fig]: Figure [#].
```

is equivalent to the following without having to manually keep track of numbering of individual
elements:

```
![Flexmark Icon Logo](https://github.com/vsch/flexmark-java/raw/master/assets/images/flexmark-icon-logo%402x.png){#fig:test}  
Figure 1.

![Flexmark Icon Logo](https://github.com/vsch/flexmark-java/raw/master/assets/images/flexmark-icon-logo%402x.png){#fig:test2}  
Figure 2.

| table |
|-------|
| data  |
[Table 1. caption]
{#tbl:test}

See [Figure 2.](#fig:test2)

See [Figure 1.](#fig:test)

See [Table 1.](#tbl:test)

```

Will render as:

```
<p><img src="https://github.com/vsch/flexmark-java/raw/master/assets/images/flexmark-icon-logo%402x.png" id="fig:test" /><br />
<span>Figure 1.</span></p>
<p><img src="https://github.com/vsch/flexmark-java/raw/master/assets/images/flexmark-icon-logo%402x.png" alt="Fig" id="fig:test2" /><br />
<span>Figure 2.</span></p>
<table id="tbl:test">
  <thead>
    <tr><th>table</th></tr>
  </thead>
  <tbody>
    <tr><td>data</td></tr>
  </tbody>
  <caption><span>Table 1.</span> caption</caption>
</table>
<p></p>
<p>See <a href="#fig:test2"><span>Figure 2.</span></a></p>
<p>See <a href="#fig:test"><span>Figure 1.</span></a></p>
<p>See <a href="#tbl:test"><span>Table 1.</span></a></p>
```

