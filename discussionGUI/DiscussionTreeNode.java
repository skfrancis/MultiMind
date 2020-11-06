package discussionGUI;

import javax.swing.tree.DefaultMutableTreeNode;

public class DiscussionTreeNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 1011242197053465041L;
	
	private int nodeID;
		
	public DiscussionTreeNode(int id, String subject) {
		super(subject);
		nodeID = id;
	}
	
	public int getNodeID(){
		return nodeID;
	}

	
}
