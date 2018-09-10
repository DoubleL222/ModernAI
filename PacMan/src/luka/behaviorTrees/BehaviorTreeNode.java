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
	
	protected NodeType myNodeType;
	
	public BehaviorTreeNode() {
	}
	
	public boolean QuerryForResult() 
	{
		return false;
	}
}


