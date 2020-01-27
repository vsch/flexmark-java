package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.test.util.spec.IParseBase;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

class HtmlConverter extends IParseBase {
    final static public DataKey<Integer> HTML_EXTENSIONS = new DataKey<>("HTML_EXTENSIONS", 0
            //| Extensions.ABBREVIATIONS
            //| Extensions.EXTANCHORLINKS /*| Extensions.EXTANCHORLINKS_WRAP*/
            //| Extensions.AUTOLINKS
            //| Extensions.DEFINITIONS
            //| Extensions.FENCED_CODE_BLOCKS
            //| Extensions.FORCELISTITEMPARA
            //| Extensions.HARDWRAPS
            //| Extensions.ATXHEADERSPACE
            //| Extensions.QUOTES
            //| Extensions.SMARTS
            //| Extensions.RELAXEDHRULES
            //| Extensions.STRIKETHROUGH
            //| Extensions.SUPPRESS_HTML_BLOCKS
            //| Extensions.SUPPRESS_INLINE_HTML
            //| Extensions.TABLES
            //| Extensions.TASKLISTITEMS
            //| Extensions.WIKILINKS
            //| Extensions.TRACE_PARSER
    );

    final static public DataKey<Integer> HTML_EXTENSIONS_ADD = new DataKey<>("HTML_EXTENSIONS_ADD", 0);
    final static public DataKey<Integer> HTML_EXTENSIONS_REMOVE = new DataKey<>("HTML_EXTENSIONS_REMOVE", 0);

    public static int getHtmlExtensions(DataHolder options) {
        return (HTML_EXTENSIONS.get(options) | HTML_EXTENSIONS_ADD.get(options)) & ~HTML_EXTENSIONS_REMOVE.get(options);
    }

    public HtmlConverter() {
        this(null);
    }

    public HtmlConverter(DataHolder options) {
        super(options);

        int pegdownExtensions = getHtmlExtensions(options);
    }

    public static class RootNode extends Node {
        final public String myRootNode;

        public RootNode(String rootNode) {
            myRootNode = rootNode;
        }

        @NotNull
        @Override
        public BasedSequence[] getSegments() {
            return Node.EMPTY_SEGMENTS;
        }
    }

    @Override
    public @NotNull Node parse(@NotNull BasedSequence input) {
        // here we make the lexer parse the input sequence from start to finish and accumulate everything in custom nodes
        String rootNode = FlexmarkHtmlConverter.builder(getOptions()).build().convert(input.toString(), 1);
        return new RootNode(rootNode);
    }
}
