package io.jonasg.bob;

import java.util.List;

public class BuilderValidationException extends RuntimeException {
	private final List<String> errors;
	private final List<String> warnings;

	public BuilderValidationException(List<String> errors, List<String> warnings) {
		super(String.join("\n", errors) + (warnings.isEmpty() ? "" : "\n---\n" + String.join("\n", warnings)));
		this.errors = errors;
		this.warnings = warnings;
	}

	public List<String> errors() {
		return errors;
	}

	public List<String> warnings() {
		return warnings;
	}
}
