package com.vladsch.flexmark.test.util;

import java.util.regex.Pattern;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class ExceptionMatcher extends BaseMatcher<Throwable> {
  private final String prefix;
  private final Pattern pattern;
  private final String message;

  public ExceptionMatcher(Class<? extends Throwable> throwable, Pattern pattern, String message) {
    this.prefix = throwable.getName();
    this.pattern = pattern;
    this.message = message;
  }

  @Override
  public boolean matches(Object object) {
    if (object instanceof RuntimeException) {
      if (object.toString().startsWith(prefix + ": ")) {
        return pattern
            .matcher(object.toString().substring(prefix.length() + ": ".length()))
            .matches();
      }
    } else if (object instanceof Throwable) {
      if (object.toString().startsWith(prefix)) {
        String input;
        Throwable throwable = (Throwable) object;
        if (throwable.getCause() == null) {
          input = object.toString();
        } else {
          input = throwable.getCause().toString();
        }
        return pattern.matcher(input).matches();
      }
    }
    return false;
  }

  @Override
  public void describeTo(Description description) {
    description.appendText(prefix + ": " + message);
  }

  public static ExceptionMatcher match(Class<? extends Throwable> throwable, String text) {
    return new ExceptionMatcher(throwable, Pattern.compile(Pattern.quote(text)), text);
  }

  public static ExceptionMatcher matchPrefix(Class<? extends Throwable> throwable, String text) {
    return new ExceptionMatcher(throwable, Pattern.compile(Pattern.quote(text) + "(?s:.*)"), text);
  }

  public static ExceptionMatcher matchRegEx(Class<? extends Throwable> throwable, String regEx) {
    return new ExceptionMatcher(throwable, Pattern.compile(regEx), regEx);
  }
}
