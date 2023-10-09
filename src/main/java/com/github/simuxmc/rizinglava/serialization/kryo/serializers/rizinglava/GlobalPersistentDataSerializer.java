package com.github.simuxmc.rizinglava.serialization.kryo.serializers.rizinglava;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.github.simuxmc.rizinglava.GlobalPersistentData;
import com.github.simuxmc.rizinglava.serialization.kryo.serializers.KryoDataSerializer;
import org.bukkit.Location;

public class GlobalPersistentDataSerializer extends KryoDataSerializer<GlobalPersistentData> {

	@Override
	public void write(Kryo kryo, Output output, GlobalPersistentData object) {
		kryo.writeObject(output, object.getSpawn());
	}

	@Override
	public GlobalPersistentData read(Kryo kryo, Input input, Class<? extends GlobalPersistentData> type) {
		Location spawn = kryo.readObject(input, Location.class);
		return new GlobalPersistentData(spawn);
	}

	@Override
	public Class<GlobalPersistentData> getType() {
		return GlobalPersistentData.class;
	}

}
