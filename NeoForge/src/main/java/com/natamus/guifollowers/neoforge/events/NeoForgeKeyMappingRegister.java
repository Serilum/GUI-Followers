package com.natamus.guifollowers.neoforge.events;

import com.natamus.guifollowers.data.Variables;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class NeoForgeKeyMappingRegister {
	@SubscribeEvent
	public static void registerKeyBinding(RegisterKeyMappingsEvent e) {
		Variables.clearlist_hotkey = new KeyMapping("guifollowers.key.clearlist", 92, "key.categories.misc");
		e.register(Variables.clearlist_hotkey);
	}
}