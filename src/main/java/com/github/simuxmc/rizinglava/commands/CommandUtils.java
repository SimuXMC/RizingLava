package com.github.simuxmc.rizinglava.commands;

import dev.jorel.commandapi.executors.CommandArguments;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class CommandUtils {

	public static final String PLAYER_ARGUMENT_NAME = "target";

	@SuppressWarnings("unchecked")
	public static Collection<Player> getTargets(MiniMessage miniMessage, CommandSender sender, CommandArguments args) {
		Collection<Player> playerArg = (Collection<Player>) args.get(PLAYER_ARGUMENT_NAME);
		Collection<Player> targets = playerArg;
		if (playerArg == null) {
			if (!(sender instanceof Player player)) {
				Component error = miniMessage.deserialize(
						"<red>Console cannot use this command unless a target is provided.");
				sender.sendMessage(error);
				return null;
			}
			targets = List.of(player);
		}
		if (targets.isEmpty()) {
			Component emptyMsg = miniMessage.deserialize("<red>No targets were found.");
			sender.sendMessage(emptyMsg);
			return null;
		}
		return targets;
	}

}
