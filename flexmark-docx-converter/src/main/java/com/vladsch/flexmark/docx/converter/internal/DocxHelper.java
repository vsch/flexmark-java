package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.docx.converter.DocxRendererContext;
import com.vladsch.flexmark.util.Function;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.docx4j.wml.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.ArrayList;

public class DocxHelper {
    protected final DocxRendererContext myDocx;
    protected final ObjectFactory myFactory;

    public DocxHelper(final DocxRendererContext docx, final ObjectFactory factory) {
        myDocx = docx;
        myFactory = factory;
    }

    public static BigInteger safeBigInt(BigInteger value) {
        return value == null ? BigInteger.ZERO : value;
    }

    public static BigInteger safeBigInt(BigInteger value, long nullValue) {
        return value == null ? BigInteger.valueOf(nullValue) : value;
    }

    public ObjectFactory getFactory() {
        return myFactory;
    }

    public enum CombineBigInt {
        NONE, // pass through
        ADD,  // add copy and orig
        MAX,  // max of copy and orig
        MIN,  // min of copy and orig
        ADD_OTHER,
        MAX_OTHER,
        MIN_OTHER,
        RANGE,;

        BigInteger combine(BigInteger orig, BigInteger copy, BigInteger other) {
            if (this == ADD) {
                return copy == null && orig == null ? null : safeBigInt(copy).add(safeBigInt(orig));
            } else if (this == MAX) {
                return copy == null && orig == null ? null : safeBigInt(copy).max(safeBigInt(orig));
            } else if (this == MIN) {
                return copy == null && orig == null ? null : safeBigInt(copy).max(safeBigInt(orig));
            } else if (this == ADD_OTHER) {
                return copy == null && orig == null && other == null ? null : safeBigInt(copy).add(safeBigInt(orig).add(safeBigInt(other)));
            } else if (this == MAX_OTHER) {
                return copy == null && orig == null && other == null ? null : safeBigInt(copy).max(safeBigInt(orig).max(safeBigInt(other)));
            } else if (this == MIN_OTHER) {
                return copy == null && orig == null && other == null ? null : safeBigInt(copy).min(safeBigInt(orig).min(safeBigInt(other)));
            } else if (this == RANGE) {
                return copy == null && orig == null && other == null ? null : safeBigInt(copy).max(safeBigInt(orig).min(safeBigInt(other)));
            } else {
                return copy;
            }
        }
    }

    public void combine(PPrBase.Ind child, PPrBase.Ind parent, CombineBigInt left, CombineBigInt right) {
        if (parent != null) {
            child.setLeft(left.combine(child.getLeft(), parent.getLeft(), parent.getHanging()));
            child.setRight(right.combine(child.getRight(), parent.getRight(), parent.getHanging()));
        }
    }

    public void inheritInd(PPrBase child, PPrBase parent, CombineBigInt left, CombineBigInt right) {
        combine(child.getInd(), parent.getInd(), left, right);
    }

    public boolean has(Object value) {
        return value != null;
    }

    public BigInteger safeIndLeft(PPrBase pPrBase) {
        return pPrBase == null ? BigInteger.ZERO : safeIndLeft(pPrBase.getInd());
    }

    public BigInteger safeIndLeft(PPrBase pPrBase, long nullValue) {
        return pPrBase == null ? BigInteger.valueOf(nullValue) : safeIndLeft(pPrBase.getInd());
    }

    public BigInteger safeIndLeft(PPrBase.Ind ind) {
        return ind == null || ind.getLeft() == null ? BigInteger.ZERO : ind.getLeft();
    }

    public BigInteger safeIndLeft(PPrBase.Ind ind, long nullValue) {
        return ind == null || ind.getLeft() == null ? BigInteger.valueOf(nullValue) : ind.getLeft();
    }

    public BigInteger safeIndRight(PPrBase pPrBase) {
        return pPrBase == null ? BigInteger.ZERO : safeIndRight(pPrBase.getInd());
    }

    public BigInteger safeIndRight(PPrBase pPrBase, long nullValue) {
        return pPrBase == null ? BigInteger.valueOf(nullValue) : safeIndRight(pPrBase.getInd());
    }

    public BigInteger safeIndRight(PPrBase.Ind ind) {
        return ind == null || ind.getRight() == null ? BigInteger.ZERO : ind.getRight();
    }

    public BigInteger safeIndRight(PPrBase.Ind ind, long nullValue) {
        return ind == null || ind.getRight() == null ? BigInteger.valueOf(nullValue) : ind.getRight();
    }

    public BigInteger safeIndHanging(PPrBase pPrBase) {
        return pPrBase == null ? BigInteger.ZERO : safeIndHanging(pPrBase.getInd());
    }

    public BigInteger safeIndHanging(PPrBase pPrBase, long nullValue) {
        return pPrBase == null ? BigInteger.valueOf(nullValue) : safeIndHanging(pPrBase.getInd());
    }

    public BigInteger safeIndHanging(PPrBase.Ind ind) {
        return ind == null || ind.getHanging() == null ? BigInteger.ZERO : ind.getHanging();
    }

    public BigInteger safeIndHanging(PPrBase.Ind ind, long nullValue) {
        return ind == null || ind.getHanging() == null ? BigInteger.valueOf(nullValue) : ind.getHanging();
    }

    /*
    Spacing access
     */
    public BigInteger safeSpacingBefore(PPr pPr) {
        return pPr == null ? BigInteger.ZERO : safeSpacingBefore(pPr.getSpacing());
    }

    public BigInteger safeSpacingBefore(PPr pPr, long nullValue) {
        return pPr == null ? BigInteger.valueOf(nullValue) : safeSpacingBefore(pPr.getSpacing());
    }

    public BigInteger safeSpacingBefore(PPrBase.Spacing spacing) {
        return spacing == null || spacing.getBefore() == null ? BigInteger.ZERO : spacing.getBefore();
    }

    public BigInteger safeSpacingBefore(PPrBase.Spacing spacing, long nullValue) {
        return spacing == null || spacing.getBefore() == null ? BigInteger.valueOf(nullValue) : spacing.getBefore();
    }

    public BigInteger safeSpacingAfter(PPr pPr) {
        return pPr == null ? BigInteger.ZERO : safeSpacingAfter(pPr.getSpacing());
    }

    public BigInteger safeSpacingAfter(PPr pPr, long nullValue) {
        return pPr == null ? BigInteger.valueOf(nullValue) : safeSpacingAfter(pPr.getSpacing());
    }

    public BigInteger safeSpacingAfter(PPrBase.Spacing spacing) {
        return spacing == null || spacing.getAfter() == null ? BigInteger.ZERO : spacing.getAfter();
    }

    public BigInteger safeSpacingAfter(PPrBase.Spacing spacing, long nullValue) {
        return spacing == null || spacing.getAfter() == null ? BigInteger.valueOf(nullValue) : spacing.getAfter();
    }

    public BigInteger safeSpacingLine(PPr pPr) {
        return pPr == null ? BigInteger.ZERO : safeSpacingLine(pPr.getSpacing());
    }

    public BigInteger safeSpacingLine(PPr pPr, long nullValue) {
        return pPr == null ? BigInteger.valueOf(nullValue) : safeSpacingLine(pPr.getSpacing());
    }

    public BigInteger safeSpacingLine(PPrBase.Spacing spacing) {
        return spacing == null || spacing.getLine() == null ? BigInteger.ZERO : spacing.getLine();
    }

    public BigInteger safeSpacingLine(PPrBase.Spacing spacing, long nullValue) {
        return spacing == null || spacing.getLine() == null ? BigInteger.valueOf(nullValue) : spacing.getLine();
    }

    public PPrBase.PBdr ensurePBdr(PPrBase pPrBase) {
        if (pPrBase.getPBdr() == null) {
            PPrBase.PBdr pBdr = myFactory.createPPrBasePBdr();
            pPrBase.setPBdr(pBdr);
        }
        return pPrBase.getPBdr();
    }

    public PPrBase.Ind ensureInd(PPrBase pPrBase) {
        if (pPrBase.getInd() == null) {
            PPrBase.Ind ind = myFactory.createPPrBaseInd();
            pPrBase.setInd(ind);
        }
        return pPrBase.getInd();
    }

    public PPrBase.Spacing ensureSpacing(PPrBase pPrBase) {
        if (pPrBase.getSpacing() == null) {
            PPrBase.Spacing spacing = myFactory.createPPrBaseSpacing();
            pPrBase.setSpacing(spacing);
        }
        return pPrBase.getSpacing();
    }

    /**
     * Try to keep the border of the parent and offset the left border by the difference in indentation between the two so the border stays
     * aligned with the parent. Max offset for border is 31pt or 620tw
     * <p>
     * if the child has its own left border then nothing is done
     * <p>
     * other borders are not affected
     * <p>
     * Must be called after the child indent is set
     */
    public void inheritPBdr(PPrBase child, PPrBase parent) {
        if (has(parent.getPBdr()) && (!has(child.getPBdr()) || !has(child.getPBdr().getLeft()) && has(parent.getPBdr().getLeft()))) {
            PPrBase.Ind cInd = getCopy(child.getInd(), false);
            PPrBase.Ind pInd = getCopy(parent.getInd(), false);
            CTBorder leftBorder = getCopy(parent.getPBdr().getLeft(), true);

            final PPrBase.NumPr numPr = child.getNumPr();
            if (numPr != null) {
                // need to check that too, it may have settings we don't have
                NumberingDefinitionsPart ndp = myDocx.getDocxDocument().getNumberingDefinitionsPart();
                if (ndp != null) {
                    final PPrBase.Ind ndpInd = ndp.getInd(numPr);
                    if (cInd.getLeft() == null) {
                        cInd.setLeft(ndpInd.getLeft());
                    }
                    if (cInd.getRight() == null) {
                        cInd.setRight(ndpInd.getRight());
                    }
                    if (cInd.getHanging() == null) {
                        cInd.setHanging(ndpInd.getHanging());
                    }
                }
            }

            // now add difference between the left indents
            BigInteger space = safeBigInt(leftBorder.getSpace()).add(BigInteger.ZERO.min(safeIndLeft(child.getInd()).subtract(safeIndLeft(pInd))));
            leftBorder.setSpace(space);

            ensurePBdr(child).setLeft(leftBorder);
        }
    }

    /**
     */
    public void inheritInd(PPrBase child, PPrBase parent) {
        if (has(parent.getInd())) {
            ensureInd(child);

            PPrBase.Ind cInd = child.getInd();
            PPrBase.Ind pInd = parent.getInd();

            final PPrBase.NumPr numPr = child.getNumPr();
            if (numPr != null) {
                // need to check that too, it may have settings we don't have
                NumberingDefinitionsPart ndp = myDocx.getDocxDocument().getNumberingDefinitionsPart();
                if (ndp != null) {
                    final PPrBase.Ind ndpInd = ndp.getInd(numPr);
                    if (cInd.getLeft() == null) {
                        cInd.setLeft(ndpInd.getLeft());
                    }
                    if (cInd.getRight() == null) {
                        cInd.setRight(ndpInd.getRight());
                    }
                    if (cInd.getHanging() == null) {
                        cInd.setHanging(ndpInd.getHanging());
                    }
                }
            }

            combine(cInd, pInd, CombineBigInt.ADD_OTHER, CombineBigInt.NONE);
        }
    }

    public PPrBase.Ind getCopy(PPrBase.Ind copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.Ind result = myFactory.createPPrBaseInd();

            result.setLeft(copy.getLeft());
            result.setLeftChars(copy.getLeftChars());
            result.setRight(copy.getRight());
            result.setRightChars(copy.getRightChars());
            result.setHanging(copy.getHanging());
            result.setHangingChars(copy.getHangingChars());
            result.setFirstLine(copy.getFirstLine());
            result.setFirstLineChars(copy.getFirstLineChars());
            return result;
        }
        if (whenNull) {
            return myFactory.createPPrBaseInd();
        }
        return null;
    }

    public PPrBase.Spacing getCopy(PPrBase.Spacing copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.Spacing result = myFactory.createPPrBaseSpacing();
            result.setAfter(copy.getAfter());
            result.setAfterLines(copy.getAfterLines());
            result.setBefore(copy.getBefore());
            result.setBeforeLines(copy.getBeforeLines());
            result.setLine(copy.getLine());
            result.setAfterAutospacing(copy.isAfterAutospacing());
            result.setBeforeAutospacing(copy.isBeforeAutospacing());
            result.setLineRule(copy.getLineRule());
            return result;
        }
        if (whenNull) {
            return myFactory.createPPrBaseSpacing();
        }
        return null;
    }

    public PPrBase.NumPr.Ilvl getCopy(PPrBase.NumPr.Ilvl copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.NumPr.Ilvl result = myFactory.createPPrBaseNumPrIlvl();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createPPrBaseNumPrIlvl();
        }
        return null;
    }

    public PPrBase.NumPr.NumId getCopy(PPrBase.NumPr.NumId copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.NumPr.NumId result = myFactory.createPPrBaseNumPrNumId();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createPPrBaseNumPrNumId();
        }
        return null;
    }

    public CTTrackChangeNumbering getCopy(CTTrackChangeNumbering copy, boolean whenNull) {
        if (copy != null) {
            CTTrackChangeNumbering result = myFactory.createCTTrackChangeNumbering();
            result.setOriginal(copy.getOriginal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTTrackChangeNumbering();
        }
        return null;
    }

    public CTTrackChange getCopy(CTTrackChange copy, boolean whenNull) {
        if (copy != null) {
            CTTrackChange result = myFactory.createCTTrackChange();
            result.setDate((XMLGregorianCalendar) copy.getDate().clone());
            result.setAuthor(copy.getAuthor());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTTrackChange();
        }
        return null;
    }

    public PPrBase.NumPr getCopy(PPrBase.NumPr copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.NumPr result = myFactory.createPPrBaseNumPr();
            result.setIlvl(getCopy(copy.getIlvl(), whenNull));
            result.setNumId(getCopy(copy.getNumId(), whenNull));
            result.setNumberingChange(getCopy(copy.getNumberingChange(), whenNull));
            result.setIns(getCopy(copy.getIns(), whenNull));
            return result;
        }
        if (whenNull) {
            PPrBase.NumPr result = myFactory.createPPrBaseNumPr();
            result.setIlvl(getCopy((PPrBase.NumPr.Ilvl) null, whenNull));
            result.setNumId(getCopy((PPrBase.NumPr.NumId) null, whenNull));
            result.setNumberingChange(getCopy((CTTrackChangeNumbering) null, whenNull));
            result.setIns(getCopy((CTTrackChange) null, whenNull));
            return result;
        }
        return null;
    }

    public CTBorder getCopy(CTBorder copy, boolean whenNull) {
        if (copy != null) {
            CTBorder result = myFactory.createCTBorder();
            result.setVal(copy.getVal());
            result.setThemeColor(copy.getThemeColor());
            result.setSpace(copy.getSpace());
            result.setSz(copy.getSz());
            result.setFrame(copy.isFrame());
            result.setShadow(copy.isShadow());
            result.setColor(copy.getColor());
            result.setThemeShade(copy.getThemeShade());
            result.setThemeTint(copy.getThemeTint());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTBorder();
        }
        return null;
    }

    public PPrBase.PBdr getCopy(PPrBase.PBdr copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.PBdr result = myFactory.createPPrBasePBdr();
            result.setTop(getCopy(copy.getTop(), whenNull));
            result.setLeft(getCopy(copy.getLeft(), whenNull));
            result.setBottom(getCopy(copy.getBottom(), whenNull));
            result.setRight(getCopy(copy.getRight(), whenNull));
            result.setBetween(getCopy(copy.getBetween(), whenNull));
            result.setBar(getCopy(copy.getBar(), whenNull));
            return result;
        }
        if (whenNull) {
            PPrBase.PBdr result = myFactory.createPPrBasePBdr();
            result.setTop(getCopy((CTBorder) null, whenNull));
            result.setLeft(getCopy((CTBorder) null, whenNull));
            result.setBottom(getCopy((CTBorder) null, whenNull));
            result.setRight(getCopy((CTBorder) null, whenNull));
            result.setBetween(getCopy((CTBorder) null, whenNull));
            result.setBar(getCopy((CTBorder) null, whenNull));
            return result;
        }
        return null;
    }

    public PPrBase.PStyle getCopy(PPrBase.PStyle copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.PStyle result = myFactory.createPPrBasePStyle();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createPPrBasePStyle();
        }
        return null;
    }

    public CTFramePr getCopy(CTFramePr copy, boolean whenNull) {
        if (copy != null) {
            CTFramePr result = myFactory.createCTFramePr();
            result.setDropCap(copy.getDropCap());
            result.setLines(copy.getLines());
            result.setW(copy.getW());
            result.setH(copy.getH());
            result.setVSpace(copy.getVSpace());
            result.setHSpace(copy.getHSpace());
            result.setWrap(copy.getWrap());
            result.setHAnchor(copy.getHAnchor());
            result.setVAnchor(copy.getVAnchor());
            result.setX(copy.getX());
            result.setXAlign(copy.getXAlign());
            result.setY(copy.getY());
            result.setYAlign(copy.getYAlign());
            result.setHRule(copy.getHRule());
            result.setAnchorLock(copy.isAnchorLock());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTFramePr();
        }
        return null;
    }

    public CTShd getCopy(CTShd copy, boolean whenNull) {
        if (copy != null) {
            CTShd result = myFactory.createCTShd();
            result.setVal(copy.getVal());
            result.setColor(copy.getColor());
            result.setThemeColor(copy.getThemeColor());
            result.setThemeTint(copy.getThemeTint());
            result.setThemeShade(copy.getThemeShade());
            result.setFill(copy.getFill());
            result.setThemeFill(copy.getThemeFill());
            result.setThemeFillTint(copy.getThemeFillTint());
            result.setThemeFillShade(copy.getThemeFillShade());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTShd();
        }
        return null;
    }

    public Tabs getCopy(Tabs copy, boolean whenNull) {
        if (copy != null) {
            Tabs result = myFactory.createTabs();
            result.getTab().addAll(copy.getTab());
            return result;
        }
        if (whenNull) {
            return myFactory.createTabs();
        }
        return null;
    }

    public Jc getCopy(Jc copy, boolean whenNull) {
        if (copy != null) {
            Jc result = myFactory.createJc();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createJc();
        }
        return null;
    }

    public TextDirection getCopy(TextDirection copy, boolean whenNull) {
        if (copy != null) {
            TextDirection result = myFactory.createTextDirection();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createTextDirection();
        }
        return null;
    }

    public PPrBase.TextAlignment getCopy(PPrBase.TextAlignment copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.TextAlignment result = myFactory.createPPrBaseTextAlignment();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createPPrBaseTextAlignment();
        }
        return null;
    }

    public CTTextboxTightWrap getCopy(CTTextboxTightWrap copy, boolean whenNull) {
        if (copy != null) {
            CTTextboxTightWrap result = myFactory.createCTTextboxTightWrap();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTTextboxTightWrap();
        }
        return null;
    }

    public PPrBase.OutlineLvl getCopy(PPrBase.OutlineLvl copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.OutlineLvl result = myFactory.createPPrBaseOutlineLvl();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createPPrBaseOutlineLvl();
        }
        return null;
    }

    public PPrBase.DivId getCopy(PPrBase.DivId copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.DivId result = myFactory.createPPrBaseDivId();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createPPrBaseDivId();
        }
        return null;
    }

    public CTCnf getCopy(CTCnf copy, boolean whenNull) {
        if (copy != null) {
            CTCnf result = myFactory.createCTCnf();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTCnf();
        }
        return null;
    }

    public PPrBase getCopy(PPrBase copy, boolean whenNull) {
        if (copy != null) {
            PPrBase result = myFactory.createPPrBase();
            setPPrBase(result, copy, whenNull);
            return result;
        }
        if (whenNull) {
            PPrBase result = myFactory.createPPrBase();
            result.setPStyle(getCopy((PPrBase.PStyle) null, whenNull));
            result.setFramePr(getCopy((CTFramePr) null, whenNull));
            result.setNumPr(getCopy((PPrBase.NumPr) null, whenNull));
            result.setPBdr(getCopy((PPrBase.PBdr) null, whenNull));
            result.setShd(getCopy((CTShd) null, whenNull));
            result.setTabs(getCopy((Tabs) null, whenNull));
            result.setSpacing(getCopy((PPrBase.Spacing) null, whenNull));
            result.setInd(getCopy((PPrBase.Ind) null, whenNull));
            result.setJc(getCopy((Jc) null, whenNull));
            result.setTextDirection(getCopy((TextDirection) null, whenNull));
            result.setTextAlignment(getCopy((PPrBase.TextAlignment) null, whenNull));
            result.setTextboxTightWrap(getCopy((CTTextboxTightWrap) null, whenNull));
            result.setOutlineLvl(getCopy((PPrBase.OutlineLvl) null, whenNull));
            result.setDivId(getCopy((PPrBase.DivId) null, whenNull));
            result.setCnfStyle(getCopy((CTCnf) null, whenNull));
            return result;
        }
        return null;
    }

    public ParaRPr getCopy(ParaRPr copy, boolean whenNull) {
        if (copy != null) {
            ParaRPr result = myFactory.createParaRPr();
            result.setIns(getCopy(copy.getIns(), whenNull));
            result.setDel(getCopy(copy.getDel(), whenNull));
            result.setMoveFrom(getCopy(copy.getMoveFrom(), whenNull));
            result.setMoveTo(getCopy(copy.getMoveTo(), whenNull));
            setRPr(result, copy, whenNull);
            return result;
        }
        if (whenNull) {
            ParaRPr result = myFactory.createParaRPr();
            result.setIns(getCopy((CTTrackChange) null, whenNull));
            result.setDel(getCopy((CTTrackChange) null, whenNull));
            result.setMoveFrom(getCopy((CTTrackChange) null, whenNull));
            result.setMoveTo(getCopy((CTTrackChange) null, whenNull));
            setRPr(result, copy, whenNull);
            return result;
        }
        return null;
    }

    public RPr getCopy(RPr copy, boolean whenNull) {
        if (copy != null) {
            RPr result = myFactory.createRPr();
            setRPr(result, copy, whenNull);
            return result;
        }
        if (whenNull) {
            RPr result = myFactory.createRPr();
            setRPr(result, copy, whenNull);
            return result;
        }
        return null;
    }

    public RStyle getCopy(RStyle copy, boolean whenNull) {
        if (copy != null) {
            RStyle result = myFactory.createRStyle();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createRStyle();
        }
        return null;
    }

    public RFonts getCopy(RFonts copy, boolean whenNull) {
        if (copy != null) {
            RFonts result = myFactory.createRFonts();
            result.setHint(copy.getHint());
            result.setAscii(copy.getAscii());
            result.setHAnsi(copy.getHAnsi());
            result.setEastAsia(copy.getEastAsia());
            result.setCs(copy.getCs());
            result.setAsciiTheme(copy.getAsciiTheme());
            result.setHAnsiTheme(copy.getHAnsiTheme());
            result.setEastAsiaTheme(copy.getEastAsiaTheme());
            result.setCstheme(copy.getCstheme());
            return result;
        }
        if (whenNull) {
            return myFactory.createRFonts();
        }
        return null;
    }

    public Color getCopy(Color copy, boolean whenNull) {
        if (copy != null) {
            Color result = myFactory.createColor();
            result.setVal(copy.getVal());
            result.setThemeColor(copy.getThemeColor());
            result.setThemeTint(copy.getThemeTint());
            result.setThemeShade(copy.getThemeShade());
            return result;
        }
        if (whenNull) {
            return myFactory.createColor();
        }
        return null;
    }

    public CTSignedTwipsMeasure getCopy(CTSignedTwipsMeasure copy, boolean whenNull) {
        if (copy != null) {
            CTSignedTwipsMeasure result = myFactory.createCTSignedTwipsMeasure();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTSignedTwipsMeasure();
        }
        return null;
    }

    public CTTextScale getCopy(CTTextScale copy, boolean whenNull) {
        if (copy != null) {
            CTTextScale result = myFactory.createCTTextScale();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTTextScale();
        }
        return null;
    }

    public HpsMeasure getCopy(HpsMeasure copy, boolean whenNull) {
        if (copy != null) {
            HpsMeasure result = myFactory.createHpsMeasure();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createHpsMeasure();
        }
        return null;
    }

    public CTSignedHpsMeasure getCopy(CTSignedHpsMeasure copy, boolean whenNull) {
        if (copy != null) {
            CTSignedHpsMeasure result = myFactory.createCTSignedHpsMeasure();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTSignedHpsMeasure();
        }
        return null;
    }

    public Highlight getCopy(Highlight copy, boolean whenNull) {
        if (copy != null) {
            Highlight result = myFactory.createHighlight();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createHighlight();
        }
        return null;
    }

    public U getCopy(U copy, boolean whenNull) {
        if (copy != null) {
            U result = myFactory.createU();
            result.setVal(copy.getVal());
            result.setColor(copy.getColor());
            result.setThemeColor(copy.getThemeColor());
            result.setThemeTint(copy.getThemeTint());
            result.setThemeShade(copy.getThemeShade());
            return result;
        }
        if (whenNull) {
            return myFactory.createU();
        }
        return null;
    }

    public CTTextEffect getCopy(CTTextEffect copy, boolean whenNull) {
        if (copy != null) {
            CTTextEffect result = myFactory.createCTTextEffect();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTTextEffect();
        }
        return null;
    }

    public CTFitText getCopy(CTFitText copy, boolean whenNull) {
        if (copy != null) {
            CTFitText result = myFactory.createCTFitText();
            result.setVal(copy.getVal());
            result.setId(copy.getId());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTFitText();
        }
        return null;
    }

    public CTVerticalAlignRun getCopy(CTVerticalAlignRun copy, boolean whenNull) {
        if (copy != null) {
            CTVerticalAlignRun result = myFactory.createCTVerticalAlignRun();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTVerticalAlignRun();
        }
        return null;
    }

    public CTEm getCopy(CTEm copy, boolean whenNull) {
        if (copy != null) {
            CTEm result = myFactory.createCTEm();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTEm();
        }
        return null;
    }

    public CTLanguage getCopy(CTLanguage copy, boolean whenNull) {
        if (copy != null) {
            CTLanguage result = myFactory.createCTLanguage();
            result.setVal(copy.getVal());
            result.setEastAsia(copy.getEastAsia());
            result.setBidi(copy.getBidi());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTLanguage();
        }
        return null;
    }

    public CTEastAsianLayout getCopy(CTEastAsianLayout copy, boolean whenNull) {
        if (copy != null) {
            CTEastAsianLayout result = myFactory.createCTEastAsianLayout();
            result.setId(copy.getId());
            result.setCombine(copy.isCombine());
            result.setCombineBrackets(copy.getCombineBrackets());
            result.setVert(copy.isVert());
            result.setVertCompress(copy.isVertCompress());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTEastAsianLayout();
        }
        return null;
    }

    //public CTRPrChange getCopy(CTRPrChange copy, boolean whenNull) {
    //    if (copy != null) {
    //        CTRPrChange result = myFactory.createCTRPrChange();
    //        result.setRPr(getCopy(copy.getRPr(), whenNull);
    //        return result;
    //    }
    //    if (whenNull) {
    //        return myFactory.createCTRPrChange();
    //    }
    //    return null;
    //}

    public void setRPr(final RPrAbstract result, final RPrAbstract from, final boolean whenNull) {
        result.setRStyle(getCopy(from == null ? null : from.getRStyle(), whenNull));
        result.setRFonts(getCopy(from == null ? null : from.getRFonts(), whenNull));
        result.setColor(getCopy(from == null ? null : from.getColor(), whenNull));
        result.setSpacing(getCopy(from == null ? null : from.getSpacing(), whenNull));
        result.setW(getCopy(from == null ? null : from.getW(), whenNull));
        result.setKern(getCopy(from == null ? null : from.getKern(), whenNull));
        result.setPosition(getCopy(from == null ? null : from.getPosition(), whenNull));
        result.setSz(getCopy(from == null ? null : from.getSz(), whenNull));
        result.setSzCs(getCopy(from == null ? null : from.getSzCs(), whenNull));
        result.setHighlight(getCopy(from == null ? null : from.getHighlight(), whenNull));
        result.setU(getCopy(from == null ? null : from.getU(), whenNull));
        result.setEffect(getCopy(from == null ? null : from.getEffect(), whenNull));
        result.setBdr(getCopy(from == null ? null : from.getBdr(), whenNull));
        result.setShd(getCopy(from == null ? null : from.getShd(), whenNull));
        result.setFitText(getCopy(from == null ? null : from.getFitText(), whenNull));
        result.setVertAlign(getCopy(from == null ? null : from.getVertAlign(), whenNull));
        result.setEm(getCopy(from == null ? null : from.getEm(), whenNull));
        result.setLang(getCopy(from == null ? null : from.getLang(), whenNull));
        result.setEastAsianLayout(getCopy(from == null ? null : from.getEastAsianLayout(), whenNull));
        //result.setRPrChange(getCopy(from == null ? null : from.getRPrChange(), whenNull));

        if (from != null) {
            result.setB(from.getB());
            result.setBCs(from.getBCs());
            result.setI(from.getI());
            result.setICs(from.getICs());
            result.setCaps(from.getCaps());
            result.setSmallCaps(from.getSmallCaps());
            result.setStrike(from.getStrike());
            result.setDstrike(from.getDstrike());
            result.setOutline(from.getOutline());
            result.setShadow(from.getShadow());
            result.setEmboss(from.getEmboss());
            result.setImprint(from.getImprint());
            result.setNoProof(from.getNoProof());
            result.setSnapToGrid(from.getSnapToGrid());
            result.setVanish(from.getVanish());
            result.setWebHidden(from.getWebHidden());
            result.setRtl(from.getRtl());
            result.setCs(from.getCs());
            result.setSpecVanish(from.getSpecVanish());
            result.setOMath(from.getOMath());
        }
    }

    public void setPPrBase(final PPrBase result, final PPrBase from, final boolean whenNull) {
        result.setPStyle(from == null ? null : getCopy(from.getPStyle(), whenNull));
        result.setFramePr(from == null ? null : getCopy(from.getFramePr(), whenNull));
        result.setNumPr(from == null ? null : getCopy(from.getNumPr(), whenNull));
        result.setPBdr(from == null ? null : getCopy(from.getPBdr(), whenNull));
        result.setShd(from == null ? null : getCopy(from.getShd(), whenNull));
        result.setTabs(from == null ? null : getCopy(from.getTabs(), whenNull));
        result.setSpacing(from == null ? null : getCopy(from.getSpacing(), whenNull));
        result.setInd(from == null ? null : getCopy(from.getInd(), whenNull));
        result.setJc(from == null ? null : getCopy(from.getJc(), whenNull));
        result.setTextDirection(from == null ? null : getCopy(from.getTextDirection(), whenNull));
        result.setTextAlignment(from == null ? null : getCopy(from.getTextAlignment(), whenNull));
        result.setTextboxTightWrap(from == null ? null : getCopy(from.getTextboxTightWrap(), whenNull));
        result.setOutlineLvl(from == null ? null : getCopy(from.getOutlineLvl(), whenNull));
        result.setDivId(from == null ? null : getCopy(from.getDivId(), whenNull));
        result.setCnfStyle(from == null ? null : getCopy(from.getCnfStyle(), whenNull));

        if (from != null) {
            result.setKeepNext(from.getKeepNext());
            result.setKeepLines(from.getKeepLines());
            result.setPageBreakBefore(from.getPageBreakBefore());
            result.setWidowControl(from.getWidowControl());
            result.setSuppressLineNumbers(from.getSuppressLineNumbers());
            result.setSuppressAutoHyphens(from.getSuppressAutoHyphens());
            result.setKinsoku(from.getKinsoku());
            result.setWordWrap(from.getWordWrap());
            result.setOverflowPunct(from.getOverflowPunct());
            result.setTopLinePunct(from.getTopLinePunct());
            result.setAutoSpaceDE(from.getAutoSpaceDE());
            result.setAutoSpaceDN(from.getAutoSpaceDN());
            result.setBidi(from.getBidi());
            result.setAdjustRightInd(from.getAdjustRightInd());
            result.setSnapToGrid(from.getSnapToGrid());
            result.setContextualSpacing(from.getContextualSpacing());
            result.setMirrorIndents(from.getMirrorIndents());
            result.setSuppressOverlap(from.getSuppressOverlap());
            result.setCollapsed(from.getCollapsed());
        }
    }

    public PPr getExplicitPPr(final PPr pPr, final boolean whenNull) {
        PPr styledPPr = myFactory.createPPr();
        ArrayList<Style> styles = new ArrayList<Style>();
        final PPrBase.PStyle pStyle = pPr.getPStyle();
        if (pStyle != null) {
            String styleId = pStyle.getVal();
            Style style = myDocx.getStyle(styleId);
            while (style != null) {
                styles.add(style);
                Style.BasedOn basedOn = style.getBasedOn();
                style = null;
                if (basedOn != null) {
                    styleId = basedOn.getVal();
                    if (styleId != null) {
                        style = myDocx.getStyle(basedOn.getVal());
                    }
                }
            }
        }

        // copy out pPr and rPr from styles
        int iMax = styles.size();
        for (int i = iMax; i-- > 0; ) {
            Style style = styles.get(i);
            setPPrBase(styledPPr, style.getPPr(), whenNull);
            setRPr(styledPPr.getRPr(), style.getRPr(), whenNull);
        }
        setPPrBase(styledPPr, pPr, whenNull);
        setRPr(styledPPr.getRPr(), pPr.getRPr(), whenNull);

        return styledPPr;
    }

    public RPr getExplicitRPr(final RPrAbstract pPr, final boolean whenNull) {
        RPr styledPPr = myFactory.createRPr();
        ArrayList<Style> styles = new ArrayList<Style>();
        final RStyle pStyle = pPr.getRStyle();
        if (pStyle != null) {
            String styleId = pStyle.getVal();
            Style style = myDocx.getStyle(styleId);
            while (style != null) {
                styles.add(style);
                Style.BasedOn basedOn = style.getBasedOn();
                style = null;
                if (basedOn != null) {
                    styleId = basedOn.getVal();
                    if (styleId != null) {
                        style = myDocx.getStyle(basedOn.getVal());
                    }
                }
            }
        }

        // copy out pPr and rPr from styles
        int iMax = styles.size();
        for (int i = iMax; i-- > 0; ) {
            Style style = styles.get(i);
            setRPr(styledPPr, style.getRPr(), whenNull);
        }
        setRPr(styledPPr, pPr, whenNull);

        return styledPPr;
    }

    // diff helpers
    public PPrBase.Ind keepDiff(PPrBase.Ind orig, PPrBase.Ind from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getLeft() != null && orig.getLeft().equals(from.getLeft())) orig.setLeft(null);
            else hadSome = true;
            if (orig.getLeftChars() != null && orig.getLeftChars().equals(from.getLeftChars())) orig.setLeftChars(null);
            else hadSome = true;
            if (orig.getRight() != null && orig.getRight().equals(from.getRight())) orig.setRight(null);
            else hadSome = true;
            if (orig.getRightChars() != null && orig.getRightChars().equals(from.getRightChars())) orig.setRightChars(null);
            else hadSome = true;
            if (orig.getHanging() != null && orig.getHanging().equals(from.getHanging())) orig.setHanging(null);
            else hadSome = true;
            if (orig.getHangingChars() != null && orig.getHangingChars().equals(from.getHangingChars())) orig.setHangingChars(null);
            else hadSome = true;
            if (orig.getFirstLine() != null && orig.getFirstLine().equals(from.getFirstLine())) orig.setFirstLine(null);
            else hadSome = true;
            if (orig.getFirstLineChars() != null && orig.getFirstLineChars().equals(from.getFirstLineChars())) orig.setFirstLineChars(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public PPrBase.Spacing keepDiff(PPrBase.Spacing orig, PPrBase.Spacing from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getAfter() != null && orig.getAfter().equals(from.getAfter())) orig.setAfter(null);
            else hadSome = true;
            if (orig.getAfterLines() != null && orig.getAfterLines().equals(from.getAfterLines())) orig.setAfterLines(null);
            else hadSome = true;
            if (orig.getBefore() != null && orig.getBefore().equals(from.getBefore())) orig.setBefore(null);
            else hadSome = true;
            if (orig.getBeforeLines() != null && orig.getBeforeLines().equals(from.getBeforeLines())) orig.setBeforeLines(null);
            else hadSome = true;
            if (orig.getLine() != null && orig.getLine().equals(from.getLine())) orig.setLine(null);
            else hadSome = true;
            if (orig.isAfterAutospacing() == from.isAfterAutospacing()) orig.setAfterAutospacing(null);
            else hadSome = true;
            if (orig.isBeforeAutospacing() == from.isBeforeAutospacing()) orig.setBeforeAutospacing(null);
            else hadSome = true;
            if (orig.getLineRule() != null && orig.getLineRule().equals(from.getLineRule())) orig.setLineRule(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public PPrBase.NumPr.Ilvl keepDiff(PPrBase.NumPr.Ilvl orig, PPrBase.NumPr.Ilvl from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public PPrBase.NumPr.NumId keepDiff(PPrBase.NumPr.NumId orig, PPrBase.NumPr.NumId from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public CTTrackChangeNumbering keepDiff(CTTrackChangeNumbering orig, CTTrackChangeNumbering from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getOriginal() != null && orig.getOriginal().equals(from.getOriginal())) orig.setOriginal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public CTTrackChange keepDiff(CTTrackChange orig, CTTrackChange from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getDate() != null && orig.getDate().equals(from.getDate())) orig.setDate(null);
            else hadSome = true;
            if (orig.getAuthor() != null && orig.getAuthor().equals(from.getAuthor())) orig.setAuthor(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public PPrBase.NumPr keepDiff(PPrBase.NumPr orig, PPrBase.NumPr from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getIlvl() != null && orig.getIlvl().equals(from.getIlvl())) orig.setIlvl(null);
            else hadSome = true;
            if (orig.getNumId() != null && orig.getNumId().equals(from.getNumId())) orig.setNumId(null);
            else hadSome = true;
            if (orig.getNumberingChange() != null && orig.getNumberingChange().equals(from.getNumberingChange())) orig.setNumberingChange(null);
            else hadSome = true;
            if (orig.getIns() != null && orig.getIns().equals(from.getIns())) orig.setIns(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public CTBorder keepDiff(CTBorder orig, CTBorder from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (orig.getThemeColor() != null && orig.getThemeColor().equals(from.getThemeColor())) orig.setThemeColor(null);
            else hadSome = true;
            if (orig.getSpace() != null && orig.getSpace().equals(from.getSpace())) orig.setSpace(null);
            else hadSome = true;
            if (orig.getSz() != null && orig.getSz().equals(from.getSz())) orig.setSz(null);
            else hadSome = true;
            if (orig.isFrame() == from.isFrame()) orig.setFrame(null);
            else hadSome = true;
            if (orig.isShadow() == from.isShadow()) orig.setShadow(null);
            else hadSome = true;
            if (orig.getColor() != null && orig.getColor().equals(from.getColor())) orig.setColor(null);
            else hadSome = true;
            if (orig.getThemeShade() != null && orig.getThemeShade().equals(from.getThemeShade())) orig.setThemeShade(null);
            else hadSome = true;
            if (orig.getThemeTint() != null && orig.getThemeTint().equals(from.getThemeTint())) orig.setThemeTint(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public PPrBase.PBdr keepDiff(PPrBase.PBdr orig, PPrBase.PBdr from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getTop() != null && orig.getTop().equals(from.getTop())) orig.setTop(null);
            else hadSome = true;
            if (orig.getLeft() != null && orig.getLeft().equals(from.getLeft())) orig.setLeft(null);
            else hadSome = true;
            if (orig.getBottom() != null && orig.getBottom().equals(from.getBottom())) orig.setBottom(null);
            else hadSome = true;
            if (orig.getRight() != null && orig.getRight().equals(from.getRight())) orig.setRight(null);
            else hadSome = true;
            if (orig.getBetween() != null && orig.getBetween().equals(from.getBetween())) orig.setBetween(null);
            else hadSome = true;
            if (orig.getBar() != null && orig.getBar().equals(from.getBar())) orig.setBar(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public PPrBase.PStyle keepDiff(PPrBase.PStyle orig, PPrBase.PStyle from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public CTFramePr keepDiff(CTFramePr orig, CTFramePr from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getDropCap() != null && orig.getDropCap().equals(from.getDropCap())) orig.setDropCap(null);
            else hadSome = true;
            if (orig.getLines() != null && orig.getLines().equals(from.getLines())) orig.setLines(null);
            else hadSome = true;
            if (orig.getW() != null && orig.getW().equals(from.getW())) orig.setW(null);
            else hadSome = true;
            if (orig.getH() != null && orig.getH().equals(from.getH())) orig.setH(null);
            else hadSome = true;
            if (orig.getVSpace() != null && orig.getVSpace().equals(from.getVSpace())) orig.setVSpace(null);
            else hadSome = true;
            if (orig.getHSpace() != null && orig.getHSpace().equals(from.getHSpace())) orig.setHSpace(null);
            else hadSome = true;
            if (orig.getWrap() != null && orig.getWrap().equals(from.getWrap())) orig.setWrap(null);
            else hadSome = true;
            if (orig.getHAnchor() != null && orig.getHAnchor().equals(from.getHAnchor())) orig.setHAnchor(null);
            else hadSome = true;
            if (orig.getVAnchor() != null && orig.getVAnchor().equals(from.getVAnchor())) orig.setVAnchor(null);
            else hadSome = true;
            if (orig.getX() != null && orig.getX().equals(from.getX())) orig.setX(null);
            else hadSome = true;
            if (orig.getXAlign() != null && orig.getXAlign().equals(from.getXAlign())) orig.setXAlign(null);
            else hadSome = true;
            if (orig.getY() != null && orig.getY().equals(from.getY())) orig.setY(null);
            else hadSome = true;
            if (orig.getYAlign() != null && orig.getYAlign().equals(from.getYAlign())) orig.setYAlign(null);
            else hadSome = true;
            if (orig.getHRule() != null && orig.getHRule().equals(from.getHRule())) orig.setHRule(null);
            else hadSome = true;
            if (orig.isAnchorLock() == from.isAnchorLock()) orig.setAnchorLock(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public CTShd keepDiff(CTShd orig, CTShd from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (orig.getColor() != null && orig.getColor().equals(from.getColor())) orig.setColor(null);
            else hadSome = true;
            if (orig.getThemeColor() != null && orig.getThemeColor().equals(from.getThemeColor())) orig.setThemeColor(null);
            else hadSome = true;
            if (orig.getThemeTint() != null && orig.getThemeTint().equals(from.getThemeTint())) orig.setThemeTint(null);
            else hadSome = true;
            if (orig.getThemeShade() != null && orig.getThemeShade().equals(from.getThemeShade())) orig.setThemeShade(null);
            else hadSome = true;
            if (orig.getFill() != null && orig.getFill().equals(from.getFill())) orig.setFill(null);
            else hadSome = true;
            if (orig.getThemeFill() != null && orig.getThemeFill().equals(from.getThemeFill())) orig.setThemeFill(null);
            else hadSome = true;
            if (orig.getThemeFillTint() != null && orig.getThemeFillTint().equals(from.getThemeFillTint())) orig.setThemeFillTint(null);
            else hadSome = true;
            if (orig.getThemeFillShade() != null && orig.getThemeFillShade().equals(from.getThemeFillShade())) orig.setThemeFillShade(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public Tabs keepDiff(Tabs orig, Tabs from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            orig.getTab().addAll(from.getTab());
            if (hadSome) return orig;
        }
        return null;
    }

    public Jc keepDiff(Jc orig, Jc from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public TextDirection keepDiff(TextDirection orig, TextDirection from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public PPrBase.TextAlignment keepDiff(PPrBase.TextAlignment orig, PPrBase.TextAlignment from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public CTTextboxTightWrap keepDiff(CTTextboxTightWrap orig, CTTextboxTightWrap from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public PPrBase.OutlineLvl keepDiff(PPrBase.OutlineLvl orig, PPrBase.OutlineLvl from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public PPrBase.DivId keepDiff(PPrBase.DivId orig, PPrBase.DivId from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public CTCnf keepDiff(CTCnf orig, CTCnf from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public ParaRPr keepDiff(ParaRPr orig, ParaRPr from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getIns() != null && orig.getIns().equals(from.getIns())) orig.setIns(null);
            else hadSome = true;
            if (orig.getDel() != null && orig.getDel().equals(from.getDel())) orig.setDel(null);
            else hadSome = true;
            if (orig.getMoveFrom() != null && orig.getMoveFrom().equals(from.getMoveFrom())) orig.setMoveFrom(null);
            else hadSome = true;
            if (orig.getMoveTo() != null && orig.getMoveTo().equals(from.getMoveTo())) orig.setMoveTo(null);
            else hadSome = true;
            ParaRPr rPr = keepDiff(orig, from);
            if (rPr != null || hadSome) return orig;
        }
        return null;
    }

    public RStyle keepDiff(RStyle orig, RStyle from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public RFonts keepDiff(RFonts orig, RFonts from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getHint() != null && orig.getHint().equals(from.getHint())) orig.setHint(null);
            else hadSome = true;
            if (orig.getAscii() != null && orig.getAscii().equals(from.getAscii())) orig.setAscii(null);
            else hadSome = true;
            if (orig.getHAnsi() != null && orig.getHAnsi().equals(from.getHAnsi())) orig.setHAnsi(null);
            else hadSome = true;
            if (orig.getEastAsia() != null && orig.getEastAsia().equals(from.getEastAsia())) orig.setEastAsia(null);
            else hadSome = true;
            if (orig.getCs() != null && orig.getCs().equals(from.getCs())) orig.setCs(null);
            else hadSome = true;
            if (orig.getAsciiTheme() != null && orig.getAsciiTheme().equals(from.getAsciiTheme())) orig.setAsciiTheme(null);
            else hadSome = true;
            if (orig.getHAnsiTheme() != null && orig.getHAnsiTheme().equals(from.getHAnsiTheme())) orig.setHAnsiTheme(null);
            else hadSome = true;
            if (orig.getEastAsiaTheme() != null && orig.getEastAsiaTheme().equals(from.getEastAsiaTheme())) orig.setEastAsiaTheme(null);
            else hadSome = true;
            if (orig.getCstheme() != null && orig.getCstheme().equals(from.getCstheme())) orig.setCstheme(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public Color keepDiff(Color orig, Color from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (orig.getThemeColor() != null && orig.getThemeColor().equals(from.getThemeColor())) orig.setThemeColor(null);
            else hadSome = true;
            if (orig.getThemeTint() != null && orig.getThemeTint().equals(from.getThemeTint())) orig.setThemeTint(null);
            else hadSome = true;
            if (orig.getThemeShade() != null && orig.getThemeShade().equals(from.getThemeShade())) orig.setThemeShade(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public CTSignedTwipsMeasure keepDiff(CTSignedTwipsMeasure orig, CTSignedTwipsMeasure from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public CTTextScale keepDiff(CTTextScale orig, CTTextScale from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public HpsMeasure keepDiff(HpsMeasure orig, HpsMeasure from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public CTSignedHpsMeasure keepDiff(CTSignedHpsMeasure orig, CTSignedHpsMeasure from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public Highlight keepDiff(Highlight orig, Highlight from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public U keepDiff(U orig, U from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (orig.getColor() != null && orig.getColor().equals(from.getColor())) orig.setColor(null);
            else hadSome = true;
            if (orig.getThemeColor() != null && orig.getThemeColor().equals(from.getThemeColor())) orig.setThemeColor(null);
            else hadSome = true;
            if (orig.getThemeTint() != null && orig.getThemeTint().equals(from.getThemeTint())) orig.setThemeTint(null);
            else hadSome = true;
            if (orig.getThemeShade() != null && orig.getThemeShade().equals(from.getThemeShade())) orig.setThemeShade(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public CTTextEffect keepDiff(CTTextEffect orig, CTTextEffect from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public CTFitText keepDiff(CTFitText orig, CTFitText from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (orig.getId() != null && orig.getId().equals(from.getId())) orig.setId(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public CTVerticalAlignRun keepDiff(CTVerticalAlignRun orig, CTVerticalAlignRun from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public CTEm keepDiff(CTEm orig, CTEm from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public CTLanguage keepDiff(CTLanguage orig, CTLanguage from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getVal() != null && orig.getVal().equals(from.getVal())) orig.setVal(null);
            else hadSome = true;
            if (orig.getEastAsia() != null && orig.getEastAsia().equals(from.getEastAsia())) orig.setEastAsia(null);
            else hadSome = true;
            if (orig.getBidi() != null && orig.getBidi().equals(from.getBidi())) orig.setBidi(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public CTEastAsianLayout keepDiff(CTEastAsianLayout orig, CTEastAsianLayout from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getId() != null && orig.getId().equals(from.getId())) orig.setId(null);
            else hadSome = true;
            if (orig.isCombine() == from.isCombine()) orig.setCombine(null);
            else hadSome = true;
            if (orig.getCombineBrackets() != null && orig.getCombineBrackets().equals(from.getCombineBrackets())) orig.setCombineBrackets(null);
            else hadSome = true;
            if (orig.isVert() == from.isVert()) orig.setVert(null);
            else hadSome = true;
            if (orig.isVertCompress() == from.isVertCompress()) orig.setVertCompress(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public <T extends RPrAbstract> T keepDiff(final T orig, final RPrAbstract from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getRStyle() != null && orig.getRStyle().equals(from.getRStyle())) orig.setRStyle(null);
            else hadSome = true;
            if (orig.getRFonts() != null && orig.getRFonts().equals(from.getRFonts())) orig.setRFonts(null);
            else hadSome = true;
            if (orig.getColor() != null && orig.getColor().equals(from.getColor())) orig.setColor(null);
            else hadSome = true;
            if (orig.getSpacing() != null && orig.getSpacing().equals(from.getSpacing())) orig.setSpacing(null);
            else hadSome = true;
            if (orig.getW() != null && orig.getW().equals(from.getW())) orig.setW(null);
            else hadSome = true;
            if (orig.getKern() != null && orig.getKern().equals(from.getKern())) orig.setKern(null);
            else hadSome = true;
            if (orig.getPosition() != null && orig.getPosition().equals(from.getPosition())) orig.setPosition(null);
            else hadSome = true;
            if (orig.getSz() != null && orig.getSz().equals(from.getSz())) orig.setSz(null);
            else hadSome = true;
            if (orig.getSzCs() != null && orig.getSzCs().equals(from.getSzCs())) orig.setSzCs(null);
            else hadSome = true;
            if (orig.getHighlight() != null && orig.getHighlight().equals(from.getHighlight())) orig.setHighlight(null);
            else hadSome = true;
            if (orig.getU() != null && orig.getU().equals(from.getU())) orig.setU(null);
            else hadSome = true;
            if (orig.getEffect() != null && orig.getEffect().equals(from.getEffect())) orig.setEffect(null);
            else hadSome = true;
            if (orig.getBdr() != null && orig.getBdr().equals(from.getBdr())) orig.setBdr(null);
            else hadSome = true;
            if (orig.getShd() != null && orig.getShd().equals(from.getShd())) orig.setShd(null);
            else hadSome = true;
            if (orig.getFitText() != null && orig.getFitText().equals(from.getFitText())) orig.setFitText(null);
            else hadSome = true;
            if (orig.getVertAlign() != null && orig.getVertAlign().equals(from.getVertAlign())) orig.setVertAlign(null);
            else hadSome = true;
            if (orig.getEm() != null && orig.getEm().equals(from.getEm())) orig.setEm(null);
            else hadSome = true;
            if (orig.getLang() != null && orig.getLang().equals(from.getLang())) orig.setLang(null);
            else hadSome = true;
            if (orig.getEastAsianLayout() != null && orig.getEastAsianLayout().equals(from.getEastAsianLayout())) orig.setEastAsianLayout(null);
            else hadSome = true;
            //if (orig.getRPrChange() != null && orig.getRPrChange().equals(from.getRPrChange())) orig.setRPrChange(null); else hadSome = true;

            if (orig.getB() != null && orig.getB().equals(from.getB())) orig.setB(null);
            else hadSome = true;
            if (orig.getBCs() != null && orig.getBCs().equals(from.getBCs())) orig.setBCs(null);
            else hadSome = true;
            if (orig.getI() != null && orig.getI().equals(from.getI())) orig.setI(null);
            else hadSome = true;
            if (orig.getICs() != null && orig.getICs().equals(from.getICs())) orig.setICs(null);
            else hadSome = true;
            if (orig.getCaps() != null && orig.getCaps().equals(from.getCaps())) orig.setCaps(null);
            else hadSome = true;
            if (orig.getSmallCaps() != null && orig.getSmallCaps().equals(from.getSmallCaps())) orig.setSmallCaps(null);
            else hadSome = true;
            if (orig.getStrike() != null && orig.getStrike().equals(from.getStrike())) orig.setStrike(null);
            else hadSome = true;
            if (orig.getDstrike() != null && orig.getDstrike().equals(from.getDstrike())) orig.setDstrike(null);
            else hadSome = true;
            if (orig.getOutline() != null && orig.getOutline().equals(from.getOutline())) orig.setOutline(null);
            else hadSome = true;
            if (orig.getShadow() != null && orig.getShadow().equals(from.getShadow())) orig.setShadow(null);
            else hadSome = true;
            if (orig.getEmboss() != null && orig.getEmboss().equals(from.getEmboss())) orig.setEmboss(null);
            else hadSome = true;
            if (orig.getImprint() != null && orig.getImprint().equals(from.getImprint())) orig.setImprint(null);
            else hadSome = true;
            if (orig.getNoProof() != null && orig.getNoProof().equals(from.getNoProof())) orig.setNoProof(null);
            else hadSome = true;
            if (orig.getSnapToGrid() != null && orig.getSnapToGrid().equals(from.getSnapToGrid())) orig.setSnapToGrid(null);
            else hadSome = true;
            if (orig.getVanish() != null && orig.getVanish().equals(from.getVanish())) orig.setVanish(null);
            else hadSome = true;
            if (orig.getWebHidden() != null && orig.getWebHidden().equals(from.getWebHidden())) orig.setWebHidden(null);
            else hadSome = true;
            if (orig.getRtl() != null && orig.getRtl().equals(from.getRtl())) orig.setRtl(null);
            else hadSome = true;
            if (orig.getCs() != null && orig.getCs().equals(from.getCs())) orig.setCs(null);
            else hadSome = true;
            if (orig.getSpecVanish() != null && orig.getSpecVanish().equals(from.getSpecVanish())) orig.setSpecVanish(null);
            else hadSome = true;
            if (orig.getOMath() != null && orig.getOMath().equals(from.getOMath())) orig.setOMath(null);
            else hadSome = true;
            if (hadSome) return orig;
        }
        return null;
    }

    public <T extends PPrBase> T keepDiff(final T orig, final PPrBase from) {
        if (from != null && orig != null) {
            boolean hadSome = false;
            if (orig.getPStyle() != null && orig.getPStyle().equals(from.getPStyle())) orig.setPStyle(null);
            else hadSome = true;
            if (orig.getFramePr() != null && orig.getFramePr().equals(from.getFramePr())) orig.setFramePr(null);
            else hadSome = true;
            if (orig.getNumPr() != null && orig.getNumPr().equals(from.getNumPr())) orig.setNumPr(null);
            else hadSome = true;
            if (orig.getPBdr() != null && orig.getPBdr().equals(from.getPBdr())) orig.setPBdr(null);
            else hadSome = true;
            if (orig.getShd() != null && orig.getShd().equals(from.getShd())) orig.setShd(null);
            else hadSome = true;
            if (orig.getTabs() != null && orig.getTabs().equals(from.getTabs())) orig.setTabs(null);
            else hadSome = true;
            if (orig.getSpacing() != null && orig.getSpacing().equals(from.getSpacing())) orig.setSpacing(null);
            else hadSome = true;
            if (orig.getInd() != null && orig.getInd().equals(from.getInd())) orig.setInd(null);
            else hadSome = true;
            if (orig.getJc() != null && orig.getJc().equals(from.getJc())) orig.setJc(null);
            else hadSome = true;
            if (orig.getTextDirection() != null && orig.getTextDirection().equals(from.getTextDirection())) orig.setTextDirection(null);
            else hadSome = true;
            if (orig.getTextAlignment() != null && orig.getTextAlignment().equals(from.getTextAlignment())) orig.setTextAlignment(null);
            else hadSome = true;
            if (orig.getTextboxTightWrap() != null && orig.getTextboxTightWrap().equals(from.getTextboxTightWrap())) orig.setTextboxTightWrap(null);
            else hadSome = true;
            if (orig.getOutlineLvl() != null && orig.getOutlineLvl().equals(from.getOutlineLvl())) orig.setOutlineLvl(null);
            else hadSome = true;
            if (orig.getDivId() != null && orig.getDivId().equals(from.getDivId())) orig.setDivId(null);
            else hadSome = true;
            if (orig.getCnfStyle() != null && orig.getCnfStyle().equals(from.getCnfStyle())) orig.setCnfStyle(null);
            else hadSome = true;

            if (orig.getKeepNext() != null && orig.getKeepNext().equals(from.getKeepNext())) orig.setKeepNext(null);
            else hadSome = true;
            if (orig.getKeepLines() != null && orig.getKeepLines().equals(from.getKeepLines())) orig.setKeepLines(null);
            else hadSome = true;
            if (orig.getPageBreakBefore() != null && orig.getPageBreakBefore().equals(from.getPageBreakBefore())) orig.setPageBreakBefore(null);
            else hadSome = true;
            if (orig.getWidowControl() != null && orig.getWidowControl().equals(from.getWidowControl())) orig.setWidowControl(null);
            else hadSome = true;
            if (orig.getSuppressLineNumbers() != null && orig.getSuppressLineNumbers().equals(from.getSuppressLineNumbers())) orig.setSuppressLineNumbers(null);
            else hadSome = true;
            if (orig.getSuppressAutoHyphens() != null && orig.getSuppressAutoHyphens().equals(from.getSuppressAutoHyphens())) orig.setSuppressAutoHyphens(null);
            else hadSome = true;
            if (orig.getKinsoku() != null && orig.getKinsoku().equals(from.getKinsoku())) orig.setKinsoku(null);
            else hadSome = true;
            if (orig.getWordWrap() != null && orig.getWordWrap().equals(from.getWordWrap())) orig.setWordWrap(null);
            else hadSome = true;
            if (orig.getOverflowPunct() != null && orig.getOverflowPunct().equals(from.getOverflowPunct())) orig.setOverflowPunct(null);
            else hadSome = true;
            if (orig.getTopLinePunct() != null && orig.getTopLinePunct().equals(from.getTopLinePunct())) orig.setTopLinePunct(null);
            else hadSome = true;
            if (orig.getAutoSpaceDE() != null && orig.getAutoSpaceDE().equals(from.getAutoSpaceDE())) orig.setAutoSpaceDE(null);
            else hadSome = true;
            if (orig.getAutoSpaceDN() != null && orig.getAutoSpaceDN().equals(from.getAutoSpaceDN())) orig.setAutoSpaceDN(null);
            else hadSome = true;
            if (orig.getBidi() != null && orig.getBidi().equals(from.getBidi())) orig.setBidi(null);
            else hadSome = true;
            if (orig.getAdjustRightInd() != null && orig.getAdjustRightInd().equals(from.getAdjustRightInd())) orig.setAdjustRightInd(null);
            else hadSome = true;
            if (orig.getSnapToGrid() != null && orig.getSnapToGrid().equals(from.getSnapToGrid())) orig.setSnapToGrid(null);
            else hadSome = true;
            if (orig.getContextualSpacing() != null && orig.getContextualSpacing().equals(from.getContextualSpacing())) orig.setContextualSpacing(null);
            else hadSome = true;
            if (orig.getMirrorIndents() != null && orig.getMirrorIndents().equals(from.getMirrorIndents())) orig.setMirrorIndents(null);
            else hadSome = true;
            if (orig.getSuppressOverlap() != null && orig.getSuppressOverlap().equals(from.getSuppressOverlap())) orig.setSuppressOverlap(null);
            else hadSome = true;
            if (orig.getCollapsed() != null && orig.getCollapsed().equals(from.getCollapsed())) orig.setCollapsed(null);
            else hadSome = true;

            if (hadSome) return orig;
        }
        return null;
    }
}
