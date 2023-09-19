package com.github.simuxmc.rizinglava.commands.forcefield;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ForceFieldHandler implements Listener {

	private final Set<Player> forceFieldPlayers;

	public ForceFieldHandler(PluginManager pluginManager, JavaPlugin plugin) {
		forceFieldPlayers = new HashSet<>();
		pluginManager.registerEvents(this, plugin);
		new BukkitRunnable() {
			@Override
			public void run() {
				// TODO create forcefield event and call it here so it can be cancelled elsewhere if necessary (if player
				// TODO is spectating, in-game, etc.)
				for (Player player : forceFieldPlayers) {
					List<Entity> nearbyEntities = player.getNearbyEntities(2.25, 3, 2.25);
					nearbyEntities.remove(player);
					nearbyEntities = nearbyEntities.stream().filter(entity -> entity instanceof Player).toList();
					for (Entity entity : nearbyEntities) {
						Location playerLoc = player.getLocation();
						Location entityLoc = entity.getLocation();
						double xDif = entityLoc.getX()-playerLoc.getX();
						double yDif = (entityLoc.getY()-playerLoc.getY())+1.25;
						double zDif = entityLoc.getZ()-playerLoc.getZ();
						Vector vector = new Vector(xDif, yDif, zDif).multiply(0.2);
						entity.setVelocity(vector);
						entityLoc.getWorld().playSound(entityLoc, Sound.ENTITY_CHICKEN_EGG, 1.5f, 0.5f);
					}
				}
			}
		}.runTaskTimer(plugin, 0, 5);
	}

	public boolean isForceFieldEnabled(Player player) {
		return forceFieldPlayers.contains(player);
	}

	public void setForceFieldEnabled(Player player, boolean enabled) {
		if (enabled) forceFieldPlayers.add(player);
		else forceFieldPlayers.remove(player);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		forceFieldPlayers.remove(event.getPlayer());
	}

}
