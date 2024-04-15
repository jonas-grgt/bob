package io.jonasg.bob;

/**
 * Container Object for a required field and its value
 * 
 * @param <T>
 *            the type of the required field its value
 */
@SuppressWarnings("unused")
public final class RequiredField<T> {

	private T fieldValue;

	private final String fieldName;

	private final String typeName;

	private RequiredField(T fieldValue, String fieldName, String typeName) {
		this.fieldValue = fieldValue;
		this.fieldName = fieldName;
		this.typeName = typeName;
	}

	public static <T> RequiredField<T> ofNameWithinType(String fieldName, String typeName) {
		return new RequiredField<>(null, fieldName, typeName);
	}

	public void set(T value) {
		this.fieldValue = value;
	}

	public T orElseThrow() {
		if (fieldValue == null) {
			throw new MandatoryFieldMissingException(fieldName, typeName);
		}
		return fieldValue;
	}
}
