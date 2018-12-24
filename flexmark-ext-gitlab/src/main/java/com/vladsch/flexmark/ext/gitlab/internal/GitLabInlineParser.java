package com.vladsch.flexmark.ext.gitlab.internal;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ext.gitlab.GitLabDel;
import com.vladsch.flexmark.ext.gitlab.GitLabInline;
import com.vladsch.flexmark.ext.gitlab.GitLabIns;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.InlineParserExtension;
import com.vladsch.flexmark.parser.InlineParserExtensionFactory;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GitLabInlineParser implements InlineParserExtension {
    private final List<GitLabInline> openInlines;
    private final GitLabOptions options;

    public GitLabInlineParser(final InlineParser inlineParser) {
        openInlines = new ArrayList<GitLabInline>();
        options = new GitLabOptions(inlineParser.getDocument());
    }

    @Override
    public void finalizeDocument(final InlineParser inlineParser) {

    }

    @Override
    public void finalizeBlock(final InlineParser inlineParser) {
        // convert any unclosed ones to text
        for (int j = openInlines.size(); j-- > 0; ) {
            final GitLabInline gitLabInline = openInlines.get(j);
            Text textNode = new Text(gitLabInline.getChars());
            gitLabInline.insertBefore(textNode);
            gitLabInline.unlink();
        }

        openInlines.clear();
    }

    @Override
    public boolean parse(final InlineParser inlineParser) {
        final char firstChar = inlineParser.peek();
        final char secondChar = inlineParser.peek(1);
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
            BasedSequence matchOpen = BasedSequenceImpl.of(secondChar == ']' ? (firstChar == '+' ? "[+" : "[-") : (firstChar == '+' ? "{+" : "{-"));

            for (int i = openInlines.size(); i-- > 0; ) {
                final GitLabInline open = openInlines.get(i);
                final BasedSequence openMarker = open.getChars();
                if (openMarker.equals(matchOpen)) {
                    // this one is now closed, we remove all intervening ones since they did not match
                    inlineParser.setIndex(inlineParser.getIndex() + 2);
                    final BasedSequence closingMarker = input.subSequence(0, 2);
                    open.setOpeningMarker(openMarker);
                    open.setClosingMarker(closingMarker);
                    open.setText(openMarker.baseSubSequence(openMarker.getEndOffset(), closingMarker.getStartOffset()));

                    inlineParser.flushTextNode();
                    final Node last = inlineParser.getBlock().getLastChild();
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
        @Override
        public Set<Class<? extends InlineParserExtensionFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public CharSequence getCharacters() {
            return "{[-+";
        }

        @Override
        public Set<Class<? extends InlineParserExtensionFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public InlineParserExtension create(final InlineParser inlineParser) {
            return new GitLabInlineParser(inlineParser);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }
}
