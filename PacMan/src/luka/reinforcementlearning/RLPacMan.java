package luka.reinforcementlearning;

import java.util.Random;

import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class RLPacMan extends Controller<MOVE> {
	
	private final int pillReward = 1;
	private final int magicPillReward = 2;
	private final int ghostEatenReward = 3;
	private final int wasEatenReward = -10;
	public boolean learn;
	int deaths = 0;
	int maxDeaths = 0;
	
	public RLPacMan(boolean _learn, int trainIterations) {
		super();
		learn = _learn;
		deaths = 0;
		maxDeaths = trainIterations * 2;
		// TODO Auto-generated constructor stub
	}
	
	public RLPacMan(boolean _explore) {
		super();
		learn = _explore;
		// TODO Auto-generated constructor stub
	}

	@Override
	public MOVE getMove(Game game, long timeDue) {
		// TODO Auto-generated method stub
		//System.out.println("Was pill eaten?: "+game.wasPillEaten() + "; Was power pill eaten?: "+ game.wasPowerPillEaten()+"; Was pacman eaten? "+game.wasPacManEaten());
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
			deaths += 1;
			reward = wasEatenReward;
		}
		
		RLManager.getInstance().Log(game, reward);

		if(learn) {
			System.out.println("DEATHS: "+deaths+"; maxDeaths: "+maxDeaths);
			float chanceToGetBestMove = (float)deaths/ (float)maxDeaths;
			Random rand = new Random();
			if(rand.nextFloat()>chanceToGetBestMove) 
			{
				System.out.println("---EXPLORE---");
				return RLManager.getInstance().explore();
			}
			else 
			{
				System.out.println("---GET BEST MOVE---; "+chanceToGetBestMove);
				return RLManager.getInstance().getBestMove();
			}

		}
		else 
		{
			return RLManager.getInstance().getBestMove();
		}
	}

}
