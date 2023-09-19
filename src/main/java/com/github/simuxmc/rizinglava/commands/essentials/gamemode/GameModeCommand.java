package com.github.simuxmc.rizinglava.commands.essentials.gamemode;

import com.github.simuxmc.rizinglava.commands.Command;
import com.github.simuxmc.rizinglava.commands.CommandName;
import com.github.simuxmc.rizinglava.commands.CommandUtils;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.EntitySelectorArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

@CommandName("gamemode")
public class GameModeCommand extends Command {

	private static final List<String> GAMEMODE_STRING_LIST = generateGameModeStrings();

	public GameModeCommand(String commandName) {
		super(commandName);
	}

	@Override
	protected void executes(CommandSender sender, CommandArguments args) {
		@SuppressWarnings("DataFlowIssue")
		String gameModeArg = ((String) args.get("gamemode")).toLowerCase(Locale.ENGLISH);
		MiniMessage miniMessage = MiniMessage.miniMessage();
		if (!GAMEMODE_STRING_LIST.contains(gameModeArg)) {
			Component error = miniMessage.deserialize("<red>'" + gameModeArg + "' is not a gamemode.");
			sender.sendMessage(error);
			return;
		}
		Collection<Player> targets = CommandUtils.getTargets(miniMessage, sender, args);
		if (targets == null) return;
		GameMode gm = GameMode.valueOf(gameModeArg.toUpperCase(Locale.ENGLISH));
		for (Player p : targets) {
			p.setGameMode(gm);
			Component feedback = miniMessage.deserialize(
					"<gold>Essentials> <green>" + p.getName() +
							"<yellow>'s gamemode has been set to <green>" + gameModeArg + "<yellow>.");
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
	public Argument<?>[] arguments() {
		Argument<String> gameModeArgument = new StringArgument("gamemode")
				.includeSuggestions(ArgumentSuggestions.strings(GAMEMODE_STRING_LIST))
				.instance();
		return new Argument[]{gameModeArgument};
	}

	@Override
	public String permission() {
		return "rizinglava.gamemode";
	}

	private static List<String> generateGameModeStrings() {
		GameMode[] gameModes = GameMode.values();
		String[] gameModesString = new String[gameModes.length];
		for (int i = 0; i < gameModes.length; i++) {
			gameModesString[i] = gameModes[i].toString().toLowerCase(Locale.ENGLISH);
		}
		return List.of(gameModesString);
	}

}
