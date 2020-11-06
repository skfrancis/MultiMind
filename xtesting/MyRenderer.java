package xtesting;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import tools.ColorScheme;

public class MyRenderer extends JLabel implements TreeCellRenderer {
	
	private static final long serialVersionUID = -8542628793927957981L;
	JPanel renderer;
	JLabel label;
	DefaultTreeCellRenderer defaultRenderer;
	Color backgroundSelectionColor;
	Color backgroundNonSelectionColor;
	
	public MyRenderer(){
		
		 renderer = new JPanel(new GridLayout(0, 1));
		 label = new JLabel("");
		 defaultRenderer = new DefaultTreeCellRenderer();
		 renderer.add(label);
		 backgroundSelectionColor = ColorScheme.getEditorBackground();
		 backgroundNonSelectionColor = ColorScheme.getPanelBackground();
	}
	
	
	
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected,
        boolean isExpanded, boolean isLeaf, int row, boolean hasFocus) {
    	
    	Component comp = null;
    	DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
    	File file = new File(node.toString());
    	label.setText(file.getName());
    	
    	if(isSelected){
    		label.setForeground(ColorScheme.getEditorForeground());
    		renderer.setBackground(ColorScheme.getEditorBackground());
    	} else {
    		label.setForeground(ColorScheme.getPanelForeground());
    		renderer.setBackground(ColorScheme.getPanelBackground());
    	}
    	
	//will change depending on if it is a folder, open folder, or file
    	ImageIcon icon;
    	
    	if(isLeaf){
    		if(!node.getAllowsChildren()){
    			icon = new ImageIcon("images/file.gif");
    		} else {
    			icon = new ImageIcon("images/Generic Folder Yellow.png");
    		}
    	} else {
    		if(isExpanded){
    			icon = new ImageIcon("images/Generic Folder Yellow Open.png");
    		} else {
    			icon = new ImageIcon("images/Generic Folder Yellow.png");
    		}
    	}
    	
    	label.setIcon(icon);
    	
    	renderer.setEnabled(tree.isEnabled());
        comp = renderer;
    	return comp;
    }
  }
