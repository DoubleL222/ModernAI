package luka.mcts;

public class MCTSManager {
	private static final MCTSManager instance = new MCTSManager();
	
	boolean interupted = false;
	public static MCTSManager getInstance() 
	{
		return instance;
	}
	
	public void GetResult() 
	{
		interupted = false;
		while(!interupted) {
			System.out.println("RUNNING");
		}
	}
	
	public void Interupt() 
	{
		interupted = true;
	}
}
