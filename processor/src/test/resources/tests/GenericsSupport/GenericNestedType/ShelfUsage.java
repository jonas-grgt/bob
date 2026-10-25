package io.jonasg.bob.test;

public class ShelfUsage {
	public static String run() {
		Shelf.Rack.Slot<String> built = new SlotBuilder<String>()
				.item("book")
				.tag(new Shelf.Tag<>("fiction"))
				.build();
		String item = built.getItem();
		String label = built.getTag().getLabel();
		return item + "/" + label;
	}
}
