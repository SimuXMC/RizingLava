package com.github.simuxmc.rizinglava.serialization.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.github.simuxmc.rizinglava.serialization.kryo.serializers.KryoDataSerializer;
import com.github.simuxmc.rizinglava.util.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.nullness.NullMarked;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import com.github.simuxmc.rizinglava.serialization.DataSerializer;

@NullMarked
public class KryoSerializer implements DataSerializer {

	private final Kryo kryo;

	public KryoSerializer(Kryo kryo, JavaPlugin plugin) {
		this.kryo = kryo;
		registerSerializers(plugin);
	}

	@Override
	public <T> T deserialize(File file, Class<T> clazz) {
		Objects.requireNonNull(file);
		Objects.requireNonNull(clazz);
		if (!file.exists()) throw new IllegalArgumentException("File provided doesn't exist");
		try (FileInputStream fileStream = new FileInputStream(file);
			 Input input = new Input(fileStream)) {
			return kryo.readObject(input, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> void serialize(T object, File file) {
		Objects.requireNonNull(object);
		Objects.requireNonNull(file);
		FileUtils.defendFile(file);
		try (FileOutputStream fileStream = new FileOutputStream(file);
			 Output output = new Output(fileStream)) {
			kryo.writeObject(output, object);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void registerSerializers(JavaPlugin plugin) {
		try {
			Method fileMethod = JavaPlugin.class.getDeclaredMethod("getFile");
			fileMethod.setAccessible(true);
			File jarFile = (File) fileMethod.invoke(plugin);
			Class<KryoDataSerializer<?>>[] serializerClasses = FileUtils.getClasses(
					"com.github.simuxmc.rizinglava.serialization.kryo.serializers",
					KryoDataSerializer.class, jarFile);
			for (Class<?> clazz : serializerClasses) {
				Constructor<?> constructor = clazz.getConstructor();
				constructor.setAccessible(true);
				KryoDataSerializer<?> serializer = (KryoDataSerializer<?>) constructor.newInstance();
				kryo.register(serializer.getType(), serializer);
			}
		} catch (IllegalAccessException | InvocationTargetException | IOException | NoSuchMethodException |
				 ClassNotFoundException | InstantiationException e) {
			throw new RuntimeException(e);
		}
	}

	Kryo getKryo() {
		return kryo;
	}

}
