package adminGUI;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import tools.ColorScheme;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

import database.LifeStream;
import javax.swing.JList;

import application.Configuration;

public class AdminTools extends JFrame {

	private static final long serialVersionUID = 6929407365166815931L;
	private JPanel settingsPanel;
	private JTextField hostField;
	private JTextField portField;
	private JTextField dbField;
	private JTextField userField;
	private JPasswordField pwField;
	private JComboBox<String> CPIDList;
	private JList<String> CPUserList;
	private JTextField urlField;
	private JTextField configField;
	private JTextField directoryField;

	/**
	 * Create the frame.
	 */
	public AdminTools() {
		setTitle("Admin Tools");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		Point middle = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setLocation(new Point((int)middle.getX() - 300, (int)middle.getY() - 250));
		settingsPanel = new JPanel();
		settingsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		settingsPanel.setBackground(ColorScheme.getPanelBackground());
		settingsPanel.setLayout(null);
		dbSettings();
		svnSettings();
		cpSettings();
		cpUserSettings();
		setContentPane(settingsPanel);
	}
	
	private void dbSettings(){
					
		JLabel dbSettingsLabel = new JLabel("Database Settings");
		dbSettingsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dbSettingsLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		dbSettingsLabel.setBounds(112, 37, 215, 20);
		dbSettingsLabel.setForeground(ColorScheme.getPanelForeground());
		settingsPanel.add(dbSettingsLabel);
			
		JLabel hostLabel = new JLabel("Host IP :");
		hostLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		hostLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		hostLabel.setBounds(20, 68, 82, 20);
		hostLabel.setForeground(ColorScheme.getPanelForeground());
		settingsPanel.add(hostLabel);
		
		JLabel portLabel = new JLabel("Port :");
		portLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		portLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		portLabel.setBounds(20, 96, 82, 20);
		portLabel.setForeground(ColorScheme.getPanelForeground());
		settingsPanel.add(portLabel);
		
		JLabel dbLabel = new JLabel("Database :");
		dbLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		dbLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		dbLabel.setBounds(20, 127, 82, 20);
		dbLabel.setForeground(ColorScheme.getPanelForeground());
		settingsPanel.add(dbLabel);
		
		JLabel userLabel = new JLabel("Username :");
		userLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userLabel.setBounds(20, 158, 82, 20);
		userLabel.setForeground(ColorScheme.getPanelForeground());
		settingsPanel.add(userLabel);
		
		JLabel pwLabel = new JLabel("Password :");
		pwLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		pwLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pwLabel.setBounds(20, 187, 82, 20);
		pwLabel.setForeground(ColorScheme.getPanelForeground());
		settingsPanel.add(pwLabel);
		
		hostField = new JTextField();
		hostField.setBounds(112, 68, 215, 20);
		hostField.setColumns(10);
		hostField.setText(Configuration.getHost());
		settingsPanel.add(hostField);
		
		portField = new JTextField();
		portField.setColumns(10);
		portField.setBounds(112, 96, 215, 20);
		portField.setText(Configuration.getPort());
		settingsPanel.add(portField);
		
		dbField = new JTextField();
		dbField.setColumns(10);
		dbField.setBounds(112, 127, 215, 20);
		dbField.setText(Configuration.getDatabase());
		settingsPanel.add(dbField);
		
		userField = new JTextField();
		userField.setColumns(10);
		userField.setBounds(112, 158, 215, 20);
		userField.setText(Configuration.getUser());
		settingsPanel.add(userField);
		
		pwField = new JPasswordField();
		pwField.setBounds(112, 187, 215, 20);
		pwField.setText(Configuration.getPassword());
		settingsPanel.add(pwField);
				
		JButton saveButton = new JButton();
		saveButton.setIcon(new ImageIcon("images/save.png"));
		saveButton.setToolTipText("Save");
		saveButton.setForeground((Color) null);
		saveButton.setBorderPainted(false);
		saveButton.setBackground((Color) null);
		saveButton.setBounds(140, 389, 48, 48);
		saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	saveConfig();
            }
        });
		settingsPanel.add(saveButton);
		
		JButton cancelButton = new JButton();
		cancelButton.setIcon(new ImageIcon("images/cancel.png"));
		cancelButton.setToolTipText("Cancel");
		cancelButton.setForeground((Color) null);
		cancelButton.setBorderPainted(false);
		cancelButton.setBackground((Color) null);
		cancelButton.setBounds(245, 389, 48, 48);
		cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
		settingsPanel.add(cancelButton);
	}

	private void svnSettings() {
		JLabel svnLabel = new JLabel("Subversion Settings");
		svnLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		svnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		svnLabel.setForeground(ColorScheme.getPanelForeground());
		svnLabel.setBounds(112, 228, 215, 20);
		settingsPanel.add(svnLabel);
		
		JLabel urlLabel = new JLabel("URL :");
		urlLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		urlLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		urlLabel.setForeground(ColorScheme.getPanelForeground());
		urlLabel.setBounds(20, 259, 82, 20);
		settingsPanel.add(urlLabel);
		
		urlField = new JTextField();
		urlField.setColumns(10);
		urlField.setBounds(112, 259, 215, 20);
		urlField.setText(Configuration.getSVNURL());
		settingsPanel.add(urlField);
		
		JLabel configLabel = new JLabel("Config File :");
		configLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		configLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		configLabel.setForeground(ColorScheme.getPanelForeground());
		configLabel.setBounds(20, 290, 82, 20);
		settingsPanel.add(configLabel);
		
		configField = new JTextField();
		configField.setColumns(10);
		configField.setBounds(112, 290, 215, 20);
		configField.setText(Configuration.getSVNConfig());
		settingsPanel.add(configField);
		
		JLabel directoryLabel = new JLabel("Local Directory :");
		directoryLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		directoryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		directoryLabel.setForeground(ColorScheme.getPanelForeground());
		directoryLabel.setBounds(20, 321, 82, 20);
		settingsPanel.add(directoryLabel);
		
		directoryField = new JTextField();
		directoryField.setColumns(10);
		directoryField.setBounds(112, 321, 215, 20);
		directoryField.setText((String) null);
		settingsPanel.add(directoryField);
	}
	
	private void cpSettings() {
				
		JLabel CPLabel = new JLabel("Composite Personaes");
		CPLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		CPLabel.setHorizontalAlignment(SwingConstants.CENTER);
		CPLabel.setBounds(356, 37, 192, 20);
		CPLabel.setForeground(ColorScheme.getPanelForeground());
		settingsPanel.add(CPLabel);
		
		CPIDList = new JComboBox<String>();
		CPIDList.setModel(buildCPList());
		CPIDList.setBounds(356, 68, 192, 20);
		CPIDList.setSelectedIndex(0);
		CPIDList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CPUserList.setModel(buildUserList());
				CPUserList.updateUI();				
			}
		});
		settingsPanel.add(CPIDList);
		
		JButton addCPButton = new JButton();
		addCPButton.setIcon(new ImageIcon("images/addCP.png"));
		addCPButton.setToolTipText("Add Composite Personae");
		addCPButton.setForeground((Color) null);
		addCPButton.setBorderPainted(false);
		addCPButton.setBackground((Color) null);
		addCPButton.setBounds(356, 99, 48, 48);
		addCPButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	CPAdd addFrame = new CPAdd();
            	addFrame.addWindowListener(new WindowListener() {
					public void windowActivated(WindowEvent arg0) {}
					@Override
					public void windowClosed(WindowEvent arg0) {
					    CPIDList.setModel(buildCPList());
					    CPIDList.setSelectedIndex(0);
					    CPIDList.updateUI();
					    CPUserList.setModel(buildUserList());
					    CPUserList.updateUI();
					}
					public void windowClosing(WindowEvent arg0) {}
					public void windowDeactivated(WindowEvent arg0) {}
					public void windowDeiconified(WindowEvent arg0) {}
					public void windowIconified(WindowEvent arg0) {}
					public void windowOpened(WindowEvent arg0) {}
            	});
    			addFrame.setVisible(true);
           }
		});
		settingsPanel.add(addCPButton);
		
		JButton editCPButton = new JButton();
		editCPButton.setIcon(new ImageIcon("images/editCP.png"));
		editCPButton.setToolTipText("Edit Composite Personae");
		editCPButton.setForeground((Color) null);
		editCPButton.setBorderPainted(false);
		editCPButton.setBackground((Color) null);
		editCPButton.setBounds(427, 99, 48, 48);
		editCPButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String CPName = (String) CPIDList.getSelectedItem();
				if(CPName.compareTo("Admin") != 0) {
					CPEdit editFrame = new CPEdit(CPName);
					editFrame.addWindowListener(new WindowListener() {
						public void windowActivated(WindowEvent arg0) {}
						@Override
						public void windowClosed(WindowEvent arg0) {
							CPIDList.setModel(buildCPList());
						    CPIDList.updateUI();
						    CPIDList.setSelectedIndex(0);
						    CPUserList.setModel(buildUserList());
						    CPUserList.updateUI();
						}
						public void windowClosing(WindowEvent arg0) {}
						public void windowDeactivated(WindowEvent arg0) {}
						public void windowDeiconified(WindowEvent arg0) {}
						public void windowIconified(WindowEvent arg0) {}
						public void windowOpened(WindowEvent arg0) {}
					});
					editFrame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(new Frame(),
						    "Admin Composite Personae can not be edited", "Edit Not Allowed", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		settingsPanel.add(editCPButton);
		
		JButton deleteCPButton = new JButton();
		deleteCPButton.setIcon(new ImageIcon("images/deleteCP.png"));
		deleteCPButton.setToolTipText("Delete Composite Personae");
		deleteCPButton.setForeground((Color) null);
		deleteCPButton.setBorderPainted(false);
		deleteCPButton.setBackground((Color) null);
		deleteCPButton.setBounds(500, 99, 48, 48);
		deleteCPButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement statement;
				String CPName = (String) CPIDList.getSelectedItem();
				
				if(CPName.compareTo("Admin") != 0) {
					int option = JOptionPane.showConfirmDialog(new Frame(), "Are you sure you want to Delete " + 
							CPName + " ?",
						    "Delete Connection", JOptionPane.YES_NO_OPTION);
					 if (option == JOptionPane.YES_OPTION) {
						 try {
							statement = LifeStream.getConnection().prepareStatement("DELETE FROM CP WHERE NAME = ?;");
							statement.setString(1, CPName);
							statement.execute();
							JOptionPane.showMessageDialog(new Frame(),
								    "Composite Personae: " + CPName + " deleted successfully",
								    "Delete Successful",
								    JOptionPane.INFORMATION_MESSAGE);
							CPIDList.setModel(buildCPList());
						    CPIDList.updateUI();
						    CPIDList.setSelectedIndex(0);
						    CPUserList.setModel(buildUserList());
						    CPUserList.updateUI();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					 }
				}
				else {
					JOptionPane.showMessageDialog(new Frame(),
							"Admin Composite Personae can not be deleted", "Delete Not Allowed", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		settingsPanel.add(deleteCPButton);
	}
		
	private void cpUserSettings() {
		
		JLabel CPUsersLabel = new JLabel("Composite Personae Users");
		CPUsersLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
		CPUsersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		CPUsersLabel.setForeground((Color) null);
		CPUsersLabel.setBounds(356, 171, 192, 20);
		CPUsersLabel.setForeground(ColorScheme.getPanelForeground());
		settingsPanel.add(CPUsersLabel);
		
		CPUserList = new JList<String>();
		CPUserList.setModel(buildUserList());
		CPUserList.setBounds(356, 205, 192, 175);
		CPUserList.setBackground(ColorScheme.getEditorBackground());
		CPUserList.setForeground(ColorScheme.getEditorForeground());
		settingsPanel.add(CPUserList);
		
		JButton addUserButton = new JButton();
		addUserButton.setIcon(new ImageIcon("images/addUser.png"));
		addUserButton.setToolTipText("Add User");
		addUserButton.setForeground((Color) null);
		addUserButton.setBorderPainted(false);
		addUserButton.setBackground((Color) null);
		addUserButton.setBounds(356, 389, 48, 48);
		addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	UserAdd addFrame = new UserAdd((String) CPIDList.getSelectedItem());
            	addFrame.addWindowListener(new WindowListener() {
					public void windowActivated(WindowEvent arg0) {}
					@Override
					public void windowClosed(WindowEvent arg0) {
					    CPUserList.setModel(buildUserList());
					    CPUserList.updateUI();
					}
					public void windowClosing(WindowEvent arg0) {}
					public void windowDeactivated(WindowEvent arg0) {}
					public void windowDeiconified(WindowEvent arg0) {}
					public void windowIconified(WindowEvent arg0) {}
					public void windowOpened(WindowEvent arg0) {}
            	});
       			addFrame.setVisible(true);
           }
		});
		settingsPanel.add(addUserButton);
		
		JButton editUserButton = new JButton();
		editUserButton.setIcon(new ImageIcon("images/editUser.png"));
		editUserButton.setToolTipText("Edit User");
		editUserButton.setForeground((Color) null);
		editUserButton.setBorderPainted(false);
		editUserButton.setBackground((Color) null);
		editUserButton.setBounds(427, 389, 48, 48);
		settingsPanel.add(editUserButton);
		
		JButton deleteUserButton = new JButton();
		deleteUserButton.setIcon(new ImageIcon("images/deleteUser.png"));
		deleteUserButton.setToolTipText("Delete User");
		deleteUserButton.setForeground((Color) null);
		deleteUserButton.setBorderPainted(false);
		deleteUserButton.setBackground((Color) null);
		deleteUserButton.setBounds(500, 389, 48, 48);
		settingsPanel.add(deleteUserButton);
	}
		
	private void saveConfig() {
		Configuration.setHost(hostField.getText());
		Configuration.setPort(portField.getText());
		Configuration.setDatabase(dbField.getText());
		Configuration.setUser(userField.getText());
		Configuration.setPassword(new String(pwField.getPassword()));
		Configuration.setSVNURL(urlField.getText());
		Configuration.setSVNConfig(configField.getText());
		Configuration.setDirectory(directoryField.getText());
		Configuration.saveConfig();
	}
	
	private DefaultComboBoxModel<String> buildCPList() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		ResultSet result = null;
		String query = null;
		
		try {
			query = "SELECT Name FROM CP ORDER BY Name";
			result = LifeStream.getQuery(query, false);
			while(result.next()){
				model.addElement(result.getString(1));
			}
			LifeStream.closeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
	
	private DefaultListModel<String> buildUserList() {
		DefaultListModel<String> model = new DefaultListModel<String>();
		ResultSet result = null;
		String query = null;
		
		try {
			query = "SELECT users.Name FROM users where CPID in (SELECT CPID FROM CP WHERE CP.Name = '" + (String) CPIDList.getSelectedItem() + "');";
			result = LifeStream.getQuery(query, false);
			while(result.next()) {
				model.addElement(result.getString(1));
			}
			LifeStream.closeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
}
