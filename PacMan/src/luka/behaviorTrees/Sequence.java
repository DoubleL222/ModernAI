package luka.behaviorTrees;

import luka.behaviorTrees.CompositeNode.ChildPointer;

public class Sequence extends CompositeNode{
	
	public Sequence() {
		super();
		myCompositeType = myCompositeType.Sequence;
	}

	@Override
	public boolean Activate() {
		for(ChildPointer _cp : children) 
		{
			boolean childResponse = _cp.child.Activate();
			
			if(_cp.decorators != null) {
				childResponse = HandleDecorators(_cp, childResponse);
			}
			
			if(!childResponse) 
			{
				return false;
			}
		}
		return true;
	}

}
