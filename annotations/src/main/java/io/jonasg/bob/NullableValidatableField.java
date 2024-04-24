package io.jonasg.bob;

/**
 * Container Object for a required field and its value that can be set as null
 *
 * @param <T>
 *            the type of the required field its value
 */
@SuppressWarnings("unused")
public class NullableValidatableField<T> implements ValidatableField<T> {

	private T fieldValue;

	private boolean fieldSet;

	private final String fieldName;

	private final String typeName;

	public NullableValidatableField(T fieldValue, String fieldName, String typeName) {
		this.fieldValue = fieldValue;
		this.fieldName = fieldName;
		this.typeName = typeName;
	}

	@Override
	public void set(T value) {
		this.fieldValue = value;
		this.fieldSet = true;
	}

	@Override
	public T orElseThrow() {
		if (!this.fieldSet && this.fieldValue == null) {
			throw new MandatoryFieldMissingException(this.fieldName, this.typeName);
		}
		return this.fieldValue;
	}
}
