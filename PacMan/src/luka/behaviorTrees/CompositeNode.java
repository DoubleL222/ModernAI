package luka.behaviorTrees;

import java.util.Vector;

import luka.behaviorTrees.BehaviorTreeNode;

public abstract class CompositeNode extends BehaviorTreeNode{
	protected class ChildPointer
	{
		public ChildPointer(BehaviorTreeNode _child)
		{
			this.child = _child;
			this.decorators = null;
		}
		public ChildPointer(BehaviorTreeNode _child, Vector<DecoratorNode> _decorators) 
		{
			this.child = _child;
			this.decorators = _decorators;
		}
		
		public BehaviorTreeNode child;
		public Vector<DecoratorNode> decorators;
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
	
	public void AddChildWithDecorator(BehaviorTreeNode _node, Vector<DecoratorNode> _decorator) 
	{
		children.addElement(new ChildPointer(_node, _decorator));
	}
	
	public boolean HandleDecorators(ChildPointer _cp, boolean _childOutput) 
	{
		for(DecoratorNode dn : _cp.decorators) 
		{
			switch(dn.myDecoratorType) 
			{
			case Inverter:
				_childOutput = !_childOutput;
				break;
			case Succeder:
				_childOutput = true;
				break;
			default:
				break;
			}
		}
		return _childOutput;
	}
	
	public abstract boolean Activate();
	
	protected CompositeType myCompositeType;
	protected Vector<ChildPointer> children;
}
