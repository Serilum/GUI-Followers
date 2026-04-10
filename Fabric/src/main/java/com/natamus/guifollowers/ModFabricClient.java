package com.natamus.guifollowers;

import com.natamus.collective.check.ShouldLoadCheck;
import com.natamus.collective.fabric.callbacks.CollectivePlayerEvents;
import com.natamus.guifollowers.data.Variables;
import com.natamus.guifollowers.events.FollowerEvent;
import com.natamus.guifollowers.util.Reference;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ModFabricClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() { 
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		registerEvents();
	}
	
	private void registerEvents() {
		ModCommon.registerHotkeys();

		ClientTickEvents.END_CLIENT_TICK.register((Minecraft client) -> {
			while (Variables.clearlist_hotkey.isDown()) {
				FollowerEvent.onHotkeyPress();
				Variables.clearlist_hotkey.setDown(false);
			}
			
			FollowerEvent.onPlayerTick();
		});
		
		CollectivePlayerEvents.PLAYER_LOGGED_OUT.register((Level world, Player player) -> {
			FollowerEvent.onPlayerLogout(world, player);
		});
	}
}
