package com.github.simuxmc.rizinglava.commands.essentials.spawn;

import com.github.simuxmc.rizinglava.GlobalPersistentData;
import com.github.simuxmc.rizinglava.RizingLava;
import com.github.simuxmc.rizinglava.commands.Command;
import com.github.simuxmc.rizinglava.commands.CommandName;
import dev.jorel.commandapi.executors.CommandArguments;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.text.DecimalFormat;
import java.util.function.Predicate;

@CommandName("setspawn")
public class SetSpawnCommand extends Command {

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

	public SetSpawnCommand(String commandName) {
		super(commandName);
	}

	@Override
	protected void executes(CommandSender sender, CommandArguments args) {
		Player player = (Player) sender;
		RizingLava pluginInstance = RizingLava.getInstance();
		GlobalPersistentData globalPersistentData = pluginInstance.getGlobalPersistentData();
		Location spawn = player.getLocation();
		globalPersistentData.setSpawn(spawn);
		File file = new File(pluginInstance.getDataFolder(), "global.rl");
		pluginInstance.getDataSerializer().serialize(globalPersistentData, file);
		String x = DECIMAL_FORMAT.format(spawn.getX());
		String y = DECIMAL_FORMAT.format(spawn.getY());
		String z = DECIMAL_FORMAT.format(spawn.getZ());
		String world = spawn.getWorld().getName();
		MiniMessage miniMessage = MiniMessage.miniMessage();
		String prettySpawn = "x: " + x + " y: " + y + " z: " + z + " <yellow>in <green>" + world;
		Component feedback = miniMessage.deserialize("<gold>Essentials> <yellow>Set spawn to: <green>" + prettySpawn);
		player.sendMessage(feedback);
	}

	@Override
	public String permission() {
		return "rizinglava.setspawn";
	}

	@Override
	public Predicate<CommandSender> requirement() {
		return commandSender -> commandSender instanceof Player;
	}

}
