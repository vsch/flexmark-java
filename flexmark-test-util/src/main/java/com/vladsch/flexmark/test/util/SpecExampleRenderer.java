package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;

public interface SpecExampleRenderer {
  boolean includeExampleInfo();

  DataHolder getOptions();

  SpecExample getExample();

  void includeDocument(String includedText);

  void parse(CharSequence input);

  // after all parsing is complete gives a chance to handle insertion of included doc
  void finalizeDocument();

  // after all rendering information is collected, give chance to release resources and reset test
  // settings needed for renderHtml or other functions.
  // after this there will be no more calls to renderer for this iteration
  void finalizeRender();

  // caches values and does not regenerate

  String getHtml();

  String getAst();

  SpecExampleRenderer NULL =
      new SpecExampleRenderer() {
        @Override
        public boolean includeExampleInfo() {
          return false;
        }

        @Override
        public DataHolder getOptions() {
          return null;
        }

        @Override
        public void includeDocument(String includedText) {}

        @Override
        public void parse(CharSequence input) {}

        @Override
        public SpecExample getExample() {
          return SpecExample.NULL;
        }

        @Override
        public void finalizeDocument() {}

        @Override
        public String getHtml() {
          return "";
        }

        @Override
        public String getAst() {
          return null;
        }

        @Override
        public void finalizeRender() {}
      };
}
