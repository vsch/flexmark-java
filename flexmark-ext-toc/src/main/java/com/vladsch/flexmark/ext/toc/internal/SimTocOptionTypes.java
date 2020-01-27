package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.options.BooleanOptionParser;
import com.vladsch.flexmark.util.options.MessageProvider;
import com.vladsch.flexmark.util.options.OptionParser;
import com.vladsch.flexmark.util.options.ParsedOption;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;

public enum SimTocOptionTypes implements OptionParser<TocOptions> {
    LEVELS(new TocLevelsOptionParser(Constants.OPTION_LEVELS)),
    HTML(new BooleanOptionParser<TocOptions>(Constants.OPTION_HTML) {
        @Override
        protected boolean isOptionSet(TocOptions options) {
            return options.isHtml;
        }

        @Override
        protected TocOptions setOptions(TocOptions options) {
            return options.withIsHtml(true);
        }
    }),
    MARKDOWN(new BooleanOptionParser<TocOptions>(Constants.OPTION_MARKDOWN) {
        @Override
        protected boolean isOptionSet(TocOptions options) {
            return !options.isHtml;
        }

        @Override
        public TocOptions setOptions(TocOptions options) {
            return options.withIsHtml(false);
        }
    }),
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
    }),
    HIERARCHY(new BooleanOptionParser<TocOptions>(Constants.OPTION_HIERARCHY) {
        @Override
        protected boolean isOptionSet(TocOptions options) {
            return options.listType == TocOptions.ListType.HIERARCHY;
        }

        @Override
        public TocOptions setOptions(TocOptions options) {
            return options.withListType(TocOptions.ListType.HIERARCHY);
        }
    }),
    FLAT(new BooleanOptionParser<TocOptions>(Constants.OPTION_FLAT) {
        @Override
        protected boolean isOptionSet(TocOptions options) {
            return options.listType == TocOptions.ListType.FLAT;
        }

        @Override
        public TocOptions setOptions(TocOptions options) {
            return options.withListType(TocOptions.ListType.FLAT);
        }
    }),
    FLAT_REVERSED(new BooleanOptionParser<TocOptions>(Constants.OPTION_FLAT_REVERSED) {
        @Override
        protected boolean isOptionSet(TocOptions options) {
            return options.listType == TocOptions.ListType.FLAT_REVERSED;
        }

        @Override
        public TocOptions setOptions(TocOptions options) {
            return options.withListType(TocOptions.ListType.FLAT_REVERSED);
        }
    }),
    SORTED(new BooleanOptionParser<TocOptions>(Constants.OPTION_SORTED) {
        @Override
        protected boolean isOptionSet(TocOptions options) {
            return options.listType == TocOptions.ListType.SORTED;
        }

        @Override
        public TocOptions setOptions(TocOptions options) {
            return options.withListType(TocOptions.ListType.SORTED);
        }
    }),
    SORTED_REVERSED(new BooleanOptionParser<TocOptions>(Constants.OPTION_SORTED_REVERSED) {
        @Override
        protected boolean isOptionSet(TocOptions options) {
            return options.listType == TocOptions.ListType.SORTED_REVERSED;
        }

        @Override
        public TocOptions setOptions(TocOptions options) {
            return options.withListType(TocOptions.ListType.SORTED_REVERSED);
        }
    }),
    ;

    final public OptionParser<TocOptions> parser;

    @Override
    public String getOptionName() {return parser.getOptionName();}

    @Override
    public Pair<TocOptions, List<ParsedOption<TocOptions>>> parseOption(BasedSequence optionText, TocOptions options, MessageProvider provider) {return parser.parseOption(optionText, options, provider);}

    @Override
    public String getOptionText(TocOptions options, TocOptions defaultOptions) {return parser.getOptionText(options, defaultOptions);}

    SimTocOptionTypes(OptionParser<TocOptions> parser) {
        this.parser = parser;
    }

    final public static OptionParser<TocOptions>[] OPTIONS = SimTocOptionTypes.values();

    private static class Constants {
        static final String OPTION_BULLET = "bullet";
        static final String OPTION_NUMBERED = "numbered";
        static final String OPTION_MARKDOWN = "markdown";
        static final String OPTION_HTML = "html";
        static final String OPTION_TEXT = "text";
        static final String OPTION_FORMATTED = "formatted";
        static final String OPTION_HIERARCHY = "hierarchy";
        static final String OPTION_FLAT = "flat";
        static final String OPTION_FLAT_REVERSED = "reversed";
        static final String OPTION_SORTED = "increasing";
        static final String OPTION_SORTED_REVERSED = "decreasing";
        static final String OPTION_LEVELS = "levels";
    }
}
