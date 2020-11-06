package projectGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import subversion.SVNConnector;
import subversion.ViewRepository;
import tools.ColorScheme;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import org.tmatesoft.svn.core.SVNException;

/**
 * @author Shawn
 *
 */
public class ProjectPanel extends JPanel {

	private static final long serialVersionUID = -7203295940833706371L;

	private JTree projectTree;
	//private JSplitPane splitPane;
	private JScrollPane scrollPane;
//	private JPanel infoPanel;
	private JPanel projectButtons;
	private File workingDirectory;
	
	/**
	 * Create the panel.
	 */
	public ProjectPanel() {
		setLayout(new BorderLayout(0, 0));
		setBackground(ColorScheme.getPanelBackground());
//		splitPane = new JSplitPane();
//		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
//		splitPane.setDividerLocation(265);
		
		createTree();
		createProjectButtons();
		scrollPane = new JScrollPane(projectTree);
//		splitPane.setLeftComponent(scrollPane);
//		splitPane.setRightComponent(infoPanel);
		add(scrollPane, BorderLayout.CENTER);
		add(projectButtons, BorderLayout.NORTH);
	}
	
	private void createTree() {
		String currentDirectory = System.getProperty("user.dir");
		workingDirectory = new File (currentDirectory, "/Projects");

		if(!workingDirectory.exists()) {
			workingDirectory.mkdir();
		}
				
		projectTree = new JTree();
		projectTree.setModel(new ProjectTreeModel(workingDirectory));
		projectTree.setCellRenderer(new ProjectRenderer());
		projectTree.setBackground(ColorScheme.getPanelBackground());
		projectTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent event) {

			}
		});
		projectTree.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				if (event.getButton() == MouseEvent.BUTTON3) {
					TreePath path = projectTree.getPathForLocation(event.getX(), event.getY());
					if (path != null) {
                    	File location = (File) path.getLastPathComponent();
                    	JPopupMenu popup;
						if(location.isFile()) {
							popup = filePopupMenu();
						} else {
							popup = dirPopupMenu();
						}
						projectTree.setSelectionPath(path);
						popup.show(projectTree, event.getX(), event.getY());
					}
				}
			}
		});
		projectTree.updateUI();
	}
	
	private JPopupMenu filePopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		JMenuItem evolveCode = new JMenuItem("Evolve Code", new ImageIcon("images/evolve.png"));
		popup.add(evolveCode);
		
		JMenuItem reviewCode = new JMenuItem("Review Code", new ImageIcon("images/review.png"));
		popup.add(reviewCode);
		
		JMenuItem viewNotes = new JMenuItem("View Notes", new ImageIcon("images/notes.png"));
		popup.add(viewNotes);
		
		JMenuItem echoEdit = new JMenuItem("Echo Edit", new ImageIcon("images/echoedit.png"));
		popup.add(echoEdit);
		
		return popup;
	}
	
	private JPopupMenu dirPopupMenu() {
		JPopupMenu popup = new JPopupMenu();
		JMenuItem newFile = new JMenuItem("New File", new ImageIcon("images/newfile.png"));
		popup.add(newFile);
		
		JMenuItem newFolder = new JMenuItem("New Folder", new ImageIcon("images/newfolder.png"));
		popup.add(newFolder);
		
		return popup;
	}
	
//	private void updateTree() {
//		createTree();
//		scrollPane = new JScrollPane(projectTree);
//		splitPane.setLeftComponent(scrollPane);
//		splitPane.setDividerLocation(265);
//		splitPane.updateUI();
//	}
	
	
	private void createProjectButtons() {
		projectButtons = new JPanel();
		projectButtons.setBackground(ColorScheme.getPanelBackground());
		
		JButton newProject = new JButton(new ImageIcon("images/addproject.png"));
        newProject.setBackground(ColorScheme.getPanelBackground());
        newProject.setForeground(ColorScheme.getPanelForeground());
        newProject.setBorderPainted(false);
        newProject.setFocusable(false);
        projectButtons.add(newProject);
        
        JButton deleteProject = new JButton(new ImageIcon("images/deleteproject.png"));
        deleteProject.setBackground(ColorScheme.getPanelBackground());
        deleteProject.setForeground(ColorScheme.getPanelForeground());
        deleteProject.setBorderPainted(false);
        deleteProject.setFocusable(false);
        projectButtons.add(deleteProject);
        
        JButton commitButton = new JButton(new ImageIcon("images/commit.png"));
		commitButton.setToolTipText("Commit Changes");
		commitButton.setBackground(ColorScheme.getPanelBackground());
		commitButton.setForeground(ColorScheme.getPanelForeground());
		commitButton.setFocusable(false);
		commitButton.setBorderPainted(false);
		
		projectButtons.add(commitButton);
		
		JButton updateButton = new JButton(new ImageIcon("images/update.png"));
		updateButton.setToolTipText("Update Files");
		updateButton.setBackground(ColorScheme.getPanelBackground());
		updateButton.setForeground(ColorScheme.getPanelForeground());
		updateButton.setFocusable(false);
		updateButton.setBorderPainted(false);
		
		projectButtons.add(updateButton);
		
        
        JButton checkOutButton = new JButton(new ImageIcon("images/checkout.png"));
		checkOutButton.setToolTipText("Checkout Repository");
		checkOutButton.setBackground(ColorScheme.getPanelBackground());
		checkOutButton.setForeground(ColorScheme.getPanelForeground());
		checkOutButton.setFocusable(false);
		checkOutButton.setBorderPainted(false);
		checkOutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					SVNConnector.checkOutRepository(workingDirectory);
				} catch (SVNException exception) {
					// TODO Auto-generated catch block
					exception.printStackTrace();
				}
			}
		});
		projectButtons.add(checkOutButton);
  
		JButton viewButton = new JButton(new ImageIcon("images/connection.png"));
        viewButton.setToolTipText("View Repository");
		viewButton.setBackground(ColorScheme.getPanelBackground());
		viewButton.setForeground(ColorScheme.getPanelForeground());
		viewButton.setFocusable(false);
		viewButton.setBorderPainted(false);
		viewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					ViewRepository viewFrame = new ViewRepository();
					viewFrame.setVisible(true);
				} catch (SVNException exception) {
					// TODO Auto-generated catch block
					exception.printStackTrace();
				}
				
			}
			
		});
		projectButtons.add(viewButton);
	}
}
