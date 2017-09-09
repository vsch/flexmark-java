package com.vladsch.flexmark.docx.converter.internal;

import org.docx4j.wml.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;

public class DocxHelper {
    protected final ObjectFactory myFactory;

    public DocxHelper(final ObjectFactory factory) {
        myFactory = factory;
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
                return safeBigInt(copy).add(safeBigInt(orig));
            } else if (this == MAX) {
                return safeBigInt(copy).max(safeBigInt(orig));
            } else if (this == MIN) {
                return safeBigInt(copy).max(safeBigInt(orig));
            } else if (this == ADD_OTHER) {
                return safeBigInt(copy).add(safeBigInt(orig).add(safeBigInt(other)));
            } else if (this == MAX_OTHER) {
                return safeBigInt(copy).max(safeBigInt(orig).max(safeBigInt(other)));
            } else if (this == MIN_OTHER) {
                return safeBigInt(copy).min(safeBigInt(orig).min(safeBigInt(other)));
            } else if (this == RANGE) {
                return safeBigInt(copy).max(safeBigInt(orig).min(safeBigInt(other)));
            } else {
                return copy;
            }
        }
    }

    public static BigInteger safeBigInt(BigInteger value) {
        return value == null ? BigInteger.ZERO : value;
    }

    public static BigInteger safeBigInt(BigInteger value, long nullValue) {
        return value == null ? BigInteger.valueOf(nullValue) : value;
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
            result.setIlvl(getCopy((PPrBase.NumPr.Ilvl)null, whenNull));
            result.setNumId(getCopy((PPrBase.NumPr.NumId)null, whenNull));
            result.setNumberingChange(getCopy((CTTrackChangeNumbering) null, whenNull));
            result.setIns(getCopy((CTTrackChange)null, whenNull));
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
            result.setTop(getCopy((CTBorder)null, whenNull));
            result.setLeft(getCopy((CTBorder)null, whenNull));
            result.setBottom(getCopy((CTBorder)null, whenNull));
            result.setRight(getCopy((CTBorder)null, whenNull));
            result.setBetween(getCopy((CTBorder)null, whenNull));
            result.setBar(getCopy((CTBorder)null, whenNull));
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
            result.setPStyle(getCopy((PPrBase.PStyle)null, whenNull));
            result.setFramePr(getCopy((CTFramePr)null, whenNull));
            result.setNumPr(getCopy((PPrBase.NumPr)null, whenNull));
            result.setPBdr(getCopy((PPrBase.PBdr)null, whenNull));
            result.setShd(getCopy((CTShd) null, whenNull));
            result.setTabs(getCopy((Tabs)null, whenNull));
            result.setSpacing(getCopy((PPrBase.Spacing)null, whenNull));
            result.setInd(getCopy((PPrBase.Ind)null, whenNull));
            result.setJc(getCopy((Jc)null, whenNull));
            result.setTextDirection(getCopy((TextDirection) null, whenNull));
            result.setTextAlignment(getCopy((PPrBase.TextAlignment)null, whenNull));
            result.setTextboxTightWrap(getCopy((CTTextboxTightWrap)null, whenNull));
            result.setOutlineLvl(getCopy((PPrBase.OutlineLvl)null, whenNull));
            result.setDivId(getCopy((PPrBase.DivId)null, whenNull));
            result.setCnfStyle(getCopy((CTCnf)null, whenNull));
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
            result.setIns(getCopy((CTTrackChange)null, whenNull));
            result.setDel(getCopy((CTTrackChange)null, whenNull));
            result.setMoveFrom(getCopy((CTTrackChange)null, whenNull));
            result.setMoveTo(getCopy((CTTrackChange)null, whenNull));
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

}
