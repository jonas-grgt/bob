package io.jonasg.bob;

/**
 * Container Object for a required field and its value that cannot be set as
 * null
 *
 * @param <T>
 *            the type of the required field its value
 */
@SuppressWarnings("unused")
public final class NotNullableRequiredField<T> implements RequiredField<T> {

	private T fieldValue;

	private final String fieldName;

	private final String typeName;

	NotNullableRequiredField(T fieldValue, String fieldName, String typeName) {
		this.fieldValue = fieldValue;
		this.fieldName = fieldName;
		this.typeName = typeName;
	}

	@Override
	public void set(T value) {
		this.fieldValue = value;
	}

	@Override
	public T orElseThrow() {
		if (fieldValue == null) {
			throw new MandatoryFieldMissingException(fieldName, typeName);
		}
		return fieldValue;
	}
}
