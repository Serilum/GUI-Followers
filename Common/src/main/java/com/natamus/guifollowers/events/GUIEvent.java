package com.natamus.guifollowers.events;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.natamus.collective.functions.WorldFunctions;
import com.natamus.guifollowers.config.ConfigHandler;
import com.natamus.guifollowers.data.Variables;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUIEvent {
	private static final Minecraft mc = Minecraft.getInstance();

	public static void renderOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
		if (mc.gui.getDebugOverlay().showDebugScreen()) {
			return;
		}

		Font font = mc.font;
		Window scaled = mc.getWindow();
		PoseStack posestack = guiGraphics.pose();
		posestack.pushPose();
		
		if (Variables.activefollowers.size() > 0) {
			int width = scaled.getGuiScaledWidth();
			
			String displaystring = ConfigHandler.followerListHeaderFormat;
			
			int stringWidth = font.width(displaystring);
			
			Color colour = new Color(ConfigHandler.RGB_R, ConfigHandler.RGB_G, ConfigHandler.RGB_B, 255);
			
			int xcoord;
			int xoffset = 5;
			if (ConfigHandler.followerListPositionIsLeft) {
				xcoord = 5;
			}
			else if (ConfigHandler.followerListPositionIsCenter) {
				xcoord = (width/2) - (stringWidth/2);
			}
			else {
				xcoord = width - stringWidth - 5;
			}
			
			boolean drawnfirst = false;
			int heightoffset = ConfigHandler.followerListHeightOffset;
			
			LocalPlayer player = mc.player;
			String playerdimension = WorldFunctions.getWorldDimensionName(player.level());
			
			List<Entity> toremove = new ArrayList<Entity>();
			for (Entity follower : new ArrayList<Entity>(Variables.activefollowers)) {
				String followerdimension = WorldFunctions.getWorldDimensionName(follower.level());
				if (!playerdimension.equals(followerdimension)) {
					toremove.add(follower);
					continue;
				}

				if (!follower.isAlive() || !(follower instanceof TamableAnimal)) {
					toremove.add(follower);
					continue;
				}

				TamableAnimal te = (TamableAnimal) follower;
				if (te.isInSittingPose()) {
					toremove.add(follower);
					continue;
				}

				String follower_string = follower.getName().getString();
				if (ConfigHandler.showFollowerHealth) {
					LivingEntity le = (LivingEntity) follower;
					float currenthealth = le.getHealth();
					float maxhealth = le.getMaxHealth();

					int percenthealth = (int) ((100 / maxhealth) * currenthealth);
					if (percenthealth <= 0) {
						toremove.add(follower);
						continue;
					}

					String healthformat = ConfigHandler.followerHealthFormat;
					follower_string = follower_string + healthformat.replaceAll("<health>", percenthealth + "");
				}

				if (ConfigHandler.showFollowerDistance) {
					Vec3 pvec = player.position();
					Vec3 fvec = follower.position();

					double distance = pvec.distanceTo(fvec);
					String distanceformat = ConfigHandler.followerDistanceFormat;
					follower_string = follower_string + distanceformat.replaceAll("<distance>", String.format("%.2f", distance));
				}

				int follower_stringWidth = font.width(follower_string);

				if (ConfigHandler.followerListPositionIsCenter) {
					xcoord = (width / 2) - (follower_stringWidth / 2) - xoffset;
				} else if (!ConfigHandler.followerListPositionIsLeft) {
					xcoord = width - follower_stringWidth - 5 - xoffset;
				}

				if (!drawnfirst) {
					drawText(font, guiGraphics, displaystring, xcoord, heightoffset, colour.getRGB(), ConfigHandler.drawTextShadow);
					drawnfirst = true;
				}

				heightoffset += 10;
				drawText(font, guiGraphics, follower_string, xcoord + xoffset, heightoffset, colour.getRGB(), ConfigHandler.drawTextShadow);
			}
			
			if (toremove.size() > 0) {
				for (Entity etr : toremove) {
					Variables.activefollowers.remove(etr);
				}
			}
		}
		
		posestack.popPose();
	}

	private static void drawText(Font font, GuiGraphics guiGraphics, String content, int x, int y, int rgb, boolean drawShadow) {
		guiGraphics.drawString(font, Component.literal(content), x, y, rgb, drawShadow);
	}
}