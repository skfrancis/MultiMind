package projectGUI;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class ProjectTreeModel implements TreeModel {

	private File root;
	private Vector<TreeModelListener> listeners;
	
	public ProjectTreeModel(File rootDirectory) {
		root = rootDirectory;
		listeners = new Vector<TreeModelListener>();
	}
	
	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public boolean isLeaf(Object node) {
		return ((File)node).isFile();
	}
	
	@Override
	public Object getChild(Object parent, int index) {
		String[] children = ((File)parent).list();
		if((children == null) || (index >= children.length)) {
			return null;
		}
		return new TreeFile ((File) parent, children[index]);
	}

	@Override
	public int getChildCount(Object parent) {
		String[] children = ((File)parent).list();
		if(children == null) {
			return 0;
		}
		return children.length;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		String[] children = ((File)parent).list();
		if(children == null) {
			return -1;
		}
		String childName = ((File)child).getName();
		for(int index = 0; index < children.length; index++) {
			if(childName.equals(children[index])) {
				return index;
			}
		}
		return -1;
	}

	public void valueForPathChanged(TreePath path, Object newValue) {
		File oldFile = (File) path.getLastPathComponent();
	    String fileParentPath = oldFile.getParent();
	    String newFileName = (String) newValue;
	    File targetFile = new File(fileParentPath, newFileName);
	    oldFile.renameTo(targetFile);
	    File parent = new File(fileParentPath);
	    int[] changedChildrenIndices = { getIndexOfChild(parent, targetFile) };
	    Object[] changedChildren = { targetFile };
	    fireTreeNodesChanged(path.getParentPath(), changedChildrenIndices, changedChildren);
	}
	
	private void fireTreeNodesChanged(TreePath parentPath, int[] indices, Object[] children) {
	    TreeModelEvent event = new TreeModelEvent(this, parentPath, indices, children);
	    Iterator<TreeModelListener> iterator = listeners.iterator();
	    TreeModelListener listener = null;
	    while (iterator.hasNext()) {
	      listener = (TreeModelListener) iterator.next();
	      listener.treeNodesChanged(event);
	    }
	}
	
	public void addTreeModelListener(TreeModelListener listener) {
		listeners.add(listener);
	}
	
	public void removeTreeModelListener(TreeModelListener listener) {
		listeners.remove(listener);
	}
	
	private class TreeFile extends File {
	
		private static final long serialVersionUID = 3737850007190585692L;
		
		public TreeFile(File parent, String child) {
			super(parent, child);
		}
		
		public String toString() {
			return getName();
		}
	}
}
