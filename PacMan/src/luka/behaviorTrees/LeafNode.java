package luka.behaviorTrees;

public abstract class LeafNode extends BehaviorTreeNode {

	public LeafNode() {
		super();
		myNodeType = myNodeType.Leaf;
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract boolean Activate();
}
