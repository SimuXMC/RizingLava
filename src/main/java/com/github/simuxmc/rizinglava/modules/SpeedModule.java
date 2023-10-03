package com.github.simuxmc.rizinglava.modules;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class SpeedModule implements Listener {

	@EventHandler
	private void onOffHand(PlayerSwapHandItemsEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() != GameMode.CREATIVE) return;
		if (!player.isFlying()) return;
		if (!player.hasPermission("rizinglava.speedfly")) return;
		player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 10, 0.5f);
		player.setFlySpeed(player.getFlySpeed() > 0.2f ? 0.1f : 0.5f);
	}

}
