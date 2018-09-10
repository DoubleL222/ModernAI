package luka.behaviorTrees;

import luka.behaviorTrees.BehaviorTreeNode.DecoratorType;

public class DecoratorNode {
	public DecoratorNode(DecoratorType _type) 
	{
		myDecoratorType = _type;
	}
	public DecoratorType myDecoratorType;
}
