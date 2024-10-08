package com.vladsch.flexmark.util.sequence;

public enum Options {
  CONVERT_TABS, // expand tabs on column multiples of 4
  COLLAPSE_WHITESPACE, // collapse multiple tabs and spaces to single space, implies CONVERT_TABS
  TRIM_TRAILING_WHITESPACE, // don't output trailing whitespace
  PASS_THROUGH, // just pass everything through to appendable with no formatting
  TRIM_LEADING_WHITESPACE, // allow leading spaces on a line, else remove
  TRIM_LEADING_EOL, // allow EOL at offset 0
  PREFIX_PRE_FORMATTED, // when prefixing lines, prefix pre-formatted lines
}
