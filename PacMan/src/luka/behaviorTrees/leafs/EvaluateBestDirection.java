package luka.behaviorTrees.leafs;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Vector;

import org.omg.CORBA._PolicyStub;

import luka.behaviorTrees.BTManager;
import luka.behaviorTrees.LeafNode;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

public class EvaluateBestDirection extends LeafNode {
	private int stepsToCheckAhead = 3;
	private float dangerDist = 30.0f;
	
	public int getStepsToCheckAhead() {
		return stepsToCheckAhead;
	}

	public void setStepsToCheckAhead(int stepsToCheckAhead) {
		this.stepsToCheckAhead = stepsToCheckAhead;
	}
	
	private Game game;

	@Override
	public boolean Activate() {
		game = BTManager.getInstance().getGame();
		int currentNode = game.getPacmanCurrentNodeIndex();
		Vector<Vector<Integer>> currMoves = new Vector<Vector<Integer>>();
		Vector<Integer> cm = new Vector<Integer>();
		cm.add(currentNode);
		currMoves.add(cm);
		Vector<Vector<Integer>> possibleMoves = GetPossiblePaths(currMoves, currentNode, game);
		
//		for(Vector<Integer> _v1 : possibleMoves) 
//		{
//			System.out.println("NEW PATH");
//			for(int _i : _v1) 
//			{
//				System.out.println("path: "+_i);
//			}
//		}
		int minRating = Integer.MIN_VALUE;
		int minIndex = 0;
		for(int i = 0; i<possibleMoves.size(); i++) 
		{
			int pathRating = EvaluatePath(possibleMoves.get(i), game);
			System.out.print("r: "+pathRating+"; ");
			if(pathRating > minRating) 
			{
				minIndex = i;
				minRating = pathRating;
			}
		}
		System.out.println();
		BTManager.getInstance().setNextMove(game.getNextMoveTowardsTarget(currentNode, possibleMoves.get(minIndex).lastElement(), DM.PATH));
		//game.getNeighbour(currentNode, moveToBeMade)
		GameView.addLines(game, Color.RED, new int[] {currentNode} , new int[] {possibleMoves.get(minIndex).lastElement()});
		return true;
	}
	
	int EvaluatePath(Vector<Integer> path, Game game) 
	{
		ArrayList<GHOST> closeGhosts = new ArrayList<GHOST>();
		int rating = 0;
		for(int pos : path) 
		{
			System.out.print(pos+", ");
			for(GHOST ghost : GHOST.values()) 
			{
				int ghostPos = game.getGhostCurrentNodeIndex(ghost);
				if(ghostPos > 0 && ghostPos != game.getGhostInitialNodeIndex()) 
				{
					float ghostDist = (float) game.getDistance(pos, ghostPos, DM.PATH);
					if(ghostDist <= dangerDist) 
					{
						rating+=ghostDist;
					}
				}
			}
		}
		
		return rating;
	}
	
	Vector<Vector<Integer>> GetPossiblePaths(Vector<Vector<Integer>> currMoves, int currMapIndex, Game game)
	{
		if(currMoves.size()>0) 
		{
			if(currMoves.get(0).size() > 0) 
			{
				if(currMoves.get(0).size() >= stepsToCheckAhead) 
				{
					return currMoves;
				}
			}
		}
		Vector<Vector<Integer>> allMoves = new Vector<Vector<Integer>>();
		Vector<Vector<Integer>> newMoves = new Vector<Vector<Integer>>();
//		if(game.isJunction(currMapIndex)) 
//		{
//			
			for(MOVE _m : game.getPossibleMoves(currMapIndex))
			{
				int newPos = game.getNeighbour(currMapIndex, _m);
				for(Vector<Integer> _v : currMoves) 
				{
					if(!_v.contains(newPos)) {
						_v.add(newPos);
						newMoves.add(_v);
					}
				}
				allMoves.addAll(GetPossiblePaths(newMoves, newPos, game));
				newMoves.clear();
			}
	
//		}
//		else 
//		{
//			for(MOVE _m : game.getPossibleMoves(currMapIndex))
//			{
//				int newPos = game.getNeighbour(currMapIndex, _m);
//				for(Vector<Integer> _v : currMoves) 
//				{
//					if(!_v.contains(newPos)) {
//						_v.add(newPos);
//						allMoves.add(_v);
//					}
//				}
//			}
//		}
		
		return allMoves;
		
	}
	
}
