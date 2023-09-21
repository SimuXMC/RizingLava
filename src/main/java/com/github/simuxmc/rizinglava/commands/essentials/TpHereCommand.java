package com.github.simuxmc.rizinglava.commands.essentials;

import com.github.simuxmc.rizinglava.commands.Command;
import com.github.simuxmc.rizinglava.commands.CommandName;
import com.github.simuxmc.rizinglava.commands.CommandUtils;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.EntitySelectorArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.function.Predicate;

@CommandName("tphere")
public class TpHereCommand extends Command {

	public TpHereCommand(String commandName) {
		super(commandName);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void executes(CommandSender sender, CommandArguments args) {
		MiniMessage miniMessage = MiniMessage.miniMessage();
		Collection<Player> targets = (Collection<Player>) args.get(CommandUtils.PLAYER_ARGUMENT_NAME);
		assert targets != null; // required argument
		Location location = ((Player) sender).getLocation();
		for (Player p : targets) {
			p.teleport(location);
			Component feedback = miniMessage.deserialize(
					"<gold>Essentials> <green>" + p.getName() + " <yellow>has been teleported to you.");
			sender.sendMessage(feedback);
		}
	}

	@Override
	public Argument<?>[] arguments() {
		Argument<Collection> playerArgument =
				new EntitySelectorArgument.ManyPlayers(CommandUtils.PLAYER_ARGUMENT_NAME).instance();
		return new Argument[]{playerArgument};
	}

	@Override
	public Predicate<CommandSender> requirement() {
		return commandSender -> commandSender instanceof Player;
	}

}
