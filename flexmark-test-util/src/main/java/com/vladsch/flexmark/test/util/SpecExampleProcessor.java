package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;

public interface SpecExampleProcessor {
  /**
   * Customize options for an example
   *
   * @param option name of the options set to use
   * @return options or null to use default
   */
  DataHolder options(String option);

  /**
   * Allows tests to modify example during reading (DumpSpecReader)
   *
   * @param example example as it is in the test or spec file
   * @return modified example if needed
   */
  default SpecExample checkExample(SpecExample example) {
    return example;
  }

  /**
   * Get spec renderer for an example spec
   *
   * @param example spec example
   * @param exampleOptions example custom options
   * @return spec renderer for given example and options
   */
  SpecExampleRenderer getSpecExampleRenderer(SpecExample example, DataHolder exampleOptions);

  /**
   * Called by DumpSpecReader for each example when processing full test spec
   *
   * @param exampleRenderer example renderer
   * @param exampleParse example parse state
   * @param exampleOptions example options
   * @param ignoredTestCase true if ignored example
   * @param html html used for comparison to expected html
   * @param ast ast used for comparison to expected ast
   */
  void addFullSpecExample(
      SpecExampleRenderer exampleRenderer,
      SpecExampleParse exampleParse,
      DataHolder exampleOptions,
      boolean ignoredTestCase,
      String html,
      String ast);
}
