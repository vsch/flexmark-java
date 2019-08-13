package com.vladsch.flexmark.docx.converter.util;

import com.vladsch.flexmark.util.Utils;
import org.docx4j.wml.CTShd;
import org.docx4j.wml.Color;
import org.docx4j.wml.RPrAbstract;
import org.docx4j.wml.STShd;

public class AttributeFormat {
    final public String fontFamily;
    final public String fontSize;
    final public Boolean fontBold;
    final public Boolean fontItalic;
    final public String textColor;
    final public String fillColor;

    public AttributeFormat(String fontFamily, String fontSize, String fontWeight, String fontStyle, String textColor, String fillColor) {
        this.fontFamily = getValidFontFamily(trimEmptyToNull(fontFamily));
        this.fontSize = getValidFontSize(trimEmptyToNull(fontSize));
        this.fontBold = getValidFontBold(trimEmptyToNull(fontWeight));
        this.fontItalic = getValidFontItalic(trimEmptyToNull(fontStyle));
        this.textColor = getValidNamedOrHexColor(trimEmptyToNull(textColor));
        this.fillColor = getValidNamedOrHexColor(trimEmptyToNull(fillColor));
    }

    private static String trimEmptyToNull(String textColor) {
        if (textColor == null) return null;

        String trimmed = textColor.trim();
        if (!trimmed.isEmpty()) return trimmed;
        return null;
    }

    public boolean isEmpty() {
        return fontFamily == null &&
                fontSize == null &&
                fontBold == null &&
                fontItalic == null &&
                textColor == null &&
                fillColor == null;
    }

    String getValidHexColor(String s) {
        if (s == null) return null;

        return ColorNameMapper.getValidHexColor(s);
    }

    String getValidNamedOrHexColor(String s) {
        if (s == null) return null;

        return ColorNameMapper.getValidNamedOrHexColor(s);
    }

    private String getValidFontFamily(String fontFamily) {
        return fontFamily == null || fontFamily.isEmpty() ? null : fontFamily;
    }

    private String getValidFontSize(String fontSize) {
        return fontSize == null || fontSize.isEmpty() ? null : fontSize;
    }

    private Boolean getValidFontBold(String s) {
        if (s == null) return null;

        switch (s) {
            case "bolder":
            case "bold":
                return true;

            case "lighter":
            case "normal":
                return false;

            default:
                // convert to numeric then round up/down to 700/400
                int weight = Utils.parseIntOrDefault(s, -1);
                if (weight != -1) return weight >= 550;
        }

        return null;
    }

    private Boolean getValidFontItalic(String s) {
        if (s == null) return null;

        switch (s) {
            case "oblique":
            case "italic":
                return true;

            case "normal":
                return false;

            default:
                // convert to numeric then round up/down to 700/400
                if (s.startsWith("oblique ")) {
                    int angle = Utils.parseIntOrDefault(s.substring("oblique ".length()).trim(), -1);
                    if (angle != -1) return angle >= 14;
                }
        }

        return null;
    }

    public <T> CTShd getShd(DocxContext<T> docx) {
        CTShd shd = docx.getFactory().createCTShd();
        shd.setColor("auto");
        shd.setFill(fillColor);
        shd.setVal(STShd.CLEAR);
        return shd;
    }

    public <T> void setFormatRPr(RPrAbstract rPr, DocxContext<T> docx) {
        if (textColor != null) {
            Color color = docx.getFactory().createColor();
            rPr.setColor(color);
            color.setVal(ColorNameMapper.getValidHexColor(textColor).toUpperCase());

            rPr.setColor(color);
        }

        if (fillColor != null) {
            CTShd shd = rPr.getShd();

            if (shd == null) {
                shd = docx.getFactory().createCTShd();
                rPr.setShd(shd);
            }

            shd.setColor("auto");
            shd.setFill(fillColor);
            shd.setVal(STShd.CLEAR);
        }

        if (fontBold != null) {
            rPr.setBCs(fontBold ? docx.getFactory().createBooleanDefaultTrue() : null);
            rPr.setB(fontBold ? docx.getFactory().createBooleanDefaultTrue() : null);
        }

        if (fontItalic != null) {
            rPr.setICs(fontItalic ? docx.getFactory().createBooleanDefaultTrue() : null);
            rPr.setI(fontItalic ? docx.getFactory().createBooleanDefaultTrue() : null);
        }
    }
}
