package luka.behaviorTrees.leafs;

import java.util.Vector;

import luka.behaviorTrees.BehaviorTreeManager;
import luka.behaviorTrees.LeafNode;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class EvaluateBestDirection extends LeafNode {
	private int stepsToCheckAhead = 3;
	
	public int getStepsToCheckAhead() {
		return stepsToCheckAhead;
	}

	public void setStepsToCheckAhead(int stepsToCheckAhead) {
		this.stepsToCheckAhead = stepsToCheckAhead;
	}
	
	private Game game;

	@Override
	public boolean Activate() {
		game = BehaviorTreeManager.getInstance().getGame();
		int currentNode = game.getPacmanCurrentNodeIndex();
		MOVE[] possibleMoves = game.getPossibleMoves(currentNode);
		//game.getNeighbour(currentNode, moveToBeMade)
		return false;
	}
	
	Vector<Vector<MOVE>> GetPossiblePaths(Vector<Vector<MOVE>> currMoves, int currMapIndex, int prevMapIndex, Game game)
	{
		if(game.isJunction(currMapIndex)) 
		{
			for(MOVE _m : game.getPossibleMoves(currMapIndex))
			{
				if(game.getNeighbour(currMapIndex, _m) != prevMapIndex) {
					for(Vector<MOVE> _v : currMoves) 
					{
						_v.add(_m);
					}
				}
			}
		}
		else 
		{
			for(MOVE _m : game.getPossibleMoves(currMapIndex))
			{
			
			}
		}
		
		return null;
		
	}
	
}
