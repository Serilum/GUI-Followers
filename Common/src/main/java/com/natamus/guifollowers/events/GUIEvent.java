package com.natamus.guifollowers.events;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.natamus.collective.functions.WorldFunctions;
import com.natamus.guifollowers.config.ConfigHandler;
import com.natamus.guifollowers.data.Variables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUIEvent {
	private static final Minecraft mc = Minecraft.getInstance();

	public static void renderOverlay(PoseStack posestack, float tickDelta) {
		if (mc.options.renderDebug) {
			return;
		}

		Font fontRenderer = mc.font;
		Window scaled = mc.getWindow();
		posestack.pushPose();
		
		if (Variables.activefollowers.size() > 0) {
			int width = scaled.getGuiScaledWidth();
			
			String displaystring = ConfigHandler.followerListHeaderFormat;
			
			int stringWidth = fontRenderer.width(displaystring);
			
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
			String playerdimension = WorldFunctions.getWorldDimensionName(player.getCommandSenderWorld());
			
			List<Entity> toremove = new ArrayList<Entity>();
			for (Entity follower : new ArrayList<Entity>(Variables.activefollowers)) {
				String followerdimension = WorldFunctions.getWorldDimensionName(follower.getCommandSenderWorld());
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

				int follower_stringWidth = fontRenderer.width(follower_string);

				if (ConfigHandler.followerListPositionIsCenter) {
					xcoord = (width / 2) - (follower_stringWidth / 2) - xoffset;
				} else if (!ConfigHandler.followerListPositionIsLeft) {
					xcoord = width - follower_stringWidth - 5 - xoffset;
				}

				if (!drawnfirst) {
					drawText(fontRenderer, posestack, displaystring, xcoord, heightoffset, colour.getRGB(), ConfigHandler.drawTextShadow);
					drawnfirst = true;
				}

				heightoffset += 10;
				drawText(fontRenderer, posestack, follower_string, xcoord + xoffset, heightoffset, colour.getRGB(), ConfigHandler.drawTextShadow);
			}
			
			if (toremove.size() > 0) {
				for (Entity etr : toremove) {
					Variables.activefollowers.remove(etr);
				}
			}
		}
		
		posestack.popPose();
	}

	private static void drawText(Font fontRenderer, PoseStack poseStack, String content, float x, float y, int rgb, boolean drawShadow) {
		if (drawShadow) {
			fontRenderer.drawShadow(poseStack, content, x, y, rgb);
			return;
		}
		fontRenderer.draw(poseStack, content, x, y, rgb);
	}
}