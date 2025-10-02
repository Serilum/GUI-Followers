package com.natamus.guifollowers;

import com.natamus.collective.globalcallbacks.CollectiveGuiCallback;
import com.natamus.collective.services.Services;
import com.natamus.guifollowers.config.ConfigHandler;
import com.natamus.guifollowers.data.Variables;
import com.natamus.guifollowers.events.GUIEvent;

public class ModCommon {

	public static void init() {
		ConfigHandler.initConfig();
		load();
	}

	private static void load() {
		if (Services.MODLOADER.isClientSide()) {
			CollectiveGuiCallback.ON_GUI_RENDER.register(((guiGraphics, deltaTracker) -> {
				GUIEvent.renderOverlay(guiGraphics, deltaTracker);
			}));
		}
	}

	public static void registerHotkeys() {
		Variables.clearlist_hotkey = Services.REGISTERKEYMAPPING.registerKeyMapping("guifollowers.key.clearlist", 92, "key.categories.misc");
	}
}