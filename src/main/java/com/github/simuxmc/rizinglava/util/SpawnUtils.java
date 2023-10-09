package com.github.simuxmc.rizinglava.util;

import com.github.simuxmc.rizinglava.RizingLava;
import com.github.simuxmc.rizinglava.commands.essentials.spawn.PlayerPostSpawnEvent;
import com.github.simuxmc.rizinglava.commands.essentials.spawn.PlayerPreSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class SpawnUtils {

	public static void spawn(Player player) {
		PluginManager pluginManager = Bukkit.getPluginManager();
		PlayerPreSpawnEvent preSpawnEvent = new PlayerPreSpawnEvent(player);
		pluginManager.callEvent(preSpawnEvent);
		if (preSpawnEvent.isCancelled()) return;
		Location spawn = RizingLava.getInstance().getGlobalPersistentData().getSpawn();
		player.teleport(spawn);
		pluginManager.callEvent(new PlayerPostSpawnEvent(player));
	}

}
