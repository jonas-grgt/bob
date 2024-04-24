package io.jonasg.bob;

public interface ValidatableField<T> {

	static <T> ValidatableField<T> ofNoneNullableField(String fieldName, String typeName) {
		return new NoneNullableValidatableField<>(null, fieldName, typeName);
	}

	static <T> ValidatableField<T> ofNullableField(String fieldName, String typeName) {
		return new NullableValidatableField<>(null, fieldName, typeName);
	}

	void set(T value);

	T orElseThrow();

}
