package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ext.attributes.AttributeNode;
import com.vladsch.flexmark.ext.attributes.AttributesDelimiter;
import com.vladsch.flexmark.ext.attributes.AttributesNode;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.InlineParserExtension;
import com.vladsch.flexmark.parser.InlineParserExtensionFactory;
import com.vladsch.flexmark.parser.LightInlineParser;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.regex.Matcher;

public class AttributesInlineParserExtension implements InlineParserExtension {
    final private AttributeParsing parsing;

    public AttributesInlineParserExtension(LightInlineParser inlineParser) {
        this.parsing = new AttributeParsing(inlineParser.getParsing());
    }

    @Override
    public void finalizeDocument(@NotNull InlineParser inlineParser) {

    }

    @Override
    public void finalizeBlock(@NotNull InlineParser inlineParser) {
    }

    @Override
    public boolean parse(@NotNull LightInlineParser inlineParser) {
        if (inlineParser.peek(1) != '{') {
            int index = inlineParser.getIndex();
            BasedSequence input = inlineParser.getInput();
            Matcher matcher = inlineParser.matcher(parsing.ATTRIBUTES_TAG);
            if (matcher != null) {
                BasedSequence attributesOpen = input.subSequence(matcher.start(), matcher.end());

                // see what we have
                // open, see if open/close
                BasedSequence attributesText = input.subSequence(matcher.start(1), matcher.end(1));
                AttributesNode attributes = attributesText.equals("#") || attributesText.equals(".") ? new AttributesDelimiter(attributesOpen.subSequence(0, 1), attributesText, attributesOpen.endSequence(1))
                        : new AttributesNode(attributesOpen.subSequence(0, 1), attributesText, attributesOpen.endSequence(1));

                attributes.setCharsFromContent();

                inlineParser.flushTextNode();
                inlineParser.getBlock().appendChild(attributes);

                BasedSequence attributeText = attributesText.trim();
                if (!attributeText.isEmpty()) {
                    // have some attribute text
                    // parse attributes
                    Matcher attributeMatcher = parsing.ATTRIBUTE.matcher(attributeText);
                    while (attributeMatcher.find()) {
                        BasedSequence attributeName = attributeText.subSequence(attributeMatcher.start(1), attributeMatcher.end(1));
                        BasedSequence attributeSeparator = attributeMatcher.groupCount() == 1 || attributeMatcher.start(2) == -1 ? BasedSequence.NULL : attributeText.subSequence(attributeMatcher.end(1), attributeMatcher.start(2)).trim();
                        BasedSequence attributeValue = attributeMatcher.groupCount() == 1 || attributeMatcher.start(2) == -1 ? BasedSequence.NULL : attributeText.subSequence(attributeMatcher.start(2), attributeMatcher.end(2));
                        boolean isQuoted = attributeValue.length() >= 2 && (attributeValue.charAt(0) == '"' && attributeValue.endCharAt(1) == '"' || attributeValue.charAt(0) == '\'' && attributeValue.endCharAt(1) == '\'');
                        BasedSequence attributeOpen = !isQuoted ? BasedSequence.NULL : attributeValue.subSequence(0, 1);
                        BasedSequence attributeClose = !isQuoted ? BasedSequence.NULL : attributeValue.endSequence(1, 0);

                        if (isQuoted) {
                            attributeValue = attributeValue.midSequence(1, -1);
                        }

                        AttributeNode attribute;
                        if (attributeSeparator.isNull() && attributeValue.isNull() && AttributeNode.isImplicitName(attributeName)) {
                            attribute = new AttributeNode(attributeName.subSequence(0, 1), attributeSeparator, attributeOpen, attributeName.subSequence(1), attributeClose);
                        } else {
                            attribute = new AttributeNode(attributeName, attributeSeparator, attributeOpen, attributeValue, attributeClose);
                        }
                        attributes.appendChild(attribute);
                    }

                    return true;
                }

                // did not process, reset to where we started
                inlineParser.setIndex(index);
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
            return "{";
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
        }

        @NotNull
        @Override
        public InlineParserExtension apply(@NotNull LightInlineParser inlineParser) {
            return new AttributesInlineParserExtension(inlineParser);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }
}
