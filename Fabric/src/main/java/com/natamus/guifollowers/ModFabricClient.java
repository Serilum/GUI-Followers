package com.natamus.guifollowers;

import com.mojang.blaze3d.platform.InputConstants;
import com.natamus.collective.fabric.callbacks.CollectivePlayerEvents;
import com.natamus.guifollowers.data.Variables;
import com.natamus.guifollowers.events.FollowerEvent;
import com.natamus.guifollowers.events.GUIEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ModFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() { 
		registerEvents();
	}
	
	private void registerEvents() {
		Variables.clearlist_hotkey = KeyBindingHelper.registerKeyBinding(new KeyMapping("guifollowers.key.clearlist", InputConstants.Type.KEYSYM, 92, "key.categories.misc"));

		ClientTickEvents.END_CLIENT_TICK.register((Minecraft client) -> {
			while (Variables.clearlist_hotkey.isDown()) {
				FollowerEvent.onHotkeyPress();
				Variables.clearlist_hotkey.setDown(false);
			}
			
			FollowerEvent.onPlayerTick(client);
		});
		
		CollectivePlayerEvents.PLAYER_LOGGED_OUT.register((Level world, Player player) -> {
			FollowerEvent.onPlayerLogout(world, player);
		});
		
		HudRenderCallback.EVENT.register((GuiGraphics guiGraphics, float tickDelta) -> {
			GUIEvent.renderOverlay(guiGraphics, tickDelta);
		});
	}
}
