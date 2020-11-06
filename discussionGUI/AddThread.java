package discussionGUI;

import java.awt.Color;
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
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import database.LifeStream;
import tools.ColorScheme;

public class AddThread extends JFrame {

	private static final long serialVersionUID = -3319493426164465305L;
	private JPanel threadPanel;
	private JLabel topicLabel;
	private JTextArea messageArea;
	private int parentID;

	/**
	 * Create the frame.
	 */
	public AddThread(int parent) {
		parentID = parent;
		setTitle("Add Reply");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 460, 400);
		Point middle = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setLocation(new Point((int)middle.getX() - 230, (int)middle.getY() - 200));
		threadPanel = new JPanel();
		threadPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		threadPanel.setLayout(null);
		threadPanel.setBackground(ColorScheme.getPanelBackground());
		threadPanel.setForeground(ColorScheme.getPanelForeground());
		threadFrame();
		setContentPane(threadPanel);
	}
	
	private void threadFrame() {
		String query; 
		ResultSet topic;
		
		
		JLabel subjectLabel = new JLabel("Subject :");
		subjectLabel.setForeground(ColorScheme.getPanelForeground());
		subjectLabel.setBounds(20, 21, 60, 20);
		threadPanel.add(subjectLabel);
		
		topicLabel = new JLabel();
		topicLabel.setBounds(90, 21, 333, 20);
		topicLabel.setForeground(ColorScheme.getPanelForeground());
		query = "select topic from discussions where messageID =" + parentID;
		try {
			topic = LifeStream.getQuery(query, false);
			topic.next();
			topicLabel.setText("RE: " + topic.getString(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		threadPanel.add(topicLabel);
	
		JLabel messageLabel = new JLabel("Message :");
		messageLabel.setForeground(ColorScheme.getPanelForeground());
		messageLabel.setBounds(20, 42, 80, 20);
		threadPanel.add(messageLabel);
		
		messageArea = new JTextArea();
		messageArea.setWrapStyleWord(true);
		messageArea.setLineWrap(true);
		messageArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		messageArea.setBounds(20, 66, 403, 213);
		threadPanel.add(messageArea);
		
		JButton addButton = new JButton();
		addButton.setToolTipText("Add Reply");
		addButton.setForeground((Color) null);
		addButton.setBorderPainted(false);
		addButton.setBackground((Color) null);
		addButton.setBounds(114, 290, 48, 48);
		addButton.setIcon(new ImageIcon("images/addReply.png"));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String query = "INSERT INTO discussions (parent, topic, message) VALUES ('" + parentID + "', '" + topicLabel.getText() +
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

		threadPanel.add(addButton);
		
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
		threadPanel.add(cancelButton);
	}
}
