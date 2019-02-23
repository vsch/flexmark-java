package com.vladsch.flexmark.test;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

import java.util.concurrent.TimeUnit;

/**
 * Pathological input cases (from commonmark.js).
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PathologicalTest extends CoreRenderingTestCase {

    private int x = 100000;

    @Rule
    public Timeout timeout = new Timeout(3, TimeUnit.SECONDS);

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            System.err.println(description.getDisplayName() + " took " + (nanos / 1000000) + " ms");
        }
    };

    @Test
    public void nestedStrongEmphasis() {
        // this is limited by the stack size because visitor is recursive
        x = 500;
        assertRendering(
                Strings.repeat("*a **a ", x) + "b" + Strings.repeat(" a** a*", x), "<p>" + Strings.repeat("<em>a <strong>a ", x) + "b" +
                        Strings.repeat(" a</strong> a</em>", x) + "</p>\n");
    }

    @Test
    public void emphasisClosersWithNoOpeners() {
        assertRendering(
                Strings.repeat("a_ ", x), "<p>" + Strings.repeat("a_ ", x - 1) + "a_</p>\n");
    }

    @Test
    public void emphasisOpenersWithNoClosers() {
        assertRendering(
                Strings.repeat("_a ", x), "<p>" + Strings.repeat("_a ", x - 1) + "_a</p>\n");
    }

    @Test
    public void linkClosersWithNoOpeners() {
        assertRendering(
                Strings.repeat("a] ", x), "<p>" + Strings.repeat("a] ", x - 1) + "a]</p>\n");
    }

    @Test
    public void linkOpenersWithNoClosers() {
        assertRendering(
                Strings.repeat("[a ", x), "<p>" + Strings.repeat("[a ", x - 1) + "[a</p>\n");
    }

    @Test
    public void linkOpenersAndEmphasisClosers() {
        assertRendering(
                Strings.repeat("[ a_ ", x), "<p>" + Strings.repeat("[ a_ ", x - 1) + "[ a_</p>\n");
    }

    @Test
    public void mismatchedOpenersAndClosers() {
        assertRendering(
                Strings.repeat("*a_ ", x), "<p>" + Strings.repeat("*a_ ", x - 1) + "*a_</p>\n");
    }

    @Test
    public void nestedBrackets() {
        assertRendering(
                Strings.repeat("[", x) + "a" + Strings.repeat("]", x), "<p>" + Strings.repeat("[", x) + "a" + Strings.repeat("]", x) + "</p>\n");
    }

    @Test
    public void nestedBlockQuotes() {
        // this is limited by the stack size because visitor is recursive
        x = 500;
        assertRendering(
                Strings.repeat("> ", x) + "a\n", Strings.repeat("<blockquote>\n", x) + "<p>a</p>\n" +
                        Strings.repeat("</blockquote>\n", x));
    }
}
