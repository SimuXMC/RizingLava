package com.github.simuxmc.rizinglava.commands.essentials.gamemode;

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

@CommandName("gmc")
public class GameModeCreativeCommand extends Command {

	public GameModeCreativeCommand(String commandName) {
		super(commandName);
	}

	@Override
	protected void executes(CommandSender sender, CommandArguments args) {
		MiniMessage miniMessage = MiniMessage.miniMessage();
		Collection<Player> targets = CommandUtils.getTargets(miniMessage, sender, args);
		if (targets == null) return;
		for (Player p : targets) {
			p.setGameMode(GameMode.CREATIVE);
			Component feedback = miniMessage.deserialize(
					"<gold>Essentials> <green>" + p.getName() +
							"<yellow>'s gamemode has been set to <green>creative<yellow>.");
			sender.sendMessage(feedback);
		}
	}

	@Override
	public Argument<?>[] optionalArguments() {
		Argument<Collection> playerArgument =
				new EntitySelectorArgument.ManyPlayers(CommandUtils.OPTIONAL_PLAYER_ARGUMENT_NAME).instance();
		return new Argument[]{playerArgument};
	}


	@Override
	public String permission() {
		return "rizinglava.gamemode";
	}

}
