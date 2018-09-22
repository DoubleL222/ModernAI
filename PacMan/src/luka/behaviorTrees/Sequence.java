package luka.behaviorTrees;

import luka.behaviorTrees.CompositeNode.ChildPointer;

public class Sequence extends CompositeNode{
	
	public Sequence() {
		super();
		myCompositeType = myCompositeType.Sequence;
	}

	@Override
	public boolean Activate() {
		if(children != null) 
		{
			if(children.size()>0) 
			{
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
		return true;
	}

}
