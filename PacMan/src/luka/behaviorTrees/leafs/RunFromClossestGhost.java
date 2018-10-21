package luka.behaviorTrees.leafs;

import java.awt.Color;

import luka.behaviorTrees.BTManager;
import luka.behaviorTrees.LeafNode;
import pacman.game.Game;
import pacman.game.GameView;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;

public class RunFromClossestGhost extends LeafNode {

	@Override
	public boolean Activate() {
		//System.out.println("Run From Closest Ghost");
		GHOST closestGhost = BTManager.getInstance().getClosestGhost();
		Game game = BTManager.getInstance().getGame();
		if(closestGhost != null) 
		{
			BTManager.getInstance().setNextMove(game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(closestGhost), DM.PATH));
			GameView.addLines(game, Color.RED, new int[] {game.getPacmanCurrentNodeIndex()} , new int[] {game.getGhostCurrentNodeIndex(closestGhost)});
			return true;
		}
		else 
		{
			return false;
		}
	}

}
