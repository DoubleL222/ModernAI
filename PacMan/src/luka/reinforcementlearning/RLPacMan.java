package luka.reinforcementlearning;

import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class RLPacMan extends Controller<MOVE> {
	
	private final int pillReward = 1;
	private final int magicPillReward = 2;
	private final int ghostEatenReward = 3;
	private final int wasEatenReward = -10;
	
	@Override
	public MOVE getMove(Game game, long timeDue) {
		// TODO Auto-generated method stub
		System.out.println("Was pill eaten?: "+game.wasPillEaten() + "; Was power pill eaten?: "+ game.wasPowerPillEaten()+"; Was pacman eaten? "+game.wasPacManEaten());
		int reward = 0;
		
		if(game.wasPillEaten()) 
		{
			reward = pillReward;
		}
		else if(game.wasPowerPillEaten()) 
		{
			reward = magicPillReward;
		}
		else if(game.wasPacManEaten())
		{
			reward = wasEatenReward;
		}
		
		RLManager.getInstance().Log(game, reward);
		return RLManager.getInstance().explore();
	}

}
