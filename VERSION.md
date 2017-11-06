flexmark-java
=============

&nbsp;<details id="version-history"><summary>**Version History**</summary>

[TOC]: # " "

- [To Do](#to-do)
    - [Docx Converter](#docx-converter)
- [0.28.4](#0284)
- [0.28.2](#0282)
- [0.28.0](#0280)
- [0.27.4](#0274)
- [0.27.2](#0272)
- [0.27.0](#0270)
- [0.26.6](#0266)
- [0.26.4](#0264)
- [0.26.2](#0262)
- [0.26.0](#0260)
- [0.24.0](#0240)
- [0.22.24](#02224)
- [0.22.22](#02222)
- [0.22.20](#02220)
- [0.22.18](#02218)
- [0.22.16](#02216)
- [0.22.14](#02214)
- [0.22.12](#02212)
- [0.22.10](#02210)
- [0.22.8](#0228)
- [0.22.6](#0226)
- [0.22.4](#0224)
- [0.22.2](#0222)
- [0.22.0](#0220)
- [0.21.0](#0210)
- [0.20.2](#0202)
- [0.20.0](#0200)
- [0.19.8](#0198)
- [0.19.6](#0196)
- [0.19.5](#0195)
- [0.19.4](#0194)
- [0.19.3](#0193)
- [0.19.2](#0192)
- [0.19.1](#0191)
- [0.19.0](#0190)
- [0.18.9](#0189)
- [0.18.8](#0188)
- [0.18.7](#0187)
- [0.18.6](#0186)
- [0.18.5](#0185)
- [0.18.4](#0184)
- [0.18.3](#0183)
- [0.18.2](#0182)
- [0.18.1](#0181)
- [0.18.0](#0180)
- [0.17.4](#0174)
- [0.17.3](#0173)
- [0.17.2](#0172)
- [0.17.1](#0171)
- [0.17.0](#0170)
- [0.16.1](#0161)
- [0.16.0](#0160)
- [0.15.4](#0154)
- [0.15.3](#0153)
- [0.15.2](#0152)
- [0.15.1](#0151)
- [0.15.0](#0150)
- [0.14.0](#0140)
- [0.13.7](#0137)
- [0.13.6](#0136)
- [0.13.5](#0135)
- [0.13.4](#0134)
- [0.13.3](#0133)
- [0.13.2](#0132)
- [0.13.1](#0131)
- [0.13.0](#0130)
- [0.12.3](#0123)
- [0.12.2](#0122)
- [0.12.1](#0121)
- [0.12.0](#0120)
- [0.11.10](#01110)
- [0.11.9](#0119)
- [0.11.8](#0118)
- [0.11.7](#0117)
- [0.11.6](#0116)
- [0.11.5](#0115)
- [0.11.4](#0114)
- [0.11.3](#0113)
- [0.11.2](#0112)
- [0.11.1](#0111)
- [0.11.0](#0110)
- [0.10.3](#0103)
- [0.10.2](#0102)
- [0.10.1](#0101)
- [0.10.0](#0100)
- [0.9.4](#094)
- [0.9.3](#093)
- [0.9.2](#092)
- [0.9.1](#091)
- [0.9.0](#090)
- [0.8.0](#080)
- [0.7.0](#070)
- [0.6.1](#061)
- [0.6.0](#060)
- [0.5.0](#050)
- [0.4.17](#0417)
- [0.4.16](#0416)
- [0.4.15](#0415)
- [0.4.14](#0414)
- [0.4.13](#0413)
- [0.4.12](#0412)
- [0.4.11](#0411)
- [0.4.10](#0410)
- [0.4.9](#049)
- [0.4.8](#048)
- [0.4.7](#047)
- [0.4.6](#046)
- [0.4.5](#045)
- [0.4.4](#044)
- [0.4.3](#043)
- [0.4.2](#042)
- [0.4.1](#041)
- [0.4.0](#040)
- [0.3.2](#032)
- [0.3.1](#031)
- [0.3.0](#030)
- [0.2.9](#029)
- [0.2.8](#028)
- [0.2.7](#027)
- [0.2.6](#026)
- [0.2.5](#025)
- [0.2.4](#024)
- [0.2.3](#023)
- [0.2.2](#022)
- [0.2.1](#021)
- [0.2.0](#020)
- [0.1.9](#019)
- [0.1.8](#018)
- [0.1.7](#017)
- [0.1.6](#016)
- [0.1.5](#015)
- [0.1.4](#014)
- [0.1.3](#013)
- [0.1.2](#012)
- [0.1.1](#011)
- [0.1.0](#010)


&nbsp;</details><details id="version-history"><summary>**To Do**</summary>

## To Do

* [ ] Change: Extensions wiki from table format for options to list, easier to maintain and read
      when descriptions can benefit form complex formatting

* [ ] Fix: clean up and verify the Extensions wiki options lists for name changes, missing or
      extra entries. Update description for better understanding

* [ ] Add: generated HTML element positions to `TagRanges` to allow mapping from source offset
      to HTML offset for the element(s). This is needed to allow synchronization with source
      when using an attribute to hold the source information is not an option.

* [ ] Add: Latex extension

* Change: complete parser profiles for variations within a family
  [Markdown Parser Emulation](https://github.com/vsch/flexmark-java/blob/master/MarkdownProcessorsEmulation.md).
  * [ ] League/CommonMark
  * [ ] Jekyll
  * [ ] Php Markdown Extra
  * CommonMark (default for family): `ParserEmulationProfile.COMMONMARK`
  * FixedIndent (default for family): `ParserEmulationProfile.FIXED_INDENT`
  * GitHub Comments (just CommonMark): `ParserEmulationProfile.COMMONMARK`
  * GitHub Docs: (Now CommonMark): `ParserEmulationProfile.COMMONMARK`, old parsing was
    `ParserEmulationProfile.GITHUB_DOC`
  * Kramdown (default for family): `ParserEmulationProfile.KRAMDOWN`
  * Markdown.pl (default for family): `ParserEmulationProfile.MARKDOWN`
  * MultiMarkdown: `ParserEmulationProfile.MULTI_MARKDOWN`
  * Pegdown, with pegdown extensions use `PegdownOptionsAdapter` in `flexmark-profile-pegdown`
  * Pegdown, without pegdown extensions `ParserEmulationProfile.PEGDOWN`

* [ ] Add hierarchical anchor id generation for headings: will splice parent heading id with
      child id to create unique ids based on header hierarchy.

* [ ] Add attribute extension parser module to allow adding arbitrary attributes to any block or
      inline element in markdown
  * [ ] use these attributes to control docx rendering and html rendering.

* [ ] Add macro module that would allow inserting arbitrary markdown into table cells. This
      would be less readable but would give complete flexibility to generate tables with complex
      content without changing the table syntax because the macro expansion would be done during
      rendering not parsing.

### Docx Converter

* [ ] Add: `DocxRenderer` options:
  * [ ] `TABLE_WIDTH_TYPE` and `TABLE_WIDTH_VALUE` for table width control

* [ ] Add: Base64 image embedding in `HtmlRenderer` as an option with images processed by a
      handler.

* [ ] Add: finish missing elements from docx converter
  * [x] Tables
    * [ ] Footnotes
    * [ ] Table of Contents
    * [x] multiple header rows, column spans, markdown alignments
    * [x] Table captions are not yet supported by docx4j API. Captions are converted to
          "TableCaption" styled paragraphs if `DocxRenderer.TABLE_CAPTION_TO_PARAGRAPH` is set
          to `true`, which it is by default. `DocxRenderer.TABLE_CAPTION_BEFORE_TABLE`, default
          `false`, when set to `true` the caption is inserted before the table.
    * [x] Ordered and unordered lists
      * [x] restart numbering on a short to do list
    * [x] Links
    * [x] Images
      * [x] Images get embedded into the document, even remote ones (using http: or https:
            links)
      * [x] Max Image width property available in options
    * [x] Inline Code
    * [x] Fenced Code
    * [x] Block Quotes
    * [x] Strike-through, Subscript, Superscript, Underline (Ins) text
  * [x] Paragraph
  * [x] Headings
  * [x] Bold and italic text

0.28.4
------

* [ ] Fix: when converting link text from HTML should escape special characters `[`, `]` and any
      others that would be interpreted as markdown inlines.

* Implement DOCX conversion for missing elements:
    * [ ] Footnotes
    * [ ] Table of Contents

* [ ] Fix: #99, YamlFrontMatterBlockParser ignores multi-key list items

&nbsp;</details>

0.28.2
------

* Fix: #169, HTML to Markdown Converter skips every second definition if there is no line break
  between definitions

* Fix: #172, Markdown parser cutting words out. Definition items used index instead of column
  based API for skipping leading spaces/tabs. If tabs were used then offset was wrong causing
  characters to be skipped.

* Add: `PegdownOptionsAdapter.PEGDOWN_EXTENSIONS` data key which reflect the extensions used to
  generate the options. This allows custom extensions to have access to pegdown based flags for
  their logic.

0.28.0
------

* API Change: before/after string parameters added to delimiter canBeOpener/canBeCloser to allow
  context based delimiter processing.

* API Change: add `DelimiterProcessor.skipNonOpenerCloser()`, for old behavior return false.
  When true then a delimiter occurrence is not an opener or closer will be skipped.

* Fix: #168, Text with colons is incorrectly interpreted as an invalid emoji shortcut. Now
      invalid emoji shortcuts only allow characters which can make up a valid shortcut:
      `[a-z0-9_+-]` and if the `:` is not preceded or followed by a digit. The latter eliminates
      time strings as erroneously interpreted as an invalid emoji shortcut.

* Fix: #163, BasedSequenceImpl.prefix seems broken. Unable to handle zero length string as
  other. Add tests for this function

* Fix: `BasedSequence.suffixOf` and add tests for this function

* Fix: #162, BasedSequenceImpl.replace is broken. Add tests for this function

0.27.4
------

* Fix: #149, StringIndexOutOfBoundsException when parsing "<strong>\t</strong>"

* Fix: `flexmark-html-parser` to generate markdown that will render closer to the HTML being
  converted.
  * now will allow 1 trailing blank line when using default parse methods.
  * `FlexmarkHtmlParser.DIV_AS_PARAGRAPH` default `false`, when true will treat `<div>` wrapped
    text as if it was `<p>` wrapped by adding a blank line after the text.
  * `FlexmarkHtmlParser.BR_AS_PARA_BREAKS` default `true`, when true `<br>` encountered at the
    beginning of a new line will be treated as a paragraph break
  * `FlexmarkHtmlParser.BR_AS_EXTRA_BLANK_LINES` default `true`, when true `<br>` encountered
    after a blank line is already in output or current output ends in `<br>` then will insert an
    inline HTML `<br>` into output to create extra blank lines in rendered result.
  * now does not force spaces around subscript and superscript markers

* Fix: `Ins` and `Superscript` nodes were marked `DoNotDecorate` which prevented abbreviation
  processing on text contained within them.

* Add: `Parser.HTML_BLOCK_START_ONLY_ON_BLOCK_TAGS`, default for deep html parser is `true` and
  regular parser `false`, but you can set your desired specific value and override the default.
  When `true` will not start an HTML block on a non-block tag, when `false` any tag will start
  an HTML block.

0.27.2
------

* Fix: #158, HTML comments followed by plain text parse as HTML comment blocks. Add
  `Parser.HTML_BLOCK_COMMENT_ONLY_FULL_LINE` default `false`, when true html comments parse as
  html block only when they are contained in one line and have no other text following them.

* Fix: #149, StringIndexOutOfBoundsException when parsing "<strong>\t</strong>"

0.27.0
------

* Fix: superscript is now allowed without regard for surrounding characters. The markers are
  rare enough in text not to warrant concern. If you don't want it treated as special, escape
  it.

* Add: default link resolvers to handle `URL` relative paths and file system relative paths

* Fix: add `<strike>` to HTML Parser as equivalent to `<del>` for OfficeLibre compatibility

* Fix: HTML to Markdown conversion should treat text wrapped in `<div>` as having an EOL at the
  end of the div to keep the text on separate lines.

* API Change: `NodeRendererContext` parameter for `LinkResolver` and `LinkResolverFactory` was
  changed to `LinkResolverContext` to allow for non-html renderer usage of link resolvers (DocX
  Converter). Methods that did not relate to HTMLRendering are now part of the link resolver
  context. The rest are not accessible. If you have an issue with the change please open an
  issue.

* Add: `flexmark-docx-converter` module to render Markdown documents to docx using docx4j API.

* Add: `Parser.LISTS_ITEM_CONTENT_AFTER_SUFFIX`, default `false`, set to `true` to treat the
  item suffix as part of the list item marker after which the content begins for indentation
  purposes. Gfm Task List uses the default setting.

* Fix: complete conditional indent/border style propagation from parent to child based on parent
  and child types. Some child types already incorporate their parent's indentation such as list
  item styles and manually propagated indents for `Quotations` style.

* Add: Numbering definitions: 1 - heading, 2 - bullet lists, 3 - ordered lists, 4 - bullet lists
  in block quotes, 5 - ordered lists in block quotes

0.26.6
------

* Fix: Source Line based position HTML rendering would break links with text spanning multiple
  lines.

0.26.4
------

* Fix: #146, Formatter missing blank line after HTML blocks. General formatter issue if
  `Parser.BLANK_LINES_IN_AST` was false. Affected elements:

  * Abbreviations
  * Definitions
  * Html Blocks
  * Lists

* Fix: `Node.segmentSpanChars(StringBuilder, int, int, String, String, String, String)` would
  not output the segment if `startOffset >= endOffset`, which would be the case if the node's
  segment was replaced with a string. Now dumping the AST using `AstCollectingVisitor` will
  reflect replaced segments.

0.26.2
------

* Fix: #143, FlexmarkHtmlParser builders not static, merged #144, Update FlexmarkHtmlParser.java

* Fix: make html parser instance re-usable, now resets state for each parse invocation

0.26.0
------

* Fix: Update parser to CommonMark Spec 0.28, the only effect on parsing is:

  * `***foo***` is now parsed as `italic(bold(foo))` whereas previously it was
    `bold(italic(foo))`.

    Use `ParserEmulationProfile.COMMONMARK_0_27` or just set the `Parser.STRONG_WRAPS_EMPHASIS`
    to `true` to get old parsing behaviour.

  * Matched Nested parentheses in link URLs now do not need to be escaped. `[foo](fun(bar))`
    will now be parsed as a link, previously it would be a `[foo]` reference followed by text
    `(fun(bar))`.

    Use `ParserEmulationProfile.COMMONMARK_0_27` or just set the
    `Parser.LINKS_ALLOW_MATCHED_PARENTHESES` to `false` to get old parsing behaviour.

* Add: profiles for `COMMONMARK_0_26`, `COMMONMARK_0_27`, `COMMONMARK_0_28`, with `COMMONMARK`
  defaulting to the most recent implemented spec: 0.28 for now. The 0.26 profile only differs
  from the 0.27 by using double blank lines to terminate lists. Delimiter parsing is not
  downgraded from 0.27

* Add: spec tests for 0.27 and 0.28 which use the version specific emulation profiles.

0.24.0
------

* API Change: add node renderer API for delegating rendering to previously registered node
  renderer stack, allowing partial node rendering customizations with non-customized rendering
  passed to previous renderer.

  * `NodeRendererContext.delegateRender()` to delegate current node rendering to previously
    registered handler for the current node. Should only be called from handler's `render()`
    method. If there is no renderer to which to delegate then the node will not be rendered.
  * `NodeRendererContext.getDelegatedSubContext(Appendable, boolean)` to get a sub-context to be
    used in delegated rendering, this context inherits current node rendering handler and node
    information needed for `delegateRender()` method.
  * Added `DelegatingNodeRendererFactory` which should be implemented by custom renderers which
    rely on delegating some rendering of their nodes to existing renderers. It provides a set of
    `NodeRendererFactory` classes to which this renderer may delegate to provide a controlled
    ordering of renderers independent of registration order.

0.22.24
-------

* Fix: remove `flexmark-ext-spec-example` module from `flexmark-profile-pegdown` dependencies
  because it isn't used and caused junit to be included in runtime scope.

0.22.22
-------

* Fix: remove `flexmark-ext-spec-example` module from `flexmark-all` dependencies because it
  would cause junit to be included in runtime scope.

0.22.20
-------

* Change: refactor link and reference nodes for common access to url, pageref, anchor and title
  fields. Also add convenience `Node.getSegmentsForChars()` which returns only sequences which
  when appended into `SegmentedSequence` will result in the chars for the node.

* Add: sample for url change in AST and output via formatter:
  [FormatterWithMods.java](https://github.com/vsch/flexmark-java/blob/master/flexmark-java-samples/src/com/vladsch/flexmark/samples/FormatterWithMods.java)

* Fix: #138, HTML to Markdown converter missing list end for two consecutive lists

* Add: `FlexmarkHtmlParser.LISTS_END_ON_DOUBLE_BLANK` default `false`, when set to `true`
  consecutive lists are separated by double blank line, otherwise by an empty HTML comment line.

0.22.18
-------

* Change: when `Parser.CODE_SOFT_LINE_BREAKS` is `true` and `HtmlRenderer.SOFT_BREAK` is not all
  spaces or tabs then inline code will render soft line breaks as per `HtmlRenderer.SOFT_BREAK`
  definition. A side-effect of this is white-space collapsing only occurs for inline code line
  segments.

* Change: add `Parser.CODE_BLOCK_INDENT` defaults to value of `Parser.LISTS_ITEM_INDENT`, allows
  for separate control of indented block setting from list item settings.

0.22.16
-------

* Fix: #133, The pom.xml file's url should be update

* Add: extra wiki link tests for combination with `TypographicExtension`

* Fix: escaped HTML blocks should be wrapped in `<p>...</p>`

* Fix: allow `Document.getLineNumber(int offset)` to return line number when
  `Parser.TRACK_DOCUMENT_LINES` is false, less efficient because it counts EOL in document char
  sequence. Useful when line number is needed for error reporting without incurring storage
  overhead of storing individual line character sequences.

0.22.14
-------

* Fix: potential exception when generating source position in HTML for individual lines

* Add: `Parser.TRACK_DOCUMENT_LINES` default `false`. When `true` document lines are tracked in
  the document's `lineSegments` list and offset to line method can be used to get the 0-based
  line number for the given offset. When `false` these functions return `0`.

* Add: line number API functions to `Node` and `Document`, set`Parser.TRACK_DOCUMENT_LINES` to
  true to have these functions return other than 0.

  * `Node.getLineNumber()` and `Node.getStartLineNumber()` returns 0-based line number for
    node's start offset.

  * `Node.getEndLineNumber()` returns 0-based line number for node's end offset - 1.

  * `Document.getLineCount()` returns number of lines in the document

  * `Document.getLineNumber(int offset)` returns 0-based line number for the given offset or
    `Document.getLineCount()` if offset is outside the document text range.

0.22.12
-------

* Add: `GfmUsersExtension` to parse Gfm style user refs `@user-name`

* Add: `GfmIssuesExtension` to parse Gfm style issue refs `#123`

0.22.10
-------

* Add: `Parser.INLINE_DELIMITER_DIRECTIONAL_PUNCTUATIONS` default `false`, when `true` allows
  delimiters to start after opening brackets `([{<` and end after closing brackets `)}]>`
  without needing to have whitespace characters on the other side of delimiter.

  Currently `aa**foo**aa` will parse bold but `aa**foo()**aa` will not because bracket direction
  is not taken into account. With above option set to `true` the second case will also parse
  bold but `aa**foo(**aa` and `aa**)foo**aa` will not.

* Add: `FlexmarkHtmlParser.PRE_CODE_PRESERVE_EMPHASIS`, default `false`. When `true` will
  preserve inline emphasis tags (`<strong>`, `<em>`, `<del>`, `<ins>`, `<sub>`, `<sup>`) and
  convert them to markdown syntax inside `<pre><code>` HTML blocks. Otherwise will strip these
  out of generated markdown.

      <pre><code class="html">&lt;div&gt;
        <strong>test</strong>
      &lt;/div&gt;
      </code></pre>

  By default will strip out emphasis and convert to:

      ```html
      <div>
        test
      </div>
      ```

  With option set to true will preserve emphasis and convert to:

      ```html
      <div>
        **test**
      </div>
      ```

0.22.8
------

* Add: `TablesExtension.MIN_SEPARATOR_DASHES` to control how many dashes minimum before
  separator column is recognized as a table separator.

0.22.6
------

* Add: parser family specific HTML block test cases

* Add `Parser.HTML_BLOCK_DEEP_PARSE_INDENTED_CODE_INTERRUPTS` default `false`, when `true`
  Indented code can interrupt HTML block without a preceding blank line.

0.22.4
------

* Add: `ParserEmulationProfile.PEGDOWN_STRICT` profile to emulate HTML block parsing according
  to pegdown rules. `ParserEmulationProfile.PEGDOWN` uses less strict HTML block parsing which
  will end an HTML block on a blank line.

* Add: `Parser.HTML_BLOCK_DEEP_PARSE_FIRST_OPEN_TAG_ON_ONE_LINE` to not parse open tags unless
  they are contained on one line. Parsers like MultiMarkdown 6.0 more compatible with this mode
  on.

* Add: html deep block parsing for non-commonmark parsers. Need to add HTML block parsing tests
  to parser emulation family tests.

* API Change: `BlockParser.isRawText()` used for interruptible blocks, when this method returns
  `true` then indenting spaces are passed to the block. Used by `HtmlBlockParser` to keep
  indents on continuation lines that could be interrupted by another markdown element.

* Fix: add optional tag logic to `HtmlDeepParser` so that optional end tags when omitted do not
  cause nesting of tags as per
  [8.1.2.4. Optional tags](https://www.w3.org/TR/html51/syntax.html#optional-tags)

0.22.2
------

* Fix: Deep HTML parser double parsing the first line of the HTML

0.22.0
------

* API Change: remove `tagOpened` and `tagClosed` from `HtmlFormattingAppendable`. These methods
  are part of implementation. Moved `HtmlFormattingAppendableBase` and made `protected`.

* API Change: add `BlockParser.isInterruptible()` and
  `BlockParser.canInterruptBy(BlockParserFactory)` to allow block parser control over which
  blocks can interrupt them.

* Add: `HtmlDeepParser` class to handle chunkwise parsing of HTML blocks to allow better HTML
  block parsing behaviour and pegdown compatibility.
  * `Parser.HTML_BLOCK_DEEP_PARSER` default `false` - enable deep HTML block parsing
  * `Parser.HTML_BLOCK_DEEP_PARSE_NON_BLOCK` default 'true', parse non-block tags inside HTML
    blocks
  * `Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS` default `true`, when true Blank line
    interrupts HTML block when not in raw tag, otherwise only when closed
  * `Parser.HTML_BLOCK_DEEP_PARSE_MARKDOWN_INTERRUPTS_CLOSED` default false, when true Other
    markdown elements can interrupt a closed HTML block without an intervening blank line
  * `Parser.HTML_BLOCK_DEEP_PARSE_BLANK_LINE_INTERRUPTS_PARTIAL_TAG` default true, when true
    blank line interrupts partially open tag ie. `<TAG` without a corresponding `>` and having a
    blank line before `>`

* Fix: `ParserEmulationProfile.PEGDOWN` now will do deep HTML parsing by default with greater
  pegdown compatibility.

0.21.0
------

* API Change: add open tag tracking to `HtmlFormattingAppendable` to allow generating correct
  HTML when generating src line position `span` wrapping of individual paragraph lines

      public Stack<String> getOpenTags();
      public List<String> getOpenTagsAfterLast(CharSequence latestTag);
      public void tagOpened(CharSequence tagName);
      public void tagClosed(CharSequence tagName);

* Add: `Parser.CODE_SOFT_LINE_BREAKS` to generate `SoftLineBreak` nodes inside `Code` nodes.
  Needed when generating line based src pos and inline code spans lines.

* Fix: src line position information rendering now properly closes and re-opens any inline tags
  spanning lines of source.

* Fix: when src line position option is enabled, inline tags no longer contain source position
  since they are wrapped in `<span>` tags with line source position.

* Fix: add src position information to superscript, insert nodes.

0.20.2
------

* Fix: #120, Fixed Indent mode always has the last list item as tight. Fixed Spaces parsing
  ignores blank line after last list item and does not check if previous item is loose either.
  Making it impossible to make the last item loose.

  Added new `Parser.LISTS_LOOSE_WHEN_LAST_ITEM_PREV_HAS_TRAILING_BLANK_LINE`, default `false`.
  When true the last item in a list will be loose if previous item has a trailing blank line.
  `ParserEmulationProfile.FIXED_INDENT` to allow last item to be loose.

0.20.0
------

* API Change: #117, Add target attribute support to ResolvedLink and updates to the associated
  core renderer code. Now `LinkResolver` can also set any attribute of the link via
  `ResolvedLink.getAttributes()` and `ResolvedLink.getNonNullAttributes()` methods and
  manipulate the desired attributes of the link.

0.19.8
------

* Fix: #112, Potential bug: Node.getChildOfType always returns null unless parent is an instance
  of the desired class. Affected some list parsing in Old Gfm compatibility, which were bugs.

0.19.6
------

* Fix: #109, Image Ref missing title tag in rendered HTML, this bug also affects title tag of
  link ref elements.

* Fix: assertion error in `ImageRef` rendering.

0.19.5
------

* Add: `Parser.SPACE_IN_LINK_ELEMENTS`, default `false`, to allow whitespace between `![]` or
  `[]` and `()` of links or images.

* Add: `Parser.SPACE_IN_LINK_ELEMENTS` setting to `true` when pegdown profile is selected.

0.19.4
------

* Add: `LinkType.LINK_REF`, `LinkType.IMAGE_REF` and `ResolvedLink.getTitle()` to allow link
  resolvers to specify/modify link title and also to allow link resolvers to provide url and
  title for unresolved `LinkRef` and `ImageRef` nodes.

0.19.3
------

* Fix: index out of bounds exception in `flexmark-ext-escaped-character` processing elements
  embedded in other elements with prefix text removed.

* Fix: `flexmark-ext-escaped-character` erroneously processing fenced code content

* Fix: change `ReplacedTextMapper` original range to base range and add real original offset
  range. Otherwise processing `SegmentedSequence` would be wrong and cause index out of bounds
  exceptions.

* Fix: `flexmark-ext-autolink` erroneously processing fenced code content

0.19.2
------

* Fix: Formatter lost `SimTocBlock` element line spacer and other blank line formatting in the
  sim toc content

0.19.1
------

* Fix: #97, Use UnsupportedOperationException

0.19.0
------

* Fix: #93, Adding TextCollectingVisitor class handlers

* Change: add `Parser.FENCED_CODE_BLOCK_PARSER` as replacement for `Parser.CODE_CONTENT_BLOCK`
  with the latter marked as deprecated.

* API Change: add child `Text` nodes to `Code` and `FencedCodeBlock` that contain the text of
  the element. NOTE: text node is only added to `FencedCodeBlock` if
  `Parser.FENCED_CODE_CONTENT_BLOCK` is `false`, otherwise `CodeContent` block is used.

0.18.9
------

* Add: #86, how can use definition extension correctly ?, definition item spaced two or more
  blank lines from previous element when `DefinitionExtension.DOUBLE_BLANK_LINE_BREAKS_LIST` is
  true to not parse as definition item.

0.18.8
------

* Fix: #86, how can use definition extension correctly ?

* Add: `DefinitionExtension.DOUBLE_BLANK_LINE_BREAKS_LIST`, default false. When true double
  blank line between definition item and next definition term will break a definition list.

0.18.7
------

* Fix: #91, DocumentParser Exception on Empty Documents

0.18.6
------

* Add: vararg argument to `PegdownOptionsAdapter.getFlexmarkOptions(Extension[])` and
  `PegdownOptionsAdapter.flexmarkOptions(int, Extension[])` for additional extensions to add.
* Add: helper functions to `Parser.addExtensions(MutableDataHolder, Extension[])` and
  `Parser.removeExtensions(MutableDataHolder, Extension[])`
* Fix: IndexOutOfBoundsException on paragraph pre-processing under some conditions

0.18.5
------

* Fix: #83, Question: is there any way to keep every html entities as-is?, add
  `HtmlRenderer.UNESCAPE_HTML_ENTITIES`, default `true`. Set to `false` to leave HTML entities
  as is in the rendered HTML.

0.18.4
------

* Add: `HtmlRenderer.HTML_BLOCK_OPEN_TAG_EOL` and `HtmlRenderer.HTML_BLOCK_CLOSE_TAG_EOL`,
  default `true`. When `false` will suppress EOL before/after HTML block tags which are
  automatically generated during html rendering. Used for doxia compatibility.

0.18.3
------

* Add: #78, Space in link, option `Parser.SPACE_IN_LINK_URLS`, default `false`. When enabled
  will allow spaces in link address as long as they are not followed by a `"`, used to start the
  title of the link. Link address wrapped in `<>` will include any trailing spaces.

  Auto-links, wrapped or not in `<>` cannot have spaces. These require URL encoding the space
  with `%20`.

0.18.2
------

* Fix: #76, HTML to Markdown hangs if comments included in Text nodes

* Add: MS-Word generated HTML list basic recognition: `1.`, `1)`, `A.`, `A)`, `a.`, `a)`, `IV.`,
  `IV)`, `iv.`, `iv)`

* Fix: MS-Excel generated HTML table parsing bug

* Add: `FlexmarkHtmlParser.RENDER_COMMENTS`, default `false`. When set to true HTML comments
  will be rendered in the Markdown.

* Add: `FlexmarkHtmlParser.DOT_ONLY_NUMERIC_LISTS`, default `true`. When set to false closing
  parenthesis as a list delimiter will be used in Markdown if present in MS-Word style list.
  Otherwise parenthesis delimited list will be converted to dot `.`.

* Add: `DoNotLinkDecorate` interface to distinguish text decoration from link text decoration
  for flexibility.

* Fix: `TypographicQuotes` and `TypographicSmarts` to not implement `DoNotDecorate` interface so
  `AbbreviationExtension` will text in quotes.

* Fix: replace regex used for extracting HTML comments from HTML blocks to manual search. RegEx
  would go into an infinite loop on MS Word created HTML.

0.18.1
------

* Fix: `TocExtension` and `SimTocExtension` to set `HtmlRenderer.GENERATE_HEADER_ID` to `true`
  and `HtmlRenderer.RENDER_HEADER_ID` to `true` if they are not already explicitly set.

* Fix: HTML to Markdown converter to add a space after empty list items

* Fix: #75, Incorrect footnote link. `\r\n` sequence was not properly recognized in `Parsing`
  patterns used by the parser, causing parsing discrepancies when EOL was not `\n` in many
  elements.

0.18.0
------

* Fix: HTML to Markdown converter to not ignore text in lists which is not included in a list
  item but instead to put this text into a new list item.

* API Change: add `DelimiterProcessor.canBeOpener(boolean, boolean, boolean, boolean, boolean,
  boolean)` and `DelimiterProcessor.canBeCloser(boolean, boolean, boolean, boolean, boolean,
  boolean)` to allow customization of when a delimiter can be an opener or closer. Default
  CommonMark does not work for `<<` and `>>` which can open and close anywhere.

0.17.4
------

* Fix: #73, Can't nest code blocks in ordered list. GitHub Doc compatibility error in parsing
  fenced code nested in list items.

0.17.3
------

* Fix: remove unused `flexmark-jira-parser` module from repo.

* Fix: #72, Multiple angle quotes not being handled correctly

0.17.2
------

* Fix: #70, parse failed for angle quotes if the end angle quote follows with a line feed or a
  carriage return. Actual error was in `flexmark-ext-typographic` in
  `QuoteDelimiterProcessorBase` not checking for sequence length.

0.17.1
------

* Add: #69, Add options to allow changing the rendering of bold, italic and other styles with
  different tags.

  The following `String` options are defined that will override the wrapper used for the HTML of
  styles. :warning: both open and close values must be set to non-null values for the setting to
  take effect:

  * HtmlRenderer.STRONG_EMPHASIS_STYLE_HTML_OPEN
  * HtmlRenderer.STRONG_EMPHASIS_STYLE_HTML_CLOSE
  * HtmlRenderer.EMPHASIS_STYLE_HTML_OPEN
  * HtmlRenderer.EMPHASIS_STYLE_HTML_CLOSE
  * HtmlRenderer.CODE_STYLE_HTML_OPEN
  * HtmlRenderer.CODE_STYLE_HTML_CLOSE
  * InsExtension.INS_STYLE_HTML_OPEN
  * InsExtension.INS_STYLE_HTML_CLOSE
  * StrikethroughSubscriptExtension.STRIKETHROUGH_STYLE_HTML_OPEN
  * StrikethroughSubscriptExtension.STRIKETHROUGH_STYLE_HTML_CLOSE
  * StrikethroughSubscriptExtension.SUBSCRIPT_STYLE_HTML_OPEN
  * StrikethroughSubscriptExtension.SUBSCRIPT_STYLE_HTML_CLOSE
  * StrikethroughExtension.STRIKETHROUGH_STYLE_HTML_OPEN
  * StrikethroughExtension.STRIKETHROUGH_STYLE_HTML_CLOSE
  * SubscriptExtension.SUBSCRIPT_STYLE_HTML_OPEN
  * SubscriptExtension.SUBSCRIPT_STYLE_HTML_CLOSE
  * SuperscriptExtension.SUPERSCRIPT_STYLE_HTML_OPEN
  * SuperscriptExtension.SUPERSCRIPT_STYLE_HTML_CLOSE

0.17.0
------

Change: language level 6 for source and byte code in attempt to get compatibility for Doxia.
Code still requires JDK7 for BitSet functionality and `org.nibor.autolink` is JDK 1.7 bytecode
level. So no success but the source is JDK 1.6.

Add: `Parser.CODE_CONTENT_BLOCK`, default `false`. If set to true will create an AST `CodeBlock`
node as a child node of `FencedCodeBlock` and `IndentedCodeBlock`. Allows custom rendering of
`CodeBlock` content code while leaving rendering of fenced code and indented code blocks
standard.

Change: `PhasedNodeRenderer.renderDocument(NodeRendererContext, HtmlWriter, Document,
RenderingPhase)` is now also called for `RenderingPhase.BODY` but no rendering should be done
for this phase. All rendering for the body phase should be done through regular node renderer
API.

0.16.1
------

Fix: #67, Formatter with GitHubDoc emulation indented code of list items not indented enough

Fix: #65, Add some comments to `Extension`, `Parser.ParserExtension`,
`Parser.ReferenceHoldingExtension` and `HtmlRenderer.HtmlRendererExtension`

0.16.0
------

API Change: To fix #66 it was necessary to add more parameters to
`BlockParser.canContain(ParserState, BlockParser, Block)` to allow for more testing whether a
fenced code block can be contained by a list item.

Fix: #66, GitHub Doc profile incorrect parsing of following markdown

0.15.4
------

Fix: #62, Autolinks extension for http:// and https:// links includes trailing spaces

Fix: #60, Kramdown parser discrepancy for mismatched ordered/ unordered list items. Now if both
`LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST` and `LISTS_ITEM_TYPE_MISMATCH_TO_SUB_LIST` are set to
true then a new list will be created if the item had a blank line, otherwise a sub-list is
created.

Change: `Kramdown` profile to set `LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST` so that parsing of
mismatched item type starts a new list.

0.15.3
------

* Add: default class option for fenced code and indented code
  `HtmlRenderer.FENCED_CODE_NO_LANGUAGE_CLASS` which can be used to disable highlighting if info
  not specified for fenced code or in indented code.

0.15.2
------

* Change: flexmark-parent renamed to flexmark-all

0.15.1
------

* Fix: default gfm task list item to include `&nbsp;` spacer after input check box and add
  `readonly` attribute

* Add: flexmark-parent artifact with classifier `lib` to create a jar with all core, extension
  and conversion modules

0.15.0
------

* Add: PDF converter extension
  [Usage: PDF Output](https://github.com/vsch/flexmark-java/wiki/Usage#pdf-output)

0.14.0
------

* Fix: Implement `TypographicExtension` to convert smarts and quotes conversion:
  * `'` to apostrophe `&apos;` &rsquo;
  * `...` and `. . .` to ellipsis `&hellip;` &hellip;
  * `--` en dash `&ndash;` &ndash;
  * `---` em dash `&mdash;` &mdash;
  * single quoted `'some text'` to `&lsquo;some text&rsquo;` &lsquo;some text&rsquo;
  * double quoted `"some text"` to `&ldquo;some text&rdquo;` &ldquo;some text&rdquo;
  * double angle quoted `<<some text>>` to `&laquo;some text&raquo;` &laquo;some text&raquo;

* API Change: `DelimiterProcessor.unmatchedDelimiterNode(InlineParser, DelimiterRun)` method
  added to allow substituting unmatched delimiter text by another node.

0.13.7
------

* Fix: #56, Pegdown HARDWRAPS option adds an additional line break when using trailing spaces

* Add: test for pegdown HARDWRAPS extension.

0.13.6
------

* Fix: #55, Indented Link Reference Definitions not parsed correctly

0.13.5
------

* Fix: #54 when two spaces followed by \r\n would not parse as HARD break.

0.13.4
------

* Fix: #54, 2 spaces at end of line is not recognized as a newline if the end of line has \r\n

* Fix: related to #54, \r\n should not include \r as part of node's text

* Add: option to HTML to Markdown to handle auto link conversion:
  * `FlexmarkHtmlParser.EXTRACT_AUTO_LINKS` default `true` to convert links which do not contain
    a `title` attribute or has one which is empty, and whose text and href are equal
  * `FlexmarkHtmlParser.WRAP_AUTO_LINKS` default `false` to wrap auto links in `<>`

* Add: options to HTML to Markdown to handle smarts and quotes conversion:
  * `&ldquo;`, `“`, `&rdquo;`, `”` to `"`
  * `&lsquo;`, `‘`, `&rsquo;`, `’`, `&apos;` to `'`
  * `&laquo;`, `«` to `<<`
  * `&raquo;`, `»` to `>>`
  * `&hellip;`, `…` to `...`
  * `&endash;`, `–` to `--`
  * `&emdash;`, `—` to `---`

0.13.3
------

* Fix: #53, HTML to Markdown converter does handle pre wrapped text properly

0.13.2
------

* Add: table formatting options for column width adjustment and alignment application

0.13.1
------

* Add: `Formatter.BLOCK_QUOTE_BLANK_LINES` default `true`, to wrap block quotes in blank lines

* Change: defaults for:
  * `BLOCK_QUOTE_MARKERS` to `BlockQuoteMarker.ADD_COMPACT_WITH_SPACE`
  * `INDENTED_CODE_MINIMIZE_INDENT` to `true`
  * `FENCED_CODE_MINIMIZE_INDENT` to `true`
  * `FENCED_CODE_MATCH_CLOSING_MARKER` to `true`

* Add: sample for formatter use.

0.13.0
------

* Add: #50, Add Email obfuscation?, using pegdown profile e-mail obfuscation is on by default.
  For CommonMark and other processors you will need to set `HtmlRenderer.OBFUSCATE_EMAIL` to
  `true`, if you need to have repeatability for testing then set
  `HtmlRenderer.OBFUSCATE_EMAIL_RANDOM` to `false`.

* Add: #47, Add option to have BlankLine nodes in the AST. option `Parser.BLANK_LINES_IN_AST`
  which results in `BlankLine` nodes to be put into the AST for every blank line in the file.

  :warning: A blank line in terms of Markdown syntax is not necessarily a blank line in the
  file. Lines prefixed with `>` that are otherwise empty are blank lines inside the block quote
  and will be in the AST as blank lines.

* Fix: #48, When `Parser.HEADING_NO_ATX_SPACE` is enabled trailing `###` should not require a
  space

* Add: #49, Add `flexmark-formatter` module to render AST as markdown with formatting options,
  and Formatting API that can be used by extensions to customize formatting markdown source of
  custom elements. This module implements formatting of core nodes, all unknown nodes are pass
  through as is. See [Markdown Formatter](../../wiki/Markdown-Formatter) for options.

  * `Formatter.FormatterExtension` implementation to all modules where it makes sense to format
    custom elements.

0.12.3
------

* Add: Extra extension flags to `com.vladsch.flexmark.profiles.pegdown.Extensions` to allow easy
  configuration of extensions that don't exist in pegdown:
  * `Extensions.SUBSCRIPT`
  * `Extensions.EXTANCHORLINKS_WRAP`
  * `Extensions.FOOTNOTES`
  * `Extensions.TOC`
  * `Extensions.MULTI_LINE_IMAGE_URLS`
  * `Extensions.SUPERSCRIPT`
  * `Extensions.INSERTED`

* Fix: HTML to Markdown
  * skipped list items under some conditions.
  * style markers now wrapping full text instead of breaking it up into text sections
  * make sure there is no space before trailing marker or after leading marker and that there
    are spaces surrounding the style markers

0.12.2
------

* Change: HTML to Markdown conversion
  * blank line before headings.
  * a few elements to be unwrapped on conversion and some to wrap but as block elements so they
    could be easily cleaned in resulting markdown.
  * text node handling to trim text and escape `\`
  * suppress empty headings
  * task list item when first content of list item is input check box
  * options for output control, pass via `DataHolder` taking method `parse()` method:
    * `FlexmarkHtmlParser.LIST_CONTENT_INDENT`, default `true`, continuation lines of list items
      and definitions indent to content column otherwise 4 spaces
    * `FlexmarkHtmlParser.SETEXT_HEADINGS`, default `true`, if true then use Setext headings for
      h1 and h2
    * `FlexmarkHtmlParser.OUTPUT_UNKNOWN_TAGS`, default `false`, when true unprocessed tags will
      be output, otherwise they are ignored
    * `FlexmarkHtmlParser.ORDERED_LIST_DELIMITER`, default `'.'`, delimiter for ordered items
    * `FlexmarkHtmlParser.UNORDERED_LIST_DELIMITER`, default `'*'`, delimiter for unordered list
      items
    * `FlexmarkHtmlParser.DEFINITION_MARKER_SPACES`, default `3`, min spaces after `:` for
      definitions
    * `FlexmarkHtmlParser.MIN_TABLE_SEPARATOR_COLUMN_WIDTH`, default `1`, min 1, minimum number
      of `-` in separator column, excluding alignment colons `:`
    * `FlexmarkHtmlParser.MIN_TABLE_SEPARATOR_DASHES`, default `3`, min 3, minimum separator
      column width, including alignment colons `:`
    * `FlexmarkHtmlParser.CODE_INDENT`, default 4 spaces, indent to use for indented code
    * `FlexmarkHtmlParser.EOL_IN_TITLE_ATTRIBUTE`, default `" "`, string to use in place of EOL
      in image and link title attribute.
    * `FlexmarkHtmlParser.NBSP_TEXT`, default `" "`, string to use in place of non-break-space
    * `FlexmarkHtmlParser.THEMATIC_BREAK`, default `"*** ** * ** ***"`, `<hr>` replacement
* Change: `FormattingAppendableImpl.flush()` no longer forces an EOL, only allows it if it is
  already pending. Also `FormattingAppendableImpl.flush(int)` and
  `FormattingAppendableImpl.getText(int)` allow -1 in which case they will suppress trailing
  EOL.
* Change: `HtmlRenderer.render()` methods now add a trailing EOL to resulting HTML if it is
  missing. Previously this was done by `FormattingAppendableImpl.flush()`

0.12.1
------

* Fix: `FlexmarkHtmlParser`
  * infinite loop when handling unknown/unhandled HTML tags.
  * recognizing emoji shortcuts for GitHub emoji, GitHub emoji URL images and images using
    EmojiCheatSheet file names.
  * convert `<br>` to hard break in paragraphs
  * `th` tags in `tbody` `tr` now create a `thead` row instead of `tbody` row

* Update: Emoji Extension to latest Emoji Cheat Sheet shortcuts

0.12.0
------

* Fix: XWiki `Macro.getAttributes()` to use `LinkedHashMap` to preserve attribute order.

* Add: `flexmark-html-parser` module to convert HTML to markdown, uses `jsoup` for HTML parsing.

  Converts HTML to Markdown, assumes all non-application specific extensions are available:
  * abbreviations
  * aside
  * block quotes
  * bold, italic, inline code
  * bullet and numbered lists
  * definition
  * emoji shortcuts
  * fenced code
  * strike through
  * subscript
  * superscript
  * tables
  * will also handle conversion for multi-line URL images

0.11.10
-------

* Fix: #44, flexmark-profile-pegdown maven, pegdown profile module was misnamed in pom, no
  errors but was not deploying.

0.11.9
------

* Fix: #43, Out of date documentation for Pegdown Migration Helper, caused by
  `flexmark-profile-pegdown` missing from project dependencies and not being updated.

0.11.8
------

* Fix: #42, Index out of bounds when XWiki macro match is rejected because of context mismatch

0.11.7
------

* Fix: `HtmlRenderer.Builder` and `Parser.Builder` constructor which takes an instance of other
  plus `DataHolder` to reload all extensions to allow extensions to register different
  processors based on options. Previously these copied all registered components plus new ones,
  not allowing components to be not-registered based on options.

* Add: XWiki Macro members to facilitate usage of these nodes in XWiki processing:
  * `MacroBlock`
    * `Map<String, String> getAttributes()`: map of attributes
    * `Macro getMacroNode()`: returns the `Macro` first child node
    * `boolean isClosedTag()`: true if macro has `{{macro /}}` form
    * `BasedSequence getMacroContentChars()`: raw text of macro content
  * `Macro`
    * `Map<String, String> getAttributes()`: map of attributes
    * `boolean isClosedTag()`: true if macro has `{{macro /}}` form
    * `boolean isBlockMacro()`: true if macro is the first child of `MacroBlock`
    * `BasedSequence getMacroContentChars()`: raw text of macro content

* Add: XWiki options `MacroExtension.ENABLE_BLOCK_MACROS`, `MacroExtension.ENABLE_INLINE_MACROS`
  and `MacroExtension.ENABLE_RENDERING`. If block macros are disabled then all macros will be
  inline macros.

0.11.6
------

* Fix: jekyll tags not parsed if two spaces included after tag and before the closing marker

* Add: jekyll tags `JekyllTagExtension.INCLUDED_HTML` a map of include parameter strings to
  string of HTML content to replace the include tag: `{% include file %}`

* Add: `HtmlRenderer.RECHECK_UNDEFINED_REFERENCES` to check link and image refs which are not
  defined again, used by `Parser.transferReferences(Document, Document)` to let extensions know
  they need to try resolving references.

* Add: `Parser.ReferenceHoldingExtension` to be implemented by extensions that create node
  repostitories so that these can be copied from included documents to including documents.

* Add: `Parser.transferReferences(Document, Document)` for use by library users and static
  `Parser.transferReferences(NodeRepository<T> destination, NodeRepository<T> included, boolean
  ifUndefined)` for use by extensions to implmement `Parser.ReferenceHoldingExtension`

* Add: Reference, Footnote and Abbreviation reference copying

0.11.5
------

* Add: Jekyll Tags extension to parse tags of the form: `{% tag params %}`, with options
  `JekyllTagExtension.ENABLE_BLOCK_TAGS` and `JekyllTagExtension.ENABLE_INLINE_TAGS` to disable
  individual types of parsers.

* Fix: remove unused xwiki macro options

0.11.4
------

* Fix: #40, XWiki Ext Block Macros should not interrupt a paragraph

* Add: test for #40

* Add: `InlineParserExtensionFactory` and `InlineParserExtension` to allow custom inline parsing
  elements.

* Add: inline xwiki macro processing

* Change: default node renderer for xwiki macros now renders the macro as text so that if node
  rendering is not done by the application then output will be equivalent to text for unhandled
  macros.

0.11.3
------

* Add: `Parser.HEADING_NO_EMPTY_HEADING_WITHOUT_SPACE` option when `true` and
  `Parser.HEADING_NO_ATX_SPACE` is false then bare `#`'s are not interpreted as headings unless
  they are followed by at least one space or tab character.

* Fix: treat spaces and tabs for purpose of heading parsing as equivalent

* Add: `flexmark-ext-xwiki-macros` xwiki application specific macro extension, for now only the
  block macros are implemented. Inline are treated as text.

0.11.2
------

* Remove: `ReversedCharSequence` and `IndexMapper` because these were moved to
  `reverse-regex-util` library that implements reverse search using regex.

0.11.1
------

* Fix: GitHub profile to correctly parse deeply nested, content indent aligned lists at the
  expense of matching poorly formatted markdown matching. GitHub Doc profile has many
  differences from GitHub parsing for edge cases because GitHub has very unorthodox and
  irregular parsing rules that I have not been able to completely figure out.

0.11.0
------

* Fix: #38, AutoLink extension does not recognize links with ampersand in the link

* Add: Footnote extension to use `Parser.LISTS_ITEM_INDENT` for determining which elements make
  up part of the footnote.

* Fix: `PegdownOptionsAdapter.flexmarkOptions(int)` was not passing pegdown options to
  constructor resulting in always having default 0 options

* Fix: `PegdownOptionsAdapter` did not properly set some list options for `pegdown`
  compatibility.

* Fix: removed some flags from `org.pegdown.Extensions` that were only added in my fork of
  pegdown and never made it into a released version:
  * `Extensions.EXTANCHORLINKS_WRAP`
  * `Extensions.FOOTNOTES`
  * `Extensions.INTELLIJ_DUMMY_IDENTIFIER`
  * `Extensions.MULTI_LINE_IMAGE_URLS`
  * `Extensions.RELAXED_STRONG_EMPHASIS_RULES`
  * `Extensions.TOC`
  * `Extensions.TRACE_PARSER`

* Add: Tests to `flexmark-profile-pegdown` based on actual pegdown HTML rendering of markdown
  for compatibility testing. Some pegdown idiosyncrasies/bugs not replicated.

* API Change: `Parser.LISTS_LOOSE_ON_PREV_LOOSE_ITEM` to
  `Parser.LISTS_LOOSE_WHEN_PREV_HAS_TRAILING_BLANK_LINE` which accurately reflects the setting.

* API Change: `Parser.LISTS_LOOSE_WHEN_BLANK_FOLLOWS_ITEM_PARAGRAPH` to
  `Parser.LISTS_LOOSE_WHEN_BLANK_LINE_FOLLOWS_ITEM_PARAGRAPH` which accurately reflects the
  setting.

* API Change: `Parser.LISTS_LOOSE_WHEN_HAS_NON_LIST_CHILDREN` to be able to emulate GitHub Doc,
  MultiMarkdown and pegdown with `Extensions.FORCELISTITEMPARA` option.

* API Change: add block quote options

  * `Parser.BLOCK_QUOTE_ALLOW_LEADING_SPACE`, default true, when `false` block quote with
    leading spaces will not be ignored

  * `Parser.BLOCK_QUOTE_INTERRUPTS_PARAGRAPH`, default true, when `false` block quote will not
    interrupt a paragraph, requiring a blank line before all block quotes

  * `Parser.BLOCK_QUOTE_INTERRUPTS_ITEM_PARAGRAPH`, default true, when `false` block quote will
    not interrupt an item paragraph, requiring a blank line between list item text and the block
    quote.

  * `Parser.BLOCK_QUOTE_WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH`, default true, when `false`
    block quote will be ignored if it has leading space before the marker and there is no blank
    line between the item text and the block quote.

* API Change: add heading parsing option `Parser.HEADING_CAN_INTERRUPT_ITEM_PARAGRAPH`, default
  `true`, when `false` heading in list item paragraph is ignored. Not Used but can be applied
  for partial GitHub compatibility implementation. GitHub parses ATX headings only in list items
  if the list has a blank line included in it. Too much of a kludge to replicate.

* API Change: rename `ParserEmulationFamily` to `ParserEmulationProfile` and make it implement
  `MutableDataSetter` and add `ParserEmulationProfile.family` field to get emulation family. Now
  there are predefined profiles for:
  * `COMMONMARK`, family `COMMONMARK`
  * `FIXED_INDENT`, family `FIXED_INDENT`
  * `KRAMDOWN`, family `KRAMDOWN`
  * `MARKDOWN`, family `MARKDOWN`
  * `GITHUB_DOC`, family `MARKDOWN`
  * `MULTI_MARKDOWN`, family `FIXED_INDENT`
  * `PEGDOWN`, family `FIXED_INDENT`

  Usage is simplified to:

```
private static final DataHolder OPTIONS = new MutableDataSet()
        .setFrom(ParserEmulationProfile.MULTI_MARKDOWN);
```

* Add: More tests to all non-CommonMark compatibility tests, block quote handling, block quote
  in list item handling, etc

* Add: GitHub document compatibility tests. It is a mix of `MARKDOWN` and some `KRAMDOWN` list
  parsing qualities, family changed to `MARKDOWN` since it is closer to it. Some edge cases not
  replicated, too many kludges in its parsing rules.

0.10.3
------

* Fix: #36, Gfm Task Items incorrectly converts deeply indented content as indented code when
  non-commonmark family

* Fix: #34, Add option to Wiki link extension to escape the pipe separating text and link

  * Add: `WikiLinkExtension.ALLOW_ANCHORS`, default `false`, to parse link for anchor refs. If
    link is text and page ref combined with anchor ref, then text will be without the trailing
    anchor marker `#` or anchor ref. For somewhat backwards compatible mode setting this key to
    `true`, will parse and set node's anchorRef field but will also remove the anchor marker and
    ref from the node's text if text and page ref are combined.

  * Add: `WikiLinkExtension.ALLOW_ANCHOR_ESCAPE`, default `false`, to allow `\` escapes for
    anchor markers.

  * Add: `WikiLinkExtension.ALLOW_PIPE_ESCAPE`, default `false`, to allow `\` escapes for pipe
    `|` that separates text from link.

  * Add: `WikiLinkNodeRenderer` to unescape link for wiki and image links before passing it to
    be resolved. Previously the link was not unescaped, causing backslashes `\` to appear in the
    HTML link address.

0.10.2
------

* Fix: #31, empty Gfm-Task list item nodes character span does not include the task marker

* Fix: #32, Thematic Break AST node includes the full line when embedded in other elements

0.10.1
------

* Fix: definitions would loose consecutive definition items without intervening definition
  terms.

0.10.0
------

* Fix: abbreviation extension for JDK7 needed to sort regex list of abbreviations in descending
  alphabetical order.

* Fix: travis YAML to JDK7

* Fix: Emoji extension to not allow spaces between delimiters and add API methods to support
  delimiters converting themselves to text if contained text does not meet constraints.

* Add: method `Delimiter.convertDelimitersToText(int, Delimiter)` to remove used delimiters and
  convert them to text nodes instead of the expected delimited text node. For use when a
  delimiter processor determines that the delimited text is not valid for its wrapping.

* Change: `LinkRefProcessor.adjustInlineText(Document, Node)` to allow access to options and now
  returning `BasedSequence` of characters to keep as part of its contained text children.

* Add: `LinkRefProcessor.allowDelimiters(BasedSequence, Document, Node)` to have delimiter
  processing disabled for some regions withing the contained text

* Add: `LinkRefProcessor.updateNodeElements(Document, Node)` to allow the processor to update
  some node elements after all delimiter processing for contained text has completed. For
  example allows `WikiLink` nodes with `WikiLinkExtension.ALLOW_INLINES` enabled to adjust
  `link` field without inline markers.

* Add: `WikiLinkExtension.ALLOW_INLINES`, default `false`, to allow delimiter processing in text
  part when `|` is used, or when combined text and page ref in the contained text.

* Change: wiki links now split text from link using `|` based on
  `WikiLinkExtension.LINK_FIRST_SYNTAX` setting. If link is first then last `|` is used to split
  off the text portion. If text is first then first `|` is used. This ensures that no `|` are in
  the text, allowing `|` in links to be handled by the `LinkResolver`

* Change: Wiki link nodes now pass the whole link text to link resolver, previously only the
  `pageRef()` portion was passed and the `anchorRef()` was appended to the resolved link. Now it
  is the link resolver's responsibility to handle this extraction and attachment of anchor refs.

* API Change: add `ParserExtension.parserOptions(MutableDataHolder)` method that is called on
  all extensions before calling the `ParserExtension.extend(Builder)` so that all extensions get
  a chance to update options before any are loaded.

* API Change: add `HtmlRendererExtension.rendererOptions(MutableDataHolder)` method that is
  called on all extensions before calling the `HtmlRendererExtension.extend(Builder, String)` so
  that all extensions get a chance to update options before any are loaded.

* API Change: `Node.childChars()` renamed to `Node.getChildChars()` for consistency.

* Add: list parser options `Parser.LISTS_ITEM_MARKER_SUFFIXES` default `String[]{}`, to allow
  suffixes after item markers that will be treated as part of the marker not content for
  purposes of computing content offset.

* Add: list parser options `Parser.LISTS_NUMBERED_ITEM_MARKER_SUFFIXED` default `true`, to
  disable list marker suffix processing for ordered list items.

* API Change: gfm-tasks extension now using list suffix options for proper parsing of task item
  sub-item and child paragraphs.

  * `TaskListExtension.CONVERT_ORDERED_LIST_ITEMS` changed to
    `Parser.LISTS_NUMBERED_ITEM_MARKER_SUFFIXED`, same default of `true` but now part of core
    parser option.

  * `TaskListItem.getTaskOpeningMarker()` and `TaskListItem.setTaskOpeningMarker(BasedSequence)`
    are now part of `ListItem`, `ListItem.getMarkerSuffix()` and
    `TaskListItem.setMarkerSuffix(BasedSequence)`

0.9.4
-----

* Fix: Definition items were included as child nodes of definition terms.

* Fix: Definition list character range was not being updated

* Fix: Definition list to respect the auto-loose setting for lists. If one definition item in
  the list is loose then all items will be loose. ie. have their text wrapped in `<p></p>`

0.9.3
-----

* Add: `WikiLinkExtension.IMAGE_LINKS`, default `false`, when true enables wiki images of the
  form `![[]]` with optional `|` used for separating file ref from alt text. Other options:

  * `WikiLinkExtension.IMAGE_FILE_EXTENSION`, default `""`, to add file extension or suffix to
    file reference.

  * `WikiLinkExtension.IMAGE_PREFIX`, default `""`, to add a prefix to file reference

* Add: #24, DefinitionList extension doesn't seem to work, implemented definition lists as per
  PHP Markdown Extra.

* API Change: paragraph rendering now can determine whether `<p>` wrapping is disabled by
  checking if invoking its parent's
  `ParagraphItemContainer.isParagraphWrappingDisabled(Paragraph, ListOptions, DataHolder)`
  method. Removes all details of having to know anything about the parent type other than its
  implementation of `ParagraphItemContainer`

0.9.2
-----

* Change: make `NO_FILE_EOL` default condition for tests. Last EOL will be stripped unless the
  previous line is blank. Also have `FILE_EOL` to reverse the condition. Default for tests now
  is `NO_FILE_EOL` to force tests without having the file EOL terminated.

* Fix: #28, Table caption support?, add `TablesExtension.WITH_CAPTION` default `true`, when true
  will parse table caption line, line after table with format `[` ... `]`

* Change: methods that had `String` arguments to `CharSequence`

* Change: add `BasedSequence.appendTo(StringBuilder)` and start/end variations to allow
  optimized appending to string builder for specific implementations.

0.9.1
-----

* Fix: #27, Abbreviation node not called when 2 abbreviations, was expecting \n even at end of
  file.

* Add: `RenderingTestCase.NO_FILE_EOL` option `NO_FILE_EOL` to test cases which will strip out
  the last EOL of example in a spec to simulate input without trailing EOL. Otherwise all test
  cases had trailing EOL.

0.9.0
-----

* API Change: for `HtmlWriter` now using `FormattingAppendableImpl` to handle output formatting
  with greater flexibility. This affects how NodeRenderers generate output by allowing for more
  flexibility to control when line breaks should be suppressed or blank lines added.

  Modifications to existing code, some from Java 7 downgrade:

  1. `HtmlWriter.getAppendCount()` changed to `HtmlWriter.getModCount()` or can use the
     `HtmlWriter.getOffsetAfter()` to get actual character offset after append to find out if
     something was appended in child rendering code.

  2. Now there is EOL and blank line tracking even when using `HtmlWriter.raw()` so trying to
     output an extra blank line via `HtmlWriter.raw("\n")` will no longer work. Use
     `HtmlWriter.blankLine()`.

  3. There are several `raw` output methods all have slightly different behaviour since normal
     output does processing you may not want for `raw` text.

     * `HtmlWriter.raw(String)` will output raw without setting a pre-formatted region, which
       means accumulated spaces before the `raw()` call will be output, which is desired for
       inline html.

     * `HtmlWriter.rawPre(String)` will set a pre-formatted region and output text as is,
       without indents but will output any accumulates spaces before the `rawPre()` call. This
       is desired for real `<pre><code>` output.

     * `HtmlWriter.rawIndentedPre(String)` will also set a pre-formatted region but will set a
       fixed indent at the current indentation level that will be prefixed to all the lines and
       not output any pending white space characters. This is desired for HTML block output.

  4. `HtmlWriter.tagIndent(String, Runnable)` will no longer output an end of line after the
     opening tag if there was no lines appended between the opening and closing tag. In which
     case both tags will be on the same line. If you desire to have the tags always split on
     separate lines use the `HtmlWriter.tagLineIndent(String, Runnable)`, which will force the
     closing tag on a new line, even if no lines were appended by child content.

  5. Rendered HTML by default will now have a maximum of 1 blank line, no matter how many are
     output with the data. This is controlled by the `HtmlWriter.flush(int)` call and the
     `HtmlRenderer.render(Node, Appendable, int)` can be used which takes `maxBlankLines` as the
     last argument and controls maximum trailing blank lines that will be appended.

  Changes made:

  * \n in appended text is equivalent to `HtmlWriter.line()` call. Will only terminate a line of
    text, never create blankLines. For blank lines use `HtmlWriter.blankLine()` or
    `HtmlWriter.blankLine(int)`

  * spaces before and after \n are suppressed unless in `preFormatted` region. Before are not
    needed and after are controlled with `HtmlWriter.indent()` and `HtmlWriter.unIndent()`

  * tabs outside of `preFormatted` region are converted to spaces and multiple spaces are
    collapsed to a single space, or eliminated if they come before or after \n. Allowing
    generating clean HTML.

  * all forms of blank lines not generated by calls to `HtmlWriter.blankLine()` or
    `HtmlWriter.blankLine(int)` are suppressed in order to generate properly formatted output
    without extra blank lines.

  * `flush()` or `flush(int)` methods can now be used to control number of trailing blank lines
    in the output. They also need to be called to make sure trailing EOL is output to the
    underlying appendable.

  * now conditional `line()` and `indent()` formatting is available without having to use the
    `HtmlWriter.tag()` with runnable argument for the child element text generation, by using
    the `HtmlWriter.openConditional(ConditionalFormatter)` and
    `HtmlWriter.closeConditional(ConditionalFormatter)` methods giving the parent element
    ability to change indent, add a new line or blank lines, prefix any output before the child
    element's text. Similar ability on closing the conditional formatting region.

  * Add: `HtmlWriter.blankLine()` and `HtmlWriter.blankLine(int)` methods that will add a single
    blank line or a count of blank lines, even when called multiple times without intervening
    output. Ensures a blank lines where needed but no more than the requested blank lines.

  * to preserve spaces and EOL in `HtmlWriter.raw()` now temporarily setting pre-formatted
    region for the call. If this gets in the way and you want `raw()` text without HTML escapes
    but without pre-formatting, just use the `append()` methods passed through from
    `FormattingAppendableImpl`, they don't do any escaping but process all other formatting,
    unless in pre-formatting region.

* API Change: clean up of sequences in flexmark-java-util was long overdue. The `BasedSequence`
  interface has evolved but not existing uses were updated. The following changes allow
  optimization of `BasedSequence.subSequence()` and `BasedSequence.baseSubSequence()` depending
  on implementation particulars.

  Fastest parsing results will be achieved if the sequence passed to the parser is a
  `CharSubSequence`.

  Modifications to existing code, some from Java 7 downgrade:

  1. `BasedSequenceImpl.NULL` to `BasedSequence.NULL`

  2. `new SubSequence(` to `SubSequence.of(`

  3. `BasedSequence.of(` to `BasedSequenceImpl.of(`

  4. `new StringSubSequence(` to `BasedSequenceImpl.of(` or `CharSubSequence.of(`

  5. `com.vladsch.flexmark.util.Escaping` moved to `com.vladsch.flexmark.util.html.Escaping`

  6. `com.vladsch.flexmark.util.Html5Entities` moved to
     `com.vladsch.flexmark.util.html.Html5Entities`

  7. `Attribute` changes:

     1. `com.vladsch.flexmark.util.options.Attributes` moved to
        `com.vladsch.flexmark.util.html.Attributes`

     2. `com.vladsch.flexmark.util.options.Attribute` moved to
        `com.vladsch.flexmark.util.html.Attribute`

     3. `Attribute` is now an interface with `MutableAttribute` interface extending it for
        in-place attribute manipulation. implemented in `AttributeImpl`, instantiate with
        `AttributeImpl.of()` variants.

        * assign delimiters for `class` and `style` attribute names that you cannot override.
          The reset will get `NUL` delimiters unless you specify otherwise.

     4. `Attribute` now has a value list delimiter and a value name delimiter, these are used to
        split and combine values. Both can be `Attribute.NUL` then no multiple values can be
        stored or manipulated in the attribute. If value name delimiter is `Attribute.NUL` then
        multiple values are delimited by value list delimiter and considered named values
        without individual item values. For example:

        * `class` attribute has a list delimiter of `' '` and class names can be added removed
          but they have no values associated with them beyond their name.

        * `style` attribute on the other hand has a list delimiter of `';'` and a name delimiter
          of `':'`. Item part before the colon is the name and the part after is the value. So
          you can change individual style's settings using the attribute manipulation functions
          of: `Attribute.removeValue(String)`, `Attribute.setValue(String)` which will parse the
          strings and add/remove/replace values. Any item whose value is empty is removed. Any
          existing item's value is change to the new value and any new items are appended at the
          end.

```java
class Test {
    void test() {
        MutableAttribute attr = MutableAttribute.of("style");

        // after setValue(): attr.getValue() == "color:#white;background:#black"
        attr.setValue("color:#white;background:#black");

        // after setValue(): attr.getValue() == "color:#green;background:#black;font-family:monospaced;"
        attr.setValue("font-family:monospaced;color:#green");

        // after setValue(): attr.getValue() == "color:#green;font-family:monospaced;"
        attr.setValue("background");
    }
}
```

Changes made:

* Add: `CharSubSequence` implementation of `BasedSequence`

* uses `char[]` as the base that it accesses directly and creates `subSequence` and
  `baseSubSequence` instances that also access it directly eliminating the nested calls through
  `charAt`

* converts `\0` to `\uFFFD` on construction eliminating this test on every character access.

* in the future has the potential to support fast `toCharArray()` and related optimizations.

* Remove: all string based `BasedSequence` implementations, now replaced by `CharSubSequence`

* Change: `BasedSequence.getBase()` now returns `Object` to reflect that the underlying source
  of text could be anything.

* Add: `BasedSequence.getBaseSequence()` which returns a `BasedSequence` of the wrapped full
  source.

* Change: all sequence classes are now final and constructors private. Use static methods
  `BasedSequenceImpl.of()` or the same of specific classes to get sequences of source. This
  allows optimization based on inputs or when a `subSequence()` of the base is required use
  `BasedSequence.baseSubSequence()` method, which will return an optimized instance where
  possible.

* Add: documentation to `BasedSequence` interface

* Fix: YouTrack and Jira renderers to remove soft line breaks and to make sure no extra blank
  lines are added after loose items. Loose items had 2 blank lines between them because one was
  added by the paragraph node and the other by the list item.

* Add: added Toc and SimToc options:

* hierarchy: as before hierarchical list of headings

* flat: flat list of headings

* reversed: flat reversed list of headings

* increasing: flat, alphabetically increasing by heading text

* decreasing: flat, alphabetically decreasing by heading text

Extension changes:

1. new key `TocExtension.LIST_TYPE` takes a `TocOptions.ListType` enum for list generation type.

2. `TocOptions` now supports `Immutable` interface allowing to get a mutable copy whose fields
   can be modified directly with `toMutable()`, and back to `TocOptions` with `toImmutable()`.

3. `SimTocOptions` have been removed. `TocOptions` holds all the options for both the regular
   TOC and simulated TOC.

* Add: `SubscriptExtension` and `StrikethroughSubscriptExtension` extensions to
  `flexmark-ext-gfm-strikethrough` artifact. You guessed it, adds `~subscript~` parsing

* Add: `SuperscriptExtension` to `flexmark-ext-superscript` artifact for `^superscript^`
  parsing.

* Add: `InsExtension` to `flexmark-ext-ins` artifact for `++inserted++` or aka underlined
  parsing.

0.8.0
-----

* Change: remove Java 8 language constructs and reduce language level to 7 for android support.

* Change: remove `Substring` class from sequences and rename `StringSequence` to
  `StringBasedSequence`. `Substring` was duplicating code and not used except in tests.

* Change: move some statics from `BasedSequenceImpl` to `BasedSequence` and add
  `BasedSequence.of()` variations to the latter that check for already being a `BasedSequence`
  and just returning it or if `String` is passed then wrapping it in `StringBasedSequence` and
  if neither then wrap it in `SubSequence`.

* Fix: #21, table column alignment was not taking accumulated spans in the row into account when
  getting alignment for cell from table separator row.

* Fix: strike through extension was not rendering correctly for YouTrack conversion.

* Fix: moved all renderer specific extension tests to their corresponding renderer instead of
  having each extension test for each renderer. Much easier to ensure the extensions are
  properly tested and if any extension has not been properly updated.

* Add: Blank lines to Jira and YouTrack renderer after: heading, thematic break, block quote and
  table.

0.7.0
-----

* Add: YouTrack converter, same as Jira but with a few differences

* Fix: Jira converter not to add an extra blank line after paragraphs in block quotes

* Add: final to all node visitors' node parameter

* Add: upgrade to CommonMark spec 0.27

* Add: option `Parser.PARSE_JEKYLL_MACROS_IN_URLS` which allows any characters to appear between
  `{{` and `}}` in URLs, including spaces, pipes and backslashes.

* Add: option `HTML_COMMENT_BLOCKS_INTERRUPT_PARAGRAPH` with `true` by default but when false
  then they require a blank line before, otherwise they become inline HTML.

* Add: test for `HTML_COMMENT_BLOCKS_INTERRUPT_PARAGRAPH` option

* Add: header id generator options to allow slight customization of heading ids

* `HtmlRenderer.HEADER_ID_GENERATOR_RESOLVE_DUPES` when set to true adds a `-` with an
  increasing number from 1 to N for each duplicated id generated.

* `HtmlRenderer.HEADER_ID_GENERATOR_TO_DASH_CHARS` a string of characters in the heading text
  which will be mapped to `-`, alphanumerics are passed as is. Anything else is suppressed.

* `HtmlRenderer.HEADER_ID_GENERATOR_NO_DUPED_DASHES` if set will not generate consecutive dashes
  in the reference ids.

* Change: complete rewrite of list handling and list handling options to allow for Markdown
  parser emulation based on major parser families, as described in
  [Markdown Parser Emulation](MarkdownProcessorsEmulation.md). All major families are done and
  tested.

* CommonMark

* GitHub Comments

* CommonMark (default for family)

* FixedIndent

* MultiMarkdown

* Kramdown

* Kramdown (default for family)

* Markdown

* Markdown.pl (default for family)

No attempt was made to emulate inline processing of these parsers. Only list processing for now.
Most of the other element discrepancies are already addressable with existing parser options.

:warning: Markdown.pl is emulated fairly well but without its abundant bugs. Use it at your own
discretion.

* Fix: #19, ArrayIndexOutOfBounds while parsing markdown with backslash as last character of
  text block

* Change: `SpecReader` to parse for headings only if spaces are present after leading `#`.
  Otherwise, leading github issue `#7` in the description is treated as a heading.

* Add: list parsing option `Parser.LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH` to allow
  an empty list sub-item to be recognized as such. Needed by Markdown Navigator to recognize
  empty sub-items during parsing for list item formatting ops

0.6.1
-----

* Fix: #13, Hard line breaks do not work if markdown text/files uses CR LF as line separator

* Fix: #14, Link reference definitions indented by spaces not recognized

* Fix: #17, IndentedCodeBlock endOffset too large?, Added an option
  `Parser.INDENTED_CODE_NO_TRAILING_BLANK_LINES`

* Fix: #18, Unclosed FencedCodeBlock endOffset too small

* Add test for `Parser.HARD_LINE_BREAK_LIMIT` option

* Change `HtmlRenderer.GENERATE_HEADER_ID` default to true

* Fix #16, jira converter does not include lang= for fenced code with info

* Fix jira converter to output to better handle loose lists, items and options

* Fix jira converter to output an extra blank line after the end of the outermost list

0.6.0
-----

* Add aside extension that behaves as block quotes with `|` marker and generates `<aside>` tags
  instead of block quotes.

* Add multi-line url image links option

* Add `HtmlRenderer.SOURCE_WRAP_HTML` and `HtmlRenderer.SOURCE_WRAP_HTML_BLOCKS` options to wrap
  HTML blocks in `div` with source position information attribute.

* Fix #10, Wrong startOffset in HardLineBreak

* Add `Parser.HARD_LINE_BREAK_LIMIT` false by default, when true only the last 2 spaces of a
  hard line break will be part of the node. The rest are part of the previous text node.

0.5.0
-----

* Add table class option to tables extension

* Add `Parser.BLOCK_QUOTE_IGNORE_BLANK_LINE` option to make block quotes ignore blank lines
  between block quotes. This duplicates pegdown and GFM parsing.

* Add: `HtmlRenderer.TYPE` data key to allow extensions to set rendering type. Default value
  `HTML`.

* Add: `JiraConverterExtension` to convert standard markdown AST to JIRA formatted text

* Add: Rendering type to allow standard non-html rendering capability to be added by extensions.
  For now rendering types are: `HTML` default and `JIRA`. The latter generates JIRA formatted
  text from Markdown AST.

* Change: `HtmlRendererExtension.extend(Builder, String)` now gets a string argument for the
  type of rendering to be desired. If the extension does not recognize the type it should not
  register a renderer. Current values are: `HTML` and `JIRA` others may be added by extensions

* Change: standard extensions that can be mapped to JIRA formatted text to implement JIRA
  renderer.

* Add: `JIRA` rendering for auto-link, emoji, strikethrough, tables and wiki link extensions.

0.4.17
------

* Factor out utilities to a separate module to eliminate maven pom cycles

* Prepare for maven release

0.4.16
------

* Fix Toc extension did not generate the right table of contents hierarchy if header levels were
  missing. The indentation is still not perfect but will do for now.

* Add source position attribute to list item tags

* Rename `TablesExtension.HEADER_SEPARATOR_COLUMNS` to
  `TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH` and implement this option in the table parser.

* Fix child paragraphs of list items would not be wrapped in `p` tags if list auto-loose option
  was disabled and the item was not loose

0.4.15
------

* Add `HtmlWriter.srcPosWithTrailingEOL()` that will extend the position information to include
  trailing EOL after then end of the sequence, skipping any spaces or tabs. Used to include EOL
  for closing code fence marker.

* Fix #8, Fenced code closing sequence allows trailing spaces but not tabs. Tab added to ignored
  trailing characters after the closing marker.

0.4.14
------

* Add `HtmlWriter.srcPos()` methods to add source position information to the next `.withAttr()`
  tag.

* Add `HtmlRenderer.SOURCE_POSITION_ATTRIBUTE` the name of the source position HTML attribute so
  set to the source position given in `.srcPos()` and `.srcPosWithEOL()` methods. These behave
  as `.attr()` methods and the source position attribute will be applied to the next tag which
  is preceded by one of `.withAttr()` methods.

* Add `HtmlRenderer.SOURCE_POSITION_PARAGRAPH_LINES` if true then paragraph source lines will be
  wrapped in `<span></span>` with source position information for the line. Works even for tight
  list items that do not generate a `<p>` wrapper for their text. :warning: Only works if source
  position attribute is set to non-empty value.

* Add `AttributablePart` instances:

* `CoreNodeRenderer.CODE_CONTENT` to mark the `code` tag part of fenced code and indented code

* `CoreNodeRenderer.PARAGRAPH_LINE` to mark line spans of paragraphs source positions, list
  items or any other text block supporting lazy continuation.

* Refine source position attribute generation to make highlighting HTML elements from source
  position information more intuitive.

0.4.13
------

* Fix #5, SimToc did not unescape the title string when rendering

* Add `EmojiExtension.ATTR_IMAGE_SIZE` and `EmojiExtension.ATTR_ALIGN` options to control image
  size and align attributes for rendering

* Change rename `RenderAs.SIMPLE` to `RenderAs.SECTIONS`

* Change `SpecExampleNodeRenderer` for `RenderAs.SECTIONS` option to render the spec example
  section and number if it is available as `h5`, also only render `hr` tag if the section text
  is not empty.

* Add `Parser.LISTS_LOOSE_ON_PREV_LOOSE_ITEM` option to set the next list item as loose if
  previous one was loose. This makes list items mimic GFM quirky (diplomatic way of saying
  buggy) list item parsing.

* Add `HtmlRenderer.HARD_BREAK` option so that GFM comment mode where soft wraps are turned into
  `<br>` and hard wraps are turned into `<br><br>` could be emulated. Are we having fun yet!?!?

* Fix #6, List items are not properly marked as tight/loose

* Fix #7, Task list items not copying the original list item's tight/loose flag

* Add `CoreNodeRenderer.LOOSE_LIST_ITEM` and `CoreNodeRenderer.TIGHT_LIST_ITEM` instances of
  `AttributablePart` to identify attributes used for generating `<li>` tag for list items.

* Add `TaskListNodeRenderer.TASK_ITEM_PARAGRAPH` `AttributablePart` to identify when a loose
  task list item `<p>` tag attributes are being requested.

* Add `TableRow.rowNumber` in `flexmark-ext-tables` which is the row number within the table
  section. Allowing easy first/even/odd determination for rendering where the browser does not
  have CSS capabilities to handle this.

* Add `TaskListExtension.CONVERT_ORDERED_LIST_ITEMS` option to convert ordered list items to
  task list items, default set to true.

* Change `TaskListItem.isOrderedItem()` added to allow distinguishing ordered from unordered
  task list items.

0.4.12
------

* Add `WikiLinkExtension.DISABLE_RENDERING` option to render wiki links as the text of the node,
  for cases where wiki links are not allowed in the document but for purposes of error
  annotations they should still be parsed.

* Fix `Attributes.getValue(String)` was not checking if the attribute was missing, causing NPE.

* Change rename `HtmlRenderer.LANGUAGE_CLASS_PREFIX` to
  `HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX`

* Add test in `HtmlRenderer.MainNodeRenderer.resolveLink(LinkType, String, Boolean)` for empty
  url in which case no link resolvers are called.

* Fix emoji renderer was not setting image height, width nor align attributes

0.4.11
------

* Fix `SimTocBlockParser` was not including the blank line spacer as part of the `SimTocContent`
  node.

* Change bare `AttributeProvider` in the API to add `AttributeProviderFactory` to allow for
  context based construction of attribute providers and for dependency resolution between
  attribute provider factories. Attribute provider factories that do not define any dependencies
  should extend `IndependentAttributeProviderFactory` which provides defaults so only
  `AttributeProviderFactory.create(NodeRendererContext)` method needs to be implemented.

* Add `NodeAdaptingVisitor`, `NodeAdaptingVisitHandler` and `NodeAdaptedVisitor` to handle
  customized node mapping functions for easier multiplexing from base class Node to specific
  node subclasses.

* Change `Visitor`, `VisitHandler`, `NodeVisitor` to use the new adapting node visitor classes

* Change `CustomNodeRenderer`, `NodeRenderingHandler`, `NodeVisitor` to use the new adapting
  node visitor classes

* Add `LinkResolvingVisitor`, `LinkResolvingHandler` and `LinkResolverAdapter` to allow generic
  link resolving mapping.

* Add Node parameter to `LinkResolver.resolveLink(Node, NodeRendererContext, ResolvedLink)` for
  symmetry and to allow node specific link resolution mapping via `LinkResolverAdapter`.

* Change `WikiLinkExtension.LINK_FILE_EXTENSION` default to `""`

0.4.10
------

* Add `Parser.BLOCK_QUOTE_TO_BLANK_LINE` option, when true block quote block stays open until a
  blank line. Making block quote parsing compatible with GFM and most other markdown processors.

* Fix `ImageRef` and `LinkRef` did not properly set the source information for a dummy reference
  part of a reference `[ref][]`

* Add a bunch of list parsing options to allow mimicking list parsing by various markdown
  implementations:

* Add `Parser.LISTS_ITEM_TYPE_MATCH` when true a new list is started when the list item type
  does not match an existing list type. When false bullet list can contain ordered list items
  and vice versa. In combination with `Parser.LISTS_ITEM_MISMATCH_TO_SUBITEM` allows mimicking
  different parser behavior: kramdown, GFM, Markdown.pl, ...

* Add `Parser.LISTS_ITEM_MISMATCH_TO_SUBITEM` when true a mismatched item is treated as a sub
  item instead of starting a new list. When false a new list will be started.
  :information_source: only applicable if `Parser.LISTS_ITEM_TYPE_MATCH` is true.

* Change `Parser.ORDERED_LIST_DOT_ONLY` to `Parser.LISTS_ORDERED_ITEM_DOT_ONLY`

* Add `Parser.LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH` option, when true a bullet list item can
  interrupt a paragraph. i.e. start without having a blank line before

* Add `Parser.LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH` option, when true a bullet list sub
  item can interrupt the parent item's item text paragraph.

* Change `Parser.ORDERED_LIST_INTERRUPTS_PARAGRAPH` to
  `Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH` option, now controls whether an ordered list
  item can interrupt a paragraph. i.e. can start without having a blank line before.
  `Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH` controls whether this is only true
  for items with 1. prefix, or any ordered item.

* Add `Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH` option, when true an ordered list
  sub item can interrupt the parent item's item text paragraph.
  `Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARENT_ITEM_PARAGRAPH` controls whether this is
  only true for items with 1. prefix, or any ordered item.

* Change `Parser.ORDERED_LIST_START` to `Parser.LISTS_ORDERED_LIST_MANUAL_START`

* Add `Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH` controls whether any ordered item
  can interrupt or only one starting with 1. :information_source: only applies if
  `Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH` is true.

* Add `Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARENT_ITEM_PARAGRAPH` controls whether any
  ordered item can interrupt or only one starting with 1. :information_source: only applies if
  `Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH` is true.

0.4.9
-----

* Fix #3, Incorrect emphasis close marker source offset

0.4.8
-----

* Fix `SimTocBlockParser` to use `Parsing.LINK_TITLE` pattern for TOC title to match what is
  allowed for reference title.

0.4.7
-----

* Change compliance to spec 0.26 with better emphasis delimiter parsing rules and list rules
  without double line breaks and ordered lists don't interrupt paragraphs unless they number
  with 1.

* Add `Parser.ORDERED_LIST_INTERRUPTS_PARAGRAPH` to control whether an ordered list item can
  start without having a blank line before, default true.

* Change `Parser.ORDERED_LIST_START` also controls if an ordered list interrupts a paragraph. If
  ordered list start is set to false, then an ordered list always starts at 1, so it will
  interrupt a paragraph regardless of whether it is `1.` in the source.

* Change `HtmlRenderer.ORDERED_LIST_START` moved to `Parser.ORDERED_LIST_START` since it is
  needed during parsing.

* Add `Parser.ORDERED_SUBITEM_INTERRUPTS_PARENT_ITEM`, when true, even with
  `Parser.ORDERED_LIST_INTERRUPTS_PARAGRAPH` set to false an ordered sub-item will interrupt
  parent's item paragraph.

* Remove `Parser.INLINE_RELAXED_EMPHASIS` since spec 0.26 does the right thing without needing
  an option.

0.4.6
-----

* Change move `Parsing` static strings and patterns into instance fields so that they can be
  changed according to selected options, making it easier to configure parsing patterns.

* Change move all `InlineParserImpl` parsing string and patterns to `Parsing`

* Add `ParserState.getParsing()` to allow block parsers to use the current parsing config from
  the core. Extensions can extend this class for their own option dependent patterns.

* Add `Parser.ORDERED_LIST_DOT_ONLY` to not allow `)` ordered list item delimiter

* Add `Parser.INTELLIJ_DUMMY_IDENTIFIER` to include the `'\u001f'` completion location dummy
  identifier used by Markdown Navigator. Other characters can be easily added if the need
  arises.

* Change `InlineParserImpl.parseCustom(BasedSequence, Node, BitSet, Map<Character,
  CharacterNodeFactory>)` now if a character is in the bit set but the map does not contain a
  node factory then the character will be treated as text and prevent any standard delimiter or
  other inline parsing checking. Can be used by extensions to prevent their special characters
  from being hijacked by other processors.

* Add `RenderingTestCase.IGNORE` and `RenderingTestCase.FAIL` boolean data keys. When
  `RenderingTestCase.getOptions(SpecExample, String)` returns a data set with one of these keys
  set to true then it is treated the same way as if the option string was `IGNORE` or `FAIL`
  respectively. Allows conditional ignore or fail on spec examples in tests.

0.4.5
-----

* Change `TextBase` node now represents text equivalent nodes and can contains `Text` and other
  decorated text nodes processed by extensions. For example `Abbreviation` is really just text
  but is decorated with link like rendering. Similarly, auto links are just text with link
  decorations.

To allow extensions to create such decorated text while allowing contiguous plain text
processing without a lot of code, extensions should replace `Text` nodes that they decorate with
`TextBase` and add their unprocessed text as `Text` nodes under the `TextBase` node, along with
their extension specific decorated nodes with a child `Text` node for the decorated part of the
text. The custom decorated text nodes should also implement `DoNotDecorate` interface so that
other extensions will know not to decorate their text.

If a `Text` node is not a child of `TextBase` then a new instance of `TextBase` should be
created and all undecorated and decorated text nodes should be its children.

Text decoration by extension should always be done on `Text` and never on `TextBase` nodes.

`TextBase` rendering is just rendering of its children. `TextCollectingVisitor` uses the
characters of `TextBase` node and does not descend into its children.

* Add `TypographicExtension` for typographic quotes and smarts, `EscapedCharacterExtension` for
  syntax highlighting escaped characters, `DefinitionExtension` for definition lists. These are
  placeholders for now. Code and tests to be done. For now all plugin required nodes are there.

0.4.4
-----

* Remove all the dependencies between nodes and their visitors, no more global visitor and
  maintaining visitor derived classes or `accept(Visitor)` implementation that requires
  implementation in every non-abstract class.

* Change all extensions to implement a custom node visitor interface which defines
  `VISITOR_HANDLERS` static method taking an instance that implements the visitor interface and
  returns an array of `VisitHandler` which can be passed to `NodeVisitor` constructor along with
  any other visitor handlers as vararg.

Nodes no longer have an `accept()` method. `NodeVisitor` delegate is used for recursive
traversal of the AST. Its generic `NodeVisitor.visit(Node)` can be used to start the visit. This
method will map the actual node class to the `VisitHandler` associated with the given node.

Maintaining the core `Visitor` interface and its derivatives became too much of a pain. Handling
custom nodes is now identical to handling core nodes and the limitation of inheritance other
than from Node has been removed from all nodes.

0.4.3
-----

* Change `LinkResolver` and `LinkResolverFactory` interfaces and registration in HtmlRenderer to
  handle resolving of URLs for links.

* `ResolvedLink` represents the link being resolved. `ResolvedLink.getUrl()` will initially
  return the raw link value from the markdown element. `LinkResolvers` can modify this value
  according to their understanding of the link type and link format. They may or may not change
  the link type and status.

* `LinkType` specifies type of link. Core defines `LinkType.LINK` and `LinkType.IMAGE`,
  extensions can define other types that use different link resolving logic. Wiki link extension
  defines `WikiLinkExtension.WIKI_LINK` type and provides a custom link resolver that will
  convert the wiki link text to a URL and the type to `LinkType.LINK`. It also changes the
  status to `LinkStatus.UNCHECKED`

* `LinkStatus` holds the result of the resolving process. Initial link status is

* `LinkStatus.UNKNOWN`, resolvers are called until status changes to another value.

* `LinkStatus.UNKNOWN` link has not been resolved yet

* `LinkStatus.VALID` link is resolved and valid

* `LinkStatus.UNCHECKED` link is resolved, validity not verified

* `LinkStatus.NOT_FOUND` link is resolved and its target is not found

* Link resolvers are tried until one reports success. They can modify the URL, if available the
  Text, and attributes. The latter is still modifiable by attribute providers at two points:
  right after all resolvers have passed and before final rendering of the link.

* like other processors they have before/after dependencies.

* Encoding is done by the context as the last step if it is requested in options. No URL
  encoding of links which are passed through resolving process.

* Any unresolved link's url is rendered as is.

* Results of resolving a link are cached based on `LinkType` and the initial url text.
  Subsequent requests to resolve the same type and url will return the same instance of
  `ResolvedLink`.

* Add `AttributablePart` that nodes provide when marking a tag
  `HtmlWriter.withAttr(AttributablePart)` so that an attribute provider has information about
  the exact HTML element the node is requesting attributes for. Core only defines:

* `AttributablePart.NODE` a generic placeholder when the node does not specify one

* `AttributablePart.ID` a node's id attribute is being requested

* `AttributablePart.LINK` a node is rendering a link, the `Attributes` parameter will hold an
  attribute named `Attribute.LINK_STATUS` whose value represents the name of the `LinkStatus` of
  the resolved link. Attribute providers can use this value to set specific attributes based on
  the resolved link status. This attribute does not render in the final HTML.

Extensions can and should define parts for specific elements they allow to modify with
extensions.

* Change `AttributeProvider.setAttributes(Node, AttributablePart, Attributes)` to now get an
  attributable part that pinpoints the exact element of the node being rendered, for nodes that
  have many elements capable of having attributes.

* Change `LinkResolver.resolveLink(NodeRendererContext, ResolvedLink)` the context allows the
  resolver to get the node for which this link is being resolved via
  `NodeRendererContext.getCurrentNode()`.

0.4.2
-----

* Add `Attributes` and `Attribute` dedicated classes to handle attributes instead of relying on
  `Map<>`. Allows easier replacing, adding, removing a value from an attribute values, which are
  a space separated list of strings. Determining which ones should not be rendered and when.

* Add `LinkResolver` and `LinkResolverFactory` interfaces and registration in HtmlRenderer to
  handle resolving of URLs for links, including adding attributes.

* Link resolvers are tried until one reports success. They can modify the URL, if available the
  Text, and attributes. The latter is still modifiable by attribute providers at two points:
  right after all resolvers have passed and before final rendering of the link.

* like other processors they have before/after dependencies.

* After all resolvers have handled the link it is passed to AttributeProviders to possibly
  add/remove/change attributes via `AttributeProvider.setAttributes(LinkRendering)` at this
  point if the link was resolved `LinkRendering.getIsResolved()` will return true, null means no
  resolver handled it, it will render as is, false means it does not resolve.

* The Attribute providers will be invoked again on the final link rendering but at this point
  there is no information on whether the link resolved or not but there is final attributes that
  can be manipulated.

* Encoding is done by the context as the last step if it is requested in options. No URL
  encoding of links which are passed through resolving process.

* Any unresolved link is rendered as is.

0.4.1
-----

* Add dependencies to CustomBlockParserFactory and core factories to eliminate the need to order
  factories manually. Custom factories that have no specific dependencies will still run before
  core factories. Core factories now define dependencies between each other to ensure correct
  processing.

* Add `BlockParser.isPropagatingLastBlankLine(BlockParser)` to remove `instanceOf` tests in
  `DocumentParser`, making it agnostic to specific block parsers.

* Add `Node.getLastBlankLineChild()` to remove `instanceOf` tests in `DocumentParser`, making it
  agnostic to specific node types.

* Add Sim TOC syntax as per Markdown Navigator simulated TOC element, with parse and rendering
  options.

* Add Flexmark Spec Example Extension to parse flexmark spec files, same as Markdown Navigator.

0.4.0
-----

* Change Sim TOC to be a container and accept only a single HTML block without blank lines or a
  heading and a list without blank lines.

* Made rendering test case classes usable for other spec based testing, not just flexmark. Too
  useful for testing other parsing implementations to leave it just for flexmark.

* Add Zzzzzz module to test suite so that the archetype also gets to run for sanity testing of
  basic extension module.

* Change spaces in example lead line to NB SP so GitHub can display it as a code fence.

* Add HR after spec front matter so GitHub can think it is Jekyll or YAML front matter.

* Fix all examples were not being recognized due to NB SP change. Now will accept first line
  with NB SP or SP or TABs for whitespace.

* Add exception if an example file results in no examples

* Add FAIL example option to allow sanity tests for failure in specs

0.3.2
-----

* More API rework to make core rendering and extension rendering identical in performance and
  ease of implementation. `NodeRenderer` interface changed. Now it provides a map of node
  classes to `NodeRenderingHandler` instance. In the implementation it becomes a new instance
  creation with class and lambda method reference. The method already has the right node class
  eliminating the need to have a slew of `if (ast instance of ...) do((cast)...)`. Just
  implement a `NodeRenderHandler.render(YourNode, NodeRendererContext, HtmlWriter)` and add an
  entry into the map.

* Add `AbstractCustomVisitor` constructor takes a collection or vararg of `NodeVisitingHandler`
  instances and uses these to map visitation of `CustomNode` and `CustomBlock` to specific
  classes of nodes and their corresponding visit handlers, like custom node rendering allows to
  create custom node class specific `visit()` methods. Similar to `AbstractVisitor` any node
  specific methods not overridden will `visitChildren()` of that node.

* Add `AbstractCustomBlockVisitor` similar to `AbstractCustomVisitor` but will only visit
  children of block elements except `Paragraph`.

0.3.1
-----

* Add ability to prioritize dependents, affects only dependents not constrained by dependencies.

* Change document post processors are run after node post processors unless constrained by
  dependencies provided.

* Add AbstractBlockVisitor, that does not visit children of `Paragraph` or any of the inline
  nodes to allow efficient block collection while ignoring non-blocks.

* Add do not render links condition to `NodeRendererContext` to allow disabling of link
  rendering in children.

* Add `NodeRendererContext` and `HtmlWriter` arguments to `NodeRenderer.render()` method so that
  node renderers are not linked to a specific context or html writer, only document.

* Add `RenderingVisitor` class which handles passing extra rendering parameters to overloaded
  node methods while using the `Visitor.visit()` plumbing.

* Add `NodeRendererContext.getSubContext()` allowing a renderer to get html rendered to be
  processed or inserted as needed. The sub-context has its own html writer and do not render
  links state.

* Add TOC extension as per pegdown, with options to not claim the `[TOC level=#]` line when # is
  not valid: 0, 1, 2. Another option to only use header text for rendering the links, default
  will use inline emphasis and other non-link generated html.

* Change header id generation is now part of the core that can be turned on and used by the
  extensions via `NodeRenderingContext.getNodeId(Node)`, can be null if no id was generated for
  the node in question.

* Add `HtmlIdGenerator` and `HtmlIdGeneratorFactory` to allow extending/replacing the header id
  generator in the core. Default is not to add id's to headers. With appropriate
  `HtmlRenderer.Builder.htmlIdGeneratorFactory()` method to register a custom generator. If id
  generation is enabled but no custom generator is registered then GitHub compatible rules are
  used to generate header ids. These can be overridden by `AttributeProvider` by changing or
  removing the `id` from the map.

* Add `HtmlRenderer.RENDER_HEADER_ID` option, default false. When enabled will render a header
  id attribute for headers using the configured `HtmlIdGenerator`

* Add `HtmlRenderer.GENERATE_HEADER_ID` option, default false. When enabled will generate a
  header id attribute using the configured `HtmlIdGenerator` but not render it. Use this when an
  extension needs a header id, like AnchorLinksExtension and TocExtension.

* Add `HtmlRenderer.DO_NOT_RENDER_LINKS` option, default false. When enabled will disable link
  rendering in the document. This will cause sub-contexts to also have link rendering disabled.

0.3.0
-----

* Add AnchorLinks extension to automatically generate anchor links for headers.

* Add `NodeIterator` and `NodeIterable` and `DescendantNodeIterator` and
  `DescendantNodeIterable` for quick and easy traversal of nodes depth first, both can be
  reversed. Descendant iterator processes parent then children.

* Add `OrderedSet`, `OrderedMap` and `OrderedMultiMap` which combine the functions of set, map
  and the latter of a one to one bi-directional map, i.e. key->value and value->key, allowing
  for either key or value to be null. All of these are iterable and have iterators for indices,
  values, keys, and entries, including reversed and reversible iterators.

Additionally, these have can be in hosted mode, in which they make callbacks on changes allowing
to keep tandem structures in sync. `OrderedMap` and `OrderedMultiMap` use `OrderedSet` in this
way.

* Change document parser to use helper classes for block parser and block pre-processor
  optimizations. Gain in performance for large files is significant. 500k file flexmark-java was
  1.39x longer parse time than commonmark-java, now 1.02x to 1.05x times.

* Add separate abstract classes for node post processors that work on certain nodes and document
  post processors that will traverse the whole AST and potentially return a new document node.
  These also specify which nodes to skip based on class list of ancestors. That way processors
  that should not process text nodes that are part of links can just add `DoNotDecorate.class`
  to the exclusion list.

* Add `` to handle dependency resolution for document post processors and node post processors.

* Add `NodePostProcessor` to support more efficient text node post processing than each post
  processor traversing to post process text nodes instead of each extension traversing the full
  document tree.

0.2.9
-----

* Change `AbstractBlockParserFactory` to require a data holder argument in the constructor so
  that options can be instantiated in `AbstractBlockParser`.

* Change builder to use `PostProcessorFactory` which takes a `Document` argument in create
  allowing creation of document specific post processors that can be re-used on any node of that
  document.

0.2.8
-----

* Add ext-zzzzzz module to hold a skeleton of an extension module, until I complete the plugin
  to handle all the extension configuration and stabilize the API.

* Add project icon

0.2.7
-----

* Add `Parser.LISTS_RELAXED_START` option to allow lists to start only if preceded by a blank
  line, when false. Default true.

* Add `Parser.THEMATIC_BREAK_RELAXED_START` to allow thematic breaks only if preceded by a blank
  line when false. Default true.

* Add Html comment nodes for blocks and inline

* Add separate options for escaping and suppressing comments

0.2.6
-----

* Fix List Option no break out of lists on two blank lines.

* Fix List Option no bullet match for starting a new list.

* Add `blockAdded()` and `removeBlock()` to `ParserState` to allow custom processor created
  blocks to be included in optimization structures used by block pre-processing handling.

* Change ext-table `TableBlock` to use `blockAdded()` method to allow preProcessing of table
  blocks by custom block pre-processors.

0.2.5
-----

* Change built in ignore test option to `IGNORE`. `Parser.THEMATIC_BREAK_RELAXED_START.`

* Change spec files to `.md` extension so that Markdown Navigator could be used to edit it to
  add completions of options, annotations of options and option declarations, structure view and
  formatting.

0.2.4
-----

* Fix Optimized `Escaping.unescape()` to pre-search for `\` or `&` not using regex but dedicated
  character search and if found start processing from that position, to save time. Approximate
  gain of 22% reduction in processing time for a large file (500k) of mostly text.

0.2.3
-----

* Add `BlockPreProcessorFactory` and `BlockPreProcessor` interfaces for block node substitution
  before inline processing.

* Add generic `Dependent`, `DependencyHandler` and `ResolvedDependencies` to handle resolving
  dependencies between processors. Need to add `PostProcessor` dependency declaration so that
  interdependent extensions can automatically control execution order where it matters.

* Add [GFM Task List extension](../../wiki/Extensions#gfm-tasklist)

* Change ext-autolink to use `ComboSpecTestCase`, was the last one left using two test classes.
  This extension needs attention, it has serious impact on parsing performance due to
  un-escaping text and mapping it back to source positions.

* Add separate nodes for `BulletListItem` and `OrderedListItem`

* Fix bug in `ReplacedTextMapper` forgetting to return original index as offset from the start
  of the string. Would return offset into original source.

0.2.2
-----

* Change spec test `options()` to take a comma separated list of option set names and use the
  combined option set of individual sets to run the test case. This is implemented in the
  `RenderingTestCase` and does not require modification of the tests.

* Add `ignore` as one of the `options()` passed. If present the test case will be ignored by
  throwing `AssumptionViolatedException()`. To allow future compatibility tests to remain in the
  spec but be ignored not to pollute the test results.

* Add Heading allow no space after # for atx and do not allow non-indent spaces before heading
  options:

* `Parser.HEADERS_NO_ATX_SPACE`

* `Parser.HEADERS_NO_LEAD_SPACE`

* Add Relaxed inline emphasis parsing option. No code behind the option yet.

* `Parser.INLINE_RELAXED_EMPHASIS`

* Add footnote link class options for footnote ref link and footnote back link:

* `FootnoteExtension.FOOTNOTE_LINK_REF_CLASS`

* `FootnoteExtension.FOOTNOTE_BACK_LINK_REF_CLASS`

0.2.1
-----

* Update integration test to check some basic file parsing

* Move wrap.md parsing from ex-table to integration test so that all extensions could be
  enabled.

* Add unimplemented inline parser option to use GFM emphasis parsing rules,
  `Parser.RELAXED_INLINE_EMPHASIS` boolean options, default false.

0.2.0
-----

* Add: ability for block parsers to store the line indents on a per line basis in the block
  content and optionally the block node. Needed for proper `ParagraphPreProcessor` handling of
  some elements, like tables.

* Remove: storage of end of line length in content blocks. Easily done with `.eolLength()` which
  counts trailing `\r` and `\n`.

* Fix: flexmark-ext-table extension to include each line's indents which exceed the first line's
  indent as part of the table row.

0.1.9
-----

* Change: flexmark-ext-table extension to use paragraph pre-processor interface and perform
  inline parsing on a line of a table before splitting into columns so that pipes embedded in
  inline elements will not be treated as column breaks. Partially complete. Need to preserve
  original line's indentation information in a paragraph block.

0.1.8
-----

* Change: `BlockPreProcessor` to `ParagraphPreProcessor` interface and paragraph pre-processing
  to allow dependencies between paragraph pre-processors and have some pre-processors to be
  executed against all paragraph blocks before running dependents on it. That way document
  global properties like `ReferenceRepository` will be updated before other pre-processors
  dependent on it will be run.

0.1.7
-----

* Fix parser and renderer to store builder copies for re-use in `withOptions()` methods without
  possible side effects from original builder being modified after assignment.

0.1.6
-----

* Add `ComboSpecTestCase` to flexmark-test-util than combines `FullSpecTestCase` and
  `SpecTestCase` functionality so that only one test class needs to be created to get both
  individual spec example results and the full file result.

* Change all tests that used `FullSpecTestCase` and `SpecTestCase` to use `ComboSpecTestCase`
  and modify their ast_spec.txt to use options to test for extension options handling.

* Fix table extension factory to be stateless and use `ParserState::getProperties()` for its
  state storage.

* Update [Writing Extensions](../../wiki/Writing-Extensions) wiki

0.1.5
-----

* Add `Parser.EXTENSIONS` data key to hold a list of Extensions so extensions can be part of the
  universal options passing. Now there is no need to explicitly call `builder().extensions()` if
  you set the extensions key to the list of desired extensions.

* Change spec test related classes to handle option sets specified in the spec per example. That
  way multiple extension/option combinations can be validated within the same spec file,
  eliminating the need to have separate spec file and test classes for each extension/option
  combination.

* Change more tests using multiple test classes and spec files to use the options mechanism. On
  the todo list:

* Wiki link extension with creole vs gfm syntax tests

* Table extension for gfm and non-gfm table testing, need to add tests for various options.

0.1.4
-----

* Remove options argument from flexmark-ext-tables `TablesExtension::create()`

* Fix `ReferenceRepository` to use `Escaping::normalizeReference` for reference normalization.

* Fix `CoreNodeRenderer` to use `SUPPRESS_HTML_BLOCK` instead of `SUPPRESS_INLINE_HTML`

* Add `HtmlWriter::withCondLine()` to make next tag output an EOL only if there is child text
  between the opening and closing tags. Only works for methods that take a `Runnable` argument
  for output child text.

* Fix ext-gfm-tables for incorrect separator line parsing, introduced right before
  ext-gfm-tables and ext-tables split.

* Fix ext-tables to handle multi-line headers or no line headers with options MIN_HEADER_ROWS,
  MAX_HEADER_ROWS.

* Add ext-tables option HEADER_SEPARATOR_COLUMNS, if true will only recognize tables whose
  headers contain no more columns than the separator line. Default false, any number of columns
  in header lines will be accepted.

0.1.3
-----

* Update Emoji extension to use the universal options mechanism.

* Add WikiLink file link extension string option to append to generated links

* Add Suppress HTML in addition to escape option, with separate options for blocks and inline
  html

0.1.2
-----

* Add `MutableData` to be used as a building set for various Parser/Renderer options that all
  extensions can hook into using `PropertyKey` instances. Moved all builder options to use this
  mechanism. Now can set options for all extensions in one place and the extensions can query
  these to get their configuration parameters.

* Add `flexmark-ext-tables` to implement tables per pegdown with GFM limitations configurable in
  options.

* Add spanning columns parsing for flexmark-ext-tables

* Make all core block parser processors optional to allow disabling core functionality.

* Make all core delimiter processors optional to allow disabling core functionality.

* Add `LinkRefProcessor` and associated registration methods for extensions to allow flexible
  parsing of elements that start from link references, such as footnotes `[^]` and wiki links
  `[[]]`. Otherwise, there is no way to properly control generation of link refs and custom
  nodes. Additionally, this allows processing of these nodes during inline parsing instead of
  each extension having to traverse the AST and transform it.

* Change Attribute provider interface to include `tag` being generated since custom nodes can
  have more than one tag which require attributes, for example footnotes.

* Remove previous attribute handling which did not take a `tag` and was done in the node
  rendering code.

* Change `WikiLinkExtension` and `FootnoteExtension` to use `LinkRefProcessor` instead of
  `PostProcessor`.

* Change `SpecReader` to recognize lines starting with EXAMPLE_START, that way each example
  start line can be augmented with section and example number for cross reference to failed
  tests.

* Add section and example number printing to `FullSpecTestCase` for cross referencing to test
  run results.

* Add output of text for delimited nodes if <= 10 then the full string, else 5 chars from start
  and end of the characters to make visual validation easier.

* Fix parsing to make undefined link refs tentative which are replaced by equivalent text if
  they are included in a label of a defined ref or link.

0.1.1
-----

* Add `PhasedNodeRenderer` which is an extension of `NodeRenderer` with additional methods to
  allow rendering for specific parts of the HTML document.

* Add html rendering phases to allow generating for different parts of the document.

* `HEAD_TOP`

* `HEAD`

* `HEAD_CSS`

* `HEAD_SCRIPTS`

* `HEAD_BOTTOM`

* `BODY_TOP`

* `BODY`

* `BODY_BOTTOM`

* `BODY_LOAD_SCRIPTS`

* `BODY_SCRIPTS`

* Add `FootnoteExtension` which converts `[^footnote]` to footnote references and `[^footnote]:
  footnote text` footnote definitions. With referenced footnotes added to the bottom of the
  generated HTML.

* Add a few HtmlWriter methods and enhancements to allow:

* indenting HTML

* methods return `this` so methods could be chained

* invocation with lambda to eliminate the need to close a tag

* `renderChildren()` to eliminate the need for each renderer to roll its own

* `attr(String, String)` method to accumulate attributes to be used on the next `tag()`
  invocation. Eliminating the need to roll your own attribute methods. Accumulated attributes
  are merged, or overwritten by ones passed in as an argument to `tag()`

* Fix `SegmentedSequence::getEndOffset()` for sub-sequences would return end offset of the full
  sequence and not its `subSequence()`.

* Fix footnotes embedded in other footnotes would not be assigned the right source offsets nor
  character sequences.

* Add characters to be dumped as part of the AST for opening and closing sequences for easy
  validation. For some nodes also dump the text.

* Fix table extension to include open/close pipe as part of TableCell node markers

0.1.0
-----

* AST is built based on Nodes in the source not nodes needed for HTML generation. New nodes:
* Reference
* Image
* LinkRef
* ImageRef
* AutoLink
* MailLink
* Emphasis
* StrongEmphasis
* HtmlEntity

Each node has `getChars()` property which returns a BasedSequence character sequence which spans
the contents of the node, with start/end offsets into the original source.

Additionally, each node can provide other `BasedSequence` properties that parcel out pieces of
the node's characters, independent of child node breakdown.

* Add `PropertyHolder` interface to store document global properties for things like references,
  abbreviations, footnotes, or anything else that is parsed from the source.

* Add `NodeRepository<T>` abstract class to make it easier to create collections of nodes
  indexed by a string like one used for references.

* ParserState a few new methods:

* `getPropertyHolder()` returns the property holder for the parse session. This is the current
  document parser. After parsing the property holder is the Document node which can be obtained
  from via `Node::getDocument()` method. Implementation is to traverse node parents until a
  Document node is reached.

* `getInlineParser()` returns the current parse session's inline processor

* `getLine()` and `getLineWithEOL()` return the current line being parsed. With or without the
  EOL.

* `getLineEolLength()` returns the current line's EOL length, usually 1 but can be 2 if `"\r\n"`
  is the current line's sequence.

* Implements `BlockPreProcessor` interface to handle pre-processing of blocks as was done in
  paragraph blocks to remove reference definitions from the beginning of a paragraph block.

* `AbstractBlockParser::closeBlock()` now takes a `ParserState` argument so that any block can
  do processing similar to Paragraph processing of leading References by using the
  `BlockPreProcessor::preProcessBlock()` method.

* Add `Builder::customInlineParserFactory()` method to allow switching of inline parser.

* Add `Builder::blockPreProcessor()` method to allow adding custom processing similar to
  `Reference` processing done previously in `ParagraphParser`.

* `InlineParserImpl` has all previously `private` fields and methods set to `protected` so that
  it can be sub-classed for customizations.

* Special processing in document parser for ParagraphParser removed, now can be done by each
  Parser since ParserState is passed to `closeBlock()` method.

* Special processing of references was removed from `ParagraphParser::closeBlock()` now it is
  done by a call to `ParserState::preProcessBlock()`

* Special processing in document parser for ListParser removed, now it is done in the ListParser
  so that it can be customized.

* `spec.txt` now `ast_spec_txt` with an added section to each example that contains the expected
  AST so that the generated AST can be validated.

````````````````````````````````example
[[*foo* bar]]

 [*foo* bar]: /url "title"
 .
 <p>[<a href="/url" title="title"><em>foo</em> bar</a>]</p>
 .
 Document[0, 41]
   Paragraph[0, 14]
     Text[0, 1]
     LinkRef[1, 12] textOpen:[0, 0] text:[0, 0] textClose:[0, 0] referenceOpen:[1, 2, "["] reference:[2, 11, "*foo* bar"] referenceClose:[11, 12, "]"]
       Emphasis[2, 7] textOpen:[2, 3, "*"] text:[3, 6] textClose:[6, 7, "*"]
         Text[3, 6]
       Text[7, 11]
     Text[12, 13]
   Reference[15, 40] refOpen:[15, 16, "["] ref:[16, 25, "*foo* bar"] refClose:[25, 27, "]:"] urlOpen:[0, 0] url:[28, 32, "/url"] urlClose:[0, 0] titleOpen:[33, 34, """] title:[34, 39, "title"] titleClose:[39, 40, """]
```

* Convert all extension tests to spec.txt style driven testing to make generating tests easier
  and to also test for the generated AST

* Add `FullSpecTestCase` to be sub-classed, this one takes the original spec text file and
  replaces the expected html and ast sections with actual ones, then compares the full text of
  the original file to the generated one. Makes it easy to copy actual and do a diff or paste it
  into the expected document for updating the expected values.

* Add `FullSpecTestCase` derived tests added to all extensions

* Add `flexmark-ext-abbreviation` to implement abbreviations processing. The extension `create`
  function can take an optional `boolean`, which if true will generate HTML abbreviations as `<a
  href="#" title"Abbreviation Expansion Text">Abbreviation</a>`, otherwise will generate `<abbr
  title"Abbreviation Expansion Text">Abbreviation</abbr>`.

````````````````````````````````

