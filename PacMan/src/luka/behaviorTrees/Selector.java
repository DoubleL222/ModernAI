package luka.behaviorTrees;

public class Selector extends CompositeNode{
	
	public Selector() {
		super();
		myCompositeType = myCompositeType.Selector;
	}

	@Override
	public boolean Activate() {
		System.out.println("Activate Selector");
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
					
					if(childResponse) 
					{
						return true;
					}
				}
				return false;
			}
		}
		return true;
	}

}
