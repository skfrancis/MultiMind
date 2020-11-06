package subversion;

import javax.swing.tree.DefaultMutableTreeNode;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNURL;


public class RepositoryTreeNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 4387549229843683078L;
	
	private String path;
	private boolean loaded;
	
	public RepositoryTreeNode (String path,  Object entry) {
		super(entry);
		this.path = path;
		loaded = false;
	}
	
	public String getNodePath() {
		return path;
	}
	
	public String toString() {
		if(this.getUserObject().getClass().equals(SVNURL.class)) {
			return this.getUserObject().toString();
		} else {
		return ((SVNDirEntry) this.getUserObject()).getName();
		}
	}
	
	public boolean getLoaded() {
		return loaded;
	}
	
	public void setLoaded(boolean isLoaded) {
		loaded = isLoaded;
	}
}
