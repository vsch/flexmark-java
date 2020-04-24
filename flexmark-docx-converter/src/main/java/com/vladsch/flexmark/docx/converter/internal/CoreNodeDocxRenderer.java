package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.docx.converter.*;
import com.vladsch.flexmark.docx.converter.util.*;
import com.vladsch.flexmark.ext.aside.AsideBlock;
import com.vladsch.flexmark.ext.attributes.AttributeNode;
import com.vladsch.flexmark.ext.attributes.AttributesNode;
import com.vladsch.flexmark.ext.emoji.Emoji;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.emoji.internal.EmojiOptions;
import com.vladsch.flexmark.ext.emoji.internal.EmojiResolvedShortcut;
import com.vladsch.flexmark.ext.enumerated.reference.*;
import com.vladsch.flexmark.ext.enumerated.reference.internal.EnumRefTextCollectingVisitor;
import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.footnotes.internal.FootnoteRepository;
import com.vladsch.flexmark.ext.gfm.strikethrough.Strikethrough;
import com.vladsch.flexmark.ext.gfm.strikethrough.Subscript;
import com.vladsch.flexmark.ext.gitlab.GitLabBlockQuote;
import com.vladsch.flexmark.ext.gitlab.GitLabDel;
import com.vladsch.flexmark.ext.gitlab.GitLabIns;
import com.vladsch.flexmark.ext.ins.Ins;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTag;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagBlock;
import com.vladsch.flexmark.ext.macros.MacroDefinitionBlock;
import com.vladsch.flexmark.ext.macros.MacroReference;
import com.vladsch.flexmark.ext.superscript.Superscript;
import com.vladsch.flexmark.ext.tables.*;
import com.vladsch.flexmark.ext.toc.SimTocBlock;
import com.vladsch.flexmark.ext.toc.TocBlock;
import com.vladsch.flexmark.ext.toc.TocBlockBase;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableScopedDataSet;
import com.vladsch.flexmark.util.data.NullableDataKey;
import com.vladsch.flexmark.util.format.options.ListSpacing;
import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.misc.ImageUtils;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Escaping;
import org.docx4j.UnitsOfMeasurement;
import org.docx4j.dml.wordprocessingDrawing.Anchor;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.model.structure.PageDimensions;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.model.styles.StyleUtil;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.AltChunkType;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.toc.TocException;
import org.docx4j.toc.TocGenerator;
import org.docx4j.wml.*;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.JAXBElement;
import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

import static com.vladsch.flexmark.html.renderer.LinkStatus.UNKNOWN;
import static com.vladsch.flexmark.util.html.Attribute.CLASS_ATTR;

@SuppressWarnings({ "WeakerAccess", "MethodMayBeStatic", "OverlyCoupledClass" })
public class CoreNodeDocxRenderer implements PhasedNodeDocxRenderer {
    final public static DataKey<Integer> LIST_ITEM_NUMBER = new DataKey<>("LIST_ITEM_NUMBER", 0);
    final public static NullableDataKey<ListSpacing> LIST_ITEM_SPACING = new NullableDataKey<>("LIST_ITEM_SPACING");
    final public static HashSet<DocxRendererPhase> RENDERING_PHASES = new HashSet<>(Arrays.asList(
            DocxRendererPhase.COLLECT,
            DocxRendererPhase.DOCUMENT_TOP,
            DocxRendererPhase.DOCUMENT_BOTTOM
    ));
    final public static String INPUT_TYPE_DROPDOWN = "dropdown";
    final public static String INPUT_TYPE_CHECKBOX = "checkbox";
    final public static String INPUT_TYPE_TEXT = "text";

    final public static String INPUT_CLASS_TEXT = "text";  // class name for input type
    final public static String INPUT_CLASS_DROPDOWN = "dropdown";  // class name for input type
    final public static String INPUT_CLASS_CHECKBOX = "checkbox";  // class name for input type

    protected final ReferenceRepository referenceRepository;

    DocxRendererOptions options;
    final EmojiOptions emojiOptions;
    final private ListOptions listOptions;
    protected boolean recheckUndefinedReferences;
    protected boolean repositoryNodesDone;
    protected final boolean linebreakOnInlineHtmlBr;
    protected final boolean tableCaptionToParagraph;
    protected final boolean tableCaptionBeforeTable;
    protected final int tablePreferredWidthPct;
    protected final int tableLeftIndent;
    protected final String tableStyle;
    private int imageId;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection") final private HashMap<Node, BigInteger> footnoteIDs; // cannot re-use footnote ids, so this is dead code, left in for future if needed
    private TocBlockBase lastTocBlock;
    private long[] numberedLists = new long[128];
    private long[] bulletLists = new long[128];
    private EnumeratedReferences enumeratedOrdinals;
    Runnable ordinalRunnable;
    final private HtmlIdGenerator headerIdGenerator; // used for enumerated text reference
    final private HashMap<String, Integer> formControlCounts = new HashMap<>();
//    final HashMap<JekyllTagBlock, Document> includedDocumentsMap = new HashMap<>();
//    Parser parser;

    final private FootnoteRepository footnoteRepository;

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
        recheckUndefinedReferences = DocxRenderer.RECHECK_UNDEFINED_REFERENCES.get(options);
        linebreakOnInlineHtmlBr = DocxRenderer.LINEBREAK_ON_INLINE_HTML_BR.get(options);
        tableCaptionToParagraph = DocxRenderer.TABLE_CAPTION_TO_PARAGRAPH.get(options);
        tableCaptionBeforeTable = DocxRenderer.TABLE_CAPTION_BEFORE_TABLE.get(options);
        tablePreferredWidthPct = DocxRenderer.TABLE_PREFERRED_WIDTH_PCT.get(options);
        tableLeftIndent = DocxRenderer.TABLE_LEFT_INDENT.get(options);
        tableStyle = DocxRenderer.TABLE_STYLE.get(options);
        repositoryNodesDone = false;
        footnoteRepository = FootnoteExtension.FOOTNOTES.get(options);
        footnoteRepository.resolveFootnoteOrdinals();

        this.options = new DocxRendererOptions(options);
        listOptions = ListOptions.get(options);
        footnoteIDs = new HashMap<>();
        lastTocBlock = null;
        ordinalRunnable = null;

        MutableScopedDataSet options1 = new MutableScopedDataSet(options);
        options1.set(EmojiExtension.ROOT_IMAGE_PATH, DocxRenderer.DOC_EMOJI_ROOT_IMAGE_PATH.get(options));
        emojiOptions = new EmojiOptions(options1);
        headerIdGenerator = new HeaderIdGenerator.Factory().create();

        // start at 100000, hoping to avoid conflicts with ids already in the document
        imageId = 100000;
    }

    @Override
    public Set<DocxRendererPhase> getFormattingPhases() {
        return RENDERING_PHASES;
    }

    @Override
    public void renderDocument(DocxRendererContext docx, Document document, DocxRendererPhase phase) {
        // here non-rendered elements can be collected so that they are rendered in another part of the document
        switch (phase) {
            case COLLECT:
                // get resolved styles
                options = docx.getDocxRendererOptions();
                headerIdGenerator.generateIds(document);
                break;

            case DOCUMENT_TOP:
                enumeratedOrdinals = EnumeratedReferenceExtension.ENUMERATED_REFERENCE_ORDINALS.get(document);
                FootnoteRepository.resolveFootnotes(document);
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

    public ReferenceRepository getRepository(DataHolder options) {
        return Parser.REFERENCES.get(options);
    }

    @Override
    public Set<NodeDocxRendererHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<>(Arrays.asList(
                // Generic unknown node formatter
                new NodeDocxRendererHandler<>(Node.class, CoreNodeDocxRenderer.this::render),

                // core and extension nodes
                new NodeDocxRendererHandler<>(AsideBlock.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(AttributeNode.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(AttributesNode.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(AutoLink.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(BlankLine.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(BlockQuote.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(BulletList.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(BulletListItem.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(Code.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(Document.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(Emoji.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(Emphasis.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(EnumeratedReferenceBlock.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(EnumeratedReferenceLink.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(EnumeratedReferenceText.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(FencedCodeBlock.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(Footnote.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(FootnoteBlock.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(GitLabBlockQuote.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(GitLabDel.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(GitLabIns.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(HardLineBreak.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(Heading.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(HtmlBlock.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(HtmlCommentBlock.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(HtmlEntity.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(HtmlInline.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(HtmlInlineComment.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(HtmlInnerBlock.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(HtmlInnerBlockComment.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(Image.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(ImageRef.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(IndentedCodeBlock.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(Ins.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(JekyllTag.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(JekyllTagBlock.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(Link.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(LinkRef.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(MacroDefinitionBlock.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(MacroReference.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(MailLink.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(OrderedList.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(OrderedListItem.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(Paragraph.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(Reference.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(SimTocBlock.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(SoftLineBreak.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(Strikethrough.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(StrongEmphasis.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(StrongEmphasis.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(Subscript.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(Superscript.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(TableBlock.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(TableBody.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(TableCaption.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(TableCell.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(TableHead.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(TableRow.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(TableSeparator.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(Text.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(TextBase.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(ThematicBreak.class, CoreNodeDocxRenderer.this::render),
                new NodeDocxRendererHandler<>(TocBlock.class, CoreNodeDocxRenderer.this::render)
        ));
    }

    private void render(Node node, DocxRendererContext docx) {
        BasedSequence chars = node.getChars();
        MainDocumentPart mdp = docx.getDocxDocument();
        if (node instanceof Block) {
            docx.setBlockFormatProvider(new BlockFormatProviderBase<>(docx, docx.getDocxRendererOptions().LOOSE_PARAGRAPH_STYLE));
            docx.createP();
            docx.renderChildren(node);
        } else {
            docx.addTextCreateR(chars.unescape());
        }
    }

    private void render(BlankLine node, DocxRendererContext docx) {
        // not rendered
    }

    private void render(Document node, DocxRendererContext docx) {
        // No rendering itself
        docx.renderChildren(node);
    }

    private boolean hasRenderingAttribute(Node node) {
        boolean pageBreak = false;

        for (Node child : node.getChildren()) {
            if (child instanceof AttributesNode) {
                AttributesNode attributesNode = (AttributesNode) child;
                for (Node attribute : attributesNode.getChildren()) {
                    if (attribute instanceof AttributeNode) {
                        AttributeNode attributeNode = (AttributeNode) attribute;
                        if (attributeNode.isClass())
                            switch (attributeNode.getValue().toString()) {
                                case "pagebreak":
                                case "tab":
                                    return true;

                                default:
                                    break;
                            }
                    }
                }
            }
        }
        return false;
    }

    private void render(Paragraph node, DocxRendererContext docx) {
        if (node.getParent() instanceof EnumeratedReferenceBlock) {
            // we need to unwrap the paragraphs
            addBlockAttributeFormatting(node, AttributablePart.NODE, docx, false);
            docx.renderChildren(node);
        } else if (!(node.getParent() instanceof ParagraphItemContainer) || !((ParagraphItemContainer) node.getParent()).isItemParagraph(node)) {
            if (node.getParent() instanceof BlockQuote || node.getParent() instanceof AsideBlock) {
                // the parent handles our formatting
                addBlockAttributeFormatting(node, AttributablePart.NODE, docx, true);
                docx.renderChildren(node);
            } else {
                if (node.getFirstChildAnyNot(NonRenderingInline.class) != null || hasRenderingAttribute(node)) {
                    docx.setBlockFormatProvider(new BlockFormatProviderBase<>(docx, docx.getDocxRendererOptions().LOOSE_PARAGRAPH_STYLE));
                    addBlockAttributeFormatting(node, AttributablePart.NODE, docx, true);
                    docx.renderChildren(node);
                }
            }
        } else {
            // the parent handles our formatting
            // for footnotes there is already an open paragraph, re-use it
            addBlockAttributeFormatting(node, AttributablePart.NODE, docx, !(node.getParent() instanceof FootnoteBlock));
            docx.renderChildren(node);
        }
    }

    private void render(Text node, DocxRendererContext docx) {
        addRunAttributeFormatting(node, docx);
        docx.addTextCreateR(node.getChars().unescape());
    }

    private void render(TextBase node, DocxRendererContext docx) {
        addRunAttributeFormatting(node, docx);
        docx.renderChildren(node);
    }

    private void addRunAttributeFormatting(Node node, DocxRendererContext docx) {
        Pair<String, AttributeFormat> format = getAttributeFormat(node, AttributablePart.NODE, docx);
        addRunAttributeFormatting(format.getSecond(), docx);
    }

    private void addRunAttributeFormatting(AttributeFormat attributeFormat, DocxRendererContext docx) {
        if (attributeFormat != null) {
            docx.setRunFormatProvider(new AttributeRunFormatProvider<>(docx, attributeFormat));
        }
    }

    void addBlockAttributeFormatting(Node node, AttributablePart part, DocxRendererContext docx, boolean createP) {
        Pair<String, AttributeFormat> format = getAttributeFormat(node, AttributablePart.NODE, docx);
        if (format.getSecond() != null) {
            if (createP) docx.setBlockFormatProvider(new AttributeBlockFormatProvider<>(docx, format.getSecond()));
            docx.setRunFormatProvider(new AttributeRunFormatProvider<>(docx, format.getSecond()));
        }

        if (createP) docx.createP(format.getFirst());
    }

    Pair<String, AttributeFormat> getAttributeFormat(Node node, AttributablePart part, DocxRendererContext docx) {
        Attributes attributes = docx.extendRenderingNodeAttributes(node, AttributablePart.NODE, null);
        // see if has class which we interpret as style id
        Attribute classAttribute = attributes.get(CLASS_ATTR);
        String className = null;
        if (classAttribute != null && !classAttribute.getValue().trim().isEmpty()) {
            String[] classNames = classAttribute.getValue().trim().split(" ");

            // first class is main style name unless it is pagebreak or tab
            if (!classNames[0].equals("pagebreak") && !classNames[0].equals("tab")) {
                className = classNames[0];
            }
        }

        AttributeFormat attributeFormat = getAttributeFormat(attributes, docx);
        return Pair.of(className, attributeFormat);
    }

    AttributeFormat getAttributeFormat(Attributes attributes, DocxRendererContext docx) {
        // need to convert style= attributes to formatting:
        // color: to text color
        // background-color: to text background
        // font-size: to font size
        // font-weight: to font weight bold/normal/light
        // font-style: to font style normal/italic
        String fontFamily = null;
        String fontSize = null;
        String fontWeight = null;
        String fontStyle = null;
        String textColor = null;
        String fillColor = null;
        AttributeFormat attributeFormat = null;

        Attribute style = attributes.get(Attribute.STYLE_ATTR);
        if (style != null && !style.getValue().trim().isEmpty()) {
            String value = style.getValue().trim();
            String[] styleValues = value.split(";");
            for (String styleValue : styleValues) {
                String[] attrParts = styleValue.trim().split(":", 2);
                String attrKey = attrParts[0].trim();
                String attrValue = attrParts.length > 1 ? attrParts[1].trim() : null;

                if (!attrKey.isEmpty() && attrValue != null && !attrValue.isEmpty()) {
                    switch (attrKey) {
                        case "color":
                            textColor = attrValue;
                            break;

                        case "background-color":
                            fillColor = attrValue;
                            break;

                        case "font-family":
                            fontFamily = attrValue;
                            break;

                        case "font-size":
                            fontSize = attrValue;
                            break;

                        case "font-weight":
                            fontWeight = attrValue;
                            break;

                        case "font-style":
                            fontStyle = attrValue;
                            break;

                        default:
                            break;
                    }
                }
            }

            attributeFormat = new AttributeFormat(
                    fontFamily,
                    fontSize,
                    fontWeight,
                    fontStyle,
                    textColor,
                    fillColor
            );

            if (attributeFormat.isEmpty()) {
                attributeFormat = null;
            }
        }

        return attributeFormat;
    }

    private void render(Emphasis node, DocxRendererContext docx) {
        docx.setRunFormatProvider(new ItalicRunFormatProvider<>(docx, options.noCharacterStyles));
        addRunAttributeFormatting(node, docx);
        docx.renderChildren(node);
    }

    private void render(StrongEmphasis node, DocxRendererContext docx) {
        docx.setRunFormatProvider(new BoldRunFormatProvider<>(docx, options.noCharacterStyles));
        addRunAttributeFormatting(node, docx);
        docx.renderChildren(node);
    }

    private void render(Subscript node, DocxRendererContext docx) {
        docx.setRunFormatProvider(new SubscriptRunFormatProvider<>(docx, options.noCharacterStyles));
        addRunAttributeFormatting(node, docx);
        docx.renderChildren(node);
    }

    private void render(Superscript node, DocxRendererContext docx) {
        docx.setRunFormatProvider(new SuperscriptRunFormatProvider<>(docx, options.noCharacterStyles));
        addRunAttributeFormatting(node, docx);
        docx.renderChildren(node);
    }

    private void render(Strikethrough node, DocxRendererContext docx) {
        docx.setRunFormatProvider(new StrikethroughRunFormatProvider<>(docx, options.noCharacterStyles));
        addRunAttributeFormatting(node, docx);
        docx.renderChildren(node);
    }

    private void render(GitLabDel node, DocxRendererContext docx) {
        docx.setRunFormatProvider(new StrikethroughRunFormatProvider<>(docx, options.noCharacterStyles));
        addRunAttributeFormatting(node, docx);
        docx.renderChildren(node);
    }

    private void render(Ins node, DocxRendererContext docx) {
        docx.setRunFormatProvider(new UnderlineRunFormatProvider<>(docx, options.noCharacterStyles));
        addRunAttributeFormatting(node, docx);
        docx.renderChildren(node);
    }

    private void render(GitLabIns node, DocxRendererContext docx) {
        docx.setRunFormatProvider(new UnderlineRunFormatProvider<>(docx, options.noCharacterStyles));
        addRunAttributeFormatting(node, docx);
        docx.renderChildren(node);
    }

    private void render(Code node, DocxRendererContext docx) {
        docx.setRunFormatProvider(new SourceCodeRunFormatProvider<>(docx, options.noCharacterStyles, options.codeHighlightShading));
        addRunAttributeFormatting(node, docx);
        docx.renderChildren(node);
    }

    private void render(Heading node, DocxRendererContext docx) {
        docx.setBlockFormatProvider(new HeadingBlockFormatProvider<>(docx, node.getLevel() - 1));
        addBlockAttributeFormatting(node, AttributablePart.NODE, docx, false);
        docx.renderChildren(node);
    }

    private void render(BlockQuote node, DocxRendererContext docx) {
        int level = node.countDirectAncestorsOfType(null, BlockQuote.class) + 1;
        docx.setBlockFormatProvider(new QuotedFormatProvider<>(docx, level, docx.getRenderingOptions().BLOCK_QUOTE_STYLE));
        addBlockAttributeFormatting(node, AttributablePart.NODE, docx, false);
        docx.renderChildren(node);
    }

    private void render(GitLabBlockQuote node, DocxRendererContext docx) {
        int level = node.countDirectAncestorsOfType(null, BlockQuote.class) + 1;
        docx.setBlockFormatProvider(new QuotedFormatProvider<>(docx, level, docx.getRenderingOptions().BLOCK_QUOTE_STYLE));
        addBlockAttributeFormatting(node, AttributablePart.NODE, docx, false);
        docx.renderChildren(node);
    }

    private void render(AsideBlock node, DocxRendererContext docx) {
        int level = node.countDirectAncestorsOfType(null, BlockQuote.class) + 1;
        docx.setBlockFormatProvider(new QuotedFormatProvider<>(docx, level, docx.getRenderingOptions().ASIDE_BLOCK_STYLE));
        addBlockAttributeFormatting(node, AttributablePart.NODE, docx, false);
        docx.renderChildren(node);
    }

    private void render(ThematicBreak node, DocxRendererContext docx) {
        // Create object for p
        docx.setBlockFormatProvider(new BlockFormatProviderBase<>(docx, docx.getDocxRendererOptions().HORIZONTAL_LINE_STYLE));

        // Create object for r
        docx.createP();
        docx.createR();
    }

    private void render(MacroReference node, DocxRendererContext docx) {
        MacroDefinitionBlock macroDefinitionBlock = node.getReferenceNode(docx.getDocument());
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
            docx.addTextCreateR(node.getChars().unescape());
        }
    }

    private void render(MacroDefinitionBlock node, DocxRendererContext docx) {

    }

    private void render(FencedCodeBlock node, DocxRendererContext docx) {
        List<BasedSequence> lines = node.getContentLines();
        addBlockAttributeFormatting(node, AttributablePart.NODE, docx, false);
        docx.renderFencedCodeLines(lines);
    }

    private void render(IndentedCodeBlock node, DocxRendererContext docx) {
        List<BasedSequence> lines = node.getContentLines();
        addBlockAttributeFormatting(node, AttributablePart.NODE, docx, false);
        docx.renderFencedCodeLines(lines);
    }

    public void renderList(ListBlock node, DocxRendererContext docx) {
        addBlockAttributeFormatting(node, AttributablePart.NODE, docx, false);
        docx.renderChildren(node);
    }

    private void render(BulletList node, DocxRendererContext docx) {
        renderList(node, docx);
    }

    private void render(OrderedList node, DocxRendererContext docx) {
        renderList(node, docx);
    }

    private void render(BulletListItem node, DocxRendererContext docx) {
        renderListItem(node, docx);
    }

    private void render(OrderedListItem node, DocxRendererContext docx) {
        renderListItem(node, docx);
    }

    private void renderListItem(ListItem node, DocxRendererContext docx) {
        int nesting = node.countDirectAncestorsOfType(ListItem.class, BulletList.class, OrderedList.class);
        DocxRendererOptions options = docx.getDocxRendererOptions();
        String listSpacingStyle = listOptions.isTightListItem(node) ? options.TIGHT_PARAGRAPH_STYLE : options.LOOSE_PARAGRAPH_STYLE;

        //final boolean inBlockQuote = node.getAncestorOfType(BlockQuote.class) != null;
        boolean wantNumbered = node instanceof OrderedListItem;

        String listParagraphStyleId = wantNumbered ? options.PARAGRAPH_NUMBERED_LIST_STYLE : options.PARAGRAPH_BULLET_LIST_STYLE;
        Style listParagraphStyle = docx.getHelper().getStyle(listParagraphStyleId);
        if (listParagraphStyle == null) {
            listParagraphStyleId = listSpacingStyle;
        }

        long numId = (wantNumbered ? 3 : 2);// + (inBlockQuote ? 2 : 0);
        int listLevel = nesting - 1;

        // see if have ListBullet style for unordered list and ListNumber for numbered list
        String listStyleId = wantNumbered ? options.NUMBERED_LIST_STYLE : options.BULLET_LIST_STYLE;

        Style listStyle = docx.getHelper().getStyle(listStyleId);
        if (listStyle != null) {
            PPrBase.NumPr numPr = listStyle.getPPr().getNumPr();
            if (numPr != null) {
                numId = numPr.getNumId().getVal().longValue();
            }
        }

        int newNum = 1;
        NumberingDefinitionsPart ndp = docx.getDocxDocument().getNumberingDefinitionsPart();
        long idNum = numId;

        if (node.getParent() instanceof OrderedList) {
            if (node == node.getParent().getFirstChild()) {
                newNum = listOptions.isOrderedListManualStart() ? ((OrderedList) node.getParent()).getStartNumber() : 1;
                idNum = ndp.restart(numId, listLevel, newNum);
                ensureNumberedListLength(listLevel);
                numberedLists[listLevel] = idNum;
            } else {
                idNum = numberedLists[listLevel];
            }
        } else if (node.getParent() instanceof BulletList) {
            if (node == node.getParent().getFirstChild()) {
                ensureBulletListLength(listLevel);
                bulletLists[listLevel] = idNum;
            } else {
                idNum = bulletLists[listLevel];
            }
        }

        docx.setBlockFormatProvider(new ListItemBlockFormatProvider<>(docx, listParagraphStyleId, listSpacingStyle, idNum, listLevel, ListItem.class, ListBlock.class));
        addBlockAttributeFormatting(node, AttributablePart.NODE, docx, false);
        docx.renderChildren(node);
    }

    private void render(SoftLineBreak node, DocxRendererContext docx) {
        docx.addTextCreateR(" ");
    }

    private void render(HardLineBreak node, DocxRendererContext docx) {
        // add a line break
        docx.addLineBreak();
    }

    private void render(HtmlBlock node, DocxRendererContext docx) {
        if (node.hasChildren()) {
            // inner blocks handle rendering
            docx.renderChildren(node);
        } else {
            renderHtmlBlock(node, docx, docx.getDocxRendererOptions().suppressHtmlBlocks, true /*|| docx.getDocxRendererOptions().escapeHtmlBlocks*/);
        }
    }

    private void render(HtmlCommentBlock node, DocxRendererContext docx) {
        renderHtmlBlock(node, docx, docx.getDocxRendererOptions().suppressHtmlCommentBlocks, true /*|| docx.getDocxRendererOptions().escapeHtmlCommentBlocks*/);
    }

    private void render(HtmlInnerBlock node, DocxRendererContext docx) {
        renderHtmlBlock(node, docx, docx.getDocxRendererOptions().suppressHtmlBlocks, true /*|| docx.getDocxRendererOptions().escapeHtmlBlocks*/);
    }

    private void render(HtmlInnerBlockComment node, DocxRendererContext docx) {
        renderHtmlBlock(node, docx, docx.getDocxRendererOptions().suppressHtmlCommentBlocks, true /*|| docx.getDocxRendererOptions().escapeHtmlCommentBlocks*/);
    }

    public void renderHtmlBlock(HtmlBlockBase node, DocxRendererContext docx, boolean suppress, boolean escape) {
        if (linebreakOnInlineHtmlBr && node.getChars().unescape().trim().matches("<br\\s*/?>")) {
            // start a new hard-break
            docx.createP();
        } else {
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
                //docx.addTextCreateR(normalizeEOL);
            } else {
                try {
                    docx.getDocxDocument().addAltChunk(AltChunkType.Html, node.getChars().toString().getBytes(StandardCharsets.UTF_8));
                } catch (Docx4JException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void render(HtmlInline node, DocxRendererContext docx) {
        //if (docx.getDocxRendererOptions().sourceWrapInlineHtml) {
        //    html.srcPos(node.getChars()).withAttr(AttributablePart.NODE_POSITION).tag("span");
        //}
        renderInlineHtml(node, docx, docx.getDocxRendererOptions().suppressInlineHtml, true /*|| docx.getDocxRendererOptions().escapeInlineHtml*/);
        //if (docx.getDocxRendererOptions().sourceWrapInlineHtml) {
        //    html.tag("/span");
        //}
    }

    private void render(HtmlInlineComment node, DocxRendererContext docx) {
        renderInlineHtml(node, docx, docx.getDocxRendererOptions().suppressInlineHtmlComments, true /*|| docx.getDocxRendererOptions().escapeInlineHtmlComments*/);
    }

    public void renderInlineHtml(HtmlInlineBase node, DocxRendererContext docx, boolean suppress, boolean escape) {
        if (linebreakOnInlineHtmlBr && node.getChars().unescape().matches("<br\\s*/?>")) {
            // start a new hard-break
            docx.addLineBreak();
        } else {
            if (suppress) return;

            if (escape) {
                // render as inline code
                docx.contextFramed(() -> {
                    docx.setRunFormatProvider(new SourceCodeRunFormatProvider<>(docx, options.noCharacterStyles, options.codeHighlightShading));
                    docx.addTextCreateR(node.getChars().normalizeEOL());
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

    private void render(Reference node, DocxRendererContext docx) {

    }

    private void render(HtmlEntity node, DocxRendererContext docx) {
        docx.addTextCreateR(node.getChars().unescape());
    }

    private void renderURL(BasedSequence urlSource, DocxRendererContext docx, String linkUrl, String linkText) {
        renderURL(urlSource, docx, linkUrl, null, new UrlRenderer(docx, linkText, linkUrl));
    }

    private void renderURL(BasedSequence urlSrc, DocxRendererContext docx, String linkUrl, Attributes attributes, Runnable runnable) {
        P p = docx.getP();

        String linkTitle = attributes != null && attributes.contains(Attribute.TITLE_ATTR) ? attributes.getValue(Attribute.TITLE_ATTR) : "";

        P.Hyperlink hyperlink = docx.getFactory().createPHyperlink();
        JAXBElement<P.Hyperlink> wrappedHyperlink = docx.getFactory().createPHyperlink(hyperlink);
        p.getContent().add(wrappedHyperlink);
        String highlightMissing = "";
        String linkTooltipText = linkTitle;
        String linkUrlText = linkUrl;
        String useLinkUrl = linkUrl;

        // remove <> from link address
        if (useLinkUrl.startsWith("<")) useLinkUrl = useLinkUrl.substring(1);
        if (useLinkUrl.endsWith(">")) useLinkUrl = useLinkUrl.substring(0, useLinkUrl.length() - 1);

        if (useLinkUrl.startsWith("#")) {
            // local, we use anchor

            // see if we need to adjust it
            String nodeId = useLinkUrl.substring(1);
            if (docx.getNodeFromId(nodeId) == null) {
                highlightMissing = docx.getRenderingOptions().localHyperlinkMissingHighlight;
                linkTooltipText = String.format(docx.getRenderingOptions().localHyperlinkMissingFormat, nodeId);

                if (docx.getDocxRendererOptions().errorsToStdErr) {
                    // output details of error
                    Pair<Integer, Integer> atIndex = urlSrc.baseLineColumnAtStart();
                    String sourceFile = docx.getDocxRendererOptions().errorSourceFile;
                    if (sourceFile.isEmpty()) {
                        sourceFile = "on line ";
                    } else {
                        sourceFile = String.format("in %s:", sourceFile);
                    }
                    System.err.println(String.format(Locale.US, "    WARN: Invalid anchor target '%s' %s%d:%d", nodeId, sourceFile, atIndex.getFirst() + 1, atIndex.getSecond() + 2));
                }
            }
            String anchor = docx.getValidBookmarkName(nodeId) + options.localHyperlinkSuffix;
            hyperlink.setAnchor(anchor);
            linkUrlText = String.format("#%s", anchor);
        } else {
            Relationship rel = docx.getHyperlinkRelationship(useLinkUrl);
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

    private void render(AutoLink node, DocxRendererContext docx) {
        String url = node.getText().unescape();
        addRunAttributeFormatting(node, docx);
        renderURL(node.getChars(), docx, url, url);
    }

    private void render(MailLink node, DocxRendererContext docx) {
        addRunAttributeFormatting(node, docx);
        String text = node.getText().unescape();
        renderURL(node.getChars(), docx, "mailto:" + text, text);
    }

    private void render(Link node, DocxRendererContext docx) {
        ResolvedLink resolvedLink = docx.resolveLink(LinkType.LINK, node.getUrl().unescape(), null, null);

        // we have a title part, use that
        Attributes attributes = resolvedLink.getNonNullAttributes();

        if (node.getTitle().isNotNull()) {
            attributes.replaceValue(Attribute.TITLE_ATTR, node.getTitle().unescape());
        } else {
            attributes.remove(Attribute.TITLE_ATTR);
        }

        attributes = docx.extendRenderingNodeAttributes(AttributablePart.NODE, attributes);

        addRunAttributeFormatting(node, docx);
        renderURL(node.getUrl(), docx, resolvedLink.getUrl(), attributes, new ChildRenderer(docx, node));
    }

    private void renderInputField(LinkRef node, DocxRendererContext docx) {
        // form
        Attributes attributes = docx.extendRenderingNodeAttributes(node, AttributablePart.NODE, null);
        String inputType = INPUT_TYPE_TEXT;

        if (attributes.contains(CLASS_ATTR)) {
            Attribute classAttr = attributes.get(CLASS_ATTR);

            if (!classAttr.containsValue(INPUT_CLASS_TEXT)) {
                if (classAttr.containsValue(INPUT_CLASS_DROPDOWN)) {
                    inputType = INPUT_TYPE_DROPDOWN;
                } else if (classAttr.containsValue(INPUT_CLASS_CHECKBOX)) {
                    inputType = INPUT_TYPE_CHECKBOX;
                }
            } else {
                inputType = INPUT_TYPE_TEXT;
            }
        }

        int fieldCount = formControlCounts.computeIfAbsent(inputType, k -> 0);
        fieldCount++;
        formControlCounts.put(inputType, fieldCount);

        String docxFieldType;
        String defaultFieldName;

        switch (inputType) {
            // @formatter:off
            case INPUT_TYPE_CHECKBOX: defaultFieldName = "Check"; break;
            case INPUT_TYPE_DROPDOWN: defaultFieldName = "Dropdown"; break;
            // @formatter:on
            default:
            case INPUT_TYPE_TEXT:
                defaultFieldName = "Text";
                String type = attributes.getValue("type").trim();
                if (!type.isEmpty()) {
                    switch (type) {
                        // @formatter:off
                        case "date": defaultFieldName = "Date"; break;
                        case "number": defaultFieldName = "Number"; break;
                        case "current-date": defaultFieldName = "CurrentDate"; break;
                        case "current-time": defaultFieldName = "CurrentTime"; break;
                        // @formatter:on
                    }
                }
                break;
        }

        // NOTE: common to all controls
        // <w:r>
        //   <w:fldChar w:fldCharType="begin"> for definition
        //     <w:ffData>
        //         <w:name w:val="Name1"/> - name of control
        //         <w:enabled/> - editing enabled,  <w:enabled w:val="0"/> editing disabled
        //         <w:calcOnExit w:val="0"/>
        //         <w:helpText w:type="text" w:val="F1 Help"/> - F1 help key, does not work on Mac, leave out if none
        //         <w:statusText w:type="text" w:val="Status Help"/> - Status Bar help text, leave out if none
        //         NOTE: control specific content
        //     </w:ffData>
        //   </w:fldChar>
        // </w:r>

        ObjectFactory factory = docx.getFactory();

        FldChar fldChar = docx.addFldCharCreateR(STFldCharType.BEGIN);
        CTFFData ffData = factory.createCTFFData();
        fldChar.setFfData(ffData);

        // CTMacroName
        // CTFFName
        // BooleanDefaultTrue
        // CTFFTextInput
        // BooleanDefaultTrue
        // CTFFCheckBox
        // CTFFHelpText
        // CTMacroName
        // CTFFStatusText
        // CTFFDDList

        String fieldName = "";
        if (attributes.contains("name")) {
            fieldName = attributes.getValue("name").trim();
        }
        if (fieldName.isEmpty()) {
            fieldName = String.format("%s%d", defaultFieldName, fieldCount);
        }

        CTFFName ffName = factory.createCTFFName();
        ffName.setVal(fieldName);
        ffData.getNameOrEnabledOrCalcOnExit().add(factory.createCTFFDataName(ffName));
        ffData.getNameOrEnabledOrCalcOnExit().add(factory.createCTFFDataEnabled(new BooleanDefaultTrue()));
        ffData.getNameOrEnabledOrCalcOnExit().add(factory.createCTFFDataCalcOnExit(docx.getBooleanDefaultTrue(false)));

        String fieldHelp = attributes.getValue("help");
        if (!fieldHelp.trim().isEmpty()) {
            CTFFHelpText ffHelpText = factory.createCTFFHelpText();
            CTFFStatusText ctffStatusText = factory.createCTFFStatusText();
            ffHelpText.setVal(fieldHelp);
            ffHelpText.setType(STInfoTextType.TEXT);
            ctffStatusText.setType(STInfoTextType.TEXT);
            ctffStatusText.setVal(fieldHelp);
            ffData.getNameOrEnabledOrCalcOnExit().add(factory.createCTFFDataHelpText(ffHelpText));
            ffData.getNameOrEnabledOrCalcOnExit().add(factory.createCTFFDataStatusText(ctffStatusText));
        }

        Runnable extraContent = null;

        switch (inputType) {
            case INPUT_TYPE_CHECKBOX:
                // <w:checkBox>
                //     <w:sizeAuto/>
                //     <w:default w:val="0"/>
                //     <w:checked w:val="0"/>
                // </w:checkBox>
                //
                // <w:sizeAuto/>  - no manual size set for checkbox
                // <w:default w:val="0"/> - 0: unchecked, 1: checked
                // <w:checked w:val="1"/> - 0: user unchecked, 1: user checked
                docxFieldType = "FORMCHECKBOX";
                CTFFCheckBox checkBox = factory.createCTFFCheckBox();
                checkBox.setDefault(docx.getBooleanDefaultTrue(attributes.contains("checked")));
                checkBox.setSizeAuto(factory.createBooleanDefaultTrue());
                ffData.getNameOrEnabledOrCalcOnExit().add(factory.createCTFFDataCheckBox(checkBox));
                break;

            case INPUT_TYPE_DROPDOWN:
                //
                // <w:ddList>
                //     <w:listEntry w:val="Item 1"/> - option text
                //     <w:listEntry w:val="Item 2"/> - option text
                //     <w:listEntry w:val="Item 3"/> - option text
                // </w:ddList>
                docxFieldType = "FORMDROPDOWN";
                CTFFDDList ddList = factory.createCTFFDDList();
                BasedSequence[] options = BasedSequence.of(attributes.getValue("options")).trim().split("|", 0, BasedSequence.SPLIT_TRIM_PARTS);
                if (options.length > 0) {
                    for (BasedSequence option : options) {
                        if (option.trim().isEmpty()) continue;
                        CTFFDDList.ListEntry listEntry = factory.createCTFFDDListListEntry();
                        listEntry.setVal(option.toString());
                        ddList.getListEntry().add(listEntry);
                    }
                }

                String dropDownDefaultValue = attributes.getValue("default").trim();
                if (!dropDownDefaultValue.isEmpty()) {
                    int iMax = options.length;
                    long defaultIndex = -1;
                    for (int i = 0; i < iMax; i++) {
                        if (options[i].equals(dropDownDefaultValue)) {
                            defaultIndex = i;
                            break;
                        }
                    }
                    if (defaultIndex == -1L) {
                        // try case insensitive
                        for (int i = 0; i < iMax; i++) {
                            if (options[i].toLowerCase().equals(dropDownDefaultValue.toLowerCase())) {
                                defaultIndex = i;
                                break;
                            }
                        }
                    }

                    if (defaultIndex == -1) {
                        // see if it is a number
                        try {
                            defaultIndex = Long.parseLong(dropDownDefaultValue);
                            if (defaultIndex > 0) defaultIndex--;
                        } catch (NumberFormatException ignored) {

                        }
                    }

                    if (defaultIndex >= 0 && defaultIndex < options.length) {
                        BigInteger maxVal = BigInteger.valueOf(defaultIndex);
                        CTFFDDList.Default defaultVal = factory.createCTFFDDListDefault();
                        defaultVal.setVal(maxVal);
                        ddList.setDefault(defaultVal);
                    }
                }

                ffData.getNameOrEnabledOrCalcOnExit().add(factory.createCTFFDataDdList(ddList));
                break;

            default:
            case INPUT_TYPE_TEXT:
                //
                // <w:textInput>
                //     <w:default w:val="Default Text"/> - default text/number/date, leave out if none
                //     <w:maxLength w:val="50"/> - max len, leave out if unlimited
                //     <w:format w:val="FIRST CAPITAL"/> - text, leave out for no format, formats: UPPERCASE, LOWERCASE, FIRST CAPITAL, TITLE CASE
                //     <w:format w:val="yyyy/M/d"/>  - date formats: yyyy/M/d, and other date formats supported by MS-Word
                //     <w:format w:val="#,##0"/>  - number formats: #,###0, and other number formats supported by MS-Word
                //     <w:type w:val="date"/> - leave out for regular text or: date, number
                // </w:textInput>
                docxFieldType = "FORMTEXT";

                CTFFTextInput textInput = factory.createCTFFTextInput();
                String textDefaultValue = attributes.getValue("default").trim();

                String maxLength = attributes.getValue("max-length").trim();
                if (!maxLength.isEmpty()) {
                    long maxLen = -1;
                    try {
                        maxLen = Long.parseLong(maxLength);
                    } catch (NumberFormatException ignored) {

                    }
                    if (maxLen > 0) {
                        BigInteger maxVal = BigInteger.valueOf(maxLen);
                        CTFFTextInput.MaxLength maxLengthValue = factory.createCTFFTextInputMaxLength();
                        maxLengthValue.setVal(maxVal);
                        textInput.setMaxLength(maxLengthValue);
                    }
                }

                String type = attributes.getValue("type").trim();
                String format = attributes.getValue("format").trim();
                String useFormat;
                String typeDefaultValue = null;  // if not null, used if user does not provide default or default is empty

                if (!format.isEmpty()) {
                    if (type.equals("regular")) {
                        // we can check these
                        // "UPPERCASE", "LOWERCASE", "FIRST CAPITAL", "TITLE CASE"
                        switch (format.toLowerCase()) {
                            // @formatter:off
                            case "upper":
                            case "uppercase":
                            case "upper-case":
                            case "upper case":
                                useFormat = "UPPERCASE";
                                break;

                            case "lower":
                            case "lowercase":
                            case "lower-case":
                            case "lower case":
                                useFormat = "LOWERCASE";
                                break;

                            case "first":
                            case "firstcap":
                            case "firstcaps":
                            case "firstcapital":
                            case "first-cap":
                            case "first-caps":
                            case "first-capital":
                            case "first cap":
                            case "first caps":
                            case "first capital":
                                useFormat = "FIRST CAPITAL";
                                break;

                            case "title":
                            case "titlecase":
                            case "title-case":
                            case "title case":
                                useFormat = "TITLE CASE";
                                break;

                            default: case "none": useFormat = "";
                            break;
                            // @formatter:on
                        }
                    } else {
                        useFormat = format;
                    }

                    if (!useFormat.isEmpty()) {
                        CTFFTextInput.Format tiFormat = factory.createCTFFTextInputFormat();
                        tiFormat.setVal(format);
                        textInput.setFormat(tiFormat);
                    }
                } else {
                    useFormat = "";
                }

                STFFTextType stffTextType = null;
                Runnable extraExtraContent;

                switch (type) {
                    case "date":
                        stffTextType = STFFTextType.DATE;
                        extraExtraContent = null;
                        typeDefaultValue = getCurrentDate(useFormat.isEmpty() ? "yyyy/M/d" : useFormat);
                        break;
                    case "number":
                        stffTextType = STFFTextType.NUMBER;
                        typeDefaultValue = "0";
                        extraExtraContent = null;
                        break;
                    case "current-date": {
                        stffTextType = STFFTextType.CURRENT_TIME; // ms word has these reversed time is date and date is time
                        //  NOTE: for current date with format
                        //  <w:r>
                        //      <w:fldChar w:fldCharType="begin"/>
                        //  </w:r>
                        //  <w:r>
                        //      <w:instrText xml:space="preserve"> DATE \@ "yyyy/M/d" </w:instrText>
                        //  </w:r>
                        //  <w:r>
                        //      <w:fldChar w:fldCharType="separate"/>
                        //  </w:r>
                        //  <w:r>
                        //      <w:rPr>
                        //          <w:noProof/>
                        //      </w:rPr>
                        //      <w:instrText>2019/11/10</w:instrText>
                        //  </w:r>
                        //  <w:r>
                        //      <w:fldChar w:fldCharType="end"/>
                        //  </w:r>
                        String currentDate = getCurrentDate(useFormat);

                        typeDefaultValue = currentDate;

                        extraExtraContent = () -> {
                            docx.addFldCharCreateR(STFldCharType.BEGIN);

                            docx.addInstrTextCreateR(String.format(" DATE \\@ \"%s\" ", useFormat));

                            docx.addFldCharCreateR(STFldCharType.SEPARATE);

                            docx.addInstrTextCreateR(currentDate, true);

                            docx.addFldCharCreateR(STFldCharType.END);
                        };
                    }
                    break;

                    case "current-time": {
                        stffTextType = STFFTextType.CURRENT_DATE; // ms word has these reversed time is date and date is time
                        //  NOTE: for current time with format
                        //  <w:r>
                        //      <w:fldChar w:fldCharType="begin"/>
                        //  </w:r>
                        //  <w:r>
                        //      <w:instrText xml:space="preserve"> TIME \@ "yyyy-MM-dd h:mm am/pm" </w:instrText>
                        //  </w:r>
                        //  <w:r>
                        //      <w:fldChar w:fldCharType="separate"/>
                        //  </w:r>
                        //  <w:r>
                        //      <w:rPr>
                        //          <w:noProof/>
                        //      </w:rPr>
                        //      <w:instrText>2019-11-10 8:42 PM</w:instrText>
                        //  </w:r>
                        //  <w:r>
                        //      <w:fldChar w:fldCharType="end"/>
                        //  </w:r>
                        String currentTime = getCurrentTime(useFormat);

                        typeDefaultValue = currentTime;

                        extraExtraContent = () -> {
                            docx.addFldCharCreateR(STFldCharType.BEGIN);

                            docx.addInstrTextCreateR(String.format(" TIME \\@ \"%s\" ", useFormat));

                            docx.addFldCharCreateR(STFldCharType.SEPARATE);

                            docx.addInstrTextCreateR(currentTime, true);

                            docx.addFldCharCreateR(STFldCharType.END);
                        };
                    }
                    break;

                    default:
                        stffTextType = STFFTextType.REGULAR;
                        extraExtraContent = null;
                        break;
                }

                // NOTE: extraExtraContent goes ahead
                //
                // NOTE: for text only
                //
                // <w:r>
                //    <w:fldChar w:fldCharType="separate"/>
                // </w:r>
                // <w:r>
                //   <w:rPr>
                //     <w:noProof/>
                //   </w:rPr>
                //   <w:t>default text</w:t>
                // </w:r>
                if (textDefaultValue.isEmpty() && typeDefaultValue != null) {
                    textDefaultValue = typeDefaultValue;
                }

                CTFFTextInput.Default defaultVal = factory.createCTFFTextInputDefault();
                defaultVal.setVal(textDefaultValue);
                textInput.setDefault(defaultVal);

                String finalTextDefaultValue = textDefaultValue;
                extraContent = () -> {
                    if (extraExtraContent != null) {
                        extraExtraContent.run();
                    }

                    docx.addFldCharCreateR(STFldCharType.SEPARATE);

                    docx.addTextCreateR(finalTextDefaultValue, true);
                };

                CTFFTextType tiType = factory.createCTFFTextType();
                tiType.setVal(stffTextType);
                textInput.setType(tiType);

                ffData.getNameOrEnabledOrCalcOnExit().add(factory.createCTFFDataTextInput(textInput));
                break;
        }

        // NOTE: bookmarks are optional and wrap, the control content, not implemented
        // <w:bookmarkStart w:id="0" w:name="Name1"/> - with control name if it is the first and unique
        //
        // NOTE: for all
        //
        // <w:r>
        //    <w:instrText xml:space="preserve"> FORMCHECKBOX </w:instrText> - NOTE: the spaces around the text: FORMTEXT, FORMDROPDOWN, FORMCHECKBOX
        // </w:r>
        //
        // NOTE: Extra Text Type Content Here
        //
        // NOTE: for all
        //
        // <w:r>
        //    <w:fldChar w:fldCharType="end"/>
        // </w:r>
        //
        //
        // NOTE: closing of bookmark, not implemented
        // <w:bookmarkEnd w:id="0"/>
        //

        docx.addInstrTextCreateR(String.format(" %s ", docxFieldType));

        if (extraContent != null) {
            extraContent.run();
        }

        docx.addFldCharCreateR(STFldCharType.END);
    }

    @NotNull String getCurrentTime(String useFormat) {
        Date date = options.runningTests ? Date.from(Instant.ofEpochSecond(1000230400L)) : new Date();

        // change to fixed date so test results do not change
        String currentTime = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(useFormat);
            currentTime = dateFormat.format(date);
        } catch (Exception ignored) {
        }
        return currentTime;
    }

    @NotNull String getCurrentDate(String useFormat) {
        Date date = options.runningTests ? Date.from(Instant.ofEpochSecond(1000230400L)) : new Date();
        String currentDate = "";
        {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(useFormat);
                currentDate = dateFormat.format(date);
            } catch (Exception ignored) {
            }
        }
        return currentDate;
    }

    private void render(LinkRef node, DocxRendererContext docx) {
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

        addRunAttributeFormatting(node, docx);
        if (resolvedLink == null) {
            // empty ref, we treat it as text
            assert !node.isDefined();

            if (!options.formControls.isEmpty() && node.getReference().equals(options.formControls)) {
                renderInputField(node, docx);
            } else {
                if (!node.hasChildren()) {
                    docx.addTextCreateR(node.getChars().unescape());
                } else {
                    docx.addTextCreateR(node.getChars().prefixOf(node.getChildChars()).unescape());
                    docx.renderChildren(node);
                    docx.addTextCreateR(node.getChars().suffixOf(node.getChildChars()).unescape());
                }
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

    private long getSizeInfo(Attributes attributes, String name, double pageDimension, double scale) {
        double value = -1.0;
        if (scale == 0.0) scale = 1.0;

        if (attributes.contains(name)) {
            String attributeValue = attributes.getValue(name).trim();
            boolean relative = false;
            if (attributeValue.endsWith("%")) {
                // make it relative
                attributeValue = attributeValue.substring(0, attributeValue.length() - 1);
                relative = true;
            }

            try {
                if (attributeValue.endsWith("cm")) {
                    value = Double.parseDouble(attributeValue.substring(0, attributeValue.length() - 2).trim());

                    // convert to twips
                    // 20 twips/pt and 72 pt/inch 2.54 cm/in
                    // assuming 72ppi we have pixel = point
                    value *= 20.0 * 72.0 / 2.54 * scale;
                } else if (attributeValue.endsWith("in")) {
                    value = Double.parseDouble(attributeValue.substring(0, attributeValue.length() - 2).trim());

                    // convert to twips
                    // 20 twips/pt and 72 pt/inch 2.54 cm/in
                    // assuming 72ppi we have pixel = point
                    value *= 20.0 * 72.0 * scale;
                } else {
                    value = Double.parseDouble(attributeValue);

                    // convert to twips assuming 72dpi for pixels
                    // 20 twips/pt and 72 pt/inch
                    // assuming 72ppi we have pixel = point
                    value *= 20.0 * scale;
                }
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
            longValue = (long) value;
        } catch (Throwable ignored) {
            longValue = -1;
        }

        return longValue;
    }

    public R newImage(DocxRendererContext docx, BufferedImage image, String filenameHint, Attributes attributes, int id1, int id2, double scale) {
        try {
            BinaryPartAbstractImage imagePart = null;
            byte[] imageBytes = ImageUtils.getImageBytes(image);
            imagePart = BinaryPartAbstractImage.createImagePart(docx.getPackage(), docx.getContainerPart(), imageBytes);
            Inline inline = null;
            Anchor anchor = null;
            String altText = attributes.contains("alt") ? attributes.getValue("alt") : "";
            List<SectionWrapper> sections = docx.getPackage().getDocumentModel().getSections();
            PageDimensions page = sections.get(sections.size() - 1).getPageDimensions();
            double writableWidthTwips = page.getWritableWidthTwips();

            long cx = getSizeInfo(attributes, "width", page.getWritableWidthTwips(), scale);
            long cy = getSizeInfo(attributes, "height", page.getWritableHeightTwips(), scale);

            // if only one dimension given calculate the other from ratio
            if (cy == -1 && cx != -1) {
                cy = (long) (image.getHeight() * (cx / (image.getWidth() * 1.0)));
            } else if (cx == -1 && cy != -1) {
                cx = (long) (image.getWidth() * (cy / (image.getHeight() * 1.0)));
            }

            // kludge: normally there is no max-width attribute but we can fake it
            long longMaxWidth = getSizeInfo(attributes, "max-width", page.getWritableWidthTwips(), 0);
            int maxWidth = longMaxWidth > 0 && longMaxWidth <= Integer.MAX_VALUE ? (int) longMaxWidth : -1;
            long longMaxheight = getSizeInfo(attributes, "max-height", page.getWritableHeightTwips(), 0);
            int maxheight = longMaxheight > 0 && longMaxheight <= Integer.MAX_VALUE ? (int) longMaxheight : -1;

            long hOffset = 0;
            long vOffset = 0;

            if (attributes.contains("align")) {
                switch (attributes.get("align").getValue()) {
                    case "left":
                        hOffset = Long.MIN_VALUE;
                        break;
                    case "right":
                        hOffset = Long.MAX_VALUE;
                        break;
                    case "center":
                        hOffset = Long.MAX_VALUE / 2;
                        break;
                    default:
                        break;
                }
            }

            if (cx > 0 && cy > 0) {
                // here we need cx & cy in emu which needs conversion from twips
                cx = UnitsOfMeasurement.twipToEMU(cx);
                cy = UnitsOfMeasurement.twipToEMU(cy);
                if (hOffset != 0) {
                    anchor = imagePart.createImageAnchor(filenameHint, altText, id1, id2, cx, cy, false, hOffset, vOffset);
                } else {
                    inline = imagePart.createImageInline(filenameHint, altText, id1, id2, cx, cy, false);
                }
            } else {
                if (cx > 0) {
                    if (hOffset != 0) {
                        anchor = imagePart.createImageAnchor(filenameHint, altText, id1, id2, cx, false, hOffset, vOffset);
                    } else {
                        inline = imagePart.createImageInline(filenameHint, altText, id1, id2, cx, false);
                    }
                } else {
                    if (maxWidth > 0 || maxheight > 0) {
                        if (hOffset != 0) {
                            anchor = imagePart.createImageAnchor(filenameHint, altText, id1, id2, false, maxWidth, maxheight, hOffset, vOffset);
                        } else {
                            inline = imagePart.createImageInline(filenameHint, altText, id1, id2, false, maxWidth, maxheight);
                        }
                    } else {
                        if (hOffset != 0) {
                            anchor = imagePart.createImageAnchor(filenameHint, altText, id1, id2, false, hOffset, vOffset);
                        } else {
                            inline = imagePart.createImageInline(filenameHint, altText, id1, id2, false);
                        }
                    }
                }
            }

            // Now add the inline in w:p/w:r/w:drawing
            org.docx4j.wml.R run = docx.createR();
            org.docx4j.wml.Drawing drawing = docx.getFactory().createDrawing();
            run.getContent().add(drawing);

            drawing.getAnchorOrInline().add(inline != null ? inline : anchor);

            return run;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void render(Image node, DocxRendererContext docx) {
        String altText = new TextCollectingVisitor().collectAndGetText(node);
        ResolvedLink resolvedLink = docx.resolveLink(LinkType.IMAGE, node.getUrl().unescape(), null, null);
        Attributes attributes = resolvedLink.getNonNullAttributes();

        if (!node.getUrlContent().isEmpty()) {
            // reverse URL encoding of =, &
            String content = Escaping.percentEncodeUrl(node.getUrlContent()).replace("+", "%2B").replace("%3D", "=").replace("%26", "&amp;");
            resolvedLink = resolvedLink.withUrl(resolvedLink.getUrl() + content);
        }

        if (!altText.isEmpty()) {
            attributes.replaceValue("alt", altText);
        }

        attributes = docx.extendRenderingNodeAttributes(AttributablePart.NODE, attributes);

        //String alt = node.getText().unescape();
        renderImage(docx, resolvedLink, attributes, 1.0);
    }

    private void render(Emoji node, DocxRendererContext docx) {
        EmojiResolvedShortcut shortcut = EmojiResolvedShortcut.getEmojiText(node, emojiOptions.useShortcutType, emojiOptions.useImageType, emojiOptions.rootImagePath);

        if (shortcut.emoji == null || shortcut.emojiText == null) {
            // output as text
            docx.addTextCreateR(":");
            docx.renderChildren(node);
            docx.addTextCreateR(":");
        } else {
            if (shortcut.isUnicode) {
                docx.addTextCreateR(shortcut.emojiText);
            } else {
                ResolvedLink resolvedLink = docx.resolveLink(LinkType.IMAGE, shortcut.emojiText, null, null);
                //String altText = shortcut.alt;
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
                HpsMeasure sz = rPr.getSz();
                long l = -1;
                if (sz != null) {
                    l = sz.getVal().longValue();
                    attributes.replaceValue("height", String.valueOf(Math.round(l / 2.0 * options.docEmojiImageVertSize)));
                    attributes.replaceValue("width", String.valueOf(Math.round(l / 2.0 * options.docEmojiImageVertSize)));
                } else if (!emojiOptions.attrImageSize.isEmpty()) {
                    attributes.replaceValue("height", emojiOptions.attrImageSize);
                    attributes.replaceValue("width", emojiOptions.attrImageSize);

                    l = getSizeInfo(attributes, "width", 100.0, 1.0); // page dimensions not used, these are not %

                    // now revert from twips to points
                    l /= 20;
                }

                if (!emojiOptions.attrAlign.isEmpty()) {
                    attributes.replaceValue("align", emojiOptions.attrAlign);
                }

                attributes = docx.extendRenderingNodeAttributes(AttributablePart.NODE, attributes);
                R r = renderImage(docx, resolvedLink, attributes, 1.0);

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

    private void render(ImageRef node, DocxRendererContext docx) {
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
            docx.addTextCreateR(node.getChars().unescape());

            if (options.logImageProcessing) {
                System.out.println("render image ref of " + referenceRepository.normalizeKey(node.getReference()) + " skipped because it was not defined");
            }
        } else {
            String altText = new TextCollectingVisitor().collectAndGetText(node);
            Attributes attributes = resolvedLink.getNonNullAttributes();

            if (!altText.isEmpty()) {
                attributes.replaceValue("alt", altText);
            }

            if (reference != null) {
                attributes = docx.extendRenderingNodeAttributes(reference, AttributablePart.NODE, attributes);
            }

            attributes = docx.extendRenderingNodeAttributes(AttributablePart.NODE, attributes);

            addRunAttributeFormatting(getAttributeFormat(attributes, docx), docx);

            renderImage(docx, resolvedLink, attributes, 1.0);
        }
    }

    private R renderImage(DocxRendererContext docx, ResolvedLink resolvedLink, Attributes attributes, double scale) {
        BufferedImage image = null;
        int id1 = imageId++;
        int id2 = imageId++;
        String filenameHint = String.format(Locale.US, "Image%d", id1);
        int cx;

        String url = resolvedLink.getUrl();

        if (url.startsWith(DocxRenderer.EMOJI_RESOURCE_PREFIX)) {
            url = this.getClass().getResource("/emoji/" + url.substring(DocxRenderer.EMOJI_RESOURCE_PREFIX.length())).toString();
            resolvedLink = resolvedLink.withUrl(url).withStatus(LinkStatus.VALID);
        }

        if (ImageUtils.isEncodedImage(url)) {
            image = ImageUtils.base64Decode(url);
        } else {
            ResolvedContent resolvedContent = docx.resolvedContent(resolvedLink);
            if (resolvedContent.getStatus() == LinkStatus.VALID) {
                image = ImageUtils.loadImageFromContent(resolvedContent.getContent(), resolvedLink.getUrl());
            } else if (url.startsWith("http:") || url.startsWith("https:")) {
                image = ImageUtils.loadImageFromURL(url, options.logImageProcessing);
            }

            if (image == null && options.logImageProcessing) {
                System.out.println("loadImageFromURL(" + url + ") returned null");
            }
        }

        if (image != null) {
            int width = image.getWidth();
            if (options.maxImageWidth > 0 && options.maxImageWidth < width) {
                cx = options.maxImageWidth;
                attributes.replaceValue("width", String.valueOf(cx));
            }

            return newImage(docx, image, filenameHint, attributes, id1, id2, scale);
        }
        return null;
    }

    private Tbl myTbl;
    private Tr myTr;

    private void render(TableBlock node, DocxRendererContext docx) {
        // if we have a caption and it goes before the table, we add it here
        Node caption = node.getFirstChildAny(TableCaption.class);
        if (caption != null && tableCaptionBeforeTable) {
            renderTableCaption((TableCaption) caption, docx);
        }

        Tbl savedTbl = myTbl;

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
        BigInteger tableInd = BigInteger.valueOf(tableLeftIndent).add(docx.getHelper().safeIndLeft(pPr.getInd()));
        tblInd.setW(tableInd);

        docx.setBlockFormatProvider(new IsolatingBlockFormatProvider<>(docx));
        addBlockAttributeFormatting(node, AttributablePart.NODE, docx, false);

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

    private void render(TableHead node, DocxRendererContext docx) {
        docx.renderChildren(node);
    }

    private void render(TableSeparator tableSeparator, DocxRendererContext docx) {

    }

    private void render(TableBody node, DocxRendererContext docx) {
        docx.renderChildren(node);
    }

    private void render(TableRow node, DocxRendererContext docx) {
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

    private void render(TableCaption node, DocxRendererContext docx) {
        // TODO: figure out how to set caption
        // table caption not yet supported by docx4j API
        //final TblPr tblPr = myTbl.getTblPr();
        //docx.getFactory().createCTCaption();
        // convert to Caption text
        if (!tableCaptionBeforeTable) {
            renderTableCaption(node, docx);
        }
    }

    private void renderTableCaption(TableCaption node, DocxRendererContext docx) {
        if (tableCaptionToParagraph) {
            docx.contextFramed(new TableCaptionRenderer(docx, node));
        }
    }

    private void render(TableCell node, DocxRendererContext docx) {
        String styleName = node.isHeader() ? docx.getDocxRendererOptions().TABLE_HEADING : docx.getDocxRendererOptions().TABLE_CONTENTS;

        // Create object for tc (wrapped in JAXBElement)
        Tc tc = docx.getFactory().createTc();
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

        boolean[] firstP = new boolean[] { true };
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

    private void render(FootnoteBlock node, DocxRendererContext docx) {

    }

    private void render(Footnote node, DocxRendererContext docx) {
        FootnoteBlock footnoteBlock = node.getFootnoteBlock();
        if (footnoteBlock == null) {
            //just text
            docx.addText("[^");
            docx.renderChildren(node);
            docx.addText("]");
        } else {
            try {
                BigInteger footnoteId = footnoteIDs.getOrDefault(footnoteBlock, BigInteger.ZERO);
                CTFtnEdn ftnEdn = docx.addFootnote(footnoteId);
                BigInteger ftnEdnId = ftnEdn.getId();
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

    private void render(TocBlock node, DocxRendererContext docx) {
        lastTocBlock = node;
    }

    private void render(SimTocBlock node, DocxRendererContext docx) {
        lastTocBlock = node;
    }

    private void render(EnumeratedReferenceText node, DocxRendererContext docx) {
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

    private void render(EnumeratedReferenceLink node, DocxRendererContext docx) {
        String text = node.getText().toString();

        if (text.isEmpty()) {
            // placeholder for ordinal
            if (ordinalRunnable != null) ordinalRunnable.run();
        } else {
            EnumeratedReferenceRendering[] renderings = enumeratedOrdinals.getEnumeratedReferenceOrdinals(text);

            String title = new EnumRefTextCollectingVisitor().collectAndGetText(node.getChars().getBaseSequence(), renderings, null);
            Attributes attributes = new Attributes();

            if (title != null) {
                attributes.replaceValue(Attribute.TITLE_ATTR, title);
            }

            attributes = docx.extendRenderingNodeAttributes(AttributablePart.NODE, attributes);
            renderURL(node.getText(), docx, "#" + text, attributes, () -> EnumeratedReferences.renderReferenceOrdinals(renderings, new OrdinalRenderer(CoreNodeDocxRenderer.this, docx)));
        }
    }

    private static class OrdinalRenderer implements EnumeratedOrdinalRenderer {
        final CoreNodeDocxRenderer renderer;
        final DocxRendererContext docx;

        public OrdinalRenderer(CoreNodeDocxRenderer renderer, DocxRendererContext docx) {
            this.renderer = renderer;
            this.docx = docx;
        }

        @Override
        public void startRendering(EnumeratedReferenceRendering[] renderings) {

        }

        @Override
        public void setEnumOrdinalRunnable(Runnable runnable) {
            renderer.ordinalRunnable = runnable;
        }

        @Override
        public Runnable getEnumOrdinalRunnable() {
            return renderer.ordinalRunnable;
        }

        @Override
        public void render(int referenceOrdinal, EnumeratedReferenceBlock referenceFormat, String defaultText, boolean needSeparator) {
            Runnable compoundRunnable = renderer.ordinalRunnable;
            String text = referenceOrdinal + (needSeparator ? "." : "");

            if (referenceFormat != null) {
                renderer.ordinalRunnable = () -> {
                    if (compoundRunnable != null) compoundRunnable.run();
                    docx.addTextCreateR(text);
                };

                docx.renderChildren(referenceFormat);
            } else {
                if (compoundRunnable != null) {
                    docx.addTextCreateR(defaultText + " ");
                    compoundRunnable.run();
                    docx.addTextCreateR(text);
                } else {
                    docx.addTextCreateR(defaultText + " " + text);
                }
            }
        }

        @Override
        public void endRendering() {

        }
    }

    private void render(EnumeratedReferenceBlock node, DocxRendererContext docx) {

    }

    private void render(AttributesNode node, DocxRendererContext docx) {
        docx.renderChildren(node);
    }

    private void render(AttributeNode node, DocxRendererContext docx) {
        if (node.isClass()) {
            switch (node.getValue().toString()) {
                case "pagebreak": {
                    R r = docx.getR();
                    Br br = docx.getFactory().createBr();
                    br.setType(STBrType.PAGE);
                    r.getContent().add(br);
                }
                break;

                case "tab": {
                    R r = docx.getR();
                    R.Tab tab = docx.getFactory().createRTab();
                    r.getContent().add(tab);
                }
                break;

                default:
                    break;
            }
        }
    }

    private void render(JekyllTag node, DocxRendererContext docx) {
        docx.renderChildren(node);
    }

    private void render(JekyllTagBlock node, DocxRendererContext docx) {
        docx.renderChildren(node);
//        Document includedDoc = includedDocumentsMap.get(node);
//
//        if (includedDoc != null) {
//            assert parser != null;
//            
//            if (parser.transferReferences(includedDoc, docx.getDocument(), null)) {
//                FootnoteRepository.resolveFootnotes(includedDoc);
//                recheckUndefinedReferences = true;
//            }
//
//            for (Node inclChild : includedDoc.getChildren()) {
//                docx.render(inclChild);
//            }
//        }
    }

    private static class UrlRenderer implements Runnable {
        final private DocxRendererContext myDocx;
        final private String myLinkText;
        final private String myLinkUrl;

        public UrlRenderer(DocxRendererContext docx, String linkText, String linkUrl) {
            myDocx = docx;
            myLinkText = linkText;
            myLinkUrl = linkUrl;
        }

        @Override
        public void run() {
            // Create object for r
            myDocx.addTextCreateR(myLinkText == null ? myLinkUrl : myLinkText);
        }
    }

    private static class UrlRunContainer implements RunContainer {
        final private P.Hyperlink myHyperlink;

        public UrlRunContainer(P.Hyperlink hyperlink) {myHyperlink = hyperlink;}

        @Override
        public void addR(R r) {
            myHyperlink.getContent().add(r);
        }

        @Override
        public R getLastR() {
            List<Object> content = myHyperlink.getContent();
            if (content == null || content.size() == 0) return null;
            Object o = content.get(content.size() - 1);
            return o instanceof R ? (R) o : null;
        }
    }

    private static class ChildRenderer implements Runnable {
        final private DocxRendererContext myDocx;
        final private Node myNode;

        public ChildRenderer(DocxRendererContext docx, Node node) {
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
        final private FootnoteBlock myFootnoteBlock;

        public FootnoteFrame(DocxRendererContext docx, CTFtnEdn ftnEdn, FootnoteBlock footnoteBlock) {
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
                public void addP(P p) {
                    myFtnEdn.getContent().add(p);
                }

                @Override
                public P getLastP() {
                    List<Object> content = myFtnEdn.getContent();
                    if (content == null || content.size() == 0) return null;
                    Object o = content.get(content.size() - 1);
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
                    List<Object> content = getContent();
                    return content != null && content.size() > 0 ? content.get(content.size() - 1) : null;
                }

                @Override
                public void addContentElement(Object element) {
                    getContent().add(element);
                }
            });
            myDocx.renderChildren(myFootnoteBlock);
        }
    }

    private static class TableCaptionRenderer implements Runnable {
        final private DocxRendererContext myDocx;
        final private TableCaption myNode;

        public TableCaptionRenderer(DocxRendererContext docx, TableCaption node) {
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
        final private Tc myTc;
        final private DocxRendererContext myDocx;
        final private Part myContainerPart;
        final private boolean[] myFirstP;

        public TableCellContentContainer(Tc tc, DocxRendererContext docx, boolean[] firstP) {
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
            List<Object> content = myTc.getContent();
            return content != null && content.size() > 0 ? content.get(content.size() - 1) : null;
        }

        @Override
        public void addContentElement(Object element) {
            myTc.getContent().add(element);
            myFirstP[0] = false;
        }
    }

    private static class TableCellParaContainer implements ParaContainer {
        final private TableCell myNode;
        final private Tc myTc;
        final private DocxRendererContext myDocx;
        final private boolean[] myFirstP;

        public TableCellParaContainer(TableCell node, Tc tc, DocxRendererContext docx, boolean[] firstP) {
            myNode = node;
            myTc = tc;
            myDocx = docx;
            myFirstP = firstP;
        }

        @Override
        public void addP(P p) {
            myFirstP[0] = false;
            myTc.getContent().add(p);
        }

        @Override
        public P getLastP() {
            List<Object> content = myTc.getContent();
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
            Object o = content.get(content.size() - 1);
            return o instanceof P ? (P) o : null;
        }
    }
}
