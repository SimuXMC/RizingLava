package com.github.simuxmc.rizinglava.serialization.kryo.serializers.bukkit;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.github.simuxmc.rizinglava.serialization.kryo.serializers.KryoDataSerializer;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldSerializer extends KryoDataSerializer<World> {

	@Override
	public void write(Kryo kryo, Output output, World object) {
		output.writeString(object.getName());
	}

	@Override
	public World read(Kryo kryo, Input input, Class<? extends World> type) {
		return Bukkit.getWorld(input.readString());
	}

	@Override
	public Class<World> getType() {
		return World.class;
	}

}
