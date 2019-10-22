package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface LightInlineParser {
    @NotNull ArrayList<BasedSequence> getCurrentText();
    @NotNull BasedSequence getInput();
    void setInput(BasedSequence input);
    int getIndex();
    void setIndex(int index);
    @NotNull Node getBlock();
    @Nullable BasedSequence match(Pattern re);
    @Nullable BasedSequence[] matchWithGroups(Pattern re);
    @Nullable Matcher matcher(Pattern re);
    char peek();
    char peek(int ahead);
    boolean flushTextNode();
    @NotNull Document getDocument();
    void setDocument(@NotNull Document document);
    @NotNull InlineParserOptions getOptions();
    @NotNull Parsing getParsing();
    void appendText(@NotNull BasedSequence text);
    void appendText(@NotNull BasedSequence text, int beginIndex, int endIndex);
    void appendNode(@NotNull Node node);

    // In some cases, we don't want the text to be appended to an existing node, we need it separate
    @NotNull Text appendSeparateText(@NotNull BasedSequence text);
    void setBlock(@NotNull Node block);
    void moveNodes(@NotNull Node fromNode, @NotNull Node toNode);
    boolean spnl();
    boolean nonIndentSp();
    boolean sp();
    boolean spnlUrl();
    @Nullable BasedSequence toEOL();
}
