package luka.behaviorTrees;

import luka.behaviorTrees.BehaviorTreeNode;

public class DecoratorNode extends BehaviorTreeNode {
	public DecoratorNode() {
		super();
		myNodeType = myNodeType.Decorator;
		// TODO Auto-generated constructor stub
	}

	private DecoratorType myDecoratorType;
	
}
