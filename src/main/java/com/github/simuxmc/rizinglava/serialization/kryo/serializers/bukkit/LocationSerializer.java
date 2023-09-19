package com.github.simuxmc.rizinglava.serialization.kryo.serializers.bukkit;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.github.simuxmc.rizinglava.serialization.kryo.serializers.KryoDataSerializer;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationSerializer extends KryoDataSerializer<Location> {

	@Override
	public void write(Kryo kryo, Output output, Location object) {
		kryo.writeObject(output, object.getWorld());
		output.writeDouble(object.getX());
		output.writeDouble(object.getY());
		output.writeDouble(object.getZ());
		output.writeFloat(object.getPitch());
		output.writeFloat(object.getYaw());
	}

	@Override
	public Location read(Kryo kryo, Input input, Class<? extends Location> type) {
		World world = kryo.readObject(input, World.class);
		double x = input.readDouble();
		double y = input.readDouble();
		double z = input.readDouble();
		float pitch = input.readFloat();
		float yaw = input.readFloat();
		return new Location(world, x, y, z, pitch, yaw);
	}

	@Override
	public Class<Location> getType() {
		return Location.class;
	}

}
