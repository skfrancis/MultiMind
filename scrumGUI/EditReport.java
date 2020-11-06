package scrumGUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import database.LifeStream;

import tools.ColorScheme;
import tools.SessionTracker;

public class EditReport extends JFrame {

	private static final long serialVersionUID = 7362269546908996666L;
	private JPanel editPanel;
	private JTextArea reportText;
	private String editReportID;

	/**
	 * Create the frame.
	 */
	public EditReport(String reportID) {
		editReportID = reportID;
		setTitle("Edit Scrum Report");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		Point middle = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setLocation(new Point((int)middle.getX() - 250, (int)middle.getY() - 200));
		getContentPane().setLayout(null);
		setBackground(ColorScheme.getPanelBackground());
		setForeground(ColorScheme.getPanelForeground());
		editPanel = buildEditPanel();
		getContentPane().add(editPanel);
	}
	
	private JPanel buildEditPanel() {
		ResultSet results;
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 485, 365);
		mainPanel.setBackground(ColorScheme.getPanelBackground());
		mainPanel.setForeground(ColorScheme.getPanelForeground());
		mainPanel.setLayout(null);
		JLabel userLabel = new JLabel("User Name :");
		userLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userLabel.setBounds(33, 11, 150, 20);
		userLabel.setForeground(ColorScheme.getPanelForeground());
		mainPanel.add(userLabel);
		
		JLabel cpLabel = new JLabel("Composite Personae :");
		cpLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		cpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cpLabel.setBounds(33, 42, 150, 20);
		cpLabel.setForeground(ColorScheme.getPanelForeground());
		mainPanel.add(cpLabel);
		
		JLabel userNameLabel = new JLabel();
		userNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userNameLabel.setBounds(288, 14, 150, 20);
		userNameLabel.setForeground(ColorScheme.getPanelForeground());
				
		JLabel CPIDLabel = new JLabel();
		CPIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
		CPIDLabel.setBounds(288, 42, 150, 20);
		CPIDLabel.setForeground(ColorScheme.getPanelForeground());
		
		reportText = new JTextArea();
		reportText.setWrapStyleWord(true);
		reportText.setLineWrap(true);
		reportText.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		reportText.setBounds(10, 73, 465, 220);
		
		results = populateEditFrame();
		try {
			results.next();
			userNameLabel.setText(results.getString(1));
			CPIDLabel.setText(results.getString(2));
			reportText.setText(results.getString(3));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mainPanel.add(userNameLabel);
		mainPanel.add(CPIDLabel);
		mainPanel.add(reportText);
		
		JButton addButton = new JButton();
		addButton.setToolTipText("Edit Scrum Report");
		addButton.setIcon(new ImageIcon("images/editscrum.png"));
		addButton.setForeground((Color) null);
		addButton.setBorderPainted(false);
		addButton.setBackground((Color) null);
		addButton.setBounds(135, 303, 48, 48);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editReport();
				dispose();
			}
		});
		mainPanel.add(addButton);
		
		JButton cancelButton = new JButton();
		cancelButton.setToolTipText("Cancel");
		cancelButton.setIcon(new ImageIcon("images/cancel.png"));
		cancelButton.setForeground((Color) null);
		cancelButton.setBorderPainted(false);
		cancelButton.setBackground((Color) null);
		cancelButton.setBounds(288, 302, 48, 48);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		mainPanel.add(cancelButton);
		
		return mainPanel;
	}
	
	private ResultSet populateEditFrame(){
		ResultSet results;
		String query = "SELECT users.Name, CP.Name, text FROM users, CP, reports WHERE users.CPID = CP.CPID AND " +
				   "users.userID =" + SessionTracker.getUserID() + " AND reportID =" + editReportID;
		try {
			results = LifeStream.getQuery(query, false);
			return results;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void editReport(){
		String query = "UPDATE reports SET text =\"" + reportText.getText() +"\"" +
			"WHERE reportID="+ editReportID;
		try {
			LifeStream.setQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(new Frame(),
			    "Scrum Report edited successfully",
			    "Post Successful",
			    JOptionPane.ERROR_MESSAGE);
	}
}
