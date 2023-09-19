package com.github.simuxmc.rizinglava.serialization;

import org.jspecify.nullness.NullMarked;

import java.io.File;

@NullMarked
public interface DataSerializer {

	/**
	 * Reads a file and converts it to a usable object.
	 *
	 * @param file the file to read
	 * @param clazz the type of the object to convert it to
	 * @return the object stored in the file
	 */
	<T> T deserialize(File file, Class<T> clazz);

	/**
	 * Saves an object to a file
	 *
	 * @param object the object to be written in the file
	 * @param file the location to store the object
	 */
	<T> void serialize(T object, File file);

}
