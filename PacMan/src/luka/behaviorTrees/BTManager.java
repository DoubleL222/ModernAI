package luka.behaviorTrees;

import luka.behaviorTrees.leafs.EvaluateBestDirection;
import luka.behaviorTrees.leafs.IsInDanger;
import luka.behaviorTrees.leafs.MoveTowardsNearestPill;
import luka.behaviorTrees.leafs.PanicRun;
import luka.behaviorTrees.leafs.RunFromClossestGhost;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class BTManager {
	private static double UPDATE_RATE = 100;
	
	private double nextUpdateTime = Double.MIN_VALUE;
	
	private static final BTManager instance = new BTManager();
	
	private Game game;
	
	private BTNode root;
	
	private GHOST closestGhost;
	
	private MOVE nextMove;
	
	private boolean isRunning = false;
	
	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	private double runningStartTime = Double.MIN_VALUE;


	public double getRunningStartTime() {
		return runningStartTime;
	}

	public void setRunningStartTime(double runningStartTime) {
		this.runningStartTime = runningStartTime;
	}

	public void setNextMove(MOVE nextMove) {
		this.nextMove = nextMove;
	}

	public GHOST getClosestGhost() {
		return closestGhost;
	}

	public void setClosestGhost(GHOST closestGhost) {
		this.closestGhost = closestGhost;
	}

	
	public void SetBasicTree() 
	{
		Selector top = new Selector();
		
		Sequence pillColector = new Sequence();
		pillColector.AddChild(new MoveTowardsNearestPill());
		
		top.AddChild(pillColector);
		
		setRoot(top);
	}
	
	public void SetFirstTree() 
	{
		Selector top = new Selector();
		Sequence dangerZone = new Sequence();
		dangerZone.AddChild(new IsInDanger(15));
		dangerZone.AddChild(new RunFromClossestGhost());
		
		Sequence pillColector = new Sequence();
		pillColector.AddChild(new MoveTowardsNearestPill());
		
		top.AddChild(dangerZone);
		top.AddChild(pillColector);
		
		setRoot(top);
	}
	
	//CREATE BEHAVIOUR TREE
	private BTManager() 
	{
		// BASE TREE
		/* 
		Selector top = new Selector();
		
		Sequence pillColector = new Sequence();
		pillColector.AddChild(new MoveTowardsNearestPill());
		
		top.AddChild(pillColector);
		
		setRoot(top);
		//*/
		
		// FIRST TREE
		/* 
		Selector top = new Selector();
		Sequence dangerZone = new Sequence();
		dangerZone.AddChild(new IsInDanger(15));
		dangerZone.AddChild(new RunFromClossestGhost());
		
		Sequence pillColector = new Sequence();
		pillColector.AddChild(new MoveTowardsNearestPill());
		
		top.AddChild(dangerZone);
		top.AddChild(pillColector);
		
		setRoot(top);
		//*/
		
		/* 
		Selector top = new Selector();
		Selector dangerZone = new Selector();
		dangerZone.AddChild(new PanicRun(500.0f));
		dangerZone.AddChild(new IsInDanger(30));
		
		Sequence pillColector = new Sequence();
		pillColector.AddChild(new MoveTowardsNearestPill());
		
		top.AddChild(dangerZone);
		top.AddChild(pillColector);
		
		setRoot(top);
		*/
		
		/*
		Selector top = new Selector();
		Sequence dangerZone = new Sequence();
		dangerZone.AddChild(new IsInDanger(30));
		dangerZone.AddChild(new EvaluateBestDirection());
		
		Sequence pillColector = new Sequence();
		pillColector.AddChild(new MoveTowardsNearestPill());
		
		top.AddChild(dangerZone);
		top.AddChild(pillColector);
		
		setRoot(top);
		*/
	}
	
	public Game getGame() {
		return game;
	}

	public static BTManager getInstance() 
	{
		return instance;
	}
	
	public BTNode getRoot() {
		return root;
	}

	public void setRoot(BTNode root) {
		this.root = root;
	}
	
	public MOVE QuerryTree(Game _game)
	{
		if(System.currentTimeMillis() > nextUpdateTime) 
		{
			/*
			game = _game;
			boolean rootBoolean = root.Activate();
			//System.out.println("root response: " + rootBoolean);
			nextUpdateTime = System.currentTimeMillis() + UPDATE_RATE;
			*/
		}
		game = _game;
		boolean rootBoolean = root.Activate();
		return nextMove;
	}
}
