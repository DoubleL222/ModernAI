package luka.mcts;

import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MCTSPacMan extends Controller<MOVE> {

	private Thread myThread = null;
	private MCTSRunner myRunner = null;

	@Override
	public MOVE getMove(Game game, long timeDue) {

		MOVE next_move = MOVE.LEFT;
		MCTSNode<Game> next_root = null;
		
		if(myRunner != null) 
		{
			myRunner.doStop();
			next_move = myRunner.next_move;
			next_root = myRunner.next_state;
			myRunner.doStart();
		}
		else 
		{
			myRunner = new MCTSRunner(next_root, game);
			myThread = new Thread(myRunner);
			myThread.start();
		}
		
		return next_move;
	}
}
