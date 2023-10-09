package com.github.simuxmc.rizinglava.commands.essentials.spawn;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerPostSpawnEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();

	public PlayerPostSpawnEvent(@NotNull Player who) {
		super(who);
	}

	@Override
	public @NotNull HandlerList getHandlers() {
		return handlers;
	}

	public static @NotNull HandlerList getHandlerList() {
		return handlers;
	}

}
