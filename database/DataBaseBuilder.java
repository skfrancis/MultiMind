package database;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseBuilder extends JFrame {
	
	private static final long serialVersionUID = 4560332707526972763L;
	private String url;
	private JPanel settingsPanel;
	private JTextField hostField;
	private JTextField portField;
	private JTextField dbField;
	private JTextField userField;
	private JPasswordField pwField;
	private JPasswordField adminPWField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataBaseBuilder frame = new DataBaseBuilder();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DataBaseBuilder() {
		setTitle("Database Initializer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 375);
		settingsPanel = new JPanel();
		settingsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		settingsPanel.setLayout(null);		
		dbSettings();
		setContentPane(settingsPanel);
	}
	
	private void dbSettings(){
		
		JLabel dbSettingsLabel = new JLabel("Database Settings");
		dbSettingsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dbSettingsLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		dbSettingsLabel.setBounds(75, 37, 145, 20);
		settingsPanel.add(dbSettingsLabel);
			
		JLabel hostLabel = new JLabel("Host IP :");
		hostLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		hostLabel.setHorizontalAlignment(SwingConstants.LEFT);
		hostLabel.setBounds(20, 68, 119, 20);
		settingsPanel.add(hostLabel);
		
		JLabel portLabel = new JLabel("Port :");
		portLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		portLabel.setHorizontalAlignment(SwingConstants.LEFT);
		portLabel.setBounds(20, 96, 119, 20);
		settingsPanel.add(portLabel);
		
		JLabel dbLabel = new JLabel("Database :");
		dbLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		dbLabel.setHorizontalAlignment(SwingConstants.LEFT);
		dbLabel.setBounds(20, 127, 119, 20);
		settingsPanel.add(dbLabel);
		
		JLabel userLabel = new JLabel("Username :");
		userLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		userLabel.setHorizontalAlignment(SwingConstants.LEFT);
		userLabel.setBounds(20, 158, 119, 20);
		settingsPanel.add(userLabel);
		
		JLabel pwLabel = new JLabel("Password :");
		pwLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		pwLabel.setHorizontalAlignment(SwingConstants.LEFT);
		pwLabel.setBounds(20, 187, 119, 20);
		settingsPanel.add(pwLabel);
		
		JLabel adminLabel = new JLabel("Admin Password :");
		adminLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		adminLabel.setHorizontalAlignment(SwingConstants.LEFT);
		adminLabel.setBounds(20, 218, 119, 20);
		settingsPanel.add(adminLabel);
		
		hostField = new JTextField();
		hostField.setBounds(149, 68, 145, 20);
		settingsPanel.add(hostField);
		hostField.setColumns(10);
		
		portField = new JTextField();
		portField.setColumns(10);
		portField.setBounds(149, 96, 145, 20);
		settingsPanel.add(portField);
		
		dbField = new JTextField();
		dbField.setColumns(10);
		dbField.setBounds(149, 127, 145, 20);
		settingsPanel.add(dbField);
		
		userField = new JTextField();
		userField.setColumns(10);
		userField.setBounds(149, 158, 145, 20);
		settingsPanel.add(userField);
		
		pwField = new JPasswordField();
		pwField.setBounds(149, 187, 145, 20);
		settingsPanel.add(pwField);
		
		adminPWField = new JPasswordField();
		adminPWField.setBounds(149, 218, 145, 20);
		settingsPanel.add(adminPWField);
		
		loadConfig();
		
		JButton buildButton = new JButton();
		buildButton.setToolTipText("Build Database");
		buildButton.setForeground((Color) null);
		buildButton.setBorderPainted(false);
		buildButton.setBackground((Color) null);
		buildButton.setBounds(75, 261, 48, 48);
		buildButton.setIcon(new ImageIcon("images/buildDB.png"));
		buildButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveConfig();
				buildDataBase();
			}
		});
		settingsPanel.add(buildButton);
		
		JButton exitButton = new JButton();
		exitButton.setToolTipText("Exit");
		exitButton.setForeground((Color) null);
		exitButton.setBorderPainted(false);
		exitButton.setBackground((Color) null);
		exitButton.setBounds(172, 261, 48, 48);
		exitButton.setIcon(new ImageIcon("images/cancel.png"));
		settingsPanel.add(exitButton);
		
	}
	
	private void loadConfig() {
		// loads the serialized settings from database.cfg
		File config = new File("database.cfg");
		FileInputStream inputStream;
		
		if(config.exists()) {
			try {
				inputStream = new FileInputStream(config);
				ObjectInputStream in = new ObjectInputStream(inputStream);
				hostField.setText((String) in.readObject());
				portField.setText((String) in.readObject());
				dbField.setText((String) in.readObject());
				userField.setText((String) in.readObject());
				pwField.setText((String) in.readObject());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// TODO Return Error if configuration file doesn't exist
			System.out.println("Could not load database configuration");
		}
	}
	
	private void saveConfig() {
		File config = new File("database.cfg");
		FileOutputStream fileStream = null;
    	ObjectOutputStream outStream = null;
    	try {
    		fileStream = new FileOutputStream(config);
    		outStream = new ObjectOutputStream(fileStream);
    		outStream.writeObject(hostField.getText());
    		outStream.writeObject(portField.getText());
    		outStream.writeObject(dbField.getText());
    		outStream.writeObject(userField.getText());
     		outStream.writeObject(new String(pwField.getPassword()));
     		outStream.writeObject(new String("http//128.196.239.104/svn/MultiMind"));
     		outStream.writeObject(new String("/etc/svn-auth-users"));
    		outStream.close();
    	} catch (IOException e){
    		e.printStackTrace();
    	}
		
	}

	private void buildDataBase() {
		String query;
		Connection dbConnection = null;
	    Statement dbStatement = null;
	   
	    dbConnection = initConnection();
	    try {
	    	query = "CREATE DATABASE " + dbField.getText();
			dbStatement = dbConnection.createStatement();
			dbStatement.executeUpdate(query);
		    // Need to build a script file for this part and read in line by line make this easier to add tables etc.
			dbStatement = dbConnection.createStatement();
			dbStatement.executeUpdate(query);
			dbStatement.close();
		    dbConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Connection initConnection() {
		Connection connection = null;
		String dbDriver = null;
		String dbCommand = null;
		String passWord;
	
		dbDriver = "com.mysql.jdbc.Driver";
		dbCommand = "jdbc:mysql://";
		
		try {
		Class.forName(dbDriver);
		url = dbCommand + hostField.getText() + ":" + portField.getText();
		passWord = new String(pwField.getPassword());
		connection = DriverManager.getConnection (url, userField.getText(), passWord);
		} catch (Exception exception) {
	        System.exit(-1);
		}
		return connection;
	}
}
