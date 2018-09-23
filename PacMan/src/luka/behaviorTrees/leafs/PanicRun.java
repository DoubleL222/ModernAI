package luka.behaviorTrees.leafs;

import java.awt.Color;

import luka.behaviorTrees.BTManager;
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
		if(BTManager.getInstance().isRunning()) 
		{
			//Stop runing
			double now = System.currentTimeMillis();
			System.out.println("current MILIS: "+ now);
			System.out.println("running START TIME: "+BTManager.getInstance().getRunningStartTime());
			if(BTManager.getInstance().getRunningStartTime()+runDuration < System.currentTimeMillis()) 
			{
				System.out.println("STOP PANIC RUN");
				BTManager.getInstance().setRunning(false);
				return false;
			}
			
			GHOST closestGhost = BTManager.getInstance().getClosestGhost();
			Game game = BTManager.getInstance().getGame();
			if(closestGhost != null) 
			{
				System.out.println("PANIC RUN");
				BTManager.getInstance().setNextMove(game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(closestGhost), DM.PATH));
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
