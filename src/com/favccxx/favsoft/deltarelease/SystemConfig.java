package com.favccxx.favsoft.deltarelease;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;


public class SystemConfig {
	
	private static SystemConfig sysConfig;
	
	private XMLConfiguration xmlConfig = null;
	
	private SystemConfig(){
		
	}
	
//	public static XMLConfiguration getInstance(){
//		if(systemConfig == null){
//			return new SystemConfig();
//		}
//		return systemConfig;
//	}
	
	public static XMLConfiguration getInstance(){
		if (sysConfig == null) {
			sysConfig = new SystemConfig();
			sysConfig.loadConfig();
		}
		return sysConfig.xmlConfig;
	}
	
	private void loadConfig(){
		if(xmlConfig == null){
			xmlConfig = new XMLConfiguration();
			try {
				xmlConfig.load(PublishConstants.SYSTEM_CONFIG_DIR + PublishConstants.PUBLISH_CONFIG_FILE_NAME);
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
