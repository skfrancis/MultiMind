package application;

import java.sql.SQLException;

import database.LifeStream;
import javax.swing.SwingUtilities;

import mainGUI.Login;

import tools.ColorScheme;


public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ColorScheme.initColor();
				Configuration.initSettings();
				if(LifeStream.initSettings()) {
					try {
						LifeStream.openConnection();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Login application = new Login();
					application.setVisible(true);
				}
				else {
					
				}
			}
		});
	}
}
