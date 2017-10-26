package com.contetial.realEstate.utility.propReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class ReadConfigurations {

	private Properties prop;
	private String propFileName;
	
	private static ReadConfigurations config;// = new ReadConfigurations();
	
	private ReadConfigurations(String pathName) throws IOException{
		URL location = ReadConfigurations.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println(location.getFile());
        propFileName = pathName;
		loadProperties();
	}
	
	public static ReadConfigurations getInstance(String pathName) throws IOException{
		if(null == config){			
			config = new ReadConfigurations(pathName);
		}
		return config;
	}
	
	public static ReadConfigurations getCurrentInstance(String pathName) throws IOException{
		if(null == config){			
			config = new ReadConfigurations(pathName);
		}else{
			config = null;
			config = new ReadConfigurations(pathName);
		}
		return config;
	}
	
	private void loadProperties() throws IOException{
		prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		 
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");			
		}
	}
	
	public Properties getProps(){
		return prop;
	}
	
	public void setPropFileName(String propFileName){
		this.propFileName = propFileName;
	}
	
	public static void main(String[] args) throws IOException {
		/*Path path;

		path = Paths.get(".");
		System.out.println("Absolute Path: " + path.getParent());

		path = FileSystems.getDefault().getPath(".");
		System.out.println("Absolute Path: " + path.toAbsolutePath());*/	
		getInstance("./conf/application.properties");
	}
}
