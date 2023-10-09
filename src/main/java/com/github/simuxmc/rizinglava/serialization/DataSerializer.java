package com.github.simuxmc.rizinglava.serialization;

import org.jetbrains.annotations.NotNull;
import org.jspecify.nullness.Nullable;

import java.io.File;

public interface DataSerializer {

	/**
	 * Reads a file and converts it to a usable object.
	 *
	 * @param file  the file to read
	 * @param clazz the type of the object to convert it to
	 * @return the object stored in the file
	 */
	<T> @Nullable T deserialize(@NotNull File file, @NotNull Class<T> clazz);

	/**
	 * Saves an object to a file
	 *
	 * @param object the object to be written in the file
	 * @param file   the location to store the object
	 */
	<T> void serialize(@NotNull T object, @NotNull File file);

}
