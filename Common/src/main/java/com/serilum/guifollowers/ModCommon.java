package com.serilum.guifollowers;

import com.natamus.collective.services.Services;
import com.serilum.guifollowers.config.ConfigHandler;
import com.serilum.guifollowers.data.Variables;
import com.serilum.guifollowers.util.Util;
import com.mojang.blaze3d.platform.InputConstants;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		if (Services.MODLOADER.isClientSide()) {
			Util.loadHiddenFollowers();
		}
	}

	public static void registerHotkeys() {
		Variables.clearlist_hotkey = Services.REGISTERKEYMAPPING.registerKeyMapping("collective.guifollowers.key.clearlist", InputConstants.KEY_BACKSLASH,"key.categories.misc");
	}
}