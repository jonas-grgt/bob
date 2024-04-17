package io.jonasg.bob;

public interface RequiredField<T> {

	static <T> RequiredField<T> notNullableOfNameWithinType(String fieldName, String typeName) {
		return new NotNullableRequiredField<>(null, fieldName, typeName);
	}

	static <T> RequiredField<T> nullableOfNameWithinType(String fieldName, String typeName) {
		return new NullableRequiredField<>(null, fieldName, typeName);
	}

	void set(T value);

	T orElseThrow();

}
