package com.serilum.guifollowers.forge.events;

import com.serilum.guifollowers.events.GUIEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeGUIEvent {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void renderOverlay(RenderGuiEvent.Post e){
		GUIEvent.renderOverlay(e.getGuiGraphics(), e.getPartialTick());
	}
}