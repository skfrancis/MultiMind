package adminGUI;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import database.LifeStream;
import tools.ColorScheme;

public class CPAdd extends JFrame {

	private static final long serialVersionUID = 158640365756388444L;
	private JPanel addPanel;
	private JTextField nameField;
	private JColorChooser colorChooser;
	/**
	 * Create the frame.
	 */
	public CPAdd() {
		setTitle("Add Composite Personae");
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
		JLabel CPNameLabel = new JLabel("Composite Personae Name :");
		CPNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		CPNameLabel.setBounds(53, 35, 220, 20);
		CPNameLabel.setForeground(ColorScheme.getPanelForeground());
		addPanel.add(CPNameLabel);
		
		nameField = new JTextField();
		nameField.setBounds(312, 35, 220, 20);
		addPanel.add(nameField);
		nameField.setColumns(10);
		
		colorChooser = new JColorChooser();
		colorChooser.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		colorChooser.setLocation(10, 84);
		colorChooser.setSize(614, 326);
		addPanel.add(colorChooser);
		setContentPane(addPanel);
		
		JButton addButton = new JButton();
		addButton.setIcon(new ImageIcon("images/addCP.png"));
		addButton.setToolTipText("Add Composite Personae");
		addButton.setForeground((Color) null);
		addButton.setBorderPainted(false);
		addButton.setBackground((Color) null);
		addButton.setBounds(225, 435, 48, 48);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(insertCP()) {
					dispose();
				} else {
					nameField.setText("");
				}
			}
		});
		addPanel.add(addButton);
		
		JButton cancelButton = new JButton();
		cancelButton.setIcon(new ImageIcon("images/cancel.png"));
		cancelButton.setToolTipText("Cancel");
		cancelButton.setForeground((Color) null);
		cancelButton.setBorderPainted(false);
		cancelButton.setBackground((Color) null);
		cancelButton.setBounds(375, 435, 48, 48);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		addPanel.add(cancelButton);
	}
	
	private boolean insertCP() {
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
					    "No Name Entered", "Creation Failed", JOptionPane.ERROR_MESSAGE);
			} else if(check.next()) {
				JOptionPane.showMessageDialog(new Frame(),
					    "CP Name or Color Already Exists", "Creation Failed", JOptionPane.ERROR_MESSAGE);
			} else if(nameField.getText().length() > 45) {
				JOptionPane.showMessageDialog(new Frame(),
					    "CP Name Must Be Less Than 45 Characters", "Creation Failed", JOptionPane.ERROR_MESSAGE);
			} else {
				statement = LifeStream.getConnection().prepareStatement("INSERT INTO CP (CPID, Name,Red,Green,Blue)" +
															" VALUES (?,?,?,?,?)");
				statement.setInt(1, CPID);
				statement.setString(2, nameField.getText());
				statement.setInt(3, cpColor.getRed());
				statement.setInt(4, cpColor.getGreen());
				statement.setInt(5, cpColor.getBlue());
				statement.execute();
				JOptionPane.showMessageDialog(new Frame(),
					    "New CPID: " + CPID +" Name: " + nameField.getText() + " created successfully",
					    "Insert Successful",
					    JOptionPane.ERROR_MESSAGE);
				success = true;
			}
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return success;
	}	
}
