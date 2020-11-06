package subversion;

import java.util.Collection;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.io.SVNRepository;
import tools.ColorScheme;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewRepository extends JFrame {

	private static final long serialVersionUID = 478666490002143449L;
	private JTree repositoryTree;
	private JPanel testPanel;

	/**
	 * Create the frame.
	 * @throws SVNException 
	 */
	public ViewRepository() throws SVNException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 425, 585);
		Point middle = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		setLocation(new Point((int)middle.getX() - 215, (int)middle.getY() - 245));
		testPanel = new JPanel();
		testPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		testPanel.setBackground(ColorScheme.getPanelBackground());
		setContentPane(testPanel);
		testPanel.setLayout(null);
		displayRepository();
	}
	
	private void displayRepository() throws SVNException  {
				
		SVNRepository repository = SVNConnector.getRepository();
		JLabel rootLabel;
		rootLabel = new JLabel("Root: " + repository.getRepositoryRoot(true));
		RepositoryTreeNode root = new RepositoryTreeNode("", repository.getRepositoryRoot(true));
		buildTree("", root);
		repositoryTree = new JTree(root);
		repositoryTree.setCellRenderer(new RepositoryRenderer());
		repositoryTree.setBackground(ColorScheme.getPanelBackground());
		rootLabel.setBounds(25, 10, 374, 20);
		rootLabel.setForeground(ColorScheme.getPanelForeground());
  		testPanel.add(rootLabel);

  		JLabel uuidLabel;
		uuidLabel = new JLabel("UUID: " + repository.getRepositoryUUID(true));
		uuidLabel.setBounds(25, 30, 374, 20);
		uuidLabel.setForeground(ColorScheme.getPanelForeground());
		testPanel.add(uuidLabel);

		long latestRevision = -1;
		latestRevision = repository.getLatestRevision();
		JLabel revisionLabel = new JLabel("Latest Revision: " + latestRevision);
		revisionLabel.setBounds(25, 50, 374, 20);
		revisionLabel.setForeground(ColorScheme.getPanelForeground());
		testPanel.add(revisionLabel);
		
        JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 81, 355, 385);
		testPanel.add(scrollPane);
		
		scrollPane.setViewportView(repositoryTree);
		
		JButton cancelButton = new JButton();
		cancelButton.setToolTipText("Cancel");
		cancelButton.setForeground((Color) null);
		cancelButton.setBorderPainted(false);
		cancelButton.setBackground((Color) null);
		cancelButton.setBounds(178, 486, 48, 48);
		cancelButton.setIcon(new ImageIcon("images/cancel.png"));
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		testPanel.add(cancelButton);
		repositoryTree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent event) {
				RepositoryTreeNode node;
				node = (RepositoryTreeNode) event.getNewLeadSelectionPath().getLastPathComponent();
				if(node.getUserObject().getClass().equals(SVNDirEntry.class)) {
					SVNDirEntry object = (SVNDirEntry) node.getUserObject();
					if(object.getKind() == SVNNodeKind.DIR) {
						if(!node.getLoaded()) {
							try {
								buildTree(node.getNodePath(), node);
							} catch (SVNException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		});
	}
	
	private void buildTree(String path, RepositoryTreeNode node) throws SVNException {
        
        Collection<?> entries = SVNConnector.getRepositoryEntries(path);
        Iterator<?> iterator = entries.iterator();
        
        while (iterator.hasNext()) {
            SVNDirEntry entry = (SVNDirEntry) iterator.next();
            RepositoryTreeNode newNode = new RepositoryTreeNode((path.equals("")) ? entry.getName() : path + "/" + entry.getName(), entry);
            node.add(newNode);
        }
        node.setLoaded(true);
	}
}
