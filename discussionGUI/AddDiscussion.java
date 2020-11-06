package discussionGUI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tools.ColorScheme;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.border.EtchedBorder;

import database.LifeStream;

public class AddDiscussion extends JFrame {

	private static final long serialVersionUID = 5606051797163985470L;
	private JPanel discussionPanel;
	private JTextField subjectField;
	private JTextArea messageArea;

	/**
	 * Create the frame.
	 */
	public AddDiscussion() {
		setTitle("Add Discussion");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 460, 400);
		Point middle = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setLocation(new Point((int)middle.getX() - 230, (int)middle.getY() - 200));
		discussionPanel = new JPanel();
		discussionPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		discussionPanel.setLayout(null);
		discussionPanel.setBackground(ColorScheme.getPanelBackground());
		discussionPanel.setForeground(ColorScheme.getPanelForeground());
		discussionFrame();
		setContentPane(discussionPanel);
		
	}
	
	private void discussionFrame() {

		JLabel subjectLabel = new JLabel("Subject :");
		subjectLabel.setForeground(ColorScheme.getPanelForeground());
		subjectLabel.setBounds(20, 21, 80, 20);
		discussionPanel.add(subjectLabel);
		
		JLabel messageLabel = new JLabel("Message :");
		messageLabel.setForeground(ColorScheme.getPanelForeground());
		messageLabel.setBounds(20, 42, 80, 20);
		discussionPanel.add(messageLabel);
		
		subjectField = new JTextField();
		subjectField.setBounds(114, 21, 309, 20);
		discussionPanel.add(subjectField);
		subjectField.setColumns(10);
		
		messageArea = new JTextArea();
		messageArea.setWrapStyleWord(true);
		messageArea.setLineWrap(true);
		messageArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		messageArea.setBounds(20, 66, 403, 213);
		discussionPanel.add(messageArea);
		
		JButton addButton = new JButton();
		addButton.setToolTipText("Add Discussion");
		addButton.setForeground((Color) null);
		addButton.setBorderPainted(false);
		addButton.setBackground((Color) null);
		addButton.setBounds(114, 290, 48, 48);
		addButton.setIcon(new ImageIcon("images/addThread.png"));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String query = "INSERT INTO discussions (parent, topic, message) VALUES (0, '" + subjectField.getText() +
						"', '" + messageArea.getText() + "')";
				try {
					LifeStream.setQuery(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(new Frame(),
					    "Discussion created successfully",
					    "Post Successful",
					    JOptionPane.ERROR_MESSAGE);
				dispose();
			}
		});
		discussionPanel.add(addButton);
		
		JButton cancelButton = new JButton();
		cancelButton.setToolTipText("Cancel");
		cancelButton.setForeground((Color) null);
		cancelButton.setBorderPainted(false);
		cancelButton.setBackground((Color) null);
		cancelButton.setBounds(266, 290, 48, 48);
		cancelButton.setIcon(new ImageIcon("images/cancel.png"));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		discussionPanel.add(cancelButton);
	}
}
