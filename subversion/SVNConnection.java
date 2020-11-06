package subversion;

import java.io.Serializable;

import application.Configuration;

public class SVNConnection implements Serializable {

	private static final long serialVersionUID = 8476812953490338308L;
	private String hostName;
	private String hostURL;
	private String userName;
	private String password;
	
	public SVNConnection() {
		hostURL = Configuration.getSVNURL();
		userName = null;
		password = null;
	}
		
	public void setCurrentUser(String user, String pw) {
		userName = user;
		password = pw;
	}
		
	/**
	 * Returns the stored hostName String
	 * @return hostName
	 */
	public String getHostName() {
		return hostName;
	}
	
	/**
	 * Returns the stored hostURL String
	 * @return hostURL
	 */
	public String getHostURL() {
		return hostURL;
	}
	
	/**
	 * Returns the stored userName String
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Returns the stored password String
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Overrides the toString method so the returned String is the hostName
	 */
	@Override
	public String toString() {
		return hostName;
	}
}
