package com.serilum.guifollowers.config;

import com.natamus.collective.config.DuskConfig;
import com.serilum.guifollowers.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry public static boolean showFollowerList = true;
	@Entry public static String followerListHeaderFormat = "Followers:";
	@Entry public static boolean showFollowerHealth = true;
	@Entry public static String followerHealthFormat = ": <health>%";
	@Entry public static boolean showFollowerDistance = true;
	@Entry public static String followerDistanceFormat = " (<distance> blocks)";
	@Entry(min = -1, max = 300) public static int distanceToCheckForFollowersAround = 50;
	@Entry(min = 0, max = 3600) public static int timeBetweenChecksInSeconds = 2;
	@Entry(registry = "entity_type", filter = "animals") public static String entityTypesToIgnore = "workers:*";
	@Entry(options = {"Left", "Center", "Right"}) public static String followerListPosition = "Left";
	@Entry(min = 0, max = 3000) public static int followerListHeightOffset = 20;
	@Entry(min = -3000, max = 3000) public static int followerListWidthOffset = 0;
	@Entry(min = 0.5, max = 3.0) public static double followerListTextScale = 1.0;
	@Entry public static boolean drawTextShadow = true;
	@Entry public static boolean colourFollowerByHealth = false;
	@Entry public static String followerHealthColours = "75:255,255,85;50:255,170,0;25:255,85,85";
	@Entry(rgb = true) public static String RGB = "255,255,255";

	public static void initConfig() {
		configMetaData.put("showFollowerList", Arrays.asList(
			"If disabled, the follower list is not shown at all."
		));
		configMetaData.put("followerListHeaderFormat", Arrays.asList(
			"The header text above the follower list."
		));
		configMetaData.put("showFollowerHealth", Arrays.asList(
			"If enabled, shows the follower's health in the GUI list."
		));
		configMetaData.put("followerHealthFormat", Arrays.asList(
			"The format of the health string in the GUI. <health> will be replaced by the percentage of total health."
		));
		configMetaData.put("showFollowerDistance", Arrays.asList(
			"If enabled, shows the follower's distance in blocks to the player in the GUI list."
		));
		configMetaData.put("followerDistanceFormat", Arrays.asList(
			"The format of the distance string in the GUI. <distance> will be replaced by distance in blocks."
		));
		configMetaData.put("distanceToCheckForFollowersAround", Arrays.asList(
			"The distance in blocks around the player where the mod checks for tamed, non-sitting followers to add to the list. A value of -1 disables this feature."
		));
		configMetaData.put("timeBetweenChecksInSeconds", Arrays.asList(
			"The time in seconds in between checking for tamed, non-sitting followers around the player."
		));
		configMetaData.put("entityTypesToIgnore", Arrays.asList(
			"A comma-separated list of entity ids that are never shown in the follower list. Use '<modid>:*' to ignore every entity of a mod. By default contains the workers from the Villager Workers mod, which always follow."
		));
		configMetaData.put("followerListPosition", Arrays.asList(
			"Where the follower list is shown at the top of the screen."
		));
		configMetaData.put("followerListHeightOffset", Arrays.asList(
			"The vertical offset (y coord) for the follower list. This determines how far down the list should be on the screen. Can be changed to prevent GUIs from overlapping."
		));
		configMetaData.put("followerListWidthOffset", Arrays.asList(
			"The horizontal offset (x coord) for the follower list. A positive value moves the list to the right, a negative value to the left."
		));
		configMetaData.put("followerListTextScale", Arrays.asList(
			"The size of the follower list text. 1.0 is the normal size."
		));
		configMetaData.put("drawTextShadow", Arrays.asList(
			"If the text displayed should have a shadow drawn below it."
		));
		configMetaData.put("colourFollowerByHealth", Arrays.asList(
			"If enabled, a follower's line changes colour based on its health, using followerHealthColours."
		));
		configMetaData.put("followerHealthColours", Arrays.asList(
			"A list of <health percentage>:<R>,<G>,<B> entries separated by a semicolon, with RGB values from 0 to 255. A follower at or below that health percentage is shown in that colour. Used when colourFollowerByHealth is enabled."
		));
		configMetaData.put("RGB", Arrays.asList(
			"The colour of the follower list text, as red, green and blue values from 0 to 255."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}