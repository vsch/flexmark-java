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
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DelimitedBuilder;
import com.vladsch.flexmark.util.sequence.SubSequence;

public class TocOptions {
    public static final TocOptions DEFAULT = new TocOptions();
    public static final int DEFAULT_LEVELS = 4 | 8; // 0 not used, default H2 & H3, H1 assumed to be document heading and does not need to be part of TOC
    public static final String DEFAULT_TITLE = "Table of Contents";
    public static final int DEFAULT_TITLE_LEVEL = 1;
    public static final int VALID_LEVELS = 0x7e;

    public final int levels;
    public final boolean isHtml;
    public final boolean isTextOnly;
    public final boolean isNumbered;
    public final int titleLevel;
    public final String title;
    public final int rawTitleLevel;
    public final String rawTitle;

    public TocOptions() {
        this(DEFAULT_LEVELS, false, false, false, DEFAULT_TITLE_LEVEL, DEFAULT_TITLE);
    }

    public TocOptions(DataHolder options) {
        this(
                options.get(SimTocExtension.LEVELS),
                options.get(SimTocExtension.IS_HTML),
                options.get(SimTocExtension.IS_TEXT_ONLY),
                options.get(SimTocExtension.IS_NUMBERED),
                options.get(SimTocExtension.TITLE_LEVEL),
                options.get(SimTocExtension.TITLE)
        );
    }

    public TocOptions(int levels, boolean isHtml, boolean isTextOnly, boolean isNumbered, int titleLevel, String title) {
        this.levels = levels & VALID_LEVELS;
        this.isHtml = isHtml;
        this.isTextOnly = isTextOnly;
        this.isNumbered = isNumbered;
        this.rawTitle = title == null ? "" : title;
        this.rawTitleLevel = titleLevel;
        if (!rawTitle.isEmpty()) {
            int markers = SubSequence.of(rawTitle.trim()).countLeading("#");
            if (markers >= 1 && markers <= 6) titleLevel = markers;
            this.title = rawTitle.substring(markers).trim();
        } else {
            this.title = "";
        }
        this.titleLevel = titleLevel <= 1 ? 1 : titleLevel >= 6 ? 6 : titleLevel;
    }

    public boolean isLevelIncluded(int level) {
        return level >= 1 && level <= 6 && (levels & 1 << level) != 0;
    }

    // @Formatter:off
    public TocOptions withLevels(int levels)                { return new TocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title); }
    public TocOptions withIsHtml(boolean isHtml)            { return new TocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title); }
    public TocOptions withIsTextOnly(boolean isTextOnly)    { return new TocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title); }
    public TocOptions withIsNumbered(boolean isNumbered)    { return new TocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title); }
    public TocOptions withTitleLevel(int titleLevel)        { return new TocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title); }
    public TocOptions withTitle( String title)              { return new TocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title); }
    public TocOptions withRawTitleLevel(int titleLevel)     { return new TocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title); }
    public TocOptions withRawTitle( String title)           { return new TocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title); }
    // @Formatter:on

    public TocOptions withLevelList(int... levelList) {
        int levels = getLevels(levelList);
        return new TocOptions(levels, isHtml, isTextOnly, isNumbered, titleLevel, title);
    }

    public static int getLevels(int... levelList) {
        int levels = 0;
        for (int level : levelList) {
            if (level < 1 || level > 6) throw new IllegalArgumentException("TocOption level out of range [1, 6]");
            levels |= 1 << level;
        }
        return levels;
    }

    public String getTitleHeading() {
        String title = this.title;

        if (!title.isEmpty()) {
            StringBuilder out = new StringBuilder();

            int level = titleLevel;
            while (level-- > 0) out.append('#');
            out.append(' ');
            out.append(title);
            return out.toString();
        }
        return "";
    }

    @Override
    public String toString() {
        DelimitedBuilder out = new DelimitedBuilder(", ");
        out.append("TocOptions[");

        out.append("levels=<").push(",");
        for (int i=1;i<=6;i++) {
            if (isLevelIncluded(i)) out.append(i).mark();
        }
        out.pop().unmark().append('>').mark();

        out.append(" isHtml=").append(isHtml).mark();
        out.append(" isTextOnly=").append(isTextOnly).mark();
        out.append(" isNumbered=").append(isNumbered).mark();
        out.append(" title=").append('"').append(title).append('"').mark();
        out.append(" titleLevel=").append(titleLevel).mark();
        out.append(" rawTitleLevel=").append(rawTitleLevel).mark();
        out.append(" rawTitle=").append('"').append(rawTitle).append('"').mark();
        out.append("]");

        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TocOptions)) return false;

        TocOptions options = (TocOptions) o;

        if (levels != options.levels) return false;
        if (isHtml != options.isHtml) return false;
        if (isTextOnly != options.isTextOnly) return false;
        if (isNumbered != options.isNumbered) return false;
        if (titleLevel != options.titleLevel) return false;
        if (rawTitleLevel != options.rawTitleLevel) return false;
        if (!title.equals(options.title)) return false;
        return rawTitle.equals(options.rawTitle);
    }

    @Override
    public int hashCode() {
        int result = levels;
        result = 31 * result + (isHtml ? 1 : 0);
        result = 31 * result + (isTextOnly ? 1 : 0);
        result = 31 * result + (isNumbered ? 1 : 0);
        result = 31 * result + titleLevel;
        result = 31 * result + title.hashCode();
        result = 31 * result + rawTitleLevel;
        result = 31 * result + rawTitle.hashCode();
        return result;
    }
}


