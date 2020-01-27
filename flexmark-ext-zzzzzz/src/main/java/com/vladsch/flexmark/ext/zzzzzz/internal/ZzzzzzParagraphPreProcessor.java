package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzBlock;
import com.vladsch.flexmark.parser.block.CharacterNodeFactory;
import com.vladsch.flexmark.parser.block.ParagraphPreProcessor;
import com.vladsch.flexmark.parser.block.ParagraphPreProcessorFactory;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.parser.core.ReferencePreProcessorFactory;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.Nullable;

import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class ZzzzzzParagraphPreProcessor implements ParagraphPreProcessor {
    private static String COL = "(?:" + "\\s*-{3,}\\s*|\\s*:-{2,}\\s*|\\s*-{2,}:\\s*|\\s*:-+:\\s*" + ")";
    private static Pattern TABLE_HEADER_SEPARATOR = Pattern.compile(
            // For single column, require at least one pipe, otherwise it's ambiguous with setext headers
            "\\|" + COL + "\\|?\\s*" + "|" +
                    COL + "\\|\\s*" + "|" +
                    "\\|?" + "(?:" + COL + "\\|)+" + COL + "\\|?\\s*");

    private static BitSet pipeCharacters = new BitSet(1);
    static {
        pipeCharacters.set('|');
    }

    private static HashMap<Character, CharacterNodeFactory> pipeNodeMap = new HashMap<>();
    static {
        pipeNodeMap.put('|', new CharacterNodeFactory() {
            @Override
            public boolean skipNext(char c) {
                return c == ' ' || c == '\t';
                //return false;
            }

            @Override
            public boolean skipPrev(char c) {
                return c == ' ' || c == '\t';
                //return false;
            }

            @Override
            public boolean wantSkippedWhitespace() {
                return true;
            }

            @Override
            public Node get() {
                return new ZzzzzzBlock();
            }
        });
    }
    final private ZzzzzzOptions options;

    ZzzzzzParagraphPreProcessor(DataHolder options) {
        this.options = new ZzzzzzOptions(options);
    }

    private ZzzzzzParagraphPreProcessor(ZzzzzzOptions options) {
        this.options = options;
    }

    @Override
    public int preProcessBlock(Paragraph block, ParserState state) {
        //InlineParser inlineParser = state.getInlineParser();
        return 0;
    }

    public static ParagraphPreProcessorFactory Factory() {
        return new ParagraphPreProcessorFactory() {
            @Override
            public boolean affectsGlobalScope() {
                return false;
            }

            @Nullable
            @Override
            public Set<Class<?>> getAfterDependents() {
                HashSet<Class<?>> set = new HashSet<>();
                set.add(ReferencePreProcessorFactory.class);
                return set;
            }

            @Nullable
            @Override
            public Set<Class<?>> getBeforeDependents() {
                return null;
            }

            @Override
            public ParagraphPreProcessor apply(ParserState state) {
                return new ZzzzzzParagraphPreProcessor(state.getProperties());
            }
        };
    }
}
