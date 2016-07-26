/*
 * Copyright (c) 2015-2016 Vladimir Schneider <vladimir.schneider@gmail.com>, all rights reserved.
 *
 * This code is private property of the copyright holder and cannot be used without
 * having obtained a license or prior written permission of the of the copyright holder.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ext.toc.SimTocExtension;
import com.vladsch.flexmark.internal.util.options.DataHolder;

public class SimTocOptions extends TocOptions {
    final public boolean isAstAddOptions;
    final public boolean isBlankLineSpacer;

    public SimTocOptions() {
        this(DEFAULT_LEVELS, false, false, false, DEFAULT_TITLE_LEVEL, DEFAULT_TITLE, false, true);
    }

    public SimTocOptions(DataHolder options) {
        super(options);
        isAstAddOptions = options.get(SimTocExtension.AST_INCLUDE_OPTIONS);
        isBlankLineSpacer = options.get(SimTocExtension.BLANK_LINE_SPACER);
    }

    public SimTocOptions(int levels, boolean isHtml, boolean isTextOnly, boolean isNumbered, int titleLevel, String title, boolean isAstAddOptions, boolean isBlankLineSpacer) {
        super(levels, isHtml, isTextOnly, isNumbered, titleLevel, title);
        this.isAstAddOptions = isAstAddOptions;
        this.isBlankLineSpacer = isBlankLineSpacer;
    }

    public boolean isLevelIncluded(int level) {
        return level >= 1 && level <= 6 && (levels & 1 << level) != 0;
    }

    // @Formatter:off
    @Override public SimTocOptions withLevels(int levels)                   { return new SimTocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title, isAstAddOptions, isBlankLineSpacer); }
    @Override public SimTocOptions withIsHtml(boolean isHtml)               { return new SimTocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title, isAstAddOptions, isBlankLineSpacer); }
    @Override public SimTocOptions withIsTextOnly(boolean isTextOnly)       { return new SimTocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title, isAstAddOptions, isBlankLineSpacer); }
    @Override public SimTocOptions withIsNumbered(boolean isNumbered)       { return new SimTocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title, isAstAddOptions, isBlankLineSpacer); }
    @Override public SimTocOptions withTitleLevel(int titleLevel)           { return new SimTocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title, isAstAddOptions, isBlankLineSpacer); }
    @Override public SimTocOptions withTitle( String title)                 { return new SimTocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title, isAstAddOptions, isBlankLineSpacer); }
    @Override public SimTocOptions withRawTitleLevel(int titleLevel)        { return new SimTocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title, isAstAddOptions, isBlankLineSpacer); }
    @Override public SimTocOptions withRawTitle( String title)              { return new SimTocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title, isAstAddOptions, isBlankLineSpacer); }
    public SimTocOptions withIsAstAddOptions(boolean isAstAddOptions)       { return new SimTocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title, isAstAddOptions, isBlankLineSpacer); }
    public SimTocOptions withIsBlankLineSpacer(boolean isBlankLineSpacer)   { return new SimTocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title, isAstAddOptions, isBlankLineSpacer); }
    // @Formatter:on

    public SimTocOptions withLevelList(int... levelList) {
        int levels = 0;
        for (int level : levelList) {
            if (level < 1 || level > 6) throw new IllegalArgumentException("TocOption level out of range [1, 6]");
            levels |= 1 << level;
        }
        return new SimTocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title, isAstAddOptions, isBlankLineSpacer);
    }
}


