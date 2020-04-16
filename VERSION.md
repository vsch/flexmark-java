# flexmark-java

&nbsp;<details id="version-history"><summary>**Version History**</summary>

[TOC]: # ""

- [Release 0.60.0](#release-0600)
  - [API Refactoring](#api-refactoring)
- [Next 0.61.12](#next-06112)
- [0.61.10](#06110)
- [0.61.8](#0618)
- [0.61.6](#0616)
- [0.61.4](#0614)
- [0.61.2](#0612)
- [0.61.0](#0610)
- [0.60.2](#0602)
- [0.60.0](#0600)
- [0.59.84](#05984)
- [0.59.82](#05982)
- [0.59.80](#05980)
- [0.59.78](#05978)
- [0.59.76](#05976)
- [0.59.74](#05974)
- [0.59.72](#05972)
- [0.59.70](#05970)
- [0.59.68](#05968)
- [0.59.66](#05966)
- [0.59.64](#05964)
- [0.59.62](#05962)
- [0.59.60](#05960)
- [0.59.58](#05958)
- [0.59.56](#05956)
- [0.59.54](#05954)
- [0.59.52](#05952)
- [0.59.50](#05950)
- [0.59.48](#05948)
- [0.59.46](#05946)
- [0.59.44](#05944)
- [0.59.42](#05942)
- [0.59.40](#05940)
- [0.59.38](#05938)
- [0.59.36](#05936)
- [0.59.34](#05934)
- [0.59.32](#05932)
- [0.59.30](#05930)
- [0.59.28](#05928)
- [0.59.27](#05927)
- [0.59.25](#05925)
- [0.59.23](#05923)
- [0.59.21](#05921)
- [0.59.19](#05919)
- [0.59.17](#05917)
- [0.59.15](#05915)
- [0.59.13](#05913)
- [0.59.11](#05911)
- [0.59.9](#0599)
- [0.59.7](#0597)
- [0.59.5](#0595)
- [0.59.1](#0591)
- [0.50.50](#05050)
- [0.50.48](#05048)
- [0.50.46](#05046)
- [0.50.44](#05044)
- [0.50.42](#05042)
- [0.50.40](#05040)
- [0.50.38](#05038)
- [0.50.36](#05036)
- [0.50.34](#05034)
- [0.50.32](#05032)
- [0.50.30](#05030)
- [0.50.28](#05028)
- [0.50.26](#05026)
- [0.50.24](#05024)
- [0.50.22](#05022)
- [0.50.20](#05020)
- [0.50.18](#05018)
- [0.50.16](#05016)
- [0.42.14](#04214)
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

&nbsp;</details>

## Release 0.60.0

### API Refactoring

:warning: Upcoming release of 0.60.0 will have some breaking changes due to re-organization,
renaming and clean up of some implementation classes.

Please give feedback on the upcoming changes if you have concerns about breaking your code:

* Break: split out generic AST utilities from `flexmark-util` module into separate smaller
  modules. IntelliJ IDEA migration to help with migration from 0.50.40 will be provided where
  needed if the package or class is changed. `com.vladsch.flexmark.util` will no longer contain
  any files but will contain the separate utilities modules with `flexmark-utils` module being
  an aggregate of all utilities modules, similar to `flexmark-all`
  * `ast/` classes to `flexmark-util-ast`
  * `builder/` classes to `flexmark-util-builder`
  * `collection/` classes to `flexmark-util-collection`
  * `data/` classes to `flexmark-util-data`
  * `dependency/` classes to `flexmark-util-dependency`
  * `format/` classes to `flexmark-util-format`
  * `html/` classes to `flexmark-util-html`
  * `mappers/` classes to `flexmark-util-sequence`
  * `options/` classes to `flexmark-util-options`
  * `sequence/` classes to `flexmark-util-sequence`
  * `visitor/` classes to `flexmark-util-visitor`
* Convert anonymous classes to lambda where possible.
* refactor `flexmark-util` to eliminate dependency cycles between classes in different
  subdirectories.
* Break: delete deprecated properties, methods and classes
* Add: `org.jetbrains:annotations:15.0` dependency to have `@Nullable`/`@NotNull` annotations
  added for all parameters. I use IntelliJ IDEA for development and it helps to have these
  annotations for analysis of potential problems and use with Kotlin.
* Break: refactor and cleanup tests to eliminate duplicated code and allow easier reuse of test
  cases with spec example data.
* Break: move formatter tests to `flexmark-core-test` module to allow sharing of formatter base
  classes in extensions without causing dependency cycles in formatter module.
* Break: move formatter module into `flexmark` core. this module is almost always included
  anyway because most extension have a dependency on formatter for their custom formatting
  implementations. Having it as part of the core allows relying on its functionality in all
  modules.
* Break: move `com.vladsch.flexmark.spec` and `com.vladsch.flexmark.util` in
  `flexmark-test-util` to `com.vladsch.flexmark.test.spec` and `com.vladsch.flexmark.test.util`
  respectively to respect the naming convention between modules and their packages.
* Break: `NodeVisitor` implementation details have changed. If you were overriding
  `NodeVisitor.visit(Node)` in the previous version it is now `final` to ensure compile time
  error is generated. You will need to change your implementation. See comment in the class for
  instructions.

  :information_source: `com.vladsch.flexmark.util.ast.Visitor` is only needed for implementation
  of `NodeVisitor` and `VisitHandler`. If you convert all anonymous implementations of
  `VisitHandler` to lambdas you can remove all imports for `Visitor`.
  * Fix: remove old visitor like adapters and implement ones based on generic classes not linked
    to flexmark AST node.
  * Deprecate old base classes:
    * `com.vladsch.flexmark.util.ast.NodeAdaptedVisitor` see javadoc for class
    * `com.vladsch.flexmark.util.ast.NodeAdaptingVisitHandler`
    * `com.vladsch.flexmark.util.ast.NodeAdaptingVisitor`

## Next 0.61.12


## 0.61.10

* Add: `HtmlRenderer.FENCED_CODE_LANGUAGE_DELIMITERS`, default `" \t"`, to configure which chars
  terminate the language part of info string
* Add: `HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_MAP`, default `new HashMap()`, to provide
  individual language to class mapping string. If language string is not in the map then
  `HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX` + language will be used. Intended to allow
  creating alias mapping and per-language class configuration.

## 0.61.8

* Fix: wrong assert conditions in `LineAppendableImpl.setPrefixLength(int, int)`
* Fix: assert condition for `MarkdownParagraph.resolveTrackedOffsetsEdit`, space and non-break
  space should be considered a match.
* Fix: `MarkdownParagraph.resolveTrackedOffsetsEdit`, space and non-break space should be
  considered a match.
* Add: details to assert for `SegmentTree.findSegmentPos(int, int[], int, int)` to allow
  diagnostics in reports

## 0.61.6

* Add: `HtmlRenderer.HEADER_ID_REF_TEXT_TRIM_LEADING_SPACES` default `true` and
  `HtmlRenderer.HEADER_ID_REF_TEXT_TRIM_TRAILING_SPACES` default `true`. When `false` then
  corresponding reference link text in heading text used for generating heading id is not
  trimmed.
* Add: `HtmlRenderer.HEADER_ID_ADD_EMOJI_SHORTCUT` default `false` When `true` then emjoi nodes
  add emoji shortcut to collected text for heading id.
* Fix: `ParserEmulationProfile.GITHUB`
  * Set `HtmlRenderer.HEADER_ID_REF_TEXT_TRIM_TRAILING_SPACES` to `false` because GitHub does
    not trim trailing spaces for generating heading ids.
  * `Emoji` node to generate text for heading id consisting of the emoji shortcut, for GitHub
    compatibility.
  * Fix: `ImageRef` not to add text to heading text for heading id generation
* Fix: add more information to `MarkdownParagraph.resolveTrackedOffsetsEdit` assert failures to
  allow better diagnostics in reported stack traces.
* Fix: disable wrapping of multiline URL image links
* Fix: `Paragraph` content node to preserve leading spaces on lines. Otherwise, multiline URL
  image content would loose indents since these were stripped from paragraph content during
  parsing.
  * Fix: `TablesExtension` to accept non-indenting leading spaces on all table rows. This
    results in previously non-table text as valid table texts:

    ```markdown
    Abc|Def
      |---|---
    ```
* Fix: `LinkRef` and `ImageRef` changing spaces to `&nbsp;` for wrap formatting when not part of
  a `Paragraph`, ie. in heading text.

## 0.61.4

* Fix: merge [#397, PR: Add base64 image support with docx rendering] thanks to [@Xaelis]
  * Add: test for base64 encoded docx conversion
* Break: `WikiLinkLinkResolver` to take `WikiLinkExtension.ALLOW_ANCHORS` and
  `WikiLinkExtension.ALLOW_ANCHOR_ESCAPE` into account when extracting page ref from link.
  * Fix: `#` or `\` included in the URL of the resolved link are now URL encoded.
  * Fix: default `WikiLinkLinkResolver` handles its own text unescaping. When resolving wiki  
    link with default resolver do not unescape. If replacing default resolver ensure you
    unescape the text. **This is needed to properly handle anchor escaping.**
  * Fix: `PegdownOptionsAdapter` to set `WikiLinkExtension.ALLOW_ANCHORS` to `true` for pegdown
    compatibility
* Fix: deprecate `CoreNodeFormatter.ATTRIBUTE_UNIQUIFICATION_ID_MAP` and move declaration to
  `Formatter.ATTRIBUTE_UNIQUIFICATION_ID_MAP`
* Fix: deprecate `CoreNodeFormatter.UNIQUIFICATION_ID_MAP` and move declaration to
  `Formatter.UNIQUIFICATION_ID_MAP`
* Fix: wiki links should not be wrapped during formatting.
  * Add: WikiLink formatter extension and tests
  * Add: WikiLink translating formatter functionality and tests

## 0.61.2

* Fix: `ScopedDataSet.getAll()` and `ScopedDataSet.getKeys()` would not return keys from parent
  scope.
* Fix: [#396, DocumentParser stops reading too early resulting in the document being cut off] 

## 0.61.0

* Fix: enable original spec tests
* Fix: merge [#391, PR: Fix: CRLF line separator in fenced code blocks produce redundant CR.]
* Fix: merge [#387, JUnit is in the compile scope]
* Add: ability to insert anchor ref targets to `HtmlIdGenerator` when scanning a document.
* Add: add `LinkResolverBasicContext` as base interface of `LinkResolverContext` to allow simple
  context to pass to `LinkResolver` when resolving links without needing to implement all unused
  methods of `LinkResolverContext`.
* Break: change argument to `LinkResolver` and `LinkResolverFactory` to
  `LinkResolverBasicContext`. Except for `JekyllTagExtension`, the argument is still
  `LinkResolverContext` and passed value can be cast to this if required.
* Add: support for `JekyllTagBlock` with `include` directive to `DocxRenderer`

  Use `{% include includeFile %}` to include the file into the source document. `includeFile` is
  resolved relative to `DocxRenderer.DOC_RELATIVE_URL` or `DocxRenderer.DOC_ROOT_URL`. The
  latter is used if `includeFile` starts with `/`

  Will only include files if `JekyllTagExtension.LINK_RESOLVER_FACTORIES` is not empty, in which
  case the link resolvers will be used to resolve `includeFile` to full file path. Use singleton
  list of `DocxLinkResolver.Factory` instance to resolve using doc relative and root url paths.
* Fix: `MergeLinkResolver` and `DocxLinkResolver`

## 0.60.2

* Fix: change formatter sub-context rendering node to nullable

## 0.60.0

* Fix: rename `BaseSequence.extendToAny` to `BaseSequence.extendByAnyNot` which better reflects
  the operation of the method.
* Deprecate: `BaseSequence.extendToAny`
* Add: `FlexmarkHtmlConverter` `div` to table options to allow converting `div` based table code
  to markdown tables.
* Fix: move `BaseSequenceManager` and related to `flexmark-util-experimental` since these are
  not used.

## 0.59.84

* Fix: convert `MarkdownTable` to use common `TrackedOffset` for offset tracking API
* Fix: convert `MarkdownParagraph` to use common `TrackedOffset` for offset tracking API
* Fix: deprecate old `addTrackedOffset` methods.
* Fix: rename formatter `HEADER` options to `HEADING`
* Fix: deprecate old header named options
* Fix: delete unused formatter option `KEEP_TRAILING_SPACES`
* Fix: delete unused formatter option `CODE_KEEP_TRAILING_SPACES`

## 0.59.82

* Add: missing formatter options:
  * `HeadingStyle`
  * `BLOCK_QUOTE_CONTINUATION_MARKERS`
  * `LIST_RESET_FIRST_ITEM_NUMBER` applies if renumbering items
  * `LIST_ALIGN_NUMERIC`
  * `flexmark-ext-attributes` formatting of individual attributes instead of dumping the
    attributes node text.
    * `FORMAT_ATTRIBUTES_COMBINE_CONSECUTIVE`
    * `FORMAT_SORT_ATTRIBUTES`
    * `FORMAT_ID_ATTRIBUTE`, `AttributeImplicitName` enum, as is, implicit preferred, explicit
      preferred
    * `FORMAT_CLASS_ATTRIBUTE`, `AttributeImplicitName` enum, as is, implicit preferred,
      explicit preferred
    * `FORMAT_ATTRIBUTES_SPACES`
    * `FORMAT_ATTRIBUTE_EQUAL_SPACE`
    * `FORMAT_ATTRIBUTE_VALUE_QUOTES`
  * prioritized task items: `flexmark-ext-gfm-tasklist`
    * `FORMAT_ORDERED_TASK_ITEM_PRIORITY`: `Integer`, priority to use for parent item. Ordered
      task items do not not sort on priority since they are already ordered by number.
    * `FORMAT_ORDERED_TASK_ITEM_PRIORITY`: `Integer`, priority to use for parent item. Ordered
      task items do not not sort on priority since they are already ordered by number.
    * `FORMAT_TASK_ITEM_PRIORITIES`: `int[]`, specifies integer priority for item marker
      characters corresponding to `LISTS_ITEM_PREFIX_CHARS` used for list items. Higher number
      is higher priority. A parent item's priority is the max priority of itself and any of its
      descendant task items. Non-task items do not have their own priority, only priority
      derived from their descendant items.
  * `ElementPlacementSort`
    * `SORT_DELETE_UNUSED`
    * `DELETE_UNUSED`
  * `LINK_MARKER_COMMENT_PATTERN`
  * `CONTINUATION_ALIGNMENT`
  * Add: Formatter control for controlling non-formatting regions.
    * `<!-- @formatter:on -->` and `<!-- @formatter:off -->` tags
    * `FORMAT_CONTROL_ON`: `String`
    * `FORMAT_CONTROL_OFF`: `String`
    * `FORMAT_CONTROL_REGEX`: `Boolean` to treat control on/off strings as regex pattern.
  * implement `SimTocExtension.FORMAT_UPDATE_ON_FORMAT` option
* Break: rename `CharWidthProvider` methods and change `zeroWidth` argument to `CharPredicate`
* Fix: `TestUtils.getOptions(SpecExample, String, Function<String, DataHolder>)` to return
  immutable data holder to prevent leak of options between spec examples
* Fix: escape of plain `.` at start of continuation.
* Fix: `ListBlockParser` escape/unEscape handler not checking if returned index == -1 and not
  handling empty char sequence.
* Fix: `MarkdownParagraph`
  * not checking if passed sequence is empty before invoking special lead-in escape/unEscape
    handlers, which is not part of the API contract.
  * backspace after typing a char should not remove spaces which surrounded the char
* Fix: make `Paired` extend `Map.Entry`
* Fix:
  [#381, StackOverflowError with long base64 image and LINKS\_ALLOW\_MATCHED\_PARENTHESES disabled],
  `USE_HARDCODED_LINK_ADDRESS_PARSER` was not used if matched parentheses were disabled causing
  stack overflow in regex evaluation. szxvalsdfj lasdf jffdddd

## 0.59.80

* Fix: erroneous not-null annotation on nullable return in
  `InlineParser.parseCustom(BasedSequence, Node, BitSet, Map<Character, CharacterNodeFactory>)`

## 0.59.78

* Fix: `MarkdownParagraph` wrap tracked offset adjustment:
  * on typing space in middle of word with second word becoming first non blank on a
    continuation line.
  * on typing space at start of continuation line with `LS` ahead of position.
  * on typing space after non-space at start of continuation line with `LS` ahead of position.
  * on typing space after end of paragraph after the right margin.

## 0.59.76

* Fix: `MarkdownParagraph`
  * Add: tracked offsets handling to prepare and resolve tracked offsets in wrapped text
    * handle inserting a space when tracked offset is followed by a space to allow two spaces at
      tracked offset
    * handle tracked offset followed by `LS` to resolve offset to end of previous line
    * handle backspace on first character of continuation line to splice word to last word of
      previous line.
  * Fix: To take `CharSequence` for indent properties instead of `int` for number of spaces to
    allow arbitrary indentation characters.
* Fix: `SegmentOffsetTree` aggregated length replacement to contain `startOffset` of next
  segment instead of `endOffset` of segment at position, to mimic equivalent aggregated length
  of `SegmentTree`.
* Fix: `BasedOffsetTracker` to handle 0 span and 1 span offset ranges and return info for the
  requested offset.

## 0.59.74

* Fix: `MarkdownParagraph` line break getter/setter names.
* Fix: `BasedOffsetTracker`
  * if segment end offset equals offset and no segment containing offset is found, then use
    segment for index calculation.
  * assertion conditions when search returns null, offset can be >= end offset

## 0.59.72

* Add: `Formatter` Paragraph wrapping options and code.
  * Add: `Formatter.RIGHT_MARGIN`, default `0`, if >0 then text will be wrapped to given margin
  * Add: `Formatter.APPLY_SPECIAL_LEAD_IN_HANDLERS`, default `true`, when true will escape
    special lead-in characters which wrap to beginning of line and un-escape any which wrap from
    beginning of line. Used to prevent special characters inside paragraph body from starting a
    new element when wrapped to beginning of line.
* Add: `CustomBlockParserFactory.getLeadInHandler(DataHolder)` and handle escaping/unescaping
  special lead in characters for the block elements. Add escape/unescape to:
  * `BlockQuoteParser`
  * `ListBlockParser`
  * `HeadingParser`
  * `AdmonitionBlockParser`
  * `AsideBlockParser`
  * `DefinitionItemBlockParser`
* Add: `Parser.Builder.specialLeadInHandler(SpecialLeadInHandler)` to allow parser extensions to
  register special lead in escaper/unescaper handlers for parser extension block elements if
  they do not have any custom block parser factories but do create extensions.
* Add: special lead-in escape/unescape handlers during text wrapping
* Add: `SegmentOffsetTree` for binary search on offset in base sequence for conversion to index
  in result sequence.
* Fix: `BitFieldSet.toString()` to output field values when fields are more than one bit long.
* Add: `BasedOffsetTracker` to take `BasedSequence` result (could be segmented, offset in based
  sequence, `PositionAnchor` and return index of this offset in the resulting based sequence.

## 0.59.70

* Fix: convert `BitEnumSet` to `BitFieldSet` to allow bit fields of more than 1 bit.
  * Add: Optional `BitField` interface, if implemented by Enum for use by `BitFieldSet` then bit
    fields can be 1 to 64 bits in size, with manipulation of bit packed values and iteration
    over non-zero valued elements.

## 0.59.68

* Fix: rename `LineFormattingAppendable` to `LineAppendable`
* Fix: remove unused overloads and methods from `LineFormattingAppendable`
* Add: new methods to `LineFormattingAppendable` for prefix changes and combined prefix/line
  manipulation.
* Break: rename `LineAppendable.F_SUPPRESS_TRAILING_WHITESPACE` to
  `LineAppendable.F_TRIM_TRAILING_WHITESPACE`
* Break: rename and **invert** `LineAppendable.F_ALLOW_LEADING_WHITESPACE` to
  `LineAppendable.F_TRIM_LEADING_WHITESPACE`
* Deprecate: convenience copies of `LineAppendable` flags in `Formatter` and `HtmlRenderer`. Use
  `LineAppendable` directly.
* Break: `Formatter.FORMAT_FLAGS` default changed from `0` to
  `LineAppendable.F_TRIM_LEADING_WHITESPACE` to reflect inversion of option flag and preserve
  behavior
* Break: `HtmlRenderer.FORMAT_FLAGS` default changed from `0` to
  `LineAppendable.F_TRIM_LEADING_WHITESPACE` to reflect inversion of option flag and preserve
  behavior
* Fix: remove `my` prefix from fields in `flexmark-utils` to keep consistent naming convention
  in module.
* Add: `BitEnumSet` to expose the bit mask of elements to use as efficient and convenient bit
  masked options
* Add: `ArrayUtils.toArray(BitSet)` to return an `int[]` of all bit numbers that are set.
* Break: make constructor `Range(int, int)` protected. Use `Range.of` to create instances.

## 0.59.66

* Fix: move experimental concept classes out of the library. Too much clutter of unused old
  experimental stuff.
  * Fix: move `PositionList` related classes to `flexmark-util-experimental`
  * Fix: move unused classes from utils to experimental
* Fix: rename `BasedSequenceBuilder` to `SequenceBuilder`
* Fix: `MarkdownParagraph`
  * use `SegmentBuilder` for accumulating wrapped text.
  * Fix: remove any position tracking code from paragraph formatter

## 0.59.64

* Fix: `SoftBreakNode` in link ref would be trimmed and loose contained EOL as a result.
* Fix: `SegmentedSequence` to take `SegmentBuilder` parts for sequence generation instead of
  list of based sequences. The parts are already resolved by builder, based sequences duplicates
  useless work on both ends.
  * Fix: optimize storage by implementing binary search with segments serialized to byte array.
    * Add: build `SegmentBuilder` from `SegmentTree`
    * Add: code to extract `startOffset`/`endOffset` and `treeData` range for `subSequence` of
      `SegmentedSequenceTree`
    * Add: `SegmentTree` binary search tree for segmented sequence segments
  * Add: `SegmentedSequenceTree` binary tree based segmented sequences with option to use old
    `SegmentedSequenceFull` if desired.
  * Add: build `SegmentTree` from `SegmentBuilder` segments
* Add: segment builder text part stats for first256 and repeatedText, former if all chars in
  part < 256, latter if all chars are the same. In preparation for storage optimized segmented
  sequence impl.
* Fix: optimized `SegmentBuilder`
  * Change `TEXT` segments from `String` content to start/end being the -ve of start+1/end+1 of
    the subsequence for this `TEXT` in `StringBuilder`
  * Change `Seg` to remove `myText` field and require `CharSequence` argument for to be passed
    to all methods which returned `myText` in previous implementation. Returned value should be
    `CharSequence` which is the `subSequence` representing the `TEXT` content.
  * `TEXT` segments no longer have a start/end offset of prev/next `BASE` since their start/end
    offsets represent offset in `StringBuilder` for their character sequence. Their base start
    offset is `endOffset` of previous `BASE` or `startOffset` of `SegmentBuilder` if first
    segment. Their `endOffset` is `startOffset` of next `BASE` or `endOffset` of
    `SegmentBuilder` if it is a dangling `TEXT` segment.
  * `TEXT` segments to be accumulated in `StringBuilder` so a single char sequence for out of
    base characters is available.
  * `SegmentBuilder` tracks last `immutableOffset` in its `StringBuilder` to reflect the
    accumulated, immutable out of base `TEXT` segments in the segment builder.
  * `immutableOffset` and `StringBuilder.length()` represents the start/end offsets of dangling
    `TEXT` segment.
  * dangling `TEXT` segment is mutable **until** a `BASE` segment is added after it which
    results in `SegmentBuilder` `endOffset` change and after `optimizeText` method has been
    invoked. If `TEXT` segment becomes immutable then `immutableOffset` is changed to the
    `endOffset` of the `TEXT` segment's subSequence end.
  * adding an `ANCHOR` after dangling `TEXT` with an `offset` equal to `StringBuilder`
    `endOffset` will invoke `optimizeText` to allow extending previous `BASE` range and reducing
    dangling `TEXT` content. It will not change the dangling `TEXT` to immutable `TEXT` to allow
    appending more text after the call.
  * appending `TEXT` to `SegmentBuilder` appends the text to `StringBuilder` and updates out of
    base stats for appended `TEXT` content. Out of base stats **always** reflect the current
    content of `SegmentBuilder`
  * methods:
    * `handleOverlap` is invoked when an overlapping, `BASE` segment is added. It may result in
      calling `optimizeText` if the resolution of overlap causes a `BASE` segment to be appended
      after a dangling `TEXT` segment.
      * An exception is thrown if:
        * an overlap is found in returned segments
        * text length of returned segments does not equal the text length of original sequence.
      * Arguments are `Object[3]`: last `BASE` range, optional dangling `TEXT` and new `BASE`
        range passed as `Object[]` with `Range` and `CharSequence` representing `BASE` and
        `TEXT` segments.
      * `StringBuilder` content is truncated to `immutableOffset` and out of base statistics are
        updated to remove the `TEXT` content character information before method invocation
        because they will be updated for changed segments returned by the method.
      * It must return an `Object[]` of segments that resolve the overlap. No optimization needs
        to be done at this point because `optimizeText` will be called if needed as the
        resulting segments are appended to the `SegmentBuilder`
      * `SegmentBuilder` will replace last range and append the rest of the returned segments
        and update all internal structures, eliminating the concern for these in
        `handleOverlap`.
    * `optimizeText` called when a `BASE` or `ANCHOR` segment is added after dangling `TEXT`
      segment.
      * An exception is thrown if:
        * an overlap is found in returned segments
        * text length of returned segments does not equal the text length of original arguments.
      * Arguments are `Object[3]`: last `BASE` range or `Range.NULL`, dangling `TEXT` and new
        `BASE`/`ANCHOR` range or `Range.NULL` are passed as `Object[]` with `Range` and `String`
        representing `BASE`/`ANCHOR` and `TEXT` segments respectively.
      * `StringBuilder` content is truncated to `immutableOffset` and out of base statistics are
        updated to remove the `TEXT` content character information before method invocation
        because they will be updated for changed segments returned by the method.
      * Method to return an `Object[]` of optimized segments, with `Range` representing
        `BASE`/`ANCHOR` segments and `CharSequence` representing `TEXT` segments.
      * `SegmentBuilder` will replace last range and append the rest of the returned segments
        and update all internal structures, eliminating the concern for these in `optimizeText`.

## 0.59.62

* Fix: remove space to `&nbsp;` conversion of spec example first line. Was done to allow GitHub
  to display spec examples as fenced code. No longer needed since GitHub switched to CommonMark
  parser.
* Fix: update all spec files to have no non-break spaces in example opener.
* Add: `TestUtils.customIntOption(String, String, Function<Integer, DataHolder>)` and
  `TestUtils.customStringOption(String, String, Function<String, DataHolder>)` to ease creating
  `CUSTOM_OPTION` option types.

## 0.59.60

* Add: `SettableInstance` and `SettableExtractedInstance` classes to facilitate implementation
  of settings which can be changed through spec example options.
* Break: move `SpecReader.getSpecInputStream(ResourceLocation)` and
  `SpecReader.getReadSpec(ResourceLocation)` to
  `ResourceLocation.getResourceInputStream(ResourceLocation)` and
  `ResourceLocation.getResourceText(ResourceLocation)`
* Add: `ResourceLocation.getResourceInputStream()` and `ResourceLocation.getResourceText()`
* Add: static `BuilderBase.removeExtensions(DataHolder, Collection<Class<? extends Extension>>)`
  for removing extension classes from `SharedDataKeys.EXTENSIONS`.

## 0.59.58

* Add: `SequenceUtils.equals(CharSequence, Object)` to consolidate char sequence equals
* Fix: `SegmentedBuilder` if anchor position is less than current `endOffset` then ignore
  anchor.
* Deprecate: `TestUtils.showTabs(String)` and `TestUtils.unShowTabs(String)` in favour of
  `TestUtils.toVisibleSpecText(String)` and `TestUtils.fromVisibleSpecText(String)` because
  these convert more than tabs.

## 0.59.56

* Fix: bug causing duplicated text in segmented sequences
* Fix: optimize `SegmentedSequence`, non base characters are now stored in base offsets as
  `-(char+1)`, this eliminates `nonBasedChars` completely.
* Add: segmented sequence stats collection
* Add: options to based sequences which are not used unless a sequence passed to
  `BasedSequence.of` is first wrapped in `BasedOptionsSequence`. Then all sequences created from
  this base will have int bit options testable with `BasedSequence.isOption(int)` and get
  arbitrary options with `BasedSequence.getOption(DataKeyBase<T>)` or just get the whole
  `DataHolder` options via `BasedSequence.getOptions()` for more sophisticated needs. If the
  `DataHolder` passed to `BasedOptionsSequence` is mutable then

  Makes adding customized behavior and features easy and transparent to implementation with no
  overhead since `SubSequence` delegates to its `baseSeq` if it is a `BasedOptionsHolder` for
  these methods but otherwise does no checking. The overhead is one level of delegation of
  `CharSequence` methods for sequences with options with no impact on the rest.

## 0.59.54

* Fix: rework `PositionList` and `Position`
  * Add: concept of scope framing to invalidate all position created in the frame and not taken
    out via `Position.unframed()`, will also take out listeners and preview listeners added in
    the frame. This optimizes removal of positions from notifications. Otherwise the update list
    grows fast and with every iteration, until gc is run, which may be a while. Problem becomes
    worse if the position list is long lived and accumulates positions which were not detached
    over its lifetime, between gc runs. Every iteration should be scoped. Iterating 10x over a
    10 item list without scoping has max 110 listener updates, scoping inner loop only 10 max
    listeners, scoping inner loop 1.

    Usage is: `Object frame = openFrame(); try { ... } finally { closeFrame(frame); }` with all
    positions created between open/close frame being detached. Frames can be nested and are
    validated for double closing, unclosed nested, and other common errors. Positions will throw
    an exception if used after frame is closed.
  * Add: `IPreviewPositionListener` to `PositionList` so changes can be previewed and changed.
    Useful for `SegmentBuilder` to keep invariants and tracked counts updated when these are
    changed by optimizers so optimizers do not need to concern themselves about these.
* Add: `SegmentBuilder` option flags for tracking unique non-based characters and including
  anchors (0 span ranges) in the segment list.

## 0.59.52

* Add: `BasedSequence.addSegments(BasedSegmentBuilder)` so each sequence adds its own optimized
  segment list without requiring by character scanning ofg of
  `BasedSequence.getIndexOffset(int)` and building the segments the hard way.
  * Fix: `BasedSequenceBuilder` to use `BasedSequence.addSegments`
    * `ReplacedBasedSequence` it has no use other than to test if sequence replaces base
  * Fix: Change segment parts to `EditOp` which extends range and holds an nullable string. This
    will allow keeping track of replaced parts of base sequence.
    * if string is null then it is a range from base
    * range is empty then string is not null and is an insert at position in base
    * range not empty and string not empty then it is a replacement of base segment.
* Fix: rename `BasedCharsRecoverySegmentOptimizer` to `CharRecoveryOptimizer`
* Fix: `SegmentBuilder` change `append...()` to plain `append()`
* Fix: `SegmentBuilder` to keep track of start and end range in the list based on first/last
  range added.
  * Test: need to test this more thoroughly
  * Fix: simplify overlap by passing index to last range in list and the range overlapping it.
    No position.
  * Fix: simplify optimization by passing the full list to optimizer. No position list.
  * Fix: eliminate multiple methods for the same functionality, append: `EditOp`, start/end
    offsets or `String`. The rest are history.
  * Test: cached start/end/length/last range index.
* Add: `BasedSegmentBuilder` to convert overlap in added ranges to out of base text
* Fix: change `BasedSequenceBuilder` to use `BasedSegmentBuilder` for segment accumulation
  instead of its own implementation.
  * Add: construction with optional `BasedSegmentOptimizer` list and apply optimizers to segment
    list before generating sequence or string.
  * Fix: if there is an overlap in appended sequence with previous segments, add the overlap as
    out of base text instead of throwing an exception.
  * Fix: optimize and simplify for speed, too many methods. Keep only add/append of
    `CharSequence` and add/append for start/end offsets. The rest are gone.
* Add: Common char sets:
  * `DECIMAL_DIGITS`
  * `HEXADECIMAL_DIGITS`
  * `OCTAL_DIGITS`
* Fix: infinite recursion in deprecated `SubSequence.of`
* Break: delete `BasedSequence.getIndexRange(int, int)` does not do the intended job and is
  complicated and useless at the same time for its purpose.
* Deprecate: to be delete `BasedSequence.getIndexOffset(int)` as soon as `SegmentedSequence` is
  migrated to segment builder sequence part list.

## 0.59.50

* Fix: move spec resource files to `flexmark-test-specs` resources. Any modules needing the spec
  have to add a test dependency on this module.
* Break: try to reduce footprint of `IRichSequence` and `IRichSequenceBase` by making getting
  rid off some overloads with default params.
  * Add: factor out bulk of `IRichSequenceBase` to `SequenceUtils` so functionality could be
    used on any `CharSequence`.
  * Fix: change all character inclusion based functions to use `CharPredicate`
* Add: `CharPredicate` to consolidate all character inclusion testing via predicates.
* Fix: make `BaseSequence.of` use plain based sequences and not managed for now. Issues when
  mutable sequences are passed as parameters make it unstable. Have to use or immutable char
  sequence or char backed sequence for managed and that will create too much overhead if used
  for all cases.
* Add: `PositionList` and `Position` classes to represent list and position in that list which
  allow modification, deletion, insertions while iterating over the list. Multiple position
  instances to different indexed elements can be created and all will be updated to reflect
  changes in their element's position.
  * Fix: cleanup naming conventions in new classes.
  * Fix: rewrite implementation and optimizer using `PositionList` and `Position` instead of
    hand rolled code.
  * Fix: make `Position` handle insert/delete index change invalidation instead of
    `PositionList`
  * Add: add anchor for position to allow next/prev anchoring
  * Add: positions are now have iterable from position to end/start of list, or from next to
    end/previous to start.
  * Fix: replace flags and position anchor field with byte flags.

## 0.59.48

* Fix: `SegmentBuilder` to not add empty text.
* Add: `SegmentOptimizer` for `SegmentBuilder` segment optimization
* Add: `CharMatchingSegmentOptimizer` to change replaced text to range in sequence when the
  replacement text matches sequence characters. Eliminates out of base unnecessary characters.
* Fix: add quick fail/success test for equality of base object. base strings through delete
  range/insert string operations.
* Fix: make all based sequence test for same base use `getBase()` instead of
  `getBaseSequence()`. It is the underlying base object that is important not the based sequence
  used to wrap it.
* Add: `BasedSequenceManager` to allow re-use of equivalent base sequences to eliminate the
  problem of having different base sequences for the same text content. Only implemented for
  `SubSequence` not `CharSubSequence`

  Uses weak refs and weak hash map so as long as there is a reference to previously created
  sequence base, requests to `BaseSequenceManager.INSTANCE.getBaseSequence(T, Function<T,
  BasedSequence>)` will return an existing based sequence for the passed object.

  Uses fast fail on `length()` and `hashCode()` differences with fallback on weak hash map for
  getting previously computed equality comparisons to minimize expensive duplicate content
  comparisons for previously determined equal/unequal sequence bases.

  Weak refs and maps ensure cache is cleared when last used reference to base or its subsequence
  is released and gc() has run.
* Fix: change `SubSequence` to create a `String` if passed sequence implements `Appendable` to
  eliminate use of mutable sequences as bases the same way `CharSubSequence` did for
  `StringBuilder`. It is important not to use mutable `CharSequences` for `BasedSequence.of()`
  argument. If not sure `toString()` on the argument is the best option.
* Fix: change all possible uses of `CharSubSequence` to `SubSequence`
* Add: `SegmentBuilder` to track offset ranges in original or base sequence and inserted out of
* Fix: `BasedSequenceBuilder.add(CharSequence)` now does not complain when adding a
  `BasedSequence` which is from a different base than the builder. It simply treats it as it
  does any other non-based sequence and adds it as out of base chars.
* Fix: `SequenceBuilder.addAll(Collection<? extends CharSequence>)` now takes list of anything
  extending `CharSequence`
* Add: javadoc to `BasedSequence.baseSubSequence(int, int)` that it should return sequence equal
  to base sequence without any modifications or additions. This should really be implemented
  only in real base sequences like `CharSubSequence` and `SubSequence` the rest should rely on
  `BasedSequenceImpl`.

## 0.59.46

* Fix: merge formatter always adding `<>` around autolinks even when original did not have them.
* Fix: docx conversion renders mail link text with `mailto:` prefix
* Fix: add `'\0'` to `'\uFFFD'` conversion in `RichSequence`
* Fix: [#376, convert markdown to html], delimiters are not allowed to span table cells.
* Add: `Text addText(String value, boolean noProofRPr, boolean createR)`
* Add: `Text addTextCreateR(String value, boolean noProofRPr)`
* Add: `Text addTextCreateR(String value)`
* Add: `Text addText(String value, boolean noProofRPr)`
* Add: `Text addText(String value)`
* Add: `Text addInstrText(String value, boolean noProofRPr, boolean createR)`
* Add: `Text addInstrTextCreateR(String value, boolean noProofRPr)`
* Add: `Text addInstrTextCreateR(String value)`
* Add: `Text addInstrText(String value, boolean noProofRPr)`
* Add: `Text addInstrText(String value)`
* Add: `FldChar addFldChar(STFldCharType charType, boolean createR)`
* Add: `FldChar addFldCharCreateR(STFldCharType charType)`
* Add: `FldChar addFldChar(STFldCharType charType)`
* Deprecate: `DocxContext.addWrappedText()` use `DocxContext.addText(String)`
* Deprecate: `DocxContext.text(String)` use `DocxContext.addTextCreateR(String)`
* Fix: `IRichSequenceBase.split` and `IRichSequenceBase.splitList` some combinations would pass
  parameters out of place causing wrong operation.
* Fix: `MarkdownParagraph` wrapping to use simplified left aligned wrapping with no trailing
  spaces

## 0.59.44

* Add: docx renderer form fields via `[name]{.class attributes}` where name is given by
  `DocxRenderer.FORM_CONTROLS` and if not empty or blank then an unresolved reference link with
  that name will be converted to a word form input field with type and options given in teh
  attributes.
  * drop down list, class name `.dropdown` attributes:
    * `name` - name for the form field
    * `default` - default selection first item if no default provided. Can be option text (case
      sensitive tried first, if no match then case insensitive is tried), if text match fails
      then attempted to parse as an integer index 1..number of options
    * `help` - text to show in status bar and on hitting F1
    * `options` - list of options separated by `|`, individual options will be trimmed but
      otherwise left as is
  * checkbox, class name `.checkbox` attributes:
    * `name` - name for the form field
    * `checked` - default will be checked, otherwise unchecked
    * `help` - text to show in status bar and on hitting F1
  * text, class name `.text` attributes:
    * `name` - name for the form field
    * `default` - default value
    * `help` - text to show in status bar and on hitting F1
    * `max-length` - number or leave out for unlimited
    * `type` - gives the type of text field
      * `regular` - default if type not provided, regular text
        * `format` - `UPPERCASE`, `LOWERCASE`, `FIRST CAPITAL`, `TITLE CASE`, not case
          sensitive, space between words can be eliminated or replaced by `-`
      * `date` - date
        * `format` - word date format
      * `number` - number
        * `format` - word number format
      * `current-date` - current date
        * `format` - word date format
      * `current-time` - current time
        * `format` - word time format

## 0.59.42

* Break: `BasedSequence.append(CharSequence[])` now constructs as segmented sequence so proper
  ordering of segments is enforced. This means `IllegalArgumentException` will be thrown when
  this condition is not met. To get old functionality you need to create an appended string of
  all sequences and `BasedSequence.of()` on that string.
* Break: rename `MappedSequence` to `MappedBasedSequence` to allow for `MappedRichSequence`
* Break: rename `RichCharSequence` to `IRichSequence` to separate base interfaces for rich and
  based sequences and allow for `RichSequence` to be the base interface for rich sequences
  similar to `BasedSequence` for based sequences
* Break: rename `RichCharSequenceBase` to `IRichSequenceBase`
* Break: rename `RichCharSequenceImpl` to `RichSequenceImpl`
* Break: rename `RepeatedCharSequence` to `RepeatedSequence`
* Add: `RichSequence.of` for creating `RichSequence` instances
* Add: `BasedSequence.of` for creating `BasedSequence` instances
* Deprecate: `RichSequenceImpl.of`, use `RichSequence.of` instead
* Deprecate: `BasedSequenceImpl.of`, use `BasedSequence.of` instead
* Deprecate: `SubSequence.of`, use `BasedSequence.of` instead
* Deprecate: `UpperCaseMapper` and `LowerCaseMapper` use `ChangeCase.toUpperCase` and
  `ChangeCase.toLowerCase`
* Deprecate: all static factory method named `of` to more specific so that errors are less
  likely. `repeatOf`, `prefixOf` otherwise it is possible to have the wrong factory method
  called and not see it.
* Break: move
  * `com.vladsch.flexmark.tree.iteration.ArrayIterable` to
    `com.vladsch.flexmark.util.collection.iteration.ArrayIterable`
  * `com.vladsch.flexmark.util.html.CellAlignment` to
    `com.vladsch.flexmark.util.collection.iteration.ArrayIterable`
  * `com.vladsch.flexmark.ast.DelimitedNode` to `com.vladsch.flexmark.util.ast.DelimitedNode`
* Fix: `IRichSequence` and by extension `RichSequence` and `BasedSequence`
  <!-- @formatter:off -->
  * Add: `IRichSequence.trimToEndOfLine(CharSequence eolChars, boolean includeEol, int index)` - trim end to end of line at index
  * Add: `IRichSequence.trimToStartOfLine(CharSequence, boolean, int)` - trimmed end after next EOL
  * Add: `IRichSequence.leadingBlankLinesRange(CharSequence eolChars, int fromIndex, int endIndex)` - find next range of blank lines
  * Add: `IRichSequence.trailingBlankLinesRange(CharSequence eolChars, int startIndex, int fromIndex)` - find previous range of blank lines
  * Add: `IRichSequence.blankLinesRemovedRanges(CharSequence eolChars, int fromIndex, int endIndex)` - list of ranges in sequence between `fromIndex` and `endIndex`  excluding all blank lines
  * Add: range based methods:
  * `IRichSequence.subSequence(Range)`
  * `IRichSequence.subSequenceAfter(Range)`
  * `IRichSequence.subSequenceBefore(Range)`
  * Add: editing methods:
  * `IRichSequence.insert(CharSequence, int)` insert another char sequence at index
  * `IRichSequence.delete(int, int)` delete range in this sequence
  * `IRichSequence.replace(CharSequence, CharSequence)` one char sequence in this sequence with another
  * `IRichSequence.replace(int, int, CharSequence)` range in this sequence with char sequence
  <!-- @formatter:on -->
* Add: to `BasedSequence`
  <!-- @formatter:off -->
  * Add: `BasedSequence.extendToEndOfLine(CharSequence eolChars, boolean includeEol)` - extend end to end of line in basedSequence
  * Add: `BasedSequence.extendToStartOfLine(CharSequence eolChars, boolean includeEol)` - extend start to start of line in basedSequence
  <!-- @formatter:on -->
  * Convenience methods returning coordinates from the base sequence
    * `@NotNull Pair<Integer, Integer> baseLineColumnAtIndex(int index);`
    * `@NotNull Range baseLineRangeAtIndex(int index);`
    * `int baseEndOfLine(int index);`
    * `int baseEndOfLineAnyEOL(int index);`
    * `int baseStartOfLine(int index);`
    * `int baseStartOfLineAnyEOL(int index);`
    * `int baseColumnAtIndex(int index);`
    * `@NotNull Pair<Integer, Integer> baseLineColumnAtStart();`
    * `@NotNull Pair<Integer, Integer> baseLineColumnAtEnd();`
    * `int baseEndOfLine();`
    * `int baseEndOfLineAnyEOL();`
    * `int baseStartOfLine();`
    * `int baseStartOfLineAnyEOL();`
    * `@NotNull Range baseLineRangeAtStart();`
    * `@NotNull Range baseLineRangeAtEnd();`
    * `int baseColumnAtEnd();`
    * `int baseColumnAtStart();`
* Add: to `Node` convenience methods delegated to `getChars()`
  * `int getStartOffset()` to `getStartOffset()`
  * `int getEndOffset()` to `getEndOffset()`
  * `int getTextLength()` to `length()`
  * `BasedSequence getBaseSequence()` to `getBaseSequence()`
  * `Range getSourceRange()` to `etSourceRange()`
  * `BasedSequence baseSubSequence(int startIndex, int endIndex)` to
    `baseSubSequence(startIndex, endIndex)`
  * `BasedSequence baseSubSequence(int startIndex)` to `baseSubSequence(startIndex)`
  * `BasedSequence getEmptyPrefix()` to `getEmptyPrefix()`
  * `BasedSequence getEmptySuffix()` to `getEmptySuffix()`
  * `int getStartOfLine()` to `baseStartOfLine()`
  * `int getEndOfLine()` to `baseEndOfLine()`
  * `int startOfLine(int index)` to `baseStartOfLine(index)`
  * `int endOfLine(int index)` to `baseEndOfLine(index)`
  * `Pair<Integer, Integer> lineColumnAtIndex(int index)` to `baseLineColumnAtIndex(index)`
  * `Pair<Integer, Integer> lineColumnAtStart()` to `baseLineColumnAtStart()`
  * `Pair<Integer, Integer> getLineColumnAtEnd()` to `baseLineColumnAtEnd()`
* Add: `ArrayUtils` for searching arrays of `<T>` on value or predicate
  * `indexOf()` variations for forward search
  * `lastIndexOf()` variations for backward search
  * `firstOf()` variations for forward search returning matched element or `null`
  * `lastOf()` variations for backward search returning matched element or `null`
* Break: `RichCharSequence` all `trim`, `trimStart`, `trimEnd` `keepLength` argument is now
  `keep` with different meaning. Instead of min length of string to keep, it is min number of
  trimmable characters to keep, which is more useful but does break compatibility. Now it can be
  used to leave whitespace padding up to a maximum of `keep` characters.
* Fix: reformat tests for compound sections
* Deprecate: `Range.subSequence(CharSequence)` use `Range.basedSubSequence(CharSequence)`,
  `Range.richSubSequence(CharSequence)` or for plain sequences
  `Range.charSubSequence(CharSequence)`
* Fix: rename `Range.subSequence()` to `Range.basedSubSequence()`
* Fix: rename `Range.safeSubSequence()` to `Range.safeBasedSubSequence()` which limit the
  subsequence within 0 to length
* Add: `Range.richSubSequence()`
* Add: `Range.safeRichBasedSubSequence()` which limit the subsequence within 0 to length
* Add: `Range.charSubSequence()`
* Add: `Range.safeCharBasedSubSequence()` which limit the subsequence within 0 to length

## 0.59.40

* Break: remove example option parsing related `TestUtils` methods.
* Add: spec example language per section options and rendering in HTML.
* Add: `ExampleOption` to parse and provide information about
* Add: compound spec-example sections, to combine previous lower level headings via `splice(" -
  ")` for better organization of tests and to allow hierarchical structure view for spec files
  in Markdown Navigator
* Fix: remove all file url prefix for tests. Either provide the file url for the spec or default
  resolution will be used.

## 0.59.38

* Fix: remove unnecessary type param from `MutableDataHolder.remove(DataKeyBase<?>)`
* Fix: `Parser.HEADING_NO_EMPTY_HEADING_WITHOUT_SPACE` had wrong regex. Did not allow non-empty
  headings without space either.

## 0.59.36

* Fix: remove synchronization. The issue was with using `HashMap.computeIfAbsent()` and passing
  the factory, which could and would access and create other keys. This caused concurrent
  modification, not threading. Now computing value with factory then adding it to the dataSet.

## 0.59.34

* Add: synchronized around `HashMap` modification to ensure thread safety. Elimination of
  nullable keys would allow using `ConcurrentHashMap` in `DataSet` but access and modification
  to options is done at start of parse as extensions and core load their options, afterwards the
  data set is mostly not modified or accessed.

## 0.59.32

* Fix: add nullable annotations to `DataSet` and `DataKey` classes
* Break: `DataKey` now represents not null data values, use `NullableDataKey` if using nullable
  values, for processing of either type of data key, use `DataKeyBase` super class which does
  not specify nullability for the data value.
* Break: `DataValueFactory` now has non-nullable dataHolder argument and nullable result. Use:
  * `DataNotNullValueFactory` for nullable result and non nullable dataHolder
  * `DataValueNullableFactory` for nullable result and nullable dataHolder
  * `DataNotNullValueNullableFactory` for non-nullable result and non-nullable dataHolder
* Add: `DataKey` 3 argument constructor with pre-computed non null default value to be used for
  null dataHolder defaults. This way the factory is `DataNotNullValueFactory` which takes a
  non-null data holder and returns a computed value.
* Add: `NullableDataKey` 3 argument constructor with pre-computed nullable default value to be
  used for null dataHolder defaults. This way the factory is `DataValueFactory` which takes a
  non-null data holder and returns a computed value.
* Fix: add `DataKey.get(DataHolder)` to replace `DataKey.getFrom(DataHolder)`, shorter and
  compatible with Kotlin array access syntax, also handles nullability of data.
* Fix: deprecate `DataKey.getFrom()`
* Fix: replace all `DataHolder.get()` by `DataKey.get()`
* Fix: add nullability annotations to a boat load of classes.
* Add: `MutableDataHolder.set(@NotNull DataKey<T>, @NotNull T)` and
  `MutableDataHolder.set(@NotNull NullableDataKey<T>, @Nullable T)` to respect nullability of
  key's data value.
* Add: `DataKey.set(@NotNull MutableDataHolder, @NotNull T)` and `NullableDataKey.set(@NotNull
  MutableDataHolder, @Nullable T)` to allow setting data values via keys, if needed.

## 0.59.30

* Fix: change all test case construction param from `Map<String, DataHolder>` to `Map<String, ?
  extends DataHolder>` to allow for any type implementing the right map.

## 0.59.28

* Fix: pass through opening line of spec example to `DumpSpecReader` so it can construct an
  accurate full spec version of expected text.
* Fix: add `ResourceResolverManager` to handle `ResourceUrlResolver` registration.

## 0.59.27

* Add: parameterized test case options, ones that have option name followed by `[` and
  terminated by `]`. Used for custom handling variations of option. These should set
  `TestUtils.CUSTOM_OPTION` to a `BiFunction<String option, String text, DataHolder>` where
  `option` is the option name (`type` in example), `text` is the text between `[]`.

      optionsMap.put("type", new MutableDataSet().set(CUSTOM_OPTION, ActionSpecTestCase::typeOption));

  ```
    public static DataHolder typeOption(String option, String params) {
      // allow escape
      String text = params
              .replace("\\\\", "\\")
              .replace("\\]", "]")
              .replace("\\t", "\t")
              .replace("\\n", "\n")
              .replace("\\r", "\r")
              .replace("\\b", "\b");

      return new MutableDataSet().set(ACTION_NAME, TYPE_ACTION).set(TYPE_ACTION_TEXT, text);
  }
  ```
* Add: now if test option `DataHolder` with `TestUtils.CUSTOM_OPTION` set then it will be
  invoked even when no `[]` present to allow handling of empty params, which will be `null` in
  this case.
* Add: before checking for parameterized option the full option with params and `[]` is tried.
  If present then its results are used, but also checked if `TestUtils.CUSTOM_OPTION` is
  present.
* Add: `ResourceUrlResolver`, registered via
  `ResourceLocation.registerUrlResolver(Function<String, String>)` to allow resource URL
  conversion to URL used in example messages to be adjusted by project. Must register resolvers
  before trying to get an instance of `ResourceLocation` since resolution of file url is done in
  constructor.
* Add: example's URL in exceptions when processing undefined example option.
* Add: HTML comment handling to `SpecReader`, start/end comment tags must be the first non-blank
  on the line and outside of spec example.

## 0.59.25

* Fix: remove moved test class from core to utils module.

## 0.59.23

* Fix: change all tests to use static const `ResourceLocation` instead of creating a new
  instance.
* Fix: make `ComboSpecTestCase.getSpecResourceLocation()` final and remove it from all
  subclasses.

## 0.59.21

* Fix: move flexmark modules test classes out of `test.util` package which is for
  `flexmark-test-util` module's test classes
* Add: `DataSet.registerDataKeyAggregator(DataKeyAggregator)` to allow for aggregation action
  keys and aggregation for combining keys which require this. Only applied in
  * `DataSet.aggregate()`
  * `DataSet.aggregate(DataHolder, DataHolder)`
  * `DataSet.aggregateActions(DataHolder, DataHolder)`

## 0.59.19

* Fix: convert all resource info to `ResourceLocation`
* Add: class location resolution via file under test `resources` named for the root package of
  the module and containing directory path(s) from parent of `resources` directory to get source
  root for test files. for maven builds it is `java/`. This allows resolving absolute file path
  for class files in tests for generating the location `file:///`
  * Fix: package for some modules did not follow module/package naming convention
    * Fix: move `flexmark-ext-superscript` to proper package by naming convention
      `com.vladsch.flexmark.ext.superscript`
    * Fix: move `flexmark-profile-pegdown` to proper package by naming convention
      `com.vladsch.flexmark.profile.pegdown`
    * Fix: move `flexmark-test-util` `spec` package under `com.vladsch.flexmark.test.util`
      package
  * Add: test class location helpers to all modules:
    * `flexmark-core-test/src/test/resources/com.vladsch.flexmark.core.test.txt`
    * `flexmark-docx-converter/src/test/resources/com.vladsch.flexmark.docx.converter.txt`
    * `flexmark-ext-abbreviation/src/test/resources/com.vladsch.flexmark.ext.abbreviation.txt`
    * `flexmark-ext-admonition/src/test/resources/com.vladsch.flexmark.ext.admonition.txt`
    * `flexmark-ext-anchorlink/src/test/resources/com.vladsch.flexmark.ext.anchorlink.txt`
    * `flexmark-ext-aside/src/test/resources/com.vladsch.flexmark.ext.aside.txt`
    * `flexmark-ext-attributes/src/test/resources/com.vladsch.flexmark.ext.attributes.txt`
    * `flexmark-ext-autolink/src/test/resources/com.vladsch.flexmark.ext.autolink.txt`
    * `flexmark-ext-definition/src/test/resources/com.vladsch.flexmark.ext.definition.txt`
    * `flexmark-ext-emoji/src/test/resources/com.vladsch.flexmark.ext.emoji.txt`
    * `flexmark-ext-enumerated-reference/src/test/resources/com.vladsch.flexmark.ext.enumerated.reference.txt`
    * `flexmark-ext-escaped-character/src/test/resources/com.vladsch.flexmark.ext.escaped.character.txt`
    * `flexmark-ext-footnotes/src/test/resources/com.vladsch.flexmark.ext.footnotes.txt`
    * `flexmark-ext-gfm-issues/src/test/resources/com.vladsch.flexmark.ext.gfm.issues.txt`
    * `flexmark-ext-gfm-strikethrough/src/test/resources/com.vladsch.flexmark.ext.gfm.strikethrough.txt`
    * `flexmark-ext-gfm-tasklist/src/test/resources/com.vladsch.flexmark.ext.gfm.tasklist.txt`
    * `flexmark-ext-gfm-users/src/test/resources/com.vladsch.flexmark.ext.gfm.users.txt`
    * `flexmark-ext-gitlab/src/test/resources/com.vladsch.flexmark.ext.gitlab.txt`
    * `flexmark-ext-ins/src/test/resources/com.vladsch.flexmark.ext.ins.txt`
    * `flexmark-ext-jekyll-front-matter/src/test/resources/com.vladsch.flexmark.ext.jekyll.front.matter.txt`
    * `flexmark-ext-jekyll-tag/src/test/resources/com.vladsch.flexmark.ext.jekyll.tag.txt`
    * `flexmark-ext-macros/src/test/resources/com.vladsch.flexmark.ext.macros.txt`
    * `flexmark-ext-media-tags/src/test/resources/com.vladsch.flexmark.ext.media.tags.txt`
    * `flexmark-ext-spec-example/src/test/resources/com.vladsch.flexmark.ext.spec.example.txt`
    * `flexmark-ext-superscript/src/test/resources/com.vladsch.flexmark.ext.superscript.txt`
    * `flexmark-ext-tables/src/test/resources/com.vladsch.flexmark.ext.tables.txt`
    * `flexmark-ext-toc/src/test/resources/com.vladsch.flexmark.ext.toc.txt`
    * `flexmark-ext-typographic/src/test/resources/com.vladsch.flexmark.ext.typographic.txt`
    * `flexmark-ext-wikilink/src/test/resources/com.vladsch.flexmark.ext.wikilink.txt`
    * `flexmark-ext-xwiki-macros/src/test/resources/com.vladsch.flexmark.ext.xwiki.macros.txt`
    * `flexmark-ext-yaml-front-matter/src/test/resources/com.vladsch.flexmark.ext.yaml.front.matter.txt`
    * `flexmark-ext-youtube-embedded/src/test/resources/com.vladsch.flexmark.ext.youtube.embedded.txt`
    * `flexmark-ext-zzzzzz/src/test/resources/com.vladsch.flexmark.ext.zzzzzz.txt`
    * `flexmark-formatter-test-suite/src/test/resources/com.vladsch.flexmark.formatter.test.suite.txt`
    * `flexmark-html2md-converter/src/test/resources/com.vladsch.flexmark.html2md.converter.txt`
    * `flexmark-integration-test/src/test/resources/com.vladsch.flexmark.integration.test.txt`
    * `flexmark-java.wiki/src/test/resources/com.vladsch.flexmark-java.wiki.txt`
    * `flexmark-jira-converter/src/test/resources/com.vladsch.flexmark.jira.converter.txt`
    * `flexmark-pdf-converter/src/test/resources/com.vladsch.flexmark.pdf.converter.txt`
    * `flexmark-profile-pegdown/src/test/resources/com.vladsch.flexmark.profile.pegdown.txt`
    * `flexmark-tree-iteration/src/test/resources/com.vladsch.flexmark.tree.iteration.txt`
    * `flexmark-util/src/test/resources/com.vladsch.flexmark.util.txt`
    * `flexmark-youtrack-converter/src/test/resources/com.vladsch.flexmark.youtrack.converter.txt`
    * `flexmark/src/test/resources/com.vladsch.flexmark.txt`
* Add: creation of `SpecExample` instance based on caller information with correct file/line of
  the source calling `RenderingTestCase.assertRendering(String, String, String)` or its
  variants.
* Fix: convert typographic smarts inline parser to match strings instead of using regex which
  sometimes takes long to execute for no apparent reason.

## 0.59.17

* Fix: make `SpecExampleRendererBase` reusable by moving any references to flexmark parsing
  rendering specifics to `FlexmarkSpecExampleRenderer` and moving up not related to flexmark
  parsing, like caching of generated HTML and AST.
* Fix: consolidate full spec and individual example test case in to one method.

## 0.59.15

* Fix: delete deprecated methods, classes, fields.
* Fix: delete deprecated flexmark-ext-gfm-tables
* Fix: format code

## 0.59.13

* Break: move formatter tests and html renderer to `flexmark-core-test` module to allow sharing
  of test base classes in extensions without causing dependency cycles in formatter module.
* Break: move formatter module into `flexmark` core. this module is almost always included
  anyway because most extension have a dependency on formatter for their custom formatting
  implementations. Having it as part of the core allows relying on its functionality in all
  modules.
* Break: move `com.vladsch.flexmark.spec` and `com.vladsch.flexmark.util` in
  `flexmark-test-util` to `com.vladsch.flexmark.test.spec` and `com.vladsch.flexmark.test.util`
  respectively to respect the naming convention between modules and their packages.
* Break: remove `flexmark-html-parser`, `flexmark-html2md-converter` is a replacement
* Fix: revert back to `MutableDataSet.set(DataKey<T>, T)` to enforce compile time type checking
  for typed class values
* Fix: clean up all tests to eliminate duplication and unnecessary interface methods.

  |    Metric     |  Before |   After |
  |---------------|--------:|--------:|
  | Files         |    1264 |    1297 |
  | Lines         | 124,192 | 118,755 |
  | Source Lines  |  96,669 |  92,145 |
  | Comment Lines |   9,878 |   9,597 |
  | Blank Lines   |  17,646 |  17,013 |

## 0.59.11

* Fix: remove need to load/unload extensions. Properly handle removal from `EXTENSIONS` property
  for those tests that need it by implementing `LOAD_EXTENSIONS` property and
  `UNLOAD_EXTENSIONS` property handling in `RenderingTestCase.combineOptions(DataHolder,
  DataHolder)`.
* Fix: remove loading/unloading and other test support code from builder, no longer needed,
  including `withOptions()` and builder construction from another builder.
* Fix: change `EXTENSIONS` from `Iterable` to `Collection`, which should not affect most code
  and easier to work with.
* Fix: translation mode formatting would loose indented code blocks in items if these blocks
  were indented more than the minimum indent for the item.
* Fix: paragraphs in containers that have non-blank termination marker would be erroneously
  marked as isTrailingBlankLine when they were terminated because the container was closed not
  because of a blank line.
* Fix: First paragraph of `AdmonitionBlock` would always format with leading blank line. Now it
  formats as it was in original markdown to allow preserving format as is when it is not
  significant or confusing.

## 0.59.9

* Fix: `NO_EOL` option for tests was applied inconsistently.
* Fix: add `SpecExample` argument to `getSpecExampleRenderer`
* Fix: add `SpecExampleParse` argument to `addSpecExample`
* Fix: regression bug #372
  [#372, \[Regression?\] Attributes extension not applied to \`code\` tag of code blocks]
  * Add: `AttributesExtension.FENCED_CODE_ADD_ATTRIBUTES`, default
    `FencedCodeAddType.ADD_TO_PRE_CODE` for backward compatibility with 0.42, but if this is
    option is not set and `AttributesExtension.FENCED_CODE_INFO_ATTRIBUTES` is set to `true`,
    default will change to `FencedCodeAddType.ADD_TO_PRE` since attributes after info are used
    to add to the `code` tag.

## 0.59.7

* Add: `SpecExampleRenderer.finalizeRender()` to allow tests and others to clean up after each
  rendering
* Add: ability to combine data sets with `DataKey<Consumer<?>>` keys in a custom way. Needed if
  some option values do not contain a single value but multiple values that are set with a
  consumer changing some values in a structure. In such cases overwriting of these values may
  not be correct and the consumers need to invoked so the results of second consumer get to
  apply changes to options.
* Fix: add dual argument constructor to `MutableDataSet`

## 0.59.5

* Fix: change `AttributesExtension.FENCED_CODE_INFO_ATTRIBUTES` to default `false` to match
  previous versions.

## 0.59.1

* Fix: MarkdownTable index out of bounds in some functions when table has no rows
* Fix: `DataSet` copy constructors to accept `null`
* Fix: change `DataSet.keySet()` to `DataSet.getKeys()`
  * Deprecate: `DataSet.keySet()`
* Add: `BasedSequence.extendByAny(CharSequence charSet, int maxCount)` to extend the based
  sequence to include any following contiguous characters from the underlying based sequence
  that are in `charSet`. Variations include `BasedSequence.extendByAny(CharSequence)` for
  unlimited count and `BasedSequence.extendByOneOfAny(CharSequence)` for max count of 1.
* Add: `BasedSequence.extendToAny(CharSequence charSet, int maxCount)` to extend the based
  sequence to include first of any following characters from the underlying based sequence that
  are in `charSet`. Variations include `BasedSequence.extendToAny(CharSequence)` for unlimited
  count.
* Add: `BasedSequence.prefixWithIndent(int maxColumns)` to extend the based sequence to include
  leading indent from the underlying based sequence up to a maximum of given columns. Tabs are
  taken into account as set to 4 space columns. Variations include
  `BasedSequence.prefixWithIndent()` for unlimited indent.
* Break: make `NodeAdaptedVisitor.myCustomHandlersMap` private. Use
  `NodeAdaptedVisitor.getHandler(Node)`, `NodeAdaptedVisitor.getHandler(Class<?>)`, and
  `NodeAdaptedVisitor.getNodeClasses()` to get access to contained data.

## 0.50.50

* Fix: change docx converter to not include `<>` around auto and mail links in generated code.
* Fix: [#384, Markdown parser produces invalid HTML]

## 0.50.48

* Fix: [#382, Is there an option for number of whitespaces needed to create sub-lists?]
  * `FIXED_INDENT` list parser did not convert list item looking text with >= 4 spaces to lazy
    continuation.

## 0.50.46

* Fix:
  [#381, StackOverflowError with long base64 image and LINKS\_ALLOW\_MATCHED\_PARENTHESES disabled],
  `USE_HARDCODED_LINK_ADDRESS_PARSER` was not used if matched parentheses were disabled causing
  stack overflow in regex evaluation.
* Fix: docx conversion renders mail link text with `mailto:` prefix

## 0.50.44

* Fix: [#376, convert markdown to html], delimiters are not allowed to span table cells.

## 0.50.42

* Fix: regression bug
  [#372, \[Regression?\] Attributes extension not applied to \`code\` tag of code blocks]
  * Add: `AttributesExtension.FENCED_CODE_ADD_ATTRIBUTES`, default
    `FencedCodeAddType.ADD_TO_PRE_CODE` for backward compatibility with 0.42, but if this is
    option is not set and `AttributesExtension.FENCED_CODE_INFO_ATTRIBUTES` is set to `true`,
    default will change to `FencedCodeAddType.ADD_TO_PRE` since attributes after info are used
    to add to the `code` tag.
* Fix: data set copy constructors to accept `null`
* Fix: change `DataSet.keySet()` to `DataSet.getKeys()`
  * Deprecate: `DataSet.keySet()`
* Add: `BasedSequence.extendByAny(CharSequence charSet, int maxCount)` to extend the based
  sequence to include any following contiguous characters from the underlying based sequence
  that are in `charSet`. Variations include `BasedSequence.extendByAny(CharSequence)` for
  unlimited count and `BasedSequence.extendByOneOfAny(CharSequence)` for max count of 1.
* Add: `BasedSequence.extendToAny(CharSequence charSet, int maxCount)` to extend the based
  sequence to include first of any following characters from the underlying based sequence that
  are in `charSet`. Variations include `BasedSequence.extendToAny(CharSequence)` for unlimited
  count.
* Add: `BasedSequence.prefixWithIndent(int maxColumns)` to extend the based sequence to include
  leading indent from the underlying based sequence up to a maximum of given columns. Tabs are
  taken into account as set to 4 space columns. Variations include
  `BasedSequence.prefixWithIndent()` for unlimited indent.
* Break: make `NodeAdaptedVisitor.myCustomHandlersMap` private. Use
  `NodeAdaptedVisitor.getHandler(Node)`, `NodeAdaptedVisitor.getHandler(Class<?>)`, and
  `NodeAdaptedVisitor.getNodeClasses()` to get access to contained data.

## 0.50.40

* Add: `BasedSequence.emptyPrefix()` and `BasedSequence.emptySuffix()` to return empty sequence
  at start or end of the current sequence. Useful for `PrefixedSubSequence.of(String,
  BasedSequence, int, int)` variants without needing to extract the subsequence manually.
* Fix: factor out link URL parser to separate class with own tests
* Fix: link URL parser not handling char escaping, nested parenthesis tracking and other edge
  cases in jekyll URL macro parsing.

## 0.50.38

* Fix: real fix for Stack Overflow when parsing long URLs, by hand rolling parsing of link
  destination.
  * Add: `Parser.USE_HARDCODED_LINK_ADDRESS_PARSER`, default `true`, setting to `false` will use
    regex parsing for link destination address. May cause `StackOverflowError` exception on long
    input but does allow customizing the regex if needed.
* Add: `BasedSequence.safeCharAt(int)` will return `\0` if index out of range for the sequence.
* Add: `BasedSequence.safeBaseCharAt(int)` will return character from the base sequence if index
  out of range for sequence or `\0` if out of base sequence range.

## 0.50.36

* Fix: `SegmentedSequence` would return sequence with start > end if ending with
  `PrefixedSubSequence` whose position was > end of real sequences before.
* Add: `BitIntegerSet.toArray(int[], int)` for easy `int[]` creation from bit set.
* Add: `BitIntegerSet.addAll(int[], int, int)` for setting from `int[]`

## 0.50.34

* Add: source `Node` to `com.vladsch.flexmark.util.format.TableCell`
* Fix: Stack overflow when parsing large embedded images with space in URL enabled. Now link
  urls starting with `data:image/png;base64,` do not allow spaces in the link.

## 0.50.32

* Add: `DocxRenderer` image max-width tests
* Add: `DocxRenderer` image max-height attribute processing and tests
* Fix: refactor `DocxRenderer` tests to extract common code to base class
* Fix: heading rendering to work with styles using numbering

## 0.50.30

* Fix: `DocxRenderer`
  * Add: page break via empty paragraph with only `{.pagebreak}` attributes
  * Add: tab via `{.tab}` attributes
  * Add: inline image alignment with `{align=}`:
    * `left` - left align, wrap text to right
    * `right` - right align, wrap text to left
    * `center` - center align, wrap text to left and right
    * else no wrapping around image
  * Add: handling of `font-size` attribute, expects float of font size in pt, rounds to nearest
    1/2 pt
  * Add: image size attribute handling for `in` and `cm` for inch and cm dimensions. Can be
    given in:
    * `%` for percent of page width
    * `pt` points
    * `cm` cm
    * `in` inches
    * if no units given then pixels are assumed.
  * Fix: if image size only given for one dimension, compute the other preserving aspect ratio
  * Fix: change default emoji size to 0.9 of line height
  * Fix: remove `<>` wrapper from URL target
  * Fix: lists to set style id to `DocxRenderer.PARAGRAPH_BULLET_LIST_STYLE` for unordered lists
    and `DocxRenderer.PARAGRAPH_NUMBERED_LIST_STYLE` for ordered lists, while still using
    `DocxRenderer.BULLET_LIST_STYLE` and `DocxRenderer.NUMBERED_LIST_STYLE` for the numbering
    style. Tight/loose is now uses paragraph settings from `DocxRenderer.TIGHT_PARAGRAPH_STYLE`
    and `DocxRenderer.LOOSE_PARAGRAPH_STYLE` respectively as mods on list style.
  * Fix: start image ids at 100000 to avoid id conflicts with images already in the document
    being added. For example in footers/headers.
  * Fix: for R tags, only add space="preserve" if contained text starts or ends with a space.
    Does not affect result but reduces xml noise.
* Fix: `Formatter` translation and merge rendering

## 0.50.28

* Fix: [#362, ArrayIndexOutOfBoundsException in BasedSequence.indexOfAll]
* Add: `DocxRenderer.BULLET_LIST_STYLE` default `"BulletList"`, numbering style to use for
  bullet list item paragraph.
* Add: `DocxRenderer.NUMBERED_LIST_STYLE` default `"NumberedList"`, numbering style to use for
  numbered list item paragraph.
* Add: Simple `Attributes` processing to docx conversion:
  * `.className` on paragraph elements will set the docx styleId to `className` if the style id
    is found.
  * Use `{style=""}` to set attributes on text or block elements. Only the following are
    processed:
    * `color` - text color
    * `background-color` - shade fill color, pattern always solid.
    * `font-family` - not implemented
    * `font-size` - not implemented
    * `font-weight` - set/clear bold (if using numeric weights then >= 550 sets bold, less
      clears it)
    * `font-style` - set/clear italic
* Add: merge multiple markdown documents into single document functionality. Includes making
  conflicting references between documents unique by adding an integer numeric suffix.
  * Conflicting headings will get a unique explicit id attribute by the
    `flexmark-ext-attributes` extension.
  * Added to `Formatter` module using modified translation API functionality.
    * `Formatter.mergeRender(Document[], Appendable, HtmlIdGeneratorFactory)`
    * `Formatter.mergeRender(Document[], Appendable, int, HtmlIdGeneratorFactory)`
    * `Formatter.mergeRender(Document[], int, HtmlIdGeneratorFactory)`
  * Add: merge tests and functionality to:
    * `flexmark-formatter` core rendering of anchors and headings.
    * `flexmark-ext-abbreviation`, by default making references unique is disabled. Enable by
      setting `AbbreviationExtension.MAKE_MERGED_ABBREVIATIONS_UNIQUE` to `true` for the
      formatter options used to merge documents.
    * `flexmark-ext-attributes`
    * `flexmark-ext-enumerated-reference`
    * `flexmark-ext-footnotes`
    * `flexmark-ext-macros`
  * Add: link mapping based on document's `Formatter.DOC_RELATIVE_URL` and
    `Formatter.DOC_ROOT_URL` properties.
* Fix: `Formatter` attributes would eliminate separator space before or after following text
  possibly changing the applicable node for the attribute.

## 0.50.26

* Fix: docx converter to use `BulletList` and `NumberedList` numbering list styles for list
  conversions to allow easy list item styling for multi-level lists
  * Add: `DocxConverterEmpty` to samples for generating `flexmark-empty-template.docx` from
    `empty.md` and `empty.xml`

## 0.50.24

* Fix: update docx4j to version 8.1.2
* Fix: update jsoup to version 1.11.3

## 0.50.22

* Fix: Attributes with spaces after `{` generated wrong previous text offsets

## 0.50.20

* Fix: [#357, HTML to markdown and removed nested list]
* Remove: `FlexmarkHtmlConverter.EXT_TABLES` unused conversion option.
* Add: allow attributes after fenced code info string as last non-blank text after the info
  string.
  * Add: `AttributesExtension.FENCED_CODE_INFO_ATTRIBUTES` default `true`, if false will not
    parse attributes after the fenced code info string

## 0.50.18

* Add: `flexmark-html2md-converter` module which implements HTML to Markdown conversion with an
  extension API to allow customizing the conversion process. Sample:
  [HtmlToMarkdownCustomizedSample.java]
  * Fix: [#313, Ability to override tags processing in FlexmarkHtmlParser]
* Fix: deprecate the old `flexmark-html-parser` classes

## 0.50.16

* Fix: GitLab block quotes should have `>>>` as termination, not `<<<`, macros are still
  terminated by `>>>` to allow nested block quotes in macros. Affects:
  * HTML converter
  * Formatter
* Fix: [#351, Is there any special format requirement for processing html data to markdown]
* Fix: [#349, Translation Helper bugs], mix-up between anchors and other non-translating
  elements after translation.
* Fix: [#349, Translation Helper bugs]
* Fix: [#348, WRAP_AUTO_LINKS defaults to false, Markdown loses a potential useful link]
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

## 0.42.14

* Fix: HTML parser converts `a` tags in preformatted text to links, should convert to URL only
* Fix: HTML deep parser to interrupt paragraph if HTML starts with a block tag
* Fix: Docx converter mail link conversion
* Fix: [#351, Is there any special format requirement for processing html data to markdown]

## 0.42.12

* Fix: [#349, Translation Helper bugs], mix-up between anchors and other non-translating
  elements after translation.

## 0.42.10

* Fix: [#349, Translation Helper bugs]
* Fix: [#348, WRAP_AUTO_LINKS defaults to false, Markdown loses a potential useful link]

## 0.42.8

* Add: PDF converter landscape sample [PdfLandscapeConverter.java]
* Fix: revert to OpenHtmlToPDF version 0.0.1-RC15 which is the last Java 7 byte code version
* Fix: fill missing columns StringIndexOutOfBoundsException when prev cell consists of
  consecutive pipes without text
* Fix: add explicit `Locale.US` to `String.format()` when using `%d` for integers to prevent
  conversion to arabic on Java 11.

## 0.42.6

* Fix: [#338, getLineNumber incorrect with Windows end of line separators]
  * merge: [#339, PR: Fix to line number when using Windows EOL characters.] thanks to

## 0.42.4

* Fix: Trailing URI prefix only auto-links do not get parsed

## 0.42.2

* Fix: [#334, CR line separators don't produce line break nodes],
  * merge: [#335, PR: Fix CR-only line separator handling] thanks to **[Kijimuna]**

## 0.42.0

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

## 0.40.34

* Fix: [#328, Html2mark - missing newline when paragraph followed by div]
* Fix: [#331, Ability to replace empty \<p\> with \<br\> during html2mark conversion]
* Fix: NPE in `TableParagraphPreProcessor`
* Fix: `AutolinkNodePostProcessor` processing links out of order causing sequence end/start
  reversal.

## 0.40.32

* Fix: compound enumerated references in attributes ids and outside of headings to output with
  last enumerated reference ordinal.

## 0.40.30

* Fix: limit `EnumeratedReferenceBlock` to single line of text without processing other block
  elements.
* Fix: change `Abbreviations` to custom block parser from paragraph pre-processor. Allows
  abbreviation definitions without preceding blank line.

## 0.40.28

* Add: enumerated reference text in heading to be used with only a format reference, the id is
  taken from the heading attributes.
* Add: compound enumerated references for creating legal numbering for enumerated references.

## 0.40.26

* Fix: upgrade dependencies
  * OpenHtmlToPdf -> 0.0.1-RC19
  * docx4j -> 6.1.2
* Add: parse int or default to `Utils.java`

## 0.40.24

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

## 0.40.22

* Fix: merge util tests from [@James-Adam](https://github.com/James-Adam) and fix bugs
* Fix: change to `MutableDataSet.set(DataKey<? extends T>, T)`

## 0.40.20

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

## 0.40.18

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

## 0.40.16

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

## 0.40.14

* Fix: `AutolinkExtension` removing leading `Typographic` nodes when the first link occurs in
  text following the typographic node.
* Add: PDF converter sample with non-latin character set rendering information.
* Fix: missing `simple_smile` emoji cheat sheet shortcut

## 0.40.12

* Fix: [#300, Typography extension breaks some auto links]
* Add: `TypographicText` interface to mark nodes which hold text which is replaced with
  typographic for rendering but treated as text for decoration processing. For now
  `AutolinkExtension` is the only one making use of it to prevent typographic smarts from
  breaking up a link and causing part of it to be left out of the URL.

## 0.40.10

* Fix: GitLab inline math parser to allow multi-line inline math elements
* Fix: [#295, CoreNodeFormatter does not descend into children on Link nodes] , more like
  kludge, `Formatter.OPTIMIZED_INLINE_RENDERING` when `false` and not translating to always
  render children of link text.

## 0.40.8

* Fix: change `AttributeProviderFactory` extensions to eliminate duplicate registrations of
  factories.
* Fix: `AttributesExtension` to assign attributes to explicit/refs links/images
* Fix: [#299, FlexmarkHtmlParser produces extra empty list item for eclosing \</p\> element]

## 0.40.6

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

## 0.40.4

* Fix: [#295, CoreNodeFormatter does not descend into children on Link nodes]
  * `Link`, `LinkRef`, `Image` and `ImageRef` node formatter renderer now descends into child
    nodes during all formatting, not just translation formatting.
* Add: `Formatter.OPTIMIZED_INLINE_RENDERING` default `false`. When set to `true` will use
  previous rendering for links and images which appends the node characters without descending
  into child nodes.

## 0.40.2

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

## 0.40.0

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
[#348, WRAP_AUTO_LINKS defaults to false, Markdown loses a potential useful link]: https://github.com/vsch/flexmark-java/issues/348
[#349, Translation Helper bugs]: https://github.com/vsch/flexmark-java/issues/349
[#351, Is there any special format requirement for processing html data to markdown]: https://github.com/vsch/flexmark-java/issues/351
[#357, HTML to markdown and removed nested list]: https://github.com/vsch/flexmark-java/issues/357
[#362, ArrayIndexOutOfBoundsException in BasedSequence.indexOfAll]: https://github.com/vsch/flexmark-java/issues/362
[#372, \[Regression?\] Attributes extension not applied to \`code\` tag of code blocks]: https://github.com/vsch/flexmark-java/issues/372
[#376, convert markdown to html]: https://github.com/vsch/flexmark-java/issues/376
[#381, StackOverflowError with long base64 image and LINKS\_ALLOW\_MATCHED\_PARENTHESES disabled]: https://github.com/vsch/flexmark-java/issues/381
[#382, Is there an option for number of whitespaces needed to create sub-lists?]: https://github.com/vsch/flexmark-java/issues/382
[#384, Markdown parser produces invalid HTML]: https://github.com/vsch/flexmark-java/issues/384
[Awesome Console]: https://plugins.jetbrains.com/plugin/7677-awesome-console "Awesome Console"
[HtmlToMarkdownCustomizedSample.java]: https://github.com/vsch/flexmark-java/blob/master/flexmark-java-samples/src/com/vladsch/flexmark/java/samples/HtmlToMarkdownCustomizedSample.java
[Kijimuna]: https://github.com/Kijimuna
[NodeInsertingPostProcessorSample.java]: https://github.com/vsch/flexmark-java/blob/master/flexmark-java-samples/src/com/vladsch/flexmark/java/samples/NodeInsertingPostProcessorSample.java
[PdfLandscapeConverter.java]: https://github.com/vsch/flexmark-java/blob/master/flexmark-java-samples/src/com/vladsch/flexmark/java/samples/PdfLandscapeConverter.java
[YouTrack: IDEA-207453]: https://youtrack.jetbrains.com/issue/IDEA-207453 "Add Conversion of ref anchor to UrlFilter for file line navigation"
[migrate 0_35_x to 0_40_0.xml]: /assets/migrations/migrate%20flexmark-java%200_35_x%20to%200_40_0.xml
[migrate flexmark-java 0_40_x to 0_42_0]: https://github.com/vsch/flexmark-java/blob/master/assets/migrations/migrate%20flexmark-java%200_40_x%20to%200_42_0.xml
[migrate flexmark-java 0_42_x to 0_50_0.xml]: https://github.com/vsch/flexmark-java/blob/master/assets/migrations/migrate%20flexmark-java%200_42_x%20to%200_50_0.xml


[#391, PR: Fix: CRLF line separator in fenced code blocks produce redundant CR.]: https://github.com/vsch/flexmark-java/pull/391
[#387, JUnit is in the compile scope]: https://github.com/vsch/flexmark-java/pull/387
[#396, DocumentParser stops reading too early resulting in the document being cut off]: https://github.com/vsch/flexmark-java/issues/396

[@Xaelis]: https://github.com/Xaelis
[#397, PR: Add base64 image support with docx rendering]: https://github.com/vsch/flexmark-java/pull/397
