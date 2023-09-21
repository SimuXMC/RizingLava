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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

@CommandName("ping")
public class PingCommand extends Command {

	public PingCommand(String commandName) {
		super(commandName);
	}

	@Override
	protected void executes(CommandSender sender, CommandArguments args) {
		MiniMessage miniMessage = MiniMessage.miniMessage();
		Collection<Player> targets = CommandUtils.getTargets(miniMessage, sender, args);
		if (targets == null) return;
		for (Player p : targets) {
			p.setGameMode(GameMode.SURVIVAL);
			Component feedback = miniMessage.deserialize(
					"<gold>Essentials> <green>" + p.getName() + "<yellow>'s ping: <green>" + p.getPing() + "ms");
			sender.sendMessage(feedback);
		}
	}

	@Override
	public Argument<?>[] optionalArguments() {
		Argument<Collection> playerArgument =
				new EntitySelectorArgument.ManyPlayers(CommandUtils.PLAYER_ARGUMENT_NAME).instance();
		return new Argument[]{playerArgument};
	}

}
