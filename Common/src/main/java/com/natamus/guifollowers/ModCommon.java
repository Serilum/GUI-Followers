package com.natamus.guifollowers;

import com.natamus.collective.services.Services;
import com.natamus.guifollowers.config.ConfigHandler;
import com.natamus.guifollowers.data.Variables;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		
	}

	public static void registerHotkeys() {
		Variables.clearlist_hotkey = Services.REGISTERKEYMAPPING.registerKeyMapping("guifollowers.key.clearlist", 92, "key.categories.misc");
	}
}