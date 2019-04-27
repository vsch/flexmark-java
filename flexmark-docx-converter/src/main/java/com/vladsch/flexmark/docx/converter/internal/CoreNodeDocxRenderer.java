package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.ast.util.TextCollectingVisitor;
import com.vladsch.flexmark.docx.converter.*;
import com.vladsch.flexmark.docx.converter.util.*;
import com.vladsch.flexmark.ext.aside.AsideBlock;
import com.vladsch.flexmark.ext.attributes.AttributesNode;
import com.vladsch.flexmark.ext.emoji.Emoji;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.emoji.internal.EmojiOptions;
import com.vladsch.flexmark.ext.emoji.internal.EmojiResolvedShortcut;
import com.vladsch.flexmark.ext.enumerated.reference.*;
import com.vladsch.flexmark.ext.enumerated.reference.internal.EnumRefTextCollectingVisitor;
import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.ext.gfm.strikethrough.Strikethrough;
import com.vladsch.flexmark.ext.gfm.strikethrough.Subscript;
import com.vladsch.flexmark.ext.gitlab.GitLabBlockQuote;
import com.vladsch.flexmark.ext.gitlab.GitLabDel;
import com.vladsch.flexmark.ext.gitlab.GitLabIns;
import com.vladsch.flexmark.ext.ins.Ins;
import com.vladsch.flexmark.ext.macros.MacroDefinitionBlock;
import com.vladsch.flexmark.ext.macros.MacroReference;
import com.vladsch.flexmark.ext.tables.*;
import com.vladsch.flexmark.ext.toc.SimTocBlock;
import com.vladsch.flexmark.ext.toc.TocBlock;
import com.vladsch.flexmark.ext.toc.TocBlockBase;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.superscript.Superscript;
import com.vladsch.flexmark.util.ImageUtils;
import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.format.options.ListSpacing;
import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableScopedDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.docx4j.UnitsOfMeasurement;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.model.structure.PageDimensions;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.model.styles.StyleUtil;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.AltChunkType;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.toc.TocException;
import org.docx4j.toc.TocGenerator;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;

import static com.vladsch.flexmark.html.renderer.LinkStatus.UNKNOWN;

@SuppressWarnings({ "WeakerAccess", "MethodOnlyUsedFromInnerClass", "MethodMayBeStatic", "OverlyCoupledClass" })
public class CoreNodeDocxRenderer implements PhasedNodeDocxRenderer {
    public static final DataKey<Integer> LIST_ITEM_NUMBER = new DataKey<>("LIST_ITEM_NUMBER", 0);
    public static final DataKey<ListSpacing> LIST_ITEM_SPACING = new DataKey<>("LIST_ITEM_SPACING", (ListSpacing) null);
    public static final HashSet<DocxRendererPhase> RENDERING_PHASES = new HashSet<DocxRendererPhase>(Arrays.asList(
            DocxRendererPhase.COLLECT,
            DocxRendererPhase.DOCUMENT_TOP,
            DocxRendererPhase.DOCUMENT_BOTTOM
    ));

    protected final ReferenceRepository referenceRepository;

    DocxRendererOptions options;
    final EmojiOptions emojiOptions;
    private final ListOptions listOptions;
    protected boolean recheckUndefinedReferences;
    protected boolean repositoryNodesDone;
    protected final boolean linebreakOnInlineHtmlBr;
    protected final boolean tableCaptionToParagraph;
    protected final boolean tableCaptionBeforeTable;
    protected final int tablePreferredWidthPct;
    protected final int tableLeftIndent;
    protected final String tableStyle;
    private int imageId;
    private final HashMap<Node, BigInteger> footnoteIDs; // cannot re-use footnote ids, so this is dead code, left in for future if needed
    private TocBlockBase lastTocBlock;
    private long[] numberedLists = new long[128];
    private long[] bulletLists = new long[128];
    private EnumeratedReferences enumeratedOrdinals;
    Runnable ordinalRunnable;
    private final HtmlIdGenerator headerIdGenerator; // used for enumerated text reference

    private void ensureNumberedListLength(int level) {
        if (numberedLists.length < level) {
            long[] newList = new long[(level / 128 + 1) * 128];
            System.arraycopy(numberedLists, 0, newList, 0, numberedLists.length);
            numberedLists = newList;
        }
    }

    private void ensureBulletListLength(int level) {
        if (bulletLists.length < level) {
            long[] newList = new long[(level / 128 + 1) * 128];
            System.arraycopy(bulletLists, 0, newList, 0, bulletLists.length);
            bulletLists = newList;
        }
    }

    public CoreNodeDocxRenderer(DataHolder options) {
        referenceRepository = getRepository(options);
        recheckUndefinedReferences = DocxRenderer.RECHECK_UNDEFINED_REFERENCES.getFrom(options);
        linebreakOnInlineHtmlBr = DocxRenderer.LINEBREAK_ON_INLINE_HTML_BR.getFrom(options);
        tableCaptionToParagraph = DocxRenderer.TABLE_CAPTION_TO_PARAGRAPH.getFrom(options);
        tableCaptionBeforeTable = DocxRenderer.TABLE_CAPTION_BEFORE_TABLE.getFrom(options);
        tablePreferredWidthPct = DocxRenderer.TABLE_PREFERRED_WIDTH_PCT.getFrom(options);
        tableLeftIndent = DocxRenderer.TABLE_LEFT_INDENT.getFrom(options);
        tableStyle = DocxRenderer.TABLE_STYLE.getFrom(options);
        repositoryNodesDone = false;

        this.options = new DocxRendererOptions(options);
        listOptions = ListOptions.getFrom(options);
        footnoteIDs = new HashMap<Node, BigInteger>();
        lastTocBlock = null;
        ordinalRunnable = null;

        final MutableScopedDataSet options1 = new MutableScopedDataSet(options);
        options1.set(EmojiExtension.ROOT_IMAGE_PATH, DocxRenderer.DOC_EMOJI_ROOT_IMAGE_PATH.getFrom(options));
        emojiOptions = new EmojiOptions(options1);
        headerIdGenerator = new HeaderIdGenerator.Factory().create();
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
                // get resolved styles
                options = docx.getDocxRendererOptions();
                headerIdGenerator.generateIds(document);
                break;

            case DOCUMENT_TOP:
                enumeratedOrdinals = EnumeratedReferenceExtension.ENUMERATED_REFERENCE_ORDINALS.getFrom(document);
                break;

            case DOCUMENT_BOTTOM:
                if (options.tocGenerate || lastTocBlock != null) {
                    TocGenerator tocGenerator = null;
                    try {
                        tocGenerator = new TocGenerator(docx.getPackage());
                        tocGenerator.setStartingIdForNewBookmarks(docx.getBookmarkIdAtomic());
                        //tocGenerator.generateToc( 0,    "TOC \\o \"1-3\" \\h \\z \\u ", true);
                        tocGenerator.generateToc(0, options.tocInstruction, true);
                    } catch (TocException e) {
                        if (tocGenerator != null && e.getMessage().contains("use updateToc instead")) {
                            try {
                                tocGenerator.updateToc(true);
                            } catch (TocException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            e.printStackTrace();
                        }
                    }
                    // to generate page numbers, you should install your own local instance of Plutext PDF Converter,
                    // and point to that in docx4j.properties

                    //tocGenerator.generateToc( 0,    "TOC \\h \\z \\t \"comh1,1,comh2,2,comh3,3,comh4,4\" ", true);
                }
                break;

            default:
                break;
        }
    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    @Override
    public Set<Class<?>> getBookmarkWrapsChildrenClasses() {
        return null;
        //return new HashSet<Class<?>>(Arrays.asList(
        //        Heading.class
        //));
    }

    public ReferenceRepository getRepository(final DataHolder options) {
        return options.get(Parser.REFERENCES);
    }

    @Override
    public Set<NodeDocxRendererHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<NodeDocxRendererHandler<? extends Node>>(Arrays.asList(
                // Generic unknown node formatter
                new NodeDocxRendererHandler<>(Node.class, new CustomNodeDocxRenderer<Node>() {
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
                new NodeDocxRendererHandler<GitLabBlockQuote>(GitLabBlockQuote.class, new CustomNodeDocxRenderer<GitLabBlockQuote>() {
                    @Override
                    public void render(final GitLabBlockQuote node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<AsideBlock>(AsideBlock.class, new CustomNodeDocxRenderer<AsideBlock>() {
                    @Override
                    public void render(final AsideBlock node, final DocxRendererContext docx) {
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
                new NodeDocxRendererHandler<StrongEmphasis>(StrongEmphasis.class, new CustomNodeDocxRenderer<StrongEmphasis>() {
                    @Override
                    public void render(final StrongEmphasis node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<Ins>(Ins.class, new CustomNodeDocxRenderer<Ins>() {
                    @Override
                    public void render(final Ins node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<GitLabIns>(GitLabIns.class, new CustomNodeDocxRenderer<GitLabIns>() {
                    @Override
                    public void render(final GitLabIns node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<MacroReference>(MacroReference.class, new CustomNodeDocxRenderer<MacroReference>() {
                    @Override
                    public void render(final MacroReference node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<MacroDefinitionBlock>(MacroDefinitionBlock.class, new CustomNodeDocxRenderer<MacroDefinitionBlock>() {
                    @Override
                    public void render(final MacroDefinitionBlock node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<Strikethrough>(Strikethrough.class, new CustomNodeDocxRenderer<Strikethrough>() {
                    @Override
                    public void render(final Strikethrough node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<GitLabDel>(GitLabDel.class, new CustomNodeDocxRenderer<GitLabDel>() {
                    @Override
                    public void render(final GitLabDel node, final DocxRendererContext docx) {
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
                new NodeDocxRendererHandler<Footnote>(Footnote.class, new CustomNodeDocxRenderer<Footnote>() {
                    @Override
                    public void render(final Footnote node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<FootnoteBlock>(FootnoteBlock.class, new CustomNodeDocxRenderer<FootnoteBlock>() {
                    @Override
                    public void render(final FootnoteBlock node, final DocxRendererContext docx) {
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
                }),
                new NodeDocxRendererHandler<TocBlock>(TocBlock.class, new CustomNodeDocxRenderer<TocBlock>() {
                    @Override
                    public void render(TocBlock node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<SimTocBlock>(SimTocBlock.class, new CustomNodeDocxRenderer<SimTocBlock>() {
                    @Override
                    public void render(SimTocBlock node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<EnumeratedReferenceText>(EnumeratedReferenceText.class, new CustomNodeDocxRenderer<EnumeratedReferenceText>() {
                    @Override
                    public void render(EnumeratedReferenceText node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<EnumeratedReferenceLink>(EnumeratedReferenceLink.class, new CustomNodeDocxRenderer<EnumeratedReferenceLink>() {
                    @Override
                    public void render(EnumeratedReferenceLink node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<EnumeratedReferenceBlock>(EnumeratedReferenceBlock.class, new CustomNodeDocxRenderer<EnumeratedReferenceBlock>() {
                    @Override
                    public void render(EnumeratedReferenceBlock node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<AttributesNode>(AttributesNode.class, new CustomNodeDocxRenderer<AttributesNode>() {
                    @Override
                    public void render(AttributesNode node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                }),
                new NodeDocxRendererHandler<Emoji>(Emoji.class, new CustomNodeDocxRenderer<Emoji>() {
                    @Override
                    public void render(Emoji node, final DocxRendererContext docx) {
                        CoreNodeDocxRenderer.this.render(node, docx);
                    }
                })
        ));
    }

    private void render(final Node node, final DocxRendererContext docx) {
        BasedSequence chars = node.getChars();
        MainDocumentPart mdp = docx.getDocxDocument();
        if (node instanceof Block) {
            docx.setBlockFormatProvider(new BlockFormatProviderBase<>(docx, docx.getDocxRendererOptions().LOOSE_PARAGRAPH_STYLE));
            docx.createP();
            docx.renderChildren(node);
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
        if (node.getParent() instanceof EnumeratedReferenceBlock) {
            // we need to unwrap the paragraphs
            //final String text = new TextCollectingVisitor().collectAndGetText(node);
            //if (!text.isEmpty()) {
            docx.renderChildren(node);
            //}
        } else if (!(node.getParent() instanceof ParagraphItemContainer) || !((ParagraphItemContainer) node.getParent()).isItemParagraph(node)) {
            if (node.getParent() instanceof BlockQuote || node.getParent() instanceof AsideBlock) {
                // the parent handles our formatting
                docx.createP();
                docx.renderChildren(node);
            } else {
                if (node.getFirstChildAnyNot(NonRenderingInline.class) != null) {
                    docx.setBlockFormatProvider(new BlockFormatProviderBase<>(docx, docx.getDocxRendererOptions().LOOSE_PARAGRAPH_STYLE));
                    docx.createP();
                    docx.renderChildren(node);
                }
            }
        } else {
            // the parent handles our formatting
            if (node.getParent() instanceof FootnoteBlock) {
                // there is already an open paragraph, re-use it
                docx.renderChildren(node);
            } else {
                docx.createP();
                docx.renderChildren(node);
            }
        }
    }

    private void render(final Text node, final DocxRendererContext docx) {
        docx.text(node.getChars().unescape());

/*
        // add text to last R
        R r = docx.getR();
        final org.docx4j.wml.Text text = docx.addWrappedText(r);
        text.setValue(node.getChars().unescape());
        text.setSpace(RunFormatProvider.SPACE_PRESERVE);
*/
    }

    private void render(final TextBase node, final DocxRendererContext docx) {
        docx.renderChildren(node);
    }

    private void render(final Emphasis node, final DocxRendererContext docx) {
        docx.setRunFormatProvider(new ItalicRunFormatProvider<>(docx, options.noCharacterStyles));
        docx.renderChildren(node);
    }

    private void render(final StrongEmphasis node, final DocxRendererContext docx) {
        docx.setRunFormatProvider(new BoldRunFormatProvider<>(docx, options.noCharacterStyles));
        docx.renderChildren(node);
    }

    private void render(final Subscript node, final DocxRendererContext docx) {
        docx.setRunFormatProvider(new SubscriptRunFormatProvider<>(docx, options.noCharacterStyles));
        docx.renderChildren(node);
    }

    private void render(final Superscript node, final DocxRendererContext docx) {
        docx.setRunFormatProvider(new SuperscriptRunFormatProvider<>(docx, options.noCharacterStyles));
        docx.renderChildren(node);
    }

    private void render(final Strikethrough node, final DocxRendererContext docx) {
        docx.setRunFormatProvider(new StrikethroughRunFormatProvider<>(docx, options.noCharacterStyles));
        docx.renderChildren(node);
    }

    private void render(final GitLabDel node, final DocxRendererContext docx) {
        docx.setRunFormatProvider(new StrikethroughRunFormatProvider<>(docx, options.noCharacterStyles));
        docx.renderChildren(node);
    }

    private void render(final Ins node, final DocxRendererContext docx) {
        docx.setRunFormatProvider(new UnderlineRunFormatProvider<>(docx, options.noCharacterStyles));
        docx.renderChildren(node);
    }

    private void render(final GitLabIns node, final DocxRendererContext docx) {
        docx.setRunFormatProvider(new UnderlineRunFormatProvider<>(docx, options.noCharacterStyles));
        docx.renderChildren(node);
    }

    private void render(final Code node, final DocxRendererContext docx) {
        docx.setRunFormatProvider(new SourceCodeRunFormatProvider<>(docx, options.noCharacterStyles, options.codeHighlightShading));
        docx.renderChildren(node);
    }

    private void render(final Heading node, final DocxRendererContext docx) {
        docx.setBlockFormatProvider(new HeadingBlockFormatProvider<>(docx, node.getLevel() - 1));
        docx.createP();
        docx.renderChildren(node);
    }

    private void render(final BlockQuote node, final DocxRendererContext docx) {
        final int level = node.countDirectAncestorsOfType(null, BlockQuote.class) + 1;
        docx.setBlockFormatProvider(new QuotedFormatProvider<>(docx, level, docx.getRenderingOptions().BLOCK_QUOTE_STYLE));
        docx.renderChildren(node);
    }

    private void render(final GitLabBlockQuote node, final DocxRendererContext docx) {
        final int level = node.countDirectAncestorsOfType(null, BlockQuote.class) + 1;
        docx.setBlockFormatProvider(new QuotedFormatProvider<>(docx, level, docx.getRenderingOptions().BLOCK_QUOTE_STYLE));
        docx.renderChildren(node);
    }

    private void render(final AsideBlock node, final DocxRendererContext docx) {
        final int level = node.countDirectAncestorsOfType(null, BlockQuote.class) + 1;
        docx.setBlockFormatProvider(new QuotedFormatProvider<>(docx, level, docx.getRenderingOptions().ASIDE_BLOCK_STYLE));
        docx.renderChildren(node);
    }

    private void render(final ThematicBreak node, final DocxRendererContext docx) {
        // Create object for p
        docx.setBlockFormatProvider(new BlockFormatProviderBase<>(docx, docx.getDocxRendererOptions().HORIZONTAL_LINE_STYLE));

        // Create object for r
        docx.createP();
        docx.createR();
    }

    private void render(final MacroReference node, final DocxRendererContext docx) {
        final MacroDefinitionBlock macroDefinitionBlock = node.getReferenceNode(docx.getDocument());
        if (macroDefinitionBlock != null) {
            if (macroDefinitionBlock.hasChildren() && !macroDefinitionBlock.isInExpansion()) {
                try {
                    macroDefinitionBlock.setInExpansion(true);
                    Node child = macroDefinitionBlock.getFirstChild();
                    if (child instanceof Paragraph && child == macroDefinitionBlock.getLastChild()) {
                        // if a single paragraph then we unwrap it and output only its children as inline text
                        docx.renderChildren(child);
                    } else {
                        docx.renderChildren(macroDefinitionBlock);
                    }
                } finally {
                    macroDefinitionBlock.setInExpansion(false);
                }
            }
        } else {
            docx.text(node.getChars().unescape());
        }
    }

    private void render(final MacroDefinitionBlock node, final DocxRendererContext docx) {

    }

    private void render(final FencedCodeBlock node, final DocxRendererContext docx) {
        List<BasedSequence> lines = node.getContentLines();
        docx.renderFencedCodeLines(lines);
    }

    private void render(final IndentedCodeBlock node, final DocxRendererContext docx) {
        List<BasedSequence> lines = node.getContentLines();
        docx.renderFencedCodeLines(lines);
    }

    public void renderList(final ListBlock node, final DocxRendererContext docx) {
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
        final int nesting = node.countDirectAncestorsOfType(ListItem.class, BulletList.class, OrderedList.class);
        final DocxRendererOptions options = docx.getDocxRendererOptions();
        final String listTextStyle = listOptions.isTightListItem(node) ? options.TIGHT_PARAGRAPH_STYLE : options.LOOSE_PARAGRAPH_STYLE;

        //final boolean inBlockQuote = node.getAncestorOfType(BlockQuote.class) != null;
        final boolean wantNumbered = node instanceof OrderedListItem;
        long numId = (wantNumbered ? 3 : 2);// + (inBlockQuote ? 2 : 0);
        int newNum = 1;
        final int listLevel = nesting - 1;
        final NumberingDefinitionsPart ndp = docx.getDocxDocument().getNumberingDefinitionsPart();

        if (node.getParent() instanceof OrderedList) {
            if (node == node.getParent().getFirstChild()) {
                newNum = listOptions.isOrderedListManualStart() ? ((OrderedList) node.getParent()).getStartNumber() : 1;
                numId = ndp.restart(numId, listLevel, newNum);
                ensureNumberedListLength(listLevel);
                numberedLists[listLevel] = numId;
            } else {
                numId = numberedLists[listLevel];
            }
        } else if (node.getParent() instanceof BulletList) {
            if (node == node.getParent().getFirstChild()) {
                newNum = 1;
                numId = ndp.restart(numId, listLevel, newNum);
                ensureBulletListLength(listLevel);
                bulletLists[listLevel] = numId;
            } else {
                numId = bulletLists[listLevel];
            }
        }

        final long idNum = numId;

        docx.setBlockFormatProvider(new ListItemBlockFormatProvider<>(docx, listTextStyle, idNum, listLevel, ListItem.class, ListBlock.class));
        docx.renderChildren(node);
    }

    private void render(final SoftLineBreak node, final DocxRendererContext docx) {
        docx.text(" ");
    }

    private void render(final HardLineBreak node, final DocxRendererContext docx) {
        // add a line break
        docx.addLineBreak();
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

            // render as fenced code
            docx.renderFencedCodeLines(node.getContentLines());
            //P p = docx.createP();
            //docx.text(normalizeEOL);
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
        if (linebreakOnInlineHtmlBr && node.getChars().unescape().matches("<br\\s*/?>")) {
            // start a new hard-break
            docx.addLineBreak();
        } else {
            if (suppress) return;

            if (escape) {
                // render as inline code
                docx.contextFramed(new Runnable() {
                    @Override
                    public void run() {
                        docx.setRunFormatProvider(new SourceCodeRunFormatProvider<>(docx, options.noCharacterStyles, options.codeHighlightShading));
                        docx.text(node.getChars().normalizeEOL());
                    }
                });
            } else {
                try {
                    docx.getDocxDocument().addAltChunk(AltChunkType.Html, node.getChars().toString().getBytes());
                } catch (Docx4JException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void render(final Reference node, final DocxRendererContext docx) {

    }

    private void render(final HtmlEntity node, final DocxRendererContext docx) {
        docx.text(node.getChars().unescape());
    }

    private void renderURL(final BasedSequence urlSource, final DocxRendererContext docx, final String linkUrl) {
        renderURL(urlSource, docx, linkUrl, linkUrl);
    }

    private void renderURL(final BasedSequence urlSource, final DocxRendererContext docx, final String linkUrl, final String linkText) {
        renderURL(urlSource, docx, linkUrl, null, new UrlRenderer(docx, linkText, linkUrl));
    }

    private void renderURL(final BasedSequence urlSrc, final DocxRendererContext docx, final String linkUrl, final Attributes attributes, final Runnable runnable) {
        P p = docx.getP();

        String linkTitle = attributes != null && attributes.contains(Attribute.TITLE_ATTR) ? attributes.getValue(Attribute.TITLE_ATTR) : "";

        final P.Hyperlink hyperlink = docx.getFactory().createPHyperlink();
        JAXBElement<P.Hyperlink> wrappedHyperlink = docx.getFactory().createPHyperlink(hyperlink);
        p.getContent().add(wrappedHyperlink);
        String highlightMissing = "";
        String linkTooltipText = linkTitle;
        String linkUrlText = linkUrl;

        if (linkUrl.startsWith("#")) {
            // local, we use anchor

            // see if we need to adjust it
            final String nodeId = linkUrl.substring(1);
            if (docx.getNodeFromId(nodeId) == null) {
                highlightMissing = docx.getRenderingOptions().localHyperlinkMissingHighlight;
                linkTooltipText = String.format(docx.getRenderingOptions().localHyperlinkMissingFormat, nodeId);

                if (docx.getDocxRendererOptions().errorsToStdErr) {
                    // output details of error
                    final Pair<Integer, Integer> atIndex = urlSrc.getBaseSequence().getLineColumnAtIndex(urlSrc.getStartOffset());
                    String sourceFile = docx.getDocxRendererOptions().errorSourceFile;
                    if (sourceFile.isEmpty()) {
                        sourceFile = "on line ";
                    } else {
                        sourceFile = String.format("in %s:", sourceFile);
                    }
                    System.err.println(String.format("    WARN: Invalid anchor target '%s' %s%d:%d", nodeId, sourceFile, atIndex.getFirst() + 1, atIndex.getSecond() + 2));
                }
            }
            final String anchor = docx.getValidBookmarkName(nodeId) + options.localHyperlinkSuffix;
            hyperlink.setAnchor(anchor);
            linkUrlText = String.format("#%s", anchor);
        } else {
            Relationship rel = docx.getHyperlinkRelationship(linkUrl);
            // Create object for hyperlink (wrapped in JAXBElement)
            hyperlink.setId(rel.getId());
        }

        if (linkTooltipText != null && !linkTooltipText.isEmpty()) {
            hyperlink.setTooltip(linkTooltipText);
        }

        docx.setRunFormatProvider(new RunFormatProviderBase<>(docx, docx.getDocxRendererOptions().HYPERLINK_STYLE, options.noCharacterStyles, highlightMissing));
        docx.setRunContainer(new UrlRunContainer(hyperlink));

        if (linkTitle != null && !linkTitle.isEmpty()) {
            // Create object for instrText (wrapped in JAXBElement)
            // Create object for r
            R r = docx.getFactory().createR();
            hyperlink.getContent().add(r);

            // Create object for fldChar (wrapped in JAXBElement)
            FldChar fldchar = docx.getFactory().createFldChar();
            JAXBElement<org.docx4j.wml.FldChar> fldcharWrapped = docx.getFactory().createRFldChar(fldchar);
            r.getContent().add(fldcharWrapped);
            fldchar.setFldCharType(org.docx4j.wml.STFldCharType.BEGIN);

            // Create object for r
            R r2 = docx.getFactory().createR();
            hyperlink.getContent().add(r2);

            // Create object for instrText (wrapped in JAXBElement)
            org.docx4j.wml.Text text = docx.getFactory().createText();
            JAXBElement<org.docx4j.wml.Text> textWrapped = docx.getFactory().createRInstrText(text);
            r2.getContent().add(textWrapped);
            text.setValue(String.format(" HYPERLINK \"%s\" \\o \"%s\" ", linkUrlText, linkTitle));
            text.setSpace(RunFormatProvider.SPACE_PRESERVE);

            // Create object for r
            R r3 = docx.getFactory().createR();
            hyperlink.getContent().add(r3);

            // Create object for fldChar (wrapped in JAXBElement)
            FldChar fldchar2 = docx.getFactory().createFldChar();
            JAXBElement<org.docx4j.wml.FldChar> fldcharWrapped2 = docx.getFactory().createRFldChar(fldchar2);
            r3.getContent().add(fldcharWrapped2);
            fldchar2.setFldCharType(org.docx4j.wml.STFldCharType.SEPARATE);
        }

        runnable.run();

        if (linkTitle != null && !linkTitle.isEmpty()) {
            // Create object for r
            R r3 = docx.getFactory().createR();
            hyperlink.getContent().add(r3);

            // Create object for fldChar (wrapped in JAXBElement)
            FldChar fldchar2 = docx.getFactory().createFldChar();
            JAXBElement<org.docx4j.wml.FldChar> fldcharWrapped2 = docx.getFactory().createRFldChar(fldchar2);
            r3.getContent().add(fldcharWrapped2);
            fldchar2.setFldCharType(STFldCharType.END);
        }
    }

    private void render(final AutoLink node, final DocxRendererContext docx) {
        final String url = node.getChars().unescape();
        renderURL(node.getChars(), docx, url);
    }

    private void render(final MailLink node, final DocxRendererContext docx) {
        renderURL(node.getChars(), docx, "mailto:" + node.getChars().unescape());
    }

    private void render(final Link node, final DocxRendererContext docx) {
        ResolvedLink resolvedLink = docx.resolveLink(LinkType.LINK, node.getUrl().unescape(), null, null);

        // we have a title part, use that
        Attributes attributes = resolvedLink.getNonNullAttributes();

        if (node.getTitle().isNotNull()) {
            attributes.replaceValue(Attribute.TITLE_ATTR, node.getTitle().unescape());
        } else {
            attributes.remove(Attribute.TITLE_ATTR);
        }

        attributes = docx.extendRenderingNodeAttributes(AttributablePart.NODE, attributes);

        renderURL(node.getUrl(), docx, resolvedLink.getUrl(), attributes, new ChildRenderer(docx, node));
    }

    private void render(final LinkRef node, final DocxRendererContext docx) {
        ResolvedLink resolvedLink = null;

        if (!node.isDefined() && recheckUndefinedReferences) {
            if (node.getReferenceNode(referenceRepository) != null) {
                node.setDefined(true);
            }
        }

        BasedSequence urlSrc = node.getReference();

        Reference reference = null;
        if (node.isDefined()) {
            reference = node.getReferenceNode(referenceRepository);
            urlSrc = reference.getUrl();
            String url = urlSrc.unescape();

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
            Attributes attributes = resolvedLink.getNonNullAttributes();

            if (reference != null) {
                attributes = docx.extendRenderingNodeAttributes(reference, AttributablePart.NODE, attributes);
            }

            attributes = docx.extendRenderingNodeAttributes(AttributablePart.NODE, attributes);
            renderURL(urlSrc, docx, resolvedLink.getUrl(), attributes, new ChildRenderer(docx, node));
        }
    }

    private long getSizeInfo(Attributes attributes, String name, double pageDimension) {
        Double value = -1.0;

        if (attributes.contains(name)) {
            String attributeValue = attributes.getValue(name).trim();
            boolean relative = false;
            if (attributeValue.endsWith("%")) {
                // make it relative
                attributeValue = attributeValue.substring(0, attributeValue.length() - 1);
                relative = true;
            }
            try {
                value = Double.parseDouble(attributeValue);
                // convert to twips assuming 72dpi for pixels
                // 20 twips/pt and 72 pt/inch
                // assuming 72ppi we have pixel = point
                value *= 20.0 * options.docEmojiImageVertSize;
            } catch (Throwable ignored) {
                value = -1.0;
            }

            if (relative) {
                // value
                value *= pageDimension;
            }
        }

        long longValue = -1;
        try {
            longValue = value.longValue();
        } catch (Throwable ignored) {
            longValue = -1;
        }

        return longValue;
    }

    public R newImage(final DocxRendererContext docx, byte[] bytes, String filenameHint, Attributes attributes, int id1, int id2) {
        try {
            BinaryPartAbstractImage imagePart = null;
            imagePart = BinaryPartAbstractImage.createImagePart(docx.getPackage(), docx.getContainerPart(), bytes);
            Inline inline = null;
            String altText = attributes.contains("alt") ? attributes.getValue("alt") : "";
            List<SectionWrapper> sections = docx.getPackage().getDocumentModel().getSections();
            PageDimensions page = sections.get(sections.size() - 1).getPageDimensions();
            double writableWidthTwips = page.getWritableWidthTwips();

            long cx = getSizeInfo(attributes, "width", page.getWritableWidthTwips());
            long cy = cx > 0 ? getSizeInfo(attributes, "height", page.getWritableHeightTwips()) : -1;

            // kludge: normally there is no max-width attribute but we can fake it
            long longMaxWidth = getSizeInfo(attributes, "max-width", page.getWritableWidthTwips());
            int maxWidth = longMaxWidth > 0 && longMaxWidth <= Integer.MAX_VALUE ? (int) longMaxWidth : -1;

            if (cx > 0 && cy > 0) {
                // here we need cx & cy in emu which needs conversion from twips
                cx = UnitsOfMeasurement.twipToEMU(cx);
                cy = UnitsOfMeasurement.twipToEMU(cy);
                inline = imagePart.createImageInline(filenameHint, altText, id1, id2, cx, cy, false);
            } else {
                if (cx > 0) {
                    inline = imagePart.createImageInline(filenameHint, altText, id1, id2, cx, false);
                } else {
                    if (maxWidth > 0) {
                        inline = imagePart.createImageInline(filenameHint, altText, id1, id2, false, maxWidth);
                    } else {
                        inline = imagePart.createImageInline(filenameHint, altText, id1, id2, false);
                    }
                }
            }

            // Now add the inline in w:p/w:r/w:drawing
            org.docx4j.wml.R run = docx.createR();
            org.docx4j.wml.Drawing drawing = docx.getFactory().createDrawing();
            run.getContent().add(drawing);
            drawing.getAnchorOrInline().add(inline);
            return run;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void render(final Image node, final DocxRendererContext docx) {
        String altText = new TextCollectingVisitor().collectAndGetText(node);
        ResolvedLink resolvedLink = docx.resolveLink(LinkType.IMAGE, node.getUrl().unescape(), null, null);
        String url = resolvedLink.getUrl();
        Attributes attributes = resolvedLink.getNonNullAttributes();

        if (!node.getUrlContent().isEmpty()) {
            // reverse URL encoding of =, &
            String content = Escaping.percentEncodeUrl(node.getUrlContent()).replace("+", "%2B").replace("%3D", "=").replace("%26", "&amp;");
            url += content;
        }

        if (!altText.isEmpty()) {
            attributes.replaceValue("alt", altText);
        }

        attributes = docx.extendRenderingNodeAttributes(AttributablePart.NODE, attributes);

        //String alt = node.getText().unescape();
        renderImage(docx, url, attributes);
    }

    private void render(final Emoji node, final DocxRendererContext docx) {
        final EmojiResolvedShortcut shortcut = EmojiResolvedShortcut.getEmojiText(node, emojiOptions.useShortcutType, emojiOptions.useImageType, emojiOptions.rootImagePath);

        if (shortcut.emoji == null || shortcut.emojiText == null) {
            // output as text
            docx.text(":");
            docx.renderChildren(node);
            docx.text(":");
        } else {
            if (shortcut.isUnicode) {
                docx.text(shortcut.emojiText);
            } else {
                ResolvedLink resolvedLink = docx.resolveLink(LinkType.IMAGE, shortcut.emojiText, null, null);
                //String altText = shortcut.alt;
                String url = resolvedLink.getUrl();
                Attributes attributes = resolvedLink.getNonNullAttributes();

                if (shortcut.alt != null) {
                    attributes.replaceValue("alt", shortcut.alt);
                }

                // need to determine the font point size from the style
                RPr rPr = docx.getFactory().createRPr();
                RPr paraRPr = docx.getFactory().createRPr();
                PPr pPr = docx.getFactory().createPPr();

                docx.getBlockFormatProvider().getPPr(pPr);
                docx.getBlockFormatProvider().getParaRPr(paraRPr);
                paraRPr = docx.getHelper().getExplicitRPr(paraRPr, pPr);

                docx.getRunFormatProvider().getRPr(rPr);
                rPr = docx.getHelper().getExplicitRPr(rPr);

                StyleUtil.apply(rPr, paraRPr);
                StyleUtil.apply(paraRPr, rPr);

                // now see if we have line height
                final HpsMeasure sz = rPr.getSz();
                long l = -1;
                if (sz != null) {
                    l = sz.getVal().longValue();
                    attributes.replaceValue("height", String.valueOf(Math.round(l / 2 / options.docEmojiImageVertSize)));
                    attributes.replaceValue("width", String.valueOf(Math.round(l / 2 / options.docEmojiImageVertSize)));
                } else if (!emojiOptions.attrImageSize.isEmpty()) {
                    attributes.replaceValue("height", emojiOptions.attrImageSize);
                    attributes.replaceValue("width", emojiOptions.attrImageSize);

                    l = getSizeInfo(attributes, "width", 100.0);// page dimensions not used, these are not %
                    // now revert from twips to points
                    l /= 20;
                }

                if (!emojiOptions.attrAlign.isEmpty()) {
                    attributes.replaceValue("align", emojiOptions.attrAlign);
                }

                attributes = docx.extendRenderingNodeAttributes(AttributablePart.NODE, attributes);
                R r = renderImage(docx, url, attributes);

                // now move down by 20%
                // <w:position w:val="-4"/>
                if (r != null) {
                    long adj = Math.round(l * options.docEmojiImageVertOffset);
                    if (adj < 0) {
                        RPr rPr1 = r.getRPr();
                        if (rPr1 == null) {
                            rPr1 = docx.getFactory().createRPr();
                            r.setRPr(rPr1);
                        }
                        CTSignedHpsMeasure hpsMeasure = docx.getFactory().createCTSignedHpsMeasure();
                        rPr1.setPosition(hpsMeasure);
                        hpsMeasure.setVal(BigInteger.valueOf(adj));
                    }
                }
            }
        }
    }

    private void render(final ImageRef node, final DocxRendererContext docx) {
        ResolvedLink resolvedLink;

        if (!node.isDefined() && recheckUndefinedReferences) {
            if (node.getReferenceNode(referenceRepository) != null) {
                node.setDefined(true);
            }
        }

        Reference reference = null;

        if (node.isDefined()) {
            reference = node.getReferenceNode(referenceRepository);
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

            if (options.logImageProcessing) {
                System.out.println("render image ref of " + referenceRepository.normalizeKey(node.getReference()) + " skipped because it was not defined");
            }
        } else {
            String altText = new TextCollectingVisitor().collectAndGetText(node);
            String url = resolvedLink.getUrl();
            Attributes attributes = resolvedLink.getNonNullAttributes();

            if (!altText.isEmpty()) {
                attributes.replaceValue("alt", altText);
            }

            if (reference != null) {
                attributes = docx.extendRenderingNodeAttributes(reference, AttributablePart.NODE, attributes);
            }

            attributes = docx.extendRenderingNodeAttributes(AttributablePart.NODE, attributes);
            renderImage(docx, url, attributes);
        }
    }

    private R renderImage(final DocxRendererContext docx, String url, final Attributes attributes) {
        BufferedImage image = null;
        int id1 = imageId++;
        int id2 = imageId++;
        String filenameHint = String.format("Image%d", id1 / 2 + 1);
        int cx;

        if (url.startsWith(DocxRenderer.EMOJI_RESOURCE_PREFIX)) {
            // we take it from resources
            url = this.getClass().getResource("/emoji/" + url.substring(DocxRenderer.EMOJI_RESOURCE_PREFIX.length())).toString();
            int tmp = 0;
        }

        if (url.startsWith("http:") || url.startsWith("https:") || url.startsWith("file:")) {
            // hyperlinked image  or file
            if (url.startsWith("file:")) {
                // try to load from file, from URL fails on some images while file load succeeds
                try {
                    File imageFile = new File(new URI(url));
                    image = ImageUtils.loadImageFromFile(imageFile);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else {
                image = ImageUtils.loadImageFromURL(url, options.logImageProcessing);
            }

            if (image == null && options.logImageProcessing) {
                System.out.println("loadImageFromURL(" + url + ") returned null");
            }
        } else if (options.logImageProcessing) {
            System.out.println("renderImage of \"" + url + "\") skipped (not file:, http: or https:)");
        }

        if (image != null) {
            int width = image.getWidth();
            if (options.maxImageWidth > 0 && options.maxImageWidth < width) {
                cx = options.maxImageWidth;
                attributes.replaceValue("width", String.valueOf(cx));
            }

            byte[] imageBytes = ImageUtils.getImageBytes(image);
            return newImage(docx, imageBytes, filenameHint, attributes, id1, id2);
        }
        return null;
    }

    private Tbl myTbl;
    private Tr myTr;

    private void render(final TableBlock node, final DocxRendererContext docx) {
        // if we have a caption and it goes before the table, we add it here
        final Node caption = node.getFirstChildAny(TableCaption.class);
        if (caption != null && tableCaptionBeforeTable) {
            renderTableCaption((TableCaption) caption, docx);
        }

        final Tbl savedTbl = myTbl;

        myTbl = docx.getFactory().createTbl();
        JAXBElement<org.docx4j.wml.Tbl> tblWrapped = docx.getFactory().createHdrTbl(myTbl);
        docx.getContent().add(tblWrapped);

        // Create object for tblPr
        TblPr tblpr = docx.getFactory().createTblPr();
        myTbl.setTblPr(tblpr);

        // Create object for jc
        Jc jc = docx.getFactory().createJc();
        tblpr.setJc(jc);
        jc.setVal(JcEnumeration.LEFT);

        // create style if one is specified
        if (!tableStyle.isEmpty()) {
            CTTblPrBase.TblStyle tblStyle = docx.getFactory().createCTTblPrBaseTblStyle();
            tblpr.setTblStyle(tblStyle);
            tblStyle.setVal(tableStyle);
        }

        // Create object for tblW
        TblWidth tblwidth = docx.getFactory().createTblWidth();
        tblpr.setTblW(tblwidth);
        if (tablePreferredWidthPct == 0) {
            tblwidth.setType("auto");
            tblwidth.setW(BigInteger.valueOf(0));
        } else {
            tblwidth.setType("pct");
            tblwidth.setW(BigInteger.valueOf(tablePreferredWidthPct * 50));
        }

        final int cellMargin = 80;
        // Create object for tblInd
        //TblWidth tblwidth2 = docx.getFactory().createTblWidth();
        //tblpr.setTblInd(tblwidth2);
        //tblwidth2.setType("dxa");
        //tblwidth2.setW(BigInteger.valueOf(30));

        PPr pPr = docx.getFactory().createPPr();
        docx.getHelper().ensureInd(pPr);
        PPrBase.Ind ind = pPr.getInd();
        ind.setLeft(null);

        PPr parentPPr = docx.getFactory().createPPr();
        docx.getBlockFormatProvider().getPPr(parentPPr);
        PPr styledPPr = docx.getHelper().getExplicitPPr(parentPPr);
        docx.getHelper().inheritInd(pPr, styledPPr);

        // add inherited indent
        TblWidth tblInd = docx.getFactory().createTblWidth();
        tblpr.setTblInd(tblInd);
        tblInd.setType("dxa");
        final BigInteger tableInd = BigInteger.valueOf(tableLeftIndent).add(docx.getHelper().safeIndLeft(pPr.getInd()));
        tblInd.setW(tableInd);

        docx.setBlockFormatProvider(new IsolatingBlockFormatProvider<>(docx));

        if (tableStyle.isEmpty()) {
            // Create object for tblBorders
            TblBorders tblborders = docx.getFactory().createTblBorders();
            tblpr.setTblBorders(tblborders);
            // Create object for left
            CTBorder border = docx.getFactory().createCTBorder();
            tblborders.setLeft(border);
            border.setVal(org.docx4j.wml.STBorder.SINGLE);
            border.setSz(BigInteger.valueOf(2));
            border.setColor("000001");
            border.setSpace(BigInteger.valueOf(0));
            // Create object for right
            CTBorder border2 = docx.getFactory().createCTBorder();
            tblborders.setRight(border2);
            border2.setVal(org.docx4j.wml.STBorder.SINGLE);
            border2.setSz(BigInteger.valueOf(2));
            border2.setColor("000001");
            border2.setSpace(BigInteger.valueOf(0));
            // Create object for top
            CTBorder border3 = docx.getFactory().createCTBorder();
            tblborders.setTop(border3);
            border3.setVal(org.docx4j.wml.STBorder.SINGLE);
            border3.setSz(BigInteger.valueOf(2));
            border3.setColor("000001");
            border3.setSpace(BigInteger.valueOf(0));
            // Create object for bottom
            CTBorder border4 = docx.getFactory().createCTBorder();
            tblborders.setBottom(border4);
            border4.setVal(org.docx4j.wml.STBorder.SINGLE);
            border4.setSz(BigInteger.valueOf(2));
            border4.setColor("000001");
            border4.setSpace(BigInteger.valueOf(0));
            // Create object for insideH
            CTBorder border5 = docx.getFactory().createCTBorder();
            tblborders.setInsideH(border5);
            border5.setVal(org.docx4j.wml.STBorder.SINGLE);
            border5.setSz(BigInteger.valueOf(2));
            border5.setColor("000001");
            border5.setSpace(BigInteger.valueOf(0));
            // Create object for insideV
            CTBorder border6 = docx.getFactory().createCTBorder();
            tblborders.setInsideV(border6);
            border6.setVal(org.docx4j.wml.STBorder.SINGLE);
            border6.setSz(BigInteger.valueOf(2));
            border6.setColor("000001");
            border6.setSpace(BigInteger.valueOf(0));
        }

        // Create object for tblCellMar
        CTTblCellMar tblcellmar = docx.getFactory().createCTTblCellMar();
        tblpr.setTblCellMar(tblcellmar);
        // Create object for left
        TblWidth tblwidth3 = docx.getFactory().createTblWidth();
        tblcellmar.setLeft(tblwidth3);
        tblwidth3.setType("dxa");
        tblwidth3.setW(BigInteger.valueOf(cellMargin));
        // Create object for right
        TblWidth tblwidth4 = docx.getFactory().createTblWidth();
        tblcellmar.setRight(tblwidth4);
        tblwidth4.setType("dxa");
        tblwidth4.setW(BigInteger.valueOf(cellMargin));
        // Create object for top
        TblWidth tblwidth5 = docx.getFactory().createTblWidth();
        tblcellmar.setTop(tblwidth5);
        tblwidth5.setType("dxa");
        tblwidth5.setW(BigInteger.valueOf(cellMargin));
        // Create object for bottom
        TblWidth tblwidth6 = docx.getFactory().createTblWidth();
        tblcellmar.setBottom(tblwidth6);
        tblwidth6.setType("dxa");
        tblwidth6.setW(BigInteger.valueOf(cellMargin));
        // Create object for tblLook
        CTTblLook tbllook = docx.getFactory().createCTTblLook();
        tblpr.setTblLook(tbllook);
        tbllook.setVal("04a0");
        tbllook.setLastRow(org.docx4j.sharedtypes.STOnOff.ZERO);
        tbllook.setLastColumn(org.docx4j.sharedtypes.STOnOff.ZERO);
        tbllook.setNoHBand(org.docx4j.sharedtypes.STOnOff.ZERO);
        tbllook.setNoVBand(org.docx4j.sharedtypes.STOnOff.ONE);
        tbllook.setFirstRow(org.docx4j.sharedtypes.STOnOff.ONE);
        tbllook.setFirstColumn(org.docx4j.sharedtypes.STOnOff.ONE);

        docx.renderChildren(node);
        myTbl = savedTbl;
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
        Tr savedTr = myTr;
        myTr = docx.getFactory().createTr();
        myTbl.getContent().add(myTr);

        // Create object for trPr
        TrPr trpr = docx.getFactory().createTrPr();
        myTr.setTrPr(trpr);

        if (node.getParent() instanceof TableHead) {
            // Create object for tblHeader (wrapped in JAXBElement)
            BooleanDefaultTrue booleandefaulttrue = docx.getFactory().createBooleanDefaultTrue();
            JAXBElement<org.docx4j.wml.BooleanDefaultTrue> booleandefaulttrueWrapped = docx.getFactory().createCTTrPrBaseTblHeader(booleandefaulttrue);
            trpr.getCnfStyleOrDivIdOrGridBefore().add(booleandefaulttrueWrapped);
        }

        docx.renderChildren(node);
        myTr = savedTr;
    }

    private void render(final TableCaption node, final DocxRendererContext docx) {
        // TODO: figure out how to set caption
        // table caption not yet supported by docx4j API
        //final TblPr tblPr = myTbl.getTblPr();
        //docx.getFactory().createCTCaption();
        // convert to Caption text
        if (!tableCaptionBeforeTable) {
            renderTableCaption(node, docx);
        }
    }

    private void renderTableCaption(final TableCaption node, final DocxRendererContext docx) {
        if (tableCaptionToParagraph) {
            docx.contextFramed(new TableCaptionRenderer(docx, node));
        }
    }

    private void render(final TableCell node, final DocxRendererContext docx) {
        String styleName = node.isHeader() ? docx.getDocxRendererOptions().TABLE_HEADING : docx.getDocxRendererOptions().TABLE_CONTENTS;

        // Create object for tc (wrapped in JAXBElement)
        final Tc tc = docx.getFactory().createTc();
        JAXBElement<org.docx4j.wml.Tc> tcWrapped = docx.getFactory().createTrTc(tc);
        myTr.getContent().add(tcWrapped);
        // Create object for tcPr
        TcPr tcpr = docx.getFactory().createTcPr();
        tc.setTcPr(tcpr);

        Style style = docx.getStyle(styleName);
        if (style != null) {
            if (style.getPPr() != null) {
                if (style.getPPr().getShd() != null) {
                    CTShd shd = docx.getHelper().getCopy(style.getPPr().getShd(), false);
                    tcpr.setShd(shd);
                }
            }
        }

        final boolean[] firstP = new boolean[] { true };
        docx.setContentContainer(new TableCellContentContainer(tc, docx, firstP));
        docx.setBlockFormatProvider(new BlockFormatProviderBase<>(docx, styleName));
        docx.setParaContainer(new TableCellParaContainer(node, tc, docx, firstP));

        if (node.getSpan() > 1) {
            // Create object for gridSpan
            TcPrInner.GridSpan tcprinnergridspan = docx.getFactory().createTcPrInnerGridSpan();
            tcpr.setGridSpan(tcprinnergridspan);
            tcprinnergridspan.setVal(BigInteger.valueOf(node.getSpan()));
        }

        docx.renderChildren(node);

        if (firstP[0]) {
            // empty content, need to create p
            docx.createP();
        }
    }

    static JcEnumeration getAlignValue(TableCell.Alignment alignment) {
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

    private void render(FootnoteBlock node, final DocxRendererContext docx) {

    }

    private void render(Footnote node, final DocxRendererContext docx) {
        final FootnoteBlock footnoteBlock = node.getFootnoteBlock();
        if (footnoteBlock == null) {
            //just text
            final org.docx4j.wml.Text text = docx.addWrappedText();
            text.setValue("[^");
            docx.renderChildren(node);
            final org.docx4j.wml.Text text1 = docx.addWrappedText();
            text1.setValue("]");
        } else {
            try {
                BigInteger footnoteId = footnoteIDs.containsKey(footnoteBlock) ? footnoteIDs.get(footnoteBlock) : BigInteger.ZERO;
                final CTFtnEdn ftnEdn = docx.addFootnote(footnoteId);
                final BigInteger ftnEdnId = ftnEdn.getId();
                if (ftnEdnId.compareTo(footnoteId) != 0) {
                    // Word does not like re-using footnotes, so we create a new one for every reference
                    //footnoteIDs.put(footnoteBlock, ftnEdnId);
                    docx.contextFramed(new FootnoteFrame(docx, ftnEdn, footnoteBlock));
                }
            } catch (Docx4JException e) {
                e.printStackTrace();
            }
        }
    }

    private void render(TocBlock node, final DocxRendererContext docx) {
        lastTocBlock = node;
    }

    private void render(SimTocBlock node, final DocxRendererContext docx) {
        lastTocBlock = node;
    }

    private void render(EnumeratedReferenceText node, final DocxRendererContext docx) {
        String text = node.getText().toString();

        if (text.isEmpty()) {
            // placeholder for ordinal
            if (ordinalRunnable != null) ordinalRunnable.run();
        } else {
            String type = EnumeratedReferenceRepository.getType(text.toString());
            if (type.isEmpty() || text.equals(type + ":")) {
                Node parent = node.getAncestorOfType(Heading.class);

                if (parent instanceof Heading) {
                    text = (type.isEmpty() ? text : type) + ":" + headerIdGenerator.getId(parent);
                }
            }

            enumeratedOrdinals.renderReferenceOrdinals(text, new OrdinalRenderer(CoreNodeDocxRenderer.this, docx));
        }
    }

    private void render(final EnumeratedReferenceLink node, final DocxRendererContext docx) {
        final String text = node.getText().toString();

        if (text.isEmpty()) {
            // placeholder for ordinal
            if (ordinalRunnable != null) ordinalRunnable.run();
        } else {
            final EnumeratedReferenceRendering[] renderings = enumeratedOrdinals.getEnumeratedReferenceOrdinals(text);

            String title = new EnumRefTextCollectingVisitor().collectAndGetText(node.getChars().getBaseSequence(), renderings, null);
            Attributes attributes = new Attributes();

            if (title != null) {
                attributes.replaceValue(Attribute.TITLE_ATTR, title);
            }

            attributes = docx.extendRenderingNodeAttributes(AttributablePart.NODE, attributes);
            renderURL(node.getText(), docx, "#" + text, attributes, new Runnable() {
                @Override
                public void run() {
                    EnumeratedReferences.renderReferenceOrdinals(renderings, new OrdinalRenderer(CoreNodeDocxRenderer.this, docx));
                }
            });
        }
    }

    private static class OrdinalRenderer implements EnumeratedOrdinalRenderer {
        final CoreNodeDocxRenderer renderer;
        final DocxRendererContext docx;

        public OrdinalRenderer(final CoreNodeDocxRenderer renderer, final DocxRendererContext docx) {
            this.renderer = renderer;
            this.docx = docx;
        }

        @Override
        public void startRendering(final EnumeratedReferenceRendering[] renderings) {

        }

        @Override
        public void setEnumOrdinalRunnable(final Runnable runnable) {
            renderer.ordinalRunnable = runnable;
        }

        @Override
        public Runnable getEnumOrdinalRunnable() {
            return renderer.ordinalRunnable;
        }

        @Override
        public void render(final int referenceOrdinal, final EnumeratedReferenceBlock referenceFormat, final String defaultText, final boolean needSeparator) {
            final Runnable compoundRunnable = renderer.ordinalRunnable;

            if (referenceFormat != null) {
                renderer.ordinalRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (compoundRunnable != null) compoundRunnable.run();
                        docx.text(String.valueOf(referenceOrdinal));
                        if (needSeparator) docx.text(".");
                    }
                };

                docx.renderChildren(referenceFormat);
            } else {
                if (compoundRunnable != null) {
                    docx.text(defaultText + " ");
                    if (compoundRunnable != null) compoundRunnable.run();
                    docx.text(referenceOrdinal + (needSeparator ? "." : ""));
                } else {
                    docx.text(defaultText + " " + referenceOrdinal + (needSeparator ? "." : ""));
                }
            }
        }

        @Override
        public void endRendering() {

        }
    }

    private void render(EnumeratedReferenceBlock node, final DocxRendererContext docx) {

    }

    private void render(AttributesNode node, final DocxRendererContext docx) {

    }

    private static class UrlRenderer implements Runnable {
        private final DocxRendererContext myDocx;
        private final String myLinkText;
        private final String myLinkUrl;

        public UrlRenderer(final DocxRendererContext docx, final String linkText, final String linkUrl) {
            myDocx = docx;
            myLinkText = linkText;
            myLinkUrl = linkUrl;
        }

        @Override
        public void run() {
            // Create object for r
            myDocx.text(myLinkText == null ? myLinkUrl : myLinkText);
        }
    }

    private static class UrlRunContainer implements RunContainer {
        private final P.Hyperlink myHyperlink;

        public UrlRunContainer(final P.Hyperlink hyperlink) {myHyperlink = hyperlink;}

        @Override
        public void addR(final R r) {
            myHyperlink.getContent().add(r);
        }

        @Override
        public R getLastR() {
            final List<Object> content = myHyperlink.getContent();
            if (content == null || content.size() == 0) return null;
            final Object o = content.get(content.size() - 1);
            return o instanceof R ? (R) o : null;
        }
    }

    private static class ChildRenderer implements Runnable {
        private final DocxRendererContext myDocx;
        private final Node myNode;

        public ChildRenderer(final DocxRendererContext docx, final Node node) {
            myDocx = docx;
            myNode = node;
        }

        @Override
        public void run() {
            myDocx.renderChildren(myNode);
        }
    }

    private static class FootnoteFrame implements Runnable {
        final DocxRendererContext myDocx;
        final CTFtnEdn myFtnEdn;
        private final FootnoteBlock myFootnoteBlock;

        public FootnoteFrame(final DocxRendererContext docx, final CTFtnEdn ftnEdn, final FootnoteBlock footnoteBlock) {
            myDocx = docx;
            myFtnEdn = ftnEdn;
            myFootnoteBlock = footnoteBlock;
        }

        @Override
        public void run() {
            myDocx.setBlockFormatProvider(new FootnoteBlockFormatProvider<>(myDocx));
            myDocx.setRunFormatProvider(new FootnoteRunFormatProvider<>(myDocx));
            myDocx.setParaContainer(new ParaContainer() {
                @Override
                public void addP(final P p) {
                    myFtnEdn.getContent().add(p);
                }

                @Override
                public P getLastP() {
                    final List<Object> content = myFtnEdn.getContent();
                    if (content == null || content.size() == 0) return null;
                    final Object o = content.get(content.size() - 1);
                    return o instanceof P ? (P) o : null;
                }
            });

            myDocx.setContentContainer(new ContentContainer() {
                @Override
                public RelationshipsPart getRelationshipsPart() {
                    try {
                        return myDocx.getFootnotesPart().getRelationshipsPart();
                    } catch (Docx4JException e) {
                        e.printStackTrace();
                        return myDocx.getDocxDocument().getRelationshipsPart();
                    }
                }

                @Override
                public Part getContainerPart() {
                    try {
                        return myDocx.getFootnotesPart();
                    } catch (Docx4JException e) {
                        e.printStackTrace();
                        return myDocx.getDocxDocument();
                    }
                }

                @Override
                public List<Object> getContent() {
                    return myFtnEdn.getContent();
                }

                @Override
                public Object getLastContentElement() {
                    final List<Object> content = getContent();
                    return content != null && content.size() > 0 ? content.get(content.size() - 1) : null;
                }

                @Override
                public void addContentElement(final Object element) {
                    getContent().add(element);
                }
            });
            myDocx.renderChildren(myFootnoteBlock);
        }
    }

    private static class TableCaptionRenderer implements Runnable {
        private final DocxRendererContext myDocx;
        private final TableCaption myNode;

        public TableCaptionRenderer(final DocxRendererContext docx, final TableCaption node) {
            myDocx = docx;
            myNode = node;
        }

        @Override
        public void run() {
            myDocx.setBlockFormatProvider(new BlockFormatProviderBase<>(myDocx, myDocx.getDocxRendererOptions().TABLE_CAPTION));
            myDocx.createP();
            myDocx.renderChildren(myNode);
        }
    }

    private static class TableCellContentContainer implements ContentContainer {
        private final Tc myTc;
        private final DocxRendererContext myDocx;
        private final Part myContainerPart;
        private final boolean[] myFirstP;

        public TableCellContentContainer(final Tc tc, final DocxRendererContext docx, final boolean[] firstP) {
            myTc = tc;
            myDocx = docx;
            myFirstP = firstP;
            myContainerPart = myDocx.getContainerPart();
        }

        @Override
        public List<Object> getContent() {
            return myTc.getContent();
        }

        @Override
        public RelationshipsPart getRelationshipsPart() {
            return myContainerPart.relationships;
        }

        @Override
        public Part getContainerPart() {
            return myContainerPart;
        }

        @Override
        public Object getLastContentElement() {
            final List<Object> content = myTc.getContent();
            return content != null && content.size() > 0 ? content.get(content.size() - 1) : null;
        }

        @Override
        public void addContentElement(final Object element) {
            myTc.getContent().add(element);
            myFirstP[0] = false;
        }
    }

    private static class TableCellParaContainer implements ParaContainer {
        private final TableCell myNode;
        private final Tc myTc;
        private final DocxRendererContext myDocx;
        private final boolean[] myFirstP;

        public TableCellParaContainer(final TableCell node, final Tc tc, final DocxRendererContext docx, final boolean[] firstP) {
            myNode = node;
            myTc = tc;
            myDocx = docx;
            myFirstP = firstP;
        }

        @Override
        public void addP(final P p) {
            myFirstP[0] = false;
            myTc.getContent().add(p);
        }

        @Override
        public P getLastP() {
            final List<Object> content = myTc.getContent();
            if (myFirstP[0] && (content == null || content.size() == 0)) {
                // Create object for p
                P p = myDocx.createP();
                PPr ppr = p.getPPr();

                // Create object for jc

                if (myNode.getAlignment() != null) {
                    JcEnumeration alignValue = getAlignValue(myNode.getAlignment());
                    Jc jc3 = myDocx.getFactory().createJc();
                    ppr.setJc(jc3);
                    jc3.setVal(alignValue);
                }

                myFirstP[0] = false;
            }

            if (content == null || content.size() == 0) return null;
            final Object o = content.get(content.size() - 1);
            return o instanceof P ? (P) o : null;
        }
    }
}
