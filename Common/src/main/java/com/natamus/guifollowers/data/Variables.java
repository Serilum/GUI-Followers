package com.natamus.guifollowers.data;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Variables {
	public static final Minecraft mc = Minecraft.getInstance();
	public static KeyMapping clearlist_hotkey;

	public static List<Entity> activefollowers = new ArrayList<Entity>();
}
