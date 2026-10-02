package com.serilum.guifollowers;

import com.natamus.collective.globalcallbacks.CollectiveGuiCallback;
import com.natamus.collective.services.Services;
import com.serilum.guifollowers.config.ConfigHandler;
import com.serilum.guifollowers.data.Variables;
import com.serilum.guifollowers.events.GUIEvent;
import com.mojang.blaze3d.platform.InputConstants;

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
		Variables.clearlist_hotkey = Services.REGISTERKEYMAPPING.registerKeyMapping("guifollowers.key.clearlist", InputConstants.KEY_BACKSLASH,"key.categories.misc");
	}
}