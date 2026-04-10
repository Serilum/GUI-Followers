package com.natamus.guifollowers.neoforge.events;

import com.natamus.guifollowers.data.Variables;
import com.natamus.guifollowers.events.FollowerEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;

public class NeoForgeFollowerEvent {
	@SubscribeEvent
	public static void onPlayerTick(ClientTickEvent.Post e) {

		FollowerEvent.onPlayerTick();
	}

	@SubscribeEvent
	public static void onPlayerLogout(PlayerLoggedOutEvent e) {
		Player player = e.getEntity();
		FollowerEvent.onPlayerLogout(player.level(), player);
	}

	@SubscribeEvent
	public static void onKey(InputEvent.Key e) {
		if (e.getAction() != 1) {
			return;
		}

		if (Variables.clearlist_hotkey != null && e.getKey() == Variables.clearlist_hotkey.getKey().getValue()) {
			FollowerEvent.onHotkeyPress();
		}
	}
}
