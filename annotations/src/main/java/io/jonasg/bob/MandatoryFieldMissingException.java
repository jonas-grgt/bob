package io.jonasg.bob;

public final class MandatoryFieldMissingException extends RuntimeException {
	public MandatoryFieldMissingException(String fieldName, String typeName) {
		super("Mandatory field (" + fieldName + ") not set when building type (" + typeName + ")");
	}
}
