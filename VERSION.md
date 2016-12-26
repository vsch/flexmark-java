flexmark-java
=============

&nbsp;<details id="version-history"><summary>**Version History**</summary>

[TOC]: # ""

- [This Release **To Do List**](#this-release-to-do-list)
- [Next Release To Do List](#next-release-to-do-list)
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


&nbsp;</details>

## This Release **To Do List**

- [ ] Change: Extensions wiki from table format for options to list, easier to maintain and read
      when descriptions can benefit form complex formatting

- [ ] Fix: clean up and verify the Extensions wiki options lists for name changes, missing or
      extra entries. Update description for better understanding.

Next Release To Do List
-----------------------

- [ ] Add: generated HTML element positions to `TagRanges` to allow mapping from source offset
      to HTML offset for the element(s). This is needed to allow synchronization with source
      when using an attribute to hold the source information is not an option.

- Add: PDF renderer

- [ ] Add: Latex extension

- [x] Change: complete parser profiles for variations within a family
      [Markdown Parser Emulation](MarkdownProcessorsEmulation.md).
      - [x] CommonMark
          - [x] GitHub Comments
          - [ ] League/CommonMark
          - [x] CommonMark (default for family)
      - [x] FixedIndent
          - [x] Pegdown (done but needs a profile)
          - [x] MultiMarkdown
          - [ ] Pandoc
      - [x] Kramdown (default for family)
          - [ ] GitHub Docs
          - [ ] Jekyll
          - [x] Kramdown (default for family)
      - [x] Markdown
          - [x] Markdown.pl (default for family)
          - [ ] Php Markdown Extra

0.9.1
-----

- Fix: #27, Abbreviation node not called when 2 abbreviations, was expecting \n even at end of
  file.

- Add: `RenderingTestCase.NO_FILE_EOL` option `NO_FILE_EOL` to test cases which will strip out
  the last EOL of example in a spec to simulate input without trailing EOL. Otherwise all test
  cases had trailing EOL.

0.9.0
-----

- API Change: for `HtmlWriter` now using `FormattingAppendableImpl` to handle output formatting
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

        - `HtmlWriter.raw(String)` will output raw without setting a pre-formatted region, which
          means accumulated spaces before the `raw()` call will be output, which is desired for
          inline html.

        - `HtmlWriter.rawPre(String)` will set a pre-formatted region and output text as is,
          without indents but will output any accumulates spaces before the `rawPre()` call.
          This is desired for real `<pre><code>` output.

        - `HtmlWriter.rawIndentedPre(String)` will also set a pre-formatted region but will set
          a fixed indent at the current indentation level that will be prefixed to all the lines
          and not output any pending white space characters. This is desired for HTML block
          output.

    4. `HtmlWriter.tagIndent(String, Runnable)` will no longer output an end of line after the
       opening tag if there was no lines appended between the opening and closing tag. In which
       case both tags will be on the same line. If you desire to have the tags always split on
       separate lines use the `HtmlWriter.tagLineIndent(String, Runnable)`, which will force the
       closing tag on a new line, even if no lines were appended by child content.

    5. Rendered HTML by default will now have a maximum of 1 blank line, no matter how many are
       output with the data. This is controlled by the `HtmlWriter.flush(int)` call and the
       `HtmlRenderer.render(Node, Appendable, int)` can be used which takes `maxBlankLines` as
       the last argument and controls maximum trailing blank lines that will be appended.

    Changes made:

    - \n in appended text is equivalent to `HtmlWriter.line()` call. Will only terminate a line
      of text, never create blankLines. For blank lines use `HtmlWriter.blankLine()` or
      `HtmlWriter.blankLine(int)`

    - spaces before and after \n are suppressed unless in `preFormatted` region. Before are not
      needed and after are controlled with `HtmlWriter.indent()` and `HtmlWriter.unIndent()`

    - tabs outside of `preFormatted` region are converted to spaces and multiple spaces are
      collapsed to a single space, or eliminated if they come before or after \n. Allowing
      generating clean HTML.

    - all forms of blank lines not generated by calls to `HtmlWriter.blankLine()` or
      `HtmlWriter.blankLine(int)` are suppressed in order to generate properly formatted output
      without extra blank lines.

    - `flush()` or `flush(int)` methods can now be used to control number of trailing blank
      lines in the output. They also need to be called to make sure trailing EOL is output to
      the underlying appendable.

    - now conditional `line()` and `indent()` formatting is available without having to use the
      `HtmlWriter.tag()` with runnable argument for the child element text generation, by using
      the `HtmlWriter.openConditional(ConditionalFormatter)` and
      `HtmlWriter.closeConditional(ConditionalFormatter)` methods giving the parent element
      ability to change indent, add a new line or blank lines, prefix any output before the
      child element's text. Similar ability on closing the conditional formatting region.

    - Add: `HtmlWriter.blankLine()` and `HtmlWriter.blankLine(int)` methods that will add a
      single blank line or a count of blank lines, even when called multiple times without
      intervening output. Ensures a blank lines where needed but no more than the requested
      blank lines.

    - to preserve spaces and EOL in `HtmlWriter.raw()` now temporarily setting pre-formatted
      region for the call. If this gets in the way and you want `raw()` text without HTML
      escapes but without pre-formatting, just use the `append()` methods passed through from
      `FormattingAppendableImpl`, they don't do any escaping but process all other formatting,
      unless in pre-formatting region.

- API Change: clean up of sequences in flexmark-java-util was long overdue. The `BasedSequence`
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

            - assign delimiters for `class` and `style` attribute names that you cannot
              override. The reset will get `NUL` delimiters unless you specify otherwise.

        4. `Attribute` now has a value list delimiter and a value name delimiter, these are used
           to split and combine values. Both can be `Attribute.NUL` then no multiple values can
           be stored or manipulated in the attribute. If value name delimiter is `Attribute.NUL`
           then multiple values are delimited by value list delimiter and considered named
           values without individual item values. For example:

            - `class` attribute has a list delimiter of `' '` and class names can be added
              removed but they have no values associated with them beyond their name.

            - `style` attribute on the other hand has a list delimiter of `';'` and a name
              delimiter of `':'`. Item part before the colon is the name and the part after is
              the value. So you can change individual style's settings using the attribute
              manipulation functions of: `Attribute.removeValue(String)`,
              `Attribute.setValue(String)` which will parse the strings and add/remove/replace
              values. Any item whose value is empty is removed. Any existing item's value is
              change to the new value and any new items are appended at the end.

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

    - Add: `CharSubSequence` implementation of `BasedSequence`

        - uses `char[]` as the base that it accesses directly and creates `subSequence` and
          `baseSubSequence` instances that also access it directly eliminating the nested calls
          through `charAt`

        - converts `\0` to `\uFFFD` on construction eliminating this test on every character
          access.

        - in the future has the potential to support fast `toCharArray()` and related
          optimizations.

    - Remove: all string based `BasedSequence` implementations, now replaced by
      `CharSubSequence`

    - Change: `BasedSequence.getBase()` now returns `Object` to reflect that the underlying
      source of text could be anything.

    - Add: `BasedSequence.getBaseSequence()` which returns a `BasedSequence` of the wrapped full
      source.

    - Change: all sequence classes are now final and constructors private. Use static methods
      `BasedSequenceImpl.of()` or the same of specific classes to get sequences of source. This
      allows optimization based on inputs or when a `subSequence()` of the base is required use
      `BasedSequence.baseSubSequence()` method, which will return an optimized instance where
      possible.

    - Add: documentation to `BasedSequence` interface

- Fix: YouTrack and Jira renderers to remove soft line breaks and to make sure no extra blank
  lines are added after loose items. Loose items had 2 blank lines between them because one was
  added by the paragraph node and the other by the list item.

- Add: added Toc and SimToc options:
    - hierarchy: as before hierarchical list of headings
    - flat: flat list of headings
    - reversed: flat reversed list of headings
    - increasing: flat, alphabetically increasing by heading text
    - decreasing: flat, alphabetically decreasing by heading text

    Extension changes:

    1. new key `TocExtension.LIST_TYPE` takes a `TocOptions.ListType` enum for list generation
       type.

    2. `TocOptions` now supports `Immutable` interface allowing to get a mutable copy whose
       fields can be modified directly with `toMutable()`, and back to `TocOptions` with
       `toImmutable()`.

    3. `SimTocOptions` have been removed. `TocOptions` holds all the options for both the
       regular TOC and simulated TOC.

- Add: `SubscriptExtension` and `StrikethroughSubscriptExtension` extensions to
  `flexmark-ext-gfm-strikethrough` artifact. You guessed it, adds `~subscript~` parsing

- Add: `SuperscriptExtension` to `flexmark-ext-superscript` artifact for `^superscript^`
  parsing.

- Add: `InsExtension` to `flexmark-ext-ins` artifact for `++inserted++` or aka underlined
  parsing.

0.8.0
-----

- Change: remove Java 8 language constructs and reduce language level to 7 for android support.

- Change: remove `Substring` class from sequences and rename `StringSequence` to
  `StringBasedSequence`. `Substring` was duplicating code and not used except in tests.

- Change: move some statics from `BasedSequenceImpl` to `BasedSequence` and add
  `BasedSequence.of()` variations to the latter that check for already being a `BasedSequence`
  and just returning it or if `String` is passed then wrapping it in `StringBasedSequence` and
  if neither then wrap it in `SubSequence`.

- Fix: #21, table column alignment was not taking accumulated spans in the row into account when
  getting alignment for cell from table separator row.

- Fix: strike through extension was not rendering correctly for YouTrack conversion.

- Fix: moved all renderer specific extension tests to their corresponding renderer instead of
  having each extension test for each renderer. Much easier to ensure the extensions are
  properly tested and if any extension has not been properly updated.

- Add: Blank lines to Jira and YouTrack renderer after: heading, thematic break, block quote and
  table.

0.7.0
-----

- Add: YouTrack converter, same as Jira but with a few differences

- Fix: Jira converter not to add an extra blank line after paragraphs in block quotes

- Add: final to all node visitors' node parameter

- Add: upgrade to CommonMark spec 0.27

- Add: option `Parser.PARSE_JEKYLL_MACROS_IN_URLS` which allows any characters to appear between
  `{{` and `}}` in URLs, including spaces, pipes and backslashes.

- Add: option `HTML_COMMENT_BLOCKS_INTERRUPT_PARAGRAPH` with `true` by default but when false
  then they require a blank line before, otherwise they become inline HTML.

- Add: test for `HTML_COMMENT_BLOCKS_INTERRUPT_PARAGRAPH` option

- Add: header id generator options to allow slight customization of heading ids

    - `HtmlRenderer.HEADER_ID_GENERATOR_RESOLVE_DUPES` when set to true adds a `-` with an
      increasing number from 1 to N for each duplicated id generated.

    - `HtmlRenderer.HEADER_ID_GENERATOR_TO_DASH_CHARS` a string of characters in the heading
      text which will be mapped to `-`, alphanumerics are passed as is. Anything else is
      suppressed.

    - `HtmlRenderer.HEADER_ID_GENERATOR_NO_DUPED_DASHES` if set will not generate consecutive
      dashes in the reference ids.

- Change: complete rewrite of list handling and list handling options to allow for Markdown
  parser emulation based on major parser families, as described in
  [Markdown Parser Emulation](MarkdownProcessorsEmulation.md). All major families are done and
  tested.
    - CommonMark
        - GitHub Comments
        - CommonMark (default for family)
    - FixedIndent
        - MultiMarkdown
    - Kramdown (default for family)
        - Kramdown (default for family)
    - Markdown
        - Markdown.pl (default for family)

    No attempt was made to emulate inline processing of these parsers. Only list processing for
    now. Most of the other element discrepancies are already addressable with existing parser
    options.

    :warning: Markdown.pl is emulated fairly well but without its abundant bugs. Use it at your
    own discretion.

- Fix: #19, ArrayIndexOutOfBounds while parsing markdown with backslash as last character of
  text block

- Change: `SpecReader` to parse for headings only if spaces are present after leading `#`.
  Otherwise, leading github issue `#7` in the description is treated as a heading.

- Add: list parsing option `Parser.LISTS_EMPTY_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH` to allow
  an empty list sub-item to be recognized as such. Needed by Markdown Navigator to recognize
  empty sub-items during parsing for list item formatting ops

0.6.1
-----

- Fix: #13, Hard line breaks do not work if markdown text/files uses CR LF as line separator

- Fix: #14, Link reference definitions indented by spaces not recognized

- Fix: #17, IndentedCodeBlock endOffset too large?, Added an option
  `Parser.INDENTED_CODE_NO_TRAILING_BLANK_LINES`

- Fix: #18, Unclosed FencedCodeBlock endOffset too small

- Add test for `Parser.HARD_LINE_BREAK_LIMIT` option

- Change `HtmlRenderer.GENERATE_HEADER_ID` default to true

- Fix #16, jira converter does not include lang= for fenced code with info

- Fix jira converter to output to better handle loose lists, items and options

- Fix jira converter to output an extra blank line after the end of the outermost list

0.6.0
-----

- Add aside extension that behaves as block quotes with `|` marker and generates `<aside>` tags
  instead of block quotes.

- Add multi-line url image links option

- Add `HtmlRenderer.SOURCE_WRAP_HTML` and `HtmlRenderer.SOURCE_WRAP_HTML_BLOCKS` options to wrap
  HTML blocks in `div` with source position information attribute.

- Fix #10, Wrong startOffset in HardLineBreak

- Add `Parser.HARD_LINE_BREAK_LIMIT` false by default, when true only the last 2 spaces of a
  hard line break will be part of the node. The rest are part of the previous text node.

0.5.0
-----

- Add table class option to tables extension

- Add `Parser.BLOCK_QUOTE_IGNORE_BLANK_LINE` option to make block quotes ignore blank lines
  between block quotes. This duplicates pegdown and GFM parsing.

- Add: `HtmlRenderer.TYPE` data key to allow extensions to set rendering type. Default value
  `HTML`.

- Add: `JiraConverterExtension` to convert standard markdown AST to JIRA formatted text

- Add: Rendering type to allow standard non-html rendering capability to be added by extensions.
  For now rendering types are: `HTML` default and `JIRA`. The latter generates JIRA formatted
  text from Markdown AST.

- Change: `HtmlRendererExtension.extend(Builder, String)` now gets a string argument for the
  type of rendering to be desired. If the extension does not recognize the type it should not
  register a renderer. Current values are: `HTML` and `JIRA` others may be added by extensions

- Change: standard extensions that can be mapped to JIRA formatted text to implement JIRA
  renderer.

- Add: `JIRA` rendering for autolink, emoji, strikethrough, tables and wiki link extensions.

0.4.17
------

- Factor out utilities to a separate module to eliminate maven pom cycles

- Prepare for maven release

0.4.16
------

- Fix Toc extension did not generate the right table of contents hierarchy if header levels were
  missing. The indentation is still not perfect but will do for now.

- Add source position attribute to list item tags

- Rename `TablesExtension.HEADER_SEPARATOR_COLUMNS` to
  `TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH` and implement this option in the table parser.

- Fix child paragraphs of list items would not be wrapped in `p` tags if list auto-loose option
  was disabled and the item was not loose

0.4.15
------

- Add `HtmlWriter.srcPosWithTrailingEOL()` that will extend the position information to include
  trailing EOL after then end of the sequence, skipping any spaces or tabs. Used to include EOL
  for closing code fence marker.

- Fix #8, Fenced code closing sequence allows trailing spaces but not tabs. Tab added to ignored
  trailing characters after the closing marker.

0.4.14
------

- Add `HtmlWriter.srcPos()` methods to add source position information to the next `.withAttr()`
  tag.

- Add `HtmlRenderer.SOURCE_POSITION_ATTRIBUTE` the name of the source position HTML attribute so
  set to the source position given in `.srcPos()` and `.srcPosWithEOL()` methods. These behave
  as `.attr()` methods and the source position attribute will be applied to the next tag which
  is preceded by one of `.withAttr()` methods.

- Add `HtmlRenderer.SOURCE_POSITION_PARAGRAPH_LINES` if true then paragraph source lines will be
  wrapped in `<span></span>` with source position information for the line. Works even for tight
  list items that do not generate a `<p>` wrapper for their text. :warning: Only works if source
  position attribute is set to non-empty value.

- Add `AttributablePart` instances:
    - `CoreNodeRenderer.CODE_CONTENT` to mark the `code` tag part of fenced code and indented
      code
    - `CoreNodeRenderer.PARAGRAPH_LINE` to mark line spans of paragraphs source positions, list
      items or any other text block supporting lazy continuation.

- Refine source position attribute generation to make highlighting HTML elements from source
  position information more intuitive.

0.4.13
------

- Fix #5, SimToc did not unescape the title string when rendering

- Add `EmojiExtension.ATTR_IMAGE_SIZE` and `EmojiExtension.ATTR_ALIGN` options to control image
  size and align attributes for rendering

- Change rename `RenderAs.SIMPLE` to `RenderAs.SECTIONS`

- Change `SpecExampleNodeRenderer` for `RenderAs.SECTIONS` option to render the spec example
  section and number if it is available as `h5`, also only render `hr` tag if the section text
  is not empty.

- Add `Parser.LISTS_LOOSE_ON_PREV_LOOSE_ITEM` option to set the next list item as loose if
  previous one was loose. This makes list items mimic GFM quirky (diplomatic way of saying
  buggy) list item parsing.

- Add `HtmlRenderer.HARD_BREAK` option so that GFM comment mode where soft wraps are turned into
  `<br>` and hard wraps are turned into `<br><br>` could be emulated. Are we having fun yet!?!?

- Fix #6, List items are not properly marked as tight/loose

- Fix #7, Task list items not copying the original list item's tight/loose flag

- Add `CoreNodeRenderer.LOOSE_LIST_ITEM` and `CoreNodeRenderer.TIGHT_LIST_ITEM` instances of
  `AttributablePart` to identify attributes used for generating `<li>` tag for list items.

- Add `TaskListNodeRenderer.TASK_ITEM_PARAGRAPH` `AttributablePart` to identify when a loose
  task list item `<p>` tag attributes are being requested.

- Add `TableRow.rowNumber` in `flexmark-ext-tables` which is the row number within the table
  section. Allowing easy first/even/odd determination for rendering where the browser does not
  have CSS capabilities to handle this.

- Add `TaskListExtension.CONVERT_ORDERED_LIST_ITEMS` option to convert ordered list items to
  task list items, default set to true.

- Change `TaskListItem.isOrderedItem()` added to allow distinguishing ordered from unordered
  task list items.

0.4.12
------

- Add `WikiLinkExtension.DISABLE_RENDERING` option to render wiki links as the text of the node,
  for cases where wiki links are not allowed in the document but for purposes of error
  annotations they should still be parsed.

- Fix `Attributes.getValue(String)` was not checking if the attribute was missing, causing NPE.

- Change rename `HtmlRenderer.LANGUAGE_CLASS_PREFIX` to
  `HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX`

- Add test in `HtmlRenderer.MainNodeRenderer.resolveLink(LinkType, String, Boolean)` for empty
  url in which case no link resolvers are called.

- Fix emoji renderer was not setting image height, width nor align attributes

0.4.11
------

- Fix `SimTocBlockParser` was not including the blank line spacer as part of the `SimTocContent`
  node.

- Change bare `AttributeProvider` in the API to add `AttributeProviderFactory` to allow for
  context based construction of attribute providers and for dependency resolution between
  attribute provider factories. Attribute provider factories that do not define any dependencies
  should extend `IndependentAttributeProviderFactory` which provides defaults so only
  `AttributeProviderFactory.create(NodeRendererContext)` method needs to be implemented.

- Add `NodeAdaptingVisitor`, `NodeAdaptingVisitHandler` and `NodeAdaptedVisitor` to handle
  customized node mapping functions for easier multiplexing from base class Node to specific
  node subclasses.

- Change `Visitor`, `VisitHandler`, `NodeVisitor` to use the new adapting node visitor classes

- Change `CustomNodeRenderer`, `NodeRenderingHandler`, `NodeVisitor` to use the new adapting
  node visitor classes

- Add `LinkResolvingVisitor`, `LinkResolvingHandler` and `LinkResolverAdapter` to allow generic
  link resolving mapping.

- Add Node parameter to `LinkResolver.resolveLink(Node, NodeRendererContext, ResolvedLink)` for
  symmetry and to allow node specific link resolution mapping via `LinkResolverAdapter`.

- Change `WikiLinkExtension.LINK_FILE_EXTENSION` default to `""`

0.4.10
------

- Add `Parser.BLOCK_QUOTE_TO_BLANK_LINE` option, when true block quote block stays open until a
  blank line. Making block quote parsing compatible with GFM and most other markdown processors.

- Fix `ImageRef` and `LinkRef` did not properly set the source information for a dummy reference
  part of a reference `[ref][]`

- Add a bunch of list parsing options to allow mimicking list parsing by various markdown
  implementations:

    - Add `Parser.LISTS_ITEM_TYPE_MATCH` when true a new list is started when the list item type
      does not match an existing list type. When false bullet list can contain ordered list
      items and vice versa. In combination with `Parser.LISTS_ITEM_MISMATCH_TO_SUBITEM` allows
      mimicking different parser behavior: kramdown, GFM, Markdown.pl, ...

    - Add `Parser.LISTS_ITEM_MISMATCH_TO_SUBITEM` when true a mismatched item is treated as a
      sub item instead of starting a new list. When false a new list will be started.
      :information_source: only applicable if `Parser.LISTS_ITEM_TYPE_MATCH` is true.

    - Change `Parser.ORDERED_LIST_DOT_ONLY` to `Parser.LISTS_ORDERED_ITEM_DOT_ONLY`

    - Add `Parser.LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH` option, when true a bullet list item
      can interrupt a paragraph. i.e. start without having a blank line before

    - Add `Parser.LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH` option, when true a bullet list
      sub item can interrupt the parent item's item text paragraph.

    - Change `Parser.ORDERED_LIST_INTERRUPTS_PARAGRAPH` to
      `Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH` option, now controls whether an ordered
      list item can interrupt a paragraph. i.e. can start without having a blank line before.
      `Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH` controls whether this is only
      true for items with 1. prefix, or any ordered item.

    - Add `Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH` option, when true an ordered
      list sub item can interrupt the parent item's item text paragraph.
      `Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARENT_ITEM_PARAGRAPH` controls whether this
      is only true for items with 1. prefix, or any ordered item.

    - Change `Parser.ORDERED_LIST_START` to `Parser.LISTS_ORDERED_LIST_MANUAL_START`

    - Add `Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH` controls whether any ordered
      item can interrupt or only one starting with 1. :information_source: only applies if
      `Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH` is true.

    - Add `Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARENT_ITEM_PARAGRAPH` controls whether
      any ordered item can interrupt or only one starting with 1. :information_source: only
      applies if `Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH` is true.

0.4.9
-----

- Fix #3, Incorrect emphasis close marker source offset

0.4.8
-----

- Fix `SimTocBlockParser` to use `Parsing.LINK_TITLE` pattern for TOC title to match what is
  allowed for reference title.

0.4.7
-----

- Change compliance to spec 0.26 with better emphasis delimiter parsing rules and list rules
  without double line breaks and ordered lists don't interrupt paragraphs unless they number
  with 1.

- Add `Parser.ORDERED_LIST_INTERRUPTS_PARAGRAPH` to control whether an ordered list item can
  start without having a blank line before, default true.

- Change `Parser.ORDERED_LIST_START` also controls if an ordered list interrupts a paragraph. If
  ordered list start is set to false, then an ordered list always starts at 1, so it will
  interrupt a paragraph regardless of whether it is `1.` in the source.

- Change `HtmlRenderer.ORDERED_LIST_START` moved to `Parser.ORDERED_LIST_START` since it is
  needed during parsing.

- Add `Parser.ORDERED_SUBITEM_INTERRUPTS_PARENT_ITEM`, when true, even with
  `Parser.ORDERED_LIST_INTERRUPTS_PARAGRAPH` set to false an ordered sub-item will interrupt
  parent's item paragraph.

- Remove `Parser.INLINE_RELAXED_EMPHASIS` since spec 0.26 does the right thing without needing
  an option.

0.4.6
-----

- Change move `Parsing` static strings and patterns into instance fields so that they can be
  changed according to selected options, making it easier to configure parsing patterns.

- Change move all `InlineParserImpl` parsing string and patterns to `Parsing`

- Add `ParserState.getParsing()` to allow block parsers to use the current parsing config from
  the core. Extensions can extend this class for their own option dependent patterns.

- Add `Parser.ORDERED_LIST_DOT_ONLY` to not allow `)` ordered list item delimiter

- Add `Parser.INTELLIJ_DUMMY_IDENTIFIER` to include the `'\u001f'` completion location dummy
  identifier used by Markdown Navigator. Other characters can be easily added if the need
  arises.

- Change `InlineParserImpl.parseCustom(BasedSequence, Node, BitSet, Map<Character,
  CharacterNodeFactory>)` now if a character is in the bit set but the map does not contain a
  node factory then the character will be treated as text and prevent any standard delimiter or
  other inline parsing checking. Can be used by extensions to prevent their special characters
  from being hijacked by other processors.

- Add `RenderingTestCase.IGNORE` and `RenderingTestCase.FAIL` boolean data keys. When
  `RenderingTestCase.getOptions(SpecExample, String)` returns a data set with one of these keys
  set to true then it is treated the same way as if the option string was `IGNORE` or `FAIL`
  respectively. Allows conditional ignore or fail on spec examples in tests.

0.4.5
-----

- Change `TextBase` node now represents text equivalent nodes and can contains `Text` and other
  decorated text nodes processed by extensions. For example `Abbreviation` is really just text
  but is decorated with link like rendering. Similarly, auto links are just text with link
  decorations.

    To allow extensions to create such decorated text while allowing contiguous plain text
    processing without a lot of code, extensions should replace `Text` nodes that they decorate
    with `TextBase` and add their unprocessed text as `Text` nodes under the `TextBase` node,
    along with their extension specific decorated nodes with a child `Text` node for the
    decorated part of the text. The custom decorated text nodes should also implement
    `DoNotDecorate` interface so that other extensions will know not to decorate their text.

    If a `Text` node is not a child of `TextBase` then a new instance of `TextBase` should be
    created and all undecorated and decorated text nodes should be its children.

    Text decoration by extension should always be done on `Text` and never on `TextBase` nodes.

    `TextBase` rendering is just rendering of its children. `TextCollectingVisitor` uses the
    characters of `TextBase` node and does not descend into its children.

- Add `TypographicExtension` for typographic quotes and smarts, `EscapedCharacterExtension` for
  syntax highlighting escaped characters, `DefinitionExtension` for definition lists. These are
  placeholders for now. Code and tests to be done. For now all plugin required nodes are there.

0.4.4
-----

- Remove all the dependencies between nodes and their visitors, no more global visitor and
  maintaining visitor derived classes or `accept(Visitor)` implementation that requires
  implementation in every non-abstract class.

- Change all extensions to implement a custom node visitor interface which defines
  `VISITOR_HANDLERS` static method taking an instance that implements the visitor interface and
  returns an array of `VisitHandler` which can be passed to `NodeVisitor` constructor along with
  any other visitor handlers as vararg.

    Nodes no longer have an `accept()` method. `NodeVisitor` delegate is used for recursive
    traversal of the AST. Its generic `NodeVisitor.visit(Node)` can be used to start the visit.
    This method will map the actual node class to the `VisitHandler` associated with the given
    node.

    Maintaining the core `Visitor` interface and its derivatives became too much of a pain.
    Handling custom nodes is now identical to handling core nodes and the limitation of
    inheritance other than from Node has been removed from all nodes.

0.4.3
-----

- Change `LinkResolver` and `LinkResolverFactory` interfaces and registration in HtmlRenderer to
  handle resolving of URLs for links.

    - `ResolvedLink` represents the link being resolved. `ResolvedLink.getUrl()` will initially
      return the raw link value from the markdown element. `LinkResolvers` can modify this value
      according to their understanding of the link type and link format. They may or may not
      change the link type and status.

    - `LinkType` specifies type of link. Core defines `LinkType.LINK` and `LinkType.IMAGE`,
      extensions can define other types that use different link resolving logic. Wiki link
      extension defines `WikiLinkExtension.WIKI_LINK` type and provides a custom link resolver
      that will convert the wiki link text to a URL and the type to `LinkType.LINK`. It also
      changes the status to `LinkStatus.UNCHECKED`

    - `LinkStatus` holds the result of the resolving process. Initial link status is
      `LinkStatus.UNKNOWN`, resolvers are called until status changes to another value.
        - `LinkStatus.UNKNOWN` link has not been resolved yet
        - `LinkStatus.VALID` link is resolved and valid
        - `LinkStatus.UNCHECKED` link is resolved, validity not verified
        - `LinkStatus.NOT_FOUND` link is resolved and its target is not found

    - Link resolvers are tried until one reports success. They can modify the URL, if available
      the Text, and attributes. The latter is still modifiable by attribute providers at two
      points: right after all resolvers have passed and before final rendering of the link.

    - like other processors they have before/after dependencies.

    - Encoding is done by the context as the last step if it is requested in options. No URL
      encoding of links which are passed through resolving process.

    - Any unresolved link's url is rendered as is.

    - Results of resolving a link are cached based on `LinkType` and the initial url text.
      Subsequent requests to resolve the same type and url will return the same instance of
      `ResolvedLink`.

- Add `AttributablePart` that nodes provide when marking a tag
  `HtmlWriter.withAttr(AttributablePart)` so that an attribute provider has information about
  the exact HTML element the node is requesting attributes for. Core only defines:
    - `AttributablePart.NODE` a generic placeholder when the node does not specify one
    - `AttributablePart.ID` a node's id attribute is being requested
    - `AttributablePart.LINK` a node is rendering a link, the `Attributes` parameter will hold
      an attribute named `Attribute.LINK_STATUS` whose value represents the name of the
      `LinkStatus` of the resolved link. Attribute providers can use this value to set specific
      attributes based on the resolved link status. This attribute does not render in the final
      HTML.
    - Extensions can and should define parts for specific elements they allow to modify with
      extensions.

- Change `AttributeProvider.setAttributes(Node, AttributablePart, Attributes)` to now get an
  attributable part that pinpoints the exact element of the node being rendered, for nodes that
  have many elements capable of having attributes.

- Change `LinkResolver.resolveLink(NodeRendererContext, ResolvedLink)` the context allows the
  resolver to get the node for which this link is being resolved via
  `NodeRendererContext.getCurrentNode()`.

0.4.2
-----

- Add `Attributes` and `Attribute` dedicated classes to handle attributes instead of relying on
  `Map<>`. Allows easier replacing, adding, removing a value from an attribute values, which are
  a space separated list of strings. Determining which ones should not be rendered and when.

- Add `LinkResolver` and `LinkResolverFactory` interfaces and registration in HtmlRenderer to
  handle resolving of URLs for links, including adding attributes.

    - Link resolvers are tried until one reports success. They can modify the URL, if available
      the Text, and attributes. The latter is still modifiable by attribute providers at two
      points: right after all resolvers have passed and before final rendering of the link.

    - like other processors they have before/after dependencies.

    - After all resolvers have handled the link it is passed to AttributeProviders to possibly
      add/remove/change attributes via `AttributeProvider.setAttributes(LinkRendering)` at this
      point if the link was resolved `LinkRendering.getIsResolved()` will return true, null
      means no resolver handled it, it will render as is, false means it does not resolve.

    - The Attribute providers will be invoked again on the final link rendering but at this
      point there is no information on whether the link resolved or not but there is final
      attributes that can be manipulated.

    - Encoding is done by the context as the last step if it is requested in options. No URL
      encoding of links which are passed through resolving process.

    - Any unresolved link is rendered as is.

0.4.1
-----

- Add dependencies to CustomBlockParserFactory and core factories to eliminate the need to order
  factories manually. Custom factories that have no specific dependencies will still run before
  core factories. Core factories now define dependencies between each other to ensure correct
  processing.

- Add `BlockParser.isPropagatingLastBlankLine(BlockParser)` to remove `instanceOf` tests in
  `DocumentParser`, making it agnostic to specific block parsers.

- Add `Node.getLastBlankLineChild()` to remove `instanceOf` tests in `DocumentParser`, making it
  agnostic to specific node types.

- Add Sim TOC syntax as per Markdown Navigator simulated TOC element, with parse and rendering
  options.

- Add Flexmark Spec Example Extension to parse flexmark spec files, same as Markdown Navigator.

0.4.0
-----

- Change Sim TOC to be a container and accept only a single HTML block without blank lines or a
  heading and a list without blank lines.

- Made rendering test case classes usable for other spec based testing, not just flexmark. Too
  useful for testing other parsing implementations to leave it just for flexmark.

- Add Zzzzzz module to test suite so that the archetype also gets to run for sanity testing of
  basic extension module.

- Change spaces in example lead line to NB SP so GitHub can display it as a code fence.

- Add HR after spec front matter so GitHub can think it is Jekyll or YAML front matter.

- Fix all examples were not being recognized due to NB SP change. Now will accept first line
  with NB SP or SP or TABs for whitespace.

- Add exception if an example file results in no examples

- Add FAIL example option to allow sanity tests for failure in specs

0.3.2
-----

- More API rework to make core rendering and extension rendering identical in performance and
  ease of implementation. `NodeRenderer` interface changed. Now it provides a map of node
  classes to `NodeRenderingHandler` instance. In the implementation it becomes a new instance
  creation with class and lambda method reference. The method already has the right node class
  eliminating the need to have a slew of `if (ast instance of ...) do((cast)...)`. Just
  implement a `NodeRenderHandler.render(YourNode, NodeRendererContext, HtmlWriter)` and add an
  entry into the map.

- Add `AbstractCustomVisitor` constructor takes a collection or vararg of `NodeVisitingHandler`
  instances and uses these to map visitation of `CustomNode` and `CustomBlock` to specific
  classes of nodes and their corresponding visit handlers, like custom node rendering allows to
  create custom node class specific `visit()` methods. Similar to `AbstractVisitor` any node
  specific methods not overridden will `visitChildren()` of that node.

- Add `AbstractCustomBlockVisitor` similar to `AbstractCustomVisitor` but will only visit
  children of block elements except `Paragraph`.

0.3.1
-----

- Add ability to prioritize dependents, affects only dependents not constrained by dependencies.

- Change document post processors are run after node post processors unless constrained by
  dependencies provided.

- Add AbstractBlockVisitor, that does not visit children of `Paragraph` or any of the inline
  nodes to allow efficient block collection while ignoring non-blocks.

- Add do not render links condition to `NodeRendererContext` to allow disabling of link
  rendering in children.

- Add `NodeRendererContext` and `HtmlWriter` arguments to `NodeRenderer.render()` method so that
  node renderers are not linked to a specific context or html writer, only document.

- Add `RenderingVisitor` class which handles passing extra rendering parameters to overloaded
  node methods while using the `Visitor.visit()` plumbing.

- Add `NodeRendererContext.getSubContext()` allowing a renderer to get html rendered to be
  processed or inserted as needed. The sub-context has its own html writer and do not render
  links state.

- Add TOC extension as per pegdown, with options to not claim the `[TOC level=#]` line when # is
  not valid: 0, 1, 2. Another option to only use header text for rendering the links, default
  will use inline emphasis and other non-link generated html.

- Change header id generation is now part of the core that can be turned on and used by the
  extensions via `NodeRenderingContext.getNodeId(Node)`, can be null if no id was generated for
  the node in question.

- Add `HtmlIdGenerator` and `HtmlIdGeneratorFactory` to allow extending/replacing the header id
  generator in the core. Default is not to add id's to headers. With appropriate
  `HtmlRenderer.Builder.htmlIdGeneratorFactory()` method to register a custom generator. If id
  generation is enabled but no custom generator is registered then GitHub compatible rules are
  used to generate header ids. These can be overridden by `AttributeProvider` by changing or
  removing the `id` from the map.

- Add `HtmlRenderer.RENDER_HEADER_ID` option, default false. When enabled will render a header
  id attribute for headers using the configured `HtmlIdGenerator`

- Add `HtmlRenderer.GENERATE_HEADER_ID` option, default false. When enabled will generate a
  header id attribute using the configured `HtmlIdGenerator` but not render it. Use this when an
  extension needs a header id, like AnchorLinksExtension and TocExtension.

- Add `HtmlRenderer.DO_NOT_RENDER_LINKS` option, default false. When enabled will disable link
  rendering in the document. This will cause sub-contexts to also have link rendering disabled.

0.3.0
-----

- Add AnchorLinks extension to automatically generate anchor links for headers.

- Add `NodeIterator` and `NodeIterable` and `DescendantNodeIterator` and
  `DescendantNodeIterable` for quick and easy traversal of nodes depth first, both can be
  reversed. Descendant iterator processes parent then children.

- Add `OrderedSet`, `OrderedMap` and `OrderedMultiMap` which combine the functions of set, map
  and the latter of a one to one bi-directional map, i.e. key->value and value->key, allowing
  for either key or value to be null. All of these are iterable and have iterators for indices,
  values, keys, and entries, including reversed and reversible iterators.

    Additionally, these have can be in hosted mode, in which they make callbacks on changes
    allowing to keep tandem structures in sync. `OrderedMap` and `OrderedMultiMap` use
    `OrderedSet` in this way.

- Change document parser to use helper classes for block parser and block pre-processor
  optimizations. Gain in performance for large files is significant. 500k file flexmark-java was
  1.39x longer parse time than commonmark-java, now 1.02x to 1.05x times.

- Add separate abstract classes for node post processors that work on certain nodes and document
  post processors that will traverse the whole AST and potentially return a new document node.
  These also specify which nodes to skip based on class list of ancestors. That way processors
  that should not process text nodes that are part of links can just add `DoNotDecorate.class`
  to the exclusion list.

- Add `` to handle dependency resolution for document post processors and node post processors.

- Add `NodePostProcessor` to support more efficient text node post processing than each post
  processor traversing to post process text nodes instead of each extension traversing the full
  document tree.

0.2.9
-----

- Change `AbstractBlockParserFactory` to require a data holder argument in the constructor so
  that options can be instantiated in `AbstractBlockParser`.

- Change builder to use `PostProcessorFactory` which takes a `Document` argument in create
  allowing creation of document specific post processors that can be re-used on any node of that
  document.

0.2.8
-----

- Add ext-zzzzzz module to hold a skeleton of an extension module, until I complete the plugin
  to handle all the extension configuration and stabilize the API.

- Add project icon

0.2.7
-----

- Add `Parser.LISTS_RELAXED_START` option to allow lists to start only if preceded by a blank
  line, when false. Default true.

- Add `Parser.THEMATIC_BREAK_RELAXED_START` to allow thematic breaks only if preceded by a blank
  line when false. Default true.

- Add Html comment nodes for blocks and inline

- Add separate options for escaping and suppressing comments

0.2.6
-----

- Fix List Option no break out of lists on two blank lines.

- Fix List Option no bullet match for starting a new list.

- Add `blockAdded()` and `removeBlock()` to `ParserState` to allow custom processor created
  blocks to be included in optimization structures used by block pre-processing handling.

- Change ext-table `TableBlock` to use `blockAdded()` method to allow preProcessing of table
  blocks by custom block pre-processors.

0.2.5
-----

- Change built in ignore test option to `IGNORE`. `Parser.THEMATIC_BREAK_RELAXED_START.`

- Change spec files to `.md` extension so that Markdown Navigator could be used to edit it to
  add completions of options, annotations of options and option declarations, structure view and
  formatting.

0.2.4
-----

- Fix Optimized `Escaping.unescape()` to pre-search for `\` or `&` not using regex but dedicated
  character search and if found start processing from that position, to save time. Approximate
  gain of 22% reduction in processing time for a large file (500k) of mostly text.

0.2.3
-----

- Add `BlockPreProcessorFactory` and `BlockPreProcessor` interfaces for block node substitution
  before inline processing.

- Add generic `Dependent`, `DependencyHandler` and `ResolvedDependencies` to handle resolving
  dependencies between processors. Need to add `PostProcessor` dependency declaration so that
  interdependent extensions can automatically control execution order where it matters.

- Add [GFM Task List extension](../../wiki/Extensions#gfm-tasklist)

- Change ext-autolink to use `ComboSpecTestCase`, was the last one left using two test classes.
  This extension needs attention, it has serious impact on parsing performance due to
  un-escaping text and mapping it back to source positions.

- Add separate nodes for `BulletListItem` and `OrderedListItem`

- Fix bug in `ReplacedTextMapper` forgetting to return original index as offset from the start
  of the string. Would return offset into original source.

0.2.2
-----

- Change spec test `options()` to take a comma separated list of option set names and use the
  combined option set of individual sets to run the test case. This is implemented in the
  `RenderingTestCase` and does not require modification of the tests.

- Add `ignore` as one of the `options()` passed. If present the test case will be ignored by
  throwing `AssumptionViolatedException()`. To allow future compatibility tests to remain in the
  spec but be ignored not to pollute the test results.

- Add Heading allow no space after # for atx and do not allow non-indent spaces before heading
  options:
    - `Parser.HEADERS_NO_ATX_SPACE`
    - `Parser.HEADERS_NO_LEAD_SPACE`

- Add Relaxed inline emphasis parsing option. No code behind the option yet.
    - `Parser.INLINE_RELAXED_EMPHASIS`

- Add footnote link class options for footnote ref link and footnote back link:
    - `FootnoteExtension.FOOTNOTE_LINK_REF_CLASS`
    - `FootnoteExtension.FOOTNOTE_BACK_LINK_REF_CLASS`

0.2.1
-----

- Update integration test to check some basic file parsing

- Move wrap.md parsing from ex-table to integration test so that all extensions could be
  enabled.

- Add unimplemented inline parser option to use GFM emphasis parsing rules,
  `Parser.RELAXED_INLINE_EMPHASIS` boolean options, default false.

0.2.0
-----

- Add: ability for block parsers to store the line indents on a per line basis in the block
  content and optionally the block node. Needed for proper `ParagraphPreProcessor` handling of
  some elements, like tables.

- Remove: storage of end of line length in content blocks. Easily done with `.eolLength()` which
  counts trailing `\r` and `\n`.

- Fix: flexmark-ext-table extension to include each line's indents which exceed the first line's
  indent as part of the table row.

0.1.9
-----

- Change: flexmark-ext-table extension to use paragraph pre-processor interface and perform
  inline parsing on a line of a table before splitting into columns so that pipes embedded in
  inline elements will not be treated as column breaks. Partially complete. Need to preserve
  original line's indentation information in a paragraph block.

0.1.8
-----

- Change: `BlockPreProcessor` to `ParagraphPreProcessor` interface and paragraph pre-processing
  to allow dependencies between paragraph pre-processors and have some pre-processors to be
  executed against all paragraph blocks before running dependents on it. That way document
  global properties like `ReferenceRepository` will be updated before other pre-processors
  dependent on it will be run.

0.1.7
-----

- Fix parser and renderer to store builder copies for re-use in `withOptions()` methods without
  possible side effects from original builder being modified after assignment.

0.1.6
-----

- Add `ComboSpecTestCase` to flexmark-test-util than combines `FullSpecTestCase` and
  `SpecTestCase` functionality so that only one test class needs to be created to get both
  individual spec example results and the full file result.

- Change all tests that used `FullSpecTestCase` and `SpecTestCase` to use `ComboSpecTestCase`
  and modify their ast_spec.txt to use options to test for extension options handling.

- Fix table extension factory to be stateless and use `ParserState::getProperties()` for its
  state storage.

- Update [Writing Extensions](../../wiki/Writing-Extensions) wiki

0.1.5
-----

- Add `Parser.EXTENSIONS` data key to hold a list of Extensions so extensions can be part of the
  universal options passing. Now there is no need to explicitly call `builder().extensions()` if
  you set the extensions key to the list of desired extensions.

- Change spec test related classes to handle option sets specified in the spec per example. That
  way multiple extension/option combinations can be validated within the same spec file,
  eliminating the need to have separate spec file and test classes for each extension/option
  combination.

- Change more tests using multiple test classes and spec files to use the options mechanism. On
  the todo list:
    - Wiki link extension with creole vs gfm syntax tests
    - Table extension for gfm and non-gfm table testing, need to add tests for various options.

0.1.4
-----

- Remove options argument from flexmark-ext-tables `TablesExtension::create()`

- Fix `ReferenceRepository` to use `Escaping::normalizeReference` for reference normalization.

- Fix `CoreNodeRenderer` to use `SUPPRESS_HTML_BLOCK` instead of `SUPPRESS_INLINE_HTML`

- Add `HtmlWriter::withCondLine()` to make next tag output an EOL only if there is child text
  between the opening and closing tags. Only works for methods that take a `Runnable` argument
  for output child text.

- Fix ext-gfm-tables for incorrect separator line parsing, introduced right before
  ext-gfm-tables and ext-tables split.

- Fix ext-tables to handle multi-line headers or no line headers with options MIN_HEADER_ROWS,
  MAX_HEADER_ROWS.

- Add ext-tables option HEADER_SEPARATOR_COLUMNS, if true will only recognize tables whose
  headers contain no more columns than the separator line. Default false, any number of columns
  in header lines will be accepted.

0.1.3
-----

- Update Emoji extension to use the universal options mechanism.

- Add WikiLink file link extension string option to append to generated links

- Add Suppress HTML in addition to escape option, with separate options for blocks and inline
  html

0.1.2
-----

- Add `MutableData` to be used as a building set for various Parser/Renderer options that all
  extensions can hook into using `PropertyKey` instances. Moved all builder options to use this
  mechanism. Now can set options for all extensions in one place and the extensions can query
  these to get their configuration parameters.

- Add `flexmark-ext-tables` to implement tables per pegdown with GFM limitations configurable in
  options.

- Add spanning columns parsing for flexmark-ext-tables

- Make all core block parser processors optional to allow disabling core functionality.

- Make all core delimiter processors optional to allow disabling core functionality.

- Add `LinkRefProcessor` and associated registration methods for extensions to allow flexible
  parsing of elements that start from link references, such as footnotes `[^]` and wiki links
  `[[]]`. Otherwise, there is no way to properly control generation of link refs and custom
  nodes. Additionally, this allows processing of these nodes during inline parsing instead of
  each extension having to traverse the AST and transform it.

- Change Attribute provider interface to include `tag` being generated since custom nodes can
  have more than one tag which require attributes, for example footnotes.

- Remove previous attribute handling which did not take a `tag` and was done in the node
  rendering code.

- Change `WikiLinkExtension` and `FootnoteExtension` to use `LinkRefProcessor` instead of
  `PostProcessor`.

- Change `SpecReader` to recognize lines starting with EXAMPLE_START, that way each example
  start line can be augmented with section and example number for cross reference to failed
  tests.

- Add section and example number printing to `FullSpecTestCase` for cross referencing to test
  run results.

- Add output of text for delimited nodes if <= 10 then the full string, else 5 chars from start
  and end of the characters to make visual validation easier.

- Fix parsing to make undefined link refs tentative which are replaced by equivalent text if
  they are included in a label of a defined ref or link.

0.1.1
-----

- Add `PhasedNodeRenderer` which is an extension of `NodeRenderer` with additional methods to
  allow rendering for specific parts of the HTML document.

- Add html rendering phases to allow generating for different parts of the document.
    - `HEAD_TOP`
    - `HEAD`
    - `HEAD_CSS`
    - `HEAD_SCRIPTS`
    - `HEAD_BOTTOM`
    - `BODY_TOP`
    - `BODY`
    - `BODY_BOTTOM`
    - `BODY_LOAD_SCRIPTS`
    - `BODY_SCRIPTS`

- Add `FootnoteExtension` which converts `[^footnote]` to footnote references and `[^footnote]:
  footnote text` footnote definitions. With referenced footnotes added to the bottom of the
  generated HTML.

- Add a few HtmlWriter methods and enhancements to allow:
    - indenting HTML
    - methods return `this` so methods could be chained
    - invocation with lambda to eliminate the need to close a tag
    - `renderChildren()` to eliminate the need for each renderer to roll its own
    - `attr(String, String)` method to accumulate attributes to be used on the next `tag()`
      invocation. Eliminating the need to roll your own attribute methods. Accumulated
      attributes are merged, or overwritten by ones passed in as an argument to `tag()`

- Fix `SegmentedSequence::getEndOffset()` for sub-sequences would return end offset of the full
  sequence and not its `subSequence()`.

- Fix footnotes embedded in other footnotes would not be assigned the right source offsets nor
  character sequences.

- Add characters to be dumped as part of the AST for opening and closing sequences for easy
  validation. For some nodes also dump the text.

- Fix table extension to include open/close pipe as part of TableCell node markers

0.1.0
-----

- AST is built based on Nodes in the source not nodes needed for HTML generation. New nodes:
    - Reference
    - Image
    - LinkRef
    - ImageRef
    - AutoLink
    - MailLink
    - Emphasis
    - StrongEmphasis
    - HtmlEntity

    Each node has `getChars()` property which returns a BasedSequence character sequence which
    spans the contents of the node, with start/end offsets into the original source.

    Additionally, each node can provide other `BasedSequence` properties that parcel out pieces
    of the node's characters, independent of child node breakdown.

- Add `PropertyHolder` interface to store document global properties for things like references,
  abbreviations, footnotes, or anything else that is parsed from the source.

- Add `NodeRepository<T>` abstract class to make it easier to create collections of nodes
  indexed by a string like one used for references.

- ParserState a few new methods:

    - `getPropertyHolder()` returns the property holder for the parse session. This is the
      current document parser. After parsing the property holder is the Document node which can
      be obtained from via `Node::getDocument()` method. Implementation is to traverse node
      parents until a Document node is reached.

    - `getInlineParser()` returns the current parse session's inline processor

    - `getLine()` and `getLineWithEOL()` return the current line being parsed. With or without
      the EOL.

    - `getLineEolLength()` returns the current line's EOL length, usually 1 but can be 2 if
      `"\r\n"` is the current line's sequence.

    - Implements `BlockPreProcessor` interface to handle pre-processing of blocks as was done in
      paragraph blocks to remove reference definitions from the beginning of a paragraph block.

- `AbstractBlockParser::closeBlock()` now takes a `ParserState` argument so that any block can
  do processing similar to Paragraph processing of leading References by using the
  `BlockPreProcessor::preProcessBlock()` method.

- Add `Builder::customInlineParserFactory()` method to allow switching of inline parser.

- Add `Builder::blockPreProcessor()` method to allow adding custom processing similar to
  `Reference` processing done previously in `ParagraphParser`.

- `InlineParserImpl` has all previously `private` fields and methods set to `protected` so that
  it can be sub-classed for customizations.

- Special processing in document parser for ParagraphParser removed, now can be done by each
  Parser since ParserState is passed to `closeBlock()` method.

- Special processing of references was removed from `ParagraphParser::closeBlock()` now it is
  done by a call to `ParserState::preProcessBlock()`

- Special processing in document parser for ListParser removed, now it is done in the ListParser
  so that it can be customized.

- `spec.txt` now `ast_spec_txt` with an added section to each example that contains the expected
  AST so that the generated AST can be validated.

        ```````````````````````````````` example
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
        ````````````````````````````````

- Convert all extension tests to spec.txt style driven testing to make generating tests easier
  and to also test for the generated AST

- Add `FullSpecTestCase` to be sub-classed, this one takes the original spec text file and
  replaces the expected html and ast sections with actual ones, then compares the full text of
  the original file to the generated one. Makes it easy to copy actual and do a diff or paste it
  into the expected document for updating the expected values.

- Add `FullSpecTestCase` derived tests added to all extensions

- Add `flexmark-ext-abbreviation` to implement abbreviations processing. The extension `create`
  function can take an optional `boolean`, which if true will generate HTML abbreviations as `<a
  href="#" title"Abbreviation Expansion Text">Abbreviation</a>`, otherwise will generate `<abbr
  title"Abbreviation Expansion Text">Abbreviation</abbr>`.

