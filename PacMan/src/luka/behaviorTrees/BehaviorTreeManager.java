package luka.behaviorTrees;

import luka.behaviorTrees.leafs.IsInDanger;
import luka.behaviorTrees.leafs.MoveTowardsNearestPill;
import luka.behaviorTrees.leafs.RunFromClossestGhost;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class BehaviorTreeManager {
	private static final BehaviorTreeManager instance = new BehaviorTreeManager();
	
	private Game game;
	
	private BehaviorTreeNode root;
	
	private GHOST closestGhost;
	
	private MOVE nextMove;


	public void setNextMove(MOVE nextMove) {
		this.nextMove = nextMove;
	}

	public GHOST getClosestGhost() {
		return closestGhost;
	}

	public void setClosestGhost(GHOST closestGhost) {
		this.closestGhost = closestGhost;
	}

	
	//CREATE BEHAVIOUR TREE
	private BehaviorTreeManager() 
	{
		Selector top = new Selector();
		Sequence dangerZone = new Sequence();
		dangerZone.AddChild(new IsInDanger(20));
		dangerZone.AddChild(new RunFromClossestGhost());
		
		Sequence pillColector = new Sequence();
		pillColector.AddChild(new MoveTowardsNearestPill());
		
		top.AddChild(dangerZone);
		top.AddChild(pillColector);
		
		setRoot(top);
	}
	
	public Game getGame() {
		return game;
	}

	public static BehaviorTreeManager getInstance() 
	{
		return instance;
	}
	
	public BehaviorTreeNode getRoot() {
		return root;
	}

	public void setRoot(BehaviorTreeNode root) {
		this.root = root;
	}
	
	public MOVE QuerryTree(Game _game)
	{
		game = _game;
		boolean rootBoolean = root.Activate();
		//System.out.println("root response: " + rootBoolean);
		return nextMove;
	}
}
