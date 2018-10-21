package luka.mcts;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

import luka.behaviorTrees.BTManager;
import pacman.controllers.examples.StarterGhosts;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MCTSNode<T> {
	public static final int MAX_TREE_DEPTH = 20;
	public static final int PACMAN_DEATH_PENALTY = -10000;
	public T game_state = null;
	public HashMap<MOVE, MCTSNode<T>> children = new HashMap<MOVE, MCTSNode<T>>() 
	{{
		put(MOVE.UP, null);
		put(MOVE.RIGHT, null);
		put(MOVE.DOWN, null);
		put(MOVE.LEFT, null);
	}};
//	public List<MCTSNode<T>> children = new ArrayList<MCTSNode<T>>();
	public MCTSNode<T> parent = null;
	public MOVE parent_action;
	public float reward = 0;
	public int timesvisited = 0;
	
	public int DefaultPolicy() 
	{
		int depth = 0;
		StarterGhosts ghosts = new StarterGhosts();
		
		ArrayList<MOVE> movesToParent = new ArrayList<MOVE>();
		
		MCTSNode _parent = this;
		while(_parent.parent != null) 
		{
			movesToParent.add(parent_action);
			_parent = _parent.parent;
		}
		
		Game game = ((Game)_parent.game_state).copy();
		
		for(int i = movesToParent.size()-1; i>=0; i--) 
		{
			game.advanceGame(movesToParent.get(i), ghosts.getMove(game, System.currentTimeMillis()));
		}
		
		while(!game.wasPacManEaten() && !game.gameOver() && (game.getNumberOfActivePills() > 0 && game.getNumberOfActivePowerPills() > 0) && depth < MAX_TREE_DEPTH) 
		{
			//Use BehaviourTree to get next move
			game.advanceGame(BTManager.getInstance().QuerryTree(game), ghosts.getMove(game, System.currentTimeMillis()));
		}
		
		return GetReward(game);
	}
	
	private int GetReward(Game _game) 
	{ 
		if(_game.wasPacManEaten() || _game.gameOver()) 
		{
			return PACMAN_DEATH_PENALTY;
		}
		else 
		{
			return _game.getScore();
		}
	}
}
