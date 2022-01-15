package com.atlassian.plugins.confluence.markdown.ext.DevOpsResizableImage.internal;

import com.atlassian.plugins.confluence.markdown.ext.DevOpsResizableImage.ResizableImage;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.InlineParserExtension;
import com.vladsch.flexmark.parser.InlineParserExtensionFactory;
import com.vladsch.flexmark.parser.LightInlineParser;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.regex.Pattern;

public class ResizableImageInlineParserExtension implements InlineParserExtension {
    final public static Pattern IMAGE_PATTERN = Pattern.compile("\\!\\[(\\S*)\\]\\((\\S*)\\s*=*(\\d*)x*(\\d*)\\)",
            Pattern.CASE_INSENSITIVE);

    public ResizableImageInlineParserExtension(LightInlineParser inlineParser) {
    }

    @Override
    public void finalizeDocument(@NotNull InlineParser inlineParser) {
    }

    @Override
    public void finalizeBlock(@NotNull InlineParser inlineParser) {
    }

    @Override
    public boolean parse(@NotNull LightInlineParser inlineParser) {
        int index = inlineParser.getIndex();
        char c = inlineParser.getInput().charAt(index + 1);
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

        @NotNull
        @Override
        public CharSequence getCharacters() {
            return "!";
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
        }

        @NotNull
        @Override
        public InlineParserExtension apply(@NotNull LightInlineParser lightInlineParser) {
            return new ResizableImageInlineParserExtension(lightInlineParser);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }
}
