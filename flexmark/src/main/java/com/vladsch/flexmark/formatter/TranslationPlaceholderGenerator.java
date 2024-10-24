package com.vladsch.flexmark.formatter;

public interface TranslationPlaceholderGenerator {
  /**
   * Return a placeholder for given translation span index
   *
   * @param index 1..N, make sure it is unique within this customizer, default format and within
   *     other customizers (huh?)
   * @return string for the placeholder
   */
  String getPlaceholder(int index);
}
