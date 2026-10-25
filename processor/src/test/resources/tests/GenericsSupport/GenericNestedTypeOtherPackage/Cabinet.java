package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

public class Cabinet {

	@Buildable(packageName = "io.jonasg.bob.test.other")
	public static class Drawer<T> {
		private T content;

		public Drawer(T content) {
			this.content = content;
		}

		public T getContent() {
			return content;
		}
	}
}
