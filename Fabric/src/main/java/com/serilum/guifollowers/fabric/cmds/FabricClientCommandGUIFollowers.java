package com.serilum.guifollowers.fabric.cmds;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.serilum.guifollowers.cmds.ClientCommandGUIFollowers;
import net.minecraft.world.entity.player.Player;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class FabricClientCommandGUIFollowers {
	public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
		dispatcher.register(ClientCommands.literal("guifollowers")
			.requires((iCommandSender) -> iCommandSender.getEntity() instanceof Player)
			.executes((command) -> {
				return ClientCommandGUIFollowers.usage(command.getSource().getPlayer());
			})
			.then(ClientCommands.literal("hide")
			.executes((command) -> {
				return ClientCommandGUIFollowers.hideLookedAtFollower(command.getSource().getPlayer());
			})
			.then(ClientCommands.argument("name", StringArgumentType.greedyString())
			.executes((command) -> {
				return ClientCommandGUIFollowers.hideFollowersByName(command.getSource().getPlayer(), StringArgumentType.getString(command, "name"));
			})))
			.then(ClientCommands.literal("unhide")
			.executes((command) -> {
				return ClientCommandGUIFollowers.unhideFollowers(command.getSource().getPlayer());
			}))
		);
	}
}
