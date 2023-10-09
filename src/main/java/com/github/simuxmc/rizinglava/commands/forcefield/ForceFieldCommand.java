package com.github.simuxmc.rizinglava.commands.forcefield;

import com.github.simuxmc.rizinglava.RizingLava;
import com.github.simuxmc.rizinglava.commands.Command;
import com.github.simuxmc.rizinglava.commands.CommandName;
import dev.jorel.commandapi.executors.CommandArguments;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

@CommandName("forcefield")
public class ForceFieldCommand extends Command {

	public ForceFieldCommand(String commandName) {
		super(commandName);
	}

	@Override
	protected void executes(CommandSender sender, CommandArguments args) {
		MiniMessage miniMessage = MiniMessage.miniMessage();
		ForceFieldHandler handler = RizingLava.getInstance().getForceFieldHandler();
		Player player = (Player) sender;
		boolean isEnabled = handler.isForceFieldEnabled(player);
		handler.setForceFieldEnabled(player, !isEnabled);
		String visual = (!isEnabled ? "<green>en" : "<red>dis") + "abled";
		Component feedback = miniMessage.deserialize(
				"<gradient:red:dark_grey>ForceField><reset> <grey>ForceField: " + visual);
		sender.sendMessage(feedback);
	}

	@Override
	public String permission() {
		return "rizinglava.forcefield";
	}

	@Override
	public String[] aliases() {
		return new String[]{"ff"};
	}

	@Override
	public Predicate<CommandSender> requirement() {
		return commandSender -> commandSender instanceof Player;
	}

}
