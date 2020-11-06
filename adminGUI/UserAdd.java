package adminGUI;

import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import tools.ColorScheme;
import tools.PasswordEncoder;
import tools.SSHTool;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPasswordField;

import database.LifeStream;

public class UserAdd extends JFrame {

	private static final long serialVersionUID = 6624839513892532042L;
	private JPanel addPanel;
	private JTextField userField;
	private JColorChooser colorChooser;
	private String CPName;
	private JPasswordField passwordField;
		
	/**
	 * Create the frame.
	 */
	public UserAdd(String CP) {
		CPName = CP;
		setTitle("Add User");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 550);
		Point middle = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setLocation(new Point((int)middle.getX() - 325, (int)middle.getY() - 275));
		addPanel = new JPanel();
		addPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		addPanel.setLayout(null);
		addPanel.setBackground(ColorScheme.getPanelBackground());
		addFrame();
	}
	
	private void addFrame() {
		JLabel userNameLabel = new JLabel("User Name :");
		userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userNameLabel.setBounds(63, 49, 220, 20);
		userNameLabel.setForeground(ColorScheme.getPanelForeground());
		addPanel.add(userNameLabel);
		
		userField = new JTextField();
		userField.setBounds(311, 49, 220, 20);
		addPanel.add(userField);
		userField.setColumns(10);
		
		colorChooser = new JColorChooser();
		colorChooser.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		colorChooser.setLocation(10, 116);
		colorChooser.setSize(614, 326);
		colorChooser.getPreviewPanel().setBackground(previewColor());
		addPanel.add(colorChooser);
		setContentPane(addPanel);
		
		JLabel CPNameLabel = new JLabel("Composite Personae:");
		CPNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		CPNameLabel.setForeground((Color) null);
		CPNameLabel.setBounds(63, 18, 220, 20);
		CPNameLabel.setForeground(ColorScheme.getPanelForeground());
		addPanel.add(CPNameLabel);
		
		JLabel currentCP = new JLabel(CPName);
		currentCP.setHorizontalAlignment(SwingConstants.CENTER);
		currentCP.setForeground((Color) null);
		currentCP.setBounds(311, 18, 220, 20);
		currentCP.setForeground(ColorScheme.getPanelForeground());
		addPanel.add(currentCP);
		
		JButton addUserButton = new JButton(new ImageIcon("images/addUser.png"));
		addUserButton.setToolTipText("Add User");
		addUserButton.setForeground((Color) null);
		addUserButton.setBorderPainted(false);
		addUserButton.setBackground((Color) null);
		addUserButton.setBounds(225, 453, 48, 48);
		addUserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(insertUser()) {
					dispose();
				} else {
					userField.setText("");
					passwordField.setText("");
				}
			}
		});
		addPanel.add(addUserButton);
		
		JButton cancelButton = new JButton(new ImageIcon("images/cancel.png"));
		cancelButton.setToolTipText("Cancel");
		cancelButton.setForeground((Color) null);
		cancelButton.setBorderPainted(false);
		cancelButton.setBackground((Color) null);
		cancelButton.setBounds(375, 453, 48, 48);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		addPanel.add(cancelButton);
		
		JLabel passWordLabel = new JLabel("Password :");
		passWordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passWordLabel.setForeground((Color) null);
		passWordLabel.setBounds(63, 80, 220, 20);
		passWordLabel.setForeground(ColorScheme.getPanelForeground());
		addPanel.add(passWordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(311, 80, 220, 20);
		addPanel.add(passwordField);
	}
	
	private Color previewColor() {
		String query;
		Color panelColor = null;
		ResultSet result;
		
		query = "SELECT Red, Green, Blue FROM CP WHERE Name = '" + CPName + "';";
		try {
			result = LifeStream.getQuery(query, false);
			if(result.next()) {
				panelColor = new Color (result.getInt(1), result.getInt(2), result.getInt(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return panelColor;
	}
	
	private boolean insertUser() {
		PreparedStatement statement;
		Color userColor = colorChooser.getColor();
		ResultSet result, check;
		String query;
		Boolean success = false;
		SSHTool ssh;
				
		try {
			query = "SELECT users.Name, userID FROM users WHERE " +
					"CPID in (SELECT CPID FROM CP WHERE CP.Name = '" + CPName + "')" +
					" AND users.Name = '" + userField.getText() + "';";
			check = LifeStream.getQuery(query, false);
			
			if(userField.getText().compareTo("") == 0){
				JOptionPane.showMessageDialog(new Frame(),
					    "No Name Entered", "Creation Failed", JOptionPane.ERROR_MESSAGE);
			} else if (new String(passwordField.getPassword()).compareTo("") == 0) {
				JOptionPane.showMessageDialog(new Frame(),
					    "No Password Entered", "Creation Failed", JOptionPane.ERROR_MESSAGE);
			} else if(userField.getText().length() > 45) {
				JOptionPane.showMessageDialog(new Frame(),
					    "User Name Must Be Less Than 45 Characters", "Creation Failed", JOptionPane.ERROR_MESSAGE);
			} else if (new String(passwordField.getPassword()).length() > 45) {
				JOptionPane.showMessageDialog(new Frame(),
					    "Password Must Be Less Than 45 Characters", "Creation Failed", JOptionPane.ERROR_MESSAGE);
			} else if(check.next()) {
				JOptionPane.showMessageDialog(new Frame(),
					    "User Already Exists", "Creation Failed", JOptionPane.ERROR_MESSAGE);
			} else {
				LifeStream.closeQuery();
				
				query = "SELECT Name FROM users WHERE Red = " + userColor.getRed() + " AND Green = " +
				        userColor.getGreen() + " AND Blue = " + userColor.getBlue() + ";";
				result = LifeStream.getQuery(query, false);
				if(result.next()) {
					JOptionPane.showMessageDialog(new Frame(),
						    "User Color Already in Use", "Creation Failed", JOptionPane.ERROR_MESSAGE);
				} else {
					LifeStream.closeQuery();
						
					String password = PasswordEncoder.encode(new String(passwordField.getPassword()), userField.getText());
					query = "SELECT CPID FROM CP WHERE Name = '" + CPName +"';";
					result = LifeStream.getQuery(query, false);
					
					if(result.next()) {
						int CPID = result.getInt(1);
						statement = LifeStream.getConnection().prepareStatement("insert into users (Name,CPID,password, Red,Green,Blue)" +
								" values (?,?,?,?,?,?)");
						statement.setString(1, userField.getText());
						statement.setInt(2, CPID);
						statement.setString(3, password);
						statement.setInt(4, userColor.getRed());
						statement.setInt(5, userColor.getGreen());
						statement.setInt(6, userColor.getBlue());
						statement.execute();
						
						ssh = new SSHTool();
						String user = new String(userField.getText() + "-" + CPName);
						ssh.createUser(user, new String(passwordField.getPassword()));
						
						JOptionPane.showMessageDialog(new Frame(),
							    "New User: " + userField.getText() + " created successfully",
							    "Insert Successful",
							    JOptionPane.ERROR_MESSAGE);
					}
					success = true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
}
