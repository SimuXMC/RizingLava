package com.github.simuxmc.rizinglava.modules;

import com.github.simuxmc.rizinglava.commands.essentials.spawn.PlayerPostSpawnEvent;
import com.github.simuxmc.rizinglava.util.SpawnUtils;
import com.github.simuxmc.rizinglava.util.items.ItemBuilder;
import com.github.simuxmc.rizinglava.util.items.SkullItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.PlayerInventory;

public class SpawnModule implements Listener {

	private final ItemBuilder.Item QUEUE_STAR = new ItemBuilder(Material.NETHER_STAR)
			.named("<#88FF00>(Left) <#D2FF9E>Auto Queue <white>| <#00FF49>(Right) <#B3FDC8>Queue")
			.build();
	private final ItemBuilder.Item COSMETICS_CHEST = new ItemBuilder(Material.CHEST)
			.named("<gradient:#F4D03F:#DC7633>Cosmetics")
			.build();

	private final ItemBuilder.Item SETTINGS_HEAD = new SkullItemBuilder(
			"https://textures.minecraft.net/texture/e4d49bae95c790c3b1ff5b2f01052a714d6185481d5b1c85930b3f99d2321674")
			.named("<#A5C5AE>Settings")
			.build();

	@EventHandler
	private void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		SpawnUtils.spawn(player);
	}

	@EventHandler
	private void onPostSpawn(PlayerPostSpawnEvent event) {
		Player player = event.getPlayer();
		player.getInventory().clear();
		player.getEquipment().clear();
		player.setGameMode(GameMode.ADVENTURE);
		AttributeInstance maxHealthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
		double maxHealth = maxHealthAttribute == null ? 20 : maxHealthAttribute.getValue();
		player.setHealth(maxHealth);
		player.setFoodLevel(20);
		player.clearActivePotionEffects();
		giveItems(player);
		player.getInventory().setHeldItemSlot(0);
	}

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
		if (!(material.contains("BUTTON") || material.contains("_DOOR")) && !isInCreative(player))
			event.setCancelled(true);
	}

	@EventHandler
	private void onDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (isInSpawn(player)) event.setCancelled(true);
	}

	@EventHandler
	private void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (!isInSpawn(player)) return;
		if (isInCreative(player)) return;
		System.out.println(player.getOpenInventory().getType());
		InventoryType type = player.getOpenInventory().getType();
		if (type == InventoryType.CRAFTING || type == InventoryType.PLAYER) event.setCancelled(true);
 	}

	@EventHandler
	private void onOffHand(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if (!isInSpawn(player)) return;
		if (!isInCreative(player)) event.setCancelled(true);
	}

	@EventHandler
	private void onHungerMeterChange(FoodLevelChangeEvent event) {
		if (isInSpawn((Player) event.getEntity())) event.setCancelled(true);
	}

	private void giveItems(Player player) {
		PlayerInventory inventory = player.getInventory();
		inventory.setItem(0, QUEUE_STAR.getItem());
		inventory.setItem(4, COSMETICS_CHEST.getItem());
		inventory.setItem(8, SETTINGS_HEAD.getItem());
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private boolean isInCreative(Player player) {
		return player.getGameMode() == GameMode.CREATIVE;
	}

	private boolean isInSpawn(Player player) {
		return player.getLocation().getWorld().getName().equalsIgnoreCase("spawn");
	}

}
