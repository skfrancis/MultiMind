package discussionGUI;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import tools.ColorScheme;


public class DiscussionRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 5622818680285565998L;
	
	public DiscussionRenderer() {}

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
		
		if(isLeaf) {
			setIcon(new ImageIcon("images/thread.png"));
		} else {
			setIcon(new ImageIcon("images/threads.png"));
		}
		return this;
	}

}
