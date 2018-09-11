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
		System.out.println("Check if in danger");
		int minDistance = Integer.MAX_VALUE;
		int rnd = new Random().nextInt(GHOST.values().length);
		GHOST closestGhost = GHOST.values()[rnd];
		
		Game game = BehaviorTreeManager.getInstance().getGame();
		int pacmanNodeIndex = game.getPacmanCurrentNodeIndex();

		for(GHOST ghost : GHOST.values()) 
		{
			if(game.getGhostCurrentNodeIndex(ghost) != game.getGhostInitialNodeIndex() && !game.isGhostEdible(ghost)) {
				int currDist = game.getShortestPathDistance(pacmanNodeIndex, game.getGhostCurrentNodeIndex(ghost));
				System.out.println("Distance from ghost "+currDist);
				if(currDist < minDistance) 
				{
					minDistance = currDist;
					closestGhost = ghost;
				}
			}
		}
		
		if(minDistance < 0 || minDistance == Integer.MAX_VALUE) 
		{
			return false;
		}
		
		BehaviorTreeManager.getInstance().setClosestGhost(closestGhost);
		
		if(minDistance <= minDistForDanger) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
}
