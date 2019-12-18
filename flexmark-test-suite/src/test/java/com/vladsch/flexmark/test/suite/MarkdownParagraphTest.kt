package com.vladsch.flexmark.test.suite

import com.vladsch.flexmark.util.Utils
import com.vladsch.flexmark.util.format.MarkdownParagraph
import com.vladsch.flexmark.util.format.TextAlignment
import com.vladsch.flexmark.util.sequence.BasedSequence
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MarkdownParagraphTest {
    companion object {
        @JvmField
        var iterations = 0
    }

    val simplePar = """Lorem ipsum dolor sit amet, consectetaur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."""

    val multiLinePar = """Lorem ipsum dolor sit amet, consectetaur adipisicing elit,
sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
nisi ut aliquip ex ea commodo consequat.

Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum Et harumd und lookum like Greek to me, dereud facilis est er expedit distinct.
Nam liber te conscient to factor tum poen legum odioque civiuda.
Et tam neque pecun modut est neque nonor et imper ned libidig met,
consectetur adipiscing elit, sed ut labore et dolore magna aliquam makes one wonder who would ever read this stuff?
Bis nostrud exercitation ullam mmodo consequet.
Duis aute in voluptate velit esse cillum dolore eu fugiat nulla pariatur."""

    val indentedMultiLinePar = """    Lorem ipsum dolor sit amet, consectetaur adipisicing elit,
    sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
    Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
    nisi ut aliquip ex ea commodo consequat.

    Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
    Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum Et harumd und lookum like Greek to me, dereud facilis est er expedit distinct.
    Nam liber te conscient to factor tum poen legum odioque civiuda.
    Et tam neque pecun modut est neque nonor et imper ned libidig met,
    consectetur adipiscing elit, sed ut labore et dolore magna aliquam makes one wonder who would ever read this stuff?
    Bis nostrud exercitation ullam mmodo consequet.
    Duis aute in voluptate velit esse cillum dolore eu fugiat nulla pariatur."""

    val hard = "  "
    val multiLineHardBreaksPar = """Lorem ipsum dolor sit amet, consectetaur adipisicing elit,
sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.$hard
Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
nisi ut aliquip ex ea commodo consequat.$hard

Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.$hard
Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum Et harumd und lookum like Greek to me, dereud facilis est er expedit distinct.$hard
Nam liber te conscient to factor tum poen legum odioque civiuda.$hard
Et tam neque pecun modut est neque nonor et imper ned libidig met,
consectetur adipiscing elit, sed ut labore et dolore magna aliquam makes one wonder who would ever read this stuff?
Bis nostrud exercitation ullam mmodo consequet.$hard
Duis aute in voluptate velit esse cillum dolore eu fugiat nulla pariatur."""

    val logOutput = true

    fun ensureAligned(alignment: TextAlignment, chars: BasedSequence, firstIndent: Int, indent: Int, width: Int, respectHardBreaks: Boolean) {
        iterations++

        val lines = chars.split("\n", 0, 0)
        var lineCount = 0
        for (line in lines) {
            val indentSize = if (lineCount == 0) firstIndent else indent
            val trimmed = line.trim()

            val padding = Utils.minLimit(0, width - indentSize - trimmed.length)

            val leftPad: Int
            val rightPad: Int
            var testAlignment = alignment
            val isHardBreakLine = lineCount == lines.lastIndex || trimmed.endsWith('.') && respectHardBreaks

            when (alignment) {
                TextAlignment.LEFT -> {
                    leftPad = 0
                    rightPad = padding - leftPad
                }
                TextAlignment.CENTER -> {
                    leftPad = padding / 2
                    rightPad = padding - leftPad
                }
                TextAlignment.RIGHT -> {
                    leftPad = padding
                    rightPad = padding - leftPad
                }
                TextAlignment.JUSTIFIED -> {
                    if (!isHardBreakLine) {
                        leftPad = 0
                        rightPad = 0
                    } else {
                        leftPad = 0
                        rightPad = padding - leftPad
                        testAlignment = TextAlignment.LEFT
                    }
                }
            }

            assertEquals("Testing left padding on line '$line' $lineCount $leftPad", leftPad + indentSize, line.countLeading(BasedSequence.SPACE_SET))

            val actualRightPad = width - leftPad - indentSize - trimmed.length

            when (testAlignment) {
                TextAlignment.LEFT,
                TextAlignment.CENTER -> {
                    if (trimmed.contains(' ')) {
                        //                        if (rightPad != actualRightPad) {
                        //                            val tmp = 0
                        //                        }
                        assertEquals("Testing right padding on line '$line' $lineCount $rightPad", rightPad, actualRightPad)
                        assertTrue("Testing fit on line $lineCount, '$trimmed'.length ${line.length} < $width", trimmed.length <= width - indentSize)
                    } else if (trimmed.length < width - indentSize) {
                        assertEquals("Testing right padding on line '$line' $lineCount $rightPad", true, rightPad <= actualRightPad)
                    }
                }
                TextAlignment.RIGHT -> {
                    if (trimmed.contains(' ')) {
                        //                        if (rightPad != actualRightPad) {
                        //                            val tmp = 0
                        //                        }
                        assertEquals("Testing right padding on line '$line' $lineCount $rightPad", rightPad, actualRightPad)
                        assertTrue("Testing fit on line $lineCount, '$trimmed'.length ${line.length} < $width", trimmed.length <= width - indentSize)
                    } else if (trimmed.length <= width - indentSize) {
                        assertEquals("Testing right padding on line '$line' $lineCount $rightPad", true, rightPad <= actualRightPad)
                    }
                }
                TextAlignment.JUSTIFIED -> {
                    if (trimmed.contains(' ')) {
                        //                        if (rightPad != actualRightPad) {
                        //                            val tmp = 0
                        //                        }
                        assertEquals("Testing right padding on line '$line' $lineCount $rightPad", rightPad, actualRightPad)
                        assertTrue("Testing fit on line $lineCount, '$trimmed'.length ${line.length} < $width", trimmed.length <= width - indentSize)
                    } else if (trimmed.length < width - indentSize) {
                        assertEquals("Testing right padding on line '$line' $lineCount $rightPad", true, rightPad <= actualRightPad)
                    }
                }
            }

            lineCount++
        }
    }

    @Test
    fun test_align() {
        val par = MarkdownParagraph(simplePar)
        assertEquals(simplePar, par.wrapTextNotTracked().toString())

        for (ind in 0..4 step 4) {
            par.indent = ind
            for (fInd in 0..8 step 4) {
                par.firstIndent = fInd
                for (i in 0..50 step 10) {
                    par.width = i
                    if (logOutput || par.indent == 4 && par.firstIndent == 8 && par.width == 50) {
                        println("reformat simplePar to $i, first: ${par.firstIndent} ind: ${par.indent}")
                        println(par.wrapTextNotTracked())
                        println()
                    }
                    if (i > 0) ensureAligned(TextAlignment.LEFT, par.wrapTextNotTracked(), par.firstIndent, par.indent, i, par.keepHardBreaks)
                }
            }
        }
    }

    @Test
    fun test_alignMultiLine() {
        val par = MarkdownParagraph(multiLinePar)
        assertEquals(multiLinePar, par.wrapTextNotTracked().toString())

            for (ind in 0..4 step 4) {
                par.indent = ind
                for (fInd in 0..8 step 4) {
                    par.firstIndent = fInd
                    for (i in 0..50 step 10) {
                        par.width = i
                        if (logOutput || par.indent == 4 && par.firstIndent == 8 && par.width == 50) {
                            println("reformat multiLinePar to $i, first: ${par.firstIndent} ind: ${par.indent}")
                            println(par.wrapTextNotTracked())
                            println()
                        }
                        if (i > 0) ensureAligned(TextAlignment.LEFT, par.wrapTextNotTracked(), par.firstIndent, par.indent, i, par.keepHardBreaks)
                    }
                }
        }
    }

    @Test
    fun test_alignIndentedMultiLine() {
        val par = MarkdownParagraph(indentedMultiLinePar)
        assertEquals(indentedMultiLinePar, par.wrapTextNotTracked().toString())

            for (ind in 0..4 step 4) {
                par.indent = ind
                for (fInd in 0..8 step 4) {
                    par.firstIndent = fInd
                    for (i in 0..50 step 10) {
                        par.width = i
                        if (logOutput || par.indent == 4 && par.firstIndent == 8 && par.width == 50) {
                            println("reformat indentedMultiLinePar to $i, first: ${par.firstIndent} ind: ${par.indent}")
                            println(par.wrapTextNotTracked())
                            println()
                        }
                        if (i > 0) ensureAligned(TextAlignment.LEFT, par.wrapTextNotTracked(), par.firstIndent, par.indent, i, par.keepHardBreaks)
                    }
                }
        }
    }

    //    @Test
    //    fun test_SingleDebug() {
    //        val par = MarkdownParagraph(multiLineHardBreaksPar)
    //        assertEquals(multiLineHardBreaksPar, par.computeResultSequence().toString())
    //
    //        par.alignment = TextAlignment.JUSTIFIED
    //        par.indent = 4
    //        par.firstIndent = 8
    //        par.width = 40
    //        par.isKeepHardBreaks = true
    //        println("reformat to ${par.width}, to ${par.width} align: ${par.alignment} first: ${par.firstIndent} ind: ${par.indent}")
    //        println(par.computeResultSequence())
    //        println()
    //        if (par.width > 0) ensureAligned(par.alignment, par.computeResultSequence(), par.firstIndent, par.indent, par.width, par.isKeepHardBreaks)
    //    }

    @After
    fun tearDown() {
        System.err.println()
        System.err.println("Total iterations $iterations")
        System.err.println()
    }
}

