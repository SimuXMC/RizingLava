package com.github.simuxmc.rizinglava.modules;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpawnModule implements Listener {

	@EventHandler
	private void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (!isInSpawn(player)) return;
		if (!isInCreative(player)) event.setCancelled(true);
	}

	@EventHandler
	private void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (!isInSpawn(player)) return;
		if (!isInCreative(player)) event.setCancelled(true);
	}

	@EventHandler
	private void onDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player player)) return;
		if (isInSpawn(player)) event.setCancelled(true);
	}

	@EventHandler
	private void onBlockInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (!isInSpawn(player)) return;
		Block block = event.getClickedBlock();
		if (block == null) return;
		String material = block.getBlockData().getMaterial().toString();
		if (!(material.contains("BUTTON") || material.contains("_DOOR")) && !isInCreative(player)) event.setCancelled(true);
	}

	@EventHandler
	private void onDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (isInSpawn(player)) event.setCancelled(true);
	}

	@EventHandler
	private void onHungerMeterChange(FoodLevelChangeEvent event) {
		if (isInSpawn((Player) event.getEntity())) event.setCancelled(true);
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private boolean isInCreative(Player player) {
		return player.getGameMode() == GameMode.CREATIVE;
	}

	private boolean isInSpawn(Player player) {
		return player.getLocation().getWorld().getName().equalsIgnoreCase("spawn");
	}

}
