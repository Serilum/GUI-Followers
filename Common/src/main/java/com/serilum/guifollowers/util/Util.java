package com.serilum.guifollowers.util;

import com.natamus.collective.functions.DataFunctions;
import com.serilum.guifollowers.config.ConfigHandler;
import com.serilum.guifollowers.data.Variables;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.UUID;

public class Util {
	public static boolean isHidden(Entity entity) {
		return Variables.hiddenFollowers.contains(entity.getUUID());
	}

	public static void hideFollower(Entity entity) {
		if (isHidden(entity)) {
			return;
		}

		Variables.hiddenFollowers.add(entity.getUUID());
		saveHiddenFollowers();
	}

	public static void showFollower(Entity entity) {
		Variables.hiddenFollowers.remove(entity.getUUID());
		saveHiddenFollowers();
	}

	public static void showAllFollowers() {
		Variables.hiddenFollowers = new ArrayList<>();
		saveHiddenFollowers();
	}

	public static void loadHiddenFollowers() {
		Variables.hiddenFollowers = new ArrayList<>();

		File hiddenFollowersFile = getHiddenFollowersFile();
		if (!hiddenFollowersFile.isFile()) {
			return;
		}

		try {
			for (String line : Files.readAllLines(hiddenFollowersFile.toPath())) {
				String uuidString = line.strip();
				if (uuidString.isEmpty()) {
					continue;
				}

				Variables.hiddenFollowers.add(UUID.fromString(uuidString));
			}
		}
		catch (IOException | IllegalArgumentException ignored) { }
	}

	private static void saveHiddenFollowers() {
		File hiddenFollowersFile = getHiddenFollowersFile();

		try {
			boolean ignored = hiddenFollowersFile.getParentFile().mkdirs();

			PrintWriter writer = new PrintWriter(hiddenFollowersFile, StandardCharsets.UTF_8);
			for (UUID uuid : Variables.hiddenFollowers) {
				writer.println(uuid.toString());
			}
			writer.close();
		}
		catch (IOException ignored) { }
	}

	private static File getHiddenFollowersFile() {
		return new File(DataFunctions.getConfigDirectory() + File.separator + Reference.MOD_ID + File.separator + "hidden_followers.txt");
	}

	public static boolean isIgnoredEntityType(Entity entity) {
		String entityTypeId = EntityType.getKey(entity.getType()).toString();
		String entityTypeNamespace = entityTypeId.split(":")[0];

		for (String rawEntityType : ConfigHandler.entityTypesToIgnore.split(",")) {
			String ignoredEntityType = rawEntityType.strip();
			if (ignoredEntityType.equals(entityTypeId) || ignoredEntityType.equals(entityTypeNamespace + ":*")) {
				return true;
			}
		}
		return false;
	}

	public static int getHealthColour(int percentHealth, int defaultColour) {
		int healthColour = defaultColour;
		int lowestMatchingThreshold = Integer.MAX_VALUE;

		for (String rawEntry : ConfigHandler.followerHealthColours.split(";")) {
			String[] thresholdAndColour = rawEntry.strip().split(":");
			if (thresholdAndColour.length != 2) {
				continue;
			}

			String[] rgb = thresholdAndColour[1].split(",");
			if (rgb.length != 3) {
				continue;
			}

			try {
				int threshold = Integer.parseInt(thresholdAndColour[0].strip());
				Color colour = new Color(Integer.parseInt(rgb[0].strip()), Integer.parseInt(rgb[1].strip()), Integer.parseInt(rgb[2].strip()), 255);

				if (percentHealth <= threshold && threshold < lowestMatchingThreshold) {
					lowestMatchingThreshold = threshold;
					healthColour = colour.getRGB();
				}
			}
			catch (IllegalArgumentException ignored) { }
		}

		return healthColour;
	}
}
