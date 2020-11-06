package application;

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

public class ConfigurationBuilder extends JFrame {
	
	private static final long serialVersionUID = 4560332707526972763L;
	private JPanel settingsPanel;
	private JTextField hostField;
	private JTextField portField;
	private JTextField dbField;
	private JTextField userField;
	private JPasswordField pwField;
	private JTextField urlField;
	private JTextField configField;
	private JTextField directoryField;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigurationBuilder frame = new ConfigurationBuilder();
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
	public ConfigurationBuilder() {
		setTitle("Database Initializer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 510);
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
		dbSettingsLabel.setBounds(125, 37, 198, 20);
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
		
		hostField = new JTextField();
		hostField.setBounds(149, 68, 260, 20);
		settingsPanel.add(hostField);
		hostField.setColumns(10);
		
		portField = new JTextField();
		portField.setColumns(10);
		portField.setBounds(149, 96, 260, 20);
		settingsPanel.add(portField);
		
		dbField = new JTextField();
		dbField.setColumns(10);
		dbField.setBounds(149, 127, 260, 20);
		settingsPanel.add(dbField);
		
		userField = new JTextField();
		userField.setColumns(10);
		userField.setBounds(149, 158, 260, 20);
		settingsPanel.add(userField);
		
		pwField = new JPasswordField();
		pwField.setBounds(149, 187, 260, 20);
		settingsPanel.add(pwField);
		
				
		JLabel subVersionLabel = new JLabel("Subversion Settings");
		subVersionLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		subVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		subVersionLabel.setBounds(125, 228, 198, 20);
		settingsPanel.add(subVersionLabel);
		
		JLabel urlLabel = new JLabel("URL :");
		urlLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		urlLabel.setHorizontalAlignment(SwingConstants.LEFT);
		urlLabel.setBounds(20, 274, 119, 20);
		settingsPanel.add(urlLabel);
		
		JLabel configLabel = new JLabel("Config File :");
		configLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		configLabel.setHorizontalAlignment(SwingConstants.LEFT);
		configLabel.setBounds(20, 305, 119, 20);
		settingsPanel.add(configLabel);
		
		JLabel directoryLabel = new JLabel("Local Directory :");
		directoryLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		directoryLabel.setHorizontalAlignment(SwingConstants.LEFT);
		directoryLabel.setBounds(20, 336, 119, 20);
		settingsPanel.add(directoryLabel);
		
		urlField = new JTextField();
		urlField.setColumns(10);
		urlField.setBounds(149, 274, 260, 20);
		settingsPanel.add(urlField);
		
		configField = new JTextField();
		configField.setColumns(10);
		configField.setBounds(149, 305, 260, 20);
		settingsPanel.add(configField);
		
		directoryField = new JTextField();
		directoryField.setColumns(10);
		directoryField.setBounds(149, 336, 260, 20);
		settingsPanel.add(directoryField);
		
		loadConfig();
		
		JButton buildButton = new JButton();
		buildButton.setToolTipText("Build Database");
		buildButton.setForeground((Color) null);
		buildButton.setBorderPainted(false);
		buildButton.setBackground((Color) null);
		buildButton.setBounds(125, 401, 48, 48);
		buildButton.setIcon(new ImageIcon("images/save.png"));
		buildButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveConfig();
			}
		});
		
		settingsPanel.add(buildButton);
				
		JButton exitButton = new JButton();
		exitButton.setToolTipText("Exit");
		exitButton.setForeground((Color) null);
		exitButton.setBorderPainted(false);
		exitButton.setBackground((Color) null);
		exitButton.setBounds(275, 401, 48, 48);
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
				urlField.setText((String) in.readObject());
				configField.setText((String) in.readObject());
				directoryField.setText((String) in.readObject());
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
     		outStream.writeObject(urlField.getText());
     		outStream.writeObject(configField.getText());
     		outStream.writeObject(directoryField.getText());
    		outStream.close();
    	} catch (IOException e){
    		e.printStackTrace();
    	}
	}
}
