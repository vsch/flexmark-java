package com.vladsch.flexmark.test.util.spec;

import java.util.function.Function;

/** Used to resolve test resource URL from copy in test location to URL of source file */
public interface ResourceUrlResolver extends Function<String, String> {
  @Override
  String apply(String externalForm);

  String FILE_PROTOCOL = "file://";

  static boolean isFileProtocol(String externalForm) {
    return externalForm.startsWith("file:/");
  }

  static boolean hasProtocol(String externalForm) {
    int pos = externalForm.indexOf(":");
    // allow windows drive letter to not be treated as protocol
    return pos > 1;
  }

  static String removeProtocol(String externalForm) {
    int pos = externalForm.indexOf(':');
    if (pos > 0) {
      return externalForm.substring(pos + 1);
    }
    return externalForm;
  }
}
