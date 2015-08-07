package com.fsti.flmnt.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * 类描述： 属性文件工具类
 * 
 */
public class PropertiesUtil {
	/**
	 * 获得对应路径的属性文件
	 * 
	 * @param path
	 *            属性文件完整路径(绝对路径)
	 * @return
	 */
	public static Properties getProperties(String path) {
		Properties properties = null;
		if (path != null && path.trim().length() > 0) {
			properties = load(path);
		}
		return properties;
	}

	/**
	 * 加载
	 * 
	 * @param path
	 * @return
	 */
	private static Properties load(String path) {
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = PropertiesUtil.class.getResourceAsStream(path);
			properties.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return properties;
	}

}
