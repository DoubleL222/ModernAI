package luka.behaviorTrees.leafs;

import java.util.Random;

import luka.behaviorTrees.BehaviorTreeManager;
import luka.behaviorTrees.LeafNode;
import pacman.game.Constants.GHOST;
import pacman.game.Game;

public class IsInDanger extends LeafNode {
	private int minDistForDanger;
	
	public IsInDanger(int _minDistForDanger) {
		minDistForDanger = _minDistForDanger;
	}
	
	@Override
	public boolean Activate() {
		int minDistance = Integer.MAX_VALUE;
		int rnd = new Random().nextInt(GHOST.values().length);
		GHOST closestGhost = GHOST.values()[rnd];
		
		Game game = BehaviorTreeManager.getInstance().getGame();
		int pacmanNodeIndex = game.getPacmanCurrentNodeIndex();

		for(GHOST ghost : GHOST.values()) 
		{
			if(game.getGhostCurrentNodeIndex(ghost) != game.getGhostInitialNodeIndex() && !game.isGhostEdible(ghost)) {
				int currDist = game.getShortestPathDistance(pacmanNodeIndex, game.getGhostCurrentNodeIndex(ghost));
				if(currDist != -1 && currDist < minDistance) 
				{
					minDistance = currDist;
					closestGhost = ghost;
				}
			}
		}
		
		if(minDistance < 0 || minDistance == Integer.MAX_VALUE) 
		{
			BehaviorTreeManager.getInstance().setRunning(false);
			return false;
		}
		
		BehaviorTreeManager.getInstance().setClosestGhost(closestGhost);
		
		if(minDistance <= minDistForDanger) 
		{
			System.out.println("IS IN DANGER");
			BehaviorTreeManager.getInstance().setRunning(true);
			BehaviorTreeManager.getInstance().setRunningStartTime(System.currentTimeMillis());
			//System.out.print("current time in milis " + System.currentTimeMillis());
			return true;
		}
		else 
		{
			BehaviorTreeManager.getInstance().setRunning(false);
			return false;
		}
	}
}
