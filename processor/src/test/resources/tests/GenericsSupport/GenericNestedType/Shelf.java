package io.jonasg.bob.test;

import io.jonasg.bob.Buildable;

public class Shelf {

	public static class Tag<T> {
		private final T label;

		public Tag(T label) {
			this.label = label;
		}

		public T getLabel() {
			return label;
		}
	}

	public static class Rack {

		@Buildable
		public static class Slot<T> {
			private T item;

			private Shelf.Tag<T> tag;

			public Slot(T item, Shelf.Tag<T> tag) {
				this.item = item;
				this.tag = tag;
			}

			public T getItem() {
				return item;
			}

			public Shelf.Tag<T> getTag() {
				return tag;
			}
		}
	}
}
