package com.github.simuxmc.rizinglava.serialization.kryo.serializers.nms;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.github.simuxmc.rizinglava.serialization.kryo.serializers.KryoDataSerializer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;

public class CraftWorldSerializer extends KryoDataSerializer<CraftWorld> {

	@Override
	public void write(Kryo kryo, Output output, CraftWorld object) {
		output.writeString(object.getName());
	}

	@Override
	public CraftWorld read(Kryo kryo, Input input, Class<? extends CraftWorld> type) {
		return (CraftWorld) Bukkit.getWorld(input.readString());
	}

	@Override
	public Class<CraftWorld> getType() {
		return CraftWorld.class;
	}

}
