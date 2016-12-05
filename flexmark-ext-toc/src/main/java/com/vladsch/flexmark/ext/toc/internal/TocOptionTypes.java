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

import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.options.BooleanOptionParser;
import com.vladsch.flexmark.util.options.MessageProvider;
import com.vladsch.flexmark.util.options.OptionParser;
import com.vladsch.flexmark.util.options.ParsedOption;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;

public enum TocOptionTypes implements OptionParser<TocOptions> {
    LEVELS(new TocLevelsOptionParser(Constants.OPTION_LEVELS)),
    BULLETS(new BooleanOptionParser<TocOptions>(Constants.OPTION_BULLET) {
        @Override
        protected boolean isOptionSet(TocOptions options) {
            return !options.isNumbered;
        }

        @Override
        public TocOptions setOptions(TocOptions options) {
            return options.withIsNumbered(false);
        }
    }),
    NUMERIC(new BooleanOptionParser<TocOptions>(Constants.OPTION_NUMBERED) {
        @Override
        protected boolean isOptionSet(TocOptions options) {
            return options.isNumbered;
        }

        @Override
        public TocOptions setOptions(TocOptions options) {
            return options.withIsNumbered(true);
        }
    }),
    TEXT(new BooleanOptionParser<TocOptions>(Constants.OPTION_TEXT) {
        @Override
        protected boolean isOptionSet(TocOptions options) {
            return options.isTextOnly;
        }

        @Override
        public TocOptions setOptions(TocOptions options) {
            return options.withIsTextOnly(true);
        }
    }),
    FORMATTED(new BooleanOptionParser<TocOptions>(Constants.OPTION_FORMATTED) {
        @Override
        protected boolean isOptionSet(TocOptions options) {
            return !options.isTextOnly;
        }

        @Override
        public TocOptions setOptions(TocOptions options) {
            return options.withIsTextOnly(false);
        }
    })
    ;

    public final OptionParser<TocOptions> parser;

    @Override
    public String getOptionName() {return parser.getOptionName();}

    @Override
    public Pair<TocOptions, List<ParsedOption<TocOptions>>> parseOption(BasedSequence optionText, TocOptions options, MessageProvider provider) {return parser.parseOption(optionText, options, provider);}

    @Override
    public String getOptionText(TocOptions options, TocOptions defaultOptions) {return parser.getOptionText(options, defaultOptions);}

    TocOptionTypes(OptionParser<TocOptions> parser) {
        this.parser = parser;
    }

    public static final OptionParser<TocOptions>[] OPTIONS = TocOptionTypes.values();

    private static class Constants {
        public static final String OPTION_BULLET = "bullet";
        public static final String OPTION_NUMBERED = "numbered";
        public static final String OPTION_TEXT = "text";
        public static final String OPTION_FORMATTED = "formatted";
        public static final String OPTION_LEVELS = "levels";
    }
}
