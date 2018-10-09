package luka.mcts;

import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MCTSPacMan extends Controller<MOVE> {

	private Thread myThread;
	private MCTSRunner myRunner;

	@Override
	public MOVE getMove(Game game, long timeDue) {

//		myRunner.doStop();
		//myRunner.interrupt();
		//myRunner.st();
		System.out.println("Best move: "+ myRunner.GetBestMove()); 
		
//		myRunner.doStart();
		
//		myRunner = new MCTSRunner();
//		myThread = new Thread(myRunner);
//		myThread.start();
//		myRunner.run();
		return null;
	}

	public MCTSPacMan() 
	{
		myRunner = new MCTSRunner();
		myThread = new Thread(myRunner);
		myThread.start();
	}
}
