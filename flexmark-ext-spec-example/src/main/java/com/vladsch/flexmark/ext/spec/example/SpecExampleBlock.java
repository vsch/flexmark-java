package com.vladsch.flexmark.ext.spec.example;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A SpecExample block node
 */
public class SpecExampleBlock extends Block {
    private BasedSequence openingMarker = BasedSequence.NULL;
    private BasedSequence exampleKeyword = BasedSequence.NULL;
    private BasedSequence coordOpeningMarker = BasedSequence.NULL;
    private BasedSequence section = BasedSequence.NULL;
    private BasedSequence numberSeparator = BasedSequence.NULL;
    private BasedSequence number = BasedSequence.NULL;
    private BasedSequence coordClosingMarker = BasedSequence.NULL;
    private BasedSequence optionsKeyword = BasedSequence.NULL;
    private BasedSequence optionsOpeningMarker = BasedSequence.NULL;
    private BasedSequence options = BasedSequence.NULL;
    private BasedSequence optionsClosingMarker = BasedSequence.NULL;
    private BasedSequence source = BasedSequence.NULL;
    private BasedSequence htmlSeparator = BasedSequence.NULL;
    private BasedSequence html = BasedSequence.NULL;
    private BasedSequence astSeparator = BasedSequence.NULL;
    private BasedSequence ast = BasedSequence.NULL;
    private BasedSequence closingMarker = BasedSequence.NULL;

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        //BasedSequence content = getContentChars();
        //int lines = getSegments().length;
        //segmentSpanChars(out, openingMarker, "open");
        //segmentSpanChars(out, section, "info");
        //segmentSpan(out, content, "content");
        //out.append(" lines[").append(lines).append("]");
        //segmentSpanChars(out, closingMarker, "close");
        if (openingMarker.isNotNull()) segmentSpan(out, openingMarker, "openingMarker");
        if (exampleKeyword.isNotNull()) segmentSpan(out, exampleKeyword, "exampleKeyword");
        if (coordOpeningMarker.isNotNull()) segmentSpan(out, coordOpeningMarker, "coordOpeningMarker");
        if (section.isNotNull()) segmentSpan(out, section, "section");
        if (numberSeparator.isNotNull()) segmentSpan(out, numberSeparator, "numberSeparator");
        if (number.isNotNull()) segmentSpan(out, number, "number");
        if (coordClosingMarker.isNotNull()) segmentSpan(out, coordClosingMarker, "coordClosingMarker");
        if (optionsKeyword.isNotNull()) segmentSpan(out, optionsKeyword, "optionsKeyword");
        if (optionsOpeningMarker.isNotNull()) segmentSpan(out, optionsOpeningMarker, "optionsOpeningMarker");
        if (options.isNotNull()) segmentSpan(out, options, "options");
        if (optionsClosingMarker.isNotNull()) segmentSpan(out, optionsClosingMarker, "optionsClosingMarker");
        if (source.isNotNull()) segmentSpan(out, source, "source");
        if (htmlSeparator.isNotNull()) segmentSpan(out, htmlSeparator, "htmlSeparator");
        if (html.isNotNull()) segmentSpan(out, html, "html");
        if (astSeparator.isNotNull()) segmentSpan(out, astSeparator, "astSeparator");
        if (ast.isNotNull()) segmentSpan(out, ast, "ast");
        if (closingMarker.isNotNull()) segmentSpan(out, closingMarker, "closingMarker");
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] {
                openingMarker,
                exampleKeyword,
                coordOpeningMarker,
                section,
                numberSeparator,
                number,
                coordClosingMarker,
                optionsKeyword,
                optionsOpeningMarker,
                options,
                optionsClosingMarker,
                source,
                htmlSeparator,
                html,
                astSeparator,
                ast,
                closingMarker
        };
    }

    public SpecExampleBlock() {
    }

    public SpecExampleBlock(BasedSequence chars) {
        super(chars);
    }

    public SpecExampleBlock(BasedSequence chars, BasedSequence openingMarker, List<BasedSequence> segments, BasedSequence closingMarker) {
        super(chars, segments);
        this.openingMarker = openingMarker;
        this.closingMarker = closingMarker;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getExampleKeyword() {
        return exampleKeyword;
    }

    public void setExampleKeyword(BasedSequence exampleKeyword) {
        this.exampleKeyword = exampleKeyword;
    }

    public BasedSequence getCoordOpeningMarker() {
        return coordOpeningMarker;
    }

    public void setCoordOpeningMarker(BasedSequence coordOpeningMarker) {
        this.coordOpeningMarker = coordOpeningMarker;
    }

    public BasedSequence getSection() {
        return section;
    }

    public void setSection(BasedSequence section) {
        this.section = section;
    }

    public BasedSequence getNumberSeparator() {
        return numberSeparator;
    }

    public void setNumberSeparator(BasedSequence numberSeparator) {
        this.numberSeparator = numberSeparator;
    }

    public BasedSequence getNumber() {
        return number;
    }

    public void setNumber(BasedSequence number) {
        this.number = number;
    }

    public BasedSequence getCoordClosingMarker() {
        return coordClosingMarker;
    }

    public void setCoordClosingMarker(BasedSequence coordClosingMarker) {
        this.coordClosingMarker = coordClosingMarker;
    }

    public BasedSequence getOptionsKeyword() {
        return optionsKeyword;
    }

    public void setOptionsKeyword(BasedSequence optionsKeyword) {
        this.optionsKeyword = optionsKeyword;
    }

    public BasedSequence getOptionsOpeningMarker() {
        return optionsOpeningMarker;
    }

    public void setOptionsOpeningMarker(BasedSequence optionsOpeningMarker) {
        this.optionsOpeningMarker = optionsOpeningMarker;
    }

    public BasedSequence getOptions() {
        return options;
    }

    public void setOptions(BasedSequence options) {
        this.options = options;
    }

    public BasedSequence getOptionsClosingMarker() {
        return optionsClosingMarker;
    }

    public void setOptionsClosingMarker(BasedSequence optionsClosingMarker) {
        this.optionsClosingMarker = optionsClosingMarker;
    }

    public BasedSequence getSource() {
        return source;
    }

    public void setSource(BasedSequence source) {
        this.source = source;
    }

    public BasedSequence getHtmlSeparator() {
        return htmlSeparator;
    }

    public void setHtmlSeparator(BasedSequence htmlSeparator) {
        this.htmlSeparator = htmlSeparator;
    }

    public BasedSequence getHtml() {
        return html;
    }

    public void setHtml(BasedSequence html) {
        this.html = html;
    }

    public BasedSequence getAstSeparator() {
        return astSeparator;
    }

    public void setAstSeparator(BasedSequence astSeparator) {
        this.astSeparator = astSeparator;
    }

    public BasedSequence getAst() {
        return ast;
    }

    public void setAst(BasedSequence ast) {
        this.ast = ast;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }
}
