Version History
===============

0.1.3
-----

- Update Emoji extension to use the universal options mechanism.

- Add WikiLink file link extension string option to append to generated links 

- Add Suppress HTML in addition to escape option, with separate options for blocks and inline html

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

