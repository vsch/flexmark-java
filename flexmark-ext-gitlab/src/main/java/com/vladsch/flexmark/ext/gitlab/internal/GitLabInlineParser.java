package com.vladsch.flexmark.ext.gitlab.internal;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ext.gitlab.GitLabDel;
import com.vladsch.flexmark.ext.gitlab.GitLabInline;
import com.vladsch.flexmark.ext.gitlab.GitLabIns;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.InlineParserExtension;
import com.vladsch.flexmark.parser.InlineParserExtensionFactory;
import com.vladsch.flexmark.parser.LightInlineParser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GitLabInlineParser implements InlineParserExtension {
    final private List<GitLabInline> openInlines;
    final private GitLabOptions options;

    public GitLabInlineParser(LightInlineParser inlineParser) {
        openInlines = new ArrayList<>();
        options = new GitLabOptions(inlineParser.getDocument());
    }

    @Override
    public void finalizeDocument(@NotNull InlineParser inlineParser) {

    }

    @Override
    public void finalizeBlock(@NotNull InlineParser inlineParser) {
        // convert any unclosed ones to text
        for (int j = openInlines.size(); j-- > 0; ) {
            GitLabInline gitLabInline = openInlines.get(j);
            Text textNode = new Text(gitLabInline.getChars());
            gitLabInline.insertBefore(textNode);
            gitLabInline.unlink();
        }

        openInlines.clear();
    }

    @Override
    public boolean parse(@NotNull LightInlineParser inlineParser) {
        char firstChar = inlineParser.peek();
        char secondChar = inlineParser.peek(1);
        if ((firstChar == '{' || firstChar == '[') && (options.insParser && secondChar == '+' || options.delParser && secondChar == '-')) {
            // possible open, if matched close
            BasedSequence input = inlineParser.getInput().subSequence(inlineParser.getIndex());

            GitLabInline open = secondChar == '+' ? new GitLabIns(input.subSequence(0, 2)) : new GitLabDel(input.subSequence(0, 2));
            inlineParser.flushTextNode();
            inlineParser.getBlock().appendChild(open);
            openInlines.add(open);
            inlineParser.setIndex(inlineParser.getIndex() + 2);
            return true;
        }

        if (((options.insParser && firstChar == '+' || options.delParser && firstChar == '-')) && (secondChar == ']' || secondChar == '}')) {
            // possible closed, if matches open
            BasedSequence input = inlineParser.getInput().subSequence(inlineParser.getIndex());
            CharSequence charSequence = secondChar == ']' ? (firstChar == '+' ? "[+" : "[-") : (firstChar == '+' ? "{+" : "{-");
            BasedSequence matchOpen = BasedSequence.of(charSequence);

            for (int i = openInlines.size(); i-- > 0; ) {
                GitLabInline open = openInlines.get(i);
                BasedSequence openMarker = open.getChars();
                if (openMarker.equals(matchOpen)) {
                    // this one is now closed, we remove all intervening ones since they did not match
                    inlineParser.setIndex(inlineParser.getIndex() + 2);
                    BasedSequence closingMarker = input.subSequence(0, 2);
                    open.setOpeningMarker(openMarker);
                    open.setClosingMarker(closingMarker);
                    open.setText(openMarker.baseSubSequence(openMarker.getEndOffset(), closingMarker.getStartOffset()));

                    inlineParser.flushTextNode();
                    Node last = inlineParser.getBlock().getLastChild();
                    inlineParser.moveNodes(open, last);
                    //open.appendChild(textNode);
                    //inlineParser.getBlock().appendChild(open);

                    if (i == 0) {
                        openInlines.clear();
                    } else {
                        openInlines.subList(i, openInlines.size()).clear();
                    }

                    return true;
                }
            }
        }
        return false;
    }

    public static class Factory implements InlineParserExtensionFactory {
        @Nullable
        @Override
        public Set<Class<?>> getAfterDependents() {
            return null;
        }

        @NotNull
        @Override
        public CharSequence getCharacters() {
            return "{[-+";
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
        }

        @NotNull
        @Override
        public InlineParserExtension apply(@NotNull LightInlineParser lightInlineParser) {
            return new GitLabInlineParser(lightInlineParser);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }
}
