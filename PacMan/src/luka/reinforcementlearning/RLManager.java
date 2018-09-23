package luka.reinforcementlearning;

import pacman.game.Game;

public class RLManager {
	public RLManager() 
	{
		
	}
	
	public void LogData(Game _game) 
	{
		int pacManIndex = _game.getPacmanCurrentNodeIndex();
		int[] pacManPos = new int[] {_game.getNodeXCood(pacManIndex), _game.getNodeYCood(pacManIndex)};
		
	}
}
