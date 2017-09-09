package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.ast.util.TextCollectingVisitor;
import com.vladsch.flexmark.docx.converter.CustomNodeDocxRenderer;
import com.vladsch.flexmark.ext.gfm.strikethrough.Strikethrough;
import com.vladsch.flexmark.ext.gfm.strikethrough.Subscript;
import com.vladsch.flexmark.ext.ins.Ins;
import com.vladsch.flexmark.ext.tables.*;
import com.vladsch.flexmark.html.renderer.LinkType;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.superscript.Superscript;
import com.vladsch.flexmark.util.Function;
import com.vladsch.flexmark.util.ImageUtils;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.ValueRunnable;
import com.vladsch.flexmark.util.format.options.ListSpacing;
import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.WordprocessingML.AltChunkType;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.*;

import static com.vladsch.flexmark.html.renderer.LinkStatus.UNKNOWN;
import static java.lang.Character.isLetter;

@SuppressWarnings("WeakerAccess")
public class CoreNodeDocxRenderer implements PhasedNodeDocxRenderer {
    public static final DataKey<Integer> LIST_ITEM_NUMBER = new DataKey<Integer>("LIST_ITEM_NUMBER", 0);
    public static final DataKey<ListSpacing> LIST_ITEM_SPACING = new DataKey<ListSpacing>("LIST_ITEM_SPACING", (ListSpacing) null);
    public static final HashSet<DocxRendererPhase> RENDERING_PHASES = new HashSet<DocxRendererPhase>(Arrays.asList(
            DocxRendererPhase.COLLECT,
            DocxRendererPhase.DOCUMENT_TOP,
            DocxRendererPhase.DOCUMENT_BOTTOM
    ));

    public static final String LOOSE_PARAGRAPH_STYLE = "ParagraphTextBody";
    public static final String TIGHT_PARAGRAPH_STYLE = "TextBody";
    public static final String PREFORMATTED_TEXT_STYLE = "PreformattedText";
    public static final String BLOCK_QUOTE_STYLE = "Quotations";
    public static final String HORIZONTAL_LINE_STYLE = "HorizontalLine";
    public static final String INLINE_CODE_STYLE = "SourceText";
    public static final String HYPERLINK_STYLE = "Hyperlink";

    protected final ReferenceRepository referenceRepository;

    private final DocxRendererOptions options;
    private final ListOptions listOptions;
    protected boolean recheckUndefinedReferences;
    protected boolean repositoryNodesDone;
    private int listNumId;
    private int imageId;

    public CoreNodeDocxRenderer(DataHolder options) {
        this.referenceRepository = getRepository(options);
        this.recheckUndefinedReferences = DocxRenderer.RECHECK_UNDEFINED_REFERENCES.getFrom(options);
        this.repositoryNodesDone = false;

        this.options = new DocxRendererOptions(options);
        this.listOptions = ListOptions.getFrom(options);
    }

    @Override
    public Set<DocxRendererPhase> getFormattingPhases() {
        return RENDERING_PHASES;
    }

    @Override
    public void renderDocument(final DocxRendererContext docx, final Document document, final DocxRendererPhase phase) {
        // here non-rendered elements can be collected so that they are rendered in another part of the document
        switch (phase) {
            case COLLECT:
                break;

            case DOCUMENT_TOP:
                break;

            case DOCUMENT_BOTTOM:
                break;

            default:
                break;
        }
    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    public ReferenceRepository getRepository(final DataHolder options) {
        return options.get(Parser.REFERENCES);
    }

    @Override
    public Set<NodeDocxRendererHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<NodeDocxRendererHandler<? extends Node>>(Arrays.asList(
                // Generic unknown node formatter
                new NodeDocxRendererHandler<Node>(Node.class, new CustomNodeDocxRenderer<Node>() {
                    @Override
                    public void render(final Node node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),

                new NodeDocxRendererHandler<AutoLink>(AutoLink.class, new CustomNodeDocxRenderer<AutoLink>() {
                    @Override
                    public void render(final AutoLink node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<BlankLine>(BlankLine.class, new CustomNodeDocxRenderer<BlankLine>() {
                    @Override
                    public void render(final BlankLine node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<BlockQuote>(BlockQuote.class, new CustomNodeDocxRenderer<BlockQuote>() {
                    @Override
                    public void render(final BlockQuote node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<Code>(Code.class, new CustomNodeDocxRenderer<Code>() {
                    @Override
                    public void render(final Code node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<Document>(Document.class, new CustomNodeDocxRenderer<Document>() {
                    @Override
                    public void render(final Document node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<Emphasis>(Emphasis.class, new CustomNodeDocxRenderer<Emphasis>() {
                    @Override
                    public void render(final Emphasis node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<Emphasis>(Emphasis.class, new CustomNodeDocxRenderer<Emphasis>() {
                    @Override
                    public void render(final Emphasis node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<Ins>(Ins.class, new CustomNodeDocxRenderer<Ins>() {
                    @Override
                    public void render(final Ins node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<Strikethrough>(Strikethrough.class, new CustomNodeDocxRenderer<Strikethrough>() {
                    @Override
                    public void render(final Strikethrough node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<Superscript>(Superscript.class, new CustomNodeDocxRenderer<Superscript>() {
                    @Override
                    public void render(final Superscript node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<Subscript>(Subscript.class, new CustomNodeDocxRenderer<Subscript>() {
                    @Override
                    public void render(final Subscript node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<FencedCodeBlock>(FencedCodeBlock.class, new CustomNodeDocxRenderer<FencedCodeBlock>() {
                    @Override
                    public void render(final FencedCodeBlock node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<HardLineBreak>(HardLineBreak.class, new CustomNodeDocxRenderer<HardLineBreak>() {
                    @Override
                    public void render(final HardLineBreak node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<Heading>(Heading.class, new CustomNodeDocxRenderer<Heading>() {
                    @Override
                    public void render(final Heading node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<HtmlBlock>(HtmlBlock.class, new CustomNodeDocxRenderer<HtmlBlock>() {
                    @Override
                    public void render(final HtmlBlock node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<HtmlCommentBlock>(HtmlCommentBlock.class, new CustomNodeDocxRenderer<HtmlCommentBlock>() {
                    @Override
                    public void render(final HtmlCommentBlock node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<HtmlInnerBlock>(HtmlInnerBlock.class, new CustomNodeDocxRenderer<HtmlInnerBlock>() {
                    @Override
                    public void render(final HtmlInnerBlock node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<HtmlInnerBlockComment>(HtmlInnerBlockComment.class, new CustomNodeDocxRenderer<HtmlInnerBlockComment>() {
                    @Override
                    public void render(final HtmlInnerBlockComment node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<HtmlEntity>(HtmlEntity.class, new CustomNodeDocxRenderer<HtmlEntity>() {
                    @Override
                    public void render(final HtmlEntity node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<HtmlInline>(HtmlInline.class, new CustomNodeDocxRenderer<HtmlInline>() {
                    @Override
                    public void render(final HtmlInline node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<HtmlInlineComment>(HtmlInlineComment.class, new CustomNodeDocxRenderer<HtmlInlineComment>() {
                    @Override
                    public void render(final HtmlInlineComment node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<Image>(Image.class, new CustomNodeDocxRenderer<Image>() {
                    @Override
                    public void render(final Image node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<ImageRef>(ImageRef.class, new CustomNodeDocxRenderer<ImageRef>() {
                    @Override
                    public void render(final ImageRef node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<IndentedCodeBlock>(IndentedCodeBlock.class, new CustomNodeDocxRenderer<IndentedCodeBlock>() {
                    @Override
                    public void render(final IndentedCodeBlock node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<Link>(Link.class, new CustomNodeDocxRenderer<Link>() {
                    @Override
                    public void render(final Link node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<LinkRef>(LinkRef.class, new CustomNodeDocxRenderer<LinkRef>() {
                    @Override
                    public void render(final LinkRef node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<BulletList>(BulletList.class, new CustomNodeDocxRenderer<BulletList>() {
                    @Override
                    public void render(final BulletList node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<OrderedList>(OrderedList.class, new CustomNodeDocxRenderer<OrderedList>() {
                    @Override
                    public void render(final OrderedList node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<BulletListItem>(BulletListItem.class, new CustomNodeDocxRenderer<BulletListItem>() {
                    @Override
                    public void render(final BulletListItem node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<OrderedListItem>(OrderedListItem.class, new CustomNodeDocxRenderer<OrderedListItem>() {
                    @Override
                    public void render(final OrderedListItem node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<MailLink>(MailLink.class, new CustomNodeDocxRenderer<MailLink>() {
                    @Override
                    public void render(final MailLink node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<Paragraph>(Paragraph.class, new CustomNodeDocxRenderer<Paragraph>() {
                    @Override
                    public void render(final Paragraph node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<Reference>(Reference.class, new CustomNodeDocxRenderer<Reference>() {
                    @Override
                    public void render(final Reference node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<SoftLineBreak>(SoftLineBreak.class, new CustomNodeDocxRenderer<SoftLineBreak>() {
                    @Override
                    public void render(final SoftLineBreak node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<StrongEmphasis>(StrongEmphasis.class, new CustomNodeDocxRenderer<StrongEmphasis>() {
                    @Override
                    public void render(final StrongEmphasis node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<Text>(Text.class, new CustomNodeDocxRenderer<Text>() {
                    @Override
                    public void render(final Text node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<TextBase>(TextBase.class, new CustomNodeDocxRenderer<TextBase>() {
                    @Override
                    public void render(final TextBase node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<ThematicBreak>(ThematicBreak.class, new CustomNodeDocxRenderer<ThematicBreak>() {
                    @Override
                    public void render(final ThematicBreak node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<TableBlock>(TableBlock.class, new CustomNodeDocxRenderer<TableBlock>() {
                    @Override
                    public void render(TableBlock node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<TableHead>(TableHead.class, new CustomNodeDocxRenderer<TableHead>() {
                    @Override
                    public void render(TableHead node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<TableSeparator>(TableSeparator.class, new CustomNodeDocxRenderer<TableSeparator>() {
                    @Override
                    public void render(TableSeparator node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<TableBody>(TableBody.class, new CustomNodeDocxRenderer<TableBody>() {
                    @Override
                    public void render(TableBody node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<TableRow>(TableRow.class, new CustomNodeDocxRenderer<TableRow>() {
                    @Override
                    public void render(TableRow node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<TableCell>(TableCell.class, new CustomNodeDocxRenderer<TableCell>() {
                    @Override
                    public void render(TableCell node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<TableCaption>(TableCaption.class, new CustomNodeDocxRenderer<TableCaption>() {
                    @Override
                    public void render(TableCaption node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                })
        ));
    }

    private void render(final Node node, final DocxRendererContext docx) {
        BasedSequence chars = node.getChars();
        MainDocumentPart mdp = docx.getDocxDocument();
        if (node instanceof Block) {
            final ValueRunnable<PPr> initializer = new ValueRunnable<PPr>() {
                @Override
                public void run(final PPr value) {
                    // Create object for rPr
                    ParaRPr pararpr = value.getRPr();
                    if (pararpr == null) {
                        pararpr = docx.getObjectFactory().createParaRPr();
                        value.setRPr(pararpr);
                    }

                    // Create object for pStyle if one does not already exist
                    if (value.getPStyle() == null) {
                        PPrBase.PStyle basePStyle = docx.getObjectFactory().createPPrBasePStyle();
                        value.setPStyle(basePStyle);
                        basePStyle.setVal(LOOSE_PARAGRAPH_STYLE);
                    }
                }
            };

            docx.pushPPrInitializer(initializer);
            docx.createP();
            docx.renderChildren(node);
            docx.popPPrInitializer(initializer);
        } else {
            docx.text(chars.unescape());
        }
    }

    private void render(final BlankLine node, final DocxRendererContext docx) {
        // not rendered
    }

    private void render(final Document node, final DocxRendererContext docx) {
        // No rendering itself
        docx.renderChildren(node);
    }

    private void render(final Paragraph node, final DocxRendererContext docx) {
        if (!(node.getParent() instanceof ParagraphItemContainer)) {
            //if (!node.isTrailingBlankLine() && (node.getNext() == null || node.getNext() instanceof ListBlock)) {
            //    renderTextBlockParagraphLines(node, docx, TIGHT_PARAGRAPH_STYLE);
            //} else {
            //    renderLooseParagraph(node, docx);
            //}
            renderLooseParagraph(node, docx);
        } else {
            boolean isItemParagraph = ((ParagraphItemContainer) node.getParent()).isItemParagraph(node);
            if (isItemParagraph) {
                ListSpacing itemSpacing = docx.getDocument().get(LIST_ITEM_SPACING);
                if (itemSpacing == ListSpacing.TIGHT) {
                    renderTextBlockParagraphLines(node, docx, TIGHT_PARAGRAPH_STYLE);
                } else if (itemSpacing == ListSpacing.LOOSE) {
                    if (node.getParent().getNextAnyNot(BlankLine.class) == null) {
                        renderTextBlockParagraphLines(node, docx, TIGHT_PARAGRAPH_STYLE);
                    } else {
                        renderLooseParagraph(node, docx);
                    }
                } else {
                    if (!((ParagraphItemContainer) node.getParent()).isParagraphWrappingDisabled(node, listOptions, docx.getOptions())) {
                        renderLooseParagraph(node, docx);
                    } else {
                        renderTextBlockParagraphLines(node, docx, TIGHT_PARAGRAPH_STYLE);
                    }
                }
            } else {
                renderLooseParagraph(node, docx);
            }
        }
    }

    private void render(final Text node, final DocxRendererContext docx) {
        docx.text(node.getChars().unescape());
    }

    private void render(final TextBase node, final DocxRendererContext docx) {
        docx.renderChildren(node);
    }

    private void render(final Emphasis node, final DocxRendererContext docx) {
        final ValueRunnable<RPr> initializer = new ValueRunnable<RPr>() {
            @Override
            public void run(final RPr value) {
                value.setI(docx.getBooleanDefaultTrue());
                value.setICs(docx.getBooleanDefaultTrue());
            }
        };
        docx.pushRPrInitializer(initializer);
        docx.renderChildren(node);
        docx.popRPrInitializer(initializer);
    }

    private void render(final StrongEmphasis node, final DocxRendererContext docx) {
        final ValueRunnable<RPr> initializer = new ValueRunnable<RPr>() {
            @Override
            public void run(final RPr value) {
                value.setB(docx.getBooleanDefaultTrue());
                value.setBCs(docx.getBooleanDefaultTrue());
            }
        };
        docx.pushRPrInitializer(initializer);
        docx.renderChildren(node);
        docx.popRPrInitializer(initializer);
    }

    private void render(final Subscript node, final DocxRendererContext docx) {
        final ValueRunnable<RPr> initializer = new ValueRunnable<RPr>() {
            @Override
            public void run(final RPr value) {
                // Create object for sz
                HpsMeasure hpsmeasure = docx.getObjectFactory().createHpsMeasure();
                value.setSz(hpsmeasure);
                hpsmeasure.setVal(BigInteger.valueOf(19));

                // Create object for position
                CTSignedHpsMeasure signedhpsmeasure = docx.getObjectFactory().createCTSignedHpsMeasure();
                value.setPosition(signedhpsmeasure);
                signedhpsmeasure.setVal(BigInteger.valueOf(-4));
            }
        };
        docx.pushRPrInitializer(initializer);
        docx.renderChildren(node);
        docx.popRPrInitializer(initializer);
    }

    private void render(final Superscript node, final DocxRendererContext docx) {
        final ValueRunnable<RPr> initializer = new ValueRunnable<RPr>() {
            @Override
            public void run(final RPr value) {
                // Create object for sz
                HpsMeasure hpsmeasure = docx.getObjectFactory().createHpsMeasure();
                value.setSz(hpsmeasure);
                hpsmeasure.setVal(BigInteger.valueOf(19));

                // Create object for position
                CTSignedHpsMeasure signedhpsmeasure = docx.getObjectFactory().createCTSignedHpsMeasure();
                value.setPosition(signedhpsmeasure);
                signedhpsmeasure.setVal(BigInteger.valueOf(8));
            }
        };
        docx.pushRPrInitializer(initializer);
        docx.renderChildren(node);
        docx.popRPrInitializer(initializer);
    }

    private void render(final Strikethrough node, final DocxRendererContext docx) {
        final ValueRunnable<RPr> initializer = new ValueRunnable<RPr>() {
            @Override
            public void run(final RPr value) {
                value.setStrike(docx.getObjectFactory().createBooleanDefaultTrue());
            }
        };
        docx.pushRPrInitializer(initializer);
        docx.renderChildren(node);
        docx.popRPrInitializer(initializer);
    }

    private void render(final Ins node, final DocxRendererContext docx) {
        final ValueRunnable<RPr> initializer = new ValueRunnable<RPr>() {
            @Override
            public void run(final RPr value) {
                U u = docx.getObjectFactory().createU();
                value.setU(u);
                u.setVal(org.docx4j.wml.UnderlineEnumeration.SINGLE);
            }
        };
        docx.pushRPrInitializer(initializer);
        docx.renderChildren(node);
        docx.popRPrInitializer(initializer);
    }

    private void render(final Code node, final DocxRendererContext docx) {
        final ValueRunnable<RPr> initializer = new ValueRunnable<RPr>() {
            @Override
            public void run(final RPr value) {
                // Create object for rStyle
                RStyle rstyle = docx.getObjectFactory().createRStyle();
                value.setRStyle(rstyle);
                rstyle.setVal(INLINE_CODE_STYLE);
            }
        };
        docx.pushRPrInitializer(initializer);
        docx.renderChildren(node);
        docx.popRPrInitializer(initializer);
    }

    private void render(final Heading node, final DocxRendererContext docx) {
        P p = docx.createP();
        PPr ppr = p.getPPr();

        // Create object for rPr
        ParaRPr paraRPr = docx.getObjectFactory().createParaRPr();
        ppr.setRPr(paraRPr);

        // Create object for numPr
        PPrBase.NumPr baseNumPr = docx.getObjectFactory().createPPrBaseNumPr();
        ppr.setNumPr(baseNumPr);

        // Create object for numId
        PPrBase.NumPr.NumId prNumId = docx.getObjectFactory().createPPrBaseNumPrNumId();
        baseNumPr.setNumId(prNumId);
        prNumId.setVal(BigInteger.valueOf(0));

        // Create object for ilvl
        PPrBase.NumPr.Ilvl prIlvl = docx.getObjectFactory().createPPrBaseNumPrIlvl();
        baseNumPr.setIlvl(prIlvl);
        prIlvl.setVal(BigInteger.valueOf(node.getLevel() - 1));

        // Create object for pStyle
        PPrBase.PStyle basePStyle = docx.getObjectFactory().createPPrBasePStyle();
        ppr.setPStyle(basePStyle);

        final String styleId = String.format("Heading%d", node.getLevel());
        basePStyle.setVal(styleId);

        docx.renderChildren(node);
    }

    @SuppressWarnings("WeakerAccess")
    public static void renderTextBlockParagraphLines(final Paragraph node, final DocxRendererContext docx, final String styleName) {
        final boolean isListItemChild = node.getParent() instanceof ListItem && !((ListItem) node.getParent()).isItemParagraph((Paragraph) node);
        final int idNum = node.getParent() instanceof OrderedListItem ? 3 : 2;
        final int nesting = node.countAncestorsOfType(BulletList.class, OrderedList.class);

        final ValueRunnable<PPr> initializer = new ValueRunnable<PPr>() {
            @Override
            public void run(final PPr value) {
                ParaRPr pararpr = value.getRPr();
                if (pararpr == null) {
                    // Create object for rPr
                    pararpr = docx.getObjectFactory().createParaRPr();
                    value.setRPr(pararpr);
                }

                if (value.getPStyle() == null) {
                    // Create object for pStyle if one does not already exist
                    PPrBase.PStyle basePStyle = docx.getObjectFactory().createPPrBasePStyle();
                    value.setPStyle(basePStyle);
                    basePStyle.setVal(styleName);
                } else {
                    // take the given styles before/after and set spacing on the PPr if we are not the first child
                    if (node.getParent().getFirstChild() != node) {
                        Style style = docx.getStyle(styleName);
                        if (style != null) {
                            BigInteger before = safeSpacingBefore(style.getPPr());
                            BigInteger after = safeSpacingAfter(style.getPPr());

                            PPrBase.Spacing spacing = docx.getObjectFactory().createPPrBaseSpacing();
                            value.setSpacing(spacing);
                            spacing.setBefore(before);
                            spacing.setAfter(after);
                        }
                    }
                }

                if (isListItemChild) {
                    // need to set indent
                    final PPrBase.Ind ind = docx.getDocxDocument().getNumberingDefinitionsPart().getInd(String.valueOf(idNum), String.valueOf(nesting - 1));
                    docx.setPPrIndent(value, ind.getLeft(), ind.getRight(), BigInteger.ZERO, DocxRendererContext.ADD_HANGING_TO_LEFT);
                }
            }
        };

        docx.pushPPrInitializer(initializer);
        docx.createP();
        docx.renderChildren(node);
        docx.popPPrInitializer(initializer);
    }

    @SuppressWarnings("WeakerAccess")
    public static void renderLooseParagraph(final Paragraph node, final DocxRendererContext docx) {
        renderTextBlockParagraphLines(node, docx, LOOSE_PARAGRAPH_STYLE);
    }

    private void render(final BlockQuote node, final DocxRendererContext docx) {
        final boolean isNested = node.getAncestorOfType(BlockQuote.class) != null;
        final BigInteger left;
        final BigInteger right;

        final Style paragraphStyle = docx.getStyle(BLOCK_QUOTE_STYLE);
        if (paragraphStyle != null) {
            // Should always be true
            left = safeAccess(safeInd(safePPr(paragraphStyle)), BigInteger.valueOf(240), new Function<PPrBase.Ind, BigInteger>() {
                @Override
                public BigInteger apply(final PPrBase.Ind ind) {
                    return ind.getLeft();
                }
            });
            right = safeAccess(safeInd(safePPr(paragraphStyle)), BigInteger.ZERO, new Function<PPrBase.Ind, BigInteger>() {
                @Override
                public BigInteger apply(final PPrBase.Ind ind) {
                    return ind.getRight();
                }
            });
        } else {
            left = BigInteger.valueOf(240);
            right = BigInteger.ZERO;
        }

        final ValueRunnable<PPr> prInitializer = new ValueRunnable<PPr>() {
            @Override
            public void run(final PPr value) {
                // Create object for rPr
                ParaRPr pararpr = value.getRPr();
                if (pararpr == null) {
                    pararpr = docx.getObjectFactory().createParaRPr();
                    value.setRPr(pararpr);
                }

                // Create object for pStyle
                PPrBase.PStyle basePStyle = docx.getObjectFactory().createPPrBasePStyle();
                value.setPStyle(basePStyle);
                basePStyle.setVal(BLOCK_QUOTE_STYLE);

                if (isNested) {
                    docx.setPPrIndent(value, left, right, BigInteger.ZERO, DocxRendererContext.ADD_LEFT_RIGHT);
                } else {
                    docx.setPPrIndent(value, BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO, DocxRendererContext.ADD_LEFT_RIGHT);
                }
            }
        };

        docx.pushPPrInitializer(prInitializer);
        docx.renderChildren(node);
        docx.popPPrInitializer(prInitializer);
    }

    private void render(final ThematicBreak node, final DocxRendererContext docx) {
        // Create object for p
        P p = docx.createP();

        // Create object for pPr
        PPr ppr = p.getPPr();

        // Create object for rPr
        ParaRPr paraRPr = docx.getObjectFactory().createParaRPr();
        ppr.setRPr(paraRPr);

        // Create object for pStyle
        PPrBase.PStyle basePStyle = docx.getObjectFactory().createPPrBasePStyle();
        ppr.setPStyle(basePStyle);
        basePStyle.setVal(HORIZONTAL_LINE_STYLE);

        // Create object for r
        R r = docx.createR();
    }

    private static <T, R> R safeAccess(T instance, R defValue, Function<T, R> access) {
        if (instance != null) {
            return access.apply(instance);
        }
        return defValue;
    }

    private static PPr safePPr(Style style) {
        return style == null ? null : style.getPPr();
    }

    private static PPrBase.Spacing safeSpacing(PPr ppr) {
        return ppr == null ? null : ppr.getSpacing();
    }

    private static BigInteger safeSpacingBefore(PPrBase.Spacing spacing) {
        return safeSpacingAfter(spacing, null);
    }

    private static BigInteger safeSpacingBefore(PPrBase.Spacing spacing, BigInteger defValue) {
        if (spacing != null) {
            BigInteger value = spacing.getBefore();
            if (value != null) {
                return value;
            }
        }
        return defValue;
    }

    private static BigInteger safeSpacingAfter(PPrBase.Spacing spacing) {
        return safeSpacingAfter(spacing, null);
    }

    private static BigInteger safeSpacingAfter(PPrBase.Spacing spacing, BigInteger defValue) {
        if (spacing != null) {
            BigInteger value = spacing.getAfter();
            if (value != null) {
                return value;
            }
        }
        return defValue;
    }

    private static BigInteger safeSpacingBefore(PPr pPr) {
        return pPr == null ? null : safeSpacingBefore(pPr.getSpacing(), null);
    }

    private static BigInteger safeSpacingBefore(PPr pPr, BigInteger defValue) {
        return pPr == null ? defValue : safeSpacingBefore(pPr.getSpacing(), defValue);
    }

    private static BigInteger safeSpacingAfter(PPr pPr) {
        return pPr == null ? null : safeSpacingAfter(pPr.getSpacing(), null);
    }

    private static BigInteger safeSpacingAfter(PPr pPr, BigInteger defValue) {
        return pPr == null ? defValue : safeSpacingAfter(pPr.getSpacing(), defValue);
    }

    private static PPrBase.Ind safeInd(PPr ppr) {
        return ppr == null ? null : ppr.getInd();
    }

    private void wrapBeforeAfterSpacing(final DocxRendererContext docx, final String style, final boolean wrapOnce, final Runnable runnable) {
        final BigInteger before;
        final BigInteger after;

        final Style paragraphStyle = docx.getStyle(style);
        if (paragraphStyle != null) {
            // Should always be true
            before = safeSpacingBefore(paragraphStyle.getPPr());
            after = safeSpacingAfter(paragraphStyle.getPPr());
        } else {
            before = BigInteger.ZERO;
            after = BigInteger.ZERO;
        }

        final Runnable afterP = new Runnable() {
            @Override
            public void run() {
                // now add empty for spacing
                P p = docx.createP();
                PPr pPr = p.getPPr();

                // Create object for pStyle
                PPrBase.PStyle basePStyle = docx.getObjectFactory().createPPrBasePStyle();
                pPr.setPStyle(basePStyle);
                basePStyle.setVal(TIGHT_PARAGRAPH_STYLE);

                ParaRPr paraRPr = docx.getObjectFactory().createParaRPr();
                pPr.setRPr(paraRPr);

                // Create Spacing
                PPrBase.Spacing spacing = docx.getObjectFactory().createPPrBaseSpacing();
                pPr.setSpacing(spacing);

                spacing.setBefore(BigInteger.ZERO);
                spacing.setAfter(BigInteger.ZERO);
                spacing.setLine(after);
                spacing.setLineRule(STLineSpacingRule.EXACT);

                R r = docx.createR();
                RPr rPr = r.getRPr();

                if (!wrapOnce) {
                    docx.clearAfterP(this);
                }
            }
        };

        final Runnable beforeP = new Runnable() {
            @Override
            public void run() {
                if (before.compareTo(BigInteger.ZERO) > 0) {
                    // now add empty for spacing
                    P p = docx.createP();
                    PPr pPr = p.getPPr();

                    // Create object for pStyle
                    PPrBase.PStyle basePStyle = docx.getObjectFactory().createPPrBasePStyle();
                    pPr.setPStyle(basePStyle);
                    basePStyle.setVal(TIGHT_PARAGRAPH_STYLE);

                    ParaRPr paraRPr = docx.getObjectFactory().createParaRPr();
                    pPr.setRPr(paraRPr);

                    // Create Spacing
                    PPrBase.Spacing spacing = docx.getObjectFactory().createPPrBaseSpacing();
                    pPr.setSpacing(spacing);

                    spacing.setBefore(BigInteger.ZERO);
                    spacing.setAfter(BigInteger.ZERO);
                    spacing.setLine(before);
                    spacing.setLineRule(STLineSpacingRule.EXACT);

                    R r = docx.createR();
                    RPr rPr = r.getRPr();
                }

                if (!wrapOnce) {
                    docx.clearBeforeP(this);

                    if (after.compareTo(BigInteger.ZERO) > 0) {
                        docx.setAfterP(afterP);
                    }
                }
            }
        };

        if (wrapOnce) {
            beforeP.run();
        } else {
            docx.setBeforeP(beforeP);
        }

        final ValueRunnable<PPr> pInitializer = new ValueRunnable<PPr>() {
            @Override
            public void run(final PPr value) {
                ParaRPr paraRPr = value.getRPr();
                if (paraRPr == null) {
                    // Create object for rPr
                    paraRPr = docx.getObjectFactory().createParaRPr();
                    value.setRPr(paraRPr);
                }

                PPrBase.Spacing spacing = value.getSpacing();
                if (spacing == null) {
                    // Create Spacing
                    spacing = docx.getObjectFactory().createPPrBaseSpacing();
                    value.setSpacing(spacing);
                }

                spacing.setBefore(BigInteger.ZERO);
                spacing.setAfter(BigInteger.ZERO);

                // Create object for pStyle
                PPrBase.PStyle basePStyle = docx.getObjectFactory().createPPrBasePStyle();
                value.setPStyle(basePStyle);
                basePStyle.setVal(style);
            }
        };

        docx.pushPPrInitializer(pInitializer);
        runnable.run();
        docx.popPPrInitializer(pInitializer);

        if (docx.getBeforeP() == before) {
            // did not run, remove it
            docx.setBeforeP(beforeP);
        }

        if (wrapOnce && after.compareTo(BigInteger.ZERO) > 0) {
            afterP.run();
        }
    }

    private void renderCodeLines(final DocxRendererContext docx, final List<BasedSequence> lines) {
        wrapBeforeAfterSpacing(docx, PREFORMATTED_TEXT_STYLE, true, new Runnable() {
            @Override
            public void run() {
                int[] leadColumns = new int[lines.size()];
                int minSpaces = Integer.MAX_VALUE;
                int i = 0;
                for (BasedSequence line : lines) {
                    leadColumns[i] = line.countLeadingColumns(0, " \t");
                    minSpaces = Utils.min(minSpaces, leadColumns[i]);
                    i++;
                }

                ArrayList<BasedSequence> trimmedLines = new ArrayList<BasedSequence>();
                i = 0;
                for (BasedSequence line : lines) {
                    StringBuilder sb = new StringBuilder();

                    int spaces = leadColumns[i] - minSpaces;
                    while (spaces-- > 0) sb.append(' ');
                    sb.append(line.trim());

                    // Create object for p
                    P p = docx.createP();
                    docx.text(sb.toString());

                    i++;
                }
            }
        });
    }

    private void render(final FencedCodeBlock node, final DocxRendererContext docx) {
        List<BasedSequence> lines = node.getContentLines();
        renderCodeLines(docx, lines);
    }

    private void render(final IndentedCodeBlock node, final DocxRendererContext docx) {
        List<BasedSequence> lines = node.getContentLines();
        renderCodeLines(docx, lines);
    }

    public void renderList(final ListBlock node, final DocxRendererContext docx) {
        if (node.countAncestorsOfType(BulletList.class, OrderedList.class) == 0) {
            // bump up the num id for lists
            listNumId++;
        }
        docx.renderChildren(node);
    }

    private void render(final BulletList node, final DocxRendererContext docx) {
        renderList(node, docx);
    }

    private void render(final OrderedList node, final DocxRendererContext docx) {
        renderList(node, docx);
    }

    private void render(final BulletListItem node, final DocxRendererContext docx) {
        renderListItem(node, docx);
    }

    private void render(final OrderedListItem node, final DocxRendererContext docx) {
        renderListItem(node, docx);
    }

    private void renderListItem(final ListItem node, final DocxRendererContext docx) {
        final boolean[] firstP = new boolean[] { true };
        final int idNum = node instanceof OrderedListItem ? 3 : 2;
        final int nesting = node.countAncestorsOfType(BulletList.class, OrderedList.class);

        final ValueRunnable<PPr> pInitializer = new ValueRunnable<PPr>() {
            @Override
            public void run(final PPr value) {
                // Create object for rPr
                ParaRPr pararpr = value.getRPr();
                if (pararpr == null) {
                    pararpr = docx.getObjectFactory().createParaRPr();
                    value.setRPr(pararpr);
                }

                // Create object for pStyle
                PPrBase.PStyle basePStyle = docx.getObjectFactory().createPPrBasePStyle();
                value.setPStyle(basePStyle);
                basePStyle.setVal(listOptions.isTightListItem(node) ? TIGHT_PARAGRAPH_STYLE : LOOSE_PARAGRAPH_STYLE);

                if (firstP[0]) {
                    firstP[0] = false;

                    // Create object for numPr
                    PPrBase.NumPr numPr = docx.getObjectFactory().createPPrBaseNumPr();
                    value.setNumPr(numPr);

                    // Create object for numId
                    PPrBase.NumPr.NumId numId = docx.getObjectFactory().createPPrBaseNumPrNumId();
                    numPr.setNumId(numId);
                    numId.setVal(BigInteger.valueOf(idNum)); //listNumId));

                    // Create object for ilvl
                    PPrBase.NumPr.Ilvl ilvl = docx.getObjectFactory().createPPrBaseNumPrIlvl();
                    numPr.setIlvl(ilvl);
                    ilvl.setVal(BigInteger.valueOf(nesting - 1));
                }
            }
        };

        docx.pushPPrInitializer(pInitializer);
        docx.renderChildren(node);
        docx.popPPrInitializer(pInitializer);
    }

    private void render(final SoftLineBreak node, final DocxRendererContext docx) {

    }

    private void render(final HardLineBreak node, final DocxRendererContext docx) {
        P p = docx.createP();
        R r = docx.createR();
    }

    private void render(HtmlBlock node, final DocxRendererContext docx) {
        if (node.hasChildren()) {
            // inner blocks handle rendering
            docx.renderChildren(node);
        } else {
            renderHtmlBlock(node, docx, docx.getDocxRendererOptions().suppressHtmlBlocks, true || docx.getDocxRendererOptions().escapeHtmlBlocks);
        }
    }

    private void render(HtmlCommentBlock node, final DocxRendererContext docx) {
        renderHtmlBlock(node, docx, docx.getDocxRendererOptions().suppressHtmlCommentBlocks, true || docx.getDocxRendererOptions().escapeHtmlCommentBlocks);
    }

    private void render(HtmlInnerBlock node, final DocxRendererContext docx) {
        renderHtmlBlock(node, docx, docx.getDocxRendererOptions().suppressHtmlBlocks, true || docx.getDocxRendererOptions().escapeHtmlBlocks);
    }

    private void render(HtmlInnerBlockComment node, final DocxRendererContext docx) {
        renderHtmlBlock(node, docx, docx.getDocxRendererOptions().suppressHtmlCommentBlocks, true || docx.getDocxRendererOptions().escapeHtmlCommentBlocks);
    }

    public void renderHtmlBlock(final HtmlBlockBase node, final DocxRendererContext docx, boolean suppress, boolean escape) {
        if (suppress) return;

        if (escape) {
            String normalizeEOL = node.getChars().normalizeEOL();

            if (node instanceof HtmlBlock) {
                if (normalizeEOL.length() > 0 && normalizeEOL.charAt(normalizeEOL.length() - 1) == '\n') {
                    // leave off the trailing EOL
                    normalizeEOL = normalizeEOL.substring(0, normalizeEOL.length() - 1);
                }
            }

            P p = docx.createP();
            PPr ppr = p.getPPr();

            // Create object for rPr
            ParaRPr pararpr = docx.getObjectFactory().createParaRPr();
            ppr.setRPr(pararpr);

            // Create object for pStyle
            PPrBase.PStyle pprbasepstyle = docx.getObjectFactory().createPPrBasePStyle();
            ppr.setPStyle(pprbasepstyle);
            pprbasepstyle.setVal(LOOSE_PARAGRAPH_STYLE);

            docx.text(normalizeEOL);
        } else {
            try {
                docx.getDocxDocument().addAltChunk(AltChunkType.Html, node.getChars().toString().getBytes(Charset.forName("UTF-8")));
            } catch (Docx4JException e) {
                e.printStackTrace();
            }
        }
    }

    private void render(final HtmlInline node, final DocxRendererContext docx) {
        //if (docx.getDocxRendererOptions().sourceWrapInlineHtml) {
        //    html.srcPos(node.getChars()).withAttr(AttributablePart.NODE_POSITION).tag("span");
        //}
        renderInlineHtml(node, docx, docx.getDocxRendererOptions().suppressInlineHtml, true || docx.getDocxRendererOptions().escapeInlineHtml);
        //if (docx.getDocxRendererOptions().sourceWrapInlineHtml) {
        //    html.tag("/span");
        //}
    }

    private void render(final HtmlInlineComment node, final DocxRendererContext docx) {
        renderInlineHtml(node, docx, docx.getDocxRendererOptions().suppressInlineHtmlComments, true || docx.getDocxRendererOptions().escapeInlineHtmlComments);
    }

    public void renderInlineHtml(final HtmlInlineBase node, final DocxRendererContext docx, boolean suppress, boolean escape) {
        if (suppress) return;

        if (escape) {
            docx.text(node.getChars().normalizeEOL());
        } else {
            try {
                docx.getDocxDocument().addAltChunk(AltChunkType.Html, node.getChars().toString().getBytes());
            } catch (Docx4JException e) {
                e.printStackTrace();
            }
        }
    }

    private void render(final Reference node, final DocxRendererContext docx) {

    }

    private void render(final HtmlEntity node, final DocxRendererContext docx) {
        docx.text(node.getChars().unescape());
    }

    private void renderURL(final DocxRendererContext docx, final String linkUrl) {
        renderURL(docx, linkUrl, linkUrl);
    }

    private void renderURL(final DocxRendererContext docx, final String linkUrl, final String linkText) {
        renderURL(docx, linkUrl, null, new Runnable() {
            @Override
            public void run() {
                // Create object for r
                docx.text(linkText == null ? linkUrl : linkText);
            }
        });
    }

    private void renderURL(final DocxRendererContext docx, final String linkUrl, final String linkTitle, final Runnable runnable) {
        P p = docx.getP();
        Relationship rel = docx.getHyperlinkRelationship(linkUrl);

        // Create object for hyperlink (wrapped in JAXBElement)
        final P.Hyperlink hyperlink = docx.getObjectFactory().createPHyperlink();
        JAXBElement<P.Hyperlink> wrappedHyperlink = docx.getObjectFactory().createPHyperlink(hyperlink);
        p.getContent().add(wrappedHyperlink);

        hyperlink.setId(rel.getId());

        final ValueRunnable<RPr> initializer = new ValueRunnable<RPr>() {
            @Override
            public void run(final RPr value) {
                // Create object for rStyle
                RStyle rstyle = docx.getObjectFactory().createRStyle();
                value.setRStyle(rstyle);
                rstyle.setVal(HYPERLINK_STYLE);
            }
        };
        docx.pushRPrInitializer(initializer);

        final ValueRunnable<R> adopter = new ValueRunnable<R>() {
            @Override
            public void run(final R value) {
                hyperlink.getContent().add(value);
            }
        };

        docx.setAdopterR(adopter);

        if (linkTitle != null && !linkTitle.isEmpty()) {
            // Create object for instrText (wrapped in JAXBElement)
            // Create object for r
            R r = docx.getObjectFactory().createR();
            hyperlink.getContent().add(r);

            // Create object for fldChar (wrapped in JAXBElement)
            FldChar fldchar = docx.getObjectFactory().createFldChar();
            JAXBElement<org.docx4j.wml.FldChar> fldcharWrapped = docx.getObjectFactory().createRFldChar(fldchar);
            r.getContent().add(fldcharWrapped);
            fldchar.setFldCharType(org.docx4j.wml.STFldCharType.BEGIN);

            // Create object for r
            R r2 = docx.getObjectFactory().createR();
            hyperlink.getContent().add(r2);

            // Create object for instrText (wrapped in JAXBElement)
            org.docx4j.wml.Text text = docx.getObjectFactory().createText();
            JAXBElement<org.docx4j.wml.Text> textWrapped = docx.getObjectFactory().createRInstrText(text);
            r2.getContent().add(textWrapped);
            text.setValue(String.format(" HYPERLINK \"%s\" \\o \"%s\" ", linkUrl, linkTitle));
            text.setSpace("preserve");

            // Create object for r
            R r3 = docx.getObjectFactory().createR();
            hyperlink.getContent().add(r3);

            // Create object for fldChar (wrapped in JAXBElement)
            FldChar fldchar2 = docx.getObjectFactory().createFldChar();
            JAXBElement<org.docx4j.wml.FldChar> fldcharWrapped2 = docx.getObjectFactory().createRFldChar(fldchar2);
            r3.getContent().add(fldcharWrapped2);
            fldchar2.setFldCharType(org.docx4j.wml.STFldCharType.SEPARATE);
        }

        runnable.run();

        if (linkTitle != null && !linkTitle.isEmpty()) {
            // Create object for r
            R r3 = docx.getObjectFactory().createR();
            hyperlink.getContent().add(r3);

            // Create object for fldChar (wrapped in JAXBElement)
            FldChar fldchar2 = docx.getObjectFactory().createFldChar();
            JAXBElement<org.docx4j.wml.FldChar> fldcharWrapped2 = docx.getObjectFactory().createRFldChar(fldchar2);
            r3.getContent().add(fldcharWrapped2);
            fldchar2.setFldCharType(STFldCharType.END);
        }
        docx.clearAdopterR(adopter);
        docx.popRPrInitializer(initializer);
    }

    private void render(final AutoLink node, final DocxRendererContext docx) {
        final String url = node.getChars().unescape();
        renderURL(docx, url);
    }

    private void render(final MailLink node, final DocxRendererContext docx) {
        renderURL(docx, "mailto:" + node.getChars().unescape());
    }

    private void render(final Link node, final DocxRendererContext docx) {
        ResolvedLink resolvedLink = docx.resolveLink(LinkType.LINK, node.getUrl().unescape(), null, null);

        // we have a title part, use that
        if (node.getTitle().isNotNull()) {
            resolvedLink.getNonNullAttributes().replaceValue(Attribute.TITLE_ATTR, node.getTitle().unescape());
        } else {
            resolvedLink.getNonNullAttributes().remove(Attribute.TITLE_ATTR);
        }

        renderURL(docx, resolvedLink.getUrl(), resolvedLink.getTitle(), new Runnable() {
            @Override
            public void run() {
                docx.renderChildren(node);
            }
        });
    }

    private void render(final LinkRef node, final DocxRendererContext docx) {
        ResolvedLink resolvedLink = null;

        if (!node.isDefined() && recheckUndefinedReferences) {
            if (node.getReferenceNode(referenceRepository) != null) {
                node.setDefined(true);
            }
        }

        if (node.isDefined()) {
            Reference reference = node.getReferenceNode(referenceRepository);
            String url = reference.getUrl().unescape();

            resolvedLink = docx.resolveLink(LinkType.LINK, url, null, null);
            if (reference.getTitle().isNotNull()) {
                resolvedLink.getNonNullAttributes().replaceValue(Attribute.TITLE_ATTR, reference.getTitle().unescape());
            } else {
                resolvedLink.getNonNullAttributes().remove(Attribute.TITLE_ATTR);
            }
        } else {
            // see if have reference resolver and this is resolved
            String normalizeRef = node.getReference().unescape();
            resolvedLink = docx.resolveLink(LinkType.LINK_REF, normalizeRef, null, null);
            if (resolvedLink.getStatus() == UNKNOWN) {
                resolvedLink = null;
            }
        }

        if (resolvedLink == null) {
            // empty ref, we treat it as text
            assert !node.isDefined();
            if (!node.hasChildren()) {
                docx.text(node.getChars().unescape());
            } else {
                docx.text(node.getChars().prefixOf(node.getChildChars()).unescape());
                docx.renderChildren(node);
                docx.text(node.getChars().suffixOf(node.getChildChars()).unescape());
            }
        } else {
            renderURL(docx, resolvedLink.getUrl(), resolvedLink.getTitle(), new Runnable() {
                @Override
                public void run() {
                    docx.renderChildren(node);
                }
            });
        }
    }

    public void newImage(final DocxRendererContext docx, byte[] bytes, String filenameHint, String altText, int id1, int id2, long cx) {
        try {
            BinaryPartAbstractImage imagePart = null;
            imagePart = BinaryPartAbstractImage.createImagePart(docx.getWordprocessingPackage(), bytes);
            Inline inline = null;
            inline = cx > 0 ? imagePart.createImageInline(filenameHint, altText, id1, id2, cx, false)
                    : imagePart.createImageInline(filenameHint, altText, id1, id2, false);

            // Now add the inline in w:p/w:r/w:drawing
            org.docx4j.wml.R run = docx.createR();
            org.docx4j.wml.Drawing drawing = docx.getObjectFactory().createDrawing();
            run.getContent().add(drawing);
            drawing.getAnchorOrInline().add(inline);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void render(final Image node, final DocxRendererContext docx) {
        String altText = new TextCollectingVisitor().collectAndGetText(node);
        ResolvedLink resolvedLink = docx.resolveLink(LinkType.IMAGE, node.getUrl().unescape(), null, null);
        String url = resolvedLink.getUrl();

        if (!node.getUrlContent().isEmpty()) {
            // reverse URL encoding of =, &
            String content = Escaping.percentEncodeUrl(node.getUrlContent()).replace("+", "%2B").replace("%3D", "=").replace("%26", "&amp;");
            url += content;
        }

        String alt = node.getText().unescape();

        renderImage(docx, altText, url);
    }

    private void render(final ImageRef node, final DocxRendererContext docx) {
        ResolvedLink resolvedLink = null;

        if (!node.isDefined() && recheckUndefinedReferences) {
            if (node.getReferenceNode(referenceRepository) != null) {
                node.setDefined(true);
            }
        }

        if (node.isDefined()) {
            Reference reference = node.getReferenceNode(referenceRepository);
            String url = reference.getUrl().unescape();

            resolvedLink = docx.resolveLink(LinkType.IMAGE, url, null, null);
            if (reference.getTitle().isNotNull()) {
                resolvedLink.getNonNullAttributes().replaceValue(Attribute.TITLE_ATTR, reference.getTitle().unescape());
            } else {
                resolvedLink.getNonNullAttributes().remove(Attribute.TITLE_ATTR);
            }
        } else {
            // see if have reference resolver and this is resolved
            String normalizeRef = referenceRepository.normalizeKey(node.getReference());
            resolvedLink = docx.resolveLink(LinkType.IMAGE_REF, normalizeRef, null, null);
            if (resolvedLink.getStatus() == UNKNOWN) {
                resolvedLink = null;
            }
        }

        if (resolvedLink == null) {
            // empty ref, we treat it as text
            docx.text(node.getChars().unescape());
        } else {
            String altText = new TextCollectingVisitor().collectAndGetText(node);
            String url = resolvedLink.getUrl();
            renderImage(docx, url, altText);
        }
    }

    private void renderImage(final DocxRendererContext docx, final String altText, final String url) {
        BufferedImage image = null;
        int id1 = imageId++;
        int id2 = imageId++;
        String filenameHint = String.format("Image%d", id1 / 2 + 1);
        int cx = -1;

        if (url.startsWith("http:") || url.startsWith("https:")) {
            // hyperlinked image
            image = ImageUtils.loadImageFromURL(url);
        } else if (url.startsWith("file:")) {
            // hyperlinked image
            String path = url.substring("file:".length());
            if (path.startsWith("///")) {
                path = path.substring(2);
            } else if (path.startsWith("//")) {
                path = path.substring(1);
            }

            if (path.length() > 3 && path.charAt(0) == '/' && isLetter(path.charAt(1)) && path.charAt(2) == ':') {
                // windows path, remove the leading '/'
                path = path.substring(1);
            }

            image = ImageUtils.loadImageFromFile(new File(path));
        }

        if (image != null) {
            int width = image.getWidth();
            if (options.maxImageWidth > 0 && options.maxImageWidth < width) {
                // convert to twips
                cx = options.maxImageWidth * 20;
            }

            byte[] imageBytes = ImageUtils.getImageBytes(image);
            newImage(docx, imageBytes, filenameHint, altText, id1, id2, cx);
        }
    }

    private Tbl myTbl;
    private Tr myTr;

    private void render(final TableBlock node, final DocxRendererContext docx) {
        myTbl = docx.getObjectFactory().createTbl();
        JAXBElement<org.docx4j.wml.Tbl> tblWrapped = docx.getObjectFactory().createHdrTbl(myTbl);
        docx.getDocxDocument().getContent().add(tblWrapped);

        // Create object for tblPr
        TblPr tblpr = docx.getObjectFactory().createTblPr();
        myTbl.setTblPr(tblpr);

        // Create object for jc
        Jc jc = docx.getObjectFactory().createJc();
        tblpr.setJc(jc);
        jc.setVal(org.docx4j.wml.JcEnumeration.LEFT);

        // Create object for tblW
        TblWidth tblwidth = docx.getObjectFactory().createTblWidth();
        tblpr.setTblW(tblwidth);
        tblwidth.setType("auto");
        tblwidth.setW(BigInteger.valueOf(0));

        // Create object for tblInd
        TblWidth tblwidth2 = docx.getObjectFactory().createTblWidth();
        tblpr.setTblInd(tblwidth2);
        tblwidth2.setType("dxa");
        tblwidth2.setW(BigInteger.valueOf(30));
        // Create object for tblBorders
        TblBorders tblborders = docx.getObjectFactory().createTblBorders();
        tblpr.setTblBorders(tblborders);
        // Create object for left
        CTBorder border = docx.getObjectFactory().createCTBorder();
        tblborders.setLeft(border);
        border.setVal(org.docx4j.wml.STBorder.SINGLE);
        border.setSz(BigInteger.valueOf(2));
        border.setColor("000001");
        border.setSpace(BigInteger.valueOf(0));
        // Create object for right
        CTBorder border2 = docx.getObjectFactory().createCTBorder();
        tblborders.setRight(border2);
        border2.setVal(org.docx4j.wml.STBorder.SINGLE);
        border2.setSz(BigInteger.valueOf(2));
        border2.setColor("000001");
        border2.setSpace(BigInteger.valueOf(0));
        // Create object for top
        CTBorder border3 = docx.getObjectFactory().createCTBorder();
        tblborders.setTop(border3);
        border3.setVal(org.docx4j.wml.STBorder.SINGLE);
        border3.setSz(BigInteger.valueOf(2));
        border3.setColor("000001");
        border3.setSpace(BigInteger.valueOf(0));
        // Create object for bottom
        CTBorder border4 = docx.getObjectFactory().createCTBorder();
        tblborders.setBottom(border4);
        border4.setVal(org.docx4j.wml.STBorder.SINGLE);
        border4.setSz(BigInteger.valueOf(2));
        border4.setColor("000001");
        border4.setSpace(BigInteger.valueOf(0));
        // Create object for insideH
        CTBorder border5 = docx.getObjectFactory().createCTBorder();
        tblborders.setInsideH(border5);
        border5.setVal(org.docx4j.wml.STBorder.SINGLE);
        border5.setSz(BigInteger.valueOf(2));
        border5.setColor("000001");
        border5.setSpace(BigInteger.valueOf(0));
        // Create object for insideV
        CTBorder border6 = docx.getObjectFactory().createCTBorder();
        tblborders.setInsideV(border6);
        border6.setVal(org.docx4j.wml.STBorder.SINGLE);
        border6.setSz(BigInteger.valueOf(2));
        border6.setColor("000001");
        border6.setSpace(BigInteger.valueOf(0));

        // Create object for tblCellMar
        CTTblCellMar tblcellmar = docx.getObjectFactory().createCTTblCellMar();
        tblpr.setTblCellMar(tblcellmar);
        // Create object for left
        TblWidth tblwidth3 = docx.getObjectFactory().createTblWidth();
        tblcellmar.setLeft(tblwidth3);
        tblwidth3.setType("dxa");
        tblwidth3.setW(BigInteger.valueOf(80));
        // Create object for right
        TblWidth tblwidth4 = docx.getObjectFactory().createTblWidth();
        tblcellmar.setRight(tblwidth4);
        tblwidth4.setType("dxa");
        tblwidth4.setW(BigInteger.valueOf(80));
        // Create object for top
        TblWidth tblwidth5 = docx.getObjectFactory().createTblWidth();
        tblcellmar.setTop(tblwidth5);
        tblwidth5.setType("dxa");
        tblwidth5.setW(BigInteger.valueOf(80));
        // Create object for bottom
        TblWidth tblwidth6 = docx.getObjectFactory().createTblWidth();
        tblcellmar.setBottom(tblwidth6);
        tblwidth6.setType("dxa");
        tblwidth6.setW(BigInteger.valueOf(80));
        // Create object for tblLook
        CTTblLook tbllook = docx.getObjectFactory().createCTTblLook();
        tblpr.setTblLook(tbllook);
        tbllook.setVal("04a0");
        tbllook.setLastRow(org.docx4j.sharedtypes.STOnOff.ZERO);
        tbllook.setLastColumn(org.docx4j.sharedtypes.STOnOff.ZERO);
        tbllook.setNoHBand(org.docx4j.sharedtypes.STOnOff.ZERO);
        tbllook.setNoVBand(org.docx4j.sharedtypes.STOnOff.ONE);
        tbllook.setFirstRow(org.docx4j.sharedtypes.STOnOff.ONE);
        tbllook.setFirstColumn(org.docx4j.sharedtypes.STOnOff.ONE);

        docx.renderChildren(node);
        myTbl = null;
    }

    private void render(final TableHead node, final DocxRendererContext docx) {
        docx.renderChildren(node);
    }

    private void render(TableSeparator tableSeparator, final DocxRendererContext docx) {

    }

    private void render(final TableBody node, final DocxRendererContext docx) {
        docx.renderChildren(node);
    }

    private void render(final TableRow node, final DocxRendererContext docx) {
        myTr = docx.getObjectFactory().createTr();
        myTbl.getContent().add(myTr);

        // Create object for trPr
        TrPr trpr = docx.getObjectFactory().createTrPr();
        myTr.setTrPr(trpr);

        if (node.getParent() instanceof TableHead) {
            // Create object for tblHeader (wrapped in JAXBElement)
            BooleanDefaultTrue booleandefaulttrue = docx.getObjectFactory().createBooleanDefaultTrue();
            JAXBElement<org.docx4j.wml.BooleanDefaultTrue> booleandefaulttrueWrapped = docx.getObjectFactory().createCTTrPrBaseTblHeader(booleandefaulttrue);
            trpr.getCnfStyleOrDivIdOrGridBefore().add(booleandefaulttrueWrapped);
        }

        docx.renderChildren(node);
        myTr = null;
    }

    private void render(final TableCaption node, final DocxRendererContext docx) {
        // TODO: figure out how to set caption
        // table caption not yet supported by docx4j API
        //final TblPr tblPr = myTbl.getTblPr();
        //docx.getObjectFactory().createCTCaption();
    }

    private void render(TableCell node, final DocxRendererContext docx) {
        String style = node.isHeader() ? "TableHeading" : "TableContents";

        // Create object for tc (wrapped in JAXBElement)
        final Tc tc = docx.getObjectFactory().createTc();
        JAXBElement<org.docx4j.wml.Tc> tcWrapped = docx.getObjectFactory().createTrTc(tc);
        myTr.getContent().add(tcWrapped);
        // Create object for tcPr
        TcPr tcpr = docx.getObjectFactory().createTcPr();
        tc.setTcPr(tcpr);

        final ValueRunnable<P> adopter = new ValueRunnable<P>() {
            @Override
            public void run(final P value) {
                tc.getContent().add(value);
            }
        };

        docx.setAdopterP(adopter);

        if (node.getSpan() > 1) {
            // Create object for gridSpan
            TcPrInner.GridSpan tcprinnergridspan = docx.getObjectFactory().createTcPrInnerGridSpan();
            tcpr.setGridSpan(tcprinnergridspan);
            tcprinnergridspan.setVal(BigInteger.valueOf(node.getSpan()));
        }

        // Create object for p
        P p = docx.createP();
        PPr ppr = p.getPPr();

        // Create object for jc
        JcEnumeration alignValue = null;

        if (node.getAlignment() != null) {
            alignValue = getAlignValue(node.getAlignment());
        } else if (node.isHeader()) {
            alignValue = JcEnumeration.CENTER;
        }
        if (alignValue != null) {
            Jc jc3 = docx.getObjectFactory().createJc();
            ppr.setJc(jc3);
            jc3.setVal(alignValue);
        }

        // Create object for rPr
        ParaRPr pararpr = docx.getObjectFactory().createParaRPr();
        ppr.setRPr(pararpr);

        // Create object for pStyle
        PPrBase.PStyle pprbasepstyle = docx.getObjectFactory().createPPrBasePStyle();
        ppr.setPStyle(pprbasepstyle);
        pprbasepstyle.setVal(style);

        // Create object for r
        //R r = docx.getObjectFactory().createR();
        //p.getContent().add(r);
        //// Create object for rPr
        //RPr rpr = docx.getObjectFactory().createRPr();
        //r.setRPr(rpr);
        //
        //// Create object for t (wrapped in JAXBElement)
        //org.docx4j.wml.Text text = docx.getObjectFactory().createText();
        //JAXBElement<org.docx4j.wml.Text> textWrapped = docx.getObjectFactory().createRT(text);
        //r.getContent().add(textWrapped);
        //text.setValue("Combined header ");
        //text.setSpace("preserve");

        docx.renderChildren(node);
        docx.clearAdopterP(adopter);
    }

    private static org.docx4j.wml.JcEnumeration getAlignValue(TableCell.Alignment alignment) {
        switch (alignment) {
            case LEFT:
                return JcEnumeration.LEFT;
            case CENTER:
                return JcEnumeration.CENTER;
            case RIGHT:
                return JcEnumeration.RIGHT;
        }
        throw new IllegalStateException("Unknown alignment: " + alignment);
    }
}
