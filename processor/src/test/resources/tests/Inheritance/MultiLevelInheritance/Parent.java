package io.jonasg.bob.test;

public class Parent extends Grandparent {
	private String parentField;

	public Parent(String grandparentField, String parentField) {
		super(grandparentField);
		this.parentField = parentField;
	}
}
