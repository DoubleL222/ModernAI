package luka.reinforcementlearning;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Random;

import luka.behaviorTrees.BTManager;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class RLManager {
	
    public float explorationChance=0.4f;

    float gammaValue=0.9f;

    float learningRate=0.15f;
    
	
	HashMap<QTableState, float[]> qTable;
	private static final RLManager instance = new RLManager();
	
	private QTableState currentState = null;
	public QTableState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(QTableState currentState) {
		this.currentState = currentState;
	}

	private QTableState prevState = null;
	private QTableState prevPrevState = null;
	private MOVE prevMove = null;
	private MOVE prevPrevMove = null;
	
	public static RLManager getInstance() 
	{
		return instance;
	}
	
	public MOVE explore() 
	{
		Random random = new Random();
		return MOVE.values()[random.nextInt(MOVE.values().length)];
	}
	
	public MOVE getBestMove() 
	{
		MOVE[] allMoves = MOVE.values();
		if(!qTable.containsKey(currentState)) 
		{
			return explore();
		}
		float[] currQvalues = GetValues(currentState);
    	if(currQvalues == null) 
    	{
    		return explore();
    	}
    	float maxVal = Float.MIN_VALUE;
    	int index = -1;
//        System.out.println("  UP   RIGHT  DOWN  LEFT" );
    	for(int i = 0; i<currQvalues.length; i++) 
    	{
//    		System.out.print(currQvalues[i] +"; ");
    		if(currQvalues[i]>maxVal) 
    		{
    			maxVal = currQvalues[i];
    			index = i;
    		}	
    	}
    	if(index == -1) 
    	{
    		//System.out.println("#############--------------PANIC: MINUS ONE-------------------#############");
    		index = 0;
    	}else if(index >4) 
    	{
    		//System.out.println("#############--------------PANIC: GREATER THAN FOUR-------------------#############");
    	}
    	return allMoves[index];
        //return 0;
	}
	
	public RLManager() 
	{
		qTable = new HashMap<QTableState, float[]>();
	}
	
	float[] GetValues(QTableState _state) 
	{
		if(qTable.containsKey(_state)) 
		{
			return qTable.get(_state);
		}
		return null;
	}
	
	public void Log(Game _game, int _reward) 
	{
		QTableState state = new QTableState(_game);
		MOVE currMove = _game.getPacmanLastMoveMade();
		currentState = state;
		//First two states
		if(prevPrevState == null) 
		{
			if(prevState == null) 
			{
				prevState = state;
				prevMove = currMove;
			}
			else 
			{
				prevPrevState = prevState;
				prevState = state;
				prevPrevMove = prevMove;
				prevMove = currMove;
			}
		}
		//After first two states
		else 
		{
			boolean contains = false;
			if(qTable.containsKey(currentState)) 
			{
				contains = true;
			}
			
			if(!contains) 
			{
				//System.out.println("NEW STATE:");
				float[] newValues = new float[MOVE.values().length];
				qTable.put(state, newValues);
				//state.PrintQTable();
			}
			else 
			{
				float[] qValues = GetValues(state);
				for(float _v : qValues) 
				{
					//System.out.print(_v +" ,");
				}
				//System.out.println();
			}
			
			float[] prevPrevQValues = GetValues(prevPrevState);
			float prevPrevQValue = prevPrevQValues[prevMove.ordinal()];
        	float[] prevQvalues = GetValues(prevState);
        	
			if(!prevState.equals(prevPrevState) && prevPrevQValues != null && prevQvalues != null) 
			{
				float prevQvalue;
	        	
	        	float maxVal = Float.MIN_VALUE;
	        	int index = -1;
	        	for(int i = 0; i<prevQvalues.length; i++) 
	        	{
	        		if(prevQvalues[i]>maxVal) 
	        		{
	        			maxVal = prevQvalues[i];
	        			index = i;
	        		}	
	        	}
	        	prevQvalue = maxVal;
	        	
	        	float gain = learningRate * (_reward + gammaValue * prevQvalue - prevPrevQValue);
	        	float updatedQValue = prevPrevQValue + gain;
	        	
	        	//Update
	        	prevPrevQValues[prevMove.ordinal()] = updatedQValue;
	        	qTable.put(prevPrevState, prevQvalues);
			}
			
			//TODO UPDATE Q TABLE
			//Set prev state now
			prevPrevState = prevState;
			prevState = state;
			prevPrevMove = prevMove;
			prevMove = currMove;
		}
	}
}
