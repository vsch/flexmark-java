package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceBlock;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceRepository;
import com.vladsch.flexmark.parser.block.ParagraphPreProcessor;
import com.vladsch.flexmark.parser.block.ParagraphPreProcessorFactory;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.parser.core.ReferencePreProcessorFactory;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// NOT USED, Parsing is done by EnumeratedReferenceBlockParser,
// otherwise Reference definitions take priority if preceded by reference definition
// because parser processor keeps going after first match
// to get around this need to add ReferenceParagraphPreProcessor set of leading characters
// which make it not a reference id
public class EnumeratedReferenceParagraphPreProcessor implements ParagraphPreProcessor {
    static String ENUM_REF_ID = "(?:[^0-9].*)?";
    static Pattern ENUM_REF_DEF_PARAGRAPH_PATTERN = Pattern.compile("\\s{0,3}(\\[[\\@]\\s*(" + ENUM_REF_ID + ")\\s*\\]:)\\s+(.*\n)");

    @SuppressWarnings("FieldCanBeLocal") final private EnumeratedReferenceOptions options;
    final private EnumeratedReferenceRepository enumeratedReferences;

    EnumeratedReferenceParagraphPreProcessor(DataHolder options) {
        this.options = new EnumeratedReferenceOptions(options);
        enumeratedReferences = EnumeratedReferenceExtension.ENUMERATED_REFERENCES.get(options);
    }

    @Override
    public int preProcessBlock(Paragraph block, ParserState state) {
        BasedSequence trySequence = block.getChars();
        Matcher matcher = ENUM_REF_DEF_PARAGRAPH_PATTERN.matcher(trySequence);
        int lastFound = 0;
        while (matcher.find()) {
            if (matcher.start() != lastFound) break;

            lastFound = matcher.end();

            int openingStart = matcher.start(1);
            int openingEnd = matcher.end(1);
            BasedSequence openingMarker = trySequence.subSequence(openingStart, openingStart + 2);
            BasedSequence text = trySequence.subSequence(openingStart + 2, openingEnd - 2).trim();
            BasedSequence closingMarker = trySequence.subSequence(openingEnd - 2, openingEnd);

            EnumeratedReferenceBlock enumeratedReferenceBlock = new EnumeratedReferenceBlock();
            enumeratedReferenceBlock.setOpeningMarker(openingMarker);
            enumeratedReferenceBlock.setText(text);
            enumeratedReferenceBlock.setClosingMarker(closingMarker);

            BasedSequence enumeratedReference = trySequence.subSequence(matcher.start(3), matcher.end(3));
            enumeratedReferenceBlock.setEnumeratedReference(enumeratedReference);
            Paragraph paragraph = new Paragraph(enumeratedReference);
            enumeratedReferenceBlock.appendChild(paragraph);
            enumeratedReferenceBlock.setCharsFromContent();

            block.insertBefore(enumeratedReferenceBlock);
            state.blockAdded(enumeratedReferenceBlock);

            enumeratedReferences.put(enumeratedReferenceBlock.getText().toString(), enumeratedReferenceBlock);
        }
        return lastFound;
    }

    public static ParagraphPreProcessorFactory Factory() {
        return new ParagraphPreProcessorFactory() {
            @Override
            public boolean affectsGlobalScope() {
                return true;
            }

            @Nullable
            @Override
            public Set<Class<?>> getAfterDependents() {
                return null;
            }

            @NotNull
            @Override
            public Set<Class<?>> getBeforeDependents() {
                HashSet<Class<?>> set = new HashSet<>();
                set.add(ReferencePreProcessorFactory.class);
                return set;
            }

            @Override
            public ParagraphPreProcessor apply(ParserState state) {
                return new EnumeratedReferenceParagraphPreProcessor(state.getProperties());
            }
        };
    }
}
