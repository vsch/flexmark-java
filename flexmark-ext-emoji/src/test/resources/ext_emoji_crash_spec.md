---
title: Emoji Extension Crash Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Emoji

No spaces between markers

```````````````````````````````` example Emoji: 1
## Markdown Navigator

Converts :warning: to its emoji image

[TOC levels=3,6]: # "Version History"

### Version History
- [2.2.0.12 - Compatibility & Enhancement Release](#22012---compatibility--enhancement-release)
- [2.2.0 - Compatibility & Enhancement Release](#220---compatibility--enhancement-release)
- [2.1.1 - Bug Fix & Enhancement Release](#211---bug-fix--enhancement-release)
- [2.1.0 - Bug Fix Release](#210---bug-fix-release)
- [2.0.0 - New Parser Release](#200---new-parser-release)


<!--![TOC Demo](https://github.com/vsch/idea-multimarkdown/raw/master/assets/images/noload/TOCDemo.gif) -->

&nbsp;<details id="todo"><summary>**To Do List**</summary>

##### This Release To Do

* [ ] Add: when typing in the text field for change link to reference, automatically enable the
      add reference text if reference id is different from original
* [ ] Add: paste image into document, use content preview and replace whatever is selected or
      under caret not just full links. Default to reuse the name at caret. Directory to be
      configurable by scope of the destination file.
* [ ] Add: transpose table, best to add `copy to clipboard transposed table`
* [ ] Add: update parser configuration to new flexmark-java options.
* [ ] Fix: Swing preview HTML table has body row count reset or reversed so first row is like
      heading.
* [ ] Add: parser emulation family to parser configuration
* [ ] Add: parser profile needs to be passed to functions handling formatting and prefix
      generation. Now this can vary significantly from one parser family to another.
* [ ] Fix: when ENTER deletes a list item prefix inserts extra blank line
* [ ] Remove: smart asterisk, underscore and tilde handlers and options.
* [ ] Add: option for escaping special cases for `*`, `-`, `+`, `#` _`N.`_ where _N_ is numeric
      with a `\` so that it is not mis-interpreted as a special char at first non-blank of a
      wrapped line. If one is found in such a position then it should be annotated with a
      warning and a quick fix to escape it, unless it is the first non-blank of the list item's
      text. The `#` affects current implementation but should only be escaped if it lands
      exactly on the items child indent position, if parser rules don't allow leading spaces
      before ATX headings.
* [ ] Add: join processor to remove bullet list marker when joining next line item
* [ ] Fix: CommonMark and Commonmark to CommonMark
* [ ] Add: other parser profiles and appropriate options to allow using these:
      * [ ] CommonMark: GitHub Comments
      * [ ] Kramdown: GitHub Docs, GitHub Wiki Pages, Jekyll
      * [ ] FixedIndent: MultiMarkdown, PanDocs, Pegdown
* [ ] Add: `PARSE_JEKYLL_MACROS_IN_URLS` option for parser and to Parser settings to enable
      parsing of jekyll macros in urls with spaces between macro and braces.
* [ ] Fix: cursor navigation very slow in table with few rows but very long text in columns: see
      `Extensions.md` in `flexmark-java` wiki. Suspect is figuring out table context for toolbar
      button state update.
* [ ] Fix: inserting list item above in a loose list should not insert blank line below current
      item since we are inserting above and not affecting the current item.
* [ ] Add: List syntax dependent list item action behavior.
      - [x] Add: flexmark option to recognize empty list sub-items option to PARSER purpose.
      - [x] Fix: psi list item prefix reporting to match fixed4, github and CommonMark list
            processing settings.
      * [x] Fix: list indent for nested items should not indent to more than (listLevel)*4 + 3
            in fixed 4 mode, and check if also in GitHub compatible mode
      - [ ] Fix: indent/un-indent for other than fixed 4 has to re-indent child items to the
            parent's new indent level. Otherwise parsing of the children will be off. Right now
            works only for fixed4
* [ ] Add: List syntax dependent list format behavior.
      * [ ] GitHub enforces styleSettings.LIST_ALIGN_CHILD_BLOCKS and has a maximum for prefix
            marker start
      * [ ] CommonMark enforces styleSettings.LIST_ALIGN_CHILD_BLOCKS and have no maximum for
            prefix as long as it matches the parent item's content indent
      * [ ]
- [ ] Fix: Un-indent item action leaves leading indent if it was aligned to parent's left text
      edge.

##### Next Release To Do

* [ ] Add: state persistence for JavaFX script parameters and modify the `details` opener and
      Collapse Markdown scripts to use these for initializing the open/close state.
* [ ] Add: save the persistence for JavaFX with the document state so that it is restored when
      the document opens.
* [ ] Fix: When pasting text that contains ref links over a selection that already has these
      references, after the paste the references are deleted but new ones are not added. Put a
      check if possible to ignore any existing references in a selection since they will be
      deleted by the paste.
* [ ] Fix: can't modify PSI inside on save listener.
* [ ] Add: GitHub links should offer the same change relative/http: intention as the rest of the
      links.
* [ ] Fix: HRule colors the whole line even when it is in a list item
* [ ] Fix: SimToc requires default settings option so that rendering will reflect project
      settings not defaults of flexmark-java SimToc extension. For now renders what is in the
      document.
* [ ] Fix: Link Map
      * [ ] implement `ExpandedItemRendererComponentWrapper` for table cells so that the
            extended tooltip does not hide an error tooltip.
* [ ] Add: ability to move a lookup-up to the start of an element's location so that completions
      for emoji shortcuts and links located in heading elements can be properly aligned.
* [ ] Fix: take a look at the toolbar implementation to see if it can be made to put in a drop
      down for buttons that don't fit.
* [ ] Add: source synchronization for Swing preview window
* [ ] Add: source synchronization for HTML plain text previews
- [ ] Add: a CommonMark profile and compatible options in settings
* [ ] Add: detection for **GitHub** issue completions when no task servers are defined.

&nbsp;</details>

### 2.2.0.12 - Compatibility & Enhancement Release

* [ ] Add: format option to sort task lists with completed ones last, leaving the order
      otherwise unchanged, with option to remove task marker for done tasks. Non task items
      sorted like completed task items.
* Fix: all inline toggling actions to remove markers if caret is between markers and no
  intervening text, as occurs when toggle action was just used to inserted markers.
* Add: all inline toggling actions take punctuation characters that they will not wrap by
  default if caret is on them or the current word to wrap ends on them. Default
  punctuation symbols in settings: `.,;:!?`. If the caret is right after one of them then
  default behavior is to wrap the word immediately before the punctuation characters.
* Change: Added option to Main Settings `Inline code toggle like other text style actions`
  change inline code action to work just like bold, italic and strike through, instead of
  continuously adding back ticks when at the end of word.
* Fix: references or links to `raw/master/*.png` showed as unresolved when on wiki home because
  only image links would handle the optional wiki prefix from home page for image files.
* Fix: backspace at end of file after `#   ` did nothing.
* Fix: Header marker equalization was broken.
* Fix: CSS when task list item is first level, bullet sub-items mess up items
* Fix: when inserting list item above in a loose list, adds a blank line right after the
      first line of the next item, even if the item has more than one line of text. Should not
      add blank line after the next item at all.
* Add: surround live templates for:
    * fenced code,
    * collapsed sections,
    * @formatter:off/@formatter:on comments
* Add: Markdown context for Live Templates
* Fix: Table body and head should not use node text for breadcrumb. Row does that causing double
  breadcrumb text to appear.
* Add: definition lists implementation from flexmark-java
* Fix: format document did not preserve block quotes on fenced code
* Change: image links to http://github.com/user/project/blob are now always flagged with a
  warning regardless of whether they are part of an image link or reference.
* Add: `<html></html>` wrapper tags to `JavaFxHtmlGenerator` and `SwingHtmlGenerator`
* Add: `NO_FILE_EOL` to flexmark example options as built-in option
* Add: updated to flexmark-java 0.9.0, added subscript/superscript/ins extensions to parser
  options. Can be used with Jira, Copy HTML mime
* Add: Toc options for table of contents list generation options:
    - hierarchy: as before hierarchical list of headings in document order
    - flat: flat list of headings in document order
    - reversed: flat reversed list of headings in reverse document order
    - increasing: flat, alphabetically increasing by heading text
    - decreasing: flat, alphabetically decreasing by heading text
* Change: for parsing purposes all bullets interrupt all paragraphs. Eliminate the possibility
  of wrap on typing merging a block of list items when one of them is edited to non-list item.
* Add: wrap on typing and document format to respect the `@formatter:off`/`@formatter:on` tags
* Change: refactor all the settings to use settable component list where possible.
* Fix: Copy HTML mime formatted text to use CSS settings only if the profile name is
  `COPY_HTML_MIME`, otherwise use internal defaults.
* Fix: table column alignment was not taking accumulated span offset in the table row when
  getting alignment for the column from separator row.
* Add: `Copy markdown document or selection as HTML mime formatted text` action that will copy
  document or selection to the clipboard in HTML format that will paste as formatted text into
  applications that handle HTML formatted text. Useful for pasting rendered markdown in e-mails.
  To override the default styles and parser options for rendered HTML create a profile named
  `COPY_HTML_MIME` and override CSS Text. Use
  `/com/vladsch/idea/multimarkdown/html_mime_default.css` as a starting template. All style
  settings must be contained in a single matching entry since they are set in each element and
  there is no stylesheet and the "css" text is parsed and its style added to the element's style
  attribute. The "parent" selector is based on Markdown AST hierarchy and not actual HTML which
  at the time attributes are applied does not exist, so any HTML tags surrounding Markdown
  elements will have no effect. Also the classes are hardcoded into the attribute provider such
  as: `tr.odd`, `tr.even` and `li.loose` based again on Markdown AST.
* Add: option to not load GIF images, later if possible to not animate them just display the
  first frame. Really messes up preview and scrolling. Even crashed PhpStorm needing a power
  down because it would not be killed. Same with IDEA but force quit worked.
* Fix: In profiles Stylesheet and HTML override project settings options were reversed in the
  code. Html controlled Stylesheet and Stylesheet controlled HTML.
* Fix: Copy Jira and YouTrack heading would not have text if `Anchor Links` parser option was
  selected.
* Add: option to not load GIF images, later if possible to not animate them just display the
  first frame. Really messes up preview and scrolling. Even crashed PhpStorm needing a power
  down because it would not be killed.
* Add: formatter control tags support
* Add: Copy YouTrack formatted text, like Jira but with differences
* Fix: Copy Jira formatted text adding extra blank line in block quote
* Add: fenced/indented code trailing space trimming options.
* Add: flexmark-java flexmark example trailing space trimming options.
* Add: fenced code style option `Space before language info` to put a space between opening
  marker and language info string
* Fix: disable backspace, enter and typed character handlers in multi-caret mode.
* Add: multi-invoke for inline code completion to select fully qualified names or just simple
  names. Make simple name the default. Very annoying to get full names in docs.

### 2.2.0 - Compatibility & Enhancement Release

#### Basic & Enhanced Editions

- Add: markdown live template for collapsible details mnemonic `.collapsed`
- Add: option in settings to hide disabled buttons
- Change: move disable annotations from debug to document settings.
- Fix #335, Markdown Navigator breaks the line end whitespace trimming feature of EditorConfig
- Change: remove all **pegdown** dependencies
- Change: remove tab previews and enable split editor for basic edition, with fixed position
  restoring.
- Add: basic version now has split editor
- Fix: Slow scrolling with JavaFX WebView, was also causing unacceptable typing response for
  files of 500+ lines. Caused by WebView handling of CSS parameters not code.
- Fix: reimplemented JavaFX WebView integration with interruptible rendering to favour typing
  response.
- Add: #225, code highlight line number via Prism.js highlighter option
- Fix: #313, Changing fonts causes WebStorm to freeze
- Add: #316, Make shared settings Project specific
- Fix: #315, NullPointerException with v2016.3 EAP (163.6110.12)
- Fix: Implement multi-line URL image links in flexmark-java
- Fix: #327, IntelliJ IDEA 2016.3 EAP API change incompatibility.
- Fix: #328, wiki link can use ` `, `-`, `+`, `<` or `>` to match a `-` in the file name. Added
  stub index for links to make file reference search efficient.
- Change: document icons to match 2016.3 style

#### Enhanced Edition

* Fix: Rename refactoring of referencing elements broken by stub index work
* Add: JavaFX WebView script provider `Details tag opener` to open all `<details>` tags in
  preview so the content can be seen while editing
* Add: collapsible headers and markdown scripts
* Fix: setting change export now forces to re-export files in case some settings changed that
  affect the content of exported files.
* Change list toolbar icons to be simpler and more distinguishable. they all look alike.
* Add: link map move up/down groups within tree node
* Add: link map add quick fix to move errant mapping group to `unused` link type so config can
  be saved.
* Add: jekyll templates by adding an option to create a initial content when creating a mapping
  text group: empty, sample1,... each sample is based on element type
* Fix: splitting a line right after list item marker would be inconsistent and not result in a
  new list item above the current one.
* Fix: bump up the index file version numbers
* Change: disabled swing synchronization until it works properly
* Add: warning that prism syntax highlighter slows typing response also added for Fire Bug Lite
* Fix: document format would sometimes wrap early.
* Fix: swing css files not to have embedded `<` in comments to eliminate `Unterminated Comment`
  exception when using `Embed stylesheet URL content` option in HTML Generation with Swing
  browser
* Fix: swing browser pane to process HTML header for stylesheet links and load them. Now Swing
  browser can be used with exported HTML documents and a fast way to play with Swing stylesheet
  by embedding it in the HTML to get live update in the preview.
* Add: warning to Prism.js and Fire Bug Lite that they can affect preview display and typing
  response.
* Add: preview update delay tweak, default of 500ms makes typing a breeze and preview updates
  half second later.
* Fix: export on smart mode exit broke exporting all together
* Fix: style sheets need url prefix when displaying HTML
* Add: Re-Export action that will ignore modification time and force re-exporting of all
  required files.
* Fix: added a short time delay to running export after settings change or project open.
* Add: option to not wrap on typing when soft wrap is enabled for the editor
* Fix: #340, 2.1.1.40 Fail to re-gen HTML files when HTML already exists
* Add: option for format document with soft wraps: disabled, enabled and infinite margins. Will
  remove all soft breaks when formatting the document.
* Fix: balloon on html project export
* Add: link text completion for GitHub issue titles. Completes same as in text. Fast way to link
  to issues and have the title in the link.
* Add: #314, Export .html files (as part of build?)
    * exported files are limited to being under the project base directory to prevent erroneous
      target directory from writing to the file system in unexpected location.
    * copy custom font file if stylesheet has reference to it
    * optionally use relative links to:
        * exported html files
        * stylesheets and scripts
        * custom font
        * image files
    * optionally copy image files
* Fix : Jira copy to add blank lines for loosely spaced lists and after the last list item of
  the outer-most list and the next element
* Add: scope based rendering profiles allowing fine grained control on markdown rendering
  options.
* Add: #319, Synchronize source caret to preview element on click.
* Add: #283, print html preview for now only for JavaFx
* Add: #174, Suggestion: URL-to-filename transformation rules for image previews
    * Options to map from markdown link text to GitHub based link reference. ie. `{{ static_root
      }}` --> `/`
    * Options to map from GitHub based link reference to markdown link text. ie. `/` --> `{{
      static_root }}`
    * With scope based rendering profiles this mapping can be customized for specific files
      and/or directories
* Add: #331, Add markdown context aware trailing space removal
* Add: #329, Now can delete all previously generated file through HTML export or just the files
  that were previously generated and will no longer be generated in the current configuration.
* Add: Update HTML Export on project settings change option.
* Fix: #330, unexpected HTML export files on save.
* Fix: exported HTML was missing custom CSS text from Stylesheet options.
* Add: HTML Export will export any HTML files that were exported with different settings
* Add: Export Markdown to HTML action will export all changed files and delete any invalid ones
  from previous exports.
* Add: HTML Export to display error on export of different sources to same target
* Add: progress indicator to HTML Export and make it backgroundable and cancellable.
* Add: Soft wrap at right margin option to application settings for markdown documents.
* Add: configurable file reference recognition in jekyll front matter element
* Fix: linked map settings adding empty group on settings load in migration code
* Add: #332, refactor file name reference in jekyll front matter when renaming file
* Fix: when Prism.js is used as highlighter, scrolling to source with caret in the code part of
  the fenced code would always scroll to top of document.
* Fix: #320, ArrayIndexOutOfBoundsException at BlockQuoteAddAction
* Fix: JavaFX preview synchronize to caret would mess up for heading and fenced code in list
  items.
* Fix: Edit TOC dialog did not add a space between `levels=...` and the next option
* Fix: Jira copy failed to include `:lang=` for fenced code and did not add an extra blank line
  after the fenced code
* Fix: flexmark-java options refactoring exception and make dialog reflect position and
  selection of element being renamed.

### 2.1.1 - Bug Fix & Enhancement Release

#### Basic & Enhanced Editions

- Fix: #299, Tables not syntax highlighted in basic version.
- Add: List syntax options: CommonMark, Fixed, GitHub.
- Add: #301, License activation not working for some network security configurations, Option to
  use non-secure connection for license activation.
- Fix: #302, IndexOutOfBoundsException: Index out of range: 190
- Fix: #307, NegativeArraySizeException when opening .md.erb file, IDE bug
- Change: update Kotlin to 1.0.4

#### Enhanced Edition

- Fix: #305, Document Format indents Footmarks converting them to code blocks
- Add: #306, Copy/Cut of reference links, images or footnote references to include the
  references and footnotes on paste.
- Add: #300, Breadcrumbs support for Markdown documents
- Fix: breadcrumbs to show heading hierarchy as parents, including headings nested within other
  elements like list items, block quotes, etc.
- Add: breadcrumb option to show element text and maximum number of characters of text to use
  (10-60, 30 default).
- Fix: breadcrumb setext heading to use atx equivalent text
- Fix: breadcrumbs to show paragraph text instead of `Text Block`
- Add: Copy as JIRA formatted text action. Copy selection or whole document to clipboard as JIRA
  formatted text.
- Fix: #308, Wiki vcs repo not recognized in 2016.3 due to API changes. Affects to http:...
  absolute link conversion from non wiki markdown files to wiki target files.
- Add: on paste reference link format resolution for new destination file
- Add: on paste link format resolution for new destination file

### 2.1.0 - Bug Fix Release

#### Basic & Enhanced Editions

- Change: update source for flexmark-java refactored file layout.
- Fix: #287, tables stopped rendering
- Fix: #286, PyCharm 2016.2.1, unterminated fenced code causing too many exceptions
- Fix: #285, Not able to parse .md.erbfile
- Fix: #287, tables stopped rendering part 2, tables not rendering at all
- Fix: #291, on open idea load multimarkdown failure some time!, tentative fix.
- Change: remove Lobo Evolution library and other unused dependencies.
- Fix: #293, Cannot adjust settings for "Explicit Link"

#### Enhanced Edition

* Fix: remove e-mail validation from fetch license dialog.
* Fix: typing at the start of text of a numbered list item with wrap on typing enabled would
  delete the character as soon as it was typed.
* Fix: wrap on typing would stop wrapping text when space was typed. Caused by the IDE no longer
  generating pre-char typed handler calls for some yet unknown reasons.
* Fix: remove wrap on typing disabling when typing back ticks or back slashes because it was
  only needed due to pegdown parser quirks.
* Fix: #288, IndexOutOfBoundsException
* Fix: #294, Structure view text not compatible with text search.
    1. Headings: searchable text is the heading text, greyed out text is the heading id with `#`
       prefixed showing the ref anchor for the heading
    2. Images: searchable text is the image link, greyed out text is the alt text
    3. List Items: searchable text is the first line of the item text
    4. Links: searchable text is the link url, greyed out text is the link text
    5. Footnotes: searchable text is footnote reference `:` first line of footnote text
    6. References: searchable text is the reference id `:` reference link url
* Fix: #296, License expiration not handled properly by plugin for versions released before
  license expired
* Fix: #297, Code Fence only minimizes leading spaces of the first code line during formatting
* Fix: #298, Formatting list items with empty text and first item a Atx heading, moves the
  heading before the list item

### 2.0.0 - New Parser Release

#### Basic & Enhanced Editions

- Fix: #282, Child paragraphs of tight list items are merged into the item text in preview
  instead of being a separate paragraph.
- Change: Component name for Markdown Navigator application shared settings changed to `Markdown
  Navigator` from a confusing `ApplicationShared`. Did't realize that the component name was
  used for display in import/export settings dialog.
- Fix: JavaFX and Swing stylesheets to better match GFM rendering.
- Add: Flexmark parser used for Swing preview rendering and plain HTML text previews.
- Add: allow task list items from ordered list items like GitHub, rendering is the same as
  bullet items.
- Fix: emoji renderer was not setting image height, width nor align attributes
- Fix: emoji parser flags were not being passed to HTML Renderer
- Add: Flexmark parser used for JavaFX Html preview rendering.
- Add: Debug setting to allow switching to pegdown for HTML rendering for debug and comparison
  purposes.
- Change: update flexmark-java parser to spec 0.26 with more intuitive emphasis parsing
- Add: skeleton error reporter to make reporting plugin errors more convenient and also more
  complete. No server code yet. For now disabled.
- Fix: With lexer as syntax highlighter deleting the last space after `[ ]` would cause an
  exception that was trapped but it would mess up syntax highlighting
- Fix: parser would accept ordered lists using `)` delimiter, as per CommonMark spec.
- Add: flexmark parser as the default option for lexer, parser and external annotator. Typing
  response is amazing. Some elements still missing:
    - Definitions
    - Typographic: Quotes, Smarts
    - Multi-Line Image URLs

#### Enhanced Edition

* Change: Move pegdown timeout from parser settings to debug settings. :grinning:
* Add: use actual char width to fix for wrap on typing fix when typing right before start of
  line elements.
* Add: GFM table rendering option to render tables text that GFM would render as text.
* Fix: wrap on typing right before an element set to always be at the beginning of line would
  always put the caret right before the element after wrapping, typing the next word and space
  would wrap the word to the previous line, leaving the caret at the start of line. Now the
  caret is kept at the end of the previous line making caret behaviour more natural.
* Fix: split editor layout change actions and preview content change actions now restore focus
  back to the text editor. Now they can be used in keyboard shortcuts without interrupting
  typing by needing a mouse click to restore focus.
* Add: source position information to list items.
* Fix: link text suggestion provider to remove any `..` directory references
* Fix: Refine JavaFX scroll preview to source position and highlighting to work more intuitively
  for block elements, images and address JavaFX WebView DOM element offset quirks.
* Add: JavaFX scroll preview to source position and various highlight options to show which
  element in the source has focus.
* Add: flexmark spec example rendering options: fenced code, sections, definition list
* Change: simulated TOC to allow `''` for titles to match what is allowed in references
* Add: list annotation and quick fixes when list items are inconsistent. i.e. bullet and
  numbered items mixed in one list.
* Add: table annotations and reformat quick fix
* Add: parser option for generated TOC to include a blank line spacer after the `[TOC]:#` marker
  to increase compatibility with existing markdown parsers.
.
<h2>Markdown Navigator</h2>
<p>Converts <img src="/img/warning.png" alt="emoji github:warning" height="20" width="20" align="absmiddle" /> to its emoji image</p>
<h3>Version History</h3>
<ul>
  <li><a href="#22012---compatibility--enhancement-release">2.2.0.12 - Compatibility &amp; Enhancement Release</a></li>
  <li><a href="#220---compatibility--enhancement-release">2.2.0 - Compatibility &amp; Enhancement Release</a></li>
  <li><a href="#211---bug-fix--enhancement-release">2.1.1 - Bug Fix &amp; Enhancement Release</a></li>
  <li><a href="#210---bug-fix-release">2.1.0 - Bug Fix Release</a></li>
  <li><a href="#200---new-parser-release">2.0.0 - New Parser Release</a></li>
</ul>
<!--![TOC Demo](https://github.com/vsch/idea-multimarkdown/raw/master/assets/images/noload/TOCDemo.gif) -->
<p> <details id="todo"><summary><strong>To Do List</strong></summary></p>
<h5>This Release To Do</h5>
<ul>
  <li>[ ] Add: when typing in the text field for change link to reference, automatically enable the
    add reference text if reference id is different from original</li>
  <li>[ ] Add: paste image into document, use content preview and replace whatever is selected or
    under caret not just full links. Default to reuse the name at caret. Directory to be
    configurable by scope of the destination file.</li>
  <li>[ ] Add: transpose table, best to add <code>copy to clipboard transposed table</code></li>
  <li>[ ] Add: update parser configuration to new flexmark-java options.</li>
  <li>[ ] Fix: Swing preview HTML table has body row count reset or reversed so first row is like
    heading.</li>
  <li>[ ] Add: parser emulation family to parser configuration</li>
  <li>[ ] Add: parser profile needs to be passed to functions handling formatting and prefix
    generation. Now this can vary significantly from one parser family to another.</li>
  <li>[ ] Fix: when ENTER deletes a list item prefix inserts extra blank line</li>
  <li>[ ] Remove: smart asterisk, underscore and tilde handlers and options.</li>
  <li>[ ] Add: option for escaping special cases for <code>*</code>, <code>-</code>, <code>+</code>, <code>#</code> <em><code>N.</code></em> where <em>N</em> is numeric
    with a <code>\</code> so that it is not mis-interpreted as a special char at first non-blank of a
    wrapped line. If one is found in such a position then it should be annotated with a
    warning and a quick fix to escape it, unless it is the first non-blank of the list item's
    text. The <code>#</code> affects current implementation but should only be escaped if it lands
    exactly on the items child indent position, if parser rules don't allow leading spaces
    before ATX headings.</li>
  <li>[ ] Add: join processor to remove bullet list marker when joining next line item</li>
  <li>[ ] Fix: CommonMark and Commonmark to CommonMark</li>
  <li>[ ] Add: other parser profiles and appropriate options to allow using these:
    * [ ] CommonMark: GitHub Comments
    * [ ] Kramdown: GitHub Docs, GitHub Wiki Pages, Jekyll
    * [ ] FixedIndent: MultiMarkdown, PanDocs, Pegdown</li>
  <li>[ ] Add: <code>PARSE_JEKYLL_MACROS_IN_URLS</code> option for parser and to Parser settings to enable
    parsing of jekyll macros in urls with spaces between macro and braces.</li>
  <li>[ ] Fix: cursor navigation very slow in table with few rows but very long text in columns: see
    <code>Extensions.md</code> in <code>flexmark-java</code> wiki. Suspect is figuring out table context for toolbar
    button state update.</li>
  <li>[ ] Fix: inserting list item above in a loose list should not insert blank line below current
    item since we are inserting above and not affecting the current item.</li>
  <li>[ ] Add: List syntax dependent list item action behavior.
    - [x] Add: flexmark option to recognize empty list sub-items option to PARSER purpose.
    - [x] Fix: psi list item prefix reporting to match fixed4, github and CommonMark list
    processing settings.
    * [x] Fix: list indent for nested items should not indent to more than (listLevel)*4 + 3
    in fixed 4 mode, and check if also in GitHub compatible mode
    - [ ] Fix: indent/un-indent for other than fixed 4 has to re-indent child items to the
    parent's new indent level. Otherwise parsing of the children will be off. Right now
    works only for fixed4</li>
  <li>[ ] Add: List syntax dependent list format behavior.
    * [ ] GitHub enforces styleSettings.LIST_ALIGN_CHILD_BLOCKS and has a maximum for prefix
    marker start
    * [ ] CommonMark enforces styleSettings.LIST_ALIGN_CHILD_BLOCKS and have no maximum for
    prefix as long as it matches the parent item's content indent
    * [ ]</li>
</ul>
<ul>
  <li>[ ] Fix: Un-indent item action leaves leading indent if it was aligned to parent's left text
    edge.</li>
</ul>
<h5>Next Release To Do</h5>
<ul>
  <li>[ ] Add: state persistence for JavaFX script parameters and modify the <code>details</code> opener and
    Collapse Markdown scripts to use these for initializing the open/close state.</li>
  <li>[ ] Add: save the persistence for JavaFX with the document state so that it is restored when
    the document opens.</li>
  <li>[ ] Fix: When pasting text that contains ref links over a selection that already has these
    references, after the paste the references are deleted but new ones are not added. Put a
    check if possible to ignore any existing references in a selection since they will be
    deleted by the paste.</li>
  <li>[ ] Fix: can't modify PSI inside on save listener.</li>
  <li>[ ] Add: GitHub links should offer the same change relative/http: intention as the rest of the
    links.</li>
  <li>[ ] Fix: HRule colors the whole line even when it is in a list item</li>
  <li>[ ] Fix: SimToc requires default settings option so that rendering will reflect project
    settings not defaults of flexmark-java SimToc extension. For now renders what is in the
    document.</li>
  <li>[ ] Fix: Link Map
    * [ ] implement <code>ExpandedItemRendererComponentWrapper</code> for table cells so that the
    extended tooltip does not hide an error tooltip.</li>
  <li>[ ] Add: ability to move a lookup-up to the start of an element's location so that completions
    for emoji shortcuts and links located in heading elements can be properly aligned.</li>
  <li>[ ] Fix: take a look at the toolbar implementation to see if it can be made to put in a drop
    down for buttons that don't fit.</li>
  <li>[ ] Add: source synchronization for Swing preview window</li>
  <li>[ ] Add: source synchronization for HTML plain text previews</li>
</ul>
<ul>
  <li>[ ] Add: a CommonMark profile and compatible options in settings</li>
</ul>
<ul>
  <li>[ ] Add: detection for <strong>GitHub</strong> issue completions when no task servers are defined.</li>
</ul>
<p> </details></p>
<h3>2.2.0.12 - Compatibility &amp; Enhancement Release</h3>
<ul>
  <li>[ ] Add: format option to sort task lists with completed ones last, leaving the order
    otherwise unchanged, with option to remove task marker for done tasks. Non task items
    sorted like completed task items.</li>
  <li>Fix: all inline toggling actions to remove markers if caret is between markers and no
    intervening text, as occurs when toggle action was just used to inserted markers.</li>
  <li>Add: all inline toggling actions take punctuation characters that they will not wrap by
    default if caret is on them or the current word to wrap ends on them. Default
    punctuation symbols in settings: <code>.,;:!?</code>. If the caret is right after one of them then
    default behavior is to wrap the word immediately before the punctuation characters.</li>
  <li>Change: Added option to Main Settings <code>Inline code toggle like other text style actions</code>
    change inline code action to work just like bold, italic and strike through, instead of
    continuously adding back ticks when at the end of word.</li>
  <li>Fix: references or links to <code>raw/master/*.png</code> showed as unresolved when on wiki home because
    only image links would handle the optional wiki prefix from home page for image files.</li>
  <li>Fix: backspace at end of file after <code>#</code> did nothing.</li>
  <li>Fix: Header marker equalization was broken.</li>
  <li>Fix: CSS when task list item is first level, bullet sub-items mess up items</li>
  <li>Fix: when inserting list item above in a loose list, adds a blank line right after the
    first line of the next item, even if the item has more than one line of text. Should not
    add blank line after the next item at all.</li>
  <li>Add: surround live templates for:
    <ul>
      <li>fenced code,</li>
      <li>collapsed sections,</li>
      <li>@formatter:off/@formatter:on comments</li>
    </ul>
  </li>
  <li>Add: Markdown context for Live Templates</li>
  <li>Fix: Table body and head should not use node text for breadcrumb. Row does that causing double
    breadcrumb text to appear.</li>
  <li>Add: definition lists implementation from flexmark-java</li>
  <li>Fix: format document did not preserve block quotes on fenced code</li>
  <li>Change: image links to http://github.com/user/project/blob are now always flagged with a
    warning regardless of whether they are part of an image link or reference.</li>
  <li>Add: <code>&lt;html&gt;&lt;/html&gt;</code> wrapper tags to <code>JavaFxHtmlGenerator</code> and <code>SwingHtmlGenerator</code></li>
  <li>Add: <code>NO_FILE_EOL</code> to flexmark example options as built-in option</li>
  <li>Add: updated to flexmark-java 0.9.0, added subscript/superscript/ins extensions to parser
    options. Can be used with Jira, Copy HTML mime</li>
  <li>Add: Toc options for table of contents list generation options:
    <ul>
      <li>hierarchy: as before hierarchical list of headings in document order</li>
      <li>flat: flat list of headings in document order</li>
      <li>reversed: flat reversed list of headings in reverse document order</li>
      <li>increasing: flat, alphabetically increasing by heading text</li>
      <li>decreasing: flat, alphabetically decreasing by heading text</li>
    </ul>
  </li>
  <li>Change: for parsing purposes all bullets interrupt all paragraphs. Eliminate the possibility
    of wrap on typing merging a block of list items when one of them is edited to non-list item.</li>
  <li>Add: wrap on typing and document format to respect the <code>@formatter:off</code>/<code>@formatter:on</code> tags</li>
  <li>Change: refactor all the settings to use settable component list where possible.</li>
  <li>Fix: Copy HTML mime formatted text to use CSS settings only if the profile name is
    <code>COPY_HTML_MIME</code>, otherwise use internal defaults.</li>
  <li>Fix: table column alignment was not taking accumulated span offset in the table row when
    getting alignment for the column from separator row.</li>
  <li>Add: <code>Copy markdown document or selection as HTML mime formatted text</code> action that will copy
    document or selection to the clipboard in HTML format that will paste as formatted text into
    applications that handle HTML formatted text. Useful for pasting rendered markdown in e-mails.
    To override the default styles and parser options for rendered HTML create a profile named
    <code>COPY_HTML_MIME</code> and override CSS Text. Use
    <code>/com/vladsch/idea/multimarkdown/html_mime_default.css</code> as a starting template. All style
    settings must be contained in a single matching entry since they are set in each element and
    there is no stylesheet and the &quot;css&quot; text is parsed and its style added to the element's style
    attribute. The &quot;parent&quot; selector is based on Markdown AST hierarchy and not actual HTML which
    at the time attributes are applied does not exist, so any HTML tags surrounding Markdown
    elements will have no effect. Also the classes are hardcoded into the attribute provider such
    as: <code>tr.odd</code>, <code>tr.even</code> and <code>li.loose</code> based again on Markdown AST.</li>
  <li>Add: option to not load GIF images, later if possible to not animate them just display the
    first frame. Really messes up preview and scrolling. Even crashed PhpStorm needing a power
    down because it would not be killed. Same with IDEA but force quit worked.</li>
  <li>Fix: In profiles Stylesheet and HTML override project settings options were reversed in the
    code. Html controlled Stylesheet and Stylesheet controlled HTML.</li>
  <li>Fix: Copy Jira and YouTrack heading would not have text if <code>Anchor Links</code> parser option was
    selected.</li>
  <li>Add: option to not load GIF images, later if possible to not animate them just display the
    first frame. Really messes up preview and scrolling. Even crashed PhpStorm needing a power
    down because it would not be killed.</li>
  <li>Add: formatter control tags support</li>
  <li>Add: Copy YouTrack formatted text, like Jira but with differences</li>
  <li>Fix: Copy Jira formatted text adding extra blank line in block quote</li>
  <li>Add: fenced/indented code trailing space trimming options.</li>
  <li>Add: flexmark-java flexmark example trailing space trimming options.</li>
  <li>Add: fenced code style option <code>Space before language info</code> to put a space between opening
    marker and language info string</li>
  <li>Fix: disable backspace, enter and typed character handlers in multi-caret mode.</li>
  <li>Add: multi-invoke for inline code completion to select fully qualified names or just simple
    names. Make simple name the default. Very annoying to get full names in docs.</li>
</ul>
<h3>2.2.0 - Compatibility &amp; Enhancement Release</h3>
<h4>Basic &amp; Enhanced Editions</h4>
<ul>
  <li>Add: markdown live template for collapsible details mnemonic <code>.collapsed</code></li>
  <li>Add: option in settings to hide disabled buttons</li>
  <li>Change: move disable annotations from debug to document settings.</li>
  <li>Fix #335, Markdown Navigator breaks the line end whitespace trimming feature of EditorConfig</li>
  <li>Change: remove all <strong>pegdown</strong> dependencies</li>
  <li>Change: remove tab previews and enable split editor for basic edition, with fixed position
    restoring.</li>
  <li>Add: basic version now has split editor</li>
  <li>Fix: Slow scrolling with JavaFX WebView, was also causing unacceptable typing response for
    files of 500+ lines. Caused by WebView handling of CSS parameters not code.</li>
  <li>Fix: reimplemented JavaFX WebView integration with interruptible rendering to favour typing
    response.</li>
  <li>Add: #225, code highlight line number via Prism.js highlighter option</li>
  <li>Fix: #313, Changing fonts causes WebStorm to freeze</li>
  <li>Add: #316, Make shared settings Project specific</li>
  <li>Fix: #315, NullPointerException with v2016.3 EAP (163.6110.12)</li>
  <li>Fix: Implement multi-line URL image links in flexmark-java</li>
  <li>Fix: #327, IntelliJ IDEA 2016.3 EAP API change incompatibility.</li>
  <li>Fix: #328, wiki link can use <code></code>, <code>-</code>, <code>+</code>, <code>&lt;</code> or <code>&gt;</code> to match a <code>-</code> in the file name. Added
    stub index for links to make file reference search efficient.</li>
  <li>Change: document icons to match 2016.3 style</li>
</ul>
<h4>Enhanced Edition</h4>
<ul>
  <li>Fix: Rename refactoring of referencing elements broken by stub index work</li>
  <li>Add: JavaFX WebView script provider <code>Details tag opener</code> to open all <code>&lt;details&gt;</code> tags in
    preview so the content can be seen while editing</li>
  <li>Add: collapsible headers and markdown scripts</li>
  <li>Fix: setting change export now forces to re-export files in case some settings changed that
    affect the content of exported files.</li>
  <li>Change list toolbar icons to be simpler and more distinguishable. they all look alike.</li>
  <li>Add: link map move up/down groups within tree node</li>
  <li>Add: link map add quick fix to move errant mapping group to <code>unused</code> link type so config can
    be saved.</li>
  <li>Add: jekyll templates by adding an option to create a initial content when creating a mapping
    text group: empty, sample1,... each sample is based on element type</li>
  <li>Fix: splitting a line right after list item marker would be inconsistent and not result in a
    new list item above the current one.</li>
  <li>Fix: bump up the index file version numbers</li>
  <li>Change: disabled swing synchronization until it works properly</li>
  <li>Add: warning that prism syntax highlighter slows typing response also added for Fire Bug Lite</li>
  <li>Fix: document format would sometimes wrap early.</li>
  <li>Fix: swing css files not to have embedded <code>&lt;</code> in comments to eliminate <code>Unterminated Comment</code>
    exception when using <code>Embed stylesheet URL content</code> option in HTML Generation with Swing
    browser</li>
  <li>Fix: swing browser pane to process HTML header for stylesheet links and load them. Now Swing
    browser can be used with exported HTML documents and a fast way to play with Swing stylesheet
    by embedding it in the HTML to get live update in the preview.</li>
  <li>Add: warning to Prism.js and Fire Bug Lite that they can affect preview display and typing
    response.</li>
  <li>Add: preview update delay tweak, default of 500ms makes typing a breeze and preview updates
    half second later.</li>
  <li>Fix: export on smart mode exit broke exporting all together</li>
  <li>Fix: style sheets need url prefix when displaying HTML</li>
  <li>Add: Re-Export action that will ignore modification time and force re-exporting of all
    required files.</li>
  <li>Fix: added a short time delay to running export after settings change or project open.</li>
  <li>Add: option to not wrap on typing when soft wrap is enabled for the editor</li>
  <li>Fix: #340, 2.1.1.40 Fail to re-gen HTML files when HTML already exists</li>
  <li>Add: option for format document with soft wraps: disabled, enabled and infinite margins. Will
    remove all soft breaks when formatting the document.</li>
  <li>Fix: balloon on html project export</li>
  <li>Add: link text completion for GitHub issue titles. Completes same as in text. Fast way to link
    to issues and have the title in the link.</li>
  <li>Add: #314, Export .html files (as part of build?)
    <ul>
      <li>exported files are limited to being under the project base directory to prevent erroneous
        target directory from writing to the file system in unexpected location.</li>
      <li>copy custom font file if stylesheet has reference to it</li>
      <li>optionally use relative links to:
        <ul>
          <li>exported html files</li>
          <li>stylesheets and scripts</li>
          <li>custom font</li>
          <li>image files</li>
        </ul>
      </li>
      <li>optionally copy image files</li>
    </ul>
  </li>
  <li>Fix : Jira copy to add blank lines for loosely spaced lists and after the last list item of
    the outer-most list and the next element</li>
  <li>Add: scope based rendering profiles allowing fine grained control on markdown rendering
    options.</li>
  <li>Add: #319, Synchronize source caret to preview element on click.</li>
  <li>Add: #283, print html preview for now only for JavaFx</li>
  <li>Add: #174, Suggestion: URL-to-filename transformation rules for image previews
    <ul>
      <li>Options to map from markdown link text to GitHub based link reference. ie. <code>{{ static_root }}</code> --&gt; <code>/</code></li>
      <li>Options to map from GitHub based link reference to markdown link text. ie. <code>/</code> --&gt; <code>{{ static_root }}</code></li>
      <li>With scope based rendering profiles this mapping can be customized for specific files
        and/or directories</li>
    </ul>
  </li>
  <li>Add: #331, Add markdown context aware trailing space removal</li>
  <li>Add: #329, Now can delete all previously generated file through HTML export or just the files
    that were previously generated and will no longer be generated in the current configuration.</li>
  <li>Add: Update HTML Export on project settings change option.</li>
  <li>Fix: #330, unexpected HTML export files on save.</li>
  <li>Fix: exported HTML was missing custom CSS text from Stylesheet options.</li>
  <li>Add: HTML Export will export any HTML files that were exported with different settings</li>
  <li>Add: Export Markdown to HTML action will export all changed files and delete any invalid ones
    from previous exports.</li>
  <li>Add: HTML Export to display error on export of different sources to same target</li>
  <li>Add: progress indicator to HTML Export and make it backgroundable and cancellable.</li>
  <li>Add: Soft wrap at right margin option to application settings for markdown documents.</li>
  <li>Add: configurable file reference recognition in jekyll front matter element</li>
  <li>Fix: linked map settings adding empty group on settings load in migration code</li>
  <li>Add: #332, refactor file name reference in jekyll front matter when renaming file</li>
  <li>Fix: when Prism.js is used as highlighter, scrolling to source with caret in the code part of
    the fenced code would always scroll to top of document.</li>
  <li>Fix: #320, ArrayIndexOutOfBoundsException at BlockQuoteAddAction</li>
  <li>Fix: JavaFX preview synchronize to caret would mess up for heading and fenced code in list
    items.</li>
  <li>Fix: Edit TOC dialog did not add a space between <code>levels=...</code> and the next option</li>
  <li>Fix: Jira copy failed to include <code>:lang=</code> for fenced code and did not add an extra blank line
    after the fenced code</li>
  <li>Fix: flexmark-java options refactoring exception and make dialog reflect position and
    selection of element being renamed.</li>
</ul>
<h3>2.1.1 - Bug Fix &amp; Enhancement Release</h3>
<h4>Basic &amp; Enhanced Editions</h4>
<ul>
  <li>Fix: #299, Tables not syntax highlighted in basic version.</li>
  <li>Add: List syntax options: CommonMark, Fixed, GitHub.</li>
  <li>Add: #301, License activation not working for some network security configurations, Option to
    use non-secure connection for license activation.</li>
  <li>Fix: #302, IndexOutOfBoundsException: Index out of range: 190</li>
  <li>Fix: #307, NegativeArraySizeException when opening .md.erb file, IDE bug</li>
  <li>Change: update Kotlin to 1.0.4</li>
</ul>
<h4>Enhanced Edition</h4>
<ul>
  <li>Fix: #305, Document Format indents Footmarks converting them to code blocks</li>
  <li>Add: #306, Copy/Cut of reference links, images or footnote references to include the
    references and footnotes on paste.</li>
  <li>Add: #300, Breadcrumbs support for Markdown documents</li>
  <li>Fix: breadcrumbs to show heading hierarchy as parents, including headings nested within other
    elements like list items, block quotes, etc.</li>
  <li>Add: breadcrumb option to show element text and maximum number of characters of text to use
    (10-60, 30 default).</li>
  <li>Fix: breadcrumb setext heading to use atx equivalent text</li>
  <li>Fix: breadcrumbs to show paragraph text instead of <code>Text Block</code></li>
  <li>Add: Copy as JIRA formatted text action. Copy selection or whole document to clipboard as JIRA
    formatted text.</li>
  <li>Fix: #308, Wiki vcs repo not recognized in 2016.3 due to API changes. Affects to http:...
    absolute link conversion from non wiki markdown files to wiki target files.</li>
  <li>Add: on paste reference link format resolution for new destination file</li>
  <li>Add: on paste link format resolution for new destination file</li>
</ul>
<h3>2.1.0 - Bug Fix Release</h3>
<h4>Basic &amp; Enhanced Editions</h4>
<ul>
  <li>Change: update source for flexmark-java refactored file layout.</li>
  <li>Fix: #287, tables stopped rendering</li>
  <li>Fix: #286, PyCharm 2016.2.1, unterminated fenced code causing too many exceptions</li>
  <li>Fix: #285, Not able to parse .md.erbfile</li>
  <li>Fix: #287, tables stopped rendering part 2, tables not rendering at all</li>
  <li>Fix: #291, on open idea load multimarkdown failure some time!, tentative fix.</li>
  <li>Change: remove Lobo Evolution library and other unused dependencies.</li>
  <li>Fix: #293, Cannot adjust settings for &quot;Explicit Link&quot;</li>
</ul>
<h4>Enhanced Edition</h4>
<ul>
  <li>Fix: remove e-mail validation from fetch license dialog.</li>
  <li>Fix: typing at the start of text of a numbered list item with wrap on typing enabled would
    delete the character as soon as it was typed.</li>
  <li>Fix: wrap on typing would stop wrapping text when space was typed. Caused by the IDE no longer
    generating pre-char typed handler calls for some yet unknown reasons.</li>
  <li>Fix: remove wrap on typing disabling when typing back ticks or back slashes because it was
    only needed due to pegdown parser quirks.</li>
  <li>Fix: #288, IndexOutOfBoundsException</li>
  <li>Fix: #294, Structure view text not compatible with text search.
    <ol>
      <li>Headings: searchable text is the heading text, greyed out text is the heading id with <code>#</code>
        prefixed showing the ref anchor for the heading</li>
      <li>Images: searchable text is the image link, greyed out text is the alt text</li>
      <li>List Items: searchable text is the first line of the item text</li>
      <li>Links: searchable text is the link url, greyed out text is the link text</li>
      <li>Footnotes: searchable text is footnote reference <code>:</code> first line of footnote text</li>
      <li>References: searchable text is the reference id <code>:</code> reference link url</li>
    </ol>
  </li>
  <li>Fix: #296, License expiration not handled properly by plugin for versions released before
    license expired</li>
  <li>Fix: #297, Code Fence only minimizes leading spaces of the first code line during formatting</li>
  <li>Fix: #298, Formatting list items with empty text and first item a Atx heading, moves the
    heading before the list item</li>
</ul>
<h3>2.0.0 - New Parser Release</h3>
<h4>Basic &amp; Enhanced Editions</h4>
<ul>
  <li>Fix: #282, Child paragraphs of tight list items are merged into the item text in preview
    instead of being a separate paragraph.</li>
  <li>Change: Component name for Markdown Navigator application shared settings changed to <code>Markdown Navigator</code> from a confusing <code>ApplicationShared</code>. Did't realize that the component name was
    used for display in import/export settings dialog.</li>
  <li>Fix: JavaFX and Swing stylesheets to better match GFM rendering.</li>
  <li>Add: Flexmark parser used for Swing preview rendering and plain HTML text previews.</li>
  <li>Add: allow task list items from ordered list items like GitHub, rendering is the same as
    bullet items.</li>
  <li>Fix: emoji renderer was not setting image height, width nor align attributes</li>
  <li>Fix: emoji parser flags were not being passed to HTML Renderer</li>
  <li>Add: Flexmark parser used for JavaFX Html preview rendering.</li>
  <li>Add: Debug setting to allow switching to pegdown for HTML rendering for debug and comparison
    purposes.</li>
  <li>Change: update flexmark-java parser to spec 0.26 with more intuitive emphasis parsing</li>
  <li>Add: skeleton error reporter to make reporting plugin errors more convenient and also more
    complete. No server code yet. For now disabled.</li>
  <li>Fix: With lexer as syntax highlighter deleting the last space after <code>[ ]</code> would cause an
    exception that was trapped but it would mess up syntax highlighting</li>
  <li>Fix: parser would accept ordered lists using <code>)</code> delimiter, as per CommonMark spec.</li>
  <li>Add: flexmark parser as the default option for lexer, parser and external annotator. Typing
    response is amazing. Some elements still missing:
    <ul>
      <li>Definitions</li>
      <li>Typographic: Quotes, Smarts</li>
      <li>Multi-Line Image URLs</li>
    </ul>
  </li>
</ul>
<h4>Enhanced Edition</h4>
<ul>
  <li>Change: Move pegdown timeout from parser settings to debug settings. <img src="/img/grinning.png" alt="emoji github:grinning" height="20" width="20" align="absmiddle" /></li>
  <li>Add: use actual char width to fix for wrap on typing fix when typing right before start of
    line elements.</li>
  <li>Add: GFM table rendering option to render tables text that GFM would render as text.</li>
  <li>Fix: wrap on typing right before an element set to always be at the beginning of line would
    always put the caret right before the element after wrapping, typing the next word and space
    would wrap the word to the previous line, leaving the caret at the start of line. Now the
    caret is kept at the end of the previous line making caret behaviour more natural.</li>
  <li>Fix: split editor layout change actions and preview content change actions now restore focus
    back to the text editor. Now they can be used in keyboard shortcuts without interrupting
    typing by needing a mouse click to restore focus.</li>
  <li>Add: source position information to list items.</li>
  <li>Fix: link text suggestion provider to remove any <code>..</code> directory references</li>
  <li>Fix: Refine JavaFX scroll preview to source position and highlighting to work more intuitively
    for block elements, images and address JavaFX WebView DOM element offset quirks.</li>
  <li>Add: JavaFX scroll preview to source position and various highlight options to show which
    element in the source has focus.</li>
  <li>Add: flexmark spec example rendering options: fenced code, sections, definition list</li>
  <li>Change: simulated TOC to allow <code>''</code> for titles to match what is allowed in references</li>
  <li>Add: list annotation and quick fixes when list items are inconsistent. i.e. bullet and
    numbered items mixed in one list.</li>
  <li>Add: table annotations and reformat quick fix</li>
  <li>Add: parser option for generated TOC to include a blank line spacer after the <code>[TOC]:#</code> marker
    to increase compatibility with existing markdown parsers.</li>
</ul>
.
Document[0, 26735]
  Heading[0, 21] textOpen:[0, 2, "##"] text:[3, 21, "Markdown Navigator"]
    Text[3, 21] chars:[3, 21, "Markd … gator"]
  Paragraph[23, 61] isTrailingBlankLine
    Text[23, 32] chars:[23, 32, "Converts "]
    Emoji[32, 41] textOpen:[32, 33, ":"] text:[33, 40, "warning"] textClose:[40, 41, ":"]
      Text[33, 40] chars:[33, 40, "warning"]
    Text[41, 60] chars:[41, 60, " to i … image"]
  Reference[62, 99] refOpen:[62, 63, "["] ref:[63, 77, "TOC levels=3,6"] refClose:[77, 79, "]:"] url:[80, 81, "#"] titleOpen:[82, 83, "\""] title:[83, 98, "Version History"] titleClose:[98, 99, "\""]
  Heading[101, 120] textOpen:[101, 104, "###"] text:[105, 120, "Version History"]
    Text[105, 120] chars:[105, 120, "Versi … story"]
  BulletList[121, 497] isTight
    BulletListItem[121, 217] open:[121, 122, "-"] isTight
      Paragraph[123, 217]
        Link[123, 216] textOpen:[123, 124, "["] text:[124, 170, "2.2.0.12 - Compatibility & Enhancement Release"] textClose:[170, 171, "]"] linkOpen:[171, 172, "("] url:[172, 215, "#22012---compatibility--enhancement-release"] pageRef:[172, 172] anchorMarker:[172, 173, "#"] anchorRef:[173, 215, "22012---compatibility--enhancement-release"] linkClose:[215, 216, ")"]
          Text[124, 170] chars:[124, 170, "2.2.0 … lease"]
    BulletListItem[217, 308] open:[217, 218, "-"] isTight
      Paragraph[219, 308]
        Link[219, 307] textOpen:[219, 220, "["] text:[220, 263, "2.2.0 - Compatibility & Enhancement Release"] textClose:[263, 264, "]"] linkOpen:[264, 265, "("] url:[265, 306, "#220---compatibility--enhancement-release"] pageRef:[265, 265] anchorMarker:[265, 266, "#"] anchorRef:[266, 306, "220---compatibility--enhancement-release"] linkClose:[306, 307, ")"]
          Text[220, 263] chars:[220, 263, "2.2.0 … lease"]
    BulletListItem[308, 387] open:[308, 309, "-"] isTight
      Paragraph[310, 387]
        Link[310, 386] textOpen:[310, 311, "["] text:[311, 348, "2.1.1 - Bug Fix & Enhancement Release"] textClose:[348, 349, "]"] linkOpen:[349, 350, "("] url:[350, 385, "#211---bug-fix--enhancement-release"] pageRef:[350, 350] anchorMarker:[350, 351, "#"] anchorRef:[351, 385, "211---bug-fix--enhancement-release"] linkClose:[385, 386, ")"]
          Text[311, 348] chars:[311, 348, "2.1.1 … lease"]
    BulletListItem[387, 439] open:[387, 388, "-"] isTight
      Paragraph[389, 439]
        Link[389, 438] textOpen:[389, 390, "["] text:[390, 413, "2.1.0 - Bug Fix Release"] textClose:[413, 414, "]"] linkOpen:[414, 415, "("] url:[415, 437, "#210---bug-fix-release"] pageRef:[415, 415] anchorMarker:[415, 416, "#"] anchorRef:[416, 437, "210---bug-fix-release"] linkClose:[437, 438, ")"]
          Text[390, 413] chars:[390, 413, "2.1.0 … lease"]
    BulletListItem[439, 497] open:[439, 440, "-"] isTight hadBlankLineAfter
      Paragraph[441, 497] isTrailingBlankLine
        Link[441, 496] textOpen:[441, 442, "["] text:[442, 468, "2.0.0 - New Parser Release"] textClose:[468, 469, "]"] linkOpen:[469, 470, "("] url:[470, 495, "#200---new-parser-release"] pageRef:[470, 470] anchorMarker:[470, 471, "#"] anchorRef:[471, 495, "200---new-parser-release"] linkClose:[495, 496, ")"]
          Text[442, 468] chars:[442, 468, "2.0.0 … lease"]
  HtmlCommentBlock[499, 607]
  Paragraph[608, 667] isTrailingBlankLine
    HtmlEntity[608, 614] "&nbsp;"
    HtmlInline[614, 633] chars:[614, 633, "<deta … odo\">"]
    HtmlInline[633, 642] chars:[633, 642, "<summary>"]
    StrongEmphasis[642, 656] textOpen:[642, 644, "**"] text:[644, 654, "To Do List"] textClose:[654, 656, "**"]
      Text[644, 654] chars:[644, 654, "To Do List"]
    HtmlInline[656, 666] chars:[656, 666, "</summary>"]
  Heading[668, 692] textOpen:[668, 673, "#####"] text:[674, 692, "This Release To Do"]
    Text[674, 692] chars:[674, 692, "This  … To Do"]
  BulletList[694, 4273] isTight
    BulletListItem[694, 858] open:[694, 695, "*"] isTight
      Paragraph[696, 858]
        LinkRef[696, 699] referenceOpen:[696, 697, "["] reference:[698, 698] referenceClose:[698, 699, "]"]
          Text[698, 698]
        Text[699, 789] chars:[699, 789, " Add: … e the"]
        SoftLineBreak[789, 790]
        Text[796, 857] chars:[796, 857, "add r … ginal"]
    BulletListItem[858, 1096] open:[858, 859, "*"] isTight
      Paragraph[860, 1096]
        LinkRef[860, 863] referenceOpen:[860, 861, "["] reference:[862, 862] referenceClose:[862, 863, "]"]
          Text[862, 862]
        Text[863, 951] chars:[863, 951, " Add: … ed or"]
        SoftLineBreak[951, 952]
        Text[958, 1042] chars:[958, 1042, "under … to be"]
        SoftLineBreak[1042, 1043]
        Text[1049, 1095] chars:[1049, 1095, "confi … file."]
    BulletListItem[1096, 1173] open:[1096, 1097, "*"] isTight
      Paragraph[1098, 1173]
        LinkRef[1098, 1101] referenceOpen:[1098, 1099, "["] reference:[1100, 1100] referenceClose:[1100, 1101, "]"]
          Text[1100, 1100]
        Text[1101, 1136] chars:[1101, 1136, " Add: …  add "]
        Code[1136, 1172] textOpen:[1136, 1137, "`"] text:[1137, 1171, "copy  … to clipboard transposed table"] textClose:[1171, 1172, "`"]
          Text[1137, 1171] chars:[1137, 1171, "copy  … table"]
    BulletListItem[1173, 1242] open:[1173, 1174, "*"] isTight
      Paragraph[1175, 1242]
        LinkRef[1175, 1178] referenceOpen:[1175, 1176, "["] reference:[1177, 1177] referenceClose:[1177, 1178, "]"]
          Text[1177, 1177]
        Text[1178, 1241] chars:[1178, 1241, " Add: … ions."]
    BulletListItem[1242, 1351] open:[1242, 1243, "*"] isTight
      Paragraph[1244, 1351]
        LinkRef[1244, 1247] referenceOpen:[1244, 1245, "["] reference:[1246, 1246] referenceClose:[1246, 1247, "]"]
          Text[1246, 1246]
        Text[1247, 1335] chars:[1247, 1335, " Fix: …  like"]
        SoftLineBreak[1335, 1336]
        Text[1342, 1350] chars:[1342, 1350, "heading."]
    BulletListItem[1351, 1410] open:[1351, 1352, "*"] isTight
      Paragraph[1353, 1410]
        LinkRef[1353, 1356] referenceOpen:[1353, 1354, "["] reference:[1355, 1355] referenceClose:[1355, 1356, "]"]
          Text[1355, 1355]
        Text[1356, 1409] chars:[1356, 1409, " Add: … ation"]
    BulletListItem[1410, 1584] open:[1410, 1411, "*"] isTight
      Paragraph[1412, 1584]
        LinkRef[1412, 1415] referenceOpen:[1412, 1413, "["] reference:[1414, 1414] referenceClose:[1414, 1415, "]"]
          Text[1414, 1414]
        Text[1415, 1498] chars:[1415, 1498, " Add: … refix"]
        SoftLineBreak[1498, 1499]
        Text[1505, 1583] chars:[1505, 1583, "gener … ther."]
    BulletListItem[1584, 1658] open:[1584, 1585, "*"] isTight
      Paragraph[1586, 1658]
        LinkRef[1586, 1589] referenceOpen:[1586, 1587, "["] reference:[1588, 1588] referenceClose:[1588, 1589, "]"]
          Text[1588, 1588]
        Text[1589, 1657] chars:[1589, 1657, " Fix: …  line"]
    BulletListItem[1658, 1731] open:[1658, 1659, "*"] isTight
      Paragraph[1660, 1731]
        LinkRef[1660, 1663] referenceOpen:[1660, 1661, "["] reference:[1662, 1662] referenceClose:[1662, 1663, "]"]
          Text[1662, 1662]
        Text[1663, 1730] chars:[1663, 1730, " Remo … ions."]
    BulletListItem[1731, 2316] open:[1731, 1732, "*"] isTight
      Paragraph[1733, 2316]
        LinkRef[1733, 1736] referenceOpen:[1733, 1734, "["] reference:[1735, 1735] referenceClose:[1735, 1736, "]"]
          Text[1735, 1735]
        Text[1736, 1780] chars:[1736, 1780, " Add: …  for "]
        Code[1780, 1783] textOpen:[1780, 1781, "`"] text:[1781, 1782, "*"] textClose:[1782, 1783, "`"]
          Text[1781, 1782] chars:[1781, 1782, "*"]
        Text[1783, 1785] chars:[1783, 1785, ", "]
        Code[1785, 1788] textOpen:[1785, 1786, "`"] text:[1786, 1787, "-"] textClose:[1787, 1788, "`"]
          Text[1786, 1787] chars:[1786, 1787, "-"]
        Text[1788, 1790] chars:[1788, 1790, ", "]
        Code[1790, 1793] textOpen:[1790, 1791, "`"] text:[1791, 1792, "+"] textClose:[1792, 1793, "`"]
          Text[1791, 1792] chars:[1791, 1792, "+"]
        Text[1793, 1795] chars:[1793, 1795, ", "]
        Code[1795, 1798] textOpen:[1795, 1796, "`"] text:[1796, 1797, "#"] textClose:[1797, 1798, "`"]
          Text[1796, 1797] chars:[1796, 1797, "#"]
        Text[1798, 1799] chars:[1798, 1799, " "]
        Emphasis[1799, 1805] textOpen:[1799, 1800, "_"] text:[1800, 1804, "`N.`"] textClose:[1804, 1805, "_"]
          Code[1800, 1804] textOpen:[1800, 1801, "`"] text:[1801, 1803, "N."] textClose:[1803, 1804, "`"]
            Text[1801, 1803] chars:[1801, 1803, "N."]
        Text[1805, 1812] chars:[1805, 1812, " where "]
        Emphasis[1812, 1815] textOpen:[1812, 1813, "_"] text:[1813, 1814, "N"] textClose:[1814, 1815, "_"]
          Text[1813, 1814] chars:[1813, 1814, "N"]
        Text[1815, 1826] chars:[1815, 1826, " is n … meric"]
        SoftLineBreak[1826, 1827]
        Text[1833, 1840] chars:[1833, 1840, "with a "]
        Code[1840, 1843] textOpen:[1840, 1841, "`"] text:[1841, 1842, "\"] textClose:[1842, 1843, "`"]
          Text[1841, 1842] chars:[1841, 1842, "\"]
        Text[1843, 1919] chars:[1843, 1919, " so t …  of a"]
        SoftLineBreak[1919, 1920]
        Text[1926, 2009] chars:[1926, 2009, "wrapp … ith a"]
        SoftLineBreak[2009, 2010]
        Text[2016, 2105] chars:[2016, 2105, "warni … tem's"]
        SoftLineBreak[2105, 2106]
        Text[2112, 2122] chars:[2112, 2122, "text. The "]
        Code[2122, 2125] textOpen:[2122, 2123, "`"] text:[2123, 2124, "#"] textClose:[2124, 2125, "`"]
          Text[2123, 2124] chars:[2123, 2124, "#"]
        Text[2125, 2195] chars:[2125, 2195, " affe … lands"]
        SoftLineBreak[2195, 2196]
        Text[2202, 2288] chars:[2202, 2288, "exact … paces"]
        SoftLineBreak[2288, 2289]
        Text[2295, 2315] chars:[2295, 2315, "befor … ings."]
    BulletListItem[2316, 2399] open:[2316, 2317, "*"] isTight
      Paragraph[2318, 2399]
        LinkRef[2318, 2321] referenceOpen:[2318, 2319, "["] reference:[2320, 2320] referenceClose:[2320, 2321, "]"]
          Text[2320, 2320]
        Text[2321, 2398] chars:[2321, 2398, " Add: …  item"]
    BulletListItem[2399, 2450] open:[2399, 2400, "*"] isTight
      Paragraph[2401, 2450]
        LinkRef[2401, 2404] referenceOpen:[2401, 2402, "["] reference:[2403, 2403] referenceClose:[2403, 2404, "]"]
          Text[2403, 2403]
        Text[2404, 2449] chars:[2404, 2449, " Fix: … nMark"]
    BulletListItem[2450, 2687] open:[2450, 2451, "*"] isTight
      Paragraph[2452, 2687]
        LinkRef[2452, 2455] referenceOpen:[2452, 2453, "["] reference:[2454, 2454] referenceClose:[2454, 2455, "]"]
          Text[2454, 2454]
        Text[2455, 2528] chars:[2455, 2528, " Add: … hese:"]
        SoftLineBreak[2528, 2529]
        Text[2535, 2537] chars:[2535, 2537, "* "]
        LinkRef[2537, 2540] referenceOpen:[2537, 2538, "["] reference:[2539, 2539] referenceClose:[2539, 2540, "]"]
          Text[2539, 2539]
        Text[2540, 2568] chars:[2540, 2568, " Comm … ments"]
        SoftLineBreak[2568, 2569]
        Text[2575, 2577] chars:[2575, 2577, "* "]
        LinkRef[2577, 2580] referenceOpen:[2577, 2578, "["] reference:[2579, 2579] referenceClose:[2579, 2580, "]"]
          Text[2579, 2579]
        Text[2580, 2629] chars:[2580, 2629, " Kram … ekyll"]
        SoftLineBreak[2629, 2630]
        Text[2636, 2638] chars:[2636, 2638, "* "]
        LinkRef[2638, 2641] referenceOpen:[2638, 2639, "["] reference:[2640, 2640] referenceClose:[2640, 2641, "]"]
          Text[2640, 2640]
        Text[2641, 2686] chars:[2641, 2686, " Fixe … gdown"]
    BulletListItem[2687, 2856] open:[2687, 2688, "*"] isTight
      Paragraph[2689, 2856]
        LinkRef[2689, 2692] referenceOpen:[2689, 2690, "["] reference:[2691, 2691] referenceClose:[2691, 2692, "]"]
          Text[2691, 2691]
        Text[2692, 2698] chars:[2692, 2698, " Add: "]
        Code[2698, 2727] textOpen:[2698, 2699, "`"] text:[2699, 2726, "PARSE … _JEKYLL_MACROS_IN_URLS"] textClose:[2726, 2727, "`"]
          Text[2699, 2726] chars:[2699, 2726, "PARSE … _URLS"]
        Text[2727, 2778] chars:[2727, 2778, " opti … nable"]
        SoftLineBreak[2778, 2779]
        Text[2785, 2855] chars:[2785, 2855, "parsi … aces."]
    BulletListItem[2856, 3077] open:[2856, 2857, "*"] isTight
      Paragraph[2858, 3077]
        LinkRef[2858, 2861] referenceOpen:[2858, 2859, "["] reference:[2860, 2860] referenceClose:[2860, 2861, "]"]
          Text[2860, 2860]
        Text[2861, 2952] chars:[2861, 2952, " Fix: … : see"]
        SoftLineBreak[2952, 2953]
        Code[2959, 2974] textOpen:[2959, 2960, "`"] text:[2960, 2973, "Exten … sions.md"] textClose:[2973, 2974, "`"]
          Text[2960, 2973] chars:[2960, 2973, "Exten … ns.md"]
        Text[2974, 2978] chars:[2974, 2978, " in "]
        Code[2978, 2993] textOpen:[2978, 2979, "`"] text:[2979, 2992, "flexm … ark-java"] textClose:[2992, 2993, "`"]
          Text[2979, 2992] chars:[2979, 2992, "flexm … -java"]
        Text[2993, 3049] chars:[2993, 3049, " wiki … olbar"]
        SoftLineBreak[3049, 3050]
        Text[3056, 3076] chars:[3056, 3076, "butto … date."]
    BulletListItem[3077, 3249] open:[3077, 3078, "*"] isTight
      Paragraph[3079, 3249]
        LinkRef[3079, 3082] referenceOpen:[3079, 3080, "["] reference:[3081, 3081] referenceClose:[3081, 3082, "]"]
          Text[3081, 3081]
        Text[3082, 3172] chars:[3082, 3172, " Fix: … rrent"]
        SoftLineBreak[3172, 3173]
        Text[3179, 3248] chars:[3179, 3248, "item  … item."]
    BulletListItem[3249, 3918] open:[3249, 3250, "*"] isTight
      Paragraph[3251, 3918]
        LinkRef[3251, 3254] referenceOpen:[3251, 3252, "["] reference:[3253, 3253] referenceClose:[3253, 3254, "]"]
          Text[3253, 3253]
        Text[3254, 3308] chars:[3254, 3308, " Add: … vior."]
        SoftLineBreak[3308, 3309]
        Text[3315, 3317] chars:[3315, 3317, "- "]
        LinkRef[3317, 3320] referenceOpen:[3317, 3318, "["] reference:[3318, 3319, "x"] referenceClose:[3319, 3320, "]"]
          Text[3318, 3319] chars:[3318, 3319, "x"]
        Text[3320, 3401] chars:[3320, 3401, " Add: … pose."]
        SoftLineBreak[3401, 3402]
        Text[3408, 3410] chars:[3408, 3410, "- "]
        LinkRef[3410, 3413] referenceOpen:[3410, 3411, "["] reference:[3411, 3412, "x"] referenceClose:[3412, 3413, "]"]
          Text[3411, 3412] chars:[3411, 3412, "x"]
        Text[3413, 3493] chars:[3413, 3493, " Fix: …  list"]
        SoftLineBreak[3493, 3494]
        Text[3506, 3526] chars:[3506, 3526, "proce … ings."]
        SoftLineBreak[3526, 3527]
        Text[3533, 3535] chars:[3533, 3535, "* "]
        LinkRef[3535, 3538] referenceOpen:[3535, 3536, "["] reference:[3536, 3537, "x"] referenceClose:[3537, 3538, "]"]
          Text[3536, 3537] chars:[3536, 3537, "x"]
        Text[3538, 3621] chars:[3538, 3621, " Fix: … 4 + 3"]
        SoftLineBreak[3621, 3622]
        Text[3634, 3694] chars:[3634, 3694, "in fi …  mode"]
        SoftLineBreak[3694, 3695]
        Text[3701, 3703] chars:[3701, 3703, "- "]
        LinkRef[3703, 3706] referenceOpen:[3703, 3704, "["] reference:[3705, 3705] referenceClose:[3705, 3706, "]"]
          Text[3705, 3705]
        Text[3706, 3787] chars:[3706, 3787, " Fix: … o the"]
        SoftLineBreak[3787, 3788]
        Text[3800, 3883] chars:[3800, 3883, "paren … t now"]
        SoftLineBreak[3883, 3884]
        Text[3896, 3917] chars:[3896, 3917, "works … ixed4"]
    BulletListItem[3918, 4273] open:[3918, 3919, "*"] isTight
      Paragraph[3920, 4273]
        LinkRef[3920, 3923] referenceOpen:[3920, 3921, "["] reference:[3922, 3922] referenceClose:[3922, 3923, "]"]
          Text[3922, 3922]
        Text[3923, 3972] chars:[3923, 3972, " Add: … vior."]
        SoftLineBreak[3972, 3973]
        Text[3979, 3981] chars:[3979, 3981, "* "]
        LinkRef[3981, 3984] referenceOpen:[3981, 3982, "["] reference:[3983, 3983] referenceClose:[3983, 3984, "]"]
          Text[3983, 3983]
        Text[3984, 4067] chars:[3984, 4067, " GitH … refix"]
        SoftLineBreak[4067, 4068]
        Text[4080, 4092] chars:[4080, 4092, "marke … start"]
        SoftLineBreak[4092, 4093]
        Text[4099, 4101] chars:[4099, 4101, "* "]
        LinkRef[4101, 4104] referenceOpen:[4101, 4102, "["] reference:[4103, 4103] referenceClose:[4103, 4104, "]"]
          Text[4103, 4103]
        Text[4104, 4186] chars:[4104, 4186, " Comm … m for"]
        SoftLineBreak[4186, 4187]
        Text[4199, 4260] chars:[4199, 4260, "prefi … ndent"]
        SoftLineBreak[4260, 4261]
        Text[4267, 4269] chars:[4267, 4269, "* "]
        LinkRef[4269, 4272] referenceOpen:[4269, 4270, "["] reference:[4271, 4271] referenceClose:[4271, 4272, "]"]
          Text[4271, 4271]
  BulletList[4273, 4380] isTight
    BulletListItem[4273, 4380] open:[4273, 4274, "-"] isTight hadBlankLineAfter
      Paragraph[4275, 4380] isTrailingBlankLine
        LinkRef[4275, 4278] referenceOpen:[4275, 4276, "["] reference:[4277, 4277] referenceClose:[4277, 4278, "]"]
          Text[4277, 4277]
        Text[4278, 4367] chars:[4278, 4367, " Fix: …  text"]
        SoftLineBreak[4367, 4368]
        Text[4374, 4379] chars:[4374, 4379, "edge."]
  Heading[4381, 4405] textOpen:[4381, 4386, "#####"] text:[4387, 4405, "Next Release To Do"]
    Text[4387, 4405] chars:[4387, 4405, "Next  … To Do"]
  BulletList[4407, 6059] isTight
    BulletListItem[4407, 4585] open:[4407, 4408, "*"] isTight
      Paragraph[4409, 4585]
        LinkRef[4409, 4412] referenceOpen:[4409, 4410, "["] reference:[4411, 4411] referenceClose:[4411, 4412, "]"]
          Text[4411, 4411]
        Text[4412, 4480] chars:[4412, 4480, " Add: …  the "]
        Code[4480, 4489] textOpen:[4480, 4481, "`"] text:[4481, 4488, "details"] textClose:[4488, 4489, "`"]
          Text[4481, 4488] chars:[4481, 4488, "details"]
        Text[4489, 4500] chars:[4489, 4500, " open … r and"]
        SoftLineBreak[4500, 4501]
        Text[4507, 4584] chars:[4507, 4584, "Colla … tate."]
    BulletListItem[4585, 4706] open:[4585, 4586, "*"] isTight
      Paragraph[4587, 4706]
        LinkRef[4587, 4590] referenceOpen:[4587, 4588, "["] reference:[4589, 4589] referenceClose:[4589, 4590, "]"]
          Text[4589, 4589]
        Text[4590, 4679] chars:[4590, 4679, " Add: …  when"]
        SoftLineBreak[4679, 4680]
        Text[4686, 4705] chars:[4686, 4705, "the d … pens."]
    BulletListItem[4706, 5014] open:[4706, 4707, "*"] isTight
      Paragraph[4708, 5014]
        LinkRef[4708, 4711] referenceOpen:[4708, 4709, "["] reference:[4710, 4710] referenceClose:[4710, 4711, "]"]
          Text[4710, 4710]
        Text[4711, 4798] chars:[4711, 4798, " Fix: … these"]
        SoftLineBreak[4798, 4799]
        Text[4805, 4893] chars:[4805, 4893, "refer … Put a"]
        SoftLineBreak[4893, 4894]
        Text[4900, 4985] chars:[4900, 4985, "check … ll be"]
        SoftLineBreak[4985, 4986]
        Text[4992, 5013] chars:[4992, 5013, "delet … aste."]
    BulletListItem[5014, 5067] open:[5014, 5015, "*"] isTight
      Paragraph[5016, 5067]
        LinkRef[5016, 5019] referenceOpen:[5016, 5017, "["] reference:[5018, 5018] referenceClose:[5018, 5019, "]"]
          Text[5018, 5018]
        Text[5019, 5066] chars:[5019, 5066, " Fix: … ener."]
    BulletListItem[5067, 5177] open:[5067, 5068, "*"] isTight
      Paragraph[5069, 5177]
        LinkRef[5069, 5072] referenceOpen:[5069, 5070, "["] reference:[5071, 5071] referenceClose:[5071, 5072, "]"]
          Text[5071, 5071]
        Text[5072, 5163] chars:[5072, 5163, " Add: … f the"]
        SoftLineBreak[5163, 5164]
        Text[5170, 5176] chars:[5170, 5176, "links."]
    BulletListItem[5177, 5247] open:[5177, 5178, "*"] isTight
      Paragraph[5179, 5247]
        LinkRef[5179, 5182] referenceOpen:[5179, 5180, "["] reference:[5181, 5181] referenceClose:[5181, 5182, "]"]
          Text[5181, 5181]
        Text[5182, 5246] chars:[5182, 5246, " Fix: …  item"]
    BulletListItem[5247, 5447] open:[5247, 5248, "*"] isTight
      Paragraph[5249, 5447]
        LinkRef[5249, 5252] referenceOpen:[5249, 5250, "["] reference:[5251, 5251] referenceClose:[5251, 5252, "]"]
          Text[5251, 5251]
        Text[5252, 5336] chars:[5252, 5336, " Fix: … oject"]
        SoftLineBreak[5336, 5337]
        Text[5343, 5430] chars:[5343, 5430, "setti … n the"]
        SoftLineBreak[5430, 5431]
        Text[5437, 5446] chars:[5437, 5446, "document."]
    BulletListItem[5447, 5617] open:[5447, 5448, "*"] isTight
      Paragraph[5449, 5617]
        LinkRef[5449, 5452] referenceOpen:[5449, 5450, "["] reference:[5451, 5451] referenceClose:[5451, 5452, "]"]
          Text[5451, 5451]
        Text[5452, 5466] chars:[5452, 5466, " Fix: … k Map"]
        SoftLineBreak[5466, 5467]
        Text[5473, 5475] chars:[5473, 5475, "* "]
        LinkRef[5475, 5478] referenceOpen:[5475, 5476, "["] reference:[5477, 5477] referenceClose:[5477, 5478, "]"]
          Text[5477, 5477]
        Text[5478, 5489] chars:[5478, 5489, " impl … ment "]
        Code[5489, 5527] textOpen:[5489, 5490, "`"] text:[5490, 5526, "Expan … dedItemRendererComponentWrapper"] textClose:[5526, 5527, "`"]
          Text[5490, 5526] chars:[5490, 5526, "Expan … apper"]
        Text[5527, 5555] chars:[5527, 5555, " for  … t the"]
        SoftLineBreak[5555, 5556]
        Text[5568, 5616] chars:[5568, 5616, "exten … ltip."]
    BulletListItem[5617, 5803] open:[5617, 5618, "*"] isTight
      Paragraph[5619, 5803]
        LinkRef[5619, 5622] referenceOpen:[5619, 5620, "["] reference:[5621, 5621] referenceClose:[5621, 5622, "]"]
          Text[5621, 5621]
        Text[5622, 5713] chars:[5622, 5713, " Add: … tions"]
        SoftLineBreak[5713, 5714]
        Text[5720, 5802] chars:[5720, 5802, "for e … gned."]
    BulletListItem[5803, 5937] open:[5803, 5804, "*"] isTight
      Paragraph[5805, 5937]
        LinkRef[5805, 5808] referenceOpen:[5805, 5806, "["] reference:[5807, 5807] referenceClose:[5807, 5808, "]"]
          Text[5807, 5807]
        Text[5808, 5897] chars:[5808, 5897, " Fix: …  drop"]
        SoftLineBreak[5897, 5898]
        Text[5904, 5936] chars:[5904, 5936, "down  …  fit."]
    BulletListItem[5937, 5996] open:[5937, 5938, "*"] isTight
      Paragraph[5939, 5996]
        LinkRef[5939, 5942] referenceOpen:[5939, 5940, "["] reference:[5941, 5941] referenceClose:[5941, 5942, "]"]
          Text[5941, 5941]
        Text[5942, 5995] chars:[5942, 5995, " Add: … indow"]
    BulletListItem[5996, 6059] open:[5996, 5997, "*"] isTight
      Paragraph[5998, 6059]
        LinkRef[5998, 6001] referenceOpen:[5998, 5999, "["] reference:[6000, 6000] referenceClose:[6000, 6001, "]"]
          Text[6000, 6000]
        Text[6001, 6058] chars:[6001, 6058, " Add: … views"]
  BulletList[6059, 6126] isTight
    BulletListItem[6059, 6126] open:[6059, 6060, "-"] isTight
      Paragraph[6061, 6126]
        LinkRef[6061, 6064] referenceOpen:[6061, 6062, "["] reference:[6063, 6063] referenceClose:[6063, 6064, "]"]
          Text[6063, 6063]
        Text[6064, 6125] chars:[6064, 6125, " Add: … tings"]
  BulletList[6126, 6214] isTight
    BulletListItem[6126, 6214] open:[6126, 6127, "*"] isTight hadBlankLineAfter
      Paragraph[6128, 6214] isTrailingBlankLine
        LinkRef[6128, 6131] referenceOpen:[6128, 6129, "["] reference:[6130, 6130] referenceClose:[6130, 6131, "]"]
          Text[6130, 6130]
        Text[6131, 6151] chars:[6131, 6151, " Add: …  for "]
        StrongEmphasis[6151, 6161] textOpen:[6151, 6153, "**"] text:[6153, 6159, "GitHub"] textClose:[6159, 6161, "**"]
          Text[6153, 6159] chars:[6153, 6159, "GitHub"]
        Text[6161, 6213] chars:[6161, 6213, " issu … ined."]
  Paragraph[6215, 6232] isTrailingBlankLine
    HtmlEntity[6215, 6221] "&nbsp;"
    HtmlInline[6221, 6231] chars:[6221, 6231, "</details>"]
  Heading[6233, 6283] textOpen:[6233, 6236, "###"] text:[6237, 6283, "2.2.0.12 - Compatibility & Enhancement Release"]
    Text[6237, 6283] chars:[6237, 6283, "2.2.0 … lease"]
  BulletList[6285, 12304] isTight
    BulletListItem[6285, 6505] open:[6285, 6286, "*"] isTight
      Paragraph[6287, 6505]
        LinkRef[6287, 6290] referenceOpen:[6287, 6288, "["] reference:[6289, 6289] referenceClose:[6289, 6290, "]"]
          Text[6289, 6289]
        Text[6290, 6372] chars:[6290, 6372, " Add: … order"]
        SoftLineBreak[6372, 6373]
        Text[6379, 6464] chars:[6379, 6464, "other … items"]
        SoftLineBreak[6464, 6465]
        Text[6471, 6504] chars:[6471, 6504, "sorte … tems."]
    BulletListItem[6505, 6677] open:[6505, 6506, "*"] isTight
      Paragraph[6507, 6677]
        Text[6507, 6592] chars:[6507, 6592, "Fix:  … nd no"]
        SoftLineBreak[6592, 6593]
        Text[6595, 6676] chars:[6595, 6676, "inter … kers."]
    BulletListItem[6677, 7023] open:[6677, 6678, "*"] isTight
      Paragraph[6679, 7023]
        Text[6679, 6766] chars:[6679, 6766, "Add:  … ap by"]
        SoftLineBreak[6766, 6767]
        Text[6769, 6846] chars:[6769, 6846, "defau … fault"]
        SoftLineBreak[6846, 6847]
        Text[6849, 6882] chars:[6849, 6882, "punct … ngs: "]
        Code[6882, 6890] textOpen:[6882, 6883, "`"] text:[6883, 6889, ".,;:!?"] textClose:[6889, 6890, "`"]
          Text[6883, 6889] chars:[6883, 6889, ".,;:!?"]
        Text[6890, 6936] chars:[6890, 6936, ". If  …  then"]
        SoftLineBreak[6936, 6937]
        Text[6939, 7022] chars:[6939, 7022, "defau … ters."]
    BulletListItem[7023, 7262] open:[7023, 7024, "*"] isTight
      Paragraph[7025, 7262]
        Text[7025, 7063] chars:[7025, 7063, "Chang … ings "]
        Code[7063, 7113] textOpen:[7063, 7064, "`"] text:[7064, 7112, "Inlin … e code toggle like other text style actions"] textClose:[7112, 7113, "`"]
          Text[7064, 7112] chars:[7064, 7112, "Inlin … tions"]
        SoftLineBreak[7113, 7114]
        Text[7116, 7203] chars:[7116, 7203, "chang … ad of"]
        SoftLineBreak[7203, 7204]
        Text[7206, 7261] chars:[7206, 7261, "conti … word."]
    BulletListItem[7262, 7447] open:[7262, 7263, "*"] isTight
      Paragraph[7264, 7447]
        Text[7264, 7292] chars:[7264, 7292, "Fix:  … s to "]
        Code[7292, 7310] textOpen:[7292, 7293, "`"] text:[7293, 7309, "raw/m … aster/*.png"] textClose:[7309, 7310, "`"]
          Text[7293, 7309] chars:[7293, 7309, "raw/m … *.png"]
        Text[7310, 7357] chars:[7310, 7357, " show … cause"]
        SoftLineBreak[7357, 7358]
        Text[7360, 7446] chars:[7360, 7446, "only  … iles."]
    BulletListItem[7447, 7505] open:[7447, 7448, "*"] isTight
      Paragraph[7449, 7505]
        Text[7449, 7485] chars:[7449, 7485, "Fix:  … fter "]
        Code[7485, 7491] textOpen:[7485, 7486, "`"] text:[7486, 7490, "#   "] textClose:[7490, 7491, "`"]
          Text[7486, 7490] chars:[7486, 7490, "#   "]
        Text[7491, 7504] chars:[7491, 7504, " did  … hing."]
    BulletListItem[7505, 7551] open:[7505, 7506, "*"] isTight
      Paragraph[7507, 7551]
        Text[7507, 7550] chars:[7507, 7550, "Fix:  … oken."]
    BulletListItem[7551, 7629] open:[7551, 7552, "*"] isTight
      Paragraph[7553, 7629]
        Text[7553, 7628] chars:[7553, 7628, "Fix:  … items"]
    BulletListItem[7629, 7862] open:[7629, 7630, "*"] isTight
      Paragraph[7631, 7862]
        Text[7631, 7717] chars:[7631, 7717, "Fix:  … r the"]
        SoftLineBreak[7717, 7718]
        Text[7724, 7812] chars:[7724, 7812, "first … d not"]
        SoftLineBreak[7812, 7813]
        Text[7819, 7861] chars:[7819, 7861, "add b …  all."]
    BulletListItem[7862, 7987] open:[7862, 7863, "*"] isTight
      Paragraph[7864, 7898]
        Text[7864, 7897] chars:[7864, 7897, "Add:  …  for:"]
      BulletList[7902, 7987] isTight
        BulletListItem[7902, 7917] open:[7902, 7903, "*"] isTight
          Paragraph[7904, 7917]
            Text[7904, 7916] chars:[7904, 7916, "fence … code,"]
        BulletListItem[7921, 7943] open:[7921, 7922, "*"] isTight
          Paragraph[7923, 7943]
            Text[7923, 7942] chars:[7923, 7942, "colla … ions,"]
        BulletListItem[7947, 7987] open:[7947, 7948, "*"] isTight
          Paragraph[7949, 7987]
            Text[7949, 7959] chars:[7949, 7959, "@formatter"]
            Emoji[7959, 7975] textOpen:[7959, 7960, ":"] text:[7960, 7974, "off/@formatter"] textClose:[7974, 7975, ":"]
              Text[7960, 7974] chars:[7960, 7974, "off/@ … atter"]
            Text[7975, 7986] chars:[7975, 7986, "on co … ments"]
    BulletListItem[7987, 8030] open:[7987, 7988, "*"] isTight
      Paragraph[7989, 8030]
        Text[7989, 8029] chars:[7989, 8029, "Add:  … lates"]
    BulletListItem[8030, 8156] open:[8030, 8031, "*"] isTight
      Paragraph[8032, 8156]
        Text[8032, 8126] chars:[8032, 8126, "Fix:  … ouble"]
        SoftLineBreak[8126, 8127]
        Text[8129, 8155] chars:[8129, 8155, "bread … pear."]
    BulletListItem[8156, 8214] open:[8156, 8157, "*"] isTight
      Paragraph[8158, 8214]
        Text[8158, 8213] chars:[8158, 8213, "Add:  … -java"]
    BulletListItem[8214, 8282] open:[8214, 8215, "*"] isTight
      Paragraph[8216, 8282]
        Text[8216, 8281] chars:[8216, 8281, "Fix:  …  code"]
    BulletListItem[8282, 8450] open:[8282, 8283, "*"] isTight
      Paragraph[8284, 8450]
        Text[8284, 8372] chars:[8284, 8372, "Chang … ith a"]
        SoftLineBreak[8372, 8373]
        Text[8375, 8449] chars:[8375, 8449, "warni … ence."]
    BulletListItem[8450, 8536] open:[8450, 8451, "*"] isTight
      Paragraph[8452, 8536]
        Text[8452, 8457] chars:[8452, 8457, "Add: "]
        Code[8457, 8472] textOpen:[8457, 8458, "`"] text:[8458, 8471, "<html … ></html>"] textClose:[8471, 8472, "`"]
          Text[8458, 8471] chars:[8458, 8471, "<html … html>"]
        Text[8472, 8489] chars:[8472, 8489, " wrap … s to "]
        Code[8489, 8510] textOpen:[8489, 8490, "`"] text:[8490, 8509, "JavaF … xHtmlGenerator"] textClose:[8509, 8510, "`"]
          Text[8490, 8509] chars:[8490, 8509, "JavaF … rator"]
        Text[8510, 8515] chars:[8510, 8515, " and "]
        Code[8515, 8535] textOpen:[8515, 8516, "`"] text:[8516, 8534, "Swing … HtmlGenerator"] textClose:[8534, 8535, "`"]
          Text[8516, 8534] chars:[8516, 8534, "Swing … rator"]
    BulletListItem[8536, 8604] open:[8536, 8537, "*"] isTight
      Paragraph[8538, 8604]
        Text[8538, 8543] chars:[8538, 8543, "Add: "]
        Code[8543, 8556] textOpen:[8543, 8544, "`"] text:[8544, 8555, "NO_FI … LE_EOL"] textClose:[8555, 8556, "`"]
          Text[8544, 8555] chars:[8544, 8555, "NO_FI … E_EOL"]
        Text[8556, 8603] chars:[8556, 8603, " to f … ption"]
    BulletListItem[8604, 8745] open:[8604, 8605, "*"] isTight
      Paragraph[8606, 8745]
        Text[8606, 8695] chars:[8606, 8695, "Add:  … arser"]
        SoftLineBreak[8695, 8696]
        Text[8698, 8744] chars:[8698, 8744, "optio …  mime"]
    BulletListItem[8745, 9143] open:[8745, 8746, "*"] isTight
      Paragraph[8747, 8811]
        Text[8747, 8810] chars:[8747, 8810, "Add:  … ions:"]
      BulletList[8815, 9143] isTight
        BulletListItem[8815, 8886] open:[8815, 8816, "-"] isTight
          Paragraph[8817, 8886]
            Text[8817, 8885] chars:[8817, 8885, "hiera … order"]
        BulletListItem[8890, 8938] open:[8890, 8891, "-"] isTight
          Paragraph[8892, 8938]
            Text[8892, 8937] chars:[8892, 8937, "flat: … order"]
        BulletListItem[8942, 9011] open:[8942, 8943, "-"] isTight
          Paragraph[8944, 9011]
            Text[8944, 9010] chars:[8944, 9010, "rever … order"]
        BulletListItem[9015, 9077] open:[9015, 9016, "-"] isTight
          Paragraph[9017, 9077]
            Text[9017, 9076] chars:[9017, 9076, "incre …  text"]
        BulletListItem[9081, 9143] open:[9081, 9082, "-"] isTight
          Paragraph[9083, 9143]
            Text[9083, 9142] chars:[9083, 9142, "decre …  text"]
    BulletListItem[9143, 9333] open:[9143, 9144, "*"] isTight
      Paragraph[9145, 9333]
        Text[9145, 9237] chars:[9145, 9237, "Chang … ility"]
        SoftLineBreak[9237, 9238]
        Text[9240, 9332] chars:[9240, 9332, "of wr … item."]
    BulletListItem[9333, 9428] open:[9333, 9334, "*"] isTight
      Paragraph[9335, 9428]
        Text[9335, 9390] chars:[9335, 9390, "Add:  …  the "]
        Code[9390, 9406] textOpen:[9390, 9391, "`"] text:[9391, 9405, "@form … atter:off"] textClose:[9405, 9406, "`"]
          Text[9391, 9405] chars:[9391, 9405, "@form … r:off"]
        Text[9406, 9407] chars:[9406, 9407, "/"]
        Code[9407, 9422] textOpen:[9407, 9408, "`"] text:[9408, 9421, "@form … atter:on"] textClose:[9421, 9422, "`"]
          Text[9408, 9421] chars:[9408, 9421, "@form … er:on"]
        Text[9422, 9427] chars:[9422, 9427, " tags"]
    BulletListItem[9428, 9511] open:[9428, 9429, "*"] isTight
      Paragraph[9430, 9511]
        Text[9430, 9510] chars:[9430, 9510, "Chang … ible."]
    BulletListItem[9511, 9649] open:[9511, 9512, "*"] isTight
      Paragraph[9513, 9649]
        Text[9513, 9595] chars:[9513, 9595, "Fix:  … me is"]
        SoftLineBreak[9595, 9596]
        Code[9598, 9614] textOpen:[9598, 9599, "`"] text:[9599, 9613, "COPY_ … HTML_MIME"] textClose:[9613, 9614, "`"]
          Text[9599, 9613] chars:[9599, 9613, "COPY_ … _MIME"]
        Text[9614, 9648] chars:[9614, 9648, ", oth … ults."]
    BulletListItem[9649, 9795] open:[9649, 9650, "*"] isTight
      Paragraph[9651, 9795]
        Text[9651, 9739] chars:[9651, 9739, "Fix:  …  when"]
        SoftLineBreak[9739, 9740]
        Text[9742, 9794] chars:[9742, 9794, "getti …  row."]
    BulletListItem[9795, 10858] open:[9795, 9796, "*"] isTight
      Paragraph[9797, 10858]
        Text[9797, 9802] chars:[9797, 9802, "Add: "]
        Code[9802, 9867] textOpen:[9802, 9803, "`"] text:[9803, 9866, "Copy  … markdown document or selection as HTML mime formatted text"] textClose:[9866, 9867, "`"]
          Text[9803, 9866] chars:[9803, 9866, "Copy  …  text"]
        Text[9867, 9889] chars:[9867, 9889, " acti …  copy"]
        SoftLineBreak[9889, 9890]
        Text[9892, 9984] chars:[9892, 9984, "docum …  into"]
        SoftLineBreak[9984, 9985]
        Text[9987, 10081] chars:[9987, 10081, "appli … ails."]
        SoftLineBreak[10081, 10082]
        Text[10084, 10174] chars:[10084, 10174, "To ov … named"]
        SoftLineBreak[10174, 10175]
        Code[10177, 10193] textOpen:[10177, 10178, "`"] text:[10178, 10192, "COPY_ … HTML_MIME"] textClose:[10192, 10193, "`"]
          Text[10178, 10192] chars:[10178, 10192, "COPY_ … _MIME"]
        Text[10193, 10220] chars:[10193, 10220, " and  … . Use"]
        SoftLineBreak[10220, 10221]
        Code[10223, 10278] textOpen:[10223, 10224, "`"] text:[10224, 10277, "/com/ … vladsch/idea/multimarkdown/html_mime_default.css"] textClose:[10277, 10278, "`"]
          Text[10224, 10277] chars:[10224, 10277, "/com/ … t.css"]
        Text[10278, 10312] chars:[10278, 10312, " as a … style"]
        SoftLineBreak[10312, 10313]
        Text[10315, 10407] chars:[10315, 10407, "setti … t and"]
        SoftLineBreak[10407, 10408]
        Text[10410, 10504] chars:[10410, 10504, "there … style"]
        SoftLineBreak[10504, 10505]
        Text[10507, 10600] chars:[10507, 10600, "attri … which"]
        SoftLineBreak[10600, 10601]
        Text[10603, 10691] chars:[10603, 10691, "at th … kdown"]
        SoftLineBreak[10691, 10692]
        Text[10694, 10787] chars:[10694, 10787, "eleme …  such"]
        SoftLineBreak[10787, 10788]
        Text[10790, 10794] chars:[10790, 10794, "as: "]
        Code[10794, 10802] textOpen:[10794, 10795, "`"] text:[10795, 10801, "tr.odd"] textClose:[10801, 10802, "`"]
          Text[10795, 10801] chars:[10795, 10801, "tr.odd"]
        Text[10802, 10804] chars:[10802, 10804, ", "]
        Code[10804, 10813] textOpen:[10804, 10805, "`"] text:[10805, 10812, "tr.even"] textClose:[10812, 10813, "`"]
          Text[10805, 10812] chars:[10805, 10812, "tr.even"]
        Text[10813, 10818] chars:[10813, 10818, " and "]
        Code[10818, 10828] textOpen:[10818, 10819, "`"] text:[10819, 10827, "li.loose"] textClose:[10827, 10828, "`"]
          Text[10819, 10827] chars:[10819, 10827, "li.loose"]
        Text[10828, 10857] chars:[10828, 10857, " base …  AST."]
    BulletListItem[10858, 11121] open:[10858, 10859, "*"] isTight
      Paragraph[10860, 11121]
        Text[10860, 10950] chars:[10860, 10950, "Add:  … y the"]
        SoftLineBreak[10950, 10951]
        Text[10953, 11043] chars:[10953, 11043, "first … power"]
        SoftLineBreak[11043, 11044]
        Text[11046, 11120] chars:[11046, 11120, "down  … rked."]
    BulletListItem[11121, 11282] open:[11121, 11122, "*"] isTight
      Paragraph[11123, 11282]
        Text[11123, 11214] chars:[11123, 11214, "Fix:  … n the"]
        SoftLineBreak[11214, 11215]
        Text[11217, 11281] chars:[11217, 11281, "code. … HTML."]
    BulletListItem[11282, 11388] open:[11282, 11283, "*"] isTight
      Paragraph[11284, 11388]
        Text[11284, 11343] chars:[11284, 11343, "Fix:  … t if "]
        Code[11343, 11357] textOpen:[11343, 11344, "`"] text:[11344, 11356, "Ancho … r Links"] textClose:[11356, 11357, "`"]
          Text[11344, 11356] chars:[11344, 11356, "Ancho … Links"]
        Text[11357, 11375] chars:[11357, 11375, " pars … n was"]
        SoftLineBreak[11375, 11376]
        Text[11378, 11387] chars:[11378, 11387, "selected."]
    BulletListItem[11388, 11613] open:[11388, 11389, "*"] isTight
      Paragraph[11390, 11613]
        Text[11390, 11480] chars:[11390, 11480, "Add:  … y the"]
        SoftLineBreak[11480, 11481]
        Text[11483, 11573] chars:[11483, 11573, "first … power"]
        SoftLineBreak[11573, 11574]
        Text[11576, 11612] chars:[11576, 11612, "down  … lled."]
    BulletListItem[11613, 11651] open:[11613, 11614, "*"] isTight
      Paragraph[11615, 11651]
        Text[11615, 11650] chars:[11615, 11650, "Add:  … pport"]
    BulletListItem[11651, 11719] open:[11651, 11652, "*"] isTight
      Paragraph[11653, 11719]
        Text[11653, 11718] chars:[11653, 11718, "Add:  … ences"]
    BulletListItem[11719, 11790] open:[11719, 11720, "*"] isTight
      Paragraph[11721, 11790]
        Text[11721, 11789] chars:[11721, 11789, "Fix:  … quote"]
    BulletListItem[11790, 11851] open:[11790, 11791, "*"] isTight
      Paragraph[11792, 11851]
        Text[11792, 11850] chars:[11792, 11850, "Add:  … ions."]
    BulletListItem[11851, 11922] open:[11851, 11852, "*"] isTight
      Paragraph[11853, 11922]
        Text[11853, 11921] chars:[11853, 11921, "Add:  … ions."]
    BulletListItem[11922, 12048] open:[11922, 11923, "*"] isTight
      Paragraph[11924, 12048]
        Text[11924, 11954] chars:[11924, 11954, "Add:  … tion "]
        Code[11954, 11982] textOpen:[11954, 11955, "`"] text:[11955, 11981, "Space …  before language info"] textClose:[11981, 11982, "`"]
          Text[11955, 11981] chars:[11955, 11981, "Space …  info"]
        Text[11982, 12013] chars:[11982, 12013, " to p … ening"]
        SoftLineBreak[12013, 12014]
        Text[12016, 12047] chars:[12016, 12047, "marke … tring"]
    BulletListItem[12048, 12130] open:[12048, 12049, "*"] isTight
      Paragraph[12050, 12130]
        Text[12050, 12129] chars:[12050, 12129, "Fix:  … mode."]
    BulletListItem[12130, 12304] open:[12130, 12131, "*"] isTight hadBlankLineAfter
      Paragraph[12132, 12304] isTrailingBlankLine
        Text[12132, 12223] chars:[12132, 12223, "Add:  … imple"]
        SoftLineBreak[12223, 12224]
        Text[12226, 12303] chars:[12226, 12303, "names … docs."]
  Heading[12305, 12352] textOpen:[12305, 12308, "###"] text:[12309, 12352, "2.2.0 - Compatibility & Enhancement Release"]
    Text[12309, 12352] chars:[12309, 12352, "2.2.0 … lease"]
  Heading[12354, 12384] textOpen:[12354, 12358, "####"] text:[12359, 12384, "Basic & Enhanced Editions"]
    Text[12359, 12384] chars:[12359, 12384, "Basic … tions"]
  BulletList[12386, 13723] isTight
    BulletListItem[12386, 12462] open:[12386, 12387, "-"] isTight
      Paragraph[12388, 12462]
        Text[12388, 12449] chars:[12388, 12449, "Add:  … onic "]
        Code[12449, 12461] textOpen:[12449, 12450, "`"] text:[12450, 12460, ".collapsed"] textClose:[12460, 12461, "`"]
          Text[12450, 12460] chars:[12450, 12460, ".collapsed"]
    BulletListItem[12462, 12513] open:[12462, 12463, "-"] isTight
      Paragraph[12464, 12513]
        Text[12464, 12512] chars:[12464, 12512, "Add:  … ttons"]
    BulletListItem[12513, 12581] open:[12513, 12514, "-"] isTight
      Paragraph[12515, 12581]
        Text[12515, 12580] chars:[12515, 12580, "Chang … ings."]
    BulletListItem[12581, 12676] open:[12581, 12582, "-"] isTight
      Paragraph[12583, 12676]
        Text[12583, 12675] chars:[12583, 12675, "Fix # … onfig"]
    BulletListItem[12676, 12722] open:[12676, 12677, "-"] isTight
      Paragraph[12678, 12722]
        Text[12678, 12697] chars:[12678, 12697, "Chang …  all "]
        StrongEmphasis[12697, 12708] textOpen:[12697, 12699, "**"] text:[12699, 12706, "pegdown"] textClose:[12706, 12708, "**"]
          Text[12699, 12706] chars:[12699, 12706, "pegdown"]
        Text[12708, 12721] chars:[12708, 12721, " depe … ncies"]
    BulletListItem[12722, 12828] open:[12722, 12723, "-"] isTight
      Paragraph[12724, 12828]
        Text[12724, 12814] chars:[12724, 12814, "Chang … ition"]
        SoftLineBreak[12814, 12815]
        Text[12817, 12827] chars:[12817, 12827, "restoring."]
    BulletListItem[12828, 12870] open:[12828, 12829, "-"] isTight
      Paragraph[12830, 12870]
        Text[12830, 12869] chars:[12830, 12869, "Add:  … ditor"]
    BulletListItem[12870, 13041] open:[12870, 12871, "-"] isTight
      Paragraph[12872, 13041]
        Text[12872, 12962] chars:[12872, 12962, "Fix:  … e for"]
        SoftLineBreak[12962, 12963]
        Text[12965, 13040] chars:[12965, 13040, "files … code."]
    BulletListItem[13041, 13147] open:[13041, 13042, "-"] isTight
      Paragraph[13043, 13147]
        Text[13043, 13134] chars:[13043, 13134, "Fix:  … yping"]
        SoftLineBreak[13134, 13135]
        Text[13137, 13146] chars:[13137, 13146, "response."]
    BulletListItem[13147, 13219] open:[13147, 13148, "-"] isTight
      Paragraph[13149, 13219]
        Text[13149, 13218] chars:[13149, 13218, "Add:  … ption"]
    BulletListItem[13219, 13273] open:[13219, 13220, "-"] isTight
      Paragraph[13221, 13273]
        Text[13221, 13272] chars:[13221, 13272, "Fix:  … reeze"]
    BulletListItem[13273, 13324] open:[13273, 13274, "-"] isTight
      Paragraph[13275, 13324]
        Text[13275, 13323] chars:[13275, 13323, "Add:  … cific"]
    BulletListItem[13324, 13389] open:[13324, 13325, "-"] isTight
      Paragraph[13326, 13389]
        Text[13326, 13388] chars:[13326, 13388, "Fix:  … 0.12)"]
    BulletListItem[13389, 13450] open:[13389, 13390, "-"] isTight
      Paragraph[13391, 13450]
        Text[13391, 13449] chars:[13391, 13449, "Fix:  … -java"]
    BulletListItem[13450, 13516] open:[13450, 13451, "-"] isTight
      Paragraph[13452, 13516]
        Text[13452, 13515] chars:[13452, 13515, "Fix:  … lity."]
    BulletListItem[13516, 13676] open:[13516, 13517, "-"] isTight
      Paragraph[13518, 13676]
        Text[13518, 13547] chars:[13518, 13547, "Fix:  …  use "]
        Code[13547, 13550] textOpen:[13547, 13548, "`"] text:[13548, 13549, " "] textClose:[13549, 13550, "`"]
          Text[13548, 13549] chars:[13548, 13549, " "]
        Text[13550, 13552] chars:[13550, 13552, ", "]
        Code[13552, 13555] textOpen:[13552, 13553, "`"] text:[13553, 13554, "-"] textClose:[13554, 13555, "`"]
          Text[13553, 13554] chars:[13553, 13554, "-"]
        Text[13555, 13557] chars:[13555, 13557, ", "]
        Code[13557, 13560] textOpen:[13557, 13558, "`"] text:[13558, 13559, "+"] textClose:[13559, 13560, "`"]
          Text[13558, 13559] chars:[13558, 13559, "+"]
        Text[13560, 13562] chars:[13560, 13562, ", "]
        Code[13562, 13565] textOpen:[13562, 13563, "`"] text:[13563, 13564, "<"] textClose:[13564, 13565, "`"]
          Text[13563, 13564] chars:[13563, 13564, "<"]
        Text[13565, 13569] chars:[13565, 13569, " or "]
        Code[13569, 13572] textOpen:[13569, 13570, "`"] text:[13570, 13571, ">"] textClose:[13571, 13572, "`"]
          Text[13570, 13571] chars:[13570, 13571, ">"]
        Text[13572, 13584] chars:[13572, 13584, " to m … ch a "]
        Code[13584, 13587] textOpen:[13584, 13585, "`"] text:[13585, 13586, "-"] textClose:[13586, 13587, "`"]
          Text[13585, 13586] chars:[13585, 13586, "-"]
        Text[13587, 13611] chars:[13587, 13611, " in t … Added"]
        SoftLineBreak[13611, 13612]
        Text[13614, 13675] chars:[13614, 13675, "stub  … ient."]
    BulletListItem[13676, 13723] open:[13676, 13677, "-"] isTight hadBlankLineAfter
      Paragraph[13678, 13723] isTrailingBlankLine
        Text[13678, 13722] chars:[13678, 13722, "Chang … style"]
  Heading[13724, 13745] textOpen:[13724, 13728, "####"] text:[13729, 13745, "Enhanced Edition"]
    Text[13729, 13745] chars:[13729, 13745, "Enhan … ition"]
  BulletList[13747, 19483] isTight
    BulletListItem[13747, 13823] open:[13747, 13748, "*"] isTight
      Paragraph[13749, 13823]
        Text[13749, 13822] chars:[13749, 13822, "Fix:  …  work"]
    BulletListItem[13823, 13965] open:[13823, 13824, "*"] isTight
      Paragraph[13825, 13965]
        Text[13825, 13861] chars:[13825, 13861, "Add:  … ider "]
        Code[13861, 13881] textOpen:[13861, 13862, "`"] text:[13862, 13880, "Detai … ls tag opener"] textClose:[13880, 13881, "`"]
          Text[13862, 13880] chars:[13862, 13880, "Detai … pener"]
        Text[13881, 13894] chars:[13881, 13894, " to o …  all "]
        Code[13894, 13905] textOpen:[13894, 13895, "`"] text:[13895, 13904, "<details>"] textClose:[13904, 13905, "`"]
          Text[13895, 13904] chars:[13895, 13904, "<details>"]
        Text[13905, 13913] chars:[13905, 13913, " tags in"]
        SoftLineBreak[13913, 13914]
        Text[13916, 13964] chars:[13916, 13964, "previ … iting"]
    BulletListItem[13965, 14013] open:[13965, 13966, "*"] isTight
      Paragraph[13967, 14013]
        Text[13967, 14012] chars:[13967, 14012, "Add:  … ripts"]
    BulletListItem[14013, 14147] open:[14013, 14014, "*"] isTight
      Paragraph[14015, 14147]
        Text[14015, 14106] chars:[14015, 14106, "Fix:  …  that"]
        SoftLineBreak[14106, 14107]
        Text[14109, 14146] chars:[14109, 14146, "affec … iles."]
    BulletListItem[14147, 14236] open:[14147, 14148, "*"] isTight
      Paragraph[14149, 14236]
        Text[14149, 14235] chars:[14149, 14235, "Chang … like."]
    BulletListItem[14236, 14289] open:[14236, 14237, "*"] isTight
      Paragraph[14238, 14289]
        Text[14238, 14288] chars:[14238, 14288, "Add:  …  node"]
    BulletListItem[14289, 14396] open:[14289, 14290, "*"] isTight
      Paragraph[14291, 14396]
        Text[14291, 14351] chars:[14291, 14351, "Add:  … p to "]
        Code[14351, 14359] textOpen:[14351, 14352, "`"] text:[14352, 14358, "unused"] textClose:[14358, 14359, "`"]
          Text[14352, 14358] chars:[14352, 14358, "unused"]
        Text[14359, 14383] chars:[14359, 14383, " link … g can"]
        SoftLineBreak[14383, 14384]
        Text[14386, 14395] chars:[14386, 14395, "be saved."]
    BulletListItem[14396, 14562] open:[14396, 14397, "*"] isTight
      Paragraph[14398, 14562]
        Text[14398, 14491] chars:[14398, 14491, "Add:  … pping"]
        SoftLineBreak[14491, 14492]
        Text[14494, 14561] chars:[14494, 14561, "text  …  type"]
    BulletListItem[14562, 14696] open:[14562, 14563, "*"] isTight
      Paragraph[14564, 14696]
        Text[14564, 14656] chars:[14564, 14656, "Fix:  …  in a"]
        SoftLineBreak[14656, 14657]
        Text[14659, 14695] chars:[14659, 14695, "new l …  one."]
    BulletListItem[14696, 14742] open:[14696, 14697, "*"] isTight
      Paragraph[14698, 14742]
        Text[14698, 14741] chars:[14698, 14741, "Fix:  … mbers"]
    BulletListItem[14742, 14807] open:[14742, 14743, "*"] isTight
      Paragraph[14744, 14807]
        Text[14744, 14806] chars:[14744, 14806, "Chang … perly"]
    BulletListItem[14807, 14903] open:[14807, 14808, "*"] isTight
      Paragraph[14809, 14903]
        Text[14809, 14902] chars:[14809, 14902, "Add:  …  Lite"]
    BulletListItem[14903, 14954] open:[14903, 14904, "*"] isTight
      Paragraph[14905, 14954]
        Text[14905, 14953] chars:[14905, 14953, "Fix:  … arly."]
    BulletListItem[14954, 15151] open:[14954, 14955, "*"] isTight
      Paragraph[14956, 15151]
        Text[14956, 14998] chars:[14956, 14998, "Fix:  … dded "]
        Code[14998, 15001] textOpen:[14998, 14999, "`"] text:[14999, 15000, "<"] textClose:[15000, 15001, "`"]
          Text[14999, 15000] chars:[14999, 15000, "<"]
        Text[15001, 15027] chars:[15001, 15027, " in c … nate "]
        Code[15027, 15049] textOpen:[15027, 15028, "`"] text:[15028, 15048, "Unter … minated Comment"] textClose:[15048, 15049, "`"]
          Text[15028, 15048] chars:[15028, 15048, "Unter … mment"]
        SoftLineBreak[15049, 15050]
        Text[15052, 15073] chars:[15052, 15073, "excep … sing "]
        Code[15073, 15103] textOpen:[15073, 15074, "`"] text:[15074, 15102, "Embed …  stylesheet URL content"] textClose:[15102, 15103, "`"]
          Text[15074, 15102] chars:[15074, 15102, "Embed … ntent"]
        Text[15103, 15140] chars:[15103, 15140, " opti … Swing"]
        SoftLineBreak[15140, 15141]
        Text[15143, 15150] chars:[15143, 15150, "browser"]
    BulletListItem[15151, 15407] open:[15151, 15152, "*"] isTight
      Paragraph[15153, 15407]
        Text[15153, 15245] chars:[15153, 15245, "Fix:  … Swing"]
        SoftLineBreak[15245, 15246]
        Text[15248, 15341] chars:[15248, 15341, "brows … sheet"]
        SoftLineBreak[15341, 15342]
        Text[15344, 15406] chars:[15344, 15406, "by em … view."]
    BulletListItem[15407, 15512] open:[15407, 15408, "*"] isTight
      Paragraph[15409, 15512]
        Text[15409, 15499] chars:[15409, 15499, "Add:  … yping"]
        SoftLineBreak[15499, 15500]
        Text[15502, 15511] chars:[15502, 15511, "response."]
    BulletListItem[15512, 15627] open:[15512, 15513, "*"] isTight
      Paragraph[15514, 15627]
        Text[15514, 15605] chars:[15514, 15605, "Add:  … dates"]
        SoftLineBreak[15605, 15606]
        Text[15608, 15626] chars:[15608, 15626, "half  … ater."]
    BulletListItem[15627, 15689] open:[15627, 15628, "*"] isTight
      Paragraph[15629, 15689]
        Text[15629, 15688] chars:[15629, 15688, "Fix:  … ether"]
    BulletListItem[15689, 15746] open:[15689, 15690, "*"] isTight
      Paragraph[15691, 15746]
        Text[15691, 15745] chars:[15691, 15745, "Fix:  …  HTML"]
    BulletListItem[15746, 15853] open:[15746, 15747, "*"] isTight
      Paragraph[15748, 15853]
        Text[15748, 15834] chars:[15748, 15834, "Add:  … f all"]
        SoftLineBreak[15834, 15835]
        Text[15837, 15852] chars:[15837, 15852, "requi … iles."]
    BulletListItem[15853, 15942] open:[15853, 15854, "*"] isTight
      Paragraph[15855, 15942]
        Text[15855, 15941] chars:[15855, 15941, "Fix:  … open."]
    BulletListItem[15942, 16019] open:[15942, 15943, "*"] isTight
      Paragraph[15944, 16019]
        Text[15944, 16018] chars:[15944, 16018, "Add:  … ditor"]
    BulletListItem[16019, 16092] open:[16019, 16020, "*"] isTight
      Paragraph[16021, 16092]
        Text[16021, 16091] chars:[16021, 16091, "Fix:  … xists"]
    BulletListItem[16092, 16243] open:[16092, 16093, "*"] isTight
      Paragraph[16094, 16243]
        Text[16094, 16187] chars:[16094, 16187, "Add:  …  Will"]
        SoftLineBreak[16187, 16188]
        Text[16190, 16242] chars:[16190, 16242, "remov … ment."]
    BulletListItem[16243, 16281] open:[16243, 16244, "*"] isTight
      Paragraph[16245, 16281]
        Text[16245, 16280] chars:[16245, 16280, "Fix:  … xport"]
    BulletListItem[16281, 16422] open:[16281, 16282, "*"] isTight
      Paragraph[16283, 16422]
        Text[16283, 16377] chars:[16283, 16377, "Add:  …  link"]
        SoftLineBreak[16377, 16378]
        Text[16380, 16421] chars:[16380, 16421, "to is … link."]
    BulletListItem[16422, 16893] open:[16422, 16423, "*"] isTight
      Paragraph[16424, 16474]
        Text[16424, 16473] chars:[16424, 16473, "Add:  … ild?)"]
      BulletList[16478, 16893] isTight
        BulletListItem[16478, 16649] open:[16478, 16479, "*"] isTight
          Paragraph[16480, 16649]
            Text[16480, 16569] chars:[16480, 16569, "expor … neous"]
            SoftLineBreak[16569, 16570]
            Text[16576, 16648] chars:[16576, 16648, "targe … tion."]
        BulletListItem[16653, 16711] open:[16653, 16654, "*"] isTight
          Paragraph[16655, 16711]
            Text[16655, 16710] chars:[16655, 16710, "copy  … to it"]
        BulletListItem[16715, 16859] open:[16715, 16716, "*"] isTight
          Paragraph[16717, 16751]
            Text[16717, 16750] chars:[16717, 16750, "optio … s to:"]
          BulletList[16759, 16859] isTight
            BulletListItem[16759, 16781] open:[16759, 16760, "*"] isTight
              Paragraph[16761, 16781]
                Text[16761, 16780] chars:[16761, 16780, "expor … files"]
            BulletListItem[16789, 16815] open:[16789, 16790, "*"] isTight
              Paragraph[16791, 16815]
                Text[16791, 16814] chars:[16791, 16814, "style … ripts"]
            BulletListItem[16823, 16837] open:[16823, 16824, "*"] isTight
              Paragraph[16825, 16837]
                Text[16825, 16836] chars:[16825, 16836, "custo …  font"]
            BulletListItem[16845, 16859] open:[16845, 16846, "*"] isTight
              Paragraph[16847, 16859]
                Text[16847, 16858] chars:[16847, 16858, "image … files"]
        BulletListItem[16863, 16893] open:[16863, 16864, "*"] isTight
          Paragraph[16865, 16893]
            Text[16865, 16892] chars:[16865, 16892, "optio … files"]
    BulletListItem[16893, 17030] open:[16893, 16894, "*"] isTight
      Paragraph[16895, 17030]
        Text[16895, 16986] chars:[16895, 16986, "Fix : … em of"]
        SoftLineBreak[16986, 16987]
        Text[16989, 17029] chars:[16989, 17029, "the o … ement"]
    BulletListItem[17030, 17131] open:[17030, 17031, "*"] isTight
      Paragraph[17032, 17131]
        Text[17032, 17119] chars:[17032, 17119, "Add:  … ering"]
        SoftLineBreak[17119, 17120]
        Text[17122, 17130] chars:[17122, 17130, "options."]
    BulletListItem[17131, 17198] open:[17131, 17132, "*"] isTight
      Paragraph[17133, 17198]
        Text[17133, 17197] chars:[17133, 17197, "Add:  … lick."]
    BulletListItem[17198, 17254] open:[17198, 17199, "*"] isTight
      Paragraph[17200, 17254]
        Text[17200, 17253] chars:[17200, 17253, "Add:  … avaFx"]
    BulletListItem[17254, 17682] open:[17254, 17255, "*"] isTight
      Paragraph[17256, 17335]
        Text[17256, 17334] chars:[17256, 17334, "Add:  … views"]
      BulletList[17339, 17682] isTight
        BulletListItem[17339, 17450] open:[17339, 17340, "*"] isTight
          Paragraph[17341, 17450]
            Text[17341, 17416] chars:[17341, 17416, "Optio …  ie. "]
            Code[17416, 17441] textOpen:[17416, 17417, "`"] text:[17417, 17440, "{{ st … atic_root\n}}"] textClose:[17440, 17441, "`"]
              Text[17417, 17440] chars:[17417, 17440, "{{ st … ot\n}}"]
            Text[17441, 17446] chars:[17441, 17446, " --> "]
            Code[17446, 17449] textOpen:[17446, 17447, "`"] text:[17447, 17448, "/"] textClose:[17448, 17449, "`"]
              Text[17447, 17448] chars:[17447, 17448, "/"]
        BulletListItem[17454, 17565] open:[17454, 17455, "*"] isTight
          Paragraph[17456, 17565]
            Text[17456, 17531] chars:[17456, 17531, "Optio …  ie. "]
            Code[17531, 17534] textOpen:[17531, 17532, "`"] text:[17532, 17533, "/"] textClose:[17533, 17534, "`"]
              Text[17532, 17533] chars:[17532, 17533, "/"]
            Text[17534, 17539] chars:[17534, 17539, " --> "]
            Code[17539, 17564] textOpen:[17539, 17540, "`"] text:[17540, 17563, "{{\nst … atic_root }}"] textClose:[17563, 17564, "`"]
              Text[17540, 17563] chars:[17540, 17563, "{{\nst … ot }}"]
        BulletListItem[17569, 17682] open:[17569, 17570, "*"] isTight
          Paragraph[17571, 17682]
            Text[17571, 17656] chars:[17571, 17656, "With  … files"]
            SoftLineBreak[17656, 17657]
            Text[17663, 17681] chars:[17663, 17681, "and/o … ories"]
    BulletListItem[17682, 17745] open:[17682, 17683, "*"] isTight
      Paragraph[17684, 17745]
        Text[17684, 17744] chars:[17684, 17744, "Add:  … moval"]
    BulletListItem[17745, 17936] open:[17745, 17746, "*"] isTight
      Paragraph[17747, 17936]
        Text[17747, 17840] chars:[17747, 17840, "Add:  … files"]
        SoftLineBreak[17840, 17841]
        Text[17843, 17935] chars:[17843, 17935, "that  … tion."]
    BulletListItem[17936, 17997] open:[17936, 17937, "*"] isTight
      Paragraph[17938, 17997]
        Text[17938, 17996] chars:[17938, 17996, "Add:  … tion."]
    BulletListItem[17997, 18048] open:[17997, 17998, "*"] isTight
      Paragraph[17999, 18048]
        Text[17999, 18047] chars:[17999, 18047, "Fix:  … save."]
    BulletListItem[18048, 18122] open:[18048, 18049, "*"] isTight
      Paragraph[18050, 18122]
        Text[18050, 18121] chars:[18050, 18121, "Fix:  … ions."]
    BulletListItem[18122, 18211] open:[18122, 18123, "*"] isTight
      Paragraph[18124, 18211]
        Text[18124, 18210] chars:[18124, 18210, "Add:  … tings"]
    BulletListItem[18211, 18332] open:[18211, 18212, "*"] isTight
      Paragraph[18213, 18332]
        Text[18213, 18306] chars:[18213, 18306, "Add:  …  ones"]
        SoftLineBreak[18306, 18307]
        Text[18309, 18331] chars:[18309, 18331, "from  … orts."]
    BulletListItem[18332, 18414] open:[18332, 18333, "*"] isTight
      Paragraph[18334, 18414]
        Text[18334, 18413] chars:[18334, 18413, "Add:  … arget"]
    BulletListItem[18414, 18499] open:[18414, 18415, "*"] isTight
      Paragraph[18416, 18499]
        Text[18416, 18498] chars:[18416, 18498, "Add:  … able."]
    BulletListItem[18499, 18587] open:[18499, 18500, "*"] isTight
      Paragraph[18501, 18587]
        Text[18501, 18586] chars:[18501, 18586, "Add:  … ents."]
    BulletListItem[18587, 18665] open:[18587, 18588, "*"] isTight
      Paragraph[18589, 18665]
        Text[18589, 18664] chars:[18589, 18664, "Add:  … ement"]
    BulletListItem[18665, 18746] open:[18665, 18666, "*"] isTight
      Paragraph[18667, 18746]
        Text[18667, 18745] chars:[18667, 18745, "Fix:  …  code"]
    BulletListItem[18746, 18830] open:[18746, 18747, "*"] isTight
      Paragraph[18748, 18830]
        Text[18748, 18829] chars:[18748, 18829, "Add:  …  file"]
    BulletListItem[18830, 18984] open:[18830, 18831, "*"] isTight
      Paragraph[18832, 18984]
        Text[18832, 18925] chars:[18832, 18925, "Fix:  … rt of"]
        SoftLineBreak[18925, 18926]
        Text[18928, 18983] chars:[18928, 18983, "the f … ment."]
    BulletListItem[18984, 19051] open:[18984, 18985, "*"] isTight
      Paragraph[18986, 19051]
        Text[18986, 19050] chars:[18986, 19050, "Fix:  … ction"]
    BulletListItem[19051, 19153] open:[19051, 19052, "*"] isTight
      Paragraph[19053, 19153]
        Text[19053, 19143] chars:[19053, 19143, "Fix:  …  list"]
        SoftLineBreak[19143, 19144]
        Text[19146, 19152] chars:[19146, 19152, "items."]
    BulletListItem[19153, 19237] open:[19153, 19154, "*"] isTight
      Paragraph[19155, 19237]
        Text[19155, 19204] chars:[19155, 19204, "Fix:  … ween "]
        Code[19204, 19216] textOpen:[19204, 19205, "`"] text:[19205, 19215, "levels=..."] textClose:[19215, 19216, "`"]
          Text[19205, 19215] chars:[19205, 19215, "levels=..."]
        Text[19216, 19236] chars:[19216, 19236, " and  … ption"]
    BulletListItem[19237, 19357] open:[19237, 19238, "*"] isTight
      Paragraph[19239, 19357]
        Text[19239, 19272] chars:[19239, 19272, "Fix:  … lude "]
        Code[19272, 19280] textOpen:[19272, 19273, "`"] text:[19273, 19279, ":lang="] textClose:[19279, 19280, "`"]
          Text[19273, 19279] chars:[19273, 19279, ":lang="]
        Text[19280, 19332] chars:[19280, 19332, " for  …  line"]
        SoftLineBreak[19332, 19333]
        Text[19335, 19356] chars:[19335, 19356, "after …  code"]
    BulletListItem[19357, 19483] open:[19357, 19358, "*"] isTight hadBlankLineAfter
      Paragraph[19359, 19483] isTrailingBlankLine
        Text[19359, 19444] chars:[19359, 19444, "Fix:  … n and"]
        SoftLineBreak[19444, 19445]
        Text[19447, 19482] chars:[19447, 19482, "selec … amed."]
  Heading[19484, 19525] textOpen:[19484, 19487, "###"] text:[19488, 19525, "2.1.1 - Bug Fix & Enhancement Release"]
    Text[19488, 19525] chars:[19488, 19525, "2.1.1 … lease"]
  Heading[19527, 19557] textOpen:[19527, 19531, "####"] text:[19532, 19557, "Basic & Enhanced Editions"]
    Text[19532, 19557] chars:[19532, 19557, "Basic … tions"]
  BulletList[19559, 19995] isTight
    BulletListItem[19559, 19620] open:[19559, 19560, "-"] isTight
      Paragraph[19561, 19620]
        Text[19561, 19619] chars:[19561, 19619, "Fix:  … sion."]
    BulletListItem[19620, 19675] open:[19620, 19621, "-"] isTight
      Paragraph[19622, 19675]
        Text[19622, 19674] chars:[19622, 19674, "Add:  … tHub."]
    BulletListItem[19675, 19823] open:[19675, 19676, "-"] isTight
      Paragraph[19677, 19823]
        Text[19677, 19770] chars:[19677, 19770, "Add:  … on to"]
        SoftLineBreak[19770, 19771]
        Text[19773, 19822] chars:[19773, 19822, "use n … tion."]
    BulletListItem[19823, 19887] open:[19823, 19824, "-"] isTight
      Paragraph[19825, 19887]
        Text[19825, 19886] chars:[19825, 19886, "Fix:  … : 190"]
    BulletListItem[19887, 19962] open:[19887, 19888, "-"] isTight
      Paragraph[19889, 19962]
        Text[19889, 19961] chars:[19889, 19961, "Fix:  … E bug"]
    BulletListItem[19962, 19995] open:[19962, 19963, "-"] isTight hadBlankLineAfter
      Paragraph[19964, 19995] isTrailingBlankLine
        Text[19964, 19994] chars:[19964, 19994, "Chang … 1.0.4"]
  Heading[19996, 20017] textOpen:[19996, 20000, "####"] text:[20001, 20017, "Enhanced Edition"]
    Text[20001, 20017] chars:[20001, 20017, "Enhan … ition"]
  BulletList[20019, 21086] isTight
    BulletListItem[20019, 20097] open:[20019, 20020, "-"] isTight
      Paragraph[20021, 20097]
        Text[20021, 20096] chars:[20021, 20096, "Fix:  … locks"]
    BulletListItem[20097, 20221] open:[20097, 20098, "-"] isTight
      Paragraph[20099, 20221]
        Text[20099, 20183] chars:[20099, 20183, "Add:  … e the"]
        SoftLineBreak[20183, 20184]
        Text[20186, 20220] chars:[20186, 20220, "refer … aste."]
    BulletListItem[20221, 20277] open:[20221, 20222, "-"] isTight
      Paragraph[20223, 20277]
        Text[20223, 20276] chars:[20223, 20276, "Add:  … ments"]
    BulletListItem[20277, 20420] open:[20277, 20278, "-"] isTight
      Paragraph[20279, 20420]
        Text[20279, 20372] chars:[20279, 20372, "Fix:  … other"]
        SoftLineBreak[20372, 20373]
        Text[20375, 20419] chars:[20375, 20419, "eleme …  etc."]
    BulletListItem[20420, 20537] open:[20420, 20421, "-"] isTight
      Paragraph[20422, 20537]
        Text[20422, 20513] chars:[20422, 20513, "Add:  … o use"]
        SoftLineBreak[20513, 20514]
        Text[20516, 20536] chars:[20516, 20536, "(10-6 … ult)."]
    BulletListItem[20537, 20597] open:[20537, 20538, "-"] isTight
      Paragraph[20539, 20597]
        Text[20539, 20596] chars:[20539, 20596, "Fix:  …  text"]
    BulletListItem[20597, 20663] open:[20597, 20598, "-"] isTight
      Paragraph[20599, 20663]
        Text[20599, 20650] chars:[20599, 20650, "Fix:  … d of "]
        Code[20650, 20662] textOpen:[20650, 20651, "`"] text:[20651, 20661, "Text Block"] textClose:[20661, 20662, "`"]
          Text[20651, 20661] chars:[20651, 20661, "Text Block"]
    BulletListItem[20663, 20778] open:[20663, 20664, "-"] isTight
      Paragraph[20665, 20778]
        Text[20665, 20759] chars:[20665, 20759, "Add:  …  JIRA"]
        SoftLineBreak[20759, 20760]
        Text[20762, 20777] chars:[20762, 20777, "forma … text."]
    BulletListItem[20778, 20948] open:[20778, 20779, "-"] isTight
      Paragraph[20780, 20948]
        Text[20780, 20869] chars:[20780, 20869, "Fix:  … p:..."]
        SoftLineBreak[20869, 20870]
        Text[20872, 20947] chars:[20872, 20947, "absol … iles."]
    BulletListItem[20948, 21022] open:[20948, 20949, "-"] isTight
      Paragraph[20950, 21022]
        Text[20950, 21021] chars:[20950, 21021, "Add:  …  file"]
    BulletListItem[21022, 21086] open:[21022, 21023, "-"] isTight hadBlankLineAfter
      Paragraph[21024, 21086] isTrailingBlankLine
        Text[21024, 21085] chars:[21024, 21085, "Add:  …  file"]
  Heading[21087, 21114] textOpen:[21087, 21090, "###"] text:[21091, 21114, "2.1.0 - Bug Fix Release"]
    Text[21091, 21114] chars:[21091, 21114, "2.1.0 … lease"]
  Heading[21116, 21146] textOpen:[21116, 21120, "####"] text:[21121, 21146, "Basic & Enhanced Editions"]
    Text[21121, 21146] chars:[21121, 21146, "Basic … tions"]
  BulletList[21148, 21660] isTight
    BulletListItem[21148, 21214] open:[21148, 21149, "-"] isTight
      Paragraph[21150, 21214]
        Text[21150, 21213] chars:[21150, 21213, "Chang … yout."]
    BulletListItem[21214, 21252] open:[21214, 21215, "-"] isTight
      Paragraph[21216, 21252]
        Text[21216, 21251] chars:[21216, 21251, "Fix:  … ering"]
    BulletListItem[21252, 21336] open:[21252, 21253, "-"] isTight
      Paragraph[21254, 21336]
        Text[21254, 21335] chars:[21254, 21335, "Fix:  … tions"]
    BulletListItem[21336, 21379] open:[21336, 21337, "-"] isTight
      Paragraph[21338, 21379]
        Text[21338, 21378] chars:[21338, 21378, "Fix:  … bfile"]
    BulletListItem[21379, 21453] open:[21379, 21380, "-"] isTight
      Paragraph[21381, 21453]
        Text[21381, 21452] chars:[21381, 21452, "Fix:  … t all"]
    BulletListItem[21453, 21533] open:[21453, 21454, "-"] isTight
      Paragraph[21455, 21533]
        Text[21455, 21532] chars:[21455, 21532, "Fix:  …  fix."]
    BulletListItem[21533, 21604] open:[21533, 21534, "-"] isTight
      Paragraph[21535, 21604]
        Text[21535, 21603] chars:[21535, 21603, "Chang … cies."]
    BulletListItem[21604, 21660] open:[21604, 21605, "-"] isTight hadBlankLineAfter
      Paragraph[21606, 21660] isTrailingBlankLine
        Text[21606, 21659] chars:[21606, 21659, "Fix:  … Link\""]
  Heading[21661, 21682] textOpen:[21661, 21665, "####"] text:[21666, 21682, "Enhanced Edition"]
    Text[21666, 21682] chars:[21666, 21682, "Enhan … ition"]
  BulletList[21684, 23172] isTight
    BulletListItem[21684, 21743] open:[21684, 21685, "*"] isTight
      Paragraph[21686, 21743]
        Text[21686, 21742] chars:[21686, 21742, "Fix:  … alog."]
    BulletListItem[21743, 21884] open:[21743, 21744, "*"] isTight
      Paragraph[21745, 21884]
        Text[21745, 21835] chars:[21745, 21835, "Fix:  … would"]
        SoftLineBreak[21835, 21836]
        Text[21838, 21883] chars:[21838, 21883, "delet … yped."]
    BulletListItem[21884, 22053] open:[21884, 21885, "*"] isTight
      Paragraph[21886, 22053]
        Text[21886, 21980] chars:[21886, 21980, "Fix:  … onger"]
        SoftLineBreak[21980, 21981]
        Text[21983, 22052] chars:[21983, 22052, "gener … sons."]
    BulletListItem[22053, 22190] open:[22053, 22054, "*"] isTight
      Paragraph[22055, 22190]
        Text[22055, 22145] chars:[22055, 22145, "Fix:  … t was"]
        SoftLineBreak[22145, 22146]
        Text[22148, 22189] chars:[22148, 22189, "only  … irks."]
    BulletListItem[22190, 22229] open:[22190, 22191, "*"] isTight
      Paragraph[22192, 22229]
        Text[22192, 22228] chars:[22192, 22228, "Fix:  … ption"]
    BulletListItem[22229, 22845] open:[22229, 22230, "*"] isTight
      Paragraph[22231, 22295]
        Text[22231, 22294] chars:[22231, 22294, "Fix:  … arch."]
      OrderedList[22299, 22845] isTight delimiter:'.'
        OrderedListItem[22299, 22447] open:[22299, 22301, "1."] isTight
          Paragraph[22302, 22447]
            Text[22302, 22388] chars:[22302, 22388, "Headi … with "]
            Code[22388, 22391] textOpen:[22388, 22389, "`"] text:[22389, 22390, "#"] textClose:[22390, 22391, "`"]
              Text[22389, 22390] chars:[22389, 22390, "#"]
            SoftLineBreak[22391, 22392]
            Text[22399, 22446] chars:[22399, 22446, "prefi … ading"]
        OrderedListItem[22451, 22529] open:[22451, 22453, "2."] isTight
          Paragraph[22454, 22529]
            Text[22454, 22528] chars:[22454, 22528, "Image …  text"]
        OrderedListItem[22533, 22599] open:[22533, 22535, "3."] isTight
          Paragraph[22536, 22599]
            Text[22536, 22598] chars:[22536, 22598, "List  …  text"]
        OrderedListItem[22603, 22679] open:[22603, 22605, "4."] isTight
          Paragraph[22606, 22679]
            Text[22606, 22678] chars:[22606, 22678, "Links …  text"]
        OrderedListItem[22683, 22767] open:[22683, 22685, "5."] isTight
          Paragraph[22686, 22767]
            Text[22686, 22735] chars:[22686, 22735, "Footn … ence "]
            Code[22735, 22738] textOpen:[22735, 22736, "`"] text:[22736, 22737, ":"] textClose:[22737, 22738, "`"]
              Text[22736, 22737] chars:[22736, 22737, ":"]
            Text[22738, 22766] chars:[22738, 22766, " firs …  text"]
        OrderedListItem[22771, 22845] open:[22771, 22773, "6."] isTight
          Paragraph[22774, 22845]
            Text[22774, 22822] chars:[22774, 22822, "Refer … e id "]
            Code[22822, 22825] textOpen:[22822, 22823, "`"] text:[22823, 22824, ":"] textClose:[22824, 22825, "`"]
              Text[22823, 22824] chars:[22823, 22824, ":"]
            Text[22825, 22844] chars:[22825, 22844, " refe … k url"]
    BulletListItem[22845, 22955] open:[22845, 22846, "*"] isTight
      Paragraph[22847, 22955]
        Text[22847, 22936] chars:[22847, 22936, "Fix:  … efore"]
        SoftLineBreak[22936, 22937]
        Text[22939, 22954] chars:[22939, 22954, "licen … pired"]
    BulletListItem[22955, 23050] open:[22955, 22956, "*"] isTight
      Paragraph[22957, 23050]
        Text[22957, 23049] chars:[22957, 23049, "Fix:  … tting"]
    BulletListItem[23050, 23172] open:[23050, 23051, "*"] isTight hadBlankLineAfter
      Paragraph[23052, 23172] isTrailingBlankLine
        Text[23052, 23140] chars:[23052, 23140, "Fix:  … s the"]
        SoftLineBreak[23140, 23141]
        Text[23143, 23171] chars:[23143, 23171, "headi …  item"]
  Heading[23173, 23203] textOpen:[23173, 23176, "###"] text:[23177, 23203, "2.0.0 - New Parser Release"]
    Text[23177, 23203] chars:[23177, 23203, "2.0.0 … lease"]
  Heading[23205, 23235] textOpen:[23205, 23209, "####"] text:[23210, 23235, "Basic & Enhanced Editions"]
    Text[23210, 23235] chars:[23210, 23235, "Basic … tions"]
  BulletList[23237, 24890] isTight
    BulletListItem[23237, 23369] open:[23237, 23238, "-"] isTight
      Paragraph[23239, 23369]
        Text[23239, 23327] chars:[23239, 23327, "Fix:  … eview"]
        SoftLineBreak[23327, 23328]
        Text[23330, 23368] chars:[23330, 23368, "inste … raph."]
    BulletListItem[23369, 23612] open:[23369, 23370, "-"] isTight
      Paragraph[23371, 23612]
        Text[23371, 23456] chars:[23371, 23456, "Chang … d to "]
        Code[23456, 23478] textOpen:[23456, 23457, "`"] text:[23457, 23477, "Markd … own\nNavigator"] textClose:[23477, 23478, "`"]
          Text[23457, 23477] chars:[23457, 23477, "Markd … gator"]
        Text[23478, 23496] chars:[23478, 23496, " from … sing "]
        Code[23496, 23515] textOpen:[23496, 23497, "`"] text:[23497, 23514, "Appli … cationShared"] textClose:[23514, 23515, "`"]
          Text[23497, 23514] chars:[23497, 23514, "Appli … hared"]
        Text[23515, 23558] chars:[23515, 23558, ". Did … e was"]
        SoftLineBreak[23558, 23559]
        Text[23561, 23611] chars:[23561, 23611, "used  … alog."]
    BulletListItem[23612, 23679] open:[23612, 23613, "-"] isTight
      Paragraph[23614, 23679]
        Text[23614, 23678] chars:[23614, 23678, "Fix:  … ring."]
    BulletListItem[23679, 23765] open:[23679, 23680, "-"] isTight
      Paragraph[23681, 23765]
        Text[23681, 23764] chars:[23681, 23764, "Add:  … iews."]
    BulletListItem[23765, 23872] open:[23765, 23766, "-"] isTight
      Paragraph[23767, 23872]
        Text[23767, 23855] chars:[23767, 23855, "Add:  … me as"]
        SoftLineBreak[23855, 23856]
        Text[23858, 23871] chars:[23858, 23871, "bulle … tems."]
    BulletListItem[23872, 23951] open:[23872, 23873, "-"] isTight
      Paragraph[23874, 23951]
        Text[23874, 23950] chars:[23874, 23950, "Fix:  … butes"]
    BulletListItem[23951, 24016] open:[23951, 23952, "-"] isTight
      Paragraph[23953, 24016]
        Text[23953, 24015] chars:[23953, 24015, "Fix:  … derer"]
    BulletListItem[24016, 24079] open:[24016, 24017, "-"] isTight
      Paragraph[24018, 24079]
        Text[24018, 24078] chars:[24018, 24078, "Add:  … ring."]
    BulletListItem[24079, 24186] open:[24079, 24080, "-"] isTight
      Paragraph[24081, 24186]
        Text[24081, 24173] chars:[24081, 24173, "Add:  … rison"]
        SoftLineBreak[24173, 24174]
        Text[24176, 24185] chars:[24176, 24185, "purposes."]
    BulletListItem[24186, 24274] open:[24186, 24187, "-"] isTight
      Paragraph[24188, 24274]
        Text[24188, 24273] chars:[24188, 24273, "Chang … rsing"]
    BulletListItem[24274, 24417] open:[24274, 24275, "-"] isTight
      Paragraph[24276, 24417]
        Text[24276, 24366] chars:[24276, 24366, "Add:  …  more"]
        SoftLineBreak[24366, 24367]
        Text[24369, 24416] chars:[24369, 24416, "compl … bled."]
    BulletListItem[24417, 24578] open:[24417, 24418, "-"] isTight
      Paragraph[24419, 24578]
        Text[24419, 24487] chars:[24419, 24487, "Fix:  … fter "]
        Code[24487, 24492] textOpen:[24487, 24488, "`"] text:[24488, 24491, "[ ]"] textClose:[24491, 24492, "`"]
          Text[24488, 24491] chars:[24488, 24491, "[ ]"]
        Text[24492, 24507] chars:[24492, 24507, " woul … se an"]
        SoftLineBreak[24507, 24508]
        Text[24510, 24577] chars:[24510, 24577, "excep … hting"]
    BulletListItem[24578, 24664] open:[24578, 24579, "-"] isTight
      Paragraph[24580, 24664]
        Text[24580, 24625] chars:[24580, 24625, "Fix:  … sing "]
        Code[24625, 24628] textOpen:[24625, 24626, "`"] text:[24626, 24627, ")"] textClose:[24627, 24628, "`"]
          Text[24626, 24627] chars:[24626, 24627, ")"]
        Text[24628, 24663] chars:[24628, 24663, " deli … spec."]
    BulletListItem[24664, 24890] open:[24664, 24665, "-"] isTight
      Paragraph[24666, 24810]
        Text[24666, 24757] chars:[24666, 24757, "Add:  … yping"]
        SoftLineBreak[24757, 24758]
        Text[24760, 24809] chars:[24760, 24809, "respo … sing:"]
      BulletList[24814, 24890] isTight
        BulletListItem[24814, 24828] open:[24814, 24815, "-"] isTight
          Paragraph[24816, 24828]
            Text[24816, 24827] chars:[24816, 24827, "Defin … tions"]
        BulletListItem[24832, 24862] open:[24832, 24833, "-"] isTight
          Paragraph[24834, 24862]
            Text[24834, 24861] chars:[24834, 24861, "Typog … marts"]
        BulletListItem[24866, 24890] open:[24866, 24867, "-"] isTight hadBlankLineAfter
          Paragraph[24868, 24890] isTrailingBlankLine
            Text[24868, 24889] chars:[24868, 24889, "Multi …  URLs"]
  Heading[24891, 24912] textOpen:[24891, 24895, "####"] text:[24896, 24912, "Enhanced Edition"]
    Text[24896, 24912] chars:[24896, 24912, "Enhan … ition"]
  BulletList[24914, 26735] isTight
    BulletListItem[24914, 24996] open:[24914, 24915, "*"] isTight
      Paragraph[24916, 24996]
        Text[24916, 24985] chars:[24916, 24985, "Chang … ngs. "]
        Emoji[24985, 24995] textOpen:[24985, 24986, ":"] text:[24986, 24994, "grinning"] textClose:[24994, 24995, ":"]
          Text[24986, 24994] chars:[24986, 24994, "grinning"]
    BulletListItem[24996, 25106] open:[24996, 24997, "*"] isTight
      Paragraph[24998, 25106]
        Text[24998, 25088] chars:[24998, 25088, "Add:  … rt of"]
        SoftLineBreak[25088, 25089]
        Text[25091, 25105] chars:[25091, 25105, "line  … ents."]
    BulletListItem[25106, 25193] open:[25106, 25107, "*"] isTight
      Paragraph[25108, 25193]
        Text[25108, 25192] chars:[25108, 25192, "Add:  … text."]
    BulletListItem[25193, 25559] open:[25193, 25194, "*"] isTight
      Paragraph[25195, 25559]
        Text[25195, 25286] chars:[25195, 25286, "Fix:  … would"]
        SoftLineBreak[25286, 25287]
        Text[25289, 25381] chars:[25289, 25381, "alway … space"]
        SoftLineBreak[25381, 25382]
        Text[25384, 25473] chars:[25384, 25473, "would … w the"]
        SoftLineBreak[25473, 25474]
        Text[25476, 25558] chars:[25476, 25558, "caret … ural."]
    BulletListItem[25559, 25797] open:[25559, 25560, "*"] isTight
      Paragraph[25561, 25797]
        Text[25561, 25653] chars:[25561, 25653, "Fix:  … focus"]
        SoftLineBreak[25653, 25654]
        Text[25656, 25744] chars:[25656, 25744, "back  … pting"]
        SoftLineBreak[25744, 25745]
        Text[25747, 25796] chars:[25747, 25796, "typin … ocus."]
    BulletListItem[25797, 25847] open:[25797, 25798, "*"] isTight
      Paragraph[25799, 25847]
        Text[25799, 25846] chars:[25799, 25846, "Add:  … tems."]
    BulletListItem[25847, 25924] open:[25847, 25848, "*"] isTight
      Paragraph[25849, 25924]
        Text[25849, 25898] chars:[25849, 25898, "Fix:  …  any "]
        Code[25898, 25902] textOpen:[25898, 25899, "`"] text:[25899, 25901, ".."] textClose:[25901, 25902, "`"]
          Text[25899, 25901] chars:[25899, 25901, ".."]
        Text[25902, 25923] chars:[25902, 25923, " dire … ences"]
    BulletListItem[25924, 26104] open:[25924, 25925, "*"] isTight
      Paragraph[25926, 26104]
        Text[25926, 26020] chars:[25926, 26020, "Fix:  … ively"]
        SoftLineBreak[26020, 26021]
        Text[26023, 26103] chars:[26023, 26103, "for b … irks."]
    BulletListItem[26104, 26231] open:[26104, 26105, "*"] isTight
      Paragraph[26106, 26231]
        Text[26106, 26195] chars:[26106, 26195, "Add:  … which"]
        SoftLineBreak[26195, 26196]
        Text[26198, 26230] chars:[26198, 26230, "eleme … ocus."]
    BulletListItem[26231, 26318] open:[26231, 26232, "*"] isTight
      Paragraph[26233, 26318]
        Text[26233, 26317] chars:[26233, 26317, "Add:  …  list"]
    BulletListItem[26318, 26406] open:[26318, 26319, "*"] isTight
      Paragraph[26320, 26406]
        Text[26320, 26351] chars:[26320, 26351, "Chang … llow "]
        Code[26351, 26355] textOpen:[26351, 26352, "`"] text:[26352, 26354, "''"] textClose:[26354, 26355, "`"]
          Text[26352, 26354] chars:[26352, 26354, "''"]
        Text[26355, 26405] chars:[26355, 26405, " for  … ences"]
    BulletListItem[26406, 26531] open:[26406, 26407, "*"] isTight
      Paragraph[26408, 26531]
        Text[26408, 26494] chars:[26408, 26494, "Add:  … t and"]
        SoftLineBreak[26494, 26495]
        Text[26497, 26530] chars:[26497, 26530, "numbe … list."]
    BulletListItem[26531, 26579] open:[26531, 26532, "*"] isTight
      Paragraph[26533, 26579]
        Text[26533, 26578] chars:[26533, 26578, "Add:  … k fix"]
    BulletListItem[26579, 26735] open:[26579, 26580, "*"] isTight
      Paragraph[26581, 26735]
        Text[26581, 26659] chars:[26581, 26659, "Add:  …  the "]
        Code[26659, 26668] textOpen:[26659, 26660, "`"] text:[26660, 26667, "[TOC]:#"] textClose:[26667, 26668, "`"]
          Text[26660, 26667] chars:[26660, 26667, "[TOC]:#"]
        Text[26668, 26675] chars:[26668, 26675, " marker"]
        SoftLineBreak[26675, 26676]
        Text[26678, 26735] chars:[26678, 26735, "to in … sers."]
````````````````````````````````


