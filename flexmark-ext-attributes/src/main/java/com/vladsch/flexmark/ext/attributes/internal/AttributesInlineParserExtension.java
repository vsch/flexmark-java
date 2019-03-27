package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ext.attributes.AttributeNode;
import com.vladsch.flexmark.ext.attributes.AttributesDelimiter;
import com.vladsch.flexmark.ext.attributes.AttributesNode;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.InlineParserExtension;
import com.vladsch.flexmark.parser.InlineParserExtensionFactory;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Set;
import java.util.regex.Matcher;

public class AttributesInlineParserExtension implements InlineParserExtension {
    private final AttributeParsing parsing;

    public AttributesInlineParserExtension(final InlineParser inlineParser) {
        this.parsing = new AttributeParsing(inlineParser.getParsing());
    }

    @Override
    public void finalizeDocument(final InlineParser inlineParser) {

    }

    @Override
    public void finalizeBlock(final InlineParser inlineParser) {
    }

    @Override
    public boolean parse(final InlineParser inlineParser) {
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

                int leadingSpaces = attributesText.countLeading(" \t");
                // give it to the text node
                if (leadingSpaces > 0) {
                    inlineParser.appendText(attributesText, 0, leadingSpaces);
                    attributesText = attributesText.subSequence(leadingSpaces);
                }

                inlineParser.flushTextNode();
                inlineParser.getBlock().appendChild(attributes);

                BasedSequence attributeText = attributesText.trim();
                if (!attributeText.isEmpty() && attributes instanceof AttributesNode) {
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
                        if (attributeSeparator.isNull() && attributeSeparator.isNull() && attributeValue.isNull() && AttributeNode.isImplicitName(attributeName)) {
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
        @Override
        public Set<Class<? extends InlineParserExtensionFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public CharSequence getCharacters() {
            return "{";
        }

        @Override
        public Set<Class<? extends InlineParserExtensionFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public InlineParserExtension create(final InlineParser inlineParser) {
            return new AttributesInlineParserExtension(inlineParser);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }
}
