package luka.reinforcementlearning;

import java.util.Arrays;
import java.util.Random;
import java.util.Vector;

import luka.behaviorTrees.BTManager;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.internal.Ghost;

//public class QTableState implements Comparable<QTableState>{
public class QTableState {
	public final float MIN_GHOST_DIST = 30.0f;
	
	//Relative positions of closest ghosts, can be varied based on how many ghosts are closer than a minimum
	Vector<int[]> closestGhosts = new Vector<int[]>(); 
	MOVE closestPillDirection;
	Vector<MOVE> possibleMoves;
	
	private MOVE getClosestPillDirection(Game game) 
	{
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
		
		return game.getNextMoveTowardsTarget (game.getPacmanCurrentNodeIndex(),closestPill,DM.PATH);
	}
	
	private Vector<GHOST> getClosestGhosts(Game game, float minGhostDist)
	{
		Vector<GHOST> closeEnoughGhosts = new Vector<GHOST>();

		int pacmanNodeIndex = game.getPacmanCurrentNodeIndex();

		for(GHOST ghost : GHOST.values()) 
		{
			if(game.getGhostCurrentNodeIndex(ghost) != game.getGhostInitialNodeIndex() && !game.isGhostEdible(ghost)) {
				int currDist = game.getShortestPathDistance(pacmanNodeIndex, game.getGhostCurrentNodeIndex(ghost));
				if(currDist != -1 && currDist < minGhostDist) 
				{
					closeEnoughGhosts.add(ghost);
				}
			}
		}
		
		return closeEnoughGhosts;
	}
	
	private Vector<int[]> getClosestGhostPositions(Game game, Vector<GHOST> _closeEnoughGhosts)
	{
		Vector<int[]> ghostPositions = new Vector<int[]>();
		
		int pacmanNodeIndex = game.getPacmanCurrentNodeIndex();
		
		int pacManX = game.getNodeXCood(pacmanNodeIndex);
		int pacManY = game.getNodeYCood(pacmanNodeIndex);
		
		for(GHOST ghost : _closeEnoughGhosts) 
		{
			int ghostNodeIndex = game.getGhostCurrentNodeIndex(ghost);
			
			int ghostX = game.getNodeXCood(ghostNodeIndex);
			int ghostY = game.getNodeYCood(ghostNodeIndex);
			
			ghostPositions.add(new int[] {ghostX - pacManX, ghostY - pacManY});
		}
		
		return ghostPositions;
	}
	
	private Vector<MOVE> getPossibleMoves(Game _game)
	{
		Vector<MOVE> possibleMoves = new Vector<MOVE>();
		MOVE[] moves = _game.getPossibleMoves(_game.getPacmanCurrentNodeIndex());
		for(MOVE _m : moves) 
		{
			possibleMoves.add(_m);
		}
		return possibleMoves;
	}
	
	public QTableState(Game _game) {
		Vector<GHOST> closestGhosts = getClosestGhosts(_game, MIN_GHOST_DIST);
		Vector<int[]> ghostPositions = getClosestGhostPositions(_game, closestGhosts);
		this.closestGhosts = ghostPositions;
		
		this.closestPillDirection = getClosestPillDirection(_game);

		this.possibleMoves = getPossibleMoves(_game);	
	}
	
	public void PrintQTable() 
	{
		String ghostPos = "";
		for(int[] pos : closestGhosts) 
		{
			ghostPos += "["+pos[0]+", "+pos[1]+"]";
		}
		System.out.println("Closest Ghosts: "+ghostPos+"; Closest pill direction: "+closestPillDirection.toString() + "; Possible Moves: "+possibleMoves.toString());
		
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
	    int result = 1;
	    result += prime * result + closestPillDirection.hashCode();
	    for(int[] pos : closestGhosts) 
	    {
		    result += prime * result + pos[0];
		    result += prime * result + pos[1];
	    }
	    for(MOVE m : possibleMoves) 
	    {
	    	result += prime * result + m.hashCode();
	    }
		// TODO Auto-generated method stub
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof QTableState)) 
		{

			return false;
		}
		QTableState o = (QTableState) obj;
		if(closestGhosts.size() != o.closestGhosts.size()) 
		{
			return false;
		}
		else 
		{
			if(closestGhosts.size() > 0) {
				for(int i = 0; i < closestGhosts.size(); i++) 
				{
					//for(int j = 0; j<closestGhosts.get(0).length; j++) 
					//{
						if(!Arrays.equals(closestGhosts.get(i), o.closestGhosts.get(i))) 
						{
							return false;
						}
					//}
				}
			}
		}
		if(closestPillDirection != o.closestPillDirection) 
		{
			return false;
		}
		
		if(possibleMoves.size() != o.possibleMoves.size()) 
		{
			return false;
		}
		else 
		{
			for(int i = 0; i < possibleMoves.size(); i++) 
			{
				if(possibleMoves.get(i) != o.possibleMoves.get(i)) 
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
}
