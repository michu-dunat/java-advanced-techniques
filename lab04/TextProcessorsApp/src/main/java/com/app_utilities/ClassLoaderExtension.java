package com.app_utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClassLoaderExtension extends ClassLoader {

	@Override
	public Class<?> findClass(String fileName) throws ClassNotFoundException {
		Path absolutePath = Paths.get(fileName);
		byte[] classToLoad = null;
		try {
			classToLoad = Files.readAllBytes(absolutePath);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return defineClass(null, classToLoad, 0, classToLoad.length);
	}

}
