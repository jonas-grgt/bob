package io.jonasg.bob.test;

import io.jonasg.bob.test.other.DrawerBuilder;

public class CabinetUsage {
	public static String run() {
		Cabinet.Drawer<String> built = new DrawerBuilder<String>().content("socks").build();
		String content = built.getContent();
		return content;
	}
}
