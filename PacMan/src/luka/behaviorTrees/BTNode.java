package luka.behaviorTrees;

public abstract class BTNode {
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
	
	public abstract boolean Activate();
	
	public BTNode() {};
}


