package mainGUI;

import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import org.tmatesoft.svn.core.SVNException;

import adminGUI.AdminTools;
import application.Configuration;

import database.LifeStream;

import subversion.SVNConnector;
import tools.ColorScheme;
import tools.PasswordEncoder;
import tools.SessionTracker;

public class Login extends JFrame {
		
	private static final long serialVersionUID = 3430961594714355640L;
	
	private JTextField userIDField;
	private JPasswordField passwordField;
	private JComboBox<String> CPIDList;

	/**
	 * Create the frame.
	 */
	public Login() {
		setTitle("MultiMind Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(400, 400));
		Point middle = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setLocation(new Point((int)middle.getX() - 200, (int)middle.getY() - 200));
		setContentPane(MainPanel());
	}
	
	private JPanel MainPanel(){
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		loginPanel.setLayout(null);
		loginPanel.setBackground(ColorScheme.getPanelBackground());
		
		JLabel userIDLabel = new JLabel("User Name:");
		userIDLabel.setBounds(66, 176, 70, 20);
		userIDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		userIDLabel.setForeground(ColorScheme.getPanelForeground());
		loginPanel.add(userIDLabel);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(66, 210, 70, 20);
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordLabel.setForeground(ColorScheme.getPanelForeground());
		loginPanel.add(passwordLabel);
		
		JLabel CPIDLabel = new JLabel("CP Name:");
		CPIDLabel.setBounds(66, 241, 70, 20);
		CPIDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		CPIDLabel.setForeground(ColorScheme.getPanelForeground());
		loginPanel.add(CPIDLabel);
		
		userIDField = new JTextField();
		userIDField.setBounds(165, 176, 150, 20);
		userIDField.setColumns(10);
		userIDField.setForeground(ColorScheme.getEditorForeground());
		userIDField.setBackground(ColorScheme.getEditorBackground());
		loginPanel.add(userIDField);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(165, 210, 150, 20);
		passwordField.setForeground(ColorScheme.getEditorForeground());
		passwordField.setBackground(ColorScheme.getEditorBackground());
		loginPanel.add(passwordField);

		CPIDList = new JComboBox<String>();
		CPIDList.setModel(buildCPList());
		CPIDList.setBounds(165, 241, 150, 20);
		CPIDList.setForeground(ColorScheme.getEditorForeground());
		CPIDList.setBackground(ColorScheme.getEditorBackground());
		CPIDList.setSelectedIndex(0);
		loginPanel.add(CPIDList);
		
		JButton connectButton = new JButton();
		connectButton.setToolTipText("Connect");
		connectButton.setIcon(new ImageIcon("images/connect.png"));
		connectButton.setBounds(109, 287, 48, 48);
		connectButton.setForeground(ColorScheme.getPanelForeground());
		connectButton.setBackground(ColorScheme.getPanelBackground());
		connectButton.setBorderPainted(false);
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent argument) {
				if(loginCheck()) {
					updateSession();
					ProjectCenter projectFrame = new ProjectCenter();
					projectFrame.addWindowListener(new WindowListener() {
    					public void windowActivated(WindowEvent arg0) {}
    					@Override
    					public void windowClosed(WindowEvent arg0) {
       		        		setVisible(true);
    					}
    					public void windowClosing(WindowEvent arg0) {}
    					public void windowDeactivated(WindowEvent arg0) {}
    					public void windowDeiconified(WindowEvent arg0) {}
    					public void windowIconified(WindowEvent arg0) {}
    					public void windowOpened(WindowEvent arg0) {}
                	});
					setVisible(false);
					projectFrame.setVisible(true);
				} else {
	        		JOptionPane.showMessageDialog(new Frame(),
	        			    "Login information incorrect",
	        			    "Invalid Login",
	        			    JOptionPane.ERROR_MESSAGE);
				}
				userIDField.setText(null);
        		passwordField.setText(null);
        		CPIDList.setSelectedIndex(0);
			}
		});
		loginPanel.add(connectButton);
		
		JButton adminButton = new JButton();
		adminButton.setIcon(new ImageIcon("images/admin.png"));
		adminButton.setToolTipText("Admin Tools");
		adminButton.setBounds(181, 287, 48, 48);
		adminButton.setForeground(ColorScheme.getPanelForeground());
		adminButton.setBackground(ColorScheme.getPanelBackground());
		adminButton.setBorderPainted(false);
		adminButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
        		if(adminLoginCheck()){
        			updateSession();
        			AdminTools adminFrame = new AdminTools();
        			adminFrame.addWindowListener(new WindowListener() {
    					public void windowActivated(WindowEvent arg0) {}
    					@Override
    					public void windowClosed(WindowEvent arg0) {
    					    CPIDList.setModel(buildCPList());
    					    CPIDList.updateUI();
    					    setVisible(true);
    					}
    					public void windowClosing(WindowEvent arg0) {}
    					public void windowDeactivated(WindowEvent arg0) {}
    					public void windowDeiconified(WindowEvent arg0) {}
    					public void windowIconified(WindowEvent arg0) {}
    					public void windowOpened(WindowEvent arg0) {}
                	});
        			setVisible(false);
        			adminFrame.setVisible(true);
        		} else {
        			JOptionPane.showMessageDialog(new Frame(),
        			    "Login information incorrect",
        			    "Invalid Login",
        			    JOptionPane.ERROR_MESSAGE);
        		}
         		userIDField.setText(null);
        		passwordField.setText(null);
        		CPIDList.setSelectedIndex(0);
   			}
		});
		loginPanel.add(adminButton);
		
		JButton exitButton = new JButton();
		exitButton.setIcon(new ImageIcon("images/close.png"));
		exitButton.setToolTipText("Exit");
		exitButton.setBounds(254, 287, 48, 48);
		exitButton.setForeground(ColorScheme.getPanelForeground());
		exitButton.setBackground(ColorScheme.getPanelBackground());
		exitButton.setBorderPainted(false);
		exitButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent argument) {
				try {
					LifeStream.closeConnection();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                System.exit(0);
            }

        });
		loginPanel.add(exitButton);
		
		return loginPanel;
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
	
	private boolean adminLoginCheck() {
		boolean success = false;
		ResultSet result = null;
		String query = null;
		String cpName = CPIDList.getSelectedItem().toString();
		
		if(cpName.compareTo("Admin") == 0) {
			try {
				query = "SELECT users.password FROM users where " +
						"CPID in (SELECT CPID FROM CP WHERE CP.Name = '" + cpName + "')" +
						" AND users.Name = '" + userIDField.getText() + "';";
				result = LifeStream.getQuery(query, false);
				String password = PasswordEncoder.encode(new String(passwordField.getPassword()), userIDField.getText());
				while(result.next()) {
					if(password.compareTo(result.getString(1)) == 0) {
						success = true;
					}
				}
				LifeStream.closeQuery();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return success;
	}
	
	private boolean loginCheck() {
		boolean success = false;
		ResultSet result = null;
		String query = null;
		String cpName = CPIDList.getSelectedItem().toString();
		try {
			query = "SELECT users.password FROM users where " +
					"CPID in (SELECT CPID FROM CP WHERE CP.Name = '" + cpName + "')" +
					" AND users.Name = '" + userIDField.getText() + "';";
			result = LifeStream.getQuery(query, false);
			String password = PasswordEncoder.encode(new String(passwordField.getPassword()), userIDField.getText());
			while(result.next()) {
				if(password.compareTo(result.getString(1)) == 0) {
					success = true;
				}
			}
			LifeStream.closeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	
	private void updateSession() {
		ResultSet result = null;
		String query = null;
		String cpName = CPIDList.getSelectedItem().toString();
		String svnUser = new String (userIDField.getText() + "-" + CPIDList.getSelectedItem().toString());
		
		Configuration.setCurrentUser(svnUser, new String(passwordField.getPassword()));
		try {
			SVNConnector.setConnection(Configuration.getSVNConnection());
		} catch (SVNException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
		
		try {
			query = "SELECT userID, Red, Green, Blue FROM users WHERE users.Name = '" + userIDField.getText() + "';";
			result = LifeStream.getQuery(query, false);
			if(result.next()){
				SessionTracker.Init(result.getInt(1));
				ColorScheme.setForeground(result.getInt(2), result.getInt(3), result.getInt(3));
			}
			LifeStream.closeQuery();
			
			query = "SELECT Red, Green, Blue FROM CP WHERE CP.Name = '" + cpName + "';";
			result = LifeStream.getQuery(query, false);
			if(result.next()){
				ColorScheme.setBackground(result.getInt(1), result.getInt(2), result.getInt(3));
			}
			LifeStream.closeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
