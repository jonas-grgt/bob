package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

@Buildable
public class MultiLevelInheritance extends Parent {
	private String childField;

	public MultiLevelInheritance(String grandparentField, String parentField, String childField) {
		super(grandparentField, parentField);
		this.childField = childField;
	}
}
