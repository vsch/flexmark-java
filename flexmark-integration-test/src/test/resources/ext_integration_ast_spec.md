---
title: Integration Test Spec
author: Vladimir Schneider
version: 0.1
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

## Integration

## Issue performance

```````````````````````````````` example(Issue performance: 1) options(TIMED)
## Markdown Navigator

[TOC levels=3,4]: # "Version History"

### Version History
- [2.7.0.8 - Bug Fix & Enhancement Release](#2708---bug-fix--enhancement-release)
- [2.7.0 - Bug Fix & Enhancement Release](#270---bug-fix--enhancement-release)
- [2.6.0 - Bug Fix & Enhancement Release](#260---bug-fix--enhancement-release)
- [2.5.4 - Bug Fix Release](#254---bug-fix-release)
- [2.5.2 - Bug Fix & Enhancement Release](#252---bug-fix--enhancement-release)
- [2.4.0 - Bug Fix & Enhancement Release](#240---bug-fix--enhancement-release)
- [2.3.8 - Bug Fix Release](#238---bug-fix-release)
- [2.3.7 - Bug Fix Release](#237---bug-fix-release)
- [2.3.6 - Bug Fix & Enhancement Release](#236---bug-fix--enhancement-release)
- [2.3.5 - Bug Fix & Enhancement Release](#235---bug-fix--enhancement-release)


### 2.7.0.8 - Bug Fix & Enhancement Release

* [ ] Fix: copy fixed utils from Arduino Support plugin.
* Fix: nasty bug introducing typing delay with preview enabled.
* in the process
* Fix: diagnostic-2012, kotlin NPE.
* Fix: Paste Image: old crop settings out of bounds for new image caused exception
* Fix: for #651, Drop image with dialog issues
  * Spaces in file name were url encoded
  * Copy dragging a file leaves its original directory instead of setting it to the closest
        or best guess based on the destination file. Should be the same as if the image was
        pasted into the file. If the destination directory is the same as the source then a new
        name should be generated to uniquify it.
* Add: in Paste/Modify Image if dragging the highlight selection without having highlight
  enabled or no border, inner nor outer fill enabled, will enable highlight and border to
  provide feedback otherwise it is confusing.
  * Add: drag selection can be used for cropping if image tab is selected and `Use mouse
    selection only for highlight` is not selected.
  * Fix: only copy image to transparent if Image tab is selected. The rest leave as is.
  * Add: restart notification if changing full highlight combinations
* Add: Image Paste highlight option to annotate an area of the image.
* Add: option to disable synthetic highlight attributes.
  * Fix: #648, too many element types registered, Option for full syntax highlighter
    combinations, disabling creates minimal set to reduce the limit of short index for these in
    the IDE.
* Add: Code Style option to treat `Hard Wraps` parser option as if soft-wraps are enabled.
* Add: Main option to force soft-wraps mode for file when opening if `Hard Wraps` are enabled

### 2.7.0 - Bug Fix & Enhancement Release

* Fix: jekyll parser option notification would not use the file's scope based profile.
* Fix: bump up dependencies to newer versions this
* Fix: #647, md to html link conversion not working for exported files on Windows
* Fix: exported files without stylesheet should not decorate link with resolved status
      class.
* Fix: `{% include ` link resolution does not work without a VCS root.
* Fix: Jekyll `{% include "" %}` completions would not work unless there was an `.html`
  extension between the strings.
* Fix: update for 2019.1 eap
* Fix: intentions missing groupKey were not showing up or being run
* Fix: make hex text dialog a licensed feature instead of dev feature.
* Fix: diagnostic/1931, possible fix for intermittent based sequence index out of bounds fix
* Fix: catch exception when github tasks request fails
* Fix: settings for HTML paste are not dependent on paste handler override.
* Fix: when ask on paste for html paste options would cause subsequent undo to fail due to
  temporary file modification.
* Fix: reverse the order of split editor configuration for "Show editor and preview" and "Show
  editor only"
* Fix: for API change in 2019.1 EAP.
* Add: `Simple structure view` option to display only heading hierarchy in the structure view
* Fix: optimize parser PSI generation by using hash map for type to factory function
* Fix: diagnostic/1849, ClassCastException: LeafPsiElement cannot be cast to
  MultiMarkdownLinkElement
* Fix: image reference links to references with wrong file type or not raw would not register as
  references to the reference definition. Added `getExactReference()` to return reference only
  if it is an exact match, `getReference()` not matches strictly by id since it is used for
  navigation and usages.
* Add: `Use Style Attribute` option to HTML Export settings. When enabled will apply stylesheet
  via `style` attribute of each element as done for `Copy Markdown as HTML mime content`.

  **NOTE:** stylesheet is expected to be in the same format as `COPY_HTML_MIME` stylesheet. See
  [Copy Markdown to HTML formatted Text Profile](https://github.com/vsch/idea-multimarkdown/wiki/Rendering-Profiles-Settings#copy-markdown-to-html-formatted-text-profile)

  **NOTE:** if `No Stylesheets, No Scripts` is selected then only styles explicitly defined by the
  profile will be used. If this option is not selected then `COPY_HTML_MIME` profile stylesheet
  will be used or if the `COPY_HTML_MIME` profile is not defined then the
  [default stylesheet for `COPY_HTML_MIME`][html_mime_default.css] will be used.
* Fix: move annotations for `Reference Links` to inspections
* Fix: move annotations for `References` to inspections
* Fix: move annotations for `Emoji` to inspections
* Fix: move annotations for `Anchor` to inspections
* Fix: move annotations for `Headings` to inspections
* Fix: move annotations for `Tables` to inspections and add quick fix for column spans
* Fix: move annotations for `List Items` and `Possible list items` to inspections
* Add: Html Generation option to not wrap paragraphs in `<p>` and use `<br>` between paragraphs
  instead. Useful for HTML exported files for use in Swing panels
* Add: Html Export target file path options to add to target directory. Useful if need to
  flatten directory structure of markdown files to a single directory for exported HTML
  * Add path relative to project
  * Add path relative to parent directory
  * Add file name only
* Add: same file path type options as target path for export image copied file path.
* Fix: IDE hangs when copying text containing the macro references which contained recursive
  macros.
* Fix: document format to ensure one blank line after macro definition
* Fix: `<>` wrapped auto links would prevent following bare auto-links from being parsed.
* Add: all elements intention to select element if intention displays dialog to give user
  feedback on which element is being used.
* Fix: do not highlight auto links as errors if remote link validation is disabled
* Fix: remote link annotation disabled by custom URI scheme handler
* Fix: #640, java.lang.NullPointerException with HtmlPasteOptionsForm
* Add: Parser
  [Macros Extension](https://github.com/vsch/idea-multimarkdown/wiki/Macros-Extension)
* Fix: list item indent/unindent could insert `&nbsp;` inserted/removed during wrapping but do
  not perform wrapping, causing the `&nbsp;` to be left in the text.
* Add: intention for auto link to explicit link conversion and vice-versa
* Fix: #605, Support for system specific protocol handlers. Pass through custom protocols to IDE
  browser launcher.
* Fix: to not highlight external URL links which consist of only the protocol at the end of the
  line.
* Add: color scheme export to save only non-synthetic attributes: `Intellij IDEA color scheme,
  reduced markdown (.icls)`
* Add: validation to auto-link remote url and completion/validation to anchor ref
* Add: url based parser settings for remote link markdown parsing. For now hardcoded for GitHub,
  GitLab and legacy GitBook compatibility. New GitBook anchor links not supported yet.
* Fix: diagnostic/1827, Empty collection can't be reduced.
* Fix: broken remote URL links to markdown files validation and anchor ref completions
* Add: quick fix intention for fixing unresolved anchor refs when a match can be made by
  ignoring case and removing duplicated `-`
* Fix: GitHub heading ids do not convert non-ascii to lowercase.
  * Add: `Heading ids lowercase non-ascii text`, selected for:
    * GitLab profile
    * GitBook profile
    * CommonMark profile
* Fix: formatter for extension
* Fix: invalid anchor refs not annotated for local links (broken by remote link validation)
* Add: intention for unresolved link addresses starting with `www.` to prefix with `https://`
  and `http://`. If remote link validation is enabled then only prefix which results in valid
  link address will be shown in the intention. If the resulting address reports as permanently
  moved then also add the destination location to intentions.
* Add: handling of HTTP:301 for remote content and intention to update link address
* Fix: for remote content cache only store list of anchors instead of content, more compact and
  provides the needed data
* Fix: remove directories from link completions to reduce noise in completions
* Fix: remote image links showed as unresolved, now IOExceptions during fetching treated as
  resolved.
* Fix: remove links returning image data now treated as resolved.
* Fix: #637, Links from main project repository to files in a sub-directory repository show
  unresolved
* Add: unresolved remote link address annotation error.
* Add: in settings total remote link count and memory use for remote content.
* Fix: only cache remote content when it is needed for anchor ref validation. For remote link
  validation only store the fact that it exists.
* Add: remote link content cache to use for validating remote content links and anchor refs
* Add: option to enable validation of remote links (annotates unresolved link if server returns
  error)
* Fix: remove anchor ref error annotation for links which do not resolve to a project file or do
  not exist if validating remote link anchor refs
* Add: error annotation for links to HTML files in project with anchor refs which do not link to
  `a` or `h1` through `h6` html tags with `name` or `id` attribute given by anchor ref
* Add: anchor link completion for links to HTML files in project to `a` or `h1` through `h6`
  html tags with `name` or `id` attribute giving the anchor ref
* Add: anchor link completion on external URLs which do not resolve to a project file.
  * Special handling if file extension matches a Markdown Language extension, will download the
    markdown file and will render it as HTML to extract anchor definitions
  * Special handling for GitHub (ones starting with http:// or https:// followed by github.com)
    * markdown files: If the link is to a `blob` type then will use `raw` type URL to get
      Markdown so it can be correctly rendered as HTML to extract anchor definitions.
    * html content:
      * remove `user-content-` prefix from anchor refs (GitHub adds these automatically)
      * remove `[0-9a-fA-F]{32}-[0-9a-fA-F]{40}` looking anchor ids
  * Special handling for GitLab (ones starting with http:// or https:// followed by gitlab.com)
    * markdown files: If the link is to a `blob` type then will use `raw` type URL to get
      Markdown so it can be correctly rendered as HTML to extract anchor definitions.
    * html content:
      * remove `[0-9a-fA-F]{32}-[0-9a-fA-F]{40}` looking anchor ids

### 2.6.0 - Bug Fix & Enhancement Release

* Fix: change definition indent maximum to 4, beyond which it converts the text to indented
  code.
* Fix: definition formatting would not add indent removal causing contained block quote prefix
  to be doubled
* Add: option to remove prefixes when joining lines
* Fix: move code style `Continuation Lines` indent into `Text` code style panel.
* Add: `Left Justified` option to ordered list style options
* Fix: force code style parser settings to CommonMark
* Fix: change code style sample parsing flags to modify parser flags to allow formatting all
  sample elements.
* Fix: settings "Manage..." exception in DataGrip without an open project. Now uses user home
  dir as default directory without an open project.
* Fix: fenced code and indented code indented with tabs would not minimize indent during
  formatting.
* Fix: HTML to markdown conversion
  * Fix: #268, Pipe characters are not escaped in Table (FlexmarkHtmlParser)
    * Fix: escape pipe characters in text (to avoid accidental use as table or other markup)
      when not inline code nor fenced code
    * Fix: escape back ticks when inside code
    * Fix: disable escaping of `[]` when inside code
      * Fix: disable escaping of `\` when inside code
    * Fix: replace non-break space with space when inside code
* Fix: `FlexmarkHtmlParser.BR_AS_EXTRA_BLANK_LINES` now adds `<br />` followed by blank line
* Fix: JavaFx Browser initialization bug introduced by 2016.3 compatibility fix.
* Add: "Paste HTML" button to HTML Paste Options dialog to paste HTML without conversion to
  markdown.
* Fix: clean up code style formatting and preview of style changes
  * style changes are now highlighted to properly reflect the last change, not whole document
    reformat changes
  * prefix changes would not be applied (or formatted) if text wrap for paragraphs was disabled,
    affected list items, definitions, block quotes
  * block quote prefix (compact with space) always inserted space after firs `>` instead of last
    `>`
  * TOC with html language option would not update preview
  * Remove unused list formatting options
* Add: link text suggestion for user label `@username` for GitHub user links of the form:
  `https://github.com/username`
* Change: remove runtime null assertions for function arguments
* Fix: scroll sync not working in 2018.3 EAP
* Fix: change lambdas to functions to have `arguments` available (causing exception in JetBrains
  Open JDK 1.8.0_152-release-1293-b10 x86_64
* Add: extra diagnostic information for Swing Browser `EmptyStackException`
* Fix: diagnostic/1759, kotlin arguments erroneously defined as not nullable.
* Fix: 2016.3 compatibility
* Fix: markdown code style settings to be created from file when available to allow IDE scope
  based resolution for markdown files to work properly.
* Add: HTML Settings option `Add <!DOCTYPE html>` to enable/disable having doc type at top of
  document. Required by Katex to work.
* Fix: update emoji icons
* Fix: GitLab math blocks to display as blocks instead of inlines
* Fix: disable tab overrides if there is a selection in the editor or multiple carets
* Change: split math and chart options from GitLab so that each can be selected without GitLab
  extensions if GitLab extensions are not selected.
* Add:
  [GitLab Flavoured Markdown](https://gitlab.com/gitlab-org/gitlab-ce/blob/master/doc/user/markdown.md)
  parsing and rendering functionality
  * Math inline using ```$``$``` and fenced code blocks with info of `math` using
    [Katex](https://github.com/Khan/KaTeX)
  * Chart fenced code blocks with info of `mermaid` using
    [Mermaid](https://github.com/knsv/mermaid)
  * Inserted text (underlined) via `{+text+}` or `[+text+]`
  * Deleted text (strike through) via `{-text-}` or `[-text-]`
  * Multiline block quotes using `>>>` at start of line to mark block start and `<<<` at start
    of line to mark block end.
    [GFM: Multiline Blockquote](https://gitlab.com/gitlab-org/gitlab-ce/blob/master/doc/user/markdown.md#multiline-blockquote)
  * Video image link rendering
    [GFM: Videos](https://gitlab.com/gitlab-org/gitlab-ce/blob/master/doc/user/markdown.md#videos)
* Fix: disable tab override when popup is showing to allow tab to be used for completions.
* Fix: with CommonMark list type bullet list item after ordered list and vice versa, would allow
  indentation in error.
* Fix: #469, pressing tab in a empty list item should indent the list
  * added option "List item indentation" under "Tab/Backtab Overrides" which enables using
    tab/backtab to change list item indentation
* Fix: #541, using tab to move to the next table cell
  * added option "Table Navigation" under "Tab/Backtab Overrides" which enables using
    tab/backtab to navigate table cells
* Fix: GitHub issue completions for 2018.3 EAP
* Fix: #577, Add feature to move table columns, added Move table column left/right
* Fix: remove line/search highlights from plain HTML preview
* Fix: remove auto links from spellchecking elements
* Fix: partial fix for list item needs blank line annotation for list in block quotes
* Fix: #610, hope can have more paste option, add HTML paste options to suppress conversion of
  inline and heading elements
* Fix: #623, Directory linking occasionally broken
* Fix: compatibility with versions 2017.
* Fix: possibly fix diagnostic pycharm exception on migrate code settings
* Change: update deprecated API usage
* Change: reduce number of highlighter overlay attributes to 3609 from 8497
* Change: update source code for 2018.2 API changes.
* Fix: #621, plugin oom in goland, potential memory leak
* Fix: #615, Plugin can't initialize in multi-user setup, now temp directory
  `.markdownNavigator` is created under the user's home directory
* Fix: #620, Check keyboard shortcut changes wrong list item
* Fix: #619, Create unchecked list item when pressing enter next to a line with a checked list
  item
* Fix: reference paste to add line references to link text in the form: `: Line #` or `: Lines
  #-#`
* Fix: diagnostic/1575, `node.treeNext must not be null`
* Fix: wrong range calculation for #612 fix
* Fix: #611, Backspace in empty check mark box deletes check mark
* Fix: #612, Code folding eats one character for underline headers
* Add: HTML comment folding and options
* Fix: diagnostic, parent already disposed

### 2.5.4 - Bug Fix Release

* Fix: High Sierra JavaFx issue, implement JavaFX initialization as per Markdown Support.
* Add: Document Translation using Yandex.Translate and flexmark-java translation helper API,
  configured in debug settings. This is a temporary implementation which will be moved to its
  own plugin in the future.
* Fix: diagnostic/NPE on JavaFX WebView save editor state.
* Add: conversion of emoji from HTML mime copied back from Apple mail.
* Fix: paste image file name from link name would not be URI decoded.
* Add: folding of list items longer than one line of text
* Fix: #590, "Create directories and folder" does only create directory on first hit.
* Fix: #591, uncomment does not remove leading space, removed padding spaces from comment
  prefix/suffix.
* Fix: shorten toolbar by moving more rare actions to popup menus: list, table, misc and copy
* Fix: wrap on typing `>` to insert block quote level into existing block quote or using
  backspace to remove a block quote level
* Fix: wrap on typing backspace in footnote definition would replicate the first line prefix on
  continuation lines
* Fix: inserting an EOL in a list item before text matching bullet list marker or numbered would
  double the list marker on the resulting line
* Add: option for escape/unescape all numbered list lead-in of `number.` when wrapping text.
* Fix: diagnostic java.lang.RuntimeException in ImageUtils.getImageFromTransferable when pasting
  an image
* Fix: java.lang.Throwable: Invalid file: DB VirtualFile: table when caused sometimes by file
  watcher requesting markdown files be re-parsed
* Fix: diagnostic java.lang.IllegalArgumentException: Argument for @NotNull parameter 'project'
* Fix: accept license code when extra spaces are added before EOL in e-mail.
* Fix: diagnostic/ prevSibling should not be null.
* Add: option to disable gutter margin for markdown documents
* Add: option to disable line markers to main settings panel.
* Fix: file types by specific extension completion did not work
* Fix: link resolution would not find files with nested extensions like `blade.php`
* Change: toggle inline attribute when the caret is at the end of a non-space span now restores
  the caret position when applying the style instead of end of the span, inside the markers.
  More natural when inlining a word to continue typing.
* Fix: #575, Broken Spell Checker, spell checking not working on paragraph text for basic
  plugin.
* Fix: JavaFX detection with Android Studio 3.0
* Fix: #434, Spellchecker context menu is duplicated
* Add: `Use rename for spelling error intention` to allow turning off `Rename to:` spellchecking
  intention and use the regular `Change to:` intention.
* Fix: remove old storage macros

### 2.5.2 - Bug Fix & Enhancement Release

* Fix: newer API use which causes exceptions in 2017.3 IDE versions.
* Add: link and image paste/drop options to exported/imported settings management
* Fix: preview window would show links resolved after link map transformation to `http://` as
  `local only`.
* Fix: #567, '_'s are replaced by '-'s in TOC, GitHub now preserves `_` in heading anchor refs
* Fix: paste handler using 2018 api `getSelectedEditor()`
* Fix: #564, Some problems with "Copy / Drop Image" dialog. Modify image directory history drop
  down to include, in order of priority:
  * last used image directory of the current file
  * image directories of images in the current file, ordered by count of occurrence
  * image directories in the current project, ordered by count of occurrence
* Fix: absolute `http://` link from main repo to wiki page which is located in a sub-directory
  would show as unresolved
* Add: help topics for all settings panels
* Fix: improve spelling error alignment in text with many embedded inline markers
* Fix: compatibility with IDE versions 2016.3, limit since version to 163.15529
* Fix: COPY_HTML_MIME and PDF stylesheets now combine user provided attributes with defaults so
  only difference has to be added to custom CSS.
* Add: emoji images to COPY_HTML_MIME and PDF stylesheets so emoji display properly.
* Fix: wiki page file name rename refactoring. Broken since 2.4.0 release
* Fix: manually exporting a file from the toolbar now treats this as if `Export on Save` was set
  for the file.
* Fix: update GitHub wiki home link resolution for image links. Changed recently by GitHub
* Fix: trailing spaces filter behavior changed, postponed trailing spaces would all be deleted.
* Fix: reverse fix for "WebView should be available by now", was causing deadlock if
  Accessibility features were enabled.
* Fix: JavaFX preview was using project profile parser settings, not scope based rendering
  profile parser settings.
* Fix: Formatting default table column alignment when no alignment marker is specified, header
  columns are centered, body columns are left aligned.
* Add: Export to treat emoji images as image linked files.
* Fix: NoSuchMethodError for IDE versions < 2018
* Fix: image paste/modify dialog to not add _# suffix to file name if pasting on image target
  ref and on paste action is "Replace file content" for link-ref targeting an image, since the
  name should be the same as the link-ref in order to replace file content. Changing the name
  will save it under a new file and change the link-ref.
* Fix: remove old project settings handling and replace with IDE provided method. Old settings
  copied to default project settings on first plugin initialization after upgrade. Now default
  project settings support having defaults for Rendering > Profiles
* Add: Format options for Attributes:
  * space inside braces: no change, add, remove
  * space around attribute equal sign: no change, add, remove
  * attribute value quotes:
    * No Change
    * None, double, single
    * None, single, double
    * Double, single
    * Double quotes
    * Single, double
    * Single quotes
* Fix: table formatting would disable wrap on typing unnecessarily because it failed to properly
  detect table at caret offset.
* Add Table Caption formatting options:
  * Caption: no change, always add, remove if empty, always remove;
  * Caption space: no change, space around text, trim spaces check box.
* Add: #556, Default editor layout > Use last selected layout, option to have layout follow last
  editor layout change action.
* Fix: typographic `'` breaking words for spell checker generates erroneous spelling errors.
* Fix: spell checking now done across inline markup. Error underline and Change to: intention do
  not work well because of interspersed markup messing up offsets but at least spelling errors
  will be highlighted. For example `do**sn't**` will now show a spelling error because the
  effective text is `dosn't`.
* Add: history to CSS settings URI text box.
* Fix: default completion for explicit `http://` absolute links to wiki pages uses the extension
* Fix: `file://` links to wiki pages with anchor refs showed as "Only resolving locally" in the
  preview, all `file://` links show as resolving only locally.
* Fix: Admonition extension would be disabled if Attributes extension was not enabled.
* Add: Admonition parser extension.
  **[Admonition](https://github.com/vsch/flexmark-java/wiki/Admonition-Extension)**, Parser
  extension based on [Admonition Extension, Material for MkDocs] to create block-styled side
  content.
* Fix: JavaFX WebView debug page reload in Not on FX application thread exception.
* Fix: remove the "canDebug" field and replace with dynamic value.
* Fix: remove all break points on connection shutdown.
* Fix: JavaFX debugger core dumping if turning off debugging while it is paused.
* Fix: Project Default settings not being copied to new projects
* Fix: intermittent preview element highlight stopped working until page refresh
* Fix: pasting a page relative URL would be mistaken for FQN reference and always paste a link
  instead of text
* Fix: renaming rendering profile would not be saved properly.
* Fix: copy action on rendering profiles caused exception
* Add: all console commands work with Chrome dev tools.
* Fix: Chrome dev tools console evals and console logging from JavFX WebView scripts.
* Fix: #561, Scroll sync and highlight preview element broken in EAP 2.4.0.44
* Remove: FirebugLite script option for JavaFX. It never worked for debugging and Chrome Dev
  Tools work really well with JavaFX WebView.
* Add: "Toggle Editor Split Orientation" action to toggle Vertical/Horizontal split orientation
* Add: drag/drop file inside inline, fenced or indented code to insert file name.
* Add: dropping file after end of line with virtual spaces enabled, will insert spaces to fill
  virtual spaces.
* Fix: Adding explicit attribute to heading did not put space between text and attributes
  element.
* Add: file/ref anchor target search/explore intention on unresolved link anchor ref.
  * Fix: launching on an anchor and cancelling, does not show intention until file is edited.
  * Add: do a partial match for anchor when no anchors match exactly.
  * Add: filter text box to filter anchor list (show all partials, the rest hidden) otherwise
    too many in the list.
* Fix: Github collapse headers script not working in 2018.1
* Fix: intermittent position not highlighting in preview.
* Fix: Drag/Drop copy files does not save link drop options.Always resets or gets them wrong.
* Add: buttons for link and image drop/paste options in markdown settings
* Add: Updated emoji to include full set of GitHub supported ones
  * Add: option to select which shortcuts to recognize:
    * Emoji Cheat Sheet
    * GitHub
    * Both: Emoji Cheat Sheet, GitHub (in order of preference for URL generation in HTML)
    * Both: GitHub, Emoji Cheat Sheet (in order of preference for URL generation in HTML)
  * Add: option to select what type of image to use:
    * Images: image files only
    * Unicode and Images: use Unicode characters when available, image file otherwise
    * Unicode Only: only use unicode characters, don't recognize shortcuts which have no unicode
      equivalent.
  * Add: option to preview settings to replace Unicode emoji characters which have a
    corresponding image file with the image file. This allows preview browser to display Unicode
    emoji for which the browser would display unrecognized character symbol instead.
* Update to flexmark-java-0.32.2
  * Fix: java-flexmark API changes
  * Fix: java-flexmark Attributes processing changes
  * Add: Parser option for Attributes assignment to text
  * Add: Parser option for Emoji Shortcut Type, Emoji Image Type
  * Add: Preview option for replacing Emoji unicode with image
* Add: settings option to allow directories as link targets. Allows directories to be used in
  links. This functionality affects operation to completions, annotations, drag/drop link
  creation and navigation.
* Add: Drag/Drop link creation in Wiki should have wiki option for link format.
* Fix: `http://` link to wiki home without the file shows as unresolved by annotator
* Fix: change explicit to wiki not showing if link format is http:// or https:// absolute
* Fix: when converting explicit to wiki don't generate text & page ref if the explicit link text
  is the same as the file part of the target: `[Page-Ref](Page-Ref.md)` -> `[[Page Ref]]`, not
  `[[Page-Ref|Page Ref]]`
* Fix: Allow links to directories under the repo not to show them as unresolved. Create ref to
  directory object if it is under VCS
* Fix: drag/drop directories to create a link to the directory
* Fix: document format would remove table caption element
* Add: Query user for new id on explicit id to heading intention to save a step of rename
  refactoring it.
* Add: if a heading has explicit id attributes, rename refactoring for it is disabled since the
  id is not part of attributes.
* Add parser option to parse inline HTML for `<a id="...">` for anchor targets
* Fix abbreviation definition with empty abbreviation would cause an exception
* Add Option to enable/disable use of image URI query serial, used to force preview update of
  image when the image file changes. Disabled by default to reduce java image caching memory
  issues.
* Fix: custom paste handling into scratch files was not handled in CLion, possibly other
  non-Java IDEs.
* Fix: #554, Settings, Import and Copy from project do not get applied until corresponding
  settings pane is viewed. The settings would be changed but not applied until the settings pane
  was clicked on first.
* Fix: diagnostic/1159, Inserting table rows could cause an index out bounds exception
* Fix: files not under VCS root would show no completions for relative addressing, only had
  completions for `file://` format completions.
* Add: recall of the last imported settings file to make it easier to reset settings to a known
  value.
* Add: markdown Application settings to exported and imported settings.
* Fix: disable local only status for links and annotation when the link is to the file itself.
* Add: allow source/preview synchronization and search/selection highlighting in basic version.
* Fix: diagnostic/1140, NPE in flexmark-java core node renderer.
* Fix: diagnostic/1141, null editor causes exception in toolbar button test.
* Add: #549, Add settings management functionality. Now in main settings panel there is a
  "Manage..." button in top-right corner, clicking it pops up a menu with the following options:
  * `Copy to Project Defaults`
  * `Copy from Project Defaults`
  * `Export Settings`
  * `Import Settings`
  * `Reset Settings` to reset settings to default. Project defaults, current project settings
    and markdown navigator application settings.

  These actions copy from current unsaved project settings and to current unsaved project
  settings therefore you can modify settings, copy to project defaults (or export) and then
  cancel, result will be project defaults (or exported settings) having modified settings while
  project settings being those before modification.

  If you copy from defaults or import a file followed by `Cancel` then no settings will be
  modified.
* Fix: #548, When "Auto-scroll to source" is enabled in project view, markdown navigator editor
  steals focus when moving through project view with keyboard arrows.
* Fix: #542, Typographical Error in PHPStorm Preferences > Editor > Code Style > Markdown
* Add: option in settings to enable editor paste handler registration so that paste handler is
  enabled by default. Because the IDE has a lot of formatter exceptions on paste which get
  erroneously attributed to the plugin when it delegates paste action to previous handler. Now a
  notification balloon will inform of the IDE exception and offer a link to disable paste
  handler customization.
* Fix: #546, Panel is guaranteed to be not null Regression.
* Fix: #260, Add horizontal split editor option to allow preview below the text editor. Added
  option in Languages & Frameworks > Markdown: `Vertical Text/Preview Split`, default not
  selected.
* Fix: #524, Dedent shortcut not working properly.
* Fix: #539, Big local images (e.g. .gif) referred to in an open .md file get locked and cause
  merge conflicts and issues on checkout. Now swing implements disable GIF images option.
* Fix: #512, Add keyboard shortcut to `Cycle between Preview only and Editor only`. Instead
  added application setting to select text/split or text/preview toggle for the toggle editor
  layout action.
* Fix: #511, `Cycle split layout` shortcut stop working when `Preview Only` is selected.
* Fix: #527, How to use *italics* instead of _italics_ when pressing `Ctrl+I`. Option added to
  Languages & Frameworks > Markdown: `Use asterisks (*) for italic text`, enabled by default.
  When enabled italic action will use only asterisks for as markers.
* Fix: #535, Documentation for link maps and mapping groups. Documentation link added to Link
  Map settings panel.
* Fix: diagnostic/1100, start/end offset on paste beyond end of document
* Fix: clicking on a link with anchor ref by name of element would not scroll element into view
* Add: #391, #anchor tags not working. Added anchors of the form `<a .... attr=anchorId
  ...>...</a>` where `attr` is `id` or `name` to be treated as anchor ref targets. NOTE: the
  first name or id attribute will be treated as the "anchor target" the other as a reference to
  the anchor target. If both have the same string value then renaming one will rename the other.
* Fix: regex error flexmark-java attributes parser which could cause a parsing loop
* Add: parser option to not generate duplicate dashes `-` in heading ids
* Fix: fenced code content erroneously processed GitHub issue marker `#`.
* Fix: #544, Export to PDF greyed out. Editor actions would be disabled if the text editor was
  not visible.
* Add: parser options for
  * **[Attributes](https://github.com/vsch/flexmark-java/wiki/Attributes-Extension)** and
  * **[Enumerated References](https://github.com/vsch/flexmark-java/wiki/Enumerated-References-Extension)**
    parser extensions
  * Add: heading intentions to add/remove explicit id
  * Add: completions for link anchors to id attribute values
  * Add: completions for enumerated references and reference formats
  * Add: formatting options and formatting for Enumerated References
  * Add: error/unused annotations for enumerated reference, enumerated format and attribute id
  * Add: refactoring/navigation for Enumerated Reference format id's, Attribute Id's, Enumerated
    Reference link/text.
* Fix: diagnostic: 1055, sometimes virtual file == null for a PsiFile causing an exception.
* Add: option to add serial query suffix to CSS URI which increments when the css file changes
  (only file:// URI's and document relative URLs are supported.)
* Fix: diagnostic 1030, when bread-crumb provider steps up to file level while looking for
  headings.
* Fix: diagnostic: 1032, sometimes an exception is thrown "AssertionError: Unexpected content
  storage modification"
* Fix: diagnostic 1033, paste handler exception `IllegalStateException: Clipboard is busy`
* Fix: diagnostic 1035, null pointer exception in Swing preview when image tag has no `src`
  attribute.
* Fix: diagnostic 1047, sometimes an IOException is generated if markdown sub-type is requested
  during indexing operation.

### 2.4.0 - Bug Fix & Enhancement Release

* Fix: #517, Invalid tool tip for "Show Breadcrumb text"
* Change: #520, Not working: As you type automation: Double of bold/emphasis markers and remove
  inserted ones if a space is typed. Enable these options in code style, disabled by default.
* Fix: #509, Text with colons is incorrectly interpreted as an invalid emoji shortcut
* Add: #507, How to be sure that HTML auto generated link will have unchanged url. Link format
  option for HTML export: page relative, project relative, http: absolute, file: absolute if
  option `Link to exported HTML` is not selected.
* Add: #466, Indents with 4 spaces instead of 2 as I like. Code style option for indent size
  added sets number of spaces to insert when pressing tab.
* Change: Remove attribute and settings migration from pre 2.3.0 versions.
* Add: nested heading outline collapsing
* Fix: improved HTML to markdown conversion from Apple Mail copied text.
* Fix: don't show emoji completions in link address part ( http: triggers it)
* Fix: abbreviation navigation and refactoring was not implemented
* Fix: line markers generate for leaf elements only, performance improvement
* Fix: swing preview on linux not showing fixed pitch font for code
* Fix: Task list items now require indent at task item marker not item content, to match GitHub
  parsing rules. Indenting to content column treats children as inline code and child list items
  not separated by a blank line as lazy continuation lines.
* Fix: formatter for new task item indentation rules.
* Fix: remove `Replace File Content` option from non-image target ref drop downs in paste/modify
  image dialog, and all link options from copy/drop image dialog and link drop/paste ref options
  dialog.
* Fix: #489, Paste Image does not create file if parent directory does not exist.
* Fix: #484, Open links in preview, not browser. Option added to preview settings to have page
  relative and repo relative links resolve to GitHub files when selected. When not selected they
  resolve to local project files.
* Fix: #486, Multi-line links do not preview correctly, when in `Line` preview element highlight
  mode.
* Fix: #481, Will not allow me to crop beyond 200px, now limits are derived from the image
  dimensions and image operations.
* Fix: Update to latest flexmark-java supporting CommonMark Spec 0.28.
* Fix: TOC entries would exclude typographic characters when "text only" option was used with
  typographic parser extension enabled.
* Fix: HTML to Markdown not adding HTML comment between consecutive lists
* Fix: #479, Multi-line URL images are not converted in PDF export or Copy HTML Mime
* Add: Show "Apply all '...'" intention on any element option to allow showing file level
  intentions to be available on any element. Otherwise only shown on elements which they affect.
* Add: enable image intentions on multi-line URL image links
* Add: Code Folding option in settings for embedded image links
* Add: HTML generation options to convert image links to embedded images, with separate option
  for http:// and https:// image urls.
* Add: base64 embedded image display in Swing Preview browser
* Add: `Base64 Encoded` as a link format for pasted images and dropped files to the Paste Image
  dialog.
* Fix: base64 encode intention would keep path of url if it was present
* Fix: image reference links to references with base64 encoded images would show as unresolved
* Add: intentions to convert images to base64 encoding and vice-versa
* Fix: base64 encoded embedded images did not display in JavaFX preview
* Fix: preview navigation to links with anchor refs and line anchor refs
* Fix: dropping a file in a document appends `null` string to the file name in error.
* Fix: #468, Move (refactoring) of .md files breaks links to sections in same file.
* Fix: reference paste with line ref anchor would always paste page relative format URL
  regardless of the link paste format (set with file copy drop action).
* Fix: diagnostics/713, tree view icon update before `FileManager` has been initialized will to
  return markdown file type (without resolving sub-type).
* Add: Convert markdown to HTML intention for fenced code and indented code blocks.
* Fix: unresolved link references would be rendered in HTML instead of being treated as plain
  text. Broken by `Reference` link map code.
* Fix: paste reference past end of line will insert spaces to caret column before inserting
  link.
* Fix: links from FQN references with spaces did not url encode the link.
* Fix: reference to link conversion for PhpStorm to truncate the reference at the `:` since
  PhpStorm is not able to convert FQN strings with class method names.
* Add: use QualifiedNameProviders to resolve reference to link conversion.
* Add: logic to not convert qualified string to link when caret is inside inline code, fenced
  code or between two back-ticks.
* Fix: HTTP links with anchor refs should not highlight anchor links as unresolved.
* Add: paste of file reference with or without line number converted to paste of link with
  GitHub line ref anchor added if line number is part of the reference. This will insert/replace
  link.
* Fix: non-vcs projects links without a path would show unresolved even when files exist.

### 2.3.8 - Bug Fix Release

* Add: GitHub Line reference anchors in the form `L#` or `L#-L#` for line ranges. Now navigating
  to such an anchor in a project file will move the caret to the line and if second form is used
  select the lines.
* Add: with JavaFX browser clicking on task item box in preview toggles open/closed task status
  in source.
* Fix: image refs and image links to non-raw GitHub image files to show as warning. Only show
  warning for references not in raw when referenced by image refs.
* Add: Apply all '...' in file intentions where these make sense.
* Add: intention to convert between typographic symbols and markdown smarts/quotes extension
  text.
* Add: `HTML block deep parsing` parser option to allow better handling of raw text tag parsing
  when they are not the first tag on the first line of the block.
* Add: split inline code class `line-spliced` for code elements split across multiple lines not
  to appear as two inline code elements in preview.
* Fix: HTML generation with line source line highlighting when inline styling spans source lines
* Add: #74, Launching external URLs inside the browser, now `navigate to declaration` opens url
  in browser, ftp or mail client depending on the link. Can also use line markers for navigation
  of these elements.
* Fix: parsing of lists in fixed 4 spaces mode would not allow last item to be loose
* Fix: reference to non-image but not used as image target warning not raw.
* Fix: exception when navigating next/previous table cells in editor without an associated
  virtual file.
* Fix: #461, TOC with HTML generated content causes exception if skipping heading levels
* Fix: #460, TOC options do not change default Heading level
* Fix: #459, PDF export does not resolve local ref anchors
* Fix: #456, Register r markdown code chunk prefix
* Fix: #453, Option to hide toolbar
* Fix: #454, Incorrect filename inspection error, weak warning now only for wiki link targets
  that contain spaces in resolved link.
* Fix: flexmark-java issue 109, image ref loosing title tag.
* Add: GitBook compatible include tags when `GitBook compatibility mode` is enabled in `Parser`
  options.
* Fix: Nested stub index exception in reference search
* Fix: breadcrumb tooltip of task items would be missing the task item marker
* Fix: completions broken on Windows
* Fix: document format erroneously creates column spans for some tables.
* Fix: diagnostics/531, line painter provider passed line number > document line count.
* Fix: diagnostics/498, highlight in preview causing exception
* Fix: diagnostics/497, flexmark-java lib erroneous assert failure
* Fix: #447, Exported HTML has unexpected CSS and JS URLs
* Fix: #445, there should no be default language injection in bare code chunks
* Add: handling of optional quotes for jekyll include tags. Either single `'` or double `"`
  quotes will be ignored if the file name is wrapped in them.
* Fix: API break with version 2016.2.3 by using EditorModificationUtil methods missing from that
  version.
* Fix: #444, Markdown Navigator 2.3.7 breaks paste of github checkout url
* Fix: #441, false positive typo annotation in header, caused by using IdentifierSplitter
  instead of TextSplitter to handle elements that can have references.
* Fix: #442, Image Paste in Windows always pastes absolute file:// regardless of selection
* Add: Insert table column on right actions and changed description of previous action to insert
  table column on left.
* Fix: exception when exporting PDF or Copy HTML Mime
* Fix: #440, Auto links should not appear in fenced code
* Add: #411, Network drives links are not resolved correctly, URI links outside of project now
  error highlighted if the file does not exist
* Add: #433, Support external links for the Link Map (eg. JIRA link), Reference to Link Map to
  allow creating automatic reference URLs from Reference IDs

### 2.3.7 - Bug Fix Release

* Fix: parser erroneously processing escape `\` is encountered in fenced code and causing a
  parsing exception.

### 2.3.6 - Bug Fix & Enhancement Release

* Fix: intermittent index out of bounds exception if document is edited after parsing but before
  AST is built.
* Fix: #438, Markdown Syntax Change looses TOC element in source
* Add: annotation to detect when list syntax is set to GitHub
* Fix: #432, Add a way to disable the startup notification
* Fix: #436, Header link results in bad Table of Contents entry formatting
* Fix: #411, Network drives links are not resolved correctly, for `file://` which is outside the
  project and any module directory structure.
* Fix: NPE in settings under rare conditions
* Fix: assertion failure in settings under rare timing conditions
* Fix: paste NPE when pasting into link with empty address
* Fix: drag/drop without copy modifier of image files uses last non-image link format instead of
  last used image link format.
* Fix: diagnostic id:208, invalid virtual file in line painter
* Add: option to break definition list on two or more blank lines
* Fix: #428, Lack of encoding declaration when exporting in html
* Add: Global page zoom for JavaFX preview in application settings so that project preview zoom
  does not need to be changed when project is opened on a machine with different HIDPI. Now can
  leave project zoom to 1.00 and change global zoom to desired value.
* Fix: #426, Cannot add images from clipboard or drag and drop under Windows
* Fix: Setext heading to not show heading id on marker line
* Add: #425, Add Heading anchor ID display in editor
* Fix: #424, NoClassDefFoundError in WS 2017.1
* Fix: #421, NoSuchFieldError on startup after upgrading plugin on IDEs version 2016.1
* Fix: image link from non-wiki page to image in wiki would show as unresolved by annotator when
  it was resolved by line marker and preview.

### 2.3.5 - Bug Fix & Enhancement Release

* Fix: #420, java.lang.IllegalStateException: node.treeNext must not be null.
* Fix: do not un-escape HTML entities in HTML, let the browser handle that.
* Fix: #419, Bread crumbs broken when running in 2017.1
* Fix: licensed features highlight now full balloon notification.
* Fix: detection when containing file and target file of a link are not under the same VCS root
  when the containing file is in a sub-directory of target VCS root but has its own root.
* Fix: #416, NPE version 2.3.4 (w/license)
* Fix: #415, Setting default right margin in code style markdown settings disables wrapping
* Fix: #414, Exception when starting IDEA
* Fix: do not hide wrap on typing and table auto format buttons even when these are disabled.
* Fix: drag/drop image files should only show copy dialog if no drop action information or it is
  a drop copy action
* Add: plugin exception reporting to `vladsch.com` without effort.
* Fix: wiki to main repo links would not resolve. Erroneously treated two vcs repos as separate.
* Fix: clipboard mime text/html now has higher priority than file list and image on the
  clipboard.
* Add: operation options for non-image drop/paste file based on caret location
* Add: `Copy Modified Image to Clipboard` in Copy/Paste Image Dialog to replace clipboard image
  contents with modified image, can use it to replace image on clipboard then Cancel dialog to
  not modify the Markdown document but still have the modified image on the clipboard.
* Add: Copy/Modify Image intention that will open the Image Copy/Paste Dialog for the image
  content of a link element at caret position. Works with local files and URLs. Get the option
  to change directory, file name and modify the image.
* Fix: `http://` and `https://` addresses to project files would be ignored due to a typo in the
  code.
* Fix: update to flexmark-java 18.2, HTML to Markdown hang fix and MS-Word and MS-Excel HTML
  quirks handling fixed.
* Fix: link resolution logic to work for multi-vcs-root projects and modules not under project
  root.
* Fix: update to flexmark-java 18.1, HTML to Markdown adds space after empty list items.
* Add: Markdown application settings for:
  * `Use clipboard text/html content when available` disabled by default, enabling it will allow
    pasting text/html when available
  * `Convert HTML content to Markdown` enabled by default, disabling will paste text/html
    content without conversion to Markdown
* Add: `Delete empty list items` intention on lists to delete all empty list items
* Fix: HTML to Markdown converter to not ignore text in lists which is not included in list item
  but instead to put this text into a new list item.
* Add: aside extension which uses leading pipe `|` to mark an aside block just like block quote
  uses leading greater than `>` to mark a block quote element
* Add: pasting file list into markdown document inserts links the same as dropping files with
  copy action.
* Add: confirmation dialog when original markdown file is going to be overwritten with
  transformed content when pasting file list or drag and dropping files.
* Fix: absolute `http://..../wiki` link to wiki home page would to resolve as a file reference.
* Fix: drag/drop wiki page files would ignore link address format and always insert page
  relative link.
* Fix: style auto wrapping when caret at end of word that is at end of file without trailing
  EOL.
* Add: future API for drag/drop handling code to eliminate the need for replacing editor
  drag/drop handler.
* Add: highlight selection in preview, `Show source selection in preview` enabled by default.
  Wraps selection in `<span>` with `selection-highlight` class.
* Add: #399, Highlight search results in preview, `Show source search highlights in preview`
  enabled by default. Wraps highlights in `<span>` with `search-highlight` class.
* Fix: text drag/drop not working because of MarkdownPasteHandler
* Add: option to enable drag/drop handler replacement to allow "Copy" extended file drag/drop
  action at the expense of text drag/drop. Disabled by default in settings `Languages &
  Frameworks > Markdown`
* Fix: loosen/tighten list action to not mark a list as loose when blank line precedes the first
  list item.
* Fix: #404, Conversion from CommonMark or FixedIndent to GitHub does not properly indent code
  blocks in list items
* Fix: #403, Indented code in list items not indented enough with GitHub list parser option
* Change: link color in Preview and Editor Colors to match new GitHub colors
* Fix: #400, Better code color consistency needed. Now same as Fenced Code/Verbatim. Also change
  copy Markdown as HTML formatted text and PDF export to align inline code color with indented
  and fenced code.
* Fix: #398, Poor alignment between source and preview when using "Sync source to preview". Now
  there is an option to vertically align synchronized position in Preview Settings, selected by
  default.
* Fix: #402, PDF Export action fails silently if no text is selected in document instead of
  exporting the full document.

[Admonition Extension, Material for MkDocs]: https://squidfunk.github.io/mkdocs-material/extensions/admonition/
[html_mime_default.css]: https://github.com/vsch/idea-multimarkdown/blob/master/resources/com/vladsch/idea/multimarkdown/html_mime_default.css
[holgerbrandl/pasteimages]: https://github.com/holgerbrandl/pasteimages

.
<h2 id="markdown-navigator">Markdown Navigator</h2>
<h3 id="version-history">Version History</h3>
<ul>
  <li><a href="#2708---bug-fix--enhancement-release">2.7.0.8 - Bug Fix &amp; Enhancement Release</a></li>
  <li><a href="#270---bug-fix--enhancement-release">2.7.0 - Bug Fix &amp; Enhancement Release</a></li>
  <li><a href="#260---bug-fix--enhancement-release">2.6.0 - Bug Fix &amp; Enhancement Release</a></li>
  <li><a href="#254---bug-fix-release">2.5.4 - Bug Fix Release</a></li>
  <li><a href="#252---bug-fix--enhancement-release">2.5.2 - Bug Fix &amp; Enhancement Release</a></li>
  <li><a href="#240---bug-fix--enhancement-release">2.4.0 - Bug Fix &amp; Enhancement Release</a></li>
  <li><a href="#238---bug-fix-release">2.3.8 - Bug Fix Release</a></li>
  <li><a href="#237---bug-fix-release">2.3.7 - Bug Fix Release</a></li>
  <li><a href="#236---bug-fix--enhancement-release">2.3.6 - Bug Fix &amp; Enhancement Release</a></li>
  <li><a href="#235---bug-fix--enhancement-release">2.3.5 - Bug Fix &amp; Enhancement Release</a></li>
</ul>
<h3 id="2708---bug-fix--enhancement-release">2.7.0.8 - Bug Fix &amp; Enhancement Release</h3>
<ul>
  <li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;Fix: copy fixed utils from Arduino Support plugin.</li>
  <li>Fix: nasty bug introducing typing delay with preview enabled.</li>
  <li>in the process</li>
  <li>Fix: diagnostic-2012, kotlin NPE.</li>
  <li>Fix: Paste Image: old crop settings out of bounds for new image caused exception</li>
  <li>Fix: for #651, Drop image with dialog issues
    <ul>
      <li>Spaces in file name were url encoded</li>
      <li>Copy dragging a file leaves its original directory instead of setting it to the closest
        or best guess based on the destination file. Should be the same as if the image was
        pasted into the file. If the destination directory is the same as the source then a new
        name should be generated to uniquify it.</li>
    </ul>
  </li>
  <li>Add: in Paste/Modify Image if dragging the highlight selection without having highlight
    enabled or no border, inner nor outer fill enabled, will enable highlight and border to
    provide feedback otherwise it is confusing.
    <ul>
      <li>Add: drag selection can be used for cropping if image tab is selected and <code>Use mouse selection only for highlight</code> is not selected.</li>
      <li>Fix: only copy image to transparent if Image tab is selected. The rest leave as is.</li>
      <li>Add: restart notification if changing full highlight combinations</li>
    </ul>
  </li>
  <li>Add: Image Paste highlight option to annotate an area of the image.</li>
  <li>Add: option to disable synthetic highlight attributes.
    <ul>
      <li>Fix: #648, too many element types registered, Option for full syntax highlighter
        combinations, disabling creates minimal set to reduce the limit of short index for these in
        the IDE.</li>
    </ul>
  </li>
  <li>Add: Code Style option to treat <code>Hard Wraps</code> parser option as if soft-wraps are enabled.</li>
  <li>Add: Main option to force soft-wraps mode for file when opening if <code>Hard Wraps</code> are enabled</li>
</ul>
<h3 id="270---bug-fix--enhancement-release">2.7.0 - Bug Fix &amp; Enhancement Release</h3>
<ul>
  <li>
    <p>Fix: jekyll parser option notification would not use the file's scope based profile.</p>
  </li>
  <li>
    <p>Fix: bump up dependencies to newer versions this</p>
  </li>
  <li>
    <p>Fix: #647, md to html link conversion not working for exported files on Windows</p>
  </li>
  <li>
    <p>Fix: exported files without stylesheet should not decorate link with resolved status
    class.</p>
  </li>
  <li>
    <p>Fix: <code>{% include</code> link resolution does not work without a VCS root.</p>
  </li>
  <li>
    <p>Fix: Jekyll <code>{% include &quot;&quot; %}</code> completions would not work unless there was an <code>.html</code>
    extension between the strings.</p>
  </li>
  <li>
    <p>Fix: update for 2019.1 eap</p>
  </li>
  <li>
    <p>Fix: intentions missing groupKey were not showing up or being run</p>
  </li>
  <li>
    <p>Fix: make hex text dialog a licensed feature instead of dev feature.</p>
  </li>
  <li>
    <p>Fix: diagnostic/1931, possible fix for intermittent based sequence index out of bounds fix</p>
  </li>
  <li>
    <p>Fix: catch exception when github tasks request fails</p>
  </li>
  <li>
    <p>Fix: settings for HTML paste are not dependent on paste handler override.</p>
  </li>
  <li>
    <p>Fix: when ask on paste for html paste options would cause subsequent undo to fail due to
    temporary file modification.</p>
  </li>
  <li>
    <p>Fix: reverse the order of split editor configuration for &quot;Show editor and preview&quot; and &quot;Show
    editor only&quot;</p>
  </li>
  <li>
    <p>Fix: for API change in 2019.1 EAP.</p>
  </li>
  <li>
    <p>Add: <code>Simple structure view</code> option to display only heading hierarchy in the structure view</p>
  </li>
  <li>
    <p>Fix: optimize parser PSI generation by using hash map for type to factory function</p>
  </li>
  <li>
    <p>Fix: diagnostic/1849, ClassCastException: LeafPsiElement cannot be cast to
    MultiMarkdownLinkElement</p>
  </li>
  <li>
    <p>Fix: image reference links to references with wrong file type or not raw would not register as
    references to the reference definition. Added <code>getExactReference()</code> to return reference only
    if it is an exact match, <code>getReference()</code> not matches strictly by id since it is used for
    navigation and usages.</p>
  </li>
  <li>
    <p>Add: <code>Use Style Attribute</code> option to HTML Export settings. When enabled will apply stylesheet
    via <code>style</code> attribute of each element as done for <code>Copy Markdown as HTML mime content</code>.</p>
    <p><strong>NOTE:</strong> stylesheet is expected to be in the same format as <code>COPY_HTML_MIME</code> stylesheet. See
    <a href="https://github.com/vsch/idea-multimarkdown/wiki/Rendering-Profiles-Settings#copy-markdown-to-html-formatted-text-profile">Copy Markdown to HTML formatted Text Profile</a></p>
    <p><strong>NOTE:</strong> if <code>No Stylesheets, No Scripts</code> is selected then only styles explicitly defined by the
    profile will be used. If this option is not selected then <code>COPY_HTML_MIME</code> profile stylesheet
    will be used or if the <code>COPY_HTML_MIME</code> profile is not defined then the
    <a href="https://github.com/vsch/idea-multimarkdown/blob/master/resources/com/vladsch/idea/multimarkdown/html_mime_default.css">default stylesheet for <code>COPY_HTML_MIME</code></a> will be used.</p>
  </li>
  <li>
    <p>Fix: move annotations for <code>Reference Links</code> to inspections</p>
  </li>
  <li>
    <p>Fix: move annotations for <code>References</code> to inspections</p>
  </li>
  <li>
    <p>Fix: move annotations for <code>Emoji</code> to inspections</p>
  </li>
  <li>
    <p>Fix: move annotations for <code>Anchor</code> to inspections</p>
  </li>
  <li>
    <p>Fix: move annotations for <code>Headings</code> to inspections</p>
  </li>
  <li>
    <p>Fix: move annotations for <code>Tables</code> to inspections and add quick fix for column spans</p>
  </li>
  <li>
    <p>Fix: move annotations for <code>List Items</code> and <code>Possible list items</code> to inspections</p>
  </li>
  <li>
    <p>Add: Html Generation option to not wrap paragraphs in <code>&lt;p&gt;</code> and use <code>&lt;br&gt;</code> between paragraphs
    instead. Useful for HTML exported files for use in Swing panels</p>
  </li>
  <li>
    <p>Add: Html Export target file path options to add to target directory. Useful if need to
    flatten directory structure of markdown files to a single directory for exported HTML</p>
    <ul>
      <li>Add path relative to project</li>
      <li>Add path relative to parent directory</li>
      <li>Add file name only</li>
    </ul>
  </li>
  <li>
    <p>Add: same file path type options as target path for export image copied file path.</p>
  </li>
  <li>
    <p>Fix: IDE hangs when copying text containing the macro references which contained recursive
    macros.</p>
  </li>
  <li>
    <p>Fix: document format to ensure one blank line after macro definition</p>
  </li>
  <li>
    <p>Fix: <code>&lt;&gt;</code> wrapped auto links would prevent following bare auto-links from being parsed.</p>
  </li>
  <li>
    <p>Add: all elements intention to select element if intention displays dialog to give user
    feedback on which element is being used.</p>
  </li>
  <li>
    <p>Fix: do not highlight auto links as errors if remote link validation is disabled</p>
  </li>
  <li>
    <p>Fix: remote link annotation disabled by custom URI scheme handler</p>
  </li>
  <li>
    <p>Fix: #640, java.lang.NullPointerException with HtmlPasteOptionsForm</p>
  </li>
  <li>
    <p>Add: Parser
    <a href="https://github.com/vsch/idea-multimarkdown/wiki/Macros-Extension">Macros Extension</a></p>
  </li>
  <li>
    <p>Fix: list item indent/unindent could insert <code>&amp;nbsp;</code> inserted/removed during wrapping but do
    not perform wrapping, causing the <code>&amp;nbsp;</code> to be left in the text.</p>
  </li>
  <li>
    <p>Add: intention for auto link to explicit link conversion and vice-versa</p>
  </li>
  <li>
    <p>Fix: #605, Support for system specific protocol handlers. Pass through custom protocols to IDE
    browser launcher.</p>
  </li>
  <li>
    <p>Fix: to not highlight external URL links which consist of only the protocol at the end of the
    line.</p>
  </li>
  <li>
    <p>Add: color scheme export to save only non-synthetic attributes: <code>Intellij IDEA color scheme, reduced markdown (.icls)</code></p>
  </li>
  <li>
    <p>Add: validation to auto-link remote url and completion/validation to anchor ref</p>
  </li>
  <li>
    <p>Add: url based parser settings for remote link markdown parsing. For now hardcoded for GitHub,
    GitLab and legacy GitBook compatibility. New GitBook anchor links not supported yet.</p>
  </li>
  <li>
    <p>Fix: diagnostic/1827, Empty collection can't be reduced.</p>
  </li>
  <li>
    <p>Fix: broken remote URL links to markdown files validation and anchor ref completions</p>
  </li>
  <li>
    <p>Add: quick fix intention for fixing unresolved anchor refs when a match can be made by
    ignoring case and removing duplicated <code>-</code></p>
  </li>
  <li>
    <p>Fix: GitHub heading ids do not convert non-ascii to lowercase.</p>
    <ul>
      <li>Add: <code>Heading ids lowercase non-ascii text</code>, selected for:
        <ul>
          <li>GitLab profile</li>
          <li>GitBook profile</li>
          <li>CommonMark profile</li>
        </ul>
      </li>
    </ul>
  </li>
  <li>
    <p>Fix: formatter for extension</p>
  </li>
  <li>
    <p>Fix: invalid anchor refs not annotated for local links (broken by remote link validation)</p>
  </li>
  <li>
    <p>Add: intention for unresolved link addresses starting with <code>www.</code> to prefix with <code>https://</code>
    and <code>http://</code>. If remote link validation is enabled then only prefix which results in valid
    link address will be shown in the intention. If the resulting address reports as permanently
    moved then also add the destination location to intentions.</p>
  </li>
  <li>
    <p>Add: handling of HTTP:301 for remote content and intention to update link address</p>
  </li>
  <li>
    <p>Fix: for remote content cache only store list of anchors instead of content, more compact and
    provides the needed data</p>
  </li>
  <li>
    <p>Fix: remove directories from link completions to reduce noise in completions</p>
  </li>
  <li>
    <p>Fix: remote image links showed as unresolved, now IOExceptions during fetching treated as
    resolved.</p>
  </li>
  <li>
    <p>Fix: remove links returning image data now treated as resolved.</p>
  </li>
  <li>
    <p>Fix: #637, Links from main project repository to files in a sub-directory repository show
    unresolved</p>
  </li>
  <li>
    <p>Add: unresolved remote link address annotation error.</p>
  </li>
  <li>
    <p>Add: in settings total remote link count and memory use for remote content.</p>
  </li>
  <li>
    <p>Fix: only cache remote content when it is needed for anchor ref validation. For remote link
    validation only store the fact that it exists.</p>
  </li>
  <li>
    <p>Add: remote link content cache to use for validating remote content links and anchor refs</p>
  </li>
  <li>
    <p>Add: option to enable validation of remote links (annotates unresolved link if server returns
    error)</p>
  </li>
  <li>
    <p>Fix: remove anchor ref error annotation for links which do not resolve to a project file or do
    not exist if validating remote link anchor refs</p>
  </li>
  <li>
    <p>Add: error annotation for links to HTML files in project with anchor refs which do not link to
    <code>a</code> or <code>h1</code> through <code>h6</code> html tags with <code>name</code> or <code>id</code> attribute given by anchor ref</p>
  </li>
  <li>
    <p>Add: anchor link completion for links to HTML files in project to <code>a</code> or <code>h1</code> through <code>h6</code>
    html tags with <code>name</code> or <code>id</code> attribute giving the anchor ref</p>
  </li>
  <li>
    <p>Add: anchor link completion on external URLs which do not resolve to a project file.</p>
    <ul>
      <li>Special handling if file extension matches a Markdown Language extension, will download the
        markdown file and will render it as HTML to extract anchor definitions</li>
      <li>Special handling for GitHub (ones starting with <a href="http://">http://</a> or <a href="https://">https://</a> followed by github.com)
        <ul>
          <li>markdown files: If the link is to a <code>blob</code> type then will use <code>raw</code> type URL to get
            Markdown so it can be correctly rendered as HTML to extract anchor definitions.</li>
          <li>html content:
            <ul>
              <li>remove <code>user-content-</code> prefix from anchor refs (GitHub adds these automatically)</li>
              <li>remove <code>[0-9a-fA-F]{32}-[0-9a-fA-F]{40}</code> looking anchor ids</li>
            </ul>
          </li>
        </ul>
      </li>
      <li>Special handling for GitLab (ones starting with <a href="http://">http://</a> or <a href="https://">https://</a> followed by gitlab.com)
        <ul>
          <li>markdown files: If the link is to a <code>blob</code> type then will use <code>raw</code> type URL to get
            Markdown so it can be correctly rendered as HTML to extract anchor definitions.</li>
          <li>html content:
            <ul>
              <li>remove <code>[0-9a-fA-F]{32}-[0-9a-fA-F]{40}</code> looking anchor ids</li>
            </ul>
          </li>
        </ul>
      </li>
    </ul>
  </li>
</ul>
<h3 id="260---bug-fix--enhancement-release">2.6.0 - Bug Fix &amp; Enhancement Release</h3>
<ul>
  <li>Fix: change definition indent maximum to 4, beyond which it converts the text to indented
    code.</li>
  <li>Fix: definition formatting would not add indent removal causing contained block quote prefix
    to be doubled</li>
  <li>Add: option to remove prefixes when joining lines</li>
  <li>Fix: move code style <code>Continuation Lines</code> indent into <code>Text</code> code style panel.</li>
  <li>Add: <code>Left Justified</code> option to ordered list style options</li>
  <li>Fix: force code style parser settings to CommonMark</li>
  <li>Fix: change code style sample parsing flags to modify parser flags to allow formatting all
    sample elements.</li>
  <li>Fix: settings &quot;Manage...&quot; exception in DataGrip without an open project. Now uses user home
    dir as default directory without an open project.</li>
  <li>Fix: fenced code and indented code indented with tabs would not minimize indent during
    formatting.</li>
  <li>Fix: HTML to markdown conversion
    <ul>
      <li>Fix: #268, Pipe characters are not escaped in Table (FlexmarkHtmlParser)
        <ul>
          <li>Fix: escape pipe characters in text (to avoid accidental use as table or other markup)
            when not inline code nor fenced code</li>
          <li>Fix: escape back ticks when inside code</li>
          <li>Fix: disable escaping of <code>[]</code> when inside code
            <ul>
              <li>Fix: disable escaping of <code>\</code> when inside code</li>
            </ul>
          </li>
          <li>Fix: replace non-break space with space when inside code</li>
        </ul>
      </li>
    </ul>
  </li>
  <li>Fix: <code>FlexmarkHtmlParser.BR_AS_EXTRA_BLANK_LINES</code> now adds <code>&lt;br /&gt;</code> followed by blank line</li>
  <li>Fix: JavaFx Browser initialization bug introduced by 2016.3 compatibility fix.</li>
  <li>Add: &quot;Paste HTML&quot; button to HTML Paste Options dialog to paste HTML without conversion to
    markdown.</li>
  <li>Fix: clean up code style formatting and preview of style changes
    <ul>
      <li>style changes are now highlighted to properly reflect the last change, not whole document
        reformat changes</li>
      <li>prefix changes would not be applied (or formatted) if text wrap for paragraphs was disabled,
        affected list items, definitions, block quotes</li>
      <li>block quote prefix (compact with space) always inserted space after firs <code>&gt;</code> instead of last
        <code>&gt;</code></li>
      <li>TOC with html language option would not update preview</li>
      <li>Remove unused list formatting options</li>
    </ul>
  </li>
  <li>Add: link text suggestion for user label <code>@username</code> for GitHub user links of the form:
    <code>https://github.com/username</code></li>
  <li>Change: remove runtime null assertions for function arguments</li>
  <li>Fix: scroll sync not working in 2018.3 EAP</li>
  <li>Fix: change lambdas to functions to have <code>arguments</code> available (causing exception in JetBrains
    Open JDK 1.8.0_152-release-1293-b10 x86_64</li>
  <li>Add: extra diagnostic information for Swing Browser <code>EmptyStackException</code></li>
  <li>Fix: diagnostic/1759, kotlin arguments erroneously defined as not nullable.</li>
  <li>Fix: 2016.3 compatibility</li>
  <li>Fix: markdown code style settings to be created from file when available to allow IDE scope
    based resolution for markdown files to work properly.</li>
  <li>Add: HTML Settings option <code>Add &lt;!DOCTYPE html&gt;</code> to enable/disable having doc type at top of
    document. Required by Katex to work.</li>
  <li>Fix: update emoji icons</li>
  <li>Fix: GitLab math blocks to display as blocks instead of inlines</li>
  <li>Fix: disable tab overrides if there is a selection in the editor or multiple carets</li>
  <li>Change: split math and chart options from GitLab so that each can be selected without GitLab
    extensions if GitLab extensions are not selected.</li>
  <li>Add:
    <a href="https://gitlab.com/gitlab-org/gitlab-ce/blob/master/doc/user/markdown.md">GitLab Flavoured Markdown</a>
    parsing and rendering functionality
    <ul>
      <li>Math inline using <code>$``$</code> and fenced code blocks with info of <code>math</code> using
        <a href="https://github.com/Khan/KaTeX">Katex</a></li>
      <li>Chart fenced code blocks with info of <code>mermaid</code> using
        <a href="https://github.com/knsv/mermaid">Mermaid</a></li>
      <li>Inserted text (underlined) via <code>{+text+}</code> or <code>[+text+]</code></li>
      <li>Deleted text (strike through) via <code>{-text-}</code> or <code>[-text-]</code></li>
      <li>Multiline block quotes using <code>&gt;&gt;&gt;</code> at start of line to mark block start and <code>&lt;&lt;&lt;</code> at start
        of line to mark block end.
        <a href="https://gitlab.com/gitlab-org/gitlab-ce/blob/master/doc/user/markdown.md#multiline-blockquote">GFM: Multiline Blockquote</a></li>
      <li>Video image link rendering
        <a href="https://gitlab.com/gitlab-org/gitlab-ce/blob/master/doc/user/markdown.md#videos">GFM: Videos</a></li>
    </ul>
  </li>
  <li>Fix: disable tab override when popup is showing to allow tab to be used for completions.</li>
  <li>Fix: with CommonMark list type bullet list item after ordered list and vice versa, would allow
    indentation in error.</li>
  <li>Fix: #469, pressing tab in a empty list item should indent the list
    <ul>
      <li>added option &quot;List item indentation&quot; under &quot;Tab/Backtab Overrides&quot; which enables using
        tab/backtab to change list item indentation</li>
    </ul>
  </li>
  <li>Fix: #541, using tab to move to the next table cell
    <ul>
      <li>added option &quot;Table Navigation&quot; under &quot;Tab/Backtab Overrides&quot; which enables using
        tab/backtab to navigate table cells</li>
    </ul>
  </li>
  <li>Fix: GitHub issue completions for 2018.3 EAP</li>
  <li>Fix: #577, Add feature to move table columns, added Move table column left/right</li>
  <li>Fix: remove line/search highlights from plain HTML preview</li>
  <li>Fix: remove auto links from spellchecking elements</li>
  <li>Fix: partial fix for list item needs blank line annotation for list in block quotes</li>
  <li>Fix: #610, hope can have more paste option, add HTML paste options to suppress conversion of
    inline and heading elements</li>
  <li>Fix: #623, Directory linking occasionally broken</li>
  <li>Fix: compatibility with versions 2017.</li>
  <li>Fix: possibly fix diagnostic pycharm exception on migrate code settings</li>
  <li>Change: update deprecated API usage</li>
  <li>Change: reduce number of highlighter overlay attributes to 3609 from 8497</li>
  <li>Change: update source code for 2018.2 API changes.</li>
  <li>Fix: #621, plugin oom in goland, potential memory leak</li>
  <li>Fix: #615, Plugin can't initialize in multi-user setup, now temp directory
    <code>.markdownNavigator</code> is created under the user's home directory</li>
  <li>Fix: #620, Check keyboard shortcut changes wrong list item</li>
  <li>Fix: #619, Create unchecked list item when pressing enter next to a line with a checked list
    item</li>
  <li>Fix: reference paste to add line references to link text in the form: <code>: Line #</code> or <code>: Lines #-#</code></li>
  <li>Fix: diagnostic/1575, <code>node.treeNext must not be null</code></li>
  <li>Fix: wrong range calculation for #612 fix</li>
  <li>Fix: #611, Backspace in empty check mark box deletes check mark</li>
  <li>Fix: #612, Code folding eats one character for underline headers</li>
  <li>Add: HTML comment folding and options</li>
  <li>Fix: diagnostic, parent already disposed</li>
</ul>
<h3 id="254---bug-fix-release">2.5.4 - Bug Fix Release</h3>
<ul>
  <li>Fix: High Sierra JavaFx issue, implement JavaFX initialization as per Markdown Support.</li>
  <li>Add: Document Translation using Yandex.Translate and flexmark-java translation helper API,
    configured in debug settings. This is a temporary implementation which will be moved to its
    own plugin in the future.</li>
  <li>Fix: diagnostic/NPE on JavaFX WebView save editor state.</li>
  <li>Add: conversion of emoji from HTML mime copied back from Apple mail.</li>
  <li>Fix: paste image file name from link name would not be URI decoded.</li>
  <li>Add: folding of list items longer than one line of text</li>
  <li>Fix: #590, &quot;Create directories and folder&quot; does only create directory on first hit.</li>
  <li>Fix: #591, uncomment does not remove leading space, removed padding spaces from comment
    prefix/suffix.</li>
  <li>Fix: shorten toolbar by moving more rare actions to popup menus: list, table, misc and copy</li>
  <li>Fix: wrap on typing <code>&gt;</code> to insert block quote level into existing block quote or using
    backspace to remove a block quote level</li>
  <li>Fix: wrap on typing backspace in footnote definition would replicate the first line prefix on
    continuation lines</li>
  <li>Fix: inserting an EOL in a list item before text matching bullet list marker or numbered would
    double the list marker on the resulting line</li>
  <li>Add: option for escape/unescape all numbered list lead-in of <code>number.</code> when wrapping text.</li>
  <li>Fix: diagnostic java.lang.RuntimeException in ImageUtils.getImageFromTransferable when pasting
    an image</li>
  <li>Fix: java.lang.Throwable: Invalid file: DB VirtualFile: table when caused sometimes by file
    watcher requesting markdown files be re-parsed</li>
  <li>Fix: diagnostic java.lang.IllegalArgumentException: Argument for @NotNull parameter 'project'</li>
  <li>Fix: accept license code when extra spaces are added before EOL in e-mail.</li>
  <li>Fix: diagnostic/ prevSibling should not be null.</li>
  <li>Add: option to disable gutter margin for markdown documents</li>
  <li>Add: option to disable line markers to main settings panel.</li>
  <li>Fix: file types by specific extension completion did not work</li>
  <li>Fix: link resolution would not find files with nested extensions like <code>blade.php</code></li>
  <li>Change: toggle inline attribute when the caret is at the end of a non-space span now restores
    the caret position when applying the style instead of end of the span, inside the markers.
    More natural when inlining a word to continue typing.</li>
  <li>Fix: #575, Broken Spell Checker, spell checking not working on paragraph text for basic
    plugin.</li>
  <li>Fix: JavaFX detection with Android Studio 3.0</li>
  <li>Fix: #434, Spellchecker context menu is duplicated</li>
  <li>Add: <code>Use rename for spelling error intention</code> to allow turning off <code>Rename to:</code> spellchecking
    intention and use the regular <code>Change to:</code> intention.</li>
  <li>Fix: remove old storage macros</li>
</ul>
<h3 id="252---bug-fix--enhancement-release">2.5.2 - Bug Fix &amp; Enhancement Release</h3>
<ul>
  <li>
    <p>Fix: newer API use which causes exceptions in 2017.3 IDE versions.</p>
  </li>
  <li>
    <p>Add: link and image paste/drop options to exported/imported settings management</p>
  </li>
  <li>
    <p>Fix: preview window would show links resolved after link map transformation to <code>http://</code> as
    <code>local only</code>.</p>
  </li>
  <li>
    <p>Fix: #567, '_'s are replaced by '-'s in TOC, GitHub now preserves <code>_</code> in heading anchor refs</p>
  </li>
  <li>
    <p>Fix: paste handler using 2018 api <code>getSelectedEditor()</code></p>
  </li>
  <li>
    <p>Fix: #564, Some problems with &quot;Copy / Drop Image&quot; dialog. Modify image directory history drop
    down to include, in order of priority:</p>
    <ul>
      <li>last used image directory of the current file</li>
      <li>image directories of images in the current file, ordered by count of occurrence</li>
      <li>image directories in the current project, ordered by count of occurrence</li>
    </ul>
  </li>
  <li>
    <p>Fix: absolute <code>http://</code> link from main repo to wiki page which is located in a sub-directory
    would show as unresolved</p>
  </li>
  <li>
    <p>Add: help topics for all settings panels</p>
  </li>
  <li>
    <p>Fix: improve spelling error alignment in text with many embedded inline markers</p>
  </li>
  <li>
    <p>Fix: compatibility with IDE versions 2016.3, limit since version to 163.15529</p>
  </li>
  <li>
    <p>Fix: COPY_HTML_MIME and PDF stylesheets now combine user provided attributes with defaults so
    only difference has to be added to custom CSS.</p>
  </li>
  <li>
    <p>Add: emoji images to COPY_HTML_MIME and PDF stylesheets so emoji display properly.</p>
  </li>
  <li>
    <p>Fix: wiki page file name rename refactoring. Broken since 2.4.0 release</p>
  </li>
  <li>
    <p>Fix: manually exporting a file from the toolbar now treats this as if <code>Export on Save</code> was set
    for the file.</p>
  </li>
  <li>
    <p>Fix: update GitHub wiki home link resolution for image links. Changed recently by GitHub</p>
  </li>
  <li>
    <p>Fix: trailing spaces filter behavior changed, postponed trailing spaces would all be deleted.</p>
  </li>
  <li>
    <p>Fix: reverse fix for &quot;WebView should be available by now&quot;, was causing deadlock if
    Accessibility features were enabled.</p>
  </li>
  <li>
    <p>Fix: JavaFX preview was using project profile parser settings, not scope based rendering
    profile parser settings.</p>
  </li>
  <li>
    <p>Fix: Formatting default table column alignment when no alignment marker is specified, header
    columns are centered, body columns are left aligned.</p>
  </li>
  <li>
    <p>Add: Export to treat emoji images as image linked files.</p>
  </li>
  <li>
    <p>Fix: NoSuchMethodError for IDE versions &lt; 2018</p>
  </li>
  <li>
    <p>Fix: image paste/modify dialog to not add _# suffix to file name if pasting on image target
    ref and on paste action is &quot;Replace file content&quot; for link-ref targeting an image, since the
    name should be the same as the link-ref in order to replace file content. Changing the name
    will save it under a new file and change the link-ref.</p>
  </li>
  <li>
    <p>Fix: remove old project settings handling and replace with IDE provided method. Old settings
    copied to default project settings on first plugin initialization after upgrade. Now default
    project settings support having defaults for Rendering &gt; Profiles</p>
  </li>
  <li>
    <p>Add: Format options for Attributes:</p>
    <ul>
      <li>space inside braces: no change, add, remove</li>
      <li>space around attribute equal sign: no change, add, remove</li>
      <li>attribute value quotes:
        <ul>
          <li>No Change</li>
          <li>None, double, single</li>
          <li>None, single, double</li>
          <li>Double, single</li>
          <li>Double quotes</li>
          <li>Single, double</li>
          <li>Single quotes</li>
        </ul>
      </li>
    </ul>
  </li>
  <li>
    <p>Fix: table formatting would disable wrap on typing unnecessarily because it failed to properly
    detect table at caret offset.</p>
  </li>
  <li>
    <p>Add Table Caption formatting options:</p>
    <ul>
      <li>Caption: no change, always add, remove if empty, always remove;</li>
      <li>Caption space: no change, space around text, trim spaces check box.</li>
    </ul>
  </li>
  <li>
    <p>Add: #556, Default editor layout &gt; Use last selected layout, option to have layout follow last
    editor layout change action.</p>
  </li>
  <li>
    <p>Fix: typographic <code>'</code> breaking words for spell checker generates erroneous spelling errors.</p>
  </li>
  <li>
    <p>Fix: spell checking now done across inline markup. Error underline and Change to: intention do
    not work well because of interspersed markup messing up offsets but at least spelling errors
    will be highlighted. For example <code>do**sn't**</code> will now show a spelling error because the
    effective text is <code>dosn't</code>.</p>
  </li>
  <li>
    <p>Add: history to CSS settings URI text box.</p>
  </li>
  <li>
    <p>Fix: default completion for explicit <code>http://</code> absolute links to wiki pages uses the extension</p>
  </li>
  <li>
    <p>Fix: <code>file://</code> links to wiki pages with anchor refs showed as &quot;Only resolving locally&quot; in the
    preview, all <code>file://</code> links show as resolving only locally.</p>
  </li>
  <li>
    <p>Fix: Admonition extension would be disabled if Attributes extension was not enabled.</p>
  </li>
  <li>
    <p>Add: Admonition parser extension.
    <strong><a href="https://github.com/vsch/flexmark-java/wiki/Admonition-Extension">Admonition</a></strong>, Parser
    extension based on <a href="https://squidfunk.github.io/mkdocs-material/extensions/admonition/">Admonition Extension, Material for MkDocs</a> to create block-styled side
    content.</p>
  </li>
  <li>
    <p>Fix: JavaFX WebView debug page reload in Not on FX application thread exception.</p>
  </li>
  <li>
    <p>Fix: remove the &quot;canDebug&quot; field and replace with dynamic value.</p>
  </li>
  <li>
    <p>Fix: remove all break points on connection shutdown.</p>
  </li>
  <li>
    <p>Fix: JavaFX debugger core dumping if turning off debugging while it is paused.</p>
  </li>
  <li>
    <p>Fix: Project Default settings not being copied to new projects</p>
  </li>
  <li>
    <p>Fix: intermittent preview element highlight stopped working until page refresh</p>
  </li>
  <li>
    <p>Fix: pasting a page relative URL would be mistaken for FQN reference and always paste a link
    instead of text</p>
  </li>
  <li>
    <p>Fix: renaming rendering profile would not be saved properly.</p>
  </li>
  <li>
    <p>Fix: copy action on rendering profiles caused exception</p>
  </li>
  <li>
    <p>Add: all console commands work with Chrome dev tools.</p>
  </li>
  <li>
    <p>Fix: Chrome dev tools console evals and console logging from JavFX WebView scripts.</p>
  </li>
  <li>
    <p>Fix: #561, Scroll sync and highlight preview element broken in EAP 2.4.0.44</p>
  </li>
  <li>
    <p>Remove: FirebugLite script option for JavaFX. It never worked for debugging and Chrome Dev
    Tools work really well with JavaFX WebView.</p>
  </li>
  <li>
    <p>Add: &quot;Toggle Editor Split Orientation&quot; action to toggle Vertical/Horizontal split orientation</p>
  </li>
  <li>
    <p>Add: drag/drop file inside inline, fenced or indented code to insert file name.</p>
  </li>
  <li>
    <p>Add: dropping file after end of line with virtual spaces enabled, will insert spaces to fill
    virtual spaces.</p>
  </li>
  <li>
    <p>Fix: Adding explicit attribute to heading did not put space between text and attributes
    element.</p>
  </li>
  <li>
    <p>Add: file/ref anchor target search/explore intention on unresolved link anchor ref.</p>
    <ul>
      <li>Fix: launching on an anchor and cancelling, does not show intention until file is edited.</li>
      <li>Add: do a partial match for anchor when no anchors match exactly.</li>
      <li>Add: filter text box to filter anchor list (show all partials, the rest hidden) otherwise
        too many in the list.</li>
    </ul>
  </li>
  <li>
    <p>Fix: Github collapse headers script not working in 2018.1</p>
  </li>
  <li>
    <p>Fix: intermittent position not highlighting in preview.</p>
  </li>
  <li>
    <p>Fix: Drag/Drop copy files does not save link drop options.Always resets or gets them wrong.</p>
  </li>
  <li>
    <p>Add: buttons for link and image drop/paste options in markdown settings</p>
  </li>
  <li>
    <p>Add: Updated emoji to include full set of GitHub supported ones</p>
    <ul>
      <li>Add: option to select which shortcuts to recognize:
        <ul>
          <li>Emoji Cheat Sheet</li>
          <li>GitHub</li>
          <li>Both: Emoji Cheat Sheet, GitHub (in order of preference for URL generation in HTML)</li>
          <li>Both: GitHub, Emoji Cheat Sheet (in order of preference for URL generation in HTML)</li>
        </ul>
      </li>
      <li>Add: option to select what type of image to use:
        <ul>
          <li>Images: image files only</li>
          <li>Unicode and Images: use Unicode characters when available, image file otherwise</li>
          <li>Unicode Only: only use unicode characters, don't recognize shortcuts which have no unicode
            equivalent.</li>
        </ul>
      </li>
      <li>Add: option to preview settings to replace Unicode emoji characters which have a
        corresponding image file with the image file. This allows preview browser to display Unicode
        emoji for which the browser would display unrecognized character symbol instead.</li>
    </ul>
  </li>
  <li>
    <p>Update to flexmark-java-0.32.2</p>
    <ul>
      <li>Fix: java-flexmark API changes</li>
      <li>Fix: java-flexmark Attributes processing changes</li>
      <li>Add: Parser option for Attributes assignment to text</li>
      <li>Add: Parser option for Emoji Shortcut Type, Emoji Image Type</li>
      <li>Add: Preview option for replacing Emoji unicode with image</li>
    </ul>
  </li>
  <li>
    <p>Add: settings option to allow directories as link targets. Allows directories to be used in
    links. This functionality affects operation to completions, annotations, drag/drop link
    creation and navigation.</p>
  </li>
  <li>
    <p>Add: Drag/Drop link creation in Wiki should have wiki option for link format.</p>
  </li>
  <li>
    <p>Fix: <code>http://</code> link to wiki home without the file shows as unresolved by annotator</p>
  </li>
  <li>
    <p>Fix: change explicit to wiki not showing if link format is <a href="http://">http://</a> or <a href="https://">https://</a> absolute</p>
  </li>
  <li>
    <p>Fix: when converting explicit to wiki don't generate text &amp; page ref if the explicit link text
    is the same as the file part of the target: <code>[Page-Ref](Page-Ref.md)</code> -&gt; <code>[[Page Ref]]</code>, not
    <code>[[Page-Ref|Page Ref]]</code></p>
  </li>
  <li>
    <p>Fix: Allow links to directories under the repo not to show them as unresolved. Create ref to
    directory object if it is under VCS</p>
  </li>
  <li>
    <p>Fix: drag/drop directories to create a link to the directory</p>
  </li>
  <li>
    <p>Fix: document format would remove table caption element</p>
  </li>
  <li>
    <p>Add: Query user for new id on explicit id to heading intention to save a step of rename
    refactoring it.</p>
  </li>
  <li>
    <p>Add: if a heading has explicit id attributes, rename refactoring for it is disabled since the
    id is not part of attributes.</p>
  </li>
  <li>
    <p>Add parser option to parse inline HTML for <code>&lt;a id=&quot;...&quot;&gt;</code> for anchor targets</p>
  </li>
  <li>
    <p>Fix abbreviation definition with empty abbreviation would cause an exception</p>
  </li>
  <li>
    <p>Add Option to enable/disable use of image URI query serial, used to force preview update of
    image when the image file changes. Disabled by default to reduce java image caching memory
    issues.</p>
  </li>
  <li>
    <p>Fix: custom paste handling into scratch files was not handled in CLion, possibly other
    non-Java IDEs.</p>
  </li>
  <li>
    <p>Fix: #554, Settings, Import and Copy from project do not get applied until corresponding
    settings pane is viewed. The settings would be changed but not applied until the settings pane
    was clicked on first.</p>
  </li>
  <li>
    <p>Fix: diagnostic/1159, Inserting table rows could cause an index out bounds exception</p>
  </li>
  <li>
    <p>Fix: files not under VCS root would show no completions for relative addressing, only had
    completions for <code>file://</code> format completions.</p>
  </li>
  <li>
    <p>Add: recall of the last imported settings file to make it easier to reset settings to a known
    value.</p>
  </li>
  <li>
    <p>Add: markdown Application settings to exported and imported settings.</p>
  </li>
  <li>
    <p>Fix: disable local only status for links and annotation when the link is to the file itself.</p>
  </li>
  <li>
    <p>Add: allow source/preview synchronization and search/selection highlighting in basic version.</p>
  </li>
  <li>
    <p>Fix: diagnostic/1140, NPE in flexmark-java core node renderer.</p>
  </li>
  <li>
    <p>Fix: diagnostic/1141, null editor causes exception in toolbar button test.</p>
  </li>
  <li>
    <p>Add: #549, Add settings management functionality. Now in main settings panel there is a
    &quot;Manage...&quot; button in top-right corner, clicking it pops up a menu with the following options:</p>
    <ul>
      <li><code>Copy to Project Defaults</code></li>
      <li><code>Copy from Project Defaults</code></li>
      <li><code>Export Settings</code></li>
      <li><code>Import Settings</code></li>
      <li><code>Reset Settings</code> to reset settings to default. Project defaults, current project settings
        and markdown navigator application settings.</li>
    </ul>
    <p>These actions copy from current unsaved project settings and to current unsaved project
    settings therefore you can modify settings, copy to project defaults (or export) and then
    cancel, result will be project defaults (or exported settings) having modified settings while
    project settings being those before modification.</p>
    <p>If you copy from defaults or import a file followed by <code>Cancel</code> then no settings will be
    modified.</p>
  </li>
  <li>
    <p>Fix: #548, When &quot;Auto-scroll to source&quot; is enabled in project view, markdown navigator editor
    steals focus when moving through project view with keyboard arrows.</p>
  </li>
  <li>
    <p>Fix: #542, Typographical Error in PHPStorm Preferences &gt; Editor &gt; Code Style &gt; Markdown</p>
  </li>
  <li>
    <p>Add: option in settings to enable editor paste handler registration so that paste handler is
    enabled by default. Because the IDE has a lot of formatter exceptions on paste which get
    erroneously attributed to the plugin when it delegates paste action to previous handler. Now a
    notification balloon will inform of the IDE exception and offer a link to disable paste
    handler customization.</p>
  </li>
  <li>
    <p>Fix: #546, Panel is guaranteed to be not null Regression.</p>
  </li>
  <li>
    <p>Fix: #260, Add horizontal split editor option to allow preview below the text editor. Added
    option in Languages &amp; Frameworks &gt; Markdown: <code>Vertical Text/Preview Split</code>, default not
    selected.</p>
  </li>
  <li>
    <p>Fix: #524, Dedent shortcut not working properly.</p>
  </li>
  <li>
    <p>Fix: #539, Big local images (e.g. .gif) referred to in an open .md file get locked and cause
    merge conflicts and issues on checkout. Now swing implements disable GIF images option.</p>
  </li>
  <li>
    <p>Fix: #512, Add keyboard shortcut to <code>Cycle between Preview only and Editor only</code>. Instead
    added application setting to select text/split or text/preview toggle for the toggle editor
    layout action.</p>
  </li>
  <li>
    <p>Fix: #511, <code>Cycle split layout</code> shortcut stop working when <code>Preview Only</code> is selected.</p>
  </li>
  <li>
    <p>Fix: #527, How to use <em>italics</em> instead of <em>italics</em> when pressing <code>Ctrl+I</code>. Option added to
    Languages &amp; Frameworks &gt; Markdown: <code>Use asterisks (*) for italic text</code>, enabled by default.
    When enabled italic action will use only asterisks for as markers.</p>
  </li>
  <li>
    <p>Fix: #535, Documentation for link maps and mapping groups. Documentation link added to Link
    Map settings panel.</p>
  </li>
  <li>
    <p>Fix: diagnostic/1100, start/end offset on paste beyond end of document</p>
  </li>
  <li>
    <p>Fix: clicking on a link with anchor ref by name of element would not scroll element into view</p>
  </li>
  <li>
    <p>Add: #391, #anchor tags not working. Added anchors of the form <code>&lt;a .... attr=anchorId ...&gt;...&lt;/a&gt;</code> where <code>attr</code> is <code>id</code> or <code>name</code> to be treated as anchor ref targets. NOTE: the
    first name or id attribute will be treated as the &quot;anchor target&quot; the other as a reference to
    the anchor target. If both have the same string value then renaming one will rename the other.</p>
  </li>
  <li>
    <p>Fix: regex error flexmark-java attributes parser which could cause a parsing loop</p>
  </li>
  <li>
    <p>Add: parser option to not generate duplicate dashes <code>-</code> in heading ids</p>
  </li>
  <li>
    <p>Fix: fenced code content erroneously processed GitHub issue marker <code>#</code>.</p>
  </li>
  <li>
    <p>Fix: #544, Export to PDF greyed out. Editor actions would be disabled if the text editor was
    not visible.</p>
  </li>
  <li>
    <p>Add: parser options for</p>
    <ul>
      <li><strong><a href="https://github.com/vsch/flexmark-java/wiki/Attributes-Extension">Attributes</a></strong> and</li>
      <li><strong><a href="https://github.com/vsch/flexmark-java/wiki/Enumerated-References-Extension">Enumerated References</a></strong>
        parser extensions</li>
      <li>Add: heading intentions to add/remove explicit id</li>
      <li>Add: completions for link anchors to id attribute values</li>
      <li>Add: completions for enumerated references and reference formats</li>
      <li>Add: formatting options and formatting for Enumerated References</li>
      <li>Add: error/unused annotations for enumerated reference, enumerated format and attribute id</li>
      <li>Add: refactoring/navigation for Enumerated Reference format id's, Attribute Id's, Enumerated
        Reference link/text.</li>
    </ul>
  </li>
  <li>
    <p>Fix: diagnostic: 1055, sometimes virtual file == null for a PsiFile causing an exception.</p>
  </li>
  <li>
    <p>Add: option to add serial query suffix to CSS URI which increments when the css file changes
    (only <a href="file://">file://</a> URI's and document relative URLs are supported.)</p>
  </li>
  <li>
    <p>Fix: diagnostic 1030, when bread-crumb provider steps up to file level while looking for
    headings.</p>
  </li>
  <li>
    <p>Fix: diagnostic: 1032, sometimes an exception is thrown &quot;AssertionError: Unexpected content
    storage modification&quot;</p>
  </li>
  <li>
    <p>Fix: diagnostic 1033, paste handler exception <code>IllegalStateException: Clipboard is busy</code></p>
  </li>
  <li>
    <p>Fix: diagnostic 1035, null pointer exception in Swing preview when image tag has no <code>src</code>
    attribute.</p>
  </li>
  <li>
    <p>Fix: diagnostic 1047, sometimes an IOException is generated if markdown sub-type is requested
    during indexing operation.</p>
  </li>
</ul>
<h3 id="240---bug-fix--enhancement-release">2.4.0 - Bug Fix &amp; Enhancement Release</h3>
<ul>
  <li>Fix: #517, Invalid tool tip for &quot;Show Breadcrumb text&quot;</li>
  <li>Change: #520, Not working: As you type automation: Double of bold/emphasis markers and remove
    inserted ones if a space is typed. Enable these options in code style, disabled by default.</li>
  <li>Fix: #509, Text with colons is incorrectly interpreted as an invalid emoji shortcut</li>
  <li>Add: #507, How to be sure that HTML auto generated link will have unchanged url. Link format
    option for HTML export: page relative, project relative, http: absolute, file: absolute if
    option <code>Link to exported HTML</code> is not selected.</li>
  <li>Add: #466, Indents with 4 spaces instead of 2 as I like. Code style option for indent size
    added sets number of spaces to insert when pressing tab.</li>
  <li>Change: Remove attribute and settings migration from pre 2.3.0 versions.</li>
  <li>Add: nested heading outline collapsing</li>
  <li>Fix: improved HTML to markdown conversion from Apple Mail copied text.</li>
  <li>Fix: don't show emoji completions in link address part ( http: triggers it)</li>
  <li>Fix: abbreviation navigation and refactoring was not implemented</li>
  <li>Fix: line markers generate for leaf elements only, performance improvement</li>
  <li>Fix: swing preview on linux not showing fixed pitch font for code</li>
  <li>Fix: Task list items now require indent at task item marker not item content, to match GitHub
    parsing rules. Indenting to content column treats children as inline code and child list items
    not separated by a blank line as lazy continuation lines.</li>
  <li>Fix: formatter for new task item indentation rules.</li>
  <li>Fix: remove <code>Replace File Content</code> option from non-image target ref drop downs in paste/modify
    image dialog, and all link options from copy/drop image dialog and link drop/paste ref options
    dialog.</li>
  <li>Fix: #489, Paste Image does not create file if parent directory does not exist.</li>
  <li>Fix: #484, Open links in preview, not browser. Option added to preview settings to have page
    relative and repo relative links resolve to GitHub files when selected. When not selected they
    resolve to local project files.</li>
  <li>Fix: #486, Multi-line links do not preview correctly, when in <code>Line</code> preview element highlight
    mode.</li>
  <li>Fix: #481, Will not allow me to crop beyond 200px, now limits are derived from the image
    dimensions and image operations.</li>
  <li>Fix: Update to latest flexmark-java supporting CommonMark Spec 0.28.</li>
  <li>Fix: TOC entries would exclude typographic characters when &quot;text only&quot; option was used with
    typographic parser extension enabled.</li>
  <li>Fix: HTML to Markdown not adding HTML comment between consecutive lists</li>
  <li>Fix: #479, Multi-line URL images are not converted in PDF export or Copy HTML Mime</li>
  <li>Add: Show &quot;Apply all '...'&quot; intention on any element option to allow showing file level
    intentions to be available on any element. Otherwise only shown on elements which they affect.</li>
  <li>Add: enable image intentions on multi-line URL image links</li>
  <li>Add: Code Folding option in settings for embedded image links</li>
  <li>Add: HTML generation options to convert image links to embedded images, with separate option
    for <a href="http://">http://</a> and <a href="https://">https://</a> image urls.</li>
  <li>Add: base64 embedded image display in Swing Preview browser</li>
  <li>Add: <code>Base64 Encoded</code> as a link format for pasted images and dropped files to the Paste Image
    dialog.</li>
  <li>Fix: base64 encode intention would keep path of url if it was present</li>
  <li>Fix: image reference links to references with base64 encoded images would show as unresolved</li>
  <li>Add: intentions to convert images to base64 encoding and vice-versa</li>
  <li>Fix: base64 encoded embedded images did not display in JavaFX preview</li>
  <li>Fix: preview navigation to links with anchor refs and line anchor refs</li>
  <li>Fix: dropping a file in a document appends <code>null</code> string to the file name in error.</li>
  <li>Fix: #468, Move (refactoring) of .md files breaks links to sections in same file.</li>
  <li>Fix: reference paste with line ref anchor would always paste page relative format URL
    regardless of the link paste format (set with file copy drop action).</li>
  <li>Fix: diagnostics/713, tree view icon update before <code>FileManager</code> has been initialized will to
    return markdown file type (without resolving sub-type).</li>
  <li>Add: Convert markdown to HTML intention for fenced code and indented code blocks.</li>
  <li>Fix: unresolved link references would be rendered in HTML instead of being treated as plain
    text. Broken by <code>Reference</code> link map code.</li>
  <li>Fix: paste reference past end of line will insert spaces to caret column before inserting
    link.</li>
  <li>Fix: links from FQN references with spaces did not url encode the link.</li>
  <li>Fix: reference to link conversion for PhpStorm to truncate the reference at the <code>:</code> since
    PhpStorm is not able to convert FQN strings with class method names.</li>
  <li>Add: use QualifiedNameProviders to resolve reference to link conversion.</li>
  <li>Add: logic to not convert qualified string to link when caret is inside inline code, fenced
    code or between two back-ticks.</li>
  <li>Fix: HTTP links with anchor refs should not highlight anchor links as unresolved.</li>
  <li>Add: paste of file reference with or without line number converted to paste of link with
    GitHub line ref anchor added if line number is part of the reference. This will insert/replace
    link.</li>
  <li>Fix: non-vcs projects links without a path would show unresolved even when files exist.</li>
</ul>
<h3 id="238---bug-fix-release">2.3.8 - Bug Fix Release</h3>
<ul>
  <li>Add: GitHub Line reference anchors in the form <code>L#</code> or <code>L#-L#</code> for line ranges. Now navigating
    to such an anchor in a project file will move the caret to the line and if second form is used
    select the lines.</li>
  <li>Add: with JavaFX browser clicking on task item box in preview toggles open/closed task status
    in source.</li>
  <li>Fix: image refs and image links to non-raw GitHub image files to show as warning. Only show
    warning for references not in raw when referenced by image refs.</li>
  <li>Add: Apply all '...' in file intentions where these make sense.</li>
  <li>Add: intention to convert between typographic symbols and markdown smarts/quotes extension
    text.</li>
  <li>Add: <code>HTML block deep parsing</code> parser option to allow better handling of raw text tag parsing
    when they are not the first tag on the first line of the block.</li>
  <li>Add: split inline code class <code>line-spliced</code> for code elements split across multiple lines not
    to appear as two inline code elements in preview.</li>
  <li>Fix: HTML generation with line source line highlighting when inline styling spans source lines</li>
  <li>Add: #74, Launching external URLs inside the browser, now <code>navigate to declaration</code> opens url
    in browser, ftp or mail client depending on the link. Can also use line markers for navigation
    of these elements.</li>
  <li>Fix: parsing of lists in fixed 4 spaces mode would not allow last item to be loose</li>
  <li>Fix: reference to non-image but not used as image target warning not raw.</li>
  <li>Fix: exception when navigating next/previous table cells in editor without an associated
    virtual file.</li>
  <li>Fix: #461, TOC with HTML generated content causes exception if skipping heading levels</li>
  <li>Fix: #460, TOC options do not change default Heading level</li>
  <li>Fix: #459, PDF export does not resolve local ref anchors</li>
  <li>Fix: #456, Register r markdown code chunk prefix</li>
  <li>Fix: #453, Option to hide toolbar</li>
  <li>Fix: #454, Incorrect filename inspection error, weak warning now only for wiki link targets
    that contain spaces in resolved link.</li>
  <li>Fix: flexmark-java issue 109, image ref loosing title tag.</li>
  <li>Add: GitBook compatible include tags when <code>GitBook compatibility mode</code> is enabled in <code>Parser</code>
    options.</li>
  <li>Fix: Nested stub index exception in reference search</li>
  <li>Fix: breadcrumb tooltip of task items would be missing the task item marker</li>
  <li>Fix: completions broken on Windows</li>
  <li>Fix: document format erroneously creates column spans for some tables.</li>
  <li>Fix: diagnostics/531, line painter provider passed line number &gt; document line count.</li>
  <li>Fix: diagnostics/498, highlight in preview causing exception</li>
  <li>Fix: diagnostics/497, flexmark-java lib erroneous assert failure</li>
  <li>Fix: #447, Exported HTML has unexpected CSS and JS URLs</li>
  <li>Fix: #445, there should no be default language injection in bare code chunks</li>
  <li>Add: handling of optional quotes for jekyll include tags. Either single <code>'</code> or double <code>&quot;</code>
    quotes will be ignored if the file name is wrapped in them.</li>
  <li>Fix: API break with version 2016.2.3 by using EditorModificationUtil methods missing from that
    version.</li>
  <li>Fix: #444, Markdown Navigator 2.3.7 breaks paste of github checkout url</li>
  <li>Fix: #441, false positive typo annotation in header, caused by using IdentifierSplitter
    instead of TextSplitter to handle elements that can have references.</li>
  <li>Fix: #442, Image Paste in Windows always pastes absolute <a href="file://">file://</a> regardless of selection</li>
  <li>Add: Insert table column on right actions and changed description of previous action to insert
    table column on left.</li>
  <li>Fix: exception when exporting PDF or Copy HTML Mime</li>
  <li>Fix: #440, Auto links should not appear in fenced code</li>
  <li>Add: #411, Network drives links are not resolved correctly, URI links outside of project now
    error highlighted if the file does not exist</li>
  <li>Add: #433, Support external links for the Link Map (eg. JIRA link), Reference to Link Map to
    allow creating automatic reference URLs from Reference IDs</li>
</ul>
<h3 id="237---bug-fix-release">2.3.7 - Bug Fix Release</h3>
<ul>
  <li>Fix: parser erroneously processing escape <code>\</code> is encountered in fenced code and causing a
    parsing exception.</li>
</ul>
<h3 id="236---bug-fix--enhancement-release">2.3.6 - Bug Fix &amp; Enhancement Release</h3>
<ul>
  <li>Fix: intermittent index out of bounds exception if document is edited after parsing but before
    AST is built.</li>
  <li>Fix: #438, Markdown Syntax Change looses TOC element in source</li>
  <li>Add: annotation to detect when list syntax is set to GitHub</li>
  <li>Fix: #432, Add a way to disable the startup notification</li>
  <li>Fix: #436, Header link results in bad Table of Contents entry formatting</li>
  <li>Fix: #411, Network drives links are not resolved correctly, for <code>file://</code> which is outside the
    project and any module directory structure.</li>
  <li>Fix: NPE in settings under rare conditions</li>
  <li>Fix: assertion failure in settings under rare timing conditions</li>
  <li>Fix: paste NPE when pasting into link with empty address</li>
  <li>Fix: drag/drop without copy modifier of image files uses last non-image link format instead of
    last used image link format.</li>
  <li>Fix: diagnostic id:208, invalid virtual file in line painter</li>
  <li>Add: option to break definition list on two or more blank lines</li>
  <li>Fix: #428, Lack of encoding declaration when exporting in html</li>
  <li>Add: Global page zoom for JavaFX preview in application settings so that project preview zoom
    does not need to be changed when project is opened on a machine with different HIDPI. Now can
    leave project zoom to 1.00 and change global zoom to desired value.</li>
  <li>Fix: #426, Cannot add images from clipboard or drag and drop under Windows</li>
  <li>Fix: Setext heading to not show heading id on marker line</li>
  <li>Add: #425, Add Heading anchor ID display in editor</li>
  <li>Fix: #424, NoClassDefFoundError in WS 2017.1</li>
  <li>Fix: #421, NoSuchFieldError on startup after upgrading plugin on IDEs version 2016.1</li>
  <li>Fix: image link from non-wiki page to image in wiki would show as unresolved by annotator when
    it was resolved by line marker and preview.</li>
</ul>
<h3 id="235---bug-fix--enhancement-release">2.3.5 - Bug Fix &amp; Enhancement Release</h3>
<ul>
  <li>Fix: #420, java.lang.IllegalStateException: node.treeNext must not be null.</li>
  <li>Fix: do not un-escape HTML entities in HTML, let the browser handle that.</li>
  <li>Fix: #419, Bread crumbs broken when running in 2017.1</li>
  <li>Fix: licensed features highlight now full balloon notification.</li>
  <li>Fix: detection when containing file and target file of a link are not under the same VCS root
    when the containing file is in a sub-directory of target VCS root but has its own root.</li>
  <li>Fix: #416, NPE version 2.3.4 (w/license)</li>
  <li>Fix: #415, Setting default right margin in code style markdown settings disables wrapping</li>
  <li>Fix: #414, Exception when starting IDEA</li>
  <li>Fix: do not hide wrap on typing and table auto format buttons even when these are disabled.</li>
  <li>Fix: drag/drop image files should only show copy dialog if no drop action information or it is
    a drop copy action</li>
  <li>Add: plugin exception reporting to <code>vladsch.com</code> without effort.</li>
  <li>Fix: wiki to main repo links would not resolve. Erroneously treated two vcs repos as separate.</li>
  <li>Fix: clipboard mime text/html now has higher priority than file list and image on the
    clipboard.</li>
  <li>Add: operation options for non-image drop/paste file based on caret location</li>
  <li>Add: <code>Copy Modified Image to Clipboard</code> in Copy/Paste Image Dialog to replace clipboard image
    contents with modified image, can use it to replace image on clipboard then Cancel dialog to
    not modify the Markdown document but still have the modified image on the clipboard.</li>
  <li>Add: Copy/Modify Image intention that will open the Image Copy/Paste Dialog for the image
    content of a link element at caret position. Works with local files and URLs. Get the option
    to change directory, file name and modify the image.</li>
  <li>Fix: <code>http://</code> and <code>https://</code> addresses to project files would be ignored due to a typo in the
    code.</li>
  <li>Fix: update to flexmark-java 18.2, HTML to Markdown hang fix and MS-Word and MS-Excel HTML
    quirks handling fixed.</li>
  <li>Fix: link resolution logic to work for multi-vcs-root projects and modules not under project
    root.</li>
  <li>Fix: update to flexmark-java 18.1, HTML to Markdown adds space after empty list items.</li>
  <li>Add: Markdown application settings for:
    <ul>
      <li><code>Use clipboard text/html content when available</code> disabled by default, enabling it will allow
        pasting text/html when available</li>
      <li><code>Convert HTML content to Markdown</code> enabled by default, disabling will paste text/html
        content without conversion to Markdown</li>
    </ul>
  </li>
  <li>Add: <code>Delete empty list items</code> intention on lists to delete all empty list items</li>
  <li>Fix: HTML to Markdown converter to not ignore text in lists which is not included in list item
    but instead to put this text into a new list item.</li>
  <li>Add: aside extension which uses leading pipe <code>|</code> to mark an aside block just like block quote
    uses leading greater than <code>&gt;</code> to mark a block quote element</li>
  <li>Add: pasting file list into markdown document inserts links the same as dropping files with
    copy action.</li>
  <li>Add: confirmation dialog when original markdown file is going to be overwritten with
    transformed content when pasting file list or drag and dropping files.</li>
  <li>Fix: absolute <code>http://..../wiki</code> link to wiki home page would to resolve as a file reference.</li>
  <li>Fix: drag/drop wiki page files would ignore link address format and always insert page
    relative link.</li>
  <li>Fix: style auto wrapping when caret at end of word that is at end of file without trailing
    EOL.</li>
  <li>Add: future API for drag/drop handling code to eliminate the need for replacing editor
    drag/drop handler.</li>
  <li>Add: highlight selection in preview, <code>Show source selection in preview</code> enabled by default.
    Wraps selection in <code>&lt;span&gt;</code> with <code>selection-highlight</code> class.</li>
  <li>Add: #399, Highlight search results in preview, <code>Show source search highlights in preview</code>
    enabled by default. Wraps highlights in <code>&lt;span&gt;</code> with <code>search-highlight</code> class.</li>
  <li>Fix: text drag/drop not working because of MarkdownPasteHandler</li>
  <li>Add: option to enable drag/drop handler replacement to allow &quot;Copy&quot; extended file drag/drop
    action at the expense of text drag/drop. Disabled by default in settings <code>Languages &amp; Frameworks &gt; Markdown</code></li>
  <li>Fix: loosen/tighten list action to not mark a list as loose when blank line precedes the first
    list item.</li>
  <li>Fix: #404, Conversion from CommonMark or FixedIndent to GitHub does not properly indent code
    blocks in list items</li>
  <li>Fix: #403, Indented code in list items not indented enough with GitHub list parser option</li>
  <li>Change: link color in Preview and Editor Colors to match new GitHub colors</li>
  <li>Fix: #400, Better code color consistency needed. Now same as Fenced Code/Verbatim. Also change
    copy Markdown as HTML formatted text and PDF export to align inline code color with indented
    and fenced code.</li>
  <li>Fix: #398, Poor alignment between source and preview when using &quot;Sync source to preview&quot;. Now
    there is an option to vertically align synchronized position in Preview Settings, selected by
    default.</li>
  <li>Fix: #402, PDF Export action fails silently if no text is selected in document instead of
    exporting the full document.</li>
</ul>
````````````````````````````````


```````````````````````````````` example(Issue performance: 2) options(TIMED, fast-render)
## Markdown Navigator

[TOC levels=3,4]: # "Version History"

### Version History
- [2.7.0.8 - Bug Fix & Enhancement Release](#2708---bug-fix--enhancement-release)
- [2.7.0 - Bug Fix & Enhancement Release](#270---bug-fix--enhancement-release)
- [2.6.0 - Bug Fix & Enhancement Release](#260---bug-fix--enhancement-release)
- [2.5.4 - Bug Fix Release](#254---bug-fix-release)
- [2.5.2 - Bug Fix & Enhancement Release](#252---bug-fix--enhancement-release)
- [2.4.0 - Bug Fix & Enhancement Release](#240---bug-fix--enhancement-release)
- [2.3.8 - Bug Fix Release](#238---bug-fix-release)
- [2.3.7 - Bug Fix Release](#237---bug-fix-release)
- [2.3.6 - Bug Fix & Enhancement Release](#236---bug-fix--enhancement-release)
- [2.3.5 - Bug Fix & Enhancement Release](#235---bug-fix--enhancement-release)


### 2.7.0.8 - Bug Fix & Enhancement Release

* [ ] Fix: copy fixed utils from Arduino Support plugin.
* Fix: nasty bug introducing typing delay with preview enabled.
* in the process
* Fix: diagnostic-2012, kotlin NPE.
* Fix: Paste Image: old crop settings out of bounds for new image caused exception
* Fix: for #651, Drop image with dialog issues
  * Spaces in file name were url encoded
  * Copy dragging a file leaves its original directory instead of setting it to the closest
        or best guess based on the destination file. Should be the same as if the image was
        pasted into the file. If the destination directory is the same as the source then a new
        name should be generated to uniquify it.
* Add: in Paste/Modify Image if dragging the highlight selection without having highlight
  enabled or no border, inner nor outer fill enabled, will enable highlight and border to
  provide feedback otherwise it is confusing.
  * Add: drag selection can be used for cropping if image tab is selected and `Use mouse
    selection only for highlight` is not selected.
  * Fix: only copy image to transparent if Image tab is selected. The rest leave as is.
  * Add: restart notification if changing full highlight combinations
* Add: Image Paste highlight option to annotate an area of the image.
* Add: option to disable synthetic highlight attributes.
  * Fix: #648, too many element types registered, Option for full syntax highlighter
    combinations, disabling creates minimal set to reduce the limit of short index for these in
    the IDE.
* Add: Code Style option to treat `Hard Wraps` parser option as if soft-wraps are enabled.
* Add: Main option to force soft-wraps mode for file when opening if `Hard Wraps` are enabled

### 2.7.0 - Bug Fix & Enhancement Release

* Fix: jekyll parser option notification would not use the file's scope based profile.
* Fix: bump up dependencies to newer versions this
* Fix: #647, md to html link conversion not working for exported files on Windows
* Fix: exported files without stylesheet should not decorate link with resolved status
      class.
* Fix: `{% include ` link resolution does not work without a VCS root.
* Fix: Jekyll `{% include "" %}` completions would not work unless there was an `.html`
  extension between the strings.
* Fix: update for 2019.1 eap
* Fix: intentions missing groupKey were not showing up or being run
* Fix: make hex text dialog a licensed feature instead of dev feature.
* Fix: diagnostic/1931, possible fix for intermittent based sequence index out of bounds fix
* Fix: catch exception when github tasks request fails
* Fix: settings for HTML paste are not dependent on paste handler override.
* Fix: when ask on paste for html paste options would cause subsequent undo to fail due to
  temporary file modification.
* Fix: reverse the order of split editor configuration for "Show editor and preview" and "Show
  editor only"
* Fix: for API change in 2019.1 EAP.
* Add: `Simple structure view` option to display only heading hierarchy in the structure view
* Fix: optimize parser PSI generation by using hash map for type to factory function
* Fix: diagnostic/1849, ClassCastException: LeafPsiElement cannot be cast to
  MultiMarkdownLinkElement
* Fix: image reference links to references with wrong file type or not raw would not register as
  references to the reference definition. Added `getExactReference()` to return reference only
  if it is an exact match, `getReference()` not matches strictly by id since it is used for
  navigation and usages.
* Add: `Use Style Attribute` option to HTML Export settings. When enabled will apply stylesheet
  via `style` attribute of each element as done for `Copy Markdown as HTML mime content`.

  **NOTE:** stylesheet is expected to be in the same format as `COPY_HTML_MIME` stylesheet. See
  [Copy Markdown to HTML formatted Text Profile](https://github.com/vsch/idea-multimarkdown/wiki/Rendering-Profiles-Settings#copy-markdown-to-html-formatted-text-profile)

  **NOTE:** if `No Stylesheets, No Scripts` is selected then only styles explicitly defined by the
  profile will be used. If this option is not selected then `COPY_HTML_MIME` profile stylesheet
  will be used or if the `COPY_HTML_MIME` profile is not defined then the
  [default stylesheet for `COPY_HTML_MIME`][html_mime_default.css] will be used.
* Fix: move annotations for `Reference Links` to inspections
* Fix: move annotations for `References` to inspections
* Fix: move annotations for `Emoji` to inspections
* Fix: move annotations for `Anchor` to inspections
* Fix: move annotations for `Headings` to inspections
* Fix: move annotations for `Tables` to inspections and add quick fix for column spans
* Fix: move annotations for `List Items` and `Possible list items` to inspections
* Add: Html Generation option to not wrap paragraphs in `<p>` and use `<br>` between paragraphs
  instead. Useful for HTML exported files for use in Swing panels
* Add: Html Export target file path options to add to target directory. Useful if need to
  flatten directory structure of markdown files to a single directory for exported HTML
  * Add path relative to project
  * Add path relative to parent directory
  * Add file name only
* Add: same file path type options as target path for export image copied file path.
* Fix: IDE hangs when copying text containing the macro references which contained recursive
  macros.
* Fix: document format to ensure one blank line after macro definition
* Fix: `<>` wrapped auto links would prevent following bare auto-links from being parsed.
* Add: all elements intention to select element if intention displays dialog to give user
  feedback on which element is being used.
* Fix: do not highlight auto links as errors if remote link validation is disabled
* Fix: remote link annotation disabled by custom URI scheme handler
* Fix: #640, java.lang.NullPointerException with HtmlPasteOptionsForm
* Add: Parser
  [Macros Extension](https://github.com/vsch/idea-multimarkdown/wiki/Macros-Extension)
* Fix: list item indent/unindent could insert `&nbsp;` inserted/removed during wrapping but do
  not perform wrapping, causing the `&nbsp;` to be left in the text.
* Add: intention for auto link to explicit link conversion and vice-versa
* Fix: #605, Support for system specific protocol handlers. Pass through custom protocols to IDE
  browser launcher.
* Fix: to not highlight external URL links which consist of only the protocol at the end of the
  line.
* Add: color scheme export to save only non-synthetic attributes: `Intellij IDEA color scheme,
  reduced markdown (.icls)`
* Add: validation to auto-link remote url and completion/validation to anchor ref
* Add: url based parser settings for remote link markdown parsing. For now hardcoded for GitHub,
  GitLab and legacy GitBook compatibility. New GitBook anchor links not supported yet.
* Fix: diagnostic/1827, Empty collection can't be reduced.
* Fix: broken remote URL links to markdown files validation and anchor ref completions
* Add: quick fix intention for fixing unresolved anchor refs when a match can be made by
  ignoring case and removing duplicated `-`
* Fix: GitHub heading ids do not convert non-ascii to lowercase.
  * Add: `Heading ids lowercase non-ascii text`, selected for:
    * GitLab profile
    * GitBook profile
    * CommonMark profile
* Fix: formatter for extension
* Fix: invalid anchor refs not annotated for local links (broken by remote link validation)
* Add: intention for unresolved link addresses starting with `www.` to prefix with `https://`
  and `http://`. If remote link validation is enabled then only prefix which results in valid
  link address will be shown in the intention. If the resulting address reports as permanently
  moved then also add the destination location to intentions.
* Add: handling of HTTP:301 for remote content and intention to update link address
* Fix: for remote content cache only store list of anchors instead of content, more compact and
  provides the needed data
* Fix: remove directories from link completions to reduce noise in completions
* Fix: remote image links showed as unresolved, now IOExceptions during fetching treated as
  resolved.
* Fix: remove links returning image data now treated as resolved.
* Fix: #637, Links from main project repository to files in a sub-directory repository show
  unresolved
* Add: unresolved remote link address annotation error.
* Add: in settings total remote link count and memory use for remote content.
* Fix: only cache remote content when it is needed for anchor ref validation. For remote link
  validation only store the fact that it exists.
* Add: remote link content cache to use for validating remote content links and anchor refs
* Add: option to enable validation of remote links (annotates unresolved link if server returns
  error)
* Fix: remove anchor ref error annotation for links which do not resolve to a project file or do
  not exist if validating remote link anchor refs
* Add: error annotation for links to HTML files in project with anchor refs which do not link to
  `a` or `h1` through `h6` html tags with `name` or `id` attribute given by anchor ref
* Add: anchor link completion for links to HTML files in project to `a` or `h1` through `h6`
  html tags with `name` or `id` attribute giving the anchor ref
* Add: anchor link completion on external URLs which do not resolve to a project file.
  * Special handling if file extension matches a Markdown Language extension, will download the
    markdown file and will render it as HTML to extract anchor definitions
  * Special handling for GitHub (ones starting with http:// or https:// followed by github.com)
    * markdown files: If the link is to a `blob` type then will use `raw` type URL to get
      Markdown so it can be correctly rendered as HTML to extract anchor definitions.
    * html content:
      * remove `user-content-` prefix from anchor refs (GitHub adds these automatically)
      * remove `[0-9a-fA-F]{32}-[0-9a-fA-F]{40}` looking anchor ids
  * Special handling for GitLab (ones starting with http:// or https:// followed by gitlab.com)
    * markdown files: If the link is to a `blob` type then will use `raw` type URL to get
      Markdown so it can be correctly rendered as HTML to extract anchor definitions.
    * html content:
      * remove `[0-9a-fA-F]{32}-[0-9a-fA-F]{40}` looking anchor ids

### 2.6.0 - Bug Fix & Enhancement Release

* Fix: change definition indent maximum to 4, beyond which it converts the text to indented
  code.
* Fix: definition formatting would not add indent removal causing contained block quote prefix
  to be doubled
* Add: option to remove prefixes when joining lines
* Fix: move code style `Continuation Lines` indent into `Text` code style panel.
* Add: `Left Justified` option to ordered list style options
* Fix: force code style parser settings to CommonMark
* Fix: change code style sample parsing flags to modify parser flags to allow formatting all
  sample elements.
* Fix: settings "Manage..." exception in DataGrip without an open project. Now uses user home
  dir as default directory without an open project.
* Fix: fenced code and indented code indented with tabs would not minimize indent during
  formatting.
* Fix: HTML to markdown conversion
  * Fix: #268, Pipe characters are not escaped in Table (FlexmarkHtmlParser)
    * Fix: escape pipe characters in text (to avoid accidental use as table or other markup)
      when not inline code nor fenced code
    * Fix: escape back ticks when inside code
    * Fix: disable escaping of `[]` when inside code
      * Fix: disable escaping of `\` when inside code
    * Fix: replace non-break space with space when inside code
* Fix: `FlexmarkHtmlParser.BR_AS_EXTRA_BLANK_LINES` now adds `<br />` followed by blank line
* Fix: JavaFx Browser initialization bug introduced by 2016.3 compatibility fix.
* Add: "Paste HTML" button to HTML Paste Options dialog to paste HTML without conversion to
  markdown.
* Fix: clean up code style formatting and preview of style changes
  * style changes are now highlighted to properly reflect the last change, not whole document
    reformat changes
  * prefix changes would not be applied (or formatted) if text wrap for paragraphs was disabled,
    affected list items, definitions, block quotes
  * block quote prefix (compact with space) always inserted space after firs `>` instead of last
    `>`
  * TOC with html language option would not update preview
  * Remove unused list formatting options
* Add: link text suggestion for user label `@username` for GitHub user links of the form:
  `https://github.com/username`
* Change: remove runtime null assertions for function arguments
* Fix: scroll sync not working in 2018.3 EAP
* Fix: change lambdas to functions to have `arguments` available (causing exception in JetBrains
  Open JDK 1.8.0_152-release-1293-b10 x86_64
* Add: extra diagnostic information for Swing Browser `EmptyStackException`
* Fix: diagnostic/1759, kotlin arguments erroneously defined as not nullable.
* Fix: 2016.3 compatibility
* Fix: markdown code style settings to be created from file when available to allow IDE scope
  based resolution for markdown files to work properly.
* Add: HTML Settings option `Add <!DOCTYPE html>` to enable/disable having doc type at top of
  document. Required by Katex to work.
* Fix: update emoji icons
* Fix: GitLab math blocks to display as blocks instead of inlines
* Fix: disable tab overrides if there is a selection in the editor or multiple carets
* Change: split math and chart options from GitLab so that each can be selected without GitLab
  extensions if GitLab extensions are not selected.
* Add:
  [GitLab Flavoured Markdown](https://gitlab.com/gitlab-org/gitlab-ce/blob/master/doc/user/markdown.md)
  parsing and rendering functionality
  * Math inline using ```$``$``` and fenced code blocks with info of `math` using
    [Katex](https://github.com/Khan/KaTeX)
  * Chart fenced code blocks with info of `mermaid` using
    [Mermaid](https://github.com/knsv/mermaid)
  * Inserted text (underlined) via `{+text+}` or `[+text+]`
  * Deleted text (strike through) via `{-text-}` or `[-text-]`
  * Multiline block quotes using `>>>` at start of line to mark block start and `<<<` at start
    of line to mark block end.
    [GFM: Multiline Blockquote](https://gitlab.com/gitlab-org/gitlab-ce/blob/master/doc/user/markdown.md#multiline-blockquote)
  * Video image link rendering
    [GFM: Videos](https://gitlab.com/gitlab-org/gitlab-ce/blob/master/doc/user/markdown.md#videos)
* Fix: disable tab override when popup is showing to allow tab to be used for completions.
* Fix: with CommonMark list type bullet list item after ordered list and vice versa, would allow
  indentation in error.
* Fix: #469, pressing tab in a empty list item should indent the list
  * added option "List item indentation" under "Tab/Backtab Overrides" which enables using
    tab/backtab to change list item indentation
* Fix: #541, using tab to move to the next table cell
  * added option "Table Navigation" under "Tab/Backtab Overrides" which enables using
    tab/backtab to navigate table cells
* Fix: GitHub issue completions for 2018.3 EAP
* Fix: #577, Add feature to move table columns, added Move table column left/right
* Fix: remove line/search highlights from plain HTML preview
* Fix: remove auto links from spellchecking elements
* Fix: partial fix for list item needs blank line annotation for list in block quotes
* Fix: #610, hope can have more paste option, add HTML paste options to suppress conversion of
  inline and heading elements
* Fix: #623, Directory linking occasionally broken
* Fix: compatibility with versions 2017.
* Fix: possibly fix diagnostic pycharm exception on migrate code settings
* Change: update deprecated API usage
* Change: reduce number of highlighter overlay attributes to 3609 from 8497
* Change: update source code for 2018.2 API changes.
* Fix: #621, plugin oom in goland, potential memory leak
* Fix: #615, Plugin can't initialize in multi-user setup, now temp directory
  `.markdownNavigator` is created under the user's home directory
* Fix: #620, Check keyboard shortcut changes wrong list item
* Fix: #619, Create unchecked list item when pressing enter next to a line with a checked list
  item
* Fix: reference paste to add line references to link text in the form: `: Line #` or `: Lines
  #-#`
* Fix: diagnostic/1575, `node.treeNext must not be null`
* Fix: wrong range calculation for #612 fix
* Fix: #611, Backspace in empty check mark box deletes check mark
* Fix: #612, Code folding eats one character for underline headers
* Add: HTML comment folding and options
* Fix: diagnostic, parent already disposed

### 2.5.4 - Bug Fix Release

* Fix: High Sierra JavaFx issue, implement JavaFX initialization as per Markdown Support.
* Add: Document Translation using Yandex.Translate and flexmark-java translation helper API,
  configured in debug settings. This is a temporary implementation which will be moved to its
  own plugin in the future.
* Fix: diagnostic/NPE on JavaFX WebView save editor state.
* Add: conversion of emoji from HTML mime copied back from Apple mail.
* Fix: paste image file name from link name would not be URI decoded.
* Add: folding of list items longer than one line of text
* Fix: #590, "Create directories and folder" does only create directory on first hit.
* Fix: #591, uncomment does not remove leading space, removed padding spaces from comment
  prefix/suffix.
* Fix: shorten toolbar by moving more rare actions to popup menus: list, table, misc and copy
* Fix: wrap on typing `>` to insert block quote level into existing block quote or using
  backspace to remove a block quote level
* Fix: wrap on typing backspace in footnote definition would replicate the first line prefix on
  continuation lines
* Fix: inserting an EOL in a list item before text matching bullet list marker or numbered would
  double the list marker on the resulting line
* Add: option for escape/unescape all numbered list lead-in of `number.` when wrapping text.
* Fix: diagnostic java.lang.RuntimeException in ImageUtils.getImageFromTransferable when pasting
  an image
* Fix: java.lang.Throwable: Invalid file: DB VirtualFile: table when caused sometimes by file
  watcher requesting markdown files be re-parsed
* Fix: diagnostic java.lang.IllegalArgumentException: Argument for @NotNull parameter 'project'
* Fix: accept license code when extra spaces are added before EOL in e-mail.
* Fix: diagnostic/ prevSibling should not be null.
* Add: option to disable gutter margin for markdown documents
* Add: option to disable line markers to main settings panel.
* Fix: file types by specific extension completion did not work
* Fix: link resolution would not find files with nested extensions like `blade.php`
* Change: toggle inline attribute when the caret is at the end of a non-space span now restores
  the caret position when applying the style instead of end of the span, inside the markers.
  More natural when inlining a word to continue typing.
* Fix: #575, Broken Spell Checker, spell checking not working on paragraph text for basic
  plugin.
* Fix: JavaFX detection with Android Studio 3.0
* Fix: #434, Spellchecker context menu is duplicated
* Add: `Use rename for spelling error intention` to allow turning off `Rename to:` spellchecking
  intention and use the regular `Change to:` intention.
* Fix: remove old storage macros

### 2.5.2 - Bug Fix & Enhancement Release

* Fix: newer API use which causes exceptions in 2017.3 IDE versions.
* Add: link and image paste/drop options to exported/imported settings management
* Fix: preview window would show links resolved after link map transformation to `http://` as
  `local only`.
* Fix: #567, '_'s are replaced by '-'s in TOC, GitHub now preserves `_` in heading anchor refs
* Fix: paste handler using 2018 api `getSelectedEditor()`
* Fix: #564, Some problems with "Copy / Drop Image" dialog. Modify image directory history drop
  down to include, in order of priority:
  * last used image directory of the current file
  * image directories of images in the current file, ordered by count of occurrence
  * image directories in the current project, ordered by count of occurrence
* Fix: absolute `http://` link from main repo to wiki page which is located in a sub-directory
  would show as unresolved
* Add: help topics for all settings panels
* Fix: improve spelling error alignment in text with many embedded inline markers
* Fix: compatibility with IDE versions 2016.3, limit since version to 163.15529
* Fix: COPY_HTML_MIME and PDF stylesheets now combine user provided attributes with defaults so
  only difference has to be added to custom CSS.
* Add: emoji images to COPY_HTML_MIME and PDF stylesheets so emoji display properly.
* Fix: wiki page file name rename refactoring. Broken since 2.4.0 release
* Fix: manually exporting a file from the toolbar now treats this as if `Export on Save` was set
  for the file.
* Fix: update GitHub wiki home link resolution for image links. Changed recently by GitHub
* Fix: trailing spaces filter behavior changed, postponed trailing spaces would all be deleted.
* Fix: reverse fix for "WebView should be available by now", was causing deadlock if
  Accessibility features were enabled.
* Fix: JavaFX preview was using project profile parser settings, not scope based rendering
  profile parser settings.
* Fix: Formatting default table column alignment when no alignment marker is specified, header
  columns are centered, body columns are left aligned.
* Add: Export to treat emoji images as image linked files.
* Fix: NoSuchMethodError for IDE versions < 2018
* Fix: image paste/modify dialog to not add _# suffix to file name if pasting on image target
  ref and on paste action is "Replace file content" for link-ref targeting an image, since the
  name should be the same as the link-ref in order to replace file content. Changing the name
  will save it under a new file and change the link-ref.
* Fix: remove old project settings handling and replace with IDE provided method. Old settings
  copied to default project settings on first plugin initialization after upgrade. Now default
  project settings support having defaults for Rendering > Profiles
* Add: Format options for Attributes:
  * space inside braces: no change, add, remove
  * space around attribute equal sign: no change, add, remove
  * attribute value quotes:
    * No Change
    * None, double, single
    * None, single, double
    * Double, single
    * Double quotes
    * Single, double
    * Single quotes
* Fix: table formatting would disable wrap on typing unnecessarily because it failed to properly
  detect table at caret offset.
* Add Table Caption formatting options:
  * Caption: no change, always add, remove if empty, always remove;
  * Caption space: no change, space around text, trim spaces check box.
* Add: #556, Default editor layout > Use last selected layout, option to have layout follow last
  editor layout change action.
* Fix: typographic `'` breaking words for spell checker generates erroneous spelling errors.
* Fix: spell checking now done across inline markup. Error underline and Change to: intention do
  not work well because of interspersed markup messing up offsets but at least spelling errors
  will be highlighted. For example `do**sn't**` will now show a spelling error because the
  effective text is `dosn't`.
* Add: history to CSS settings URI text box.
* Fix: default completion for explicit `http://` absolute links to wiki pages uses the extension
* Fix: `file://` links to wiki pages with anchor refs showed as "Only resolving locally" in the
  preview, all `file://` links show as resolving only locally.
* Fix: Admonition extension would be disabled if Attributes extension was not enabled.
* Add: Admonition parser extension.
  **[Admonition](https://github.com/vsch/flexmark-java/wiki/Admonition-Extension)**, Parser
  extension based on [Admonition Extension, Material for MkDocs] to create block-styled side
  content.
* Fix: JavaFX WebView debug page reload in Not on FX application thread exception.
* Fix: remove the "canDebug" field and replace with dynamic value.
* Fix: remove all break points on connection shutdown.
* Fix: JavaFX debugger core dumping if turning off debugging while it is paused.
* Fix: Project Default settings not being copied to new projects
* Fix: intermittent preview element highlight stopped working until page refresh
* Fix: pasting a page relative URL would be mistaken for FQN reference and always paste a link
  instead of text
* Fix: renaming rendering profile would not be saved properly.
* Fix: copy action on rendering profiles caused exception
* Add: all console commands work with Chrome dev tools.
* Fix: Chrome dev tools console evals and console logging from JavFX WebView scripts.
* Fix: #561, Scroll sync and highlight preview element broken in EAP 2.4.0.44
* Remove: FirebugLite script option for JavaFX. It never worked for debugging and Chrome Dev
  Tools work really well with JavaFX WebView.
* Add: "Toggle Editor Split Orientation" action to toggle Vertical/Horizontal split orientation
* Add: drag/drop file inside inline, fenced or indented code to insert file name.
* Add: dropping file after end of line with virtual spaces enabled, will insert spaces to fill
  virtual spaces.
* Fix: Adding explicit attribute to heading did not put space between text and attributes
  element.
* Add: file/ref anchor target search/explore intention on unresolved link anchor ref.
  * Fix: launching on an anchor and cancelling, does not show intention until file is edited.
  * Add: do a partial match for anchor when no anchors match exactly.
  * Add: filter text box to filter anchor list (show all partials, the rest hidden) otherwise
    too many in the list.
* Fix: Github collapse headers script not working in 2018.1
* Fix: intermittent position not highlighting in preview.
* Fix: Drag/Drop copy files does not save link drop options.Always resets or gets them wrong.
* Add: buttons for link and image drop/paste options in markdown settings
* Add: Updated emoji to include full set of GitHub supported ones
  * Add: option to select which shortcuts to recognize:
    * Emoji Cheat Sheet
    * GitHub
    * Both: Emoji Cheat Sheet, GitHub (in order of preference for URL generation in HTML)
    * Both: GitHub, Emoji Cheat Sheet (in order of preference for URL generation in HTML)
  * Add: option to select what type of image to use:
    * Images: image files only
    * Unicode and Images: use Unicode characters when available, image file otherwise
    * Unicode Only: only use unicode characters, don't recognize shortcuts which have no unicode
      equivalent.
  * Add: option to preview settings to replace Unicode emoji characters which have a
    corresponding image file with the image file. This allows preview browser to display Unicode
    emoji for which the browser would display unrecognized character symbol instead.
* Update to flexmark-java-0.32.2
  * Fix: java-flexmark API changes
  * Fix: java-flexmark Attributes processing changes
  * Add: Parser option for Attributes assignment to text
  * Add: Parser option for Emoji Shortcut Type, Emoji Image Type
  * Add: Preview option for replacing Emoji unicode with image
* Add: settings option to allow directories as link targets. Allows directories to be used in
  links. This functionality affects operation to completions, annotations, drag/drop link
  creation and navigation.
* Add: Drag/Drop link creation in Wiki should have wiki option for link format.
* Fix: `http://` link to wiki home without the file shows as unresolved by annotator
* Fix: change explicit to wiki not showing if link format is http:// or https:// absolute
* Fix: when converting explicit to wiki don't generate text & page ref if the explicit link text
  is the same as the file part of the target: `[Page-Ref](Page-Ref.md)` -> `[[Page Ref]]`, not
  `[[Page-Ref|Page Ref]]`
* Fix: Allow links to directories under the repo not to show them as unresolved. Create ref to
  directory object if it is under VCS
* Fix: drag/drop directories to create a link to the directory
* Fix: document format would remove table caption element
* Add: Query user for new id on explicit id to heading intention to save a step of rename
  refactoring it.
* Add: if a heading has explicit id attributes, rename refactoring for it is disabled since the
  id is not part of attributes.
* Add parser option to parse inline HTML for `<a id="...">` for anchor targets
* Fix abbreviation definition with empty abbreviation would cause an exception
* Add Option to enable/disable use of image URI query serial, used to force preview update of
  image when the image file changes. Disabled by default to reduce java image caching memory
  issues.
* Fix: custom paste handling into scratch files was not handled in CLion, possibly other
  non-Java IDEs.
* Fix: #554, Settings, Import and Copy from project do not get applied until corresponding
  settings pane is viewed. The settings would be changed but not applied until the settings pane
  was clicked on first.
* Fix: diagnostic/1159, Inserting table rows could cause an index out bounds exception
* Fix: files not under VCS root would show no completions for relative addressing, only had
  completions for `file://` format completions.
* Add: recall of the last imported settings file to make it easier to reset settings to a known
  value.
* Add: markdown Application settings to exported and imported settings.
* Fix: disable local only status for links and annotation when the link is to the file itself.
* Add: allow source/preview synchronization and search/selection highlighting in basic version.
* Fix: diagnostic/1140, NPE in flexmark-java core node renderer.
* Fix: diagnostic/1141, null editor causes exception in toolbar button test.
* Add: #549, Add settings management functionality. Now in main settings panel there is a
  "Manage..." button in top-right corner, clicking it pops up a menu with the following options:
  * `Copy to Project Defaults`
  * `Copy from Project Defaults`
  * `Export Settings`
  * `Import Settings`
  * `Reset Settings` to reset settings to default. Project defaults, current project settings
    and markdown navigator application settings.

  These actions copy from current unsaved project settings and to current unsaved project
  settings therefore you can modify settings, copy to project defaults (or export) and then
  cancel, result will be project defaults (or exported settings) having modified settings while
  project settings being those before modification.

  If you copy from defaults or import a file followed by `Cancel` then no settings will be
  modified.
* Fix: #548, When "Auto-scroll to source" is enabled in project view, markdown navigator editor
  steals focus when moving through project view with keyboard arrows.
* Fix: #542, Typographical Error in PHPStorm Preferences > Editor > Code Style > Markdown
* Add: option in settings to enable editor paste handler registration so that paste handler is
  enabled by default. Because the IDE has a lot of formatter exceptions on paste which get
  erroneously attributed to the plugin when it delegates paste action to previous handler. Now a
  notification balloon will inform of the IDE exception and offer a link to disable paste
  handler customization.
* Fix: #546, Panel is guaranteed to be not null Regression.
* Fix: #260, Add horizontal split editor option to allow preview below the text editor. Added
  option in Languages & Frameworks > Markdown: `Vertical Text/Preview Split`, default not
  selected.
* Fix: #524, Dedent shortcut not working properly.
* Fix: #539, Big local images (e.g. .gif) referred to in an open .md file get locked and cause
  merge conflicts and issues on checkout. Now swing implements disable GIF images option.
* Fix: #512, Add keyboard shortcut to `Cycle between Preview only and Editor only`. Instead
  added application setting to select text/split or text/preview toggle for the toggle editor
  layout action.
* Fix: #511, `Cycle split layout` shortcut stop working when `Preview Only` is selected.
* Fix: #527, How to use *italics* instead of _italics_ when pressing `Ctrl+I`. Option added to
  Languages & Frameworks > Markdown: `Use asterisks (*) for italic text`, enabled by default.
  When enabled italic action will use only asterisks for as markers.
* Fix: #535, Documentation for link maps and mapping groups. Documentation link added to Link
  Map settings panel.
* Fix: diagnostic/1100, start/end offset on paste beyond end of document
* Fix: clicking on a link with anchor ref by name of element would not scroll element into view
* Add: #391, #anchor tags not working. Added anchors of the form `<a .... attr=anchorId
  ...>...</a>` where `attr` is `id` or `name` to be treated as anchor ref targets. NOTE: the
  first name or id attribute will be treated as the "anchor target" the other as a reference to
  the anchor target. If both have the same string value then renaming one will rename the other.
* Fix: regex error flexmark-java attributes parser which could cause a parsing loop
* Add: parser option to not generate duplicate dashes `-` in heading ids
* Fix: fenced code content erroneously processed GitHub issue marker `#`.
* Fix: #544, Export to PDF greyed out. Editor actions would be disabled if the text editor was
  not visible.
* Add: parser options for
  * **[Attributes](https://github.com/vsch/flexmark-java/wiki/Attributes-Extension)** and
  * **[Enumerated References](https://github.com/vsch/flexmark-java/wiki/Enumerated-References-Extension)**
    parser extensions
  * Add: heading intentions to add/remove explicit id
  * Add: completions for link anchors to id attribute values
  * Add: completions for enumerated references and reference formats
  * Add: formatting options and formatting for Enumerated References
  * Add: error/unused annotations for enumerated reference, enumerated format and attribute id
  * Add: refactoring/navigation for Enumerated Reference format id's, Attribute Id's, Enumerated
    Reference link/text.
* Fix: diagnostic: 1055, sometimes virtual file == null for a PsiFile causing an exception.
* Add: option to add serial query suffix to CSS URI which increments when the css file changes
  (only file:// URI's and document relative URLs are supported.)
* Fix: diagnostic 1030, when bread-crumb provider steps up to file level while looking for
  headings.
* Fix: diagnostic: 1032, sometimes an exception is thrown "AssertionError: Unexpected content
  storage modification"
* Fix: diagnostic 1033, paste handler exception `IllegalStateException: Clipboard is busy`
* Fix: diagnostic 1035, null pointer exception in Swing preview when image tag has no `src`
  attribute.
* Fix: diagnostic 1047, sometimes an IOException is generated if markdown sub-type is requested
  during indexing operation.

### 2.4.0 - Bug Fix & Enhancement Release

* Fix: #517, Invalid tool tip for "Show Breadcrumb text"
* Change: #520, Not working: As you type automation: Double of bold/emphasis markers and remove
  inserted ones if a space is typed. Enable these options in code style, disabled by default.
* Fix: #509, Text with colons is incorrectly interpreted as an invalid emoji shortcut
* Add: #507, How to be sure that HTML auto generated link will have unchanged url. Link format
  option for HTML export: page relative, project relative, http: absolute, file: absolute if
  option `Link to exported HTML` is not selected.
* Add: #466, Indents with 4 spaces instead of 2 as I like. Code style option for indent size
  added sets number of spaces to insert when pressing tab.
* Change: Remove attribute and settings migration from pre 2.3.0 versions.
* Add: nested heading outline collapsing
* Fix: improved HTML to markdown conversion from Apple Mail copied text.
* Fix: don't show emoji completions in link address part ( http: triggers it)
* Fix: abbreviation navigation and refactoring was not implemented
* Fix: line markers generate for leaf elements only, performance improvement
* Fix: swing preview on linux not showing fixed pitch font for code
* Fix: Task list items now require indent at task item marker not item content, to match GitHub
  parsing rules. Indenting to content column treats children as inline code and child list items
  not separated by a blank line as lazy continuation lines.
* Fix: formatter for new task item indentation rules.
* Fix: remove `Replace File Content` option from non-image target ref drop downs in paste/modify
  image dialog, and all link options from copy/drop image dialog and link drop/paste ref options
  dialog.
* Fix: #489, Paste Image does not create file if parent directory does not exist.
* Fix: #484, Open links in preview, not browser. Option added to preview settings to have page
  relative and repo relative links resolve to GitHub files when selected. When not selected they
  resolve to local project files.
* Fix: #486, Multi-line links do not preview correctly, when in `Line` preview element highlight
  mode.
* Fix: #481, Will not allow me to crop beyond 200px, now limits are derived from the image
  dimensions and image operations.
* Fix: Update to latest flexmark-java supporting CommonMark Spec 0.28.
* Fix: TOC entries would exclude typographic characters when "text only" option was used with
  typographic parser extension enabled.
* Fix: HTML to Markdown not adding HTML comment between consecutive lists
* Fix: #479, Multi-line URL images are not converted in PDF export or Copy HTML Mime
* Add: Show "Apply all '...'" intention on any element option to allow showing file level
  intentions to be available on any element. Otherwise only shown on elements which they affect.
* Add: enable image intentions on multi-line URL image links
* Add: Code Folding option in settings for embedded image links
* Add: HTML generation options to convert image links to embedded images, with separate option
  for http:// and https:// image urls.
* Add: base64 embedded image display in Swing Preview browser
* Add: `Base64 Encoded` as a link format for pasted images and dropped files to the Paste Image
  dialog.
* Fix: base64 encode intention would keep path of url if it was present
* Fix: image reference links to references with base64 encoded images would show as unresolved
* Add: intentions to convert images to base64 encoding and vice-versa
* Fix: base64 encoded embedded images did not display in JavaFX preview
* Fix: preview navigation to links with anchor refs and line anchor refs
* Fix: dropping a file in a document appends `null` string to the file name in error.
* Fix: #468, Move (refactoring) of .md files breaks links to sections in same file.
* Fix: reference paste with line ref anchor would always paste page relative format URL
  regardless of the link paste format (set with file copy drop action).
* Fix: diagnostics/713, tree view icon update before `FileManager` has been initialized will to
  return markdown file type (without resolving sub-type).
* Add: Convert markdown to HTML intention for fenced code and indented code blocks.
* Fix: unresolved link references would be rendered in HTML instead of being treated as plain
  text. Broken by `Reference` link map code.
* Fix: paste reference past end of line will insert spaces to caret column before inserting
  link.
* Fix: links from FQN references with spaces did not url encode the link.
* Fix: reference to link conversion for PhpStorm to truncate the reference at the `:` since
  PhpStorm is not able to convert FQN strings with class method names.
* Add: use QualifiedNameProviders to resolve reference to link conversion.
* Add: logic to not convert qualified string to link when caret is inside inline code, fenced
  code or between two back-ticks.
* Fix: HTTP links with anchor refs should not highlight anchor links as unresolved.
* Add: paste of file reference with or without line number converted to paste of link with
  GitHub line ref anchor added if line number is part of the reference. This will insert/replace
  link.
* Fix: non-vcs projects links without a path would show unresolved even when files exist.

### 2.3.8 - Bug Fix Release

* Add: GitHub Line reference anchors in the form `L#` or `L#-L#` for line ranges. Now navigating
  to such an anchor in a project file will move the caret to the line and if second form is used
  select the lines.
* Add: with JavaFX browser clicking on task item box in preview toggles open/closed task status
  in source.
* Fix: image refs and image links to non-raw GitHub image files to show as warning. Only show
  warning for references not in raw when referenced by image refs.
* Add: Apply all '...' in file intentions where these make sense.
* Add: intention to convert between typographic symbols and markdown smarts/quotes extension
  text.
* Add: `HTML block deep parsing` parser option to allow better handling of raw text tag parsing
  when they are not the first tag on the first line of the block.
* Add: split inline code class `line-spliced` for code elements split across multiple lines not
  to appear as two inline code elements in preview.
* Fix: HTML generation with line source line highlighting when inline styling spans source lines
* Add: #74, Launching external URLs inside the browser, now `navigate to declaration` opens url
  in browser, ftp or mail client depending on the link. Can also use line markers for navigation
  of these elements.
* Fix: parsing of lists in fixed 4 spaces mode would not allow last item to be loose
* Fix: reference to non-image but not used as image target warning not raw.
* Fix: exception when navigating next/previous table cells in editor without an associated
  virtual file.
* Fix: #461, TOC with HTML generated content causes exception if skipping heading levels
* Fix: #460, TOC options do not change default Heading level
* Fix: #459, PDF export does not resolve local ref anchors
* Fix: #456, Register r markdown code chunk prefix
* Fix: #453, Option to hide toolbar
* Fix: #454, Incorrect filename inspection error, weak warning now only for wiki link targets
  that contain spaces in resolved link.
* Fix: flexmark-java issue 109, image ref loosing title tag.
* Add: GitBook compatible include tags when `GitBook compatibility mode` is enabled in `Parser`
  options.
* Fix: Nested stub index exception in reference search
* Fix: breadcrumb tooltip of task items would be missing the task item marker
* Fix: completions broken on Windows
* Fix: document format erroneously creates column spans for some tables.
* Fix: diagnostics/531, line painter provider passed line number > document line count.
* Fix: diagnostics/498, highlight in preview causing exception
* Fix: diagnostics/497, flexmark-java lib erroneous assert failure
* Fix: #447, Exported HTML has unexpected CSS and JS URLs
* Fix: #445, there should no be default language injection in bare code chunks
* Add: handling of optional quotes for jekyll include tags. Either single `'` or double `"`
  quotes will be ignored if the file name is wrapped in them.
* Fix: API break with version 2016.2.3 by using EditorModificationUtil methods missing from that
  version.
* Fix: #444, Markdown Navigator 2.3.7 breaks paste of github checkout url
* Fix: #441, false positive typo annotation in header, caused by using IdentifierSplitter
  instead of TextSplitter to handle elements that can have references.
* Fix: #442, Image Paste in Windows always pastes absolute file:// regardless of selection
* Add: Insert table column on right actions and changed description of previous action to insert
  table column on left.
* Fix: exception when exporting PDF or Copy HTML Mime
* Fix: #440, Auto links should not appear in fenced code
* Add: #411, Network drives links are not resolved correctly, URI links outside of project now
  error highlighted if the file does not exist
* Add: #433, Support external links for the Link Map (eg. JIRA link), Reference to Link Map to
  allow creating automatic reference URLs from Reference IDs

### 2.3.7 - Bug Fix Release

* Fix: parser erroneously processing escape `\` is encountered in fenced code and causing a
  parsing exception.

### 2.3.6 - Bug Fix & Enhancement Release

* Fix: intermittent index out of bounds exception if document is edited after parsing but before
  AST is built.
* Fix: #438, Markdown Syntax Change looses TOC element in source
* Add: annotation to detect when list syntax is set to GitHub
* Fix: #432, Add a way to disable the startup notification
* Fix: #436, Header link results in bad Table of Contents entry formatting
* Fix: #411, Network drives links are not resolved correctly, for `file://` which is outside the
  project and any module directory structure.
* Fix: NPE in settings under rare conditions
* Fix: assertion failure in settings under rare timing conditions
* Fix: paste NPE when pasting into link with empty address
* Fix: drag/drop without copy modifier of image files uses last non-image link format instead of
  last used image link format.
* Fix: diagnostic id:208, invalid virtual file in line painter
* Add: option to break definition list on two or more blank lines
* Fix: #428, Lack of encoding declaration when exporting in html
* Add: Global page zoom for JavaFX preview in application settings so that project preview zoom
  does not need to be changed when project is opened on a machine with different HIDPI. Now can
  leave project zoom to 1.00 and change global zoom to desired value.
* Fix: #426, Cannot add images from clipboard or drag and drop under Windows
* Fix: Setext heading to not show heading id on marker line
* Add: #425, Add Heading anchor ID display in editor
* Fix: #424, NoClassDefFoundError in WS 2017.1
* Fix: #421, NoSuchFieldError on startup after upgrading plugin on IDEs version 2016.1
* Fix: image link from non-wiki page to image in wiki would show as unresolved by annotator when
  it was resolved by line marker and preview.

### 2.3.5 - Bug Fix & Enhancement Release

* Fix: #420, java.lang.IllegalStateException: node.treeNext must not be null.
* Fix: do not un-escape HTML entities in HTML, let the browser handle that.
* Fix: #419, Bread crumbs broken when running in 2017.1
* Fix: licensed features highlight now full balloon notification.
* Fix: detection when containing file and target file of a link are not under the same VCS root
  when the containing file is in a sub-directory of target VCS root but has its own root.
* Fix: #416, NPE version 2.3.4 (w/license)
* Fix: #415, Setting default right margin in code style markdown settings disables wrapping
* Fix: #414, Exception when starting IDEA
* Fix: do not hide wrap on typing and table auto format buttons even when these are disabled.
* Fix: drag/drop image files should only show copy dialog if no drop action information or it is
  a drop copy action
* Add: plugin exception reporting to `vladsch.com` without effort.
* Fix: wiki to main repo links would not resolve. Erroneously treated two vcs repos as separate.
* Fix: clipboard mime text/html now has higher priority than file list and image on the
  clipboard.
* Add: operation options for non-image drop/paste file based on caret location
* Add: `Copy Modified Image to Clipboard` in Copy/Paste Image Dialog to replace clipboard image
  contents with modified image, can use it to replace image on clipboard then Cancel dialog to
  not modify the Markdown document but still have the modified image on the clipboard.
* Add: Copy/Modify Image intention that will open the Image Copy/Paste Dialog for the image
  content of a link element at caret position. Works with local files and URLs. Get the option
  to change directory, file name and modify the image.
* Fix: `http://` and `https://` addresses to project files would be ignored due to a typo in the
  code.
* Fix: update to flexmark-java 18.2, HTML to Markdown hang fix and MS-Word and MS-Excel HTML
  quirks handling fixed.
* Fix: link resolution logic to work for multi-vcs-root projects and modules not under project
  root.
* Fix: update to flexmark-java 18.1, HTML to Markdown adds space after empty list items.
* Add: Markdown application settings for:
  * `Use clipboard text/html content when available` disabled by default, enabling it will allow
    pasting text/html when available
  * `Convert HTML content to Markdown` enabled by default, disabling will paste text/html
    content without conversion to Markdown
* Add: `Delete empty list items` intention on lists to delete all empty list items
* Fix: HTML to Markdown converter to not ignore text in lists which is not included in list item
  but instead to put this text into a new list item.
* Add: aside extension which uses leading pipe `|` to mark an aside block just like block quote
  uses leading greater than `>` to mark a block quote element
* Add: pasting file list into markdown document inserts links the same as dropping files with
  copy action.
* Add: confirmation dialog when original markdown file is going to be overwritten with
  transformed content when pasting file list or drag and dropping files.
* Fix: absolute `http://..../wiki` link to wiki home page would to resolve as a file reference.
* Fix: drag/drop wiki page files would ignore link address format and always insert page
  relative link.
* Fix: style auto wrapping when caret at end of word that is at end of file without trailing
  EOL.
* Add: future API for drag/drop handling code to eliminate the need for replacing editor
  drag/drop handler.
* Add: highlight selection in preview, `Show source selection in preview` enabled by default.
  Wraps selection in `<span>` with `selection-highlight` class.
* Add: #399, Highlight search results in preview, `Show source search highlights in preview`
  enabled by default. Wraps highlights in `<span>` with `search-highlight` class.
* Fix: text drag/drop not working because of MarkdownPasteHandler
* Add: option to enable drag/drop handler replacement to allow "Copy" extended file drag/drop
  action at the expense of text drag/drop. Disabled by default in settings `Languages &
  Frameworks > Markdown`
* Fix: loosen/tighten list action to not mark a list as loose when blank line precedes the first
  list item.
* Fix: #404, Conversion from CommonMark or FixedIndent to GitHub does not properly indent code
  blocks in list items
* Fix: #403, Indented code in list items not indented enough with GitHub list parser option
* Change: link color in Preview and Editor Colors to match new GitHub colors
* Fix: #400, Better code color consistency needed. Now same as Fenced Code/Verbatim. Also change
  copy Markdown as HTML formatted text and PDF export to align inline code color with indented
  and fenced code.
* Fix: #398, Poor alignment between source and preview when using "Sync source to preview". Now
  there is an option to vertically align synchronized position in Preview Settings, selected by
  default.
* Fix: #402, PDF Export action fails silently if no text is selected in document instead of
  exporting the full document.

[Admonition Extension, Material for MkDocs]: https://squidfunk.github.io/mkdocs-material/extensions/admonition/
[html_mime_default.css]: https://github.com/vsch/idea-multimarkdown/blob/master/resources/com/vladsch/idea/multimarkdown/html_mime_default.css
[holgerbrandl/pasteimages]: https://github.com/holgerbrandl/pasteimages

.
<h2 id="markdown-navigator">Markdown Navigator</h2>
<h3 id="version-history">Version History</h3>
<ul>
<li><a href="#2708---bug-fix--enhancement-release">2.7.0.8 - Bug Fix &amp; Enhancement Release</a></li>
<li><a href="#270---bug-fix--enhancement-release">2.7.0 - Bug Fix &amp; Enhancement Release</a></li>
<li><a href="#260---bug-fix--enhancement-release">2.6.0 - Bug Fix &amp; Enhancement Release</a></li>
<li><a href="#254---bug-fix-release">2.5.4 - Bug Fix Release</a></li>
<li><a href="#252---bug-fix--enhancement-release">2.5.2 - Bug Fix &amp; Enhancement Release</a></li>
<li><a href="#240---bug-fix--enhancement-release">2.4.0 - Bug Fix &amp; Enhancement Release</a></li>
<li><a href="#238---bug-fix-release">2.3.8 - Bug Fix Release</a></li>
<li><a href="#237---bug-fix-release">2.3.7 - Bug Fix Release</a></li>
<li><a href="#236---bug-fix--enhancement-release">2.3.6 - Bug Fix &amp; Enhancement Release</a></li>
<li><a href="#235---bug-fix--enhancement-release">2.3.5 - Bug Fix &amp; Enhancement Release</a></li>
</ul>
<h3 id="2708---bug-fix--enhancement-release">2.7.0.8 - Bug Fix &amp; Enhancement Release</h3>
<ul>
<li class="task-list-item"><input type="checkbox" class="task-list-item-checkbox" disabled="disabled" readonly="readonly" />&nbsp;Fix: copy fixed utils from Arduino Support plugin.</li>
<li>Fix: nasty bug introducing typing delay with preview enabled.</li>
<li>in the process</li>
<li>Fix: diagnostic-2012, kotlin NPE.</li>
<li>Fix: Paste Image: old crop settings out of bounds for new image caused exception</li>
<li>Fix: for #651, Drop image with dialog issues
<ul>
<li>Spaces in file name were url encoded</li>
<li>Copy dragging a file leaves its original directory instead of setting it to the closest
or best guess based on the destination file. Should be the same as if the image was
pasted into the file. If the destination directory is the same as the source then a new
name should be generated to uniquify it.</li>
</ul>
</li>
<li>Add: in Paste/Modify Image if dragging the highlight selection without having highlight
enabled or no border, inner nor outer fill enabled, will enable highlight and border to
provide feedback otherwise it is confusing.
<ul>
<li>Add: drag selection can be used for cropping if image tab is selected and <code>Use mouse selection only for highlight</code> is not selected.</li>
<li>Fix: only copy image to transparent if Image tab is selected. The rest leave as is.</li>
<li>Add: restart notification if changing full highlight combinations</li>
</ul>
</li>
<li>Add: Image Paste highlight option to annotate an area of the image.</li>
<li>Add: option to disable synthetic highlight attributes.
<ul>
<li>Fix: #648, too many element types registered, Option for full syntax highlighter
combinations, disabling creates minimal set to reduce the limit of short index for these in
the IDE.</li>
</ul>
</li>
<li>Add: Code Style option to treat <code>Hard Wraps</code> parser option as if soft-wraps are enabled.</li>
<li>Add: Main option to force soft-wraps mode for file when opening if <code>Hard Wraps</code> are enabled</li>
</ul>
<h3 id="270---bug-fix--enhancement-release">2.7.0 - Bug Fix &amp; Enhancement Release</h3>
<ul>
<li>
<p>Fix: jekyll parser option notification would not use the file's scope based profile.</p>
</li>
<li>
<p>Fix: bump up dependencies to newer versions this</p>
</li>
<li>
<p>Fix: #647, md to html link conversion not working for exported files on Windows</p>
</li>
<li>
<p>Fix: exported files without stylesheet should not decorate link with resolved status
class.</p>
</li>
<li>
<p>Fix: <code>{% include</code> link resolution does not work without a VCS root.</p>
</li>
<li>
<p>Fix: Jekyll <code>{% include &quot;&quot; %}</code> completions would not work unless there was an <code>.html</code>
extension between the strings.</p>
</li>
<li>
<p>Fix: update for 2019.1 eap</p>
</li>
<li>
<p>Fix: intentions missing groupKey were not showing up or being run</p>
</li>
<li>
<p>Fix: make hex text dialog a licensed feature instead of dev feature.</p>
</li>
<li>
<p>Fix: diagnostic/1931, possible fix for intermittent based sequence index out of bounds fix</p>
</li>
<li>
<p>Fix: catch exception when github tasks request fails</p>
</li>
<li>
<p>Fix: settings for HTML paste are not dependent on paste handler override.</p>
</li>
<li>
<p>Fix: when ask on paste for html paste options would cause subsequent undo to fail due to
temporary file modification.</p>
</li>
<li>
<p>Fix: reverse the order of split editor configuration for &quot;Show editor and preview&quot; and &quot;Show
editor only&quot;</p>
</li>
<li>
<p>Fix: for API change in 2019.1 EAP.</p>
</li>
<li>
<p>Add: <code>Simple structure view</code> option to display only heading hierarchy in the structure view</p>
</li>
<li>
<p>Fix: optimize parser PSI generation by using hash map for type to factory function</p>
</li>
<li>
<p>Fix: diagnostic/1849, ClassCastException: LeafPsiElement cannot be cast to
MultiMarkdownLinkElement</p>
</li>
<li>
<p>Fix: image reference links to references with wrong file type or not raw would not register as
references to the reference definition. Added <code>getExactReference()</code> to return reference only
if it is an exact match, <code>getReference()</code> not matches strictly by id since it is used for
navigation and usages.</p>
</li>
<li>
<p>Add: <code>Use Style Attribute</code> option to HTML Export settings. When enabled will apply stylesheet
via <code>style</code> attribute of each element as done for <code>Copy Markdown as HTML mime content</code>.</p>
<p><strong>NOTE:</strong> stylesheet is expected to be in the same format as <code>COPY_HTML_MIME</code> stylesheet. See
<a href="https://github.com/vsch/idea-multimarkdown/wiki/Rendering-Profiles-Settings#copy-markdown-to-html-formatted-text-profile">Copy Markdown to HTML formatted Text Profile</a></p>
<p><strong>NOTE:</strong> if <code>No Stylesheets, No Scripts</code> is selected then only styles explicitly defined by the
profile will be used. If this option is not selected then <code>COPY_HTML_MIME</code> profile stylesheet
will be used or if the <code>COPY_HTML_MIME</code> profile is not defined then the
<a href="https://github.com/vsch/idea-multimarkdown/blob/master/resources/com/vladsch/idea/multimarkdown/html_mime_default.css">default stylesheet for <code>COPY_HTML_MIME</code></a> will be used.</p>
</li>
<li>
<p>Fix: move annotations for <code>Reference Links</code> to inspections</p>
</li>
<li>
<p>Fix: move annotations for <code>References</code> to inspections</p>
</li>
<li>
<p>Fix: move annotations for <code>Emoji</code> to inspections</p>
</li>
<li>
<p>Fix: move annotations for <code>Anchor</code> to inspections</p>
</li>
<li>
<p>Fix: move annotations for <code>Headings</code> to inspections</p>
</li>
<li>
<p>Fix: move annotations for <code>Tables</code> to inspections and add quick fix for column spans</p>
</li>
<li>
<p>Fix: move annotations for <code>List Items</code> and <code>Possible list items</code> to inspections</p>
</li>
<li>
<p>Add: Html Generation option to not wrap paragraphs in <code>&lt;p&gt;</code> and use <code>&lt;br&gt;</code> between paragraphs
instead. Useful for HTML exported files for use in Swing panels</p>
</li>
<li>
<p>Add: Html Export target file path options to add to target directory. Useful if need to
flatten directory structure of markdown files to a single directory for exported HTML</p>
<ul>
<li>Add path relative to project</li>
<li>Add path relative to parent directory</li>
<li>Add file name only</li>
</ul>
</li>
<li>
<p>Add: same file path type options as target path for export image copied file path.</p>
</li>
<li>
<p>Fix: IDE hangs when copying text containing the macro references which contained recursive
macros.</p>
</li>
<li>
<p>Fix: document format to ensure one blank line after macro definition</p>
</li>
<li>
<p>Fix: <code>&lt;&gt;</code> wrapped auto links would prevent following bare auto-links from being parsed.</p>
</li>
<li>
<p>Add: all elements intention to select element if intention displays dialog to give user
feedback on which element is being used.</p>
</li>
<li>
<p>Fix: do not highlight auto links as errors if remote link validation is disabled</p>
</li>
<li>
<p>Fix: remote link annotation disabled by custom URI scheme handler</p>
</li>
<li>
<p>Fix: #640, java.lang.NullPointerException with HtmlPasteOptionsForm</p>
</li>
<li>
<p>Add: Parser
<a href="https://github.com/vsch/idea-multimarkdown/wiki/Macros-Extension">Macros Extension</a></p>
</li>
<li>
<p>Fix: list item indent/unindent could insert <code>&amp;nbsp;</code> inserted/removed during wrapping but do
not perform wrapping, causing the <code>&amp;nbsp;</code> to be left in the text.</p>
</li>
<li>
<p>Add: intention for auto link to explicit link conversion and vice-versa</p>
</li>
<li>
<p>Fix: #605, Support for system specific protocol handlers. Pass through custom protocols to IDE
browser launcher.</p>
</li>
<li>
<p>Fix: to not highlight external URL links which consist of only the protocol at the end of the
line.</p>
</li>
<li>
<p>Add: color scheme export to save only non-synthetic attributes: <code>Intellij IDEA color scheme, reduced markdown (.icls)</code></p>
</li>
<li>
<p>Add: validation to auto-link remote url and completion/validation to anchor ref</p>
</li>
<li>
<p>Add: url based parser settings for remote link markdown parsing. For now hardcoded for GitHub,
GitLab and legacy GitBook compatibility. New GitBook anchor links not supported yet.</p>
</li>
<li>
<p>Fix: diagnostic/1827, Empty collection can't be reduced.</p>
</li>
<li>
<p>Fix: broken remote URL links to markdown files validation and anchor ref completions</p>
</li>
<li>
<p>Add: quick fix intention for fixing unresolved anchor refs when a match can be made by
ignoring case and removing duplicated <code>-</code></p>
</li>
<li>
<p>Fix: GitHub heading ids do not convert non-ascii to lowercase.</p>
<ul>
<li>Add: <code>Heading ids lowercase non-ascii text</code>, selected for:
<ul>
<li>GitLab profile</li>
<li>GitBook profile</li>
<li>CommonMark profile</li>
</ul>
</li>
</ul>
</li>
<li>
<p>Fix: formatter for extension</p>
</li>
<li>
<p>Fix: invalid anchor refs not annotated for local links (broken by remote link validation)</p>
</li>
<li>
<p>Add: intention for unresolved link addresses starting with <code>www.</code> to prefix with <code>https://</code>
and <code>http://</code>. If remote link validation is enabled then only prefix which results in valid
link address will be shown in the intention. If the resulting address reports as permanently
moved then also add the destination location to intentions.</p>
</li>
<li>
<p>Add: handling of HTTP:301 for remote content and intention to update link address</p>
</li>
<li>
<p>Fix: for remote content cache only store list of anchors instead of content, more compact and
provides the needed data</p>
</li>
<li>
<p>Fix: remove directories from link completions to reduce noise in completions</p>
</li>
<li>
<p>Fix: remote image links showed as unresolved, now IOExceptions during fetching treated as
resolved.</p>
</li>
<li>
<p>Fix: remove links returning image data now treated as resolved.</p>
</li>
<li>
<p>Fix: #637, Links from main project repository to files in a sub-directory repository show
unresolved</p>
</li>
<li>
<p>Add: unresolved remote link address annotation error.</p>
</li>
<li>
<p>Add: in settings total remote link count and memory use for remote content.</p>
</li>
<li>
<p>Fix: only cache remote content when it is needed for anchor ref validation. For remote link
validation only store the fact that it exists.</p>
</li>
<li>
<p>Add: remote link content cache to use for validating remote content links and anchor refs</p>
</li>
<li>
<p>Add: option to enable validation of remote links (annotates unresolved link if server returns
error)</p>
</li>
<li>
<p>Fix: remove anchor ref error annotation for links which do not resolve to a project file or do
not exist if validating remote link anchor refs</p>
</li>
<li>
<p>Add: error annotation for links to HTML files in project with anchor refs which do not link to
<code>a</code> or <code>h1</code> through <code>h6</code> html tags with <code>name</code> or <code>id</code> attribute given by anchor ref</p>
</li>
<li>
<p>Add: anchor link completion for links to HTML files in project to <code>a</code> or <code>h1</code> through <code>h6</code>
html tags with <code>name</code> or <code>id</code> attribute giving the anchor ref</p>
</li>
<li>
<p>Add: anchor link completion on external URLs which do not resolve to a project file.</p>
<ul>
<li>Special handling if file extension matches a Markdown Language extension, will download the
markdown file and will render it as HTML to extract anchor definitions</li>
<li>Special handling for GitHub (ones starting with <a href="http://">http://</a> or <a href="https://">https://</a> followed by github.com)
<ul>
<li>markdown files: If the link is to a <code>blob</code> type then will use <code>raw</code> type URL to get
Markdown so it can be correctly rendered as HTML to extract anchor definitions.</li>
<li>html content:
<ul>
<li>remove <code>user-content-</code> prefix from anchor refs (GitHub adds these automatically)</li>
<li>remove <code>[0-9a-fA-F]{32}-[0-9a-fA-F]{40}</code> looking anchor ids</li>
</ul>
</li>
</ul>
</li>
<li>Special handling for GitLab (ones starting with <a href="http://">http://</a> or <a href="https://">https://</a> followed by gitlab.com)
<ul>
<li>markdown files: If the link is to a <code>blob</code> type then will use <code>raw</code> type URL to get
Markdown so it can be correctly rendered as HTML to extract anchor definitions.</li>
<li>html content:
<ul>
<li>remove <code>[0-9a-fA-F]{32}-[0-9a-fA-F]{40}</code> looking anchor ids</li>
</ul>
</li>
</ul>
</li>
</ul>
</li>
</ul>
<h3 id="260---bug-fix--enhancement-release">2.6.0 - Bug Fix &amp; Enhancement Release</h3>
<ul>
<li>Fix: change definition indent maximum to 4, beyond which it converts the text to indented
code.</li>
<li>Fix: definition formatting would not add indent removal causing contained block quote prefix
to be doubled</li>
<li>Add: option to remove prefixes when joining lines</li>
<li>Fix: move code style <code>Continuation Lines</code> indent into <code>Text</code> code style panel.</li>
<li>Add: <code>Left Justified</code> option to ordered list style options</li>
<li>Fix: force code style parser settings to CommonMark</li>
<li>Fix: change code style sample parsing flags to modify parser flags to allow formatting all
sample elements.</li>
<li>Fix: settings &quot;Manage...&quot; exception in DataGrip without an open project. Now uses user home
dir as default directory without an open project.</li>
<li>Fix: fenced code and indented code indented with tabs would not minimize indent during
formatting.</li>
<li>Fix: HTML to markdown conversion
<ul>
<li>Fix: #268, Pipe characters are not escaped in Table (FlexmarkHtmlParser)
<ul>
<li>Fix: escape pipe characters in text (to avoid accidental use as table or other markup)
when not inline code nor fenced code</li>
<li>Fix: escape back ticks when inside code</li>
<li>Fix: disable escaping of <code>[]</code> when inside code
<ul>
<li>Fix: disable escaping of <code>\</code> when inside code</li>
</ul>
</li>
<li>Fix: replace non-break space with space when inside code</li>
</ul>
</li>
</ul>
</li>
<li>Fix: <code>FlexmarkHtmlParser.BR_AS_EXTRA_BLANK_LINES</code> now adds <code>&lt;br /&gt;</code> followed by blank line</li>
<li>Fix: JavaFx Browser initialization bug introduced by 2016.3 compatibility fix.</li>
<li>Add: &quot;Paste HTML&quot; button to HTML Paste Options dialog to paste HTML without conversion to
markdown.</li>
<li>Fix: clean up code style formatting and preview of style changes
<ul>
<li>style changes are now highlighted to properly reflect the last change, not whole document
reformat changes</li>
<li>prefix changes would not be applied (or formatted) if text wrap for paragraphs was disabled,
affected list items, definitions, block quotes</li>
<li>block quote prefix (compact with space) always inserted space after firs <code>&gt;</code> instead of last
<code>&gt;</code></li>
<li>TOC with html language option would not update preview</li>
<li>Remove unused list formatting options</li>
</ul>
</li>
<li>Add: link text suggestion for user label <code>@username</code> for GitHub user links of the form:
<code>https://github.com/username</code></li>
<li>Change: remove runtime null assertions for function arguments</li>
<li>Fix: scroll sync not working in 2018.3 EAP</li>
<li>Fix: change lambdas to functions to have <code>arguments</code> available (causing exception in JetBrains
Open JDK 1.8.0_152-release-1293-b10 x86_64</li>
<li>Add: extra diagnostic information for Swing Browser <code>EmptyStackException</code></li>
<li>Fix: diagnostic/1759, kotlin arguments erroneously defined as not nullable.</li>
<li>Fix: 2016.3 compatibility</li>
<li>Fix: markdown code style settings to be created from file when available to allow IDE scope
based resolution for markdown files to work properly.</li>
<li>Add: HTML Settings option <code>Add &lt;!DOCTYPE html&gt;</code> to enable/disable having doc type at top of
document. Required by Katex to work.</li>
<li>Fix: update emoji icons</li>
<li>Fix: GitLab math blocks to display as blocks instead of inlines</li>
<li>Fix: disable tab overrides if there is a selection in the editor or multiple carets</li>
<li>Change: split math and chart options from GitLab so that each can be selected without GitLab
extensions if GitLab extensions are not selected.</li>
<li>Add:
<a href="https://gitlab.com/gitlab-org/gitlab-ce/blob/master/doc/user/markdown.md">GitLab Flavoured Markdown</a>
parsing and rendering functionality
<ul>
<li>Math inline using <code>$``$</code> and fenced code blocks with info of <code>math</code> using
<a href="https://github.com/Khan/KaTeX">Katex</a></li>
<li>Chart fenced code blocks with info of <code>mermaid</code> using
<a href="https://github.com/knsv/mermaid">Mermaid</a></li>
<li>Inserted text (underlined) via <code>{+text+}</code> or <code>[+text+]</code></li>
<li>Deleted text (strike through) via <code>{-text-}</code> or <code>[-text-]</code></li>
<li>Multiline block quotes using <code>&gt;&gt;&gt;</code> at start of line to mark block start and <code>&lt;&lt;&lt;</code> at start
of line to mark block end.
<a href="https://gitlab.com/gitlab-org/gitlab-ce/blob/master/doc/user/markdown.md#multiline-blockquote">GFM: Multiline Blockquote</a></li>
<li>Video image link rendering
<a href="https://gitlab.com/gitlab-org/gitlab-ce/blob/master/doc/user/markdown.md#videos">GFM: Videos</a></li>
</ul>
</li>
<li>Fix: disable tab override when popup is showing to allow tab to be used for completions.</li>
<li>Fix: with CommonMark list type bullet list item after ordered list and vice versa, would allow
indentation in error.</li>
<li>Fix: #469, pressing tab in a empty list item should indent the list
<ul>
<li>added option &quot;List item indentation&quot; under &quot;Tab/Backtab Overrides&quot; which enables using
tab/backtab to change list item indentation</li>
</ul>
</li>
<li>Fix: #541, using tab to move to the next table cell
<ul>
<li>added option &quot;Table Navigation&quot; under &quot;Tab/Backtab Overrides&quot; which enables using
tab/backtab to navigate table cells</li>
</ul>
</li>
<li>Fix: GitHub issue completions for 2018.3 EAP</li>
<li>Fix: #577, Add feature to move table columns, added Move table column left/right</li>
<li>Fix: remove line/search highlights from plain HTML preview</li>
<li>Fix: remove auto links from spellchecking elements</li>
<li>Fix: partial fix for list item needs blank line annotation for list in block quotes</li>
<li>Fix: #610, hope can have more paste option, add HTML paste options to suppress conversion of
inline and heading elements</li>
<li>Fix: #623, Directory linking occasionally broken</li>
<li>Fix: compatibility with versions 2017.</li>
<li>Fix: possibly fix diagnostic pycharm exception on migrate code settings</li>
<li>Change: update deprecated API usage</li>
<li>Change: reduce number of highlighter overlay attributes to 3609 from 8497</li>
<li>Change: update source code for 2018.2 API changes.</li>
<li>Fix: #621, plugin oom in goland, potential memory leak</li>
<li>Fix: #615, Plugin can't initialize in multi-user setup, now temp directory
<code>.markdownNavigator</code> is created under the user's home directory</li>
<li>Fix: #620, Check keyboard shortcut changes wrong list item</li>
<li>Fix: #619, Create unchecked list item when pressing enter next to a line with a checked list
item</li>
<li>Fix: reference paste to add line references to link text in the form: <code>: Line #</code> or <code>: Lines #-#</code></li>
<li>Fix: diagnostic/1575, <code>node.treeNext must not be null</code></li>
<li>Fix: wrong range calculation for #612 fix</li>
<li>Fix: #611, Backspace in empty check mark box deletes check mark</li>
<li>Fix: #612, Code folding eats one character for underline headers</li>
<li>Add: HTML comment folding and options</li>
<li>Fix: diagnostic, parent already disposed</li>
</ul>
<h3 id="254---bug-fix-release">2.5.4 - Bug Fix Release</h3>
<ul>
<li>Fix: High Sierra JavaFx issue, implement JavaFX initialization as per Markdown Support.</li>
<li>Add: Document Translation using Yandex.Translate and flexmark-java translation helper API,
configured in debug settings. This is a temporary implementation which will be moved to its
own plugin in the future.</li>
<li>Fix: diagnostic/NPE on JavaFX WebView save editor state.</li>
<li>Add: conversion of emoji from HTML mime copied back from Apple mail.</li>
<li>Fix: paste image file name from link name would not be URI decoded.</li>
<li>Add: folding of list items longer than one line of text</li>
<li>Fix: #590, &quot;Create directories and folder&quot; does only create directory on first hit.</li>
<li>Fix: #591, uncomment does not remove leading space, removed padding spaces from comment
prefix/suffix.</li>
<li>Fix: shorten toolbar by moving more rare actions to popup menus: list, table, misc and copy</li>
<li>Fix: wrap on typing <code>&gt;</code> to insert block quote level into existing block quote or using
backspace to remove a block quote level</li>
<li>Fix: wrap on typing backspace in footnote definition would replicate the first line prefix on
continuation lines</li>
<li>Fix: inserting an EOL in a list item before text matching bullet list marker or numbered would
double the list marker on the resulting line</li>
<li>Add: option for escape/unescape all numbered list lead-in of <code>number.</code> when wrapping text.</li>
<li>Fix: diagnostic java.lang.RuntimeException in ImageUtils.getImageFromTransferable when pasting
an image</li>
<li>Fix: java.lang.Throwable: Invalid file: DB VirtualFile: table when caused sometimes by file
watcher requesting markdown files be re-parsed</li>
<li>Fix: diagnostic java.lang.IllegalArgumentException: Argument for @NotNull parameter 'project'</li>
<li>Fix: accept license code when extra spaces are added before EOL in e-mail.</li>
<li>Fix: diagnostic/ prevSibling should not be null.</li>
<li>Add: option to disable gutter margin for markdown documents</li>
<li>Add: option to disable line markers to main settings panel.</li>
<li>Fix: file types by specific extension completion did not work</li>
<li>Fix: link resolution would not find files with nested extensions like <code>blade.php</code></li>
<li>Change: toggle inline attribute when the caret is at the end of a non-space span now restores
the caret position when applying the style instead of end of the span, inside the markers.
More natural when inlining a word to continue typing.</li>
<li>Fix: #575, Broken Spell Checker, spell checking not working on paragraph text for basic
plugin.</li>
<li>Fix: JavaFX detection with Android Studio 3.0</li>
<li>Fix: #434, Spellchecker context menu is duplicated</li>
<li>Add: <code>Use rename for spelling error intention</code> to allow turning off <code>Rename to:</code> spellchecking
intention and use the regular <code>Change to:</code> intention.</li>
<li>Fix: remove old storage macros</li>
</ul>
<h3 id="252---bug-fix--enhancement-release">2.5.2 - Bug Fix &amp; Enhancement Release</h3>
<ul>
<li>
<p>Fix: newer API use which causes exceptions in 2017.3 IDE versions.</p>
</li>
<li>
<p>Add: link and image paste/drop options to exported/imported settings management</p>
</li>
<li>
<p>Fix: preview window would show links resolved after link map transformation to <code>http://</code> as
<code>local only</code>.</p>
</li>
<li>
<p>Fix: #567, '_'s are replaced by '-'s in TOC, GitHub now preserves <code>_</code> in heading anchor refs</p>
</li>
<li>
<p>Fix: paste handler using 2018 api <code>getSelectedEditor()</code></p>
</li>
<li>
<p>Fix: #564, Some problems with &quot;Copy / Drop Image&quot; dialog. Modify image directory history drop
down to include, in order of priority:</p>
<ul>
<li>last used image directory of the current file</li>
<li>image directories of images in the current file, ordered by count of occurrence</li>
<li>image directories in the current project, ordered by count of occurrence</li>
</ul>
</li>
<li>
<p>Fix: absolute <code>http://</code> link from main repo to wiki page which is located in a sub-directory
would show as unresolved</p>
</li>
<li>
<p>Add: help topics for all settings panels</p>
</li>
<li>
<p>Fix: improve spelling error alignment in text with many embedded inline markers</p>
</li>
<li>
<p>Fix: compatibility with IDE versions 2016.3, limit since version to 163.15529</p>
</li>
<li>
<p>Fix: COPY_HTML_MIME and PDF stylesheets now combine user provided attributes with defaults so
only difference has to be added to custom CSS.</p>
</li>
<li>
<p>Add: emoji images to COPY_HTML_MIME and PDF stylesheets so emoji display properly.</p>
</li>
<li>
<p>Fix: wiki page file name rename refactoring. Broken since 2.4.0 release</p>
</li>
<li>
<p>Fix: manually exporting a file from the toolbar now treats this as if <code>Export on Save</code> was set
for the file.</p>
</li>
<li>
<p>Fix: update GitHub wiki home link resolution for image links. Changed recently by GitHub</p>
</li>
<li>
<p>Fix: trailing spaces filter behavior changed, postponed trailing spaces would all be deleted.</p>
</li>
<li>
<p>Fix: reverse fix for &quot;WebView should be available by now&quot;, was causing deadlock if
Accessibility features were enabled.</p>
</li>
<li>
<p>Fix: JavaFX preview was using project profile parser settings, not scope based rendering
profile parser settings.</p>
</li>
<li>
<p>Fix: Formatting default table column alignment when no alignment marker is specified, header
columns are centered, body columns are left aligned.</p>
</li>
<li>
<p>Add: Export to treat emoji images as image linked files.</p>
</li>
<li>
<p>Fix: NoSuchMethodError for IDE versions &lt; 2018</p>
</li>
<li>
<p>Fix: image paste/modify dialog to not add _# suffix to file name if pasting on image target
ref and on paste action is &quot;Replace file content&quot; for link-ref targeting an image, since the
name should be the same as the link-ref in order to replace file content. Changing the name
will save it under a new file and change the link-ref.</p>
</li>
<li>
<p>Fix: remove old project settings handling and replace with IDE provided method. Old settings
copied to default project settings on first plugin initialization after upgrade. Now default
project settings support having defaults for Rendering &gt; Profiles</p>
</li>
<li>
<p>Add: Format options for Attributes:</p>
<ul>
<li>space inside braces: no change, add, remove</li>
<li>space around attribute equal sign: no change, add, remove</li>
<li>attribute value quotes:
<ul>
<li>No Change</li>
<li>None, double, single</li>
<li>None, single, double</li>
<li>Double, single</li>
<li>Double quotes</li>
<li>Single, double</li>
<li>Single quotes</li>
</ul>
</li>
</ul>
</li>
<li>
<p>Fix: table formatting would disable wrap on typing unnecessarily because it failed to properly
detect table at caret offset.</p>
</li>
<li>
<p>Add Table Caption formatting options:</p>
<ul>
<li>Caption: no change, always add, remove if empty, always remove;</li>
<li>Caption space: no change, space around text, trim spaces check box.</li>
</ul>
</li>
<li>
<p>Add: #556, Default editor layout &gt; Use last selected layout, option to have layout follow last
editor layout change action.</p>
</li>
<li>
<p>Fix: typographic <code>'</code> breaking words for spell checker generates erroneous spelling errors.</p>
</li>
<li>
<p>Fix: spell checking now done across inline markup. Error underline and Change to: intention do
not work well because of interspersed markup messing up offsets but at least spelling errors
will be highlighted. For example <code>do**sn't**</code> will now show a spelling error because the
effective text is <code>dosn't</code>.</p>
</li>
<li>
<p>Add: history to CSS settings URI text box.</p>
</li>
<li>
<p>Fix: default completion for explicit <code>http://</code> absolute links to wiki pages uses the extension</p>
</li>
<li>
<p>Fix: <code>file://</code> links to wiki pages with anchor refs showed as &quot;Only resolving locally&quot; in the
preview, all <code>file://</code> links show as resolving only locally.</p>
</li>
<li>
<p>Fix: Admonition extension would be disabled if Attributes extension was not enabled.</p>
</li>
<li>
<p>Add: Admonition parser extension.
<strong><a href="https://github.com/vsch/flexmark-java/wiki/Admonition-Extension">Admonition</a></strong>, Parser
extension based on <a href="https://squidfunk.github.io/mkdocs-material/extensions/admonition/">Admonition Extension, Material for MkDocs</a> to create block-styled side
content.</p>
</li>
<li>
<p>Fix: JavaFX WebView debug page reload in Not on FX application thread exception.</p>
</li>
<li>
<p>Fix: remove the &quot;canDebug&quot; field and replace with dynamic value.</p>
</li>
<li>
<p>Fix: remove all break points on connection shutdown.</p>
</li>
<li>
<p>Fix: JavaFX debugger core dumping if turning off debugging while it is paused.</p>
</li>
<li>
<p>Fix: Project Default settings not being copied to new projects</p>
</li>
<li>
<p>Fix: intermittent preview element highlight stopped working until page refresh</p>
</li>
<li>
<p>Fix: pasting a page relative URL would be mistaken for FQN reference and always paste a link
instead of text</p>
</li>
<li>
<p>Fix: renaming rendering profile would not be saved properly.</p>
</li>
<li>
<p>Fix: copy action on rendering profiles caused exception</p>
</li>
<li>
<p>Add: all console commands work with Chrome dev tools.</p>
</li>
<li>
<p>Fix: Chrome dev tools console evals and console logging from JavFX WebView scripts.</p>
</li>
<li>
<p>Fix: #561, Scroll sync and highlight preview element broken in EAP 2.4.0.44</p>
</li>
<li>
<p>Remove: FirebugLite script option for JavaFX. It never worked for debugging and Chrome Dev
Tools work really well with JavaFX WebView.</p>
</li>
<li>
<p>Add: &quot;Toggle Editor Split Orientation&quot; action to toggle Vertical/Horizontal split orientation</p>
</li>
<li>
<p>Add: drag/drop file inside inline, fenced or indented code to insert file name.</p>
</li>
<li>
<p>Add: dropping file after end of line with virtual spaces enabled, will insert spaces to fill
virtual spaces.</p>
</li>
<li>
<p>Fix: Adding explicit attribute to heading did not put space between text and attributes
element.</p>
</li>
<li>
<p>Add: file/ref anchor target search/explore intention on unresolved link anchor ref.</p>
<ul>
<li>Fix: launching on an anchor and cancelling, does not show intention until file is edited.</li>
<li>Add: do a partial match for anchor when no anchors match exactly.</li>
<li>Add: filter text box to filter anchor list (show all partials, the rest hidden) otherwise
too many in the list.</li>
</ul>
</li>
<li>
<p>Fix: Github collapse headers script not working in 2018.1</p>
</li>
<li>
<p>Fix: intermittent position not highlighting in preview.</p>
</li>
<li>
<p>Fix: Drag/Drop copy files does not save link drop options.Always resets or gets them wrong.</p>
</li>
<li>
<p>Add: buttons for link and image drop/paste options in markdown settings</p>
</li>
<li>
<p>Add: Updated emoji to include full set of GitHub supported ones</p>
<ul>
<li>Add: option to select which shortcuts to recognize:
<ul>
<li>Emoji Cheat Sheet</li>
<li>GitHub</li>
<li>Both: Emoji Cheat Sheet, GitHub (in order of preference for URL generation in HTML)</li>
<li>Both: GitHub, Emoji Cheat Sheet (in order of preference for URL generation in HTML)</li>
</ul>
</li>
<li>Add: option to select what type of image to use:
<ul>
<li>Images: image files only</li>
<li>Unicode and Images: use Unicode characters when available, image file otherwise</li>
<li>Unicode Only: only use unicode characters, don't recognize shortcuts which have no unicode
equivalent.</li>
</ul>
</li>
<li>Add: option to preview settings to replace Unicode emoji characters which have a
corresponding image file with the image file. This allows preview browser to display Unicode
emoji for which the browser would display unrecognized character symbol instead.</li>
</ul>
</li>
<li>
<p>Update to flexmark-java-0.32.2</p>
<ul>
<li>Fix: java-flexmark API changes</li>
<li>Fix: java-flexmark Attributes processing changes</li>
<li>Add: Parser option for Attributes assignment to text</li>
<li>Add: Parser option for Emoji Shortcut Type, Emoji Image Type</li>
<li>Add: Preview option for replacing Emoji unicode with image</li>
</ul>
</li>
<li>
<p>Add: settings option to allow directories as link targets. Allows directories to be used in
links. This functionality affects operation to completions, annotations, drag/drop link
creation and navigation.</p>
</li>
<li>
<p>Add: Drag/Drop link creation in Wiki should have wiki option for link format.</p>
</li>
<li>
<p>Fix: <code>http://</code> link to wiki home without the file shows as unresolved by annotator</p>
</li>
<li>
<p>Fix: change explicit to wiki not showing if link format is <a href="http://">http://</a> or <a href="https://">https://</a> absolute</p>
</li>
<li>
<p>Fix: when converting explicit to wiki don't generate text &amp; page ref if the explicit link text
is the same as the file part of the target: <code>[Page-Ref](Page-Ref.md)</code> -&gt; <code>[[Page Ref]]</code>, not
<code>[[Page-Ref|Page Ref]]</code></p>
</li>
<li>
<p>Fix: Allow links to directories under the repo not to show them as unresolved. Create ref to
directory object if it is under VCS</p>
</li>
<li>
<p>Fix: drag/drop directories to create a link to the directory</p>
</li>
<li>
<p>Fix: document format would remove table caption element</p>
</li>
<li>
<p>Add: Query user for new id on explicit id to heading intention to save a step of rename
refactoring it.</p>
</li>
<li>
<p>Add: if a heading has explicit id attributes, rename refactoring for it is disabled since the
id is not part of attributes.</p>
</li>
<li>
<p>Add parser option to parse inline HTML for <code>&lt;a id=&quot;...&quot;&gt;</code> for anchor targets</p>
</li>
<li>
<p>Fix abbreviation definition with empty abbreviation would cause an exception</p>
</li>
<li>
<p>Add Option to enable/disable use of image URI query serial, used to force preview update of
image when the image file changes. Disabled by default to reduce java image caching memory
issues.</p>
</li>
<li>
<p>Fix: custom paste handling into scratch files was not handled in CLion, possibly other
non-Java IDEs.</p>
</li>
<li>
<p>Fix: #554, Settings, Import and Copy from project do not get applied until corresponding
settings pane is viewed. The settings would be changed but not applied until the settings pane
was clicked on first.</p>
</li>
<li>
<p>Fix: diagnostic/1159, Inserting table rows could cause an index out bounds exception</p>
</li>
<li>
<p>Fix: files not under VCS root would show no completions for relative addressing, only had
completions for <code>file://</code> format completions.</p>
</li>
<li>
<p>Add: recall of the last imported settings file to make it easier to reset settings to a known
value.</p>
</li>
<li>
<p>Add: markdown Application settings to exported and imported settings.</p>
</li>
<li>
<p>Fix: disable local only status for links and annotation when the link is to the file itself.</p>
</li>
<li>
<p>Add: allow source/preview synchronization and search/selection highlighting in basic version.</p>
</li>
<li>
<p>Fix: diagnostic/1140, NPE in flexmark-java core node renderer.</p>
</li>
<li>
<p>Fix: diagnostic/1141, null editor causes exception in toolbar button test.</p>
</li>
<li>
<p>Add: #549, Add settings management functionality. Now in main settings panel there is a
&quot;Manage...&quot; button in top-right corner, clicking it pops up a menu with the following options:</p>
<ul>
<li><code>Copy to Project Defaults</code></li>
<li><code>Copy from Project Defaults</code></li>
<li><code>Export Settings</code></li>
<li><code>Import Settings</code></li>
<li><code>Reset Settings</code> to reset settings to default. Project defaults, current project settings
and markdown navigator application settings.</li>
</ul>
<p>These actions copy from current unsaved project settings and to current unsaved project
settings therefore you can modify settings, copy to project defaults (or export) and then
cancel, result will be project defaults (or exported settings) having modified settings while
project settings being those before modification.</p>
<p>If you copy from defaults or import a file followed by <code>Cancel</code> then no settings will be
modified.</p>
</li>
<li>
<p>Fix: #548, When &quot;Auto-scroll to source&quot; is enabled in project view, markdown navigator editor
steals focus when moving through project view with keyboard arrows.</p>
</li>
<li>
<p>Fix: #542, Typographical Error in PHPStorm Preferences &gt; Editor &gt; Code Style &gt; Markdown</p>
</li>
<li>
<p>Add: option in settings to enable editor paste handler registration so that paste handler is
enabled by default. Because the IDE has a lot of formatter exceptions on paste which get
erroneously attributed to the plugin when it delegates paste action to previous handler. Now a
notification balloon will inform of the IDE exception and offer a link to disable paste
handler customization.</p>
</li>
<li>
<p>Fix: #546, Panel is guaranteed to be not null Regression.</p>
</li>
<li>
<p>Fix: #260, Add horizontal split editor option to allow preview below the text editor. Added
option in Languages &amp; Frameworks &gt; Markdown: <code>Vertical Text/Preview Split</code>, default not
selected.</p>
</li>
<li>
<p>Fix: #524, Dedent shortcut not working properly.</p>
</li>
<li>
<p>Fix: #539, Big local images (e.g. .gif) referred to in an open .md file get locked and cause
merge conflicts and issues on checkout. Now swing implements disable GIF images option.</p>
</li>
<li>
<p>Fix: #512, Add keyboard shortcut to <code>Cycle between Preview only and Editor only</code>. Instead
added application setting to select text/split or text/preview toggle for the toggle editor
layout action.</p>
</li>
<li>
<p>Fix: #511, <code>Cycle split layout</code> shortcut stop working when <code>Preview Only</code> is selected.</p>
</li>
<li>
<p>Fix: #527, How to use <em>italics</em> instead of <em>italics</em> when pressing <code>Ctrl+I</code>. Option added to
Languages &amp; Frameworks &gt; Markdown: <code>Use asterisks (*) for italic text</code>, enabled by default.
When enabled italic action will use only asterisks for as markers.</p>
</li>
<li>
<p>Fix: #535, Documentation for link maps and mapping groups. Documentation link added to Link
Map settings panel.</p>
</li>
<li>
<p>Fix: diagnostic/1100, start/end offset on paste beyond end of document</p>
</li>
<li>
<p>Fix: clicking on a link with anchor ref by name of element would not scroll element into view</p>
</li>
<li>
<p>Add: #391, #anchor tags not working. Added anchors of the form <code>&lt;a .... attr=anchorId ...&gt;...&lt;/a&gt;</code> where <code>attr</code> is <code>id</code> or <code>name</code> to be treated as anchor ref targets. NOTE: the
first name or id attribute will be treated as the &quot;anchor target&quot; the other as a reference to
the anchor target. If both have the same string value then renaming one will rename the other.</p>
</li>
<li>
<p>Fix: regex error flexmark-java attributes parser which could cause a parsing loop</p>
</li>
<li>
<p>Add: parser option to not generate duplicate dashes <code>-</code> in heading ids</p>
</li>
<li>
<p>Fix: fenced code content erroneously processed GitHub issue marker <code>#</code>.</p>
</li>
<li>
<p>Fix: #544, Export to PDF greyed out. Editor actions would be disabled if the text editor was
not visible.</p>
</li>
<li>
<p>Add: parser options for</p>
<ul>
<li><strong><a href="https://github.com/vsch/flexmark-java/wiki/Attributes-Extension">Attributes</a></strong> and</li>
<li><strong><a href="https://github.com/vsch/flexmark-java/wiki/Enumerated-References-Extension">Enumerated References</a></strong>
parser extensions</li>
<li>Add: heading intentions to add/remove explicit id</li>
<li>Add: completions for link anchors to id attribute values</li>
<li>Add: completions for enumerated references and reference formats</li>
<li>Add: formatting options and formatting for Enumerated References</li>
<li>Add: error/unused annotations for enumerated reference, enumerated format and attribute id</li>
<li>Add: refactoring/navigation for Enumerated Reference format id's, Attribute Id's, Enumerated
Reference link/text.</li>
</ul>
</li>
<li>
<p>Fix: diagnostic: 1055, sometimes virtual file == null for a PsiFile causing an exception.</p>
</li>
<li>
<p>Add: option to add serial query suffix to CSS URI which increments when the css file changes
(only <a href="file://">file://</a> URI's and document relative URLs are supported.)</p>
</li>
<li>
<p>Fix: diagnostic 1030, when bread-crumb provider steps up to file level while looking for
headings.</p>
</li>
<li>
<p>Fix: diagnostic: 1032, sometimes an exception is thrown &quot;AssertionError: Unexpected content
storage modification&quot;</p>
</li>
<li>
<p>Fix: diagnostic 1033, paste handler exception <code>IllegalStateException: Clipboard is busy</code></p>
</li>
<li>
<p>Fix: diagnostic 1035, null pointer exception in Swing preview when image tag has no <code>src</code>
attribute.</p>
</li>
<li>
<p>Fix: diagnostic 1047, sometimes an IOException is generated if markdown sub-type is requested
during indexing operation.</p>
</li>
</ul>
<h3 id="240---bug-fix--enhancement-release">2.4.0 - Bug Fix &amp; Enhancement Release</h3>
<ul>
<li>Fix: #517, Invalid tool tip for &quot;Show Breadcrumb text&quot;</li>
<li>Change: #520, Not working: As you type automation: Double of bold/emphasis markers and remove
inserted ones if a space is typed. Enable these options in code style, disabled by default.</li>
<li>Fix: #509, Text with colons is incorrectly interpreted as an invalid emoji shortcut</li>
<li>Add: #507, How to be sure that HTML auto generated link will have unchanged url. Link format
option for HTML export: page relative, project relative, http: absolute, file: absolute if
option <code>Link to exported HTML</code> is not selected.</li>
<li>Add: #466, Indents with 4 spaces instead of 2 as I like. Code style option for indent size
added sets number of spaces to insert when pressing tab.</li>
<li>Change: Remove attribute and settings migration from pre 2.3.0 versions.</li>
<li>Add: nested heading outline collapsing</li>
<li>Fix: improved HTML to markdown conversion from Apple Mail copied text.</li>
<li>Fix: don't show emoji completions in link address part ( http: triggers it)</li>
<li>Fix: abbreviation navigation and refactoring was not implemented</li>
<li>Fix: line markers generate for leaf elements only, performance improvement</li>
<li>Fix: swing preview on linux not showing fixed pitch font for code</li>
<li>Fix: Task list items now require indent at task item marker not item content, to match GitHub
parsing rules. Indenting to content column treats children as inline code and child list items
not separated by a blank line as lazy continuation lines.</li>
<li>Fix: formatter for new task item indentation rules.</li>
<li>Fix: remove <code>Replace File Content</code> option from non-image target ref drop downs in paste/modify
image dialog, and all link options from copy/drop image dialog and link drop/paste ref options
dialog.</li>
<li>Fix: #489, Paste Image does not create file if parent directory does not exist.</li>
<li>Fix: #484, Open links in preview, not browser. Option added to preview settings to have page
relative and repo relative links resolve to GitHub files when selected. When not selected they
resolve to local project files.</li>
<li>Fix: #486, Multi-line links do not preview correctly, when in <code>Line</code> preview element highlight
mode.</li>
<li>Fix: #481, Will not allow me to crop beyond 200px, now limits are derived from the image
dimensions and image operations.</li>
<li>Fix: Update to latest flexmark-java supporting CommonMark Spec 0.28.</li>
<li>Fix: TOC entries would exclude typographic characters when &quot;text only&quot; option was used with
typographic parser extension enabled.</li>
<li>Fix: HTML to Markdown not adding HTML comment between consecutive lists</li>
<li>Fix: #479, Multi-line URL images are not converted in PDF export or Copy HTML Mime</li>
<li>Add: Show &quot;Apply all '...'&quot; intention on any element option to allow showing file level
intentions to be available on any element. Otherwise only shown on elements which they affect.</li>
<li>Add: enable image intentions on multi-line URL image links</li>
<li>Add: Code Folding option in settings for embedded image links</li>
<li>Add: HTML generation options to convert image links to embedded images, with separate option
for <a href="http://">http://</a> and <a href="https://">https://</a> image urls.</li>
<li>Add: base64 embedded image display in Swing Preview browser</li>
<li>Add: <code>Base64 Encoded</code> as a link format for pasted images and dropped files to the Paste Image
dialog.</li>
<li>Fix: base64 encode intention would keep path of url if it was present</li>
<li>Fix: image reference links to references with base64 encoded images would show as unresolved</li>
<li>Add: intentions to convert images to base64 encoding and vice-versa</li>
<li>Fix: base64 encoded embedded images did not display in JavaFX preview</li>
<li>Fix: preview navigation to links with anchor refs and line anchor refs</li>
<li>Fix: dropping a file in a document appends <code>null</code> string to the file name in error.</li>
<li>Fix: #468, Move (refactoring) of .md files breaks links to sections in same file.</li>
<li>Fix: reference paste with line ref anchor would always paste page relative format URL
regardless of the link paste format (set with file copy drop action).</li>
<li>Fix: diagnostics/713, tree view icon update before <code>FileManager</code> has been initialized will to
return markdown file type (without resolving sub-type).</li>
<li>Add: Convert markdown to HTML intention for fenced code and indented code blocks.</li>
<li>Fix: unresolved link references would be rendered in HTML instead of being treated as plain
text. Broken by <code>Reference</code> link map code.</li>
<li>Fix: paste reference past end of line will insert spaces to caret column before inserting
link.</li>
<li>Fix: links from FQN references with spaces did not url encode the link.</li>
<li>Fix: reference to link conversion for PhpStorm to truncate the reference at the <code>:</code> since
PhpStorm is not able to convert FQN strings with class method names.</li>
<li>Add: use QualifiedNameProviders to resolve reference to link conversion.</li>
<li>Add: logic to not convert qualified string to link when caret is inside inline code, fenced
code or between two back-ticks.</li>
<li>Fix: HTTP links with anchor refs should not highlight anchor links as unresolved.</li>
<li>Add: paste of file reference with or without line number converted to paste of link with
GitHub line ref anchor added if line number is part of the reference. This will insert/replace
link.</li>
<li>Fix: non-vcs projects links without a path would show unresolved even when files exist.</li>
</ul>
<h3 id="238---bug-fix-release">2.3.8 - Bug Fix Release</h3>
<ul>
<li>Add: GitHub Line reference anchors in the form <code>L#</code> or <code>L#-L#</code> for line ranges. Now navigating
to such an anchor in a project file will move the caret to the line and if second form is used
select the lines.</li>
<li>Add: with JavaFX browser clicking on task item box in preview toggles open/closed task status
in source.</li>
<li>Fix: image refs and image links to non-raw GitHub image files to show as warning. Only show
warning for references not in raw when referenced by image refs.</li>
<li>Add: Apply all '...' in file intentions where these make sense.</li>
<li>Add: intention to convert between typographic symbols and markdown smarts/quotes extension
text.</li>
<li>Add: <code>HTML block deep parsing</code> parser option to allow better handling of raw text tag parsing
when they are not the first tag on the first line of the block.</li>
<li>Add: split inline code class <code>line-spliced</code> for code elements split across multiple lines not
to appear as two inline code elements in preview.</li>
<li>Fix: HTML generation with line source line highlighting when inline styling spans source lines</li>
<li>Add: #74, Launching external URLs inside the browser, now <code>navigate to declaration</code> opens url
in browser, ftp or mail client depending on the link. Can also use line markers for navigation
of these elements.</li>
<li>Fix: parsing of lists in fixed 4 spaces mode would not allow last item to be loose</li>
<li>Fix: reference to non-image but not used as image target warning not raw.</li>
<li>Fix: exception when navigating next/previous table cells in editor without an associated
virtual file.</li>
<li>Fix: #461, TOC with HTML generated content causes exception if skipping heading levels</li>
<li>Fix: #460, TOC options do not change default Heading level</li>
<li>Fix: #459, PDF export does not resolve local ref anchors</li>
<li>Fix: #456, Register r markdown code chunk prefix</li>
<li>Fix: #453, Option to hide toolbar</li>
<li>Fix: #454, Incorrect filename inspection error, weak warning now only for wiki link targets
that contain spaces in resolved link.</li>
<li>Fix: flexmark-java issue 109, image ref loosing title tag.</li>
<li>Add: GitBook compatible include tags when <code>GitBook compatibility mode</code> is enabled in <code>Parser</code>
options.</li>
<li>Fix: Nested stub index exception in reference search</li>
<li>Fix: breadcrumb tooltip of task items would be missing the task item marker</li>
<li>Fix: completions broken on Windows</li>
<li>Fix: document format erroneously creates column spans for some tables.</li>
<li>Fix: diagnostics/531, line painter provider passed line number &gt; document line count.</li>
<li>Fix: diagnostics/498, highlight in preview causing exception</li>
<li>Fix: diagnostics/497, flexmark-java lib erroneous assert failure</li>
<li>Fix: #447, Exported HTML has unexpected CSS and JS URLs</li>
<li>Fix: #445, there should no be default language injection in bare code chunks</li>
<li>Add: handling of optional quotes for jekyll include tags. Either single <code>'</code> or double <code>&quot;</code>
quotes will be ignored if the file name is wrapped in them.</li>
<li>Fix: API break with version 2016.2.3 by using EditorModificationUtil methods missing from that
version.</li>
<li>Fix: #444, Markdown Navigator 2.3.7 breaks paste of github checkout url</li>
<li>Fix: #441, false positive typo annotation in header, caused by using IdentifierSplitter
instead of TextSplitter to handle elements that can have references.</li>
<li>Fix: #442, Image Paste in Windows always pastes absolute <a href="file://">file://</a> regardless of selection</li>
<li>Add: Insert table column on right actions and changed description of previous action to insert
table column on left.</li>
<li>Fix: exception when exporting PDF or Copy HTML Mime</li>
<li>Fix: #440, Auto links should not appear in fenced code</li>
<li>Add: #411, Network drives links are not resolved correctly, URI links outside of project now
error highlighted if the file does not exist</li>
<li>Add: #433, Support external links for the Link Map (eg. JIRA link), Reference to Link Map to
allow creating automatic reference URLs from Reference IDs</li>
</ul>
<h3 id="237---bug-fix-release">2.3.7 - Bug Fix Release</h3>
<ul>
<li>Fix: parser erroneously processing escape <code>\</code> is encountered in fenced code and causing a
parsing exception.</li>
</ul>
<h3 id="236---bug-fix--enhancement-release">2.3.6 - Bug Fix &amp; Enhancement Release</h3>
<ul>
<li>Fix: intermittent index out of bounds exception if document is edited after parsing but before
AST is built.</li>
<li>Fix: #438, Markdown Syntax Change looses TOC element in source</li>
<li>Add: annotation to detect when list syntax is set to GitHub</li>
<li>Fix: #432, Add a way to disable the startup notification</li>
<li>Fix: #436, Header link results in bad Table of Contents entry formatting</li>
<li>Fix: #411, Network drives links are not resolved correctly, for <code>file://</code> which is outside the
project and any module directory structure.</li>
<li>Fix: NPE in settings under rare conditions</li>
<li>Fix: assertion failure in settings under rare timing conditions</li>
<li>Fix: paste NPE when pasting into link with empty address</li>
<li>Fix: drag/drop without copy modifier of image files uses last non-image link format instead of
last used image link format.</li>
<li>Fix: diagnostic id:208, invalid virtual file in line painter</li>
<li>Add: option to break definition list on two or more blank lines</li>
<li>Fix: #428, Lack of encoding declaration when exporting in html</li>
<li>Add: Global page zoom for JavaFX preview in application settings so that project preview zoom
does not need to be changed when project is opened on a machine with different HIDPI. Now can
leave project zoom to 1.00 and change global zoom to desired value.</li>
<li>Fix: #426, Cannot add images from clipboard or drag and drop under Windows</li>
<li>Fix: Setext heading to not show heading id on marker line</li>
<li>Add: #425, Add Heading anchor ID display in editor</li>
<li>Fix: #424, NoClassDefFoundError in WS 2017.1</li>
<li>Fix: #421, NoSuchFieldError on startup after upgrading plugin on IDEs version 2016.1</li>
<li>Fix: image link from non-wiki page to image in wiki would show as unresolved by annotator when
it was resolved by line marker and preview.</li>
</ul>
<h3 id="235---bug-fix--enhancement-release">2.3.5 - Bug Fix &amp; Enhancement Release</h3>
<ul>
<li>Fix: #420, java.lang.IllegalStateException: node.treeNext must not be null.</li>
<li>Fix: do not un-escape HTML entities in HTML, let the browser handle that.</li>
<li>Fix: #419, Bread crumbs broken when running in 2017.1</li>
<li>Fix: licensed features highlight now full balloon notification.</li>
<li>Fix: detection when containing file and target file of a link are not under the same VCS root
when the containing file is in a sub-directory of target VCS root but has its own root.</li>
<li>Fix: #416, NPE version 2.3.4 (w/license)</li>
<li>Fix: #415, Setting default right margin in code style markdown settings disables wrapping</li>
<li>Fix: #414, Exception when starting IDEA</li>
<li>Fix: do not hide wrap on typing and table auto format buttons even when these are disabled.</li>
<li>Fix: drag/drop image files should only show copy dialog if no drop action information or it is
a drop copy action</li>
<li>Add: plugin exception reporting to <code>vladsch.com</code> without effort.</li>
<li>Fix: wiki to main repo links would not resolve. Erroneously treated two vcs repos as separate.</li>
<li>Fix: clipboard mime text/html now has higher priority than file list and image on the
clipboard.</li>
<li>Add: operation options for non-image drop/paste file based on caret location</li>
<li>Add: <code>Copy Modified Image to Clipboard</code> in Copy/Paste Image Dialog to replace clipboard image
contents with modified image, can use it to replace image on clipboard then Cancel dialog to
not modify the Markdown document but still have the modified image on the clipboard.</li>
<li>Add: Copy/Modify Image intention that will open the Image Copy/Paste Dialog for the image
content of a link element at caret position. Works with local files and URLs. Get the option
to change directory, file name and modify the image.</li>
<li>Fix: <code>http://</code> and <code>https://</code> addresses to project files would be ignored due to a typo in the
code.</li>
<li>Fix: update to flexmark-java 18.2, HTML to Markdown hang fix and MS-Word and MS-Excel HTML
quirks handling fixed.</li>
<li>Fix: link resolution logic to work for multi-vcs-root projects and modules not under project
root.</li>
<li>Fix: update to flexmark-java 18.1, HTML to Markdown adds space after empty list items.</li>
<li>Add: Markdown application settings for:
<ul>
<li><code>Use clipboard text/html content when available</code> disabled by default, enabling it will allow
pasting text/html when available</li>
<li><code>Convert HTML content to Markdown</code> enabled by default, disabling will paste text/html
content without conversion to Markdown</li>
</ul>
</li>
<li>Add: <code>Delete empty list items</code> intention on lists to delete all empty list items</li>
<li>Fix: HTML to Markdown converter to not ignore text in lists which is not included in list item
but instead to put this text into a new list item.</li>
<li>Add: aside extension which uses leading pipe <code>|</code> to mark an aside block just like block quote
uses leading greater than <code>&gt;</code> to mark a block quote element</li>
<li>Add: pasting file list into markdown document inserts links the same as dropping files with
copy action.</li>
<li>Add: confirmation dialog when original markdown file is going to be overwritten with
transformed content when pasting file list or drag and dropping files.</li>
<li>Fix: absolute <code>http://..../wiki</code> link to wiki home page would to resolve as a file reference.</li>
<li>Fix: drag/drop wiki page files would ignore link address format and always insert page
relative link.</li>
<li>Fix: style auto wrapping when caret at end of word that is at end of file without trailing
EOL.</li>
<li>Add: future API for drag/drop handling code to eliminate the need for replacing editor
drag/drop handler.</li>
<li>Add: highlight selection in preview, <code>Show source selection in preview</code> enabled by default.
Wraps selection in <code>&lt;span&gt;</code> with <code>selection-highlight</code> class.</li>
<li>Add: #399, Highlight search results in preview, <code>Show source search highlights in preview</code>
enabled by default. Wraps highlights in <code>&lt;span&gt;</code> with <code>search-highlight</code> class.</li>
<li>Fix: text drag/drop not working because of MarkdownPasteHandler</li>
<li>Add: option to enable drag/drop handler replacement to allow &quot;Copy&quot; extended file drag/drop
action at the expense of text drag/drop. Disabled by default in settings <code>Languages &amp; Frameworks &gt; Markdown</code></li>
<li>Fix: loosen/tighten list action to not mark a list as loose when blank line precedes the first
list item.</li>
<li>Fix: #404, Conversion from CommonMark or FixedIndent to GitHub does not properly indent code
blocks in list items</li>
<li>Fix: #403, Indented code in list items not indented enough with GitHub list parser option</li>
<li>Change: link color in Preview and Editor Colors to match new GitHub colors</li>
<li>Fix: #400, Better code color consistency needed. Now same as Fenced Code/Verbatim. Also change
copy Markdown as HTML formatted text and PDF export to align inline code color with indented
and fenced code.</li>
<li>Fix: #398, Poor alignment between source and preview when using &quot;Sync source to preview&quot;. Now
there is an option to vertically align synchronized position in Preview Settings, selected by
default.</li>
<li>Fix: #402, PDF Export action fails silently if no text is selected in document instead of
exporting the full document.</li>
</ul>
````````````````````````````````


## Issue xxx-02

Caused segments out of order exception in Markdown Navigator, but cannot duplicate

```````````````````````````````` example(Issue xxx-02: 1) options(dummy-identifier)
# Next Minor Release

 - **Bugs**
    #240 & #1⎮
    - Escape Key on https://thirteenag.github.io/wfp is not being stopped

 - **Features**

 - **Test, issue?**
    - Possible improvement to obscured element detection:
        - Use the middle point of the intersection of the selection rect and the bounding rect
            rather than the middle point of the bounding rect, this catches cases where the bottom
            half of the element is obscured, but not all of it

     - Consider: querySelectorAll('*:enabled') may give all elements of concern, even Elements which are divs but have an onclick() enabled or listened to.


 -  **Todo**
    - Switch this._onKeyUp = ... .bind() to this.onKeyUp = ... .bind()

# Next Major Release (3.2.0)
 - **Bugs**
    -

 - **Features**
    - Options GUI (minimal)

 - **Test, issue?**
    -


# Known Bugs


# Plans
 - Create Updated Plugin Page
 - Setup site using Jekyll
 - Add Options GUI
 - Better handling of label placement (separate from sizing rect, above &lt;SVG> Element)
 - Highlight "greatest of types" elements differently than elements which do not meet the greatest of.
    eg: 3 Buttons and 1 Anchor lassoed, the 3 Buttons would be highlighted in green vs the Anchor being highlighted in grey


# Todo / Ideas
 - Mutation Observer (clear DocElemRects, re-index if during drag)
 - Resolve all inspection errors
 - Externalize CSS Styles
 - Compiler/minimizer for production build?
 - docElem is a sandboxed global, replace document.documentElement references with docElem
 - Refactor ElemDocRects / RectMapper to ElemCache/ElemCacher (to reflect new font-size cache functionality)

# Test
 -

# Deferred / Possibly Won't Implement
 - Add Elements that have cursor: pointer (and have a click handler?)

# Feature Requests
 - [Issue #91](https://github.com/cpriest/SnapLinksPlus/issues/91)-1 - Width of selection box independent of zoom factor... possibly doable with SVG since it has it's own coordinate system??

<style>
    del { opacity: .5; }
    A { color: #36a3d9; text-decoration: underline; }
    BODY { background-color: black; }
    ARTICLE.markdown-body { padding: 35px !important; background-color: #1a1817 !important; }

/*  span { position: relative; }
    del::after {
        border-bottom: 1px solid rgba(255,255,255,.4);
        content: "";
        left: 0;
        position: absolute;
        right: 0;
        top: 50%;
    } */
</style>

.
<h1 id="next-minor-release">Next Minor Release</h1>
<ul>
  <li>
    <p><strong>Bugs</strong>
    #240 &amp; #1⎮</p>
    <ul>
      <li>Escape Key on <a href="https://thirteenag.github.io/wfp">https://thirteenag.github.io/wfp</a> is not being stopped</li>
    </ul>
  </li>
  <li>
    <p><strong>Features</strong></p>
  </li>
  <li>
    <p><strong>Test, issue?</strong></p>
    <ul>
      <li>
        <p>Possible improvement to obscured element detection:</p>
        <ul>
          <li>Use the middle point of the intersection of the selection rect and the bounding rect
            rather than the middle point of the bounding rect, this catches cases where the bottom
            half of the element is obscured, but not all of it</li>
        </ul>
      </li>
      <li>
        <p>Consider: querySelectorAll('*:enabled') may give all elements of concern, even Elements which are divs but have an onclick() enabled or listened to.</p>
      </li>
    </ul>
  </li>
  <li>
    <p><strong>Todo</strong></p>
    <ul>
      <li>Switch this._onKeyUp = ... .bind() to this.onKeyUp = ... .bind()</li>
    </ul>
  </li>
</ul>
<h1 id="next-major-release-320">Next Major Release (3.2.0)</h1>
<ul>
  <li>
    <h2 id="bugs"><strong>Bugs</strong></h2>
  </li>
  <li>
    <p><strong>Features</strong></p>
    <ul>
      <li>Options GUI (minimal)</li>
    </ul>
  </li>
  <li>
    <h2 id="test-issue"><strong>Test, issue?</strong></h2>
  </li>
</ul>
<h1 id="known-bugs">Known Bugs</h1>
<h1 id="plans">Plans</h1>
<ul>
  <li>Create Updated Plugin Page</li>
  <li>Setup site using Jekyll</li>
  <li>Add Options GUI</li>
  <li>Better handling of label placement (separate from sizing rect, above &lt;SVG&gt; Element)</li>
  <li>Highlight &quot;greatest of types&quot; elements differently than elements which do not meet the greatest of.
    eg: 3 Buttons and 1 Anchor lassoed, the 3 Buttons would be highlighted in green vs the Anchor being highlighted in grey</li>
</ul>
<h1 id="todo--ideas">Todo / Ideas</h1>
<ul>
  <li>Mutation Observer (clear DocElemRects, re-index if during drag)</li>
  <li>Resolve all inspection errors</li>
  <li>Externalize CSS Styles</li>
  <li>Compiler/minimizer for production build?</li>
  <li>docElem is a sandboxed global, replace document.documentElement references with docElem</li>
  <li>Refactor ElemDocRects / RectMapper to ElemCache/ElemCacher (to reflect new font-size cache functionality)</li>
</ul>
<h1 id="test">Test</h1>
<ul>
  <li></li>
</ul>
<h1 id="deferred--possibly-wont-implement">Deferred / Possibly Won't Implement</h1>
<ul>
  <li>Add Elements that have cursor: pointer (and have a click handler?)</li>
</ul>
<h1 id="feature-requests">Feature Requests</h1>
<ul>
  <li><a href="https://github.com/cpriest/SnapLinksPlus/issues/91">Issue #91</a>-1 - Width of selection box independent of zoom factor... possibly doable with SVG since it has it's own coordinate system??</li>
</ul>
<style>
    del { opacity: .5; }
    A { color: #36a3d9; text-decoration: underline; }
    BODY { background-color: black; }
    ARTICLE.markdown-body { padding: 35px !important; background-color: #1a1817 !important; }

/*  span { position: relative; }
    del::after {
        border-bottom: 1px solid rgba(255,255,255,.4);
        content: "";
        left: 0;
        position: absolute;
        right: 0;
        top: 50%;
    } */
</style>
````````````````````````````````


## Issue xxx.3

```````````````````````````````` example Issue xxx.3: 1
Supported Capabilities
======================

Here is a non-comprehensive table of git commands and features whose equivalent
is supported by go-git.

| Feature                               | Status | Notes |
|---------------------------------------|--------|-------|
| **protocols** |
| http(s):// (dumb)                     | ✖ |
| http(s):// (smart)                    | ✔ |
| git://                                | ✔ |
| ssh://                                | ✔ |
| file://                               | ✔ |

.
<h1 id="supported-capabilities">Supported Capabilities</h1>
<p>Here is a non-comprehensive table of git commands and features whose equivalent
is supported by go-git.</p>
<table>
  <thead>
    <tr><th>Feature</th><th>Status</th><th>Notes</th></tr>
  </thead>
  <tbody>
    <tr><td><strong>protocols</strong></td></tr>
    <tr><td>http(s):// (dumb)</td><td>✖</td></tr>
    <tr><td>http(s):// (smart)</td><td>✔</td></tr>
    <tr><td><a href="git://">git://</a></td><td>✔</td></tr>
    <tr><td><a href="ssh://">ssh://</a></td><td>✔</td></tr>
    <tr><td><a href="file://">file://</a></td><td>✔</td></tr>
  </tbody>
</table>
.
Document[0, 519]
  Heading[0, 45] text:[0, 22, "Supported Capabilities"] textClose:[23, 45, "======================"]
    Text[0, 22] chars:[0, 22, "Suppo … ities"]
  Paragraph[47, 151] isTrailingBlankLine
    Text[47, 126] chars:[47, 126, "Here  … alent"]
    SoftLineBreak[126, 127]
    Text[127, 150] chars:[127, 150, "is su … -git."]
  TableBlock[152, 518]
    TableHead[152, 210]
      TableRow[152, 210] rowNumber=1
        TableCell[152, 193] header textOpen:[152, 153, "|"] text:[154, 161, "Feature"] textClose:[192, 193, "|"]
          Text[154, 161] chars:[154, 161, "Feature"]
        TableCell[193, 202] header text:[194, 200, "Status"] textClose:[201, 202, "|"]
          Text[194, 200] chars:[194, 200, "Status"]
        TableCell[202, 210] header text:[203, 208, "Notes"] textClose:[209, 210, "|"]
          Text[203, 208] chars:[203, 208, "Notes"]
    TableSeparator[211, 269]
      TableRow[211, 269]
        TableCell[211, 252] textOpen:[211, 212, "|"] text:[212, 251, "---------------------------------------"] textClose:[251, 252, "|"]
          Text[212, 251] chars:[212, 251, "----- … -----"]
        TableCell[252, 261] text:[252, 260, "--------"] textClose:[260, 261, "|"]
          Text[252, 260] chars:[252, 260, "--------"]
        TableCell[261, 269] text:[261, 268, "-------"] textClose:[268, 269, "|"]
          Text[261, 268] chars:[261, 268, "-------"]
    TableBody[270, 517]
      TableRow[270, 287] rowNumber=1
        TableCell[270, 287] textOpen:[270, 271, "|"] text:[272, 285, "**protocols**"] textClose:[286, 287, "|"]
          StrongEmphasis[272, 285] textOpen:[272, 274, "**"] text:[274, 283, "protocols"] textClose:[283, 285, "**"]
            Text[274, 283] chars:[274, 283, "protocols"]
          Text[285, 285]
      TableRow[288, 333] rowNumber=2
        TableCell[288, 329] textOpen:[288, 289, "|"] text:[290, 307, "http(s):// (dumb)"] textClose:[328, 329, "|"]
          Text[290, 307] chars:[290, 307, "http( … dumb)"]
        TableCell[329, 333] text:[330, 331, "✖"] textClose:[332, 333, "|"]
          Text[330, 331] chars:[330, 331, "✖"]
      TableRow[334, 379] rowNumber=3
        TableCell[334, 375] textOpen:[334, 335, "|"] text:[336, 354, "http(s):// (smart)"] textClose:[374, 375, "|"]
          Text[336, 354] chars:[336, 354, "http( … mart)"]
        TableCell[375, 379] text:[376, 377, "✔"] textClose:[378, 379, "|"]
          Text[376, 377] chars:[376, 377, "✔"]
      TableRow[380, 425] rowNumber=4
        TableCell[380, 421] textOpen:[380, 381, "|"] text:[382, 388, "git://"] textClose:[420, 421, "|"]
          TextBase[382, 388] chars:[382, 388, "git://"]
            AutoLink[382, 388] text:[382, 388, "git://"] pageRef:[382, 388, "git://"]
              Text[382, 388] chars:[382, 388, "git://"]
        TableCell[421, 425] text:[422, 423, "✔"] textClose:[424, 425, "|"]
          Text[422, 423] chars:[422, 423, "✔"]
      TableRow[426, 471] rowNumber=5
        TableCell[426, 467] textOpen:[426, 427, "|"] text:[428, 434, "ssh://"] textClose:[466, 467, "|"]
          TextBase[428, 434] chars:[428, 434, "ssh://"]
            AutoLink[428, 434] text:[428, 434, "ssh://"] pageRef:[428, 434, "ssh://"]
              Text[428, 434] chars:[428, 434, "ssh://"]
        TableCell[467, 471] text:[468, 469, "✔"] textClose:[470, 471, "|"]
          Text[468, 469] chars:[468, 469, "✔"]
      TableRow[472, 517] rowNumber=6
        TableCell[472, 513] textOpen:[472, 473, "|"] text:[474, 481, "file://"] textClose:[512, 513, "|"]
          TextBase[474, 481] chars:[474, 481, "file://"]
            AutoLink[474, 481] text:[474, 481, "file://"] pageRef:[474, 481, "file://"]
              Text[474, 481] chars:[474, 481, "file://"]
        TableCell[513, 517] text:[514, 515, "✔"] textClose:[516, 517, "|"]
          Text[514, 515] chars:[514, 515, "✔"]
````````````````````````````````


