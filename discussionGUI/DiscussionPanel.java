package discussionGUI;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JSplitPane;
import database.LifeStream;
import tools.ColorScheme;
import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class DiscussionPanel extends JPanel {

	private static final long serialVersionUID = 491274111116605939L;
	
	private JTree discussionThreads;
	private JTextArea messagePanel;
	private JSplitPane splitPane;
	private JScrollPane scrollPane;
	private JPanel buttonPanel;
	private JButton replyButton;
	private DiscussionTreeNode rootNode;

	/**
	 * Create the panel.
	 */
	public DiscussionPanel() {
		setLayout(new BorderLayout(0, 0));
		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(300);
		
		createTree();
		
		scrollPane = new JScrollPane(discussionThreads);
		splitPane.setLeftComponent(scrollPane);
		
		createMessagePanel();
		splitPane.setRightComponent(messagePanel);
		
		createButtonPanel();
				
		add(splitPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
}
	
	private void createMessagePanel() {
		messagePanel = new JTextArea();
		messagePanel.setLineWrap(true);
		messagePanel.setWrapStyleWord(true);
	}
	
	private void createButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setBackground(ColorScheme.getPanelBackground());
		buttonPanel.setForeground(ColorScheme.getPanelForeground());
		
		JButton discussionButton = new JButton();
		discussionButton.setFocusPainted(false);
		discussionButton.setToolTipText("Add Discussion");
		discussionButton.setIcon(new ImageIcon("images/addThread.png"));
		discussionButton.setForeground((Color) null);
		discussionButton.setBorderPainted(false);
		discussionButton.setBackground((Color) null);
		discussionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddDiscussion discussionPanel = new AddDiscussion();
				discussionPanel.addWindowListener(new WindowListener() {
					public void windowActivated(WindowEvent arg0) {}
					@Override
					public void windowClosed(WindowEvent arg0) {
						updateTree();
				}
					public void windowClosing(WindowEvent arg0) {}
					public void windowDeactivated(WindowEvent arg0) {}
					public void windowDeiconified(WindowEvent arg0) {}
					public void windowIconified(WindowEvent arg0) {}
					public void windowOpened(WindowEvent arg0) {}
				});
				discussionPanel.setVisible(true);
			}
			
		});
		buttonPanel.add(discussionButton);
		
		replyButton = new JButton();
		replyButton.setFocusPainted(false);
		replyButton.setToolTipText("Add Reply");
		replyButton.setIcon(new ImageIcon("images/addReply.png"));
		replyButton.setForeground((Color) null);
		replyButton.setBorderPainted(false);
		replyButton.setBackground((Color) null);
		replyButton.setEnabled(false);
		replyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DiscussionTreeNode node;
				node = (DiscussionTreeNode) discussionThreads.getSelectionPath().getLastPathComponent();
				AddThread threadPanel = new AddThread(node.getNodeID());
				threadPanel.addWindowListener(new WindowListener() {
					public void windowActivated(WindowEvent arg0) {}
					@Override
					public void windowClosed(WindowEvent arg0) {
						updateTree();
					}
					public void windowClosing(WindowEvent arg0) {}
					public void windowDeactivated(WindowEvent arg0) {}
					public void windowDeiconified(WindowEvent arg0) {}
					public void windowIconified(WindowEvent arg0) {}
					public void windowOpened(WindowEvent arg0) {}
				});
				threadPanel.setVisible(true);
			}

		});
		buttonPanel.add(replyButton);
	}
	
	private void updateTree() {
		createTree();
		scrollPane = new JScrollPane(discussionThreads);
		splitPane.setLeftComponent(scrollPane);
		splitPane.setDividerLocation(300);
		messagePanel.setText("");
		replyButton.setEnabled(false);
	}
	
	
	private void createTree(){
		rootNode = new DiscussionTreeNode(0, "Discussion Threads");
		discussionThreads = new JTree(rootNode);
		discussionThreads.setCellRenderer(new DiscussionRenderer());
		discussionThreads.setBackground(ColorScheme.getPanelBackground());
		discussionThreads.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		populateTree();
		discussionThreads.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent event) {
				DiscussionTreeNode node;
				String query;
				ResultSet message;
				
				try {
					node = (DiscussionTreeNode) event.getNewLeadSelectionPath().getLastPathComponent();
					if(node.getNodeID() == 0) {
						replyButton.setEnabled(false);
					} else {
						replyButton.setEnabled(true);
					}
					query = "SELECT message FROM discussions WHERE messageID = " + node.getNodeID();
					message = LifeStream.getQuery(query, false);
					if(message.next()) {
						messagePanel.setText(message.getString(1));
					} else {
						messagePanel.setText("");
					}
				} catch (Exception e) {}
			}
		});
		discussionThreads.expandPath(new TreePath((TreeNode) rootNode));
		discussionThreads.updateUI();
	}
	
	private void populateTree() {
		int parentID;
		String query;
		ResultSet threads;
		TreeModel model;
		DiscussionTreeNode node;
	
		query = "SELECT parent, messageID, topic, date FROM discussions ORDER BY parent";
		
		try {
			threads = LifeStream.getQuery(query, false);
			while(threads.next()){
				model = discussionThreads.getModel();
				parentID = threads.getInt(1);
				node = findParent(model, parentID);
				node.add(new DiscussionTreeNode(threads.getInt(2), threads.getString(3) + " - " + threads.getTimestamp(4)));
			}
			LifeStream.closeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private DiscussionTreeNode findParent(TreeModel model, int parentID) {
		DiscussionTreeNode node = null;
		DiscussionTreeNode root = (DiscussionTreeNode) model.getRoot();
		
		Enumeration<?> treeEnumeration = root.postorderEnumeration();
		
		while(treeEnumeration.hasMoreElements()) {
			node = (DiscussionTreeNode) treeEnumeration.nextElement();
			if(node.getNodeID() == parentID) {
				return node;
			}
		}
		return null;
	}
}
