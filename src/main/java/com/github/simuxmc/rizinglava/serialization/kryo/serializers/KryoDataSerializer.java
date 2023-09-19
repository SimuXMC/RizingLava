package com.github.simuxmc.rizinglava.serialization.kryo.serializers;

import com.esotericsoftware.kryo.Serializer;

public abstract class KryoDataSerializer<T> extends Serializer<T> {

	/**
	 * @return The class of the type to be serialized
	 */
	public abstract Class<T> getType();

}
