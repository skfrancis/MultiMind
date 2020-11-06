package xtesting;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Document;
import javax.swing.tree.DefaultMutableTreeNode;

import database.LifeStream;

import tools.ColorScheme;
import tools.DocumentEditor;
import tools.SessionTracker;



class Selector implements TreeSelectionListener {
	
	//change to false when done
	private boolean debug = true;
	private DocumentEditor editor;
	private JSplitPane splitPane;
	private JTree projectTree;
	private JScrollPane editorView;
	
	//import the editor and split pane to modify view correctly
	public Selector(DocumentEditor editor, JSplitPane splitPane, JTree projectTree, JScrollPane editorView){
		this.editor = editor;
		this.splitPane = splitPane;
		this.projectTree = projectTree;
		this.editorView = editorView;
	}

	public void valueChanged(TreeSelectionEvent event){
		
		//panel for the buttons
		final JPanel displayPane = new JPanel();
		displayPane.setBackground(ColorScheme.getPanelBackground());
		displayPane.setLayout(new GridLayout(4,1));
		JPanel fileOptions = new JPanel();
		fileOptions.setBackground(ColorScheme.getPanelBackground());
		fileOptions.setLayout(new BoxLayout(fileOptions, BoxLayout.LINE_AXIS));
		
		//import the data from the Tree about current selection
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode)event.getNewLeadSelectionPath().getLastPathComponent();
		final boolean isFile = !node.getAllowsChildren();
		
		JButton middleVariableButton;
		if(isFile){
			middleVariableButton = new JButton(new ImageIcon("images/evolve.png"));
		} else {
			middleVariableButton = new JButton(new ImageIcon("images/addfile.png"));
		}
		middleVariableButton.setBackground(ColorScheme.getPanelBackground());
		middleVariableButton.setForeground(ColorScheme.getPanelForeground());
		middleVariableButton.setBorderPainted(false);
		
		JButton codeReviewerButton = new JButton(new ImageIcon("images/review.png"));
		codeReviewerButton.setBackground(ColorScheme.getPanelBackground());
		codeReviewerButton.setForeground(ColorScheme.getPanelForeground());
		codeReviewerButton.setBorderPainted(false);

		JButton updateNotesButton = new JButton(new ImageIcon("images/updatenotes.png"));
		updateNotesButton.setBackground(ColorScheme.getPanelBackground());
		updateNotesButton.setForeground(ColorScheme.getPanelForeground());
		updateNotesButton.setBorderPainted(false);

		JButton speechActButton = new JButton(new ImageIcon("images/echoedit.png"));
		speechActButton.setBackground(ColorScheme.getPanelBackground());
		speechActButton.setForeground(ColorScheme.getPanelForeground());
		speechActButton.setBorderPainted(false);
		
		fileOptions.add(Box.createRigidArea(new Dimension(10, 40)));
		fileOptions.add(updateNotesButton);
		fileOptions.add(Box.createRigidArea(new Dimension(10, 40)));
		fileOptions.add(middleVariableButton);
		fileOptions.add(Box.createRigidArea(new Dimension(10, 40)));
		fileOptions.add(codeReviewerButton);
		fileOptions.add(Box.createRigidArea(new Dimension(10, 40)));
		fileOptions.add(speechActButton);
		
		
		final JEditorPane fileNote = new JEditorPane();
		fileNote.setBackground(ColorScheme.getPanelBackground());
		fileNote.setForeground(ColorScheme.getPanelForeground());
		fileNote.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
		String query = "";
		int projectID = -1;
		ResultSet result = null;
		if(isFile){
			try {
				query = "select projectID from projects where name='" + node.getParent().toString() + "'";
				result = LifeStream.getQuery(query, false);
				result.next();
				projectID = result.getInt(1);
				LifeStream.closeQuery();
				query = "select note from files where fileName='" + node.toString() + "' and projectID=" + projectID;
				result = LifeStream.getQuery(query, false);
				result.next();
				fileNote.setText(result.getString(1));
				LifeStream.closeQuery();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			
			try {
				query = "select notes from projects where name='" + node.toString() + "'";
				result = LifeStream.getQuery(query, false);
				result.next();
				fileNote.setText(result.getString(1));
				LifeStream.closeQuery();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
		
		fileNote.setEditable(true);
		
		//changes right view to the display pane
		JLabel fileLabel = new JLabel();
		File file = new File(node.toString());
		//System.out.println(System.getProperty("os.name"));
		fileLabel.setText("  " + file.getName());
		fileLabel.setHorizontalTextPosition(10);
		fileLabel.setForeground(ColorScheme.getPanelForeground());
		fileLabel.setVerticalTextPosition(JLabel.BOTTOM);
		Font f = new Font(null, Font.PLAIN, 22);
		fileLabel.setFont(f);
		
		
		displayPane.add(fileLabel);
		displayPane.add(fileNote);
		displayPane.add(fileOptions);
		splitPane.setRightComponent(displayPane);
		
		speechActButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            		
            	}
         });
		
		updateNotesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	String query = "";
        		int projectID = -1;
        		ResultSet result = null;
            	if(isFile){
        			try {
        				query = "select projectID from projects where name='" + node.getParent().toString() + "'";
        				result = LifeStream.getQuery(query, false);
        				result.next();
        				projectID = result.getInt(1);
        				LifeStream.closeQuery();
        				query = "update files set note='" + fileNote.getText() + "' where fileName='" + node.toString() + "' and projectID=" + projectID;
        				LifeStream.setQuery(query);
        				LifeStream.closeQuery();
        				SessionTracker.updateIncrement();
        			} catch (SQLException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        		} else {
        			try {
        				query = "update projects set notes='" + fileNote.getText() + "' where name='" + node.toString() + "'";
        				LifeStream.setQuery(query);
        				LifeStream.closeQuery();
        				SessionTracker.updateIncrement();
        			} catch (SQLException e){
        				e.printStackTrace();
        			}
        		}
            }
        });
		
		
		middleVariableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(isFile){
                	try {
                		
	        			String projectName = node.getParent().toString();
        				String query = "select fileID,content from files,projects where files.projectID=projects.projectID " +
        							"and files.fileName='" + node.toString() + "' and projects.name='" + projectName + "'";
        				ResultSet code = LifeStream.getQuery(query, false);
        				code.next();
        				
//        				final int fileID = code.getInt(1);
        				//String fileNode = node.toString();
                		//String ext = fileNode.substring(fileNode.lastIndexOf('.')+1, fileNode.length());
        				ByteArrayInputStream bis = null;;
        				ObjectInputStream ins = null;;
        				try{
        					System.err.println("1");
	                		bis = new ByteArrayInputStream(code.getBytes(2));
	                		System.err.println("2");
	                		ins = new ObjectInputStream(bis);
	                		System.err.println("3");
	                		
	                		
        				} catch (EOFException e){
        					//
        				}
        				
        				try{
	        				editor.setDocument((Document) ins.readObject());
	                		System.err.println("4");
	                		ins.close();
        				} catch(NullPointerException e){
        					editor.setText("");
        				}
                		
                		/*
        				if(ext.compareTo("mmd") == 0){
                			//editor.loadDocument(node.toString());
                			editor.setDocument((Document)code.getObject(2));
                		} else {
                			editor.setText((String) code.getObject(2));
                		}*/
	        			
	        			LifeStream.closeQuery();
                		
                		
                		
                		/*else {
                			File file = new File(node.toString());
	                		StringBuilder contents = new StringBuilder();
	                		BufferedReader input;
							try {
								input = new BufferedReader(new FileReader(file));
								String line = null;
		                		try {
									while (( line = input.readLine()) != null){
									      contents.append(line);
									      contents.append(System.getProperty("line.separator"));
									    }
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		                		try {
									input.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} catch (FileNotFoundException e1) {
							//
							}
							editor.setText(contents.toString());
                		}*/
        			
	        			JPanel editorButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
	        			JButton commitButton = new JButton(new ImageIcon ("images/save.png"));
	        			commitButton.setBackground(ColorScheme.getPanelBackground());
	        			commitButton.setForeground(ColorScheme.getPanelForeground());
	        			commitButton.setBorderPainted(false);
	        			JButton copyButton = new JButton(new ImageIcon ("images/clipboard.png"));
	        			copyButton.setBackground(ColorScheme.getPanelBackground());
	        			copyButton.setForeground(ColorScheme.getPanelForeground());
	        			copyButton.setBorderPainted(false);
	        			editorButtonPanel.add(commitButton);
	        			editorButtonPanel.add(copyButton);
	        			
	        			JPanel editorPanel = new JPanel(new BorderLayout(1,1));
	        			editorPanel.setBackground(ColorScheme.getPanelBackground());
	        			editorPanel.add(editorView, BorderLayout.CENTER);
	        			editorPanel.add(editorButtonPanel, BorderLayout.SOUTH);
	        			editorButtonPanel.setBackground(ColorScheme.getPanelBackground());
	        			
	        			//change splitpane back to editor view with buttons for saving to DB
	        			splitPane.setRightComponent(editorPanel);
	        			editor.setEditable(true);
	        			splitPane.setAutoscrolls(true);
	        			splitPane.updateUI();
	        			editorView.updateUI();
	        			
	        			commitButton.addActionListener(new ActionListener() {
	        				public void actionPerformed(ActionEvent arg0){
//	        					String fileNode = node.toString();
//	                    		String ext = fileNode.substring(fileNode.lastIndexOf('.')+1, fileNode.length());
//	                    		String query = "";	
	            				//if(ext.compareTo("mmd") == 0){
	            					try {
//	            						PreparedStatement ps = conn.prepareStatement("update files set content=? where fileID=?");
	            						ByteArrayOutputStream bos = new ByteArrayOutputStream();
										ObjectOutputStream oos = new ObjectOutputStream(bos);
										oos.writeObject(editor.getDocument());
										oos.flush();
										oos.close();
										bos.close();
										
										
										
//										byte[] textdata = bos.toByteArray();
										
//										ps.setBytes(1, textdata);
//										ps.setInt(2, fileID);
//										ps.execute();
//										ps.close();
										//query = "update files set content='" + textdata + 
		                    			//"' where fileID='" + fileID + "'";
										
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
//									} catch (SQLException e) {
										// TODO Auto-generated catch block
//										e.printStackTrace();
									}
									
	            					
	                    		/*} else {
	                    			query = "update files set content='" + editor.getText() + 
	                    			"' where fileID='" + fileID + "'";
	                    		}*/
	        				
	        						
	        					//LifeStream.setQuery(query);
								try {
									LifeStream.closeQuery();
									SessionTracker.commitIncrement();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
	        				}
	        			});
	        		
	        		} catch (SQLException e) {
	        			//shows errors if debugging is on
	        			if (debug) {
	        				e.printStackTrace();
	        			}
	        		} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                } else {
                	
                	JPanel addFilePanel = new JPanel(new BorderLayout(1,1));
                	addFilePanel.setBackground(ColorScheme.getPanelBackground());
                	addFilePanel.setPreferredSize(new Dimension(550,550));
                	
                	final JPanel topFiller = new JPanel();
                	topFiller.setBackground(ColorScheme.getPanelBackground());
                	topFiller.setPreferredSize(new Dimension(250,175));
                	final JLabel badFileName = new JLabel();
                	badFileName.setForeground(ColorScheme.getPanelForeground());
                	topFiller.add(badFileName);
                	
                	JPanel bottomButtons = new JPanel(new FlowLayout(FlowLayout.CENTER,5, 50));
                	bottomButtons.setBackground(ColorScheme.getPanelBackground());
                	bottomButtons.setPreferredSize(new Dimension(250,325));
                	JButton addFileButton = new JButton(new ImageIcon("images/createfile.png"));
                	addFileButton.setBackground(ColorScheme.getPanelBackground());
                	addFileButton.setForeground(ColorScheme.getPanelForeground());
                	addFileButton.setBorderPainted(false);
                	JButton cancelAddFileButton = new JButton(new ImageIcon("images/cancel.png"));
                	cancelAddFileButton.setBackground(ColorScheme.getPanelBackground());
                	cancelAddFileButton.setForeground(ColorScheme.getPanelForeground());
                	cancelAddFileButton.setBorderPainted(false);
                	bottomButtons.add(addFileButton);
                	bottomButtons.add(cancelAddFileButton);
                	
                	JPanel rightFiller = new JPanel();
                	rightFiller.setBackground(ColorScheme.getPanelBackground());
                	rightFiller.setPreferredSize(new Dimension(150,550));
                	JPanel leftFiller = new JPanel();
                	leftFiller.setBackground(ColorScheme.getPanelBackground());
                	leftFiller.setPreferredSize(new Dimension(150,550));
                	
                	JPanel mainAddFilePanel = new JPanel(new GridLayout(2,2));
                	mainAddFilePanel.setBackground(ColorScheme.getPanelBackground());
                	JLabel fileNameLabel = new JLabel("File Name:\t");
                	fileNameLabel.setForeground(ColorScheme.getPanelForeground());
                	final JTextField fileNameField = new JTextField();
                	fileNameField.setBackground(ColorScheme.getEditorBackground());
                	fileNameField.setForeground(ColorScheme.getEditorForeground());
                	mainAddFilePanel.add(fileNameLabel);
                	mainAddFilePanel.add(fileNameField);
                	
                	JLabel noteLabel = new JLabel("Note (optional):\t");
                	noteLabel.setForeground(ColorScheme.getPanelForeground());
                	final JTextField noteField = new JTextField();
                	noteField.setBackground(ColorScheme.getEditorBackground());
                    noteField.setForeground(ColorScheme.getEditorForeground());
                	mainAddFilePanel.add(noteLabel);
                	mainAddFilePanel.add(noteField);
                	
                	
                	addFilePanel.add(leftFiller, BorderLayout.WEST);
                	addFilePanel.add(rightFiller, BorderLayout.EAST);
                	addFilePanel.add(topFiller, BorderLayout.NORTH);
                	addFilePanel.add(bottomButtons, BorderLayout.SOUTH);
                	addFilePanel.add(mainAddFilePanel, BorderLayout.CENTER);
                	
                	
                	splitPane.setRightComponent(addFilePanel);
                	
                	addFileButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                        	String query = "";
                        	ResultSet insertingFile;
                        	int projectID;
                        	try {
                        		query = "select projectID from projects where name='" + node.toString() + "'";
                        		insertingFile = LifeStream.getQuery(query, false);
								insertingFile.next();
							    projectID = insertingFile.getInt(1);
								
								query = "select fileName from files where fileName='"
										+ fileNameField.getText() + "' and projectID='" + projectID + "'";
								insertingFile = LifeStream.getQuery(query, false);
								
								if(insertingFile.next() || fileNameField.getText().equals("")){
									badFileName.setText("<html><p style=\"color:Red\"><br><br><br><br><br><br>Invalid File Name</html>");
									topFiller.updateUI();
								
								} else {			
									
									System.out.println(fileNameField.getText());
									
									if(noteField.getText().equals("")){
										query = "insert into files (projectID,fileName, content, note) values " +
												"(" + projectID + ", \"" + fileNameField.getText() + " \", \"\", \"\")"; 
												
									} else {
								
										query = "insert into files (projectID,fileName, content,note) values " +
											"(" + projectID + ", \"" + fileNameField.getText() + " \", \"\", \"" 
											+ noteField.getText() + "\")"; 
									}
									
									LifeStream.setQuery(query);
									node.add(new DefaultMutableTreeNode(fileNameField.getText(), false));
									projectTree.updateUI();
									LifeStream.closeQuery();
									splitPane.setRightComponent(displayPane);
								
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        }

                    });
                	
                	cancelAddFileButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            splitPane.setRightComponent(displayPane);
                        }

                    });
                	
                	
                }
            }
        });
	}

}
