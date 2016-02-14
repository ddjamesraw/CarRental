package com.andreev.rental.resources;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class CommandResources implements IResources {

	private static final String RESOURCES_PATH = "resources.resources";
	private static final Logger LOG = Logger.getLogger(CommandResources.class);
	private ResourceBundle resourceBundle;

	private static class SingletonLoader {
		public static final CommandResources INSTANCE = new CommandResources();
	}

	private CommandResources() {
		resourceBundle = ResourceBundle.getBundle(RESOURCES_PATH);
		LOG.info("Application resources has been initialized");
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
