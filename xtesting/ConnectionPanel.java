package xtesting;

//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//import javax.swing.JSplitPane;
//import javax.swing.JButton;
//
//import subversion.SVNConnection;
//import subversion.SVNConnector;
//import subversion.ViewRepository;
//import tools.ColorScheme;
//import java.awt.Color;
//import java.awt.Frame;
//import java.awt.GraphicsEnvironment;
//import java.awt.Point;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import javax.swing.DefaultListModel;
//import javax.swing.JList;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.ListSelectionModel;
//import javax.swing.SwingConstants;
//import javax.swing.JTextField;
//import javax.swing.JPasswordField;
//import org.tmatesoft.svn.core.SVNException;

public class ConnectionPanel {
//
//	private static final long serialVersionUID = -6913405335826954297L;
//	private JPanel connectionsPanel;
//	private JTextField nameField;
//	private JTextField urlField;
//	private JTextField userField;
//	private JPasswordField passwordField;
//	private JList<SVNConnection> connectionsList;
//	private DefaultListModel<SVNConnection> listModel;
//	private JSplitPane splitPane;
//	private JButton editButton;
//	private JButton deleteButton;
//
//	/**
//	 * Create the frame.
//	 */
//	public ConnectionPanel() {
//		setTitle("Connections");
//		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		setBounds(100, 100, 550, 400);
//		Point middle = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
//		setLocation(new Point((int)middle.getX() - 275, (int)middle.getY() - 125));
//		connectionsPanel = new JPanel();
//		connectionsPanel.setBackground(ColorScheme.getPanelBackground());
//		connectionsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
//		connectionsPanel.setLayout(null);
//				
//		splitPane = new JSplitPane();
//		splitPane.setDividerSize(2);
//		splitPane.setBounds(10, 30, 515, 250);
//		splitPane.setDividerLocation(325);
//		
//		buildOptions();
//		buildList();
//		buildButtons();
//		
//		connectionsPanel.add(splitPane);
//		setContentPane(connectionsPanel);
//	}
//	
//	private void buildOptions() {
//		JLabel optionsLabel = new JLabel("Connection Options");
//		optionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		optionsLabel.setBounds(10, 8, 325, 20);
//		optionsLabel.setForeground(ColorScheme.getPanelForeground());
//		connectionsPanel.add(optionsLabel);
//		
//		JPanel optionsPanel = new JPanel();
//		optionsPanel.setLayout(null);
//		optionsPanel.setBackground(ColorScheme.getPanelBackground());
//		
//		JLabel nameLabel = new JLabel("Name :");
//		nameLabel.setBounds(10, 15, 79, 20);
//		nameLabel.setForeground(ColorScheme.getPanelForeground());
//		optionsPanel.add(nameLabel);
//		
//		nameField = new JTextField();
//		nameField.setBounds(90, 15, 210, 20);
//		nameField.setColumns(10);
//		optionsPanel.add(nameField);
//		
//		JLabel urlLabel = new JLabel("URL :");
//		urlLabel.setBounds(10, 45, 79, 20);
//		urlLabel.setForeground(ColorScheme.getPanelForeground());
//		optionsPanel.add(urlLabel);
//		
//		urlField = new JTextField();
//		urlField.setBounds(90, 45, 210, 20);
//		urlField.setColumns(10);
//		optionsPanel.add(urlField);
//		
//		JLabel userLabel = new JLabel("User :");
//		userLabel.setBounds(10, 75, 79, 20);
//		userLabel.setForeground(ColorScheme.getPanelForeground());
//		optionsPanel.add(userLabel);
//		
//		userField = new JTextField();
//		userField.setBounds(90, 75, 210, 20);
//		userField.setColumns(10);
//		optionsPanel.add(userField);
//		
//		JLabel passwordLabel = new JLabel("Password :");
//		passwordLabel.setBounds(10, 105, 79, 20);
//		passwordLabel.setForeground(ColorScheme.getPanelForeground());
//		optionsPanel.add(passwordLabel);
//		
//		
//		passwordField = new JPasswordField();
//		passwordField.setBounds(90, 105, 210, 20);
//		optionsPanel.add(passwordField);
//		
//		JButton addButton = new JButton();
//		addButton.setToolTipText("Add Connection");
//		addButton.setForeground((Color) null);
//		addButton.setBorderPainted(false);
//		addButton.setBackground((Color) null);
//		addButton.setBounds(19, 160, 48, 48);
//		addButton.setIcon(new ImageIcon("images/connectAdd.png"));
//		addButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				SVNConnection connection = new SVNConnection(nameField.getText(), urlField.getText(), userField.getText(), new String(passwordField.getPassword()));
//				listModel.addElement(connection);
//			}
//		});
//		optionsPanel.add(addButton);
//		
//		editButton = new JButton();
//		editButton.setToolTipText("Edit Connection");
//		editButton.setForeground((Color) null);
//		editButton.setBorderPainted(false);
//		editButton.setBackground((Color) null);
//		editButton.setBounds(90, 160, 48, 48);
//		editButton.setIcon(new ImageIcon("images/connectEdit.png"));
//		editButton.setEnabled(false);
//		editButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				SVNConnection connection = new SVNConnection(nameField.getText(), urlField.getText(), userField.getText(), new String(passwordField.getPassword()));
//				listModel.setElementAt(connection, connectionsList.getSelectedIndex());				
//			}
//			
//		});
//		optionsPanel.add(editButton);
//		
//		deleteButton = new JButton();
//		deleteButton.setToolTipText("Delete Connection");
//		deleteButton.setForeground((Color) null);
//		deleteButton.setBorderPainted(false);
//		deleteButton.setBackground((Color) null);
//		deleteButton.setBounds(161, 160, 48, 48);
//		deleteButton.setIcon(new ImageIcon("images/connectDelete.png"));
//		deleteButton.setEnabled(false);
//		deleteButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int option = JOptionPane.showConfirmDialog(new Frame(), "Are you sure you want to Delete " + 
//						listModel.getElementAt(connectionsList.getSelectedIndex()).toString() + " ?",
//					    "Delete Connection", JOptionPane.YES_NO_OPTION);
//				 if (option == JOptionPane.YES_OPTION) {
//					 listModel.remove(connectionsList.getSelectedIndex());
//				 }
//			}
//		});
//		optionsPanel.add(deleteButton);
//		
//		splitPane.setLeftComponent(optionsPanel);
//		
//		JButton testButton = new JButton();
//		testButton.setToolTipText("Test Connection");
//		testButton.setForeground((Color) null);
//		testButton.setEnabled(true);
//		testButton.setBorderPainted(false);
//		testButton.setBackground((Color) null);
//		testButton.setBounds(232, 160, 48, 48);
//		testButton.setIcon(new ImageIcon("images/connectTest.png"));
//		testButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				SVNConnection connection = new SVNConnection(nameField.getText(), urlField.getText(), userField.getText(), new String(passwordField.getPassword()));
//				ViewRepository testPanel;
//				try {
//					testPanel = new ViewRepository(connection);
//					testPanel.setVisible(true);
//				} catch (SVNException svnEvent) {
//					JOptionPane.showMessageDialog(new Frame(),
//						    "Connection Test Failed", "Test Failed", JOptionPane.ERROR_MESSAGE);
//				}
//				
//			}
//		});
//		optionsPanel.add(testButton);
//	}
//	
//	private void buildList() {
//		JLabel listLabel = new JLabel("Connection List");
//		listLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		listLabel.setBounds(340, 5, 185, 20);
//		listLabel.setForeground(ColorScheme.getPanelForeground());
//		connectionsPanel.add(listLabel);
//		
//		loadList();
//		
//		connectionsList = new JList<SVNConnection>();
//		connectionsList.setModel(listModel);
//		connectionsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//		connectionsList.addListSelectionListener(new ListSelectionListener(){
//			@Override
//			public void valueChanged(ListSelectionEvent arg0) {
//				if (connectionsList.getSelectedIndex() == -1) {
//			        editButton.setEnabled(false);
//			        deleteButton.setEnabled(false);
//			        nameField.setText("");
//			        urlField.setText("");
//			        userField.setText("");
//			        passwordField.setText("");
//		        } else {
//			        SVNConnection node = listModel.getElementAt(connectionsList.getSelectedIndex());
//		        	editButton.setEnabled(true);
//			        deleteButton.setEnabled(true);
//			        nameField.setText(node.getHostName());
//			        urlField.setText(node.getHostURL());
//			        userField.setText(node.getUserName());
//			        passwordField.setText(node.getPassword());
//			    }
//			}
//		});
//		splitPane.setRightComponent(connectionsList);
//	}
//	
//	private void buildButtons() {
//		JButton loadButton = new JButton();
//		loadButton.setToolTipText("Load Connection");
//		loadButton.setForeground((Color) null);
//		loadButton.setBorderPainted(false);
//		loadButton.setBackground((Color) null);
//		loadButton.setBounds(170, 300, 48, 48);
//		loadButton.setIcon(new ImageIcon("images/connectLoad.png"));
//		loadButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				try {
//					SVNConnection selectedNode = listModel.getElementAt(connectionsList.getSelectedIndex());
//					SVNConnector.setConnection(selectedNode);
//					saveList();
//					dispose();
//				} catch (SVNException svnEvent) {
//					svnEvent.printStackTrace();
//
//				}
//			}
//		});
//		connectionsPanel.add(loadButton);
//		
//		JButton cancelButton = new JButton();
//		cancelButton.setToolTipText("Cancel");
//		cancelButton.setIcon(new ImageIcon("images/cancel.png"));
//		cancelButton.setForeground((Color) null);
//		cancelButton.setBorderPainted(false);
//		cancelButton.setBackground((Color) null);
//		cancelButton.setBounds(320, 300, 48, 48);
//		cancelButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent arg0) {
//            	try {
//            		SVNConnector.setConnection(null);
//				} catch (SVNException svnEvent) {
//					svnEvent.printStackTrace();
//				}
//            	saveList();
//                dispose();
//            	
//            }
//        });
//		connectionsPanel.add(cancelButton);
//	}
//	
//	@SuppressWarnings("unchecked")
//	private void loadList() {
//		File config = new File("connections.cfg");
//		FileInputStream inputStream;
//		
//		if(config.exists()) {
//			try {
//				inputStream = new FileInputStream(config);
//				ObjectInputStream in = new ObjectInputStream(inputStream);
//				listModel = (DefaultListModel<SVNConnection>) in.readObject();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			listModel = new DefaultListModel<SVNConnection>();
//		}
//		
//	}
//	
//	private void saveList() {
//		File config = new File("connections.cfg");
//		FileOutputStream fileStream = null;
//    	ObjectOutputStream outStream = null;
//    	
//    	try {
//			fileStream = new FileOutputStream(config);
//			outStream = new ObjectOutputStream(fileStream);
//			outStream.writeObject(listModel);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//    	
//		
//	}
}
