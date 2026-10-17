package com.serilum.guifollowers.events;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.natamus.collective.functions.ColourFunctions;
import com.natamus.collective.functions.GUIFunctions;
import com.serilum.guifollowers.config.ConfigHandler;
import com.serilum.guifollowers.data.Variables;
import com.serilum.guifollowers.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUIEvent {
	private static final Minecraft mc = Minecraft.getInstance();

	public static void renderOverlay(GuiGraphics guiGraphics, float tickDelta) {
		if (GUIFunctions.shouldHideGUI()) {
			return;
		}

		if (!ConfigHandler.showFollowerList) {
			return;
		}

		Font font = mc.font;
		Window scaled = mc.getWindow();
		float textScale = (float)ConfigHandler.followerListTextScale;

		PoseStack posestack = guiGraphics.pose();
		posestack.pushPose();
		posestack.scale(textScale, textScale, 1.0F);

		if (Variables.activefollowers.size() > 0) {
			int width = (int)(scaled.getGuiScaledWidth() / textScale);

			String displaystring = ConfigHandler.followerListHeaderFormat;

			int stringWidth = font.width(displaystring);

			Color colour = new Color(ColourFunctions.getRgbColour(ConfigHandler.RGB));

			int xcoord;
			int xoffset = 5;
			int widthoffset = (int)(ConfigHandler.followerListWidthOffset / textScale);
			if (ConfigHandler.followerListPosition.equals("Left")) {
				xcoord = 5;
			}
			else if (ConfigHandler.followerListPosition.equals("Center")) {
				xcoord = (width/2) - (stringWidth/2);
			}
			else {
				xcoord = width - stringWidth - 5;
			}

			boolean drawnfirst = false;
			int heightoffset = (int)(ConfigHandler.followerListHeightOffset / textScale);

			LocalPlayer player = mc.player;
			Level playerLevel = player.level();

			List<Entity> toremove = new ArrayList<Entity>();
			for (Entity follower : new ArrayList<Entity>(Variables.activefollowers)) {
				if (follower.level() != playerLevel) {
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

				if (Util.isHidden(te) || Util.isIgnoredEntityType(te)) {
					toremove.add(follower);
					continue;
				}

				LivingEntity le = (LivingEntity) follower;
				float currenthealth = le.getHealth();
				float maxhealth = le.getMaxHealth();

				int percenthealth = (int) ((100 / maxhealth) * currenthealth);

				String follower_string = follower.getName().getString();
				if (ConfigHandler.showFollowerHealth) {
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

				int followerColour = colour.getRGB();
				if (ConfigHandler.colourFollowerByHealth) {
					followerColour = Util.getHealthColour(percenthealth, followerColour);
				}

				int follower_stringWidth = font.width(follower_string);

				if (ConfigHandler.followerListPosition.equals("Center")) {
					xcoord = (width / 2) - (follower_stringWidth / 2) - xoffset;
				}
				else if (!ConfigHandler.followerListPosition.equals("Left")) {
					xcoord = width - follower_stringWidth - 5 - xoffset;
				}

				if (!drawnfirst) {
					drawText(font, guiGraphics, displaystring, xcoord + widthoffset, heightoffset, colour.getRGB(), ConfigHandler.drawTextShadow);
					drawnfirst = true;
				}

				heightoffset += 10;
				drawText(font, guiGraphics, follower_string, xcoord + xoffset + widthoffset, heightoffset, followerColour, ConfigHandler.drawTextShadow);
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
