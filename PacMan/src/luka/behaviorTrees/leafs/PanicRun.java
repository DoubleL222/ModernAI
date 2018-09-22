package luka.behaviorTrees.leafs;

import java.awt.Color;

import luka.behaviorTrees.BehaviorTreeManager;
import luka.behaviorTrees.LeafNode;
import pacman.game.Game;
import pacman.game.GameView;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;

public class PanicRun extends LeafNode{

	private float runDuration;
	
	public PanicRun(float _runDuration) {
		super();
		runDuration = _runDuration;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean Activate() {
		if(BehaviorTreeManager.getInstance().isRunning()) 
		{
			//Stop runing
			double now = System.currentTimeMillis();
			System.out.println("current MILIS: "+ now);
			System.out.println("running START TIME: "+BehaviorTreeManager.getInstance().getRunningStartTime());
			if(BehaviorTreeManager.getInstance().getRunningStartTime()+runDuration < System.currentTimeMillis()) 
			{
				System.out.println("STOP PANIC RUN");
				BehaviorTreeManager.getInstance().setRunning(false);
				return false;
			}
			
			GHOST closestGhost = BehaviorTreeManager.getInstance().getClosestGhost();
			Game game = BehaviorTreeManager.getInstance().getGame();
			if(closestGhost != null) 
			{
				System.out.println("PANIC RUN");
				BehaviorTreeManager.getInstance().setNextMove(game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(closestGhost), DM.PATH));
				GameView.addLines(game, Color.RED, new int[] {game.getPacmanCurrentNodeIndex()} , new int[] {game.getGhostCurrentNodeIndex(closestGhost)});
			}
			return true;
		}
		else 
		{
			return false;
		}
	}
}
