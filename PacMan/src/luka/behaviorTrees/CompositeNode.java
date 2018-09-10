package luka.behaviorTrees;

import java.util.Vector;

import luka.behaviorTrees.BehaviorTreeNode;

public class CompositeNode extends BehaviorTreeNode{
	private class ChildPointer
	{
		public ChildPointer(BehaviorTreeNode _child)
		{
			this.child = _child;
		}
		public ChildPointer(BehaviorTreeNode _child, DecoratorNode _decorator) 
		{
			child = _child;
			decorator = _decorator;
		}
		
		public BehaviorTreeNode child;
		public DecoratorNode decorator;
	}
	
	public CompositeNode() {
		super();
		myNodeType = myNodeType.Composite;
		children = new Vector<ChildPointer>();
		// TODO Auto-generated constructor stub
	}
	
	public void AddChild(BehaviorTreeNode _node) 
	{
		children.add(new ChildPointer(_node));
	}
	
	public void AddChildWithDecorator(BehaviorTreeNode _node, DecoratorNode _decorator) 
	{
		children.addElement(new ChildPointer(_node, _decorator));
	}
	
	protected CompositeType myCompositeType;
	protected Vector<ChildPointer> children;
}
