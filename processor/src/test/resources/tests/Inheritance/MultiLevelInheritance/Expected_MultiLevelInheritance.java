package io.jonasg.bob.test;

import java.lang.String;
import javax.annotation.processing.Generated;

@Generated("io.jonasg.bob.BuildableProcessor")
public final class MultiLevelInheritanceBuilder {
  private String grandparentField;

  private String parentField;

  private String childField;

  public MultiLevelInheritanceBuilder() {
  }

  public MultiLevelInheritanceBuilder grandparentField(String grandparentField) {
    this.grandparentField = grandparentField;
    return this;
  }

  public MultiLevelInheritanceBuilder parentField(String parentField) {
    this.parentField = parentField;
    return this;
  }

  public MultiLevelInheritanceBuilder childField(String childField) {
    this.childField = childField;
    return this;
  }

  public MultiLevelInheritance build() {
    return new MultiLevelInheritance(grandparentField, parentField, childField);
  }
}
