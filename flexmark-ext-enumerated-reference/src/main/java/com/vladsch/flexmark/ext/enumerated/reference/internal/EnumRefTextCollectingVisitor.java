package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.enumerated.reference.*;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequenceBuilder;

@SuppressWarnings("WeakerAccess")
public class EnumRefTextCollectingVisitor {
    private SegmentedSequenceBuilder out;
    private final NodeVisitor myVisitor;
    private int myOrdinal;

    public EnumRefTextCollectingVisitor() {
        this(0);
    }

    public EnumRefTextCollectingVisitor(int ordinal) {
        myOrdinal = ordinal;

        myVisitor = new NodeVisitor(
                new VisitHandler<Text>(Text.class, new Visitor<Text>() {
                    @Override
                    public void visit(Text node) {
                        EnumRefTextCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<TextBase>(TextBase.class, new Visitor<TextBase>() {
                    @Override
                    public void visit(TextBase node) {
                        EnumRefTextCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<HtmlEntity>(HtmlEntity.class, new Visitor<HtmlEntity>() {
                    @Override
                    public void visit(HtmlEntity node) {
                        EnumRefTextCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<SoftLineBreak>(SoftLineBreak.class, new Visitor<SoftLineBreak>() {
                    @Override
                    public void visit(SoftLineBreak node) {
                        EnumRefTextCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<HardLineBreak>(HardLineBreak.class, new Visitor<HardLineBreak>() {
                    @Override
                    public void visit(HardLineBreak node) {
                        EnumRefTextCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<EnumeratedReferenceText>(EnumeratedReferenceText.class, new Visitor<EnumeratedReferenceText>() {
                    @Override
                    public void visit(EnumeratedReferenceText node) {
                        EnumRefTextCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<EnumeratedReferenceLink>(EnumeratedReferenceLink.class, new Visitor<EnumeratedReferenceLink>() {
                    @Override
                    public void visit(EnumeratedReferenceLink node) {
                        EnumRefTextCollectingVisitor.this.visit(node);
                    }
                })
        );
    }

    public String getText() {
        return out.toString();
    }

    /**
     * @param node format node
     * @deprecated use {@link #collect(BasedSequence, EnumeratedReferenceRendering[], String)}
     */
    @Deprecated
    public void collect(Node node) {
        out = new SegmentedSequenceBuilder(node.getChars());
        myVisitor.visit(node);
    }

    /**
     * @param node format node
     * @deprecated use {@link #collectAndGetText(BasedSequence, EnumeratedReferenceRendering[], String)}
     */
    @Deprecated
    public String collectAndGetText(Node node) {
        collect(node);
        return out.toString();
    }

    /**
     * @param node format node
     * @deprecated use {@link #collectAndGetSegments(BasedSequence, EnumeratedReferenceRendering[], String)}
     */
    @Deprecated
    public BasedSequence[] collectAndGetSegments(Node node) {
        collect(node);
        return out.toSegments();
    }

    /**
     * @param node format node
     * @deprecated use {@link #collectAndGetSequence(BasedSequence, EnumeratedReferenceRendering[], String)}
     */
    @Deprecated
    public BasedSequence collectAndGetSequence(Node node) {
        collect(node);
        return out.toBasedSequence();
    }

    public void collect(BasedSequence basedSequence, final EnumeratedReferenceRendering[] renderings, final String defaultFormat) {
        out = new SegmentedSequenceBuilder(basedSequence);
        
        EnumeratedReferences.renderReferenceOrdinals(renderings, defaultFormat, new EnumeratedOrdinalRenderer() {
            @Override
            public void startRendering(final EnumeratedReferenceRendering[] renderings) {
                
            }

            @Override
            public void render(final int referenceOrdinal, final EnumeratedReferenceBlock referenceFormat, final String defaultText, final boolean needSeparator) {
                if (needSeparator) out.append(".");

                if (referenceFormat != null) {
                    myOrdinal = referenceOrdinal;
                    collect(referenceFormat);
                } else {
                    // use default text
                    out.append(defaultText);
                }
            }

            @Override
            public void endRendering() {

            }
        });
    }

    public String collectAndGetText(BasedSequence basedSequence, EnumeratedReferenceRendering[] renderings, final String defaultFormat) {
        collect(basedSequence, renderings, defaultFormat);
        return out.toString();
    }

    public BasedSequence[] collectAndGetSegments(BasedSequence basedSequence, EnumeratedReferenceRendering[] renderings, final String defaultFormat) {
        collect(basedSequence, renderings, defaultFormat);
        return out.toSegments();
    }

    public BasedSequence collectAndGetSequence(BasedSequence basedSequence, EnumeratedReferenceRendering[] renderings, final String defaultFormat) {
        collect(basedSequence, renderings, defaultFormat);
        return out.toBasedSequence();
    }

    private void visit(EnumeratedReferenceText node) {
        final String text = node.getText().toString();

        if (text.isEmpty()) {
            // placeholder for ordinal
            out.append(String.valueOf(myOrdinal));
        }
    }

    private void visit(EnumeratedReferenceLink node) {
        final String text = node.getText().toString();

        if (text.isEmpty()) {
            // placeholder for ordinal
            out.append(String.valueOf(myOrdinal));
        }
    }

    private void visit(SoftLineBreak node) {
        out.append(node.getChars());
    }

    private void visit(HardLineBreak node) {
        final BasedSequence chars = node.getChars();
        out.append(chars.subSequence(chars.length() - 1, chars.length()));
    }

    private void visit(HtmlEntity node) {
        out.append(node.getChars().unescape());
    }

    private void visit(Text node) {
        if (!node.isOrDescendantOfType(DoNotCollectText.class)) {
            out.append(node.getChars());
        }
    }

    private void visit(TextBase node) {
        out.append(node.getChars());
    }
}
