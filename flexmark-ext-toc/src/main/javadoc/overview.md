**flexmark-java table of contents extension**

Converts `[TOC style]` text to TocBlock nodes and 

Or Sim TOC tag, which has the following format: `[TOC style]: # "Title"` and includes all
following lines until a blank line. <!--SimTocContent-->


Lines after the TOC tag are added to the `SimTocContent` child node of the SimToc block.

The intention for this tag is to have the `SimTocContent` updated to reflect the content of the
document.



1. `style` consists of space separated list of options:

   * `levels=levelList` where level list is a comma separated list of levels or ranges. Default
     is to include heading levels 2 and 3. Examples:
     * `levels=4` include levels 2,3 and 4
     * `levels=2-4` include levels 2,3 and 4. same as `levels=4`
     * `levels=2-4,5` include levels 2,3,4 and 5
     * `levels=1,3` include levels 1 and 3
     * `levels=-3` include levels 1 to 3
     * `levels=3-` include levels 3 to 6

   * `html` generate HTML version of the TOC

   * `markdown` generate Markdown version of the TOC

   * `text` to only include the text of the heading

   * `formatted` to include text and inline formatting

   * `hierarchy` to render as hierarchical list in order of appearance in the document

   * `flat` to render as a flat list in order of appearance in the document

   * `reversed` to render as a flat list in order of appearance in the document

   * `sort-up` to render as a flat list sorted alphabetically by heading text only, no inlines

   * `sort-down` to render as a flat list sorted reversed alphabetically by heading text only,
     no inlines

   * `bullet` to use a bullet list for the TOC items

   * `numbered` to use a numbered list for TOC items

