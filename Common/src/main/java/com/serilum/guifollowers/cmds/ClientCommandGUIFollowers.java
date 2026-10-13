package com.serilum.guifollowers.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.natamus.collective.functions.MessageFunctions;
import com.serilum.guifollowers.data.Variables;
import com.serilum.guifollowers.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;

public class ClientCommandGUIFollowers {
	private static final Minecraft mc = Minecraft.getInstance();

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("guifollowers")
			.requires((iCommandSender) -> iCommandSender.getEntity() instanceof Player)
			.executes((command) -> {
				return usage(mc.player);
			})
			.then(Commands.literal("hide")
			.executes((command) -> {
				return hideLookedAtFollower(mc.player);
			})
			.then(Commands.argument("name", StringArgumentType.greedyString())
			.executes((command) -> {
				return hideFollowersByName(mc.player, StringArgumentType.getString(command, "name"));
			})))
			.then(Commands.literal("unhide")
			.executes((command) -> {
				return unhideFollowers(mc.player);
			}))
		);
	}

	public static int usage(Player player) {
		if (player == null) {
			return 0;
		}

		MessageFunctions.sendClientTranslatableMessage(player, "collective.guifollowers.message.usage", ChatFormatting.DARK_GREEN);
		return 1;
	}

	public static int hideLookedAtFollower(Player player) {
		if (player == null) {
			return 0;
		}

		if (!(mc.crosshairPickEntity instanceof TamableAnimal tamableAnimal) || !tamableAnimal.isOwnedBy(player) || tamableAnimal.isInSittingPose()) {
			MessageFunctions.sendClientTranslatableMessage(player, "collective.guifollowers.message.notlookingatfollower", ChatFormatting.RED);
			return 0;
		}

		Util.hideFollower(tamableAnimal);
		MessageFunctions.sendClientTranslatableMessage(player, "collective.guifollowers.message.hidden", ChatFormatting.DARK_GREEN, tamableAnimal.getName());
		return 1;
	}

	public static int hideFollowersByName(Player player, String name) {
		if (player == null) {
			return 0;
		}

		String followerName = name.strip();

		int hiddenCount = 0;
		for (Entity follower : new ArrayList<Entity>(Variables.activefollowers)) {
			if (!follower.getName().getString().equalsIgnoreCase(followerName)) {
				continue;
			}

			Util.hideFollower(follower);
			hiddenCount += 1;
		}

		if (hiddenCount == 0) {
			MessageFunctions.sendClientTranslatableMessage(player, "collective.guifollowers.message.nofollowernamed", ChatFormatting.RED, followerName);
			return 0;
		}

		MessageFunctions.sendClientTranslatableMessage(player, "collective.guifollowers.message.hiddenbyname", ChatFormatting.DARK_GREEN, hiddenCount, followerName);
		return 1;
	}

	public static int unhideFollowers(Player player) {
		if (player == null) {
			return 0;
		}

		int hiddenCount = Variables.hiddenFollowers.size();
		if (hiddenCount == 0) {
			MessageFunctions.sendClientTranslatableMessage(player, "collective.guifollowers.message.nohiddenfollowers", ChatFormatting.YELLOW);
			return 0;
		}

		Util.showAllFollowers();
		MessageFunctions.sendClientTranslatableMessage(player, "collective.guifollowers.message.unhidden", ChatFormatting.DARK_GREEN, hiddenCount);
		return 1;
	}
}
