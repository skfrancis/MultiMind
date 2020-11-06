package projectGUI;

import java.awt.Component;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import tools.ColorScheme;

public class ProjectRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 119141520266392752L;
	
	public ProjectRenderer() {}
	
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
		
		File object = (File) value;
		if(object.isDirectory()) {
				setIcon(new ImageIcon("images/fullFolder.png"));
		}
		else if(object.isFile()) {
				setIcon(new ImageIcon("images/file.png"));
		}
		return this;
	}
}
