flexmark-java
=============

&nbsp;<details id="version-history"><summary>**Version History**</summary>

[TOC]: # " "

- [To Do](#to-do)
- [Next 0.50.28](#next-05028)
- [0.50.26](#05026)
- [0.50.24](#05024)
- [0.50.22](#05022)
- [0.50.20](#05020)
- [0.50.18](#05018)
- [0.50.16](#05016)
- [Next 0.42.14](#next-04214)
- [0.42.12](#04212)
- [0.42.10](#04210)
- [0.42.8](#0428)
- [0.42.6](#0426)
- [0.42.4](#0424)
- [0.42.2](#0422)
- [0.42.0](#0420)
- [0.40.34](#04034)
- [0.40.32](#04032)
- [0.40.30](#04030)
- [0.40.28](#04028)
- [0.40.26](#04026)
- [0.40.24](#04024)
- [0.40.22](#04022)
- [0.40.20](#04020)
- [0.40.18](#04018)
- [0.40.16](#04016)
- [0.40.14](#04014)
- [0.40.12](#04012)
- [0.40.10](#04010)
- [0.40.8](#0408)
- [0.40.6](#0406)
- [0.40.4](#0404)
- [0.40.2](#0402)
- [0.40.0](#0400)
- [0.35.10](#03510)
- [0.35.8](#0358)
- [0.35.6](#0356)
- [0.35.4](#0354)
- [0.35.2](#0352)
- [0.35.0](#0350)
- [0.34.58](#03458)
- [0.34.56](#03456)
- [0.34.53](#03453)
- [0.34.52](#03452)
- [0.34.51](#03451)
- [0.34.50](#03450)
- [0.34.49](#03449)
- [0.34.48](#03448)
- [0.34.46](#03446)
- [0.34.44](#03444)
- [0.34.42](#03442)
- [0.34.40](#03440)
- [0.34.38](#03438)
- [0.34.36](#03436)
- [0.34.34](#03434)
- [0.34.32](#03432)
- [0.34.30](#03430)
- [0.34.28](#03428)
- [0.34.26](#03426)
- [0.34.24](#03424)
- [0.34.22](#03422)
- [0.34.20](#03420)
- [0.34.18](#03418)
- [0.34.16](#03416)
- [0.34.14](#03414)
- [0.34.12](#03412)
- [0.34.10](#03410)
- [0.34.8](#0348)
- [0.34.6](#0346)
- [0.34.4](#0344)
- [0.34.2](#0342)
- [0.34.0](#0340)
- [0.32.24](#03224)
- [0.32.22](#03222)
- [0.32.20](#03220)
- [0.32.18](#03218)
- [0.32.16](#03216)
- [0.32.14](#03214)
- [0.32.12](#03212)
- [0.32.10](#03210)
- [0.32.8](#0328)
- [0.32.6](#0326)
- [0.32.4](#0324)
- [0.32.2](#0322)
- [0.32.0](#0320)
- [0.30.0](#0300)


&nbsp;</details>

### To Do

* [ ] Add: `<!-- @formatter:on -->` and `<!-- @formatter:on -->` tags to `Formatter` for
      controlling non-formatting regions.
* [ ] Convert anonymous classes to lambda where possible.

Next 0.50.28
------------

* Add: `DocxRenderer.BULLET_LIST_STYLE` default `"BulletList"`, numbering style to use for
  bullet list item paragraph.
* Add: `DocxRenderer.NUMBERED_LIST_STYLE` default `"NumberedList"`, numbering style to use for
  numbered list item paragraph.

0.50.26
-------

* Fix: docx converter to use `BulletList` and `NumberedList` numbering list styles for list
  conversions to allow easy list item styling for multi-level lists
  * Add: `DocxConverterEmpty` to samples for generating `flexmark-empty-template.docx` from
    `empty.md` and `empty.xml`

0.50.24
-------

* Fix: update docx4j to version 8.1.2
* Fix: update jsoup to version 1.11.3

0.50.22
-------

* Fix: Attributes with spaces after `{` generated wrong previous text offsets

0.50.20
-------

* Fix: [#357, HTML to markdown and removed nested list]
* Remove: `FlexmarkHtmlConverter.EXT_TABLES` unused conversion option.
* Add: allow attributes after fenced code info string as last non-blank text after the info
  string.
  * Add: `AttributesExtension.FENCED_CODE_INFO_ATTRIBUTES` default `true`, if false will not
    parse attributes after the fenced code info string

0.50.18
-------

* Add: `flexmark-html2md-converter` module which implements HTML to Markdown conversion with an
  extension API to allow customizing the conversion process. Sample:
  [HtmlToMarkdownCustomizedSample.java]
  * Fix: [#313, Ability to override tags processing in FlexmarkHtmlParser]
* Fix: deprecate the old `flexmark-html-parser` classes

0.50.16
-------

* Fix: GitLab block quotes should have `>>>` as termination, not `<<<`, macros are still
  terminated by `>>>` to allow nested block quotes in macros. Affects:
  * HTML converter
  * Formatter
* Fix: [#351, Is there any special format requirement for processing html data to markdown]
* Fix: [#349, Translation Helper bugs], mix-up between anchors and other non-translating
  elements after translation.
* Fix: [#349, Translation Helper bugs]
* Fix: [#348, WRAP\_AUTO\_LINKS defaults to false, Markdown loses a potential useful link]
* Break: change `FlexmarkHtmlParser.WRAP_AUTO_LINKS` default to true
* Fix: add `SpecReader.readExamples(String, String)` which takes resource name and file URL to
  allow for non-standard resource file locations.
* Fix: html to markdown string not add EOL by default.
* Break: make Java 8 minimum version and use JDK 8 for compilation
  * Fix: IntelliJ Migration contained in [migrate flexmark-java 0_42_x to 0_50_0.xml], to use:
    * copy to IntelliJ application settings to `migration` subdirectory
    * if you have the project which you want to migrate open, then close it
    * open the project in IntelliJ Ultimate or Community
    * update the flexmark-java dependency version to 0.42.0 (or later) and make sure the new
      library is downloaded/updated in the project.
    * use menu `Refactor` > `Migrate...` or search everywhere (double shift activation) and
      search for migrate action.
    * select `migrate flexmark-java 0.42.x to 0.50.0`
    * press `Run`
    * in the refactoring preview tool window that opens hit `Do Refactor`
  * Remove classes from utils which are implemented in Java 8
  * replace `RunnableValue` with `Supplier`, requires changing `run()` with `get()`
  * replace `ValueRunnable` with `Consumer`, requires changing `run()` with `accept()`
  * replace `Factory` with `Supplier`, requires changing `create()` with `get()`
  * replace `Computable` with `Function`, requires changing `compute()` with `apply()` and
    reversing template parameters.
  * replace `ComputableFactory` with `Function`, requires changing `create()` with `apply()` and
    reversing template parameters.
  * replace `ComputeFactory` with `Function`, requires changing `create()` with `apply()` and
    reversing template parameters.
  * replace all factory interfaces to be compatible with `Function` interface and change
    `create()` with `apply()`
  * replace `com.vladsch.flexmark.util.collection.Consumer` with `java.util.function.Consumer`
  * clean up `DataKey` and `DataHolder` related classes
    * move `com.vladsch.flexmark.util.collection.DataValueFactory` to
      `com.vladsch.flexmark.util.DataValueFactory`
    * replace `com.vladsch.flexmark.util.collection.DynamicDefaultKey` with
      `com.vladsch.flexmark.util.options.DataKey`
    * change `MutableDataHolder.getOrCompute(DataKey<T>, DataValueFactory<T>)` to
      `MutableDataHolder.getOrCompute(DataKey<T>)` with the data value factory taken from they
      key.
  * remove unused `ItemIndexSetMap`, use `IndexedItemSetMap` instead
  * move `flexmark-util` Node related classes and interfaces to `com.vladsch.flexmark.util.ast`
    * move `com.vladsch.flexmark.util.NodeTracker` to
      `com.vladsch.flexmark.util.ast.NodeTracker`
    * move `com.vladsch.flexmark.util.BlockTracker` to
      `com.vladsch.flexmark.util.ast.BlockTracker`
    * move `com.vladsch.flexmark.util.KeepType` to `com.vladsch.flexmark.util.ast.KeepType`
    * move `com.vladsch.flexmark.util.IParse` to `com.vladsch.flexmark.util.ast.IParse`
    * move `com.vladsch.flexmark.util.IRender` to `com.vladsch.flexmark.util.ast.IRender`
    * move `com.vladsch.flexmark.util.collection.NodeClassifierVisitor` to
      `com.vladsch.flexmark.util.ast.NodeClassifierVisitor`
    * move `com.vladsch.flexmark.util.collection.NodeCollectingVisitor` to
      `com.vladsch.flexmark.util.ast.NodeCollectingVisitor`
    * move `com.vladsch.flexmark.util.collection.ClassifyingNodeTracker` to
      `com.vladsch.flexmark.util.ast.ClassifyingNodeTracker`
    * move `com.vladsch.flexmark.util.mappers.NodeClassifier` to
      `com.vladsch.flexmark.util.ast.NodeClassifier`
  * move data key/set/holder classes to `flexmark.util.data` package from
    `flexmark.util.options` package
    * move `com.vladsch.flexmark.util.options.DataHolder` to
      `com.vladsch.flexmark.util.data.DataHolder`
    * move `com.vladsch.flexmark.util.options.MutableDataSet` to
      `com.vladsch.flexmark.util.data.MutableDataSet`
    * move `com.vladsch.flexmark.util.options.MutableScopedDataSet` to
      `com.vladsch.flexmark.util.data.MutableScopedDataSet`
    * move `com.vladsch.flexmark.util.options.ScopedDataSet` to
      `com.vladsch.flexmark.util.data.ScopedDataSet`
    * move `com.vladsch.flexmark.util.options.DataValueFactory` to
      `com.vladsch.flexmark.util.data.DataValueFactory`
    * move `com.vladsch.flexmark.util.options.MutableDataHolder` to
      `com.vladsch.flexmark.util.data.MutableDataHolder`
    * move `com.vladsch.flexmark.util.options.DataSet` to
      `com.vladsch.flexmark.util.data.DataSet`
    * move `com.vladsch.flexmark.util.options.MutableDataSetter` to
      `com.vladsch.flexmark.util.data.MutableDataSetter`
    * move `com.vladsch.flexmark.util.options.DataKey` to
      `com.vladsch.flexmark.util.data.DataKey`
* Add: `LineFormattingAppendable` and `LineFormattingAppendableImpl`
  * Fix: deprecate `FormattingAppendable` to be replaced by `LineFormattingAppendable`
  * Fix: deprecate `FormattingAppendableImpl` to be replaced by `LineFormattingAppendableImpl`
  * Fix: replace all uses of `FormattingAppendable` by `LineFormattingAppendable`
  * Fix: replace all uses of `FormattingAppendableImpl` by `LineFormattingAppendableImpl`
  * Delete: `FormattingAppendable` and `FormattingAppendableImpl`
* Fix: Factor out BasedSequenceImpl functionality that does not depend on `BasedSequence` and
  can be applied to any CharSequence into its own `RichCharSequence` interface with default
  abstract implementation in `RichCharSequenceBase` and implementation in
  `RichCharSequenceImpl`.

Next 0.42.14
------------

* Fix: [#351, Is there any special format requirement for processing html data to markdown]
* Fix: HTML parser converts `a` tags in preformatted text to links, should convert to URL only

0.42.12
-------

* Fix: [#349, Translation Helper bugs], mix-up between anchors and other non-translating
  elements after translation.

0.42.10
-------

* Fix: [#349, Translation Helper bugs]
* Fix: [#348, WRAP\_AUTO\_LINKS defaults to false, Markdown loses a potential useful link]

0.42.8
------

* Add: PDF converter landscape sample [PdfLandscapeConverter.java]
* Fix: revert to OpenHtmlToPDF version 0.0.1-RC15 which is the last Java 7 byte code version
* Fix: fill missing columns StringIndexOutOfBoundsException when prev cell consists of
  consecutive pipes without text
* Fix: add explicit `Locale.US` to `String.format()` when using `%d` for integers to prevent
  conversion to arabic on Java 11.

0.42.6
------

* Fix: [#338, getLineNumber incorrect with Windows end of line separators]
  * merge: [#339, PR: Fix to line number when using Windows EOL characters.] thanks to

0.42.4
------

* Fix: Trailing URI prefix only auto-links do not get parsed

0.42.2
------

* Fix: [#334, CR line separators don't produce line break nodes],
  * merge: [#335, PR: Fix CR-only line separator handling] thanks to **[Kijimuna]**

0.42.0
------

* Fix: [#332, withOptions forgets about old link resolvers]
  * Break: move `com.vladsch.flexmark.Extension` to
    `com.vladsch.flexmark.util.builder.Extension`
  * Fix: IntelliJ Migration contained in [migrate flexmark-java 0_40_x to 0_42_0], to use:
    * copy to IntelliJ application settings to `migration` subdirectory
    * if you have the project which you want to migrate open, then close it
    * open the project in IntelliJ Ultimate or Community
    * update the flexmark-java dependency version to 0.42.0 (or later) and make sure the new
      library is downloaded/updated in the project.
    * use menu `Refactor` > `Migrate...`
    * select `migrate flexmark-java 0.42.x to 0.50.0`
    * press `Run`
    * in the refactoring preview tool window that opens hit `Do Refactor`
  * Add: common builder base to handle unloading extensions
  * Add: `BuilderBase.RELOAD_EXTENSIONS` default `true`, if `true` then will unload loaded
    extension from the builder when `withOptions()` is used on: `Parser`, `HtmlRenderer`,
    `Formatter` or `DocxRenderer`. If `false` then will not reload extensions which are already
    loaded. The latter is slightly faster because loaded extensions are not re-loaded but will
    not change extension configuration for loaded extensions based on new options.
  * Fix: `HtmlParser.Builder` constructor with options does not preserve all already loaded
    extensions and custom api factories
  * Fix: `Parser.Builder` constructor with options does not preserve all already loaded
    extensions and custom api factories
  * Fix: `Formatter.Builder` constructor with options does not preserve all already loaded
    extensions and custom api factories
  * Fix: `DocxRenderer.Builder` constructor with options does not preserve all already loaded
    extensions and custom api factories
  * Add: `BuilderBase.UNLOAD_EXTENSIONS`, default `Extension.EMPTY_LIST`, all extensions in the
    list will be removed. Used only when using `withOptions()` and providing new options with
    this key set. Used during testing to remove default extensions.

0.40.34
-------

* Fix: [#328, Html2mark - missing newline when paragraph followed by div]
* Fix: [#331, Ability to replace empty \<p\> with \<br\> during html2mark conversion]
* Fix: NPE in `TableParagraphPreProcessor`
* Fix: `AutolinkNodePostProcessor` processing links out of order causing sequence end/start
  reversal.

0.40.32
-------

* Fix: compound enumerated references in attributes ids and outside of headings to output with
  last enumerated reference ordinal.

0.40.30
-------

* Fix: limit `EnumeratedReferenceBlock` to single line of text without processing other block
  elements.
* Fix: change `Abbreviations` to custom block parser from paragraph pre-processor. Allows
  abbreviation definitions without preceding blank line.

0.40.28
-------

* Add: enumerated reference text in heading to be used with only a format reference, the id is
  taken from the heading attributes.
* Add: compound enumerated references for creating legal numbering for enumerated references.

0.40.26
-------

* Fix: upgrade dependencies
  * OpenHtmlToPdf -> 0.0.1-RC19
  * docx4j -> 6.1.2
* Add: parse int or default to `Utils.java`

0.40.24
-------

* Break: test case related classes changed to allow providing URL string for the file resource
  used in the text instead of relying on heuristic conversion of resource URL to file path.
* Fix: [#326, flexmark-html-parser - multiple \<code\> inside \<pre\> bug]
* Fix: `EscapedCharacterExtension` disabled for `DoNotLinkDecorate` nodes if another extension
  specified its `NodePostProcessorFactory` as dependent on `Text` with `DoNotDecorate` and
  `DoNotLinkDecorate` and the extension was added after `EscapedCharacterExtension`
* Add: `Parser.WWW_AUTO_LINK_ELEMENT`, default `false`. If `true` then strings of the form
  `<www.someurl>` will be parsed as auto-links
* Fix: copy `Extensions` from `flexmark-profile-pegdown` module to `PegdownExtensions` in core
  parser. Old class still available and extends the new named class.
* Add: `ParserEmulationProfile.getOptions(DataHolder)` to `ParserEmulationProfile` to allow
  using current option values in deciding what the settings should be for the profile. Used by
  `ParserEmulationProfile.PEGDOWN`
* Fix: [#323, TOC generation improvement], profile setting HeaderId generation to `false` even
  though `PegdownExtensions.ANCHORLINKS` is not used
* Add: css option to PDF export and use as default css from [#323, TOC generation improvement],
  thanks to @jvdvegt
  * Add: `PdfConverterExtension.DEFAULT_CSS` data key with default value of embedded CSS.
  * Add: `PdfConverterExtension.embedCss(String html, String css)` will embed the css in html by
    inserting it between `<head>` and `</head>` wrapped in `<style>` and `</style>`. Does its
    best to generate valid HTML, use it if your don't want to bother creating your own HTML
    document with embedded CSS.

  :information_source: Default CSS is only added if
  `PdfConverterExtension.exportToPdf(OutputStream, String, String, DataHolder)` is used to
  provide options. For all other calls you need to embed the default css into your HTML string
  before exporting to PDF.
* Fix: HTML parser link/image conversion to ref link and reference when cannot create a valid
  reference fallback to generating explicit link instead.
* Fix: HTML parser ignoring `NONE` for `FlexmarkHtmlParser.EXT_INLINE_LINK` and
  `FlexmarkHtmlParser.EXT_INLINE_IMAGE` options
* Fix: HTML parser to add blank line before block quote and aside if they are not the first
  child of their parent.
* Fix: HTML parser, divs always generated line before and after, instead of before if not first
  child element and after if not last child element.
* Add: `IParse.transferReferences(Document, Document, Boolean)`, to allow overriding repository
  `KeepType` selected copying behaviour for references already defined in the destination
  document.
* Deprecate: `IParse.transferReferences(Document, Document)`, in favour of
  `IParse.transferReferences(Document, Document, Boolean)`

0.40.22
-------

* Fix: merge util tests from [@James-Adam](https://github.com/James-Adam) and fix bugs
* Fix: change to `MutableDataSet.set(DataKey<? extends T>, T)`

0.40.20
-------

* Fix: [#316, Github user extension incorrectly formats some text]
  * Add: test to make sure previous character to `@` is not `isUnicodeIdentifierPart()`, `-` nor
    `.`

* Add: `FlexmarkHtmlParser` options:
  * Fix: [#318, Ability to disable table caption in FlexmarkHtmlParser],
    * Add: `TABLE_CAPTION` option as a convenience alias for `Formatter.FORMAT_TABLE_CAPTION`.
      Add documentation to
      [Extensions: Html To Markdown](../../wiki/Extensions#html-to-markdown)
  * Fix: [#314, Ability to override character replacements map in FlexmarkHtmlParser],
    * Add: `TYPOGRAPHIC_REPLACEMENT_MAP` option key taking a `Map<String,String>`, if not empty
      will be used instead of the bundled map. Any typographic characters or HTML entities
      missing from the map will be output without conversion. If you want to suppress a
      typographic so it is not output add `""` to its mapped value.
  * Fix:
    [#317, FlexmarkHtmlParser outputs extra newline when converting nested \<ol\>, \<ul\> lists]
  * Fix: when rendering raw HTML for inline elements, have to escape contained special markdown
    characters.
  * Add: `FOR_DOCUMENT` option to use as document into which the generated Markdown will be
    inserted. This is to allow re-use matching references generated from HTML instead of new
    ones.
  * `EXT_INLINE_LINK` and `EXT_INLINE_IMAGE` option default `LinkConversion.MARKDOWN_EXPLICIT`,
    specifies type of link and image conversion to apply: `NONE`, `MARKDOWN_EXPLICIT`,
    `MARKDOWN_REFERENCE`, `TEXT`, `HTML`

* Add: Remove reliance on [YouTrack: IDEA-207453] and instead change resource file URL to path
  in `SpecReader` and add a message to all example tests with the file URL with `:xxx` where
  `xxx` is the line number of the spec example in the file.

* This will print the source location of the test in the console. Clicking the link goes right
  to the spec source file and line of the failed test if [Awesome Console] plugin is installed.

0.40.18
-------

* Add: `EmbeddedAttributeProvider` documentation and add it to the provider's list by default
  unless `HtmlRenderer.EMBEDDED_NODE_PROVIDER` is set to false. Add attributes to nodes in the
  AST by inserting a `EmbeddedAttributeProvider.EmbeddedNodeAttributes` node with the desired
  attributes. See: [NodeInsertingPostProcessorSample.java] for example.

* Add: document that `DocxRenderer` emoji with GitHub preferred is not able to download images,
  compiling `ImageUtils` library with Java 8 eliminates the problem.

* Add: resource file URL to `SpecReader` and add a message to all example tests with the file
  URL with `:xxx` where `xxx` is the line number of the spec example in the file.

* This will print the source location of the test in the console. Clicking the link goes right
  to the spec source file and line of the failed test. Unfortunately this URL will only work in
  IntelliJ when JetBrains add this to console to handle such `file://` URLs:
  [YouTrack: IDEA-207453]

* Fix: [#310, PR: Change URL of GitHub CDN] thanks to @benelog

* Fix: `AsideExtension` option keys to be dynamic data keys dependent on corresponding Parser
  block quote options for their defaults.

* :warning: This can potentially break code relying on versions of the extension before
  `0.40.18` because parsing rules can change depending on which block quote options are changed
  from their default values.

* To ensure independent options for aside blocks and block quotes, set aside options explicitly.
  The following will set all aside options to default values, independent from block quote
  options:

      .set(EXTEND_TO_BLANK_LINE, false)
      .set(IGNORE_BLANK_LINE, false)
      .set(ALLOW_LEADING_SPACE, true)
      .set(INTERRUPTS_PARAGRAPH, true)
      .set(INTERRUPTS_ITEM_PARAGRAPH, true)
      .set(WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH, true)

* Deprecate: `Parser.BLOCK_QUOTE_TO_BLANK_LINE`, use more mnemonic
  `Parser.BLOCK_QUOTE_EXTEND_TO_BLANK_LINE`

* Deprecate: `CustomNode` and `CustomBlock`. `Block` and `Node` should be used directly. The
  library aims to make no distinction between core and extension implementations, these classes
  add no useful information.

* Deprecate: BaseSequence had old named functions which were misleading and duplicated under
  proper names:
  * `countChars()` -> `countLeading()`
  * `countCharsNot()` -> `countLeadingNot()`
  * `countCharsReversed()` -> `countTrailing()`
  * `countNotCharsReversed()` -> `countTrailingNot()`

* First of all they only counted leading characters which the name did not imply. Second they
  were duplicated.

* Add character counting functions:

  * `countOfAny()`
  * `countOfAnyNot()`

* Fix: `DefinitionExtension` does not correctly set the child parse column, causing list items
  to be expecting 1 extra space for child item recognition.

* Add: `ExtensionConversion.NONE` to suppress any output from corresponding element

* Add: `FlexmarkHtmlParser.EXT_MATH`, default `ExtensionConversion.HTML`, for selecting `<math>`
  tag processing. For now only `MARKDOWN` does nothing useful. Later it will be used to convert
  math ml to GitLab math inline element.

0.40.16
-------

* Fix: `EnumeratedReferenceExtension` would process GitHub issue link text as enumerated
  reference. Now enumerated `reference` must start with a non-digit character to be interpreted
  as enumerated reference.
* Fix: Reference Text, Reference Id, Link Text and Image Alt Text child text was not trimmed in
  the AST causing the parent text range to be smaller than the contained children's text range.
* Add: `PdfConverterExtension.PROTECTION_POLICY`, default `null`. Set the protection policy for
  the generated PDF document.
  * Merge: [#306, PR: Add password protection support], send appreciations to
    **[niksw7](https://github.com/niksw7)**
* Add: `youtu.be` link handling to `YouTubeLinkExtension`
  * Merge:
    [#305, PR: add new youtube link style support to flexmark-ext-youtube-embedded for youtu.be/xyz(?t=123)],
    send appreciations to **[jjYBdx4IL](https://github.com/jjYBdx4IL)**

0.40.14
-------

* Fix: `AutolinkExtension` removing leading `Typographic` nodes when the first link occurs in
  text following the typographic node.
* Add: PDF converter sample with non-latin character set rendering information.
* Fix: missing `simple_smile` emoji cheat sheet shortcut

0.40.12
-------

* Fix: [#300, Typography extension breaks some auto links]
* Add: `TypographicText` interface to mark nodes which hold text which is replaced with
  typographic for rendering but treated as text for decoration processing. For now
  `AutolinkExtension` is the only one making use of it to prevent typographic smarts from
  breaking up a link and causing part of it to be left out of the URL.

0.40.10
-------

* Fix: GitLab inline math parser to allow multi-line inline math elements
* Fix: [#295, CoreNodeFormatter does not descend into children on Link nodes] , more like
  kludge, `Formatter.OPTIMIZED_INLINE_RENDERING` when `false` and not translating to always
  render children of link text.

0.40.8
------

* Fix: change `AttributeProviderFactory` extensions to eliminate duplicate registrations of
  factories.
* Fix: `AttributesExtension` to assign attributes to explicit/refs links/images
* Fix: [#299, FlexmarkHtmlParser produces extra empty list item for eclosing \</p\> element]

0.40.6
------

* Fix: `MarkdownTable.appendTable(FormattingAppendable)` to set
  `FormattingAppendable.ALLOW_LEADING_WHITESPACE` so indentation prefix is not eliminated.
* Fix: `FormattingAppendableImpl` to not skip pending spaces if
  `FormattingAppendable.ALLOW_LEADING_WHITESPACE` is selected, these will be prefixed before
  indent.
* Add: `FlexmarkHtmlParser.SKIP_LINKS`, default `false`. When true links are converted to text
  part of the link.
* Add: `DoNotAttributeDecorate` interface to be implemented by all text nodes which do not
  result in span-like rendering of their text, such as `TypographicSmarts`, `TypographicQuotes`
  and `SoftLineBreak` but will erroneously create a break in the text and mess up attribute
  assignment to text spans.
* Add: `AttributesExtension.WRAP_NON_ATTRIBUTE_TEXT`, default `true`. When `false` does not wrap
  nodes marked as `DoNotAttributeDecorate` in `TextBase`. When `true`, will allow intuitive
  attribute processing for text containing `DoNotAttributeDecorate` as single text span instead
  of one interrupted by the typographic smarts nodes.
* Fix: `AttributesExtension` incorrectly parsed invalid empty sequences and absorbed the text
  instead of skipping it. ie. `{}`, `{ }`, `{#}` and `{.}`
* Add: `AttributesExtension.USE_EMPTY_IMPLICIT_AS_SPAN_DELIMITER`, default `false`. When set to
  `true` will treat `{#}` or `{.}`, without embedded spaced, as start attribute span delimiter
  to mark start of attribute assignment to text between `{.}` or `{#}` and the matching
  attributes element.

0.40.4
------

* Fix: [#295, CoreNodeFormatter does not descend into children on Link nodes]
  * `Link`, `LinkRef`, `Image` and `ImageRef` node formatter renderer now descends into child
    nodes during all formatting, not just translation formatting.
* Add: `Formatter.OPTIMIZED_INLINE_RENDERING` default `false`. When set to `true` will use
  previous rendering for links and images which appends the node characters without descending
  into child nodes.

0.40.2
------

* Fix: [#294, BlankLine nodes within BlockQuote change in behaviour]
  * remove blank lines from AST for `BlockQuote` and `AsideBlock` nodes if
    `Parser.BLANK_LINES_IN_AST` is not set.
* Fix: [#292, 0.40.0: JUnit is in the compile scope]
  * remove unnecessary ast collecting visitor in `DocumentParser`
  * add test scope to flexmark pom
* Fix: [#293, YamlFrontMatterBlock rendered as markdown does not preserve nested lists]
  * Add: `YamlFrontMatterValue` node containing yaml value(s) inserted as children of
    `YamlFrontMatterNode`
  * Fix: change stored key as `BasedSequence` instead of string, can be retrieved as
    `YamlFrontMatterNode.getKeySequence()`
  * Add: code to return `List<String>` from child nodes of `YamlFrontMatterNode`
  * Fix: resolve offsets in `YamlFrontMatterNode` and `YamlFrontMatterValue` nodes.

0.40.0
------

* Fix: Java9+ Compatibility, IntelliJ Migration contained in [migrate 0_35_x to 0_40_0.xml], to
  use:

  * copy to IntelliJ application settings to `migrations` subdirectory
  * if you have the project which you want to migrate open, then close it
  * open the project in IntelliJ Ultimate or Community
  * update the flexmark-java dependency version to 0.40.0 (or later) and make sure the new
    library is downloaded/updated in the project.
  * use menu `Refactor` > `Migrate...`
  * select `migrate flexmark-java 0.35.x to 0.40.0`
  * press `Run`
  * in the refactoring preview tool window that opens hit `Do Refactor`

* :warning: In my projects, the IDE can miss some class migrations, especially on files which
  are not open. Re-running the migration tends to apply the migration. In cases where migration
  is not applied manual editing will be needed. In my projects `Document`, `Node` and
  `Formatter` were the problem classes:

  * `com.vladsch.flexmark.ast.Document` to `com.vladsch.flexmark.util.ast.Document`
  * `com.vladsch.flexmark.ast.Node` to `com.vladsch.flexmark.util.ast.Node`
  * `com.vladsch.flexmark.formatter.internal.Formatter` to
    `com.vladsch.flexmark.formatter.Formatter`

* :warning: `flexmark-docx-converter` and `flexmark-pdf-converter` cannot be used with Java 9+
  modules because the underlying libraries used have package load conflicts that will be
  resolved in a later release.

<!--@formatter:off-->
  * Fix: overlapping packages in different modules, change:
    * `com.vladsch.flexmark.ast.AllNodesVisitor` to `com.vladsch.flexmark.util.ast.AllNodesVisitor`
    * `com.vladsch.flexmark.ast.BlankLine` to `com.vladsch.flexmark.util.ast.BlankLine`
    * `com.vladsch.flexmark.ast.BlankLineContainer` to `com.vladsch.flexmark.util.ast.BlankLineContainer`
    * `com.vladsch.flexmark.ast.Block` to `com.vladsch.flexmark.util.ast.Block`
    * `com.vladsch.flexmark.ast.BlockContent` to `com.vladsch.flexmark.util.ast.BlockContent`
    * `com.vladsch.flexmark.ast.BlockNodeVisitor` to `com.vladsch.flexmark.util.ast.BlockNodeVisitor`
    * `com.vladsch.flexmark.ast.Content` to `com.vladsch.flexmark.util.ast.Content`
    * `com.vladsch.flexmark.ast.ContentNode` to `com.vladsch.flexmark.util.ast.ContentNode`
    * `com.vladsch.flexmark.ast.CustomBlock` to `com.vladsch.flexmark.util.ast.CustomBlock`
    * `com.vladsch.flexmark.ast.CustomNode` to `com.vladsch.flexmark.util.ast.CustomNode`
    * `com.vladsch.flexmark.ast.DescendantNodeIterable` to `com.vladsch.flexmark.util.ast.DescendantNodeIterable`
    * `com.vladsch.flexmark.ast.DescendantNodeIterator` to `com.vladsch.flexmark.util.ast.DescendantNodeIterator`
    * `com.vladsch.flexmark.ast.Node` to `com.vladsch.flexmark.util.ast.Node`
    * `com.vladsch.flexmark.ast.Document` to `com.vladsch.flexmark.util.ast.Document`
    * `com.vladsch.flexmark.ast.DoNotCollectText` to `com.vladsch.flexmark.util.ast.DoNotCollectText`
    * `com.vladsch.flexmark.ast.DoNotDecorate` to `com.vladsch.flexmark.util.ast.DoNotDecorate`
    * `com.vladsch.flexmark.ast.DoNotLinkDecorate` to `com.vladsch.flexmark.util.ast.DoNotLinkDecorate`
    * `com.vladsch.flexmark.ast.KeepTrailingBlankLineContainer` to `com.vladsch.flexmark.util.ast.KeepTrailingBlankLineContainer`
    * `com.vladsch.flexmark.ast.NodeAdaptedVisitor` to `com.vladsch.flexmark.util.ast.NodeAdaptedVisitor`
    * `com.vladsch.flexmark.ast.NodeAdaptingVisitHandler` to `com.vladsch.flexmark.util.ast.NodeAdaptingVisitHandler`
    * `com.vladsch.flexmark.ast.NodeAdaptingVisitor` to `com.vladsch.flexmark.util.ast.NodeAdaptingVisitor`
    * `com.vladsch.flexmark.ast.NodeIterable` to `com.vladsch.flexmark.util.ast.NodeIterable`
    * `com.vladsch.flexmark.ast.NodeIterator` to `com.vladsch.flexmark.util.ast.NodeIterator`
    * `com.vladsch.flexmark.ast.NodeRepository` to `com.vladsch.flexmark.util.ast.NodeRepository`
    * `com.vladsch.flexmark.ast.NodeVisitor` to `com.vladsch.flexmark.util.ast.NodeVisitor`
    * `com.vladsch.flexmark.ast.NodeVisitorBase` to `com.vladsch.flexmark.util.ast.NodeVisitorBase`
    * `com.vladsch.flexmark.ast.NonRenderingInline` to `com.vladsch.flexmark.util.ast.NonRenderingInline`
    * `com.vladsch.flexmark.ast.Package` to `com.vladsch.flexmark.util.ast.Package`
    * `com.vladsch.flexmark.ast.ReferenceNode` to `com.vladsch.flexmark.util.ast.ReferenceNode`
    * `com.vladsch.flexmark.ast.ReferencingNode` to `com.vladsch.flexmark.util.ast.ReferencingNode`
    * `com.vladsch.flexmark.ast.VisitHandler` to `com.vladsch.flexmark.util.ast.VisitHandler`
    * `com.vladsch.flexmark.ast.Visitor` to `com.vladsch.flexmark.util.ast.Visitor`
    * `com.vladsch.flexmark.IParse` to `com.vladsch.flexmark.util.IParse`
    * `com.vladsch.flexmark.IRender` to `com.vladsch.flexmark.util.IRender`

  * Fix: API classes under `internal` package:
    * `flexmark`:
      * `com.vladsch.flexmark.internal.inline.AsteriskDelimiterProcessor` to `com.vladsch.flexmark.parser.core.delimiter.AsteriskDelimiterProcessor`
      * `com.vladsch.flexmark.internal.inline.EmphasisDelimiterProcessor` to `com.vladsch.flexmark.parser.core.delimiter.EmphasisDelimiterProcessor`
      * `com.vladsch.flexmark.internal.Bracket` to `com.vladsch.flexmark.parser.core.delimiter.Bracket`
      * `com.vladsch.flexmark.internal.Delimiter` to `com.vladsch.flexmark.parser.core.delimiter.Delimiter`
      * `com.vladsch.flexmark.internal.ThematicBreakOptions` to `com.vladsch.flexmark.internal.ThematicBreakParser.ThematicBreakOptions`
      * `com.vladsch.flexmark.internal.HeadingOptions` to `com.vladsch.flexmark.internal.HeadingParser.HeadingOptions`
      * `com.vladsch.flexmark.internal.BlockParserTracker` to `com.vladsch.flexmark.parser.block.BlockParserTracker`
      * `com.vladsch.flexmark.internal.BlockQuoteParser` to `com.vladsch.flexmark.parser.core.BlockQuoteParser`
      * `com.vladsch.flexmark.internal.DocumentBlockParser` to `com.vladsch.flexmark.parser.core.DocumentBlockParser`
      * `com.vladsch.flexmark.internal.FencedCodeBlockParser` to `com.vladsch.flexmark.parser.core.FencedCodeBlockParser`
      * `com.vladsch.flexmark.internal.HeadingParser` to `com.vladsch.flexmark.parser.core.HeadingParser`
      * `com.vladsch.flexmark.internal.IndentedCodeBlockParser` to `com.vladsch.flexmark.parser.core.IndentedCodeBlockParser`
      * `com.vladsch.flexmark.internal.ListBlockParser` to `com.vladsch.flexmark.parser.core.ListBlockParser`
      * `com.vladsch.flexmark.internal.ListItemParser` to `com.vladsch.flexmark.parser.core.ListItemParser`
      * `com.vladsch.flexmark.internal.ParagraphParser` to `com.vladsch.flexmark.parser.core.ParagraphParser`
      * `com.vladsch.flexmark.internal.ThematicBreakParser` to `com.vladsch.flexmark.parser.core.ThematicBreakParser`
      * `com.vladsch.flexmark.internal.HtmlBlockParser` to `com.vladsch.flexmark.parser.core.HtmlBlockParser`
      * `com.vladsch.flexmark.internal.ReferencePreProcessorFactory` to `com.vladsch.flexmark.parser.core.ReferencePreProcessorFactory`
      * `com.vladsch.flexmark.internal.InlineParserImpl` to `com.vladsch.flexmark.parser.internal.InlineParserImpl`
      * `com.vladsch.flexmark.internal.BlockStartImpl` to `com.vladsch.flexmark.parser.internal.BlockStartImpl`
      * `com.vladsch.flexmark.internal.BlockContinueImpl` to `com.vladsch.flexmark.parser.internal.BlockContinueImpl`
      * `com.vladsch.flexmark.internal.LinkRefProcessorData` to `com.vladsch.flexmark.parser.internal.LinkRefProcessorData`
      * `com.vladsch.flexmark.internal.CommonmarkInlineParser` to `com.vladsch.flexmark.parser.internal.CommonmarkInlineParser`
      * `com.vladsch.flexmark.internal.MatchedBlockParserImpl` to `com.vladsch.flexmark.parser.internal.MatchedBlockParserImpl`
      * `com.vladsch.flexmark.internal.PostProcessorManager` to `com.vladsch.flexmark.parser.internal.PostProcessorManager`
      * `com.vladsch.flexmark.internal.DocumentParser` to `com.vladsch.flexmark.parser.internal.DocumentParser`
      * `com.vladsch.flexmark.internal.HtmlDeepParser` to `com.vladsch.flexmark.parser.internal.HtmlDeepParser`

      * resource `/com/vladsch/flexmark/internal/util/entities.properties` to `/com/vladsch/flexmark/util/html/entities.properties` in `flexmark-util` module

    * `flexmark-docx-converter`
      * `com.vladsch.flexmark.docx.converter.internal.DocxRenderer` to `com.vladsch.flexmark.docx.converter.DocxRenderer`
      * `com.vladsch.flexmark.docx.converter.internal.DocxRendererPhase` to `com.vladsch.flexmark.docx.converter.DocxRendererPhase`
      * `com.vladsch.flexmark.docx.converter.internal.NodeDocxRendererHandler` to `com.vladsch.flexmark.docx.converter.NodeDocxRendererHandler`
      * `com.vladsch.flexmark.docx.converter.internal.DocxRendererOptions` to `com.vladsch.flexmark.docx.converter.DocxRendererOptions`

    * `flexmark-formatter`
      * `com.vladsch.flexmark.formatter.internal.Formatter` to `com.vladsch.flexmark.formatter.Formatter`
      * `com.vladsch.flexmark.formatter.internal.FormattingPhase` to `com.vladsch.flexmark.formatter.FormattingPhase`
      * `com.vladsch.flexmark.formatter.internal.MarkdownWriter` to `com.vladsch.flexmark.formatter.MarkdownWriter`
      * `com.vladsch.flexmark.formatter.internal.NodeFormatter` to `com.vladsch.flexmark.formatter.NodeFormatter`
      * `com.vladsch.flexmark.formatter.internal.NodeFormatterContext` to `com.vladsch.flexmark.formatter.NodeFormatterContext`
      * `com.vladsch.flexmark.formatter.internal.NodeFormatterFactory` to `com.vladsch.flexmark.formatter.NodeFormatterFactory`
      * `com.vladsch.flexmark.formatter.internal.NodeFormattingHandler` to `com.vladsch.flexmark.formatter.NodeFormattingHandler`
      * `com.vladsch.flexmark.formatter.internal.NodeRepositoryFormatter` to `com.vladsch.flexmark.formatter.NodeRepositoryFormatter`
      * `com.vladsch.flexmark.formatter.internal.PhasedNodeFormatter` to `com.vladsch.flexmark.formatter.PhasedNodeFormatter`
      * `com.vladsch.flexmark.formatter.internal.NodeFormatterSubContext` to `com.vladsch.flexmark.formatter.NodeFormatterSubContext`
<!--@formatter:on-->
0.35.10
-------

* Add: compound enum refs and enum refs in headings without element reference.

0.35.8
------

* Fix: missing test scope on flexmark-test-utils

0.35.6
------

* Fix: `MarkdownTable.fillMissingColumns()` would add empty columns not setting the proper
  source offset, causing later `MarkdownTable.addTrackedOffset(int)` to fail
* Fix: table extension parsing of table cells with inline elements containing table separator
  characters would parse correctly but not convert inlined table separators back to text, so
  these would not render.
* Add: `FlexmarkHtmlParser.SKIP_FENCED_CODE`, default `false`. When enabled will convert fenced
  code to indented code in generated markdown.
* Add: `FlexmarkHtmlParser.SKIP_CHAR_ESCAPE`, default `false`. When enabled will not escape
  special characters.

0.35.4
------

* Add: html/ui helpers flexmark-util for use with Swing UI HTML generation

0.35.2
------

* Fix: base64 encoding function in `ImageUtils` to remove `\n` from encoded string.

* Fix: Aside extension to use new `KeepTrailingBlankLineContainer` marker for blocks which have
  a prefix which allows attribution of blank lines to the block.

* Add: sample for converting inline nodes to text nodes in node post processor. Thanks to
  **[@markkolich](https://github.com/markkolich)**.
  * Merged [#288, PR: Adding TokenReplacingPostProcessorSample and UnderlineExtensionSample]

* Fix: `ReplacedTextMapper` can only handle a single replacement set, while AutoLinkExtension
  was applying a replacement on top of already replaced text.

* :warning: Now any functions which perform replacements using `ReplacedTextMapper` need to
  check if the passed in text mapper `isModified()` and if this is the case invoke
  `startNestedReplacement(BasedSequence)`.

* Add: log4j logging to `DocumentParser`, when debug is enabled parser will log resulting AST
  after parsing, paragraph pre-processing, block pre-processing and inline processing to isolate
  where a problem is introduced by an extension into the AST.

* Breaking: :warning: Potentially breaking change for some code if parsing with
  `Parser.BLANK_LINES_IN_AST` enabled. Last blank line of blocks is now moved out to the parent
  block. Greatest effect is on list items which previously held on to their last blank line,
  except for the last item in the list. Now all trailing blank lines are moved out to the parent
  list. Therefore children of lists can be list items or blank lines, not just list items. This
  makes blank line attribution more consistent.

* Fix: [#287, ''flexmark-html-parser' The module has an mistake]

* Fix: empty table cells now contain a space so that the position of the cell's text in the file
  is not lost.

* Fix: Formatter inline elements leaving embedded EOL sequences when
  `Formatter.KEEP_SOFT_LINE_BREAKS` is false.

* Fix: trailing blank lines in block quotes are now left inside the block quote instead of
  moving them up to the document level if these blank lines are unambiguously attributable to
  the block quote by having a block quote prefix on the line. Unlike other block elements where
  a trailing blank line is not attributable to the block element, block quotes have explicit
  prefix which identifies the blank line as part of the block quote.

* :warning: trailing blank line nodes will be appended to the block quote even if
  `Parser.BLANK_LINES_IN_AST` is `false` to force block quote to span the blank lines as the
  only way to extend the block quote enclosed characters.

0.35.0
------

* Test: navigation
  * Next/Prev Tab
* Add: For next/prev tab table navigation in:
  * separator stop at start and end of cell for editing `:`
  * header/body stop at end of text to allow continued typing
  * caption navigate to end of text like a cell
* Test: `addTrackOffset()` for caption before/inside/after location.
  * Add: `isBeforeCaption()`, `isInsideCaption()`, `isAfterCaption()`
* Fix: change offset tracking to provide nullable `Character` and `isDeleted`. `true` - deleted,
  `false` - inserted/typed. Character null - unknown, else character typed, inserted or deleted.
  Then all internal changes to what is needed will be isolated inside the `MarkdownTable` class.
* Add: table navigation helpers
* Fix: `TextCollectingVisitor` to handle not just inline nodes but paragraphs and optionally
  other blocks. It now adds spaces between non-adjacent text nodes and adds a blank line between
  paragraphs.
* Add: `TableTextCollectingVisitor` which will extract the table text (without separator) and
  add a blank line after the table.
* Add: inline parser will no longer process block nodes marked as `DoNotDecorate` with inline
  parser extensions to prevent undesired inline processing of nodes such as a table separator by
  typographic extension.
* Add: table manipulation functions to `MarkdownTable` to allow easy modification of a markdown
  table which can then be output as formatted markdown text. This class implements table
  formatting for the tables extension.
* Add: to table formatter options:
  * `FORMAT_TABLE_CAPTION_SPACES` , default `DiscretionaryText.AS_IS`, how to handle spaces
    after `[` and before `]` in formatted output |
  * `FORMAT_TABLE_INDENT_PREFIX` , default `""`, adds arbitrary prefix to tables in formatted
    output |
  * `FORMAT_TABLE_MANIPULATOR` , default `TableManipulator.NULL`, interface invoked before table
    is appended to formatted output. Allows table manipulation |
  * `FORMAT_CHAR_WIDTH_PROVIDER` , default `CharWidthProvider.NULL`, interface used to provide
    actual characters widths so table formatting can take these into account |
* Break: potentially breaking change, now FencedCode block with empty content returns
  `SubSequence.NULL` for content with no lines, previously returned empty string with start==end
  representing the position in the document where the content should have been. Affects only
  `FencedCodeBlock` and `AsideBlock`. Can use`FencedCodeBlock.getOpeningMarker().getEndOffset()
  \+ 1` if you need the position where the content would have been. Similarly for `AsideBlock`
* Fix: `SegmentedSequence` start and end offsets to always be correct when contained segments
  are all non-based characters and computed on creation.
* Break: some breaking changes to `FormattingAppendableImpl` derived constructors. Now most
  arguments must be passed, no defaults. It was getting too messy to trace which constructors
  were used and what were the resulting options for the appendable.
* Fix: with `HtmlRenderer.FORMAT_FLAGS` set to `HtmlRenderer.PASS_THROUGH` pre-formatted would
  incorrectly add indent spaces.
* Fix: optimized HTML rendering with `HtmlRenderer.FORMAT_FLAGS` set to
  `HtmlRenderer.PASS_THROUGH`, no fancy output formatting other than line breaks but 7x faster
  than using standard fancy formatting output.
* Fix: Emoji shortcut did not render emoji with UNICODE_ONLY image type option
* Add: `Formatter.KEEP_HARD_LINE_BREAKS`, default true and `Formatter.KEEP_SOFT_LINE_BREAKS`
  default true. Setting to `false` will not output corresponding line break.
* Add: `Formatter.APPEND_TRANSFERRED_REFERENCES`, default false. Setting to `true` will append
  all transferred references to the bottom of the formatted document.
* Add: `NodeRepository` API to return referenced references from a given node.
* Fix: Jira and YouTrack converters ignored `HtmlRenderer.RECHECK_UNDEFINED_REFERENCES`
* Add: Base64 conversion to image utils

0.34.58
-------

* Fix: possible index out of bounds on segmented sequences
* Add: HTML parser option to modify id attribute before output. Used to strip prefix
  `user-content-` from GitHub rendered docs. See
  [`OUTPUT_ID_ATTRIBUTE_REGEX`](../../wiki/Extensions#html-to-markdown)
* Fix: HTML parser to transfer last `<a>` with `id`/`name` attribute to parent.
* Fix: HTML parser to strip out `<a>` tags with `id` attribute and no text when direct children
  of `<body>`. A kludge to strip out duplicate ids in GitHub rendered docs.
* Fix: HTML parser to respect `<ol>` `start` attribute for first list item.
* Fix: HTML parser to add `*`, `~` and `^` to escaped text characters so they don't generate
  erroneous markup.
* Add: to TOC extension `CASE_SENSITIVE_TOC_TAG` option, default `true`, setting it to false
  allows `[TOC]` and `[TOC]: #` to be matched without case sensitivity. The syntax for toc
  options is still case sensitive.

0.34.56
-------

* Fix: [#274, FlexmarkHtmlParser can not handle escaped tags correctly], now `<`, `>` and `&`
  are also escaped.

0.34.53
-------

* Change: revert docx4j to 3.3.4 in docx4j-3 branch

0.34.52
-------

* Fix: intermittent NPE when including macro with table in table

0.34.51
-------

* Fix: intermittent NPE when including macro with table in table in docx4j-3 branch

0.34.50
-------

* Add: `HtmlRenderer.NO_P_TAGS_USE_BR`, default `false`. When enabled instead of wrapping loose
  paragraphs in `<p>` tag adds 2 x `<br />` after paragraph.
* Fix: extension auto links would not be processed if paragraph had inline parser processed auto
  links before.

0.34.49
-------

* Change: revert docx4j to 3.3.6 in docx4j-3 branch

0.34.48
-------

* Add: `Macros` extension handling to docx converter. Macros allow nesting of block elements
  inside tables.

0.34.46
-------

* Add: `flexmark-ext-macros` extension to allow macro definition and inclusion in inline
  contexts. [Extensions: Macros](../../wiki/Extensions#macros)
* Fix: `Link.setTextChars(BasedSequence)` was trimming the string before setting `Link.text`.
  Now the full untrimmed text is used.

0.34.44
-------

* Fix: [#271, Regression? Comments are preserved in 0.26.4 but removed in 0.34.40]
  * Fix: formatter HTML comments in non-translating mode did not output comment markers
* Fix: treat protocol prefix at the end of line as a valid link.

0.34.42
-------

* Add: url parsing to `AutoLink` to set other link parts of the node like: pageref, anchor
  marker and anchor

0.34.40
-------

* Fix: add `HtmlRenderer.HEADER_ID_GENERATOR_NON_ASCII_TO_LOWERCASE`, default `true`. When set
  to `false` changes the default header id generator to not convert non-ascii alphabetic
  characters to lowercase. Needed for `GitHub` id compatibility.
* Fix: add `HtmlRenderer.HEADER_ID_GENERATOR_NON_ASCII_TO_LOWERCASE` set to `false` to `GitHub`
  and `GitHubDoc` parser profiles.

0.34.38
-------

* Fix: docx conversion of hyperlinks did not take relationship part into account when optimizing
  rel references causing invalid rel ids to be used if the same link was included in multiple
  contexts.

0.34.36
-------

* Fix: docx conversion of footnote refs in tables to footnotes which contained links generated
  invalid docx.

0.34.34
-------

* Change: docx4j library version to 6.0.1

0.34.32
-------

* Fix: change anonymous classes to static classes in `CoreNodeDocxRenderer`
* Add: test case for `Parser.SPACE_IN_LINK_URLS` to validate allowing spaces in URLs
* Fix: Docx Converter to use style names instead of style ids because Word localizes ids and
  leaves the names common to all locales. Completely opposite to intuition but that is Word.
* Fix: Docx Converter remove hardcoded "CENTER" alignment for table headings overriding the
  `Table Heading` style provided alignment.

0.34.30
-------

* Fix: [#268, Pipe characters are not escaped in Table (FlexmarkHtmlParser)]
  * Fix: escape pipe characters in text (to avoid accidental use as table or other markup) when
    not inline code nor fenced code
  * Fix: escape back ticks when inside code
  * Fix: disable escaping of `[]` when inside code
  * Fix: disable escaping of `\` when inside code
  * Fix: replace non-break space with space when inside code
* Fix: `FlexmarkHtmlParser.BR_AS_EXTRA_BLANK_LINES` now adds `<br />` followed by blank line
* Add: `GitLab` extension handling to docx converter. For now `math` and `mermaid` blocks are
  not converted.
* Add: `GitLab` extension formatter to properly handle block quote formatting
* Add: `ParagraphContainer` interface to allow container nodes to control how their paragraphs
  are output for formatting/rendering with respect to blank line before/after control.
* Fix: `GitLab` block quote parser bug for terminating sequence.

0.34.28
-------

* Add: html parser options to specify how to generate some markdown formatting elements, default
  for all is `ExtensionConversion.MARKDOWN`:
  * `EXT_INLINE_STRONG` - strong
  * `EXT_INLINE_EMPHASIS` - emphasis
  * `EXT_INLINE_CODE` - code
  * `EXT_INLINE_DEL` - del
  * `EXT_INLINE_INS` - ins
  * `EXT_INLINE_SUB` - sub
  * `EXT_INLINE_SUP` - sup

* Available settings:

  * `ExtensionConversion.MARKDOWN` - convert to markdown
  * `ExtensionConversion.TEXT` - convert to inner text
  * `ExtensionConversion.HTML` - leave HTML as is

* Corresponding `SKIP_` options have been deprecated since their function is duplicated by new
  options.

0.34.26
-------

* Fix: Add `DoNotLinkDecorate` to `Code` for preventing AutoLink extension from decorating
  inline code with autolinks.
* Fix: `BasedSequenceImpl.lastIndexOfAny(CharSequence)` and
  `BasedSequenceImpl.lastIndexOfAnyNot(CharSequence)` were using `indexOf...` instead of
  `lastIndexOf...`

0.34.24
-------

* Fix: `BasedSequenceImpl.countCharsReversed(char, int, int)` and
  `BasedSequenceImpl.countNotCharsReversed(char, int, int)` to not return -1, should return 0
* Fix: `BasedSequenceImpl.spliceAtEnd()` not to fail if this or other is empty
* Add: `GitLabExtension`, Documented in
  [Extensions: Gitlab Flavoured Markdown](../../wiki/Extensions#gitlab-flavoured-markdown)

0.34.22
-------

* Add: OSGi module support thanks to [@klcodanr](https://github.com/klcodanr)

0.34.20
-------

* Add: renderer type equivalences for allowing new renderer types which override existing ones.
  Now "YOUTRACK" is defined as an override of "JIRA" by `YouTrackConverterExtension`
  * use `HtmlRenderer.Builder.isRendererType(String)` to test for recognizing renderer type
    instead of `rendererType.equals("...")` make sure renderer type checking is done with most
    specific one, otherwise the wrong match will occur. For example, test for "YOUTRACK" before
    "JIRA" because "JIRA" matches "YOUTRACK".
  * `HtmlRenderer.isCompatibleRendererType(MutableDataHolder, String)`
  * `HtmlRenderer.isCompatibleRendererType(MutableDataHolder, String, String)`
  * `HtmlRenderer.addRenderTypeEquivalence(MutableDataHolder, String, String)` add new to old
    renderer type mapping.

0.34.18
-------

* Add: html parser options to suppress generating some markdown formatting elements, default for
  all is false:
  * `SKIP_INLINE_STRONG` - strong
  * `SKIP_INLINE_EMPHASIS` - emphasis
  * `SKIP_INLINE_CODE` - code
  * `SKIP_INLINE_DEL` - del
  * `SKIP_INLINE_INS` - ins
  * `SKIP_INLINE_SUB` - sub
  * `SKIP_INLINE_SUP` - sup
  * `SKIP_HEADING_1` - heading 1
  * `SKIP_HEADING_2` - heading 2
  * `SKIP_HEADING_3` - heading 3
  * `SKIP_HEADING_4` - heading 4
  * `SKIP_HEADING_5` - heading 5
  * `SKIP_HEADING_6` - heading 6
  * `SKIP_ATTRIBUTES` - attribute extension formatting
* Add: html parser option `ADD_TRAILING_EOL`, default `false`. Will add trailing EOL to
  generated markdown text.
* Fix: html parser did not add a blank line before first list item of the first list

0.34.16
-------

* Fix: [#254, customized HTML\_BLOCK\_TAGS Parser option seems not taken into account]

0.34.14
-------

* Fix:
  [#252, GfmUser and GfmIssue are not recognized if immediately followed by non-space character]
  character, change regex to allow word break after user and issue

0.34.12
-------

* Fix: merge [#249, support Jira links titles] thanks to @qwazer

0.34.10
-------

* Fix: [#247, Admonition Expression may lack a part of the text.]
* Add: `TocExtension.TOC_CONTENT`, `TocExtension.TOC_LIST`, `SimTocExtension.TOC_CONTENT`,
  `SimTocExtension.TOC_LIST` attributable parts for `div` and `ul`/`ol` HTML tags. Documented in
  [Table-of-Contents-Extension](../../wiki/Table-of-Contents-Extension)
  * Fix: `TOC` element to use `TITLE` option if one is defined. Default for `TOC` is `""`, for
    sim `TOC` it is `"Table of Contents"`
* Fix: [#248, Request to add CSS class of TOC]
  * Add: `TocExtension.DIV_CLASS` default `""`, class attribute to use on table of content `div`
    wrapper, duplicated as `SimTocExtension.DIV_CLASS`
  * Add: `TocExtension.LIST_CLASS` default `""`, class attribute to use on table of content `ul`
    or `ol` element, duplicated as `SimTocExtension.LIST_CLASS`

0.34.8
------

* Fix: [#220, Jekyll Tag "include"]
* Fix: [#245, Markdown output of multiple Definition Lists has a problem that list items merge.]
  Blank lines were removed if `Parser.BLANK_LINES_IN_AST` is `false`

0.34.6
------

* Fix: [#243, Markdown output of ImageRef syntax referencing ID is incorrect]
* Fix: [#244, Duplicate footnotes have the problem of HTML ID collision conflict.] Now each
  footnote reference adds a back references and the id of the back reference has `-#` suffix
  added except for the first back-reference, where `#` is an integer from 1..N

0.34.4
------

* Add: media tags extension thanks to Cornelia Schultz (GitHub @CorneliaXaos)
* Change: convert class methods to static when possible
* Fix: [#239, flexmark-ext-youtube-embedded missing test case for '@' usage.]
* Fix: add ability to escape `@` for youtube links.

0.34.2
------

* Fix: update nexus staging plugin to 1.6.7
* Fix: move nexus-staging plugin to deploy profile so it is not needed for ci test build
* Fix: gfm tables extension to only require a single separator dash
* Fix: for matched parenthesis destination link parsing, only treat backslash as escape if
  followed by characters which can be escaped.

0.34.0
------

* Fix: table formatter for translations to insert a space if the translated text is empty.
* Add: changed regex for parsing unquoted HTML attribute value to exclude `{}`.
* Add: translation formatting awareness to: abbreviation, admonition, attributes, emoji,
  enumerated reference footnotes and tables extensions
* Add: translation API sample:
  [TranslationSample.java](https://github.com/vsch/flexmark-java/blob/master/flexmark-java-samples/src/com/vladsch/flexmark/samples/TranslationSample.java)
* Fix: `AttributesExtension` incorrectly parsed consecutive attributes as a single element
* Add: `TranslationHandlerFactory` to allow format options to be passed for custom handler
  creation.
* Add: `RenderPurpose` arg to `Formatter.translationRender` eliminating the need for separate
  render purpose to be set in translation handler instance.
* Add: Translation helper API to Formatter module. A bit of a kludge but has the potential to
  work. Preserves non-translatable entities, uses a single translation for all occurrences of
  text snippets such as ref text. Leaves inline emphasis in, Yandex has no issues with it.
  Replaces all element text which needs to be preserved with _#_ where is unique integer. Will
  replace anchor refs to headings in file if the heading text is translated.
* Add: to html parser recognition that `<img alt="emoji category:shortcut" src="">` is an emoji
  shortcut generated by flexmark-java emoji extension and convert it to such.

0.32.24
-------

* Fix: [#232, List continuation], add `Formatter.LIST_REMOVE_EMPTY_ITEMS`, default `false`. If
  `true` then empty list items or list items which only contain a `BlankLine` node are removed
  from the output.
* Fix: formatter output of empty list items
* Fix: formatter output of empty loose task list items

0.32.22
-------

* Fix: [#229, gfm-tasklist extension - add state class to list item]
  * Add: `TaskListExtension.ITEM_DONE_CLASS` and `TaskListExtension.ITEM_NOT_DONE_CLASS` `li`
    tag classes to use for done and not done items.
  * Add: `TaskListExtension.TIGHT_ITEM_CLASS` to complement the
    `TaskListExtension.LOOSE_ITEM_CLASS` to replace generically named
    `TaskListExtension.ITEM_CLASS`
  * Add: depreciation of `TaskListExtension.ITEM_CLASS` in favour of
    `TaskListExtension.TIGHT_ITEM_CLASS`

* Fix: merge [#231, PR: Fix two small bugs in ext-toc]
  [@BlueBoxWare](https://github.com/BlueBoxWare)

* Add: `TaskListAttributeProviderSample` to `flexmark-java-samples` module.

* Add: `GitHubParsingSample` to `flexmark-java-samples` module to show GitHub compatible parser
  setup.

* Add: `ParserEmulationProfile.GITHUB` to reflect current GitHub profile, effectively it is
  `ParserEmulationProfile.COMMONMARK_0_28` with GitHub compatible id generator settings.

* Fix: [#221, XSS: Javascript execution through links], add `HtmlRenderer.SUPPRESSED_LINKS`
  default `"javascript:.*"`, a regular expression to suppress any links that match. The test
  occurs before the link is resolved using a link resolver. Therefore any link matching will be
  suppressed before it is resolved. Likewise, a link resolver returning a suppressed link will
  not be suppressed since this is up to the custom link resolver to handle.

* Suppressed links will render only the child nodes, effectively `[Something
  New](javascript:alert(1))` will render as if it was `Something New`.

* Link suppression based on URL prefixes does not apply to HTML inline or block elements. Use
  HTML suppression options for this.

* Add: `AutolinkExtension.IGNORE_LINKS` default `""`, a regex expression to match link text
  which should not be auto-linked. This can include full urls like `www.google.com` or parts by
  including wildcard match patterns. Any recognized auto-link which matches the expression will
  be rendered as text.

0.32.20
-------

* Fix: [#225, BlankLine nodes within IndentedCodeBlock]

0.32.18
-------

* Fix: [#216, Wrong source positions for Tables with empty TableHead/TableBody]

0.32.16
-------

* Fix: change all javadoc/overview.html to be generated from javadoc/overview.md files.
* Fix: admonition.css remove non-existent `horiz-align` property.
* Add:
  [HtmlToMarkdownSample.java](https://github.com/vsch/flexmark-java/blob/master/flexmark-java-samples/src/com/vladsch/flexmark/samples/HtmlToMarkdownSample.java)
* Add: `HtmlRenderer.HEADER_ID_GENERATOR_NON_DASH_CHARS`, default `""`. Any characters in the
  string will be preserved and passed through to the heading ID.
* Fix: `ParserEmulationProfile.GITHUB_DOC` now sets
  `HtmlRenderer.HEADER_ID_GENERATOR_NO_DUPED_DASHES` to `" -"` and
  `HtmlRenderer.HEADER_ID_GENERATOR_NON_DASH_CHARS` to `"_"` to match current GitHub heading id
  generation rules.

0.32.14
-------

* Change: `AdmonitionExtension.WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH` to
  `AdmonitionExtension.WITH_SPACES_INTERRUPTS_ITEM_PARAGRAPH`
* Fix: Formatter for Tables now treats header column alignment as centered if no alignment
  marker is given for the column, body cells remain left aligned. This is default browser
  rendered table appearance.
* Fix: [#213, Attributes element does not allow spaces before closing }]
* Fix: update to open html to pdf 0.0.1-RC12

0.32.12
-------

* Add: `DocxRenderer.TABLE_PREFERRED_WIDTH_PCT` default `0`, if `0` then table width is `auto`,
  otherwise specifies percent of page width.
* Add: `DocxRenderer.TABLE_LEFT_INDENT` default `120`, in twips
* Add: `DocxRenderer.TABLE_STYLE` default `""`, if not empty then table then table style will be
  set to this value and default cell borders will not be generated.

0.32.10
-------

* Add: `AdmonitionBlock` getters/setters for `titleOpeningMarker`, `title`, `titleClosingMarker`
* Fix: `AdmonitionNodeRenderer` remove erroneous, extra `</div>`
* Add: `AdmonitionNodeRenderer` attributable parts:
  * ADMONITION_SVG_OBJECT_PART - svg object at top of body, part is on `Document` node
  * ADMONITION_HEADING_PART - heading div
  * ADMONITION_ICON_PART - heading icon svg
  * ADMONITION_TITLE_PART - heading title span
  * ADMONITION_BODY_PART - admonition body div

0.32.8
------

* Add: [Admonition Extension](https://github.com/vsch/flexmark-java/wiki/Extensions#admonition)
  To create block-styled side content. For complete documentation please see the
  [Admonition Extension, Material for MkDocs] documentation.

0.32.6
------

* Add: `DocxRenderer.PREFIX_WWW_LINKS`, default `true`, when true adds `http://` to link
  addresses which start with `www.`

* Fix: `DocxRenderer` some images would not be embedded and generate a CRC Error when loaded via
  URL vs file.

0.32.4
------

* Fix: [#207, Issue extension doesn't support windows new lines], same for Gfm User extension.

0.32.2
------

* Add: `EmojiExtension.ATTR_IMAGE_CLASS`, default `""`, if not empty will set the image tag
  class attribute to this value.

* Fix: when image type was set to unicode then unicode char would be used for a shortcut even
  when that shortcut was not defined for requested shortcut type: github or ecs.

* Fix: update to docx4j version 3.3.6

0.32.0
------

* **API Change**: removed `EmojiCheatSheet` class to replace with `EmojiShortcuts` which has
  better referencing for GitHub shortcuts and unicode chars for all emojis from
  [emoji-cross-reference](https://github.com/vsch/emoji-cross-reference)

  * Removed: `EmojiExtension.USE_IMAGE_URL`
  * Add: `EmojiExtension.USE_SHORTCUT_TYPE`, default `EmojiShortcutType.EMOJI_CHEAT_SHEET`,
    select type of shortcuts:
    * `EmojiShortcutType.EMOJI_CHEAT_SHEET`
    * `EmojiShortcutType.GITHUB`
    * `EmojiShortcutType.ANY_EMOJI_CHEAT_SHEET_PREFERRED` use any shortcut from any source. If
      image type options is not `UNICODE_ONLY`, will generate links to Emoji Cheat Sheet files
      or GitHub URL, with preference given to Emoji Cheat Sheet files.
    * `EmojiShortcutType.ANY_GITHUB_PREFERRED` - use any shortcut from any source. If image type
      options is not `UNICODE_ONLY`, will generate links to Emoji Cheat Sheet files or GitHub
      URL, with preference given to GitHub URL.
  * Add: `EmojiExtension.USE_IMAGE_TYPE`, default `EmojiImageType.IMAGE_ONLY`, to select what
    type of images are allowed.
    * `EmojiImageType.IMAGE_ONLY`, only use image link
    * `EmojiImageType.UNICODE_ONLY` convert to unicode and if there is no unicode treat as
      invalid emoji shortcut
    * `EmojiImageType.UNICODE_FALLBACK_TO_IMAGE` convert to unicode and if no unicode use image.

* **API Change**: removed `HtmlRenderer.WRAP_TIGHT_ITEM_PARAGRAPH_IN_SPAN`, it was only needed
  to have somewhere to set attributes for tight item text for `Attributes` which now implement
  intelligent span wrapping of text.

* **Change**: `AttributesExtension.ASSIGN_TEXT_ATTRIBUTES`, default `true`. Set to `false` for
  backward compatibility.

* When `true` attribute assignment rules to nodes are changed to allow text elements to get
  attributes. If an attribute element is spaced from the previous plain text element `some text
  {attributes}` then attributes are for the parent of the text. If they are attached to the text
  element `some text{attributes}` then they are assigned to the immediately preceding plain text
  span. Within an inline element same rules apply: `**bold text {attr}**` are for the strong
  emphasis span, while `**bold text{attr}**` will wrap the text between the strong emphasis
  tags, in a span with given attributes.

* If a plain text span is interrupted by an HTML comment then it is considered as two separate
  plain text spans. ie. `some <!---->text{attr}` will result in `some <!----><span
  attr>text</span>` rendering.

* Full spec:
  [ext_attributes_ast_spec](flexmark-ext-attributes/src/test/resources/ext_attributes_ast_spec.md)

* Fix: Attributes, when they are first child then delete leading spaces of following node first
  rendered child will have leading spaces.

* Add: Attribute syntax option allows `Attributes` on reference definition so that all elements
  referencing it, inherit the attributes.

* To assign attributes to a reference definition you need to leave a blank line between the
  reference definition and the attributes element, placed by itself and followed by a blank
  line.

      ![reference 1][ref]

      ![reference 2][ref]

      [ref]: http://example.com/image.png

      {width=20 height=20}

* Fix: HTML Parser:
  * image alt text should escape markdown special characters which can affect parsing of image
    link text like: `[]`
  * convert `<pre><tt>` to indented code
  * add attribute extraction
    * id : if `FlexmarkHtmlParser.OUTPUT_ATTRIBUTES_ID` is `true`, default `true`
    * `FlexmarkHtmlParser.OUTPUT_ATTRIBUTES_NAMES_REGEX` default `""`, if not empty then will
      output attributes extension syntax for attribute names that are matched by the regex.
  * did not output a line end at start of `<div>`, this tended to splice text in div to previous
    element.

* Add: Emoji Extension support to DocxRenderer.
  * Add: Emoji Cheat Sheet images to resources in DocxRender jar, default configuration will
    resolve emoji image files to the files in the jar.
  * Add: `DocxRenderer.DOC_EMOJI_IMAGE_VERT_OFFSET`, default `-0.1`, vertical offset of emoji
    image as a factor of line height at point of insertion. The final value is rounded to
    nearest pt so jumps of 1 pt for small changes of this value can occur.
  * Add: `DocxRenderer.DOC_EMOJI_IMAGE_VERT_SIZE`, default `1.05`, size of emoji image as a
    factor of line height at point of insertion.

0.30.0
------

* Fix: [#198, StringIndexOutOfBoundsException] in `AbbreviationExtension` if abbreviation
  definition had an empty abbreviation.

* API Change: Refactoring of Interfaces to allow extensions only providing link resolver,
  attribute provider and html id generator to be re-used by the `DocxRenderer` and
  `HtmlRenderer` without modifications other than changing the `implemented` extension from
  `HtmlRenderer.HtmlRendererExtension` to `RendererExtension`

  * `AttributesProviderFactory` pass only `LinkResolverContext` instead of
    `NodeRenderingContext` to allow for attribute provider extensions to be re-used with
    `DocxRender`

  * `HeadIdGenerator` pass only `LinkResolverContext` instead of `NodeRenderingContext` to allow
    for header id generator provider extensions to be re-used with `DocxRender`

  * new `RendererExtension` with only ability to register html id generator, link resolver and
    attribute provider. Such an extension can be used as is with `HtmlRenderer` and
    `DocxRenderer`

  * `RendererExtension.extend(RendererBuilder, String)` method of these gets passed
    `RendererBuilder` instead of `HtmlRenderer.Builder`

  * extensions that implement both `RendererExtension` and `HtmlRendererExtension` will have
    only have the html renderer extension `extend` method called.

* Add: `flexmark-ext-aside` handling to DocxConverter

* Fix: DocxConverter formatting around footnote reference would be applied to footnote text

* Add: `DocxRenderer.LOCAL_HYPERLINK_MISSING_HIGHLIGHT`, default `"red"` to highlight in
  document hyperlinks with missing targets. Set to `""` to disable this. NOTE: must be one of
  the 8 named colors used by Word or it might complain.

* Add: `DocxRenderer.LOCAL_HYPERLINK_MISSING_FORMAT`, default `"Missing target id: #%s"` to
  change the tooltip text of the missing hyperlink. `%s` will be replaced with the reference id
  of the link.

* Fix: DocxConverter self referencing ref anchors should be converted to bookmark references in
  the docx

* Add: DocxConverter now supports `AttributesExtension` and `EnumeratedReferenceExtension` by
  converting id attributes to bookmarks and enumerated reference links to hyperlinks to
  bookmarks.

* Change: Enumerated references which are missing the definition for their type now default to
  `type #` where # is the reference ordinal instead of just `#`.

* Add: Enumerated links now have title set to the text value of the reference.

* Add: DocxConverter options to control heading id generation to resolve anchor refs to document
  anchors
  * `DocxRenderer.HTML_ID_GENERATOR`, default `HtmlIdGenerator instance`, the id generator to
    use for generating heading ids.

  * `DocxRenderer.LOCAL_HYPERLINK_SUFFIX`, default `""`, used to add a suffix to hyperlink
    bookmark names, except ones linking to first heading in a document. Only needed if you have
    some post processor on the docx adding suffixes to bookmark names.

<!--
  * `DocxRenderer.FIRST_HEADING_ID_SUFFIX`, default `""`, used to add a suffix to the id of the
    first heading of the document and any hyperlinks to it.
 -->
For convenience these `HtmlRenderer` keys are aliased through `DocxRenderer`, keep in mind that
setting either will affect both keys. For information on these keys see
[`HtmlRenderer` options](https://github.com/vsch/flexmark-java/wiki/Extensions#renderer)

* `DocxRenderer.HEADER_ID_GENERATOR_RESOLVE_DUPES`, default `true`,

* `DocxRenderer.HEADER_ID_GENERATOR_TO_DASH_CHARS`, default `" -_"`

* `DocxRenderer.HEADER_ID_GENERATOR_NO_DUPED_DASHES`, default `false`

* Add: DocxConverter options to re-map standard style names to user defined ones.
  * `DocxRenderer.DEFAULT_STYLE`, default "Normal"
  * `DocxRenderer.LOOSE_PARAGRAPH_STYLE`, default "ParagraphTextBody"
  * `DocxRenderer.TIGHT_PARAGRAPH_STYLE`, default "BodyText"
  * `DocxRenderer.PREFORMATTED_TEXT_STYLE`, default "PreformattedText"
  * `DocxRenderer.BLOCK_QUOTE_STYLE`, default "Quotations"
  * `DocxRenderer.ASIDE_BLOCK_STYLE`, default "AsideBlock"
  * `DocxRenderer.HORIZONTAL_LINE_STYLE`, default "HorizontalLine"
  * `DocxRenderer.TABLE_CAPTION`, default "TableCaption"
  * `DocxRenderer.TABLE_CONTENTS`, default "TableContents"
  * `DocxRenderer.TABLE_HEADING`, default "TableHeading"
  * `DocxRenderer.FOOTNOTE_STYLE`, default "Footnote"

<!--
  * `DocxRenderer.BULLET_LIST_STYLE`, default "BulletList"
  * `DocxRenderer.BLOCK_QUOTE_BULLET_LIST_STYLE`, default "QuotationsBulletList"
  * `DocxRenderer.NUMBERED_LIST_STYLE`, default "NumberedList"
  * `DocxRenderer.BLOCK_QUOTE_NUMBERED_LIST_STYLE`, default "QuotationsNumberedList"
 -->
* `DocxRenderer.BOLD_STYLE`, default "StrongEmphasis"
* `DocxRenderer.ITALIC_STYLE`, default "Emphasis"
* `DocxRenderer.STRIKE_THROUGH_STYLE`, default "Strikethrough"
* `DocxRenderer.SUBSCRIPT_STYLE`, default "Subscript"
* `DocxRenderer.SUPERSCRIPT_STYLE`, default "Superscript"
* `DocxRenderer.INS_STYLE`, default "Underlined"
* `DocxRenderer.INLINE_CODE_STYLE`, default "SourceText"
* `DocxRenderer.HYPERLINK_STYLE`, default "Hyperlink"

[#198, StringIndexOutOfBoundsException]: https://github.com/vsch/flexmark-java/issues/198
[#207, Issue extension doesn't support windows new lines]: https://github.com/vsch/flexmark-java/issues/207
[#213, Attributes element does not allow spaces before closing }]: https://github.com/vsch/flexmark-java/issues/213
[#216, Wrong source positions for Tables with empty TableHead/TableBody]: https://github.com/vsch/flexmark-java/issues/216
[#220, Jekyll Tag "include"]: https://github.com/vsch/flexmark-java/issues/220
[#221, XSS: Javascript execution through links]: https://github.com/vsch/flexmark-java/issues/221
[#225, BlankLine nodes within IndentedCodeBlock]: https://github.com/vsch/flexmark-java/issues/225
[#229, gfm-tasklist extension - add state class to list item]: https://github.com/vsch/flexmark-java/issues/229
[#231, PR: Fix two small bugs in ext-toc]: https://github.com/vsch/flexmark-java/pull/231
[#232, List continuation]: https://github.com/vsch/flexmark-java/issues/232
[#239, flexmark-ext-youtube-embedded missing test case for '@' usage.]: https://github.com/vsch/flexmark-java/issues/239
[#243, Markdown output of ImageRef syntax referencing ID is incorrect]: https://github.com/vsch/flexmark-java/issues/243
[#244, Duplicate footnotes have the problem of HTML ID collision conflict.]: https://github.com/vsch/flexmark-java/issues/244
[#245, Markdown output of multiple Definition Lists has a problem that list items merge.]: https://github.com/vsch/flexmark-java/issues/245
[#247, Admonition Expression may lack a part of the text.]: https://github.com/vsch/flexmark-java/issues/247
[#248, Request to add CSS class of TOC]: https://github.com/vsch/flexmark-java/issues/248
[#249, support Jira links titles]: https://github.com/vsch/flexmark-java/pull/249
[#252, GfmUser and GfmIssue are not recognized if immediately followed by non-space character]: https://github.com/vsch/flexmark-java/issues/252
[#254, customized HTML\_BLOCK\_TAGS Parser option seems not taken into account]: https://github.com/vsch/flexmark-java/issues/254
[#268, Pipe characters are not escaped in Table (FlexmarkHtmlParser)]: https://github.com/vsch/flexmark-java/issues/268
[#271, Regression? Comments are preserved in 0.26.4 but removed in 0.34.40]: https://github.com/vsch/flexmark-java/issues/271
[#274, FlexmarkHtmlParser can not handle escaped tags correctly]: https://github.com/vsch/flexmark-java/issues/274
[#287, ''flexmark-html-parser' The module has an mistake]: https://github.com/vsch/flexmark-java/issues/287
[#288, PR: Adding TokenReplacingPostProcessorSample and UnderlineExtensionSample]: https://github.com/vsch/flexmark-java/pull/288
[#292, 0.40.0: JUnit is in the compile scope]: https://github.com/vsch/flexmark-java/issues/292
[#293, YamlFrontMatterBlock rendered as markdown does not preserve nested lists]: https://github.com/vsch/flexmark-java/issues/293
[#294, BlankLine nodes within BlockQuote change in behaviour]: https://github.com/vsch/flexmark-java/issues/294
[#295, CoreNodeFormatter does not descend into children on Link nodes]: https://github.com/vsch/flexmark-java/issues/295
[#299, FlexmarkHtmlParser produces extra empty list item for eclosing \</p\> element]: https://github.com/vsch/flexmark-java/issues/299
[#300, Typography extension breaks some auto links]: https://github.com/vsch/flexmark-java/issues/300
[#305, PR: add new youtube link style support to flexmark-ext-youtube-embedded for youtu.be/xyz(?t=123)]: https://github.com/vsch/flexmark-java/pull/305
[#306, PR: Add password protection support]: https://github.com/vsch/flexmark-java/pull/306
[#310, PR: Change URL of GitHub CDN]: https://github.com/vsch/flexmark-java/pull/310
[#313, Ability to override tags processing in FlexmarkHtmlParser]: https://github.com/vsch/flexmark-java/issues/313
[#314, Ability to override character replacements map in FlexmarkHtmlParser]: https://github.com/vsch/flexmark-java/issues/314
[#316, Github user extension incorrectly formats some text]: https://github.com/vsch/flexmark-java/issues/316
[#317, FlexmarkHtmlParser outputs extra newline when converting nested \<ol\>, \<ul\> lists]: https://github.com/vsch/flexmark-java/issues/317
[#318, Ability to disable table caption in FlexmarkHtmlParser]: https://github.com/vsch/flexmark-java/issues/318
[#323, TOC generation improvement]: https://github.com/vsch/flexmark-java/issues/323
[#326, flexmark-html-parser - multiple \<code\> inside \<pre\> bug]: https://github.com/vsch/flexmark-java/issues/326
[#328, Html2mark - missing newline when paragraph followed by div]: https://github.com/vsch/flexmark-java/issues/328
[#331, Ability to replace empty \<p\> with \<br\> during html2mark conversion]: https://github.com/vsch/flexmark-java/issues/331
[#332, withOptions forgets about old link resolvers]: https://github.com/vsch/flexmark-java/issues/332
[#334, CR line separators don't produce line break nodes]: https://github.com/vsch/flexmark-java/issues/334
[#335, PR: Fix CR-only line separator handling]: https://github.com/vsch/flexmark-java/pull/335
[#338, getLineNumber incorrect with Windows end of line separators]: https://github.com/vsch/flexmark-java/issues/338
[#339, PR: Fix to line number when using Windows EOL characters.]: https://github.com/vsch/flexmark-java/pull/339
[#348, WRAP\_AUTO\_LINKS defaults to false, Markdown loses a potential useful link]: https://github.com/vsch/flexmark-java/issues/348
[#349, Translation Helper bugs]: https://github.com/vsch/flexmark-java/issues/349
[#351, Is there any special format requirement for processing html data to markdown]: https://github.com/vsch/flexmark-java/issues/351
[#357, HTML to markdown and removed nested list]: https://github.com/vsch/flexmark-java/issues/357
[Admonition Extension, Material for MkDocs]: https://squidfunk.github.io/mkdocs-material/extensions/admonition/
[Awesome Console]: https://plugins.jetbrains.com/plugin/7677-awesome-console "Awesome Console"
[HtmlToMarkdownCustomizedSample.java]: https://github.com/vsch/flexmark-java/blob/master/flexmark-java-samples/src/com/vladsch/flexmark/samples/HtmlToMarkdownCustomizedSample.java
[Kijimuna]: https://github.com/Kijimuna
[migrate 0_35_x to 0_40_0.xml]: /assets/migrations/migrate%20flexmark-java%200_35_x%20to%200_40_0.xml
[migrate flexmark-java 0_40_x to 0_42_0]: https://github.com/vsch/flexmark-java/blob/master/assets/migrations/migrate%20flexmark-java%200_40_x%20to%200_42_0.xml
[migrate flexmark-java 0_42_x to 0_50_0.xml]: https://github.com/vsch/flexmark-java/blob/master/assets/migrations/migrate%20flexmark-java%200_42_x%20to%200_50_0.xml
[NodeInsertingPostProcessorSample.java]: https://github.com/vsch/flexmark-java/blob/master/flexmark-java-samples/src/com/vladsch/flexmark/samples/NodeInsertingPostProcessorSample.java
[PdfLandscapeConverter.java]: https://github.com/vsch/flexmark-java/blob/master/flexmark-java-samples/src/com/vladsch/flexmark/samples/PdfLandscapeConverter.java
[YouTrack: IDEA-207453]: https://youtrack.jetbrains.com/issue/IDEA-207453 "Add Conversion of ref anchor to UrlFilter for file line navigation"

