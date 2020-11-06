package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import subversion.SVNConnection;

public class Configuration {
	
	private static String host;
	private static String dbPort;
	private static String database;
	private static String userName;
	private static String password;
	private static String svnURL;
	private static String svnConfig;
	private static SVNConnection svn;
	private static String directory;
	
	private Configuration() {}		
	
	public static void initSettings() {
		// loads the serialized settings from database.cfg
		File config = new File("database.cfg");
		FileInputStream inputStream;
		
		if(config.exists()) {
			try {
				inputStream = new FileInputStream(config);
				ObjectInputStream in = new ObjectInputStream(inputStream);
				host = (String) in.readObject();
				dbPort = (String) in.readObject();
				database = (String) in.readObject();
				userName = (String) in.readObject();
				password = (String) in.readObject();
				svnURL = (String) in.readObject();
				svnConfig =(String) in.readObject();
				svn = new SVNConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// TODO Return Error if configuration file doesn't exist
			System.out.println("Could not load database configuration");
		}
	}
	
	public static String getHost() { 
		return host;
	}
	
	public static void setHost(String newHost) {
		host = newHost;
	}
	
	public static String getPort() {
		return dbPort;
	}
	
	public static void setPort(String newPort) {
		dbPort = newPort;
	}
	
	public static String getDatabase() {
		return database;
	}
	
	public static void setDatabase(String newDatabase) {
		database = newDatabase;
	}
	
	public static String getUser() {
		return userName;
	}
	
	public static void setUser(String newUser) {
		userName = newUser;
	}
	
	public static String getPassword() {
		return password;
	}
	
	public static void setPassword(String newPassword) {
		password = newPassword;
	}
	
	public static String getSVNURL () {
		return svnURL;
	}
	
	public static void setSVNURL (String newSVNURL) {
		svnURL = newSVNURL;
	}
	
	public static String getSVNConfig() {
		return svnConfig;
	}
	
	public static void setSVNConfig(String newSVNConfig) {
		svnConfig = newSVNConfig;
	}
	
	public static SVNConnection getSVNConnection() {
		return svn;
	}
	
	public static void setCurrentUser(String user, String pw) {
		svn.setCurrentUser(user, pw);
	}
	
	public static void setDirectory (String dir) {
		directory = dir;
	}
	
	public static void saveConfig() {
		File config = new File("database.cfg");
		FileOutputStream fileStream = null;
    	ObjectOutputStream outStream = null;
    	try {
    		fileStream = new FileOutputStream(config);
    		outStream = new ObjectOutputStream(fileStream);
    		outStream.writeObject(host);
    		outStream.writeObject(dbPort);
    		outStream.writeObject(database);
    		outStream.writeObject(userName);
     		outStream.writeObject(password);
     		outStream.writeObject(svnURL);
     		outStream.writeObject(svnConfig);
     		outStream.writeObject(directory);
    		outStream.close();
    	} catch (IOException e){
    		e.printStackTrace();
    	}
		
	}
}
