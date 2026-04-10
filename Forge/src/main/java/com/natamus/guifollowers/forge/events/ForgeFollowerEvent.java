package com.natamus.guifollowers.forge.events;

import com.natamus.guifollowers.data.Variables;
import com.natamus.guifollowers.events.FollowerEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgeFollowerEvent {
	public static void registerEventsInBus() {
		BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeFollowerEvent.class);
	}

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
