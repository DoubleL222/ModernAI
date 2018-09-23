package luka.behaviorTrees.leafs;

import java.awt.Color;

import luka.behaviorTrees.BTManager;
import luka.behaviorTrees.LeafNode;
import pacman.game.Game;
import pacman.game.GameView;
import pacman.game.Constants.DM;

public class MoveTowardsNearestPill extends LeafNode{

	@Override
	public boolean Activate() {
		Game game = BTManager.getInstance().getGame();
		//get all active pills
		int[] activePills=game.getActivePillsIndices();
		
		//get all active power pills
		int[] activePowerPills=game.getActivePowerPillsIndices();
		
		//create a target array that includes all ACTIVE pills and power pills
		int[] targetNodeIndices=new int[activePills.length+activePowerPills.length];
		
		for(int i=0;i<activePills.length;i++)
			targetNodeIndices[i]=activePills[i];
		
		for(int i=0;i<activePowerPills.length;i++)
			targetNodeIndices[activePills.length+i]=activePowerPills[i];
		int closestPill = game.getClosestNodeIndexFromNodeIndex(game.getPacmanCurrentNodeIndex(),targetNodeIndices,DM.PATH);
		BTManager.getInstance().setNextMove(game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),closestPill,DM.PATH));
		
		GameView.addLines(game, Color.RED, new int[] {game.getPacmanCurrentNodeIndex()} , new int[] {closestPill});
		return true;
	}

}
