package com.natamus.guifollowers.forge.events;

import com.natamus.guifollowers.data.Variables;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class ForgeKeyMappingRegister {
    @SubscribeEvent
	public void registerKeyBinding(RegisterKeyMappingsEvent e) {
    	Variables.clearlist_hotkey = new KeyMapping("guifollowers.key.clearlist", 92, "key.categories.misc");
    	e.register(Variables.clearlist_hotkey);
    }
}