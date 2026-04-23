package io.jonasg.bob;

import java.util.List;
import java.util.stream.Collectors;

public final class MandatoryFieldsMissingException extends RuntimeException {

	private final List<MissingField> missingFields;

	public MandatoryFieldsMissingException(List<MissingField> missingFields) {
		super(buildMessage(missingFields));
		this.missingFields = List.copyOf(missingFields);
	}

	@SuppressWarnings("unused")
	public List<MissingField> missingFields() {
		return this.missingFields;
	}

	private static String buildMessage(List<MissingField> missingFields) {
		String fieldNames = missingFields.stream()
				.map(MissingField::fieldName)
				.collect(Collectors.joining(", "));
		String typeName = missingFields.isEmpty() ? "unknown" : missingFields.get(0).typeName();
		return "Missing mandatory fields: [" + fieldNames + "] when building type (" + typeName + ")";
	}
}
