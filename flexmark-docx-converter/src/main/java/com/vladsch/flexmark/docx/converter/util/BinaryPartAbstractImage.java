package com.vladsch.flexmark.docx.converter.util;

import org.apache.xmlgraphics.image.loader.ImageInfo;
import org.apache.xmlgraphics.image.loader.ImageSize;
import org.docx4j.UnitsOfMeasurement;
import org.docx4j.XmlUtils;
import org.docx4j.dml.Graphic;
import org.docx4j.dml.wordprocessingDrawing.Anchor;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.model.structure.PageDimensions;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.Base;
import org.docx4j.openpackaging.contenttype.ContentType;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.OpcPackage;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.ExternalTarget;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.PartName;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.CxCy;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.relationships.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.xml.bind.JAXBElement;
import java.awt.geom.Dimension2D;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;

public class BinaryPartAbstractImage {
    final org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage myAbstractImage;
    protected static Logger log = LoggerFactory.getLogger(org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.class);

    public BinaryPartAbstractImage(org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage abstractImage) {
        myAbstractImage = abstractImage;
    }

    /**
     * Create a &lt;wp:inline&gt; element suitable for this image,
     * which can be linked or embedded in w:p/w:r/w:drawing.
     * If the image is wider than the page, it will be scaled
     * automatically.
     *
     * @param filenameHint Any text, for example the original filename
     * @param altText      Like HTML's alt text
     * @param id1          An id unique in the document
     * @param id2          Another id unique in the document
     * @param link         true if this is to be linked not embedded
     *                     None of these things seem to be exposed in Word 2007's
     *                     user interface, but Word won't open the document if
     *                     any of the attributes these go in (except @ desc) aren't present!
     * @param posHOffset   offset from column, {@link Long#MAX_VALUE} for right aligned, {@link Long#MIN_VALUE} for left aligned, {@link Long#MAX_VALUE}/2 for centered
     * @param posVOffset   offset from paragraph
     * @return anchor element
     * @throws Exception throws exceptions on failure
     */
    public Anchor createImageAnchor(
            String filenameHint, String altText,
            int id1, int id2, boolean link, long posHOffset, long posVOffset
    ) throws Exception {

        WordprocessingMLPackage wmlPackage = ((WordprocessingMLPackage) myAbstractImage.getPackage());

        List<SectionWrapper> sections = wmlPackage.getDocumentModel().getSections();
        PageDimensions page = sections.get(sections.size() - 1).getPageDimensions();

        CxCy cxcy = CxCy.scale(myAbstractImage.getImageInfo(), page);

        return createImageAnchor(filenameHint, altText,
                id1, id2, cxcy.getCx(), cxcy.getCy(), link, posHOffset, posVOffset);
    }

    /**
     * @param filenameHint file name hint
     * @param altText      description
     * @param id1          &lt;wp:docPr id
     * @param id2          &lt;pic:cNvPr id
     * @param link         true if to link, else embed
     * @param maxWidth     max width of image
     * @param maxHeight    max height of image
     * @param posHOffset   offset from column, {@link Long#MAX_VALUE} for right aligned, {@link Long#MIN_VALUE} for left aligned, {@link Long#MAX_VALUE}/2 for centered
     * @param posVOffset   offset from paragraph
     * @return anchor element
     * @throws Exception throws exceptions on failure
     */
    public Anchor createImageAnchor(
            String filenameHint, String altText,
            int id1, int id2, boolean link, int maxWidth, int maxHeight, long posHOffset, long posVOffset
    ) throws Exception {

        // This signature can scale the image to specified maxWidth

        WordprocessingMLPackage wmlPackage = ((WordprocessingMLPackage) this.getPackage());

        List<SectionWrapper> sections = wmlPackage.getDocumentModel().getSections();
        PageDimensions page = sections.get(sections.size() - 1).getPageDimensions();

        CxCy cxcy = scale(myAbstractImage.getImageInfo(), page, maxWidth, maxHeight);

        return createImageAnchor(filenameHint, altText, id1, id2, cxcy.getCx(), cxcy.getCy(), link, posHOffset, posVOffset);
    }

    /**
     * Return scaling values constrained by specified maxWidth/maxHeight.
     * Useful if the image is to be inserted into a table cell of known width.
     *
     * @param imageInfo image info
     * @param page      page dimensions
     * @param maxWidth  max width
     * @param maxHeight max height
     * @return cx cy sizes
     * @since 3.3.0
     */
    public static CxCy scale(ImageInfo imageInfo, PageDimensions page, int maxWidth, int maxHeight) {

        double writableWidthTwips = page.getWritableWidthTwips();
        log.debug("writableWidthTwips: " + writableWidthTwips);

        if (maxWidth > 0 && maxWidth < writableWidthTwips) {
            writableWidthTwips = maxWidth;
            log.debug("reduced to: " + writableWidthTwips);
        }

        double writableHeightTwips = page.getWritableHeightTwips();
        log.debug("writableHeightTwips: " + writableHeightTwips);

        if (maxHeight > 0 && maxHeight < writableHeightTwips) {
            writableHeightTwips = maxHeight;
            log.debug("reduced to: " + writableHeightTwips);
        }

        ImageSize size = imageInfo.getSize();

        // get image size in pixels
        Dimension2D dpx = size.getDimensionPx();

        // convert pixels to twips (uses configured DPI setting - NOT the
        // image DPI)
        double imageWidthTwips = UnitsOfMeasurement.pxToTwipDouble(dpx.getWidth());
        double imageHeightTwips = UnitsOfMeasurement.pxToTwipDouble(dpx.getHeight());

        log.debug("imageWidthTwips: " + imageWidthTwips);
        log.debug("imageHeightTwips: " + imageHeightTwips);

        long cx;
        long cy;
        boolean scaled = false;
        if (imageWidthTwips > writableWidthTwips && imageHeightTwips > writableHeightTwips) {
            log.debug("Scaling image to fit page width/height");
            scaled = true;

            double scale = Math.min(writableWidthTwips / imageWidthTwips, writableHeightTwips / imageHeightTwips);

            cx = UnitsOfMeasurement.twipToEMU(imageWidthTwips * scale);
            cy = UnitsOfMeasurement.twipToEMU(imageHeightTwips * scale);
        } else if (imageWidthTwips > writableWidthTwips) {
            log.debug("Scaling image to fit page width");
            scaled = true;

            cx = UnitsOfMeasurement.twipToEMU(writableWidthTwips);
            cy = UnitsOfMeasurement.twipToEMU(imageHeightTwips * writableWidthTwips / imageWidthTwips);
        } else if (imageHeightTwips > writableHeightTwips) {
            log.debug("Scaling image to fit page height");
            scaled = true;

            cx = UnitsOfMeasurement.twipToEMU(imageWidthTwips * writableHeightTwips / imageHeightTwips);
            cy = UnitsOfMeasurement.twipToEMU(writableHeightTwips);
        } else {
            log.debug("Scaling image - not necessary");

            cx = UnitsOfMeasurement.twipToEMU(imageWidthTwips);
            cy = UnitsOfMeasurement.twipToEMU(imageHeightTwips);
        }

        log.debug("cx=" + cx + "; cy=" + cy);

        return CxCy.scaleToFit(UnitsOfMeasurement.twipToEMU(imageWidthTwips), UnitsOfMeasurement.twipToEMU(imageHeightTwips), cx, cy);
    }

    /**
     * Create a &lt;wp:inline&gt; element suitable for this image,
     * which can be _embedded_ in w:p/w:r/w:drawing.
     *
     * @param filenameHint Any text, for example the original filename
     * @param altText      Like HTML's alt text
     * @param id1          &lt;wp:docPr id
     * @param id2          &lt;pic:cNvPr id
     * @param cx           Image width in twip
     * @param link         true if this is to be linked not embedded
     *                     None of these things seem to be exposed in Word 2007's
     *                     user interface, but Word won't open the document if
     *                     any of the attributes these go in (except @ desc) aren't present!
     * @param posHOffset   offset from column, {@link Long#MAX_VALUE} for right aligned, {@link Long#MIN_VALUE} for left aligned, {@link Long#MAX_VALUE}/2 for centered
     * @param posVOffset   offset from paragraph
     * @return anchor element
     * @throws Exception throws exceptions on failure
     */
    public Anchor createImageAnchor(
            String filenameHint, String altText,
            int id1, int id2, long cx, boolean link, long posHOffset, long posVOffset
    ) throws Exception {

        ImageSize size = myAbstractImage.getImageInfo().getSize();

        Dimension2D dPt = size.getDimensionPt();
        double imageWidthTwips = dPt.getWidth() * 20;
        log.debug("imageWidthTwips: " + imageWidthTwips);

        long cy;

        log.debug("Scaling image height to retain aspect ratio");
        cy = UnitsOfMeasurement.twipToEMU(dPt.getHeight() * 20 * cx / imageWidthTwips);

        // Now convert cx to EMU
        cx = UnitsOfMeasurement.twipToEMU(cx);

        log.debug("cx=" + cx + "; cy=" + cy);

        return createImageAnchor(filenameHint, altText, id1, id2, cx, cy, link, posHOffset, posVOffset);
    }

    /**
     * Create a &lt;wp:anchor&gt; element suitable for this image, which can be
     * linked or embedded in w:p/w:r/w:drawing, specifying height and width.  Note
     * that you'd ordinarily use one of the methods which don't require
     * you to specify height (cy).
     *
     * @param filenameHint Any text, for example the original filename
     * @param altText      Like HTML's alt text
     * @param id1          &lt;wp:docPr id
     * @param id2          &lt;pic:cNvPr id
     * @param cx           Image width in EMU
     * @param cy           Image height in EMU
     * @param link         true if this is to be linked not embedded
     * @param posHOffset   offset from column, {@link Long#MAX_VALUE} for right aligned, {@link Long#MIN_VALUE} for left aligned, {@link Long#MAX_VALUE}/2 for centered
     * @param posVOffset   offset from paragraph
     * @return anchor element
     * @throws Exception throws exceptions on failure
     */
    public Anchor createImageAnchor(
            String filenameHint, String altText,
            int id1, int id2, long cx, long cy, boolean link,
            long posHOffset, long posVOffset
    ) throws Exception {

        // Floating - The drawing object is anchored within the text, but can be absolutely positioned in the
        // document relative to the page.

        if (filenameHint == null) {
            filenameHint = "";
        }
        if (altText == null) {
            altText = "";
        }

        String type;
        if (link) {
            type = "r:link";
        } else {
            type = "r:embed";
        }

        String alignmentNone = "" +
                "<wp:positionH relativeFrom=\"column\"><wp:posOffset>${posHOffset}</wp:posOffset></wp:positionH>" +
                "<wp:positionV relativeFrom=\"paragraph\"><wp:posOffset>${posVOffset}</wp:posOffset></wp:positionV>" +
                "<wp:wrapNone/>" +
                "";

        String alignmentLeft = "" +
                "<wp:positionH relativeFrom=\"column\">\n" +
                "  <wp:align>left</wp:align>\n" +
                "</wp:positionH>\n" +
                "<wp:positionV relativeFrom=\"paragraph\">\n" +
                "  <wp:posOffset>0</wp:posOffset>\n" +
                "</wp:positionV>" +
                "<wp:wrapSquare wrapText=\"right\"/>\n" +
                "";

        String alignmentCenter = "" +
                "<wp:positionH relativeFrom=\"column\">\n" +
                "  <wp:align>center</wp:align>\n" +
                "</wp:positionH>\n" +
                "<wp:positionV relativeFrom=\"paragraph\">\n" +
                "  <wp:posOffset>0</wp:posOffset>\n" +
                "</wp:positionV>" +
                "<wp:wrapSquare wrapText=\"bothSides\"/>\n" +
                "";

        String alignmentRight = "" +
                "<wp:positionH relativeFrom=\"column\">\n" +
                "  <wp:align>right</wp:align>\n" +
                "</wp:positionH>\n" +
                "<wp:positionV relativeFrom=\"paragraph\">\n" +
                "  <wp:posOffset>0</wp:posOffset>\n" +
                "</wp:positionV>" +
                "<wp:wrapSquare wrapText=\"left\"/>\n" +
                "";

        String mlTop =
                "<wp:anchor distT=\"0\" distB=\"0\" distL=\"114300\" distR=\"114300\" simplePos=\"0\" relativeHeight=\"251658240\" behindDoc=\"0\" locked=\"0\" layoutInCell=\"1\" allowOverlap=\"1\"" + namespaces + ">" //  wp14:anchorId=\"28C768E3\" wp14:editId=\"050FA02A\">"
                        + "<wp:simplePos x=\"0\" y=\"0\"/>"
                        + "<wp:extent cx=\"${cx}\" cy=\"${cy}\"/>"
                        + "<wp:effectExtent l=\"0\" t=\"0\" r=\"0\" b=\"0\"/>"  // b=4445
                        + "<wp:docPr id=\"${id1}\" name=\"${filenameHint}\" descr=\"${altText}\"/><wp:cNvGraphicFramePr><a:graphicFrameLocks xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" noChangeAspect=\"1\"/></wp:cNvGraphicFramePr>"
                        + "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
                        + " <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
                        + " <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\"><pic:nvPicPr><pic:cNvPr id=\"${id2}\" name=\"${filenameHint}\"/><pic:cNvPicPr/></pic:nvPicPr>"
                        + "<pic:blipFill><a:blip " + type + "=\"${rEmbedId}\"/>"
                        //		           + " <a:extLst><a:ext uri=\"{28A0092B-C50C-407E-A947-70E740481C1C}\"><a14:useLocalDpi xmlns:a14=\"http://schemas.microsoft.com/office/drawing/2010/main\" val=\"0\"/></a:ext></a:extLst>"
                        + "<a:stretch><a:fillRect/></a:stretch></pic:blipFill>"
                        + "<pic:spPr><a:xfrm><a:off x=\"0\" y=\"0\"/><a:ext cx=\"${cx}\" cy=\"${cy}\"/></a:xfrm><a:prstGeom prst=\"rect\"><a:avLst/></a:prstGeom></pic:spPr></pic:pic></a:graphicData>"
                        + "</a:graphic>"
                        //		      + "<wp14:sizeRelH relativeFrom=\"page\"><wp14:pctWidth>0</wp14:pctWidth></wp14:sizeRelH><wp14:sizeRelV relativeFrom=\"page\"><wp14:pctHeight>0</wp14:pctHeight></wp14:sizeRelV>"
                        + "";

        String mlBottom = ""
                + "</wp:anchor>";

        HashMap<String, String> mappings = new HashMap<>();

        String ml;

        if (posHOffset == Long.MAX_VALUE) {
            ml = mlTop + alignmentRight + mlBottom;
        } else if (posHOffset == Long.MAX_VALUE / 2) {
            ml = mlTop + alignmentCenter + mlBottom;
        } else if (posHOffset == Long.MIN_VALUE) {
            ml = mlTop + alignmentLeft + mlBottom;
        } else {
            ml = mlTop + alignmentNone + mlBottom;
        }

        mappings.put("cx", Long.toString(cx));
        mappings.put("cy", Long.toString(cy));
        mappings.put("posHOffset", Long.toString(posHOffset));
        mappings.put("posVOffset", Long.toString(posVOffset));
        mappings.put("filenameHint", filenameHint);
        mappings.put("altText", altText);
        mappings.put("rEmbedId", myAbstractImage.getRelLast().getId());
        mappings.put("id1", Integer.toString(id1));
        mappings.put("id2", Integer.toString(id2));

        Object o = XmlUtils.unmarshallFromTemplate(ml, mappings);
        Anchor anchor = (Anchor) ((JAXBElement) o).getValue();

        return anchor;
    }

    final static String namespaces = " xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" "
            + "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" "
            + "xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\"";

    /**
     * @param filenameHint Any text, for example the original filename
     * @param altText      Like HTML's alt text
     * @param id1          &lt;wp:docPr id
     * @param id2          &lt;pic:cNvPr id
     * @param link         true if this is to be linked not embedded
     * @param maxWidth     max width
     * @param maxHeight    max height
     * @return inline element
     * @throws Exception throws exceptions on failure
     * @since 3.3.0
     */
    public Inline createImageInline(
            String filenameHint, String altText,
            int id1, int id2, boolean link, int maxWidth, int maxHeight
    ) throws Exception {

        // This signature can scale the image to specified maxWidth and maxHeight

        WordprocessingMLPackage wmlPackage = ((WordprocessingMLPackage) this.getPackage());

        List<SectionWrapper> sections = wmlPackage.getDocumentModel().getSections();
        PageDimensions page = sections.get(sections.size() - 1).getPageDimensions();
        ImageSize size = myAbstractImage.getImageInfo().getSize();

        CxCy cxcy = scale(myAbstractImage.getImageInfo(), page, maxWidth, maxHeight);

        return createImageInline(filenameHint, altText,
                id1, id2, cxcy.getCx(), cxcy.getCy(), link);
    }

    /**
     * Create an image part from the provided byte array, attach it to the source part
     * (eg the main document part, a header part etc), and return it.
     * <p>
     * Works for both docx and pptx.
     * <p>
     * Note: this method creates a temp file (and attempts to delete it).
     * That's because it uses org.apache.xmlgraphics
     *
     * @param opcPackage package
     * @param sourcePart part
     * @param bytes      image bytes
     * @return binary image
     * @throws Exception throws exceptions on failure
     */
    public static BinaryPartAbstractImage createImagePart(
            OpcPackage opcPackage,
            Part sourcePart, byte[] bytes
    ) throws Exception {
        return new BinaryPartAbstractImage(org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.createImagePart(opcPackage, sourcePart, bytes));
    }

    @Deprecated
    public ImageInfo getImageInfo() {return myAbstractImage.getImageInfo();}

    @Deprecated
    public void setImageInfo(ImageInfo imageInfo) {myAbstractImage.setImageInfo(imageInfo);}

    public List<Relationship> getRels() {return myAbstractImage.getRels();}

    public Relationship getRelLast() {return myAbstractImage.getRelLast();}

    public static void setDensity(int density) {org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.setDensity(density);}

    public static org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage createImagePart(WordprocessingMLPackage wordMLPackage, byte[] bytes) throws Exception {return org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);}

    public static org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage createImagePart(WordprocessingMLPackage wordMLPackage, File imageFile) throws Exception {return org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.createImagePart(wordMLPackage, imageFile);}

    @Deprecated
    public static String createImageName(Base sourcePart, String proposedRelId, String ext) {return org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.createImageName(sourcePart, proposedRelId, ext);}

    public static String createImageName(OpcPackage opcPackage, Base sourcePart, String proposedRelId, String ext) {return org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.createImageName(opcPackage, sourcePart, proposedRelId, ext);}

    @Deprecated
    public static org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage createImagePart(OpcPackage opcPackage, Part sourcePart, byte[] bytes, String mime, String ext) throws Exception {return org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.createImagePart(opcPackage, sourcePart, bytes, mime, ext);}

    public static org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage createImagePart(OpcPackage opcPackage, Part sourcePart, byte[] bytes, String mime) throws Exception {return org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.createImagePart(opcPackage, sourcePart, bytes, mime);}

    public static org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage createImagePart(OpcPackage opcPackage, Part sourcePart, File imageFile) throws Exception {return org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.createImagePart(opcPackage, sourcePart, imageFile);}

    public static org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage createLinkedImagePart(WordprocessingMLPackage wordMLPackage, URL fileurl) throws Exception {return org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.createLinkedImagePart(wordMLPackage, fileurl);}

    public static org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage createLinkedImagePart(OpcPackage opcPackage, Part sourcePart, URL url) throws Exception {return org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.createLinkedImagePart(opcPackage, sourcePart, url);}

    @Deprecated
    public Inline createImageInline(String filenameHint, String altText, int id1, int id2) throws Exception {return myAbstractImage.createImageInline(filenameHint, altText, id1, id2);}

    public Inline createImageInline(String filenameHint, String altText, int id1, int id2, boolean link) throws Exception {return myAbstractImage.createImageInline(filenameHint, altText, id1, id2, link);}

    public Inline createImageInline(String filenameHint, String altText, int id1, int id2, boolean link, int maxWidth) throws Exception {return myAbstractImage.createImageInline(filenameHint, altText, id1, id2, link, maxWidth);}

    @Deprecated
    public Inline createImageInline(String filenameHint, String altText, int id1, int id2, long cx) throws Exception {return myAbstractImage.createImageInline(filenameHint, altText, id1, id2, cx);}

    public Inline createImageInline(String filenameHint, String altText, int id1, int id2, long cx, boolean link) throws Exception {return myAbstractImage.createImageInline(filenameHint, altText, id1, id2, cx, link);}

    public Inline createImageInline(String filenameHint, String altText, int id1, int id2, long cx, long cy, boolean link) throws Exception {return myAbstractImage.createImageInline(filenameHint, altText, id1, id2, cx, cy, link);}

    public static ImageInfo getImageInfo(URL url) throws Exception {return org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.getImageInfo(url);}

    public static void main(String[] args) throws Exception {org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.main(args);}

    public static void displayImageInfo(ImageInfo info) {org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.displayImageInfo(info);}

    public static byte[] getImage(WordprocessingMLPackage wmlPkg, Graphic graphic) {return org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.getImage(wmlPkg, graphic);}

    public static void convertToPNG(InputStream is, OutputStream os, int density) throws IOException, InterruptedException {org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.convertToPNG(is, os, density);}

    public static void copy2(InputStream is, OutputStream os) throws IOException {org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage.copy2(is, os);}

    public ExternalTarget getExternalTarget() {return myAbstractImage.getExternalTarget();}

    public void setBinaryData(InputStream binaryData) {myAbstractImage.setBinaryData(binaryData);}

    public void setBinaryData(byte[] bytes) {myAbstractImage.setBinaryData(bytes);}

    public void setBinaryData(ByteBuffer bb) {myAbstractImage.setBinaryData(bb);}

    public boolean isLoaded() {return myAbstractImage.isLoaded();}

    public ByteBuffer getBuffer() {return myAbstractImage.getBuffer();}

    public void writeDataToOutputStream(OutputStream out) throws IOException {myAbstractImage.writeDataToOutputStream(out);}

    public byte[] getBytes() {return myAbstractImage.getBytes();}

    public boolean isContentEqual(Part other) throws Docx4JException {return myAbstractImage.isContentEqual(other);}

    public List<Relationship> getSourceRelationships() {return myAbstractImage.getSourceRelationships();}

    @Deprecated
    public Relationship getSourceRelationship() {return myAbstractImage.getSourceRelationship();}

    @Deprecated
    public void setSourceRelationship(Relationship sourceRelationship) {myAbstractImage.setSourceRelationship(sourceRelationship);}

    public String getRelationshipType() {return myAbstractImage.getRelationshipType();}

    public void setRelationshipType(String relationshipType) {myAbstractImage.setRelationshipType(relationshipType);}

    @Deprecated
    public RelationshipsPart getOwningRelationshipPart() {return myAbstractImage.getOwningRelationshipPart();}

    @Deprecated
    public void setOwningRelationshipPart(RelationshipsPart owningRelationshipPart) {myAbstractImage.setOwningRelationshipPart(owningRelationshipPart);}

    public long getContentLengthAsLoaded() {return myAbstractImage.getContentLengthAsLoaded();}

    public OpcPackage getPackage() {return myAbstractImage.getPackage();}

    public void setPackage(OpcPackage pack) {myAbstractImage.setPackage(pack);}

    public boolean setPartShortcut(Part part, String relationshipType) {return myAbstractImage.setPartShortcut(part, relationshipType);}

    public void setVersion(long version) {myAbstractImage.setVersion(version);}

    public long getVersion() {return myAbstractImage.getVersion();}

    public void rename(PartName newName) {myAbstractImage.rename(newName);}

    public void remove() {myAbstractImage.remove();}

    public Object getUserData(String key) {return myAbstractImage.getUserData(key);}

    public void setUserData(String key, Object value) {myAbstractImage.setUserData(key, value);}

    public RelationshipsPart getRelationshipsPart() {return myAbstractImage.getRelationshipsPart();}

    public RelationshipsPart getRelationshipsPart(boolean createIfAbsent) {return myAbstractImage.getRelationshipsPart(createIfAbsent);}

    public void setRelationships(RelationshipsPart relationships) {myAbstractImage.setRelationships(relationships);}

    public String getContentType() {return myAbstractImage.getContentType();}

    public void setContentType(ContentType contentType) {myAbstractImage.setContentType(contentType);}

    public void setPartName(PartName partName) {myAbstractImage.setPartName(partName);}

    public PartName getPartName() {return myAbstractImage.getPartName();}

    public Relationship addTargetPart(Part targetpart, RelationshipsPart.AddPartBehaviour mode) throws InvalidFormatException {return myAbstractImage.addTargetPart(targetpart, mode);}

    public Relationship addTargetPart(Part targetpart) throws InvalidFormatException {return myAbstractImage.addTargetPart(targetpart);}

    public Relationship addTargetPart(Part targetpart, String proposedRelId) throws InvalidFormatException {return myAbstractImage.addTargetPart(targetpart, proposedRelId);}

    public Relationship addTargetPart(Part targetpart, RelationshipsPart.AddPartBehaviour mode, String proposedRelId) throws InvalidFormatException {return myAbstractImage.addTargetPart(targetpart, mode, proposedRelId);}

    public void reset() {myAbstractImage.reset();}
}
