package luka.behaviorTrees;

import java.util.Vector;

import luka.behaviorTrees.BTNode;

public abstract class CompositeNode extends BTNode{
	protected class ChildPointer
	{
		public ChildPointer(BTNode _child)
		{
			this.child = _child;
			this.decorators = null;
		}
		public ChildPointer(BTNode _child, Vector<DecoratorNode> _decorators) 
		{
			this.child = _child;
			this.decorators = _decorators;
		}
		
		public BTNode child;
		public Vector<DecoratorNode> decorators;
	}
	
	public CompositeNode() {
		super();
		myNodeType = myNodeType.Composite;
		children = new Vector<ChildPointer>();
		// TODO Auto-generated constructor stub
	}
	
	public void AddChild(BTNode _node) 
	{
		children.add(new ChildPointer(_node));
	}
	
	public void AddChildWithDecorator(BTNode _node, Vector<DecoratorNode> _decorator) 
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
