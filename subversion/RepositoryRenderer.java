package subversion;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNNodeKind;


import tools.ColorScheme;

public class RepositoryRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 119141520266392752L;
	
	public RepositoryRenderer() {}
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean isSelected, boolean isExpanded, boolean isLeaf, int row, boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, isSelected, isExpanded, isLeaf, row, hasFocus);
		
		if(isSelected) {
			super.setTextSelectionColor(ColorScheme.getEditorForeground());
			super.setBackgroundSelectionColor(ColorScheme.getEditorBackground());
		} else {
			super.setTextNonSelectionColor(ColorScheme.getPanelForeground());
			super.setBackgroundNonSelectionColor(ColorScheme.getPanelBackground());
		}
		
		RepositoryTreeNode node = (RepositoryTreeNode) value;
		if(node.getUserObject().getClass().equals(SVNDirEntry.class)) {
			SVNDirEntry object = (SVNDirEntry) node.getUserObject();
			if(object.getKind() == SVNNodeKind.DIR) {
				setIcon(new ImageIcon("images/fullFolder.png"));
			}
			else if(object.getKind() == SVNNodeKind.FILE) {
				setIcon(new ImageIcon("images/file.png"));
			}
		} else {
			setIcon(new ImageIcon("images/fullFolder.png"));
		}
		return this;
	}
}
