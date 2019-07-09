package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.spec.IParseBase;
import com.vladsch.flexmark.util.ast.IParse;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;

class HtmlConverter extends IParseBase {
    @SuppressWarnings("PointlessBitwiseExpression") final static public DataKey<Integer> HTML_EXTENSIONS = new DataKey<>("HTML_EXTENSIONS", 0
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
        return (HTML_EXTENSIONS.getFrom(options) | HTML_EXTENSIONS_ADD.getFrom(options)) & ~HTML_EXTENSIONS_REMOVE.getFrom(options);
    }

    public HtmlConverter() {
        this(null);
    }

    public HtmlConverter(DataHolder options) {
        super(options);

        int pegdownExtensions = getHtmlExtensions(options);
    }

    public static class RootNode extends Node {
        public final String myRootNode;

        public RootNode(String rootNode) {
            myRootNode = rootNode;
        }

        @Override
        public BasedSequence[] getSegments() {
            return Node.EMPTY_SEGMENTS;
        }
    }

    @Override
    public Node parse(BasedSequence input) {
        // here we make the lexer parse the input sequence from start to finish and accumulate everything in custom nodes
        String rootNode = FlexmarkHtmlConverter.builder(getOptions()).build().convert(input.toString(), 1);
        return new RootNode(rootNode);
    }

    @Override
    public IParse withOptions(DataHolder options) {
        MutableDataSet mutableDataSet = new MutableDataSet(getOptions());
        if (options != null) mutableDataSet.setAll(options);
        return new HtmlConverter(mutableDataSet);
    }
}
