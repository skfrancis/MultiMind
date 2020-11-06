package scrumGUI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import tools.ColorScheme;
import tools.SessionTracker;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.border.EtchedBorder;
import database.LifeStream;

public class AddReport extends JFrame {
	
	private static final long serialVersionUID = -6325514755953547933L;
	private JPanel addPanel;
	private JTextArea reportText;

	/**
	 * Create the frame.
	 */
	public AddReport() {
		setTitle("Add Scrum Report");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		Point middle = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setLocation(new Point((int)middle.getX() - 250, (int)middle.getY() - 200));
		getContentPane().setLayout(null);
		setBackground(ColorScheme.getPanelBackground());
		setForeground(ColorScheme.getPanelForeground());
		addPanel = buildAddPanel();
		getContentPane().add(addPanel);
	}
	
	private JPanel buildAddPanel() {
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
			
		results = populateReportFrame();
		try {
			results.next();
			userNameLabel.setText(results.getString(1));
			CPIDLabel.setText(results.getString(2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mainPanel.add(userNameLabel);
		mainPanel.add(CPIDLabel);
		
		reportText = new JTextArea();
		reportText.setLineWrap(true);
		reportText.setWrapStyleWord(true);
		reportText.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		reportText.setBounds(10, 73, 465, 220);
		mainPanel.add(reportText);
		
		JButton addButton = new JButton();
		addButton.setToolTipText("Add Scrum Report");
		addButton.setIcon(new ImageIcon("images/addscrum.png"));
		addButton.setForeground((Color) null);
		addButton.setBorderPainted(false);
		addButton.setBackground((Color) null);
		addButton.setBounds(135, 303, 48, 48);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				postReport();
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
	
	private ResultSet populateReportFrame(){
		ResultSet results;
		String query = "SELECT users.Name, CP.Name FROM CP, users WHERE users.CPID = CP.CPID AND userID =" + SessionTracker.getUserID();
		try {
			results = LifeStream.getQuery(query, false);
			return results;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void postReport(){
		String query = "INSERT INTO reports (userID, text) VALUES (" + SessionTracker.getUserID()
		               + ", \"" + reportText.getText() + "\")";
		try {
			LifeStream.setQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		SessionTracker.scrumReportComplete();
		JOptionPane.showMessageDialog(new Frame(),
			    "Scrum Report created successfully",
			    "Post Successful",
			    JOptionPane.ERROR_MESSAGE);
	}
}
