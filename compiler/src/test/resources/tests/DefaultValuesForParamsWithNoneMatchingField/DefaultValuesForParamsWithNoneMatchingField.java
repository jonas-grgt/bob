package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

import java.math.BigDecimal;

@Buildable
public class DefaultValuesForParamsWithNoneMatchingField {

	public DefaultValuesForParamsWithNoneMatchingField(String string,
			int primitiveInt,
			Integer boxedInteger,
			double primitiveDouble,
			Double boxedDouble,
			boolean primitiveBoolean,
			Boolean boxedBoolean,
			float primitiveFloat,
			Float boxedFloat,
			long primitiveLong,
			Long boxedLong,
			BigDecimal bigDecimal) {
	}
}
