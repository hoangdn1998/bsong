package util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	public static Properties readProperties() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			properties.load(classloader.getResourceAsStream("/config/database.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
}
