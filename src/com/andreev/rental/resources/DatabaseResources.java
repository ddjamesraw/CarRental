package com.andreev.rental.resources;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class DatabaseResources implements IResources {
	
	private static final String RESOURCES_PATH="resources.database";
	private static final Logger LOG = Logger.getLogger(DatabaseResources.class);
	private ResourceBundle resourceBundle;

	private static class SingletonLoader{
		public static final DatabaseResources INSTANCE = new DatabaseResources();
	}
	
	private DatabaseResources() {
		resourceBundle = ResourceBundle.getBundle(RESOURCES_PATH);
		LOG.info("Database resources has been initialized");
	}
	
	public static IResources getInstance() {
		return SingletonLoader.INSTANCE;
	}

	@Override
	public String getString(String key) {
		return resourceBundle.getString(key);
	}
	
	@Override
	public boolean containsKey(String key) {
		if (key != null) {
			return resourceBundle.containsKey(key);
		}
		return false;
	}

}
