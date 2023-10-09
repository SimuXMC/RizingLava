package com.github.simuxmc.rizinglava.commands.essentials;

import com.github.simuxmc.rizinglava.commands.Command;
import com.github.simuxmc.rizinglava.commands.CommandName;
import com.github.simuxmc.rizinglava.commands.CommandUtils;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.EntitySelectorArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

@CommandName("invsee")
public class InvSeeCommand extends Command {

	public InvSeeCommand(String commandName) {
		super(commandName);
	}

	@Override
	protected void executes(CommandSender sender, CommandArguments args) {
		MiniMessage miniMessage = MiniMessage.miniMessage();
		Player target = (Player) args.get(CommandUtils.PLAYER_ARGUMENT_NAME);
		assert target != null; // required argument
		((Player) sender).openInventory(target.getInventory());
		Component feedback = miniMessage.deserialize(
				"<gold>Essentials> <yellow>You are now viewing <green>" + target.getName() + "<yellow>'s inventory.");
		sender.sendMessage(feedback);
	}

	@Override
	public Argument<?>[] arguments() {
		Argument<Player> playerArgument =
				new EntitySelectorArgument.OnePlayer(CommandUtils.PLAYER_ARGUMENT_NAME).instance();
		return new Argument[]{playerArgument};
	}

	@Override
	public Predicate<CommandSender> requirement() {
		return commandSender -> commandSender instanceof Player;
	}

}
