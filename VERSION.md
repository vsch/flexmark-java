flexmark-java
=============

[TOC]: # "## Version History"
## Version History
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


0.4.0
-----

- Add Sim TOC syntax as per Markdown Navigator simulated TOC element.

- Change Sim TOC to be a container and accept only a single HTML block without blank lines or a
  heading and a list without blank lines.

- [ ] Add Flexmark Spec Example Extension to parse flexmark spec files, same as Markdown
      Navigator.

- [ ] Add `LinkRenderer` and `LinkRendererFactory` interfaces and registration to HtmlWriter to
      handle rendering of URLs for links, including adding attributes.

      - [ ] parameters should include the raw link text to be converted to a URL and a source
            Node for hinting purposes.

      - [ ] Link renderers should be tried until one reports success, so that several renderers
            could be used for different types of link text syntax without conflict.

      - [ ] like other processors they should have before/after dependencies.

      - [ ] Any unhandled rendering will be rendered by the core

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
  eliminating the need to have a slew of `if (node instance of ...) do((cast)...)`. Just
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
  that should not process text nodes that are part of links can just add `DoNotLinkify.class` to
  the exclusion list.   

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

- Add generic `Dependent`, `DependencyResolver` and `ResolvedDependencies` to handle resolving
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

