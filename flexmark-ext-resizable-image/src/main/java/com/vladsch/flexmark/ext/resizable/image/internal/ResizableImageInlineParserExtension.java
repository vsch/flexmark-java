package com.vladsch.flexmark.ext.resizable.image.internal;

import com.vladsch.flexmark.ext.resizable.image.ResizableImage;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.InlineParserExtension;
import com.vladsch.flexmark.parser.InlineParserExtensionFactory;
import com.vladsch.flexmark.parser.LightInlineParser;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jspecify.annotations.Nullable;

import java.util.Set;
import java.util.regex.Pattern;

public class ResizableImageInlineParserExtension implements InlineParserExtension {
    final public static Pattern IMAGE_PATTERN = Pattern.compile("\\!\\[([^\\s\\]]*)]\\(([^\\s\\]]+)\\s*=*(\\d*)x*(\\d*)\\)",
            Pattern.CASE_INSENSITIVE);

    public ResizableImageInlineParserExtension(LightInlineParser inlineParser) {
    }

    @Override
    public void finalizeDocument(InlineParser inlineParser) {
    }

    @Override
    public void finalizeBlock(InlineParser inlineParser) {
    }

    @Override
    public boolean parse(LightInlineParser inlineParser) {
        int index = inlineParser.getIndex();
        char c = inlineParser.getInput().safeCharAt(index + 1);
        if (c == '[') {
            BasedSequence[] matches = inlineParser.matchWithGroups(IMAGE_PATTERN);
            if (matches != null) {
                inlineParser.flushTextNode();

                BasedSequence text = matches[1];
                BasedSequence source = matches[2];
                BasedSequence width = matches[3];
                BasedSequence height = matches[4];

                ResizableImage image = new ResizableImage(text, source, width, height);
                inlineParser.getBlock().appendChild(image);
                return true;
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

        @Override
        public CharSequence getCharacters() {
            return "!";
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
        }

        @Override
        public InlineParserExtension apply(LightInlineParser lightInlineParser) {
            return new ResizableImageInlineParserExtension(lightInlineParser);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }
}
