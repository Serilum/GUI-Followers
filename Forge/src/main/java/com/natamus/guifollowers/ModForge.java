package com.natamus.guifollowers;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import com.natamus.guifollowers.data.Variables;
import com.natamus.guifollowers.forge.config.IntegrateForgeConfig;
import com.natamus.guifollowers.forge.events.ForgeFollowerEvent;
import com.natamus.guifollowers.forge.events.ForgeGUIEvent;
import com.natamus.guifollowers.forge.events.ForgeKeyMappingRegister;
import com.natamus.guifollowers.util.Reference;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Reference.MOD_ID)
public class ModForge {
	
	public ModForge() {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		if (!FMLEnvironment.dist.equals(Dist.CLIENT)) {
			return;
		}

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		modEventBus.addListener(this::loadComplete);
		modEventBus.register(new ForgeKeyMappingRegister());

		setGlobalConstants();
		ModCommon.init();

		IntegrateForgeConfig.registerScreen(ModLoadingContext.get());

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadComplete(final FMLLoadCompleteEvent event) {
		MinecraftForge.EVENT_BUS.register(new ForgeGUIEvent(Variables.mc, Variables.mc.getItemRenderer()));
    	MinecraftForge.EVENT_BUS.register(new ForgeFollowerEvent());
	}

	private static void setGlobalConstants() {

	}
}