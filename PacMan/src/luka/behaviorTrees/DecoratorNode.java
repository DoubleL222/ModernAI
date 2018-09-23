package luka.behaviorTrees;

import luka.behaviorTrees.BTNode.DecoratorType;

public class DecoratorNode {
	public DecoratorNode(DecoratorType _type) 
	{
		myDecoratorType = _type;
	}
	public DecoratorType myDecoratorType;
}
