package io.jonasg.bob.test;

import java.lang.String;
import io.jonasg.bob.Buildable;

@Buildable
public class GenericsAreBuildable<T, R extends String> {

	private T contents;
	private R topping;

	public GenericsAreBuildable(T contents, R topping) {
		this.contents = contents;
		this.topping = topping;
	}
}
