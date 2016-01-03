package com.testcase.n11.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

	private static Configuration instance;

	private Properties configProps = new Properties();

	private String serverUrl;
	private String n11LoginEmail;
	private String n11LoginPassword;

	public static Configuration getInstance() {

		if (instance == null) {
			createInstance();
		}
		return instance;
	}

	private static synchronized void createInstance() {
		if (instance == null) {
			instance = new Configuration();
		}
	}

	private Configuration() {
		InputStream is = null;
		try {
			is = ClassLoader.getSystemResourceAsStream("config.properties");
			configProps.load(is);
			
			this.serverUrl = configProps.getProperty("server.url");
			this.n11LoginEmail = configProps.getProperty("n11LoginEmail");
			this.n11LoginPassword = configProps.getProperty("n11LoginPassword");

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(is !=null){
				try{
					is.close();
				}catch(IOException e){}
			}
		}

	}
	public String[][] stringTo2DArray(String string){
		String[][] datas= new String [(string.split("\\|\\|")).length]
									 [(string.split("\\|\\|")[0]).split(",").length];
		for(int i=0;i<(string.split("\\|\\|")).length;i++){
			datas[i]=(string.split("\\|\\|")[i]).split(",");
		}
		return datas;
	}

	public String getServerUrl() {
		return serverUrl;
	}
	
	public String getN11LoginEmail() {
		return n11LoginEmail;
	}

	public String getN11LoginPassword() {
		return n11LoginPassword;
	}

}
