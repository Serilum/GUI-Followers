package com.natamus.guifollowers.forge.events;

import com.natamus.guifollowers.events.GUIEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeGUIEvent extends Gui {
	public ForgeGUIEvent(Minecraft mc, ItemRenderer itemRenderer){
		super(mc, itemRenderer);
	}
	
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void renderOverlay(RenderGuiOverlayEvent.Pre e){
		GUIEvent.renderOverlay(e.getPoseStack(), e.getPartialTick());
	}
}