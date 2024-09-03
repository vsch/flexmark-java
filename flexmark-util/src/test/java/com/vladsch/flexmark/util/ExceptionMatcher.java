package com.vladsch.flexmark.util;

import java.util.regex.Pattern;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jetbrains.annotations.NotNull;

public class ExceptionMatcher extends BaseMatcher<Throwable> {
  private final @NotNull String prefix;
  private final @NotNull Pattern pattern;
  private final @NotNull String message;

  public ExceptionMatcher(
      @NotNull Class<? extends Throwable> throwable,
      @NotNull Pattern pattern,
      @NotNull String message) {
    this.prefix = throwable.getName();
    this.pattern = pattern;
    this.message = message;
  }

  @Override
  public boolean matches(Object o) {
    if (o instanceof RuntimeException) {
      if (o.toString().startsWith(prefix + ": ")) {
        return pattern.matcher(o.toString().substring(prefix.length() + ": ".length())).matches();
      }
    } else if (o instanceof Throwable) {
      if (o.toString().startsWith(prefix)) {
        String input;
        Throwable throwable = (Throwable) o;
        if (throwable.getCause() == null) {
          input = o.toString();
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

  @NotNull
  public static ExceptionMatcher match(
      @NotNull Class<? extends Throwable> throwable, @NotNull String text) {
    return new ExceptionMatcher(throwable, Pattern.compile(Pattern.quote(text)), text);
  }

  @NotNull
  public static ExceptionMatcher matchPrefix(
      @NotNull Class<? extends Throwable> throwable, @NotNull String text) {
    return new ExceptionMatcher(throwable, Pattern.compile(Pattern.quote(text) + "(?s:.*)"), text);
  }

  @NotNull
  public static ExceptionMatcher matchRegEx(
      @NotNull Class<? extends Throwable> throwable, @NotNull String regEx) {
    return new ExceptionMatcher(throwable, Pattern.compile(regEx), regEx);
  }
}
