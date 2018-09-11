package luka.behaviorTrees.controller;

import luka.behaviorTrees.BehaviorTreeManager;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public class BehaviorTreePacMan extends Controller<MOVE>{

	@Override
	public MOVE getMove(Game game, long timeDue) {
		return BehaviorTreeManager.getInstance().QuerryTree(game);
		//return null;
	}

}
