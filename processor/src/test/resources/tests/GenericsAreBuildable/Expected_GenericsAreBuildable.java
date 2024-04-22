package io.jonasg.bob.test;

import java.lang.Class;
import java.lang.String;

public final class GenericsAreBuildableBuilder<T, R extends String> {
  private T contents;

  private R topping;

  public GenericsAreBuildableBuilder() {
  }

  public GenericsAreBuildableBuilder<T, R> contents(T contents) {
    this.contents = contents;
    return this;
  }

  public GenericsAreBuildableBuilder<T, R> topping(R topping) {
    this.topping = topping;
    return this;
  }

  public GenericsAreBuildable<T, R> build() {
    return new GenericsAreBuildable<T, R>(contents, topping);
  }

  public static <T, R extends String> GenericsAreBuildableBuilder<T, R> of(Class<T> Ttype,
      Class<R> Rtype) {
    return new GenericsAreBuildableBuilder<>();
  }
}
