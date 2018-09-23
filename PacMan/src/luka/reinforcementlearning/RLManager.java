package luka.reinforcementlearning;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Random;

import luka.behaviorTrees.BTManager;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class RLManager {
	HashMap<QTableState, float[]> qTable;
	private static final RLManager instance = new RLManager();
	
	private QTableState prevState = null;
	private QTableState prevPrevState = null;
	
	public static RLManager getInstance() 
	{
		return instance;
	}
	
	public MOVE explore() 
	{
		Random random = new Random();
		return MOVE.values()[random.nextInt(MOVE.values().length)];
	}
	
	public RLManager() 
	{
		qTable = new HashMap<QTableState, float[]>();
	}
	
	public void Log(Game _game, int _reward) 
	{
		QTableState state = new QTableState(_game);
		
		//First two states
		if(prevPrevState == null) 
		{
			if(prevState == null) 
			{
				prevState = state;
			}
			else 
			{
				prevPrevState = prevState;
				prevState = state;
			}
		}
		//After first two states
		else 
		{
			boolean contains = false;
			for(QTableState _q : qTable.keySet()) 
			{
				if(_q.compareTo(state) == 0) 
				{
					contains = true;
				}
			}
			if(!contains) 
			{
				//System.out.println("NEW STATE:");
				float[] newValues = new float[MOVE.values().length];
				qTable.put(state, newValues);
			}
			//TODO UPDATE Q TABLE
			
			
			//state.PrintQTable();
			
			//Set prev state now
			prevPrevState = prevState;
			prevState = state;
		}
	}
}
