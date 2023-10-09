package com.github.simuxmc.rizinglava;

import org.bukkit.Location;

public class GlobalPersistentData {

	private Location spawn;

	public GlobalPersistentData(Location spawn) {
		this.spawn = spawn;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

}
