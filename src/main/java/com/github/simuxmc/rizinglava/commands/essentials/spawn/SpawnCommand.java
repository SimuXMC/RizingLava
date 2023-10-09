package com.github.simuxmc.rizinglava.commands.essentials.spawn;

import com.github.simuxmc.rizinglava.commands.Command;
import com.github.simuxmc.rizinglava.commands.CommandName;
import com.github.simuxmc.rizinglava.util.SpawnUtils;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandName("spawn")
public class SpawnCommand extends Command {

	public SpawnCommand(String commandName) {
		super(commandName);
	}

	@Override
	protected void executes(CommandSender sender, CommandArguments args) {
		Player player = (Player) sender;
		SpawnUtils.spawn(player);
	}

}
