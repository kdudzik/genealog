package org.dudyngo.genealog;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Properties;

public class GenealogConfig {
	private static final GenealogConfig instance = null;
	private static final String CONFIG_FILE = "genealog.properties";
	private Properties properties;

	private GenealogConfig() {
	}

	public static GenealogConfig getInstance() {
		if (instance == null) {
			return new GenealogConfig();
		} else {
			return instance;
		}
	}

	public Optional<String> getProperty(String key) {
		if (properties == null) {
			try {
				properties = new Properties();
				InputStream stream = GenealogConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
				properties.load(new InputStreamReader(stream, Charset.forName("UTF8")));
			} catch (IOException e) {
				System.err.println(CONFIG_FILE + " not found");
				System.exit(1);
			}
		}
		return Optional.ofNullable(properties.getProperty(key));
	}
}
