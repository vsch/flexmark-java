package com.vladsch.flexmark.docx.converter.util;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

/**
 * Pretty-prints xml, supplied as a string.
 * <p>
 * eg.
 * {@code
 * String formattedXml = XmlFormatter.format("<tag><nested>hello</nested></tag>");
 * }
 * <p>
 * StackOverflow: https://stackoverflow.com/questions/139076/how-to-pretty-print-xml-from-java
 */
public class XmlFormatter {
    public static String format(String xml) {
        try {
            InputSource src = new InputSource(new StringReader(xml));
            Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
            Boolean keepDeclaration = Boolean.valueOf(xml.startsWith("<?xml"));

            //May need this: System.setProperty(DOMImplementationRegistry.PROPERTY,"com.sun.org.apache.xerces.internal.dom.DOMImplementationSourceImpl");

            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            LSSerializer writer = impl.createLSSerializer();

            writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE); // Set this to true if the output needs to be beautified.
            writer.getDomConfig().setParameter("xml-declaration", keepDeclaration); // Set this to true if the declaration is needed to be outputted.

            return writer.writeToString(document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String formatDocumentBody(String xml) {
        try {
            InputSource src = new InputSource(new StringReader(xml));
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(false);

            Document document = builderFactory.newDocumentBuilder().parse(src);
            NodeList bodies = document.getElementsByTagName("w:body");
            NodeList sections = document.getElementsByTagName("w:sectPr");
            NodeList footnotes = document.getElementsByTagName("w:footnote");

            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            LSSerializer writer = impl.createLSSerializer();

            writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE); // Set this to true if the output needs to be beautified.
            writer.getDomConfig().setParameter("xml-declaration", false);

            int iMax = sections.getLength();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < iMax; i++) {
                Node item = sections.item(i);
                item.getParentNode().removeChild(item);
            }

            iMax = bodies.getLength();
            for (int i = 0; i < iMax; i++) {
                Node item = bodies.item(i);
                sb.append(writer.writeToString(item));
            }

            iMax = footnotes.getLength();
            for (int i = 0; i < iMax; i++) {
                Node item = footnotes.item(i);
                sb.append(writer.writeToString(item));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
