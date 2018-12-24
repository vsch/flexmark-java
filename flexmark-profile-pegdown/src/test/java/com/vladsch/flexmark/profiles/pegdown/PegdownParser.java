package com.vladsch.flexmark.profiles.pegdown;

import com.vladsch.flexmark.util.IParse;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.spec.IParseBase;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

class PegdownParser extends IParseBase {
    @SuppressWarnings("PointlessBitwiseExpression") final static public DataKey<Integer> PEGDOWN_EXTENSIONS = new DataKey<>("PEGDOWN_EXTENSIONS", 0
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

    final static public DataKey<Integer> PEGDOWN_EXTENSIONS_ADD = new DataKey<>("PEGDOWN_EXTENSIONS_ADD", 0);
    final static public DataKey<Integer> PEGDOWN_EXTENSIONS_REMOVE = new DataKey<>("PEGDOWN_EXTENSIONS_REMOVE", 0);

    public static int getPegdownExtensions(DataHolder options) {
        return (PEGDOWN_EXTENSIONS.getFrom(options) | PEGDOWN_EXTENSIONS_ADD.getFrom(options)) & ~PEGDOWN_EXTENSIONS_REMOVE.getFrom(options);
    }

    public PegdownParser() {
        this(null);
    }

    public PegdownParser(DataHolder options) {
        super(options);

        int pegdownExtensions = getPegdownExtensions(options);
    }

    public static class PegdownRootNode extends Node {
        public final RootNode myRootNode;

        public PegdownRootNode(final RootNode rootNode) {
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
        final int pegdownExtensions = getPegdownExtensions(getOptions());
        RootNode rootNode = new PegDownProcessor(pegdownExtensions).parseMarkdown(input.toString().toCharArray());
        return new PegdownRootNode(rootNode);
    }

    @Override
    public IParse withOptions(DataHolder options) {
        final MutableDataSet mutableDataSet = new MutableDataSet(getOptions());
        if (options != null) mutableDataSet.setAll(options);
        return new PegdownParser(mutableDataSet);
    }
}
