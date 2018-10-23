package luka.mcts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import pacman.game.Constants.MOVE;
import pacman.controllers.examples.StarterGhosts;
import pacman.game.Game;
import static pacman.game.Constants.*;

public class MCTSRunner{

	public MCTSRunner(MCTSNode<Game> _tree_root, Game game) {
		super();
		if(_tree_root == null) 
		{
			this.tree_root = new MCTSNode<Game>();
		}
		else 
		{
			this.tree_root = _tree_root;
		}
		tree_root.parent = null;
		tree_root.game_state = game;
	}

	public static final float Cp= 0.1f;
	
    private boolean doStop = false;
    
    public MOVE next_move = MOVE.LEFT;
    public MCTSNode<Game> next_state = null;
    
    private MCTSNode<Game> tree_root = null;
    
    public synchronized void doStop() {
        this.doStop = true;
    }

    public synchronized void doStart() {
        this.doStop = false;
    }

    
    private synchronized boolean keepRunning() {
        return this.doStop == false;
    }
	
    public synchronized int GetBestMove() 
    {
    	//System.out.println("-------------------------------------------Gettin' best move-----------------------------------------");
    	return 0;
    }
    
	public MOVE GetMove() {
		long startTime = System.currentTimeMillis();
		// TODO Auto-generated method stub
		while((System.currentTimeMillis() - startTime) < (500) && keepRunning()) 
		//while(true)
		{
			//System.out.println("Current Time: "+System.currentTimeMillis());
			//System.out.println("Curr - start: " + (System.currentTimeMillis() - startTime) +"; DELAY: " + (DELAY));
            // vl TREEPOLICY(v0)
			MCTSNode<Game> selectedChild = TreePolicy(tree_root);
			
			//delta = DEFAULTPOLICY(s(vl))
			int delta = selectedChild.DefaultPolicy();
			
			Backup(selectedChild, delta);
        }
		
		//System.out.println(" ENDING WHILE LOOP ");
		
		next_state = BestChild(tree_root, Cp);
		next_move = next_state.parent_action;
		
		return next_move;
	}
	
	MCTSNode<Game> TreePolicy(MCTSNode<Game> v) 
	{
		//System.out.println("IN TREE POLICY");
		
		StarterGhosts ghosts = new StarterGhosts();
		ArrayList<MOVE> movesToParent = new ArrayList<MOVE>();
		MCTSNode _parent = v;
		while(_parent.parent != null) 
		{
			movesToParent.add(_parent.parent_action);
			_parent = _parent.parent;
		}
		
		//System.out.println("AFTER WHILE");
		
		Game game = ((Game)_parent.game_state).copy();
		
		for(int i = movesToParent.size()-1; i>=0; i--) 
		{
			game.advanceGame(movesToParent.get(i), ghosts.getMove(game, System.currentTimeMillis()));
		}
		
		//while(!v.game_state.wasPacManEaten()) 
		while(!game.wasPacManEaten()) 
		{
			boolean isVFullyExpanded = true;
			if(v.children != null) 
			{
//				if(v.children.size() >= 4) 
//				{
//					isVFullyExpanded = true;
//				}
				for (Entry<MOVE, MCTSNode<Game>> entry : v.children.entrySet()) {
				    if(entry.getValue() == null) 
				    {
				    	isVFullyExpanded = false;
				    }
				}
			}
			else 
			{
				isVFullyExpanded = false;
			}
			
			
			if(!isVFullyExpanded) 
			{
			//	System.out.println("NOT IS FULLY EXPANDED");
				return Expand(v);
			}
			else 
			{
			//	System.out.println("IS FULLY EXPANDED");
				
				v = BestChild(v, Cp);
				if(v == null) 
				{
				//	System.out.println("PARENT ACTION NULL");
				} 
				else 
				{
				//	System.out.println("WUTT THEN");
				}
				if(v.parent_action == null) 
				{
				//	System.out.println("PARENT ACTION NULL");
				} 
				else 
				{
				//	System.out.println("WUTT THEN");
				}
				game.advanceGame(v.parent_action, ghosts.getMove(game, System.currentTimeMillis()));
			}
		}
		return v;
	}
	
	MCTSNode<Game> Expand(MCTSNode<Game> v)
	{
		MOVE next_move = null;
		for (Entry<MOVE, MCTSNode<Game>> entry : v.children.entrySet()) {
		    if(entry.getValue() == null) 
		    {
		    	next_move = entry.getKey();
		    	break;
		    }
		}
		MCTSNode<Game> newNode = new MCTSNode<>();
		newNode.parent_action = next_move;
		newNode.parent = v;
		v.children.put(next_move, newNode); 
		return newNode;
	}
	
	MCTSNode<Game> BestChild(MCTSNode<Game> v, float Cp)
	{
		
		float maxValue = Float.MIN_VALUE;
		MOVE nextMove = null;
		for (Entry<MOVE, MCTSNode<Game>> entry : v.children.entrySet()) {
			//Left part (part 1)
			if(entry.getValue() != null) 
			{
				float currValue = (Math.max(1, entry.getValue().reward) / Math.max(1, entry.getValue().timesvisited));
				//Right part (part 2)
				currValue += Cp * Math.sqrt((2*Math.log(v.timesvisited))/ Math.max(1, entry.getValue().timesvisited));
				if(currValue > maxValue) 
				{
					nextMove = entry.getKey();
					maxValue = currValue;
				}
			}
			else 
			{
				System.out.println("is null --------------");
			}
		}
		if(nextMove == null) 
		{
			//System.out.println("NEXT MOVE IS NULL");
		}
		return v.children.get(nextMove);
	}
	
	void Backup(MCTSNode<Game> bottom_node, int delta) 
	{
		while(bottom_node != null) 
		{
			bottom_node.timesvisited = bottom_node.timesvisited + 1;
			bottom_node.reward = bottom_node.reward + delta;
			bottom_node = bottom_node.parent;
		}
	}
//	function BACKUP(v;)
//	while v is not null do
//	N(v)   N(v) + 1
//	Q(v)   Q(v) + (v; p)
//	v   parent of v
	
}
