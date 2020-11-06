package tools;

import java.io.InputStream;
import java.io.OutputStream;
import application.Configuration;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class SSHTool {
	
	JSch jschModule;
	Session session;
	Channel channel;
	
	public SSHTool () throws JSchException {
		jschModule = new JSch();
		session = jschModule.getSession(Configuration.getUser(), Configuration.getHost(), 22);
		session.setUserInfo(new MyUserInfo());
	}
	
	public boolean createUser(String user, String password) throws Exception {
		String command = new String("sudo -S -p '' htpasswd -bm " + Configuration.getSVNConfig() + " " + user + " " + password); 
		connect(command);
		disconnect();
		return false;
	}
	
	public boolean editUser() throws Exception {
		String command = null;
		connect(command);
		
		disconnect();
		return false;
	}
	
	public boolean deleteUser(String user) throws Exception {
		String command = new String("sudo -S - p '' htpasswd -D " + Configuration.getSVNConfig() + " " + user);
		connect(command);
		
		disconnect();
		return false;
	}
	
	private void connect (String command) throws Exception {
		session.connect();
		channel = session.openChannel("exec");
		((ChannelExec)channel).setCommand(command);
		InputStream in = channel.getInputStream();
	    OutputStream out = channel.getOutputStream();
	    ((ChannelExec)channel).setErrStream(System.err);
	    channel.connect();
	    
	    out.write((Configuration.getPassword() + "\n").getBytes());
	    out.flush();

	    byte[] tmp=new byte[1024];
	    while(true){
	    	while(in.available() > 0){
	    		int index = in.read(tmp, 0, 1024);
	    		if(index < 0) {
	    			break;
	    		}
	    		System.out.print(new String(tmp, 0, index));
	        }
	        if(channel.isClosed()) {
	        	System.out.println("exit-status: " + channel.getExitStatus());
	        	break;
	        }
        }
	}
	
	private void disconnect() {
		channel.disconnect();
		session.disconnect();
	}
	
	
	private class MyUserInfo implements UserInfo {
		@Override
		public String getPassphrase() {
			return null;
		}

		@Override
		public String getPassword() {
			return Configuration.getPassword();
		}

		@Override
		public boolean promptPassphrase(String message) {
			return true;
		}

		@Override
		public boolean promptPassword(String message) {
			return true;
		}

		@Override
		public boolean promptYesNo(String message) {
			return true;
		}

		@Override
		public void showMessage(String message) {
			System.out.println(message);
		}
	}
}
