package com.vladsch.flexmark.docx.converter.util;

import com.vladsch.flexmark.util.sequence.SequenceUtils;
import org.docx4j.wml.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

public class AttributeFormat {
    final public @Nullable String fontFamily;
    final public @Nullable String fontSize;
    final public @Nullable Boolean fontBold;
    final public @Nullable Boolean fontItalic;
    final public @Nullable String textColor;
    final public @Nullable String fillColor;

    public AttributeFormat(@Nullable String fontFamily, @Nullable String fontSize, @Nullable String fontWeight, @Nullable String fontStyle, @Nullable String textColor, @Nullable String fillColor) {
        this.fontFamily = getValidFontFamily(trimEmptyToNull(fontFamily));
        this.fontSize = getValidFontSize(trimEmptyToNull(fontSize));
        this.fontBold = getValidFontBold(trimEmptyToNull(fontWeight));
        this.fontItalic = getValidFontItalic(trimEmptyToNull(fontStyle));
        this.textColor = getValidNamedOrHexColor(trimEmptyToNull(textColor));
        this.fillColor = getValidNamedOrHexColor(trimEmptyToNull(fillColor));
    }

    @Nullable
    private static String trimEmptyToNull(@Nullable String textColor) {
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

    @Nullable
    String getValidHexColor(@Nullable String s) {
        if (s == null) return null;

        return ColorNameMapper.getValidHexColor(s);
    }

    @Nullable
    String getValidNamedOrHexColor(@Nullable String s) {
        if (s == null) return null;

        return ColorNameMapper.getValidNamedOrHexColor(s);
    }

    @Nullable
    private String getValidFontFamily(@Nullable String fontFamily) {
        return fontFamily == null || fontFamily.isEmpty() ? null : fontFamily;
    }

    @Nullable
    private String getValidFontSize(@Nullable String fontSize) {
        return fontSize == null || fontSize.isEmpty() ? null : fontSize;
    }

    @Nullable
    private Boolean getValidFontBold(@Nullable String s) {
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
                int weight = SequenceUtils.parseIntOrDefault(s, -1);
                if (weight != -1) return weight >= 550;
        }

        return null;
    }

    @Nullable
    private Boolean getValidFontItalic(@Nullable String s) {
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
                    int angle = SequenceUtils.parseIntOrDefault(s.substring("oblique ".length()).trim(), -1);
                    if (angle != -1) return angle >= 14;
                }
        }

        return null;
    }

    @NotNull
    public <T> CTShd getShd(@NotNull DocxContext<T> docx) {
        CTShd shd = docx.getFactory().createCTShd();
        shd.setColor("auto");
        shd.setFill(fillColor);
        shd.setVal(STShd.CLEAR);
        return shd;
    }

    public <T> void setFormatRPr(@NotNull RPrAbstract rPr, @NotNull DocxContext<T> docx) {
        if (textColor != null) {
            Color color = docx.getFactory().createColor();
            rPr.setColor(color);
            color.setVal(ColorNameMapper.getValidHexColorOrDefault(textColor, "000000").toUpperCase());

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

        if (fontSize != null) {
            long sz = 0;

            if (fontSize.endsWith("pt")) {
                float ptSz = Float.parseFloat(fontSize.substring(0, fontSize.length() - 2).trim());
                sz = Math.round(ptSz * 2.0);
            } else {
                // treat as
                float ptSz = Float.parseFloat(fontSize);
                sz = Math.round(ptSz * 2.0);
            }

            if (sz != 0) {
                HpsMeasure hpsMeasure = docx.getFactory().createHpsMeasure();
                hpsMeasure.setVal(BigInteger.valueOf(sz));
                rPr.setSzCs(hpsMeasure);
                rPr.setSz(hpsMeasure);
            } else {
                rPr.setSzCs(null);
                rPr.setSz(null);
            }
        }
    }
}
