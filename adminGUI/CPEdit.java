package adminGUI;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.JCheckBox;
import database.LifeStream;


public class CPEdit extends JFrame {

	private static final long serialVersionUID = 7562976668463769092L;
	private JPanel editPanel;
	private JTextField nameField;
	private JCheckBox colorCheckBox;
	private JCheckBox nameCheckBox;
	private JColorChooser colorChooser;
	private String CPName;

	/**
	 * Create the frame.
	 */
	public CPEdit(String name) {
		CPName = name;
		setTitle("Edit Composite Personae");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 650, 575);
		Point middle = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setLocation(new Point((int)middle.getX() - 325, (int)middle.getY() - 275));
		editPanel = new JPanel();
		editPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		editPanel.setLayout(null);
		editPanel.setBackground(ColorScheme.getPanelBackground());
		editFrame();
	}
	
	private void editFrame() {
		JLabel oldCPNameLabel = new JLabel(CPName);
		oldCPNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		oldCPNameLabel.setBounds(312, 22, 220, 20);
		oldCPNameLabel.setForeground(ColorScheme.getPanelBackground());
		oldCPNameLabel.setForeground(ColorScheme.getPanelForeground());
		editPanel.add(oldCPNameLabel);
		
		JLabel oldCPLabel = new JLabel("Old Composite Personae Name :");
		oldCPLabel.setHorizontalAlignment(SwingConstants.CENTER);
		oldCPLabel.setBounds(53, 22, 220, 20);
		oldCPLabel.setForeground(ColorScheme.getPanelBackground());
		oldCPLabel.setForeground(ColorScheme.getPanelForeground());
		editPanel.add(oldCPLabel);
		
		JLabel newCPLabel = new JLabel("New Composite Personae Name :");
		newCPLabel.setHorizontalAlignment(SwingConstants.CENTER);
		newCPLabel.setBounds(53, 53, 220, 20);
		newCPLabel.setForeground(ColorScheme.getPanelForeground());
		editPanel.add(newCPLabel);
				
		nameField = new JTextField();
		nameField.setBounds(312, 53, 220, 20);
		editPanel.add(nameField);
		nameField.setEnabled(false);
		nameField.setColumns(10);
		
		nameCheckBox = new JCheckBox("Use Original Name");
		nameCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		nameCheckBox.setBounds(63, 80, 220, 23);
		nameCheckBox.setForeground(ColorScheme.getPanelForeground());
		nameCheckBox.setBackground(ColorScheme.getPanelBackground());
		nameCheckBox.setSelected(true);
		nameCheckBox.setFocusPainted(false);
		nameCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					colorCheckBox.setSelected(false);
					nameField.setEnabled(false);
					colorChooser.setEnabled(true);
				} else {
					nameField.setEnabled(true);
				}
			}
		});
		editPanel.add(nameCheckBox);
		
		colorCheckBox = new JCheckBox("Use Original Color");
		colorCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		colorCheckBox.setBounds(312, 80, 220, 23);
		colorCheckBox.setForeground(ColorScheme.getPanelForeground());
		colorCheckBox.setBackground(ColorScheme.getPanelBackground());
		colorCheckBox.setSelected(false);
		colorCheckBox.setFocusPainted(false);
		colorCheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					nameCheckBox.setSelected(false);
					colorChooser.setEnabled(false);
					nameField.setEnabled(true);
				} else {
					colorChooser.setEnabled(true);
				}
			}
		});
		editPanel.add(colorCheckBox);
		
		colorChooser = new JColorChooser();
		colorChooser.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		colorChooser.setLocation(10, 110);
		colorChooser.setSize(614, 326);
		editPanel.add(colorChooser);
		setContentPane(editPanel);
		
		JButton addButton = new JButton();
		addButton.setIcon(new ImageIcon("images/editCP.png"));
		addButton.setToolTipText("Edit Composite Personae");
		addButton.setForeground((Color) null);
		addButton.setBorderPainted(false);
		addButton.setBackground((Color) null);
		addButton.setBounds(225, 461, 48, 48);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean modified = false;
				if(colorCheckBox.isSelected()) {
					modified = modifyName();
				} else if(nameCheckBox.isSelected()) {
					modified = modifyColor();
				} else {
					modified = modifyAll();
				}
				if(modified) {
					dispose();
				} else {
					nameField.setText("");
				}
			}
		});
		editPanel.add(addButton);
		
		JButton cancelButton = new JButton();
		cancelButton.setIcon(new ImageIcon("images/cancel.png"));
		cancelButton.setToolTipText("Cancel");
		cancelButton.setForeground((Color) null);
		cancelButton.setBorderPainted(false);
		cancelButton.setBackground((Color) null);
		cancelButton.setBounds(375, 461, 48, 48);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		editPanel.add(cancelButton);
	}
	
	private boolean modifyName() {
		PreparedStatement statement;
		ResultSet check;
		String query;
		Boolean success = false;
		
		query = "SELECT Name FROM CP WHERE Name = '" + nameField.getText() + "';";
		
		try {
			check = LifeStream.getQuery(query, false);
			if(nameField.getText().compareTo("") == 0){
				JOptionPane.showMessageDialog(new Frame(),
					    "No Name Entered", "Modification Failed", JOptionPane.ERROR_MESSAGE);
			} else if(check.next()) {
				JOptionPane.showMessageDialog(new Frame(),
					    "CP Already Exists", "Modification Failed", JOptionPane.ERROR_MESSAGE);
			} else if(nameField.getText().length() > 45) {
				JOptionPane.showMessageDialog(new Frame(),
					    "CP Name Must Be Less Than 45 Characters", "Modification Failed", JOptionPane.ERROR_MESSAGE);
			} else {
				statement = LifeStream.getConnection().prepareStatement("UPDATE CP SET Name = ? WHERE Name = ?;");
				statement.setString(1, nameField.getText());
				statement.setString(2, CPName);
				statement.execute();
				JOptionPane.showMessageDialog(new Frame(),
					    "Updated CP: " + CPName +" To Name: " + nameField.getText() + " successfully",
					    "Update Successful",
					    JOptionPane.INFORMATION_MESSAGE);
				success = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	
	private boolean modifyColor() {
		PreparedStatement statement;
		ResultSet check;
		String query;
		Color cpColor = colorChooser.getColor();
		Boolean success = false;
		
		int CPID = (int) ((cpColor.getRed() * Math.pow(10,6)) + (cpColor.getGreen() * Math.pow(10, 3) + cpColor.getBlue()));
		
		query = "SELECT CPID FROM CP WHERE CPID = " + CPID + ";";
		try {
			check = LifeStream.getQuery(query, false);
			if(check.next()) {
				JOptionPane.showMessageDialog(new Frame(),
					    "CP Color Already Exists", "Modification Failed", JOptionPane.ERROR_MESSAGE);
			} else {
				statement = LifeStream.getConnection().prepareStatement("UPDATE CP SET CPID = ?, Red = ?, Green = ?, Blue = ? WHERE Name = ?;");
				statement.setInt(1, CPID);
				statement.setInt(2, cpColor.getRed());
				statement.setInt(3, cpColor.getGreen());
				statement.setInt(4, cpColor.getBlue());
				statement.setString(5, CPName);
				statement.execute();
				JOptionPane.showMessageDialog(new Frame(),
					    "Updated Color of CP: " + CPName + " successfully",
					    "Update Successful",
					    JOptionPane.INFORMATION_MESSAGE);
				success = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	
	private boolean modifyAll() {
		PreparedStatement statement;
		Color cpColor = colorChooser.getColor();
		ResultSet check;
		Boolean success = false;
		
		int CPID = (int) ((cpColor.getRed() * Math.pow(10,6)) + (cpColor.getGreen() * Math.pow(10, 3) + cpColor.getBlue()));
		String query = "SELECT Name,CPID FROM CP WHERE Name = '" + nameField.getText() + "' OR CPID = '" + CPID + "'";
		
		try {
			check = LifeStream.getQuery(query, false);
			
			if(nameField.getText().compareTo("") == 0){
				JOptionPane.showMessageDialog(new Frame(),
					    "No Name Entered", "Modification Failed", JOptionPane.ERROR_MESSAGE);
			} else if(check.next()) {
				JOptionPane.showMessageDialog(new Frame(),
					    "CP Name or Color Already Exists", "Modification Failed", JOptionPane.ERROR_MESSAGE);
			} else if(nameField.getText().length() > 45) {
				JOptionPane.showMessageDialog(new Frame(),
					    "CP Name Must Be Less Than 45 Characters", "Modification Failed", JOptionPane.ERROR_MESSAGE);
			} else {
				statement = LifeStream.getConnection().prepareStatement("UPDATE CP SET CPID = ?, Name = ?, Red = ?, Green = ?, Blue = ? WHERE Name = ?;");
				statement.setInt(1, CPID);
				statement.setString(2, nameField.getText());
				statement.setInt(3, cpColor.getRed());
				statement.setInt(4, cpColor.getGreen());
				statement.setInt(5, cpColor.getBlue());
				statement.setString(6, CPName);
				statement.execute();
				JOptionPane.showMessageDialog(new Frame(),
					    "Updated CPID to : " + CPID +"and Name to: " + nameField.getText() + " successfully",
					    "Insert Successful",
					    JOptionPane.INFORMATION_MESSAGE);
				success = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
}
