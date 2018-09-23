package luka.behaviorTrees.controller;

import luka.behaviorTrees.BTManager;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class BTPacMan extends Controller<MOVE>{

	@Override
	public MOVE getMove(Game game, long timeDue) {
		return BTManager.getInstance().QuerryTree(game);
		//return null;
	}

}
