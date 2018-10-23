package luka.mcts;

import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class MCTSPacMan extends Controller<MOVE> {

	private Thread myThread = null;
	private MCTSRunner myRunner = null;

	@Override
	public MOVE getMove(Game game, long timeDue) {

		MCTSNode<Game> next_root = null;
		
		if(myRunner != null) 
		{
			next_root = myRunner.next_state;
		}
		
		myRunner = new MCTSRunner(next_root, game);
		
		return myRunner.GetMove();
	}
}
