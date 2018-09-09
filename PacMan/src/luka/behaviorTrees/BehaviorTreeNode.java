package luka.behaviorTrees;

public class BehaviorTreeNode {
	public enum NodeType 
	{
		Leaf,
		Composite,
		Decorator
	}
	public enum CompositeType
	{
		None,
		Sequence,
		Selector,
		Random
	}
	public enum DecoratorType
	{
		None,
		Inverter,
		Succeder,
		Repeater,
		RepeatUntilFail
	}
	
	private NodeType myNodeType;
	
	public BehaviorTreeNode(NodeType myNodeType) {
		this.myNodeType = myNodeType;
	}
	
	public boolean QuerryForResult() 
	{
		return false;
	}
}

